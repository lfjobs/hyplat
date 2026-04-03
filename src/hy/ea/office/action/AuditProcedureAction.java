package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.audits.Auditor;
import hy.ea.bo.audits.Phase;
import hy.ea.bo.audits.Procedure;
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

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 
 * 
 * 审核流程模块
 * @author mz
 *
 */
@Controller
@Scope("prototype")
public class AuditProcedureAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private PageForm pageForm;
	private InputStream excelStream;
	private String search;
	private int pageNumber;
	private Procedure procedure;//审核流程
	private Phase phase;//审核阶段
	private Auditor auditor;//审核人表
	private String strid;//人ID-公司ID-部门ID 
	private String result;
	private List<BaseBean> list;
	private List<BaseBean> phaselist;
	private List<BaseBean> auditlist;


  
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", procedure);
		return getProcedureList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(Procedure.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			procedure = (Procedure) session.get("tablesearch");
			dc.add(Restrictions.like("organizationID", procedure.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}


	
	
	/**
	 * 
	 * 
	 * 获取审核流程列表
	 * @return
	 */
	public String getProcedureList() {
	    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
		return "procedurelist";	
	}

    
	
	/**
	 * 
	 * 
	 * 保存流程
	 * @return
	 */
    public String saveProcedure(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		try{
		//保存流程
		if(procedure.getProId()==null||procedure.getProId().equals("")){
			
			String proId = serverService.getServerID("proId");
			procedure.setProId(proId);
			procedure.setCompanyID(account.getCompanyID());
			procedure.setOrganizationID(organizationID);
			procedure.setStatus("00");
			procedure.setCreatorID(account.getStaffID());
			procedure.setCreateDate(new Date());
			baseBeanService.save(procedure);
			
		}else{
			String hql = "from Procedure where proId = ?";
			Procedure pro = (Procedure) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{procedure.getProId()});
	        pro.setStartDate(procedure.getStartDate());
	        pro.setEndDate(procedure.getEndDate());
	        pro.setForms(procedure.getForms());
	        pro.setProName(procedure.getProName());
	        baseBeanService.update(pro);
	        
		}
		
		//保存阶段
		if(phase.getPhaseId()==null||phase.getPhaseId().equals("")){
			
			String phaseId = serverService.getServerID("phaseId");
			phase.setPhaseId(phaseId);
			phase.setCompanyID(account.getCompanyID());
			phase.setCreateDate(new Date());
			phase.setCreatorID(account.getStaffID());
			phase.setOrganizationID(organizationID);
			phase.setProID(procedure.getProId());
			baseBeanService.save(phase);
			
		}else{
			
			String hql = "from Phase where phaseId = ?";
		    Phase ph = (Phase) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{phase.getPhaseId()});
		    ph.setPhaseName(phase.getPhaseName());
		    ph.setAuditType(phase.getAuditType());
		    ph.setOrderNum(phase.getOrderNum());
		    baseBeanService.update(ph);
		    
		}

		
		
		//保存审核人
		
		//先删除这个阶段的所有审核人
		
		
		if (phase.getPhaseId() != null && !phase.getPhaseId().equals("")) {

			String deletehql = "delete Auditor where phaseId = ?";
			String[] hqls = { deletehql };
			Object[] param = new Object[] { phase.getPhaseId() };
			List<Object[]> parmsList = new ArrayList<Object[]>();
			parmsList.add(param);

			baseBeanService.executeHqlsByParamsList(null, hqls, parmsList);
		}
		//删除后再添加新的的
		
		
		
		String[] arraystrid = strid.split(",");
		String[] arrayaudtor = auditor.getAuditorName().split(",");
		
		List<BaseBean> beans=new ArrayList<BaseBean>();
		
		Auditor ad = null;
		for (int i = 0; i < arrayaudtor.length; i++) {
			ad = new Auditor();
			String strid = arraystrid[i];//获取人ID-公司ID-部门ID
			String[] strids = strid.split("-");//分别获取
			ad.setAuditId(serverService.getServerID("auditId"));
			
			ad.setAuditorName(arrayaudtor[i]);//名称
			ad.setAuditorID(strids[0]);//人ID
			ad.setCompanyID(strids[1]);//人公司
			ad.setOrganizationID(strids[2]);//人部门
			ad.setProId(procedure.getProId());
			ad.setPhaseId(phase.getPhaseId());
            beans.add(ad);
			
		}
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
    	return "savesuc";
    }
   
    
    
    /**
     * 
     * 
     * 获取阶段信息 利用 proId 以及 orderNujmn
     * @return
     */
    public String  getPhaseInfo(){
    	
    	String hql = "from Phase where proId = ? and orderNum = ?";
    	
    	Phase p  = (Phase) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{procedure.getProId(),phase.getOrderNum()});
    	
    	
    	Map<String,Object> map  = new HashMap<String,Object>();
    	map.put("result", p);
    	JSONObject jo = JSONObject.fromObject(map);
    	this.result = jo.toString();
    	return "success";
    	
    }
    
    
    
    /**
     * 
     * 根据ID获取阶段
     * @return
     */
      public String  getPhaseInfoById(){
    	
    	String hql = "from Phase where phaseId = ?";
    	
    	Phase p  = (Phase) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{phase.getPhaseId()});
    	
    	hql = "from Auditor where phaseId = ?";
    	auditlist = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{phase.getPhaseId()});
    	
    	
    	Map<String,Object> map  = new HashMap<String,Object>();
    	map.put("phase", p);
    	map.put("auditlist", auditlist);
    	JSONObject jo = JSONObject.fromObject(map);
    	this.result = jo.toString();
    	return "success";
    	
    }
    
    
    
    /**
     * 
     * 
     * 
     * @return
     */
    public String editProcedure(){
    	
    	String hql = "from Procedure where proId = ?";
    	procedure = (Procedure) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{procedure.getProId()});
    	hql = "from Phase where proID = ? order by orderNum";
    	phaselist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{procedure.getProId()});
    	
   
    	return "edit";
    }
    
    
    /**
     * 
     * 
     * 获取提示内容
     * @return
     */
    public String getAudtiorInfo(){
    	
    	String hql = "from Phase where proID = ? order by orderNum";
    	phaselist = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{procedure.getProId()});
    	
    	hql = "from Auditor where proId = ?";

    	auditlist = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{procedure.getProId()});

    	Map<String,Object> map= new HashMap<String,Object>();
    	
    	map.put("auditlist", auditlist);
    	map.put("phaselist", phaselist);
    	
    	JSONObject jo = JSONObject.fromObject(map);
    	this.result = jo.toString();
    	return "success";
    }
    
    
    /**
     * 
     * 修改phase
     */
    public String updatePhase(){
    	String hql = "from Phase where phaseId = ?";
    	Phase p = (Phase) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{phase.getPhaseId()});
    	p.setAuditType(phase.getAuditType());
    	p.setPhaseName(phase.getPhaseName());
    	p.setCreateDate(new Date());
		//先删除这个阶段的所有审核人
		
		
		if (phase.getPhaseId() != null && !phase.getPhaseId().equals("")) {

			String deletehql = "delete Auditor where phaseId = ?";
			String[] hqls = { deletehql };
			Object[] param = new Object[] { phase.getPhaseId() };
			List<Object[]> parmsList = new ArrayList<Object[]>();
			parmsList.add(param);

			baseBeanService.executeHqlsByParamsList(null, hqls, parmsList);
		}
		//删除后再添加新的的
		
		
		
		String[] arraystrid = strid.split(",");
		String[] arrayaudtor = auditor.getAuditorName().split(",");
		
		List<BaseBean> beans=new ArrayList<BaseBean>();
		
		Auditor ad = null;
		for (int i = 0; i < arrayaudtor.length; i++) {
			ad = new Auditor();
			String strid = arraystrid[i];//获取人ID-公司ID-部门ID
			String[] strids = strid.split("-");//分别获取
			ad.setAuditId(serverService.getServerID("auditId"));
			
			ad.setAuditorName(arrayaudtor[i]);//名称
			ad.setAuditorID(strids[0]);//人ID
			ad.setCompanyID(strids[1]);//人公司
			ad.setOrganizationID(strids[2]);//人部门
			ad.setProId(procedure.getProId());
			ad.setPhaseId(phase.getPhaseId());
            beans.add(ad);
			
		}
		
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    	
    	
    	return "edit";
    }
    
    
    /*********************************************个人审核管理************************************************/

    
    
    /**
     * 
     * 个人审核
     */
    public String getAuditList(){
    	
    	
    	return "auditlist";
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



	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public Auditor getAuditor() {
		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	public String getStrid() {
		return strid;
	}

	public void setStrid(String strid) {
		this.strid = strid;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public List<BaseBean> getPhaselist() {
		return phaselist;
	}

	public void setPhaselist(List<BaseBean> phaselist) {
		this.phaselist = phaselist;
	}

	public List<BaseBean> getAuditlist() {
		return auditlist;
	}

	public void setAuditlist(List<BaseBean> auditlist) {
		this.auditlist = auditlist;
	}
	
    
}
