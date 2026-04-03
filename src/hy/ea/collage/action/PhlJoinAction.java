package hy.ea.collage.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffCertificate;
import hy.ea.bo.office.CarInformation;
import hy.ea.collage.service.PhlIndexSerivce;
import hy.ea.collage.service.PhlJoinService;
import hy.ea.driving.service.StaffManageService;
import hy.ea.util.Constant;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyc on 2019-01-21.
 * 拼货拉车主加盟
 */

@Controller
@Scope("prototype")
public class PhlJoinAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private PhlJoinService phlJoinSerivce;
    private PageForm pageForm;
    private String result;
    private StaffCertificate credentials;//证件
    private CarInformation carInformation;//车辆信息
    private Staff staff;//人员
    private String driverId;
    private String drivingId;
    private File file;//上传文件
    private String fileFileName;//文件名
    private String marketId;//所选市场

    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    SessionWrap sw = SessionWrap.getInstance();
    TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
            SessionWrap.KEY_SHOPCUSCOM);

    /**
     * 初始化有车加入页面
     * @return
     */
    public String initCarJoin(){
        String hql="from Staff s where s.staffID=?";
        Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{tc.getStaffid()});
        List<BaseBean> marketList=phlJoinSerivce.getMarketList(Constant.NMSC_ID,staff.getStaffID());
        List<BaseBean> carType=phlJoinSerivce.getCarTypeOrUse(tc.getCompanyId(),Constant.CARTYPE_ID);
        List<BaseBean> carUse=phlJoinSerivce.getCarTypeOrUse(tc.getCompanyId(),Constant.CARUSE_ID);
        String driverId="";
        StaffCertificate cre =null;
        cre=phlJoinSerivce.getCertificate(tc.getStaffid(),"驾驶证");
        if(cre!=null){
            driverId=cre.getCredentialsNo();
        }
        /*String drivingId="";
        cre=phlJoinSerivce.getCertificate(tc.getStaffid(),"行驶证");
        if(cre!=null){
            drivingId=cre.getCredentialsNo();
        }*/
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("phone",tc.getAccount());//手机号
        map.put("name",staff.getStaffName());//登录人名
        map.put("idCard",staff.getStaffIdentityCard());//身份证
        map.put("driverId",driverId);//驾驶证
        //map.put("drivingId",drivingId);//行驶证
        map.put("marketList",marketList);
        map.put("carTypeList",carType);
        map.put("carUseList",carUse);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }

    /**
     * 保存车辆信息，证件信息，更新员工信息
     * @return
     */
    public String saveJoinOwnCar(){
        phlJoinSerivce.saveCarjoin(carInformation,driverId,drivingId,tc,staff,file,fileFileName,marketId);
        return "success";
    }

    /**
     * 获取加入拼货拉所有车辆
     * @return
     */
    public String getPhlCarList(){
        pageForm = phlJoinSerivce.getPageFormCar((null != pageForm ? pageForm.getPageNumber() : 1), 10,Constant.NMSC_ID,marketId);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pageForm",pageForm);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }

    /**
     * 验证车牌号、发动机号是否已注册
     * @return
     */
    public String checkUnique(){
        String drivingId=request.getParameter("drivingId");
        String carNum=request.getParameter("carNum");
        String engineNum=request.getParameter("engineNum");
        String count=phlJoinSerivce.checkUnique(drivingId,carNum,engineNum,Constant.NMSC_ID);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("count",count);
        JSONObject jo = JSONObject.fromObject(map);
        result = jo.toString();
        return "success";
    }


    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public StaffCertificate getCredentials() {
        return credentials;
    }

    public void setCredentials(StaffCertificate credentials) {
        this.credentials = credentials;
    }

    public CarInformation getCarInformation() {
        return carInformation;
    }

    public void setCarInformation(CarInformation carInformation) {
        this.carInformation = carInformation;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDrivingId() {
        return drivingId;
    }

    public void setDrivingId(String drivingId) {
        this.drivingId = drivingId;
    }

}
