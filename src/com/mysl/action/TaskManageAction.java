package com.mysl.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Remind;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.DocumentTemplate;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.DtMyattach;
import com.mysl.bo.DtMyattachrecord;
import com.mysl.bo.DtMyaudit;
import com.mysl.bo.DtMypassrecord;
import com.mysl.bo.DtMyproject;
import com.mysl.bo.DtMyproprogress;
import com.mysl.bo.DtMystaff;
import com.mysl.bo.DtMytask;
import com.mysl.bo.DtSzcost;
import com.mysl.service.OfficeService;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;

/*
 * 任务管理
 * */
@Controller
@Scope("prototype")
public class TaskManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	/**
	 * 
	 * 孟竹
	 * 
	 */

	 @Resource
	 private OfficeService officeService;
	 @Resource
	 private RemindService remindService; 
	 private DtMytask mytask;//任务实体
	 private DtMyproject myproject;//项目实体
	 private String result;
	 private String startDate;//计划开始时间
	 private String endDate;//计划结束时间
	 private DtMyattach myattach;//附件
	 private DtSzcost myszcost;
	 private List<BaseBean> auditlist;
	 private List<BaseBean> attachlist;
	 private List<BaseBean> attachlist2;
	 private List<BaseBean> attrecordlist;
	 private String cost1 = "0.0";//应收
	 private String cost2 = "0.0";//已收
	 private String cost3 = "0.0";//未收
	 private String num;//序号
	 
	
	/**
	 * 
	 * 娄飞
	 * 
	 */
	 
	 private List<BaseBean> passrecordlist;  //传阅记录
	 private String auditid;//审核id
	 private String auditurl;//审核路径

	/**
	 * 
	 * 许亚洲
	 * 
	 */

	/**
	 * 
	 * 张灿
	 * 
	 */
	 @Resource
	 private ShowExcelService excelService;
	/**
	 * 获得session
	 */
	private Map<String, Object> session = ActionContext.getContext()
			.getSession();
	/**
	 * 获得account
	 */
	private CAccount account = (CAccount) ActionContext.getContext()
			.getSession().get("account");
	/**
	 * 传阅人 id  与  name  map集合
	 */
	private Map<String, DtMypassrecord>  dtMypassrecordMap;
	/**
	 * 任务 id map集合
	 */
	private Map<String, DtMytask>  dtMytaskMap;
	/**
	 * 审核
	 */
	private Map<String, DtMyaudit> dtMyauditMap;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;

	public int getPageNumber() {
		return pageNumber;
	}

	/****************************************************************** 孟竹开始 ******************************************/

	/**
	 * 任务通知查询
	 * 
	 * 
	 */
	public String toSearchByTaskNotice() {
		session.put("tableSearch", mytask);
		return getTaskNoticeList();
	}
	
	/**
	 * 
	 * 
	 * 获取任务列表
	 */
	public String getTaskNoticeList() {
		String hql = "from DtMytask where  proid = ?";
		List<Object> params = new ArrayList<Object>();
//		System.out.println(myproject.getProid());
		params.add(myproject.getProid());
        
		if (search != null && "search".equals(search)) {
			DtMytask mytask = (DtMytask) session.get("tableSearch");
			if (mytask != null) {
				if (mytask.getTaskname() != null
						&& !"".equals(mytask.getTaskname().trim())) {
					hql += " and taskname like ?";
					params.add("%" + mytask.getTaskname().trim() + "%");
				}
			}
		}
		hql+=" order by createtime desc";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params.toArray());
		
		return "tasknotice";

	}

	/**
	 * 
	 * 
	 * 点击添加任务 跳转到新建页面
	 * 
	 * @return
	 */
	public String addNewTaskPage() {
		String hql = "";
		 if(mytask!=null&&!"".equals(mytask.getTaskid())){
	    	  
	    	 hql = "from DtMytask where taskid = ?";
	    	 mytask = (DtMytask) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{mytask.getTaskid()});
	    	 
	    	 hql = "from DtMyattach where taskid = ? and  type = ? order by status asc,uploadtime desc";
	 		//附件记录
	 		 attachlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{mytask.getTaskid(),"00"});
	           
	          
	      }
	
		 hql = "from DtMyproject where proid = ?";
         myproject = (DtMyproject) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{myproject.getProid()});
        
     	
         
       
		return "newtask";
	}
	
	

	/**
	 * 
	 * 
	 * ajax获取编码序号
	 * @return
	 */
	public String ajaxGetNum() {

		String num = officeService.getTaskSequenceNum(mytask.getTasktype(),
				mytask.getCompanyid(), mytask.getOrgid(), mytask.getProid());
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result",num);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";

	}
	
	
	
	/**
	 * 
	 * 
	 * 判断编号重复
	 * @return
	 */
	public String checkCodeRepeat() {

		// 合同制定2014—01—SJ 公司

		// 生产设计 生计通-年份-序号 公司

		// 生产任务通知书 生通+部门全称-年份-序号 部门

		// 设计大纲 设计大纲-项目编号-序号 项目

		// 绘图任务 绘图-项目编号-序号 项目

		String result = officeService.checkCodeRepeat(mytask.getTasktype(),
				mytask.getCompanyid(), mytask.getOrgid(), mytask.getProid(),
				mytask.getTaskcode());
		
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("result",result);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		
		

		return "success";

	}

	/**
	 * 
	 * 
	 * 查看任务页面
	 * 
	 * @return
	 */
	public String seeTaskPage() {
		String hql = "from DtMytask where taskid = ?";
		mytask = (DtMytask) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{mytask.getTaskid()});
		
		//获取审核历史
		hql = "from DtMyaudit where taskid = ? order by audittime desc";
		auditlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{mytask.getTaskid()});
		
		hql = "from DtMyattach where taskid = ? and  type = ? order by status asc,uploadtime desc";
		//附件记录
		attachlist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{mytask.getTaskid(),"01"});
		
     	 hql = "from DtMyattach where taskid = ? and  type = ? order by status asc,uploadtime desc";
		//任务附属附件
		 attachlist2 = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{mytask.getTaskid(),"00"});
		
		//操作记录
		hql = "from DtMyattachrecord where taskid = ? order by operatetime desc";
		attrecordlist = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{mytask.getTaskid()});
		//传阅记录
		hql = "from DtMypassrecord where taskid = ? order by passtime desc";
		passrecordlist= baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{mytask.getTaskid()});
		return "seetask";
	}
	
	
	
	
	
	

	/**
	 * 
	 * 保存更新任务同时下达任务
	 * @return
	 */
	public String saveTask(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
		.get("account");
		String taskid = "";
		
		try {
			if(mytask.getTaskid()==null||mytask.getTaskid().equals("")){
				taskid = serverService.getServerID("taskid");
				
				mytask.setTaskid(taskid);
				
				mytask.setPhasestatus("01");
				
				//添加任务进度的个数
				DtMyproprogress myproprogress=(DtMyproprogress)baseBeanService.getBeanByHqlAndParams("from DtMyproprogress where proid=? and staffid=?", new Object[]{mytask.getProid(),mytask.getStaffid().split("-")[0]});
				if(myproprogress!=null){
					myproprogress.setTasknumbyone(myproprogress.getTasknumbyone()+1);
					baseBeanService.update(myproprogress);
				}
				
				
				
				//提醒
				Remind remind = new Remind();
				remind.setRemindID(serverService.getServerID("remind"));
				remind.setCircularType("02");
				remind.setCircularTitle(mytask.getTaskname());
				remind.setCircularText("您项目管理生产设计阶段有一个待办任务，请及时处理！");
				remind.setStaffID(mytask.getStaffid().split("-")[0]);
				remind.setStaffName(mytask.getStaffid().split("-")[3]);
				remind.setOrganizationID(mytask.getStaffid().split("-")[1]);
				remind.setCompanyID(mytask.getStaffid().split("-")[2]);
				remind.setAddDate(new Date());
				remind.setRemindStatus("01");
				remind.setRemindType("01");
				remind.setDetailedurl("ea/taskmanage/ea_getListByTaskManageProduction.jspa?myproject.proid="+mytask.getProid());
				remindService.addremind(remind);
				
				
			}
			
			
			mytask.setCreatetime(new Date());
			mytask.setDistributeid(account.getStaffID());
			String hql = "from Staff where staffID = ?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getStaffID()});
			mytask.setDistributeName(staff.getStaffName());
			//处理执行人的信息
			if(mytask.getStaffid()!=null&&!mytask.getStaffid().equals("")){
				String[] staffinfo = mytask.getStaffid().split("-");
				mytask.setStaffid(staffinfo[0]);
				mytask.setOrgid(staffinfo[1]);
				mytask.setCompanyid(staffinfo[2]);
				mytask.setStaffname(staffinfo[3]);
				mytask.setOrgname(staffinfo[4]);
				mytask.setCompanyname(staffinfo[5]);
			}
			
			baseBeanService.update(mytask);
			
			
			
			//附件
			if(!"".equals(taskid)){
				String hql2 = "from DtMytask where taskid = ?";
				mytask = (DtMytask) baseBeanService.getBeanByHqlAndParams(hql2,new Object[]{taskid});
			}
			String filepath = myattach.getFilepath();
			String filename = myattach.getFilename();
			String fileformat =myattach.getFileformat();
			if(!filepath.equals("")){
			String[] filepaths = filepath.split(",");
			String[] filenames = filename.split(",");
			String[] fileformats = fileformat.split(",");
			DtMyattach attach = null;
			for (int i = 0; i < filepaths.length; i++) {
				attach = new DtMyattach();
				attach.setAttachid(serverService.getServerID("attachid"));
				attach.setFileformat(fileformats[i]);
				attach.setFilename(filenames[i]);
				attach.setFileformat(fileformats[i]);
				attach.setFilepath(filepaths[i]);
				attach.setUploadtime(new Date());
				attach.setProid(mytask.getProid());
				attach.setTaskid(mytask.getTaskid());
				attach.setStaffid(mytask.getStaffid());
				attach.setStaffname(mytask.getStaffname());
				attach.setStatus("00");
				attach.setType("00");
				baseBeanService.save(attach);
				
			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return "success";
	}
	

	/**
	 * 
	 * 删除已保存的附件
	 * @return
	 */
	 public String deleteAttach(){
		 try{
		 String hql = "from DtMyattach where attachid = ?";
		 DtMyattach attach = (DtMyattach) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{myattach.getAttachid()});
		 
		 //删除文件
		String realpath = ServletActionContext.getRequest().getRealPath("/");
		File file = new File(realpath+attach.getFilepath());
		if(file.exists()){
			file.delete();
		}
		
		  baseBeanService.deleteBeanByKey(DtMyattach.class,attach.getAttachkey());
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		
		 return "success";
	 }
	
	/**
	 * 
	 * 
	 * 获取office具体模板，根据office类型不用了
	 * @return
	 */
	public String getOfficeTemplate() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "from DocumentTemplate where companyId  = ? and fileType = ?";
		List<BaseBean> templist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { account.getCompanyID(),
						mytask.getFiletype() });
		
		
		//创建office
	
		String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		
		DocumentTemplate dtemp = null;
		if(templist.size()!=0){
		   dtemp  = (DocumentTemplate)templist.get(0);
		}
		String storepath = "upload_files/mysl/"+account.getCompanyID();
		
		String officePath = officeService.createOffice(realpath, mytask.getFiletype(), dtemp!=null?dtemp
					.getTemplatePath():null, storepath);
	
		//创建office结束
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templist", templist);
		map.put("docPath", officePath);
		map.put("filetype",mytask.getFiletype());
		
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}
	
	
	
	
	/**
	 * 
	 * 保存Office
	 * @return
	 */
	public String saveOffice() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String realpath = request.getSession()
		.getServletContext().getRealPath("/");
        HttpServletResponse response = ServletActionContext.getResponse();
        String docPath = request.getParameter("docPath");
        System.out.println(docPath);
		officeService.saveOffice(request, response, realpath, docPath);
		return "none";
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 根据项目ID获取该项目的执行人
	 * 
	 * @return
	 */
	public String getExpeopleByproID() {

		String hql = "from DtMystaff where proid = ? and identity = ?";
		List<BaseBean> mystafflist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { myproject.getProid(), "01" });
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", mystafflist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}
	
	
	
	/**
	 * 
	 * 下达任务
	 * @return
	 */
         //添加时间
	public String assignTask(){
		String hql = "from DtMytask where taskid = ?";
		mytask = (DtMytask) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{mytask.getTaskid()});
		mytask.setPhasestatus("01");
		mytask.setAuditstatus("00");
		baseBeanService.update(mytask);
		
		//提醒
		Remind remind = new Remind();
		remind.setRemindID(serverService.getServerID("remind"));
		remind.setCircularType("02");
		remind.setCircularTitle(mytask.getTaskname());
		remind.setCircularText("您项目管理生产设计阶段有一个待办任务，请及时处理！");
		remind.setStaffID(mytask.getStaffid());
		remind.setStaffName(mytask.getStaffname());
		remind.setOrganizationID(mytask.getOrgid());
		remind.setCompanyID(mytask.getCompanyid());
		remind.setAddDate(new Date());
		remind.setRemindStatus("01");
		remind.setRemindType("01");
		remind.setDetailedurl("ea/taskmanage/ea_getTaskNoticeList.jspa?myproject.proid="+mytask.getProid());
		remindService.addremind(remind);
		return "success";
	}
	
	
	
	
	/**
	 * 
	 * 
	 * 保存上传附件
	 * @return
	 */
	public String saveUploadAttach(){
		String filepath = myattach.getFilepath();
		String filename = myattach.getFilename();
		String fileformat =myattach.getFileformat();
		String[] filepaths = filepath.split(",");
		String[] filenames = filename.split(",");
		String[] fileformats = fileformat.split(",");
		DtMyattach attach = null;
		for (int i = 0; i < filepaths.length; i++) {
			attach = new DtMyattach();
			attach.setAttachid(serverService.getServerID("attachid"));
			attach.setFileformat(fileformats[i]);
			attach.setFilename(filenames[i]);
			attach.setFileformat(fileformats[i]);
			attach.setFilepath(filepaths[i]);
			attach.setUploadtime(new Date());
			attach.setProid(myattach.getProid());
			attach.setTaskid(myattach.getTaskid());
			attach.setStaffid(myattach.getTaskid());
			attach.setStaffname(myattach.getStaffname());
			attach.setStatus("00");
			attach.setType("01");
			baseBeanService.save(attach);
			//记录上传操作
			recordOperateAttach(attach.getAttachid(),"03");
		}
		
		return "success";
	}
	
	
	
	/**
	 * 
	 * 操作附件 作废以及恢复
	 * @return
	 */
     public String operateAttach(){
    	 String hql = "from DtMyattach where attachid = ?";
    	 DtMyattach  attach = (DtMyattach) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{myattach.getAttachid()});
    	 if("00".equals(attach.getStatus())){
    		 attach.setStatus("01");
    		 
    	 }else{
    		 attach.setStatus("00"); 
    	 }
    	 baseBeanService.update(attach);
    	 recordOperateAttach(attach.getAttachid(),attach.getStatus());
    	 
    	 
    	 return "success";
     }
     
     
     
     
     /**
      * 
      * 
      * 记录附件操作历史
      * @param taskid
      * @param operate
      */
     public void recordOperateAttach(String attachid,String operate){
    	 try{
    	 
    	 String hql = "from DtMyattach where attachid = ?";
    	 
    	 DtMyattach  attach = (DtMyattach) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{attachid});
    	 
    	 DtMyattachrecord attachrecord = new DtMyattachrecord();
    	 attachrecord.setRecordid(serverService.getServerID("recordid"));
    	 attachrecord.setAttachid(attach.getAttachid());
    	 attachrecord.setFilename(attach.getFilename());
    	 attachrecord.setFilepath(attach.getFilepath());
    	 attachrecord.setOperatetime(new Date());
    	 attachrecord.setProid(attach.getProid());
    	 attachrecord.setTaskid(attach.getTaskid());
    	 attachrecord.setStaffid(attach.getStaffid());
    	 attachrecord.setStaffname(attach.getStaffname());
    	 attachrecord.setStatus(operate);
    	 baseBeanService.save(attachrecord);
    	 }catch(Exception e){
    		 e.printStackTrace();
    	 }
    	
     }
	
	
     
     ////////////收支费用////////
     
     /**
      * 
      * 
      * 
      * 收支费用列表
      * @return
      */
     public String getSzcostList(){
    	 
 		String hql = "from DtSzcost where  proid = ?";
 		List<Object> params = new ArrayList<Object>();
 		params.add(myproject.getProid());
         
 		if (search != null && "search".equals(search)) {
 			DtSzcost myszcost = (DtSzcost) session.get("tableSearch");
 			if (myszcost != null) {
 				if (myszcost.getCosttype() != null
 						&& !"".equals(myszcost.getCosttype().trim())) {
 					hql += " and costType like ?";
 					params.add("%"+myszcost.getCosttype().trim()+"%");
 				}
 			}
 		}
 		hql+=" order by createtime desc";
 		pageForm = baseBeanService.getPageForm(
 				(null != pageForm ? pageForm.getPageNumber() : 1),
 				(pageNumber == 0 ? 10 : pageNumber), hql, params.toArray());
 		
 		
 		
 		//计算应收，已收，未收金额
 		DecimalFormat fnum = new DecimalFormat("###,###.0");
 		if(pageForm!=null){
 		for(BaseBean b:pageForm.getList()){
 			DtSzcost szcost = (DtSzcost) b;
 			szcost.setMoneystr(fnum.format(szcost.getMoney()));
 		}
 		String sql = "select sum(money) from DT_MySzcost where proid = ? and costtype = ?";
 		Object cost11 = baseBeanService.getObjectBySqlAndParams(sql,new Object[]{myproject.getProid(),"00"});
 		Object cost22 = baseBeanService.getObjectBySqlAndParams(sql,new Object[]{myproject.getProid(),"01"});
 		cost1 = fnum.format(cost11);
 		if(cost22!=null){
 		cost2 = fnum.format(cost22);
 		
 	    cost3 = fnum.format(Float.parseFloat(cost11.toString())-Float.parseFloat(cost22.toString()));
 		 }
 		}
// 		System.out.println(cost1);
// 		System.out.println(cost2);
// 		System.out.println(cost3);
    	 
    	 return "szcost";
     }
     
     
     
     
     /**
      * 
      * 收支费用查询
      * @return
      */
     public String toSearchByszcost(){
    		session.put("tableSearch", myszcost);
    		return getSzcostList();
     }
     
     
     /**
      * 
      * 
      * 收支费用保存更新
      * @return
      */
     public String saveSzCost(){
    	 
    	 if(myszcost.getSzid()==null||myszcost.getSzid().equals("")){
    		 myszcost.setSzid(serverService.getServerID("szid"));
    	 }
    	 myszcost.setCreatetime(new Date());
    	 
    	 baseBeanService.update(myszcost);
    	 
    	 return "success";
     }
	
   
	
	

	/****************************************************************** 孟竹结束 ******************************************/

	
	/****************************************************************** 许亚洲开始 ******************************************/

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/****************************************************************** 许亚洲结束 ******************************************/

	/****************************************************************** 张灿开始 ******************************************/
	/**---------------------------------生产设计开始-------------------------------**/
	/**
	 * @author zc
	 * @version 1.1
	 * @see 生产设计-生产设计列表
	 * @param mytask实体变量
	 * @return list
	 * @exception 暂无
	 */
	public String getListByTaskManageProduction() {
		String hql = " from DtMytask where 1=1 ";
		List<Object> list = new ArrayList<Object>();
		if(account==null||account.getStaffID()==null){
			return null;
		}
		hql+=" and proid = ? ";
		list.add(myproject.getProid());
		hql+=" and staffid = ? ";
		list.add(account.getStaffID());
		hql+="  and phasestatus = ? ";
		list.add("01");
		if (search != null && "search".equals(search)) {
			DtMytask mytask = (DtMytask) session.get("tableSearch");
			if (mytask != null) {
				if (mytask.getTaskname() != null
						&& !"".equals(mytask.getTaskname().trim())) {
					hql += " and taskName like ?";
					list.add("%" + mytask.getTaskname().trim() + "%");
				}
			}
		}
		hql+=" order by createtime desc";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, list.toArray());
		
		return "TaskManageProductionList";
	}

	/**
	 * @author zc
	 * @version 1.1
	 * @see 生产设计查询
	 * @param mytask实体变量
	 * @return list
	 * @exception 暂无
	 */
	public String toSearchByTaskManageProduction() {
		session.put("tableSearch", mytask);
		return getListByTaskManageProduction();
	}
	/**
	 * @author zc
	 * @version 1.1
	 * @see 生产设计-批量传阅任务
	 * @param mytask 实体变量  dtMypassrecord 实体变量
	 * @return 状态
	 * @exception 暂无
	 */
	public String toCirculatedByTask(){
		List<BaseBean>  beans=new ArrayList<BaseBean>();
		for (DtMytask dmt : dtMytaskMap.values()) {
			DtMytask   dmt1=(DtMytask) baseBeanService.getBeanByHqlAndParams(" from DtMytask where taskid = ?", new Object[]{dmt.getTaskid()});
			for (DtMypassrecord dmr : dtMypassrecordMap.values()) {
				DtMypassrecord dmrNew=new DtMypassrecord();
				dmrNew.setPassid( serverService.getServerID("dtMypassrecord"));
				dmrNew.setTaskid(dmt1.getTaskid());
				dmrNew.setTaskname(dmt1.getTaskname());
				dmrNew.setProid(dmt1.getProid());
				dmrNew.setProjectname(dmt1.getProName());
				dmrNew.setPasserid(account.getStaffID());
				dmrNew.setPassername(account.getStaffName());
				dmrNew.setPasstime(new Date());
				dmrNew.setReadstatus("00");
				
				dmrNew.setReceiverid(dmr.getReceiverid());
				dmrNew.setReceiverName(dmr.getReceiverName());
				
				beans.add(dmrNew);
				//提醒
				Remind remind = new Remind();
				remind.setRemindID(serverService.getServerID("remind"));
				remind.setCircularType("01");
				remind.setCircularTitle(dmrNew.getTaskname());
				remind.setCircularText("您传阅有一个待阅文件，请及时处理！");
				remind.setStaffID(dmrNew.getReceiverid());
				remind.setStaffName(dmrNew.getReceiverName());
				remind.setAddDate(new Date());
				remind.setRemindStatus("01");
				remind.setRemindType("01");
				remind.setDetailedurl("ea/checkmanage/ea_getMypassrecordList.jspa?");
				remindService.addremind(remind);
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	
	/**
	 * @author zc
	 * @version 1.1
	 * @see 生产设计-批量报送审批
	 * @param mytask实体变量
	 * @return 状态
	 * @exception 暂无
	 */
	public String toExamineByTask(){
		List<BaseBean>  beans=new ArrayList<BaseBean>();
		for (DtMytask dmt : dtMytaskMap.values()) {
			DtMytask   dmt1=(DtMytask) baseBeanService.getBeanByHqlAndParams(" from DtMytask where taskid = ?", new Object[]{dmt.getTaskid()});
			for (DtMyaudit dmd : dtMyauditMap.values()) {
				DtMyaudit  dmdNew=new DtMyaudit();
				dmdNew.setAuditid(serverService.getServerID("dtMyaudit"));
				dmdNew.setTaskid(dmt1.getTaskid());
				dmdNew.setTaskname(dmt1.getTaskname());
				dmdNew.setProid(dmt1.getProid());
				dmdNew.setProname(dmt1.getProName()); 
				dmdNew.setApplytime(new Date());
				dmdNew.setAuditorid(dmd.getAuditorid());
				dmdNew.setAuditorname(dmd.getAuditorname());
				dmdNew.setAuditororgID(dmd.getAuditororgID());
				dmdNew.setAuditororgName(dmd.getAuditororgName());
				dmdNew.setAuditorcompanyid(dmd.getAuditorcompanyid());
				dmdNew.setAuditorcompanyname(dmd.getAuditorcompanyname());
				dmdNew.setTaskstatus(dmd.getTaskstatus());
				
				String hql = "from DtMystaff where proid = ? and staffid = ? ";
				List<BaseBean>  beans1=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{dmt1.getProid(),account.getStaffID()});
				if(beans1!=null&&beans1.size()>0){
					DtMystaff dms=(DtMystaff)beans1.get(0);
					dmdNew.setApplyerid(dms.getStaffid());
					dmdNew.setSqrid(dms.getStaffid());
					dmdNew.setApplyername(dms.getStaffname());
					dmdNew.setApplyorg(dms.getOrganizationID());
					dmdNew.setApplyorgname(dms.getOrganizationName());
					dmdNew.setApplycompanyid(dms.getCompanyID());
					dmdNew.setApplycompanyName(dms.getCompanyName());
				}
				dmdNew.setApplyerupdate("01");
				dmdNew.setAuditorstatus("01");
				beans.add(dmdNew);
				//提醒
				Remind remind = new Remind();
				remind.setRemindID(serverService.getServerID("remind"));
				remind.setCircularType("02");
				remind.setCircularTitle(dmdNew.getTaskname());
				remind.setCircularText("您项目审核管理阶段有一个待办任务，请及时处理！");
				remind.setStaffID(dmdNew.getAuditorid());
				remind.setStaffName(dmdNew.getAuditorname());
				remind.setOrganizationID(dmdNew.getAuditororgID());
				remind.setCompanyID(dmdNew.getAuditorcompanyid());
				remind.setAddDate(new Date());
				remind.setRemindStatus("01");
				remind.setRemindType("01");
				remind.setDetailedurl("ea/checkmanage/ea_getDtMyauditList.jspa?");
				remindService.addremind(remind);
			}
			dmt1.setAuditstatus("01");
			beans.add(dmt1);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		
		return "success";
	}
	/**
	 * @author zc
	 * @version 1.1
	 * @see 任务导出
	 * @param mytask实体变量
	 * @return list
	 * @exception 暂无
	 */
	public String toExportExcelByTask() {
		String hql = " from DtMytask where 1=1 ";
		List<Object> list = new ArrayList<Object>();
		if(account==null||account.getStaffID()==null){
			return null;
		}
		hql+=" and proid = ? ";
		list.add(myproject.getProid());
		if("production".equals(ServletActionContext.getRequest().getParameter("production"))){
			hql+=" and staffid = ? ";
			list.add(account.getStaffID());
		}
		if(mytask!=null&&mytask.getPhasestatus()!=null&&!"".equals(mytask.getPhasestatus())){
			hql+="  and phasestatus = ? ";
			list.add(mytask.getPhasestatus());
		}
		if (search != null && "search".equals(search)) {
			DtMytask mytask = (DtMytask) session.get("tableSearch");
			if (mytask != null) {
				if (mytask.getTaskname() != null
						&& !"".equals(mytask.getTaskname().trim())) {
					hql += " and taskName like ?";
					list.add("%" + mytask.getTaskname().trim() + "%");
				}
			}
		}
		hql+=" order by createtime desc";
		List<BaseBean> baseBeanlist = baseBeanService.getListBeanByHqlAndParams(hql, list.toArray());
		excelStream = excelService.showExcel(DtMytask.columnHeadings(),
				baseBeanlist);
		return "showexcel";
	}
	/**
	 * @author zc
	 * @version 1.1
	 * @see 变更阶段状态
	 * @param mytask实体变量
	 * @return 
	 * @exception 暂无
	 */
	public String toChangePhaseByTask(){
		List<BaseBean>  beans=new ArrayList<BaseBean>();
		Map<String, DtMyproprogress>  dtmpMap=new HashMap<String, DtMyproprogress>();
		for (DtMytask dmt : dtMytaskMap.values()) {
			String hql = "from DtMytask where taskid = ?";
			DtMytask ms = (DtMytask) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{dmt.getTaskid()});
			DtMyproprogress myproprogress=(DtMyproprogress)baseBeanService.getBeanByHqlAndParams("from DtMyproprogress where proid=? and staffid=?", new Object[]{ms.getProid(),ms.getStaffid()});
			DtMyproprogress myproprogressOther=null;
			if(!dtmpMap.containsKey(myproprogress.getProgkey())){
				dtmpMap.put(myproprogress.getProgkey(), myproprogress);
				myproprogressOther=dtmpMap.get(myproprogress.getProgkey());
			}else{
				myproprogressOther=dtmpMap.get(myproprogress.getProgkey());
			}
			if("02".equals(mytask.getPhasestatus())){
				ms.setFactfinishdate(new Date());
				ms.setPhasestatus(mytask.getPhasestatus());
				
				myproprogressOther.setScsjnum(myproprogressOther.getScsjnum()+1);
				
			}else if("03".equals(mytask.getPhasestatus())){
				ms.setPhasestatus(mytask.getPhasestatus());
				ms.setToCustomer("00");
				
				myproprogressOther.setSjwcnum(myproprogressOther.getSjwcnum()+1);
				
			}else if("04".equals(mytask.getPhasestatus())){
				ms.setPhasestatus(mytask.getPhasestatus());
				
				myproprogressOther.setTjcgnum(myproprogressOther.getTjcgnum()+1);
				myproprogressOther.setXmdanum(myproprogressOther.getXmdanum()+1);
				
			}else if("05".equals(mytask.getPhasestatus())){
				ms.setToCustomer("01");
			}
			beans.add(ms);
		}
		if(!"05".equals(mytask.getPhasestatus())){
			beans.addAll(dtmpMap.values());
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	
	public static void main(String[] args) {
		Set<DtMyproprogress>  dmpp=new HashSet<DtMyproprogress>();
		List<DtMyproprogress>  dmpp1=new ArrayList<DtMyproprogress>();
		DtMyproprogress  ss=new DtMyproprogress();
		DtMyproprogress  s1s=ss;
		dmpp.add(ss);
		dmpp.add(s1s);
		dmpp1.add(ss);
		dmpp1.add(s1s);
		System.out.println(dmpp);
		System.out.println(dmpp1);
		
	}
	/**---------------------------------生产设计结束-------------------------------**/
	
	/**---------------------------------设计完成开始-------------------------------**/
	/**
	 * @author zc
	 * @version 1.1
	 * @see 查询设计完成阶段的任务列表 分页显示
	 * @param 前台动态传递参数
	 * @return 列表数据list
	 * @exception 暂无
	 */
	public String getListByTaskManageFinished(){
		String hql = " from DtMytask where 1=1 ";
		List<Object> list = new ArrayList<Object>();
		if(account==null||account.getStaffID()==null){
			return null;
		}
		hql+=" and proid = ? ";
		list.add(myproject.getProid());
		hql+="  and phasestatus = ? ";
		list.add("02");
		if (search != null && "search".equals(search)) {
			DtMytask mytask = (DtMytask) session.get("tableSearch");
			if (mytask != null) {
				if (mytask.getTaskname() != null
						&& !"".equals(mytask.getTaskname().trim())) {
					hql += " and taskName like ?";
					list.add("%" + mytask.getTaskname().trim() + "%");
				}
			}
		}
		hql+=" order by createtime desc";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, list.toArray());
		
		return "TaskManageFinishedList";
	}
	
	/**
	 * @author zc
	 * @version 1.1
	 * @see 生产设计查询
	 * @param mytask实体变量
	 * @return list
	 * @exception 暂无
	 */
	public String toSearchByTaskManageFinished() {
		session.put("tableSearch", mytask);
		return getListByTaskManageFinished();
	}
	/**---------------------------------设计完成结束-------------------------------**/
	
	/**---------------------------------提交成果开始-------------------------------**/
	/**
	 * @author zc
	 * @version 1.1
	 * @see 查询提交成果阶段的任务列表 分页显示
	 * @param 前台动态传递参数
	 * @return 列表数据list
	 * @exception 暂无
	 */
	public String getListByTaskManageResults() {
		String hql = " from DtMytask where 1=1 ";
		List<Object> list = new ArrayList<Object>();
		if(account==null||account.getStaffID()==null){
			return null;
		}
		hql+=" and proid = ? ";
		list.add(myproject.getProid());
		hql+="  and phasestatus = ? ";
		list.add("03");
		if (search != null && "search".equals(search)) {
			DtMytask mytask = (DtMytask) session.get("tableSearch");
			if (mytask != null) {
				if (mytask.getTaskname() != null
						&& !"".equals(mytask.getTaskname().trim())) {
					hql += " and taskName like ?";
					list.add("%" + mytask.getTaskname().trim() + "%");
				}
			}
		}
		hql+=" order by createtime desc";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, list.toArray());
		return "TaskManageResultsList";
	}
	
	/**
	 * @author zc
	 * @version 1.1
	 * @see 生产设计查询
	 * @param mytask实体变量
	 * @return list
	 * @exception 暂无
	 */
	public String toSearchByTaskManageResults() {
		session.put("tableSearch", mytask);
		return getListByTaskManageResults();
	}
	/**---------------------------------提交成果结束-------------------------------**/
	
	/**
	 * @author zc
	 * @version 1.1
	 * @see 权限判断
	 * @param myproject.proid 前台传递实体变量
	 * @return list
	 * @exception 暂无
	 */
	public String getPermission() {
		if(account==null||account.getStaffID()==null){
			return null;
		}
		String hql = "from DtMystaff where proid = ? and staffid = ? and identity = ? ";
		List<BaseBean>  beans=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{myproject.getProid(),account.getStaffID(),"00"});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", beans==null||beans.size()==0?0:1);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";

	}
	
	
	
	
	
	
	
	/****************************************************************** 张灿结束 ******************************************/
	
	/****************************************************************** 娄飞开始 ******************************************/
	/**---------------------------------项目归档-------------------------------**/
	/**
	 * @author lf
	 * @version 1.1
	 * @see 项目归档管理的条件查询
	 * @param 前台动态传递参数
	 * @return 列表数据list
	 * @exception 暂无
	 */
	public String toSearchByTaskManageFile() {
		session.put("tableSearch", mytask);
		return getListByTaskManageFile();
	}
	
	/**
	 * @author lf
	 * @version 1.1
	 * @see 查询项目归档管理的任务列表 分页显示
	 * @param 前台动态传递参数
	 * @return 列表数据list
	 * @exception 暂无
	 */
    public String getListByTaskManageFile() {
		String hql = " from DtMytask where 1=1 ";
		List<Object> list = new ArrayList<Object>();
		if(account==null||account.getStaffID()==null){
			return null;
		}
		hql+=" and proid = ? ";
		list.add(myproject.getProid());
		hql+="  and phasestatus = ? ";
		list.add("04");
		if (search != null && "search".equals(search)) {
			DtMytask mytask = (DtMytask) session.get("tableSearch");
			if (mytask != null) {
				if (mytask.getTaskname() != null
						&& !"".equals(mytask.getTaskname().trim())) {
					hql += " and taskName like ?";
					list.add("%" + mytask.getTaskname().trim() + "%");
				}
			}
		}
		hql+=" order by createtime desc";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, list.toArray());
		return "TaskManageFileList";
	}
    
    /**
     * @author lf
	 * @version 1.1
	 * @see 申请修改
	 * @param mytask实体变量
	 * @return 状态
	 * @exception 暂无
     */
	public String toExamineByTaskupdate(){
		String hql = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getStaffID()});
		List<BaseBean>  beans=new ArrayList<BaseBean>();
		for (DtMytask dmt : dtMytaskMap.values()) {
			DtMytask dmt1=(DtMytask) baseBeanService.getBeanByHqlAndParams(" from DtMytask where taskid = ?", new Object[]{dmt.getTaskid()});
			for (DtMyaudit dmd1 : dtMyauditMap.values()) {
				DtMyaudit dmd=new DtMyaudit();
				dmd.setAuditid(serverService.getServerID("dtMyaudit"));
				dmd.setApplyerid(account.getStaffID());
				dmd.setSqrid(account.getStaffID());
				dmd.setApplyername(staff.getStaffName());
				dmd.setApplyorg(dmt1.getOrgid());
				dmd.setApplyorgname(dmt1.getOrgname());
				dmd.setApplycompanyid(account.getCompanyID());
				dmd.setApplycompanyName(account.getCompanyName());
				dmd.setApplytime(new Date());
				dmd.setAuditorid(dmd1.getAuditorid());
				dmd.setAuditorname(dmd1.getAuditorname());
				dmd.setAuditororgID(dmd1.getAuditororgID());
				dmd.setAuditororgName(dmd1.getAuditororgName());
				dmd.setAuditorcompanyid(dmd1.getAuditorcompanyid());
				dmd.setAuditorcompanyname(dmd1.getAuditorcompanyname());
				dmd.setTaskid(dmt1.getTaskid());
				dmd.setProid(dmt1.getProid());
				dmd.setProname(dmt1.getProName());
				dmd.setTaskname(dmt1.getTaskname());
				dmd.setTaskstatus("02");
				dmd.setApplyerupdate("02");
				dmd.setAuditorstatus("01");
				beans.add(dmd);
				//提醒
				Remind remind = new Remind();
				remind.setRemindID(serverService.getServerID("remind"));
				remind.setCircularType("02");
				remind.setCircularTitle(dmd.getTaskname());
				remind.setCircularText("您项目审核管理阶段有一个待办任务，请及时处理！");
				remind.setStaffID(dmd.getAuditorid());
				remind.setStaffName(dmd.getAuditorname());
				remind.setOrganizationID(dmd.getAuditororgID());
				remind.setCompanyID(dmd.getAuditorcompanyid());
				remind.setAddDate(new Date());
				remind.setRemindStatus("01");
				remind.setRemindType("01");
				remindService.addremind(remind);
			}
			dmt1.setApplyerupdate("02");
			dmt1.setUpdatestatus("01");
			beans.add(dmt1);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		
		return "success";
	}
	
	/**
     * @author lf
	 * @version 1.1
	 * @see Ajxa 添加保存档案
	 * @param 前台传递参数
	 * @return 状态
	 * @exception 暂无
     */
	public String saveArchives(){
		mytask.setTaskid(serverService.getServerID("taskid"));			
		mytask.setPhasestatus("04");
		DtMyproject dtproject = (DtMyproject)baseBeanService.getBeanByHqlAndParams(
				 "from DtMyproject where proid=?", new Object[] {mytask.getProid()});
		mytask.setProName(dtproject.getProname());
		mytask.setCreatetime(new Date());
		mytask.setDistributeid(account.getStaffID());
		String hql = "from Staff where staffID = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getStaffID()});
		mytask.setDistributeName(staff.getStaffName());
		//处理执行人的信息
		if(mytask.getStaffid()!=null&&!mytask.getStaffid().equals("")){
			String[] staffinfo = mytask.getStaffid().split("-");
			mytask.setStaffid(staffinfo[0]);
			mytask.setOrgid(staffinfo[1]);
			mytask.setCompanyid(staffinfo[2]);
			mytask.setStaffname(staffinfo[3]);
			mytask.setOrgname(staffinfo[4]);
			mytask.setCompanyname(staffinfo[5]);
		}
		
		String filepath = myattach.getFilepath();
		String filename = myattach.getFilename();
		String fileformat =myattach.getFileformat();
		String[] filepaths = filepath.split(",");
		String[] filenames = filename.split(",");
		String[] fileformats = fileformat.split(",");
		DtMyattach attach = null;
		for (int i = 0; i < filepaths.length; i++) {
			attach = new DtMyattach();
			attach.setAttachid(serverService.getServerID("attachid"));
			attach.setFileformat(fileformats[i]);
			attach.setFilename(filenames[i]);
			attach.setFileformat(fileformats[i]);
			attach.setFilepath(filepaths[i]);
			attach.setUploadtime(new Date());
			attach.setProid(myattach.getProid());
			attach.setTaskid(mytask.getTaskid());
			attach.setStaffid(account.getStaffID());
			attach.setStaffname(staff.getStaffName());
			attach.setStatus("00");
			baseBeanService.save(attach);
			//记录上传操作
			recordOperateAttach(attach.getAttachid(),"03");
		}
		baseBeanService.update(mytask);
		
		DtMyproprogress myproprogress=(DtMyproprogress)baseBeanService.getBeanByHqlAndParams("from DtMyproprogress where proid=? and staffid=?", new Object[]{mytask.getProid(),mytask.getStaffid()});
		if(myproprogress!=null){
			myproprogress.setTasknumbyone(myproprogress.getTasknumbyone()+1);
			myproprogress.setXmdanum(myproprogress.getXmdanum()+1);
			baseBeanService.update(myproprogress);
		}
		
		return "success";
	}
	
	/**
     * @author lf
	 * @version 1.1
	 * @see Ajxa 根据项目id查询项目数据
	 * @param 前台传递参数项目id
	 * @return 状态
	 * @exception 暂无
     */
	public String getMyprojectbyproid(){
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from DtMyproject where proid=?";
		DtMyproject dtproject = (DtMyproject)baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] {myproject.getProid()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dtproject", dtproject);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 
	 * 
	 * 点击添加任务 跳转到新建页面
	 * 
	 * @return
	 */
	public String addNewArchivesPage() {
		String hql = "from DtMyproject where proid = ?";
        myproject = (DtMyproject) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{myproject.getProid()});
		return "newArchives";
	}
	
	/****************************************************************** 娄飞结束 ******************************************/
	
	
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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

	public DtMytask getMytask() {
		return mytask;
	}

	public void setMytask(DtMytask mytask) {
		this.mytask = mytask;
	}

	public DtMyproject getMyproject() {
		return myproject;
	}

	public void setMyproject(DtMyproject myproject) {
		this.myproject = myproject;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<String, DtMypassrecord> getDtMypassrecordMap() {
		return dtMypassrecordMap;
	}

	public void setDtMypassrecordMap(Map<String, DtMypassrecord> dtMypassrecordMap) {
		this.dtMypassrecordMap = dtMypassrecordMap;
	}

	public Map<String, DtMytask> getDtMytaskMap() {
		return dtMytaskMap;
	}

	public void setDtMytaskMap(Map<String, DtMytask> dtMytaskMap) {
		this.dtMytaskMap = dtMytaskMap;
	}

	public Map<String, DtMyaudit> getDtMyauditMap() {
		return dtMyauditMap;
	}

	public void setDtMyauditMap(Map<String, DtMyaudit> dtMyauditMap) {
		this.dtMyauditMap = dtMyauditMap;
	}

	public DtMyattach getMyattach() {
		return myattach;
	}

	public void setMyattach(DtMyattach myattach) {
		this.myattach = myattach;
	}

	public List<BaseBean> getAuditlist() {
		return auditlist;
	}

	public void setAuditlist(List<BaseBean> auditlist) {
		this.auditlist = auditlist;
	}

	public List<BaseBean> getAttachlist() {
		return attachlist;
	}

	public void setAttachlist(List<BaseBean> attachlist) {
		this.attachlist = attachlist;
	}

	public List<BaseBean> getAttrecordlist() {
		return attrecordlist;
	}

	public void setAttrecordlist(List<BaseBean> attrecordlist) {
		this.attrecordlist = attrecordlist;
	}

	public DtSzcost getMyszcost() {
		return myszcost;
	}

	public void setMyszcost(DtSzcost myszcost) {
		this.myszcost = myszcost;
	}

	public String getCost1() {
		return cost1;
	}

	public void setCost1(String cost1) {
		this.cost1 = cost1;
	}

	public String getCost2() {
		return cost2;
	}

	public void setCost2(String cost2) {
		this.cost2 = cost2;
	}

	public String getCost3() {
		return cost3;
	}

	public void setCost3(String cost3) {
		this.cost3 = cost3;
	}

	public List<BaseBean> getPassrecordlist() {
		return passrecordlist;
	}

	public void setPassrecordlist(List<BaseBean> passrecordlist) {
		this.passrecordlist = passrecordlist;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public List<BaseBean> getAttachlist2() {
		return attachlist2;
	}

	public void setAttachlist2(List<BaseBean> attachlist2) {
		this.attachlist2 = attachlist2;
	}

	public String getAuditid() {
		return auditid;
	}

	public void setAuditid(String auditid) {
		this.auditid = auditid;
	}

	public String getAuditurl() {
		return auditurl;
	}

	public void setAuditurl(String auditurl) {
		this.auditurl = auditurl;
	}
	
}
