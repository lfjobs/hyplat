package hy.ea.human.service.impl;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.*;
import hy.ea.human.dao.MenuDao;
import hy.ea.human.service.MenuService;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.util.DateUtil;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuDao menuDao;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private ServerService serverService;

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private CLogBookService logBookService;
    @Autowired
    private CCodeService ccodeService;

    private String parameter;

    private List<BaseBean> beans;


    @Override
    public List<Menu> getMenuList() {
        return menuDao.getMenuList();
    }

    @Override
    public List<Menu> getAllMenuList() {
        return menuDao.getAllMenuList();
    }

    @Override
    public JSONObject getAllMenuListAndCheckedMenu() {
        JSONObject data = new JSONObject();
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String empowerId = request1.getParameter("empowerId");
        String companyId = request1.getParameter("companyId");
        List<Menu> list = menuDao.getAllMenuList();
        JSONArray obj = JSONArray.fromObject(list);
        JSONArray checkedMenuList = new JSONArray();
        if (!"".equals(empowerId)) {
            List<BaseBean> checkedList = menuDao.getCheckedMenuByEmpowerId(empowerId);
            checkedMenuList = JSONArray.fromObject(checkedList);
        }
        if (!"".equals(companyId)) {
            List<Menu> checkedList = menuDao.getMenuByCompanyId(companyId);
            checkedMenuList = JSONArray.fromObject(checkedList);
        }

        data.put("menuList",obj);
        data.put("checkedMenuList",checkedMenuList);
        return data;
    }

    @Override
    public String createMenu() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String menuPID = request.getParameter("menuPID")== null ? "" : request.getParameter("menuPID");
        String menuName = request.getParameter("menuName");
        String menuURL = request.getParameter("menuURL");
        String menuMark = request.getParameter("menuMark");
        String menuDesc = request.getParameter("menuDesc");
        Integer menuType = Integer.parseInt(request.getParameter("menuType"));
        int menuLevel = 0;
        String menuPIDList = "";
        if (menuPID == null || "".equals(menuPID)){
            menuLevel = 1;
            menuPID = "";
        } else {
            Menu pInfo = menuDao.getMenuByMenuId(menuPID);
            menuLevel = pInfo.getMenuLevel() + 1;
            menuPIDList = pInfo.getMenuPIDList() + menuPID;
        }
        int sortNum = menuDao.getMaxSortNumByMenuLevel(menuLevel,menuPID);

        //根据编号校验数据是否已经存在
        if (!menuDao.isExistMenuName(menuName,menuPID)) {
            return "exist";
        }
        Menu menu = new Menu();
        menu.setMenuId(serverService.getServerID("bMenu"));
        menu.setMenuPID(menuPID);
        menu.setMenuName(menuName);
        menu.setMenuURL(menuURL);
        menu.setMenuMark(menuMark);
        menu.setMenuLevel(menuLevel);
        menu.setMenuType(menuType);
        menu.setSortNum(sortNum + 1);
        menu.setStatus(1);
        menu.setMenuDesc(menuDesc);
        menu.setMenuPIDList(menuPIDList);
        menuDao.createBaseBean(menu);
        return "success";
    }

    @Override
    public String delMenuByMenuId(String menuId) {
        if (StringUtil.isEmpty(menuId)) {
            return "noId";
        }
        Menu info = menuDao.getMenuByMenuId(menuId);
        Map<String,String> data = new HashMap<>();
        String menuPID = info.getMenuId();
        if (menuPID != null && !("".equals(menuPID))){
            data.put("menuPID",menuPID);
        }
        List<BaseBean> childList = getMenuByQueryData(data);
        if (!CollectionUtils.isEmpty(childList)) {
            return "no";
        }

        info.setStatus(0);
        menuDao.updateBaseBean(info);
        return "success";
    }

    @Override
    public String updateMenu() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String menuKey = request.getParameter("menuKey");
        String menuPID = request.getParameter("menuPID");
        String menuId = request.getParameter("menuId");
        String menuName = request.getParameter("menuName");
        String menuURL = request.getParameter("menuURL");
        String menuMark = request.getParameter("menuMark");
        String menuDesc = request.getParameter("menuDesc");
        Integer menuType = Integer.parseInt(request.getParameter("menuType"));
        String oldMenuName = request.getParameter("oldMenuName");
        String oldMenuPID = request.getParameter("oldMenuPID");
        Integer sortNum = Integer.parseInt(request.getParameter("sortNum"));
        if (!oldMenuName.equals(menuName)) {
            //根据名称校验同一级别名称是否重复
            if (!menuDao.isExistMenuName(menuName,menuPID)) {
                return "exist";
            }
        }
        Menu menu = menuDao.getMenuByMenuKey(menuKey);
        String menuPIDList = "";
        if (!oldMenuPID.equals(menuPID)){
            Menu menuPInfo = menuDao.getMenuByMenuId(menuPID);
            int menuLevel = menuPInfo.getMenuLevel() + 1;
            menu.setMenuLevel(menuLevel);
            sortNum = sortNum + 1;
            menuPIDList = menuPInfo.getMenuPIDList() + menuPID;
            updateMenuLevel(menuId,menuLevel);
        }
        menu.setMenuPID(menuPID);
        menu.setMenuName(menuName);
        menu.setMenuURL(menuURL);
        menu.setMenuMark(menuMark);
        menu.setMenuDesc(menuDesc);
        menu.setMenuType(menuType);
        menu.setMenuPIDList(menuPIDList);
        menu.setSortNum(sortNum);
        menuDao.updateBaseBean(menu);
        return "success";
    }

    @Override
    public List<BaseBean> getMenuByQueryData(Map<String,String> data) {
        StringBuffer sql = new StringBuffer(" from Menu o where o.status=1 ");
        List<Object> params = new ArrayList<>();
        String menuPID = data.get("menuPID");
        if (menuPID != null && !("".equals(menuPID))){
            sql.append(" and o.menuPID = ?");
            params.add(menuPID);
        }
        String menuType = data.get("menuType");
        if (menuType != null && !("".equals(menuType))){
            sql.append(" and menuType = ?");
            params.add(Integer.parseInt(menuType));
        }
        String notMenuID = data.get("notMenuID");
        if (notMenuID != null && !("".equals(notMenuID))){
            sql.append(" and menuId != ?");
            params.add(notMenuID);
        }
        sql.append(" order by o.sortNum ");
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params.toArray());
        return list;
    }

    @Override
    public Menu getMenuByMenuKey(String menuKey) {
        Menu menu = menuDao.getMenuByMenuKey(menuKey);
        String menuPID = menu.getMenuPID();
        String menuParentName = "";
        if (!"".equals(menuPID) && menuPID != null) {
            Menu pInfo = menuDao.getMenuByMenuId(menu.getMenuPID());
            menuParentName = pInfo.getMenuName();
        }
        menu.setMenuParentName(menuParentName);
        return menu;
    }

    @Override
    public List<BaseBean> getMoneyEmpowerList() {
        return menuDao.getMoneyEmpowerList();
    }

    @Override
    public String createMoneyEmpower() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String empowerName = request.getParameter("empowerName");
        String empowerDesc = request.getParameter("empowerDesc");
        String ccomType = request.getParameter("ccomType");

        //根据名称校验数据是否已经存在
        if (!menuDao.isExistEmpowerName(empowerName)) {
            return "existName";
        }
        //根据企业类型校验数据是否已经存在
        if (!menuDao.isExistEmpowerType(ccomType)) {
            return "existType";
        }
        MoneyEmpower moneyEmpower = new MoneyEmpower();
        moneyEmpower.setEmpowerId(serverService.getServerID("bMoney"));
        moneyEmpower.setEmpowerName(empowerName);
        moneyEmpower.setCcomType(ccomType);
        moneyEmpower.setEmpowerDesc(empowerDesc);
        moneyEmpower.setStatus(1);
        moneyEmpower.setCreateTime(DateUtil.getCurrDate());
        menuDao.createBaseBean(moneyEmpower);
        return "success";
    }

    @Override
    public String updateMoneyEmpower() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String empowerKey = request.getParameter("empowerKey");
        String empowerName = request.getParameter("empowerName");
        String empowerDesc = request.getParameter("empowerDesc");
        String oldEmpowerName = request.getParameter("oldEmpowerName");
        String ccomType = request.getParameter("ccomType");
        String oldCcomType = request.getParameter("oldCcomType");
        if (!oldEmpowerName.equals(empowerName)) {
            //根据名称校验同一级别名称是否重复
            if (!menuDao.isExistEmpowerName(empowerName)) {
                return "existName";
            }
        }
        if (!oldCcomType.equals(ccomType)) {
            //根据名称校验同一级别名称是否重复
            if (!menuDao.isExistEmpowerType(ccomType)) {
                return "existType";
            }
        }
        MoneyEmpower moneyEmpower = menuDao.getMoneyEmpowerByEmpowerKey(empowerKey);
        moneyEmpower.setEmpowerName(empowerName);
        moneyEmpower.setEmpowerDesc(empowerDesc);
        moneyEmpower.setCcomType(ccomType);
        menuDao.updateBaseBean(moneyEmpower);
        return "success";
    }

    @Override
    public String delMoneyEmpower() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                .get(ServletActionContext.HTTP_REQUEST);
        String empowerKey = request.getParameter("empowerKey");

        if (StringUtil.isEmpty(empowerKey)) {
            return "noKey";
        }
        MoneyEmpower info = menuDao.getMoneyEmpowerByEmpowerKey(empowerKey);
        info.setStatus(0);
        menuDao.updateBaseBean(info);
        return "success";
    }

    @Override
    public MoneyEmpower getMoneyEmpowerByEmpowerKey(String empowerKey) {
        return menuDao.getMoneyEmpowerByEmpowerKey(empowerKey);
    }

    @Override
    public String saveMoneyEmpowerMenu(String checkedData,String empowerId) {
        List<String> menuIdList = Arrays.asList(checkedData.split("\\,"));
        MoneyEmpowerMenu moneyEmpowerMenu;
        String sql = "delete from MoneyEmpowerMenu where empowerId = ? ";
        List<Object[]> parms = new ArrayList<>();
        parms.add(new Object[]{empowerId});
        baseBeanService.executeHqlsByParamsList(null, new String[]{sql}, parms);
        List<BaseBean> baseBeansList = new ArrayList<>();
        for (String menuId : menuIdList ){
            moneyEmpowerMenu = new MoneyEmpowerMenu();
            moneyEmpowerMenu.setEmpowerId(empowerId);
            moneyEmpowerMenu.setMenuId(menuId);
            baseBeansList.add(moneyEmpowerMenu);
        }

        menuDao.saveBaseBean(baseBeansList);
        return "success";
    }


    @Override
    public List<Menu> getMenuByCompanyId(String companyId) {
        List<Menu> list = menuDao.getMenuByCompanyId(companyId);
        return getTreeData(list);
    }
    @Override
    public List<Menu> getCompanyMenuListByCompanyId(String companyId) {
        return menuDao.getMenuByCompanyId(companyId);
    }
    @Override
    public List<Menu> getMenuListByCompanyIdAndPID(String companyId, String menuPID) {
        return menuDao.getMenuListByCompanyIdAndPID(companyId,menuPID);
    }

    @Override
    public List<Menu> getMenuByStaffId(String staffId) {
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<BaseBean> staffRoleList = menuDao.getStaffRoleByStaffId(staffId,companyId);
        int staffRoleSize = staffRoleList.size();
        if (staffRoleSize > 0){
            return getTreeData(menuDao.getMenuByRoleList(staffRoleList,companyId));
        } else {
            //查询是否为注册改公司人员
            TEshopCusCom cus = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams("from TEshopCusCom where companyId=? ", new Object[]{companyId});
            if (staffId.equals(cus.getStaffid())){
                return getRoleMenuByStaffId(companyId,account.getStaffID());
            } else {
               return new ArrayList<>();
            }
        }

    }

    @Override
    public PageForm getAllCompanyData(Integer pageNumber,Integer pageSize,Map<String,Object> param) {
        String companyName = (String) param.get("companyName");
        String ccomType = (String) param.get("ccomType");
        String queryName = (String) param.get("queryName");
        StringBuffer sql = new StringBuffer(128);
        sql.append(" SELECT C.COMPANYID,C.ccomtype,C.COMPANYNAME FROM DTCOMPANY C ,DT_ccom_com d WHERE C.COMPANYID= D.compnay_id ");
        List<Object> data = new ArrayList<>();
        if (!"".equals(companyName) && companyName != null){
            sql.append(" and c.companyName like ?");
            data.add("%" + companyName + "%");
        }
        if (!"".equals(ccomType) && ccomType != null){
            sql.append(" and c.ccomtype = ?");
            data.add(ccomType);
        }
        if (!"".equals(queryName) && queryName != null){
            sql.append(" and c.companyName like ?");
            data.add("%" + queryName + "%");
        }
        String hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, data.toArray());
        return pageForm;
    }

    @Override
    public String saveCompanyMenu(String checkedData,String companyId) {
        List<String> menuIdList = Arrays.asList(checkedData.split("\\,"));
        menuDao.deleteCompanyMenuById(companyId);
        CompanyMenu companyMenu;
        List<BaseBean> baseBeansList = new ArrayList<>();
        for (String menuId : menuIdList ){
            companyMenu = new CompanyMenu();
            companyMenu.setCompanyId(companyId);
            companyMenu.setMenuId(menuId);
            baseBeansList.add(companyMenu);
        }
        menuDao.saveBaseBean(baseBeansList);

        return "success";
    }

    @Override
    public String saveOrgByCompanyMenu(String companyId) {
        menuDao.saveOrgByCompanyMenu(companyId);
        return "success";
    }

    @Override
    public PageForm getListCRoleByCompanyId(Integer pageNumber,Integer pageSize,String companyId) {
        return menuDao.getListCRoleByCompayId(companyId,pageNumber,pageSize);
    }

    @Override
    public String saveCRoleData(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String type = request1.getParameter("type");
        String roleName = request1.getParameter("roleName");
        String organizationName = request1.getParameter("organizationName");
        String organizationNameDesc = request1.getParameter("organizationNameDesc");
        String oldRoleName = request1.getParameter("oldRoleName");
        String roleDesc = request1.getParameter("roleDesc");
        String roleId = request1.getParameter("roleId");
        String roleKey = request1.getParameter("roleKey");
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        //根据名称校验数据是否已经存在
        Boolean bool = ("add".equals(type)) || ("edit".equals(type) && !oldRoleName.equals(roleName) );
        if (bool){
            if (!menuDao.isExistRoleName(roleName,companyId)) {
                return "existName";
            }
        }
        CRole role = new CRole();
        if ("add".equals(type)){
            roleId = serverService.getServerID("crole");
            parameter = "添加角色";
            parameter += "(角色名称:" + roleName + ")";
        } else{
            parameter = "修改角色";
            parameter += "(角色名称:" + roleName + ")";
            role.setRoleKey(roleKey);
        }
        role.setRoleID(roleId);
        role.setCompanyID(companyId);
        role.setRoleName(roleName);
        role.setRoleDesc(roleDesc);
        role.setOrganizationName(organizationName);
        role.setOrganizationNameDesc(organizationNameDesc);
        role.setCreateDate(new Date());
        CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
        beans = new ArrayList<>();
        beans.add(role);
        beans.add(logBook);
        baseBeanService.executeHqlsByParamsList(beans, null, null);
        return "success";
    }

    @Override
    public String saveRoleMenu() {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String checkedData = request1.getParameter("checkedData");
        String roleId = request1.getParameter("roleId");
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        // 保存角色菜单数据
        List<String> menuIdList = Arrays.asList(checkedData.split("\\,"));
        RoleMenu roleMenu;
        String sql = "delete from RoleMenu where roleId = ? and companyId = ? ";
        List<Object[]> parms = new ArrayList<>();
        parms.add(new Object[]{roleId,companyId});
        baseBeanService.executeHqlsByParamsList(null, new String[]{sql}, parms);
        List<BaseBean> baseBeansList = new ArrayList<>();
        for (String menuId : menuIdList ){
            roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setCompanyId(companyId);
            roleMenu.setMenuId(menuId);
            baseBeansList.add(roleMenu);
        }
        menuDao.saveBaseBean(baseBeansList);
        return "success";
    }

    @Override
    public CRole getCRoleByParam(String roleId, String companyId) {
        return menuDao.getCRoleByParam(roleId,companyId);
    }

    @Override
    public JSONObject getRoleMenuByRoleId(String roleId) {
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<BaseBean> checkedList = menuDao.getCRoleMenuByParam(roleId,companyId);
        JSONArray checkedMenuList = JSONArray.fromObject(checkedList);
        List<Menu> companyMenuData = menuDao.getMenuByCompanyId(companyId);
        JSONArray companyMenuList = JSONArray.fromObject(companyMenuData);
        JSONObject data = new JSONObject();
        data.put("checkedMenuList",checkedMenuList);
        data.put("companyMenuList",companyMenuList);
        return data;
    }


    @Override
    public String deleteRoleByID(String roleId) {
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        Object[] param = {companyId,roleId};
        String hql = "from StaffRole  where companyId = ? and roleId = ?";
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, param);
        if(list!=null && list.size()!=0){
            return "existAccount";
        }else{
            CLogBook logBook = logBookService.saveCLogBook(null,"删除角色(角色名称："+ menuDao.getCRoleByParam(roleId,companyId).getRoleName()
                    +")", account);
            menuDao.deleteRoleByID(companyId,roleId,logBook);
        }
        return "success";
    }

    @Override
    public List<BaseBean> getDeptListByDeptPID(String organizationPID) {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId =  account.getCompanyID();
        if ("".equals(organizationPID)){
            organizationPID = companyId;
        }
        return menuDao.getDeptListByDeptPID(companyId,organizationPID);
    }

    @Override
    public ContactCompany getContactCompanyByCompanyId(String companyId) {
        return (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany t where t.ccompanyID  = (select m.ccompanyId from CcomCom m where m.comanyId = ? AND m.ccompanyId IS NOT NULL)",
                new Object[] { companyId});
    }

    @Override
    public TEshopCusCom getTEshopCusComByCompanyId(String companyId) {
        return (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where companyId = ? ", new Object[]{companyId});
    }

    @Override
    public PageForm returnsVisit(Integer pageNumber,Integer pageSize,Map<String,String> param,String companyId) {
        String search = param.get("search") == null ? "" : param.get("search");
        String type = param.get("type") == null ? "" : param.get("type");
        PageForm pageForm = new PageForm();
        if (search == null || search.length() < 1) {
            String Obj = " TelOutRecord as s ";
            String where = " order by s.visitedTime desc ";
            Object[] obj = null;
            if (type != null && type.equals("group")) {
                where = "and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.visitedTime desc";
                obj = new Object[] { companyId, companyId};
            }
            pageForm = getDataByCustmerType(Obj,where, obj,type,pageNumber,pageSize,companyId);
        } else {
            StringBuilder buffer = new StringBuilder();
            String returnVisits = param.get("returnVisits") == null ? "" : param.get("returnVisits");
            String begin = param.get("beginDate") == null ? "" : param.get("beginDate");
            String end = param.get("endDate") == null ? "" : param.get("endDate");

            List<String> list = new ArrayList<String>();
            if (null != returnVisits && returnVisits.toString().length() > 0) {
                buffer.append("  and telType = ? ");
                list.add(returnVisits.toString());
            }
            // 日期
            Date from = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
                    null == begin ? null : begin.toString());
            Date to = DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT,
                    null == end ? null : end.toString());
            if (from != null && to != null) {
                if (from.compareTo(to) > 0) {
                    return pageForm;
                }
            }

            if (from != null) {
                buffer
                        .append(" and visitedtime > to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
                list.add(begin.toString());
            }

            if (to != null) {
                buffer.append(" and visitedtime < to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
                list.add(end.toString());
            }
            if (type != null && type.equals("group")) {
                buffer.append(" and s.company in(select c.companyID from Company c where c.companyID = ? or c.companyPID = ?) order by s.visitedTime desc");

                list.add(companyId);
                list.add(companyId);

            } else {
                buffer.append(" order by s.visitedTime desc ");
            }

            pageForm = getDataByCustmerType("TelOutRecord as s ", buffer.toString(), list.toArray(),type,pageNumber,pageSize,companyId);
        }

        return pageForm;
    }

    @Override
    public PageForm getPersonDataByRoleId(Map<String,String> param,Integer pageNumber, Integer pageSize) {
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        String companyId =  account.getCompanyID();
        String type = param.get("type");
        if (type == null || "".equals(type)){
            return menuDao.getPersonDataByRoleId(companyId,param,pageNumber,pageSize);
        } else {
            return menuDao.getPersonDataByNotRoleId(companyId,param,pageNumber,pageSize);
        }

    }

    @Override
    public String saveStaffRole(String roleId, String staffIds) {
        List<String> param = new ArrayList<>();
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        // 保存角色菜单数据
        List<String> staffList = Arrays.asList(staffIds.split("\\|"));
        StringBuffer sql = new StringBuffer(128);
        int staffSize = staffList.size();
        if (staffSize > 0){
            sql.append("delete from StaffRole where roleId = ? and companyId = ? and staffId in (");
            param.add(roleId);
            param.add(companyId);
            for (int i = 0; i < staffSize; i++) {
                String staffId = staffList.get(i).toString();
                if (!"".equals(staffId)){
                    sql.append("?");
                    param.add(staffId);
                    if (i == staffList.size() - 1) {
                        sql.append(" )");
                    } else if (i > 0){
                        sql.append(" ,");
                    }

                }


            }
            List<Object[]> paramArr = new ArrayList<>();
            paramArr.add(param.toArray());
            baseBeanService.executeHqlsByParamsList(null, new String[]{sql.toString()}, paramArr);
        }

        List<BaseBean> baseBeansList = new ArrayList<>();
        StaffRole staffRole ;
        for (String staffId : staffList ){
            staffRole = new StaffRole();
            if (!"".equals(staffId)){
                staffRole.setRoleId(roleId);
                staffRole.setCompanyId(companyId);
                staffRole.setStaffId(staffId);
                baseBeansList.add(staffRole);
            }
        }
        menuDao.saveBaseBean(baseBeansList);
        return "success";
    }

    @Override
    public String deleteRoleStaffByStaffId(String roleId, String staffId) {
        CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
        String companyId = account.getCompanyID();
        List<Object[]> params = new ArrayList<>();
        params.add(new Object[]{companyId,roleId,staffId});
        StringBuffer sql = new StringBuffer(" delete from StaffRole  where companyId = ? and roleId = ? and staffId = ?  ");
        baseBeanService.executeHqlsByParamsList(null, new String[]{sql.toString()}, params);
        return "success";
    }

    /**
     * 根据角色获取菜单数据
     * @param companyId
     * @param staffId
     * @return
     */
    private List<Menu> getRoleMenuByStaffId(String companyId,String staffId){
        CRole role = menuDao.getCRoleByRoleName("管理员",companyId);
        if (role == null){
            role = new CRole();
            role.setRoleID(serverService.getServerID("crole"));
            role.setCompanyID(companyId);
            role.setRoleName("管理员");
            role.setRoleDesc("管理员");
            sessionFactory.getCurrentSession().persist(role);
        }
        StaffRole staffRole = new StaffRole();
        staffRole.setStaffId(staffId);
        staffRole.setRoleId(role.getRoleID());
        staffRole.setCompanyId(companyId);
        sessionFactory.getCurrentSession().persist(staffRole);
        List<Menu> menuList = menuDao.getMenuByCompanyId(companyId);
        //角色菜单
        RoleMenu roleMenu;
        List<BaseBean> dataList = new ArrayList<>();
        for (Menu baseBean : menuList ){
            roleMenu = new RoleMenu();
            roleMenu.setRoleId(role.getRoleID());
            roleMenu.setCompanyId(companyId);
            roleMenu.setMenuId(baseBean.getMenuId());
            dataList.add(roleMenu);
        }
        baseBeanDao.executeSqlsByParmsList(dataList,null,null);
        return getTreeData(menuList);
    }
    /**
     * 构建分页
     *
     * @param Obj
     *            查询所属对象
     * @param where
     *            更多条件
     * @param obj
     *            where参数列表
     */
    private PageForm getDataByCustmerType(String Obj, String where, Object[] obj,String type,Integer pageNumber,Integer pageSize,String companyId) {
        List<CCode> codeList = getCCodeList(companyId);

        String hql = " from @@ where s.company = ? ### "
                .replace("@@", Obj);

        List<Object> list = new ArrayList<Object>();
        if (type == null || type.equals("null") || type.equals("")) {
            list.add(companyId);
        }
        if(type != null && type.equals("customer")){ //个人客户呼叫信息中心
            list.add(companyId);
            hql = " from @@ where s.company = ?  ### "
                    .replace("@@", Obj);
        }

        if (where != null && where.length() > 0) {
            hql = hql.replace("###", where);

            if (obj != null) {
                for (Object item : obj) {
                    list.add(item);
                }
                obj = null;
            }
        } else {
            hql = hql.replace("###", "");
        }

        if (type != null && type.equals("group")) {
            hql = hql.replace("s.company = ? and", "");
            hql = hql.replace("s.company = ?   and","");
            hql = hql.replace("s.company = ?  and","");
        }
        PageForm pageForm = baseBeanService.getPageForm((pageNumber == 0 ? 1 : pageNumber), pageSize == 0 ? 5 : pageSize, hql,
                list.toArray());
        if (pageForm == null) {
            pageForm = new PageForm();
        }
        return pageForm;
    }
    /**
     * 获取电话通话类型
     *
     * @param companyID
     */
    private List<CCode>  getCCodeList(String companyID) {
        return ccodeService.getCCodeListByPID(companyID,
                "scode20120511cyyypnpchw0000000002");
    }
    public static List<Menu> getTreeData(List<Menu> list) {

        List<Menu> tree = new LinkedList<>();
        if(null != list && !list.isEmpty()){
            for(Menu menu: list){
                addToTreeNodeList(list, menu);
            }

            for (Menu nodeVo: list){
                if(null == nodeVo.getMenuPID()){
                    tree.add(nodeVo);
                }
            }
        }
        return tree;
    }

    private static void addToTreeNodeList(List<Menu> menuList, Menu menu){
        if(null != menuList && !menuList.isEmpty()){
            for(Menu parentMenu: menuList){
                if (parentMenu.getChildren() == null){
                    parentMenu.setChildren(new ArrayList<>());
                }
                if(parentMenu.getMenuId().equals(menu.getMenuPID())){
                    List<Menu> children = parentMenu.getChildren();
                    boolean flag = true;
                    for(Menu node: children){
                        if(menu.getMenuId().equals(node.getMenuId())){
                            flag = false;
                        }
                    }
                    if(flag){
                        children.add(menu);
                    }
                    break;
                }else{

                    addToTreeNodeList(parentMenu.getChildren(), menu);
                }
            }
        }
    }
    public List<Menu> getListBeanByHqlAndParams(String hql, Object[] params) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (params != null && params.length > 0) {
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
        }
        return query.list();
    }

    @Override
    public List<Menu> getMenuByParentId(String companyId, String menuPid) {
        List<Menu> list = menuDao.getMenuByParentId(companyId, menuPid);
        return list;
    }

    @Override
    public String saveCompanyMenuByEmpowerId(String empowerId, String companyIds) {
        List<String> companyIdList = Arrays.asList(companyIds.split("\\,"));
        menuDao.deleteCompanyMenuByCompanyIdList(companyIdList);
        menuDao.saveCompanyMenuByEmpowerId(empowerId,companyIdList);
        return "success";
    }

    public void updateMenuLevel(String menuIds,Integer menuLevel){
        String[] menuIdArr =  menuIds.split(",");
        int length = menuIdArr.length;
        StringBuilder sql = new StringBuilder(128);
        List<String> parms = new ArrayList<>();
        Menu menu;
        if (length > 0){
            sql.append(" FROM Menu where menuPID IN (");
            String menuIdSql = "";

            for (int i = 0; i < length; i++){
                if (i > 0){
                    menuIdSql += ",";
                }
                menuIdSql += "?";
                parms.add(menuIdArr[i]);
                if (i == length-1 ){
                    menuIdSql += ")";
                }
            }
            sql.append(menuIdSql);
            List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), parms.toArray());
            if (!list.isEmpty()){
                menuIds = "";
                sql.setLength(0);
                sql.append("UPDATE Menu set menuLevel = menuLevel + " + menuLevel + " where menuPID IN (").append(menuIdSql);
                for(BaseBean baseBean : list){
                    menu = (Menu)baseBean;
                    String menuId = menu.getMenuId();
                    menuIds += "," + menuId;
                }
                menuIds = menuIds.substring(1);
                baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{sql.toString()}, parms.toArray());
                updateMenuLevel(menuIds,menuLevel+1);
            }
        }


    }
}