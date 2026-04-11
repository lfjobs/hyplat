package com.alipay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.alipay.config.AlipayConfig;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
public class AlipayDemo {
	private static final Logger logger = LoggerFactory.getLogger(AlipayDemo.class);
    public static void main(String[] args) {


        /**2017030109104700010
         * out_trade_no<字符长度64>订单支付时传入的商户订单号,不能和 trade_no同时为空。
         * trade_no<字符长度64>支付宝交易号，和商户订单号不能同时为空
         * refund_amount<字符长度9>需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
         * refund_reason<字符长度256>退款的原因说明
         * out_request_no<字符长度64>标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
         * */
//        String out_trade_no = "2017041710374600021";
//        String trade_no = "2017041721001004070210660094";
//        String refund_amount = "730";
//        String refund_reason = "正常退款";
//
//        JSONObject jsonObj = new JSONObject();
//
//        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
//        jsonObj.accumulate("out_trade_no", out_trade_no);
//        jsonObj.accumulate("trade_no", trade_no);
//        jsonObj.accumulate("refund_amount", refund_amount);
//        jsonObj.accumulate("refund_reason", refund_reason);
//        String res = jsonObj.toString();
//        request.setBizContent(res);
//        try {
//            AlipayConfig.response = AlipayConfig.alipayClient.execute(request);
//        } catch (AlipayApiException e) {
//            // TODO Auto-generated catch block
//            logger.error("操作异常", e);
//        }
//        String aa=null;
//        if(AlipayConfig.response.isSuccess()){
//            aa="成功";
//        } else {
//            aa="失败";
//        }
//        System.out.print(aa);










        String URL = "https://openapi.alipay.com/gateway.do";   //URL	支付宝网关（固定）
        String APP_ID = AlipayConfig.APP_ID;       //APPID	APPID 即创建应用后生成
        String APP_PRIVATE_KEY = AlipayConfig.APP_PRIVATE_KEY;//APP_PRIVATE_KEY	开发者私钥，由开发者自己生成
        String FORMAT = "json";       //FORMAT	参数返回格式，只支持json
        String CHARSET = "UTF-8";  //CHARSET	编码集，支持GBK/UTF-8
        String ALIPAY_PUBLIC_KEY = AlipayConfig.APP_PUBLIC_KEY;  //ALIPAY_PUBLIC_KEY	支付宝公钥，由支付宝生成
        String SIGN_TYPE = "RSA";   //SIGN_TYPE	商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2

        //调用
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        //获取到的授权code
        request.setCode("526c43762fb14c76bfe01d509a64TE95");
        request.setGrantType("authorization_code");

        try {
            //code查询user_id和access_token
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            logger.info("调试信息");
            //根据access_token查询用户信息
            AlipayUserInfoShareRequest userInfoRequest = new AlipayUserInfoShareRequest();
            AlipayUserInfoShareResponse userInfoResponse = alipayClient.execute(userInfoRequest,oauthTokenResponse.getAccessToken());
            logger.info("值：{}", userInfoResponse);


            //转账
            AlipayFundTransToaccountTransferRequest toaTranReq = new AlipayFundTransToaccountTransferRequest();
            toaTranReq.setBizContent("{" +
                    "\"out_biz_no\":\"3142321423439\"," +
                    "\"payee_type\":\"ALIPAY_USERID\"," +
                    "\"payee_account\":\"2088902363293951\"," +
                    "\"amount\":\"0.1\"," +
                    "\"payer_show_name\":\"北京天太世统\"," +
                    // "\"payee_real_name\":\"张三\"," +
                    "\"remark\":\"转账\"" +
                    "  }");
            AlipayFundTransToaccountTransferResponse toaTranRes  = alipayClient.execute(toaTranReq);
            if(toaTranRes.isSuccess()){
                logger.info("调用转账成功");
            } else {
                logger.info("调用转账失败");
            }



            //查询转账
            AlipayFundTransOrderQueryRequest orderQueryReq = new AlipayFundTransOrderQueryRequest();
            orderQueryReq.setBizContent("{" +
                    "\"out_biz_no\":\""+toaTranRes.getOutBizNo()+"\"," +
                    "\"order_id\":\""+toaTranRes.getOrderId()+"\"" +
                    "  }");
            AlipayFundTransOrderQueryResponse orderQueryRes = alipayClient.execute(orderQueryReq);

            if(orderQueryRes.isSuccess()){
                logger.info("调用查询接口成功");
            } else {
                logger.info("调用查询接口失败");
            }

        } catch (AlipayApiException e) {
            //处理异常
            logger.error("操作异常", e);
        }

//        String content="apiname=com.alipay.account.auth&app_id="+AlipayConfig.APP_ID+"&app_name=mc&auth_type=AUTHACCOUNT&biz_type=openservice&method=alipay.open.auth.sdk.code.get&pid=2088011999101771&product_id=APP_FAST_LOGIN&scope=kuaijie&target_id="+System.currentTimeMillis()+"&sign_type=RSA";
//        String sign= null;
//        try {
//            sign = AlipaySignature.rsaSign(content, AlipayConfig.APP_PRIVATE_KEY, CHARSET, AlipayConfig.sign_type);
//        } catch (AlipayApiException e) {
//            logger.error("操作异常", e);
//        }
//        String enCodesign = null;
//        try {
//            enCodesign = URLEncoder.encode(sign, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            logger.error("操作异常", e);
//        }
//        String authInfo = content+"&sign="+enCodesign;
//        logger.info("值：{}", authInfo);
//
//    }
    }
}

