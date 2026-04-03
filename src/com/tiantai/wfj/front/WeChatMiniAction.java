package com.tiantai.wfj.front;

import com.tiantai.wfj.service.WeChatMiniService;
import hy.base.action.BaseAction;
import hy.ea.util.StringUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Controller
@Scope("prototype")
public class WeChatMiniAction {
    @Resource
    private WeChatMiniService weChatMiniService;


    private String code;

    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;

    private String phoneCode;
    private Object result;
    private Object jsonResult; // 用于 JSON 输出
    public Object Code2Session(){
         result = weChatMiniService.Code2Session(code);

        return "success";
    }
    public Object getPhone(){
        result= weChatMiniService.getPhone(phoneCode);

        return "success";
    }

    public Object MiniLogin(){
        if (StringUtil.isNotEmpty(code)&&StringUtil.isNotEmpty(phoneCode)){
            jsonResult=weChatMiniService.MiniLogin(code,phoneCode);
        }
        return "json";
    }

    public Object WxH5Login(){
          result= weChatMiniService.WxH5Login(code);
        return "success";
    }

    public Object wxVerify(){
        result=new ByteArrayInputStream("success".getBytes(StandardCharsets.UTF_8));
        return "success";
    }


    /**
     * 参数	描述
     * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * timestamp	时间戳
     * nonce	随机数
     * echostr	随机字符串
     * @return
     */
    public Object wxOpenVerify(){
        if (StringUtil.isNotEmpty(echostr)){
            result=new ByteArrayInputStream(echostr.getBytes(StandardCharsets.UTF_8));
        }else {
            result=new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
        }
        return "success";
    }

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }


    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public Object getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(Object jsonResult) {
        this.jsonResult = jsonResult;
    }
}
