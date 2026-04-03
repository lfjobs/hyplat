<%@page import="com.sun.xml.bind.v2.runtime.output.Encoded" %>
<%@ page import="com.alipay.util.AlipaySubmit"%>
<%@page import="com.alipay.config.AlipayConfig" %>
<%@page import="com.alipay.api.DefaultAlipayClient" %>
<%@page import="com.alipay.api.request.AlipayTradeWapPayRequest" %>
<%@page import="com.alipay.api.domain.AlipayTradeWapPayModel" %>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%@ page import="com.tiantai.wfj.front.WfjEshopProductAction" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>支付宝手机网页支付</title>
</head>
<%
    Logger logger = LoggerFactory.getLogger(WfjEshopProductAction.class);
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    if (request.getParameter("WIDout_trade_no") != null) {

        //驾校报名类型
        String buyIsOkPage = request.getParameter("buyIsOkPage");
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        // 订单名称，必填
        //String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"), "UTF-8");
        String subject =request.getParameter("WIDsubject");
        System.out.println(subject);
        // 付款金额，必填
        String WIDtotal_fee = new String(request.getParameter("WIDtotal_fee").getBytes("ISO-8859-1"), "UTF-8");
        // 商品描述，可空
        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"), "UTF-8");


        // 销售产品码 必填
        String product_code = "QUICK_WAP_WAY";

        session.setAttribute("total", WIDtotal_fee);
        //必填

        String WIDflag = new String(request.getParameter("WIDflag").getBytes("ISO-8859-1"), "UTF-8");
        //选填
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式

        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();
        //增加返回标识
        String[] temp = body.split(",");
        String flag = null;
        if ("BenDis".equals(WIDflag)) {
            flag = temp[7];
        } else if ("GoodsShow".equals(WIDflag)) {
            flag = temp[0];
        } else if ("ShopOwner".equals(WIDflag)) {
            flag = "02";
        }

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(WIDtotal_fee);
        model.setBody(body);
        model.setQuitUrl(basePath + "/page/WFJClient/CustomerOrder/back.jsp?flag=" + flag);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        if (WIDflag.equals("selfservice")) {
            //服务器异步通知页面路径
            alipay_request.setNotifyUrl(basePath + "/ea/sm/ea_getzfb.jspa");
            //需http://格式的完整路径，不能加?id=123这类自定义参数

            //页面跳转同步通知页面路径
            alipay_request.setReturnUrl(basePath + "/ea/sm/ea_call_back.jspa");
        } else if (WIDflag.equals("BenDis")) {
            //服务器异步通知页面路径
            alipay_request.setNotifyUrl(basePath + "/ea/jinbi/ea_getzfb.jspa");
            //需http://格式的完整路径，不能加?id=123这类自定义参数

            //页面跳转同步通知页面路径
            alipay_request.setReturnUrl(basePath + "/ea/jinbi/ea_call_back.jspa");
        } else if (WIDflag.equals("ShopOwner")||WIDflag.equals("GoodsShow")) {
            // 设置异步通知地址
            alipay_request.setNotifyUrl(basePath + "/ea/wfjshop/ea_getzfb.jspa");
            // 设置同步地址
            alipay_request.setReturnUrl(basePath + "/ea/wfjshop/ea_call_back.jspa?buyIsOkPage=" + buyIsOkPage);

            //必填，不能修改
        } else if (WIDflag.equals("customerorder")) {
            // 设置异步通知地址
            alipay_request.setNotifyUrl(basePath + "/ea/buyproducts/ea_notify_url.jspa");
            // 设置同步地址
            alipay_request.setReturnUrl(basePath + "/ea/buyproducts/ea_call_back.jspa");
        }

        //把请求参数打包成数组
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "alipay.wap.create.direct.pay.by.user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_id", AlipayConfig.seller_id);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
        sParaTemp.put("payment_type", "1");
        sParaTemp.put("notify_url", alipay_request.getNotifyUrl());
        sParaTemp.put("return_url", alipay_request.getReturnUrl());
        sParaTemp.put("out_trade_no", out_trade_no);
        sParaTemp.put("subject", subject);
        sParaTemp.put("total_fee", WIDtotal_fee);
        sParaTemp.put("show_url", basePath + "/page/WFJClient/CustomerOrder/back.jsp?flag=" + flag);
        sParaTemp.put("body", body);
        sParaTemp.put("it_b_pay", "");
        sParaTemp.put("extern_token", "");

        //建立请求
        String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
        out.println(sHtmlText);
    }

%>
<body>
</body>
</html>
