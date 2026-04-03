package hy.ea.marketing.service;


import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.lottery.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import java.util.List;
import java.util.Map;

/**
 * 活动的发布，抽奖活动
 *
 * @author [mz]
 * @version [1.0, 2017-03-17]
 */
public interface LotteryActivitySerivce
{
    /**
     * 删除抽奖活动
     * @param activityId 活动id
     * @return
     */
    public boolean deletePrizeActivity( String activityId );

    /**
     * 获取抽奖活动列表
     * @param pageNo 当前页数
     * @param pageSize 页数大小
     * @return 活动数据
     */
    public List<BaseBean> getActivityList (int pageNo , int pageSize);

    /**
     * 分页
     * @param pageNumber
     * @param pageSize
     * @param flag 活动类型0:抽奖1：签到
     * @param companyId 公司id
     * @return
     */
    PageForm getActivityPageForm (Integer pageNumber, Integer pageSize, String flag,String companyId);

    /**
     * 保存抽奖活动设定
     * @param model 页面数据实体
     * @return 页面数据实体
     * @throws Exception 实体转换异常
     */
    public PrizeActivityModel savePrizeActivity(PrizeActivityModel model) throws Exception;

    /**
     * 查询活动
     * @param model
     * @return
     */
    public PrizeActivityModel getPrizeActivity(PrizeActivityModel model)throws Exception;

    /**
     * 保存奖品池
     * @param ppb  奖品池表
     * @param model 活动表
     * @return
     */
    public PrizePoolBean savePrizePool(PrizePoolBean ppb, PrizeActivityModel model);

    /**
     * 查询产品
     * @param companyId 公司id
     * @return
     */
    public PageForm getProPageForm(Integer pageNumber,Integer pageSize,String companyId,String search);

    /**
     * 查询奖品等级
     * @return
     */
    public List<BaseBean> getPrizeProbaBean();

    /**
     * 查询优惠券
     * @param companyId
     * @return
     */
    public PageForm getPrizeCouponBean(String companyId);

    /**
     * 新增优惠券
     * @param model
     * @return
     */
    public PrizeCouponBean savePrizeCouponBean(PrizeCouponBean model);

    /**
     * 删除优惠券
     * @param couponId
     * @return
     */
    public Boolean deleteCoupon(String couponId);

    /**
     * 删除活动说明图片
     * @param model
     * @return
     */
    public Boolean deletePrizeDescImage (PrizeActivityModel model);

    /**
     * 抽奖
     * @param model
     * @return
     */
    public String lottery(PrizeActivityModel model);

    /**
     * 查询奖品池
     * @param activityId
     * @return
     */
    public List<BaseBean> getPrizePoolBean(String activityId);

    /**
     * 保存抽奖记录
     * @param flag
     * @param model
     * @flag 判断积分抽奖或者免费抽奖
     * @return
     */
    public LotteryRecordBean saveLotteryRecord (TEshopCusCom cusCom,String flag,PrizeActivityModel model);

    /**
     * 保存中奖记录
     * @param index
     * @param model
     * @return
     */
    public PrizePoolBean saveWinRecord(String index,PrizeActivityModel model,LotteryRecordBean recordBean);

    /**
     * 查询中奖记录
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageForm pageFormWinRecords(Integer pageNumber,Integer pageSize,String lotteryMan,String activityId);

    /**
     * 滚动中奖记录
     * @return
     */
    public List<BaseBean> rollingRecords (String activityId);

    /**
     * 免费抽奖次数
     * @param activityId
     * @return
     */
    public String qualification(String activityId);

    /**
     * 免费抽奖次数
     * @param cusCom
     * @return
     */
    public Integer freeLotteryTimes (TEshopCusCom cusCom,String activityId);

    /**
     * 是否有抽奖活动
     * @param companyId
     * @return
     */
    public PrizeActivityModel isDraw (String companyId);

    /**
     * 保存会议活动
     */
    public boolean saveMeetingActivity(PrizeActivityModel model,String tickets,String content );

    /*
    *查询会议活动
     */
    public Boolean selMeetingActivity(String companyId);
    /**
     *  查询本公司抽奖活动有没有过期
     */

    public Boolean selprizeActivity(String companyId);
    /**
     *  查询本公司签到活动有没有过期
     */

    public Boolean signPrizeActivity(String companyId);

    /**
     * 分页查询会议活动
     */
    public PageForm selMeetingPage(int pageNumber);

    /**
     * 查询会议活动的详情
     */
    public Object[] selMeetingDetail(String activityId);

    /**
     * 查询票券活动详情
     */
    public List<Object> TicketDetail(String activityId);
}
