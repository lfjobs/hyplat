package hy.ea.office.action;

import hy.ea.office.bo.CheckOnType;
import hy.ea.office.service.CheckOnSetupService;
import hy.ea.office.bo.CheckOnSetup;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope("prototype")
public class CheckOnSetupAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Autowired
	private CheckOnSetupService checkOnSetupService;
	private PageForm pageForm;
	private String search;
	private String result;
	private CheckOnSetup checkOnSetup;
	private CheckOnType checkOnType;

	public String find() {
		return "find";
	}

	public String ajaxCheckOnSetupList() {
		result = checkOnSetupService.findCheckOnSetupList(pageForm);
		return "success";
	}

	public String add(){
		return "add";
	}

	/**
	 * 增加考勤设置
	 * @return
	 */
	public String addCheckOnSetup(){
		checkOnSetupService.addCheckOnSetup(checkOnSetup);
		return "success";
	}

	public String update(){
		return "update";
	}

	public String deleteCheckOnSetup(){
		checkOnSetupService.deleteCheckOnSetupById(checkOnSetup.getCheckOnSetupId());
		return "success";
	}

	public String findCheckOnSetup(){
		result = checkOnSetupService.findCheckOnSetupById(checkOnSetup.getCheckOnSetupId());
		return "success";
	}

	public String findCurrentUser(){
		result = checkOnSetupService.findCurrentUser();
		return "success";
	}

	public String findRankSalary(){
		result = checkOnSetupService.findRankSalary(checkOnSetup.getRankId());
		return "success";
	}

	/**
	 * 更新考勤设置
	 * @return
	 */
	public String updateCheckOnSetup(){
		checkOnSetupService.updateCheckOnSetup(checkOnSetup);
		return "success";
	}

	/**
	 * 查询考勤类别
	 * @return
	 */
	public String findCheckOnType(){
		result = checkOnSetupService.findCheckOnType();
		return "success";
	}

	public String findSalaryLevel(){
		result = checkOnSetupService.findSalaryLevelByCompanyID();
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
}
