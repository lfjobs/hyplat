package hy.ea.finance.action.BenDis;

import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProSetup;

import hy.ea.bo.finance.BenDis.ConsigneeSheet;
import hy.ea.bo.finance.BenDis.DtMemberBackup;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.BenDis.PhoneBill;
import hy.ea.bo.finance.BenDis.RefundSheet;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.finance.service.transferService;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.front.WfjEshopProductAction;
import com.tiantai.wfj.util.SessionWrap;

/**
 * 手机页面订单管理
 * 
 * @author lf
 * 
 */
public class hy_phoneBillAction {
	private static final String String = null;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private transferService trService;
	@Resource
	private ServerService serverService;
	@Autowired
	private MobileMessage msg;// 发短信zzl
	private Logger logger = LoggerFactory.getLogger(WfjEshopProductAction.class);
	private int pageNumber;
	private PageForm pageForm;

	private RefundSheet refundSheet;
	private CashierBills cashierBills;

	private String ppid;
	private String id;
	private String status;
	private String staid;
	private String result;
	private String pl;//8.24

	/*
	 * http://localhost:8080/hyplat/ea/hypb/ea_getcomporder.jspa?staid=
	 * cstaff20151104IF8I49HSDR0000000001 查询订单（我的订单、其他会员订单、批量发货）
	 */

	public String getcomporder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pl = request.getParameter("pl");
		String staid = request.getParameter("staid");
		request.setAttribute("pl", pl);
		request.setAttribute("staid", staid);

