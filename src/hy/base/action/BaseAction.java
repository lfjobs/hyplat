package hy.base.action;

import hy.ea.bo.CAccount;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>,
		ServletRequestAware, ServletResponseAware {

	@Resource
	protected BaseBeanService baseBeanService;
	@Resource
	protected CLogBookService logBookService;
	@Resource
	protected ServerService serverService;
	@Resource
	protected ShowExcelService excelService;
	protected PageForm pageForm;
	protected Object result;
	protected int pageNumber;

	protected T entity;
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	@SuppressWarnings("unchecked")
	public BaseAction(){
		@SuppressWarnings("rawtypes")
		Class entityClass = GenericSuperClassUtils.getGenericSuperClass(this.getClass());
		try {
			entity = (T) entityClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取当前session用户
	 * @return
	 */
	public CAccount getCurrentAccount() {
		return (CAccount) ActionContext.getContext().getSession()
				.get("account");
	}
	/**
	 * 获取当前部门Id
	 * @return
	 */
	public String getOrganizationId(){
		return  ActionContext.getContext().getSession()
				.get("organizationID").toString();
	}
	
	/**
	 * 获取当前集团SN
	 * @return
	 */
	public String getGroupCompanySn(){
		return  ActionContext.getContext().getSession()
				.get("groupCompanySn").toString();
	}
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	@Override
	public void setServletResponse(HttpServletResponse res) {
		this.response = res;

	}

	@Override
	public void setServletRequest(HttpServletRequest req) {
		this.request = req;
	}

	@Override
	public T getModel() {
		return entity;
	}
	public Object isNull(Object tep){
		if(tep==null||"null".equals(tep)){
			return "";
		}else{
			return tep;
		}
	}
}
