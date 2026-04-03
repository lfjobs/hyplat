package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffInsurance;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 社会保险
 * 
 * @author zgzg
 * 
 */
@Controller
@Scope("prototype")
public class InsuranceAction {
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService;
	private List<CCode> codeInsuranceList;
	private StaffInsurance insurance;
	private List<BaseBean> insuranceList;
	
	private String parameter;
	private Map<String, StaffInsurance> insurancemap;// 添加或修改的时候 在前台要拿到值
														// 所以就把值放到Insurance里面
														// 传到后台 以便调用
	private List<BaseBean> beans;

	/**
	 * 添加或修改保险 参数：addressmap ，pageNumber（分页页数） 返回：getListAddress()
	 */
	public String saveInsurance() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		insurance = new StaffInsurance();
		beans = new ArrayList<BaseBean>();
		if (insurancemap != null) {
			for (StaffInsurance insurances : insurancemap.values()) {
				this.insurance.setStaffID(insurances.getStaffID());
				if (insurances.getPhotos() != null) {
					String path = ServletActionContext.getRequest()
							.getSession().getServletContext().getRealPath("/");
					String photoPath = fileService.savePhoto(path, insurances
							.getPhotosFileName(), insurances.getPhotos(),
							account.getCompanyID(), "/human/insurance/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
					insurances.setPhoto(photoPath);
				}
				if (null == insurances.getInsuranceID()
						|| "".equals(insurances.getInsuranceID())) {
					insurances.setInsuranceID(serverService
							.getServerID("insurance"));
					parameter = "添加保险情况";
				} else {
					parameter = "修改保险情况";

				}
				String hql2 = "from Staff where staffID=?";
				Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(
						hql2, new Object[] { insurances.getStaffID() });
				parameter += "(人员名称:" + staff.getStaffName() + ")";
				insurances.setCompanyID(account.getCompanyID());
				beans.add(insurances);
			}
			CLogBook logBook = logBookService.saveCLogBook(null, parameter,
					account);
			beans.add(logBook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);

		}
		return "success";
	}

	/**
	 * 删除某条保险信息 参数:getStaffID(),getInsuranceID()
	 * 
	 * @return
	 */
	public String delInsurance() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans = new ArrayList<BaseBean>();
		String hql2 = "from Staff where staffID=?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
				new Object[] { insurance.getStaffID() });
		CLogBook logBook = logBookService.saveCLogBook(null, "删除保险情况(人员名称："
				+ staff.getStaffName() + ")", account);
		beans.add(logBook);
		String hql = "delete StaffInsurance where staffID= ? and insuranceID = ?";
		Object[] params = { insurance.getStaffID(), insurance.getInsuranceID() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
				new String[] { hql }, params);
		return "success";
	}

	/**
	 * 查看保险信息列表 参数：insurance.getStaffID()｛人员ID｝，pageNumber（分页页数） 返回：pageForm
	 * 
	 * @return
	 */
	public String getListInsurance() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { insurance.getStaffID() };
		insuranceList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffInsurance where staffID = ? order by insuranceID desc", params);
		
		codeInsuranceList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100423qr9eg4m2nx0000000005");
		return "list";
	}

	public String showExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		Object[] params = { insurance.getStaffID() };
		String hql = " from StaffInsurance where staffID = ? order by insuranceID desc";
		list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(StaffInsurance.columnHeadings(),
				list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出保险情况", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}

	public List<CCode> getCodeInsuranceList() {
		return codeInsuranceList;
	}

	public void setCodeInsuranceList(List<CCode> codeInsuranceList) {
		this.codeInsuranceList = codeInsuranceList;
	}

	public Map<String, StaffInsurance> getInsurancemap() {
		return insurancemap;
	}

	public void setInsurancemap(Map<String, StaffInsurance> insurancemap) {
		this.insurancemap = insurancemap;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public List<BaseBean> getInsuranceList() {
		return insuranceList;
	}

	public void setInsuranceList(List<BaseBean> insuranceList) {
		this.insuranceList = insuranceList;
	}

	public StaffInsurance getInsurance() {
		return insurance;
	}

	public void setInsurance(StaffInsurance insurance) {
		this.insurance = insurance;
	}
}