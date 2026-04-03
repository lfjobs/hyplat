package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.finance.BillCheck;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillVO;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.omg.CORBA.Request;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 进销存  PurchaseAction
 * @author
 *
 */
@Controller
@Scope("prototype")
public class PurchaseAction1 {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	public InputStream excelStream;
	private FinancialBill financialBill;  //进销存单据表(采购单据表)
	private FinancialBillsGood financialBillsGood;   //进销存子类(物品单据表)
	private FinancialBillVO financialBillVO;	//进销存查询条件类(单据+物品)
	//采购出纳单获取
	private CashierBillsVO cashierBillsVO;//出纳单的视图（用于采购单获取相关数据）
	private CashierBills cashierBills;//出纳单表
	private GoodsBills goodsbills;//出纳单子表
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private Map<String, FinancialBillsGood> goodsmap;
	private Map<String, GoodsBills> goodsbillmap;
	private Map<String,List<BaseBean>> billgoodsmap;
	private Map<String, String> billcheckmap;
	private List<CCode> payTypeList;
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;
	private List<CCode> codeRelationList;
	private List<CCode> typelist;//仓库管理
	private String companyname;
	private String organizationname;
	private String str;//选择验货的物品id

	private String type;		// '00' 采购订单  '01' 收货管理  '02' 入库管理 '03' 出库管理 '04' 市场调查 '05'盘库单

	/**
	 * 仓库列表中--库列表
	 */
	private List<BaseBean> warehouseList;

	private List<BaseBean> billsgoodlist;//采购入库物品集合

	private List<FinancialBillsGood> BillsGoodList;

	private String message;

	private Staff contactUser;

	private ContactUser contactUser1;

	private ContactCompany contactCompanyView;

	private ContactCompanyView contactCompanyView1;

	private Inventory   inventoryParam;

	private String sdate;

	private String edate;

	private String result;

	private String typeID;

	private String print;

	private int camount;
	private String xmtype;

	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	/**
	 * 验货保存（采购单据业务id）
	 */
	private String financialbillID;
	//区分是保存草稿还是提交审核参数
	private String subtype;
	private String deptpost;//单据审核的职务
	private String remarks;
	private int loginstaffcheck;//单据审核人中有没有当前登录人参数
	/**
	 * 采购审核信息
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
		Object[] params = { cashierBillsVO.getCashierBillsID()};
		String hql = "from CashierBills where cashierBillsID= ?";
		CashierBills cashier= (CashierBills) baseBeanService.getBeanByHqlAndParams(
				hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("getstatus", cashier.getStatus());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 根据黏贴单编号查询出纳单列表
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getListcashierByjournalNum() throws UnsupportedEncodingException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		DetachedCriteria dc = DetachedCriteria
				.forClass(CashierBillsVO.class);
		dc.add(Restrictions.eq("groupCompanySn", groupCompanySn));
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", organizationID));

		dc.add(Restrictions.eq("billsType", "预入款单"));
		dc.add(Restrictions.or(Restrictions.eq("status", "07"), Restrictions.eq("status", "45")));
		dc.add(Restrictions.or(Restrictions.eq("statusbill", "00"), Restrictions.eq("statusbill", "02")));
		//黏贴单号查询
		if(!(cashierBillsVO.getJournalNum()).equals("")){
			dc.add(Restrictions.like("journalNum", cashierBillsVO.getJournalNum(), MatchMode.ANYWHERE));
		}
		dc.addOrder(Order.desc("cashierDate"));
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), dc);
		map.put("pageForm", pageForm);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出  
		JSONObject obj = JSONObject.fromObject(map,jsonConfig);
		result = obj.toString();
		return "success";
	}
	/**
	 * 根据出纳单id获取相应物品信息
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getListcashierByID() throws UnsupportedEncodingException {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { cashierBillsVO.getCashierBillsID()};
		String hql = "from GoodsCashierBillsVO where cashierBillsID= ? order by goodsNomber";
		List<BaseBean> goodsList = baseBeanService.getListBeanByHqlAndParams(
				hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("goodsList", goodsList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 审核信息的保存
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unused")
	public String saveBillCheck() throws UnsupportedEncodingException {
		HttpServletRequest re=ServletActionContext.getRequest();
		String status=re.getParameter("type");
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
		CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[] { cashierBills.getCashierBillsID() });
		//修改对应物品的状态为驳回      --  03=驳回
		if("03".equals(status)){
			String[] hql={"update CashierBills set status=10 where cashierBillsID=?"};
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql,new Object[]{cashierBills.getCashierBillsID()});
		}
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
		billcheck.setAuditorstatus("02".equals(status)?"02":"03"); // 审核状态 '01'未审核 '02'已审核 '03' 驳回
		billcheck.setDeptpost(deptpost);//职务；
		billcheck.setRemark(remarks);//备注
		billcheck.setCashierDate(new Date());// 单据日期
		baseBeanList.add(billcheck);
		parameter = "添加审核记录（" + billcheck.getJournalNum() + "）";
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginstaff", loginstaffcheck);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**********************************************************采购订单********************************************************/

