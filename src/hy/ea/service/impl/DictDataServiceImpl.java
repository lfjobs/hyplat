package hy.ea.service.impl;

import hy.ea.bo.DictData;
import hy.ea.dao.DictDataDao;
import hy.ea.service.DictDataService;
import hy.plat.bo.BaseBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class DictDataServiceImpl implements DictDataService {
    @Resource
    private DictDataDao dictDataDao;

    @Override
    public List<BaseBean> getDictDataListByType(String type, String companyId) {
        return dictDataDao.getDictDataListByType(type, companyId);
    }

    @Override
    public DictData getDictDataByType(String type, String companyId) {
        return dictDataDao.getDictDataByType(type, companyId);
    }

    @Override
    public DictData getDictDataByParam(String type, String dictValue, String companyId) {
        return dictDataDao.getDictDataByParam(type, dictValue, companyId);
    }

    @Override
    public List<BaseBean> getDictListByParam(String type, String dictValue, String companyId) {
        return dictDataDao.getDictListByParam(type, dictValue, companyId);
    }

    @Override
    public DictData getDictDataByType(String type) {
        return dictDataDao.getDictDataByType(type);
    }
}
