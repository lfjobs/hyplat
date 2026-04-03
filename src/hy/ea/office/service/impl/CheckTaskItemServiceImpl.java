package hy.ea.office.service.impl;
 
import hy.ea.bo.office.CarCheckTaskItem;
import hy.ea.office.dao.CheckTaskItemDao;
import hy.ea.office.service.CheckTaskItemService;
import hy.plat.CoreManagerImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckTaskItemServiceImpl extends CoreManagerImpl<CarCheckTaskItem>
		implements CheckTaskItemService {

	@Autowired
	public void setDao(CheckTaskItemDao dao) {
		super.setDao(dao);
	}

	protected CheckTaskItemDao getDao() {
		return (CheckTaskItemDao) super.getDao();
	}

	@Override
	public List<CarCheckTaskItem> getListByTaskId(String taskId) {
		
		return getDao().getListByTaskId(taskId);
	}

	/**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckTaskItem> findByProperty(String property, Object value) {
		return getDao().findByProperty(property, value);
	}

	@Override
	public CarCheckTaskItem findUniqueBeanById(Object value) {
		return getDao().findUniqueByProperty("taskitemid", value);
	}
	
	
	public CarCheckTaskItem findUnique(String hql, Object... values){
		return (CarCheckTaskItem) getDao().findUnique(hql, values);
	}
}
