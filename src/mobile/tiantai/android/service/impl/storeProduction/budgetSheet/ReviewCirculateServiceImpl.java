package mobile.tiantai.android.service.impl.storeProduction.budgetSheet;

import cn.hutool.core.util.StrUtil;
import com.opensymphony.xwork2.ActionContext;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.CashierBillsExt;
import hy.ea.bo.human.Staff;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.bo.GoodsBillsExtOpinion;
import mobile.tiantai.android.service.storeProduction.budgetSheet.ReviewCirculateService;
import org.hibernate.criterion.*;
import org.hibernate.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * ReviewCirculateServiceImpl
 *
 * @author zch
 */
@Service
public class ReviewCirculateServiceImpl implements ReviewCirculateService {
    @Resource
    private BaseBeanService baseBeanService;

    @Override
    public Map<String, String> getNum(String companyID) {

        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        StringBuffer sql = new StringBuffer(128);
        sql.append("SELECT bill.billTYpe,typeName,NVL(bh,0)bh,NVL(ys,0)ys,NVL(ns,0)ns FROM ( ").append("SELECT  '出库单' as billTYpe,'Outbound' as typeName from Dual union all SELECT  '入库单','Warehousing' as typeName from Dual  ").append(" union all SELECT  '验货单' as billTYpe,'Warehousing1' as typeName from Dual ").append(" union all SELECT  '盘库单' as billTYpe,'Warehousing2' as typeName from Dual union all SELECT  '报损单' as billTYpe,'Warehousing3' as typeName from Dual  ").append(" union all SELECT  '采购单' as billTYpe,'Warehousing4' as typeName from Dual union all SELECT  '上架单' as billTYpe,'Warehousing5' as typeName from Dual ").append(" union all SELECT  '下架单' as billTYpe,'Warehousing6' as typeName from Dual union all SELECT  '订货单' as billTYpe,'Warehousing7' as typeName from Dual ").append(" union all SELECT  '发货单' as billTYpe,'Warehousing8' as typeName from Dual union all SELECT  '收款单' as billTYpe,'Warehousing9' as typeName from Dual ").append(" union all SELECT  '费用报销单' as billTYpe,'Warehousing10' as typeName from Dual").append(" union all SELECT  '收入' as billTYpe,'Warehousing11' as typeName from Dual union all SELECT  '支出' as billTYpe,'Warehousing12' as typeName from Dual").append(" ) bill LEFT JOIN (SELECT o.billsType,SUM(CASE WHEN o.STATUS='11' THEN 1 ELSE 0 end )bh,SUM(CASE WHEN o.STATUS='07' THEN 1 ELSE 0 end )ys,").append(" SUM(CASE WHEN o.STATUS!='07'  and o.STATUS!='11' THEN 1 ELSE 0 end )ns  from DTCashierBills o ,DTCashierBillsExt b").append("  WHERE  COMPANYID = ? AND o.cashierBillsID = b.CASHIERBILLSID AND b.RECEIVERID = ?").append(" GROUP BY o.billsType)data on bill.billTYpe=data.billsType ");

        List list = baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{companyID, account.getStaffID()});
        Map<String, String> map = new HashMap<>();
        Integer csIsWarehousing = 0, csRejectWarehousing = 0, ysNoWarehousing = 0;
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            Object[] obj = (Object[]) o;
            if ("WAREHOUSING11".equals(obj[1]) || "WAREHOUSING12".equals(obj[1])) {
                csIsWarehousing += Integer.parseInt(obj[3].toString());
                csRejectWarehousing += Integer.parseInt(obj[2].toString());
                ysNoWarehousing += Integer.parseInt(obj[4].toString());

            } else {
                String name = obj[1].toString().substring(0, 1) + obj[1].toString().substring(1).toLowerCase();
                map.put("is" + name, obj[3] + "");
                map.put("reject" + name, obj[2] + "");
                map.put("no" + name, obj[4] + "");
            }
        }
        map.put("isWarehousing11", csIsWarehousing + "");
        map.put("rejectWarehousing11", csRejectWarehousing + "");
        map.put("noWarehousing11", ysNoWarehousing + "");
        return map;
    }

    @Override
    public DetachedCriteria getBudgetBillDc(String staffID, String companyID, String status, String cashierBillsId) throws Exception {
        // 使用子查询方式实现两表关联
        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class, "o");
        dc.addOrder(Order.desc("o.cashierDate"));
