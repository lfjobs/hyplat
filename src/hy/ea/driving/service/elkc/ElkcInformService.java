package hy.ea.driving.service.elkc;


import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.elyc.ElycDrivingSchoolNotice;
import hy.ea.bo.human.Staff;
import hy.plat.bo.PageForm;

import java.io.File;
import java.util.Map;

public interface ElkcInformService {

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
     * 查询通知消息 *
     * @param staffId :接收人id
     * @param theme :主题
     * @param pageForm
     * @return
     */
    PageForm mission(String staffId, String theme, PageForm pageForm);
    /**
     * 学员消息详情 *
     * @param edsnId :消息id
     * @return
     */
    Map<String,Object> osfDetails(String edsnId);
    /**
     * 查询消息发送 *
     * @param createperson :发送人id
     * @param theme :主题
     * @param pageForm
     * @return
     */
    Map<String,Object> messageSent(String createperson, String theme, PageForm pageForm);
    /**
     * 教练发送消息详情 *
     * @param edsnId :消息id
     * @return
     */
    Map<String,Object> cmsDetails(String edsnId);
    /**
     * ajax查询教练学员 *
     * @param staffName :人员名称
     * @param companyid :公司id
     * @return
     **/
    PageForm coachStudents(String staffName, String companyid, PageForm pageForm);
    /**
     * ajax查询教练学员 *
     * @param edsn :消息发送参数
     * @param file :图片数组
     * @param fileName :图片名称数组
     * @param staffId :接收人数组
     * @return
     **/
    void addInform(ElycDrivingSchoolNotice edsn, File[] file, String[] fileName, String[] staffId);
}
