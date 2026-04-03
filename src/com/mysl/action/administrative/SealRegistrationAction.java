package com.mysl.action.administrative;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Remind;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMysealregistration;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;

/**
 * SealRegistrationAction 盖章登记表
 * 
 * @author zc
 * 
 */
@Controller
@Scope("prototype")
public class SealRegistrationAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private RemindService remindService; 
	
	public InputStream excelStream;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private String result;
	/**
	 * 盖章登记表
	 */
	private DtMysealregistration dtMysealregistration;
	/**
	 * 获得session
	 */
	private Map<String, Object> session = ActionContext.getContext()
			.getSession();
	/**
	 * 获得account
	 */
	private CAccount account = (CAccount) ActionContext.getContext()
			.getSession().get("account");
	/**
	 * 获得department
	 */
	private Object[] department;
	/**
	 * 获得staff
	 * @return
	 */
	private Staff  staff;
	/**
	 * 提交审核与保存草稿标识符
	 * @return
	 */
	private String buttonType;
	/**
	 * 
	 */
	private String serialnumber;
	/**
	 * 
	 */
	private DtMycheck dtMycheck;
	
	
	private String checkid;//审核id
	private String checkurl;//审核路径
	/**
	 * 查询
	 * @return
	 */
	public String toSearchBySealRegistration(){
		session.put("tableSearch", dtMysealregistration);
		return getListBySealRegistration();
	}
	/**
	 * 默认列表
	 * @return
	 */
	public String getListBySealRegistration() {
		String sql = "select c.organizationid,c.organizationname,d.postname"
				+ " from dtcos t"
				+ " left join dtcorganization c on t.organizationid = c.organizationid"
				+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
				+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
		 department = (Object[]) baseBeanService.getObjectBySqlAndParams(sql,new Object[] { account.getCompanyID(), account.getStaffID() });
		 staff=(Staff)baseBeanService.getBeanByHqlAndParams(" from Staff where staffID=? ", new Object[]{account.getStaffID()});
		 List<Object> params=new ArrayList<Object>();
		 String hql=" from DtMysealregistration  where 1=1 ";
		 hql+=" and staffid=? ";
		 params.add(account.getStaffID());
		 if(search!=null&&"search".equals(search)){
			 dtMysealregistration=(DtMysealregistration)session.get("tableSearch");
			 if(dtMysealregistration.getSerialnumber()!=null&&!"".equals(dtMysealregistration.getSerialnumber())){
				 hql+=" and serialnumber=? ";
				 params.add(dtMysealregistration.getSerialnumber());
			 }
			 if(dtMysealregistration.getAddtime()!=null&&!"".equals(dtMysealregistration.getAddtime())){
				 hql+=" and addtime=? ";
				 params.add(dtMysealregistration.getAddtime());
			 }
			 if(dtMysealregistration.getStatus()!=null&&!"".equals(dtMysealregistration.getStatus())){
				 hql+=" and status=? ";
				 params.add(dtMysealregistration.getStatus());
			 }
		 }
		 hql+=" order by addtime  desc ";
		 pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params.toArray());
		 return "list";
	}
	/**
	 * 保存与修改
	 * @return
	 */
	public String saveOrEditBySealRegistration(){
		List<BaseBean>   list=new ArrayList<BaseBean>();
		if(dtMysealregistration.getId()==null||"".equals(dtMysealregistration.getId())){
			dtMysealregistration.setId(serverService.getServerID("sanitaryinspection"));
			parameter = "增加盖章登记表(单据编号:" + "" + ")";
			dtMysealregistration.setAddtime(new Date());
		}else{
			parameter = "修改盖章登记表(单据编号:" + "" + ")";
		}
		if("1".equals(buttonType)){
			dtMysealregistration.setStatus("01");
			
			dtMycheck.setCheckid(serverService.getServerID("dtMycheck"));
			dtMycheck.setId(dtMysealregistration.getId());
			dtMycheck.setReceiptType("盖章登记单");
			dtMycheck.setSerialnumber(dtMysealregistration.getSerialnumber());
			dtMycheck.setLookOverurl("/ea/sealregistration/ea_getDetailsBySealRegistration.jspa?dtMysealregistration.id="+dtMycheck.getId());
			dtMycheck.setPrinturl("/ea/sealregistration/ea_toPrintPreviewBySealRegistration.jspa?dtMysealregistration.id="+dtMycheck.getId());
			dtMycheck.setListurl("/ea/sealregistration/ea_getListBySealRegistration.jspa?dtMysealregistration.staffid="+dtMysealregistration.getStaffid());
			dtMycheck.setTeablename("DT_MYSEALREGISTRATION");
			dtMycheck.setAddtime(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, DateUtil.getCurrentDate()));
			dtMycheck.setApplyerid(dtMysealregistration.getStaffid());
			dtMycheck.setApplyername(dtMysealregistration.getStaffname());
			dtMycheck.setApplyorg(dtMysealregistration.getOrganizationid());
			dtMycheck.setApplyorgname(dtMysealregistration.getOrganizationname());
			dtMycheck.setApplycompanyid(dtMysealregistration.getCompanyid());
			dtMycheck.setApplycompanyname(dtMysealregistration.getCompanyname());
			dtMycheck.setAuditorstatus("01");
			
			//行政提醒
			Remind remind = new Remind();
			remind.setRemindID(serverService.getServerID("remind"));
			remind.setCircularType("02");
			remind.setCircularTitle("盖章登记申请单审核待办");
			remind.setCircularText("您项个人审核有一个待办任务，请及时处理！");
			remind.setStaffID(dtMycheck.getAuditorid());
			remind.setStaffName(dtMycheck.getAuditorname());
			remind.setOrganizationID(dtMycheck.getApplyorg());
			remind.setCompanyID(dtMycheck.getApplycompanyid());
			remind.setAddDate(new Date());
			remind.setRemindStatus("01");
			remind.setRemindType("01");
			remind.setDetailedurl("page/mysl/administrative/ReceiptCheck.jsp");
			remindService.addremind(remind);
			
			list.add(dtMycheck);
		}else{
			dtMysealregistration.setStatus("00");
		}
		list.add(dtMysealregistration);
		CLogBook logbook = logBookService.saveCLogBook("",
				parameter, account);
		list.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
		
		return "success";
	}
	/**
	 * 查看详情
	 * @return
	 */
	public String getDetailsBySealRegistration(){
		dtMysealregistration=(DtMysealregistration) baseBeanService.getBeanByHqlAndParams(" from DtMysealregistration  where id=?", new Object[]{dtMysealregistration.getId()});
		String hqlc="from DtMycheck where id=? order by addtime desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hqlc, new Object[]{dtMysealregistration.getId()});
		return "details";
	}
	
	/**
	 * 打印预览
	 * @return
	 */
	public String toPrintPreviewBySealRegistration(){
		dtMysealregistration=(DtMysealregistration) baseBeanService.getBeanByHqlAndParams(" from DtMysealregistration  where id=?", new Object[]{dtMysealregistration.getId()});
		return "printPreview";
	}
	/**
	 * 删除
	 * @return
	 */
	public String deleteBySealRegistration(){
		baseBeanService.deleteBeanByKey(DtMysealregistration.class, dtMysealregistration.getKey());
		return "success";
	}
	/**
	 * 导出excel
	 * @return
	 */
	public String exportBySealRegistration(){
		List<Object> params=new ArrayList<Object>();
		 String hql=" from DtMysealregistration  where 1=1 ";
		 hql+=" and staffid=? ";
		 params.add(account.getStaffID());
		 if(search!=null&&"search".equals(search)){
			 dtMysealregistration=(DtMysealregistration)session.get("tableSearch");
			 if(dtMysealregistration.getSerialnumber()!=null&&!"".equals(dtMysealregistration.getSerialnumber())){
				 hql+=" and serialnumber=? ";
				 params.add(dtMysealregistration.getSerialnumber());
			 }
			 if(dtMysealregistration.getAddtime()!=null&&!"".equals(dtMysealregistration.getAddtime())){
				 hql+=" and addtime=? ";
				 params.add(dtMysealregistration.getAddtime());
			 }
			 if(dtMysealregistration.getStatus()!=null&&!"".equals(dtMysealregistration.getStatus())){
				 hql+=" and status=? ";
				 params.add(dtMysealregistration.getStatus());
			 }
		 }
		 hql+=" order by addtime  desc ";
		excelStream=excelService.showExcel(DtMysealregistration.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(hql, params.toArray()));
		return "excel";
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public DtMysealregistration getDtMysealregistration() {
		return dtMysealregistration;
	}

	public void setDtMysealregistration(
			DtMysealregistration dtMysealregistration) {
		this.dtMysealregistration = dtMysealregistration;
	}

	public Object[] getDepartment() {
		return department;
	}

	public void setDepartment(Object[] department) {
		this.department = department;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public DtMycheck getDtMycheck() {
		return dtMycheck;
	}
	public void setDtMycheck(DtMycheck dtMycheck) {
		this.dtMycheck = dtMycheck;
	}
	public String getCheckid() {
		return checkid;
	}
	public void setCheckid(String checkid) {
		this.checkid = checkid;
	}
	public String getCheckurl() {
		return checkurl;
	}
	public void setCheckurl(String checkurl) {
		this.checkurl = checkurl;
	}
	
}
