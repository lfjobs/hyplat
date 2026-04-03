package hy.ea.human.service.impl;

import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.human.DutyScheduling;
import hy.ea.human.service.DutyApplyService;
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
public class DutyApplyServiceImpl implements DutyApplyService {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private SignInCheckOnService signInCheckOnService;

    @Override
    public void addDutyApply(DutyScheduling dutyScheduling) throws Exception {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (account == null) {
            return;
        }
        //校验重复请假，目前校验同一天不允许有多次值班
        checkDutyApplyDate(dutyScheduling,null);

        dutyScheduling.setCompanyId(account.getCompanyID());
        dutyScheduling.setDutyStaffId(account.getStaffID());
        dutyScheduling.setSchedulingDate(dateTimeFormat.format(new Date()));
        baseBeanService.save(dutyScheduling);
    }

    @Override
    public void updateDutyApply(DutyScheduling dutyScheduling) throws Exception {
        DutyScheduling duty =  (DutyScheduling) baseBeanService
                .getBeanByHqlAndParams("from DutyScheduling where dutySchedulingId = ?", new Object[]{dutyScheduling.getDutySchedulingId()});
        if(Objects.nonNull(duty)){
            Map<String, Object> session = ActionContext.getContext().getSession();
            CAccount account = (CAccount) session.get("account");
            if (account == null) {
                return;
            }
            //校验重复请假，目前校验同一天不允许有多次值班
            checkDutyApplyDate(dutyScheduling,dutyScheduling.getDutySchedulingId());

            dutyScheduling.setDutyStaffId(account.getStaffID());
            dutyScheduling.setCompanyId(duty.getCompanyId());
            dutyScheduling.setSchedulingDate(duty.getSchedulingDate());
            baseBeanService.update(dutyScheduling);
        }
    }

    @Override
    public String findDutyApplyList(PageForm pageForm,String dutyApplyTime) throws Exception {
        pageForm = getList(pageForm,dutyApplyTime);
        if(Objects.nonNull(pageForm)){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("pageForm", pageForm);
            JSONObject obj = JSONObject.fromObject(resultMap);
            return obj.toString();
        }
        return null;
    }

