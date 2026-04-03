package mobile.tiantai.android.action;

import com.opensymphony.xwork2.ActionSupport;
import hy.ea.bo.CCode;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.invoicing.InvCheckGoods;
import hy.ea.bo.invoicing.Inventory;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import mobile.tiantai.android.service.StockService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyc on 2018-09-11. 安卓端库存 ********  未使用  ********
 */

@Controller
@Scope("prototype")
public class StockAndroidAction extends ActionSupport {

    private Logger logger = LoggerFactory.getLogger(StockAndroidAction.class);
    @Resource
    private StockService stockService;
    public HttpServletRequest request = ServletActionContext.getRequest();

    private List<CCode> codeList;
    private Object result;
    private PageForm pageForm;
    public List<String> list = new ArrayList<String>();
    //public ServletRequest request;
    /** 每页显示条数 */
    private int pageSize=50;
    /** 页数 */
    private int pageNumber;
    /** 总数 */
    private int recordCount;
    private File file;//上传文件
    private String fileFileName;//文件名
    private String goodsId;// 物品id
    private String goodsName;//物品名
    private String goodsType;//物品类别
    private String barCode;//条码
    private String unit;//单位
    private String wareHouseId;//库id
    private String wareHouseName;//库名
    private String areaId;//区id
    private String areaName;//区名
    private String frameId;//架id
    private String frameName;//架名
    private String positionId;//位id
    private String positionName;//位名
    private String invenQuantity;//库存
    private String invenUnderline;//库存下限
    private String invenOnline;//库存上限
    private String unitPrice;//成本价
    private String purPrice;//进货价
    private String piFaPrice;//批发价
    private String startDate;//生产日期
    private String endDate;//保质到期日期
    private String standard;//规格
    //private String staffId;//操作人id
    private String staffName;//操作人名
    private String realQuantity;//实际数量
    private String breakReason;//报损原因
    private String auditorId;//审核人id
    private String status;//审核结果 02已审核 03驳回
    private String comments;//处理意见
    private String arrgoodsNum;//物品编号
    private String goodsIds;//所选物品id
    private String ccompanyId;//客户公司id
    private String subType;     //保存or提交审核
    private Object isNull(Object tep){
        if(tep==null||"null".equals(tep)){
            return "";
        }else{
            return tep;
        }
    }
    /**
     * 货品名、条码 查询货品库存列表/货品库存详情
     * @return
     */
    public String getGoodsListByName(){
        String companyId=request.getParameter("companyId");
        String goodsName=request.getParameter("goodsName");
        String goodsType=request.getParameter("goodsType");
        String inventoryId=request.getParameter("inventoryId");
        pageForm =stockService.getGoodsList(companyId,goodsName,goodsType,inventoryId,pageNumber,pageSize);
        if(pageForm!=null){
            if(pageForm.getList()!=null&&pageForm.getList().size()>0){
                JSONObject jsonObjList = new JSONObject();
                List<JSONObject> retJson = new ArrayList<JSONObject>();
                for(int i=0;i<pageForm.getList().size();i++){
                    Object[] obj = (Object[]) (Object)pageForm.getList().get(i);
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.accumulate("image",isNull(obj[0]));             //图片路径
                    jsonObj.accumulate("goodsName",isNull(obj[1]));         //货品名
                    jsonObj.accumulate("piFaPrice","");                     //批发价
                    jsonObj.accumulate("unitPrice",isNull(obj[2]));         //成本价
                    jsonObj.accumulate("purPrice",isNull(obj[3]));          //进货价
                    jsonObj.accumulate("invenQuantity",isNull(obj[4]));     //库存数量
                    jsonObj.accumulate("unit",isNull(obj[5]));              //单位
                    jsonObj.accumulate("goodsType",isNull(obj[6]));         //物品类别
                    jsonObj.accumulate("barCode",isNull(obj[7]));           //条码
                    jsonObj.accumulate("invenUnderline",isNull(obj[8]));    //库存下限
                    jsonObj.accumulate("invenOnline",isNull(obj[9]));       //库存上限
                    jsonObj.accumulate("standard",isNull(obj[10]));         //规格
                    jsonObj.accumulate("wareHouseName",isNull(obj[11]));    //库
                    jsonObj.accumulate("areaName",isNull(obj[12]));         //区
                    jsonObj.accumulate("frameName",isNull(obj[13]));        //架
                    jsonObj.accumulate("positionName",isNull(obj[14]));     //位
                    jsonObj.accumulate("inventoryId",isNull(obj[15]));      //库存id
                    jsonObj.accumulate("goodsId",isNull(obj[16]));          //物品编号
                    jsonObj.accumulate("goodsNum",isNull(obj[17]));         //物品排位
                    retJson.add(jsonObj);
                }
                jsonObjList.accumulate("list",retJson);                       //数据
                jsonObjList.accumulate("count",pageForm.getList().size());    //查询总数
                result=jsonObjList;
            }else {
                result="noData";
            }
        }
        return "success";
    }

