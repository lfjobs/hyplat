package com.tiantai.wfj.service;

import hy.ea.bo.company.ContactCompany;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface RestaurantService {
	//初始化行业数据，比如餐饮
	public String initIndustryData(String companyID);

	public ContactCompany scanCodePay(String ppid);

	/**
	 *
	 * 扫码菜单加入购物车
	 * @param sccid
	 * @param ppid
	 * @return
	 */
	public String  joinShopCart(String sccid,String ppid);


	/**
	 *
	 * 查询购物车
	 * @return
	 */

	public Map<String,Object>  queryShopCart(String companyID,String staffID);


	/**
	 * 扫码加入公司
	 */
    public void addComapnyKuaiJie(String sccid,String companyId);

	/**
	 * 查询餐饮公司的分类
	 * @param companyId
	 * @return
	 */
	public List<BaseBean> getALLCate(String companyId);

	/**
	 *
	 * 根据分类查询商品
	 * @param companyId
	 * @param cateID
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageForm getProductByCate(String companyId, String staffid,String posNum,String cateID, int pageNumber, int pageSize);

	/**
	 *
	 * 获取公司购物车数量
	 * @param posNum
	 * @param staffId
	 * @param companyId
	 * @return
	 */
	public int getCompanyCartNum(String posNum,String staffId,String companyId);
	
	/**
     * 新闻
     * @return
     */
    public  List<BaseBean> getNewsList();
}
