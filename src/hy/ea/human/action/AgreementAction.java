package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAgreement;
import hy.ea.bo.human.Track;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class AgreementAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private CCodeService codeService;	
	private PageForm pageForm;
	private int pageNumber;
	private StaffAgreement agreement;
	private List<BaseBean> agreementList;
	private String result;
	private String parameter;
	private List<BaseBean> beans;
	private Map<String,StaffAgreement>  agreementmap;
	private String search;
	private Staff staff;
	
	/**
	 * 
	 * @return
	 */
	public String toSearch(){
		ActionContext.getContext().getSession().put("staff",
				staff);
		return getListEXC();
	}
	@SuppressWarnings("unchecked")
	public String printExcel(){
		
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		
		beans = baseBeanService.getListBeanBySqlAndParams(sql, parms);
		return "printExcel";
	}
	
	/**
	 * 横向报表
	 * @return
	 */
	public String getListEXC(){
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), sql, "select count(max(1)) "
				+ sql.substring(sql.indexOf("from")), parms);
		
		return "getListEXC";
	}
	public List<Object> getList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> result = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		
		String sql ="select  max(s.staffid),max(s.staffcode),max(s.staffname),max(o.organizationName)," +
				" max(d.postName),max(da.categoryName)," +
				" max(s.birthday),max(s.staffIdentityCard),max(s.reference)," +
				" max(case when a.status ='00' then '有' else '无' end)," +
				" max(case when a.status ='01' then '有' else '无' end)," +
				" max(case when a.status ='02' then '有' else '无' end)," +
				" max(case when a.status ='03' then '有' else '无' end)," +
				" max(case when a.status ='04' then '有' else '无' end)," +
				" max(case when a.status ='05' then '有' else '无' end),max(pa.scale)" +
				" from dt_hr_staff s left join dtcos c on s.staffid = c.staffid  and c.cosStatus = '50' and c.status = '01'" +
				" left join dtcorganization o on c.organizationID = o.organizationID" +
				" left join dt_hr_deptpost d on c.depPostID = d.depPostID" +
				" left join dtaudition da on c.staffID = da.staffID " +
				" left join dt_hr_staff_agreement a on a.staffid = s.staffid " +
				" left join dtCsp p on p.staffid = s.staffid" +
				" left join dtpayscale pa on pa.payscaleid = p.payscaleid" +
				" where c.companyID = ?";
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			staff = (Staff) session.get("staff");
			if(staff != null){
				if (staff.getStaffName() != null && !"".equals(staff.getStaffName().trim())) {
					sql += " and s.staffname like ?";
					parms.add("%" + staff.getStaffName().trim() + "%");
				}
				if (staff.getStaffCode() != null && !"".equals(staff.getStaffCode().trim())) {
					sql += " and s.staffcode like ?";
					parms.add("%" + staff.getStaffCode().trim() + "%");
				}
				if (staff.getStaffIdentityCard() != null && !"".equals(staff.getStaffIdentityCard().trim())) {
					sql += " and s.staffIdentityCard like ?";
					parms.add("%" + staff.getStaffIdentityCard().trim() + "%");
				}
			}
		}
		sql += " group by s.staffid";
		result.add(sql);
		result.add(parms.toArray());
		return result;
	}
	
	
	/**
	 * @since
	 * @author 
	 * info:添加或修改合同
	 */
	public String saveAgreement() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		beans = new ArrayList<BaseBean>();
		if(null!=agreementmap){
			for(StaffAgreement ct:agreementmap.values()){
				if(null==ct.getAgreementID()||"".equals(ct.getAgreementID())){
					ct.setAgreementID(serverService.getServerID("agreement"));
					ct.setStaffID(agreement.getStaffID());
					ct.setCompanyID(account.getCompanyID());
					parameter = "添加银行帐号";
				}else{
					parameter = "修改银行帐号";
				}  
				String[] hql2={"from Staff where staffID=?"};
				Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2[0], new Object[]{ct.getStaffID()});
				parameter += "(人员名称:"+staff.getStaffName()+")";
				beans.add(ct);
			}
			
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		} 
		return "succ";
	}
	/**
	 * 删除银行帐号 
	 * @return
	 */
	public String delAgreement() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "delete StaffAgreement where agreementID = ?";
		Object[] params = {  agreement.getAgreementID() };
		beans = new ArrayList<BaseBean>();
		String hql2="from Staff where staffID=?";
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{agreement.getStaffID()});
		CLogBook logBook = logBookService.saveCLogBook(null,"删除银行帐号(人员名称："+ staff.getStaffName()+")", account);
		beans.add(logBook);
		String filePath = agreement.getAttachUrl() ;
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		File file = new File(path+filePath);
		file.delete();
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		return "succ";
	}

	
	/**
	 * 
	 *  根据公司和个人查询合同
	 *  
	 * @return
	 */
	public String getListAgreement() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		agreementList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffAgreement a  where a.companyID = ? and a.staffID = ?", new Object[]{account.getCompanyID(),agreement.getStaffID()});
		
		return "list";
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	


	public ShowExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(ShowExcelService excelService) {
		this.excelService = excelService;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public CLogBookService getLogBookService() {
		return logBookService;
	}

	public void setLogBookService(CLogBookService logBookService) {
		this.logBookService = logBookService;
	}

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}

	public Map<String, StaffAgreement> getAgreementmap() {
		return agreementmap;
	}

	public void setAgreementmap(Map<String, StaffAgreement> agreementmap) {
		this.agreementmap = agreementmap;
	}

	public StaffAgreement getAgreement() {
		return agreement;
	}

	public void setAgreement(StaffAgreement agreement) {
		this.agreement = agreement;
	}

	public List<BaseBean> getAgreementList() {
		return agreementList;
	}

	public void setAgreementList(List<BaseBean> agreementList) {
		this.agreementList = agreementList;
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
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

}