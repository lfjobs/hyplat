package hy.ea.ddsr.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.ddsr.Ddsrcontent;
import hy.ea.bo.ddsr.Ddsrsyllabus;
import hy.ea.bo.ddsr.Dssrsubject;
import hy.ea.util.DateJsonValueProcessor;
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
import net.sf.json.JsonConfig;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * @author zhb
 * 
 */
@Controller
@Scope("prototype")
public class SubjectManagerAction {

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private String innerAction;

	private Dssrsubject dssrSubject;
	private Map<String, Ddsrsyllabus> ddsrsyllabusMap;
	private Ddsrsyllabus ddsrsyllabus;
	private Map<String, Ddsrcontent> ddsrcontentMap;
	private Ddsrcontent ddsrcontent;
	private String result;

	public String doSubjectManagerAction() {
		if (innerAction == null || "".equals(innerAction.trim()))
			return getSubjectList();
		else if ("showSubjectList".equals(innerAction.trim()))
			return getSubjectList();
		else if ("updateSubject".equals(innerAction.trim()))
			return updateSubject();
		else if ("delSubject".equals(innerAction.trim()))
			return delSubject();
		else
			return getSubjectList();
	}

	private String getSubjectList() {
		pageForm = baseBeanService.getPageFormByDC(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), getList());

		return "showSubjectList";
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(Dssrsubject.class);
		dc.add(Restrictions.eq("subjCompanyid", companyID));
		dc.addOrder(Order.asc("subjType"));
		return dc;
	}

	private String updateSubject() {
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		if (null == dssrSubject.getSubjKey()
				|| "".equals(dssrSubject.getSubjKey())) {
			dssrSubject.setSubjCompanyid(companyID);
		} else {
			//String hql = " from DssrSubject where subjKey = ? ";
			//Dssrsubject _dssrSubject = (Dssrsubject) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { dssrSubject.getSubjKey() });
		}
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(dssrSubject);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	private String delSubject() {
		baseBeanService.deleteBeanByKey(Dssrsubject.class, dssrSubject.getSubjKey());
		return "success";
	}
	
	/**
	 * @time 2014-09-11
	 * @author zc
	 * @return
	 * 保存教学内容
	 */
	public String toSaveTeachingContentByAjax(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		List<BaseBean>  beanList=new ArrayList<BaseBean>();
		/*String hql=" delete from Ddsrsyllabus where companyID=? and type=?";*/
		for (Ddsrsyllabus ddsy : ddsrsyllabusMap.values()) {
			if(null==ddsy.getId()||"".equals(ddsy.getId())){
				ddsy.setId(serverService.getServerID("ddsrsyllabus"));
			}
			ddsy.setCompanyID(account.getCompanyID());
			ddsy.setType(ddsrsyllabus.getType());
			beanList.add(ddsy);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beanList, null, null);
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("message", "操作成功");
		JSONObject json = JSONObject.fromObject(mapValue);
		result=json.toString();
		return "success";
	}
	
	/**
	 * @time 2014-09-11
	 * @author zc
	 * @return
	 * 获得教学内容
	 */
	public String getListOfTeachingContentByAjax(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String hql=" from Ddsrsyllabus where companyID=? and type=?";
		List<BaseBean>  beanList=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),ddsrsyllabus.getType()});
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("beanList", beanList);
		JSONObject json = JSONObject.fromObject(mapValue);
		result=json.toString();
		return "success";
	}
	
	/**
	 * @time 2014-10-23
	 * @author zc
	 * @return
	 * 保存教学内容详细内容
	 */
	public String toSaveTeachingContentOfDetailsByAjax(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		List<BaseBean>  beanList=new ArrayList<BaseBean>();
		for (Ddsrcontent ddsc : ddsrcontentMap.values()) {
			if(null==ddsc.getId()||"".equals(ddsc.getId())){
				ddsc.setId(serverService.getServerID("ddsrcontent"));
			}
			ddsc.setAddTime(new Date());
			ddsc.setCompanyID(account.getCompanyID());
			beanList.add(ddsc);
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beanList, null, null);
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("message", "操作成功");
		JSONObject json = JSONObject.fromObject(mapValue);
		result=json.toString();
		return "success";
	}
	/**
	 * @time 2014-10-23
	 * @author zc
	 * @return
	 * 获得教学内容详细内容
	 */
	public String getListOfTeachingContentOfDetailsByAjax(){
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		List<Object> params=new ArrayList<Object>();
		String hql=" from Ddsrcontent where 1=1 ";
		hql+=" and  companyID=? ";
		params.add(account.getCompanyID());
		if(ddsrcontent!=null){
			if(ddsrcontent.getType()!=null&&!"".equals(ddsrcontent.getType())){
				hql+=" and type = ? ";
				params.add(ddsrcontent.getType().trim());
			}
			if(ddsrcontent.getSubject()!=null&&!"".equals(ddsrcontent.getSubject())){
				hql+=" and subject = ? ";
				params.add(ddsrcontent.getSubject().trim());
			}
			if(ddsrcontent.getProgram()!=null&&!"".equals(ddsrcontent.getProgram())){
				hql+=" and program = ? ";
				params.add(ddsrcontent.getProgram().trim());
			}
			if(ddsrcontent.getContent()!=null&&!"".equals(ddsrcontent.getContent())){
				hql+=" and content like ? ";
				params.add("%"+ddsrcontent.getContent().trim()+"%");
			}
		}
		List<BaseBean>  beanList=baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("beanList", beanList);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出
		JSONObject json = JSONObject.fromObject(mapValue,jsonConfig);
		result=json.toString();
		return "success";
	}
	/**
	 * @time 2014-10-23
	 * @author zc
	 * @return
	 * 删除教学内容详细内容
	 */
	public String deleteOfTeachingContentOfDetailsByAjax(){
		baseBeanService.deleteBeanByKey(Ddsrcontent.class, ddsrcontent.getKey());
		Map<String, Object>  mapValue=new HashMap<String, Object>();
		mapValue.put("message", "1");
		JSONObject json = JSONObject.fromObject(mapValue);
		result=json.toString();
		return "success";
	}
	public String getInnerAction() {
		return innerAction;
	}

	public void setInnerAction(String innerAction) {
		this.innerAction = innerAction;
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public Dssrsubject getDssrSubject() {
		return dssrSubject;
	}

	public void setDssrSubject(Dssrsubject dssrSubject) {
		this.dssrSubject = dssrSubject;
	}

	public Map<String, Ddsrsyllabus> getDdsrsyllabusMap() {
		return ddsrsyllabusMap;
	}

	public void setDdsrsyllabusMap(Map<String, Ddsrsyllabus> ddsrsyllabusMap) {
		this.ddsrsyllabusMap = ddsrsyllabusMap;
	}

	public Ddsrsyllabus getDdsrsyllabus() {
		return ddsrsyllabus;
	}

	public void setDdsrsyllabus(Ddsrsyllabus ddsrsyllabus) {
		this.ddsrsyllabus = ddsrsyllabus;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Ddsrcontent> getDdsrcontentMap() {
		return ddsrcontentMap;
	}

	public void setDdsrcontentMap(Map<String, Ddsrcontent> ddsrcontentMap) {
		this.ddsrcontentMap = ddsrcontentMap;
	}

	public Ddsrcontent getDdsrcontent() {
		return ddsrcontent;
	}

	public void setDdsrcontent(Ddsrcontent ddsrcontent) {
		this.ddsrcontent = ddsrcontent;
	}
	
}
