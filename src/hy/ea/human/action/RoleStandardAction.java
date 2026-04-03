package hy.ea.human.action;

import hy.ea.bo.human.RoleStandard;
import hy.ea.human.service.RoleStandardService;
import hy.plat.bo.BaseBean;
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
 * 岗位标准化
 */
@Controller
@Scope("prototype")
public class RoleStandardAction {
    @Resource
    private RoleStandardService roleStandardService;

    String result ;

    RoleStandard roleStandard;
    /**
     * 查询岗位标准化数据
     */
    public String getRoleStandardList() {
        List<RoleStandard> list = roleStandardService.getRoleStandardList();
        if (list.size() > 0){
            JSONArray obj = JSONArray.fromObject(list);
            result = obj.toString();
        } else {
            result = "";
        }

        return "success";
    }

    /**
     * 根据角色key和所属金额id查询角色数据
     * @return
     */
    public String getRoleStandardByRoleId(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String roleId = request1.getParameter("roleId");
        roleStandard = roleStandardService.getRoleStandardByRoleId(roleId);
        return "bRoleEdit";
    }

    /**
     * 删除角色数据
     * @return
     */
    public String deleteRoleStandardByRoleId() {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String roleId = request1.getParameter("roleId");
        result = roleStandardService.deleteRoleStandardByRoleId(roleId);
        return "success";
    }



    /**
     * 保存角色数据
     * @return
     */
    public String saveRoleStandardData(){
        result = roleStandardService.saveRoleStandardData();
        return "success";
    }

    /**
     * 根据角色id获取角色菜单
     * @return
     */
    public String getRoleMenuByRoleId(){
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String roleId = request1.getParameter("roleId");
        JSONObject data = roleStandardService.getRoleMenuByRoleId(roleId);
        result = data.toString();
        return "success";
    }

    /**
     * 保存角色权限菜单
     * @return
     */
    public String saveRoleMenu(){
        result = roleStandardService.saveRoleMenu();
        return "success";
    }

    public String getRoleDataByEmpowerId(){
        Map<String,String> param = new HashMap<>();
        HttpServletRequest request = ServletActionContext.getRequest();
        String empowerId = request.getParameter("empowerId");
        String type = request.getParameter("type");
        param.put("type",type);
        param.put("empowerId",empowerId);
        List<BaseBean> list = roleStandardService.getRoleStandardByEmpowerId(param);
        result = JSONArray.fromObject(list).toString();
        return "success";
    }
    /**
     * 删除金额数据下的角色
     */
    public String deleteEmpowerRoleDataByRoleId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String empowerId = request.getParameter("empowerId");
        String roleId = request.getParameter("roleId");
        result = roleStandardService.deleteEmpowerRoleDataByRoleId(empowerId,roleId);
        return "success";
    }

    /**
     * 保存公司类型（金额）角色
     * @return
     */
    public String saveEmpowerRole(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String empowerId = request.getParameter("empowerId");
        String roleIds = request.getParameter("roleIds");
        result = roleStandardService.saveEmpowerRole(empowerId,roleIds);
        return "success";
    }

    public String saveRoleMenuStandardByCompanyId(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String companyId = request.getParameter("companyId");
        result = roleStandardService.saveRoleMenuStandardByCompanyId(companyId);
        return "success";
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public RoleStandard getRoleStandard() {
        return roleStandard;
    }

    public void setRoleStandard(RoleStandard roleStandard) {
        this.roleStandard = roleStandard;
    }
}
