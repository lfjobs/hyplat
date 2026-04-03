package hy.ea.human.action;

import hy.ea.human.service.SalarySettlementService;
import hy.plat.bo.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class SalarySettlementAction {
	@Autowired
	private SalarySettlementService salarySettlementService;
	@Autowired
	private TaskBean taskBean;

	private PageForm pageForm;
	private String result;
	private String settlementTime;
	private String companyId;
	private String staffId;
	private String settlementTimeType;

	public String find() {
		return "find";
	}

	public String salarySettlementList() throws Exception {
		result = salarySettlementService.findSalarySettlementAddList(pageForm,settlementTime,staffId);
		return "success";
	}

	public String findStaff() {
		return "findStaff";
	}

	public String findSalaryStaffSettlementList() throws Exception {
		result = salarySettlementService.findSalaryStaffSettlementAddList(pageForm,settlementTime);
		return "success";
	}

	public String salarySettlementDayList() throws Exception {
		result = salarySettlementService.findSalarySettlementDayList(pageForm,settlementTime,settlementTimeType,companyId,staffId);
		return "success";
	}

	public String findStaffTime() {
		return "findStaffTime";
	}

	public String findSalaryStaffTimeList() throws Exception {
		result = salarySettlementService.findSalaryStaffTimeList(pageForm,settlementTimeType);
		return "success";
	}

	public String findSalaryStaffTime(){
		result = salarySettlementService.findSalaryStaffTime(settlementTimeType);
		return "success";
	}

	public void runDayTask() throws Exception {
		taskBean.runDayTask();
	}


	public String details() {
		return "details";
	}

	public String dayList() {
		return "dayList";
	}

	public String dayDetails() {
		return "dayDetails";
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

	public String getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(String settlementTime) {
		this.settlementTime = settlementTime;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getSettlementTimeType() {
		return settlementTimeType;
	}

	public void setSettlementTimeType(String settlementTimeType) {
		this.settlementTimeType = settlementTimeType;
	}
}
