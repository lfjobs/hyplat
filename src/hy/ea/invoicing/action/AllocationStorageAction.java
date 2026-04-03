package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;

/**
 * 调拨 入库
 * @author xyz
 *
 */
public class AllocationStorageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	public InputStream excelStream;
	private FinancialBill financialBill;  //进销存单据表(调拨单据表)
	private FinancialBillsGood financialBillsGood;   //进销存子类(物品单据表)
	private CashierBills cashierBills;//出纳单表
	//调拨出纳单获取
    private CashierBillsVO cashierBillsVO;//出纳单的视图（用于调拨单获取相关数据）
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private Map<String, GoodsBills> goodsmapold;
	private Map<String, GoodsBills> goodsmap;
	private Map<String, String> billcheckmap;//单据审核map
	private List<CCode> payTypeList;
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;
	private List<CCode> codeRelationList;
	private List<CCode> typelist;//仓库管理
	private String companyname;
	private String organizationname;
	/**
	 * 仓库列表中--库列表
	 */
	private List<BaseBean> warehouseList;
	private List<BaseBean> billsgoodlist;//调拨入库物品集合
	private List<FinancialBillsGood> BillsGoodList;
	private ContactUser contactUser1;
	private ContactCompanyView contactCompanyView1;
	private Inventory   inventoryParam;
	private String sdate;
	private String edate;
	private String print;
	private stockInv stockinv;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
