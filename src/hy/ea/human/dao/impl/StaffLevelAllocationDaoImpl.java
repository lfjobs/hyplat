package hy.ea.human.dao.impl;

import hy.ea.bo.DictData;
import hy.ea.bo.ExamineProcessData;
import hy.ea.bo.human.StaffLevelAllocation;
import hy.ea.human.dao.StaffLevelAllocationDao;
import hy.ea.service.DictDataService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StaffLevelAllocationDaoImpl implements StaffLevelAllocationDao {

    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private BaseBeanDao baseBeanDao;

    @Resource
    private SessionFactory sessionFactory;
    @Resource
    private DictDataService dictDataService;


    @Override
    public PageForm getLevelAllocationList(String companyId,String staffID,Map<String,String> map,Integer pageNumber, Integer pageSize) {
        StringBuffer sql = new StringBuffer(128);
        // 0:全部 ， 1：我的申请  2：我的创建 3：待审核  4：已审核
        String status = map.get("status");
        List<String> params = new ArrayList<>();
        DictData dictData = dictDataService.getDictDataByParam("Allocation_ALL", staffID, "");
        getLevelAllocationSql(sql,params,companyId,staffID,dictData);
        if (dictData == null ){
            if ("0".equals(status)){
                sql.append("and (sla.CREATE_STAFF_ID =? or sla.STAFF_ID=? or INSTR(sla.REVIEWED,?)>0  OR sla.PENGING_REVIEW IS NOT NULL ) ");
                params.add(staffID);
                params.add(staffID);
                params.add(staffID);
            } else if ("1".equals(status)){
                sql.append(" and sla.STAFF_ID=?");
                params.add(staffID);
            } else if ("2".equals(status)){
                sql.append(" and sla.CREATE_STAFF_ID =?");
                params.add(staffID);
            } else if ("3".equals(status)){
                sql.append(" and sla.PENGING_REVIEW IS NOT NULL");
            } else if ("4".equals(status)){
                sql.append(" and INSTR(sla.REVIEWED,?)>0");
                params.add(staffID);
            }

        }
        sql.append(" ORDER BY sla.CREATE_DATE DESC");
        String hql2 = " SELECT COUNT(*) FROM (" + sql + ")";
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                (null != pageNumber ? pageNumber : 1), (pageSize==0?20:pageSize), sql.toString(),hql2, params.toArray());
        return pageForm;
    }

    @Override
    public StaffLevelAllocation getLevelAllocationById(String levelAllocationId,String companyId) {
        StringBuffer sql = new StringBuffer(" from StaffLevelAllocation where levelAllocationId=? and companyId=?");
        Object[] params = {levelAllocationId,companyId};
        return (StaffLevelAllocation)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
    }

    @Override
    public ExamineProcessData getExamineProcessDataById(String examineProcessId, String companyId) {
        StringBuffer sql = new StringBuffer(" from ExamineProcessData where examineProcessId=? and companyId=?");
        Object[] params = {examineProcessId,companyId};
        return (ExamineProcessData)baseBeanDao.getBeanByHqlAndParams(sql.toString(), params);
    }

    @Override
    public PageForm getRoleDataByParam(Map<String, String> param, String companyId, Integer pageNumber, Integer pageSize) {
        String roleName = param.get("roleName");
        StringBuffer hql = new StringBuffer(128);
        List<String> arr = new ArrayList<>();
        hql.append(" from CRole where companyID=?  ");
        arr.add(companyId);
        int size = param.size();
        if (size > 0){
            if (!"".equals(roleName) && !"".equals(roleName)) {
                hql.append(" and roleName like ? ");
                arr.add("%" + roleName + "%");
            }
        }
        hql.append(" order by CREATE_DATE");
        PageForm pageForm = baseBeanService.getPageForm( (null != pageNumber ? pageNumber : 1),(pageSize==0?20:pageSize),
                hql.toString(), arr.toArray());
        return pageForm;
    }

    @Override
    public List<BaseBean> getExamineProcessList(String levelAllocationId,String companyId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("SELECT EXAMINE_STATUS,EXAMINE_STAFF_NAME,TO_CHAR(EXAMINE_DATE, 'YYYY-MM-DD HH24:MI:SS')EXAMINE_DATE")
            .append(" FROM DT_EXAMINE_PROCESS_DATA WHERE PROCESS_ASSOCIATION_ID=? ")
            .append(" AND EXAMINE_STAFF_ID IS NOT NULL AND COMPANY_ID=?")
            .append(" UNION ALL")
            .append(" SELECT allocation.STATUS,WM_CONCAT(per.EXAMINE_DATA_NAME)REVIEWED,'' EXAMINE_DATE FROM DT_HR_STAFF_LEVEL_ALLOCATION allocation")
            .append(" LEFT JOIN  DT_EXAMINE_PERSON_DATA per")
            .append(" ON per.PROCESS_ASSOCIATION_ID = allocation.LEVEL_ALLOCATION_ID and per.EXAMINE_PROCESS_ID = allocation.EXAMINE_PROCESS_ID")
            .append(" AND per.COMPANY_ID = allocation.COMPANY_ID   WHERE allocation.STATUS !=1")
            .append("  And allocation.LEVEL_ALLOCATION_ID=? AND allocation.COMPANY_ID =? GROUP BY per.EXAMINE_DATA_NAME,allocation.STATUS ORDER BY EXAMINE_DATE");
        Object[] param = new Object[]{levelAllocationId,companyId,levelAllocationId,companyId};
        return baseBeanDao.getListBeanBySqlAndParams(sql.toString(),param);
    }


    public void getLevelAllocationSql(StringBuffer sql,List<String> params,String companyId,String staffID,DictData dictData){
        sql.append(" SELECT LEVEL_ALLOCATION_ID,TO_CHAR(sla.CREATE_DATE,'yyyy-mm-dd')CREATE_DATE,")
                .append(" sla.STAFF_NAME,oldLevel.SALARY_LEVEL_SERIAL SALARY_LEVEL_SERIAL_OLD, nowLevel.SALARY_LEVEL_SERIAL SALARY_LEVEL_SERIAL_NOW,")
                .append(" sla.STATUS,PENGING_REVIEW,REVIEWED,sla.STAFF_ID,sla.CREATE_STAFF_ID,")
                .append("nowLevel.GUARANTEE_SALARY,nowLevel.WELFARE_SALARY,nowLevel.OVERTIME_SALARY,nowLevel.SUMMARY_SALARY FROM ")
                /*获取已审核的数据*/
                .append(" (SELECT sla.*,exper.PENGING_REVIEW, review.REVIEWED FROM DT_HR_STAFF_LEVEL_ALLOCATION sla")
                .append(" LEFT JOIN ( SELECT PROCESS_ASSOCIATION_ID,COMPANY_ID,WM_CONCAT(EXAMINE_STAFF_ID)REVIEWED")
                .append(" FROM DT_EXAMINE_PROCESS_DATA WHERE ")
                .append(" EXAMINE_STAFF_ID IS NOT NULL ")
                .append(" AND COMPANY_ID=? GROUP BY PROCESS_ASSOCIATION_ID,COMPANY_ID )review")
                .append(" on sla.LEVEL_ALLOCATION_ID = review.PROCESS_ASSOCIATION_ID AND sla.COMPANY_ID=?")
                /*获取待审核的数据*/
                .append(" LEFT JOIN ( SELECT EXAMINE_PROCESS_ID,PROCESS_ASSOCIATION_ID,COMPANY_ID,WM_CONCAT(EXAMINE_DATA_ID)PENGING_REVIEW FROM DT_EXAMINE_PERSON_DATA")
                .append(" WHERE COMPANY_ID=?  ");
        params.add(companyId);
        params.add(companyId);
        params.add(companyId);
        if (dictData == null){
            sql.append("AND (EXAMINE_DATA_ID IN (")
                    .append(" SELECT ROLE_ID FROM DT_HR_STAFF_ROLE WHERE STAFF_ID=? AND COMPANY_ID=?")
                    .append(" ) OR EXAMINE_DATA_ID=? )");
            params.add(staffID);
            params.add(companyId);
            params.add(staffID);
        }
        sql.append(" GROUP BY EXAMINE_PROCESS_ID,PROCESS_ASSOCIATION_ID,COMPANY_ID ) exper")
                .append("  ON sla.LEVEL_ALLOCATION_ID = exper.PROCESS_ASSOCIATION_ID AND sla.EXAMINE_PROCESS_ID = exper.EXAMINE_PROCESS_ID ")
                .append(" AND sla.COMPANY_ID=? and sla.status =0 and sla.EXAMINE_PROCESS_ID IS NOT NULL")
                .append(")sla")
                .append(" LEFT JOIN DT_HR_SALARY_LEVEL oldLevel")
                .append(" ON sla.SALARY_LEVEL_ID_OLD = oldLevel.SALARY_LEVEL_ID and sla.COMPANY_ID = oldLevel.COMPANY_ID")
                .append(" LEFT JOIN DT_HR_SALARY_DATA nowLevel ")
                .append(" ON sla.SALARY_LEVEL_ID =  nowLevel.SALARY_LEVEL_ID and sla.COMPANY_ID = nowLevel.COMPANY_ID")
                .append(" WHERE sla.status != 9 and sla.COMPANY_ID = ? ");
        params.add(companyId);
        params.add(companyId);
    }


}
