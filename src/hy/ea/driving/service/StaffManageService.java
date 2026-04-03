package hy.ea.driving.service;

import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.human.Staff;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface StaffManageService {

    public void saveStudentInfo(TbJpStudentInfo studentInfo, Staff staff) throws Exception;

    public void saveStudentApplyInfo(TbJpStudentInfo studentInfo) throws Exception;

    public void saveCoachInfo(TbJpTeacher coach,Staff staff) throws Exception;
}