    /**\
     * 获取公司所有物品类别
     * @return
     */
    public String getAllGoodsType(){
        String companyId=request.getParameter("companyId");
        List<Object> typeList=stockService.getGoodsType(companyId);
        JSONObject jsonObjList = new JSONObject();
        jsonObjList.accumulate("typeList",typeList);
        if(typeList!=null&&typeList.size()>0){
            result=jsonObjList;
        }else{
            result="noData";
        }
        return "success";
    }
    /**
     *  获取公司 库、区、架、位 名称
     * @return
     */
    public String getDepots(){
        //传入库房类型
         String companyId=request.getParameter("companyId");
        String depotType=request.getParameter("depotType");
        String depotpid=request.getParameter("depotpid");       //父的id为子的pid
        List<BaseBean> depotList=stockService.getDepots(companyId,depotType,depotpid);
        JSONObject jsonObjList = new JSONObject();
        jsonObjList.accumulate("depotList",depotList);
        if(depotList!=null&&depotList.size()>0){
            result=jsonObjList;
        }else{
            result="noData";
        }
        return "success";
    }

    /**
     * 新增货品
     * @return
     */
    public String saveGoods() {
        /**HttpSession httpsession = request.getSession();
        HttpSession httpsession=ServletActionContext.getRequest().getSession();
        Map<String, Object> session = ActionContext.getContext().getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom tcc = (TEshopCusCom) sw.getObject(httpsession, SessionWrap.KEY_SHOPCUSCOM);
        Map<String, Object> session = ActionContext.getContext().getSession();*/
        String companyId=request.getParameter("companyId");
        String staffId=request.getParameter("staffId");
        //String staffId=request.getParameter("staffId");
        Inventory inv=new Inventory();
        /**inv.setOrganizationID(organizationID);
         inv.setDepartmentID((String) session.get("organizaitonID"));*/
        inv.setCompanyID(companyId);
        inv.setStaffID(staffId);
        inv.setStaffName(staffName);
        inv.setGoodsName(goodsName);
        inv.setGoodsType(goodsType);
        inv.setBarcode(barCode);
        inv.setUnitPrice(unitPrice);
        inv.setPurPrice(purPrice);
        inv.setUnit(unit);
        inv.setWarehouse(wareHouseId);//库
        inv.setWarehouseName(wareHouseName);
        inv.setWeizhi(wareHouseName);
        inv.setArea(areaId);//区
        inv.setAreaName(areaName);
        inv.setFrame(frameId);//架
        inv.setFrameName(frameName);
        inv.setPosition(positionId);//位
        inv.setPositionName(positionName);
        inv.setInvenQuantity(invenQuantity);	//货品数量
        inv.setInvenUnderline(invenUnderline);//库存下限
        inv.setInvenOnline(invenOnline);//库存上限
        inv.setStartDate(startDate);
        inv.setEndDate(endDate);
        inv.setStandard(standard);//规格
        String s = "1";
        try {
            stockService.saveGoods(file,fileFileName,inv);
        } catch (Exception e) {
            s = "0";
            e.printStackTrace();
            logger.error("保存失败");
        }
            JSONObject json = new JSONObject();
            json.accumulate("s", s);
            result = json.toString();
            return "success";
    }

    /**
     * 盘点
     * @return
     */
    public String checkInv(){
        String companyId=request.getParameter("companyId");
        String inventoryId=request.getParameter("inventoryId");//库存id 用来更新库存
        InvCheckGoods ck=new InvCheckGoods();
        //ck.setCompanyID(companyId);
        /**ck.setStaffID(staffId);
        ck.setStaffName(staffName);*/
        ck.setRealQuantity(realQuantity);
        ck.setPrice(unitPrice);
        String s = "1";
        try {
            if(new Integer(realQuantity)>=0){
                stockService.checkInv(ck,inventoryId);
            }else{
                s = "0";
            }
        } catch (Exception e) {
            s = "0";
            e.printStackTrace();
            logger.error("保存失败");
        }
        JSONObject json = new JSONObject();
        json.accumulate("s", s);
        result = json.toString();
        return "success";
    }

