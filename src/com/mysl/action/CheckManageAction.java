package com.mysl.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Remind;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.EnterpriseSpirit;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
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

import com.mysl.bo.DtMyaudit;
import com.mysl.bo.DtMypassrecord;
import com.mysl.bo.DtMyproprogress;
import com.mysl.bo.DtMystaff;
import com.mysl.bo.DtMytask;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;

/*
 * 项目审批管理
 * */
@Controller
@Scope("prototype")
public class CheckManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private RemindService remindService;
	@Resource
	private ShowExcelService excelService;
	private String parameter;
	private EnterpriseSpirit enterpriseSpirit;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	private DtMyaudit myaudit;
	private DtMypassrecord mypassrecord;

	private String auditid; // 审核表id
	private String sdate;
	private String edate;
	private boolean blo; //判断是查询列表还是导出查询
	private String staffid; //执行人id
	private String proid; //项目id
	private String phasestatus; //阶段
	/**
	 * 审核
	 */
	private Map<String, DtMyaudit> dtMyauditMap;
	private Map<String, DtMyaudit> dtMyaudit2Map;

	/**
	 * 审核状态 audit方法里状态区分｛'01'通过审核并继续审核 '02'通过审核并结束 '03'驳回｝
	 */
	private String auditorstatus;
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
	 * 获得部门id
	 */
	String organizationID = (String) session.get("organizationID");

	
