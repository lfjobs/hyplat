package hy.ea.human.dao.impl;

import hy.ea.bo.human.salary.DateInfo;
import hy.ea.bo.human.salary.SalaryData;
import hy.ea.bo.human.salary.SalaryDictData;
import hy.ea.bo.human.salary.SalaryLevelList;
import hy.ea.human.dao.SalaryLevelDao;
import hy.ea.office.bo.HrSalaryLevel;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SalaryLevelDaoImpl implements SalaryLevelDao {

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Override
    public PageForm getSalaryLevelList(String companyId, Integer pageNumber, Integer pageSize) {
        StringBuffer hql = new StringBuffer(128);
        hql.append("select levelList.LEVEL_LIST_ID,levelList.CREATE_DATE,levelList.LEVEL_SERIAL,levelList.LEVEL_NAME,staff.STAFFNAME,levelList.LEVEL_NUM ")
                .append(" from DT_HR_SALARY_LEVEL_LIST levelList left join DT_HR_STAFF staff  ")
                .append(" on levelList.CREATE_STAFF_ID = staff.STAFFID where levelList.COMPANY_ID = ? order by levelList.CREATE_DATE desc ");
        String hql2 = " SELECT COUNT(*) FROM (" + hql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), hql.toString(),hql2, new String[]{companyId});
        return pageForm;
    }

    @Override
    public PageForm getSalaryLevelByNum(Integer serialNum, String companyId, Integer pageNumber, Integer pageSize) {
        StringBuffer hql = new StringBuffer(128);
        hql.append("select salaryLevel.SALARY_LEVEL_ID,salaryLevel.CREATE_DATE,salaryLevel.SALARY_LEVEL_SERIAL,salaryLevel.SALARY_LEVEL_NUM,staff.STAFFNAME ")
                .append(" from DT_HR_SALARY_LEVEL salaryLevel left join DT_HR_STAFF staff  ")
                .append(" on salaryLevel.CREATE_STAFF_ID = staff.STAFFID where COMPANY_ID = ? ")
                .append(" AND TO_NUMBER(SALARY_LEVEL_SERIAL) <= ?")
                .append(" order by TO_NUMBER(salaryLevel.SALARY_LEVEL_SERIAL) desc");
        String hql2 = " SELECT COUNT(*) FROM (" + hql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), hql.toString(),hql2, new Object[]{companyId,serialNum});
        return pageForm;
    }

    @Override
    public PageForm getLevelSalaryDataListByCompanyId(String companyId, Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(" from SalaryData where companyId=? order by TO_NUMBER(salaryLevelSerial) desc");
        Object[] params = {companyId};
        return baseBeanService.getPageForm((null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize),sql.toString(),params);
    }

    @Override
    public PageForm getLevelDataPageFormByCompanyId(String companyId, Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(" from HrSalaryLevel where companyId=? order by TO_NUMBER(salaryLevelSerial) desc");
        Object[] params = {companyId};
        return baseBeanService.getPageForm((null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize),sql.toString(),params);
    }

    @Override
    public List<BaseBean> getDictDataListByType(String type,String companyId) {
        StringBuffer sql = new StringBuffer(" from SalaryDictData where dictType=? and companyId=?");
        Object[] params = {type,companyId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(),params );
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<BaseBean> getDictDataListByCompanyId(String companyId) {
        StringBuffer sql = new StringBuffer(" from SalaryDictData where companyId=? order by dictType");
        Object[] params = {companyId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(),params );
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public DateInfo getDateInfoByDate(String date) {
        StringBuffer sql = new StringBuffer(" from DateInfo where date=? ");
        List<Object> params = new ArrayList<>();
        params.add(date);
        DateInfo info = (DateInfo) baseBeanDao.getBeanByHqlAndParams(sql.toString(), params.toArray());
        return info;
    }

    @Override
    public SalaryDictData getDictDataByDictName(String dictName,String dictType, String companyId) {
        StringBuffer sql = new StringBuffer(" from SalaryDictData where dictName=? and dictType=? and companyId=?");
        Object[] params = {dictName,dictType,companyId};
        SalaryDictData  dictData = (SalaryDictData)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
        return dictData;
    }

    @Override
    public List<BaseBean> getAllSalaryLevelList(String companyId) {
        StringBuffer sql = new StringBuffer(" from HrSalaryLevel where companyId=? order by TO_NUMBER(salaryLevelSerial) ");
        Object[] params = {companyId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(),params );
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<BaseBean> getLevelSalaryByCompanyId(String companyId) {
        StringBuffer sql = new StringBuffer(" from SalaryData where 1=1 ");
        if (!"".equals(companyId) ){
            sql.append(" and companyId = ?");
        }
        sql.append("order by companyId,TO_NUMBER(salaryLevelSerial) ");
        Object[] params = {companyId};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(),params );
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<BaseBean> getDictDataListByDictName(String dictName) {
        StringBuffer sql = new StringBuffer(" from SalaryDictData where dictName=?");
        Object[] params = {dictName};
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(),params );
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public HrSalaryLevel getSalaryLevelById(String salaryLevelId) {
        StringBuffer sql = new StringBuffer(" from HrSalaryLevel where salaryLevelId=?");
        Object[] params = {salaryLevelId};
        return (HrSalaryLevel)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
    }

    @Override
    public HrSalaryLevel getSalaryLevelBySerial(String companyId,String salaryLevelSerial) {
        StringBuffer sql = new StringBuffer(" from HrSalaryLevel where companyId=? and salaryLevelSerial=?");
        Object[] params = {companyId,salaryLevelSerial};
        return (HrSalaryLevel)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
    }

    @Override
    public SalaryLevelList getSalaryLevelListById(String companyId,String levelListId) {
        StringBuffer sql = new StringBuffer(" from SalaryLevelList where companyId=? and levelListId=?");
        Object[] params = {companyId,levelListId};
        return (SalaryLevelList)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
    }

    @Override
    public SalaryData getSalaryLevelDataBySalaryLevelId(String salaryLevelId, String companyId) {
        StringBuffer sql = new StringBuffer(" from SalaryData where companyId=? and salaryLevelId=?");
        Object[] params = {companyId,salaryLevelId};
        return (SalaryData)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
    }

    @Override
    public SalaryData getSalaryLevelDataBySalaryLevelSerial(String salaryLevelSerial, String companyId) {
        StringBuffer sql = new StringBuffer(" from SalaryData where companyId=? and salaryLevelSerial=?");
        Object[] params = {companyId,salaryLevelSerial};
        return (SalaryData)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
    }

    @Override
    public SalaryData getSalaryLevelDataByStaffId(String staffId, String companyId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append(" from SalaryData where companyId=? and salaryLevelId in (select salaryLevelId from COS where companyID=? and staffID=?)");
        Object[] params = {companyId,companyId,staffId};
        return (SalaryData)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
    }
}
