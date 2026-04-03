package hy.ea.office.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.BenDis.Sign;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.office.*;
import hy.ea.office.service.MakeAppointmentService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
public class MakeAppointmentAction {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private MakeAppointmentService maaService;

    private CarInformation carInformation;//车辆表
    private VehicleBinding vehicleBinding;//车辆人员关系表
    private VenueInformation venueInformation;//场地表
    private ExaminationRoom examinationRoom;//考场表
    private ExaminationRoomCharge examinationRoomCharge;//考场收费表
    private ContactCompany concom;
    private ProductPackaging ppk;//产品表
    private BookingInformation bookingInformation;//预约信息表
    private Sign sign;
    private TEshopCusCom tcc;//账号表
    private String sccId;//账号id
    private String cbId;//订单id
    private List<BaseBean> list;
    private PageForm pageForm;
    private Object object;
    private String result;
    private String type;
    private String conditions;
    private String companyId;
    private boolean bl;
    private String phone;

    /**
     * 跳转登录
     * @return
     */
    public String goBackLogin() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("url");
        HttpServletRequest request = ServletActionContext.getRequest();
        String url = request.getHeader("Referer");
        session.setAttribute("url", url);
        return "login";
    }


    /**
     * 注册
     *
     * @return
     */
    public String parkingRegistrationSharing() {

        return "parkingRegistrationSharing";
    }

    /**
     * 注册绑定车牌
     *
     * @return
     */
    public String registeredViewVehicle() {

        return "registeredViewVehicle";
    }


    /**
     * 注册绑定车辆
     *
     * @return
     */
    public String registeredAddVehicle() {

        bl = true;

        tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",new Object[]{tcc.getAccount()});

        try {
            maaService.addVehicle(carInformation,tcc);
        } catch (Exception e) {
            bl = false;
        }

        JSONObject json = new JSONObject();
        json.accumulate("bl",bl);
        this.result = json.toString();
        return "success";
    }



    /**
     * 跳转停车介绍页面
     *
     * @return
     */
    public String parkingIsIntroduced() {

        if (sccId!=null && !sccId.equals(""))
        {
            maaService.addUser(sccId);
        }
        tcc =  maaService.queryUser();

        if (tcc==null)
        {
            return goBackLogin();
        }

        return "parkingIsIntroduced";
    }



    /**
     * 查询停车场地list
     *
     * @return
     */
    public String parkingSpacesList() {

        pageForm = maaService.parkingSpacesList(pageForm.getPageNumber(),pageForm.getPageSize(),venueInformation);

        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        this.result = json.toString();
        return "success";
    }

    /**
     * 查询停车场地详情
     *
     * @return
     */
    public String parkingSpacesDetails() {

        tcc =  maaService.queryUser();

        if (tcc==null)
        {
            return goBackLogin();
        }

        object = maaService.parkingSpacesDetails(venueInformation);

        return "parkingSpacesDetails";
    }


    /**
     * 查询停车场包月/年套餐
     *
     * @return
     */
    public String queryPlan() {

        list = maaService.queryPlan(venueInformation.getSiteId());

        JSONObject json = new JSONObject();
        json.accumulate("list",list);
        this.result = json.toString();
        return "success";
    }

    /**
     *
     * 验证是否购买过包天
     * @return
     */
    public String checkbday(){
         HttpServletRequest request = ServletActionContext.getRequest();
         String carNumber = request.getParameter("carNumber");
         String ppid = request.getParameter("ppid");
         String result =  maaService.checkbday(carNumber,ppid);

         JSONObject json = new JSONObject();
         json.accumulate("result",result);
         this.result = json.toString();
         return "success";

    }

    /**
     * 停车倒计时
     *
     * @return
     */
    public String countdown() {

        tcc =  maaService.queryUser();

        if (tcc==null)
        {
            return goBackLogin();
        }

        list = maaService.countdown(tcc);

        object = maaService.parkingRecord_is(tcc);

        return "countdown";
    }


    /**
     * 查询停车记录(历史)
     *
     * @return
     */
    public String parkingRecord() {

        tcc =  maaService.queryUser();

        pageForm = maaService.parkingRecord_history(pageForm.getPageNumber(),pageForm.getPageSize(),tcc);

        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        this.result = json.toString();
        return "success";
    }
    /**
     * 查询绑定车辆
     *
     * @return
     */
    public String viewVehicle() {

        if (tcc!=null && tcc.getAccount()!=null && !tcc.getAccount().equals(""))
        {
            tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",new Object[]{tcc.getAccount()});
        }
        else
        {
            tcc =  maaService.queryUser();
        }

        if (tcc==null)
        {
            return goBackLogin();
        }

        list = maaService.viewVehicle(tcc);

        phone = tcc.getAccount();

        if (list.size()!=0)
        {
            return "bindingStateHave";
        }
        else
        {
            return "bindingStateWithout";
        }
    }


    /**
     * 跳转新增车辆
     *
     * @return
     */
    public String newVehicle() {

        if (tcc!=null && tcc.getAccount()!=null && !tcc.getAccount().equals(""))
        {
            tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",new Object[]{tcc.getAccount()});
        }
        else
        {
            tcc =  maaService.queryUser();
        }

        if (tcc==null)
        {
            return goBackLogin();
        }

        phone = tcc.getAccount();

        return "bindingStateWithout";
    }

    /**
     * 查询历史绑定车辆
     *
     * @return
     */
    public String HistoryBindingVehicle() {

        tcc =  maaService.queryUser();

        pageForm = maaService.HistoryBindingVehicle(tcc,pageForm.getPageNumber(),pageForm.getPageSize());

        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        this.result = json.toString();
        return "success";
    }

    /**
     * 解除车辆绑定
     *
     * @return
     */
    public String delVehicle() {

        tcc =  maaService.queryUser();

        if (tcc==null)
        {
            return goBackLogin();
        }

        maaService.delVehicle(carInformation,tcc);

        return "success";
    }


    /**
     * 绑定车辆
     *
     * @return
     */
    public String addVehicle() {

        tcc =  maaService.queryUser();

        if (tcc==null)
        {
            return goBackLogin();
        }

        maaService.addVehicle(carInformation,tcc);

        return "success";
    }
    
    

    /**
     * 扫码结算时绑定车辆
     *
     * @return
     */
    public String bindVehicle() {

        tcc =  maaService.queryUser();

        if (tcc==null)
        {
            return goBackLogin();
        }

        //判断车牌号是否有最新进入记录如果没有请确认车牌号是否正确或者联系工作人员进入车牌号识别错误，处理误差
        Map<String,Object> map = new HashMap<String,Object>();
        String r = maaService.isExistCar(tcc.getStaffid(), carInformation.getCarNum());

        if("0".equals(r)){
            //识别记录离开状态并且未支付绑定车辆
            maaService.addVehicle(carInformation,tcc);
            map.put("result","2");//成功绑定车辆
        }else{
            map.put("result","5");//该车牌已绑定过了
        }
       JSONObject jo = JSONObject.fromObject(map);
       this.result = jo.toString();
       return "success";
    }


    /**
     * 验证车牌是否绑定
     *
     * @return
     */
    public String validation() {

        bl = maaService.validation(carInformation);

        JSONObject json = new JSONObject();
        json.accumulate("bl",bl);
        this.result = json.toString();
        return "success";
    }
    
    
    
    /**
     * 
     * 获取当前的登录账号的所有绑定的车牌
     * @return
     */
   public String getAllCarNum(){
	   tcc =  maaService.queryUser();

       if (tcc==null)
       {
           return goBackLogin();
       }
       
	   List<BaseBean> carlist = maaService.getAllCarNum(tcc.getStaffid());
	   HttpServletRequest request = ServletActionContext.getRequest();
	   request.setAttribute("carlist", carlist);
	   
	   return  "carlist";
   }
   
   /**
    * 
    * 跳回收费页
    * @return
    */
   public String parkingIndex(){
	   HttpServletRequest request = ServletActionContext.getRequest();
	   String carNum = request.getParameter("carNum");
	   String staffID = request.getParameter("staffID");
	   String equip = request.getParameter("equip");
       String status = request.getParameter("status");

       request.setAttribute("carNum", carNum);

       if(carNum!=null&&!carNum.equals("")){
           //如果绑定了车牌号
           CarManage  carManage = maaService.getCarInRecord(carNum,status,equip);
           request.setAttribute("carManage", carManage);
           List<Object> list =  maaService.searchFeeScale(carNum,equip);
           if(list!=null&&list.size()>0) {
               Object[] obj = (Object[]) list.get(0);

               request.setAttribute("price", obj[1].toString());
               request.setAttribute("ppid", obj[0].toString());
               request.setAttribute("timeUnits", obj[3].toString());
               Company y = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{obj[2].toString()});

               request.setAttribute("companyName", y.getCompanyName());
           }
           request.setAttribute("feelist", list);

       }

	   
	   return "parking";
	   
	   
   }
   /**
    * 
    * 验证选择的车牌号有没有进出记录
    * @return
    */
   public String validateCarNum(){
	   HttpServletRequest request = ServletActionContext.getRequest();
	   String carNum = request.getParameter("carNum");
       String status = request.getParameter("status");
       String equip = request.getParameter("equip");


       tcc =  maaService.queryUser();

       if (tcc==null)
       {
           return goBackLogin();
       }
       
       //判断车牌号是否有最新进入记录如果没有请确认车牌号是否正确或者联系工作人员进入车牌号识别错误，处理误差
       Map<String,Object> map = new HashMap<String,Object>();
       CarManage carManage =  maaService.getCarInRecord(carNum,status,equip);
       if(carManage==null){
       	map.put("result","0");//不存在该记录
       }else if("1".equals(carManage.getStatus())){
       	map.put("result","1");//只有进入记录没有出记录，请识别车牌 
       	//推送
       }else if("0".equals(carManage.getStatus())&&"00".equals(carManage.getChargeType())){
       	  //识别记录离开状态并且未支付绑定车辆
       	 map.put("result","2");//成功绑定车辆
      
       }else if("0".equals(carManage.getStatus())&&"01".equals(carManage.getChargeType())){
    
     	    map.put("result","3");//已支付
     	
       }else if("2".equals(carManage.getStatus())&&carManage.getChargeType()==null){
    
     	    map.put("result","4");//未支付异常
     	
       }

      JSONObject jo = JSONObject.fromObject(map);
      this.result = jo.toString();
      return "success";
	
   }






    /**-------------------------------驾校预约----------------------------------**/

    /**
     * 跳转购买
     *
     * @return
     */
    public String jumpToBuy() {

        object = maaService.jumpToBuy(companyId);

        return "jumpToBuy";
    }

    /**
     * 跳转
     *
     * @return
     */
    public String jump() {

        HttpServletRequest request = ServletActionContext.getRequest();

        request.setAttribute("companyID", companyId);

        if (type.equals("教练"))
        {
            return "coachList";
        }
        else if (type.equals("主管"))
        {
            return "headList";
        }
        else
        {
            return "testList";
        }
    }



    /**
     * 查询考场list
     *
     * @return
     */
    public String testList() {

        pageForm = maaService.testList(pageForm.getPageNumber(),pageForm.getPageSize(),conditions,ppk.getCompanyID());

        JSONObject json = new JSONObject();

        json.accumulate("pageForm",pageForm);

        result = json.toString();

        return "success";
    }


    /**
     * 查询教练员list
     *
     * @return
     */
    public String coachOrheadList() {

        pageForm = maaService.coachOrheadList(pageForm.getPageNumber(),pageForm.getPageSize(),conditions,ppk.getCompanyID(),type);

        JSONObject json = new JSONObject();

        json.accumulate("pageForm",pageForm);

        result = json.toString();

        return "success";
    }

    /**
     * 查询金币数是否足够
     *
     * @return
     */
    public String goldCoins() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String tel = request.getParameter("tel");
        String name =  request.getParameter("name");
        String tuistaffID =  request.getParameter("tuistaffID");
        if(tel==null||tel.equals("")) {
            tcc = maaService.queryUser();
        }else{
            tcc = maaService.queryWfjtc(tel,name,tuistaffID);
            if(tcc!=null&&(tcc.getAccount()==null||tcc.getAccount().equals(""))){
                tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where staffid = ? and acquiesce = ?", new Object[]{tcc.getStaffid(), "01"});

            }

        }

        String ss = maaService.goldCoins(tcc,examinationRoom.getErId());

        JSONObject json = new JSONObject();

        json.accumulate("ss",ss);

        json.accumulate("tcc",tcc);
        if(tcc!=null) {
            json.accumulate("sccId", tcc.getSccId());
        }

        result = json.toString();

        return "success";
    }


    /**
     * 预约
     *
     * @return
     */
    public String buySuccess() {
      if(bookingInformation.getSccId()!=null&&!bookingInformation.getSccId().equals("")){
          tcc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId  = ?",new Object[]{bookingInformation.getSccId()});
      }else {
          tcc = maaService.queryUser();
      }

        object = maaService.buySuccess(tcc,bookingInformation);

        JSONObject json = new JSONObject();

        json.accumulate("sccid",tcc.getSccId());

        json.accumulate("object",object);

        result = json.toString();

        return "success";
    }

    /**
     * 预约成功
     *
     * @return
     */
    public String successful() {

        return "buySuccess";
    }


    /**
     * 查询预约详情
     *
     * @return
     */
    public String bookingDetails() {
        HttpServletRequest request = ServletActionContext.getRequest();

        object = maaService.bookingDetails(cbId,sccId);
        String jifen = maaService.getJiFen(sccId);
        String jinbi = maaService.getJinBi(sccId);
        request.setAttribute("jifen",jifen);
        request.setAttribute("jinbi",jinbi);
        TEshopCusCom tc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId  = ?",new Object[]{sccId});
        request.setAttribute("user",tc.getAccount());

        return "bookingDetails";
    }




    /**
     * 快捷查询考场计时
     *
     * @return
     */
    public String theTestTime() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String posNum = request.getParameter("posNum");
        String comID = "";
        String companyname = "";
        if(posNum!=null&&!posNum.equals("")){
            Map<String,String> map  =  maaService.getComIDByPosNum(posNum);
            if(map.size()>0) {
                try {
                    comID = map.get("companyId");
                    companyname = map.get("companyname");
                    request.setAttribute("companyname",companyname);
                }catch (Exception e){
                    companyname = "溯源互帮";
                }
            }
        }
        if("".equals(comID)) {
            if (sccId != null && !sccId.equals("")) {
                maaService.addUser(sccId);
            }

            tcc = maaService.queryUser();

            if (tcc == null) {
                return goBackLogin();
            }
        }else{
           companyId = comID;
        }

        if (companyId!=null && !companyId.equals(""))
        {
            ppk = maaService.theTestTime(companyId);
        }
        if("".equals(comID)) {
            bl = maaService.userJudge(sccId, companyId);
        }else{
            bl = true;
        }

        //当前用户是否是该驾校的审核员true:是,false:不是
        if (bl)
        {
            return "theTestTimeTrue";
        }
        else
        {
            return "theTestTimeFalse";
        }
    }

    /**
     *
     * 根据手机号获取学员姓名
     * @return
     */
    public String getStudentName(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String tel = request.getParameter("tel");
        String stuname = maaService.getStudentName(tel);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("stuname",stuname);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


    /**
     * 我的练车记录
     *
     * @return
     */
    public String myRecord() {

        pageForm = maaService.myRecord(sccId,companyId,pageForm.getPageNumber(),pageForm.getPageSize());

        JSONObject json = new JSONObject();

        json.accumulate("pageForm",pageForm);

        result = json.toString();

        return "success";
    }


    /**
     * 用户练车记录
     *
     * @return
     */
    public String userRecord() {

        pageForm = maaService.userRecord(sccId,companyId,pageForm.getPageNumber(),pageForm.getPageSize(),conditions);

        JSONObject json = new JSONObject();

        json.accumulate("pageForm",pageForm);

        result = json.toString();

        return "success";
    }


    /**
     * 查询驾校列表
     *
     * @return
     */
    public String drivingSchoolList() {

        tcc =  maaService.queryUser();

        if (tcc==null)
        {
            return goBackLogin();
        }

        return "drivingSchoolList";
    }

    /**
     * ajax查询考场列表
     *
     * @return
     */
    public String ajaxSchoolList() {

        pageForm = maaService.ajaxSchoolList(pageForm.getPageNumber(),pageForm.getPageSize(),conditions,examinationRoom.getItsLocation());

        JSONObject json = new JSONObject();

        json.accumulate("pageForm",pageForm);

        result = json.toString();

        return "success";
    }


    /**
     * 跳转资讯列表
     *
     * @return
     */
    public String informationList() {

        return "informationList";
    }


    /**
     * 查询资讯列表
     *
     * @return
     */
    public String ajaxInformationList() {

        pageForm = maaService.ajaxInformationList(pageForm.getPageNumber(),pageForm.getPageSize(),sccId);

        JSONObject json = new JSONObject();

        json.accumulate("pageForm",pageForm);

        result = json.toString();

        return "success";
    }


    /**
     * 签到详情
     *
     * @return
     */
    public String checkTheDetails() {

        object = maaService.checkTheDetails(conditions);

        list = maaService.informationList(ppk.getPpID());

        tcc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?",new Object[]{sccId});

        concom = (ContactCompany) baseBeanService.getBeanByHqlAndParams("from ContactCompany where ccompanyID = ?",new Object[] { "contactCompany20101230UB4U5884S30000000176" });

        return "checkTheDetails";
    }

    /**
     * 跳转考场页面
     *
     * @return
     */
    public String queryTheTest() {


        return "queryTheTest";
    }


    /**
     * ajax查询驾校列表
     *
     * @return
     */
    public String ajaxDrivingList() {

        pageForm = maaService.ajaxDrivingList(pageForm.getPageNumber(),pageForm.getPageSize(),conditions,concom.getCompanyAddr());

        JSONObject json = new JSONObject();

        json.accumulate("pageForm",pageForm);

        result = json.toString();

        return "success";
    }

    /**
     * 查询状态
     * @return
     */
    public String searchBookInfo(){
        BookingInformation book = maaService.getBookInfo(bookingInformation.getJournalNum());
       Map<String,Object> map = new HashMap<String,Object>();
       if(book!=null){
           map.put("state",book.getSignInState());
           map.put("howMuchTime",book.getHowMuchTime());
           map.put("money",book.getMoney());
       }

       JSONObject jo = JSONObject.fromObject(map);
       this.result = jo.toString();
        return "success";
    }


    /**
     *
     * 预约打印小票
     * @return
     */
   public String printInfo(){
       HttpServletRequest request = ServletActionContext.getRequest();
       String journalNum = request.getParameter("journalNum");

       List<Object> list = maaService.printInfo(journalNum);

       Map<String,Object> map = new HashMap<String,Object>();
       map.put("result",list.get(0));
       JSONObject jo = JSONObject.fromObject(map);
       this.result = jo.toString();
       return "success";
   }

    /**
     *
     * 人脸验证
     * @return
     */
   public String faceValidate(){
       HttpServletRequest request = ServletActionContext.getRequest();
       String  openid = request.getParameter("openid");
       Map<String,Object> map = maaService.faceValidate(openid);
       JSONObject jo = JSONObject.fromObject(map);
       this.result = jo.toString();
       return "success";
   }

    /**
     *
     * 绑定微分金账号
     * @return
     */
   public String bindWfjAccount(){
       HttpServletRequest request = ServletActionContext.getRequest();
       String  tel = request.getParameter("tel");
       String  openid = request.getParameter("openid");
       String  cardNum = request.getParameter("cardNum");
       Map<String,Object> map = maaService.bindWfjAccount(openid,tel,cardNum);
       JSONObject jo = JSONObject.fromObject(map);
       this.result = jo.toString();
       return "success";
   }
   
   
 

    public CarInformation getCarInformation() {
        return carInformation;
    }

    public void setCarInformation(CarInformation carInformation) {
        this.carInformation = carInformation;
    }

    public VehicleBinding getVehicleBinding() {
        return vehicleBinding;
    }

    public void setVehicleBinding(VehicleBinding vehicleBinding) {
        this.vehicleBinding = vehicleBinding;
    }

    public List<BaseBean> getList() {
        return list;
    }

    public void setList(List<BaseBean> list) {
        this.list = list;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public VenueInformation getVenueInformation() {
        return venueInformation;
    }

    public void setVenueInformation(VenueInformation venueInformation) {
        this.venueInformation = venueInformation;
    }

    public String getSccId() {
        return sccId;
    }

    public void setSccId(String sccId) {
        this.sccId = sccId;
    }

    public ProductPackaging getPpk() {
        return ppk;
    }

    public void setPpk(ProductPackaging ppk) {
        this.ppk = ppk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public ExaminationRoom getExaminationRoom() {
        return examinationRoom;
    }

    public void setExaminationRoom(ExaminationRoom examinationRoom) {
        this.examinationRoom = examinationRoom;
    }

    public ExaminationRoomCharge getExaminationRoomCharge() {
        return examinationRoomCharge;
    }

    public void setExaminationRoomCharge(ExaminationRoomCharge examinationRoomCharge) {
        this.examinationRoomCharge = examinationRoomCharge;
    }

    public BookingInformation getBookingInformation() {
        return bookingInformation;
    }

    public void setBookingInformation(BookingInformation bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public String getCbId() {
        return cbId;
    }

    public void setCbId(String cbId) {
        this.cbId = cbId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public boolean isBl() {
        return bl;
    }

    public void setBl(boolean bl) {
        this.bl = bl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public TEshopCusCom getTcc() {
        return tcc;
    }

    public void setTcc(TEshopCusCom tcc) {
        this.tcc = tcc;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign sign) {
        this.sign = sign;
    }

    public ContactCompany getConcom() {
        return concom;
    }

    public void setConcom(ContactCompany concom) {
        this.concom = concom;
    }
}