	/**
	 * 采购订单——条件查询(保存条件)
	 *
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("cashierBillsVO",
				cashierBillsVO);
		return getPurchaseList();
	}

	/**
	 * 采购订单——列表
	 *
	 * @return
	 */
	public String getPurchaseList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		//String organizationID = (String) session.get("organizationID");
		List<Object> list = getCashierBillList();
		String hql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);

		//查询获得数据中的责任人（用于查询使用）
		//String staffhql = "from Staff s where exists ( select staffID from FinancialBill c where c.staffID=s.staffID and c.companyID=? and c.organizationID=? and c.billStatus=? and c.billsType=?)";
		//stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, new Object[]{account.getCompanyID(),organizationID,"22","采购单"});
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, parms);
		return "list";
	}
	/**
	 * 查询列表（可根据条件查询）被调用
	 *
	 * @return
	 */
	public List<Object> getCashierBillList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String Hql = " from CashierBillsVO";
		Hql += " where 1=1";
		Hql+=" and groupCompanySn=?";
		parms.add(groupCompanySn);
		Hql += " and companyID=?";
		parms.add(account.getCompanyID());
		Hql += " and organizationID=?";
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			CashierBillsVO  cashierbillsvo=(CashierBillsVO)session.get("cashierBillsVO");
			if (cashierbillsvo.getJournalNum() != null
					&& !cashierbillsvo.getJournalNum().trim().equals("")) {
				Hql += " and journalNum like ? ";
				parms.add("%" + cashierbillsvo.getJournalNum().trim() + "%");
			}
			if (cashierbillsvo.getInputName() != null
					&& !cashierbillsvo.getInputName().equals("")) {
				Hql += " and inputName = ? ";
				parms.add(cashierbillsvo.getInputName());
			}
			if (sdate != null && edate != null && !"".equals(sdate)
					&& !"".equals(edate)) {
				Hql += " and cashierDate between ? and ?";
				parms.add(Utilities.getDateFromString(sdate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
				parms.add(Utilities.getDateFromString(edate+" 24:00:00", "yyyy-MM-dd HH:mm:ss"));
			}
			if (cashierbillsvo.getStatus()!= null
					&& !cashierbillsvo.getStatus().equals("")) {
				Hql += " and status = ? ";
				parms.add(cashierbillsvo.getStatus());
			}
			if(type != null){
				Hql += " and billsType=?";
				parms.add("采购单");
			}
		}
		if(search==null||search.equals("")){
			if(type!=null&&!type.equals("")){
				Hql += " and billsType=?";
				parms.add("采购单");
				if(type.equals("00"))
				{

				}else if(type.equals("01")){
					Hql += " and status=?";
					parms.add("05");//审核中
				}
			}
		}
		if(xmtype!=null&&!"".endsWith(xmtype)){
			Hql+=" and xmtype like ?";
			parms.add(xmtype+"%");
		}
		Hql += " order by cashierDate desc";
		result.add(Hql);
		result.add(parms.toArray());
		return result;
	}

	/**
	 * 采购订单——保存
	 * @throws ParseException
	 */
	public String savePurchase() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if(groupCompanySn==null){
			return "login";
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		String staffhql2 = "from Staff where staffID = ?";
		Staff staff2 = (Staff) baseBeanService.getBeanByHqlAndParams(staffhql2,new Object[] { account.getStaffID() });
		//更改关联票据的状态为21:库存已关联单据
		String hqlcash = "from CashierBills where cashierBillsID = ?";
		CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[] { cashierBills.getCashierBillsID() });
		cbills.setStatus("21");//库存已关联单据
		baseBeanList.add(cbills);
		//单据数据保存
		CashierBills cashierbill=new CashierBills();
		cashierbill.setCashierBillsID(serverService.getServerID("cashierTally"));
		cashierbill.setGroupCompanySn(cbills.getGroupCompanySn());
		cashierbill.setCompanyID(cbills.getCompanyID());
		cashierbill.setCompanyName(cbills.getCompanyName());
		cashierbill.setOrganizationID(cbills.getOrganizationID());
		cashierbill.setDepartmentID(cbills.getDepartmentID());
		cashierbill.setDepartmentName(cbills.getDepartmentName());
		cashierbill.setCompanyBankNum(cbills.getCompanyBankNum());
		cashierbill.setPcompanyID(cbills.getPcompanyID());
		cashierbill.setPcompanyName(cbills.getPcompanyName());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		cashierbill.setCashierDate(date);// 制单日期
		cashierbill.setBillsType("采购单");// 单据类型
		cashierbill.setJournalNum(financialBill.getJournalNum());// 凭证号(单据编号)
		/******责任人******/
		cashierbill.setStaffID(financialBill.getStaffID());// 责任人ID（采购员）
		String staffhql1 = "from Staff where staffID = ?";
		Staff staff1 = (Staff) baseBeanService.getBeanByHqlAndParams(staffhql1,new Object[] {financialBill.getStaffID()});
		cashierbill.setStaffName(staff1.getStaffName());//责任人name
		cashierbill.setStaffCode(staff1.getStaffCode());// 责任人编号
		/*******制单人*********/
		cashierbill.setInputid(staff2.getStaffID());//制单人员id
		cashierbill.setInputName(staff2.getStaffName());//制单人名称
		cashierbill.setInputCompanyid(account.getCompanyID());//制单人公司id
		cashierbill.setInputCompanyName(account.getCompanyName());//制单人公司名称
		String coshql = "from COS where staffID = ?";
		COS cos = (COS) baseBeanService.getBeanByHqlAndParams(coshql,new Object[] { account.getStaffID()});
		cashierbill.setInputOrganizationID(cos.getOrganizationID());//制单人部门id
		String corhql = "from COrganization where organizationID = ?";
		COrganization cor = (COrganization) baseBeanService.getBeanByHqlAndParams(corhql,new Object[] { cos.getOrganizationID()});
		cashierbill.setInputOrganizationName(cor.getOrganizationName());//制单人部门名称
		//保存草稿状态
		if(subtype.equals("save")){
			cashierbill.setStatus("22");//草稿
		}else if(subtype.equals("savecheck")){
			cashierbill.setStatus("05");//审核中
		}
		//项目信息
		cashierbill.setProID(cbills.getProID());//项目单据id
		cashierbill.setProjectName(cbills.getProjectName());//项目名称
		cashierbill.setXmtype(cbills.getXmtype());//项目类型
		cashierbill.setXmtypename(cbills.getXmtypename());//项目类型名称
		cashierbill.setProjectCode(cbills.getProjectCode());//凭证号（项目编号）
		cashierbill.setContent(cbills.getContent());//项目内容
		baseBeanList.add(cashierbill);

		financialBill.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加采购单据（单据编号" + financialBill.getJournalNum() + "）";
		financialBill.setCashierBillsID(cashierbill.getCashierBillsID());//父单据外键
		financialBill.setOrganizationID(organizationID);
		financialBill.setDepartmentID(organizationID);
		financialBill.setCompanyID(account.getCompanyID());
		financialBill.setGroupCompanySn(groupCompanySn);
		financialBill.setBillsType("采购单");
		financialBill.setBillsDate(new Date());
		financialBill.setBillStaffName(staff2.getStaffName());
		financialBill.setBillStaffID(account.getStaffID());
		//保存22:草稿状态
		if(subtype.equals("save")){
			financialBill.setBillStatus("22");
		}else if(subtype.equals("savecheck")){
			financialBill.setBillStatus("05");
		}
		baseBeanList.add(financialBill);
		if (goodsbillmap != null) {
			for (GoodsBills goods : goodsbillmap.values()) {
				goods.setCashierBillsID(cashierbill.getCashierBillsID());
				goods.setGoodsBillsID(serverService.getServerID("goodsbills"));
				String hql = "from GoodsManage g where g.goodsID=?";
				GoodsManage goodsManage = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goods
								.getGoodsID() });
				goods.setCompanyID(account.getCompanyID());
				goods.setGoodsName(goodsManage.getGoodsName());
				goods.setGoodsNum(goodsManage.getGoodsCoding());//品名编号
				goods.setTypeID(goodsManage.getTypeID());
				//goods.setBatchNumber(batchNumber);// 批号或期号
				goods.setStandard(goodsManage.getStandard());//品牌规格
				goods.setSortCode(goodsManage.getDefaultStorage());//统一分类条码

				if(goodsManage.getSubjectsID()!=null ){
					goods.setSubjectsID(goodsManage.getSubjectsID());
					goods.setSubjectsName(goodsManage.getSubjectsName());
				}
				goods.setKcStatus("00");//状态
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		return "success";
	}
	/**
	 * 采购订单——修改
	 * @throws ParseException
	 */
	public String editPurchase() throws ParseException {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if(groupCompanySn==null){
			return "login";
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		//采购单据父表
		String hqlcashb = "from CashierBills where cashierBillsID = ?";
		CashierBills cashierbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcashb,new Object[] { cashierBills.getCashierBillsID() });
		if(subtype.equals("save")){
			cashierbills.setStatus("22");//草稿
		}else if(subtype.equals("savecheck")){
			cashierbills.setStatus("05");//审核中
		}
		baseBeanList.add(cashierbills);
		//修改的采购单据子表
		String hqlcash = "from FinancialBill where cashierBillsID = ?";
		FinancialBill fiBill = (FinancialBill) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[] { cashierBills.getCashierBillsID() });
		parameter = "修改采购单据（单据编号" + fiBill.getJournalNum() + "）";
		fiBill.setCcompanyID(financialBill.getCcompanyID());
		fiBill.setCcompanyName(financialBill.getCcompanyName());
		fiBill.setCcompanyTel(financialBill.getCcompanyTel());
		fiBill.setFrontMoney(financialBill.getFrontMoney());
		fiBill.setGoodsmoney(financialBill.getGoodsmoney());
		fiBill.setLogisticsType(financialBill.getLogisticsType());
		fiBill.setMoneyType(financialBill.getMoneyType());
		fiBill.setPaymentType(financialBill.getPaymentType());
		fiBill.setPurchaseDate(financialBill.getPurchaseDate());
		fiBill.setStaffID(financialBill.getStaffID());
		fiBill.setStaffName(financialBill.getStaffName());
		fiBill.setStaffPhone(financialBill.getStaffPhone());
		fiBill.setSubjectID(financialBill.getSubjectID());
		fiBill.setSubjectName(financialBill.getSubjectName());
		if(subtype.equals("save")){
			fiBill.setBillStatus("22");
		}else if(subtype.equals("savecheck")){
			fiBill.setBillStatus("05");
		}
		baseBeanList.add(fiBill);
		//删除物品
		String hqlgb = "delete from GoodsBills where cashierBillsID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqlgb}, new Object[] { cashierBills.getCashierBillsID()});
		if (goodsbillmap != null) {
			for (GoodsBills goods : goodsbillmap.values()) {
				goods.setCashierBillsID(cashierBills.getCashierBillsID());
				goods.setGoodsBillsID(serverService.getServerID("goodsbills"));
				String hql = "from GoodsManage g where g.goodsID=?";
				GoodsManage goodsManage = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goods
								.getGoodsID() });
				goods.setCompanyID(account.getCompanyID());
				goods.setGoodsName(goodsManage.getGoodsName());
				goods.setGoodsNum(goodsManage.getGoodsCoding());//品名编号
				goods.setTypeID(goodsManage.getTypeID());
				//goods.setBatchNumber(batchNumber);// 批号或期号
				goods.setStandard(goodsManage.getStandard());//品牌规格
				goods.setSortCode(goodsManage.getDefaultStorage());//统一分类条码

				if(goodsManage.getSubjectsID()!=null ){
					goods.setSubjectsID(goodsManage.getSubjectsID());
					goods.setSubjectsName(goodsManage.getSubjectsName());
				}
				goods.setKcStatus("00");//状态
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,null);
		return "success";
	}
	/**
	 * 采购订单——添加、修改页面
	 *
	 */
	public String toSavePurchase() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		//责任人name
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		session.put("ManStaffName", sta.getStaffName()+"---"+sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		String companyID = account.getCompanyID();
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		if(cashierBills!=null)
		{
			//单据父表
			String hql="from CashierBills where groupCompanySn = ? and companyID = ? and organizationID=? and cashierBillsID=?";
			cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,cashierBills.getCashierBillsID()});
			//单据子表
			String hqlf="from FinancialBill where cashierBillsID=?";
			financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hqlf, new Object[]{cashierBills.getCashierBillsID()});
			String hqlg="from GoodsBills where cashierBillsID=? order by goodsNomber";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
							.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hqlg, new Object[]{cashierBills.getCashierBillsID()});
			type="edit";
		}
		else{type="add";}
		return "add";
	}

	/**
	 * 采购订单——查看
	 * @return
	 */
	public String toPurchase(){
		toSee();
		return "edit";
	}
	/**
	 * 采购订单——打印单据
	 */
	public String toPrintPurchase() {
		toSee();
		return "printpurchase";
	}
