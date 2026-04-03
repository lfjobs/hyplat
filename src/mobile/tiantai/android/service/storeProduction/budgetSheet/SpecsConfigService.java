package mobile.tiantai.android.service.storeProduction.budgetSheet;

import hy.ea.bo.finance.PerformanceManagement;
import hy.ea.bo.finance.vo.SpecsConfigAddDTO;
import hy.ea.bo.finance.vo.SpecsConfigListVO;
import hy.plat.bo.BaseBean;
import mobile.tiantai.android.bo.DtSpecsConfig;

import java.util.List;

public interface SpecsConfigService {

    /**
     * 查询所有的配置参数
     *
     * @return
     */
    List<SpecsConfigListVO> getSpecsList() throws Exception;

    void addSpecsConfig(SpecsConfigAddDTO specsConfigAddDTO) throws Exception;


    DtSpecsConfig findConfigByType(String specsType);

    /**
     * 删除规格信息
     *
     * @param specsConfigAddDTO
     */
    void delSpecsConfig(SpecsConfigAddDTO specsConfigAddDTO);

    void delSpecsConfig1(SpecsConfigAddDTO specsConfigAddDTO);

    /**
     * 修改单位配置信息
     *
     * @param specsConfigAddDTO
     */
    void updateSpecsConfig(SpecsConfigAddDTO specsConfigAddDTO);

    SpecsConfigAddDTO updateSpecsInfo(SpecsConfigAddDTO specsConfigAddDTO);

    DtSpecsConfig findConfigByType1(String specsType);


    /**
     * 查询根节点数据
     *
     * @return
     */
    List<BaseBean> getPerformanceManagementRootList();

    /**
     * 根据父级id查找子级数据（激励绩效）
     *
     * @param parentId
     * @return
     */
    List<BaseBean> getPerformanceManagementByParentId(String parentId);

    /**
     * 校验同级目录下，是否有相同的名字
     *
     * @param name
     * @param parentId
     * @return
     */
    boolean isExistName(String name, String parentId);

    /**
     * 新增激励绩效
     *
     * @param info
     */
    void createPerformanceManagement(PerformanceManagement info);

    /**
     * 修改激励绩效
     *
     * @param info
     */
    void updatePerformanceManagement(PerformanceManagement info);

    PerformanceManagement getPerformanceManagementByKey(String key);

    PerformanceManagement getPerformanceManagementById(String id);
}
