package hy.ea.service.impl;

import hy.ea.bo.CLogBook;
import hy.ea.dao.CEMBDao;
import hy.ea.service.CEMBService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CEMBServiceImpl implements CEMBService{

	@Resource
	private CEMBDao cembDao;
	
	@Override
	public void saveCEMB(String companyID,String eaID, String menuID, String[] boIDs,CLogBook logBook) {
		cembDao.saveCEMB(companyID,eaID, menuID, boIDs,logBook);
	}
}
