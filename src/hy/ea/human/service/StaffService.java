package hy.ea.human.service;

import hy.ea.bo.human.*;
import hy.ea.bo.office.DocStaff;
import hy.ea.bo.office.Document;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;


public interface StaffService {

    /**
     * 保存员工数据
     * @return
     */
    String saveStaffData(Staff staff, COS cos,List<UpLoadFile> upLoadFileList,String postIds,String type,String deleteFileIds);

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
     * 获取cos数据 对cos内的数据进行整合
     * @param staffId
     * @param companyId
     * @return
     */
    COS getCosDataByParam(String staffId,String companyId);

    /**
     * 获取cos数据
     * @param staffId
     * @param companyId
     * @return
     */
    COS getCosDataByStaffIdAndCompanyId(String staffId,String companyId);
    /**
     * 获取离职数据
     * @param staffId
     * @param companyId
     * @return
     */
    COSDimissionStaff getCOSDimissionStaffDataByParam(String staffId, String companyId);
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
    List<UpLoadFile>  getFileDataByStaffId(String staffId,String companyId);

    /**
     * 删除入职人员
     * @param companyId
     * @param staffId
     * @return
     */
    String deleteStaffByStaffId( String companyId,String staffId,String cosId);

    /**
     * 数据
     * @param account
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getSignData(String account,String beginDate,String endDate,Integer pageNumber, Integer pageSize);

    /**
     * 保存
     * @return
     */
    String saveSignData();

    /**
     * 获取公司人员数据
     * @return
     */
    PageForm getPersonDataByParam(String companyId, Map<String,String> param, Integer pageNumber, Integer pageSize);


    /**
     * 查询需要入职的人员
     *
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getNeedJoinStaffData(String companyId, Integer pageNumber, Integer pageSize);

    /**
     * 查询入职统计数据
     * @return
     */
    String getEntryStatisticsData();

    List<Object> getDocStaffInfo(String staffID, String companyID,String contractSource);

    void saveRelateDoc(String templateId,String contractType,String contractTypeName,String companyID,String temptId,String staffID,String templateTypeName);

    /**
     * 刪除公司合同模板
     * @param templateId
     * @param companyID
     */
    String deleteRelateDoc(String templateId,String companyID);

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

    String getStaffContractByDocId(String docId);

    /**
     * 保存公司合同模板
     * @param templateId
     * @param contractType
     * @param contractTypeName
     * @param companyID
     * @param temptId
     * @param staffID
     */
    String saveContractTemp(String templateId,String contractType,String contractTypeName,String companyID,String temptId,String staffID);

    /**
     * 获取离职人员数据
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @param queryName
     * @return
     */
    PageForm getResignStaffData(String companyId, Integer pageNumber, Integer pageSize,String queryName);

    /**
     * 保存离职人员信息
     * @param cOSDimissionStaff
     * @param type
     * @return
     */
    String saveResignStaffData(COSDimissionStaff cOSDimissionStaff,Staff staff,String type);
}
