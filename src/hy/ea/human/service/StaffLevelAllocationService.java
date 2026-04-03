package hy.ea.human.service;

import hy.ea.bo.ExamineProcessData;
import hy.ea.bo.human.StaffLevelAllocation;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface StaffLevelAllocationService {

    /**
     * 员工分配列表
     * @return
     */
    PageForm getLevelAllocationList(String companyId,String staffID,Map<String,String> params, Integer pageNumber, Integer pageSize);

     /**
     * 根据id获取分配数据
     * @param levelAllocationId
     * @return
     */
    StaffLevelAllocation getLevelAllocationById(String levelAllocationId,String companyId);

    /**
     * 查询公司人员数据
     * @return
     */
    PageForm getStaffDataList(Integer pageNumber, Integer pageSize);

    /**
     * 保存分配数据
     * @param staffLevelAllocation
     * @return
     */
    String saveAllocationData(StaffLevelAllocation staffLevelAllocation);

    /**
     * 删除级别分配数据
     * @param levelAllocationId
     * @param companyId
     * @return
     */
    String deleteLevelAllocationById(String levelAllocationId,String companyId);

    /**
     * 审核级别分配
     * @param examineProcessData
     * @return
     */
    String examineLevelAllocation(ExamineProcessData examineProcessData,StaffLevelAllocation staffLevelAllocation);

    /**
     * 保存转交审批人员数据
     * @param id
     * @param transmitId
     * @param transmitName
     * @return
     */
    String saveTransmitPersonData(String id,String transmitId,String transmitName);

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
     * @param companyId
     * @return
     */
    List<BaseBean> getExamineProcessList(String levelAllocationId,String companyId);

    /**
     * 保存人员级别
     * @param allocationId
     * @return
     */
    String saveStaffLevelByAllocationId(String allocationId);

}
