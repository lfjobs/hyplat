package mobile.tiantai.android.service.storeProduction.wholesaleMall;


import hy.ea.bo.CAccount;
import hy.ea.bo.human.StaffAddress;

import hy.plat.bo.BaseBean;
import mobile.tiantai.android.bean.wholesaleMall.WholesaleMallBean;
import mobile.tiantai.android.bo.scMobile.PfscShoppingCart;

import java.util.List;
import java.util.Map;

/**
 * 批发商城service
 * Created by lyc on 2018-10-08.
 */
public interface WholesaleMallService {

    /**
     * 异步获取一级商品分类
     *
     * @param ccompanyID 公司id
     * @param codePID    物类id[该超市所有商品分类的父id]
     * @return 拼接map字符串
     * @throws Exception
     */
    String ajaxGetOneGoodsClassify(String ccompanyID, String codePID) throws Exception;
    
    
    
    /**
     * 市场平台
     * 
     * @param ccompanyID
     * @param codePID
     * @return
     * @throws Exception
     */
    public String ajaxGetOneGoodsClassifyPlat(String ccompanyID, String codePID) throws Exception;

    /**
     * 异步获取二级商品分类
     *
     * @param ccompanyID 公司id
     * @param codePID    物类id[该超市所有商品分类的父id]
     * @return 拼接map字符串
     * @throws Exception
     */
    String ajaxGetTwoGoodsClassify(String ccompanyID, String codePID,String ccomIDPlatform) throws Exception;

    /**
     * 异步获取超市有关商品
     *
     * @param ccompanyID   公司id
     * @param codePID      物类id[该超市所有商品分类的父id]
     * @param search       查询条件
     * @param industryType 行业类别
     * @return 拼接map字符串
     * @throws Exception
     */
    Map<String, Object> ajaxGetSupermarketGoodsList(String ccompanyID, String codePID, String search, String industryType,String ccomIDPlatform,String staffId,String companyId) throws Exception;

    /**
     * 查询出三级商品后更新购物车内信息getShopCartInfor
     *
     * @param mallBean     三级商品相关信息
     * @param shoppingCart 购物车信息
     * @param companyId    公司id
     * @param account      账号
     * @throws Exception
     */
    void getUpShopCartInfor(WholesaleMallBean mallBean, PfscShoppingCart shoppingCart, String companyId, CAccount account) throws Exception;

    /**
     * 异步获取货物规格及颜色
     *
     * @param goodsId 货物id
     * @return
     * @throws Exception
     */
    String ajaxGetGGFlList(String goodsId) throws Exception;

    /**
     * 
     * 根据产品ID获取产品详细信息
     * @param pfscShoppingCart
     * @return
     */
    public  PfscShoppingCart getProCartInfo(PfscShoppingCart pfscShoppingCart);
    /**
     * 异步保存购物车
     *
     * @param pfscShoppingCart 购物车form
     * @return
     * @throws Exception
     */
    String ajaxAddShoppingCart(PfscShoppingCart pfscShoppingCart,String companyId,String staffId) throws Exception;

    /**
     * 获取购物车商品数量金额种类
     *
     * @param map
     * @param pscId 购物车id
     * @return
     * @throws Exception
     */
    String getShopCartInfor(Map<String, Object> map, String pscId,String staffId,String companyId) throws Exception;

    /**
     * 购物车数量减一
     *
     * @param shoppingCartFrom 购物车form
     * @return
     * @throws Exception
     */
    void jianShoppingCart(PfscShoppingCart shoppingCartFrom) throws Exception;

    /**
     * 单独购物车数量为0删除购物车货物
     *
     * @param shoppingCartFrom 购物车form
     * @throws Exception
     */
    void delShoppingCart(PfscShoppingCart shoppingCartFrom) throws Exception;

    /**
     * 跳转购物车列表页
     *
     * @return
     * @throws Exception
     */
    Map<String, Object> shoppingCartList() throws Exception;

    /**
     * 异步修改购物车数量
     *
     * @param shoppingCartParmStr 购物车修改数据
     * @throws Exception
     */
    void ajaxChangeCartNum(String shoppingCartParmStr) throws Exception;

    /**
     * 批量删除购物车数据
     *
     * @param pscId     需删除购物车ids
     * @param companyId 当前登录人所在公司id
     * @param staffId   当前登录人id
     * @throws Exception
     */
    void delShoppingCarts(String pscId, String companyId, String staffId) throws Exception;

    /**
     * 跳转结算页面
     *
     * @param staffAddress        购买地址
     * @param shoppingCartParmStr 选中结算购物车ids
     * @return
     * @throws Exception
     */
    Map<String, Object> toSettlement(StaffAddress staffAddress, String shoppingCartParmStr) throws Exception;

    /**
     * 拼接查询所有商家所需参数
     *
     * @param parms        参数集合
     * @param longitude    经度
     * @param latitude     纬度
     * @param industryType 行业类别//行业类别id
     * @param search       模糊查询条件
     * @return
     * @throws Exception
     */
    Map<String, Object> toBussList(List<Object> parms, String longitude, String latitude, String industryType, String search) throws Exception;

    /**
     * 跳转大屏购物车列表页
     *
     * @return
     * @throws Exception
     */
    List<BaseBean> dpShoppingCartList() throws Exception;
    
    
    /**
     * 
     * 获取批发商城广告图
     * @param ccompanyID
     * @param ccomIDPlatform
     * @return
     */
    public Map<String,Object>  getAdvertPic(String ccompanyID,String ccomIDPlatform);
}
