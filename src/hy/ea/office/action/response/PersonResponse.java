package hy.ea.office.action.response;

/**
 * Q系人脸识别-人员返回响应参数
 */
public class PersonResponse {

    private String id; //人员id，不能重复

    private String cmd; //add：新增人员，如已存在则更新

    private String name; //人员姓名，编码为UTF-8

    private String type; //人员类型 人员类型，whiteList：白名单，blackList：黑名单，guest：访客，如果没有该字段，则为默认值白名单

    private String icCard; //IC 卡卡号

    private String idCard; //身份证号码

    private String passPeriod; //允许通行时段，用时分表示，如08:00~09:30表示早上八点到九点半允许通行，可以设置多段时间，各段用逗号隔开，支持最多3个

    private String startTime; //授权开始时间 格式：yyyy-MM-dd HH:mm:ss

    private String expireTime; //授权结束时间 格式：yyyy-MM-dd HH:mm:ss

    private String facePicture; //人脸照片URL路径，通过http方式获取

    private String facePictureX1; //人脸第二张照片URL路径，通过http方式获取

    private String facePictureX2; //人脸第三张照片URL路径，通过http方式获取

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcCard() {
        return icCard;
    }

    public void setIcCard(String icCard) {
        this.icCard = icCard;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPassPeriod() {
        return passPeriod;
    }

    public void setPassPeriod(String passPeriod) {
        this.passPeriod = passPeriod;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getFacePicture() {
        return facePicture;
    }

    public void setFacePicture(String facePicture) {
        this.facePicture = facePicture;
    }

    public String getFacePictureX1() {
        return facePictureX1;
    }

    public void setFacePictureX1(String facePictureX1) {
        this.facePictureX1 = facePictureX1;
    }

    public String getFacePictureX2() {
        return facePictureX2;
    }

    public void setFacePictureX2(String facePictureX2) {
        this.facePictureX2 = facePictureX2;
    }
}
