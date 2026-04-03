package hy.ea.human.dao.impl;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;
import hy.ea.bo.DictData;
import hy.ea.bo.human.*;
import hy.ea.human.dao.MenuDao;
import hy.ea.service.DictDataService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuDaoImpl implements MenuDao {

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private DictDataService dictDataService;

    @Override
    public List<Menu> getMenuList() {
        StringBuffer sql = new StringBuffer(" from Menu o where o.status=1 and o.menuLevel=1  order by sortNum");
        List<Menu> list = this.getListBeanByHqlAndParams(sql.toString(), null);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Menu> getAllMenuList() {
        StringBuffer sql = new StringBuffer(" from Menu o where o.status=1  order by menuLevel,menuType,sortNum");
        List<Menu> list = this.getListBeanByHqlAndParams(sql.toString(), null);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public Menu getMenuByMenuId(String menuId) {
        StringBuffer sql = new StringBuffer(" from Menu o where o.status=1 and o.menuId=? ");
        List<Object> params = new ArrayList<>();
        params.add(menuId);
        Menu info = (Menu) baseBeanDao.getBeanByHqlAndParams(sql.toString(), params.toArray());
        return info;
    }

    @Override
    public int getMaxSortNumByMenuLevel(Integer menuLevel, String menuPID) {
        StringBuffer sql = new StringBuffer(" select nvl(max(sortNum),0) from Menu o where o.menuLevel=?  ");
        List<Object> params = new ArrayList<>();
        params.add(menuLevel);
        if (!"".equals(menuPID)) {
            sql.append(" and menuPID=?");
            params.add(menuPID);
        }
        return baseBeanDao.getConutByByHqlAndParams(sql.toString(), params.toArray());
    }

    @Override
    public boolean isExistMenuName(String menuName, String menuPID) {
        StringBuffer sql = new StringBuffer(" from Menu o where o.status=1 and o.menuName=? ");
        List<Object> params = new ArrayList<>();
        params.add(menuName);
        if (menuPID != null && !"".equals(menuPID) && !"-1".equals(menuPID)) {
            sql.append(" and o.menuPID = ?");
            params.add(menuPID);
        }
        List<Menu> list = this.getListBeanByHqlAndParams(sql.toString(), params.toArray());
        return CollectionUtils.isEmpty(list) ? true : false;
    }


    @Override
    public Menu getMenuByMenuKey(String menuKey) {
        return (Menu) baseBeanDao.getBeanByKey(Menu.class, menuKey);
    }

    @Override
    public List<BaseBean> getMoneyEmpowerList() {
        StringBuffer sql = new StringBuffer(" from MoneyEmpower o where o.status=1   order by createTime");
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), null);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public boolean isExistEmpowerName(String empowerName) {
        StringBuffer sql = new StringBuffer(" from MoneyEmpower o where o.status=1 and o.empowerName=? ");
        Object[] params = {empowerName};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? true : false;
    }

    @Override
    public boolean isExistEmpowerType(String ccomType) {
        StringBuffer sql = new StringBuffer(" from MoneyEmpower o where o.status=1 and o.ccomType=? ");
        Object[] params = {ccomType};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? true : false;
    }

    @Override
    public void saveBaseBean(List<BaseBean> BaseBeanList) {
        baseBeanDao.executeSqlsByParmsList(BaseBeanList, null, null);
    }

    @Override
    public List<BaseBean> getCheckedMenuByEmpowerId(String empowerId) {
        StringBuffer sql = new StringBuffer(" from MoneyEmpowerMenu  where empowerId=?  ");
        Object[] params = {empowerId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Menu> getMenuByCompanyId(String companyId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append(" from Menu o where   status = 1 and menuId in (");
        sql.append(" select menuId from CompanyMenu where companyId = ?) ORDER BY menuLevel,sortNum");
        Object[] params = {companyId};
        List<Menu> list = this.getListBeanByHqlAndParams(sql.toString(), params);
        if (list.size() == 0) {
            sql.setLength(0);
            sql.append(" from Menu where status = 1 and menuId in (")
                    .append(" select m.menuId from MoneyEmpowerMenu m,MoneyEmpower e,Company dtc")
                    .append(" where e.status=1 and m.empowerId = e.empowerId and nvl(dtc.ccomtype,6) = e.ccomType and dtc.companyID=?)")
                    .append(" ORDER BY menuLevel,sortNum) ");
            list = this.getListBeanByHqlAndParams(sql.toString(), params);
            saveComanyMenuByMenuList(list, companyId);
        }
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<Menu> getMenuListByCompanyIdAndPID(String companyId, String menuPID) {
        StringBuffer sql = new StringBuffer(128);
        List<Object> params = new ArrayList<>();
        sql.append(" from Menu o where   status = 1 and menuId in (");
        sql.append(" select menuId from CompanyMenu where companyId = ?) ");
        params.add(companyId);
        if ( menuPID.isEmpty()) {
            sql.append(" and o.menuLevel=1 ");
        } else {
            sql.append(" and o.menuPID = ?");
            params.add(menuPID);
        }
        sql.append(" ORDER BY menuLevel,sortNum");
        List<Menu> list = this.getListBeanByHqlAndParams(sql.toString(), params.toArray());
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<BaseBean> getStaffRoleByStaffId(String staffId, String companyId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("from StaffRole where staffId = ? and companyId = ? ");
        return baseBeanService.getListBeanByHqlAndParams(sql.toString(), new Object[]{staffId, companyId});
    }

    @Override
    public List<Menu> getMenuByRoleList(List<BaseBean> staffRoleList, String companyId) {
        StringBuffer sql = new StringBuffer(128);
        List<String> param = new ArrayList<>();
        sql.append(" from Menu o where status = 1 and menuId in ( select menuId from CompanyMenu where companyId=? and menuId in ( ")
                .append("select menuId from RoleMenu where companyId=? ");
        param.add(companyId);
        param.add(companyId);
        int size = staffRoleList.size();
        StaffRole staffRole = null;
        for (int i = 0; i < size; i++) {
            staffRole = (StaffRole) staffRoleList.get(i);
            if (i == 0) {
                sql.append(" AND ( roleId = ?");
            } else {
                sql.append(" OR roleId = ?");
            }
            if (i == size - 1) {
                sql.append(" ) ");
            }
            param.add(staffRole.getRoleId());
        }
        sql.append(" )) ORDER BY menuLevel,sortNum");
        return this.getListBeanByHqlAndParams(sql.toString(), param.toArray());
    }

    @Override
    public String saveOrgByCompanyMenu(String companyId) {
        DictData dictData = dictDataService.getDictDataByParam("menuOrg","01","");
        if (dictData != null) {
            StringBuilder sql = new StringBuilder(128);
            sql.append("delete from COrganization where companyId = ?  and ORGANIZATIONID LIKE '%bMenu%'");
            List<Object[]> parms = new ArrayList<>();
            parms.add(new Object[]{companyId});
            baseBeanService.executeHqlsByParamsList(null, new String[]{sql.toString()}, parms);
            sql.setLength(0);
            sql.append(" INSERT INTO dtCOrganization(ORGANIZATIONKEY,ORGANIZATIONID,COMPANYID,ORGANIZATIONNAME,")
                    .append(" ORGANIZATIONNUMBER,ORGANIZATIONPID,STATUS,ORGANIZATIONCREATEDATE,OCODE,ORGANIZATIONLEVEL)")
                    .append(" SELECT  CONCAT(MENU_KEY,'" + companyId.substring(companyId.length() - 3))
                    .append(" ')ORGANIZATIONKEY,o.MENU_ID ORGANIZATIONID,'" + companyId + "' as COMPANYID,")
                    .append(" menu_name as ORGANIZATIONNAME,SORT_NUM AS ORGANIZATIONNUMBER,nvl(menu_pid,'" + companyId + "') as ORGANIZATIONPID,")
                    .append(" '00' as status, CURRENT_TIMESTAMP as ORGANIZATIONCREATEDATE,(cast(SORT_NUM as number) + 1) as OCODE,MENU_LEVEL")
                    .append(" from DT_COMPANY_MENU c left join DT_Menu o on c.MENU_ID = o.MENU_ID  where   o.status = 1  ")
                    .append(" and COMPANY_ID=? and menu_level < 3");

            List<Object[]> paramslist = new ArrayList<>();
            Object[] obj = {companyId};
            paramslist.add(obj);
            baseBeanDao.executeSqlsByParmsList(null,new String[]{sql.toString()},paramslist);
        }

        return "success";
    }

    public void saveComanyMenuByMenuList(List<Menu> list, String companyId) {
        List<BaseBean> dataList = new ArrayList<>();
        CompanyMenu companyMenu = null;
        for (Menu menu : list) {
            companyMenu = new CompanyMenu();
            companyMenu.setCompanyId(companyId);
            companyMenu.setMenuId(menu.getMenuId());
            dataList.add(companyMenu);
        }
        baseBeanDao.executeSqlsByParmsList(dataList, null, null);
    }


    @Override
    public void deleteCompanyMenuById(String companyId) {
        String sql = "delete from CompanyMenu where companyId = ? ";
        List<Object[]> parms = new ArrayList<>();
        parms.add(new Object[]{companyId});
        baseBeanService.executeHqlsByParamsList(null, new String[]{sql}, parms);
    }

    @Override
    public void deleteCompanyMenuByCompanyIdList(List<String> companyIdList) {
        List<Object[]> parms = new ArrayList<Object[]>();
        int length = companyIdList.size();
        String[]  hqls = new String[length];
        for (int i = 0; i < length; i++ ) {
            hqls[i] = "delete from CompanyMenu where companyId = ?";
            parms.add(new Object[]{companyIdList.get(i)});
        }
        baseBeanService.executeHqlsByParamsList(null, hqls, parms);
    }

    @Override
    public boolean isExistRoleName(String roleName, String companyId) {
        StringBuffer sql = new StringBuffer(" from CRole  where companyId=? and roleName=? ");
        Object[] params = {companyId, roleName};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? true : false;
    }

    @Override
    public List<BaseBean> getCRoleDataByCompanyId(String companyId) {
        StringBuffer sql = new StringBuffer(" from CRole  where companyId=? ");
        Object[] params = {companyId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return list;
    }

    @Override
    public PageForm getListCRoleByCompayId(String companyId, Integer pageNumber, Integer pageSize) {
        StringBuffer hql = new StringBuffer(128);
        hql.append("SELECT role.ROLEID,role.ROLENAME, COUNT(per.STAFFNAME) AS PERSONNUM")
                .append(" FROM DTCROLE role left join (")
                .append(" SELECT DISTINCT staff.STAFFNAME,sr.ROLE_ID FROM DT_HR_STAFF staff")
                .append(" left join DT_HR_STAFF_ROLE sr on staff.STAFFID = sr.STAFF_ID")
                .append(" WHERE sr.COMPANY_ID = ? AND staff.STAFFNAME IS NOT NULL )per")
                .append(" on role.ROLEID = per.ROLE_ID")
                .append(" where role.COMPANYID = ?")
                .append(" GROUP BY role.ROLEID,role.ROLENAME,role.CREATE_DATE order by role.CREATE_DATE");
        String hql2 = " SELECT COUNT(*) FROM (" + hql.toString() + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), hql.toString(), hql2, new String[]{companyId, companyId});
        return pageForm;
    }

    @Override
    public void createBaseBean(BaseBean baseBean) {
        baseBeanDao.save(baseBean);
    }

    @Override
    public void updateBaseBean(BaseBean baseBean) {
        baseBeanDao.update(baseBean);
    }

    @Override
    public CRole getCRoleByParam(String roleId, String companyId) {
        StringBuffer sql = new StringBuffer(" from CRole  where roleId=? and companyId=?  ");
        List<Object> params = new ArrayList<>();
        params.add(roleId);
        params.add(companyId);
        CRole info = (CRole) baseBeanDao.getBeanByHqlAndParams(sql.toString(), params.toArray());
        return info;
    }

    @Override
    public CRole getCRoleByRoleName(String roleName, String companyId) {
        StringBuffer sql = new StringBuffer(" from CRole  where roleName=?  and companyId=? ");
        List<Object> params = new ArrayList<>();
        params.add(roleName);
        params.add(companyId);
        CRole info = (CRole) baseBeanDao.getBeanByHqlAndParams(sql.toString(), params.toArray());
        return info;
    }


    @Override
    public List<BaseBean> getCRoleMenuByParam(String roleId, String companyId) {
        StringBuffer sql = new StringBuffer(" from RoleMenu  where roleId=?  and companyId=?");
        Object[] params = {roleId, companyId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<BaseBean> getRoleStandMenuByRoleId(String roleId, String companyId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append(" from Menu o where   status = 1 and menuId in (");
        sql.append(" select menuId from CompanyMenu where companyId = ?) ")
                .append("and menuId in (select menuId from RoleStandardMenu where roleId=? ")
                .append("ORDER BY menuLevel,sortNum");
        List<BaseBean> menuList = baseBeanService.getListBeanByHqlAndParams(sql.toString(), new Object[]{companyId, roleId});
        RoleMenu roleMenu;
        Menu menu;
        List<BaseBean> dataList = new ArrayList<>();
        for (BaseBean baseBean : menuList) {
            menu = (Menu) baseBean;
            roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setCompanyId(companyId);
            roleMenu.setMenuId(menu.getMenuId());
            dataList.add(roleMenu);
        }
        baseBeanDao.executeSqlsByParmsList(dataList, null, null);
        return menuList;
    }

    @Override
    public MoneyEmpower getMoneyEmpowerByEmpowerKey(String empowerKey) {
        return (MoneyEmpower) baseBeanDao.getBeanByKey(MoneyEmpower.class, empowerKey);
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
    public void deleteRoleByID(String companyId, String roleId, CLogBook logBook) {
        String sqlRole = "delete from CRole where roleId = ? and companyId = ? ";
        String sqlMenu = "delete from RoleMenu where roleId = ? and companyId = ? ";

        List<Object[]> parms = new ArrayList<>();
        parms.add(new Object[]{roleId, companyId});
        baseBeanService.executeHqlsByParamsList(null, new String[]{sqlRole}, parms);
        baseBeanService.executeHqlsByParamsList(null, new String[]{sqlMenu}, parms);
    }

    @Override
    public List<BaseBean> getDeptListByDeptPID(String companyId, String organizationPID) {
        Object[] params1 = {companyId, organizationPID};
        String ohql = "from COrganization where companyID=? and organizationPID =? and Status = '00' order by organizationNumber";
        return baseBeanService.getListBeanByHqlAndParams(ohql, params1);
    }

    @Override
    public PageForm getPersonDataByRoleId(String companyId, Map<String, String> param, Integer pageNumber, Integer pageSize) {
        String roleId = param.get("roleId");
        StringBuffer hql = new StringBuffer(128);
        hql.append(" from Staff staff ")
                .append("where staffID in (select staffId from StaffRole where roleId=? and companyId=?) ");
        PageForm pageForm = baseBeanService.getPageForm(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), hql.toString(), new String[]{roleId, companyId});
        return pageForm;
    }

    @Override
    public PageForm getPersonDataByNotRoleId(String companyId, Map<String, String> param, Integer pageNumber, Integer pageSize) {
        String roleId = param.get("roleId");
        String staffName = param.get("staffName");
        StringBuffer hql = new StringBuffer(128);
        List<String> arr = new ArrayList<>();
        hql.append(" FROM Staff staff where staffID in (select staffID from COS where companyID=? and status='01'");
        arr.add(companyId);
        hql.append(") and staffID not in (")
                .append(" select staffId from StaffRole where roleId=? and companyId=?) ");
        arr.add(roleId);
        arr.add(companyId);
        if (!"".equals(staffName)) {
            hql.append(" and ( staffName like ?");
            arr.add("%" + staffName + "%");
            hql.append(" or reference like ?)");
            arr.add("%" + staffName + "%");
        }
        PageForm pageForm = baseBeanService.getPageForm(
                (null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), hql.toString(), arr.toArray());

        return pageForm;
    }

    @Override
    public List<Menu> getMenuByParentId(String companyId, String menuPid) {
        StringBuffer sql = new StringBuffer();
        sql.append("select o from Menu o,CompanyMenu c where o.menuId=c.menuId and  o.status = 1 and c.companyId=? ");
        List<String> params = new ArrayList<>();
        params.add(companyId);
        if (StringUtils.isNotEmpty(menuPid)) {
            sql.append(" and o.menuPID=? ");
            params.add(menuPid);
        } else {
            sql.append(" and o.menuPID is null ");
        }
        sql.append(" ORDER BY o.menuLevel,o.sortNum");
        List<Menu> list = this.getListBeanByHqlAndParams(sql.toString(), params.toArray());
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public void saveCompanyMenuByEmpowerId(String empowerId, List<String> companyIdList) {
        List<Object[]> parms = new ArrayList<Object[]>();
        int length = companyIdList.size();
        String[]  sqls = new String[length];

        for (int i = 0; i < length; i++ ) {
            String companyId = companyIdList.get(i);
            String key = companyId.substring(7,15) + companyId.substring(companyId.length() - 3);
            sqls[i] = "INSERT INTO DT_COMPANY_MENU(COMPANY_MENU_KEY,COMPANY_ID,MENU_ID) "
            + " SELECT CONCAT(MENU_ID,'" + key
            + " ')EMPOWER_MENU_KEY,'" + companyId + "' as COMPANY_ID,MENU_ID FROM DT_MONEY_EMPOWER_MENU  WHERE EMPOWER_ID=?";
            parms.add(new Object[]{empowerId});
        }
        baseBeanDao.executeSqlsByParmsList(null,sqls,parms);
    }
}
