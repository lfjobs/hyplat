package com.stamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.junziqian.sdk.bean.ResultInfo;
import com.junziqian.sdk.bean.req.sign.ext.SignatoryReq;
import com.junziqian.sdk.bean.req.user.OrganizationCreateReq;
import com.junziqian.sdk.util.RequestUtils;
import com.wechat.bo.sft.ApplyResult;

import org.apache.http.entity.mime.content.FileBody;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class JzqAPI {
	private static final Logger logger = LoggerFactory.getLogger(JzqAPI.class);
    private static  String   APP_KEY = "35e2ab24ccc84034";
    private static String  APP_SECRET = "1093fc5635e2ab24ccc8403449149748";
    private static String  SERVICE_URL = "https://api.junziqian.com";

//    private static  String   APP_KEY = "eeb93097491c612e";
//    private static String  APP_SECRET = "ad637bf0eeb93097491c612e2cb27966";
//    private static String  SERVICE_URL = "https://api.sandbox.junziqian.com";


    public  static void main(String[] args){
     // tmplPre("","company201009046vxdyzy4wg0000000130");
///applySign();
    //  startH5Face();
//signLink();
      uploadEntSign("合同章","jO16dtmk@service.ebaoquan.org","G:\\stamp.png");
  }
//上传企业认证
    public static ApplyResult addCompany(OrganizationCreateReq req,ApplyResult applyResult){

        try {

            RequestUtils requestUtils = RequestUtils.init(SERVICE_URL, APP_KEY, APP_SECRET);
            //构建请求参数

            //req.setSignImg(new ByteArrayBody(Files.readAllBytes(new File("/tmp/test.png").toPath()),"test.png"));

            String url = "";
            url = "/v2/user/organizationCreate";
//            if("用户申请已通过".equals(applyResult.getJzqmsg())){
//                url = "/v2/user/organizationReapply";
//            }else{
//                url = "/v2/user/organizationCreate";
//            }
            ResultInfo<String> ri = requestUtils.doPost(url, req);
            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);

            logger.info("调试信息");

            boolean  success = jsonObject.getBoolean("success");
            if(success==true){
                String  email = jsonObject.getString("data");
                applyResult.setEmailOrMobile(email);
                applyResult.setJzqmsg("审核中");
            }else{
                String msg = jsonObject.getString("msg");
                String resultCode = jsonObject.getString("resultCode");
                applyResult.setJzqresultCode(resultCode);
                applyResult.setJzqmsg(msg);
            }






        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return applyResult;
    }


    /**
     *  查询企业认证状态
     *
     */
    public static ApplyResult auditStatus(String emailOrMobile, ApplyResult applyResult){
        Map<String,String> map = new HashMap<String,String>();
        try {

            RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
           //构建请求参数
            Map<String,Object> params=new HashMap<>();
            params.put("emailOrMobile",emailOrMobile);
            ResultInfo<JSONObject> ri= requestUtils.doPost("/v2/user/organizationAuditStatus",params);
            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);

            boolean  success = jsonObject.getBoolean("success");
            if(success==true){
                net.sf.json.JSONObject   data = jsonObject.getJSONObject("data");
                String status = data.getString("status");
                String msg = data.getString("msg");
                applyResult.setJzqresultCode(status);
                applyResult.setJzqmsg(msg);

            }else{
                String msg = jsonObject.getString("msg");
                String resultCode = jsonObject.getString("resultCode");
                applyResult.setJzqresultCode(resultCode);
                applyResult.setJzqmsg(msg);


            }
            logger.info("调试信息");

        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return applyResult;
    }

    public static String fileUpload(){
        try {

            RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
            //构建请求参数
            Map<String,Object> params=new HashMap<>();
            params.put("file", new FileBody(new File("E:\\1111.docx")));

            ResultInfo<Void> ri= requestUtils.doPost("/v2/file/upload",params);
            logger.info("调试信息");
//            logger.info("调试信息");
//            logger.info("调试信息");
//            logger.info("调试信息");
//            logger.info("调试信息");





        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return null;
    }

    /**
     *
     * 发起签约
     * @param templateParams
     * @param contractName
     * @param templateNo
     * @param liceneNo
     * @param email
     * @return
     */
    public static String applySign(JSONObject templateParams,String contractName,String templateNo,String liceneNo,String email,String signId){

        String aplNo = "";
        RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
        //构建请求参数
        Map<String,Object> params=new HashMap<>();
        params.put("contractName",contractName);

        JSONArray signatories = new JSONArray();


        JSONObject  signObject1 = new JSONObject();
        signObject1.put("fullName",templateParams.getString("companyName"));  //公司自定签约方名称
        signObject1.put("identityType",11);//	身份类型:1身份证 11营业执照,
        signObject1.put("identityCard",liceneNo);   //个人传身份证号，企业传营业执照号/统一社会信用代码号
        signObject1.put("email",email);//邮箱,企业必传（必须与调“企业实名认证”接口时传的邮箱一致）
         signObject1.put("noNeedVerify",1); //1 不验证 其他验证
        signObject1.put("signLevel",1);  //签字类型，标准图形章或公章:0标准图形章,1公章或手写,2公章手写或手写

        if(!signId.equals("")) {
            signObject1.put("signId", signId);//签字类型，标准图形章或公章:0标准图形章,1公章或手写,2公章手写或手写

        }
        signObject1.put("searchKey","（盖章）");//	签字位置-按关键字签署，positionType=2时必须传入，关键字支持多个;以英文;分隔
        signObject1.put("serverCaAuto",1);//是否使用自动签署完成，0或null不使用，1自动(当且只当合同处理方式为部份自动或收集批量签时有效)

//        List<String> params2 = new ArrayList<String>();
//        params2.add("12");
//       signObject1.put("authLevel",params2.toArray());//验证等级(传数组字符串)例:[2,10];2 银行卡四要素认证； 10 银行卡三要素认证； 11 人脸识别；12 短信验证码 ；13 运营商三要素认证
        signatories.add(signObject1);


        JSONObject  signObject = new JSONObject();
        signObject.put("fullName",templateParams.getString("staffName"));
        signObject.put("identityType",1);	//身份类型:1身份证
        signObject.put("identityCard",templateParams.getString("staffIdentityCard"));
        signObject.put("mobile",templateParams.getString("reference"));
           signObject.put("noNeedVerify",1);//1 不验证 其他验证
        signObject.put("signLevel",1);//签字类型，标准图形章或公章:0标准图形章,1公章或手写,2公章手写或手写

        signObject.put("authLevel","[11]");
        signObject.put("authLevelRange",1);

        signObject.put("searchKey","（签字）");//签字位置-按关键字签署，positionType=2时必须传入，关键字支持多个;以英文;分隔

//        List<String> params1 = new ArrayList<String>();
//        params1.add("12");
//       signObject.put("authLevel",params1.toArray());
        signatories.add(signObject);
        params.put("faceThreshold",70);

        params.put("signatories",signatories);
        params.put("serverCa",1);//是否需要服务端云证书：非1不需要(默认);1需要;建议需要，否则影响后续司法服务
        params.put("dealType",5);//部份自动签
        params.put("fileType",2);//2 tmpl模版生成
        params.put("positionType",2);//2关键字定义
       // params.put("faceThreshold",79); //人脸验证必传
        params.put("templateNo",templateNo);//合同模板

        params.put("templateParams",templateParams.toJSONString());

        params.put("noEbqSign","2");//暂时不保全


        ResultInfo<String> ri= requestUtils.doPost("/v2/sign/applySign",params);
        logger.info("调试信息");
        String result = JSONObject.toJSONString(ri);
        net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);

        boolean  success = jsonObject.getBoolean("success");
        if(success==true){
             aplNo = jsonObject.getString("data");
        }





          return aplNo;

    }

    /**
     *
     * 获取签约链接
     * @return
     */
    public static String signLink(String applyNo,String fullName,String identityCard){
        String signUrl = "";
        try {

            RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
           //构建请求参数91110101699594067UjFdurd2B@service.ebaoquan.org
            Map<String,Object> params=new HashMap<>();
            params.put("applyNo",applyNo);
            params.put("fullName",fullName);
            params.put("identityCard",identityCard);
            params.put("identityType",1);//证件类型 1身份证, 2护照, 3台胞证, 4港澳居民来往内地通行证, 11营业执照, 12统一社会信用代码, 20子账号, 99其他
            ResultInfo<String> ri= requestUtils.doPost("/v2/sign/link",params);
          logger.info("调试信息");

            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);


            boolean  success = jsonObject.getBoolean("success");
            if(success==true) {
                signUrl = jsonObject.getString("data");
            }


        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return signUrl;
    }

    /**
     *
     * 获取签约链接
     * @return
     */
    public static String signLinkDoc(String applyNo,String fullName,String identityCard,int identityType){
        String signUrl = "";
        try {

            RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
            //构建请求参数91110101699594067UjFdurd2B@service.ebaoquan.org
            Map<String,Object> params=new HashMap<>();
            params.put("applyNo",applyNo);
            params.put("fullName",fullName);
            params.put("identityCard",identityCard);
            params.put("identityType",identityType);//证件类型 1身份证, 2护照, 3台胞证, 4港澳居民来往内地通行证, 11营业执照, 12统一社会信用代码, 20子账号, 99其他
            ResultInfo<String> ri= requestUtils.doPost("/v2/sign/link",params);
            logger.info("调试信息");

            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);

            boolean  success = jsonObject.getBoolean("success");
            if(success==true){
                signUrl = jsonObject.getString("data");
            }



        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return signUrl;
    }


    /**
     *
     * 获取签约完成后的合同查看链接地址
     * @return
     */
    public static String signDetailLink(String applyNo){
        String viewUrl = "";
        try {

            RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
            Map<String,Object> params=new HashMap<>();
            params.put("applyNo",applyNo);
            ResultInfo<String> ri= requestUtils.doPost("/v2/sign/linkAnonyDetail",params);
            logger.info("调试信息");
            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);
            viewUrl = jsonObject.getString("data");



        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return viewUrl;
    }

    /**
     *
     * 获取最新的合同文件下载地址，可能文件还没有签署完成，建议合同签署完成后再获取下载地址。
     * @return
     */
    public static String linkFile(String applyNo){
        String downUrl = "";
        try {

            RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
            Map<String,Object> params=new HashMap<>();
            params.put("applyNo",applyNo);
            ResultInfo<String> ri= requestUtils.doPost("/v2/sign/linkFile",params);
            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);
            downUrl = jsonObject.getString("data");

        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return downUrl;
    }




    /**
     *
     *  获取模板预览
     * @return
     */
    public static String tmplPre(String contractParams,String templateNo){
        String str = "";
        try {

            RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
            Map<String,Object> params=new HashMap<>();
            params.put("templateNo",templateNo);//模版ID
            params.put("contractParams",contractParams);
            ResultInfo<Void> ri= requestUtils.doPost("/v2/tmpl/pre",params);
            String result = JSONObject.toJSONString(ri);

            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);
            net.sf.json.JSONObject   data = jsonObject.getJSONObject("data");
            str = data.getString("html");

        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return str;
    }

    /***
     *
     *
     * 上传企业自定义的公章，可用于合同签署。上传公章规格要求：170*170PX，背景透明，png格式。 若不调此接口，合同上盖的企业章默认使用系统自动根据企业名称生成圆形的图形章。
     * @param signName
     * @return
     */
    public static String uploadEntSign(String signName,String email,String signImgFile){
        String data = "";

        try {

            RequestUtils requestUtils = RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);
            Map<String,Object> params=new HashMap<>();
            params.put("signName",signName);
            params.put("email",email);
            params.put("signImgFile",new FileBody(new File(signImgFile)));
            ResultInfo<Long> ri= requestUtils.doPost("/v2/user/uploadEntSign",params);
            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);
            boolean  success = jsonObject.getBoolean("success");
            logger.info("调试信息");
            if(success==true){
                data = jsonObject.getString("data");
            }else{
                data = "error:"+jsonObject.getString("resultCode")+jsonObject.getString("msg");

            }
        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return data;
    }



    /***
     *
     *
     * 上传个人印章
     * @param
     * @return
     */
    public static String uploadPersSign(String identityCard,String signImgFile){
        String  success = "";
        try {

            RequestUtils requestUtils = RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);
            Map<String,Object> params=new HashMap<>();


            params.put("identityCard",identityCard);
            params.put("signImgFile",new FileBody(new File(signImgFile)));
            ResultInfo<Void> ri= requestUtils.doPost("/v2/user/uploadPersSign",params);

            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);
            success = jsonObject.getString("success");
            logger.info("调试信息");
            logger.info("值：{}", success);

        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return success;
    }


    /**
     *
     * 个人人脸识别
     * @return
     */
    public static String startH5Face(String orderNo,String name,String identityCard,String backUrl){
        String startFaceUrl = "";
        try {

            RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
            //构建请求参数
            Map<String,Object> params=new HashMap<>();
            params.put("orderNo",orderNo);//
            params.put("name",name);//
            params.put("identityCard",identityCard);//
            params.put("backUrl",backUrl);//

            ResultInfo<Void> ri= requestUtils.doPost("/v2/auth/startH5Face",params);
            logger.info("调试信息");
            String result = JSONObject.toJSONString(ri);
            net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);

            boolean  success = jsonObject.getBoolean("success");
            if(success==true){
                net.sf.json.JSONObject data = jsonObject.getJSONObject("data");
                 startFaceUrl = data.getString("startFaceUrl");
            }



        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return startFaceUrl;

    }

    /**
     *
     * 下载人脸视频
     * @return
     */
    public static String downloadFaceVideo(String applyNo,String fullName,String identityCard,String identityType){
        RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
//构建请求参数lin
        Map<String,Object> params=new HashMap<>();
        params.put("applyNo",applyNo); //TODO *
        params.put("fullName",fullName);
        params.put("identityCard",identityCard);
        params.put("identityType",identityType);
        params.put("evidenceType",2);//证据类型,0图片,1视频,2人脸证据
        ResultInfo<JSONObject> ri= requestUtils.doPost("/v2/sign/evidenceLinkFile",params);

        logger.info("调试信息");

        String result = JSONObject.toJSONString(ri);
        net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);
        net.sf.json.JSONObject  datas = jsonObject.getJSONObject("data");
        String linkUrl = "";
        if(datas!=null&&datas.size()>0) {
            net.sf.json.JSONArray array = datas.getJSONArray("links");

            if (array != null && array.size() > 0) {
                linkUrl = array.get(0).toString();
            }

        }


        return   linkUrl;
    }


    /**
     *
     * 根据文件pdfURL发起签约

     * @return
     */
    public static String applySignUrl(String pdfUrl,String contractName,SignatoryReq  sReq){

        String aplNo = "";
        RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
        //构建请求参数
        Map<String,Object> params=new HashMap<>();
        params.put("contractName",contractName);//合同名称必传

        JSONArray signatories = new JSONArray();


        JSONObject  signObject1 = new JSONObject();
        signObject1.put("fullName",sReq.getFullName());  //公司自定签约方名称
        sReq.setIdentityCard(sReq.getIdentityCard());
        signObject1.put("identityType",sReq.getIdentityType());//	身份类型:1身份证 11营业执照,
        signObject1.put("identityCard",sReq.getIdentityCard());   //个人传身份证号，企业传营业执照号/统一社会信用代码号
        signObject1.put("mobile",sReq.getMobile());
        signObject1.put("email",sReq.getEmail());//邮箱,企业必传（必须与调“企业实名认证”接口时传的邮箱一致）

        signObject1.put("noNeedVerify",1); //1 不验证 其他验证

        if(sReq.getIdentityType().equals(1)){
            signObject1.put("signLevel",2);


        }else{
            signObject1.put("signLevel",2);  //签字类型，标准图形章或公章:0标准图形章,1公章或手写,2公章手写或手写
            signObject1.put("certigierName",sReq.getChapteName());
            signObject1.put("certigierCardNo",sReq.getChapteJson());
            signObject1.put("certigierMobile",sReq.getMobile());

        }

        signObject1.put("authLevel","[11]");
        signObject1.put("authLevelRange",1);

//        signObject1.put("signId", sReq.getSignId());//自定义印章ID说



        signObject1.put("serverCaAuto",0);//是否使用自动签署完成，0或null不使用，1自动(当且只当合同处理方式为部份自动或收集批量签时有效)

        signatories.add(signObject1);

        params.put("faceThreshold",70);
        params.put("signatories",signatories);
        params.put("serverCa",1);//是否需要服务端云证书：非1不需要(默认);1需要;建议需要，否则影响后续司法服务
        params.put("dealType",0);//为空或0时默认为手签合同(用户有感知)
        params.put("fileSuffix",0);//0或null默认.pdf; 1url地址下载
        params.put("fileType",1);//合同上传方式:0或null直接上传PDF/ofd/word文件;1url地址下载;
     //  pdfUrl = "http://www.impf2010.com/document/company201009046vxdyzy4wg0000000025/word_XND.pdf";
        params.put("url",pdfUrl);

        params.put("positionType",null);//0或null使用签字座标位置或不指定签字位置

        params.put("noEbqSign",1);//暂时不保全
        params.put("needQifengSign","0");//0	是否使用骑缝章:1使用;其它不使用
        params.put("isArchive","0");//:0不归档;
        params.put("noBorderSign","0");//:0不归档;

       try {
           ResultInfo<String> ri = requestUtils.doPost("/v2/sign/applySign", params);
           logger.info("调试信息");
           String result = JSONObject.toJSONString(ri);

        net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);

        boolean  success = jsonObject.getBoolean("success");
        if(success==true){
            aplNo = jsonObject.getString("data");
        }




       }catch (Exception e){
           logger.error("操作异常", e);
       }
        return aplNo;

    }

    /**
     *
     * 追加签署人

     * @return
     */
    public static String applySignAdd(String aplNo,SignatoryReq sReq){

        RequestUtils requestUtils=RequestUtils.init(SERVICE_URL,APP_KEY,APP_SECRET);//建议生成为spring bean
        //构建请求参数
        Map<String,Object> params=new HashMap<>();

        params.put("applyNo",aplNo);
        params.put("isArchive",0);//是否归档，0不归档，1归档，默认值1
        /**
         * 签约方（个人）
         */
        JSONArray signatories=new JSONArray();


        JSONObject  signObject1 = new JSONObject();
        signObject1.put("fullName",sReq.getFullName());  //公司自定签约方名称
        signObject1.put("identityType",sReq.getIdentityType());//	身份类型:1身份证 11营业执照,
        signObject1.put("identityCard",sReq.getIdentityCard());   //个人传身份证号，企业传营业执照号/统一社会信用代码号
        signObject1.put("mobile",sReq.getMobile());
        signObject1.put("email",sReq.getEmail());//邮箱,企业必传（必须与调“企业实名认证”接口时传的邮箱一致）

        signObject1.put("noNeedVerify",1); //1 不验证 其他验证

        if(sReq.getIdentityType().equals(1)){
            signObject1.put("signLevel",2);

        }else{
            signObject1.put("signLevel",2);  //签字类型，标准图形章或公章:0标准图形章,1公章或手写,2公章手写或手写

        }
        signObject1.put("authLevelRange",1);

//        signObject1.put("authLevel","[11]");

//        signObject1.put("signId", sReq.getSignId());//自定义印章ID说

        signObject1.put("serverCaAuto",0);//是否使用自动签署完成，0或null不使用，1自动(当且只当合同处理方式为部份自动或收集批量签时有效)

        signatories.add(signObject1);

        params.put("faceThreshold",70);
        params.put("signatories",signatories.toJSONString());
        logger.info("调试信息");

        logger.info("调试信息");
        ResultInfo<String> ri= requestUtils.doPost("/v2/sign/signatory/add",params);
        logger.info("调试信息");
        String result = JSONObject.toJSONString(ri);
        net.sf.json.JSONObject  jsonObject = net.sf.json.JSONObject.fromObject(result);

//        String str= HttpClientUtils.init().getPost(url,null,params,true);
//        logger.info("值：{}", str);

        return null;

    }
}
