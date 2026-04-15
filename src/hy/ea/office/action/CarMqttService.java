package hy.ea.office.action;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiantai.wfj.util.MqttService;
import org.eclipse.paho.client.mqttv3.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CarMqttService {

    private final String serverURI = "tcp://112.126.87.226:1883";
    String request = "https://impf2010.com/ea/carmanage/r_ea_mqttShenZhenShiBie.jspa";
    //private final String serverURI = "tcp://127.0.0.1:1883";//测试环境
    //private String request = "http://127.0.0.1:8080/ea/carmanage/r_ea_mqttShenZhenShiBie.jspa";//测试环境
    private final int keepAliveInterval = 60;
    private final int connectionTimeout = 30;
    private final boolean cleanSession = true;
    private final boolean autoReconnect = true;
    private final String clientId = "TCMQTTService123456";//将clientId设置为唯一的 ，防止备用服务器连接导致消息被多次消费出现问题。本地要使用自行修改上面的mqtt地址。
    private final String userName = "admin";
    private final String password = "admin";
    public static Map<String, Boolean> deviceStatusMap = new HashMap<>();
    public static Map<String, Date> deviceTimeMap = new HashMap<>();
    private static MqttClient mqttClient;
    private int number=1;



    private CarMqttService(){
        System.out.println("开始连接1");
        connect();
    }

    private static volatile CarMqttService instance=null;

    public static CarMqttService getInstance(){
        if (instance == null) {
            synchronized (MqttService.class) {
                if (instance == null) {
                    instance = new CarMqttService();
                }
            }
        }
        return instance;
    }

    //回调函数
    final MqttCallbackExtended mCallback = new MqttCallbackExtended() {
        @Override
        public void connectComplete(boolean reconnect, String serverURI) {
            if (reconnect) {
                System.out.println("重新连接到 : " + serverURI);
                if(number==2){
                    subscribeToTopic();
                }
            } else {
                System.out.println("连接完成");
                System.out.println("连接目标: " + serverURI);
                subscribeToTopic();
                number=1;
            }
        }

        @Override
        public void connectionLost(Throwable cause) {
            System.out.println("连接已断开，自动开始重连");
            while (true){
                try {
                    Thread.sleep(5000);
                    number=2;
                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setUserName(userName);
                    options.setPassword(password.toCharArray());
                    options.setKeepAliveInterval(keepAliveInterval);
                    options.setConnectionTimeout(connectionTimeout);
                    options.setCleanSession(cleanSession);
                    options.setAutomaticReconnect(autoReconnect);
                    mqttClient.connect(options);
                    break;
                } catch (Exception e) {
                    System.out.println("连接错误："+e.getMessage());
                }
            }
        }

        @Override
        public void messageArrived(String topic, MqttMessage message) {
            //System.out.println("订阅的消息" + new String(message.getPayload()));
            // 收到消息的处理逻辑
            String info = new String(message.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(info);
            //将当前参数丢给处理逻辑程序
            boolean containsIvsResult = topic.contains("ivs_result");//车辆识别数据包
            boolean containsKeepAlive = topic.contains("keep_alive");//心跳包
            if (containsIvsResult) {
                vehicleInformation(jsonObject); // 调用你的方法
            }
            if (containsKeepAlive) {
                heartbeatData(jsonObject);
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
            //System.out.println("消息成功发送到服务器上回调");
        }
    };

    void connect(){
        try {
        //创建连接
        mqttClient = new MqttClient(serverURI, clientId);
        //设置回调
        mqttClient.setCallback(mCallback);

        //验证
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setKeepAliveInterval(keepAliveInterval);
        options.setConnectionTimeout(connectionTimeout);
        options.setCleanSession(cleanSession);
        options.setAutomaticReconnect(autoReconnect);
        System.out.println("开始连接到" + serverURI);
        mqttClient.connect(options);
        } catch (MqttException e) {
            System.out.println("连接失败" + e.getMessage());
        }
    }

    private void subscribeToTopic() {
        System.out.println("订阅主题");
        try {
            // 订阅多个主题
            String[] topics = {"device/+/message/up/ivs_result", "device/+/message/up/keep_alive"};
            mqttClient.subscribe(topics); // 订阅所有主题
        } catch (MqttException ex) {
            System.err.println("订阅时出现异常:" + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public void vehicleInformation(JSONObject jsonObject) {
        try {
            System.out.println("处理接收到的车辆信息：" + jsonObject);
            String line;
            StringBuilder response = new StringBuilder();
            //开始处理接收到的消息
            JSONObject payload = jsonObject.getJSONObject("payload");
            //车牌号暂时无用
            String license = payload.getJSONObject("AlarmInfoPlate").getJSONObject("result").getJSONObject("PlateResult").getString("license");
            //测试发送显示数字到屏幕
            // 使用Base64解码器解码
            byte[] decodedBytes = Base64.getDecoder().decode(license);
            license = new String(decodedBytes, "UTF-8");
            if ("_无_".equals(license) || license == null || "".equals(license)) {
                System.out.println("当前车牌号：" + license + "，无需进行任何处理");
            } else {
                String urlParameters = "payloadJson=" + payload;
                byte[] bytes = urlParameters.getBytes("UTF-8");
                URL url = new URL(request);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                connection.setRequestProperty("Content-Length", Integer.toString(bytes.length));
                connection.setUseCaches(false);

                try (OutputStream os = connection.getOutputStream()) {
                    os.write(bytes);
                }

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    JSONObject responseObject = JSONObject.parseObject(response.toString());
                    //System.out.println(responseObject);
                    String open = responseObject.getJSONObject("Response_AlarmInfoPlate").getString("info");
                    String id = jsonObject.getString("id");
                    String sn = jsonObject.getString("sn");
                    if ("ok".equals(responseObject.getJSONObject("Response_AlarmInfoPlate").getString("info"))) {
                        //发送开门信号
                        String content = responseObject.getJSONObject("Response_AlarmInfoPlate").getString("content");
                        org.json.JSONObject json = new org.json.JSONObject(content);
                        String string = json.get("pronunciationgMap").toString();
                        String string2 = json.get("showTextMap").toString();
                        //System.out.println(string);
                        //System.out.println(string2);
                        ObjectMapper objectMapper = new ObjectMapper();
                        LinkedHashMap<String, String> pronunciationgMap = objectMapper.readValue(string, LinkedHashMap.class);//语音
                        LinkedHashMap<String, String> showTextMap = objectMapper.readValue(string2, LinkedHashMap.class);//显示
                        //显示屏文字
                        xianshishuju(id, sn, showTextMap, open);
                        //语音
                        pronunciation(id, sn, pronunciationgMap);
                        //开门
                        isOpen(id, sn);
                    } else {
                        String content = responseObject.getJSONObject("Response_AlarmInfoPlate").getString("content");
                        org.json.JSONObject json = new org.json.JSONObject(content);
                        String jsonStr="";
                        if(json.length()!=0){
                            jsonStr = json.get("pronunciationgMap").toString();
                        }
                        String string2 = json.get("showTextMap").toString();
                        //System.out.println(string);
                        // System.out.println(string2);

                        ObjectMapper objectMapper = new ObjectMapper();
                        LinkedHashMap<String, String> pronunciationgMap = objectMapper.readValue(jsonStr, LinkedHashMap.class);//语音
                        LinkedHashMap<String, String> showTextMap = objectMapper.readValue(string2, LinkedHashMap.class);//显示
                        xianshishuju(id, sn, showTextMap, open);
                        pronunciation(id, sn, pronunciationgMap);
                    }
                    in.close();
                } else {
                    // error, handle error
                    System.out.println("请求失败");
                }
                connection.disconnect();
            }
        } catch (Exception e) {
            System.err.println("消息处理失败：" + e.getMessage());
        }
    }

    public static void isOpen(String idString, String sn) {
        try {
            String openSignal = getOpenSignal(idString, sn);
            MqttMessage publishMessage = new MqttMessage(openSignal.getBytes());
            publishMessage.setQos(2);
            publishMessage.setRetained(false);
            MqttTopic topic1 = mqttClient.getTopic("device/" + sn + "/message/down/gpio_out");
            MqttDeliveryToken token = topic1.publish(publishMessage);
            token.waitForCompletion();
            System.out.println("sn:"+sn+",idString："+idString);
            if (token.isComplete()) {
                System.out.println("通知道闸开门消息发送完成");
            } else {
                System.out.println("消息发送未完成");
            }
        } catch (Exception e) {
            System.err.println("发送异常:" + e.getMessage());
        }
    }

    public void heartbeatData(JSONObject jsonObject) {
        //System.out.println("处理接收到的心跳信息："+jsonObject);
        //维护一个全局的设备是否在线的map,心跳为60秒
        String sn = jsonObject.getString("sn");
        //System.out.println(sn);
        long date = jsonObject.getLong("timestamp");
        //System.out.println(date);
        deviceStatusMap.put(sn, true);
        deviceTimeMap.put(sn, new Date(date));
        //System.out.println("设备在线情况："+deviceStatusMap.toString());
        //System.out.println("设备心跳时间情况："+deviceTimeMap.toString());
    }


    private static String getOpenSignal(String idString, String sn) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("id", idString);
        map.put("sn", sn);
        map.put("name", "gpio_out");
        map.put("version", "1.0");
        map.put("timestamp", new Date().getTime());
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("type", "gpio_out");
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("delay", 500);
        bodyMap.put("io", 0);
        bodyMap.put("value", 2);
        payloadMap.put("body", bodyMap);
        map.put("payload", payloadMap);
        String json = objectMapper.writeValueAsString(map).toString();
        return json;
    }

    private static String serial_data(String idString, String sn, List<Map<String, Object>> list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("id", idString);
        map.put("sn", sn);
        map.put("name", "serial_data");
        map.put("version", "1.0");
        map.put("timestamp", new Date().getTime());
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("type", "serial_data");
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("interval", 50);
        bodyMap.put("serialData", list);
        payloadMap.put("body", bodyMap);
        map.put("payload", payloadMap);
        String json = objectMapper.writeValueAsString(map).toString();
        return json;
    }

    public static void xianshishuju(String id, String sn, LinkedHashMap<String, String> showTextMap, String open) {
        try {
            //显示第一行（车牌号）
            List<Map<String, Object>> list = new ArrayList<>();
            for (Map.Entry<String, String> entry : showTextMap.entrySet()) {
                Map<String, Object> serialMap = new HashMap<>();
                //System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
                String licenseInfo = CarShenZhenManageActionUtiles.displayFourLines(entry.getValue(), entry.getKey());
                serialMap.put("serialChannel", 0);
                serialMap.put("data", licenseInfo);
                serialMap.put("dataLen", licenseInfo.length());
                list.add(serialMap);

            }
            //红绿灯数据:绿灯超时时间8秒
            if ("ok".equals(open)) {
                //开门，开启绿灯，超时时间为8秒。
                Map<String, Object> trafficLight = CarShenZhenManageActionUtiles.trafficLight();
                list.add(trafficLight);
            }

            //发送mqtt
		/*String idString = jsonObject.getString("id");
		String sn = jsonObject.getString("sn");*/
            String openSignal = serial_data(id, sn, list);
            //System.out.println("发送文字的数据:"+openSignal);
            MqttMessage publishMessage = new MqttMessage(openSignal.getBytes());
            publishMessage.setQos(2);
            publishMessage.setRetained(false);
            MqttTopic topic1 = mqttClient.getTopic("device/" + sn + "/message/down/serial_data");
            MqttDeliveryToken token = topic1.publish(publishMessage);
            token.waitForCompletion();
            if (token.isComplete()) {
                System.out.println("文字消息发送完成");
            } else {
                System.out.println("文字消息发送未完成");
            }
        } catch (Exception e) {
            System.out.println("文字消息发生异常：" + e.getMessage());
        }
    }

    public static void pronunciation(String id, String sn, LinkedHashMap<String, String> map) {
        try {
            String licenseInfo = CarShenZhenManageActionUtiles.pronunciationg(map);
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> serialMap = new HashMap<>();
            serialMap.put("serialChannel", 0);
            serialMap.put("data", licenseInfo);
            serialMap.put("dataLen", licenseInfo.length());
            list.add(serialMap);
            if (licenseInfo == null) {
                System.out.println("发生异常，不能发布订阅");
                return;
            }
            //发送开门信号
		/*String idString = jsonObject.getString("id");
		String sn = jsonObject.getString("sn");*/
            String openSignal = serial_data(id, sn, list);
            //System.out.println("发送语音的数据:"+openSignal);
            MqttMessage publishMessage = new MqttMessage(openSignal.getBytes());
            publishMessage.setQos(2);
            publishMessage.setRetained(false);
            MqttTopic topic1 = mqttClient.getTopic("device/" + sn + "/message/down/serial_data");
            MqttDeliveryToken token = topic1.publish(publishMessage);
            token.waitForCompletion();
            if (token.isComplete()) {
                System.out.println("语音消息发送完成");
            } else {
                System.out.println("语音消息发送未完成");
            }
        } catch (Exception e) {
            System.out.println("语音消息发生异常：" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String formatNumberToChinese(int number) {
        if (number < 0) {
            return "负" + formatNumberToChinese(-number);
        }

        String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿"};
        String numberStr = String.valueOf(number);
        StringBuilder result = new StringBuilder();
        int length = numberStr.length();
        int unitIndex = 0;

        for (int i = 0; i < length; i++) {
            int digit = numberStr.charAt(i) - '0'; // 获取当前位的数字
            if (digit == 0) {
                // 如果当前数字为0，且不是个位，则跳过该单位（例如“1万0千”应简化为“1万千”）
                if (i != length - 1) {
                    continue;
                } else { // 对于个位，即使是0也要加上单位“十”或“零”
                    result.append("0");
                }
            } else {
                result.append(digit);
                if (i == length - 1) { // 个位直接加单位“十”或“零”
                    result.append("");
                } else { // 其他位根据剩余长度和单位数组选择合适的单位
                    int remainingLength = length - i - 1; // 剩余位数（不包括当前位）
                    unitIndex = remainingLength % 4; // 计算应该在哪个单位上，例如12345中的5应该在“万”上，所以取余数3对应的单位
                    if (unitIndex == 0 && result.charAt(result.length() - 1) != '十') { // 如果当前是万位但不是个位，需要添加“万”前缀
                        result.append("万");
                    } else if (unitIndex > 0) { // 添加其他单位（除了“万”）
                        result.append(units[unitIndex]);
                    }
                }
            }
        }
        return result.toString();
    }

    public static void enablingSignal(String sn) {
        try {
            String idString = "1234567890";
            String openSignal = getOpenSignal(idString, sn);
            MqttMessage publishMessage = new MqttMessage(openSignal.getBytes());
            publishMessage.setQos(2);
            publishMessage.setRetained(false);
            MqttTopic topic1 = mqttClient.getTopic("device/" + sn + "/message/down/gpio_out");
            MqttDeliveryToken token = topic1.publish(publishMessage);
            token.waitForCompletion();
            if (token.isComplete()) {
                System.out.println("消息发送完成");
            } else {
                System.out.println("消息发送未完成");
            }
        } catch (Exception e) {
            System.out.println("发生错误，无法开门：" + e.getMessage());
        }
    }

   // public static void main(String[] args) throws MqttException, JsonProcessingException {
		/*// 创建MQTT客户端实例
		MqttClient client = new MqttClient("tcp://127.0.0.1:1883", MqttClient.generateClientId());
		// 连接选项
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		connOpts.setAutomaticReconnect(true); // 设置自动重连
		connOpts.setCleanSession(true);
		// 设置用户名和密码，如果需要的话
		// 设置认证信息
		connOpts.setUserName("admin");
		connOpts.setPassword("admin".toCharArray());
		// 连接到MQTT代理
		client.connect(connOpts);

		List<Map<String,Object>> list=new LinkedList<>();
		Map<String,Object> serialMap=new HashMap<>();
		String licenseInfo = CarShenZhenManageActionUtiles.displayFourLines("04","11扫码支付");
		serialMap.put("serialChannel",0);
		serialMap.put("data",licenseInfo);
		serialMap.put("dataLen",licenseInfo.length());
		list.add(serialMap);

		String idString = "123";
		String sn = "9cfce3ef-e127c3e5";
		String openSignal = serial_data(idString, sn,list);
		System.out.println("发送文字的数据:"+openSignal);
		MqttMessage publishMessage = new MqttMessage(openSignal.getBytes());
		publishMessage.setQos(2);
		publishMessage.setRetained(false);
		MqttTopic topic1 = client.getTopic("device/"+sn+"/message/down/serial_data");
		MqttDeliveryToken token = topic1.publish(publishMessage);
		token.waitForCompletion();
		if (token.isComplete()) {
			System.out.println("消息发送完成");
		} else {
			System.out.println("消息发送未完成");
		}*/


        //String content="{}";
       // org.json.JSONObject json = new org.json.JSONObject(content);
       // System.out.println(json);
    //}

    public static String getIP() {
        String ip = "";
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
            System.out.println("当前服务器的IP地址是：" + ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

}
