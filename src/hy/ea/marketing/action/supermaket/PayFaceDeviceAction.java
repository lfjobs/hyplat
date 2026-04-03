package hy.ea.marketing.action.supermaket;


import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.*;
import hy.ea.bo.CAccount;
import hy.ea.marketing.service.PayFaceDeviceSerivce;
import hy.ea.marketing.service.PosDeviceManageSerivce;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.*;
import org.hibernate.criterion.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 刷脸设备维护
 */
@Controller
@Scope("prototype")
public class PayFaceDeviceAction {
    @Resource
    private ServerService serverService;
    @Resource
    private PayFaceDeviceSerivce faceDeviceSerivce;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ShowExcelService excelService;
    @Resource
    private UpLoadFileService fileService;
    private InputStream excelStream;
    private String result;
    private PayFaceDevice payDevice;
    private StoreBindDevice bindDevice;
    private BindDevice bdevice;
    private SetPoster setPoster;
    private File file;
    private String fileFileName;
    private String search;
    private PageForm pageForm;
    private int pageNumber;
    private String companyID;
    private String deviceType;
    CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
    private String parameter;


    public String toSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("payDevice", payDevice);
        return getFaceDeviceList();
    }



    private DetachedCriteria getList() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String companyID = account.getCompanyID();
        DetachedCriteria dc = DetachedCriteria.forClass(PayFaceDevice.class);
        PayFaceDevice payDevice= (PayFaceDevice) session.get("payDevice");

        dc.add(Restrictions.eq("companyID", companyID));
        dc.addOrder(Order.desc("createDate"));
        if (search != null && search.equals("search")) {

            if (payDevice.getSn() != null && !"".equals(payDevice.getSn())) {
                dc.add(Restrictions.eq("sn", payDevice.getSn()));

            }
            if (payDevice.getActiveState()!= null && !"".equals(payDevice.getActiveState())) {
                dc.add(Restrictions.eq("activeState", payDevice.getActiveState()));

            }

            if (payDevice.getBindState()!= null && !"".equals(payDevice.getBindState())) {
                dc.add(Restrictions.eq("bindState", payDevice.getBindState()));

            }
        }
        return dc;
    }

    // 终端列表
    public String getFaceDeviceList() {
        pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), getList());
        return "paylist";
    }

    /**
     * 添加修改
     *
     * @return
     */
    public String addOrUpdate() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "login";
        }
        faceDeviceSerivce.addOrUpdate(payDevice, account.getCompanyID(), account.getStaffID());

        return "success";
    }

    /**
     * 删除POS终端
     *
     * @return
     */
    public String deletePos() {
        if (payDevice != null && payDevice.getPfdkey() != null && !payDevice.getPfdkey().equals("")) {
            faceDeviceSerivce.delete(payDevice.getPfdkey());
        }

        return "success";
    }


    /**
     *
     * 判断设备号是否重复
     * @return
     */
    public  String  checkRepPosNum(){

          String result = faceDeviceSerivce.checkRepPosNum(payDevice.getSn(),payDevice.getPfdID());
          Map<String,Object> map = new HashMap<String,Object>();
          map.put("result",result);
          JSONObject jo = JSONObject.fromObject(map);
          this.result = jo.toString();
          return "success";
    }


    /**
     *
     * 判断商户是否签约
     * @return
     */
    public  String  checkComPosNum(){

        String result = faceDeviceSerivce.checkComPosNum(bindDevice.getSubCompanyID(),bindDevice.getSbdID());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("result",result);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


    /**
     * 导出Excel
     * @return
     */
    public String showExcel(){
        List<BaseBean> list = baseBeanService.getListByDC(getList());
        excelStream = excelService.showExcel(PayFaceDevice.columnHeadings(),list);

        return "showexcel";
    }

    /**
     * 设备分配业务员
     * @return
     */
    public  String bindClerk(){
        if(account==null){
            return "nologin";
        }
        faceDeviceSerivce.bindClerk(payDevice.getSn(),payDevice.getClearkAccount(),account.getStaffID());

        return "success";
    }


    /**
     * 设备分配业务员
     * @return
     */
    public  String checkUser(){
        Object obj = faceDeviceSerivce.checkUser(payDevice.getClearkAccount());
        Map<String,Object> map = new HashMap<String,Object>();

        if(obj==null){
            map.put("result","false");  //不存在账号

        }else{
            map.put("result","true");//存在账号
        }
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }


    /**
     * 解除绑定设备与业务员
     * @return
     */
    public String removeClerk(){
        CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
        if(account==null){

               return "nologin";

        }
        faceDeviceSerivce.removeClerk(payDevice.getSn(),payDevice.getClearkAccount(),account.getStaffID());

        return "success";

    }


    //////////////////////////////////////////////////////商户绑定////////////////////////////////////////////////////////////////////////////

    // 商户签约

    public String getStoreBindList() {
        pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 10 : pageNumber), getBindList());
        return "bindlist";
    }

    public String toBindSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("bindDevice", bindDevice);
        return getStoreBindList();
    }



    private DetachedCriteria getBindList() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String companyID = account.getCompanyID();
        DetachedCriteria dc = DetachedCriteria.forClass(StoreBindDevice.class);
        StoreBindDevice bindDevice= (StoreBindDevice) session.get("bindDevice");

        dc.add(Restrictions.eq("companyID", companyID));
        if (search != null && search.equals("search")) {

            if (bindDevice.getStoreName() != null && !"".equals(bindDevice.getStoreName())) {
                dc.add(Restrictions.like("storeName", bindDevice.getStoreName(),MatchMode.ANYWHERE));

            }
            if (bindDevice.getSubAppID() != null && !"".equals(bindDevice.getSubAppID())) {
                dc.add(Restrictions.eq("subAppID", bindDevice.getSubAppID()));

            }

        }
        return dc;
    }
    /**
     * 导出Excel
     * @return
     */
    public String showBindExcel(){
        List<BaseBean> list = baseBeanService.getListByDC(getBindList());
     //   excelStream = excelService.showExcel(StoreBindDevice.columnHeadings(),list);

        return "bindexcel";
    }

    /**
     * 解除绑定设备和商户之间
     * @return
     */
    public String removeStoreBind(){
        if(account==null){
            return "nologin";
        }

        faceDeviceSerivce.removeStoreBind(bdevice.getSn(),bdevice.getSubCompanyID(),account.getStaffID());

        return "success";

    }


    /**
     * 删除商户
     *
     * @return
     */
    public String deleteStore() {

        faceDeviceSerivce.deleteStore(bindDevice.getSbdKey());


        return "success";
    }

    /**
     *
     * 获取商家
     * @return
     */
    public String getCompanyList(){
         pageForm = faceDeviceSerivce.getCompanyList((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 15 : pageNumber),parameter);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pageForm", pageForm);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }

    /**
     *
     * 商户绑定设备
     * @return
     */
    public String storeBindDevice(){
        if(account==null){
            return "nologin";
        }

        faceDeviceSerivce.storeBindDevice(bdevice.getSn(),bdevice.getSubCompanyID(),account.getStaffID());

        return "success";

    }

    /**
     *全部
     * 调整费率
     * @return
     */
    public String changeRate(){
        if(account==null){
            return "nologin";
        }

        faceDeviceSerivce.changeRate(bindDevice.getSubCompanyID(),bindDevice.getStoreRate(),account.getStaffID());
        return "success";
    }


    /**
     * 添加修改
     *
     * @return
     */
    public String addStoreOrUpdate() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return "login";
        }

        faceDeviceSerivce.addStoreOrUpdate(bindDevice, account.getCompanyID(), account.getStaffID());

        return "success";
    }

    /**
     *
     * 获取商户绑定的所有设备
     * @return
     */
    public String getStoreAllDevice(){

        if (search != null && search.equals("search")) {
            Map<String, Object> session = ActionContext.getContext().getSession();
            BindDevice bdevice= (BindDevice) session.get("bdevice");
            if(bdevice!=null&&bdevice.getSn()!=null&&!bdevice.getSn().equals("")){
                parameter = bdevice.getSn();
            }

        }
        pageForm = faceDeviceSerivce.getStoreAllDevice((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 15 : pageNumber),bindDevice.getSubCompanyID(),parameter);
        return "devicelist";
    }

    /**
     *
     * 验证序列号是否可用
     * @return
     */
    public String checkBindSn(){
         String result1 = faceDeviceSerivce.checkSn(bdevice.getSn());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result1);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";

    }
    public String toBDeviceSearch() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("bdevice", bdevice);
        return getStoreAllDevice();
    }

    /**
     * 验证商户是否绑定设备如果绑定不准删除也不准修改
     * @return
     */
    public String checkStoreHaveDevice(){
        String result1 = faceDeviceSerivce.checkStoreDevice(bindDevice.getSubCompanyID());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", result1);
        JSONObject obj = JSONObject.fromObject(map);
        result = obj.toString();
        return "success";
    }
