package com.daifu.chinapay.meth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.daifu.chinapay.model.bean.TransactionBean;
import com.daifu.chinapay.util.Config;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单笔代付
 * 
 * @author 2582565296@qq.com
 *
 */
public class SinglePay {
	private static final Logger logger = LoggerFactory.getLogger(SinglePay.class);

    private final static Logger logger= LoggerFactory.getLogger(SinglePay.class);

	// 第二步，单笔代付-交易
	public static Map<String, Object> payMeth(TransactionBean tb) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", 1);// 未初始化
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		PubKeyPath = config.getProperty(Config.KEY_CHINAPAY_PUBKEY_FILEPATH);
		pay_url = config.getProperty(Config.PaymentUrl); 
		// 获取页面数据
		String merId = tb.getMerId();
		String merDate = tb.getMerDate();
		String merSeqId = tb.getMerSeqId();
		String cardNo = tb.getCardNo();
		String usrName = tb.getUserName();
		String openBank = tb.getOpenBank();
		String prov = tb.getProv();
		String city = tb.getCity();
		String transAmt = tb.getTransAmt();
		String purpose = tb.getPurpose();
		String flag = tb.getFlag();
		String version = tb.getVersion();
		String chkValue = tb.getChkValue();
		String signFlag = "1";
		String subBank = tb.getSubBank();
		String termType = tb.getTermType();

        logger.error("verify:" + merId + merDate + merSeqId + cardNo + usrName + openBank+ prov+ city+ transAmt+ purpose+ flag+ version+ chkValue+ signFlag+ subBank+ termType);

