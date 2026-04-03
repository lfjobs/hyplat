package hy.ea.office.dao;

import java.util.List;

import hy.ea.bo.office.CarCheckTask;
import hy.plat.CoreDao;

public interface CheckTaskDao  extends CoreDao<CarCheckTask>{
	
	List<CarCheckTask> getNotCheckTask(String principal, String companyId);

}
