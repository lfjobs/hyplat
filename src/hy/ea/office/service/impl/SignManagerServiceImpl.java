package hy.ea.office.service.impl;

import hy.ea.bo.office.SignManager;
import hy.ea.office.service.SignManagerService;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class SignManagerServiceImpl implements SignManagerService {

	@Resource
	private hy.ea.office.dao.SignManagerDao SignManagerDao;

	public hy.ea.office.dao.SignManagerDao getSignManagerDao() {
		return SignManagerDao;
	}

	public void setSignManagerDao(
			hy.ea.office.dao.SignManagerDao signManagerDao) {
		SignManagerDao = signManagerDao;
	}

	@Override
	public boolean DeleteSignManager(String signmanagerkey) {
		// TODO Auto-generated method stub
		return SignManagerDao.DeleteSignManager(signmanagerkey);
	}

	@Override
	public void InsertSignManager(SignManager signmanager) {
		// TODO Auto-generated method stub
		SignManagerDao.InsertSignManager(signmanager);
	}

	@Override
	public boolean UpdatePosition(String position, String signmanagerkey) {
		// TODO Auto-generated method stub
		return SignManagerDao.UpdatePosition(position, signmanagerkey);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getSignManager(String signmanagerkey) {
		// TODO Auto-generated method stub
		return SignManagerDao.getSignManager(signmanagerkey);
	}

	@Override
	public boolean UpdateRelationtable(String signstat, String relationid) {
		// TODO Auto-generated method stub
		return SignManagerDao.UpdateRelationtable(signstat, relationid);
	}

	@Override
	public boolean UpdateSignstat(String signstat, String signmanagerkey) {
		// TODO Auto-generated method stub
		return SignManagerDao.UpdateSignstat(signstat, signmanagerkey);
	}

	@Override
	public boolean SignManager(SignManager signManager, String signstat,
			String signmanagerkey, String relationid) {
		// TODO Auto-generated method stub
		if (SignManagerDao.UpdateRelationtable(signstat, relationid) == true) {
			boolean bool = SignManagerDao.UpdateSignstat(signstat, signmanagerkey);
			if (bool == true) {
				SignManagerDao.InsertSignManager(signManager);
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public List QuerySignManager(String accountid, int rp, int pages,
			String signstat, String signid, String relationtable,
			Date starttime, Date endtime) {
		List list = SignManagerDao.QuerySignManager(accountid, rp, pages,
				signstat, signid, relationtable, starttime, endtime);
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List QueryRelationTable(String accountid, int rp, int pages,
			String relationid, String signstat, String signid, Date starttime,
			Date endtime) {
		List list = SignManagerDao.QueryRelationTable(accountid, rp, pages,
				relationid, signstat, signid, starttime, endtime);
		return list;
	}

	public int CountRelationTable(String accountid, String relationid,
			String signstat, String signid, Date starttime, Date endtime) {
		int result = SignManagerDao.CountRelationTable(accountid, relationid,
				signstat, signid, starttime, endtime);
		return result;
	}

	public int CountSignManager(String accountid, int rp, int pages, String signstat,
			String signid, String relationtable, Date starttime, Date endtime) {
		int result = SignManagerDao.CountSignManager(accountid, rp, pages, signstat,
				signid, relationtable, starttime, endtime);
		return result;
	}

}
