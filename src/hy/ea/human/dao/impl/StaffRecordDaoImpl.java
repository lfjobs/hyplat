package hy.ea.human.dao.impl;

import hy.ea.human.dao.StaffRecordDao;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class StaffRecordDaoImpl implements StaffRecordDao {

    @Resource
    private BaseBeanService baseBeanService;
    @Override
    public Object getResumeDataByResumeID(String resumeID) {
        StringBuilder sb = new StringBuilder(128);
        sb.append("select r.resumeID,j.position,f.staffname,j.region,f.headimage,f.sex,f.nativePlace,");
        sb.append("f.staffAddress,f.referenceOrganization,f.reference,j.salary,r.evaluate,f.birthday");
        sb.append(",nvl(floor(months_between(sysdate, to_date(birthday, 'yyyy-mm-dd')) / 12),0)");
        sb.append(" from dtresumeS r");
        sb.append(" left join dtJobWanted j on r.resumeID = j.resumeID");
        sb.append(" left join dt_hr_staff f on f.staffID = r.staffID");
        sb.append(" where r.resumeID = ?");
        return baseBeanService.getObjectBySqlAndParams(sb.toString(), new Object[]{resumeID});
    }

    @Override
    public List<BaseBean> getEducationalByResumeID(String resumeID) {
        String hqledu = "from Educational where resumeID = ? order by admissionTime";
        return baseBeanService.getListBeanByHqlAndParams(hqledu, new Object[]{resumeID});
    }

    @Override
    public List<BaseBean> getStaffResumeByResumeID(String resumeID) {
        String hqlsr = "from StaffResume where resumeID = ? order by startTime";
        return baseBeanService.getListBeanByHqlAndParams(hqlsr, new Object[]{resumeID});
    }

    @Override
    public List<BaseBean> getInterviewResultByStaffID(String companyId, String staffID) {
        StringBuilder sql = new StringBuilder(128);
        sql.append("WITH staff_data AS (")
            .append(" SELECT s.staffID,s.staffName,s.staffIdentityCard,a.zpState,a.bsState,a.ksState")
            .append(" FROM dt_hr_Staff s JOIN dtAudition a ON s.staffID = a.staffID")
            .append(" WHERE a.companyID = ? AND s.staffID =? AND a.zpState IS NOT NULL)")
            .append(" SELECT staffID, staffName, staffIdentityCard, '综合判定' AS stateType,")
            .append(" CASE WHEN zpState = '1' THEN '合格' ELSE '不合格' END AS state FROM staff_data")
            .append(" UNION ALL ")
            .append(" SELECT staffID, staffName, staffIdentityCard, '笔试' AS stateType,")
            .append(" CASE WHEN bsState = '1' THEN '合格' ELSE '不合格' END AS state FROM staff_data")
            .append(" UNION ALL")
            .append(" SELECT staffID, staffName, staffIdentityCard, '口试' AS stateType,")
            .append(" CASE WHEN ksState = '1' THEN '合格' ELSE '不合格' END AS state FROM staff_data");
        return baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{companyId,staffID});
    }

    @Override
    public List<BaseBean> getInterviewRegistrationByStaffId(String companyId, String staffID) {

        StringBuilder sql = new StringBuilder(128);
         sql.append(" SELECT s.staffID,s.staffName,s.staffIdentityCard,")
                .append(" CAST('面试登记' AS VARCHAR(20)) AS stateType,")
                .append(" CAST(CASE WHEN a.status='01' THEN '待登记' ELSE '已登记' END AS VARCHAR(20)) AS status")
                .append(" FROM dt_hr_Staff s ,dtAudition a WHERE s.staffID = a.staffID")
                .append("  and a.companyID = ? AND s.staffID =?");

       return baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{companyId,staffID});

    }


}
