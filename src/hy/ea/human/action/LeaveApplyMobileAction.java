package hy.ea.human.action;

import com.mysl.bo.administrative.DtMyleave;
import hy.ea.human.service.LeaveApplyMobileService;
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
public class LeaveApplyMobileAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private SignInCheckOnService signInCheckOnService;
	@Autowired
	private LeaveApplyMobileService leaveApplyMobileService;
	private PageForm pageForm;
	private String result;
	private DtMyleave leaveApply;
	private String leaveApplyTime;

	public String add() {
		return "add";
	}

	public String addLeaveApply() throws Exception {
		leaveApplyMobileService.addLeaveApply(leaveApply);
		return "success";
	}

	public String find() {
		return "find";
	}

	public String leaveApplyList() throws Exception {
		result = leaveApplyMobileService.findLeaveApplyList(pageForm,leaveApplyTime);
		return "success";
	}

	public String update() {
		return "update";
	}

	public String delete(){
		leaveApplyMobileService.deleteLeaveApplyByKey(leaveApply.getKey());
		return "success";
	}

	public String findLeaveApply() {
		result = leaveApplyMobileService.findLeaveApplyByKey(leaveApply.getKey());
		return "success";
	}

	public String updateLeaveApply() throws Exception {
		leaveApplyMobileService.updateLeaveApply(leaveApply);
		return "success";
	}

	public String findCurrentUser(){
		result = signInCheckOnService.findCurrentUser();
		return "success";
	}

	/**
	 * 查询请假类别
	 * @return
	 */
	public String findLeaveCheckOnType(){
		result = signInCheckOnService.findLeaveCheckOnType();
		return "success";
	}

	public String findCheckOnReviewer(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkOnReviewer", leaveApplyMobileService.findCheckOnReviewer());
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String computeLeaveHours() throws Exception {
		double leaveHours = leaveApplyMobileService.computeLeaveHours(leaveApply.getLeaveStartDate(),leaveApply.getLeaveEndDate());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("leaveHours", String.valueOf(leaveHours));
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}

	public String findSignInSetup() {
		String[] signInSetup = leaveApplyMobileService.findSignInSetup();
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
