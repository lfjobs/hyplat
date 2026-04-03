package hy.ea.human.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.SocialSecuritySetup;
import hy.ea.bo.human.Staff;
import hy.ea.human.service.SocialSecuritySetupService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class SocialSecuritySetupServiceImpl implements SocialSecuritySetupService {
    @Resource
    private BaseBeanService baseBeanService;
    @Override
    public String findSocialSecuritySetupList(PageForm pageForm) {
        pageForm = getList(pageForm);
        if(Objects.nonNull(pageForm)){
            List<BaseBean> socialSecuritySetupList = new ArrayList<>();
            List<BaseBean> objectList = pageForm.getList();
            if(Objects.nonNull(objectList) && !objectList.isEmpty()){
                for (Object obj : objectList) {
                    Object[] arr = (Object[]) obj;
                    SocialSecuritySetup socialSecuritySetup = new SocialSecuritySetup(arr[0].toString(),arr[1].toString(),arr[2].toString(),arr[3].toString(),
                            Objects.nonNull(arr[4]) ? arr[4].toString():null,null,null,null,
                            Objects.nonNull(arr[8]) ? arr[8].toString():null,
                            Objects.nonNull(arr[9]) ? arr[9].toString():null,
                            Objects.nonNull(arr[10]) ? arr[10].toString():null,null,null);
                    socialSecuritySetupList.add(socialSecuritySetup);
                }
                pageForm.setList(socialSecuritySetupList);
            }

            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    @Override
    public void addSocialSecuritySetup(SocialSecuritySetup socialSecuritySetup) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        socialSecuritySetup.setCompanyId(account.getCompanyID());
        socialSecuritySetup.setSetStaffId(account.getStaffID());
        baseBeanService.save(socialSecuritySetup);
    }

    @Override
    public void deleteSocialSecuritySetupById(String id) {
        SocialSecuritySetup socialSecuritySetup =  (SocialSecuritySetup) baseBeanService
                .getBeanByHqlAndParams("from SocialSecuritySetup where socialSecuritySetupId = ?", new Object[]{id});
        if(Objects.nonNull(socialSecuritySetup)){
            baseBeanService.deleteBeanByKey(SocialSecuritySetup.class,socialSecuritySetup.getSocialSecuritySetupId());
        }
    }

    @Override
    public String findSocialSecuritySetupById(String id) {
        SocialSecuritySetup socialSecuritySetup =  (SocialSecuritySetup) baseBeanService
                .getBeanByHqlAndParams("from SocialSecuritySetup where socialSecuritySetupId = ?", new Object[]{id});
        if(Objects.nonNull(socialSecuritySetup)){
            String staffId = socialSecuritySetup.getSetStaffId();
            if(Objects.nonNull(staffId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
                socialSecuritySetup.setStaffName(staff.getStaffName());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("socialSecuritySetup", socialSecuritySetup);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    @Override
    public String findCurrentUser() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if(Objects.nonNull(account)){
            Staff staff =  (Staff) baseBeanService
                    .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{account.getStaffID()});
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("staff", staff);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    @Override
    public void updateSocialSecuritySetup(SocialSecuritySetup socialSecuritySetup) {
        SocialSecuritySetup SocialSecuritySetupOld =  (SocialSecuritySetup) baseBeanService
                .getBeanByHqlAndParams("from SocialSecuritySetup where socialSecuritySetupId = ?", new Object[]{socialSecuritySetup.getSocialSecuritySetupId()});
        if(Objects.nonNull(SocialSecuritySetupOld)){
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            if (account == null) {
                return;
            }
            socialSecuritySetup.setSetStaffId(account.getStaffID());
            socialSecuritySetup.setSetupDate(new Date());
            socialSecuritySetup.setCompanyId(SocialSecuritySetupOld.getCompanyId());
            baseBeanService.update(socialSecuritySetup);
        }
    }

    public PageForm getList(PageForm pageForm) {
        Object[] param = new Object[]{null};
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getCompanyID()) && StringUtils.isNotBlank(account.getCompanyID()))
            {
                param[0] = account.getCompanyID();
            }
        }
        String sql = getDataUseSql();
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                + sql.substring(sql.indexOf("FROM")),param);
        return pageForm;
    }

    private String getDataUseSql() {
        String sql = "SELECT S.SOCIAL_SECURITY_SETUP_ID socialSecuritySetupId,S.COMPANY_ID companyId,S.DEDUCTION_TYPE deductionType," +
                "                S.SOCIAL_SECURITY_LOW_LEVEL socialSecurityLowLevel,S.SOCIAL_SECURITY_LEVEL socialSecurityLevel," +
                "                S.ELDERLY_CARE_RATE elderlyCareRate,S.MEDICAL_RATE medicalRate,S.UNEMPLOYMENT_RATE unemploymentRete," +
                "                S.ELDERLY_CARE_DISCOUNT_MONEY elderlyCareDiscountMoney,S.MEDICAL_DISCOUNT_MONEY medicalDiscountMoney,S.UNEMPLOYMENT_DISCOUNT_MONEY unemploymentDiscountMoney,S.SET_STAFF_ID setStaffId " +
                "                FROM DT_SOCIAL_SECURITY_SETUP S " +
                "                WHERE S.COMPANY_ID = ? " +
                "                ORDER BY S.COMPANY_ID,S.DEDUCTION_TYPE";
        return sql;
    }

    public SocialSecuritySetup findSocialSecuritySetupByCompanyId(String companyId) {
        SocialSecuritySetup socialSecuritySetup = (SocialSecuritySetup) baseBeanService
                .getBeanByHqlAndParams("from SocialSecuritySetup where companyId = ?", new Object[]{companyId});
        return socialSecuritySetup;
    }
}
