package com.tiantai.wfj.service;

import com.tiantai.wfj.bo.SafetyInspect;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface SafetyInspectService {

    /**
     * 保存
     *
     * @param si
     * @return
     */
    boolean saveInspect(SafetyInspect si, String ImagesPath, String VideoPath, String oask);

    /**
     * 巡查信息列表
     *
     * @param Comid      公司id
     * @param oaskName   安全类别
     * @param staffname  巡查人
     * @param pageNumber 当前页数
     * @param pageSize   每页条数
     * @return
     */
    PageForm getInspectList(String Comid,String ln, String staffname, String oaskName,String sDate,String eDate,String siType, int pageNumber, int pageSize);

    Object getInspect(String id);

    List getAnnex(String id);

    List<BaseBean> getInspectKind(String siid);

}
