package hy.ea.finance.service.impl;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.config.AlipayConfig;

import hy.ea.finance.service.AlipayRefundService;
import net.sf.json.JSONObject;
@Service
public class AlipayRefundServiceImpl implements AlipayRefundService {
	/**
	 * 
	 * out_trade_no<字符长度64>订单支付时传入的商户订单号,不能和 trade_no同时为空。
	 * trade_no<字符长度64>支付宝交易号，和商户订单号不能同时为空
	 * refund_amount<字符长度9>需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数
	 * refund_reason<字符长度256>退款的原因说明
	 * out_request_no<字符长度64>标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
	 * */
	@Override
	public String refund(String out_trade_no, String trade_no, String refund_amount, String refund_reason) {
		// TODO Auto-generated method stub
		JSONObject jsonObj = new JSONObject();
		
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		jsonObj.accumulate("out_trade_no", out_trade_no);
		jsonObj.accumulate("trade_no", trade_no);
		jsonObj.accumulate("refund_amount", refund_amount);
		jsonObj.accumulate("refund_reason", refund_reason);
		String res = jsonObj.toString();
		request.setBizContent(res); 
		 try {
				AlipayConfig.response = AlipayConfig.alipayClient.execute(request);
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String aa=null;
		 if(AlipayConfig.response.isSuccess()){
			 aa="成功";
		} else {
			 aa="失败";
		
		}
		return aa;
	}

}
