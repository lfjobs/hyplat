package com.tiantai.wfj.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.emay.mina.util.Base64;
import org.eclipse.paho.client.mqttv3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MqttService {
	private static final Logger logger = LoggerFactory.getLogger(MqttService.class);

    // 连接参数
    private static final String serverURI = "tcp://post-cn-1ls43nmas01.mqtt.aliyuncs.com:1883";
    private static final int keepAliveInterval = 60;
    private static final boolean cleanSession = false;
    private static final boolean autoReconnect = true;
    private static final String clientId = "GID_avm@@@ttsw-server"+System.currentTimeMillis();
    private static final String instanceId = "post-cn-1ls43nmas01";
    private static final String accessKey = "LTAIhoOYsMdxmhy1";
    private static final String secretKey = "DUXMnK26k9kzwqXu9A6oi2KV8UlMn2";
    // private  final String baseTopic = "sys";

    // 订阅主题
    private static final String MQTT_THING_WILL = "sys/+/+/thing/will";
    private static final String MQTT_THING_SERVICE_OPEN_RELAY_REPLY = "sys/+/+/thing/service/open_relay_reply";

    private static volatile MqttService instance;
    private MqttClient mqttClient;

    private MqttService() {
        try {
            connect();
        } catch (MqttException e) {
            logger.info("售货柜服务器MQTT客户端初始化连接失败！");
            logger.error("操作异常", e);
        }
    }

    public static MqttService getInstance() {
        if (instance == null) {
            synchronized (MqttService.class) {
                if (instance == null) {
                    instance = new MqttService();
                }
            }
        }
        return instance;
    }

    private final MqttCallbackExtended mCallback = new MqttCallbackExtended() {
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
            if (reconnect) {
                //logger.info("Reconnected to : : {}", serverURI);
                // Because Clean Session is true, we need to re-subscribe
            } else {
                logger.info("connectComplete");
                logger.info("Connected to: : {}", serverURI);
                subscribeToTopic();
            }
        }

        @Override
        public void connectionLost(Throwable cause) {
            //logger.info("mqtt客户端连接丢失:: {}", cause.getLocalizedMessage());
            //causlogger.error("操作异常", e);
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            logger.info("调试信息");
            String data = new String(message.getPayload());
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            System.out.print("消息发送成功");
        }
    };

    private void connect() throws MqttException {
        mqttClient = new MqttClient(serverURI, clientId);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName("Signature|" + accessKey + "|" + instanceId);
        options.setPassword(macSignature(clientId, secretKey).toCharArray());
        options.setKeepAliveInterval(keepAliveInterval);
        options.setCleanSession(cleanSession);
        options.setAutomaticReconnect(autoReconnect);

        mqttClient.setCallback(mCallback);

        logger.info("Start Connecting to : {}", serverURI);
        mqttClient.connect(options);
    }

    public void disconnect() {
        try {
            if (mqttClient != null && mqttClient.isConnected()) {
                mqttClient.disconnect();
                logger.info("Disconnected from MQTT broker");


            }
        } catch (MqttException e) {
            System.err.println("Error disconnecting from MQTT broker: " + e.getMessage());
        }
    }

    private void subscribeToTopic() {
        logger.info("Subscribe to topic");
        try {
            mqttClient.subscribe(new String[]{
                    MQTT_THING_WILL,
                    MQTT_THING_SERVICE_OPEN_RELAY_REPLY
            }, new int[]{
                    0, 0
            }, new IMqttMessageListener[]{
                    new IMqttMessageListener() {
                        @Override
                        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                            logger.info(" 设备下线遗嘱 ");
                            logger.info("Message arrived - Topic: : {}", s);
                            logger.info("调试信息");
                        }
                    },
                    new IMqttMessageListener() {
                        @Override
                        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {


                            logger.info(" 继电器开关结果反馈 ");
                            logger.info("Message arrived - Topic: : {}", s);
                            logger.info("调试信息");
                        }
                    }
            });
        } catch (MqttException ex) {
            logger.error("Exception whilst subscribing");
            logger.error("操作异常", ex);
        }
    }

    /**
     * 打开继电器
     *
     * @throws MqttException
     */
    public void openRelay(int num, String posNum) throws MqttException {
        logger.info("openRelay");
        String topic = "sys/avm/" + posNum + "/thing/service/open_relay";
//        String[] commands = new String[]{
//                "A00101A2",
//                "A00201A3",
//                "A00301A4",
//                "A00401A5",
//                "A00501A6",
//                "A00601A7",
//                "A00701A8",
//                "A00801A9"
//        };
        publishMessage(topic, num + "");
    }

    /**
     * 关闭继电器
     *
     * @throws MqttException
     */
    public void closeRelay(int num, String posNum) throws MqttException {
        logger.info("closeRelay");
//        String[] commands = new String[]{
//                "A00100A1",
//                "A00200A2",
//                "A00300A3",
//                "A00400A4",
//                "A00500A5",
//                "A00600A6",
//                "A00700A7",
//                "A00800A8"
//        };
        String topic = "sys/avm/" + posNum + "/thing/service/close_relay";
        publishMessage(topic, num + "");
    }

    /**
     * 打开继电器
     *
     * @throws MqttException
     */
    public void openWeb(String posNum, String url) throws MqttException {
        logger.info("url: {}", url);
        String topic = "sys/avm/" + posNum + "/thing/service/open_web";
        publishMessage(topic, url);
    }

    /**
     * 发送语音
     *
     * @throws MqttException
     */
    public void pubAudio(String posNum, String audio) throws MqttException {
        logger.info("audio: {}", audio);
        String topic = "sys/avm/" + posNum + "/thing/service/pub_audio";
        publishMessage(topic, audio);
    }

    /**
     * 发送序列号
     *
     * @throws MqttException
     */
    public void pubSeq(String posNum, String seq) throws MqttException {
        logger.info("seq: {}", seq);
        String topic = "sys/avm/" + posNum + "/thing/service/pub_seq";
        publishMessage(topic, seq);
    }

    /**
     * 发布消息
     *
     * @throws MqttException
     */
    public void publishMessage(String topic, String payload) throws MqttException {
        if (mqttClient == null) {
            throw new NullPointerException("MQTT客户端未初始化！");
        }

        if (!mqttClient.isConnected()) {
            connect();
        }

        MqttMessage message = new MqttMessage();
        message.setQos(2);
        message.setPayload(payload.getBytes());

        mqttClient.publish(topic, message);
    }

    /**
     * @param text      要签名的文本
     * @param secretKey 阿里云MQ secretKey
     * @return 加密后的字符串
     */
    public static String macSignature(String text, String secretKey) {
        try {
            Charset charset = StandardCharsets.UTF_8;
            String algorithm = "HmacSHA1";
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secretKey.getBytes(charset), algorithm));
            byte[] bytes = mac.doFinal(text.getBytes(charset));
            return new String(Base64.encodeBase64(bytes), charset);
        } catch (Exception e) {
            logger.error("操作异常", e);
            return null;
        }
    }
}
