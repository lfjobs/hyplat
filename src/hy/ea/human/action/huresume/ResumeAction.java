package hy.ea.human.action.huresume;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.huresume.Resume;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

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

import com.opensymphony.xwork2.ActionContext;

/**
 * 网站人才
 * @author Administrator
 *
 */
public class ResumeAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;

	private List<BaseBean> resumeList;
	private Resume resume;
	private String search;
	private PageForm pageForm;
	private int pageNumber;
	private List<BaseBean> beans;
	private String result;
	
	public String toResume(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String groupCompanySn = ActionContext.getContext().getSession()
				.get("groupCompanySn").toString();
		String hql = "from Resume r where r.resumekey = ? and r.companyid = ?";
		resume = (Resume)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{resume.getResumekey(),account.getCompanyID()});
		
		String hql1 = "select count(*) from Staff where  staffIdentityCard = ? and groupCompanySn = ? ";
		
		int count = baseBeanService.getConutByByHqlAndParams(hql1, new Object[]{ resume.getIdentity(), groupCompanySn});
		
		if(count == 0){
			beans = new ArrayList<BaseBean>();
			Staff staff = new Staff();
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			
			staff.setStaffID(serverService.getServerID("cstaff"));
			staff.setGroupCompanySn(groupCompanySn);
			staff.setStaffStatus("00");
			staff.setStaffCode("NO" + pcount);
			staff.setRecordCode("NO" + pcount);
			staff.setStaffName(resume.getCname());
			if(resume.getSex().equals("1")){
				staff.setSex("男");
			}else{
				staff.setSex("女");
			}
			staff.setNativePlace(resume.getNativeplace());
			staff.setNation(resume.getNation());
			staff.setStaffIdentityCard(resume.getIdentity());
			staff.setStaffAddress(resume.getResidenceplace());
			staff.setAddress(serverService.getServerID("address"));
			staff.setReference(resume.getCommunicate());
			staff.setVerifyTime(new Date());
			staff.setBirthday(resume.getBirthday().toString().substring(0, 10));
			staff.setStaus("00");
			staff.setStatus("00");
			
			beans.add(staff);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("suc", "suc");
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
			
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("suc", "roo");
			JSONObject oj = JSONObject.fromObject(map);
			result = oj.toString();
		}
		
		
		return "success";
	}
	
	public String toSearch(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", resume);
		
		return getList();
	}
	
	
	public String getList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), 
				(pageNumber==0?5:pageNumber), getlists(session,account));
		
		
		return "list";
		
	}

	private DetachedCriteria getlists(Map<String,Object> session ,CAccount account){
		
		DetachedCriteria dc=DetachedCriteria.forClass(Resume.class);
		dc.add(Restrictions.eq("companyid", account.getCompanyID()));
		if (search != null && search.equals("search")) {
			resume = (Resume) session.get("tablesearch");
			if(resume != null){
				if(resume.getCname() !=null && !"".equals(resume.getCname().trim())){
					dc.add(Restrictions.like("cname", resume.getCname().trim(), MatchMode.ANYWHERE));
				}
				if(resume.getSex()!=null && !"".equals(resume.getSex().trim())){
					if(resume.getSex().trim().equals("男")){
						dc.add(Restrictions.like("sex", "1", MatchMode.ANYWHERE));
					}
					if(resume.getSex().trim().equals("女")){
						dc.add(Restrictions.like("sex", "0", MatchMode.ANYWHERE));
					}
				}
			}
		}
		
		return dc;
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

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public List<BaseBean> getResumeList() {
		return resumeList;
	}

	public void setResumeList(List<BaseBean> resumeList) {
		this.resumeList = resumeList;
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

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