/*********************************************************审核***************************************************/
	/**
	 * 审核有查询条件调用方法
	 * 
	 * @return
	 */
	public String toSearchByDtMyaudit() {
		session.put("DtMyaudit", myaudit);
		return getDtMyauditList();
	}
	
	/**
	 * 查询审核列表
	 * 
	 * @return
	 */
	public String getDtMyauditList() {
		blo=true;
		List<Object> result = getlista();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql,"select count(1) from ("
						+ hql +" )", parms);
		return "DtMyauditList";
	}
	
	/**
	 * 查询审核列表的方法调用
	 * @return
	 */
	public List<Object> getlista(){
		List<Object> result = new ArrayList<Object>();
		String hql =null;
		if(blo){
			hql= "select ma.auditid,ma.taskid,t.COMPANYNAME,ma.proname,t.taskCode," +
					" t.taskName,ma.applyername,ma.applytime,t.staffname,t.orgname," +
					" case when t.taskType=? then '合同制定'" +
					" when t.taskType=? then '生产计划通知书'" +
					" when t.taskType=? then '设计大纲'" +
					" when t.taskType=? then '绘图任务'" +
					" else '' end," +
					" case when t.emergency=? then '普通'" +
					" when t.emergency=? then '紧急'" +
					" when t.emergency=? then '特急'" +
					" else '' end," +
					" t.startDate,t.planfinishDate,t.factfinishDate,t.distributeName," +
					" ma.taskstatus,ma.auditorstatus" +
					" from DT_MYAUDIT ma" +
					" left join DT_MYTASK t on t.taskid=ma.taskid" +
					" where ma.auditorid=?";
		}else{
			hql = "select t.COMPANYNAME,ma.proname,t.taskCode," +
					" t.taskName,ma.applyername,ma.applytime,t.staffname,t.orgname," +
					" case when t.taskType=? then '合同制定'" +
					" when t.taskType=? then '生产计划通知书'" +
					" when t.taskType=? then '设计大纲'" +
					" when t.taskType=? then '绘图任务'" +
					" else '' end," +
					" case when t.emergency=? then '普通'" +
					" when t.emergency=? then '紧急'" +
					" when t.emergency=? then '特急'" +
					" else '' end," +
					" t.startDate,t.planfinishDate,t.factfinishDate,t.distributeName," +
					" case when ma.taskstatus='00' then '生产设计阶段'" +
					" when ma.taskstatus='01' then '设计完成阶段阶段'" +
					" else '项目归档管理申请修改' end," +
					" case when ma.auditorstatus='01' then '未审核'" +
					" when ma.auditorstatus='02' then '已审核'" +
					" else '驳回' end"+
					" from DT_MYAUDIT ma" +
					" left join DT_MYTASK t on t.taskid=ma.taskid" +
					" where ma.auditorid=?";
		}
		List<Object> params = new ArrayList<Object>();
		params.add("htzd");
		params.add("scsj");
		params.add("sjdg");
		params.add("htrw");
		params.add("pt");
		params.add("jj");
		params.add("tj");
		params.add(account.getStaffID());
		if (auditorstatus != null && !"".equals(auditorstatus)) {
			hql += " and ma.auditorstatus=?";
			params.add(auditorstatus);
		}
		if (search != null && "search".equals(search)) {
			DtMyaudit myaudit = (DtMyaudit) session.get("DtMyaudit");
			if (myaudit != null) {
				if (myaudit.getProname() != null
						&& !"".equals(myaudit.getProname())) {
					hql += " and ma.proname like ?";
					params.add("%" + myaudit.getProname().trim() + "%");
				}
				if (myaudit.getTaskname() != null
						&& !"".equals(myaudit.getTaskname())) {
					hql += " and ma.taskname like ?";
					params.add("%" + myaudit.getTaskname().trim()+ "%");
				}
				if (myaudit.getApplyername() != null
						&& !"".equals(myaudit.getApplyername())) {
					hql += " and ma.applyerName like ?";
					params.add("%" + myaudit.getApplyername().trim() + "%");
				}
				if (sdate != null && !"".equals(sdate) && edate != null
						&& !"".equals(edate)) {
					hql += " and ma.applytime between ? and ?";
					params.add(Utilities.getDateFromString(sdate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					params.add(Utilities.getDateFromString(edate+" 59:59:59", "yyyy-MM-dd HH:mm:ss"));
				}
			}
		}
		hql += " order by ma.applytime desc";
		result.add(hql);
		result.add(params.toArray());
		return result;
	} 
	
	/**
	 * 审核调用方法
	 * 
	 * @return
	 */
	public String audit() {
		List<BaseBean> beans = new ArrayList<BaseBean>();
		for (DtMyaudit dma : dtMyaudit2Map.values()) {
			DtMyaudit dma1=(DtMyaudit) baseBeanService.getBeanByHqlAndParams("from DtMyaudit where auditid=?", new Object[]{dma.getAuditid()});
			for (DtMyaudit dmd1 : dtMyauditMap.values()) {
				String auditorstatus=dmd1.getAuditorstatus();
				parameter = "任务审核（任务名称：" + dma1.getTaskname() + "）";
				if (auditorstatus != null && "01".equals(auditorstatus)) {
					String hql = "from Staff where staffID = ?";
					Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getStaffID()});
					DtMyaudit dmd=new DtMyaudit();
					dmd.setAuditid(serverService.getServerID("dtMyaudit"));
					dmd.setApplycompanyid(dma1.getApplycompanyid());
					dmd.setApplycompanyName(dma1.getApplycompanyName());
					dmd.setApplyerid(account.getStaffID());
					dmd.setApplyername(staff.getStaffName());
					dmd.setApplyorg(dma1.getApplyorg());
					dmd.setApplyorgname(dma1.getApplyorgname());
					dmd.setApplytime(new Date());
					dmd.setProid(dma1.getProid());
					dmd.setProname(dma1.getProname());
					dmd.setTaskid(dma1.getTaskid());
					dmd.setTaskname(dma1.getTaskname());
					dmd.setAuditorid(dmd1.getAuditorid());
					dmd.setAuditorname(dmd1.getAuditorname());
					dmd.setAuditororgID(dmd1.getAuditororgID());
					dmd.setAuditororgName(dmd1.getAuditororgName());
					dmd.setAuditorcompanyid(dmd1.getAuditorcompanyid());
					dmd.setAuditorcompanyname(dmd1.getAuditorcompanyname());
					dmd.setTaskstatus(dma1.getTaskstatus());
					dmd.setApplyerupdate(dma1.getApplyerupdate());
					dmd.setSqrid(dma1.getSqrid());
					dmd.setAuditorstatus("01");
					//dma.setApplyerupdate(dma1.getApplyerupdate());
					parameter+="审核结果：审核通过并继续审核";
					beans.add(dmd);

					dma1.setAuditorstatus("02");
					//提醒
					String []str ={dma1.getTaskname(),"您项目审核管理阶段有一个待办任务，请及时处理！",dmd1.getAuditorid(),
							dmd1.getAuditorname(),dmd1.getAuditororgID(),dmd1.getAuditorcompanyid()};
					saveRemind("01",str,null);
				}else{
					DtMytask dtMytask = (DtMytask) baseBeanService
							.getBeanByHqlAndParams("from DtMytask where taskid=?",
									new Object[] { dma1.getTaskid() });
					staffid=dtMytask.getStaffid();
					proid=dtMytask.getProid();
					phasestatus=dtMytask.getPhasestatus();
					if (auditorstatus != null && "02".equals(auditorstatus)){
						parameter+="审核结果：审核通过并结束";
						if(dma1.getApplyerupdate()!=null&&"02".equals(dma1.getApplyerupdate())){
							upMyproprogress();
							dtMytask.setPhasestatus("01");
							dtMytask.setAuditstatus("00");
							dtMytask.setApplyerupdate("01");
						}else{
							dtMytask.setAuditstatus("02");
						}
						if(dtMytask.getPhasestatus()!=null&&"01".equals(dtMytask.getPhasestatus())){
							String []str ={dtMytask.getTaskname(),"您项目管理生产设计阶段有一个待办任务，请及时处理！",dtMytask.getStaffid(),
									dtMytask.getStaffname(),dtMytask.getProid()};
							if(dma1.getApplyerupdate()!=null&&"02".equals(dma1.getApplyerupdate()))
								saveRemind("02",str,dma1.getSqrid());
							else
								saveRemind("02",str,null);
						}else if(dtMytask.getPhasestatus()!=null&&"02".equals(dtMytask.getPhasestatus())){
							dtMytask.setUpdatestatus("00");
							String []str ={dtMytask.getTaskname(),"您项目管理设计完成阶段有一个待办任务，请及时处理！","02",dtMytask.getProid()};
							saveRemind("02",str,null);
						}
					}else{
						parameter+="审核结果：驳回";
						
						if(dma1.getApplyerupdate()!=null&&"02".equals(dma1.getApplyerupdate())){
							dtMytask.setUpdatestatus("03");
							String []str ={dtMytask.getTaskname(),"您项目档案管理阶段有一个待办任务，请及时处理！","03",dtMytask.getProid()};
							saveRemind("03",str,dma1.getSqrid());
						}else{
							upMyproprogress();
							dtMytask.setAuditstatus("03");
							dtMytask.setPhasestatus("01");
							String []str ={dtMytask.getTaskname(),"您项目管理生产设计阶段有一个待办任务，请及时处理！",dtMytask.getStaffid(),
									dtMytask.getStaffname(),dtMytask.getProid()};
							saveRemind("03",str,null);
						}
					}
					beans.add(dtMytask);
					
					dma1.setAuditorstatus(auditorstatus);
				}
				
				dma1.setAudittime(new Date());
				dma1.setComments(dmd1.getComments());
				beans.add(dma1);
				
				CLogBook logBook = logBookService.saveCLogBook(organizationID,
						parameter, account);
				beans.add(logBook);
			}
		}
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}
	
	/**
	 * 保存通知信息
	 * @param struta
	 * @param str
	 */
	public void saveRemind(String struta,String [] str,String sqrid){
		if(str.length==4){
			List<BaseBean> stList=null;
			if(sqrid!=null&&!"".equals(sqrid))
				stList=baseBeanService.getListBeanByHqlAndParams("from DtMystaff where proid=? and identity=? and staffid=?", new Object[]{str[3],"00",sqrid});
			else
				stList=baseBeanService.getListBeanByHqlAndParams("from DtMystaff where proid=? and identity=?", new Object[]{str[3],"00"});
			for (int i = 0; i < stList.size(); i++) {
				DtMystaff mystaff =(DtMystaff)stList.get(i);
				Remind remind = new Remind();
				remind.setRemindID(serverService.getServerID("remind"));
				remind.setCircularTitle(str[0]);
				remind.setCircularText(str[1]);
				remind.setStaffID(mystaff.getStaffid());
				remind.setStaffName(mystaff.getStaffname());
				remind.setOrganizationID(mystaff.getOrganizationID());
				remind.setCompanyID(mystaff.getCompanyID());
				remind.setCircularType("02");
				remind.setAddDate(new Date());
				if(str[2].equals("02")){
					remind.setDetailedurl("ea/taskmanage/ea_getListByTaskManageFinished.jspa?myproject.proid="+str[3]);
				}else{
					remind.setDetailedurl("ea/taskmanage/ea_getListByTaskManageFile.jspa?myproject.proid="+str[3]);
				}
				remind.setRemindStatus("01");
				remind.setRemindType("01");
				remindService.addremind(remind);
			}
		}else{
			Remind remind = new Remind();
			remind.setRemindID(serverService.getServerID("remind"));
			remind.setCircularTitle(str[0]);
			remind.setCircularText(str[1]);
			if(struta!=null&&struta.equals("01")){
				remind.setStaffID(str[2]);
				remind.setStaffName(str[3]);
				remind.setOrganizationID(str[4]);
				remind.setCompanyID(str[5]);
				remind.setDetailedurl("ea/taskmanage/ea_getDtMyauditList.jspa?auditorstatus=02");
			}else if(struta!=null&&(struta.equals("02")||struta.equals("03"))){
				DtMystaff dtMystaff=(DtMystaff)baseBeanService.getBeanByHqlAndParams("from DtMystaff where proid=? and staffid=? and identity=?", new Object[]{str[4],str[2],"01"});
				remind.setStaffID(str[2]);
				remind.setStaffName(str[3]);
				remind.setOrganizationID(dtMystaff.getOrganizationID());
				remind.setCompanyID(dtMystaff.getCompanyID());
				remind.setDetailedurl("ea/taskmanage/ea_getListByTaskManageProduction.jspa?myproject.proid="+str[4]);
			}
			remind.setCircularType("02");
			remind.setAddDate(new Date());
			remind.setRemindStatus("01");
			remind.setRemindType("01");
			if(sqrid!=null&&!"".equals(sqrid)){
				DtMystaff dtMystaff=(DtMystaff)baseBeanService.getBeanByHqlAndParams("from DtMystaff where proid=? and identity=? and staffid=?", new Object[]{str[4],"00",sqrid});
				Remind remind2 =null;
				try {
					remind2=(Remind) remind.cloneRemind();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				remind2.setRemindID(serverService.getServerID("remind"));
				remind2.setRemindKey(null);
				remind2.setCircularText("您项目管理生产设计阶段任务已申请修改通过！");
				remind2.setStaffID(dtMystaff.getStaffid());
				remind2.setStaffName(dtMystaff.getStaffname());
				remind2.setOrganizationID(dtMystaff.getOrganizationID());
				remind2.setCompanyID(dtMystaff.getCompanyID());
				remind2.setAddDate(new Date());
				remind2.setDetailedurl(null);
				remindService.addremind(remind2);
			}
			remindService.addremind(remind);
		}
	}
	
	/**
	 * 改变项目进度方法
	 */
	public void upMyproprogress(){
		DtMyproprogress myproprogress =(DtMyproprogress)baseBeanService.getBeanByHqlAndParams("from DtMyproprogress where staffid=? and proid=?", new Object[]{staffid,proid});
		if(phasestatus!=null&&"04".equals(phasestatus)){
			myproprogress.setXmdanum(myproprogress.getXmdanum()-1);
			myproprogress.setTjcgnum(myproprogress.getTjcgnum()-1);
			myproprogress.setSjwcnum(myproprogress.getSjwcnum()-1);
			myproprogress.setScsjnum(myproprogress.getScsjnum()-1);
		}else if(phasestatus!=null&&"02".equals(phasestatus)){
			myproprogress.setScsjnum(myproprogress.getScsjnum()-1);
		}
		baseBeanService.update(myproprogress);
	}
	
	/**
	 * 审核列表导出
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcelDtMyaudit() {
		blo=false;
	    List<BaseBean> lists=new ArrayList<BaseBean>();
	    List<Object> result = getlista();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		lists=baseBeanService.getListBeanBySqlAndParams(hql, parms);
	    excelStream = excelService.showExcel(DtMyaudit.columnHeadings(),lists);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出审核列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/*********************************************************传阅***************************************************/
	
	/**
	 * 传阅有查询条件调用方法
	 * 
	 * @return
	 */
	public String toSearchByMypassrecord() {
		session.put("mypassrecord", mypassrecord);
		return getMypassrecordList();
	}
	
	/**
	 * 查询传阅列表
	 * 
	 * @return
	 */
	public String getMypassrecordList() {
		List<Object> result = getListp();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql,"select count(1) from ("
						+ hql+" )", parms);
		return "MypassrecordList";
	}
	
	/**
	 * 查询传阅列表方法调用
	 * @return
	 */
	public List<Object> getListp(){
		List<Object> result = new ArrayList<Object>();
		String hql = "select p.passid,p.taskid,p.projectname,t.taskCode," +
				" t.taskName,p.receiverName,p.passtime,t.staffname,t.orgname," +
				" case when t.taskType=? then '合同制定'" +
				" when t.taskType=? then '生产计划通知书'" +
				" when t.taskType=? then '设计大纲'" +
				" when t.taskType=? then '绘图任务'" +
				" else '' end," +
				" case when t.emergency=? then '普通'" +
				" when t.emergency=? then '紧急'" +
				" when t.emergency=? then '特急'" +
				" else '' end," +
				" t.startDate,t.planfinishDate,t.factfinishDate,t.distributeName" +
				" from DT_MYPASSRECORD p" +
				" left join DT_MYTASK t on t.taskid=p.taskid" +
				" where p.receiverid=?";
		List<Object> params = new ArrayList<Object>();
		params.add("htzd");
		params.add("scsj");
		params.add("sjdg");
		params.add("htrw");
		params.add("pt");
		params.add("jj");
		params.add("tj");
		params.add(account.getStaffID());
		if (search != null && "search".equals(search)) {
			DtMypassrecord dtMypassrecord = (DtMypassrecord) session.get("mypassrecord");
			if (dtMypassrecord != null) {
				if (dtMypassrecord.getProjectname().trim() != null
						&& !"".equals(dtMypassrecord.getProjectname().trim())) {
					hql += " and p.projectname like ?";
					params.add("%" + dtMypassrecord.getProjectname().trim() + "%");
				}
				if (dtMypassrecord.getTaskname().trim() != null
						&& !"".equals(dtMypassrecord.getTaskname().trim())) {
					hql += " and p.taskname like ?";
					params.add("%" + dtMypassrecord.getTaskname() + "%");
				}
				if (sdate != null && !"".equals(sdate) && edate != null
						&& !"".equals(edate)) {
					hql += " and p.passtime between ? and ?";
					params.add(Utilities.getDateFromString(sdate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
					params.add(Utilities.getDateFromString(edate+" 59:59:59", "yyyy-MM-dd HH:mm:ss"));
				}
			}
		}
		hql += " order by passtime desc";
		result.add(hql);
		result.add(params.toArray());
		return result;
	}
	
	/**
	 * 传阅列表导出
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcelMypassrecord() {
	    List<BaseBean> lists=new ArrayList<BaseBean>();
	    List<Object> result = getListp();
		String hql=result.get(0).toString();
		Object[] parms = (Object[])result.get(1);
		hql="select "+hql.substring(hql.indexOf(",",hql.indexOf(",")+1)+1);
		lists=baseBeanService.getListBeanBySqlAndParams(hql, parms);
	    excelStream = excelService.showExcel(DtMypassrecord.columnHeadings(),lists);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出传阅列表", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

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

	public EnterpriseSpirit getEnterpriseSpirit() {
		return enterpriseSpirit;
	}

	public void setEnterpriseSpirit(EnterpriseSpirit enterpriseSpirit) {
		this.enterpriseSpirit = enterpriseSpirit;
	}

	public DtMyaudit getMyaudit() {
		return myaudit;
	}

	public void setMyaudit(DtMyaudit myaudit) {
		this.myaudit = myaudit;
	}

	public String getAuditorstatus() {
		return auditorstatus;
	}

	public void setAuditorstatus(String auditorstatus) {
		this.auditorstatus = auditorstatus;
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

	public String getAuditid() {
		return auditid;
	}

	public void setAuditid(String auditid) {
		this.auditid = auditid;
	}

	public Map<String, DtMyaudit> getDtMyauditMap() {
		return dtMyauditMap;
	}

	public void setDtMyauditMap(Map<String, DtMyaudit> dtMyauditMap) {
		this.dtMyauditMap = dtMyauditMap;
	}

	public Map<String, DtMyaudit> getDtMyaudit2Map() {
		return dtMyaudit2Map;
	}

	public void setDtMyaudit2Map(Map<String, DtMyaudit> dtMyaudit2Map) {
		this.dtMyaudit2Map = dtMyaudit2Map;
	}

	public DtMypassrecord getMypassrecord() {
		return mypassrecord;
	}

	public void setMypassrecord(DtMypassrecord mypassrecord) {
		this.mypassrecord = mypassrecord;
	}
}
