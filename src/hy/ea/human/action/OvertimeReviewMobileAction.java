package hy.ea.human.action;

import com.mysl.bo.administrative.DtMyovertime;
import hy.ea.human.service.OvertimeReviewMobileService;
import hy.plat.bo.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class OvertimeReviewMobileAction {
	@Autowired
	private OvertimeReviewMobileService overtimeReviewMobileService;
	private PageForm pageForm;
	private String result;
	private DtMyovertime overtimeApply;
	private String overtimeApplyTime;

	public String find() {
		return "find";
	}

	public String overtimeApplyList() throws Exception {
		result = overtimeReviewMobileService.findOvertimeApplyList(pageForm,overtimeApplyTime,overtimeApply.getStatus());
		return "success";
	}

	public String update() {
		return "update";
	}

	public String findOvertimeApply() {
		result = overtimeReviewMobileService.findOvertimeApplyByKey(overtimeApply.getKey());
		return "success";
	}

	public String updateOvertimeApply() throws Exception {
		overtimeReviewMobileService.updateOvertimeApply(overtimeApply);
		return "success";
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

	public String getOvertimeApplyTime() {
		return overtimeApplyTime;
	}

	public void setOvertimeApplyTime(String overtimeApplyTime) {
		this.overtimeApplyTime = overtimeApplyTime;
	}

	public DtMyovertime getOvertimeApply() {
		return overtimeApply;
	}

	public void setOvertimeApply(DtMyovertime overtimeApply) {
		this.overtimeApply = overtimeApply;
	}
}
