package hy.ea.driving.action.elkc;

import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.elyc.ComplaintSchool;
import hy.ea.driving.service.elkc.ComplaintSchoolService;
import hy.ea.driving.service.elkc.impl.ComplaintSchoolServiceImpl;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
@Controller
@Scope("prototype")
public class ComplaintSchoolAction {
    @Resource
    private ComplaintSchoolServiceImpl csl;
    private ComplaintSchool coms;
    private String staffid;
    private String result;
    private List<Object> list;
    private String teacherId;
    private String companyId;
    private String studentId;
    private String content;
    private String clid;
    private String companyreply;
    private String studentName;
    private String clId;

    /**
     * 查询以投诉的教练
     * @return
     */
    public String findComplaint(){
        list=csl.findComplaint(staffid);
        return "detailsComplaints";
    }

    /**
     * 查询教练
     * @return
     */
    public String findTeacher(){
        list=csl.findTeacher(staffid);
        return "complaintSchool";
    }

    /**
     * 添加投诉
     * @return
     */
    public String addComplaint(){
      boolean b= csl.addComplaint(companyId,teacherId, studentId,staffid,content,studentName);
        Map<String, Object> map = new HashMap<String, Object>();
        if (b) {
            map.put("fanhui", "ok");
        } else {
            map.put("fanhui", "shibai");
        }
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    /**
     * 查找被投诉的教练
     * @return
     */
    public  String teacher(){
        list=csl.teacher(staffid,teacherId,clId);
        return "complaint";
    } /**
     * 查找被投诉的教练
     * @return
     */
    public  String teachers(){
        list=csl.teachers(teacherId,clId);
        return "teachers";
    }

    /**
     * 被投诉的教练
     * @return
     */

    public String allteacher(){

        if(companyId==null||companyId.equals("")){
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            CAccount  caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
            companyId =caccount.getCompanyID();
        }


        list=csl.allTeacher(companyId);


        return "allteacher";
    }

    /**
     * 驾校回复
     * @return
     */
    public String replyComplaint(){
        boolean b=csl.replyComplaint(clid,companyreply);
        Map<String, Object> map = new HashMap<String, Object>();
        if (b) {
            map.put("fanhui", "ok");
        } else {
            map.put("fanhui", "shibai");
        }
        JSONObject json = JSONObject.fromObject(map);
        result = json.toString();
        return "success";
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public ComplaintSchoolServiceImpl getCsl() {
        return csl;
    }

    public void setCsl(ComplaintSchoolServiceImpl csl) {
        this.csl = csl;
    }

    public ComplaintSchool getComs() {
        return coms;
    }

    public void setComs(ComplaintSchool coms) {
        this.coms = coms;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClid() {
        return clid;
    }

    public void setClid(String clid) {
        this.clid = clid;
    }

    public String getCompanyreply() {
        return companyreply;
    }

    public void setCompanyreply(String companyreply) {
        this.companyreply = companyreply;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClId() {
        return clId;
    }

    public void setClId(String clId) {
        this.clId = clId;
    }
}
