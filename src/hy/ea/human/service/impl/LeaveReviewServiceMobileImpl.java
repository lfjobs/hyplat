package hy.ea.human.service.impl;

import com.mysl.bo.administrative.DtMyleave;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.human.service.LeaveApplyMobileService;
import hy.ea.human.service.LeaveReviewMobileService;
import hy.ea.human.service.SignInCheckOnService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

import static hy.ea.human.constant.Constant.*;

@Service
@Transactional
public class LeaveReviewServiceMobileImpl implements LeaveReviewMobileService {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    @Resource
    private BaseBeanService baseBeanService;

    @Override
    public void updateLeaveApply(DtMyleave leaveApply) throws Exception {
        DtMyleave dtMyleave =  (DtMyleave) baseBeanService
                .getBeanByHqlAndParams("from DtMyleave where key = ?", new Object[]{leaveApply.getKey()});
        if(Objects.nonNull(dtMyleave)){
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            if (account == null) {
                return;
            }
            dtMyleave.setStatus(leaveApply.getStatus());
            dtMyleave.setAuditTime(dateTimeFormat.format(new Date()));
            baseBeanService.update(dtMyleave);
        }
    }

    @Override
    public String findLeaveApplyList(PageForm pageForm,String leaveApplyTime,String status) throws Exception {
        pageForm = getList(pageForm,leaveApplyTime,status);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    public PageForm getList(PageForm pageForm,String leaveApplyTime,String status) {
        if(Objects.isNull(pageForm) || Objects.isNull(leaveApplyTime)){
            return pageForm;
        }
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getStaffID()) && StringUtils.isNotBlank(account.getStaffID()))
            {
                paramList.add(account.getCompanyID());
                paramList.add(account.getStaffID());
            }else {
                return pageForm;
            }
        }else {
            return pageForm;
        }
        if(!"-1".equals(status)){
            paramList.add(status);
        }
        paramList.add(generateDate(leaveApplyTime));
        String sql = getDataUseSql();
        if("-1".equals(status)){
            sql = sql.replace("AND L.STATUS  = ?","");
        }
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                        + sql.substring(sql.indexOf("FROM")),paramList.toArray());
        return pageForm;
    }

    private String getDataUseSql() {
        return "SELECT L.KEY,L.ID,L.STATUS,L.STAFFID,S.STAFFNAME,L.LEAVESTARTDATE,L.LEAVEENDDATE,L.LEAVETYPE,L.LEAVEHOUR,L.LEAVEREASON,L.AUDITOR_ID,L.APPLYDATE " +
                "                FROM DT_MYLeave L,DT_HR_STAFF S " +
                "                WHERE L.STAFFID = S.STAFFID " +
                "                AND L.COMPANYID = ? " +
                "                AND L.AUDITOR_ID = ? " +
                "                AND L.STATUS  = ? " +
                "                AND TO_DATE(APPLYDATE,'YYYY-MM-DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') " +
                "                ORDER BY L.STAFFID,L.LEAVETYPE,L.APPLYDATE DESC,L.LEAVESTARTDATE DESC";
    }

    @Override
    public String findLeaveApplyByKey(String key) {
        DtMyleave dtMyleave =  (DtMyleave) baseBeanService
                .getBeanByHqlAndParams("from DtMyleave where key = ?", new Object[]{key});
        if(Objects.nonNull(dtMyleave)){
            String staffId = dtMyleave.getStaffid();
            String auditorId = dtMyleave.getAuditorId();
            if(Objects.nonNull(staffId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
                dtMyleave.setStaffname(staff.getStaffName());
            }
            if(Objects.nonNull(auditorId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{auditorId});
                dtMyleave.setAuditor(staff.getStaffName());
            }
            dtMyleave.setAddtime(null);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dtMyleave", dtMyleave);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    private boolean checkRole(String staffId,String companyId){
        boolean result = false;
        List<String> roleList = new ArrayList<>();
        List<String> staffRoleList = new ArrayList<>();

        List<BaseBean> setDataList =  baseBeanService
                .getListBeanBySqlAndParams("SELECT DICT_NAME FROM Dt_dict_data " +
                        "WHERE COMPANY_ID = ? " +
                        "AND DICT_TYPE = 'CHECKON_ALL'", new Object[]{companyId});
        if(Objects.nonNull(setDataList) && setDataList.size() > 0){
            for(Object obj : setDataList){
                roleList.add(obj.toString());
            }
            List<BaseBean> dataList =  baseBeanService
                    .getListBeanBySqlAndParams("SELECT H.ROLE_ID,R.ROLENAME " +
                            "FROM Dt_hr_staff_role H,Dtcrole R " +
                            "WHERE H.ROLE_ID = R.ROLEID  " +
                            "AND STAFF_ID = ? " +
                            "AND COMPANY_ID = ? ", new Object[]{staffId,companyId});
            if(Objects.nonNull(dataList) && dataList.size() > 0) {
                for (Object obj : dataList) {
                    Object[] arr = (Object[]) obj;
                    staffRoleList.add(arr[1].toString());
                }
            }

            if(staffRoleList.size() > 0){
                for(String roleName : staffRoleList){
                    for(String role : roleList){
                        if(roleName.indexOf(role) != -1){
                            result = true;
                            break;
                        }
                    }
                    if(result){
                        break;
                    }
                }
            }
        }
        return result;
    }

    private String generateDate(String leaveApplyTime){
        String date = selectDate("0");
        if(TIME_RANGE_1M.equals(leaveApplyTime)){
            date = selectDate("0");
        }else if(TIME_RANGE_3M.equals(leaveApplyTime)){
            date = selectDate("-2");
        }else if(TIME_RANGE_6M.equals(leaveApplyTime)){
            date = selectDate("-5");
        }else if(TIME_RANGE_1Y.equals(leaveApplyTime)){
            date = selectDate("-11");
        }else if(TIME_RANGE_2Y.equals(leaveApplyTime)){
            date = selectDate("-23");
        }
        return date;
    }

    private String selectDate(String month){
        return baseBeanService.getObjectBySqlAndParams("SELECT ADD_MONTHS(TRUNC(SYSDATE, 'MM'), ?) AS NEXT_MONTH_START FROM DUAL"
                , new Object[]{month}).toString();
    }

}
