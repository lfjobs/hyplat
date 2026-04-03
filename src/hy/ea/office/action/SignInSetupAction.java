package hy.ea.office.action;

import hy.ea.office.bo.SignInSetup;
import hy.ea.office.service.SignInSetupService;
import hy.plat.bo.PageForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class SignInSetupAction {
	@Autowired
	private SignInSetupService signInSetupService;
	private PageForm pageForm;
	private String search;
	private String result;
	private SignInSetup signInSetup;

	public String find() {
		return "find";
	}

	public String ajaxSignInSetupList() {
		result = signInSetupService.findSignInSetupList(pageForm);
		return "success";
	}

	public String add(){
		return "add";
	}

	/**
	 * 增加签到设置
	 * @return
	 */
	public String addSignInSetup(){
		signInSetupService.addSignInSetup(signInSetup);
		return "success";
	}

	public String update(){
		return "update";
	}

	public String deleteSignInSetup(){
		signInSetupService.deleteSignInSetupById(signInSetup.getSignInSetupId());
		return "success";
	}

	public String findSignInSetup(){
		result = signInSetupService.findSignInSetupById(signInSetup.getSignInSetupId());
		return "success";
	}

	public String findCurrentUser(){
		result = signInSetupService.findCurrentUser();
		return "success";
	}
	/**
	 * 更新签到设置
	 * @return
	 */
	public String updateSignInSetup(){
		signInSetupService.updateSignInSetup(signInSetup);
		return "success";
	}

	/**
	 * 查询签到类别
	 * @return
	 */
	public String findSignInType(){
		result = signInSetupService.findSignInType();
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

	public SignInSetup getSignInSetup() {
		return signInSetup;
	}

	public void setSignInSetup(SignInSetup signInSetup) {
		this.signInSetup = signInSetup;
	}
}
