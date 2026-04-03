package hy.ea.driving.action.elkc;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.DrivingSchool.elyc.ElycTrainerLeave;
import hy.ea.bo.DrivingSchool.elyc.ElycTrainerVacation;
import hy.ea.bo.DrivingSchool.elyc.TbElycSchoolRest;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.driving.service.ElkcCompanyConfigService;
import hy.ea.driving.service.elkc.AflovacationService;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author xgb
 * @version 1.0
 * @describe 驾校/教练请假休假
 *
 */
@Controller
@Scope("prototype")
public class AfloVacationAction {
    private Logger logger = LoggerFactory.getLogger(AfloVacationAction.class);
    @Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
    @Resource
	private AflovacationService aflovacationService;
    @Resource
    private ElkcCompanyConfigService elkcCompanyConfigService;

    private PageForm pageForm;

    private int pageNumber;

    private ProductPackaging ppk;

    private TEshopCusCom tcc;

    private CAccount caccount;

    private TbElycSchoolRest tbElycSchoolRest;

    private ElycTrainerVacation elycTrainerVacation;

    private TbJpTeacher tbJpTeacher;

    private ElycTrainerLeave elycTrainerLeave;

    private String result;

    private String beginDate;

    private String endDate;

    private String leaveData;

    private String substitutename;





    /**
     * 驾校休假列表 *
     *
     * @return
     */
    public String schoolVacation(){

        CAccount account = aflovacationService.companyInformation();

        if(account==null)
        {
            return "nologin";
        }
        pageForm  = aflovacationService.avacation(account.getCompanyID(),pageForm,pageNumber,beginDate,endDate);

        return "schoolVacation";
    }

    /**
     * ajax驾校休假详情 *
     *
     * @return
     **/
    public String ajaxLeaveTheDetails(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        Object object = aflovacationService.leaveTheDetails(tbElycSchoolRest.getEsrId());

        json.accumulate("object",object);
        result = json.toString();

        return "success";
    }


    /**
     * ajax删除驾校休假信息 *
     *
     * @return
     **/
    public String ajaxDelLeaveThe(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        boolean bl = aflovacationService.delLeaveThe(tbElycSchoolRest.getEsrId());

        json.accumulate("boolean",bl);
        result = json.toString();

        return "success";
    }

    /**
     * ajax添加修改驾校休假信息 *
     *
     * @return 00:成功,01:时间格式不正确,02:教练已排班无法休假
     **/
    public String ajaxAddOrUpdateLeaveThe(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        String status = aflovacationService.addOrUpdateLeaveThe(tbElycSchoolRest,beginDate,endDate,account);

        json.accumulate("status",status);
        result = json.toString();

        return "success";
    }








    /**
     * 教练休班列表 *
     *
     * @return
     */
    public String coachVeave(){

        CAccount account = aflovacationService.companyInformation();

        if(account==null)
        {
            return "nologin";
        }
        pageForm  = aflovacationService.cVeave(account.getCompanyID(),pageForm,pageNumber,beginDate,tbJpTeacher);

        return "coachVeave";
    }

    /**
     * ajax教练休班详情 *
     *
     * @return
     **/
    public String ajaxCoachVeaveDetails(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        Object object = aflovacationService.coachVeaveDetails(elycTrainerVacation.getEtvId());

        json.accumulate("object",object);
        result = json.toString();

        return "success";
    }


    /**
     * ajax删除教练休班信息 *
     *
     * @return
     **/
    public String ajaxDelCoachVeave(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        boolean bl = aflovacationService.delCoachVeave(elycTrainerVacation.getEtvId());

        json.accumulate("boolean",bl);
        result = json.toString();

        return "success";
    }


    /**
     * ajax查询教练员列表 *
     *
     * @return
     **/
    public String ajaxCoachList(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        pageForm = aflovacationService.coachList(account.getCompanyID(),pageForm,tbJpTeacher);

        json.accumulate("pageForm",pageForm);
        result = json.toString();

        return "success";
    }


    /**
     * ajax添加修改教练休班信息 *
     *
     * @return 00:成功,01:时间格式不正确,02:教练已排班无法休班
     **/
    public String ajaxAddOrUpdateCoach(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        String status = aflovacationService.addOrUpdateCoach(elycTrainerVacation,beginDate,endDate,account);

        json.accumulate("status",status);
        result = json.toString();

        return "success";
    }





