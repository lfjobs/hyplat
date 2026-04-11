package hy.ea.human.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.SocialSecuritySetup;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.salary.*;
import hy.ea.bo.human.vo.LeaveData;
import hy.ea.bo.human.vo.OvertimeData;
import hy.ea.bo.human.vo.SignInCheckOn;
import hy.ea.bo.human.vo.SignInResult;
import hy.ea.human.service.OvertimeApplyMobileService;
import hy.ea.human.service.SalarySettlementService;
import hy.ea.human.service.SignInCheckOnService;
import hy.ea.human.service.SocialSecuritySetupService;
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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static hy.ea.human.constant.Constant.*;

@Service
@Transactional
public class SalarySettlementServiceImpl implements SalarySettlementService {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private SignInCheckOnService signInCheckOnService;
    @Resource
    private OvertimeApplyMobileService overtimeApplyMobileService;
    @Resource
    private SocialSecuritySetupService socialSecuritySetupService;

    @Override
    public String findSalarySettlementList(PageForm pageForm,String settlementTime) throws Exception {
        pageForm = getList(pageForm,settlementTime);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    private PageForm getList(PageForm pageForm,String settlementTime) {
        if(Objects.isNull(pageForm) || Objects.isNull(settlementTime)){
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
        paramList.add(settlementTime);
        String sql = getDataUseSql();
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 12), sql,"select count(1) "
                        + sql.substring(sql.indexOf("FROM")),paramList.toArray());
        return pageForm;
    }

    private String getDataUseSql() {
        return "SELECT DISTINCT S.ROLE_SALARY,S.STAFF_ID,ST.STAFFNAME,S.COMPANY_ID,S.SETTLEMENT_MONTH,S.SALARY_LEVEL_NUM,S.BASE_SALARY," +
                "       S.GUARANTEE_SALARY,S.WELFARE_SALARY,S.TOTAL_SALARY,S.LATE_DISCOUNT_MONEY,S.LEAVE_EARLY_DISCOUNT_MONEY," +
                "       S.ABSENTEE_DISCOUNT_MONEY,S.LEAVE_DISCOUNT_MONEY,S.WEEKEND_OVERTIME_MONEY,S.EIGHT_HOUR_OVERTIME_MONEY," +
                "                S.HOLIDAY_OVERTIME_MONEY,S.REAL_SALARY," +
                "                S.SOCIAL_SECURITY_LEVEL socialSecurityLevel,S.ELDERLY_CARE_DISCOUNT_MONEY elderlyCareDiscountMoney,S.MEDICAL_DISCOUNT_MONEY medicalDiscountMoney,S.UNEMPLOYMENT_DISCOUNT_MONEY unemploymentDiscountMoney " +
                "                FROM DT_SALARY_SETTLEMENT_MONTH S,DT_HR_STAFF ST " +
                "                WHERE S.STAFF_ID = ST.STAFFID " +
                "                AND S.COMPANY_ID = ? " +
                "                AND S.STAFF_ID IN (?) " +
                "                AND SUBSTR(S.SETTLEMENT_MONTH,0,INSTR(S.SETTLEMENT_MONTH,'-') - 1) = ? " +
                "                ORDER BY S.SETTLEMENT_MONTH";
    }

