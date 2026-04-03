package com.wechat.bo;

/**
 * Created by Administrator on 2017/12/11.
 */
public class TemplateMsgResult {
    private String errcode;//0
    private String errmsg;//OK
    private String msgid;//模板ID

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
}
