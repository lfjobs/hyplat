package hy.ea.human.action;

import com.mysl.bo.administrative.DtMyovertime;
import hy.ea.human.service.OvertimeApplyMobileService;
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
public class OvertimeApplyMobileAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private SignInCheckOnService signInCheckOnService;
	@Autowired
	private OvertimeApplyMobileService overtimeApplyMobileService;
	private PageForm pageForm;
	private String result;
	private DtMyovertime overtimeApply;
	private String overtimeApplyTime;

	public String add() {
		return "add";
	}

	public String addOvertimeApply() throws Exception {
		overtimeApplyMobileService.addOvertimeApply(overtimeApply);
		return "success";
	}

	public String find() {
		return "find";
	}

	public String overtimeApplyList() throws Exception {
		result = overtimeApplyMobileService.findOvertimeApplyList(pageForm,overtimeApplyTime);
		return "success";
	}

	public String update() {
		return "update";
	}

	public String delete(){
		overtimeApplyMobileService.deleteOvertimeApplyByKey(overtimeApply.getKey());
		return "success";
	}

	public String findOvertimeApply() {
		result = overtimeApplyMobileService.findOvertimeApplyByKey(overtimeApply.getKey());
		return "success";
	}

	public String updateOvertimeApply() throws Exception {
		overtimeApplyMobileService.updateOvertimeApply(overtimeApply);
		return "success";
	}

	public String findCurrentUser(){
		result = signInCheckOnService.findCurrentUser();
		return "success";
	}

	public String findCheckOnReviewer(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkOnReviewer", overtimeApplyMobileService.findCheckOnReviewer());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String computeOvertimeHours() throws Exception {
		double overtimeHours = overtimeApplyMobileService.computeOvertimeHours(overtimeApply.getOverTimeStartDate(),overtimeApply.getOverTimeEndDate());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("overtimeHours", String.valueOf(overtimeHours));
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String findSignInSetup() {
		String[] signInSetup = overtimeApplyMobileService.findSignInSetup();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("signInSetup", signInSetup);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String checkOvertime() throws Exception{
		String checkResult = overtimeApplyMobileService.checkOvertime(overtimeApply.getOverTimeStartDate(),overtimeApply.getOverTimeEndDate());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkResult", checkResult);
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
