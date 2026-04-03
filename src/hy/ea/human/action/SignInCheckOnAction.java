package hy.ea.human.action;

import hy.ea.bo.human.vo.SignInCheckOn;
import hy.ea.human.service.SignInCheckOnService;
import hy.ea.office.bo.CheckOnSetup;
import hy.ea.office.bo.CheckOnType;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope("prototype")
public class SignInCheckOnAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private SignInCheckOnService signInCheckOnService;
	private PageForm pageForm;
	private String search;
	private String result;
	private CheckOnSetup checkOnSetup;
	private CheckOnType checkOnType;
	private SignInCheckOn signInCheckOn;
	private String employee;
	private String detailType;

	public String find() {
		return "find";
	}

	public String ajaxCheckOnList() throws Exception {
		result = signInCheckOnService.findCheckOnList(pageForm,signInCheckOn.getCheckOnTimeType(),employee);
		return "success";
	}

	public String details() {
		return "details";
	}

	public String checkOnDetails() {
		return "checkOnDetails";
	}

	public String ajaxCheckOnDetails() {
		result = signInCheckOnService.getDetailFromSession(detailType,signInCheckOn);
		return "success";
	}

	public String findCurrentUser(){
		result = signInCheckOnService.findCurrentUser();
		return "success";
	}

	/**
	 * 查询考勤类别
	 * @return
	 */
	public String findCheckOnType(){
		result = signInCheckOnService.findCheckOnType();
		return "success";
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
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

	public CheckOnSetup getCheckOnSetup() {
		return checkOnSetup;
	}

	public void setCheckOnSetup(CheckOnSetup checkOnSetup) {
		this.checkOnSetup = checkOnSetup;
	}

	public CheckOnType getCheckOnType() {
		return checkOnType;
	}

	public void setCheckOnType(CheckOnType checkOnType) {
		this.checkOnType = checkOnType;
	}

	public SignInCheckOn getSignInCheckOn() {
		return signInCheckOn;
	}

	public void setSignInCheckOn(SignInCheckOn signInCheckOn) {
		this.signInCheckOn = signInCheckOn;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public String getDetailType() {
		return detailType;
	}

	public void setDetailType(String detailType) {
		this.detailType = detailType;
	}
}
