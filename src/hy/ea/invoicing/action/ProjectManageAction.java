package hy.ea.invoicing.action;

import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.ProjectByIndustry;
import hy.ea.bo.invoicing.ProjectMCar;
import hy.ea.bo.invoicing.ProjectManage;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.Document;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.SCode;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;


/**
 * 项目管理
 * @author mz
 *
 */
@Controller
@Scope("prototype")
public class ProjectManageAction extends BaseAction<ProjectManage>{
	private static final long serialVersionUID = -2934872042375599964L;
	@Resource
	private ServerService serverService;
	@Resource
	private ShowExcelService excelService;
	private InputStream excelStream;
	private String search;
	private ProjectManage projectManage;
	private List<BaseBean> projectlist;
	private String parameter;
	private ProjectByIndustry pbyIndusty;
	private ProjectByIndustry recruiter;
	private ProjectMCar projectMcar;
	private Document document;//合同和规划
	private String type;
	private String audittype;//审核类型 通过 ，驳回
	private String selectDept;
	private Staff staff;
	private String view;//判断是否是查看，修改 ，添加
	private String codeID;
	private String sub;//手动防止表单提交
	  //产品
    private  ProductPackaging productPack;

	

		
	
		
  /**
   * 
   * 项目列表
   * @return
   */
  public String getProjectList(){
	
	  pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getDcList());
	  
