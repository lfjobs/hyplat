package mobile.tiantai.android.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
//封装调第三方接口GET方法
public class HttpGetResult {
	private static final Logger logger = LoggerFactory.getLogger(HttpGetResult.class);
	public static String httpGet(String url) {
		String result = null;
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(60000);  
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000); 
	
		try {
			int statusCode = httpClient.executeMethod(getMethod);// 执行getMethod
			if (statusCode != HttpStatus.SC_OK) {
			}
			result = ConvertStream2Json(getMethod.getResponseBodyAsStream());
		} catch (HttpException e) {
		} catch (IOException e) {
		} finally {
			getMethod.releaseConnection(); // 释放连接
		}
		return new String(result).trim();
	}

	public static String ConvertStream2Json(InputStream inputStream) {
		String jsonStr = "";
		// ByteArrayOutputStream相当于内存输出流
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		// 将输入流转移到内存输出流中
		try {
			while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, len);
			}
			// 将内存流转换为字符串
			jsonStr = new String(out.toByteArray(),"UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("操作异常", e);
		}
		return jsonStr;
	}

}
