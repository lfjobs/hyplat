package com.faceSDK;

import com.alibaba.fastjson.JSON;
import com.faceSDK.faceUtil.EmpwerUtils;
import com.faceSDK.faceUtil.FaceUtils;
import com.faceSDK.faceUtil.HttpUtil;
import com.faceSDK.faceVO.*;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.office.face.OfficeFaceEmpower;
import hy.ea.bo.office.face.OfficeFaceResult;
import hy.ea.bo.office.face.TbFaceDevice;
import hy.ea.face.service.FaceService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Controller
@Scope("prototype")
public class FaceRecognitionController extends ActionSupport {
    private final Logger logger = LoggerFactory.getLogger(FaceRecognitionController.class);

    @Resource
    private FaceService faceService;

    public List<TbFaceDevice> users;

    public PageForm pageForm;

    public ServletRequest request = ServletActionContext.getRequest();

    public OfficeFaceEmpower officeFaceEmpower;

    public String staffName;

    public String payMoney;
    public String payStatus;

    public String personImage;

    public File photoFiles;

    public String photoFilesFileName;
    @Resource
    private UpLoadFileService fileService;
    public List<Object> objectList;
    public String companyId;

    public String deviceSN;

    public String deviceName;

    public byte inoutFlag;

    public String result;

    public String notes;

    public String personName;

    public String erId;

    private String deviceIds;

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    SessionWrap sw = SessionWrap.getInstance();
    HttpSession session = ServletActionContext.getRequest().getSession();

    public String findDeviceInfo(){
        deviceSN = request.getParameter("deviceSN");
        List<BaseBean> list =faceService.findDeviceInfo(deviceSN);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list", list);
        JSONObject js=JSONObject.fromObject(map);
        result=js.toString();
        return "success";
    }
    /**
     * 查询公司列表
     * @return
     */
    public String findCompanyList(){
        String companyName = request.getParameter("companyName");
        List<BaseBean> list = faceService.findCompanyList(companyName);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list", list);
        JSONObject js=JSONObject.fromObject(map);
        result=js.toString();
        return "success";
    }
    public String findERInfoList(){
        String erId = request.getParameter("erId");
        List<BaseBean> list = faceService.findERInfoList(erId);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list", list);
        JSONObject js=JSONObject.fromObject(map);
        result=js.toString();
        return "success";
    }

    /**
     * 查询设备列表
     * @return
     */
    public String findFaceDeviceInfo(){
        String companyName = request.getParameter("companyName");
        pageForm = getUsersFromDatabase(companyName);
        return "findFaceDeviceInfo";
    }

    private PageForm getUsersFromDatabase(String companyName) {
        PageForm users = faceService.findFaceDevice(companyName);
        return users;
    }

