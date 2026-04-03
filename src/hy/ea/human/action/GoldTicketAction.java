package hy.ea.human.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.publicreceipts.Publicreceipts;
import hy.ea.bo.human.publicreceipts.PublicreceiptsChild;
import hy.ea.human.service.GoldTicketService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

//金币罚款  
@Controller
@Scope("prototype")
public class GoldTicketAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private GoldTicketService goldTicketService;
	@Resource
	private ShowExcelService excelService;
	
	
	private List<BaseBean> organizationlist;
	private Company company;
	private PageForm pageForm;
	private int pageNumber;
	private String result;
	private String photoFileName;
	private File photo;//附件
	private Publicreceipts publicreceipts;
	private PublicreceiptsChild publicreceiptsChild;
	private String parameter;
	private String downLoadPath; // 下载传值
	private String search;//查询
	private String flag;
	private String prID;
	private String labelTag;
	private String types;
	public InputStream excelStream;
	private String pri;//导出
	private String account;//微分金帐号
	private String staffname;
	
	//金币罚款列表
	public String getListGoldTicket(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String orgID = session.get("organizationID").toString();
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String companyID = account.getCompanyID();
		String ohql = "from COrganization where companyID=? and Status = ?";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				new Object[] { companyID, "00" });
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { companyID });
		List<Object> list = getList(session, account, orgID);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);

			
		return "goldTicketList";		
	}
	
	//返回金币罚款列表sql
	private List<Object> getList(Map<String, Object> session,CAccount account, String organizationID){
		List<Object> parms = new ArrayList<Object>();
		List<Object> result = new ArrayList<Object>();
		String companyID = account.getCompanyID();
		StringBuilder sql = new StringBuilder();
				
		// "pri".equals(pri) 导出
		if("pri".equals(pri)){
			sql.append(" select s.vouchernum,c.companyname,co.organizationname,s.principal, ");
			sql.append(" s.accountinform,s.custype,d.finecount,d.finereason,s.operator,s.applydate, ");
			sql.append(" s.firstauditor,s.secondauditor,");
			sql.append(" case when s.receiptsstatus='p' then '待审' ");
			sql.append(" when s.receiptsstatus='f' then '部门主管审核通过' ");
			sql.append(" when s.receiptsstatus='s' then '人力资源部审核通过' ");
			sql.append(" when s.receiptsstatus='a' then '总经理审核通过' ");
			sql.append(" when s.receiptsstatus='r' then '驳回作废' ");
			sql.append(" when s.receiptsstatus='d' then  '草稿' ");		
			sql.append(" when s.receiptsstatus='b' then '撤销' end, ");
			sql.append(" d.finedate,s.accessory ");
									
		}else{
			sql.append(" select s.prid,s.vouchernum,c.companyname,co.organizationname,s.principal, ");
			sql.append(" s.accountinform,s.custype,d.finecount,d.finereason,s.operator,s.applydate, ");
			sql.append(" s.firstauditor,s.secondauditor, s.finalauditor,s.receiptsstatus,");		
			sql.append(" d.finedate,s.accessory,s.prKey,d.orKey,d.orID,s.refundDate ");
			
		}			
		sql.append(" from dtpublicreceiptschild d ");
		sql.append(" left join dtpublicreceipts s on s.prid = d.prid ");		
		sql.append(" left join dtcompany c on c.companyid = s.companyid ");
		sql.append(" left join dtcorganization co on s.principalOrganizationID =co.organizationid ");
		sql.append(" left join dtcorganization cr on cr.organizationid = s.principalorganizationid ");
		sql.append(" where s.type=? ");
		sql.append(" and s.companyID=? ");
		sql.append(" and s.organizationID=? ");
		sql.append(" and s.operator=? ");
		
		
		parms.add("金币折扣单");
		parms.add(companyID);
		parms.add(organizationID);
		String hqlForMan = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,new Object[] { account.getStaffID() });
		parms.add(sta.getStaffName());
		
		//查询
		if(search != null && "search".equals(search)){
		
			publicreceipts = (Publicreceipts)session.get("tablesearchFPub");
			
			//凭证号
			if(!"".equals(publicreceipts.getVoucherNum().trim())){
				sql.append(" and s.vouchernum = ? ");
				parms.add(publicreceipts.getVoucherNum().trim());
			}
			//责任人
			if(!"".equals(publicreceipts.getPrincipal().trim())){
				sql.append(" and s.principal = ? ");
				parms.add(publicreceipts.getPrincipal().trim());
			}
			
			//帐号信息
			if(!"".equals(publicreceipts.getAccountinform().trim())){
				sql.append(" and s.accountinform = ? ");
				parms.add(publicreceipts.getAccountinform().trim());
			}
		}
		sql.append(" order by s.applyDate desc ");
		result.add(sql);
		result.add(parms.toArray());
		return result;
								
	}
	
	//获取制单人
	public String getOperator() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hqlForMan = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
				new Object[] { account.getStaffID() });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staff", staff.getStaffName());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	
	public String getoList() {
		Map<String, Object>  session=ActionContext.getContext().getSession();
		CAccount account = (CAccount)session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String companyID = account.getCompanyID();
		Object[] params1 =new  Object[1];
		params1[0]=companyID;
		String ohql = "from COrganization where companyID=? and Status = '00' order by organizationNumber";
		
		
		HttpServletRequest  request=ServletActionContext.getRequest();
		String series=request.getParameter("series");
		String level=request.getParameter("level");
		//-----------------//仅查询公司下一级部门 
		if(series!=null&&"one".equals(series.trim())){
			ohql = "from COrganization where organizationPID=? and Status = '00' order by organizationNumber";
			params1[0]=request.getParameter("companyID");
			//-----------------//仅查询当前部门 
			if(level!=null&&"organization".equals(level.trim())){
				ohql = "from COrganization where (organizationPID=? or organizationID=?) and  Status = '00' order by organizationNumber";
				params1=new  Object[2];
				params1[0]=organizationID;
				params1[1]=organizationID;
			}			
		}				
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				params1);
		List<BaseBean> compareOList = getCutAcctOrg();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationlist", organizationlist);
		map.put("compareOList", compareOList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		organizationlist=null;
		compareOList=null;
		return "success";
	}	
	/**
	 * 获取责任人为当前账户的所有部门
	 * 
	 * @return
	 */
	private List<BaseBean> getCutAcctOrg() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount acct = (CAccount) session.get("account");
		String hql = " from COrganization org where org.companyID = ? "
				+ "and org.organizationID in "
				+ "(select ag.organizationID from Agencies ag where ag.staffID =?)";
		return baseBeanService.getListBeanByHqlAndParams(hql, new Object[] {
				acct.getCompanyID(), acct.getStaffID() });
	}

	//帐号信息
	public String getAccounts(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount caccount = (CAccount) session.get("account");
		TEshopCusCom cus = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId=? ", new Object[]{caccount.getCompanyID()});
		
		String custype = request.getParameter("custype");
		request.setAttribute("custype", custype);
		List<Object> para = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
			
		sql.append(" select te.sccid,te.account,hr.staffname,hr.username,hr.sex,hr.birthday,hr.nativeplace, ");
		sql.append(" hr.nationality,hr.nation,hr.staffidentitycard,te.custype,te.state ");		
		sql.append(" from T_ESHOP_CUSCOM te ,dt_hr_Staff hr ");					
		sql.append(" where  te.staffid = hr.staffid and te.account in ( " );			
		sql.append(" select te.account " );
		sql.append(" from T_ESHOP_CUSCOM te ");	
		sql.append(" start with  te.account= ? ");
		sql.append(" connect by prior te.account= te.superioragent) ");	
		
		para.add(cus.getAccount());
			
		//账号级别
		if(!"".equals(custype)&& custype != null){
			
			sql.append(" and te.custype=? ");
			para.add(custype);
		}
		
		if(!"".equals(account)&& account != null){
			sql.append(" and te.account=? ");
			para.add(account);
		}
		
		if(!"".equals(staffname)&& staffname != null){
			sql.append(" and hr.staffname=? ");
			para.add(staffname);
		}
	
		pageForm = baseBeanService.getPageFormBySQL(pageForm==null?0:pageForm.getPageNumber(), (pageNumber == 0 ? 5 : pageNumber), sql.toString(), "select count(*) from ("+sql.toString()+")", para.toArray());
		
		return "account";		
	}
	
	//查询用户的级别
	public String cusType(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String sccid = request.getParameter("sccid");
		
		String hql = " from TEshopCusCom where sccId=? ";
		TEshopCusCom te = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{sccid});
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custype",te.getCusType());		
		map.put("sccid", sccid);
		map.put("staffid", te.getStaffid());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();		
		return "success";
	}
	
	//提交审核（金币罚款）
	public String saveFindGold(){
		List<BaseBean> beans = new ArrayList<BaseBean>();
		HttpServletRequest request = ServletActionContext.getRequest();
		String satus = request.getParameter("satus");
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");				
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		//附件
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/human/staffrank/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			publicreceipts.setAccessory(photoPath);
		}			
		if("".equals(publicreceipts.getPrID())){
			publicreceipts.setPrID(serverService.getServerID("publicreceipts").trim());
			
			parameter = "添加惩罚金币申请单(凭证号:" + publicreceipts.getVoucherNum() + ";制单人："+account.getStaffName()+")";
			publicreceiptsChild.setOrID(serverService.getServerID("publicreceiptsChild"));
		}
		publicreceipts.setOrganizationID(organizationID);
		publicreceipts.setCompanyID(account.getCompanyID());
		Company company1 = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { account.getCompanyID() });		
		publicreceipts.setPcompanyID(company1.getCompanyPID());
		publicreceipts.setType("金币折扣单");
		
		//0 为草稿  1 为提交审核
		if("0".equals(satus)){
			publicreceipts.setReceiptsStatus("D");
		}else{
			publicreceipts.setReceiptsStatus("P");
		}		
		publicreceiptsChild.setPrID(publicreceipts.getPrID());
				
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(publicreceiptsChild);
		beans.add(publicreceipts);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);	
		return getListGoldTicket();		
	}
	
	//查看详情
	public String getDetailsFine(){
		
		HttpServletRequest request = ServletActionContext.getRequest();	
		String print = request.getParameter("print");
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String prID = request.getParameter("prID");
		System.out.println("prid:"+prID);		
		publicreceipts = (Publicreceipts)baseBeanService.getBeanByHqlAndParams("from Publicreceipts where prID=? ", new Object[]{prID});
		publicreceiptsChild = (PublicreceiptsChild)baseBeanService.getBeanByHqlAndParams("from PublicreceiptsChild where prID=? ", new Object[]{prID});				

		StringBuilder sb=new StringBuilder("select te.account,hr.staffname,te.custype,te.state");
		sb.append(" from T_ESHOP_CUSCOM te ,dt_hr_Staff hr");
		sb.append(" where te.staffid = hr.staffid and te.sccid = ? ");
		Object st=baseBeanService.getObjectBySqlAndParams(sb.toString(),new Object[]{publicreceipts.getRefundSccid()});
        request.setAttribute("st", st);

		String companyID = account.getCompanyID();
		String ohql = "from COrganization where companyID=? and Status = ?";
		organizationlist = baseBeanService.getListBeanByHqlAndParams(ohql,
				new Object[] { companyID, "00" });
		company = (Company) baseBeanService.getBeanByHqlAndParams(
				"from Company where companyID=?", new Object[] { companyID });
		
		COrganization co = (COrganization)baseBeanService.getBeanByHqlAndParams("from COrganization where organizationID = ? ", new Object[]{publicreceipts.getOrganizationID()});
		request.setAttribute("organizationName", co.getOrganizationName());
		
		if("print".equals(print)){
			return "print";
		}else{
			return "details";
		}		
	}
	
	/**
	 * 下载
	 * 
	 */
	public void downFile() {
		FileUtil fu = new FileUtil();
		try {
			fu.downFile(downLoadPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//金币折扣单 中查询
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearchFPub", publicreceipts);		
		return getListGoldTicket();		
	}
	
	//金币折扣 （审核）
	@SuppressWarnings("deprecation")
	public synchronized String PublicreceiptsAudit() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String view = request.getParameter("view");
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");	
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		Staff entity = (Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID = ? ", new Object[] { account.getStaffID() });
			
		Publicreceipts publicreceipts = (Publicreceipts)baseBeanService.getBeanByHqlAndParams("from Publicreceipts where prID=? ", new Object[]{prID});
		PublicreceiptsChild publicreceiptsChild = (PublicreceiptsChild)baseBeanService.getBeanByHqlAndParams("from PublicreceiptsChild where prID=? ", new Object[]{prID});
		List<BaseBean> beans = new ArrayList<BaseBean>();
		String str = "";		
		if(types.equals("部门主管审核通过")){
			publicreceipts.setReceiptsStatus("F");
			publicreceipts.setFirstAuditor(entity.getStaffName());			
			if(view == null || "".equals(view)){
				publicreceipts.setFirstAuditComments("符合要求，特此通过。");//意见
			}else{
				publicreceipts.setFirstAuditComments(view);//意见
			}			
			publicreceiptsChild.setFineDate(new Date());
			str = "部门主管审核通过";
		}else if (types.equals("部门主管驳回作废")) {
			publicreceipts.setReceiptsStatus("R");
			publicreceipts.setFirstAuditor(entity.getStaffName());
			if(view == null && "".equals(view)){
				publicreceipts.setFirstAuditComments("不符合要求，驳回 。");//意见
			}else{
				publicreceipts.setFirstAuditComments(view);//意见
			}			
			publicreceipts.setRefundDate(new Date());
			str = "部门主管驳回作废";
		}else if(types.equals("人力资源部审核通过")){
			publicreceipts.setReceiptsStatus("S");
			publicreceipts.setSecondAuditor(entity.getStaffName());
			if(view == null || "".equals(view)){
				publicreceipts.setSecondAuditComments("符合要求，特此通过。");//意见
			}else{
				publicreceipts.setSecondAuditComments(view);//意见
			}
			publicreceiptsChild.setFineDate(new Date());
			str = goldTicketService.fineGold(prID);				
		}else{//人事驳回作废
			publicreceipts.setReceiptsStatus("R");
			publicreceipts.setSecondAuditor(entity.getStaffName());
			if(view == null || "".equals(view)){
				publicreceipts.setSecondAuditComments("不符合要求，驳回 。");//意见
			}else{
				publicreceipts.setSecondAuditComments(view);
			}
			publicreceipts.setRefundDate(new Date());
			str = "人力资源部驳回作废";
		}
		
		if("该用户的金币数不足，系统驳回！".equals(str)){
			publicreceipts.setReceiptsStatus("R");
			publicreceipts.setSecondAuditor("系统录入");
			publicreceipts.setSecondAuditComments("该用户没有金币，系统驳回！");
			publicreceipts.setRefundDate(new Date());
		}
		beans.add(publicreceipts);	
		beans.add(publicreceiptsChild);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("str", str);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	
	}
	
	public String pass(){
		HttpServletRequest request = ServletActionContext.getRequest();				
		String receiptsStatus = request.getParameter("receiptsStatus");
		String view = request.getParameter("view");		
		String hql = " from Publicreceipts where prID = ? ";
		Publicreceipts publicreceipts = (Publicreceipts) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{prID});
		List<BaseBean> beans = new ArrayList<BaseBean>();
				
		if("待审".equals(receiptsStatus)){
			publicreceipts.setFirstAuditComments(view);
		}
		
		beans.add(publicreceipts);
		
		return "success";		
	}

	//导出金币折扣单
	@SuppressWarnings("unchecked")
	public String ShowExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account,organizationID);
		String sql =  list.get(0).toString();		
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(PublicreceiptsChild
				.columnFineHeadings(), baseBeanService
				.getListBeanBySqlAndParams(sql, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出金币罚单",account);
		baseBeanService.update(logBook);
		return "showrewardexcel";
	}
	
	public List<BaseBean> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<BaseBean> organizationlist) {
		this.organizationlist = organizationlist;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public Publicreceipts getPublicreceipts() {
		return publicreceipts;
	}

	public void setPublicreceipts(Publicreceipts publicreceipts) {
		this.publicreceipts = publicreceipts;
	}

	public PublicreceiptsChild getPublicreceiptsChild() {
		return publicreceiptsChild;
	}

	public void setPublicreceiptsChild(PublicreceiptsChild publicreceiptsChild) {
		this.publicreceiptsChild = publicreceiptsChild;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getDownLoadPath() {
		return downLoadPath;
	}

	public void setDownLoadPath(String downLoadPath) {
		this.downLoadPath = downLoadPath;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPrID() {
		return prID;
	}

	public void setPrID(String prID) {
		this.prID = prID;
	}

	public String getLabelTag() {
		return labelTag;
	}

	public void setLabelTag(String labelTag) {
		this.labelTag = labelTag;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getPri() {
		return pri;
	}

	public void setPri(String pri) {
		this.pri = pri;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getStaffname() {
		return staffname;
	}

	public void setStaffname(String staffname) {
		this.staffname = staffname;
	}
		
	

}
