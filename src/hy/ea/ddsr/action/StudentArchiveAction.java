package hy.ea.ddsr.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.driving.DtDrivingAllInformation;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.bo.driving.Studentarchives;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.human.StaffContact;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 
 * 教务学员归档
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class StudentArchiveAction {
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	private int pageNumber;
	private List<BaseBean> beans;
	private PageForm pageForm;
	
	private String result;
	
	private String search;
	private Staff staff;
	private Studentarchives stua;	//档案
	private DtDrivingPrincipal dpp ; //报名详情
	private List dar ; //驾校-预约记录
	private DtDrivingAllInformation dainf;//学员信息
	private StaffAddress sadd;//地址
	private StaffContact scon; //邮箱
	private List dpt;//考试记录
	
	public String save(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff s where s.staffID = ?", new Object[]{account.getStaffID()});
		beans = new ArrayList<BaseBean>();
		
		if(!"".equals(stua.getArchivesviews())){
			if("".equals(stua.getArchivesname())){
				stua.setArchivesname(staff.getStaffName());
			}
		}
		if(!"".equals(stua.getComments())){
			if("".equals(stua.getCheckname())){
				stua.setCheckname(staff.getStaffName());
			}
		}
		beans.add(stua);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}
	
	
	public String toSTUarvhive(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		stua = (Studentarchives)baseBeanService.getBeanByHqlAndParams(" from Studentarchives s where s.studentarchivesid = ? and s.companyid = ?", new Object[]{stua.getStudentarchivesid(),account.getCompanyID()});
		
		staff = (Staff)baseBeanService.getBeanByHqlAndParams("from Staff s where s.staffID = ?", new Object[]{stua.getStaffid()});
		dpp = (DtDrivingPrincipal)baseBeanService.getBeanByHqlAndParams("from DtDrivingPrincipal s where s.drivingprincipalid = ? and s.companyid = ?", new Object[]{stua.getDrivingprincipalid(),account.getCompanyID()});
		String sql = "select  max(case p.docstatus when '01' then '科一' when '02' then '科二' when '03' then '科三' when '04' then '科四' else '' end)," +
				" min(p.appointmentdate),max(p.appointmentdate),sum(p.haveschooltime),wm_concat(distinct(p.coach)),wm_concat(d.reason),max(d.istrues)" +
				" from DT_DRIVING_APPOINTMENT_RECORD p left join " +
				" dt_driving_principal d on p.drivingprincipalid = d.drivingprincipalid where " +
				" p.companyid = ? and p.drivingprincipalid = ?" +
				" group by p.docstatus order by p.docstatus";
		dar = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{account.getCompanyID(),stua.getDrivingprincipalid()});
		beans = new ArrayList<BaseBean>();
		beans = baseBeanService.getListBeanByHqlAndParams("from StaffContact d where d.staffID = ? and d.contactType = ?",new Object[]{stua.getStaffid(),"scode20100426c8rdqacjae0000000002"});
		scon = new StaffContact();
		if(beans != null){
			scon = (StaffContact)beans.get(0);
		}
		beans = new ArrayList<BaseBean>();
		beans = baseBeanService.getListBeanByHqlAndParams("from DtDrivingAllInformation d where d.companyID = ? and d.staffID = ? and d.organizationName is not null",new Object[]{ account.getCompanyID(),stua.getStaffid()});
		dainf = new DtDrivingAllInformation();
		if(beans != null){
			dainf = (DtDrivingAllInformation)beans.get(0);
		}
		beans = new ArrayList<BaseBean>();
		beans = baseBeanService.getListBeanByHqlAndParams("from StaffAddress d where  d.staffID = ? ",new Object[]{stua.getStaffid()});
		sadd = new StaffAddress();
		if(beans != null){
			sadd = (StaffAddress)beans.get(0);
		}
		String sql1 = "select case when t.docstatus = '01' then '科一' when t.docstatus='02'" +
				" then '科二'when t.docstatus='03' then '科三'when t.docstatus='04' then '科四' else '' end," +
				" case when t.studentstatus= '07' then '合格' else '无' end" +
				" from dt_driving_principal_type t where t.drivingprincipalid = ? order by t.docstatus asc";
		dpt = baseBeanService.getListBeanBySqlAndParams(sql1, new Object[]{stua.getDrivingprincipalid()});
		int j = dpt.size();
		for(int i=0;i<4;i++){
			if(i+1>j){
				List str = new ArrayList();
				if(i==0){
					str.add(0,"科一");
					str.add(1,"无");
				}
				if(i==1){
					str.add(0,"科二");
					str.add(1,"无");
				}
				if(i==2){
					str.add(0,"科三");
					str.add(1,"无");
				}
				if(i==3){
					str.add(0,"科四");
					str.add(1,"无");
				}
				dpt.add(str.toArray());
			}
		}
		return "toSTUarvhive";
	}
	
	public String toSearch(){
		ActionContext.getContext().getSession().put("tablesearch",
				dpp);
		return getListSTU();
	}
	
	public String getListSTU(){
		List<Object> list = getList();
		String sql = (String) list.get(0);
		Object[] parms = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 20 : pageNumber), sql, "select count(1) "
				+ sql.substring(sql.indexOf("from")), parms);
		
		return "getListSTU";
	}
	
	public List<Object> getList(){
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> resultl = new ArrayList<Object>();
		List<Object> parms = new ArrayList<Object>();
		
		String sql = "select a.studentarchivesid,s.staffcode,d.registrationdate," +
				" d.studentname,d.studentcard,s.reference,registrationcarname," +
				" a.cardcopy,a.photo,a.medical,a.health,a.records,a.stucard," +
				" a.temporary,a.seach,a.drivercopy,a.driverfin,other" +
				" from dt_studentarchives a left join dt_hr_staff s on a.staffid = s.staffid" +
				" left join dt_driving_principal d on a.staffid = d.studentid where a.companyid = ?";
		parms.add(account.getCompanyID());
		if (search != null && search.equals("search")) {
			dpp = (DtDrivingPrincipal) session.get("tablesearch");
			if(dpp != null){
				if (dpp.getStudentname() != null && !"".equals(dpp.getStudentname().trim())) {
					sql += " and d.studentname like ?";
					parms.add("%" + dpp.getStudentname().trim() + "%");
				}
				if (dpp.getStudentcard() != null && !"".equals(dpp.getStudentcard().trim())) {
					sql += " and d.studentcard like ?";
					parms.add("%" + dpp.getStudentcard().trim() + "%");
				}
				if (dpp.getRegistrationcarname() != null && !"".equals(dpp.getRegistrationcarname().trim())) {
					sql += " and d.registrationcarname = ?";
					parms.add(dpp.getRegistrationcarname().toLowerCase().trim());
				}
			}
		}
		
		resultl.add(sql);
		resultl.add(parms.toArray());
		return resultl;
	}
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public Studentarchives getStua() {
		return stua;
	}

	public void setStua(Studentarchives stua) {
		this.stua = stua;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public DtDrivingPrincipal getDpp() {
		return dpp;
	}

	public void setDpp(DtDrivingPrincipal dpp) {
		this.dpp = dpp;
	}

	public List getDar() {
		return dar;
	}

	public void setDar(List dar) {
		this.dar = dar;
	}

	public DtDrivingAllInformation getDainf() {
		return dainf;
	}

	public void setDainf(DtDrivingAllInformation dainf) {
		this.dainf = dainf;
	}


	public StaffAddress getSadd() {
		return sadd;
	}

	public void setSadd(StaffAddress sadd) {
		this.sadd = sadd;
	}

	public List getDpt() {
		return dpt;
	}

	public void setDpt(List dpt) {
		this.dpt = dpt;
	}


	public StaffContact getScon() {
		return scon;
	}


	public void setScon(StaffContact scon) {
		this.scon = scon;
	}

	
	
}
