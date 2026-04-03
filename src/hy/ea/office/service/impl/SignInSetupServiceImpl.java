package hy.ea.office.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.office.bo.SignInSetup;
import hy.ea.office.service.SignInSetupService;
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
public class SignInSetupServiceImpl implements SignInSetupService {
    @Resource
    private BaseBeanService baseBeanService;
    @Override
    public String findSignInSetupList(PageForm pageForm) {
        pageForm = getList(pageForm);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    @Override
    public void addSignInSetup(SignInSetup signInSetup) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        signInSetup.setStaffId(account.getStaffID());
        signInSetup.setCompanyId(account.getCompanyID());
        baseBeanService.save(signInSetup);
    }

    @Override
    public void deleteSignInSetupById(String id) {
        SignInSetup signInSetup =  (SignInSetup) baseBeanService
                .getBeanByHqlAndParams("from SignInSetup where signInSetupId = ?", new Object[]{id});
        if(Objects.nonNull(signInSetup)){
            baseBeanService.deleteBeanByKey(SignInSetup.class,signInSetup.getSignInSetupId());
        }
    }

    @Override
    public String findSignInSetupById(String id) {
        Object[] obj =  (Object[])baseBeanService.getObjectByHqlAndParams("SELECT S.signInSetupId,S.companyId,S.signInTypeId," +
                        "S.startTime,S.endTime,S.signInAddress,T.signInTypeName,S.staffId " +
                        "from SignInSetup S,SignInType T " +
                        "where S.signInTypeId  = T.signInTypeId " +
                        "and S.signInSetupId = ?", new Object[]{id});
        if(Objects.nonNull(obj)){
            SignInSetup signInSetup = new SignInSetup(obj[0].toString(), obj[1].toString(), obj[2].toString(),
                    obj[3].toString(), obj[4].toString(), obj[5].toString(), null, obj[6].toString(), Objects.nonNull(obj[7]) ? obj[7].toString() : null);
            String staffId = signInSetup.getStaffId();
            if(Objects.nonNull(staffId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
                signInSetup.setStaffName(staff.getStaffName());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("signInSetup", signInSetup);
            JSONObject json = JSONObject.fromObject(map);
            return json.toString();
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
    public void updateSignInSetup(SignInSetup signInSetup) {
        SignInSetup signInSetupOld =  (SignInSetup) baseBeanService
                .getBeanByHqlAndParams("from SignInSetup where signInSetupId = ?", new Object[]{signInSetup.getSignInSetupId()});
        if(Objects.nonNull(signInSetupOld)){
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            if (account == null) {
                return;
            }
            signInSetup.setStaffId(account.getStaffID());
            signInSetup.setSetupDate(new Date());
            signInSetup.setCompanyId(signInSetupOld.getCompanyId());
            baseBeanService.update(signInSetup);
        }
    }

    @Override
    public String findSignInType() {
        PageForm pageForm =  baseBeanService.getPageForm(1,1000,"from SignInType " +
                "order by signInTypeSerial",new Object[]{});
        if(Objects.nonNull(pageForm)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("signInType", pageForm);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
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
        String sql = "SELECT S.SIGNIN_SETUP_ID signInSetupId,S.COMPANY_ID companyId,S.SIGNIN_TYPE_ID signInTypeId," +
                "S.START_TIME startTime,S.END_TIME endTime,S.SIGNIN_ADDRESS signInAddress,T.SIGNIN_TYPE_NAME signInTypeName " +
                "FROM DT_SIGNIN_SETUP S,DT_SIGNIN_TYPE T " +
                "WHERE S.SIGNIN_TYPE_ID  = T.SIGNIN_TYPE_ID " +
                "AND S.COMPANY_ID = ? " +
                "ORDER BY T.SIGNIN_TYPE_SERIAL,S.START_TIME,S.END_TIME,S.SIGNIN_ADDRESS";
        return sql;
    }

    private String getDataUseHql() {
        String sql = "SELECT S.signInSetupId,S.companyId,S.signInTypeId," +
                "S.startTime,S.endTime,S.signInAddress,T.signInTypeName " +
                "FROM SignInSetup S, SignInType T " +
                "WHERE S.signInTypeId  = T.signInTypeId " +
                "AND S.companyId = ? " +
                "ORDER BY T.signInTypeSerial,S.startTime,S.endTime,S.signInAddress";
        return sql;
    }
}