    /**
     * 新增不是系统里面的人员
     * @return
     */
    public String savePersonEmpower(){
        //获取当前登录人员信息
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if(tc!=null && tc.getAccount()!=null){
            String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
            //判断文件大小，大于200k,就直接压缩0.5
            String  photoPath= FaceUtils.scaleImage(path,photoFilesFileName, photoFiles, "faceImage","staff/headimage/"
                    + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
            OfficeFaceEmpower officeFaceEmpower=new OfficeFaceEmpower();
            officeFaceEmpower.setPersonImage(photoPath);
            officeFaceEmpower.setStaffName(personName);
            officeFaceEmpower.setPayMoney(new BigDecimal(payMoney));
            officeFaceEmpower.setEmpowerState(1);
            officeFaceEmpower.setPersonType(2);
            officeFaceEmpower.setPayStatus(Integer.parseInt(payStatus));
            officeFaceEmpower.setNotes(notes);
            officeFaceEmpower.setEmpowerStaffId(tc.getStaffid());
            String staffName=faceService.findStaffInfoById(tc.getStaffid());
            officeFaceEmpower.setEmpowerStaffName(staffName);
            officeFaceEmpower.setSiteId(erId);
            //查询当前场所对应的公司companyId
            String companyIdInfo=faceService.findCompanyIdByERId(erId);
            officeFaceEmpower.setCompanyId(companyIdInfo);
            faceService.savePersonEmpower(officeFaceEmpower);
            String personEmpower = findPersonEmpower();
            return personEmpower;
        }else{
            return null;
        }
    }

    /**
     * 查询授权人员列表
     * @return
     */
    public String findPersonEmpower(){
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        if(tc!=null && tc.getAccount()!=null){
            //获取当前登录的用户，查询用户所在的所有公司id
            String sccId = tc.getSccId();
            List<String> listInfo=faceService.findCompanyIdByAccount(sccId);
            Integer emoderState = Integer.parseInt(request.getParameter("emoderState"));
            String personName = request.getParameter("personName");
            pageForm=faceService.findPersonEmpower(emoderState,personName,listInfo);
            //查询公司下面的所有授权场所
            objectList=faceService.findContactCompanyDevice(listInfo);
            return "PersonEmpower";
        }else{
            return "login";
        }
    }
    public String findPersonEmpowerInfo(){
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
        String pageNumber = request.getParameter("pageNumber");
        String pageSize = request.getParameter("pageSize");
        String timeNumber=request.getParameter("timeNumber");
        if(tc!=null && tc.getAccount()!=null){
            //获取当前登录的用户，查询用户所在的所有公司id
            String sccId = tc.getSccId();
            List<String> listInfo=faceService.findCompanyIdByAccount(sccId);
            Integer emoderState = Integer.parseInt(request.getParameter("emoderState"));
            String personName = request.getParameter("personName");
            pageForm=faceService.findPersonEmpowerInfo(emoderState,personName,listInfo,pageNumber,pageSize,timeNumber);

            Map<String,Object> map=new HashMap<String, Object>();
            map.put("pageForm", pageForm);
            result= JSON.toJSONString(map);
            return "success";
        }else{
            return "login";
        }
    }

    public String findSiteInfo(){
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
        //获取当前登录的用户，查询用户所在的所有公司id
        String sccId = tc.getSccId();
        List<String> listInfo=faceService.findCompanyIdByAccount(sccId);
        //查询公司下面的所有授权场所
        objectList=faceService.findContactCompanyDevice(listInfo);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("SiteInfo", objectList);
        result= JSON.toJSONString(map);
        return "success";
    }
    /**
     * 人员授权
     * @return
     */
    public String empoder(){
        String staffId = request.getParameter("staffId");
        personEmpower(staffId,deviceIds);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("info", "授权成功");
        result= JSON.toJSONString(map);
        return "success";
    }
    public void personEmpower(String staffId,String deviceIds){
        String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        if(staffId!=null){
            //向火种平台授权
            //logger.info("staffId="+staffId);
            officeFaceEmpower=faceService.findPersonEmpowerByStaffId(staffId);
            //logger.info("====officeFaceEmpower======"+officeFaceEmpower.toString());
            FaceRecognitionVO faceRecognitionVO=new FaceRecognitionVO();
            faceRecognitionVO.setType(1);
            //faceRecognitionVO.setFaceUrl("http://localhost:8080/"+officeFaceEmpower.getPersonImage());
            faceRecognitionVO.setFaceUrl(path+officeFaceEmpower.getPersonImage());
            faceRecognitionVO.setCode(staffId);
            faceRecognitionVO.setEmpowerType(1);
            faceRecognitionVO.setName(officeFaceEmpower.getStaffName());
           /* List<TbFaceDevice>  device=faceService.findCompanyDeviceByCompanyKey(officeFaceEmpower.getSiteId());*/

            /*String devices="";
            for (int i = 0; i < device.size() ; i++) {
                if(i==device.size()-1){
                    devices+=device.get(i).getFaceDeviceId();
                }else{
                    devices+=device.get(i).getFaceDeviceId()+",";
                }
            }*/
            faceRecognitionVO.setDeviceId(deviceIds);
            EmpwerUtils.personCreateOrUpdate(faceRecognitionVO,officeFaceEmpower,faceService);
        }
    }
    /**
     * 向数据库新增设备信息并将设备添加到第三方平台（火种平台）
     * @return
     */
    public String saveFaceDevice(){
        TbFaceDevice tbFaceDevice=new TbFaceDevice();
        tbFaceDevice.setDeviceSN(deviceSN);
        tbFaceDevice.setDeviceName(deviceName);
        tbFaceDevice.setSiteId(erId);
        tbFaceDevice.setInoutFlag(String.valueOf(inoutFlag));
        //通过传递的公司名称查询公司id
        String[] split = companyId.split(",");
        if(split.length>0){
            tbFaceDevice.setCompanyId(split[1]);
            tbFaceDevice.setCompanyName(split[0]);
        }
        tbFaceDevice.setDeleteState(0);
        faceService.saveFaceDevice(tbFaceDevice);
        FaceDeviceVO faceDeviceVo=new FaceDeviceVO();
        faceDeviceVo.setName(deviceName);
        faceDeviceVo.setId(tbFaceDevice.getId());
        faceDeviceVo.setType(2);
        faceDeviceVo.setSerial(deviceSN);
        faceDeviceVo.setInoutFlag(inoutFlag);
        String info = EmpwerUtils.deviceCreate(faceDeviceVo, faceService);
        String faceDeviceInfo = findFaceDeviceInfo();
        return faceDeviceInfo;
    }



    public String TrafficResults() throws IOException {
        String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        String json = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        ////logger.info("人脸识别结果回调"+json);
        //将json转为实体对象
        TrafficResultsVO trafficResultsVO = new Gson().fromJson(json, TrafficResultsVO.class);
        //请求图片
        FaceVO faceVo = EmpwerUtils.getFaceVo(FaceConstant.RECOGNITION_PICTURE);
        BodyVO bodyVO=new BodyVO();
        bodyVO.setId(trafficResultsVO.getBody().getId());
        faceVo.setBody(bodyVO);
        String outJson = JSON.toJSONString(faceVo);
        ////logger.info("请求图片发送的json：{}",outJson);
        String imageInfo = HttpUtil.doPost(FaceConstant.HttpUrl, outJson);
        ////logger.info("请求图片响应信息：{}",imageInfo);
        //将base64编码转换为图片并上传到服务器
        RecognitionPictureVO recognitionPictureVO = new Gson().fromJson(imageInfo, RecognitionPictureVO.class);
        RecognitionPictureVO.Body body = recognitionPictureVO.getBody();
        String fileName=trafficResultsVO.getSign()+trafficResultsVO.getBody().getId();
        String snapImage = FaceUtils.setFaceIMGBase64(body.getSnapPic(), fileName, fileService,path);
        //将数据保存到数据库
        TrafficResultsVO.Body resultBody = trafficResultsVO.getBody();
        OfficeFaceResult faceResult=new OfficeFaceResult();
        faceResult.setPersonId(resultBody.getPersonId());
        faceResult.setPersonName(resultBody.getPersonName());
        faceResult.setDeviceSN(resultBody.getDeviceSerial());
        faceResult.setSnapImage(snapImage);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //dateFormat
        faceResult.setSnapTime(FaceUtils.updateTime(resultBody.getSnapTime()));
        faceResult.setCreateTime(new Date());
        faceResult.setStatus(resultBody.getStatus());
        faceResult.setInoutFlag(resultBody.getInoutFlag());
        faceService.saveFaceResult(faceResult);
        //组装成功返回报文
        String jsonInfo="{ \"type\":\"NOTIFY_RECOGNITION\", \"code\": \"SUCCESS\", \"requestId\": \""+faceVo.getRequestId()+"\"}";
        //发起http请求
        //logger.info("报文请求json:"+jsonInfo);
        String requestIdInfo = HttpUtil.doPost(FaceConstant.HttpUrl, jsonInfo);
        //logger.info("成功后报文请求：{}",requestIdInfo);
        return "success";
    }

    public String findDeviceBySiteid(){
        List<BaseBean> deviceBySiteid = faceService.findDeviceBySiteid(erId);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list", deviceBySiteid);
        result= JSON.toJSONString(map);
        return "success";
    }
    public String findFaceResult(){
        HttpSession session = ServletActionContext.getRequest().getSession();
        String personName = request.getParameter("personName");
        String pageNumber = request.getParameter("pageNumber");
        String pageSize = request.getParameter("pageSize");
        String timeNumber=request.getParameter("timeNumber");
        PageForm pageFormList=faceService.findFaceResult(personName,pageNumber,pageSize,timeNumber);
        //查询进出人数
        Map<String, Integer> faceResultPersonNumber = faceService.findFaceResultPersonNumber(personName, timeNumber);
        //session.setAttribute("enterCount", faceResultPersonNumber.get("enterCount"));
        //session.setAttribute("outCount", faceResultPersonNumber.get("outCount"));
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("pageForm", pageFormList);
        map.put("enterCount",faceResultPersonNumber.get("enterCount"));
        map.put("outCount",faceResultPersonNumber.get("outCount"));
        result= JSON.toJSONString(map);
        return "success";
    }

    public String findFaceResultById(){
        String resultId = request.getParameter("resultId");
        List<BaseBean> list=faceService.findFaceResultById(resultId);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list", list);
        JSONObject js=JSONObject.fromObject(map);
        result= js.toString();
        return "success";
    }
    public String findEmpowerById(){
        String staffKey = request.getParameter("staffKey");
        List<BaseBean> list=faceService.findEmpowerById(staffKey);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list", list);
        JSONObject js=JSONObject.fromObject(map);
        result= js.toString();
        return "success";
    }
    public String deletedEmpowerById(){
        String staffKey = request.getParameter("staffKey");
        String empowerStaffId = request.getParameter("empowerStaffId");
        //然后删除第三方授权
        EmpwerUtils.personDelete(empowerStaffId);
        faceService.deletedEmpowerById(staffKey);
        return "success";
    }
    public String infoList;
    public String fileUrl;
    public FileInputStream zipFile;
    public String importPersonnel() throws IOException {
        fileUrl=photoFiles.getAbsolutePath();
        JSONArray jsonArray = JSONArray.fromObject(infoList);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.get("name").toString();
            String imageUrl = jsonObject.get("imageUrl").toString();
            String[] split = imageUrl.split("/");
            logger.info("调试信息");
            File files=new File(fileUrl+imageUrl);
            importSavePersonEmpower(split[split.length-1],files,name);
        }
        return "success";
    }
    public void importSavePersonEmpower(String fileName,File files,String name){
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        //判断文件大小，大于200k,就直接压缩0.5
        String  photoPath= scaleImage(path,fileName, files, "faceImage","staff/headimage/"
                + Utilities.getDateString(new Date(), "yyyy-MM-dd"));
        logger.info("值：{}", photoPath);
        OfficeFaceEmpower officeFaceEmpower=new OfficeFaceEmpower();
        officeFaceEmpower.setPersonImage(photoPath);
        officeFaceEmpower.setStaffName(name);
        officeFaceEmpower.setPayMoney(new BigDecimal(0));
        officeFaceEmpower.setEmpowerState(1);
        officeFaceEmpower.setPersonType(2);
        officeFaceEmpower.setPayStatus(Integer.parseInt("0"));
        officeFaceEmpower.setNotes("导入");
        officeFaceEmpower.setEmpowerStaffId(tc.getStaffid());
        String staffName=faceService.findStaffInfoById(tc.getStaffid());
        officeFaceEmpower.setEmpowerStaffName(staffName);
        officeFaceEmpower.setSiteId(erId);
        //查询当前场所对应的公司companyId
        String companyIdInfo=faceService.findCompanyIdByERId(erId);
        officeFaceEmpower.setCompanyId(companyIdInfo);
        faceService.savePersonEmpower(officeFaceEmpower);
        //进行授权，并将授权失败的人统计起来。
        importEmpoder(officeFaceEmpower.getStaffKey(),deviceIds);
    }
    public String importEmpoder(String staffId,String devices){
        personEmpower(staffId,devices);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("info", "授权成功");
        result= JSON.toJSONString(map);
        return "success";
    }
    public String uploadPhoto() throws IOException {
       //获取到文件，并将文件传到服务器解压
        //fileZip
        String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        String savePath = path + "upload_files/hotoQFece";
        unzip(photoFiles,savePath);
        return "success";
    }

