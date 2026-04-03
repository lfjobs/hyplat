package hy.ea.driving.service.impl;

import hy.ea.bo.DrivingSchool.elyc.TbElycReservationDetail;
import hy.ea.bo.DrivingSchool.elyc.TbElycReservationMode;
import hy.ea.driving.dao.ElkcCompanyConfigDao;
import hy.ea.driving.service.ElkcReservModeService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@Service
public class ElkcReservModeServiceImpl implements ElkcReservModeService {
	@Resource
	private BaseBeanDao basedao;
	@Resource
	private ServerService serverService;
	@Resource
	private ElkcCompanyConfigDao companyConfigDao;

	/***
	 *
	 * 保存时间段模式
	 * @param companyId
	 * @param staffID
	 */
	@Override
	@Transactional
	public String updateReservationMode(TbElycReservationMode mode, List<TbElycReservationDetail> detailList, String staffID, String companyId) throws Exception {
		String detailResion = "";
		if(StringUtils.isNotBlank(mode.getReservationModeId())) {//如果是修改状态
			this.deleteDetailByModeId(mode.getReservationModeId());//删除之前的详细
			Vector modeId = new Vector();
			modeId.add((Object)null);
			detailList.removeAll(modeId);
			if(detailList != null && detailList.size() > 0) {
				Collections.sort(detailList, new Comparator<TbElycReservationDetail>() {
					public int compare(TbElycReservationDetail arg0, TbElycReservationDetail arg1) {


						Date date1 = DateUtilElkc.toDateTime("2015-11-11 " + arg0.getStartTime() + ":00");
						Date date2 = DateUtilElkc.toDateTime("2015-11-11 " + arg1.getStartTime() + ":00");
						return date1.compareTo(date2);
					}
				});

				TbElycReservationDetail nuCon;
				for(Iterator detail = detailList.iterator(); detail.hasNext(); detailResion = detailResion + String.valueOf(this.checkDetailTimeCanAdd(nuCon)) + ",") {
					nuCon = (TbElycReservationDetail)detail.next();
					nuCon.setCreateperson(staffID);
					nuCon.setUpdateperson(staffID);
					nuCon.setReservationModeId(mode.getReservationModeId());
				}
			}

			mode.setUpdateperson(staffID);
			mode.setUpdatedate(new Date());
			basedao.update(mode);
		} else {
			String modeId1 = serverService.getServerID("modeId");
			Vector nuCon1 = new Vector();
			nuCon1.add((Object)null);
			detailList.removeAll(nuCon1);
			TbElycReservationDetail detail1;
			if(detailList != null && detailList.size() > 0) {
				for(Iterator i = detailList.iterator(); i.hasNext(); detailResion = detailResion + String.valueOf(this.checkDetailTimeCanAdd(detail1)) + ",") {
					detail1 = (TbElycReservationDetail)i.next();
					detail1.setCreateperson(staffID);
					detail1.setUpdateperson(staffID);
					detail1.setReservationModeId(modeId1);
				}
			}

			mode.setReservationModeId(modeId1);
			mode.setCompanyId(companyId);
			mode.setCreateperson(staffID);
			mode.setUpdateperson(staffID);
			mode.setCreatedate(new Date());
			mode.setUpdatedate(new Date());
			basedao.save(mode);
		}

		return detailResion;
	}
	@Transactional
	private void deleteDetailByModeId(String modeId) throws Exception {

		String hql = "delete from TbElycReservationDetail where reservationModeId = ?";
        basedao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{modeId});

	}

	@Transactional
	public boolean checkDetailTimeCanAdd(TbElycReservationDetail obj) throws Exception {
		if(obj != null && obj.getStartTime() != null && obj.getEndTime() != null) {
			Date startTime = DateUtilElkc.toDateTime("2015-11-11 " + obj.getStartTime() + ":00");
			Date endTime = DateUtilElkc.toDateTime("2015-11-11 " + obj.getEndTime() + ":00");
			long t = DateUtilElkc.getDifftime(endTime, startTime, "mi");
			if(t % 60L == 0L && StringUtils.isNotBlank(obj.getReservationModeId())) {
//				String hql = "from TbElycReservationDetail where reservationModeId = ?";
//				List list = basedao.getListBeanByHqlAndParams(hql,new Object[]{obj.getReservationModeId()});
//				if(list == null || list.size() < 1) {
					this.addDetailObject(obj, t / 60L);
					return true;
			//	}

//				TbElycReservationDetail detail = (TbElycReservationDetail)list.get(list.size() - 1);
//				if(DateUtilElkc.toDateTime("2015-11-11 " + detail.getEndTime() + ":00").compareTo(endTime) < 0) {
//					this.addDetailObject(obj, t / 60L);
//					return true;
//				}
			}
		}

		return false;
	}
	@Transactional
	private void addDetailObject(TbElycReservationDetail obj, long hours) throws Exception {
		if(obj != null) {
			obj.setHours(Double.valueOf((double)hours));
			obj.setRdId(serverService.getServerID("rdId"));
			obj.setCreatedate(new Date());
			obj.setUpdatedate(new Date());
			basedao.save(obj);
		}

	}


	public TbElycReservationMode getReservModeById(String modeId){

		String hql = "from TbElycReservationMode where reservationModeId = ?";
		TbElycReservationMode mode = (TbElycReservationMode) basedao.getBeanByHqlAndParams(hql,new Object[]{modeId});

		return mode;

	}

	public List<TbElycReservationDetail> getDetailObjectsByModeId(String modeId){

		List detaillist =  basedao.getListBeanByHqlAndParams("from TbElycReservationDetail where reservationModeId = ?",new Object[]{modeId});

		List<TbElycReservationDetail> list = (List<TbElycReservationDetail>)detaillist;
		return  detaillist;

	}
	@Transactional
	@Override
	public void deleteModeById(String modeId) throws Exception {

		deleteDetailByModeId(modeId);
		String hql = "delete from TbElycReservationMode where reservationModeId = ?";
		basedao.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{modeId});


	}

}
