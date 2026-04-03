package hy.ea.driving.service;

import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * mz
 */
public interface ElkcCompanyConfigService {
    /**
     *
     * 保存或者更新配置
     * @param tbElycCompanyConfig
     */
    public void saveConfig(TbElycCompanyConfig tbElycCompanyConfig,String companyID,String staffID);

    /**
     * 修改时获取内容
     * @param companyId
     * @return
     */
  public TbElycCompanyConfig findCompanyConfig(String companyId);

    /**
     *
     * 获取所有已启用的配置
     * @return
     */
    public List<BaseBean> getCompanyConfig();

    /**
     *
     * 初始化当前驾校约车配置
     * @param companyId
     */
    public  void  createTeacherTime(String companyId,String staffID,Date date);

    /**
     *
     * 查询某个教练某个时间段状态的个数
     * @return
     */
    public Integer getOrderDetailTimeStatusCount(String companyId,String teacherId,Date startTime,Date endTime,String status);


    public List<BaseBean> getOrderDetailTimeStatusList(String teacherId, String companyId, Date startTime, Date endTime);


}
