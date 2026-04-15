package hy.ea.office.service.impl;

import hy.ea.bo.office.CarCheckTask;
import hy.ea.office.dao.CheckTaskDao;
import hy.ea.office.service.CheckTaskService;
import hy.plat.CoreManagerImpl;
import hy.plat.bo.PageForm;
import hy.plat.common.hibernate3.Finder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckTaskServiceImpl extends CoreManagerImpl<CarCheckTask>
		implements CheckTaskService {

	@Autowired
	public void setDao(CheckTaskDao dao) {
		super.setDao(dao);
	}

	protected CheckTaskDao getDao() {
		return (CheckTaskDao) super.getDao();
	}

	@Override
	public PageForm getPageForm(int pageNo, int pageSize, CarCheckTask task) {
		Finder finder = null;
		try {
			finder = new Finder(" from CarCheckTask as bean");
			finder.append(" where bean.companyid=:companyid").setParam(
					"companyid", task.getCompanyid());
			if (task.getChecktaskname() != null
					&& !task.getChecktaskname().equals("")) {
				finder.append(" and bean.checktaskname like :checktaskname")
						.setParam("checktaskname",
								"%" + task.getChecktaskname().trim() + "%");
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
	public CarCheckTask findUniqueBeanById(Object value) {
		return getDao().findUniqueByProperty("checktaskid", value);
	}

	/**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckTask> findByProperty(String property, Object value) {
		return getDao().findByProperty(property, value);
	}

	@Override
	public List<CarCheckTask> getNotCheckTask(String principal, String companyId) {

		return getDao().getNotCheckTask(principal, companyId);
	}

}
