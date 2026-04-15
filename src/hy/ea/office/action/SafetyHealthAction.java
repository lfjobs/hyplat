package hy.ea.office.action;

import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.Dtcarassemblytable;
import hy.ea.bo.office.Dtsafetyhealth;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



import com.opensymphony.xwork2.ActionContext;

/*
 * 车辆安全卫生检查
 * */
@Controller
@Scope("prototype")
public class SafetyHealthAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private  CarInformation carInformation;
	private String safetycarID;
	private ShowExcelService excelService;
	private Dtsafetyhealth safetyhealth;
	private PageForm pageForm;
	private InputStream excelStream;
	private String companyname;
	private String organizationname;
	private String search;
	private String parameter;
	private int pageNumber;
	private List<BaseBean> carNumList;
	private String accountname;
	private String adddate;    //添加日期
	private String countrewards; //总奖励分
	private String countpenalty; //总处罚分
	private String totleScore; //总得分
	private Dtcarassemblytable carassemblytable ;
	private Map<String,Dtcarassemblytable> dtcarassemblytablemap;
	/*
	 * 获得车辆卫生安全检查列表
	 * */
	public String getSafetyHealthList(){
		Map<String, Object> session =  ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		Object[] params = { account.getCompanyID(),organizationID};
		String sql  = "select d.safetyid,d.adddate,c.organizationname,d.staffid,d.countrewards,d.countpenalty," +
				"d.totlescore,d.safetykey,d.companyid,d.organizationid from Dtsafetyhealth d  " +
				"left join dtcorganization c on c.organizationid = d.organizationid  where d.companyID = ? and d.organizationid= ?";
		accountname = account.getAccountName();
		//System.out.println(organizationID);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "+sql.substring(sql.indexOf("from")), params);
	    return "safetylist";
	}
	/**
	 * 保存检查信息
	 * @return  
	 */
	
	public String SaveSafetyInformation(){
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID=(String) session.get("organizationID");
		String hql="from CarInformation dt where dt.companyID= ? and dt.organizationID= ?";
		Object[] params={account.getCompanyID(),organizationID};
		CarInformation cars= (CarInformation) baseBeanService.getBeanByHqlAndParams(hql,params);
		Dtsafetyhealth safety = new Dtsafetyhealth();
		safety.setSafetyid(serverService.getServerID("Dtsafetyhealth"));
		safety.setCarid(cars.getCarID());
		safety.setAdddate(adddate);
		safety.setCompanyid(account.getCompanyID());
		safety.setOrganizationid(organizationID);
		safety.setStaffid(cars.getStaffID());
		safety.setTotleScore(totleScore);
		CLogBook cLogBook = logBookService.saveCLogBook(organizationID, parameter, account);
		List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
		baseBeansList.add(safety);
		baseBeansList.add(cLogBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList, null, null);	
		return "success";
	}
	
	
	/*
	 * 
	 * 
	 * 获得当前部门下所有车辆的车牌号
	 * */
	@SuppressWarnings("unchecked")
	public String getCarNumber(){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		Map<String, Object> session = ActionContext.getContext().getSession();
		String organizationID = (String) session.get("organizationID");
		Object[] params = { ((CAccount)session.get("account")).getCompanyID(),organizationID };
		String sql  = "select z.postname,s.staffname,c.carnum,c.carID from dt_Car_CarInformation c  " +
				"left join dt_hr_staff s on s.staffid = c.staffid  " +
				"left join dtCos d on d.staffid = c.staffid and d.status = '01' and d.cosstatus = '50' and d.companyid = c.companyid " +
				"left join dt_hr_deptpost z on z.deppostid = d.deppostid " +
				"where c.companyid = ?  and c.organizationid = ?";
		carNumList = baseBeanService.getListBeanBySqlAndParams(sql, params);
		request.setAttribute("carNumlist", carNumList);
		return "getCarNumberList";
	}
	/**
	 * 删除安全检查信息
	 * @return
	 */
	public String DeleteSafety(){
 			Map<String, Object> session = ActionContext.getContext().getSession();
 			String organizationID=(String) session.get("organizationID"); 
 			CAccount account = (CAccount) session.get("account");
			Object[] params = {account.getCompanyID(),safetyhealth.getSafetyid() };
			String hql2 = "from Dtsafetyhealth where companyid = ? and safetyid = ? ";
			Dtsafetyhealth q0 = (Dtsafetyhealth)baseBeanService.getBeanByHqlAndParams(hql2, params);
					CLogBook cLogBook = logBookService.saveCLogBook(organizationID, "删除车辆安全检查信息(检查编号:"+q0.getSafetyid()+")",account);
					String[] hql={"delete from Dtsafetyhealth where companyid = ? and safetyid=?"};
					List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
					baseBeansList.add(cLogBook);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(baseBeansList,hql,params);
			return "success";
	}
	//导出excel
	@SuppressWarnings("unchecked")
	public String showsafetyHealthExcel(){
		List<BaseBean> list;
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = session.get("organizationID").toString();
		String sql="select  d.adddate,z.organizationname,s.accountname,d.countrewards," +
				"d.countpenalty,d.totleScore  from dtsafetyhealth d " +
				"left join dtcorganization z on z.organizationid = d.organizationid " +
				"left join dtCAccount s on s.staffid = d.safetyid where d.companyID= ? and d.organizationID=?";
		Object[] params = {account.getCompanyID(),organizationID};
		list = baseBeanService.getListBeanBySqlAndParams(sql, params);
		excelStream = excelService.showExcel(Dtsafetyhealth.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, "导出车辆安全检查信息", account);
		baseBeanService.update(logBook);
		return "showexcel";	
	}
	
	//打印预览
	public String getprintList(){
		
		return "print";
	}
	
	public Dtsafetyhealth getSafetyhealth() {
		return safetyhealth;
	}
	public void setSafetyhealth(Dtsafetyhealth safetyhealth) {
		this.safetyhealth = safetyhealth;
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<BaseBean> getCarNumList() {
		return carNumList;
	}

	public void setCarNumList(List<BaseBean> carNumList) {
		this.carNumList = carNumList;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOrganizationname() {
		return organizationname;
	}

	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}
	public String getAdddate() {
		return adddate;
	}
	public void setAdddate(String adddate) {
		this.adddate = adddate;
	}
	public String getCountrewards() {
		return countrewards;
	}
	
	
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public void setCountrewards(String countrewards) {
		this.countrewards = countrewards;
	}
	public String getCountpenalty() {
		return countpenalty;
	}
	public void setCountpenalty(String countpenalty) {
		this.countpenalty = countpenalty;
	}
	public String getTotleScore() {
		return totleScore;
	}
	public void setTotleScore(String totleScore) {
		this.totleScore = totleScore;
	}
	public CarInformation getCarInformation() {
		return carInformation;
	}
	public void setCarInformation(CarInformation carInformation) {
		this.carInformation = carInformation;
	}
	public String getSafetycarID() {
		return safetycarID;
	}
	public void setSafetycarID(String safetycarID) {
		this.safetycarID = safetycarID;
	}
	public Dtcarassemblytable getCarassemblytable() {
		return carassemblytable;
	}
	public void setCarassemblytable(Dtcarassemblytable carassemblytable) {
		this.carassemblytable = carassemblytable;
	}
	public Map<String, Dtcarassemblytable> getDtcarassemblytablemap() {
		return dtcarassemblytablemap;
	}
	public void setDtcarassemblytablemap(
			Map<String, Dtcarassemblytable> dtcarassemblytablemap) {
		this.dtcarassemblytablemap = dtcarassemblytablemap;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	
	
}
