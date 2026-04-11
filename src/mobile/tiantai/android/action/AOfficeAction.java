package mobile.tiantai.android.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mysl.bo.administrative.DtMycheck;
import com.mysl.bo.administrative.DtMyleave;

@Controller
@Scope("prototype")
public class AOfficeAction  {
	private static final Logger logger = LoggerFactory.getLogger(AOfficeAction.class);
	@Resource
	private BaseBeanService baseBeanService;

	@Resource
	private ServerService serverService;


	private PageForm pageForm;
	private String search;

	private DtMycheck dtMycheck; //审核单
	private DtMyleave dtMyleave;// 请假单
	private int pageSize;// 每页条数
	private int pageNumber;// 页数

	private String checkOrgID;
	private String checkComID;
	private String staffID;

	private Object result;// 返回值

	private String parameter;
    
	private String leavePostName;			//职位      
	private String leaveDays;				//请假天数    
	private String leaveHour;			    //请假小时    
	private String leaveStartDate;			//起时间     
	private String leaveEndDate;			//止时间     
	private String leaveReceiver;			//工作接管人   
	private String leaveReceiverid;//工作接管人id          
	private String leaveType;				//请假类别    
	private String leaveReason; 			//请假原因    
	private String signdate;				//报到日期    
	private String terminatedate;			//销假日期    
	private String leavecasual;				//事假累计    
	private String leavesick;				//病假累计    
	private String checkdiscount; 			//考勤折扣    
	private String attachPath;//附件                    
	private String applyDate;//申请时间                   


