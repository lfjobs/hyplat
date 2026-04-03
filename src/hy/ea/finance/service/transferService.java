package hy.ea.finance.service;

import com.wechatpay.bo.WxPayDto;
import hy.ea.bo.finance.BenDis.DtMemberBackup;
import hy.ea.bo.finance.HdBackupBillHistory;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;

public interface transferService {

	/**
	 * 发货   一键发货
	 *
	 * @param com_id      供应商id
	 * @param cashi_id    根据ID单条发货      如果批量发货  则为null
	 * @param fh_staff_id 发货人id
	 */
	List<String> onkeyfh(String com_id, String fh_staff_id, String cashi_id);

	/**
	 * 分配金币
	 *
	 * @param com_id   供应商公司id
	 * @param cashid   订单id
	 * @param staff_id 操作人id
	 * @param money    供应商成本(金币)
	 * @return
	 */
	String Distribution(String cashid, String staff_id);

	/**
	 * 修改订单状态
	 *
	 * @param cashid 订单id
	 * @param status 要改成的状态
	 */
	void getCoasUpdate(String cashid, String status);

	/**
	 * 虚拟发货自动收货
	 *
	 * @param com_id 公司id
	 */
	void virtual(String cashid);

	/**
	 * 用户确认收货
	 *
	 * @param cashierBillsID 订单id
	 * @return
	 */
	String recognitionHarvest(String cashierBillsID);

	/**
	 * 购买商城会员发短信
	 */
	void pushMessage(DtMemberBackup backup);

	/**
	 * 金币充值
	 *
	 * @param comid    公司id
	 * @param jum      订单编号
	 * @param staffid  付款人id
	 * @param money    购买金币金额
	 * @param appstyle 支付方式
	 * @param trade_no 第三方交易号
	 */
	void buyJinbi(String comid, String jum, String staffid, String sccid, String money, String appstyle, String trade_no);


	//物流信息

//	//单号识别      expNo(运单号)
//	public String expno(String expNo) throws Exception;

	//物流及时查询
//	public String wuLiu(String exCode,String expNo) throws Exception;
	public String wuLiu(String expNo) throws Exception;

	String confirmReceipt(String cashierBillsID);

	/**
	 * 保存拣货出库单
	 *
	 * @param cashid 订单号
	 * @throws Exception
	 */
	void saveSorting(String cashid) throws Exception;

	/**
	 * 拣货出库逻辑处理
	 *
	 * @return
	 * @throws Exception
	 */
	String DeliveryLogicalProcessing(String content, String orderid, String staffid) throws Exception;

	/**
	 * 发货逻辑处理
	 *
	 * @return
	 * @throws Exception
	 */
	String SendLogicalProcessing(String sendid, String staffid) throws Exception;

	/**
	 * 送货逻辑处理
	 *
	 * @return
	 * @throws Exception
	 */
	String TransportLogicalProcessing(String transportid, String Waybillno, String ExCode, String staffid) throws Exception;

	/**
	 * 保存欠款单
	 *
	 * @param cashid
	 * @return
	 */
	String addOverdraft(String cashid, String raddressId, String flag) throws Exception;

	/**
	 * 保存地址
	 *
	 * @param cashid     单据id
	 * @param raddressId 地址id
	 * @throws Exception
	 */
	void addAddress(String cashid, String raddressId) throws Exception;


	/**
	 * 退款补充数据
	 *
	 * @param wxPayDto
	 * @return
	 */
	List<WxPayDto> getRefundInfo(WxPayDto wxPayDto);



	public void addWqrshOrder();


	public  void addWxAccountDetail(HdBackupBillHistory hdBackupBill, List<BaseBean> beans, List<String> mainlist, List<String> monthlist, List<String> jourlist);

	public  WxPayDto  getProfitInfo(String journalNum,String companyID);
	/**
	 *
	 * 修改新表状态
	 * @param companyID
	 * @param fkStatus
	 * @param fkdate
	 * @param cashierBillsID
	 */
	public void updateCashState(String companyID, String fkStatus, Date fkdate, String cashierBillsID);

	}