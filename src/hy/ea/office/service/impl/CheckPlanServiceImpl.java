package hy.ea.office.service.impl;

import java.util.List;

import hy.ea.bo.office.CarCheckPlan; 
import hy.ea.office.dao.CheckPlanDao;
import hy.ea.office.service.CheckPlanService;
import hy.plat.CoreManagerImpl;
import hy.plat.bo.PageForm;
import hy.plat.common.hibernate3.Finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckPlanServiceImpl extends CoreManagerImpl<CarCheckPlan>
		implements CheckPlanService {

	@Autowired
	public void setDao(CheckPlanDao dao) {
		super.setDao(dao);
	}

	protected CheckPlanDao getDao() {
		return (CheckPlanDao) super.getDao();
	}

	@Override
	public PageForm getPageForm(int pageNo, int pageSize, CarCheckPlan plan) {
		Finder finder = null;
		try {
			finder = new Finder(" from CarCheckPlan as bean");
			finder.append(" where bean.companyid=:companyid").setParam(
					"companyid", plan.getCompanyid());
			if (plan.getCheckplanname() != null
					&& !plan.getCheckplanname().equals("")) {
				finder.append(" and bean.checkplanname like :checkplanname")
						.setParam("checkplanname",
								"%"+plan.getCheckplanname().trim()+"%");
			}
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
	public CarCheckPlan findUniqueBeanById(Object value) {
		return getDao().findUniqueByProperty("checkplanid", value);
	}

	/**
	 * 按属性查找对象列表.
	 */
	public List<CarCheckPlan> findByProperty(String property, Object value) {
		return getDao().findByProperty(property, value);
	}

}
