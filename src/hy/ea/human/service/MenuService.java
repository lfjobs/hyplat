package hy.ea.human.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CRole;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.Menu;
import hy.ea.bo.human.MoneyEmpower;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;


public interface MenuService {


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
     * 获取所有菜单数据
     *
     * @return
     */
    JSONObject getAllMenuListAndCheckedMenu();

    /**
     * 创建菜单
     * @return
     */
    String createMenu();

    /**
     * 删除菜单
     * @return
     */
    String delMenuByMenuId(String menuId);
    /**
     * 创建菜单
     * @return
     */
    String updateMenu();

    /**
     * 根据条件查询数据
     * @return
     */
    List<BaseBean> getMenuByQueryData(Map<String,String> data);

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
     * 创建金额权限
     * @return
     */
    String createMoneyEmpower();

    /**
     * 修改金额权限
     * @return
     */
    String updateMoneyEmpower();

    /**
     * 删除金额权限
     * @return
     */
    String delMoneyEmpower();


    /**
     * 根据金额权限主键查询数据
     * @param empowerKey
     * @return
     */
    MoneyEmpower getMoneyEmpowerByEmpowerKey(String empowerKey);

    /**
     * 保存金额菜单
     * @return
     */
    String saveMoneyEmpowerMenu(String checkedData,String empowerId);

    /**
     * 根据公司id获取菜单数据树形
     *
     * @return
     */
    List<Menu> getMenuByCompanyId(String companyId);
    /**
     * 根据公司id获取菜单数据
     *
     * @return
     */
    List<Menu> getCompanyMenuListByCompanyId(String companyId);

    /**
     * 根据公司id和父级获取菜单数据
     * @param companyId
     * @param menuPID
     * @return
     */
    List<Menu> getMenuListByCompanyIdAndPID(String companyId,String menuPID);
    /**
     * 根据员工id获取菜单数据
     *
     * @param staffId
     * @return
     */
    List<Menu> getMenuByStaffId(String staffId);

    /**
     * 获取公司数据
     * @return
     */
    PageForm getAllCompanyData(Integer pageNumber,Integer pageSize,Map<String,Object> param);

    /**
     * 保存公司菜单
     * @return
     */
    String saveCompanyMenu(String checkedData,String companyId);

    String saveOrgByCompanyMenu(String companyId);
    /**
     * 获取角色数据
     * @return
     */
    PageForm getListCRoleByCompanyId(Integer pageNumber,Integer pageSize,String companyId);

    /**
     * 保存角色数据
     * @return
     */
    String saveCRoleData();
    /**
     * 保存角色菜单
     * @return
     */
    String saveRoleMenu();

    /**
     * 根据角色id和公司id获取角色数据
     * @param roleId
     * @param companyId
     * @return
     */
    CRole getCRoleByParam(String roleId, String companyId);

    /**
     * 根据角色id和公司id获取菜单数据
     * @param roleId
     * @return
     */
    JSONObject getRoleMenuByRoleId(String roleId);

    /**
     * 根据ID(Not Key)删除Role及相关信息
     * @param roleId
     */
    String deleteRoleByID(String roleId);

    /**
     * 根据部门父级Id获取部门数据
     * @param organizationPID
     * @return
     */
    List<BaseBean> getDeptListByDeptPID(String organizationPID);

    /**
     * 根据公司id获取往来单位信息
     * @param companyId
     * @return
     */
    ContactCompany getContactCompanyByCompanyId(String companyId);

    /**
     * 根据公司id获取公司购买信息
     * @param companyId
     * @return
     */
    TEshopCusCom getTEshopCusComByCompanyId(String companyId);

    /**
     * 查询公司回访记录
     * @param pageNumber
     * @param pageSize
     * @param param
     * @param companyId
     * @return
     */
    PageForm returnsVisit(Integer pageNumber,Integer pageSize,Map<String,String> param, String companyId);

    /**
     * 根据角色id获取人员数据
     * @param param
     * @return
     */
    PageForm getPersonDataByRoleId(Map<String,String> param,Integer pageNumber, Integer pageSize);

    /**
     * 保存角色人员数据
     * @param roleId
     * @param staffIds
     * @return
     */
    String saveStaffRole(String roleId,String staffIds);

    /**
     * 删除角色下的员工
     * @param roleId
     * @param staffId
     * @return
     */
    String deleteRoleStaffByStaffId (String roleId,String staffId);

    /**
     * 根据公司和父级id查询关联数据
     * @param companyId
     * @param menuPid
     * @return
     */
    List<Menu> getMenuByParentId(String companyId, String menuPid);

    /**
     * 根据金额权限保存公司菜单
     * @param empowerId
     * @param companyIds
     * @return
     */
    String saveCompanyMenuByEmpowerId(String empowerId,String companyIds);
}
