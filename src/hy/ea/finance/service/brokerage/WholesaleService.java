package hy.ea.finance.service.brokerage;

import hy.ea.bo.finance.brokerage.PWhoBrokerage;
import hy.ea.bo.finance.brokerage.PWholesale;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-11-22.
 */
public interface WholesaleService {
    PageForm selectWholesaleList(Map<String, Object> map);

    List<BaseBean> agencyTypeList();

    PageForm getProductByStatus(Map<String, Object> map1);

    Map<String,Object> getBrokeragePercent(String ppID);

    void updatewholesaleStatus(String ppid);

    Object[] PWholesaleById(String ppid);

    List<BaseBean> brokerageListById(String wholesaleId);

    PWholesale getPWholesaleById(String wholesaleId);

    PWhoBrokerage getPWhoBrokerageById(String sbtzId);

    Map<String,Object> wholesaleDeleteById(PWholesale wholesale);
}
