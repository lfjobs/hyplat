package com.alipay.faceTopay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.bo.TradePayParam;
import com.alipay.config.AlipayConfig;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.util.Constant;

/**
 * Created by Administrator on 2017/3/10 0010.
 */
public class AlipayTradePay {
	private static final Logger logger = LoggerFactory.getLogger(AlipayTradePay.class);

    public static  AlipayClient  alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", "GBK", AlipayConfig.APP_PUBLIC_KEY, "RSA");


    /**
     *
     * 当面付支付
     * @param tradePayParam
     * @return
     */
    public static TradePayReuslt tradePay(TradePayParam tradePayParam){

        String out_trade_no = tradePayParam.getOut_trade_no();
        String store_id = tradePayParam.getStore_id();

        if(out_trade_no==null||out_trade_no.equals("")){
            out_trade_no = ""+WeChatUtils.getNow();
        }
        if(store_id==null||store_id.equals("")){
            store_id = Constant.ZLY_COMPNAYID;
        }
        store_id = store_id.substring(7);
        TradePayReuslt tradePayReuslt = new TradePayReuslt();


        try {
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizContent("{" +
                " \"out_trade_no\":\""+out_trade_no+"\"," +
                "\"scene\":\"bar_code\"," +
                "\"auth_code\":\""+tradePayParam.getAuth_code()+"\"," +//即用户在支付宝客户端内出示的付款码，使用一次即失效，需要刷新后再去付款
                " \"subject\":\""+tradePayParam.getSubject()+"\"," +
                "\"body\":\""+tradePayParam.getBody()+"\"," +
                "\"store_id\":\""+store_id+"\"," +
                "\"timeout_express\":\"2m\"," +
                "\"total_amount\":\""+tradePayParam.getTotal_amount()+"\"}"); //设置业务参数
        AlipayTradePayResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            System.out.print(response.getBody());

            logger.info("调用成功");
        } else {
            logger.info("调用失败");
        }

            tradePayReuslt.setCode(response.getCode());
            tradePayReuslt.setBuyer_user_id(response.getBuyerUserId());
            tradePayReuslt.setTrade_no(response.getTradeNo());

        } catch (Exception e) {
            logger.error("操作异常", e);
        }

              return tradePayReuslt;
    }

    /**
     *
     * 当面付查询
     * @param out_trade_no
     * @return
     */
    public static TradePayReuslt tradeQuery(String out_trade_no){
        TradePayReuslt tradePayReuslt = new TradePayReuslt();
        String code = "";
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
            request.setBizContent("{" +
                    "    \"out_trade_no\":"+out_trade_no+"}"); //设置业务参数
            AlipayTradeQueryResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
            tradePayReuslt.setCode(response.getCode());
            tradePayReuslt.setBuyer_user_id(response.getBuyerUserId());
            tradePayReuslt.setTrade_no(response.getTradeNo());
            tradePayReuslt.setTrade_status(response.getTradeStatus());
            //根据response中的结果继续业务逻辑处理
        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return tradePayReuslt;
    }

    /**
     *
     * 当面付撤销（只有发生支付系统超时或者支付结果未知时可调用撤销）
     * @return
     */
    public static String tradeCancel(String out_trade_no){
        String code = "";
        try {
        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();//创建API对应的request类
        request.setBizContent("{" +
                "    \"out_trade_no\":"+out_trade_no+"}"); //设置业务参数


        AlipayTradeCancelResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
//        System.out.print(response.getBody());
        code = response.getCode();
        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return code;
    }


    public static void main(String[] args) {
        try {
            AlipayClient  alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", "GBK", AlipayConfig.APP_PUBLIC_KEY, "RSA");
            AlipayTradePayRequest request = new AlipayTradePayRequest();
            request.setBizContent("{" +
                   "    \"out_trade_no\":\"20130330013231231\"," +
                    "    \"scene\":\"bar_code\"," +
                    "    \"auth_code\":\"288856081934315676\"," +//即用户在支付宝客户端内出示的付款码，使用一次即失效，需要刷新后再去付款
                   "    \"subject\":\"Iphone6 16G\"," +
                    "    \"store_id\":\"NJ_001\"," +
                    "    \"timeout_express\":\"2m\"," +
                    "    \"total_amount\":\"0.01\"" +
                   "  }"); //设置业务参数
            AlipayTradePayResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.print(response.getBody());
                response.getCode();
                logger.info("调用成功");
            } else {
                logger.info("调用失败");
                System.out.print(response.getBody());
            }

        } catch (Exception e) {
            logger.error("操作异常", e);
        }
    }
}

