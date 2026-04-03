package hy.ea.human.action;

import hy.ea.bo.human.SocialSecuritySetup;
import hy.ea.human.service.SocialSecuritySetupService;
import hy.ea.office.bo.CheckOnSetup;
import hy.ea.office.bo.CheckOnType;
import hy.ea.office.service.CheckOnSetupService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope("prototype")
public class SocialSecuritySetupAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private SocialSecuritySetupService socialSecuritySetupService;
	private PageForm pageForm;
	private String search;
	private String result;
	private SocialSecuritySetup socialSecuritySetup;

	public String find() {
		return "find";
	}

	public String ajaxSocialSecuritySetupList() {
		result = socialSecuritySetupService.findSocialSecuritySetupList(pageForm);
		return "success";
	}

	public String add(){
		return "add";
	}

	/**
	 * 增加设置
	 * @return
	 */
	public String addSocialSecuritySetup(){
		socialSecuritySetupService.addSocialSecuritySetup(socialSecuritySetup);
		return "success";
	}

	public String update(){
		return "update";
	}

	public String deleteSocialSecuritySetup(){
		socialSecuritySetupService.deleteSocialSecuritySetupById(socialSecuritySetup.getSocialSecuritySetupId());
		return "success";
	}

	public String findSocialSecuritySetup(){
		result = socialSecuritySetupService.findSocialSecuritySetupById(socialSecuritySetup.getSocialSecuritySetupId());
		return "success";
	}

	public String findCurrentUser(){
		result = socialSecuritySetupService.findCurrentUser();
		return "success";
	}

	/**
	 * 更新设置
	 * @return
	 */
	public String updateSocialSecuritySetup(){
		socialSecuritySetupService.updateSocialSecuritySetup(socialSecuritySetup);
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

	public SocialSecuritySetup getSocialSecuritySetup() {
		return socialSecuritySetup;
	}

	public void setSocialSecuritySetup(SocialSecuritySetup socialSecuritySetup) {
		this.socialSecuritySetup = socialSecuritySetup;
	}
}
