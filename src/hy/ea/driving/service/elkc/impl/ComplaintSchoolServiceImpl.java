package hy.ea.driving.service.elkc.impl;

import hy.ea.bo.DrivingSchool.elyc.ComplaintSchool;
import hy.ea.driving.service.elkc.ComplaintSchoolService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
@Service
public class ComplaintSchoolServiceImpl implements ComplaintSchoolService{
    @Resource
    private BaseBeanService bbservice;
    @Resource
    private ServerService serverService;
    /**
     * 查询以投诉
     * @param staffid
     * @return
     */
    @Override
    public List<Object> findComplaint(String staffid) {
        String sql="select d.photo,t.name,s.teacherid ,s.staffid ,s.clId from tb_elyc_Complaint_School s,dt_hr_Staff d ,TB_JP_TEACHER t where d.staffid=t.staffid and t.teacherid=s.teacherid and s.staffid=? ";
       List<Object> list= bbservice.getListBeanBySqlAndParams(sql,new Object[]{staffid});
        return list;
    }

    /**
     * 查询受教的教练
     * @param staffid
     * @return
     */
    @Override
    public List<Object> findTeacher(String staffid) {
        String sql="select DISTINCT d.photo,t.name,t.teacherid,o.staffid,o.studentid ,t.companyid,o.studentName,d.reference from TB_Elyc_Order_Record o,TB_JP_TEACHER t ,dt_hr_Staff d where d.staffid=t.staffid and t.teacherid=o.teacherid and o.staffid=?";
        List<Object> list=bbservice.getListBeanBySqlAndParams(sql,new Object[]{staffid});
        return list;
    }

    /**
     * 添加被投诉的教练
     */
    @Override
    public Boolean addComplaint(String companyId,String teacherId,String studentId,String staffId,String content,String studentName) {
        Boolean b=true;
        List<BaseBean> baseBeanList=new ArrayList<>();
        ComplaintSchool csl=new ComplaintSchool();
        csl.setClId(serverService.getServerID("complaint"));
        csl.setCompanyId(companyId);
        csl.setTeacherId(teacherId);
        csl.setStudentId(studentId);
        csl.setStaffId(staffId);
        csl.setContent(content);
        csl.setComplaintDate(new Date());
        csl.setStudentName(studentName);
        baseBeanList.add(csl);
        bbservice.saveBeansListAndexecuteSqlsByParams(baseBeanList,null,null);
            return b;
    }

    @Override
    public List<Object> teacher(String staffid,String teachertid,String clId) {
        String sql="select  d.photo,t.name,s.teacherid ,s.studentid,s.content,s.companyreply,s.studentName,s.clId,t.subject,s.complaintDate from tb_elyc_Complaint_School s,dt_hr_Staff d ,TB_JP_TEACHER t where d.staffid=t.staffid and t.teacherid=s.teacherid and s.staffid=? and s.teacherid=? and s.clId=?";
        List<Object> list=bbservice.getListBeanBySqlAndParams(sql,new Object[]{staffid,teachertid,clId});
        return list;
    }
    @Override
    public List<Object> teachers(String teachertid,String clId) {
        String sql="select  d.photo,t.name,s.teacherid ,s.studentid,s.content,s.companyreply,s.studentName,s.clId,t.subject,s.complaintDate from tb_elyc_Complaint_School s,dt_hr_Staff d ,TB_JP_TEACHER t where d.staffid=t.staffid and t.teacherid=s.teacherid and  s.teacherid=? and s.clId=?";
        List<Object> list=bbservice.getListBeanBySqlAndParams(sql,new Object[]{teachertid,clId});
        return list;
    }

    @Override
    public List<Object> allTeacher(String companyid) {
        String sql="select  d.photo,t.name,s.teacherid ,s.staffid,s.clId,s.complaintDate,s.companyReply from tb_elyc_Complaint_School s,dt_hr_Staff d ,TB_JP_TEACHER t where d.staffid=t.staffid and t.teacherid=s.teacherid and s.companyid=? order by s.complaintDate desc";
        List<Object> list=bbservice.getListBeanBySqlAndParams(sql,new Object[]{companyid});
        return list;
    }

    @Override
    public Boolean replyComplaint(String clid, String companyreply) {
        Boolean b=true;
        String sql=" from ComplaintSchool  where clId=?";
        ComplaintSchool csl= (ComplaintSchool) bbservice.getBeanByHqlAndParams(sql,new Object[]{clid});
        if (csl!=null){
            List<BaseBean> baseBeanList=new ArrayList<>();
            csl.setCompanyReply(companyreply);
            baseBeanList.add(csl);
            bbservice.saveBeansListAndexecuteSqlsByParams(baseBeanList,null,null);
        }
        return b;
    }


}
