package com.tiantai.wfj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MqttListener implements ApplicationListener {

    private MqttService mqttService = null;

    public MqttListener() {
        this.mqttService = MqttService.getInstance();
    }

    /**
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//     try {
//         logger.info("发一条mqtt消息试试");
//        mqttService.pubSeq("1", "2");
//      } catch (MqttException e) {
//          throw new RuntimeException(e);
//
//       }
    }

}