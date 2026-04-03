package hy.ea.company.action.office;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
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
 * 车辆信息集团汇总
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
public class CarInforGroupAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private CarInformation carInformation;
	private PageForm pageForm;
	private int pageNumber;
	private String search;
	private InputStream excelStream;
	
	/**
	 * 根据条件查询车辆信息集团汇总
	 */
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", carInformation);
		return getCarInformationList();
	}

	/**
	 * 车辆信息集团汇总列表
	 */
	public String getCarInformationList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		List<Object> list = getList(session, account);
		String sql = list.get(0).toString();
		Object[] params = (Object[]) list.get(1);
		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, "select count(1)"
						+ sql.substring(sql.indexOf("from")), params);
		return "carInformationlist";	
	}
	
	/**
	 * 车辆信息集团汇总列表、查询、导出调用
	 * @param session
	 * @param account
	 * @return
	 */
	private List<Object> getList(Map<String, Object> session,
			CAccount account) {
		List<Object> parms = new ArrayList<Object>();
		List<Object> results = new ArrayList<Object>();
		String sql = "select c.carID,co.companyName,cor.organizationName,c.staffName,"
				+ " c.carType,c.carNum,c.engineNum,c.carFrameNum,c.registrationDate,"
				+ " case when c.state3 ='00' then '未使用'"
				+ " when c.state3='01' then '已使用'"
				+ " else '下线' end,"
				+ " case when c.state1 ='00' then '加盟车'"
				+ " when c.state3='01' then '本校车'"
				+ " else '' end"
				+ " from dt_Car_CarInformation c"
				+ " left join dtcompany co on co.companyID = c.companyID"
				+ " left join dtCOrganization cor on cor.organizationID = c.organizationID"
				+ " where exists ( select com.companyID from dtCompany com"
				+ " where com.companyID = c.companyID and (com.companyID = ? or com.companyPID = ?))"
				+ " and c.state3 between '00' and '01'";
		parms.add(account.getCompanyID());
		parms.add(account.getCompanyID());
		
		if (search != null && search.equals("search")) {
			carInformation = (CarInformation) session.get("tablesearch");
			if(carInformation.getCarNum()!=null && !"".equals(carInformation.getCarNum().trim())){
				sql += " and c.carNum like ?";
				parms.add("%" + carInformation.getCarNum().trim()+"%");
			}
			if(carInformation.getCarType()!=null && !"".equals(carInformation.getCarType().trim())){
				sql += " and c.carType like ?";
				parms.add("%" + carInformation.getCarType().trim()+"%");
			}
			if(carInformation.getCompanyID()!=null && !"".equals(carInformation.getCompanyID())){
				sql += " and c.companyID = ?";
				parms.add(carInformation.getCompanyID());
			}
			if(carInformation.getStaffName()!=null && !"".equals(carInformation.getStaffName().trim())){
				sql += " and c.staffName like ?";
				parms.add("%" + carInformation.getStaffName().trim()+"%");
			}
			if(carInformation.getState1()!=null && !"".equals(carInformation.getState1())){
				sql += " and c.state1 = ?";
				parms.add(carInformation.getState1());
			}
			if(carInformation.getState3()!=null && !"".equals(carInformation.getState3())){
				sql += " and c.state3 = ?";
				parms.add(carInformation.getState3());
			}
		}
		sql += " order by c.organizationID desc";
		results.add(sql);
		results.add(parms.toArray());
		return results;
	}
	
	/**
	 * 车辆信息集团汇总导出
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public String showExcel() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (!account.getStaffID().substring(0, 6).equals("cstaff")) {
			return "syserror";
		}
		String organizationID = (String) session.get("organizationID");
		List<Object> list = getList(session, account);
		String sql = (String) list.get(0);
		String sql1 = "select "
				+ sql.substring(sql.indexOf(",") + 1);
		Object[] parms = (Object[]) list.get(1);
		excelStream = excelService.showExcel(carInformation.columnHeadings(),
				baseBeanService.getListBeanBySqlAndParams(sql1, parms));
		CLogBook logBook = logBookService.saveCLogBook(organizationID,
				"导出车辆基本管理集团汇总", account);
		baseBeanService.update(logBook);
		return "showexcel";
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
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
}