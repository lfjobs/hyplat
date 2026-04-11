package hy.ea.test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.tiantai.wfj.bo.GiftCards;
import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.SelfCart;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.front.WfjEshopProductAction;
import com.tiantai.wfj.service.GoldOrderService;
import com.tiantai.wfj.service.impl.GoldOrderServiceImpl;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.Enroll;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.BenDis.*;
import hy.ea.finance.service.transferService;
import hy.ea.marketing.bo.ProProxy;
import hy.ea.office.service.CarManageService;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.MakeAppointmentService;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alipay.config.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019-07-31.
 */
@Controller
@Scope("prototype")
public class testAction {
    private static Logger logger = LoggerFactory.getLogger(testAction.class);
    @Resource
    private static BaseBeanService baseBeanService;
    @Resource
    private static GoldOrderService goldOrderServiceImpl;
    @Resource
    private static CarManageService cmService;
    @Resource
    private static MakeAppointmentService makeAppointmentService;
    @Resource
    private static CarManageService carManageService;
    @Resource
    private static ContractService contractService;
    @Resource
    private static transferService transService;
    private static List<BaseBean> mbList;
    private static List<BaseBean> dlList;
    private static List<BaseBean> ppList;

    public static void main(String[] args) {
        //testAction.way1("4327500963202311211790009644", "2023112111185000005", "1700", "00", "01");
        way4();
    }

    private static void way4() {
        try {
            WebBookHelper.玄巫秦耳();
        } catch (IOException e) {
            logger.error("操作异常", e);
        } catch (InterruptedException e) {
            logger.error("操作异常", e);
        }
    }

