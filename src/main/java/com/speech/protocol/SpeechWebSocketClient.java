package main.java.com.speech.protocol;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class SpeechWebSocketClient extends WebSocketClient {
    private final BlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();
    // 新增：连接就绪标识（原子变量保证线程安全）
    private final AtomicBoolean isConnected = new AtomicBoolean(false);

    public SpeechWebSocketClient(URI serverUri, Map<String, String> headers) {
        super(serverUri, headers);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        // 连接成功后标记为就绪
        isConnected.set(true);
        System.out.println("WebSocket connection established, Logid: {}"+handshakedata.getFieldValue("x-tt-logid"));
      //  log.info("WebSocket connection established, Logid: {}", handshakedata.getFieldValue("x-tt-logid"));
    }

    // 新增：等待连接就绪（带超时）
    public void waitForConnected(long timeoutMs) throws InterruptedException {
        long start = System.currentTimeMillis();
        while (!isConnected.get()) {
            if (System.currentTimeMillis() - start > timeoutMs) {
                throw new InterruptedException("WebSocket connection timeout after " + timeoutMs + "ms");
            }
            Thread.sleep(10); // 短暂休眠，避免自旋
        }
    }

    // 重写 send 方法，增加连接校验
    @Override
    public void send(byte[] data) {
        if (!isConnected.get()) {
            throw new IllegalStateException("WebSocket is not connected");
        }
        super.send(data);
    }

    // 其余原有方法（onMessage/onClose/onError 等）保持不变
    @Override
    public void onMessage(String message) {
        //log.warn("Received unexpected text message: {}", message);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        try {
            Message message = Message.unmarshal(bytes.array());
            messageQueue.put(message);
        } catch (Exception e) {
          //  log.error("Failed to parse message", e);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        isConnected.set(false); // 连接关闭后重置标识
     //   log.info("WebSocket connection closed: code={}, reason={}, remote={}", code, reason, remote);
    }

    @Override
    public void onError(Exception ex) {
        isConnected.set(false); // 出错后重置标识
       // log.error("WebSocket error", ex);
    }

    // 原有发送消息的方法（sendStartConnection/sendFinishConnection 等）保持不变
    public void sendStartConnection() throws Exception {
        Message message = new Message(MsgType.FULL_CLIENT_REQUEST, MsgTypeFlagBits.WITH_EVENT);
        message.setEvent(EventType.START_CONNECTION);
        message.setPayload("{}".getBytes());
        sendMessage(message);
    }

    public void sendFinishConnection() throws Exception {
        Message message = new Message(MsgType.FULL_CLIENT_REQUEST, MsgTypeFlagBits.WITH_EVENT);
        message.setEvent(EventType.FINISH_CONNECTION);
        sendMessage(message);
    }

    public void sendStartSession(byte[] payload, String sessionId) throws Exception {
        Message message = new Message(MsgType.FULL_CLIENT_REQUEST, MsgTypeFlagBits.WITH_EVENT);
        message.setEvent(EventType.START_SESSION);
        message.setSessionId(sessionId);
        message.setPayload(payload);
        sendMessage(message);
    }

    public void sendFinishSession(String sessionId) throws Exception {
        Message message = new Message(MsgType.FULL_CLIENT_REQUEST, MsgTypeFlagBits.WITH_EVENT);
        message.setEvent(EventType.FINISH_SESSION);
        message.setSessionId(sessionId);
        message.setPayload("{}".getBytes());
        sendMessage(message);
    }

    public void sendTaskRequest(byte[] payload, String sessionId) throws Exception {
        Message message = new Message(MsgType.FULL_CLIENT_REQUEST, MsgTypeFlagBits.WITH_EVENT);
        message.setEvent(EventType.TASK_REQUEST);
        message.setSessionId(sessionId);
        message.setPayload(payload);
        sendMessage(message);
    }

    public void sendFullClientMessage(byte[] payload) throws Exception {
        Message message = new Message(MsgType.FULL_CLIENT_REQUEST, MsgTypeFlagBits.NO_SEQ);
        message.setPayload(payload);
        sendMessage(message);
    }

    public void sendMessage(Message message) throws Exception {
       // log.info("Send: {}", message);
//        if (!isOpen()) {
//            throw new IllegalStateException("WebSocket connection is closed");
//        }
    //    log.info("Send: {}", message);
        send(message.marshal());
    }

    public Message receiveMessage() throws InterruptedException {
        Message message = messageQueue.take();
     //   log.info("Receive: {}", message);
        return message;
    }

    // 优化后的 waitForMessage 方法（带超时）
    public Message waitForMessage(MsgType type, EventType event) throws InterruptedException {
        long timeout = 30000; // 30秒超时
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeout) {
            Message message = receiveMessage();
            if (message.getType() == type && message.getEvent() == event) {
                return message;
            }
        //    log.warn("Received unexpected message (continue waiting): {}", message);
        }
        throw new RuntimeException("Timeout waiting for message: type=" + type + ", event=" + event);
    }
}