//--------------------------------------------------------------------------------
	private int camount;//打印预览调拨入库单计算总的金额
	private ContactCompany contactCompanyView;
	private String result;
	private String purchaseDate;//入库时间
	private String type;//保存或者提交审核
	private String deptpost;//单据审核的职务
	private String remarks;
	private int loginstaffcheck;//单据审核人中有没有当前登录人参数
	/**
	 * 调拨入库单审核信息
	 */
	private List<BaseBean> BillCheckList;
	/**
	 * 获取单据的状态验证
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getcashierBillstatus() throws UnsupportedEncodingException {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { financialBill.getFinancialbillID()};
		String hql = "from FinancialBill where financialbillID= ?";
		FinancialBill fina= (FinancialBill) baseBeanService.getBeanByHqlAndParams(
				hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("getstatus", fina.getBillStatus());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 调拨入库单——列表
	 * 
	 * @return
	 */
	public String toAllocationStorageList() {
		List<Object> list = getListyh();		
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		
		return "toallostoragelist";
	}
	/**
	 * 查询调拨入库单列表（可根据条件查询）被调用
	 * 
	 * @return
	 */
	public List<Object> getListyh() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql=VOtool.getCashierBillVO(4);
		sql+=" where f.groupCompanySn = ?";
		parms.add(groupCompanySn);
		sql += " and f.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and f.organizationID=?";
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			FinancialBill  financialBill=(FinancialBill)session.get("financialBill");
			if (financialBill.getJournalNum() != null
					&& !financialBill.getJournalNum().trim().equals("")) {
				sql += " and f.journalNum like ? ";
				parms.add("%" + financialBill.getJournalNum().trim() + "%");
			}
			if (financialBill.getBillStaffName() != null
					&& !financialBill.getBillStaffName().equals("")) {
				sql += " and f.billStaffName = ? ";
				parms.add(financialBill.getBillStaffName());
			}
			if (sdate != null && edate != null && !"".equals(sdate)
					&& !"".equals(edate)) {
				sql += " and to_date(f.billsDate,'yyyy-MM-dd') between ? and ?";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
		}
		sql+="and f.billsType=?";
		parms.add("调拨入库单");
		sql+=" and (f.billStatus=?";
		parms.add("22");//草稿
		sql+=" or f.billStatus=?";
		parms.add("05");//审核中
		sql+=" or f.billStatus=?)";
		parms.add("15");//已入库
		sql+="order by f.billsDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	/**
	 * 调拨入库单——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearchEGB() {
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
		return toAllocationStorageList();
	}
	/**
	 * 调拨入库单——查看
	 * @return
	 */
	public String seeExamineGoodsBill(){
		toSeegb();
		return "toSeegb";
	}
	/**
	 * 调拨入库单——打印单据
	 */
	public String toPrintExamineGoodsBill() {
		toSeegb();
		return "toSeegbprint";
	}
	/**
	 * 调拨入库单——查看，打印调用
	 * 
	 */
	private void toSeegb(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName", sta.getStaffName());
		session.put("ManStaffCode", sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		String hql="from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
		financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,financialBill.getFinancialbillID()});
		String hqlca="from CashierBills where cashierBillsID=?";
		cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams(hqlca, 
				new Object[]{financialBill.getCashierBillsID()});
		String hqlg="from GoodsBills where cashierBillsID=? order by goodsNomber";
		warehouseList = baseBeanService.getListBeanByHqlAndParams(hqlg, new Object[]{financialBill.getCashierBillsID()});
		//计算合格物品总金额
		/*if(pageForm!=null){
		    int size = pageForm.getList().size();
			for(int i=0;i<size;i++) {
			GoodsBills good=(GoodsBills) pageForm.getList().get(i);
			int price=Integer.parseInt(good.getPrice());//单价
			int num=Integer.parseInt(good.getIsQualify());//合格数量
			int amount=price*num;
			camount+=amount;
		     }
		}*/
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { financialBill.getCompanyID() });
		companyname = company.getCompanyName();
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						financialBill.getCompanyID(),
						financialBill.getOrganizationID()});
		organizationname = cOrganization.getOrganizationName();
		//审核信息
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?", new Object[]{financialBill.getCashierBillsID()});
		if(bCheck!=null){
			BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
					"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
			loginstaffcheck=0;
			if(BillCheckList.size()>0){
				billcheckmap=new HashMap<String, String>();
				for(int i=0;i<BillCheckList.size();i++){
					BillCheck bc=(BillCheck) BillCheckList.get(i);
					if(bc.getDeptpost()!=null){
						billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
						if((bc.getAuditorid()).equals(sta.getStaffID())){
							loginstaffcheck++;
						}
					}
				}
			}
		}
		
	}
	/**
	 * 添加调拨入库单-页面
	 * 
	 */
	public String toExamineGoodsAddDan(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String hql="from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
		//单据数据
		financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,financialBill.getFinancialbillID()});
		String hqlca="from CashierBills where cashierBillsID=?";
		cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams(hqlca, new Object[]{financialBill.getCashierBillsID()});
		//物品数据
		String hqlg="from GoodsBills where cashierBillsID=? order by goodsNomber";
		Object[] par = {financialBill.getCashierBillsID()};
		billsgoodlist=baseBeanService.getListBeanByHqlAndParams(hqlg, par);
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName1", sta.getStaffName()+"---"+sta.getStaffCode());
		session.put("ManStaffName", sta.getStaffName());
		session.put("ManStaffCode", sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		//String companyID = account.getCompanyID();
		/*payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");*/
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		/*codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");*/
		//选择仓库
		Object[] params = {account.getCompanyID()};
		typelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20101014v5zed7cukk0000000004");
		pageForm = baseBeanService
				.getPageForm(
						(null != pageForm ? pageForm.getPageNumber() : 1),
						(pageNumber==0?10:pageNumber),
						" from DepotManage where companyID = ? and depotState='00'",
						params);
		//关联的调拨入库单审核信息
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck " +
				"where newBillsID=?", new Object[]{financialBill.getCashierBillsID()});
		if(bCheck!=null){
			BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
					"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
			loginstaffcheck=0;
			if(BillCheckList.size()>0){
				billcheckmap=new HashMap<String, String>();
				for(int i=0;i<BillCheckList.size();i++){
					BillCheck bc=(BillCheck) BillCheckList.get(i);
					if(bc.getDeptpost()!=null){
						billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
						if((bc.getAuditorid()).equals(sta.getStaffID())){
							loginstaffcheck++;
						}
					}
				}
			}
		}
		return "toinvoicingstorage";
	}
	/**
	 * 审核信息的保存
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("unused")
	public String saveBillCheck() throws UnsupportedEncodingException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		//当前登录人员信息
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hqlcash = "from CashierBills where cashierBillsID = ?";
		CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,
				new Object[] { cashierBills.getCashierBillsID() });		
		
		BillCheck billcheck=new BillCheck();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		billcheck.setCheckid(serverService.getServerID("billcheck"));
		billcheck.setAuditorcompanyid(cbills.getCompanyID());
		billcheck.setAuditorcompanyname(cbills.getCompanyName());
		billcheck.setAuditororgID(cbills.getDepartmentID());
		billcheck.setAuditororgName(cbills.getDepartmentName());
		billcheck.setNewBillsID(cbills.getCashierBillsID());
		billcheck.setOldBillsID(cbills.getCashierBillsID());
		billcheck.setFirstBillsID(cbills.getCashierBillsID());
		billcheck.setInputid(sta.getStaffID());
		billcheck.setInputname(sta.getStaffName());
		billcheck.setStaffID(sta.getStaffID());
		billcheck.setStaffName(sta.getStaffName());
		billcheck.setJournalNum(cbills.getJournalNum());
		billcheck.setBillsType(cbills.getBillsType());
		billcheck.setProjectName(cbills.getProjectName());
		billcheck.setAudittime(new Date());
		billcheck.setAuditorid(sta.getStaffID());
		billcheck.setAuditorname(sta.getStaffName());
		billcheck.setComments(remarks);// 审核意见 b
		billcheck.setAuditorstatus("02"); // 审核状态 '01'未审核 '02'已审核 '03' 驳回
		billcheck.setDeptpost(deptpost);//职务；
		billcheck.setRemark(remarks);//备注
		billcheck.setCashierDate(new Date());// 单据日期
		baseBeanList.add(billcheck);
		parameter = "添加审核记录（" + billcheck.getJournalNum() + "）";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		//统计当前登录人员审核次数
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck " +
				"where newBillsID=?", new Object[]{cashierBills.getCashierBillsID()});
		if(bCheck!=null){
			BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where " +
					"firstBillsID=? order by audittime desc", new Object[]{bCheck.getFirstBillsID()});
			loginstaffcheck=0;
			if(BillCheckList.size()>0){
				for(int i=0;i<BillCheckList.size();i++){
					BillCheck bc=(BillCheck) BillCheckList.get(i);
					if(bc.getDeptpost()!=null){
						if((bc.getAuditorid()).equals(sta.getStaffID())){
							loginstaffcheck++;
						}
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginstaff", loginstaffcheck);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 调拨入库——保存
	 * @throws ParseException 
	 */
	public String saveWareManagement() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,new Object[] { account.getStaffID() });
		/*** 调拨入库单数据修改*/
		String hqlfb = "from FinancialBill where financialbillID = ?";
		FinancialBill finbill = (FinancialBill) baseBeanService.getBeanByHqlAndParams(hqlfb,new Object[] {financialBill.getFinancialbillID()});
		finbill.setStaffID(financialBill.getStaffID());
		finbill.setStaffName(financialBill.getStaffName());
		finbill.setStaffPhone(financialBill.getStaffPhone());
		finbill.setCcompanyTel(financialBill.getCcompanyTel());
		finbill.setDrccompanyTel(financialBill.getDrccompanyTel());
		finbill.setPurchaseDate(purchaseDate);//入库日期
		finbill.setStaffsID(financialBill.getStaffsID());//入库人
		finbill.setStaffsName(financialBill.getStaffsName());
		if(type.equals("save")){
			finbill.setBillStatus("22");;//草稿
		}
		else if(type.equals("savecheck")){
			finbill.setBillStatus("05");;//审核中
		}
		baseBeanList.add(finbill);
		
		String hqlcash = "from CashierBills where cashierBillsID = ?";
		CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[] { finbill.getCashierBillsID() });
		cbills.setStaffID(sta.getStaffID());// 责任人ID（制单人）
		cbills.setStaffName(sta.getStaffName());//责任人name
		cbills.setStaffCode(sta.getStaffCode());// 责任人编号
		if(type.equals("save")){
			cbills.setStatus("22");//草稿
		}
		else if(type.equals("savecheck")){
			cbills.setStatus("05");//审核中
		}
	    baseBeanList.add(cbills);
	    parameter = "修改调拨入库单（凭证号" + cbills.getJournalNum() + "）";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);			
		return "success";
	}
	/**
	 * 调拨入库——确认入库
	 * 
	 */
	@SuppressWarnings("deprecation")
	public String updateWareManagement() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		//当前登录人员信息
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		/*** 调拨入库单更改单据状态*/
		String hqlfb = "from FinancialBill where financialbillID = ?";
		FinancialBill finbill = (FinancialBill) baseBeanService.getBeanByHqlAndParams(hqlfb,new Object[] {financialBill.getFinancialbillID()});		
		finbill.setBillStatus("15");//已入库
		String hqlcash = "from CashierBills where cashierBillsID = ?";
		CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[] { finbill.getCashierBillsID() });
		cbills.setStatus("15");//已入库
		baseBeanList.add(finbill);
	    baseBeanList.add(cbills);
	    //改变物品的状态
		String hqlg="from GoodsBills where cashierBillsID = ?";
		Object[] par = {finbill.getCashierBillsID()};
		billsgoodlist=baseBeanService.getListBeanByHqlAndParams(hqlg, par);
		
		String strInventoryID = null;//存入库存表的业务主键
		String invenQuantity= null;//存入库存表后库存
		
		List<String> sql=new ArrayList<String>();
		List<Object[]> obj=new ArrayList<Object[]>();
		Map<String,GoodsBills> goodsIdMap =  new HashMap<String,GoodsBills>();
		for(int i=0;i<billsgoodlist.size();i++){
			GoodsBills finbg =  new GoodsBills();
			GoodsBills goodsBills =  new GoodsBills();
			finbg=(GoodsBills) billsgoodlist.get(i);
			goodsBills = (GoodsBills) billsgoodlist.get(i);
			if(goodsIdMap.get(finbg.getGoodsID())!=null){
				goodsBills.setQuantity((Integer.parseInt(goodsIdMap.get(finbg.getGoodsID()).getQuantity())+1)+"");
			}
			goodsIdMap.put(finbg.getGoodsID(),goodsBills);
			List<Object> goodsNumObj = new ArrayList<Object>();
			if(finbg.getGoodsBillsID()!=null&&!"".equals(finbg.getGoodsBillsID())){
				//更改调拨入库单入库物品的状态	
				finbg.setKcStatus("15");//已入库
				//判断库存表中有没有要入库的物品

				baseBeanList.add(finbg);
				parameter = "员工："+account.getAccountName()+"调拨入库（更新库房物品 ID：" + finbg.getGoodsID() + "）";
				CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);
				baseBeanList.add(cLogBook);
				//库存盘点表
				String goodsNumHql="update dtGoodsNum set goodsBillsID=?,status=?,cashierbillsid=? where companyID=? and cashierBillsID=? and status=?";
				goodsNumObj.add(finbg.getGoodsBillsID());goodsNumObj.add("03");goodsNumObj.add(strInventoryID);
				goodsNumObj.add(account.getCompanyID());goodsNumObj.add(finbg.getInventoryID());goodsNumObj.add("04");
				goodsNumHql+=" and goodsnumber=?";
				goodsNumObj.add(finbg.getGoodsnumber());
				sql.add(goodsNumHql);
				obj.add(goodsNumObj.toArray());
			}
		}
		for (Map.Entry<String, GoodsBills> entry : goodsIdMap.entrySet()) {
			List<Object> goodsNumObj = new ArrayList<Object>();
			String hql1=" from Inventory where goodsID=? and warehouse=? and area =? and frame = ? and position = ?";
			String hql2 = "from ProductPackaging where goodsID = ?";
			ProductPackaging productPackaging = (ProductPackaging)baseBeanService.getBeanByHqlAndParams(hql2,new Object[]{ entry.getValue().getGoodsID()});
			Inventory   inventory=(Inventory)baseBeanService.getBeanByHqlAndParams(hql1,
					new Object[]{entry.getValue().getGoodsID(),finbill.getDrdepotID(),finbill.getArea(),finbill.getFrame(),finbill.getPosition()});
			if(inventory==null){
				Inventory  inven=new Inventory();
				inven.setInventoryID(serverService.getServerID("inventory"));
				inven.setCompanyID(finbill.getDrccompanyID());//根据公司区分（调入机构）
				inven.setGroupCompanySn(groupCompanySn);
				inven.setOrganizationID(organizationID);
				inven.setDepartmentID(organizationID);
				//仓库
				inven.setWarehouse(finbill.getDrdepotID());
				inven.setWarehouseName(finbill.getDrdepotName());
				inven.setArea(finbill.getArea());
				inven.setAreaName(finbill.getAreaName());
				inven.setFrame(finbill.getFrame());
				inven.setFrameName(finbill.getFrameName());
				inven.setPosition(finbill.getPosition());
				inven.setPositionName(finbill.getPositionName());
				//位置
				inven.setWeizhi(finbill.getDrdepotName());
				inven.setGoodsID(entry.getValue().getGoodsID());
				inven.setProductId(productPackaging!=null?productPackaging.getPpID():"");
				inven.setGoodsType(entry.getValue().getTypeID());//物品类别
				inven.setGoodsName(entry.getValue().getGoodsName());
				inven.setBarcode(entry.getValue().getSortCode());//条码
				inven.setStandard(entry.getValue().getStandard());//品牌规格
				inven.setGoodsCoding(entry.getValue().getGoodsNum());//品名编号
				inven.setBadQuantity("0");
				inven.setUnit(entry.getValue().getGoodsVariableID());//单位
				inven.setUnitPrice(String.valueOf(Double.parseDouble(entry.getValue().getPrice())));//单价
				inven.setInvenQuantity(entry.getValue().getQuantity());//商品数量
				inven.setGoodstatus("00");
				//计算入库总金额
				double a=Double.parseDouble(entry.getValue().getPrice());//单价
				double b=Double.parseDouble(entry.getValue().getQuantity());//数量
				inven.setSumPrice(String.valueOf(a*b));
				inven.setSubjectsID(entry.getValue().getSubjectsID());//科目
				inven.setSubjectsName(entry.getValue().getSubjectsName());
				baseBeanList.add(inven);
				strInventoryID = inven.getInventoryID();//存入库存表业务主键
				invenQuantity=inven.getInvenQuantity();//库存
			}else{
				int  invenQuant=Integer.parseInt(inventory.getInvenQuantity());//库存商品数量
				int  qualify=Integer.parseInt(entry.getValue().getQuantity());//数量
				double unitprice=Double.parseDouble(entry.getValue().getPrice());//单价
				double  amount=unitprice*qualify;//收入商品总金额
				double  sumPrice=Double.parseDouble(inventory.getSumPrice());//库存商品总金额
				double  averagePrice=(sumPrice+amount)/(invenQuant+qualify);//移动加权平均单价
				BigDecimal   b   =   new   BigDecimal(averagePrice);//转换类型
				double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();//四舍五入后保留小数点后俩位
				int sum=invenQuant+qualify;//入库后库存商品总数量
				double sumPrice1=f1*sum;//入库后库存商品总金额
				entry.getValue().setDepotID(inventory.getInventoryID());
				inventory.setSumPrice(String.valueOf(sumPrice1));
				inventory.setUnitPrice(String.valueOf(f1));
				inventory.setInvenQuantity(String.valueOf(sum));
				baseBeanList.add(inventory);
				strInventoryID = inventory.getInventoryID();//存入库存表业务主键
				invenQuantity=inventory.getInvenQuantity();//库存
			}

			stockInv stockinvs=new stockInv();
			stockinvs.setStockinvID(serverService.getServerID("stockInv"));
			stockinvs.setCompanyID(finbill.getDrccompanyID());
			stockinvs.setGroupCompanySn(groupCompanySn);
			stockinvs.setGoodsID(entry.getValue().getGoodsID());
			stockinvs.setGoodsType(entry.getValue().getTypeID());
			stockinvs.setGoodsName(entry.getValue().getGoodsName());
			stockinvs.setInvenQuantity(invenQuantity);
			stockinvs.setWarehouse(finbill.getDepotID());
			stockinvs.setWarehouseName(finbill.getDepotName());
			stockinvs.setStaffID(sta.getStaffID());//使用人id
			stockinvs.setStaffName(sta.getStaffName());//使用人name
			double  unitPrice=Double.parseDouble(entry.getValue().getPrice());//商品单价
			stockinvs.setSummoney(String.valueOf(Integer.parseInt(invenQuantity)*unitPrice));//库存商品总金额
			stockinvs.setIntime(new Date());
			stockinvs.setType("00");//入库
			baseBeanList.add(stockinvs);

			//每个物品生成序列号
			//dd(baseBeanList,strInventoryID,null,null,finbg.getGoodsID(),finbill.getDrccompanyID(),"03",true,Integer.parseInt(invenQuantity),null);
		}

		String[] sss=new String[sql.size()];
		parameter = "调拨入库单确认入库（凭证号" + financialBill.getJournalNum() + "）";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.executeSqlsByParmsList(baseBeanList, sql.toArray(sss), obj);			
		
		financialBill.setBillsType("调拨入库单");
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
		search="search";
		return toAllocationStorageList();
	}
	/**
	 * 本方法从SplitBillAction类中拷贝部分而来，作用是在入库时，对每一个入库的物品生成序列号（GoodsNum对象记录），存放到数据对象的List中(baseBeanList).
	 * @param baseBeanList  
	 * @param Scb  cashierBillsID (null)
	 * @param Sgb  goodsBillsID
	 * @param Sgb2 goodsBillsID (null)
	 * @param Sg  goodsID
	 * @param Sc  companyID
	 * @param Ss status
	 * @param Sn True
	 * @param quantity 物品数量
	 * @param num 序列号 (null)
	 * @see hy.ea.finance.action.SplitBillAction
	 * dd(baseBeanList,"","","",goods.getGoodsID(),account.getCompanyID(),"02",true,inventoryParam.getInvenQuantity(),null);
	 */
	private void dd(List<BaseBean> baseBeanList,String Scb,String Sgb,String Sgb2,String Sg,String Sc,String Ss,Boolean Sn,int quantity,String num){
		GoodsNum goodsNum=null;
		String num3="";
		Object gval=null;
		if(Sn){
			goodsNum=new GoodsNum();
			goodsNum.setGnID(serverService.getServerID("goodsnum"));
			goodsNum.setGnPID(goodsNum.getGnID());//父id
			goodsNum.setCashierBillsID(Scb);//单据id(库存表业务id)
			goodsNum.setGoodsBillsID(Sgb);//物品单据id
			goodsNum.setGoodsID(Sg);
			goodsNum.setCompanyID(Sc);
			goodsNum.setPhaseDate(new Date());
			goodsNum.setStatus(Ss);
			GoodsManage goodsManage=(GoodsManage)baseBeanService.getBeanByHqlAndParams("from GoodsManage where goodsID=?", new Object[]{Sg});
			goodsNum.setGoodsCoding(goodsManage.getGoodsCoding());
			if(num!=null&&!num.equals("")){
				num3=Integer.toString(Integer.parseInt(num) + 1);
			}else{
				gval=baseBeanService.getObjectBySqlAndParams("select max(goodsnumber) from dtgoodsnum", null);
				if (gval == null) {
					num3 = "0001";
				} else {
					num3 = Integer.toString(Integer.parseInt(gval.toString()) + 1);
				}
			}
			if (gval != null||num3!=null) {
				int num1=num3.length();
				for(int i=0;i<4-num1;i++){
					num3="0"+num3;
				}
			}
			goodsNum.setGoodsnumber(num3);
			baseBeanList.add(goodsNum);
		}
				
		if(quantity>1){
			dd(baseBeanList,Scb,Sgb,Sgb2,Sg,Sc,Ss,Sn,quantity-1,num3);
		}
	}
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public FinancialBill getFinancialBill() {
		return financialBill;
	}

	public void setFinancialBill(FinancialBill financialBill) {
		this.financialBill = financialBill;
	}

	public FinancialBillsGood getFinancialBillsGood() {
		return financialBillsGood;
	}

	public void setFinancialBillsGood(FinancialBillsGood financialBillsGood) {
		this.financialBillsGood = financialBillsGood;
	}
	public CashierBills getCashierBills() {
		return cashierBills;
	}
	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	public Map<String, GoodsBills> getGoodsmap() {
		return goodsmap;
	}
	public void setGoodsmap(Map<String, GoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
	}
	public Map<String, GoodsBills> getGoodsmapold() {
		return goodsmapold;
	}
	public void setGoodsmapold(Map<String, GoodsBills> goodsmapold) {
		this.goodsmapold = goodsmapold;
	}
	public List<CCode> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public List<BaseBean> getWarehouseList() {
		return warehouseList;
	}

	public void setWarehouseList(List<BaseBean> warehouseList) {
		this.warehouseList = warehouseList;
	}

	public List<FinancialBillsGood> getBillsGoodList() {
		return BillsGoodList;
	}

	public void setBillsGoodList(List<FinancialBillsGood> billsGoodList) {
		BillsGoodList = billsGoodList;
	}

	public ContactUser getContactUser1() {
		return contactUser1;
	}

	public void setContactUser1(ContactUser contactUser1) {
		this.contactUser1 = contactUser1;
	}

	public ContactCompanyView getContactCompanyView1() {
		return contactCompanyView1;
	}

	public void setContactCompanyView1(ContactCompanyView contactCompanyView1) {
		this.contactCompanyView1 = contactCompanyView1;
	}

	public Inventory getInventoryParam() {
		return inventoryParam;
	}

	public void setInventoryParam(Inventory inventoryParam) {
		this.inventoryParam = inventoryParam;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public stockInv getStockinv() {
		return stockinv;
	}

	public void setStockinv(stockInv stockinv) {
		this.stockinv = stockinv;
	}

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public int getCamount() {
		return camount;
	}
	public void setCamount(int camount) {
		this.camount = camount;
	}
	public ContactCompany getContactCompanyView() {
		return contactCompanyView;
	}
	public void setContactCompanyView(ContactCompany contactCompanyView) {
		this.contactCompanyView = contactCompanyView;
	}
	public List<BaseBean> getBillsgoodlist() {
		return billsgoodlist;
	}
	public void setBillsgoodlist(List<BaseBean> billsgoodlist) {
		this.billsgoodlist = billsgoodlist;
	}
	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}
	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<BaseBean> getBillCheckList() {
		return BillCheckList;
	}
	public void setBillCheckList(List<BaseBean> billCheckList) {
		BillCheckList = billCheckList;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLoginstaffcheck() {
		return loginstaffcheck;
	}
	public void setLoginstaffcheck(int loginstaffcheck) {
		this.loginstaffcheck = loginstaffcheck;
	}
	public Map<String, String> getBillcheckmap() {
		return billcheckmap;
	}
	public void setBillcheckmap(Map<String, String> billcheckmap) {
		this.billcheckmap = billcheckmap;
	}
	public String getDeptpost() {
		return deptpost;
	}
	public void setDeptpost(String deptpost) {
		this.deptpost = deptpost;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
