package hy.ea.human.service;

import hy.ea.bo.human.salary.SalaryData;
import hy.ea.office.bo.HrSalaryLevel;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface CSalaryLevelService {
    /**
     * 保存级别
     * @return
     */
    String saveSalaryLevel();

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
     * @param levelListId
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getSalaryLevelByNum(String levelListId,String companyId,Integer pageNumber, Integer pageSize);

    /**
     * 获取级别工资数据
     */
    PageForm getLevelSalaryDataListByCompanyId(String companyId,Integer pageNumber, Integer pageSize);

    /**
     * 保存保障工资数据
     * @return
     */
    String saveDictData();

    /**
     * 根据类型获取保障工资数据
     * @param type
     * @return
     */
    List<BaseBean> getDictDataListByType(String type,String companyId);

    /**
     * 根据类型获取保障工资数据
     * @param companyId
     * @return
     */
    List<BaseBean> getDictDataListByCompanyId(String companyId);

    /**
     * 获取级别数据（分页）
     * @param companyId
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageForm getLevelDataPageFormByCompanyId(String companyId,Integer pageNumber, Integer pageSize);
    /**
     * 保存日期
     * @return
     */
    String saveDate();

    /**
     * 保存级别工资数据
     * @return
     */
    List<BaseBean> saveLevelSalaryData(String type,List<BaseBean>  dictDataList,List<BaseBean> levelDataList);


    HrSalaryLevel getSalaryLevelById(String salaryLevelId);

    HrSalaryLevel getSalaryLevelBySerial(String companyId,String salaryLevelSerial);

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
     * 根据级别id获取级别数据
     * @param staffId
     * @return
     */
    SalaryData getSalaryLevelDataByStaffId(String staffId,String companyId);

    String saveGuaranteeSalary();
}
