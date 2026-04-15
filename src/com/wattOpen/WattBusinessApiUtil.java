package com.wattOpen;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.model.LiveChannelListing;
import com.wattOpen.businessTO.FollowerTO;
import com.wattOpen.businessTO.PriceTemplateTO;
import com.wattOpen.businessTO.PriceTemplatesTO;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.wattOpen.WattConfig.TEST_API_URL;

/**
 * WattOPEN接口文档（业务层）
 *
 * @author author wangdaigang
 * @create 2024
 */
public class WattBusinessApiUtil {

    /**
     * 获取房间和设备对应关系
     */
    public static final String spaceUnitDeviceUrl = "/open/v2/spaceunit-device";

    /**
     * 出租
     * <p>
     * 为什么以房间为出租对象？以支持一个房间中存在多个/多种设备的场景。
     */
    public static final String leaseUrl = "/open/v2/spaceUnit/{spaceId}/lease";

    /**
     * 修改电价
     */
    public static final String updateDevicePriceUrl = "/open/v2/device/{sn}/priceType/{priceType}";

    /**
     * 退租
     */
    public static final String surrenderUrl = "/open/v2/spaceUnit/{spaceId}/surrender";

    /**
     * 充值记账
     * <p>
     * 此接口只是记账接口，未提供支付功能
     */
    public static final String rechargeUrl = "/open/v2/device/{sn}/recharge";

    /**
     * 退款
     */
    public static final String refundUrl = "/open/v2/device/{sn}/refund";

    /**
     * 查询余额
     */
    public static final String balanceUrl = "/open/v2/device/{sn}/balance";

    /**
     * 查询卡记录
     */
    public static final String rechargeRecordUrl = " /open/v2/device/{sn}/recharge-record";

    /**
     * 更新租客信息
     */
    public static final String updateTenantInfoUrl = "/open/v2/spaceUnit/{spaceId}/tenantInfo";

    /**
     * 查看房间合约信息
     */
    public static final String agreementUrl = "/open/v2/spaceUnit/{spaceId}/agreement";

    /**
     * 查询设备、卡信息
     */
    public static final String paymentInfoUrl = "/open/v2/device/{sn}/payment-Info";