	  return "projectlist";
	  
  }
  
  
  /**
   * 
   * 项目列表查询
   * @return
   */
  public String toSearch(){
	  Map<String, Object> session = ActionContext.getContext().getSession();
	  session.put("projectManage", projectManage);
	  return getProjectList();
  }
  
  
	public DetachedCriteria getDcList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(ProjectManage.class);
		dc.add(Restrictions.eq("companyID", account.getCompanyID()));
		dc.add(Restrictions.eq("organizationID", session.get("organizationID")));
		if(type!=null&&type.equals("wxm")){
			dc.add(Restrictions.eq("status", "01"));
			
			
		}
        if(type!=null&&type.equals("yxm")){
        	dc.add(Restrictions.or(Restrictions.eq("status","02"), Restrictions.eq("status","03")));
		}
		if (search != null && search.equals("search")) {
			projectManage = (ProjectManage) session.get("projectManage");
			if (projectManage.getProjectName() != null
					&& !projectManage.getProjectName().equals("")) {
				dc.add(Restrictions.like("projectName", projectManage
						.getProjectName().trim(), MatchMode.ANYWHERE));

			}
			if (projectManage.getXmtype() != null
					&& !projectManage.getXmtype().equals("")) {
				dc.add(Restrictions.eq("xmtypename",projectManage.getXmtype()));
			}
			
			
			if(projectManage.getStaffName()!=null&&!projectManage.getStaffName().equals("")){
				dc.add(Restrictions.or(Restrictions.like("staffCode",
						projectManage.getStaffCode(), MatchMode.ANYWHERE),
						Restrictions.like("staffName",
								projectManage.getStaffName(),
								MatchMode.ANYWHERE)));
			}
			//利用startDate和endDate传值而已
			if(projectManage.getStartDate()!=null&&!projectManage.getStartDate().equals("")){
				if(projectManage.getEndDate()==null||projectManage.getEndDate().equals("")){
					projectManage.setEndDate(new Date());
				}
				dc.add(Restrictions.between("updateDate", projectManage.getStartDate(), projectManage.getEndDate()));
			}
			dc.addOrder(Order.desc("updateDate"));
			
			

		}
		dc.addOrder(Order.asc("projectCode"));

		return dc;

	}
  
  
  /**
   * 
   * 
   * 获取添加页面
   * @return
   */
  public String getAddPage(){
	  Map<String, Object> session = ActionContext.getContext().getSession();
	  CAccount account = (CAccount) session.get("account");
	  if(projectManage==null||projectManage.getProID()==null||projectManage.getProID().equals("")){
	  String BillID = serverService.getBillID(account.getCompanyID());
	  projectManage = new ProjectManage();
	  projectManage.setProjectCode(BillID);
	 
	  }else{
		  String hql = "from ProjectManage where proID = ?";
		  projectManage = (ProjectManage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{projectManage.getProID()});
	  
	     if(projectManage.getXmtype().equals("0542")||projectManage.getXmtype().equals("0521")){
	    	 hql = "from ProjectByIndustry where projectID = ?";
	    	 pbyIndusty = (ProjectByIndustry) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{projectManage.getProID()});
	    
	     }
	     if(projectManage.getXmtype().startsWith("0240511")){
	    	 hql = "from ProjectMCar where projectID = ?";
	    	projectMcar = (ProjectMCar) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{projectManage.getProID()});
	     }
	     
	  }
	  
	//责任人name
	String hqlForMan = "from Staff where staffID = ?";
	staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlForMan,
									new Object[] { account.getStaffID() });
	  
	
	if(view.equals("add")||view.equals("update")){
		  session.put("session_value", Math.random() + "");
	}
	  return "addpage";
	  
  }
  
  /**
   * 
   * 
   * 添加和修改保存项目；
   * @return
   */
  public String saveProject(){
	  
	  Map<String, Object> session = ActionContext.getContext().getSession();
	  CAccount account = (CAccount) session.get("account");
	  
		 if(sub!=null&&!sub.equals("")){
				if(sub!=null&&sub.equals(session.get("session_value"))){
					session.remove("session_value");
				}else{
					view ="view";
				    projectManage.setProID((String)session.get("proID"));
				   return getAddPage();
				}
			  }
	  String hqlstaff = "from Staff where staffId = ?";
	  Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlstaff,new Object[]{account.getStaffID()});
	  if(projectManage.getProID()==null||projectManage.getProID().equals("")){
		  projectManage.setProID(serverService.getServerID("proid"));
		  projectManage.setCompanyID(account.getCompanyID());
		  projectManage.setOrganizationID((String)session.get("organizationID"));
		  
		  //创建人信息
		  projectManage.setCreateDate(new Date());
		  String levelNumber = getProjectNum(projectManage.getPproID());
		  
		  if(projectManage.getPproID()!=null&&!projectManage.getPproID().equals("")){
		     String hql = "from ProjectManage where proID = ?";
		     ProjectManage pc = (ProjectManage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{projectManage.getPproID()});
		     projectManage.setLevels(pc.getLevels()+1);
		     projectManage.setProjectCode(pc.getProjectCode()+levelNumber);
		     projectManage.setPcode(pc.getProjectCode());
		     projectManage.setLevelNumber(levelNumber);
		  }else{
			  projectManage.setProjectCode(levelNumber);
			  projectManage.setLevelNumber(levelNumber);
			  projectManage.setPcode(levelNumber);
			  projectManage.setLevels(1);
		  }
		  
		 
		  
		  
	  }else{
		  //删除之前的副表
		  ProjectByIndustry pbi =  (ProjectByIndustry) baseBeanService.getBeanByHqlAndParams("from ProjectByIndustry where projectID = ?", new Object[]{projectManage.getProID()});
		  if(pbi!=null){
			  baseBeanService.deleteBeanByKey(ProjectByIndustry.class,pbi.getReKey());
		  }
		  
		  ProjectMCar pmc = (ProjectMCar)baseBeanService.getBeanByHqlAndParams("from ProjectMCar where projectID = ?", new Object[]{projectManage.getProID()});
		  if(pmc!=null){
			  baseBeanService.deleteBeanByKey(ProjectMCar.class,pmc.getMcKey());
		  }

		  
	  }
	  
	  
	  //处理车辆
	  if(projectManage.getXmtype().startsWith("0240511")){
		  
		     projectMcar.setMcID(serverService.getServerID("mcid"));
		     projectMcar.setProjectID(projectManage.getProID());
		  

	  }
	  
	  //处理行业
	  if(projectManage.getXmtype().equals("0521")){

		   pbyIndusty.setReID(serverService.getServerID("inid"));
		   pbyIndusty.setProjectID(projectManage.getProID());
		  

	  }
	  
	  //处理招生员
	  
	  if(projectManage.getXmtype().equals("0542")){
		 
		  recruiter.setReID(serverService.getServerID("reid"));
		  recruiter.setProjectID(projectManage.getProID());
		 

	  }
			
	  projectManage.setCreateName(staff.getStaffName());
	  projectManage.setCreateCode(staff.getStaffCode());
	  projectManage.setCreateID(staff.getStaffID());
	  projectManage.setUpdateDate(new Date());//更新时间
	  
	  List<BaseBean> beanlist = new ArrayList<BaseBean>();
	  beanlist.add(projectManage);
	  beanlist.add(projectMcar);
	  beanlist.add(pbyIndusty);
	  beanlist.add(recruiter);
	  baseBeanService.saveBeansListAndexecuteHqlsByParams(beanlist,null,null);
	  projectManage.setUpdateDate(new Date());
	  view = "view";
	  session.put("proID", projectManage.getProID());
	  
	  return getAddPage();
	  
  }
  
  
 /**
  * 
  * 删除项目
  * @return
  */
	public String deleteProject() {
		String hql = "from CashierBills where csbID = ?";
		List<BaseBean> cashlist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { projectManage.getProID() });
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			if (cashlist.size() == 0) {
				baseBeanService.deleteBeanByKey(ProjectManage.class,
						((ProjectManage) baseBeanService.getBeanByHqlAndParams(
								"from ProjectManage where proID = ?",
								new Object[] { projectManage.getProID() }))
								.getProKey());
				map.put("result","success");
			}else{
				map.put("result", "fail");
			}
			JSONObject jo = JSONObject.fromObject(map);
			result = jo;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";

	}
	
	
	/**
	 * 
	 * 导出
	 * @return
	 */
	public String exportExcel(){

		
		List<BaseBean> list = baseBeanService.getListByDC(getDcList());
		excelStream = excelService.showExcel(ProjectManage.columnHeadings(),list);
		
		
		return "showexcel";
	}
	
	
	/**
	 * 
	 * 打印项目列表
	 * @return
	 */
	public String printList(){
		projectlist = baseBeanService.getListByDC(getDcList());
		
		return "printlist";
	}
	
	
	/**
	 * 
	 * 根据代码树获取项目分类
	 * @return
	 */
	public String getXmtypeByCode(){
		DetachedCriteria dc = DetachedCriteria.forClass(CCode.class);
		
		dc.addOrder(Order.asc("codeSn"));
		dc.add(Restrictions.eq("groupSn", "scode201410284shpd9x4fa0000000005"));
		dc.add(Restrictions.eq("companyID", this.getCurrentAccount().getCompanyID()));

		dc.add(Restrictions.or(Restrictions.like("codeSn",parameter.trim(),MatchMode.ANYWHERE),Restrictions.like("codeValue",parameter.trim(),MatchMode.ANYWHERE)));
        
	  
		List<BaseBean>  xmlist = baseBeanService.getListByDC(dc);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("xmlist",xmlist);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	
	
	/**
	 * 根据codeID查询其子节点Scode
	 */
	public String getListSCodeByPID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj;
			return "success";
		}
		
		String hql ="from SCode where  codePID = ?  order by codeNumber"; 
		List<BaseBean> codeList = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{codeID});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeList", codeList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj;
		return "success";
	}

	
	
	/**
	 * 
	 * 
	 * 获取当前部门包括子部门下的在职员工
	 * @return
	 */
	public String getStaffForOrg() {
 		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");

		

		String	hql = "from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and (c.organizationID = ? " +
					"or exists(select d.depPostID from DepartmentPost d where d.depPostID = c.depPostID and d.leveloneOrgID = ?))) and s.staffName like ?";
		

		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 20 : pageNumber), 
				hql,"select count(s) "+hql.substring(hql.indexOf("from")),
				new Object[]{ account.getCompanyID(), "50",selectDept,selectDept,"%"+parameter+"%"});
		
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	
	
	/**
	 * 
	 * 获取当前公司的在职员工
	 * @return
	 */
	public String getFormalStaffForCompany(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from Staff s where exists(select c.staffID from COS c where c.staffID = s.staffID and c.cosStatus  = ? and c.status = ? and c.companyID = ?) and s.staffName like ?";
		
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 20 : pageNumber), 
				hql,new Object[]{"50","01",account.getCompanyID(),"%"+parameter+"%" });
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo;
		return "success";
		
	}
	
	
	/**
	 * 
	 * 获取当前部门的车辆
	 * 可通过编号 车牌号  车架号 发动机号查询
	 * @return
	 */
	public String getCarInfoByOrg(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarInformation.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
        dc.add(Restrictions.or(
                Restrictions.or(
                  Restrictions.like("goodsCoding", parameter.trim(),MatchMode.ANYWHERE),
                  Restrictions.like("carNum", parameter.trim(),MatchMode.ANYWHERE)
                  ),
                   Restrictions.or(
                  Restrictions.like("engineNum", parameter.trim(),MatchMode.ANYWHERE),
                  Restrictions.like("carFrameNum", parameter.trim(),MatchMode.ANYWHERE)
                  )
                    ));
        pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 20 : pageNumber), dc);
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo;
		return "success";
		
	}
	
	
	/**
	 * 
	 * 
	 * 获取行业类别
	 * 可根据编号名称查询
	 * @return
	 */
	public String getIndustryCate() {
		DetachedCriteria dc = DetachedCriteria.forClass(SCode.class);

		dc.addOrder(Order.asc("codeSn"));
		dc.add(Restrictions.eq("groupSn", "scode20110106hfjes5ucxp0000000003"));

		dc.add(Restrictions.or(Restrictions.like("codeSn",
				parameter.trim(), MatchMode.ANYWHERE), Restrictions
				.like("codeValue", parameter.trim(),
						MatchMode.ANYWHERE)));

		List<BaseBean>  industrylist = baseBeanService.getListByDC(dc);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("xmlist", industrylist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo;
		return "success";
	}
	
	
	//////////////////////////////////////合同和规划/////////////////////////////////////////////////////////
   
	/**
	 * 
	 * 查询合同
	 * 
	 * @return
	 */
	public String toSearchForContract() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", document);
		return getContractByProject();
	}

	private DetachedCriteria getListByContract(String journalNum) {
		Map<String, Object> session = ActionContext.getContext().getSession();

		DetachedCriteria dc = DetachedCriteria.forClass(Document.class);
		dc.add(Restrictions.eq("journalNum",productPack.getPpID()));
		if (search != null && search.equals("search")) {
			document = (Document) session.get("tablesearch");
			if (document.getTitle() != null
					&& !"".equals(document.getTitle())) {
				dc.add(Restrictions.like("title",document.getTitle().trim(), MatchMode.ANYWHERE));
			}
		   
		}
		return dc;
	}
	
	
	
	/**
	 * 
	 * 获取项目的合同和规划
	 * @return
	 */
	public String getContractByProject() {
        //合同
		
	   pageForm = baseBeanService.getPageFormByDC(
					(null != pageForm ? pageForm.getPageNumber() : 1),
					(pageNumber == 0 ? 5 : pageNumber),
					getListByContract(productPack.getPpID()));
		
	


		return "doccontract";
	}
	
	
	
	private DetachedCriteria getListByPlan(String docIds) {
		Map<String, Object> session = ActionContext.getContext().getSession();
        String[] arraydocId = docIds.split(",");
		DetachedCriteria dc = DetachedCriteria.forClass(Document.class);
		dc.add(Restrictions.in("docId",arraydocId));
		
		if (search != null && search.equals("search")) {
			document = (Document) session.get("tablesearch");
			if (document.getTitle() != null
					&& !"".equals(document.getTitle())) {
				dc.add(Restrictions.like("title",document.getTitle().trim(), MatchMode.ANYWHERE));
			}
		   
		}
		return dc;
	}
	
	
	
	
	
	/**
	 * 
	 * 获取项目规划列表
	 */
	public String getxmPlanList(){
		try{
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String hql = "from Document where companyID = ? and organizationID = ? and status != ? and module in('cg','dg','pg','jg')";
		List<String> params = new ArrayList<String>();
		params.add(account.getCompanyID());
		params.add((String)session.get("organizationID"));
		params.add("D");
		if(search!=null&&search.equals("search")){
			hql+= " and title like ? and module like ?";
			params.add("%"+document.getTitle().trim()+"%");
			params.add("%"+document.getModule()+"%");
			
		}
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber), hql, params.toArray());
		
		if(pageForm!=null){
			String staffhql = "from Staff where staffID = ?";
			for(BaseBean b:pageForm.getList()){
				
				Document doc = (Document) b;
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(staffhql,new Object[]{doc.getDrafterID()});
			    doc.setDrafterName(staff.getStaffName());
			   
			}
		}
		Map<String,Object> map = new HashMap<String, Object>();
		

		JsonConfig cfg = new JsonConfig();
	    cfg.setRootClass(Document.class);
	    cfg.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object arg0, String arg1, Object arg2) {
                if (arg1.equals("attachFiles") || arg1.equals("subscribers")) {
                    return true;
                } else {
                    return false;
                }
            }
        });

	    map.put("pageForm", pageForm);
	    map.put("search",search);
        JSONObject oj = JSONObject.fromObject(map,cfg);
		
		this.result = oj;
		}catch(Exception e){
			e.printStackTrace();
		}
		return "success";
		
	}
	


	
	
	///////////////////////////////////////合同和规划结束///////////////////////////////////////////////////////////
	
	/**
	 * 
	 * 
	 * 项目提交审核
	 * @return
	 */
	public String  submitAudit(){
		
		String hql = "from ProjectManage where proID = ?";
		ProjectManage project = (ProjectManage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{projectManage.getProID()});
	    project.setSubmittime(new Date());//提交审核时间
	    project.setStatus("01");//待审核
	    baseBeanService.update(project);
		return "success";
		
	}
	
	
	
	/**
	 * 
	 * 
	 * 审核项目
	 * @return
	 */
	public String  auditProject(){
		
		String hql = "from ProjectManage where proID = ?";
		ProjectManage project = (ProjectManage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{projectManage.getProID()});
		
	    project.setAudittime(new Date());//审核时间
	    //audittype :02 通过 03驳回
	    project.setStatus(audittype);//待审核
	 
	    baseBeanService.update(project);
		return "success";
		
	}
	
	
	
	//获取项目编号
	
	/**
	 * 
	 * 获得编号 type:一个是编号docNum，一个是号numCode
	 * 
	 * @param companyID
	 * @param type
	 * @return
	 */
	public String getProjectNum(String pproID) {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String s = "";
		
        //看看父ID存不存在，如果是一级项目，不会存在
		String hql = "from ProjectManage where proID = ?";
	    ProjectManage pm = (ProjectManage) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{pproID}); 
	    int level = 0;
	    if(pm!=null){
	    	level = pm.getLevels();
	    }
	    List<Object> params = new ArrayList<Object>();
	    params.add(account.getCompanyID());
	    params.add(level+1);
       
	    String sql = "select max(to_number(levelNumber)) from dt_projectManage where companyID  = ? and levels = ?";
	    if(level>0){
	    	sql+=" and pcode= ?";
	    	params.add(pm.getProjectCode());
	    }
        int count = baseBeanService.getConutByBySqlAndParams("select count(*) "+sql.substring(sql.indexOf("from")), params.toArray());
		if(count==0){
			 
			if(level+1==1){
				s = "0001";
			}else if(level+1==2){
				s = "001";
			}else{
				s = "01";
			}
			return s;
		}
		int a = baseBeanService.getConutByBySqlAndParams(sql,
				params.toArray());
		
		
		int nextnum = a+1;
		
		
		
		
		if(level+1==1){
			if (nextnum > 0 && nextnum <= 9) {
				s = "000" + nextnum;
			} else if (nextnum > 9 && nextnum <= 99) {
				s = "00" + nextnum;
			} else if (nextnum > 99 && nextnum <= 999) {
				s = "0" + nextnum;
			} else if (nextnum > 999 && nextnum < 9999) {
				s = "" + nextnum;
			}
		}
		
		
		if(level+1==2){
			if (nextnum > 0 && nextnum <= 9) {
			s = "00" + nextnum;
		} else if (nextnum > 9 && nextnum <= 99) {
			s = "0" + nextnum;
		} else if (nextnum > 99 && nextnum <= 999) {
			s = "" + nextnum;
		 } 
		}
		
		if(level+1>2){
			if (nextnum > 0 && nextnum <= 9) {
			s = "0" + nextnum;
		} else if (nextnum > 9 && nextnum <= 99) {
			s = "" + nextnum;
		}
		}
		

		return s;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public ProjectManage getProjectManage() {
		return projectManage;
	}
	public void setProjectManage(ProjectManage projectManage) {
		this.projectManage = projectManage;
	}
	public List<BaseBean> getProjectlist() {
		return projectlist;
	}
	public void setProjectlist(List<BaseBean> projectlist) {
		this.projectlist = projectlist;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public ProjectByIndustry getPbyIndusty() {
		return pbyIndusty;
	}
	public void setPbyIndusty(ProjectByIndustry pbyIndusty) {
		this.pbyIndusty = pbyIndusty;
	}
	public ProjectMCar getProjectMcar() {
		return projectMcar;
	}
	public void setProjectMcar(ProjectMCar projectMcar) {
		this.projectMcar = projectMcar;
	}
	public ProjectByIndustry getRecruiter() {
		return recruiter;
	}
	public void setRecruiter(ProjectByIndustry recruiter) {
		this.recruiter = recruiter;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAudittype() {
		return audittype;
	}
	public void setAudittype(String audittype) {
		this.audittype = audittype;
	}
	public String getSelectDept() {
		return selectDept;
	}
	public void setSelectDept(String selectDept) {
		this.selectDept = selectDept;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getCodeID() {
		return codeID;
	}
	public void setCodeID(String codeID) {
		this.codeID = codeID;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}


	public ProductPackaging getProductPack() {
		return productPack;
	}


	public void setProductPack(ProductPackaging productPack) {
		this.productPack = productPack;
	}
    
	
	
}
