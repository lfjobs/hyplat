package hy.ea.human.action;

import com.mysl.bo.administrative.DtMytravel;
import hy.ea.human.service.OutworkReviewMobileService;
import hy.plat.bo.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class OutworkReviewMobileAction {
	@Autowired
	private OutworkReviewMobileService outworkReviewMobileService;
	private PageForm pageForm;
	private String result;
	private DtMytravel outworkApply;
	private String outworkApplyTime;

	public String find() {
		return "find";
	}

	public String outworkApplyList() throws Exception {
		result = outworkReviewMobileService.findOutworkApplyList(pageForm,outworkApplyTime,outworkApply.getStatus());
		return "success";
	}

	public String update() {
		return "update";
	}

	public String findOutworkApply() {
		result = outworkReviewMobileService.findOutworkApplyByKey(outworkApply.getKey());
		return "success";
	}

	public String updateOutworkApply() throws Exception {
		outworkReviewMobileService.updateOutworkApply(outworkApply);
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

	public String getOutworkApplyTime() {
		return outworkApplyTime;
	}

	public void setOutworkApplyTime(String outworkApplyTime) {
		this.outworkApplyTime = outworkApplyTime;
	}

	public DtMytravel getOutworkApply() {
		return outworkApply;
	}

	public void setOutworkApply(DtMytravel outworkApply) {
		this.outworkApply = outworkApply;
	}
}
