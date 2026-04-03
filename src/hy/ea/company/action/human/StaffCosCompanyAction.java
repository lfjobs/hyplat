package hy.ea.company.action.human;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.vo.CStaffCos;
import hy.ea.service.ShowExcelService;
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
 * 集团 --在职员工汇总
 * 
 * @author lwz
 * 
 */
@Controller
@Scope("prototype")
public class StaffCosCompanyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ShowExcelService excelService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private CStaffCos cosStaff ;
	private InputStream excelStream;
	

	
	/**
	 * 导出
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> parmsList = new ArrayList<Object>();
		String sql = "select z.companyname,s.staffCode,s.recordCode," +
				" case when da.categoryName is null then '暂未分配'"
				+ " else da.categoryName end" +
				",s.staffName,"+
				" o.organizationName, d.postName,s.usedNmae,s.sex,s.birthday,s.nationality,"+
				" s.nativePlace,s.nation,s.staffIdentityCard from dtcos c "+
				" left join dt_hr_deptpost d on c.depPostID = d.depPostID"+
				" left join dtcorganization o on c.organizationID = o.organizationID "+
				" left join dtcompany z on z.companyid = c.companyid "+
				" right join dt_hr_staff s on c.staffID = s.staffID"+
				" left join dtaudition da on c.staffID = da.staffID and da.companyID= ? and da.status = ?"+
				" where (z.companyid = ? or z.companyPID = ?)"+
				" and c.cosStatus = ? "+
				" and c.status = ? " +
				" and z.companystatus = ?";
		parmsList.add(account.getCompanyID());
		parmsList.add("22");
		parmsList.add(account.getCompanyID());
		parmsList.add(account.getCompanyID());
		parmsList.add("50");
		parmsList.add("01");
		parmsList.add("00");
		if (search != null && search.equals("search")) {
			CStaffCos cos = (CStaffCos) session.get("tablesearch");
			//人员姓名
			if (cos.getStaffName()!= null
					&& !cos.getStaffName().trim().equals("")) {
				sql += " and s.staffName like ? ";
				parmsList.add("%" + cos.getStaffName().trim() + "%");
			}
			//公司
			if (cos.getCompanyID()!= null
					&& !cos.getCompanyID().equals("")) {
				sql += " and c.companyid = ? ";
				parmsList.add(cos.getCompanyID());
			}
			//人员编号
			if (cos.getStaffCode()!= null
					&& !cos.getStaffCode().trim().equals("")) {
				sql += " and s.staffCode like ? ";
				parmsList.add("%" + cos.getStaffCode().trim() + "%");
			}
			//身份证
			if (cos.getStaffCode()!= null
					&& !cos.getStaffIdentityCard().trim().equals("")) {
				sql += " and s.staffIdentityCard like ? ";
				parmsList.add("%" + cos.getStaffIdentityCard().trim() + "%");
			}
		}
		sql+= " order by z.companyname,s.staffID ";
		
		List<BaseBean> list = baseBeanService.getListBeanBySqlAndParams(sql,parmsList.toArray());
		excelStream = excelService.showExcel(CStaffCos.columnHeadings1(),list);
		return "showexcel";
	}
	
	
	
	/**
	 * 查询
	 * @return
	 */
	public String toSearch() {
		ActionContext.getContext().getSession().put("tablesearch",
				cosStaff);
		return getStaffCosList();
	}
	
	/**
	 * 加载
	 * @return
	 */
	
	public String getStaffCosList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		List<Object> parmsList = new ArrayList<Object>();
		String sql = "select s.staffID,s.staffCode,s.recordCode,da.categoryName,s.staffName,"+
				" o.organizationName, d.postName,s.usedNmae,s.sex,s.birthday,s.nationality,"+
				" s.nativePlace,s.nation,s.staffIdentityCard, s.staffAddress,s.reference,"+
				" s.referenceCode,s.referenceOrganization,s.verifyTime,s.politicsStatus, "+
				" s.culturalDegree,s.marriage,s.health,s.bankNum,s.staffDesc,s.staffKey,"+
				" s.address,s.photo,z.companyname from dtcos c "+
				" left join dt_hr_deptpost d on c.depPostID = d.depPostID"+
				" left join dtcorganization o on c.organizationID = o.organizationID "+
				" left join dtcompany z on z.companyid = c.companyid "+
				" right join dt_hr_staff s on c.staffID = s.staffID"+
				" left join dtaudition da on c.staffID = da.staffID and da.companyID= ? and da.status = ?"+
				" where (z.companyid = ? or z.companyPID = ?)"+
				" and c.cosStatus = ? "+
				" and c.status = ? " +
				" and z.companystatus = ?";
		parmsList.add(account.getCompanyID());
		parmsList.add("22");
		parmsList.add(account.getCompanyID());
		parmsList.add(account.getCompanyID());
		parmsList.add("50");
		parmsList.add("01");
		parmsList.add("00");
		if (search != null && search.equals("search")) {
			CStaffCos cos = (CStaffCos) session.get("tablesearch");
			//人员姓名
			if (cos.getStaffName()!= null
					&& !cos.getStaffName().trim().equals("")) {
				sql += " and s.staffName like ? ";
				parmsList.add("%" + cos.getStaffName().trim() + "%");
			}
			//公司
			if (cos.getCompanyID()!= null
					&& !cos.getCompanyID().equals("")) {
				sql += " and c.companyid = ? ";
				parmsList.add(cos.getCompanyID());
			}
			//人员编号
			if (cos.getStaffCode()!= null
					&& !cos.getStaffCode().trim().equals("")) {
				sql += " and s.staffCode like ? ";
				parmsList.add("%" + cos.getStaffCode().trim() + "%");
			}
			//身份证
			if (cos.getStaffCode()!= null
					&& !cos.getStaffIdentityCard().trim().equals("")) {
				sql += " and s.staffIdentityCard like ? ";
				parmsList.add("%" + cos.getStaffIdentityCard().trim() + "%");
			}
		}
		sql+= " order by z.companyname,s.staffID ";
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1) "
						+ sql.substring(sql.indexOf("from")), parmsList.toArray());
		return "getStaffCosList";
	}
	
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
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

	public CStaffCos getCosStaff() {
		return cosStaff;
	}

	public void setCosStaff(CStaffCos cosStaff) {
		this.cosStaff = cosStaff;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	
}
