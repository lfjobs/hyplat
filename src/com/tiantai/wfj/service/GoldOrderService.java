package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.wechat.bo.sft.SubOrders;
import com.wechatpay.bo.WxPayDto;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.Enroll;
import hy.ea.bo.finance.BenDis.Rewarddetail;
import hy.ea.bo.finance.BenDis.StatusEntity;
import hy.ea.bo.finance.BenDis.transferPay;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.TableRalate;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GoldOrderService {

    /**
     * 生成收款单
     *
     * @param c     支付宝或者第三方交易号
     * @param ddid  单据表订单号
     * @param morrt 交易金额
     * @return
     */
    Boolean generateBill(String c, String ddid, String morrt, String wfStatus4, String wfStatus1);

    /**
     * 会员升级
     *
     * @param tsc         购买产品的会员
     * @param BoxStandard 购买会员级别
     * @return
     */
    List<BaseBean> upgradeServece(TEshopCusCom tsc, String BoxStandard);

    /**
     * 新注册用户确认抢单成功人
     *
     * @param e 报名信息
     * @return
     */
    Boolean addMarKeting(Enroll e);

    /**
     * 促销品下订单
     *
     * @param ppid       主产品id
     * @param ptppid     促销品id
     * @param cb         主产品订单
     * @param companyid  促销品公司id
     * @param ptstandard 促销品规格
     */
    List<BaseBean> promotionalOrderServece(String ppid, String ptppid, CashierBills cb, String companyid, String ptstandard);

    /**
     * 现金入库
     */
    List<BaseBean> xjrkService(String journalNum);

    /**
     * 分购买生成订单和出入库单
     *
     * @param c         支付宝或者第三方交易号
     * @param ddid      单据表订单号
     * @param morrt     交易金币
     * @param wfStatus4 支付方式
     * @return
     */
    Boolean generateSpoints(String c, String ddid, String morrt, String wfStatus4);

    /**
     *
     * 混合支付  金币+积分 够扣
     * @param ddid
     * @param morrt
     * @param wfStatus4
     * @return
     */
    Boolean coinAndScorePay(String tradeNo,String ddid, String morrt, String wfStatus4,String sccId,String hgcode);


    /**
     *
     * 混合支付  金币+积分 不够扣，金币或者积分不够扣
     * @param ddid
     * @param morrt
     * @param wfStatus4
     * @return
     */
    String mixPay(String ddid, String morrt, String wfStatus4,String sccId,String hgcode);

    /**
     * 优先扣积分再扣金币，不够扣微信支付宝或者现金支付。
     *
     * @param ddid      单据表订单号
     * @param morrt     交易金币
     * @return
     */

    String  checkPay(String ddid, String morrt,String sccId);
    /**
     * 业务佣金数据备份
     *
     * @param tsc   下订单用户
     * @param dd    订单
     * @param bksum 订单产品总业务佣金数
     */
    List<BaseBean> getddService(TEshopCusCom tsc, CashierBills dd, BigDecimal bksum);

    /**
     * 代理佣金数据备份
     *
     * @param goodsbillid 订单产品id
     * @param ppid        产品ppid
     * @param cashid      订单的id编号
     * @param cashjum     订单编号
     * @param sccid       购买产品人员帐号id
     * @param tradeName   产品类型
     * @param companyID   供应商公司id
     * @param quantity    产品数量
     * @return
     */
    List<BaseBean> dailiFen(String goodsbillid, String ppid, String cashid, String cashjum, String sccid, String tradeName, String companyID, String quantity, String priceType, String activityid, String proid) throws Exception;

    /**
     * 计时收费
     *
     * @param @paySccid 交钱人的paySccid
     * @param @colSccid 收钱的colSccid
     * @param @money    需要支付的金额
     * @return 01扣金币成功，10扣积分成功，11扣钱不成功（出现异常），00金币和积分都不够
     */
    String timeCharge(String paySccid, String colSccid, BigDecimal money, String journalNum);

    /**
     * 抽奖收费
     *
     * @param lotSccid 抽奖人的id
     * @param colSccid 被抽奖Id
     * @param money    每次抽奖支付的积分数
     * @return 10抽奖成功  11发生异常
     */
    String lotteryCharge(String lotSccid, String colSccid, BigDecimal money);


    /**
     * 短信扣费
     *
     * @param lotSccid
     * @param colSccid
     * @param money
     * @return
     */
    String messageCharge(String lotSccid, String colSccid, BigDecimal money);


    /**
     * 二维码收款生成订单
     *
     * @param ppid
     * @param morre
     * @param sccid
     * @param journalNum
     * @return
     */
    String generateOrderSheet(String ppid, String morre, String sccid, String journalNum, String waiterID, String wfStatus4) throws Exception;

    /**
     * 修改订单状态
     *
     * @return
     */
    void updateFkState(String cashierBillsID);

    /**
     * 微信支付判断是否回调过
     *
     * @param journalNum
     * @return
     */
    boolean isCalledPay(String journalNum);

    /**
     * 扫码助手生成订单
     *
     * @param waiterID
     * @param companyID
     * @param morre
     * @param sccid
     * @param journalNum
     * @return
     */
    String generateMuliProOrder(String waiterID, String companyID, String morre, String sccid, String journalNum, String coID);

    /**
     * 自助收银生成订单
     *
     * @param morre
     * @param sccid
     * @param journalNum
     * @return
     */
    String generateSelfPayOrder(String morre, String sccid, String journalNum, String wfStatus4, String addressID) throws Exception;

    /**
     * 打赏生成订单
     *
     * @param rd
     * @return
     * @throws Exception
     */
    String rewardOrder(Rewarddetail rd) throws Exception;

    /**
     * 修改打赏状态
     *
     * @param rd
     * @param wfStatus4
     * @param trade_no
     * @throws Exception
     */
    void addReward(Rewarddetail rd, String wfStatus4, String trade_no) throws Exception;

    /**
     * 查询打赏列表
     *
     * @param ppid 打赏新闻id
     * @throws Exception
     */
    List<Object> getReward(String ppid) throws Exception;


    String genMealNum(String ddid);


    String searchMealNum(String companyID, CashierBills cc);

    //收银自动发货
    String autoFh(String cashierBillsID);

    /**
     * 变价信息处理
     *
     * @param cashid 订单id
     */
    void getPrice(String cashid) throws Exception;

    /**
     * 收藏商品
     *
     * @param ppid
     * @param pricetype
     * @param staffID
     * @return
     */
    String collectGoods(String ppid, String pricetype, String staffID);

    /**
     * 判断是否已收藏
     *
     * @param ppid
     * @param pricetype
     * @param staffID
     * @return
     */
    String isCollect(String ppid, String pricetype, String staffID);

    /**
     * 获取收藏的商品
     *
     * @param pageNumer
     * @param pageSize
     * @param paramter
     * @param staffID
     * @return
     */
    PageForm getCollectGoodsList(int pageNumer, int pageSize, String paramter, String staffID);


    /**
     * 获取收藏的店铺
     *
     * @param pageNumer
     * @param pageSize
     * @param parameter
     * @param sccid
     * @return
     */
    PageForm getCollectShopsList(int pageNumer, int pageSize, String parameter, String sccid);


    /**
     * 批量取消商品收藏
     *
     * @param gcIds
     * @return
     */
    void batchCCGoods(String[] gcIds);


    /**
     * 批量取消店铺收藏
     *
     * @param jfIDs
     * @return
     */
    void batchCCShops(String[] jfIDs);

    /**
     * 保存转他人支付信息
     *
     * @param t 他人支付信息
     */
    void transferPayOrder(transferPay t) throws Exception;

    /**
     * 保存单据状态表信息
     *
     * @param cashid     订单id
     * @param journalNum 订单编号
     * @param paystatus  支付状态
     * @param supstatus  购买流程跟踪状态
     */
    StatusEntity SaveStatus(String cashid, String journalNum, String paystatus, String supstatus) throws Exception;

    /**
     * 根据订单号获取注册的公司
     *
     * @param journalNum
     * @return
     */
    String getComBz(String journalNum);

    /**
     * 购买不免费的公司会员临时存储公司信息现在用在周边经济入驻
     *
     * @param company
     * @param cdl
     * @return
     */
    void tempCompany(Company company, CDetail cdl, String journalNum);


    /**
     * 微分账号绑定手机号或者微信账号和手机号合并
     *
     * @param scc
     * @param cus
     * @param account
     * @return
     */
    void bindAccount(TEshopCusCom scc, TEshopCustomer cus, String account);

    /**
     * 根据支付订单号构建子单信息
     *
     * @param
     * @return
     */
    List<SubOrders> getOrdersList(WxPayDto payDto);

    /**
     * 提交保存第三方交易号
     *
     * @param sublist
     */
    void setPayCashiber(Map<String, String> sublist, String payJournalNum);

    /**
     * 添加颌单信息
     *
     * @param journalNum
     */
    void addHdInfo(String journalNum);
    /**
     *
     *
     *
     * @return
     */
    public TableRalate createTable(String companyID, String type);



           /*
             *    创建表 用于处理数据

             * @return
             */
    public TableRalate  createTableDealData(String companyID,String type,String year);

    /**
     *
     * 拷贝记录
     * @param companyID
     * @param type
     * @param idValue
     */
    public void copyTable(String source_table,String companyID,String type,String idName,String idValue);


    /**
     *
     * 根据订单号复制订单和收款单到新表
     * @param journalNum
     * @return
     */
    public void copyCash(String journalNum,String bills);


    /**
     *
     * 获取表
     *
     * @return
     */
    public TableRalate  getTableName(String companyID,String type,String year);


    public void  copyData(String companyID,String year,String nyr);

    }
