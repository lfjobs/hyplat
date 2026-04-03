package hy.ea.marketing.service;


import hy.plat.bo.BaseBean;

import java.util.List;
import java.util.Map;


public interface SupermarketSerivce
{
    /**
     *
     * 社区加入购物车
     */
    public String sqJoinCart(String pid,String stardard,String itemNum,String sendNum,String posNum,String barCode,String showNum,String sgrId,String relateID);


    /**
     *
     * 社区减少购物车
     */
    public String sqReduceCart(String pid,String stardard,String itemNum,String sendNum,String posNum,String barCode,String showNum);


    /**
     *
     * 社区购物车商品数量
     * @param posNum
     * @return
     */
    public Object sqTotalNumCart(String posNum,String ccompanyID);

    /**
     *
     * 社区购物车商品数量
     * @param posNum
     * @return
     */
    public Object sqPidTotalNumCart(String posNum,String ppid);

    /**
     *
     * 获取购物车
     * @param posNum 社区机器编码
     * @param lxType 查询类型，0：其他入口进入1：批发商城进入
     * @return
     */
    public List<BaseBean> getSqCartList(String posNum,String ccompanyID,int lxType,String fhform);

    /**
     *
     * 删除购物车商品
     * @param pid
     * @param stardard
     * @param posNum
     * @param barCode
     */
    public void deleteCartGoods(String pid, String stardard,String posNum,String barCode);

    /**
     *
     * 清空公司购物车
     * @param posNum 社区机器编码
     * @return
     */
      public void clearSqCart(String posNum);

    /**
     * 存储超市入口公司
     * @param posNum
     * @return
     */
    public void saveAccessCComID(String posNum,String ccompanyID);

    /**
     *
     * 根据机器编号获取入口公司用于返回页面
     * @param posNum
     * @return
     */
      public Map<String,Object> getAccessCompany(String posNum);

    /**
     * 返回默认网址
     * @param posNum
     * @return
     */
      public String getAccessUrl(String posNum);

    /**
     *
     * 根据机器编号是否是终端机
     * @param posNum
     * @return
     */
    public  Map<String, Object> isExitPosNum(String posNum);
    /**
     *
     * 选择促销品存储
     */
    public  void joinCxp(String ptppid,String ptstandard,String ppid,String posNum,String cartid);



}
