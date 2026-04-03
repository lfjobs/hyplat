package hy.ea.driving.service;

import hy.ea.bo.DrivingSchool.elyc.TbElycTeacherRScheduling;
import hy.plat.bo.BaseBean;

import java.util.List;

/**
 * mz
 */
public interface ElkcTeacherPbService {
    /**
     *
     * 保存时间模式
     * @param companyID
     * @param staffID
     */
    public void saveTeacherPb(String teachers,String classID, String companyID, String staffID) ;


        /**
         *
         * 获取当前公司的班次启用的班次
         * @param companyID
         * @return
         */
    public List<BaseBean> getSchedulingList(String companyID);


    /**
     *
     * 删除
     * @param trsId
     */
    public void deleteTeacherPbById(String trsId);






}
