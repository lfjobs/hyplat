package hy.ea.production.service;

import java.io.InputStream;
import java.util.List;

public interface WarehouseService {
	InputStream OutboundOrderExcel(List<Object> list);
	InputStream OutFieldisExcel(List<Object> list);
	InputStream OutOrderExcel(String title, String[] str, List<Object> list);
	void EstablishGoodsNumber(String[] str);
	/**
	 * 入库物品时生成对应序号
	 *	参数顺序：
		 *	实体类名称
		 *	公司ID
		 *	库存表ID
		 *	物品单据表ID
		 *	物品ID
		 *	状态
		 * 数量   数量为number类型
		 * 品名编号
	 */
	void numberOfGeneratedItems(String[] str);
	InputStream receiptExcelTable(String title, String[] str, List<Object> list);
	/*
	 * 参数排序
	 * ppID		：  当前产品ID
	 * goodsName		当前产品名称
	 * productPID		最上级产品ID
	 * cashierBillsID	单据ID
	 */
	List<Object> getProductMix(String[] str);
}
