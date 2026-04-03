package hy.ea.marketing.service;


import com.tiantai.wfj.bo.*;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.PayBackupBill;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.plat.bo.BaseBean;

import java.util.List;
import java.util.Map;


public interface SuperSelfSerivce
{

    public  int  login(String companyIdentifier,CAccount account);
    /**
     *
     * 根据条码获取商品的名称以及金额
     * @param barCode
     * @param lxType 查询类型，0：其他入口进入1：批发商城进入
     * @return
     */
    public Map<String,Object> searchGoods(String comID, String barCode,int lxType);

    /**
     *
     * 生成订单号加入购物清单
     * @param selfCartmap
     * @return
     */
    public  Map<String,Object> joinSelfCart(Map<String, SelfCart> selfCartmap,String comID,String posNum,String relateID);
    /**
     * 返回订单号
     * @return
     */
    public Map<String,Object>  joinSelfCartClose(String hgcode);
    /**
     * 单产品加入超市购物车
     * @param   ppid
     * @param comID
     * @param posNum
     * @return
     */
    public Map<String,Object>  joinDanSelfCart(String ppid,String stardard,String price,String itemNum,String comID,String posNum);

    /**
     *
     * 更新数量
     * @param ppid
     * @param itemNum
     */
    public void updateItemNum(String ppid,String itemNum,String journalNum);
    /**
     * 多公司多商品加入超市购物车
     * @param posNum
     * @return
     */
    public Map<String,Object>  joinMultiSelfCart(String posNum);


    /**
     *
     * 查询购买人的积分
     * @param sccid
     * @return
     */
    public String  searchPoint(String sccid);


    /**
     *
     * 查询购买人的积分
     * @param sccid
     * @return
     */
    public String  searchJINBIN(String sccid);
    /**
     * 支付前需要的
     * @param sccid
     * @param journalNum
     */
    public  void genPayBackupBill(String sccid,String journalNum,String addressID);

    /**
     *
     * 取消订单
     * @param journalNum
     * @return
     */
    public String  cancelOrder(String journalNum);

    /**
     *
     * 查看订单详情
     * @param journalNum
     * @return
     */
    public List<BaseBean> viewOrderDetail(String journalNum);


    /**
     *
     * 判断货柜问题
     * @param journalNum
     * @return
     */
    public List<BaseBean> viewGoodsBills(String journalNum);
    /**
     *
     * 查询支付结果
     * @param journalNum
     * @return
     */
    public  boolean searchPayResult(String journalNum);

    /**
     *
     * 查询当前超市购物袋
     * @param comID
     * @param lxType 查询类型，0：其他入口进入1：批发商城进入
     * @return
     */
    public  Map<String,Object> searchShoppBag(String comID,int lxType);

    /**
     *
     *打印小票
     * @param journalNum
     * @return
     */
    public String printTicket(String journalNum,String staffID);

    /**
     *
     * 根据公司查询公司微分金账号
     * @param companyID
     * @return
     */
    public String searchSccidByCompany(String companyID);

    /**
     *
     * 根据StaffID 查用户密码
     * @param staffID
     * @return
     */
    public Map<String,Object>  searchPwByStaff(String staffID,String comID,String telphone);

    public String searchSccidByStaff(String staffID);


    public void addMoreInfo(String journalNum,String ssprice,String zlprice);

    public void saveCashMoreInfo(String journalNum,String oprID,String oprName);

    /**
     *
     * 根据公司ID取微分金账号
     * @param companyID
     * @return
     */
    public String getSccidBycomID(String companyID);

    /**
     *
     * 核对购物卡是否正确以及余额是否充足
     * @return
     */
    public   Map<String,Object> checkScard(String sccId,String totalMoney,String journalNum);

    /**
     *
     * check用户地址
     * @return
     */
    public  StaffAddress getUserAddress(String sccId);

    /**
     * 添加地址
     * @param sccId
     */
    public String addAddress(String sccId,StaffAddress staffAddress);
    /**
     *
     * 根据购物卡获取用户积分信息
     * @param scard
     * @return
     */
    public  Map<String,Object> getSccIdByscard(String scard);

    /**
     *
     * 根据订单判断是否是社区终端，目前只要是社区终端下单就不发货
     * @return
     */
    public boolean isSqPos(String journalNum);


    /**
     *   判断是显示一个数量还是2个数量
     *
     * @return
     */
    public String  findFhForm(String posNum,String ccompanyID);
    /**
     *
     * 根据支付凭证号查询购物车
     * @param journalNum
     * @return
     */
    public SelfCart getSelfCartByJum(String journalNum);




