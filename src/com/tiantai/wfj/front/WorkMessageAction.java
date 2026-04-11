package com.tiantai.wfj.front;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.service.WorkMessageService;
import com.tiantai.wfj.util.SessionWrap;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bo.AuditRecord;
import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class WorkMessageAction {
	private static final Logger logger = LoggerFactory.getLogger(WorkMessageAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private WorkMessageService workService;
	private Object result;// AJAX使用
	private PageForm pageForm;
	private String sccid;
	private Staff staff;
	private AuditRecord auditRecord;
	private String auid;
	private String AuditComment;
	private List<Object> list1;
	public ServletRequest request = ServletActionContext.getRequest();
	private String parameter;;
	private List<BaseBean> list;
	private String viewUrl;
	//跳转到任务消息页面
	public String taskMessage(){
		String companyId=request.getParameter("companyId");
		String sccId=request.getParameter("sccId");
		if(companyId!=null&&sccId!=null){
			HttpSession session = ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();
			StringBuilder sbhql=new StringBuilder();
			sbhql.append("from CAccount acc where acc.staffID in ");
			sbhql.append("(select ss.staffID from Staff ss,TEshopCusCom cus where cus.sccId =? ) ");
			sbhql.append("and acc.companyID =?");
			List<BaseBean> objectList=(List<BaseBean>)baseBeanService.getListBeanByHqlAndParams(sbhql.toString(),new Object[]{sccId,companyId});
			CAccount t=(CAccount)objectList.get(0);
			session.setAttribute("t",t);
			sw.setObject(session,SessionWrap.KEY_CACCOUNT,t);
		}
		return "tmessage";
	}
	
	
	
	/**
	 * 查看所有需要审批的任务
	 * @return
	 */
	public String findMessage(){
		JSONObject ret = new JSONObject();
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		CAccount t = (CAccount)sw.getObject(session,
				SessionWrap.KEY_CACCOUNT);
		List<String> params = new ArrayList<String>();
		
		
		String sql="  select d.startname,d.module,to_char(d.submitdate,'YYYY-MM-DD HH24:MI:SS'),to_char(d.commitdate,'YYYY-MM-DD HH24:MI:SS'),d.state,d.viewurl,d.auditcomment from DT_AUDITRECORD d where d.auditid=? and d.auditcomid = ? and d.state !='00' order by d.submitdate desc  ";
		params.add(t.getStaffID());
		params.add(t.getCompanyID());
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 5, sql.toString(), "select count(1) from (" + sql.toString() +")", params.toArray());
	if(pageForm!=null){
		ret.accumulate("pageForm", pageForm); 
		result=ret.toString();
	}else{
		ret.accumulate("pageForm", "1"); 
		result=ret.toString();
	}	
		return "success";
	}
	
	/**
	 * 同意审批任务
	 * @return
	 */
	public boolean agreeMessage(){
		
		String hql=" from AuditRecord  where  auid=? ";
		AuditRecord ad=(AuditRecord) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{auid});
		
		if(ad !=null){
		ad.setState("02");
		ad.setAuditComment(AuditComment);
		ad.setCommitDate(new Date());
		baseBeanService.update(ad);
		return true;
		}else{
			return false;
		}
	}  
	
	
	/**
	 * 
	 * 驳回审批任务
	 * @return
	 */
	public boolean rejectedMessage(){
		
		
		String hql=" from AuditRecord  where auid=? ";
		AuditRecord ad=(AuditRecord) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{auid});
		
		if(ad !=null){
		ad.setState("03");
		ad.setAuditComment(AuditComment);
		ad.setCommitDate(new Date());
		baseBeanService.update(ad);
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 
	 * 转交任务 
	 * @return
	 */
	public String transferMessage(){
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			String hql=" from AuditRecord  where auid=? ";
			AuditRecord ad=(AuditRecord) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{auditRecord.getAuid()});
			List<BaseBean>  baselist = new ArrayList<BaseBean>();
			ad.setState("04");
			ad.setAuditComment(auditRecord.getAuditComment());
			ad.setCommitDate(new Date());
			baselist.add(ad);
			//转发给另一个人审批,新生成一条记录
			auditRecord.setAuid(serverService.getServerID("au"));
			auditRecord.setState("01");
			auditRecord.setSorts(String.valueOf(Integer.parseInt(ad.getSorts())+1));
			auditRecord.setBatchNum(ad.getBatchNum());
			auditRecord.setViewUrl(ad.getViewUrl());
			auditRecord.setThirdId(ad.getThirdId());
			auditRecord.setSubmitDate(new Date());
			auditRecord.setStartID(ad.getStartID());
			auditRecord.setStartName(ad.getStartName());
			auditRecord.setModule(ad.getModule());
			auditRecord.setAuditComment("");
			auditRecord.setSubmitDate(new Date());
			baselist.add(auditRecord);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(baselist,null,null);
			
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		map.put("result", "success");
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		
		return "success";
	}
	
	
	public String saveAudi(){
		JSONObject jobj = new JSONObject();
		AuditRecord audi=new AuditRecord();
		
		audi.setAuid(serverService.getServerID("au"));
		audi.setAuditID("Staff2016120953GB4T3GK40000000001");
		audi.setAuditName("吴先生");
		audi.setPosition("人事经理");
		audi.setStartID("cstaff20151207FI79BW2ERZ0000018806");
		audi.setStartName("啦啦啦啦");
		audi.setSubmitDate(new Date());
		audi.setModule("亮亮牌专业是事实上在");
		audi.setState("02");
		audi.setAuditComment("");
		baseBeanService.save(audi);
		jobj.accumulate("company", "111");
		result = jobj;
		return "success";
	}
	/**
	 * 查询所有的关联公司
	 * @return
	 */
	public String findCompanyPeople(){
		HttpServletRequest request = ServletActionContext.getRequest();
		AuditRecord audi=workService.findByAuid(auid);
		Map<String,List<BaseBean>> map = new HashMap<String,List<BaseBean>>();
		map = workService.findCompany(audi.getAuditComID());
		request.setAttribute("companylist", map.get("companylist"));
		request.setAttribute("orglist",map.get("orglist"));
		
		
		
		return "audiPersol";
	}
	
	/**
	 * 查询公司下面的所有部门
	 * 
	 * @return
	 */
	public String ajaxFindOrgList(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyID= request.getParameter("companyID");
		List<BaseBean> orglist = workService.findOrg(companyID);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orglist", orglist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		
		return "success";
		
	}
	
	/**
	 * 查询公司部门下的所有人
	 * @return
	 */
	public String findStaffList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String companyId= request.getParameter("companyID");
		String orgID = request.getParameter("orgID");
		List<BaseBean> stafflist = workService.findStaffList(companyId, orgID);
		request.setAttribute("stafflist",stafflist);
		return "stafflist";
	}


	
	public BaseBeanService getBaseBeanService() {
		return baseBeanService;
	}
	public void setBaseBeanService(BaseBeanService baseBeanService) {
		this.baseBeanService = baseBeanService;
	}
	public ServerService getServerService() {
		return serverService;
	}
	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getSccid() {
		return sccid;
	}
	public void setSccid(String sccid) {
		this.sccid = sccid;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public List<Object> getList1() {
		return list1;
	}
	public void setList1(List<Object> list1) {
		this.list1 = list1;
	}
	public ServletRequest getRequest() {
		return request;
	}
	public void setRequest(ServletRequest request) {
		this.request = request;
	}


	public AuditRecord getAuditRecord() {
		return auditRecord;
	}


	public void setAuditRecord(AuditRecord auditRecord) {
		this.auditRecord = auditRecord;
	}


	public String getAuid() {
		return auid;
	}



	public void setAuid(String auid) {
		this.auid = auid;
	}



	public String getAuditComment() {
		return AuditComment;
	}


	public void setAuditComment(String auditComment) {
		AuditComment = auditComment;
	}



	public String getParameter() {
		return parameter;
	}



	public void setParameter(String parameter) {
		this.parameter = parameter;
	}



	public List<BaseBean> getList() {
		return list;
	}



	public void setList(List<BaseBean> list) {
		this.list = list;
	}



	public String getViewUrl() {
		return viewUrl;
	}



	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
	
}
