package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.company.PrintInformation;
import hy.ea.bo.company.vo.ContactUser;
import hy.ea.bo.driving.DtDrivingCard;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.adance.Studentmanager;

import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

public class StudentManagerAction  implements ServletContextAware,SessionAware{
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CCodeService codeService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;
	private String result;
	private String search;// 判断是否搜索人员
	private PageForm pageForm;
	private ContactUser contactuser;
	private List<CCode> codeRelationList;
	private int pageNumber;
	private String showType;//add 添加 edit 修改 
	private PrintInformation printInfo;
	private String chicun;
	private Staff cstaff;
	private String staffID;
	private String staffIDS;
	private File photo;
	private ContactRelation contactrelation;
	private String photoFileName;
	private List<BaseBean> beans; 
	private String parameter;
	private Studentmanager studentmanager;
	private String module_title;

	//barcode 条码打印专用 ----------↓↓↓↓↓↓↓↓ 
	private Map<String, Object> reportParameter = null;   
    private String recordCode;//档案号String//↑↑↑↑↑↑↑↑↑↑↑↑↑
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", contactuser);
		return getStudentListCStaffByCompanyID();
	}
	
	public String getStudentManagerList(){
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		//加载默认民族
		List<CCode> codeNationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100331mk6yn5b5f60000000006");
		//加载默认籍贯
		List<CCode> codeNativePlaceList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode2010053143wpua87db0000000008");
		ActionContext.getContext().put("nations", codeNationList);
		ActionContext.getContext().put("nativeplaces", codeNativePlaceList);
		
		String hql = "from Studentmanager where companyID = ?";
		studentmanager = (Studentmanager)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});
		
		if(!showType.equals("add")){
			 Object[] param = {staffID};
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", param);
		}
		return "list";
	}
	//打印吊牌
	
	public String SaveprintInformation(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		String[] staffID = staffIDS.split(",");
		for (int i = 0; i < staffID.length; i++) {
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
					"from Staff where staffID=? ", new Object[] { staffID[i] });
			PrintInformation printInfos = new PrintInformation();
			printInfos.setCredentialsName(printInfo.getCredentialsName());
			printInfos.setCredentialsType(printInfo.getCredentialsType());
			printInfos.setPrintInfoID(serverService
					.getServerID("printinformation"));
			printInfos.setAccountID(account.getAccountID());
			printInfos.setCompanyID(account.getCompanyID());
			printInfos.setCreateDate(new Date());
			printInfos.setServeWay(printInfo.getServeWay());
			printInfos.setDateofissue(printInfo.getDateofissue());
			printInfos.setStaffCode(staff.getStaffCode());
			printInfos.setStaffName(staff.getStaffName());
			printInfos.setCredentialsCode(printInfo.getCredentialsCode());
			printInfos.setCredentialsTitle(printInfo.getCredentialsTitle());
			printInfos.setStaffIdentityCard(staff.getStaffIdentityCard());
			printInfos.setStaffID(staff.getStaffID());
			printInfos.setPhoto(staff.getPhoto());
			beans.add(printInfos);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
				null);
		if(chicun != null){
			return "bigprintpage";
		}
		return "printinfo";	
	}
	
	public DetachedCriteria getListBYDC()
	{ 
		DetachedCriteria dc=DetachedCriteria.forClass(ContactUser.class);
		CAccount account = (CAccount) session.get("account");
		 codeRelationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("relation", "学员"));
		if (search != null && search.equals("search")) {
			this.contactuser = (ContactUser) session.get("tablesearch");
			if (null != contactuser.getStaffIdentityCard()
					&& !"".equals(contactuser.getStaffIdentityCard())) {
				dc.add(Restrictions.like("staffIdentityCard", contactuser
						.getStaffIdentityCard(), MatchMode.ANYWHERE));
			}
			if (null != contactuser.getStaffName()
					&& !"".equals(contactuser.getStaffName())) {
				dc.add(Restrictions.like("staffName", contactuser
						.getStaffName(), MatchMode.ANYWHERE));
			}
		}
		return dc;
	}
	/**
	 *学员管理汇总列表
	 * 
	 * @return
	 */
	public String  getStudentListCStaffByCompanyID(){ 
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber),getListBYDC());
		if(pageForm!=null)
		{
			session.put("RecordCount", pageForm.getRecordCount());
		}
		 return  "academicadmin_list_student";
	}
	/**
	 * 保存学员管理启用项
	 * @return
	 */
	public String saveStudemtmanagerlist(){ 
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",
				new Object[] { account.getStaffID()});
		if(studentmanager.getStudentmanagerid() == null 
				|| "".equals(studentmanager.getStudentmanagerid())){
			studentmanager.setStudentmanagerid(serverService.getServerID("studentmanager"));
			parameter = staff.getStaffName() + "添加了学员管理启用项";
		}else{
			parameter = staff.getStaffName() + "修改了学员管理启用项";
		}
		studentmanager.setCompanyid(account.getCompanyID());
		beans = new ArrayList<BaseBean>();
		beans.add(studentmanager);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	
	/**
	 * 添加新的学员
	 * @return
	 */

	public String saveStudentList() {
		String groupCompanySn = session.get("groupCompanySn").toString();
		if (groupCompanySn == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		CAccount account = (CAccount) session.get("account");
		String hql = "select count(*) from Staff where  staffIdentityCard = ? and groupCompanySn = ? ";
		Object[] params = { cstaff.getStaffIdentityCard(), groupCompanySn };
		beans = new ArrayList<BaseBean>();
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		if (cstaff.getStaffID() == null || "".equals(cstaff.getStaffID())) {
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			cstaff.setStaffCode("NO" + pcount);
			cstaff.setRecordCode("NO" + pcount);
			cstaff.setVerifyTime(new Date());
			cstaff.setStaffID(serverService.getServerID("cstaff"));
			parameter = "添加员工（人员姓名：:" + cstaff.getStaffName() + ")";
		} else {
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { cstaff.getStaffID() });
			cstaff.setVerifyTime(new Date());
			parameter = "修改员工(人员名称:" + staff.getStaffName() + ")";
			if (staff.getStaffIdentityCard() != null
					&& !staff.getStaffIdentityCard().equals(""))
				if (staff.getStaffIdentityCard().equals(
						cstaff.getStaffIdentityCard())) {
					count = 0;
				}
		}
		if (count > 0) {
			return "此处为往来个人存在人员";
		}
		if (photo != null) {
			String path = context.getRealPath("/");
			String photoPath = fileService
					.savePhoto(
							path,
							photoFileName,
							photo,
							account.getCompanyID(),
							"/human/personPhotos/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
			if (cstaff.getPhoto() != null && !"".equals(cstaff.getPhoto())) {
				ArrayList<String> paths = new ArrayList<String>();
				paths.add(path + cstaff.getPhoto());
				fileService.deletePhotos(paths);
			}
			cstaff.setPhoto(photoPath);
		}
		cstaff.setGroupCompanySn(groupCompanySn);
		cstaff.setStaffStatus("00");
		beans.add(cstaff);
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		showType="edit";
		return getStudentManagerList();
	}
	
	DtDrivingPrincipal principal;
	/**
	 * 条码生成
	 * 传入员工号和档案号	 * 
	 * @return
	 */
	public String codebar(){
		beans = new ArrayList<BaseBean>();
		BaseBean bean = baseBeanService.getBeanByHqlAndParams("from DtDrivingPrincipal as bean where bean.studentid=?", new String[]{staffID});
		beans.add(bean);
		reportParameter = new HashMap<String, Object>();   
        reportParameter.put("Code",recordCode); 
        
		return "codebar";
	}
	/**
	 * 学员证打印
	 * @return
	 * @throws MalformedURLException 
	 */  
	public String printCard() {
	    StringBuilder buff = new StringBuilder(); 
		buff.append("select cc.registrationcarname, c.companyname, s.staffname,s.staffidentitycard,s.photo from dt_hr_staff s ");
		buff.append(" join dt_driving_principal cc on cc.studentid = s.staffid");
        buff.append(" join dtcompany c on c.companyid = cc.companyid");
        buff.append(" where c.companyid = ? and s.staffid = ?"); 
        
        List<BaseBean>	 beans =  baseBeanService.getListBeanBySqlAndParams(buff.toString(), new Object[]{parameter,staffID});
        Object obj = beans.get(0);
        Object[] objs = (Object[]) obj;
//        Object obj = baseBeanService.getObjectBySqlAndParams(buff.toString(), new Object[]{parameter,staffID});//页面上用parameter存储companyId
//            Object[] objs = (Object[]) obj;
            
            
            
            Object obj1 = baseBeanService.getObjectBySqlAndParams("select t.logo,e.scanningaccessories from dtcdetail t join dt_oa_enterprisestamp e on e.companyid = t.companyid where t.companyid = ? and e.isstustamp = 1", new Object[]{parameter});
            Object[] objs1 = (Object[]) obj1;
            
            HttpServletRequest request = ServletActionContext.getRequest();
            StringBuilder bufUrl = new StringBuilder();
            bufUrl.append(request.getScheme()).append("://");
            bufUrl.append(request.getServerName()).append(":");
            bufUrl.append(request.getServerPort()); 
            bufUrl.append(context.getContextPath());
            
    		reportParameter = new HashMap<String,Object>();
    		reportParameter.put("bg", bufUrl.toString() + "/jasper/driving/student_card_bg.jpg");
    		if(objs1!=null&&objs1[0] != null&&objs1[1]!=null){
    		reportParameter.put("logo",bufUrl.toString() + "/" + objs1[0]);
    		reportParameter.put("gongzhang",bufUrl.toString() + "/" + objs1[1]);
    		}
    		if(objs[1] != null){
    		reportParameter.put("companyname",objs[1]); 
    		}
    		if(objs[0] != null){
    		reportParameter.put("registrationcarname", objs[0]);
    		}
    		if(objs[2]!= null){
    		reportParameter.put("staffname", objs[2]);
    		}
    		if(objs[3]!= null){
    		reportParameter.put("staffidentitycard",objs[3]);
    		}
    		if(objs[4]!= null){
    		reportParameter.put("photo",bufUrl.toString() + "/" + objs[4]);
    		}
    		
    		
    		//记录打印次数
    		String hql = "from DtDrivingCard where studentId = ? and companyID = ?";
    		DtDrivingCard dcard = (DtDrivingCard) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{staffID,parameter});
    		if(dcard==null){
    		 dcard = new DtDrivingCard();
    		 dcard.setId(serverService.getServerID("dcard"));
     		 dcard.setPrintCount(1);
    		 dcard.setCompanyID(parameter);
    		 dcard.setStudentId(staffID);
    		 baseBeanService.save(dcard);
    		}else{
    			dcard.setPrintCount(dcard.getPrintCount()+1);
    			baseBeanService.update(dcard);
    		}
    		
		return "printCard";
	}
	
	
	/**
	 * 学员证打印背面
	 * @return
	 * @throws MalformedURLException 
	 */  
	public String printCardBack() {
	
            
            HttpServletRequest request = ServletActionContext.getRequest();
            StringBuilder bufUrl = new StringBuilder();
            bufUrl.append(request.getScheme()).append("://");
            bufUrl.append(request.getServerName()).append(":");
            bufUrl.append(request.getServerPort()); 
            bufUrl.append(context.getContextPath());
            
    		reportParameter = new HashMap<String,Object>();
    		reportParameter.put("bg", bufUrl.toString() + "/jasper/driving/student_card_bg.jpg");
    		
		
		    return "printCardBack";
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
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


	public List<CCode> getCodeRelationList() {
		return codeRelationList;
	}

	public void setCodeRelationList(List<CCode> codeRelationList) {
		this.codeRelationList = codeRelationList;
	}
	public ContactUser getContactuser() {
		return contactuser;
	}
	public void setContactuser(ContactUser contactuser) {
		this.contactuser = contactuser;
	}
	public Staff getCstaff() {
		return cstaff;
	}
	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}
	public Studentmanager getStudentmanager() {
		return studentmanager;
	}

	public void setStudentmanager(Studentmanager studentmanager) {
		this.studentmanager = studentmanager;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
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

	public ContactRelation getContactrelation() {
		return contactrelation;
	}

	public void setContactrelation(ContactRelation contactrelation) {
		this.contactrelation = contactrelation;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}
 
	public Map<String, Object> getReportParameter() {
		return reportParameter;
	}

	public void setReportParameter(Map<String, Object> reportParameter) {
		this.reportParameter = reportParameter;
	}

	public DtDrivingPrincipal getPrincipal() {
		return principal;
	}

	public void setPrincipal(DtDrivingPrincipal principal) {
		this.principal = principal;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	
	private ServletContext context;
	@Override
	public void setServletContext(ServletContext arg0) {
		this.context = arg0;
	}

	
	private Map<String, Object> session;
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}  
	public String getStaffIDS() {
		return staffIDS;
	}

	public void setStaffIDS(String staffIDS) {
		this.staffIDS = staffIDS;
	}

	public PrintInformation getPrintInfo() {
		return printInfo;
	}

	public void setPrintInfo(PrintInformation printInfo) {
		this.printInfo = printInfo;
	}

	public String getChicun() {
		return chicun;
	}

	public void setChicun(String chicun) {
		this.chicun = chicun;
	}

	public String getModule_title() {
		return module_title;
	}

	public void setModule_title(String module_title) {
		this.module_title = module_title;
	}

	
	
}