    /**
     * 获取房间和设备对应关系
     *
     * @param sn       设备序列号
     * @param spaceId  房间ID
     * @param pageNumb
     * @param pageSize
     * @return
     */
    public static JSONObject handleSpaceUnitDevice(String sn, String spaceId, Integer pageNumb, Integer pageSize) {

        Assert.isTrue(StrUtil.isNotEmpty(sn), "设备序列号不能为空");
        Assert.isTrue(StrUtil.isNotEmpty(spaceId), "设备序列号不能为空");
        Assert.isTrue(ObjUtil.isNotNull(pageNumb), "设备序列号不能为空");
        Assert.isTrue(ObjUtil.isNotNull(pageSize), "设备序列号不能为空");

        StringBuffer strUrl = new StringBuffer();
        strUrl.append("?sn=" + sn);
        strUrl.append("&spaceId=" + spaceId);
        strUrl.append("&pageNumb=" + pageNumb);
        strUrl.append("&pageSize=" + pageSize);

        String getSpaceUnitDeviceUrl = spaceUnitDeviceUrl + strUrl;

        String r = WattConfig.handleGet(TEST_API_URL + getSpaceUnitDeviceUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 出租
     *
     * @param followerList
     * @param priceTemplates
     * @param paymentType
     * @param waterElectricLinkage
     * @param overdraft
     * @param insufficientBalance
     * @return
     */
    public static JSONObject handleLease(List<FollowerTO> followerList, PriceTemplatesTO priceTemplates, String paymentType, Boolean waterElectricLinkage, Integer overdraft, Integer insufficientBalance) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("followerList", followerList);
        bodyJson.put("priceTemplates", priceTemplates);
        bodyJson.put("insufficientBalance", insufficientBalance);
        bodyJson.put("waterElectricLinkage", waterElectricLinkage);
        bodyJson.put("overdraft", overdraft);
        bodyJson.put("paymentType", paymentType);

        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + leaseUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 修改电价
     *
     * @param sn            设备序列号
     * @param priceTemplate 计费模板
     *                      sharp(尖价格),
     *                      peak(峰价格)
     *                      flat(平价格)
     *                      valley(谷价格)注:①:若priceType不传则计费模板设置则只需要设置price
     *                      ②:若priceType=1
     *                      计费模板则需设sharp,
     *                      peak,
     *                      flat,
     *                      valley
     *                      单位为元
     * @param priceType     价格类型:
     *                      ①.不传则修改平均价格
     *                      ②.priceType=1//表示修改尖、峰、平、谷电价
     *                      3.标识只修改预付费和后付费
     * @param freeType      付费模式：
     *                      0 表示预付费
     *                      1 表示后付费
     * @param overdraft     透支金额：
     *                      当freeType字段 为“预付费 ”时，这个字段必填。
     * @return
     */
    public static JSONObject handleUpdateDevicePrice(String sn, PriceTemplateTO priceTemplate, String priceType, Integer freeType, Integer overdraft) {

        Assert.isTrue(StrUtil.isNotEmpty(sn), "设备序列号不能为空");
        Assert.isTrue(StrUtil.isNotEmpty(priceType), "价格类型不能为空");

        String getDevicePriceUrl = updateDevicePriceUrl;
        getDevicePriceUrl.replace("{sn}", sn).replace("{priceType}", priceType);

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sn", sn);
        bodyJson.put("priceTemplate", priceTemplate);

        bodyJson.put("priceType", priceType);
        bodyJson.put("freeType", freeType);
        bodyJson.put("overdraft", overdraft);
        String r = WattConfig.handlePut(bodyJson.toString(), TEST_API_URL + updateDevicePriceUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 退租
     *
     * @param spaceId 房源ID
     * @return
     */
    public static JSONObject handleSurrender(String spaceId) {

        Assert.isTrue(StrUtil.isNotEmpty(spaceId), "房间ID不能为空");


        String getSurrenderUrl = surrenderUrl;
        getSurrenderUrl.replace("{spaceId}", spaceId);


        JSONObject bodyJson = new JSONObject();
        bodyJson.put("spaceId", spaceId);
        String r = WattConfig.handleDelete(bodyJson.toString(), TEST_API_URL + surrenderUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 充值记账
     * 此接口只是记账接口，未提供支付功能
     *
     * @param sn      设备序列号
     * @param orderId 接入方订单ID
     *                (订单ID必须唯一,最长19位)
     * @param amount  充值金额/单位:元
     * @param remark  描述订单情况
     * @return
     */
    public static JSONObject handleRecharge(String sn, String orderId, BigDecimal amount, String remark) {

        Assert.isTrue(StrUtil.isNotEmpty(sn), "设备序列号不能为空");
        Assert.isTrue(StrUtil.isNotEmpty(orderId), "订单ID不能为空");
        Assert.isTrue(ObjUtil.isNotNull(amount), "充值金额不能为空");
        Assert.isTrue(StrUtil.isNotEmpty(remark), "描述订单情况不能为空");

        // 充值接口处理
        String getRechargeUrl = rechargeUrl;
        getRechargeUrl.replace("{sn}", sn);

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sn", sn);
        bodyJson.put("orderId", orderId);
        bodyJson.put("amount", amount);
        bodyJson.put("remark", remark);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + rechargeUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 退款
     * 此接口只是记账接口，未提供支付功能
     *
     * @param sn      设备序列号
     * @param orderId 接入方订单ID
     *                (订单ID必须唯一,最长19位)
     * @param amount  充值金额/单位:元
     * @param remark  描述订单情况
     * @return
     */
    public static JSONObject handleRefund(String sn, String orderId, BigDecimal amount, String remark) {

        Assert.isTrue(StrUtil.isNotEmpty(sn), "设备序列号不能为空");
        Assert.isTrue(StrUtil.isNotEmpty(orderId), "订单ID不能为空");
        Assert.isTrue(ObjUtil.isNotNull(amount), "充值金额不能为空");
        Assert.isTrue(StrUtil.isNotEmpty(remark), "描述订单情况不能为空");

        String getRefundUrl = refundUrl;
        getRefundUrl.replace("{sn}", sn);

        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sn", sn);
        bodyJson.put("orderId", orderId);
        bodyJson.put("amount", amount);
        bodyJson.put("remark", remark);
        String r = WattConfig.handlePut(bodyJson.toString(), TEST_API_URL + getRefundUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 查询余额
     *
     * @return
     */
    public static JSONObject handleBalance(String sn) {

        Assert.isTrue(StrUtil.isNotEmpty(sn), "设备序列号不能为空");

        String getBalanceUrl = balanceUrl;
        getBalanceUrl.replace("{sn}", sn);

        String r = WattConfig.handleGet(TEST_API_URL + getBalanceUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 查询卡记录
     *
     * @param sn 设备序列号
     * @return
     */
    public static JSONObject handleRechargeRecord(String sn) {

        Assert.isTrue(StrUtil.isNotEmpty(sn), "设备序列号不能为空");

        String getRechargeRecordUrl = rechargeRecordUrl;
        getRechargeRecordUrl.replace("{sn}", sn);

        String r = WattConfig.handleGet(TEST_API_URL + getRechargeRecordUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 更新租客信息
     *
     * @param followerList         租客信息
     * @param priceTemplates       计费模板
     * @param waterElectricLinkage 是否关联水表/电表
     * @param overdraft            透支额度(默认0),单位(元)
     * @param insufficientBalance  余额不足提醒(默认10) 单位为"元"
     * @return
     */
    public static JSONObject handleUpdateTenantInfo(String spaceId, List<FollowerTO> followerList, PriceTemplatesTO priceTemplates, Boolean waterElectricLinkage, Integer overdraft, BigDecimal insufficientBalance) {

        Assert.isTrue(ObjUtil.isNotNull(followerList), "租客信息不能为空");
        Assert.isTrue(ObjUtil.isNotNull(priceTemplates), "计费模板不能为空");

        JSONObject bodyJson = new JSONObject();

        bodyJson.put("followerList", followerList);
        bodyJson.put("priceTemplates", priceTemplates);

        bodyJson.put("waterElectricLinkage", waterElectricLinkage);
        bodyJson.put("overdraft", overdraft);
        bodyJson.put("insufficientBalance", insufficientBalance);

        String getUpdateTenantInfoUrl = updateTenantInfoUrl;
        getUpdateTenantInfoUrl.replace("{spaceId}", spaceId);

        String r = WattConfig.handlePut(bodyJson.toString(), TEST_API_URL + getUpdateTenantInfoUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 查看房间合约信息
     *
     * @return
     */
    public static JSONObject handleAgreement(String spaceId) {

        String getAgreementUrl = agreementUrl;
        getAgreementUrl.replace("{spaceId}", spaceId);

        String r = WattConfig.handleGet(TEST_API_URL + agreementUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 查询设备、卡信息
     *
     * @param sn 设备序列号
     * @return
     */
    public static JSONObject handlePaymentInfo(String sn) {

        Assert.isTrue(StrUtil.isNotEmpty(sn), "设备序列号不能为空");

        String getPaymentInfoUrl = paymentInfoUrl;
        getPaymentInfoUrl.replace("{sn}", sn);

        String r = WattConfig.handleGet(TEST_API_URL + getPaymentInfoUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }
}
