package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.common.Response;
import com.tiantai.wfj.vo.WxResult;

import java.io.IOException;

public interface WeChatMiniService {
    public Object Code2Session(String code);

    public Object getPhone(String code);

    Response<Object> MiniLogin(String code, String phoneCode);

    Response<Object> WxH5Login(String code);

    Response<Object> bindPhone(WxResult wxResult, String phone,String channel);
}
