package com.wattOpen;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 *WattOPEN接口文档（设备层）
 *
 * @author author wangdaigang
 * @create 2024
 */
public class WattEquipmentApiUtil {

    /**
     * 测试环境
     */
    private static final String TEST_API_URL = "https://test-openapi.elecwatt.com:8043";
    /**
     * 生产环境
     */
    private final String PROD_API_URL = "https://openapi.elecwatt.com";


    /**
     * 获取设备信息
     */
    public static final String getDeviceInfoUrl = "/open/iot2/device/info";

    /**
     * 获取设备信息分页
     */
    public static final String getDeviceInfoPageUrl = "/open/iot2/device/infoPage";

    /**
     * 获取最近一次的读数
     */
    public static final String getLatelyReadingUrl = "/open/iot2/device/latelyReading";

    /**
     * 异步控制
     */
    public static final String asyncControlUrl = "/open/iot2/device/asynControl";

    /**
     * (异步)召测设备信息
     */
    public static final String asyncInfoUrl = "/open/iot2/call/info/async";

    /**
     * 获取多个设备读数
     */
    public static final String timeReadingsPageUrl = "/open/iot2/device/timeReadingsPage";

    /**
     * 获取单个设备历史读数整点小时数据
     */
    public static final String readingsHourUrl = "/open/iot2/device/readingsHour";

    /**
     * 获取单个设备历史读数
     */
    public static final String readingsPageUrl = "/open/iot2/device/readingsPage";

    /**
     * 查询请求（异步）返回结果
     */
    public static final String resultUrl = "/open/iot2/device/result";


    /**
     * 获取设备信息
     *
     * @param sn 设备序列号
     * @return
     */
    public static JSONObject handleDeviceInfo(String sn) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sn", sn);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + getDeviceInfoUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }

    /**
     * 获取设备信息列表（分页）
     *
     * @param pageNumb
     * @param pageSize
     * @return
     */
    public static JSONObject handleDeviceInfoPage(Integer pageNumb, Integer pageSize) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("pageNumb", pageNumb);
        bodyJson.put("pageSize", pageSize);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + getDeviceInfoPageUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 获取最近一次的读数
     *
     * @param sn
     * @return
     */
    public static JSONObject handleLatelyReading(String sn) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sn", sn);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + getLatelyReadingUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * (异步)设备拉合闸
     *
     * @param sn          设备序列号
     * @param controlType 控制类型
     * @param callback    调用会立即返回,结果会以回调的方式发送
     * @return
     */
    public static JSONObject handleAsyncControl(String sn, String controlType, String callback) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sn", sn);
        bodyJson.put("controlType", controlType);
        bodyJson.put("callback", callback);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + asyncControlUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }

    /**
     * (异步)召测设备信息
     *
     * @param sn       设备序列号
     * @param dataType 召测数据类型
     * @param callback 调用会立即返回,结果会以回调的方式发送
     * @return
     */
    public static JSONObject handleAsync(String sn, String dataType, String callback) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sn", sn);
        bodyJson.put("dataType", dataType);
        bodyJson.put("callback", callback);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + asyncInfoUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 获取单个设备历史读数整点小时数据
     * @param sn 设备序列号
     * @param startTime 开始时间，大于等于
     * @param endTime 结束时间，小于
     * @return
     */
    public static JSONObject handleReadingsHour(String sn, Date startTime, Date endTime) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sn", sn);
        bodyJson.put("startTime", startTime);
        bodyJson.put("endTime", endTime);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + readingsHourUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 获取多个设备读数
     * @param sns  未填：查询所有设备，
     *             已填：查询当前已填设备
     * @param queryTime 某个时间点，电表读数一般只有小时时间。
     * @param type 设备类型
     * @param pageNumb 默认1
     * @param pageSize 默认值20，最大100
     * @return
     */
    public static JSONObject handleTimeReadingsPage(List<String> sns, Date queryTime, String type, Integer pageNumb, Integer pageSize) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("sns", sns);
        bodyJson.put("queryTime", queryTime);
        bodyJson.put("type", type);
        bodyJson.put("pageNumb", pageNumb);
        bodyJson.put("pageSize", pageSize);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + timeReadingsPageUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 获取单个设备历史某个时间段上报读数（时间跨度不得超过2天）
     *
     * @param startTime
     * @param endTime
     * @param pageNumb
     * @param pageSize
     * @return
     */
    public static JSONObject handleReadingsPage(Date startTime, Date endTime, Integer pageNumb, Integer pageSize) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("startTime", startTime);
        bodyJson.put("endTime", endTime);
        bodyJson.put("pageNumb", pageNumb);
        bodyJson.put("pageSize", pageSize);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + readingsPageUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


    /**
     * 通过requestId，查询异步返回结果；如：拉合闸、召测实时读数、继电器状态等
     *
     * @param requestId 请求id
     * @return
     */
    public static JSONObject handleResult(String requestId) {
        JSONObject bodyJson = new JSONObject();
        bodyJson.put("requestId", requestId);
        String r = WattConfig.handlePost(bodyJson.toString(), TEST_API_URL + resultUrl);
        JSONObject rJson = JSONObject.parseObject(r);
        if (rJson.getBoolean("success")) {
            return rJson.getJSONObject("data");
        }
        return null;
    }


}
