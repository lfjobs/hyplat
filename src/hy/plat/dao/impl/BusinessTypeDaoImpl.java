package hy.plat.dao.impl;

import cn.jpush.api.utils.StringUtils;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.BusinessType;
import hy.plat.dao.BusinessTypeDao;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BusinessTypeDaoImpl implements BusinessTypeDao {
    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Override
    public List<BusinessType> getBusinessTypeByTypePID(String typePID) {
        StringBuffer sql = new StringBuffer(" from BusinessType o where o.status='1' ");
        List<Object> params = new ArrayList<>();
        if (StringUtil.isNotEmpty(typePID)) {
            sql.append(" and o.typePID=? ");
            params.add(typePID);
        }
//        sql.append(" order by o.sortNum");
        sql.append(" order by o.typeNum ");

        List<BusinessType> list = this.getListBeanByHqlAndParams(sql.toString(), params.toArray());
        return list;
    }

    @Override
    public List<BusinessType> getBusinessTypeRootList() {
        StringBuffer sql = new StringBuffer(" from BusinessType o where o.status=1 and o.typeLevel=2  order by sortNum");
        List<BusinessType> list = this.getListBeanByHqlAndParams(sql.toString(), null);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<BusinessType> getBusinessTypeDivisionList() {
        StringBuffer sql = new StringBuffer(" from BusinessType o where o.status=1 and o.typeLevel=1 order by sortNum");
        List<BusinessType> list = this.getListBeanByHqlAndParams(sql.toString(), null);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }


    @Override
    public void createBusinessType(BusinessType info) {
        baseBeanDao.save(info);
    }

    @Override
    public void updateBusinessType(BusinessType info) {
        baseBeanDao.update(info);
    }

    @Override
    public BusinessType getBusinessTypeById(String typeKey) {
        return (BusinessType) baseBeanDao.getBeanByKey(BusinessType.class, typeKey);
    }

    @Override
    public BusinessType getBusinessTypeByTypeId(String typeId) {
        StringBuffer sql = new StringBuffer(" from BusinessType o where o.status=1 and o.typeId=? ");
        List<Object> params = new ArrayList<>();
        params.add(typeId);

        BusinessType info = (BusinessType) baseBeanDao.getBeanByHqlAndParams(sql.toString(), params.toArray());
        return info;
    }

    @Override
    public List<BusinessType> getBusinessTypeByNum(String typeNum) {
        StringBuffer sql = new StringBuffer(" from BusinessType o where o.status=1 and o.typeNum=? ");
        List<Object> params = new ArrayList<>();
        params.add(typeNum);
//        sql.append(" order by o.sortNum ");

        sql.append(" order by o.typeNum ");

        List<BusinessType> list = this.getListBeanByHqlAndParams(sql.toString(), params.toArray());
        return list;
    }

    @Override
    public boolean isExistTypeNum(String typeNum) {
        List<BusinessType> list = this.getBusinessTypeByNum(typeNum);
        return CollectionUtils.isEmpty(list) ? true : false;
    }

    public List<BusinessType> getListBeanByHqlAndParams(String hql, Object[] params) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (params != null && params.length > 0) {
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
        }
        return query.list();
    }

    @Override
    public int getMaxSortNumBy(String typePID) {
        StringBuffer sql = new StringBuffer(" select nvl(max(sortNum),0) from BusinessType o where o.typePID=? ");
        List<Object> params = new ArrayList<>();
        params.add(typePID);
        int sortNum = baseBeanDao.getConutByByHqlAndParams(sql.toString(), params.toArray());
        return sortNum;
    }

    @Override
    public int getMaxSortNumByTypeLevel(Integer typeLevel) {
        StringBuffer sql = new StringBuffer(" select nvl(max(sortNum),0) from BusinessType o where o.typeLevel=? ");
        List<Object> params = new ArrayList<>();
        params.add(typeLevel);
        int sortNum = baseBeanDao.getConutByByHqlAndParams(sql.toString(), params.toArray());
        return sortNum;
    }

    @Override
    public List<BusinessType> getBusinessTypeByPID(String typePID) {
        StringBuffer sql = new StringBuffer(" from BusinessType o where o.status=1  ");
        List<Object> params = new ArrayList<>();

        if(StringUtils.isNotEmpty(typePID)){
            sql.append(" and o.typePID=? ");
            params.add(typePID);
        }else{
            sql.append(" and o.typeLevel=2 ");
        }
        sql.append(" order by o.sortNum ");

        List<BusinessType> list = this.getListBeanByHqlAndParams(sql.toString(), params.toArray());
        return list;
    }
}
