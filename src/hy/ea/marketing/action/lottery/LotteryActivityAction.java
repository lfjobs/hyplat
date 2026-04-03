package hy.ea.marketing.action.lottery;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.lottery.LotteryRecordBean;
import hy.ea.bo.lottery.PrizeActivityModel;
import hy.ea.bo.lottery.PrizePoolBean;
import hy.ea.marketing.service.LotteryActivitySerivce;
import hy.ea.util.DateUtil;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


/**
 * 活动的发布，抽奖活动
 *
 * @author [mz]
 * @version [1.0, 2017-03-17]
 */
@Controller
@Scope("prototype")
public class LotteryActivityAction
{
    @Resource
    private ServerService serverService;
    @Resource
    private LotteryActivitySerivce lotteryActivitySerivce;

    @Resource
    private BaseBeanService baseBeanService;

    private PageForm pageForm;
    private String search;
    private int pageNumber;
    private String result;
    private String flag;//活动类型0:抽奖1：签到
    private PrizeActivityModel model;
    private PrizePoolBean poolBean;
    private String sign;//标记 edit 编辑

    private String ccompanyId;
    private CAccount caccount;
    private String activityId;
    private String type;
    private String selectType;

    private Logger logger = Logger.getLogger(LotteryActivityAction.class);
    private static final String KEY_CACCOUNT = "account";
    private static final String TYPE_ALL_CUSTOMER = "allCustomer";
    /**
     * 活动首页
     * @return
     */
    public String prizeActivityHomepage ()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap wrap = SessionWrap.getInstance();
        CAccount account = (CAccount) wrap.getObject(session,SessionWrap.KEY_CACCOUNT);
        if(Objects.isNull(account))
        {
            account = (CAccount) wrap.getObject(session,KEY_CACCOUNT);
        }
        request.setAttribute("companyId",account != null ? account.getCompanyID() : "");
        return "prizeActivityHomepage";
    }

    public String prizeActivityHomepageBack ()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap wrap = SessionWrap.getInstance();
        CAccount account = (CAccount) wrap.getObject(session,SessionWrap.KEY_CACCOUNT);
        if(Objects.isNull(account))
        {
            account = (CAccount) wrap.getObject(session,KEY_CACCOUNT);
        }
        request.setAttribute("companyId",account != null ? account.getCompanyID() : "");
        return "prizeActivityHomepageBack";
    }

    /**
     * 活动列表
     * @return
     */
    public String prizeActivityList ()
    {
       if("2".equals(flag)){
           HttpServletRequest request = ServletActionContext.getRequest();
           HttpSession session = request.getSession();
           SessionWrap sw = SessionWrap.getInstance();
           caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
           Boolean isflag = lotteryActivitySerivce.selMeetingActivity(model.getCompanyId());
           //没有数据直接跳到会议活动添加页面
           if(!isflag){
               return "MeetingActivityAdd";
           }
       }
       return "prizeActivityList";
    }
    /**
     * 查找抽奖活动有没有过期的，有过期的就添加，没有就不能添加
     * @return
     */
    public String prizeActivity ()
    {
        if("0".equals(flag)){
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
            Boolean isflag = lotteryActivitySerivce.selprizeActivity(model.getCompanyId());
            //没有数据直接跳到抽奖活动添加页面
            JSONObject json = new JSONObject();
            json.accumulate("isflag", isflag);
            result = json.toString();
        }
        return "success";
    }
    /**
     * 查找抽奖活动有没有过期的，有过期的就添加，没有就不能添加
     * @return
     */
    public String signPrizeActivity ()
    {
        if("1".equals(flag)){
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpSession session = request.getSession();
            SessionWrap sw = SessionWrap.getInstance();
            caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
            Boolean isflag = lotteryActivitySerivce.signPrizeActivity(model.getCompanyId());
            //没有数据直接跳到抽奖活动添加页面
            JSONObject json = new JSONObject();
            json.accumulate("isflag", isflag);
            result = json.toString();
        }
        return "success";
    }

    /**
     * 会议活动添加返回页面判断
     */
    public String meetingAddReturn(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        caccount = (CAccount)sw.getObject(session, SessionWrap.KEY_CACCOUNT);
        Boolean isflag = lotteryActivitySerivce.selMeetingActivity(model.getCompanyId());
        if(!isflag){ //没有数据跳到活动首页
            request.setAttribute("companyId",caccount != null ? caccount.getCompanyID() : "");
            return "prizeActivityHomepage";
        }else{  //有数据跳到会议活动列表页面
            return "prizeActivityList";
        }
    }

    /**
     * 抽奖活动添加
     * @return
     */
    public String prizeActivityAdd ()
    {
        //奖品等级
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("poolList",lotteryActivitySerivce.getPrizeProbaBean());

        //活动
        if (model != null && model.getActivityId() != null && model.getActivityId().length() > 0)
        {
            try
            {
                model = lotteryActivitySerivce.getPrizeActivity(model);
            }
            catch (Exception e)
            {
                logger.error("查询活动详情失败");
            }
        }
        if("2".equals(flag)){
            return "MeetingActivityAdd";
        }else{
            return "prizeActivityAdd";
        }
    }

    public String meetingActivity(){
        return "MeetingActivityAdd";
    }

    /**
     * ajax分页活动列表
     * @return
     */
    public String ajaxPrizeActivityList ()
    {
        if (flag != null && !flag.isEmpty())
        {
            pageForm = lotteryActivitySerivce.getActivityPageForm(
                    pageForm != null ? pageForm.getPageNumber() : 1,
                    pageForm != null ? pageForm.getPageSize() : 10,
                    flag,
                    model != null ? model.getCompanyId() : ""
            );
        }

        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        result = json.toString();
        return "success";
    }


    public String allActivityList(){
        List<Object> objList = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select dc.ccompanyid, c.companyname,p.activity_img,p.activity_name,p.company_id   from ");
        sql.append(" dt_ccom_com cc,dtcontactcompany dc ,dt_prize_activity p,dtcompany c where ");
        sql.append(" to_char(sysdate,'yyyyMMdd HH24 MI') between to_char(p.starting_time,'yyyyMMdd HH24 MI') ");
        sql.append(" and to_char(p.end_time,'yyyyMMdd HH24 MI') and p.company_id = c.companyid ");
        sql.append(" and cc.compnay_id = c.companyid and cc.ccompany_id = dc.ccompanyid and ");
        sql.append(" trim(p.activity_type) = ? and trim(p.status) = ? and trim(p.activity_range) = ? ");
        objList.add(flag);
        objList.add("0");
        objList.add("0");
        if (ccompanyId!=null) {
             sql.append("and cc.ccompany_id = ?");
             objList.add(ccompanyId);
        }
        sql.append("  order by p.end_time desc");
        pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 3, sql.toString(),
                "select count(*) from (" + sql.toString() + ")", objList.toArray());
        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        result = json.toString();
        return "success";
    }

    public String vipActivityList(){
        String sql = "select dtp.ppid,dtp.goodsid,dtp.companyid,dtp.goodsname,dtp.image from dt_productpackaging dtp where dtp.type = ? or dtp.type =?";
        pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1), 3, sql,
                "select count(*) from (" + sql + ")", new Object[] {"公司会员","个人会员"});
        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        result = json.toString();
        return "success";
    }

    /**
     * 删除活动
     * @return
     */
    public String delActivity ()
    {
        if (model != null && model.getActivityId().length() > 0)
        {
           boolean b = lotteryActivitySerivce.deletePrizeActivity(model.getActivityId());
           result = String.valueOf(b);
        }
        return "success";
    }

    /**
     * 保存抽奖活动
     * @return
     */
    public String saveActivity()
    {
        if (model != null )
        {
            JSONObject jsonObject = new JSONObject();
            try{
                PrizeActivityModel pm = lotteryActivitySerivce.savePrizeActivity(model);
                if (pm != null)
                {
                    jsonObject.accumulate("activityId",pm.getActivityId());
                    jsonObject.accumulate("flag","1");
                }
                else
                {
                    //该公司没有积分
                    jsonObject.accumulate("flag","2");
                }
            }
            catch (Exception e)
            {
                System.out.print(e);
                jsonObject.accumulate("flag","0");
            }
            result = jsonObject.toString();
        }
        return "success";
    }

    /**
     * 保存奖品池
     * @return
     */
    public String savePrizePool()
    {
        if (poolBean != null && model != null)
        {
            JSONObject jsonObject = new JSONObject();
            PrizePoolBean ppb = lotteryActivitySerivce.savePrizePool(poolBean,model);
            if (ppb != null)
            {
                jsonObject.accumulate("flag","1");
                jsonObject.accumulate("ppb",ppb);
            }
            else
            {
                jsonObject.accumulate("flag","0");
            }
            result = jsonObject.toString();
        }
        return "success";
    }
    /**
     * 更新奖品池
     * @return
     */
    public String updatePrizePool(){
        PrizePoolBean gm = (PrizePoolBean) baseBeanService
                .getBeanByHqlAndParams("from PrizePoolBean where poolId=?",
                        new Object[]{poolBean.getPoolId()});
        gm.setPoolId(poolBean.getPoolId());
        gm.setPrizeType(poolBean.getPrizeType());
        gm.setPrizeNum(poolBean.getPrizeNum());
        gm.setPrizeLvl(poolBean.getPrizeLvl());
        gm.setPpName(poolBean.getPpName());
        JSONObject json = new JSONObject();
        baseBeanService.update(gm);
        json.accumulate("gm",gm);
        result = json.toString();
        return "success";
    }
    /**
     * 实物产品列表
     * @return
     */
    public String proList ()
    {
        return "proList";
    }

    /**
     * 优惠券列表
     * @return
     */
    public String couponsList ()
    {
        return "couponsList";
    }

    /**
     * 优惠券增加
     * @return
     */
    public String couponAdd ()
    {
        return "couponsAdd";
    }

    /**
     * ajax 实物产品列表分页
     * @return
     */
    public String ajaxProPageForm ()
    {
        if (model != null && model.getCompanyId().length() > 0)
        {
            pageForm = lotteryActivitySerivce.getProPageForm(
                    pageForm != null ? pageForm.getPageNumber() : 1,
                    pageForm != null ? pageForm.getPageSize() : 10,
                    model.getCompanyId(),
                    search
            );
        }
        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        result = json.toString();
        return "success";
    }

    /**
     * 删除活动说明图片
     * @return
     */
    public String delPrizeDecImage ()
    {
        if (model != null && model.getPoolId() != null
                && model.getPoolId().length() > 0)
        {
            Boolean b = lotteryActivitySerivce.deletePrizeDescImage(model);
            result = String.valueOf(b);
        }
        return "success";
    }

    /**
     * 公司是否有抽奖活动
     * @return
     */
    public String ajaxIsDraw ()
    {
        JSONObject jsonObject = new JSONObject();
        model = lotteryActivitySerivce.isDraw(model.getCompanyId());
        jsonObject.accumulate("model",model);
        result = jsonObject.toString();
        return "success";
    }
    /**
     * 抽奖页面
     * @return
     */
    public String goLottery()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cusCom = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
        /*if (cusCom == null)
        {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            session.setAttribute("url"
                    ,basePath + "ea/lottery/ea_goLottery.jspa?model.activityId=" + model.getActivityId()
                    + "&model.companyId=" + model.getCompanyId()
            );
            return "login";
        }*/
        if (model != null
                && model.getActivityId() != null
                && model.getActivityId().length() > 0)
        {
            //奖品池
            List<BaseBean> list = lotteryActivitySerivce.getPrizePoolBean(model.getActivityId());
            //查询活动时间,抽奖所需积分
            try {
                model = lotteryActivitySerivce.getPrizeActivity(model);
                request.setAttribute("activityName", model.getActivityName());
                request.setAttribute("startDate", DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss",model.getStartingTime()));
                request.setAttribute("endDate", DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss",model.getEndTime()));
                request.setAttribute("bonusPoints", Objects.nonNull(model.getBonusPoints()) ? model.getBonusPoints() : 1000);
            } catch (Exception e) {
                logger.error("获取活动信息失败");
            }
            request.setAttribute("list",list);

            //获取奖品级别
            List<String> prizeLevelList = new ArrayList<>();
            for(BaseBean baseBean : list){
                prizeLevelList.add(((PrizePoolBean) baseBean).getPrizeLvl());
            }
            request.setAttribute("prizeLevelList",prizeLevelList);
        }

        List<BaseBean> records = lotteryActivitySerivce.rollingRecords(model.getActivityId());
        request.setAttribute("records",records);
        return "lottery";
    }

    public String goPrizeList()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cusCom = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
        if (model != null
                && model.getActivityId() != null
                && model.getActivityId().length() > 0)
        {
            //奖品池
            List<BaseBean> list = lotteryActivitySerivce.getPrizePoolBean(model.getActivityId());

            //活动时间
            try {
                model = lotteryActivitySerivce.getPrizeActivity(model);
                request.setAttribute("activityName", model.getActivityName());
                request.setAttribute("startDate", DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss",model.getStartingTime()));
                request.setAttribute("endDate", DateUtil.toPaseDate("yyyy-MM-dd HH:mm:ss",model.getEndTime()));
                request.setAttribute("bonusPoints", Objects.nonNull(model.getBonusPoints()) ? model.getBonusPoints() : 1000);
            } catch (Exception e) {
                logger.error("获取活动信息失败");
            }
            request.setAttribute("list",list);
        }
        return "prizeList";
    }

    public String awardRecordAllCustomer(){
        return "awardRecordAllCustomer";
    }

    public String goAwardRecordAllCustomerList()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cusCom = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);

        List<JSONObject> list = new ArrayList<JSONObject>();
        JSONObject jsonObject = new JSONObject();
        List<BaseBean> records = lotteryActivitySerivce.rollingRecords(model.getActivityId());
        for (int i = 0; i < records.size(); i++) {
            Object[] obj = (Object[]) (Object) records.get(i);
            JSONObject jsonObj = new JSONObject();
            jsonObj.accumulate("account", isNull(obj[0]));
            jsonObj.accumulate("productName", isNull(obj[1]));
            jsonObj.accumulate("winDate", isNull(obj[2]));
            list.add(jsonObj);
        }
        jsonObject.accumulate("awardRecord", list);
        result = jsonObject.toString();
        return "success";
    }

    private Object isNull(Object tep){
        if(tep==null||"null".equals(tep)){
            return "";
        }else{
            return tep;
        }
    }

    public String login() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        //TEshopCusCom cusCom = (TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            session.setAttribute("url"
                    , basePath + "ea/lottery/ea_goLottery.jspa?model.activityId=" + model.getActivityId()
                            + "&model.companyId=" + model.getCompanyId()
            );

        return "login";
    }

    /**
     * 抽奖
     * @return
     */
    public String lottery ()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cusCom = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);

        JSONObject jsonObject = new JSONObject();
        if (cusCom == null)
        {
            jsonObject.accumulate("t","login");
        }
        else {
            //抽奖资格验证
            String t = lotteryActivitySerivce.qualification(model.getActivityId());

            if (!"no".equals(t) && !"less".equals(t)) {
                String s = lotteryActivitySerivce.lottery(model);
               if(!"end".equals(s)){
                   jsonObject.accumulate("index", s);
               }else{
                   t=s;
               }
            }
            jsonObject.accumulate("t", t);
            jsonObject.accumulate("staffId", cusCom.getStaffid());
            //免费抽奖次数
            Integer count = lotteryActivitySerivce.freeLotteryTimes(cusCom, model.getActivityId());
            jsonObject.accumulate("count", count);
        }
        result = jsonObject.toString();
        System.out.print(result);
        return "success";
    }

    /**
     * 保存抽奖记录及中奖记录
     * @return
     */
    public String saveLotteryRecords ()
    {
        JSONObject jsonObject = new JSONObject();

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cusCom = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);

        logger.error("登录账号:" + cusCom);
        logger.error("model.staffId:" + model.getStaffId() + "model.activityId:" + model.getActivityId());
        logger.error("flag:" + flag);
        LotteryRecordBean  recordBean = lotteryActivitySerivce.saveLotteryRecord(cusCom,flag,model);
        if (recordBean != null
                && recordBean.getLotteryResult() != null
                && "中奖".equals(recordBean.getLotteryResult()))
        {
            jsonObject.accumulate("recordId",recordBean.getRecordId());
            PrizePoolBean poolBean = lotteryActivitySerivce.saveWinRecord(model.getIndex(),model,recordBean);
            jsonObject.accumulate("ppId",poolBean.getProductId());
        }

        //免费抽奖次数
        Integer count = lotteryActivitySerivce.freeLotteryTimes(cusCom,model.getActivityId());
        jsonObject.accumulate("count",count);
        result = jsonObject.toString();
        return "success";
    }
    /**
     * 查询中奖记录
     * @return
     */
    public String lotteryRecords ()
    {
        return "lotteryRecords";
    }

    /**
     * 分页中奖记录
     * @return
     */
    public String ajaxLotteryRecord ()
    {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        SessionWrap sw = SessionWrap.getInstance();
        TEshopCusCom cusCom = (TEshopCusCom) sw.getObject(session,SessionWrap.KEY_SHOPCUSCOM);
        String lotteryMan=cusCom.getStaffid();
        //显示当前活动所有用户的中奖记录
        if(TYPE_ALL_CUSTOMER.equals(type)){
            lotteryMan = null;
        }
        pageForm = lotteryActivitySerivce.pageFormWinRecords(
                pageForm != null ? pageForm.getPageNumber() : 1,
                pageForm != null ? pageForm.getPageSize() : 10,lotteryMan,model.getActivityId()
        );
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("pageForm",pageForm);
        result = jsonObject.toString();
        return "success";
    }

    //保存会议活动（在prizeActivity表中添加一条记录）
    public String saveMeetingActivity(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String tickets = request.getParameter("tickets");
        String content = request.getParameter("content");
        boolean flag = false;
        if(model != null){
            flag = lotteryActivitySerivce.saveMeetingActivity(model,tickets,content);
            if(flag) {
                request.setAttribute("flag", "2");
                return "prizeActivityList";
            }
        }
        return "MeetingActivityAdd";
    }

    /**
     * 分页查询会议活动
     */
    public String selMeetingPage(){
        pageForm = lotteryActivitySerivce.selMeetingPage(pageNumber);
        JSONObject json = new JSONObject();
        json.accumulate("pageForm",pageForm);
        result = json.toString();
        return "success";
    }

    /**
     * 查询会议活动详情
     */
    public String selMeetingDetail(){
        HttpServletRequest request = ServletActionContext.getRequest();
        Object[] meetingDetail= lotteryActivitySerivce.selMeetingDetail(activityId);
        request.setAttribute("md",meetingDetail);
        return "MeetingActivityDetail";
    }

    /**
     * 查询票券列表
     */
    public String TicketList(){
        HttpServletRequest request = ServletActionContext.getRequest();
        List<Object> ticketList = lotteryActivitySerivce.TicketDetail(activityId);
        request.setAttribute("ticketList",ticketList);
        return "MeetingTicketList";
    }

    public String loginPc()
    {
        return "loginPc";
    }



    public PageForm getPageForm ()
    {
        return pageForm;
    }

    public void setPageForm (PageForm pageForm)
    {
        this.pageForm = pageForm;
    }

    public String getSearch ()
    {
        return search;
    }

    public void setSearch (String search)
    {
        this.search = search;
    }

    public int getPageNumber ()
    {
        return pageNumber;
    }

    public void setPageNumber (int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
    {
        this.result = result;
    }

    public String getFlag ()
    {
        return flag;
    }

    public void setFlag (String flag)
    {
        this.flag = flag;
    }

    public PrizeActivityModel getModel ()
    {
        return model;
    }

    public void setModel (PrizeActivityModel model)
    {
        this.model = model;
    }

    public PrizePoolBean getPoolBean ()
    {
        return poolBean;
    }

    public void setPoolBean (PrizePoolBean poolBean)
    {
        this.poolBean = poolBean;
    }

    public String getSign ()
    {
        return sign;
    }

    public void setSign (String sign)
    {
        this.sign = sign;
    }

    public String getCcompanyId ()
    {
        return ccompanyId;
    }

    public void setCcompanyId (String ccompanyId)
    {
        this.ccompanyId = ccompanyId;
    }

    public CAccount getCaccount() {
        return caccount;
    }

    public void setCaccount(CAccount caccount) {
        this.caccount = caccount;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    public String getSelectType() {
        return selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
