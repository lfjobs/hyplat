package com.tiantai.wfj.util;

public class CodeEntry {

        String code;
        long expireTime;
        long lastSentTime;
        CodeEntry(String code, long expireTime, long lastSentTime){
            this.code = code;
            this.expireTime = expireTime;
            this.lastSentTime = lastSentTime;
        }

}