/**********************************************************验货管理开始********************************************************/
	/**
	 * 获取所有已收货的物品
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getInspectListSize() throws UnsupportedEncodingException {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<Object> querylist = queryInspectList();
		@SuppressWarnings("rawtypes")
		List res=(List) querylist.get(0);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inspectlistsize", res.size());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	/**
	 * 验货管理——条件查询(保存条件)
	 *
	 * @return
	 */
	public String toinspectSearch() {
		ActionContext.getContext().getSession().put("goodsbills",
				goodsbills);
		return getinspectList();
	}

	/**
	 * 验货管理——列表
	 *
	 * @return
	 */
	public String getinspectList() {
		List<Object> list = getInspectList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		if(print!=null&&"print".equals("print")){
			return "inspectlistBill";
		}
		return "inspectlist";
	}

	/**
	 * 验货管理——查询列表（可根据条件查询）被调用
	 * @return
	 */
	public List<Object> getInspectList() {
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		StringBuilder sql=new StringBuilder("select c.cashierbillsid,c.journalnum,c.companyName,f.staffName staff,");
		sql.append("c.departmentName,f.paymentType,f.staffsName,c.cashierDate,c.status,c.projectName");
		StringBuilder sql2=new StringBuilder();
		sql2.append(" from dtcashierbills c left join dt_inv_fb f on c.cashierbillsid=f.cashierbillsid");
        sql2.append(" where 1=1");
		if(cashierBills!=null){
			if(!"".equals(cashierBills.getJournalNum())&&cashierBills.getJournalNum()!=null){
				sql2.append(" and c.journalNum like ?");
                parms.add("%"+cashierBills.getJournalNum()+"%");
			}
            if(!"".equals(cashierBills.getProjectName())&&cashierBills.getProjectName()!=null){
                sql2.append(" and c.projectName like ?");
                parms.add("%"+cashierBills.getProjectName()+"%");
            }
            if(!"".equals(cashierBills.getStatus())&&cashierBills.getStatus()!=null){
                sql2.append(" and c.status = ?");
                parms.add(cashierBills.getStatus());
            }
		}else{
            sql2.append(" and c.status=?");
            parms.add("13");
        }
        sql2.append(" and f.organizationID=?");
        parms.add(organizationID);
        sql2.append(" and f.companyID = ? ");
        parms.add(account.getCompanyID());
		sql2.append(" order by c.cashierdate desc");

		result.add(sql.append(sql2).toString());
		result.add(parms.toArray());
		return result;
	}
	/**
	 * 验货管理——验货单页面
	 * @return
	 */
	public String toInspectAddDan(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}

        if(print!=null&&print.equals("danframe")){
            return "toinspectdanframe";
        }

		//当前登录人员信息
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });


		session.put("ManStaffName1", sta.getStaffName()+"---"+sta.getStaffCode());
		session.put("ManStaffName", sta.getStaffName());
		session.put("ManStaffCode", sta.getStaffCode());
		session.put("ManStaffId", sta.getStaffID());
		/*String journalNum="";
		//获取所有已收货的物品
		if(str==null){
			List<Object> querylist = queryInspectList();
			@SuppressWarnings("rawtypes")
			List res=(List) querylist.get(0);
			String strnum="";
			for(int f=0;f<res.size();f++){
				Object obj= res.get(f);
				Object[] os = (Object[])obj;
				String financialgood=(String) os[0];//物品id
				String journalnum=(String) os[1];//单据编号
				strnum+=financialgood+"-"+journalnum+",";
			}
			str=strnum;
		}
		if (str != null && !"".equals(str)) {
			Set<String>  s= new  HashSet<String>();
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			for (int i = 0; i < strs.length; i++) {
				String a=strs[i].trim();
				s.add(a.split("-")[1]);
			}
			Iterator<String> it = s.iterator();
			billgoodsmap=new HashMap<String, List<BaseBean>>();
			for(;it.hasNext();){
				String s1=it.next();
				journalNum+="'"+s1+"'"+",";
				List<BaseBean> list=new ArrayList<BaseBean>();
				for (int j = 0; j < strs.length; j++) {
					String s2=strs[j].trim().split("-")[1];
					String goodid=strs[j].trim().split("-")[0];
					if(s1.equals(s2))
					{
						GoodsBills goods= (GoodsBills) baseBeanService
								.getBeanByHqlAndParams(
										"from GoodsBills where goodsBillsID=?",
										new Object[] {goodid});
						list.add(goods);
					}
					billgoodsmap.put(s1,list);
				}
			}
		}
		if(journalNum.equals("")){
			getinspectList();
			return "inspectlist";
		}
		String nums= journalNum.substring(0, journalNum.lastIndexOf(","));
		//单据父表
		String  hql="from FinancialBill c where c.journalNum in("+nums+")";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 1 : pageNumber), hql,
				null);*/
        String  hql="from FinancialBill c where c.cashierBillsID =?";
        financialBill=(FinancialBill) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{financialbillID});
        List<BaseBean> goodsList= baseBeanService
                .getListBeanByHqlAndParams(
                        "from GoodsBills where cashierBillsID=?",
                        new Object[] {financialBill.getCashierBillsID()});
        billgoodsmap=new HashMap<String, List<BaseBean>>();
        billgoodsmap.put(financialBill.getJournalNum(),goodsList);
		return "toinspectdan";
	}
	/**
	 * 查询所有已收货的物品
	 */
	@SuppressWarnings("unchecked")
	public List<Object> queryInspectList(){
		List<Object> result = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql = "select g.goodsBillsID,f.journalNum"
				+ " from dtGoodsBills g"
				+ " left join dtCashierBills f on g.cashierBillsID=f.cashierBillsID";
		sql += " where f.groupCompanySn = ?";
		sql += " and f.companyid = ? ";
		sql += " and f.organizationID=?";
		//已收货
		sql+=" and f.status=13";
		//已收货和入库失败
		sql+=" and (g.kcStatus=13";
		sql+=" or g.kcStatus=23)";
		Object[] params = {groupCompanySn,account.getCompanyID(),organizationID};
		List<BaseBean> listysh=new ArrayList<BaseBean>();
		listysh=baseBeanService.getListBeanBySqlAndParams(sql,params);
		result.add(listysh);
		return result;
	}
	/**
	 * 验货管理——验货单提交审核/合格入库
	 * @return
	 * @throws ParseException
	 */
	public String toUpdateIsQualify() throws ParseException {
		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String groupCompanySn = session.get("groupCompanySn").toString();
			if(groupCompanySn==null){
				return "login";
			}
			//采购单状态改变
			List<BaseBean> list1=new ArrayList<BaseBean>();
			String hql = "from FinancialBill where financialbillID = ?";
			FinancialBill  finbill= (FinancialBill) baseBeanService.getBeanByHqlAndParams(hql,new Object[] {financialbillID});
			finbill.setBillStatus("14");//已验货
			String hqlcash = "from CashierBills where cashierBillsID = ?";
			CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,new Object[] { finbill.getCashierBillsID() });
			cbills.setStatus("14");//已验货
			baseBeanList.add(finbill);
			baseBeanList.add(cbills);

			String hqlForMan = "from Staff where staffID = ?";
			Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,new Object[] { account.getStaffID() });
			CashierBills cashierbill=new CashierBills();
			cashierbill.setCashierBillsID(serverService.getServerID("cashierTally"));
			cashierbill.setGroupCompanySn(cbills.getGroupCompanySn());
			cashierbill.setCompanyID(cbills.getCompanyID());
			cashierbill.setCompanyName(cbills.getCompanyName());
			cashierbill.setOrganizationID(cbills.getOrganizationID());
			cashierbill.setDepartmentID(cbills.getDepartmentID());
			cashierbill.setDepartmentName(cbills.getDepartmentName());
			cashierbill.setCompanyBankNum(cbills.getCompanyBankNum());
			cashierbill.setPcompanyID(cbills.getPcompanyID());
			cashierbill.setPcompanyName(cbills.getPcompanyName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss"));
			cashierbill.setCashierDate(date);// 制单日期
			cashierbill.setBillsType("验货单");// 单据类型
			cashierbill.setJournalNum(financialBill.getJournalNum());// 凭证号(单据编号)
			/******责任人******/
			cashierbill.setStaffID(financialBill.getStaffID());// 责任人ID（采购员）
			String staffhql1 = "from Staff where staffID = ?";
			Staff staff1 = (Staff) baseBeanService.getBeanByHqlAndParams(staffhql1,new Object[] {financialBill.getStaffID()});
			cashierbill.setStaffName(staff1.getStaffName());//责任人name
			cashierbill.setStaffCode(staff1.getStaffCode());// 责任人编号
			/*******制单人*********/
			cashierbill.setInputid(sta.getStaffID());//制单人员id
			cashierbill.setInputName(sta.getStaffName());//制单人名称
			cashierbill.setInputCompanyid(account.getCompanyID());//制单人公司id
			cashierbill.setInputCompanyName(account.getCompanyName());//制单人公司名称
			String coshql = "from COS where staffID = ?";
			COS cos = (COS) baseBeanService.getBeanByHqlAndParams(coshql,new Object[] { account.getStaffID()});
			//cashierbill.setInputOrganizationID(cos.getOrganizationID());//制单人部门id
			String corhql = "from COrganization where organizationID = ?";
			COrganization cor = (COrganization) baseBeanService.getBeanByHqlAndParams(corhql,new Object[] { cos.getOrganizationID()});
			cashierbill.setInputOrganizationName(cor.getOrganizationName());//制单人部门名称
			if(type!=null&&type.equals("hgrk")){
				cashierbill.setStatus("05");//审核中
			}else{
				cashierbill.setStatus("05");//审核中
			}
			//项目信息
			cashierbill.setProID(cbills.getProID());//项目单据id
			cashierbill.setProjectName(cbills.getProjectName());//项目名称
			cashierbill.setXmtype(cbills.getXmtype());//项目类型
			cashierbill.setXmtypename(cbills.getXmtypename());//项目类型名称
			cashierbill.setProjectCode(cbills.getProjectCode());//凭证号（项目编号）
			cashierbill.setContent(cbills.getContent());//项目内容
			baseBeanList.add(cashierbill);
			//验货单 子表
			String billnum=financialBill.getJournalNum();
			financialBill.setFinancialbillID(serverService.getServerID("financial"));
			financialBill.setCashierBillsID(cashierbill.getCashierBillsID());
			financialBill.setGroupCompanySn(cbills.getGroupCompanySn());
			parameter = "添加验货单据（单据编号" + billnum + "）";
			financialBill.setBillsType("验货单");
			financialBill.setBillsDate(new Date());
			financialBill.setBillStaffName(sta.getStaffName());
			financialBill.setBillStaffID(account.getStaffID());
			if(type!=null&&type.equals("hgrk")){
				financialBill.setBillStatus("05");//审核中
			}else{
				financialBill.setBillStatus("05");//审核中
			}
			baseBeanList.add(financialBill);
			String billid = "";
			if(type!=null&&type.equals("hgrk")){
				billid=financialBill.getFinancialbillID();//新增验货单业务id
				session.put("BillId", billid);
			}
			CLogBook logBook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			baseBeanList.add(logBook);

			//物品合格数保存
			String hqlu="";
			if (goodsbillmap != null){
				for (GoodsBills goods : goodsbillmap.values()) {
					//采购单更改物品
					GoodsBills goods1= (GoodsBills) baseBeanService
							.getBeanByHqlAndParams(
									"from GoodsBills where goodsBillsID=?",
									new Object[] {goods.getGoodsBillsID()});
					goods1.setIsQualify(goods.getIsQualify());
					goods1.setStorageQuantity(goods.getIsQualify());
					goods1.setKcStatus("14");
					baseBeanList.add(goods1);
					//新添加验货单物品
					GoodsBills newgood=new GoodsBills();
					newgood.setGoodsBillsID(serverService.getServerID("goodsbills"));
					newgood.setCashierBillsID(cashierbill.getCashierBillsID());
					newgood.setCompanyID(goods1.getCompanyID());
					newgood.setGoodsID(goods1.getGoodsID());
					newgood.setGoodsName(goods1.getGoodsName());
					newgood.setGoodsNomber(goods1.getGoodsNomber());
					newgood.setGoodsNum(goods1.getGoodsNum());
					newgood.setGoodsStatus(goods1.getGoodsStatus());
					newgood.setGoodstatus(goods1.getGoodstatus());
					newgood.setGoodsVariableID(goods1.getGoodsVariableID());
					newgood.setIdentifyingCode(goods1.getIdentifyingCode());
					newgood.setIsQualify(goods1.getIsQualify());
					newgood.setKcStatus(goods1.getKcStatus());
					newgood.setMoney(goods1.getMoney());
					newgood.setPrice(goods1.getPrice());
					newgood.setQuantity(goods1.getQuantity());
					newgood.setReQuantity(goods1.getReQuantity());
					newgood.setStandard(goods1.getStandard());
					newgood.setStorageQuantity(goods1.getStorageQuantity());
					newgood.setTypeID(goods1.getTypeID());
					newgood.setCcompanyID(goods1.getCcompanyID());
					newgood.setCcompanyName(goods1.getCcompanyName());
					baseBeanList.add(newgood);
					parameter = "员工："+account.getAccountName()+"验货（物品单据号：" + goods1.getGoodsBillsID() + "）";
					CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);
					baseBeanList.add(cLogBook);

				}
			}
			baseBeanService.executeHqlsByParamsList(baseBeanList, null, null);
			if(type!=null&&type.equals("hgrk")){
				String hqlhg="from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
				//子单据数据
				financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hqlhg, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,billid});
				//物品数据
				String hqlg="from GoodsBills where cashierBillsID=? order by goodsNomber";
				Object[] par = {financialBill.getCashierBillsID()};
				billsgoodlist=baseBeanService.getListBeanByHqlAndParams(hqlg, par);
				//责任人name
				String hqlMan = "from Staff where staffID = ?";
				Staff staf = (Staff) baseBeanService.getBeanByHqlAndParams(hqlMan,
						new Object[] { account.getStaffID() });
				session.put("ManStaffName", staf.getStaffName()+"---"+staf.getStaffCode());
				session.put("ManStaffId", staf.getStaffID());
				//选择仓库
				Object[] params = {account.getCompanyID()};
				typelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode20101014v5zed7cukk0000000004");
				pageForm = baseBeanService
						.getPageForm(
								(null != pageForm ? pageForm.getPageNumber() : 1),
								(pageNumber==0?10:pageNumber),
								" from DepotManage where companyID = ? and depotState='00'",
								params);
				connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
				return "toinvoicingstorage";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	/**********************************************************验货管理结束********************************************************/
	/**
	 * 收货管理——收货处理
	 *
	 * @return
	 */
	public String getUpdate(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=session.get("organizationID").toString();
		//String groupCompanySn = session.get("groupCompanySn").toString();
		List<BaseBean> listss=new ArrayList<BaseBean>();
		//单据子表对象
		String hqlfb="from FinancialBill where cashierBillsID=?";
		financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hqlfb,
				new Object[]{cashierBills.getCashierBillsID()});
		parameter = "员工："+account.getAccountName()+"收货（单据编号" + financialBill.getJournalNum() + "）";
		CLogBook cLogBook=logBookService.saveCLogBook(organizationID,
				parameter, account);
		listss.add(cLogBook);
		String hql = "from FinancialBill where financialbillID = ?";
		FinancialBill  finbill= (FinancialBill) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] {financialBill.getFinancialbillID()});
		finbill.setBillStatus("13");//已收货
		String hqlcash = "from CashierBills where cashierBillsID = ?";
		CashierBills cbills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlcash,
				new Object[] { finbill.getCashierBillsID() });
		cbills.setStatus("13");//已收货
		listss.add(finbill);
		listss.add(cbills);

		if (goodsmap != null) {
			List<Object[]> paramlist = new ArrayList<Object[]>();
			int i=0;
			String[] hqlg= new String[goodsmap.size()];
			for (FinancialBillsGood goods : goodsmap.values()) {
				//单据物品
				String hql3= "update GoodsBills set reQuantity=?,kcStatus=?,goodstatus=? where cashierBillsID=? and goodsID=?";
				hqlg[i] = hql3;
				Object[] param = new Object[] {
						goods.getReQuantity(),
						"13",
						"00",
						cashierBills.getCashierBillsID(),
						goods.getGoodsID(),
				};
				paramlist.add(param);
				i++;
			}
			baseBeanService.executeHqlsByParamsList(null, hqlg, paramlist);
		}
		baseBeanService.executeHqlsByParamsList(listss, null, null);//表单状态

		return "success";
	}

	/**
	 * 采购单/收货管理——查看调用
	 *
	 */
	private void toSee(){
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
		//单据父表
		String hql="from CashierBills where groupCompanySn = ? and companyID = ? and organizationID=? and cashierBillsID=?";
		cashierBills=(CashierBills)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,cashierBills.getCashierBillsID()});
		//单据子表
		String hqlf="from FinancialBill where cashierBillsID=?";
		financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hqlf,
				new Object[]{cashierBills.getCashierBillsID()});
		String hqlg="from GoodsBills where cashierBillsID=? order by goodsNomber";
		warehouseList = baseBeanService.getListBeanByHqlAndParams(hqlg, new Object[]{cashierBills.getCashierBillsID()});
		//计算物品总金额
		/*if(pageForm!=null){
		    int size = pageForm.getList().size();
		    GoodsBills goods=new GoodsBills();
			for(int i=0;i<size;i++) {
			    goods=(GoodsBills) pageForm.getList().get(i); 
			    int amount=Integer.parseInt(goods.getMoney());
			    camount+=amount;
		    }
		}*/
		//往来个人
		//String hql2 = " from Staff where staffID=? ";
		//contactUser=(Staff)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{financialBill.getCstaffID()});
		//供货商
		String hql3 = " from ContactCompany where ccompanyID=? ";
		contactCompanyView=(ContactCompany)baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{financialBill.getCcompanyID()});
		//公司名称
		String comhql = "from Company where companyID=?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(
				comhql, new Object[] { financialBill.getCompanyID() });
		companyname = company.getCompanyName();
		//部门名称
		String orghql = "from COrganization where companyID=? and organizationID=?";
		COrganization cOrganization = (COrganization) baseBeanService
				.getBeanByHqlAndParams(orghql, new Object[] {
						financialBill.getCompanyID(),
						financialBill.getOrganizationID()});
		organizationname = cOrganization.getOrganizationName();
		//审核信息
		BillCheck bCheck=(BillCheck)baseBeanService.getBeanByHqlAndParams("from BillCheck where newBillsID=?",
				new Object[]{cashierBills.getCashierBillsID()});
		if(bCheck!=null){
			BillCheckList=baseBeanService.getListBeanByHqlAndParams("from BillCheck where firstBillsID=? order by audittime desc",
					new Object[]{bCheck.getFirstBillsID()});
			loginstaffcheck=0;
			if(BillCheckList.size()>0){
				billcheckmap=new HashMap<String, String>();
				for(int i=0;i<BillCheckList.size();i++){
					BillCheck bc=(BillCheck) BillCheckList.get(i);
					if(bc.getDeptpost()!=null){
						billcheckmap.put(bc.getDeptpost(), bc.getAuditorname());
						billcheckmap.put(bc.getDeptpost()+"zt",bc.getAuditorstatus());
						if((bc.getAuditorid()).equals(sta.getStaffID())){
							loginstaffcheck++;
						}
					}
				}
			}
		}
		if(type!=null&&!type.equals("")){
			if(type.equals("00"))
			{type="00";
			}else if(type.equals("01")){
				type="01";
			}
		}
	}
