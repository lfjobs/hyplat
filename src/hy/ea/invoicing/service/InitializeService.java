package hy.ea.invoicing.service;

import hy.plat.bo.PageForm;

import java.util.Map;

/**
 * Created by Administrator on 2020-12-03.
 */
public interface InitializeService {
    Map<String, Object> getInitializeList(String companyid,String parameter,PageForm pageForm) throws Exception;
    Map<String, Object> getConditionQuery(String companyid, String sdate, String edate, String pemer, String title, String type,PageForm pageForm,int pageNumber) throws Exception;
    void SaveInitialize(String formjum, String serializeJson) throws Exception;
}
