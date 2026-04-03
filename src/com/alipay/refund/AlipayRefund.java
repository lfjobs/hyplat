package com.alipay.refund;

import java.util.HashMap;
import java.util.Map;

import com.alipay.bo.RefundParam;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;


/**
 * 
 * 
 * 有密退款借口
 * @author mz
 *
 */
public class AlipayRefund {
	
	
	public  static String refund(RefundParam rparam){
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
	    sParaTemp.put("partner", AlipayConfig.partner);
	    sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", rparam.getNotify_url());
		sParaTemp.put("seller_email", rparam.getSeller_email());
		sParaTemp.put("refund_date", rparam.getRefund_date());
		sParaTemp.put("batch_no", rparam.getBatch_no());
		sParaTemp.put("batch_num", rparam.getBatch_num());
		sParaTemp.put("detail_data", rparam.getDetail_data());
		
		//建立请求
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		return sHtmlText;

	}
	
	
	
	
	

}
