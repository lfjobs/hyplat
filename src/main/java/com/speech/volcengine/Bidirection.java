package main.java.com.speech.volcengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import main.java.com.speech.protocol.EventType;
import main.java.com.speech.protocol.Message;
import main.java.com.speech.protocol.MsgType;
import main.java.com.speech.protocol.SpeechWebSocketClient;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@ServerEndpoint("/tts")
public class Bidirection {
    private static final String ENDPOINT = "wss://openspeech.bytedance.com/api/v3/tts/bidirection";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private Session frontSession;
    public Bidirection(){
        // 关键！启动项目就会打印，没打印说明后端根本没加载！
        System.out.println("========== TTS WebSocket 启动成功 ==========");
    }
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("客户端连接成功！ID："+session.getId());

        System.out.println("onOpen");
        this.frontSession = session;
    }
    // 接收前端消息 + 返回数据
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到消息："+message);
        System.out.println(message);

        try {
            connectDoubao(message);
        } catch (InterruptedException e) {

        }
    }
    /**
     * 【客户端断开连接】触发
     */
    @OnClose
    public void onClose() {

        System.out.println("客户端断开：");
    }

    /**
     * 【发生错误】触发
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public static String voiceToResourceId(String voice) {
        // Map different voice types to resource IDs based on actual needs
        if (voice.startsWith("S_")) {
            return "volc.megatts.default";
        }
        return "volc.service_type.10029";
    }

    public static void main(String[] args) throws Exception {

    }
    // 只生成 44 字节 WAV 头，不处理 PCM
    public static byte[] generateWavHeader(int sampleRate, int channels, int bitDepth) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write("RIFF".getBytes());
            writeInt(out, 0xffffffff); // 流式用占位符
            out.write("WAVEfmt ".getBytes());
            writeInt(out, 16);
            writeShort(out, 1);
            writeShort(out, channels);
            writeInt(out, sampleRate);
            writeInt(out, sampleRate * channels * bitDepth / 8);
            writeShort(out, channels * bitDepth / 8);
            writeShort(out, bitDepth);
            out.write("data".getBytes());
            writeInt(out, 0xffffffff); // 流式用占位符
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // ====================== 这两个是自己写的方法，不是第三方！======================
    private static void writeInt(ByteArrayOutputStream out, int value) {
        out.write(value & 0xFF);
        out.write((value >> 8) & 0xFF);
        out.write((value >> 16) & 0xFF);
        out.write((value >> 24) & 0xFF);
    }

    private static void writeShort(ByteArrayOutputStream out, int value) {
        out.write(value & 0xFF);
        out.write((value >> 8) & 0xFF);
    }
    public  void connectDoubao(String text) throws InterruptedException {
        System.out.println("connectDoubao");
        // Configure parameters
        String appId = System.getProperty("appId", "1565334111");
        String accessToken = System.getProperty("accessToken", "wn4dR-6HPl5x2M3rc1onuo0EhJLm3hTr");
        String resourceId = System.getProperty("resourceId", "seed-tts-2.0");
        String voice = System.getProperty("voice", "zh_female_vv_uranus_bigtts");
       // String text = System.getProperty("text", "你好了么。");
        String encoding = System.getProperty("encoding", "pcm");

        if (appId.isEmpty() || accessToken.isEmpty()) {
            throw new IllegalArgumentException("Please set appId and accessToken system properties");
        }
        // 2. 先发送 WAV 头（Base64 发送）
//        byte[] header = generateWavHeader(24000, 1, 16);
//        String headerBase64 = Base64.getEncoder().encodeToString(header);
//        sendToFront(frontSession,"header:" + headerBase64);


        // ========== 修复1：替换 Map.of() 为 HashMap ==========
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Api-App-Key", appId);
        headers.put("X-Api-Access-Key", accessToken);
        headers.put("X-Api-Resource-Id", resourceId.isEmpty() ? voiceToResourceId(voice) : resourceId);
        headers.put("X-Api-Connect-Id", UUID.randomUUID().toString());
        SpeechWebSocketClient client = null;
        try {

            // Create WebSocket client
             client = new SpeechWebSocketClient(new URI(ENDPOINT), headers);
            client.connectBlocking();

            // ========== 修复2：替换 Map.of() 为 HashMap ==========
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("uid", UUID.randomUUID().toString());

            Map<String, Object> audioParamsMap = new HashMap<>();
            audioParamsMap.put("format", encoding);
            audioParamsMap.put("sample_rate", 24000);
            audioParamsMap.put("enable_timestamp", true);

            Map<String, Object> additionsMap = new HashMap<>();
            additionsMap.put("disable_markdown_filter", false);

            Map<String, Object> reqParamsMap = new HashMap<>();
            reqParamsMap.put("speaker", voice);
            reqParamsMap.put("audio_params", audioParamsMap);
            reqParamsMap.put("additions", objectMapper.writeValueAsString(additionsMap));

            Map<String, Object> request = new HashMap<>();
            request.put("user", userMap);
            request.put("namespace", "BidirectionalTTS");
            request.put("req_params", reqParamsMap);

            // Start connection
            client.sendStartConnection();
            // Wait for connection started
            client.waitForMessage(MsgType.FULL_SERVER_RESPONSE, EventType.CONNECTION_STARTED);

            // Process each sentence
            String[] sentences = text.split("。");
            boolean audioReceived = false;
            for (int i = 0; i < sentences.length; i++) {
                if (sentences[i].trim().isEmpty()) {
                    continue;
                }

                String sessionId = UUID.randomUUID().toString();
                ByteArrayOutputStream audioStream = new ByteArrayOutputStream();

                // ========== 修复3：替换 Map.of() 为 HashMap ==========
                Map<String, Object> startReq = new HashMap<>();
                startReq.put("user", request.get("user"));
                startReq.put("namespace", request.get("namespace"));
                startReq.put("req_params", request.get("req_params"));
                startReq.put("event", EventType.START_SESSION.getValue());

                client.sendStartSession(objectMapper.writeValueAsBytes(startReq), sessionId);
                // Wait for session started
                client.waitForMessage(MsgType.FULL_SERVER_RESPONSE, EventType.SESSION_STARTED);

                // Send text
                for (char c : sentences[i].toCharArray()) {
                    // Create new req_params with text
                    @SuppressWarnings("unchecked")
                    Map<String, Object> currentReqParams = new HashMap<>(
                            (Map<String, Object>) request.get("req_params"));
                    currentReqParams.put("text", String.valueOf(c));

                    // ========== 修复4：替换 Map.of() 为 HashMap ==========
                    Map<String, Object> currentRequest = new HashMap<>();
                    currentRequest.put("user", request.get("user"));
                    currentRequest.put("namespace", request.get("namespace"));
                    currentRequest.put("req_params", currentReqParams);
                    currentRequest.put("event", EventType.TASK_REQUEST.getValue());

                    client.sendTaskRequest(objectMapper.writeValueAsBytes(currentRequest), sessionId);
                }

                // End session
                client.sendFinishSession(sessionId);

                // Receive response
          /*      while (true) {
                    Message msg = client.receiveMessage();
                    switch (msg.getType()) {
                        case FULL_SERVER_RESPONSE:
                            break;
                        case AUDIO_ONLY_SERVER:
                            if (!audioReceived && audioStream.size() > 0) {
                                audioReceived = true;
                            }
                            if (msg.getPayload() != null) {
                                audioStream.write(msg.getPayload());
                            }
                            break;
                        default:
                            throw new RuntimeException("Unexpected message: " + msg);
                    }
                    if (msg.getEvent() == EventType.SESSION_FINISHED) {
                        break;
                    }
                }*/

                while (true) {
                    Message msg = client.receiveMessage();


                    switch (msg.getType()) {
                        case AUDIO_ONLY_SERVER:
                            // 核心：音频片段转Base64发送给前端
                            if (msg.getPayload() != null && msg.getPayload().length > 0) {
                                System.out.println(msg.getPayload());
                                audioReceived = true;
                                byte[] pcm = msg.getPayload();
                                if (pcm != null && pcm.length > 0) {
                                    String base64 = Base64.getEncoder().encodeToString(pcm);
                                    sendToFront(frontSession, "data:" + base64);
                                }
//                                String json = new String(msg.getPayload(), StandardCharsets.UTF_8);
//                                sendToFront(frontSession,json);
//                              String audioBase64 = java.util.Base64.getEncoder().encodeToString(msg.getPayload());
//                            sendToFront(frontSession, "audio:" + audioBase64);


//                                byte[] a = Base64.getDecoder().decode(audio);
//                                frontSession.getBasicRemote().sendBinary(ByteBuffer.wrap(a));
                            }
                            break;
                        case FULL_SERVER_RESPONSE:
                            break;
                        default:
                            System.out.println("未知消息类型：" + msg.getType());
                    }
                    if (msg.getEvent() == EventType.SESSION_FINISHED) {
                        break;
                    }
                }

//                if (audioStream.size() > 0) {
//                    String fileName = String.format("%s_session_%d.%s", voice, i, encoding);
//                    Files.write(new File(fileName).toPath(), audioStream.toByteArray());
//                //    log.info("Audio saved to file: {}", fileName);
//                }
            }

//            if (!audioReceived) {
//                throw new RuntimeException("No audio data received");
//            }
            if (audioReceived) {
                sendToFront(frontSession, "end");
            } else {
                sendToFront(frontSession, "error:未接收到音频数据");
            }

            // End connection
            client.sendFinishConnection();
        } catch (JsonProcessingException e) {
                e.printStackTrace();
        } catch (InterruptedException e) {
e.printStackTrace();
        } catch (Exception e) {
e.printStackTrace();
        } finally {
            client.closeBlocking();
        }

    }

    // 发送消息给前端
    public static  void sendToFront(Session session, String message) {
        System.out.println("sendToFront");
        try {
            if (session.isOpen()) {
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            System.out.println("发送消息给前端失败：" + e.getMessage());
        }
    }

}