package hy.ea.human.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.WfjGuize;
import com.tiantai.wfj.bo.WfjJifen;
import com.tiantai.wfj.bo.WfjJifenDetail;
import com.tiantai.wfj.bo.WfjTask;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.RelatedBill;
import hy.ea.bo.human.publicreceipts.Publicreceipts;
import hy.ea.bo.human.publicreceipts.PublicreceiptsChild;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.human.service.GoldTicketService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

@Service
@Transactional
public class GoldTicketServiceImpl implements GoldTicketService{

	@Resource
	private BaseBeanDao beandao;
	@Resource
	private ServerService idgec;
	@Resource
	private BaseBeanService baseBeanService;
	
	
	@Transactional	
	@Override
	public synchronized String fineGold(String prID) throws Exception {
		
		String str = "";
		
		String hql = " from Publicreceipts where prID = ? ";	
		Publicreceipts publicreceipts = (Publicreceipts)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{prID});
		
		String Hql = " from PublicreceiptsChild where prID = ? ";	
		PublicreceiptsChild publicreceiptsChild = (PublicreceiptsChild)baseBeanService.getBeanByHqlAndParams(Hql, new Object[]{prID});
			
		//获取公司责任人
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { publicreceipts.getCompanyID() });
							
		//获取当前登录用户信息(操作人)
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			
		/*//生成2个表单
		String comId = Constant.COMPAYN_ID;
		String comName = "北京天太世统科技有限公司";
		String comgn = "groupcompany20120523G3VR9PXHZD0000000021"; */
		
		String cdid1 = idgec.getServerID("CashierBills");//用户金币出库单
		String cdid2 = idgec.getServerID("CashierBills");//平台金币入库单
		logger.info("调试信息");
		logger.info("调试信息");
		//凭证号
		String pzid1 = idgec.getBillID(company.getCompanyID());//用户金币出库单
		String pzid2 = idgec.getBillID(company.getCompanyID());//平台金币入库单

		String fineCount = publicreceiptsChild.getFineCount();//金币数
		String fineMoney = (Float.parseFloat(fineCount)/100)+"";
		
		
		//查询产品 微分金金币(责任人所在的公司)
		String phql ="from ProductPackaging where goodsName =?";
		ProductPackaging gm= (ProductPackaging) beandao.getBeanByHqlAndParams(phql, new Object[]{"微分金金币"});
		
		List<BaseBean> beans=new ArrayList<BaseBean>();
		CashierBills cb=null;
		
		//用户金币出库单
		cb = new CashierBills();
		cb.setCashierBillsID(cdid1);
		cb.setCashierDate(new Date());//单据日期
		cb.setBillsType("金币出库单");//单据类型
		cb.setJournalNum(pzid1);//凭证号					
		cb.setStaffID(publicreceipts.getPrincipalID());//责任人id
		cb.setStaffName(publicreceipts.getPrincipal());//责任人name
		cb.setCompanyID(publicreceipts.getCompanyID());
		cb.setCompanyName(company.getCompanyName());
		cb.setGroupCompanySn(null);
		
		//制单人
		cb.setInputid(account.getStaffID());
		cb.setInputName(account.getStaffName());
		cb.setInputCompanyid(account.getCompanyID());
		cb.setInputCompanyName(account.getCompanyName());
		
		cb.setStatus("16"); //已出库
		
		/**************往来单位****************/		
		cb.setCcompanyID(null);          //往来单位ID (如果是订单这个字段存的是商户id)
		cb.setCcompanyName(null);        //往来单位name (如果是订单这个字段存的是（商户编号，商户名称）)
		cb.setCompanyAddr(null);         //具体地址  (如果是订单这个字段存的是发货地址)
		cb.setCompanyTel(null);          //公司电话  (如果是订单这个字段存的是发货人电话)
		cb.setCresponsible(null);        //负责人 (如果是订单这个字段存的是发货人名称)
		cb.setResponsibleTel(null);      //负责人电话			
		cb.setAppstyle("13");  //拨款方式 13：金币折扣
		cb.setStatusbill("04"); //单据状态判断单据来源      04：用户下单
		cb.setPriceSub(fineMoney);//金币折扣数
		cb.setProID("003");
		cb.setProjectName("金币 ");
	
		CashierBills cb1 = new CashierBills();
		cb1 = (CashierBills) cb.cloneCashierBills();
		
		beans.add(cb1);
		
		 GoodsBills gb=new GoodsBills();
		 gb.setCashierBillsID(cdid1);
		 gb.setGoodsBillsID(idgec.getServerID("GoodsBills"));
		 //填充产品
		 gb.setGoodsID(gm.getGoodsID());
		 gb.setGoodsNum(gm.getGoodsNum());
		 gb.setGoodsName(gm.getGoodsName());
		 gb.setStandard(gm.getStandard());
		 gb.setGoodsVariableID(gm.getVariableID());
		 gb.setWeight(gm.getWeight());		
		 gb.setPrice("0.01");
		 gb.setQuantity(fineCount);//被罚金币数
		 gb.setMoney(fineMoney);//被罚金额
		 
		 
		 gb.setStartDate(null);// 款源日期
		 gb.setEndDate(null);// 报账日期
		 gb.setBatchNumber(null);// 批号或期号
		 gb.setPeriodDate(null);// 有效日期
		 gb.setRemindedContent(null);// 提醒内容
		 gb.setCostType(null);// 费用类别
		 gb.setPayType("08");// 付款方式
		 gb.setPriceManage(null);// 单价管理
		 
		 		
		/******************库存流程字段*****************/
		 gb.setKcStatus("16");			//状态15：已入库 16：已出库
		 gb.setGoodstatus("00");            //状态00正常
		 gb.setSortCode(null);              //统一分类条码
		 gb.setPpID(gm.getPpID());  //项目ID也就是产品ID；
		 
		 GoodsBills gb1=new GoodsBills();
		 gb1 = (GoodsBills) gb.cloneGoodsBills();
