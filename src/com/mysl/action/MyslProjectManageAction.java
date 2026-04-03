package com.mysl.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.DtMyproject;
import com.mysl.bo.DtMyproprogress;
import com.mysl.bo.DtMystaff;
import com.opensymphony.xwork2.ActionContext;
/*
 * 项目管理
 * */
@Controller
@Scope("prototype")
public class MyslProjectManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private String parameter;
	private DtMyproject project;//项目
	private DtMystaff mystaff;//项目人员
	private DtMyproprogress myproprogress;//项目进度统计
	private PageForm pageForm;
	private String search;
	private int pageNumber;
	private String type;//编辑项目的标题add:添加项目,edit:修改项目
	private String manager;//项目责任人
	private String member;//项目成员
	private List<BaseBean> stafflist;
	private List<BaseBean> projectlist;
	private List<BaseBean> tasklist;
	private List<BaseBean> gresslist;
	private String addtime;//修改的时候不改变创建时间的参数
	private String result;//ajax返回结果
	private String Proid;//ajax参数
	private String cost3;//费用百分比
	private InputStream excelStream;
	@Resource
	private ShowExcelService excelService;
	private String excel;
    //导出项目列表
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		List<BaseBean> list = null;
	    list = baseBeanService.getListByDC(getList());
	    List<BaseBean> lists=new ArrayList<BaseBean>();
	    	for(int i=0;i<list.size();i++)
	    	{
	    		DtMyproject	project=new DtMyproject();
	    		project=(DtMyproject) list.get(i);
	    		Object[] params = {project.getProid()};
			    String hqlstaff="from DtMystaff where proid=?";
			    stafflist= baseBeanService.getListBeanByHqlAndParams(hqlstaff, params);
			    if(stafflist!=null)
			    {
			    	String manager="";
			    	String member="";
			    	for(int j=0;j<stafflist.size();j++){
			    		DtMystaff staff=(DtMystaff) stafflist.get(j);
			    	    String identity=staff.getIdentity();
			    	    if(identity.equals("00")){
			    	    	manager+=staff.getStaffname()+";";
			    	    }else if(identity.equals("01"))
			    	    {member+=staff.getStaffname()+";";}
			    		
			    	}
			    	project.setManager(manager);
			    	project.setMember(member);
			    }
			    lists.add(project);
	    	}
	    
	    excelStream = excelService.showExcel(DtMyproject.columnHeadings(),lists);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出项目列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	//ajax获取对应任务表数据
	public String getTaskByproid() {
		List<String> params = new ArrayList<String>();
		String hql= "from  DtMytask t where t.proid = ?";
		params.add(Proid);
		List<BaseBean> tasklist = baseBeanService.getListBeanByHqlAndParams(
				hql, params.toArray());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tasklist", tasklist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		
		return "success";
	}
	//ajax获取对应公司项目表数据
		public String getProjectCode() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			Object[] params = {account.getCompanyID()};
			String hql= "from  DtMyproject where companyid=?";
			List<BaseBean> prolist = baseBeanService.getListBeanByHqlAndParams(
					hql, params);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("prolist", prolist);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			
			return "success";
		}
	//根据条件查询项目
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", project);
		return getDtMyprojectList();
	}
	
	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		DetachedCriteria dc = DetachedCriteria.forClass(DtMyproject.class);
		dc.add(Restrictions.eq("companyid", account.getCompanyID()));
		dc.addOrder(Order.desc("addDate"));
		if (search != null && search.equals("search")) {
			project = (DtMyproject) session.get("tablesearch");
			if (!"".equals(project.getProcode())){
				dc.add(Restrictions.like("procode", project.getProcode(), MatchMode.ANYWHERE));
			}
			if (!"".equals(project.getProname())){
				dc.add(Restrictions.like("proname",project.getProname(),MatchMode.ANYWHERE));
			}
			if (!"".equals(project.getContactcompany())){
				dc.add(Restrictions.like("contactcompany", project.getContactcompany(), MatchMode.ANYWHERE));
			}
			if (project.getStartdate()!= null){
				dc.add(Restrictions.eq("startdate",project.getStartdate()));
			}
			if (project.getPlanfinishdate()!= null){
				dc.add(Restrictions.eq("planfinishdate",project.getPlanfinishdate()));
			}
			if (!"".equals(project.getStraffName())){
				dc.add(Restrictions.like("straffName", project.getStraffName(), MatchMode.ANYWHERE));
			}
			
	    }
		return dc;
	}
	// 跳转添加页面
	public String toAddProject() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if(type.equals("add")){
			type="add";
			/** 项目编号自动排序*/
	        SimpleDateFormat matter=new SimpleDateFormat("yyyy");
	        String year1 = matter.format(new Date());
	        Object[] params = {account.getCompanyID()};
	        String hql="from DtMyproject where companyid=?";
			projectlist= baseBeanService.getListBeanByHqlAndParams(hql,params);
			String code1="";
			String code2="";
			int num=0;
			if(projectlist.size()==0)
			{
				code1=year1;
				code2="01";
				
			}
			else if(projectlist.size()>0)
			{
				for(int j=0;j<projectlist.size();j++)
				{
					DtMyproject project=(DtMyproject) projectlist.get(j);
					Date date=project.getAddDate();
			        String year2 = matter.format(date);
			        if(year1.equals(year2))
			        {num+=1;}
				}
				num+=1;
				if(num<=9)
				{
					String num1="0"+num;
					code1=year1;
					code2=num1;
				}else{
					code1=year1;
					code2=String.valueOf(num);
				}
			}
			session.put("procode1", code1);
			session.put("procode2", code2);
			}
		if(type.equals("edit")){
			 type="edit";
			 String hql="from DtMyproject where proid=?";
			 project=(DtMyproject) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{project.getProid()});
			 String hqlstaff="from DtMystaff where proid=?";
			 stafflist= baseBeanService.getListBeanByHqlAndParams(hqlstaff, new Object[]{project.getProid()});
		}
		return "toprojectAdd";
	}
	// 项目列表
	public String getDtMyprojectList() {
	   pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
	   return "projectlist";	
	}
	// 项目查看详情进度方法
	public String toDetailProject(){
		 /**项目基本信息**/
		 String hql="from DtMyproject where proid=?";
		 project=(DtMyproject) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{project.getProid()});
		 /**项目人员信息**/
		 String hqlstaff="from DtMystaff where proid=?";
		 stafflist= baseBeanService.getListBeanByHqlAndParams(hqlstaff, new Object[]{project.getProid()});
		 /**项目任务信息**/
		 String hqltask="from DtMytask where proid=?";
		 tasklist= baseBeanService.getListBeanByHqlAndParams(hqltask, new Object[]{project.getProid()});
		 /**项目任务进度信息**/
		 String hqlgress="from DtMyproprogress where proid=?";
		 gresslist= baseBeanService.getListBeanByHqlAndParams(hqlgress, new Object[]{project.getProid()});
		 /**收支费用**/
		 String sql = "select sum(money) from DT_MySzcost where proid = ? and costtype = ?";
		 //应收
		 Object cost11 = baseBeanService.getObjectBySqlAndParams(sql,new Object[]{project.getProid(),"00"});
		 //已收
		 Object cost22 = baseBeanService.getObjectBySqlAndParams(sql,new Object[]{project.getProid(),"01"});
		 if(cost11==null&&cost22==null){
			 cost3="暂无";
	 	 }
		 else if(cost11!=null&&cost22==null){
			 cost3="0%";
		 }
		 else{
			 float cost1 =Float.parseFloat(cost11.toString());//应收
		 	 float cost2 =Float.parseFloat(cost22.toString());//已收
		 	 float cost=(cost2/cost1);
			 DecimalFormat df = new DecimalFormat("#.##%");
			 cost3=df.format(cost);
		 }
		 return "toprojectDetail";
	}
	//项目完成更新factfinishdate实际完成时间
	public String finishedDtMyproject() throws ParseException{
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		DtMyproject dtproject=new DtMyproject();
		if (project.getProid()!= null|| !"".equals(project.getProid())) {
			 String hql2="from DtMyproject where proid=?";
			 dtproject=(DtMyproject) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{project.getProid()});
		     parameter = "项目完成(项目名称:"+dtproject.getProname()+")";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logbook);
		Date date =new Date();
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd");
        String da=matter.format(date);
        Date  dd= matter.parse(da);
		dtproject.setFactfinishdate(dd);
		beans.add(dtproject);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
	}
   //新建项目
    public String saveDtMyproject() throws ParseException
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		Company company = (Company) session.get("currentcompany");
		if (project.getProid()== null|| "".equals(project.getProid())) {
			project.setProid(serverService.getServerID("project"));
			parameter = "添加项目(项目名称:"+project.getProname()+")";
			Date dt=new Date();
	        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String tt=matter.format(dt);
	        Date  ttt= matter.parse(tt);
	        project.setAddDate(ttt);
		}
		else
		{
			 String hql1="from DtMyproject where proid=?";
			 DtMyproject dtproject=(DtMyproject) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{project.getProid()});
		     parameter = "修改项目(项目名称:"+dtproject.getProname()+")";
		     Object[] params = {project.getProid()};
		     /**删除项目的人员 */
		     String hql2="delete from DtMystaff where proid=?";
		     baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql2}, params);
		     /**删除修改项目的人员进度 */
		     List<BaseBean> list=new ArrayList<BaseBean>();
			 String hql3= "from  DtMyproprogress s where s.proid = ? and s.staffid " +
			 		"not in(select t.staffid from DtMytask t where t.proid = ?)";
			 list= baseBeanService.getListBeanByHqlAndParams(hql3, new Object[]{project.getProid(),project.getProid()});
		     if(list!=null){
		    	 for(int i=0;i<list.size();i++)
			     {
			    	 DtMyproprogress progress=(DtMyproprogress) list.get(i);
			    	 String hqls="delete from DtMyproprogress where progid=?";
				     baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqls},new Object[]{progress.getProgid()});
			     }
		     }
		     SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		     Date  date= matter.parse(addtime);
		     project.setAddDate(date);
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		project.setCompanyid(company.getCompanyID());
		project.setCompanyname(company.getCompanyName());
		String hql4 = "from Staff where staffID = ?";
		Staff sta = (Staff) baseBeanService.getBeanByHqlAndParams(hql4,
				new Object[] { account.getStaffID() });
		project.setStaffID(sta.getStaffID());
		project.setStraffName(sta.getStaffName());
        String pid=project.getProid();
        String pname=project.getProname();

        //循环存储项目负责人和人员 
        if(manager!=""||!manager.equals(""))
        {for(int i=0;i<manager.split(",").length;i++){
        	DtMystaff mystaff=new DtMystaff();
        	mystaff.setMystaffid(serverService.getServerID("mystaff"));
        	mystaff.setProid(pid);
        	mystaff.setProjectname(pname);
        	mystaff.setIdentity("00");//身份(项目负责人00,任务执行人01)
        	String hql5 = "from Staff where staffID = ?";
    		Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams(hql5,
    				new Object[] {manager.split(",")[i].split("-")[0]});
    		mystaff.setStaffid(sf.getStaffID());
    		mystaff.setStaffname(sf.getStaffName());
        	mystaff.setCompanyID(company.getCompanyID());
        	mystaff.setCompanyName(company.getCompanyName());
        	mystaff.setOrganizationID(manager.split(",")[i].split("-")[1]);
        	String hql6 = "from COrganization where organizationID = ?";
        	COrganization  co= (COrganization) baseBeanService.getBeanByHqlAndParams(hql6,
    				new Object[] {manager.split(",")[i].split("-")[1]});
            mystaff.setOrganizationName(co.getOrganizationName());
        	beans.add(mystaff);
		}	
        }
        if(member!=""||!member.equals(""))
        {for(int j=0;j<member.split(",").length;j++){
        	String hql7 = "from Staff where staffID = ?";
        	String memberstaffid=member.split(",")[j].split("-")[0];
    		Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams(hql7,
    				new Object[] {memberstaffid});
        	/**项目进度数据存储开始 */
    		if(type.equals("edit")){
	    		List<BaseBean> list=new ArrayList<BaseBean>();
	   			String hql8= "from  DtMyproprogress s where s.proid = ? and s.staffid " +
	   			 "in(select t.staffid from DtMytask t where t.proid = ?)";
	   			list= baseBeanService.getListBeanByHqlAndParams(hql8, new Object[]{project.getProid(),project.getProid()});
	   			//有任务的人员
	   			if(list!=null){
	   				 int x = 0;
			    	 for(int i=0;i<list.size();i++)
				     {
			    		 DtMyproprogress progress=(DtMyproprogress) list.get(i);
				    	 String memberstaffid1=progress.getStaffid();
				    	 if(memberstaffid1.equals(memberstaffid)){
				    		 x++;
				    		 break;
				    	 }else{x=0;}
				    	 
				     }
			    	 if(x==0)
			    	 {
			    		 DtMyproprogress  myprogre=new DtMyproprogress();
				         myprogre.setProgid(serverService.getServerID("myproprogress"));
				         myprogre.setProid(pid);
				         myprogre.setStaffid(sf.getStaffID());
				         myprogre.setStaffname(sf.getStaffName());
				         myprogre.setTasknumbyone(0);
				         myprogre.setScsjnum(0);
				         myprogre.setSjwcnum(0);
				         myprogre.setTjcgnum(0);
				         myprogre.setXmdanum(0);
				         beans.add(myprogre);
			    	 }
			    	
			     }
    		}
    		else if(type.equals("add")){
    			DtMyproprogress  myprogre=new DtMyproprogress();
	         	myprogre.setProgid(serverService.getServerID("myproprogress"));
	         	myprogre.setProid(pid);
	         	myprogre.setStaffid(sf.getStaffID());
	         	myprogre.setStaffname(sf.getStaffName());
	         	myprogre.setTasknumbyone(0);
	         	myprogre.setScsjnum(0);
	         	myprogre.setSjwcnum(0);
	         	myprogre.setTjcgnum(0);
	         	myprogre.setXmdanum(0);
	         	beans.add(myprogre);
    		}
        	/**项目进度数据存储结束 */
        	/**项目成员开始 */
        	DtMystaff mystaff=new DtMystaff();
        	mystaff.setMystaffid(serverService.getServerID("mystaff"));
        	mystaff.setProid(pid);
        	mystaff.setProjectname(pname);
        	mystaff.setIdentity("01");//身份(项目负责人00,任务执行人01)
    		mystaff.setStaffid(sf.getStaffID());
    		mystaff.setStaffname(sf.getStaffName());
        	mystaff.setCompanyID(company.getCompanyID());
        	mystaff.setCompanyName(company.getCompanyName());
        	mystaff.setOrganizationID(member.split(",")[j].split("-")[1]);
        	String hql9 = "from COrganization where organizationID = ?";
        	COrganization  co= (COrganization) baseBeanService.getBeanByHqlAndParams(hql9,
    				new Object[] {member.split(",")[j].split("-")[1]});
            mystaff.setOrganizationName(co.getOrganizationName());
        	beans.add(mystaff);
        	/**项目成员结束 */
		}
        }
        beans.add(project);
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	return "success";
    }
    //删除项目
	 public String delDtMyproject()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID");
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {project.getProid()};
		    String hql2="from DtMyproject where proid=?";
		    DtMyproject dtproject=(DtMyproject) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除项目(项目名称:"+dtproject.getProname()+")", account);
	    	
		    String hql="delete from DtMyproject where proid=?";
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
			return "success";
	    }

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getExcel() {
		return excel;
	}


	public void setExcel(String excel) {
		this.excel = excel;
	}


	public DtMyproject getProject() {
		return project;
	}

	public void setProject(DtMyproject project) {
		this.project = project;
	}
	public DtMystaff getMystaff() {
		return mystaff;
	}
	public void setMystaff(DtMystaff mystaff) {
		this.mystaff = mystaff;
	}
	public DtMyproprogress getMyproprogress() {
		return myproprogress;
	}
	
	public void setMyproprogress(DtMyproprogress myproprogress) {
		this.myproprogress = myproprogress;
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

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public List<BaseBean> getStafflist() {
		return stafflist;
	}

	public void setStafflist(List<BaseBean> stafflist) {
		this.stafflist = stafflist;
	}


	public List<BaseBean> getProjectlist() {
		return projectlist;
	}

	public void setProjectlist(List<BaseBean> projectlist) {
		this.projectlist = projectlist;
	}

	public List<BaseBean> getTasklist() {
		return tasklist;
	}
	public void setTasklist(List<BaseBean> tasklist) {
		this.tasklist = tasklist;
	}
	public List<BaseBean> getGresslist() {
		return gresslist;
	}
	public void setGresslist(List<BaseBean> gresslist) {
		this.gresslist = gresslist;
	}
	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getProid() {
		return Proid;
	}
	public void setProid(String proid) {
		Proid = proid;
	}
	public String getCost3() {
		return cost3;
	}
	public void setCost3(String cost3) {
		this.cost3 = cost3;
	}

}
