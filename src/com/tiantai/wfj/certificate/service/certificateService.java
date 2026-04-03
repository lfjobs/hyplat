package com.tiantai.wfj.certificate.service;

import hy.ea.bo.human.StaffCertificate;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface certificateService {
    /**
     * 添加
     * @param certificate
     */
    Map<String, Object> saveOrUpdate(StaffCertificate certificate);

    /**
     * 删除
     * @param credentialskey
     */
    void deleteCertificate(String credentialskey);

    /**
     * 查看
     * @param credentialskey
     * @return
     */
    StaffCertificate getCertificateByKey(String credentialskey);

    StaffCertificate getCertificateById(String id);

    /**
     * 分页
     * @param PageNumber
     * @param pageSize
     * @param params
     * @return
     */
    PageForm getCertificateByPage(int PageNumber,int pageSize,Map<String, Object> params);

    List<BaseBean>  getCertificateList(Map<String, Object> params);
}
