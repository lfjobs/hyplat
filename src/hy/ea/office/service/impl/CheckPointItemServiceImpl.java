package hy.ea.office.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.office.CarCheckPointItem;
import hy.ea.office.dao.CheckPointItemDao;
import hy.ea.office.service.CheckPointItemService;
import hy.plat.CoreManagerImpl;
import hy.plat.bo.PageForm;
import hy.plat.common.hibernate3.Finder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckPointItemServiceImpl extends
		CoreManagerImpl<CarCheckPointItem> implements CheckPointItemService {

	@Autowired
	public void setDao(CheckPointItemDao dao) {
		super.setDao(dao);
	}

	protected CheckPointItemDao getDao() {
		return (CheckPointItemDao) super.getDao();
	}

	@Override
	public PageForm getPageForm(int pageNo, int pageSize,
			CarCheckPointItem pointitem) {

		Finder finder = null;
		try {
			finder = new Finder(" from CarCheckPointItem as bean");
			finder.append(" where bean.companyid=:companyid").setParam(
					"companyid", pointitem.getCompanyid());
			if (pointitem.getCheckpointitemname() != null
					&& !pointitem.getCheckpointitemname().equals("")) {
				finder.append(" and bean.checkpointitemname like :checkpointitemname").setParam(
						"checkpointitemname", "%"+pointitem.getCheckpointitemname().trim()+"%");
			}
			finder.append(" order by bean.createtime desc");
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return getPageForm(pageNo, pageSize, finder);
	}
	
	/**
	 * 
	 * 根据ID查找唯一Bean
	 */
	@Override
	public CarCheckPointItem findUniqueBeanById(Object value) {
		return getDao().findUniqueByProperty("checkpointitemid", value);
	}
	
	
	/**
	 * 按属性查找对象列表.
	 */
	@Override
	public List<CarCheckPointItem> findByProperty(String property, Object value){
		return getDao().findByProperty(property, value);
	}

	@Override
	public List<CarCheckPointItem> findBeanByPointId(Object value) {
		return getDao().findByProperty("checkpointid", value); 
	}

}