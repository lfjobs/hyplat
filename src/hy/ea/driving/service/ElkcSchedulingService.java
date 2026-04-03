package hy.ea.driving.service;

import hy.ea.bo.DrivingSchool.elyc.TbElycReservationMode;
import hy.ea.bo.DrivingSchool.elyc.TbElycScheduling;
import hy.ea.bo.DrivingSchool.elyc.TbElycSchedulingDetail;

import java.util.List;

/**
 * mz
 */
public interface ElkcSchedulingService {
    /**
     *
     * 保存班次
     * @param companyId
     * @param staffID
     */
    public void saveScheduling(TbElycScheduling scheduling, TbElycSchedulingDetail schedulingDetail, String companyId, String staffID);

    public void deleteSchedul(String classId);

    /**
     *
     * 获取公司的启用的时间模式
     * @param companyId
     * @return
     */
    public List<TbElycReservationMode> getModeList(String companyId);






}