    public PageForm getList(PageForm pageForm,String dutyApplyTime) {
        if(Objects.isNull(pageForm) || Objects.isNull(dutyApplyTime)){
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
        paramList.add(generateDate(dutyApplyTime));
        String sql = getDataUseSql();
        pageForm = baseBeanService.getPageFormBySQL ((null != pageForm ? pageForm.getPageNumber() : 1),
                (null != pageForm ? pageForm.getPageSize() : 10), sql,"select count(1) "
                        + sql.substring(sql.indexOf("FROM")),paramList.toArray());
        return pageForm;
    }

    private String getDataUseSql() {
        return "SELECT DT_DUTY_SCHEDULING_ID,DUTY_STAFF_ID,DUTY_START_DATE,DUTY_END_DATE,DUTY_TYPE,SCHEDULING_DATE,ADDRESS " +
                "                                                FROM DT_DUTY_SCHEDULING " +
                "                                                WHERE COMPANY_ID = ? " +
                "                                                AND DUTY_STAFF_ID = ? " +
                "                                                AND TO_DATE(DUTY_START_DATE,'YYYY-MM-DD HH24:MI:SS') >= TO_DATE(?, 'YYYY-MM-DD') " +
                "                                                ORDER BY ADDRESS,DUTY_START_DATE,DUTY_TYPE";
    }

    @Override
    public void deleteDutyApplyById(String id) {
        DutyScheduling dutyScheduling =  (DutyScheduling) baseBeanService
                .getBeanByHqlAndParams("from DutyScheduling where dutySchedulingId = ?", new Object[]{id});
        if(Objects.nonNull(dutyScheduling)){
            baseBeanService.deleteBeanByKey(DutyScheduling.class,dutyScheduling.getDutySchedulingId());
        }
    }

    @Override
    public String findDutyApplyById(String id) {
        DutyScheduling dutyScheduling =  (DutyScheduling) baseBeanService
                .getBeanByHqlAndParams("from DutyScheduling where dutySchedulingId = ?", new Object[]{id});
        if(Objects.nonNull(dutyScheduling)){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dutyScheduling", dutyScheduling);
            JSONObject obj = JSONObject.fromObject(map);
            return obj.toString();
        }
        return null;
    }

    private String generateDate(String dutyApplyTime){
        String date = selectDate("0");
        if(TIME_RANGE_1M.equals(dutyApplyTime)){
            date = selectDate("0");
        }else if(TIME_RANGE_3M.equals(dutyApplyTime)){
            date = selectDate("-2");
        }else if(TIME_RANGE_6M.equals(dutyApplyTime)){
            date = selectDate("-5");
        }else if(TIME_RANGE_1Y.equals(dutyApplyTime)){
            date = selectDate("-11");
        }else if(TIME_RANGE_2Y.equals(dutyApplyTime)){
            date = selectDate("-23");
        }
        return date;
    }

    private String selectDate(String month){
        return baseBeanService.getObjectBySqlAndParams("SELECT ADD_MONTHS(TRUNC(SYSDATE, 'MM'), ?) AS NEXT_MONTH_START FROM DUAL"
                , new Object[]{month}).toString();
    }

    private boolean selectDutyApplyList(String dutyApplyDate,String id){
        List<Object> paramList = new ArrayList<>();
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        if (Objects.isNull(account)) {
            return false;
        }
        String keyStr = "";
        if(Objects.nonNull(id)){
            paramList.add(id);
            keyStr = "AND DT_DUTY_SCHEDULING_ID != ? ";
        }
        paramList.add(account.getCompanyID());
        paramList.add(account.getStaffID());
        paramList.add(dutyApplyDate);
        paramList.add(dutyApplyDate);
        List<BaseBean> dutyApplyList =  baseBeanService.getListBeanBySqlAndParams("SELECT DT_DUTY_SCHEDULING_ID " +
                        "FROM DT_DUTY_SCHEDULING " +
                        "WHERE 1=1 " +
                        keyStr +
                        "AND COMPANY_ID = ? " +
                        "AND DUTY_STAFF_ID = ? " +
                        "AND TO_DATE(?, 'YYYY-MM-DD') >= TO_DATE(SUBSTR(DUTY_START_DATE,0,10),'YYYY-MM-DD') " +
                        "AND TO_DATE(?, 'YYYY-MM-DD') <= TO_DATE(SUBSTR(DUTY_END_DATE,0,10),'YYYY-MM-DD') ", paramList.toArray());
        if(Objects.nonNull(dutyApplyList) && dutyApplyList.size() > 0){
            return false;
        }
        return true;
    }

    /**
     * 校验重复加班，目前校验同一天不允许有多次值班
     * @param
     */
    private void checkDutyApplyDate(DutyScheduling dutyScheduling,String id) throws Exception{
        //带时间则不做校验
        if(dutyScheduling.getDutyStartDate().split(" ").length > 1){
            return;
        }
        List<String> dateArray = new ArrayList<>();
        String startDate = dutyScheduling.getDutyStartDate();
        String endDate = dutyScheduling.getDutyEndDate();
        dateArray.add(startDate);
        int days = signInCheckOnService.selectDays(startDate,endDate);
        if(days > 0){
            String date = startDate;
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
            if(!selectDutyApplyList(date,id)){
                throw new RuntimeException("值班时间重复");
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

    /**
     * 值班查询
     * @param startDate
     * @param endDate
     * @param staffIdList
     * @param companyId
     * @return
     */
    public List findDutyList(String startDate,String endDate, List<String> staffIdList, String companyId){
        List<Object> paramList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("SELECT DT_DUTY_SCHEDULING_ID,DUTY_STAFF_ID,DUTY_START_DATE,DUTY_END_DATE,DUTY_TYPE,SCHEDULING_DATE " +
                "                             FROM DT_DUTY_SCHEDULING " +
                "                             WHERE 1=1 ");
        String tempStr = "";
        for(String staffId : staffIdList){
            tempStr += "?,";
            paramList.add(staffId);
        }
        if(StringUtils.isNotBlank(tempStr)){
            sb.append("AND DUTY_STAFF_ID IN ("+tempStr.substring(0,tempStr.length() - 1)+") ");
        }

        sb.append("AND COMPANY_ID = ? " +
                "AND TO_DATE(DUTY_START_DATE,'YYYY-MM-DD HH24:MI:SS') >= TO_DATE(?, 'YYYY-MM-DD') " +
                "AND TO_DATE(DUTY_END_DATE,'YYYY-MM-DD HH24:MI:SS') < TO_DATE(?, 'YYYY-MM-DD') " +
                "ORDER BY DUTY_START_DATE,DUTY_TYPE");
        String sql = sb.toString();
        paramList.add(companyId);
        paramList.add(startDate);
        paramList.add(endDate);
        return baseBeanService.getListBeanBySqlAndParams(sql, paramList.toArray());
    }

}
