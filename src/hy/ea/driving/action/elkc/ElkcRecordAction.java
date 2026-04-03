package hy.ea.driving.action.elkc;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.DrivingSchool.elyc.TbElycOrderRecord;
import hy.ea.driving.service.elkc.ElkcRecordService;
import hy.ea.driving.service.elkc.impl.ElkcRecordServiceImpl;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author xgb
 * @version 1.0
 * @describe 预约记录信息
 *
 */
@Controller
@Scope("prototype")
public class ElkcRecordAction {
    private Logger logger = LoggerFactory.getLogger(ElkcRecordAction.class);
    @Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
    @Resource
	private ElkcRecordService elkcRecordService;

    private PageForm pageForm;

    private CAccount caccount;

    private TbJpTeacher tbJpTeacher;

    private TbElycOrderRecord tbElycOrderRecord;

    private TbJpStudentInfo tbJpStudentInfo;

    private Object object;

    private String result;
    private  String staffId;




    /**
     * 跳转预约记录 *
     *
     * @return
     **/
    public String record(){

        caccount = elkcRecordService.goCaccount();

        if (caccount == null) {
            return elkcRecordService.mobileLogin();
        }

        //查询学员信息
        tbJpStudentInfo = elkcRecordService.studentInfo(caccount.getStaffID());


        return "record";
    }
    public String recorde(){

        //查询学员信息
        tbJpStudentInfo = elkcRecordService.studentInfo(staffId);


        return "record";
    }


    /**
     * ajax查询预约记录 *
     *
     * @return
     **/
    public String ajaxReservationRecord(){

        pageForm = elkcRecordService.reservationRecord(tbElycOrderRecord.getStudentId(),pageForm);

        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        result = json.toString();

        return "success";
    }



    /**
     * 跳转预约详情 *
     *
     * @return
     **/
    public String bookingDetails(){

//        caccount = elkcRecordService.goCaccount();
//
//        if (caccount == null) {
//            return elkcRecordService.mobileLogin();
//        }

        return "bookingDetails";
    }

    /**
     * ajax预约详情 *
     *
     * @return
     **/
    public String ajaxDetails(){

        object = elkcRecordService.bkDetails(tbElycOrderRecord.getEtoId());

        JSONObject json = new JSONObject();
        json.accumulate("object",object);
        result = json.toString();

        return "success";
    }



    /**
     * ajax取消预约记录 *
     *
     * @return
     **/
    public String ajaxCancellation(){

        elkcRecordService.cancellation(tbElycOrderRecord.getEtoId());

        JSONObject json = new JSONObject();
        result = json.toString();

        return "success";
    }






    /**
     * ajax计时练车 *
     *
     * @return 00:未登录,01:未报名,02:未预约,03:时间未到,04:练车开始
     **/
    public String ajaxTimingToPracticeCar(){

        Map<String,Object> map = elkcRecordService.timingToPracticeCar();

        JSONObject json = new JSONObject();
        json.accumulate("map",map);
        result = json.toString();

        return "success";
    }






    /**
     * 自动启动 *
     **/
    public void automaticStartup(){

        //获取前一天的日期字符串
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);
        String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

        try {
            elkcRecordService.bootUp(dayBefore);
        } catch (IOException e) {
            logger.error(dayBefore+"号同步elkc终端信息失败");
        } catch (ParseException e) {
            logger.error(dayBefore+"号同步elkc终端信息时时间格式转换错误");
        }

    }





    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public TbJpTeacher getTbJpTeacher() {
        return tbJpTeacher;
    }

    public void setTbJpTeacher(TbJpTeacher tbJpTeacher) {
        this.tbJpTeacher = tbJpTeacher;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public CAccount getCaccount() {
        return caccount;
    }

    public void setCaccount(CAccount caccount) {
        this.caccount = caccount;
    }

    public TbElycOrderRecord getTbElycOrderRecord() {
        return tbElycOrderRecord;
    }

    public void setTbElycOrderRecord(TbElycOrderRecord tbElycOrderRecord) {
        this.tbElycOrderRecord = tbElycOrderRecord;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public TbJpStudentInfo getTbJpStudentInfo() {
        return tbJpStudentInfo;
    }

    public void setTbJpStudentInfo(TbJpStudentInfo tbJpStudentInfo) {
        this.tbJpStudentInfo = tbJpStudentInfo;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
