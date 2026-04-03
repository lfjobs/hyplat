package hy.ea.finance.service.brokerage.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.BenDis.ProSetupSubBackup;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProSetupBackup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.finance.brokerage.PWhoBroHistory;
import hy.ea.bo.finance.brokerage.PWhoBrokerage;
import hy.ea.bo.finance.brokerage.PWhoHistory;
import hy.ea.bo.finance.brokerage.PWholesale;
import hy.ea.finance.service.brokerage.WholesalePriceMobileService;
import hy.ea.finance.service.brokerage.WholesaleService;
import hy.ea.util.elkc.DateUtilElkc;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class WholesalePriceMobileServiceImpl implements WholesalePriceMobileService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;
    @Resource
    private WholesaleService wholesaleService;

    /**
     * 批发产品[佣金...]查询
     *
     * @param map
     * @return 2018-11-26
     */
    @Override
    public PageForm selectWholesalePriceList(Map<String, Object> map) {
        Integer pageNumber = (Integer) map.get("pageNumber");//当前页
        Integer pageSize = (Integer) map.get("pageSize");//每页显示最大数
        String search = (String) map.get("search");//商品模糊查询参数
        String companyId = (String) map.get("companyId");//公司id
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT(PP.PPID),PP.BARCODE,PP.GOODSNAME,PW.FACTORY AS CB,");
        sql.append(" PW.BROKERAGE AS YW,PW.WHOLESALE AS XTJ,PW.BROKERAGES AS DL,");
        sql.append(" NVL(PW.WHOLESALE*SU.TOTAL_PCT*0.01,0) AS HB,NVL(PW.WHOLESALE*(1+SU.TOTAL_PCT*0.01),PW.WHOLESALE) AS PRICE,");
        sql.append(" PP.SHOWWEIXIN ,PP.WHOLESALESTATUS,PW.WHOLESALEID,PW.ADDTIMES,PW.STATE FROM DT_PRO_WHOLESALE PW");
        sql.append(" LEFT JOIN DT_PRODUCTPACKAGING PP ON PP.PPID=PW.PPID");
        sql.append(" LEFT JOIN DTCOMPANY CP ON CP.COMPANYID=PP.COMPANYID");
        sql.append(" LEFT JOIN DTGOODSMANAGE GM ON GM.GOODSID=PP.GOODSID");
        sql.append(" LEFT JOIN DT_CCOM_COM CCO ON CCO.COMPNAY_ID = CP.COMPANYID");
        sql.append(" LEFT JOIN DTCONTACTCOMPANY CC ON CC.CCOMPANYID = CCO.CCOMPANY_ID");
        sql.append(" LEFT JOIN DT_SET_SUBSIDIZE SU ON SU.GTID = CC.INDUSTRYTYPE AND SU.STUTAS = '01'");
        sql.append(" WHERE PP.WHOLESALESTATUS='01' AND CP.COMPANYID =? ");
        List<Object> parms = new ArrayList<Object>();
        parms.add(companyId);
        if (search != null && !search.equals("")) {
            sql.append(" AND (PP.GOODSNAME LIKE ?");
            sql.append(" OR PP.BARCODE LIKE ?)");
            parms.add("%" + search.trim() + "%");
            parms.add("%" + search.trim() + "%");
        }
        sql.append(" ORDER BY PW.ADDTIMES DESC");
        PageForm pageForm = this.baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());
        return pageForm;
    }

    public Map<String, Object> getRetailPriceList(String psid) {
        Map<String, Object> result = new HashMap<>();

        // SQL语句作为常量定义
        final String SQL_MAIN_QUERY = "SELECT DW.WHOLESALEID,DW.WHOLESALE,DW.FACTORY," +
                "DW.BROKERAGE,DW.BROKERAGES,DW.INVESTTYPE , P.GOODSNAME, " +
                "P.BARCODE, S.STAFFNAME, C.COMPANYNAME, DW.WHOLESALEKEY, DW.STATE,DW.PPID " +
                "FROM DT_PRO_WHOLESALE DW " +
                "LEFT JOIN DT_PRODUCTPACKAGING P ON DW.PPID = P.PPID " +
                "LEFT JOIN DT_HR_STAFF S ON DW.PRINCIPAL = S.STAFFID " +
                "LEFT JOIN DTCOMPANY C ON DW.COMPANYID = C.COMPANYID " +
                "WHERE DW.WHOLESALEID = ?";

        final String SQL_SUB_QUERY = "SELECT PSS.WHOBROID,PSS.BROKERAGE,PSS.TYPEPPID, P.GOODSNAME " +
                "FROM DT_PRO_WHO_BROKERAGE PSS " +
                "LEFT JOIN DT_PRODUCTPACKAGING P ON PSS.TYPEPPID = P.PPID " +
                "WHERE PSS.WHOLESALEID = ? " +
                "ORDER BY PSS.WHOBROID ASC";

        try {
            Object[] mainResult = (Object[]) this.baseBeanService.getObjectBySqlAndParams(SQL_MAIN_QUERY, new Object[]{psid});
            List<BaseBean> subResult = this.baseBeanService.getListBeanBySqlAndParams(SQL_SUB_QUERY, new Object[]{psid});

            result.put("pRetail", mainResult);
            result.put("beanList", subResult);
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
            // 可以添加日志记录或其他错误处理机制
        }
        return result;
    }

    /**
     * 查询已设置佣金的产品
     * 2019-05-27
     *
     * @return
     */
    public String getPRetailByPpid(List<BaseBean> saveBean, PWholesale pWholesales) throws Exception {
        //修改佣金并将原数据存入历史表
        PWhoHistory pWhoHistory = new PWhoHistory();
        pWhoHistory.setWholesaleId(pWholesales.getWholesaleId());
        pWhoHistory.setWholesaleKey(pWholesales.getWholesaleKey());
        pWhoHistory.setWholesale(pWholesales.getWholesale());
        pWhoHistory.setFactory(pWholesales.getFactory());
        pWhoHistory.setBrokerage(pWholesales.getBrokerage());
        pWhoHistory.setBrokerages(pWholesales.getBrokerages());
        pWhoHistory.setInvestType(pWholesales.getInvestType());
        pWhoHistory.setAddTimes(pWholesales.getAddTimes());
        pWhoHistory.setUpdateTimes(pWholesales.getUpdateTimes());
        pWhoHistory.setPrincipal(pWholesales.getPrincipal());
        pWhoHistory.setPpid(pWholesales.getPpid());
        pWhoHistory.setCompanyId(pWholesales.getCompanyId());
        pWhoHistory.setState(pWholesales.getState());
        saveBean.add(pWhoHistory);
        //零售价代理商佣金
        List<BaseBean> brokerageList = wholesaleService.brokerageListById(pWholesales.getWholesaleId());
        if (brokerageList != null && brokerageList.size() > 0) {
            Object objects = null;
            for (int i = 0; i < brokerageList.size(); i++) {
                objects = brokerageList.get(i);
                Object[] objs = (Object[]) objects;
                if (objs != null) {
                    //批发价佣金修改
                    PWhoBroHistory pWhoBroHistory = new PWhoBroHistory();
                    pWhoBroHistory.setWhobroId(objs[0].toString());
                    pWhoBroHistory.setBrokerage(Double.valueOf(objs[1].toString()));
                    pWhoBroHistory.setPpid(pWhoHistory.getPpid());
                    pWhoBroHistory.setTypePpid(objs[2].toString());
                    pWhoBroHistory.setCompanyId(pWhoHistory.getCompanyId());
                    pWhoBroHistory.setWholesaleId(pWhoHistory.getWholesaleId());
                    saveBean.add(pWhoBroHistory);
                }
            }
            return "201";
        } else {
            return "401";//数据异常
        }
    }

    @Override
    @Transactional
    public Map<String, String> savePrice(String state, CAccount cac, Map mapPro, Map mapdl) {
        List<BaseBean> saveBean = new ArrayList<BaseBean>();
        Map<String, String> result = new HashMap<>();
        String flag = "200";
        String wholesaleId = "";
        try {
            PWholesale pWholesales = this.wholesaleService.getPWholesaleById(mapPro.get("priceid").toString());
            //批发价添加
            PWholesale wholesale = new PWholesale();
            wholesaleId = serverService.getServerID("wholesaleId");
            System.out.println("wholesaleId:" + wholesaleId);
            result.put("priceid", wholesaleId);
            wholesale.setWholesaleId(wholesaleId);
            wholesale.setCompanyId(cac.getCompanyID());
            Date dateTime = DateUtilElkc.getCurrentDateTime();
            wholesale.setAddTimes(dateTime);
            wholesale.setPrincipal(cac.getStaffName());
            wholesale.setFactory(Double.valueOf(mapPro.get("cb").toString()));
            wholesale.setWholesale(Double.valueOf(mapPro.get("dj").toString()));
            wholesale.setBrokerage(Double.valueOf(mapPro.get("yw").toString()));
            wholesale.setInvestType(mapPro.get("tzt").toString());
            wholesale.setPpid(mapPro.get("ppid").toString());
            wholesale.setState(state);//默认给:00
            List<BaseBean> list = wholesaleService.agencyTypeList();
            if (list != null && list.size() > 0) {
                Double f = 0d;
                for (int i = 0; i < list.size(); i++) {
                    ProductPackaging p = (ProductPackaging) list.get(i);
                    //批发价佣金添加
                    PWhoBrokerage pWhoBrokerage = new PWhoBrokerage();
                    pWhoBrokerage.setPpid(wholesale.getPpid());
                    pWhoBrokerage.setCompanyId(cac.getCompanyID());
                    pWhoBrokerage.setWholesaleId(wholesaleId);
                    String whobroId = serverService.getServerID("whobroId");
                    pWhoBrokerage.setWhobroId(whobroId);
                    pWhoBrokerage.setBrokerage(Double.valueOf(mapdl.get(p.getPpID()).toString()));
                    f = pWhoBrokerage.getBrokerage() + f;
                    pWhoBrokerage.setTypePpid(p.getPpID());
                    saveBean.add(pWhoBrokerage);
                }
                wholesale.setBrokerages(f);
            }

            List<String> sqlList = new ArrayList<>();
            List<Object[]> parm = new ArrayList<>();

            if (pWholesales != null) {
                wholesale.setUpdateTimes(new Date());
                System.out.println(pWholesales.getWholesaleId());
                flag = getPRetailByPpid(saveBean, pWholesales);
                if (!flag.equals("201")) {
                    throw new Exception("数据异常");
                }
                wholesale.setUpdateTimes(new Date());
                wholesale.setAddTimes(pWholesales.getAddTimes());
                String setup = "delete dt_pro_wholesale where wholesaleId=?";
                String sub = "delete dt_pro_who_brokerage where wholesaleId=?";
                sqlList.add(setup);
                sqlList.add(sub);
                parm.add(new Object[]{pWholesales.getWholesaleId()});
                parm.add(new Object[]{pWholesales.getWholesaleId()});
            }
            saveBean.add(wholesale);
            sqlList.add("update dt_ProductPackaging set wholesaleStatus ='01' where ppid = ? ");
            parm.add(new Object[]{mapPro.get("ppid")});
            String[] array = new String[sqlList.size()];
            for (int i = 0; i < sqlList.size(); i++) {
                array[i] = sqlList.get(i);
            }
            baseBeanService.executeSqlsByParmsList(saveBean, array, parm);
        } catch (Exception e) {
            e.printStackTrace();
            flag = "500";
        }
        result.put("flag", flag);
        return result;
    }

    @Override
    public void updateState(String priceid) {
        String sql = "UPDATE DT_PRO_WHOLESALE SET STATE ='03' WHERE WHOLESALEID = ? ";
        this.baseBeanService.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{priceid});
    }
}
