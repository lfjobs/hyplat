package hy.ea.invoicing.service;

import hy.plat.bo.BaseBean;

import java.util.List;

public interface EstablishService {
	/**
	 * 入库物品时生成对应序号
	 *	参数顺序：
		 *	实体类名称
		 *	公司ID
		 *	库存表ID
		 *	物品单据表ID
		 *	物品ID
		 *	状态
		 * 	数量   数量为number类型
		 * 	品名编号
	 */
	void establishGoodsNumber(Object[] obj);
}
