package hy.ea.human.service.impl;

import com.mysl.bo.administrative.DtMytravel;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.human.service.OutworkApplyMobileService;
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
public class OutworkApplyMobileServiceImpl implements OutworkApplyMobileService {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private SignInCheckOnService signInCheckOnService;

    @Override
    public void addOutworkApply(DtMytravel outworkApply) throws Exception {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        //校验重复请假，目前校验同一天不允许有多次请假
        checkLeaveApplyDate(outworkApply,null);

        outworkApply.setCompanyid(account.getCompanyID());
        outworkApply.setStaffid(account.getStaffID());
        outworkApply.setTravelHours(String.valueOf(computeOutworkHours(outworkApply.getTravelStartDate(),outworkApply.getTravelEndDate())));
        outworkApply.setAddtime(new Date());
        outworkApply.setApplyDate(dateTimeFormat.format(new Date()));
        baseBeanService.save(outworkApply);
    }

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
            //校验重复请假，目前校验同一天不允许有多次请假
            checkLeaveApplyDate(outworkApply,outworkApply.getKey());

            outworkApply.setStaffid(account.getStaffID());
            outworkApply.setCompanyid(dtMytravel.getCompanyid());
            outworkApply.setStatus(dtMytravel.getStatus());
            outworkApply.setTravelHours(String.valueOf(computeOutworkHours(outworkApply.getTravelStartDate(),outworkApply.getTravelEndDate())));
            outworkApply.setAddtime(dtMytravel.getAddtime());
            outworkApply.setApplyDate(dtMytravel.getApplyDate());
            baseBeanService.update(outworkApply);
        }
    }

    @Override
    public String findOutworkApplyList(PageForm pageForm,String outworkApplyTime) throws Exception {
        pageForm = getList(pageForm,outworkApplyTime);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    public PageForm getList(PageForm pageForm,String outworkApplyTime) {
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
        paramList.add(generateDate(outworkApplyTime));
        String sql = getDataUseSql();
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                        + sql.substring(sql.indexOf("FROM")),paramList.toArray());
        return pageForm;
    }

    private String getDataUseSql() {
        return "SELECT KEY,ID,STATUS,STAFFID,TRAVELSTARTDATE,TRAVELENDDATE,TRAVELHOURS,TRAVELCAUSE,AUDITOR_ID,APPLYDATE " +
                "                FROM DT_MYTravel " +
                "                WHERE COMPANYID = ? " +
                "                AND STAFFID IN (?) " +
                "                AND TO_DATE(APPLYDATE,'YYYY-MM-DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') " +
                "                ORDER BY APPLYDATE DESC,TRAVELSTARTDATE DESC";
    }

    @Override
    public void deleteOutworkApplyByKey(String key) {
        DtMytravel dtMyleave =  (DtMytravel) baseBeanService
                .getBeanByHqlAndParams("from DtMytravel where key = ?", new Object[]{key});
        if(Objects.nonNull(dtMyleave)){
            baseBeanService.deleteBeanByKey(DtMytravel.class,dtMyleave.getKey());
        }
    }

    @Override
    public String findOutworkApplyByKey(String key) {
        DtMytravel dtMytravel =  (DtMytravel) baseBeanService
                .getBeanByHqlAndParams("from DtMytravel where key = ?", new Object[]{key});
        if(Objects.nonNull(dtMytravel)){
            String auditorId = dtMytravel.getAuditorId();
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

    /**
     * 查询请假数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @return
     */
    private List findOutworkData(String startDate,String endDate, List<String> staffIdList){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }

        StringBuilder sb = new StringBuilder("SELECT ID,STATUS,STAFFID,STAFFNAME,TRAVELSTARTDATE,TRAVELENDDATE " +
                "FROM DT_MYTravel " +
                "WHERE STATUS = '02' ") ;
        String tempStr = "";
        for(String staffId : staffIdList){
            tempStr += "?,";
            paramList.add(staffId);
        }
        if(StringUtils.isNotBlank(tempStr)){
            sb.append("AND STAFFID IN ("+tempStr.substring(0,tempStr.length() - 1)+") ");
        }
        sb.append("AND COMPANYID = ? " +
                (StringUtils.isNotBlank(startDate) ? "AND TO_DATE(TRAVELSTARTDATE,'YYYY/MM/DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') AND TO_DATE(TRAVELSTARTDATE,'YYYY/MM/DD HH24:MI:SS') < TO_DATE(?, 'YYYY/MM/DD') " : "") +
                "ORDER BY STAFFID");
        if(Objects.nonNull(account.getCompanyID()) && StringUtils.isNotBlank(account.getCompanyID()))
        {
            paramList.add(account.getCompanyID());
        }
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sb.toString(), paramList.toArray());
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

    @Override
    public double computeOutworkHours(String startTime,String endTime) throws Exception{
        double hours = 0;
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)){
            return 0;
        }
        String signInSetupStartTime = "09:00";
        String signInSetupEndTime = "18:00";
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return 0;
        }
        String [] signInTime = signInCheckOnService.findSignInSetup(account.getCompanyID(),SIGN_IN_TYPE_ORDINARY);
        if(Objects.nonNull(signInTime)) {
            signInSetupStartTime = signInTime[0];
            signInSetupEndTime = signInTime[1];
        }
        String [] startDateArr = startTime.split(BLANK_CHAR);
        String [] endDateArr = endTime.split(BLANK_CHAR);

        if(startDateArr.length == 1){
            int days = signInCheckOnService.selectDays(startTime,endTime) + 1;
            hours = days * HOUR_WORK_DAY;
        }else{
            if(startDateArr[0].equals(endDateArr[0])){
                hours = signInCheckOnService.selectHours(startTime,endTime);
                hours = signInCheckOnService.leaveHours(hours,startDateArr[0],startTime,endTime,signInSetupStartTime,signInSetupEndTime);
            }else{
                double firstDayHours = signInCheckOnService.selectHours(startTime,startDateArr[0] + " " + signInSetupEndTime);
                double lastDayHours = signInCheckOnService.selectHours(endDateArr[0] + " " + signInSetupStartTime,endTime);
                firstDayHours = signInCheckOnService.leaveHours(firstDayHours,startDateArr[0],startTime,startDateArr[0] + " "
                        + signInSetupEndTime,signInSetupStartTime,signInSetupEndTime);
                lastDayHours = signInCheckOnService.leaveHours(lastDayHours,endDateArr[0],endDateArr[0] + " " + signInSetupStartTime,endTime,signInSetupStartTime,signInSetupEndTime);

                hours = firstDayHours + lastDayHours;
                int days = signInCheckOnService.selectDays(startDateArr[0],endDateArr[0]);
                if(days > 1){
                    hours += (days - 1) * HOUR_WORK_DAY;
                }
            }
        }
        hours = new BigDecimal(hours).setScale(1, RoundingMode.HALF_UP).doubleValue();
        return hours;
    }

    public String[] findSignInSetup(){
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }
        return signInCheckOnService.findSignInSetup(account.getCompanyID(),SIGN_IN_TYPE_ORDINARY);
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

    private boolean selectOutworkApplyList(String outworkApplyDate,String key){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return false;
        }
        String keyStr = "";
        if(Objects.nonNull(key)){
            paramList.add(key);
            keyStr = "AND key != ? ";
        }
        paramList.add(account.getCompanyID());
        paramList.add(account.getStaffID());
        paramList.add(outworkApplyDate);
        paramList.add(outworkApplyDate);
        List<BaseBean> leaveApplyList =  baseBeanService.getListBeanBySqlAndParams("SELECT KEY,ID,STATUS " +
                        "FROM DT_MYTravel " +
                        "WHERE 1=1 " +
                        keyStr +
                        "AND COMPANYID = ? " +
                        "AND STAFFID = ? " +
                        "AND TO_DATE(?, 'YYYY-MM-DD') >= TO_DATE(SUBSTR(TRAVELSTARTDATE,0,10),'YYYY-MM-DD') " +
                        "AND TO_DATE(?, 'YYYY-MM-DD') <= TO_DATE(SUBSTR(TRAVELENDDATE,0,10),'YYYY-MM-DD') " +
                        "AND STATUS != '03'", paramList.toArray());
        if(Objects.nonNull(leaveApplyList) && leaveApplyList.size() > 0){
            return false;
        }
        return true;
    }

    /**
     * 校验重复外勤，目前校验同一天不允许有多次请假
     * @param
     */
    private void checkLeaveApplyDate(DtMytravel outwrokApply,String key) throws Exception{
        List<String> dateArray = new ArrayList<>();
        String outworkStartDate = outwrokApply.getTravelStartDate().split(" ")[0];
        String outworkEndDate = outwrokApply.getTravelEndDate().split(" ")[0];
        dateArray.add(outworkStartDate);
        int days = signInCheckOnService.selectDays(outworkStartDate,outworkEndDate);
        if(days > 0){
            String date = outworkStartDate;
            for(int i = 0;i < days;i++){
                date = signInCheckOnService.selectNextDate(date);
                if(StringUtils.isNotBlank(date)){
                    dateArray.add(date);
                }else{
                    break;
                }
            }
        }
        for(String date : dateArray){
            if(!selectOutworkApplyList(date,key)){
                throw new RuntimeException("外勤时间重复");
            }
        }
    }

    public List findCheckOnReviewer(){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("SELECT S.STAFFID,S.STAFFNAME " +
                "FROM DT_HR_STAFF_ROLE R,DT_HR_STAFF S " +
                "WHERE S.STAFFID = R.STAFF_ID " +
                "AND ROLE_ID = (" +
                "SELECT ROLEID FROM DTCROLE " +
                "WHERE COMPANYID = ? " +
                "AND ROLENAME = (" +
                "SELECT DICT_NAME FROM DT_DICT_DATA " +
                "WHERE COMPANY_ID = ? " +
                "AND DICT_TYPE = ? ))" +
                "AND R.COMPANY_ID = ? " +
                "ORDER BY S.STAFFNAME");
        paramList.add(account.getCompanyID());
        paramList.add(account.getCompanyID());
        paramList.add(CHECKON_MANAGE_ROLE_NAME);
        paramList.add(account.getCompanyID());
        return baseBeanService.getListBeanBySqlAndParams(sb.toString(), paramList.toArray());
    }

}
