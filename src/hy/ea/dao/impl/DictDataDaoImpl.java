package hy.ea.dao.impl;

import hy.ea.bo.DictData;
import hy.ea.bo.human.Staff;
import hy.ea.dao.DictDataDao;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DictDataDaoImpl implements DictDataDao {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private BaseBeanDao baseBeanDao;
    @Override
    public List<BaseBean> getDictDataListByType(String type, String companyId) {
        StringBuffer sql = new StringBuffer(" from DictData where dictType=?");
        List<String> param = new ArrayList<>();
        param.add(type);
        if (!"".equals(companyId)){
            sql.append(" and companyId=?");
            param.add(companyId);
        }
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(),param.toArray() );
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public DictData getDictDataByType(String type, String companyId) {
        StringBuffer sql = new StringBuffer(" from DictData where dictType=?");
        List<String> param = new ArrayList<>();
        param.add(type);
        if (!"".equals(companyId)){
            sql.append(" and companyId=?");
            param.add(companyId);
        }
        return (DictData) baseBeanDao.getBeanByHqlAndParams(sql.toString(), param.toArray());
    }

    @Override
    public DictData getDictDataByParam(String type, String dictValue, String companyId) {
        StringBuffer sql = new StringBuffer(" from DictData o where o.dictType=? and dictValue=? ");
        List<String> param = new ArrayList<>();
        param.add(type);
        param.add(dictValue);
        if (!"".equals(companyId)){
            sql.append(" and companyId=?");
            param.add(companyId);
        }
        return (DictData) baseBeanDao.getBeanByHqlAndParams(sql.toString(), param.toArray());
    }

    @Override
    public List<BaseBean> getDictListByParam(String type, String dictValue, String companyId) {
        StringBuffer sql = new StringBuffer(" from DictData o where o.dictType=?  and dictValue in ( ");
        List<String> param = new ArrayList<>();
        param.add(type);
        String[] value = dictValue.split(",");
        for (int i = 0; i < value.length; i++){
            sql.append("?");
            param.add(value[i]);
            if (i == value.length - 1){
                sql.append(")");
            } else {
                sql.append(",");
            }
        }
        if (!"".equals(companyId)){
            sql.append(" and companyId=?");
            param.add(companyId);
        }
        return baseBeanDao.getListBeanByHqlAndParams(sql.toString(),param.toArray());
    }

    @Override
    public DictData getDictDataByType(String type) {
        StringBuffer sql = new StringBuffer(" from DictData o where o.dictType=? ");
        return (DictData) baseBeanDao.getBeanByHqlAndParams(sql.toString(), new Object[]{type});
    }
}