    /**
     *
     * 根据购物卡号查询账号信息
     * @param cardNum
     * @return
     */
    public TEshopCusCom getTEshopByCum(String cardNum);

    /**
     *
     * 根据购买的产品id获取产品类型
     * @param ppId
     * @return
     */
    public String getSelfByProType(String ppId);

    /**
     *
     * 添加购物车信息
     * @param cardNum
     * @return
     */
    public void addSelfCart(String posNum,String cardNum,String journalNum,String totalNum,String totalMoney,String ppId,String proName,String comID,String companyId);

    /**
     *
     * 查询会员列表
     * @return
     */
    public List<BaseBean> getMemberList();

    /**
     *
     * 根据往来公司ID获取公司表
     */
    public Company getCompanyByCComID(String ccompanyID);
    /**
     *
     * 生成备份信息
     * @param payBackupBill
     * @return
     */
    public String savePaybackUp(PayBackupBill payBackupBill);

    /**
     *
     * 获取打印小票关于公司的信息
     * @param list :List<SelfCart>
     * @return
     */
    public ContactCompany getPrintCompanyInfo( List<BaseBean> list);

    /**
     * 如果产品原本是原价，并且设置了VIP价格，会员购买后修改成VIP的价格以及类型
     */
    public void updateSelfCart(String sccid,String journalNum,String mode);


    /**
     *
     * 根据购物卡获取sccid
     * @param scard
     * @return
     */
    public String getSccidByCard(String scard);

    /**
     * 验证购物卡是否绑定微信openid
     *
     * @param scard
     * @return
     */
    public Map<String,Object> checkWxCard(String scard,String openid);


    /**
     * 验证购物卡是否绑定微信openid成功并绑定
     *
     * @return
     */
    public String bindCard(String sccId,String openid);




        /**
         *
         * 获取VIP专享价格
         * @return
         */
    public  String  getVipTmoney(String sccId,String journalNum,String totalMoney);

    /**
     * 根据账号密码或者操作员编号
     * @param telphone
     * @param companyID
     * @param psw
     * @return
     */
    public Map<String,String> getCashOperator(String telphone,String companyID,String psw);

    /**
     *
     * 根据公司ID获取往来单位
     * @param comID
     * @return
     */
    public ContactCompany getCCompanyBycomID(String comID);

    void savePrice(List<BaseBean> parem) throws Exception;

    /**
     *
     * 根据人查询扫描记录
     * @param posNum
     * @param staffID
     * @param companyID
     * @return
     */
    public List<BaseBean> getScanRecord(String posNum,String staffID,String companyID);

    /**
     * 第一次扫描记录
     * @return
     */
    public String joinScanRecord(ScanGoodsRecord scanGoods);

    /**
     *
     * 删除扫描记录
     * @return
     */
    public void   deleteScanRecord(String sgrId);

    /**
     *
     * 增加数量或者减少数量
     * @param sgrId
     * @param itemNum
     */
    public String joinOrReduceGoods(String sgrId,String itemNum,ScanGoodsRecord scanGoods);


    /**
     *
     * 记录理由
     * @param relateID
     * @param reason
     * @return
     */
    public void addReason(String relateID,String reason);

    /**
     * 验证账号是否被其他用户登录 过
     * @param accountEmail
     * @param staffID
     * @param comID
     * @param loginGuid
     * @return
     */
    public String  validateLogin(String accountEmail,String staffID,String comID,String loginGuid);



    /**
     *
     * 验证刷脸用户
     * @param openid
     * @return
     */
    public Map<String,Object> faceValiShopping(String openid);

    /**
     *
     * 得到库存数
     * @param companyID
     * @param ppid
     * @return
     */
    public String getInv(String companyID,String ppid);

    /**
     *
     * 设置预扣库存
     * @param companyID
     * @param ppid
     * @param num
     */
    public void setPreInv(String companyID,String ppid,String num);


    /**
     *
     * 获取自动开通权限
     * @param sccid
     * @return
     */
  public CusComAuth getAuthcc(String sccid);

    /**
     *
     * 开通或者不开通金币积分扣款
     * @param coinfee
     */
  public void setKt(String coinfee,String sccid);

    /**
     * 获取信息
     * @param staffID
     * @return
     */
    public Staff getInfo(String staffID);

    public Staff getInfoBySccid(String sccid);

    /**
     *
     * 获取货柜编号
     * @param companyID
     * @return
     */
    public  List<BaseBean> getHgcodeList(String companyID);

    /**
     * 获取货柜编码
     * @return
     */
    public String getHgcodeing(String posNum);

    /**
     *
     * 验证账号获取用户信息
     * @param phoneNumber
     * @return
     */
    public Staff validAccount(String phoneNumber);


