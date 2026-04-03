package com.faceSDK.faceVO;


import java.util.Arrays;

public class FaceFeedbackVO {
    private String type;
    private String requestId;
    private String msg;
    private String code;
    private FeedbackBody body;


    public class FeedbackBody{
        private String[] faceIds;
        private String personId;

        public String[] getFaceIds() {
            return faceIds;
        }

        public void setFaceIds(String[] faceIds) {
            this.faceIds = faceIds;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        @Override
        public String toString() {
            return "FeedbackBody{" +
                    "faceIds=" + Arrays.toString(faceIds) +
                    ", personId='" + personId + '\'' +
                    '}';
        }
    }

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

    public FeedbackBody getBody() {
        return body;
    }

    public void setBody(FeedbackBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "FaceFeedbackVO{" +
                "type='" + type + '\'' +
                ", requestId='" + requestId + '\'' +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", body=" + body +
                '}';
    }

}