    @Override
    public String findSalaryStaffSettlementList(PageForm pageForm,String settlementTime) throws Exception {
        pageForm = getSalaryList(pageForm,settlementTime);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    public PageForm getSalaryList(PageForm pageForm,String settlementTime) {
        if(Objects.isNull(pageForm) || Objects.isNull(settlementTime)){
            return pageForm;
        }
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getStaffID()) && StringUtils.isNotBlank(account.getStaffID()))
            {
                paramList.add(account.getCompanyID());
            }else {
                return pageForm;
            }
        }else {
            return pageForm;
        }
        paramList.add(settlementTime);
        String sql = getDataSql();
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                        + sql.substring(sql.indexOf("FROM")),paramList.toArray());
        return pageForm;
    }

    private String getDataSql() {
        return "SELECT DISTINCT S.ROLE_SALARY,S.STAFF_ID,ST.STAFFNAME,S.COMPANY_ID,S.SETTLEMENT_MONTH,S.SALARY_LEVEL_NUM,S.BASE_SALARY," +
                "                       S.GUARANTEE_SALARY,S.WELFARE_SALARY,S.TOTAL_SALARY,S.LATE_DISCOUNT_MONEY,S.LEAVE_EARLY_DISCOUNT_MONEY," +
                "                       S.ABSENTEE_DISCOUNT_MONEY,S.LEAVE_DISCOUNT_MONEY,S.WEEKEND_OVERTIME_MONEY,S.EIGHT_HOUR_OVERTIME_MONEY," +
                "                       S.HOLIDAY_OVERTIME_MONEY,S.REAL_SALARY," +
                "                       S.SOCIAL_SECURITY_LEVEL socialSecurityLevel,S.ELDERLY_CARE_DISCOUNT_MONEY elderlyCareDiscountMoney,S.MEDICAL_DISCOUNT_MONEY medicalDiscountMoney,S.UNEMPLOYMENT_DISCOUNT_MONEY unemploymentDiscountMoney " +
                "                                FROM DT_SALARY_SETTLEMENT_MONTH S,DT_HR_STAFF ST " +
                "                                WHERE S.STAFF_ID = ST.STAFFID " +
                "                                AND S.COMPANY_ID = ? " +
                "                                AND S.SETTLEMENT_MONTH = ? " +
                "                                ORDER BY ST.STAFFNAME,S.SETTLEMENT_MONTH";
    }

    @Override
    public String findSalarySettlementAddList(PageForm pageForm,String settlementTime,String staffId) throws Exception {
        pageForm = getAddList(pageForm,settlementTime,staffId);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    private PageForm getAddList(PageForm pageForm,String settlementTime,String staffId) {
        if(Objects.isNull(pageForm) || Objects.isNull(settlementTime)){
            return pageForm;
        }
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getStaffID()) && StringUtils.isNotBlank(account.getStaffID()))
            {
                paramList.add(account.getCompanyID());
                if(StringUtils.isNotEmpty(staffId)){
                    paramList.add(staffId);
                }else{
                    paramList.add(account.getStaffID());
                }
            }else {
                return pageForm;
            }
        }else {
            return pageForm;
        }
        paramList.add(settlementTime);
        String currentYear = dateFormat.format(new Date()).split("-")[0];
        if(settlementTime.equals(currentYear)){
            paramList.add(account.getCompanyID());
            if(StringUtils.isNotEmpty(staffId)){
                paramList.add(staffId);
            }else{
                paramList.add(account.getStaffID());
            }
            paramList.add(settlementTime);
        }
        String sql = getDataUseAddSql(settlementTime,currentYear);
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 12), sql,"select count(1) "
                        + sql.substring(sql.indexOf("FROM")),paramList.toArray());
        return pageForm;
    }

    private String getDataUseAddSql(String year,String currentYear) {
        String unionSql = "";
        String sqlStart = "";
        String sqlEnd = "";
        if(currentYear.equals(year)){
            sqlStart = "SELECT * FROM (";
            sqlEnd = ") C";
            unionSql = " UNION " +
                    "SELECT * FROM (" +
                    "SELECT DISTINCT S.ROLE_SALARY,S.STAFF_ID,ST.STAFFNAME,S.COMPANY_ID,S.SETTLEMENT_MONTH,S.SALARY_LEVEL_NUM,S.BASE_SALARY," +
                    "       S.GUARANTEE_SALARY,S.WELFARE_SALARY,S.TOTAL_SALARY,S.SIGNIN_DAY_COUNT,S.SIGNIN_SALARY," +
                    "       S.LEAVE_ADD_MONEY,S.WEEKEND_OVERTIME_MONEY,S.EIGHT_HOUR_OVERTIME_MONEY," +
                    "                S.HOLIDAY_OVERTIME_MONEY,S.REAL_SALARY," +
                    "                S.SOCIAL_SECURITY_LEVEL socialSecurityLevel,S.ELDERLY_CARE_DISCOUNT_MONEY elderlyCareDiscountMoney,S.MEDICAL_DISCOUNT_MONEY medicalDiscountMoney,S.UNEMPLOYMENT_DISCOUNT_MONEY unemploymentDiscountMoney," +
                    "                S.SIGNIN_DAY_TOTAL_COUNT signInDayTotalCount,S.LATE_LEAVEEARLY_DAY_COUNT lateLeaveEarlyDayCount,S.LEAVE_ADD_DAY_COUNT leaveAddDayCount," +
                    "                S.WEEKEND_OVERTIME_DAY_COUNT weekendOvertimeDayCount,S.EIGHT_HOUR_OVERTIME_DAY_COUNT eightHourOvertimeDayCount,S.HOLIDAY_OVERTIME_DAY_COUNT holidayOvertimeDayCount " +
                    "                FROM DT_SALARY_CUR_MONTH_ADD S,DT_HR_STAFF ST " +
                    "                WHERE S.STAFF_ID = ST.STAFFID " +
                    "                AND S.COMPANY_ID = ? " +
                    "                AND S.STAFF_ID IN (?) " +
                    "                AND SUBSTR(S.SETTLEMENT_MONTH,0,INSTR(S.SETTLEMENT_MONTH,'-') - 1) = ? " +
                    "                ORDER BY S.SETTLEMENT_MONTH) B";
        }
        String sql = "SELECT * FROM ( " +
                "SELECT DISTINCT S.ROLE_SALARY,S.STAFF_ID,ST.STAFFNAME,S.COMPANY_ID,S.SETTLEMENT_MONTH,S.SALARY_LEVEL_NUM,S.BASE_SALARY," +
                "       S.GUARANTEE_SALARY,S.WELFARE_SALARY,S.TOTAL_SALARY,S.SIGNIN_DAY_COUNT,S.SIGNIN_SALARY," +
                "       S.LEAVE_ADD_MONEY,S.WEEKEND_OVERTIME_MONEY,S.EIGHT_HOUR_OVERTIME_MONEY," +
                "                S.HOLIDAY_OVERTIME_MONEY,S.REAL_SALARY," +
                "                S.SOCIAL_SECURITY_LEVEL socialSecurityLevel,S.ELDERLY_CARE_DISCOUNT_MONEY elderlyCareDiscountMoney,S.MEDICAL_DISCOUNT_MONEY medicalDiscountMoney,S.UNEMPLOYMENT_DISCOUNT_MONEY unemploymentDiscountMoney," +
                "                S.SIGNIN_DAY_TOTAL_COUNT signInDayTotalCount,S.LATE_LEAVEEARLY_DAY_COUNT lateLeaveEarlyDayCount,S.LEAVE_ADD_DAY_COUNT leaveAddDayCount," +
                "                S.WEEKEND_OVERTIME_DAY_COUNT weekendOvertimeDayCount,S.EIGHT_HOUR_OVERTIME_DAY_COUNT eightHourOvertimeDayCount,S.HOLIDAY_OVERTIME_DAY_COUNT holidayOvertimeDayCount " +
                "                FROM DT_SALARY_MONTH_ADD S,DT_HR_STAFF ST " +
                "                WHERE S.STAFF_ID = ST.STAFFID " +
                "                AND S.COMPANY_ID = ? " +
                "                AND S.STAFF_ID IN (?) " +
                "                AND SUBSTR(S.SETTLEMENT_MONTH,0,INSTR(S.SETTLEMENT_MONTH,'-') - 1) = ? " +
                "                ORDER BY S.SETTLEMENT_MONTH) A";
        return sqlStart + sql + unionSql + sqlEnd;
    }

    @Override
    public String findSalaryStaffSettlementAddList(PageForm pageForm,String settlementTime) throws Exception {
        pageForm = getSalaryAddList(pageForm,settlementTime);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    public PageForm getSalaryAddList(PageForm pageForm,String settlementTime) {
        String employee = CURRENT;
        if(Objects.isNull(pageForm) || Objects.isNull(settlementTime)){
            return pageForm;
        }
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getStaffID()) && StringUtils.isNotBlank(account.getStaffID()))
            {
                String sql = getDataAddSql(settlementTime);
                paramList.add(account.getCompanyID());
                //如果是授权角色则可以查对应公司全部员工的考勤数据
                if(signInCheckOnService.checkRole(account.getStaffID(),account.getCompanyID())){
                    employee = ALL;
                }else{
                    employee = CURRENT;
                }
                if(!ALL.equals(employee)){
                    paramList.add(account.getStaffID());
                }else{
                    sql = sql.replace("AND S.STAFF_ID = ?","");
                }
                paramList.add(settlementTime);
                pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
                        (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                                + sql.substring(sql.indexOf("FROM")),paramList.toArray());
                return pageForm;
            }else {
                return pageForm;
            }
        }else {
            return pageForm;
        }
    }

    private String getDataAddSql(String settlementTime) {
        String table = "DT_SALARY_MONTH_ADD";
        String now = dateFormat.format(new Date());
        String currentYear = now.split("-")[0];
        String currentMonth = now.split("-")[1];
        if(settlementTime.equals(currentYear +"-"+ currentMonth)){
            table = "DT_SALARY_CUR_MONTH_ADD";
        }
        return "SELECT DISTINCT S.ROLE_SALARY,S.STAFF_ID,ST.STAFFNAME,S.COMPANY_ID,S.SETTLEMENT_MONTH,S.SALARY_LEVEL_NUM,S.BASE_SALARY," +
                "                       S.GUARANTEE_SALARY,S.WELFARE_SALARY,S.TOTAL_SALARY,S.SIGNIN_DAY_COUNT,S.SIGNIN_SALARY," +
                "                       S.LEAVE_ADD_MONEY,S.WEEKEND_OVERTIME_MONEY,S.EIGHT_HOUR_OVERTIME_MONEY," +
                "                       S.HOLIDAY_OVERTIME_MONEY,S.REAL_SALARY," +
                "                       S.SOCIAL_SECURITY_LEVEL socialSecurityLevel,S.ELDERLY_CARE_DISCOUNT_MONEY elderlyCareDiscountMoney,S.MEDICAL_DISCOUNT_MONEY medicalDiscountMoney,S.UNEMPLOYMENT_DISCOUNT_MONEY unemploymentDiscountMoney," +
                "                       S.SIGNIN_DAY_TOTAL_COUNT signInDayTotalCount,S.LATE_LEAVEEARLY_DAY_COUNT lateLeaveEarlyDayCount,S.LEAVE_ADD_DAY_COUNT leaveAddDayCount," +
                "                       S.WEEKEND_OVERTIME_DAY_COUNT weekendOvertimeDayCount,S.EIGHT_HOUR_OVERTIME_DAY_COUNT eightHourOvertimeDayCount,S.HOLIDAY_OVERTIME_DAY_COUNT holidayOvertimeDayCount " +
                "                                FROM " + table + " S,DT_HR_STAFF ST " +
                "                                WHERE S.STAFF_ID = ST.STAFFID " +
                "                                AND S.COMPANY_ID = ? " +
                "                                AND S.STAFF_ID = ? " +
                "                                AND S.SETTLEMENT_MONTH = ? " +
                "                                ORDER BY ST.STAFFNAME,S.SETTLEMENT_MONTH";
    }

    public String findSalaryStaffTime(String settlementTimeType) {
        if(Objects.isNull(settlementTimeType)){
            return null;
        }
        String [] timeRange = signInCheckOnService.timeTypeToTimeRange(settlementTimeType);
        String startDate = timeRange[0];
        String endDate = signInCheckOnService.beforeDay(timeRange[1]);
        if(CURRENT_WEEK.equals(settlementTimeType)){
            endDate = signInCheckOnService.beforeDay(endDate);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("salaryStaffTime", startDate + " - " + endDate);
        JSONObject obj = JSONObject.fromObject(resultMap);
        return obj.toString();
    }

    @Override
    public String findSalaryStaffTimeList(PageForm pageForm,String settlementTimeType) throws Exception {
        if(YESTERDAY.equals(settlementTimeType) || CURRENT_WEEK.equals(settlementTimeType) || BEFORE_WEEK.equals(settlementTimeType)){
            pageForm = getSalaryAddTimeNotMonthList(pageForm,settlementTimeType);
        }else{
            pageForm = getSalaryAddTimeList(pageForm,settlementTimeType);
        }
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    private PageForm getSalaryAddTimeNotMonthList(PageForm pageForm,String settlementTimeType) throws Exception{
        String employee = CURRENT;
        if(Objects.isNull(pageForm) || Objects.isNull(settlementTimeType)){
            return pageForm;
        }
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getStaffID()) && StringUtils.isNotBlank(account.getStaffID()))
            {
                //如果是授权角色则可以查对应公司全部员工的考勤数据
                if(signInCheckOnService.checkRole(account.getStaffID(),account.getCompanyID())){
                    employee = ALL;
                }else{
                    employee = CURRENT;
                }
                String [] timeRange = signInCheckOnService.timeTypeToTimeRange(settlementTimeType);
                if(CURRENT_WEEK.equals(settlementTimeType)){
                    timeRange[1] = signInCheckOnService.beforeDay(timeRange[1]);
                }
                List<BaseBean> salaryMonthAddList = new ArrayList<>();
                if(!ALL.equals(employee)){
                    salaryMonthAddList = findEmployeeSalaryAddByTime(timeRange[0],timeRange[1],account.getCompanyID(),account.getStaffID());
                }else{
                    salaryMonthAddList = findEmployeeSalaryAddByTime(timeRange[0],timeRange[1],account.getCompanyID(),null);
                }
                pageForm.setList(salaryMonthAddList);
                pageForm.setPageNumber(1);
                pageForm.setPageSize(1000);
                pageForm.setRecordCount(salaryMonthAddList.size());
                return pageForm;
            }else {
                return pageForm;
            }
        }else {
            return pageForm;
        }
    }

    private PageForm getSalaryAddTimeList(PageForm pageForm,String settlementTimeType) {
        String employee = CURRENT;
        if(Objects.isNull(pageForm) || Objects.isNull(settlementTimeType)){
            return pageForm;
        }
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getStaffID()) && StringUtils.isNotBlank(account.getStaffID()))
            {
                if(Objects.isNull(settlementTimeType)){
                    settlementTimeType = CURRENT_MONTH;
                }
                String [] timeRange = signInCheckOnService.timeTypeToTimeRange(settlementTimeType);
                String sql = getDataAddTimeSql(timeRange);
                paramList.add(account.getCompanyID());
                //如果是授权角色则可以查对应公司全部员工的考勤数据
                if(signInCheckOnService.checkRole(account.getStaffID(),account.getCompanyID())){
                    employee = ALL;
                }else{
                    employee = CURRENT;
                }
                if(!ALL.equals(employee)){
                    paramList.add(account.getStaffID());
                }else{
                    sql = sql.replace("AND S.STAFF_ID = ?","");
                }

                if(CURRENT_MONTH.equals(settlementTimeType) || BEFORE_MONTH.equals(settlementTimeType)){
                    String month = timeRange[0].substring(0,7);
                    paramList.add(month);
                }else if(CURRENT_YEAR.equals(settlementTimeType) || BEFORE_YEAR.equals(settlementTimeType)){
                    String year = timeRange[0].substring(0,4);
                    paramList.add(year);
                    sql = sql.replace("AND S.SETTLEMENT_MONTH = ?",
                            "AND SUBSTR(S.SETTLEMENT_MONTH,0,INSTR(S.SETTLEMENT_MONTH,'-') - 1) = ?");
                }else if(CURRENT_QUARTER.equals(settlementTimeType) || BEFORE_QUARTER.equals(settlementTimeType)){
                    paramList.add(timeRange[0].substring(0,7));
                    paramList.add(timeRange[1].substring(0,7));
                    sql = sql.replace("AND S.SETTLEMENT_MONTH = ?",
                            "AND S.SETTLEMENT_MONTH >= ? AND S.SETTLEMENT_MONTH < ?");
                }
                pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
                        (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) FROM ("
                                + sql + ") B",paramList.toArray());
                return pageForm;
            }else {
                return pageForm;
            }
        }else {
            return pageForm;
        }
    }

    private String getDataAddTimeSql(String [] timeRange) {
        return "SELECT MIN(ROLE_SALARY),STAFF_ID,MIN(STAFFNAME),MIN(COMPANY_ID),MIN(SETTLEMENT_MONTH),MIN(SALARY_LEVEL_NUM),MIN(BASE_SALARY)," +
                "                                       MIN(GUARANTEE_SALARY),MIN(WELFARE_SALARY),MIN(TOTAL_SALARY),SUM(SIGNIN_DAY_COUNT),SUM(SIGNIN_SALARY)," +
                "                                       SUM(LEAVE_ADD_MONEY),SUM(WEEKEND_OVERTIME_MONEY),SUM(EIGHT_HOUR_OVERTIME_MONEY)," +
                "                                       SUM(HOLIDAY_OVERTIME_MONEY),SUM(REAL_SALARY)," +
                "                                       MIN(socialSecurityLevel) socialSecurityLevel,SUM(elderlyCareDiscountMoney) elderlyCareDiscountMoney,SUM(medicalDiscountMoney) medicalDiscountMoney,SUM(unemploymentDiscountMoney) unemploymentDiscountMoney," +
                "                                       SUM(signInDayTotalCount) signInDayTotalCount,SUM(lateLeaveEarlyDayCount) lateLeaveEarlyDayCount,SUM(leaveAddDayCount) leaveAddDayCount," +
                "                                       SUM(weekendOvertimeDayCount) weekendOvertimeDayCount,SUM(eightHourOvertimeDayCount) eightHourOvertimeDayCount,SUM(holidayOvertimeDayCount) holidayOvertimeDayCount " +
                "                                       FROM (" +
                "SELECT DISTINCT S.ROLE_SALARY,S.STAFF_ID,ST.STAFFNAME,S.COMPANY_ID,S.SETTLEMENT_MONTH,S.SALARY_LEVEL_NUM,S.BASE_SALARY," +
                "                                       S.GUARANTEE_SALARY,S.WELFARE_SALARY,S.TOTAL_SALARY,S.SIGNIN_DAY_COUNT,S.SIGNIN_SALARY," +
                "                                       S.LEAVE_ADD_MONEY,S.WEEKEND_OVERTIME_MONEY,S.EIGHT_HOUR_OVERTIME_MONEY," +
                "                                       S.HOLIDAY_OVERTIME_MONEY,S.REAL_SALARY," +
                "                                       S.SOCIAL_SECURITY_LEVEL socialSecurityLevel,S.ELDERLY_CARE_DISCOUNT_MONEY elderlyCareDiscountMoney,S.MEDICAL_DISCOUNT_MONEY medicalDiscountMoney,S.UNEMPLOYMENT_DISCOUNT_MONEY unemploymentDiscountMoney," +
                "                                       S.SIGNIN_DAY_TOTAL_COUNT signInDayTotalCount,S.LATE_LEAVEEARLY_DAY_COUNT lateLeaveEarlyDayCount,S.LEAVE_ADD_DAY_COUNT leaveAddDayCount," +
                "                                       S.WEEKEND_OVERTIME_DAY_COUNT weekendOvertimeDayCount,S.EIGHT_HOUR_OVERTIME_DAY_COUNT eightHourOvertimeDayCount,S.HOLIDAY_OVERTIME_DAY_COUNT holidayOvertimeDayCount " +
                "                                                FROM (" +
                "                                       SELECT * FROM DT_SALARY_MONTH_ADD " +
                "                                       UNION " +
                "                                       SELECT * FROM DT_SALARY_CUR_MONTH_ADD" +
                "                                                ) S,DT_HR_STAFF ST " +
                "                                                WHERE S.STAFF_ID = ST.STAFFID " +
                "                                                AND S.COMPANY_ID = ? " +
                "                                                AND S.STAFF_ID = ?  " +
                "                                                AND S.SETTLEMENT_MONTH = ? " +
                "                                                ORDER BY ST.STAFFNAME,S.SETTLEMENT_MONTH) A " +
                "                                                GROUP BY A.STAFF_ID";
    }


    public String findSalarySettlementDayList(PageForm pageForm,String settlementTime,String settlementTimeType, String companyId,String staffId) throws Exception {
        pageForm = getDayList(pageForm,settlementTime,settlementTimeType,companyId,staffId);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    private PageForm getDayList(PageForm pageForm,String settlementTime,String settlementTimeType,String companyId,String staffId) throws Exception {
        if(Objects.isNull(pageForm) || Objects.isNull(settlementTime) || Objects.isNull(companyId) || Objects.isNull(staffId)){
            return pageForm;
        }
        List<SalaryDay> salaryDayList = new ArrayList<>();
        if(settlementTime.length() <= 7){
            String year = settlementTime.split("-")[0];
            String month = settlementTime.split("-")[1];
            salaryDayList = findSalaryDayListByMonth(year,month,companyId,staffId);
        }else{
            String year1 = settlementTime.split("-")[0];
            String month1 = settlementTime.split("-")[1];
            String day1 = settlementTime.split("-")[2].trim();
            String year2 = settlementTime.split("-")[3].trim();
            String month2 = settlementTime.split("-")[4];
            String day2 = settlementTime.split("-")[5];
            String endDate = signInCheckOnService.selectNextDate(year2 + "-" + month2 + "-" + day2);
            if(YESTERDAY.equals(settlementTimeType) || CURRENT_WEEK.equals(settlementTimeType) || BEFORE_WEEK.equals(settlementTimeType)){
                salaryDayList = findSalaryDayListByDate(year1 + "-" + month1 + "-" + day1,endDate,companyId,staffId);
            }else{
                if(CURRENT_MONTH.equals(settlementTimeType) || BEFORE_MONTH.equals(settlementTimeType)){
                    salaryDayList = findSalaryDayListByMonth(year1,month1,companyId,staffId);
                }else if(CURRENT_QUARTER.equals(settlementTimeType) || BEFORE_QUARTER.equals(settlementTimeType)){
                    for(int i = 0;i < 3;i++){
                        String m = month1;
                        int month = Integer.valueOf(month1) + i;
                        if(month < 10){
                            m = "0" + month;
                        }else{
                            m = "" + month;
                        }
                        salaryDayList.addAll(findSalaryDayListByMonth(year1,m,companyId,staffId));
                    }
                }else if(CURRENT_YEAR.equals(settlementTimeType) || BEFORE_YEAR.equals(settlementTimeType)){
                    for(int i = 0;i < 12;i++){
                        String m = month1;
                        int month = Integer.valueOf(month1) + i;
                        if(month < 10){
                            m = "0" + month;
                        }else{
                            m = "" + month;
                        }
                        salaryDayList.addAll(findSalaryDayListByMonth(year1,m,companyId,staffId));
                    }
                }
            }
        }
        List<BaseBean> baseBeanList = new ArrayList<>();
        for(SalaryDay salaryDay : salaryDayList){
            baseBeanList.add(salaryDay);
        }
        pageForm.setList(baseBeanList);
        pageForm.setPageNumber(1);
        pageForm.setPageSize(1000);
        pageForm.setRecordCount(baseBeanList.size());
        return pageForm;
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

    /**
     * 总工资扣减的方式不用了
     * @param year
     * @param month
     * @throws Exception
     */
    @Override
    public void saveEmployeeSalary(String year,String month) throws Exception{
//        List<String> companyIdList = new ArrayList<>();
//        List<BaseBean> baseBeanlist = findCompanyIdList();
//        if(Objects.nonNull(baseBeanlist) && !baseBeanlist.isEmpty()){
//            for (Object obj : baseBeanlist) {
//                String companyId = obj.toString();
//                companyIdList.add(companyId);
//            }
//        }
//        for(String companyId:companyIdList){
//            //社保数据
//            SocialSecuritySetup socialSecuritySetup = findSocialSecuritySetupByCompanyId(companyId);
//            //员工工资数据
//            Map<String, SalaryData> salaryDataMap = new HashMap<>();
//            //员工考勤数据
//            List<SignInCheckOn> signInCheckOnList = signInCheckOnService.findCheckOnListByMonth(year,month,salaryDataMap,companyId);
//            if(salaryDataMap.size()>0){
//                List<BaseBean> salarySettlementMonthList = new ArrayList<>();
//                for(Map.Entry<String, SalaryData> salaryDataEntry : salaryDataMap.entrySet() ){
//                    String staffId = salaryDataEntry.getKey();
//                    SalaryData salaryData = salaryDataEntry.getValue();
//                    for(SignInCheckOn signInCheckOn : signInCheckOnList){
//                        if(signInCheckOn.getStaffId().equals(staffId)){
//                            SalarySettlementMonth salarySettlementMonth = new SalarySettlementMonth();
//                            salarySettlementMonth.setStaffId(staffId);
//                            salarySettlementMonth.setSalaryLevelId(salaryData.getSalaryLevelId());
//                            salarySettlementMonth.setSalaryLevelNum(salaryData.getSalaryLevelNum());
//                            salarySettlementMonth.setSettlementMonth(year + "-" + month);
//                            salarySettlementMonth.setCompanyId(signInCheckOn.getCompanyId());
//                            salarySettlementMonth.setBaseSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getBaseSalary()) ? salaryData.getBaseSalary() : "0")));
//                            salarySettlementMonth.setRoleSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getRoleSalary()) ? salaryData.getRoleSalary() : "0")));
//                            salarySettlementMonth.setDutySalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getDutySalary()) ? salaryData.getDutySalary() : "0")));
//                            salarySettlementMonth.setCompeteSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getCompeteSalary()) ? salaryData.getCompeteSalary() : "0")));
//                            salarySettlementMonth.setSecrecySalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getSecrecySalary()) ? salaryData.getSecrecySalary() : "0")));
//                            salarySettlementMonth.setLevelSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getLevelSalary()) ? salaryData.getLevelSalary() : "0")));
//                            salarySettlementMonth.setGuaranteeSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getGuaranteeSalary()) ? salaryData.getGuaranteeSalary() : "0")));
//                            salarySettlementMonth.setWelfareSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getWelfareSalary()) ? salaryData.getWelfareSalary() : "0")));
//                            salarySettlementMonth.setTotalSalary(BigDecimal.valueOf(Double.parseDouble(Objects.nonNull(salaryData.getGuaranteeSalary()) ? salaryData.getGuaranteeSalary() : "0"))
//                                    .add(BigDecimal.valueOf(Double.parseDouble(Objects.nonNull(salaryData.getWelfareSalary()) ? salaryData.getWelfareSalary() : "0"))).setScale(2, RoundingMode.HALF_UP).toString());
//
//                            //针对于中途入职或离职的员工在入职和离职当月的工资算法是，将入职前的节假日天数，离职后的节假日天数的工资从月总工资中扣减掉，入职前，
//                            //离职后的工作日是不需要扣减的，因为会认定为旷工而扣减
//                            //只用改月总工资即可，但需要记录入职，离职的时间 todo
//
//                            salarySettlementMonth.setLateDiscountMoney(signInCheckOn.getLateDiscountMoney().toString());
//                            salarySettlementMonth.setLeaveEarlyDiscountMoney(signInCheckOn.getLeaveEarlyDiscountMoney().toString());
//                            salarySettlementMonth.setAbsenteeDiscountMoney(signInCheckOn.getAbsenteeDiscountMoney().toString());
//                            salarySettlementMonth.setLeaveDiscountMoney(signInCheckOn.getLeaveAbsenceDiscountMoney()
//                                    .add(signInCheckOn.getLeaveAnnualDiscountMoney())
//                                    .add(signInCheckOn.getLeaveFuneralDiscountMoney())
//                                    .add(signInCheckOn.getLeaveMarriageDiscountMoney())
//                                    .add(signInCheckOn.getLeaveMaternityDiscountMoney())
//                                    .add(signInCheckOn.getLeaveRotationRestDiscountMoney())
//                                    .add(signInCheckOn.getLeaveSickDiscountMoney())
//                                    .add(signInCheckOn.getLeaveWorkInjuryDiscountMoney())
//                                    .setScale(2, RoundingMode.HALF_UP)
//                                    .toString());
//                            salarySettlementMonth.setWeekendOvertimeMoney(signInCheckOn.getWeekendOverTimeMoney().toString());
//                            salarySettlementMonth.setEightHourOvertimeMoney(signInCheckOn.getEightHourOverTimeMoney().toString());
//                            salarySettlementMonth.setHolidayOvertimeMoney(signInCheckOn.getHolidayOverTimeMoney().toString());
//                            //工资总额-迟到，早退，请假，旷工扣款+加班费
//                            salarySettlementMonth.setRealSalary(BigDecimal.valueOf(Double.parseDouble(salarySettlementMonth.getTotalSalary()))
//                                    .subtract(signInCheckOn.getLateDiscountMoney())
//                                    .subtract(signInCheckOn.getLeaveEarlyDiscountMoney())
//                                    .subtract(signInCheckOn.getAbsenteeDiscountMoney())
//                                    .subtract(BigDecimal.valueOf(Double.parseDouble(salarySettlementMonth.getLeaveDiscountMoney())))
//                                    .add(signInCheckOn.getWeekendOverTimeMoney())
//                                    .add(signInCheckOn.getEightHourOverTimeMoney())
//                                    .add(signInCheckOn.getHolidayOverTimeMoney())
//                                    .setScale(2, RoundingMode.HALF_UP)
//                                    .toString());
//                            if(Double.parseDouble(salarySettlementMonth.getRealSalary()) < 0){
//                                salarySettlementMonth.setRealSalary("0.00");
//                            }
//
//                            if(Objects.nonNull(socialSecuritySetup)){
//                                //扣社保
//                                if(DEDUCTION_TYPE_UNIFIED.equals(socialSecuritySetup.getDeductionType())){
//                                    salarySettlementMonth.setSocialSecurityLevel(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getSocialSecurityLevel())).setScale(2, RoundingMode.HALF_UP).toString());
//                                    salarySettlementMonth.setElderlyCareDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());
//                                    salarySettlementMonth.setMedicalDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());
//                                    salarySettlementMonth.setUnemploymentDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());
//
//                                    //交了社保则扣社保
//                                    List<String> notSocialSecurity = Arrays.asList(NOT_SOCIAL_SECURITY);
//                                    if(!notSocialSecurity.contains(staffId)){
//                                        salarySettlementMonth.setRealSalary(
//                                                BigDecimal.valueOf(Double.parseDouble(salarySettlementMonth.getRealSalary()))
//                                                        .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareDiscountMoney())))
//                                                        .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentDiscountMoney())))
//                                                        .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalDiscountMoney())))
//                                                        .setScale(2, RoundingMode.HALF_UP)
//                                                        .toString()
//                                        );
//                                    }
//                                }else{
//                                    computeRealSalary(salarySettlementMonth,socialSecuritySetup);
//                                }
//                            }else{
//                                salarySettlementMonth.setSocialSecurityLevel("0.00");
//                                salarySettlementMonth.setElderlyCareDiscountMoney("0.00");
//                                salarySettlementMonth.setMedicalDiscountMoney("0.00");
//                                salarySettlementMonth.setUnemploymentDiscountMoney("0.00");
//                            }
//                            salarySettlementMonthList.add(salarySettlementMonth);
//                            break;
//                        }
//                    }
//                }
//                try {
//                    baseBeanService.saveBeansListAndexecuteSqlsByParams(salarySettlementMonthList,null,null);
//                }catch (Exception e){
//                    logger.error("操作异常", e);
//                }
//            }
//        }
    }

    /**
     * 查出社保配置
     * @param companyId
     * @return
     */
    private SocialSecuritySetup findSocialSecuritySetupByCompanyId(String companyId){
        return socialSecuritySetupService.findSocialSecuritySetupByCompanyId(companyId);
    }

    private void computeRealSalary(SalarySettlementMonth salarySettlementMonth,SocialSecuritySetup socialSecuritySetup){
        BigDecimal elderlyCareDiscountMoney = BigDecimal.ZERO;
        BigDecimal unemploymentDiscountMoney = BigDecimal.ZERO;
        BigDecimal medicalDiscountMoney = BigDecimal.ZERO;
        BigDecimal socialSecurityLevel = BigDecimal.ZERO;
        if(Y_VALUE.equals(socialSecuritySetup.getReferenceGuaranteeSalary())){
            socialSecurityLevel = socialSecurityLevel.add(BigDecimal.valueOf(Double.parseDouble(salarySettlementMonth.getGuaranteeSalary())));
        }
        if(Y_VALUE.equals(socialSecuritySetup.getReferenceWelfareSalary())){
            socialSecurityLevel = socialSecurityLevel.add(BigDecimal.valueOf(Double.parseDouble(salarySettlementMonth.getWelfareSalary())));
        }

        BigDecimal elderlyCareRate = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareRate())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal unemploymentRete = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentRete())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal medicalRate = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalRate())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal socialSecurityLowLevel = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getSocialSecurityLowLevel()));
        if(socialSecurityLevel.compareTo(socialSecurityLowLevel) < 0){
            socialSecurityLevel = socialSecurityLowLevel;
        }
        elderlyCareDiscountMoney = socialSecurityLevel.multiply(elderlyCareRate);
        unemploymentDiscountMoney = socialSecurityLevel.multiply(unemploymentRete);
        medicalDiscountMoney = socialSecurityLevel.multiply(medicalRate);

        salarySettlementMonth.setSocialSecurityLevel(socialSecurityLevel.setScale(2, RoundingMode.HALF_UP).toString());
        salarySettlementMonth.setElderlyCareDiscountMoney(elderlyCareDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());
        salarySettlementMonth.setUnemploymentDiscountMoney(unemploymentDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());
        salarySettlementMonth.setMedicalDiscountMoney(medicalDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());

        //交了社保则扣社保
        List<String> notSocialSecurity = Arrays.asList(NOT_SOCIAL_SECURITY);
        if(!notSocialSecurity.contains(salarySettlementMonth.getStaffId())){
            salarySettlementMonth.setRealSalary(
                    BigDecimal.valueOf(Double.parseDouble(salarySettlementMonth.getRealSalary()))
                            .subtract(elderlyCareDiscountMoney)
                            .subtract(unemploymentDiscountMoney)
                            .subtract(medicalDiscountMoney)
                            .setScale(2, RoundingMode.HALF_UP)
                            .toString()
            );
        }
    }

    private void computeRealSalaryAdd(SalaryMonthAdd salaryMonthAdd,SocialSecuritySetup socialSecuritySetup){
        BigDecimal elderlyCareDiscountMoney = BigDecimal.ZERO;
        BigDecimal unemploymentDiscountMoney = BigDecimal.ZERO;
        BigDecimal medicalDiscountMoney = BigDecimal.ZERO;
        BigDecimal socialSecurityLevel = BigDecimal.ZERO;
        if(Y_VALUE.equals(socialSecuritySetup.getReferenceGuaranteeSalary())){
            socialSecurityLevel = socialSecurityLevel.add(BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getGuaranteeSalary())));
        }
        if(Y_VALUE.equals(socialSecuritySetup.getReferenceWelfareSalary())){
            socialSecurityLevel = socialSecurityLevel.add(BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getWelfareSalary())));
        }

        BigDecimal elderlyCareRate = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareRate())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal unemploymentRete = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentRete())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal medicalRate = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalRate())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal socialSecurityLowLevel = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getSocialSecurityLowLevel()));
        if(socialSecurityLevel.compareTo(socialSecurityLowLevel) < 0){
            socialSecurityLevel = socialSecurityLowLevel;
        }
        elderlyCareDiscountMoney = socialSecurityLevel.multiply(elderlyCareRate);
        unemploymentDiscountMoney = socialSecurityLevel.multiply(unemploymentRete);
        medicalDiscountMoney = socialSecurityLevel.multiply(medicalRate);

        salaryMonthAdd.setSocialSecurityLevel(socialSecurityLevel.setScale(2, RoundingMode.HALF_UP).toString());
        salaryMonthAdd.setElderlyCareDiscountMoney(elderlyCareDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());
        salaryMonthAdd.setUnemploymentDiscountMoney(unemploymentDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());
        salaryMonthAdd.setMedicalDiscountMoney(medicalDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());

        //交了社保则扣社保
        List<String> notSocialSecurity = Arrays.asList(NOT_SOCIAL_SECURITY);
        if(!notSocialSecurity.contains(salaryMonthAdd.getStaffId())){
            salaryMonthAdd.setRealSalary(
                    BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getRealSalary()))
                            .subtract(elderlyCareDiscountMoney)
                            .subtract(unemploymentDiscountMoney)
                            .subtract(medicalDiscountMoney)
                            .setScale(2, RoundingMode.HALF_UP)
                            .toString()
            );
        }
    }

    private void computeRealSalaryCurAdd(SalaryCurMonthAdd salaryMonthAdd,SocialSecuritySetup socialSecuritySetup,Integer dayCount){
        BigDecimal elderlyCareDiscountMoney = BigDecimal.ZERO;
        BigDecimal unemploymentDiscountMoney = BigDecimal.ZERO;
        BigDecimal medicalDiscountMoney = BigDecimal.ZERO;
        BigDecimal socialSecurityLevel = BigDecimal.ZERO;
        if(Y_VALUE.equals(socialSecuritySetup.getReferenceGuaranteeSalary())){
            socialSecurityLevel = socialSecurityLevel.add(BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getGuaranteeSalary())));
        }
        if(Y_VALUE.equals(socialSecuritySetup.getReferenceWelfareSalary())){
            socialSecurityLevel = socialSecurityLevel.add(BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getWelfareSalary())));
        }

        BigDecimal elderlyCareRate = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareRate())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal unemploymentRete = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentRete())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal medicalRate = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalRate())).divide(BigDecimal.valueOf(100D),8,RoundingMode.HALF_UP);
        BigDecimal socialSecurityLowLevel = BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getSocialSecurityLowLevel()));
        if(socialSecurityLevel.compareTo(socialSecurityLowLevel) < 0){
            socialSecurityLevel = socialSecurityLowLevel;
        }
        elderlyCareDiscountMoney = socialSecurityLevel.multiply(elderlyCareRate);
        unemploymentDiscountMoney = socialSecurityLevel.multiply(unemploymentRete);
        medicalDiscountMoney = socialSecurityLevel.multiply(medicalRate);

        if(Objects.nonNull(dayCount)){
            elderlyCareDiscountMoney = socialSecurityLevel.multiply(elderlyCareRate).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount));
            unemploymentDiscountMoney = socialSecurityLevel.multiply(unemploymentRete).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount));
            medicalDiscountMoney = socialSecurityLevel.multiply(medicalRate).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount));
        }
        salaryMonthAdd.setSocialSecurityLevel(socialSecurityLevel.setScale(2, RoundingMode.HALF_UP).toString());
        salaryMonthAdd.setElderlyCareDiscountMoney(elderlyCareDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());
        salaryMonthAdd.setUnemploymentDiscountMoney(unemploymentDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());
        salaryMonthAdd.setMedicalDiscountMoney(medicalDiscountMoney.setScale(2, RoundingMode.HALF_UP).toString());

        //交了社保则扣社保
        List<String> notSocialSecurity = Arrays.asList(NOT_SOCIAL_SECURITY);
        if(!notSocialSecurity.contains(salaryMonthAdd.getStaffId())){
            salaryMonthAdd.setRealSalary(
                    BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getRealSalary()))
                            .subtract(elderlyCareDiscountMoney)
                            .subtract(unemploymentDiscountMoney)
                            .subtract(medicalDiscountMoney)
                            .setScale(2, RoundingMode.HALF_UP)
                            .toString()
            );
        }
    }

    /**
     * 查询有效的companyId
     * @return
     */
    private List findCompanyIdList(){
        StringBuilder sb = new StringBuilder("SELECT C.COMPANYID " +
                "FROM DTCOS C,DT_HR_STAFF ST,DT_WFJ_SIGN S " +
                "WHERE ST.STAFFID = C.STAFFID AND S.account = ST.reference " +
                "AND S.signdate >= trunc(CURRENT_DATE - 365) " +
                "AND C.COSSTATUS = '50' " +
                "AND C.COMPANYID IS NOT NULL " +
                "GROUP BY C.COMPANYID " +
                "ORDER BY C.COMPANYID");
        return baseBeanService.getListBeanBySqlAndParams(sb.toString(), new Object[]{});
    }

    public String findStaffById(String staffId) {
        Staff staff =  (Staff) baseBeanService
                .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("staff", staff);
        JSONObject obj = JSONObject.fromObject(map);
        return obj.toString();
    }

    /**
     * 累加方式放计算工资
     * @param year
     * @param month
     * @throws Exception
     */
    @Override
    public void saveEmployeeSalaryAdd(String year,String month,String[] comIds) throws Exception{
        List<String> companyIdList = new ArrayList<>();
        List<BaseBean> baseBeanlist = findCompanyIdList();
        if(Objects.nonNull(baseBeanlist) && !baseBeanlist.isEmpty()){
            for (Object obj : baseBeanlist) {
                String companyId = obj.toString();
                companyIdList.add(companyId);
            }
        }
        for(String companyId:companyIdList){
            Boolean isRun = true;
            if(Objects.nonNull(comIds)){
                if(!Arrays.asList(comIds).contains(companyId)){
                    isRun = false;
                }
            }
            if(isRun){
                double addDay = signInDayAdd(year,month,companyId);
                //社保数据
                SocialSecuritySetup socialSecuritySetup = findSocialSecuritySetupByCompanyId(companyId);
                //员工工资数据
                Map<String, SalaryData> salaryDataMap = new HashMap<>();
                //员工考勤数据
                List<SignInCheckOn> signInCheckOnList = signInCheckOnService.findCheckOnListByMonth(year,month,null,null,salaryDataMap,companyId);
                if(salaryDataMap.size()>0){
                    List<BaseBean> salaryMonthAddList = new ArrayList<>();
                    for(Map.Entry<String, SalaryData> salaryDataEntry : salaryDataMap.entrySet() ){
                        String staffId = salaryDataEntry.getKey();
                        SalaryData salaryData = salaryDataEntry.getValue();
                        for(SignInCheckOn signInCheckOn : signInCheckOnList){
                            if(signInCheckOn.getStaffId().equals(staffId)){
                                SalaryMonthAdd salaryMonthAdd = new SalaryMonthAdd();
                                salaryMonthAdd.setStaffId(staffId);
                                salaryMonthAdd.setSalaryLevelId(salaryData.getSalaryLevelId());
                                salaryMonthAdd.setSalaryLevelNum(salaryData.getSalaryLevelNum());
                                salaryMonthAdd.setSettlementMonth(year + "-" + month);
                                salaryMonthAdd.setCompanyId(signInCheckOn.getCompanyId());
                                salaryMonthAdd.setBaseSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getBaseSalary()) ? salaryData.getBaseSalary() : "0")));
                                salaryMonthAdd.setRoleSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getRoleSalary()) ? salaryData.getRoleSalary() : "0")));
                                salaryMonthAdd.setDutySalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getDutySalary()) ? salaryData.getDutySalary() : "0")));
                                salaryMonthAdd.setCompeteSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getCompeteSalary()) ? salaryData.getCompeteSalary() : "0")));
                                salaryMonthAdd.setSecrecySalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getSecrecySalary()) ? salaryData.getSecrecySalary() : "0")));
                                salaryMonthAdd.setLevelSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getLevelSalary()) ? salaryData.getLevelSalary() : "0")));
                                salaryMonthAdd.setGuaranteeSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getGuaranteeSalary()) ? salaryData.getGuaranteeSalary() : "0")));
                                salaryMonthAdd.setWelfareSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getWelfareSalary()) ? salaryData.getWelfareSalary() : "0")));
                                salaryMonthAdd.setTotalSalary(BigDecimal.valueOf(Double.parseDouble(Objects.nonNull(salaryData.getGuaranteeSalary()) ? salaryData.getGuaranteeSalary() : "0"))
                                        .add(BigDecimal.valueOf(Double.parseDouble(Objects.nonNull(salaryData.getWelfareSalary()) ? salaryData.getWelfareSalary() : "0"))).setScale(2, RoundingMode.HALF_UP).toString());

                                Double signInDayCount = 0D;
                                Double signInDayTotalCount = 0D;
                                Double lateLeaveEarlyDayCount = 0D;
                                if(Objects.nonNull(signInCheckOn.getSignInResultList())){
                                    //排除周6，周天，节假日
                                    Iterator<SignInResult> iterator = signInCheckOn.getSignInResultList().iterator();
                                    while(iterator.hasNext()){
                                        SignInResult signInResult = iterator.next();
                                        if(overtimeApplyMobileService.isWeekendDay(signInResult.getSignInDate())
                                                || overtimeApplyMobileService.isHoliday(signInResult.getSignInDate())){
                                            iterator.remove();
                                        }
                                    }
                                    //签到总天数
                                    signInDayTotalCount = (double)signInCheckOn.getSignInResultList().size();
                                    signInDayCount = computeSignInDayCount(signInCheckOn.getSignInResultList());
                                    //迟到早退天数
                                    lateLeaveEarlyDayCount = signInDayTotalCount - signInDayCount;
                                }
                                //节假日天数
                                int holiDayCount = overtimeApplyMobileService.holidayDayCount(salaryMonthAdd.getSettlementMonth());
                                signInDayTotalCount += holiDayCount;
                                signInDayCount += holiDayCount;

                                salaryMonthAdd.setSignInDayCount(BigDecimal.valueOf(signInDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setSignInDayTotalCount(BigDecimal.valueOf(signInDayTotalCount).setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setLateLeaveEarlyDayCount(BigDecimal.valueOf(lateLeaveEarlyDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                                //每天考勤工资
                                signInCheckOn.setSalaryPerDay(BigDecimal.ZERO);
                                if(Double.valueOf(salaryMonthAdd.getTotalSalary()) > 0){
                                    signInCheckOn.setSalaryPerDay(new BigDecimal(salaryMonthAdd.getTotalSalary()).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP));
                                }
                                //每天保障工资
                                signInCheckOn.setGuaranteeSalaryPerDay(BigDecimal.ZERO);
                                if(Double.valueOf(salaryMonthAdd.getGuaranteeSalary()) > 0){
                                    signInCheckOn.setGuaranteeSalaryPerDay(new BigDecimal(salaryMonthAdd.getGuaranteeSalary()).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP));
                                }
                                //请假天数
                                Double leaveDayCount = 0D;
                                leaveDayCount += signInCheckOn.getLeaveSickDayCount();
                                leaveDayCount += signInCheckOn.getLeaveMarriageDayCount();
                                leaveDayCount += signInCheckOn.getLeaveMaternityDayCount();
                                leaveDayCount += signInCheckOn.getLeaveFuneralDayCount();
                                leaveDayCount += signInCheckOn.getLeaveWorkInjuryDayCount();

                                //年假天数
                                Double leaveAnnualDayCount = 0D;
                                leaveAnnualDayCount += signInCheckOn.getLeaveAnnualDayCount();
                                //轮岗休息天数
                                leaveAnnualDayCount += signInCheckOn.getLeaveRotationRestDayCount();

                                //请假有保障工资
                                BigDecimal guaranteeSalary = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                                if(leaveDayCount > 0){
                                    guaranteeSalary = signInCheckOn.getGuaranteeSalaryPerDay().multiply(BigDecimal.valueOf(leaveDayCount)).setScale(2, RoundingMode.HALF_UP);
                                }
                                //实际考勤总工资
                                BigDecimal totalSalary = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                                if(signInDayCount > 0){
                                    totalSalary = signInCheckOn.getSalaryPerDay().multiply(BigDecimal.valueOf(signInDayCount + addDay)).setScale(2, RoundingMode.HALF_UP);
                                }
                                //加年假
                                guaranteeSalary = guaranteeSalary.add(signInCheckOn.getSalaryPerDay().multiply(BigDecimal.valueOf(leaveAnnualDayCount)).setScale(2, RoundingMode.HALF_UP));
                                leaveDayCount += leaveAnnualDayCount;

                                salaryMonthAdd.setSignInSalary(totalSalary.toString());
                                salaryMonthAdd.setLeaveAddMoney(guaranteeSalary.toString());
                                salaryMonthAdd.setLeaveAddDayCount(BigDecimal.valueOf(leaveDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                                //加班工资
                                salaryMonthAdd.setWeekendOvertimeMoney(signInCheckOn.getWeekendOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setEightHourOvertimeMoney(signInCheckOn.getEightHourOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setHolidayOvertimeMoney(signInCheckOn.getHolidayOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setWeekendOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getWeekendOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setEightHourOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getEightHourOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setHolidayOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getHolidayOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());

                                //月打卡天数（不算周6，周天，节假日）*天工资+年假的总天数（申请时长折合成天数）*天工资+请假工资+加班费
                                salaryMonthAdd.setRealSalary(totalSalary
                                        .add(guaranteeSalary)
                                        .add(signInCheckOn.getWeekendOverTimeMoney())
                                        .add(signInCheckOn.getEightHourOverTimeMoney())
                                        .add(signInCheckOn.getHolidayOverTimeMoney())
                                        .setScale(2, RoundingMode.HALF_UP)
                                        .toString());

                                if(Double.parseDouble(salaryMonthAdd.getRealSalary()) < 0){
                                    salaryMonthAdd.setRealSalary("0.00");
                                }

                                if(Objects.nonNull(socialSecuritySetup)){
                                    //扣社保
                                    if(DEDUCTION_TYPE_UNIFIED.equals(socialSecuritySetup.getDeductionType())){
                                        salaryMonthAdd.setSocialSecurityLevel(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getSocialSecurityLevel())).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryMonthAdd.setElderlyCareDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryMonthAdd.setMedicalDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryMonthAdd.setUnemploymentDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());

                                        //交了社保则扣社保
                                        List<String> notSocialSecurity = Arrays.asList(NOT_SOCIAL_SECURITY);
                                        if(!notSocialSecurity.contains(staffId)){
                                            salaryMonthAdd.setRealSalary(
                                                    BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getRealSalary()))
                                                            .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareDiscountMoney())))
                                                            .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentDiscountMoney())))
                                                            .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalDiscountMoney())))
                                                            .setScale(2, RoundingMode.HALF_UP)
                                                            .toString()
                                            );
                                        }
                                    }else{
                                        computeRealSalaryAdd(salaryMonthAdd,socialSecuritySetup);
                                    }
                                }else{
                                    salaryMonthAdd.setSocialSecurityLevel("0.00");
                                    salaryMonthAdd.setElderlyCareDiscountMoney("0.00");
                                    salaryMonthAdd.setMedicalDiscountMoney("0.00");
                                    salaryMonthAdd.setUnemploymentDiscountMoney("0.00");
                                }
                                salaryMonthAddList.add(salaryMonthAdd);
                                break;
                            }
                        }
                    }
                    try {
                        baseBeanService.saveBeansListAndexecuteSqlsByParams(salaryMonthAddList,null,null);
                    }catch (Exception e){
                        logger.error("操作异常", e);
                    }
                }
            }
        }
    }

    @Override
    public void saveCurEmployeeSalaryAdd(String year,String month) throws Exception{
        List<String> companyIdList = new ArrayList<>();
        List<BaseBean> baseBeanlist = findCompanyIdList();
        if(Objects.nonNull(baseBeanlist) && !baseBeanlist.isEmpty()){
            for (Object obj : baseBeanlist) {
                String companyId = obj.toString();
                companyIdList.add(companyId);
            }
        }
        for(String companyId:companyIdList){
            double addDay = signInDayAdd(year,month,companyId);
            //社保数据
            SocialSecuritySetup socialSecuritySetup = findSocialSecuritySetupByCompanyId(companyId);
            //员工工资数据
            Map<String, SalaryData> salaryDataMap = new HashMap<>();
            //员工考勤数据
            List<SignInCheckOn> signInCheckOnList = signInCheckOnService.findCheckOnListByMonth(year,month,null,null,salaryDataMap,companyId);
            if(salaryDataMap.size()>0){
                List<BaseBean> salaryMonthAddList = new ArrayList<>();
                for(Map.Entry<String, SalaryData> salaryDataEntry : salaryDataMap.entrySet() ){
                    String staffId = salaryDataEntry.getKey();
                    SalaryData salaryData = salaryDataEntry.getValue();
                    for(SignInCheckOn signInCheckOn : signInCheckOnList){
                        if(signInCheckOn.getStaffId().equals(staffId)){
                            SalaryCurMonthAdd salaryMonthAdd = new SalaryCurMonthAdd();
                            salaryMonthAdd.setStaffId(staffId);
                            salaryMonthAdd.setSalaryLevelId(salaryData.getSalaryLevelId());
                            salaryMonthAdd.setSalaryLevelNum(salaryData.getSalaryLevelNum());
                            salaryMonthAdd.setSettlementMonth(year + "-" + month);
                            salaryMonthAdd.setCompanyId(signInCheckOn.getCompanyId());
                            salaryMonthAdd.setBaseSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getBaseSalary()) ? salaryData.getBaseSalary() : "0")));
                            salaryMonthAdd.setRoleSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getRoleSalary()) ? salaryData.getRoleSalary() : "0")));
                            salaryMonthAdd.setDutySalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getDutySalary()) ? salaryData.getDutySalary() : "0")));
                            salaryMonthAdd.setCompeteSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getCompeteSalary()) ? salaryData.getCompeteSalary() : "0")));
                            salaryMonthAdd.setSecrecySalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getSecrecySalary()) ? salaryData.getSecrecySalary() : "0")));
                            salaryMonthAdd.setLevelSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getLevelSalary()) ? salaryData.getLevelSalary() : "0")));
                            salaryMonthAdd.setGuaranteeSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getGuaranteeSalary()) ? salaryData.getGuaranteeSalary() : "0")));
                            salaryMonthAdd.setWelfareSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getWelfareSalary()) ? salaryData.getWelfareSalary() : "0")));
                            salaryMonthAdd.setTotalSalary(BigDecimal.valueOf(Double.parseDouble(Objects.nonNull(salaryData.getGuaranteeSalary()) ? salaryData.getGuaranteeSalary() : "0"))
                                    .add(BigDecimal.valueOf(Double.parseDouble(Objects.nonNull(salaryData.getWelfareSalary()) ? salaryData.getWelfareSalary() : "0"))).setScale(2, RoundingMode.HALF_UP).toString());

                            Double signInDayCount = 0D;
                            Double signInDayTotalCount = 0D;
                            Double lateLeaveEarlyDayCount = 0D;
                            if(Objects.nonNull(signInCheckOn.getSignInResultList())){
                                //排除周6，周天，节假日
                                Iterator<SignInResult> iterator = signInCheckOn.getSignInResultList().iterator();
                                while(iterator.hasNext()){
                                    SignInResult signInResult = iterator.next();
                                    if(overtimeApplyMobileService.isWeekendDay(signInResult.getSignInDate())
                                            || overtimeApplyMobileService.isHoliday(signInResult.getSignInDate())){
                                        iterator.remove();
                                    }
                                }
                                //签到总天数
                                signInDayTotalCount = (double)signInCheckOn.getSignInResultList().size();
                                signInDayCount = computeSignInDayCount(signInCheckOn.getSignInResultList());
                                //迟到早退天数
                                lateLeaveEarlyDayCount = signInDayTotalCount - signInDayCount;
                            }
                            //节假日天数
                            int holiDayCount = overtimeApplyMobileService.holidayDayCount(salaryMonthAdd.getSettlementMonth());
                            signInDayTotalCount += holiDayCount;
                            signInDayCount += holiDayCount;

                            salaryMonthAdd.setSignInDayCount(BigDecimal.valueOf(signInDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                            salaryMonthAdd.setSignInDayTotalCount(BigDecimal.valueOf(signInDayTotalCount).setScale(2, RoundingMode.HALF_UP).toString());
                            salaryMonthAdd.setLateLeaveEarlyDayCount(BigDecimal.valueOf(lateLeaveEarlyDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                            //每天考勤工资
                            signInCheckOn.setSalaryPerDay(BigDecimal.ZERO);
                            if(Double.valueOf(salaryMonthAdd.getTotalSalary()) > 0){
                                signInCheckOn.setSalaryPerDay(new BigDecimal(salaryMonthAdd.getTotalSalary()).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP));
                            }
                            //每天保障工资
                            signInCheckOn.setGuaranteeSalaryPerDay(BigDecimal.ZERO);
                            if(Double.valueOf(salaryMonthAdd.getGuaranteeSalary()) > 0){
                                signInCheckOn.setGuaranteeSalaryPerDay(new BigDecimal(salaryMonthAdd.getGuaranteeSalary()).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP));
                            }
                            //请假天数
                            double leaveDayCount = 0;
                            leaveDayCount += signInCheckOn.getLeaveSickDayCount();
                            leaveDayCount += signInCheckOn.getLeaveMarriageDayCount();
                            leaveDayCount += signInCheckOn.getLeaveMaternityDayCount();
                            leaveDayCount += signInCheckOn.getLeaveFuneralDayCount();
                            leaveDayCount += signInCheckOn.getLeaveWorkInjuryDayCount();

                            //年假天数
                            Double leaveAnnualDayCount = 0D;
                            leaveAnnualDayCount += signInCheckOn.getLeaveAnnualDayCount();
                            //轮岗休息天数
                            leaveAnnualDayCount += signInCheckOn.getLeaveRotationRestDayCount();

                            //请假保障工资
                            BigDecimal guaranteeSalary = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                            if(leaveDayCount > 0){
                                guaranteeSalary = signInCheckOn.getGuaranteeSalaryPerDay().multiply(BigDecimal.valueOf(leaveDayCount)).setScale(2, RoundingMode.HALF_UP);
                            }
                            //实际考勤总工资
                            BigDecimal totalSalary = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                            if(signInDayCount > 0){
                                totalSalary = signInCheckOn.getSalaryPerDay().multiply(BigDecimal.valueOf(signInDayCount + addDay)).setScale(2, RoundingMode.HALF_UP);
                            }
                            //加年假
                            guaranteeSalary = guaranteeSalary.add(signInCheckOn.getSalaryPerDay().multiply(BigDecimal.valueOf(leaveAnnualDayCount)).setScale(2, RoundingMode.HALF_UP));
                            leaveDayCount += leaveAnnualDayCount;

                            salaryMonthAdd.setSignInSalary(totalSalary.toString());
                            salaryMonthAdd.setLeaveAddMoney(guaranteeSalary.toString());
                            salaryMonthAdd.setLeaveAddDayCount(BigDecimal.valueOf(leaveDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                            //加班工资
                            salaryMonthAdd.setWeekendOvertimeMoney(signInCheckOn.getWeekendOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                            salaryMonthAdd.setEightHourOvertimeMoney(signInCheckOn.getEightHourOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                            salaryMonthAdd.setHolidayOvertimeMoney(signInCheckOn.getHolidayOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                            salaryMonthAdd.setWeekendOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getWeekendOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());
                            salaryMonthAdd.setEightHourOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getEightHourOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());
                            salaryMonthAdd.setHolidayOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getHolidayOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());

                            //月打卡天数（不算周6，周天，节假日）*天工资+年假的总天数（申请时长折合成天数）*天工资+请假工资+加班费
                            salaryMonthAdd.setRealSalary(totalSalary
                                    .add(guaranteeSalary)
                                    .add(signInCheckOn.getWeekendOverTimeMoney())
                                    .add(signInCheckOn.getEightHourOverTimeMoney())
                                    .add(signInCheckOn.getHolidayOverTimeMoney())
                                    .setScale(2, RoundingMode.HALF_UP)
                                    .toString());

                            if(Double.parseDouble(salaryMonthAdd.getRealSalary()) < 0){
                                salaryMonthAdd.setRealSalary("0.00");
                            }

                            if(Objects.nonNull(socialSecuritySetup)){
                                //扣社保
                                if(DEDUCTION_TYPE_UNIFIED.equals(socialSecuritySetup.getDeductionType())){
                                    salaryMonthAdd.setSocialSecurityLevel(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getSocialSecurityLevel())).setScale(2, RoundingMode.HALF_UP).toString());
                                    salaryMonthAdd.setElderlyCareDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());
                                    salaryMonthAdd.setMedicalDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());
                                    salaryMonthAdd.setUnemploymentDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentDiscountMoney())).setScale(2, RoundingMode.HALF_UP).toString());

                                    //交了社保则扣社保
                                    List<String> notSocialSecurity = Arrays.asList(NOT_SOCIAL_SECURITY);
                                    if(!notSocialSecurity.contains(staffId)){
                                        salaryMonthAdd.setRealSalary(
                                                BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getRealSalary()))
                                                        .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareDiscountMoney())))
                                                        .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentDiscountMoney())))
                                                        .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalDiscountMoney())))
                                                        .setScale(2, RoundingMode.HALF_UP)
                                                        .toString()
                                        );
                                    }
                                }else{
                                    computeRealSalaryCurAdd(salaryMonthAdd,socialSecuritySetup,null);
                                }
                            }else{
                                salaryMonthAdd.setSocialSecurityLevel("0.00");
                                salaryMonthAdd.setElderlyCareDiscountMoney("0.00");
                                salaryMonthAdd.setMedicalDiscountMoney("0.00");
                                salaryMonthAdd.setUnemploymentDiscountMoney("0.00");
                            }
                            salaryMonthAddList.add(salaryMonthAdd);
                            break;
                        }
                    }
                }
                try {
                    baseBeanService.saveBeansListAndexecuteSqlsByParams(salaryMonthAddList,null,null);
                }catch (Exception e){
                    logger.error("操作异常", e);
                }
            }
        }
    }

    private List<BaseBean> findEmployeeSalaryAddByTime(String startDate,String endDate,String companyId,String empStaffId) throws Exception{
        List<BaseBean> salaryMonthAddList = new ArrayList<>();
        int dayCount = signInCheckOnService.selectDays(startDate,endDate);
        //社保数据
        SocialSecuritySetup socialSecuritySetup = findSocialSecuritySetupByCompanyId(companyId);
        //员工工资数据
        Map<String, SalaryData> salaryDataMap = new HashMap<>();
        //员工考勤数据
        List<SignInCheckOn> signInCheckOnList = signInCheckOnService.findCheckOnListByMonth(null,null,startDate,endDate,salaryDataMap,companyId);
        if(salaryDataMap.size()>0){
            //查特定某个员工时
            if(Objects.nonNull(empStaffId)){
                SalaryData salaryData = salaryDataMap.get(empStaffId);
                salaryDataMap = new HashMap<>();
                salaryDataMap.put(empStaffId,salaryData);
            }

            for(Map.Entry<String, SalaryData> salaryDataEntry : salaryDataMap.entrySet() ){
                String staffId = salaryDataEntry.getKey();
                SalaryData salaryData = salaryDataEntry.getValue();
                for(SignInCheckOn signInCheckOn : signInCheckOnList){
                    if(signInCheckOn.getStaffId().equals(staffId)){
                        SalaryCurMonthAdd salaryMonthAdd = new SalaryCurMonthAdd();
                        salaryMonthAdd.setStaffId(staffId);
                        salaryMonthAdd.setStaffName(signInCheckOn.getStaffName());
                        salaryMonthAdd.setSalaryLevelId(salaryData.getSalaryLevelId());
                        salaryMonthAdd.setSalaryLevelNum(salaryData.getSalaryLevelNum());
                        salaryMonthAdd.setSettlementMonth(startDate + " - " + signInCheckOnService.beforeDay(endDate));
                        salaryMonthAdd.setCompanyId(signInCheckOn.getCompanyId());
                        salaryMonthAdd.setBaseSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getBaseSalary()) ? salaryData.getBaseSalary() : "0")));
                        salaryMonthAdd.setRoleSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getRoleSalary()) ? salaryData.getRoleSalary() : "0")));
                        salaryMonthAdd.setDutySalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getDutySalary()) ? salaryData.getDutySalary() : "0")));
                        salaryMonthAdd.setCompeteSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getCompeteSalary()) ? salaryData.getCompeteSalary() : "0")));
                        salaryMonthAdd.setSecrecySalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getSecrecySalary()) ? salaryData.getSecrecySalary() : "0")));
                        salaryMonthAdd.setLevelSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getLevelSalary()) ? salaryData.getLevelSalary() : "0")));
                        salaryMonthAdd.setGuaranteeSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getGuaranteeSalary()) ? salaryData.getGuaranteeSalary() : "0")));
                        salaryMonthAdd.setWelfareSalary(decimalFormat.format(Double.valueOf(Objects.nonNull(salaryData.getWelfareSalary()) ? salaryData.getWelfareSalary() : "0")));
                        salaryMonthAdd.setTotalSalary(BigDecimal.valueOf(Double.parseDouble(Objects.nonNull(salaryData.getGuaranteeSalary()) ? salaryData.getGuaranteeSalary() : "0"))
                                .add(BigDecimal.valueOf(Double.parseDouble(Objects.nonNull(salaryData.getWelfareSalary()) ? salaryData.getWelfareSalary() : "0"))).setScale(2, RoundingMode.HALF_UP).toString());

                        Double signInDayCount = 0D;
                        Double signInDayTotalCount = 0D;
                        Double lateLeaveEarlyDayCount = 0D;
                        if(Objects.nonNull(signInCheckOn.getSignInResultList())){
                            //排除周6，周天，节假日
                            Iterator<SignInResult> iterator = signInCheckOn.getSignInResultList().iterator();
                            while(iterator.hasNext()){
                                SignInResult signInResult = iterator.next();
                                if(overtimeApplyMobileService.isWeekendDay(signInResult.getSignInDate())
                                        || overtimeApplyMobileService.isHoliday(signInResult.getSignInDate())){
                                    iterator.remove();
                                }
                            }
                            //签到总天数
                            signInDayTotalCount = (double)signInCheckOn.getSignInResultList().size();
                            signInDayCount = computeSignInDayCount(signInCheckOn.getSignInResultList());
                            //迟到早退天数
                            lateLeaveEarlyDayCount = signInDayTotalCount - signInDayCount;
                        }
                        //节假日天数
                        int holiDayCount = overtimeApplyMobileService.holidayDayCount(salaryMonthAdd.getSettlementMonth());
                        signInDayTotalCount += holiDayCount;
                        signInDayCount += holiDayCount;

                        salaryMonthAdd.setSignInDayCount(BigDecimal.valueOf(signInDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                        salaryMonthAdd.setSignInDayTotalCount(BigDecimal.valueOf(signInDayTotalCount).setScale(2, RoundingMode.HALF_UP).toString());
                        salaryMonthAdd.setLateLeaveEarlyDayCount(BigDecimal.valueOf(lateLeaveEarlyDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                        //每天考勤工资
                        signInCheckOn.setSalaryPerDay(BigDecimal.ZERO);
                        if(Double.valueOf(salaryMonthAdd.getTotalSalary()) > 0){
                            signInCheckOn.setSalaryPerDay(new BigDecimal(salaryMonthAdd.getTotalSalary()).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP));
                        }
                        //每天保障工资
                        signInCheckOn.setGuaranteeSalaryPerDay(BigDecimal.ZERO);
                        if(Double.valueOf(salaryMonthAdd.getGuaranteeSalary()) > 0){
                            signInCheckOn.setGuaranteeSalaryPerDay(new BigDecimal(salaryMonthAdd.getGuaranteeSalary()).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP));
                        }
                        //请假天数
                        double leaveDayCount = 0;
                        leaveDayCount += signInCheckOn.getLeaveSickDayCount();
                        leaveDayCount += signInCheckOn.getLeaveMarriageDayCount();
                        leaveDayCount += signInCheckOn.getLeaveMaternityDayCount();
                        leaveDayCount += signInCheckOn.getLeaveFuneralDayCount();
                        leaveDayCount += signInCheckOn.getLeaveWorkInjuryDayCount();

                        //年假天数
                        Double leaveAnnualDayCount = 0D;
                        leaveAnnualDayCount += signInCheckOn.getLeaveAnnualDayCount();
                        //轮岗休息天数
                        leaveAnnualDayCount += signInCheckOn.getLeaveRotationRestDayCount();

                        //请假保障工资
                        BigDecimal guaranteeSalary = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                        if(leaveDayCount > 0){
                            guaranteeSalary = signInCheckOn.getGuaranteeSalaryPerDay().multiply(BigDecimal.valueOf(leaveDayCount)).setScale(2, RoundingMode.HALF_UP);
                        }
                        //实际考勤总工资
                        BigDecimal totalSalary = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
                        if(signInDayCount > 0){
                            totalSalary = signInCheckOn.getSalaryPerDay().multiply(BigDecimal.valueOf(signInDayCount)).setScale(2, RoundingMode.HALF_UP);
                        }
                        //加年假
                        guaranteeSalary = guaranteeSalary.add(signInCheckOn.getSalaryPerDay().multiply(BigDecimal.valueOf(leaveAnnualDayCount)).setScale(2, RoundingMode.HALF_UP));
                        leaveDayCount += leaveAnnualDayCount;

                        salaryMonthAdd.setSignInSalary(totalSalary.toString());
                        salaryMonthAdd.setLeaveAddMoney(guaranteeSalary.toString());
                        salaryMonthAdd.setLeaveAddDayCount(BigDecimal.valueOf(leaveDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                        //加班工资
                        salaryMonthAdd.setWeekendOvertimeMoney(signInCheckOn.getWeekendOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                        salaryMonthAdd.setEightHourOvertimeMoney(signInCheckOn.getEightHourOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                        salaryMonthAdd.setHolidayOvertimeMoney(signInCheckOn.getHolidayOverTimeMoney().setScale(2, RoundingMode.HALF_UP).toString());
                        salaryMonthAdd.setWeekendOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getWeekendOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());
                        salaryMonthAdd.setEightHourOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getEightHourOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());
                        salaryMonthAdd.setHolidayOvertimeDayCount(BigDecimal.valueOf(signInCheckOn.getHolidayOverTimeDayCount()).setScale(2, RoundingMode.HALF_UP).toString());

                        //月打卡天数（不算周6，周天，节假日）*天工资+年假的总天数（申请时长折合成天数）*天工资+请假工资+加班费
                        salaryMonthAdd.setRealSalary(totalSalary
                                .add(guaranteeSalary)
                                .add(signInCheckOn.getWeekendOverTimeMoney())
                                .add(signInCheckOn.getEightHourOverTimeMoney())
                                .add(signInCheckOn.getHolidayOverTimeMoney())
                                .setScale(2, RoundingMode.HALF_UP)
                                .toString());

                        if(Double.parseDouble(salaryMonthAdd.getRealSalary()) < 0){
                            salaryMonthAdd.setRealSalary("0.00");
                        }

                        if(Objects.nonNull(socialSecuritySetup)){
                            //扣社保
                            if(DEDUCTION_TYPE_UNIFIED.equals(socialSecuritySetup.getDeductionType())){
                                salaryMonthAdd.setSocialSecurityLevel(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getSocialSecurityLevel())).setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setElderlyCareDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareDiscountMoney()))
                                        .divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount)).setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setMedicalDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalDiscountMoney()))
                                        .divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount)).setScale(2, RoundingMode.HALF_UP).toString());
                                salaryMonthAdd.setUnemploymentDiscountMoney(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentDiscountMoney()))
                                        .divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount)).setScale(2, RoundingMode.HALF_UP).toString());

                                //交了社保则扣社保
                                List<String> notSocialSecurity = Arrays.asList(NOT_SOCIAL_SECURITY_1);
                                if(!notSocialSecurity.contains(staffId)){
                                    salaryMonthAdd.setRealSalary(
                                            BigDecimal.valueOf(Double.parseDouble(salaryMonthAdd.getRealSalary()))
                                                    .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getElderlyCareDiscountMoney()))
                                                            .divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount)))
                                                    .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getUnemploymentDiscountMoney()))
                                                            .divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount)))
                                                    .subtract(BigDecimal.valueOf(Double.parseDouble(socialSecuritySetup.getMedicalDiscountMoney()))
                                                            .divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(dayCount)))
                                                    .setScale(2, RoundingMode.HALF_UP)
                                                    .toString()
                                    );
                                }
                            }else{
                                computeRealSalaryCurAdd(salaryMonthAdd,socialSecuritySetup,dayCount);
                            }
                        }else{
                            salaryMonthAdd.setSocialSecurityLevel("0.00");
                            salaryMonthAdd.setElderlyCareDiscountMoney("0.00");
                            salaryMonthAdd.setMedicalDiscountMoney("0.00");
                            salaryMonthAdd.setUnemploymentDiscountMoney("0.00");
                        }
                        salaryMonthAddList.add(salaryMonthAdd);
                        break;
                    }
                }
            }
        }
        return salaryMonthAddList;
    }

    @Override
    public void deleteSalaryCurMonthAdd(){
        //删除当月表数据
        List<BaseBean> salaryCurMonthAdds = baseBeanService.getListBeanByHqlAndParams("from SalaryCurMonthAdd", null);
        if(Objects.nonNull(salaryCurMonthAdds)){
            for(BaseBean salaryCurMonthAdd : salaryCurMonthAdds){
                baseBeanService.deleteBeanByKey(SalaryCurMonthAdd.class, ((SalaryCurMonthAdd)salaryCurMonthAdd).getSalaryMonthAddId());
            }
        }
    }

    private double computeSignInDayCount(List<SignInResult> signInResultList){
        double dayCount = 0;
        for(SignInResult signInResult : signInResultList){
            int sec = signInResult.getLateSeconds();
            sec += signInResult.getLeaveEarlySeconds();
            dayCount += new BigDecimal(HOUR_WORK_DAY * 60 * 60 - sec)
                    .divide(BigDecimal.valueOf(HOUR_WORK_DAY * 60 * 60),8,RoundingMode.HALF_UP).doubleValue();
        }
        return dayCount;
    }

    private double computeSignInDayCount(SignInResult signInResult){
        double dayCount = 0;
        int sec = signInResult.getLateSeconds();
        sec += signInResult.getLeaveEarlySeconds();
        dayCount = new BigDecimal(HOUR_WORK_DAY * 60 * 60 - sec)
                .divide(BigDecimal.valueOf(HOUR_WORK_DAY * 60 * 60),8,RoundingMode.HALF_UP).doubleValue();
        if(dayCount > 1){
            dayCount = 1;
        }
        return dayCount;
    }

    /**
     * 天工资
     * @param year
     * @param month
     * @param companyId
     * @param staffId
     * @return
     */
    public List<SalaryDay> findSalaryDayListByMonth(String year, String month, String companyId, String staffId) throws Exception{
        List<SalaryDay> salaryDayList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        Date date = dateFormat.parse(year + "-" + month + "-01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String now = dateFormat.format(new Date());
        String curYear = now.split("-")[0];
        String curMonth = now.split("-")[1];
        String curDay = now.split("-")[2];
        if(year.equals(curYear) && month.equals(curMonth)){
            dayCount = Integer.valueOf(curDay) - 1;
        }
        //员工考勤数据
        List<SignInCheckOn> signInCheckOnList = signInCheckOnService.findCheckOnListByMonth(year,month,null,null,new HashMap<>(),companyId);
        List<BaseBean> baseBeanList = findSalarySettlementListByStaffId(year,month,staffId,companyId);
        for(int i = 1; i <= dayCount; i++){
            SalaryDay salaryDay = new SalaryDay();
            for(SignInCheckOn signInCheckOn : signInCheckOnList) {
                if (signInCheckOn.getStaffId().equals(staffId)) {
                    if(Objects.nonNull(baseBeanList) && !baseBeanList.isEmpty()) {
                        Object obj = baseBeanList.get(0);
                        Object[] dataArr = (Object[]) obj;
                        String levelNum = dataArr[4].toString();
                        String guaranteeSalary = dataArr[6].toString();
                        String welfareSalary = dataArr[7].toString();
                        salaryDay.setSalaryLevelNum(levelNum);
                        //工作日每天保障工资
                        BigDecimal guaranteeSalaryPerDay = BigDecimal.ZERO;
                        BigDecimal guaranteeSalaryPerSec = BigDecimal.ZERO;
                        if(Double.valueOf(guaranteeSalary) > 0){
                            guaranteeSalaryPerDay = new BigDecimal(guaranteeSalary).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP);
                        }
                        //加班每天保障工资
                        BigDecimal guaranteeSalaryOvertimePerDay = BigDecimal.ZERO;
                        BigDecimal guaranteeSalaryOvertimePerSec = BigDecimal.ZERO;
                        if(Double.valueOf(guaranteeSalary) > 0){
                            guaranteeSalaryOvertimePerDay = new BigDecimal(guaranteeSalary).divide(BigDecimal.valueOf(signInCheckOnService.findCheckOnDays(companyId,year,month)),8,RoundingMode.HALF_UP);
                            guaranteeSalaryOvertimePerSec = new BigDecimal(guaranteeSalary).divide(BigDecimal.valueOf(signInCheckOnService.findCheckOnDays(companyId,year,month) * HOUR_WORK_DAY * 60 * 60),8,RoundingMode.HALF_UP);
                        }
                        //每天福利工资
                        BigDecimal welfareSalaryPerDay = BigDecimal.ZERO;
                        if(Double.valueOf(welfareSalary) > 0){
                            welfareSalaryPerDay = new BigDecimal(welfareSalary).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP);
                        }
                        salaryDay.setGuaranteeSalary(guaranteeSalaryPerDay.setScale(2, RoundingMode.HALF_UP).toString());
                        salaryDay.setWelfareSalary(welfareSalaryPerDay.setScale(2, RoundingMode.HALF_UP).toString());

                        List<SignInResult> signInResultList = Objects.nonNull(signInCheckOn.getSignInResultList())
                                ? signInCheckOn.getSignInResultList() : new ArrayList<>();
                        String day = "";
                        if(i < 10){
                            day = "0" + i;
                        }else{
                            day = "" + i;
                        }
                        salaryDay.setDate(year + "-" + month + "-" + day);
                        //请假比对
                        List<LeaveData> leaveDataList = Objects.nonNull(signInCheckOn.getLeaveDataList()) ?
                                signInCheckOn.getLeaveDataList() : new ArrayList<>();
                        if(!overtimeApplyMobileService.isWeekendDay(salaryDay.getDate())){
                            if(!overtimeApplyMobileService.isHoliday(salaryDay.getDate())){
                                for(LeaveData leaveData : leaveDataList){
                                    if(salaryDay.getDate().equals(leaveData.getLeaveDate())){
                                        leaveSalary(leaveData,salaryDay);
                                        break;
                                    }
                                }
                            }
                            //打卡比对
                            if(Objects.isNull(salaryDay.getLeaveType())){
                                salaryDay.setLeaveDayCount("0");
                                for(SignInResult signInResult : signInResultList){
                                    if(salaryDay.getDate().equals(signInResult.getSignInDate())){
                                        double signInDayCount = computeSignInDayCount(signInResult);
                                        salaryDay.setSignInDayCount(BigDecimal.valueOf(signInDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryDay.setLateLeaveEarlyDayCount(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(signInDayCount)).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryDay.setRealGuaranteeSalary(guaranteeSalaryPerDay.multiply(BigDecimal.valueOf(signInDayCount)).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryDay.setRealWelfareSalary(welfareSalaryPerDay.multiply(BigDecimal.valueOf(signInDayCount)).setScale(2, RoundingMode.HALF_UP).toString());
                                        break;
                                    }
                                }
                            }
                        }
                        if(Objects.isNull(salaryDay.getSignInDayCount())){
                            salaryDay.setSignInDayCount("0");
                            salaryDay.setRealGuaranteeSalary("0");
                            salaryDay.setRealWelfareSalary("0");
                            salaryDay.setLateLeaveEarlyDayCount("0");
                        }
                        //节假日
                        if(overtimeApplyMobileService.isHoliday(salaryDay.getDate())){
                            //周6，7不算工资
                            String[] dates = salaryDay.getDate().split("-");
                            cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
                            if(cal.get(Calendar.DAY_OF_WEEK) != 1 && cal.get(Calendar.DAY_OF_WEEK) != 7){
                                salaryDay.setRealGuaranteeSalary(guaranteeSalaryPerDay.setScale(2, RoundingMode.HALF_UP).toString());
                                salaryDay.setRealWelfareSalary(welfareSalaryPerDay.setScale(2, RoundingMode.HALF_UP).toString());
                                salaryDay.setSignInDayCount("1.00");
                                salaryDay.setLateLeaveEarlyDayCount("0");
                            }
                        }
                        //加班比对
                        List<OvertimeData> overtimeDataList = Objects.nonNull(signInCheckOn.getOvertimeDataList())
                                ? signInCheckOn.getOvertimeDataList() : new ArrayList<>();
                        for(OvertimeData overtimeData : overtimeDataList){
                            if(salaryDay.getDate().equals(overtimeData.getOvertimeDate())){
                                salaryDay.setOvertimeType(overtimeEngNameToTypeName(overtimeData.getOvertimeType()));
                                BigDecimal overtimeDayCount = BigDecimal.valueOf(overtimeData.getOvertimeHours()).divide(BigDecimal.valueOf(HOUR_WORK_DAY),8,RoundingMode.HALF_UP);
                                salaryDay.setOvertimeDayCount(overtimeDayCount.setScale(2, RoundingMode.HALF_UP).toString());
                                BigDecimal guaranteeSal = guaranteeSalaryOvertimePerSec.multiply(BigDecimal.valueOf(overtimeData.getOvertimeSeconds())).setScale(2, RoundingMode.HALF_UP);
                                salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(2)).toString());
                                //节假日周6，7加班
                                if(overtimeApplyMobileService.isHoliday(salaryDay.getDate())){
                                    String[] dates = salaryDay.getDate().split("-");
                                    cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
                                    if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
                                        salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(2)).toString());
                                    }
                                }
                                //3倍加班费
                                if(overtimeApplyMobileService.isBigHoliday(salaryDay.getDate())){
                                    String[] dates = salaryDay.getDate().split("-");
                                    cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
                                    if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
                                        salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(6)).toString());
                                    }else{
                                        salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(4)).toString());
                                    }
                                }
                                break;
                            }
                        }
                        if(Objects.isNull(salaryDay.getOvertimeDayCount())){
                            salaryDay.setOvertimeDayCount("0");
                            salaryDay.setOvertimeMoney("0");
                        }

                        List<OvertimeData> weekendOvertimeDataList = Objects.nonNull(signInCheckOn.getWeekendOvertimeDataList())
                                ? signInCheckOn.getWeekendOvertimeDataList() : new ArrayList<>();
                        for(OvertimeData overtimeData : weekendOvertimeDataList){
                            if(salaryDay.getDate().equals(overtimeData.getOvertimeDate())){
                                salaryDay.setOvertimeType(overtimeEngNameToTypeName(overtimeData.getOvertimeType()));
                                BigDecimal overtimeDayCount = BigDecimal.valueOf(overtimeData.getOvertimeHours()).divide(BigDecimal.valueOf(HOUR_WORK_DAY),8,RoundingMode.HALF_UP);
                                if(overtimeDayCount.compareTo(BigDecimal.valueOf(1)) > 0){
                                    overtimeDayCount = BigDecimal.valueOf(1);
                                }
                                salaryDay.setOvertimeDayCount(overtimeDayCount.setScale(2, RoundingMode.HALF_UP).toString());
                                BigDecimal guaranteeSal = guaranteeSalaryOvertimePerSec.multiply(BigDecimal.valueOf(overtimeData.getOvertimeSeconds())).setScale(2, RoundingMode.HALF_UP);
                                salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(2)).toString());

                                salaryDay.setSignInDayCount("1.00");
                                salaryDay.setRealGuaranteeSalary("0");
                                salaryDay.setRealWelfareSalary("0");
                                salaryDay.setLateLeaveEarlyDayCount("0");
                                salaryDay.setLeaveDayCount("0");
                                break;
                            }
                        }
                        if(Objects.isNull(salaryDay.getOvertimeDayCount())){
                            salaryDay.setOvertimeDayCount("0");
                            salaryDay.setOvertimeMoney("0");
                        }
                        if(Objects.isNull(salaryDay.getLeaveDayCount())){
                            salaryDay.setLeaveDayCount("0");
                        }
                        //求和
                        BigDecimal guarantee = Objects.nonNull(salaryDay.getRealGuaranteeSalary()) ?
                                BigDecimal.valueOf(Double.valueOf(salaryDay.getRealGuaranteeSalary())) : BigDecimal.ZERO;
                        BigDecimal welfare = Objects.nonNull(salaryDay.getRealWelfareSalary()) ?
                                BigDecimal.valueOf(Double.valueOf(salaryDay.getRealWelfareSalary())) : BigDecimal.ZERO;
                        BigDecimal overtimeMoney = Objects.nonNull(salaryDay.getOvertimeMoney()) ?
                                BigDecimal.valueOf(Double.valueOf(salaryDay.getOvertimeMoney())) : BigDecimal.ZERO;
                        salaryDay.setSignInSalary(guarantee.add(welfare).setScale(2, RoundingMode.HALF_UP).toString());
                        salaryDay.setRealSalary(guarantee.add(welfare).add(overtimeMoney).setScale(2, RoundingMode.HALF_UP).toString());
                        salaryDayList.add(salaryDay);
                    }
                    break;
                }
            }
        }
        return salaryDayList;
    }

    /**
     * 按日期范围查天工资
     * @param startDate
     * @param endDate
     * @param companyId
     * @param staffId
     * @return
     * @throws Exception
     */
    public List<SalaryDay> findSalaryDayListByDate(String startDate, String endDate, String companyId, String staffId) throws Exception{
        int dayCount = signInCheckOnService.selectDays(startDate,endDate);
        List<SalaryDay> salaryDayList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();

        List<String> dateArray = new ArrayList<>();
        dateArray.add(startDate);
        if(dayCount > 0){
            String date = startDate;
            for(int i = 0;i < dayCount;i++){
                date = signInCheckOnService.selectNextDate(date);
                if(StringUtils.isNotBlank(date)){
                    dateArray.add(date);
                }else{
                    break;
                }
            }
        }
        //员工考勤数据
        List<SignInCheckOn> signInCheckOnList = signInCheckOnService.findCheckOnListByMonth(null,null,startDate,endDate,new HashMap<>(),companyId);
        List<BaseBean> baseBeanList = findSalarySettlementListByStaffId(startDate.substring(0,4),startDate.substring(5,7),staffId,companyId);
        for(int i = 1; i <= dayCount; i++){
            SalaryDay salaryDay = new SalaryDay();
            for(SignInCheckOn signInCheckOn : signInCheckOnList) {
                if (signInCheckOn.getStaffId().equals(staffId)) {
                    if(Objects.nonNull(baseBeanList) && !baseBeanList.isEmpty()) {
                        Object obj = baseBeanList.get(0);
                        Object[] dataArr = (Object[]) obj;
                        String levelNum = dataArr[4].toString();
                        String guaranteeSalary = dataArr[6].toString();
                        String welfareSalary = dataArr[7].toString();
                        salaryDay.setSalaryLevelNum(levelNum);
                        //工作日每天保障工资
                        BigDecimal guaranteeSalaryPerDay = BigDecimal.ZERO;
                        BigDecimal guaranteeSalaryPerSec = BigDecimal.ZERO;
                        if(Double.valueOf(guaranteeSalary) > 0){
                            guaranteeSalaryPerDay = new BigDecimal(guaranteeSalary).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP);
                        }
                        //加班每天保障工资
                        BigDecimal guaranteeSalaryOvertimePerDay = BigDecimal.ZERO;
                        BigDecimal guaranteeSalaryOvertimePerSec = BigDecimal.ZERO;
                        if(Double.valueOf(guaranteeSalary) > 0){
                            guaranteeSalaryOvertimePerDay = new BigDecimal(guaranteeSalary).divide(BigDecimal.valueOf(signInCheckOnService.findCheckOnDays(companyId,null,null)),8,RoundingMode.HALF_UP);
                            guaranteeSalaryOvertimePerSec = new BigDecimal(guaranteeSalary).divide(BigDecimal.valueOf(signInCheckOnService.findCheckOnDays(companyId,null,null) * HOUR_WORK_DAY * 60 * 60),8,RoundingMode.HALF_UP);
                        }
                        //每天福利工资
                        BigDecimal welfareSalaryPerDay = BigDecimal.ZERO;
                        if(Double.valueOf(welfareSalary) > 0){
                            welfareSalaryPerDay = new BigDecimal(welfareSalary).divide(BigDecimal.valueOf(CHECK_ON_DAYS_PER_MONTH),8,RoundingMode.HALF_UP);
                        }
                        salaryDay.setGuaranteeSalary(guaranteeSalaryPerDay.setScale(2, RoundingMode.HALF_UP).toString());
                        salaryDay.setWelfareSalary(welfareSalaryPerDay.setScale(2, RoundingMode.HALF_UP).toString());

                        List<SignInResult> signInResultList = Objects.nonNull(signInCheckOn.getSignInResultList())
                                ? signInCheckOn.getSignInResultList() : new ArrayList<>();

                        salaryDay.setDate(dateArray.get(i - 1));
                        //请假比对
                        List<LeaveData> leaveDataList = Objects.nonNull(signInCheckOn.getLeaveDataList()) ?
                                signInCheckOn.getLeaveDataList() : new ArrayList<>();
                        if(!overtimeApplyMobileService.isWeekendDay(salaryDay.getDate())){
                            if(!overtimeApplyMobileService.isHoliday(salaryDay.getDate())){
                                for(LeaveData leaveData : leaveDataList){
                                    if(salaryDay.getDate().equals(leaveData.getLeaveDate())){
                                        leaveSalary(leaveData,salaryDay);
                                        break;
                                    }
                                }
                            }
                            //打卡比对
                            if(Objects.isNull(salaryDay.getLeaveType())){
                                salaryDay.setLeaveDayCount("0");
                                for(SignInResult signInResult : signInResultList){
                                    if(salaryDay.getDate().equals(signInResult.getSignInDate())){
                                        double signInDayCount = computeSignInDayCount(signInResult);
                                        salaryDay.setSignInDayCount(BigDecimal.valueOf(signInDayCount).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryDay.setLateLeaveEarlyDayCount(BigDecimal.valueOf(1).subtract(BigDecimal.valueOf(signInDayCount)).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryDay.setRealGuaranteeSalary(guaranteeSalaryPerDay.multiply(BigDecimal.valueOf(signInDayCount)).setScale(2, RoundingMode.HALF_UP).toString());
                                        salaryDay.setRealWelfareSalary(welfareSalaryPerDay.multiply(BigDecimal.valueOf(signInDayCount)).setScale(2, RoundingMode.HALF_UP).toString());
                                        break;
                                    }
                                }
                            }
                        }
                        if(Objects.isNull(salaryDay.getSignInDayCount())){
                            salaryDay.setSignInDayCount("0");
                            salaryDay.setRealGuaranteeSalary("0");
                            salaryDay.setRealWelfareSalary("0");
                            salaryDay.setLateLeaveEarlyDayCount("0");
                        }
                        //节假日
                        if(overtimeApplyMobileService.isHoliday(salaryDay.getDate())){
                            //周6，7不算工资
                            String[] dates = salaryDay.getDate().split("-");
                            cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
                            if(cal.get(Calendar.DAY_OF_WEEK) != 1 && cal.get(Calendar.DAY_OF_WEEK) != 7){
                                salaryDay.setRealGuaranteeSalary(guaranteeSalaryPerDay.setScale(2, RoundingMode.HALF_UP).toString());
                                salaryDay.setRealWelfareSalary(welfareSalaryPerDay.setScale(2, RoundingMode.HALF_UP).toString());
                                salaryDay.setSignInDayCount("1.00");
                                salaryDay.setLateLeaveEarlyDayCount("0");
                            }
                        }
                        //加班比对
                        List<OvertimeData> overtimeDataList = Objects.nonNull(signInCheckOn.getOvertimeDataList())
                                ? signInCheckOn.getOvertimeDataList() : new ArrayList<>();
                        for(OvertimeData overtimeData : overtimeDataList){
                            if(salaryDay.getDate().equals(overtimeData.getOvertimeDate())){
                                salaryDay.setOvertimeType(overtimeEngNameToTypeName(overtimeData.getOvertimeType()));
                                BigDecimal overtimeDayCount = BigDecimal.valueOf(overtimeData.getOvertimeHours()).divide(BigDecimal.valueOf(HOUR_WORK_DAY),8,RoundingMode.HALF_UP);
                                salaryDay.setOvertimeDayCount(overtimeDayCount.setScale(2, RoundingMode.HALF_UP).toString());
                                BigDecimal guaranteeSal = guaranteeSalaryOvertimePerSec.multiply(BigDecimal.valueOf(overtimeData.getOvertimeSeconds())).setScale(2, RoundingMode.HALF_UP);
                                salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(2)).toString());
                                //节假日周6，7加班
                                if(overtimeApplyMobileService.isHoliday(salaryDay.getDate())){
                                    String[] dates = salaryDay.getDate().split("-");
                                    cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
                                    if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
                                        salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(2)).toString());
                                    }
                                }
                                //3倍加班费
                                if(overtimeApplyMobileService.isBigHoliday(salaryDay.getDate())){
                                    String[] dates = salaryDay.getDate().split("-");
                                    cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
                                    if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
                                        salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(6)).toString());
                                    }else{
                                        salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(4)).toString());
                                    }
                                }
                                break;
                            }
                        }
                        if(Objects.isNull(salaryDay.getOvertimeDayCount())){
                            salaryDay.setOvertimeDayCount("0");
                            salaryDay.setOvertimeMoney("0");
                        }

                        List<OvertimeData> weekendOvertimeDataList = Objects.nonNull(signInCheckOn.getWeekendOvertimeDataList())
                                ? signInCheckOn.getWeekendOvertimeDataList() : new ArrayList<>();
                        for(OvertimeData overtimeData : weekendOvertimeDataList){
                            if(salaryDay.getDate().equals(overtimeData.getOvertimeDate())){
                                salaryDay.setOvertimeType(overtimeEngNameToTypeName(overtimeData.getOvertimeType()));
                                BigDecimal overtimeDayCount = BigDecimal.valueOf(overtimeData.getOvertimeHours()).divide(BigDecimal.valueOf(HOUR_WORK_DAY),8,RoundingMode.HALF_UP);
                                if(overtimeDayCount.compareTo(BigDecimal.valueOf(1)) > 0){
                                    overtimeDayCount = BigDecimal.valueOf(1);
                                }
                                salaryDay.setOvertimeDayCount(overtimeDayCount.setScale(2, RoundingMode.HALF_UP).toString());
                                BigDecimal guaranteeSal = guaranteeSalaryOvertimePerSec.multiply(BigDecimal.valueOf(overtimeData.getOvertimeSeconds())).setScale(2, RoundingMode.HALF_UP);
                                salaryDay.setOvertimeMoney(guaranteeSal.multiply(BigDecimal.valueOf(2)).toString());

                                salaryDay.setSignInDayCount("1.00");
                                salaryDay.setRealGuaranteeSalary("0");
                                salaryDay.setRealWelfareSalary("0");
                                salaryDay.setLateLeaveEarlyDayCount("0");
                                salaryDay.setLeaveDayCount("0");
                                break;
                            }
                        }
                        if(Objects.isNull(salaryDay.getOvertimeDayCount())){
                            salaryDay.setOvertimeDayCount("0");
                            salaryDay.setOvertimeMoney("0");
                        }
                        if(Objects.isNull(salaryDay.getLeaveDayCount())){
                            salaryDay.setLeaveDayCount("0");
                        }
                        //求和
                        BigDecimal guarantee = Objects.nonNull(salaryDay.getRealGuaranteeSalary()) ?
                                BigDecimal.valueOf(Double.valueOf(salaryDay.getRealGuaranteeSalary())) : BigDecimal.ZERO;
                        BigDecimal welfare = Objects.nonNull(salaryDay.getRealWelfareSalary()) ?
                                BigDecimal.valueOf(Double.valueOf(salaryDay.getRealWelfareSalary())) : BigDecimal.ZERO;
                        BigDecimal overtimeMoney = Objects.nonNull(salaryDay.getOvertimeMoney()) ?
                                BigDecimal.valueOf(Double.valueOf(salaryDay.getOvertimeMoney())) : BigDecimal.ZERO;
                        salaryDay.setSignInSalary(guarantee.add(welfare).setScale(2, RoundingMode.HALF_UP).toString());
                        salaryDay.setRealSalary(guarantee.add(welfare).add(overtimeMoney).setScale(2, RoundingMode.HALF_UP).toString());
                        salaryDayList.add(salaryDay);
                    }
                    break;
                }
            }
        }
        return salaryDayList;
    }
    private void leaveSalary(LeaveData leaveData,SalaryDay salaryDay){
        BigDecimal leaveDay = BigDecimal.valueOf(leaveData.getLeaveHours()).divide(BigDecimal.valueOf(HOUR_WORK_DAY),8,RoundingMode.HALF_UP);
        if(leaveDay.compareTo(BigDecimal.valueOf(1)) > 0){
            leaveDay = BigDecimal.valueOf(1);
        }
        BigDecimal guaranteeSalary = BigDecimal.valueOf(Double.valueOf(salaryDay.getGuaranteeSalary()));
        BigDecimal welfareSalary = BigDecimal.valueOf(Double.valueOf(salaryDay.getWelfareSalary()));
        if(LEAVE_ABSENCE.equals(leaveData.getLeaveType())){
            salaryDay.setRealGuaranteeSalary(guaranteeSalary.subtract(guaranteeSalary.multiply(leaveDay)).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setRealWelfareSalary(welfareSalary.subtract(welfareSalary.multiply(leaveDay)).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setSignInDayCount(BigDecimal.valueOf(1).subtract(leaveDay).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setLeaveType(checkOnEngNameToTypeName(LEAVE_ABSENCE));
            salaryDay.setLeaveDayCount(leaveDay.setScale(2, RoundingMode.HALF_UP).toString());
        }else if(LEAVE_SICK.equals(leaveData.getLeaveType())){
            salaryDay.setRealGuaranteeSalary(salaryDay.getGuaranteeSalary());
            salaryDay.setRealWelfareSalary(welfareSalary.subtract(welfareSalary.multiply(leaveDay)).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setSignInDayCount(BigDecimal.valueOf(1).subtract(leaveDay).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setLeaveType(checkOnEngNameToTypeName(LEAVE_SICK));
            salaryDay.setLeaveDayCount(leaveDay.setScale(2, RoundingMode.HALF_UP).toString());
        }else if(LEAVE_MARRIAGE.equals(leaveData.getLeaveType())){
            salaryDay.setRealGuaranteeSalary(salaryDay.getGuaranteeSalary());
            salaryDay.setRealWelfareSalary(welfareSalary.subtract(welfareSalary.multiply(leaveDay)).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setSignInDayCount(BigDecimal.valueOf(1).subtract(leaveDay).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setLeaveType(checkOnEngNameToTypeName(LEAVE_MARRIAGE));
            salaryDay.setLeaveDayCount(leaveDay.setScale(2, RoundingMode.HALF_UP).toString());
        }else if(LEAVE_ANNUAL.equals(leaveData.getLeaveType())){
            salaryDay.setRealGuaranteeSalary(salaryDay.getGuaranteeSalary());
            salaryDay.setRealWelfareSalary(salaryDay.getWelfareSalary());
            salaryDay.setSignInDayCount(BigDecimal.valueOf(1).subtract(leaveDay).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setLeaveType(checkOnEngNameToTypeName(LEAVE_ANNUAL));
            salaryDay.setLeaveDayCount(leaveDay.setScale(2, RoundingMode.HALF_UP).toString());
        }else if(LEAVE_MATERNITY.equals(leaveData.getLeaveType())){
            salaryDay.setRealGuaranteeSalary(salaryDay.getGuaranteeSalary());
            salaryDay.setRealWelfareSalary(welfareSalary.subtract(welfareSalary.multiply(leaveDay)).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setSignInDayCount(BigDecimal.valueOf(1).subtract(leaveDay).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setLeaveType(checkOnEngNameToTypeName(LEAVE_MATERNITY));
            salaryDay.setLeaveDayCount(leaveDay.setScale(2, RoundingMode.HALF_UP).toString());
        }else if(LEAVE_FUNERAL.equals(leaveData.getLeaveType())){
            salaryDay.setRealGuaranteeSalary(salaryDay.getGuaranteeSalary());
            salaryDay.setRealWelfareSalary(welfareSalary.subtract(welfareSalary.multiply(leaveDay)).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setSignInDayCount(BigDecimal.valueOf(1).subtract(leaveDay).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setLeaveType(checkOnEngNameToTypeName(LEAVE_FUNERAL));
            salaryDay.setLeaveDayCount(leaveDay.setScale(2, RoundingMode.HALF_UP).toString());
        }else if(LEAVE_WORKINJURY.equals(leaveData.getLeaveType())){
            salaryDay.setRealGuaranteeSalary(salaryDay.getGuaranteeSalary());
            salaryDay.setRealWelfareSalary(welfareSalary.subtract(welfareSalary.multiply(leaveDay)).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setSignInDayCount(BigDecimal.valueOf(1).subtract(leaveDay).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setLeaveType(checkOnEngNameToTypeName(LEAVE_WORKINJURY));
            salaryDay.setLeaveDayCount(leaveDay.setScale(2, RoundingMode.HALF_UP).toString());
        }else if(LEAVE_ROTATIONREST.equals(leaveData.getLeaveType())){
            salaryDay.setRealGuaranteeSalary(salaryDay.getGuaranteeSalary());
            salaryDay.setRealWelfareSalary(salaryDay.getWelfareSalary());
            salaryDay.setSignInDayCount(BigDecimal.valueOf(1).subtract(leaveDay).setScale(2, RoundingMode.HALF_UP).toString());
            salaryDay.setLeaveType(checkOnEngNameToTypeName(LEAVE_ROTATIONREST));
            salaryDay.setLeaveDayCount(leaveDay.setScale(2, RoundingMode.HALF_UP).toString());
        }
    }

    private List findSalarySettlementListByStaffId(String year, String month, String staffId,String companyId){
        List<Object> paramList = new ArrayList<>();
        paramList.add(companyId);
        paramList.add(staffId);
        paramList.add(year + "-" + month);
        String sql = staffIdSalarySql(year + "-" + month);
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

    private String staffIdSalarySql(String settlementTime) {
        String table = "DT_SALARY_MONTH_ADD";
        String now = dateFormat.format(new Date());
        String currentYear = now.split("-")[0];
        String currentMonth = now.split("-")[1];
        if(settlementTime.equals(currentYear +"-"+ currentMonth)){
            table = "DT_SALARY_CUR_MONTH_ADD";
        }
        return "SELECT DISTINCT S.ROLE_SALARY,S.STAFF_ID,S.COMPANY_ID,S.SETTLEMENT_MONTH,S.SALARY_LEVEL_NUM,S.BASE_SALARY," +
                "                       S.GUARANTEE_SALARY,S.WELFARE_SALARY,S.TOTAL_SALARY " +
                "                                FROM " + table + " S " +
                "                                WHERE S.COMPANY_ID = ? " +
                "                                AND S.STAFF_ID = ? " +
                "                                AND S.SETTLEMENT_MONTH = ? " +
                "                                ORDER BY S.STAFF_ID,S.SETTLEMENT_MONTH";
    }

    private String checkOnEngNameToTypeName(String engName){
        switch (engName){
            case LEAVE_ABSENCE:
                return "事假";
            case LEAVE_SICK:
                return "病假";
            case LEAVE_MARRIAGE:
                return "婚假";
            case LEAVE_ANNUAL:
                return "年假";
            case LEAVE_MATERNITY:
                return "产假";
            case LEAVE_FUNERAL:
                return "丧假";
            case LEAVE_WORKINJURY:
                return "工伤假";
            case LEAVE_ROTATIONREST:
                return "轮岗休息";
        }
        return "";
    }

    private String overtimeEngNameToTypeName(String engName){
        switch (engName){
            case WEEKEND_OVERTIME:
                return "周末日加班";
            case EIGHT_HOUR_OVERTIME:
                return "正常日加班";
            case HOLIDAY_OVERTIME:
                return "节假日加班";
        }
        return "";
    }

    private double signInDayAdd(String year, String month,String companyId) throws Exception{
        int y = 2025;
        int m = 6;
        if(COMPANY_ID.equals(companyId)){
            y = 2024;
            m = 1;
        }
        if(Integer.valueOf(year) >= y && Integer.valueOf(month) >= m){
            double dayCount = signInCheckOnService.findMonthWorkDays(year,month);
            double addDay = CHECK_ON_DAYS_PER_MONTH - dayCount;
            if(addDay > 0){
                return addDay;
//                return BigDecimal.valueOf(addDay).setScale(0, RoundingMode.HALF_UP).intValue();
            }
        }
        return 0;
    }

    /**
     * 删除指定年份月份公司数据
     * @param years
     * @param months
     * @param companyIds
     */
    public void deleteSalaryMonthAdd(String[] years,String[] months,String[] companyIds){
        String sql = "select * from DT_SALARY_MONTH_ADD S " +
                "where SUBSTR(S.SETTLEMENT_MONTH,1,INSTR(S.SETTLEMENT_MONTH,'-') - 1) IN (years) " +
                "AND SUBSTR(S.SETTLEMENT_MONTH,6,2) IN (months) " +
                "AND S.COMPANY_ID IN (companyIds)";
        List<Object> paramList = new ArrayList<>();
        String year = "";
        String month = "";
        String companyId = "";
        for(String y : years){
            year += "?,";
            paramList.add(y);
        }
        sql = sql.replace("years", year.substring(0,year.length() - 1));
        if(Objects.nonNull(months)){
            for(String m : months){
                month += "?,";
                paramList.add(m);
            }
            sql = sql.replace("months", month.substring(0,month.length() - 1));
        }else{
            sql = sql.replace("AND SUBSTR(S.SETTLEMENT_MONTH,6,2) IN (months)", "");
        }
        if(Objects.nonNull(companyIds)){
            for(String c : companyIds){
                companyId += "?,";
                paramList.add(c);
            }
            sql = sql.replace("companyIds", companyId.substring(0,companyId.length() - 1));
        }else{
            sql = sql.replace("AND S.COMPANY_ID IN (companyIds)", "");
        }
        List<Object> salaryMonthAdds = baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
        if(Objects.nonNull(salaryMonthAdds)){
            for(Object salaryMonthAdd : salaryMonthAdds){
                baseBeanService.deleteBeanByKey(SalaryMonthAdd.class, ((Object[])salaryMonthAdd)[0].toString());
            }
        }
    }

    public static void main(String[] args){
        String[] years = {"2024","2025"};
        String year = Arrays.asList(years).toString();
        logger.info("值：{}", year);
    }

    /**
     * 工资日结 todo
     * @param year
     * @param date
     * @throws Exception
     */
    public void saveEmployeeSalaryDay(String year,String date) throws Exception{

    }

}
