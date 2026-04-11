package hy.ea.finance.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import hy.ea.bo.finance.BenDis.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.WfjGuize;
import com.tiantai.wfj.service.GoldOrderService;
import com.tiantai.wfj.service.impl.WfjJifenServiceImpl;

import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.lottery.PrizeActivityBean;
import hy.ea.bo.lottery.SignScoreBean;
import hy.ea.bo.office.BookingInformation;
import hy.ea.finance.dao.BonusPointsDao;
import hy.ea.finance.service.BonusPointsService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
@Service

public class BonusPointsServiceImpl implements BonusPointsService{


	@Resource
	private BaseBeanDao beandao;
	@Resource
	private BonusPointsDao bonusPointsDao;
	@Resource
	private ServerService serverService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private GoldOrderService gOrderService;
	
	
	
	private final static Logger logger=LoggerFactory.getLogger(BonusPointsServiceImpl.class);

	
	/**
	 * 签到送积分
	 * @param sccid 签到人的sccid
	 * @param staffid  签到人的staffid
	 * @param account  签到人的帐号
	 * @param companyId  被签到公司id
	 * @param num  签到赠送的积分数
	 * @return
	 */
	@Override
	@Transactional
	public String toSign(String sccid, String staffid, String account, String companyId, Integer num, String isFlag)
	{
		if(sccid == null || staffid == null || account == null || companyId == null || num  <= 0)
		{
			return "操作失败";
		}
		String flag = "";
		String type = "sign";//签到标识
		List<BaseBean> beans = new ArrayList();  
		BigDecimal JF = new BigDecimal(num);
		logger.error("sccid="+sccid+"  "+"staffid="+staffid+"  "+"account="+account+"  "+"companyId="+companyId+"  "+"num="+num);
		
		//判断用户是否有积分表			
		String bonusPointsId = addBP(sccid,JF,beans);
										
		//被签到公司积分 
		TEshopCusCom cc = (TEshopCusCom)baseBeanService
				.getBeanByHqlAndParams("from TEshopCusCom where companyId=? ", new Object[]{companyId});				
		BonusPoints cpJF = (BonusPoints)baseBeanService
				.getBeanByHqlAndParams("from BonusPoints where sccid = ?", new Object[]{cc.getSccId()});
		if(cpJF == null )
		{
			//该公司没有积分	 			 
			flag = "该公司没有积分";
			return flag;
		}
		else
		{
			BigDecimal companyJF = new BigDecimal(cpJF.getBonusPointScore());
			//说明签到积分  比  公司总积分  多	
			if(companyJF.compareTo(JF) < 0)
			{ 											
				flag = "积分不够";
				return flag;
			}
			else
			{
				BigDecimal  sum = companyJF.subtract(JF);
                cpJF.setBonusPointScore(sum + "");
			}			
		}
		beans.add(cpJF);
		boolean str = detail(type, sccid, staffid, account, companyId,
				bonusPointsId, cpJF.getBonusPointsId(), JF, beans, null, true, isFlag);
		if(str == false)
		{
			flag = "操作失败";
			return flag;
		}
		else
		{														
			beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null); 
			flag = "签到成功";
			List<String> slist=new ArrayList<String>() ;
			slist.add(account);
			JushMain.sendjiguangMessage("签到成功赠送积分"+num+"分","", "", "envelope", slist);
		}	
		return flag;  
	}

	
	/**
	 * 分享文章   赠送积分
	 * @param sccid  分享文章人  sccid
	 * @param staffid  分享文章人  staffid
	 * @param account  分享文章帐号
	 * @param ppid   被分享文章的 id
	 * @return 字符串 flag
	 */
	@Override
	@Transactional
	public String shareArticle(String sccid, String staffid,String account, String ppid) 
	{	
		logger.error("*************分享文章****************");
		if(sccid == null || staffid == null || account == null || ppid == null)
		{
			return "操作失败";
		}
		String flag = "";
		logger.error("ppid="+ppid);
		logger.info("调试信息"); 
		String type = "shareArticle";//分享文章的标识
		List<BaseBean> beans = new ArrayList<BaseBean>();
		BigDecimal JF = new BigDecimal("500");//规定分享文章  可获得  500 个积分
		
		//查找分享文章标题
		ProductPackaging pp = (ProductPackaging)baseBeanService
				.getBeanByHqlAndParams("from ProductPackaging where ppID= ? ", new Object[]{ppid});
		
		//判断该文章是否分享过
		BonusPointsDetail bpd = (BonusPointsDetail)baseBeanService
				.getBeanByHqlAndParams("from BonusPointsDetail where ppid = ? ", new Object[]{ppid});
		
		if(pp != null && bpd == null)
		{
			
			//判断用户是否有积分表			
			String bonusPointsId = addBP(sccid,JF,beans);
			
			//分享文章，默认积分出库是该公司
			String companyId = "company201009046vxdyzy4wg0000000025";
			boolean str = detail(type, sccid, staffid, account, companyId,
					bonusPointsId, null, JF, beans, ppid, true, null);//明细
			if(str == false)
			{
				flag = "操作失败";
				return flag;
			}
			else
			{				
				
				//beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
				flag = "分享《"+pp.getGoodsName()+"》文章，赠送500个积分";
			}					
		}
		else
		{
			flag = "no";
			return flag;
		}		
		return flag;
	}
	
	/**
	 *分享二维码 注册成功 上级获得赠送积分   赠送积分
	 * @param sccid  分享二维码的人的sccid
	 * @param staffid  分享二维码的人的staffid
	 * @param account 分享二维码的人 的帐号
	 * @param account 赠送积分数
	 * @return 字符串  flag
	 */
	@Override
	@Transactional
	public String shareCode(String sccid,String staffid,String account,String jiFen)
	{
		if(sccid == null || staffid == null || jiFen == null)
		{
			return "操作失败";
		}
		String flag = "";
		String type = "shareCode";
		List<BaseBean> beans = new ArrayList<BaseBean>(); 
		BigDecimal JF = new BigDecimal(jiFen);
				
		//判断用户是否有积分表
		String bonusPoints = addBP(sccid,JF,beans);
				
		String companyId = "company201009046vxdyzy4wg0000000025";//分享二维码，发展下级成功，赠送积分
		boolean str = detail(type, sccid, staffid, account, companyId,
				bonusPoints, null, JF, beans, null, true, null);//明细
		if(str == false)
		{
			flag = "no";
			return flag;
		}
		else
		{														
			//beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			flag = "ok";
		}
		
		return flag;
	}

	/**
	 *判断是否有积分表	
	 * @param sccid  用户sccId
	 * @return 积分id
	 */
	private String addBP(String sccid,BigDecimal JF,List<BaseBean> beans){
				
		String hql = "from BonusPoints where sccid = ? ";
		BonusPoints bonusPoints = (BonusPoints)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid});
		//bonusPoints  为 null  是该用户没有 积分表
		if(bonusPoints == null)
		{
			bonusPoints = new BonusPoints();			
			bonusPoints.setBonusPointsId(serverService.getServerID("BonusPoints"));
			bonusPoints.setSccid(sccid);
			bonusPoints.setBonusPointScore(JF + "");
		}
		else
		{
			BigDecimal bp=new BigDecimal(bonusPoints.getBonusPointScore());
			BigDecimal  sum = bp.add(JF);			
			bonusPoints.setBonusPointScore(sum + "");
		}
		beans.add(bonusPoints);
							
		return bonusPoints.getBonusPointsId();		
	}
	
	
	/**
	 * 库存明细
	 * @param type   判断是签到，分享的标识 type="sign" 签到  type="shareArticle" 分享文章   type="shareCode" 分享二维码
	 * @param sccid   用户的sccis
	 * @param staffid   用户的staffid
	 * @param account  用户的帐号
	 * @param companyId  签到是  被签到的公司id    分享  是平台的公司id
	 * @param bonusPointsId  用户的积分id
	 * @param cpJFId  公司的积分id
	 * @param nums 赠送积分数
	 * @param beans  实体类
	 * @param ppid  分享文章的id
	 * @param subtract
	 * @return  boolean 类型
	 */
	private boolean detail(String type, String sccid, String staffid, String account, String companyId,
						   String bonusPointsId, String cpJFId, BigDecimal nums, List<BaseBean> beans,
						   String ppid, boolean subtract, String isFlag)
	{
		try {
			Company cp = (Company)baseBeanService
					.getBeanByHqlAndParams(" from Company where companyID= ?", new Object[]{companyId});
			String cpName = cp.getCompanyName();
			String groupCompanySn = cp.getGroupCompanySn();
			String goodsId="goodsID20170220ZVZR76B88M0000000014";

			BigDecimal pri = new BigDecimal("100");//积分单价
			Staff staff = (Staff)beandao
					.getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{staffid});
			String staname = staff.getStaffName();



			//出库单 出库单(公司)
			CashierBills cb = new CashierBills();
			cb.setCashierBillsID(serverService.getServerID("BonusPoints"));
			cb.setCashierDate(new Date());
			cb.setBillsType("积分出库单");
			cb.setJournalNum(serverService.getBillID(companyId));
			cb.setCompanyID(companyId);
			cb.setCompanyName(cpName);
			cb.setGroupCompanySn(groupCompanySn);
			cb.setStatus("16");
			cb.setAppstyle("14");//积分分配
			cb.setStatusbill("04");
			cb.setPriceSub((nums.divide(pri))+"");
			cb.setProID("007");
			cb.setInputName("系统生成");
			cb.setContactUserID(staffid);
			cb.setCtUserName(staname);
			//签到
			if("sign".equals(type))
			{
				cb.setProjectName("签到送积分");
			}
			else if("shareArticle".equals(type))
			{
				cb.setProjectName("分享文章赠送积分");
				cb.setCompanyAddr("东直门外大街42号宇飞大厦801室");
				cb.setCompanyTel("010-64167113");
				cb.setCresponsible("刘太平");
				cb.setResponsibleTel("15810799888");
			}
			else if("shareCode".equals(type))
			{
				cb.setProjectName("分享二维码,发展下级成功赠送积分");
			}

			beans.add(cb);

			String manageHql = "from ProductPackaging where goodsID=?";
			ProductPackaging manage = (ProductPackaging)beandao
					.getBeanByHqlAndParams(manageHql, new Object[]{goodsId});

			GoodsBills gb = new GoodsBills();
			gb.setCashierBillsID(cb.getCashierBillsID());
			gb.setGoodsBillsID(serverService.getServerID("GoodsBills"));
			gb.setGoodsID(goodsId);
			gb.setGoodsNum(manage.getGoodsNum());
			gb.setGoodsName(manage.getGoodsName());
			gb.setStandard(manage.getStandard());
			gb.setGoodsVariableID(manage.getVariableID());
			gb.setWeight(manage.getWeight());
			gb.setPrice("0.01");
			gb.setQuantity(nums + "");
			gb.setMoney((nums.divide(pri))+"");
			gb.setPayType("14");
			gb.setKcStatus("16");
			gb.setGoodstatus("00");
			gb.setPpID(manage.getPpID());
			beans.add(gb);


			//积分入库单
			CashierBills cb1 = new CashierBills();
			cb1.setCashierBillsID(serverService.getServerID("BonusPoints"));
			cb1.setCashierDate(new Date());
			cb1.setJournalNum(serverService.getBillID(companyId));
			cb1.setBillsType("积分入库单");
			cb1.setStatus("15");
			cb1.setStaffID(staffid);
			cb1.setStaffName(staname);
			// 获得公司id
			@SuppressWarnings("unchecked")
			List<Object> list = baseBeanService.getListBeanBySqlAndParams(
					"select t.pseudo_company_name,t.companyId from T_ESHOP_CUSCOM t "
							+ "where t.state=2 connect by nocycle prior t.suppersccid = t.SCCID start with t.SCCID = ?",
					new Object[] {sccid });
			Object rk = list.get(0);
			Object[] rkd = (Object[]) rk;
			cb1.setCompanyID(rkd[1].toString());
			cb1.setCompanyName(rkd[0].toString());
			cb1.setInputid(staffid);
			cb1.setInputName(staname);
			cb1.setCcompanyID(companyId);
			cb1.setCcompanyName(cpName);
			beans.add(cb1);

			GoodsBills gb1 = new GoodsBills();
			gb1 = (GoodsBills)gb.cloneGoodsBills();
			gb1.setGoodsBillsID(serverService.getServerID("GoodsBills"));
			gb1.setCashierBillsID(cb1.getCashierBillsID());
			gb1.setKcStatus("15");
			beans.add(gb1);

			String depotHql = "from DepotManage where depotPID=? and "
					+ "companyID=? and depotName=? and depotState=?";
			DepotManage depot = (DepotManage)beandao
					.getBeanByHqlAndParams(depotHql, new Object[] { "001", companyId, "销售库", "00" });
			FinancialBill fin = new FinancialBill();
			fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
			fin.setCashierBillsID(cb.getCashierBillsID());
			fin.setGroupCompanySn(groupCompanySn);
			fin.setCompanyID(companyId);
			fin.setOrganizationID(null);
			fin.setStaffsID(staffid);
			fin.setStaffsName(staname);
			fin.setJournalNum(serverService.getBillID(companyId));
			fin.setBillsDate(new Date());
			fin.setBillStaffID(staffid);
			fin.setBillStaffName(staname);
			fin.setBillsType("积分出库单");
			fin.setDepotID(depot.getDepotID());
			fin.setDepotName(depot.getDepotName());
			beans.add(fin);

			String invHql = "from Inventory where companyID=? and goodsID=? and warehouse=?";
			Inventory inv = (Inventory)this.beandao.getBeanByHqlAndParams(invHql, new Object[] { companyId, goodsId, depot.getDepotID() });

			if("shareArticle".equals(type)|| "shareCode".equals(type)){
				//库存表(出库)
				if (inv == null){
					inv = new Inventory();
					inv.setInventoryID(serverService.getServerID("BonusPoints"));
					inv.setCompanyID(companyId);
					inv.setGroupCompanySn(groupCompanySn);
					inv.setWarehouse(depot.getDepotID());
					inv.setWarehouseName(depot.getDepotName());
					inv.setGoodsID(manage.getGoodsID());
					inv.setGoodsName(manage.getGoodsName());
					inv.setGoodsType(manage.getTradeID());
					inv.setStandard(manage.getStandard());
					inv.setGoodsCoding(manage.getGoodsNum());
					inv.setInvenQuantity("-"+nums+"");
					inv.setGoodstatus("00");
					beans.add(inv);
				}else{
					BigDecimal in = new BigDecimal(inv.getInvenQuantity());
					inv.setInvenQuantity((in.add(nums))+"");
					beans.add(inv);
				}
				// 库存盘点表	(出库明细）
				stockInv sto = new stockInv();
				sto.setStockinvID(serverService.getServerID("BonusPoints"));
				sto.setCompanyID(companyId);
				sto.setGroupCompanySn(groupCompanySn);
				sto.setGoodsID(manage.getGoodsID());
				sto.setGoodsType(manage.getTradeID());
				sto.setGoodsName(manage.getGoodsName());
				sto.setInvenQuantity(nums + "");
				sto.setIntime(new Date());
				sto.setType("01");
				sto.setWarehouse(depot.getDepotID());
				sto.setWarehouseName(depot.getDepotName());
				beans.add(sto);

				//入库明细
				BonusPointsDetail bpd = new BonusPointsDetail();
				if("shareArticle".equals(type))
				{
					WfjGuize shareGuize = (WfjGuize) beandao.getBeanByHqlAndParams("from WfjGuize where wfjGuizeName=?",new Object[] { "分享文章送积分" });
					bpd.setWfjGuizeId(shareGuize.getWfjGuizeId());
					bpd.setBonusPointsDetailName("分享文章送积分");
				}
				else if("shareCode".equals(type))
				{
					WfjGuize shareCode = (WfjGuize) beandao.getBeanByHqlAndParams("from WfjGuize where wfjGuizeName=?",new Object[] { "分享二维码,发展下级成功赠送积分" });
					bpd.setWfjGuizeId(shareCode.getWfjGuizeId());
					bpd.setBonusPointsDetailName("分享二维码，发展下级成功，赠送积分");

				}
				bpd.setBonusPointsDetailId(serverService.getServerID("BonusPointsDetail"));
				bpd.setBonusPointsDetailScore(nums + "");
				bpd.setBonusPointsDetailDate(new Date());
				bpd.setBonusPointsId(bonusPointsId);
				bpd.setWfjCashId(cb1.getCashierBillsID());
				bpd.setWfjGoodId(gb1.getGoodsBillsID());
				bpd.setPpid(ppid);
				beans.add(bpd);
			}

			if("sign".equals(type)){


				//公司 明细（积分减少）
				//判断签到是个人还是公司
				TEshopCusCom cc = (TEshopCusCom)baseBeanService
						.getBeanByHqlAndParams("from TEshopCusCom where sccid=?", new Object[]{sccid});
				String hql = "from WfjGuize where wfjGuizeName=? and wfjGuizeCalc=? ";
				WfjGuize signGuizeE = (WfjGuize) beandao
						.getBeanByHqlAndParams(hql,new Object[] { "用户签到失去积分","M" });
				BonusPointsDetail cpbpd = new BonusPointsDetail();
				cpbpd.setBonusPointsDetailId(serverService.getServerID("BonusPointsDetail"));
				if("2".equals(cc.getState())){//说明是公司
					Company cname = (Company)baseBeanService
							.getBeanByHqlAndParams(" from Company where companyID= ?", new Object[]{cc.getCompanyId()});
					cpbpd.setBonusPointsDetailName(cname.getCompanyName()+"签到-失去积分");
				}else{
					cpbpd.setBonusPointsDetailName(staname+"签到-失去积分");
				}
				cpbpd.setWfjGuizeId(signGuizeE.getWfjGuizeId());
				cpbpd.setBonusPointsDetailScore(nums + "");
				cpbpd.setBonusPointsDetailDate(new Date());
				cpbpd.setBonusPointsId(cpJFId);
				cpbpd.setWfjCashId(cb.getCashierBillsID());
				cpbpd.setWfjGoodId(gb.getGoodsBillsID());
				cpbpd.setPpid(manage.getPpID());
				beans.add(cpbpd);

				//用户明细（积分增加）
				WfjGuize signGuizeI = (WfjGuize) beandao
						.getBeanByHqlAndParams(hql,new Object[] { "签到获得赠送积分","A" });
				BonusPointsDetail bpd = new BonusPointsDetail();
				bpd = (BonusPointsDetail)cpbpd.cloneBonusPointsDetail();
				bpd.setBonusPointsDetailId(serverService.getServerID("BonusPointsDetail"));
				bpd.setBonusPointsDetailName("签到送积分");
				bpd.setWfjGuizeId(signGuizeI.getWfjGuizeId());
				bpd.setBonusPointsId(bonusPointsId);
				bpd.setWfjCashId(cb1.getCashierBillsID());
				bpd.setWfjGoodId(gb1.getGoodsBillsID());
				beans.add(bpd);

				//签到
				Sign sign = null;
				if (isFlag == null)
				{
					sign = new Sign();
					sign.setSignId(this.serverService.getServerID("Sign"));
					sign.setSccid(sccid);
					sign.setAccount(account);
					sign.setCompanyId(companyId);
					sign.setSignScore(nums+"");
					sign.setSignDate(new Date());
					sign.setBonusPointsId(bonusPointsId);
					sign.setBonusPointsDetailId(bpd.getBonusPointsDetailId());
					beans.add(sign);
				}
				else {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String time = df.format(new Date());
					StringBuffer hqlSign = new StringBuffer();
					List<Object> obj = new ArrayList<Object>();
					hqlSign.append(" from Sign ");
					hqlSign.append(" where bonusPointsId is null and companyId=? ");
					hqlSign.append(" and account=? ");
					hqlSign.append(" and signDate between ? and ?");
					hqlSign.append(" order by signDate desc");
					obj.add(companyId);
					obj.add(account);
					obj.add(Utilities.getDateFromString(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					obj.add(Utilities.getDateFromString(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
					List<BaseBean> listSign = this.baseBeanService.getListBeanByHqlAndParams(hqlSign.toString(), obj.toArray());
					if (listSign != null) {
						sign = (Sign) listSign.get(0);
						sign.setSignScore(nums + "");
						sign.setBonusPointsId(bonusPointsId);
						sign.setBonusPointsDetailId(bpd.getBonusPointsDetailId());
						beans.add(sign);
					}
					else
					{
						subtract = false;
					}
				}
			}
		} catch (CloneNotSupportedException e) {
			logger.error("操作异常", e);
			subtract = false;
		}
		return subtract;
	}
	
	/**
	 * 获取积分详情列表
	 * @param pageNumber  分页（第几页）
	 * @param sccid  所查询积分明细人的sccid
	 * @return  map
	 */	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public PageForm getDetailList(int pageNumber,String sccid,String sdate,String edate,String type,String operator,String inAndExp,String schType) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<Object[]> signList = new ArrayList<Object[]>();
		//目前只提供一年内的积分明细记录
//		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.YEAR, 0);
//		sdate = new SimpleDateFormat("yyyy-01-01 00:00:00").format(calendar.getTime());
//		edate = new SimpleDateFormat("yyyy-12-31 23:59:59").format(calendar.getTime());

		List<Object> para = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select pd.bonuspointsdetailname,pd.bonuspointsdetailscore,to_char(pd.bonuspointsdetaildate,'yyyy-mm-dd hh24:mi:ss'),pd.bonuspointsdetailid,wg.wfj_guize_calc,wg.wfj_guize_ico,");
		sql.append(	"hs.staffname,case when wg.wfj_guize_name ='佣金' then nvl(pp.goodsname,wg.wfj_guize_name) when wg.wfj_guize_name !='佣金' then wg.wfj_guize_name end,cb.jnumorder ");
		sql.append("from dt_wfj_bonuspoints_detail pd left join dt_wfj_bonuspoints bp on pd.bonuspointsid = bp.bonuspointsid ");
		sql.append("left join dt_wfj_guize wg on pd.wfjguizeid = wg.wfj_guize_id ");
		sql.append("left join dtcashierbills cb on cb.cashierbillsid = pd.wfjcashid ");
		sql.append("left join dt_OperatorInfo oi on cb.jnumorder = oi.journalnum and oi.status = ?");
		sql.append("left join dt_hr_staff hs on oi.staffid = hs.staffid ");
		sql.append("left join dt_productpackaging pp on pp.ppid = wg.wfj_guize_ppid ");
		sql.append("where bp.sccid = ? ");
		para.add("01");
		para.add(sccid);
		if (sdate != null && !"".equals(sdate) && edate != null
				&& !"".equals(edate)) {
			sql.append("and  pd.bonuspointsdetaildate between ? and ? ");
			para.add(Utilities.getDateFromString(sdate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			para.add(Utilities.getDateFromString(edate+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		}
		if (type != null && !"".equals(type) ) {
			sql.append("and  wg.wfj_guize_id = ? ");
			para.add(type);
		}
		if (inAndExp != null && !"".equals(inAndExp) ) {
			sql.append("and  wg.wfj_guize_calc = ? ");
			para.add(inAndExp);
		}
		if (operator != null && !"".equals(operator) ) {
			sql.append("and  oi.staffid = ? ");
			para.add(operator);
		}
		sql.append("order by pd.bonuspointsdetaildate desc ");
		 	    
		PageForm pageForm=baseBeanService.getPageFormBySQL(pageNumber, 5, sql.toString(), "select count(*) from ("+sql.toString()+")", para.toArray());
		if("all".equals(schType) && pageForm!=null){
			pageForm=baseBeanService.getPageFormBySQL(1, pageForm.getRecordCount(), sql.toString(), "select count(*) from ("+sql.toString()+")", para.toArray());
		}
		
//		StringBuffer sSql = new StringBuffer();
//		List<Object> sList = new ArrayList<Object>();
//		sSql.append("select sign.bonuspointsdetailid,cp.companyname ");
//		sSql.append("from dt_wfj_sign sign ");
//		sSql.append("left join dtcompany cp on sign.companyid = cp.companyid ");
//		sSql.append("where sign.bonuspointsdetailid in( ");
//		if(pageForm != null && pageForm.getList() != null)
//		{
//			for(int i = 0 ; i <pageForm.getList().size() ; i++)
//			{
//				Object object = pageForm.getList().get(i);
//				Object[] obj = (Object[])object;
//				sSql.append("?,");
//				sList.add(obj[3]);
//			}
//			String signSql = sSql.toString();
//			signSql = sSql.substring(0,sSql.length() - 1 ) + ")";
//			signList = baseBeanService.getListBeanBySqlAndParams(signSql,sList.toArray());
//		}
		map.put("pageForm", pageForm);
//		map.put("signList", signList);
		return pageForm;
	}

	/**
	 * 积分充值
	 * @param comid 公司id
	 * @param jum 订单编号
	 * @param staffid 付款人id
	 * @param sccid 账号id
	 * @param money 购买金币金额
	 * @param appstyle 支付方式
	 * @param trade_no 第三方交易号
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buyBonusPoints(String comid, String jum, String staffid, String sccid, 
			String money, String appstyle,String trade_no) {
		try {
			logger.error("开始调用-积分充值-存储过程");
			bonusPointsDao.buyBonusPoints(comid, jum, staffid, sccid, money, appstyle, trade_no);	
			logger.error("结束调用-积分充值-存储过程");
			
			StringBuilder sBuilder=new StringBuilder();
			sBuilder.append("select ps.ppname,ps.ppid,ps.re_price,p.model,p.goodsid ");
			sBuilder.append("from DT_PRO_SETUP ps left join dt_ProductPackaging p on p.ppid = ps.ppid ");
			sBuilder.append("where ps.re_price > 1 and ");
			sBuilder.append("p.type = ? and p.model >? and model <? ");
			sBuilder.append("and p.showweixin = ? and ps.com_id = ? order by p.model,to_number(ps.re_price)");
			List<Object> vlaList=baseBeanService.getListBeanBySqlAndParams(sBuilder.toString(), new Object[]{"个人会员","1","6","01","company201009046vxdyzy4wg0000000025"});
		
			TEshopCusCom t=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
			List<BaseBean> backList = new ArrayList<BaseBean>();
			if(vlaList!=null&&vlaList.size()>0){
				for(int i=0;i<=vlaList.size();i++){
					Object valobj=vlaList.get(i);
					if(valobj!=null){
						Object [] val=(Object [])valobj;
						if(Integer.parseInt(val[3].toString())<5&&Integer.parseInt(val[3].toString())<Integer.parseInt(t.getCusType())){
							if(Float.parseFloat(money)>=Float.parseFloat(val[2].toString())){
								backList=gOrderService.upgradeServece(t, val[3].toString());
								break;
							}
						}
					}
				}
			}
			if(backList!=null&&backList.size()>0){
				baseBeanService.executeHqlsByParamsList(backList, null, null);
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
	}


	/**
	 * 活动
	 * @param ccompanyId  发起活动的往来公司id
	 * @param isflag 辨识是安卓端过来的还是网页端
	 * @return 字符串 flag 返回是  no 该公司没有活动 
	 *         notEnough 积分不够  ；  without 该公司没有积分；notSet 没有设置签到积分； 
	 *         sign 有积分，有活动 （可以进行签到活动）；end 时间活动结束;no exist 该公司不存在 
	 */
	@Override
	@Transactional 
	public String getPrizeActivity(String ccompanyId,String isflag,String companyId) {
		String flag = "";
		if(companyId == null)
		{
			CcomCom com = null;
			if(ccompanyId != null){
				//往来公司表
				com = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId=?", new Object[]{ccompanyId}); 
			}
			if(com == null )
			{
				return "no exist";
			}
			else
			{
				companyId = com.getComanyId();
			}
		}
		String activityRange = "";
		if(isflag == null )
		{
			activityRange = "0";
		}
		else
		{
			activityRange = "1";
		}
		//判断该公司都有有活动 activityType 1： 签到活动 ；   status 0 ：活动发布 ；activityRange 0:全网发布，1会员
	    String hql = "from PrizeActivityBean where trim(activityType)=? and companyId=? and trim(status)=? and trim(activityRange)=?";				
		PrizeActivityBean prizeActivityBean = (PrizeActivityBean)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"1",companyId,"0",activityRange});
		logger.error("公司ID:"+companyId);  
		if(prizeActivityBean == null) 
		{
			flag = "no";
		}else
		{
			//获取当前系统时间和  活动表中的开始时间和结束时间比较
			//设置日期格式
			String time = DateUtil.getCurrentDate();//系统时间
			String start = DateUtil.toPaseDate(DateUtil.C_TIME_PATTON_DEFAULT,prizeActivityBean.getStartingTime());//活动开始时间
			String end = DateUtil.toPaseDate(DateUtil.C_TIME_PATTON_DEFAULT,prizeActivityBean.getEndTime());//活动结束时间

			Date date=Utilities.getDateFromString(time,"yyyy-MM-dd HH:ss:mm");
			Date startingTime = Utilities.getDateFromString(start,"yyyy-MM-dd HH:ss:mm");
			Date endTime = Utilities.getDateFromString(end,"yyyy-MM-dd HH:ss:mm");
			
			if(date.compareTo(startingTime)>= 0 && date.compareTo(endTime) <= 0)
			{
				//判断该公司是否有积分
				TEshopCusCom cc = (TEshopCusCom)baseBeanService
						.getBeanByHqlAndParams("from TEshopCusCom where companyId=?", new Object[]{companyId});
				BonusPoints bp = (BonusPoints)baseBeanService
						.getBeanByHqlAndParams("from BonusPoints where sccid= ? ", new Object[]{cc.getSccId()});
				
				if(bp == null )
				{
					flag = "without";//没有积分
				}else
				{	
					SignScoreBean ssb = (SignScoreBean)baseBeanService
							.getBeanByHqlAndParams("from SignScoreBean where activityId = ?"
									, new Object[]{prizeActivityBean.getActivityId()});
					if(ssb == null)
					{
						flag = "notSet";//该公司没有设置签到积分数
					}else
					{
						BigDecimal signScore = new BigDecimal(ssb.getScore());//签到赠送积分数
						BigDecimal score = new BigDecimal(bp.getBonusPointScore());//该公司总积分数
						if(signScore.compareTo(score) >0 )
						{
							flag = "notEnough";//说明积分不够
						}else
						{
							flag = "sign";//可以进行签到活动
						}
					}																		
				}									
			}else
			{
				flag = "end";//活动结束
			}				
		}
		return flag;
	}


	/**
	 * 跳转签到页面
	 * @param companyId  发起活动的公司id
	 * @return 字符串 toSign  yes 说明签到过了  no  说明还没有签到
	 */
	@Override
	@Transactional
	public String getSign(String companyId,String account) {
		String toSign = "";
		//根据时间查询用户是否签到（一个帐号只能在一个公司签到）
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(new Date());
        StringBuffer sql = new StringBuffer();
        List<Object> obj = new ArrayList<Object>();       
  		sql.append("select * from DT_WFJ_SIGN sign ");		
  		sql.append("where sign.companyId=? ");
  		sql.append("and sign.account=? ");
  		sql.append("and sign.signdate between ? and ?");
  		obj.add(companyId);
  		obj.add(account);
  		
  		obj.add(Utilities.getDateFromString(time+" 00:00:00","yyyy-MM-dd HH:ss:mm"));
  		obj.add(Utilities.getDateFromString(time+" 23:59:59", "yyyy-MM-dd HH:ss:mm"));								
  		@SuppressWarnings("unchecked")
  		List<Object> list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), obj.toArray());
  		if(list != null && list.size() > 0)
  		{//说明该用户签到过了
  			toSign = "yes"; 
  		}
  		else
  		{
  			toSign = "no";
  		}       		
		return toSign;
	}

	/**
	 * 判断签到次数
	 * @param companyId
	 * @param account
	 * @return
	 */
	public Map<String, Object> isPhoneSignCount(String companyId, String account)
	{
		Map<String, Object> map = new HashMap();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String time = df.format(new Date());
		StringBuffer sql = new StringBuffer();
		List<Object> obj = new ArrayList();
		sql.append("select count(*) from DT_WFJ_SIGN sign ");
		sql.append(" where sign.bonuspointsid is not null and sign.signsite is not null ");
		sql.append(" and sign.companyId=? and sign.account=? ");
		sql.append(" and sign.signdate between ? and ?");
		obj.add(companyId);
		obj.add(account);
		obj.add(Utilities.getDateFromString(time + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
		obj.add(Utilities.getDateFromString(time + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
		BigDecimal number = (BigDecimal)this.baseBeanService.getObjectBySqlAndParams(sql.toString(), obj.toArray());
		map.put("alreadySignNum", number);
		List<Object> param = new ArrayList();
		param.add(companyId);
		param.add("1");
		param.add("0");
		PrizeActivityBean pab = (PrizeActivityBean)this.baseBeanService
				.getBeanByHqlAndParams("from PrizeActivityBean where companyId=? and trim(showStatus)=? and trim(status)=? ",
						param.toArray());
		String ss = "yes";
		if (pab != null)
		{
			SignScoreBean ssb = (SignScoreBean)this.baseBeanService
					.getBeanByHqlAndParams("from SignScoreBean where activityId=?", new Object[] { pab.getActivityId() });
			if (ssb != null)
			{
				BigDecimal count = new BigDecimal(ssb.getCount().intValue());
				map.put("count", count);
				if (number.compareTo(count) >= 0) {
					ss = "no";
				}
			}
			else
			{
				ss = "no";
			}
		}
		else
		{
			ss = "no";
		}
		map.put("signFlag", ss);
		return map;
	}

	/**
	 * 手机签到个人签到详情
	 * @param sccid
	 * @param account
	 * @param companyId
	 * @param pageNumber
	 * @return
	 */
	public PageForm signPerDetail(String sccid, String account, String companyId, int pageNumber)
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.signdate,u.signsite,u.signinformation,u.signimagepath,u.signtype,hr.staffname,hr.photo");
		sql.append(" from dt_hr_staff hr,t_Eshop_Cuscom ec,( ");
		sql.append(" select s.signdate,s.signsite,s.signinformation,s.signimagepath,s.sccid,s.signtype from dt_wfj_sign s ");
		sql.append(" where s.sccid = ? and s.account = ? and s.companyid = ? and s.signsite is not null ");
		sql.append(" ) u");
		sql.append(" where u.sccid = ec.sccid and hr.staffid = ec.staffid order by u.signdate desc");
		PageForm pageForm = this.baseBeanService
				.getPageFormBySQL(pageNumber, 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", new Object[] { sccid, account, companyId });

		return pageForm;
	}
	/**
	 * 手机签到购买公司所有人签到详情
	 * @param companyId
	 * @param pageNumber
	 * @return
	 */
	public PageForm signComDetail(String companyId, int pageNumber)
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" select u.signdate,u.signsite,u.signinformation,u.signimagepath,u.signtype,hr.staffname,hr.photo");
		sql.append(" from dt_hr_staff hr,t_Eshop_Cuscom ec,(");
		sql.append(" select s.signdate,s.signsite,s.signinformation,s.signimagepath,s.sccid,s.signtype from dt_wfj_sign s");
		sql.append(" where s.companyid = ? and s.signsite is not null) u");
		sql.append(" where u.sccid = ec.sccid and hr.staffid = ec.staffid order by u.signdate desc");
		PageForm pageForm = this.baseBeanService
				.getPageFormBySQL(pageNumber, 10, sql.toString(), "select count(*) from (" + sql.toString() + ")", new Object[] { companyId });

		return pageForm;
	}

	/**
	 * 分享之前保存手机签到信息
	 * @param sccid 签到人sccid
	 * @param account 签到人account
	 * @param companyId  被签到公司ID
	 * @param nums 积分数
	 * @param signSite 签到地址
	 * @param signInfo 签到信息
	 * @param signImagePath 签到发表图片路径
	 * @return
	 */
	@Transactional
	public Map<String, Object> saveSign(String sccid, String account, String companyId, int nums, String signSite, String signInfo, String signImagePath)
	{
		Map<String, Object> map = new HashMap();
		String flag = "";
		try
		{
			List<BaseBean> beans = new ArrayList();
//			this.beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			Sign sign = new Sign();
			sign.setSignId(this.serverService.getServerID("Sign"));
			sign.setSccid(sccid);
			sign.setAccount(account);
			sign.setCompanyId(companyId);
			sign.setSignScore(nums+"");
			sign.setSignDate(new Date());
			sign.setSignSite(signSite);
			if("pos".equals(signImagePath)){
				sign.setSignType("01");
			}else{
				sign.setSignType("00");
			}
			if ((signInfo != null) && (!"".equals(signInfo))) {
				sign.setSignInformation(signInfo);
			}
			if ((signImagePath != null) && (!"".equals(signImagePath))) {
				sign.setSignImagePath(signImagePath);
			}
			//禁用手机打卡
			if("00".equals(sign.getSignType())){
				flag = "签到失败";
			}else{
				beans.add(sign);
				this.beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
				flag = "签到成功";
			}
			String signId = sign.getSignId();
			map.put("signId", signId);
			map.put("signDate", Utilities.getDateString(sign.getSignDate(), "yyyy-MM-dd HH:mm:ss"));
		}
		catch (Exception e)
		{
			logger.error("操作异常", e);
			flag = "操作失败";
		}
		map.put("flag", flag);
		return map;
	}
	

	/**
	 * 
	 * 根据编号获取当前公司信息
	 * @param posNum
	 * @return
	 */
	public  Company getCompanyByPosNum(String posNum){
		
		String hql = "from Company c where c.companyID = (select cc.comanyId from CcomCom cc where cc.ccompanyId = (select p.accessCcomID from  PosDevice p where p.posNum = ?))";
		Company company = (Company)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{posNum});
		
		return company;
	}
	
	
	/**
	 * mz
	 * 验证该手机号是否入职过公司
	 * @param sccid
	 * @param account
	 * @return
	 */
	public String  toSignValidate(String sccid,String account,String companyID){
		
		List<BaseBean> list = baseBeanService
				.getListBeanByHqlAndParams(
						"from CAccount t where t.staffID in(select f.staffID from Staff f where f.sccid = ? or f.reference = ?) and companyID = ? and accountStatus = ?",
						new Object[] { sccid,account,companyID,"00" });
		
		if(list.size()>0){//入职了
			return "1";
		}else{
			
			return "0";//没入职
		}
		
	}
	/**
	 * 
	 * 
	 * @param openid
	 * @return
	 */
	@Transactional
	public Map<String,Object> faceValidate(String openid,String companyId){
		Map<String,Object> map = new HashMap<String,Object>();
       String hql = "from TEshopCusCom where openId = ?";
		TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{openid});
		if(tc==null){
			map.put("result","3");//没有绑定手机号
		   //没有账号，就要提示绑定手机号
		}else if(tc.getAccount()==null||tc.getAccount().length()>11){
			map.put("result","2");//有账号，没关联手机号，绑定手机号
		}else{
			//有账号验证是否入职过该公司
			String result = toSignValidate(tc.getSccId(),tc.getAccount(),companyId);
			map.put("result", result);
			if("1".equals(result)){//刷脸后验证有账号也入职了正常签到
				Map<String, Object> mp =  saveSign(tc.getSccId(), tc.getAccount(), companyId, 0,
						null, null, "pos");
				map.put("signDate", (String)mp.get("signDate"));
				Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{tc.getStaffid()});
				map.put("staffName", staff.getStaffName());
				
				//查询次数
				String hqlsign = "from Sign where companyId = ? and sccid = ? and signDate  between ? and ?";
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String time = df.format(new Date());
				Date start = Utilities.getDateFromString(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
				Date end = Utilities.getDateFromString(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
				List<BaseBean> signlist = baseBeanService.getListBeanByHqlAndParams(hqlsign, new Object[]{companyId,tc.getSccId(),start,end});
				map.put("signCount",signlist.size());
			}
		}
		
		return map;
	}
	

	/**
	 * 绑定微分金账号
	 * @param openid
	 * @param tel
	 * @return
	 */
	@Transactional
	public Map<String,Object> bindWfj(String openid,String tel,String companyId){
		Map<String,Object> map = new HashMap<String,Object>();
		List<BaseBean> beans = new ArrayList<BaseBean>();
		TEshopCusCom tc = null;
		
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and cusType!='0' order by cusType", new Object[]{tel});
		if(list.size()>0){
			tc= (TEshopCusCom)list.get(0);
		}else{
			map.put("result","0");//此手机号没有注册微分金
		}
		
		
		if(tc!=null){
			List<BaseBean> accountlist = baseBeanService
					.getListBeanByHqlAndParams(
							"from CAccount t where t.staffID in(select f.staffID from Staff f where f.sccid = ? or f.reference = ?) and companyID = ? and accountStatus = ?",
							new Object[] { tc.getSccId(),tel,companyId,"00" });
			
			if(accountlist.size()>0){
				TEshopCusCom wxtc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where openId = ?",new Object[]{openid});
				TEshopCustomer wxtr = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where openId = ?",new Object[]{openid});

				TEshopCusCom teltc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = '01'",new Object[]{tel});
				TEshopCustomer teltr = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0",new Object[]{tel});
				int bd = 0;
				if(teltc!=null&&teltc.getOpenId()!=null){
					map.put("result","1");//该手机账号已绑定微信，不能重复绑定
				}else if(wxtc==null){
					map.put("result","3");//绑定成功
                         teltc.setOpenId(openid);
                         teltr.setOpenId(openid);
					beans.add(teltc);
					beans.add(teltr);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null,null);
					bd = 1;
					
				 }else{

                     //有2个账号
					 teltc.setOpenId(openid);
					 teltr.setOpenId(openid);
					if (wxtc != null&&wxtc.getAccount()!=null&&wxtc.getAccount().length()!=11) {
						wxtc.setOpenId(openid + tel);
						wxtc.setAccount(openid + tel);
						wxtr.setOpenId(openid + tel);
						wxtr.setAccount(openid + tel);
                        beans.add(wxtr);
                        beans.add(wxtc);
					}
					map.put("result","3");//绑定成功
					beans.add(teltr);
					beans.add(teltc);

					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null,null);
					bd = 1;

				}
				if(bd==1){
					Map<String, Object> mp =  saveSign(tc.getSccId(), tc.getAccount(), companyId, 0,
							null, null, "pos");
					map.put("signDate", (String)mp.get("signDate"));
					Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{tc.getStaffid()});
					map.put("staffName", staff.getStaffName());
					
					//查询次数
					String hqlsign = "from Sign where companyId = ? and sccid = ? and signDate  between ? and ?";
					
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String time = df.format(new Date());
					Date start = Utilities.getDateFromString(time + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
					Date end = Utilities.getDateFromString(time + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
					List<BaseBean> signlist = baseBeanService.getListBeanByHqlAndParams(hqlsign, new Object[]{companyId,tc.getSccId(),start,end});
					map.put("signCount",signlist.size());
				}
				
				
			}else{
				map.put("result","2");//没入职该公司不给绑定
				
			}
			
	

				

			
		}
		return map;
	}
		
}
