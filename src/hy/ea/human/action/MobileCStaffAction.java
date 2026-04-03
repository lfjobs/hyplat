package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.human.service.COrganizationService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.bo.SDistrict;
import hy.plat.service.BaseBeanService;
import hy.plat.service.SDistrictService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.telrec.tool.JsonDateValueProcessor;

@Controller
@Scope("prototype")
public class MobileCStaffAction {
	@Resource
	private ShowExcelService excelService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private COrganizationService organizationService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;
	private Staff cstaff;
	private Staff searchCStaff;// 保存人员搜索信息
	private String search;// 判断是否搜索人员
	private COrganization organization;
	private PageForm pageForm;
	private String result;
	private String organizationID;
	private Company company;
	private String sub;
	private int pageNumber;
	@Resource
	private CCodeService codeService;
	@Resource
	private SDistrictService regionService;
	private List<SDistrict> distinctlist;
	private String districtPID;
	// 接收photo文件
	private File photo;
	private String photoContentType;
	private List<SDistrict> distinctlistSaved;
	private SDistrict district;
	public InputStream excelStream;
	private COS cos;
	private String parameter;
	private ContactRelation conRelation;

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public SDistrict getDistrict() {
		return district;
	}

	public void setDistrict(SDistrict district) {
		this.district = district;
	}

	public List<SDistrict> getDistinctlistSaved() {
		return distinctlistSaved;
	}

	public void setDistinctlistSaved(List<SDistrict> distinctlistSaved) {
		this.distinctlistSaved = distinctlistSaved;
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

	/**
	 * 判断社会人员是否已在本公司
	 * 
	 * @return
	 */
	public String saveCOS() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = "select count(*)  from Audition where companyID = ?  and status not like '9_' and staffID = ?";
		Object[] params = { account.getCompanyID(), cos.getStaffID() };
		int c = baseBeanService.getConutByByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", c);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 判断社会人员是否已在往来个人
	 * 
	 * @return
	 */
	public String isContactUser() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "select count(*) from ContactRelation where staffID = ? and companyID = ? and relation = ?";
		Object[] params = { conRelation.getStaffID(), account.getCompanyID(),
				conRelation.getRelation() };
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		if (count <= 0) {
			conRelation.setCompanyID(account.getCompanyID());

			if (null == conRelation.getRelationID()
					|| "".equals(conRelation.getRelationID())) {
				conRelation.setRelationID(serverService
						.getServerID("contactrelation"));
			}
			baseBeanService.update(conRelation);
		}
		map.put("c", count);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 判断身份证唯一
	 * 
	 * @return
	 */
	public String IsLawfulIdentityCard() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Object[] params = { result };
		String hql = " from Staff where staffStatus = '00' and staffIdentityCard = ?";
		cstaff = (Staff) baseBeanService.getBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("SynIDCarder", "0");
		map.put("IDCarder", "0");
		if (null != cstaff) {
			map.put("SynIDCarder", cstaff);
			map.put("IDCarder", "1");
		}
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";

	}

