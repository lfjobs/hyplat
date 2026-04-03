package com.faceSDK.faceVO;

public class TrafficResultsVO {
    private String type;//请求类型
    private String appId;//平台appId
    private String requestId;
    private String timestamp;
    private String sign;
    private Body body;

    public class Body{
        private String id;//识别记录 ID
        private String personId;//人员 ID
        private String faceId;//人脸 ID
        private String deviceId;//设备 ID
        private Integer status;//识别状态 0失败 1成功 2授权过期 授权未过期但不在同行时间
        private Integer personType;//人员类型 1：白名单，2：黑名单，3：访客，4：陌生人
        private String personName;//姓名
        private String personIdNum;//证件号
        private String personIcNum;//IC 卡号
        private String personEthnic;//民族
        private Integer personGender;//性别 1：男,2：女,3：未知
        private String personBirthday;//生日
        private String personAddress;//地址
        private String personIdIssuingOrgan;//发证机关
        private String personIdUsefulLife;//证件有效期
        private Integer deviceType;//设备类型 1 ： 未 知 ， 2 ： 人 脸 识别，3：人证比对
        private String deviceGroupId;//设备组 ID
        private String deviceName;//设备名称
        private byte inoutFlag;//出入标识1:入，0:出
        private Integer score;//比对评分
        private boolean matched;//是否匹配 true:匹配 false:未匹配
        private String snapTime;//抓拍时间 格式:yyyy-MM-dd HH:mm:ss
        private Float bodyTemp;//体温 只针对带测温功能的设备
        private String appProperties;//应 用 （ 项 目）属性   用户在项目（应用） 中定义的“属性”内容，原样返回。
        private String deviceProperties;//设备属性 用 户 为 设 备 定 义 的“属性”内容，原样 返回。
        private String personProperties;//人员属性 用 户 为 人 员 定 义 的 “属性”内容，原样  返回。

        private String area;

        private boolean attendEnabled;

        private String blacklistType;

        private String callbackSuccess;

        private String comparePic;

        private String createTime;

        private String dayCount;

        private String deviceSerial;

        private String expired;

        private String genderValue;

        private String intervieweeId;

        private String intervieweeName;

        private Integer passMode;
        private String personBirthdayValue;
        private String personCode;
        private String personCodeExt;

        private String personGroupId;

        private String personGroupName;

        private String personJob;

        private String personTypeValue;

        private String sectionId;

        private String  sectionName;

        private String snapPic;

        private String snapTimeValue;

        private String updateTime;
        private String visitorPassble;
        private String visitorType;

        private String voucherNo;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public boolean isAttendEnabled() {
            return attendEnabled;
        }

        public void setAttendEnabled(boolean attendEnabled) {
            this.attendEnabled = attendEnabled;
        }

        public String getBlacklistType() {
            return blacklistType;
        }

        public void setBlacklistType(String blacklistType) {
            this.blacklistType = blacklistType;
        }

        public String getCallbackSuccess() {
            return callbackSuccess;
        }

        public void setCallbackSuccess(String callbackSuccess) {
            this.callbackSuccess = callbackSuccess;
        }

        public String getComparePic() {
            return comparePic;
        }