    //http://localhost:8080/st/test/ea_way8.jspa?
    public static Boolean way1(String tradeNo, String ddid, String morrt, String wfStatus4, String wfStatus1) {
        Boolean b = true;
        String type = "";
        try {
            List<BaseBean> backList = new ArrayList<BaseBean>();
            List<BaseBean> pcbs = new ArrayList<BaseBean>();

            String payhql = "from PayCashierBill where payJournalNum=? and dtype is null";
            pcbs = baseBeanService.getListBeanByHqlAndParams(payhql, new String[]{ddid});

            if (pcbs.size() == 0) {
                payhql = "from PayCashierBill where oriJournalNum=?";
                pcbs = baseBeanService.getListBeanByHqlAndParams(payhql, new String[]{ddid});
            }
            //logger.error("输出错误判断是否有值" + pcbs);
            String hqlh = " from CashierBills where journalNum = ?";
            CashierBills cashierBills = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqlh, new Object[]{((PayCashierBill) pcbs.get(0)).getOriJournalNum()});
            boolean bo = true;
            BigDecimal totalprice = BigDecimal.ZERO; //累计所有订单总金额
            BigDecimal yb = new BigDecimal(100);
            TEshopCusCom thcuscom = new TEshopCusCom();
            if (cashierBills.getFkStatus().equals("01") || cashierBills.getFkStatus().equals("09")) {
                DtOrderBillAdd bd = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum=?", new Object[]{cashierBills.getJournalNum()});
                //查询当前订单用户
                String hql2 = "from TEshopCusCom d where d.sccId=?";
                TEshopCusCom tsc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql2, new String[]{bd.getOaSccId()});
                bd.setFkDate(new Date());
                backList.add(bd);
                if (tsc.getCusType().equals("7") || tsc.getCusType().equals("6")) {
                    List<String> param = new ArrayList<String>();
                    String sql1 = "select sum(priceSub) from dtCashierBills where journalNum in(";
                    for (int d = 0; d < pcbs.size(); d++) {
                        PayCashierBill payCashierBill = (PayCashierBill) pcbs.get(d);
                        if (d == pcbs.size() - 1) {
                            sql1 += "?)";
                        } else {
                            sql1 += "?,";
                        }
                        param.add(payCashierBill.getOriJournalNum());
                    }
                    Object obj = baseBeanService.getObjectBySqlAndParams(sql1, param.toArray());
                    totalprice = new BigDecimal(obj.toString()); //累计所有订单总金额
                }
                String hqlcom = "from Company where companyID = ?";
                for (int a = 0; a < pcbs.size(); a++) {
                    PayCashierBill payCashierBill = (PayCashierBill) pcbs.get(a);
                    payCashierBill.setPayJournalNum(ddid);
                    if (payCashierBill.getTrade_no() == null || payCashierBill.getTrade_no().equals("")) {
                        payCashierBill.setTrade_no(tradeNo);
                    }
                    //订单
                    CashierBills dd = new CashierBills();
                    //收款单
                    CashierBills sk = new CashierBills();

                    //订单里的产品的业务佣金和
                    BigDecimal bksum = BigDecimal.ZERO;

                    ProductPackaging pps = null;
                    BigDecimal ss = BigDecimal.ZERO; //订单产品总价格
                    String mes = "";
                    StringBuffer aaa = null;

                    String hql = "from CashierBills d where d.journalNum=?";
                    dd = (CashierBills) baseBeanService.getBeanByHqlAndParams(hql,
                            new String[]{payCashierBill.getOriJournalNum()});

                    if (dd == null) {
                        logger.info("调试信息");
                        continue;
                    } else if (!dd.getFkStatus().equals("01") && !dd.getFkStatus().equals("09")) {
                        logger.info("调试信息");
                        return null;
                    }

                    sk = (CashierBills) dd.cloneCashierBills();

                    BeanUtils.copyProperties(dd, sk);
                    //sk.setCashierBillsID(serverService.getServerID("CashierBills"));
                    sk.setCashierBillsKey("");
                    sk.setFkStatus("00");
                    dd.setFkStatus("00");
                    sk.setPriceSub(dd.getPriceSub());
                    sk.setStatus("45");
                    sk.setCompanyID("company201009046vxdyzy4wg0000000025");
                    sk.setCompanyName("北京天太世统科技有限公司");
                    sk.setWfStatus4(wfStatus4);
                    dd.setWfStatus4(wfStatus4);
                    sk.setBillsType("收款单");
                    sk.setjNumOrder(dd.getJournalNum());
                    sk.setCashierDate(new Date());
                    //sk.setJournalNum(serverService.getBillID(dd.getCompanyID()));
                    sk.setTrade_no(payCashierBill.getTrade_no());
                    sk.setWfStatus1(wfStatus1);
                    dd.setTrade_no(payCashierBill.getTrade_no());
                    dd.setWfStatus1(wfStatus1);


                    DtOrderBillAdd billAdd = (DtOrderBillAdd) baseBeanService
                            .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum=?", new Object[]{payCashierBill.getOriJournalNum()});

                    billAdd.setFkDate(new Date());

                    backList.add(billAdd);

                    StatusEntity status = goldOrderServiceImpl.SaveStatus(dd.getCashierBillsID(), dd.getJournalNum(), "03", null);
                    status.setXddate(dd.getCashierDate());
                    backList.add(status);

                    List<String> hqls = new ArrayList<String>();
                    List<Object[]> parmsList = new ArrayList<Object[]>();
                    List<BaseBean> beans = baseBeanService.getListBeanByHqlAndParams("from DtMemberBackup where cashId=?", new Object[]{dd.getCashierBillsID()});
                    if (beans != null) {
                        for (int k = 0; k < beans.size(); k++) {
                            DtMemberBackup backup = (DtMemberBackup) beans.get(k);
                            hqls.add("delete DT_MEMBER_BACKUP where MB_ID=?");
                            parmsList.add(new Object[]{backup.getMbId()});
                        }
                    }
                    if (hqls.size() > 0) {
                        String[] toBeStored = hqls.toArray(new String[hqls.size()]);
                        baseBeanService.executeSqlsByParmsList(null, toBeStored, parmsList);
                    }

                    String goodl = "from GoodsBills d where d.cashierBillsID=?";

                    List<BaseBean> ret = baseBeanService.getListBeanByHqlAndParams(goodl, new String[]{dd.getCashierBillsID()});

                    String hqlpp = "from ProductPackaging t where t.ppID = ?";

                    Company comp = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom, new Object[]{dd.getCompanyID()});

                    String goodsTotal = "";
                    BigDecimal of = BigDecimal.ZERO;//报名产品操作费
                    for (int i = 0; i < ret.size(); i++) {
                        GoodsBills gbs = (GoodsBills) ret.get(i);

                        goodsTotal += gbs.getGoodsName() + ",";
                        mes = gbs.getTypeID();
                        BigDecimal zs = BigDecimal.ZERO; //单个产品售价
                        BigDecimal e = BigDecimal.ZERO;//产品业务佣金
                        BigDecimal p = BigDecimal.ZERO;//实际价格
                        BigDecimal c = BigDecimal.ZERO;//产品总业务佣金

                        Object[] zpro_su = null;
                        Object zpro_setup = null;
                        Object[] psuObj = null;
                        Enroll en = (Enroll) baseBeanService.getBeanByHqlAndParams("from Enroll where  cashierBillsID=? and ppID=? and payMethod=?", new Object[]{dd.getCashierBillsID(),gbs.getPpID(),"01"});
                        if (en != null && en.getPpID().equals(gbs.getPpID())) {
                            of = new BigDecimal((en.getOperatingFee() == null || en.getOperatingFee().equals("")) ? "0" : en.getOperatingFee());
                        }

                        String priceType = (gbs.getPricetype() == null || gbs.getPricetype().equals("")) ? "0" : gbs.getPricetype();
                        String activityid = "";
                        if(gbs.getPricetype()!=null&&!gbs.getPricetype().equals("undefined")&&!gbs.getPricetype().equals("")) {
                            activityid=gbs.getActivityID();
                        }else {
                            activityid=null;
                        }

                        if (priceType == null || (priceType.equals("0") && activityid == null)) {
                            zpro_setup = baseBeanService.getObjectBySqlAndParams(VOtool.getsql(1, 1, 1).toString(), new Object[]{gbs.getPpID(), gbs.getCompanyID()});
                        } else {
                            zpro_setup = baseBeanService.getObjectBySqlAndParams(VOtool.getsql(1, Integer.valueOf(priceType) + 1, 2).toString(), new Object[]{activityid});
                            if (zpro_setup == null) {
                                zpro_setup = baseBeanService.getObjectBySqlAndParams(VOtool.getsql(1, Integer.valueOf(priceType) + 1, 3).toString(), new Object[]{activityid});
                            }
                        }
                        ////S.EF_PRICE,S.RE_PRICE,S.BROKERAGE
                        if (zpro_setup != null) {
                            zpro_su = (Object[]) zpro_setup;
                            if (zpro_su[1] != null && zpro_su[2] != null && zpro_su[3] != null) {


                                List<Object> splist = baseBeanService.getListBeanBySqlAndParams(VOtool.getsql(2, Integer.valueOf(priceType) + 1, 1).toString(), new Object[]{zpro_su[3]});

                                if (splist == null || splist.size() <= 0) {

                                    splist = baseBeanService.getListBeanBySqlAndParams(VOtool.getsql(2, Integer.valueOf(priceType) + 1, 2).toString(), new Object[]{zpro_su[3]});

                                }
                                psuObj = new Object[zpro_su.length + 1];

                                System.arraycopy(zpro_su, 0, psuObj, 0, zpro_su.length);

                                psuObj[psuObj.length - 1] = splist;

                                zs = new BigDecimal(zpro_su[1].toString()); //单个产品售价

                                ss = ss.add(zs.multiply(new BigDecimal(gbs.getQuantity())));  //订单产品总价格

                                p = new BigDecimal(gbs.getPrice());  //实际价格

                                e = new BigDecimal(zpro_su[2].toString()); //单产品业务佣金

                                c = e.multiply(new BigDecimal(gbs.getQuantity())); //产品总业务佣金

                                bksum = bksum.add(c);  //产品业务总佣金
                            }
                        }

                        if (gbs.getTypeID() != null && gbs.getTypeID().indexOf("金币计时") != -1) {
                            makeAppointmentService.setBookState(ddid);
                        } else if (gbs.getTypeID() != null && (gbs.getTypeID().indexOf("包月") != -1 || gbs.getTypeID().indexOf("包年") != -1 || gbs.getTypeID().indexOf("包天") != -1)) {
                            //xgb新增修改车辆计费信息
                            cmService.updateTiming(dd.getJournalNum(), gbs.getTypeID());
                            logger.error("订单凭证=" + dd.getJournalNum() + "---------类型=" + gbs.getTypeID());
                            type = gbs.getTypeID();
                        }

                        //tradeName
                        if (gbs.getTypeID().equals("公司会员") || gbs.getTypeID().equals("个人会员")) {
                            //会员升级
                            SelfCart selfCart = (SelfCart) baseBeanService.getBeanByHqlAndParams("from SelfCart where journalNum = ?", new Object[]{ddid});
                        } else if ((gbs.getTypeID().equals("省级城市") || gbs.getTypeID().equals("村级新城")
                                || gbs.getTypeID().equals("县级城市") || gbs.getTypeID().equals("乡镇城市")) || gbs.getTypeID().equals("平台分期")) {
                            StringBuilder strq = new StringBuilder();
                            strq.append(" select cco.compnay_id,con.CompanyName from DT_ccom_com cco,dtContactCompany con");
                            strq.append(" where  cco.ccompany_Id =con.ccompanyId and con.CompanyName =?");
                            List list = baseBeanService.getListBeanBySqlAndParams(strq.toString(), new Object[]{dd.getPlatfromConpanyName()});

                            Object[] str = (Object[]) list.get(0);
                            //thcuscom.setSccId(serverService.getServerID("sccId"));
                            thcuscom.setAccount(tsc.getAccount());
                            thcuscom.setStaffid(tsc.getStaffid());
                            thcuscom.setCusType("0");
                            thcuscom.setSuperioragent(tsc.getSuperioragent());
                            thcuscom.setSupperSccId(tsc.getSupperSccId());
                            thcuscom.setState("2");
                            thcuscom.setTeccDate(new Date());
                            thcuscom.setPpid(dd.getPlatfromid());
                            thcuscom.setPseudoCompanyName(dd.getPlatfromConpanyName());
                            if (list != null) {
                                //暂时注释
                                if (str[0].toString() != null) {
                                    thcuscom.setCompanyId(str[0].toString());
                                }
                            }

                            thcuscom.setAcquiesce("00");
                            backList.add(thcuscom);

                        } else if (gbs.getTypeID() != null && gbs.getTypeID().equals("学员报名")) {
                            try {
                                contractService.docTempleateParams(cashierBills, tsc.getStaffid(), ddid, gbs.getPpID(), gbs.getMoney());

                            } catch (Exception ec) {
                                logger.error("操作异常", e);
                            }
                        } else if (gbs.getTypeID() != null && gbs.getTypeID().equals("学员协议")) {
                            try {
                                PayBackupBill pb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{ddid});

                                contractService.updateDocState(pb.getCoID());
                            } catch (Exception ec) {
                                logger.error("操作异常", e);
                            }
                        }
//                        else if (bo && (tsc.getCusType().equals("7") || tsc.getCusType().equals("6")) && (totalprice.compareTo(yb) == 1 || totalprice.compareTo(yb) == 0)) {
//                            backList.addAll(this.upgradeServece(tsc, "5"));
//                            bo = false;
//
//                        }
                        else if (bo && tsc.getCusType().equals("7")) {
                            BigDecimal v = BigDecimal.ZERO;

                            if (zs.compareTo(p) <= 0 && e.multiply(new BigDecimal(gbs.getQuantity())).compareTo(new BigDecimal(3)) == 1) {
                                ProductPackaging ppk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
                                        "from ProductPackaging t where t.type = ? and t.model=?", new String[]{"个人会员", "6"});


                                gbs.setMoney(gbs.getMoney() + "-3");
                                backList.add(gbs);

                                String hqlinvt = "from DepotManage where companyID = ? and depotName= ?";

                                DepotManage wh = (DepotManage) baseBeanService.getBeanByHqlAndParams(hqlinvt, new Object[]{ppk.getCompanyID(), "销售库"});

                                GoodsBills g = new GoodsBills();
                                g.setCashierBillsID(sk.getCashierBillsID());
                                //g.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                                g.setCompanyID(ppk.getCompanyID());
                                g.setTypeID(ppk.getType());
                                g.setDepotID(wh.getDepotID());
                                g.setDepotName(wh.getDepotName());
                                g.setStartDate(new Date());
                                g.setPrice("3");
                                g.setMoney("3");
                                g.setQuantity("1");
                                g.setRealMoney("3");
                                g.setCostmoney("2");
                                g.setGoodsID(ppk.getGoodsID());
                                g.setGoodsName(ppk.getGoodsName());
                                g.setPpID(ppk.getPpID());
                                g.setBoxStandard(ppk.getModel());
                                g.setEntryTime(new Date());
                                g.setGoodsNum(ppk.getProductCode());
                                g.setPremiums("1");

                                Object hy_pro_setup = baseBeanService.getObjectBySqlAndParams(VOtool.getsql(1, 1, 1).toString(), new Object[]{ppk.getPpID(), ppk.getCompanyID()});

                                Object[] hy_pro_setupObj = new Object[zpro_su.length + 1];

                                System.arraycopy(zpro_su, 0, hy_pro_setupObj, 0, zpro_su.length);

                                hy_pro_setupObj[hy_pro_setupObj.length - 1] = null;
                                if (hy_pro_setup != null) {
                                    Object[] hy_pro_su = (Object[]) hy_pro_setup;
                                    bksum = bksum.subtract(new BigDecimal(hy_pro_su[1].toString()));
                                    bksum = bksum.add(new BigDecimal(hy_pro_su[2].toString()));
                                    g.setCostmoney(hy_pro_su[0].toString());
                                }

                                backList.add(g);

                                GoodsBills gg = (GoodsBills) g.cloneGoodsBills();
                                gg.setGoodsBillsKey("");
                                //gg.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                                gg.setCashierBillsID(gbs.getCashierBillsID());
                                backList.add(gg);

                                //vip客户代理分钱数据
                                backList.addAll(dailiFen2(gg.getGoodsBillsID(), "p201602014ECNY2VNSJ0000012165", dd.getCashierBillsID(), dd.getJournalNum(), tsc.getSccId(), ppk.getTradeName(), ppk.getCompanyID(), "1", "0", hy_pro_setupObj, null));


                                ppk.setMonthSales(ppk.getMonthSales() + 1);
                                backList.add(ppk);

                                //backList.addAll(this.upgradeServece(tsc, "6"));
                                bo = false;
                            }
                        }
                        if (sk.getPlatfromid() != null && !sk.getPlatfromid().equals("")) {
                            //购买省县村的判断购买
                            //生成设备安装的绑定代理商
                            TEshopCuscomSub tshop = new TEshopCuscomSub();
                            //tshop.setSccskey(serverService.getServerID("sccSkey"));
                            //tshop.setSccsid(serverService.getServerID("sccsid"));
                            tshop.setAccount(tsc.getAccount());
                            tshop.setSccid(billAdd.getOaSccId());
                            tshop.setTyepPpid(sk.getPlatfromid());
                            tshop.setSjdate(new Date());

                            if (sk.getPlatfromid().equals("p20170220ZVZR76B88M0000000018") || sk.getPlatfromid().equals("p20170220ZVZR76B88M0000000019")
                                    || sk.getPlatfromid().equals("p20170220ZVZR76B88M0000000020")) {
                                //当买资格的时候，此处的Platfromid值是设置佣金的产品ppid 和 名字
                                tshop.setAreappid(gbs.getAreappid());
                            }
                            backList.add(tshop);
                        }
                        GoodsBills goodbill = (GoodsBills) gbs.cloneGoodsBills();
                        goodbill.setTradeNo(tradeNo);
                        goodbill.setGoodsBillsKey("");
                        //goodbill.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                        goodbill.setCashierBillsID(sk.getCashierBillsID());
                        //查询产品表增加销量
                        pps = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(hqlpp, new Object[]{gbs.getPpID()});
                        BigDecimal quan = new BigDecimal(gbs.getQuantity());
                        BigDecimal sale = new BigDecimal(pps.getMonthSales());
                        pps.setMonthSales(sale.add(quan).intValue());

