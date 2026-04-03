package hy.ea.human.service;



import hy.ea.bo.human.salaryNew.SalaryItem;
import hy.ea.bo.human.salaryNew.SalaryUnits;
import hy.plat.bo.PageForm;

import java.util.List;

public interface SalaryLevelService {


    public PageForm getSalaryLevelList(int pageNumber, int pageSize, String companyID);

    /**
     *
     *
     * 获取所有薪资项目点
     * @param suID
     * @return
     */
    public List<Object>  getSalaryItemsList(String suID);

    /**
     *
     * 获取薪资组成单元
     * @param parentslID
     * @return
     */
    public List<Object> getSalaryUnitsList(String parentslID,String companyID);


    /**
     *
     * 复制预设数据到公司
     * @param companyID
     * @param staffID
     * @return
     */
    public  String copySalaryStruct(String companyID,String staffID);

    /**
     *
     * 改变排序
     * @param id1
     * @param id2
     * @return
     */
    public String changeSort(String id1,String id2,String staffID,String companyID);


    /**
     *
     * 添加薪资单元
     * @param salaryUnits
     * @return
     */

    public String addSalaryUnits(SalaryUnits salaryUnits, String staffID, String companyID);


    /**
     *
     * 添加薪资项目节点
     * @param
     * @return
     */
    public String addSalaryItems( SalaryItem salaryItem, String staffID, String companyID);

    /**
     *
     * 修改薪资结构名称
     * @param id
     * @param name
     * @param staffID
     * @param companyID
     * @return
     */
    public String updateSalaryStrutsName(String id,String name,String staffID,String companyID);

    /**
     *
     * 删除
     * @param id
     * @param staffID
     * @param companyID
     * @return
     */
    public String deleteSalaryStruts(String id,String staffID,String companyID);
}
