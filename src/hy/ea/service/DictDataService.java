package hy.ea.service;

import hy.ea.bo.DictData;
import hy.plat.bo.BaseBean;

import java.util.List;

public interface DictDataService {
    /**
     * 根据类型获取字典表数据
     * @param type
     * @return
     */
    List<BaseBean> getDictDataListByType(String type, String companyId);

    /**
     * 根据类型获取字典表数据
     * @param type
     * @return
     */
    DictData getDictDataByType(String type, String companyId);
    /**
     * 获取字典数据
     * @param type
     * @param dictValue
     * @param companyId
     * @return
     */
    DictData getDictDataByParam(String type, String dictValue, String companyId);

    List<BaseBean> getDictListByParam(String type, String dictValue, String companyId);

    DictData getDictDataByType(String type);
}
