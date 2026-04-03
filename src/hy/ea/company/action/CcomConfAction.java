package hy.ea.company.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.CcomConf;
import hy.ea.bo.company.ContactCompany;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.ImageCut;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 公司首页配置管理
 * */
@Controller
@Scope("prototype")
public class CcomConfAction {
	private int pageNumber;
	private PageForm pageForm;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;
	private String search;
	private String ccompanyID;
	private String parameter;
	private CcomConf ccomConf;
	private String ccomConfId;
	private String modalName;
	private Map<String, CcomConf> ccomconfmap;
	private String result;
	
	
	/**
	 * 
	 * 获取添加修改页面
	 * @return
	 */
	public String findAddOrEditPage(){
		if(ccomConf.getCcomConfId()!=null&&!ccomConf.getCcomConfId().equals("")){
			String hql = "from CcomConf where ccomConfId = ?";
			ccomConf = (CcomConf) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccomConf.getCcomConfId()});
			
		}
		
		return "topage";
	}
	
	/**
	 * 
	 * 新版保存功能
	 * @return
	 */
	public String saveConfig(){
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		if(ccomConf.getCcomConfId()==null||ccomConf.getCcomConfId().equals("")){
			ccomConf.setCcomConfId(serverService.getServerID("CcomConf"));
		}
		if(ccomConf.getPhoto()!=null){
			String path=ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, ccomConf.getPhotoFileName(),
					ccomConf.getPhoto(), ccomConf.getCcompanyId(), "/human/ccomconf/"
						+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			ContactCompany contantcompany = (ContactCompany)baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID = ?", new Object[]{ccomConf.getCcompanyId()});
			String jjPath = photoPath.split("\\.")[0] + "small." + photoPath.split("\\.")[1];
			ImageCut.scale(path+photoPath, path+jjPath, 460, 276);
			contantcompany.setJjPath(jjPath);
			ccomConf.setPicPath(photoPath);
			baseBeansList.add(contantcompany);
		}
		baseBeansList.add(ccomConf);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,null,null);
		return "success";
	}
	
	
	//查询
	public String toSearch() {
	Map<String, Object> session = ActionContext.getContext().getSession();
	session.put("tablesearch", ccomConf);
	return getCconConfList();
}
	
	//查询全部的公司配置表
	public String getCconConfList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		List<Object> parms = new ArrayList<Object>();
		parms.add(ccomConf.getCcompanyId());
		CAccount ca= (CAccount) session.get("account");
		String hql="from CcomConf where ccompanyId= ? and modalType between '0' and '1'";
		if(ca.getCompany().getCompanyIdentifier().equals("fljybj")){
			hql="from CcomConf where ccompanyId= ?";
		}
		if (search != null && search.equals("search")) {
			ccomConf = (CcomConf) session.get("tablesearch");
			if(null!=ccomConf.getModalName()&&!ccomConf.getModalName().equals("")){
				hql+=" and modalName=?";
				parms.add(ccomConf.getModalName());
			}
		} 
		String hql1 ="order by cast(sn as integer)";
		pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?4:pageNumber), hql+hql1, parms.toArray());
		return "getCconConfList";
	}
	//保存
	public String saveCconConf(){
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		List<BaseBean> bBaseBean = new ArrayList<BaseBean>();
		for(CcomConf cc:ccomconfmap.values()){
			if(cc.getPhoto()!=null){
				String path=ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
				String photoPath = fileService.savePhoto(path, cc.getPhotoFileName(),
   						cc.getPhoto(), ccomConf.getCcompanyId(), "/human/ccomconf/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
   				cc.setPicPath(photoPath);
   				
			}
   			if(null==cc.getCcomConfId()||"".equals(cc.getCcomConfId())){
   				cc.setCcomConfId(serverService.getServerID("CcomConf"));
   				parameter = "添加公司配置列表(配置编号:"+cc.getCcomConfId()+")";
   			}else{
   				String hql = " from CcomConf where ccomConfId=?";
   				CcomConf cof=(CcomConf) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{cc.getCcomConfId()});
   				parameter = "修改公司配置列表(配置编号:"+cof.getCcomConfId()+")";
   			}
   			cc.setCcompanyId(ccomConf.getCcompanyId());
			CLogBook cLogBook = logBookService.saveCLogBook(null, parameter, account);
			bBaseBean.add(cc);
			bBaseBean.add(cLogBook);

    }
		baseBeanService.saveBeansListAndexecuteHqlsByParams(bBaseBean, null, null);
		return "success";
	}
	//删除
	public String delCcomConf(){
	    CAccount account =(CAccount) ActionContext.getContext().getSession().get("account");
	    Object[] params = {ccomConf.getCcomConfId()};
	    String hql1=" from CcomConf where ccomConfId=?";
	    CcomConf cof=(CcomConf) baseBeanService.getBeanByHqlAndParams(hql1, params);
		CLogBook cLogBook = logBookService.saveCLogBook(null, "删除公司首页配置列表(配置编号:"+cof.getCcomConfId()+")", account);
    	String hql="delete from CcomConf where  ccomConfId=?";
    	List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
    	baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
		return "success";
	}
	//验证是否有公司简介
	public String ajaxCheck(){
		String flag="1";
		String hql="from CcomConf ccf where ccf.ccompanyId= ? and ccf.modalType='0' ";
		CcomConf ccomconf=(CcomConf) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ccomConf.getCcompanyId()});
		if(ccomconf==null){
			flag="0";
		}
		JSONObject obj=new JSONObject();
		obj.accumulate("flag", flag);
		result=obj.toString();
		return "success";
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
	public String getCcompanyID() {
		return ccompanyID;
	}
	public void setCcompanyID(String ccompanyID) {
		this.ccompanyID = ccompanyID;
	}        
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public CcomConf getCcomConf() {
		return ccomConf;
	}
	public void setCcomConf(CcomConf ccomConf) {
		this.ccomConf = ccomConf;
	}
	public String getCcomConfId() {
		return ccomConfId;
	}
	public void setCcomConfId(String ccomConfId) {
		this.ccomConfId = ccomConfId;
	}
	public Map<String, CcomConf> getCcomconfmap() {
		return ccomconfmap;
	}
	public void setCcomconfmap(Map<String, CcomConf> ccomconfmap) {
		this.ccomconfmap = ccomconfmap;
	}
	public String getModalName() {
		return modalName;
	}
	public void setModalName(String modalName) {
		this.modalName = modalName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
