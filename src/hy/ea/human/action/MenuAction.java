package hy.ea.human.action;


import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.CRole;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.Menu;
import hy.ea.bo.human.MoneyEmpower;
import hy.ea.human.service.MenuService;
import hy.ea.service.CompanyService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 菜单
 */
@Controller
@Scope("prototype")
public class MenuAction {
    @Resource
    private MenuService menuService;

    @Resource
    private CompanyService companyService;

    private ContactCompany contactCompany;

    private String result;

    private Menu menu;

    private MoneyEmpower moneyEmpower;

    private Company company;

    private PageForm pageForm;

    private int pageNumber;

    private int pageSize;

    private CRole role;

    private TEshopCusCom cusCom;

    /**
     * 查询所有菜单数据
     */
    public String getMenuList() {
        List<Menu> list = menuService.getMenuList();
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";
    }

    /**
     * 查询所有菜单数据
     */
    public String getAllMenuList() {
        JSONObject data = menuService.getAllMenuListAndCheckedMenu();
        result = data.toString();
        return "success";
    }

    /**
     * 查询所有菜单数据根据公司ID树形结构
     */
    public String getAllMenuListByCompanyId() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<Menu> data = menuService.getMenuByCompanyId(companyId);
        JSONArray obj = JSONArray.fromObject(data);
        result = obj.toString();
        return "success";
    }
    /**
     * 查询所有菜单数据根据公司ID
     */
    public String getCompanyMenuListByCompanyId() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<Menu> data = menuService.getCompanyMenuListByCompanyId(companyId);
        JSONArray obj = JSONArray.fromObject(data);
        result = obj.toString();
        return "success";
    }


    /**
     * 查询所有菜单数据根据公司ID
     */
    public String getMenuListByCompanyId() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String menuPID = request.getParameter("menuPID");
        List<Menu> data = menuService.getMenuListByCompanyIdAndPID(companyId,menuPID);
        JSONArray obj = JSONArray.fromObject(data);
        result = obj.toString();
        return "success";
    }

    /**
     * 添加菜单数据
     */
    public String createMenu(){
        result = menuService.createMenu();
        return "success";
    }

    /**
     * 修改菜单数据
     *
     * @return
     */
    public String updateMenu() {
        result = menuService.updateMenu();
        return "success";
    }

    /**
     * 删除菜单
     *
     * @return
     */
    public String delMenu() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String menuId = request.getParameter("menuId");
        result = menuService.delMenuByMenuId(menuId);
        return "success";
    }


    /**
     * 根据条件查询数据
     * @return
     */
    public String getMenuByQueryData() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        Map<String,String> data = new HashMap<>();
        String menuPID = request.getParameter("menuPID");
        data.put("menuPID",menuPID);
        String menuType = request.getParameter("menuType");
        data.put("menuType",menuType);
        String notMenuID = request.getParameter("notMenuID");
        data.put("notMenuID",notMenuID);
        List<BaseBean> menuList = menuService.getMenuByQueryData(data);
        JSONArray arr = JSONArray.fromObject(menuList);
        result = arr.toString();
        return "success";
    }

    /**
     * 根据主键查询菜单数据
     *
     * @return
     */
    public String getMenuByKey() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String menuKey = request.getParameter("menuKey");
        menu = menuService.getMenuByMenuKey(menuKey);
        return "bMenuEdit";
    }

    /**
     * 根据类型查询授权数据
     */
    public String getMoneyEmpowerList() {
        List<BaseBean> list = menuService.getMoneyEmpowerList();
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";
    }

    /**
     * 添加权限信息
     * @return
     */
    public String createMoneyEmpower(){
        result = menuService.createMoneyEmpower();
        return "success";
    }

    /**
     * 修改菜单金额授权数据
     *
     * @return
     */
    public String updateMoneyEmpower() {
        result = menuService.updateMoneyEmpower();
        return "success";
    }

    /**
     * 删除金额授权
     *
     * @return
     */
    public String delMoneyEmpower() {
        result = menuService.delMoneyEmpower();
        return "success";
    }

    /**
     * 根据主键查询菜单数据
     *
     * @return
     */
    public String getMoneyEmpowerByKey() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String empowerKey = request.getParameter("empowerKey");
        moneyEmpower = menuService.getMoneyEmpowerByEmpowerKey(empowerKey);
        return "bMoneyEmpowerEdit";
    }
    /**
     * 保存金额权限菜单
     * @return
     */
    public String saveMoneyEmpowerMenu(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String checkedData = request1.getParameter("checkedData");
        String empowerId = request1.getParameter("empowerId");
        result = menuService.saveMoneyEmpowerMenu(checkedData,empowerId);
        return "success";
    }

    /**
     * 根据公司id获取菜单
     * @return
     */
    public String getMenuByCompanyId() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<Menu> data = menuService.getMenuByCompanyId(companyId);
        JSONArray obj = JSONArray.fromObject(data);
        result = obj.toString();
        return "success";
    }
    /**
     * 根据员工id获取菜单
     * @return
     */
    public String getMenuByStaffId(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String staffId = account.getStaffID();
        List<Menu> data = menuService.getMenuByStaffId(staffId);
        JSONArray obj = JSONArray.fromObject(data);
        result = obj.toString();
        return "success";
    }


    /**
     * 查询所有公司数据
     */
    public String getAllCompanyData() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyName = request.getParameter("companyName");
        String ccomType = request.getParameter("ccomType");
        String queryName = request.getParameter("queryName");
        Map<String,Object> param = new HashMap<>();
        param.put("companyName",companyName);
        param.put("ccomType",ccomType);
        param.put("queryName",queryName);
        pageForm = menuService.getAllCompanyData(pageNumber,pageSize,param);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 保存公司菜单
     * @return
     */
    public String saveCompanyMenu(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String checkedData = request1.getParameter("checkedData");
        String companyId = request1.getParameter("companyId");
        result = menuService.saveCompanyMenu(checkedData,companyId);
        menuService.saveOrgByCompanyMenu(companyId);
        return "success";
    }

    /**
     * 获取角色数据
     * @return
     */
    public String getListCRole() {
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        pageForm = menuService.getListCRoleByCompanyId(pageNumber,pageSize,companyId);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }
    /**
     * 保存角色数据
     * @return
     */
    public String saveCRoleData(){
        result = menuService.saveCRoleData();
        return "success";
    }

    /**
     * 保存角色权限菜单
     * @return
     */
    public String saveRoleMenu(){
        result = menuService.saveRoleMenu();
        return "success";
    }

    /**
     * 根据角色id获取role数据
     * @return
     */
    public String getRoleByRoleId(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String roleId = request1.getParameter("roleId");
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        role = menuService.getCRoleByParam(roleId,companyId);
        return "bRoleEdit";
    }

    /**
     * 根据角色id获取角色菜单
     * @return
     */
    public String getRoleMenuByRoleId(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String roleId = request1.getParameter("roleId");
        JSONObject data = menuService.getRoleMenuByRoleId(roleId);
        result = data.toString();
        return "success";
    }

    /**
     * 删除角色数据
     * @return
     */
    public String delCRole() {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String roleId = request1.getParameter("roleId");
        result = menuService.deleteRoleByID(roleId);
        return "success";
    }

    /**
     * 根据父级id获取部门数据
     * @return
     */
    public String getDeptListByDeptPID() {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String organizationPID = request1.getParameter("organizationPID");
        List<BaseBean> list = menuService.getDeptListByDeptPID(organizationPID);
        JSONArray obj = JSONArray.fromObject(list);
        result = obj.toString();
        return "success";

    }

    /**
     * 根据公司id获取公司信息
     * @return
     */
    public String getCompanyDataById(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String companyId = request1.getParameter("companyId");
        company = companyService.getCompanyByCompanyID(companyId);
        contactCompany = menuService.getContactCompanyByCompanyId(companyId);
        cusCom =menuService.getTEshopCusComByCompanyId(companyId);
        Map<String,String> param = new HashMap<>();
        param.put("type","group");
        pageForm = menuService.returnsVisit(pageNumber,pageSize,param,companyId);
        return "companyData";
    }

    /**
     * 获取回访记录
     * @return
     */
    public String getVisitByCompanyId(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String companyId = request1.getParameter("companyId");
        Map<String,String> param = new HashMap<>();
        param.put("type","group");
        pageForm = menuService.returnsVisit(pageNumber,pageSize,param,companyId);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 根据角色id获取人员数据
     * @return
     */
    public String getPersonDataByRoleId(){
        Map<String,String> param = new HashMap<>();
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleId = request.getParameter("roleId");
        String type = request.getParameter("type");
        String staffName = request.getParameter("staffName");
        param.put("type",type);
        param.put("roleId",roleId);
        param.put("staffName",staffName);
        pageForm = menuService.getPersonDataByRoleId(param,pageNumber,pageSize);
        result = JSONObject.fromObject(pageForm).toString();
        return "success";
    }

    /**
     * 保存员工角色
     * @return
     */
    public String saveStaffRole(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleId = request.getParameter("roleId");
        String staffIds = request.getParameter("staffIds");
        result = menuService.saveStaffRole(roleId,staffIds);
        return "success";
    }

    /**
     * 删除角色下的员工
     */
    public String deleteRoleStaffByStaffId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String roleId = request.getParameter("roleId");
        String staffId = request.getParameter("staffId");
        result = menuService.deleteRoleStaffByStaffId(roleId,staffId);
        return "success";
    }

    public String getMenuByParentId() {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String menuId = request.getParameter("menuId");
        List<Menu> data = menuService.getMenuByParentId(companyId, menuId);
        JSONArray obj = JSONArray.fromObject(data);
        result = obj.toString();
        return "success";
    }
    /**
     * 保存公司菜单
     * @return
     */
    public String saveCompanyMenuByEmpowerId(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String empowerId = request1.getParameter("empowerId");
        String companyIds = request1.getParameter("companyIds");
        result = menuService.saveCompanyMenuByEmpowerId(empowerId,companyIds);
        return "success";
    }

    public MoneyEmpower getMoneyEmpower() {
        return moneyEmpower;
    }

    public void setMoneyEmpower(MoneyEmpower moneyEmpower) {
        this.moneyEmpower = moneyEmpower;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public CRole getRole() {
        return role;
    }

    public void setRole(CRole role) {
        this.role = role;
    }

    public ContactCompany getContactCompany() {
        return contactCompany;
    }

    public void setContactCompany(ContactCompany contactCompany) {
        this.contactCompany = contactCompany;
    }

    public MenuService getMenuService() {
        return menuService;
    }

    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public TEshopCusCom getCusCom() {
        return cusCom;
    }

    public void setCusCom(TEshopCusCom cusCom) {
        this.cusCom = cusCom;
    }

}
