package hy.ea.finance.service.brokerage;

import hy.ea.bo.CAccount;
import hy.plat.bo.PageForm;

import java.util.Map;

public interface VipPriceMobileService {
    PageForm selectVipPriceList(Map<String, Object> map);

    Map<String, String> savePrice(String state, CAccount cac, Map mapPro, Map mapdl);

    Map<String, Object> getRetailPriceList(String psid);

    void updateState(String priceid);
}
