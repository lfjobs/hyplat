package hy.ea.finance.service.brokerage;

import hy.ea.bo.finance.brokerage.PActPrice;
import hy.ea.bo.finance.brokerage.PActivity;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-12-20.
 */
public interface ActivityService {
    PageForm selectActivityList(Map<String, Object> map);

    PageForm getProductByStatus(Map<String, Object> map1);

    Object[] pActivityById(String activityId);

    PActivity getPActivityById(String activityId);

    Map<String, Object> addActivityBrokerage(List<PActPrice> pActPriceList, PActPrice pActPrice);

    void addActivity(PActivity pActivity);

    void updateActivity(PActivity pActivity1);

    void activityDeleteById(String activityId);

    void activitySuspendById(String activityId);

    Map<String,Object> getActivityPrice(String activityId);

    Map<String,Object> getActivityBrokerage(String actPriceId);

    Map<String,Object> updateActivityBrokerage(List<PActPrice> pActPriceList, PActPrice pActPrice);

    Map<String,Object> delActivityBrokerageById(PActPrice actPrice);

    void activityOpenById(String activityId);
}
