package hy.ea.finance.service.impl;

import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.finance.service.ReportService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Created by LG on 2018/11/12.
 */
@Service
public class reportServiceImpl implements ReportService {

    @Resource
    private BaseBeanDao beandao;
    @Resource
    private BaseBeanService baseBeanService;

    @Override
    public EnumMap<pageFormAadSum,Object> getGorssListBySch(int pageNumber, ProductPackaging product, CashierBills cashierBills,String timeInterval,String companyID,String type,String reportType) {
        StringBuilder sql  =  new StringBuilder();
        StringBuilder sqlByCount  =  new StringBuilder();
        List<Object> param  = new ArrayList<Object>();
        PageForm pageForm  =  new PageForm();
        EnumMap<pageFormAadSum,Object> retMap = new EnumMap<pageFormAadSum, Object>(pageFormAadSum.class);
        String sqlsum = "SELECT SUM(TAB.SALESCOUNT),SUM(TAB.SALESAMOUNT),SUM(PROFITS),SUM(COSTAMOUNT)";
        //列表
        if("all".equals(type)){
            if("gross".equals(reportType)){
                sql.append("SELECT PP.GOODSNAME,PP.BARCODE,PP.REMARK,GB.PRICE,GB.COSTMONEY,SUM(GB.QUANTITY) AS SALESCOUNT,SUM(GB.QUANTITY) * GB.PRICE AS SALESAMOUNT,");
                sql.append("  SUM(GB.QUANTITY) * GB.COSTMONEY AS COSTAMOUNT,SUM(GB.QUANTITY) * GB.PRICE - SUM(GB.QUANTITY) * GB.COSTMONEY AS PROFITS,");
                sql.append(" COUNT(CB.CASHIERBILLSID) AS ORDERSCOUNT,COUNT(DISTINCT OBA.OA_SCCID) AS NUMBERCONSUMER,CC.COMPANYNAME");
            }else if("refin".equals(reportType)){
                sql.append("SELECT PP.GOODSNAME,PP.BARCODE,PP.REMARK,GB.COSTMONEY,SUM(GB.QUANTITY) AS SALESCOUNT,");
                sql.append("  SUM(GB.QUANTITY) * GB.COSTMONEY AS COSTAMOUNT,");
                sql.append(" COUNT(CB.CASHIERBILLSID) AS ORDERSCOUNT,COUNT(DISTINCT OBA.OA_SCCID) AS NUMBERCONSUMER,CC.COMPANYNAME,SUM(GB.QUANTITY) * GB.PRICE AS SALESAMOUNT");
                sqlsum = sqlsum.replace(",SUM(PROFITS)","");
            }else if("sales".equals(reportType)){
                sql.append("SELECT PP.GOODSNAME,PP.BARCODE,PP.REMARK,GB.PRICE,SUM(GB.QUANTITY) AS SALESCOUNT,SUM(GB.QUANTITY) * GB.PRICE AS SALESAMOUNT,'现金',");
                sql.append(" COUNT(CB.CASHIERBILLSID) AS ORDERSCOUNT,COUNT(DISTINCT OBA.OA_SCCID) AS NUMBERCONSUMER,CC.COMPANYNAME");
                sqlsum = sqlsum.replace(",SUM(PROFITS)","");
                sqlsum = sqlsum.replace(",SUM(COSTAMOUNT)","");
            }
        }else {
            sql.append("SELECT PP.GOODSNAME,PP.BARCODE,PP.REMARK,GB.PRICE,GB.COSTMONEY,SUM(GB.QUANTITY) AS SALESCOUNT,SUM(GB.QUANTITY) * GB.PRICE AS SALESAMOUNT,");
            sql.append("  SUM(GB.QUANTITY) * GB.COSTMONEY AS COSTAMOUNT,SUM(GB.QUANTITY) * GB.PRICE - SUM(GB.QUANTITY) * GB.COSTMONEY AS PROFITS,");
            sql.append(" COUNT(CB.CASHIERBILLSID) AS ORDERSCOUNT,COUNT(DISTINCT OBA.OA_SCCID) AS NUMBERCONSUMER,CC.COMPANYNAME,GB.PPID");
        }
        sql.append(" FROM  DTGOODSBILLS GB LEFT JOIN DTCASHIERBILLS CB ON CB.CASHIERBILLSID = GB.CASHIERBILLSID");
        sql.append(" LEFT JOIN DT_PRODUCTPACKAGING PP ON PP.PPID = GB.PPID");
        sql.append(" LEFT JOIN DTCONTACTCOMPANY CC ON CC.CCOMPANYID = PP.CCOMPANYID");
        sql.append(" LEFT JOIN DT_ORDER_BILL_ADD OBA ON CB.CASHIERBILLSID = OBA.OA_BILL_ID");
        sql.append(" WHERE CB.FKSTATUS NOT IN (?, ?, ?, ?) AND CB.BILLSTYPE = ? AND CB.STATUSBILL = ? AND (GB.PREMIUMS IS NULL OR GB.PREMIUMS = ?) AND CB.COMPANYID = ?  ");
        //合计
        sqlByCount.append("SELECT COUNT(DISTINCT CB.CASHIERBILLSID), COUNT(DISTINCT OBA.OA_SCCID) FROM DTCASHIERBILLS CB");
        sqlByCount.append(" LEFT JOIN DT_ORDER_BILL_ADD OBA ON CB.CASHIERBILLSID = OBA.OA_BILL_ID");
        sqlByCount.append(" LEFT JOIN DTGOODSBILLS GB ON CB.CASHIERBILLSID = GB.CASHIERBILLSID");
        sqlByCount.append(" LEFT JOIN DT_PRODUCTPACKAGING PP ON PP.PPID = GB.PPID");
        sqlByCount.append(" LEFT JOIN DTCONTACTCOMPANY CC  ON PP.CCOMPANYID = CC.CCOMPANYID");
        sqlByCount.append(" WHERE CB.FKSTATUS NOT IN (?, ?, ?, ?) AND CB.BILLSTYPE = ? AND CB.STATUSBILL = ?  AND (GB.PREMIUMS IS NULL OR GB.PREMIUMS = ?) AND CB.COMPANYID = ?  ");
        param.add("01");param.add("09");param.add("15");param.add("18");
        param.add("项目收入预算单");
        param.add("04");
        param.add("0");
        param.add(companyID);
        if(timeInterval!=null && !"".equals(timeInterval)){
            String [] time = timeInterval.split("至");
            sql.append("AND OBA.FKDATE BETWEEN TO_DATE( ? ,'YYYY-MM-DD HH24:MI:SS') AND TO_DATE( ? ,'YYYY-MM-DD HH24:MI:SS')");
            sqlByCount.append("AND OBA.FKDATE BETWEEN TO_DATE( ? ,'YYYY-MM-DD HH24:MI:SS') AND TO_DATE( ? ,'YYYY-MM-DD HH24:MI:SS')");
            param.add(time[0]);param.add(time[1]);
        }
        if(product.getBarCode()!=null && !"".equals(product.getBarCode())){
            sql.append(" AND (PP.GOODSNAME LIKE ? OR PP.BARCODE LIKE ? )");
            sqlByCount.append(" AND (PP.GOODSNAME LIKE ? OR PP.BARCODE LIKE ? )");
            param.add("%"+product.getBarCode()+"%");
            param.add("%"+product.getBarCode()+"%");
        }
        if(cashierBills.getJournalNum()!=null && !"".equals(cashierBills.getJournalNum())){
            sql.append("AND CB.JOURNALNUM = ? ");
            sqlByCount.append("AND CB.JOURNALNUM = ? ");
            param.add(cashierBills.getJournalNum());
        }
        if(product.getCcompanyName()!=null && !"".equals(product.getCcompanyName())){
            sql.append(" AND CC.COMPANYNAME LIKE ? ");
            sqlByCount.append(" AND CC.COMPANYNAME LIKE ? ");
            param.add("%"+product.getCcompanyName()+"%");
        }
        sql.append(" GROUP BY GB.PPID, GB.PRICE, PP.GOODSNAME, PP.BARCODE, PP.REMARK, GB.COSTMONEY, CC.COMPANYNAME");
        sqlsum+= " FROM ("+sql+" ) TAB";
        Object sum = baseBeanService.getObjectBySqlAndParams(sqlsum,param.toArray());
        Object count = baseBeanService.getObjectBySqlAndParams(sqlByCount.toString(),param.toArray());
        pageForm  = baseBeanService.getPageFormBySQL(pageNumber==0?1:pageNumber,6,sql.toString(),
                "select count(*) from ("+sql.toString()+")",param.toArray());
        retMap.put(pageFormAadSum.pageForm,pageForm);
        retMap.put(pageFormAadSum.sum,sum);
        retMap.put(pageFormAadSum.count,count);
        if("all".equals(type)){
            List<BaseBean> reportList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),param.toArray());
            retMap.put(pageFormAadSum.pageForm,reportList);
        }
        return retMap;
    }

    @Override
    public EnumMap<pageFormAadSum, Object> getOrderListBySch(int pageNumber, String accountOrName, CashierBills cashierBills, String timeInterval, String companyID, String type) {
        StringBuilder sql  =  new StringBuilder();
        List<Object> param  = new ArrayList<Object>();
        PageForm pageForm  =  new PageForm();
        EnumMap<pageFormAadSum,Object> retMap = new EnumMap<pageFormAadSum, Object>(pageFormAadSum.class);
        sql.append("SELECT CB.JOURNALNUM,EC.ACCOUNT,HS.STAFFNAME,CB.INPUTNAME,CB.PRICESUB,CASE WHEN CB.WFSTATUS4=00 THEN '微信支付' WHEN CB.WFSTATUS4=01 THEN '支付宝支付' WHEN CB.WFSTATUS4=02 THEN '银联支付' WHEN CB.WFSTATUS4=03 THEN '线下转账' WHEN CB.WFSTATUS4=04 THEN '钱盒子支付' WHEN CB.WFSTATUS4=05 THEN '积分支付' WHEN CB.WFSTATUS4=06 THEN '现金支付'END ,TO_CHAR( OBA.FKDATE ,'YYYY-MM-DD HH24:MI:SS'),OBA.OA_SCCID");
        sql.append(" FROM DTCASHIERBILLS CB LEFT JOIN DT_ORDER_BILL_ADD OBA ON CB.CASHIERBILLSID = OBA.OA_BILL_ID");
        sql.append(" LEFT JOIN T_ESHOP_CUSCOM EC ON OBA.OA_SCCID = EC.SCCID");
        sql.append(" LEFT JOIN DT_HR_STAFF HS ON EC.STAFFID = HS.STAFFID");
        sql.append(" WHERE CB.FKSTATUS NOT IN (?, ?, ?, ?) AND CB.BILLSTYPE = ? AND CB.STATUSBILL = ? AND CB.COMPANYID = ? ");
        param.add("01");param.add("09");param.add("15");param.add("18");
        param.add("项目收入预算单");
        param.add("04");
        param.add(companyID);
        if(timeInterval!=null && !"".equals(timeInterval)){
            String [] time = timeInterval.split("至");
            sql.append(" AND OBA.FKDATE BETWEEN TO_DATE( ? ,'YYYY-MM-DD HH24:MI:SS') AND TO_DATE( ? ,'YYYY-MM-DD HH24:MI:SS') ");
            param.add(time[0]);param.add(time[1]);
        }
        if(accountOrName!=null && !"".equals(accountOrName)){
            sql.append(" AND (EC.ACCOUNT LIKE ? OR HS.STAFFNAME LIKE ?) ");
            param.add("%"+accountOrName+"%");
            param.add("%"+accountOrName+"%");
        }
        if(cashierBills.getJournalNum()!=null && !"".equals(cashierBills.getJournalNum())){
            sql.append("AND CB.JOURNALNUM = ? ");
            param.add(cashierBills.getJournalNum());
        }
        if(cashierBills.getInputName()!=null && !"".equals(cashierBills.getInputName())){
            sql.append(" AND CB.INPUTNAME LIKE ? ");
            param.add("%"+cashierBills.getInputName()+"%");
        }
        sql.append(" ORDER BY OBA.FKDATE DESC");
        String sqlsum = "SELECT COUNT(DISTINCT L.OA_SCCID),COUNT(L.JOURNALNUM),SUM(L.PRICESUB) FROM ("+sql.toString()+") L";
        pageForm  = baseBeanService.getPageFormBySQL(pageNumber==0?1:pageNumber,6,sql.toString(),
                "select count(*) from ("+sql.toString()+")",param.toArray());
        Object sum  = baseBeanService.getObjectBySqlAndParams(sqlsum,param.toArray());
        retMap.put(pageFormAadSum.pageForm,pageForm);
        retMap.put(pageFormAadSum.sum,sum);
        if("all".equals(type)){
            List<BaseBean> reportList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),param.toArray());
            retMap.put(pageFormAadSum.pageForm,reportList);
        }
        return retMap;
    }
}
