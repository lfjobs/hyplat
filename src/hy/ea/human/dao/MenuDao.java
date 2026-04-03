package hy.ea.human.dao;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;
import hy.ea.bo.human.Menu;
import hy.ea.bo.human.MoneyEmpower;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface MenuDao {

    /**
     * 获取菜单数据
     *
     * @return
     */
    List<Menu> getMenuList();

    /**
     * 获取所有菜单数据
     *
     * @return
     */
    List<Menu> getAllMenuList();


    /**
     * 根据菜单id获取菜单信息
     */
    Menu getMenuByMenuId(String menuId);

    /**
     * 根据菜单层级获取最大sortNum
     * @param menuLevel
     * @return
     */
    int getMaxSortNumByMenuLevel(Integer menuLevel,String menuPID);

    /**
     * 菜单名称是否已经存在
     * @param menuName
     * @param menuPID
     * @return
     */
    boolean isExistMenuName(String menuName,String menuPID);


    /**
     * 根据菜单主键查询数据
     * @param menuKey
     * @return
     */
    Menu getMenuByMenuKey(String menuKey);

    /**
     * 根据类型获取权限数据
     *
     * @return
     */
    List<BaseBean> getMoneyEmpowerList();

    /**
     * 金额权限名称是否已经存在
     * @param empowerName
     * @return
     */
    boolean isExistEmpowerName(String empowerName);

    /**
     * 企业类型是否已经存在
     * @param ccomType
     * @return
     */
    boolean isExistEmpowerType(String ccomType);

    /**
     * 根据金额权限主键查询数据
     * @param empowerKey
     * @return
     */
    MoneyEmpower getMoneyEmpowerByEmpowerKey(String empowerKey);

    /**
     * 保存数据
     * @param BaseBeanList
     */
    void saveBaseBean(List<BaseBean> BaseBeanList);

    /**
     * 根据权限id获取菜单
     * @param empowerId
     * @return
     */
    List<BaseBean> getCheckedMenuByEmpowerId(String empowerId);

    /**
     * 根据公司id获取菜单数据
     *
     * @return
     */
    List<Menu> getMenuByCompanyId(String companyId);

    /**
     * 根据公司id和父级获取菜单数据
     * @param companyId
     * @param menuPID
     * @return
     */
    List<Menu> getMenuListByCompanyIdAndPID(String companyId,String menuPID);

    /**
     * 获取角色根据员工id
     * @param staffId
     * @return
     */
    List<BaseBean> getStaffRoleByStaffId(String staffId,String companyId);

    /**
     * 根据角色列表获取菜单
     * @param staffRoleList
     * @return
     */
    List<Menu> getMenuByRoleList(List<BaseBean> staffRoleList,String companyId);

    String saveOrgByCompanyMenu(String companyId);
    /**
     * 根据公司ID删除菜单
     * @param companyId
     */
    void deleteCompanyMenuById(String companyId);


    void deleteCompanyMenuByCompanyIdList(List<String> companyIdList);
    /**
     * 角色权限名称是否已经存在
     * @param roleName
     * @return
     */
    boolean isExistRoleName(String roleName, String companyId);


    /**
     * 根据公司id获取基本角色数据
     * @param companyId
     * @return
     */
    List<BaseBean> getCRoleDataByCompanyId(String companyId);

    /**
     * 获取角色数据
     * @return
     */
    PageForm getListCRoleByCompayId(String companyId,Integer pageNumber, Integer pageSize);

    /**
     * 保存新建数据
     * @param baseBean
     */
    void createBaseBean(BaseBean baseBean);
    /**
     * 保存修改数据
     * @param baseBean
     */
    void updateBaseBean(BaseBean baseBean);

    /**
     * 根据角色id和公司id获取角色数据
     * @param roleId
     * @param companyId
     * @return
     */
    CRole getCRoleByParam(String roleId,String companyId);

    /**
     * 根据角色名称获取角色数据
     * @param roleName
     * @return
     */
    CRole getCRoleByRoleName(String roleName,String companyId);
    /**
     * 根据角色id和公司id获取菜单数据
     *
     * @return
     */
    List<BaseBean> getCRoleMenuByParam(String roleId,String companyId);


    List<BaseBean> getRoleStandMenuByRoleId(String roleId,String companyId);
    /**
     * 根据ID(Not Key)删除Role及相关信息
     * @param companyID
     * @param roleID
     * @param logBook
     */
    void deleteRoleByID(String companyID, String roleID, CLogBook logBook);

    /**
     * 根据部门父级Id获取部门数据
     * @param companyId
     * @param organizationPID
     * @return
     */
    List<BaseBean> getDeptListByDeptPID(String companyId,String organizationPID);

    /**
     * 获取角色数据
     * @return
     */
    PageForm getPersonDataByRoleId(String companyId,Map<String,String> param,Integer pageNumber, Integer pageSize);

    /**
     * 获取公司人员数据不包括角色人员
     * @return
     */
    PageForm getPersonDataByNotRoleId(String companyId,Map<String,String> param,Integer pageNumber, Integer pageSize);

    /**
     * 根据公司和父级id查询关联数据
     * @param companyId
     * @param menuPid
     * @return
     */
    List<Menu> getMenuByParentId(String companyId, String menuPid);


    void saveCompanyMenuByEmpowerId(String empowerId,List<String> companyIdList);
}