    /**
     * 提交报损/报修
     * @return
     */
    public String saveBreak(){
        String companyId=request.getParameter("companyId");
        String breakType=request.getParameter("breakType");//报损/报修
        String inventoryId=request.getParameter("inventoryId");
        String staffId=request.getParameter("staffId");//员工id
        CashierBills cashierBills=new CashierBills();
        cashierBills.setCompanyID(companyId);
        cashierBills.setStaffID(staffId);
        cashierBills.setRemark(breakReason);//报损原因
        String s = "1";
        try {
            stockService.saveBreak(breakType,breakReason,inventoryId,cashierBills);
        } catch (Exception e) {
            s = "0";
            e.printStackTrace();
            logger.error("保存失败");
        }
        JSONObject json = new JSONObject();
        json.accumulate("s", s);
        result = json.toString();
        return "success";
    }

    /**
     * 查看报修/报损单
     * @return
     */
    public String getBreak(){
        String companyId=request.getParameter("companyId");
        String cashierBillsId=request.getParameter("cashierBillsId");
        Object obj=stockService.getBreak(companyId,cashierBillsId);
        if(obj!=null){
            Object[] os=(Object[])obj;
            JSONObject jsonObj = new JSONObject();
            jsonObj.accumulate("cashierBillsId",isNull(os[0]));             //进销存单id
            jsonObj.accumulate("goodsName",isNull(os[1]));                  //货品名称
            jsonObj.accumulate("journalNum",isNull(os[2]));                 //编号
            jsonObj.accumulate("remark",isNull(os[3]));                     //报损原因
            jsonObj.accumulate("comments",isNull(os[4]));                   //处理意见
            jsonObj.accumulate("warehouseName",isNull(os[5]));              //仓库名
            jsonObj.accumulate("status",isNull(os[6]));                     //审核状态
            jsonObj.accumulate("cashierDate",isNull(os[7].toString()));     //单据日期
            result=jsonObj;
        }else{
            result="noData";
        }
        return "success";
    }

    public String saveCar(){

        return "success";
    }

    public String updateBreak(){
        String companyId=request.getParameter("companyId");
        String cashierBillsId=request.getParameter("cashierBillsId");
        String s = "1";
        try {
            stockService.updateBreak(companyId,cashierBillsId,status,auditorId,comments);
        } catch (Exception e) {
            s = "0";
            e.printStackTrace();
            logger.error("保存失败");
        }
        JSONObject json = new JSONObject();
        json.accumulate("s", s);
        result = json.toString();
        return "success";
    }

    public String getInStockList(){
        return "";
    }

    public String getOutStockList(){
        return "";
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

    public List<CCode> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<CCode> codeList) {
        this.codeList = codeList;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getPiFaPrice() {
        return piFaPrice;
    }

    public void setPiFaPrice(String piFaPrice) {
        this.piFaPrice = piFaPrice;
    }

    public String getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(String purPrice) {
        this.purPrice = purPrice;
    }

    public String getInvenQuantity() {
        return invenQuantity;
    }

    public void setInvenQuantity(String invenQuantity) {
        this.invenQuantity = invenQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInvenUnderline() {
        return invenUnderline;
    }

    public void setInvenUnderline(String invenUnderline) {
        this.invenUnderline = invenUnderline;
    }

    public String getInvenOnline() {
        return invenOnline;
    }

    public void setInvenOnline(String invenOnline) {
        this.invenOnline = invenOnline;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getWareHouseId() {
        return wareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getFrameId() {
        return frameId;
    }

    public void setFrameId(String frameId) {
        this.frameId = frameId;
    }

    public String getFrameName() {
        return frameName;
    }

    public void setFrameName(String frameName) {
        this.frameName = frameName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /*public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }*/

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(String realQuantity) {
        this.realQuantity = realQuantity;
    }

    public String getBreakReason() {
        return breakReason;
    }

    public void setBreakReason(String breakReason) {
        this.breakReason = breakReason;
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getArrgoodsNum() {
        return arrgoodsNum;
    }

    public void setArrgoodsNum(String arrgoodsNum) {
        this.arrgoodsNum = arrgoodsNum;
    }

    public String getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(String goodsIds) {
        this.goodsIds = goodsIds;
    }

    public String getCcompanyId() {
        return ccompanyId;
    }

    public void setCcompanyId(String ccompanyId) {
        this.ccompanyId = ccompanyId;
    }
}
