package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class PrizePoolBean implements BaseBean
{
    private  String poolkey;
    private String poolId;
    private String productId;
    private Long prizeNum;
    private String prizeLvl;
    private String companyId;
    private String activityId;
    private String status;
    private Timestamp startingTime;
    private Timestamp endTime;
    private String prizeType;// 0会员产品；1商家优惠券；2实物产品；3会员积分
    private String ppName;//产品名称


    private Double probaRate; //非数据库字段
    private Long lotteryIndex;//位置


    public PrizePoolBean (String poolId, String productId, Long prizeNum, String prizeLvl, String companyId,
                         String activityId, String status, Timestamp startingTime, Timestamp endTime,
                         String prizeType, String ppName, Double probaRate, Long lotteryIndex)
    {
        this.poolId = poolId;
        this.productId = productId;
        this.prizeNum = prizeNum;
        this.prizeLvl = prizeLvl;
        this.companyId = companyId;
        this.activityId = activityId;
        this.status = status;
        this.startingTime = startingTime;
        this.endTime = endTime;
        this.prizeType = prizeType;
        this.ppName = ppName;
        this.probaRate = probaRate;
        this.lotteryIndex = lotteryIndex;
    }

    public PrizePoolBean (Long lotteryIndex, Long prizeNum, Double probaRate)
    {
        super();
        this.lotteryIndex = lotteryIndex;
        this.prizeNum = prizeNum;
        this.probaRate = probaRate;
    }

    public PrizePoolBean(String prizeLvl,String ppName) {
        this.prizeLvl = prizeLvl;
        this.ppName = ppName;
    }

    public String getPoolkey() {
        return poolkey;
    }

    public void setPoolkey(String poolkey) {
        this.poolkey = poolkey;
    }

    public PrizePoolBean ()
    {
        super();
    }



    public String getPoolId() {
        return poolId;
    }

    public void setPoolId(String poolId) {
        this.poolId = poolId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getPrizeNum() {
        return prizeNum;
    }

    public void setPrizeNum(Long prizeNum) {
        this.prizeNum = prizeNum;
    }

    public String getPrizeLvl() {
        return prizeLvl;
    }

    public void setPrizeLvl(String prizeLvl) {
        this.prizeLvl = prizeLvl;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Timestamp startingTime) {
        this.startingTime = startingTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getPrizeType() {
        return prizeType;
    }

    public void setPrizeType(String prizeType) {
        this.prizeType = prizeType;
    }

    public String getPpName ()
    {
        return ppName;
    }

    public void setPpName (String ppName)
    {
        this.ppName = ppName;
    }

    public Double getProbaRate ()
    {
        return probaRate;
    }

    public void setProbaRate (Double probaRate)
    {
        this.probaRate = probaRate;
    }

    public Long getLotteryIndex ()
    {
        return lotteryIndex;
    }

    public void setLotteryIndex (Long lotteryIndex)
    {
        this.lotteryIndex = lotteryIndex;
    }
}
