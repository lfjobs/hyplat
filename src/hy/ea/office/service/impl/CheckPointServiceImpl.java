package hy.ea.office.service.impl;


import java.util.List;
 
import hy.ea.bo.office.CarCheckPoint; 
import hy.ea.bo.office.CarCheckTask;
import hy.ea.office.dao.CheckPointDao;
import hy.ea.office.service.CheckPointService;
import hy.plat.CoreManagerImpl;
import hy.plat.bo.PageForm;
import hy.plat.common.hibernate3.Finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckPointServiceImpl extends CoreManagerImpl<CarCheckPoint>
		implements CheckPointService {
	
	@Autowired
	public void setDao(CheckPointDao dao) {
		super.setDao(dao);
	}

	protected CheckPointDao getDao() {
		return (CheckPointDao) super.getDao();
	}

	@Override
	public PageForm getPageForm(int pageNo, int pageSize, String sql,Object[] params,CarCheckPoint point) {

		return getPageForm(pageNo, pageSize, sql, params);
	}
	
	
	@Override
	public PageForm getPageForm(int pageNo, int pageSize, CarCheckPoint point) {

		Finder finder = null;
		try {
			finder = new Finder(" from CarCheckPoint as bean");
			finder.append(" where bean.companyid=:companyid").setParam(
					"companyid", point.getCompanyid());
			if (point.getCheckpointname()!= null
					&& !point.getCheckpointname().equals("")) {
				finder.append(" and bean.checkpointname like :checkpointname")
						.setParam("checkpointname",
								"%"+point.getCheckpointname().trim()+"%");
			}
			if (point.getCarNum()!= null
					&& !point.getCarNum().equals("")) {
				finder.append(" and bean.carNum like :carNum")
						.setParam("carNum",
								"%"+point.getCarNum().trim()+"%");
			}
			finder.append(" order by bean.createtime desc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getPageForm(pageNo, pageSize, finder);
	}

	/**
	 * 
	 * 根据ID查找唯一Bean
	 */
	@Override
	public CarCheckPoint findUniqueBeanById(Object value) {
		return getDao().findUniqueByProperty("checkpointid", value);
	}
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckPoint> findByProperty(String property, Object value) {
		return getDao().findByProperty(property, value);
	}
	
	
	
	public List<CarCheckPoint> getPointListByTask(CarCheckTask task){
		Finder finder = null;
		try {
			finder = new Finder(" from CarCheckPoint as bean");
			finder.append(" where exists(select ti.pointid from CarCheckTaskItem ti where ti.taskid=:taskid and ti.pointid = bean.checkpointid)").setParam(
					"taskid", task.getChecktaskid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return getDao().findByList(finder);
	}


}
