package hy.ea.driving.action;

import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.DrivingSchool.elyc.TbElycTeacherEvaluate;
import hy.ea.driving.service.StudentService;
import hy.ea.service.UpLoadFileService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/8 0008.
 */

@Controller
@Scope("prototype")
public class StudentAction {


    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private StudentService studentService;
    @Resource
    private UpLoadFileService fileService;
    private TbElycTeacherEvaluate tbly;
    private Logger logger;
    private String results;
    private String teacherId; //教练的id
    private PageForm pageForm;
    private int pageSize;
    private String pingji; //判断为好中差评
    private String photo;
    ; //图片logo
    private Map<String, String> map = new HashMap<String, String>();
    private TbJpTeacher teacher;
    private String studentId;
    private String  staffId;

    /**
     * 学员对教练进行添加评价
     *
     * @return
     */
    public String addStudentAppraise() {
        List<String> listImage = new ArrayList<>();
        Boolean b = studentService.AddAppraise(tbly, photo);
        Map<String, Object> map = new HashMap<String, Object>();
        if (b) {
            map.put("fanhui", "ok");
        } else {
            map.put("fanhui", "shibai");
        }
        JSONObject json = JSONObject.fromObject(map);
        results = json.toString();
        return "success";
    }

    /**
     * 获取全部的教练评价和教练信息
     *
     * @return
     */
    public String getStudentAppraise() {


        teacher = (TbJpTeacher) baseBeanService.getBeanByHqlAndParams(" from TbJpTeacher where teacherId = ?", new Object[]{teacherId});

        map = studentService.getPunLunShu(teacherId);
        return "pjList";
    }


    /**
     * 获取全部评论，好评、中评、差评教练评价
     *
     * @return
     */
    public String getStudentAppraiseBufen() {
        Map<String, Object> map = new HashMap<String, Object>();
        String evaluateType = "";
        if (pingji.contains("好评")) {
            evaluateType = "0";
        } else if (pingji.contains("中评")) {
            evaluateType = "1";
        } else if (pingji.contains("差评")) {
            evaluateType = "2";
        } else if (pingji.contains("全部") || pingji.equals("")) {
            evaluateType = "3";
        }

        pageForm = studentService.getAppraise(teacherId, pageForm, pageSize, evaluateType);

        Map<String,List> plist=studentService.getPicpath(pageForm);
        if(plist !=null &&plist.size() !=0){
            map.put("plist",plist);
        }

        map.put("pageForm",pageForm);
        JSONObject json = JSONObject.fromObject(map);
        results = json.toString();
        return "success";
    }

    /**
     * 进入评价页面入口
     * http://localhost:8080/hyplat/ea/student/ea_addApprise.jspa?studentId=123456789&teacherId=402884d25e092ba6015e03f8cccb00082
     * @return
     */
    public String addApprise(){



        return "addApprise";
    }

    /**
     *
     * 获取当前学员预约过的教练的列表
     * @return
     */
    public String getMyTeacherByRecord(){
        HttpServletRequest request = ServletActionContext.getRequest();
       String sql = "select distinct(t.teacherId),t.name,t.mobile,f.photo from TB_Elyc_Order_Record  r ,TB_JP_TEACHER t,dt_hr_Staff f where t.teacherId = r.teacherId and f.staffId = t.staffId and r.staffId = ?";
       List<BaseBean> tealist = baseBeanService.getListBeanBySqlAndParams(sql,new Object[]{staffId});
       request.setAttribute("tealist",tealist);
       return "tealist";

    }


    public TbElycTeacherEvaluate getTbly() {
        return tbly;
    }

    public void setTbly(TbElycTeacherEvaluate tbly) {
        this.tbly = tbly;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPingji() {
        return pingji;
    }

    public void setPingji(String pingji) {
        this.pingji = pingji;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public TbJpTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(TbJpTeacher teacher) {
        this.teacher = teacher;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }


}





