package hy.ea.human.dao;

import hy.ea.bo.human.*;
import hy.ea.bo.office.DocStaff;
import hy.ea.bo.office.Document;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface StaffDao {
    /**
     * 入职管理数据
     * @return
     */
    PageForm getEntryStaffDataList(String companyId, Integer pageNumber, Integer pageSize,String queryName,COS cos);

    /**
     * 根据staffId获取员工数据
     * @param staffId
     * @return
     */
    Staff getStaffDataByStaffId(String staffId);

    /**
     * 获取cos数据
     * @param param
     * @return
     */
    COS getCosDataByParam(Map<String,String> param);

    /**
     * 获取离职数据
     * @param staffId
     * @param companyId
     * @return
     */
    COSDimissionStaff getCOSDimissionStaffDataByParam(String staffId, String companyId);
    /**
     * 根据staffId获取角色数据
     * @param staffId
     * @param companyId
     * @return
     */
    List<BaseBean> getRoleListByStaffId(String staffId, String companyId);
    /**
     * 根据staffId获取部门数据
     * @param staffId
     * @param companyId
     * @return
     */
    List<BaseBean> getDeptListByStaffId(String staffId, String companyId);


    /**
     * 根据staffId获取部门数据
     * @param staffId
     * @param companyId
     * @return
     */
    List<BaseBean> getCOSListByStaffId(String companyId, String staffId);


    /**
     * 根据staffId获取崗位數據
     * @param staffId
     * @param companyId
     * @return
     */
    List<BaseBean> getPostListByStaffId(String staffId, String companyId);

    /**
     * 获取岗位数据
     * @param companyId
     * @param depPostID
     * @return
     */
    DepartmentPost getPostDataByStaffId(String companyId, String depPostID);
    /**
     * 根据id获取人员数据
     * @return
     */
    Staff getStaffByStaffId(String staffId);

    /**
     * 获取入职数据
     * @param staffId
     * @param companyId
     * @return
     */
    Audition getAuditionDataByParam(String staffId, String companyId);
    /**
     * 获取文件数据
     * @param staffId
     * @param companyId
     * @return
     */
    List<UpLoadFile>  getFileDataByStaffId(String staffId, String companyId);

    /**
     * 数据
     * @param account
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getSignData(String account,String beginDate,String endDate,Integer pageNumber, Integer pageSize);

    /**
     * 查询需要入职的人员
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getNeedJoinStaffData(String companyId,Integer pageNumber,Integer pageSize);

    /**
     * 获取入职数据
     * @param companyId
     * @return
     */
    Object getEntryData(String companyId);

    /**
     * 获取学历数据
     * @param companyId
     * @return
     */
    List getEducationData(String companyId);

    /**
     * 获取合同数据
     * @param companyId
     * @return
     */
    List getContractData(String companyId);

    /**
     * 获取入职人数
     * @param companyId
     * @return
     */
    List getEntryPersonData(String companyId);

    /**
     * 获取人员合同
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @param params
     * @return
     */
    PageForm getStaffDocList(String companyId, Integer pageNumber, Integer pageSize,Map<String,String> params);

    /**
     * 获取员工合同信息
     * @param staffId
     * @param companyId
     * @param docId
     * @return
     */
    DocStaff getStaffContractInfo(String staffId, String companyId, String docId);

    /**
     * 获取合同信息
     * @param docId
     * @return
     */
    Document getDocumentByDocId(String docId);

    /**
     * 离职人员信息
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @param queryName
     * @return
     */
    PageForm getResignStaffData(String companyId, Integer pageNumber, Integer pageSize,String queryName);

}

