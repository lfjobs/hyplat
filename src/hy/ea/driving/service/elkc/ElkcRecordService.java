package hy.ea.driving.service.elkc;


import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.plat.bo.PageForm;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface ElkcRecordService {

    /**
     * 获取当前登录对象 *
     *
     * @return
     **/
    CAccount goCaccount();
    /**
     * 保存页面信息 *
     *
     * @return
     */
    String mobileLogin();
    /**
     * 查询预约记录 *
     * @param studentId:学员id
     * @param pageForm:分页参数
     * @return
     */
    PageForm reservationRecord(String studentId, PageForm pageForm);
    /**
     * 查询预约记录 *
     * @param etoId:预约记录id
     * @return
     */
    Object bkDetails(String etoId);
    /**
     * 取消预约记录 *
     * @param etoId:预约记录id
     * @return
     */
    void cancellation(String etoId);
    /**
     * 计时练车 *
     * @return 00:未登录,01:未报名,02:未预约,03:时间未到,04:练车开始
     */
    Map<String, Object> timingToPracticeCar();
    /**
     * 查询学员信息 *
     * @return
     */
    TbJpStudentInfo studentInfo(String staffID);
    /**
     * 启动 *
     * @param dayBefore
     */
    void bootUp(String dayBefore) throws IOException, ParseException;
}
