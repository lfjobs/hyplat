package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

/**
 * Created by lyc on 2019-01-30.
 */
public class CarRelationMarket implements BaseBean {
    public String rmKey;
    public String rmId;
    public String carId;//车辆信息id
    public String marketId;//关联市场id

    public String getRmKey() {
        return rmKey;
    }

    public void setRmKey(String rmKey) {
        this.rmKey = rmKey;
    }

    public String getRmId() {
        return rmId;
    }

    public void setRmId(String rmId) {
        this.rmId = rmId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }
}
