package hy.ea.finance.service.brokerage;

import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018-11-22.
 */
public interface RetailService {
    PageForm selectRetailList(Map<String, Object> map);

    List<BaseBean> agencyTypeList();

    PageForm getProductByStatus(Map<String, Object> map1);

    List<BaseBean> brokerageListById(String retailId);

    void updateretailStatus(String ppid);

    Object[] PRetailById(String ppID);

    ProSetup getPRetailById(String suid);

    ProSetupSub getPRetBrokerageById(String sbtzId);

    Map<String,Object> retailDeleteById(ProSetup proSetup);

    String getPRetailByPpid(ProSetup proSetup, Map<String, String> mapPro);

    InputStream RetailShowExcel(String search,String showweixin,String comid);
}
