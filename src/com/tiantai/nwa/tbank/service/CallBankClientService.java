package com.tiantai.nwa.tbank.service;

import com.tiantai.nwa.tbank.bean.CallBankRequestBean;
import com.tiantai.nwa.tbank.bean.CallBankReturnBean;

public interface CallBankClientService {

	CallBankReturnBean CallBankClientHttp(CallBankRequestBean reqBean);
	CallBankReturnBean CallBankClientBIOSocket(CallBankRequestBean reqBean);
	CallBankReturnBean CallBankClientNIOSocket(CallBankRequestBean reqBean);
}
