package com.faceSDK.faceVO;



public class FaceFeedbackDeviceVO {
    private String type;
    private String requestId;
    private String msg;
    private String code;
    private FeedbackDeviceBody body;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FeedbackDeviceBody getBody() {
        return body;
    }

    public void setBody(FeedbackDeviceBody body) {
        this.body = body;
    }

    public class FeedbackDeviceBody{
        private String deviceId;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }
    }
}
