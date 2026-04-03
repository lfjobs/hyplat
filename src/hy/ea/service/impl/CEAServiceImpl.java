package hy.ea.service.impl;

import hy.ea.dao.CEADao;
import hy.ea.service.CEAService;
import hy.plat.bo.SEA;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class  CEAServiceImpl implements CEAService{
	
	@Resource
	private CEADao ceaDao;

	@Override
	public List<SEA> getListSea(String companyID) {
		return ceaDao.getListSea(companyID);
	}

	@Override
	public List<SEA> getListSea(String companyID,String roleID) {
		return ceaDao.getListSea(companyID,roleID);
	}

	@Override
	public void pushSEAtoCEA(String companyID, String eaID,String roleID) {
		ceaDao.pushSEAtoCEA(companyID, eaID,roleID);
	}

	@Override
	public void updateSEAtoCEA(String companyID, SEA cea, String roleID) {
		ceaDao.updateSEAtoCEA(companyID, cea, roleID);
	}

}
