package com.mysl.action.administrative;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.Company;
import hy.ea.bo.Remind;
import hy.ea.bo.human.Staff;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMyovertime;
import com.mysl.service.RemindService;
import com.opensymphony.xwork2.ActionContext;

/**
 * OvertimeApplyAction 加班申请单
 * 
 * @author mz
 * 
 */
@Controller
@Scope("prototype")
public class OvertimeApplyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private RemindService remindService;
	private String photoFileName;
	private Company company;
	public InputStream excelStream;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String search;
	private String companyname;
	private String organizationname;

	private String result;
	private File photo;

	private String downLoadPath; // 下载传值

	private String type;// 用于汇总
	
	private String checkid;//审核id
	private String checkurl;//审核路径
	/**
	 * 加班申请单
	 */
	private DtMyovertime dtMyovertime;
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
	/**
	 * 查询
	 * @return
	 */
	public String toSearchByOvertime(){
		session.put("tableSearch", dtMyovertime);
		return getListByOvertime() ;
	}
	/**
	 * 默认列表
	 * @return
	 */
	public String getListByOvertime() {
		String sql = "select c.organizationid,c.organizationname,d.postname"
				+ " from dtcos t"
				+ " left join dtcorganization c on t.organizationid = c.organizationid"
				+ " left join dt_hr_deptpost d on t.deppostid = d.deppostid"
				+ " where t.companyid = ? and t.staffid = ? and t.cosstatus = '50' and t.status = '01'";
		 department = (Object[]) baseBeanService.getObjectBySqlAndParams(sql,new Object[] { account.getCompanyID(), account.getStaffID() });
		 staff=(Staff)baseBeanService.getBeanByHqlAndParams(" from Staff where staffID=? ", new Object[]{account.getStaffID()});
		 List<Object> params=new ArrayList<Object>();
		 String hql=" from DtMyovertime  where 1=1 ";
		
		 if(type!=null&&type.equals("org")){
			 hql+=" and organizationid=? ";
			 params.add((String)session.get("organizationID"));
		 }else if(type!=null&&type.equals("company")){
			 hql+=" and companyid=? ";
			 params.add(account.getCompanyID());
		 }else{
			 hql+=" and staffid=? ";
			 params.add(account.getStaffID());
		 }
		 if(search!=null&&"search".equals(search)){
			 dtMyovertime=(DtMyovertime)session.get("tableSearch");
			 if(dtMyovertime.getSerialnumber()!=null&&!"".equals(dtMyovertime.getSerialnumber())){
				 hql+=" and serialnumber like ? ";
				 params.add("%"+dtMyovertime.getSerialnumber()+"%");
			 }
			 if(dtMyovertime.getAddtime()!=null&&!"".equals(dtMyovertime.getAddtime())){
				 hql+=" and addtime=? ";
				 params.add(dtMyovertime.getAddtime());
			 }
			 if(dtMyovertime.getStatus()!=null&&!"".equals(dtMyovertime.getStatus())){
				 hql+=" and status=? ";
				 params.add(dtMyovertime.getStatus());
			 }
		 }
		 hql+=" order by addtime desc";
		 pageForm=baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, params.toArray());
		 return "list";
	}
	/**
	 * 保存与修改
	 * @return
	 */
	public String saveOrEditByOvertime(){
		List<BaseBean>   list=new ArrayList<BaseBean>();
		if(dtMyovertime.getId()==null||"".equals(dtMyovertime.getId())){
			dtMyovertime.setId(serverService.getServerID("overtime"));
			parameter = "增加加班申请单(单据编号:" + "" + ")";

		}else{
			parameter = "修改加班申请单(单据编号:" + "" + ")";

		}
		
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/mysl/admin/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			dtMyovertime.setAttachPath(photoPath);
		}
		if("1".equals(buttonType)){
			dtMyovertime.setStatus("01");
			
			dtMycheck.setCheckid(serverService.getServerID("overtime"));
			dtMycheck.setId(dtMyovertime.getId());
			dtMycheck.setReceiptType("加班申请单");
			dtMycheck.setSerialnumber(dtMyovertime.getSerialnumber());
			dtMycheck.setLookOverurl("/ea/overtime/ea_getDetailsByOvertime.jspa?dtMyovertime.id="+dtMycheck.getId());
			dtMycheck.setPrinturl("/ea/overtime/ea_toPrintPreviewByOvertime.jspa?dtMyovertime.id="+dtMycheck.getId());
			dtMycheck.setListurl("/ea/overtime/ea_getListByOvertime.jspa?dtMyovertime.staffid="+dtMyovertime.getStaffid());
			dtMycheck.setTeablename("DT_MYOVERTIME");
			dtMycheck.setAddtime(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, DateUtil.getCurrentDate()));
			dtMycheck.setApplyerid(dtMyovertime.getStaffid());
			dtMycheck.setApplyername(dtMyovertime.getStaffname());
			dtMycheck.setApplyorg(dtMyovertime.getOrganizationid());
			dtMycheck.setApplyorgname(dtMyovertime.getOrganizationname());
			dtMycheck.setApplycompanyid(dtMyovertime.getCompanyid());
			dtMycheck.setApplycompanyname(dtMyovertime.getCompanyname());
			dtMycheck.setAuditorstatus("01");
			list.add(dtMycheck);
			
			//提醒
			Remind remind = new Remind();
			remind.setRemindID(serverService.getServerID("remind"));
			remind.setCircularTitle("加班申请单待审核");
			remind.setCircularText("您有一个加班申请单待审核，请及时处理");
			remind.setStaffID(dtMycheck.getAuditorid());
			remind.setStaffName(dtMycheck.getAuditorname());
			remind.setOrganizationID(dtMycheck.getAuditororgid());
			remind.setCompanyID(dtMycheck.getAuditorcompanyid());
			remind.setCircularType("02");
			remind.setAddDate(new Date());
			
		    remind.setDetailedurl("/page/mysl/administrative/ReceiptCheck.jsp");
			
			remind.setRemindStatus("01");
			remind.setRemindType("01");
			remindService.addremind(remind);
			
			
		}else{
			dtMyovertime.setStatus("00");
		}
		dtMyovertime.setAddtime(new Date());
		list.add(dtMyovertime);
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
	public String getDetailsByOvertime(){
		dtMyovertime=(DtMyovertime) baseBeanService.getBeanByHqlAndParams(" from DtMyovertime  where id=?", new Object[]{dtMyovertime.getId()});
		String hqlc="from DtMycheck where id=? order by addtime desc";
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 100 : pageNumber),
				hqlc, new Object[]{dtMyovertime.getId()});
		return "details";
	}
	
	/**
	 * 打印预览
	 * @return
	 */
	public String toPrintPreviewByOvertime(){
		dtMyovertime=(DtMyovertime) baseBeanService.getBeanByHqlAndParams(" from DtMyovertime  where id=?", new Object[]{dtMyovertime.getId()});
		return "printPreview";
	}
	/**
	 * 删除
	 * @return
	 */
	public String deleteByOvertime(){
		baseBeanService.deleteBeanByKey(DtMyovertime.class, dtMyovertime.getKey());
		return "success";
	}
	/**
	 * 导出excel
	 * @return
	 */
	public String exportByOvertime(){
		List<Object> params=new ArrayList<Object>();
		 String hql=" from DtMyovertime  where 1=1 ";
		 if(type!=null&&type.equals("org")){
			 hql+=" and organizationid=? ";
			 params.add((String)session.get("organizationID"));
		 }else if(type!=null&&type.equals("company")){
			 hql+=" and companyid=? ";
			 params.add(account.getCompanyID());
		 }else{
			 hql+=" and staffid=? ";
			 params.add(account.getStaffID());
		 }
		 if(search!=null&&"search".equals(search)){
			 dtMyovertime=(DtMyovertime)session.get("tableSearch");
			 if(dtMyovertime.getSerialnumber()!=null&&!"".equals(dtMyovertime.getSerialnumber())){
				 hql+=" and serialnumber=? ";
				 params.add(dtMyovertime.getSerialnumber());
			 }
			 if(dtMyovertime.getAddtime()!=null&&!"".equals(dtMyovertime.getAddtime())){
				 hql+=" and addtime=? ";
				 params.add(dtMyovertime.getAddtime());
			 }
			 if(dtMyovertime.getStatus()!=null&&!"".equals(dtMyovertime.getStatus())){
				 hql+=" and status=? ";
				 params.add(dtMyovertime.getStatus());
			 }
		 }
		List<BaseBean> list =  baseBeanService.getListBeanByHqlAndParams(hql, params.toArray());
	    String hqlc="from DtMycheck where id=? order by addtime desc";
	    
	   
	    for(BaseBean b:list){
	    	String str="";
	    	DtMyovertime overtime = (DtMyovertime) b;
		   List<BaseBean> clist = baseBeanService.getListBeanByHqlAndParams(hqlc,new Object[]{overtime.getId()});
		   int num = 0; 
		   for(BaseBean c:clist){
	        	 DtMycheck check = (DtMycheck) c;
	        	 String status = "";
	        	if(check.getAuditorstatus().equals("01")){
	        		status="待审核";
	        		
	        	}else if(check.getAuditorstatus().equals("03")){
	        		status="已驳回";
	        		
	        	}else{
	        		status="审核通过";
	        		
	        	}
	        	
	        	if(num!=0){
	        		
	        		str+="——>"+check.getAuditorname()+"  "+status;
	        	}else{
	        		str+=check.getAuditorname()+"  "+status;
	        	}
	        	num++;
	        				
	         }
	         overtime.setAuditor(str);
	    }
		excelStream=excelService.showExcel(DtMyovertime.columnHeadings(),list);
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
	
	/**
	 * 下载
	 * 
	 */
	public void downFile() {
		FileUtil fu = new FileUtil();
		try {
			fu.downFile(downLoadPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}






	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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



	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
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

	public String getDownLoadPath() {
		return downLoadPath;
	}

	public void setDownLoadPath(String downLoadPath) {
		this.downLoadPath = downLoadPath;
	}


	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public DtMyovertime getDtMyovertime() {
		return dtMyovertime;
	}
	public void setDtMyovertime(DtMyovertime dtMyovertime) {
		this.dtMyovertime = dtMyovertime;
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