		pageForm = getList(staid, pl);
		return "list";
	}

	public String getAjax() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pl = request.getParameter("pl");
		String staid = request.getParameter("staid");
		request.setAttribute("pl", pl);
		request.setAttribute("staid", staid);
		pageForm = getList(staid, pl);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 单据列表
	 * 
	 * @param staid
	 *            公司id
	 * @param pl
	 *            单据跟踪状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageForm getList(String staid, String pl) {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ljly = request.getParameter("ljly");
		request.setAttribute("ljly", ljly);
		DetachedCriteria dc = DetachedCriteria.forClass(PhoneBill.class);
		dc.add(Restrictions.eq("contactUserID", staid));
		if (pl != null && !pl.equals("")) { 
			dc.add(Restrictions.eq("fkStatus", pl));
		}
		dc.addOrder(Order.desc("cashierdate"));
		try {
			pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1), 6, dc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Object[]> goodlist = null;
		// 0:id 1:key 2:数量 3:单价 4:产品编号 5:产品名称 6:产品图片 7:产品id 8:供应商id
		StringBuffer sql=new StringBuffer();
		StringBuffer ptsql=new StringBuffer();
		String goodhql="";
		String psql="";
		sql.append("select g.goodsbillsid,g.goodsbillskey,");
		sql.append("g.quantity,g.price,g.goodsnum,g.goodsname,p.image,g.ppid,c.companyid,c.companyname,g.cashierbillsid,g.standard,c.jnumorder  ");
		sql.append(" from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
		sql.append(" where g.ppid=p.ppid and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid in(");
		
		ptsql.append("select DISTINCT g.goodsbillsid,g.goodsbillskey,g.quantity,g.price,g.goodsnum,");
		ptsql.append(" g.goodsname,p.image,g.ppid,g.companyid,g.cashierbillsid,g.standard,pa.cashierbillsid as ptcashid,c.fkStatus,c.jnumorder ");
        ptsql.append(" from dtgoodsbills g, dt_productpackaging p, dtcashierbills c,dt_promotionassociation pa");
        ptsql.append(" where g.ppid = p.ppid");
        ptsql.append(" and c.cashierbillsid = g.cashierbillsid");
        ptsql.append(" and c.cashierbillsid = pa.ptcashierbillsid");
        ptsql.append(" and pa.cashierbillsid in(");
		
		List<Object> parmsforbillId = new ArrayList<Object>();
		
		//促销订单ljc
		List<Object[]> ptlist=null;

		if (pageForm != null && pageForm.getList() != null) {
			for (int i = 0; i < pageForm.getList().size(); i++) {
				PhoneBill pb = (PhoneBill) pageForm.getList().get(i);
				sql.append("?,");
				ptsql.append("?,");
				parmsforbillId.add(pb.getCashierBillsID());
			}
			goodhql=sql.toString();
			goodhql = sql.substring(0, sql.length() - 1) + ")";
			
			psql=ptsql.toString();
			psql = ptsql.substring(0, ptsql.toString().length() - 1) + ")";
			goodlist = baseBeanService.getListBeanBySqlAndParams(goodhql.toString(),
					parmsforbillId.toArray());
			//促销品
			ptlist=baseBeanService.getListBeanBySqlAndParams(psql, parmsforbillId.toArray());
			List<Object> parmsforpcomId = new ArrayList<Object>();
			// List<Object[]> goodentity=new ArrayList<Object[]>();
			// List<Object[]> yjenitty=new ArrayList<Object[]>();
			String pcomhql = "from Company where companyID in(";
			for (Object[] goodObj : goodlist) {
				pcomhql += "?,";
				parmsforpcomId.add(goodObj[8]);
			}

			pcomhql = pcomhql.substring(0, pcomhql.length() - 1) + ")";
			List<BaseBean> pcom = baseBeanService.getListBeanByHqlAndParams(
					pcomhql, parmsforpcomId.toArray());
			
			// 物品填到pageform
			List<Object[]> glist = null;
			List<Object[]> plist=null;
			for (int i = 0; i < pageForm.getList().size(); i++) {
				PhoneBill pb = (PhoneBill) pageForm.getList().get(i);
				glist = new ArrayList<Object[]>();
				for (Object[] obj : goodlist) {
					if (pb.getCashierBillsID().equals(obj[10].toString())) {
						glist.add(obj);
					}
				}
				pb.setGoodsList(glist);
				pageForm.getList().set(i, pb);
			}
		
			//ljc
			for (int i = 0; i < pageForm.getList().size(); i++) {
				PhoneBill pb = (PhoneBill) pageForm.getList().get(i);
				plist = new ArrayList<Object[]>();
				for (Object[] obj : ptlist) {
					if (pb.getCashierBillsID().equals(obj[11].toString())) {
						plist.add(obj);
					}
				}
				pb.setPtgoodsList(plist);
				pageForm.getList().set(i, pb);
			}
		}
		return pageForm;
	}
	
	/**
	 * 删除订单
	 * @return
	 */

	public String delBill() {
		
		List<Object[]> objs=new ArrayList<Object[]>();		
		String hql = "from CashierBills where cashierBillsID = ? ";
		CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{id});
						
		String[] hqls={"delete from GoodsBills where cashierBillsID = ?",
						"delete from CashierBills where cashierBillsID = ?",
						"delete from DtOrderBillAdd where oaBillId = ?",
						"delete from PayCashierBill where  payJournalNum= ?"};		
		objs.add(new Object[]{id});
		objs.add(new Object[]{id});
		objs.add(new Object[]{id});
		objs.add(new Object[]{cashierBills.getJournalNum()});
		
		baseBeanService.executeHqlsByParamsList(null,hqls,objs);
		return getcomporder();
	}
	
	

	/**
	 * 单据详情
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getCashBill() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// String sccid = request.getParameter("sccid");
		String pl = request.getParameter("pl");
		String staid = request.getParameter("staid");
		request.setAttribute("pl", pl);
		request.setAttribute("staid", staid);

		PhoneBill pb = (PhoneBill) baseBeanService
				.getBeanByHqlAndParams("from PhoneBill where cashierBillsID=?",
						new Object[] { staid });
		// pb.getFkStatus();

		List<BaseBean> billList = baseBeanService.getListBeanByHqlAndParams(
				"from CashierBills where jNumOrder=?",
				new Object[] { pb.getJournalNum() });
		
		/*	Object[] objs = null;
			Object obj = billList.get(0);
			objs = (Object[]) obj;
			Object jour = objs [59];
			request.setAttribute("pb", jour);
		*/
		ConsigneeSheet consign = (ConsigneeSheet) baseBeanService
				.getBeanByHqlAndParams("from ConsigneeSheet where orderCode=?",
						new Object[] { pb.getJournalNum() });				
		if (billList.size() > 0) {
			Map<String, Date> dateMap = new HashMap<String, Date>();
			for (int i = 0; i < billList.size(); i++) {
				CashierBills c = (CashierBills) billList.get(i);
				if (c.getBillsType().equals("收款单")) {
					dateMap.put("付款时间", c.getCashierDate());
				} else if (c.getBillsType().equals("收款单")
						&& c.getProjectName().equals("供应商成本")) {
					dateMap.put("发货时间", c.getCashierDate());
				} else if (c.getBillsType().equals("收款单")) {
					dateMap.put("发货时间", c.getCashierDate());
				} else if (consign != null) {
					dateMap.put("成交时间", consign.getSendDate());
				}else if(c.getBillsType().equals("销售出库单")){
					Calendar calendar = Calendar.getInstance();   
				    calendar.setTime(c.getCashierDate()); 
				    calendar.add(Calendar.DATE, 15+Integer.parseInt(pb.getExtendedTime()==null?"0":pb.getExtendedTime()));
				    int ir=new Date().compareTo(calendar.getTime());					
			    	request.setAttribute("deliverDate",ir);
				}
			}
			request.setAttribute("dateMap", dateMap);
		}
		/***
		 * 0:id 1:key 2:数量 3:单价 4:产品编号 5:产品名称 6:产品图片 7:产品id 8:供应商id 9:供货商名称
		 * 10:物品id
		 ***/
		StringBuffer goodhql=new StringBuffer();
		goodhql.append("select g.goodsbillsid,g.goodsbillskey,"); 
		goodhql.append("g.quantity,g.price,g.goodsnum,g.goodsname,p.image,g.ppid,c.ccompanyid,c.ccompanyname,g.goodsID,g.standard,g.companyid ,c.journalnum,c.projectName");
		goodhql.append(" from dtgoodsbills g,dt_productpackaging p,dtcashierbills c");
		goodhql.append(" where g.ppid=p.ppid and g.cashierbillsid=c.cashierbillsid and g.cashierbillsid=?");
		List<Object[]> goodlist = baseBeanService.getListBeanBySqlAndParams(
				goodhql.toString(), new Object[] { pb.getCashierBillsID() });
		pb.setGoodsList(goodlist);
		request.setAttribute("pb", pb);
		//促销品ljc
		goodhql.delete(0, goodhql.length());
		goodhql.append("select DISTINCT g.goodsbillsid,g.goodsbillskey,g.quantity,g.price,g.goodsnum,");
		goodhql.append(" g.goodsname,p.image,g.ppid,g.goodsID,g.companyid,g.cashierbillsid,g.standard,pa.cashierbillsid as ptcashid,c.fkStatus");
		goodhql.append(" from dtgoodsbills g, dt_productpackaging p, dtcashierbills c,dt_promotionassociation pa");
		goodhql.append(" where g.ppid = p.ppid");
		goodhql.append(" and c.cashierbillsid = g.cashierbillsid");
		goodhql.append(" and c.cashierbillsid = pa.ptcashierbillsid");
		goodhql.append(" and pa.cashierbillsid= ?");
		List<Object[]> list=baseBeanService.getListBeanBySqlAndParams(goodhql.toString(), new Object[]{pb.getCashierBillsID()});
		pb.setPtgoodsList(list);
		request.setAttribute("pt", pb);
		
		goodhql.delete(0, goodhql.length());
		goodhql.append(" select pp.ppid,pp.companyid,pp.goodsname,pp.quantity,pp.price,pp.image,pp.stocksize,pp.monthsales,");
		goodhql.append(" pp.standard,pp.shangjiastatus,pp.logo,pp.photo,pp.tradecode,pp.tradename,pp.tradeid,pp.brand,pp.goodsID");
		goodhql.append(" from dt_productpackaging pp where pp.goodsid in  ");
		goodhql.append("(select d.goodsid from dt_inv_invt d where  d.warehouse in (select d.depotid from dtDepotManage d where d.companyid=? and d.depotname = '销售库')) and ");
		goodhql.append("pp.yjstatus='01' and pp.showweixin='01' and pp.companyid= ? and rownum<7");

		List<BaseBean> cplist = baseBeanService.getListBeanBySqlAndParams(goodhql.toString(),
				new Object[] { pb.getCompanyid(), pb.getCompanyid() });
		request.setAttribute("cplist", cplist);
		String hql1 = " from CashierBills c where c.cashierBillsID=?";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql1, new Object[] { id });
		request.setAttribute("cashierBills", cashierBills);
		return "edit";
	}

	/**
	 * 买家查看退货地址详情
	 * 
	 */
	public String refundMobileDetails() {
		String hql = " from RefundSheet t where t.cashierBillsID=? and t.ppid=?";
		refundSheet = (RefundSheet) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { id, ppid });
		String hql1 = " from CashierBills c where c.cashierBillsID=? ";
		cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql1, new Object[] { id });
		status = cashierBills.getFkStatus();
		return "refundDetails";
	}

	/**
	 * 用户确认收货
	 * 
	 * @return
	 */
	public synchronized String userConfirmationReceipt() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
				SessionWrap.KEY_CUSTOMER);
		String staid = request.getParameter("staid");
		String id = request.getParameter("id");
		request.setAttribute("staid", staid);
		String s=trService.recognitionHarvest(id);
		// TEshopCusCom
		// cusCom=(TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where staffid=?",
		// new Object[]{staid});
		if (cus != null&&"操作成功".equals(s)) {
			request.setAttribute("user", cus.getAccount());
		}
		return "sh";
	}

	// http://localhost:8080/hyplat/ea/hypb/ea_saveorupdate.jspa
	public void saveorupdate() {
		Object[][] object = {
				{ "15308382225", "13208156128", "13183941666", "13882952428",
						"15108155718", "15378235066", "18081688760",
						"13982963339", "15308380760", "15308382217",
						"18080566816", "15928359352" },
				{ "13882988299", "13629011135", "18982992586", "15308382215",
						"15308382229", "13550692798", "13708260266",
						"13882968318", "13982967031", "13795733111",
						"13980388988", "13508046058" },
				{ "15883247398", "18980385596", "6633222", "13398318001",
						"15283229215", "13982968592", "15883210770",
						"18328238847", "18982945168", "13778498046",
						"13684129009", "13982960616" },
				{ "18982988882", "13550696158", "15108130250", "18608050898",
						"13064364111", "15680386665", "15308382219",
						"15528698829", "13684126548", "26195196",
						"13982939720", "13982996740" },
				{ "13982980456", "18398090797", "13550688539", "18628870725",
						"13708240245", "13550676434", "15308382227",
						"15884221947", "13982944180", "13684119769",
						"15183793369", "18628848886", "13568536182" },
				{ "18982962623", "15283216189", "13688379325", "13688274887",
						"15775122772", "15183232150", "15308382216",
						"13882958828", "18708217901" },
				{ "15378235073", "13881109882", "13689688477", "13699616381",
						"15082151238", "13035671068", "13198090383",
						"15378235081", "15281101061", "15378235071",
						"15378235017", "13990112085", "15182416200" },
				{ "13689681028", "13550826299", "13990132806", "13398385495",
						"13458300761", "13108151629", "13547131616",
						"15284007982", "13990122998", "13689681818",
						"18981121423", "15182410173", "13548413931",
						"13778121001" },
				{ "1378124118", "13700969298", "15228335558", "13458041104",
						"18281635709", "13658134426", "18981180199",
						"13989278868", "13696284954", "13060178886",
						"15378235067", "18990192800", "18281530273",
						"15378235070" },
				{ "13696251696", "13890138248", "13990182923", "13689677555",
						"13908118361", "13890153717", "13990104473",
						"13990132369", "18981106028", "15378235079",
						"15378235077", "13183437056", "15378235078",
						"15378235076" },
				{ "13990187728", "15281618596", "13881182609", "18781176677",
						"13550824420", "18281965790", "18780566672",
						"15378235688", "13659025098", "13778097528",
						"13990110126", "13990107380", "13108108026",
						"13198098526" },
				{ "13882157622", "15108291012", "13880413111", "18980806189",
						"13438993862", "13981777558", "13683438965",
						"13618058310", "15982292810", "13668230968",
						"13558662431", "13438145598" },
				{ "13488909768", "13808010062", "13881970662", "18084828917",
						"13808041258", "13258300910", "13708011446",
						"13982299373", "18081150508", "13808009474",
						"13882189499", "15928708403", "13982022832" },
				{ "13882288148", "13708184529", "15828257186", "13558674250",
						"15982221118", "15982210485", "18980785137",
						"18982222493", "18980739880", "13540047614",
						"13699403328", "18980707860", "13032889587" },
				{ "18980680997", "18508222762", "13219008089", "13778991711",
						"13547073791", "18782151181", "13882082109",
						"15982474967", "13458537212", "18111241603",
						"13880117098" },
				{ "15884663989", "15378235075", "13550823240", "15883740161",
						"13395260158", "13331348866" },
				{ "18784043159", "15328111365", "15378235069", "15182488165",
						"15910666136", "18780401368", "15910666136" },
				{ "13881917530", "18615702867", "13739476900", "15984594985",
						"13659017382", "18615766331", "15181223873",
						"18382099658", "15214306645", "18282390826",
						"13551314428", "15850312140", "15680993004",
						"15208349965", "17608004998", "13959878196",
						"18382206986", "18349382588" },
				{ "18607145199", "18610856766", "18611966437", "13764608606",
						"15801151282", "15210904250", "15510522186",
						"15210819620", "18701367443" },
				{ "15308382235", "15282228658", "15928462206", "18382206986" },
				{ "18081688858", "13228244364", "18328365246" },
				{ "13981182586", "18780598999", "15883228779", "13550697336",
						"13882200694", "13910451047", "13708226199" },
				{ "13882969066", "18081688763", "15308382218", "13086680598" } };

		Object[] objects = { "15308382235", "13982986958", "13684118823",
				"15282228658", "15308382218", "18980388482", "18081688858",
				"13981182586", "18780598999", "13228244364", "13882969066",
				"15928462206", "13882200694", "18382206986", "18328365246",
				"13910451047", "13331348866", "13708226199", "13395260158",
				"18080891109", "18980036679", "15378235067", "18482269312" };
		List<BaseBean> bases = new ArrayList<BaseBean>();
		for (int i = 0; i < objects.length; i++) {
			String a = objects[i].toString();
			for (int j = 0; j < object[i].length; j++) {
				String b = object[i][j].toString();
				MarKeting keting = (MarKeting) baseBeanService
						.getBeanByHqlAndParams("from MarKeting where userID=?",
								new Object[] { b });
				System.out.println(i + "-" + j + "{被推荐人：" + b);
				if (keting == null) {
					keting = new MarKeting();
					System.out.println("修改");
					keting.setMkID(serverService.getServerID("MarKeting"));
					keting.setUserID(b);

				}
				System.out.println("推荐人：" + a + "}");
				keting.setMkuserID(a);
				keting.setMkdate(new Date());
				bases.add(keting);
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(bases, null, null);
	}

	// http://localhost:8080/hyplat/ea/hypb/ea_treatHistory.jspa
	@SuppressWarnings("unchecked")
	public void treatHistory() {

		String ss = "select o.oa_sccid from dt_order_bill_add o group by o.oa_sccid";
		List<BaseBean> b = baseBeanService.getListBeanBySqlAndParams(ss, null);

		String sql = "select c.cashierbillsid,c.journalnum,c.cashierdate,o.oa_sccid,c.fkstatus"
				+ " from dtcashierbills c,dt_order_bill_add o"
				+ " where o.oa_bill_id = c.cashierbillsid"
				+ " and c.billstype = '项目收入预算单'"
				+ " and c.statusbill = '04' and c.fkstatus!='01'"
				+ " and c.fkstatus!='09' and o.oa_sccid =? and c.projectname!='粉丝名片' order by c.cashierdate";

		String cumsql = "SELECT t.staffid, t.companyid, t.state,t.superioragent,"
				+ "t.pseudo_company_name,t.custype,t.sccid,t.account"
				+ " FROM T_ESHOP_CUSCOM t START WITH t.sccid = ?"
				+ " CONNECT BY PRIOR t.SUPERIORAGENT =  t.account";

		for (int i = 0; i < b.size(); i++) {
			List<String> hqls = new ArrayList<String>();
			List<Object[]> parmsList = new ArrayList<Object[]>();
			List<BaseBean> operator = new ArrayList<BaseBean>();
			TEshopCusCom t = (TEshopCusCom) baseBeanService
					.getBeanByHqlAndParams("from TEshopCusCom where sccId=?",
							new Object[] { b.get(i)});
			
			if(t!=null){
				List<Object[]> object = baseBeanService.getListBeanBySqlAndParams(
						sql, new Object[] { b.get(i)});
				if (object.size() > 0) {
					System.out.println(i + "-" + t.getAccount() + ":[");

					MarKeting keting = (MarKeting) baseBeanService
							.getBeanByHqlAndParams("from MarKeting where userID=?",
									new Object[] { t.getAccount() });
					
					if (keting != null) {
						// 推荐人
						TEshopCusCom t1 = (TEshopCusCom) baseBeanService
								.getBeanByHqlAndParams(
										"from TEshopCusCom t where t.account = ?",
										new Object[] { keting.getMkuserID() });
						// 被推荐人
						TEshopCusCom t2 = (TEshopCusCom) baseBeanService
								.getBeanByHqlAndParams(
										"from TEshopCusCom t where t.account = ?",
										new Object[] { keting.getUserID() });
						if (t1 != null) {
							if (Float.parseFloat(t1.getCusType()) < Float
									.parseFloat(t2.getCusType())) {
								List<Object[]> cusList = baseBeanService
										.getListBeanBySqlAndParams(cumsql,
												new Object[] { t2.getSccId() });
								if (cusList != null) {
									for (int j = 0; j < object.size(); j++) {
										Object obj = (Object) object.get(j);
										Object[] objects2 = (Object[]) obj;
										List<BaseBean> beans = baseBeanService
												.getListBeanByHqlAndParams(
														"from DtMemberBackup where cashId=?",
														new Object[] { objects2[0] });
										if (beans != null) {
											for (int k = 0; k < beans.size(); k++) {
												DtMemberBackup backup = (DtMemberBackup) beans
														.get(k);
												hqls.add("delete DtMemberBackup where mbId=?");
												parmsList.add(new Object[] { backup
														.getMbId() });
											}
										}

										//System.out.println("{" + objects2[1] + ","+ objects2[3] + "},");

										CashierBills cb = (CashierBills) baseBeanService
												.getBeanByHqlAndParams(
														"from CashierBills where cashierBillsID=?",
														new Object[] { objects2[0] });
										operator.addAll(addList(cusList, t1, cb, t2));
									}
								}
							} else if (Float.parseFloat(t1.getCusType()) >= Float
									.parseFloat(t2.getCusType())) {
								List<Object[]> cusList = baseBeanService
										.getListBeanBySqlAndParams(cumsql,
												new Object[] { t1.getSccId() });
								if (cusList != null) {
									for (int j = 0; j < object.size(); j++) {
										Object obj = (Object) object.get(j);
										Object[] objects2 = (Object[]) obj;

										List<BaseBean> beans = baseBeanService
												.getListBeanByHqlAndParams(
														"from DtMemberBackup where cashId=?",
														new Object[] { objects2[0] });
										if (beans != null) {
											for (int k = 0; k < beans.size(); k++) {
												DtMemberBackup backup = (DtMemberBackup) beans
														.get(k);
												hqls.add("delete DtMemberBackup where mbId=?");
												parmsList.add(new Object[] { backup
														.getMbId() });
											}
										}

										

										CashierBills cb = (CashierBills) baseBeanService
												.getBeanByHqlAndParams(
														"from CashierBills where cashierBillsID=?",
														new Object[] { objects2[0] });
										if (j == 0) {
											cusList = baseBeanService
													.getListBeanBySqlAndParams(
															cumsql,
															new Object[] { t1
																	.getSccId() });
											if(Float.parseFloat(t1.getCusType())<=5){
												Object[] o={t2.getStaffid(),t2.getCompanyId(),t2.getState(),t2.getSuperioragent(),t2.getPseudoCompanyName(),t2.getCusType(),t2.getSccId(),t2.getAccount()};
												cusList.add(0,o);
												operator.addAll(addList(cusList, t1,
														cb, t2));
											}else{
												operator.addAll(addList(cusList, t1,
														cb, t2));
											}
										} else {
											cusList = baseBeanService
													.getListBeanBySqlAndParams(
															cumsql,
															new Object[] { t2
																	.getSccId() });
											operator.addAll(addList(cusList, null,
													cb, t2));
										}
									}
								}
							}
						}
					}
				}
				String[] toBeStored = hqls.toArray(new String[hqls.size()]);
				baseBeanService.executeHqlsByParamsList(operator, toBeStored,
						parmsList);
			}
		}	
	}

	/**
	 * 
	 * @param baseList
	 *            要分钱的会员
	 * @param tEshopCusCom
	 * @param cb
	 *            订单
	 * @param scc
	 * @return
	 */
	private List<BaseBean> addList(List<Object[]> baseList,
			TEshopCusCom tEshopCusCom, CashierBills cb, TEshopCusCom scc) {
		String hql = "from Staff d where d.staffID=? ";
		List<BaseBean> operator = new ArrayList<BaseBean>();
		DtOrderBillAdd billAdd = (DtOrderBillAdd) baseBeanService
				.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?",
						new Object[] { cb.getCashierBillsID() });
		int a = 0;
		Boolean b = false;
		// if(!tEshopCusCom.getCusType().equals("0")&&!tEshopCusCom.getCusType().equals("0.5")&&!tEshopCusCom.getCusType().equals("1")){
		for (int i = 0; i < baseList.size(); i++) {
			// DtMemberBackup表关联ljc
			Object[] cuscom = baseList.get(i);
			if (i == 0) {
				billAdd.setYjid(cuscom[5].toString());
				operator.add(billAdd);
			}
			DtMemberBackup dmb = new DtMemberBackup();

			if (tEshopCusCom != null) {
				if (tEshopCusCom.getCusType().equals("0")) {
					if (cuscom[5].toString().equals("0")) {
						dmb.setJbbl(100 - (baseList.size() - 1) * 5 + "%");
					} else {
						dmb.setJbbl("5%");
					}
				} else {
					if (Float.parseFloat(baseList.get(1)[5].toString()) >= 2
							&& Float.parseFloat(baseList.get(1)[5].toString()) <= 5) {
						if (i == 2) {
							if (cuscom[5].toString().equals("1")) {
								dmb.setJbbl("5%");
								b = true;
							} else {
								dmb.setJbbl("10%");
							}
						} else if (i == 1) {
							dmb.setJbbl(100 - baseList.size() * 5 + "%");
						} else {
							if (b && cuscom[5].toString().equals("0")) {
								dmb.setJbbl("10%");
							} else {
								dmb.setJbbl("5%");
							}
						}
					}
				}
			} else {
				if (Float.parseFloat(baseList.get(1)[5].toString()) == 1) {
					if (cuscom[5].toString().equals("0")) {
						dmb.setJbbl("10%");
					} else if (i == 0) {
						dmb.setJbbl(100 - baseList.size() * 5 + "%");
					} else {
						dmb.setJbbl("5%");
					}
				}else{
					if (i == 1) {
						dmb.setJbbl("10%");
					} else if (i == 0) {
						dmb.setJbbl(100 - baseList.size() * 5 + "%");
					} else {
						dmb.setJbbl("5%");
					}
				}
				
			}
			System.out.println(cuscom[5].toString() + "--"
					+ cuscom[7].toString() + "-" + dmb.getJbbl());
			dmb.setMbId(serverService.getServerID("DtMemberBackup"));
			dmb.setCashId(cb.getCashierBillsID());
			dmb.setCashJum(cb.getJournalNum());
			dmb.setMType(cuscom[5].toString());
			dmb.setMZh(cuscom[7].toString());
			dmb.setMId(cuscom[6].toString());
			dmb.setStaffId(cuscom[0].toString());

			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
					new String[] { cuscom[0].toString() });
			if (staff != null) {
				dmb.setStaffName(staff.getStaffName());
			}
			staff = null;
			if (cuscom[1] != null && !cuscom[1].equals("")) {
				dmb.setComId(cuscom[1].toString());
			}
			dmb.setMStatus(cuscom[2].toString());
			if (cuscom[3] != null && !cuscom[3].equals("")) {
				dmb.setMbPid(cuscom[3].toString());
			}

			operator.add(dmb);
		}
		return operator;
	}

	/*
	 * 查询是否已延长收货
	 */
	public String QueryWhetherToExtendTheReceipt() {
		HttpServletRequest re = ServletActionContext.getRequest();
		String cashierbillsID = re.getParameter("cashierbillsID");
		DtOrderBillAdd order = (DtOrderBillAdd) baseBeanService
				.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?",
						new Object[] { cashierbillsID });
		Map<String, Object> map = new HashMap<String, Object>();
		if (!"".equals(order.getExtendedTime())
				&& order.getExtendedTime() != null) {
			map.put("status", "01");
		} else {
			CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
	    			"from CashierBills where cashierBillsID=?", new Object[]{cashierbillsID});
		    Calendar calendar = Calendar.getInstance();   
		    calendar.setTime(c.getCashierDate()); 
		    calendar.add(Calendar.DATE, 15+Integer.parseInt(order.getExtendedTime()==null?"0":order.getExtendedTime()));
	    	String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		    map.put("time", time);
			map.put("status", "00");
		}
	    JSONObject js=JSONObject.fromObject(map);
	    result=js.toString();
		return "success";  
	}

	public String extendedReceipt() {
		HttpServletRequest re = ServletActionContext.getRequest();
		String cashierbillsID = re.getParameter("cashierbillsID");
		String zhi = re.getParameter("zhi");

		DtOrderBillAdd order = (DtOrderBillAdd) baseBeanService
				.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?",
						new Object[] { cashierbillsID });
		order.setExtendedTime(zhi);
		baseBeanService.update(order);
		return "success";
	}

	/**
	 * 系统自动收货
	 * 
	 * @throws IOException
	 */
	public void automaticReceipt() throws IOException {
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
				"from PhoneBill where (fkStatus=?  or fkStatus=?) and cashierdate > ?",
				new Object[] { "02" ,"03",Utilities.getDateFromString("2016-02-29 23:59:59", "yyyy-MM-dd hh:mm:ss")});
		a: for (int i = 0; i < list.size(); i++) {
			PhoneBill phone = (PhoneBill) list.get(i);
			if("03".equals(phone.getFkStatus())){
				String str = trService.Distribution(
						phone.getCashierBillsID(),
						"cstaff20160325ZAUAJEU6JH6192643691");
				if (!"操作成功".equals(str)) {
					msg.setMobiles("15210904250");// 15210904250
					msg.setMessage("订单号为：" + phone.getJournalNum()
							+ "的订单，系统自动收货分配金币时出错，错误信息为：" + str);
					String reStr = msg.sendMsg();
					System.out.println(reStr);
					break a;
				}
			}else{
				List<BaseBean> ccList=baseBeanService.getListBeanByHqlAndParams(
						"from CashierBills where jNumOrder=? and billsType=?",new Object[]{phone.getJournalNum(),"销售出库单"});
				CashierBills cc=(CashierBills) ccList.get(0);
				Calendar c = Calendar.getInstance();
				int i1 = -15 - Integer.parseInt(phone.getExtendedTime() == null ? "0"
						: phone.getExtendedTime());
				c.add(Calendar.DATE, i1);
				Date dateToo = c.getTime();
				if (dateToo.compareTo(cc.getCashierDate()) >= 0) {
					GoodsBills goods = (GoodsBills) baseBeanService
							.getBeanByHqlAndParams(
									"from GoodsBills where cashierBillsID=?",
									new Object[] { phone.getCashierBillsID() });
					ProSetup setup = (ProSetup) baseBeanService
							.getBeanByHqlAndParams("from ProSetup where ppid=?",
									new Object[] { goods.getPpID() });
					String invHql = "from Inventory  where companyID=? and warehouseName=? and goodsName=? and goodstatus=?";
					Inventory inv = (Inventory) baseBeanService
							.getBeanByHqlAndParams(invHql, new Object[] {
									"company201009046vxdyzy4wg0000000025", "销售库",
									"微分金金币", "00" });
					double ii = Double.parseDouble(goods.getQuantity())
							* Double.parseDouble(setup.getBrokerage());
					if (Double.parseDouble(inv == null ? "0" : inv
							.getInvenQuantity()) < ii) {
						msg.setMobiles("15210904250");
						msg.setMessage("微分金金币库存不足，无法进行系统自动确认收货，请及时补充。");
						String reStr = msg.sendMsg();
						System.out.println(reStr);
						modifyState(list);
						break a;
					} else {
						try {
							trService.recognitionHarvest(phone.getCashierBillsID());
							/*String str = trService.Distribution(
									phone.getCashierBillsID(),
									"cstaff20160325ZAUAJEU6JH6192643691");
							if (!"操作成功".equals(str)) {
								msg.setMobiles("15210904250");// 15210904250
								msg.setMessage("订单号为：" + phone.getJournalNum()
										+ "的订单，系统自动收货分配金币时出错，错误信息为：" + str);
								String reStr = msg.sendMsg();
								System.out.println(reStr);
								modifyState(list);
								break a;
							}*/
						} catch (Exception e) {
							msg.setMobiles("15210904250");// 15210904250
							msg.setMessage("订单号为：" + phone.getJournalNum()
									+ "的订单，系统自动收货时出错，错误代码为：" + e.getClass().getName());
							String reStr = msg.sendMsg();
							System.out.println(reStr);
						}
					}
				}
			}
		}
		System.out.println("自动收货完成");
	}

	/**
	 * 修改订单状态
	 * 
	 * @param list
	 */
	public void modifyState(List<BaseBean> list) {
		List<Object[]> obj = new ArrayList<Object[]>();
		List<String> sqls = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			PhoneBill phone = (PhoneBill) list.get(i);
			Calendar c = Calendar.getInstance();
			int i1 = 0 - Integer.parseInt(phone.getExtendedTime() == null ? "0"
					: phone.getExtendedTime());
			c.add(Calendar.DATE, i1);
			Date dateToo = c.getTime();
			if (dateToo.compareTo(phone.getCashierdate()) >= 0) {
				sqls.add("update dtcashierbills set fkStatus=? where cashierbillsid=?");
				obj.add(new Object[] { "03", phone.getCashierBillsID() });
			}
		}
		String[] str = new String[sqls.size()];
		baseBeanService.executeSqlsByParmsList(null, sqls.toArray(str), obj);
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getPpid() {
		return ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RefundSheet getRefundSheet() {
		return refundSheet;
	}

	public void setRefundSheet(RefundSheet refundSheet) {
		this.refundSheet = refundSheet;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStaid() {
		return staid;
	}

	public void setStaid(String staid) {
		this.staid = staid;
	}

	public String getPl() {
		return pl;
	}

	public void setPl(String pl) {
		this.pl = pl;
	}

}
