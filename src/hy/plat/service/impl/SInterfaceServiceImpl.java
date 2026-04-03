package hy.plat.service.impl;

import hy.plat.bo.SInterface;
import hy.plat.dao.SInterfaceDao;
import hy.plat.service.SInterfaceService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class SInterfaceServiceImpl implements SInterfaceService{
	
	@Resource
	private SInterfaceDao sinterfaceDao;

	@Override
	public SInterface getSInterfaceByID(String interfaceID) {
		return sinterfaceDao.getSInterfaceByID(interfaceID);
	}

	@Override
	public void deleteSInterfaceByID(String interfaceID) {
		sinterfaceDao.deleteSInterfaceByID(interfaceID);
	}

	@Override
	public List<SInterface> getSInterfaceListByStatus() {
		return sinterfaceDao.getSInterfaceListByStatus();
	}
}
