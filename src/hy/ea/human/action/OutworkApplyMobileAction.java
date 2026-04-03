package hy.ea.human.action;

import com.mysl.bo.administrative.DtMytravel;
import hy.ea.human.service.OutworkApplyMobileService;
import hy.ea.human.service.SignInCheckOnService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("prototype")
public class OutworkApplyMobileAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private SignInCheckOnService signInCheckOnService;
	@Autowired
	private OutworkApplyMobileService outworkApplyMobileService;


	private PageForm pageForm;
	private String result;
	private DtMytravel outworkApply;
	private String outworkApplyTime;

	public String add() {
		return "add";
	}

	public String addOutworkApply() throws Exception {
		outworkApplyMobileService.addOutworkApply(outworkApply);
		return "success";
	}

	public String find() {
		return "find";
	}


	public String outworkApplyList() throws Exception {
		result = outworkApplyMobileService.findOutworkApplyList(pageForm,outworkApplyTime);
		return "success";
	}

	public String update() {
		return "update";
	}

	public String delete(){
		outworkApplyMobileService.deleteOutworkApplyByKey(outworkApply.getKey());
		return "success";
	}

	public String findOutworkApply() {
		result = outworkApplyMobileService.findOutworkApplyByKey(outworkApply.getKey());
		return "success";
	}

	public String updateOutworkApply() throws Exception {
		outworkApplyMobileService.updateOutworkApply(outworkApply);
		return "success";
	}

	public String findCurrentUser(){
		result = signInCheckOnService.findCurrentUser();
		return "success";
	}

	public String findCheckOnReviewer(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkOnReviewer", outworkApplyMobileService.findCheckOnReviewer());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String computeOutworkHours() throws Exception {
		double outworkHours = outworkApplyMobileService.computeOutworkHours(outworkApply.getTravelStartDate(),outworkApply.getTravelEndDate());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("outworkHours", String.valueOf(outworkHours));
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String findSignInSetup() {
		String[] signInSetup = outworkApplyMobileService.findSignInSetup();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("signInSetup", signInSetup);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
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

	public DtMytravel getOutworkApply() {
		return outworkApply;
	}

	public void setOutworkApply(DtMytravel outworkApply) {
		this.outworkApply = outworkApply;
	}

	public String getOutworkApplyTime() {
		return outworkApplyTime;
	}

	public void setOutworkApplyTime(String outworkApplyTime) {
		this.outworkApplyTime = outworkApplyTime;
	}
}
