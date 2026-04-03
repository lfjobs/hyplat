package com.faceSDK.faceVO;

/**
 * 发送人脸识别http请求时的请求方式
 */
public class FaceConstant {

    //平台请求地址
    public static final String HttpUrl="http://qy-rgs.com/api";
    //平台appId
    public static final String AppId="9a054437a09146d6b609d5c63606d919";
    //平台秘钥
    public static final String AppSecret="2D69A545502D441FBC6BE099BDE4506B";
    //新增或者修改
    public static final String PERSON_CREATE_OR_UPDATE = "PERSON_CREATE_OR_UPDATE";
    //向火种平台删除人
    public static final String PERSON_DELETE = "PERSON_DELETE";
    //人员向设备授权
    public static final String PERSON_GRANT_DEVICE="PERSON_GRANT_DEVICE";
    //向火种平台添加设备
    public static final String DEVICE_CREATE="DEVICE_CREATE";
    //请求人脸识别通过的图片
    public static  final String RECOGNITION_PICTURE="RECOGNITION_PICTURE";




}
