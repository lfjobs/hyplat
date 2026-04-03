package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.human.Dtrecruitchannel;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 招聘渠道管理
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class RecruitChannelAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CCodeService codeService;
	
	private Dtrecruitchannel recruitchannel;
	public InputStream excelStream;
	private PageForm pageForm;
	private List<BaseBean> channelList;
	
	private int pageNumber;
	private String parameter;
	private String search;
	public String result;
	
	/**
	 * 前台页面Ajax获取招聘渠道类别List
	 * @return
	 */
	public String getSortsList(){
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		List<CCode> codeSortsList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20121122drr7pykbwr0000000002");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeSortsList", codeSortsList);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 查询
	 * @return
	 */
	public String toSearch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", recruitchannel);
		return getRecruitChannelList();
	}
	
	/**
	 * 获取招聘渠道列表
	 * @return
	 */
	public String getRecruitChannelList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params);
		return "list";
	}
	
	/**
	 * 查询列表（可根据条件查询）被调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,CAccount account){
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String hql = " from Dtrecruitchannel where companyID = ?";
		parms.add(account.getCompanyID());
		if(search != null && search.equals("search")){
			recruitchannel = (Dtrecruitchannel)session.get("tablesearch");
			if(recruitchannel.getSorts() != null 
					&& !"".equals(recruitchannel.getSorts())){
				hql += " and sorts = ?";
				parms.add(recruitchannel.getSorts());
			}
			if(recruitchannel.getChannelname() != null 
					&& !"".equals(recruitchannel.getChannelname())){
				hql += " and channelname like ?";
				parms.add("%" +recruitchannel.getChannelname() + "%");
			}
		}
		hql += " order by recruitchannelid desc";
		results.add(hql);
		results.add(parms.toArray());
		return results;
	}
	
	/**
	 * 添加/修改
	 * @return
	 */
	public String saveRecruitChannel(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		if(recruitchannel.getRecruitchannelid() == null 
				|| "".equals(recruitchannel.getRecruitchannelid())){
			recruitchannel.setRecruitchannelid(serverService.getServerID("recruitchannel"));
			recruitchannel.setCompanyID(account.getCompanyID());
			parameter = "添加招聘渠道,名称为：" + recruitchannel.getChannelname();
		}else{
			parameter = "修改招聘渠道,名称为：" + recruitchannel.getChannelname();
		}
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(recruitchannel);
		String organizationID = (String) session.get("organizationID");
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String deleteRecruitChannel(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		
		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> hqls = new ArrayList<String>();
		List<Object[]> pars = new ArrayList<Object[]>();
		parameter = "删除招聘渠道,名称为：" + recruitchannel.getChannelname();
		CLogBook logbook = logBookService.saveCLogBook(organizationID,
				parameter, account);
		String hql = "delete from Dtrecruitchannel where recruitchannelid = ?";
		Object[] param = {recruitchannel.getRecruitchannelid()};
		
		beans.add(logbook);
		hqls.add(hql);
		pars.add(param);
		baseBeanService.executeHqlsByParamsList(beans, hqls.toArray(new String[0]), pars);
		return getRecruitChannelList();
	}
	
	/**
	 * 导出
	 * @return
	 */
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		excelStream = excelService.showExcel(Dtrecruitchannel.columnHeadings(),
				baseBeanService.getListBeanByHqlAndParams(hql, params));
		CLogBook logBook =logBookService.saveCLogBook(organizationID,"导出招聘渠道管理", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	
	/**
	 * 打印预览
	 * @return
	 */
	public String toPrint(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) session.get("account");
		Company entity = (Company) baseBeanService.getBeanByHqlAndParams(
				" from Company where companyID = ? ", new Object[] { account
						.getCompanyID() });
		request.setAttribute("companyname", entity.getCompanyName());
		List<Object> list = getList(session, account);
		String hql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		channelList = baseBeanService.getListBeanByHqlAndParams(hql, params);
		String organizationID=(String) session.get("organizationID");
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"打印全部招聘渠道", account);
		baseBeanService.update(logBook);
		return "toprint";
	}
	
	public Dtrecruitchannel getRecruitchannel() {
		return recruitchannel;
	}
	public void setRecruitchannel(Dtrecruitchannel recruitchannel) {
		this.recruitchannel = recruitchannel;
	}
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public List<BaseBean> getChannelList() {
		return channelList;
	}
	public void setChannelList(List<BaseBean> channelList) {
		this.channelList = channelList;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}