//////////////////////////////////////////////////海报设置/////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     *
     *
     * @return
     */
    public String getPosterList(){

        if (search != null && search.equals("search")) {
            Map<String, Object> session = ActionContext.getContext().getSession();
            SetPoster setPoster= (SetPoster) session.get("setPoster");
            if(setPoster!=null&&setPoster.getPosterName()!=null&&!setPoster.getPosterName().equals("")){
                parameter = setPoster.getPosterName();
            }

        }
        pageForm = faceDeviceSerivce.getPosterList((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber == 0 ? 15 : pageNumber),account.getCompanyID(),parameter,deviceType);

        return  "posterlist";
    }


    public String toSearchPoster() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("setPoster", setPoster);
        return getPosterList();
    }

    /**
     *
     *  海报上线下线
     * @return
     */
    public String onOffLine(){
        faceDeviceSerivce.onOffLine(setPoster.getSpID(),setPoster.getIsPublish(),account.getStaffID());
        return "success";
    }

    /**
     *
     *  海报排序
     * @return
     */
    public String posterSorts(){

        faceDeviceSerivce.posterSorts(setPoster.getSpID(),setPoster.getSorts());

        return "success";
    }

    /**
     * 删除POS终端
     *
     * @return
     */
    public String deletePoster() {
        if (setPoster != null && setPoster.getSpKey() != null && !setPoster.getSpKey().equals("")) {
            faceDeviceSerivce.deletePoster(setPoster.getSpKey());
        }

        return "success";
    }

    public  String addPoster(){

        String path= ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
        if(file!=null) {
            String posterPath = fileService.savePhoto(path, fileFileName,
                    file, account.getCompanyID(), "/wxface/poster/"
                            + Utilities.getDateString(new Date(), "yyyy-MM-dd"));

            setPoster.setPosterPath(posterPath);
        }
        setPoster.setCompanyID(account.getCompanyID());
        setPoster.setInputID(account.getStaffID());
        faceDeviceSerivce.addPoster(setPoster);

        return "success";
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public PageForm getPageForm() {
        return pageForm;
    }

    public void setPageForm(PageForm pageForm) {
        this.pageForm = pageForm;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public PayFaceDevice getPayDevice() {
        return payDevice;
    }

    public void setPayDevice(PayFaceDevice payDevice) {
        this.payDevice = payDevice;
    }

    public CAccount getAccount() {
        return account;
    }

    public void setAccount(CAccount account) {
        this.account = account;
    }


    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public StoreBindDevice getBindDevice() {
        return bindDevice;
    }

    public void setBindDevice(StoreBindDevice bindDevice) {
        this.bindDevice = bindDevice;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public BindDevice getBdevice() {
        return bdevice;
    }

    public void setBdevice(BindDevice bdevice) {
        this.bdevice = bdevice;
    }

    public SetPoster getSetPoster() {
        return setPoster;
    }

    public void setSetPoster(SetPoster setPoster) {
        this.setPoster = setPoster;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}