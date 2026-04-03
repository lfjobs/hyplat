package hy.ea.human.service.impl;

import com.mysl.bo.administrative.DtMyovertime;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.Staff;
import hy.ea.human.service.OvertimeApplyMobileService;
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
public class OvertimeApplyMobileServiceImpl implements OvertimeApplyMobileService {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private SignInCheckOnService signInCheckOnService;

    @Override
    public void addOvertimeApply(DtMyovertime overtimeApply) throws Exception {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        //校验重复请假，目前校验同一天不允许有多次加班
        checkOvertimeApplyDate(overtimeApply,null);

        overtimeApply.setCompanyid(account.getCompanyID());
        overtimeApply.setStaffid(account.getStaffID());
        overtimeApply.setOverTimeHour(String.valueOf(computeOvertimeHours(overtimeApply.getOverTimeStartDate(),overtimeApply.getOverTimeEndDate())));
        overtimeApply.setAddtime(new Date());
        overtimeApply.setApplyDate(dateTimeFormat.format(new Date()));
        baseBeanService.save(overtimeApply);
    }

    @Override
    public void updateOvertimeApply(DtMyovertime overtimeApply) throws Exception {
        DtMyovertime dtMyovertime =  (DtMyovertime) baseBeanService
                .getBeanByHqlAndParams("from DtMyovertime where key = ?", new Object[]{overtimeApply.getKey()});
        if(Objects.nonNull(dtMyovertime)){
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            if (account == null) {
                return;
            }
            //校验重复请假，目前校验同一天不允许有多次请假
            checkOvertimeApplyDate(overtimeApply,overtimeApply.getKey());

            overtimeApply.setStaffid(account.getStaffID());
            overtimeApply.setCompanyid(dtMyovertime.getCompanyid());
            overtimeApply.setStatus(dtMyovertime.getStatus());
            overtimeApply.setOverTimeHour(String.valueOf(computeOvertimeHours(overtimeApply.getOverTimeStartDate(),overtimeApply.getOverTimeEndDate())));
            overtimeApply.setAddtime(dtMyovertime.getAddtime());
            overtimeApply.setApplyDate(dtMyovertime.getApplyDate());
            baseBeanService.update(overtimeApply);
        }
    }

