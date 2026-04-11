package hy.ea.driving.action.elkc;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.ea.bo.DrivingSchool.elyc.TbElycOrderDetailTime;
import hy.ea.bo.DrivingSchool.elyc.TbElycOrderRecord;
import hy.ea.bo.Enroll;
import hy.ea.bo.SubjectHour;
import hy.ea.bo.human.Staff;
import hy.ea.driving.service.elkc.impl.OrderServiceImpl;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Constant;
import hy.ea.util.DateUtil;
import hy.ea.util.bean.Bean;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.httpclient.HttpClient;
/**
 * Created by Administrator on 2017/9/4 0004.
 */
@Controller
@Scope("prototype")
public class CoacheRservationAction {
	private static final Logger logger = LoggerFactory.getLogger(CoacheRservationAction.class);
    @Resource
    private  OrderServiceImpl ordersi;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private CLogBookService logBookService;
    private Logger logger = LoggerFactory.getLogger(CoacheRservationAction.class);
    private HttpServletRequest request;
    private CAccount caccount;
    private TbElycOrderDetailTime tdetailTime;
    private TbElycOrderRecord tbElycOrderRecord;
    private String result;
    private  String companyId;
    private String staffId;
    private List<Object> list;
    private String name;
    private String teacherId;
    private String chestr;
    private TbElycCompanyConfig tbConfig;
    private String genday;
    private Double studentay;
    private String StartTime;
    private String EndTime;
    private TbElycCompanyConfig tbElycCompanyConfig;
    private Date lessionStartTime;
    private TbJpStudentInfo student;
    private TbJpTeacher teacher;
    private SubjectHour subjectHour;
    private   TbElycOrderDetailTime ordertime;
    private String odtId;
    private  String status;
    private  String orderTime;
    private double hours;
    private Date  appointment;
    private String TeacherName;
    private PageForm pageForm;
    private int pageNumber;
    private String staffname;
    private String staffCode;
    private InputStream excelStream;
    private Integer hasTime;
    private  TEshopCusCom cuscom ;
    @Resource
    private ShowExcelService excelService;
    /**
     * 查询该驾校是否正常
     *
     * @return
     */
    public String order(){
        Integer i=ordersi.getOnHoliday(companyId);
        JSONObject json=new JSONObject();
        json.accumulate("i", i);
        result=json.toString();
        return "success";
    }

    /**
     * 查询该驾校里所有的教练
     *
     * @return
     */

    public String CoacheRservation(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        if(caccount!=null&&!caccount.equals("")){
            staffId=caccount.getStaffID();
            companyId=caccount.getCompanyID();
        }
        if (staffId!=null&&!staffId.equals("")){
            SubjectHour subjectHour=ordersi.studentTime(staffId);
//            if(subjectHour.getP1Time()!=subjectHour.getP1NetworkTime()){
//                list=ordersi.teachers(companyId,"1");
//            } else
                if(subjectHour.getP2Time()!=subjectHour.getP2NetworkTime()){
                list=ordersi.teachers(companyId,"2");
            }else if(subjectHour.getP3Time()!=subjectHour.getP3NetworkTime()){
                list=ordersi.teachers(companyId,"3");
            }
//            else if(subjectHour.getP4Time()!=subjectHour.getP4NetworkTime()){
//                list=ordersi.teachers(companyId,"4");
//            }
        }

        return "bookingByCoach";
    }
    public String CoacheRservations(){
        if (staffId!=null&&!staffId.equals("")){
            SubjectHour subjectHour=ordersi.studentTime(staffId);
//            if(subjectHour.getP1Time()!=subjectHour.getP1NetworkTime()){
//                list=ordersi.teachers(companyId,"1");
//            } else
                if(subjectHour.getP2Time()!=subjectHour.getP2NetworkTime()){
                list=ordersi.teachers(companyId,"2");
            }else if(subjectHour.getP3Time()!=subjectHour.getP3NetworkTime()){
                list=ordersi.teachers(companyId,"3");
            }
//            else if(subjectHour.getP4Time()!=subjectHour.getP4NetworkTime()){
//                list=ordersi.teachers(companyId,"4");
//            }
        }

        return "bookingByCoach";
    }