//        if (billsType.equals("初始项目单")) {
//            dc.add(Restrictions.in("o.billsType", new Object[]{"收入", "支出"}));
//        } else {
//            dc.add(Restrictions.eq("o.billsType", billsType));
//        }
//        dc.add(Restrictions.eq("o.companyID", companyID));
        if (StringUtil.isNotEmpty(cashierBillsId)) {
            dc.add(Restrictions.eq("o.cashierBillsID", cashierBillsId));
        }
        // 子查询：检查是否存在关联的 CashierBillsExt 记录
        DetachedCriteria extSubquery = DetachedCriteria.forClass(CashierBillsExt.class, "b");
        extSubquery.add(Restrictions.eqProperty("b.cashierBillsID", "o.cashierBillsID"));
        extSubquery.add(Restrictions.eq("b.receiverID", staffID));
        extSubquery.add(Restrictions.eq("b.receiverCompanyID", companyID));
        extSubquery.setProjection(Projections.property("b.cashierBillsID"));
        dc.add(Subqueries.exists(extSubquery));
        if (status.equals("08")) {
            dc.add(Restrictions.not(Restrictions.in("o.status", new String[]{"07", "11"})));
        } else if (status.equals("100")) {
        } else {
            dc.add(Restrictions.eq("o.status", status));
        }
        searchSwitch(0, "", dc);
        return dc;
    }

    @Transactional
    @Override
    public String updateStatus(List<String> ids, String status) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        for (String id : ids) {
            CashierBills cashierBillsOld = (CashierBills) baseBeanService.getBeanByHqlAndParams(" FROM CashierBills WHERE cashierbillsid = ?", new Object[]{id});
            if (status.equals("驳回")) {
                cashierBillsOld.setStatus("11");
                status = "11";
            } else if (status.equals("审核")) {
                cashierBillsOld.setStatus("07");
                status = "07";
            }
            baseBeanService.update(cashierBillsOld);
            List<BaseBean> extList = baseBeanService.getListBeanByHqlAndParams("FROM CashierBillsExt WHERE cashierBillsID = ? AND receiverID = ? ORDER BY reviewTime DESC", new Object[]{id, account.getStaffID()});
            CashierBillsExt cashierBillsExtOld = (CashierBillsExt) extList.get(0);
            cashierBillsExtOld.setReviewerId(account.getStaffID());
            cashierBillsExtOld.setReviewerName(account.getStaffName());
            cashierBillsExtOld.setReviewTime(getTime());
            cashierBillsExtOld.setReviewStatus(status);
            baseBeanService.update(cashierBillsExtOld);
        }
        return "success";
    }

    @Override
    public List<CashierBillsExt> getCashierBillsExt(String cashierBillsId) {
        List<CashierBillsExt> extList = new ArrayList<>();
        List<BaseBean> listBeanByHqlAndParams = baseBeanService.getListBeanByHqlAndParams(" FROM CashierBillsExt WHERE cashierBillsID = ? ORDER BY REVIEWTIME DESC ", new Object[]{cashierBillsId});
        for (BaseBean listBeanByHqlAndParam : listBeanByHqlAndParams) {
            CashierBillsExt cashierBillsExt = (CashierBillsExt) listBeanByHqlAndParam;
            if (cashierBillsExt.getReviewerName() == null) {
                Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{cashierBillsExt.getReceiverID()});
                cashierBillsExt.setReviewerName("待审核人: " + staff.getStaffName());
            }
            extList.add(cashierBillsExt);
        }
        return extList;
    }

    @Transactional
    @Override
    public String updateOpinion(String cashierBillsId, String search, String reviewOpinion) {
        if (reviewOpinion.equals("同意")) {
            reviewOpinion = "1";
        } else {
            reviewOpinion = "0";
        }
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        GoodsBillsExtOpinion goodsBillsExtOpinionOld = (GoodsBillsExtOpinion) baseBeanService.getBeanByHqlAndParams(" FROM GoodsBillsExtOpinion WHERE goodsBillsID = ?", new Object[]{search});
        if (goodsBillsExtOpinionOld == null) {
            GoodsBillsExtOpinion goodsBillsExtOpinionNew = new GoodsBillsExtOpinion();
            goodsBillsExtOpinionNew.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            goodsBillsExtOpinionNew.setGoodsBillsID(search);
            goodsBillsExtOpinionNew.setCashierBillsID(cashierBillsId);
            List<BaseBean> extList = baseBeanService.getListBeanByHqlAndParams("FROM CashierBillsExt WHERE cashierBillsID = ? AND receiverID = ? ORDER BY reviewTime DESC", new Object[]{cashierBillsId, account.getStaffID()});
            CashierBillsExt cashierBillsExtOld = (CashierBillsExt) extList.get(0);
            if (StrUtil.isEmpty(cashierBillsExtOld.getReceiverCompanyID())) {
                goodsBillsExtOpinionNew.setReviewNameSource("往来个人");
            } else {
                // dtcompany   Company
                Company bean = (Company) baseBeanService.getBeanByHqlAndParams(" FROM Company WHERE companyID = ?", new Object[]{cashierBillsExtOld.getReceiverCompanyID()});
                goodsBillsExtOpinionNew.setReviewNameSource(bean.getCompanyName());
            }
            goodsBillsExtOpinionNew.setReviewerName(account.getStaffName());
            Staff beanByHqlAndParams = (Staff) baseBeanService.getBeanByHqlAndParams(" FROM Staff WHERE staffID = ?", new Object[]{account.getStaffID()});
            goodsBillsExtOpinionNew.setReviewerNameCode(beanByHqlAndParams.getStaffCode());
            goodsBillsExtOpinionNew.setReviewerId(account.getStaffID());
            goodsBillsExtOpinionNew.setReviewTime(getTime());
            goodsBillsExtOpinionNew.setReviewOpinion(reviewOpinion);
            baseBeanService.save(goodsBillsExtOpinionNew);
        } else {
//            CashierBillsExt cashierBillsExtOld = (CashierBillsExt) baseBeanService.getBeanByHqlAndParams("FROM CashierBillsExt WHERE cashierBillsID = ? and receiverID = ?  order by reviewTime desc", new Object[]{cashierBillsId, account.getStaffID()});
            List<BaseBean> extList = baseBeanService.getListBeanByHqlAndParams("FROM CashierBillsExt WHERE cashierBillsID = ? AND receiverID = ? ORDER BY reviewTime DESC", new Object[]{cashierBillsId, account.getStaffID()});
            CashierBillsExt cashierBillsExtOld = (CashierBillsExt) extList.get(0);
            if (StrUtil.isEmpty(cashierBillsExtOld.getReceiverCompanyID())) {
                goodsBillsExtOpinionOld.setReviewNameSource("往来个人");
            } else {
                // dtcompany   Company
                Company bean = (Company) baseBeanService.getBeanByHqlAndParams(" FROM Company WHERE companyID = ?", new Object[]{cashierBillsExtOld.getReceiverCompanyID()});
                goodsBillsExtOpinionOld.setReviewNameSource(bean.getCompanyName());
            }
            goodsBillsExtOpinionOld.setReviewerName(account.getStaffName());
            Staff beanByHqlAndParams = (Staff) baseBeanService.getBeanByHqlAndParams(" FROM Staff WHERE staffID = ?", new Object[]{account.getStaffID()});
            goodsBillsExtOpinionOld.setReviewerNameCode(beanByHqlAndParams.getStaffCode());
            goodsBillsExtOpinionOld.setReviewTime(getTime());
            goodsBillsExtOpinionOld.setReviewerId(account.getStaffID());
            goodsBillsExtOpinionOld.setReviewOpinion(reviewOpinion);
            baseBeanService.update(goodsBillsExtOpinionOld);
        }
//        CashierBillsExt cashierBillsExtOld = (CashierBillsExt) baseBeanService.getBeanByHqlAndParams(" FROM CashierBillsExt WHERE cashierBillsID = ? and receiverID = ? and reviewTime is null and reviewStatus is null ", new Object[]{id, account.getStaffID()});


        return "success";
    }

    @Override
    public String examineAndVerify(List<String> ids) {
        int i = 0;
        for (String id : ids) {
            CashierBills cashierBillsOld = (CashierBills) baseBeanService.getBeanByHqlAndParams(" FROM CashierBills WHERE cashierBillsID = ?", new Object[]{id});
            int conut1 = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from dtGoodsBills gb where gb.cashierBillsID = ? ", new Object[]{id});
            int conut2 = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from DTGOODSBILLSEXTOPINION gb where gb.CASHIERBILLSID = ? ", new Object[]{id});
            if (conut1 == conut2) {
                i++;
            }
        }
        if (i == ids.size()) {
            return "success";
        }

        return "no";
    }

    @Override
    public boolean examineAndVerify1(List<String> ids) {
        for (String id : ids) {
            int conut1 = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from dtGoodsBills gb where gb.cashierBillsID = ? ", new Object[]{id});
            int conut2 = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from DTGOODSBILLSEXTOPINION gb where gb.CASHIERBILLSID = ? ", new Object[]{id});

            List listBeanBySqlAndParams = baseBeanService.getListBeanBySqlAndParams("SELECT REVIEWOPINION from DTGOODSBILLSEXTOPINION gb where gb.CASHIERBILLSID = ? ", new Object[]{id});

            boolean allEqual = true;
            if (conut1 == conut2) {
                if (listBeanBySqlAndParams != null && !listBeanBySqlAndParams.isEmpty()) {
                    if (listBeanBySqlAndParams.size() > 1) { // 只有多个值才需要比较
                        Object firstValue = listBeanBySqlAndParams.get(0);
                        for (Object value : listBeanBySqlAndParams) {
                            if (!Objects.equals(firstValue, value)) {
                                allEqual = false;
                                break;
                            }
                        }
                    }
                    // 如果只有一个值，默认 allEqual 为 true
                } else {
                    allEqual = false; // 空列表认为不相等
                }
                if (allEqual) {
                    //修改CashierBillsExt数据
                    return true;
                }
            }
        }

        return false;
    }


    //CashierBills
    private int getList(String companyID, String billType, String receiverId, String status) {
        int count = 0;

        if (billType.equals("初始项目单")) {
            if (status.equals("07") || status.equals("11")) {
                count = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from DTCashierBills o ,DTCashierBillsExt b \n" + "WHERE o.billstype IN ('收入','支出') AND COMPANYID = ? AND o.cashierBillsID = b.CASHIERBILLSID AND o.STATUS = ? AND b.RECEIVERID = ?", new Object[]{companyID, status, receiverId});
            } else {
                count = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from DTCashierBills o ,DTCashierBillsExt b \n" + "WHERE o.billstype IN ('收入','支出') AND COMPANYID = ? AND o.cashierBillsID = b.CASHIERBILLSID AND o.STATUS NOT IN ('07', '11') AND b.RECEIVERID = ?", new Object[]{companyID, receiverId});
            }
        } else {

            if (status.equals("07") || status.equals("11")) {
                count = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from DTCashierBills o ,DTCashierBillsExt b \n" + "WHERE o.billstype = ? AND COMPANYID = ? AND o.cashierBillsID = b.CASHIERBILLSID AND o.STATUS = ? AND b.RECEIVERID = ?", new Object[]{billType, companyID, status, receiverId});
            } else {
                count = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from DTCashierBills o ,DTCashierBillsExt b \n" + "WHERE o.billstype = ? AND COMPANYID = ? AND o.cashierBillsID = b.CASHIERBILLSID AND o.STATUS NOT IN ('07', '11') AND b.RECEIVERID = ?", new Object[]{billType, companyID, receiverId});
            }
        }
        return count;
    }

    @Override
    public DetachedCriteria getBudgetBillDc1(String staffID, String companyID, String status) throws Exception {
        // 使用子查询方式实现两表关联
        DetachedCriteria dc = DetachedCriteria.forClass(CashierBills.class, "o");
        dc.addOrder(Order.desc("o.cashierDate"));
        dc.add(Restrictions.eq("o.companyID", companyID));
        // 子查询：检查是否存在关联的 CashierBillsExt 记录
        DetachedCriteria extSubquery = DetachedCriteria.forClass(CashierBillsExt.class, "b");
        extSubquery.add(Restrictions.eqProperty("b.cashierBillsID", "o.cashierBillsID"));
        extSubquery.add(Restrictions.eq("b.receiverID", staffID));
        extSubquery.add(Restrictions.eq("b.isSend", "1"));
        extSubquery.setProjection(Projections.property("b.cashierBillsID"));
        dc.add(Subqueries.exists(extSubquery));
        searchSwitch(0, "", dc);
        return dc;
    }

    @Override
    public String examineAndVerify2(List<String> ids) {
        int i = 0;
        for (String id : ids) {
            CashierBills cashierBillsOld = (CashierBills) baseBeanService.getBeanByHqlAndParams(" FROM CashierBills WHERE cashierBillsID = ?", new Object[]{id});
            int conut1 = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from dtGoodsBills gb where gb.cashierBillsID = ? ", new Object[]{id});
            int conut2 = baseBeanService.getConutByBySqlAndParams("SELECT COUNT(*) from DTGOODSBILLSEXTOPINION gb where gb.CASHIERBILLSID = ? ", new Object[]{id});
            if (conut1 == conut2) {
                i++;
            }
        }
        if (i == ids.size()) {
            return "success";
        }

        return "no";
    }

    private void searchSwitch(int searchType, String search, DetachedCriteria dc) throws Exception {
        //拼接模糊查询条件
        if (StringHelper.isNotEmpty(search)) {
            //模糊查询
            switch (searchType) {
                case 2://项目名称
                    dc.add(Restrictions.like("projectName", search, MatchMode.ANYWHERE));
                    break;
                case 3://凭证号
                    dc.add(Restrictions.like("journalNum", search, MatchMode.ANYWHERE));
                    break;
                case 4://责任人
                    dc.add(Restrictions.like("staffName", search, MatchMode.ANYWHERE));
                    break;
                case 11://制单人
                    dc.add(Restrictions.like("inputName", search, MatchMode.ANYWHERE));
                    break;
                default:
                    dc.add(Restrictions.or(Restrictions.or(Restrictions.like("projectName", search, MatchMode.ANYWHERE), Restrictions.like("journalNum", search, MatchMode.ANYWHERE)), Restrictions.or(Restrictions.like("staffName", search, MatchMode.ANYWHERE), Restrictions.like("inputName", search, MatchMode.ANYWHERE))));
            }
        }
    }

    private String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA));
    }

    @Transactional
    @Override
    public String updateStatus1(List<String> ids, String status) {
        Map<String, Object> session = ActionContext.getContext().getSession();
        CAccount account = (CAccount) session.get("account");
        for (String id : ids) {
            List<BaseBean> extList = baseBeanService.getListBeanByHqlAndParams("FROM CashierBillsExt WHERE cashierBillsID = ? AND receiverID = ? ORDER BY reviewTime DESC", new Object[]{id, account.getStaffID()});
            CashierBillsExt cashierBillsExtOld = (CashierBillsExt) extList.get(0);
            cashierBillsExtOld.setReviewerId(account.getStaffID());
            cashierBillsExtOld.setReviewerName(account.getStaffName());
            cashierBillsExtOld.setReviewTime(getTime());
            cashierBillsExtOld.setReviewStatus(status);
            baseBeanService.update(cashierBillsExtOld);
        }
        return "success";
    }


}
