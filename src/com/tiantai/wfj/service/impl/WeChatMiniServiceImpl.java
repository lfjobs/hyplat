package com.tiantai.wfj.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.common.Response;
import com.tiantai.wfj.front.constact.WxMiniConstant;
import com.tiantai.wfj.service.WeChatMiniService;
import com.tiantai.wfj.util.SessionWrap;
import com.tiantai.wfj.vo.WxPhone;
import com.tiantai.wfj.vo.WxResult;
import com.tiantai.wfj.vo.WxSession;
import hy.ea.bo.human.Staff;
import hy.ea.util.HttpClient;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import org.apache.struts2.ServletActionContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.tiantai.wfj.front.constact.WxMiniConstant.ServerId.*;

@Service
public class WeChatMiniServiceImpl implements WeChatMiniService {

    public static void main(String[] args) {
//        WxPhone response = new WeChatMiniServiceImpl().getPhone("8cdddc2e40f2a64d4365cfaf0189f70c4758f79f91bc74a1594a9b1160cdb86d");
//
        WxPhone wxPhone=JSONObject.parseObject("{\"errcode\":0,\"errmsg\":\"ok\",\"phone_info\":{\"phoneNumber\":\"13708207052\",\"purePhoneNumber\":\"13708207052\",\"countryCode\":\"86\",\"watermark\":{\"timestamp\":1745491458,\"appid\":\"wxed2a1187d180aeb6\"}}}",WxPhone.class);
        logger.info("值：{}", wxPhone);
    }

    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;

    @Override
    public Object Code2Session(String code) {
        String format = String.format(WxMiniConstant.URL.CODE_TO_SESSION, code);
        String result = HttpClient.doGet(format);
        WxSession wxSession = JSONObject.parseObject(result, WxSession.class);
        TEshopCusCom cus = null;
        if (wxSession != null) {
            String openid = wxSession.getOpenid();
            cus = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams("from TEshopCusCom where openId=? and logOff=0", new Object[]{openid});
        }
        return cus;
    }

