package hy.ea.office.service.impl;

import hy.ea.bo.office.CarCheckDefect;
import hy.ea.office.dao.CheckDefectDao; 
import hy.ea.office.service.CheckDefectService;
import hy.plat.CoreManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CheckDefectServiceImpl extends CoreManagerImpl<CarCheckDefect> implements CheckDefectService{
	@Autowired
	public void setDao(CheckDefectDao dao) {
		super.setDao(dao);
	}

	protected CheckDefectDao getDao() {
		return (CheckDefectDao) super.getDao();
	}
}
