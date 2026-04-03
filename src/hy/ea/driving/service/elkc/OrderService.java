package hy.ea.driving.service.elkc;

import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.ea.bo.SubjectHour;
import hy.ea.util.bean.Bean;
import hy.plat.bo.PageForm;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4 0004.
 */
public interface OrderService {
    /**
     * 查询该驾校是否正常
     * @param companyId
     * @return
     */
    Integer getOnHoliday(String companyId);

    /**
     * 查询该驾校里所有的教练
     * @param companyId
     * @param subject
     * @return
     */
    public List<Object> teachers(String companyId,String subject);

    /**
     * 模糊查询该驾校里的教练
     * @param name
     * @param companyId
     * @return
     */

    public List<Object> teacher(String name,String companyId,String subject);

    /**
     *  教练信息
     * @param teacherId
     * @param companyId
     * @return
     */

    public Object coachingInformation(String teacherId,String companyId);

    /**
     * 按教练查可预约时间
     * @param teacherId
     * @param companyId
     * @param StartTime
     * @return
     */

    public List<Bean> timeOfAppointment(String teacherId ,String companyId,String StartTime);

    /**
     * 按时间查可预约时间
     * @param companyId
     * @param StartTime
     * @param teacherId
     * @return
     */

    public List<Bean> ppointment(String companyId,String  StartTime,String teacherId);

    /**
     * 所有的教练信息
     * @param companyId
     * @param subject
     * @return
     */

    public List<Object> allCoachingInformation(String companyId,String subject);

    /**
     * 学员提前预约天数
     * @param companyId
     * @return
     */

    public List<Object> allTime(String companyId);

    /**
     * 查询所有教练的Id
     * @param companyId
     * @param subject
     * @return
     */

    public List<Object> teachId(String companyId,String subject);

    /**
     * 查询学员信息
     * @param StaffID
     * @return
     */

    public TbJpStudentInfo student(String StaffID);

    /**
     * 查询教练信息
     * @param teacherId
     * @return
     */
    public TbJpTeacher teach(String teacherId);

    /**
     * 查询学员学时
     * @param StaffID
     * @return
     */

    public SubjectHour studentTime(String StaffID);

    /**
     * 按教练查可预约时间
     * @param teacherId
     * @param companyId
     * @param StartTime
     * @return
     */
    public List<Bean> timeOfAppointment1(String teacherId ,String companyId,String StartTime);

    /**
     * 查已预约时间
     * @param companyId
     * @param orderTime
     * @param staffid
     * @return
     */

    public List<Object> Reservation(String companyId,String orderTime,String staffid);

    /**
     * 查教练预约时间是否被预约
     * @param companyId
     * @param odtId
     * @param teacherId
     * @return
     */
    public List<Object> ReservationTime(String companyId,String odtId,String teacherId );

    /**
     * 教练预约管理
     *
     *
     * @param staffid
     * @param companyId
     * @param StartTime
     * @return
     */
    public List<Bean> appointmentManagement(String staffid ,String companyId,String StartTime);

    /**
     *学员信息及预约详情
     * @param staffid
     * @param companyId
     * @return
     */
    public List<Bean> eservationetails(String staffid,String companyId ,String  detailId);

    /**
     * 已报名学员信息
     * @param companyId
     * @return
     */
    public PageForm registrationTrainee(String companyId,  PageForm pageForm, int pageNumber, String staffname, String staffCode);


    public String conflictTime(String staffId,String odtId);

}
