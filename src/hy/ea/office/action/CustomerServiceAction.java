package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.vo.CashierBillsVO;
import hy.ea.bo.finance.vo.ServiceBillsPrintVO;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.UpLoadFile;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CompanyService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
/**
 * 售前客户服务单据：CustomerServiceAction
 * 
 */
public class CustomerServiceAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;
	@Resource
	private UpLoadFileService upLoadFileService;
	@Resource
	private CompanyService companyserverService;
	@Resource
	private UpLoadFileService fileService;
	public InputStream excelStream;
	private String result;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	/**
	 * 责任人
	 */
	private List<BaseBean> stafflist;
	private CashierBills cashierBills;
	private CashierBillsVO cashierBillsVO;
	private Map<String, GoodsBills> goodsmap;
	/**
	 * 单据类别
	 */
	private List<CCode> billsTypeList;
	/**
	 * 费用类别--GoodsBills
	 */
	private List<CCode> costTypeList;
	/**
	 * 付款方式--GoodsBills
	 */
	private List<CCode> payTypeList;
	/**
	 * 单价管理--GoodsBills
	 */
	private List<CCode> priceManageList;
	/**
	 * 物流方式--GoodsBills
	 */
	private List<CCode> logisticsList;
	/**
	 * 单位往来关系
	 */
	private List<CCode> connectionlist;
	/**
	 * 仓库类别
	 */
	private List<CCode> typelist;
	/**
	 * 个人往来关系
	 */
	private List<CCode> codeRelationList;
	private String typeID;
	private String search;
	private String sdate;
	private String edate;
	private String departmentID;
	private String differenceStyle;
	private String BType;
	
	private String projectDecPath;
	private String projectDecPath1;
	
	/**
	 * 客户咨询单查询打印
	 */
	private List<BaseBean> cashierBillsVoList;
	private List<ServiceBillsPrintVO> serviceBillsPrintVOList;
	private String staffNameID;  //打印查询传值
	
	/** **************************************************** */
	/**
	 * 前台获取负责人
	 */
	public String getToStaffID(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from Staff where staffID = ?";
		Object[] param = {};
		if(cashierBillsVO == null){
			param = new Object[]{account.getStaffID()};
		}else{
			param = new Object[]{cashierBillsVO.getStaffID()};
		}
		Staff staff =(Staff)baseBeanService.getBeanByHqlAndParams(hql,param);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffID", staff.getStaffID());
		map.put("staffName", staff.getStaffName());
		JSONObject jo = JSONObject.fromObject(map);
		result = jo.toString();
		return "success";
	}
	
	/**
	 * 售前客户服务跳转编辑页面
	 * 
	 * @return
	 */
	public String toCashierPage() {
		toPage();
		return "custService_edit";
	}
	
	/**
	 * 封装跳转添加或编辑页面的方法
	 * 
	 * @param :companyID,
	 *            organizationID 添加
	 * @param :companyID,
	 *            organizationID ，cashierTally.getCashierID()修改
	 * @return : edit
	 */
	private void toPage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		billsTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		costTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000030");
		payTypeList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000031");
		logisticsList = codeService.getCCodeListByPID(companyID,
				"scode20110106hfjes5ucxp0000000021");
		priceManageList = codeService.getCCodeListByPID(companyID,
				"scode20101101dfs3uhdprp0000000032");
		typelist = codeService.getCCodeListByPID(companyID,
				"scode20101014v5zed7cukk0000000004");
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "sode20110106hfjes5ucxp0000000017");
		if (cashierBills != null) {
			Object[] params1 = { companyID, cashierBills.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where companyID = ?  and cashierBillsID=? order by goodsNomber asc";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from CashierBillsVO where companyID = ?  and cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
		}
	}
	
	/**
	 * 售前客户服务列表
	 * 
	 * @return
	 */
	public String getCashierToPage() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		search();
		return "custService_list";
	}

	/**
	 * 售前客户服务条件查询
	 * 
	 * @return
	 */
	public String toSearchByCondition() {
		ActionContext.getContext().getSession().put("tablesearch",
				cashierBillsVO);
		search();
		return "custService_list";
	}
	
	/**
	 * 封装查询类表的方法
	 */
	private void search() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(), organizationID };
		String staffhql = "from Staff s where exists ( select staffID from COS c where c.staffID=s.staffID and c.companyID=? and c.cosStatus='50' and c.organizationID=? )";
		stafflist = baseBeanService.getListBeanByHqlAndParams(staffhql, params);
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		billsTypeList = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20101101dfs3uhdprp0000000029");
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parms);
	}
	
	/**
	 * 查询列表（可根据条件查询）被调用
	 * 
	 * @param :
	 *            CashierBills 查询条件
	 * @return ：beanlist
	 */
	private List<Object> getList() {
		List<Object> result = new ArrayList<Object>();
		String sql = VOtool.getCashierBillVO(3);
		List<Object> parms = new ArrayList<Object>();
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		sql += " where t.billstype = '咨询跟踪单'";
		sql += " and t.companyid = ? ";
		parms.add(account.getCompanyID());
		sql += " and t.organizationid = ? ";
		parms.add(organizationID);
		if (search != null && search.equals("search")) {
			cashierBillsVO = (CashierBillsVO) session.get("tablesearch");
			if (cashierBillsVO != null) {
				if (null != cashierBillsVO.getStaffID().trim()
						&& !"".equals(cashierBillsVO.getStaffID().trim())) {
					sql += " and t.staffID = ?";
					parms.add(cashierBillsVO.getStaffID().trim());
				}
				if (null != cashierBillsVO.getDepartmentID().trim()
						&& !"".equals(cashierBillsVO.getDepartmentID().trim())) {
					sql += " and t.departmentid = ?";
					parms.add(cashierBillsVO.getDepartmentID().trim());
				}
				if (null != cashierBillsVO.getJournalNum().trim()
						&& !"".equals(cashierBillsVO.getJournalNum().trim())) {
					sql += " and t.journalNum like ? ";
					parms.add("%" + cashierBillsVO.getJournalNum().trim() + "%");
				}
				if (null != cashierBillsVO.getConsultStatus().trim()
						&& !"".equals(cashierBillsVO.getConsultStatus().trim())) {
					sql += " and t.consultStatus = ?";
					parms.add(cashierBillsVO.getConsultStatus().trim());
				}
				if (null != cashierBillsVO.getContactConnections().trim()
						&& !"".equals(cashierBillsVO.getContactConnections().trim())) {
					sql += " and t.contactConnections = ? ";
					parms.add(cashierBillsVO.getContactConnections());
				}
				if (null != cashierBillsVO.getPhone().trim()
						&& !"".equals(cashierBillsVO.getPhone().trim())) {
					sql += " and t.phone = ? ";
					parms.add(cashierBillsVO.getPhone().trim());
				}
				if (sdate != null && edate != null && !("").equals(sdate)
						&& !("").equals(edate)) {
					sql += " and t.cashierDate between ? and ? ";

					parms.add(Utilities.getDateFromString(sdate + " 00:00:00",
							"yyyy-MM-dd hh:mm:ss"));

					parms.add(Utilities.getDateFromString(edate + " 23:59:59",
							"yyyy-MM-dd hh:mm:ss"));
				}
				if (null != cashierBillsVO.getCcompanyname().trim()
						&& !"".equals(cashierBillsVO.getCcompanyname().trim())) {
					sql += " and cc.companyname like ? ";
					parms.add("%" + cashierBillsVO.getCcompanyname().trim() + "%");
				}
				if (null != cashierBillsVO.getContactUserName().trim()
						&& !"".equals(cashierBillsVO.getContactUserName().trim())) {
					sql += " and cu.staffname like ? ";
					parms.add("%" + cashierBillsVO.getContactUserName().trim() + "%");
				}
			}
		}else{
			sql += " and (t.consultStatus between '01' and '03' or t.consultStatus = '10')";
			sql += " and t.staffID = ?";
			parms.add(account.getStaffID());
		}
		sql += " order by t.cashierDate desc";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	/**
	 * 客户服务咨询单保存
	 * 
	 */
	public String saveCustCashierBills() {
		saveCust();
		return "success";
	}

	/**
	 * 封装咨询单保存的方法
	 * 
	 * @param : cashierTally
	 * @return : toSaveCashierTally() 继续添加
	 * @return : getCashierTallyList() 返回列表
	 */
	private void saveCust() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (null == cashierBills.getCashierBillsID()
				|| "".equals(cashierBills.getCashierBillsID())) {
			cashierBills.setJournalNum(serverService.getBillID(account
					.getCompanyID()));
			cashierBills.setCashierBillsID(serverService
					.getServerID("cashierTally"));
			
			String hqlForMan = "from Staff where staffID = ?";
			Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
					new Object[] { account.getStaffID() });
			cashierBills.setInputName(sta.getStaffName()); //设置制单人
			
			parameter = "添加咨询跟踪单（凭证号" + cashierBills.getJournalNum() + "）";
		} else {
			parameter = "修改咨询跟踪单（凭证号" + cashierBills.getJournalNum() + "）";

		}
		if (cashierBills.getConsultStatus() != null) {
			if (cashierBills.getConsultStatus() == "01"
					|| cashierBills.getConsultStatus().equals("01")) {
				parameter += "并把档案归档！";
			}
		}
		cashierBills.setCompanyID(account.getCompanyID());
		cashierBills.setOrganizationID(organizationID);
		cashierBills.setCashierDate(new Date());
		if (cashierBillsVO != null) {
			cashierBills.setBillsType(BType);
			cashierBills.setContactConnections(cashierBillsVO
					.getContactConnections());
			cashierBills.setPhone(cashierBillsVO.getPhone());
		}
		Company company = companyserverService.getCompanyByCompanyID(account
				.getCompanyID());
		if (company.getCompanyPID().startsWith("pcompany")) {
			cashierBills.setPcompanyID(company.getCompanyID());
		} else {
			cashierBills.setPcompanyID(company.getCompanyPID());
		}
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		baseBeanList.add(cashierBills);
		if (goodsmap != null) {
			for (GoodsBills goods : goodsmap.values()) {
				if (null == goods.getGoodsBillsID()
						|| "".equals(goods.getGoodsBillsID())) {
					goods.setGoodsBillsID(serverService
							.getServerID("goodsbills"));
				}
				if(null != goods.getEtime() 
						|| !"".equals(goods.getEtime())){
					goods.setEntryTime(new Date());
				}
				if (goods.getSdate() != null && !goods.getSdate().equals("")) {
					goods.setStartDate(Utilities.getDateFromString(goods
							.getSdate(), "yyyy-MM-dd HH:mm:ss"));
				}
				if (goods.getEdate() != null && !goods.getEdate().equals("")) {
					goods.setEndDate(Utilities.getDateFromString(goods
							.getEdate(), "yyyy-MM-dd HH:mm:ss"));
				}
				if(goods.getAttachmentPath()!=null && !"".equals(goods.getAttachmentPath())
						&& goods.getAttachmentPath().indexOf("\\\\")==-1){
					goods.setAttachmentPath(goods.getAttachmentPath()
							.replaceAll("\\\\", "\\\\\\\\"));
				}
				goods.setCompanyID(account.getCompanyID());
				goods.setCashierBillsID(cashierBills.getCashierBillsID());
				baseBeanList.add(goods);
			}
		}
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		baseBeanList.add(logBook);
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList,
					null, null);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 打印预览
	 * @return
	 */
	public String toprintCashierService() {
		if (cashierBillsVO != null) {
			Object[] params1 = {  cashierBillsVO.getCashierBillsID() };
			String hql1 = "from GoodsBillsVO where  cashierBillsID=?";
			pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
					.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
					hql1, params1);
			String hql = " from CashierBillsVO where  cashierBillsID=?";
			cashierBillsVO = (CashierBillsVO) baseBeanService
					.getBeanByHqlAndParams(hql, params1);
		}
		return "printcashierService";
	}
	
	/**
	 * 打印全部单据(除了草稿单据)
	 * @return
	 */
	public String toprintCashierServiceAll(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID = (String) session.get("organizationID");
		List<Object> params = new ArrayList<Object>();
		String hql = "from CashierBillsVO where organizationID = ? and depStatue = '11' and consultStatus <> '00'";
		params.add(organizationID);
		if(!"".equals(staffNameID)){
			hql += " and staffID = ?";
			params.add(staffNameID);
		}
		if(!"".equals(sdate) && !"".equals(edate)){
			hql += " and cashierDate between ? and ? ";
			params.add(Utilities.getDateFromString(sdate + " 00:00:00",
			"yyyy-MM-dd hh:mm:ss"));
			params.add(Utilities.getDateFromString(edate + " 23:59:59","yyyy-MM-dd hh:mm:ss"));
		}
		cashierBillsVoList = baseBeanService.getListBeanByHqlAndParams(hql,params.toArray());
		List<ServiceBillsPrintVO> printList = new ArrayList<ServiceBillsPrintVO>();
		if(cashierBillsVoList.size() > 0){
			for(BaseBean bean:cashierBillsVoList){
				cashierBillsVO = (CashierBillsVO)bean;
				
				ServiceBillsPrintVO serviceBillsPrintVO = new ServiceBillsPrintVO();
				serviceBillsPrintVO.setServiceBillsPrintID(cashierBillsVO.getCashierBillsID());
				serviceBillsPrintVO.setBillsType(cashierBillsVO.getBillsType());
				
				serviceBillsPrintVO.setCompanyname(cashierBillsVO.getCompanyname());
				serviceBillsPrintVO.setDepartmentname(cashierBillsVO.getDepartmentname());
				serviceBillsPrintVO.setStaffname(cashierBillsVO.getStaffname());
				serviceBillsPrintVO.setRecordcode(cashierBillsVO.getRecordcode());
				serviceBillsPrintVO.setCashierDate(cashierBillsVO.getCashierDate());
				serviceBillsPrintVO.setCompanyBankNum(cashierBillsVO.getCompanyBankNum());
				serviceBillsPrintVO.setJournalNum(cashierBillsVO.getJournalNum());
				
				serviceBillsPrintVO.setCcompanyname(cashierBillsVO.getCcompanyname());
				serviceBillsPrintVO.setCompanyTel(cashierBillsVO.getCompanyTel());
				serviceBillsPrintVO.setCresponsible(cashierBillsVO.getCresponsible());
				serviceBillsPrintVO.setCompanyAddr(cashierBillsVO.getCompanyAddr());
				serviceBillsPrintVO.setContactConnections(cashierBillsVO.getContactConnections());
				
				serviceBillsPrintVO.setContactUserName(cashierBillsVO.getContactUserName());
				serviceBillsPrintVO.setTel(cashierBillsVO.getTel());
				serviceBillsPrintVO.setStaffIdentityCard(cashierBillsVO.getStaffIdentityCard());
				serviceBillsPrintVO.setUserQq(cashierBillsVO.getUserQq());
				serviceBillsPrintVO.setEmail(cashierBillsVO.getEmail());
				serviceBillsPrintVO.setPhone(cashierBillsVO.getPhone());
				serviceBillsPrintVO.setUserAddr(cashierBillsVO.getUserAddr());
				
				serviceBillsPrintVO.setStatus(cashierBillsVO.getConsultStatus());
				
				String hql1 = "from GoodsBills where cashierbillsid = ?";
				List<BaseBean> goodsList = baseBeanService.getListBeanByHqlAndParams(hql1,
						new Object[]{serviceBillsPrintVO.getServiceBillsPrintID()});
				
				serviceBillsPrintVO.setGoodsList(goodsList);
				printList.add(serviceBillsPrintVO);
			}
			serviceBillsPrintVOList = printList;
		}
		return "toprintCashierServiceAll";
	}

	/**
	 * 咨询跟踪单删除
	 * 
	 * @param cashierTally.getCashierID()
	 * @return getCashierTallyList()
	 */
	public String delCashierBills() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(),
				cashierBills.getCashierBillsID() };
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
		String hql1 = " delete from CashierBills where companyID=?   and  cashierBillsID= ? ";
		String hql2 = " delete from GoodsBills where companyID=?   and  cashierBillsID= ? ";
		String hql3 = " delete from UpLoadFile where companyID=?   and  parmeterID = ? ";
		String hql4 = "from CashierBills where companyID= ?   and cashierBillsID = ? ";
		String hql5 = "from UpLoadFile where companyID= ?   and parmeterID = ? ";
		List<BaseBean> beanlist = baseBeanService.getListBeanByHqlAndParams(
				hql5, params);
		ArrayList<String> paths = new ArrayList<String>();
		for (BaseBean bean : beanlist) {
			UpLoadFile load = (UpLoadFile) bean;
			paths.add(path + load.getFilepath());
		}
		upLoadFileService.deletePhotos(paths);
		String[] hqls = { hql3, hql2, hql1 };
		CashierBills cash = (CashierBills) baseBeanService
				.getBeanByHqlAndParams(hql4, params);
		List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"删除咨询跟踪单 (凭证号:" + cash.getJournalNum() + ")", account);
		baseBeanList.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeanList, hqls,
				params);

		return "success";
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<CCode> getBillsTypeList() {
		return billsTypeList;
	}

	public void setBillsTypeList(List<CCode> billsTypeList) {
		this.billsTypeList = billsTypeList;
	}

	public List<CCode> getCostTypeList() {
		return costTypeList;
	}

	public void setCostTypeList(List<CCode> costTypeList) {
		this.costTypeList = costTypeList;
	}

	public List<CCode> getPayTypeList() {
		return payTypeList;
	}

	public void setPayTypeList(List<CCode> payTypeList) {
		this.payTypeList = payTypeList;
	}

	public List<CCode> getPriceManageList() {
		return priceManageList;
	}

	public void setPriceManageList(List<CCode> priceManageList) {
		this.priceManageList = priceManageList;
	}

	public CashierBills getCashierBills() {
		return cashierBills;
	}

	public void setCashierBills(CashierBills cashierBills) {
		this.cashierBills = cashierBills;
	}

	public Map<String, GoodsBills> getGoodsmap() {
		return goodsmap;
	}

	public void setGoodsmap(Map<String, GoodsBills> goodsmap) {
		this.goodsmap = goodsmap;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public CashierBillsVO getCashierBillsVO() {
		return cashierBillsVO;
	}

	public void setCashierBillsVO(CashierBillsVO cashierBillsVO) {
		this.cashierBillsVO = cashierBillsVO;
	}

	public List<CCode> getLogisticsList() {
		return logisticsList;
	}

	public void setLogisticsList(List<CCode> logisticsList) {
		this.logisticsList = logisticsList;
	}

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(String departmentID) {
		this.departmentID = departmentID;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public String getDifferenceStyle() {
		return differenceStyle;
	}

	public void setDifferenceStyle(String differenceStyle) {
		this.differenceStyle = differenceStyle;
	}

	public String getBType() {
		return BType;
	}

	public void setBType(String type) {
		BType = type;
	}

	public String getProjectDecPath() {
		return projectDecPath;
	}

	public void setProjectDecPath(String projectDecPath) {
		this.projectDecPath = projectDecPath;
	}

	public String getProjectDecPath1() {
		return projectDecPath1;
	}

	public void setProjectDecPath1(String projectDecPath1) {
		this.projectDecPath1 = projectDecPath1;
	}

	public List<BaseBean> getCashierBillsVoList() {
		return cashierBillsVoList;
	}

	public void setCashierBillsVoList(List<BaseBean> cashierBillsVoList) {
		this.cashierBillsVoList = cashierBillsVoList;
	}

	public String getStaffNameID() {
		return staffNameID;
	}

	public void setStaffNameID(String staffNameID) {
		this.staffNameID = staffNameID;
	}

	public List<ServiceBillsPrintVO> getServiceBillsPrintVOList() {
		return serviceBillsPrintVOList;
	}

	public void setServiceBillsPrintVOList(
			List<ServiceBillsPrintVO> serviceBillsPrintVOList) {
		this.serviceBillsPrintVOList = serviceBillsPrintVOList;
	}
}