    @Override
    public WxPhone getPhone(String code)  {

        String token = getAccessToken();
        logger.info("值：{}", token);
        String url = String.format(WxMiniConstant.URL.GET_USER_PHONE, JSONObject.parseObject(fetchAccessTokenFromWeChat()).getString("access_token"));
        OkHttpClient client = new OkHttpClient();

        // 设置请求体参数（类似 param: name=value&key=value）
         MediaType MEDIA_TYPE_FORM = MediaType.parse("application/json");
        String jsonBody = "{\"code\":\"" + code + "\"}";
        RequestBody body = RequestBody.create(MEDIA_TYPE_FORM, jsonBody);

        // 创建 POST 请求对象
        Request request = new Request.Builder()
                .url(url) // 替换成你的接口地址
                .post(body)
                .build();
        com.squareup.okhttp.Response response = null;
        try {
            response = client.newCall(request).execute();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String res= null;
        try {
            res = response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        WxPhone wxPhone = JSONObject.parseObject(res, WxPhone.class);
        return wxPhone;
    }

    @Override
    public Response<Object> MiniLogin(String code, String phoneCode) {

        String format = String.format(WxMiniConstant.URL.CODE_TO_SESSION, code);
        String result = HttpClient.doGet(format);
        WxSession wxSession = JSONObject.parseObject(result, WxSession.class);
        if (wxSession.getErrcode()!=null){
            return   Response.error(wxSession.getErrcode(),"code 无效");
        }
        TEshopCusCom cus = null;

        TEshopCustomer customer=null;
        if (wxSession != null) {
            String openid = wxSession.getOpenid();
            cus = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams("from TEshopCusCom where wxminiopenid=? and logOff=0", new Object[]{openid});

            if (cus != null && !StringUtil.isEmpty(cus.getAccount())) {
                customer = (TEshopCustomer) baseBeanService
                        .getBeanByHqlAndParams("from TEshopCustomer where account=? and logOff=0", new Object[]{cus.getAccount()});
                return Response.success(customer);
            }

            WxPhone wxPhone = getPhone(phoneCode);
            logger.info("调试信息");
            if (wxPhone.getErrcode()!=null&&wxPhone.getErrcode()!=0)
            {
                return   Response.error(wxPhone.getErrcode(),"phone_code 无效");
            }
            cus = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams("from TEshopCusCom where account=? and logOff=0", new Object[]{wxPhone.getPhone_info().getPhoneNumber()});
            customer = (TEshopCustomer) baseBeanService
                    .getBeanByHqlAndParams("from TEshopCustomer where account=? and logOff=0", new Object[]{wxPhone.getPhone_info().getPhoneNumber()});
            String unionid = wxSession.getUnionid();

            if (cus == null) {
                TEshopCusCom tEshopCusCom = gettEshopCusCom(openid, wxPhone.getPhone_info().getPhoneNumber(), Mini, unionid);
                customer= (TEshopCustomer) baseBeanService
                        .getBeanByHqlAndParams("from TEshopCustomer where account=? and logOff=0", new Object[]{wxPhone.getPhone_info().getPhoneNumber()});
                return Response.success(customer);
            } else {

                cus.setWxminiopenid(openid);
                if (StringUtil.isNotEmpty(wxSession.getUnionid())) {
                    cus.setWxunionid(wxSession.getUnionid());
                }
                baseBeanService.updateAndExecute(cus);
            }
        }

        return Response.success(customer);
    }

    private TEshopCusCom gettEshopCusCom(String openid, String phone, String serverId, String unionid) {
        TEshopCusCom cus;
        TEshopCustomer costmer=new TEshopCustomer();
        cus = new TEshopCusCom();
        cus.setTeccDate(new Date());
        switch (serverId){
            case Mini:
                cus.setWxminiopenid(openid);
                break;
            case H5:
                cus.setWxhopenid(openid);
            case App:
                cus.setOpenId(openid);
        }
        if (StringUtil.isNotEmpty(phone)){
            cus.setAccount(phone);
        }
        cus.setLogOff("0");
        String wxMini = serverService.getServerID(serverId);
        cus.setStaffid(wxMini);
        String tcsccid = serverService.getServerID("sccid");
        cus.setSccId(tcsccid);
        cus.setState("1");
        cus.setCusType("7");
        cus.setTeccDate(new Date());
        cus.setAcquiesce("01");
        Staff staff = new Staff();
        String phql = "select count(*) from Staff ";
        int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
        staff.setStaffCode("NO" + pcount);
        staff.setRecordCode("NO" + pcount);
        staff.setStaffStatus("00");
        staff.setVerifyTime(new Date());

        staff.setStaffID(wxMini);

        staff.setSource("微分金");
        staff.setStaus("00");
        staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
        if (StringUtil.isNotEmpty(unionid)) {
            cus.setWxunionid(unionid);
        }
        costmer.setLogOff("0");
        costmer.setAccount(phone);
        costmer.setStaffid(wxMini);
        costmer.setCustid(serverService.getServerID("custid"));
        List<BaseBean> beans = new ArrayList<BaseBean>();
        beans.add(cus);
        beans.add(costmer);
        beans.add(staff);


        baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,null);
        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        sw.setObject(session,SessionWrap.KEY_SHOPCUSCOM,cus);
        sw.setObject(session, SessionWrap.KEY_CUSTOMER, costmer);

        return cus;
    }

    @Override
    public Response<Object> WxH5Login(String code) {
        String s = HttpClient.doGet(String.format(WxMiniConstant.URL.GET_H5_OAUTH2, WxMiniConstant.INFO.PUB_APP_ID, WxMiniConstant.INFO.PUB_APP_SECRET, code));
        JSONObject jsonObject = JSONObject.parseObject(s);
        if (jsonObject.containsKey("openid")){
            String openid=jsonObject.getString("openid");
            TEshopCusCom cus = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams("from TEshopCusCom where openId=? and logOff=0", new Object[]{openid});
            TEshopCustomer costmer = null;
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            if (cus!=null){
                costmer= (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where staffid=? and logOff=0", new Object[]{cus.getStaffid()});

                sw.setObject(session,SessionWrap.KEY_SHOPCUSCOM,cus);
                sw.setObject(session, SessionWrap.KEY_CUSTOMER, costmer);
                return Response.success(cus);
            }else {
                //获取unionID
//                TEshopCusCom tEshopCusCom = gettEshopCusCom(jsonObject.getString("openid"), null, H5, jsonObject.getString("unionid"));
                WxResult wxResult=new WxResult();
                wxResult.setOpenid(jsonObject.getString("openid"));
                wxResult.setUnionid(jsonObject.getString("unionid"));
                sw.setObject(session,SessionWrap.KEY_TEMPLATE_WX_RESULT,wxResult);

                return Response.success(wxResult);

            }

        }


        return Response.error(-1,"no account");
    }

    @Override
    public Response<Object> bindPhone(WxResult wxResult, String phone,String channel) {
        TEshopCusCom cusCom = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? and logOff=0", new Object[]{phone});
        if (cusCom!=null){
            cusCom.setWxhopenid(wxResult.getOpenid());
            cusCom.setWxunionid(wxResult.getUnionid());
            baseBeanService.updateAndExecute(cusCom);
        }else {
            cusCom=gettEshopCusCom(wxResult.getOpenid(), phone,channel, wxResult.getUnionid());
        }

        Response<Object> objectResponse = new Response<>();
        objectResponse.setData(cusCom);
        return objectResponse;
    }

    public static String decrypt(String encryptedData, String sessionKey, String iv) {
        try {
            byte[] data = Base64.getDecoder().decode(encryptedData);
            byte[] key = Base64.getDecoder().decode(sessionKey);
            byte[] ivData = Base64.getDecoder().decode(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivData);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            byte[] decrypted = cipher.doFinal(data);
            return new String(decrypted);
        } catch (Exception e) {
            logger.error("操作异常", e);
            return null;
        }
    }

    private final AtomicReference<String> accessToken = new AtomicReference<>(); // 存储 token
    private volatile Instant expireTime = Instant.now(); // 记录过期时间

    // 定时任务，每5分钟执行一次
    @Scheduled(fixedDelay = 5 * 60 * 1000) // 5分钟
    public void refreshAccessToken() {
        if (Instant.now().isAfter(expireTime.minusSeconds(60))) { // 提前 60 秒刷新
            String newToken =
                    fetchAccessTokenFromWeChat(); // 调用微信 API 获取
            accessToken.set(newToken);
            expireTime = Instant.now().plusSeconds(7200); // 假设 7200 秒有效期
            logger.info("更新 access_token: : {}", newToken);
        }
    }

    public String getAccessToken() {
        return accessToken.get();
    }

    private String fetchAccessTokenFromWeChat() {
        return HttpClient.doGet(WxMiniConstant.URL.GET_ACCESS_TOKEN);
    }
}
