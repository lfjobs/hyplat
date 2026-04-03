package hy.ea.office.dao.impl;

import java.util.List;
 
import hy.ea.bo.office.CarCheckTaskItem; 
import hy.ea.office.dao.CheckTaskItemDao; 
import hy.plat.CoreDaoImpl; 

import org.springframework.stereotype.Repository;

@Repository
public class CheckTaskItemDaoImpl extends CoreDaoImpl<CarCheckTaskItem> implements CheckTaskItemDao{

	@Override
	public List<CarCheckTaskItem> getListByTaskId(String taskId) {
	    return findByProperty("taskid", taskId); 
	}
}
