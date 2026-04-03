package hy.ea.service.impl;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CRole;
import hy.ea.dao.CRoleDao;
import hy.ea.service.CRoleService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CRoleServiceImpl implements CRoleService{

	@Resource
	private CRoleDao roleDao;
	
	@Override
	public void deleteRoleByID(String companyID,String roleID,CLogBook logBook) {
		roleDao.deleteRoleByID(companyID,roleID,logBook);
	}

	@Override
	public CRole getRoleByID(String companyID,String roleID) {
		return roleDao.getRoleByID(companyID,roleID);
	}

	@Override
	public List<CRole> getRoleList(String companyID) {
		return roleDao.getRoleList(companyID);
	}

}
