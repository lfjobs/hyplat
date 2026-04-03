package mobile.tiantai.android.service.impl.storeProduction.budgetSheet;

import cn.hutool.core.collection.CollectionUtil;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.DictData;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.human.Staff;
import hy.ea.service.DictDataService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.bo.AfterSales;
import mobile.tiantai.android.service.storeProduction.budgetSheet.AfterSalesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * AfterSalesServiceImpl
 *
 * @author zch
 */
@Service
public class AfterSalesServiceImpl implements AfterSalesService {
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private DictDataService dictDataService;

    @Override
    public PageForm getCashierBillsList(String startTime, String endTime, Integer pageNumber, Integer pageSize) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String roleName = account.getRoleName();
        String[] roles = roleName.split(("\\s*,\\s*"));
        DictData data = dictDataService.getDictDataByType("orderAll", "");
        String[] dataValues = data.getDictValue().split(("\\s*,\\s*"));
        boolean hasCommonData = false;
        for (String role : roles) {
            if (Arrays.asList(dataValues).contains(role)) {
                hasCommonData = true;
                break;
            }
        }
        StringBuffer sql = new StringBuffer(128);
        PageForm pageForm = null;
        if (hasCommonData) {
            sql.append("select c.cashierBillsID,\n" + "       c.journalnum,\n" + "       c.projectname,\n" + "       c.pricesub    价格,\n" + "       c.inputname   操作员,\n" +
//                    "       c.cashierdate 时间,\n" +
                    "       --to_char(c.cashierdate, 'yyyy-mm-dd HH24:mi:ss') || ',',\n" + "       decode(c.fkstatus,'00','已付款', '02',  '已发货','03', '已收货', '04',  '已分配金币') 单据状态,\n" + "       decode(c.wfstatus4,'00', '微信支付', '01', '支付宝支付', '02', '银联支付', '03', '线下转账', '04', '钱盒子支付', '05', '积分支付', '06', '现金支付', '07', '金币支付') 支付方式\n" + "  from dtcashierbills c\n" + "  left join dt_order_bill_add o\n" + "    on c.cashierbillsid = o.oa_bill_id\n" + "  left join t_eshop_cuscom t\n" + "    on o.oa_sccid = t.sccid\n" + "  left join t_eshop_customer tt\n" + "    on t.staffid = tt.staffid\n" + "  left join dt_hr_staff s\n" + "    on t.staffid = s.staffid\n" + " where\n" + " c.statusbill = '04'\n" + " and c.billstype = '项目收入预算单'\n" + " and (c.fkstatus = '00' or c.fkstatus = '02' or c.fkstatus = '03' or\n" + " c.fkstatus = '04')\n" + " and c.status != '99' AND c.COMPANYID = ?\n" + " and c.cashierdate between\n" + " to_date(?||' 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and\n" + " to_date(?||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss')\n" + " order by c.cashierdate\n");
            String hql2 = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql2, new String[]{account.getCompanyID(), startTime, endTime});

        } else if (Arrays.asList(roles).contains("经理")) {
            sql.append("SELECT f.* FROM (select DISTINCT a.STAFFID staffId from dtcos a , (SELECT companyid,ORGANIZATIONID 　FROM DTCOS WHERE staffId= ? and companyId=? and cosstatus='50' )b WHERE a.companyid = b.companyid and a.ORGANIZATIONID= b.ORGANIZATIONID AND a.cosstatus='50')x INNER  JOIN(select c.cashierBillsID,s.staffid staffId,\n" + "       c.journalnum,\n" + "       c.projectname,\n" + "       c.pricesub    价格,\n" + "       c.inputname   操作员,\n" +
//                    "       c.cashierdate 时间,\n" +
                    "       --to_char(c.cashierdate, 'yyyy-mm-dd HH24:mi:ss') || ',',\n" + "       decode(c.fkstatus,'00','已付款', '02',  '已发货','03', '已收货', '04',  '已分配金币') 单据状态,\n" + "       decode(c.wfstatus4,'00', '微信支付', '01', '支付宝支付', '02', '银联支付', '03', '线下转账', '04', '钱盒子支付', '05', '积分支付', '06', '现金支付', '07', '金币支付') 支付方式\n" + "  from dtcashierbills c\n" + "  left join dt_order_bill_add o\n" + "    on c.cashierbillsid = o.oa_bill_id\n" + "  left join t_eshop_cuscom t\n" + "    on o.oa_sccid = t.sccid\n" + "  left join t_eshop_customer tt\n" + "    on t.staffid = tt.staffid\n" + "  left join dt_hr_staff s\n" + "    on t.staffid = s.staffid\n" + " where\n" + " c.statusbill = '04'\n" + " and c.billstype = '项目收入预算单'\n" + " and (c.fkstatus = '00' or c.fkstatus = '02' or c.fkstatus = '03' or\n" + " c.fkstatus = '04')\n" + " and c.status != '99' AND c.COMPANYID = ?\n" + " and c.cashierdate between\n" + "  to_date(?||' 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and\n" + " to_date(?||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss')\n" + " order by c.cashierdate) f\n" + " on x.staffId = f.staffId");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql, new String[]{account.getStaffID(), account.getCompanyID(), account.getCompanyID(), startTime, endTime});

        } else {
            sql.append("select c.cashierBillsID,\n" + "       c.journalnum,\n" + "       c.projectname,\n" + "       c.pricesub    价格,\n" + "       c.inputname   操作员,\n" +
//                    "       c.cashierdate 时间,\n" +
                    "       --to_char(c.cashierdate, 'yyyy-mm-dd HH24:mi:ss') || ',',\n" + "       decode(c.fkstatus,'00','已付款', '02',  '已发货','03', '已收货', '04',  '已分配金币') 单据状态,\n" + "       decode(c.wfstatus4,'00', '微信支付', '01', '支付宝支付', '02', '银联支付', '03', '线下转账', '04', '钱盒子支付', '05', '积分支付', '06', '现金支付', '07', '金币支付') 支付方式\n" + "  from dtcashierbills c\n" + "  left join dt_order_bill_add o\n" + "    on c.cashierbillsid = o.oa_bill_id\n" + "  left join t_eshop_cuscom t\n" + "    on o.oa_sccid = t.sccid\n" + "  left join t_eshop_customer tt\n" + "    on t.staffid = tt.staffid\n" + "  left join dt_hr_staff s\n" + "    on t.staffid = s.staffid\n" + " where\n" + " c.statusbill = '04'\n" + " and c.billstype = '项目收入预算单'\n" + " and (c.fkstatus = '00' or c.fkstatus = '02' or c.fkstatus = '03' or\n" + " c.fkstatus = '04')\n" + " and c.status != '99' AND c.COMPANYID = ? AND s.STAFFID = ?\n" + " and c.cashierdate between\n" + " to_date(?||' 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and\n" + " to_date(?||' 23:59:59', 'yyyy-mm-dd hh24:mi:ss')\n" + " order by c.cashierdate");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql, new String[]{account.getCompanyID(), account.getStaffID(), startTime, endTime});

        }
        return pageForm;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updCustomerData(AfterSales salesOld) {
        AfterSales salesOld1 = (AfterSales) baseBeanService.getBeanByHqlAndParams(" FROM AfterSales WHERE cashierbillsId = ?", new Object[]{salesOld.getCashierbillsId()});
        if (salesOld1 == null) {
            salesOld.setIsProcess("1");
            salesOld.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            salesOld.setProcessResult(salesOld.getProcessResult());
            salesOld.setAddress(salesOld.getAddress());
            salesOld.setProcessPerson(salesOld.getProcessPerson());
            salesOld.setProcessPersonPhone(salesOld.getProcessPersonPhone());
            baseBeanService.save(salesOld);
        } else {
            salesOld1.setMnemonicCode(salesOld.getMnemonicCode());
            salesOld1.setCustomerOpinion(salesOld.getCustomerOpinion());
            if (salesOld.getIsProcess().equals("1")) {
                salesOld1.setIsProcess("1");
            } else {
                salesOld1.setIsProcess("0");
            }
            salesOld.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            salesOld1.setProcessResult(salesOld.getProcessResult());
            salesOld1.setAddress(salesOld.getAddress());
            salesOld1.setProcessPerson(salesOld.getProcessPerson());
            salesOld1.setProcessPersonPhone(salesOld.getProcessPersonPhone());
            baseBeanService.update(salesOld1);
        }
        return "success";
    }

    @Override
    public PageForm getCustomerFeedbackList(Integer pageNumber, Integer pageSize) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        StringBuffer sql = new StringBuffer(128);
        sql.append("select GOODSBILLS_ID, GOODS_ID,GOODS_NAME_BILL, MNEMONIC_CODE,VALIDITY_PERIOD, CHARGING_TIME FROM sh_after_sales WHERE customer_staff_id = ? order by charging_time desc");
        String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
        PageForm pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql, new String[]{account.getStaffID()});
        return pageForm;
    }

    @Override
    public PageForm getCashierBillsList1(String startTime, String endTime, Integer pageNumber, Integer pageSize) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        String roleName = account.getRoleName();
        String[] roles = roleName.split(("\\s*,\\s*"));
        DictData data = dictDataService.getDictDataByType("orderAll", "");
        String[] dataValues = data.getDictValue().split(("\\s*,\\s*"));
        boolean hasCommonData = false;
        for (String role : roles) {
            if (Arrays.asList(dataValues).contains(role)) {
                hasCommonData = true;
                break;
            }
        }
        PageForm pageForm = null;
        StringBuffer sql = new StringBuffer(128);
        if (hasCommonData) {
            sql.append("select cashierbills_id, GOODS_ID,GOODS_NAME_BILL, MNEMONIC_CODE,VALIDITY_PERIOD, CHARGING_TIME ,customer_opinion,is_process FROM sh_after_sales WHERE  ccompany_id= ? and charging_time between ?||' 00:00:00' and ?||' 23:59:59' order by charging_time desc");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql, new String[]{account.getCompanyID(), startTime, endTime});
        } else if (Arrays.asList(roles).contains("经理")) {
            sql.append("SELECT b.* FROM(select DISTINCT a.STAFFID staffId from dtcos a , (SELECT companyid,ORGANIZATIONID 　FROM DTCOS WHERE staffId=? and companyId=? and cosstatus='50' )b WHERE a.companyid = b.companyid and a.ORGANIZATIONID= b.ORGANIZATIONID AND a.cosstatus='50') x INNER JOIN (select cashierbills_id, GOODS_ID,GOODS_NAME_BILL, MNEMONIC_CODE,VALIDITY_PERIOD, CHARGING_TIME , customer_opinion,is_process,staff_id staffId FROM sh_after_sales WHERE staff_id = ? and ccompany_id= ? and charging_time between ?||' 00:00:00' and ?||' 23:59:59' order by charging_time desc)b ON x.staffId = b.staffId");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql, new String[]{account.getStaffID(), account.getCompanyID(), account.getStaffID(), account.getCompanyID(), startTime, endTime});
        } else {
            sql.append("select cashierbills_id, GOODS_ID,GOODS_NAME_BILL, MNEMONIC_CODE,VALIDITY_PERIOD, CHARGING_TIME,customer_opinion,is_process FROM sh_after_sales WHERE staff_id = ? and ccompany_id= ? and charging_time between ?||' 00:00:00' and ?||' 23:59:59' order by charging_time desc");
            String hql = " SELECT COUNT(*) FROM ( " + sql + " )";
            pageForm = baseBeanService.getPageFormBySQL((null != pageNumber ? pageNumber : 1), (pageSize == 0 ? 20 : pageSize), sql.toString(), hql, new String[]{account.getStaffID(), account.getCompanyID(), startTime, endTime});
        }
        return pageForm;
    }

    @Override
    public AfterSales updAfterSales(String goodsbillsId) {
        StringBuffer sql = new StringBuffer(128);
        sql.append("select c.companyname 公司,c.COMPANYID 公司id,\n" + "c.CRESPONSIBLE 负责人,\n" + "c.responsibleTel 负责人电话,\n" + "c.STAFFID 客服id,\n" + "c.STAFFNAME 客服名字,\n" + "c.REFERENCE 客服电话,\n" + "c.CONTACTUSERID 客户id,\n" + "c.ADDRESS 地址,\n" + "       s.staffname 购买人名称,\n" + "       t.account 购买人帐号,\n" + "       c.staffidentitycard 身份证号,\n" + "       c.projectname 产品,\n" + "       c.pricesub    价格,\n" + "       c.inputname   操作员,\n" + "\t\t\t c.goodsCoding 设备编号,\n" + "\t\t\t c.GOODSNAME 设备名称,\n" + "\t\t\t c.MNEMONICCODE 设备品牌,\n" + "\t\t\t  c.cashierdate 时间,\n" + "    c.journalnum\n" + "  from dtcashierbills c\n" + "  left join dt_order_bill_add o\n" + "    on c.cashierbillsid = o.oa_bill_id\n" + "  left join t_eshop_cuscom t\n" + "    on o.oa_sccid = t.sccid\n" + "  left join t_eshop_customer tt\n" + "    on t.staffid = tt.staffid\n" + "  left join dt_hr_staff s\n" + "    on t.staffid = s.staffid\n" + " where\n" + " c.statusbill = '04'\n" + " and c.billstype = '项目收入预算单'\n" + " and (c.fkstatus = '00' or c.fkstatus = '02' or c.fkstatus = '03' or\n" + " c.fkstatus = '04')\n" + " and c.status != '99' AND c.cashierBillsID = ?\n" + " order by c.cashierdate");
        List<Object> result = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{goodsbillsId});
        AfterSales afterSales = new AfterSales();
        if (CollectionUtil.isNotEmpty(result)) {
            for (Object obj : result) {
                Object[] o = (Object[]) obj;
                //订单编号
                afterSales.setCashierbillsId(goodsbillsId);
                //商品订单编号
                afterSales.setGoodsId(o[19] != null ? o[19].toString() : "");
                afterSales.setAccount(o[10] != null ? o[10].toString() : "");
                afterSales.setAccountName(o[9] != null ? o[9].toString() : "");
                //商品名称
                afterSales.setGoodsNameBill(o[12] != null ? o[12].toString() : "");
                //设备编号
                afterSales.setGoodsCoding(o[1] != null ? o[1].toString() : "");
                //设备名称
                afterSales.setGoodsName(o[11] != null ? o[11].toString() : "");
//                afterSales.setGoodsName(o[2] + "");
                //设备品牌
                afterSales.setMnemonicCode(o[2] != null ? o[2].toString() : "");
                //收费时间
                afterSales.setChargingTime(o[18] != null ? o[18].toString() : "");
                //公司ID
                afterSales.setCcompanyID(o[1] != null ? o[1].toString() : "");
                //公司名称
                afterSales.setCcompanyName(o[0] != null ? o[0].toString() : "");
                //负责人
                afterSales.setCresponsible(o[2] != null ? o[2].toString() : "");
                //负责人电话
                afterSales.setResponsibleTel(o[3] != null ? o[3].toString() : "");
                //客服ID
                afterSales.setStaffID(o[4] != null ? o[4].toString() : "");
                //客服名称
                afterSales.setStaffName(o[5] != null ? o[5].toString() : "");
                //客服电话
                afterSales.setStaffPhone(o[6] != null ? o[6].toString() : "");
                //客户ID
                afterSales.setCustomerStaffId(o[7] != null ? o[7].toString() : "");
                afterSales.setAddress(o[8] != null ? o[8].toString() : "");
            }
        }
        afterSales.setId(UUID.randomUUID().toString().replaceAll("-", ""));
        baseBeanService.save(afterSales);
        return afterSales;
    }


    private String getTime(Date date) {
        if (date != null) {
            // 转换为 LocalDateTime（使用系统默认时区）
            LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
        }
        return null;

    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAfterSales() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        List<BaseBean> returnBeans = new ArrayList<BaseBean>();
        // 根据   tc.getStaffid();   查询

        //单据表CashierBills   //单据明细表 GoodsBills
        List<GoodsBills> goodsBillsList = new ArrayList<GoodsBills>();
        List<CashierBills> cashierBillsList = new ArrayList<CashierBills>();
        List<BaseBean> dtCashierBillsList = baseBeanService.getListBeanByHqlAndParams(" FROM CashierBills  WHERE companyID = ? order by cashierDate desc", new Object[]{account.getCompanyID()});
        if (CollectionUtil.isNotEmpty(dtCashierBillsList)) {
            for (Object obj : dtCashierBillsList) {
                CashierBills cashierBills = (CashierBills) obj;
                cashierBillsList.add(cashierBills);
            }
            if (CollectionUtil.isNotEmpty(cashierBillsList)) {
                for (CashierBills cashierBills : cashierBillsList) {
                    List<BaseBean> dtGoodsBillsList = baseBeanService.getListBeanByHqlAndParams(" FROM GoodsBills  WHERE cashierBillsID = ?", new Object[]{cashierBills.getCashierBillsID()});
                    if (CollectionUtil.isNotEmpty(dtGoodsBillsList)) {
                        for (Object obj : dtGoodsBillsList) {
                            Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{cashierBills.getStaffID()});
                            GoodsBills goodsBills = (GoodsBills) obj;
                            AfterSales afterSales = new AfterSales();
                            // 单据ID
                            afterSales.setCashierbillsId(cashierBills.getCashierBillsID());
                            //单据明细id
                            afterSales.setGoodsbillsId(goodsBills.getGoodsBillsID());
                            //商品名称
                            afterSales.setGoodsNameBill(goodsBills.getGoodsName());
                            //商品ID
                            afterSales.setGoodsId(goodsBills.getGoodsID());
                            //购买数量
                            afterSales.setQuantity(goodsBills.getQuantity());
                            //设备编号
                            afterSales.setGoodsCoding(cashierBills.getGoodsCoding());
                            //设备名称
                            afterSales.setGoodsName(cashierBills.getGoodsName());
                            //设备品牌
                            afterSales.setMnemonicCode(cashierBills.getMnemonicCode());
                            //有效日期
                            afterSales.setValidityPeriod(getTime(goodsBills.getPeriodDate()));
                            //收费时间
                            afterSales.setChargingTime(getTime(cashierBills.getCashierDate()));
                            //公司ID
                            afterSales.setCcompanyID(cashierBills.getCcompanyID());
                            //公司名称
                            afterSales.setCcompanyName(cashierBills.getCcompanyName());
                            //负责人
                            afterSales.setCresponsible(cashierBills.getCresponsible());
                            //负责人电话
                            afterSales.setResponsibleTel(cashierBills.getResponsibleTel());
                            //客服ID
                            afterSales.setStaffID(cashierBills.getStaffID());
                            //客服名称
                            afterSales.setStaffName(cashierBills.getStaffName());
                            //客服电话
                            afterSales.setStaffPhone(cashierBills.getReference());
                            //客户ID
                            afterSales.setCustomerStaffId(cashierBills.getContactUserID());
                            //客户意见
                            //  afterSales.setCustomerOpinion();
                            // 是否处理
                            //afterSales.setIsProcess();
                            // 地址
                            afterSales.setAddress(cashierBills.getAddress());
                            // 处理人
                            // afterSales.setProcessPerson(goodsBills.getProcessPerson());
                            // 处理人电话
                            // afterSales.setProcessPersonPhone(goodsBills.getProcessPersonPhone());
                            AfterSales afterSalesOld = (AfterSales) baseBeanService.getBeanByHqlAndParams("from AfterSales where goodsbillsId = ?", new Object[]{goodsBills.getGoodsBillsID()});
                            if (afterSalesOld == null) {
                                afterSales.setId(UUID.randomUUID().toString().replaceAll("-", ""));
//                                baseBeanService.save(afterSales);
                            }
                            returnBeans.add(afterSales);
                            goodsBillsList.add(goodsBills);
                        }
                    }
                }
            }
        }
    }

}
