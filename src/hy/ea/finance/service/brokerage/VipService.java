package hy.ea.finance.service.brokerage;

import hy.ea.bo.finance.brokerage.PVip;
import hy.ea.bo.finance.brokerage.PVipBrokerage;
import hy.ea.bo.finance.brokerage.PWhoBrokerage;
import hy.ea.bo.finance.brokerage.PWholesale;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-11-22.
 */
public interface VipService {
    PageForm selectVipList(Map<String, Object> map);

    PageForm getProductByStatus(Map<String, Object> map1);

    void updateVipStatus(String ppid);

    Object[] pVipById(String ppid);

    List<BaseBean> brokerageListById(String vipId);

    PVip getPVipById(String vipId);

    PVipBrokerage getPVipBrokerageById(String sbtzId);

    Map<String, Object> vipDeleteById(PVip vip);
}
