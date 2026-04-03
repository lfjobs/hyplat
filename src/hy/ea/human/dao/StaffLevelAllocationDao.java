package hy.ea.human.dao;

import hy.ea.bo.ExamineProcessData;
import hy.ea.bo.human.StaffLevelAllocation;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface StaffLevelAllocationDao {
    /**
     * 员工分配列表
     * @return
     */
    PageForm getLevelAllocationList(String companyId,String staffID,Map<String,String> params,Integer pageNumber, Integer pageSize);


    StaffLevelAllocation getLevelAllocationById(String levelAllocationId,String companyId);

    /**
     * 获取审核流程id
     * @param examineProcessId
     * @param companyId
     * @return
     */
    ExamineProcessData getExamineProcessDataById(String examineProcessId,String companyId);

    /**
     * 根据名称查询角色数据
     * @param param
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getRoleDataByParam(Map<String,String> param, String companyId, Integer pageNumber, Integer pageSize);

    /**
     * 根据id获取审核数据
     * @param levelAllocationId
     * @return
     */
    List<BaseBean> getExamineProcessList(String levelAllocationId,String companyId);
}