		// 连接Chinapay控台
		HttpClient httpClient = new HttpClient();
        logger.error("HttpClient方法创建！");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
        logger.error(url);
		PostMethod postMethod = new PostMethod(url);
        logger.error("Post方法创建！");
		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("merId", merId), new NameValuePair("merDate", merDate),
				new NameValuePair("merSeqId", merSeqId), new NameValuePair("cardNo", cardNo),
				new NameValuePair("usrName", new String(usrName.getBytes("GBK"))), new NameValuePair("openBank", new String(openBank.getBytes("GBK"))),
				new NameValuePair("prov", new String(prov.getBytes("GBK"))), new NameValuePair("city", new String(city.getBytes("GBK"))),
				new NameValuePair("transAmt", transAmt), new NameValuePair("purpose", new String(purpose.getBytes("GBK"))),
				new NameValuePair("subBank", new String(subBank.getBytes("GBK"))), new NameValuePair("flag", flag),
				new NameValuePair("version", version), new NameValuePair("chkValue", chkValue),
				new NameValuePair("termType", termType), new NameValuePair("signFlag", signFlag) };

		logger.error(data.toString());

		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		try {
			httpClient.executeMethod(postMethod);
		} catch (HttpException e1) {
			logger.error("操作异常", e1);
		} catch (Exception e1) {
			logger.error("操作异常", e1);
		}

		// 读取内容
		InputStream resInputStream = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
		} catch (IOException e1) {
			logger.error("操作异常", e1);
		}
		// 处理内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
		String tempBf = null;
		StringBuffer html = new StringBuffer();
		while ((tempBf = reader.readLine()) != null) {

			html.append(tempBf);
		}
		String resMes = html.toString();
		int dex = resMes.lastIndexOf("&");

		// 拆分页面应答数据
		String str[] = resMes.split("&");

		// 提取返回数据
		if (str.length == 10) {
			int Res_Code = str[0].indexOf("=");
			int Res_merId = str[1].indexOf("=");
			int Res_merDate = str[2].indexOf("=");
			int Res_merSeqId = str[3].indexOf("=");
			int Res_cpDate = str[4].indexOf("=");
			int Res_cpSeqId = str[5].indexOf("=");
			int Res_transAmt = str[6].indexOf("=");
			int Res_stat = str[7].indexOf("=");
			int Res_cardNo = str[8].indexOf("=");
			int Res_chkValue = str[9].indexOf("=");

			String responseCode = str[0].substring(Res_Code + 1);
			String MerId = str[1].substring(Res_merId + 1);
			String MerDate = str[2].substring(Res_merDate + 1);
			String MerSeqId = str[3].substring(Res_merSeqId + 1);
			String CpDate = str[4].substring(Res_cpDate + 1);
			String CpSeqId = str[5].substring(Res_cpSeqId + 1);
			String TransAmt = str[6].substring(Res_transAmt + 1);
			String Stat = str[7].substring(Res_stat + 1);
			String CardNo = str[8].substring(Res_cardNo + 1);
			String ChkValue = str[9].substring(Res_chkValue + 1);

			String msg = resMes.substring(0, dex);
			String plainData = new String(Base64.encode(msg.getBytes()));

			// 传入显示页面的数据准备
			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(responseCode);
			pay.setMerId(MerId);
			pay.setMerDate(MerDate);
			pay.setMerSeqId(MerSeqId);
			pay.setCpDate(CpDate);
			pay.setCpSeqId(CpSeqId);
			pay.setTransAmt(TransAmt);
			pay.setStat(Stat);
			pay.setCardNo(CardNo);
			pay.setData(resMes);
			pay.setTermType(termType);
			
			map.put("bean", pay);
			// 对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			int KeyUsage = 0;
			PrivateKey key = new PrivateKey();
			try {
				buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
			if (!buildOK) {
                logger.error("build error!");
				map.put("result", 2);// build error
				return map;
			}

			SecureLink sl = new SecureLink(key);
			res = sl.verifyAuthToken(plainData, ChkValue);
			if (res) {
				map.put("result", 0);
			} else {
			    logger.error("签名数据不匹配！");
				map.put("result", 3);// 签名数据不匹配！
			}
			return map;
		}

		// 交易失败应答
		if (str.length == 2) {
			int Res_Code = str[0].indexOf("=");
			int Res_chkValue = str[1].indexOf("=");

			String responseCode = str[0].substring(Res_Code + 1);
			String ChkValue = str[1].substring(Res_chkValue + 1);

			String plainData = str[0];
			String plainData1 = new String(Base64.encode(plainData.getBytes()));

			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(responseCode);
			pay.setData(resMes);
			map.put("bean", pay);
			// 对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			int KeyUsage = 0;
			PrivateKey key = new PrivateKey();
			try {
				buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
			if (!buildOK) {
			    logger.error(" build error!");
				map.put("result", 2);// build error!
				return map;
			}

			SecureLink sl = new SecureLink(key);
			res = sl.verifyAuthToken(plainData1, ChkValue);
			if (res) {
				map.put("result", -1);
			} else {
			    logger.error("签名数据不匹配！");
				map.put("result", 3);// 签名数据不匹配！
			}
			return map;
		}

		return map;
	}

	// 第一步，单笔代付生成订单
	/**
	 * 商户日期： （8位数字，不填系统默认为当前日期<br/>
	 * 交易流水号： （16位数字，不填由系统自动产生）<br/>
	 * 收款账号： （必填，银行卡号或者存折号）<br/>
	 * 收款人姓名： （必须与开户时的姓名完全一致）<br/>
	 * 开户银行： （必填，开户银行名称）<br/>
	 * 省份： （必填，收款人开户行所在省）<br/>
	 * 城市： （必填，收款人开户行所在地区）<br/>
	 * 交易金额（分）： （12位数字,不填默认金额为1分)）<br/>
	 * 用途： （必填）<br/>
	 * 支行： （开户支行名称,该字段可以不填）<br/>
	 * 付款标志： 00对私 01对公<br/>
	 * 渠道类型：非必须（可为空）07:互联网 08：移动端 <br/>
	 * 
	 * @return result 0成功。其他失败,<br/>
	 *         1:未初始化2:私钥初始化失败！3:buildkey error<br/>
	 *         bean 实体（TransactionBean）
	 */
	public static Map<String, Object> genterOrder(TransactionBean tb) {
		tb.setVersion("20150304");// （8位数字，Ora接口的版本号）
		String MerKeyPath = null;
		String merId = null;
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("result", 1);// 未初始化
		try {
			Properties config = Config.getInstance().getProperties();
			MerKeyPath = config.getProperty(Config.KEY_CHINAPAY_MERKEY_FILEPATH); // chinapay.merkey.filepath
			merId = config.getProperty(Config.KEY_CHINAPAY_MERID);
		} catch (Exception e) {
		    logger.error("私钥初始化失败！");
			ret.put("result", 2);// 私钥初始化失败！
			return ret;
		}

		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMdd");

		Date dt = new Date();
		String merDate = tb.getMerDate();
		if (merDate == null || merDate.isEmpty())
			merDate = sf2.format(dt);

		String merSeqId = tb.getMerSeqId();
		if (merSeqId == null || merSeqId.isEmpty()) {
			merSeqId = "00" + sf.format(dt);
		}

		// 签名数据组装
		TransactionBean pay = new TransactionBean();
		pay.setMerId(merId);
		pay.setMerDate(merDate);
		pay.setMerSeqId(merSeqId);
		pay.setCardNo(tb.getCardNo());
		pay.setUserName(tb.getUserName());
		pay.setOpenBank(tb.getOpenBank());
		pay.setProv(tb.getProv());
		pay.setCity(tb.getCity());
		pay.setTransAmt(tb.getTransAmt());
		pay.setPurpose(tb.getPurpose());
		pay.setSubBank(tb.getSubBank());
		pay.setFlag(tb.getFlag());
		pay.setVersion(tb.getVersion());
		pay.setTermType(tb.getTermType());

		String Data = pay.toString();
		String plainData = new String(Base64.encode(Data.getBytes()));

		// 签名
		String chkValue = null;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		boolean flage = key.buildKey(merId, KeyUsage, MerKeyPath);
		if (flage == false) {
		    logger.error("buildkey error!");
			ret.put("result", 3);// buildkey error
			return ret;
		} else {
			SecureLink sl = new SecureLink(key);
			chkValue = sl.Sign(plainData);
		}
		;
		pay.setChkValue(chkValue);
		logger.error("成功");
		ret.put("result", 0);// 成功
		ret.put("bean", pay);
		return ret;
	}

	/**
	 * 查询单笔代付订单信息
	 * @param merDate 商户日期yyyyMMdd,数字，定长8位
	 * @param merSeqId 流水号，数字，变长16位
	 * @return TransactionBean实体
	 * @throws IOException
	 */
	public static TransactionBean findOrder(String merDate, String merSeqId) throws IOException {
		String MerKeyPath = null;
		String merId = null;
		String pay_url = null;
		String PubKeyPath = null;
		String signFlag = "1";
		//String version = "20150304";
		String version = "20090501";
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(Config.KEY_CHINAPAY_MERKEY_FILEPATH);
		PubKeyPath = config.getProperty(Config.KEY_CHINAPAY_PUBKEY_FILEPATH);
		merId = config.getProperty(Config.KEY_CHINAPAY_MERID);
		
		
		pay_url = config.getProperty(Config.queryUrl);
		//String Data = merId + merDate + merSeqId + tb.getVersion();
		String Data = merId + merDate + merSeqId + version;
		String plainData1 = new String(Base64.encode(Data.getBytes()));
		// 签名
		String chkValue = null;
		int KeyUsage1 = 0;
		PrivateKey key1 = new PrivateKey();
		key1.buildKey(merId, KeyUsage1, MerKeyPath);
		SecureLink sl = new SecureLink(key1);
		chkValue = sl.Sign(plainData1);
		TransactionBean tb = new TransactionBean();
		tb.setVersion(version);// （8位数字，Ora接口的版本号）
		tb.setMerDate(merDate);
		tb.setMerSeqId(merSeqId);
		tb.setMerId(merId);
		tb.setChkValue(chkValue);

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
		PostMethod postMethod = new PostMethod(url);
		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("merId", merId), new NameValuePair("merDate", merDate),
				new NameValuePair("merSeqId", merSeqId), new NameValuePair("version", version),
				new NameValuePair("chkValue", chkValue), new NameValuePair("signFlag", signFlag) };
		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		try {
			httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			logger.error("操作异常", e);
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
		// 读取内容
		InputStream resInputStream = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
		// 处理内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(resInputStream));
		String tempBf = null;
		StringBuffer html = new StringBuffer();
		while ((tempBf = reader.readLine()) != null) {

			html.append(tempBf);
		}
		String resMes = html.toString();
		int dex = resMes.lastIndexOf("|");
		String Res_Code = resMes.substring(0, 3);
		// 提取返回数据
		if (Res_Code.equals("000")) {
			//修改的部分20171019
			//String Res_stat = resMes.substring(dex-2,dex-1);
			String temp = resMes.substring(dex-1, dex);
			String Res_stat = "";
			if("|".endsWith(temp)){
				Res_stat = resMes.substring(dex-2,dex-1);
			}else{
				Res_stat = resMes.substring(dex-10,dex-9);
			}

			String Res_chkValue = resMes.substring(dex + 1);

			String plainData = resMes.substring(0, dex + 1);

			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(Res_Code);
			pay.setPurpose(plainData);
			pay.setStat(Res_stat);
			pay.setData(resMes);
			pay.setChkValue(Res_chkValue);

			// 对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			int KeyUsage = 0;
			PrivateKey key = new PrivateKey();
			try {
				buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
			if (!buildOK) {
				return pay;
			}

			SecureLink s2 = new SecureLink(key);
			res = s2.verifyAuthToken(Data, Res_chkValue);
			if (res) {
				return pay;
			} else {
				return pay;
			}
		}

		else {
			String Res_chkValue = resMes.substring(dex + 1);

			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(Res_Code);
			pay.setData(resMes);

			// 对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			int KeyUsage = 0;
			PrivateKey key = new PrivateKey();
			try {
				buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
			if (!buildOK) {
				return pay;
			}

			SecureLink s3 = new SecureLink(key);
			res = s3.verifyAuthToken(Data, Res_chkValue);
			if (res) {
				return pay;
			} else {
				return pay;
			}
		}
	}
	
	/**
	 * 查询备付金余额
	 * @return
	 * @throws IOException
	 */
	public static TransactionBean BalanceQuery() throws IOException {

		String MerKeyPath = null;
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(Config.KEY_CHINAPAY_MERKEY_FILEPATH);
		PubKeyPath = config.getProperty(Config.KEY_CHINAPAY_PUBKEY_FILEPATH);
		pay_url = config.getProperty(Config.BalanceQueryUrl);

		String merId = config.getProperty(Config.KEY_CHINAPAY_MERID);
		String version = "20090501";
		String signFlag = "1";

		String Data = merId + version;
		String plainData = new String(Base64.encode(Data.getBytes()));

		// 签名
		String chkValue = null;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		key.buildKey(merId, KeyUsage, MerKeyPath);
		SecureLink sl = new SecureLink(key);
		chkValue = sl.Sign(plainData);

		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
		PostMethod postMethod = new PostMethod(url);
		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("merId", merId),
				new NameValuePair("version", version),
				new NameValuePair("chkValue", chkValue),
				new NameValuePair("signFlag", signFlag) };


		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		try {
			httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		// 读取内容
		InputStream resInputStream = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		// 处理内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				resInputStream));
		String tempBf = null;
		StringBuffer html = new StringBuffer();
		try {
			while ((tempBf = reader.readLine()) != null) {

				html.append(tempBf);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e1);
		}
		String resMes = html.toString();
		int dex = resMes.lastIndexOf("|");
		String Res_Code = resMes.substring(0, 3);

		// 提取返回数据
		if (Res_Code.equals("000")) {
			String Res_merId = resMes.substring(4, 19);
			String Res_merAmt = resMes.substring(20, dex);
			String Res_chkValue = resMes.substring(dex + 1);


			String plainData1 = resMes.substring(0, dex + 1);
			String plainData2 = new String(Base64.encode(plainData1.getBytes()));

			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(Res_Code);
			pay.setMerId(Res_merId);
			pay.setMerAmt(Res_merAmt);
			pay.setChkValue(Res_chkValue);
			pay.setData(resMes);

			// 对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			try {
				buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
			if (!buildOK) {
				logger.error("build error!");
				return null;
			}
			res = sl.verifyAuthToken(plainData2, Res_chkValue);
			return pay;
		} else {
			String Res_chkValue = resMes.substring(dex + 1);

			String plainData1 = resMes.substring(0, dex + 1);
			String plainData2 = new String(Base64.encode(plainData1.getBytes()));

			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(Res_Code);
			pay.setData(resMes);
			// request.setAttribute("payInput", pay);

			// 对收到的ChinaPay应答传回的域段进行验签
			boolean buildOK = false;
			boolean res = false;
			try {
				buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
			if (!buildOK) {
				logger.error("build error!");
				return null;
			}
			res = sl.verifyAuthToken(plainData2, Res_chkValue);
			return pay;
		}
	}
	
	public static String toUnicode(String zhStr) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < zhStr.length(); i++) {
			char c = zhStr.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}
}
