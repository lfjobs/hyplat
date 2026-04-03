// 配置项
const mqttConfig = {
    instanceId: 'post-cn-1ls43nmas01', //实例 ID，购买后从控制台获取
    host: 'post-cn-1ls43nmas01.mqtt.aliyuncs.com', // 设置当前用户的接入点域名，接入点获取方法请参考接入准备章节文档，先在控制台创建实例
    port: 443, //WebSocket 协议服务端口，如果是走 HTTPS，设置443端口
    parentTopic: 'sys', //需要操作的 Topic,第一级父级 topic 需要在控制台申请
    useTLS: true, //是否走加密 HTTPS，如果走 HTTPS，设置为 true
    accessKey: 'LTAIhoOYsMdxmhy1',
    secretKey: 'DUXMnK26k9kzwqXu9A6oi2KV8UlMn2',
    cleansession: true,
    groupId: 'GID_avm', //MQTT GroupID,创建实例后从 MQTT 控制台创建
    deviceId: randomID(), // 管理员终端设备号（可以是客户端类型+其他标识组合）
    reconnectTimeout: 2000
};

const clientId = mqttConfig.groupId + '@@@' + mqttConfig.deviceId; //GroupId@@@DeviceId，由控制台创建的 Group ID 和自己指定的 Device ID 组合构成
const username = 'Signature|' + mqttConfig.accessKey + '|' + mqttConfig.instanceId;//username和 Password 签名模式下的设置方法，参考文档 https://help.aliyun.com/document_detail/48271.html?spm=a2c4g.11186623.6.553.217831c3BSFry7
const password = CryptoJS.HmacSHA1(clientId, mqttConfig.secretKey).toString(CryptoJS.enc.Base64);
var mqtt;
var managedDeviceId; // 被扫码的货柜编号

function MQTTconnect() {
    managedDeviceId = posNum;
    mqtt = new Paho.MQTT.Client(
        mqttConfig.host,//MQTT 域名
        mqttConfig.port,//WebSocket 端口，如果使用 HTTPS 加密则配置为443,否则配置80
        clientId,//客户端 ClientId
    );
    var options = {
        timeout: 3,
        onSuccess: function (message) {
            subscribeTopics()
            //getDoorStatus()
        },
        mqttVersion: 4,
        cleanSession: mqttConfig.cleansession,
        onFailure: function (message) {
            setTimeout(MQTTconnect, mqttConfig.reconnectTimeout);
        }
    };
    mqtt.onConnectionLost = onConnectionLost;
    mqtt.onMessageArrived = onMessageArrived;
    if (username != null) {
        options.userName = username;
        options.password = password;
        options.useSSL = mqttConfig.useTLS;//如果使用 HTTPS 加密则配置为 true
    }
    mqtt.connect(options);
    console.log("MQTTconnect");
}

function subscribeTopics() {
    // Connection succeeded; subscribe to our topic
    // 订阅被管理货柜扫码开门结果回复
    mqtt.subscribe(`${mqttConfig.parentTopic}/avm/${managedDeviceId}/thing/service/+`, {qos: 0});
}

function onConnectionLost(response) {
    setTimeout(MQTTconnect, mqttConfig.reconnectTimeout);
}

/**
 * 接收到消息
 * @param message
 * @returns {boolean}
 */
function onMessageArrived(message) {
    var topic = message.destinationName;
    var payload = message.payloadString;
    console.log("recv msg : " + topic + "   " + payload);
    if (topic == `${mqttConfig.parentTopic}/avm/${managedDeviceId}/thing/service/open_relay_reply`) {
        if (payload == "open") {
            console.log("开门成功");
            pageJump(payload);
        } else {
            return false;
        }
    }
    if(topic == `${mqttConfig.parentTopic}/avm/${managedDeviceId}/thing/service/query_door_status_reply`) {
        onDoorStateChange(payload);
    }
}

/**
 * 开门
 * @param doorNumber 门编号
 */
function openDoor(doorNumber) {
    console.log(doorNumber+"开门")
    message = new Paho.MQTT.Message(`${doorNumber}`);//set body
    message.destinationName = `${mqttConfig.parentTopic}/avm/${managedDeviceId}/thing/service/open_relay`;// set topic
    mqtt.send(message);
}

/**
 * 关门
 * @param doorNumber 门编号
 */
function closeDoor(doorNumber) {
    console.log(doorNumber+"关门")
    message = new Paho.MQTT.Message(`${doorNumber}`)
    message.destinationName = `${mqttConfig.parentTopic}/avm/${managedDeviceId}/thing/service/close_relay`;
    mqtt.send(message);
}

/**
 * 查询开关门状态
 */
function getDoorStatus() {
    try {
        console.log("查询状态");
        message = new Paho.MQTT.Message(`11`);//set body
        message.destinationName = `${mqttConfig.parentTopic}/avm/${managedDeviceId}/thing/service/query_door_status`;// set topic
        mqtt.send(message);
    } catch (e) {
        console.log(e);
    }
}

/**
 * 更新产品信息
 * @param scaleNum 秤盘编号
 */
function updatePro(scaleNum) {
    try {
        console.log("更新产品信息");
        message = new Paho.MQTT.Message(`${scaleNum}`);//set body
        message.destinationName = `${mqttConfig.parentTopic}/avm/${managedDeviceId}/thing/service/update_products`;// set topic
        mqtt.send(message);
    } catch (e) {
        console.log(e);
    }
}

/**
 * 播放音频
 * @param audio 语音字符串
 */
function pubAduio(audio) {
    try {
        console.log("更新产品信息");
        message = new Paho.MQTT.Message(`${audio}`);//set body
        message.destinationName = `${mqttConfig.parentTopic}/avm/${managedDeviceId}/thing/service/pub_audio`;// set topic
        mqtt.send(message);
    } catch (e) {
        console.log(e);
    }
}

/* 生成随机randomID */
function randomID() {
    let a='manager_' + Math.random().toString(16).substring(2,10);
    for (let i = 0; i < 2; i++){
        a=a+Math.random().toString(16).substring(2,10);
    }
    console.log(a);
    return a
}
