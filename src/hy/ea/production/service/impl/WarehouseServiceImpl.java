package hy.ea.production.service.impl;


import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import hy.ea.production.dao.WarehouseDao;
import hy.ea.production.service.WarehouseService;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	@Resource
	private WarehouseDao warehouseDao;

	@Override
	public InputStream OutboundOrderExcel(List<Object> list) {
		InputStream input=warehouseDao.OutboundOrderExcel(list);
		return input;
	}

	@Override
	public InputStream OutFieldisExcel(List<Object> list) {
		InputStream input=warehouseDao.OutFieldisExcel(list);
		return input;
	}

	@Override
	public InputStream OutOrderExcel(String title, String[] str,
			List<Object> list) {
		InputStream input=warehouseDao.OutOrderExcel(title,str,list);
		return input;
	}
	@Override
	public InputStream receiptExcelTable(String title, String[] str,
			List<Object> list) {
		InputStream input=warehouseDao.receiptExcelTable(title,str,list);
		return input;
	}
	@Override
	public void EstablishGoodsNumber(String[] str) {
		warehouseDao.EstablishGoodsNumber(str);
	}

	@Override
	public void numberOfGeneratedItems(String[] str) {
		warehouseDao.numberOfGeneratedItems(str);
	}

	@Override
	public List<Object> getProductMix(String[] str) {
		return warehouseDao.getProductMix(str);
	}


}
