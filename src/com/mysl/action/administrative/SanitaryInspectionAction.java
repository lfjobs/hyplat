package com.mysl.action.administrative;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Remind;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMysanitaryinspection;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;

/**
 * SanitaryInspectionAction 卫生检查情况计分表
 * 
 * @author zc
 * 
 */
@Controller
@Scope("prototype")
public class SanitaryInspectionAction {
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
	 * 卫生检查情况计分表
	 */
	private DtMysanitaryinspection dtMysanitaryinspection;
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
	public String toSearchBySanitaryInspection(){
		session.put("tableSearch", dtMysanitaryinspection);
		return getListBySanitaryInspection() ;
	}
	/**
	 * 默认列表
	 * @return
	 */
	public String getListBySanitaryInspection() {
		String sql = "select c.organizationid,c.organizationname,d.postname"
				+ " from dtcos t"
				+ " left join dtcorganization c on t.organizationid = c.organizationid"
				+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
				+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
		 department = (Object[]) baseBeanService.getObjectBySqlAndParams(sql,new Object[] { account.getCompanyID(), account.getStaffID() });
		 staff=(Staff)baseBeanService.getBeanByHqlAndParams(" from Staff where staffID=? ", new Object[]{account.getStaffID()});
		 List<Object> params=new ArrayList<Object>();
		 String hql=" from DtMysanitaryinspection  where 1=1 ";
		 hql+=" and staffid=? ";
		 params.add(account.getStaffID());
		 if(search!=null&&"search".equals(search)){
			 dtMysanitaryinspection=(DtMysanitaryinspection)session.get("tableSearch");
			 if(dtMysanitaryinspection.getSerialnumber()!=null&&!"".equals(dtMysanitaryinspection.getSerialnumber())){
				 hql+=" and serialnumber=? ";
				 params.add(dtMysanitaryinspection.getSerialnumber());
			 }
			 if(dtMysanitaryinspection.getAddtime()!=null&&!"".equals(dtMysanitaryinspection.getAddtime())){
				 hql+=" and addtime=? ";
				 params.add(dtMysanitaryinspection.getAddtime());
			 }
			 if(dtMysanitaryinspection.getStatus()!=null&&!"".equals(dtMysanitaryinspection.getStatus())){
				 hql+=" and status=? ";
				 params.add(dtMysanitaryinspection.getStatus());
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
	public String saveOrEditBySanitaryInspection(){
		List<BaseBean>   list=new ArrayList<BaseBean>();
		if(dtMysanitaryinspection.getId()==null||"".equals(dtMysanitaryinspection.getId())){
			dtMysanitaryinspection.setId(serverService.getServerID("sanitaryinspection"));
			parameter = "增加卫生检查情况计分表(单据编号:" + "" + ")";
			dtMysanitaryinspection.setAddtime(new Date());
		}else{
			parameter = "修改卫生检查情况计分表(单据编号:" + "" + ")";
		}
		if("1".equals(buttonType)){
			dtMysanitaryinspection.setStatus("01");
			
			dtMycheck.setCheckid(serverService.getServerID("dtMycheck"));
			dtMycheck.setId(dtMysanitaryinspection.getId());
			dtMycheck.setReceiptType("卫生检查单");
			dtMycheck.setSerialnumber(dtMysanitaryinspection.getSerialnumber());
			dtMycheck.setLookOverurl("/ea/sanitaryinspection/ea_getDetailsBySanitaryInspection.jspa?dtMysanitaryinspection.id="+dtMycheck.getId());
			dtMycheck.setPrinturl("/ea/sanitaryinspection/ea_toPrintPreviewBySanitaryInspection.jspa?dtMysanitaryinspection.id="+dtMycheck.getId());
			dtMycheck.setListurl("/ea/sanitaryinspection/ea_getListBySanitaryInspection.jspa?dtMysanitaryinspection.staffid="+dtMysanitaryinspection.getStaffid());
			dtMycheck.setTeablename("DT_MYSANITARYINSPECTION");
			dtMycheck.setAddtime(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, DateUtil.getCurrentDate()));
			dtMycheck.setApplyerid(dtMysanitaryinspection.getStaffid());
			dtMycheck.setApplyername(dtMysanitaryinspection.getStaffname());
			dtMycheck.setApplyorg(dtMysanitaryinspection.getOrganizationid());
			dtMycheck.setApplyorgname(dtMysanitaryinspection.getOrganizationname());
			dtMycheck.setApplycompanyid(dtMysanitaryinspection.getCompanyid());
			dtMycheck.setApplycompanyname(dtMysanitaryinspection.getCompanyname());
			dtMycheck.setAuditorstatus("01");
			
			//行政提醒
			Remind remind = new Remind();
			remind.setRemindID(serverService.getServerID("remind"));
			remind.setCircularType("02");
			remind.setCircularTitle("卫生检查申请单审核待办");
			remind.setCircularText("您项个人审核有一个待办任务,请及时处理！");
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
			dtMysanitaryinspection.setStatus("00");
		}
		list.add(dtMysanitaryinspection);
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
	public String getDetailsBySanitaryInspection(){
		dtMysanitaryinspection=(DtMysanitaryinspection) baseBeanService.getBeanByHqlAndParams(" from DtMysanitaryinspection  where id=?", new Object[]{dtMysanitaryinspection.getId()});
		String hqlc="from DtMycheck where id=? order by addtime desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber),
				hqlc, new Object[]{dtMysanitaryinspection.getId()});
		return "details";
	}
	
	/**
	 * 打印预览
	 * @return
	 */
	public String toPrintPreviewBySanitaryInspection(){
		dtMysanitaryinspection=(DtMysanitaryinspection) baseBeanService.getBeanByHqlAndParams(" from DtMysanitaryinspection  where id=?", new Object[]{dtMysanitaryinspection.getId()});
		return "printPreview";
	}
	/**
	 * 删除
	 * @return
	 */
	public String deleteBySanitaryInspection(){
		baseBeanService.deleteBeanByKey(DtMysanitaryinspection.class, dtMysanitaryinspection.getKey());
		return "success";
	}
	/**
	 * 导出excel
	 * @return
	 */
	public String exportBySanitaryInspection(){
		List<Object> params=new ArrayList<Object>();
		 String hql=" from DtMysanitaryinspection  where 1=1 ";
		 hql+=" and staffid=? ";
		 params.add(account.getStaffID());
		 if(search!=null&&"search".equals(search)){
			 dtMysanitaryinspection=(DtMysanitaryinspection)session.get("tableSearch");
			 if(dtMysanitaryinspection.getSerialnumber()!=null&&!"".equals(dtMysanitaryinspection.getSerialnumber())){
				 hql+=" and serialnumber=? ";
				 params.add(dtMysanitaryinspection.getSerialnumber());
			 }
			 if(dtMysanitaryinspection.getAddtime()!=null&&!"".equals(dtMysanitaryinspection.getAddtime())){
				 hql+=" and addtime=? ";
				 params.add(dtMysanitaryinspection.getAddtime());
			 }
			 if(dtMysanitaryinspection.getStatus()!=null&&!"".equals(dtMysanitaryinspection.getStatus())){
				 hql+=" and status=? ";
				 params.add(dtMysanitaryinspection.getStatus());
			 }
		 }
		 hql+=" order by addtime  desc ";
		excelStream=excelService.showExcel(DtMysanitaryinspection.columnHeadings(), baseBeanService.getListBeanByHqlAndParams(hql, params.toArray()));
		return "excel";
	}
	/**
	 * JSON取得出单据编号
	 */
	public String getSerialNumber() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		String BillID=""; 
		if(serialnumber!=null&&!"".equals(serialnumber.trim())){
			if(Constant.billTypeNumber.get(serialnumber)!=null){
				BillID=serverService.getSerialNumber(Constant.billTypeNumber.get(serialnumber));
			}
		}
		 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("BillID", BillID);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
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
	public DtMysanitaryinspection getDtMysanitaryinspection() {
		return dtMysanitaryinspection;
	}

	public void setDtMysanitaryinspection(
			DtMysanitaryinspection dtMysanitaryinspection) {
		this.dtMysanitaryinspection = dtMysanitaryinspection;
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