/**********************************************************收货管理********************************************************/
	/**
	 * 收货管理——查看
	 * @return
	 */
	public String toAccpt(){
		toSee();
		return "edit";
	}
	/**
	 * 收货管理——条件查询(保存条件)
	 *
	 * @return
	 */
	public String toAccptSearch() {
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
		return getAccptList();
	}

	/**
	 * 收货管理——列表
	 *
	 * @return
	 */
	public String getAccptList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID};
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus='50' and c.organizationID=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		return "accptlist";
	}
	/**
	 * 查询列表（可根据条件查询）被调用
	 *
	 * @return
	 */
	public List<Object> getList() {
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
			if (financialBill.getStaffID() != null
					&& !financialBill.getStaffID().equals("")) {
				sql += " and f.staffID = ? ";
				parms.add(financialBill.getStaffID());
			}
			if (financialBill.getCcompanyRelationship() != null
					&& !financialBill.getCcompanyRelationship().equals("")) {
				sql += " and f.ccompanyRelationship = ? ";
				parms.add(financialBill.getCcompanyRelationship());
			}
			if (financialBill.getCstaffRelationship() != null
					&& !financialBill.getCstaffRelationship().equals("")) {
				sql += " and f.cstaffRelationship = ? ";
				parms.add(financialBill.getCstaffRelationship());
			}
			if (sdate != null && edate != null && !"".equals(sdate)
					&& !"".equals(edate)) {
				sql += " and to_date(f.billsDate,'yyyy-MM-dd') between ? and ?";
				parms.add(Utilities.getDateFromString(sdate, "yyyy-MM-dd"));
				parms.add(Utilities.getDateFromString(edate, "yyyy-MM-dd"));
			}
			if (financialBill.getCcompanyName() != null
					&& !financialBill.getCcompanyName().trim().equals("")) {
				sql += " and f.ccompanyName like ? ";
				parms.add("%" + financialBill.getCcompanyName().trim() + "%");
			}
			if (financialBill.getCstaffName() != null
					&& !financialBill.getCstaffName().trim().equals("")) {
				sql += " and f.cstaffName like ? ";
				parms.add("%" + financialBill.getCstaffName().trim() + "%");
			}
			if (financialBill.getBillStatus()!= null
					&& !financialBill.getBillStatus().equals("")) {
				sql += " and f.billStatus = ? ";
				parms.add(financialBill.getBillStatus());
			}
			if(type != null){
				sql+="and f.billsType=?";
				parms.add("采购单");
			}
		}
		if(search==null||search.equals("")){
			if(type!=null&&!type.equals("")){
				sql+="and f.billsType=?";
				parms.add("采购单");
				sql+="and f.billStatus=?";
				parms.add("00");
			}
		}
		sql+="order by f.billsDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
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

	public List<CCode> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}

	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}

	public Map<String, FinancialBillsGood> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, FinancialBillsGood> goodsmap) {
		this.goodsmap = goodsmap;
	}
	public Map<String, GoodsBills> getGoodsbillmap() {
		return goodsbillmap;
	}
	public void setGoodsbillmap(Map<String, GoodsBills> goodsbillmap) {
		this.goodsbillmap = goodsbillmap;
	}
	public Map<String, List<BaseBean>> getBillgoodsmap() {
		return billgoodsmap;
	}
	public void setBillgoodsmap(Map<String, List<BaseBean>> billgoodsmap) {
		this.billgoodsmap = billgoodsmap;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Staff getContactUser() {
		return contactUser;
	}

	public void setContactUser(Staff contactUser) {
		this.contactUser = contactUser;
	}

	public ContactCompany getContactCompanyView() {
		return contactCompanyView;
	}

	public void setContactCompanyView(ContactCompany contactCompanyView) {
		this.contactCompanyView = contactCompanyView;
	}

	public Inventory getInventoryParam() {
		return inventoryParam;
	}

	public void setInventoryParam(Inventory inventoryParam) {
		this.inventoryParam = inventoryParam;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}


	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	public int getCamount() {
		return camount;
	}
	public void setCamount(int camount) {
		this.camount = camount;
	}
	public FinancialBillVO getFinancialBillVO() {
		return financialBillVO;
	}

	public void setFinancialBillVO(FinancialBillVO financialBillVO) {
		this.financialBillVO = financialBillVO;
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

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	public String getFinancialbillID() {
		return financialbillID;
	}
	public void setFinancialbillID(String financialbillID) {
		this.financialbillID = financialbillID;
	}
	public List<BaseBean> getBillsgoodlist() {
		return billsgoodlist;
	}
	public void setBillsgoodlist(List<BaseBean> billsgoodlist) {
		this.billsgoodlist = billsgoodlist;
	}
	public List<CCode> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}
	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}
	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}
	public CashierBills getCashierBills() {
		return cashierBills;
	}
	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public List<BaseBean> getBillCheckList() {
		return BillCheckList;
	}
	public void setBillCheckList(List<BaseBean> billCheckList) {
		BillCheckList = billCheckList;
	}
	public GoodsBills getGoodsbills() {
		return goodsbills;
	}
	public void setGoodsbills(GoodsBills goodsbills) {
		this.goodsbills = goodsbills;
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
	public Map<String, String> getBillcheckmap() {
		return billcheckmap;
	}
	public void setBillcheckmap(Map<String, String> billcheckmap) {
		this.billcheckmap = billcheckmap;
	}
	public int getLoginstaffcheck() {
		return loginstaffcheck;
	}
	public void setLoginstaffcheck(int loginstaffcheck) {
		this.loginstaffcheck = loginstaffcheck;
	}
	public String getXmtype() {
		return xmtype;
	}
	public void setXmtype(String xmtype) {
		this.xmtype = xmtype;
	}
	
}