    /**
     * 报名成功后入口
     * @return
     */
    public String registration(){
        HttpServletRequest request = ServletActionContext.getRequest();
        if (staffId!=null&&!staffId.equals("")&&!staffId.equals("null")){
            SubjectHour subjectHour=ordersi.studentTime(staffId);
//            if(subjectHour.getP1Time()!=subjectHour.getP1NetworkTime()){
//                list=ordersi.teachers(companyId,"1");
//            } else
                if(subjectHour.getP2Time()!=subjectHour.getP2NetworkTime()){
                list=ordersi.teachers(companyId,"2");
            }else if(subjectHour.getP3Time()!=subjectHour.getP3NetworkTime()){
                list=ordersi.teachers(companyId,"3");
            }
//            else if(subjectHour.getP4Time()!=subjectHour.getP4NetworkTime()){
//                list=ordersi.teachers(companyId,"4");
//            }
        }
        request.setAttribute("list",list);

        return "bookingByCoach";
    }
    /**
     * 模糊查询该驾校里的教练
     *
     * @return
     */
    public String chaTeacher(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        cuscom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        if(caccount!=null&&!caccount.equals("")){
            staffId=caccount.getStaffID();
            companyId=caccount.getCompanyID();
        }
        if (staffId!=null&&!staffId.equals("")) {
            JSONObject json = new JSONObject();
            if (TeacherName != null && !TeacherName.equals("")) {
                SubjectHour subjectHour=ordersi.studentTime(staffId);
//                if(subjectHour.getP1Time()!=subjectHour.getP1NetworkTime()) {
//                    list = ordersi.teacher(TeacherName, companyId, "1");
//                }
//                else
                    if(subjectHour.getP2Time()!=subjectHour.getP2NetworkTime()) {
                    list = ordersi.teacher(TeacherName, companyId, "2");
                }
                else if(subjectHour.getP3Time()!=subjectHour.getP3NetworkTime()) {
                    list = ordersi.teacher(TeacherName, companyId, "3");
                }
//                else if(subjectHour.getP4Time()!=subjectHour.getP4NetworkTime()) {
//                    list = ordersi.teacher(TeacherName, companyId, "4");
//                }
                json.accumulate("list", list);
                result = json.toString();
            }
        }
        return "success";
    }
    /**
     * 教练信息和可预约时间
     * @return
     */
    public String coachingTime(){
        HttpSession session =ServletActionContext.getRequest().getSession();
        HttpServletRequest request=ServletActionContext.getRequest();
        Object coaching=ordersi.coachingInformation(teacherId,companyId);
        request.setAttribute("coaching", coaching);
        if(coaching!=null) {
            List<Object> listTime = new ArrayList<Object>();
            List<Object> list = ordersi.allTime(companyId);
            //List<Bean> listof =null;
            Map<Date,List<Bean>> map=new Hashtable<Date,List<Bean> >();
            if (!list.isEmpty()) {
                Object[] obj = (Object[]) list.get(0);
                genday = obj[0].toString();
                int j = Integer.parseInt(genday);
                for (int i = 0; i < j; ++i) {
                    Date d = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DATE, i);
                    d = cal.getTime();
                    System.out.print(d);
                    listTime.add(d);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date =sdf.format(d);
                    List<Bean>  listof = ordersi.timeOfAppointment(teacherId, companyId, date);
                    Collections.reverse(listof);//倒序list
                    map.put(d,listof );
                    request.setAttribute("listTime", listTime);
                }
                request.setAttribute("map", map);

            }
            request.setAttribute("list", list);
        }
        return "coachingTime";
    }

    /**
     * 设置预约时间段
     */
    public String saveTime(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        if(caccount!=null&&!caccount.equals("")){
            staffId=caccount.getStaffID();
        }
        if (staffId!=null&&!staffId.equals("")){
            student=ordersi.student(staffId);
        }
        String [] stringTeacher= teacherId.split(",");
        if(stringTeacher!=null){
            for (int i = 0; i < stringTeacher.length; i++) {
                teacher = ordersi.teach(stringTeacher[i].trim());
            }
        }
        List<BaseBean> list=new ArrayList<BaseBean>();
        TbElycOrderRecord detailTime=null;
        TbElycOrderDetailTime ordertime=null;
        String [] stringStart= StartTime.split(",");
        String [] stringEnd= EndTime.split(",");
        String [] stringArr= chestr.split(",");
        if(stringArr!=null) {
            for (int i = 0; i < stringArr.length; i++) {
                detailTime=new TbElycOrderRecord();
                    if(detailTime.getEtoId()==null||detailTime.getEtoId().equals("")){
                        detailTime.setEtoId(serverService.getServerID("tb"));
                    }
                    String star=stringStart[i].trim();
                     String end= stringEnd[i].trim();
                    String dateId=stringArr[i].trim();
                SimpleDateFormat  sdf= new  SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                Date  dateStart=null;
                Date  dateEnd=null;
                double t=0.0;
                try {
                    dateStart= sdf.parse(star);
                    dateEnd=sdf.parse(end);
                    long h = dateEnd.getTime() - dateStart.getTime();
                    long day = h/ (24 * 60 * 60 * 1000);
                    long hour = (h / (60 * 60 * 1000) - day * 24);
                    long min = ((h / (60 * 1000)) - day * 24 * 60 - hour * 60);
                    //long s = (h / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                    t=(double)hour+(min/60);
                } catch (ParseException e) {
                    logger.error("操作异常", e);
                }
                    detailTime.setStudentId(student.getStudentId());
                    detailTime.setStaffId(student.getStaffId());
                    detailTime.setStudentName(student.getName());
                    detailTime.setStudentPhone(student.getPhone());
                    detailTime.setStudentNum(student.getCardNum());
                    detailTime.setTeacherId(teacher.getTeacherId());
                    detailTime.setTeacherName(teacher.getName());
                    detailTime.setTeacherNum(teacher.getIdcard());
                    detailTime.setTeacherPhone(teacher.getMobile());
                    detailTime.setStatus("1");
                    detailTime.setDetailId(dateId);
                    detailTime.setLessionStartTime(dateStart);
                    detailTime.setLessionEndTime(dateEnd);
                    detailTime.setHours(t);
                    detailTime.setSubject(teacher.getSubject());
                    detailTime.setCompanyId(companyId);
                    detailTime.setSource("0");
                    detailTime.setOrderTime(new Date());
                    list.add(detailTime);
                ordertime=new TbElycOrderDetailTime();
                String hql1 = "from TbElycOrderDetailTime where odtId=?";
                ordertime = (TbElycOrderDetailTime) baseBeanService.getBeanByHqlAndParams(hql1, new Object[]{dateId});
                if(ordertime!=null){
                    ordertime.setStatus("1");
                    list.add(ordertime);
                }
            }

        }
        baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
        String  i=detailTime.getEtoId();
        JSONObject json=new JSONObject();
        json.accumulate("i", i);
        result=json.toString();
        return Action.SUCCESS;
    }
    /**
     * 初始化按时间预约时间段
     */
    public String accordingTime(){
        HttpServletRequest request=ServletActionContext.getRequest();
        List<Object> teachsList = new ArrayList<Object>();
        List<Object> teachers=new ArrayList<Object>();
        if (staffId!=null&&!staffId.equals("")){
            SubjectHour subjectHour=ordersi.studentTime(staffId);
//            if(subjectHour.getP1Time()!=subjectHour.getP1NetworkTime()){
//                teachsList=ordersi.teachId(companyId,"1");
//                teachers=ordersi.allCoachingInformation(companyId,"1");
//            } else
                if(subjectHour.getP2Time()!=subjectHour.getP2NetworkTime()){
                teachsList=ordersi.teachId(companyId,"2");
                teachers=ordersi.allCoachingInformation(companyId,"2");
            }else if(subjectHour.getP3Time()!=subjectHour.getP3NetworkTime()){
                teachsList=ordersi.teachId(companyId,"3");
                teachers=ordersi.allCoachingInformation(companyId,"3");
            }
//            else if(subjectHour.getP4Time()!=subjectHour.getP4NetworkTime()){
//                teachsList=ordersi.teachId(companyId,"4");
//                teachers=ordersi.allCoachingInformation(companyId,"4");
//            }
        }
        List<Object> listTime = new ArrayList<Object>();
        List<Object> list=ordersi.allTime(companyId);
        request.setAttribute("list", list);
        if(!list.isEmpty()){
            Object [] obj=(Object[]) list.get(0);
            genday=obj[0].toString();
            int j=Integer.parseInt(genday);
            studentay=Double.parseDouble( obj[1].toString());
            for (int i =0;i<j ;i++){
                Date d = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DATE,i);
                d = cal.getTime();
                listTime.add(d);
            }
            request.setAttribute("listTime",listTime);
        }
        Map<String ,List<Bean>> map=new Hashtable<String,List<Bean> >();

        if(!listTime.isEmpty()){
            Date[] Start = new Date[listTime.size()];
            Date[] i=listTime.toArray(Start);
            Date StartTime=i[0];
            SimpleDateFormat sdf= new  SimpleDateFormat("yyyy-MM-dd");
            String date= sdf.format(StartTime);
            if(!teachsList.isEmpty()){
                for(int t=0;t<teachsList.size();t++){
                    teacherId= teachsList.get(t).toString();
                    List<Bean> listTeach=ordersi.timeOfAppointment(teacherId,companyId,date);
                    Collections.reverse(listTeach);
                    map.put(teacherId,listTeach);
                }
                request.setAttribute("map",map);

            }
            request.setAttribute("teachers",teachers);

        }
        return "bookTime";
    }

    /**
     * 初始化按时间预约时间段
     */
    public String allTime(){
        //HttpSession session =ServletActionContext.getRequest().getSession();
        HttpServletRequest request=ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        cuscom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        if(caccount!=null&&!caccount.equals("")){
            staffId=caccount.getStaffID();
            companyId=caccount.getCompanyID();
        }
        List<Object> teachsList = new ArrayList<Object>();
        List<Object> teachers=new ArrayList<Object>();
        if (staffId!=null&&!staffId.equals("")){
            SubjectHour subjectHour=ordersi.studentTime(staffId);
//            if(subjectHour.getP1Time()!=subjectHour.getP1NetworkTime()){
//                teachsList=ordersi.teachId(companyId,"1");
//                teachers=ordersi.allCoachingInformation(companyId,"1");
//            } else
                if(subjectHour.getP2Time()!=subjectHour.getP2NetworkTime()){
                teachsList=ordersi.teachId(companyId,"2");
                teachers=ordersi.allCoachingInformation(companyId,"2");
            }else if(subjectHour.getP3Time()!=subjectHour.getP3NetworkTime()){
                teachsList=ordersi.teachId(companyId,"3");
                teachers=ordersi.allCoachingInformation(companyId,"3");
            }
//            else if(subjectHour.getP4Time()!=subjectHour.getP4NetworkTime()){
//                teachsList=ordersi.teachId(companyId,"4");
//                teachers=ordersi.allCoachingInformation(companyId,"4");
//            }
        }
        List<Object> listTime = new ArrayList<Object>();
        List<Object> list=ordersi.allTime(companyId);
        request.setAttribute("list", list);
        if(!list.isEmpty()){
            Object [] obj=(Object[]) list.get(0);
            genday=obj[0].toString();
            int j=Integer.parseInt(genday);
            studentay=Double.parseDouble( obj[1].toString());
            for (int i =0;i<j ;i++){
            Date d = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE,i);
            d = cal.getTime();
            listTime.add(d);
            }
            request.setAttribute("listTime",listTime);
        }
        Map<String ,List<Bean>> map=new Hashtable<String,List<Bean> >();

       if(!listTime.isEmpty()){
           Date[] Start = new Date[listTime.size()];
           Date[] i=listTime.toArray(Start);
           Date StartTime=i[0];
           SimpleDateFormat sdf= new  SimpleDateFormat("yyyy-MM-dd");
           String date= sdf.format(StartTime);
           if(!teachsList.isEmpty()){
                   for(int t=0;t<teachsList.size();t++){
                       teacherId= teachsList.get(t).toString();
                       List<Bean> listTeach=ordersi.timeOfAppointment(teacherId,companyId,date);
                       Collections.reverse(listTeach);
                       map.put(teacherId,listTeach);
                   }
                   request.setAttribute("map",map);

           }
           request.setAttribute("teachers",teachers);

        }
        return "bookTime";
    }

    /***
     * 按时间查询教练可预约时间
     * @return
     */

    public  String coachByTime(){
        HttpServletRequest request=ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        if(caccount!=null&&!caccount.equals("")){
            staffId=caccount.getStaffID();
        }
        List<Object> teachsList = new ArrayList<Object>();
        List<Object> teachers = new ArrayList<Object>();
        if (staffId!=null&&!staffId.equals("")){
            SubjectHour subjectHour=ordersi.studentTime(staffId);
//            if(subjectHour.getP1Time()!=subjectHour.getP1NetworkTime()){
//                teachsList=ordersi.teachId(companyId,"1");
//                teachers=ordersi.allCoachingInformation(companyId,"1");
//            } else
                if(subjectHour.getP2Time()!=subjectHour.getP2NetworkTime()){
                teachsList=ordersi.teachId(companyId,"2");
                teachers=ordersi.allCoachingInformation(companyId,"2");
            }else if(subjectHour.getP3Time()!=subjectHour.getP3NetworkTime()){
                teachsList=ordersi.teachId(companyId,"3");
                teachers=ordersi.allCoachingInformation(companyId,"3");
            }
//            else if(subjectHour.getP4Time()!=subjectHour.getP4NetworkTime()){
//                teachsList=ordersi.teachId(companyId,"4");
//                teachers=ordersi.allCoachingInformation(companyId,"4");
//            }
        }
        List<Object> listTime = new ArrayList<Object>();
        JSONObject json = new JSONObject();
        Map<String ,List<Bean>> map=new Hashtable<String,List<Bean> >();
        List<Object> list=ordersi.allTime(companyId);
        request.setAttribute("list", list);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(lessionStartTime);
            if (!teachsList.isEmpty()) {
                for (int t = 0; t < teachsList.size(); t++) {
                    teacherId = teachsList.get(t).toString();
                    List<Bean> listTeach = ordersi.timeOfAppointment1(teacherId, companyId, date);
                    Collections.reverse(listTeach);
                    map.put(teacherId, listTeach);
                }
                json.accumulate("map", map);
            }
            json.accumulate("teachers", teachers);
            result = json.toString();

        return "success";
    }
    /**
     * 按时间查询教练可预约时间
     * @return
     */
    public String Reservation(){
        HttpServletRequest request=ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        if(caccount!=null&&!caccount.equals("")){
            staffId=caccount.getStaffID();
        }
        JSONObject json = new JSONObject();
        if(staffId!=null&&!staffId.equals("")){
            Date date=new Date();
            SimpleDateFormat foramt=new SimpleDateFormat("yyyy-MM-dd");
            orderTime=foramt.format(date);
            List<Object> reservation=ordersi.Reservation(companyId,orderTime,staffId);
            if (!reservation.isEmpty()){
                BigDecimal obj= (BigDecimal) reservation.get(0);
                hours=Double.parseDouble(obj.toString());
                json.accumulate("hours",hours);
            }
        }
        List teachTime=ordersi.ReservationTime(companyId,odtId, teacherId);
        if(!teachTime.isEmpty()){
           String  obj=(String) teachTime.get(0);
            status=obj;
            json.accumulate("status",status);

        }


        String  conflict =ordersi.conflictTime(staffId,odtId);
        json.accumulate("conflict",conflict);
        result =json.toString();
        return "success";
    }

    /**
     * 教练预约管理
     * @return
     */
    public String courseManagement(){
        HttpSession session =ServletActionContext.getRequest().getSession();
        HttpServletRequest request=ServletActionContext.getRequest();
            List<Object> listTime = new ArrayList<Object>();
            List<Object> list = ordersi.allTime(companyId);
            //List<Bean> listof =null;
            Map<Date,List<Bean>> map=new Hashtable<Date,List<Bean> >();
            if (!list.isEmpty()) {
                Object[] obj = (Object[]) list.get(0);
                genday = obj[0].toString();
                int j = Integer.parseInt(genday);
                for (int i = 0; i < j; ++i) {
                    Date d = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    cal.add(Calendar.DATE, i);
                    d = cal.getTime();
                    System.out.print(d);
                    listTime.add(d);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String date =sdf.format(d);
                    List<Bean>  listof = ordersi.appointmentManagement(staffId, companyId, date);
                    Collections.reverse(listof);//倒序list
                    map.put(d,listof );
                    request.setAttribute("listTime", listTime);
                }
                request.setAttribute("map", map);

            }
            request.setAttribute("list", list);
        return "courseManagement";
    }

    /**
     * 学员信息及预约详情
     * @return
     */

    public  String eservationetails(){
        HttpSession session =ServletActionContext.getRequest().getSession();
        HttpServletRequest request=ServletActionContext.getRequest();
        List<Bean>  eservation =ordersi.eservationetails(staffId,companyId,odtId);
        request.setAttribute("listof", eservation);
        return "courseManagementDetails";
    }
    /**
     * pc端学员管理
     * @return
     */
    public  String pcStudentManager(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account==null){
            return "login";
        }

        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("from TbElycOrderRecord d where d.companyId = ? ");
        list.add(account.getCompanyID());
        if (tbElycOrderRecord!=null){
            if(tbElycOrderRecord.getStudentName()!=null && !tbElycOrderRecord.getStudentName().equals(""))
            {
                sb.append("and d.studentName like ? ");
                list.add("%"+tbElycOrderRecord.getStudentName()+"%");
            }
            if(tbElycOrderRecord.getTeacherName()!=null && !tbElycOrderRecord.getTeacherName().equals(""))
            {
                sb.append("and d.teacherName like ? ");
                list.add("%"+tbElycOrderRecord.getTeacherName()+"%");
            }
            if(StartTime!=null && !StartTime.equals(""))
            {
                sb.append("and d.lessionStartTime>= to_date(?,'yyyy-mm-dd') ");
                list.add(StartTime);
            }
            if(EndTime!=null && !EndTime.equals(""))
            {
                sb.append("and d.lessionEndTime<= to_date(?,'yyyy-mm-dd') ");
                list.add(EndTime);
            }
            if(tbElycOrderRecord.getStatus()!=null && !tbElycOrderRecord.getStatus().equals(""))
            {
                sb.append("and d.status = ? ");
                list.add(tbElycOrderRecord.getStatus());
            }
        }
        sb.append("order by d.orderTime desc ");


        pageForm = baseBeanService.getPageForm(null == pageForm ? 1 : pageForm.getPageNumber(),
                pageNumber==0 ? 10 : pageNumber, sb.toString(),list.toArray());

        return "pcStudentManager";
    }

    /**
     * 已报名学员信息
     * @return
     */
    public  String registrationTrainee(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account==null){
            return "login";
        }
         pageForm=ordersi.registrationTrainee(account.getCompanyID(),pageForm, pageNumber,staffname,staffCode);
        return "registrationTrainee";
    }
    public String excl(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account==null){
            return "login";
        }
        List<Object> list = new ArrayList<Object>();
        StringBuffer sql=new StringBuffer();
        sql.append("select h.staffCode,h.staffname,s.sex,s.cardnum,s.phone,s.brith,s.trainType,e.companyName ");
        sql.append(" from dtCashierBills c,dtEnroll e,dt_hr_Staff h,TB_JP_STUDENT_INFO s");
        sql.append(" where h.staffId=e.staffId ");
        sql.append(" and e.cashierBillsID=c.cashierBillsID ");
        sql.append(" and e.staffid=s.staffid ");
        sql.append(" and e.companyid=? ");
        sql.append(" and c.fkStatus=? ");
        list.add(account.getCompanyID());
        list.add("00");
        if(staffname!=null&&"".equals(staffname)){
            sql.append(" and h.staffname like ?");
            list.add("%" + staffname + "%");
        }
        if(staffCode!=null&&"".equals(staffCode)){
            sql.append(" and h.staffCode like ?");
            list.add("%" + staffCode + "%");
        }
        int size=list.size();
        Object [] params=new Object[size];
        for (int i = 0; i < size; i++) {
            params[i]=list.get(i);
        }
        List<BaseBean> obj = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params);
        excelStream = excelService.showExcel(TbJpStudentInfo.columnHeadings(),
                obj);
        return "showexcel";
    }

    /**
     * 查询学员学时
     * @return
     */
    public String hour(){
        HttpServletRequest request=ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        cuscom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
        if(caccount!=null&&!caccount.equals("")){
            staffId=caccount.getStaffID();
        }else {
            staffId=cuscom.getStaffid();
        }
        SubjectHour subjectHour=ordersi.studentTime(staffId);
        JSONObject json = new JSONObject();
        if(subjectHour!=null&&!"".equals(subjectHour)){
            hasTime=subjectHour.getHasTime();
            json.accumulate("hasTime",hasTime);
            result=json.toString();
        }
        return "success";
    }
    /**
     * 查询学员学时
     * @return
     */
    public String hourAPP(){
        SubjectHour subjectHour=ordersi.studentTime(staffId);
        JSONObject json = new JSONObject();

        if(subjectHour!=null&&!"".equals(subjectHour)){
            hasTime=subjectHour.getHasTime();
        }


        json.accumulate("hasTime",hasTime);
        result=json.toString();
        return "success";
    }
    private String getVal(Object o)
    {
        if (o==null)
        {
            return "";
        }else
        {
            return o.toString();
        }
    }
    /**
     * 同步学员信息
     * @return
     */
    public String SyncStudent(){
        String hql = "from TbJpStudentInfo where staffId=?";
        TbJpStudentInfo cc= (TbJpStudentInfo) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{staffId});
        String hql2 = "from Staff where staffId=?";
        Staff staff= (Staff) baseBeanService.getBeanByHqlAndParams(hql2,new Object[]{staffId});
        JSONObject json=new JSONObject();
        if(cc.getPkTbJpStudentId()==null){
        if(Sync(cc,staff)){
            hql="update TbJpStudentInfo set pkTbJpStudentId=? where staffId=?";
            baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{cc.getPkTbJpStudentId(),staffId});
            json.accumulate("a",1);
          }
        }else {
            json.accumulate("c", cc.getPkTbJpStudentId());
        }
        result=json.toString();
        return "success";
    }
    private boolean Sync(TbJpStudentInfo parmEnt,Staff staff)
    {
        try {
            HttpClient httpClient = new HttpClient();
            httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            String url = Constant.TIMING_DOMAIN+"/jpstudent/syncStudentInfo";
            PostMethod postMethod = new PostMethod(url);
            NameValuePair[] data = {
                    new NameValuePair("studentId", getVal(parmEnt.getStudentId())),
                    new NameValuePair("companyId", getVal(parmEnt.getCompanyId())),
                    new NameValuePair("staffCode", getVal(staff.getStaffCode())),
                    new NameValuePair("staffname", getVal(staff.getStaffName())),
                    new NameValuePair("sex", getVal(parmEnt.getSex())),
                    new NameValuePair("cardnum", getVal(parmEnt.getCardNum())),
                    new NameValuePair("phone", getVal(parmEnt.getPhone())),
                    new NameValuePair("brith", getVal(DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss",parmEnt.getBrith()))),
                    new NameValuePair("trainType", getVal(parmEnt.getTrainType())),
                    new NameValuePair("updatedate", parmEnt.getUpdateDate()==null?"":DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss",parmEnt.getUpdateDate()))
            };
            postMethod.setRequestBody(data);
            httpClient.executeMethod(postMethod);
            // 读取内容
            InputStream resInputStream = null;
            resInputStream = postMethod.getResponseBodyAsStream();
            InputStreamReader inStream = new InputStreamReader(resInputStream, "UTF-8");
            // 处理内容
            /*BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));*/
            BufferedReader reader = new BufferedReader(inStream);
            String tempBf = null;
            StringBuffer html = new StringBuffer();
            while ((tempBf = reader.readLine()) != null) {

                html.append(tempBf);
            }
            String resMes = html.toString();
            int dex = resMes.lastIndexOf(",");

            // 拆分页面应答数据
            String str[] = resMes.split(",");


            // 提取返回数据
            if (str.length == 10) {
                String studentId=str[0].substring(str[0].indexOf("=")+2,str[0].toString().length()-1);
                String companyId=str[1].substring(str[1].indexOf("=")+2,str[1].toString().length()-1);
                String staffCode=str[2].substring(str[2].indexOf("=")+2,str[2].toString().length()-1);
                String staffname=str[3].substring(str[3].indexOf("=")+2,str[3].toString().length()-1);
                String sex=str[4].substring(str[4].indexOf("=")+2,str[4].toString().length()-1);
                String cardnum=str[5].substring(str[5].indexOf("=")+2,str[5].toString().length()-1);
                String phone=str[6].substring(str[6].indexOf("=")+2,str[6].toString().length()-1);
                String brith=str[7].substring(str[7].indexOf("=")+2,str[7].toString().length()-1);
                String trainType=str[8].substring(str[8].indexOf("=")+2,str[8].toString().length()-1);
                String updatedate=str[9].substring(str[9].indexOf("=")+2,str[9].toString().length()-2);

                parmEnt.setStudentId(studentId);
                parmEnt.setCompanyId(companyId);
                staff.setStaffCode(staffCode);
                staff.setStaffName(staffname);
                parmEnt.setSex(sex);
                parmEnt.setCardNum(cardnum);
                parmEnt.setPhone(phone);
                parmEnt.setBrith(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",brith));
                parmEnt.setTrainType(trainType);
                parmEnt.setUpdateDate(DateUtil.parseDate("yyyy-MM-dd HH:mm:ss",updatedate));

                parmEnt.setPkTbJpStudentId(studentId);
                return true;
            }
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
        return false;
    }
    public String appointment(){

        return "appointment";
    }
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public CAccount getCaccount() {
        return caccount;
    }

    public void setCaccount(CAccount caccount) {
        this.caccount = caccount;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public OrderServiceImpl getOrdersi() {
        return ordersi;
    }

    public void setOrdersi(OrderServiceImpl ordersi) {
        this.ordersi = ordersi;
    }

    public String getChestr() {
        return chestr;
    }

    public void setChestr(String chestr) {
        this.chestr = chestr;
    }

    public TbElycOrderDetailTime getTdetailTime() {
        return tdetailTime;
    }

    public void setTdetailTime(TbElycOrderDetailTime tdetailTime) {
        this.tdetailTime = tdetailTime;
    }

    public TbElycCompanyConfig getTbConfig() {
        return tbConfig;
    }

    public void setTbConfig(TbElycCompanyConfig tbConfig) {
        this.tbConfig = tbConfig;
    }

    public String getGenday() {
        return genday;
    }

    public void setGenday(String genday) {
        this.genday = genday;
    }

    public Double getStudentay() {
        return studentay;
    }

    public void setStudentay(Double studentay) {
        this.studentay = studentay;
    }

    public TbElycCompanyConfig getTbElycCompanyConfig() {
        return tbElycCompanyConfig;
    }

    public void setTbElycCompanyConfig(TbElycCompanyConfig tbElycCompanyConfig) {
        this.tbElycCompanyConfig = tbElycCompanyConfig;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public Date getLessionStartTime() {
        return lessionStartTime;
    }

    public void setLessionStartTime(Date lessionStartTime) {
        this.lessionStartTime = lessionStartTime;
    }

    public TbJpStudentInfo getStudent() {
        return student;
    }

    public void setStudent(TbJpStudentInfo student) {
        this.student = student;
    }

    public TbJpTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(TbJpTeacher teacher) {
        this.teacher = teacher;
    }

    public SubjectHour getSubjectHour() {
        return subjectHour;
    }

    public void setSubjectHour(SubjectHour subjectHour) {
        this.subjectHour = subjectHour;
    }

    public TbElycOrderDetailTime getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(TbElycOrderDetailTime ordertime) {
        this.ordertime = ordertime;
    }

    public String getOdtId() {
        return odtId;
    }

    public void setOdtId(String odtId) {
        this.odtId = odtId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public Date getAppointment() {
        return appointment;
    }

    public void setAppointment(Date appointment) {
        this.appointment = appointment;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public Integer getHasTime() {
        return hasTime;
    }

    public void setHasTime(Integer hasTime) {
        this.hasTime = hasTime;
    }

    public TEshopCusCom getCuscom() {
        return cuscom;
    }

    public void setCuscom(TEshopCusCom cuscom) {
        this.cuscom = cuscom;
    }

    public TbElycOrderRecord getTbElycOrderRecord() {
        return tbElycOrderRecord;
    }

    public void setTbElycOrderRecord(TbElycOrderRecord tbElycOrderRecord) {
        this.tbElycOrderRecord = tbElycOrderRecord;
    }
}
