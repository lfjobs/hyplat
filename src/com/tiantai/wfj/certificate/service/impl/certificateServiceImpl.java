package com.tiantai.wfj.certificate.service.impl;

import com.tiantai.wfj.certificate.service.certificateService;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.StaffCertificate;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class certificateServiceImpl implements certificateService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService bbservice;
    @Resource
    private UpLoadFileService fileService;

    @Override
    @Transactional
    public Map<String, Object> saveOrUpdate(StaffCertificate certificate) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            certificate.setAuditorState("03");
            if (certificate == null) {
                map.put("success", false);
                map.put("message", "参数错误！");
            }

            if (certificate.getCompanyID() == null||certificate.getCompanyID().isEmpty()) {
                certificate.setCompanyID("company20161010W9FXK9NJ450000011427");
            }
            if (certificate.getPhotos() != null) {
                String path = ServletActionContext.getRequest()
                        .getSession().getServletContext().getRealPath("/");
                String photoPath = fileService
                        .savePhoto(path, certificate.getPhotosFileName()==null?certificate.getCredentialsType():certificate.getPhotosFileName(), certificate
                                        .getPhotos(), certificate.getCompanyID()==null?"1":certificate.getCompanyID(),
                                "/human/credentials/"
                                        + Utilities.getDateString(
                                        new Date(), "yyyy-MM-dd"));
                certificate.setPhoto(photoPath);
            }
            List<BaseBean> b = new ArrayList<>();
            b.add(certificate);
            beandao.saveBeansListAndexecuteHqlsByParams(b,null,null);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "保存失败！");
        }
        return map;
    }


    @Override
    @Transactional
    public void deleteCertificate(String credentialskey) {
        /*String hql = "delete StaffCertificate where credentialskey = ?";
        beandao.saveBeansListAndexecuteHqlsByParams(null,
                new String[] { hql }, new Object[]{credentialskey});*/
        beandao.deleteBeanByKey(StaffCertificate.class,credentialskey);
    }

    /**
     * 查看
     * @param credentialskey
     * @return
     */
    @Override
    public StaffCertificate getCertificateByKey(String credentialskey) {
        StaffCertificate sc=(StaffCertificate)beandao.getBeanByKey(StaffCertificate.class,credentialskey);
        return sc;
    }


    @Override
    public StaffCertificate getCertificateById(String id) {
        String hql="StaffCertificate from credentialsID=?";
        StaffCertificate sc=(StaffCertificate)bbservice.getBeanByHqlAndParams(hql,new Object[]{id});
        return sc;
    }

    @Override
    public PageForm getCertificateByPage(int PageNumber,int pageSize, Map<String, Object> params) {
        PageForm pageForm =null;
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(StaffCertificate.class);
            dc.add(Restrictions.eq("staffID",params.get("staffID")));
            if (params != null&&params.size()>0) {
                if (params.get("credentialsNo") != null) {
                    dc.add(Restrictions.like ("credentialsNo",params.get("credentialsNo")));
                }
                if (params.get("credentialsType") != null) {
                    dc.add(Restrictions.eq("credentialsType",params.get("credentialsType")));
                }
            }
            dc.addOrder(Order.desc("credentialsID"));
            
            pageForm = bbservice.getPageFormByDC(PageNumber, pageSize,dc);
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageForm;
    }

    @Override
    public List<BaseBean> getCertificateList(Map<String, Object> params) {
        List<BaseBean> cer=new ArrayList<>();
        try {
            DetachedCriteria dc = DetachedCriteria.forClass(StaffCertificate.class);
            dc.add(Restrictions.eq("staffID",params.get("staffID")));
            if (params != null&&params.size()>0) {
                if (params.get("credentialsNo") != null) {
                    dc.add(Restrictions.like ("credentialsNo",params.get("credentialsNo")));
                }
                if (params.get("credentialsType") != null) {
                    dc.add(Restrictions.eq("credentialsType",params.get("credentialsType")));
                }
            }
            dc.addOrder(Order.desc("credentialsID"));

            cer= bbservice.getListByDC(dc);

        }catch (Exception e){
            e.printStackTrace();
        }
        return cer;
    }
}
