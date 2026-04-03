package hy.ea.human.dao;

import hy.ea.bo.human.salary.DateInfo;
import hy.ea.bo.human.salary.SalaryData;
import hy.ea.bo.human.salary.SalaryDictData;
import hy.ea.bo.human.salary.SalaryLevelList;
import hy.ea.office.bo.HrSalaryLevel;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface SalaryLevelDao {
    /**
     * 获取级别列表
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getSalaryLevelList(String companyId,Integer pageNumber, Integer pageSize);


    /**
     * 获取列别数据
     * @param serialNum
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getSalaryLevelByNum(Integer serialNum,String companyId,Integer pageNumber, Integer pageSize);

    /**
     * 获取级别工资数据
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getLevelSalaryDataListByCompanyId(String companyId,Integer pageNumber, Integer pageSize);

    /**
     * 分页获取级别表数据
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getLevelDataPageFormByCompanyId(String companyId,Integer pageNumber, Integer pageSize);
    /**
     * 根据类型获取保障工资数据
     * @param type
     * @return
     */
    List<BaseBean> getDictDataListByType(String type,String companyId);
    /**
     * 根据公司获取数据
     * @param companyId
     * @return
     */
    List<BaseBean> getDictDataListByCompanyId(String companyId);

    /**
     * 根据日期获取日期数据
     * @param date
     * @return
     */
    DateInfo getDateInfoByDate(String date);

    /**
     * 根据名称获取字典数据
     * @param dictName
     * @param companyId
     * @return
     */
    SalaryDictData getDictDataByDictName(String dictName,String dictType, String companyId);

    /**
     * 根据公司id获取所有工资级别
     * @param companyId
     * @return
     */
    List<BaseBean> getAllSalaryLevelList(String companyId);
    /**
     * 根据公司id获取级别工资数据
     * @param companyId
     * @return
     */
    List<BaseBean> getLevelSalaryByCompanyId(String companyId);
    /**
     * 根据名称获取字典数据列表
     * @param dictName
     * @return
     */
    List<BaseBean>getDictDataListByDictName(String dictName);

    HrSalaryLevel getSalaryLevelById(String salaryLevelId);

    HrSalaryLevel getSalaryLevelBySerial(String companyId,String salaryLevelSerial);

    SalaryLevelList getSalaryLevelListById(String companyId, String levelListId);
    /**
     * 根据级别id获取级别数据
     * @param salaryLevelId
     * @return
     */
    SalaryData getSalaryLevelDataBySalaryLevelId(String salaryLevelId,String companyId);

    /**
     * 根据级别序号获取级别数据
     * @param salaryLevelSerial
     * @return
     */
    SalaryData getSalaryLevelDataBySalaryLevelSerial(String salaryLevelSerial,String companyId);
    /**
     * 根据人员id获取级别数据
     * @param staffId
     * @return
     */
    SalaryData getSalaryLevelDataByStaffId(String staffId,String companyId);
}

