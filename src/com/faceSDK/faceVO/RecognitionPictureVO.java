package com.faceSDK.faceVO;

public class RecognitionPictureVO {
    private String type;
    private String code;
    private String requestId;

    private Body body;

    public class Body{
        private String id;
        private String snapPic;

        private String comparePic;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSnapPic() {
            return snapPic;
        }

        public void setSnapPic(String snapPic) {
            this.snapPic = snapPic;
        }

        public String getComparePic() {
            return comparePic;
        }

        public void setComparePic(String comparePic) {
            this.comparePic = comparePic;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
