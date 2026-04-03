package hy.ea.production.dao;

import java.io.InputStream;
import java.util.List;



public interface WarehouseDao {
	
	InputStream OutboundOrderExcel(List<Object> list);
	InputStream OutFieldisExcel(List<Object> list);
	InputStream OutOrderExcel(String title, String[] str, List<Object> list);
	void EstablishGoodsNumber(String[] str);
	void numberOfGeneratedItems(String[] str);
	InputStream receiptExcelTable(String title, String[] str, List<Object> list);
	List<Object> getProductMix(String[] str);
}
