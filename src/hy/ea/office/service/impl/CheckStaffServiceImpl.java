package hy.ea.office.service.impl;

import hy.ea.bo.human.Staff;
import hy.ea.office.dao.CheckStaffDao;
import hy.ea.office.service.CheckStaffService;
import hy.plat.CoreManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckStaffServiceImpl extends CoreManagerImpl<Staff>
		implements CheckStaffService {

	@Autowired
	public void setDao(CheckStaffDao dao) {
		super.setDao(dao);
	}

	protected CheckStaffDao getDao() {
		return (CheckStaffDao) super.getDao();
	}

	/**
	 * 
	 * 根据ID查找唯一Bean
	 */
	@Override
	public Staff findUniqueBeanById(Object value) {
		return getDao().findUniqueByProperty("staffID", value);
	}





}
