package hy.ea.human.action;

import hy.ea.human.service.SalarySettlementService;
import hy.ea.util.AutoTaskUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Service
public class TaskBean {
    private static final String EXECUTE_TRUE = "TRUE";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private SalarySettlementService salarySettlementService;

    /**
     * 每月1号0点1分
     */
    @Scheduled(cron = "0 1 0 1 * ?")
    public void runMonthTask() throws Exception {
        String now = sdf.format(new Date());
        System.out.println("start runMonthTask:" + now + " ...");
        String y = "2025";
        String m = "01";
        String[] date = now.split("-");
        int year = Integer.valueOf(date[0]);
        int month = Integer.valueOf(date[1]);
        if(month == 1){
            year = year - 1;
            m = "12";
        }else{
            month = month - 1;
            if(month <= 9){
                m = "0" + month;
            }else{
                m = month + "";
            }
        }
        y = String.valueOf(year);
        salarySettlementService.saveEmployeeSalaryAdd(y,m,null);
        System.out.println("end runMonthTask:" + now + " ...");
    }

    /**
     * 每天3点1分
     * @throws Exception
     */
    @Scheduled(cron = "0 1 3 * * ?")
    @Transactional
    public void runDayTask() throws Exception {
        //计算当月工资，但排除1号
        String now = sdf.format(new Date());
        String[] date = now.split("-");
        String year = date[0];
        String month = date[1];
        String day = date[2];
        salarySettlementService.deleteSalaryCurMonthAdd();
        if(!"01".equals(day)){
            salarySettlementService.saveCurEmployeeSalaryAdd(year,month);
        }
    }

    /**
     * 工资批量初始化
     * @throws Exception
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void runTask() throws Exception {
        String[] years = AutoTaskUtils.getSettlementYear();
        String[] months = AutoTaskUtils.getMonth();
        String[] companyIds = AutoTaskUtils.getCompanyId();
        if(EXECUTE_TRUE.equals(AutoTaskUtils.getExecute())){
            salarySettlement(years,months,companyIds);
        }
    }

    /**
     * 批量生成指定年份，月份，公司工资
     * @param
     */
    @Transactional
    public void salarySettlement(String[] years,String[] months,String[] companyIds) throws Exception {
        if(Objects.isNull(years)){
            System.out.println("年份为空，不生成工资");
            return;
        }
        salarySettlementService.deleteSalaryMonthAdd(years,months,companyIds);

        for(String year : years){
            int month = 0;
            Date date = new Date();
            String currentYear = sdf.format(date).split("-")[0];
            String currentMonth = sdf.format(date).split("-")[1];
            if(Integer.valueOf(year).intValue() > Integer.valueOf(currentYear).intValue()){
                System.out.println("年份大于今年，不生成工资");
                return;
            }else if(Integer.valueOf(year).intValue() == Integer.valueOf(currentYear).intValue()){
                month = Integer.valueOf(currentMonth) - 1;
            }else{
                month = 12;
            }

            for(int i = 1; i <= month; i++){
                String m = Integer.toString(i);
                if(i < 10){
                    m = "0" + m;
                }
                Boolean isRun = true;
                if(Objects.nonNull(months)){
                    if(!Arrays.asList(months).contains(m)){
                        isRun = false;
                    }
                }
                if(isRun){
                    salarySettlementService.saveEmployeeSalaryAdd(year,m,companyIds);
                }
            }
        }
    }
}
