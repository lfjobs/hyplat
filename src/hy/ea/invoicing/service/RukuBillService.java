package hy.ea.invoicing.service;

import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface RukuBillService {
    Map<String, Object> getInitializeList(String companyid, String parameter, PageForm pageForm) throws Exception;
    Map<String, Object> getBillList(String userID, String parameter, PageForm pageForm) throws Exception;
    List<BaseBean> SaveShouhuo(String formjum, String serializeJson) throws Exception ;
    List<BaseBean> SaveYanhuo(String formjum, String serializeJson) throws Exception ;
    void SaveRuku(String formjum, String serializeJson)throws Exception;
}