    public static void unzip(File zipFile, String destDir) throws IOException {

        File directory = new File(destDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        FileInputStream fis = new FileInputStream(zipFile);
        ZipInputStream zis = new ZipInputStream(fis);
        ZipEntry ze = zis.getNextEntry();

        byte[] buffer = new byte[1024];
        int length;

        while (ze != null) {
            String fileName = ze.getName();
            String dir=destDir + File.separator + fileName;
            FileOutputStream fos = new FileOutputStream(dir);
            while ((length = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            ze = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
        zipFile.delete();
    }

    public static String scaleImage(String path, String fileType,File photo, String companyID, String storedpath){
        try {
            // 读取图片
            BufferedImage originalImage = ImageIO.read(photo);
            String savePath = path + "upload_files/" + companyID + "/" + storedpath;
            String type = fileType.substring(fileType.lastIndexOf(".") + 1);
            String fileName="" ;
            if(type.equals("apk"))

            {
                fileName=fileType.substring(0,fileType.lastIndexOf("."));

            }
            else if(type.equals("ipa"))
            {
                fileName=fileType.substring(0,fileType.lastIndexOf("."));
            }else
            {
                fileName	= UUID.randomUUID().toString().replaceAll("-", "");
            }

            String photoPath = savePath + "/" + fileName + "." + type;
            File dFile = new File(savePath);
            if (!dFile.exists()) {
                // 如果文件夹不存在则建一个
                dFile.mkdirs();
            }
            ImageIO.write(originalImage, "jpg",new File(photoPath));
            return "upload_files/" + companyID + "/" + storedpath + "/" + fileName + "." + type;
        }catch (Exception e){
            return null;
        }
    }



    //大文件分块上传
    public File chunkFile;//块文件
    public String chunkFilename;//块文件的名字
    public Integer chunkNumber;//当前是第几个块文件

    public Integer totalChunks;//快文件的总数量

    public String handleChunkUpload() throws IOException {
        String path = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        String savePath = path + "upload_files/hotoQFece/"+chunkNumber+chunkFilename;
        // 分块文件保存的最终路径
        File destFile = new File(savePath);
        concatenateFiles(destFile, chunkFile);

        // 如果是最后一个分块，则合并文件
        if (chunkNumber == totalChunks - 1) {
            //合并文件
            List<String> fileNames=new ArrayList<>();
            for (int i=0;i<totalChunks;i++){
                fileNames.add(path + "upload_files/hotoQFece/"+i+chunkFilename);
            }
            mergeZipFiles(path + "upload_files/hotoQFece/"+chunkFilename,fileNames);
            //解压当前文件
            unzip(new File(path + "upload_files/hotoQFece/"+chunkFilename),path + "upload_files/hotoQFece/person");
            for (int i=0;i<totalChunks;i++){
                new File(path + "upload_files/hotoQFece/"+i+chunkFilename).delete();
            }
        }
        return "success";
    }
    public static void mergeZipFiles(String outputZipPath, List<String> inputZipBlocks) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputZipPath, true); // 开启追加模式
             FileChannel outputChannel = fos.getChannel()) {
                for (String inputZipFile : inputZipBlocks) {
                    try (FileInputStream fis = new FileInputStream(inputZipFile);
                         FileChannel inputChannel = fis.getChannel()) {
                        // 将输入的文件块写入输出的ZIP文件
                        outputChannel.transferFrom(inputChannel, outputChannel.size(), inputChannel.size());
                    }
                }
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
    }
    private void concatenateFiles(File destFile, File chunkFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(destFile, true);
             FileInputStream fis = new FileInputStream(chunkFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }
}
