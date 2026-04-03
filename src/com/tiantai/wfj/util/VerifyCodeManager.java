package com.tiantai.wfj.util;

import groovy.util.logging.Log4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j
@Component
public class VerifyCodeManager {



    private final Map<String, CodeEntry> codeMap = new ConcurrentHashMap<>();
    private static final long VALID_TIME = 5 * 60 * 1000; // 5分钟
    private static final long SEND_INTERVAL = 60 * 1000;  // 60秒

    public String generateCode(String mobile,String code) {
        long now = System.currentTimeMillis();
        CodeEntry entry = codeMap.get(mobile);
        if (entry != null && now - entry.lastSentTime < SEND_INTERVAL) {
            throw new RuntimeException("请勿频繁发送验证码，请稍后再试");
        }

        codeMap.put(mobile, new CodeEntry(code, now + VALID_TIME, now));
        return code;
    }

    public boolean verifyCode(String mobile, String inputCode) {
        CodeEntry entry = codeMap.get(mobile);
        if (entry == null) return false;
        long now = System.currentTimeMillis();
        if (now > entry.expireTime) {
            codeMap.remove(mobile);
            return false;
        }
        System.out.println("输入code:"+inputCode+"保存的code:"+entry.code);
        boolean match = entry.code.equals(inputCode);
        if (match) codeMap.remove(mobile);
        return match;
    }

    @Scheduled(fixedRate = 15*60 * 1000)
    public void cleanExpiredCodes() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, CodeEntry>> iterator = codeMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, CodeEntry> entry = iterator.next();
            if (entry.getValue().expireTime < now) {
                iterator.remove();
            }
        }

    }
}
