package hy.ea.finance.dao;

import java.util.List;

public interface transferDao {
	
	/**
	 * 发货   一键发货
	 * @param com_id 供应商id
	 * @param cashi_id 根据ID单条发货      如果批量发货  则为null
	 * @param fh_staff_id 发货人id
	 */
	List<String> onkeyfh(String com_id,String fh_staff_id,String cashi_id);

	/**
	 * 分配金币
	 * @param com_id 供应商公司id
	 * @param cashid 订单id
	 * @param staff_id 操作人id
	 * @param money 供应商成本(金币)
	 * @return
	 */
	String Distribution(String com_id,String cashid,String staff_id,String money) throws Exception;
	
	/**
	 * 分配给供应商成本
	 * @param com_id 公司id
	 * @param cashid 订单id
	 * @param staff_id 操作人id
	 * @param money 供应商成本金币个数
	 */
	String cost(String com_id,String cashid,String staff_id,String money);
	
	/**
	 * 修改订单状态
	 * @param cashid 订单id
	 * @param status 要改成的状态
	 */
	String getCoasUpdate(String cashid,String status);
	
	
	/**
	 * 虚拟发货自动收货
	 * @param com_id 公司id
	 */
	void virtual(String cashid,String staffid);
	
	/**
	 * 用户确认收货
	 * @param cashierBillsID 订单id
	 */
	void recognitionHarvest(String cashierBillsID);
	/**
	 * 金币充值
	 * @param comid 公司id
	 * @param jum 订单编号
	 * @param staffid 付款人id
	 * @param money 购买金币金额
	 * @param appstyle 支付方式
	 * @param trade_no 第三方交易号
	 */
	void buyJinbi(String comid,String jum,String staffid,String sccid,String money,String appstyle,String trade_no);
}
