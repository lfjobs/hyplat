package com.wechat.utils;

public class ConstantURL {
	

	

	// 获取access_token的接口地址（GET） 限200（次/天）
	public static final String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

   //jsapi_ticket
	public static final String ticket_url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 菜单创建（POST） 限100（次/天） ID
	public static final String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	// 获取素材列表
	public static final String material_list_url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
    
	// 上传图文消息素材
	public static final String material_upload_url = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";

   //发送消息给指定用户，在手机端查看消息的样式和排版
	public static final String material_preview_url = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
    //分组群发
	public static final String material_sendgroup_url = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
   //根据OpenID列表群发
	public static final String material_sendopenid_url = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
   //删除群发 ，半小时内可删除
	public static final String  material_deleteSend_url = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";

	//基础access_token获取用户基本信息
	public static final String get_wxuser_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN ";

	//网页授权获取用户基本信息
	public static final String get_wxuserinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

	//公众账号通过code换取网页授权access_token以及OpenID
	public static final String access_Token_OpenID_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
   //发送模板消息
    public static final String  send_Temp_SMS  = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    //餐饮推送模板
	public static final String canyin_temp_id = "z8X-E4HBPujjX6Z5Q6XLrYW3cqKMlDMBJiteX7Cv2PQ";

	//普通微信支付消费提醒
	public static final String normal_temp_id = "dVzdpEBUYcMzKo_2Aa52iX3F2fO5cdF_jP7VyRKSTUo";
	//扫描付款码发起扣款
	public static final String micropay= "https://api.mch.weixin.qq.com/pay/micropay";
    //根据授权码获取OPENID
	public static final String  authcodeopenid =  "https://api.mch.weixin.qq.com/tools/authcodetoopenid";

	//人脸支付相关
     //获取SDK调用凭证
	public static final String  get_wxpayface_authinfo = "https://payapp.weixin.qq.com/face/get_wxpayface_authinfo";

	//刷脸支付
	public static final String  facepay = "https://api.mch.weixin.qq.com/pay/facepay";

     //二级商户进件API
	public static final  String apply = "https://api.mch.weixin.qq.com/v3/ecommerce/applyments/";
    //通过业务申请编号查询申请状态
	public static final String  applyout= "https://api.mch.weixin.qq.com/v3/ecommerce/applyments/out-request-no/";
   //图片上传API
	public static final String  upload =  "https://api.mch.weixin.qq.com/v3/merchant/media/upload";

   //微信平台证书
	public static final String cert = "https://api.mch.weixin.qq.com/v3/certificates";

	/**
	 *
	 * 合并支付APP支付
	 */
	public static  final String   APP_PAY = "https://api.mch.weixin.qq.com/v3/combine-transactions/app";

	/**
	 *
	 *  合单下单-JSAPI支付/小程序支付API
	 */
	public static final String  JSAPI_PAY  = "https://api.mch.weixin.qq.com/v3/combine-transactions/jsapi";

	/**
	 *
	 * 合单下单-H5支付API
	 */
	public static final  String  H5_PAY = "https://api.mch.weixin.qq.com/v3/combine-transactions/h5";

    //普通服务商h5支付
	public static final  String  H5Sever_PAY =  "https://api.mch.weixin.qq.com/v3/pay/partner/transactions/h5";

	//普通服务商JSapi支付
	public static final String  JSapiServer_PAY = "https://api.mch.weixin.qq.com/v3/pay/partner/transactions/jsapi";

	//普通服务商JSapi支付
	public static final String  App_PAY = "https://api.mch.weixin.qq.com/v3/pay/partner/transactions/app";



	//普通服务商查询订单
	public static final  String  Server_Query =  "https://api.mch.weixin.qq.com/v3/pay/partner/transactions/out-trade-no/"; //https://api.mch.weixin.qq.com/v3/pay/partner/transactions/id/1217752501201407033233368018?sp_mchid=1230000109&sub_mchid=1900000109


	/**
	 * 请求分账API
	 */
	public static final String profitsharing = "https://api.mch.weixin.qq.com/v3/ecommerce/profitsharing/orders";

	/**
	 *
	 * 完结分账API
	 */
	public static final String finishOrder = "https://api.mch.weixin.qq.com/v3/ecommerce/profitsharing/finish-order";
	/**
	 *
	 *
	 * 合单查询订单API
	 */
	public static final String    searchOrder = "https://api.mch.weixin.qq.com/v3/combine-transactions/out-trade-no/";

	/**
	 *
	 * 添加分账接收方API
	 */
	public static final String  receivers = "https://api.mch.weixin.qq.com/v3/ecommerce/profitsharing/receivers/add";

	/**
	 *
	 * 查询二级商户账户实时余额API
	 */
	public static final String   balance = "https://api.mch.weixin.qq.com/v3/ecommerce/fund/balance/";
	/**
	 *
	 * 二级商户余额提现API
	 */
	public static final String withdraw = "https://api.mch.weixin.qq.com/v3/ecommerce/fund/withdraw";

	/**
	 *
	 * 商户预约提现单号查询
	 */
	public static final String searchWithdraw = "https://api.mch.weixin.qq.com/v3/ecommerce/fund/withdraw/out-request-no/out_request_no";

	/**
	 *
	 * 退款申请API
	 */
	public static final String  refunds = "https://api.mch.weixin.qq.com/v3/ecommerce/refunds/apply";

	/**
	 *
	 * 修改结算帐号API
	 */
	public static final String  modifysettlement = "https://api.mch.weixin.qq.com/v3/apply4sub/sub_merchants/sub_mchid/modify-settlement";
	/**
	 *
	 * 查询结算账户API
	 */
	public static final String  searchsettlement = "https://api.mch.weixin.qq.com/v3/apply4sub/sub_merchants/sub_mchid/settlement";



	/**
	 *
	 *请求分账回退API
	 */
	public  static final   String  returnorders ="https://api.mch.weixin.qq.com/v3/ecommerce/profitsharing/returnorders";


}
