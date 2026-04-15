package hy.ea.office.action;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.EnterpriseStamp;
import hy.ea.bo.office.ModifyStampPswLog;
import hy.ea.office.service.EnterpriceStampService;
import hy.ea.production.service.MaterialManageService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.*;

@Controller
@Scope("prototype")
/**
 * 企业印章管理
 */
public class EnterpriseStampAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private EnterpriseStamp enterpriseStamp;
	private ModifyStampPswLog pswLog;
	private PageForm pageForm;
	private InputStream excelStream;
	private String result;
	private String search;
	private List<BaseBean> staffList;
	private int pageNumber;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private List<EnterpriseStamp> liststamp;
	private String type;// 用于区分是部门，公司，集团
	@Resource
	private MaterialManageService scManageService;
	@Resource
	private EnterpriceStampService enterpriceStampService;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = ServletActionContext.getRequest().getSession();
	SessionWrap sw=SessionWrap.getInstance();
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseStamp);
		if (enterpriseStamp.getGore().equals("e")) {
			return getListEnterpriseStamp();
		} else {
			return getListGeneralStamp();
		}
	}

	/**
	 * 
	 * 得到电子印章
	 * 
	 * @return
	 */
	private DetachedCriteria getList(String gore, String type) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseStamp.class);
		if (type != null && !type.equals("")&&!type.equals("null")) {
			if (type.equals("group")) {
				String hql = "from Company where companyID = ?";

				hql = "select companyID from Company where companyPID = ? or companyID = ?";

				List<BaseBean> list = baseBeanService
						.getListBeanByHqlAndParams(hql,
								new Object[] { account.getCompanyID(),
										account.getCompanyID() });

				dc.add(Restrictions.in("companyID", list));

			} else {
				dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			}
		} else {
			dc.add(Restrictions.eq("companyID", account.getCompanyID()));
			dc.add(Restrictions.eq("organizationID", (String) session
					.get("organizationID")));
		}

		dc.add(Restrictions.eq("gore", gore));
		dc.addOrder(Order.desc("createTime"));
		if (search != null && search.equals("search")) {
			enterpriseStamp = (EnterpriseStamp) session.get("tablesearch");
			if (!"".equals(enterpriseStamp.getStampName())) {
				dc.add(Restrictions.like("stampName", enterpriseStamp
						.getStampName().trim(), MatchMode.ANYWHERE));
			}

			if (type != null && type.equals("company")) {
				if (!"".equals(enterpriseStamp.getOrganizationID())) {
					dc.add(Restrictions.like("organizationID", enterpriseStamp
							.getOrganizationID(), MatchMode.ANYWHERE));
				}
			}
			if (type != null && type.equals("group")) {
				if (!"".equals(enterpriseStamp.getOrganizationID())) {
					dc.add(Restrictions.like("organizationID", enterpriseStamp
							.getOrganizationID(), MatchMode.ANYWHERE));
				}
				if (!"".equals(enterpriseStamp.getCompanyID())) {
					dc.add(Restrictions.like("companyID", enterpriseStamp
							.getCompanyID(), MatchMode.ANYWHERE));
				}
			}
		}
		return dc;
	}

	public String getListEnterpriseStamp() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber),
				getList("e", type));
		String hqlorg = "from COrganization where organizationID = ?";
		String hqlcom = "from Company where companyID = ?";
		String hqlstaff = "from Staff where staffID = ?";
		COrganization org = null;
		Company company = null;
		Staff staff = null;
		if (pageForm != null) {
			for (BaseBean b : pageForm.getList()) {
				EnterpriseStamp stamp = (EnterpriseStamp) b;
				org = (COrganization) baseBeanService.getBeanByHqlAndParams(
						hqlorg, new Object[] { stamp.getOrganizationID() });
				staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hqlstaff, new Object[] { stamp.getResponsibleID()});
				if (org != null) {
					stamp.setOrganizationName(org.getOrganizationName());
				}
				company = (Company) baseBeanService.getBeanByHqlAndParams(
						hqlcom, new Object[] { stamp.getCompanyID() });
				if (company != null) {
					stamp.setCompanyName(company.getCompanyName());
				}
				if(staff!=null){
					stamp.setResponsibleName(staff.getStaffName());
				}
				

			}

		}
		return "enterprisestamp";
	}

	/**
	 * 
	 * 得到普通印章列表
	 * 
	 * @return
	 */

	public String getListGeneralStamp() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber),
				getList("g", type));
		String hqlorg = "from COrganization where organizationID = ?";
		String hqlcom = "from Company where companyID = ?";
		String hqlstaff = "from Staff where staffID = ?";
		COrganization org = null;
		Company company = null;
		Staff staff = null;
		if (pageForm != null) {
			for (BaseBean b : pageForm.getList()) {
				EnterpriseStamp stamp = (EnterpriseStamp) b;
				org = (COrganization) baseBeanService.getBeanByHqlAndParams(
						hqlorg, new Object[] { stamp.getOrganizationID() });
				staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hqlstaff, new Object[] { stamp.getResponsibleID()});
				if (org != null) {
					stamp.setOrganizationName(org.getOrganizationName());
				}
				if(staff!=null){
					stamp.setResponsibleName(staff.getStaffName());
				}
				if (type != null && type.equals("group")) {
					company = (Company) baseBeanService.getBeanByHqlAndParams(
							hqlcom, new Object[] { stamp.getCompanyID() });
					if (company != null) {
						stamp.setCompanyName(company.getCompanyName());
					}

				}

			}

		}
		return "generalstamp";
	}

	public String showExcel() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String gore = request.getParameter("gore");
		excelStream = excelService.showExcel(EnterpriseStamp.columnHeadings(),
				baseBeanService.getListByDC(getList(gore, type)));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				"导出企业印章管理", account);
		baseBeanService.update(logbook);
		return "showexcel";
	}

	public String addEnterpriseStamp() {
		try {
			ActionContext actionContext = ActionContext.getContext();
			Map<String, Object> session = actionContext.getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			if (photo != null) {
				String path = ServletActionContext.getRequest().getSession()
						.getServletContext().getRealPath("/");
				String photoPath = fileService.savePhoto(path, photoFileName,
						photo, account.getCompanyID(),
						"document//office/enterpriseStamp/"
								+ Utilities.getDateString(new Date(),
										"yyyy-MM-dd"));
				enterpriseStamp.setScanningAccessories(photoPath);
			}
			if (enterpriseStamp.getEnterpriseStampID() == null
					|| "".equals(enterpriseStamp.getEnterpriseStampID())) {
				enterpriseStamp.setEnterpriseStampID(serverService
						.getServerID("enterpriseStamp"));
				parameter = "添加企业印章管理(印章名称:" + enterpriseStamp.getStampName()
						+ ")";
			} else {
				String hql2 = "from EnterpriseStamp where companyID=?  and enterpriseStampID=?";
				EnterpriseStamp aff = (EnterpriseStamp) baseBeanService
						.getBeanByHqlAndParams(hql2, new Object[] {
								account.getCompanyID(),
								enterpriseStamp.getEnterpriseStampID() });
				parameter = "修改企业印章管理(印章名称:" + aff.getStampName() + ")";
			}
			List<BaseBean> beans = new ArrayList<BaseBean>();
			Date curtime = new Date(System.currentTimeMillis());
			enterpriseStamp.setCreateTime(curtime);
			enterpriseStamp.setOrganizationID(organizationID);
			enterpriseStamp.setCompanyID(account.getCompanyID());
			enterpriseStamp.setUseStatus("use");
			CLogBook logbook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			beans.add(enterpriseStamp);
			beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 使停用
	public String stopUseStamp() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(),
				enterpriseStamp.getEnterpriseStampID() };
		String hql2 = "from EnterpriseStamp where companyID = ?  and enterpriseStampID=?";
		EnterpriseStamp aff = (EnterpriseStamp) baseBeanService
				.getBeanByHqlAndParams(hql2, params);
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				"停用企业印章管理(印章名称:" + aff.getStampName() + ")", account);
		aff.setUseStatus("unuse");
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(aff);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	// 修改中的启用和停用
	public String operationUseStatus() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		Object[] params = { account.getCompanyID(),
				enterpriseStamp.getEnterpriseStampID() };
		String hql2 = "from EnterpriseStamp where companyID = ?  and enterpriseStampID=?";
		EnterpriseStamp aff = (EnterpriseStamp) baseBeanService
				.getBeanByHqlAndParams(hql2, params);
		aff.setUseStatus(enterpriseStamp.getUseStatus());
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(aff);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	public String delEnterpriseStamp() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Object[] params = { account.getCompanyID(),
				enterpriseStamp.getEnterpriseStampID() };
		String hql2 = "from EnterpriseStamp where companyID=?  and enterpriseStampID=?";
		EnterpriseStamp aff = (EnterpriseStamp) baseBeanService
				.getBeanByHqlAndParams(hql2, params);
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				"删除企业印章管理(印章名称:" + aff.getStampName() + ")", account);
		String hql = "delete from EnterpriseStamp where companyID=?  and enterpriseStampID=?";
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}

	// 修改印章密码；
	public String changeStampPsw() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		ServletRequest request = ServletActionContext.getRequest();
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String oldpsw = request.getParameter("oldpsw");
		String newpsw = request.getParameter("newpsw");
		String enterpriseStampID = request.getParameter("enterpriseStampID");
		String hql = "from EnterpriseStamp where enterpriseStampID=?";
		EnterpriseStamp enterpriseStamp = (EnterpriseStamp) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { enterpriseStampID });
		String sealpath = enterpriseStamp.getScanningAccessories().replace("/",
				"\\");
		String sSeal = path + sealpath;
		ESealX.Seal SealTool = new ESealX.Seal();
		boolean bResult;
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			bResult = SealTool.changePsw(sSeal, oldpsw, newpsw);

			if (bResult) {

				ModifyStampPswLog mspl = new ModifyStampPswLog();
				mspl.setPswLogId(serverService.getServerID("pswId"));
				mspl.setModifyTime(new Date());
				mspl.setModifyUser(account.getStaffID());
				mspl.setNewPsw(newpsw);
				mspl.setEnterpriseStampID(enterpriseStampID);
				baseBeanService.save(mspl);
				map.put("result", "suc");
			} else {
				map.put("result", "fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/*
	 * 
	 * 
	 * 获取印章密码修改记录
	 * 
	 */

	public String getStampPswLog() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		String enterpriseStampID = request.getParameter("enterpriseStampID");

		String hql = "from ModifyStampPswLog where enterpriseStampID = ?";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 8 : pageNumber), hql,
				new Object[] { enterpriseStampID });
		if (pageForm != null) {
			String hqlstaff = "from Staff where staffID = ?";
			Staff staff = null;
			for (BaseBean b : pageForm.getList()) {
				pswLog = (ModifyStampPswLog) b;
				staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,
						new Object[] { pswLog.getModifyUser() });
				pswLog.setModifyUserName(staff.getStaffName() + "("
						+ staff.getStaffCode() + ")");
			}
		}

		return "pswlog";
	}

	// 或得印章信息；范围为公司ID
	public String queryStampFileInfo() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		ServletRequest request = ServletActionContext.getRequest();
		String type = request.getParameter("type");
		String hql = "from EnterpriseStamp where companyID=? and type=? and useStatus = ? and gore = ? and scanningAccessories is not null";
		List<BaseBean> bbstamplist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { account.getCompanyID(), type, "use", "e" });
		liststamp = new ArrayList<EnterpriseStamp>();
		for (BaseBean beanlist : bbstamplist) {
			EnterpriseStamp cb = (EnterpriseStamp) beanlist;
			liststamp.add(cb);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", liststamp);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}
	
	
	
	/**
	 * 
	 * 设置为学员证上的章
	 * @return
	 */
	public String setStudentStamp() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
        String companyID = account.getCompanyID();
        String hql1= "from EnterpriseStamp where companyID=? and isStuStamp = ?";
        EnterpriseStamp stamp1 = (EnterpriseStamp) baseBeanService
		.getBeanByHqlAndParams(hql1, new Object[]{companyID,"1"});
        if(stamp1!=null){
        	stamp1.setIsStuStamp(null);
        	baseBeanService.update(stamp1);
        }
        
        String hql = "from EnterpriseStamp where enterpriseStampID=?";
		EnterpriseStamp stamp = (EnterpriseStamp) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[]{enterpriseStamp.getEnterpriseStampID()});
		stamp.setIsStuStamp("1");
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(stamp);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
 /////////////////////////////////////////////////////////////////////////////////手机端///////////////////////////////////////////////////////////////////////////////////////

	/**
	 *
	 * 获取印章
	 * @return
	 */
	public String getStampList(){

		String search = request.getParameter("search");
		String parameter = request.getParameter("parameter");

		String staffID = "";
		String companyID = "";
		HttpSession session = ServletActionContext.getRequest().getSession();

		CAccount account = (CAccount) session.getAttribute("account");

		staffID = account.getStaffID();
		companyID = account.getCompanyID();
		System.out.println(parameter);
        List<BaseBean> list =  enterpriceStampService.getStampList(staffID,companyID,parameter);
        if("search".equals(search)){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("stamplist",list);
            JSONObject jo = JSONObject.fromObject(map);
            result = jo.toString();
            return "success";
		}
		request.setAttribute("stamplist",list);


	return "stampm";

	}

	/**
	 *
	 * 删除印章
	 * @return
	 */
	public String deleteStamp(){


			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");

			String staffID  = account.getStaffID();


		enterpriceStampService.deleteStamp(staffID,enterpriseStamp.getEnterpriseStampID());
          return "success";
   }

	/**
	 *
	 * 印章使用状态修改
	 * @return
	 */
	public String setUseStamp(){
		String sccId = request.getParameter("sccId");

		String staffID = "";
		String companyID = "";
		if (sccId != null && !sccId.equals("")) {
			TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
			staffID = tc.getStaffid();
		} else {
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");

			staffID = account.getStaffID();

		}
		enterpriceStampService.setUseStamp(staffID,enterpriseStamp.getEnterpriseStampID());
		return "success";
   }

	/**
	 *
	 * 添加修改印章
	 * @return
	 */
   public String addUpdateStamp(){


	   CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");

	   String  staffID = account.getStaffID();
	   String  companyID = account.getCompanyID();

	   String path = ServletActionContext.getRequest().getSession()
			   .getServletContext().getRealPath("/");
	   if (photo != null) {

		   String photoPath = fileService.savePhoto(path, photoFileName,
				   photo, companyID,
				   "document//office/enterpriseStamp/"
						   + Utilities.getDateString(new Date(),
						   "yyyy-MM-dd"));
		   enterpriseStamp.setScanningAccessories(photoPath);
	   }

	      enterpriceStampService.addUpdateStamp(staffID,companyID,enterpriseStamp,path);
	     request.setAttribute("message",request.getParameter("message"));
         return "success";
   }

	/**
	 *
	 *   添加修改页面
	 * @return
	 */
	public String getAddPage() {
		if (enterpriseStamp != null) {
			enterpriseStamp = enterpriceStampService.getEditInfo(enterpriseStamp.getEnterpriseStampID());

		}

		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String noauth = "0";
		if (account != null) {

			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{account.getStaffID()});
			if (staff != null && staff.getRealname() != null && !staff.getRealname().equals("")) {
				{
					noauth = "1";
				}



			}

			if(account.getCompanyID()!=null&&!account.getCompanyID().equals("")){
				ContactCompany contactCompany = scManageService.queryMessage(account);
				request.setAttribute("authState", contactCompany.getAuthState());
				if (contactCompany.getAuthState()!=null&&contactCompany.getAuthState().equals("02")){
                     //查询君子签是否认证
					String sql = "select r.emailOrMobile from dtContactCompany c left join DT_ccom_com m on c.ccompanyID = m.ccompany_Id  left join dt_sft_ApplyParam p on c.applyid = p.applyid left join dt_sft_BusinessLicenseInfo b on p.bliKey = b.bliKey left join dt_sft_ApplyResult r on r.out_request_no = p.out_request_no where m.compnay_id = ?";

					Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{account.getCompanyID()});

					String email = obj != null ? obj.toString() : "";//邮编

					if(email.equals("")){
						request.setAttribute("authState", "04");
					}
				}
			}



		}
		request.setAttribute("noauth", noauth);
		return "stampu";
	}

