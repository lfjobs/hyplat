package hy.ea.driving.service.impl;

import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.human.Staff;
import hy.ea.bo.lottery.PrizeActivityModel;
import hy.ea.driving.service.StaffManageService;
import hy.ea.util.ImageCut;
import hy.plat.service.BaseBeanService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2017/8/25.
 */
@Transactional
@Service
public class StaffManageServiceImpl implements StaffManageService{

    @Resource
    private BaseBeanService baseBeanService;

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Override
    public void saveStudentInfo(TbJpStudentInfo studentInfo, Staff staff) throws Exception {
        String path = ServletActionContext.getRequest()
                .getSession().getServletContext().getRealPath("/");
        if (studentInfo.getPhoto() != null
                && studentInfo.getPhoto().length() > 0
                && studentInfo.getPhoto().indexOf("base64") != -1)
        {
            String imagePath = Base64(studentInfo.getPhoto());
            String jjPath = imagePath.split("\\.")[0] + "small." + imagePath.split("\\.")[1];
            ImageCut.scale(path + imagePath, path + jjPath, 300, 331);
            studentInfo.setPhoto(jjPath);
            staff.setPhoto(jjPath);
        }
        baseBeanService.update(staff);
        baseBeanService.saveOrUpdate(studentInfo);
    }

    @Override
    public void saveStudentApplyInfo(TbJpStudentInfo studentInfo) throws Exception {
        baseBeanService.saveOrUpdate(studentInfo);
    }

    @Transactional(propagation= Propagation.NOT_SUPPORTED)
    @Override
    public void saveCoachInfo(TbJpTeacher coach,Staff staff) throws Exception {
        try {
            String path = ServletActionContext.getRequest()
                    .getSession().getServletContext().getRealPath("/");
            if (coach.getPhoto() != null
                    && coach.getPhoto().length() > 0
                    && coach.getPhoto().indexOf("base64") != -1)
            {
                String imagePath = Base64(coach.getPhoto());
                String jjPath = imagePath.split("\\.")[0] + "small." + imagePath.split("\\.")[1];
                ImageCut.scale(path + imagePath, path + jjPath, 300, 331);
                coach.setPhoto(jjPath);
                staff.setPhoto(jjPath);
            }
            baseBeanService.update(staff);
            baseBeanService.update(coach);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * base64上传图片
     * @param image 图片
     * @param model
     * @return
     */
    public String Base64(String image) {


        if(image.indexOf("jpeg") != -1){
            image= image.replace("jpeg","jpg");
        }

        // 图片存储路径
        String photoPath = "upload_files\\staffManage\\";
        // 重命名
        String upName = UUID.randomUUID().toString()
                + System.currentTimeMillis() + "." + image.substring(image.indexOf("image/") + 6, image.indexOf(";base64"));

        String path = ServletActionContext.getRequest().getSession()
                .getServletContext().getRealPath("/");
        String dir = path + photoPath;
        File fileLocation = new File(dir);
        // 判断上传路径是否存在，如果不存在就创建
        if (!fileLocation.exists()) {
            boolean isCreated = fileLocation.mkdirs();
            if (!isCreated) {
                return "";
            }
        }
        // 重命名
        FileOutputStream out;
        String iconBase64 = image.substring(22);
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
            out = new FileOutputStream(dir + "/" + upName);
            out.write(buffer);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return photoPath + upName;
    }
}
