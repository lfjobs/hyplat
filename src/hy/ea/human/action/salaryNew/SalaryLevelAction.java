package hy.ea.human.action.salaryNew;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;

import hy.ea.bo.human.salaryNew.SalaryItem;
import hy.ea.bo.human.salaryNew.SalaryUnits;
import hy.ea.human.service.SalaryLevelService;

import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
/**
 * 薪资级别
 */
public class SalaryLevelAction {

	@Resource
	private SalaryLevelService salaryLevelService;
	private PageForm pageForm;
	private String result;
	private String search;
	private int pageNumber;
	private String parameter;
	private String suID;
	private SalaryUnits salaryUnits;
	private SalaryItem salaryItem;

	@Resource
	private BaseBeanService baseBeanService;


	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	SessionWrap sw = SessionWrap.getInstance();

	TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
			SessionWrap.KEY_SHOPCUSCOM);

    public String getSalaryLevelList(){

       CAccount account = (CAccount) session.getAttribute("account");
		salaryLevelService.getSalaryLevelList(pageNumber,30,account.getCompanyID());

		return "salarylevel";
	}

	/**
	 *
	 * 获取项目点
	 * @return
     */
	public String getSalaryItemList(){

		List<Object> list = salaryLevelService.getSalaryItemsList(suID);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("itemlist",list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}

	/**
	 *
	 * 获取项目单元
	 * @return
	 */
	public String getSalaryUnitsList(){
		CAccount account = (CAccount) session.getAttribute("account");
		Map<String,Object> map = new HashMap<String,Object>();
		if(account==null){
			map.put("login","login");
			JSONObject jo = JSONObject.fromObject(map);
			this.result  = jo.toString();
			return "success";
		}
		List<Object> list = salaryLevelService.getSalaryUnitsList(suID,account.getCompanyID());

		map.put("unitslist",list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}






	public String getSalarySet(){

		return "toAdd";
	}

	/**
	 *
	 * 预设工资结构
	 * @return
     */
	public String initPreSalary(){
		CAccount account = (CAccount) session.getAttribute("account");
		Map<String,Object> map = new HashMap<String,Object>();
		if(account==null){
			map.put("login","login");
			JSONObject jo = JSONObject.fromObject(map);
			this.result  = jo.toString();
			return "success";
		}
		String r = salaryLevelService.copySalaryStruct(account.getCompanyID(),account.getStaffID());

		map.put("result",r);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}

	/**
	 *
	 * 排序
	 * @return
     */
    public String changeSort(){
		CAccount account = (CAccount) session.getAttribute("account");
		Map<String,Object> map = new HashMap<String,Object>();
		if(account==null){
			map.put("login","login");
			JSONObject jo = JSONObject.fromObject(map);
			this.result  = jo.toString();
			return "success";
		}
		String id1 = request.getParameter("id1");
		String id2 = request.getParameter("id2");
		String r = salaryLevelService.changeSort(id1,id2,account.getStaffID(),account.getCompanyID());

		map.put("result",r);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
		return "success";
	}


    /**
	 *
	 * 添加单元
	 * @return
     */
	public String addSalarySuits(){
		CAccount account = (CAccount) session.getAttribute("account");

		salaryLevelService.addSalaryUnits(salaryUnits,account.getStaffID(),account.getCompanyID());
		request.setAttribute("message","11");
		return "success";

	}

	/**
	 *
	 * 添加项目
	 * @return
     */
	public String addSalaryItems(){
		CAccount account = (CAccount) session.getAttribute("account");
		salaryLevelService.addSalaryItems(salaryItem,account.getStaffID(),account.getCompanyID());
		request.setAttribute("message","11");
		return "success";

	}

	/**
	 *
	 * 修改名称
	 * @return
     */
	public String updateSalary(){
		CAccount account = (CAccount) session.getAttribute("account");
		String id = "";
		String name = "";
		if(salaryItem!=null&&salaryItem.getStID()!=null&&!salaryItem.getStID().equals("")){
			id = salaryItem.getStID();
			name = salaryItem.getItemName();
		}else{
			id = salaryUnits.getSuID();
			name = salaryUnits.getUnitName();
		}

		salaryLevelService.updateSalaryStrutsName(id,name,account.getStaffID(),account.getCompanyID());
		request.setAttribute("message","11");
		return "success";

	}


	public String deleteSalaryStruts(){
		CAccount account = (CAccount) session.getAttribute("account");
		Map<String,Object> map = new HashMap<String,Object>();
		if(account==null){
			map.put("login","login");
			JSONObject jo = JSONObject.fromObject(map);
			this.result  = jo.toString();
			return "success";
		}
		String id = request.getParameter("id");
		String r = salaryLevelService.deleteSalaryStruts(id,account.getStaffID(),account.getCompanyID());

		map.put("result",r);
		JSONObject jo = JSONObject.fromObject(map);
		this.result  = jo.toString();
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

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getSuID() {
		return suID;
	}

	public void setSuID(String suID) {
		this.suID = suID;
	}

	public SalaryItem getSalaryItem() {
		return salaryItem;
	}

	public void setSalaryItem(SalaryItem salaryItem) {
		this.salaryItem = salaryItem;
	}

	public SalaryUnits getSalaryUnits() {
		return salaryUnits;
	}

	public void setSalaryUnits(SalaryUnits salaryUnits) {
		this.salaryUnits = salaryUnits;
	}
}