    /**
     * 核对购物卡是否正确以及余额是否充足
     * @param sccId
     * @return
     */
    public Map<String, Object> checkHgcard(String sccId,String totalMoney) ;



    /**
     *
     * 根据设备序列号查询往来公司
     * @param posNum
     * @return
     */
    public ContactCompany getCCompanyByPosNum(String posNum);


    /* 根据货柜编号查询所有的秤盘编号以及所有的库存
   */
    public List<Object> getCplist(String hgcode);

    /**
     *
     * 货柜加入购物车
     * @param weights
     * @param cplist
     * @param posNum
     * @param staffID
     * @return
     */
    public String addCartHg(String weights,String cplist,String posNum,String staffID,String hgcode);

    /**
     *
     * 查询货柜扫码用户
     * @param hgCode
     * @return
     */
    public HgRelateUser getHgUser(String hgCode);
    /**
     *  添加货柜用户信息
     *
     * @return
     */
    public void addHgRelateUser(String hgCode,String sccId,String loginMode);

    /**
     *
     * 更新状态
     * @param hgcode
     */
    public void updateUser(String hgcode);

    /**
     *
     * 删除货柜用户记录
     * @param hgCode
     * @return
     */
    public void delHgRelateUser(String hgCode);

    /**
     *
     * 查询货柜扫码用户
     * @param hgCode
     * @return
     */
    public String searchHgUser(String hgCode);

    /**
     * 根据订单获取剩余金额
     * @param journalNum
     * @return
     */
    public UnPayRecord getUnPayRecord(String journalNum);

    /**
     *
     * 更新货柜信息
     * @param journalNum
     * @param hgcode
     */
    public void updateCash(String journalNum,String hgcode);

    /**
     *
     * 获取智能货柜未付款的单子
     * @param staffid
     * @return
     */
    public String getUnPayCash(String staffid);

    /**
     *
     * 获取智能货柜未付款的单子
     * @param sccId
     * @return
     */
    public Map<String,Object> getUnPayCashPos(String sccId,String staffID,Map<String,Object> map);

    /**
     *
     *
     * @param hgcode
     * @param journalNum
     */
    public void setHgCash(String hgcode, String journalNum);


    /**
     *
     * 清除购物车
     * @param hgcode
     */
    public void clearSqCart(String hgcode);


    /**
     *
     * 扫码推送到开门成功
     * @param hgcode
     * @param content
     * @param type
     * @param sccId
     */
    public void  pushHg(String hgcode,String content,String type,String sccId);

/**
        *
        * 货柜情况，现金存储公司ID
     * @param journalNum
     * @param sccId
     */
    public void setCashCom(String journalNum,String sccId);

    /**
     *
     * 设置刷卡的用户
     * @param journalNum
     * @param sccId
     */
    public void setPaySccid(String journalNum,String sccId);
    /**
     *
     * 关门
     * @param posNum
     */
    public void pushCloseDoor(String posNum,String door);
    /**
     *
     * 推送到终端
     * @param posNum
     */
    public void pushOpenDoor(String posNum,String door);



    /**
     *
     * 推送到终端
     * @param
     */
    public void pushMessage(String posNum,String url);



    /**
     *
     * 推送序列号
     * @param posNum
     */
    public void pushSeq(String posNum,String seq);


    /**
     *
     * 推送语音
     * @param posNum
     */
    public void pushAudio(String posNum,String audio);
    /**
     *
     * 根据货柜查询posNum
     * @param hgcode
     * @return
     */
    public String getPosNumByHg(String hgcode);
    /**
     * 自动贩卖机管理员登录
     * @param companyIdentifier
     * @param tEshopCustomer
     * @return
     */
    Map<String, Object> adminLogin(String companyIdentifier, TEshopCustomer tEshopCustomer);


    /**
     * 验证贩卖机管理员账号是否被其他用户登录 过
     * @param user
     * @param loginGuid
     * @return
     */
    String validateAdminLogin(String user, String loginGuid);


    /**
     *
     * 保存结果
     * @param sccId
     * @param journalNum
     * @return
     */
    public String addResult(String sccId,String journalNum,String hgCode,String hrId);


    /**
            *
            * 查询ID
     * @param sccId
     * @param hgCode
     * @return
             */
    public String getHgResultHrId(String sccId,String hgCode);
    /**
     *
     * 获取结果
     * @param sccId
     * @param hgCode
     * @return
     */
    public  String  getHgResult(String sccId,String hgCode,String hrId);

    /**
     *
     * 重量记录关联订单号
     * @param hgcode
     * @param journalNum
     */
    public void addJournal(String hgcode,String journalNum);


}