///////////////////////////////////////////////////////////////审核///////////////////////////////////////////////////
	public String getAuditStampList(){
    CAccount account = (CAccount) request.getSession().getAttribute("account");
    if(account==null){
		return "not_login";
	}

	if("search".equals(search)){
		Map<String, Object> session = ActionContext.getContext().getSession();
		enterpriseStamp = (EnterpriseStamp) session.get("tablesearch");
	}

		pageForm = enterpriceStampService.getAuditStampList(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber),enterpriseStamp.getStampName(),account.getCompanyID(),enterpriseStamp.getAuditStatus());


       return "toauditlist";

	}


	public String toSearchAudit(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", enterpriseStamp);

              return getAuditStampList();
	}

	/**
	 *
	 *
	 * 审核
	 * @return
	 */
	public String auditStamp(){
		CAccount account = (CAccount) request.getSession().getAttribute("account");
		if(account==null){
			return "not_login";
		}

		enterpriceStampService.auditStamp(enterpriseStamp.getEnterpriseStampID(),enterpriseStamp.getAuditStatus(),enterpriseStamp.getRejectReason(),account.getStaffID());

		return "success";
	}



	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<BaseBean> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<BaseBean> staffList) {
		this.staffList = staffList;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public EnterpriseStamp getEnterpriseStamp() {
		return enterpriseStamp;
	}

	public void setEnterpriseStamp(EnterpriseStamp enterpriseStamp) {
		this.enterpriseStamp = enterpriseStamp;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public List<EnterpriseStamp> getListstamp() {
		return liststamp;
	}

	public void setListstamp(List<EnterpriseStamp> liststamp) {
		this.liststamp = liststamp;
	}

	public ModifyStampPswLog getPswLog() {
		return pswLog;
	}

	public void setPswLog(ModifyStampPswLog pswLog) {
		this.pswLog = pswLog;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
