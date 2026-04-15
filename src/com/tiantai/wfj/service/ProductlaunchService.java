package com.tiantai.wfj.service;



import java.util.Date;
import java.util.List;
import java.util.Map;
import com.tiantai.wfj.bo.Promotion;
import com.tiantai.wfj.bo.ScaleWeight;
import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;


public interface ProductlaunchService {

	/**
	 * @Title: 查询
	 * @Description: 查询产品及其下的促销品
	 * @param ppid:产品id
	 * @return 返回的集合
	 */
	Map<String, Object> productInquiry(String ppID);


	/**
	 * @param ptppId
	 * @Title: 模糊查询加分页
	 * @Description: 查询产品及其下的促销品
	 * @param pageNumber:当前页,pageSize:每页显示条数,goodsName:查询条件
	 * @return 返回的集合
	 */
	PageForm allGiftsProducts(int pageNumber, int pageSize, String goodsName, String ptppId);

	/**
	 * @Title: 添加促销品
	 * @Description: 为产品添加促销品套餐
	 * @param promotions:促销品数据
	 * @return 无返回值
	 */
	void save(Promotion promotions);

	/**
	 * @Title: 添加产品分类
	 * @param companyid:公司id
	 * @return 无返回值
	 */
	void addProductType(String companyid);

	/**
	 * @Title: 查询
	 * @Description: 查询产品
	 * @param ppId:产品id
	 * @return 返回的集合
	 */
	List<BaseBean> queryPromotionProduct(String ppId);


	List<Object> PromotionsDetail(String companyId,String ppId);

	String generateProductCode(String tradeNum,String parentID);

	boolean upOrdown(String ppId,String flag);

	PageForm productsPageForm(String flag,Integer pageNumber,String search,String companyId);

	public String productsManage(String user);

	List<Object> getAttr(String ppId);

	Boolean checkProName(String ppName,String companyId);

	void savePssb(ProSetup ps,List<ProSetupSub> pssList,List<BaseBean> beans);

	/**
	 * 获取货柜下的商品
	 *
	 * @param coding
	 * @param companyid
	 * @param t 1：货柜 2：秤盘
	 * @return
	 */
	JSONObject getProductToDepot(String coding, String companyid, int t);

	/**
	 * 获取秤盘产品信息
	 *
	 * @param coding
	 * @param companyid
	 * @return
	 */
	Map<String, Object> getProductTochBalance(String coding,String companyid);

	/**
	 * 获取秤盘产品信息
	 *
	 * @param proWeight
	 */
	String calculationNum(Map<String, ScaleWeight> proWeight, CAccount account);

	/**
	 * 新移动版库存分页
	 * @param pageNumber 当前页数
	 * @param search
	 * @param companyId  公司
	 * @return
	 */
	Map<String,Object> ProductManageMobilePageForm(Integer pageNumber,String pname,String depotid,String posNum, String companyId);

	/**
	 * 自动贩卖机库存调整
	 * @param account
	 * @param map
	 */
	void calculationNum(CAccount account,Map<String, Object> map,String proStr)throws Exception;

	/**
	 * 保存秤盘时时重量
	 * @param sc 秤盘编码
	 * @param cc 货柜编码
	 * @param w 重量
	 * @param t 时间
	 * @throws Exception
	 */
	void updateScaleWeight(String sc, String cc, double w, Date t) throws Exception;

	/**
	 * 获取秤盘时时重量
	 * @param sc
	 * @param cc
	 * @return ScaleWeight 秤盘重量记录表
	 */
	ScaleWeight getWeitht(String sc, String cc) throws Exception;

	/**
	 * 获取货柜上所有秤盘实时重量
	 * @param cc 货柜编号
	 * @return list 秤盘重量记录集合
	 */
	List<BaseBean> getWeithts(String cc) throws Exception;

	public String registerPShop(TEshopCusCom tcc);

	public String checkVip(String ppid);

	public PageForm getVipList(String sccId,int pageSize,int pageNumber);
	public PageForm getVipList1(int pageSize,int pageNumber,String companyID,String productName);
	public PageForm getComViplist(String sccId,int pageSize,int pageNumber,String industryId,String companyName);

	public PageForm getVipListALL(int pageSize,int pageNumber,String industryId,String goodsName);
	public PageForm getVipListCompany(int pageSize,int pageNumber,String companyID,String goodsName);

		public List<Object> getCxList(PageForm pageForm);
	public List<Object> getCxList(Map<String,List<Object>>  mainlist);
	public  Map<String,List<Object>> getproList(PageForm pageForm);
	public String addJoinFans(String companyId,String sccId);

	/**
	 *
	 * 广告
	 * @return
	 */
	public List<BaseBean> advList();

	public ContactCompany getShopInfo(String companyID);

	public Map<String,String> getJoinFans(String sccid,String companyID);
	}

