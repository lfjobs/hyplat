package hy.ea.ccompany.action;

import com.opensymphony.xwork2.ActionContext;
import hy.base.action.BaseAction;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.*;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CompanyService;
import hy.ea.service.UpLoadFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.SInterface;
import hy.plat.service.SInterfaceService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class CCOrganizationAction extends BaseAction<COrganization>{
	private COrganization porganization ;
	@Resource
	private COrganizationService organizationService;
	@Resource
	private CompanyService companyService;
	@Resource
	private SInterfaceService sinterfaceService;
	@SuppressWarnings("unused")
	@Resource
	private UpLoadFileService fileService;
	private String organizationID;
	private String organizationName;
	private List<COrganization> organizationlist;
	private List<COrganization> children;
	private List<SInterface> SInterfaceList;
	private InputStream excelStream;
	private String childrenID;
	private String selectedtreeID;
	private String parameter;
	private List<BaseBean> beans;
	private String corString;
	private List<DepartmentPost> deppostlist;
	private List<InstitutionsRegistration> institList; 
	private List<Agencies> agenciesList; 
	private List<Object> staffDepList; 
	private List<Organizationdesc> orgdescList;
	private int num;//存储微分金店的部门个数
	/**
	 * 公司
	 */
	private Company company;
	/**
	 * 部门
	 */
	private COrganization organization;
	/**
	 * 公司部门区别
	 */
	private String type;
	/**
	 * 模块区别
	 */
	private String mold;
	
	/**
	 * 打印
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toPrint(){

		
		String hql="from COrganization where organizationID=?";
		organization = (COrganization) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{organization.getOrganizationID()});
		SInterfaceList = sinterfaceService.getSInterfaceListByStatus();

		//职位集合
		String hql1 = "from DepartmentPost where organizationID = ?";
		beans = baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{organization.getOrganizationID()});
		deppostlist = new ArrayList<DepartmentPost>();
		for (int i = 0; i < beans.size(); i++) {
			deppostlist.add((DepartmentPost)(beans.get(i)));
		}
		//岗位人员
		String sql2 = "select s.staffID,s.staffCode,s.recordCode,da.categoryName,s.staffName,d.postName,s.usedNmae,"
				+ " s.sex,s.birthday,s.nationality,s.nativePlace,s.nation,s.staffIdentityCard,s.staffAddress,"
				+ " s.reference,s.referenceCode,s.referenceOrganization,s.verifyTime,s.politicsStatus,"
				+ " s.culturalDegree,s.marriage,s.health,s.bankNum,s.staffDesc,s.staffKey,s.address,s.photo"
				+ " from dtcos c"
				+ " left join dt_hr_deptpost d on c.depPostID = d.depPostID"
				+ " left join dtcorganization o on c.organizationID = o.organizationID"
				+ " right join dt_hr_staff s on c.staffID = s.staffID"
				+ " right join dtaudition da on c.staffID = da.staffID and da.companyID= ? and da.status = '22'"
				+ " where c.companyID = ? and c.organizationID = ? and c.cosStatus = '50'";
		List<Object> params = new ArrayList<Object>();
		params.add(this.getCurrentAccount().getCompanyID());
		params.add(this.getCurrentAccount().getCompanyID());
		params.add(organization.getOrganizationID());
		staffDepList = baseBeanService.getListBeanBySqlAndParams(sql2, params.toArray());
		//银行集合
		String hql3 = "from InstitutionsRegistration where organizationID = ?";
		beans = baseBeanService.getListBeanByHqlAndParams(hql3, new Object[]{organization.getOrganizationID()});
		institList = new ArrayList<InstitutionsRegistration>();
		for (int j = 0; j < beans.size(); j++) {
			institList.add((InstitutionsRegistration)(beans.get(j)));
		}
		//机构责任人
		String hql4 = "from Agencies where organizationID = ?";
		beans = baseBeanService.getListBeanByHqlAndParams(hql4, new Object[]{organization.getOrganizationID()});
		agenciesList = new ArrayList<Agencies>();
		for (int x = 0; x < beans.size(); x++) {
			agenciesList.add((Agencies)(beans.get(x)));
		}
		//机构职责
		String hql5 ="from Organizationdesc where organizationid = ?";
		beans = baseBeanService.getListBeanByHqlAndParams(hql5,  new Object[]{organization.getOrganizationID()});
		orgdescList = new ArrayList<Organizationdesc>();
		for (int c = 0; c < beans.size(); c++) {
			orgdescList.add((Organizationdesc)(beans.get(c)));
		}
		return "toPrint";
	}
 	
	/**
	 * 得到单位对象
	 * 
	 * @return
	 */
	public String getCompanyMessage() {
		company = companyService.getCompanyByCompanyID(this.getCurrentAccount().getCompanyID());
		if(type.equals("t")){//部门
			String orgID =ActionContext.getContext().getSession().get("organizationID").toString();
			organization = (COrganization)baseBeanService.getBeanByHqlAndParams("from COrganization o where o.organizationID = ?", new Object[]{orgID});
			if(mold.equals("m")){//人员配备
				return "list_f";
			}
		}else if(type.equals("e")){//公司
			if(mold.equals("m")){//人员配备
				return "list_f";
			}else if(mold.equals("o")){//职务
				return "list_a";
			}else if(corString.equals("l")){
				return "list_e";
			}
		}else{//其他
			return "list";
		}
		return "";
	}
	
	/**
	 * 动态验证机构名称
	 * @return
	 * @throws IOException
	 */
	public String validateName() throws IOException{
		Map<String, String> map = new HashMap<String, String>();
		String ajax = "";
		String ql = "select count(*) from COrganization o where o.companyID = ? and o.ocode = ? ";
		Object[] obj = null;
		if(!organization.getOrganizationID().equals("")){
			ql += " and o.organizationID <> ?";
			obj = new Object[]{this.getCurrentAccount().getCompanyID(),organization.getOcode().trim(),organization.getOrganizationID()};
		}else{
			obj = new Object[]{this.getCurrentAccount().getCompanyID(),organization.getOcode().trim()};
		}
		int i = baseBeanService.getConutByByHqlAndParams(ql, obj);
		if(i > 0){
			ajax="erro";
		}else{
			//根据机构名称查询是否存在
			String hql = "from COrganization c where c.companyID = ? and c.organizationPID = ? and c.organizationName = ?";
			Object[] params = new Object[]{this.getCurrentAccount().getCompanyID(),organization.getOrganizationPID(),organization.getOrganizationName()};
			COrganization cor = (COrganization)baseBeanService.getObjectByHqlAndParams( hql , params);
			
			if(cor!=null){ //存在
				if(cor.getStatus().equals("00")){
					if(organization.getOrganizationID().equals(cor.getOrganizationID())){
						//修改 机构id 存在 状态为00
						ajax="suc";
					}else{
						ajax="err";
						//重复创建   机构id 存在 状态为00
					}
				}else if("".equals(organization.getOrganizationID()) && cor.getStatus().equals("98")){
					//禁用 机构id 存在 状态为98 准备重新启用
					ajax="succ";
				}
			}else{ //无
				ajax="suc";
			}
		}
		map.put("ajaxString", ajax);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/**
	 * 重启禁用机构
	 * @return
	 */
	public String resOrganization(){
		String hql = "from COrganization where organizationPID = ? and organizationName = ? and status = ?";
		Object[] params = {organization.getOrganizationPID(),organization.getOrganizationName(),"98"};
		COrganization cor = (COrganization)baseBeanService.getObjectByHqlAndParams( hql , params);
		cor.setStatus("00");
		beans = new ArrayList<BaseBean>();
		beans.add(cor);
		parameter = "重启机构(机构名称:"+cor.getOrganizationName()+")";
		CLogBook logBook = logBookService.saveCLogBook(this.getCurrentAccount().getCompanyID(), parameter, this.getCurrentAccount());
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		corString = "66";
		organization = cor;
		
		//前台ajax调用返回
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getParameter("ogID") != null 
				&& "ogID".equals(request.getParameter("ogID"))){
			Map<String, String> map = new HashMap<String, String>();
			map.put("ogIDs", organization.getOrganizationID());
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			return "success";
		}
		
		return getOrganizationListAll();
	}
	// 保存机构
	public String saveOrganization() {
		DepartmentPost departmentPost = null;
		//添加
		if(null == organization.getOrganizationID() || organization.getOrganizationID().equals("")){
			organization.setOdutiesID(getOrgPostMaxNum());
			organization.setOrganizationID(serverService.getServerID("organization"));
			organization.setOrganizationCreateDate(new Date());
			organization.setCompanyID(this.getCurrentAccount().getCompanyID());
			organization.setStatus("00");
			departmentPost = new DepartmentPost();
			departmentPost.setDepPostID(serverService.getServerID("departmentPost"));
			departmentPost.setCompanyID(this.getCurrentAccount().getCompanyID());
			departmentPost.setLeveloneOrgID(getLeveloneOrgID(organization));
			departmentPost.setOrganizationID(organization.getOrganizationID());
			departmentPost.setPostNum(organization.getOdutiesID());
			departmentPost.setPostName(organization.getOdutiesName());  //职位名称
			departmentPost.setPostResponsibility(organization.getOrganizationDesc());
			departmentPost.setResponsibilityRequire(organization.getOpostRequirements());
			departmentPost.setPostSureNum("1");
			departmentPost.setAdminNum("0");
			departmentPost.setSpecialpostNum("0");
			departmentPost.setOmppostNum("0");
			parameter = "添加机构(机构名称:"+organization.getOrganizationName()+")";
		}else{//修改
			if( null == organization.getOdutiesID() || organization.getOdutiesID().matches("\\d+") == false){
				organization.setOdutiesID(getOrgPostMaxNum());
			}
			departmentPost = (DepartmentPost)baseBeanService.getBeanByHqlAndParams("from DepartmentPost d where d.companyID = ? and d.postNum = ?"
					, new Object[]{this.getCurrentAccount().getCompanyID(),organization.getOdutiesID()});
			if(null == departmentPost){
				departmentPost = new DepartmentPost();
				departmentPost.setDepPostID(serverService.getServerID("departmentPost"));
				departmentPost.setCompanyID(this.getCurrentAccount().getCompanyID());
				departmentPost.setLeveloneOrgID(getLeveloneOrgID(organization));
				departmentPost.setOrganizationID(organization.getOrganizationID());
				departmentPost.setPostNum(organization.getOdutiesID());
				departmentPost.setPostName(organization.getOdutiesName());  //职位名称
				departmentPost.setPostResponsibility(organization.getOrganizationDesc());
				departmentPost.setResponsibilityRequire(organization.getOpostRequirements());
				departmentPost.setPostSureNum("1");
				departmentPost.setAdminNum("0");
				departmentPost.setSpecialpostNum("0");
				departmentPost.setOmppostNum("0");
			}else{
				departmentPost.setPostName(organization.getOdutiesName());
			}
			parameter= "修改机构(机构名称:"+organization.getOrganizationName()+")";
		}
		
		
		CLogBook logBook = logBookService.saveCLogBook(this.getCurrentAccount().getCompanyID(), parameter, this.getCurrentAccount());
		beans = new ArrayList<BaseBean>();
		beans.add(logBook);
		beans.add(organization);
		beans.add(departmentPost);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		//前台ajax调用返回
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request.getParameter("ogID") != null 
				&& "ogID".equals(request.getParameter("ogID"))){
			Map<String, String> map = new HashMap<String, String>();
			map.put("ogIDs", organization.getOrganizationID());
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			return "success";
		}
		return getOrganizationListAll();
	}

	/**
	 * 公司下一级部门id
	 * @param org
	 * @return
	 */
	private String getLeveloneOrgID(COrganization org){
		if(!org.getOrganizationPID().contains("company")){
			org =(COrganization)baseBeanService.getBeanByHqlAndParams("from COrganization where organizationID = ? ", new Object[]{org.getOrganizationPID()});
			getLeveloneOrgID(org);
		}
		return org.getOrganizationID();
	}
	

	/**
	 * 自动职务编号
	 * 
	 * @return
	 */
	private String getOrgPostMaxNum() {
		String hql = "select max(d.postNum) from DepartmentPost d where companyID = ?";
		Object b = baseBeanService.getObjectByHqlAndParams(hql,
				new Object[] { this.getCurrentAccount().getCompanyID() });
		String maxcount = "00";
		if(b!=null){
			int m = Integer.parseInt(b.toString()) + 1 ;
			maxcount = "00" + m;
		}else{
			maxcount="000";
		}
		
		return maxcount;
	}

	
	// 删除机构，根据机构ID和对应的单位ID删除
	/**
	 *  机构删除后，招聘管理下的在职员工部门organizationID 变成 99
	 * @return
	 */
	public String delOrganization() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		if (organizationID.equals(companyID)) {
			organizationID = selectedtreeID;
			return getOrganizationListAll();
		}
			COrganization newOrganization = (COrganization) baseBeanService.getBeanByHqlAndParams("from COrganization where companyID=? and organizationID = ? ", new Object[]{companyID,organizationID});
			
		String sql = "select count(*) from dtCOrganization where organizationPID = ? and Status = '00'";
		Object[] paramsp = { newOrganization.getOrganizationID()};
		int i = baseBeanService.getConutByBySqlAndParams( sql , paramsp);
		
		if(i>0){
			corString = "22";
			organization =newOrganization;
			return getOrganizationListAll();
		}else{
			organization =newOrganization;
			CLogBook logBook = logBookService.saveCLogBook(companyID,"删除机构(机构名称："+ 
					newOrganization.getOrganizationName()
					+")", this.getCurrentAccount());
			beans = new ArrayList<BaseBean>();
			String hql1 = " delete from COA where companyID = ?  and organizationID = ?";
			String hql2 = " update COS set organizationID = '99' where companyID = ? and organizationID = ? "; 
			String hql3 = " update  COrganization set Status = '98' where companyID = ? and organizationID = ? ";
			String hql4 = " delete from DepartmentPost where companyID = ? and organizationID = ?"; 
			Object[] params = { companyID, organizationID };
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[] {hql1,hql2,hql3,hql4 }, params);
			//organizationService.updateOrganizationByID(organizationID,companyID );
			organizationID = selectedtreeID;
			
			corString = "77";
			
		}
		return getOrganizationListAll();
	}

	// 查看某个机构的详细信息
	public String toeditOrganization() {
		organization = (COrganization) baseBeanService.getBeanByKey(
				COrganization.class, organizationID);
		Map<String, COrganization> map = new HashMap<String, COrganization>();
		map.put("organization", organization);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	// 查看某个机构的详细信息
	public String getAjaxOrganization() {
		String hql="from COrganization where organizationID=?";
		organization = (COrganization) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{organizationID});
		Map<String, COrganization> map = new HashMap<String, COrganization>();
		map.put("organization", organization);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 查询机构树,根据机构ID和对应单位ID查询
	 */
	public String getOrganizationList() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		organizationID = request.getParameter("oID");
		List<COrganization>	organizationList = organizationService.getOrganizationList(
					(!"0".equals(organizationID) ? organizationID : companyID), companyID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationList", organizationList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	//用于公文流转未分发查询群发部门
	public String getOrganizationLists() {
		//String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		List<COrganization>	organizationList = organizationService.getOrganizationList(
					organizationID, organizationID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationList", organizationList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	// 导出Excel
	public String showExcel() {
		List<COrganization> olist = new ArrayList<COrganization>();
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		if(companyID == null){ //companyId
			if(organization.getOrganizationPID().contains("company")){
				companyID = organization.getOrganizationPID();
			}else{
				COrganization pcon =(COrganization) baseBeanService.getBeanByHqlAndParams("from COrganization where organizationID = ? ", new Object[]{organizationID});
				companyID = pcon.getCompanyID();
			}
		}
		organizationlist = organizationService.getOrganizationByID(
				organizationID, companyID, olist);
		excelStream = organizationService.exportOrganization(organizationlist);
		CLogBook logBook = logBookService.saveCLogBook(companyID,"导出机构树", this.getCurrentAccount());
		baseBeanService.update(logBook);
		return "showexcel";
	}

	/**
	 * 递归查询出下面所有的机构 lwt
	 */
	public String getOrganizationListAll() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		organizationID = request.getParameter("organizationID");
		if (companyID.equals(organizationID)) {
			Object[] params = { companyID };
			pageForm = baseBeanService
					.getPageForm(
							(null != pageForm ? pageForm.getPageNumber() : 1),
							(pageNumber==0?10:pageNumber),
							" from COrganization where Status = '00' and companyID = ?",
							params);
		} else {
			pageForm = organizationService.getPageForm(
					(null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber),
					organizationID, companyID);
		}
		SInterfaceList = sinterfaceService.getSInterfaceListByStatus();
		return "organizationlist";
	}
	/**
	 * 跳转添加修改页面
	 * @return
	 */
	public String toAdd() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		String news = request.getParameter("news");
		if(companyID == null){
			if(porganization.getOrganizationID().contains("company")){
				companyID = porganization.getOrganizationID();
			}else{
				String hql="from COrganization where organizationID=?";
				COrganization orgc = (COrganization) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{porganization.getOrganizationID()});
				companyID = orgc.getCompanyID();
			}
		}
		ActionContext.getContext().getSession().put("companyID",companyID);
		ActionContext.getContext().getSession().put("organizationID",organization.getOrganizationID());
		organizationlist = new ArrayList<COrganization>();
		if (null != organization.getOrganizationID() ) {
			organization = (COrganization) organizationService.getOrganization(
					companyID, organization.getOrganizationID());
			 company = (Company) companyService
				.getCompanyByCompanyID(companyID);

		} else if (porganization.getOrganizationID() != null) {
			organization.setOrganizationPID(porganization.getOrganizationID());
			organizationlist.add(porganization);
		}
		SInterfaceList = sinterfaceService.getSInterfaceListByStatus();
		String hql="select count(*) from COrganization where storageWFJ=00 and companyID=?";
		num=baseBeanService.getConutByByHqlAndParams(hql, new Object[]{companyID});

       if(news!=null&&!news.equals("")){
                return  "orgadd";
	   }

		return "add";
	}
	/**
	 * 修改机构去机构属性
	 */
	public String toAddgetorganizationlist() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		String OID = (String)ActionContext.getContext().getSession().get("organizationID");
		List<COrganization> corganizationlist = organizationService
			.getOrganizationList(companyID);
		organization = (COrganization) organizationService.getOrganization(
				companyID, OID);
		organizationlist = new ArrayList<COrganization>();
		for (int i = 0; i < corganizationlist.size(); i++) {
			COrganization corganization = corganizationlist.get(i);
			if (!corganization.getOrganizationID().equals(organizationID)) {
				organizationlist.add(corganization);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationList", organizationlist);
		map.put("organization", organization);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
		
	}

	/**
	 * 去排序子机构页面
	 */
	public String toSortChildOrganization() {
		String companyID=(String) ActionContext.getContext().getSession().get("companyID");
		String news = request.getParameter("news");
		if("news".equals(news)){
			companyID = getCurrentAccount()==null?"":getCurrentAccount().getCompanyID();
			organizationID = request.getParameter("organizationID");
		}
		children = organizationService.getOrganizationList(organizationID,
				companyID);
		if(news!=null&&news.equals("news")){
			request.setAttribute("children",children);
			return "orgsort";
		}
		return "sortchildren";
	}

	/**
	 * 排序子机构
	 * 
	 * @author zg
	 * @verson 2011-4-19
	 */
	public String sortChildOrganization() {
		String[] ids = childrenID.split("_");
		String hql = "";
		for (int i=0;i<ids.length;i++) {
			String id=ids[i];
			 hql += "update COrganization set organizationNumber = "+i+" where organizationID = '"+id+"'_";
		}
		String[] hqls = hql.substring(0, hql.length()-1).split("_");
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null,hqls,null);
		String news = request.getParameter("news");
		if("news".equals(news)){
			request.setAttribute("message","11");
			return "success";
		}

		return getOrganizationListAll();
	}
   ///////////////////////////////////////////////////////////////////////新版///////////////////////////////////////////////////////////////////////////////////////////////////////////

	public String getOrgTree() {
		company = companyService.getCompanyByCompanyID(this.getCurrentAccount()==null?"":this.getCurrentAccount().getCompanyID());

		return "orgtree";
	}

	// 保存机构
	public String saveOrg() {
		organizationService.saveOrg(getCurrentAccount(),organization);
		request.setAttribute("message","11");
		return "success";

	}


	// 删除机构，根据机构ID和对应的单位ID删除
	/**
	 *  机构删除后，招聘管理下的在职员工部门organizationID 变成 99
	 * @return
	 */
	public String delOrg() {
		String org = request.getParameter("organizationID");
		String companyID=getCurrentAccount().getCompanyID();
		organizationService.delOrg(companyID,org);
		return "success";
	}
	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public List<COrganization> getOrganizationlist() {
		return organizationlist;
	}

	public void setOrganizationlist(List<COrganization> organizationlist) {
		this.organizationlist = organizationlist;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public List<COrganization> getChildren() {
		return children;
	}

	public void setChildren(List<COrganization> children) {
		this.children = children;
	}

	public String getSelectedtreeID() {
		return selectedtreeID;
	}

	public void setSelectedtreeID(String selectedtreeID) {
		this.selectedtreeID = selectedtreeID;
	}

	public List<SInterface> getSInterfaceList() {
		return SInterfaceList;
	}

	public void setSInterfaceList(List<SInterface> interfaceList) {
		SInterfaceList = interfaceList;
	}

	public String getChildrenID() {
		return childrenID;
	}

	public void setChildrenID(String childrenID) {
		this.childrenID = childrenID;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public COrganization getPorganization() {
		return porganization;
	}

	public void setPorganization(COrganization porganization) {
		this.porganization = porganization;
	}

	public List<DepartmentPost> getDeppostlist() {
		return deppostlist;
	}

	public void setDeppostlist(List<DepartmentPost> deppostlist) {
		this.deppostlist = deppostlist;
	}

	public List<InstitutionsRegistration> getInstitList() {
		return institList;
	}

	public void setInstitList(List<InstitutionsRegistration> institList) {
		this.institList = institList;
	}

	public List<Agencies> getAgenciesList() {
		return agenciesList;
	}

	public void setAgenciesList(List<Agencies> agenciesList) {
		this.agenciesList = agenciesList;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMold() {
		return mold;
	}

	public void setMold(String mold) {
		this.mold = mold;
	}

	public String getCorString() {
		return corString;
	}

	public void setCorString(String corString) {
		this.corString = corString;
	}

	public List<Object> getStaffDepList() {
		return staffDepList;
	}

	public void setStaffDepList(List<Object> staffDepList) {
		this.staffDepList = staffDepList;
	}

	public List<Organizationdesc> getOrgdescList() {
		return orgdescList;
	}

	public void setOrgdescList(List<Organizationdesc> orgdescList) {
		this.orgdescList = orgdescList;
	}

	public COrganization getOrganization() {
		return organization;
	}

	public void setOrganization(COrganization organization) {
		this.organization = organization;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}


	
}
