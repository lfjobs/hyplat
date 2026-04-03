package hy.ea.human.service;

public interface StaffRecordService {
    /**
     * 获取个人简历
     * @param staffID
     */
    void getResumeDataByStaffID(String staffID);

    /**
     * 获取面试结果
     * @param companyId
     * @param staffID
     * @return
     */
    String getInterviewResultByStaffID(String companyId,String staffID);

    /**
     * 获取面试登记结果
     * @param companyId
     * @param staffID
     * @return
     */
    String getInterviewRegistrationByStaffId(String companyId,String staffID);
}
