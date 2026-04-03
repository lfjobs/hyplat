package hy.ea.human.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.production.recruit.CollectThing;
import hy.ea.bo.production.recruit.TalentPool;
import hy.ea.bo.production.resume.ResumeS;
import hy.ea.human.dao.StaffRecordDao;
import hy.ea.human.service.StaffRecordService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StaffRecordServiceImpl implements StaffRecordService {
    @Resource
    private BaseBeanService baseBeanService;

    @Resource
    private StaffRecordDao staffRecordDao;

    @Override
    public void getResumeDataByStaffID(String staffID) {
        ResumeS persion = (ResumeS)baseBeanService.getBeanByHqlAndParams("from ResumeS where staffID=? and nvl(state,'00') =?",new Object[]{staffID,"00"});
        String resumeID = persion.getResumeID();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cus = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        String iscol = "no";
        String istou = "no";
        if (cus != null) {
            String hqlc = "from CollectThing where id = ? and staffID = ?";
            CollectThing ct = (CollectThing) baseBeanService.getBeanByHqlAndParams(hqlc, new Object[]{resumeID, cus.getStaffid()});
            if (ct != null) {
                iscol = "yes";
            } else {
                iscol = "no";
            }
            String hqlt = "from TalentPool t where t.staffID = ? and resumeID = ?";
            TalentPool tp = (TalentPool) baseBeanService.getBeanByHqlAndParams(hqlt, new Object[]{cus.getStaffid(), resumeID});
            if (tp != null) {
                istou = "yes";
            } else {
                istou = "no";
            }

        }
        request.setAttribute("iscol", iscol);
        request.setAttribute("istou", istou);
        // 简历数据
        Object obj = staffRecordDao.getResumeDataByResumeID(resumeID);
        //教育经历
        List<BaseBean> edulist = staffRecordDao.getEducationalByResumeID(resumeID);

        List<BaseBean> srlist = staffRecordDao.getStaffResumeByResumeID(resumeID);

        Object edu = null;
        if (srlist.size() != 0) {
            String sql = "select max(endTime),min(startTime),max(startTime) from dt_hr_staff_Resume where resumeID = ?";
            edu = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{resumeID});
        }

        request.setAttribute("obj", obj);
        request.setAttribute("edu", edu);
        request.setAttribute("edulist", edulist);
        request.setAttribute("srlist", srlist);



    }

    @Override
    public String getInterviewResultByStaffID(String companyId, String staffID) {
        List<BaseBean> listData = staffRecordDao.getInterviewResultByStaffID(companyId,staffID);
        JSONArray obj = JSONArray.fromObject(listData);
        return obj.toString();
    }

    @Override
    public String getInterviewRegistrationByStaffId(String companyId, String staffID) {
        List<BaseBean> listData = staffRecordDao.getInterviewRegistrationByStaffId(companyId,staffID);
        JSONArray obj = JSONArray.fromObject(listData);
        return obj.toString();
    }
}