package hy.ea.human.action;

import hy.ea.bo.human.DutyScheduling;
import hy.ea.human.service.DutyApplyService;
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
public class DutyApplyAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private SignInCheckOnService signInCheckOnService;
	@Autowired
	private DutyApplyService dutyApplyService;
	private PageForm pageForm;
	private String result;
	private DutyScheduling dutyScheduling;
	private String dutyApplyTime;

	public String add() {
		return "add";
	}

	public String addDutyApply() throws Exception {
		dutyApplyService.addDutyApply(dutyScheduling);
		return "success";
	}

	public String find() {
		return "find";
	}

	public String dutyApplyList() throws Exception {
		result = dutyApplyService.findDutyApplyList(pageForm,dutyApplyTime);
		return "success";
	}

	public String update() {
		return "update";
	}

	public String delete(){
		dutyApplyService.deleteDutyApplyById(dutyScheduling.getDutySchedulingId());
		return "success";
	}

	public String findDutyApply() {
		result = dutyApplyService.findDutyApplyById(dutyScheduling.getDutySchedulingId());
		return "success";
	}

	public String updateDutyApply() throws Exception {
		dutyApplyService.updateDutyApply(dutyScheduling);
		return "success";
	}

	public String findCurrentUser(){
		result = signInCheckOnService.findCurrentUser();
		return "success";
	}

//	public String findCheckOnReviewer(){
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("checkOnReviewer", dutyApplyService.findCheckOnReviewer());
//		JSONObject obj = JSONObject.fromObject(map);
//		result = obj.toString();
//		return "success";
//	}

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

	public String getDutyApplyTime() {
		return dutyApplyTime;
	}

	public void setDutyApplyTime(String dutyApplyTime) {
		this.dutyApplyTime = dutyApplyTime;
	}

	public DutyScheduling getDutyScheduling() {
		return dutyScheduling;
	}

	public void setDutyScheduling(DutyScheduling dutyScheduling) {
		this.dutyScheduling = dutyScheduling;
	}
}