    /**
     * 教练请假列表 *
     *
     * @return
     */
    public String coachAsksForLeave(){

        CAccount account = aflovacationService.companyInformation();

        if(account==null)
        {
            return "nologin";
        }
        pageForm  = aflovacationService.cafLeave(account.getCompanyID(),pageForm,pageNumber,beginDate,tbJpTeacher);

        return "coachAsksForLeave";
    }


    /**
     * ajax教练请假详情 *
     *
     * @return
     **/
    public String ajaxCafLeaveDetails(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        Object object = aflovacationService.cafLeaveDetails(elycTrainerLeave.getEtlId());

        json.accumulate("object",object);
        result = json.toString();

        return "success";
    }


    /**
     * ajax删除教练请假信息 *
     *
     * @return
     **/
    public String ajaxDelCafLeave(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        boolean bl = aflovacationService.delCafLeave(elycTrainerLeave.getEtlId());

        json.accumulate("boolean",bl);
        result = json.toString();

        return "success";
    }

    /**
     * ajax查询替班教练员列表 *
     *
     * @return
     **/
    public String ajaxOffDutyCoachList(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        pageForm = aflovacationService.offDutyCoachList(account.getCompanyID(),pageForm,beginDate,tbJpTeacher);

        json.accumulate("pageForm",pageForm);
        result = json.toString();

        return "success";
    }


    /**
     * ajax添加修改教练请假信息 *
     *
     * @return 00:成功,01:时间格式不正确,02:请勿重复添加请假信息
     **/
    public String ajaxAddOrUpdateAskForLeave(){
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }

        String status = aflovacationService.addOrUpdateAskForLeave(elycTrainerLeave,beginDate,endDate,leaveData,account,substitutename);

        json.accumulate("status",status);
        result = json.toString();

        return "success";
    }



    /**
     * ajax查询教练是否已约 *
     *
     * @return
     **/
    public String ajaxIsAbout() throws ParseException {
        CAccount account = aflovacationService.companyInformation();

        JSONObject json = new JSONObject();
        if(account==null)
        {
            json.accumulate("nologin",true);
            result = json.toString();
            return "success";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Integer count = elkcCompanyConfigService.getOrderDetailTimeStatusCount(account.getCompanyID(),tbJpTeacher.getTeacherId(),sdf.parse(beginDate),sdf.parse(endDate),"1");

        json.accumulate("count",count);
        result = json.toString();

        return "success";
    }












    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public ProductPackaging getPpk() {
        return ppk;
    }

    public void setPpk(ProductPackaging ppk) {
        this.ppk = ppk;
    }

    public TEshopCusCom getTcc() {
        return tcc;
    }

    public void setTcc(TEshopCusCom tcc) {
        this.tcc = tcc;
    }

    public CAccount getCaccount() {
        return caccount;
    }

    public void setCaccount(CAccount caccount) {
        this.caccount = caccount;
    }

    public TbElycSchoolRest getTbElycSchoolRest() {
        return tbElycSchoolRest;
    }

    public void setTbElycSchoolRest(TbElycSchoolRest tbElycSchoolRest) {
        this.tbElycSchoolRest = tbElycSchoolRest;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ElycTrainerVacation getElycTrainerVacation() {
        return elycTrainerVacation;
    }

    public void setElycTrainerVacation(ElycTrainerVacation elycTrainerVacation) {
        this.elycTrainerVacation = elycTrainerVacation;
    }

    public TbJpTeacher getTbJpTeacher() {
        return tbJpTeacher;
    }

    public void setTbJpTeacher(TbJpTeacher tbJpTeacher) {
        this.tbJpTeacher = tbJpTeacher;
    }

    public String getLeaveData() {
        return leaveData;
    }

    public void setLeaveData(String leaveData) {
        this.leaveData = leaveData;
    }

    public ElycTrainerLeave getElycTrainerLeave() {
        return elycTrainerLeave;
    }

    public void setElycTrainerLeave(ElycTrainerLeave elycTrainerLeave) {
        this.elycTrainerLeave = elycTrainerLeave;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSubstitutename() {
        return substitutename;
    }

    public void setSubstitutename(String substitutename) {
        this.substitutename = substitutename;
    }
}
