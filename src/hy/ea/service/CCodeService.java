package hy.ea.service;

import hy.ea.bo.CCode;
import hy.ea.bo.DrivingSchool.TbSysGeography;

import java.util.List;


public interface CCodeService {
    /**
     * 根据codeID查询出code
     *
     * @param codeID
     * @return
     */
    CCode getCCodeByID(String companyID, String codeID);

    /**
     * 根据companyID和codePID查询出其子节点
     *
     * @param companyID
     * @param codePID
     * @return
     */
    List<CCode> getCCodeListByPID(String companyID, String codePID);

    /**
     * 根据companyID和codePID查询出其子节点
     *
     * @param companyID
     * @param codePID
     * @return
     */
    List<CCode> getCCodeListNewByPID(String companyID, String codePID, String codetype);

    /**
     * 根据companyID和codePID查询出其子节点
     *
     * @param companyID
     * @param codePID
     * @return
     */
    List<CCode> getCCodeListByPIDs(String companyID, String[] codePID);

    /**
     * 根据companyID和codePID查询出其所有子节点
     *
     * @param companyID
     * @param codePID
     * @return
     */
    List<CCode> getAllCCodeListByPID(String companyID, String codePID, List<CCode> list);

    /**
     * 注册将Code中的数据更新到CCode中去
     *
     * @param companyID
     */
    void upadateCodeToCCode(String companyID);

    /**
     * 获取所有父类id为codeID的所有子类id集合
     *
     * @param companyID 公司ID
     * @param codeID    需要删除的codeID
     * @param codeStr   传空字符串
     * @return 通过连接符"-"链接起来返回
     */
    String getCodeList(String companyID, String codeID, String codeStr);


    /**
     * 获取所属行政区域
     *
     * @param @ geoLevel区域级别
     */
    List<TbSysGeography> getAllTbSysGeographyBygeoLevel(String geoLevel);

}
