package hy.ea.human.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.DutyScheduling;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.salary.SalaryData;
import hy.ea.bo.human.salary.SalaryDictData;
import hy.ea.bo.human.vo.*;
import hy.ea.human.service.DutyApplyService;
import hy.ea.human.service.OvertimeApplyMobileService;
import hy.ea.human.service.SignInCheckOnService;
import hy.ea.office.bo.CheckOnSetup;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static hy.ea.human.constant.Constant.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class SignInCheckOnServiceImpl implements SignInCheckOnService {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final Logger log = LoggerFactory.getLogger(SignInCheckOnServiceImpl.class);
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private DutyApplyService dutyApplyService;
    @Resource
    private OvertimeApplyMobileService overtimeApplyMobileService;

    /**
     * 目前迟到会统计今天，早退，请假和旷工暂时不统计今天，因为今天统计不准
     * @param pageForm
     * @param checkOnTimeType
     * @param employee
     * @return
     * @throws Exception
     */
    @Override
    public String findCheckOnList(PageForm pageForm,String checkOnTimeType,String employee) throws Exception {
        String companyId = null;
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if (Objects.nonNull(account.getCompanyID()) && StringUtils.isNotBlank(account.getCompanyID())) {
                companyId = account.getCompanyID();
            }
        }
        if(Objects.nonNull(companyId) && (Objects.nonNull(account.getStaffID()) && StringUtils.isNotBlank(account.getStaffID()))){
            //如果是授权角色则可以查对应公司全部员工的考勤数据
            if(checkRole(account.getStaffID(),companyId)){
                employee = ALL;
            }else{
                employee = CURRENT;
            }
            if(Objects.isNull(checkOnTimeType)){
                checkOnTimeType = CURRENT_WEEK;
            }

            String [] timeRange = timeTypeToTimeRange(checkOnTimeType);
            //查询员工数据
            pageForm = getList(pageForm, checkOnTimeType, employee);
            if(Objects.nonNull(pageForm)){
                List<SignInCheckOn> checkOnList = new ArrayList<>();
                List<BaseBean> objectList = pageForm.getList();
                if(Objects.nonNull(objectList) && !objectList.isEmpty()){
                    for (Object obj : objectList) {
                        Object[] arr = (Object[]) obj;
                        SignInCheckOn signInCheckOn = new SignInCheckOn(arr[2].toString(),arr[0].toString(),null,arr[1].toString(),
                                arr[3].toString(),null,null,null);
                        checkOnList.add(signInCheckOn);
                    }
                    if(Objects.nonNull(checkOnList) && !checkOnList.isEmpty()){
                        //拿到员工ID List
                        List<String> staffIdList = new ArrayList<>();
                        for(SignInCheckOn signInCheckOn : checkOnList){
                            staffIdList.add(signInCheckOn.getStaffId());
                        }
                        //汇总员工的签到数据
                        Map<String,List<SignInResult>> signInFourResultMap = new HashMap<>();
                        //4次打卡
                        List<BaseBean> fourCheckOnList = findFourSignInList(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]), staffIdList,false);
                        signInCheck(fourCheckOnList,signInFourResultMap,false,companyId);
                        //过滤中午没有打两次卡的数据
                        List<BaseBean> afternoonCheckOnList = findAfternoonSignInList(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]), staffIdList,false);
                        Map<String,List<SignInResult>> signInResultMap = filterSignInCheck(afternoonCheckOnList,signInFourResultMap);
                        //对于今天不会统计早退情况，只会统计迟到，因为当天的早退数据是查不准的，员工可能很晚才打下班卡
                        if(checkToday(checkOnTimeType)){
                            String today = dateFormat.format(new Date());
                            List<BaseBean> objectCheckOnTodayList = findFourSignInList(today,selectNextDate(today), staffIdList,true);
                            signInCheck(objectCheckOnTodayList,signInResultMap,true,companyId);
                        }
                        //2次打卡
                        List<BaseBean> objectCheckOnList = findSignInList(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]), staffIdList,false);
                        signInCheck(objectCheckOnList,signInResultMap,false,companyId);
                        //对于今天不会统计早退情况，只会统计迟到，因为当天的早退数据是查不准的，员工可能很晚才打下班卡
                        if(checkToday(checkOnTimeType)){
                            String today = dateFormat.format(new Date());
                            List<BaseBean> objectCheckOnTodayList = findSignInList(today,selectNextDate(today), staffIdList,true);
                            signInCheck(objectCheckOnTodayList,signInResultMap,true,companyId);
                        }
                        //非普通班打卡
                        List<BaseBean> dutyList = dutyApplyService.findDutyList(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]),staffIdList,companyId);
                        List<BaseBean> nonOrdinarycheckOnList = findNonOrdinarySignInList(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]), staffIdList,false,companyId);
                        signInCheckNonOrdinary(nonOrdinarycheckOnList,dutyList,signInResultMap,false,companyId);
                        //对于今天不会统计早退情况，只会统计迟到，因为当天的早退数据是查不准的，员工可能很晚才打下班卡
                        if(checkToday(checkOnTimeType)){
                            String today = dateFormat.format(new Date());
                            List<BaseBean> dutyTodayList = dutyApplyService.findDutyList(today,selectNextDate(today),staffIdList,companyId);
                            List<BaseBean> nonOrdinarycheckOnTodayList = findNonOrdinarySignInList(today,selectNextDate(today), staffIdList,true,companyId);
                            signInCheckNonOrdinary(nonOrdinarycheckOnTodayList,dutyTodayList,signInResultMap,true,companyId);
                        }
                        //增加不需要打卡的日期
                        signInNoNeed(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]),signInResultMap,companyId,staffIdList);
                        //月考勤天数
                        Double checkOnDaysPerMonth = findCheckOnDays(companyId,timeRange[0].split("-")[0],timeRange[0].split("-")[1]);
                        //查询员工级别工资
                        Map<String, SalaryData> salaryDataMap = findEmployeeSalary(staffIdList,companyId);

                        Map<String, SignInCheckOn>  signInCheckOnMap = new HashMap<>();
                        //对于有签到的员工做统计迟到，早退情况，如果没有签到，则是做旷工，请假统计
                        if(signInResultMap.size() > 0){
                            //计算员工的考勤数据，迟到，早退
                            signInCheckOnMap = computeCheckOnData(signInResultMap,staffIdList,companyId,checkOnTimeType,checkOnDaysPerMonth,salaryDataMap);
                        }
                        saveToSessionSignIn(signInResultMap);
                        //计算员工请假，不统计今天的请假
                        Map<String, SignInCheckOn> leaveMap = computeLeaveData(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]),
                                staffIdList,companyId,checkOnDaysPerMonth,salaryDataMap,true);
                        //计算员工外勤，不统计今天的外勤
                        Map<String, SignInCheckOn> outworkMap = computeOutworkData(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]),
                                staffIdList,companyId,true);
                        //计算员工旷工,如果没有签到,没有请假，没有外勤则是旷工
                        Map<String, SignInCheckOn> absenteeMap = computeAbsenteeData(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]),
                                staffIdList,companyId,checkOnTimeType,checkOnDaysPerMonth,salaryDataMap,signInResultMap,leaveMap,outworkMap,true);

                        //计算员工加班
                        //需要申请的加班，平时8小时以外，节假日加班(包含节假日的周6周天)
                        Map<String, SignInCheckOn> overtimeMap = computeOvertimeData(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]),
                                staffIdList,companyId,checkOnDaysPerMonth,salaryDataMap,signInResultMap,true);

                        //周6周天不用申请的加班，排除因调休需要上班的周6周天,排除节假日的周6周天
                        Map<String, SignInCheckOn> weekendOvertimeMap = computeWeekendOvertimeData(timeRange[0],getEndDate(checkOnTimeType,timeRange[1]),null,
                                staffIdList,companyId,checkOnTimeType,checkOnDaysPerMonth,salaryDataMap,signInResultMap,true);

                        //填充数据
                        List<BaseBean> baseBeanList = new ArrayList<>();
                        for(SignInCheckOn signInCheckOn : checkOnList){
                            signInCheckOn.setStartTime(timeRange[0]);
                            signInCheckOn.setEndTime(beforeDay(timeRange[1]));
                            signInCheckOn.setCheckOnTimeType(checkOnTimeType);
                            SalaryData salaryData = salaryDataMap.get(signInCheckOn.getStaffId());
                            if(Objects.nonNull(salaryData)){
                                try {
                                    signInCheckOn.setBaseSalary(Objects.nonNull(salaryData.getBaseSalary()) ? Double.parseDouble(salaryData.getBaseSalary()) : null);
                                    signInCheckOn.setRoleSalary(Objects.nonNull(salaryData.getRoleSalary()) ? Double.parseDouble(salaryData.getRoleSalary()) : null);
                                    signInCheckOn.setDutySalary(Objects.nonNull(salaryData.getDutySalary()) ? Double.parseDouble(salaryData.getDutySalary()) : null);
                                    signInCheckOn.setCompeteSalary(Objects.nonNull(salaryData.getCompeteSalary()) ? Double.parseDouble(salaryData.getCompeteSalary()) : null);
                                    signInCheckOn.setSecrecySalary(Objects.nonNull(salaryData.getSecrecySalary()) ? Double.parseDouble(salaryData.getSecrecySalary()) : null);
                                    signInCheckOn.setLevelSalary(Objects.nonNull(salaryData.getLevelSalary()) ? Double.parseDouble(salaryData.getLevelSalary()) : null);
                                    signInCheckOn.setGuaranteeSalary(Objects.nonNull(salaryData.getGuaranteeSalary()) ? Double.parseDouble(salaryData.getGuaranteeSalary()) : null);
                                    signInCheckOn.setWelfareSalary(Objects.nonNull(salaryData.getWelfareSalary()) ? Double.parseDouble(salaryData.getWelfareSalary()) : null);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                signInCheckOn.setSalaryLevelId(salaryData.getSalaryLevelId());
                                signInCheckOn.setSalaryLevelNum(salaryData.getSalaryLevelNum());
                            }
                            //迟到早退统计
                            SignInCheckOn staffSignIn = signInCheckOnMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setLateCount(Objects.nonNull(staffSignIn) ? staffSignIn.getLateCount() : 0);
                            signInCheckOn.setLateMinutes(Objects.nonNull(staffSignIn) ? staffSignIn.getLateMinutes() : 0);
                            signInCheckOn.setLateDiscountMoney(Objects.nonNull(staffSignIn) ? staffSignIn.getLateDiscountMoney() : BigDecimal.ZERO);
                            signInCheckOn.setLeaveEarlyCount(Objects.nonNull(staffSignIn) ? staffSignIn.getLeaveEarlyCount() : 0);
                            signInCheckOn.setLeaveEarlyMinutes(Objects.nonNull(staffSignIn) ? staffSignIn.getLeaveEarlyMinutes() : 0);
                            signInCheckOn.setLeaveEarlyDiscountMoney(Objects.nonNull(staffSignIn) ? staffSignIn.getLeaveEarlyDiscountMoney() : BigDecimal.ZERO);
                            signInCheckOn.setTotalDiscountMoney(Objects.nonNull(staffSignIn) ? staffSignIn.getTotalDiscountMoney() : BigDecimal.ZERO);
                            //请假统计
                            SignInCheckOn staffLeave = leaveMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setLeaveDataList(Objects.nonNull(staffLeave) ? staffLeave.getLeaveDataList() : new ArrayList<>());
                            signInCheckOn.setLeaveAbsenceDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAbsenceDayCount(): 0);
                            signInCheckOn.setLeaveAbsenceHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAbsenceHours(): 0);
                            signInCheckOn.setLeaveAbsenceMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAbsenceMinutes(): 0);
                            signInCheckOn.setLeaveAbsenceDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAbsenceDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveSickDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveSickDayCount(): 0);
                            signInCheckOn.setLeaveSickHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveSickHours(): 0);
                            signInCheckOn.setLeaveSickMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveSickMinutes(): 0);
                            signInCheckOn.setLeaveSickDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveSickDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveMarriageDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMarriageDayCount(): 0);
                            signInCheckOn.setLeaveMarriageHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMarriageHours(): 0);
                            signInCheckOn.setLeaveMarriageMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMarriageMinutes(): 0);
                            signInCheckOn.setLeaveMarriageDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMarriageDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveAnnualDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAnnualDayCount(): 0);
                            signInCheckOn.setLeaveAnnualHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAnnualHours(): 0);
                            signInCheckOn.setLeaveAnnualMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAnnualMinutes(): 0);
                            signInCheckOn.setLeaveAnnualDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAnnualDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveMaternityDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMaternityDayCount(): 0);
                            signInCheckOn.setLeaveMaternityHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMaternityHours(): 0);
                            signInCheckOn.setLeaveMaternityMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMaternityMinutes(): 0);
                            signInCheckOn.setLeaveMaternityDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMaternityDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveFuneralDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveFuneralDayCount(): 0);
                            signInCheckOn.setLeaveFuneralHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveFuneralHours(): 0);
                            signInCheckOn.setLeaveFuneralMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveFuneralMinutes(): 0);
                            signInCheckOn.setLeaveFuneralDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveFuneralDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveWorkInjuryDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveWorkInjuryDayCount(): 0);
                            signInCheckOn.setLeaveWorkInjuryHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveWorkInjuryHours(): 0);
                            signInCheckOn.setLeaveWorkInjuryMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveWorkInjuryMinutes(): 0);
                            signInCheckOn.setLeaveWorkInjuryDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveWorkInjuryDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveRotationRestDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveRotationRestDayCount(): 0);
                            signInCheckOn.setLeaveRotationRestHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveRotationRestHours(): 0);
                            signInCheckOn.setLeaveRotationRestMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveRotationRestMinutes(): 0);
                            signInCheckOn.setLeaveRotationRestDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveRotationRestDiscountMoney(): BigDecimal.ZERO);

                            signInCheckOn.setTotalDiscountMoney(Objects.nonNull(staffLeave) ? signInCheckOn.getTotalDiscountMoney().add(staffLeave.getTotalDiscountMoney())
                                    : signInCheckOn.getTotalDiscountMoney());
                            //旷工统计
                            SignInCheckOn staffAbsentee = absenteeMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setAbsenteeDayCount(Objects.nonNull(staffAbsentee) ? staffAbsentee.getAbsenteeDayCount(): 0);
                            signInCheckOn.setAbsenteeHours(Objects.nonNull(staffAbsentee) ? staffAbsentee.getAbsenteeHours(): 0);
                            signInCheckOn.setAbsenteeMinutes(Objects.nonNull(staffAbsentee) ? staffAbsentee.getAbsenteeMinutes(): 0);
                            signInCheckOn.setAbsenteeDiscountMoney(Objects.nonNull(staffAbsentee) ? staffAbsentee.getAbsenteeDiscountMoney(): BigDecimal.ZERO);

                            signInCheckOn.setTotalDiscountMoney(signInCheckOn.getTotalDiscountMoney().add(signInCheckOn.getAbsenteeDiscountMoney()));
                            //外勤统计
                            SignInCheckOn outwork = outworkMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setOutworkDayCount(Objects.nonNull(outwork) ? outwork.getOutworkDayCount(): 0);
                            signInCheckOn.setOutworkHours(Objects.nonNull(outwork) ? outwork.getOutworkHours(): 0);
                            signInCheckOn.setOutworkMinutes(Objects.nonNull(outwork) ? outwork.getOutworkMinutes(): 0);
                            //周末加班统计
                            SignInCheckOn staffWeekendOvertime = weekendOvertimeMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setWeekendOvertimeDataList(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOvertimeDataList() : new ArrayList<>());
                            signInCheckOn.setWeekendOverTimeDayCount(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOverTimeDayCount(): 0);
                            signInCheckOn.setWeekendOverTimeHours(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOverTimeHours(): 0);
                            signInCheckOn.setWeekendOverTimeMinutes(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOverTimeMinutes(): 0);
                            signInCheckOn.setWeekendOverTimeMoney(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOverTimeMoney(): BigDecimal.ZERO);
                            //平时加班，节假日加班
                            SignInCheckOn staffOvertime = overtimeMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setOvertimeDataList(Objects.nonNull(staffOvertime) ? staffOvertime.getOvertimeDataList() : new ArrayList<>());
                            signInCheckOn.setEightHourOverTimeDayCount(Objects.nonNull(staffOvertime) ? staffOvertime.getEightHourOverTimeDayCount(): 0);
                            signInCheckOn.setEightHourOverTimeHours(Objects.nonNull(staffOvertime) ? staffOvertime.getEightHourOverTimeHours(): 0);
                            signInCheckOn.setEightHourOverTimeMinutes(Objects.nonNull(staffOvertime) ? staffOvertime.getEightHourOverTimeMinutes(): 0);
                            signInCheckOn.setEightHourOverTimeMoney(Objects.nonNull(staffOvertime) ? staffOvertime.getEightHourOverTimeMoney(): BigDecimal.ZERO);
                            signInCheckOn.setHolidayOverTimeDayCount(Objects.nonNull(staffOvertime) ? staffOvertime.getHolidayOverTimeDayCount(): 0);
                            signInCheckOn.setHolidayOverTimeHours(Objects.nonNull(staffOvertime) ? staffOvertime.getHolidayOverTimeHours(): 0);
                            signInCheckOn.setHolidayOverTimeMinutes(Objects.nonNull(staffOvertime) ? staffOvertime.getHolidayOverTimeMinutes(): 0);
                            signInCheckOn.setHolidayOverTimeMoney(Objects.nonNull(staffOvertime) ? staffOvertime.getHolidayOverTimeMoney(): BigDecimal.ZERO);

                            baseBeanList.add(signInCheckOn);
                        }
                        pageForm.setList(baseBeanList);
                    }
                }
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("pageForm", pageForm);
                JSONObject obj = JSONObject.fromObject(resultMap);
                return obj.toString();
            }
        }
        return null;
    }

    @Override
    public List<SignInCheckOn> findCheckOnListByMonth(String year,String month,String startDate,String endDate,Map<String, SalaryData> salaryDataMap,String companyId) throws Exception {
        if(Objects.nonNull(companyId) && (Objects.nonNull(year) || Objects.nonNull(startDate))){
            String [] timeRange = new String[2];
            if(Objects.nonNull(year)){
                timeRange[0] = year+"-"+month+"-01";
                if(month.equals("12")){
                    timeRange[1] = (Integer.valueOf(year) + 1)+"-01-01";
                }else{
                    timeRange[1] = year+"-"+(Integer.valueOf(month) + 1)+"-01";
                }
            }else{
                timeRange[0] = startDate;
                timeRange[1] = endDate;
            }

            //查询员工数据
            PageForm pageForm = getListForSalarySettlement(companyId);
            if(Objects.nonNull(pageForm)){
                //填充数据
                List<SignInCheckOn> signInCheckOnList = new ArrayList<>();
                List<SignInCheckOn> checkOnList = new ArrayList<>();
                List<BaseBean> objectList = pageForm.getList();
                if(Objects.nonNull(objectList) && !objectList.isEmpty()){
                    for (Object obj : objectList) {
                        Object[] arr = (Object[]) obj;
                        SignInCheckOn signInCheckOn = new SignInCheckOn(arr[2].toString(),arr[0].toString(),null,arr[1].toString(),
                                arr[3].toString(),null,null,null);
                        checkOnList.add(signInCheckOn);
                    }
                    if(Objects.nonNull(checkOnList) && !checkOnList.isEmpty()){
                        //拿到员工ID List
                        List<String> staffIdList = new ArrayList<>();
                        for(SignInCheckOn signInCheckOn : checkOnList){
                            staffIdList.add(signInCheckOn.getStaffId());
                        }
                        //汇总员工的签到数据
                        Map<String,List<SignInResult>> signInFourResultMap = new HashMap<>();
                        //4次打卡
                        List<BaseBean> fourCheckOnList = findFourSignInListForSalarySettlement(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]), staffIdList,false,companyId);
                        signInCheck(fourCheckOnList,signInFourResultMap,false,companyId);
                        //过滤中午没有打两次卡的数据
                        List<BaseBean> afternoonCheckOnList = findAfternoonSignInListForSalarySettlement(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]), staffIdList,false,companyId);
                        Map<String,List<SignInResult>> signInResultMap = filterSignInCheck(afternoonCheckOnList,signInFourResultMap);
                        //2次打卡
                        List<BaseBean> objectCheckOnList = findSignInListForSalarySettlement(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]), staffIdList,false,companyId);
                        signInCheck(objectCheckOnList,signInResultMap,false,companyId);
                        //非普通班打卡
                        List<BaseBean> dutyList = dutyApplyService.findDutyList(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]),staffIdList,companyId);
                        List<BaseBean> nonOrdinarycheckOnList = findNonOrdinarySignInList(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]), staffIdList,false,companyId);
                        signInCheckNonOrdinary(nonOrdinarycheckOnList,dutyList,signInResultMap,false,companyId);
                        //增加不需要打卡的日期
                        signInNoNeed(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]),signInResultMap,companyId,staffIdList);
                        //月考勤天数
                        Double checkOnDaysPerMonth = findCheckOnDays(companyId,year,month);
                        //查询员工级别工资
                        salaryDataMap.putAll(findEmployeeSalary(staffIdList,companyId));

                        Map<String, SignInCheckOn>  signInCheckOnMap = new HashMap<>();
                        //对于有签到的员工做统计迟到，早退情况，如果没有签到，则是做旷工，请假统计
                        if(signInResultMap.size() > 0){
                            //计算员工的考勤数据，迟到，早退
                            signInCheckOnMap = computeCheckOnData(signInResultMap,staffIdList,companyId,MONTH,checkOnDaysPerMonth,salaryDataMap);
                        }
                        //计算员工的考勤数据，请假,暂时不统计今天的请假
                        Map<String, SignInCheckOn> leaveMap = computeLeaveData(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]),
                                staffIdList,companyId,checkOnDaysPerMonth,salaryDataMap,false);
                        //计算员工外勤，不统计今天的外勤
                        Map<String, SignInCheckOn> outworkMap = computeOutworkData(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]),
                                staffIdList,companyId,false);
                        //计算员工旷工，如果没有签到，没有请假，没有外勤则是旷工
                        Map<String, SignInCheckOn> absenteeMap = computeAbsenteeData(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]),
                                staffIdList,companyId,null,checkOnDaysPerMonth,salaryDataMap,signInResultMap,leaveMap,outworkMap,false);

                        //计算员工加班
                        //需要申请的加班，平时8小时以外，节假日加班(包含节假日的周6周天)
                        Map<String, SignInCheckOn> overtimeMap = computeOvertimeData(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]),
                                staffIdList,companyId,checkOnDaysPerMonth,salaryDataMap,signInResultMap,false);

                        //周6周天不用申请的加班，排除因调休需要上班的周6周天,排除节假日的周6周天
                        Map<String, SignInCheckOn> weekendOvertimeMap = computeWeekendOvertimeData(timeRange[0],getEndDate(BEFORE_MONTH,timeRange[1]),month,
                                staffIdList,companyId,null,checkOnDaysPerMonth,salaryDataMap,signInResultMap,false);

                        for(SignInCheckOn signInCheckOn : checkOnList){
                            signInCheckOn.setStartTime(timeRange[0]);
                            signInCheckOn.setEndTime(beforeDay(timeRange[1]));
                            signInCheckOn.setCheckOnTimeType(MONTH);
                            //员工签到数据
                            signInCheckOn.setSignInResultList(signInResultMap.get(signInCheckOn.getStaffId()));
                            SalaryData salaryData = salaryDataMap.get(signInCheckOn.getStaffId());
                            if(Objects.nonNull(salaryData)){
                                try {
                                    signInCheckOn.setBaseSalary(Objects.nonNull(salaryData.getBaseSalary()) ? Double.parseDouble(salaryData.getBaseSalary()) : null);
                                    signInCheckOn.setRoleSalary(Objects.nonNull(salaryData.getRoleSalary()) ? Double.parseDouble(salaryData.getRoleSalary()) : null);
                                    signInCheckOn.setDutySalary(Objects.nonNull(salaryData.getDutySalary()) ? Double.parseDouble(salaryData.getDutySalary()) : null);
                                    signInCheckOn.setCompeteSalary(Objects.nonNull(salaryData.getCompeteSalary()) ? Double.parseDouble(salaryData.getCompeteSalary()) : null);
                                    signInCheckOn.setSecrecySalary(Objects.nonNull(salaryData.getSecrecySalary()) ? Double.parseDouble(salaryData.getSecrecySalary()) : null);
                                    signInCheckOn.setLevelSalary(Objects.nonNull(salaryData.getLevelSalary()) ? Double.parseDouble(salaryData.getLevelSalary()) : null);
                                    signInCheckOn.setGuaranteeSalary(Objects.nonNull(salaryData.getGuaranteeSalary()) ? Double.parseDouble(salaryData.getGuaranteeSalary()) : null);
                                    signInCheckOn.setWelfareSalary(Objects.nonNull(salaryData.getWelfareSalary()) ? Double.parseDouble(salaryData.getWelfareSalary()) : null);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                signInCheckOn.setSalaryLevelId(salaryData.getSalaryLevelId());
                                signInCheckOn.setSalaryLevelNum(salaryData.getSalaryLevelNum());
                            }
                            //迟到早退统计
                            SignInCheckOn staffSignIn = signInCheckOnMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setLateCount(Objects.nonNull(staffSignIn) ? staffSignIn.getLateCount() : 0);
                            signInCheckOn.setLateMinutes(Objects.nonNull(staffSignIn) ? staffSignIn.getLateMinutes() : 0);
                            signInCheckOn.setLateDiscountMoney(Objects.nonNull(staffSignIn) ? staffSignIn.getLateDiscountMoney() : BigDecimal.ZERO);
                            signInCheckOn.setLeaveEarlyCount(Objects.nonNull(staffSignIn) ? staffSignIn.getLeaveEarlyCount() : 0);
                            signInCheckOn.setLeaveEarlyMinutes(Objects.nonNull(staffSignIn) ? staffSignIn.getLeaveEarlyMinutes() : 0);
                            signInCheckOn.setLeaveEarlyDiscountMoney(Objects.nonNull(staffSignIn) ? staffSignIn.getLeaveEarlyDiscountMoney() : BigDecimal.ZERO);
                            signInCheckOn.setTotalDiscountMoney(Objects.nonNull(staffSignIn) ? staffSignIn.getTotalDiscountMoney() : BigDecimal.ZERO);
                            //请假统计
                            SignInCheckOn staffLeave = leaveMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setLeaveDataList(Objects.nonNull(staffLeave) ? staffLeave.getLeaveDataList() : new ArrayList<>());
                            signInCheckOn.setLeaveAbsenceDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAbsenceDayCount(): 0);
                            signInCheckOn.setLeaveAbsenceHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAbsenceHours(): 0);
                            signInCheckOn.setLeaveAbsenceMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAbsenceMinutes(): 0);
                            signInCheckOn.setLeaveAbsenceDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAbsenceDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveSickDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveSickDayCount(): 0);
                            signInCheckOn.setLeaveSickHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveSickHours(): 0);
                            signInCheckOn.setLeaveSickMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveSickMinutes(): 0);
                            signInCheckOn.setLeaveSickDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveSickDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveMarriageDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMarriageDayCount(): 0);
                            signInCheckOn.setLeaveMarriageHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMarriageHours(): 0);
                            signInCheckOn.setLeaveMarriageMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMarriageMinutes(): 0);
                            signInCheckOn.setLeaveMarriageDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMarriageDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveAnnualDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAnnualDayCount(): 0);
                            signInCheckOn.setLeaveAnnualHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAnnualHours(): 0);
                            signInCheckOn.setLeaveAnnualMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAnnualMinutes(): 0);
                            signInCheckOn.setLeaveAnnualDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveAnnualDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveMaternityDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMaternityDayCount(): 0);
                            signInCheckOn.setLeaveMaternityHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMaternityHours(): 0);
                            signInCheckOn.setLeaveMaternityMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMaternityMinutes(): 0);
                            signInCheckOn.setLeaveMaternityDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveMaternityDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveFuneralDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveFuneralDayCount(): 0);
                            signInCheckOn.setLeaveFuneralHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveFuneralHours(): 0);
                            signInCheckOn.setLeaveFuneralMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveFuneralMinutes(): 0);
                            signInCheckOn.setLeaveFuneralDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveFuneralDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveWorkInjuryDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveWorkInjuryDayCount(): 0);
                            signInCheckOn.setLeaveWorkInjuryHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveWorkInjuryHours(): 0);
                            signInCheckOn.setLeaveWorkInjuryMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveWorkInjuryMinutes(): 0);
                            signInCheckOn.setLeaveWorkInjuryDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveWorkInjuryDiscountMoney(): BigDecimal.ZERO);
                            signInCheckOn.setLeaveRotationRestDayCount(Objects.nonNull(staffLeave) ? staffLeave.getLeaveRotationRestDayCount(): 0);
                            signInCheckOn.setLeaveRotationRestHours(Objects.nonNull(staffLeave) ? staffLeave.getLeaveRotationRestHours(): 0);
                            signInCheckOn.setLeaveRotationRestMinutes(Objects.nonNull(staffLeave) ? staffLeave.getLeaveRotationRestMinutes(): 0);
                            signInCheckOn.setLeaveRotationRestDiscountMoney(Objects.nonNull(staffLeave) ? staffLeave.getLeaveRotationRestDiscountMoney(): BigDecimal.ZERO);

                            signInCheckOn.setTotalDiscountMoney(Objects.nonNull(staffLeave) ? signInCheckOn.getTotalDiscountMoney().add(staffLeave.getTotalDiscountMoney())
                                    : signInCheckOn.getTotalDiscountMoney());
                            //旷工统计
                            SignInCheckOn staffAbsentee = absenteeMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setAbsenteeDayCount(Objects.nonNull(staffAbsentee) ? staffAbsentee.getAbsenteeDayCount(): 0);
                            signInCheckOn.setAbsenteeHours(Objects.nonNull(staffAbsentee) ? staffAbsentee.getAbsenteeHours(): 0);
                            signInCheckOn.setAbsenteeMinutes(Objects.nonNull(staffAbsentee) ? staffAbsentee.getAbsenteeMinutes(): 0);
                            signInCheckOn.setAbsenteeDiscountMoney(Objects.nonNull(staffAbsentee) ? staffAbsentee.getAbsenteeDiscountMoney(): BigDecimal.ZERO);

                            signInCheckOn.setTotalDiscountMoney(signInCheckOn.getTotalDiscountMoney().add(signInCheckOn.getAbsenteeDiscountMoney()));
                            //外勤统计
                            SignInCheckOn outwork = outworkMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setOutworkDayCount(Objects.nonNull(outwork) ? outwork.getOutworkDayCount(): 0);
                            signInCheckOn.setOutworkHours(Objects.nonNull(outwork) ? outwork.getOutworkHours(): 0);
                            signInCheckOn.setOutworkMinutes(Objects.nonNull(outwork) ? outwork.getOutworkMinutes(): 0);
                            //周末加班统计
                            SignInCheckOn staffWeekendOvertime = weekendOvertimeMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setWeekendOvertimeDataList(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOvertimeDataList() : new ArrayList<>());
                            signInCheckOn.setWeekendOverTimeDayCount(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOverTimeDayCount(): 0);
                            signInCheckOn.setWeekendOverTimeHours(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOverTimeHours(): 0);
                            signInCheckOn.setWeekendOverTimeMinutes(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOverTimeMinutes(): 0);
                            signInCheckOn.setWeekendOverTimeMoney(Objects.nonNull(staffWeekendOvertime) ? staffWeekendOvertime.getWeekendOverTimeMoney(): BigDecimal.ZERO);
                            //平时加班，节假日加班
                            SignInCheckOn staffOvertime = overtimeMap.get(signInCheckOn.getStaffId());
                            signInCheckOn.setOvertimeDataList(Objects.nonNull(staffOvertime) ? staffOvertime.getOvertimeDataList() : new ArrayList<>());
                            signInCheckOn.setEightHourOverTimeDayCount(Objects.nonNull(staffOvertime) ? staffOvertime.getEightHourOverTimeDayCount(): 0);
                            signInCheckOn.setEightHourOverTimeHours(Objects.nonNull(staffOvertime) ? staffOvertime.getEightHourOverTimeHours(): 0);
                            signInCheckOn.setEightHourOverTimeMinutes(Objects.nonNull(staffOvertime) ? staffOvertime.getEightHourOverTimeMinutes(): 0);
                            signInCheckOn.setEightHourOverTimeMoney(Objects.nonNull(staffOvertime) ? staffOvertime.getEightHourOverTimeMoney(): BigDecimal.ZERO);
                            signInCheckOn.setHolidayOverTimeDayCount(Objects.nonNull(staffOvertime) ? staffOvertime.getHolidayOverTimeDayCount(): 0);
                            signInCheckOn.setHolidayOverTimeHours(Objects.nonNull(staffOvertime) ? staffOvertime.getHolidayOverTimeHours(): 0);
                            signInCheckOn.setHolidayOverTimeMinutes(Objects.nonNull(staffOvertime) ? staffOvertime.getHolidayOverTimeMinutes(): 0);
                            signInCheckOn.setHolidayOverTimeMoney(Objects.nonNull(staffOvertime) ? staffOvertime.getHolidayOverTimeMoney(): BigDecimal.ZERO);

                            signInCheckOnList.add(signInCheckOn);
                        }
                    }
                }
                return signInCheckOnList;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 迟到，早退数据存入session
     * @param signInResultMap
     */
    private void saveToSessionSignIn(Map<String,List<SignInResult>> signInResultMap){
        //迟到，早退过滤
        Map<String,List<SignInResult>> signInLateMap = new HashMap<>();
        Map<String,List<SignInResult>> signInLeaveEarlyMap = new HashMap<>();
        for(Map.Entry<String, List<SignInResult>> signInResultMapEntry : signInResultMap.entrySet() ){
            List<SignInResult> signInLateResultList = new ArrayList<>();
            List<SignInResult> signInLeaveEarlyResultList = new ArrayList<>();
            List<SignInResult> signInResultList = signInResultMapEntry.getValue();
            for(SignInResult signInResult : signInResultList){
                if(signInResult.getLateSeconds() > 0){
                    signInResult.setLateMinutes(new BigDecimal(signInResult.getLateSeconds())
                            .divide(BigDecimal.valueOf(60),2,RoundingMode.HALF_UP).doubleValue());
                    signInLateResultList.add(signInResult);
                }
                if(signInResult.getLeaveEarlySeconds() > 0){
                    signInResult.setLeaveEarlyMinutes(new BigDecimal(signInResult.getLeaveEarlySeconds())
                            .divide(BigDecimal.valueOf(60),2,RoundingMode.HALF_UP).doubleValue());
                    signInLeaveEarlyResultList.add(signInResult);
                }
            }
            if(signInLateResultList.size() > 0){
                signInLateMap.put(signInResultMapEntry.getKey(),signInLateResultList);
            }
            if(signInLeaveEarlyResultList.size() > 0){
                signInLeaveEarlyMap.put(signInResultMapEntry.getKey(),signInLeaveEarlyResultList);
            }
        }
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("signInLateMap",signInLateMap);
        session.put("signInLeaveEarlyMap",signInLeaveEarlyMap);
        ActionContext.getContext().setSession(session);
    }

    /**
     * 请假数据存入session
     * @param leaveMap
     */
    private void saveToSessionLeave(Map<String, List<LeaveData>> leaveMap){
        Map<String,List<LeaveData>> absenceLeaveMap = new HashMap<>();
        Map<String,List<LeaveData>> sickLeaveMap = new HashMap<>();
        Map<String,List<LeaveData>> marriageLeaveMap = new HashMap<>();
        Map<String,List<LeaveData>> annualLeaveMap = new HashMap<>();
        Map<String,List<LeaveData>> maternityLeaveMap = new HashMap<>();
        Map<String,List<LeaveData>> funeralLeaveMap = new HashMap<>();
        Map<String,List<LeaveData>> workinjuryLeaveMap = new HashMap<>();
        Map<String,List<LeaveData>> rotationrestLeaveMap = new HashMap<>();
        for(Map.Entry<String, List<LeaveData>> checkOnLeaveMapEntry : leaveMap.entrySet()){
            List<LeaveData> absenceLeaveList = new ArrayList<>();
            List<LeaveData> sickLeaveList = new ArrayList<>();
            List<LeaveData> marriageLeaveList = new ArrayList<>();
            List<LeaveData> annualLeaveList = new ArrayList<>();
            List<LeaveData> maternityLeaveList = new ArrayList<>();
            List<LeaveData> funeralLeaveList = new ArrayList<>();
            List<LeaveData> workinjuryLeaveList = new ArrayList<>();
            List<LeaveData> rotationrestLeaveList = new ArrayList<>();
            List<LeaveData> leaveDataList = checkOnLeaveMapEntry.getValue();
            if(leaveDataList.size() > 0){
                for(LeaveData leaveData : leaveDataList) {
                    if (LEAVE_ABSENCE.equals(leaveData.getLeaveType())) {
                        absenceLeaveList.add(leaveData);
                    } else if (LEAVE_SICK.equals(leaveData.getLeaveType())) {
                        sickLeaveList.add(leaveData);
                    } else if (LEAVE_MARRIAGE.equals(leaveData.getLeaveType())) {
                        marriageLeaveList.add(leaveData);
                    } else if (LEAVE_ANNUAL.equals(leaveData.getLeaveType())) {
                        annualLeaveList.add(leaveData);
                    } else if (LEAVE_MATERNITY.equals(leaveData.getLeaveType())) {
                        maternityLeaveList.add(leaveData);
                    } else if (LEAVE_FUNERAL.equals(leaveData.getLeaveType())) {
                        funeralLeaveList.add(leaveData);
                    } else if (LEAVE_WORKINJURY.equals(leaveData.getLeaveType())) {
                        workinjuryLeaveList.add(leaveData);
                    } else if (LEAVE_ROTATIONREST.equals(leaveData.getLeaveType())) {
                        rotationrestLeaveList.add(leaveData);
                    }
                }
                absenceLeaveMap.put(checkOnLeaveMapEntry.getKey(),absenceLeaveList);
                sickLeaveMap.put(checkOnLeaveMapEntry.getKey(),sickLeaveList);
                marriageLeaveMap.put(checkOnLeaveMapEntry.getKey(),marriageLeaveList);
                annualLeaveMap.put(checkOnLeaveMapEntry.getKey(),annualLeaveList);
                maternityLeaveMap.put(checkOnLeaveMapEntry.getKey(),maternityLeaveList);
                funeralLeaveMap.put(checkOnLeaveMapEntry.getKey(),funeralLeaveList);
                workinjuryLeaveMap.put(checkOnLeaveMapEntry.getKey(),workinjuryLeaveList);
                rotationrestLeaveMap.put(checkOnLeaveMapEntry.getKey(),rotationrestLeaveList);
            }
        }
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("absenceLeaveMap",absenceLeaveMap);
        session.put("sickLeaveMap",sickLeaveMap);
        session.put("marriageLeaveMap",marriageLeaveMap);
        session.put("annualLeaveMap",annualLeaveMap);
        session.put("maternityLeaveMap",maternityLeaveMap);
        session.put("funeralLeaveMap",funeralLeaveMap);
        session.put("workinjuryLeaveMap",workinjuryLeaveMap);
        session.put("rotationrestLeaveMap",rotationrestLeaveMap);
        ActionContext.getContext().setSession(session);
    }

    /**
     * 外勤数据存入session
     * @param outworkMap
     */
    private void saveToSessionOutwork(Map<String, List<LeaveData>> outworkMap){
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("outworkMap",outworkMap);
        ActionContext.getContext().setSession(session);
    }

    /**
     * 加班数据存入session
     * @param overtimeMap
     */
    private void saveToSessionOvertime(Map<String, List<OvertimeData>> overtimeMap){
        Map<String,List<OvertimeData>> eightHourMap = new HashMap<>();
        Map<String,List<OvertimeData>> holidayMap = new HashMap<>();
        for(Map.Entry<String, List<OvertimeData>> checkOnOvertimeMapEntry : overtimeMap.entrySet()){
            List<OvertimeData> eightHourList = new ArrayList<>();
            List<OvertimeData> holidayList = new ArrayList<>();
            List<OvertimeData> overtimeDataList = checkOnOvertimeMapEntry.getValue();
            if(overtimeDataList.size() > 0){
                for(OvertimeData overtimeData : overtimeDataList) {
                    if (EIGHT_HOUR_OVERTIME.equals(overtimeData.getOvertimeType())) {
                        eightHourList.add(overtimeData);
                    } else if (HOLIDAY_OVERTIME.equals(overtimeData.getOvertimeType())) {
                        holidayList.add(overtimeData);
                    }
                }
                eightHourMap.put(checkOnOvertimeMapEntry.getKey(),eightHourList);
                holidayMap.put(checkOnOvertimeMapEntry.getKey(),holidayList);
            }
        }
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("eightHourMap",eightHourMap);
        session.put("holidayMap",holidayMap);
        ActionContext.getContext().setSession(session);
    }

    /**
     * 旷工数据存入session
     * @param absenteeMap
     */
    private void saveToSessionAbsentee(Map<String,List<String>> absenteeMap){
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("absenteeMap",absenteeMap);
        ActionContext.getContext().setSession(session);
    }

    /**
     * 加班数据存入session
     * @param weekendOvertimeMap
     */
    private void saveToSessionWeekendOvertime(Map<String,List<String>> weekendOvertimeMap){
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("weekendOvertimeMap",weekendOvertimeMap);
        ActionContext.getContext().setSession(session);
    }

    public String getDetailFromSession(String detailType,SignInCheckOn signInCheckOn){
        String staffId = signInCheckOn.getStaffId();
        List<CheckOnResult> checkOnResultList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        if(LATE.equals(detailType)){
            Map<String,List<SignInResult>> signInLateMap = (Map<String,List<SignInResult>>)session.get("signInLateMap");
            List<SignInResult> signInResultList = signInLateMap.get(staffId);
            if(Objects.nonNull(signInResultList)){
                for(SignInResult signInResult : signInResultList){
                    CheckOnResult checkOnResult = new CheckOnResult();
                    checkOnResult.setStaffId(staffId);
                    checkOnResult.setCheckOnType(LATE);
                    checkOnResult.setCheckOnDate(signInResult.getSignInDate());
                    checkOnResult.setSignInTime(dateTimeFormat.format(signInResult.getSignInTime()));
                    checkOnResult.setMinutes(signInResult.getLateMinutes());
                    checkOnResultList.add(checkOnResult);
                }
            }
        }else if(LEAVE_EARLY.equals(detailType)){
            Map<String,List<SignInResult>> signInLeaveEarlyMap = (Map<String,List<SignInResult>>)session.get("signInLeaveEarlyMap");
            List<SignInResult> signInResultList = signInLeaveEarlyMap.get(staffId);
            if(Objects.nonNull(signInResultList)){
                for(SignInResult signInResult : signInResultList){
                    CheckOnResult checkOnResult = new CheckOnResult();
                    checkOnResult.setStaffId(staffId);
                    checkOnResult.setCheckOnType(LEAVE_EARLY);
                    checkOnResult.setCheckOnDate(signInResult.getSignInDate());
                    checkOnResult.setSignOutTime(dateTimeFormat.format(signInResult.getSignOutTime()));
                    checkOnResult.setMinutes(signInResult.getLeaveEarlyMinutes());
                    checkOnResultList.add(checkOnResult);
                }
            }
        }else if(LEAVE.equals(detailType)){
            loadLeaveData(session,staffId,checkOnResultList);
        }else if(ABSENTEE.equals(detailType)){
            List<CheckOnResult> leaveDataList = new ArrayList<>();
            loadLeaveData(session,staffId,leaveDataList);
            Double absenceHours = new Double(0);
            Map<String,List<String>> absenceLeaveMap = (Map<String,List<String>>)session.get("absenteeMap");
            List<String> absenceLeaveList = absenceLeaveMap.get(staffId);
            if(Objects.nonNull(absenceLeaveList)){
                for(String absenceLeaveDate : absenceLeaveList){
                    Double leaveHours = new Double(0);
                    for(CheckOnResult checkOnResult : leaveDataList){
                        if(checkOnResult.getCheckOnDate().equals(absenceLeaveDate)){
                            leaveHours += checkOnResult.getHours();
                        }
                    }
                    absenceHours = new BigDecimal(HOUR_WORK_DAY).subtract(new BigDecimal(leaveHours)).setScale(2,RoundingMode.HALF_UP).doubleValue();
                    if(absenceHours > 0){
                        CheckOnResult checkOnResult = new CheckOnResult();
                        checkOnResult.setStaffId(staffId);
                        checkOnResult.setCheckOnType(ABSENTEE);
                        checkOnResult.setCheckOnDate(absenceLeaveDate);
                        checkOnResult.setHours(absenceHours);
                        checkOnResultList.add(checkOnResult);
                    }
                }
            }
        }else if(WEEKEND_OVERTIME.equals(detailType)){
            Map<String,List<String>> weekendOvertimeMap = (Map<String,List<String>>)session.get("weekendOvertimeMap");
            List<String> weekendOvertimeList = weekendOvertimeMap.get(staffId);
            if(Objects.nonNull(weekendOvertimeList)){
                for(String weekendOvertimeDate : weekendOvertimeList){
                    CheckOnResult checkOnResult = new CheckOnResult();
                    checkOnResult.setStaffId(staffId);
                    checkOnResult.setCheckOnType(WEEKEND_OVERTIME);
                    checkOnResult.setCheckOnDate(weekendOvertimeDate);
                    checkOnResult.setHours(HOUR_WORK_DAY.doubleValue());
                    checkOnResultList.add(checkOnResult);
                }
            }
        }else if(OUTWORK.equals(detailType)){
            Map<String,List<LeaveData>> outworkMap = (Map<String,List<LeaveData>>)session.get("outworkMap");
            List<LeaveData> outworkList = outworkMap.get(staffId);
            if(Objects.nonNull(outworkList)){
                for(LeaveData outworkData : outworkList){
                    CheckOnResult checkOnResult = new CheckOnResult();
                    checkOnResult.setStaffId(staffId);
                    checkOnResult.setCheckOnType(OUTWORK);
                    checkOnResult.setCheckOnDate(outworkData.getLeaveDate());
                    checkOnResult.setHours(outworkData.getLeaveHours());
                    checkOnResultList.add(checkOnResult);
                }
            }
        }else if(EIGHT_HOUR_OVERTIME.equals(detailType)){
            Map<String,List<OvertimeData>> eightHourOvertimeMap = (Map<String,List<OvertimeData>>)session.get("eightHourMap");
            List<OvertimeData> eightHourOvertimeList = eightHourOvertimeMap.get(staffId);
            if(Objects.nonNull(eightHourOvertimeList)){
                for(OvertimeData eightHourOvertimeData : eightHourOvertimeList){
                    CheckOnResult checkOnResult = new CheckOnResult();
                    checkOnResult.setStaffId(staffId);
                    checkOnResult.setCheckOnType(EIGHT_HOUR_OVERTIME);
                    checkOnResult.setCheckOnDate(eightHourOvertimeData.getOvertimeDate());
                    checkOnResult.setHours(eightHourOvertimeData.getOvertimeHours());
                    checkOnResultList.add(checkOnResult);
                }
            }
        }else if(HOLIDAY_OVERTIME.equals(detailType)){
            Map<String,List<OvertimeData>> holidayOvertimeMap = (Map<String,List<OvertimeData>>)session.get("holidayMap");
            List<OvertimeData> holidayOvertimeList = holidayOvertimeMap.get(staffId);
            if(Objects.nonNull(holidayOvertimeList)){
                for(OvertimeData holidayOvertimeData : holidayOvertimeList){
                    CheckOnResult checkOnResult = new CheckOnResult();
                    checkOnResult.setStaffId(staffId);
                    checkOnResult.setCheckOnType(HOLIDAY_OVERTIME);
                    checkOnResult.setCheckOnDate(holidayOvertimeData.getOvertimeDate());
                    checkOnResult.setHours(holidayOvertimeData.getOvertimeHours());
                    checkOnResultList.add(checkOnResult);
                }
            }
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("detailList", checkOnResultList);
        JSONObject obj = JSONObject.fromObject(resultMap);
        return obj.toString();
    }

    private void leaveData(List<LeaveData> leaveDataList,String staffId,List<CheckOnResult> checkOnResultList,String checkOnType){
        if(Objects.nonNull(leaveDataList)){
            for(LeaveData leaveData : leaveDataList){
                CheckOnResult checkOnResult = new CheckOnResult();
                checkOnResult.setStaffId(staffId);
                checkOnResult.setCheckOnType(checkOnType);
                checkOnResult.setCheckOnDate(leaveData.getLeaveDate());
                checkOnResult.setHours(leaveData.getLeaveHours());
                checkOnResultList.add(checkOnResult);
            }
        }
    }

    private void loadLeaveData(Map<String, Object> session,String staffId,List<CheckOnResult> checkOnDataList){
        Map<String,List<LeaveData>> absenceLeaveMap = (Map<String,List<LeaveData>>)session.get("absenceLeaveMap");
        Map<String,List<LeaveData>> sickLeaveMap = (Map<String,List<LeaveData>>)session.get("sickLeaveMap");
        Map<String,List<LeaveData>> marriageLeaveMap = (Map<String,List<LeaveData>>)session.get("marriageLeaveMap");
        Map<String,List<LeaveData>> annualLeaveMap = (Map<String,List<LeaveData>>)session.get("annualLeaveMap");
        Map<String,List<LeaveData>> maternityLeaveMap = (Map<String,List<LeaveData>>)session.get("maternityLeaveMap");
        Map<String,List<LeaveData>> funeralLeaveMap = (Map<String,List<LeaveData>>)session.get("funeralLeaveMap");
        Map<String,List<LeaveData>> workinjuryLeaveMap = (Map<String,List<LeaveData>>)session.get("workinjuryLeaveMap");
        Map<String,List<LeaveData>> rotationrestLeaveMap = (Map<String,List<LeaveData>>)session.get("rotationrestLeaveMap");
        leaveData(absenceLeaveMap.get(staffId),staffId,checkOnDataList,LEAVE_ABSENCE);
        leaveData(sickLeaveMap.get(staffId),staffId,checkOnDataList,LEAVE_SICK);
        leaveData(marriageLeaveMap.get(staffId),staffId,checkOnDataList,LEAVE_MARRIAGE);
        leaveData(annualLeaveMap.get(staffId),staffId,checkOnDataList,LEAVE_ANNUAL);
        leaveData(maternityLeaveMap.get(staffId),staffId,checkOnDataList,LEAVE_MATERNITY);
        leaveData(funeralLeaveMap.get(staffId),staffId,checkOnDataList,LEAVE_FUNERAL);
        leaveData(workinjuryLeaveMap.get(staffId),staffId,checkOnDataList,LEAVE_WORKINJURY);
        leaveData(rotationrestLeaveMap.get(staffId),staffId,checkOnDataList,LEAVE_ROTATIONREST);
    }

    @Override
    public String findCheckOnSetupById(String id) {
        CheckOnSetup checkOnSetup =  (CheckOnSetup) baseBeanService
                .getBeanByHqlAndParams("from CheckOnSetup where checkOnSetupId = ?", new Object[]{id});
        if(Objects.nonNull(checkOnSetup)){
            String staffId = checkOnSetup.getStaffId();
            if(Objects.nonNull(staffId)){
                Staff staff =  (Staff) baseBeanService
                        .getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffId});
                checkOnSetup.setStaffName(staff.getStaffName());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("checkOnSetup", checkOnSetup);
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
    public String findCheckOnType() {
        PageForm pageForm =  baseBeanService.getPageForm(1,1000,"from CheckOnType order by checkOnTypeSerial",new Object[]{});
        if(Objects.nonNull(pageForm)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("checkOnType", pageForm);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    /**
     * 查询请假类别
     * @return
     */
    @Override
    public String findLeaveCheckOnType() {
        PageForm pageForm =  baseBeanService.getPageForm(1,1000,"from CheckOnType where checkOnTypeName LIKE '%假' order by checkOnTypeSerial",new Object[]{});
        if(Objects.nonNull(pageForm)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("checkOnType", pageForm);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    @Override
    public String findSalaryLevelByCompanyID() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            PageForm pageForm =  baseBeanService.getPageForm(1,1000,"from HrSalaryLevel where companyId = ?" +
                            " order by salaryLevelNum",new Object[]{account.getCompanyID()});
            if(Objects.nonNull(pageForm)){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("salaryLevel", pageForm);
                JSONObject obj = JSONObject.fromObject(map);
                return obj.toString();
            }
        }
        return null;
    }

    private PageForm getList(PageForm pageForm,String checkOnTimeType,String employee) {
        String sql = getDataUseSql();
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.nonNull(account)) {
            if(Objects.nonNull(account.getCompanyID()) && StringUtils.isNotBlank(account.getCompanyID()))
            {
                paramList.add(account.getCompanyID());
            }
            if(!ALL.equals(employee)){
                paramList.add(account.getStaffID());
            }else{
                sql = sql.replace("AND ST.STAFFID = ?","");
            }
        }else{
            return null;
        }

        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,
                "SELECT count(1) FROM (select count(1) " + sql.substring(sql.indexOf("FROM")) + ") A",paramList.toArray());
        return pageForm;
    }

    private PageForm getListForSalarySettlement(String companyId) {
        String sql = getDataUseSql();
        List<Object> paramList = new ArrayList<>();
        paramList.add(companyId);
        sql = sql.replace("AND ST.STAFFID = ?","");
        PageForm pageForm = baseBeanService.getPageFormBySQL (1,1000, sql,
                "SELECT count(1) FROM (select count(1) " + sql.substring(sql.indexOf("FROM")) + ") A",paramList.toArray());
        return pageForm;
    }

    private String getDataUseSql() {
        String sql = "SELECT S.ACCOUNT,C.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "FROM DTCOS C,DT_HR_STAFF ST,DT_WFJ_SIGN S " +
                "WHERE ST.STAFFID = C.STAFFID AND S.account = ST.reference " +
                "AND C.COMPANYID = S.COMPANYID " +
                "AND C.COMPANYID = ? " +
                "AND S.signdate >= trunc(CURRENT_DATE - 180) " +
                "AND ST.STAFFID = ? " +
                "AND C.COSSTATUS = '50' " +
                "GROUP BY S.ACCOUNT,C.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "ORDER BY ST.STAFFID";
        return sql;
    }

    /**
     * 查询员工签到数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @return
     */
    private List findSignInList(String startDate,String endDate, List<String> staffIdList,boolean isToday){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("SELECT S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME,MIN(S.SIGNDATE),MAX(S.SIGNDATE) " +
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
                "AND S.SIGNDATE < TO_DATE('"+SIGN_IN_COUNT_CHANGE_DATE+"', 'YYYY-MM-DD') " +
                (StringUtils.isNotBlank(startDate) ? "AND S.signdate >= TO_DATE(?, 'YYYY-MM-DD') AND S.signdate < TO_DATE(?, 'YYYY-MM-DD') " : "") +
                "AND ((S.SIGNTYPE != '00' AND ST.STAFFID = ?) " +
                "OR (S.SIGNTYPE = '00' AND ST.STAFFID != ?) OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
//                "AND ((S.SIGNTYPE = '00' AND S.STATUS= 'T') OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
                "GROUP BY TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "HAVING count(1) >= "+SIGN_IN_COUNT_2+" " +
                "ORDER BY ST.STAFFNAME,MIN(S.SIGNDATE)");
        String sql = sb.toString();
        if(isToday){
            sql = sql.replace("HAVING count(1) >= " + SIGN_IN_COUNT_2,"HAVING count(1) >= 1");
        }
        if(Objects.nonNull(account.getCompanyID()) && StringUtils.isNotBlank(account.getCompanyID()))
        {
            paramList.add(account.getCompanyID());
        }
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        paramList.add(NOT_SIGN_IN_PHONE);
        paramList.add(NOT_SIGN_IN_PHONE);
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

    /**
     * 查询员工非普通班签到数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @return
     */
    private List findNonOrdinarySignInList(String startDate,String endDate, List<String> staffIdList,boolean isToday,String companyId){
        List<Object> paramList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME,MIN(S.SIGNDATE),MAX(S.SIGNDATE) " +
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
                (StringUtils.isNotBlank(startDate) ? "AND S.signdate >= TO_DATE(?, 'YYYY-MM-DD') AND S.signdate < TO_DATE(?, 'YYYY-MM-DD') " : "") +
                "AND ((S.SIGNTYPE = '00' AND S.STATUS= 'T') OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
                "GROUP BY TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "HAVING count(1) >= "+SIGN_IN_COUNT_2+" " +
                "ORDER BY ST.STAFFNAME,MIN(S.SIGNDATE)");
        String sql = sb.toString();
        if(isToday){
            sql = sql.replace("HAVING count(1) >= " + SIGN_IN_COUNT_2,"HAVING count(1) >= 1");
        }
        if(Objects.nonNull(companyId) && StringUtils.isNotBlank(companyId))
        {
            paramList.add(companyId);
        }
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

    /**
     * 查询4次打卡数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param isToday
     * @return
     */
    private List findFourSignInList(String startDate,String endDate, List<String> staffIdList,boolean isToday){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("SELECT S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME,MIN(S.SIGNDATE),MAX(S.SIGNDATE) " +
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
                "AND S.SIGNDATE >= TO_DATE('"+SIGN_IN_COUNT_CHANGE_DATE+"', 'YYYY-MM-DD') " +
                (StringUtils.isNotBlank(startDate) ? "AND S.signdate >= TO_DATE(?, 'YYYY-MM-DD') AND S.signdate < TO_DATE(?, 'YYYY-MM-DD') " : "") +
                "AND ((S.SIGNTYPE = '00' AND S.STATUS= 'T') OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
                "GROUP BY TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "HAVING count(1) >= "+SIGN_IN_COUNT_4+" " +
                "ORDER BY ST.STAFFNAME,MIN(S.SIGNDATE)");
        String sql = sb.toString();
        if(isToday){
            sql = sql.replace("HAVING count(1) >= " + SIGN_IN_COUNT_4,"HAVING count(1) >= 1");
        }
        if(Objects.nonNull(account.getCompanyID()) && StringUtils.isNotBlank(account.getCompanyID()))
        {
            paramList.add(account.getCompanyID());
        }
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

    /**
     * 查询中午打卡数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param isToday
     * @return
     */
    private List findAfternoonSignInList(String startDate,String endDate, List<String> staffIdList,boolean isToday){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }
        StringBuilder sb = new StringBuilder("SELECT S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME,MIN(S.SIGNDATE),MAX(S.SIGNDATE) " +
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
                "AND S.SIGNDATE >= TO_DATE('"+SIGN_IN_COUNT_CHANGE_DATE+"', 'YYYY-MM-DD') " +
                "AND S.SIGNDATE >= TO_DATE(CONCAT(TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),' " + MORNING_SIGN_OUT_TIME + ":00'),'YYYY-MM-DD HH24:MI:SS') " +
                "AND S.SIGNDATE <= TO_DATE(CONCAT(TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),' " + AFTERNOON_SIGN_IN_TIME + ":00'),'YYYY-MM-DD HH24:MI:SS') " +
                (StringUtils.isNotBlank(startDate) ? "AND S.signdate >= TO_DATE(?, 'YYYY-MM-DD') AND S.signdate < TO_DATE(?, 'YYYY-MM-DD') " : "") +
                "AND ((S.SIGNTYPE = '00' AND S.STATUS= 'T') OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
                "GROUP BY TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "HAVING count(1) >= "+SIGN_IN_COUNT_2+" " +
                "ORDER BY ST.STAFFNAME,MIN(S.SIGNDATE)");
        String sql = sb.toString();
        if(Objects.nonNull(account.getCompanyID()) && StringUtils.isNotBlank(account.getCompanyID()))
        {
            paramList.add(account.getCompanyID());
        }
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

    private List findSignInListForSalarySettlement(String startDate,String endDate, List<String> staffIdList,boolean isToday,String companyId){
        List<Object> paramList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME,MIN(S.SIGNDATE),MAX(S.SIGNDATE) " +
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
                "AND S.SIGNDATE < TO_DATE('"+SIGN_IN_COUNT_CHANGE_DATE+"', 'YYYY-MM-DD') " +
                (StringUtils.isNotBlank(startDate) ? "AND S.signdate >= TO_DATE(?, 'YYYY-MM-DD') AND S.signdate < TO_DATE(?, 'YYYY-MM-DD') " : "") +
//                "AND ((S.SIGNTYPE = '00' AND S.STATUS= 'T') OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
                "AND ((S.SIGNTYPE != '00' AND ST.STAFFID = ?) " +
                "OR (S.SIGNTYPE = '00' AND ST.STAFFID != ?) OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
                "GROUP BY TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "HAVING count(1) >= "+SIGN_IN_COUNT_2+" " +
                "ORDER BY ST.STAFFNAME,MIN(S.SIGNDATE)");
        String sql = sb.toString();
        if(isToday){
            sql = sql.replace("HAVING count(1) >= " + SIGN_IN_COUNT_2,"HAVING count(1) >= 1");
        }
        paramList.add(companyId);
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        paramList.add(NOT_SIGN_IN_PHONE);
        paramList.add(NOT_SIGN_IN_PHONE);
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

    /**
     * 查询4次打卡数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param isToday
     * @param companyId
     * @return
     */
    private List findFourSignInListForSalarySettlement(String startDate,String endDate, List<String> staffIdList,boolean isToday,String companyId){
        List<Object> paramList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME,MIN(S.SIGNDATE),MAX(S.SIGNDATE) " +
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
                "AND S.SIGNDATE >= TO_DATE('"+SIGN_IN_COUNT_CHANGE_DATE+"', 'YYYY-MM-DD') " +
                (StringUtils.isNotBlank(startDate) ? "AND S.signdate >= TO_DATE(?, 'YYYY-MM-DD') AND S.signdate < TO_DATE(?, 'YYYY-MM-DD') " : "") +
                "AND ((S.SIGNTYPE = '00' AND S.STATUS= 'T') OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
                "GROUP BY TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "HAVING count(1) >= "+SIGN_IN_COUNT_4+" " +
                "ORDER BY ST.STAFFNAME,MIN(S.SIGNDATE)");
        String sql = sb.toString();
        if(isToday){
            sql = sql.replace("HAVING count(1) >= " + SIGN_IN_COUNT_4,"HAVING count(1) >= 1");
        }
        paramList.add(companyId);
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

    /**
     * 查询中午打卡数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param isToday
     * @param companyId
     * @return
     */
    private List findAfternoonSignInListForSalarySettlement(String startDate,String endDate, List<String> staffIdList,boolean isToday,String companyId){
        List<Object> paramList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME,MIN(S.SIGNDATE),MAX(S.SIGNDATE) " +
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
                "AND S.SIGNDATE >= TO_DATE('"+SIGN_IN_COUNT_CHANGE_DATE+"', 'YYYY-MM-DD') " +
                "AND S.SIGNDATE >= TO_DATE(CONCAT(TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),' " + MORNING_SIGN_OUT_TIME + ":00'),'YYYY-MM-DD HH24:MI:SS') " +
                "AND S.SIGNDATE <= TO_DATE(CONCAT(TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),' " + AFTERNOON_SIGN_IN_TIME + ":00'),'YYYY-MM-DD HH24:MI:SS') " +
                (StringUtils.isNotBlank(startDate) ? "AND S.signdate >= TO_DATE(?, 'YYYY-MM-DD') AND S.signdate < TO_DATE(?, 'YYYY-MM-DD') " : "") +
                "AND ((S.SIGNTYPE = '00' AND S.STATUS= 'T') OR S.SIGNTYPE = '01' OR S.SIGNTYPE IS NULL) " +
                "GROUP BY TO_CHAR(S.SIGNDATE, 'YYYY-MM-DD'),S.SCCID,S.ACCOUNT,S.COMPANYID,ST.STAFFID,ST.STAFFNAME " +
                "HAVING count(1) >= "+SIGN_IN_COUNT_2+" " +
                "ORDER BY ST.STAFFNAME,MIN(S.SIGNDATE)");
        String sql = sb.toString();
        paramList.add(companyId);
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

    /**
     * 查询请假数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @return
     */
    private List findLeaveData(String startDate,String endDate, List<String> staffIdList){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }

        StringBuilder sb = new StringBuilder("SELECT ID,STATUS,STAFFID,STAFFNAME,LEAVESTARTDATE,LEAVEENDDATE,LEAVETYPE " +
                "FROM DT_MYLeave " +
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
                (StringUtils.isNotBlank(startDate) ? "AND TO_DATE(LEAVESTARTDATE,'YYYY/MM/DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') AND TO_DATE(LEAVESTARTDATE,'YYYY/MM/DD HH24:MI:SS') < TO_DATE(?, 'YYYY/MM/DD') " : "") +
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

    /**
     * 查询请假数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @return
     */
    private List findLeaveDataForSalarySettlement(String startDate,String endDate, List<String> staffIdList, String companyID){
        List<Object> paramList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT ID,STATUS,STAFFID,STAFFNAME,LEAVESTARTDATE,LEAVEENDDATE,LEAVETYPE " +
                "FROM DT_MYLeave " +
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
                (StringUtils.isNotBlank(startDate) ? "AND TO_DATE(LEAVESTARTDATE,'YYYY/MM/DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') AND TO_DATE(LEAVESTARTDATE,'YYYY/MM/DD HH24:MI:SS') < TO_DATE(?, 'YYYY/MM/DD') " : "") +
                "ORDER BY STAFFID");
        paramList.add(companyID);
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sb.toString(), paramList.toArray());
    }

    /**
     * 查询外勤数据
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

    /**
     * 查询外勤数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @return
     */
    private List findOutworkDataForSalarySettlement(String startDate,String endDate, List<String> staffIdList, String companyID){
        List<Object> paramList = new ArrayList<>();
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
        paramList.add(companyID);
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sb.toString(), paramList.toArray());
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
                (StringUtils.isNotBlank(startDate) ? "AND TO_DATE(OVERTIMESTARTDATE,'YYYY/MM/DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') AND TO_DATE(OVERTIMESTARTDATE,'YYYY/MM/DD HH24:MI:SS') < TO_DATE(?, 'YYYY/MM/DD') " : "") +
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

    /**
     * 查询加班数据
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param companyID
     * @return
     */
    private List findOvertimeDataForSalarySettlement(String startDate,String endDate, List<String> staffIdList, String companyID){
        List<Object> paramList = new ArrayList<>();
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
                (StringUtils.isNotBlank(startDate) ? "AND TO_DATE(OVERTIMESTARTDATE,'YYYY/MM/DD HH24:MI:SS') >= TO_DATE(?, 'YYYY/MM/DD') AND TO_DATE(OVERTIMESTARTDATE,'YYYY/MM/DD HH24:MI:SS') < TO_DATE(?, 'YYYY/MM/DD') " : "") +
                "ORDER BY STAFFID");
        paramList.add(companyID);
        if(StringUtils.isNotBlank(startDate)){
            paramList.add(startDate);
            paramList.add(endDate);
        }
        return baseBeanService.getListBeanBySqlAndParams(sb.toString(), paramList.toArray());
    }

    /**
     * 查询员工对应的级别工资
     * @param staffIdList
     * @return
     */
    private Map<String, SalaryData> findEmployeeSalary(List<String> staffIdList,String companyId){
        List<Object> paramList = new ArrayList<>();
        Map<String, SalaryData> salaryDataMap = new HashMap<>();
        if(Objects.isNull(staffIdList) || staffIdList.isEmpty()){
            return salaryDataMap;
        }
        paramList.add(companyId);
        String tempStr = "";
        for(String staffId : staffIdList){
            tempStr += "?,";
            paramList.add(staffId);
        }
        String sql = "SELECT D.STAFFID,MIN(S.BASE_SALARY),MIN(S.GUARANTEE_SALARY),MIN(S.WELFARE_SALARY),MIN(S.SALARY_LEVEL_ID),MIN(S.SALARY_LEVEL_NUM)," +
                "MIN(S.ROLE_SALARY),MIN(S.DUTY_SALARY),MIN(S.COMPETE_SALARY),MIN(S.SECRECY_SALARY),MIN(S.LEVEL_SALARY) " +
                "FROM DTCOS d, DT_HR_SALARY_DATA S " +
                "WHERE D.SALARY_LEVEL_ID = S.SALARY_LEVEL_ID " +
                "AND D.COMPANYID = S.COMPANY_ID " +
                "AND S.COMPANY_ID = ? " +
                "AND D.STAFFID IN ("+tempStr.substring(0,tempStr.length() - 1)+") " +
                "GROUP BY D.STAFFID";
        List<BaseBean> baseBeanList = baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
        if(Objects.nonNull(baseBeanList)){
            for(Object obj : baseBeanList){
                Object[] arr = (Object[]) obj;
                SalaryData salaryData = new SalaryData();
                salaryData.setBaseSalary(arr[1].toString());
                salaryData.setGuaranteeSalary(arr[2].toString());
                salaryData.setWelfareSalary(arr[3].toString());
                salaryData.setSalaryLevelId(arr[4].toString());
                salaryData.setSalaryLevelNum(arr[5].toString());
                salaryData.setRoleSalary(arr[6].toString());
                salaryData.setDutySalary(arr[7].toString());
                salaryData.setCompeteSalary(arr[8].toString());
                salaryData.setSecrecySalary(arr[9].toString());
                salaryData.setLevelSalary(arr[10].toString());
                salaryData.setCompanyId(companyId);
                salaryDataMap.put(arr[0].toString(),salaryData);
            }
        }
        return salaryDataMap;
    }

    /**
     * 查询公司的考勤设置，然在结果集合中找到考勤类别和工资级别对应的考勤设置
     * @param
     * @return
     */
    private List<BaseBean> findSetupByCompanyID(String companyID) {
        List<BaseBean> baseBeanList = baseBeanService.getListBeanBySqlAndParams("SELECT S.CHECKON_SETUP_ID," +
                "S.RANK_ID,S.CHECKON_TYPE_ID,S.REFERENCE_BASE_SALARY," +
                "S.REFERENCE_GUARANTEE_SALARY,S.REFERENCE_WELFARE_SALARY," +
                "S.DISCOUNT_RATE,S.AMOUNT_TYPE," +
                "S.CHECKON_AMOUNT,S.REWARD_DEDUCT_TYPE,S.REWARD_DEDUCT_AMOUNT,T.CHECKON_TYPE_NAME,T.CHECKON_TYPE_ENAME " +
                "FROM DT_CHECKON_SETUP S,DT_CHECKON_TYPE T " +
                "WHERE S.CHECKON_TYPE_ID = T.CHECKON_TYPE_ID  " +
                "AND S.COMPANY_ID = ?", new Object[]{companyID});
        return baseBeanList;
    }

    public Double findCheckOnDays(String companyId,String year,String month) throws Exception{
        List<BaseBean> salaryDictDataList =  baseBeanService
                .getListBeanByHqlAndParams("from SalaryDictData where companyId = ?" +
                        " and dictName = 'dateSet' and dictType = 'basic'", new Object[]{companyId});
        if(Objects.nonNull(salaryDictDataList) && salaryDictDataList.size() > 0){
            String dictValue = ((SalaryDictData)salaryDictDataList.get(0)).getDictValue();
            if("2".equals(dictValue)){
//                if(Objects.nonNull(year) && Objects.nonNull(month)){
//                    Calendar calendar = Calendar.getInstance();
//                    Date date = dateFormat.parse(year + "-" + month + "-01");
//                    calendar.setTime(date);
//                    return Double.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//                }
                //统一30
                return OVERTIME_DAYS_PER_MONTH;
            }
        }
        return CHECK_ON_DAYS_PER_MONTH;
    }

    public int findMonthWorkDays(String year,String month) throws Exception{
        Calendar calendar = Calendar.getInstance();
        Date date = dateFormat.parse(year + "-" + month + "-01");
        calendar.setTime(date);
        int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int monthDayCount = 0;
        for(int i = 1;i <= dayCount;i++){
            String day = "01";
            if(i < 10){
                day = "0" + i;
            }else{
                day = "" + i;
            }
            if(!overtimeApplyMobileService.isWeekendDay(year + "-" + month + "-" + day)){
                monthDayCount++;
            }
        }
        return monthDayCount;
    }

    public String[] findSignInSetup(String companyId,String signInType){
        List<BaseBean> signInSetupList =  baseBeanService
                .getListBeanBySqlAndParams("SELECT START_TIME,END_TIME FROM DT_SIGNIN_SETUP dss " +
                        "    WHERE dss.COMPANY_ID = ? " +
                        "    AND SIGNIN_TYPE_ID = (SELECT SIGNIN_TYPE_ID FROM DT_SIGNIN_TYPE dst " +
                        "    WHERE dst.signin_type_en_name = ?) " +
                        "    AND ROWNUM = 1", new Object[]{companyId,signInType});
        if(Objects.nonNull(signInSetupList) && signInSetupList.size() > 0){
            String[] signInTime = new String[]{"09:00","18:00"};
            for(Object obj : signInSetupList){
                Object[] arr = (Object[]) obj;
                signInTime[0] = arr[0].toString();
                signInTime[1] = arr[1].toString();
            }
            return signInTime;
        }
        return new String[]{"09:00","18:00"};
    }

    private CheckOnSetup getCheckOnSetup(String salaryLevel,String checkOnTypeName,String companyID){
        //公司的考勤设置
        List<BaseBean> checkOnSetupList = findSetupByCompanyID(companyID);
        for(Object obj : checkOnSetupList){
            Object[] arr = (Object[]) obj;
            if(arr[1].toString().equals(salaryLevel) && arr[12].toString().equals(checkOnTypeName)){
                CheckOnSetup checkOnSetup = new CheckOnSetup();
                checkOnSetup.setCheckOnSetupId(arr[0].toString());
                checkOnSetup.setRankId(arr[1].toString());
                checkOnSetup.setCheckOnTypeId(arr[2].toString());
                checkOnSetup.setCheckOnTypeName(arr[12].toString());
                checkOnSetup.setReferenceBaseSalary(Objects.nonNull(arr[3]) ? arr[3].toString() : Y_VALUE);
                checkOnSetup.setReferenceGuaranteeSalary(Objects.nonNull(arr[4]) ? arr[4].toString() : Y_VALUE);
                checkOnSetup.setReferenceWelfareSalary(Objects.nonNull(arr[5]) ? arr[5].toString() : Y_VALUE);
                checkOnSetup.setDiscountRate(Double.parseDouble(arr[6].toString()));
                checkOnSetup.setAmountType(arr[7].toString());
                checkOnSetup.setCheckOnAmount(Objects.nonNull(arr[8]) ? Double.parseDouble(arr[8].toString()) : 0);
                checkOnSetup.setRewardDeductType(Objects.nonNull(arr[9]) ? arr[9].toString() : DISCOUNT_TYPE_COMPUTE);
                checkOnSetup.setRewardDeductAmount(Objects.nonNull(arr[10]) ? Double.parseDouble(arr[10].toString()) : 0);
                return checkOnSetup;
            }
        }
        return null;
    }

    private Map<String, SignInCheckOn> computeCheckOnData(Map<String, List<SignInResult>> signInResultMap,List<String> staffIdList
            ,String companyId,String checkOnTimeType,Double checkOnDaysPerMonth,Map<String, SalaryData> salaryDataMap){
        Map<String, SignInCheckOn> signInCheckOnMap = new HashMap<>();
        for(Map.Entry<String, List<SignInResult>> signInResultMapEntry : signInResultMap.entrySet() ){
            SignInCheckOn signInCheckOn = new SignInCheckOn();
            signInCheckOn.setLateCount(0);
            signInCheckOn.setLateMinutes(0D);
            signInCheckOn.setLateSeconds(0);
            signInCheckOn.setLateDiscountMoney(BigDecimal.ZERO);
            signInCheckOn.setLeaveEarlyCount(0);
            signInCheckOn.setLeaveEarlyMinutes(0D);
            signInCheckOn.setLeaveEarlySeconds(0);
            signInCheckOn.setLeaveEarlyDiscountMoney(BigDecimal.ZERO);
            signInCheckOn.setCheckOnTimeType(checkOnTimeType);
            signInCheckOn.setCheckOnDayCount(checkOnDaysPerMonth);
            signInCheckOn.setStaffId(signInResultMapEntry.getKey());
            signInCheckOn.setTotalDiscountMoney(BigDecimal.ZERO);

            List<SignInResult> signInResultList = signInResultMapEntry.getValue();
            Integer lateCount = 0;
            Integer lateSeconds = 0;
            Integer leaveEarlyCount = 0;
            Integer leaveEarlySeconds = 0;
            for(SignInResult signInResult : signInResultList){
                if(signInResult.getLateSeconds() > 0){
                    lateCount++;
                    lateSeconds += signInResult.getLateSeconds();
                }
                if(signInResult.getLeaveEarlySeconds() > 0){
                    leaveEarlyCount++;
                    leaveEarlySeconds += signInResult.getLeaveEarlySeconds();
                }
            }
            signInCheckOn.setLateCount(lateCount);
            signInCheckOn.setLateSeconds(lateSeconds);
            signInCheckOn.setLateMinutes(new BigDecimal(lateSeconds)
                    .divide(BigDecimal.valueOf(60),2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveEarlyCount(leaveEarlyCount);
            signInCheckOn.setLeaveEarlySeconds(leaveEarlySeconds);
            signInCheckOn.setLeaveEarlyMinutes(new BigDecimal(leaveEarlySeconds)
                    .divide(BigDecimal.valueOf(60),2,RoundingMode.HALF_UP).doubleValue());
            //计算应扣工资
            SalaryData salaryData = salaryDataMap.get(signInCheckOn.getStaffId());
            if(Objects.nonNull(salaryData)){
                try {
                    signInCheckOn.setBaseSalary(Objects.nonNull(salaryData.getBaseSalary()) ? Double.parseDouble(salaryData.getBaseSalary()) : null);
                    signInCheckOn.setGuaranteeSalary(Objects.nonNull(salaryData.getGuaranteeSalary()) ? Double.parseDouble(salaryData.getGuaranteeSalary()) : null);
                    signInCheckOn.setWelfareSalary(Objects.nonNull(salaryData.getWelfareSalary()) ? Double.parseDouble(salaryData.getWelfareSalary()) : null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                signInCheckOn.setSalaryLevelId(salaryData.getSalaryLevelId());
            }
            computeDiscountSalary(signInCheckOn,LATE,companyId);
            computeDiscountSalary(signInCheckOn,LEAVE_EARLY,companyId);

            signInCheckOn.setTotalDiscountMoney(signInCheckOn.getLateDiscountMoney().add(signInCheckOn.getLeaveEarlyDiscountMoney()).setScale(2,RoundingMode.HALF_UP));
            signInCheckOnMap.put(signInCheckOn.getStaffId(),signInCheckOn);
        }
        return signInCheckOnMap;
    }

    /**
     * 加班统计
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param companyId
     * @param checkOnDaysPerMonth
     * @param salaryDataMap
     * @param signInResultMap
     * @param needSaveToSession
     * @return
     * @throws Exception
     */
    private Map<String, SignInCheckOn> computeOvertimeData(String startDate, String endDate, List<String> staffIdList,String companyId
            ,Double checkOnDaysPerMonth,Map<String, SalaryData> salaryDataMap,Map<String,List<SignInResult>> signInResultMap,Boolean needSaveToSession) throws Exception{
        Map<String, SignInCheckOn> overtimeCheckOnMap = new HashMap<>();
        List<BaseBean> overtimeList = new ArrayList<>();
        if(needSaveToSession){
            overtimeList = findOvertimeData(startDate,endDate,staffIdList);
        }else{
            overtimeList = findOvertimeDataForSalarySettlement(startDate,endDate,staffIdList,companyId);
        }

        Map<String, List<OvertimeData>> overtimeMap = new HashMap<>();
        List<OvertimeData> overtimeDataList = new ArrayList<>();
        if(Objects.nonNull(overtimeList) && !overtimeList.isEmpty()){
            for (Object obj : overtimeList) {
                Object[] leaveArr = (Object[]) obj;
                String staffId = leaveArr[2].toString();
                String leaveStartDate = leaveArr[4].toString();
                String leaveEndDate = leaveArr[5].toString();
                overtimeDataList.addAll(productOvertimeData(staffId,leaveStartDate,leaveEndDate,endDate,companyId));
            }
        }

        if(overtimeDataList.size() > 0){
            //对加班时长取申请时间和打卡时间的小值，以得到实际加班的时长
            updateOvertimeDuration(overtimeDataList,signInResultMap,companyId);
            for(OvertimeData overtimeData : overtimeDataList){
                if(overtimeData.getOvertimeSeconds() <= 0){
                    continue;
                }
                //将数据汇总到Map中
                if(Objects.nonNull(overtimeMap.get(overtimeData.getStaffId()))){
                    List<OvertimeData> overtimeDatas =  overtimeMap.get(overtimeData.getStaffId());
                    overtimeDatas.add(overtimeData);
                }else{
                    List<OvertimeData> overtimeDatas = new ArrayList<>();
                    overtimeDatas.add(overtimeData);
                    overtimeMap.put(overtimeData.getStaffId(),overtimeDatas);
                }
            }
        }
        if(needSaveToSession){
            saveToSessionOvertime(overtimeMap);
        }

        for(Map.Entry<String, List<OvertimeData>> overtimeMapEntry : overtimeMap.entrySet()){
            SignInCheckOn signInCheckOn = new SignInCheckOn();
            signInCheckOn.setStaffId(overtimeMapEntry.getKey());
            signInCheckOn.setOvertimeDataList(overtimeMapEntry.getValue());
            signInCheckOn.setCheckOnDayCount(checkOnDaysPerMonth);
            //计算加班工资
            SalaryData salaryData = salaryDataMap.get(signInCheckOn.getStaffId());
            if(Objects.nonNull(salaryData)){
                try {
                    signInCheckOn.setBaseSalary(Objects.nonNull(salaryData.getBaseSalary()) ? Double.parseDouble(salaryData.getBaseSalary()) : null);
                    signInCheckOn.setGuaranteeSalary(Objects.nonNull(salaryData.getGuaranteeSalary()) ? Double.parseDouble(salaryData.getGuaranteeSalary()) : null);
                    signInCheckOn.setWelfareSalary(Objects.nonNull(salaryData.getWelfareSalary()) ? Double.parseDouble(salaryData.getWelfareSalary()) : null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                signInCheckOn.setSalaryLevelId(salaryData.getSalaryLevelId());
            }
            computeOvertimeSalary(signInCheckOn,overtimeMapEntry.getValue(),companyId,EIGHT_HOUR_OVERTIME);
            computeOvertimeSalary(signInCheckOn,overtimeMapEntry.getValue(),companyId,HOLIDAY_OVERTIME);

            overtimeCheckOnMap.put(signInCheckOn.getStaffId(),signInCheckOn);
        }
        return overtimeCheckOnMap;
    }

    /**
     * 修正加班时长，以实际打卡为准
     * @param overtimeDataList
     * @param signInResultMap
     * @param companyId
     */
    private void updateOvertimeDuration(List<OvertimeData> overtimeDataList,Map<String,List<SignInResult>> signInResultMap,String companyId) throws Exception{
        String signInSetupStartTime = "09:00:00";
        String signInSetupEndTime = "18:00:00";

        String [] signInTime = findSignInSetup(companyId,SIGN_IN_TYPE_ORDINARY);
        if(Objects.nonNull(signInTime)) {
            signInSetupStartTime = signInTime[0] + ":00";
            signInSetupEndTime = signInTime[1] + ":00";
        }
        for(OvertimeData overtimeData : overtimeDataList){
            boolean find = false;
            List<SignInResult> signInResultList = signInResultMap.get(overtimeData.getStaffId());
            if(Objects.nonNull(signInResultList) && signInResultList.size()>0){
                for(SignInResult signInResult : signInResultList){
                    if(signInResult.getSignInDate().equals(overtimeData.getOvertimeDate())){
                        int overtimeSeconds = 0;
                        if(EIGHT_HOUR_OVERTIME.equals(overtimeData.getOvertimeType())) {
                            overtimeSeconds = (int)((signInResult.getSignOutTime().getTime() - dateTimeFormat.parse(signInResult.getSignInDate() + " " + signInSetupEndTime).getTime()) / 1000);
                        }else{
                            overtimeSeconds = (int)((signInResult.getSignOutTime().getTime() - signInResult.getSignInTime().getTime()) / 1000);
                        }
                        if(overtimeSeconds > 0){
                            if(overtimeSeconds < overtimeData.getOvertimeSeconds()){
                                overtimeData.setOvertimeSeconds(overtimeSeconds);
                                overtimeData.setOvertimeHours(new BigDecimal(overtimeData.getOvertimeSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                                overtimeData.setOvertimeMinutes(new BigDecimal(overtimeData.getOvertimeSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
                            }
                        }else{
                            overtimeData.setOvertimeHours(0d);
                            overtimeData.setOvertimeMinutes(0);
                            overtimeData.setOvertimeSeconds(0);
                        }
                        find = true;
                        break;
                    }
                }
            }
            if(!find){
                overtimeData.setOvertimeHours(0d);
                overtimeData.setOvertimeMinutes(0);
                overtimeData.setOvertimeSeconds(0);
            }
        }
    }

    /**
     * 加班统计
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param companyId
     * @param checkOnDaysPerMonth
     * @param salaryDataMap
     * @param needSaveToSession
     * @return
     * @throws Exception
     */
    private Map<String, SignInCheckOn> computeLeaveData(String startDate, String endDate, List<String> staffIdList,String companyId
            ,Double checkOnDaysPerMonth,Map<String, SalaryData> salaryDataMap,Boolean needSaveToSession) throws Exception{
        Map<String, SignInCheckOn> leaveCheckOnMap = new HashMap<>();
        List<BaseBean> leaveList = new ArrayList<>();
        if(needSaveToSession){
            leaveList = findLeaveData(startDate,endDate,staffIdList);
        }else{
            leaveList = findLeaveDataForSalarySettlement(startDate,endDate,staffIdList,companyId);
        }

        Map<String, List<LeaveData>> leaveMap = new HashMap<>();
        List<LeaveData> leaveDataList = new ArrayList<>();
        if(Objects.nonNull(leaveList) && !leaveList.isEmpty()){
            for (Object obj : leaveList) {
                Object[] leaveArr = (Object[]) obj;
                String staffId = leaveArr[2].toString();
                String leaveStartDate = leaveArr[4].toString();
                String leaveEndDate = leaveArr[5].toString();
                String leaveType = checkOnTypeNameToEngName(leaveArr[6].toString());
                leaveDataList.addAll(productLeaveData(staffId,leaveStartDate,leaveEndDate,endDate,leaveType,companyId));
            }
        }
        if(leaveDataList.size() > 0){
            for(LeaveData leaveData : leaveDataList){
                //将请假数据汇总到Map中
                if(Objects.nonNull(leaveMap.get(leaveData.getStaffId()))){
                    List<LeaveData> leaveDatas =  leaveMap.get(leaveData.getStaffId());
                    leaveDatas.add(leaveData);
                }else{
                    List<LeaveData> leaveDatas = new ArrayList<>();
                    leaveDatas.add(leaveData);
                    leaveMap.put(leaveData.getStaffId(),leaveDatas);
                }
            }
        }
        if(needSaveToSession){
            saveToSessionLeave(leaveMap);
        }

        for(Map.Entry<String, List<LeaveData>> leaveMapEntry : leaveMap.entrySet()){
            SignInCheckOn signInCheckOn = new SignInCheckOn();
            signInCheckOn.setStaffId(leaveMapEntry.getKey());
            signInCheckOn.setLeaveDataList(leaveMapEntry.getValue());
            signInCheckOn.setCheckOnDayCount(checkOnDaysPerMonth);
            //计算应扣工资
            SalaryData salaryData = salaryDataMap.get(signInCheckOn.getStaffId());
            if(Objects.nonNull(salaryData)){
                try {
                    signInCheckOn.setBaseSalary(Objects.nonNull(salaryData.getBaseSalary()) ? Double.parseDouble(salaryData.getBaseSalary()) : null);
                    signInCheckOn.setGuaranteeSalary(Objects.nonNull(salaryData.getGuaranteeSalary()) ? Double.parseDouble(salaryData.getGuaranteeSalary()) : null);
                    signInCheckOn.setWelfareSalary(Objects.nonNull(salaryData.getWelfareSalary()) ? Double.parseDouble(salaryData.getWelfareSalary()) : null);
                }catch (Exception e){
                    e.printStackTrace();
                }
                signInCheckOn.setSalaryLevelId(salaryData.getSalaryLevelId());
            }
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,LEAVE_ABSENCE);
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,LEAVE_SICK);
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,LEAVE_MARRIAGE);
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,LEAVE_ANNUAL);
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,LEAVE_MATERNITY);
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,LEAVE_FUNERAL);
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,LEAVE_WORKINJURY);
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,LEAVE_ROTATIONREST);


            signInCheckOn.setTotalDiscountMoney(signInCheckOn.getLeaveAbsenceDiscountMoney()
                    .add(signInCheckOn.getLeaveSickDiscountMoney())
                    .add(signInCheckOn.getLeaveMarriageDiscountMoney())
                    .add(signInCheckOn.getLeaveAnnualDiscountMoney())
                    .add(signInCheckOn.getLeaveMaternityDiscountMoney())
                    .add(signInCheckOn.getLeaveFuneralDiscountMoney())
                    .add(signInCheckOn.getLeaveWorkInjuryDiscountMoney())
                    .add(signInCheckOn.getLeaveRotationRestDiscountMoney())
                    .setScale(2,RoundingMode.HALF_UP));
            leaveCheckOnMap.put(signInCheckOn.getStaffId(),signInCheckOn);
        }
        return leaveCheckOnMap;
    }

    /**
     * 外勤统计
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param companyId
     * @param needSaveToSession
     * @return
     * @throws Exception
     */
    private Map<String, SignInCheckOn> computeOutworkData(String startDate, String endDate, List<String> staffIdList,String companyId,Boolean needSaveToSession) throws Exception{
        Map<String, SignInCheckOn> outworkMap = new HashMap<>();
        List<BaseBean> leaveList = new ArrayList<>();
        if(needSaveToSession){
            leaveList = findOutworkData(startDate,endDate,staffIdList);
        }else{
            leaveList = findOutworkDataForSalarySettlement(startDate,endDate,staffIdList,companyId);
        }

        Map<String, List<LeaveData>> leaveMap = new HashMap<>();
        List<LeaveData> leaveDataList = new ArrayList<>();
        if(Objects.nonNull(leaveList) && !leaveList.isEmpty()){
            for (Object obj : leaveList) {
                Object[] leaveArr = (Object[]) obj;
                String staffId = leaveArr[2].toString();
                String leaveStartDate = leaveArr[4].toString();
                String leaveEndDate = leaveArr[5].toString();
                leaveDataList.addAll(productLeaveData(staffId,leaveStartDate,leaveEndDate,endDate,OUTWORK,companyId));
            }
        }
        if(leaveDataList.size() > 0){
            for(LeaveData leaveData : leaveDataList){
                //将外勤数据汇总到Map中
                if(Objects.nonNull(leaveMap.get(leaveData.getStaffId()))){
                    List<LeaveData> leaveDatas =  leaveMap.get(leaveData.getStaffId());
                    leaveDatas.add(leaveData);
                }else{
                    List<LeaveData> leaveDatas = new ArrayList<>();
                    leaveDatas.add(leaveData);
                    leaveMap.put(leaveData.getStaffId(),leaveDatas);
                }
            }
        }
        if(needSaveToSession){
            saveToSessionOutwork(leaveMap);
        }

        for(Map.Entry<String, List<LeaveData>> leaveMapEntry : leaveMap.entrySet()){
            SignInCheckOn signInCheckOn = new SignInCheckOn();
            signInCheckOn.setStaffId(leaveMapEntry.getKey());
            signInCheckOn.setOutworkList(leaveMapEntry.getValue());
            computeLeaveDiscountSalary(signInCheckOn,leaveMapEntry.getValue(),companyId,OUTWORK);
            outworkMap.put(signInCheckOn.getStaffId(),signInCheckOn);
        }
        return outworkMap;
    }

    /**
     * 旷工统计
     * @param staffIdList
     * @param companyId
     * @param checkOnTimeType
     * @return
     */
    private Map<String, SignInCheckOn> computeAbsenteeData(String firstDate,String endDate,List<String> staffIdList,String companyId,String checkOnTimeType
            ,Double checkOnDaysPerMonth,Map<String, SalaryData> salaryDataMap
            ,Map<String, List<SignInResult>> signInResultMap,Map<String, SignInCheckOn> leaveCheckOnMap,Map<String, SignInCheckOn> outworkMap,Boolean needSaveToSession){
        Map<String, SignInCheckOn> absenteeMap = new HashMap<>();
        List<String> absenteeDate = new ArrayList<>();
        String today = dateFormat.format(new Date());
        //今天的旷工不做统计，因为统计不准
        if(today.equals(firstDate)){
            if(needSaveToSession){
                saveToSessionAbsentee(new HashMap<>());
            }
            return absenteeMap;
        }
        Integer dayCount = selectDays(firstDate,endDate) - 1;
        absenteeDate.add(firstDate);
        if(dayCount > 0){
            String date = firstDate;
            for(int i = 0;i < dayCount;i++){
                date = selectNextDate(date);
                if(StringUtils.isNotBlank(date)){
                    absenteeDate.add(date);
                }else{
                    break;
                }
            }
        }
        if(absenteeDate.size() > 0){
            List<String> endWeekDate = Arrays.asList(END_WEEK_OF_WORK_DATE);
            List<String> holidayDate2024 = Arrays.asList(HOLIDAY_DATE_2024);
            List<String> holidayDate2025 = Arrays.asList(HOLIDAY_DATE_2025);
            List<String> holidayDate = new ArrayList<>();
            holidayDate.addAll(holidayDate2024);
            holidayDate.addAll(holidayDate2025);
            Calendar cal = Calendar.getInstance();
            //排除周6，周天
            Iterator<String> iterator = absenteeDate.iterator();
            while (iterator.hasNext()){
                String date = iterator.next();
                String[] dates = date.split("-");
                cal.set(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]) - 1,Integer.parseInt(dates[2]));
                if(cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7){
                    if(!endWeekDate.contains(date)){
                        iterator.remove();
                    }
                }
            }
            //排除节假日,目前只考虑2024,2025这两年的情况
            iterator = absenteeDate.iterator();
            while (iterator.hasNext()){
                String date = iterator.next();
                if(holidayDate.contains(date)){
                    iterator.remove();
                }
            }
        }

        if(absenteeDate.size() > 0) {
            Map<String,List<String>> absMap = new HashMap<>();//员工旷工日期存入session
            for (String staffId : staffIdList) {
                SignInCheckOn signInCheckOn = new SignInCheckOn();
                signInCheckOn.setStaffId(staffId);
                signInCheckOn.setCheckOnDayCount(checkOnDaysPerMonth);
                signInCheckOn.setAbsenteeDayCount(0d);
                signInCheckOn.setAbsenteeHours(0d);
                signInCheckOn.setAbsenteeMinutes(0);
                signInCheckOn.setAbsenteeSeconds(0);
                signInCheckOn.setAbsenteeDiscountMoney(BigDecimal.ZERO);

                //计算应扣工资
                SalaryData salaryData = salaryDataMap.get(signInCheckOn.getStaffId());
                if (Objects.nonNull(salaryData)) {
                    try {
                        signInCheckOn.setBaseSalary(Objects.nonNull(salaryData.getBaseSalary()) ? Double.parseDouble(salaryData.getBaseSalary()) : null);
                        signInCheckOn.setGuaranteeSalary(Objects.nonNull(salaryData.getGuaranteeSalary()) ? Double.parseDouble(salaryData.getGuaranteeSalary()) : null);
                        signInCheckOn.setWelfareSalary(Objects.nonNull(salaryData.getWelfareSalary()) ? Double.parseDouble(salaryData.getWelfareSalary()) : null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    signInCheckOn.setSalaryLevelId(salaryData.getSalaryLevelId());
                }
                List<SignInResult> signInResultList = signInResultMap.get(staffId);
                List<String> signInResultDate = new ArrayList<>();
                if(Objects.nonNull(signInResultList)){
                    for (SignInResult signInResult : signInResultList) {
                        signInResultDate.add(signInResult.getSignInDate());
                    }
                }
                //没有签到的日期
                List<String> staffAbsenteeDate = new ArrayList<>();
                for(String date : absenteeDate){
                    if(!signInResultDate.contains(date)){
                        staffAbsenteeDate.add(date);
                    }
                }
                if(staffAbsenteeDate.size() > 0){
                    //排除请假
                    SignInCheckOn leaveCheckOn = leaveCheckOnMap.get(staffId);
                    if(Objects.nonNull(leaveCheckOn)){
                        List<LeaveData> leaveDateList = leaveCheckOn.getLeaveDataList();
                        Iterator<String> iterator = staffAbsenteeDate.iterator();
                        while (iterator.hasNext()){
                            String date = iterator.next();
                            for(LeaveData leaveData : leaveDateList){
                                if(leaveData.getLeaveDate().equals(date)){
                                    iterator.remove();
                                    break;
                                }
                            }
                        }
                    }

                    //排除外勤，外勤也需要打卡所以就不需要排除外勤了
//                    SignInCheckOn outworkCheckOn = outworkMap.get(staffId);
//                    if(Objects.nonNull(outworkCheckOn)){
//                        List<LeaveData> outworkDateList = outworkCheckOn.getOutworkList();
//                        Iterator<String> iterator = staffAbsenteeDate.iterator();
//                        while (iterator.hasNext()){
//                            String date = iterator.next();
//                            for(LeaveData leaveData : outworkDateList){
//                                if(leaveData.getLeaveDate().equals(date)){
//                                    iterator.remove();
//                                    break;
//                                }
//                            }
//                        }
//                    }

                    if(staffAbsenteeDate.size() > 0){
                        absMap.put(staffId,staffAbsenteeDate);

                        signInCheckOn.setAbsenteeDayCount(new Double(staffAbsenteeDate.size()));
                        signInCheckOn.setAbsenteeHours(staffAbsenteeDate.size() * (double)HOUR_WORK_DAY);
                        signInCheckOn.setAbsenteeMinutes(staffAbsenteeDate.size() * HOUR_WORK_DAY * 60);
                        signInCheckOn.setAbsenteeSeconds(staffAbsenteeDate.size() * HOUR_WORK_DAY * 60 * 60);
                    //减掉请假和外勤的时间,不需要做去掉
//                    if(Objects.nonNull(leaveSignInCheckOn)){
//                        signInCheckOn.setAbsenteeDayCount(signInCheckOn.getAbsenteeDayCount()
//                                - leaveSignInCheckOn.getLeaveAbsenceDayCount()
//                                - leaveSignInCheckOn.getLeaveSickDayCount()
//                                - leaveSignInCheckOn.getLeaveMarriageDayCount()
//                                - leaveSignInCheckOn.getLeaveAnnualDayCount()
//                                - leaveSignInCheckOn.getLeaveMaternityDayCount()
//                                - leaveSignInCheckOn.getLeaveFuneralDayCount()
//                                - leaveSignInCheckOn.getLeaveWorkInjuryDayCount()
//                                - leaveSignInCheckOn.getLeaveRotationRestDayCount()
//                                - leaveSignInCheckOn.getOutworkDayCount()
//                        );
//                        signInCheckOn.setAbsenteeHours(signInCheckOn.getAbsenteeHours()
//                                - leaveSignInCheckOn.getLeaveAbsenceHours()
//                                - leaveSignInCheckOn.getLeaveSickHours()
//                                - leaveSignInCheckOn.getLeaveMarriageHours()
//                                - leaveSignInCheckOn.getLeaveAnnualHours()
//                                - leaveSignInCheckOn.getLeaveMaternityHours()
//                                - leaveSignInCheckOn.getLeaveFuneralHours()
//                                - leaveSignInCheckOn.getLeaveWorkInjuryHours()
//                                - leaveSignInCheckOn.getLeaveRotationRestHours()
//                                - leaveSignInCheckOn.getOutworkHours()
//                        );
//                        signInCheckOn.setAbsenteeMinutes(signInCheckOn.getAbsenteeMinutes()
//                                - leaveSignInCheckOn.getLeaveAbsenceMinutes()
//                                - leaveSignInCheckOn.getLeaveSickMinutes()
//                                - leaveSignInCheckOn.getLeaveMarriageMinutes()
//                                - leaveSignInCheckOn.getLeaveAnnualMinutes()
//                                - leaveSignInCheckOn.getLeaveMaternityMinutes()
//                                - leaveSignInCheckOn.getLeaveFuneralMinutes()
//                                - leaveSignInCheckOn.getLeaveWorkInjuryMinutes()
//                                - leaveSignInCheckOn.getLeaveRotationRestMinutes()
//                                - leaveSignInCheckOn.getOutworkMinutes()
//                        );
//                        signInCheckOn.setAbsenteeSeconds(signInCheckOn.getAbsenteeSeconds()
//                                - leaveSignInCheckOn.getLeaveAbsenceSeconds()
//                                - leaveSignInCheckOn.getLeaveSickSeconds()
//                                - leaveSignInCheckOn.getLeaveMarriageSeconds()
//                                - leaveSignInCheckOn.getLeaveAnnualSeconds()
//                                - leaveSignInCheckOn.getLeaveMaternitySeconds()
//                                - leaveSignInCheckOn.getLeaveFuneralSeconds()
//                                - leaveSignInCheckOn.getLeaveWorkInjurySeconds()
//                                - leaveSignInCheckOn.getLeaveRotationRestSeconds()
//                                - leaveSignInCheckOn.getOutworkSeconds()
//                        );
//                        signInCheckOn.setAbsenteeDayCount(signInCheckOn.getAbsenteeDayCount() >= 0 ? BigDecimal.valueOf(signInCheckOn.getAbsenteeDayCount()).setScale(2,RoundingMode.HALF_UP).doubleValue() : 0);
//                        signInCheckOn.setAbsenteeHours(signInCheckOn.getAbsenteeHours() >= 0 ? BigDecimal.valueOf(signInCheckOn.getAbsenteeHours()).setScale(2,RoundingMode.HALF_UP).doubleValue() : 0);
//                        signInCheckOn.setAbsenteeMinutes(signInCheckOn.getAbsenteeMinutes() >= 0 ? signInCheckOn.getAbsenteeMinutes() : 0);
//                        signInCheckOn.setAbsenteeSeconds(signInCheckOn.getAbsenteeSeconds() >= 0 ? signInCheckOn.getAbsenteeSeconds() : 0);
//                    }
                    }
                }
                computeDiscountSalary(signInCheckOn,ABSENTEE,companyId);
                absenteeMap.put(staffId, signInCheckOn);
            }
            if(needSaveToSession){
                saveToSessionAbsentee(absMap);
            }
        }
        return absenteeMap;
    }


    private Map<String, SignInCheckOn> computeWeekendOvertimeData(String firstDate,String endDate,String month,List<String> staffIdList,String companyId,String checkOnTimeType
            ,Double checkOnDaysPerMonth,Map<String, SalaryData> salaryDataMap
            ,Map<String, List<SignInResult>> signInResultMap,Boolean needSaveToSession){
        Map<String, SignInCheckOn> weekendOvertimeMap = new HashMap<>();
        String today = dateFormat.format(new Date());
        //今天的加班不做统计，因为统计不准
        if(today.equals(firstDate)){
            if(needSaveToSession){
                saveToSessionWeekendOvertime(new HashMap<>());
            }
            return weekendOvertimeMap;
        }
        //查询周6周天的目期
        List<String> weekdayDateList = selectWeekdayDates(firstDate,endDate);
        if(weekdayDateList.size() > 0){
            List<String> endWeekDate = Arrays.asList(END_WEEK_OF_WORK_DATE);
            List<String> holidayDate2024 = Arrays.asList(HOLIDAY_DATE_2024);
            List<String> holidayDate2025 = Arrays.asList(HOLIDAY_DATE_2025);
            List<String> holidayDate = new ArrayList<>();
            holidayDate.addAll(holidayDate2024);
            holidayDate.addAll(holidayDate2025);
            //排除周6，周天
            Iterator<String> iterator = weekdayDateList.iterator();
            if(Objects.nonNull(month)){
                while (iterator.hasNext()){
                    String date = iterator.next();
                    String[] dates = date.split("-");
                    //排除非当月的日期
                    if(!dates[1].equals(month)){
                        iterator.remove();
                    }else if(endWeekDate.contains(date)){//排除需要上班的日期
                        iterator.remove();
                    }else if(holidayDate.contains(date)){//排除节假日中的周6，周天
                        iterator.remove();
                    }
                }
            }else if(Objects.nonNull(checkOnTimeType)){
                String notNeedDate = notNeedDate(checkOnTimeType,endDate);
                while (iterator.hasNext()){
                    String date = iterator.next();
                    //排除非当月的日期
                    if(Objects.isNull(notNeedDate)){
                        if(endWeekDate.contains(date)){//排除需要上班的日期
                            iterator.remove();
                        }else if(holidayDate.contains(date)){//排除节假日中的周6，周天
                            iterator.remove();
                        }
                    }else{
                        if(date.equals(notNeedDate)){
                            iterator.remove();
                        }else if(endWeekDate.contains(date)){//排除需要上班的日期
                            iterator.remove();
                        }else if(holidayDate.contains(date)){//排除节假日中的周6，周天
                            iterator.remove();
                        }
                    }

                }
            }else{
                while (iterator.hasNext()){
                    String date = iterator.next();
                    if(endWeekDate.contains(date)){//排除需要上班的日期
                        iterator.remove();
                    }else if(holidayDate.contains(date)){//排除节假日中的周6，周天
                        iterator.remove();
                    }
                }
            }
        }

        if(weekdayDateList.size() > 0) {
            Map<String,List<String>> weekendMap = new HashMap<>();//员工加班日期存入session
            for (String staffId : staffIdList) {
                SignInCheckOn signInCheckOn = new SignInCheckOn();
                signInCheckOn.setStaffId(staffId);
                signInCheckOn.setCheckOnDayCount(checkOnDaysPerMonth);
                signInCheckOn.setWeekendOverTimeDayCount(0d);
                signInCheckOn.setWeekendOverTimeHours(0d);
                signInCheckOn.setWeekendOverTimeMinutes(0);
                signInCheckOn.setWeekendOverTimeSeconds(0);
                signInCheckOn.setWeekendOverTimeMoney(BigDecimal.ZERO);

                SalaryData salaryData = salaryDataMap.get(signInCheckOn.getStaffId());
                if (Objects.nonNull(salaryData)) {
                    try {
                        signInCheckOn.setBaseSalary(Objects.nonNull(salaryData.getBaseSalary()) ? Double.parseDouble(salaryData.getBaseSalary()) : null);
                        signInCheckOn.setGuaranteeSalary(Objects.nonNull(salaryData.getGuaranteeSalary()) ? Double.parseDouble(salaryData.getGuaranteeSalary()) : null);
                        signInCheckOn.setWelfareSalary(Objects.nonNull(salaryData.getWelfareSalary()) ? Double.parseDouble(salaryData.getWelfareSalary()) : null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    signInCheckOn.setSalaryLevelId(salaryData.getSalaryLevelId());
                }
                List<SignInResult> signInResultList = signInResultMap.get(staffId);
                Map<String,SignInResult> signInResultDateMap = new HashMap<>();
                if(Objects.nonNull(signInResultList)){
                    for (SignInResult signInResult : signInResultList) {
                        signInResultDateMap.put(signInResult.getSignInDate(),signInResult);
                    }
                }
                //有签到的周末日期
                List<String> signInDate = new ArrayList<>();
                for(String date : weekdayDateList){
                    if(signInResultDateMap.keySet().contains(date)){
                        signInDate.add(date);
                    }
                }
                if(signInDate.size() > 0){
                    weekendMap.put(staffId,signInDate);
                    //迟到，早退秒数求和
                    int discountSec = 0;
                    for(String date : signInDate){
                        SignInResult signInResult = signInResultDateMap.get(date);
                        if(Objects.nonNull(signInResult)){
                            discountSec += signInResult.getLateSeconds() + signInResult.getLeaveEarlySeconds();
                        }
                    }
                    //将迟到，早退秒数减掉，暂不实现 todo
                    //周6周天目前没有扣除迟到，早退秒数
                    List<OvertimeData> overtimeDataList = new ArrayList<>();
                    for(String date : signInDate){
                        OvertimeData overtimeData = new OvertimeData();
                        overtimeData.setOvertimeDate(date);
                        overtimeData.setOvertimeType(WEEKEND_OVERTIME);
                        overtimeData.setStaffId(staffId);
                        overtimeData.setOvertimeHours(HOUR_WORK_DAY.doubleValue());
                        overtimeData.setOvertimeMinutes(HOUR_WORK_DAY * 60);
                        overtimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
                        overtimeDataList.add(overtimeData);
                    }
                    signInCheckOn.setWeekendOvertimeDataList(overtimeDataList);
                    signInCheckOn.setWeekendOverTimeDayCount(new Double(signInDate.size()));
                    signInCheckOn.setWeekendOverTimeHours(signInDate.size() * (double)HOUR_WORK_DAY);
                    signInCheckOn.setWeekendOverTimeMinutes(signInDate.size() * HOUR_WORK_DAY * 60);
                    signInCheckOn.setWeekendOverTimeSeconds(signInDate.size() * HOUR_WORK_DAY * 60 * 60);
                    signInCheckOn.setWeekendOverTimeDayCount(signInCheckOn.getWeekendOverTimeDayCount() >= 0 ? BigDecimal.valueOf(signInCheckOn.getWeekendOverTimeDayCount()).setScale(2,RoundingMode.HALF_UP).doubleValue() : 0);
                    signInCheckOn.setWeekendOverTimeHours(signInCheckOn.getWeekendOverTimeHours() >= 0 ? BigDecimal.valueOf(signInCheckOn.getWeekendOverTimeHours()).setScale(2,RoundingMode.HALF_UP).doubleValue() : 0);
                    signInCheckOn.setWeekendOverTimeMinutes(signInCheckOn.getWeekendOverTimeMinutes() >= 0 ? signInCheckOn.getWeekendOverTimeMinutes() : 0);
                    signInCheckOn.setWeekendOverTimeSeconds(signInCheckOn.getWeekendOverTimeSeconds() >= 0 ? signInCheckOn.getWeekendOverTimeSeconds() : 0);
                }
                computeDiscountSalary(signInCheckOn,WEEKEND_OVERTIME,companyId);
                weekendOvertimeMap.put(staffId, signInCheckOn);
            }
            if(needSaveToSession){
                saveToSessionWeekendOvertime(weekendMap);
            }
        }
        return weekendOvertimeMap;
    }

    //时间转换
    public String[] timeTypeToTimeRange(String checkOnTimeType){
        String [] timeRange = new String[2];
        if(ALL.equals(checkOnTimeType)){
            return timeRange;
        }
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        Map<Integer,Integer> weekMap = new HashMap<>();
        weekMap.put(1,6);
        weekMap.put(2,0);
        weekMap.put(3,1);
        weekMap.put(4,2);
        weekMap.put(5,3);
        weekMap.put(6,4);
        weekMap.put(7,5);

        Map<Integer,Integer> quarterMap = new HashMap<>();
        quarterMap.put(1,1);
        quarterMap.put(2,1);
        quarterMap.put(3,1);
        quarterMap.put(4,4);
        quarterMap.put(5,4);
        quarterMap.put(6,4);
        quarterMap.put(7,7);
        quarterMap.put(8,7);
        quarterMap.put(9,7);
        quarterMap.put(10,10);
        quarterMap.put(11,10);
        quarterMap.put(12,10);

        //默认查本月
        timeRange[0] = year+"-"+month+"-01";
        if(month == 12){
            timeRange[1] = (year + 1)+"-01-01";
        }else{
            timeRange[1] = year+"-"+(month + 1)+"-01";
        }

        switch (checkOnTimeType){
            case TODAY:
                timeRange[0] = year+"-"+month+"-"+day;
                int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if(day >= lastDayOfMonth){
                    if(month == 12){
                        timeRange[1] = (year + 1)+"-01-01";
                    }else{
                        timeRange[1] = year+"-"+(month + 1)+"-01";
                    }
                }else{
                    timeRange[1] = year+"-"+month+"-"+(day + 1);
                }
                break;
            case YESTERDAY:
                if(day == 1){
                    if(month == 1){
                        timeRange[0] = (year - 1)+"-12-31";
                    }else{
                        Calendar cal = Calendar.getInstance();
                        cal.set(year,month - 2,1);
                        timeRange[0] = year+"-"+(month - 1)+"-"+cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    }
                }else{
                    timeRange[0] = year+"-"+month+"-"+(day - 1);
                }
                timeRange[1] = year+"-"+month+"-"+day;
                break;
            case CURRENT_WEEK:
                int firstDay = day - weekMap.get(dayOfWeek);
                if(firstDay < 1){
                    if(month == 1){
                        Calendar cal = Calendar.getInstance();
                        cal.set((year - 1),11,1);
                        timeRange[0] = (year - 1)+"-12-"+(cal.getActualMaximum(Calendar.DAY_OF_MONTH) + firstDay);
                    }else{
                        Calendar cal = Calendar.getInstance();
                        cal.set(year,month - 2,1);
                        timeRange[0] = year+"-"+(month - 1)+"-"+(cal.getActualMaximum(Calendar.DAY_OF_MONTH) + firstDay);
                    }
                }else{
                    timeRange[0] = year+"-"+month+"-"+firstDay;
                }

                lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                if(day >= lastDayOfMonth){
                    if(month == 12){
                        timeRange[1] = (year + 1)+"-01-01";
                    }else{
                        timeRange[1] = year+"-"+(month + 1)+"-01";
                    }
                }else{
                    timeRange[1] = year+"-"+month+"-"+(day + 1);
                }
                break;
            case BEFORE_WEEK:
                int beforeWeekFirstDay = day - weekMap.get(dayOfWeek) - 7;
                if(beforeWeekFirstDay < 1){
                    if(month == 1){
                        Calendar cal = Calendar.getInstance();
                        cal.set((year - 1),11,1);
                        timeRange[0] = (year - 1)+"-12-"+(cal.getActualMaximum(Calendar.DAY_OF_MONTH) + beforeWeekFirstDay);
                    }else{
                        Calendar cal = Calendar.getInstance();
                        cal.set(year,month - 2,1);
                        timeRange[0] = year+"-"+(month - 1)+"-"+(cal.getActualMaximum(Calendar.DAY_OF_MONTH) + beforeWeekFirstDay);
                    }
                }else{
                    timeRange[0] = year+"-"+month+"-"+(day - weekMap.get(dayOfWeek) - 7);
                }
                int currentWeekFirstDay = day - weekMap.get(dayOfWeek);
                if(currentWeekFirstDay < 1){
                    if(month == 1){
                        Calendar cal = Calendar.getInstance();
                        cal.set((year - 1),11,1);
                        timeRange[1] = (year - 1)+"-12-"+(cal.getActualMaximum(Calendar.DAY_OF_MONTH) + currentWeekFirstDay);
                    }else{
                        Calendar cal = Calendar.getInstance();
                        cal.set(year,month - 2,1);
                        timeRange[1] = year+"-"+(month - 1)+"-"+(cal.getActualMaximum(Calendar.DAY_OF_MONTH) + currentWeekFirstDay);
                    }
                }else {
                    timeRange[1] = year+"-"+month+"-"+(day - weekMap.get(dayOfWeek));
                }
                break;
            case CURRENT_MONTH:
                timeRange[0] = year+"-"+month+"-01";
                if(month == 12){
                    timeRange[1] = (year + 1)+"-01-01";
                }else{
                    timeRange[1] = year+"-"+(month + 1)+"-01";
                }
                break;
            case BEFORE_MONTH:
                if(month == 1){
                    timeRange[0] = (year - 1)+"-12-01";
                }else{
                    timeRange[0] = year+"-"+(month - 1)+"-01";
                }
                timeRange[1] = year+"-"+month+"-01";
                break;
            case CURRENT_QUARTER:
                timeRange[0] = year+"-"+ quarterMap.get(month) +"-01";
                if(quarterMap.get(month) + 3 > 12){
                    timeRange[1] = (year + 1)+"-01-01";
                }else {
                    timeRange[1] = year+"-"+ (quarterMap.get(month) + 3) +"-01";
                }
                break;
            case BEFORE_QUARTER:
                if(quarterMap.get(month) - 3 < 1){
                    timeRange[0] = (year - 1)+"-"+ quarterMap.get(12) +"-01";
                }else{
                    timeRange[0] = year+"-"+ (quarterMap.get(month) - 3) +"-01";
                }
                timeRange[1] = year+"-"+ quarterMap.get(month) +"-01";
                break;
            case CURRENT_YEAR:
                timeRange[0] = year+"-01-01";
                timeRange[1] = (year + 1)+"-01-01";
                break;
            case BEFORE_YEAR:
                timeRange[0] = (year - 1)+"-01-01";
                timeRange[1] = year+"-01-01";
                break;
        }
        String[] start = timeRange[0].split("-");
        String startMonth = start[1];
        String startDay = start[2];
        String[] end = timeRange[1].split("-");
        String endMonth = end[1];
        String endDay = end[2];
        if(start[1].length() < 2){
            startMonth = "0" + start[1];
        }
        if(start[2].length() < 2){
            startDay = "0" + start[2];
        }
        if(end[1].length() < 2){
            endMonth = "0" + end[1];
        }
        if(end[2].length() < 2){
            endDay = "0" + end[2];
        }
        timeRange[0] = start[0] + "-" + startMonth + "-" + startDay;
        timeRange[1] = end[0] + "-" + endMonth + "-" + endDay;
        return timeRange;
    }

    public String beforeDay(String date){
        if(Objects.isNull(date)){
            return date;
        }
        String beforeDay = date;
        String[] ymd = date.split("-");
        if(ymd.length == 3){
            int year = Integer.parseInt(ymd[0]);
            int month = Integer.parseInt(ymd[1]);
            int day = Integer.parseInt(ymd[2]);

            if(ymd[2].equals("1") || ymd[2].equals("01")){
                if(ymd[1].equals("1") || ymd[1].equals("01")){
                    beforeDay = (year - 1) + "-12-31";
                }else{
                    Calendar cal = Calendar.getInstance();
                    cal.set(year,month - 2,1);
                    beforeDay = year + "-" + (month - 1) + "-" + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                }
            }else{
                beforeDay = year + "-" + month + "-" + (day - 1);
            }
        }
        String[] beforeDate = beforeDay.split("-");
        String beforeDateMonth = beforeDate[1];
        String beforeDateDay = beforeDate[2];
        if(beforeDate[1].length() < 2){
            beforeDateMonth = "0" + beforeDate[1];
        }
        if(beforeDate[2].length() < 2){
            beforeDateDay = "0" + beforeDate[2];
        }
        return beforeDate[0] + "-" + beforeDateMonth + "-" + beforeDateDay;
    }

    private void computeDiscountSalary(SignInCheckOn signInCheckOn,String checkOnTypeName,String companyId){
        CheckOnSetup checkOnSetup = null;
        if(Objects.nonNull(signInCheckOn.getSalaryLevelId()))
        {
            checkOnSetup = getCheckOnSetup(signInCheckOn.getSalaryLevelId(), checkOnTypeName, companyId);
        }
        //目前考勤设置没有默认值
        if(Objects.nonNull(checkOnSetup)){
            String discountType = DISCOUNT_TYPE_COMPUTE;
            BigDecimal setupDiscountSalary = BigDecimal.ZERO;
            BigDecimal salary = new BigDecimal(BigInteger.ZERO);
            if(Y_VALUE.equals(checkOnSetup.getReferenceBaseSalary()) && Objects.nonNull(signInCheckOn.getBaseSalary())){
                salary = salary.add(BigDecimal.valueOf(signInCheckOn.getBaseSalary()));
            }
            if(Y_VALUE.equals(checkOnSetup.getReferenceGuaranteeSalary()) && Objects.nonNull(signInCheckOn.getGuaranteeSalary())){
                salary = salary.add(BigDecimal.valueOf(signInCheckOn.getGuaranteeSalary()));
            }
            if(Y_VALUE.equals(checkOnSetup.getReferenceWelfareSalary()) && Objects.nonNull(signInCheckOn.getWelfareSalary())){
                salary = salary.add(BigDecimal.valueOf(signInCheckOn.getWelfareSalary()));
            }

            if(DISCOUNT_TYPE_COMPUTE.equals(checkOnSetup.getRewardDeductType())){
                if(salary.compareTo(BigDecimal.ZERO) > 0){
                    signInCheckOn.setSalaryPerSeconds(salary.divide(BigDecimal.valueOf(signInCheckOn.getCheckOnDayCount()),8,RoundingMode.HALF_UP)
                            .divide(BigDecimal.valueOf(HOUR_WORK_DAY * 60 * 60),8,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(checkOnSetup.getDiscountRate())));
                }
            }else {
                discountType = DISCOUNT_TYPE_SETUP;
                if(Objects.nonNull(checkOnSetup.getRewardDeductAmount())){
                    setupDiscountSalary = BigDecimal.valueOf(checkOnSetup.getRewardDeductAmount()).multiply(BigDecimal.valueOf(checkOnSetup.getDiscountRate()));
                }
            }

            if(LATE.equals(checkOnTypeName) && signInCheckOn.getLateSeconds() > 0){//迟到计算
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLateSeconds()));
                        signInCheckOn.setLateDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLateDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLateCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_EARLY.equals(checkOnTypeName) && signInCheckOn.getLeaveEarlySeconds() > 0){//早退计算
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveEarlySeconds()));
                        signInCheckOn.setLeaveEarlyDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveEarlyDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveEarlyCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_ABSENCE.equals(checkOnTypeName) && signInCheckOn.getLeaveAbsenceSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveAbsenceSeconds()));
                        signInCheckOn.setLeaveAbsenceDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveAbsenceDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveAbsenceDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_SICK.equals(checkOnTypeName) && signInCheckOn.getLeaveSickSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveSickSeconds()));
                        signInCheckOn.setLeaveSickDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveSickDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveSickDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_MARRIAGE.equals(checkOnTypeName) && signInCheckOn.getLeaveMarriageSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveMarriageSeconds()));
                        signInCheckOn.setLeaveMarriageDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveMarriageDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveMarriageDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_ANNUAL.equals(checkOnTypeName) && signInCheckOn.getLeaveAnnualSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveAnnualSeconds()));
                        signInCheckOn.setLeaveAnnualDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveAnnualDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveAnnualDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_MATERNITY.equals(checkOnTypeName) && signInCheckOn.getLeaveMaternitySeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveMaternitySeconds()));
                        signInCheckOn.setLeaveMaternityDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveMaternityDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveMaternityDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_FUNERAL.equals(checkOnTypeName) && signInCheckOn.getLeaveFuneralSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveFuneralSeconds()));
                        signInCheckOn.setLeaveFuneralDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveFuneralDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveFuneralDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_WORKINJURY.equals(checkOnTypeName) && signInCheckOn.getLeaveWorkInjurySeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveWorkInjurySeconds()));
                        signInCheckOn.setLeaveWorkInjuryDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveWorkInjuryDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveWorkInjuryDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(LEAVE_ROTATIONREST.equals(checkOnTypeName) && signInCheckOn.getLeaveRotationRestSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getLeaveRotationRestSeconds()));
                        signInCheckOn.setLeaveRotationRestDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setLeaveRotationRestDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getLeaveRotationRestDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(ABSENTEE.equals(checkOnTypeName) && signInCheckOn.getAbsenteeSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal discountSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getAbsenteeSeconds()));
                        signInCheckOn.setAbsenteeDiscountMoney(discountSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setAbsenteeDiscountMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getAbsenteeDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(WEEKEND_OVERTIME.equals(checkOnTypeName) && signInCheckOn.getWeekendOverTimeSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal overTimeSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getWeekendOverTimeSeconds()));
                        signInCheckOn.setWeekendOverTimeMoney(overTimeSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setWeekendOverTimeMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getWeekendOverTimeDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(EIGHT_HOUR_OVERTIME.equals(checkOnTypeName) && signInCheckOn.getEightHourOverTimeSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal overTimeSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getEightHourOverTimeSeconds()));
                        signInCheckOn.setEightHourOverTimeMoney(overTimeSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setEightHourOverTimeMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getEightHourOverTimeDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }else if(HOLIDAY_OVERTIME.equals(checkOnTypeName) && signInCheckOn.getHolidayOverTimeSeconds() > 0){
                if(DISCOUNT_TYPE_COMPUTE.equals(discountType)){
                    if(Objects.nonNull(signInCheckOn.getSalaryPerSeconds())){
                        BigDecimal overTimeSalary = signInCheckOn.getSalaryPerSeconds().multiply(BigDecimal.valueOf(signInCheckOn.getHolidayOverTimeSeconds()));
                        signInCheckOn.setHolidayOverTimeMoney(overTimeSalary.setScale(2,RoundingMode.HALF_UP));
                    }
                }else{
                    signInCheckOn.setHolidayOverTimeMoney(setupDiscountSalary.multiply(BigDecimal.valueOf(signInCheckOn.getHolidayOverTimeDayCount())).setScale(2,RoundingMode.HALF_UP));
                }
            }
        }
    }

    /**
     * 计算迟到时间，早退时间
     * @param signInResult
     */
    private void computeSignInTime(SignInResult signInResult, String companyID,boolean isToday,String signInType) throws Exception{
        String [] signInTime = findSignInSetup(companyID,signInType);
        if(Objects.nonNull(signInResult) && Objects.nonNull(signInTime)){
            String signInSetupStartTime = signInResult.getSignInDate() + " " + signInTime[0] + ":00";
            String signInSetupEndTime = signInResult.getSignInDate() + " " + signInTime[1] + ":00";
            Long setupStartTime = dateTimeFormat.parse(signInSetupStartTime).getTime();
            Long setupEndTime = dateTimeFormat.parse(signInSetupEndTime).getTime();
            Long lateMilliSeconds = signInResult.getSignInTime().getTime() - setupStartTime;
            Long earlyMilliSeconds = signInResult.getSignOutTime().getTime() - setupEndTime;
            if(lateMilliSeconds > 0){
                signInResult.setLateSeconds((int)Math.ceil(lateMilliSeconds / 1000));
            }
            //对于今天是不求早退数据的，因为当天的早退数据是查不准的，员工可能很晚才打下班卡
            if(!isToday){
                if(earlyMilliSeconds < 0){
                    signInResult.setLeaveEarlySeconds((int)Math.ceil(Math.abs(earlyMilliSeconds) /1000));
                }
            }
        }
    }

    /**
     * 生成请假数据
     * @param staffId
     * @param leaveStartDate
     * @param leaveEndDate
     * @param leaveType
     * @return
     */
    private List<LeaveData> productLeaveData(String staffId, String leaveStartDate, String leaveEndDate, String endDate, String leaveType, String companyId) throws Exception{
        List<LeaveData> leaveDataList = new ArrayList<>();
        if(StringUtils.isBlank(leaveStartDate) || StringUtils.isBlank(leaveEndDate)){
            return leaveDataList;
        }
        String signInSetupStartTime = "09:00:00";
        String signInSetupEndTime = "18:00:00";

        String [] signInTime = findSignInSetup(companyId,SIGN_IN_TYPE_ORDINARY);
        if(Objects.nonNull(signInTime)) {
            signInSetupStartTime = signInTime[0] + ":00";
            signInSetupEndTime = signInTime[1] + ":00";
        }
        String [] startDateArr = leaveStartDate.split(BLANK_CHAR);
        String [] endDateArr = leaveEndDate.split(BLANK_CHAR);

        if(startDateArr.length == 2){
            if(leaveStartDate.split(":").length <= 2){
                leaveStartDate += ":00";
                startDateArr[1] += ":00";
            }
        }
        if(endDateArr.length == 2){
            if(leaveEndDate.split(":").length <= 2){
                leaveEndDate += ":00";
                endDateArr[1] += ":00";
            }
        }


        Date startDate = dateFormat.parse(startDateArr[0]);
        Date endDateT = dateFormat.parse(endDateArr[0]);
        if(startDate.compareTo(endDateT) > 0){
            return leaveDataList;
        }
        if(startDate.compareTo(endDateT) == 0){
            LeaveData leaveData = new LeaveData();
            leaveData.setStaffId(staffId);
            leaveData.setLeaveType(leaveType);
            leaveData.setLeaveDate(startDateArr[0]);

            if(startDateArr.length == 1 && endDateArr.length == 1){
                leaveData.setLeaveHours((double)HOUR_WORK_DAY);
                leaveData.setLeaveMinutes(HOUR_WORK_DAY * 60);
                leaveData.setLeaveSeconds(HOUR_WORK_DAY * 60 * 60);
            }else if(startDateArr.length == 2 && endDateArr.length == 2){
                leaveData.setLeaveSeconds((int)((dateTimeFormat.parse(leaveEndDate).getTime() - dateTimeFormat.parse(leaveStartDate).getTime()) / 1000));
                //todo 需要把午休的时间去掉
                if(leaveData.getLeaveSeconds() > HOUR_WORK_DAY * 60 * 60){
                    leaveData.setLeaveSeconds(HOUR_WORK_DAY * 60 * 60);
                }
                leaveData.setLeaveHours(new BigDecimal(leaveData.getLeaveSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                leaveData.setLeaveMinutes(new BigDecimal(leaveData.getLeaveSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
            }else{
                if(startDateArr.length == 1 && endDateArr.length == 2){
                    //todo 需要把午休的时间去掉
                    leaveData.setLeaveSeconds((int)((dateTimeFormat.parse(leaveEndDate).getTime() - dateTimeFormat.parse(leaveStartDate + " " + signInSetupStartTime).getTime()) / 1000));
                }else{
                    //todo 需要把午休的时间去掉
                    leaveData.setLeaveSeconds((int)((dateTimeFormat.parse(leaveEndDate + " " + signInSetupEndTime).getTime() - dateTimeFormat.parse(leaveStartDate).getTime()) / 1000));
                }
                if(leaveData.getLeaveSeconds() > HOUR_WORK_DAY * 60 * 60){
                    leaveData.setLeaveSeconds(HOUR_WORK_DAY * 60 * 60);
                }
                leaveData.setLeaveHours(new BigDecimal(leaveData.getLeaveSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                leaveData.setLeaveMinutes(new BigDecimal(leaveData.getLeaveSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
            }
            leaveDataList.add(leaveData);
        }else{
            Date endDateD = dateFormat.parse(beforeDay(endDate));
            Date end = endDateD;
            if(endDateT.compareTo(endDateD) < 0){
                end = endDateT;
            }

            //第一天
            LeaveData firstLeaveData = new LeaveData();
            firstLeaveData.setStaffId(staffId);
            firstLeaveData.setLeaveType(leaveType);
            firstLeaveData.setLeaveDate(startDateArr[0]);
            if(startDateArr.length == 1) {
                firstLeaveData.setLeaveHours((double)HOUR_WORK_DAY);
                firstLeaveData.setLeaveMinutes(HOUR_WORK_DAY * 60);
                firstLeaveData.setLeaveSeconds(HOUR_WORK_DAY * 60 * 60);
            }else{
                firstLeaveData.setLeaveSeconds((int)((dateTimeFormat.parse(startDateArr[0] + " " + signInSetupEndTime).getTime() - dateTimeFormat.parse(leaveStartDate).getTime()) / 1000));
                //todo 需要把午休的时间去掉
                if(firstLeaveData.getLeaveSeconds() > HOUR_WORK_DAY * 60 * 60){
                    firstLeaveData.setLeaveSeconds(HOUR_WORK_DAY * 60 * 60);
                }
                firstLeaveData.setLeaveHours(new BigDecimal(firstLeaveData.getLeaveSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                firstLeaveData.setLeaveMinutes(new BigDecimal(firstLeaveData.getLeaveSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
            }
            leaveDataList.add(firstLeaveData);
            //最后一天
            LeaveData lastLeaveData = new LeaveData();
            lastLeaveData.setStaffId(staffId);
            lastLeaveData.setLeaveType(leaveType);
            lastLeaveData.setLeaveDate(dateFormat.format(end));
            if(endDateArr.length == 1) {
                lastLeaveData.setLeaveHours((double)HOUR_WORK_DAY);
                lastLeaveData.setLeaveMinutes(HOUR_WORK_DAY * 60);
                lastLeaveData.setLeaveSeconds(HOUR_WORK_DAY * 60 * 60);
            }else{
                lastLeaveData.setLeaveSeconds((int)((dateTimeFormat.parse(lastLeaveData.getLeaveDate() + " " + endDateArr[1]).getTime() - dateTimeFormat.parse(lastLeaveData.getLeaveDate() + " " + signInSetupStartTime).getTime()) / 1000));
                //todo 需要把午休的时间去掉
                if(lastLeaveData.getLeaveSeconds() > HOUR_WORK_DAY * 60 * 60){
                    lastLeaveData.setLeaveSeconds(HOUR_WORK_DAY * 60 * 60);
                }
                lastLeaveData.setLeaveHours(new BigDecimal(lastLeaveData.getLeaveSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                lastLeaveData.setLeaveMinutes(new BigDecimal(lastLeaveData.getLeaveSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
            }
            //中间的日期
            int days = selectDays(firstLeaveData.getLeaveDate(),lastLeaveData.getLeaveDate()) - 1;
            if(days > 0){
                String date = firstLeaveData.getLeaveDate();
                for(int i = 0;i < days;i++){
                    date = selectNextDate(date);
                    if(StringUtils.isNotBlank(date)){
                        LeaveData leaveData = new LeaveData();
                        leaveData.setStaffId(staffId);
                        leaveData.setLeaveType(leaveType);
                        leaveData.setLeaveDate(date);
                        leaveData.setLeaveHours((double)HOUR_WORK_DAY);
                        leaveData.setLeaveMinutes(HOUR_WORK_DAY * 60);
                        leaveData.setLeaveSeconds(HOUR_WORK_DAY * 60 * 60);
                        leaveDataList.add(leaveData);
                    }else{
                        break;
                    }
                }
            }
            if(selectDays(firstLeaveData.getLeaveDate(),lastLeaveData.getLeaveDate()) > 0){
                leaveDataList.add(lastLeaveData);
            }
        }
        //排除周6周天
        List<LeaveData> leaveList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for(LeaveData leaveData : leaveDataList){
            if(OUTWORK.equals(leaveType)){
                leaveList.add(leaveData);
            }else{
                List<String> endWeekDate = Arrays.asList(END_WEEK_OF_WORK_DATE);
                if(endWeekDate.contains(leaveData.getLeaveDate())){
                    leaveList.add(leaveData);
                }else{
                    String[] date = leaveData.getLeaveDate().split("-");
                    cal.set(Integer.parseInt(date[0]),Integer.parseInt(date[1]) - 1,Integer.parseInt(date[2]));
                    if(cal.get(Calendar.DAY_OF_WEEK) != 1 && cal.get(Calendar.DAY_OF_WEEK) != 7){
                        //排除节假日
                        List<String> holidayDate2024 = Arrays.asList(HOLIDAY_DATE_2024);
                        List<String> holidayDate2025 = Arrays.asList(HOLIDAY_DATE_2025);
                        List<String> holidayDate = new ArrayList<>();
                        holidayDate.addAll(holidayDate2024);
                        holidayDate.addAll(holidayDate2025);
                        if(!holidayDate.contains(leaveData.getLeaveDate())){
                            leaveList.add(leaveData);
                        }
                    }
                }
            }
        }
        return leaveList;
    }

    /**
     * 生成加班数据
     * @param staffId
     * @param overtimeStartDate
     * @param overtimeEndDate
     * @param endDate
     * @param companyId
     * @return
     * @throws Exception
     */
    private List<OvertimeData> productOvertimeData(String staffId, String overtimeStartDate, String overtimeEndDate, String endDate, String companyId) throws Exception{
        List<OvertimeData> overtimeDataList = new ArrayList<>();
        if(StringUtils.isBlank(overtimeStartDate) || StringUtils.isBlank(overtimeEndDate)){
            return overtimeDataList;
        }
        String signInSetupStartTime = "09:00:00";
        String signInSetupEndTime = "18:00:00";

        String [] signInTime = findSignInSetup(companyId,SIGN_IN_TYPE_ORDINARY);
        if(Objects.nonNull(signInTime)) {
            signInSetupStartTime = signInTime[0] + ":00";
            signInSetupEndTime = signInTime[1] + ":00";
        }
        String [] startDateArr = overtimeStartDate.split(BLANK_CHAR);
        String [] endDateArr = overtimeEndDate.split(BLANK_CHAR);

        if(startDateArr.length == 2){
            if(overtimeStartDate.split(":").length <= 2){
                overtimeStartDate += ":00";
                startDateArr[1] += ":00";
            }
        }
        if(endDateArr.length == 2){
            if(overtimeEndDate.split(":").length <= 2){
                overtimeEndDate += ":00";
                endDateArr[1] += ":00";
            }
        }

        Date startDate = dateFormat.parse(startDateArr[0]);
        Date endDateT = dateFormat.parse(endDateArr[0]);
        if(startDate.compareTo(endDateT) > 0){
            return overtimeDataList;
        }
        if(startDate.compareTo(endDateT) == 0){
            OvertimeData overtimeData = new OvertimeData();
            overtimeData.setStaffId(staffId);
            overtimeData.setOvertimeDate(startDateArr[0]);

            if(startDateArr.length == 1 && endDateArr.length == 1){
                overtimeData.setOvertimeHours((double)HOUR_WORK_DAY);
                overtimeData.setOvertimeMinutes(HOUR_WORK_DAY * 60);
                overtimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
            }else if(startDateArr.length == 2 && endDateArr.length == 2){
                overtimeData.setOvertimeSeconds((int)((dateTimeFormat.parse(overtimeEndDate).getTime() - dateTimeFormat.parse(overtimeStartDate).getTime()) / 1000));
                //todo 需要把午休的时间去掉
                if(overtimeData.getOvertimeSeconds() > HOUR_WORK_DAY * 60 * 60){
                    overtimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
                }
                overtimeData.setOvertimeHours(new BigDecimal(overtimeData.getOvertimeSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                overtimeData.setOvertimeMinutes(new BigDecimal(overtimeData.getOvertimeSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
            }else{
                if(startDateArr.length == 1 && endDateArr.length == 2){
                    //todo 需要把午休的时间去掉
                    overtimeData.setOvertimeSeconds((int)((dateTimeFormat.parse(overtimeEndDate).getTime() - dateTimeFormat.parse(overtimeStartDate + " " + signInSetupStartTime).getTime()) / 1000));
                }else{
                    //todo 需要把午休的时间去掉
                    overtimeData.setOvertimeSeconds((int)((dateTimeFormat.parse(overtimeEndDate + " " + signInSetupEndTime).getTime() - dateTimeFormat.parse(overtimeStartDate).getTime()) / 1000));
                }
                if(overtimeData.getOvertimeSeconds() > HOUR_WORK_DAY * 60 * 60){
                    overtimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
                }
                overtimeData.setOvertimeHours(new BigDecimal(overtimeData.getOvertimeSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                overtimeData.setOvertimeMinutes(new BigDecimal(overtimeData.getOvertimeSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
            }
            overtimeDataList.add(overtimeData);
        }else{
            Date endDateD = dateFormat.parse(beforeDay(endDate));
            Date end = endDateD;
            if(endDateT.compareTo(endDateD) < 0){
                end = endDateT;
            }

            //第一天
            OvertimeData firstOvertimeData = new OvertimeData();
            firstOvertimeData.setStaffId(staffId);
            firstOvertimeData.setOvertimeDate(startDateArr[0]);
            if(startDateArr.length == 1) {
                firstOvertimeData.setOvertimeHours((double)HOUR_WORK_DAY);
                firstOvertimeData.setOvertimeMinutes(HOUR_WORK_DAY * 60);
                firstOvertimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
            }else{
                firstOvertimeData.setOvertimeSeconds((int)((dateTimeFormat.parse(startDateArr[0] + " " + signInSetupEndTime).getTime() - dateTimeFormat.parse(overtimeStartDate).getTime()) / 1000));
                //todo 需要把午休的时间去掉
                if(firstOvertimeData.getOvertimeSeconds() > HOUR_WORK_DAY * 60 * 60){
                    firstOvertimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
                }
                firstOvertimeData.setOvertimeHours(new BigDecimal(firstOvertimeData.getOvertimeSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                firstOvertimeData.setOvertimeMinutes(new BigDecimal(firstOvertimeData.getOvertimeSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
            }
            overtimeDataList.add(firstOvertimeData);
            //最后一天
            OvertimeData lastOvertimeData = new OvertimeData();
            lastOvertimeData.setStaffId(staffId);
            lastOvertimeData.setOvertimeDate(dateFormat.format(end));
            if(endDateArr.length == 1) {
                lastOvertimeData.setOvertimeHours((double)HOUR_WORK_DAY);
                lastOvertimeData.setOvertimeMinutes(HOUR_WORK_DAY * 60);
                lastOvertimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
            }else{
                lastOvertimeData.setOvertimeSeconds((int)((dateTimeFormat.parse(lastOvertimeData.getOvertimeDate() + " " + endDateArr[1]).getTime() - dateTimeFormat.parse(lastOvertimeData.getOvertimeDate() + " " + signInSetupStartTime).getTime()) / 1000));
                //todo 需要把午休的时间去掉
                if(lastOvertimeData.getOvertimeSeconds() > HOUR_WORK_DAY * 60 * 60){
                    lastOvertimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
                }
                lastOvertimeData.setOvertimeHours(new BigDecimal(lastOvertimeData.getOvertimeSeconds() / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue());
                lastOvertimeData.setOvertimeMinutes(new BigDecimal(lastOvertimeData.getOvertimeSeconds() / 60.0).setScale(1,RoundingMode.HALF_UP).intValue());
            }
            //中间的日期
            int days = selectDays(firstOvertimeData.getOvertimeDate(),lastOvertimeData.getOvertimeDate()) - 1;
            if(days > 0){
                String date = firstOvertimeData.getOvertimeDate();
                for(int i = 0;i < days;i++){
                    date = selectNextDate(date);
                    if(StringUtils.isNotBlank(date)){
                        OvertimeData overtimeData = new OvertimeData();
                        overtimeData.setStaffId(staffId);
                        overtimeData.setOvertimeDate(date);
                        overtimeData.setOvertimeHours((double)HOUR_WORK_DAY);
                        overtimeData.setOvertimeMinutes(HOUR_WORK_DAY * 60);
                        overtimeData.setOvertimeSeconds(HOUR_WORK_DAY * 60 * 60);
                        overtimeDataList.add(overtimeData);
                    }else{
                        break;
                    }
                }
            }
            if(selectDays(firstOvertimeData.getOvertimeDate(),lastOvertimeData.getOvertimeDate()) > 0){
                overtimeDataList.add(lastOvertimeData);
            }
        }
        List<String> holidayDate2024 = Arrays.asList(HOLIDAY_DATE_2024);
        List<String> holidayDate2025 = Arrays.asList(HOLIDAY_DATE_2025);
        List<String> holidayDate = new ArrayList<>();
        holidayDate.addAll(holidayDate2024);
        holidayDate.addAll(holidayDate2025);
        //排除普通周6周天
        List<OvertimeData> overtimeList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for(OvertimeData overtimeData : overtimeDataList){
            List<String> endWeekDate = Arrays.asList(END_WEEK_OF_WORK_DATE);
            if(endWeekDate.contains(overtimeData.getOvertimeDate())){
                overtimeList.add(overtimeData);
            }else{
                String[] date = overtimeData.getOvertimeDate().split("-");
                cal.set(Integer.parseInt(date[0]),Integer.parseInt(date[1]) - 1,Integer.parseInt(date[2]));
                if(cal.get(Calendar.DAY_OF_WEEK) != 1 && cal.get(Calendar.DAY_OF_WEEK) != 7){
                    overtimeList.add(overtimeData);
                }else if(holidayDate.contains(overtimeData.getOvertimeDate())){
                    overtimeList.add(overtimeData);
                }
            }
        }
        //根据日期来标记加班日是平时加班还是节假日加班,设置overtimeType
        for(OvertimeData overtimeData : overtimeList){
            if(holidayDate.contains(overtimeData.getOvertimeDate())){
                overtimeData.setOvertimeType(HOLIDAY_OVERTIME);
            }else{
                overtimeData.setOvertimeType(EIGHT_HOUR_OVERTIME);
            }
        }
        return overtimeList;
    }

    public int selectDays(String startDate,String endDate){
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            int days =  ((BigDecimal)baseBeanService.getObjectBySqlAndParams("SELECT TO_DATE(?, 'YYYY/MM/DD') - TO_DATE(?, 'YYYY/MM/DD') AS days " +
                    "FROM dual", new Object[]{endDate,startDate})).intValue();
            return days;
        }
        return 0;
    }

    public String selectNextDate(String startDate){
        if(StringUtils.isNotBlank(startDate)){
            return baseBeanService.getObjectBySqlAndParams("SELECT TO_DATE(?, 'YYYY/MM/DD') + NUMTODSINTERVAL(1, 'DAY') " +
                    "FROM dual", new Object[]{startDate}).toString();
        }
        return null;
    }

    private List<String> selectWeekdayDates(String startDate,String endDate){
        List<String> dateList = new ArrayList<>();
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            List<BaseBean> baseBeanList = baseBeanService.getListBeanBySqlAndParams("SELECT CHINA_WEEK AS WEEK,DATETIME " +
                    "FROM " +
                    "(" +
                    "SELECT " +
                    "to_char(TO_DATE(?, 'yyyy-MM-dd') - LEVEL, 'd') AS week," +
                    "decode(to_char(TO_DATE(?, 'yyyy-MM-dd') - LEVEL, 'd'), 1, 'Mon.', 2, 'Tues.', 3, 'Wed.', 4, 'Thurs.', 5, 'Fri.', 6, 'Sat.', 7, 'Sun.') AS china_week," +
                    "TO_CHAR((TO_DATE(?, 'yyyy-MM-dd') - (ROWNUM - 1)), 'yyyy-MM-dd') AS datetime " +
                    "FROM " +
                    "dual a " +
                    "CONNECT BY " +
                    "LEVEL <= trunc(TO_DATE(?, 'yyyy-MM-dd') - TO_DATE(?, 'yyyy-MM-dd')+ 1) " +
                    "ORDER BY " +
                    "datetime) d " +
                    "WHERE d.week IN (6, 7)", new Object[]{endDate,endDate,endDate,endDate,startDate});
            if(Objects.nonNull(baseBeanList)){
                for(Object obj : baseBeanList){
                    Object[] arr = (Object[]) obj;
                    dateList.add(arr[1].toString());
                }
                return dateList;
            }
        }
        return dateList;
    }

    private String checkOnTypeNameToEngName(String checkOnTypeName){
        switch (checkOnTypeName){
            case "事假":
                return LEAVE_ABSENCE;
            case "病假":
                return LEAVE_SICK;
            case "婚假":
                return LEAVE_MARRIAGE;
            case "年假":
                return LEAVE_ANNUAL;
            case "产假":
                return LEAVE_MATERNITY;
            case "丧假":
                return LEAVE_FUNERAL;
            case "工伤假":
                return LEAVE_WORKINJURY;
            case "轮岗休息":
                return LEAVE_ROTATIONREST;
        }
        return LEAVE_ABSENCE;
    }

    private void computeLeaveDiscountSalary(SignInCheckOn signInCheckOn,List<LeaveData> leaveDataList,String companyId,String checkOnTypeName){
        if(LEAVE_ABSENCE.equals(checkOnTypeName)){
            signInCheckOn.setLeaveAbsenceDayCount(0d);
            signInCheckOn.setLeaveAbsenceHours(0d);
            signInCheckOn.setLeaveAbsenceMinutes(0);
            signInCheckOn.setLeaveAbsenceSeconds(0);
            signInCheckOn.setLeaveAbsenceDiscountMoney(BigDecimal.ZERO);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(LEAVE_ABSENCE)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setLeaveAbsenceDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveAbsenceHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveAbsenceMinutes(leaveMinutes);
            signInCheckOn.setLeaveAbsenceSeconds(leaveSeconds);
            computeDiscountSalary(signInCheckOn,LEAVE_ABSENCE,companyId);
        }else if(LEAVE_SICK.equals(checkOnTypeName)){
            signInCheckOn.setLeaveSickDayCount(0d);
            signInCheckOn.setLeaveSickHours(0d);
            signInCheckOn.setLeaveSickMinutes(0);
            signInCheckOn.setLeaveSickSeconds(0);
            signInCheckOn.setLeaveSickDiscountMoney(BigDecimal.ZERO);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(LEAVE_SICK)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setLeaveSickDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveSickHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveSickMinutes(leaveMinutes);
            signInCheckOn.setLeaveSickSeconds(leaveSeconds);
            computeDiscountSalary(signInCheckOn,LEAVE_SICK,companyId);
        }else if(LEAVE_MARRIAGE.equals(checkOnTypeName)){
            signInCheckOn.setLeaveMarriageDayCount(0d);
            signInCheckOn.setLeaveMarriageHours(0d);
            signInCheckOn.setLeaveMarriageMinutes(0);
            signInCheckOn.setLeaveMarriageSeconds(0);
            signInCheckOn.setLeaveMarriageDiscountMoney(BigDecimal.ZERO);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(LEAVE_MARRIAGE)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setLeaveMarriageDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveMarriageHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveMarriageMinutes(leaveMinutes);
            signInCheckOn.setLeaveMarriageSeconds(leaveSeconds);
            computeDiscountSalary(signInCheckOn,LEAVE_MARRIAGE,companyId);
        }else if(LEAVE_ANNUAL.equals(checkOnTypeName)){
            signInCheckOn.setLeaveAnnualDayCount(0d);
            signInCheckOn.setLeaveAnnualHours(0d);
            signInCheckOn.setLeaveAnnualMinutes(0);
            signInCheckOn.setLeaveAnnualSeconds(0);
            signInCheckOn.setLeaveAnnualDiscountMoney(BigDecimal.ZERO);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(LEAVE_ANNUAL)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setLeaveAnnualDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveAnnualHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveAnnualMinutes(leaveMinutes);
            signInCheckOn.setLeaveAnnualSeconds(leaveSeconds);
            computeDiscountSalary(signInCheckOn,LEAVE_ANNUAL,companyId);
        }else if(LEAVE_MATERNITY.equals(checkOnTypeName)){
            signInCheckOn.setLeaveMaternityDayCount(0d);
            signInCheckOn.setLeaveMaternityHours(0d);
            signInCheckOn.setLeaveMaternityMinutes(0);
            signInCheckOn.setLeaveMaternitySeconds(0);
            signInCheckOn.setLeaveMaternityDiscountMoney(BigDecimal.ZERO);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(LEAVE_MATERNITY)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setLeaveMaternityDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveMaternityHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveMaternityMinutes(leaveMinutes);
            signInCheckOn.setLeaveMaternitySeconds(leaveSeconds);
            computeDiscountSalary(signInCheckOn,LEAVE_MATERNITY,companyId);
        }else if(LEAVE_FUNERAL.equals(checkOnTypeName)){
            signInCheckOn.setLeaveFuneralDayCount(0d);
            signInCheckOn.setLeaveFuneralHours(0d);
            signInCheckOn.setLeaveFuneralMinutes(0);
            signInCheckOn.setLeaveFuneralSeconds(0);
            signInCheckOn.setLeaveFuneralDiscountMoney(BigDecimal.ZERO);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(LEAVE_FUNERAL)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setLeaveFuneralDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveFuneralHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveFuneralMinutes(leaveMinutes);
            signInCheckOn.setLeaveFuneralSeconds(leaveSeconds);
            computeDiscountSalary(signInCheckOn,LEAVE_FUNERAL,companyId);
        }else if(LEAVE_WORKINJURY.equals(checkOnTypeName)){
            signInCheckOn.setLeaveWorkInjuryDayCount(0d);
            signInCheckOn.setLeaveWorkInjuryHours(0d);
            signInCheckOn.setLeaveWorkInjuryMinutes(0);
            signInCheckOn.setLeaveWorkInjurySeconds(0);
            signInCheckOn.setLeaveWorkInjuryDiscountMoney(BigDecimal.ZERO);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(LEAVE_WORKINJURY)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setLeaveWorkInjuryDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveWorkInjuryHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveWorkInjuryMinutes(leaveMinutes);
            signInCheckOn.setLeaveWorkInjurySeconds(leaveSeconds);
            computeDiscountSalary(signInCheckOn,LEAVE_WORKINJURY,companyId);
        }else if(LEAVE_ROTATIONREST.equals(checkOnTypeName)){
            signInCheckOn.setLeaveRotationRestDayCount(0d);
            signInCheckOn.setLeaveRotationRestHours(0d);
            signInCheckOn.setLeaveRotationRestMinutes(0);
            signInCheckOn.setLeaveRotationRestSeconds(0);
            signInCheckOn.setLeaveRotationRestDiscountMoney(BigDecimal.ZERO);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(LEAVE_ROTATIONREST)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setLeaveRotationRestDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveRotationRestHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setLeaveRotationRestMinutes(leaveMinutes);
            signInCheckOn.setLeaveRotationRestSeconds(leaveSeconds);
            computeDiscountSalary(signInCheckOn,LEAVE_ROTATIONREST,companyId);
        }else if(OUTWORK.equals(checkOnTypeName)){
            signInCheckOn.setOutworkDayCount(0d);
            signInCheckOn.setOutworkHours(0d);
            signInCheckOn.setOutworkMinutes(0);
            signInCheckOn.setOutworkSeconds(0);

            Double leaveHours = 0d;
            Integer leaveMinutes = 0;
            Integer leaveSeconds = 0;

            for(LeaveData leaveData : leaveDataList){
                if(leaveData.getLeaveType().equals(OUTWORK)){
                    leaveHours += leaveData.getLeaveHours();
                    leaveMinutes += leaveData.getLeaveMinutes();
                    leaveSeconds += leaveData.getLeaveSeconds();
                }
            }
            signInCheckOn.setOutworkDayCount(new BigDecimal(leaveHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setOutworkHours(new BigDecimal(leaveHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setOutworkMinutes(leaveMinutes);
            signInCheckOn.setOutworkSeconds(leaveSeconds);
        }
    }

    private void computeOvertimeSalary(SignInCheckOn signInCheckOn,List<OvertimeData> overtimeDataList,String companyId,String checkOnTypeName){
        if(EIGHT_HOUR_OVERTIME.equals(checkOnTypeName)){
            signInCheckOn.setEightHourOverTimeDayCount(0d);
            signInCheckOn.setEightHourOverTimeHours(0d);
            signInCheckOn.setEightHourOverTimeMinutes(0);
            signInCheckOn.setEightHourOverTimeSeconds(0);
            signInCheckOn.setEightHourOverTimeMoney(BigDecimal.ZERO);

            Double overtimeHours = 0d;
            Integer overtimeMinutes = 0;
            Integer overtimeSeconds = 0;

            for(OvertimeData overtimeData : overtimeDataList){
                if(overtimeData.getOvertimeType().equals(EIGHT_HOUR_OVERTIME)){
                    overtimeHours += overtimeData.getOvertimeHours();
                    overtimeMinutes += overtimeData.getOvertimeMinutes();
                    overtimeSeconds += overtimeData.getOvertimeSeconds();
                }
            }
            signInCheckOn.setEightHourOverTimeDayCount(new BigDecimal(overtimeHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setEightHourOverTimeHours(new BigDecimal(overtimeHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setEightHourOverTimeMinutes(overtimeMinutes);
            signInCheckOn.setEightHourOverTimeSeconds(overtimeSeconds);
            computeDiscountSalary(signInCheckOn,EIGHT_HOUR_OVERTIME,companyId);
        }else if(HOLIDAY_OVERTIME.equals(checkOnTypeName)){
            signInCheckOn.setHolidayOverTimeDayCount(0d);
            signInCheckOn.setHolidayOverTimeHours(0d);
            signInCheckOn.setHolidayOverTimeMinutes(0);
            signInCheckOn.setHolidayOverTimeSeconds(0);
            signInCheckOn.setHolidayOverTimeMoney(BigDecimal.ZERO);

            Double overtimeHours = 0d;
            Integer overtimeMinutes = 0;
            Integer overtimeSeconds = 0;

            for(OvertimeData overtimeData : overtimeDataList){
                if(overtimeData.getOvertimeType().equals(HOLIDAY_OVERTIME)){
                    overtimeHours += overtimeData.getOvertimeHours();
                    overtimeMinutes += overtimeData.getOvertimeMinutes();
                    overtimeSeconds += overtimeData.getOvertimeSeconds();
                }
            }
            signInCheckOn.setHolidayOverTimeDayCount(new BigDecimal(overtimeHours / HOUR_WORK_DAY).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setHolidayOverTimeHours(new BigDecimal(overtimeHours).setScale(2,RoundingMode.HALF_UP).doubleValue());
            signInCheckOn.setHolidayOverTimeMinutes(overtimeMinutes);
            signInCheckOn.setHolidayOverTimeSeconds(overtimeSeconds);
            computeDiscountSalary(signInCheckOn,HOLIDAY_OVERTIME,companyId);
        }
    }

    private String getEndDate(String checkOnTimeType,String endDate){
        String today = dateFormat.format(new Date());
        switch (checkOnTimeType){
            case TODAY:
                return today;
            case YESTERDAY:
                return endDate;
            case CURRENT_WEEK:
                return today;
            case BEFORE_WEEK:
                return endDate;
            case CURRENT_MONTH:
                return today;
            case BEFORE_MONTH:
                return endDate;
            case CURRENT_QUARTER:
                return today;
            case BEFORE_QUARTER:
                return endDate;
            case CURRENT_YEAR:
                return today;
            case BEFORE_YEAR:
                return endDate;
        }
        return endDate;
    }

    private boolean checkToday(String checkOnTimeType){
        switch (checkOnTimeType){
            case TODAY:
                return true;
            case YESTERDAY:
                return false;
            case CURRENT_WEEK:
                return false;
            case BEFORE_WEEK:
                return false;
            case CURRENT_MONTH:
                return false;
            case BEFORE_MONTH:
                return false;
            case CURRENT_QUARTER:
                return false;
            case BEFORE_QUARTER:
                return false;
            case CURRENT_YEAR:
                return false;
            case BEFORE_YEAR:
                return false;
        }
        return false;
    }

    private String notNeedDate(String checkOnTimeType,String endDate){
        switch (checkOnTimeType){
            case TODAY:
                return null;
            case YESTERDAY:
                return endDate;
            case CURRENT_WEEK:
                return null;
            case BEFORE_WEEK:
                return null;
            case CURRENT_MONTH:
                return null;
            case BEFORE_MONTH:
                return endDate;
            case CURRENT_QUARTER:
                return null;
            case BEFORE_QUARTER:
                return endDate;
            case CURRENT_YEAR:
                return null;
            case BEFORE_YEAR:
                return endDate;
        }
        return null;
    }

    public boolean checkRole(String staffId,String companyId){
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

    private void signInCheck(List<BaseBean> objectCheckOnList,Map<String,List<SignInResult>> signInResultMap,boolean isToday,String companyId) throws Exception{
        if(Objects.nonNull(objectCheckOnList) && !objectCheckOnList.isEmpty()){
            for (Object obj : objectCheckOnList) {
                Object[] checkOnArr = (Object[]) obj;
                String signDate = checkOnArr[5].toString();
                signDate = signDate.substring(0, signDate.indexOf(BLANK_CHAR));
                String signStartTime = checkOnArr[5].toString();
                signStartTime = signStartTime.substring(0, signStartTime.indexOf("."));
                String signEndTime = checkOnArr[6].toString();
                signEndTime = signEndTime.substring(0, signEndTime.indexOf("."));
                SignInResult signInResult = new SignInResult(checkOnArr[3].toString(),signDate
                        ,dateTimeFormat.parse(signStartTime),dateTimeFormat.parse(signEndTime),0D,0D,0,0);
                computeSignInTime(signInResult,companyId,isToday,SIGN_IN_TYPE_ORDINARY);
                //将签到结果汇总到Map中
                if(Objects.nonNull(signInResultMap.get(signInResult.getStaffId()))){
                    List<SignInResult> signInResultList =  signInResultMap.get(signInResult.getStaffId());
                    signInResultList.add(signInResult);
                }else{
                    List<SignInResult> signInResultList = new ArrayList<>();
                    signInResultList.add(signInResult);
                    signInResultMap.put(signInResult.getStaffId(),signInResultList);
                }
            }
        }
    }

    /**
     * 值班迟到早退计算
     * @param objectCheckOnList
     * @param dutyList
     * @param signInResultMap
     * @param isToday
     * @param companyId
     * @throws Exception
     */
    private void signInCheckNonOrdinary(List<BaseBean> objectCheckOnList,List<BaseBean> dutyList
            ,Map<String,List<SignInResult>> signInResultMap,boolean isToday,String companyId) throws Exception{
        if(Objects.nonNull(objectCheckOnList) && !objectCheckOnList.isEmpty()){
            for (Object obj : objectCheckOnList) {
                Object[] checkOnArr = (Object[]) obj;
                String signDate = checkOnArr[5].toString();
                signDate = signDate.substring(0, signDate.indexOf(BLANK_CHAR));
                String signStartTime = checkOnArr[5].toString();
                signStartTime = signStartTime.substring(0, signStartTime.indexOf("."));
                String signEndTime = checkOnArr[6].toString();
                signEndTime = signEndTime.substring(0, signEndTime.indexOf("."));
                SignInResult signInResult = new SignInResult(checkOnArr[3].toString(), signDate
                        , dateTimeFormat.parse(signStartTime), dateTimeFormat.parse(signEndTime), 0D, 0D, 0, 0);
                for (Object dutyObj : dutyList) {
                    Object[] dutyArr = (Object[]) dutyObj;
                    DutyScheduling dutyScheduling = new DutyScheduling(dutyArr[0].toString(),null, dutyArr[1].toString()
                            , dutyArr[2].toString().split(" ")[0],dutyArr[3].toString().split(" ")[0],dutyArr[4].toString());
                    List<String> dateArray = new ArrayList<>();
                    dateArray.add(dutyScheduling.getDutyStartDate());
                    int days = this.selectDays(dutyScheduling.getDutyStartDate(),dutyScheduling.getDutyEndDate());
                    if(days > 0){
                        String date = dutyScheduling.getDutyStartDate();
                        for(int i = 0;i < days;i++){
                            date = this.selectNextDate(date);
                            if(StringUtils.isNotBlank(date)){
                                dateArray.add(date);
                            }else{
                                break;
                            }
                        }
                    }
                    if(signInResult.getStaffId().equals(dutyScheduling.getDutyStaffId())
                            && dateArray.contains(signInResult.getSignInDate())){
                        computeSignInTime(signInResult, companyId, isToday, dutyScheduling.getDutyType());
                        //将签到结果汇总到Map中
                        if (Objects.nonNull(signInResultMap.get(signInResult.getStaffId()))) {
                            List<SignInResult> signInResultList = signInResultMap.get(signInResult.getStaffId());
                            Boolean have = false;
                            for(SignInResult sign : signInResultList){
                                if(signInResult.getSignInDate().equals(sign.getSignInDate())){
                                    sign.setSignInTime(signInResult.getSignInTime());
                                    sign.setSignOutTime(signInResult.getSignOutTime());
                                    sign.setLateMinutes(signInResult.getLateMinutes());
                                    sign.setLateSeconds(signInResult.getLateSeconds());
                                    sign.setLeaveEarlyMinutes(signInResult.getLeaveEarlyMinutes());
                                    sign.setLeaveEarlySeconds(signInResult.getLeaveEarlySeconds());
                                    have = true;
                                    break;
                                }
                            }
                            if(!have){
                                signInResultList.add(signInResult);
                            }
                        } else {
                            List<SignInResult> signInResultList = new ArrayList<>();
                            signInResultList.add(signInResult);
                            signInResultMap.put(signInResult.getStaffId(), signInResultList);
                        }
                    }
                }
            }
        }
    }

    private void signInNoNeed(String startDate,String endDate,Map<String,List<SignInResult>> signInResultMap,String companyId,List<String> staffIdList) throws Exception{
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        List<String> noNeedSignIn = Arrays.asList(NO_NEED_SIGN_IN);
        List<String> noNeedSignInDate = new ArrayList<>();
        for(String date : noNeedSignIn){
            if(dateFormat.parse(date).getTime() >= start.getTime() && dateFormat.parse(date).getTime() <= end.getTime()){
                noNeedSignInDate.add(date);
            }
        }
        if(Objects.nonNull(noNeedSignInDate) && !noNeedSignInDate.isEmpty()){
            String [] signInTime = findSignInSetup(companyId,SIGN_IN_TYPE_ORDINARY);
            for(String staffId : staffIdList){
                for (String signDate : noNeedSignInDate) {
                    String signStartTime = signDate + " " + signInTime[0] + ":00";
                    String signEndTime = signDate + " " + signInTime[1] + ":00";
                    SignInResult signInResult = new SignInResult(staffId,signDate
                            ,dateTimeFormat.parse(signStartTime),dateTimeFormat.parse(signEndTime),0D,0D,0,0);
                    //将签到结果汇总到Map中
                    if(Objects.nonNull(signInResultMap.get(signInResult.getStaffId()))){
                        List<SignInResult> signInResultList =  signInResultMap.get(signInResult.getStaffId());
                        Boolean have = false;
                        for(SignInResult sign : signInResultList){
                            if(signInResult.getSignInDate().equals(sign.getSignInDate())){
                                sign.setSignInTime(signInResult.getSignInTime());
                                sign.setSignOutTime(signInResult.getSignOutTime());
                                sign.setLateMinutes(signInResult.getLateMinutes());
                                sign.setLateSeconds(signInResult.getLateSeconds());
                                sign.setLeaveEarlyMinutes(signInResult.getLeaveEarlyMinutes());
                                sign.setLeaveEarlySeconds(signInResult.getLeaveEarlySeconds());
                                have = true;
                                break;
                            }
                        }
                        if(!have){
                            signInResultList.add(signInResult);
                        }
                    }else{
                        List<SignInResult> signInResultList = new ArrayList<>();
                        signInResultList.add(signInResult);
                        signInResultMap.put(signInResult.getStaffId(),signInResultList);
                    }
                }
            }
        }
    }

    private Map<String,List<SignInResult>> filterSignInCheck(List<BaseBean> objectCheckOnList,Map<String,List<SignInResult>> signInResultMap) throws Exception{
        Map<String,List<SignInResult>> newSignInResultMap = new HashMap<>();
        if(Objects.nonNull(objectCheckOnList) && !objectCheckOnList.isEmpty()){
            for (Object obj : objectCheckOnList) {
                Object[] checkOnArr = (Object[]) obj;
                String signDate = checkOnArr[5].toString();
                signDate = signDate.substring(0, signDate.indexOf(BLANK_CHAR));
                String signStartTime = checkOnArr[5].toString();
                signStartTime = signStartTime.substring(0, signStartTime.indexOf("."));
                String signEndTime = checkOnArr[6].toString();
                signEndTime = signEndTime.substring(0, signEndTime.indexOf("."));
                SignInResult signInResult = new SignInResult(checkOnArr[3].toString(),signDate
                        ,dateTimeFormat.parse(signStartTime),dateTimeFormat.parse(signEndTime),0D,0D,0,0);

                List<SignInResult> signInResultList = signInResultMap.get(signInResult.getStaffId());
                if(Objects.nonNull(signInResultList) && !signInResultList.isEmpty()){
                    for(SignInResult sign : signInResultList){
                        if(sign.getSignInDate().equals(signInResult.getSignInDate())){
                            //将签到结果汇总到Map中
                            if(Objects.nonNull(newSignInResultMap.get(sign.getStaffId()))){
                                List<SignInResult> signInList =  newSignInResultMap.get(sign.getStaffId());
                                signInList.add(sign);
                            }else{
                                List<SignInResult> signInList = new ArrayList<>();
                                signInList.add(sign);
                                newSignInResultMap.put(sign.getStaffId(),signInList);
                            }
                        }
                    }
                }
            }
        }
        return newSignInResultMap;
    }

    public double computeNoonBreakDurationHours(String signInTime,String signOutTime) throws Exception{
        double noonBreakDuration = 0;
        long singInTime = dateTimeFormat.parse("2025-01-01 " + signInTime + ":00").getTime();
        long singOutTime = dateTimeFormat.parse("2025-01-01 " + signOutTime + ":00").getTime();

        int seconds = (int)((singOutTime - singInTime) / 1000);
        seconds -=  HOUR_WORK_DAY * 60 * 60;
        if(seconds > 0){
            noonBreakDuration = new BigDecimal(seconds / 60.0 / 60.0).setScale(1,RoundingMode.HALF_UP).doubleValue();
        }
        return noonBreakDuration;
    }

    public long computeNoonBreakDurationSeconds(String signInTime,String signOutTime) throws Exception{
        long singInTime = dateTimeFormat.parse("2025-01-01 " + signInTime + ":00").getTime();
        long singOutTime = dateTimeFormat.parse("2025-01-01 " + signOutTime + ":00").getTime();

        int seconds = (int)((singOutTime - singInTime) / 1000);
        seconds -=  HOUR_WORK_DAY * 60 * 60;
        if(seconds > 0){
            return seconds;
        }
        return 0;
    }

    public String computeAfternoonSignInTime() throws Exception{
        String signInSetupStartTime = "09:00";
        String signInSetupEndTime = "18:00";
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return null;
        }
        String [] signInTime = findSignInSetup(account.getCompanyID(),SIGN_IN_TYPE_ORDINARY);
        if(Objects.nonNull(signInTime)) {
            signInSetupStartTime = signInTime[0];
            signInSetupEndTime = signInTime[1];
        }
        String afternoonSignInTime = AFTERNOON_SIGN_IN_TIME;
        long noonBreakDurationSeconds = computeNoonBreakDurationSeconds(signInSetupStartTime,signInSetupEndTime);
        long time = dateTimeFormat.parse( "2025-01-01 " + MORNING_SIGN_OUT_TIME + ":00").getTime() + noonBreakDurationSeconds * 1000;
        Date afternoonSignInDate = new Date(time);
        String date = dateTimeFormat.format(afternoonSignInDate);
        afternoonSignInTime = date.substring(date.indexOf(" ") + 1, date.lastIndexOf(":"));
        return afternoonSignInTime;
    }

    public double leaveHours(double hours,String date,String startTime,String endTime, String signInSetupStartTime,String signInSetupEndTime) throws Exception{
        String afternoonSignInTime = computeAfternoonSignInTime();
        double noonBreakDurationHours = computeNoonBreakDurationHours(signInSetupStartTime, signInSetupEndTime);
        long noonBreakDurationSeconds = computeNoonBreakDurationSeconds(signInSetupStartTime, signInSetupEndTime);
        String signOutTStr = date + " " + MORNING_SIGN_OUT_TIME;
        String signInTStr = date + " " + afternoonSignInTime;
        long signOutT = dateTimeFormat.parse(signOutTStr + ":00").getTime();
        long signInT = dateTimeFormat.parse(signInTStr + ":00").getTime();

        long startT = dateTimeFormat.parse(startTime + ":00").getTime();
        long endT = dateTimeFormat.parse(endTime + ":00").getTime();
        if (startT <= signOutT && endT >= signInT) {
            hours -= noonBreakDurationHours;
        } else if (startT <= signOutT && endT >= signOutT && endT <= signInT) {
            hours = selectHours(startTime, signOutTStr);
        } else if (startT >= signOutT && startT <= signInT && endT >= signInT) {
            hours = selectHours(signInTStr, endTime);
        } else if (startT >= signOutT && endT <= signInT) {
            hours = 0;
        }
        return hours;
    }

    public double selectHours(String startTime,String endTime){
        if(StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)){
            return  ((BigDecimal)baseBeanService.getObjectBySqlAndParams("SELECT ROUND((To_date(? , 'yyyy-mm-dd hh24-mi') - To_date(?, 'YYYY-MM-DD HH24:MI')) * 24, 1)  FROM DUAL"
                    , new Object[]{endTime,startTime})).doubleValue();
        }
        return 0;
    }
}
