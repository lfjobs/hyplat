package hy.ea.driving.action.elkc;

import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.elyc.ElycDrivingSchoolNotice;
import hy.ea.bo.DrivingSchool.elyc.ElycNSAssociation;
import hy.ea.driving.service.elkc.ElkcInformService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;

/**
 *
 * @author xgb
 * @version 1.0
 * @describe 驾校通知
 *
 */
@Controller
@Scope("prototype")
public class ElkcInformAction {
    private Logger logger = LoggerFactory.getLogger(ElkcInformAction.class);
    @Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
    @Resource
	private ElkcInformService elkcInformService;

    private ElycDrivingSchoolNotice edsn;

    private PageForm pageForm;

    private CAccount caccount;

    private String result;

    private Map<String,Object> map;

    private ElycNSAssociation elycNSAssociation;

    private String companyid;

    private String staffName;

    private File[] file;

    private String[] fileName;

    private String[] staffId;

    private String staffID;

    /**
     * 学员消息 *
     *
     * @return
     **/
    public String traineeInformation(){

        caccount = elkcInformService.goCaccount();

        if (caccount == null) {
            if(staffID!=null){
                caccount = new CAccount();
                caccount.setStaffID(staffID);
                return "traineeInformation";
            }
            return elkcInformService.mobileLogin();
        }

        return "traineeInformation";
    }



    /**
     * ajax通知消息 *
     *
     * @return
     **/
    public String ajaxNotificationMessage(){

        pageForm = elkcInformService.mission(elycNSAssociation.getStaffId(),edsn.getTheme(),pageForm);

        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        result = json.toString();

        return "success";
    }



    /**
     * 学员消息详情 *
     *
     * @return
     **/
    public String detailsOfStudentInformation(){

//        caccount = elkcInformService.goCaccount();
//
//        if (caccount == null) {
//            return elkcInformService.mobileLogin();
//        }

        map = elkcInformService.osfDetails(edsn.getEdsnId());

        return "detailsOfStudentInformation";
    }


    /**
     * 教练消息 *
     *
     * @return
     **/
    public String coachNews(){

        caccount = elkcInformService.goCaccount();

        if (caccount == null) {
            if(staffID!=null&&!staffID.equals("")){
                return "coachNews";
            }
            return elkcInformService.mobileLogin();
        }

        return "coachNews";
    }


    /**
     * 教练消息发送 *
     *
     * @return
     **/
    public String coachMessageSend(){

//        caccount = elkcInformService.goCaccount();
//
//        if (caccount == null) {
//            return elkcInformService.mobileLogin();
//        }

        return "coachMessageSend";
    }




    /**
     * ajax消息发送 *
     *
     * @return
     **/
    public String ajaxMessageSent(){

        map = elkcInformService.messageSent(edsn.getCreateperson(),edsn.getTheme(),pageForm);

        JSONObject json = new JSONObject();
        json.accumulate("map",map);
        result = json.toString();

        return "success";
    }



    /**
     * 教练发送消息详情 *
     *
     * @return
     **/
    public String coachMessageSendDetails(){

        caccount = elkcInformService.goCaccount();

        if (caccount == null) {
            if(staffID==null||staffID.equals("")){
                return elkcInformService.mobileLogin();
            }

        }

        map = elkcInformService.cmsDetails(edsn.getEdsnId());

        return "coachMessageSendDetails";
    }


    /**
     * 添加信息 *
     *
     * @return
     **/
    public String addAMessage(){

//        caccount = elkcInformService.goCaccount();
//
//        if (caccount == null) {
//            return elkcInformService.mobileLogin();
//        }

        return "addAMessage";
    }




    /**
     * ajax查询教练学员 *
     *
     * @return
     **/
    public String ajaxCoachStudents(){

        pageForm = elkcInformService.coachStudents(staffName,companyid,pageForm);

        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        result = json.toString();

        return "success";
    }


    /**
     * ajax添加通知信息 *
     *
     * @return
     **/
    public String ajaxAddInform(){

        elkcInformService.addInform(edsn,file,fileName,staffId);

        return "success";
    }









    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public CAccount getCaccount() {
        return caccount;
    }

    public void setCaccount(CAccount caccount) {
        this.caccount = caccount;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ElycDrivingSchoolNotice getEdsn() {
        return edsn;
    }

    public void setEdsn(ElycDrivingSchoolNotice edsn) {
        this.edsn = edsn;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public ElycNSAssociation getElycNSAssociation() {
        return elycNSAssociation;
    }

    public void setElycNSAssociation(ElycNSAssociation elycNSAssociation) {
        this.elycNSAssociation = elycNSAssociation;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public File[] getFile() {
        return file;
    }

    public void setFile(File[] file) {
        this.file = file;
    }

    public void setFileName(String[] fileName) {
        this.fileName = fileName;
    }

    public String[] getFileName() {
        return fileName;
    }

    public String[] getStaffId() {
        return staffId;
    }

    public void setStaffId(String[] staffId) {
        this.staffId = staffId;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
}