    @Override
    public String findOvertimeApplyList(PageForm pageForm,String overtimeApplyTime) throws Exception {
        pageForm = getList(pageForm,overtimeApplyTime);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    public PageForm getList(PageForm pageForm,String overtimeApplyTime) {
        if(Objects.isNull(pageForm) || Objects.isNull(overtimeApplyTime)){
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
        paramList.add(generateDate(overtimeApplyTime));
        String sql = getDataUseSql();
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                        + sql.substring(sql.indexOf("FROM")),paramList.toArray());
        return pageForm;
    }

    private String getDataUseSql() {
        return "SELECT KEY,ID,STATUS,STAFFID,OVERTIMESTARTDATE,OVERTIMEENDDATE,OVERTIMEHOUR,OVERTIMEREASON,AUDITOR_ID,APPLYDATE " +
                "                                FROM DT_MYOVERTIME " +
                "                                WHERE COMPANYID = ? " +
                "                                AND STAFFID IN (?) " +
                "                                AND TO_DATE(APPLYDATE,'YYYY-MM-DD HH24:MI:SS') >= TO_DATE(?, 'YYYY-MM-DD') " +
                "                                ORDER BY APPLYDATE DESC,OVERTIMESTARTDATE DESC";
    }

    @Override
    public void deleteOvertimeApplyByKey(String key) {
        DtMyovertime dtMyovertime =  (DtMyovertime) baseBeanService
                .getBeanByHqlAndParams("from DtMyovertime where key = ?", new Object[]{key});
        if(Objects.nonNull(dtMyovertime)){
            baseBeanService.deleteBeanByKey(DtMyovertime.class,dtMyovertime.getKey());
        }
    }

    @Override
    public String findOvertimeApplyByKey(String key) {
        DtMyovertime dtMyovertime =  (DtMyovertime) baseBeanService
                .getBeanByHqlAndParams("from DtMyovertime where key = ?", new Object[]{key});
        if(Objects.nonNull(dtMyovertime)){
            String auditorId = dtMyovertime.getAuditorId();
            if(Objects.nonNull(auditorId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{auditorId});
                dtMyovertime.setAuditor(staff.getStaffName());
            }
            dtMyovertime.setAddtime(null);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dtMyovertime", dtMyovertime);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    /**
     * 查询加班数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @return
     */
    private List findOvertimeData(String startDate,String endDate, List<String> staffIdList){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }

        StringBuilder sb = new StringBuilder("SELECT ID,STATUS,STAFFID,STAFFNAME,OVERTIMESTARTDATE,OVERTIMEENDDATE " +
                "FROM DT_MYOVERTIME " +
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
                (StringUtils.isNotBlank(startDate) ? "AND TO_DATE(OVERTIMESTARTDATE,'YYYY/MM/DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') AND TO_DATE(OVERTIMEENDDATE,'YYYY/MM/DD HH24:MI:SS') < TO_DATE(?, 'YYYY/MM/DD') " : "") +
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
    public double computeOvertimeHours(String startTime,String endTime) throws Exception{
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

    private boolean selectOvertimeApplyList(String overtimeApplyDate,String key){
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
        paramList.add(overtimeApplyDate);
        paramList.add(overtimeApplyDate);
        List<BaseBean> overtimeApplyList =  baseBeanService.getListBeanBySqlAndParams("SELECT KEY,ID,STATUS " +
                        "FROM DT_MYOVERTIME " +
                        "WHERE 1=1 " +
                        keyStr +
                        "AND COMPANYID = ? " +
                        "AND STAFFID = ? " +
                        "AND TO_DATE(?, 'YYYY-MM-DD') >= TO_DATE(SUBSTR(OVERTIMESTARTDATE,0,10),'YYYY-MM-DD') " +
                        "AND TO_DATE(?, 'YYYY-MM-DD') <= TO_DATE(SUBSTR(OVERTIMEENDDATE,0,10),'YYYY-MM-DD') " +
                        "AND STATUS != '03'", paramList.toArray());
        if(Objects.nonNull(overtimeApplyList) && overtimeApplyList.size() > 0){
            return false;
        }
        return true;
    }

    /**
     * 校验重复加班，目前校验同一天不允许有多次加班
     * @param
     */
    private void checkOvertimeApplyDate(DtMyovertime overtimeApply,String key) throws Exception{
        List<String> dateArray = new ArrayList<>();
        String overtimeStartDate = overtimeApply.getOverTimeStartDate().split(" ")[0];
        String overtimeEndDate = overtimeApply.getOverTimeEndDate().split(" ")[0];
        dateArray.add(overtimeStartDate);
        int days = signInCheckOnService.selectDays(overtimeStartDate,overtimeEndDate);
        if(days > 0){
            String date = overtimeStartDate;
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
            if(!selectOvertimeApplyList(date,key)){
                throw new RuntimeException("加班时间重复");
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

    @Override
    public String checkOvertime(String startDate,String endDate) throws Exception {
        String[] start = startDate.split(" ");
        String[] end = endDate.split(" ");
        if(isHoliday(start[0]) && isHoliday(end[0])){
            return "true";
        }
        if(isWeekendDay(start[0]) || isWeekendDay(end[0])){
            return "false";
        }
        if(!isHoliday(start[0]) && !isHoliday(end[0])){
            if(!start[0].equals(end[0])){
                return "false";
            }
            if(start.length == 2 && end.length == 2){
                String[] signInSetup = this.findSignInSetup();
                Date signInEnd = dateTimeFormat.parse(start[0] + " " + signInSetup[1] + ":00");
                Date overtimeStart = dateTimeFormat.parse(startDate + ":00");
                if(overtimeStart.getTime() >= signInEnd.getTime()){
                    return "true";
                }else{
                    return "false";
                }
            }else{
                return "false";
            }
        }else{
            return "false";
        }
    }

    public boolean isWeekendDay(String date){
        Calendar cal = Calendar.getInstance();
        List<String> endWeekDate = Arrays.asList(END_WEEK_OF_WORK_DATE);
        if(endWeekDate.contains(date)){
            return false;
        }else{
            String[] dates = date.split("-");
            cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
            if(cal.get(Calendar.DAY_OF_WEEK) != 1 && cal.get(Calendar.DAY_OF_WEEK) != 7){
                return false;
            }
        }
        return true;
    }

    public boolean isHoliday(String date){
        List<String> holidayDate2024 = Arrays.asList(HOLIDAY_DATE_2024);
        List<String> holidayDate2025 = Arrays.asList(HOLIDAY_DATE_2025);
        List<String> holidayDate = new ArrayList<>();
        holidayDate.addAll(holidayDate2024);
        holidayDate.addAll(holidayDate2025);
        if(holidayDate.contains(date)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isBigHoliday(String date){
        List<String> bigHolidayDate = Arrays.asList(BIG_HOLIDAY_2024_2025_2026);
        if(bigHolidayDate.contains(date)){
            return true;
        }else{
            return false;
        }
    }

    public boolean isNoNeedSignIn(String date){
        List<String> noNeedSignIn = Arrays.asList(NO_NEED_SIGN_IN);
        if(noNeedSignIn.contains(date)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 排除周6，7
     * @param yearMonth
     * @return
     */
    public int holidayDayCount(String yearMonth){
        Calendar cal = Calendar.getInstance();
        int count = 0;
        List<String> holidayDate2024 = Arrays.asList(HOLIDAY_DATE_2024);
        List<String> holidayDate2025 = Arrays.asList(HOLIDAY_DATE_2025);
        List<String> holidayDate = new ArrayList<>();
        holidayDate.addAll(holidayDate2024);
        holidayDate.addAll(holidayDate2025);
        for(String date : holidayDate){
            if(date.contains(yearMonth)){
                String[] dates = date.split("-");
                cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
                if(cal.get(Calendar.DAY_OF_WEEK) != 1 && cal.get(Calendar.DAY_OF_WEEK) != 7){
                    count++;
                }
            }
        }
        return count;
    }

}
