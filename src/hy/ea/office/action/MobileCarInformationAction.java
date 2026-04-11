package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.CarInformation;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;
/*
 * 车辆信息
 * */
@Controller
@Scope("prototype")
public class MobileCarInformationAction {
	private static final Logger logger = LoggerFactory.getLogger(MobileCarInformationAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private ServerService serverService;  
	@Resource
	private CLogBookService logBookService; 
	private String parameter;
	private CarInformation carInformation;
	private PageForm pageForm;
	private InputStream excelStream;
	private File photo;
	private String photoContentType;
	private String search;
	private String sub;
	private int pageNumber;
    public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	//根据条件查询车辆信息
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", carInformation);
		return getCarInformationList();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc = DetachedCriteria.forClass(CarInformation.class);
		dc.add(Restrictions.eq("companyID", companyID));
		dc.add(Restrictions.eq("organizationID", organizationID));
		if (search != null && search.equals("search")) {
			carInformation = (CarInformation) session.get("tablesearch");
			//logger.info("调试信息");
			//logger.info("调试信息");
			dc.add(Restrictions.like("organizationID", carInformation.getOrganizationID(), MatchMode.ANYWHERE));
		} 
		return dc;
	}

	// 车辆信息列表  
	public String getCarInformationList() {
		if(pageNumber==0)
		{
			return "carInformationlist";
		}
		else
		{
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		    pageForm = baseBeanService.getPageFormByDC((pageNumber), (1), getList());
		    HttpServletResponse response = ServletActionContext.getResponse();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
			String outString = jsonArray.toString();
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(outString);
				//logger.info("值：{}", outString);
				response.flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("操作异常", e);
			}
			return null;
		}
	}
	// 导出车辆信息列表

	public String showExcel() {
		excelStream = excelService.showExcel(CarInformation.columnHeadings(), baseBeanService.getListByDC(getList()));
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		logBookService.saveCLogBook(organizationID,"导出车辆信息", account);
		return "showexcel";
	}
  //车辆信息保存
    
    public String saveCarInformation()
    {
    	ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String obj = (String) session.get("session_value");
		if (sub != null && sub.equals(obj)) {
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			String photoPath = fileService.savePhoto(path,photoContentType, photo, account.getCompanyID(),"images/ea/human/carInformation/");
			carInformation.setPhoto(photoPath);
		}
		if (carInformation.getCarID()== null|| "".equals(carInformation.getCarID())) {
			carInformation.setCarID(serverService.getServerID("carInformation"));
			parameter = "添加车辆信息(车牌号:"+carInformation.getCarNum()+")";
		}
		else
		{
			 String hql2="from CarInformation where companyID=?  and carID=?";
			  CarInformation carinfo=(CarInformation) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{account.getCompanyID(),carInformation.getCarID()});
		     parameter = "修改车辆信息(车牌号:"+carinfo.getCarNum()+")";
		
		}
		carInformation.setOrganizationID(organizationID);
		carInformation.setCompanyID(account.getCompanyID());
		baseBeanService.update(carInformation);
		logBookService.saveCLogBook(organizationID, parameter, account);
		}
    	return "success";
    }
    //删除车辆信息
	 public String delCarInformation()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
		    String obj = (String) session.get("session_value");
			String organizationID=(String) session.get("organizationID");
		    if (sub != null && sub.equals(obj)) {
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),carInformation.getCarID()};
		    String hql2="from CarInformation where companyID=?  and carID=?";
		    CarInformation carinfo=(CarInformation) baseBeanService.getBeanByHqlAndParams(hql2, params);
			logBookService.saveCLogBook(organizationID, "删除车辆信息(车牌号:"+carinfo.getCarNum()+")", account);
	    	String hql="delete from CarInformation where companyID=?  and carID=?";
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, params);
		    }
		    return "success";
	    }

	public CarInformation getCarInformation() {
		return carInformation;
	}

	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

}
