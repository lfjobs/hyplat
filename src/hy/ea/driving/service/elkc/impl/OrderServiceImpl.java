package hy.ea.driving.service.elkc.impl;

import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.ea.bo.DrivingSchool.elyc.TbElycOrderDetailTime;
import hy.ea.bo.DrivingSchool.elyc.TbElycSchoolRest;
import hy.ea.bo.SubjectHour;
import hy.ea.driving.service.elkc.OrderService;
import hy.ea.util.DateUtil;
import hy.ea.util.bean.Bean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4 0004.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private BaseBeanService bbservice;
    private PageForm pageForm;
    /**
     * 查询该驾校是否正常
     * @param companyId
     * @return
     */
    public Integer getOnHoliday(String companyId) {
        String hql="from TbElycSchoolRest where companyId=?";
        TbElycSchoolRest tbElycSchoolRest=(TbElycSchoolRest)bbservice.getBeanByHqlAndParams(hql, new Object[]{companyId});
        Date date = DateUtil.string2Time(DateUtil.getCurrentDate());
        Date startDate = tbElycSchoolRest.getHolidayBegin();
        Date endDate = tbElycSchoolRest.getHolidayEnd();
        if("1".equals(tbElycSchoolRest.getStatus())) {
            if (date.compareTo(startDate) <= 0 && date.compareTo(endDate) <= 0) {

                return 1;
            }
        }
        return 0;
    }

    /**
     * 查询该驾校里所有的教练
     * @param companyId
     * @param subject
     * @return
     */
    @Override
    public List<Object> teachers(String companyId,String subject) {
        StringBuffer sql=new StringBuffer();
        sql.append("select hs.photo,t.Name,hs.staffID,t.companyId,t.teacherId ,");
        sql.append(" cast( (cast(sum(ev.totalScore) as decimal(18, 0))/2/cast(g.shu as decimal(18, 0))) as int) as b ");
        sql.append(" from TB_JP_TEACHER t left  join dt_hr_Staff hs  on  hs.staffID=t.staffID left join Tb_Elyc_Teacher_Evaluate ev on  ev.teacherid=t.teacherid left join");
        sql.append("  (SELECT COUNT(*) as shu,t.teacherid as id from  dt_hr_Staff hs ,TB_JP_TEACHER t, Tb_Elyc_Teacher_Evaluate ev where hs.staffID=t.staffID and ev.teacherid=t.teacherid\n" +
                "   and t.companyID=? group by t.teacherid)  g on g.id=t.teacherid");
        sql.append(" where  t.companyID=?");
        sql.append(" and  t.subject=?");
        sql.append(" group by  hs.photo,t.Name,hs.staffID,t.companyId,t.teacherId,g.shu ");
        List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{companyId,companyId,subject});
        return list;
    }

    /**
     * 模糊查询该驾校里的教练
     * @param name
     * @param companyId
     * @return
     */
    @Override
    public List<Object> teacher(String name,String companyId,String subject) {
        StringBuffer sql=new StringBuffer();
        sql.append("select hs.photo,t.Name,hs.staffID,t.companyId,t.teacherId ,");
        sql.append(" cast( (cast(sum(ev.totalScore) as decimal(18, 0))/2/cast(g.shu as decimal(18, 0))) as int) as b ");
        sql.append(" from TB_JP_TEACHER t left  join dt_hr_Staff hs  on  hs.staffID=t.staffID left join Tb_Elyc_Teacher_Evaluate ev on  ev.teacherid=t.teacherid left join");
        sql.append("  (SELECT COUNT(*) as shu,t.teacherid as id from  dt_hr_Staff hs ,TB_JP_TEACHER t, Tb_Elyc_Teacher_Evaluate ev where hs.staffID=t.staffID and ev.teacherid=t.teacherid\n" +
                "   and t.companyID=? group by t.teacherid)  g on g.id=t.teacherid");
        sql.append(" where t.Name like ?");
        sql.append(" and  t.companyID=?");
        sql.append(" and  t.subject=?");
        sql.append(" group by  hs.photo,t.Name,hs.staffID,t.companyId,t.teacherId,g.shu ");
        List<Object> obj=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[]{companyId,"%"+name+"%",companyId,subject});
        return obj;
    }

    /**
     * 教练信息
     * @param teacherId
     * @param companyId
     * @return
     */
    @Override
    public Object coachingInformation(String teacherId,String companyId) {
        StringBuffer sql=new StringBuffer();
        sql.append("select t.name as aname,t.sex,t.dripermitted,t.mobile,jc.name as dname ,t.subject,hs.photo from TB_JP_TEACHER t,TB_JP_COMPANY jc,dt_hr_Staff hs");
        sql.append(" where jc.company_id=t.companyid ");
        sql.append(" and hs.staffID=t.staffID");
        sql.append(" and t.teacherid=?");
        sql.append(" and t.companyid=?");
        List<Bean> obj=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[]{teacherId,companyId});
        return obj;
    }

    /**
     * 按教练可预约时间
     * @param teacherId
     * @param companyId
     * @param StartTime
     * @return
     */
    @Override
    public List<Bean> timeOfAppointment(String teacherId ,String companyId,String StartTime) {
        String sql="select t.status,t.lessionStartTime,t.lessionEndTime ,t.teacherId ,t.odtId from Tb_Elyc_Order_Detail_Time t where t.teacherId=? and t.companyId=? and to_char(t.lessionStartTime,'yyyy-mm-dd')=?";
        List<Bean> list=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {teacherId,companyId,StartTime});

        return list;
    }

    /**
     * 按教练可预约时间
     * @param teacherId
     * @param companyId
     * @param StartTime
     * @return
     */
    @Override
    public List<Bean> timeOfAppointment1(String teacherId ,String companyId,String StartTime) {
        String sql="select t.status,to_char(t.lessionStartTime,'HH24'),to_char(t.lessionEndTime,'HH24') ,t.teacherId ,t.odtId,to_char(t.lessionStartTime,'yyyy-MM-dd HH24:mi:ss') as StartTime ,to_char(t.lessionEndTime,'yyyy-MM-dd HH24:mi:ss') as EndTime from Tb_Elyc_Order_Detail_Time t where t.teacherId=? and t.companyId=?  and to_char(t.lessionStartTime,'yyyy-mm-dd')=?";
        List<Bean> list=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {teacherId,companyId,StartTime});

        return list;
    }

    /**
     * 按时间可预约时间
     * @param companyId
     * @param StartTime
     * @param teacherId
     * @return
     */
    @Override
    public List<Bean> ppointment(String companyId,String StartTime,String teacherId) {
        StringBuffer sql=new StringBuffer();
        sql.append("select hs.photo,t.Name,hs.staffID,t.companyId,t.teacherId ,t.subject, tb.status,tb.lessionStartTime,tb.lessionEndTime ,(to_char(tb.lessionEndTime,'HH24')-to_char(tb.lessionStartTime,'HH24')) as hours from dt_hr_Staff hs ,TB_JP_TEACHER t ,Tb_Elyc_Order_Detail_Time tb");
        sql.append("  where hs.staffID=t.staffID and  t.teacherId=tb.teacherId");
        sql.append("  and t.companyId=?  and to_char(tb.lessionStartTime,'yyyy-mm-dd')=? and t.teacherId=?");
        List<Bean> list=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {companyId,StartTime,teacherId});
        return list;
    }

    /**
     * 所有的教练信息
     * @param companyId
     * @param subject
     * @return
     */
    @Override
    public List<Object> allCoachingInformation(String companyId,String subject) {
        StringBuffer sql=new StringBuffer();
        sql.append("select t.name as aname,t.sex,t.dripermitted,t.mobile,jc.name as dname ,t.subject ,t.teacherId,hs.photo from TB_JP_TEACHER t,TB_JP_COMPANY jc, dt_hr_Staff hs");
        sql.append(" where jc.company_id=t.companyid ");
        sql.append(" and hs.staffID=t.staffID ");
        sql.append(" and t.companyid=?");
        sql.append(" and t.subject=?");
        List<Object> obj=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[]{companyId,subject});
        return obj;
    }

    /**
     * 学员提前预约天数
     * @param companyId
     * @return
     */
    public List<Object> allTime(String companyId){
        String sql="select genday,studentay,studentLeaveTime,studentCanBookingToday from Tb_Elyc_Company_Config where companyid=?";
        List<Object> config=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {companyId});
        return config;
    }

    /**
     * 查询所有教练的Id
     * @param companyId
     * @param subject
     * @return
     */
    public List<Object> teachId(String companyId,String subject){
        String sql="select t.teacherId from TB_JP_TEACHER t where  t.companyId=? and t.subject=?";
        List<Object> teachList=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {companyId,subject});
        return teachList;
    }

    /**
     * 查询学员信息
     * @param StaffID
     * @return
     */
    public TbJpStudentInfo student(String StaffID){

        String hql="from TbJpStudentInfo where staffId=?";
        TbJpStudentInfo student = (TbJpStudentInfo) bbservice.getBeanByHqlAndParams(hql.toString(),new Object[] {StaffID});
        return student;
    }

    /**
     * 查询教练信息
     * @param teacherId
     * @return
     */
    public TbJpTeacher teach(String teacherId){
        String hql="from TbJpTeacher where teacherId=?";
        TbJpTeacher Teacher= (TbJpTeacher) bbservice.getBeanByHqlAndParams(hql.toString(),new Object[] {teacherId});
        return Teacher;
    }

    /**
     * 查询学员学时
     * @param StaffID
     * @return
     */

    public SubjectHour studentTime(String StaffID){
        String hql="from SubjectHour where staffId=?";
        SubjectHour subjectHour= (hy.ea.bo.SubjectHour) bbservice.getBeanByHqlAndParams(hql.toString(),new Object[] {StaffID});
        return subjectHour;
    }

    /**
     * 查已预约时间
     * @param companyId
     * @param orderTime
     * @param staffid
     * @return
     */

    public List<Object> Reservation(String companyId,String orderTime,String staffid ){
        String sql="select NVL(sum(t.hours), 0) from TB_Elyc_Order_Record t,TB_JP_STUDENT_INFO tb where tb.student_id=t.studentId and t.companyId=? and to_char(t.orderTime,'yyyy-mm-dd')=? and tb.staffid=?";
        List<Object> Reservation=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {companyId,orderTime,staffid});
        return Reservation;
    }

    /**
     * 查教练预约时间是否被预约
     * @param companyId
     * @param odtId
     * @param teacherId
     * @return
     */
    public List<Object> ReservationTime(String companyId,String odtId,String teacherId ){
        String sql="select t.status from Tb_Elyc_Order_Detail_Time t where  t.companyId=? and odtId=? and t.teacherId=?";
        List<Object> teachTime=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {companyId,odtId,teacherId});
        return teachTime;
    }

    /**
     * 教练预约管理
     * @param staffid
     * @param companyId
     * @param StartTime
     * @return
     */
    @Override
    public List<Bean> appointmentManagement(String staffid ,String companyId,String StartTime) {
        StringBuffer sql=new StringBuffer();
        sql.append("select t.status,t.lessionStartTime,t.lessionEndTime ,t.teacherId ,t.odtId from Tb_Elyc_Order_Detail_Time t ,TB_JP_TEACHER tb");
        sql.append("  where tb.teacherId=t.teacherId");
        sql.append("  and tb.staffId=?");
        sql.append("  and t.companyId=?");
        sql.append("  and to_char(t.lessionStartTime,'yyyy-mm-dd')=?");
        List<Bean> list=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[] {staffid,companyId,StartTime});

        return list;
    }

    /**
     * 学员信息及预约详情
     * @param staffid
     * @param companyId
     * @param detailId
     * @return
     */

    @Override
    public List<Bean> eservationetails(String staffid,String companyId ,String detailId) {
        StringBuffer sql=new StringBuffer();
        sql.append("select t.studentName,t.studentPhone,t.teacherName,t.lessionStartTime,t.lessionEndTime, t.status ,t.subject ,jc.NAME,tb.teachpermitted,hs.photo,jp.sex from TB_Elyc_Order_Record t,TB_JP_COMPANY jc,TB_JP_TEACHER tb,dt_hr_Staff hs,TB_JP_STUDENT_INFO jp");
        sql.append(" where jc.company_id=t.companyid ");
        sql.append(" and t.teacherid=tb.teacherid ");
        sql.append(" and hs.staffID=jp.staffID ");
        sql.append(" and t.studentId=jp.student_id ");
        sql.append(" and tb.staffid=?");
        sql.append(" and t.companyid=?");
        sql.append("  and t.detailId=?");
        List<Bean> obj=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[]{staffid,companyId,detailId});
        return obj;
    }
    /**
     * 已报名学员信息
     * @param companyId
     * @return
     */
    @Override
    public PageForm registrationTrainee(String companyId, PageForm pageForm, int pageNumber,String staffname, String staffCode){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql=new StringBuffer();
        sql.append("select h.staffId,h.staffCode,h.staffname,s.sex,s.cardnum,s.phone,s.brith,s.trainType,e.companyName ,s.student_id,s.company_id");
        sql.append(" from dtCashierBills c,dtEnroll e,dt_hr_Staff h,TB_JP_STUDENT_INFO s");
        sql.append(" where h.staffId=e.staffId ");
        sql.append(" and e.cashierBillsID=c.cashierBillsID ");
        sql.append(" and e.staffid=s.staffid ");
        sql.append(" and e.companyid=? ");
        sql.append(" and c.fkStatus=? ");
        params.add(companyId);
        params.add("00");
        //List<Bean> obj=bbservice.getListBeanBySqlAndParams(sql.toString(),new Object[]{companyId,"00"});
        if(staffname!=null&&!"".equals(staffname)){
            sql.append(" and h.staffname like ?");
            params.add("%" + staffname + "%");
        }
        if(staffCode!=null&&!"".equals(staffCode)){
            sql.append(" and h.staffCode like ?");
            params.add("%" + staffCode + "%");
        }
        pageForm = bbservice.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) " + sql.toString().substring(sql.toString().indexOf("from")), params.toArray());

        return pageForm;
    }

    /**
     *
     * 预约时间是否冲突
     * @param companyId
     * @param staffId
     * @return
     */
    public String conflictTime(String staffId,String odtId){
        String hql="from TbElycOrderDetailTime t where odtId=?";

        TbElycOrderDetailTime dd =   (TbElycOrderDetailTime)bbservice.getBeanByHqlAndParams(hql,new Object[]{odtId});


        String sql="select count(*) from TB_Elyc_Order_Record t,TB_JP_STUDENT_INFO tb where tb.student_id=t.studentId and tb.staffid=? and ((t.lessionStartTime<=? and t.lessionEndTime>=?) or (t.lessionStartTime<=? and t.lessionEndTime>=?) or (t.lessionStartTime>=? and t.lessionEndTime<=?))";

        int  count =bbservice.getConutByBySqlAndParams(sql.toString(),new Object[] {staffId,dd.getLessionStartTime(),dd.getLessionStartTime(),dd.getLessionEndTime(),dd.getLessionEndTime(),dd.getLessionStartTime(),dd.getLessionEndTime()});
        if(count==0){

           return "0";
        }

       return "1";

    }
}
