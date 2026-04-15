package com.wechatpay.utils;

import java.io.*;
import java.lang.reflect.Method;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.core.io.ClassPathResource;
import org.xml.sax.InputSource;

import com.wechatpay.bo.WxPayResult;

import hy.ea.util.Constant;

import javax.crypto.Cipher;
import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

public class WeChatUtils {
	private static int socketTimeout = 10000;// 连接超时时间，默认10秒
	private static int connectTimeout = 30000;// 传输超时时间，默认30秒
	private static RequestConfig requestConfig;// 请求器的配置
	private static CloseableHttpClient httpClient;// HTTP请求器


	/**
	 * 获取随机字符串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
	    // 随机数
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		return strTime + strRandom;
		
   /*      Random random=new Random();
		
		return MD5Util.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());*/

	}

	/**
	 * 获取日期
	 * @return
	 */
	public static int getNow(){
//		String dateStr = "";
//		Date date = new Date();
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		try {
//			dateStr = dateFormat.format(date);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		int times = 0;
//		try {
//			times = (int) ((Timestamp.valueOf(dateStr).getTime())/1000);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		int times = new Long(System.currentTimeMillis()/1000L).intValue();
          System.out.print(times);
		return times;

	}

	/**
	 * 元转换成分
	 * 
	 * @param amount
	 * @return
	 */
	public static String getMoney(String amount) {
		if (amount == null) {
			return "";
		}
		// 金额转化为分为单位
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}
	
	public static String changeF2Y(String fen) throws Exception{  
		Long amount  = Long.parseLong(fen);
		 String CURRENCY_FEN_REGEX = "\\-?[0-9]+";    
	        
        if(!amount.toString().matches(CURRENCY_FEN_REGEX)) {    
            throw new Exception("金额格式有误");    
        }    
            
        int flag = 0;    
        String amString = amount.toString();    
        if(amString.charAt(0)=='-'){    
            flag = 1;    
            amString = amString.substring(1);    
        }    
        StringBuffer result = new StringBuffer();    
        if(amString.length()==1){    
            result.append("0.0").append(amString);    
        }else if(amString.length() == 2){    
            result.append("0.").append(amString);    
        }else{    
            String intString = amString.substring(0,amString.length()-2);    
            for(int i=1; i<=intString.length();i++){    
//                if( (i-1)%3 == 0 && i !=1){
//                    result.append(",");
//                }
                result.append(intString.substring(intString.length()-i,intString.length()-i+1));    
            }    
            result.reverse().append(".").append(amString.substring(amString.length()-2));    
        }    
        if(flag == 1){    
            return "-"+result.toString();    
        }else{    
            return result.toString();    
        }    
    }    

	/**
	 * xml解析成MAP
	 * 
	 * @param xml
	 * @return
	 */

	public static Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			Document doc = (Document) sb.build(source);
			Element root = doc.getRootElement();// 指向根节点
			List<Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return retMap;
	}

	/**
	 * map解析成Mxml
	 *
	 * @param map
	 * @return
	 */
	public static String mapToXml(Map<String, String> map)throws Exception{
		org.dom4j.Document d = DocumentHelper.createDocument();
		org.dom4j.Element root = d.addElement("xml");
		Set<String> keys = map.keySet();
		for(String key:keys) {
			root.addElement(key).addText(map.get(key));
		}
		StringWriter sw = new StringWriter();
		XMLWriter xw = new XMLWriter(sw);
		xw.setEscapeText(false);
		xw.write(d);
		return sw.toString();
	}

	/**
	 * 
	 * xml解析成WxPayResult
	 * 
	 * @param xml
	 * @return
	 */
	public static WxPayResult parseXmlToResult(String xml) {
		Map m = parseXmlToList2(xml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(m.get("total_fee").toString());
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());
		wpr.setAttach(m.get("attach").toString());

		return wpr;
	}


	
	/**
	 * 
	 * 通知结果验证签名
	 * @param xmlResult
	 * @param wechatbz
	 * @return
	 */
	public static boolean verifyWeixinNotify(String xmlResult,String wechatbz) { 
		
		Map<Object, Object> map = parseXmlToList2(xmlResult);  
        SortedMap<String, String> parameterMap = new TreeMap<String, String>();  
        String sign = (String) map.get("sign");  
        for (Object keyValue : map.keySet()) {  
            if(!keyValue.toString().equals("sign")){  
                parameterMap.put(keyValue.toString(), map.get(keyValue).toString());  
            }  
              
        }  
     
		RequestHandler reqHandler = new RequestHandler(null, null);
		String appid = Constant.wechatMap.get(wechatbz).get("appID");
		String appsecret = Constant.wechatMap.get(wechatbz).get("appSecret");
		String partnerkey = Constant.wechatMap.get(wechatbz).get("partnerkey");
		reqHandler.init(appid, appsecret, partnerkey);

     		String createSign = reqHandler.createSign(parameterMap);

        if(createSign.equals(sign)){  
            return true;  
        }else{  
            return false;  
        }  
          
    }
	
	/**
	 * 
	 * PC端微信通知结果验证签名
	 * @param xmlResult
	 * @param wechatbz
	 * @return
	 * @throws Exception 
	 */
	public static boolean verifyWeChatPayNotify(String xmlResult,String wechatbz) throws Exception
	{
		Map<String, String> map = GetWxOrderno.xmlToMap(xmlResult);
		String sign = map.get("sign");
		String createSign = RequestHandler.generateSignature(map, Constant.wechatMap.get(wechatbz).get("partnerkey"));
		if(createSign.equals(sign))
		{
			return true;
		}
		return false;
	}

	

	/**
	 * 
	 * 回调通知后返回微信状态码信息
	 * 
	 * @param return_code
	 *            返回状态码
	 * @param return_msg
	 *            返回信息
	 * @return
	 */
	public static String backWeixinResult(String return_code, String return_msg) {
		String resXml = "<xml>" + "<return_code><![CDATA[" + return_code + "]]></return_code>" + "<return_msg><![CDATA["
				+ return_msg + "]]></return_msg>" + "</xml> ";

		return resXml;
	}

	/**
	 * 通过Https往API post xml数据
	 *
	 * @param url API地址
	 * @param xmlObj 要提交的XML数据对象
	 * @param mchId 商户ID
	 * @param certPath 证书位置
	 * @return
	 */
	public static String postData(String url, String xmlObj, String mchId, String certPath) {
		// 加载证书
		try {
			initCert(mchId, certPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);
		// 根据默认超时限制初始化requestConfig
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
		// 设置请求器的配置
		httpPost.setConfig(requestConfig);
		try {
			HttpResponse response = null;
			try {
				response = httpClient.execute(httpPost);
			} catch (IOException e) {
				e.printStackTrace();
			}
			HttpEntity entity = response.getEntity();
			try {
				result = EntityUtils.toString(entity, "UTF-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			httpPost.abort();
		}
		return result;
	}

	/**
	 * 加载证书
	 *
	 * @param mchId 商户ID
	 * @param certPath 证书位置
	 * @throws Exception
	 */
	public static void initCert(String mchId, String certPath) throws Exception {
		// 证书密码，默认为商户ID
		String key = mchId;
		// 证书的路径
		String path = certPath;
		// 指定读取证书格式为PKCS12
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		// 读取本机存放的PKCS12证书文件
		//ClassPathResource cp = new ClassPathResource(path);

		//InputStream instream = cp.getInputStream();
		FileInputStream  instream = new FileInputStream(new File(path));
		try {
			// 指定PKCS12的密码(商户ID)
			keyStore.load(instream, key.toCharArray());
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, key.toCharArray()).build();
		SSLConnectionSocketFactory sslsf =
				new SSLConnectionSocketFactory(sslcontext, new String[] {"TLSv1"}, null,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
	}

	/**
	 * @Title: getRequestIp
	 * @Description: 获取用户的ip地址
	 * @param:
	 * @return:
	 */
	public static String getRequestIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.indexOf(",") != -1) {
			String[] ips = ip.split(",");
			ip = ips[0].trim();
		}
		return ip;
	}


	public static PublicKey getPubKey(String publicKeyPath, String keyAlgorithm){
		PublicKey publicKey = null;
		InputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(publicKeyPath);
			publicKey = getPublicKey(inputStream,keyAlgorithm);
		} catch (Exception e) {
			e.printStackTrace();//EAD PUBLIC KEY ERROR
		} finally {
			if (inputStream != null){
				try {
					inputStream.close();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return publicKey;
	}
	public static PublicKey getPublicKey(InputStream inputStream, String keyAlgorithm) throws Exception {
		try
		{

			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(decodeBase64(sb.toString()));
			KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
			//下行出错  java.security.spec.InvalidKeySpecException: java.security.InvalidKeyException: IOException: DerInputStream.getLength(): lengthTag=127, too big.
			PublicKey publicKey = keyFactory.generatePublic(pubX509);
			return publicKey;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("READ PUBLIC KEY ERROR:", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				inputStream = null;
				throw new Exception("INPUT STREAM CLOSE ERROR:", e);
			}
		}
	}

	/***
	 * decode by Base64
	 */
	public static byte[] decodeBase64(String input) throws Exception{
		Class clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("decode", String.class);
		mainMethod.setAccessible(true);
		Object retObj=mainMethod.invoke(null, input);
		return (byte[])retObj;
	}


	/***
	 * 银行卡加密
	 */
	public static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
		int keyByteSize = keyLength / 8;
		int encryptBlockSize = keyByteSize - reserveSize;
		int nBlock = plainBytes.length / encryptBlockSize;
		if ((plainBytes.length % encryptBlockSize) != 0) {
			nBlock += 1;
		}
		ByteArrayOutputStream outbuf = null;
		try {
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
			for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
				int inputLen = plainBytes.length - offset;
				if (inputLen > encryptBlockSize) {
					inputLen = encryptBlockSize;
				}
				byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
				outbuf.write(encryptedBlock);
			}
			outbuf.flush();
			return outbuf.toByteArray();
		} catch (Exception e) {
			throw new Exception("ENCRYPT ERROR:", e);
		} finally {
			try{
				if(outbuf != null){
					outbuf.close();
				}
			}catch (Exception e){
				outbuf = null;
				throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);
			}
		}
	}
}
