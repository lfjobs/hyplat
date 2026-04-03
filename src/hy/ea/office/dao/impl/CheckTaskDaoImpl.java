package hy.ea.office.dao.impl;

import java.util.List;

import hy.ea.bo.office.CarCheckTask;
import hy.ea.office.dao.CheckTaskDao;
import hy.plat.CoreDaoImpl;
import hy.plat.common.hibernate3.Finder;

import org.springframework.stereotype.Repository;

@Repository
public class CheckTaskDaoImpl extends CoreDaoImpl<CarCheckTask> implements CheckTaskDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<CarCheckTask> getNotCheckTask(String principal, String companyId) {
		Finder f = Finder.create("select bean from CarCheckTask bean");
		f.append(" where bean.taskstatus=:taskstatus").setParam("taskstatus", 0);
		f.append(" and bean.principal=:principal").setParam("principal", principal);
		f.append(" and bean.companyid=:companyid").setParam("companyid", companyId);
		
		return find(f); 
	}
}
