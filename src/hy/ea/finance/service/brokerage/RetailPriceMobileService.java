package hy.ea.finance.service.brokerage;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.ProSetup;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface RetailPriceMobileService {
    PageForm selectRetailPriceList(Map<String, Object> map);

    Map<String, String> savePrice(String state, CAccount cac, Map mapPro, Map mapdl);

    Map<String, Object> getRetailPriceList(String psid);

    void updateState(String priceid);
}
