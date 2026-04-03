package hy.ea.service.impl;

import hy.ea.bo.CLogBook;
import hy.ea.bo.CMenu;
import hy.ea.dao.CMenuDao;
import hy.ea.service.CMenuService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CMenuServiceImpl implements CMenuService{

	@Resource
	private CMenuDao menuDao;
	
	@Override
	public void deleteMenuByID(String companyID,CLogBook logBook, String menuID) {
		menuDao.deleteMenuByID(companyID,logBook,menuID);
	}

	@Override
	public CMenu getMenuByID(String companyID,String menuID) {
		return menuDao.getMenuByID(companyID,menuID);
	}

	@Override
	public List<CMenu> getMenuListByEaID(String companyID,String eaID) {
		return menuDao.getMenuListByEaID(companyID,eaID);
	}

}
