package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class CStaffAction extends ActionSupport{
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
	private String photoFileName;
	private String photoContentType;
	private List<SDistrict> distinctlistSaved;
	private List<CCode> connectionlist;
	private List<CCode> comconnectlist;
	private SDistrict district;
	public InputStream excelStream;
	private COS cos;
	private String parameter;
	private ContactRelation conRelation;
	private List<BaseBean> beans;
	private List<CCode> typelist;
	private List<CCode> provincelist;
	private List<SDistrict> northeast;
	private List<SDistrict> northChina;
	private List<SDistrict> eastChina;
	private List<SDistrict> southernChina;
	private List<SDistrict> southwest;
	private List<SDistrict> northwest;
	private String parameterStaffIDs;
	private String showType;
	private List<BaseBean> distinctlist1;
	/**
	 * 
	 */
	private String type;
	/** 
	 * aa 显示 招聘 功能
	 * 没有值显示 个人需求客户登记表 功能
	 */
	private String aa; // 判断是社会人力页面还是收集个人办页面
	private ContactCompany contactCompany;
	private String produce;//区分营销和营销生产页面
	/**
	 * 判断社会人员是否已在本公司，如果不存在 办理入职
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
		String hql = "select count(*) from Audition where companyID = ?  and status not like '9_' and staffID = ?";
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
		if (type != null && !type.equals("")) {
			map.put("c", count);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			type = "";
			return "success";
		}
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
	 * 
	 * 设置多个人员的往来关系
	 */	
	public String updateContactUsers() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		if(parameterStaffIDs != null && !"".equals(parameterStaffIDs)){
			String[] parameterStaffIDsArray = parameterStaffIDs.substring(0, parameterStaffIDs.lastIndexOf("-")).split("-");
			for (int i = 0; i < parameterStaffIDsArray.length; i++) {
				String hql = "select count(*) from ContactRelation where staffID = ? and companyID = ? and relation = ?";
				Object[] params = { parameterStaffIDsArray[i], account.getCompanyID(),
						"学员" };
				int count = baseBeanService.getConutByByHqlAndParams(hql, params);
				if(count == 0){
					conRelation=new ContactRelation();
					conRelation.setCompanyID(account.getCompanyID());
					conRelation.setRelationID(serverService.getServerID("contactrelation"));
					conRelation.setRelation("学员");
					conRelation.setStaffID( parameterStaffIDsArray[i]);
					beans.add(conRelation);
				}
			}
		}
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
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
		String groupCompanySn = ActionContext.getContext().getSession()
				.get("groupCompanySn").toString();
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null || groupCompanySn == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from Staff where staffStatus = ? and staffIdentityCard = ? and groupCompanySn=?";
		Object[] params = { "00", result, groupCompanySn };
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
	 * 判断芯片号唯一
	 * 
	 * @return
	 */
	public String IsLawfulChip() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String hql = " from Staff where chipid = ?";
		Object[] params = {result};
		cstaff = (Staff) baseBeanService.getBeanByHqlAndParams(hql, params);
		Map<String, Object> map = new HashMap<String, Object>();
		if(cstaff==null){
			map.put("result","0");
		}else{
			map.put("result", "1");
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
		if (account == null && !"wechat".equals(type)) {
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
		if (account == null && !"wechat".equals(type)) {
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
		if (account == null && !"wechat".equals(type)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(showType!=null && "weifenjin".equals(showType)){
			String hql="from SDistrict where districtPID = ? or districtPID = ? or districtPID = ? or districtPID = ? " +
			" or districtPID = ? or districtPID = ?  order by districtName ";
			distinctlist1=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"sdistrict20140829779hcj26db0000000012","sdistrict20140829779hcj26db0000000013",
			"sdistrict20140829779hcj26db0000000014","sdistrict20140829779hcj26db0000000015","sdistrict20140829779hcj26db0000000016","sdistrict20140829779hcj26db0000000011"});
			map.put("distinctlist", distinctlist1);
			
		}else{
			distinctlist = regionService.getDistrictListByPID(districtPID);
			map.put("distinctlist", distinctlist);
		}
		
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
		List<CCode> codeSexList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20100331qqrexctzfc0000000035");
		List<CCode> codeRelationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
		List<CCode> codeNationalityList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode201004233ern4m24yx0000000014");
		List<CCode> codeNationList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode20100331mk6yn5b5f60000000006");
		List<CCode> codeNativePlaceList = codeService.getCCodeListByPID(
				account.getCompanyID(), "scode2010053143wpua87db0000000008");
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
	 * 进入带有菜单数的市场调查管理页面
	 * @return
	 */
	public String getFileMarketSurvey() {

		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			typelist = codeService.getCCodeListByPID(account.getCompanyID(),
					"scode20110106hfjes5ucxp0000000003");
			provincelist=codeService.getCCodeListByPID(account.getCompanyID(),
			"scode2010053143wpua87db0000000008");	
			
			northeast = regionService.getDistrictListByPID("sdistrict20140829779hcj26db0000000011");
			northChina = regionService.getDistrictListByPID("sdistrict20140829779hcj26db0000000012");
			eastChina = regionService.getDistrictListByPID("sdistrict20140829779hcj26db0000000014");
			southernChina = regionService.getDistrictListByPID("sdistrict20140829779hcj26db0000000015");
			southwest = regionService.getDistrictListByPID("sdistrict20140829779hcj26db0000000016");
			northwest = regionService.getDistrictListByPID("sdistrict20140829779hcj26db0000000013");
			connectionlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110106hfjes5ucxp0000000017");
			comconnectlist = codeService.getCCodeListByPID(account.getCompanyID(), "scode20110224xpd2t2jvda0000000002");
			
			if(null !=produce && produce.equals("Company") ){
			return "cFileList";
		}else if(null !=produce && produce.equals("group")){
			return "gFileList";
		}else{	
			return "fileList";
		} 
		
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getListCStaffByCompanyID() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String groupCompanySn = ActionContext.getContext().getSession()
				.get("groupCompanySn").toString();
		if (groupCompanySn == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List prams = new ArrayList();
		String hql = " from Staff s where s.groupCompanySn = ? ";
		prams.add(groupCompanySn);
		
		if(null != cstaff){
			if(!cstaff.getStatus().equals("99")){
				hql += " and s.status = ?";
				prams.add(cstaff.getStatus());
			} 
		}
		if (search != null && search.equals("search")) {
			searchCStaff = (Staff) ActionContext.getContext().getSession()
					.get("tablesearch");
			if (searchCStaff.getStaffName() != null
					&& !"".equals(searchCStaff.getStaffName().trim())) {
				hql += " and s.staffName like ?";
				prams.add("%" + searchCStaff.getStaffName().trim() + "%");
			}
			if (searchCStaff.getStaffIdentityCard() != null
					&& !"".equals(searchCStaff.getStaffIdentityCard().trim())) {
				hql += " and s.staffIdentityCard like ?";
				prams.add("%" + searchCStaff.getStaffIdentityCard().trim() + "%");
			}
			if(searchCStaff.getEntryPersonnel() != null
					&& !"".equals(searchCStaff.getEntryPersonnel())){

				hql += " and s.entryPersonnel like ?";
				prams.add("%" + searchCStaff.getEntryPersonnel().trim() + "%");
			}
			if(searchCStaff.getAccountID() != null
					&& !"".equals(searchCStaff.getAccountID())){

				hql += " and s.account like ?";
				prams.add("%" + searchCStaff.getAccountID().trim() + "%");
			}
			if(showType==null||showType.equals("")) {
				if (searchCStaff.getStaffName() != null
						&& !"".equals(searchCStaff.getStaffName().trim()) && searchCStaff.getStaffIdentityCard() != null
						&& !"".equals(searchCStaff.getStaffIdentityCard().trim())) {


					hql += " or (exists (select f.staffID from RelateStaff f where f.staffID = s.staffID and  f.companyID = ?) and s.staffName like ? and s.staffIdentityCard like ?)";
					prams.add(account.getCompanyID());
					prams.add("%" + searchCStaff.getStaffName().trim() + "%");
					prams.add("%" + searchCStaff.getStaffIdentityCard().trim() + "%");
				} else if (searchCStaff.getStaffName() != null
						&& !"".equals(searchCStaff.getStaffName().trim())) {

					hql += " or (exists (select f.staffID from RelateStaff f where f.staffID = s.staffID and  f.companyID = ?) and s.staffName like ?)";
					prams.add(account.getCompanyID());
					prams.add("%" + searchCStaff.getStaffName().trim() + "%");

				} else if (searchCStaff.getStaffIdentityCard() != null
						&& !"".equals(searchCStaff.getStaffIdentityCard().trim())) {


					hql += "or (exists (select f.staffID from RelateStaff f where f.staffID = s.staffID and  f.companyID = ?) and s.staffIdentityCard like ?)";
					prams.add(account.getCompanyID());
					prams.add("%" + searchCStaff.getStaffIdentityCard().trim() + "%");
				}
			}

		}
		if(parameter != null && showType!=null){
			if(showType.equals("address")){
				String address;
//				if(search.length()>2){
//					address=parameter.substring(0, search.length()-1);
//				}else{
//					address=parameter.substring(0, search.length());
//				}

				hql += " and exists (select a.staffID from StaffAddress a where a.staffID = s.staffID and a.addressDetailed like ? and a.companyID = ?)";
				prams.add("%" + parameter + "%");
				prams.add(account.getCompanyID());
			}else if(showType.equals("connection")){
				hql += " and exists (select c.staffID from ContactRelation c where c.staffID = s.staffID and c.relation = ? and c.companyID = ? )";
				prams.add(parameter);
				prams.add(account.getCompanyID());
			}
		}
		if(search == null||search.equals("")){
			//没有条件查询的情况下查询三个月内的信息
			hql += " and trunc(s.verifyTime,'mm') between trunc(add_months(sysdate,-3),'mm') and trunc(sysdate,'mm')";
			if(showType==null||showType.equals("")) {
				hql += " or exists (select f.staffID from RelateStaff f where f.staffID = s.staffID and  f.companyID = ?)";
				prams.add(account.getCompanyID());
			}
		}



		hql += " order by s.verifyTime desc";
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 15 : pageNumber), hql, prams.toArray());
		
		connectionlist = codeService.getCCodeListByPID(account.getCompanyID(),
				"scode20110224xpd2t2jvda0000000002");
		return "list";
	}
	
	/**
	 * 导出Staff
	 * 
	 * @return
	 */
	public String showStaffExcel() {
		String groupCompanySn = ActionContext.getContext().getSession().get(
				"groupCompanySn").toString();
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		List<BaseBean> stafflist;
		CAccount account = (CAccount) session.get("account");
		if (search != null) {
			String hql = "from Staff where staffStatus = '00' and groupCompanySn = ?";
			searchCStaff = (Staff) ActionContext.getContext().getSession()
				.get("tablesearch");
			List<Object> list = new ArrayList<Object>();
			list.add(groupCompanySn);
			if(search.equals("search")){
				if (searchCStaff.getStaffName() != null
						&& !"".equals(searchCStaff.getStaffName().trim())) {
					hql+=" and staffName like ?";
					list.add("%" + searchCStaff.getStaffName() + "%");
				}
				if (searchCStaff.getStaffIdentityCard() != null
						&& !"".equals(searchCStaff.getStaffIdentityCard().trim())) {
					hql+=" and staffIdentityCard like ?";
					list.add("%" + searchCStaff.getStaffIdentityCard() + "%");
				}
				if(searchCStaff.getStaffAddress() !=null
						&& !"".equals(searchCStaff.getStaffAddress().trim())){
					hql+=" and staffAddress like ?";
					list.add("%" + searchCStaff.getStaffAddress() + "%");
				}
				if(searchCStaff.getEntryPersonnel() != null
						&& !"".equals(searchCStaff.getEntryPersonnel())){
					hql+=" and entryPersonnel like ?";
					list.add("%" + searchCStaff.getEntryPersonnel() + "%");
				}
			}
			if(showType.equals("address")){
				String address;
				if(search.length()>2){
					address=search.substring(0, search.length()-1);
				}else{
					address=search.substring(0, search.length());
				}
				hql+=" and staffAddress like ?";
				list.add("%" + address + "%");
			}
				
			hql+=" order by verifyTime desc ";
			int size=list.size();
			Object [] params=new Object[size];
			for (int i = 0; i < size; i++) {
				params[i]=list.get(i);
			}
			stafflist = baseBeanService.getListBeanByHqlAndParams(hql, params);
			excelStream = excelService.showExcel(Staff.columnHeadings(),
					stafflist);
			CLogBook logBook = logBookService.saveCLogBook(null, "导出人员列表",
					account);
			baseBeanService.update(logBook);
			return "showexcel";
		}
		String hql = " from Staff where groupCompanySn = ?  and"
				+ " staffStatus = '00' order by verifyTime desc ";
		Object[] params = { groupCompanySn };
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
		String groupCompanySn = ActionContext.getContext().getSession()
				.get("groupCompanySn").toString();
		if (groupCompanySn == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		String hql = "select count(*) from Staff where  staffIdentityCard = ? and groupCompanySn = ? ";
		Object[] params = { cstaff.getStaffIdentityCard(), groupCompanySn };
		beans = new ArrayList<BaseBean>();
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		if (cstaff.getStaffID() == null || "".equals(cstaff.getStaffID())) {
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			cstaff.setStaffCode("NO" + pcount);
			cstaff.setRecordCode("NO" + pcount);
			cstaff.setVerifyTime(new Date());
			cstaff.setStaffID(serverService.getServerID("cstaff"));
			parameter = "添加员工（人员姓名：:" + cstaff.getStaffName() + ")";
		} else {
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { cstaff.getStaffID() });
			cstaff.setVerifyTime(new Date());
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
					.savePhoto(
							path,
							photoFileName,
							photo,
							account.getCompanyID(),
							"/human/personPhotos/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
			if (cstaff.getPhoto() != null && !"".equals(cstaff.getPhoto())) {
				ArrayList<String> paths = new ArrayList<String>();
				paths.add(path + cstaff.getPhoto());
				fileService.deletePhotos(paths);
			}
			cstaff.setPhoto(photoPath);
		}
		cstaff.setGroupCompanySn(groupCompanySn);
		cstaff.setStaffStatus("00");
		beans.add(cstaff);
		CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	/**
	 * 删除Staff把状态staffStatus改为98
	 * 
	 * @return
	 */
	// public String delCStaff() {
	// ActionContext actionContext = ActionContext.getContext();
	// Map<String, Object> session = actionContext.getSession();
	// String groupCompanySn = ActionContext.getContext().getSession().get(
	// "groupCompanySn").toString();
	// CAccount account = (CAccount) session.get("account");
	// String obj = (String) session.get("session_value");
	// beans = new ArrayList<BaseBean>();
	// if (sub != null && sub.equals(obj)) {
	// String hql = "update Staff set staffStatus = ? where groupCompanySn = ?
	// and staffID = ? ";
	// Object[] params = { "98", groupCompanySn, cstaff.getStaffID() };
	// String hql2 = "from Staff where staffID=?";
	// Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
	// params);
	// parameter = "删除员工(人员名称:" + staff.getStaffName() + ")";
	// CLogBook logBook = logBookService.saveCLogBook(null, parameter,
	// account);
	// beans.add(logBook);
	// baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
	// new String[] { hql }, params);
	// }
	// return "success";
	// }

	/**
	 * 个人客户需求报表查询
	 */
	@SuppressWarnings("unchecked")
	public String getBasicInformation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");// company201009046vxdyzy4wg0000000025
		HttpServletRequest request = ServletActionContext.getRequest();
		String staffID = request.getParameter("staffID");// staffid-cstaff20100910z2wswpqgbd0000000218
		String sqlBasic = " select sa.recordcode,sa.staffName,sa.usednmae,sa.birthday,sa.nativePlace,"
				+ "sa.nation,sa.staffIdentityCard,sa.staffAddress,sa.Photo,"
				+ "sa.reference,sa.referenceorganization,sa.referencecode"
				+ " from dt_hr_staff sa where sa.staffid = ?";
		List<BaseBean> listBasic = baseBeanService.getListBeanBySqlAndParams(
				sqlBasic, new Object[] { staffID });
		request.setAttribute("basic", listBasic);
		String sql2 = " select ad.addressdetailed from dt_hr_staff_address ad "
				+ " where ad.addresskey = (select  max(t.addresskey) "
				+ "from dt_hr_staff_address t where ad.staffid = ?)";
		List<BaseBean> data2 = baseBeanService.getListBeanBySqlAndParams(sql2,
				new Object[] { staffID });
		request.setAttribute("address", data2);
		String degreeSql = "select se.educationStart,se.educationEnd,se.educationName,cc.codeValue,cc1.codeValue codeValue1,cc2.codeValue codeValue2,cc3.codeValue codeValue3,se.provePerson ";
		degreeSql += " from dt_hr_staff_education se left join dt_hr_staff sa on sa.staffid=se.staffid left join dtccode cc on cc.codeid=se.majortype";
		degreeSql += " left join dtccode cc1 on cc1.codeid=se.educationHistory left join dtccode cc2 on cc2.codeid=se.educationStyle left join dtccode cc3 on cc3.codeid=se.educationType";
		degreeSql += " where cc.companyID=? and cc.codeStatus='00' and cc1.companyID=? and cc1.codeStatus='00' and cc2.companyID=? and cc2.codeStatus='00'";
		degreeSql += " and cc3.companyID=? and cc3.codeStatus='00' and sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus ='50' ) and sa.staffid=?";
		List<BaseBean> listDegree = baseBeanService.getListBeanBySqlAndParams(
				degreeSql,
				new Object[] { account.getCompanyID(), account.getCompanyID(),
						account.getCompanyID(), account.getCompanyID(),
						account.getCompanyID(), staffID });
		request.setAttribute("listDegree", listDegree);
		String resumeSql = " select	se.startTime, se.endTime,se.companyName,se.position,se.duties,se.postName,se.reference";
		resumeSql += " from dt_hr_staff_Resume se where se.staffid=? ";
		List<BaseBean> listResume = baseBeanService.getListBeanBySqlAndParams(
				resumeSql, new Object[] { staffID });
		request.setAttribute("listResume", listResume);
		return "getBasicInformation";
	}

	/**
	 * 个人需求报表打印预览
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String toprintpeople() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");// company201009046vxdyzy4wg0000000025
		HttpServletRequest request = ServletActionContext.getRequest();
		String staffID = request.getParameter("staffID");// staffid-cstaff20100910z2wswpqgbd0000000218
		String sqlBasic = " select sa.recordcode,sa.staffName,sa.usednmae,sa.birthday,sa.nativePlace,"
				+ "sa.nation,sa.staffIdentityCard,sa.staffAddress,sa.Photo,"
				+ "sa.reference,sa.referenceorganization,sa.referencecode"
				+ " from dt_hr_staff sa where sa.staffid = ?";
		List<BaseBean> listBasic = baseBeanService.getListBeanBySqlAndParams(
				sqlBasic, new Object[] { staffID });
		request.setAttribute("basic", listBasic);
		String sql2 = " select ad.addressdetailed from dt_hr_staff_address ad "
				+ " where ad.addresskey = (select  max(t.addresskey) "
				+ "from dt_hr_staff_address t where ad.staffid = ?)";
		List<BaseBean> data2 = baseBeanService.getListBeanBySqlAndParams(sql2,
				new Object[] { staffID });
		request.setAttribute("address", data2);
		String degreeSql = "select se.educationStart,se.educationEnd,se.educationName,cc.codeValue,cc1.codeValue codeValue1,cc2.codeValue codeValue2,cc3.codeValue codeValue3,se.provePerson ";
		degreeSql += " from dt_hr_staff_education se left join dt_hr_staff sa on sa.staffid=se.staffid left join dtccode cc on cc.codeid=se.majortype";
		degreeSql += " left join dtccode cc1 on cc1.codeid=se.educationHistory left join dtccode cc2 on cc2.codeid=se.educationStyle left join dtccode cc3 on cc3.codeid=se.educationType";
		degreeSql += " where cc.companyID=? and cc.codeStatus='00' and cc1.companyID=? and cc1.codeStatus='00' and cc2.companyID=? and cc2.codeStatus='00'";
		degreeSql += " and cc3.companyID=? and cc3.codeStatus='00' and sa.staffID in (select staffID from dtcos where companyID = ?  and cosStatus ='50' ) and sa.staffid=?";
		List<BaseBean> listDegree = baseBeanService.getListBeanBySqlAndParams(
				degreeSql,
				new Object[] { account.getCompanyID(), account.getCompanyID(),
						account.getCompanyID(), account.getCompanyID(),
						account.getCompanyID(), staffID });
		request.setAttribute("listDegree", listDegree);
		String resumeSql = " select	se.startTime, se.endTime,se.companyName,se.position,se.duties,se.postName,se.reference";
		resumeSql += " from dt_hr_staff_Resume se where se.staffid=? ";
		List<BaseBean> listResume = baseBeanService.getListBeanBySqlAndParams(
				resumeSql, new Object[] { staffID });
		request.setAttribute("listResume", listResume);
		return "toprintpeople";

	}

	/**
	 * 单位客户需求报表打印预览
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String toprintutil() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String sqlBasic = " select t.companyname,t.companytel,t.cresponsible,t.responsibletel,t.industrytype,t.companyaddr from dtcontactcompany t where t.ccompanyid=?";
		List<BaseBean> listBasic = baseBeanService.getListBeanBySqlAndParams(
				sqlBasic, new Object[] { contactCompany.getCcompanyID() });
		request.setAttribute("basic", listBasic);
		return "toprintutil";
	}

	/**
	 * 完成单位客户需求报表查询
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String getUtilInformation() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String sqlBasic = " select t.companyname,t.companytel,t.cresponsible,t.responsibletel,t.industrytype,t.companyaddr from dtcontactcompany t where t.ccompanyid=?";
		List<BaseBean> listBasic = baseBeanService.getListBeanBySqlAndParams(
				sqlBasic, new Object[] { contactCompany.getCcompanyID() });
		request.setAttribute("basic", listBasic);
		return "getUtilInformation";
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

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAa() {
		return aa;
	}

	public void setAa(String aa) {
		this.aa = aa;
	}

	public ContactCompany getContactCompany() {
		return contactCompany;
	}

	public void setContactCompany(ContactCompany contactCompany) {
		this.contactCompany = contactCompany;
	}

	public List<CCode> getConnectionlist() {
		return connectionlist;
	}

	public void setConnectionlist(List<CCode> connectionlist) {
		this.connectionlist = connectionlist;
	}

	public String getParameterStaffIDs() {
		return parameterStaffIDs;
	}

	public void setParameterStaffIDs(String parameterStaffIDs) {
		this.parameterStaffIDs = parameterStaffIDs;
	}

	public List<SDistrict> getNortheast() {
		return northeast;
	}

	public void setNortheast(List<SDistrict> northeast) {
		this.northeast = northeast;
	}

	public List<SDistrict> getNorthChina() {
		return northChina;
	}

	public void setNorthChina(List<SDistrict> northChina) {
		this.northChina = northChina;
	}

	public List<SDistrict> getEastChina() {
		return eastChina;
	}

	public void setEastChina(List<SDistrict> eastChina) {
		this.eastChina = eastChina;
	}

	public List<SDistrict> getSouthernChina() {
		return southernChina;
	}

	public void setSouthernChina(List<SDistrict> southernChina) {
		this.southernChina = southernChina;
	}

	public List<SDistrict> getSouthwest() {
		return southwest;
	}

	public void setSouthwest(List<SDistrict> southwest) {
		this.southwest = southwest;
	}

	public List<SDistrict> getNorthwest() {
		return northwest;
	}

	public void setNorthwest(List<SDistrict> northwest) {
		this.northwest = northwest;
	}

	public List<CCode> getTypelist() {
		return typelist;
	}

	public void setTypelist(List<CCode> typelist) {
		this.typelist = typelist;
	}

	public List<CCode> getProvincelist() {
		return provincelist;
	}

	public void setProvincelist(List<CCode> provincelist) {
		this.provincelist = provincelist;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public String getProduce() {
		return produce;
	}

	public void setProduce(String produce) {
		this.produce = produce;
	}

	public List<BaseBean> getDistinctlist1() {
		return distinctlist1;
	}

	public void setDistinctlist1(List<BaseBean> distinctlist1) {
		this.distinctlist1 = distinctlist1;
	}

	public List<CCode> getComconnectlist() {
		return comconnectlist;
	}

	public void setComconnectlist(List<CCode> comconnectlist) {
		this.comconnectlist = comconnectlist;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	

}
