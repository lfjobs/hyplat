package hy.ea.office.dao;

import java.util.List;

import hy.ea.bo.office.CarCheckTaskItem; 
import hy.plat.CoreDao;

public interface CheckTaskItemDao  extends CoreDao<CarCheckTaskItem>{
	
	List<CarCheckTaskItem> getListByTaskId(String taskId);

}