	/**
	 * 保存District，并将保存的District返回
	 * 
	 * @return
	 */
	public String saveDistrict() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String districtPID = district.getDistrictPID();
		String districtName = district.getDistrictName();
		List<BaseBean> bBean = new ArrayList<BaseBean>();
		district = regionService.getSDistrictByPIDAndName(districtPID,
				districtName);
		if (null == district) {
			district = new SDistrict();
			district.setDistrictID(serverService.getServerID("sdistrict"));
			district.setDistrictPID(districtPID);
			district.setDistrictName(districtName);
			district.setDistrictStatus("98");
			// baseBeanService.save(district);
			bBean.add(district);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(bBean, null,
					null);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sdistrict", district);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	// 用JSON取得具体人员详细地址

	public String getPDetailsDistricts() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		distinctlistSaved = regionService.getDistrictListByID(districtPID);
		Collections.reverse(distinctlistSaved);
		List<Object> list = new ArrayList<Object>();
		for (SDistrict distinct : distinctlistSaved) {
			List<SDistrict> distinctlist = regionService
					.getDistrictListNOselfByPID(distinct.getDistrictPID(),
							distinct.getDistrictID());
			list.add(distinctlist);
			districtPID = distinct.getDistrictID();
		}
		distinctlist = regionService.getDistrictListByPID(districtPID);
		list.add(distinctlist);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("distinctlistSaved", distinctlistSaved);
		map.put("list", list);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	// 用JSON取得地域树
	public String getCDistricts() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		distinctlist = regionService.getDistrictListByPID(districtPID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("distinctlist", distinctlist);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 为info页面准备下拉框中的内容
	 * 
	 * @return
	 */
	public String getSelectLists() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<CCode> codeSexList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100331qqrexctzfc0000000035");
		List<CCode> codeRelationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<CCode> codeNationalityList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode201004233ern4m24yx0000000014");
		List<CCode> codeNationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100331mk6yn5b5f60000000006");
		List<CCode> codeNativePlaceList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode2010053143wpua87db0000000008");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeSexList", codeSexList);
		map.put("codeNationalityList", codeNationalityList);
		map.put("codeNationList", codeNationList);
		map.put("codeNativePlaceList", codeNativePlaceList);
		map.put("codeRelationList", codeRelationList);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	/**
	 * 查询机构树,根据机构ID和对应单位ID查询
	 */
	public String getOrganizationList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<COrganization> organizationList = organizationService
				.getOrganizationList(organizationID, account.getCompanyID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("organizationList", organizationList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 保存模糊查询
	 * 
	 * @return
	 */
	public String toSearchCStaff() {
		ActionContext.getContext().getSession()
				.put("tablesearch", searchCStaff);
		return getListCStaffByCompanyID();
	}

	/**
	 * 社会人力资源模糊查询
	 * 
	 * @return
	 */
	public String getListCStaffByCompanyID() {
		if (search != null && search.equals("search")) {
			searchCStaff = (Staff) ActionContext.getContext().getSession().get(
					"tablesearch");
			String hql = "from Staff where staffName like  '%"
					+ searchCStaff.getStaffName() + "%'";
			String hql1 = " and staffIdentityCard like  '%"
					+ searchCStaff.getStaffIdentityCard() + "%'";
			String hql2 = " and staffAddress like    '%"
					+ searchCStaff.getStaffAddress() + "%'";
			if (searchCStaff.getStaffIdentityCard() != null
					&& !"".equals(searchCStaff.getStaffIdentityCard())) {
				hql = hql + hql1;
			}
			if (searchCStaff.getStaffAddress() != null
					&& !"".equals(searchCStaff.getStaffAddress())) {
				hql = hql + hql2;
			}
			hql = hql + "  and staffStatus = '00' order by staffID desc";
			pageForm = baseBeanService
					.getPageForm((pageNumber), (1), hql, null);
			HttpServletResponse response = ServletActionContext.getResponse();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(java.util.Date.class,
					new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
			String outString = jsonArray.toString();
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(outString);
				// System.out.println(outString);
				response.flushBuffer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		// String companyid = account.getCompanyID();
		String hql = " from Staff where  staffStatus = '00' order by staffID desc ";
		Object[] params = null;
		pageForm = baseBeanService.getPageForm((pageNumber), (1), hql, params);
		HttpServletResponse response = ServletActionContext.getResponse();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		JSONObject jsonArray = JSONObject.fromObject(pageForm, jsonConfig);
		String outString = jsonArray.toString();
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(outString);
			// System.out.println(outString);
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 导出Staff
	 * 
	 * @return
	 */
	public String showStaffExcel() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		List<BaseBean> stafflist;
		CAccount account = (CAccount) session.get("account");
		if (search != null && search.equals("search")) {

			searchCStaff = (Staff) ActionContext.getContext().getSession().get(
					"searchCStaff");
			String hql = "from Staff where  staffCode like ?  and staffName like  ? and staffIdentityCard like ? and staffStatus = '00' order by staffID desc ";
			Object[] params = { "%" + searchCStaff.getStaffCode() + "%",
					"%" + searchCStaff.getStaffName() + "%",
					"%" + searchCStaff.getStaffIdentityCard() + "%" };
			stafflist = baseBeanService.getListBeanByHqlAndParams(hql, params);
			excelStream = excelService.showExcel(Staff.columnHeadings(),
					stafflist);
			logBookService.saveCLogBook(null, "导出人员列表", account);
			return "showexcel";
		}
		String hql = " from Staff where companyID = ? and staffStatus = '00' order by staffID desc  ";
		Object[] params = { account.getCompanyID() };
		stafflist = baseBeanService.getListBeanByHqlAndParams(hql, params);
		excelStream = excelService.showExcel(Staff.columnHeadings(), stafflist);
		return "showexcel";
	}

	/**
	 * 保存或修改Staff
	 * 
	 * @return
	 */
	public String saveCStaff() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String hql = "select count(*) from Staff where  staffIdentityCard = ? ";
		Object[] params = { cstaff.getStaffIdentityCard() };

		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		if (cstaff.getStaffID() == null || "".equals(cstaff.getStaffID())) {
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			cstaff.setStaffCode("NO" + pcount);
			cstaff.setRecordCode("NO" + pcount);
			cstaff.setStaffID(serverService.getServerID("cstaff"));
			parameter = "添加员工（人员姓名：:" + cstaff.getStaffName() + ")";
		} else {
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { cstaff.getStaffID() });
			parameter = "修改员工(人员名称:" + staff.getStaffName() + ")";
			if (staff.getStaffIdentityCard() != null
					&& !staff.getStaffIdentityCard().equals(""))
				if (staff.getStaffIdentityCard().equals(
						cstaff.getStaffIdentityCard())) {
					count = 0;
				}
		}
		if (count > 0) {
			return getListCStaffByCompanyID();
		}
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoContentType, photo, account
							.getCompanyID(), "/human/personPhotos/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			if (cstaff.getPhoto() != null && !"".equals(cstaff.getPhoto())) {
				ArrayList<String> paths = new ArrayList<String>();
				paths.add(path + cstaff.getPhoto());
				fileService.deletePhotos(paths);
			}
			cstaff.setPhoto(photoPath);
		}
		cstaff.setStaffStatus("00");
		baseBeanService.update(cstaff);
		logBookService.saveCLogBook(null, parameter, account);
		return "success";
	}

	/**
	 * 删除Staff把状态staffStatus改为98
	 * 
	 * @return
	 */
	public String delCStaff() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String obj = (String) session.get("session_value");
		if (sub != null && sub.equals(obj)) {
			String hql = "update Staff set staffStatus = '98' where  staffID = ? ";
			Object[] params = { cstaff.getStaffID() };
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					params);
			parameter = "删除员工(人员名称:" + staff.getStaffName() + ")";
			// baseBeanService.executeHqlByParams(hql, params);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hql }, params);
			logBookService.saveCLogBook(null, parameter, account);
		}
		return "success";
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public COrganization getOrganization() {
		return organization;
	}

	public void setOrganization(COrganization organization) {
		this.organization = organization;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}

	public Staff getSearchCStaff() {
		return searchCStaff;
	}

	public void setSearchCStaff(Staff searchCStaff) {
		this.searchCStaff = searchCStaff;
	}

	public List<SDistrict> getDistinctlist() {
		return distinctlist;
	}

	public void setDistinctlist(List<SDistrict> distinctlist) {
		this.distinctlist = distinctlist;
	}

	public String getDistrictPID() {
		return districtPID;
	}

	public void setDistrictPID(String districtPID) {
		this.districtPID = districtPID;
	}

	public ContactRelation getConRelation() {
		return conRelation;
	}

	public void setConRelation(ContactRelation conRelation) {
		this.conRelation = conRelation;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public COS getCos() {
		return cos;
	}

	public void setCos(COS cos) {
		this.cos = cos;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

}
