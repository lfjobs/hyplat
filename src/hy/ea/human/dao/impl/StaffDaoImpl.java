package hy.ea.human.dao.impl;

import hy.ea.bo.human.*;
import hy.ea.bo.office.DocStaff;
import hy.ea.bo.office.Document;
import hy.ea.bo.office.DocumentTemplate;
import hy.ea.human.dao.StaffDao;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class StaffDaoImpl implements StaffDao {

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Resource
    private SessionFactory sessionFactory;
    @Override
    public PageForm getEntryStaffDataList(String companyId,Integer pageNumber, Integer pageSize,String queryName,COS cos) {
        StringBuffer sql = new StringBuffer(128);
        List<Object> params = new ArrayList<>();
        sql.append("SELECT distinct S.STAFFID,S.STAFFCODE,S.STAFFNAME,S.SEX,C.SALARY_LEVEL_ID,S.REFERENCE,")
                .append(" NVL(C.ENTRY_DATE,TO_CHAR(DA.REGISTERDATE, 'YYYY-MM-DD')) ENTRY_DATE,C.STATUS, ")
                .append("S.staffIdentityCard,l.SALARY_LEVEL_SERIAL FROM DT_HR_STAFF S")
                .append(" LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID ")
                .append(" LEFT JOIN DT_HR_SALARY_LEVEL l ON  C.SALARY_LEVEL_ID = l.SALARY_LEVEL_ID")
                .append(" LEFT JOIN DTAUDITION DA ON C.STAFFID = DA.STAFFID  WHERE DA.COMPANYID= ?")
                .append(" AND C.COMPANYID=?  ");
        params.add(companyId);
        params.add(companyId);
        if (!"".equals(queryName)){
            sql.append(" AND (S.STAFFNAME LIKE ? OR S.REFERENCE LIKE ?)");
            params.add("%" + queryName + "%");
            params.add("%" + queryName + "%");
        }
        if (cos != null){
            if (cos.getStatus() != null){
                sql.append(" and C.status = ?");
                params.add(cos.getStatus());
            }
            if (cos.getCosStatus() != null){
                sql.append(" and C.cosStatus = ?");
                params.add(cos.getCosStatus());
            }
        }
        sql.append(" ORDER BY   NVL(C.ENTRY_DATE,TO_CHAR(DA.REGISTERDATE, 'YYYY-MM-DD')) desc  NULLS LAST  ");
        String hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), sql.toString(),hql2, params.toArray());
        return pageForm;
    }

    @Override
    public Staff getStaffDataByStaffId(String staffId) {
        StringBuffer sql = new StringBuffer(" from Staff o where o.staffID=? ");
        List<Object> params = new ArrayList<>();
        params.add(staffId);
        Staff info = (Staff) baseBeanDao.getBeanByHqlAndParams(sql.toString(), params.toArray());
        return info;
    }

    @Override
    public COS getCosDataByParam(Map<String,String> param) {
        List<String> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("from COS where 1=1 ");
        if (!"".equals(param.get("companyId")) && param.get("companyId") != null){
            sql.append(" and companyID=?");
            list.add(param.get("companyId"));
        }
        if (!"".equals(param.get("staffId"))  && param.get("staffId") != null){
            sql.append(" and staffID=?");
            list.add(param.get("staffId"));
        }
        if (!"".equals(param.get("cosId"))  && param.get("cosId") != null){
            sql.append(" and cosID=?");
            list.add(param.get("cosId"));
        }
        if (!"".equals(param.get("cosStatus"))  && param.get("cosStatus") != null){
            sql.append(" and cosStatus=?");
            list.add(param.get("cosStatus"));
        }
        return (COS) baseBeanDao.getBeanByHqlAndParams(sql.toString(), list.toArray() );
    }

    @Override
    public COSDimissionStaff getCOSDimissionStaffDataByParam(String staffId, String companyId) {
        StringBuffer sql = new StringBuffer(" from COSDimissionStaff c where c.staffID=? and c.companyID=?");
        List<Object> params = new ArrayList<>();
        params.add(staffId);
        params.add(companyId);
        return (COSDimissionStaff) baseBeanDao.getBeanByHqlAndParams(sql.toString(), params.toArray());
    }


    @Override
    public List<BaseBean> getRoleListByStaffId(String staffId, String companyId) {
        StringBuffer sql = new StringBuffer(" from CRole where companyId=? and roleID in (select roleId from StaffRole where staffId=? and companyId=?)");
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), new Object[]{companyId,staffId,companyId});
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public List<BaseBean> getDeptListByStaffId(String staffId, String companyId) {
        StringBuffer sql = new StringBuffer(" from COrganization where companyID=? and organizationID in (select organizationID from COS where staffID=? and companyID=?)");
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), new Object[]{companyId,staffId,companyId});
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;


    }

    @Override
    public List<BaseBean> getCOSListByStaffId(String companyId, String staffId) {
        StringBuffer sql = new StringBuffer(" from COS where companyID=? and staffID=?");
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), new Object[]{companyId,staffId});
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }
    @Override
    public List<BaseBean> getPostListByStaffId(String staffId, String companyId) {
        StringBuffer sql = new StringBuffer(" from DepartmentPost where companyID=? and depPostID in (select depPostID from COS where staffID=? and companyID=?)");
        List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(sql.toString(), new Object[]{companyId,staffId,companyId});
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;


    }
    @Override
    public DepartmentPost getPostDataByStaffId(String companyId, String depPostID) {
        String hql = "from DepartmentPost where companyID=? and depPostID=?";
        return (DepartmentPost) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { companyId,depPostID});
    }

    @Override
    public Staff getStaffByStaffId(String staffId) {
        String hql = "from Staff where staffID=?";
        return (Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[] { staffId});
    }

    @Override
    public Audition getAuditionDataByParam(String staffId, String companyId) {
        String hql = "from Audition where  companyID = ? and staffID = ? ";
        return (Audition) baseBeanService.getBeanByHqlAndParams(
                hql, new Object[] { companyId, staffId });
    }

    @Override
    public List<UpLoadFile> getFileDataByStaffId(String staffId, String companyId) {
        StringBuffer sql = new StringBuffer(" from UpLoadFile o where o.fileType='staffData' and o.companyID=? and parmeterID=?  order by filedesc");
        List<UpLoadFile> list = this.getListBeanByHqlAndParams(sql.toString(), new Object[]{companyId,staffId});
        return CollectionUtils.isEmpty(list) ? new ArrayList<>() : list;
    }

    @Override
    public PageForm getSignData(String account, String beginDate,String endDate,Integer pageNumber, Integer pageSize) {
        StringBuffer hql1 = new StringBuffer(128);
        hql1.append("select signkey,signid,account,TO_CHAR(signdate,'yyyy-mm-dd hh24:mi:ss.ff')signdate,week from DT_WFJ_SIGN sign")
                .append(" LEFT JOIN DT_DATE_INFO dateInfo on TO_CHAR(sign.signDate, 'YYYY-MM-DD') = dateInfo.dates")
                .append(" where account=? and signDate between ? and ? order by signDate");
        String hql2 = " SELECT COUNT(*) FROM (" + hql1 + ")";
        Date begin = Utilities.getDateFromString(beginDate + " 00:00:00", "yyyy-MM-dd HH:ss:mm");
        Date end = Utilities.getDateFromString(endDate + " 23:59:59", "yyyy-MM-dd HH:ss:mm");
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), hql1.toString(),hql2,
                new Object[]{account,begin,end});

        return pageForm;
    }


    @Override
    public PageForm getNeedJoinStaffData(String companyId, Integer pageNumber, Integer pageSize) {
        String hql = "select s from Staff s,Audition a where a.staffID = s.staffID and" +
                " a.companyID = ? and a.status=21 " +
                " and a.ztState = 3 order by  a.auditionDate  desc nulls last";
        return baseBeanService.getPageForm((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 5 : pageSize),
                hql,"select count(s) "+hql.substring(hql.indexOf("from")),
                new String[]{companyId});
    }

    @Override
    public Object getEntryData(String companyId) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("SELECT COUNT(*)entryNum,SUM(case when status='00' then 1 else 0 end) AS partNum,")
            .append("SUM(case when status='01' then 1 else 0 end) AS fullNum FROM (")
            .append("SELECT DISTINCT C.STAFFID,C.STATUS FROM DT_HR_STAFF S LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID ")
            .append(" LEFT JOIN DTAUDITION DA ON C.STAFFID = DA.STAFFID  WHERE DA.COMPANYID= ? ")
            .append(" and  C.COMPANYID=? AND DA.STATUS ='22')DATA");

        return baseBeanService.getObjectBySqlAndParams(sql.toString(), new String[]{companyId,companyId});
    }

    @Override
    public List getEducationData(String companyId) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("SELECT  nvl(culturalDegree,'未填写')culturalDegree,COUNT(*)NUM FROM (")
                .append("SELECT DISTINCT S.STAFFID,S.culturalDegree FROM DT_HR_STAFF S LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID ")
                .append(" LEFT JOIN DTAUDITION DA ON C.STAFFID = DA.STAFFID  WHERE DA.COMPANYID= ? ")
                .append(" and  C.COMPANYID=? AND DA.STATUS ='22')DATA group by culturalDegree");
        return baseBeanService.getListBeanBySqlAndParams(sql.toString(), new String[]{companyId,companyId});
    }

    @Override
    public List getContractData(String companyId) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("  SELECT D.DICT_NAME name,SUM(case when INSTR(data.CONTRACT_TYPE,D.DICT_VALUE)>0 then 1 else 0 end) AS num FROM DT_DICT_DATA D, (")
                .append("SELECT DISTINCT S.STAFFID,c.CONTRACT_TYPE FROM DT_HR_STAFF S LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID ")
                .append(" LEFT JOIN DTAUDITION DA ON C.STAFFID = DA.STAFFID ")
                .append("  WHERE DA.COMPANYID= ? ")
                .append(" AND C.COMPANYID=? AND DA.STATUS ='22')DATA where DICT_TYPE=? GROUP BY D.DICT_NAME");
        return baseBeanService.getListBeanBySqlAndParams(sql.toString(), new String[]{companyId,companyId,"contractType"});

    }

    @Override
    public List getEntryPersonData(String companyId) {
        StringBuilder sql = new StringBuilder(128);
        sql.append(" SELECT d.month name,count(STAFFID)num FROM (")
            .append("SELECT TO_CHAR(ADD_MONTHS(SYSDATE, -LEVEL+1), 'YYYY-MM') AS month FROM dual CONNECT BY LEVEL <= 6 )d LEFT JOIN  ( ")
            .append(" SELECT distinct S.STAFFID, SUBSTR(NVL(C.ENTRY_DATE,TO_CHAR(DA.REGISTERDATE, 'YYYY-MM')),0,7) ENTRY_DATE FROM DT_HR_STAFF S ")
            .append("  LEFT JOIN DTCOS C ON  C.STAFFID = S.STAFFID ")
            .append(" LEFT JOIN DTAUDITION DA ON C.STAFFID = DA.STAFFID  WHERE DA.COMPANYID= ?")
            .append(" AND C.COMPANYID=? AND DA.STATUS ='22' )data ON  d.month = data.ENTRY_DATE")
            .append(" group by d.month order by d.month");
        return baseBeanService.getListBeanBySqlAndParams(sql.toString(), new String[]{companyId,companyId});
    }

    @Override
    public PageForm getStaffDocList(String companyId, Integer pageNumber, Integer pageSize, Map<String,String> paramsData) {
        StringBuffer sql = new StringBuffer(128);
        List<Object> params = new ArrayList<>();
        sql.append("SELECT distinct doc.DOCID,staff.STAFFID,staff.STAFFNAME,doc.CONTRACTTYPENAME,t.STATUS,doc.contractType,staff.SEX,")
                .append(" NVL(C.ENTRY_DATE,TO_CHAR(DA.REGISTERDATE, 'YYYY-MM-DD')) ENTRY_DATE,staff.REFERENCE,C.STATUS staffStatus,")
                .append(" TO_CHAR(t.CREATETIME, 'YYYY-MM-DD')CREATETIMEDOC,staff.staffCode,staff.staffIdentityCard,l.SALARY_LEVEL_SERIAL, ")
                .append(" t.subscriberID2,t.drafterID")
                .append(" FROM DT_OA_DocStaff doc ,DT_HR_STAFF staff ,DT_OA_DOCUMENT t, DTCOS c ,DTAUDITION da,DT_HR_SALARY_LEVEL l")
                .append(" where  doc.STAFFID = staff.STAFFID and doc.docId = t.docId and c.STAFFID = doc.STAFFID")
                .append(" and doc.STAFFID = da.STAFFID and c.SALARY_LEVEL_ID = l.SALARY_LEVEL_ID ")
                .append(" and doc.COMPANYID=? and c.companyid=? AND doc.STATUS='00'  ");
        params.add(companyId);
        params.add(companyId);
        String queryName = paramsData.get("queryName");
        if (!"".equals(queryName) ){
            sql.append(" AND (staff.STAFFNAME LIKE ? OR staff.REFERENCE LIKE ?)");
            params.add("%" + queryName + "%");
            params.add("%" + queryName + "%");
        }
        String staffID = paramsData.get("staffID");
        if (!"".equals(staffID) ){
            sql.append(" AND staff.STAFFID=? ");
            params.add(staffID);
        }
        sql.append(" order by CREATETIMEDOC desc,doc.contractType ASC ");
        String hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), sql.toString(),hql2, params.toArray());
        return pageForm;
    }

    @Override
    public DocStaff getStaffContractInfo(String staffId, String companyId, String docId) {
        String hql = "from DocStaff where staffID = ? and companyID=? and docId=?";
        return (DocStaff)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{staffId,companyId,docId});
    }

    @Override
    public Document getDocumentByDocId(String docId) {
        String hql = "from Document where  docId=?";
        return (Document)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{docId});
    }

    @Override
    public PageForm getResignStaffData(String companyId, Integer pageNumber, Integer pageSize, String queryName) {
        StringBuffer sql = new StringBuffer(128);
        List<Object> params = new ArrayList<>();
        sql.append("SELECT distinct d.STAFFID,s.STAFFCODE,s.STAFFNAME,s.SEX,c.SALARY_LEVEL_ID,l.SALARY_LEVEL_SERIAL,")
                .append("NVL(c.ENTRY_DATE,TO_CHAR(u.REGISTERDATE, 'YYYY-MM-DD')) ENTRY_DATE,TO_CHAR(d.dimissionDate, 'YYYY-MM-DD')dimissionDate,")
                .append("d.dimissioncause,d.issued,d.dimissionstatus,s.staffIdentityCard,s.reference,d.dimissionstatusName,d.createDate ")
                .append("FROM dtCOSDimissionStaff d LEFT JOIN DT_HR_STAFF s on d.STAFFID= s.StaffID ")
                .append("LEFT JOIN DTAUDITION u on d.COMPANYID = u.COMPANYID and d.STAFFID = u.STAFFID ")
                .append("LEFT JOIN DTCOS c ON  d.STAFFID = c.STAFFID AND  d.STAFFID = c.STAFFID ")
                .append("LEFT JOIN DT_HR_SALARY_LEVEL l ON  c.SALARY_LEVEL_ID = l.SALARY_LEVEL_ID ")
                .append("WHERE d.COMPANYID=? ");
        params.add(companyId);
        if (!"".equals(queryName)){
            sql.append(" AND (s.STAFFNAME LIKE ? OR s.REFERENCE LIKE ?)");
            params.add("%" + queryName + "%");
            params.add("%" + queryName + "%");
        }
        sql.append(" ORDER BY DIMISSIONDATE DESC NULLS LAST,d.createDate DESC  NULLS LAST ");
        String hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), sql.toString(),hql2, params.toArray());
        return pageForm;
    }

    public List<UpLoadFile> getListBeanByHqlAndParams(String hql, Object[] params) {
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
