package hy.ea.finance.action.brokerage;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.brokerage.*;
import hy.ea.finance.service.brokerage.ActivityService;
import hy.ea.finance.service.brokerage.WholesaleService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static hy.ea.util.elkc.DateUtilElkc.convertStringToDate;

/**
 * Created by Administrator on 2018-12-19.
 */
@Controller
@Scope("prototype")
public class ActivityAction {
    @Resource
    ServerService serverService;
    @Resource
    BaseBeanService baseBeanService;
    @Resource
    ActivityService activityService;
    @Resource
    WholesaleService wholesaleService;
    @Resource
    private ShowExcelService excelService;
    @Resource
    private UpLoadFileService fileService;

    private PageForm pageForm;
    private String result;//ajax响应结果
    private String search;//模糊查询参数
    private String flag;//查看数据标识 flag=01[数据设置为只读]
    private int pageNumber;//每页显示最大数[pageSize]
    private List<PActPrice> actPriceList;
    private PActPrice actPrice;
    private PActivity activity;
    private String startTime;//活动起始时间
    private String endTime;//活动结束时间
    private InputStream excelStream;
    private File file; // 文件
    private String fileFileName; // 文件名
    private String activityType; // 活动类型[00:普通活动  01:特价活动]
    private ProductPackaging productPackaging;
    //// 添加日志实例对象
    private static final Logger logger = LoggerFactory.getLogger(ActivityAction.class);

    //获取后台用户登录信息
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpSession session = request.getSession();
    CAccount cac = (CAccount) session.getAttribute("account");

    //////商品活动价模块//////

    /**
     * 相关活动查询
     * 2018-12-20
     *
     * @return
     */
    public String selectActivityList() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            String target= "activity_list";
            //每页显示最大数[默认显示30条]
            int pageSize;
            if("all".equals(activityType)){
                target="activity_print";
                pageSize = 10;
                map.put("pageNumber", pageNumber);
            }else{
                pageSize = (pageNumber == 0 ? 30 : pageNumber);
                //当前页
                int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
                map.put("pageNumber", pageNumber1);
            }

