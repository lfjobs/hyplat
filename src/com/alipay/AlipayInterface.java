package com.alipay;

import com.alipay.api.*;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.alipay.config.AlipayConfigTtsw;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;


/**
 * Created by Administrator on 20
 * 20-11-06.
 */
@Service
public class AlipayInterface {
    //实例化客户端
    private static DefaultAlipayClient alipayClient = AlipayConfigTtsw.AlipayConfig();

    /**
     * app支付接口2.0
     * alipay.trade.app.pay
     *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("AlipayTradeAppPay")
    public static String AlipayTradeAppPay(AlipayObject ao) throws UnsupportedEncodingException {
        String orderString=null;
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String basePath = request1.getScheme() + "://"
                + request1.getServerName() + ":" + request1.getServerPort()
                + request1.getContextPath() + "/";
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = (AlipayTradeAppPayModel) ao;
        model.setProductCode("QUICK_MSECURITY_PAY");// 销售产品码 必填
        request.setBizModel(model);
        //request.setBizModel(this.jsonToSearchFilter(model));
        request.setNotifyUrl("商户外网可以访问的异步地址");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            orderString=response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return orderString;
    }


    /**
     * 手机网站支付接口2.0
     * alipay.trade.wap.pay
     *
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("generateOrder")
    public static void AlipayTradeWapPay(AlipayObject ao) throws UnsupportedEncodingException {
        HttpServletRequest request1 = ServletActionContext.getRequest();
        String basePath = request1.getScheme() + "://"
                + request1.getServerName() + ":" + request1.getServerPort()
                + request1.getContextPath() + "/";
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeWapPayModel model = (AlipayTradeWapPayModel) ao;
        model.setProductCode("QUICK_WAP_WAY");// 销售产品码 必填
        request.setBizModel(model);
        request.setNotifyUrl(basePath + "ea/wfjshop/ea_getzfb.jspa");//服务器异步通知页面路径
        request.setReturnUrl(basePath + "ea/wfjshop/ea_call_back.jspa");//页面跳转同步通知页面路径
        try {
            AlipayTradeAppPayResponse response = alipayClient.pageExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付异步回调
     *
     * @param request
     * @return
     */
    @RequestMapping("notifyUrl")
    @ResponseBody
    public void AlipayNotify(HttpServletRequest request, HttpServletResponse response) {
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        try {
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号

            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
            //支付宝交易号

            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");

            /**
             *@param params 参数列表(包括待验签参数和签名值sign) key-参数名称 value-参数值
             *@param alipayPublicCertPath 验签支付宝公钥证书路径
             *@param charset 验签字符集
             **/
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfigTtsw.ALIPAY_CERT_PATH, AlipayConfigTtsw.CHARSET, AlipayConfigTtsw.SIGN_TYPE);

            if (verify_result) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码

                //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

                if (trade_status.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                    //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                    //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                    //如果有做过处理，不执行商户的业务程序

                    //注意：
                    //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
                }

                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
                //out.clear();
                //out.println("success");	//请不要修改或删除
                response.getWriter().close();
                response.getWriter().write("success");
            } else {//验证失败
                //out.println("fail");
                //System.out.println("验证失败");
                //logger.error("验证失败");
                response.getWriter().write("fail");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付同步步回调
     *
     * @param request
     * @return
     */
    @RequestMapping("returnUrl")
    @ResponseBody
    public void AlipayReturn(HttpServletRequest request, HttpServletResponse response) {
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();

        try {
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
            //商户订单号

            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            //支付宝交易号

            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            /**
             *@param params 参数列表(包括待验签参数和签名值sign) key-参数名称 value-参数值
             *@param alipayPublicCertPath 验签支付宝公钥证书路径
             *@param charset 验签字符集
             **/
            boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfigTtsw.ALIPAY_CERT_PATH, AlipayConfigTtsw.CHARSET, AlipayConfigTtsw.SIGN_TYPE);

            if (verify_result) {//验证成功
                //////////////////////////////////////////////////////////////////////////////////////////
                //请在这里加上商户的业务逻辑程序代码
                //该页面可做页面美工编辑
                //out.clear();
                //out.println("验证成功<br />");
                response.getWriter().close();
                response.getWriter().write("验证成功");
                //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

                //////////////////////////////////////////////////////////////////////////////////////////
            } else {
                //该页面可做页面美工编辑
                //out.clear();
                //out.println("验证失败");
                response.getWriter().close();
                response.getWriter().write("验证失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 直付通商户创建预校验咨询
     * ant.merchant.expand.indirect.zft.consult
     *
     * @return
     */
    public boolean AlipayMerchantConsult() {
        AntMerchantExpandIndirectZftConsultRequest request = new AntMerchantExpandIndirectZftConsultRequest();
        request.setBizContent("");
        boolean falg = false;
        try {
            AntMerchantExpandIndirectZftConsultResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            falg = false;
            e.printStackTrace();
        }
        return falg;
    }

    /**
     * 直付通二级商户创建
     * ant.merchant.expand.indirect.zft.create
     *
     * @return
     */
    public boolean AlipayMerchantCreate() {
        AntMerchantExpandIndirectZftCreateRequest request = new AntMerchantExpandIndirectZftCreateRequest();
        request.setBizContent("");
        boolean falg = false;
        try {
            AntMerchantExpandIndirectZftCreateResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            falg = false;
            e.printStackTrace();
        }
        return falg;
    }

    /**
     * 直付通二级商户修改
     * ant.merchant.expand.indirect.zft.modify
     *
     * @return
     */
    public boolean AlipayMerchantmodify() {
        AntMerchantExpandIndirectZftModifyRequest request = new AntMerchantExpandIndirectZftModifyRequest();
        request.setBizContent("");
        boolean falg = false;
        try {
            AntMerchantExpandIndirectZftModifyResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            falg = false;
            e.printStackTrace();
        }
        return falg;
    }

    /**
     * 直付通商户入驻进度查询
     * ant.merchant.expand.indirect.zftorder.query
     *
     * @return
     */
    public boolean AlipayMerchantquery() {
        AntMerchantExpandIndirectZftorderQueryRequest request = new AntMerchantExpandIndirectZftorderQueryRequest();
        request.setBizContent("");
        boolean falg = false;
        try {
            AntMerchantExpandIndirectZftorderQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            falg = false;
            e.printStackTrace();
        }
        return falg;
    }

    /**
     * 支付宝图片上传
     * ant.merchant.expand.indirect.image.upload
     *
     * @param filePath 图片路径
     * @return
     */
    public String AlipayImageUpload(String filePath) {
        AntMerchantExpandIndirectImageUploadRequest request = new AntMerchantExpandIndirectImageUploadRequest();
        request.setImageType("jpg");
        FileItem ImageContent = new FileItem(filePath);
        request.setImageContent(ImageContent);
        AntMerchantExpandIndirectImageUploadResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return response.getImageId();
    }

    /**
     * 分账关系绑定
     * alipay.trade.royalty.relation.bind
     *
     * @return
     */
    public boolean AlipayRelationBind(AlipayTradeRoyaltyRelationBatchqueryModel RelationBind) {
        boolean falg = false;
        AlipayTradeRoyaltyRelationBindRequest request = new AlipayTradeRoyaltyRelationBindRequest();
        request.setBizModel(RelationBind);
        try {
            AlipayTradeRoyaltyRelationBindResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * 分账关系解绑
     * alipay.trade.royalty.relation.unbind
     *
     * @return
     */
    public boolean AlipayRelationUnbind(AlipayTradeRoyaltyRelationBatchqueryModel RelationBind) {
        boolean falg = false;
        AlipayTradeRoyaltyRelationUnbindRequest request = new AlipayTradeRoyaltyRelationUnbindRequest();
        request.setBizModel(RelationBind);
        try {
            AlipayTradeRoyaltyRelationUnbindResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * 分账关系查询
     * alipay.trade.royalty.relation.batchquery
     *
     * @return
     */
    public boolean AlipayRelationBatchquery(AlipayTradeRoyaltyRelationBatchqueryModel RelationBind) {
        boolean falg = false;
        AlipayTradeRoyaltyRelationBatchqueryRequest request = new AlipayTradeRoyaltyRelationBatchqueryRequest();
        request.setBizModel(RelationBind);
        try {
            AlipayTradeRoyaltyRelationBatchqueryResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * 统一收单交易创建接口
     * alipay.trade.create
     *
     * @return
     */
    public boolean AlipayTradeCreate(AlipayObject ao) {
        boolean falg = false;
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        AlipayTradeCreateModel model = (AlipayTradeCreateModel) ao;
        //销售产品码。
        //如果签约的是当面付快捷版，则传OFFLINE_PAYMENT;
        //其它支付宝当面付产品传FACE_TO_FACE_PAYMENT；
        //不传默认使用FACE_TO_FACE_PAYMENT；
        model.setProductCode("FACE_TO_FACE_PAYMENT");
        request.setBizModel(model);
        try {
            AlipayTradeCreateResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * 统一收单合并支付预创建接口
     * alipay.trade.merge.precreate
     *
     * @return
     */
    public String AlipayTradeMergePrecreate(AlipayObject ao) {
        String pre_order_no = null;
        AlipayTradeMergePrecreateRequest request = new AlipayTradeMergePrecreateRequest();
        AlipayTradeMergePrecreateModel model=(AlipayTradeMergePrecreateModel) ao;
        request.setBizModel(model);
        try {
            AlipayTradeMergePrecreateResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                pre_order_no = response.getPreOrderNo();
            } else {
                System.out.println("调用失败");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return pre_order_no;
    }

    /**
     * app合并支付接口
     * alipay.trade.app.merge.pay
     *
     * @param pre_order_no 预下单号
     * @return
     */
    public boolean AlipayTradeAppMergePay(String pre_order_no) {
        boolean falg = false;
        AlipayTradeAppMergePayRequest request = new AlipayTradeAppMergePayRequest();
        AlipayTradeAppMergePayModel alipayObject = new AlipayTradeAppMergePayModel();
        alipayObject.setPreOrderNo(pre_order_no);
        request.setBizModel(alipayObject);
        try {
            AlipayTradeAppMergePayResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * 无线Wap合并支付接口2.0
     * alipay.trade.wap.merge.pay
     *
     * @param pre_order_no 预下单号
     * @return
     */
    public boolean AlipayTradeWapMergePay(String pre_order_no) {
        boolean falg = false;
        AlipayTradeWapMergePayRequest request = new AlipayTradeWapMergePayRequest();
        AlipayTradeWapMergePayModel alipayObject = new AlipayTradeWapMergePayModel();
        alipayObject.setPreOrderNo(pre_order_no);
        request.setBizModel(alipayObject);
        try {
            AlipayTradeWapMergePayResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * 统一收单确认结算接口
     * alipay.trade.settle.confirm
     *
     * @return
     */
    public boolean AlipayTradeSettle(AlipayTradeSettleConfirmCancelModel alipayObject) {
        boolean falg = false;
        AlipayTradeSettleConfirmRequest request = new AlipayTradeSettleConfirmRequest();
        request.setBizModel(alipayObject);
        try {
            AlipayTradeSettleConfirmResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * 统一收单线下交易查询
     * alipay.trade.query
     *
     * @return
     */
    public boolean AlipayTradeQuery(AlipayTradeSettleConfirmCancelModel alipayObject) {
        boolean falg = false;
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(alipayObject);
        try {
            AlipayTradeQueryResponse response = alipayClient.pageExecute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * 统一收单交易退款接口
     * alipay.trade.refund
     *
     * @return
     */
    public boolean AlipayTradeRefund(AlipayTradeSettleConfirmCancelModel alipayObject) {
        boolean falg = false;
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(alipayObject);
        try {
            AlipayTradeRefundResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功");
                falg = true;
            } else {
                System.out.println("调用失败");
                falg = false;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            falg = false;
        }
        return falg;
    }

    /**
     * json转换实体类型
     *
     * @param filters
     * @return
     */
    private AlipayTradeAppPayModel jsonToSearchFilter(String filters) {
        AlipayTradeAppPayModel model = null;
        try {
            JSONObject jsonobject = JSONObject.fromObject(filters);//将json格式的字符串转换成JSONObject 对象
            model = (AlipayTradeAppPayModel) JSONObject.toBean(jsonobject, AlipayTradeAppPayModel.class);  //通过JSONObject.toBean()方法进行对象间的转换
            /*
            JSONArray array = jsonobject.getJSONArray("rules");
            //如果json格式的字符串里含有数组格式的属性，将其转换成JSONArray，以方便后面转换成对应的实体
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = (JSONObject) array.get(i);     //将array中的数据进行逐条转换
                model = (AlipayTradeAppPayModel) JSONObject.toBean(object, AlipayTradeAppPayModel.class);  //通过JSONObject.toBean()方法进行对象间的转换
            }*/
        } catch (Exception e) {
            System.out.println("filters=" + filters.toString() + ".json转换成实体类出错");
            e.printStackTrace();
        }
        return model;
    }

}
