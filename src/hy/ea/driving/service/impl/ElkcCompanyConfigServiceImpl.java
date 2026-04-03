package hy.ea.driving.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.DrivingSchool.elyc.TbElycCompanyConfig;
import hy.ea.dao.CAccountDao;
import hy.ea.driving.dao.ElkcCompanyConfigDao;
import hy.ea.driving.service.ElkcCompanyConfigService;
import hy.ea.service.CAccountService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ElkcCompanyConfigServiceImpl implements ElkcCompanyConfigService {
	@Resource
	private BaseBeanDao basedao;
	@Resource
	private ServerService serverService;
	@Resource
	private ElkcCompanyConfigDao companyConfigDao;
	/**
	 *
	 * 保存或者更新配置
	 * @param tbElycCompanyConfig
	 */
	@Transactional
	@Override
	public void saveConfig(TbElycCompanyConfig tbElycCompanyConfig,String companyID,String staffID) {
		if(tbElycCompanyConfig!=null&&tbElycCompanyConfig.getCcId()!=null&&!tbElycCompanyConfig.getCcId().equals("")){
			String hql = "from TbElycCompanyConfig where ccId = ?";
			TbElycCompanyConfig config = (TbElycCompanyConfig)basedao.getBeanByHqlAndParams(hql,new Object[]{tbElycCompanyConfig.getCcId()});
			config.setGenday(tbElycCompanyConfig.getGenday());
			config.setStudentay(tbElycCompanyConfig.getStudentay());
			config.setStartTime(tbElycCompanyConfig.getStartTime());
			config.setEndTime(tbElycCompanyConfig.getEndTime());
			config.setCancelDetail(tbElycCompanyConfig.getCancelDetail());
			config.setStudentCanBookingToday(tbElycCompanyConfig.getStudentCanBookingToday());
			config.setStudentLeaveTime(tbElycCompanyConfig.getStudentLeaveTime());
			config.setUpdatedate(new Date());
			config.setUpdateperson(staffID);
			basedao.update(config);

		}else{
			tbElycCompanyConfig.setCcId(serverService.getServerID("ccId"));
			tbElycCompanyConfig.setCreatedate(new Date());
			tbElycCompanyConfig.setCreateperson(staffID);
			tbElycCompanyConfig.setCompanyId(companyID);
			tbElycCompanyConfig.setIsUsed("0");//尚未生成时间段
			basedao.save(tbElycCompanyConfig);
		}

	}


	/**
	 * 修改时获取内容
	 * @param companyId
	 * @return
	 */
	@Override
	public TbElycCompanyConfig findCompanyConfig(String companyId) {
		String hql = "from TbElycCompanyConfig where companyId = ?";
		TbElycCompanyConfig config = (TbElycCompanyConfig) basedao.getBeanByHqlAndParams(hql,new Object[]{companyId});
		return config;
	}

	/**
	 *
	 * 获取所有已启用的配置
	 * @return
	 */
	public List<BaseBean> getCompanyConfig(){
		String hqlconfig = "from TbElycCompanyConfig where isUsed = ?";
		List<BaseBean> list = basedao.getListBeanByHqlAndParams(hqlconfig,new Object[]{"1"});
		return list;
	}

	/**
	 *
	 * 初始化约车配置
	 * @param companyId
	 */
	@Override
	public  void  createTeacherTime(String companyId,String staffID,Date date){

		String hql = "from TbElycCompanyConfig where companyId = ?";

		TbElycCompanyConfig config = (TbElycCompanyConfig) basedao.getBeanByHqlAndParams(hql,new Object[]{companyId});

		companyConfigDao.createTeacherTime(companyId,staffID,date);
		companyConfigDao.updateTeacherLeave(companyId,date);


	}

	/**
	 *
	 * 查询某个教练某个时间段状态的个数
	 * @return
	 */
	public Integer getOrderDetailTimeStatusCount(String companyId,String teacherId,Date startTime,Date endTime,String status){
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from Tb_Elyc_Order_Detail_Time t where t.teacherId = ? and t.companyId = ? and t.status = ?");
        sb.append(" and ((? >= t.lessionStartTime  and  ? < t.lessionEndTime)"); //9:00 =start10:00
		sb.append(" or (? > t.lessionStartTime and ? <= t.lessionEndTime)");//9:00 end 10:00
		sb.append(" or (t.lessionStartTime >= ? and t.lessionEndTime <= ?))");//=start9:00  10：00 end=

		int count = basedao.getConutByBySqlAndParams(sb.toString(),new Object[]{teacherId,companyId,status,startTime,startTime,endTime,endTime,startTime,endTime});
		return count;
	}

	/**
	 * 查询所有状态为的预约
	 *
	 * @return
	 */
	public  List<BaseBean> getOrderDetailTimeStatusList(String teacherId,String companyId,Date startTime,Date endTime){
		StringBuilder sb = new StringBuilder();

		sb.append("from TbElycOrderDetailTime where t.teacherId = ? and t.companyId = ?");
		sb.append(" and ((? >= t.lessionStartTime  and  ? < t.lessionEndTime)"); //9:00 =start10:00
		sb.append(" or (? > t.lessionStartTime and ? <= t.lessionEndTime)");//9:00 end 10:00
		sb.append(" or (t.lessionStartTime >= ? and t.lessionEndTime <= ?))");//=start9:00  10：00 end=
		List<BaseBean> list = basedao.getListBeanByHqlAndParams(sb.toString(),new Object[]{teacherId,companyId,"1",startTime,startTime,endTime,endTime,startTime,endTime});

         return list;
	}

}
