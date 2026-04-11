package com.wechatpay.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wechatpay.bo.StatementResult;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.utils.http.HttpClientConnectionManager;

public class GetWxOrderno
{
	private static final Logger logger = LoggerFactory.getLogger(GetWxOrderno.class);
  public static DefaultHttpClient httpclient;

  static
  {
    httpclient = new DefaultHttpClient();
    httpclient = (DefaultHttpClient)HttpClientConnectionManager.getSSLInstance(httpclient);
  }


  
  
 /**
  * 
  * 根据code获取AccessToken以及openID
  * 
  * @param url
  * @param xmlParam
  * 
  * https://api.weixin.qq.com/sns/oauth2/access_token?
  * appid=wx8888888888888888
  * &secret=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  * &code=00b788e3b42043c8459a57a8d8ab5d9f
  * &grant_type=authorization_code
  * @return
  */
 public static Map<String,String> getOpenID(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  Map<String,String> map = new HashMap<String,String>();
    try {
    	if(xmlParam!=null&&!xmlParam.equals("")) {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		}
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	     logger.info("调试信息");
	    if(jsonStr.indexOf("errcode")!=-1){
	    	logger.info("存在errcode");
			map.put("errcode","1");
	    	return map;
	    }
	   // jsonStr:{"access_token":"OezXcEiiBSKSxW0eoylIeMWBGcMTk9dhKjkQnNGXqlB5xpc8Siu7g_4lSNe8-K6CcaMtceImaeV2UslRjy4ZoYKEEKrRKUIaTIDXmb489xaPEBNPRiYJ9lnZ9QztwhG7N4OnqGRImtaHwvvuaBziUg","expires_in":7200,"refresh_token":"OezXcEiiBSKSxW0eoylIeMWBGcMTk9dhKjkQnNGXqlB5xpc8Siu7g_4lSNe8-K6Ct6bINSOkyz-hR8G9x0aaJyL9w1Lp7xK2eV77BIygfORk4cRZtm2bmO48304AF1o5rK0OgTXWaDQOaxPUbPwHXA","openid":"owWE7t_mSfyvUEt6iQRW3R_QpKbA","scope":"snsapi_base"}
	    JSONObject jsonObject = new JSONObject(jsonStr);
	    logger.info("调试信息");
	    String openid  =  jsonObject.getString("openid");
		String access_token = jsonObject.getString("access_token");
		map.put("openid",openid);
		map.put("access_token",access_token);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error("操作异常", e);
	}
	return map;
 }
  
  
  
  
  /**
   *description:获取预支付id
   *@param url
   *@param xmlParam
   *@return
   * @author ex_yangxiaoyi
   * @see
   */
  public static WxPayDto getPayNo(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String prepay_id = "";
	  String err_code = "";
	  WxPayDto payDto = new WxPayDto();
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	     logger.info("值：{}", jsonStr);
	     Map map = doXMLParse(jsonStr);
	     if(jsonStr.indexOf("FAIL")!=-1){
	    	payDto.setPrepay_id(prepay_id);
	    	 payDto.setErr_code((String) map.get("err_code"));

	  
	     }else{
	    	 payDto.setPrepay_id((String) map.get("prepay_id")); 
	    	 payDto.setErr_code(err_code);
	    	 payDto.setOpenId((String) map.get("openid"));
		 }
		 payDto.setReturn_code((String) map.get("return_code"));
		 payDto.setResult_code((String) map.get("result_code"));


	 } catch (Exception e) {
		logger.error("操作异常", e);
	}
	return payDto;
  }
  
  
  
  
  public static WxPayDto getSearchResult(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);

	  WxPayDto payDto = new WxPayDto();
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	     logger.info("************************************");
	     logger.info("查询微信订单返回json字符串：: {}", jsonStr);
	     parseTextTable(jsonStr);
	    
	     if(jsonStr.indexOf("return_code")!=-1){
	    	  Map map = doXMLParse(jsonStr);
			  String return_code  = (String) map.get("return_code");
			  String return_msg  = (String) map.get("return_msg");
			  String trade_state = (String) map.get("trade_state");
			  payDto.setReturn_code(return_code);
			  payDto.setReturn_msg(return_msg);
			  payDto.setTrade_state(trade_state);
			 if(return_code.equals("SUCCESS")&&return_msg.equals("OK")&&trade_state.equals("SUCCESS")){
                 System.out.print((String) map.get("time_end"));
				 payDto.setTime_end((String) map.get("time_end")); 
				 payDto.setOut_trade_no((String) map.get("out_trade_no"));
				 payDto.setTransaction_id((String) map.get("transaction_id"));
				 payDto.setTotalFee(WeChatUtils.changeF2Y((String) map.get("total_fee"))+"");
				 payDto.setResult_code((String) map.get("result_code"));
			 }
		  }
		  

	} catch (Exception e) {
		
		logger.error("操作异常", e);
	}
	return payDto;
  }
  
  /**
   *description:获取扫码支付连接
   *@param url
   *@param xmlParam
   *@return
   * @author ex_yangxiaoyi
   * @see
   */
  public static String getCodeUrl(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
	  String code_url = "";
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	    if(jsonStr.indexOf("FAIL")!=-1){
	    	return code_url;
	    }
	    //Map map = doXMLParse(jsonStr);
	    Map<String, String> map = xmlToMap(jsonStr);
	    code_url  = (String) map.get("code_url");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.error("操作异常", e);
	}
	return code_url;
  }


	/**
	 * 刷脸调用凭证
	 * @param
	 * @param
	 * @return
	 */
	public static WxPayDto getFaceAuthinfo(String requestUrl,String xmlParam){
		WxPayDto payDto = new WxPayDto();
		Map map = null;
		try {

				URL url = new URL(requestUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setUseCaches(false);
				// 设置请求方式（GET/POST）
				conn.setRequestMethod("POST");
				conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
				// 当outputStr不为null时向输出流写数据
				if (null != xmlParam) {
					OutputStream outputStream = conn.getOutputStream();
					// 注意编码格式
					outputStream.write(xmlParam.getBytes("UTF-8"));
					outputStream.close();
				}
				// 从输入流读取返回内容
				InputStream inputStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String str = null;
				StringBuffer buffer = new StringBuffer();
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				logger.info("调试信息");
				 map = GetWxOrderno.doXMLParse(buffer.toString());


			if(buffer.toString().indexOf("SUCCESS")!=-1){
				payDto.setAuthinfo((String) map.get("authinfo"));
			    payDto.setExpires_in(Integer.parseInt(map.get("expires_in").toString()));
			}
			payDto.setReturn_code((String) map.get("return_code"));



				// 释放资源
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
				inputStream = null;
				conn.disconnect();

			} catch (Exception ce) {
				payDto.setReturn_code((String) map.get("return_code"));
                clogger.error("操作异常", e);
			}

		return payDto;
	}

	/**
	 * 刷脸调用凭证
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	public static WxPayDto facePay(String url,String xmlParam){
		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
		String err_code = "";
		WxPayDto payDto = new WxPayDto();
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
			logger.info("值：{}", jsonStr);
			Map map = doXMLParse(jsonStr);
			if(jsonStr.indexOf("FAIL")!=-1){
				payDto.setErr_code((String) map.get("err_code"));
			}else{
				payDto.setErr_code(err_code);

			}
			payDto.setReturn_code((String) map.get("return_code"));
			if(payDto.getReturn_code().equals("SUCCESS")){
				payDto.setResult_code((String) map.get("result_code"));
				if(payDto.getResult_code().equals("SUCCESS")){
					payDto.setTransaction_id((String) map.get("transaction_id"));

				}
			}




		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return payDto;
	}



	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
            	logger.error("操作异常", ex);
            }
            return data;
        } catch (Exception ex) {
            throw ex;
        }

    }

  public static InputStream String2Inputstream(String str) {
	  ByteArrayInputStream stream = null;
	  try {
		 stream = new ByteArrayInputStream(str.getBytes("utf-8"));
	} catch (UnsupportedEncodingException e) {
		logger.error("操作异常", e);
	}
		return stream;
		
	}
  
  
  
  /**
   * 
   * 
   * json字符串解析成Map
   * @param jsonString
   * @return
   */
  public static Map parseJson(String  jsonString){

	  JSONObject jsonObject = new JSONObject(jsonString);
      
      Map result = new HashMap();
      Iterator iterator = jsonObject.keys();
      String key = null;
      String value = null;
      
      while (iterator.hasNext()) {

          key = (String) iterator.next();
          value = jsonObject.getString(key);
          result.put(key, value);
          logger.info("调试信息");
          logger.info("调试信息");

      }
      return result;
	 
	  
  }
  
  
  
  /**
   * 
   * 
   * 获取微信对账单
   * @param url
   * @param xmlParam
   * @return
   */
  public static String getWxStatement(String url,String xmlParam){
	  DefaultHttpClient client = new DefaultHttpClient();
	  client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	  HttpPost httpost= HttpClientConnectionManager.getPostMethod(url);
     try {
		 httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
		 HttpResponse response = httpclient.execute(httpost);
	     String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
	     logger.info("值：{}", jsonStr);
	     parseTextTable(jsonStr);
	     if(jsonStr.indexOf("return_code")!=-1){
	    	  Map map = doXMLParse(jsonStr);
			  String return_code  = (String) map.get("return_code");
			  String return_msg  = (String) map.get("return_msg");
			  logger.info("值：{}", return_code);
			  logger.info("值：{}", return_msg);
			  return return_code;
		  }
		  

	} catch (Exception e) {
		
		logger.error("操作异常", e);
	}
	return null;
  }
  
  
  
  public static  List<StatementResult> parseTextTable(String jsonStr){
	  List<StatementResult> list =  new ArrayList<StatementResult>();
	  String[] arry = jsonStr.split(",");
	  logger.info("调试信息");
	  StatementResult stateResult = null;
	  List<String> strlist = new ArrayList<String>();
	  for (int i = 0; i < arry.length-10; i++) {
		if(i>=23){
		if(i%23==0){
			stateResult = new StatementResult();
			for (int j = i; j <=i+23; j++) {
				if(j<arry.length){

				stateResult.setAppid(arry[j]);

				  
				}
			}
			if(stateResult.getAppid()!=null&&!stateResult.getAppid().equals("")){
			   list.add(stateResult);
			}
			
			
		}
	 }
	  }
	  
	  return null;
	  
	  
	  
  }
  
}