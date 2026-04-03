package hy.ea.driving.service.elkc;

import hy.ea.bo.DrivingSchool.elyc.ComplaintSchool;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1 0001.
 */
public interface ComplaintSchoolService {

    /**
     * 查询投诉
     */
    public List<Object> findComplaint(String staffid);
    /**
     * 查询已教授的教练
     */
    public List<Object> findTeacher(String staffid);
    /**
     * 添加被投诉的教练
     */
    public Boolean addComplaint(String companyId,String teacherId,String studentId,String staffId,String content,String studentName);
    /**
     * 查找被投诉的教练
     */
    public List<Object> teacher(String staffid,String teachertid,String clId);
    /**
     * 查询所有被投诉的教练
     */
    public List<Object> allTeacher(String companyid);
    /**
     * 学校回复
     */
    public Boolean replyComplaint(String clid,String complaintreply);

    /**
     * 查询教练投诉
     * @param teachertid
     * @param clId
     * @return
     */
    public List<Object> teachers(String teachertid,String clId);
}