            map.put("pageSize", pageSize);
            map.put("search", search);
            map.put("activityType", activityType);
            map.put("companyId", cac.getCompanyID());
            request.setAttribute("search", search);
            //logger.info("活动类型:" + activityType + "[00:普通活动  01:特价活动]");
            pageForm = this.activityService.selectActivityList(map);
            return target;
        }
    }

    /**
     * 获取没有设置活动价佣金的产品
     * 2018-12-22
     *
     * @return
     */
    public String getProductByStatus() {
        Map<String, Object> map2 = new HashMap<String, Object>();
        //非空校验
        if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
            //当前页
            int pageNumber1 = pageForm != null && pageForm.getPageNumber() > 0 ? pageForm.getPageNumber() : 1;
            //每页显示最大数[默认显示30条]
            int pageSize = (pageNumber == 0 ? 30 : pageNumber);
            Map<String, Object> map1 = new HashMap<String, Object>();
            map1.put("pageNumber", pageNumber1);
            map1.put("pageSize", pageSize);
            map1.put("search", search);
            map1.put("companyId", cac.getCompanyID());
            pageForm = this.activityService.getProductByStatus(map1);
            map2.put("pageForm", pageForm);
            map2.put("search", search);
            map2.put("code", "200");
        } else {
            map2.put("code", "400");
        }

        JSONObject jsonObject = JSONObject.fromObject(map2);
        this.result = jsonObject.toString();
        return "success";
    }


    /**
     * 去添加活动页面
     * 2018-12-21
     *
     * @return
     */
    public String toActivityAdd() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取添加活动时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            //Date dateTime = DateUtilElkc.getCurrentDateTime();
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            request.setAttribute("agencyTypeList", agencyList);
            return "activity_add";
        }
    }

    /**
     * 添加活动
     * 2018-12-21
     *
     * @return
     */
    public String addActivity() throws ParseException {
        String path = request.getRealPath("/");
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null && cac.getCompanyID() != null && !"".equals(cac.getCompanyID())) {
            //活动添加
            String activityId = serverService.getServerID("activityId");
            activity.setActivityId(activityId);
            activity.setCompanyId(cac.getCompanyID());
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            activity.setAddTimes(dateTime);
            activity.setPrincipal(cac.getStaffName());
            Date startTime1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(startTime);//活动起始时间转换
            logger.info(startTime1 + "活动起始时间");
            activity.setStartTime(startTime1);
            Date endTime1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(endTime);//活动结束时间转换
            logger.info(endTime1 + "活动结束时间");
            activity.setEndTime(endTime1);
            activity.setState("00");//活动状态[00:初始 01:已开启 02:暂停 03:结束 04:已删除]
            String times = Utilities.getDateString(new Date(), "yyyy-MM-dd");
            String photoPath = null;
            if (file != null) {
                //上传活动图片
                photoPath = fileService.savePhoto(path, fileFileName, file, cac.getCompanyID(), "/brokerage/activity/"
                        + times);
                //保存图片路径
                activity.setActivityPicture(photoPath);
            }
            //活动添加
            this.activityService.addActivity(activity);
            map.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 去修改活动页面
     * 2018-12-22
     *
     * @return
     */
    public String toActivityUpdate() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取设置活动时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            //活动
            Object[] pActivity1 = this.activityService.pActivityById(activity.getActivityId());
            request.setAttribute("activity", pActivity1);
            request.setAttribute("flag", flag);
            return "activity_update";
        }
    }

    /**
     * 修改活动
     * 2018-12-22
     *
     * @return
     */
    public String updateActivity() throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        String path = request.getRealPath("/");
        //非空校验
        if (cac != null) {
            //获取活动修改回显数据
            PActivity pActivity1 = this.activityService.getPActivityById(activity.getActivityId());
            pActivity1.setActivityName(activity.getActivityName());
            pActivity1.setActivityDescribe(activity.getActivityDescribe());
            Date startTime1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(startTime);//活动起始时间转换
            pActivity1.setStartTime(startTime1);
            Date endTime1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(endTime);//活动结束时间转换

            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            Date currentTime_2 = formatter.parse(dateString);
            //当前时间与活动时间比较判断
            if (currentTime_2.getTime() < startTime1.getTime()) {
                pActivity1.setState("00");//活动状态改为:初始
            } else if (currentTime_2.getTime() > endTime1.getTime()) {
                pActivity1.setState("03");//活动状态改为:结束
            } else {
                pActivity1.setState("01");//活动状态改为:已开启
            }
            pActivity1.setEndTime(endTime1);
            pActivity1.setPrincipal(cac.getStaffName());
            //生成当前修改时间
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            pActivity1.setUpdateTimes(dateTime);
            String times = Utilities.getDateString(new Date(), "yyyy-MM-dd");
            String photoPath = null;
            if (file != null) {
                //上传活动图片
                photoPath = fileService.savePhoto(path, fileFileName, file, cac.getCompanyID(), "/brokerage/activity/"
                        + times);
                //保存图片路径
                pActivity1.setActivityPicture(photoPath);
            }
            //活动修改
            this.activityService.updateActivity(pActivity1);
            map.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 去添加活动产品及佣金页面
     * 2018-12-22
     *
     * @return
     */
    public String toActivityBrokerageAdd() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取添加活动时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            //Date dateTime = DateUtilElkc.getCurrentDateTime();
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            request.setAttribute("activityId", actPrice.getActivityId());//活动id
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            request.setAttribute("agencyTypeList", agencyList);
            return "actBrokerage_add";
        }
    }

    /**
     * 添加活动佣金
     * 2019-1-5
     *
     * @return
     */
    public String addActivityBrokerage() {
        Map<String, Object> map = this.activityService.addActivityBrokerage(actPriceList, actPrice);
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 去修改活动产品及佣金页面
     * 2019-1-4
     *
     * @return
     */
    public String toActivityBrokerageUpdate() {
        //非空校验
        if (cac == null) {
            //未登录
            return "failed";
        } else {
            //获取修改活动时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String myDate = e.format(new Date());
            request.setAttribute("cac", cac);
            request.setAttribute("myDate", myDate);
            request.setAttribute("activityId", actPrice.getActivityId());//活动id
            //代理类别
            List<BaseBean> agencyList = this.wholesaleService.agencyTypeList();
            request.setAttribute("agencyTypeList", agencyList);
            return "actBrokerage_update";
        }
    }

    /**
     * 修改活动佣金
     * 2019-1-14
     *
     * @return
     */
    public String updateActivityBrokerage() {
        Map<String, Object> map = this.activityService.updateActivityBrokerage(actPriceList, actPrice);
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 获取当前活动所有产品的活动价及相关佣金
     * 2019-1-12
     *
     * @return
     */
    public String getActivityPrice() {
        Map<String, Object> map = this.activityService.getActivityPrice(actPrice.getActivityId());//获取当前活动所有产品的活动价及相关佣金
        Map<String, Object> map1 = this.wholesaleService.getBrokeragePercent(productPackaging.getPpID());//获取产品佣金百分比
        map.putAll(map1);//集合合并
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 获取当前活动产品的相关代理佣金
     * 2019-1-12
     *
     * @return
     */
    public String getActivityBrokerage() {
        Map<String, Object> map = this.activityService.getActivityBrokerage(actPrice.getActPriceId());
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 活动开启[更改状态为01:已开启]
     * 2019-1-30
     *
     * @return
     */
    public String activityOpenById() throws ParseException {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null) {

            //获取活动修改回显数据
            PActivity pActivity1 = this.activityService.getPActivityById(activity.getActivityId());
            //日期格式转换
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(pActivity1.getEndTime());
            Date endTime1 = sdf.parse(format);
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            Date currentTime_2 = formatter.parse(dateString);
            //当前时间与活动时间比较判断
            if (currentTime_2.getTime() > endTime1.getTime()) {
                pActivity1.setState("03");//活动状态改为:结束
                map.put("code", "201");
            } else {
                pActivity1.setState("01");//活动状态改为:已开启
                map.put("code", "200");
            }
            pActivity1.setPrincipal(cac.getStaffName());
            //生成当前修改时间
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            pActivity1.setUpdateTimes(dateTime);
            //活动修改
            this.activityService.updateActivity(pActivity1);

            //this.activityService.activityOpenById(activity.getActivityId());

        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }


        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 活动暂停[更改状态为02:暂停]
     * 2019-1-11
     *
     * @return
     */
    public String activitySuspendById() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null) {
            this.activityService.activitySuspendById(activity.getActivityId());
            map.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }


        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 活动删除[更改状态为04:已删除]
     * 2019-1-11
     *
     * @return
     */
    public String activityDeleteById() {
        Map<String, Object> map = new HashMap<String, Object>();
        //非空校验
        if (cac != null) {
            this.activityService.activityDeleteById(activity.getActivityId());
            map.put("code", "200");
        } else {
            logger.error("登录异常,重新登录");
            map.put("code", "400");//登录异常
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
    }

    /**
     * 删除当前活动产品及佣金[更改状态为:01]
     * 2019-1-29
     *
     * @return
     */
    public String delActivityBrokerageById() {
        Map<String, Object> map = this.activityService.delActivityBrokerageById(actPrice);
        JSONObject jsonObject = JSONObject.fromObject(map);
        this.result = jsonObject.toString();
        return "success";
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
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

    public List<PActPrice> getActPriceList() {
        return actPriceList;
    }

    public void setActPriceList(List<PActPrice> actPriceList) {
        this.actPriceList = actPriceList;
    }

    public PActPrice getActPrice() {
        return actPrice;
    }

    public void setActPrice(PActPrice actPrice) {
        this.actPrice = actPrice;
    }

    public PActivity getActivity() {
        return activity;
    }

    public void setActivity(PActivity activity) {
        this.activity = activity;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public ProductPackaging getProductPackaging() {
        return productPackaging;
    }

    public void setProductPackaging(ProductPackaging productPackaging) {
        this.productPackaging = productPackaging;
    }
}