                        //售价
                        BigDecimal prbd = new BigDecimal(zpro_su[1].toString());
                        //成本价
                        BigDecimal cdb = new BigDecimal(gbs.getCostmoney());
                        //公司总销量
                        BigDecimal tsale = new BigDecimal(comp.getTotalSales());
                        comp.setTotalSales(tsale.add(quan).toString());

                        if (prbd.compareTo(cdb) > 0) {
                            //backList.addAll(this.dailiFen(gbs.getGoodsBillsID(), gbs.getPpID(), dd.getCashierBillsID(), dd.getJournalNum(), tsc.getSccId(), pps.getTradeName(), pps.getCompanyID(), gbs.getQuantity(), priceType, activityid,dd.getProID()));
                            backList.addAll(dailiFen2(gbs.getGoodsBillsID(), gbs.getPpID(), dd.getCashierBillsID(), dd.getJournalNum(), tsc.getSccId(), pps.getTradeName(), pps.getCompanyID(), gbs.getQuantity(), priceType, psuObj, dd.getProID()));
                        }
                        backList.add(pps);
                        backList.add(goodbill);
                        //主产品付款成功后，促销品生成订单ljc。
                        StringBuffer phql = new StringBuffer();
                        phql.append("select pm from PromotionAssociation pm,ProductPackaging pp,GoodsBills gb");
                        phql.append(" where pp.ppID=gb.ppID and gb.cashierBillsID=pm.cashierBillsID and pp.ppID=?");
                        phql.append(" and pm.cashierBillsID=? and pm.ptcashierBillsID is null");
                        List<BaseBean> plist = baseBeanService.getListBeanByHqlAndParams(phql.toString(), new Object[]{pps.getPpID(), dd.getCashierBillsID()});
                        if (plist.size() > 0) {
                            PromotionAssociation pa = null;
                            String ptppid = "";
                            String companyid = "";
                            String ptstandard = "";
                            for (int x = 0; x < plist.size(); x++) {
                                pa = (PromotionAssociation) plist.get(x);
                                ptppid += pa.getPpId() + ",";
                                companyid += pa.getCompanyId() + ",";
                                ptstandard += pa.getStandard() + ",,";
                            }
                            //backList.addAll(this.promotionalOrderServece(pps.getPpID(), ptppid, dd, companyid, ptstandard));
                        }
                    }
                    backList.add(comp);
                    BigDecimal p = new BigDecimal(dd.getPriceSub());
                    if (p.compareTo(ss.add(of)) > 0) {
                        //backList.addAll(this.sdfd(dd.getCompanyID(), tsc.getSccId(), dd.getCashierBillsID(), dd.getJournalNum(), p.subtract(ss.add(of)).setScale(2, BigDecimal.ROUND_DOWN)));
                    }
                    //分金币
                    /*backList.addAll(this.getddService(tsc, dd, bksum));
                    if (payCashierBill.getOriJournalNum() != null) {
                        backList.addAll(this.xjrkService(payCashierBill.getOriJournalNum()));
                    }*/
                    // 记录订单跟收款单的关联
                    RelatedBill relatedBill = new RelatedBill();
                    //relatedBill.setRbID(serverService.getServerID("relatedbill"));
                    relatedBill.setCashid(dd.getCashierBillsID());
                    relatedBill.setCashfid(sk.getCashierBillsID());
                    relatedBill.setBilltype("收款单");
                    backList.add(relatedBill);


                    // 记录 打钱给谁了
                    CashApplyBills applyBills = new CashApplyBills();
                    //applyBills.setCashApplyBillsID(serverService.getServerID("cashApply"));
                    applyBills.setCashierBillsID(sk.getCashierBillsID());
                    applyBills.setAppropriateStatus("01");
                    applyBills.setAppropriationcompanyID(tsc.getStaffid());
                    applyBills.setAppropriationcompanyName(sk.getCtUserName());// 拨款方name
                    applyBills.setReceivablesID("company201009046vxdyzy4wg0000000025");// 收款方id
                    applyBills.setReceivablesName("北京天太世统科技有限公司");
                    applyBills.setAppstyle("01");
                    backList.add(applyBills);
                    backList.add(sk);
                    backList.add(dd);
                    backList.add(payCashierBill);
                    HttpSession session = ServletActionContext.getRequest().getSession();
                    SessionWrap sw = SessionWrap.getInstance();
                    sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tsc);
                    if (mes != null && (mes.equals("公司会员") || mes.equals("个人会员"))) {
                        //pushMessage(tsc, ((GoodsBills) ret.get(0)).getGoodsName());
                    }
                    String hqql = " from TEshopCusCom where companyId = ?";
                    TEshopCusCom com = (TEshopCusCom) baseBeanService.getObjectByHqlAndParams(hqql, new Object[]{pps.getCompanyID()});
                    //查询当前公司是否为餐饮行业

                    ContactCompany co = (ContactCompany) baseBeanService.getBeanByHqlAndParams(" from ContactCompany m where m.ccompanyID = (select c.ccompanyId from CcomCom c where c.comanyId = ?) ", new Object[]{dd.getCompanyID()});
                    if (co.getIndustryType() != null && co.getIndustryType().contains("餐饮")) {
                        //getCaiYinXiangQing(dd, dd.getMealNum(), ret, sk.getCashierDate(), billAdd, wfStatus4, com, tsc);
                    } else {
                        if (!"00".equals(dd.getNopush())) {
                            //String attachtype = getAttachType(cashierBills.getJournalNum());
                            /*if ("".equals(attachtype) || (!attachtype.equals("smsk"))) {
                                String content = "恭喜！您有新的数字地球5L5C订单" + dd.getJournalNum() + ",商品为:" + goodsTotal + "收货人姓名:" + billAdd.getReceivename() + ";收货人手机号:" + billAdd.getReceivetel() + ";收货人地址:" + billAdd.getReceiveaddress() + ",请及时处理！";
                                zfMessage(com, content, "支付", "sellerindent", "defray");
                            }*/

                        }
                        if (dd.getWfStatus1() != null && dd.getWfStatus1().equals("00")) {
                            if (tsc != null && tsc.getOpenId() != null && !tsc.getOpenId().equals("")) {
                                //给推送微信通知
                                logger.error("tsc.getOpenId()" + tsc.getOpenId());
                                //pushWechat(tsc.getOpenId(), dd, sk.getCashierDate(), tsc.getSccId());
                            }

                        }
                    }
                    if (mes.equals("公司会员")) {
                        if (tsc.getCompanyId() == null) {

                            PayBackupBill pbb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{ddid});

                            Company company = new Company();// 购买使用对象
                            CDetail cdl = new CDetail();// 公司详细信息
                            if (pbb != null) {
                                //setTempCompany(company, cdl, pbb);
                            } else {
                                cdl.setCompanyAddress("1");
                                cdl.setRegistrationNumber("1");
                                cdl.setCompanyEmail("1");
                                cdl.setCompanyManager("1");
                                cdl.setCompanyPhone("1");
                                company.setCompanyIdentifier(dd.getPlatfromConpanyName());
                                company.setCompanyName(dd.getPlatfromConpanyName());
                                company.setIndustryType(dd.getPlatfromAccount());
                                company.setIndustryId(dd.getIndustryId());
                            }
                            //wfjserv.registerCompanyInfo("", tsc, company, cdl);
                        }
                    }
                }
                //baseBeanService.executeHqlsByParmsList(backList, null, null);
            } else {
                b = false;
            }

            if (type != null && (type.indexOf("包月") != -1 || type.indexOf("包年") != -1 || type.indexOf("包天") != -1)) {
                PayBackupBill pb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{ddid});
                if (pb == null) {
                    //updateFkState(cashierBills.getCashierBillsID());
                    //分金币
                    //transService.Distribution(cashierBills.getCashierBillsID(), "cstaff20160325ZAUAJEU6JH6192643691");
                }
            }


        } catch (BeansException e) {
            logger.error("操作异常", e);
            b = false;
        } catch (NumberFormatException e) {
            logger.error("操作异常", e);
            b = false;
        } catch (CloneNotSupportedException e) {
            logger.error("操作异常", e);
            b = false;
        } catch (Exception e) {
            logger.error("操作异常", e);
            b = false;
        }

        return b;
    }

    /**
     * 代理佣金数据备份（一）新
     *
     * @param goodsbillid 订单产品id
     * @param ppid        产品ppid
     * @param cashid      订单的id编号
     * @param cashjum     订单编号
     * @param sccid       购买产品人员帐号id
     * @param tradeName   产品类型
     * @param companyID   供应商公司id
     * @param quantity    产品数量
     * @param priceType   价格类型
     * @param psuObj      价格数据
     * @param proid       判断是否是打赏功能  打赏：011
     * @return
     */
    public static List<BaseBean> dailiFen2(String goodsbillid, String ppid, String cashid, String cashjum, String sccid, String tradeName, String companyID, String quantity, String priceType, Object[] psuObj, String proid) throws Exception {
        //p20170220ZVZR76B88M0000000022	客户积分
        //p20170510SIR5KABJEP0000000003	未招标成功代理
        //p20170220ZVZR76B88M0000000017	设备安装
        //p20170220ZVZR76B88M0000000016	贴牌
        //p20170220ZVZR76B88M0000000019	县级代理
        //p20170220ZVZR76B88M0000000018	省级代理
        //p20170220ZVZR76B88M0000000020	村级代理
        //p20170220ZVZR76B88M0000000014	全国代理
        //p20170605KY3VAANZJG0000000003	设备投资

        //  logger.error("代理佣金分配方式存储开始");
        List<BaseBean> backList = new ArrayList<BaseBean>();
        //判断是否设置设备投资佣金
        boolean sbbl=false;

        StringBuffer s_sql = new StringBuffer();
        s_sql.append(" SELECT C.CUSTYPE,C.SCCID,C.ACCOUNT,C.STAFFID,S.STAFFNAME,C.COMPANYID,C.STATE,C.SUPERIORAGENT ");
        s_sql.append(" FROM T_ESHOP_CUSCOM C,DT_HR_STAFF S ");
        s_sql.append(" WHERE S.STAFFID = C.STAFFID AND C.SCCID=?");

        StringBuffer p_sql = new StringBuffer();
        p_sql.append(" SELECT C.CUSTYPE,C.SCCID,C.ACCOUNT,C.STAFFID,S.STAFFNAME,C.COMPANYID,C.STATE,C.SUPERIORAGENT ");
        p_sql.append(" FROM DT_PRODUCTPACKAGING P,T_ESHOP_CUSCOM C,DT_HR_STAFF S ");
        p_sql.append(" WHERE P.COMPANYID = C.COMPANYID AND S.STAFFID = C.STAFFID AND P.PPID = ?");

        String[] a = {"01", "p20170220ZVZR76B88M0000000019", "p20170220ZVZR76B88M0000000018", "p20170220ZVZR76B88M0000000020", ppid};

        BigDecimal quantityNum = new BigDecimal(quantity);

        //查询报名产品信息
        Enroll e = (Enroll) baseBeanService.getBeanByHqlAndParams("from Enroll where  cashierBillsID=? and ppID=?", new Object[]{cashid,ppid});

        if (psuObj != null) {
            List<Object> subList = (List<Object>) psuObj[5];

            if (subList != null && !subList.isEmpty()) {

                StringBuilder cushql = new StringBuilder(
                        "SELECT * FROM (SELECT T.SCCID FROM T_ESHOP_CUSCOM T");
                cushql.append(" START WITH T.SCCID= ? CONNECT BY PRIOR T.SUPPERSCCID=T.SCCID");
                cushql.append(" ORDER BY T.CUSTYPE ) WHERE ROWNUM=1 ");

                Object ptsccid = baseBeanService
                        .getObjectBySqlAndParams(cushql.toString(),
                                new Object[]{sccid});

                List<BaseBean> slist = null;
                List<BaseBean> sxclist = null;

                List<BaseBean> pplist = baseBeanService.getListBeanByHqlAndParams("FROM ProProxy P WHERE P.ppid = ?", new Object[]{ppid});
                Map<String, ProProxy> proxyMap = null;
                if (pplist != null && !pplist.isEmpty()) {
                    proxyMap = new HashedMap();
                    boolean b = true;
                    boolean c = true;
                    for (int j = 0; j < pplist.size(); j++) {
                        ProProxy pp = (ProProxy) pplist.get(j);
                        proxyMap.put(pp.getTypePpid(), pp);
                        if (pp != null) {
                            if (b && (pp.getTypePpid().equals("p20170220ZVZR76B88M0000000017") || pp.getTypePpid().equals("p20170220ZVZR76B88M0000000016"))) {
                                if (pp.getState() != null && pp.getState().equals("01")) {
                                    StringBuffer result = new StringBuffer("SELECT PRO.PPID, SUB.SCCID, SUB.TYEPPPID");
                                    result.append(" FROM DT_PRO_PROXY PP LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = PP.PPID");
                                    result.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUB.SCCSID = PRO.SCCSID AND SUB.TYEPPPID = PP.TYPE_PPID");
                                    result.append(" WHERE PP.STATE = ? AND SUB.TYEPPPID NOT IN(?,?,?) AND PRO.PPID= ?");

                                    slist = baseBeanService.getListBeanBySqlAndParams(result.toString(), a);
                                    b = false;
                                }
                            } else if (c && (pp.getTypePpid().equals("p20170220ZVZR76B88M0000000019") || pp.getTypePpid().equals("p20170220ZVZR76B88M0000000018") || pp.getTypePpid().equals("p20170220ZVZR76B88M0000000020"))) {
                                if (pp.getState() != null && pp.getState().equals("01")) {
                                    StringBuffer result1 = new StringBuffer("SELECT PRO.PPID, SUB.SCCID, SUB.TYEPPPID, P.GOODSNAME");
                                    result1.append(" FROM DT_PRO_PROXY PP LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = PP.PPID");
                                    result1.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUB.SCCSID = PRO.SCCSID AND SUB.TYEPPPID = PP.TYPE_PPID");
                                    result1.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                                    result1.append(" WHERE PP.STATE = ? AND SUB.TYEPPPID IN (?, ?, ?) AND PRO.PPID = ?");
                                    sxclist = baseBeanService.getListBeanBySqlAndParams(result1.toString(), a);
                                    c = false;
                                }
                            }
                        }
                    }
                }

                DtOrderBillAdd oba = (DtOrderBillAdd) baseBeanService.getBeanByHqlAndParams("FROM DtOrderBillAdd WHERE oaBillId = ? ", new Object[]{cashid});

                BigDecimal d = BigDecimal.ZERO;
                for (int j = 0; j < subList.size(); j++) {
                    Object[] psup = (Object[]) subList.get(j);
                    ProProxy pp = null;
                    if (proxyMap != null && !proxyMap.isEmpty()) {
                        pp = proxyMap.get(psup[1]);
                    }
                    //logger.info("调试信息");
                    if (psup[0] != null || !psup[0].equals("")) {
                        BigDecimal jinbi = new BigDecimal(psup[0].toString()).multiply(quantityNum).multiply(new BigDecimal(100));
                        if (jinbi.compareTo(BigDecimal.ZERO) > 0) {
                            if (psup[1].equals("p20170220ZVZR76B88M0000000017") || psup[1].equals("p20170220ZVZR76B88M0000000016")) {
                                if (pp != null && pp.getState() != null && pp.getState().equals("01")) {
                                    //已抢购除省县村代理以外的该产品
                                    if (slist != null && !slist.isEmpty()) {
                                        for (int i = 0; i < slist.size(); i++) {
                                            Object ojb = slist.get(i);
                                            if (ojb != null) {
                                                Object[] objects = (Object[]) ojb;
                                                if (objects[2].equals(psup[1])) {
                                                    addDlMember(s_sql.toString(), objects[1].toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, objects[2].toString());
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    d = d.add(jinbi);
                                }
                            } else if (psup[1].equals("p20170220ZVZR76B88M0000000019") || psup[1].equals("p20170220ZVZR76B88M0000000018") || psup[1].equals("p20170220ZVZR76B88M0000000020")) {
                                if (pp != null && pp.getState() != null && pp.getState().equals("01")) {
                                    //已抢购省县村代理的该产品
                                    if (sxclist != null && !sxclist.isEmpty()) {
                                        for (int i = 0; i < sxclist.size(); i++) {
                                            Object ojb = sxclist.get(i);
                                            if (ojb != null) {
                                                Object[] objects = (Object[]) ojb;
                                                if (objects[2].equals(psup[1]) && (oba.getReceiveaddress() != null && !oba.getReceiveaddress().equals(""))) {
                                                    int anum = oba.getReceiveaddress().indexOf(objects[3].toString());
                                                    if (anum > -1) {
                                                        addDlMember(s_sql.toString(), objects[1].toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, objects[2].toString());
                                                        break;
                                                    } else {
                                                        if (i == sxclist.size() - 1) {
                                                            addDlMember(s_sql.toString(), ptsccid.toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, psup[1].toString());
                                                        }
                                                    }
                                                } else {
                                                    if (i == sxclist.size() - 1) {
                                                        addDlMember(s_sql.toString(), ptsccid.toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, psup[1].toString());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    d = d.add(jinbi);
                                }
                            } else if (psup[1].equals("p20170220ZVZR76B88M0000000022")) {
                                //购物积分代理
                                addDlMember(s_sql.toString(), sccid, cashid, cashjum, jinbi, ppid, goodsbillid, backList, psup[1].toString());

                            } else if (psup[1].equals("p20170605KY3VAANZJG0000000003") && psuObj[4] != null && !psuObj[4].equals("")) {
                                sbbl=true;
                                //设备投资
                                /*if () {*/
                                jinbi = new BigDecimal(psup[0].toString()).multiply(quantityNum).multiply(new BigDecimal(100));
                                if (proid != null && proid.equals("011")) {//打赏的
                                    Rewarddetail rd = (Rewarddetail) baseBeanService.getBeanByHqlAndParams("from Rewarddetail r where r.ordernum=? and r.tradeStatus=?", new Object[]{cashjum, "00"});
                                    addDlMember(s_sql.toString(), rd.getFbSccid(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, psup[1].toString());
                                } else {//普通的
                                    String sid = null;//设备投资人
                                    if (e != null&&e.getPayMethod()!=null&&!e.getPayMethod().equals("")) {
                                        if(e.getPayMethod().equals("01")){
                                            //报名产品合并支付操作费收款人为设备投资责任人
                                            //报名产品合并支付操作费为设备投资佣金
                                            jinbi=new BigDecimal(e.getOperatingFee()).multiply(new BigDecimal(100));
                                            addDlMember(s_sql.toString(), e.getOperatingSccid(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, psup[1].toString());
                                        }else {
                                            continue;
                                        }
                                    } else {
                                        MarKeting marketing = (MarKeting) baseBeanService.getBeanByHqlAndParams("from MarKeting where userSccId=?", new Object[]{sccid});
                                        if (marketing != null) {
                                            sid = marketing.getMkSccId();
                                        } else {
                                            throw new Exception("设备投资数据异常！");
                                        }

                                        String sql = "select d.dbsccid from dt_deviceBind d,dt_devicebind_staff ds where d.dbid=ds.dbsdbid and d.dbstatus=? and ds.dbsstatus=? and ds.dbssccid=? and d.dbtzType=?";
                                        Object ywsccid = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{'1', '1', sid, psuObj[4]});
                                        List<Object> pamer = new ArrayList<Object>();
                                        //通过业务员找到设备及设备投资人如果找不到则绑定产品供应商公司
                                        if (ywsccid != null) {
                                            addDlMember(s_sql.toString(), ywsccid.toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, psup[1].toString());
                                        } else {
                                            addDlMember(p_sql.toString(), ppid, cashid, cashjum, jinbi, ppid, goodsbillid, backList, psup[1].toString());
                                        }
                                    }
                                }
                                /*} else {
                                    logger.error("设备投资:投资设备类型为空");

                                    psuObj[4].toString();
                                }*/
                            }
                        }
                        if (j == subList.size() - 1 && d.compareTo(BigDecimal.ZERO) == 1) {
                            addDlMember(p_sql.toString(), ppid, cashid, cashjum, d, ppid, goodsbillid, backList, "p20170510SIR5KABJEP0000000003");
                        }
                    }
                }

                //产品没设置设备投资佣金并且是合并支付的报名产品的设备投资佣金分配方案
                if(!sbbl){
                    if (e != null) {
                        if(e.getPayMethod().equals("01")){
                            //报名产品合并支付操作费收款人为设备投资责任人
                            //报名产品合并支付操作费为设备投资佣金
                            BigDecimal jinbi=new BigDecimal(e.getOperatingFee()).multiply(new BigDecimal(100));
                            addDlMember(s_sql.toString(), e.getOperatingSccid(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, "p20170605KY3VAANZJG0000000003");
                        }
                    }
                }
            }
        }
        //    logger.error("代理佣金分配方式存储结束");
        return backList;
    }


    /**
     * 代理佣金数据备份（二）
     *
     * @param sql         查询人员信息sql
     * @param p_obj       查询人员信息参数
     * @param cashid      订单id
     * @param cashjum     订单编号
     * @param noScore     佣金数
     * @param ppid        产品id
     * @param goodsbillid 订单产品id
     * @param beanList    保存数组
     * @param type_ppid   代理类型ppid
     */
    public static void addDlMember(String sql, String p_obj, String cashid, String cashjum, BigDecimal noScore, String ppid, String goodsbillid, List<BaseBean> beanList, String type_ppid) throws Exception {

        //查询人员信息
        Object[] colInfor = (Object[]) baseBeanService.getObjectBySqlAndParams(sql, new Object[]{p_obj});

        if (colInfor != null && colInfor.length > 0) {
            if (noScore.compareTo(BigDecimal.ZERO) == 1) {
                //将查询到的数据添加到DtDaiLiMember表中
                DtDaiLiMember dt1 = new DtDaiLiMember();

                //dt1.setMbId(serverService.getServerID("Mbid"));
                dt1.setCashId(cashid);
                dt1.setCashJum(cashjum);
                dt1.setMbType(colInfor[0] != null ? colInfor[0].toString() : null);
                dt1.setMbCusId(colInfor[1].toString());
                dt1.setMbZh(colInfor[2].toString());
                dt1.setStaffId(colInfor[3].toString());
                dt1.setStaffName(colInfor[4] != null ? colInfor[4].toString() : null);
                dt1.setComId(colInfor[5] != null ? colInfor[5].toString() : null);
                dt1.setMbStatus(colInfor[6] != null ? colInfor[6].toString() : null);
                dt1.setJbNum(noScore.toString());
                dt1.setMbPid(colInfor[7] != null ? colInfor[7].toString() : null);
                dt1.setPpid(ppid);
                dt1.setTypePpid(type_ppid);
                dt1.setStatus("0");
                ProductPackaging pro = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
                        "from ProductPackaging where ppid = ?", new Object[]{type_ppid});
                dt1.setZgMingcheng(pro.getGoodsName());
                dt1.setGoodsbillid(goodsbillid);
                beanList.add(dt1);
                //  logger.error(pro.getGoodsName() + "-" + (colInfor[4] != null ? colInfor[4].toString() : null) + "-" + (colInfor[2] != null ? colInfor[2].toString() : null) + "-" + noScore);

            } else {
                logger.error("代理佣金：金额为0");
                noScore.toString();
            }
        } else {
            logger.error("代理佣金：用户数据为空");
            colInfor[4].toString();
        }
    }


    public static String way2(){
        HttpServletRequest request = ServletActionContext.getRequest();
        String jum=request.getParameter("cashJum");
        mbList=baseBeanService.getListBeanByHqlAndParams("from DtMemberBackup where cashJum=?",new Object[]{jum});
        dlList=baseBeanService.getListBeanByHqlAndParams("from DtDaiLiMember where cashJum=?",new Object[]{jum});
        ppList=new ArrayList<BaseBean>();
        List<String > slist=new ArrayList<String>();
        for (int i = 0; i <dlList.size() ; i++) {
            DtDaiLiMember dl=(DtDaiLiMember) dlList.get(i);
            if(!slist.contains(dl.getPpid())){
                slist.add(dl.getPpid());
                ProductPackaging packaging=(ProductPackaging)baseBeanService.getBeanByHqlAndParams("from ProductPackaging where ppid=?",new Object[]{dl.getPpid()});
                ppList.add(packaging);
            }
        }
        return "way2";
    }

    public static String way3(){
        HttpServletRequest re = ServletActionContext.getRequest();
        String path = re.getContextPath();
        String basePath = re.getScheme()+"://"+re.getServerName()+":"+re.getServerPort()+path+"/";
        DefaultAlipayClient alipayClient = AlipayConfig.AlipayConfig();
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest ();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel ();
        model.setBody("我是测试数据" );
        model.setSubject ( "App支付测试Java" );
        model.setOutTradeNo ( "111111" );
        model.setTimeoutExpress ( "30m" );
        model.setTotalAmount ( "0.01" );
        model.setProductCode ( "QUICK_MSECURITY_PAY" );
        request.setBizModel ( model );
        request.setNotifyUrl ( "/ea/sm/ea_getzfb.jspa" );
        try  {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute( request );
            logger.info("调试信息"); //就是orderString 可以直接给客户端请求，无需再做处理。
        }  catch (AlipayApiException e ) {
            logger.error("操作异常", e);
        }
        return "success";
    }

    public List<BaseBean> getMbList() {
        return mbList;
    }

    public void setMbList(List<BaseBean> mbList) {
        this.mbList = mbList;
    }

    public List<BaseBean> getDlList() {
        return dlList;
    }

    public void setDlList(List<BaseBean> dlList) {
        this.dlList = dlList;
    }

    public List<BaseBean> getPpList() {
        return ppList;
    }

    public void setPpList(List<BaseBean> ppList) {
        this.ppList = ppList;
    }
}
