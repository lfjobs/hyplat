package hy.plat.dao;

import hy.plat.bo.BusinessType;

import java.util.List;

public interface BusinessTypeDao {

    /**
     * 根据父节点id查询子节点数据
     *
     * @param typePID
     * @return
     */
    List<BusinessType> getBusinessTypeByTypePID(String typePID);

    /**
     * 获取展示的根节点数据
     *
     * @return
     */
    List<BusinessType> getBusinessTypeRootList();

    /**
     * 获取展示的事业部数据
     *
     * @return
     */
    List<BusinessType> getBusinessTypeDivisionList();


    /**
     * 新增行业类别
     *
     * @param info
     */
    void createBusinessType(BusinessType info);

    /**
     * 修改行业类别
     *
     * @param info
     */
    void updateBusinessType(BusinessType info);

    /**
     * 根据id查询行业类别
     *
     * @param typeKey
     */
    BusinessType getBusinessTypeById(String typeKey);


    /**
     * 根据编号校验数据是否已经存在
     *
     * @param typeNum
     * @return
     */
    List<BusinessType> getBusinessTypeByNum(String typeNum);

    /**
     * 编号是否已经存在
     *
     * @param typeNum
     * @return
     */
    boolean isExistTypeNum(String typeNum);

    /**
     * 根据typeID查询类型行业类型数据
     *
     * @param typeId
     * @return
     */
    BusinessType getBusinessTypeByTypeId(String typeId);


    /**
     * 获取该父类子级的最大sortNum
     *
     * @param typePID
     * @return
     */
    int getMaxSortNumBy(String typePID);

    /**
     * 根据类型层级获取最大sortNum
     * @param typeLevel
     * @return
     */
    int getMaxSortNumByTypeLevel(Integer typeLevel);

    /**
     * 根据父级id查找子类型
     * @param typePID
     * @return
     */
    List<BusinessType> getBusinessTypeByPID(String typePID);
}
