package hy.ea.invoicing.service.impl;

import hy.ea.invoicing.dao.EstablishDao;
import hy.ea.invoicing.service.EstablishService;

public class EstablishServiceImpl implements EstablishService{
	EstablishDao establishDao;
	@Override
	public void establishGoodsNumber(Object[] obj) {
		// TODO Auto-generated method stub
		establishDao.establishGoodsNumber(obj);
	}

}
