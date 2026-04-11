package hy.ea.human.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysl.bo.administrative.DtMytravel;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.Sign;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.vo.LeaveData;
import hy.ea.bo.human.vo.SignInResult;
import hy.ea.human.service.OutworkReviewMobileService;
import hy.ea.human.service.SignInCheckOnService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

import static hy.ea.human.constant.Constant.*;

@Service
@Transactional
public class OutworkReviewMobileServiceImpl implements OutworkReviewMobileService {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private SignInCheckOnService signInCheckOnService;

    @Override
    public void updateOutworkApply(DtMytravel outworkApply) throws Exception {
        DtMytravel dtMytravel =  (DtMytravel) baseBeanService
                .getBeanByHqlAndParams("from DtMytravel where key = ?", new Object[]{outworkApply.getKey()});
        if(Objects.nonNull(dtMytravel)){
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            if (account == null) {
                return;
            }
            dtMytravel.setStatus(outworkApply.getStatus());
            dtMytravel.setAuditTime(dateTimeFormat.format(new Date()));
            baseBeanService.update(dtMytravel);
            //更新打卡状态
            updateSignInStatus(dtMytravel);
        }
    }

    @Override
    public String findOutworkApplyList(PageForm pageForm,String outworkApplyTime,String status) throws Exception {
        pageForm = getList(pageForm,outworkApplyTime,status);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    public PageForm getList(PageForm pageForm,String outworkApplyTime,String status) {
        if(Objects.isNull(pageForm) || Objects.isNull(outworkApplyTime)){
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
        paramList.add(generateDate(outworkApplyTime));
        String sql = getDataUseSql();
        if("-1".equals(status)){
            sql = sql.replace("AND T.STATUS  = ?","");
        }
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                        + sql.substring(sql.indexOf("FROM")),paramList.toArray());
        return pageForm;
    }

    private String getDataUseSql() {
        return "SELECT T.KEY,T.ID,T.STATUS,T.STAFFID,S.STAFFNAME,T.TRAVELSTARTDATE,T.TRAVELENDDATE,T.TRAVELHOURS,T.TRAVELCAUSE,T.AUDITOR_ID,T.APPLYDATE,T.STARTDATE,T.ENDDATE " +
                "                                FROM DT_MYTravel T,DT_HR_STAFF S " +
                "                                WHERE T.STAFFID = S.STAFFID " +
                "                                AND T.COMPANYID = ? " +
                "                                AND T.AUDITOR_ID = ? " +
                "                                AND T.STATUS  = ? " +
                "                                AND TO_DATE(APPLYDATE,'YYYY-MM-DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') " +
                "                                ORDER BY T.STAFFID,T.APPLYDATE DESC,T.TRAVELSTARTDATE DESC";
    }

    @Override
    public String findOutworkApplyByKey(String key) {
        DtMytravel dtMytravel = (DtMytravel) baseBeanService
                .getBeanByHqlAndParams("from DtMytravel where key = ?", new Object[]{key});
        if(Objects.nonNull(dtMytravel)){
            String staffId = dtMytravel.getStaffid();
            String auditorId = dtMytravel.getAuditorId();
            if(Objects.nonNull(staffId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
                dtMytravel.setStaffname(staff.getStaffName());
            }
            if(Objects.nonNull(auditorId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{auditorId});
                dtMytravel.setAuditor(staff.getStaffName());
            }
            dtMytravel.setAddtime(null);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dtMytravel", dtMytravel);
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

    private void updateSignInStatus(DtMytravel dtMytravel){
        String status = dtMytravel.getStatus();
        String signInStatus = "F";
        if("02".equals(status)){
            signInStatus = "T";
        }
        String[] startDate = dtMytravel.getTravelStartDate().split(BLANK_CHAR);
        String[] endDate = dtMytravel.getTravelEndDate().split(BLANK_CHAR);

        List signInList = findSignInList(startDate[0],endDate[0],Arrays.asList(dtMytravel.getStaffid()),dtMytravel.getCompanyid());
        if(Objects.nonNull(signInList) && !signInList.isEmpty()){
            Object obj = signInList.get(0);
            Object[] checkOnArr = (Object[]) obj;
            String account = checkOnArr[1].toString();

            List<BaseBean> signList =  baseBeanService
                    .getListBeanByHqlAndParams("from Sign where account = ? and companyId= ? and signType = '00' " +
                            "and signDate >= TO_DATE(?, 'YYYY-MM-DD') " +
                            "and signDate <= TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') ", new Object[]{account,dtMytravel.getCompanyid(),startDate[0],endDate[0] + " 23:59:59"});
            if(Objects.nonNull(signList) && signList.size() > 0){
                List<BaseBean> signIns = new ArrayList<>();
                for(BaseBean signObj : signList){
                    Sign sign = (Sign) signObj;
                    sign.setStatus(signInStatus);
                    signIns.add(sign);
                }
                try {
                    baseBeanService.saveBeansListAndexecuteSqlsByParams(signIns,null,null);
                }catch (Exception e){
                    logger.error("操作异常", e);
                }
            }
        }
    }

    private List findSignInList(String startDate,String endDate, List<String> staffIdList,String  companyId){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("SELECT S.SIGNKEY,S.ACCOUNT,S.COMPANYID,ST.STAFFID " +
                "FROM DT_WFJ_SIGN S,DT_HR_STAFF ST " +
                "WHERE S.account = ST.reference ") ;
        String tempStr = "";
        for(String staffId : staffIdList){
            tempStr += "?,";
            paramList.add(staffId);
        }
        if(StringUtils.isNotBlank(tempStr)){
            sb.append("AND ST.STAFFID IN ("+tempStr.substring(0,tempStr.length() - 1)+") ");
        }
        sb.append("AND S.COMPANYID = ? " +
                "AND S.SIGNTYPE = '00' " +
                "AND S.signdate >= TO_DATE(?, 'YYYY-MM-DD') AND S.signdate <= TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS') " +
                "ORDER BY S.ACCOUNT,S.SIGNDATE");
        paramList.add(companyId);
        paramList.add(startDate);
        paramList.add(endDate + " 23:59:59");
        return baseBeanService.getListBeanBySqlAndParams(sb.toString(), paramList.toArray());
    }

}
