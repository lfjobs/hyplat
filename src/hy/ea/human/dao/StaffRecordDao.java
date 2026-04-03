package hy.ea.human.dao;

import hy.plat.bo.BaseBean;

import java.util.List;

public interface StaffRecordDao {
    /**
     * 获取简历数据
     * @param resumeID
     * @return
     */
    Object getResumeDataByResumeID(String resumeID);

    /**
     * 获取教育经历
     * @param resumeID
     * @return
     */
    List<BaseBean> getEducationalByResumeID(String resumeID);

    /**
     * 获取简历数据
     * @param resumeID
     * @return
     */
    List<BaseBean> getStaffResumeByResumeID(String resumeID);

    /**
     * 获取面试结果
     * @param companyId
     * @param staffID
     * @return
     */
    List<BaseBean> getInterviewResultByStaffID(String companyId,String staffID);

    /**
     * 获取面试登记结果
     * @param companyId
     * @param staffID
     * @return
     */
    List<BaseBean> getInterviewRegistrationByStaffId(String companyId,String staffID);
}

