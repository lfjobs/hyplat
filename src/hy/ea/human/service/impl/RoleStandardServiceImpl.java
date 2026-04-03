package hy.ea.human.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;
import hy.ea.bo.human.*;
import hy.ea.human.dao.RoleStandardDao;
import hy.ea.human.service.MenuService;
import hy.ea.human.service.RoleStandardService;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class RoleStandardServiceImpl implements RoleStandardService {

    @Resource
    private RoleStandardDao roleStandardDao;

    private String parameter;

    @Resource
    private CLogBookService logBookService;

    @Resource
    private BaseBeanService baseBeanService;

    private List<BaseBean> beans;

    @Resource
    private MenuService menuService;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Resource
    private ServerService serverService;

    @Override
    public List<RoleStandard> getRoleStandardList() {
        return roleStandardDao.getRoleStandardList();
    }

    @Override
    public String saveRoleStandardData() {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String type = request1.getParameter("type");
        String roleName = request1.getParameter("roleName");
        String roleDesc = request1.getParameter("roleDesc");
        String roleId = request1.getParameter("roleId");
        String oldRoleName = request1.getParameter("oldRoleName");
        //根据名称校验数据是否已经存在
        Boolean bool = ("add".equals(type)) || ("edit".equals(type) && !oldRoleName.equals(roleName) );
        if (bool){
            if (!roleStandardDao.isExistRoleName(roleName)) {
                return "existName";
            }
        }
        RoleStandard role = new RoleStandard();
        if ("add".equals(type)){
            parameter = "添加角色";
            parameter += "(角色名称:" + roleName + ")";
            role.setRoleId(serverService.getServerID("crole"));
            role.setCreateDate(new Date());
            role.setCreateStaffId(account.getStaffID());
        } else{
            role = getRoleStandardByRoleId(roleId);
            parameter = "修改角色";
            parameter += "(角色名称:" + roleName + ")";
        }
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setUpdateDate(new Date());
        CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
        beans = new ArrayList<>();
        beans.add(role);
        beans.add(logBook);
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        return "success";
    }

    @Override
    public RoleStandard getRoleStandardByRoleId(String roleId) {
        return roleStandardDao.getRoleStandardByRoleId(roleId);
    }

    @Override
    public String deleteRoleStandardByRoleId(String roleId) {
        return roleStandardDao.deleteRoleStandardByRoleId(roleId);
    }

    @Override
    public JSONObject getRoleMenuByRoleId(String roleId) {
        List<Menu> menuList = menuService.getAllMenuList();
        JSONArray menuListArray = JSONArray.fromObject(menuList);
        List<BaseBean> checkedList = roleStandardDao.getRoleStandardMenuByRoleId(roleId);
        JSONArray checkedMenuList = JSONArray.fromObject(checkedList);
        JSONObject data = new JSONObject();
        data.put("checkedMenuList",checkedMenuList);
        data.put("menuList",menuListArray);
        return data;
    }

    @Override
    public String saveRoleMenu() {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String checkedData = request1.getParameter("checkedData");
        String roleId = request1.getParameter("roleId");
        // 保存标准化角色菜单数据
        List<String> menuIdList = Arrays.asList(checkedData.split("\\,"));
        RoleStandardMenu roleMenu;
        String sql = "delete from RoleStandardMenu where roleId = ? ";
        List<Object[]> params = new ArrayList<>();
        params.add(new Object[]{roleId});
        baseBeanService.executeHqlsByParamsList(null, new String[]{sql}, params);
        List<BaseBean> baseBeansList = new ArrayList<>();
        for (String menuId : menuIdList ){
            roleMenu = new RoleStandardMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            baseBeansList.add(roleMenu);
        }
        baseBeanDao.executeSqlsByParmsList(baseBeansList,null,null);
        return "success";
    }

    @Override
    public List<BaseBean> getRoleStandardByEmpowerId(Map<String, String> param) {
        String type = param.get("type");
        String empowerId = param.get("empowerId");
        if (type == null || "".equals(type)){
            return roleStandardDao.getRoleStandardByEmpowerId(empowerId);
        } else {
            return roleStandardDao.getRoleStandardByNotEmpowerId(empowerId);
        }
    }

    @Override
    public String deleteEmpowerRoleDataByRoleId(String empowerId, String roleId) {
        return roleStandardDao.deleteEmpowerRoleDataByRoleId(empowerId,roleId);
    }

    @Override
    public String saveEmpowerRole(String empowerId, String roleIds) {
        List<String> roleIdList = Arrays.asList(roleIds.split("\\|"));
        List<BaseBean> baseBeansList = new ArrayList<>();
        MoneyEmpowerRole empowerRole ;
        for (String roleId : roleIdList ){
            empowerRole = new MoneyEmpowerRole();
            if (!"".equals(roleId)){
                empowerRole.setEmpowerId(empowerId);
                empowerRole.setRoleId(roleId);
                baseBeansList.add(empowerRole);
            }
        }
        baseBeanDao.executeSqlsByParmsList(baseBeansList,null,null);
        return "success";
    }

    @Override
    public String saveRoleMenuStandardByCompanyId(String companyId) {
        MoneyEmpower moneyEmpower = roleStandardDao.getMoneyEmpowerByCompanyId(companyId);
        String empowerId = moneyEmpower.getEmpowerId();
        List<BaseBean> list = roleStandardDao.getRoleStandardByEmpowerId(empowerId);
        List<BaseBean> dataList = new ArrayList<>();
        CRole crole;
        RoleStandard roleStandard;
        for(BaseBean baseBean : list){
            roleStandard = (RoleStandard) baseBean;
            crole = new CRole();
            crole.setRoleID(roleStandard.getRoleId());
            crole.setCompanyID(companyId);
            crole.setRoleName(roleStandard.getRoleName());
            crole.setRoleDesc(roleStandard.getRoleDesc());
            crole.setCreateDate(new Date());
            dataList.add(crole);
        }
        baseBeanDao.executeSqlsByParmsList(dataList,null,null);
        list = roleStandardDao.getRoleStandardMenuByEmpowerId(empowerId);
        dataList = new ArrayList<>();
        RoleMenu roleMenu;
        RoleStandardMenu roleStandardMenu;
        for (BaseBean baseBean : list ){
            roleStandardMenu = (RoleStandardMenu) baseBean;
            roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleStandardMenu.getRoleId());
            roleMenu.setCompanyId(companyId);
            roleMenu.setMenuId(roleStandardMenu.getMenuId());
            dataList.add(roleMenu);
        }
        baseBeanDao.executeSqlsByParmsList(dataList,null,null);
        return "success";
    }
}