//		 BeanUtils.copyProperties(gb1, gb);
		 beans.add(gb1);
		
		//平台   金币入库单	
		cb1 = new CashierBills();
		cb1 = (CashierBills) cb.cloneCashierBills();
		cb1.setCashierBillsID(cdid2);
		cb1.setBillsType("金币入库单");
		cb1.setStatus("15");
		cb1.setJournalNum(pzid2);// 凭证号	
		cb1.setStaffID(null);// 责任人ID
		cb1.setStaffName(null);//责任人name
		cb1.setStaffCode(null);// 责任人编号
		
		/**************************制单人信息************************/
		cb1.setInputid(null);  //制单人员id
		cb1.setInputName("系统生成");//制单人名称
		cb1.setInputCompanyid(null); //制单人公司id
		cb1.setInputCompanyName(null); //制单人公司名称
		
		/**************往来单位****************/
		cb1.setCcompanyID(null);          //往来单位ID (如果是订单这个字段存的是商户id)
		cb1.setCcompanyName(null);        //往来单位name (如果是订单这个字段存的是（商户编号，商户名称）)
		cb1.setCompanyAddr(null);         //具体地址  (如果是订单这个字段存的是发货地址)
		cb1.setCompanyTel(null);          //公司电话  (如果是订单这个字段存的是发货人电话)
		cb1.setCresponsible(null);        //负责人 (如果是订单这个字段存的是发货人名称)
		cb1.setResponsibleTel(null);      //负责人电话
		/**************往来个人****************/
		cb1.setContactUserID(publicreceipts.getAccountinformID());//被罚帐号staffid
		cb1.setCtUserName(publicreceipts.getAccountinform());//被罚帐号
		beans.add(cb1);
		
		
		gb1=new GoodsBills();		
		gb1 = (GoodsBills) gb.cloneGoodsBills();
		gb1.setGoodsBillsID(idgec.getServerID("GoodsBills"));
		gb1.setCashierBillsID(cdid2);
		gb1.setQuantity(fineCount);
		gb1.setMoney(fineMoney);
		gb1.setKcStatus("15");
		beans.add(gb1);
	 					
		//关系
		RelatedBill rb = new RelatedBill();
		rb = new RelatedBill();
		rb.setRbID(idgec.getServerID("RelatedBill"));
		rb.setCashid(cdid2);
		rb.setCashfid(cdid1);
		rb.setBilltype("金币入库单");
		beans.add(rb);
		
		//测试规则  WfjGuize201701118RU8JA6IG60000000001  本地  WfjGuize20161227Z4S665XKBB0000000005
		WfjJifenDetail edetail = new WfjJifenDetail();
		edetail.setJifenDetailId(idgec.getServerID("WfjJifenDetail"));	
		logger.info("调试信息");
		String temp = null;
		BigDecimal tempjifen = new BigDecimal(0);
		BaseBean bguize = beandao.getBeanByHqlAndParams("from WfjGuize where companyId=? and wfjGuizeId=?", new Object[]{Constant.COMPAYN_ID,"WfjGuize201701118RU8JA6IG60000000001"});
		WfjGuize eguize = (WfjGuize) bguize;
		
		//被扣金币人 
		BaseBean bb = beandao.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=? ", new Object[]{publicreceipts.getAccountinformID(),publicreceipts.getRefundSccid()});	
		if(bb == null){
			beans = null ;
			str = "该用户的金币数不足，系统驳回！";
			return str;
		}
		//判读用户金币是否满足   被折扣金币
		String bbScore=((WfjJifen)bb).getWfjJifenScore();
		float bbGolds = Float.parseFloat(bbScore)-Float.parseFloat(fineCount);
		if(bbGolds < 0){
			str = "该用户的金币数不足，系统驳回！";
			return str;
		}				
		String jifenId = ((WfjJifen)bb).getWfjJifenId();
		temp = "减少"+publicreceipts.getPrincipal()+"的金币，金币折扣单";
		tempjifen = new BigDecimal(fineCount);
		edetail.setJifenDetailScore(tempjifen.toString());
		edetail.setWfjGuizeId(eguize.getWfjGuizeId());
		edetail.setWfjJifenId(jifenId);
		edetail.setJifenDetailState(0);
		edetail.setJifenDetailName(temp);
		edetail.setJifenDetailDate(new Date());
		edetail.setWfjCashId(cdid1);
		edetail.setWfjGoodId(gm.getGoodsID());
		beans.add(edetail);
		
				
		//获得金币数人	本地 WfjGuize201701113KR9JGB8MT0000000001 测试 	WfjGuize2017011192CRSBGFZ70000000001
		WfjJifenDetail adetail = new WfjJifenDetail();
		adetail.setJifenDetailId(idgec.getServerID("WfjJifenDetail"));	
		logger.info("调试信息");
		String atemp = null;
		BigDecimal jinfen = new BigDecimal(0);
		TEshopCusCom cus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(" from TEshopCusCom where companyId = ? and acquiesce=? ", new Object[]{publicreceipts.getCompanyID(),"01"});				
		Company cp = (Company)baseBeanService.getBeanByHqlAndParams(" from Company where companyID= ?", new Object[]{publicreceipts.getCompanyID()});
				
		BaseBean beguize = beandao.getBeanByHqlAndParams("from WfjGuize where companyId=? and wfjGuizeId=?", new Object[]{Constant.COMPAYN_ID,"WfjGuize2017011192CRSBGFZ70000000001"});
		WfjGuize aguize = (WfjGuize) beguize;	
		BaseBean acc = beandao.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=? ", new Object[]{cus.getStaffid(),cus.getSccId()});	
		String ajifenId;
		WfjJifen wfjJifen = new WfjJifen();
		if(acc == null){		
			ajifenId = idgec.getServerID("WfjJifenDetail");
			wfjJifen.setWfjJifenId(ajifenId);
			wfjJifen.setSccid(cus.getSccId());
			wfjJifen.setStaffId(cus.getStaffid());
			wfjJifen.setWfjJifenScore(fineCount);
			wfjJifen.setWfjJifenState(0);
			beans.add(wfjJifen);
		}else{
			ajifenId = ((WfjJifen)acc).getWfjJifenId();
			wfjJifen = (WfjJifen)baseBeanService.getBeanByHqlAndParams(" from WfjJifen where wfjJifenId=? ", new Object[]{ajifenId});			
			String JifenScore=((WfjJifen)acc).getWfjJifenScore();
			float golds = Float.parseFloat(JifenScore)+Float.parseFloat(fineCount);	
			wfjJifen.setWfjJifenScore(golds+"");
			beans.add(wfjJifen);
		}				
		atemp = "折扣"+publicreceipts.getPrincipal()+"的金币，"+"增加"+cp.getCompanyName()+"的金币，来源于金币折扣";
		jinfen = new BigDecimal(fineCount);
		adetail.setJifenDetailScore(jinfen.toString());
		adetail.setWfjGuizeId(aguize.getWfjGuizeId());
		adetail.setWfjJifenId(ajifenId);
		adetail.setJifenDetailState(0);
		adetail.setJifenDetailName(atemp);
		adetail.setJifenDetailDate(new Date());
		adetail.setWfjCashId(cdid2);
		adetail.setWfjGoodId(gm.getGoodsID());
		beans.add(adetail);
		
		
		//积分任务
		WfjTask wt = new WfjTask();
		wt.setWfjTaskId(idgec.getServerID("WfjTask"));
		logger.info("调试信息");
		wt.setStaffId(publicreceipts.getAccountinformID());
		wt.setWfjGuizeId(eguize.getWfjGuizeId());
		wt.setWfjTaskDate(new Date());
		beans.add(wt);
		
		
			
		String uhql = "update WfjJifen u set u.wfjJifenScore=u.wfjJifenScore-? where u.staffId=?";
		beandao.saveBeansListAndexecuteHqlsByParams(beans, new String[]{uhql}, new Object[]{fineCount,publicreceipts.getAccountinformID()});
			
		str = "操作成功";						
		return str;
					
	}
	
}
