package hy.ea.production.action.cprocedure;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.production.ProductInspection;
import hy.ea.bo.production.view.UtboundOrderVo;
import hy.ea.production.service.WarehouseService;
import hy.ea.util.DateUtil;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


import net.sf.json.JSONObject;


import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**
 * 产品库存管理
 * @author zj
 *
 */
public class ProductStorageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private WarehouseService warehouseService;
	private PageForm pageForm;
	private int pageNumber;				//每页显示的条数
	private String result;					//ajax返回字段
	private UtboundOrderVo utboundOrderVo;
	private String type;						//判断单据是从什么类型过来的
	private String goodId;					//物品ID
	private String parameter;				//物品参数（条码或物品编号）
	private String fiveClear;   //组织机构
	private String category;	//产品类别


	/**
	 * 获取主页面
	 */
	public String getAccessToProductInformation(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> obj=new ArrayList<Object>();
		String hql="from UtboundOrderVo u where u.billstype=? and u.companyid=? and u.organizationid=? ";
		obj.add("生产入库单");obj.add(account.getCompanyID());obj.add((String) session.get("organizationID"));
		if(!"".equals(type)&&type!=null){
			hql+=" and u.status=?";
			obj.add(type);
		}else{
			hql+=" and u.status between ? and ?";
			obj.add("14");obj.add("15");
		}
		if(utboundOrderVo!=null){
			if(utboundOrderVo.getJournalnum()!=null&&!"".equals(utboundOrderVo.getJournalnum())){
				hql+=" and u.journalnum like ?";
				obj.add("%"+utboundOrderVo.getJournalnum()+"%");
			}
			if(utboundOrderVo.getGoodsname()!=null&&!"".equals(utboundOrderVo.getGoodsname())){
				hql+=" and u.goodsname like ?";
				obj.add("%"+utboundOrderVo.getGoodsname()+"%");
			}
		}
		if(category!=null&&!"".equals(category)){
			 hql+=" and u.category=?";
			 obj.add(category);
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			 hql+=" and u.fiveClear=?";
			 obj.add(fiveClear);
		}
		hql+="order by u.cashierDate desc";
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber <1 ? 10 : pageNumber), hql, obj.toArray());
		return "pageInformation";
	}

	/*
	 * 获取页面信息
	 */
	public String getSinglePageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String staffHql="from Staff where groupCompanySn=? and staffID=?";
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{session.get("groupCompanySn").toString(),account.getStaffID()});
		UtboundOrderVo ut=null;

		String hql="from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
		DepotManage depot=(DepotManage) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"001",account.getCompanyID(),"成品库","00"});

		if("01".equals(type)){
			ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
					"from ProductPackaging where ppID=? and companyID=?", new Object[]{utboundOrderVo.getPpId(),account.getCompanyID()});
			re.setAttribute("pp",pp);
			ut=(UtboundOrderVo)baseBeanService.getBeanByHqlAndParams(
					"from UtboundOrderVo where cashierbillsid=?", new Object[]{utboundOrderVo.getCashierbillsid()});
		}
		if(ut==null){
			ut=new UtboundOrderVo();
			ut.setJournalnum(serverService.getBillID(account.getCompanyID()));
		}
		ut.setCashierDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
		re.setAttribute("status","01".equals(type)?"edit":"add");
		re.setAttribute("ut",ut);
		re.setAttribute("staff", staff);
		re.setAttribute("depot", depot);
		return "proData";
	}
	/**
	 * 获取修改页面信息
	 */
	public String ajaxEditASinglePageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String staffHql="from Staff where groupCompanySn=? and staffID=?";
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{session.get("groupCompanySn").toString(),account.getStaffID()});
		String ppHql="from ProductPackaging where ppID=? and companyID=?";
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(ppHql, new Object[]{utboundOrderVo.getPpId(),account.getCompanyID()});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("date",DateUtil.getCurrentDate("yyyy-MM-dd"));
		map.put("staff",staff);
		map.put("pp",pp);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}
	/**
	 * 获取添加页面基本信息
	 */
	public String ajaxAddASinglePageData(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String journalNum=serverService.getBillID(account.getCompanyID());
		String currentTime=DateUtil.getCurrentDate("yyyy-MM-dd");
		String hql="from DepotManage where depotPID=? and companyID=? and depotName=? and depotState=?";
		List<BaseBean> depotList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"001",account.getCompanyID(),"成品库","00"});
		String staffHql="from Staff where groupCompanySn=? and staffID=?";
		Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams(staffHql, new Object[]{session.get("groupCompanySn").toString(),account.getStaffID()});
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("staff",staff);
		map.put("currentTime",currentTime);
		map.put("journalNum", journalNum);
		map.put("staffId",account.getStaffID());
		map.put("staffName",account.getStaffName());
		map.put("list",depotList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "proData";
	}

	/**
	 * 修改入库单
	 */
	public String editProductStorageList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		List<BaseBean> list=new ArrayList<BaseBean>();
		String num=re.getParameter("number");
		String cashiHql="from CashierBills where cashierBillsID=? and companyID=?";
		CashierBills ca=(CashierBills)baseBeanService.getBeanByHqlAndParams(cashiHql, new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		String goodsHql=" from GoodsBills where cashierBillsID=? and companyID=?";
		GoodsBills goods=(GoodsBills)baseBeanService.getBeanByHqlAndParams(goodsHql, new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		ProductInspection pi=(ProductInspection)baseBeanService.getBeanByHqlAndParams(
				"from ProductInspection where proInspectionID=?", new Object[]{goods.getProInspectionID()});
		ca.setStaffID(utboundOrderVo.getStaffID());
		ca.setStaffName(utboundOrderVo.getStaffName());
		goods.setQuantity(utboundOrderVo.getQuantity());
		pi.setResidualQuantity(Double.parseDouble(pi.getResidualQuantity())+(Double.parseDouble(num)-Double.parseDouble(utboundOrderVo.getQuantity()))+"");
		list.add(ca);list.add(goods);list.add(pi);
		baseBeanService.executeHqlsByParamsList(list, null, null);
		return "success";
	}



	/**
	 * 获取检验合格的产品
	 */
	@SuppressWarnings("unchecked")
	public String ajaxGetDocumentsForInspection(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String industryClassification=re.getParameter("industry");
		String productClassification=re.getParameter("product");
		List<Object> testParams=new ArrayList<Object>();
		String testSql=/*"select p.ppid,p.goodsid,pd.Batchnumber,p.goodsname,p.productCode," +
				"p.projecttype,p.standard,p.price,i.qualifiedQuantity,case when i.qualifiedQuantity is" +
				" null then 0 else i.qualifiedQuantity*p.price end,i.inspectionManName,i.inspectionManID," +
				"i.inspectionTime,i.proInspectionID,pd.type,pd.cashierBillsID from dtprodocuments pd left join dtgoodsbills g on" +
				" g.cashierbillsid=pd.proDocumentsID left join dt_productpackaging p on g.ppid=p.ppid" +
				" left join dtassembly ass on ass.productID=g.ppid and ass.cashierbillsid=pd.prodocumentsid" +
				" and ass.productpid=g.ppid left join dtinspection i on i.proassemblyid=ass.proassemblyid" +
				" where pd.status=? and pd.companyid=? and i.residualQuantity>?";*/
        "select case when p.ppid is null then ' ' else p.ppid end ," +
		 "case when p.goodsid is null then ' ' else p.goodsid end,case when pp.lot is null then ' ' else pp.lot end," +
		 "case when p.goodsname is null then ' ' else p.goodsname end,case when p.productCode is null then ' ' else p.productCode end," +
		 "case when p.projecttype is null then ' ' else p.projecttype end,case when p.standard is null then ' ' else p.standard end,case when " +
		 "p.price is null then ' ' else p.price end,case when d.yieldnumber is null then ' ' else d.yieldnumber end,case when d.yieldnumber is null then 0 else" +
		 " d.yieldnumber*p.price end,case when d.auditor is null then ' ' else d.auditor end,case when d.auditorid is null then ' ' else d.auditorid end,case when d.dchecktime is null " +
		 "then ' ' else d.dchecktime end,case when d.dcheckid is null then ' ' else d.dcheckid end from dt_ProductPackaging p left join production_Ptrack pp on pp.id=p.ppid left join production_DCheck d  " +
		 "on pp.ptrackeId=d.id where p.companyid=? and d.status=? and d.yieldnumber>?";

		testParams.add("02");testParams.add(account.getCompanyID());
		testParams.add("0.0");
		if(industryClassification!=null&&!"".equals(industryClassification)){
			testSql+=" and p.producttype=?";
			testParams.add(industryClassification);
		}
		if(productClassification!=null&&!"".equals(productClassification)){
			testSql+=" and p.tradeCode=?";
			testParams.add(productClassification);
		}
		if(parameter!=null&&!"".equals(parameter)){
			testSql+=" and p.productCode like ? and p.parentId is null";
			testParams.add("%"+parameter+"%");
		}
		if(category!=null&&!"".equals(category)){
			testSql+=" and pd.category=?";
			testParams.add(category);
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			testSql+=" and pd.fiveClear=?";
			testParams.add(fiveClear);
		}
		List<BaseBean>   bsimList=baseBeanService.getListBeanBySqlAndParams(testSql, testParams.toArray());
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("list",bsimList);
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}

	/**
	 * 添加产品入库单
	 * @throws ParseException
	 */
	public String addProductStorageList() throws ParseException{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		List<BaseBean> list=new ArrayList<BaseBean>();
		String cashierbillsId=serverService.getServerID("CashierBills");
		String financialbillId=serverService.getServerID("FinancialBill");
		String goodsbillsId=serverService.getServerID("GoodsBills");
		String organizationID=(String) session.get("organizationID");
		String groupCompanySn=session.get("groupCompanySn").toString();
		String cashierBillsID=re.getParameter("cashierBillsID");
		String dcheckid=re.getParameter("dcheckid");
		String jNumOrder="";
		if("00".equals(type)){
			CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
					"from CashierBills where cashierBillsID=?", new Object[]{cashierBillsID});
			jNumOrder=c.getJournalNum();
		}
		//存储入库单
				CashierBills cashier=new CashierBills();
				cashier.setCashierBillsID(cashierbillsId);
				cashier.setJournalNum(utboundOrderVo.getJournalnum());
				cashier.setBillsType("生产入库单");
				cashier.setGroupCompanySn(groupCompanySn);
				cashier.setCompanyID(account.getCompanyID());
				cashier.setCompanyName(account.getCompanyName());
				cashier.setOrganizationID(organizationID);
				cashier.setCashierDate(new Date());
				cashier.setStaffID(utboundOrderVo.getStaffID());
				cashier.setStaffName(utboundOrderVo.getStaffName());
				cashier.setInputid(account.getStaffID());
				cashier.setInputName(account.getStaffName());
				cashier.setStatus("14");
				cashier.setjNumOrder(jNumOrder);
				cashier.setFiveClear(fiveClear);
				list.add(cashier);

				//添加进销存单据
				FinancialBill fin=new FinancialBill();
				fin.setFinancialbillID(financialbillId);
				fin.setCashierBillsID(cashierbillsId);
				fin.setGroupCompanySn(groupCompanySn);
				fin.setCcompanyID(account.getCompanyID());
				fin.setOrganizationID(organizationID);
				fin.setDepotID("001");
				fin.setDepotName("生产流水线");
				fin.setDrdepotID(utboundOrderVo.getDrdepotID());
				fin.setDrdepotName(utboundOrderVo.getDrdepotName());
				fin.setBillsType("生产入库单");
				fin.setJournalNum(utboundOrderVo.getJournalnum());
				fin.setBillsDate(new Date());
				fin.setBillStaffID(account.getStaffID());
				fin.setBillStaffName(account.getStaffName());
				fin.setStaffsID(utboundOrderVo.getStaffsID());
				fin.setStaffsName(utboundOrderVo.getStaffsName());
				fin.setExaminegoodsDate(utboundOrderVo.getExaminegoodsDate());
				fin.setBillStatus("14");
				list.add(fin);

				//添加物品单据
				GoodsBills goods=new GoodsBills();
				goods.setGoodsBillsID(goodsbillsId);
				goods.setCompanyID(account.getCompanyID());
				goods.setCashierBillsID(cashierbillsId);
				goods.setPpID(utboundOrderVo.getPpId());
				goods.setGoodsID(utboundOrderVo.getGoodsId());
				goods.setTypeID(utboundOrderVo.getTypeId());
				goods.setGoodsNum(utboundOrderVo.getGoodsnum());
				goods.setGoodsName(utboundOrderVo.getGoodsname());
				goods.setStandard(utboundOrderVo.getStandard());
				goods.setPrice(utboundOrderVo.getPrice());
				goods.setQuantity(utboundOrderVo.getQuantity());
				goods.setMoney(StringUtil.formatFloatNumber((Double.parseDouble(utboundOrderVo.getQuantity())*Double.parseDouble(utboundOrderVo.getPrice()))));
				goods.setDepotID(utboundOrderVo.getDrdepotID());
				goods.setDepotName(utboundOrderVo.getDrdepotName());
				goods.setKcStatus("14");
				goods.setGoodsStatus("00");
				goods.setCategory(category);
				goods.setProInspectionID(dcheckid);
				list.add(goods);


				//修改产品表中的可用数量
				String pdHql="from ProductInspection where proInspectionID=?";
				ProductInspection pi=(ProductInspection)baseBeanService.getBeanByHqlAndParams(pdHql, new Object[]{dcheckid});
				pi.setResidualQuantity((Double.parseDouble(pi.getResidualQuantity())-Double.parseDouble(utboundOrderVo.getQuantity()))+"");
				list.add(pi);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);

		return "success";
	}

	/**
	 * 查看审核
	 */
	@SuppressWarnings("unchecked")
	public String storageListReviewAudit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String utHql=" from UtboundOrderVo where cashierbillsid=? and companyid=?";
		UtboundOrderVo ut=(UtboundOrderVo)baseBeanService.getBeanByHqlAndParams(utHql,new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		re.setAttribute("ut",ut);
		String orgHql="from COrganization where companyID=? and organizationID=?";
		COrganization org=(COrganization)baseBeanService.getBeanByHqlAndParams(orgHql, new Object[]{account.getCompanyID(),ut.getOrganizationid()});
		re.setAttribute("org",org);
		re.setAttribute("comName",account.getCompanyName());
		//配件信息
		String sql="select productcode,goodsname,goodsnum,type,standard,quantity,price,(quantity*price),remark" +
				" from dt_ProductPackaging where field=? connect by parentid= prior ppid start with ppid=? order by productcode";
		List<BaseBean> list=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{"01",ut.getPpId()});
		re.setAttribute("list",list);
		//审核信息
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?",
				new Object[]{utboundOrderVo.getCashierbillsid()});
		if(bCheck!=null){
			List<BaseBean> BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
					"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
			String str="";
			Map<String,String> billcheckmap=new HashMap<String, String>();
			if(BillCheckList.size()>0){
				for(int i=0;i<BillCheckList.size();i++){
					BillCheck bc=(BillCheck) BillCheckList.get(i);
					if(bc.getDeptpost()!=null){
						billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
						billcheckmap.put(bc.getDeptpost()+"zt",bc.getAuditorstatus());
						str+=bc.getAuditorname()+" : "+bc.getComments()==null?" ":bc.getComments()+" ; ";
					}
				}
			}
			re.setAttribute("str",str);
			re.setAttribute("billcheckmap", billcheckmap);
		}
		if("examine".equals(type)){
			return "reviewAudit";
		}else{
			re.setAttribute("orgName", (String) session.get("organizationName"));  re.setAttribute("comName", account.getCompanyName());
			return "toPrint";
		}
	}

	/**
	 * 入库单审核
	 */
	public String StorageSingleAudit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest re=ServletActionContext.getRequest();
		String status=re.getParameter("status");
		String audit=re.getParameter("audit");
		String deptpost=re.getParameter("deptpost");
		List<BaseBean> list=new ArrayList<BaseBean>();
		BillCheck bc=new BillCheck();
		bc.setCheckid(serverService.getServerID("BillCheck"));
		bc.setAuditorcompanyid(account.getCompanyID());
		bc.setAuditorcompanyname(account.getCompanyName());
		bc.setFirstBillsID(utboundOrderVo.getCashierbillsid());
		bc.setInputid(account.getStaffID());
		bc.setNewBillsID(utboundOrderVo.getCashierbillsid());
		bc.setInputname(account.getStaffName());
		bc.setAudittime(new Date());
		bc.setAuditorid(account.getStaffID());
		bc.setAuditorname(account.getStaffName());
		bc.setComments(audit);
		bc.setDeptpost(deptpost);
		bc.setAuditorstatus("yes".equals(status)?"02":"03");
		list.add(bc);
		if(!"yes".equals(status)){
			CashierBills ca=(CashierBills)baseBeanService.getBeanByHqlAndParams(" from CashierBills where cashierBillsID=? and companyID=?",
								new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
			ca.setStatus("10");
			list.add(ca);
		}

		baseBeanService.saveBeansListAndexecuteHqlsByParams(list,null,null);
		return "success";
	}
	/**
	 * 获取是否审核
	 */
	public String ajaxCheckWhetherAudit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String BillHql="from BillCheck where newBillsID=? and auditorcompanyid=?";
		List<BaseBean> billList=baseBeanService.getListBeanByHqlAndParams(BillHql, new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		String cashiHql="from UtboundOrderVo where cashierbillsid=? and companyid=?";
		UtboundOrderVo ut=(UtboundOrderVo)baseBeanService.getBeanByHqlAndParams(cashiHql, new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		Map<String,Object> map=new HashMap<String, Object>();
		if(billList.size()==0||"10".equals(ut.getStatus())){
			map.put("sta","0");
		}else{
			map.put("sta","1");
		}
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return Action.SUCCESS;
	}
	/**
	 * 确认入库
	 */
	public String confirmationOfStorage(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		CashierBills c=(CashierBills)baseBeanService.getBeanByHqlAndParams(
				"from CashierBills where cashierBillsID=? and companyID=?", new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		String sql="update dtcashierbills set status=? where cashierbillsid=? and companyid=?";
		String utHql="from UtboundOrderVo where cashierbillsid=? and companyid=?";
		UtboundOrderVo ut=(UtboundOrderVo)baseBeanService.getBeanByHqlAndParams(utHql, new Object[]{utboundOrderVo.getCashierbillsid(),account.getCompanyID()});
		String invHql="from Inventory where goodsID=? and companyID=? and warehouse=? and productId=?";
		Inventory inv=(Inventory)baseBeanService.getBeanByHqlAndParams(invHql, new Object[]{ut.getGoodsId(),account.getCompanyID(),ut.getDrdepotID(),ut.getPpId()});
		List<BaseBean> list=new ArrayList<BaseBean>();

		c.setStatus("15");
		if(inv==null){
			inv=new Inventory();
			//库存表数据
			inv.setInventoryID(serverService.getServerID("Inventory"));
			inv.setCompanyID(account.getCompanyID());
			inv.setGroupCompanySn(session.get("groupCompanySn").toString());
			inv.setOrganizationID((String) session.get("organizationID"));
			inv.setStaffID(account.getStaffID());
			inv.setStaffName(account.getStaffName());
			inv.setWarehouse(ut.getDrdepotID());
			inv.setWarehouseName(ut.getDrdepotName());
			inv.setGoodsID(ut.getGoodsId());
			inv.setGoodsType(ut.getTypeId());
			inv.setGoodsName(ut.getGoodsname());
			inv.setStandard(ut.getStandard());
			inv.setGoodsCoding(ut.getGoodsnum());
			inv.setUnitPrice(ut.getPrice());
			inv.setInvenQuantity(ut.getQuantity());
			inv.setGoodstatus("00");
			inv.setSumPrice(ut.getMoney());
			inv.setStatus("03");
			inv.setWeizhi(ut.getDrdepotName());
			inv.setProductId(ut.getPpId());
			inv.setFiveClear(ut.getFiveClear());
			inv.setCategory(ut.getCategory());
			list.add(inv);
		}else{
			inv.setInvenQuantity((Double.parseDouble(inv.getInvenQuantity())+Double.parseDouble(ut.getQuantity()))+"");
			inv.setSumPrice(StringUtil.formatFloatNumber((Double.parseDouble(inv.getSumPrice())+Double.parseDouble(ut.getMoney()))));
			inv.setStatus("03");
			list.add(inv);
		}

		//库存盘点表数据
		stockInv st=new stockInv();
		st.setStockinvID(serverService.getServerID("stockInv"));
		st.setCompanyID(account.getCompanyID());
		st.setGroupCompanySn(session.get("groupCompanySn").toString());
		st.setGoodsID(ut.getGoodsId());
		st.setGoodsType(ut.getTypeId());
		st.setGoodsName(ut.getGoodsname());
		st.setInvenQuantity(ut.getQuantity());
		st.setSummoney(ut.getMoney());
		st.setIntime(new Date());
		st.setType("00");
		st.setStaffID(account.getStaffID());
		st.setStaffName(account.getStaffName());
		st.setWarehouse(ut.getDrdepotID());
		st.setWarehouseName(ut.getDrdepotName());
		list.add(st);
		baseBeanService.saveBeansListAndexecuteSqlsByParams(list,new String[]{sql},new Object[]{"15",utboundOrderVo.getCashierbillsid(),account.getCompanyID()});

		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppID=?", new Object[]{ut.getPpId()});
		if(!"虚拟物品".equals(pp.getType())){
			String[] str={"GoodsNum",account.getCompanyID(),inv.getInventoryID(),ut.getGoodsbillsid(),ut.getGoodsId(),"03",ut.getQuantity(),ut.getGoodsnum()};
			warehouseService.numberOfGeneratedItems(str);
		}
		return "success";
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public UtboundOrderVo getUtboundOrderVo() {
		return utboundOrderVo;
	}
	public void setUtboundOrderVo(UtboundOrderVo utboundOrderVo) {
		this.utboundOrderVo = utboundOrderVo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
