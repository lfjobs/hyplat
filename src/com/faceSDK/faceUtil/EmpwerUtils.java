package com.faceSDK.faceUtil;

import com.alibaba.fastjson.JSON;
import com.faceSDK.faceVO.*;
import com.google.gson.Gson;
import hy.ea.bo.office.face.OfficeFaceEmpower;
import hy.ea.bo.office.face.TbFaceDevice;
import hy.ea.face.service.FaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class EmpwerUtils {
    private static final Logger logger = LoggerFactory.getLogger(EmpwerUtils.class);
    /**
     * 向火种平台添加或修改人脸信息(empowerType决定是否向设备授权)
     * @param faceRecognitionVo
     * @return
     */
    public static String personCreateOrUpdate(FaceRecognitionVO faceRecognitionVo, OfficeFaceEmpower officeFaceEmpower, FaceService faceService) {
        //logger.info("faceRecognitionVo:"+faceRecognitionVo.toString());
        //组装请求外体
        FaceVO faceVo = getFaceVo(FaceConstant.PERSON_CREATE_OR_UPDATE);
        //组装请求人员主体信息
        faceRecognitionVo.setType(1);
        String faceIMGBase64 = FaceUtils.getFaceIMGBase64(faceRecognitionVo.getFaceUrl());
        faceRecognitionVo.setFaces(faceIMGBase64);
        faceRecognitionVo.setFaceUrl(null);
        faceVo.setBody(faceRecognitionVo);
        //将整个实体转为json格式
        //发起http请求
        String personJson = JSON.toJSONString(faceVo);
        ////logger.info("添加人员前json数据："+personJson);
        String feedbackJson = HttpUtil.doPost(FaceConstant.HttpUrl,personJson);
        //logger.info("添加人员返回json：{}",feedbackJson);
        //处理返回值,需要得到火种平台新增人员的personId
        Gson gson = new Gson();
        FaceFeedbackVO faceFeedbackVo = gson.fromJson(feedbackJson, FaceFeedbackVO.class);
        //再次请求，将人员授权到相应的设备里面去
        String code = faceFeedbackVo.getCode();
        if("SUCCESS".equals(code)){
            //将成功后的人员ID：PersonId保存到数据库中，方便下发失败后的人脸再次下发和后期对人脸授权和平台人脸识别的删除。
            //logger.info("添加人员成功后的PersonId:{}",faceFeedbackVo.getBody().getPersonId());
            officeFaceEmpower.setPersonId(faceFeedbackVo.getBody().getPersonId());
            officeFaceEmpower.setEmpowerTime(new Date());
            faceService.updateFaceEmpwer(officeFaceEmpower);
            if(faceRecognitionVo.getEmpowerType().equals(1)){//直接向设备进行授权
                PersonGrantDeviceVO personGrantDeviceVo =new PersonGrantDeviceVO();
                String deviceId = faceRecognitionVo.getDeviceId();
                String[] devices = deviceId.split(",");
                personGrantDeviceVo.setDeviceIds(devices);
                personGrantDeviceVo.setPersonId(faceFeedbackVo.getBody().getPersonId());
                //重新得到FaceVO
                FaceVO faceCaretDevice = getFaceVo(FaceConstant.PERSON_GRANT_DEVICE);
                faceCaretDevice.setBody(personGrantDeviceVo);
                String deviceJson = JSON.toJSONString(faceCaretDevice);
                //logger.info("授权时发送的json数据："+deviceJson);
                String feedbackPersonDeviceJson = HttpUtil.doPost(FaceConstant.HttpUrl, deviceJson);
                //logger.info("向设备授权返回json："+feedbackPersonDeviceJson);
                FaceFeedbackVO faceFeedbackVODevice = gson.fromJson(feedbackPersonDeviceJson, FaceFeedbackVO.class);
                String codeDevice = faceFeedbackVODevice.getCode();
                if("SUCCESS".equals(codeDevice)){
                    //将成功下发的人员将设备信息绑定到数据库中，方便后续的操作
                    officeFaceEmpower.setEmpowerState(2);
                    officeFaceEmpower.setEmpowerTime(new Date());
                    faceService.updateFaceEmpwer(officeFaceEmpower);
                    //logger.info("人员向设备授权的设备ID：{}",devices[0]);
                    return "添加人脸成功，向设备授权成功！";
                }else{
                    //logger.info("添加人脸成功，但向设备授权失败：",faceFeedbackVODevice.getMsg());
                    return "添加人脸成功，但向设备授权失败："+ faceFeedbackVODevice.getMsg();
                }
            }
            //logger.info("添加并下发人脸成功且不向设备授权");
            return "添加并下发人脸成功且不向设备授权";
        }else{
            //logger.info("添加人员到火种平台失败："+ faceFeedbackVo.getMsg());
            return "添加人员到火种平台失败："+ faceFeedbackVo.getMsg();
        }
    }
    /**
     *添加设备到火种平台并将返回的全局设备id保存到数据库
     * @param faceDeviceVo
     * @return
     */
    public static String deviceCreate(FaceDeviceVO faceDeviceVo, FaceService faceService) {
        FaceVO faceVo = getFaceVo(FaceConstant.DEVICE_CREATE);
        faceVo.setBody(faceDeviceVo);
        String jsonString = JSON.toJSONString(faceVo);
        //logger.info("绑定设备到第三方平台发送的json数据："+jsonString);
        String feedbackDeviceJson = HttpUtil.doPost(FaceConstant.HttpUrl,jsonString);
        //logger.info("添加设备到第三方平台返回日志："+feedbackDeviceJson);
        Gson gson = new Gson();
        FaceFeedbackDeviceVO faceFeedbackDeviceVo=gson.fromJson(feedbackDeviceJson, FaceFeedbackDeviceVO.class);
        String code = faceFeedbackDeviceVo.getCode();
        if("SUCCESS".equals(code)){
            //将设备的id保存到数据库中，方便之后向设备授权人脸信息
            TbFaceDevice tbFaceDevice=new TbFaceDevice();
            tbFaceDevice.setId(faceDeviceVo.getId());
            tbFaceDevice.setFaceDeviceId(faceFeedbackDeviceVo.getBody().getDeviceId());
            faceService.updateDeviceById(tbFaceDevice);
            //logger.info("返回热人脸识别设备全局ID:{}",faceFeedbackDeviceVo.getBody().getDeviceId());
            return "添加人脸识别设备成功";
        }else{
            return "添加人脸识别设备失败";
        }
    }

    /**
     *单独将人员向设备授权
     * @param personGrantDeviceVO
     * @return
     */
    @RequestMapping("personCreateDevice")
    public String personCreateDevice(@RequestBody PersonGrantDeviceVO personGrantDeviceVO) {
        //重新得到FaceVO
        FaceVO faceCaretDevice = getFaceVo(FaceConstant.PERSON_GRANT_DEVICE);
        //查询当前人员所选择的场所对应的设备ID,然后才能授权
        faceCaretDevice.setBody(JSON.toJSONString(personGrantDeviceVO));
        String feedbackPersonDeviceJson = HttpUtil.doPost(FaceConstant.HttpUrl, JSON.toJSONString(faceCaretDevice));
        Gson gson = new Gson();
        FaceFeedbackVO faceFeedbackVODevice = gson.fromJson(feedbackPersonDeviceJson, FaceFeedbackVO.class);
        String codeDevice = faceFeedbackVODevice.getCode();
        if("SUCCESS".equals(codeDevice)){
            //将成功下发的人员将设备信息绑定到数据库中，方便后续的操作
            //logger.info("人员向设备授权的设备ID：{}",personGrantDeviceVO.getDeviceIds()[1]);
            return "向设备授权成功";
        }else{
            return "向设备授权失败："+ faceFeedbackVODevice.getMsg();
        }
    }

    /**
     * 删除人脸信息（火种平台和设备一起删除，微分金暂定）
     * @param personId
     * @return
     */
    public static String personDelete(String personId) {
        FaceVO faceVo = getFaceVo(FaceConstant.PERSON_DELETE);
        ContentBodyVO cbVO=new ContentBodyVO();
        cbVO.setPersonId(personId);
        faceVo.setBody(cbVO);
        String outJson = JSON.toJSONString(faceVo);
        //logger.info("发送删除的信息：{}",outJson);
        String personDeleteJson = HttpUtil.doPost(FaceConstant.HttpUrl, outJson);
        //logger.info("响应信息：{}",personDeleteJson);
        Gson gson = new Gson();
        FaceFeedbackDeviceVO faceFeedbackDeviceVo = gson.fromJson(personDeleteJson, FaceFeedbackDeviceVO.class);
        if("SUCCESS".equals(faceFeedbackDeviceVo.getCode())){
            return "删除人员成功";
        }
        return "删除人员失败："+ faceFeedbackDeviceVo.getMsg();
    }
    public static FaceVO getFaceVo(String type) {
        FaceVO faceVo=new FaceVO();
        faceVo.setType(type);
        faceVo.setAppId(FaceConstant.AppId);
        String requesId= UUID.randomUUID().toString();
        String replace = requesId.replace("-", "");
        faceVo.setRequestId(replace);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp =sdf.format(new Date());
        faceVo.setTimestamp(timestamp);
        String sign= FaceUtils.getFaceMd5Info(FaceConstant.AppId,replace,type,FaceConstant.AppSecret,timestamp);
        faceVo.setSign(sign);
        return faceVo;
    }
}
