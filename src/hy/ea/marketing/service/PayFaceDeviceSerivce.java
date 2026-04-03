package hy.ea.marketing.service;


import com.tiantai.wfj.bo.PayFaceDevice;
import com.tiantai.wfj.bo.SetPoster;
import com.tiantai.wfj.bo.StoreBindDevice;
import hy.ea.bo.office.InteractDocInfo;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;


public interface PayFaceDeviceSerivce {
    //添加刷脸终端
    public void addOrUpdate(PayFaceDevice pos, String companyID, String staffID);

    //删除刷脸终端
    public void delete(String pfdkey);

    /**
     * 设备号全网唯一
     *
     * @param sn
     * @return
     */
    public String checkRepPosNum(String sn, String pfdID);

    /**
     * 判断商户是否签约
     *
     * @param sbdID
     * @return
     */
    public String checkComPosNum(String subCompanyID, String sbdID);
    /**
     * 验证设备是否录入到系统里，如果录入了，查询是否已经绑定过商户，如果绑定了则不能再绑定
     * @param sn
     * @return
     */

    public String checkSn(String sn);

    /**
     * 设备绑定商户
     * @param sn
     * @param subCompanyID
     * @param staffID
     * @return
     */
    public void storeBindDevice(String sn,String subCompanyID,String  staffID);

    /**
     * 移除设备和商户绑定
     * @param
     * @return
     */
    public void removeStoreBind(String sn,String subCompanyID,String staffID);

    /**
     * 设备绑定业务员
     *
     * @param sn
     * @param wfjAccount
     * @return
     */
    public void bindClerk(String sn,String wfjAccount,String staffID);


    /**
     * 验证手机号是否是微分金账号
     *
     * @param wfjAccount
     * @return
     */
    public Object checkUser(String wfjAccount);


    /***
     *  解绑业务员
     * @param sn
     * @param wfjAccount
     * @param staffID
     */
    public void removeClerk(String sn, String wfjAccount, String staffID);

    /**
     * 获取商家
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageForm getCompanyList(int pageNumber, int pageSize, String companyName);


    /**
     *  商户微信信息保存
     * @param store
     * @param companyID
     * @param staffID
     */
    public void addStoreOrUpdate(StoreBindDevice store, String companyID, String staffID);

    /**
     *
     * 调整费率并记录历史记录
     * @param subCompanyID
     * @param rate
     * @return
     */
    public void changeRate(String subCompanyID,String rate,String staffID);

    /**
     * 删除商户
     * @param sbdKey
     */
    public void deleteStore(String sbdKey);

    /**
     *   根据商户获取它绑定的设备
     * @param pageNumber
     * @param pageSize
     * @param subCompanyID
     * @param parameter
     * @return
     */
    public PageForm getStoreAllDevice(int pageNumber, int pageSize, String subCompanyID,String parameter);

    /**
     *
     * 验证商户是否绑定过设备
     * @param subCompanyID
     * @return
     */
    public String checkStoreDevice(String subCompanyID);

    /**
     *
     * 根据设备号获取绑定的商户的微信APPID和商户号
     * @return
     */
    public Object getWXData(String sn);

    /**
     * 刷脸支付记录信息
     * @param journalNum
     * @param openid
     * @param transaction_id
     * @param money
     */
    public void savePayBill(String journalNum,String openid,String nickname, String transaction_id,String money,String store_id,String device_id);

    /**
     * 设备交易金额
     * @param sn
     * @return
     */
    public void recordTradeMoney(String sn,String money,String journalNum,String source,String openid,String nickname);

    /**
     *   海报
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @return
     */
    public PageForm getPosterList(int pageNumber, int pageSize, String companyID,String parameter,String type);


    /**
     *
     * 删除海报
     * @param spKey
     */
    public void deletePoster(String spKey);

    /**
     * 上下线海报
     * @param spID
     * @param isPublish
     */
    public void onOffLine(String spID,String isPublish,String staffID);

    /**
     *
     * 排序
     * @param sorts
     */
    public void posterSorts( String spID,int sorts);

    /**
     *
     * 获取在线海报
     * @return
     */
    public List<Object> getPosterList();

    /**
     * 添加海报
     * @param poster
     */
    public void addPoster(SetPoster poster);

    /**
     * 从数据库里获取已经保存的accessToken
     *
     * @return
     */
    public String getAccessTokenFromDataBase();
}