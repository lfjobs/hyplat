package hy.ea.driving.service;

import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.ea.bo.DrivingSchool.elyc.TbElycReservationDetail;
import hy.ea.bo.DrivingSchool.elyc.TbElycReservationMode;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * mz
 */
public interface ElkcReservModeService {


    public String updateReservationMode(TbElycReservationMode mode, List<TbElycReservationDetail> detailList, String staffID, String companyId) throws Exception;

    public TbElycReservationMode getReservModeById(String modeId);

    public List<TbElycReservationDetail> getDetailObjectsByModeId(String modeId);

    /**
     *
     * 删除
     * @param modeId
     */
    public void deleteModeById(String modeId) throws Exception;






}
