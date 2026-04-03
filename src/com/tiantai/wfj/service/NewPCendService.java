package com.tiantai.wfj.service;

import java.util.List;
import java.util.Map;

import com.tiantai.wfj.bo.TEshopCusCom;

import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

public interface NewPCendService {
	/**
	 * 首页查询联营平台
	 * @param pageNumber:第几页,pageSize:一页显示多少条,standard:判断条件
	 * @return 
	 */
	PageForm queryPlatform(int pageNumber, int pageSize, String standard);
	/**
	 * 退出登录
	 * @param 
	 * @return 
	 */
	void quitSession();
	/**
	 * 查询是否登陆
	 * @param 
	 * @return 
	 */
	TEshopCusCom queryUser();
	
	/**
	 * 查询所有资讯
	 * @param pageNumber :第几页,pageSize:一页显示多少条,informationJudge:查询不同类型的资讯所需条件
	 * @return
	 */
	Map<String, Object> ajaxInformation(int pageNumber, int pageSize, String informationJudge);
	
	
	/**
	 * 查询商品详情
	 * @param ppID:商品id
	 * @return 
	 */
	Map<String, Object> goodsDetails(String ppID);
	/**
	 * 热搜推荐
	 * @param pageNumber:第几页,pageSize:一页显示多少条,ppID:商品id
	 * @return 
	 */
	PageForm ajaxRecommend(int pageNumber, int pageSize, String ppID);
	/**
	 * 商品评价
	 * @param pageNumber:第几页,pageSize:一页显示多少条,ppID:商品id
	 * @return 
	 */
	PageForm ajaxProductEvaluation(int pageNumber, int pageSize, String ppID);
	/**
	 * 商品详情
	 * @param ppID :商品id
	 * @return 
	 */
	List<String> ajaxProductParticulars(String ppID);
	/**
	 * PC端查询用户购物车的商品数量
	 * @param staffId : 用户Id
	 * @return
	 */
	Integer ajaxShoppingCartCount(String staffId);
	/**
	 * PC端商品加入购物车
	 * @param staffId : 用户Id
	 * @param ppID : 产品ID
	 * @param ptppid : 促销品ID
	 * @param stardard : 产品规格
	 * @param ptStandard : 促销品规格
	 * @param count : 购买数量
	 * @return
	 */
	void pcAddShoppingCart(String staffId,String ppID,String[] ptppid,String standard,String[] ptStandard,String count);
	/**
	 * PC端查询用户商品购物车
	 * @param staffId : 用户Id
	 * @param showParam : 搜索条件
	 * @return
	 */
	Map<String,Object> selPcShoppingCart(String staffId,String showParam);
	/**
	 * PC端删除购物车商品
	 * @param staffId : 用户Id
	 * @param cartId : 购物车Id
	 * @return
	 */
	void ajaxDelShoppingCart(String staffId,String cartId);
	/**
	 * PC端购物车商品结算
	 * @param cartIds : 需要结算的购物车Id
	 * @param counts : 需要结算的购买数量
	 * @return
	 */
	Map<String,Object> payShoppingCart(String[] cartIds,String[] counts);
	/**
	 * PC端商品立即购买
	 * @param ppID : 产品ID
	 * @param count : 购买数量
	 * @param ptppid : 促销品ID
	 * @param ptStandard : 促销品样式
	 * @return
	 */
	Map<String,Object>  pcGoodsPayNow(String ppID,String count,String[] ptppid,String[] ptStandard);
	/**
	 * PC端查询用户收货地址
	 * @param staffId : 用户Id
	 * @return
	 */
	Map<String,Object> ajaxPcStaffAddress(String staffId);
	/**
	 * PC端查询用户收货地址个数
	 * @param staffId : 用户Id
	 * @return
	 */
	Integer selStaffAddressCount(String staffId);
	/**
	 * 
	 * PC端修改用户默认收货地址
	 * @param staffId : 用户Id
	 * @param addressID : 收货地址的ID
	 * @return
	 */
	void ajaxChangeDefaultAddress(String staffId,String addressID);
	/**
	 * 
	 * PC端回显收货地址
	 * @param staffId : 用户Id
	 * @param addressID : 收货地址的ID
	 * @return
	 */
	Map<String, Object> selShowStaffAddress(String staffId,String addressID);
	/**
	 * PC端新增或修改用户收货地址
	 * @param account : 用户帐号
	 * @param staffAddress : 用户地址
	 * @return
	 */
	void ajaxAddStaffAddress(String account,StaffAddress staffAddress);
	/**
	 * PC端删除用户收货地址
	 * @param staffId : 用户Id,
	 * @param addressID : 收货地址的ID
	 * @return
	 */
	void ajaxDeleteStaffAddress(String staffId,String addressID);
	/**
	 * PC端查询一级地址
	 * @param districtID : 地域ID
	 * @return
	 */
	Map<String,Object> selDistrictCity();
	/**
	 * PC端查询二三级地址
	 * @param districtID : 地域ID
	 * @param showParam : 是否展示
	 * @return
	 */
	Map<String,Object> selDistrictByID(String districtID,String showParam);
	/**
	 * PC端购物车商城下订单(多个产品下订单)
	 * @param staffID : 用户Id
	 * @param account : 用户账号
	 * @param companyIds : 需要结算的商品所属公司Id
	 * @param cartIds : 需要结算的购物车Id
	 * @param counts : 需要结算的商品数量
	 * @param addressID : 收货地址ID
	 * @param companyNames : 驾校产品所属公司
	 * @param accountNames : 学员信息中所有的学员姓名
	 * @param references : 学员信息中所有的学员联系方式
	 * @param staffIdentityCards : 学员信息中所有的学员身份证号
	 * @param staffAddresses : 学员信息中所有的学员住址
	 * @param messageMap : 异步返回的参数集合
	 * @return
	 */
	void ajaxMakeShoppingCartPayBills(String staffID,String account,String[] companyIds,String[] cartIds,String[] counts,String addressID,
		String[] companyNames,String[] accountNames,String[] references,String[] staffIdentityCards,String[] staffAddresses,Map<String,Object> messageMap);
	/**
	 * PC端用户商城下订单(结算)
	 * @param staffID : 用户Id
	 * @param account : 用户账号
	 * @param ppID : 产品ID
	 * @param totalMoney : 购买的产品总价
	 * @param count : 购买的产品数量
	 * @param standard : 购买的产品规格
	 * @param ptppid : 促销品(赠品)Id
	 * @param ptStandard : 促销品(赠品)规格
	 * @param addressID : 收货地址ID
	 * @param staffMessage : 学员信息
	 * @param messageMap : 异步返回的参数集合
	 * @return
	 */
	void ajaxMakePayBills(String staffID,String account,String ppID,String totalMoney,String count,String standard,
		 String[] ptppid,String[] ptStandard,String addressID,Staff staffMessage,Map<String,Object> messageMap);
	/**
	 * PC端查询用户下订单的详情
	 * @param payJournalNum : 支付订单编号
	 * @return
	 */
	Map<String,Object> selBillsDetails(String payJournalNum);
	/**
	 * PC端验证支付订单
	 * @param payJournalNum : 支付订单编号
	 * @param total_amount : 支付金额
	 * @return
	 */
	Map<String,Object> ajaxValidatePayBills(String payJournalNum,String total_amount);
	/**
	 * PC端验证是否生成收款单
	 * @param payJournalNum : 支付订单编号
	 * @return
	 */
	boolean ajaxValidateRelatedBill(String payJournalNum);
	Map<String, Object> pcShoppingCart();
	List<BaseBean> ajaxRim();
	PageForm international(int pageNumber, int pageSize, String ppID);
	PageForm ajaxTheTenderList(String search, ProductPackaging ppk,
			String temporary, String hot, PageForm pf);
	Map<String, Object> pcTheTenderDetails(String ppID, String cashierBillsID,
			String goodsID);
	PageForm ajaxRecruitment(int pageNumber, int pageSize);
	/**
	 * PC端查询招聘人才
	 * @param pageNumber : 第几页
	 * @param pageSize : 一页显示多少条
	 * @return 
	 */
	PageForm ajaxPCRecruitment(int pageNumber, int pageSize,String companyID,String staffid);
	/**
	 * PC端查询热门职位
	 * @param pageNumber : 第几页
	 * @param pageSize : 一页显示多少条
	 * @param companyID
	 * @return
	 */
	PageForm ajaxHotPosition(int pageNumber, int pageSize, String companyID);
	/**
	 * PC端招聘职位详情
	 * @param riId职位ID
	 * @return 
	 */
	Map<String,Object> pcRecruitmentDetails(String riId);
	/**
	 * PC端热门招聘职位详情
	 * @param riId职位ID
	 * @return
	 */
	Map<String,Object> pcHotRecruitmentDetails(String staffID,String riId);
	/**
	 * PC端查询一级职位分类
	 * @return 
	 */
	Map<String,Object> ajaxCodeValueFirst();
	/**
	 * PC端查询二级、三级职位分类
	 * @param codeID : 一级职位分类的ID
	 * @return 
	 */
	Map<String,Object> ajaxCodeValue(String codeID);
	/**
	 * PC端职位搜索
	 * @param pageNumber : 第几页
	 * @param pageSize : 一页显示多少条
	 * @param positionName : 职位名称
	 * @param codeID : 一级职位分类的ID
	 * @param codePID : 三级职位分类的ID
	 * @param workCity : 工作地点
	 * @param workPlace : 具体工作地址
	 * @return 
	 */
	PageForm ajaxSelPosition(int pageNumber, int pageSize,
			String jobTitle,String codeID,String codePID,Boolean codePID2,
			String workCity,String workPlace);
	PageForm proAgentList(int i, int j, ProductPackaging ppk,String hot);
	List<BaseBean> theQueryImage(String goodsid);
	/**
	 * pc端城市联营平台
	 * @return
	 */
    List<BaseBean> UrbanEconomy();
	/**
	 * pc端城市联营平台类型
	 * @param hot : 类型
	 * @return
	 */
    List<BaseBean> platformType(String hot);
    /**
     * pc端城市联营平台类型
     * @param hot : 类型
     * @param goodsName :名称
     * @return
     */
    PageForm regionalTypes(int pageNumber, int pageSize, String hot, String goodsName);
	/**
	 * 查询收货地址
	 * @return
	 * @param cuscom
	 */
    StaffAddress shippingAddress(TEshopCusCom cuscom);
	/**
	 * pc端代理资格
	 * @param hot : 类型
	 * @param goodsName:名称
	 * @return
	 */
    PageForm qualification(int pageNumber, int pageSize, String hot, String goodsName);
	/**
	 * pc端代理价格
	 * @param ppID : 产品id
	 * @param companyID:公司ID
	 * @return
	 */
	Object agencyPrice(String ppID, String companyID);
	/**
	 * pc端个人/公司加入平台
	 * @param ccompanyId:往来单位id
	 * @return
	 */
	List<BaseBean> joinPlatform(String ccompanyId,String typeNews);
	/**
	 * pc端查询促销品
	 * @param ppID:产品id
	 * @param companyID:公司id
	 * @return
	 */
    Object promotionProducts(String ppID, String companyID);
	/**
	 * pc端查询公司轮播图
	 * @param ccompanyId:往来单位id
	 * @return
	 */
    List<BaseBean> shufflingFigure(String ccompanyId);


	Map<String, Object> companyNews(int pageNumber, int pageSize,String ccompanyId);
}
