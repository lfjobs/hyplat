package hy.ea.finance.service;

import com.wechat.bo.WxUserInfo;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.PayBackupBill;
import hy.ea.bo.office.Cater;
import hy.plat.bo.BaseBean;
import org.apache.commons.collections.map.HashedMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AssiCartService {




	/**
	 *   根据sccid获取人的公司
	 * @param sccid
	 * @return
	 */
	public List<BaseBean> getListCompanyBySccid(String  sccid);


	public List<String> getCompanyIDBySccid(String  sccid);

	/**
	 *
	 * 购物车列表
	 * @return
	 */
	public List<Object>  getCartList(String staffId,String companyID);

	/***
	 * 计算总金额
	 * @param list
	 * @return
	 */
	public float getTotalMoney(List<Object>  list);

	/**
	 *
	 * 获取待结算订单明细
	 * @param coID
	 * @return
	 */
	public List<Object>  getClientGoods(String coID);

	/**
	 *
	 * 根据公司ID查询往来单位
	 * @param companyId
	 * @return
	 */
	public ContactCompany getContactCompany(String companyId);

	/**
	 *
	 * 根据支付凭证号查询回调需要的参数
	 * @param journalNum
	 * @return
	 */
	public PayBackupBill  getPayBakupByJum(String journalNum);



	public Map<String,Object> getOrderDetail(String coID);


	public void  genClientOrder(String staffid, String companyId, Cater cater, String codeID, String remark,String eatType);

	/**
	 *
	 * 查询餐桌
	 * @param companyID
	 * @return
	 */
	public List<BaseBean> getCateList(String companyID);


	public void genFiniClientOrder(String cashierBillsID,String coID);




	}
