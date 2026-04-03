package hy.ea.driving.service.impl;

import hy.ea.bo.DrivingSchool.elyc.TbElycReservationMode;
import hy.ea.bo.DrivingSchool.elyc.TbElycScheduling;

import hy.ea.bo.DrivingSchool.elyc.TbElycSchedulingDetail;
import hy.ea.bo.office.Schedule;
import hy.ea.driving.service.ElkcSchedulingService;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ElkcSchedulingServiceImpl implements ElkcSchedulingService {
	@Resource
	private BaseBeanDao basedao;
	@Resource
	private ServerService serverService;


	/**
	 *
	 * 保存班次
	 * @param companyId
	 * @param staffID
	 */
	@Transactional
	public void saveScheduling(TbElycScheduling scheduling, TbElycSchedulingDetail schedulingDetail, String companyId, String staffID){
         List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
         String classId = "";
	 	if(scheduling!=null&&scheduling.getClassId()!=null&&!scheduling.getClassId().equals("")){
			//删除原来配置
			deleteSchedulDetailById(scheduling.getClassId());
			String hql = "from TbElycScheduling where classId = ?";
			TbElycScheduling schedul = (TbElycScheduling)basedao.getBeanByHqlAndParams(hql,new Object[]{scheduling.getClassId()});
			schedul.setStatus(scheduling.getStatus());
			schedul.setClassName(scheduling.getClassName());
			schedul.setUpdatedate(new Date());
			schedul.setUpdateperson(staffID);
			classId = schedul.getClassId();
			baseBeanList.add(schedul);

		}else{
			//新建
			scheduling.setClassId(serverService.getServerID("classId"));
			scheduling.setCreatedate(new Date());
			scheduling.setCreateperson(staffID);
			scheduling.setUpdatedate(new Date());
			scheduling.setUpdateperson(staffID);
			scheduling.setCompanyId(companyId);
			classId = scheduling.getClassId();
			scheduling.setRotateMode("日");
			baseBeanList.add(scheduling);
		}

		//新建关联表
		schedulingDetail.setScdId(serverService.getServerID("scdId"));
	 	schedulingDetail.setUpdatedate(new Date());
	 	schedulingDetail.setCreatedate(new Date());
	 	schedulingDetail.setClassId(classId);
	 	schedulingDetail.setCreateperson(staffID);
		schedulingDetail.setUpdateperson(staffID);
		schedulingDetail.setRotateMode("日");
		baseBeanList.add(schedulingDetail);
		basedao.saveBeansListAndexecuteHqlsByParams(baseBeanList,null,null);

	}
	@Transactional
	public void deleteSchedul(String classId){
		deleteSchedulDetailById(classId);
		String hql = "delete from TbElycScheduling where classId = ?";
		basedao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{classId});
	}
	@Transactional
	private void deleteSchedulDetailById(String classId){
         String hql = "delete from TbElycSchedulingDetail where classId = ?";
         basedao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{classId});

	}

	/**
	 *
	 * 获取公司的启用的时间模式
	 * @param companyId
	 * @return
	 */
	public List<TbElycReservationMode> getModeList(String companyId){
		String hql = "from TbElycReservationMode where companyId = ? and status = ?";
		List list = basedao.getListBeanByHqlAndParams(hql,new Object[]{companyId,"01"});
		List<TbElycReservationMode> modelist = (List<TbElycReservationMode>)list;

		return modelist;
	}
}
