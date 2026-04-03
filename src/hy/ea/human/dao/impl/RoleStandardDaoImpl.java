package hy.ea.human.dao.impl;

import hy.ea.bo.human.MoneyEmpower;
import hy.ea.bo.human.RoleStandard;
import hy.ea.human.dao.RoleStandardDao;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleStandardDaoImpl implements RoleStandardDao {
    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private BaseBeanDao baseBeanDao;


    @Override
    public List<RoleStandard> getRoleStandardList() {
        StringBuffer sql = new StringBuffer(" from RoleStandard order by createDate");
        List<RoleStandard> list = this.getListBeanByHqlAndParams(sql.toString(), null);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public boolean isExistRoleName(String roleName) {
        StringBuffer sql = new StringBuffer(" from RoleStandard  where roleName=?  ");
        Object[] params = {roleName};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? true : false;
    }

    @Override
    public RoleStandard getRoleStandardByRoleId(String roleId) {
        StringBuffer sql = new StringBuffer(" from RoleStandard  where roleId=? ");
        List<Object> params = new ArrayList<>();
        params.add(roleId);
        return (RoleStandard) baseBeanDao.getBeanByHqlAndParams(sql.toString(), params.toArray());
    }

    @Override
    public String deleteRoleStandardByRoleId(String roleId) {
        String sqlRole = "delete from RoleStandard where roleId = ?";
        List<Object[]> params = new ArrayList<>();
        params.add(new Object[]{roleId});
        baseBeanService.executeHqlsByParamsList(null, new String[]{sqlRole}, params);
        String sql = "delete from RoleStandardMenu where roleId = ? ";
        baseBeanService.executeHqlsByParamsList(null, new String[]{sql}, params);
        return "success";
    }

    @Override
    public List<BaseBean> getRoleStandardMenuByRoleId(String roleId) {
        StringBuffer sql = new StringBuffer(" from RoleStandardMenu  where roleId=?");
        Object[] params = {roleId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }


    @Override
    public List<BaseBean> getRoleStandardByEmpowerId(String empowerId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append(" from RoleStandard  where roleId in (SELECT roleId from MoneyEmpowerRole where empowerId=?) order by createDate");
        Object[] params = {empowerId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<BaseBean> getRoleStandardByNotEmpowerId(String empowerId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append(" from RoleStandard  where roleId not in (SELECT roleId from MoneyEmpowerRole where empowerId=?) order by createDate");
        Object[] params = {empowerId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), params);
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public String deleteEmpowerRoleDataByRoleId(String empowerId, String roleId) {
        List<Object[]> params = new ArrayList<>();
        params.add(new Object[]{empowerId,roleId});
        StringBuffer sql = new StringBuffer(" delete from MoneyEmpowerRole  where empowerId = ? and roleId = ? ");
        baseBeanService.executeHqlsByParamsList(null, new String[]{sql.toString()}, params);
        return "success";
    }

    @Override
    public MoneyEmpower getMoneyEmpowerByCompanyId(String companyId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append(" from MoneyEmpower where ccomType=(select ccomtype from Company where companyID=?) and status = 1");
        return (MoneyEmpower) baseBeanDao.getBeanByHqlAndParams(sql.toString(), new Object[]{companyId});
    }

    @Override
    public List<BaseBean> getRoleStandardMenuByEmpowerId(String empowerId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("from RoleStandardMenu where roleId in (select roleId from MoneyEmpowerRole where empowerId=?)")
                .append(" and menuId in (select menuId from MoneyEmpowerMenu where empowerId=?)");
        return baseBeanDao.getListBeanByHqlAndParams(sql.toString(), new Object[]{empowerId,empowerId});
    }


    public List<RoleStandard> getListBeanByHqlAndParams(String hql, Object[] params) {
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        if (params != null && params.length > 0) {
            int i = 0;
            for (Object param : params) {
                query.setParameter(i++, param);
            }
        }
        return query.list();
    }
}