	/**
	 * 列表
	 * 
	 * @return
	 */
	public String getOfficeList() {

		String sql = " from DT_MYLEAVE d where d.companyid=? and d.organizationid=? and d.staffid=?";
		List<Object> params = new ArrayList<Object>();

		params.add(checkComID);
		params.add(checkOrgID);
		params.add(staffID);
		JSONObject jret = new JSONObject();

		if (search != null && "search".equals(search)) {
			if (dtMyleave.getSerialnumber() != null
					&& !"".equals(dtMyleave.getSerialnumber())) {
				sql += " and serialnumber like ? ";
				params.add("%" + dtMyleave.getSerialnumber() + "%");
			}
			if (dtMyleave.getAddtime() != null
					&& !"".equals(dtMyleave.getAddtime())) {
				sql += " and addtime=? ";
				params.add(dtMyleave.getAddtime());
			}
			if (dtMyleave.getStatus() != null
					&& !"".equals(dtMyleave.getStatus())) {
				sql += " and status=? ";
				params.add(dtMyleave.getStatus());
			}

		}

		sql += " order by addtime desc";

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber),
				"select id,companyname,organizationname,staffname,leaveType,leaveReason,to_char(addtime,'yyyy-mm-dd'),key" + sql,
				"select count(*)" + sql, params.toArray());
		if (pageForm != null && pageForm.getList() != null
				&& pageForm.getList().size() > 0) {

			List<Object> list = new ArrayList<Object>();
			for (Object page : pageForm.getList()) {
				Object[] object = (Object[]) page;
				JSONObject json = new JSONObject();
				json.accumulate("id", object[0] == null ? "" : object[0]);
				json.accumulate("companyname", object[1] == null ? ""
						: object[1]);
				json.accumulate("organizationname", object[2] == null ? ""
						: object[2]);
				json.accumulate("staffname", object[3] == null ? "" : object[3]);
				json.accumulate("leaveType", object[4] == null ? "" : object[4]);
				json.accumulate("leaveReason", object[5] == null ? "": object[5]);
				json.accumulate("addtime", object[6] == null ? "": object[6]);
				json.accumulate("key", object[7] == null ? "" : object[7]);
				
				list.add(json);
			}
			jret.accumulate("list", list);
			jret.accumulate("pageNumber", pageForm.getPageNumber());
			jret.accumulate("pageSize", pageForm.getPageSize());
			jret.accumulate("result", "data");

		} else {
			jret.accumulate("result", "nodata");
		}
		result = jret;

		return "success";
		                 
	}
	
	
	/**
	 * 保存
	 * 
	 * @return
	 */
	public String saveOrEditByLeave() {
		List<BaseBean> list = new ArrayList<BaseBean>();
		JSONObject jret = new JSONObject();

		try {
            
			
			dtMyleave.setId(serverService.getServerID("leave"));
			parameter = "增加请假申请单(单据编号:" + "" + ")";
            
			dtMyleave.setStatus("01");
			dtMyleave.setAddtime(new Date());
			list.add(dtMyleave);

			dtMycheck.setCheckid(serverService.getServerID("Leave"));
			dtMycheck.setId(dtMyleave.getId());
			dtMycheck.setReceiptType("请假申请单");
			dtMycheck.setSerialnumber(dtMyleave.getSerialnumber());
			dtMycheck
					.setLookOverurl("/ea/leaveapply/ea_getDetailsByLeave.jspa?dtMyleave.id="
							+ dtMycheck.getId());
			dtMycheck
					.setPrinturl("/ea/leaveapply/ea_toPrintPreviewByLeave.jspa?dtMyleave.id="
							+ dtMycheck.getId());
			dtMycheck
					.setListurl("/ea/leaveapply/ea_getListByLeave.jspa?dtMyleave.staffid="
							+ dtMyleave.getStaffid());
			dtMycheck.setTeablename("DT_MYLeave");
			dtMycheck.setAddtime(DateUtil.parseDate(
					DateUtil.C_DATE_PATTON_DEFAULT, DateUtil.getCurrentDate()));
			dtMycheck.setApplyerid(dtMyleave.getStaffid());
			dtMycheck.setApplyername(dtMyleave.getStaffname());
			dtMycheck.setApplyorg(dtMyleave.getOrganizationid());
			dtMycheck.setApplyorgname(dtMyleave.getOrganizationname());
			dtMycheck.setApplycompanyid(dtMyleave.getCompanyid());
			dtMycheck.setApplycompanyname(dtMyleave.getCompanyname());
	     	dtMycheck.setAuditorid(dtMycheck.getAuditorid());
			dtMycheck.setAuditorname(dtMycheck.getAuditorname());
			dtMycheck.setAuditororgid(dtMycheck.getAuditororgid());
			dtMycheck.setAuditororgname(dtMycheck.getAuditororgname());
			dtMycheck.setAuditorcompanyid(dtMycheck.getAuditorcompanyid());
			dtMycheck.setAuditorcompanyname(dtMycheck.getAuditorcompanyname());
			dtMycheck.setAuditorstatus("01");
			list.add(dtMycheck);

			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null,
					null);
			jret.accumulate("result", "suc");
		} catch (Exception e) {
			logger.error("操作异常", e);
			jret.accumulate("result", "fail");
		}
		result = jret;
		return "success";
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	public String deleteByLeave() {
		JSONObject jret = new JSONObject();
		try {
			baseBeanService
					.deleteBeanByKey(DtMyleave.class, dtMyleave.getKey());
			jret.accumulate("result", "suc");
		} catch (Exception e) {
			logger.error("操作异常", e);
			jret.accumulate("result", "fail");
		}
		result = jret;
		return "success";
	}
	
	
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public DtMyleave getDtMyleave() {
		return dtMyleave;
	}

	public void setDtMyleave(DtMyleave dtMyleave) {
		this.dtMyleave = dtMyleave;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getCheckOrgID() {
		return checkOrgID;
	}

	public void setCheckOrgID(String checkOrgID) {
		this.checkOrgID = checkOrgID;
	}

	public String getCheckComID() {
		return checkComID;
	}

	public void setCheckComID(String checkComID) {
		this.checkComID = checkComID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}


	public DtMycheck getDtMycheck() {
		return dtMycheck;
	}

	public void setDtMycheck(DtMycheck dtMycheck) {
		this.dtMycheck = dtMycheck;
	}




	/**
	 * 查询
	 * 
	 * @return
	 */
	public String toSearchByLeave() {
		return getOfficeList();
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getLeavePostName() {
		return leavePostName;
	}
	public void setLeavePostName(String leavePostName) {
		this.leavePostName = leavePostName;
	}
	public String getLeaveDays() {
		return leaveDays;
	}
	public void setLeaveDays(String leaveDays) {
		this.leaveDays = leaveDays;
	}
	public String getLeaveHour() {
		return leaveHour;
	}
	public void setLeaveHour(String leaveHour) {
		this.leaveHour = leaveHour;
	}
	public String getLeaveStartDate() {
		return leaveStartDate;
	}
	public void setLeaveStartDate(String leaveStartDate) {
		this.leaveStartDate = leaveStartDate;
	}
	public String getLeaveEndDate() {
		return leaveEndDate;
	}
	public void setLeaveEndDate(String leaveEndDate) {
		this.leaveEndDate = leaveEndDate;
	}
	public String getLeaveReceiver() {
		return leaveReceiver;
	}
	public void setLeaveReceiver(String leaveReceiver) {
		this.leaveReceiver = leaveReceiver;
	}
	public String getLeaveReceiverid() {
		return leaveReceiverid;
	}
	public void setLeaveReceiverid(String leaveReceiverid) {
		this.leaveReceiverid = leaveReceiverid;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public String getSigndate() {
		return signdate;
	}
	public void setSigndate(String signdate) {
		this.signdate = signdate;
	}
	public String getTerminatedate() {
		return terminatedate;
	}
	public void setTerminatedate(String terminatedate) {
		this.terminatedate = terminatedate;
	}
	public String getLeavecasual() {
		return leavecasual;
	}
	public void setLeavecasual(String leavecasual) {
		this.leavecasual = leavecasual;
	}
	public String getLeavesick() {
		return leavesick;
	}
	public void setLeavesick(String leavesick) {
		this.leavesick = leavesick;
	}
	public String getCheckdiscount() {
		return checkdiscount;
	}
	public void setCheckdiscount(String checkdiscount) {
		this.checkdiscount = checkdiscount;
	}
	public String getAttachPath() {
		return attachPath;
	}
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	
	
}
