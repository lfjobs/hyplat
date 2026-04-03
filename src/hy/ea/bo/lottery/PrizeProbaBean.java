package hy.ea.bo.lottery;

import hy.plat.bo.BaseBean;

/**
 * Created by Administrator on 2017/3/15 0015.
 */
public class PrizeProbaBean implements BaseBean
{
    private String probaId;
    private String prizeLevel;
    private Double probaRate;
    private String prizeScheme;

    private Long lotteryIndex;//位置


    public String getProbaId ()
    {
        return probaId;
    }

    public void setProbaId (String probaId)
    {
        this.probaId = probaId;
    }

    public String getPrizeLevel ()
    {
        return prizeLevel;
    }

    public void setPrizeLevel (String prizeLevel)
    {
        this.prizeLevel = prizeLevel;
    }

    public Double getProbaRate ()
    {
        return probaRate;
    }

    public void setProbaRate (Double probaRate)
    {
        this.probaRate = probaRate;
    }

    public String getPrizeScheme ()
    {
        return prizeScheme;
    }

    public void setPrizeScheme(String prizeScheme) {
        this.prizeScheme = prizeScheme;
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
