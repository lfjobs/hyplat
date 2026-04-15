package com.batch.chinapay.meth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.batch.chinapay.bean.BatchPayBean;
import com.batch.chinapay.util.AscHexSwitch;
import com.batch.chinapay.util.Config;
import com.batch.chinapay.util.IntegralityUtil;
import com.batch.chinapay.util.MsgUtil;
import com.batch.chinapay.util.SignData;

import chinapay.PrivateKey;
import chinapay.SecureLink;
import chinapay.util.DigestMD5;
import chinapay.util.SecureUtil;

public class BatchPay {
	//private static int tmpResultLength = -1;
	private static final String oraBatchSer = "chinapay.oraBatchSer.url";
	private static final String oraDownFileSer = "chinapay.oraDownFileSer.url";
	private static final String FileStatQuery = "chinapay.FileStatQuery.url";
	/**生成批量交易信息*/
	public static BatchPayBean createFile(){
		String MerId = null;
		Properties config = Config.getInstance().getProperties();
		MerId = config.getProperty(Config.KEY_CHINAPAY_MERID);

		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sf2 = new SimpleDateFormat("HHmmss");
		Date dt = new Date();

		String MerSeqId = sf2.format(dt); // 批次号
		String TransDate = sf1.format(dt); // 交易日期

		int n = 3; // 批量交易数量

		// 文件的第一行，包含：商户号，批次号，总笔数，总金额，各项用“|”分割
		String plain = MerId + "|" + MerSeqId + "|"+n+"|3000";
		System.out.println("文件头：" + plain);
		plain += "\r\n"
			+ TransDate
			+ "|"
			+ "201409031117341"
			+ "|6228481190350963516|民生1|民生银行|上海|上海|浦东支行|1000|付款|";
		plain += "\r\n"
			+ TransDate
			+ "|"
			+ TransDate + MerSeqId + 1
			+ "|6228481190350963516|民生2|0305|上海|上海|浦东支行|1000|付款|";
		plain += "\r\n"
			+ TransDate
			+ "|"
			+ TransDate + MerSeqId + 3
			+ "|6228481190350963516|民生3|民生银行|上海|上海|浦东支行|1000|付款|";

		
		System.out.println("文件内容：");
		System.out.println(plain);

		// 文件命名规范：MERID_YYYYMMDD_XXXXXX.TXT
		String fileName = MerId + "_" + TransDate + "_" + MerSeqId + ".txt";
//		String fileName = "null";
		System.out.println("fileName=[" + fileName + "]");

		BatchPayBean charge = new BatchPayBean();
		charge.setPlain(plain);
		charge.setData(fileName);
		return charge;
		/*BatchSer.jsp*/
	}
	/**批量文件上传 @return 获取bean*/
	public static Map<String,Object> batchSer(String fileName,String fileContent) throws IOException{
		Map<String,Object> map=new HashMap<String, Object>();
		BatchPayBean charge=null;
		String merId = null;
		String MerKeyPath = null;
		String PubKeyPath = null;
		Properties config = Config.getInstance().getProperties();
		merId = config.getProperty(Config.KEY_CHINAPAY_MERID);
		MerKeyPath = config.getProperty(Config.KEY_CHINAPAY_MERKEY_FILEPATH);
		PubKeyPath = config.getProperty(Config.KEY_CHINAPAY_PUBKEY_FILEPATH);
		String url = config.getProperty(oraBatchSer);

		System.out.println(fileContent);
		String filepath = config.getProperty(Config.FilePath) + fileName;
		File file = new File(filepath);

		// 对存款批量交易信息进行签名
		String chkValue1 = null;
		try {
			chkValue1 = SecureUtil.digitalSign(merId, fileContent, MerKeyPath);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		System.out.println("txt文件签名密文:" + chkValue1);
		String plain = fileContent + chkValue1;

		// 将ORA批量信息写入临时文件
		FileOutputStream fos = new FileOutputStream(filepath);
		fos.write(plain.getBytes("GBK"));
		fos.flush();
		fos.close();

		// 文件上传准备
		HttpClient httpClient = null;
		PostMethod postMethod = null;
		BufferedReader reader = null;
		InputStream resInputStream = null;
		try {
			httpClient = new HttpClient();
			httpClient.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
			postMethod = new PostMethod(url);

			byte[] temSen = null;

			try {
				temSen = getBytes(file);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			int temSenLength = temSen.length;
			System.out.println("temSen=[" + temSenLength + "]");
			String tian = new String(temSen, "GBK");
			System.out.println("tian=[" + tian + "]");

			// 对需要上传的字段签名
			String chkValue2 = null;
			chkValue2 = DigestMD5.MD5Sign(merId, fileName, plain.getBytes("GBK"), MerKeyPath);
			System.out.println("文件上传签名内容:" + chkValue2);

			httpClient.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
			// 获得管理参数
			HttpConnectionManagerParams managerParams = httpClient
					.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(40000);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(120000);
			postMethod.setRequestHeader("Connection", "close");
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(1, false));
			NameValuePair[] data = { new NameValuePair("merId", merId), new NameValuePair("fileName", fileName), new NameValuePair("fileContent", tian), new NameValuePair("chkValue", chkValue2) };

			postMethod.setRequestBody(data);

			int statusCode = 0;
			try {
				statusCode = httpClient.executeMethod(postMethod);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				resInputStream = postMethod.getResponseBodyAsStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			reader = new BufferedReader(new InputStreamReader(resInputStream));
			String tempBf = null;
			StringBuffer html = new StringBuffer();
			while ((tempBf = reader.readLine()) != null) {

				html.append(tempBf);
			}
			String result = html.toString();
			System.out.println("返回数据" + "[" + result + "]");
			int dex = result.lastIndexOf("=");
			String tiakong = result.substring(0, dex + 1);
			System.out.println("验签明文：" + "[" + tiakong + "]");

			// 拆分页面应答数据
			String str[] = result.split("&");
			System.out.println(str.length);
			int Res_Code = str[0].indexOf("=");
			int Res_message = str[1].indexOf("=");
			int Res_chkValue = str[2].indexOf("=");

			String responseCode = str[0].substring(Res_Code + 1);
			String message = str[1].substring(Res_message + 1);
			String ChkValue = str[2].substring(Res_chkValue + 1);
			System.out.println("responseCode=" + responseCode);
			System.out.println("message=" + message);
			System.out.println("chkValue=" + ChkValue);

			// 对收到的ChinaPay应答传回的域段进行验签
			boolean res = DigestMD5.MD5Verify(tiakong, ChkValue,PubKeyPath); 
			System.out.println(res);

			charge = new BatchPayBean();
			charge.setResponseCode(responseCode);
			charge.setMessage(message);
			charge.setPlain(tiakong);
			charge.setData(result);
			charge.setChkValue(ChkValue);
			map.put("bean", charge);

			if (responseCode.equals("20FM")) {
				System.out.println("批量文件接口上传成功！");
			}
			if (res) {
				System.out.println("验签数据正确!");
				map.put("result", 0);
			} else {
				System.out.println("签名数据不匹配！");
				map.put("result", 1);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 释放httpclient
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
			if (null != httpClient) {
				SimpleHttpConnectionManager manager = (SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager();
				if (null == manager) {
					httpClient.getHttpConnectionManager().closeIdleConnections(
							0);
				} else {
					manager.shutdown();
				}
			}
			if (reader != null) {
				reader.close();
			}
			if (resInputStream != null) {
				resInputStream.close();
			}
		}
		return map;
	}
	/*回盘文件下载*/
	public static Map<String,Object> downloadFile() throws IOException{
		Map<String,Object> map=new HashMap<String, Object>();
		String merId = null;
		String MerKeyPath = null;
		String PubKeyPath = null;
		BatchPayBean charge = null;
		Properties config = Config.getInstance().getProperties();
		merId = config.getProperty(Config.KEY_CHINAPAY_MERID);
		MerKeyPath = config.getProperty(Config.KEY_CHINAPAY_MERKEY_FILEPATH);
		PubKeyPath = config.getProperty(Config.KEY_CHINAPAY_PUBKEY_FILEPATH);
		String url = config.getProperty(oraDownFileSer);

		String TransDate = "TransDate";
		String MerSeqId = "MerSeqId";

		// 原始文件命名规范：MERID_YYYYMMDD_XXXXXX.TXT
		String orFileName = merId + "_" + TransDate + "_" + MerSeqId + ".txt";
		System.out.println("orFileName=[" + orFileName + "]");

//		String orFileName = "80808029000000320120827154456198";
//		System.out.println("orFileName=[" + orFileName + "]");
		
		// 回盘文件命名规范：MERID_YYYYMMDD_XXXXXX_H.TXT
		String fileName = merId + "_" + TransDate + "_" + MerSeqId + "_H.txt";
		System.out.println("fileName=[" + fileName + "]");

		// 签名明文
		String signMsg = merId + orFileName + fileName;

		// 文件上传准备
		HttpClient httpClient = null;
		PostMethod postMethod = null;
		BufferedReader reader = null;
		InputStream resInputStream = null;
		try {
			httpClient = new HttpClient();
			httpClient.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
			postMethod = new PostMethod(url);

			// 对需要上传的字段签名
			String chkValue2 = null;
			String msgMd5 = DigestMD5.Hex2Asc(DigestMD5.getMD5(signMsg.getBytes("GBK")).length, DigestMD5.getMD5(signMsg.getBytes("GBK")));
			int KeyUsage = 0;
			PrivateKey key = new PrivateKey();
			key.buildKey(merId, KeyUsage, MerKeyPath);
			SecureLink sl = new SecureLink(key);
			chkValue2 = sl.Sign(msgMd5);
			System.out.println("回盘文件下载接口签名内容:" + chkValue2);

			httpClient.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
			// 获得管理参数
			HttpConnectionManagerParams managerParams = httpClient
					.getHttpConnectionManager().getParams();
			// 设置连接超时时间(单位毫秒)
			managerParams.setConnectionTimeout(40000);
			// 设置读数据超时时间(单位毫秒)
			managerParams.setSoTimeout(120000);
			postMethod.setRequestHeader("Connection", "close");
			postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(1, false));
			NameValuePair[] data = { new NameValuePair("merId", merId),new NameValuePair("fileName", fileName), new NameValuePair("orFileName", orFileName), new NameValuePair("chkValue", chkValue2) };

			postMethod.setRequestBody(data);

			int statusCode = 0;
			try {
				statusCode = httpClient.executeMethod(postMethod);
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				resInputStream = postMethod.getResponseBodyAsStream();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 接收返回报文
			reader = new BufferedReader(new InputStreamReader(resInputStream,
					"GBK"));
			String tempBf = null;
			StringBuffer html = new StringBuffer();
			while ((tempBf = reader.readLine()) != null) {

				html.append(tempBf);
			}

			String result = html.toString();
			System.out.println("result=[" + result + "]");

			// 拆分页面应答数据
			int dex = result.lastIndexOf("=");
			String tiakong = result.substring(0, dex + 1);
			String ChkValue = result.substring(dex + 1);

			String str[] = result.split("&");
			System.out.println(str.length);
			charge = new BatchPayBean();
			// 验签明文
			String plainData = "";

			if (str.length == 5) {

				// 回盘文件下载成功
				int Res_merId = str[0].indexOf("=");
				int Res_orFileName = str[1].indexOf("=");
				int Res_filename = str[2].indexOf("=");
				int Res_fileData = str[3].indexOf("=");

				String MerId = str[0].substring(Res_merId + 1);
				String OrFileName = str[1].substring(Res_orFileName + 1);
				String Filename = str[2].substring(Res_filename + 1);
				String FileData = str[3].substring(Res_fileData + 1);
				System.out.println("merId=" + MerId);
				System.out.println("orFileName=" + OrFileName);
				System.out.println("filename=" + Filename);
				System.out.println("FileData=" + FileData);
				MsgUtil msgUtil = new MsgUtil();

				String resultText = new String(msgUtil.decodeInflate(FileData.getBytes()), "GBK");
				plainData = resultText;
				System.out.println("回盘文件内容：");
				System.out.println(resultText);
				
				// 对下载到的回盘文件内容进行验签
				int index = resultText.length() - 256;
				System.out.println("回盘文件内容验签明文："
						+ resultText.substring(0, index));
				System.out.println("回盘文件内容验签密文：" + resultText.substring(index));
				boolean re = false;
				re = SecureUtil.validateSign("999999999999999", resultText.substring(0, index), resultText.substring(index),PubKeyPath);
				if (re) {
					System.out.println("回盘文件内容验签正确!");
				} else {
					System.out.println("回盘文件内容验签失败！");
				}

				charge.setResponseCode(MerId);
				charge.setPlain(resultText);
				charge.setMessage("回盘文件下载成功！");
				charge.setData(result);
				charge.setChkValue(ChkValue);

			} else {

				// 回盘文件下载失败
				System.out.println("验签明文：");
				plainData = tiakong;
				System.out.println(plainData);
				int Res = str[0].indexOf("=");
				String ResponseCode = str[0].substring(Res + 1);
				String Message = str[1].substring(str[1].indexOf("=") + 1);
				System.out.println("responseCode = " + ResponseCode);
				charge.setResponseCode(ResponseCode);
				charge.setMessage(Message);
				charge.setPlain(tiakong);
				charge.setData(result);
			}

			// 对收到的ChinaPay应答传回的域段进行验签
			boolean res = DigestMD5.MD5Verify(plainData, ChkValue, PubKeyPath);
			/*request.setAttribute("chargeInput", charge);
*/
			if (res) {
				System.out.println("验签数据正确!");
				map.put("result", 0);
				/*request.getRequestDispatcher("./Response.jsp").forward(request,
						response);*/
			} else {
				System.out.println("签名数据不匹配！");
				map.put("result", 1);
				/*request.getRequestDispatcher("./VerifyFail.jsp").forward(
						request, response);*/
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
			map.put("bean", charge);
			// 释放httpclient
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
			if (null != httpClient) {
				SimpleHttpConnectionManager manager = (SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager();
				if (null == manager) {
					httpClient.getHttpConnectionManager().closeIdleConnections(
							0);
				} else {
					manager.shutdown();
				}
			}
			if (reader != null) {
				reader.close();
			}
			if (resInputStream != null) {
				resInputStream.close();
			}
		}
		return map;
	}
	
	public static Map<String,Object> fileStatQueryReturn() throws IOException{
		Map<String,Object> map=new HashMap<String, Object>();
		String PubKeyPath = null;
		String pay_url = null;
		Properties config = Config.getInstance().getProperties();
		PubKeyPath = config.getProperty(Config.KEY_CHINAPAY_PUBKEY_FILEPATH);
		pay_url = config.getProperty(FileStatQuery);

		System.out.println(PubKeyPath);


		String merId = "merId"; // 15
		String fileName = "fileName";
		String type = "queryType";
		String chkValue = "chkValue";

		HttpClient httpClient = new HttpClient();
		System.out.println("HttpClient方法创建！");
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
		System.out.println(url);
		PostMethod postMethod = new PostMethod(url);
		System.out.println("Post方法创建！");
		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("merId", merId), new NameValuePair("fileName", fileName), new NameValuePair("queryType", type), new NameValuePair("chkValue", chkValue), };

		System.out.println(data);

		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		try {
			httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 读取内容
		InputStream resInputStream = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 对收到的ChinaPay应答传回的域段进行验签
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				resInputStream));
		String tempBf = null;

		StringBuffer html = new StringBuffer();
		while ((tempBf = reader.readLine()) != null) {
			html.append(tempBf);
		}

		String resultData = new String(html.toString().getBytes(), "GBK");
		System.out.println("response message：" + resultData);

		String[] splits = resultData.split("&");
		String resVerifyData = null;
		String responseChkValue = null;

		BatchPayBean pay = new BatchPayBean();
		if (splits.length == 3) {// 返回错误报文
			String resCode = splits[0];
			System.out.println("resCode=[" + resCode.split("=")[1] + "]");
			String resMessage = splits[1];
			System.out.println("resMessage=[" + resMessage.split("=")[1] + "]");
			responseChkValue = splits[2].split("=")[1];
			System.out.println("responseChkValue=[" + responseChkValue + "]");
			StringBuffer str = new StringBuffer();
			String format = "&";
			str.append("responseCode=").append(resCode.split("=")[1]).append(format)
			   .append("message=").append(resMessage.split("=")[1]).append(format)
			   .append("chkValue=");
			resVerifyData = str.toString();

			pay.setData(resMessage.split("=")[1]);
			pay.setResponseCode(resCode.split("=")[1]);
		}
		
		if (splits.length == 5) {
			String resMerId = splits[0];
			System.out.println("merId=[" + resMerId.split("=")[1] + "]");
			String resFileName = splits[1];
			System.out.println("resFileName=[" + resFileName.split("=")[1] + "]");
			String resQueryStat = splits[2];
			System.out.println("resQueryStat=[" + resQueryStat.split("=")[1] + "]");
			String resStat = splits[3];
			System.out.println("resStat=[" + resStat.split("=")[1] + "]");
			responseChkValue = splits[4].split("=")[1];
			System.out.println("responseChkValue=[" + responseChkValue + "]");

			StringBuffer str = new StringBuffer();
			String format = "&";
			str.append("merId=").append(resMerId.split("=")[1]).append(format)
			   .append("fileName=").append(resFileName.split("=")[1]).append(format)
			   .append("queryType=").append(resQueryStat.split("=")[1]).append(format)
			   .append("stat=").append(resStat.split("=")[1]).append(format)
			   .append("chkValue=");
			resVerifyData = str.toString();

			pay.setData(resVerifyData);
			pay.setResponseCode(resStat.split("=")[1]);
		}
		System.out.println("verifyData=[" + resVerifyData + "],resChkValue=[" + responseChkValue + "]");
		String ctmpResMd5 = AscHexSwitch.Hex2Asc(IntegralityUtil.getMD5(resVerifyData.getBytes("GBK")).length, IntegralityUtil.getMD5(resVerifyData.getBytes("GBK")));
		System.out.println("ctmpResMd5=[" + ctmpResMd5 + "]");
		SignData signData = new SignData();
		boolean verfiyResult = signData.verifyForCP("999999999999999", ctmpResMd5, responseChkValue, PubKeyPath);
		System.out.println("[verfiyResult=" + verfiyResult + "]");

		pay.setMerId(merId);
		/*request.setAttribute("payInput", pay);*/
		map.put("bean", pay);
		if (!verfiyResult) {
			System.out.println("验签失败");
			map.put("result", 1);
			return map;
			/*request.getRequestDispatcher("./QueryFail.jsp").forward(request, response);
			*/
		}

		System.out.println("验签成功");
		map.put("result",0);
		return map;
		/*request.getRequestDispatcher("./FileStatQueryReturn.jsp").forward(request, response);*/
	}
	public static BatchPayBean fileStatQuery()throws IOException{
		String MerKeyPath = null;
		String merId = null;
		Properties config = Config.getInstance().getProperties();
		MerKeyPath = config.getProperty(Config.KEY_CHINAPAY_MERKEY_FILEPATH);
		merId = config.getProperty(Config.KEY_CHINAPAY_MERID);

		// 查询订单数据准备
		String fileName = "fileName";// 8
		String queryType = "queryType";

		StringBuffer tmpPalin = new StringBuffer();
		tmpPalin.append(merId).append(fileName).append(queryType);
		String ctmpRetMd5 = AscHexSwitch.Hex2Asc(
				IntegralityUtil.getMD5(tmpPalin.toString().getBytes("GBK")).length,
				IntegralityUtil.getMD5(tmpPalin.toString().getBytes("GBK")));
		SignData signData = new SignData();
		String chkValue = signData.signForCP(merId, ctmpRetMd5, MerKeyPath);
		System.out.println("签名内容:"+ chkValue);
		
		BatchPayBean charge = new BatchPayBean();
		charge.setMerId(merId);
		charge.setFileName(fileName);
		charge.setFileMatter(queryType);
		charge.setChkValue(chkValue);

		/*request.setAttribute("chargeInput", charge);*/
		/*request.getRequestDispatcher("./FileStatQueryCommit.jsp").forward(request, response);*/
		return charge;
		
	}
	
	/**
	 * @author note by Shine
	 * @20131127
	 * The method of compress
	 * */
	public static byte[] getBytes(File f) throws Exception {
		FileInputStream in = new FileInputStream(f);
		byte[] b = new byte[4 * 1024];
		int n;
		byte[] tmpResult = null;
		byte[] base64Result = null;
		while ((n = in.read(b)) != -1) {
			if (tmpResult == null) {
				tmpResult = getSumByte(null, 0, b, n);
			} else {
				tmpResult = getSumByte(tmpResult, tmpResult.length, b, n);
			}
		}
		//tmpResultLength = tmpResult.length;
		MsgUtil msgUtil = new MsgUtil();
		base64Result = msgUtil.deflateEncode(tmpResult);
		System.out.println("参数:==[" + base64Result + "]");
		in.close();
		return base64Result;
	}

	public static byte[] getSumByte(byte[] baseValue, int orLength, byte[] streamByte,
			int length) {
		byte[] result = new byte[orLength + length];
		for (int i = 0; i < orLength; i++) {
			result[i] = baseValue[i];
		}
		for (int i = 0; i < length; i++) {
			result[orLength + i] = streamByte[i];
		}
		return result;
	}
}
