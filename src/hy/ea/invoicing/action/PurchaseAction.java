package hy.ea.invoicing.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.company.vo.ContactCompanyView;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.FinancialBillVO;
import hy.ea.bo.invoicing.FinancialBillsGood;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 进销存  PurchaseAction
 * @author lf
 *
 */
@Controller
@Scope("prototype")
public class PurchaseAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	private FinancialBill financialBill;  //进销存单据表(采购单据表)
	private FinancialBillsGood financialBillsGood;   //进销存子类(物品单据表)
	private FinancialBillVO financialBillVO;	//进销存查询条件类(单据+物品)
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private Map<String, FinancialBillsGood> goodsmap;
	private List<CCode> payTypeList;
	private List<CCode> logisticsList;
	private List<CCode> connectionlist;
	private List<CCode> codeRelationList;
	private String companyname;
	private String organizationname;
	private String str;//用于接收人
	
	private String type;		// '00' 采购订单  '01' 收货管理  '02' 入库管理 '03' 出库管理 '04' 市场调查 '05'盘库单
	
	/**
	 * 仓库列表中--库列表
	 */
	private List<BaseBean> warehouseList;
	
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
	
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
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
					sql+="and f.billStatus=?";
					parms.add(type);
			}
		}
		if(search==null||search.equals("")){
			if(type!=null&&!type.equals("")){
				sql+="and f.billStatus=?";
				parms.add(type);
			}
		}
		sql+="order by f.billsDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	/**
	 * 采购订单/市场调查/收货管理——查看调用
	 * 
	 */
	private void toSee(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String hql="from FinancialBill where groupCompanySn = ? and companyid = ? and organizationID=? and financialbillID=?";
		financialBill=(FinancialBill)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{groupCompanySn,account.getCompanyID(),organizationID,financialBill.getFinancialbillID()});
		String hqlg="from FinancialBillsGood where financialbillID=? order by numbers";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hqlg, new Object[]{financialBill.getFinancialbillID()});
		//计算总金额
		if(pageForm!=null){
		    int size = pageForm.getList().size();
			for(int i=0;i<size;i++) {
			financialBillsGood=(FinancialBillsGood) pageForm.getList().get(i); 
			float amount=Float.parseFloat(financialBillsGood.getAmount());
			camount+=amount;
		     }
		}
		String hql2 = " from Staff where staffID=? ";
		contactUser=(Staff)baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{financialBill.getCstaffID()});
		String hql3 = " from ContactCompany where ccompanyID=? ";
		contactCompanyView=(ContactCompany)baseBeanService.getBeanByHqlAndParams(hql3, new Object[]{financialBill.getCcompanyID()});
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
		if(type!=null&&!type.equals("")){
			type="02";
		}
	}
	/**********************************************************采购订单********************************************************/

	/**
	 * 采购订单——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
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
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList();		
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		String staffhql = "from Staff s where exists ( select staffID from FinancialBill c where c.staffID=s.staffID and c.companyID=? and c.organizationID=? and c.billStatus=? and c.billsType=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, new Object[]{account.getCompanyID(),organizationID,"00","采购订单"});
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
		
		return "list";
	}

	

	/**
	 * 采购订单——保存
	 */
	public String savePurchase() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if(groupCompanySn==null){
			return "login";
		}
		financialBill.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加采购单据（凭证号" + financialBill.getJournalNum() + "）";
		financialBill.setOrganizationID(organizationID);
		financialBill.setCompanyID(account.getCompanyID());
		financialBill.setGroupCompanySn(groupCompanySn);
		financialBill.setBillsType("采购订单");
		financialBill.setBillsDate(new Date());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,new Object[] { account.getStaffID() });		
		financialBill.setBillStaffName(sta.getStaffName());
		financialBill.setBillStaffID(account.getStaffID());
		//financialBill.setStaffID(sta.getStaffID());
		//financialBill.setStaffName(sta.getStaffName());
		/*if (financialBill.getStaffName() != null
				&& !financialBill.getStaffName().equals("")) {
			String staffname = financialBill.getStaffName().substring(0,
					financialBill.getStaffName().indexOf("-"));
			financialBill.setStaffName(staffname);
		}*/
		financialBill.setBillStatus("00");
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(financialBill);
		if (goodsmap != null) {
			for (FinancialBillsGood goods : goodsmap.values()) {
				goods.setFinancialbillID(financialBill.getFinancialbillID());
				goods.setFinancialgoodID(serverService
						.getServerID("financialgood"));
				String hql = "from GoodsManage g where g.goodsID=?";
				GoodsManage goodsManage = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goods
								.getGoodsID() });
				goods.setGoodsName(goodsManage.getGoodsName());
				goods.setGoodsNum(goodsManage.getGoodsCoding());
				goods.setModelNumber(goodsManage.getModel());
				goods.setBrand(goodsManage.getMnemonicCode());
				goods.setType(goodsManage.getTypeID());
				goods.setSortCode(goodsManage.getBarCode());
				goods.setDefaultStorage(goodsManage.getDefaultStorage());
			
				if(goodsManage.getSubjectsID()!=null ){
				goods.setSubjectsID(goodsManage.getSubjectsID());
				goods.setSubjectsName(goodsManage.getSubjectsName());
				}
				goods.setStatus("00");
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		return "success";
	}

	/**
	 * 采购订单——添加、修改页面
	 * 
	 */
	public String toSavePurchase() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
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
	/**********************************************************市场调查********************************************************/
	
	/**
	 * 市场调查——查看
	 * @return
	 */
	public String toMarketResearch(){
		toSee();
		return "marketedit";
	}
	
	/**
	 * 市场调查——打印预览
	 * @return
	 */
	public String toPrintMarketResearch(){
		toSee();
		return "marketprint";
	}
	
	/**
	 * 市场调查——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toMarketResearchSearch() {
		ActionContext.getContext().getSession().put("financialBill",
				financialBill);
		return getMarketResearchList();
	}
	
	/**
	 * 市场调查——列表
	 * 
	 * @return
	 */
	public String getMarketResearchList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID};
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
		
		return "marketlist";
	}
	
	/**
	 * 市场调查——添加、修改页面
	 * 
	 */
	public String toSaveMarketResearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		return "marketadd";
	}
	
	/**
	 * 市场调查——保存
	 */
	public String saveMarketResearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		if(groupCompanySn==null){
			return "login";
		}
		financialBill.setFinancialbillID(serverService.getServerID("financial"));
		parameter = "添加市场调查单据（凭证号" + financialBill.getJournalNum() + "）";
		financialBill.setOrganizationID(organizationID);
		financialBill.setCompanyID(account.getCompanyID());
		financialBill.setGroupCompanySn(groupCompanySn);
		financialBill.setBillsType("市场调查单");
		financialBill.setBillsDate(new Date());
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,new Object[] { account.getStaffID() });		
		financialBill.setBillStaffName(sta.getStaffName());
		financialBill.setBillStaffID(account.getStaffID());
		if (financialBill.getStaffName() != null
				&& !financialBill.getStaffName().equals("")) {
			String staffname = financialBill.getStaffName().substring(0,
					financialBill.getStaffName().indexOf("-"));
			financialBill.setStaffName(staffname);
		}
		financialBill.setBillStatus("04");
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(financialBill);
		if (goodsmap != null) {
			for (FinancialBillsGood goods : goodsmap.values()) {
				goods.setFinancialbillID(financialBill.getFinancialbillID());
				goods.setFinancialgoodID(serverService
						.getServerID("financialgood"));
				String hql = "from GoodsManage g where g.goodsID=?";
				GoodsManage goodsManage = (GoodsManage) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] { goods
								.getGoodsID() });
				goods.setGoodsName(goodsManage.getGoodsName());
				goods.setGoodsNum(goodsManage.getGoodsCoding());
				goods.setModelNumber(goodsManage.getModel());
				goods.setBrand(goodsManage.getMnemonicCode());
				goods.setType(goodsManage.getTypeID());
				goods.setStatus("04");
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, null,
				null);
		return "success";
	}
	
	/**********************************************************收货管理********************************************************/
	
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
	 * 收货管理——收货处理
	 * 
	 * @return
	 */
	public String getUpdate(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=session.get("organizationID").toString();
		if (goodsmap != null) {
			for (FinancialBillsGood goods : goodsmap.values()) {
				List<Object[]> list=new ArrayList<Object[]>();
				List<BaseBean> listbabe=new ArrayList<BaseBean>(); 
				parameter = "员工："+account.getAccountName()+"收货（凭证号" + financialBill.getJournalNum() + "）";
				CLogBook cLogBook=logBookService.saveCLogBook(organizationID,
						parameter, account);
				String hql="update FinancialBillsGood set reQuantity=?,status=?,goodstatus=? where financialbillID=? and goodsID=?";
				String hql1="update FinancialBill set billStatus=? where financialbillID=? and companyID=? and organizationID=?";
				String[] hqls={hql,hql1};
				list.add(new Object[]{goods.getReQuantity(),"01","00",financialBill.getFinancialbillID(),goods.getGoodsID()});
				list.add(new Object[]{"01",financialBill.getFinancialbillID(),account.getCompanyID(),organizationID});
				listbabe.add(cLogBook);
				baseBeanService.executeHqlsByParamsList(listbabe, hqls, list);
			}
		}
		return "success";
	}
	
	/**
	 * 收货管理——查看
	 * @return
	 */
	public String toAccpt(){
		toSee();
		return "edit";
	}
	
	/**********************************************************验货管理********************************************************/
	
	/**
	 * 验货管理——条件查询(保存条件)
	 * 
	 * @return
	 */
	public String toinspectSearch() {
		ActionContext.getContext().getSession().put("financialBillsGood",
				financialBillsGood);
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
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql = "select g.financialgoodid,g.goodsnum,g.sortcode,g.goodsname,"
				+ " g.type,g.brand,g.modelnumber,g.unit,g.quantity,g.requantity,g.unitprice,"
				+ " g.amount,g.paymenttype,g.logisticstype,g.remindcontent,g.isQualify,case when g.status='00' then '未收货' " 
				+ " when g.status='01' then '已收货' when g.status='02' then '已验库'  when g.status='03' then '已入库' when g.status='07' then '出库' " 
				+ " when g.status='08' then '入库失败' when g.status='09' then '出库审核' else '' end, "
				+ " case when g.goodstatus='00' then '正常' when g.goodstatus='01' then '维修'" 
				+ " when g.goodstatus='02' then '报废' else '' end, f.journalNum,f.StaffName, f.billStaffName, f.staffsName,f.cstaffName,f.ccompanyName"
				+ " from dt_inv_fbg g"
				+ " left join dt_inv_fb f on g.financialbillid=f.financialbillid";
		sql+=" where f.groupCompanySn = ?";
		parms.add(groupCompanySn);
		sql += " and f.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and f.organizationID=?";
		parms.add(organizationID);
		//已收货
		sql+=" and f.billStatus=?";
		parms.add("01");
		if(search!=null&&search.equals("search")){
			FinancialBillsGood financialBillsGood=(FinancialBillsGood)session.get("financialBillsGood");
			if(financialBillsGood!=null&&financialBillsGood.getGoodsName()!=null&&!"".equals(financialBillsGood.getGoodsName().trim())){
				sql+=" and g.goodsName like ?";
				parms.add("%"+financialBillsGood.getGoodsName().trim()+"%");
			}
			if(financialBillsGood!=null&&financialBillsGood.getType()!=null&&!"".equals(financialBillsGood.getType().trim())){
				sql+=" and g.type like ?";
				parms.add("%"+financialBillsGood.getType().trim()+"%");
			}
			if(financialBillsGood!=null&&financialBillsGood.getStatus()!=null&&!financialBillsGood.getStatus().equals("")){
				sql+=" and g.status=?";
				parms.add(financialBillsGood.getStatus());
			}
			if(financialBillsGood!=null&&financialBillsGood.getGoodsNum()!=null&&!"".equals(financialBillsGood.getGoodsNum().trim())){
				sql+=" and g.goodsNum like ?";
				parms.add("%"+financialBillsGood.getGoodsNum().trim()+"%");
			}
		}
		if(search==null||search.equals("")){
			sql+=" and (g.status=?";
			parms.add("01");
			sql+=" or g.status=?)";
			parms.add("08");
		}
		sql += " order by g.financialgoodid desc ";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 验货管理——验货处理
	 * @return
	 */
	public String toUpdateIsQualify() {
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (goodsmap != null){
			for (FinancialBillsGood goods : goodsmap.values()) {
				List<BaseBean> list=new ArrayList<BaseBean>();
				FinancialBillsGood goods1= (FinancialBillsGood) baseBeanService
						.getBeanByHqlAndParams(
								"from FinancialBillsGood where financialgoodid=?",
								new Object[] {goods.getFinancialgoodID()});
				goods1.setIsQualify(goods.getIsQualify());
				goods1.setStorageQuantity(goods.getIsQualify());
				goods1.setStatus("02");
				parameter = "员工："+account.getAccountName()+"验货（物品单据号：" + goods1.getFinancialgoodID() + "）";
				CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);				
				list.add(goods1);
				list.add(cLogBook);
				baseBeanService.executeHqlsByParamsList(list, null, null);
			}
		}
		return "success";
	}
	
	/**********************************************************验货误差********************************************************/
	
	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @return
	 */
	private List<Object> getDetList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		String organizationID = (String) session.get("organizationID");
		String groupCompanySn = session.get("groupCompanySn").toString();
		String sql = "select f.financialbillID, c.companyname, f.journalNum, f.billsType, cr.organizationname,"
				+ " f.StaffName,f.billsDate,g.goodsnum,g.sortcode,g.goodsname,g.type,g.brand,g.modelnumber,"
				+ " g.unit,g.isQualify,g.requantity,g.quantity,g.unitprice,g.amount,g.paymenttype,g.logisticstype,"
				+ " g.remindcontent,f.ccompanyName,f.ccompanyRelationship,f.cstaffName,f.cstaffRelationship"
				+ " from dt_inv_fbg g"
				+ " left join dt_inv_fb f on g.financialbillid=f.financialbillid"
				+ " left join dtcompany c on  c.companyid = f.companyID"
				+ " left join dtcorganization cr on  cr.organizationid = f.departmentID";
		sql+=" where f.groupCompanySn = ?";
		parms.add(groupCompanySn);
		sql += " and f.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and f.organizationID=?";
		parms.add(organizationID);
		//已收货
		sql+=" and f.billStatus=?";
		parms.add("01");
		//物品已验货
		sql+=" and g.status=?";
		parms.add("02");
		if (search != null && search.equals("search")) {
			FinancialBillVO financialBillVO=(FinancialBillVO)session.get("financialBillVO");
			if(financialBillVO!=null){
				if(financialBillVO.getGoodsName()!=null&&!"".equals(financialBillVO.getGoodsName().trim())){
					sql+=" and g.goodsName like ?";
					parms.add("%"+financialBillVO.getGoodsName().trim()+"%");
				}
				if(financialBillVO.getType()!=null&&!"".equals(financialBillVO.getType().trim())){
					sql+=" and g.type like ?";
					parms.add("%"+financialBillVO.getType().trim()+"%");
				}
				if (financialBillVO.getJournalNum()!= null
						&& !financialBillVO.getJournalNum().trim().equals("")) {
					sql += " and f.journalNum like ? ";
					parms.add("%" + financialBillVO.getJournalNum().trim() + "%");
				}
				if (financialBillVO.getStaffID() != null
						&& !financialBillVO.getStaffID().equals("")) {
					sql += " and f.staffID = ? ";
					parms.add(financialBillVO.getStaffID());
				}
				if (financialBillVO.getCcompanyRelationship() != null
						&& !financialBillVO.getCcompanyRelationship().equals("")) {
					sql += " and f.ccompanyRelationship = ? ";
					parms.add(financialBillVO.getCcompanyRelationship());
				}
				if (financialBillVO.getCstaffRelationship() != null
						&& !financialBillVO.getCstaffRelationship().equals("")) {
					sql += " and f.cstaffRelationship = ? ";
					parms.add(financialBillVO.getCstaffRelationship());
				}
				if (sdate != null && edate != null && !"".equals(sdate)
						&& !"".equals(edate)) {
					sql += " and TO_DATE(f.billsDate,'yyyy-MM-dd') between TO_DATE(?,'YYYY-MM-DD') and TO_DATE(?,'YYYY-MM-DD') ";
					parms.add(sdate);
					parms.add(edate);
				}
				if (financialBillVO.getCcompanyName() != null
						&& !financialBillVO.getCcompanyName().trim().equals("")) {
					sql += " and f.ccompanyName like ? ";
					parms.add("%" + financialBillVO.getCcompanyName().trim() + "%");
				}
				if (financialBillVO.getCstaffName() != null
						&& !financialBillVO.getCstaffName().trim().equals("")) {
					sql += " and f.cstaffName like ? ";
					parms.add("%" + financialBillVO.getCstaffName().trim() + "%");
				}
			}
		}
		sql+=" order by f.billsDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 验货误差——条件查询(保存条件)
	 */
	public String toSearchDeviation	() {
		ActionContext.getContext().getSession().put("financialBillVO",
				financialBillVO);
		return getDeviationList();
	}
	
	/**
	 * 验货误差——列表查询
	 */
	public String getDeviationList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getDetList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		//查询本部门责任人
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus=? and c.organizationID=?)";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, new Object[]{account.getCompanyID(),"50",organizationID});
		//查询往来单位关系
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
		//查询往来个人关系
		codeRelationList = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);		
		return "deviationList";
	}
	
	/**
	 * 验货误差——导出
	 */
	@SuppressWarnings("unchecked")
	public String showExcelDeviation(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result=getDetList();
		String sql=result.get(0).toString();
		sql = "select " + sql.substring(sql.indexOf(",") + 1);
		Object[] prarms=(Object[])result.get(1);
		excelStream = excelService.showExcel(FinancialBillsGood.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql, prarms));
		CLogBook  logbook=logBookService.saveCLogBook(null, "导出验货误差", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}
	
	/**
	 * 验货时选择接收人
	 * @return
	 */
	public String updatelist(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (str != null && !"".equals(str)) {
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			for (int i = 0; i < strs.length; i++) {
				List<BaseBean> list=new ArrayList<BaseBean>();
				FinancialBillsGood goods1= (FinancialBillsGood) baseBeanService
						.getBeanByHqlAndParams(
								"from FinancialBillsGood where financialgoodid=?",
								new Object[] {strs[i].trim()});
				FinancialBill bill=(FinancialBill) baseBeanService.getBeanByHqlAndParams("from FinancialBill where financialbillID=?",new Object[] {goods1.getFinancialbillID()});
				bill.setStaffsID(financialBill.getStaffsID());
				bill.setStaffsName(financialBill.getStaffsName());
				parameter = "员工："+account.getAccountName()+"验货（物品单据号：" + goods1.getFinancialgoodID() + "）";
				CLogBook cLogBook=logBookService.saveCLogBook(organizationID,parameter, account);				
				list.add(bill);
				list.add(cLogBook);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
				baseBeanService.executeHqlsByParamsList(list, null, null);
			}
		}
		return "success";
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
	
}
