package mobile.tiantai.android.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.tiantai.android.bo.DtMicroletterMenu;
import mobile.tiantai.android.bo.DtMicroletterMenuContent;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
/**
 * 微信图片列表
 */
@Controller
@Scope("prototype")
public class MicroLetterContentAction {
	private int pageNumber;
	private InputStream excelStream;
	private PageForm pageForm;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ServerService serverService;

	private String search;
	private String parameter;
	@Resource
	private BaseBeanService baseBeanService;
	private DtMicroletterMenuContent dtMicroletterMenuContent;
	private DtMicroletterMenu dtMicroletterMenu;
	@Resource
	private UpLoadFileService fileService;
	
	private String result;

	//根据条件查询企业图片列表
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", dtMicroletterMenuContent);
		return getDtMicroletterMenuContentList();
	}

	private DetachedCriteria getCAList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		DetachedCriteria dc = DetachedCriteria.forClass(DtMicroletterMenuContent.class);
		dc.createAlias("dtMicroletterMenu", "dtMicroletterMenu");
		dc.add(Restrictions.eq("dtMicroletterMenu.microlettermenukey", dtMicroletterMenu.getMicrolettermenukey()));
		if (search != null && search.equals("search")) {
			dtMicroletterMenuContent = (DtMicroletterMenuContent) session.get("tablesearch");
		}
		return dc;
	}

	/***********************************************/

	//企业图片列表
	public String getDtMicroletterMenuContentList() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber():1), (pageNumber==0?4:pageNumber),getCAList());
		return "microlettercontent_List";	
	}
	
	/**
	 * ajax根据菜单ID 查询 图片信息 for android 4028e445428d30a901428e151de20041
	 * http://localhost:8080/hyplat/ea/microlettercontent/sajax_ea_getAjaxListDtMicroletterMenuContentAllforAndroid.jspa?dtMicroletterMenu.microlettermenukey=4028e445428d30a901428e151de20041
	 * @return listDtMicroletterMenuContent
	 */
	public String getAjaxListDtMicroletterMenuContentAllforAndroid(){
		DetachedCriteria dc = DetachedCriteria.forClass(DtMicroletterMenuContent.class);
		dc.createAlias("dtMicroletterMenu", "dtMicroletterMenu");
		if(dtMicroletterMenu!=null&&dtMicroletterMenu.getMicrolettermenukey()!=null&&!"".equals(dtMicroletterMenu.getMicrolettermenukey().trim())){
			dc.add(Restrictions.eq("dtMicroletterMenu.microlettermenukey", dtMicroletterMenu.getMicrolettermenukey().trim()));
		}
		if(dtMicroletterMenu!=null&&dtMicroletterMenu.getCompanyid()!=null&&!"".equals(dtMicroletterMenu.getCompanyid().trim())){
			dc.add(Restrictions.eq("dtMicroletterMenu.companyid",dtMicroletterMenu.getCompanyid()));
		}
		List<BaseBean> listDtMicroletterMenuContent=baseBeanService.getListByDC(dc);
		Map<String, Object> map=new HashMap<String, Object>();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); 
		jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer","dtMicroletterMenu"});  
		if(listDtMicroletterMenuContent==null){
			map.put("listDtMicroletterMenuContent", "无数据");
		}else{
			map.put("listDtMicroletterMenuContent", listDtMicroletterMenuContent);
		}
		JSONObject json=JSONObject.fromObject(map,jsonConfig);
		result=json.toString();
		return  "success";
	}
	
  //保存企业图片列表
    
    public String saveDtMicroletterMenuContent(){
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		if (dtMicroletterMenuContent.getPhoto() != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path, dtMicroletterMenuContent.getPhotoFileName(),
					dtMicroletterMenuContent.getPhoto(), account.getCompanyID(), "/office/dtMicroletterMenuContent/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			dtMicroletterMenuContent.setMicrolettermenucontentimageurl(photoPath);
		}
		if (dtMicroletterMenuContent.getMicrolettermenucontentid()== null
				|| "".equals(dtMicroletterMenuContent.getMicrolettermenucontentid())) {
			dtMicroletterMenuContent.setMicrolettermenucontentid(serverService.getServerID("dtMicrolMenuCon"));
			parameter = "添加微信图片列表(图片编码:"+dtMicroletterMenuContent.getMicrolettermenucontentdescribe()+")";
			dtMicroletterMenuContent.setMicrolettermenucontentcdate(new Date());
			dtMicroletterMenuContent.setMicrolettermenucontentudate(new Date());
		}
		else
		{
			parameter = "修改微信图片列表(图片编码:"+dtMicroletterMenuContent.getMicrolettermenucontentdescribe()+")";
			dtMicroletterMenuContent.setMicrolettermenucontentudate(new Date());
		}
		dtMicroletterMenuContent.setDtMicroletterMenu(dtMicroletterMenu);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(dtMicroletterMenuContent);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);
		return "success";
    }

    //删除企业图片列表
	 public String delDtMicroletterMenuContent(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
		    Object[] params = {dtMicroletterMenuContent.getMicrolettermenucontentid()};
		    String hql1=" from DtMicroletterMenuContent where  microlettermenucontentid=?" ;
		    DtMicroletterMenuContent cf=(DtMicroletterMenuContent) baseBeanService.getBeanByHqlAndParams(hql1, params);
		    CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除企业图片列表(图片编码:"+cf.getMicrolettermenucontentdescribe()+")", account);
	    	String hql="delete from DtMicroletterMenuContent where   microlettermenucontentid=?";
	    	List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
			baseBeansList.add(cLogBook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, new String[]{hql}, params);
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

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public DtMicroletterMenuContent getDtMicroletterMenuContent() {
		return dtMicroletterMenuContent;
	}

	public void setDtMicroletterMenuContent(
			DtMicroletterMenuContent dtMicroletterMenuContent) {
		this.dtMicroletterMenuContent = dtMicroletterMenuContent;
	}

	public DtMicroletterMenu getDtMicroletterMenu() {
		return dtMicroletterMenu;
	}

	public void setDtMicroletterMenu(DtMicroletterMenu dtMicroletterMenu) {
		this.dtMicroletterMenu = dtMicroletterMenu;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
