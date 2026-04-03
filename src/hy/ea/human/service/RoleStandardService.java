package hy.ea.human.service;


import hy.ea.bo.human.RoleStandard;
import hy.plat.bo.BaseBean;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface RoleStandardService {
    /**
     * 查询岗位标准化数据
     */
    List<RoleStandard> getRoleStandardList();

    /**
     * 保存角色数据
     * @return
     */
    String saveRoleStandardData();

    /**
     * 根据角色key获取标准角色数据
     * @param roleId
     * @return
     */
    RoleStandard getRoleStandardByRoleId(String roleId);

    /**
     * 根据角色key删除标准角色数据
     * @param roleId
     * @return
     */
    String deleteRoleStandardByRoleId(String roleId);

    /**
     * 根据角色id获取菜单数据
     * @param roleId
     * @return
     */
    JSONObject getRoleMenuByRoleId(String roleId);


    /**
     * 保存角色菜单
     * @return
     */
    String saveRoleMenu();
    /**
     * 根据金额id获取角色数据
     * @param param
     * @return
     */
    List<BaseBean> getRoleStandardByEmpowerId(Map<String,String> param);

    /**
     * 删除金额数据下的角色
     * @param empowerId
     * @param roleId
     * @return
     */
    String deleteEmpowerRoleDataByRoleId(String empowerId,String roleId);

    /**
     * 保存公司类型（金额）角色
     * @param empowerId
     * @param roleIds
     * @return
     */
    String saveEmpowerRole(String empowerId,String roleIds);

    /**
     * 根据公司id将标准角色菜单保存到角色菜单中
     * @param companyId
     * @return
     */
    String saveRoleMenuStandardByCompanyId(String companyId);
}
