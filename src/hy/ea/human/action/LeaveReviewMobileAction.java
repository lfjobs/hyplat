package hy.ea.human.action;

import com.mysl.bo.administrative.DtMyleave;
import hy.ea.human.service.LeaveReviewMobileService;
import hy.plat.bo.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class LeaveReviewMobileAction {
	@Autowired
	private LeaveReviewMobileService leaveReviewMobileService;
	private PageForm pageForm;
	private String result;
	private DtMyleave leaveApply;
	private String leaveApplyTime;

	public String find() {
		return "find";
	}

	public String leaveApplyList() throws Exception {
		result = leaveReviewMobileService.findLeaveApplyList(pageForm,leaveApplyTime,leaveApply.getStatus());
		return "success";
	}

	public String update() {
		return "update";
	}

	public String findLeaveApply() {
		result = leaveReviewMobileService.findLeaveApplyByKey(leaveApply.getKey());
		return "success";
	}

	public String updateLeaveApply() throws Exception {
		leaveReviewMobileService.updateLeaveApply(leaveApply);
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

	public DtMyleave getLeaveApply() {
		return leaveApply;
	}

	public void setLeaveApply(DtMyleave leaveApply) {
		this.leaveApply = leaveApply;
	}

	public String getLeaveApplyTime() {
		return leaveApplyTime;
	}

	public void setLeaveApplyTime(String leaveApplyTime) {
		this.leaveApplyTime = leaveApplyTime;
	}
}
