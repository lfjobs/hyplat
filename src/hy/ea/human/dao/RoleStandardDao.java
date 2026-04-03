package hy.ea.human.dao;

import hy.ea.bo.human.MoneyEmpower;
import hy.ea.bo.human.RoleStandard;
import hy.plat.bo.BaseBean;

import java.util.List;

public interface RoleStandardDao {

    /**
     * 查询岗位标准化数据
     *
     * @return
     */
    List<RoleStandard> getRoleStandardList();

    /**
     * 角色权限名称是否已经存在
     *
     * @param roleName
     * @return
     */
    boolean isExistRoleName(String roleName);

    /**
     * 根据角色id获取标准角色数据
     *
     * @param roleId
     * @return
     */
    RoleStandard getRoleStandardByRoleId(String roleId);

    /**
     * 根据角色key删除标准角色数据
     *
     * @param roleId
     * @return
     */
    String deleteRoleStandardByRoleId(String roleId);

    /**
     * 根据角色id和菜单数据
     *
     * @return
     */
    List<BaseBean> getRoleStandardMenuByRoleId(String roleId);


    /**
     * 获取角色数据
     *
     * @return
     */
    List<BaseBean> getRoleStandardByEmpowerId(String empowerId);

    /**
     * 获取角色数据不包括金额id中
     *
     * @return
     */
    List<BaseBean> getRoleStandardByNotEmpowerId(String empowerId);

    /**
     * 删除金额数据下的角色
     *
     * @param empowerId
     * @param roleKey
     * @return
     */
    String deleteEmpowerRoleDataByRoleId(String empowerId, String roleKey);

    /**
     * 根据公司id获取金额商品数据
     * @param companyId
     * @return
     */
    MoneyEmpower getMoneyEmpowerByCompanyId(String companyId);

    /**
     * 根据金额id获取标准角色菜单
     * @param empowerId
     * @return
     */
    List<BaseBean> getRoleStandardMenuByEmpowerId(String empowerId);
}

