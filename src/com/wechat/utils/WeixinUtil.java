package com.wechat.utils;


import com.tiantai.wfj.bo.WeChatToken;
import com.unionpay.acp.sdk.BaseHttpSSLSocketFactory.MyX509TrustManager;
import com.wechat.bo.*;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.utils.GetWxOrderno;
import com.wechatpay.utils.RequestHandler;
import com.wechatpay.utils.Sha1Util;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.util.Constant;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 公众平台通用接口工具类
 *
 *
 *
 */
@Service
@Transactional
public class WeixinUtil {
	private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);


	private static String accessToken = "6eGcLr49Gd9qsTXnCgZ-FA_7oCL1xZ4K4yjErg5eYC0veIL35xUzs59c35TixmZlsTaWyycXTRM-nB24Z2Jb6IK_Wgfl30EhbMZ1ccT3q9EZJNbAFAUEI";

	/**
	 * 创建菜单
	 *
	 * @param menu        菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = ConstantURL.menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 获取access_token
	 *
	 * @param appid     凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ConstantURL.access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		log.error("getAccessToken:" + requestUrl);
		System.out.print("getAccessToken:" + requestUrl);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				log.error("access_token:" + jsonObject.getString("access_token"));
				log.error("expires_in:" + jsonObject.getInt("expires_in"));
				System.out.print("access_token:" + jsonObject.getString("access_token"));
				System.out.print("expires_in:" + jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",
						jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
				System.out.print("获取token失败 errcode:{} errmsg:{}" + jsonObject.getInt("errcode") +
						jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 *
	 * 获取jsapi_ticket
	 * @param accessToken
	 * @return
     */
	public static String getJSticket(String accessToken) {

		String requestUrl = ConstantURL.ticket_url.replace("ACCESS_TOKEN", accessToken);

		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		String ticket= "";
		// 如果请求成功
		if (null != jsonObject) {
			try {

				 ticket=jsonObject.getString("ticket");
				System.out.print("ticket"+ticket);
			} catch (JSONException e) {

			}
		}
		return ticket;
	}
	/**
	 * 发起https请求并获取结果
	 *
	 * @param requestUrl    请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr     提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
										 String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {new MyX509TrustManager()};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}




	/**
	 * 创建图文消息
	 *
	 * @return
	 */
	public static String createMaterialMessage() {
		String url = ConstantURL.material_upload_url.replace("ACCESS_TOKEN", accessToken);
		ArticleMain articleMain = new ArticleMain();
		Articles articles = new Articles();

		articles.setThumb_media_id("1Kw3gpZ2anB6Ey2tpcHkaPtoZ5oupwHTbb0FoHrddfIbZoRMmZTSRBPdcdsfkLX2");
		articles.setAuthor("mz");
		articles.setTitle("推送测试无需理会");
		articles.setContent_source_url("www.qq.com");
		articles.setContent("Happy Day content");
		articles.setDigest("描述");
		articles.setShow_cover_pic("1");

		Articles articles1 = new Articles();

		articles1.setThumb_media_id("1Kw3gpZ2anB6Ey2tpcHkaPtoZ5oupwHTbb0FoHrddfIbZoRMmZTSRBPdcdsfkLX2");
		articles1.setAuthor("mz");
		articles1.setTitle("推送测试无需理会");
		articles1.setContent_source_url("www.qq.com");
		articles1.setContent("<img data-s=\"300,640\" data-type=\"jpeg\" src=\"https://mmbiz.qlogo.cn/mmbiz/qS3bxSdrOIIp9HRws0TNO5wTZ77233Tol8GA8a542HXDU5ibs1xT9dJdqnuph8lrQT1fL5bLtrBviaf2JgdF6hqQ/0?wx_fmt=jpeg\" _src=\"https://mmbiz.qlogo.cn/mmbiz/qS3bxSdrOIIp9HRws0TNO5wTZ77233Tol8GA8a542HXDU5ibs1xT9dJdqnuph8lrQT1fL5bLtrBviaf2JgdF6hqQ/0?wx_fmt=jpeg\">");
		articles1.setDigest("描述");
		articles1.setShow_cover_pic("1");


		articleMain.setArticles(new Articles[]{articles, articles1});


		// 将对象转换成json字符串
		String jsonmater = JSONObject.fromObject(articleMain).toString();
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonmater);
		logger.info("调试信息");
		logger.info("调试信息");


		return "";

	}


	/**
	 * 发送到手机端预览
	 *
	 * @return
	 */
	public static String MessagePreview() {
		String url = ConstantURL.material_preview_url.replace("ACCESS_TOKEN", accessToken);
		MpnewsMessage mpMessage = new MpnewsMessage();
		mpMessage.setTowxname("mzdudumz");
		mpMessage.setMsgtype("mpnews");
		MediaID mediaID = new MediaID();
		mediaID.setMedia_id("3meV6DI_W-GLlHUq6VjhpFc4nJAKAsXLMC47ufutLaBmX9j0CRY6U9B4cIYpAvcH");
		mpMessage.setMpnews(mediaID);

		// 将对象转换成json字符串
		String jsonmater = JSONObject.fromObject(mpMessage).toString();
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonmater);
		logger.info("调试信息");
		logger.info("调试信息");
		return "";


	}

	/**
	 * 删除群发
	 *
	 * @return
	 */
	public static String deleteGroupSend() {
		String url = ConstantURL.material_deleteSend_url.replace("ACCESS_TOKEN", accessToken);
		DeleteGroupSend dsend = new DeleteGroupSend();
		dsend.setMsg_id("401645236");
		// 将对象转换成json字符串
		String jsonmater = JSONObject.fromObject(dsend).toString();
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonmater);
		logger.info("调试信息");
		logger.info("调试信息");

		return "";
	}


	/**
	 * 分群群发
	 *
	 * @return
	 */
	public static String GroupSend() {
		String url = ConstantURL.material_sendgroup_url.replace("ACCESS_TOKEN", accessToken);
		GroupSendNews gsnews = new GroupSendNews();
		Filter filter = new Filter();
		filter.setIs_to_all(true);
		gsnews.setFilter(filter);
		MediaID mpnews = new MediaID();
		mpnews.setMedia_id("3meV6DI_W-GLlHUq6VjhpFc4nJAKAsXLMC47ufutLaBmX9j0CRY6U9B4cIYpAvcH");
		gsnews.setMpnews(mpnews);
		gsnews.setMsgtype("mpnews");

		// 将对象转换成json字符串
		String jsonmater = JSONObject.fromObject(gsnews).toString();
		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST", jsonmater);
		logger.info("调试信息");
		logger.info("调试信息");
		return "";


	}

	/**
	 * 素材列表
	 *
	 * @return
	 */
	public static String batchget_material() {
		String url = ConstantURL.material_list_url.replace("ACCESS_TOKEN", accessToken);

		Material mater = new Material();
		mater.setType("news");
		mater.setOffset(0);
		mater.setCount(20);
		// 将对象转换成json字符串
		String jsonmater = JSONObject.fromObject(mater).toString();
		JSONObject jsonObject = httpRequest(url, "POST", jsonmater);
		logger.info("调试信息");
		logger.info("调试信息");

		return "";


	}


	/**
	 * 获取公众账号用户openID以及AccessToken
	 *
	 * @param code
	 * @return
	 */
	public static OpenAccessToken getOpenAccessTokenInfo(String code, String weixinbz) {

		OpenAccessToken openAccessToken = null;
		try {
			String appID = Constant.wechatMap.get(weixinbz).get("appID");
			String secret = Constant.wechatMap.get(weixinbz).get("appSecret");
			String accessurl = ConstantURL.access_Token_OpenID_url.replace("APPID", appID).replace(
					"SECRET", secret).replace("CODE", code);

			log.error("getOpenAccessTokenInfo:" + accessurl);
			System.out.print("getOpenAccessTokenInfo:" + accessurl);

			JSONObject jsonObject = httpRequest(accessurl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					openAccessToken = new OpenAccessToken();
					openAccessToken.setAccess_token(jsonObject.getString("access_token"));
					openAccessToken.setExpires_in(jsonObject.getInt("expires_in"));
					openAccessToken.setOpenid(jsonObject.getString("openid"));
					openAccessToken.setRefresh_token(jsonObject.getString("refresh_token"));
					openAccessToken.setScope(jsonObject.getString("scope"));
					log.error("access_token:" + jsonObject.getString("access_token"));
					log.error("openid:" + jsonObject.getString("openid"));

					System.out.print("access_token:" + jsonObject.getString("access_token"));
					System.out.print("openid:" + jsonObject.getString("openid"));
				} catch (JSONException e) {
					openAccessToken = null;
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}",
							jsonObject.getInt("errcode"),
							jsonObject.getString("errmsg"));

					System.out.print("获取token失败 errcode:{} errmsg:{}" +
							jsonObject.getInt("errcode") +
							jsonObject.getString("errmsg"));
				}
			}


		} catch (Exception e) {

			logger.error("操作异常", e);
		}

		return openAccessToken;
	}

	/**
	 * 根据openID以及AccessToken获取微信信息
	 *
	 * @return
	 */
	public static WxUserInfo getWxUserInfos(String access_token, String openid) {
		WxUserInfo wxUserInfo = null;
		try {
			String accessurl = ConstantURL.get_wxuserinfo_url.replace("ACCESS_TOKEN", access_token).replace(
					"OPENID", openid);
//			log.error("getWxUserInfos:" + accessurl);
//			System.out.print("getWxUserInfos:" + accessurl);
			JSONObject jsonObject = httpRequest(accessurl, "GET", null);
			// 如果请求成功
			if (null != jsonObject) {
				try {
					wxUserInfo = new WxUserInfo();
					wxUserInfo.setOpenid(jsonObject.getString("openid"));
					wxUserInfo.setNickname(jsonObject.getString("nickname"));
					wxUserInfo.setSex(jsonObject.getString("sex"));
					wxUserInfo.setProvince(jsonObject.getString("province"));
					wxUserInfo.setCity(jsonObject.getString("city"));
					wxUserInfo.setCountry(jsonObject.getString("country"));
					wxUserInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
					wxUserInfo.setPrivilege(jsonObject.getString("privilege"));
					//	wxUserInfo.setUnionid(jsonObject.getString("unionid"));
					log.error("nickname:" + jsonObject.getString("nickname"));
					log.error("headimgurl:" + jsonObject.getString("headimgurl"));
					System.out.print("nickname:" + jsonObject.getString("nickname"));
					System.out.print("headimgurl:" + jsonObject.getString("headimgurl"));

				} catch (JSONException e) {
					wxUserInfo = null;

					log.error("获取用户信息失败 errcode:{} errmsg:{}",
							jsonObject.getString("errcode"),
							jsonObject.getString("errmsg"));
					System.out.print("获取用户信息失败 errcode:{} errmsg:{}" +
							jsonObject.getString("errcode") +
							jsonObject.getString("errmsg"));
				}
			}


		} catch (Exception e) {

			logger.error("操作异常", e);
		}

		return wxUserInfo;


	}


	public static WxUserInfo getWxUserInfoByCode(String code, String weixinbz) {

		OpenAccessToken openAccessToken = getOpenAccessTokenInfo(code, weixinbz);
		WxUserInfo wxUserInfo = getWxUserInfos(openAccessToken.getAccess_token(), openAccessToken.getOpenid());
		return wxUserInfo;
	}



	public static TemplateMsgResult sendTemplate(String accessToken, TemplateMsg templateMsg) {
		String url = ConstantURL.send_Temp_SMS.replace("ACCESS_TOKEN", accessToken);
		log.error("url:"+url);
		TemplateMsgResult templateMsgResult = null;
		TreeMap<String,String> params = new TreeMap<String,String>();
		params.put("access_token", accessToken);
		String jsonparam = JSONObject.fromObject(templateMsg).toString();
		JSONObject jsonObject = httpRequest(url, "POST",jsonparam);
		log.error("jsonObject:"+jsonObject);
		templateMsgResult = new TemplateMsgResult();
		try {
			templateMsgResult.setMsgid(jsonObject.getString("msgid"));
			templateMsgResult.setErrcode(jsonObject.getString("errcode"));
			templateMsgResult.setErrmsg(jsonObject.getString("errmsg"));
		}catch(Exception e){
			templateMsgResult.setErrcode(jsonObject.getString("errcode"));
			templateMsgResult.setErrmsg(jsonObject.getString("errmsg"));
		}
		return templateMsgResult;
	}

	/**
	 *
	 * 扫用户付款码
	 * @return
	 */
	public static WxPayDto payCard(Micropay mp){
		String url = ConstantURL.micropay;
		mp.setAppid(Constant.WXSP_APPID);
		mp.setMch_id(Constant.WXSP_Mchid);

		if(mp.getSub_appid()==null||mp.getSub_appid().equals("")){
			mp.setSub_appid(Constant.WXSP_SUBAPPID);
		}

		if(mp.getSub_mch_id()==null||mp.getSub_mch_id().equals("")){
			mp.setSub_mch_id(Constant.WXSP_SUBMchid);

		}
		mp.setDevice_info(mp.getDevice_info()==null?"":mp.getDevice_info());
		mp.setNonce_str(WeChatUtils.getNonceStr());
		mp.setSpbill_create_ip("");
		mp.setTime_expire("");
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(Constant.WXSP_APPID, Constant.WXSP_AppSecret, Constant.WXSP_Partnerkey);
		SortedMap<String, String> data = new TreeMap<String, String>();
		data.put("appid",mp.getAppid());
		data.put("sub_appid",mp.getSub_appid());
		data.put("attach",mp.getAttach());
		data.put("auth_code",mp.getAuth_code());
		data.put("body",mp.getBody());
		data.put("device_info",mp.getDevice_info());
		data.put("mch_id",mp.getMch_id());
		data.put("sub_mch_id",mp.getSub_mch_id());
		data.put("nonce_str",mp.getNonce_str());
		data.put("out_trade_no",mp.getOut_trade_no());
		data.put("spbill_create_ip",mp.getSpbill_create_ip());
		data.put("time_expire",mp.getTime_expire());
		data.put("total_fee",mp.getTotal_fee()+"");

		// 要签名
		String sign = reqHandler.createSign(data);

		mp.setSign(sign);
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<appid>"+mp.getAppid()+"</appid>");
		sb.append("<sub_appid>"+mp.getSub_appid()+"</sub_appid>");
		sb.append("<attach>"+mp.getAttach()+"</attach>");
		sb.append("<auth_code>"+mp.getAuth_code()+"</auth_code>");
		sb.append("<body>"+mp.getBody()+"</body>");
		sb.append("<device_info>"+mp.getDevice_info()+"</device_info>");
		sb.append("<mch_id>"+mp.getMch_id()+"</mch_id>");
		sb.append("<sub_mch_id>"+mp.getSub_mch_id()+"</sub_mch_id>");
		sb.append("<nonce_str>"+mp.getNonce_str()+"</nonce_str>");
		sb.append("<out_trade_no>"+mp.getOut_trade_no()+"</out_trade_no>");
		sb.append("<spbill_create_ip>"+mp.getSpbill_create_ip()+"</spbill_create_ip>");
		sb.append("<time_expire>"+mp.getTime_expire()+"</time_expire>");
		sb.append("<total_fee>"+mp.getTotal_fee()+"</total_fee>");
		sb.append("<sign>"+sign+"</sign>");
		sb.append("</xml>");


		WxPayDto pto = new GetWxOrderno().getPayNo(url, sb.toString());
		return pto;

	}


	public static String authcodeopenid(String auth_code,String sub_appid,String sub_mch_id){

		String url = ConstantURL.authcodeopenid;
		AuthCodeOpenID aco = new AuthCodeOpenID();
		aco.setAppid(Constant.WXSP_APPID);
		aco.setMch_id(Constant.WXSP_Mchid);
		if(sub_appid==null||sub_appid.equals("")){
			aco.setSub_appid(Constant.WXSP_SUBAPPID);
		}else{
			aco.setSub_appid(sub_appid);
		}

		if(sub_mch_id==null||sub_mch_id.equals("")){
           aco.setSub_mch_id(Constant.WXSP_SUBMchid);
		}else{
			aco.setSub_mch_id(sub_mch_id);
		}
		aco.setAuth_code(auth_code);
		aco.setNonce_str(WeChatUtils.getNonceStr());


		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(aco.getAppid(),Constant.WXSP_AppSecret, Constant.WXSP_Partnerkey);
		SortedMap<String, String> data = new TreeMap<String, String>();
		data.put("appid",aco.getAppid());
		data.put("sub_appid",aco.getSub_appid());
		data.put("mch_id",aco.getMch_id());
		data.put("sub_mch_id",aco.getSub_mch_id());
		data.put("auth_code",aco.getAuth_code());
		data.put("nonce_str",aco.getNonce_str());

		// 要签名
		String sign = reqHandler.createSign(data);
		aco.setSign(sign);


		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<appid>"+aco.getAppid()+"</appid>");
		sb.append("<sub_appid>"+aco.getSub_appid()+"</sub_appid>");
		sb.append("<mch_id>"+aco.getMch_id()+"</mch_id>");
		sb.append("<sub_mch_id>"+aco.getSub_mch_id()+"</sub_mch_id>");
		sb.append("<auth_code>"+aco.getAuth_code()+"</auth_code>");
		sb.append("<nonce_str>"+aco.getNonce_str()+"</nonce_str>");
		sb.append("<sign>"+sign+"</sign>");
		sb.append("</xml>");



		WxPayDto pto = new GetWxOrderno().getPayNo(url, sb.toString());
		if(pto!=null&&pto.getReturn_code().equals("SUCCESS")){
			return pto.getOpenId();
		}


		return null;
	}

	/**
	 * 微信刷脸支付 获取SDK调用凭证
	 * @param mp
	 * @return
	 */
	public static String getWxpayfaceAuthinfo(WxpayFaceAuth mp){
		String url = ConstantURL.get_wxpayface_authinfo;
		mp.setNow(WeChatUtils.getNow());
		mp.setAppid(Constant.WXSP_APPID);
		mp.setMch_id(Constant.WXSP_Mchid);


		if(mp.getDevice_id()==null||mp.getDevice_id().equals("")){
			mp.setDevice_id(WeChatUtils.getNow()+"");
		}

		mp.setSign_type("MD5");
		mp.setVersion("1");
		mp.setNonce_str(WeChatUtils.getNonceStr());

		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(Constant.WXSP_APPID, Constant.WXSP_AppSecret, Constant.WXSP_Partnerkey);
		SortedMap<String, String> data = new TreeMap<String, String>();

		data.put("sub_appid",mp.getSub_appid());
		data.put("sub_mch_id",mp.getSub_mch_id());
	//	data.put("attach",mp.getAttach());

		data.put("appid",mp.getAppid());
		data.put("mch_id",mp.getMch_id());
		data.put("now",mp.getNow()+"");
		data.put("version",mp.getVersion());
		data.put("sign_type",mp.getSign_type());
		data.put("nonce_str",mp.getNonce_str());
		data.put("store_id",mp.getStore_id());
		data.put("store_name",mp.getStore_name());
		data.put("device_id",mp.getDevice_id());
		data.put("rawdata",mp.getRawdata());


		// 要签名
		String sign = reqHandler.createSign(data);

		StringBuilder sb = new StringBuilder();

		//	sb.append("<attach>"+mp.getAttach()+"</attach>");

		sb.append("<xml>");
		sb.append("<appid>"+mp.getAppid()+"</appid>");
		sb.append("<mch_id>"+mp.getMch_id()+"</mch_id>");
		sb.append("<sub_appid>"+mp.getSub_appid()+"</sub_appid>");
		sb.append("<sub_mch_id>"+mp.getSub_mch_id()+"</sub_mch_id>");
		sb.append("<now>"+mp.getNow()+"</now>");
		sb.append("<version>"+mp.getVersion()+"</version>");
		sb.append("<sign_type>"+mp.getSign_type()+"</sign_type>");
		sb.append("<nonce_str>"+mp.getNonce_str()+"</nonce_str>");
		sb.append("<sign>"+sign+"</sign>");
		sb.append("<store_id>"+mp.getStore_id()+"</store_id>");
		sb.append("<store_name>"+mp.getStore_name()+"</store_name>");
		sb.append("<device_id>"+mp.getDevice_id()+"</device_id>");
		sb.append("<rawdata>"+mp.getRawdata()+"</rawdata>");
		sb.append("</xml>");



       //System.out.print(sb.toString());

		WxPayDto pto = new GetWxOrderno().getFaceAuthinfo(url, sb.toString());
		if(pto!=null&&pto.getReturn_code().equals("SUCCESS")){
			return pto.getAuthinfo();
		}
		return "";

	}






	/**
	 * 发起刷脸支付
	 * @param mp
	 * @return
	 */
	public static WxPayDto wxpayface(WxpayFace mp){
		String url = ConstantURL.facepay;
		mp.setAppid(Constant.WXSP_APPID);
		mp.setMch_id(Constant.WXSP_Mchid);
		if(mp.getBody()==null||mp.getBody().equals("")){
			mp.setBody("数字地球-溯源互帮-消费商品");
		}
		mp.setNonce_str(WeChatUtils.getNonceStr());
		mp.setSpbill_create_ip("127.0.0.1");//正常应该写设备的IP


		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(Constant.WXSP_APPID, Constant.WXSP_AppSecret, Constant.WXSP_Partnerkey);
		SortedMap<String, String> data = new TreeMap<String, String>();
		data.put("appid",mp.getAppid());
		data.put("body",mp.getBody());
		data.put("sub_appid",mp.getSub_appid());
		data.put("sub_mch_id",mp.getSub_mch_id());

		data.put("mch_id",mp.getMch_id());
		data.put("nonce_str",mp.getNonce_str());
		data.put("out_trade_no",mp.getOut_trade_no());
		data.put("spbill_create_ip",mp.getSpbill_create_ip());
		data.put("total_fee",mp.getTotal_fee());
		data.put("openid",mp.getOpenid());
		data.put("face_code",mp.getFace_code());


		// 要签名
		String sign = reqHandler.createSign(data);

		mp.setSign(sign);
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<appid>"+mp.getAppid()+"</appid>");
	//	sb.append("<attach>"+mp.getMch_id()+"</attach>");
		sb.append("<body>"+mp.getBody()+"</body>");
//		sb.append("<device_info>1</device_info>");
//		sb.append("<goods_tag></goods_tag>");
		sb.append("<mch_id>"+mp.getMch_id()+"</mch_id>");
		sb.append("<sub_appid>"+mp.getSub_appid()+"</sub_appid>");
		sb.append("<sub_mch_id>"+mp.getSub_mch_id()+"</sub_mch_id>");
		sb.append("<nonce_str>"+mp.getNonce_str()+"</nonce_str>");
		sb.append("<out_trade_no>"+mp.getOut_trade_no()+"</out_trade_no>");
		sb.append("<spbill_create_ip>"+mp.getSpbill_create_ip()+"</spbill_create_ip>");
	//	sb.append("<time_expire></time_expire>");
		sb.append("<total_fee>"+mp.getTotal_fee()+"</total_fee>");
		sb.append("<openid><![CDATA["+mp.getOpenid()+"]]></openid>");
		sb.append("<face_code>"+mp.getFace_code()+"</face_code>");
		sb.append("<sign>"+sign+"</sign>");
		sb.append("</xml>");

		WxPayDto pto = new GetWxOrderno().facePay(url, sb.toString());



		return pto;

	}


	/**
	 *
	 * 根据access_token和OPENID获取用户信息
	 */
	public static String  getUserByToken(String access_Token,String openID){
        String nickname= "";
		String requestUrl = ConstantURL.get_wxuser_url.replace("ACCESS_TOKEN", access_Token).replace(
				"OPENID", openID);

		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

		// 如果请求成功
		if (null != jsonObject) {
			try {
				logger.info("调试信息");
				nickname =jsonObject.getString("nickname");
				log.error("nickname:" + jsonObject.getString("nickname"));
				System.out.print("nickname:" + jsonObject.getString("nickname"));

			} catch (JSONException e) {
				logger.error("操作异常", e);
				nickname= "佚名";


			}
		}
		return nickname;

	}







	public static void main(String[] args) {
		WxpayFace mp = new WxpayFace();
		//mp.setRawdata("H0kvnUgGHKuqflNwtNqCdOVpbO4Fd4u2NRS2uJz5/n080cOlYF5nNnuyVc+UsX0+q3nVrEYAhJFyxeG8MBx/cmZSicjI8UipaehhfFiIHnBZndrCSeGizNs6PSowudTG");
		//WeixinUtil.wxpayface(mp);
	//	WxpayFaceAuth mp1 = new WxpayFaceAuth();
	//	WeixinUtil.getWxpayfaceAuthinfo(mp1);
////		WxUserInfo wxUserInfo = WeixinUtil.getWxUserInfoByCode("0318SqlR1x80971lSKlR1bLjlR18SqlH","wxa1b3f84c027804c3");
//		AccessToken accessToken = WeixinUtil.getAccessToken("wxa1b3f84c027804c3","26d569353c295fa8ad4fcb85a199f631");
//		String token = accessToken.getToken();

//		TreeMap<String,TreeMap<String,String>> params = new TreeMap<String,TreeMap<String,String>>();
//
//		//根据具体模板参数组装
//
//		params.put("first", TemplateMsg.item("您的取餐码是 【】", "#000000"));
//		params.put("keyword1",TemplateMsg.item("居然没事", "#000000"));
//		params.put("keyword2",TemplateMsg.item("外卖", "#000000"));
//		params.put("keyword3",TemplateMsg.item("取餐码", "#000000"));
//		params.put("keyword4",TemplateMsg.item("2017", "#000000"));
//		params.put("keyword5",TemplateMsg.item("15", "#000000"));
//		params.put("remark",TemplateMsg.item("欢迎下载微分金APP，查询订单、兑换现金、买卖产品更方便！", "#000000"));
//
//		TemplateMsg wechatTemplateMsg = new TemplateMsg();
//		wechatTemplateMsg.setTemplate_id(ConstantURL.canyin_temp_id);
//		wechatTemplateMsg.setTouser("oXoxsuPVhnadkv1oIMwVtEXeGY2I");//openID
//		wechatTemplateMsg.setUrl("http://www.impf2010.com/");
//		wechatTemplateMsg.setData(params);
//
//		WeixinUtil.sendTemplate("6_7vrm98QBkgwoThzJS-yNLG-XYMP9rtXHFKdwwiKnnnNRQ4SCKNPcbRdi1XOPpxxEXJucutOgrGLlUoBjCqpf_A0n9bGtAUuo9xrtIWF9MhqIky042N3QvOnn2bJhoNz7q2BOoKD1FgLxmHNeLVMbABACMD",wechatTemplateMsg);

//		System.out.print(wxUserInfo.getOpenid());
//		System.out.print(wxUserInfo.getNickname());
		//String accessToken="c2GZeS52v8egxMk26eoYH0sCwBl1vhsoQkUqiLhwfXaKu4d_UZfSM3cmzTVuodTRRmDUJorvqILvvlprhgbL-yrUGFfc0fZL76-XHX3BhXYSTBdAJADEM";
//
//		String requestUrl= "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+accessToken;
//		Material mater = new Material();
//		mater.setType("image");
//		mater.setOffset(0);
//		mater.setCount(20);
//	    // 将对象转换成json字符串
//	    String jsonmater = JSONObject.fromObject(mater).toString();
//		JSONObject jsonObject = httpRequest(requestUrl, "POST",jsonmater);
//		logger.info("11111111");
//		ArticleMain articleMain = new ArticleMain();
//		Articles articles = new Articles();
//
//        articles.setThumb_media_id("CIfkProlY5c_51atUtEnBjfLeBBXLEdHfMI4bu6pGIMZ0a_kJfc1nXs3YaXYiFjZ");
//		articles.setAuthor("mz");
//		articles.setTitle("Happy Day title");
//		articles.setContent_source_url("www.qq.com");
//		articles.setContent("Happy Day content");
//		articles.setDigest("描述");
//		articles.setShow_cover_pic("1");
//		articleMain.setArticles(new Articles[]{articles});

//
//	    // 将对象转换成json字符串
//	    String jsonmater = JSONObject.fromObject(articleMain).toString();
//		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST",jsonmater);
//		logger.info("调试信息");


//
//		MpnewsMessage mpMessage = new MpnewsMessage();
//		mpMessage.setTowxname("mzdudumz");
//		mpMessage.setMsgtype("mpnews");
//		MediaID mediaID = new MediaID();
//		mediaID.setMedia_id("nEximY2PTVzn2GheQr6gw8dAAMoWf4pSNHrGq4nJJrrZfANUSc-QiVZYP7357ThW");
//		mpMessage.setMpnews(mediaID);
//
//	    // 将对象转换成json字符串
//	    String jsonmater = JSONObject.fromObject(mpMessage).toString();
//		JSONObject jsonObject = WeixinUtil.httpRequest(url, "POST",jsonmater);
//		logger.info("调试信息");

		//WeixinUtil.batchget_material();
		//WeixinUtil.createMaterialMessage();

		//WeixinUtil.MessagePreview();
		//	WeixinUtil.deleteGroupSend();
	//	WeixinUtil.authcodeopenid("134504847527967183");

	}

}