        public void setComparePic(String comparePic) {
            this.comparePic = comparePic;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDayCount() {
            return dayCount;
        }

        public void setDayCount(String dayCount) {
            this.dayCount = dayCount;
        }

        public String getDeviceSerial() {
            return deviceSerial;
        }

        public void setDeviceSerial(String deviceSerial) {
            this.deviceSerial = deviceSerial;
        }

        public String getExpired() {
            return expired;
        }

        public void setExpired(String expired) {
            this.expired = expired;
        }

        public String getGenderValue() {
            return genderValue;
        }

        public void setGenderValue(String genderValue) {
            this.genderValue = genderValue;
        }

        public String getIntervieweeId() {
            return intervieweeId;
        }

        public void setIntervieweeId(String intervieweeId) {
            this.intervieweeId = intervieweeId;
        }

        public String getIntervieweeName() {
            return intervieweeName;
        }

        public void setIntervieweeName(String intervieweeName) {
            this.intervieweeName = intervieweeName;
        }

        public Integer getPassMode() {
            return passMode;
        }

        public void setPassMode(Integer passMode) {
            this.passMode = passMode;
        }

        public String getPersonBirthdayValue() {
            return personBirthdayValue;
        }

        public void setPersonBirthdayValue(String personBirthdayValue) {
            this.personBirthdayValue = personBirthdayValue;
        }

        public String getPersonCode() {
            return personCode;
        }

        public void setPersonCode(String personCode) {
            this.personCode = personCode;
        }

        public String getPersonCodeExt() {
            return personCodeExt;
        }

        public void setPersonCodeExt(String personCodeExt) {
            this.personCodeExt = personCodeExt;
        }

        public String getPersonGroupId() {
            return personGroupId;
        }

        public void setPersonGroupId(String personGroupId) {
            this.personGroupId = personGroupId;
        }

        public String getPersonGroupName() {
            return personGroupName;
        }

        public void setPersonGroupName(String personGroupName) {
            this.personGroupName = personGroupName;
        }

        public String getPersonJob() {
            return personJob;
        }

        public void setPersonJob(String personJob) {
            this.personJob = personJob;
        }

        public String getPersonTypeValue() {
            return personTypeValue;
        }

        public void setPersonTypeValue(String personTypeValue) {
            this.personTypeValue = personTypeValue;
        }

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getSnapPic() {
            return snapPic;
        }

        public void setSnapPic(String snapPic) {
            this.snapPic = snapPic;
        }

        public String getSnapTimeValue() {
            return snapTimeValue;
        }

        public void setSnapTimeValue(String snapTimeValue) {
            this.snapTimeValue = snapTimeValue;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getVisitorPassble() {
            return visitorPassble;
        }

        public void setVisitorPassble(String visitorPassble) {
            this.visitorPassble = visitorPassble;
        }

        public String getVisitorType() {
            return visitorType;
        }

        public void setVisitorType(String visitorType) {
            this.visitorType = visitorType;
        }

        public String getVoucherNo() {
            return voucherNo;
        }

        public void setVoucherNo(String voucherNo) {
            this.voucherNo = voucherNo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        public String getFaceId() {
            return faceId;
        }

        public void setFaceId(String faceId) {
            this.faceId = faceId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getPersonType() {
            return personType;
        }

        public void setPersonType(Integer personType) {
            this.personType = personType;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public String getPersonIdNum() {
            return personIdNum;
        }

        public void setPersonIdNum(String personIdNum) {
            this.personIdNum = personIdNum;
        }

        public String getPersonIcNum() {
            return personIcNum;
        }

        public void setPersonIcNum(String personIcNum) {
            this.personIcNum = personIcNum;
        }

        public String getPersonEthnic() {
            return personEthnic;
        }

        public void setPersonEthnic(String personEthnic) {
            this.personEthnic = personEthnic;
        }

        public Integer getPersonGender() {
            return personGender;
        }

        public void setPersonGender(Integer personGender) {
            this.personGender = personGender;
        }

        public String getPersonBirthday() {
            return personBirthday;
        }

        public void setPersonBirthday(String personBirthday) {
            this.personBirthday = personBirthday;
        }

        public String getPersonAddress() {
            return personAddress;
        }

        public void setPersonAddress(String personAddress) {
            this.personAddress = personAddress;
        }

        public String getPersonIdIssuingOrgan() {
            return personIdIssuingOrgan;
        }

        public void setPersonIdIssuingOrgan(String personIdIssuingOrgan) {
            this.personIdIssuingOrgan = personIdIssuingOrgan;
        }

        public String getPersonIdUsefulLife() {
            return personIdUsefulLife;
        }

        public void setPersonIdUsefulLife(String personIdUsefulLife) {
            this.personIdUsefulLife = personIdUsefulLife;
        }

        public Integer getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(Integer deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviceGroupId() {
            return deviceGroupId;
        }

        public void setDeviceGroupId(String deviceGroupId) {
            this.deviceGroupId = deviceGroupId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public byte getInoutFlag() {
            return inoutFlag;
        }

        public void setInoutFlag(byte inoutFlag) {
            this.inoutFlag = inoutFlag;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public boolean isMatched() {
            return matched;
        }

        public void setMatched(boolean matched) {
            this.matched = matched;
        }

        public String getSnapTime() {
            return snapTime;
        }

        public void setSnapTime(String snapTime) {
            this.snapTime = snapTime;
        }

        public Float getBodyTemp() {
            return bodyTemp;
        }

        public void setBodyTemp(Float bodyTemp) {
            this.bodyTemp = bodyTemp;
        }

        public String getAppProperties() {
            return appProperties;
        }

        public void setAppProperties(String appProperties) {
            this.appProperties = appProperties;
        }

        public String getDeviceProperties() {
            return deviceProperties;
        }

        public void setDeviceProperties(String deviceProperties) {
            this.deviceProperties = deviceProperties;
        }

        public String getPersonProperties() {
            return personProperties;
        }

        public void setPersonProperties(String personProperties) {
            this.personProperties = personProperties;
        }

        @Override
        public String toString() {
            return "Body{" +
                    "id='" + id + '\'' +
                    ", personId='" + personId + '\'' +
                    ", faceId='" + faceId + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    ", status=" + status +
                    ", personType=" + personType +
                    ", personName='" + personName + '\'' +
                    ", personIdNum='" + personIdNum + '\'' +
                    ", personIcNum='" + personIcNum + '\'' +
                    ", personEthnic='" + personEthnic + '\'' +
                    ", personGender=" + personGender +
                    ", personBirthday='" + personBirthday + '\'' +
                    ", personAddress='" + personAddress + '\'' +
                    ", personIdIssuingOrgan='" + personIdIssuingOrgan + '\'' +
                    ", personIdUsefulLife='" + personIdUsefulLife + '\'' +
                    ", deviceType=" + deviceType +
                    ", deviceGroupId='" + deviceGroupId + '\'' +
                    ", deviceName='" + deviceName + '\'' +
                    ", inoutFlag=" + inoutFlag +
                    ", score=" + score +
                    ", matched=" + matched +
                    ", snapTime='" + snapTime + '\'' +
                    ", bodyTemp=" + bodyTemp +
                    ", appProperties='" + appProperties + '\'' +
                    ", deviceProperties='" + deviceProperties + '\'' +
                    ", personProperties='" + personProperties + '\'' +
                    ", area='" + area + '\'' +
                    ", attendEnabled=" + attendEnabled +
                    ", blacklistType='" + blacklistType + '\'' +
                    ", callbackSuccess='" + callbackSuccess + '\'' +
                    ", comparePic='" + comparePic + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", dayCount='" + dayCount + '\'' +
                    ", deviceSerial='" + deviceSerial + '\'' +
                    ", expired='" + expired + '\'' +
                    ", genderValue='" + genderValue + '\'' +
                    ", intervieweeId='" + intervieweeId + '\'' +
                    ", intervieweeName='" + intervieweeName + '\'' +
                    ", passMode=" + passMode +
                    ", personBirthdayValue='" + personBirthdayValue + '\'' +
                    ", personCode='" + personCode + '\'' +
                    ", personCodeExt='" + personCodeExt + '\'' +
                    ", personGroupId='" + personGroupId + '\'' +
                    ", personGroupName='" + personGroupName + '\'' +
                    ", personJob='" + personJob + '\'' +
                    ", personTypeValue='" + personTypeValue + '\'' +
                    ", sectionId='" + sectionId + '\'' +
                    ", sectionName='" + sectionName + '\'' +
                    ", snapPic='" + snapPic + '\'' +
                    ", snapTimeValue='" + snapTimeValue + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", visitorPassble='" + visitorPassble + '\'' +
                    ", visitorType='" + visitorType + '\'' +
                    ", voucherNo='" + voucherNo + '\'' +
                    '}';
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "TrafficResultsVO{" +
                "type='" + type + '\'' +
                ", appId='" + appId + '\'' +
                ", requestId='" + requestId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                ", body=" + body +
                '}';
    }
}
