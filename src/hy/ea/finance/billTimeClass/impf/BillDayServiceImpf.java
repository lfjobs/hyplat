package hy.ea.finance.billTimeClass.impf;

import hy.ea.bo.finance.billTimeClass.CashierBillDay;
import hy.ea.bo.finance.billTimeClass.GoodsBillsDay;
import hy.ea.finance.billTimeClass.BillDayService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillDayServiceImpf implements BillDayService {

    /**
     *
     * @param bean
     * @param type bean来源  1:CashierBills   2:GoodsBills
     * @param beanList
     */
    public void convertFromModel(BaseBean bean, int type, List<BaseBean> beanList){
        String falg="00";
        CashierBillDay cashierBillDay=null;
        GoodsBillsDay goodsBillsDay=null;
        if (bean==null){
            falg="01";
        }
        if (type==1){
            cashierBillDay=new CashierBillDay();
            BeanUtils.copyProperties(bean,cashierBillDay);
            beanList.add(cashierBillDay);
        }else if (type==2){
            goodsBillsDay=new GoodsBillsDay();
            BeanUtils.copyProperties(bean,goodsBillsDay);
            beanList.add(goodsBillsDay);
        }
    }

    /**
     *
     * @param parameter 参数值
     * @param type 参数分类
     *             1:id
     *             2:单据号
     * @return Map
     *         falg 返回值状态:
     *                  00：正常
     *                  01：参数值为空
     *                  02：参数值格式不正确
     *                  03: 参数日期不在查询范围内
     *         c_s:sql单据表名称
     *         c_h:hql单据表名称
     *         g_s:sql单据产品表名称
     *         g_h:hql单据产品表名称
     */
    public Map gatTableNameByPar(String parameter,int type) throws ParseException {
        Map<String, String> map = new HashMap<String, String>();
        String falg="00";
        if (parameter==null||parameter.equals("")){
            falg="01";
        }
        if (type==1||type==2){
            long nowtime=(new Date()).getTime();
            String a="";
            if (type==1){
                a=parameter.substring(12,20);
            }else if (type==2){
                a=parameter.substring(0,8);
            }
            String regex = "^[A-Za-z]";
            if (a.matches(regex)){
                falg="02";
            }else {
                long partime=(Utilities.getDateFromString(a,"yyyy-MM-dd")).getTime();
                //判断日
                if (partime>nowtime){
                    //获取本周星期一
                    long wtime=Utilities.getDateFromString(DateUtil.getCurrentMonday("yyyy-MM-dd"),"yyyy-MM-dd").getTime();
                    //判断周
                    if (partime>wtime){
                        //获取本月一号日期
                        String m = DateUtil.getDateOfMonthBegin(DateUtil.getMonthOfDate(new Date())+"",DateUtil.C_DATE_PATTON_DEFAULT);
                        long mtime=Utilities.getDateFromString(m,DateUtil.C_DATE_PATTON_DEFAULT).getTime();
                        //判断月
                        if (partime>mtime){
                            //获取当季第一天
                            String[] s = DateUtil.getseasonDay(null,DateUtil.C_DATE_PATTON_DEFAULT);
                            long stime=Utilities.getDateFromString(s[0],DateUtil.C_DATE_PATTON_DEFAULT).getTime();
                            //判断季
                            if (partime>stime){
                                //获取当年第一天
                                int y = DateUtil.getYearOfDate(new Date());
                                long ytime=Utilities.getDateFromString(y+"-01-01",DateUtil.C_DATE_PATTON_DEFAULT).getTime();
                                //判断年
                                if (partime>ytime){
                                    falg="03";
                                }else {
                                    map.put("c_s","dt_cb_year");
                                    map.put("c_h","CashierBillYear");
                                    map.put("g_s","dt_gb_year");
                                    map.put("g_h","GoodsBillsYear");
                                }
                            }else {
                                map.put("c_s","dt_cb_season");
                                map.put("c_h","CashierBillSeason");
                                map.put("g_s","dt_gb_season");
                                map.put("g_h","GoodsBillsSeason");
                            }
                        }else {
                            map.put("c_s","dt_cb_month");
                            map.put("c_h","CashierBillMonth");
                            map.put("g_s","dt_gb_month");
                            map.put("g_h","GoodsBillsMonth");
                        }
                    }else {
                        map.put("c_s","dt_cb_weeks");
                        map.put("c_h","CashierBillWeeks");
                        map.put("g_s","dt_gb_weeks");
                        map.put("g_h","GoodsBillsWeeks");
                    }
                }else {
                    map.put("c_s","dt_cb_day");
                    map.put("c_h","CashierBillDay");
                    map.put("g_s","dt_gb_day");
                    map.put("g_h","GoodsBillsDay");
                }
            }
        }
        return map;
    }

    /**
     *
     * @param tableType 表明类型
     *               1：c_s-sql单据表名称
     *               2：c_h-hql单据表名称
     *               3：g_s-sql单据产品表名称
     *               4：g_h-hql单据产品表名称
     * @param parameter 参数值
     * @param type 参数分类
     *             1:id
     *             2:单据号
     * @return
     * @throws ParseException
     */
    public String getTableNeame(int tableType,String parameter,int type) throws ParseException {
        Map parMap=gatTableNameByPar(parameter,type);
        String falg=parMap.get("falg").toString();
        if (falg!=null&&falg.equals("00")){
            if (tableType==1){
                return parMap.get("c_h").toString();
            }
            else if (tableType==2){
                return parMap.get("c_s").toString();
            }
            else if (tableType==3){
                return parMap.get("g_h").toString();
            }
            else if (tableType==4){
                return parMap.get("g_s").toString();
            }
        }
        return "";
    }
}
