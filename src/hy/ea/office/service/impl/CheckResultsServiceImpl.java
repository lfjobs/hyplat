package hy.ea.office.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
 
import hy.ea.bo.office.vo.ViewCarcheckresults;
import hy.ea.office.dao.CheckResultsDao;
import hy.ea.office.service.CheckResultsService;
import hy.plat.CoreManagerImpl;
import hy.plat.bo.PageForm;
import hy.plat.common.hibernate3.Finder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckResultsServiceImpl extends
		CoreManagerImpl<ViewCarcheckresults> implements CheckResultsService {

	@Autowired
	public void setDao(CheckResultsDao dao) {
		super.setDao(dao);
	}

	protected CheckResultsDao getDao() {
		return (CheckResultsDao) super.getDao();
	}

	@Override
	public PageForm getPageForm(int pageNo, int pageSize,
			ViewCarcheckresults results) {

		Finder finder = null;
		try {
			finder = new Finder(" from ViewCarcheckresults as bean");
			finder.append(" where bean.companyid=:companyid").setParam(
					"companyid", results.getCompanyid());
			if (results.getChecktaskid() != null
					&& !results.getChecktaskid().equals("")) {
				finder.append(" and bean.checktaskid=:checktaskid").setParam(
						"checktaskid", results.getChecktaskid().trim());
			}
			if (results.getCarnum() != null && !results.getCarnum().equals("")) {
				finder.append(" and bean.carnum like :carnum").setParam(
						"carnum", "%"+results.getCarnum().trim()+"%");
			}
			if (results.getFinishflag() != null && !results.getFinishflag().equals("")) {
				finder.append(" and bean.finishflag =:finishflag").setParam(
						"finishflag", results.getFinishflag().trim());
			}
			finder.append(" order by checkdatetime desc");
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return getPageForm(pageNo, pageSize, finder);
	}
	
	/**
	 * 按属性查找对象列表.
	 */
	public List<ViewCarcheckresults> findByProperty(String property, Object value) {
		return getDao().findByProperty(property, value);
	}

}
