package com.tiantai.wfj.service.impl;

import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.service.GoldOrderService;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.AccessToken;
import com.wechat.bo.TemplateMsg;
import com.wechat.bo.TemplateMsgResult;
import com.wechat.bo.sft.Amount;
import com.wechat.bo.sft.SettleInfo;
import com.wechat.bo.sft.SubOrders;
import com.wechat.utils.ConstantURL;
import com.wechat.utils.WeChatUtil;
import com.wechat.utils.WeixinUtil;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.Enroll;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BenDis.*;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.brokerage.*;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarManage;
import hy.ea.finance.service.transferService;
import hy.ea.marketing.bo.ProProxy;
import hy.ea.office.service.CarManageService;
import hy.ea.office.service.ContractService;
import hy.ea.office.service.MakeAppointmentService;
import hy.ea.st.service.EnrollsService;
import hy.ea.util.Constant;
import hy.ea.util.MobileMessage;
import hy.ea.util.VOtool;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class GoldOrderServiceImpl implements GoldOrderService {

    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private WfjJifenService wfjserv;

    @Resource
    private EnrollsService enrollsService;

    @Resource
    private BaseBeanService baseBeanService;

    @Autowired
    private MobileMessage msage;//发短信zzl

    private final Logger logger = LoggerFactory.getLogger(GoldOrderServiceImpl.class);

    @Resource
    private CarManageService cmService;
    @Resource
    private MakeAppointmentService makeAppointmentService;
    @Resource
    private CarManageService carManageService;
    @Resource
    private ContractService contractService;
    @Resource
    private transferService transService;

    /**
     * 生成收款单
     *
     * @param tradeNo 支付宝或者第三方交易号
     * @param ddid    单据表订单号
     * @param morrt   交易金额
     * @return
     */
    @Override
    public synchronized Boolean generateBill(String tradeNo, String ddid, String morrt, String wfStatus4, String wfStatus1) {
        Boolean b = true;
        String type = "";
        try {
            List<BaseBean> backList = new ArrayList<BaseBean>();
            List<BaseBean> pcbs = new ArrayList<BaseBean>();

            String payhql = "from PayCashierBill where payJournalNum=? and dtype is null";
            pcbs = beandao.getListBeanByHqlAndParams(payhql, new String[]{ddid});

            if (pcbs.size() == 0) {
                payhql = "from PayCashierBill where oriJournalNum=?";
                pcbs = beandao.getListBeanByHqlAndParams(payhql, new String[]{ddid});
            }
            //logger.error("输出错误判断是否有值" + pcbs);
            String hqlh = " from CashierBills where journalNum = ?";
            CashierBills cashierBills = (CashierBills) beandao.getBeanByHqlAndParams(hqlh, new Object[]{((PayCashierBill) pcbs.get(0)).getOriJournalNum()});
            boolean bo = true;
            BigDecimal totalprice = BigDecimal.ZERO; //累计所有订单总金额
            BigDecimal yb = new BigDecimal(100);
            TEshopCusCom thcuscom = new TEshopCusCom();
            if (cashierBills.getFkStatus().equals("01") || cashierBills.getFkStatus().equals("09")||cashierBills.getFkStatus().equals("19")) {
                DtOrderBillAdd bd = (DtOrderBillAdd) beandao.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum=?", new Object[]{cashierBills.getJournalNum()});
                //查询当前订单用户
                String hql2 = "from TEshopCusCom d where d.sccId=?";
                TEshopCusCom tsc = (TEshopCusCom) beandao.getBeanByHqlAndParams(hql2, new String[]{bd.getOaSccId()});
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
                    Object obj = beandao.getObjectBySqlAndParams(sql1, param.toArray());
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
                    dd = (CashierBills) beandao.getBeanByHqlAndParams(hql,
                            new String[]{payCashierBill.getOriJournalNum()});

                    if (dd == null) {
                        System.out.println("没有查询出订单,订单号:" + payCashierBill.getOriJournalNum());
                        continue;
                    } else if (!dd.getFkStatus().equals("01") && !dd.getFkStatus().equals("09")&& !dd.getFkStatus().equals("19")) {
                        System.out.println("该订单已经付款,订单号：" + payCashierBill.getOriJournalNum());
                        return null;
                    }

                    sk = (CashierBills) dd.cloneCashierBills();

                    BeanUtils.copyProperties(dd, sk);
                    sk.setCashierBillsID(serverService.getServerID("CashierBills"));
                    sk.setCashierBillsKey("");
                    sk.setFkStatus("00");
                    dd.setFkStatus("00");
                    if(Float.parseFloat(dd.getPriceSub())!=Float.parseFloat(morrt)){
                        sk.setPriceSub(morrt);
                    }else{
                        sk.setPriceSub(dd.getPriceSub());
                    }

                    sk.setStatus("45");
                    sk.setCompanyID("company201009046vxdyzy4wg0000000025");
                    sk.setCompanyName("北京天太世统科技有限公司");
                    sk.setWfStatus4(wfStatus4);
                    dd.setWfStatus4(wfStatus4);
                    sk.setBillsType("收款单");
                    sk.setjNumOrder(dd.getJournalNum());
                    sk.setCashierDate(new Date());
                    sk.setJournalNum(serverService.getBillID(dd.getCompanyID()));
                    sk.setTrade_no(payCashierBill.getTrade_no());
                    sk.setWfStatus1(wfStatus1);
                    dd.setTrade_no(payCashierBill.getTrade_no());
                    dd.setWfStatus1(wfStatus1);


                    DtOrderBillAdd billAdd = (DtOrderBillAdd) beandao
                            .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum=?", new Object[]{payCashierBill.getOriJournalNum()});

                    billAdd.setFkDate(new Date());

                    backList.add(billAdd);

                    StatusEntity status = SaveStatus(dd.getCashierBillsID(), dd.getJournalNum(), "03", null);
                    status.setXddate(dd.getCashierDate());
                    backList.add(status);

                    List<String> hqls = new ArrayList<String>();
                    List<Object[]> parmsList = new ArrayList<Object[]>();
                    List<BaseBean> beans = beandao.getListBeanByHqlAndParams("from DtMemberBackup where cashId=?", new Object[]{dd.getCashierBillsID()});
                    if (beans != null) {
                        for (int k = 0; k < beans.size(); k++) {
                            DtMemberBackup backup = (DtMemberBackup) beans.get(k);
                            hqls.add("delete DT_MEMBER_BACKUP where MB_ID=?");
                            parmsList.add(new Object[]{backup.getMbId()});
                        }
                    }
                    if (hqls.size() > 0) {
                        String[] toBeStored = hqls.toArray(new String[hqls.size()]);
                        beandao.executeSqlsByParmsList(null, toBeStored, parmsList);
                    }

                    String goodl = "from GoodsBills d where d.cashierBillsID=?";

                    List<BaseBean> ret = beandao.getListBeanByHqlAndParams(goodl, new String[]{dd.getCashierBillsID()});

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
                            SelfCart selfCart = (SelfCart) beandao.getBeanByHqlAndParams("from SelfCart where journalNum = ?", new Object[]{ddid});
                            if (selfCart != null && selfCart.getCardNum() != null) {
                                GiftCards giftCards = (GiftCards) beandao.getBeanByHqlAndParams("from GiftCards where cardNumber = ?", new Object[]{selfCart.getCardNum()});
                                TEshopCusCom tscbycard = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where staffid = ?", new Object[]{giftCards.getStaffID()});
                                backList.addAll(this.upgradeServece(tscbycard, gbs.getBoxStandard()));
                            } else {



                                String hqlcxp = "from CashierBills c where  c.cashierBillsID  = (select n.cashierBillsID from PromotionAssociation n where n.ptcashierBillsID = ?)";
                                CashierBills cb = (CashierBills)beandao.getBeanByHqlAndParams(hqlcxp,new Object[]{dd.getCashierBillsID()});
                                if(cb!=null){
                                    TEshopCusCom ztc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where staffid = ?", new Object[]{cb.getContactUserID()});
                                    backList.addAll(this.upgradeServece(ztc, gbs.getBoxStandard()));
                                }else{
                                    backList.addAll(this.upgradeServece(tsc, gbs.getBoxStandard()));
                                }


                            }
                        } else if ((gbs.getTypeID().equals("省级城市") || gbs.getTypeID().equals("村级新城")
                                || gbs.getTypeID().equals("县级城市") || gbs.getTypeID().equals("乡镇城市")) || gbs.getTypeID().equals("平台分期")) {
                            StringBuilder strq = new StringBuilder();
                            strq.append(" select cco.compnay_id,con.CompanyName from DT_ccom_com cco,dtContactCompany con");
                            strq.append(" where  cco.ccompany_Id =con.ccompanyId and con.CompanyName =?");
                            List list = beandao.getListBeanBySqlAndParams(strq.toString(), new Object[]{dd.getPlatfromConpanyName()});

                            Object[] str = (Object[]) list.get(0);
                            thcuscom.setSccId(serverService.getServerID("sccId"));
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
                                ec.printStackTrace();
                            }
                        } else if (gbs.getTypeID() != null && gbs.getTypeID().equals("学员协议")) {
                            try {
                                PayBackupBill pb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{ddid});

                                contractService.updateDocState(pb.getCoID());
                            } catch (Exception ec) {
                                ec.printStackTrace();
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
                                ProductPackaging ppk = (ProductPackaging) beandao.getBeanByHqlAndParams(
                                        "from ProductPackaging t where t.type = ? and t.model=?", new String[]{"个人会员", "6"});


                                gbs.setMoney(gbs.getMoney() + "-3");
                                backList.add(gbs);

                                String hqlinvt = "from DepotManage where companyID = ? and depotName= ?";

                                DepotManage wh = (DepotManage) beandao.getBeanByHqlAndParams(hqlinvt, new Object[]{ppk.getCompanyID(), "销售库"});

                                GoodsBills g = new GoodsBills();
                                g.setCashierBillsID(sk.getCashierBillsID());
                                g.setGoodsBillsID(serverService.getServerID("GoodsBills"));
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
                                gg.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                                gg.setCashierBillsID(gbs.getCashierBillsID());
                                backList.add(gg);

                                //vip客户代理分钱数据
                                backList.addAll(this.dailiFen2(gg.getGoodsBillsID(), "p201602014ECNY2VNSJ0000012165", dd.getCashierBillsID(), dd.getJournalNum(), tsc.getSccId(), ppk.getTradeName(), ppk.getCompanyID(), "1", "0", hy_pro_setupObj, null));


                                ppk.setMonthSales(ppk.getMonthSales() + 1);
                                backList.add(ppk);

                                backList.addAll(this.upgradeServece(tsc, "6"));
                                bo = false;
                            }
                        }
                        if (sk.getPlatfromid() != null && !sk.getPlatfromid().equals("")) { //购买省县村的判断购买
                            //生成设备安装的绑定代理商
                            TEshopCuscomSub tshop = new TEshopCuscomSub();
                            tshop.setSccskey(serverService.getServerID("sccSkey"));
                            tshop.setSccsid(serverService.getServerID("sccsid"));
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
                        goodbill.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                        goodbill.setCashierBillsID(sk.getCashierBillsID());
                        //查询产品表增加销量
                        pps = (ProductPackaging) beandao.getBeanByHqlAndParams(hqlpp, new Object[]{gbs.getPpID()});
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
                            backList.addAll(this.dailiFen2(gbs.getGoodsBillsID(), gbs.getPpID(), dd.getCashierBillsID(), dd.getJournalNum(), tsc.getSccId(), pps.getTradeName(), pps.getCompanyID(), gbs.getQuantity(), priceType, psuObj, dd.getProID()));
                        }
                        backList.add(pps);
                        backList.add(goodbill);
                        //主产品付款成功后，促销品生成订单ljc。
                        StringBuffer phql = new StringBuffer();
                        phql.append("select pm from PromotionAssociation pm,ProductPackaging pp,GoodsBills gb");
                        phql.append(" where pp.ppID=gb.ppID and gb.cashierBillsID=pm.cashierBillsID and pp.ppID=?");
                        phql.append(" and pm.cashierBillsID=? and pm.ptcashierBillsID is null");
                        List<BaseBean> plist = beandao.getListBeanByHqlAndParams(phql.toString(), new Object[]{pps.getPpID(), dd.getCashierBillsID()});
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
                            backList.addAll(this.promotionalOrderServece(pps.getPpID(), ptppid, dd, companyid, ptstandard));
                        }
                    }
                    backList.add(comp);
                    BigDecimal p = new BigDecimal(dd.getPriceSub());
                    if (p.compareTo(ss.add(of)) > 0) {
                        backList.addAll(this.sdfd(dd.getCompanyID(), tsc.getSccId(), dd.getCashierBillsID(), dd.getJournalNum(), p.subtract(ss.add(of)).setScale(2, BigDecimal.ROUND_DOWN)));
                    }
                    //分金币
                    backList.addAll(this.getddService(tsc, dd, bksum));
                    if (payCashierBill.getOriJournalNum() != null) {
                        backList.addAll(this.xjrkService(payCashierBill.getOriJournalNum()));
                    }
                    // 记录订单跟收款单的关联
                    RelatedBill relatedBill = new RelatedBill();
                    relatedBill.setRbID(serverService.getServerID("relatedbill"));
                    relatedBill.setCashid(dd.getCashierBillsID());
                    relatedBill.setCashfid(sk.getCashierBillsID());
                    relatedBill.setBilltype("收款单");
                    backList.add(relatedBill);


                    // 记录 打钱给谁了
                    CashApplyBills applyBills = new CashApplyBills();
                    applyBills.setCashApplyBillsID(serverService.getServerID("cashApply"));
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
                        pushMessage(tsc, ((GoodsBills) ret.get(0)).getGoodsName());
                    }
                    String hqql = " from TEshopCusCom where companyId = ?";
                    TEshopCusCom com = (TEshopCusCom) beandao.getObjectByHqlAndParams(hqql, new Object[]{pps.getCompanyID()});
                    //查询当前公司是否为餐饮行业

                    ContactCompany co = (ContactCompany) beandao.getBeanByHqlAndParams(" from ContactCompany m where m.ccompanyID = (select c.ccompanyId from CcomCom c where c.comanyId = ?) ", new Object[]{dd.getCompanyID()});
                    if (co.getIndustryType() != null && co.getIndustryType().contains("餐饮")) {
                        getCaiYinXiangQing(dd, dd.getMealNum(), ret, sk.getCashierDate(), billAdd, wfStatus4, com, tsc);
                    } else {
                        if (!"00".equals(dd.getNopush())) {
                            String attachtype = getAttachType(cashierBills.getJournalNum());
                            if ("".equals(attachtype) || (!attachtype.equals("smsk"))) {
                                String content = "恭喜！您有新的数字地球5L5C订单" + dd.getJournalNum() + ",商品为:" + goodsTotal + "收货人姓名:" + billAdd.getReceivename() + ";收货人手机号:" + billAdd.getReceivetel() + ";收货人地址:" + billAdd.getReceiveaddress() + ",请及时处理！";
                                zfMessage(com, content, "支付", "sellerindent", "defray");
                            }

                        }
                        if (dd.getWfStatus1() != null && dd.getWfStatus1().equals("00")) {
                            if (tsc != null && tsc.getOpenId() != null && !tsc.getOpenId().equals("")) {
                                //给推送微信通知
                                logger.error("tsc.getOpenId()" + tsc.getOpenId());
                                pushWechat(tsc.getOpenId(), dd, sk.getCashierDate(), tsc.getSccId());
                            }

                        }
                    }
                    if (mes.equals("公司会员")) {
                        if (tsc.getCompanyId() == null) {

                            PayBackupBill pbb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{ddid});

                            Company company = new Company();// 购买使用对象
                            CDetail cdl = new CDetail();// 公司详细信息
                            if (pbb != null) {
                                setTempCompany(company, cdl, pbb);
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

                           String goodname = pps.getGoodsName();
                            if (goodname.equals("大型企业平台管理商城系统")) {
                                company.setCcomtype("0");
                            } else if (goodname.equals("中型企业平台管理商城系统")) {
                                company.setCcomtype("1");
                            } else if (goodname.equals("小型企业平台管理商城系统")) {
                                company.setCcomtype("2");
                            } else if (goodname.equals("微型企业平台管理商城系统")) {
                                company.setCcomtype("3");
                            } else if (goodname.equals("小微型企业平台管理商城系统")) {
                                company.setCcomtype("4");
                            } else if (goodname.equals("供应商企业平台管理商城系统")) {
                                company.setCcomtype("5");
                            }else if (goodname.equals("0元加入")) {
                                company.setCcomtype("6");
                            }

                            wfjserv.registerCompanyInfo("", tsc, company, cdl);
                        }
                    }




                    //判断货柜相关是否完成结算
                     if("智能货柜".equals(dd.getGoodsName())){
                           setUnPayRecord(dd.getJournalNum());
                     }


                }
                beandao.executeHqlsByParmsList(backList, null, null);
            } else {
                b = false;
            }

            if (type != null && (type.indexOf("包月") != -1 || type.indexOf("包年") != -1 || type.indexOf("包天") != -1)) {
                PayBackupBill pb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{ddid});
                if (pb == null) {
                    updateFkState(cashierBills.getCashierBillsID());
                    //分金币
                    transService.Distribution(cashierBills.getCashierBillsID(), "cstaff20160325ZAUAJEU6JH6192643691");
                }
            }



        } catch (BeansException e) {
            e.printStackTrace();
            b = false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            b = false;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            b = false;
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        }

        return b;
    }

    /**
     *
     * 设置完成支付
     * @param journalNum
     */
    private  void setUnPayRecord(String journalNum){
         try {
             String hql = "from UnPayRecord where journalNum = ? and status = ?";
             UnPayRecord unPayRecord = (UnPayRecord) beandao.getBeanByHqlAndParams(hql, new Object[]{journalNum, "00"});
             if (unPayRecord != null) {
                 unPayRecord.setStatus("01");
                 unPayRecord.setFinishDate(new Date());
                 beandao.update(unPayRecord);
             }
         }catch (Exception e){
             e.printStackTrace();
         }



    }
    /**
     * 会员升级
     *
     * @param tsc         购买产品的会员
     * @param BoxStandard 购买会员级别
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<BaseBean> upgradeServece(TEshopCusCom tsc, String BoxStandard) {

        List<BaseBean> backList = new ArrayList<BaseBean>();
        String cushql = "SELECT t.staffid, t.companyid, t.state,t.superioragent,"
                + "t.pseudo_company_name,t.custype,t.sccid,t.account"
                + " FROM T_ESHOP_CUSCOM t START WITH t.sccid = ?"
                + " CONNECT BY PRIOR t.suppersccid = t.sccid";


        if (Float.parseFloat(BoxStandard) < Float.parseFloat(tsc.getCusType())) {
            //绑定上级
            if (!BoxStandard.equals("0")) {
                if (tsc.getSuperioragent() != null
                        && !tsc.getSuperioragent().equals("")) {
                    List cusList = beandao
                            .getListBeanBySqlAndParams(
                                    cushql,
                                    new Object[]{tsc.getSccId()});
                    float hyType = Float.parseFloat(BoxStandard);
                    for (int j = 0; j < cusList.size(); j++) {
                        Object[] cusCom = (Object[]) cusList.get(j);
                        if (Float.parseFloat(cusCom[5].toString()) < hyType) {
                            tsc.setSuperioragent(cusCom[7].toString());
                            tsc.setSupperSccId(cusCom[6].toString());
                            TEshopCusCom cusCom2 = (TEshopCusCom) beandao
                                    .getBeanByHqlAndParams(
                                            "from TEshopCusCom where account=? AND logOff=0",
                                            new Object[]{cusCom[7]});
                            cusCom2.setTeccDate(new Date());
                            backList.add(cusCom2);
                            break;
                        }
                    }
                }
            } else {
                tsc.setSuperioragent("");
                tsc.setSupperSccId("");
            }
        }
        //变更当前会员级别
        tsc.setCusType(BoxStandard);
        tsc.setTeccDate(new Date());
        backList.add(tsc);

        return backList;
    }

    /**
     * 学员绑推荐人
     *
     * @param ee 报名信息
     * @return
     */
    public synchronized Boolean addMarKeting(Enroll ee) {
        List<BaseBean> backList = new ArrayList<BaseBean>();
        Boolean flag = true;
        DtOrderBillAdd o = (DtOrderBillAdd) beandao.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{ee.getCashierBillsID()});

        try {
            //用户信息
            TEshopCusCom scc = (TEshopCusCom) baseBeanService
                    .getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",
                            new Object[]{o.getOaSccId()});
            //给本人添加上级
            scc.setSupperSccId(ee.getReserved1());
            scc.setSuperioragent(ee.getReserved2());
            backList.add(scc);
            TEshopCusCom tec = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?", new Object[]{o.getOaSccId()});
            //和推荐人互粉
            JoinFans jf1 = (JoinFans) beandao.getBeanByHqlAndParams("from JoinFans where zsccId=?", new Object[]{scc.getSccId()});
            if (jf1 == null) {
                jf1 = new JoinFans();
                jf1.setJfID(serverService.getServerID("jfID"));
                jf1.setSource("app");
                jf1.setState("00");
                jf1.setZaccount(scc.getAccount());
                jf1.setZsccId(scc.getSccId());
            }

            jf1.setFaccount(ee.getReserved2());
            jf1.setFansDate(new Date());
            jf1.setFsccId(ee.getReserved1());
            jf1.setStaffid(tec.getStaffid());
            backList.add(jf1);
            JoinFans jf2 = (JoinFans) beandao.getBeanByHqlAndParams("from JoinFans where fsccId=?", new Object[]{scc.getSccId()});
            if (jf2 == null) {
                jf2 = new JoinFans();
                jf2.setJfID(serverService.getServerID("jfID"));
                jf2.setSource("app");
                jf2.setState("00");
                jf2.setFaccount(scc.getAccount());
                jf2.setFsccId(scc.getSccId());
                jf2.setStaffid(scc.getStaffid());//faccount对应的staffid
            }
            jf2.setFansDate(new Date());
            jf2.setZaccount(ee.getReserved2());
            jf2.setZsccId(ee.getReserved1());
            backList.add(jf2);
            //邀请人修改
            MarKeting mk = (MarKeting) beandao.getBeanByHqlAndParams("from MarKeting where userSccId=?", new Object[]{scc.getSccId()});
            if (mk == null) {
                mk = new MarKeting();
                mk.setMkID(serverService.getServerID("MarKeting"));
                mk.setSource("app");
                mk.setUserID(scc.getAccount());//被邀请人账号
                mk.setUserSccId(scc.getSccId());//被邀请人sccId
            }
            mk.setMkdate(new Date());
            mk.setMkSccId(ee.getReserved1());//邀请人sccId
            mk.setMkuserID(ee.getReserved2());//邀请人账号
            backList.add(mk);
            beandao.executeHqlsByParmsList(backList, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }


    /**
     * 促销品下订单
     *
     * @param ppid       主产品id
     * @param ptppid     促销品id
     * @param cb         主产品订单
     * @param companyid  促销品公司id
     * @param ptstandard 促销品规格
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<BaseBean> promotionalOrderServece(String ppid, String ptppid, CashierBills cb, String companyid, String ptstandard) {


        List<BaseBean> pmlist = new ArrayList<BaseBean>();
        //促销品关联ljc
        if (ptppid != null && ptppid.length() > 0) {
            StringBuilder sqlp = new StringBuilder();
            StringBuilder sqlc = new StringBuilder();//公司
            StringBuilder sqla = new StringBuilder();//地址
            StringBuilder sqls = new StringBuilder();//卖家信息
            StringBuffer hql = new StringBuffer();//促销品关系

            /* 0 产品名称 1产品id 2销售价 3产品主图 4物品id 5公司id 6产品类型 7产品月销量  8型号 9 项目编码*/
            sqlp.append("select ps.ppname,ps.ppid,ps.re_price,p.image,p.goodsid,p.companyid,p.type,p.monthSales,p.model,p.productCode,ps.ef_price");
            sqlp.append(" from DT_PRO_SETUP ps, dt_ProductPackaging p,dt_promotion pt");
            sqlp.append(" where p.ppid = ps.ppid and ps.ppid=pt.ptppid and pt.ppid=? and ps.fcom_id is null and pt.ptppid in(");
            /*0公司id 1 公司名称 2往来单位id 3序号 4库房名称*/
            sqlc.append("select cp.companyid,cc.companyname,cc.ccompanyid,dp.depotid,dp.depotname");
            sqlc.append(" from dtcompany cp,dtcontactcompany cc,dt_ccom_com ccom,dt_productpackaging pp,dtdepotmanage dp");
            sqlc.append(" where dp.companyid=pp.companyid and pp.companyid = cp.companyid and cp.companyid = ccom.compnay_id");
            sqlc.append(" and ccom.ccompany_id = cc.ccompanyid and dp.depotname='销售库' and pp.ppid in (");
            //发货地址
            sqla.append("select ra.rname, ra.rphone, ra.rpostcode, ra.rarea, ra.rstreet,ra.companyid");
            sqla.append(" from dtrefundaddress ra, dt_productpackaging pp, dt_pro_setup ps");
            sqla.append(" where pp.ppid=ps.ppid and pp.companyid=ra.companyid");
            sqla.append(" and ra.rtype = 1 and ra.status = '00' and pp.ppid in (");

            sqls.append("  select sa.staffid,sa.staffname,tec.sccid ");
            sqls.append(" from dt_productpackaging pt, t_eshop_cuscom tec, dt_hr_staff sa");
            sqls.append(" where tec.companyid = pt.companyid");
            sqls.append(" and sa.staffid = tec.staffid");
            sqls.append(" and pt.ppid =? and tec.acquiesce=?");

            hql.append("from PromotionAssociation where cashierBillsID=? and ppId in(");

            String hql_p = "from DtOrderBillAdd where oaBillId=?";
            DtOrderBillAdd doba = (DtOrderBillAdd) beandao.getBeanByHqlAndParams(hql_p, new Object[]{cb.getCashierBillsID()});

            List<Object> params = new ArrayList<Object>();
            List<Object> paramsc = new ArrayList<Object>();
            List<Object> paramsa = new ArrayList<Object>();
            List<Object> paramsp = new ArrayList<Object>();

            params.add(ppid);
            paramsp.add(cb.getCashierBillsID());
            String[] ptstandards = new String[3];
            String[] comids = new String[3];
            if (ptstandard != null && !ptstandard.equals(",,,,")) {
                ptstandards = ptstandard.split(",,");
            }
            if (companyid != null && companyid.length() > 0) {
                comids = companyid.split(",");
            }

            String[] temp = ptppid.split(",");
            for (int i = 0; i < temp.length; i++) {
                if (i == temp.length - 1) {
                    sqlp.append("?");
                    sqlc.append("?");
                    sqla.append("?");
                    hql.append("?");
                } else {
                    sqlp.append("?,");
                    sqlc.append("?,");
                    sqla.append("?,");
                    hql.append("?,");
                }
                params.add(temp[i]);
                paramsc.add(temp[i]);
                paramsa.add(temp[i]);
                paramsp.add(temp[i]);
            }
            sqlp.append(")");
            sqlc.append(")");
            sqla.append(")");
            hql.append(")");

            List plist = beandao.getListBeanBySqlAndParams(sqlp.toString(), params.toArray(new Object[]{}));
            List comlist = beandao.getListBeanBySqlAndParams(sqlc.toString(), paramsc.toArray(new Object[]{}));
            List adlist = beandao.getListBeanBySqlAndParams(sqla.toString(), paramsa.toArray(new Object[]{}));
            List<BaseBean> prlist = beandao.getListBeanByHqlAndParams(hql.toString(), paramsp.toArray(new Object[]{}));
            Object obj = beandao.getObjectBySqlAndParams(sqls.toString(), new Object[]{ppid, "01"});
            Object[] mjObj = (Object[]) obj;
            CashierBills ptcb = null;
            PayCashierBill pc = null;
            DtOrderBillAdd ptdl = null;
            List<BaseBean> cblist = new ArrayList<BaseBean>();
            if (plist.size() > 0) {
                for (int i = 0; i < comids.length; i++) {
                    if (cblist == null || cblist.size() == 0) {
                        //促销品订单
                        ptcb = new CashierBills();
                        ptcb.setCashierBillsID(serverService.getServerID("CashierBills"));
                        ptcb.setCompanyID(comids[i]);
                        ptcb.setJournalNum(serverService.getBillID(comids[i]));
                        cblist.add(ptcb);
                    } else {
                        int times = 0;
                        for (int x = 0; x < cblist.size(); x++) {
                            CashierBills c = (CashierBills) cblist.get(x);
                            if (c.getCompanyID().equals(comids[i])) {
                                times++;
                            }
                        }
                        if (times == 0) {
                            ptcb = new CashierBills();
                            ptcb.setCashierBillsID(serverService.getServerID("CashierBills"));
                            ptcb.setCompanyID(comids[i]);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                logger.error("延时失败！");
                            }
                            ptcb.setJournalNum(serverService.getBillID(comids[i]));
                            cblist.add(ptcb);
                        }
                    }
                }
                for (int i = 0; i < cblist.size(); i++) {
                    ptcb = (CashierBills) cblist.get(i);
                    ptcb.setOrganizationID("organization20110425U539EJC3VF0000012237");
                    ptcb.setCashierDate(new Date());
                    ptcb.setStaffName("系统生成");
                    ptcb.setInputName("系统生成");
                    ptcb.setStatus("40");
                    ptcb.setWfStatus1("0");
                    ptcb.setWfStatus2("00");
                    ptcb.setFkStatus("01");//主产品已付款后,促销品已付款。
                    ptcb.setDepartmentName("客户");
                    ptcb.setZctype("cg");
                    ptcb.setBillsType("项目收入预算单");
                    ptcb.setWfStatus1("00");
                    ptcb.setjNumOrder(ptcb.getJournalNum());
                    ptcb.setStatusbill("04");

                    pc = new PayCashierBill();
                    pc.setPcbID(serverService.getServerID("pcbid"));
                    pc.setOriJournalNum(ptcb.getJournalNum());
                    pc.setPayJournalNum(ptcb.getJournalNum());

                    ptdl = new DtOrderBillAdd();
                    ptdl.setOaId(serverService.getServerID("DtOrderBillAdd"));
                    ptdl.setOaComId(ptcb.getCompanyID());
                    ptdl.setOaSccId(mjObj[2].toString());
                    ptdl.setOaGysId(ptcb.getCompanyID());
                    ptdl.setReceivename(doba.getReceivename());
                    ptdl.setReceivecode(doba.getReceivecode());
                    ptdl.setReceivetel(doba.getReceivetel());
                    ptdl.setReceiveaddress(doba.getReceiveaddress());
                    ptdl.setOaBillId(ptcb.getCashierBillsID());
                    ptdl.setOaBillJounum(ptcb.getJournalNum());
                    //发货
                    if (adlist != null && adlist.size() > 0) {
                        for (int j = 0; j < adlist.size(); j++) {
                            Object[] oa = (Object[]) adlist.get(j);
                            if (oa[5].equals(ptdl.getOaComId())) {
                                ptdl.setSendname(oa[0] != null ? oa[0].toString() : null);
                                ptdl.setSendtel(oa[1] != null ? oa[1].toString() : null);
                                ptdl.setSendcode(oa[2] != null ? oa[2].toString() : null);
                                ptdl.setSendaddress((oa[3] != null ? oa[3].toString() : null) + (oa[4] != null ? oa[4].toString() : null));
                            }
                        }
                    }
                    pmlist.add(ptdl);
                    pmlist.add(pc);
                }


                PromotionAssociation pa = null;
                GoodsBills ptgbs = null;
                CashierBills cashb = null;
                List<BaseBean> gblist = new ArrayList<BaseBean>();
                for (int i = 0; i < plist.size(); i++) {
                    Object[] o = (Object[]) plist.get(i);//产品信息
                    Object[] oc = (Object[]) comlist.get(i);    //公司信息
                    PromotionAssociation promotionAssociation = (PromotionAssociation) prlist.get(i);//促销品订单关联
                    for (int y = 0; y < cblist.size(); y++) {
                        cashb = (CashierBills) cblist.get(y);
                        if (o[5].equals(cashb.getCompanyID())) {
                            cashb.setContactUserID(mjObj[0].toString());
                            cashb.setCtUserName(mjObj[1].toString());
                            cashb.setProjectName(o[0].toString());
                            Float priceSub = Float.parseFloat((cashb.getPriceSub() == null ? "0" : cashb.getPriceSub()));
                            priceSub = priceSub + Float.parseFloat(o[10].toString());
                            cashb.setPriceSub(priceSub.toString());
                            cashb.setCompanyName(oc[1].toString());
                            cashb.setCcompanyID(oc[2].toString());
                            //如果赠品为0元且不是个人会员，公司会员的，订单为付款状态
                            if ("0".equals(o[2]) && !"个人会员".equals(o[6]) && !"公司会员".equals(o[6])) {
                                cashb.setFkStatus("00");
                            }

                            ptgbs = new GoodsBills();
                            ptgbs.setCashierBillsID(cashb.getCashierBillsID());
                            ptgbs.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                            ptgbs.setTypeID(o[6].toString());
                            ptgbs.setDepotID(oc[3].toString());
                            ptgbs.setDepotName(oc[4].toString());
                            ptgbs.setStartDate(new Date());
                            ptgbs.setPrice(o[10].toString());
                            ptgbs.setMoney(o[10].toString());
                            ptgbs.setQuantity(promotionAssociation.getCount() + "");
                            ptgbs.setRealMoney(o[2].toString());
                            ptgbs.setGoodsID(o[4].toString());
                            ptgbs.setGoodsName(o[0].toString());
                            ptgbs.setStandard(ptstandards[i]);
                            ptgbs.setPpID(o[1].toString());
                            ptgbs.setBoxStandard(o[8] != null ? o[8].toString() : null);
                            ptgbs.setEntryTime(new Date());
                            ptgbs.setGoodsNum(o[9].toString());
                            ptgbs.setCostmoney(o[10].toString());
                            ptgbs.setCompanyID(cashb.getCompanyID());
                            //ptgbs.setPremiums("1");//是否是奖品  0或null:否  1:是

                            gblist.add(ptgbs);
                            pmlist.add(ptgbs);
                        }
                    }
                }
                if (prlist != null && prlist.size() > 0) {
                    for (int z = 0; z < prlist.size(); z++) {
                        pa = (PromotionAssociation) prlist.get(z);
                        for (int y = 0; y < gblist.size(); y++) {
                            GoodsBills gb = (GoodsBills) gblist.get(y);
                            if (pa.getPpId().equals(gb.getPpID())) {
                                pa.setPtcashierBillsID(gb.getCashierBillsID());//促销品本身订单号
                                pa.setCreatDate(new Date());
                                pmlist.add(pa);
                            }
                        }
                    }
                }
            }
            pmlist.addAll(cblist);
        }
        return pmlist;
    }

    /**
     * 业务佣金数据备份(一)
     *
     * @param tsc   下订单用户
     * @param dd    订单
     * @param bksum 订单产品总业务佣金数
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<BaseBean> getddService(TEshopCusCom tsc, CashierBills dd, BigDecimal bksum) {
        List<BaseBean> backList = new ArrayList<BaseBean>();
        if (bksum.compareTo(BigDecimal.ZERO) > 0) {
            if (tsc.getCusType().equals("0")) {
                StringBuilder cushql = new StringBuilder(
                        "SELECT t.staffid, t.companyid, t.state,t.superioragent,");
                cushql.append("t.pseudo_company_name,t.custype,t.sccid,t.account ");
                cushql.append("FROM T_ESHOP_CUSCOM t where rownum<=3 START WITH t.sccid= ? ");
                cushql.append("CONNECT BY PRIOR  t.sccid=t.suppersccid");
                List cusList = beandao.getListBeanBySqlAndParams(
                        cushql.toString(), new Object[]{tsc.getSccId()});
                backList.addAll(this.addListService(cusList, null, dd, tsc, bksum));
            } else {
                StringBuilder cushql = new StringBuilder(
                        "SELECT t.staffid, t.companyid, t.state,t.superioragent,");
                cushql.append("t.pseudo_company_name,t.custype,t.sccid,t.account ");
                cushql.append("FROM T_ESHOP_CUSCOM t START WITH t.sccid= ? ");
                cushql.append("CONNECT BY PRIOR t.suppersccid=t.sccid");
                MarKeting keting = (MarKeting) beandao
                        .getBeanByHqlAndParams("from MarKeting where userSccId =?",
                                new Object[]{tsc.getSccId()});

                if (keting != null) {
                    // 推荐人
                    TEshopCusCom t1 = (TEshopCusCom) beandao
                            .getBeanByHqlAndParams(
                                    "from TEshopCusCom t where t.sccId = ?",
                                    new Object[]{keting.getMkSccId()});
                    // 被推荐人
                    TEshopCusCom t2 = (TEshopCusCom) beandao
                            .getBeanByHqlAndParams(
                                    "from TEshopCusCom t where t.sccId = ?",
                                    new Object[]{keting.getUserSccId()});
                    // 被推荐人上级
                    TEshopCusCom t3 = (TEshopCusCom) beandao
                            .getBeanByHqlAndParams(
                                    "from TEshopCusCom t where t.sccId = ?",
                                    new Object[]{t2.getSupperSccId()});
                    // 如果推荐人级别大于被推荐人
                    if (Float.parseFloat(t1.getCusType()) < Float.parseFloat(t2
                            .getCusType())) {

                        // 如果被推荐人级别小于代理商（客户或者vip客户）
                        List cusList = beandao
                                .getListBeanBySqlAndParams(cushql.toString(),
                                        new Object[]{t2.getSccId()});
                        if (cusList != null) {
                            backList.addAll(this.addListService(cusList, t1, dd, t2, bksum));
                        }

                    } else {
                        if (t1.getCusType().equals("7")
                                || t1.getCusType().equals("6")) {
                            // 如果被推荐人级别小于代理商（客户或者vip客户）
                            List cusList1 = beandao
                                    .getListBeanBySqlAndParams(cushql.toString(),
                                            new Object[]{t2.getSccId()});
                            List<Object[]> cusList = cusList1;
                            if (cusList != null) {
                                if (t3.getCusType().equals("1")) {
                                    // 平台
                                    TEshopCusCom t0 = (TEshopCusCom) beandao
                                            .getBeanByHqlAndParams(
                                                    "from TEshopCusCom t where t.sccid = ?",
                                                    new Object[]{cusList
                                                            .get(cusList.size() - 1)[6]});
                                    backList.addAll(this.addListService(cusList, t0, dd, t2, bksum));
                                } else {
                                    backList.addAll(this.addListService(cusList, t3, dd, t2, bksum));
                                }

                            }
                        } else {
                            List cusList = beandao
                                    .getListBeanBySqlAndParams(cushql.toString(),
                                            new Object[]{tsc.getSccId()});
                            if (cusList != null) {
                                backList.addAll(this.addListService(cusList, null, dd, tsc, bksum));
                            }
                        }
                    }
                } else {
                    List cusList = beandao
                            .getListBeanBySqlAndParams(cushql.toString(),
                                    new Object[]{tsc.getSccId()});
                    if (cusList != null) {
                        backList.addAll(this.addListService(cusList, null, dd, tsc, bksum));
                    }
                }
            }
        }
        return backList;
    }


    /**
     * 业务佣金数据备份(二)
     *
     * @param baseList     保存数据数组
     * @param tEshopCusCom 推荐人
     * @param cb           订单
     * @param scc          购买 产品用户
     * @param bksum        订单产品总业务佣金数
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private List<BaseBean> addListService(List baseList, TEshopCusCom tEshopCusCom, CashierBills cb, TEshopCusCom scc, BigDecimal bksum) {
        String hql = "from Staff d where d.staffID=? ";
        List<BaseBean> operator = new ArrayList<BaseBean>();
        if (baseList.size() > 1) {
            DtOrderBillAdd billAdd = (DtOrderBillAdd) beandao.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{cb.getCashierBillsID()});
            Boolean b = false;
            for (int i = 0; i < baseList.size(); i++) {
                // DtMemberBackup表关联ljc
                //将baseList 集合的cuscoms对象转成数组
                Object cuscoms = baseList.get(i);
                Object[] cuscom = (Object[]) cuscoms;

                //将list集合转成Object对象，再将Object对象转成Object数组
                Object baseList1 = baseList.get(1);
                Object[] baseList2 = (Object[]) baseList1;
                DtMemberBackup dmb = new DtMemberBackup();
                if (i == 0) {
                    billAdd.setYjid(cuscom[5].toString());
                    billAdd.setFkDate(new Date());
                    operator.add(billAdd);
                }

                /*if (tEshopCusCom != null) {
                    if (tEshopCusCom.getCusType().equals("0")) {
                        if (cuscom[5].toString().equals("0")) {
                            dmb.setJbbl(100 - (baseList.size() - 1) * 5 + "");
                        } else {
                            dmb.setJbbl("5");
                        }
                    } else {
                        if (Float.parseFloat(baseList2[5].toString()) >= 2
                                && Float.parseFloat(baseList2[5].toString()) <= 5) {
                            if (i == 2) {
                                if (cuscom[5].toString().equals("1")) {
                                    dmb.setJbbl("5");
                                    b = true;
                                } else {
                                    dmb.setJbbl("10");
                                }
                            } else if (i == 1) {
                                dmb.setJbbl(100 - baseList.size() * 5 + "");
                            } else {
                                if (b && cuscom[5].toString().equals("0")) {
                                    dmb.setJbbl("10");
                                } else {
                                    dmb.setJbbl("5");
                                }
                            }
                        }
                    }
                } else {
                    if (baseList.size() <= 3) {
                        if (cuscom[5].toString().equals("0")) {
                            dmb.setJbbl(100 - (baseList.size() - 1) * 5 + "");
                        } else {
                            dmb.setJbbl("5");
                        }
                    } else {
                        if (Float.parseFloat(baseList2[5].toString()) == 1) {
                            if (cuscom[5].toString().equals("0")) {
                                dmb.setJbbl("10");
                            } else if (i == 0) {
                                dmb.setJbbl(100 - baseList.size() * 5 + "");
                            } else {
                                dmb.setJbbl("5");
                            }
                        } else {
                            if (i == 1) {
                                dmb.setJbbl("10");
                            } else if (i == 0) {
                                dmb.setJbbl(100 - baseList.size() * 5 + "");
                            } else {
                                dmb.setJbbl("5");
                            }
                        }
                    }
                }*/

                if (tEshopCusCom != null) {
                    if (tEshopCusCom.getCusType().equals("0")) {
                        if (cuscom[5].toString().equals("0")) {
                            dmb.setJbbl(90 + "");
                        } else {
                            dmb.setJbbl(new BigDecimal(10f / (baseList.size() - 1)).setScale(3, BigDecimal.ROUND_DOWN) + "");
                        }
                    } else {
                        if (Float.parseFloat(baseList2[5].toString()) >= 2
                                && Float.parseFloat(baseList2[5].toString()) <= 5) {
                            if (i == 1) {
                                dmb.setJbbl(90 + "");
                            } else {
                                dmb.setJbbl(new BigDecimal(10f / (baseList.size() - 1)).setScale(3, BigDecimal.ROUND_DOWN) + "");
                            }
                        }
                    }
                } else {
                    if (baseList.size() <= 3) {
                        if (cuscom[5].toString().equals("0")) {
                            dmb.setJbbl(90 + "");
                        } else {
                            dmb.setJbbl(new BigDecimal(10f / (baseList.size() - 1)).setScale(3, BigDecimal.ROUND_DOWN) + "");
                        }
                    } else {
                        if (Float.parseFloat(baseList2[5].toString()) == 1) {
                            if (i == 0) {
                                dmb.setJbbl(90 + "");
                            } else {
                                dmb.setJbbl(new BigDecimal(10f / (baseList.size() - 1)).setScale(3, BigDecimal.ROUND_DOWN) + "");
                            }
                        } else {
                            if (i == 0) {
                                dmb.setJbbl(90 + "");
                            } else {
                                dmb.setJbbl(new BigDecimal(10f / (baseList.size() - 1)).setScale(2, BigDecimal.ROUND_DOWN) + "");
                            }
                        }
                    }
                }

                BigDecimal bl = new BigDecimal(dmb.getJbbl()).divide(new BigDecimal(100));

                dmb.setJbbl(dmb.getJbbl() + "%");
                BigDecimal bksumnum = bksum.multiply(new BigDecimal(100)).multiply(bl);
                System.out.println(cuscom[5].toString() + "--"
                        + (cuscom[7] == null ? "" : cuscom[7].toString()) + "-" + dmb.getJbbl() + "-" + bksumnum);
                dmb.setJbNum(bksumnum + "");
                dmb.setMbId(serverService.getServerID("DtMemberBackup"));
                dmb.setCashId(cb.getCashierBillsID());
                dmb.setCashJum(cb.getJournalNum());
                dmb.setMType(cuscom[5].toString());
                dmb.setMZh(cuscom[7] == null ? "" : cuscom[7].toString());
                dmb.setMId(cuscom[6].toString());
                dmb.setStaffId(cuscom[0].toString());
                Staff staff = (Staff) beandao.getBeanByHqlAndParams(hql,
                        new String[]{cuscom[0].toString()});
                if (staff == null) {
                    System.out.println((cuscom[7] == null ? "" : cuscom[7].toString()) + "-staffname为空");
                } else {
                    dmb.setStaffName(staff.getStaffName());
                }
                staff = null;
                if (cuscom[1] != null && !cuscom[1].equals("")) {
                    dmb.setComId(cuscom[1].toString());
                }
                dmb.setMStatus(cuscom[2].toString());
                if (cuscom[3] != null && !cuscom[3].equals("")) {
                    dmb.setMbPid(cuscom[3].toString());
                }
                operator.add(dmb);
            }
        }

        return operator;
    }


    /**
     * 现金入库
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    @Override
    public List<BaseBean> xjrkService(String journalNum) {
        List<BaseBean> backList = new ArrayList<BaseBean>();
        String caHql = "from CashierBills where journalNum=?";
        CashierBills ca = (CashierBills) beandao.getBeanByHqlAndParams(
                caHql, new Object[]{journalNum});

        // 查询现金库的Id
        String potHql = "from DepotManage where companyID=? and depotName=?";
        DepotManage depot = (DepotManage) beandao
                .getBeanByHqlAndParams(potHql, new Object[]{
                        "company201009046vxdyzy4wg0000000025", "资金仓库"});

        // 查询现金库中是否存在对应的数据，有则在原来基础上相加，无则创建
        String invHql = "from Inventory where companyID=? and goodsName=? and warehouse=? and source=?";
        Inventory inv = (Inventory) beandao.getBeanByHqlAndParams(
                invHql, new Object[]{"company201009046vxdyzy4wg0000000025",
                        "银行存款", depot.getDepotID(), "销售盈利"});
        // 获取对应科目
        String subHql = "from CSubjects where companyID=? and subjectsName=?";
        CSubjects sub = (CSubjects) beandao.getBeanByHqlAndParams(
                subHql, new Object[]{"company201009046vxdyzy4wg0000000025", "银行存款"});

        if (inv == null) {
            // 创建库存数据
            inv = new Inventory();
            inv.setInventoryID(serverService.getServerID("Inventory"));
            inv.setCompanyID("company201009046vxdyzy4wg0000000025");
            inv.setWarehouse(depot.getDepotID());
            inv.setWarehouseName(depot.getDepotName());
            inv.setGoodsType("银行存款");
            inv.setGoodsName("银行存款");
            inv.setUnit("元");
            inv.setUnitPrice("1");
            inv.setInvenQuantity(ca.getPriceSub());
            inv.setGoodstatus("00");
            inv.setSumPrice(ca.getPriceSub());
            inv.setSubjectsID(sub.getSubjectsID());
            inv.setSubjectsName(sub.getSubjectsName());
            inv.setSource("销售盈利");
        } else {
            String quantity = Double.parseDouble(inv.getInvenQuantity())
                    + Double.parseDouble(ca.getPriceSub()) + "";
            inv.setInvenQuantity(quantity);
            inv.setSumPrice(quantity);
        }
        backList.add(inv);
        // 创建库存盘点表
        stockInv sto = new stockInv();
        sto.setStockinvID(serverService.getServerID("stockInv"));
        sto.setCompanyID("company201009046vxdyzy4wg0000000025");
        sto.setGoodsBillsId(ca.getCashierBillsID());
        sto.setGoodsType("银行存款");
        sto.setGoodsName("银行存款");
        sto.setInvenQuantity(ca.getPriceSub());
        sto.setSummoney(ca.getPriceSub());
        sto.setIntime(new Date());
        sto.setType("00");
        sto.setStaffID(ca.getStaffID());
        sto.setStaffName(ca.getStaffName());
        sto.setWarehouse(depot.getDepotID());
        sto.setWarehouseName(depot.getDepotName());
        backList.add(sto);
        return backList;
    }

    /**
     * 积分/金币购买生成订单和出入库单
     *
     * @param tradeNo   支付宝或者第三方交易号
     * @param ddid      单据表订单号
     * @param morrt     交易金币
     * @param wfStatus4 支付方式
     * @return
     */
    @Override
    public synchronized Boolean generateSpoints(String tradeNo, String ddid, String morrt, String wfStatus4) {
        Boolean b = true;

        StringBuffer aaa = null;
        try {
            List<BaseBean> backList = new ArrayList<BaseBean>();

            List<BaseBean> pcbs = new ArrayList<BaseBean>();

            String payhql = "from PayCashierBill where payJournalNum=?";
            pcbs = beandao.getListBeanByHqlAndParams(payhql,
                    new String[]{ddid});

            if (pcbs.size() == 0) {
                payhql = "from PayCashierBill where oriJournalNum=?";
                pcbs = beandao.getListBeanByHqlAndParams(payhql,
                        new String[]{ddid});

            }
            //订单
            String hqlh = " from CashierBills where journalNum = ?";
            CashierBills cashierBills = (CashierBills) beandao.getBeanByHqlAndParams(hqlh, new Object[]{((PayCashierBill) pcbs.get(0)).getOriJournalNum()});

            boolean bo = true;

            BigDecimal totalprice = new BigDecimal(0);
            BigDecimal yb = new BigDecimal(100);
            TEshopCusCom thcuscom = new TEshopCusCom();

            //判断订单是不是未付款状态
            if (cashierBills.getFkStatus().equals("01") || cashierBills.getFkStatus().equals("09")) {
                DtOrderBillAdd bd = (DtOrderBillAdd) beandao
                        .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum=?", new Object[]{cashierBills.getJournalNum()});
                // 查询当前订单用户
                String hql2 = "from TEshopCusCom d where d.sccId=?";

                String hqlbb = "from PayBackupBill where journalNum = ?";
                PayBackupBill blb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hqlbb, new Object[]{ddid});

                TEshopCusCom tsc = null;
                TEshopCusCom ts = null;
                if (blb != null && blb.getCashCompany() != null && !blb.getCashCompany().equals("")) {
                    tsc = (TEshopCusCom) beandao.getBeanByHqlAndParams(hql2, new String[]{blb.getCashCompany()});
                } else if (blb != null && blb.getSccid() != null && !blb.getSccid().equals("") && !blb.getSccid().equals(bd.getOaSccId())) {
                    tsc = (TEshopCusCom) beandao.getBeanByHqlAndParams(hql2, new String[]{blb.getSccid()});
                    ts = (TEshopCusCom) beandao.getBeanByHqlAndParams(hql2, new String[]{bd.getOaSccId()});
                } else {
                    tsc = (TEshopCusCom) beandao.getBeanByHqlAndParams(hql2, new String[]{bd.getOaSccId()});
                }

                bd.setFkDate(new Date());
                backList.add(bd);

                StatusEntity status = SaveStatus(cashierBills.getCashierBillsID(), cashierBills.getJournalNum(), "03", null);
                status.setXddate(cashierBills.getCashierDate());
                backList.add(status);

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
                    Object obj = beandao.getObjectBySqlAndParams(sql1, param.toArray());

                    totalprice = new BigDecimal(obj.toString());

                }
                String hqlcom = "from Company where companyID = ?";

                for (int a = 0; a < pcbs.size(); a++) {
                    PayCashierBill payCashierBill = (PayCashierBill) pcbs.get(a);
                    payCashierBill.setPayJournalNum(ddid);
                    payCashierBill.setTrade_no(tradeNo);
                    //订单
                    CashierBills dd = new CashierBills();
                    //订单里的产品的业务佣金和
                    BigDecimal bksum = BigDecimal.ZERO;

                    String hql = "from CashierBills d where d.journalNum=?";
                    dd = (CashierBills) beandao.getBeanByHqlAndParams(hql,
                            new String[]{payCashierBill.getOriJournalNum()});


                    if (dd == null) {
                        System.out.println("没有查询出订单,订单号:" + payCashierBill.getOriJournalNum());
                        return null;
                    } else if (!dd.getFkStatus().equals("01") && !dd.getFkStatus().equals("09")) {
                        System.out.println("该订单积分已付款,订单号：" + payCashierBill.getOriJournalNum());
                        return null;
                    }

                    dd.setFkStatus("00");
                    dd.setWfStatus4(wfStatus4);
                    String goodl = "from GoodsBills d where d.cashierBillsID=?";
                    List<BaseBean> ret = beandao.getListBeanByHqlAndParams(goodl,
                            new String[]{dd.getCashierBillsID()});

                    dd.setTrade_no(tradeNo);
                    String mes = "";
                    String goodname = "";
                    List<String> hqls = new ArrayList<String>();
                    List<Object[]> parmsList = new ArrayList<Object[]>();

                    String hqlpp = "from ProductPackaging t where t.ppID = ?";
                    ProductPackaging pps = null;

                    String companyId = "company201009046vxdyzy4wg0000000025";//平台公司id
                    Company cp = (Company) beandao
                            .getBeanByHqlAndParams(" from Company where companyID= ?", new Object[]{companyId});
                    String cpName = cp.getCompanyName();
                    String groupCompanySn = cp.getGroupCompanySn();

                    String jfppid = "p20170220ZVZR76B88M0000000023";//积分产品id
                    String goldPpID = "p20160105ZEBQRITQIZ0000000278";//金币产品id
                    String manageHql = "from ProductPackaging where ppID=?";
                    //积分产品信息
                    ProductPackaging manage = (ProductPackaging) beandao
                            .getBeanByHqlAndParams(manageHql, new Object[]{"07".equals(wfStatus4) ? goldPpID : jfppid});

                    String depotHql = "from DepotManage where "
                            + "companyID=? and depotName=? and depotState!=?";
                    //库房信息
                    DepotManage depot = (DepotManage) beandao
                            .getBeanByHqlAndParams(depotHql, new Object[]{companyId, "销售库", "01"});

                    BigDecimal pri = new BigDecimal("100");//积分/金币单价

                    //购买人信息
                    Staff staff = (Staff) beandao
                            .getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{tsc.getStaffid()});
                    String staname = staff.getStaffName();
                    BigDecimal mpb = new BigDecimal(morrt).multiply(pri);
                    WfjJifen gold = null;
                    BonusPoints pbp = null;
                    if ("07".equals(wfStatus4)) {
                        //个人金币购物减少
                        gold = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where sccid =  ?",
                                new Object[]{tsc.getSccId()});
                        if (gold == null) {
                            gold = new WfjJifen();
                            gold.setWfjJifenId(serverService.getServerID("BonusPoints"));
                            gold.setWfjJifenScore("0");
                            gold.setSccid(tsc.getSccId());
                        } else {
                            BigDecimal pbd = new BigDecimal(gold.getWfjJifenScore());
                            if (pbd.compareTo(mpb) < 0) {
                                return false;
                            }
                            gold.setWfjJifenScore(pbd.subtract(mpb).toString());
                        }
                        //公司获得购物的金币增加
                        String invHqlc = "from Inventory where companyID=? and productId=? and warehouseName = ?";
                        Inventory invc = (Inventory) this.beandao.getBeanByHqlAndParams(invHqlc, new Object[]{companyId, goldPpID, "销售库"});
                        BigDecimal in = new BigDecimal(invc.getInvenQuantity());
                        invc.setInvenQuantity((in.add(mpb)).toString());

                        invc.setSumPrice((new BigDecimal(invc.getInvenQuantity()).divide(pri)).toString());
                        backList.add(invc);
                        backList.add(gold);
                    } else {
                        //个人积分购物减少
                        pbp = (BonusPoints) beandao.getBeanByHqlAndParams(" from BonusPoints where sccid =  ?",
                                new Object[]{tsc.getSccId()});
                        if (pbp == null) {
                            pbp = new BonusPoints();
                            pbp.setBonusPointsId(serverService.getServerID("BonusPoints"));
                            pbp.setBonusPointScore("0");
                            pbp.setSccid(tsc.getSccId());
                        } else {
                            BigDecimal pbd = new BigDecimal(pbp.getBonusPointScore());
                            if (pbd.compareTo(mpb) < 0) {
                                return false;
                            }
                            pbp.setBonusPointScore(pbd.subtract(mpb).toString());
                        }
                        backList.add(pbp);
                        //公司获得购物的积分增加
                        String invHqlc = "from Inventory where companyID=? and productId=?";
                        Inventory invc = (Inventory) this.beandao.getBeanByHqlAndParams(invHqlc, new Object[]{companyId, jfppid});
                        BigDecimal in = new BigDecimal(invc.getInvenQuantity());
                        invc.setInvenQuantity((in.add(mpb)).toString());

                        invc.setSumPrice((new BigDecimal(invc.getInvenQuantity()).divide(pri)).toString());
                        backList.add(invc);

                    }
                    //查询规则
                    WfjGuize shareCode = (WfjGuize) beandao.getBeanByHqlAndParams("from WfjGuize where wfjGuizeName=?", new Object[]{"07".equals(wfStatus4) ? "金币购物" : "积分购物"});

                    //公司积分入库单
                    CashierBills gscb = new CashierBills();
                    gscb.setCashierBillsID(serverService.getServerID("cashierTally"));
                    //logger.error("公司入库单-------" + gscb.getCashierBillsID());
                    gscb.setCashierDate(new Date());
                    gscb.setJournalNum(serverService.getBillID(companyId));
                    gscb.setBillsType("07".equals(wfStatus4) ? "金币入库单" : "积分入库单");
                    gscb.setStatus("15");
                    gscb.setStaffID("cstaff20160325ZAUAJEU6JH6192643691");
                    gscb.setStaffName("系统生成");
                    gscb.setCompanyID(companyId);
                    gscb.setCompanyName(cpName);
                    gscb.setGroupCompanySn(groupCompanySn);
                    gscb.setInputid("cstaff20160325ZAUAJEU6JH6192643691");
                    gscb.setInputName("系统生成");
                    gscb.setStatusbill("04");
                    gscb.setPriceSub(dd.getPriceSub());
                    gscb.setjNumOrder(dd.getjNumOrder());
                    gscb.setContactUserID(tsc.getStaffid());
                    gscb.setCtUserName(staname);
                    gscb.setProjectName("07".equals(wfStatus4) ? "金币购物" : "积分购物");
                    gscb.setProID("07".equals(wfStatus4) ? "010" : "007");
                    gscb.setAppstyle("07".equals(wfStatus4) ? "08" : "14");//金币/积分分配
                    gscb.setFkStatus("00");
                    gscb.setWfStatus4(wfStatus4);
                    String conpamid = tsc.getCompanyId();
                    String conppname = tsc.getPseudoCompanyName();

                    if (tsc.getCompanyId() == null || tsc.getPseudoCompanyName() == null ||
                            tsc.getCompanyId().equals("") || tsc.getPseudoCompanyName().equals("")) {
                        StringBuilder cushql = new StringBuilder(
                                "SELECT t.companyid, t.pseudo_company_name,t.custype FROM T_ESHOP_CUSCOM t " +
                                        " where t.companyid is not null and t.pseudo_company_name is not null  and rownum = 1 " +
                                        " START WITH t.sccid = ? CONNECT BY PRIOR  t.suppersccid =t.sccid ");
                        List<BaseBean> objc = beandao.getListBeanBySqlAndParams(
                                cushql.toString(), new Object[]{tsc.getSccId()});
                        if (objc == null || objc.size() == 0) {
                            return false;
                        }
                        Object objcompany = objc.get(0);
                        Object[] objcc = (Object[]) objcompany;
                        conpamid = objcc[0].toString();
                        conppname = objcc[1].toString();

                    }
                    gscb.setCcompanyID(conpamid);
                    gscb.setCcompanyName(conppname);

                    backList.add(gscb);

                    //出库单 出库单(个人)
                    CashierBills grcb = new CashierBills();
                    grcb.setCashierBillsID(serverService.getServerID("cashierTally"));
                    //     logger.error("个人出库单-------" + grcb.getCashierBillsID());
                    grcb.setCashierDate(new Date());
                    grcb.setBillsType("07".equals(wfStatus4) ? "金币出库单" : "积分出库单");
                    grcb.setJournalNum(serverService.getBillID(companyId));
                    grcb.setCompanyID(conpamid);
                    grcb.setCompanyName(conppname);
                    grcb.setStaffID(tsc.getStaffid());
                    grcb.setStaffName(staname);
                    grcb.setjNumOrder(dd.getjNumOrder());
                    grcb.setStatus("16");
                    grcb.setAppstyle("07".equals(wfStatus4) ? "08" : "14");//积分分配
                    grcb.setStatusbill("04");
                    grcb.setPriceSub(dd.getPriceSub());
                    grcb.setProID("07".equals(wfStatus4) ? "010" : "007");
                    grcb.setInputName("系统生成");
                    grcb.setContactUserID("cstaff20160325ZAUAJEU6JH6192643691");
                    grcb.setCtUserName(staname);
                    grcb.setProjectName("07".equals(wfStatus4) ? "金币购物" : "积分购物");
                    grcb.setFkStatus("00");
                    grcb.setWfStatus4(wfStatus4);
                    backList.add(grcb);
                    //公司产品单据入库单
                    GoodsBills gscp = new GoodsBills();
                    gscp.setCashierBillsID(gscb.getCashierBillsID());
                    gscp.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                    gscp.setGoodsID("07".equals(wfStatus4) ? goldPpID : jfppid);
                    gscp.setGoodsNum(manage.getGoodsNum());
                    gscp.setGoodsName(manage.getGoodsName());
                    gscp.setStandard(manage.getStandard());
                    gscp.setGoodsVariableID(manage.getVariableID());
                    gscp.setWeight(manage.getWeight());
                    gscp.setPrice("0.01");
                    gscp.setQuantity(mpb + "");
                    gscp.setMoney(morrt + "");
                    gscp.setPayType("07".equals(wfStatus4) ? "08" : "14");
                    gscp.setKcStatus("15");
                    gscp.setGoodstatus("00");
                    gscp.setPpID(manage.getPpID());
                    backList.add(gscp);


                    //个人产品单据出出库单
                    GoodsBills grcp = new GoodsBills();
                    grcp = (GoodsBills) gscp.cloneGoodsBills();
                    grcp.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                    grcp.setCashierBillsID(grcb.getCashierBillsID());
                    grcp.setKcStatus("16");
                    backList.add(grcp);

                    BigDecimal ss = new BigDecimal(0);
                    Company comp = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom, new Object[]{dd.getCompanyID()});
                    String goodsTotal = "";
                    for (int i = 0; i < ret.size(); i++) {
                        GoodsBills gbs = (GoodsBills) ret.get(i);
                        goodsTotal += gbs.getGoodsName() + ",";
                        mes = gbs.getTypeID();
                        goodname = gbs.getGoodsName();
                        BigDecimal zs = BigDecimal.ZERO; //单个产品售价
                        BigDecimal e = BigDecimal.ZERO;//产品业务佣金
                        BigDecimal p = BigDecimal.ZERO;//实际价格
                        BigDecimal c = BigDecimal.ZERO;//实际价格
                        BigDecimal pay = BigDecimal.ZERO;//报名产品操作费

                        Object[] zpro_su = null;
                        Object zpro_setup = null;
                        Object[] psuObj = null;

                        //价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动
                        String priceType = (gbs.getPricetype() == null || gbs.getPricetype().equals("")) ? "0" : gbs.getPricetype();
                        String activityid = gbs.getActivityID();

                        if (priceType == null || (priceType.equals("0") && activityid == null)) {
                            //零售订单
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

                        if ("07".equals(wfStatus4)) {
                            WfjJifenDetail edetail = new WfjJifenDetail();
                            edetail.setJifenDetailId(serverService.getServerID("WfjJifenDetail"));
                            edetail.setJifenDetailScore(new BigDecimal(gbs.getMoney()).multiply(pri) + "");
                            edetail.setWfjGuizeId(shareCode.getWfjGuizeId());
                            edetail.setWfjJifenId(gold.getWfjJifenId());
                            edetail.setJifenDetailState(Integer.valueOf(0));
                            edetail.setJifenDetailName("金币购物-" + gbs.getGoodsName());
                            edetail.setJifenDetailDate(new Date());
                            edetail.setWfjGoodId(gbs.getGoodsBillsID());
                            edetail.setWfjCashId(grcb.getCashierBillsID());
                            backList.add(edetail);
                        } else {
                            //积分明细个人
                            BonusPointsDetail userbp = new BonusPointsDetail();
                            userbp.setBonusPointsDetailName("积分购物-" + gbs.getGoodsName());
                            userbp.setBonusPointsDetailId(serverService.getServerID("BonusPointsDetail"));
                            //  logger.error("个人积分明细单号-----" + userbp.getBonusPointsDetailId());
                            userbp.setBonusPointsDetailScore(new BigDecimal(gbs.getMoney()).multiply(pri) + "");
                            userbp.setBonusPointsDetailDate(new Date());
                            userbp.setBonusPointsId(pbp.getBonusPointsId());
                            userbp.setWfjCashId(grcb.getCashierBillsID());
                            userbp.setWfjGoodId(gbs.getGoodsBillsID());
                            userbp.setWfjGuizeId(shareCode.getWfjGuizeId());
                            backList.add(userbp);
                        }


                        //公司积分明细入库详情
                        stockInv sto = new stockInv();
                        sto.setStockinvID(serverService.getServerID("stockInv"));
                        // logger.error("公司积分明细单号-------" + sto.getStockinvID());
                        //  logger.error(sto.getStockinvID());
                        sto.setCompanyID(companyId);
                        sto.setGroupCompanySn(groupCompanySn);
                        sto.setGoodsID(manage.getGoodsID());
                        sto.setGoodsType(manage.getTradeID());
                        sto.setGoodsName(manage.getGoodsName());
                        sto.setInvenQuantity(new BigDecimal(gbs.getMoney()).multiply(pri) + "");
                        sto.setIntime(new Date());
                        sto.setType("00");
                        if("智能货柜".equals(dd.getGoodsName())){
                            sto.setWarehouse(gbs.getDepotID());
                            sto.setWarehouseName(gbs.getDepotName());
                        }else{
                            sto.setWarehouse(depot.getDepotID());
                            sto.setWarehouseName(depot.getDepotName());
                        }
                        sto.setGoodsBillsId(gscp.getGoodsBillsID());
                        backList.add(sto);
                        if (ts != null) {
                            tsc = ts;
                        }

                        if (gbs.getTypeID().equals("公司会员") || gbs.getTypeID().equals("个人会员")) {
                            //会员升级
                            SelfCart selfCart = (SelfCart) beandao.getBeanByHqlAndParams("from SelfCart where journalNum = ?", new Object[]{ddid});
                            if (selfCart != null && selfCart.getCardNum() != null) {
                                GiftCards giftCards = (GiftCards) beandao.getBeanByHqlAndParams("from GiftCards where cardNumber = ?", new Object[]{selfCart.getCardNum()});
                                TEshopCusCom tscbycard = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where staffid = ?", new Object[]{giftCards.getStaffID()});
                                backList.addAll(this.upgradeServece(tscbycard, gbs.getBoxStandard()));
                            } else {


                                String hqlcxp = "from CashierBills c where  c.cashierBillsID  = (select n.cashierBillsID from PromotionAssociation n where n.ptcashierBillsID = ?)";
                                CashierBills cb = (CashierBills)beandao.getBeanByHqlAndParams(hqlcxp,new Object[]{dd.getCashierBillsID()});
                                if(cb!=null){
                                    TEshopCusCom ztc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where staffid = ?", new Object[]{cb.getContactUserID()});
                                    backList.addAll(this.upgradeServece(ztc, gbs.getBoxStandard()));
                                }else{
                                    backList.addAll(this.upgradeServece(tsc, gbs.getBoxStandard()));
                                }
                            }
                        } else if (gbs.getTypeID().indexOf("金币计时") != -1) {
                            makeAppointmentService.setBookState(ddid);
                        } else if (gbs.getTypeID().indexOf("包月") != -1 || gbs.getTypeID().indexOf("包年") != -1 || gbs.getTypeID().indexOf("包天") != -1) {
                            //xgb新增修改车辆计费信息
                            cmService.updateTiming(dd.getJournalNum(), gbs.getTypeID());
                            logger.error("订单凭证=" + dd.getJournalNum() + "---------类型=" + gbs.getTypeID());
                        } else if ((gbs.getTypeID().equals("省级城市") || gbs.getTypeID().equals("村级新城")
                                || gbs.getTypeID().equals("县级城市") || gbs.getTypeID().equals("乡镇城市")) || gbs.getTypeID().equals("平台分期")) {
                            StringBuilder strq = new StringBuilder();
                            strq.append(" select cco.compnay_id,con.CompanyName from DT_ccom_com cco,dtContactCompany con");
                            strq.append(" where  cco.ccompany_Id =con.ccompanyId and con.CompanyName =?");
                            List list = beandao.getListBeanBySqlAndParams(strq.toString(), new Object[]{dd.getPlatfromConpanyName()});

                            Object[] str = (Object[]) list.get(0);
                            thcuscom.setSccId(serverService.getServerID("sccId"));
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

                        }
//                        else if (bo && (tsc.getCusType().equals("7") || tsc.getCusType().equals("6")) && (totalprice.compareTo(yb) == 1 || totalprice.compareTo(yb) == 0)) {
//                            backList.addAll(this.upgradeServece(tsc, "5"));
//                            bo = false;
//
//                        }
                        else if (bo && tsc.getCusType().equals("7")) {
                            BigDecimal v = BigDecimal.ZERO;

                            if (zs.compareTo(p) <= 0 && e.multiply(new BigDecimal(gbs.getQuantity())).compareTo(new BigDecimal(3)) == 1) {
                                ProductPackaging ppk = new ProductPackaging();
                                ppk = (ProductPackaging) beandao.getBeanByHqlAndParams(
                                        "from ProductPackaging t where t.type = ? and t.model=?", new String[]{"个人会员", "6"});


                                gbs.setMoney(gbs.getMoney() + "-3");
                                backList.add(gbs);

                                String hqlinvt = "from DepotManage where companyID = ? and depotName= ?";

                                DepotManage wh = (DepotManage) beandao.getBeanByHqlAndParams(hqlinvt, new Object[]{ppk.getCompanyID(), "销售库"});

                                GoodsBills g = new GoodsBills();
                                g.setCashierBillsID(dd.getCashierBillsID());
                                g.setGoodsBillsID(serverService.getServerID("GoodsBills"));
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
                                gg.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                                gg.setCashierBillsID(gbs.getCashierBillsID());
                                backList.add(gg);

                                //vip客户代理分钱数据
                                backList.addAll(this.dailiFen2(gg.getGoodsBillsID(), "p201602014ECNY2VNSJ0000012165", dd.getCashierBillsID(), dd.getJournalNum(), tsc.getSccId(), ppk.getTradeName(), ppk.getCompanyID(), "1", "0", hy_pro_setupObj, null));


                                ppk.setMonthSales(ppk.getMonthSales() + 1);
                                backList.add(ppk);

                                backList.addAll(this.upgradeServece(tsc, "6"));
                                bo = false;
                            }
                        }
                        if (dd.getPlatfromid() != null && !dd.getPlatfromid().equals("")) {
                            //购买省县村的判断购买
                            //生成设备安装的绑定代理商
                            TEshopCuscomSub tshop = new TEshopCuscomSub();
                            tshop.setSccskey(serverService.getServerID("sccSkey"));
                            tshop.setSccsid(serverService.getServerID("sccsid"));
                            tshop.setAccount(tsc.getAccount());
                            tshop.setSccid(bd.getOaSccId());
                            tshop.setTyepPpid(dd.getPlatfromid());
                            tshop.setSjdate(new Date());

                            if (dd.getPlatfromid().equals("p20170220ZVZR76B88M0000000018") || dd.getPlatfromid().equals("p20170220ZVZR76B88M0000000019")
                                    || dd.getPlatfromid().equals("p20170220ZVZR76B88M0000000020")) {
                                //当买资格的时候，此处的Platfromid值是设置佣金的产品ppid 和 名字
                                tshop.setAreappid(gbs.getAreappid());
                            }

                            backList.add(tshop);
                        }
                        //查询产品表增加销量
                        pps = (ProductPackaging) beandao.getBeanByHqlAndParams(hqlpp, new Object[]{gbs.getPpID()});
                        BigDecimal quan = new BigDecimal(gbs.getQuantity());
                        BigDecimal sale = new BigDecimal(pps.getMonthSales());
                        pps.setMonthSales(sale.add(quan).intValue());

                        //零售价
                        BigDecimal prbd = new BigDecimal(gbs.getPrice());
                        //成本价
                        BigDecimal cdb = new BigDecimal(gbs.getCostmoney());

                        if (prbd.compareTo(cdb) > 0) {
                            //分代理佣金
                            backList.addAll(this.dailiFen2(gbs.getGoodsBillsID(), gbs.getPpID(), dd.getCashierBillsID(), dd.getJournalNum(), tsc.getSccId(), pps.getTradeName(), pps.getCompanyID(), gbs.getQuantity(), priceType, psuObj, dd.getProID()));
                        }

                        backList.add(pps);


                        //主产品付款成功后，促销品生成订单ljc。
                        StringBuffer phql = new StringBuffer();
                        phql.append("select pm from PromotionAssociation pm,ProductPackaging pp,GoodsBills gb");
                        phql.append(" where pp.ppID=gb.ppID and gb.cashierBillsID=pm.cashierBillsID and pp.ppID=?");
                        phql.append(" and pm.cashierBillsID=? and pm.ptcashierBillsID is null");
                        List<BaseBean> plist = beandao.getListBeanByHqlAndParams(phql.toString(), new Object[]{pps.getPpID(), dd.getCashierBillsID()});
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
                            backList.addAll(this.promotionalOrderServece(pps.getPpID(), ptppid, dd, companyid, ptstandard));
                        }

                    }
                    backList.add(comp);

                    backList.add(dd);

                    backList.add(payCashierBill);

                    //分业务佣金
                    backList.addAll(this.getddService(tsc, dd, bksum));

                    BigDecimal p = new BigDecimal(dd.getPriceSub());
                    if (p.compareTo(ss) > 0) {
                        backList.addAll(this.sdfd(dd.getCompanyID(), tsc.getSccId(), dd.getCashierBillsID(), dd.getJournalNum(), p.subtract(ss).setScale(2, BigDecimal.ROUND_DOWN)));
                    }

                    HttpSession session = ServletActionContext.getRequest().getSession();
                    SessionWrap sw = SessionWrap.getInstance();
                    sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tsc);
                    if (mes != null && (mes.equals("公司会员") || mes.equals("个人会员"))) {
                        //发送短信提醒购买成功
                        pushMessage(tsc, ((GoodsBills) ret.get(0)).getGoodsName());
                    }

                    String hqql = " from TEshopCusCom where companyId = ?";
                    TEshopCusCom com = (TEshopCusCom) beandao.getObjectByHqlAndParams(hqql, new Object[]{pps.getCompanyID()});
                    //查询当前公司是否为餐饮行业

                    ContactCompany co = (ContactCompany) beandao.getBeanByHqlAndParams(" from ContactCompany m where m.ccompanyID = (select c.ccompanyId from CcomCom c where c.comanyId = ?) ", new Object[]{dd.getCompanyID()});
                    if (co.getIndustryType() != null && co.getIndustryType().contains("餐饮")) {
                        getCaiYinXiangQing(dd, dd.getMealNum(), ret, dd.getCashierDate(), bd, wfStatus4, com, tsc);
                    } else {
                        if (!"00".equals(dd.getNopush())) {
                            //发送短信提醒发货
                            String content = "恭喜！您有新的数字地球5L5C订单" + dd.getJournalNum() + ",商品为:" + goodsTotal + "收货人姓名:" + bd.getReceivename() + ";收货人手机号:" + bd.getReceivetel() + ";收货人地址:" + bd.getReceiveaddress() + ",请及时处理！";

                            zfMessage(com, content, "支付", "sellerindent", "defray");
                        }
                    }

                    if (mes.equals("公司会员")) {
                        if (tsc.getCompanyId() == null) {
                            Company company = new Company();// 购买使用对象
                            CDetail cdl = new CDetail();// 公司详细信息
                            cdl.setCompanyAddress("1");
                            cdl.setRegistrationNumber("1");
                            cdl.setCompanyEmail("1");
                            cdl.setCompanyManager("1");
                            cdl.setCompanyPhone("1");
                            company.setCompanyIdentifier(dd.getPlatfromConpanyName());
                            company.setCompanyName(dd.getPlatfromConpanyName());
                            company.setIndustryType(dd.getPlatfromAccount());

                            if (goodname.equals("大型企业平台管理商城系统")) {
                                company.setCcomtype("0");
                            } else if (goodname.equals("中型企业平台管理商城系统")) {
                                company.setCcomtype("1");
                            } else if (goodname.equals("小型企业平台管理商城系统")) {
                                company.setCcomtype("2");
                            } else if (goodname.equals("微型企业平台管理商城系统")) {
                                company.setCcomtype("3");
                            } else if (goodname.equals("小微型企业平台管理商城系统")) {
                                company.setCcomtype("4");
                            } else if (goodname.equals("供应商企业平台管理商城系统")) {
                                company.setCcomtype("5");
                            }else if (goodname.equals("0元加入")) {
                                company.setCcomtype("6");
                            }

                            wfjserv.registerCompanyInfo("", tsc, company, cdl);

                        }

                    }


                    if("智能货柜".equals(dd.getGoodsName())){
                      setUnPayRecord(dd.getJournalNum());
                    }
                }
                beandao.executeHqlsByParmsList(backList, null, null);
            } else {
                b = false;
            }
        } catch (BeansException e) {
            e.printStackTrace();
            b = false;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            b = false;
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }


    /**
     *
     * 金币+积分够扣
     * @param tradeNo
     * @param ddid
     * @param morrt
     * @param wfStatus4
     * @param sccId
     * @param hgcode
     * @return
     */
    public synchronized Boolean coinAndScorePay(String tradeNo, String ddid, String morrt, String wfStatus4,String sccId,String hgcode) {
        Boolean b = true;
        String cashid1 = "";
        String cashid2 = "";

        StringBuffer aaa = null;
        try {
            List<BaseBean> backList = new ArrayList<BaseBean>();

            List<BaseBean> pcbs = new ArrayList<BaseBean>();

            String payhql = "from PayCashierBill where payJournalNum=?";
            pcbs = beandao.getListBeanByHqlAndParams(payhql,
                    new String[]{ddid});

            //订单
            String hqlh = " from CashierBills where journalNum = ?";
            CashierBills cashierBills = (CashierBills) beandao.getBeanByHqlAndParams(hqlh, new Object[]{((PayCashierBill) pcbs.get(0)).getOriJournalNum()});

            boolean bo = true;


            //判断订单是不是未付款状态
            if (cashierBills.getFkStatus().equals("01") || cashierBills.getFkStatus().equals("09")) {

                DtOrderBillAdd bd = (DtOrderBillAdd) beandao
                        .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillJounum=?", new Object[]{cashierBills.getJournalNum()});
                // 查询当前订单用户

                String hqlbb = "from PayBackupBill where journalNum = ?";
                PayBackupBill blb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hqlbb, new Object[]{ddid});

                TEshopCusCom tsc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom d where d.sccId=?", new String[]{sccId});
                TEshopCusCom ts = null;




                bd.setFkDate(new Date());
                backList.add(bd);

                StatusEntity status = SaveStatus(cashierBills.getCashierBillsID(), cashierBills.getJournalNum(), "03", null);
                status.setXddate(cashierBills.getCashierDate());
                backList.add(status);

                String hqlcom = "from Company where companyID = ?";

                    PayCashierBill payCashierBill = (PayCashierBill) pcbs.get(0);
                    payCashierBill.setPayJournalNum(ddid);
                    payCashierBill.setTrade_no(tradeNo);

                    //订单里的产品的业务佣金和
                    BigDecimal bksum = BigDecimal.ZERO;



                    if (cashierBills == null) {
                        System.out.println("没有查询出订单,订单号:" + payCashierBill.getOriJournalNum());
                        return null;
                    } else if (!cashierBills.getFkStatus().equals("01") && !cashierBills.getFkStatus().equals("09")) {
                        System.out.println("该订单积分已付款,订单号：" + payCashierBill.getOriJournalNum());
                        return null;
                    }

                   cashierBills.setFkStatus("00");
                    cashierBills.setWfStatus4(wfStatus4);
//                    cashierBills.setGoodsCoding(hgcode);
//                    cashierBills.setGoodsName("智能货柜");
                    String goodl = "from GoodsBills d where d.cashierBillsID=?";
                    List<BaseBean> ret = beandao.getListBeanByHqlAndParams(goodl,
                            new String[]{cashierBills.getCashierBillsID()});

                    cashierBills.setTrade_no(tradeNo);

                    List<String> hqls = new ArrayList<String>();
                    List<Object[]> parmsList = new ArrayList<Object[]>();

                    String hqlpp = "from ProductPackaging t where t.ppID = ?";
                    ProductPackaging pps = null;

                    String companyId = "company201009046vxdyzy4wg0000000025";//平台公司id
                    Company cp = (Company) beandao
                            .getBeanByHqlAndParams(" from Company where companyID= ?", new Object[]{companyId});
                    String cpName = cp.getCompanyName();
                    String groupCompanySn = cp.getGroupCompanySn();

                    String jfppid = "p20170220ZVZR76B88M0000000023";//积分产品id
                    String goldPpID = "p20160105ZEBQRITQIZ0000000278";//金币产品id
                    String manageHql = "from ProductPackaging where ppID=?";
                    //积分产品信息
                    ProductPackaging manage = (ProductPackaging) beandao
                            .getBeanByHqlAndParams(manageHql, new Object[]{"07".equals(wfStatus4) ? goldPpID : jfppid});

//                    String depotHql = "from DepotManage where depotPID=? and "
//                            + "companyID=? and depotName=? and depotState=?";
//                    //库房信息
//                    DepotManage depot = (DepotManage) beandao
//                            .getBeanByHqlAndParams(depotHql, new Object[]{"001", companyId, "销售库", "00"});


                String depotHql = "from DepotManage where "
                        + "companyID=? and depotName=? and depotState!=?";
                //库房信息
                DepotManage depot = (DepotManage) beandao
                        .getBeanByHqlAndParams(depotHql, new Object[]{companyId, "销售库", "01"});

                    BigDecimal pri = new BigDecimal("100");//积分/金币单价

                    //购买人信息
                    Staff staff = (Staff) beandao
                            .getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{tsc.getStaffid()});
                    String staname = staff.getStaffName();
                    BigDecimal mpb = new BigDecimal(morrt).multiply(pri);




                    //个人积分购物减少
                    BonusPoints pbp = (BonusPoints) beandao.getBeanByHqlAndParams(" from BonusPoints where sccid =  ?",
                            new Object[]{tsc.getSccId()});
                    BigDecimal pbd = new BigDecimal(pbp.getBonusPointScore());
                    pbp.setBonusPointScore("0");//把积分全部扣掉
                    String priceSubf = pbd.divide(pri)+"";
                    backList.add(pbp);

                    //公司获得购物的积分增加
                    String invHqlc = "from Inventory where companyID=? and productId=?";
                    Inventory invc = (Inventory) this.beandao.getBeanByHqlAndParams(invHqlc, new Object[]{companyId, jfppid});
                    BigDecimal in = new BigDecimal(invc.getInvenQuantity());
                    invc.setInvenQuantity((in.add(pbd)).toString());//公司增加

                    invc.setSumPrice((new BigDecimal(invc.getInvenQuantity()).divide(pri)).toString());
                    backList.add(invc);





                      //个人金币购物减少
                        WfjJifen gold  = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where sccid =  ?",
                                new Object[]{tsc.getSccId()});

                       BigDecimal pbb = new BigDecimal(gold.getWfjJifenScore());

                        gold.setWfjJifenScore(pbb.subtract(mpb.subtract(pbd)).toString());

                       String priceSubb = mpb.subtract(pbd).divide(pri)+"";
                        //公司获得购物的金币增加
                        String invHqlc1 = "from Inventory where companyID=? and productId=? and warehouseName = ?";
                        Inventory invc1 = (Inventory) this.beandao.getBeanByHqlAndParams(invHqlc1, new Object[]{companyId, goldPpID, "销售库"});
                        BigDecimal in1 = new BigDecimal(invc1.getInvenQuantity());
                        invc1.setInvenQuantity((in1.add(mpb.subtract(pbd))).toString());

                        invc1.setSumPrice((new BigDecimal(invc1.getInvenQuantity()).divide(pri)).toString());
                        backList.add(invc1);
                        backList.add(gold);



                    //查询规则
                    WfjGuize shareCode1 = (WfjGuize) beandao.getBeanByHqlAndParams("from WfjGuize where wfjGuizeName=?", new Object[]{"积分购物"});

                    WfjGuize shareCode2 = (WfjGuize) beandao.getBeanByHqlAndParams("from WfjGuize where wfjGuizeName=?", new Object[]{"金币购物"});

                    for(int i = 0;i<2;i++) {
                        //公司的金币和积分入库单
                        CashierBills gscb = new CashierBills();
                        gscb.setCashierBillsID(serverService.getServerID("cashierTally"));
                        //logger.error("公司入库单-------" + gscb.getCashierBillsID());
                        gscb.setCashierDate(new Date());
                        gscb.setJournalNum(serverService.getBillID(companyId));
                        if(i==0){
                            gscb.setBillsType("积分入库单");
                            gscb.setProjectName("积分购物");
                            gscb.setProID("007");
                            gscb.setAppstyle("14");//金币/积分分配
                            gscb.setPriceSub(priceSubf);

                        }else{
                            gscb.setBillsType("金币入库单");
                            gscb.setProjectName("金币购物");
                            gscb.setProID("010");
                            gscb.setAppstyle("08");//金币/积分分配
                            gscb.setPriceSub(priceSubb);

                        }

                        gscb.setStatus("15");
                        gscb.setStaffID("cstaff20160325ZAUAJEU6JH6192643691");
                        gscb.setStaffName("系统生成");
                        gscb.setCompanyID(companyId);
                        gscb.setCompanyName(cpName);
                        gscb.setGroupCompanySn(groupCompanySn);
                        gscb.setInputid("cstaff20160325ZAUAJEU6JH6192643691");
                        gscb.setInputName("系统生成");
                        gscb.setStatusbill("04");

                        gscb.setjNumOrder(cashierBills.getjNumOrder());
                        gscb.setContactUserID(tsc.getStaffid());
                        gscb.setCtUserName(staname);



                        gscb.setFkStatus("00");
                        gscb.setWfStatus4(wfStatus4);
                        String conpamid = tsc.getCompanyId();
                        String conppname = tsc.getPseudoCompanyName();

                        if (tsc.getCompanyId() == null || tsc.getPseudoCompanyName() == null ||
                                tsc.getCompanyId().equals("") || tsc.getPseudoCompanyName().equals("")) {
                            StringBuilder cushql = new StringBuilder(
                                    "SELECT t.companyid, t.pseudo_company_name,t.custype FROM T_ESHOP_CUSCOM t " +
                                            " where t.companyid is not null and t.pseudo_company_name is not null  and rownum = 1 " +
                                            " START WITH t.sccid = ? CONNECT BY PRIOR  t.suppersccid =t.sccid ");
                            List<BaseBean> objc = beandao.getListBeanBySqlAndParams(
                                    cushql.toString(), new Object[]{tsc.getSccId()});

                            Object objcompany = objc.get(0);
                            Object[] objcc = (Object[]) objcompany;
                            conpamid = objcc[0].toString();
                            conppname = objcc[1].toString();

                        }
                        gscb.setCcompanyID(conpamid);
                        gscb.setCcompanyName(conppname);

                        backList.add(gscb);

                        //公司产品单据入库单
                        GoodsBills gscp = new GoodsBills();
                        gscp.setCashierBillsID(gscb.getCashierBillsID());
                        gscp.setGoodsBillsID(serverService.getServerID("GoodsBills"));

                        if(i==0){
                            gscp.setGoodsID(jfppid);
                            gscp.setPayType("14");
                            gscp.setQuantity(new BigDecimal(priceSubf).multiply(pri) + "");
                            gscp.setMoney(priceSubf + "");
                        }else{
                            gscp.setGoodsID(goldPpID);
                            gscp.setPayType("08");
                            gscp.setQuantity(new BigDecimal(priceSubb).multiply(pri) + "");
                            gscp.setMoney(priceSubb + "");
                        }

                        gscp.setGoodsNum(manage.getGoodsNum());
                        gscp.setGoodsName(manage.getGoodsName());
                        gscp.setStandard(manage.getStandard());
                        gscp.setGoodsVariableID(manage.getVariableID());
                        gscp.setWeight(manage.getWeight());
                        gscp.setPrice("0.01");
                        gscp.setKcStatus("15");
                        gscp.setGoodstatus("00");
                        gscp.setPpID(manage.getPpID());
                        backList.add(gscp);



                        //公司积分明细入库详情
                        stockInv sto = new stockInv();
                        sto.setStockinvID(serverService.getServerID("stockInv"));
                        // logger.error("公司积分明细单号-------" + sto.getStockinvID());
                        //  logger.error(sto.getStockinvID());
                        sto.setCompanyID(companyId);
                        sto.setGroupCompanySn(groupCompanySn);
                        sto.setGoodsID(manage.getGoodsID());
                        sto.setGoodsType(manage.getTradeID());
                        sto.setGoodsName(manage.getGoodsName());
                        if(i==0) {
                            sto.setInvenQuantity(new BigDecimal(priceSubf).multiply(pri) + "");
                        }else{
                            sto.setInvenQuantity(new BigDecimal(priceSubb).multiply(pri) + "");

                        }
                        sto.setIntime(new Date());
                        sto.setType("00");
                        sto.setWarehouse(depot.getDepotID());
                        sto.setWarehouseName(depot.getDepotName());
                        sto.setGoodsBillsId("");//gscp.getGoodsBillsID()
                        backList.add(sto);

                        //出库单 出库单(个人)
                        CashierBills grcb = new CashierBills();
                        grcb.setCashierBillsID(serverService.getServerID("cashierTally"));
                        //     logger.error("个人出库单-------" + grcb.getCashierBillsID());
                        grcb.setCashierDate(new Date());


                        if(i==0){
                            grcb.setBillsType("积分出库单");
                            grcb.setAppstyle("14");//积分分配
                            grcb.setProID("007");
                            grcb.setProjectName("积分购物");
                            grcb.setPriceSub(priceSubf);
                            cashid1 = grcb.getCashierBillsID();
                        }else{
                            grcb.setBillsType("金币出库单");
                            grcb.setAppstyle("08");//积分分配
                            grcb.setProID("010");
                            grcb.setProjectName("金币购物");
                            grcb.setPriceSub(priceSubb);
                            cashid1 = grcb.getCashierBillsID();
                        }



                        grcb.setJournalNum(serverService.getBillID(companyId));
                        grcb.setCompanyID(conpamid);
                        grcb.setCompanyName(conppname);
                        grcb.setStaffID(tsc.getStaffid());
                        grcb.setStaffName(staname);
                        grcb.setjNumOrder(cashierBills.getjNumOrder());
                        grcb.setStatus("16");

                        grcb.setStatusbill("04");


                        grcb.setInputName("系统生成");
                        grcb.setContactUserID("cstaff20160325ZAUAJEU6JH6192643691");
                        grcb.setCtUserName(staname);

                        grcb.setFkStatus("00");
                        grcb.setWfStatus4(wfStatus4);
                        backList.add(grcb);


                        //个人产品单据出出库单
                        GoodsBills grcp = new GoodsBills();
                        grcp = (GoodsBills) gscp.cloneGoodsBills();
                        grcp.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                        grcp.setCashierBillsID(grcb.getCashierBillsID());
                        grcp.setKcStatus("16");
                        backList.add(grcp);




                        if(i==0) {
                            //积分明细个人
                            BonusPointsDetail userbp = new BonusPointsDetail();
                            userbp.setBonusPointsDetailName("积分购物");
                            userbp.setBonusPointsDetailId(serverService.getServerID("BonusPointsDetail"));
                            //  logger.error("个人积分明细单号-----" + userbp.getBonusPointsDetailId());
                            userbp.setBonusPointsDetailScore(new BigDecimal(priceSubf).multiply(pri) + "");//金额问题，如果两种支付方式，这个每个商品的明细怎么分配时积分的还是金币的
                            userbp.setBonusPointsDetailDate(new Date());
                            userbp.setBonusPointsId(pbp.getBonusPointsId());
                            userbp.setWfjCashId(cashid1);
                            userbp.setWfjOrderId(cashierBills.getCashierBillsID());
                            userbp.setWfjGuizeId(shareCode1.getWfjGuizeId());
                            backList.add(userbp);
                        }else {

                            WfjJifenDetail edetail = new WfjJifenDetail();
                            edetail.setJifenDetailId(serverService.getServerID("WfjJifenDetail"));
                            edetail.setJifenDetailScore(new BigDecimal(priceSubb).multiply(pri) + "");
                            edetail.setWfjGuizeId(shareCode2.getWfjGuizeId());
                            edetail.setWfjJifenId(gold.getWfjJifenId());
                            edetail.setJifenDetailState(Integer.valueOf(0));
                            edetail.setJifenDetailName("金币购物");
                            edetail.setJifenDetailDate(new Date());
                            edetail.setWfjOrderId(cashierBills.getCashierBillsID());
                            edetail.setWfjCashId(cashid2);
                            backList.add(edetail);
                        }
                    }


                    BigDecimal ss = new BigDecimal(0);
                    Company comp = (Company) baseBeanService.getBeanByHqlAndParams(hqlcom, new Object[]{cashierBills.getCompanyID()});
                    for (int i = 0; i < ret.size(); i++) {
                        GoodsBills gbs = (GoodsBills) ret.get(i);
                        BigDecimal zs = BigDecimal.ZERO; //单个产品售价
                        BigDecimal e = BigDecimal.ZERO;//产品业务佣金
                        BigDecimal p = BigDecimal.ZERO;//实际价格
                        BigDecimal c = BigDecimal.ZERO;//实际价格
                        BigDecimal pay = BigDecimal.ZERO;//报名产品操作费

                        Object[] zpro_su = null;
                        Object zpro_setup = null;
                        Object[] psuObj = null;

                        //价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动
                        String priceType = (gbs.getPricetype() == null || gbs.getPricetype().equals("")) ? "0" : gbs.getPricetype();


                            //零售订单
                            zpro_setup = baseBeanService.getObjectBySqlAndParams(VOtool.getsql(1, 1, 1).toString(), new Object[]{gbs.getPpID(), gbs.getCompanyID()});

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



                                e = new BigDecimal(zpro_su[2].toString()); //单产品业务佣金

                                c = e.multiply(new BigDecimal(gbs.getQuantity())); //产品总业务佣金

                                bksum = bksum.add(c);  //产品业务总佣金
                            }
                        }



                        if (ts != null) {
                            tsc = ts;
                        }


                        //查询产品表增加销量
                        pps = (ProductPackaging) beandao.getBeanByHqlAndParams(hqlpp, new Object[]{gbs.getPpID()});
                        BigDecimal quan = new BigDecimal(gbs.getQuantity());
                        BigDecimal sale = new BigDecimal(pps.getMonthSales());
                        pps.setMonthSales(sale.add(quan).intValue());

                        //零售价
                        BigDecimal prbd = new BigDecimal(gbs.getPrice());
                        //成本价
                        BigDecimal cdb = new BigDecimal(gbs.getCostmoney());

                        if (prbd.compareTo(cdb) > 0) {
                            //分代理佣金
                            backList.addAll(this.dailiFen2(gbs.getGoodsBillsID(), gbs.getPpID(), cashierBills.getCashierBillsID(), cashierBills.getJournalNum(), tsc.getSccId(), pps.getTradeName(), pps.getCompanyID(), gbs.getQuantity(), priceType, psuObj, cashierBills.getProID()));
                        }

                        backList.add(pps);

                    }
                    backList.add(comp);

                    backList.add(cashierBills);

                    backList.add(payCashierBill);

                    //分业务佣金
                    backList.addAll(this.getddService(tsc, cashierBills, bksum));

                    BigDecimal p = new BigDecimal(cashierBills.getPriceSub());
                    if (p.compareTo(ss) > 0) {
                        backList.addAll(this.sdfd(cashierBills.getCompanyID(), tsc.getSccId(), cashierBills.getCashierBillsID(), cashierBills.getJournalNum(), p.subtract(ss).setScale(2, BigDecimal.ROUND_DOWN)));
                    }



                beandao.executeHqlsByParmsList(backList, null, null);
            } else {
                b = false;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return b;
    }
    /**
     *
     * 混合支付
     * @param ddid
     * @param morrt
     * @param wfStatus4 09 金币积分和其他付款混合 10 积分+其他 11 金币+其他
     * @return
     */
    public synchronized String mixPay(String ddid, String morrt, String wfStatus4,String sccId,String hgcode) {

        String remainMoney  = "";

        try {
            //订单
            String hqlh = " from CashierBills where journalNum = ?";
            CashierBills cashierBills = (CashierBills) beandao.getBeanByHqlAndParams(hqlh, new Object[]{ddid});


            if (cashierBills == null) {
                System.out.println("没有查询出订单,订单号:" +ddid);
                return null;
            } else if (!cashierBills.getFkStatus().equals("01")) {
                System.out.println("该订单积分已付款,订单号：" + ddid);
                return null;
            }
            List<BaseBean> backList = new ArrayList<BaseBean>();



            // 查询当前订单用户
            TEshopCusCom tsc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom d where d.sccId=?", new String[]{sccId});


            cashierBills.setWfStatus4(wfStatus4);
//            cashierBills.setFkStatus("01");//付款状态想想
//            cashierBills.setGoodsCoding(hgcode);
//            cashierBills.setGoodsName("智能货柜");
            String goodl = "from GoodsBills d where d.cashierBillsID=?";
            List<BaseBean> ret = beandao.getListBeanByHqlAndParams(goodl,
                    new String[]{cashierBills.getCashierBillsID()});



            String companyId = "company201009046vxdyzy4wg0000000025";//平台公司id
            Company cp = (Company) beandao
                    .getBeanByHqlAndParams(" from Company where companyID= ?", new Object[]{companyId});

            String cpName = cp.getCompanyName();
            String groupCompanySn = cp.getGroupCompanySn();

            String jfppid = "p20170220ZVZR76B88M0000000023";//积分产品id
            String goldPpID = "p20160105ZEBQRITQIZ0000000278";//金币产品id
            String manageHql = "from ProductPackaging where ppID=?";
                    //积分产品信息
                    ProductPackaging manage = (ProductPackaging) beandao
                            .getBeanByHqlAndParams(manageHql, new Object[]{"11".equals(wfStatus4) ? goldPpID : jfppid});




            String depotHql = "from DepotManage where "
                    + "companyID=? and depotName=? and depotState!=?";
            //库房信息
            DepotManage depot = (DepotManage) beandao
                    .getBeanByHqlAndParams(depotHql, new Object[]{companyId, "销售库", "01"});
                    //购买人信息
            Staff staff = (Staff) beandao
                    .getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{tsc.getStaffid()});
            String staname = staff.getStaffName();
            WfjJifen gold = null;
            BonusPoints pbp = null;
            String priceSubf = "";
            String priceSubb = "";

            BigDecimal pbd = null;
            BigDecimal pbb = null;
            BigDecimal pri = new BigDecimal("100");//积分/金币单价

            if ("10".equals(wfStatus4)||"09".equals(wfStatus4)) {
                //个人积分购物减少
                pbp = (BonusPoints) beandao.getBeanByHqlAndParams(" from BonusPoints where sccid =  ?",
                        new Object[]{tsc.getSccId()});
                 pbd = new BigDecimal(pbp.getBonusPointScore());
                pbp.setBonusPointScore("0");

                backList.add(pbp);
                //公司获得购物的积分增加
                String invHqlc = "from Inventory where companyID=? and productId=?";
                Inventory invc = (Inventory) this.beandao.getBeanByHqlAndParams(invHqlc, new Object[]{companyId, jfppid});
                BigDecimal in = new BigDecimal(invc.getInvenQuantity());
                invc.setInvenQuantity((in.add(pbd.multiply(pri))).toString());

                invc.setSumPrice((new BigDecimal(invc.getInvenQuantity()).divide(pri)).toString());
                priceSubf = pbd.divide(pri).setScale(2, RoundingMode.HALF_UP)+"";
                backList.add(invc);

            }
            if ("11".equals(wfStatus4)||"09".equals(wfStatus4)) {
                        //个人金币购物减少
                        gold = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where sccid =  ?",
                                new Object[]{tsc.getSccId()});
                         pbb = new BigDecimal(gold.getWfjJifenScore());


                         gold.setWfjJifenScore("0");//全部扣除
                        //公司获得购物的金币增加
                        String invHqlc = "from Inventory where companyID=? and productId=? and warehouseName = ?";
                        Inventory invc = (Inventory) this.beandao.getBeanByHqlAndParams(invHqlc, new Object[]{companyId, goldPpID, "销售库"});
                        BigDecimal in = new BigDecimal(invc.getInvenQuantity());
                invc.setInvenQuantity((in.add(pbb.multiply(pri))).toString());
                priceSubb = pbb.divide(pri).setScale(2, RoundingMode.HALF_UP)+"";

                    invc.setSumPrice((new BigDecimal(invc.getInvenQuantity()).divide(pri)).toString());

                        backList.add(invc);
                        backList.add(gold);

                }


                   int length = 1;
                   if ("09".equals(wfStatus4)){
                       length = 2;
                   }
                   for(int i = 0;i<length;i++) {
                       //公司金币积分入库单
                       CashierBills gscb = new CashierBills();
                       gscb.setCashierBillsID(serverService.getServerID("cashierTally"));
                       //logger.error("公司入库单-------" + gscb.getCashierBillsID());
                       gscb.setCashierDate(new Date());
                       gscb.setJournalNum(serverService.getBillID(companyId));
                       if("11".equals(wfStatus4)||"10".equals(wfStatus4)) {
                           gscb.setBillsType("11".equals(wfStatus4) ? "金币入库单" : "积分入库单");
                           gscb.setProjectName("11".equals(wfStatus4) ? "金币购物" : "积分购物");
                           gscb.setProID("11".equals(wfStatus4) ? "010" : "007");
                           gscb.setAppstyle("11".equals(wfStatus4) ? "08" : "14");//金币/积分分配
                           if("11".equals(wfStatus4)){
                               gscb.setPriceSub(priceSubb);
                           }else{
                               gscb.setPriceSub(priceSubf);
                           }
                       }else{
                           if(i==0) {
                               gscb.setProjectName("积分购物");
                               gscb.setBillsType("积分入库单");
                               gscb.setProID("007");
                               gscb.setAppstyle("14");//金币/积分分配

                               gscb.setPriceSub(priceSubf);

                           }else{
                               gscb.setProjectName("金币购物");
                               gscb.setBillsType("金币入库单");
                               gscb.setProID("010");
                               gscb.setAppstyle("08");
                               gscb.setPriceSub(priceSubb);
                           }
                       }
                       gscb.setStatus("15");
                       gscb.setStaffID("cstaff20160325ZAUAJEU6JH6192643691");
                       gscb.setStaffName("系统生成");
                       gscb.setCompanyID(companyId);
                       gscb.setCompanyName(cpName);
                       gscb.setGroupCompanySn(groupCompanySn);
                       gscb.setInputid("cstaff20160325ZAUAJEU6JH6192643691");
                       gscb.setInputName("系统生成");
                       gscb.setStatusbill("04");


                       gscb.setjNumOrder(cashierBills.getjNumOrder());
                       gscb.setContactUserID(tsc.getStaffid());
                       gscb.setCtUserName(staname);
                       gscb.setFkStatus("00");
                       gscb.setWfStatus4(wfStatus4);
                       String conpamid = tsc.getCompanyId();
                       String conppname = tsc.getPseudoCompanyName();

                       if (tsc.getCompanyId() == null || tsc.getPseudoCompanyName() == null ||
                               tsc.getCompanyId().equals("") || tsc.getPseudoCompanyName().equals("")) {
                           StringBuilder cushql = new StringBuilder(
                                   "SELECT t.companyid, t.pseudo_company_name,t.custype FROM T_ESHOP_CUSCOM t " +
                                           " where t.companyid is not null and t.pseudo_company_name is not null  and rownum = 1 " +
                                           " START WITH t.sccid = ? CONNECT BY PRIOR  t.suppersccid =t.sccid ");
                           List<BaseBean> objc = beandao.getListBeanBySqlAndParams(
                                   cushql.toString(), new Object[]{tsc.getSccId()});
                           if (objc != null && objc.size() >0) {
                               Object objcompany = objc.get(0);
                               Object[] objcc = (Object[]) objcompany;
                               conpamid = objcc[0].toString();
                               conppname = objcc[1].toString();
                           }


                       }
                       gscb.setCcompanyID(conpamid);
                       gscb.setCcompanyName(conppname);

                       backList.add(gscb);

                       //出库单 出库单(个人)
                       CashierBills grcb = new CashierBills();
                       grcb.setCashierBillsID(serverService.getServerID("cashierTally"));
                       //     logger.error("个人出库单-------" + grcb.getCashierBillsID());
                       grcb.setCashierDate(new Date());




                       if("11".equals(wfStatus4)||"10".equals(wfStatus4)) {
                           grcb.setBillsType("11".equals(wfStatus4) ? "金币出库单" : "积分出库单");
                           grcb.setProjectName("11".equals(wfStatus4) ? "金币购物" : "积分购物");
                           grcb.setProID("11".equals(wfStatus4) ? "010" : "007");
                           grcb.setAppstyle("11".equals(wfStatus4) ? "08" : "14");//积分分配
                           if("11".equals(wfStatus4)){
                               grcb.setPriceSub(priceSubb);
                           }else{
                               grcb.setPriceSub(priceSubf);
                           }
                       }else{
                           if(i==0) {
                               grcb.setProjectName("积分购物");
                               grcb.setBillsType("积分出库单");
                               grcb.setProID("007");
                               grcb.setAppstyle("14");//金币/积分分配

                               grcb.setPriceSub(priceSubf);

                           }else{
                               grcb.setProjectName("金币购物");
                               grcb.setBillsType("金币出库单");
                               grcb.setProID("010");
                               grcb.setAppstyle("08");
                               grcb.setPriceSub(priceSubb);
                           }
                       }

                       grcb.setJournalNum(serverService.getBillID(companyId));
                       grcb.setCompanyID(conpamid);
                       grcb.setCompanyName(conppname);
                       grcb.setStaffID(tsc.getStaffid());
                       grcb.setStaffName(staname);
                       grcb.setjNumOrder(cashierBills.getjNumOrder());
                       grcb.setStatus("16");
                       grcb.setStatusbill("04");
                       grcb.setInputName("系统生成");
                       grcb.setContactUserID("cstaff20160325ZAUAJEU6JH6192643691");
                       grcb.setCtUserName(staname);
                       grcb.setFkStatus("00");
                       grcb.setWfStatus4(wfStatus4);
                       backList.add(grcb);




                       //公司产品单据入库单
                       GoodsBills gscp = new GoodsBills();
                       gscp.setCashierBillsID(gscb.getCashierBillsID());
                       gscp.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                       gscp.setGoodsNum(manage.getGoodsNum());
                       gscp.setGoodsName(manage.getGoodsName());
                       gscp.setStandard(manage.getStandard());
                       gscp.setGoodsVariableID(manage.getVariableID());
                       gscp.setWeight(manage.getWeight());
                       gscp.setPrice("0.01");



                       //公司积分明细入库详情
                       stockInv sto = new stockInv();



                       if("11".equals(wfStatus4)||"10".equals(wfStatus4)) {
                           gscp.setPayType("11".equals(wfStatus4) ? "08" : "14");
                           gscp.setGoodsID("11".equals(wfStatus4) ? goldPpID : jfppid);
                           if("11".equals(wfStatus4)){
                               gscp.setQuantity(new BigDecimal(priceSubb).multiply(pri) + "");
                               gscp.setMoney(priceSubb + "");
                               gscp.setGoodsID(jfppid);
                               sto.setInvenQuantity(new BigDecimal(priceSubb).multiply(pri) + "");
                           }else{
                               gscp.setQuantity(new BigDecimal(priceSubf).multiply(pri) + "");
                               gscp.setMoney(priceSubf + "");
                               gscp.setGoodsID(goldPpID);
                               sto.setInvenQuantity(new BigDecimal(priceSubf).multiply(pri) + "");
                           }
                       }else{
                           if(i==0) {
                               gscp.setPayType("14");
                               gscp.setQuantity(new BigDecimal(priceSubf).multiply(pri) + "");
                               gscp.setMoney(priceSubf + "");
                               sto.setInvenQuantity(new BigDecimal(priceSubf).multiply(pri) + "");

                           }else{
                               gscp.setPayType("08");
                               gscp.setQuantity(new BigDecimal(priceSubb).multiply(pri) + "");
                               gscp.setMoney(priceSubb + "");
                               sto.setInvenQuantity(new BigDecimal(priceSubb).multiply(pri) + "");

                           }
                       }





                       gscp.setKcStatus("15");
                       gscp.setGoodstatus("00");
                       gscp.setPpID(manage.getPpID());
                       backList.add(gscp);




                       sto.setStockinvID(serverService.getServerID("stockInv"));
                       sto.setCompanyID(companyId);
                       sto.setGroupCompanySn(groupCompanySn);
                       sto.setGoodsID(manage.getGoodsID());
                       sto.setGoodsType(manage.getTradeID());
                       sto.setGoodsName(manage.getGoodsName());

                       sto.setIntime(new Date());
                       sto.setType("00");
                           sto.setWarehouse(depot.getDepotID());
                           sto.setWarehouseName(depot.getDepotName());


                       sto.setGoodsBillsId(gscp.getGoodsBillsID());
                       backList.add(sto);







                       //个人产品单据出出库单
                       GoodsBills grcp = new GoodsBills();
                       grcp = (GoodsBills) gscp.cloneGoodsBills();
                       grcp.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                       grcp.setCashierBillsID(grcb.getCashierBillsID());
                       grcp.setKcStatus("16");
                       backList.add(grcp);







                       //查询规则

                       if ("11".equals(wfStatus4)||"09".equals(wfStatus4)) {//金币
                           WfjGuize shareCode = (WfjGuize) beandao.getBeanByHqlAndParams("from WfjGuize where wfjGuizeName=?", new Object[]{"金币购物"});

                           WfjJifenDetail edetail = new WfjJifenDetail();
                           edetail.setJifenDetailId(serverService.getServerID("WfjJifenDetail"));


                           edetail.setJifenDetailScore(new BigDecimal(priceSubb).multiply(pri) + "");
                           edetail.setWfjGuizeId(shareCode.getWfjGuizeId());
                           edetail.setWfjJifenId(gold.getWfjJifenId());
                           edetail.setJifenDetailState(Integer.valueOf(0));
                           edetail.setJifenDetailName("金币购物");
                           edetail.setJifenDetailDate(new Date());
                           edetail.setWfjOrderId(cashierBills.getCashierBillsID());
                           edetail.setWfjCashId(grcb.getCashierBillsID());
                           backList.add(edetail);
                       }


                       if ("10".equals(wfStatus4)||"08".equals(wfStatus4)||"09".equals(wfStatus4)){   //积分明细个人
                           WfjGuize shareCode = (WfjGuize) beandao.getBeanByHqlAndParams("from WfjGuize where wfjGuizeName=?", new Object[]{"积分购物"});

                           BonusPointsDetail userbp = new BonusPointsDetail();
                           userbp.setBonusPointsDetailName("积分购物");
                           userbp.setBonusPointsDetailId(serverService.getServerID("BonusPointsDetail"));
                           userbp.setBonusPointsDetailScore(new BigDecimal(priceSubf).multiply(pri) + "");
                           userbp.setBonusPointsDetailDate(new Date());
                           userbp.setBonusPointsId(pbp.getBonusPointsId());
                           userbp.setWfjCashId(grcb.getCashierBillsID());
                           userbp.setWfjOrderId(cashierBills.getCashierBillsID());
                           userbp.setWfjGuizeId(shareCode.getWfjGuizeId());
                           backList.add(userbp);
                       }




                   }



             backList.add(cashierBills);



            BigDecimal mor = new BigDecimal(morrt);
            BigDecimal re = new BigDecimal(0);
            if("11".equals(wfStatus4)){
                re = mor.subtract(new BigDecimal(priceSubb));

            }else if("10".equals(wfStatus4)){
                re = mor.subtract(new BigDecimal(priceSubf));
            }else{//09
                re = mor.subtract(new BigDecimal(priceSubf)).subtract(new BigDecimal(priceSubb));
            }
            re = re.setScale(2, RoundingMode.HALF_UP);
            UnPayRecord unPayRecord = new UnPayRecord();
            unPayRecord.setJournalNum(ddid);
            unPayRecord.setCreateDate(new Date());
            unPayRecord.setSccId(sccId);
            unPayRecord.setRemainNum(re+"");
            unPayRecord.setStatus("00");
            unPayRecord.setUprId(serverService.getServerID("uprid"));
            unPayRecord.setTotalMoney(morrt);
            unPayRecord.setCoinNum(priceSubb);
            unPayRecord.setScoreNum(priceSubf);
            backList.add(unPayRecord);
            remainMoney = re+"";

           beandao.executeHqlsByParmsList(backList, null, null);





        } catch (Exception e) {
            e.printStackTrace();

        }




        return remainMoney;
    }

    /**
     * 优先扣积分再扣金币，不够扣微信支付宝或者现金支付。
     *
     * @param ddid      单据表订单号
     * @param morrt     交易金币
     * @return
     */
    @Override
    public  String checkPay(String ddid, String morrt,String sccId) {

        BonusPoints pbp = (BonusPoints) beandao.getBeanByHqlAndParams(" from BonusPoints where sccid =  ?",
                new Object[]{sccId});//积分
        if(pbp==null){
            pbp = new BonusPoints();
            pbp.setBonusPointScore("0");
        }


        WfjJifen  gold = null;


        BigDecimal pri = new BigDecimal("100");//积分/金币单价
        BigDecimal mpb = new BigDecimal(morrt).multiply(pri);//转换成分
     //   BigDecimal zero = new BigDecimal("0");
        BigDecimal one = new BigDecimal("1");
        BigDecimal pbd = new BigDecimal(pbp.getBonusPointScore());


        if (pbd.compareTo(mpb) < 0) {//积分小于金额
            gold = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where sccid =  ?",
                    new Object[]{sccId});  //金币
            if(gold==null) {
                gold = new WfjJifen();
                gold.setWfjJifenScore("0");
            }
            BigDecimal jbb = new BigDecimal(gold.getWfjJifenScore());//金币

            if(jbb.compareTo(mpb) > 0){//积分不够扣，金币够扣，直接扣金币
                return "07";


            }else{  //金币不够扣
                if(pbd.compareTo(one)>0){//如果积分大于1并且金币也大于1
                    if(jbb.compareTo(one)>0){//如果金币也大于1
                        if(pbd.add(pbd).compareTo(mpb)>=0){//积分+金币 够扣
                            return "08";//金币+积分

                        }else{
                            return "09";//金币+积分+其他
                        }
                    }else{//金币小于0或者等于0
                        return "10";//积分+其他
                    }

                }else{//积分等于0或者小于0
                    if(jbb.compareTo(one)>0){//如果金币也大于1
                        return "11";//金币+其他
                    }else{//金币小于0或者等于0
                        return "0";//积分金币都没有的情况，跳转到支付页面手动支付
                    }
                }


            }
        }else{//积分大于金额 积分够扣
           return "05";
        }

    }

    /**
     * 代理佣金数据备份（一）
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
     * @param activityid  活动id
     * @param proid       判断是否是打赏功能  打赏：011
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<BaseBean> dailiFen(String goodsbillid, String ppid, String cashid, String cashjum, String sccid, String tradeName, String companyID, String quantity, String priceType, String activityid, String proid) throws Exception {
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
        //    logger.error("订单号：" + cashjum);
        StringBuffer s_sql = new StringBuffer();
        s_sql.append(" select c.custype,c.sccid,c.account,c.staffid,s.staffname,c.companyid,c.state,c.superioragent ");
        s_sql.append(" from t_Eshop_Cuscom c,dt_hr_Staff s ");
        s_sql.append(" where s.staffid = c.staffid and c.sccId=?");

        StringBuffer p_sql = new StringBuffer();
        p_sql.append(" select c.custype,c.sccid,c.account,c.staffid,s.staffname,c.companyid,c.state,c.superioragent ");
        p_sql.append(" from dt_productpackaging p,t_Eshop_Cuscom c,dt_hr_Staff s ");
        p_sql.append(" where p.companyid = c.companyid and s.staffid = c.staffid and p.ppid = ?");

        String[] a = {"p20170220ZVZR76B88M0000000019", "p20170220ZVZR76B88M0000000018", "p20170220ZVZR76B88M0000000020"};

        String[] b = new String[a.length + 1];
        System.arraycopy(a, 0, b, 0, a.length);
        b[3] = ppid;

        String[] c = new String[a.length + 1];
        System.arraycopy(a, 0, c, 0, a.length);
        c[3] = activityid;


        //int strLen1 = b.length;//保存第一个数组长度
        //int strLen2 = a.length;//保存第二个数组长度
        //int strLen3 = c.length;//保存第二个数组长度

        /*b = Arrays.copyOf(b, strLen1 + strLen2 + strLen3);//扩容
        System.arraycopy(a, 0, b, strLen1, strLen2);//将第二个数组与第一个数组合并
        System.arraycopy(c, 0, b, strLen1 + strLen2, strLen3);//将第二个数组与第一个数组合并*/


        BigDecimal quantityNum = new BigDecimal(quantity);

        StringBuilder cushql = new StringBuilder(
                "select * from (SELECT t.sccid FROM T_ESHOP_CUSCOM t");
        cushql.append(" START WITH t.sccid= ? CONNECT BY PRIOR t.suppersccid=t.sccid");
        cushql.append(" order by t.custype ) where rownum=1 ");

        Object ptsccid = beandao
                .getObjectBySqlAndParams(cushql.toString(),
                        new Object[]{sccid});

        /**************************已抢购除省县村代理以外的该产品********************************/
        //查询已抢购该产品的代理商
        StringBuffer str = null;
        List<BaseBean> slist = null;
        if (priceType == null) {
            str = VOtool.getBrokerageSql(2, 1, 1);

            slist = beandao.getListBeanBySqlAndParams(str.toString(), b);
        } else {
            str = VOtool.getBrokerageSql(2, Integer.valueOf(priceType) + 1, 2);

            slist = beandao.getListBeanBySqlAndParams(str.toString(), c);
            if (slist == null || slist.size() == 0) {
                str.delete(0, str.length());
                str = VOtool.getBrokerageSql(2, Integer.valueOf(priceType) + 1, 3);
                slist = beandao.getListBeanBySqlAndParams(str.toString(), c);
            }
        }

        if (slist != null && slist.size() > 0) {
            for (int i = 0; i < slist.size(); i++) {
                Object ojb = slist.get(i);
                if (ojb != null) {
                    Object[] objects = (Object[]) ojb;
                    if (objects[2] != null) {
                        BigDecimal jinbi = new BigDecimal(objects[2].toString()).multiply(quantityNum).multiply(new BigDecimal(100));

                        addDlMember(s_sql.toString(), objects[1].toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, objects[3].toString());
                    }
                }
            }
        }

        /**************************已抢购省县村代理的该产品********************************/
        //查询已抢购该产品的代理商

        StringBuffer sxcstr = null;

        List<BaseBean> sxclist = null;

        if (priceType == null) {
            sxcstr = VOtool.getBrokerageSql(3, 1, 1);
            sxclist = beandao.getListBeanBySqlAndParams(sxcstr.toString(), b);
        } else {
            sxcstr = VOtool.getBrokerageSql(3, Integer.valueOf(priceType) + 1, 2);
            sxclist = beandao.getListBeanBySqlAndParams(sxcstr.toString(), c);
            if (sxclist == null || sxclist.size() == 0) {
                sxcstr.delete(0, str.length());
                sxcstr = VOtool.getBrokerageSql(3, Integer.valueOf(priceType) + 1, 3);
                sxclist = beandao.getListBeanBySqlAndParams(sxcstr.toString(), c);
            }
        }

        DtOrderBillAdd oba = (DtOrderBillAdd) beandao.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId = ? ", new Object[]{cashid});

        for (int j = 0; j < a.length; j++) {
            if (sxclist != null && sxclist.size() > 0) {
                BigDecimal jinbi = BigDecimal.ZERO;
                for (int i = 0; i < sxclist.size(); i++) {
                    Object ojb = sxclist.get(i);
                    if (ojb != null) {
                        Object[] objects = (Object[]) ojb;
                        if (objects[3].equals(a[j]) && (oba.getReceiveaddress() != null && !oba.getReceiveaddress().equals(""))) {
                            if (objects[2] != null) {
                                //jinbi = Float.parseFloat(objects[2].toString()) * 100;
                                jinbi = new BigDecimal(objects[2].toString()).multiply(quantityNum).multiply(new BigDecimal(100));

                                int anum = oba.getReceiveaddress().indexOf(objects[4].toString());
                                if (anum > -1) {
                                    addDlMember(s_sql.toString(), objects[1].toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, objects[3].toString());
                                    break;
                                } else {
                                    if (i == sxclist.size() - 1 && jinbi.compareTo(BigDecimal.ZERO) == 1) {

                                        addDlMember(s_sql.toString(), ptsccid.toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, a[j]);
                                    }
                                }
                            }
                        } else {
                            if (i == sxclist.size() - 1 && jinbi.compareTo(BigDecimal.ZERO) == 1) {

                                addDlMember(s_sql.toString(), ptsccid.toString(), cashid, cashjum, jinbi, ppid, goodsbillid, backList, a[j]);
                            }
                        }
                    }
                }
            }
        }


        /**************************p20170220ZVZR76B88M0000000022 购物积分代理********************************/

        //查询给购物者分的积分数
        str.delete(0, str.length());

        Object obj = null;

        if (priceType == null) {
            str = VOtool.getBrokerageSql(4, 1, 1);
            obj = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"p20170220ZVZR76B88M0000000022", ppid});
        } else {
            str = VOtool.getBrokerageSql(4, Integer.valueOf(priceType) + 1, 2);
            obj = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"p20170220ZVZR76B88M0000000022", activityid});
            if (obj == null) {
                str.delete(0, str.length());
                str = VOtool.getBrokerageSql(4, Integer.valueOf(priceType) + 1, 3);
                obj = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"p20170220ZVZR76B88M0000000022", activityid});
            }
        }

        if (obj != null) {
            //Float buyScore = Float.parseFloat(obj.toString()) * 100;
            BigDecimal buyScore = new BigDecimal(obj.toString()).multiply(quantityNum).multiply(new BigDecimal(100));
            if (buyScore.compareTo(BigDecimal.ZERO) == 1) {
                addDlMember(s_sql.toString(), sccid, cashid, cashjum, buyScore, ppid, goodsbillid, backList, "p20170220ZVZR76B88M0000000022");
            }
        }
        /**************************p20170605KY3VAANZJG0000000003 设备投资********************************/
        //查询给设备投资人分的佣金数
        str.delete(0, str.length());
        obj = null;
        if (priceType == null) {
            str = VOtool.getBrokerageSql(5, 1, 1);
            obj = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"p20170605KY3VAANZJG0000000003", ppid});
        } else {
            str = VOtool.getBrokerageSql(5, Integer.valueOf(priceType) + 1, 2);
            obj = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"p20170605KY3VAANZJG0000000003", activityid});
            if (obj == null) {
                str.delete(0, str.length());
                str = VOtool.getBrokerageSql(5, Integer.valueOf(priceType) + 1, 3);
                obj = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"p20170605KY3VAANZJG0000000003", activityid});
            }
        }

        if (obj != null) {
            Object[] tz = (Object[]) obj;
            if (tz[0] != null && !tz[0].equals("")) {
                //Float buyScore = Float.parseFloat(tz[0].toString()) * 100;

                BigDecimal buyScore = new BigDecimal(tz[0].toString()).multiply(quantityNum).multiply(new BigDecimal(100));
                if (buyScore.compareTo(BigDecimal.ZERO) == 1) {
                    if (tz[1] != null && !tz[1].equals("")) {
                        if (proid != null && proid.equals("011")) {//打赏的
                            Rewarddetail rd = (Rewarddetail) baseBeanService.getBeanByHqlAndParams("from Rewarddetail r where r.ordernum=? and r.tradeStatus=?", new Object[]{cashjum, "00"});
                            addDlMember(s_sql.toString(), rd.getFbSccid(), cashid, cashjum, buyScore, ppid, goodsbillid, backList, "p20170605KY3VAANZJG0000000003");
                        } else {//普通的
                            MarKeting marketing = (MarKeting) baseBeanService.getBeanByHqlAndParams("from MarKeting where userSccId=?", new Object[]{sccid});

                            String sql = "select d.dbsccid from dt_deviceBind d,dt_devicebind_staff ds where d.dbid=ds.dbsdbid and d.dbstatus=? and ds.dbsstatus=? and ds.dbssccid=? and d.dbtzType=?";
                            Object ywsccid = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{'1', '1', marketing.getMkSccId(), tz[1]});
                            List<Object> pamer = new ArrayList<Object>();

                            str.delete(0, str.length());

                            //通过业务员找到设备及设备投资人如果找不到则绑定产品供应商公司
                            if (ywsccid != null) {
                                //   logger.error("投资设备：上级绑定");

                                addDlMember(s_sql.toString(), ywsccid.toString(), cashid, cashjum, buyScore, ppid, goodsbillid, backList, "p20170605KY3VAANZJG0000000003");
                            } else {
                                //  logger.error("投资设备：公司绑定");

                                addDlMember(p_sql.toString(), ppid, cashid, cashjum, buyScore, ppid, goodsbillid, backList, "p20170605KY3VAANZJG0000000003");
                            }
                        }
                    } else {
                        logger.error("设备投资:投资设备类型为空");

                        tz[1].toString();
                    }
                } else {
                    logger.error("设备投资:投资设备佣金为0");
                }
            } else {
                logger.error("设备投资:投资设备佣金为0");
            }
        } else {
            logger.error("设备投资:设置产品佣金时未设置设备投资信息");
        }

        /**************************没分出去的佣金给供应商********************************/
        //查询没分出去的佣金总数
        str.delete(0, str.length());

        Object objScore = null;
        if (priceType == null) {
            str = VOtool.getBrokerageSql(6, 1, 1);
            objScore = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"00", "p20170220ZVZR76B88M0000000022", "p20170605KY3VAANZJG0000000003", ppid});
        } else {
            str = VOtool.getBrokerageSql(6, Integer.valueOf(priceType) + 1, 2);
            objScore = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"00", "p20170220ZVZR76B88M0000000022", "p20170605KY3VAANZJG0000000003", activityid});
            if (objScore == null) {
                str.delete(0, str.length());
                str = VOtool.getBrokerageSql(6, Integer.valueOf(priceType) + 1, 3);
                objScore = beandao.getObjectBySqlAndParams(str.toString(), new Object[]{"00", "p20170220ZVZR76B88M0000000022", "p20170605KY3VAANZJG0000000003", activityid});
            }
        }

        if (objScore != null) {
            //Float noScore = Float.parseFloat(objScore.toString()) * 100;
            BigDecimal noScore = new BigDecimal(objScore.toString()).multiply(quantityNum).multiply(new BigDecimal(100));
            if (noScore.compareTo(BigDecimal.ZERO) == 1) {
                addDlMember(p_sql.toString(), ppid, cashid, cashjum, noScore, ppid, goodsbillid, backList, "p20170510SIR5KABJEP0000000003");
            }
        }
        //    logger.error("代理佣金分配方式存储结束");
        return backList;
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
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<BaseBean> dailiFen2(String goodsbillid, String ppid, String cashid, String cashjum, String sccid, String tradeName, String companyID, String quantity, String priceType, Object[] psuObj, String proid) throws Exception {
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

                Object ptsccid = beandao
                        .getObjectBySqlAndParams(cushql.toString(),
                                new Object[]{sccid});

                List<BaseBean> slist = null;
                List<BaseBean> sxclist = null;

                List<BaseBean> pplist = beandao.getListBeanByHqlAndParams("FROM ProProxy P WHERE P.ppid = ?", new Object[]{ppid});
                Map<String, ProProxy> proxyMap = null;
                if (pplist != null && !pplist.isEmpty()) {
                    proxyMap = new HashedMap<>();
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

                                    slist = beandao.getListBeanBySqlAndParams(result.toString(), a);
                                    b = false;
                                }
                            } else if (c && (pp.getTypePpid().equals("p20170220ZVZR76B88M0000000019") || pp.getTypePpid().equals("p20170220ZVZR76B88M0000000018") || pp.getTypePpid().equals("p20170220ZVZR76B88M0000000020"))) {
                                if (pp.getState() != null && pp.getState().equals("01")) {
                                    StringBuffer result1 = new StringBuffer("SELECT PRO.PPID, SUB.SCCID, SUB.TYEPPPID, P.GOODSNAME");
                                    result1.append(" FROM DT_PRO_PROXY PP LEFT JOIN T_CUSCOM_PRODUCT PRO ON PRO.PPID = PP.PPID");
                                    result1.append(" LEFT JOIN T_ESHOP_CUSCOM_SUB SUB ON SUB.SCCSID = PRO.SCCSID AND SUB.TYEPPPID = PP.TYPE_PPID");
                                    result1.append(" LEFT JOIN DT_PRODUCTPACKAGING P ON SUB.AREAPPID = P.PPID");
                                    result1.append(" WHERE PP.STATE = ? AND SUB.TYEPPPID IN (?, ?, ?) AND PRO.PPID = ?");
                                    sxclist = beandao.getListBeanBySqlAndParams(result1.toString(), a);
                                    c = false;
                                }
                            }
                        }
                    }
                }

                DtOrderBillAdd oba = (DtOrderBillAdd) beandao.getBeanByHqlAndParams("FROM DtOrderBillAdd WHERE oaBillId = ? ", new Object[]{cashid});

                BigDecimal d = BigDecimal.ZERO;
                for (int j = 0; j < subList.size(); j++) {
                    Object[] psup = (Object[]) subList.get(j);
                    ProProxy pp = null;
                    if (proxyMap != null && !proxyMap.isEmpty()) {
                        pp = proxyMap.get(psup[1]);
                    }
                    //System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
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
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private void addDlMember(String sql, String p_obj, String cashid, String cashjum, BigDecimal noScore, String ppid, String goodsbillid, List<BaseBean> beanList, String type_ppid) throws Exception {

        //查询人员信息
        Object[] colInfor = (Object[]) beandao.getObjectBySqlAndParams(sql, new Object[]{p_obj});

        if (colInfor != null && colInfor.length > 0) {
            if (noScore.compareTo(BigDecimal.ZERO) == 1) {
                //将查询到的数据添加到DtDaiLiMember表中
                DtDaiLiMember dt1 = new DtDaiLiMember();

                dt1.setMbId(serverService.getServerID("Mbid"));
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
                ProductPackaging pro = (ProductPackaging) beandao.getBeanByHqlAndParams(
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

    /**
     * 消费补助数据备份（一）<新>
     *
     * @param companyID 公司id
     * @param sccid     购买人员id
     * @param cashid    订单id
     * @param cashjum   订单编号
     * @param total     消费补助分配金额
     * @return 数据集合
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private List<BaseBean> sdfd(String companyID, String sccid, String cashid, String cashjum, BigDecimal total) throws Exception {
        String fsccid = null;
        String ssccid = null;

        BigDecimal xf = BigDecimal.ZERO;
        BigDecimal xb = BigDecimal.ZERO;
        BigDecimal fs = BigDecimal.ZERO;
        BigDecimal fb = BigDecimal.ZERO;
        BigDecimal pt = BigDecimal.ZERO;
        BigDecimal bl = new BigDecimal(100);

        StringBuilder pussql = new StringBuilder(
                "select s.sccid from(SELECT * FROM dt_uall_subsidize t");
        pussql.append(" where t.ssid=?) s where rownum<=? START WITH s.sccid= ?");
        pussql.append(" CONNECT BY PRIOR s.psccid=s.sccid order by s.adddate desc");

        StringBuilder cushql = new StringBuilder(
                "select a.*,s.staffname from (select * from (SELECT t.sccid,t.account,t.staffid FROM T_ESHOP_CUSCOM t");
        cushql.append(" START WITH t.sccid= ? CONNECT BY PRIOR t.suppersccid=t.sccid");
        cushql.append(" order by t.custype ) where rownum=1) a,dt_hr_staff s where a.staffid=s.staffid");

        Object[] ptsccid = (Object[]) beandao
                .getObjectBySqlAndParams(cushql.toString(),
                        new Object[]{sccid});

        List<BaseBean> ul = new ArrayList<BaseBean>();

        //查询产品行业类别
        String hysql = "select cc.industrytype from dtcontactcompany cc,dt_ccom_com ccc where cc.ccompanyid=ccc.ccompany_id and ccc.compnay_id=?";
        Object hy = baseBeanService.getObjectBySqlAndParams(hysql, new Object[]{companyID});

        SetSubsidize setSubsidize = (SetSubsidize) baseBeanService.getBeanByHqlAndParams("from SetSubsidize where gtid=? and stutas=?", new Object[]{hy, "01"});

        if (setSubsidize != null) {
            //消费红包
            xf = total.multiply(new BigDecimal(setSubsidize.getXfPct()).divide(new BigDecimal(100)));
            //消费补充红包
            xb = total.multiply(new BigDecimal(setSubsidize.getXbPct()).divide(new BigDecimal(100)));
            addTeshopCusCom(ul, "xfbzhb", "xfbzhb", xb, cashid, cashjum, setSubsidize.getXbPct().toString(), "1");
            //粉丝红包
            fs = total.multiply(new BigDecimal(setSubsidize.getFsPct()).divide(new BigDecimal(100)));
            addTeshopCusCom(ul, "fshb", "fshb", fs, cashid, cashjum, setSubsidize.getFsPct().toString(), "2");
            //粉丝补充红包
            /*fb=total.multiply(new BigDecimal(setSubsidize.getFbPct()).divide(new BigDecimal(100)));
            addTeshopCusCom(ul,"fsbchb","fsbchb",fb,cashid,cashjum,setSubsidize.getFbPct().toString(),"3");*/
            //平台
            pt = total.subtract(xb).subtract(fs).subtract(fb).subtract(xf);
            bl = bl.subtract(new BigDecimal(setSubsidize.getXfPct())).subtract(new BigDecimal(setSubsidize.getXbPct())).subtract(new BigDecimal(setSubsidize.getFsPct()));
            addSubsidizeBackup2(ul, ptsccid[0].toString(), ptsccid[1].toString(), ptsccid[2].toString(), ptsccid[3].toString(), cashid, cashjum, pt, bl, "4");

            UallSubsidize uallSubsidize = (UallSubsidize) baseBeanService.getBeanByHqlAndParams("from UallSubsidize where ssid=? and sccid=?", new Object[]{setSubsidize.getSsid(), sccid});

            if (uallSubsidize != null) {
                if (uallSubsidize.getPsccid() != null) {
                    List<Object> o = baseBeanService.getListBeanBySqlAndParams(pussql.toString(), new Object[]{setSubsidize.getSsid(), 2, uallSubsidize.getPsccid()});
                    if (o != null) {
                        if (o.size() >= 1) {
                            fsccid = o.get(0).toString();
                        }
                        if (o.size() >= 2) {
                            ssccid = o.get(1).toString();
                        }
                        addSubsidizeBackup(ul, fsccid, ssccid, ptsccid[0].toString(), cashid, cashjum, new BigDecimal(setSubsidize.getFlPct()), new BigDecimal(setSubsidize.getSlPct()), xf);
                    }
                }
            } else {
                uallSubsidize = new UallSubsidize();
                uallSubsidize.setAdddate(new Date());
                uallSubsidize.setUsid(serverService.getServerID("uall"));
                uallSubsidize.setSccid(sccid);
                //根据行业类别查询消费补助绑定的最后一个人
                StringBuilder uasql = new StringBuilder("select * from (select t.*, rownum from (select us.*, ts.stnum");
                uasql.append(" from dt_uall_subsidize us, dt_set_subsidize  dss, dt_type_subsidize ts where us.ssid = dss.ssid");
                uasql.append(" and dss.stid = ts.stid and stutas = ? and dss.gtid = ? order by us.adddate desc) t order by rownum) where rownum = ?");
                Object[] ualls = (Object[]) baseBeanService.getObjectBySqlAndParams(uasql.toString(), new Object[]{"01", hy, 1});
                if (ualls != null) {
                    int count = baseBeanService.getConutByByHqlAndParams("select count(*) from UallSubsidize where ssid=? and psccid=?", new Object[]{ualls[2], ualls[3]});
                    if (count < Integer.parseInt(ualls[8].toString())) {
                        if (ualls[3] != null) {
                            uallSubsidize.setPsccid(ualls[3].toString());
                            uallSubsidize.setSlevel(Long.parseLong(ualls[5].toString()));
                        } else {
                            uallSubsidize.setPsccid(ualls[4].toString());
                            uallSubsidize.setSlevel(Long.parseLong(ualls[5].toString()) + 1);
                        }

                        uallSubsidize.setSsid(ualls[2].toString());
                    } else {
                        StringBuffer ussql = new StringBuffer("select * from (select Sccid,CountNum,Ssid,Slevel from dt_uall_subsidize uuu where");
                        ussql.append(" uuu.countnum>(select max(u.countnum) from dt_uall_subsidize u where u.ssid=? and u.sccid=?)");
                        ussql.append(" and uuu.ssid=? order by uuu.countnum) where rownum = ?");

                        Object[] uu = (Object[]) baseBeanService.getObjectBySqlAndParams(ussql.toString(), new Object[]{ualls[2], ualls[3], ualls[2], 1});

                        if (uu != null) {
                            uallSubsidize.setPsccid(uu[0].toString());
                            uallSubsidize.setSsid(uu[2].toString());
                            uallSubsidize.setSlevel(Long.parseLong(uu[3].toString()) + 1);
                        }
                    }
                    uallSubsidize.setCountNum(Long.parseLong(ualls[7].toString()) + 1);

                    List<Object> o = baseBeanService.getListBeanBySqlAndParams(pussql.toString(), new Object[]{setSubsidize.getSsid(), 2, uallSubsidize.getPsccid()});

                    if (o != null) {
                        if (o.size() >= 1) {
                            fsccid = o.get(0).toString();
                        }
                        if (o.size() >= 2) {
                            ssccid = o.get(1).toString();
                        }
                        addSubsidizeBackup(ul, fsccid, ssccid, ptsccid[0].toString(), cashid, cashjum, new BigDecimal(setSubsidize.getFlPct()), new BigDecimal(setSubsidize.getSlPct()), xf);
                    }
                } else {
                    uallSubsidize.setCountNum(1L);
                    uallSubsidize.setSsid(setSubsidize.getSsid());
                    uallSubsidize.setSlevel(1L);
                }
                ul.add(uallSubsidize);
            }
        }
        return ul;
    }

    /**
     * 消费补助数据备份（二）
     *
     * @param ul      数据集合
     * @param fsccid  第一级分配人员id
     * @param ssccid  第二级分配人员id
     * @param psccid  第三级（平台）分配人员id
     * @param cashid  订单id
     * @param cashjum 订单编号
     * @param f       第一级分配比例
     * @param s       第二级分配比例
     * @param total   消费补助分配金额
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private void addSubsidizeBackup(List<BaseBean> ul, String fsccid, String ssccid, String psccid, String cashid, String cashjum, BigDecimal f, BigDecimal s, BigDecimal total) throws Exception {
        BigDecimal pp = new BigDecimal("100");
        String ts = "select t.account,t.custype,s.staffid,s.staffname,t.state from t_eshop_cuscom t,dt_hr_staff s where t.staffid=s.staffid and t.sccid=?";
        BigDecimal ffs = BigDecimal.ZERO;
        BigDecimal sss = BigDecimal.ZERO;
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            if (fsccid != null) {
                Object[] fo = (Object[]) baseBeanService.getObjectBySqlAndParams(ts, new Object[]{fsccid});
                subsidizeBackup sbf = new subsidizeBackup();
                sbf.setSbId(serverService.getServerID("SbId"));
                sbf.setCashId(cashid);
                sbf.setCashJum(cashjum);
                sbf.setmType(fo[1].toString());
                sbf.setmId(fsccid);
                sbf.setmZh(fo[0].toString());
                sbf.setmStatus(fo[4].toString());
                sbf.setStaffId(fo[2].toString());
                sbf.setStaffName(fo[3].toString());
                sbf.setJbBl(f + "%");
                sbf.setJbNum((total.multiply(f.divide(pp))).setScale(2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).toString());
                ffs = (total.multiply(f.divide(pp))).setScale(2, BigDecimal.ROUND_DOWN);
                sbf.setStatus("0");
                ul.add(sbf);
                // logger.error("消费补助：" + total + "-" + sbf.getJbBl() + "-" + sbf.getJbNum() + "-" + sbf.getmZh());
            } else {
                f = new BigDecimal(0);
            }

            if (ssccid != null) {
                Object[] so = (Object[]) baseBeanService.getObjectBySqlAndParams(ts, new Object[]{ssccid});
                subsidizeBackup sbs = new subsidizeBackup();
                sbs.setSbId(serverService.getServerID("SbId"));
                sbs.setCashId(cashid);
                sbs.setCashJum(cashjum);
                sbs.setmType(so[1].toString());
                sbs.setmId(ssccid);
                sbs.setmZh(so[0].toString());
                sbs.setmStatus(so[4].toString());
                sbs.setStaffId(so[2].toString());
                sbs.setStaffName(so[3].toString());
                sbs.setJbBl(s + "%");
                sbs.setJbNum((total.multiply(s.divide(pp))).setScale(2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).toString());
                sss = (total.multiply(s.divide(pp))).setScale(2, BigDecimal.ROUND_DOWN);
                sbs.setStatus("0");
                ul.add(sbs);
                //  logger.error("消费补助：" + total + "-" + sbs.getJbBl() + "-" + sbs.getJbNum() + "-" + sbs.getmZh());
            } else {
                s = new BigDecimal(0);
            }

            BigDecimal p = new BigDecimal(0);
            p = pp.subtract(f.add(s));
            if (p.compareTo(BigDecimal.ZERO) > 0) {
                Object[] sp = (Object[]) baseBeanService.getObjectBySqlAndParams(ts, new Object[]{psccid});
                subsidizeBackup sbp = new subsidizeBackup();
                sbp.setSbId(serverService.getServerID("SbId"));
                sbp.setCashId(cashid);
                sbp.setCashJum(cashjum);
                sbp.setmType(sp[1].toString());
                sbp.setmId(psccid);
                sbp.setmZh(sp[0].toString());
                sbp.setmStatus(sp[4].toString());
                sbp.setStaffId(sp[2].toString());
                sbp.setStaffName(sp[3].toString());
                sbp.setJbBl(p + "%");
                sbp.setJbNum(total.subtract(ffs).subtract(sss).setScale(2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).toString());
                sbp.setStatus("0");
                ul.add(sbp);
                // logger.error("消费补助：" + total + "-" + sbp.getJbBl() + "-" + sbp.getJbNum() + "-" + sbp.getmZh());
            }
        }
    }

    /**
     * 消费补助数据备份（三）
     *
     * @param ul      集合
     * @param sdf     帐号
     * @param sname   人员名称
     * @param xb      金币数量
     * @param cashid  订单id
     * @param cashjum 订单编号
     * @param xbbl    百分比
     * @param type    类型
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private void addTeshopCusCom(List<BaseBean> ul, String sdf, String sname, BigDecimal xb, String cashid, String cashjum, String xbbl, String type) throws Exception {

        String sql = "select t.sccid,t.account,s.staffid,s.staffname from T_ESHOP_CUSCOM t,dt_hr_staff s where t.staffid=s.staffID and t.account=? and s.staffName=?";
        Object[] objects = (Object[]) baseBeanService.getObjectBySqlAndParams(sql, new Object[]{sdf, sname});
        if (xb.compareTo(BigDecimal.ZERO) > 0) {
            if (objects == null || objects.length <= 0) {
                TEshopCusCom teccc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[]{"15810799888"});
                Staff staff = new Staff();
                String staffid = serverService.getServerID("cstaff");
                String phql = "select count(*) from Staff ";
                int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
                staff.setStaffCode("NO" + (pcount + 1));
                staff.setRecordCode("NO" + (pcount + 1));
                staff.setVerifyTime(new Date());
                staff.setStaffID(staffid);
                staff.setSource("app");
                staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
                staff.setStaffStatus("00");
                staff.setStatus("01");
                staff.setStaffName(sname);
                staff.setVipno("1");
                ul.add(staff);

                TEshopCusCom tecc = new TEshopCusCom();
                tecc.setAccount(sdf);
                tecc.setCusType("7");
                tecc.setSccId(serverService.getServerID("sccid"));
                tecc.setSuperioragent(teccc.getAccount());
                tecc.setSupperSccId(teccc.getSccId());
                tecc.setState("1");
                tecc.setTeccDate(new Date());
                tecc.setAcquiesce("01");
                tecc.setStaffid(staffid);
                ul.add(tecc);

                TEshopCustomer tecr = new TEshopCustomer();
                tecr.setStaffid(staffid);
                tecr.setAccount(sdf);
                tecr.setPassword(sdf + "123456");
                tecr.setPaymentCode(sdf + "123456");
                ul.add(tecr);
                addSubsidizeBackup2(ul, tecc.getSccId(), tecc.getAccount(), staffid, staff.getStaffName(), cashid, cashjum, xb, new BigDecimal(xbbl), type);
            } else {
                addSubsidizeBackup2(ul, objects[0].toString(), objects[1].toString(), objects[2].toString(), objects[3].toString(), cashid, cashjum, xb, new BigDecimal(xbbl), type);
            }
        }
    }

    /**
     * 消费补助数据备份（四）
     *
     * @param ul      集合
     * @param sccid   帐号id
     * @param zh      帐号
     * @param staffid 人员id
     * @param name    人员姓名
     * @param cashid  订单id
     * @param cashjum 订单编号
     * @param f       百分比
     * @param jb      金币数量
     * @param type    类型
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    private void addSubsidizeBackup2(List<BaseBean> ul, String sccid, String zh, String staffid, String name, String cashid, String cashjum, BigDecimal jb, BigDecimal f, String type) throws Exception {
        BigDecimal pp = new BigDecimal("100");
        if (jb.compareTo(BigDecimal.ZERO) > 0) {
            subsidizeBackup sbf = new subsidizeBackup();
            sbf.setSbId(serverService.getServerID("SbId"));
            sbf.setCashId(cashid);
            sbf.setCashJum(cashjum);
            sbf.setmType("7");
            sbf.setmId(sccid);
            sbf.setmZh(zh);
            sbf.setmStatus("1");
            sbf.setStaffId(staffid);
            sbf.setStaffName(name);
            sbf.setJbBl(f + "%");
            sbf.setJbNum(jb.multiply(new BigDecimal(100)).toString());
            sbf.setStatus("0");
            sbf.setSbType(type);
            ul.add(sbf);
            //    logger.error("type：" + jb + "-" + sbf.getJbBl() + "-" + sbf.getJbNum() + "-" + sbf.getmZh());
        }
    }

    /**
     * 变价信息处理
     *
     * @param cashid 订单id
     */
    public void getPrice(String cashid) throws Exception {
        String hqlh = " from CashierBills where cashierBillsID = ?";
        CashierBills cashierBills = (CashierBills) beandao.getBeanByHqlAndParams(hqlh, new Object[]{cashid});
        String goodl = "from GoodsBills d where d.cashierBillsID=?";
        List<BaseBean> ret = beandao.getListBeanByHqlAndParams(goodl, new String[]{cashierBills.getCashierBillsID()});
        List<BaseBean> baseBeansList = new ArrayList<>();
        for (int i = 0; i < ret.size(); i++) {
            GoodsBills gb = (GoodsBills) ret.get(i);
            if (gb.getPricetype() != null && gb.getPricetype().equals("5")) {
                String priceid = "";
                String pcHql = "from Pricechange where pcid=?";
                Pricechange p = (Pricechange) beandao.getBeanByHqlAndParams(pcHql, new Object[]{gb.getActivityID()});
                if (p.getPricestatus() != null && p.getPricestatus() != null && !p.getPricestatus().equals("") && p.getPriceid() != null && !p.getPriceid().equals("")) {
                    //0:零售  1：批发 2：VIP  3.普通活动 4.特价活动
                    if (p.getPricestatus().equals("0")) {
                        ProSetup ps = (ProSetup) beandao.getBeanByHqlAndParams("from ProSetup where suid=?", new Object[]{p.getPriceid()});

                        List<BaseBean> psbList = beandao.getListBeanByHqlAndParams("from ProSetupSub where suid=?", new Object[]{p.getPriceid()});
                        ProSetupBackup psb = new ProSetupBackup();
                        psb.setSubId(serverService.getServerID("setupbackup"));
                        psb.setEfPrice(p.getEfPrice());
                        psb.setRePrice(p.getRePrice());
                        psb.setSjDate(p.getSjdate());
                        psb.setPrincipal(p.getStaffid());
                        psb.setPpName(ps.getPpname());
                        psb.setComId(ps.getComId());
                        psb.setPpid(p.getPpid());
                        psb.setBrokerage(p.getBrokerage());
                        psb.setProxySumPrice(p.getProxySumPrice());
                        psb.setTzType(ps.getTzType());
                        psb.setState(ps.getState());
                        psb.setEditDate(new Date());
                        priceid = psb.getSubId();
                        baseBeansList.add(psb);
                        for (int j = 0; j < psbList.size(); j++) {
                            ProSetupSub pss = (ProSetupSub) psbList.get(j);
                            ProSetupSubBackup pssb = new ProSetupSubBackup();
                            pssb.setSusbId(serverService.getServerID("prosetupsubbackup"));
                            pssb.setSubId(psb.getSubId());
                            pssb.setAmount(pss.getAmount());
                            pssb.setSjDate(new Date());
                            pssb.setPpid(p.getPpid());
                            pssb.setTypePpid(pss.getTypePpid());
                            pssb.setAgentType(pss.getAgentType());
                            pssb.setInvestComId(pss.getInvestComId());
                            pssb.setAgentState(pss.getAgentState());
                            pssb.setTextUrl(pss.getTextUrl());
                            pssb.setState(pss.getState());
                            baseBeansList.add(pssb);
                        }
                    } else if (p.getPricestatus().equals("1")) {
                        PWholesale pws = (PWholesale) beandao.getBeanByHqlAndParams("from PWholesale where wholesaleId=?", new Object[]{p.getPriceid()});
                        List<BaseBean> psbList = beandao.getListBeanByHqlAndParams("from PWhoBrokerage where wholesaleId=?", new Object[]{p.getPriceid()});
                        PWhoHistory pwh = new PWhoHistory();
                        pwh.setWholesaleId(serverService.getServerID("wholesaleId"));
                        pwh.setWholesale(Double.valueOf(p.getRePrice()));
                        pwh.setFactory(Double.valueOf(p.getEfPrice()));
                        pwh.setBrokerage(Double.valueOf(p.getBrokerage()));
                        pwh.setBrokerages(Double.valueOf(p.getProxySumPrice()));
                        pwh.setInvestType(pws.getInvestType());
                        pwh.setUpdateTimes(new Date());
                        pwh.setPpid(p.getPpid());
                        pwh.setCompanyId(p.getComId());
                        pwh.setState(pws.getState());
                        pwh.setAddTimes(p.getSjdate());
                        pwh.setPrincipal(p.getStaffid());
                        priceid = pwh.getWholesaleId();
                        baseBeansList.add(pwh);
                        for (int j = 0; j < psbList.size(); j++) {
                            PWhoBroHistory pwbh = new PWhoBroHistory();
                            PWhoBrokerage pwb = (PWhoBrokerage) psbList.get(j);
                            pwbh.setWhobroId(serverService.getServerID("whobroId"));
                            pwbh.setBrokerage(pwb.getBrokerage());
                            pwbh.setPpid(p.getPpid());
                            pwbh.setTypePpid(pwb.getTypePpid());
                            pwbh.setCompanyId(pwb.getCompanyId());
                            pwbh.setWholesaleId(pwh.getWholesaleId());

                            baseBeansList.add(pwbh);
                        }
                    } else if (p.getPricestatus().equals("2")) {
                        PVip pv = (PVip) beandao.getBeanByHqlAndParams("from PVip where vipId=?", new Object[]{p.getPriceid()});
                        List<BaseBean> psbList = beandao.getListBeanByHqlAndParams("from PVipBrokerage where vipId=?", new Object[]{p.getPriceid()});
                        PVipHistory pvh = new PVipHistory();
                        pvh.setVipId(serverService.getBillID("vipId"));
                        pvh.setVip(Double.valueOf(p.getRePrice()));
                        pvh.setFactory(Double.valueOf(p.getEfPrice()));
                        pvh.setBrokerage(Double.valueOf(p.getBrokerage()));
                        pvh.setBrokerages(Double.valueOf(p.getProxySumPrice()));
                        pvh.setInvestType(pv.getInvestType());
                        pvh.setUpdateTimes(new Date());
                        pvh.setPpid(p.getPpid());
                        pvh.setCompanyId(p.getComId());
                        pvh.setState(pv.getState());
                        pvh.setAddTimes(p.getSjdate());
                        pvh.setPrincipal(p.getStaffid());
                        priceid = pvh.getVipId();
                        baseBeansList.add(pvh);

                        for (int j = 0; j < psbList.size(); j++) {
                            PVipBroHistory pvbh = new PVipBroHistory();
                            PVipBrokerage pvb = (PVipBrokerage) psbList.get(j);
                            pvbh.setVipbroId(serverService.getServerID("vipbroId"));
                            pvbh.setBrokerage(pvb.getBrokerage());
                            pvbh.setPpid(p.getPpid());
                            pvbh.setTypePpid(pvb.getTypePpid());
                            pvbh.setCompanyId(pvb.getCompanyId());
                            pvbh.setVipId(pvh.getVipId());

                            baseBeansList.add(pvbh);
                        }
                    } else if (p.getPricestatus().equals("3") || p.getPricestatus().equals("4")) {
                        PActPrice pap = (PActPrice) beandao.getBeanByHqlAndParams("from PActPrice where actPriceId=?", new Object[]{p.getPriceid()});
                        List<BaseBean> psbList = beandao.getListBeanByHqlAndParams("from PActivityBrokerage where actPriceId=?", new Object[]{p.getPriceid()});

                        pap.setActPriceId(serverService.getServerID("actPriceId"));
                        pap.setActPriceKey(null);
                        pap.setActPrice(Double.valueOf(p.getRePrice()));
                        pap.setFactory(Double.valueOf(p.getEfPrice()));
                        pap.setState("01");
                        priceid = pap.getActPriceId();
                        baseBeansList.add(pap);

                        for (int j = 0; j < psbList.size(); j++) {
                            PActivityBrokerage pab = (PActivityBrokerage) psbList.get(j);
                            pab.setActivitybroId(serverService.getServerID("activitybroId"));
                            pab.setActivitybroKey(null);
                            pab.setActPriceId(pap.getActPriceId());

                            baseBeansList.add(pab);
                        }
                    }
                    gb.setPricetype(p.getPricestatus());
                    gb.setActivityID(priceid);
                    baseBeansList.add(gb);
                }
            }
        }
        if (baseBeansList != null && baseBeansList.size() > 0) {
            beandao.executeSqlsByParmsList(baseBeansList, null, null);
        }
    }

    private void pushMessage(TEshopCusCom cus, String goodsname) {
        try {

            msage.setMobiles(cus.getAccount());
            msage.setMessage("恭喜您已成功升级微分金会员，请重新登陆App！！！");
            msage.sendMsg("【微分金平台】");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //保存账号
        List<String> slist = new ArrayList<String>();//极光推送设备号
        slist = new ArrayList<String>();
        slist.add(cus.getAccount());
        //极光推送
        JushMain.sendjiguangMessage("恭喜您已成功升级微分金会员，请重新登陆App！！！", "会员", "homepage", "member", slist);
    }

    /**
     * 支付宝/银联支付发短信
     * 供应商
     */
    private void zfMessage(TEshopCusCom cus, String content, String type, String body, String id) {
        List<String> slist = new ArrayList<String>();//极光推送设备号
        String sql = "select t.contactway  from dt_hr_staff_contact t where t.staffid= ? and t.contactType in (select e.codeID from dtCCode e where  e.codevalue= ? and e.companyid = ?)";
        List list = beandao.getListBeanBySqlAndParams(sql, new Object[]{cus.getStaffid(), "短信提醒", cus.getCompanyId()});
        String cellphoneMark = "";
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String obj = list.get(i).toString();
                if (obj != null) {
                    cellphoneMark += obj + ",";
                }
            }
        }
        cellphoneMark += cus.getAccount();
        try {
            msage.setMobiles(cellphoneMark);
            msage.setMessage(content);
            msage.sendMsg("【数字地球】");
            //  System.out.println(cellphoneMark);
            //  System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //xgb
        // logger.error("生成订单-----提醒人账号:" + cus.getAccount() + "------公司下员工账号:" + cellphoneMark);
        //查询是否绑定终端机
        String jiguangMark = cellphoneMark;
        if (cus.getCompanyId() != null && !cus.getCompanyId().equals("")) {

            String hql = "from PosDevice p where p.accessCcomID = (select c.ccompanyId from CcomCom c where comanyId = ?)";

            List<BaseBean> poslist = beandao.getListBeanByHqlAndParams(hql, new Object[]{cus.getCompanyId()});
            if (poslist != null && poslist.size() > 0) {
                for (int i = 0; i < poslist.size(); i++) {
                    PosDevice obj = (PosDevice) poslist.get(i);
                    if (obj != null) {
                        if (i == 0) {
                            if (poslist.size() == 1) {
                                jiguangMark += "," + obj.getPosNum();
                            } else {
                                jiguangMark += "," + obj.getPosNum() + ",";
                            }
                        } else if (i == poslist.size() - 1) {
                            jiguangMark += obj.getPosNum();
                        } else {
                            jiguangMark += obj.getPosNum() + ",";
                        }
                    }
                }

            }
        }


        //保存账号
        String[] arr = jiguangMark.split(",");
        slist = Arrays.asList(arr);
        //极光推送
        if (content.indexOf("您有新的数字地球5L5C订单") != -1) {
            content = "您有新的数字地球5L5C订单，请及时处理!";
            //System.out.println(content);
        }
        JushMain.sendjiguangMessage(content, type, body, id, slist);

    }

    /**
     * 计时收费
     *
     * @param paySccid 交钱人的paySccid
     * @param colSccid 收钱的colSccid
     * @param money    需要支付的金额
     * @return 01扣金币成功，10扣积分成功，11扣钱不成功（出现异常），00金币和积分都不够
     */
    @Override
    public synchronized String timeCharge(String paySccid, String colSccid, BigDecimal money, String journalNum) {

        //付钱个人
        TEshopCusCom payCuscum = (TEshopCusCom) beandao
                .getBeanByHqlAndParams(" from TEshopCusCom where sccid= ?", new Object[]{paySccid});
        TEshopCustomer payCustomer = (TEshopCustomer) beandao
                .getBeanByHqlAndParams(" from TEshopCustomer where account= ? and logOff=0", new Object[]{payCuscum.getAccount()});
        String str = "";
        String isflag = "gold"; //标识扣得是金币还是积分，默认金币timeCharge
        BigDecimal morrer = money.multiply(new BigDecimal(100)); //金额换算为金币数或积分数
        //金币冻结的判断
        if (payCustomer.getStatus() != null && payCustomer.getStatus().equals("1")) {
            str = "金币账户冻结,不能付费";
        }
        //判断交钱人的金币是否足够付费
        //根据paySccid查询金币表
        WfjJifen payWfjGold = (WfjJifen) beandao.getBeanByHqlAndParams("from WfjJifen where sccid = ?", new Object[]{paySccid});
        if ("金币账户冻结,不能付费".equals(str) || payWfjGold == null || (payWfjGold != null && morrer.compareTo(new BigDecimal(payWfjGold.getWfjJifenScore())) > 0)) {
            //根据paySccid查询积分表
            BonusPoints bp = (BonusPoints) beandao.getBeanByHqlAndParams("from BonusPoints where sccid = ?", new Object[]{paySccid});
            if (bp == null || (bp != null && morrer.compareTo(new BigDecimal(bp.getBonusPointScore())) > 0)) {
                return "00";  //当积分或金币表不存在或金币数积分数不够时，返回“00”
            } else {
                isflag = "score"; //当金币数不够且积分数足够时扣积分数
            }
        }
        return inOutStock(isflag, paySccid, colSccid, "考场计时扣费", "考场计时收费", money, journalNum);

    }

    /**
     * 抽奖收费
     *
     * @param lotSccid 抽奖人的id
     * @param colSccid 被抽奖Id
     * @param money    每次抽奖支付的积分数
     * @return 10抽奖成功  11发生异常
     */
    @Override
    public String lotteryCharge(String lotSccid, String colSccid, BigDecimal money) {
        String result = inOutStock("score", lotSccid, colSccid, "积分抽奖付费", "积分抽奖收费", money, null);
        return result;
    }

    /**
     * 短信扣费
     *
     * @param lotSccid
     * @param colSccid
     * @param money
     * @return
     */
    @Override
    public String messageCharge(String lotSccid, String colSccid, BigDecimal money) {
        String result = inOutStock("score", lotSccid, colSccid, "短信扣费", "短信收费", money, null);
        return result;
    }

    /**
     * 积分或金币出入库
     *
     * @param isflag
     * @param paySccid
     * @param colSccid
     * @param guiZeNamePay
     * @param guiZeNameCol
     * @param money
     * @return 10积分 01金币 11发生异常
     */
    private String inOutStock(String isflag, String paySccid, String colSccid, String guiZeNamePay, String guiZeNameCol, BigDecimal money, String journalNum) {
        try {
            BigDecimal morrer = money.multiply(new BigDecimal(100)); //金额换算为金币数或积分数
            List<BaseBean> beans = new ArrayList<BaseBean>();

            //收钱方公司
            Object[] colCom = (Object[]) beandao.getObjectBySqlAndParams(
                    "select t.pseudo_company_name,t.companyId from T_ESHOP_CUSCOM t "
                            + "where t.state=2 connect by nocycle prior t.suppersccid = t.SCCID and rownum=1 start with t.SCCID = ?",
                    new Object[]{colSccid});
            String colComName = colCom[0].toString();
            String colComId = colCom[1].toString();

            //付钱方公司
            Object[] payCom = (Object[]) beandao.getObjectBySqlAndParams(
                    "select t.pseudo_company_name,t.companyId from T_ESHOP_CUSCOM t "
                            + "where t.state=2 connect by nocycle prior t.suppersccid = t.SCCID and rownum=1 start with t.SCCID = ?",
                    new Object[]{paySccid});
            String payComName = payCom[0].toString();
            String payComId = payCom[1].toString();

            //收钱方人
            TEshopCusCom collectCuscum = (TEshopCusCom) beandao
                    .getBeanByHqlAndParams(" from TEshopCusCom where sccid= ?", new Object[]{colSccid});
            String colStaId = collectCuscum.getStaffid();
            Staff colStaff = (Staff) beandao
                    .getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{colStaId});
            String colStaName = colStaff.getStaffName();

            //付钱个人
            TEshopCusCom payCuscum = (TEshopCusCom) beandao
                    .getBeanByHqlAndParams(" from TEshopCusCom where sccid= ?", new Object[]{paySccid});
            TEshopCustomer payCustomer = (TEshopCustomer) beandao
                    .getBeanByHqlAndParams(" from TEshopCustomer where account= ? and logOff=0", new Object[]{payCuscum.getAccount()});
            String payStaId = collectCuscum.getStaffid();
            Staff payStaff = (Staff) beandao
                    .getBeanByHqlAndParams("from Staff where staffID=? ", new Object[]{payStaId});
            String payStaName = payStaff.getStaffName();

            //出库单 出库
            CashierBills cb = new CashierBills();
            ProductPackaging manage = null;
            if ("score".equals(isflag)) {
                manage = (ProductPackaging) beandao
                        .getBeanByHqlAndParams("from ProductPackaging where goodsName=?", new Object[]{"微分金积分"});
                cb.setBillsType("积分出库单");
            } else {
                manage = (ProductPackaging) beandao
                        .getBeanByHqlAndParams("from ProductPackaging where goodsName=?", new Object[]{"微分金金币"});
                cb.setBillsType("金币出库单");
            }
            cb.setCashierBillsID(serverService.getServerID("CashierBills"));
            cb.setCashierDate(new Date());
            cb.setJournalNum(serverService.getBillID(payComId));
            cb.setCompanyID(payComId);
            cb.setCompanyName(payComName);
            cb.setInputid(payStaId);
            cb.setInputName(payStaName);
            cb.setCcompanyID(colComId);
            cb.setCcompanyName(colComName);
            cb.setStatus("16"); //已出库
            cb.setjNumOrder(journalNum);//订单编号进行关联
            if ("车辆计时扣费".equals(guiZeNamePay)) {
                cb.setAppstyle("15");//计时付费
                cb.setProID("008"); //计时付费
            }
            if ("短信扣费".equals(guiZeNamePay)) {
                cb.setAppstyle("17");//短信扣费
                cb.setProID("012"); //短信扣费
            } else {
                cb.setAppstyle("16");//积分抽奖
                cb.setProID("009"); //积分抽奖
            }
            cb.setStatusbill("04");  //与微分金相关
            cb.setPriceSub(money + "");
            cb.setInputName("系统生成");
            cb.setProjectName(guiZeNamePay);
            cb.setContactUserID(colStaId);
            cb.setCtUserName(colStaName);
            beans.add(cb);

            //物品单据
            GoodsBills gb = new GoodsBills();
            gb.setCashierBillsID(cb.getCashierBillsID());
            gb.setGoodsBillsID(serverService.getServerID("GoodsBills"));
            gb.setGoodsID(manage.getGoodsID());
            gb.setGoodsNum(manage.getGoodsNum());
            gb.setGoodsName(manage.getGoodsName());
            gb.setStandard(manage.getStandard());
            gb.setGoodsVariableID(manage.getVariableID());
            gb.setWeight(manage.getWeight());
            gb.setPrice("0.01");
            gb.setQuantity(morrer + "");


            gb.setKcStatus("16"); //已出库
            gb.setPpID(manage.getPpID());
            beans.add(gb);

            //入库单
            CashierBills cb1 = new CashierBills();
            if ("score".equals(isflag)) {
                cb1.setBillsType("积分入库单");
            } else {
                cb1.setBillsType("金币入库单");
            }
            cb1.setCashierBillsID(serverService.getServerID("CashierBills"));
            cb1.setCashierDate(new Date());
            cb1.setJournalNum(serverService.getBillID(colComId));
            cb1.setCompanyID(colComId);
            cb1.setCompanyName(colComName);
            cb1.setInputid(colStaId);
            cb1.setInputName(colStaName);
            cb1.setCcompanyID(payComId);
            cb1.setCcompanyName(payComName);
            cb1.setStatus("15"); //已入库
            cb1.setStatusbill("04");  //与微分金相关
            cb1.setjNumOrder(journalNum);//订单编号进行关联
            if ("车辆计时扣费".equals(guiZeNamePay)) {
                cb.setAppstyle("15");//计时付费
                cb.setProID("008"); //计时付费
            }
            if ("短信扣费".equals(guiZeNamePay)) {
                cb.setAppstyle("17");//短信扣费
                cb.setProID("012"); //短信扣费
            } else {
                cb1.setProID("009"); //积分抽奖
                cb1.setAppstyle("16");//积分抽奖
            }
            cb1.setInputName("系统生成");
            cb1.setProjectName(guiZeNameCol);
            cb1.setContactUserID(payStaId);
            cb1.setCtUserName(payStaName);
            cb1.setPriceSub(money + "");
            beans.add(cb1);

            GoodsBills gb1 = new GoodsBills();
            gb1 = (GoodsBills) gb.cloneGoodsBills();
            gb1.setGoodsBillsID(serverService.getServerID("GoodsBills"));
            gb1.setCashierBillsID(cb1.getCashierBillsID());
            gb1.setKcStatus("15");
            beans.add(gb1);

            //查询规则表
            WfjGuize payGuizeIM = (WfjGuize) beandao.getBeanByHqlAndParams
                    ("from WfjGuize where wfjGuizeName=? and wfjGuizeCalc=? ", new Object[]{guiZeNamePay, "M"});
            WfjGuize colGuizeIA = (WfjGuize) beandao.getBeanByHqlAndParams
                    ("from WfjGuize where wfjGuizeName=? and wfjGuizeCalc=? ", new Object[]{guiZeNameCol, "A"});

            if ("score".equals(isflag)) {
                //付钱方积分减少
                //付钱方积分表
                BonusPoints payBp = (BonusPoints) beandao.getBeanByHqlAndParams
                        ("from BonusPoints where sccid = ?", new Object[]{paySccid});
                BigDecimal payScore = new BigDecimal(payBp.getBonusPointScore());
                payBp.setBonusPointScore(payScore.subtract(morrer).toString());
                beans.add(payBp);
                //付钱方积分详细表添加一条记录
                BonusPointsDetail payBpd = new BonusPointsDetail();
                payBpd.setBonusPointsDetailId(serverService.getServerID("BonusPointsDetail"));
                payBpd.setBonusPointsDetailName(guiZeNamePay);
                payBpd.setWfjGuizeId(payGuizeIM.getWfjGuizeId());
                payBpd.setBonusPointsDetailScore(morrer + "");
                payBpd.setBonusPointsDetailDate(new Date());
                payBpd.setBonusPointsId(payBp.getBonusPointsId());
                payBpd.setWfjCashId(cb.getCashierBillsID());
                payBpd.setWfjGoodId(gb.getGoodsBillsID());
                payBpd.setPpid(manage.getPpID());
                beans.add(payBpd);

                //收钱方积分增加
                BonusPoints colBp = (BonusPoints) beandao.getBeanByHqlAndParams
                        ("from BonusPoints where sccid = ?", new Object[]{colSccid});
                //判断收钱方在积分总表中是否有数据
                if (colBp == null) //若无添加一条记录
                {
                    colBp = new BonusPoints();
                    colBp.setBonusPointsId(serverService.getServerID("BonusPoints"));
                    colBp.setSccid(colSccid);
                    colBp.setBonusPointScore(morrer + "");
                } else  //若有则改变积分值
                {
                    BigDecimal colScore = new BigDecimal(colBp.getBonusPointScore());
                    colBp.setBonusPointScore(colScore.add(morrer).toString());
                }
                beans.add(colBp);

                //收钱方积分详细表添加一条记录
                BonusPointsDetail colBpd = new BonusPointsDetail();
                colBpd.setBonusPointsDetailId(serverService.getServerID("BonusPointsDetail"));
                colBpd.setBonusPointsDetailName(guiZeNameCol);
                colBpd.setWfjGuizeId(colGuizeIA.getWfjGuizeId());
                colBpd.setBonusPointsDetailScore(morrer + "");
                colBpd.setBonusPointsDetailDate(new Date());
                colBpd.setBonusPointsId(colBp.getBonusPointsId());
                colBpd.setWfjCashId(cb1.getCashierBillsID());
                colBpd.setWfjGoodId(gb1.getGoodsBillsID());
                colBpd.setPpid(manage.getPpID());
                beans.add(colBpd);
            } else {
                //付钱方金币减少
                //付钱方金币表
                WfjJifen payWfjGold = (WfjJifen) beandao.getBeanByHqlAndParams("from WfjJifen where sccid = ?", new Object[]{paySccid});
                BigDecimal payGold = new BigDecimal(payWfjGold.getWfjJifenScore());
                payWfjGold.setWfjJifenScore(payGold.subtract(morrer).toString());
                beans.add(payWfjGold);
                //付钱方金币详细表添加一条记录
                WfjJifenDetail payWjd = new WfjJifenDetail();
                payWjd.setJifenDetailId(serverService.getServerID("WfjJifenDetail"));
                payWjd.setJifenDetailScore(morrer + "");
                payWjd.setWfjGuizeId(payGuizeIM.getWfjGuizeId());
                payWjd.setWfjJifenId(payWfjGold.getWfjJifenId());
                payWjd.setJifenDetailState(Integer.valueOf(0));
                payWjd.setJifenDetailName(guiZeNamePay);
                payWjd.setJifenDetailDate(new Date());
                payWjd.setWfjCashId(cb.getCashierBillsID());
                payWjd.setWfjGoodId(gb.getGoodsID());
                payWjd.setWfjGuizeCalc("M");
                beans.add(payWjd);
                //收钱方金币增加
                //收钱方金币总表
                WfjJifen colWfjGold = (WfjJifen) beandao.
                        getBeanByHqlAndParams("from WfjJifen where sccid = ?", new Object[]{colSccid});
                //判断收钱方在金币总表中是否有数据
                if (colWfjGold == null) {
                    colWfjGold = new WfjJifen();
                    colWfjGold.setWfjJifenId(serverService.getServerID("WfjJifen"));
                    colWfjGold.setSccid(colSccid);
                    colWfjGold.setStaffId(colStaId);
                    colWfjGold.setStaffName(colStaName);
                    colWfjGold.setWfjJifenScore(morrer + "");
                    colWfjGold.setWfjJifenState(new Integer(0));
                } else {
                    BigDecimal colGold = new BigDecimal(colWfjGold.getWfjJifenScore());
                    colWfjGold.setWfjJifenScore(colGold.add(morrer).toString());
                }
                beans.add(colWfjGold);
                //收钱方金币详细表添加一条记录
                WfjJifenDetail colWjd = new WfjJifenDetail();
                colWjd.setJifenDetailId(serverService.getServerID("WfjJifenDetail"));
                colWjd.setJifenDetailScore(morrer + "");
                colWjd.setWfjGuizeId(colGuizeIA.getWfjGuizeId());
                colWjd.setWfjJifenId(colWfjGold.getWfjJifenId());
                colWjd.setJifenDetailState(Integer.valueOf(0));
                colWjd.setJifenDetailName(guiZeNameCol);
                colWjd.setJifenDetailDate(new Date());
                colWjd.setWfjCashId(cb1.getCashierBillsID());
                colWjd.setWfjGoodId(gb1.getGoodsID());
                colWjd.setWfjGuizeCalc("A");
                beans.add(colWjd);
            }
            beandao.executeHqlsByParmsList(beans, null, null);
            if ("score".equals(isflag)) {
                return "10";
            } else {
                return "01";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "11"; //出现异常
        }
    }

    /**
     * 单产品生成订单
     *
     * @param ppid       购买的产品
     * @param morre      支付金额
     * @param sccid      用户信息
     * @param journalNum 订单号
     * @return
     */
    public String generateOrderSheet(String ppid, String morre, String sccid, String journalNum, String waiterID, String wfStatus4) throws Exception {

        CashierBills dd = (CashierBills) beandao.getBeanByHqlAndParams("from CashierBills where journalNum = ?",
                new String[]{journalNum});

        if (dd != null) {
            System.out.println("订单已生成,订单号:" + journalNum);
            return null;
        }

        //用户信息
        String scchql = "from TEshopCusCom where sccid = ?";
        TEshopCusCom scc = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams(scchql,
                        new Object[]{sccid});

        List<BaseBean> operator = new ArrayList<BaseBean>();


        ProductPackaging ppk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
                "from ProductPackaging where ppid=?", new String[]{ppid});


        CashierBills cb = new CashierBills();
        cb.setCashierBillsID(serverService.getServerID("CashierBills"));
        cb.setOrganizationID("organization20110425U539EJC3VF0000012237");
        cb.setCashierDate(new Date());
        cb.setStaffName("系统生成");
        cb.setInputName("系统生成");
        cb.setStatus("40");
        cb.setWfStatus2("00");
        cb.setFkStatus("01");//
        cb.setDepartmentName("客户");
        cb.setZctype("cg");

        cb.setProjectName(ppk.getGoodsName());
        cb.setPriceSub(morre);
        cb.setBillsType("项目收入预算单");
        cb.setWfStatus1("00");
        cb.setWaiterID(waiterID);
        cb.setWfStatus4(wfStatus4);

        String hql = "from Staff d where d.staffID=? ";
        Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
                new String[]{scc.getStaffid()});
        cb.setContactUserID(sf.getStaffID());
        cb.setCtUserName(sf.getStaffName());

        Company company1 = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{ppk.getCompanyID()});
        cb.setCompanyName(company1.getCompanyName());

        cb.setCompanyID(ppk.getCompanyID());
        cb.setJournalNum(journalNum);
        cb.setjNumOrder(cb.getJournalNum());
        cb.setStatusbill("04");

        CcomCom ccomCom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where comanyId = ?", new Object[]{ppk.getCompanyID()});
        cb.setCcompanyID(ccomCom.getCcompanyId());

        PayCashierBill payCashierBill = new PayCashierBill();
        payCashierBill.setPcbID(serverService.getServerID("pcbid"));
        payCashierBill.setOriJournalNum(cb.getJournalNum());
        payCashierBill.setPayJournalNum(cb.getJournalNum());

        ProSetup proSetup = (ProSetup) baseBeanService.getBeanByHqlAndParams("from ProSetup where comId = ? and ppid = ? and fcomId is null", new Object[]{ppk.getCompanyID(), ppid});

        String hqlinvt = "from DepotManage where companyID = ? and depotName= ?";

        DepotManage wh = (DepotManage) baseBeanService.getBeanByHqlAndParams(hqlinvt, new Object[]{ppk.getCompanyID(), "销售库"});

        // 订单地址关联
        DtOrderBillAdd dl = new DtOrderBillAdd();
        dl.setOaId(serverService.getServerID("DtOrderBillAdd"));
        dl.setOaComId(cb.getCompanyID());
        dl.setOaSccId(scc.getSccId());
        dl.setReceivename(sf.getStaffName());
        dl.setReceivetel(sf.getReference());
        dl.setShDate(new Date());

        //发货地址查询
        RefundAddress address = (RefundAddress) baseBeanService
                .getBeanByHqlAndParams("from RefundAddress where " +
                                "companyId=? and rtype=? and status=?",
                        new Object[]{ppk.getCompanyID(), 1, "00"});
        //发货信息
        if (address != null) {
            dl.setSendname(address.getRname());
            dl.setSendtel(address.getRphone());
            dl.setSendcode(address.getRpostcode());
            dl.setSendaddress(address.getRarea() + address.getRstreet());
            dl.setFhDate(new Date());
        }
        dl.setOaBillId(cb.getCashierBillsID());
        dl.setOaBillJounum(cb.getJournalNum());
        dl.setOaGysId(ppk.getCompanyID());
        dl.setXdDate(new Date());
        dl.setFkDate(new Date());


        GoodsBills gbs = new GoodsBills();
        gbs.setCashierBillsID(cb.getCashierBillsID());
        gbs.setGoodsBillsID(serverService.getServerID("GoodsBills"));
        gbs.setCompanyID(ppk.getCompanyID());
        gbs.setTypeID(ppk.getType());
        gbs.setDepotID(wh.getDepotID());
        gbs.setDepotName(wh.getDepotName());
        gbs.setStartDate(new Date());
        gbs.setMoney(morre);
        String q = Math.round(Float.parseFloat(morre) / Float.parseFloat(proSetup.getRePrice())) + "";

        //((int) (Float.parseFloat(morre) * 100)) + ""
        gbs.setQuantity(q);//数量
        gbs.setCostmoney(proSetup.getEfPrice());
        gbs.setPrice(proSetup.getRePrice());
        gbs.setRealMoney(morre);
        gbs.setGoodsID(ppk.getGoodsID());

        gbs.setGoodsName(ppk.getGoodsName());
        gbs.setStandard("默认规格");
        gbs.setPpID(ppid);
        gbs.setBoxStandard(ppk.getModel());
        gbs.setEntryTime(new Date());
        gbs.setGoodsNum(ppk.getProductCode());
        gbs.setActivityID(proSetup.getSuid());
        gbs.setPricetype("0");


        operator.add(cb);
        operator.add(gbs);
        operator.add(dl);
        operator.add(payCashierBill);

        baseBeanService.saveBeansListAndexecuteHqlsByParams(operator, null, null);


        if (ppk != null && ("金币计时".equals(ppk.getType()) || "包天计时".equals(ppk.getType()) || "包月计时".equals(ppk.getType()) || "包天计时".equals(ppk.getType()))) {
            setCarManage(journalNum, wfStatus4, scc, ppid);

        }


        return cb.getCashierBillsID();
    }

    /**
     * 修改订单状态
     *
     * @param cashierBillsID
     * @return
     */
    public void updateFkState(String cashierBillsID) {
        List<BaseBean> beanList = new ArrayList<BaseBean>();
        String hql = "update CashierBills set fkStatus = ? where cashierBillsID = ? and fkStatus = ?";
        DtOrderBillAdd billAdd = (DtOrderBillAdd) beandao
                .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{cashierBillsID});

        billAdd.setShDate(new Date());

        beanList.add(billAdd);
        try {
            StatusEntity status = SaveStatus(cashierBillsID, billAdd.getOaBillJounum(), "03", "06");

            beanList.add(status);
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beanList, new String[]{hql}, new Object[]{"03", cashierBillsID, "00"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付判断是否回调过
     *
     * @param journalNum
     * @return
     */
    public boolean isCalledPay(String journalNum) {


        PayCallRecord payCallRecord = (PayCallRecord) baseBeanService.getBeanByHqlAndParams("from PayCallRecord where journalNum = ?", new Object[]{journalNum});

        if (payCallRecord != null) {
            return false;
        }
        payCallRecord = new PayCallRecord();
        payCallRecord.setPcId(serverService.getServerID("pcId"));
        payCallRecord.setJournalNum(journalNum);
        baseBeanService.save(payCallRecord);


        return true;
    }

    /**
     * 扫码助手生成订单
     *
     * @param waiterID
     * @param companyID
     * @param morre
     * @param sccid
     * @param journalNum
     * @return
     */
    public String generateMuliProOrder(String waiterID, String companyID, String morre, String sccid, String journalNum, String coID) {
        System.out.println("generateMuliProOrder:" + sccid);
        //用户信息
        TEshopCusCom scc = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",
                        new Object[]{sccid});


        Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff d where d.staffID=?",
                new String[]{scc.getStaffid()});
        List<Object> beanList = null;
        StringBuilder sql = new StringBuilder();

        if (coID == null || coID.equals("")) {
            //当前公司人的购物车助手

            sql.append("select p.ppid,p.goodsname,p.goodsid,p.type,ps.ef_price,ps.re_price,p.companyid,p.productCode,p.model,c.itemNum,c.stardard,p.goodsNum");
            sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,DT_cart c");
            sql.append(" where  p.ppid=ps.ppid and p.ppid = c.pid");
            sql.append(" and ps.fcom_id is null and c.staff_id = ? and p.showweixin = ? and c.pos = ? and c.companyID = ?");

            beanList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
                    new Object[]{waiterID, "01", "1", companyID});
        } else {
            sql.append("select p.ppid,p.goodsname,p.goodsid,p.type,ps.ef_price,ps.re_price,p.companyid,p.productCode,p.model,c.quantity,c.standard,p.goodsNum");
            sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,dtClientOrderGoods c");
            sql.append(" where  p.ppid=ps.ppid and p.ppid = c.ppid");
            sql.append(" and ps.fcom_id is null and c.coID = ?");
            beanList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
                    new Object[]{coID});

        }

        String sqlccom = "select cc.ccompany_Id,c.companyname from DT_ccom_com cc,dtCompany c where cc.compnay_id=c.companyId and c.companyId = ?";
        CashierBills cb = null;
        List<BaseBean> operator = new ArrayList<BaseBean>();
        StringBuffer sb = new StringBuffer();

        Object[] objcom = (Object[]) baseBeanService.getObjectBySqlAndParams(sqlccom, new Object[]{companyID});
        //每个公司生成一个订单
        cb = new CashierBills();
        cb.setCashierBillsID(serverService.getServerID("CashierBills"));
        cb.setOrganizationID("organization20110425U539EJC3VF0000012237");//部门为什么写死了
        cb.setCashierDate(new Date());
        cb.setStaffName("系统生成");
        cb.setInputName("系统生成");
        cb.setStatus("40");
        cb.setWfStatus2("00");
        cb.setFkStatus("01");
        cb.setDepartmentName("客户");
        cb.setZctype("cg");
        cb.setWaiterID(waiterID);

        cb.setContactUserID(sf.getStaffID());
        cb.setCtUserName(sf.getStaffName());
        cb.setBillsType("项目收入预算单");
        cb.setWfStatus1("00");
        cb.setJournalNum(journalNum);
        cb.setjNumOrder(cb.getJournalNum());
        cb.setStatusbill("04");
        cb.setCcompanyID(objcom[0].toString());
        cb.setCompanyID(companyID);
        cb.setCompanyName(objcom[1].toString());
        cb.setRemark("");//买家

        //声明支付订单关联表
        PayCashierBill pb = new PayCashierBill();
        pb.setPcbID(serverService.getServerID("pcbid"));
        pb.setOriJournalNum(cb.getjNumOrder());
        pb.setPayJournalNum(cb.getjNumOrder());

        operator.add(pb);
        // 订单地址关联
        DtOrderBillAdd dl = new DtOrderBillAdd();
        dl.setOaId(serverService.getServerID("DtOrderBillAdd"));
        dl.setOaComId(cb.getCompanyID());
        dl.setOaSccId(scc.getSccId());
        //收货信息
        dl.setReceivename(sf.getStaffName());
        dl.setReceivetel(scc.getAccount());
        //发货地址查询
        RefundAddress address = (RefundAddress) baseBeanService
                .getBeanByHqlAndParams("from RefundAddress where " +
                                "companyId=? and rtype=? and status=?",
                        new Object[]{companyID, 1, "00"});


        if (address != null) {
            dl.setSendname(address.getRname());
            dl.setSendtel(address.getRphone());
            dl.setSendcode(address.getRpostcode());
            dl.setSendaddress(address.getRarea() + address.getRstreet());
        }
        dl.setOaBillId(cb.getCashierBillsID());
        dl.setOaBillJounum(cb.getJournalNum());
        dl.setOaGysId(companyID);
        DepotManage wh = (DepotManage) baseBeanService.getBeanByHqlAndParams("from DepotManage where companyID = ? and depotName= ?", new Object[]{companyID, "销售库"});
        String gname = "";
        for (int j = 0; j < beanList.size(); j++) {
            Object[] pros = (Object[]) beanList.get(j);

            GoodsBills gbs = new GoodsBills();
            gbs.setCashierBillsID(cb.getCashierBillsID());
            gbs.setGoodsBillsID(serverService.getServerID("GoodsBills"));
            gbs.setTypeID(pros[3] != null ? pros[3].toString() : null);
            gbs.setCompanyID(companyID);
            gbs.setDepotID(wh != null ? wh.getDepotID() : null);//销售库
            gbs.setDepotName(wh.getDepotName());
            gbs.setStartDate(new Date());
            gbs.setPrice(pros[5].toString());//零售价
            gbs.setQuantity(pros[9].toString());//数量前台传的
            gbs.setMoney(Float.parseFloat(gbs.getQuantity()) * Float.parseFloat(gbs.getPrice()) + "");//金额
            gbs.setRealMoney(gbs.getMoney());//金额
            gbs.setGoodsID(pros[2].toString());
            gbs.setGoodsName(pros[1].toString());
            gname += pros[1].toString() + ",";
            gbs.setStandard(pros[10] != null ? pros[10].toString() : "");
            gbs.setPpID(pros[0].toString());
            gbs.setBoxStandard(pros[8] != null ? pros[8].toString() : null);
            gbs.setEntryTime(new Date());
            gbs.setGoodsNum(pros[11] != null ? pros[11].toString() : null);
            gbs.setCostmoney(pros[4].toString());
            gbs.setPremiums("0");//是否是奖品  0或null:否  1:是
            operator.add(gbs);


        }
        cb.setPriceSub(morre + "");//计算总金额
        if (gname != null && gname.length() > 200) {
            cb.setProjectName(gname.substring(0, 200));//注意
        } else {
            cb.setProjectName(gname);//注意
        }
        operator.add(cb);
        operator.add(dl);


        ///清空购物车
        String hql = "delete from Cart c where c.pos = ? and c.companyId = ? and c.staffId = ?";
        Object[] params = new Object[]{"1", companyID, waiterID};
        List<Object[]> parmsList = new ArrayList<Object[]>();
        parmsList.add(params);


        try {
            baseBeanService.executeHqlsByParamsList(operator, new String[]{hql}, parmsList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cb.getCashierBillsID();

    }


    /**
     * 自助收银
     *
     * @param morre
     * @param sccid
     * @param journalNum
     * @return
     */
    public String generateSelfPayOrder(String morre, String sccid, String journalNum, String wfStatus4, String addressID) throws Exception {

        CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams("from  CashierBills where journalNum = ?", new Object[]{journalNum});

        if (cc != null) {
            if ("金币计时".equals(cc.getProjectName())) {
                setLcCash(wfStatus4, cc, sccid);
            }
            return cc.getCashierBillsID();
        }
        List<BaseBean> operator = new ArrayList<BaseBean>();
        if ("06".equals(wfStatus4)) {


            String hql = "from Staff s where staffid = (select e.staffId from Enroll e where e.enrollID = (select p.enrollID from  PayBackupBill p where p.journalNum = ?))";
            Staff fd = (Staff) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{journalNum});
            if (fd != null) {
                //存储下cashCompany sccid
                String hqlbb = "from PayBackupBill where journalNum = ?";
                PayBackupBill blb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hqlbb, new Object[]{journalNum});
                blb.setCashCompany(sccid);
                operator.add(blb);
                sccid = fd.getSccid();
            }
        }
        //  wfStatus4 = "05";
        //用户信息
        TEshopCusCom scc = (TEshopCusCom) baseBeanService
                .getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",
                        new Object[]{sccid});


        Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff d where d.staffID=?",
                new String[]{scc.getStaffid()});
        List<Object> beanList = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select p.ppid,p.goodsname,p.goodsid,p.type,ps.ef_price,c.price,p.companyid,p.productCode,p.model,c.itemNum,p.goodsNum,c.stardard,c.sendNum,c.journalNum,c.posNum,c.costmoney,c.pritype,c.activityID,ps.suid,c.deptId,c.deptName");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,dt_selfcart c");
        sql.append(" where  p.ppid=ps.ppid and p.ppid = c.pid");
        sql.append(" and ps.fcom_id is null and (c.journalNum = ? or (c.payjournalNum = ? and c.payjournalNum!=c.journalNum))");
        beanList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
                new Object[]{journalNum, journalNum});

        List<String> comlist = new ArrayList<String>();
        List<String> cashiIDlist = new ArrayList<String>();


        String sqlccom = "select cc.ccompany_Id,c.companyname from DT_ccom_com cc,dtCompany c where cc.compnay_id=c.companyId and c.companyId = ?";

        CashierBills cb = null;

        for (int b = 0; b < beanList.size(); b++) {
            Object[] bbs = (Object[]) beanList.get(b);
            String companyID = bbs[6].toString();
            if (!comlist.contains(companyID)) {
                comlist.add(companyID);
                cashiIDlist.add(bbs[13].toString());
            }
        }

        for (int b = 0; b < comlist.size(); b++) {

            StringBuffer sb = new StringBuffer();

            Object[] objcom = (Object[]) baseBeanService.getObjectBySqlAndParams(sqlccom, new Object[]{comlist.get(b)});
            cb = new CashierBills();
            cb.setCashierBillsID(serverService.getServerID("CashierBills"));


            cb.setOrganizationID("organization20110425U539EJC3VF0000012237");//部门为什么写死了
            cb.setNopush("00");//暂时都不要推送
            cb.setCashierDate(new Date());
            cb.setStaffName("系统生成");
            cb.setInputName("系统生成");
            cb.setStatus("40");
            cb.setWfStatus2("00");
            cb.setWfStatus4(wfStatus4);
            cb.setFkStatus("01");
            cb.setDepartmentName("客户");
            cb.setZctype("cg");

            cb.setContactUserID(sf.getStaffID());
            String hqlbk = "from PayBackupBill where journalNum = ?";
            PayBackupBill bl = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hqlbk, new Object[]{journalNum});
            if (bl != null) {
                if (bl.getNoviceName() != null && !bl.getNoviceName().equals("")) {
                    cb.setCtUserName(bl.getNoviceName());
                    cb.setReference(bl.getNovicePhone());
                    cb.setStaffIdentityCard(bl.getNoviceCode());
                    cb.setReferrerAddress(bl.getNoviceAddress());
                }
                if (!"".equals(bl.getEnrollID()) && bl.getEnrollID() != null && !"null".equals(bl.getEnrollID())) {
                    Enroll enroll = (Enroll) baseBeanService.getBeanByHqlAndParams("from Enroll e where e.enrollID = ? ", new Object[]{bl.getEnrollID()});
                    enroll.setCashierBillsID(cb.getCashierBillsID());
                    operator.add(enroll);
                    enrollsService.generateAccount(scc.getAccount(), comlist.get(b), enroll.getCoachStaffID());
                }
                if (bl.getPrivateRoom() != null && !bl.getPrivateRoom().equals("")) {
                    cb.setPrivateRoom(bl.getPrivateRoom());
                }
                if (bl.getRemark() != null && !bl.getRemark().equals("")) {
                    cb.setRemark(bl.getRemark());
                }

                if (bl.getPtppid() != null && bl.getPtppid().length() > 0) {
                    String[] ppidlists = bl.getPtppid().split(",");
                    String[] ptstandards = new String[3];
                    if (bl.getPtstandard() != null && !bl.getPtstandard().equals(",,,,")) {
                        ptstandards = bl.getPtstandard().split(",,");
                    }
                    StringBuffer csql = new StringBuffer();
                    List<Object> params = new ArrayList<Object>();
                    List<Object> tempp = new ArrayList<Object>();
                    csql.append("select pp.companyid,pp.goodsname,ps.ef_price from dt_productpackaging pp,dt_pro_setup ps");
                    csql.append(" where ps.ppid=pp.ppid and pp.ppid in(");
                    StringBuffer temp = new StringBuffer();
                    temp.append(" order by case pp.ppid ");
                    PromotionAssociation pa = null;
                    for (int i = 0; i < ppidlists.length; i++) {
                        if (i == ppidlists.length - 1) {
                            csql.append("?");
                        } else {
                            csql.append("?,");
                        }
                        temp.append("when ? then '" + i + "'");
                        params.add(ppidlists[i]);
                        tempp.add(ppidlists[i]);
                    }
                    csql.append(")");
                    temp.append(" else pp.ppid end");
                    csql.append(temp);
                    params.addAll(tempp);
                    List<BaseBean> clist = new ArrayList<BaseBean>();
                    clist = baseBeanService.getListBeanBySqlAndParams(csql.toString(), params.toArray(new Object[]{}));
                    for (int i = 0; i < ppidlists.length; i++) {
                        Object o = clist.get(i);
                        Object[] objc = (Object[]) o;
                        pa = new PromotionAssociation();
                        pa.setPaId(serverService.getServerID("paId"));
                        pa.setCashierBillsID(cb.getCashierBillsID());
                        pa.setCompanyId(objc[0].toString());
                        pa.setPpId(ppidlists[i]);
                        pa.setCreatDate(new Date());
                        pa.setPpName(objc[1].toString());
                        pa.setPrice(objc[2].toString());
                        pa.setStandard(ptstandards[i]);
                        pa.setCount(1);
                        operator.add(pa);
                    }
                }
            }
            if (cb.getCtUserName() == null || "".equals(cb.getCtUserName())) {
                cb.setCtUserName(sf.getStaffName());
            }
            cb.setBillsType("项目收入预算单");
            cb.setWfStatus1("00");
            cb.setJournalNum(cashiIDlist.get(b));
            cb.setjNumOrder(cb.getJournalNum());
            cb.setStatusbill("04");
            cb.setCcompanyID(objcom[0].toString());
            cb.setCompanyID(comlist.get(b));
            cb.setCompanyName(objcom[1].toString());


            //声明支付订单关联表
            PayCashierBill pb = new PayCashierBill();
            pb.setPcbID(serverService.getServerID("pcbid"));
            pb.setOriJournalNum(cb.getjNumOrder());
            pb.setPayJournalNum(journalNum);

            operator.add(pb);
            // 订单地址关联
            DtOrderBillAdd dl = new DtOrderBillAdd();
            dl.setOaId(serverService.getServerID("DtOrderBillAdd"));
            dl.setOaComId(cb.getCompanyID());
            dl.setOaSccId(scc.getSccId());
            if (addressID != null && !addressID.equals("")) {
                StaffAddress sa = (StaffAddress) baseBeanService.getBeanByHqlAndParams("from StaffAddress where addressID = ?", new Object[]{addressID});
                if (sa != null) {
                    dl.setReceivename(sa.getConsignee());
                    dl.setReceivetel(sa.getPhone());
                    dl.setReceiveaddress(sa.getArea().replaceAll(",", "") + sa.getAddressDetailed());
                } else {
                    //收货信息
                    dl.setReceivename(sf.getStaffName());
                    dl.setReceivetel(sf.getReference());
                    dl.setReceiveaddress("四川省成都市锦江区督苑府");
                }
            }

            if (bl != null && bl.getNoviceName() != null && bl.getNoviceName() != "") {

                dl.setReceivename(bl.getNoviceName());
                dl.setReceivetel(bl.getNovicePhone());
                dl.setReceiveaddress(bl.getNoviceAddress());
            }
            //发货地址查询
            RefundAddress address = (RefundAddress) baseBeanService
                    .getBeanByHqlAndParams("from RefundAddress where " +
                                    "companyId=? and rtype=? and status=?",
                            new Object[]{comlist.get(b), 1, "00"});


            if (address != null) {
                dl.setSendname(address.getRname());
                dl.setSendtel(address.getRphone());
                dl.setSendcode(address.getRpostcode());
                dl.setSendaddress(address.getRarea() + address.getRstreet());
            }
            dl.setOaBillId(cb.getCashierBillsID());
            dl.setOaBillJounum(cb.getJournalNum());
            dl.setOaGysId(comlist.get(b));
            DepotManage wh = (DepotManage) baseBeanService.getBeanByHqlAndParams("from DepotManage where companyID = ? and depotName= ?", new Object[]{comlist.get(b), "销售库"});
             List<String> listsc = new ArrayList<String>();
            String gname = "";
            for (int j = 0; j < beanList.size(); j++) {
                Object[] pros = (Object[]) beanList.get(j);
                if (!pros[6].toString().equals(comlist.get(b))) {
                    continue;
                }
                GoodsBills gbs = new GoodsBills();

                if(pros[19]!=null&&!pros[19].toString().equals("")) {

                    if (!listsc.contains(pros[0].toString())) {
                        listsc.add(pros[0].toString());
                        float isnum = 0f;
                        for (int kk = 0;kk<beanList.size();kk++){

                            Object[] pros2 = (Object[]) beanList.get(kk);
                            if(pros[0].toString().equals(pros2[0].toString())){
                                  isnum+=Float.parseFloat(pros2[9].toString());
                            }

                        }

                        gbs.setQuantity(String.valueOf(isnum));
                    }else{
                        continue;
                    }

                }else{
                    gbs.setQuantity(pros[9].toString());//数量前台传的
                }

                gbs.setCashierBillsID(cb.getCashierBillsID());
                gbs.setGoodsBillsID(serverService.getServerID("GoodsBills"));
                gbs.setTypeID(pros[3] != null ? pros[3].toString() : null);
                gbs.setCompanyID(comlist.get(b));


                if(pros[19]!=null&&!pros[19].toString().equals("")){
                    gbs.setDepotID("");
                    gbs.setDepotName("智能货柜");
                }else{
                    gbs.setDepotID(wh != null ? wh.getDepotID() : null);//销售库
                    gbs.setDepotName(wh.getDepotName());
                }
                gbs.setStartDate(new Date());
                gbs.setPrice(pros[5].toString());//零售价

                gbs.setMoney(Float.parseFloat(gbs.getQuantity()) * Float.parseFloat(gbs.getPrice()) + "");//金额
                gbs.setRealMoney(gbs.getMoney());//金额
                gbs.setGoodsID(pros[2].toString());
                gbs.setGoodsName(pros[1].toString());
                gname += pros[1].toString() + ",";
                gbs.setStandard(pros[11] != null ? pros[11].toString() : "");
                gbs.setPpID(pros[0].toString());
                gbs.setBoxStandard(pros[8] != null ? pros[8].toString() : null);
                gbs.setEntryTime(new Date());
                gbs.setGoodsNum(pros[10] != null ? pros[10].toString() : null);
                gbs.setCostmoney(pros[15] != null ? pros[15].toString() : pros[4].toString());
                gbs.setPricetype(pros[16] != null ? pros[16].toString() : "0");
                gbs.setActivityID(pros[17] != null ? pros[17].toString() : pros[18].toString());
                gbs.setPremiums("0");//是否是奖品  0或null:否  1:是

                operator.add(gbs);
                if (pros[14] != null && !pros[14].equals("")) {
                    operator = setProAss(pros[0].toString(), pros[14].toString(), cb.getCashierBillsID(), operator);
                }


            }
            cb.setPriceSub(morre + "");//计算总金额
            if (gname != null && gname.length() > 200) {
                cb.setProjectName(gname.substring(0, 200));//注意
            } else {
                cb.setProjectName(gname);//注意
            }
            operator.add(cb);
            operator.add(dl);


//       ///清空selfCart
//        String hql = "delete from SelfCart c where journalNum = ?";
//        Object[] params = new Object[]{journalNum};
//        List<Object[]> parmsList = new ArrayList<Object[]>();
//        parmsList.add(params);


        }
        try {
            List<String> hqls = new ArrayList<String>();
            List<Object[]> parms = new ArrayList<Object[]>();

            String hqlpos = "from PosDevice cc where cc.posNum = (select max(f.posNum) from SelfCart f where f.journalNum=?)";
            PosDevice pos = (PosDevice) baseBeanService.getBeanByHqlAndParams(hqlpos, new Object[]{journalNum});

            if (pos != null) {
                cb.setPosNum(pos.getPosNum());
                String hqldelete = "delete from  SqSelfCart where posNum = ?";
                hqls.add(hqldelete);
                parms.add(new Object[]{pos.getPosNum()});

                hqls.add("delete from CartShopPromotion where posNum = ?");
                parms.add(new Object[]{pos.getPosNum()});
            }

            String hqld = "delete ScanGoodsRecord s where s.relateID = (select max(c.relateID) from SelfCart c where payjournalNum = ?)";
            hqls.add(hqld);
            parms.add(new Object[]{journalNum});


            baseBeanService.executeHqlsByParamsList(operator, hqls.toArray(new String[]{}), parms);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cb.getCashierBillsID();

    }

    /**
     * 打赏生成订单
     *
     * @param rd
     * @return
     * @throws Exception
     */
    @Override
    public String rewardOrder(Rewarddetail rd) throws Exception {
        List<BaseBean> operator = new ArrayList<BaseBean>();
        CashierBills cb = new CashierBills();
        ProductPackaging p2 = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
                "from ProductPackaging where ppid=?", new String[]{rd.getPpid()});

        ProductPackaging ppk = new ProductPackaging();
        ppk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
                "from ProductPackaging where type=? and goodsname=?", new String[]{"预设", p2.getType()});

        StringBuilder cushql = new StringBuilder(
                "SELECT t.staffid, t.companyid,t.pseudo_company_name,t.sccid,t.account");
        cushql.append(" FROM T_ESHOP_CUSCOM t where t.state=? and rownum=? START WITH t.sccid= ?");
        cushql.append(" CONNECT BY PRIOR t.suppersccid=t.sccid");

        Object tec = baseBeanService.getObjectBySqlAndParams(cushql.toString(), new Object[]{"2", 1, rd.getFbSccid()});

        Object[] tecs = (Object[]) tec;

        //发货地址查询
        RefundAddress address = (RefundAddress) baseBeanService
                .getBeanByHqlAndParams("from RefundAddress where " +
                                "companyId=? and rtype=? and status=?",
                        new Object[]{tecs[1], 1, "00"});

        cb.setCashierBillsID(serverService.getServerID("CashierBills"));
        cb.setOrganizationID("organization20110425U539EJC3VF0000012237");
        cb.setCashierDate(new Date());
        cb.setStaffName("系统生成");
        cb.setInputName("系统生成");
        cb.setStatus("40");
        cb.setWfStatus2("00");
        cb.setFkStatus("01");
        cb.setWfStatus4(rd.getPayway());
        cb.setProID("011");
        cb.setProjectName("打赏");
        cb.setDepartmentName("客户");
        cb.setZctype("cg");

        String hql = "from Staff d where d.staffID=? ";
        Staff sf = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
                new String[]{tecs[0].toString()});
        cb.setContactUserID(sf.getStaffID());
        cb.setCtUserName(sf.getStaffName());
        cb.setPriceSub(rd.getRdmoney());
        cb.setBillsType("项目收入预算单");
        cb.setWfStatus1("00");
        cb.setCompanyName(tecs[2].toString());
        cb.setCompanyID(tecs[1].toString());
        cb.setJournalNum(rd.getOrdernum());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(cb.getJournalNum());
        cb.setjNumOrder(cb.getJournalNum());
        cb.setStatusbill("04");

        PayCashierBill payCashierBill = new PayCashierBill();
        payCashierBill.setPcbID(serverService.getServerID("pcbid"));
        payCashierBill.setOriJournalNum(cb.getJournalNum());
        payCashierBill.setPayJournalNum(cb.getJournalNum());


        String hqlinvt = "from DepotManage where companyID = ? and depotName= ?";

        DepotManage wh = (DepotManage) baseBeanService.getBeanByHqlAndParams(hqlinvt, new Object[]{tecs[1], "销售库"});

        // 订单地址关联
        DtOrderBillAdd dl = new DtOrderBillAdd();
        dl.setOaId(serverService.getServerID("DtOrderBillAdd"));
        dl.setOaComId(cb.getCompanyID());
        dl.setOaSccId(rd.getRdSccid());

        dl.setOaBillId(cb.getCashierBillsID());
        dl.setOaBillJounum(cb.getJournalNum());
        dl.setOaGysId(tecs[1].toString());
        dl.setXdDate(new Date());
        dl.setFkDate(new Date());

        StringBuilder sql = new StringBuilder("SELECT S.RE_PRICE,S.EF_PRICE,S.SUID FROM DT_PRO_SETUP S WHERE S.PPID = ? AND S.COM_ID = ?");
        Object[] goodparm = new Object[]{ppk.getPpID(), ppk.getCompanyID()};

        Object pro_su = baseBeanService.getObjectBySqlAndParams(sql.toString(), goodparm);
        Object[] pro_sus = (Object[]) pro_su;
        String hysql = "select cc.industrytype from dtcontactcompany cc,dt_ccom_com ccc where cc.ccompanyid=ccc.ccompany_id and ccc.compnay_id=?";

        Object hy = baseBeanService.getObjectBySqlAndParams(hysql, new Object[]{tecs[1]});
        SetSubsidize setSubsidize = (SetSubsidize) baseBeanService.getBeanByHqlAndParams("from SetSubsidize where gtid=? and stutas=?", new Object[]{hy, "01"});
        BigDecimal bl = BigDecimal.ONE;
        if (setSubsidize != null) {
            //消费红包比例
            bl = bl.add(new BigDecimal(setSubsidize.getTotalPct()).divide(new BigDecimal(100)));
        }
        //系统售价
        BigDecimal b = new BigDecimal(pro_sus[0].toString()).multiply(bl);

        //数量
        BigDecimal q = new BigDecimal(rd.getRdmoney()).divide(b, 2, BigDecimal.ROUND_DOWN);

        StringBuilder xfhbsql = new StringBuilder();

        GoodsBills gbs = new GoodsBills();
        gbs.setCashierBillsID(cb.getCashierBillsID());
        gbs.setGoodsBillsID(serverService.getServerID("GoodsBills"));
        gbs.setCompanyID(tecs[1].toString());
        gbs.setTypeID(ppk.getType());
        gbs.setDepotID(wh.getDepotID());
        gbs.setDepotName(wh.getDepotName());
        gbs.setStartDate(new Date());
        gbs.setMoney(rd.getRdmoney());
        gbs.setQuantity(q.toString());
        gbs.setPrice(b.toString());
        gbs.setRealMoney(rd.getRdmoney());
        gbs.setGoodsID(ppk.getGoodsID());
        gbs.setGoodsName("打赏");
        gbs.setStandard(ppk.getStandard());
        gbs.setPpID(ppk.getPpID());
        gbs.setBoxStandard(ppk.getModel());
        gbs.setEntryTime(new Date());
        gbs.setTypeID(ppk.getType());
        gbs.setGoodsNum(ppk.getProductCode());
        gbs.setPricetype("0");//价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动
        gbs.setActivityID(pro_sus[2].toString());
        gbs.setCostmoney(pro_sus[1].toString());

        operator.add(cb);
        operator.add(gbs);
        operator.add(dl);
        operator.add(payCashierBill);

        baseBeanService.saveBeansListAndexecuteHqlsByParams(operator, null, null);

        return cb.getCashierBillsID();

    }

    /**
     * 修改打赏状态
     *
     * @param rd
     * @param wfStatus4
     * @param trade_no
     * @throws Exception
     */
    @Override
    public void addReward(Rewarddetail rd, String wfStatus4, String trade_no) throws Exception {
        rd.setPayway(wfStatus4);
        rd.setTradeStatus("01");
        rd.setTradeNo(trade_no);
        baseBeanService.update(rd);
    }

    /**
     * 查询打赏列表
     *
     * @param ppid 打赏新闻id
     * @throws Exception
     */
    @Override
    public List<Object> getReward(String ppid) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select t.rdid,t.rdmoney,s.staffname,headimage");
        stringBuffer.append(" from DTREWARDDETAIL t,t_eshop_cuscom e,dt_hr_staff s");
        stringBuffer.append(" where t.rd_sccid=e.sccid and e.staffid=s.staffid");
        stringBuffer.append(" and t.ppid=? and t.trade_status=?");
        List<Object> rList = beandao.getListObjectBySqlAndParams(stringBuffer.toString(), new Object[]{ppid, "01"});
        return rList;
    }

    /**
     * 购物车情况生成促销品，订单关联
     *
     * @return
     */
    private List<BaseBean> setProAss(String pid, String posNum, String cashierBillsID, List<BaseBean> operator) {

        //处理促销品
        String sqlcart = "select s.ppId,s.pptId,s.ptstandard,p.companyId,p.goodsname,p.price,s.ptCount from DT_CartShopPromotion s left join DT_sqselfCart c on s.cartId = c.sqid left join dt_ProductPackaging p on p.ppID = s.pptId where c.pid = ? and c.posNum = ?";

        List<Object> clist = baseBeanService.getListBeanBySqlAndParams(sqlcart, new Object[]{pid, posNum});
        PromotionAssociation pa = null;
        for (int k = 0; k < clist.size(); k++) {
            Object o = clist.get(k);
            Object[] objc = (Object[]) o;
            pa = new PromotionAssociation();
            pa.setPaId(serverService.getServerID("paId"));
            pa.setCashierBillsID(cashierBillsID);
            pa.setCompanyId(objc[3].toString());
            pa.setPpId(objc[1].toString());
            pa.setCreatDate(new Date());
            pa.setPpName(objc[4].toString());
            pa.setPrice(objc[5].toString());
            pa.setStandard(objc[2].toString());
            pa.setCount(Integer.parseInt(objc[6].toString()));
            operator.add(pa);
        }

        return operator;
    }

    /**
     * 非空验证
     *
     * @param tep
     * @return
     */
    private Object isNull(Object tep) {
        if (tep == null || "null".equals(tep)) {
            return "";
        } else {
            return tep;
        }
    }

    /**
     * 餐饮特别推送
     *
     * @param cashierBills
     * @param mealNum
     * @param gList
     * @param nowDate
     * @param billAdd
     * @param wfStatus4
     * @param com
     * @param tsc
     */
    private void getCaiYinXiangQing(CashierBills cashierBills, String mealNum, List<BaseBean> gList, Date nowDate, DtOrderBillAdd billAdd, String wfStatus4, TEshopCusCom com, TEshopCusCom tsc) {
        try {

            String attachtype = getAttachType(cashierBills.getJournalNum());
            if ("".equals(attachtype) || (attachtype != null && attachtype.equals("gsmzs")) || "selfpay".equals(attachtype)) {
                JSONObject json = new JSONObject();
                List<JSONObject> goodsList = new ArrayList<>();

                for (int i = 0; i < gList.size(); i++) {
                    GoodsBills goodsBills = (GoodsBills) gList.get(i);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("shuliang", isNull(goodsBills.getQuantity()));
                    jsonObject.accumulate("price", isNull(goodsBills.getPrice()));
                    String standard = (goodsBills.getStandard() == null || goodsBills.getStandard().equals("") || goodsBills.getStandard().equals("默认规格")) ? "" : "(" + goodsBills.getStandard() + ")";
                    jsonObject.accumulate("goodsname", isNull(goodsBills.getGoodsName()) + standard);
                    goodsList.add(jsonObject);
                }
                json.accumulate("TotalPrice", cashierBills.getPriceSub());
                json.accumulate("companyName", cashierBills.getCompanyName());
                json.accumulate("goodsList", goodsList);
                //按指定格式生成当前时间

                SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                json.accumulate("nowDate", dateFormats.format(nowDate));//付款时间
                json.accumulate("danhao", cashierBills.getjNumOrder());//订单号
                json.accumulate("beizhu", cashierBills.getRemark());
                json.accumulate("payType", wfStatus4 == "00" ? "微信" : wfStatus4 == "01" ? "支付宝" : wfStatus4 == "02" ? "银联" : "其它");

                json.accumulate("mealNum", mealNum);//取餐号
//            cashierBills.setMealNum(mealNum);
                if (cashierBills.getPrivateRoom() != null && !cashierBills.getPrivateRoom().equals("")) {
                    json.accumulate("eatType", "堂食");
                    json.accumulate("zhuohao", cashierBills.getPrivateRoom());

                } else {


                    //收货人信息
                    if (billAdd.getReceiveaddress() != null) {
                        json.accumulate("eatType", "外卖");
                        json.accumulate("receiveaddress", billAdd.getReceiveaddress() + "\n\r" + billAdd.getReceivename() + " " + billAdd.getReceivetel());
                    }

                }

                System.out.println(json);
                //  logger.error("body" + json.toString());

                zfMessage(com, "您有一笔新的数字地球5L5C订单，请及时处理!", "餐饮订单", json.toString(), "canyin");
                // logger.error("cashierBills.getWfStatus1()：" + cashierBills.getWfStatus1());
                //   logger.error("tsc.getOpenId()" + tsc.getOpenId());
                if (cashierBills.getWfStatus1() != null && cashierBills.getWfStatus1().equals("00")) {
                    if (tsc != null && tsc.getOpenId() != null && !tsc.getOpenId().equals("")) {
                        //给推送微信通知
                        logger.error("tsc.getOpenId()" + tsc.getOpenId());
                        pushWechatMessage(tsc.getOpenId(), cashierBills, nowDate, tsc.getSccId());
                    }


                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成取餐顺序号
     *
     * @param companyID
     * @return
     */
    public String searchMealNum(String companyID, CashierBills cc) {
        List<BaseBean> beanList = new ArrayList<BaseBean>();
        String ss = null;
        try {
            String hql = "from MealNum where companyID = ?";
            MealNum mealNum = (MealNum) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyID});
            int num = 0;
            if (mealNum == null) {
                num = num + 1;
                mealNum = new MealNum();
                mealNum.setMnId(serverService.getServerID("mnId"));
                mealNum.setCompanyID(companyID);
                mealNum.setMealNum(num);
            } else {
                num = mealNum.getMealNum() + 1;
                mealNum.setMealNum(num);
            }
            beanList.add(mealNum);
            DecimalFormat form = new DecimalFormat("0000");
            ss = form.format(num);
            cc.setMealNum(ss);
            beanList.add(cc);
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beanList, null, null);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return ss;
    }

    public String genMealNum(String ddid) {
        try {
            String hqls = "from CashierBills where journalNum = ?";
            CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams(hqls, new Object[]{ddid});
            if (cc == null || cc.getPrivateRoom() == null || cc.getPrivateRoom().equals("")) {
                return "-1";  ///不是餐厅就餐
            }


            if (cc.getMealNum() != null && !cc.getMealNum().equals("")) {

                return cc.getMealNum();
            } else {

                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "-1";
        }

    }


    /**
     * 获取是哪个位置的收款
     *
     * @return
     */
    private String getAttachType(String journalNum) {
        String hql = "from PayBackupBill where journalNum = ?";
        String attach = "";
        PayBackupBill payBackupBill = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{journalNum});
        if (payBackupBill != null) {
            attach = payBackupBill.getAttach();
            if (attach == null) {
                attach = "";
            }
        }
        return attach;
    }

    /**
     * 餐饮推送通知微信
     *
     * @param openid
     * @param sccid
     */
    private void pushWechatMessage(String openid, CashierBills dd, Date skDate, String sccid) {
        //  logger.error("openid：" + openid);
        //   logger.error("sccid：" + sccid);
        try {
            TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();

            //根据具体模板参数组装

            params.put("first", TemplateMsg.item("您的取餐码是 【" + dd.getMealNum() + "】", "#000000"));
            params.put("keyword1", TemplateMsg.item(dd.getCompanyName(), "#000000"));
            params.put("keyword2", TemplateMsg.item(dd.getPrivateRoom() != null ? "堂食" : "外卖", "#000000"));
            params.put("keyword3", TemplateMsg.item(dd.getMealNum(), "#000000"));

            SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            params.put("keyword4", TemplateMsg.item(dateFormats.format(skDate), "#000000"));
            params.put("keyword5", TemplateMsg.item(dd.getPriceSub(), "#000000"));
            params.put("remark", TemplateMsg.item("欢迎下载微分金APP，查询订单、兑换现金、买卖产品更方便！", "#000000"));

            TemplateMsg wechatTemplateMsg = new TemplateMsg();
            wechatTemplateMsg.setTemplate_id(ConstantURL.canyin_temp_id);
            wechatTemplateMsg.setTouser(openid);//openID
            wechatTemplateMsg.setUrl("http://www.impf2010.com/ea/pobuy/ea_getPhoneOrdersList.jspa?staid=" + dd.getContactUserID() + "&sccId=" + sccid);
            wechatTemplateMsg.setData(params);


            String hql = "from WeChatToken where appID = ?";
            WeChatToken wt = (WeChatToken) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"wxa1b3f84c027804c3"});
            String accessTokens = "";
            if (wt != null && wt.getAccessToken() != null && !wt.getAccessToken().equals("")) {
                accessTokens = wt.getAccessToken();

            } else {
                AccessToken accessToken = WeixinUtil.getAccessToken("wxa1b3f84c027804c3", "26d569353c295fa8ad4fcb85a199f631");
                accessTokens = accessToken.getToken();

            }
            TemplateMsgResult templateMsgResult = WeixinUtil.sendTemplate(accessTokens, wechatTemplateMsg);
            if (templateMsgResult != null) {
                // System.out.println(templateMsgResult.getErrcode() + templateMsgResult.getErrmsg() + templateMsgResult.getMsgid());
                //  logger.error("tmsg:" + templateMsgResult.getErrcode() + templateMsgResult.getErrmsg() + templateMsgResult.getMsgid());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 推送通知微信
     *
     * @param openid
     * @param sccid
     */
    private void pushWechat(String openid, CashierBills dd, Date skDate, String sccid) {
        try {
            TreeMap<String, TreeMap<String, String>> params = new TreeMap<String, TreeMap<String, String>>();

            //根据具体模板参数组装

            params.put("first", TemplateMsg.item("您好，您已消费成功！", "#000000"));
            params.put("keyword1", TemplateMsg.item(dd.getPriceSub(), "#000000"));
            SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            params.put("keyword2", TemplateMsg.item(dateFormats.format(skDate), "#000000"));
            params.put("keyword3", TemplateMsg.item("点击查看", "#000000"));
            params.put("remark", TemplateMsg.item("欢迎下载微分金APP，查询订单、兑换现金、买卖产品更方便！", "#000000"));

            TemplateMsg wechatTemplateMsg = new TemplateMsg();
            wechatTemplateMsg.setTemplate_id(ConstantURL.normal_temp_id);
            wechatTemplateMsg.setTouser(openid);//openID
            wechatTemplateMsg.setUrl("http://www.impf2010.com/ea/pobuy/ea_getPhoneOrdersList.jspa?staid=" + dd.getContactUserID() + "&sccId=" + sccid);
            wechatTemplateMsg.setData(params);


            String hql = "from WeChatToken where appID = ?";
            WeChatToken wt = (WeChatToken) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{"wxa1b3f84c027804c3"});
            String accessTokens = "";
            if (wt != null && wt.getAccessToken() != null && !wt.getAccessToken().equals("")) {
                accessTokens = wt.getAccessToken();

            } else {
                AccessToken accessToken = WeixinUtil.getAccessToken("wxa1b3f84c027804c3", "26d569353c295fa8ad4fcb85a199f631");
                accessTokens = accessToken.getToken();

            }
            TemplateMsgResult templateMsgResult = WeixinUtil.sendTemplate(accessTokens, wechatTemplateMsg);
            if (templateMsgResult != null) {
                // System.out.println(templateMsgResult.getErrcode() + templateMsgResult.getErrmsg() + templateMsgResult.getMsgid());
                //  logger.error("tmsg:" + templateMsgResult.getErrcode() + templateMsgResult.getErrmsg() + templateMsgResult.getMsgid());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 收银自助发货
     *
     * @param cashierBillsID
     * @return
     */
    public String autoFh(String cashierBillsID) {
        try {
            List<BaseBean> beanList = new ArrayList<BaseBean>();


            //订单
            CashierBills dd = new CashierBills();
            //销售出库单
            CashierBills ck = new CashierBills();

            String hql = "from CashierBills d where d.cashierBillsID = ?";
            if(cashierBillsID.length()<=19){
                hql = "from CashierBills d where d.journalNum = ?";
            }
            dd = (CashierBills) beandao.getBeanByHqlAndParams(hql,
                    new String[]{cashierBillsID});


            //发货人
//        select staffname into fh_staff_name from dt_hr_staff where staffid=fh_staff_id;
//
//        update dt_order_bill_add set sendId = fh_staff_id ,sName = fh_staff_name,fhDate=sysdate where oa_bill_id =ddid ;

            try {
                ck = (CashierBills) dd.cloneCashierBills();   //出库单
                ck.setCashierBillsID(serverService.getServerID("cashier"));
                ck.setJournalNum(serverService.getBillID(dd.getCompanyID()));
                ck.setCashierBillsKey("");
                ck.setBillsType("销售出库单");
                ck.setStatus("19");
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            //beanList.add(dd);
            beanList.add(ck);


            // 进销存单据
            FinancialBill fin = new FinancialBill();
            fin.setFinancialbillID(serverService.getServerID("FinancialBill"));
            fin.setGroupCompanySn(dd.getGroupCompanySn());
            fin.setCompanyID(dd.getCompanyID());
            fin.setBillStatus("16");
            fin.setBillsType("销售出库单");
            fin.setJournalNum(dd.getJournalNum());
            fin.setBillsDate(new Date());
            fin.setBillStaffID(dd.getInputid());
            fin.setBillStaffName(dd.getInputName());
            fin.setCashierBillsID(ck.getCashierBillsID());

            beanList.add(fin);

            DtOrderBillAdd billAdd = (DtOrderBillAdd) beandao
                    .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{dd.getCashierBillsID()});

            billAdd.setFhDate(new Date());

            beanList.add(billAdd);
            StatusEntity status = SaveStatus(dd.getCashierBillsID(), dd.getJournalNum(), null, "02");
            status.setXddate(dd.getCashierDate());
            beanList.add(status);

            String goodl = "from GoodsBills d where d.cashierBillsID=?";

            List<BaseBean> ret = beandao.getListBeanByHqlAndParams(goodl,
                    new String[]{dd.getCashierBillsID()});
            List<Object[]> params = new ArrayList<Object[]>();
            String[] hqls = new String[ret.size()];
            if ("智能货柜".equals(dd.getGoodsName())) {
                String hqcc = "from SelfCart where payjournalNum = ?";
                List<BaseBean> clists = beandao.getListBeanByHqlAndParams(hqcc, new Object[]{dd.getJournalNum()});
                hqls = new String[clists.size()];
            }
                String hql1 = "update Inventory set invenQuantity = invenQuantity-?,sumPrice=sumprice- ? where  companyID=? and goodsID = ? and warehouse = ? and productId = ?";
             int j = 0;
            for (int i = 0; i < ret.size(); i++) {
                GoodsBills goodsBills = (GoodsBills) ret.get(i);
                try {


                    GoodsBills newGoods = (GoodsBills) goodsBills.cloneGoodsBills();
                    newGoods.setGoodsBillsID(serverService.getServerID("goodsBills"));
                    newGoods.setGoodsBillsKey("");
                    newGoods.setCashierBillsID(ck.getCashierBillsID());
                    newGoods.setIdentifyingCode("00");
                    beanList.add(newGoods);
                    String money_jisuan = newGoods.getMoney();
                    if (newGoods.getMoney().indexOf("-") != -1) {
                        money_jisuan = newGoods.getMoney().substring(0, newGoods.getMoney().indexOf("-"));
                    }
                    Object[] obj = new Object[6];
                   try {
                       if ("智能货柜".equals(dd.getGoodsName())) {
                           String hqc = "from SelfCart where payjournalNum = ? and pid = ?";
                           List<BaseBean> cclist = beandao.getListBeanByHqlAndParams(hqc, new Object[]{dd.getJournalNum(), newGoods.getPpID()});

                           SelfCart selfCart = null;
                           if (cclist.size() > 1) {//说明这个产品在多个秤上要多个秤出库
                               for (int k = 0; k < cclist.size(); k++) {
                                    obj = new Object[6];
                                    selfCart = (SelfCart)cclist.get(k);
                                   j = j + k;
                                   obj[0] = selfCart.getItemNum();

                                   money_jisuan = Float.parseFloat(selfCart.getItemNum()) * Float.parseFloat(selfCart.getPrice())+"";//金额

                                   obj[1] = money_jisuan;
                                   obj[2] = dd.getCompanyID();
                                   obj[3] = newGoods.getGoodsID();
                                   obj[4] = selfCart.getDeptId();
                                   obj[5] = newGoods.getPpID();
                                   params.add(obj);
                                   hqls[j] = hql1;
                               }
                           }else{
                               selfCart = (SelfCart)cclist.get(0);

                               obj[0] = selfCart.getItemNum();
                               money_jisuan = Float.parseFloat(selfCart.getItemNum()) * Float.parseFloat(selfCart.getPrice())+"";//金额

                               obj[1] = money_jisuan;

                               obj[2] = dd.getCompanyID();
                               obj[3] = newGoods.getGoodsID();

                               obj[4] = selfCart.getDeptId();
                               obj[5] = newGoods.getPpID();

                               params.add(obj);
                               hqls[j] = hql1;
                           }

                           j = j + 1;

                       }
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                    if (!"智能货柜".equals(dd.getGoodsName())) {
                        obj[0] = newGoods.getQuantity();//
                        obj[1] = money_jisuan;
                        obj[2] = dd.getCompanyID();
                        obj[3] = newGoods.getGoodsID();
                        obj[4] = newGoods.getDepotID();
                        obj[5] = newGoods.getPpID();
                        params.add(obj);
                        hqls[i] = hql1;
                    }
                    //库存盘点表
                    stockInv stockinvs = new stockInv();
                    stockinvs.setStockinvID(serverService.getServerID("stockInv"));
                    stockinvs.setCompanyID(dd.getCompanyID());
                    stockinvs.setGroupCompanySn(dd.getGroupCompanySn());
                    stockinvs.setGoodsBillsId(newGoods.getGoodsBillsID());
                    stockinvs.setGoodsID(newGoods.getGoodsID());
                    stockinvs.setGoodsType(newGoods.getTypeID());
                    stockinvs.setGoodsName(newGoods.getGoodsName());
                    stockinvs.setInvenQuantity(newGoods.getQuantity());
                    stockinvs.setSummoney(money_jisuan);
                    stockinvs.setIntime(new Date());
                    stockinvs.setType("01");
                    stockinvs.setWarehouse(newGoods.getDepotID());
                    stockinvs.setWarehouseName(newGoods.getDepotName());
                    beanList.add(stockinvs);




                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }

            }

            baseBeanService.executeHqlsByParamsList(beanList, hqls, params);
            //库存


        } catch (Exception e) {
            e.printStackTrace();
        }


        //销售出库单 （Cashierbills)以及GoodsBills,FinalcaialBill
        //dtrelatedbill 关联表  订单和销售出库单

        //一个物品库存明细
        //一个物品的库存

        return "";

    }

    /**
     * 保存转他人支付信息
     *
     * @param t 他人支付信息
     */
    public void transferPayOrder(transferPay t) throws Exception {
        List<BaseBean> beanList = new ArrayList<BaseBean>();
        beanList.add(SaveStatus(t.getCashierbillsid(), t.getJournalnum(), "01", null));
        beanList.add(t);
        beandao.executeSqlsByParmsList(beanList, null, null);
    }

    /**
     * 保存单据状态表信息
     *
     * @param cashid     订单id
     * @param journalNum 订单编号
     * @param paystatus  支付状态
     * @param supstatus  购买流程跟踪状态
     */
    public StatusEntity SaveStatus(String cashid, String journalNum, String paystatus, String supstatus) throws Exception {
        StatusEntity status = (StatusEntity) baseBeanService.getBeanByHqlAndParams("from StatusEntity where cashierbillsid=?", new Object[]{cashid});
        if (status == null) {
            status = new StatusEntity();
            status.setStordid(serverService.getServerID("status"));
            status.setCashierbillsid(cashid);
            status.setJnumorder(journalNum);
            status.setXddate(new Date());
        }
        if (paystatus != null && !paystatus.equals("")) {
            status.setPaystatus(paystatus);
            if (paystatus.equals("03")) {
                status.setFkdate(new Date());
            }
        }
        //01:拣货 02：发货 03：送货 04：物流 05:签收 06：收货 07：验货 08：入库 09：分金币
        if (supstatus != null && !supstatus.equals("")) {
            status.setSupplierstatus(supstatus);
            if (supstatus.equals("02")) {
                status.setJhdate(new Date());
            } else if (supstatus.equals("03")) {
                status.setFhdate(new Date());
            } else if (supstatus.equals("04")) {
                //status.setShdate(new Date());
            } else if (supstatus.equals("06")) {
                status.setShdate(new Date());
            } else if (supstatus.equals("09")) {
                status.setFzdate(new Date());
            }
        }
        return status;
    }

    /**
     * 考场练车现金特殊处理
     */
    private void setLcCash(String wfStatus4, CashierBills cc, String sccid) {

        if ("金币计时".equals(cc.getProjectName())) {
            if ("06".equals(wfStatus4)) {
                String hqlbb = "from PayBackupBill where journalNum = ?";
                PayBackupBill blb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hqlbb, new Object[]{cc.getJournalNum()});
                if (blb == null) {
                    PayBackupBill n = new PayBackupBill();
                    n.setPbbID(serverService.getServerID("pbbid"));
                    n.setJournalNum(cc.getJournalNum());
                    n.setCashCompany(sccid);
                    beandao.save(n);
                } else {
                    blb.setCashCompany(sccid);
                    beandao.update(blb);
                }
            }
            makeAppointmentService.setBookState(cc.getJournalNum());

        }

    }

    /**
     * 设置
     *
     * @return
     */
    private void setCarManage(String journalNum, String wfStatus4, TEshopCusCom tcc, String ppid) {
        System.out.println("journalNumz"+journalNum);
        String hqlbb = "from PayBackupBill where journalNum = ?";
        PayBackupBill blb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hqlbb, new Object[]{journalNum});
        if (blb != null && blb.getCoID() != null && !blb.getCoID().equals("")) {
            makeAppointmentService.setCarManage(blb.getCoID(), journalNum, wfStatus4);

            CarManage carm = (CarManage) beandao.getBeanByHqlAndParams("from CarManage where carmID = ?", new Object[]{blb.getCoID()});
            CarInformation car = new CarInformation();
            car.setCarNum(carm.getCarNumber());

            String r = makeAppointmentService.isExistCar(tcc.getStaffid(), car.getCarNum());
            if ("0".equals(r)) {
                makeAppointmentService.addVehicle(car, tcc);
            }
            carManageService.addTiming("", ppid, journalNum, carm.getCarNumber(), tcc.getStaffid(), carm.getIndate());

        } else {
            carManageService.addTiming("", ppid, journalNum, blb.getCarNum(), tcc.getStaffid(), null);

        }

        //推送成功
        //cmService.pushJG(null, "pay", null, blb.getCoID(), null,"");//通知付款成功打印小票
        //将上面的极光推送结果改为推送到mqtt,让道闸立即开门，如果是以前的开门方式直接不兼容。
        //cmService.pushMqtt(null, "pay", null, blb.getCoID(), null,"");
    }
    /**
     * 收藏商品
     *
     * @param ppid
     * @param pricetype
     * @param staffID
     * @return
     */
    public String collectGoods(String ppid, String pricetype, String staffID) {
        if (pricetype == null || pricetype.equals("")) {
            pricetype = "0";
        }
        String result = "0";

        String hql = "from GoodsCollect where pid = ? and pricetype = ? and staffId = ?";
        GoodsCollect gc = (GoodsCollect) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppid, pricetype, staffID});

        if (gc == null) {
            //收藏
            gc = new GoodsCollect();
            gc.setGcId(serverService.getServerID("gcid"));
            gc.setPid(ppid);
            gc.setPricetype(pricetype);
            gc.setStaffId(staffID);
            gc.setCreateDate(new Date());
            result = "1";
            baseBeanService.save(gc);

        } else {
            //取消收藏
            baseBeanService.deleteBeanByKey(GoodsCollect.class, gc.getGcKey());

        }

        return result;
    }

    /**
     * 判断是否已收藏
     *
     * @param ppid
     * @param pricetype
     * @param staffID
     * @return
     */
    public String isCollect(String ppid, String pricetype, String staffID) {
        if (pricetype == null || pricetype.equals("")) {
            pricetype = "0";
        }
        String result = "0";

        String hql = "from GoodsCollect where pid = ? and pricetype = ? and staffId = ?";
        GoodsCollect gc = (GoodsCollect) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{ppid, pricetype, staffID});

        if (gc == null) {

            result = "0";


        } else {
            result = "1";

        }

        return result;
    }


    /**
     * 获取收藏的商品
     *
     * @param pageNumber
     * @param pageSize
     * @param paramter
     * @param staffID
     * @return
     */
    public PageForm getCollectGoodsList(int pageNumber, int pageSize, String paramter, String staffID) {
        StringBuilder sql = new StringBuilder();
        List<Object> param = new ArrayList<Object>();


        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());

        sql.append("select case when  gc.pricetype ='1' then ROUND(dpw.wholesale* (1 + nvl(sz.total_pct, 0) / 100), 2)");
        sql.append(" when tpa.type is not null then ROUND(tpap.actPrice * (1 + nvl(sz.total_pct, 0) / 100), 2)");
        sql.append(" when pa.type is not null then ROUND(pap.actPrice * (1 + nvl(sz.total_pct, 0) / 100), 2)");
        sql.append(" else ROUND(ps.re_price * (1 + nvl(sz.total_pct, 0) / 100), 2) end price,");
        sql.append(" gc.pricetype,p.image,p.goodsname,p.ppid,p.goodsid,gc.gcId,p.monthsales,p.variableid,cm.companyaddr,cm.ccompanyid,t.companyid");

        sql.append(" from dt_GoodsCollect gc left join dt_ProductPackaging p on gc.pid = p.ppid");
        sql.append(" left join dt_pro_wholesale dpw on gc.pid = dpw.ppid and dpw.state = '00' and gc.pricetype='1' ");//批发价
        sql.append(" left join  DT_PRO_SETUP ps on ps.ppid = p.ppid and gc.pricetype='0'");

        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00'");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') and  gc.pricetype='0'");


        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");


        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append("where gc.staffID =? ");
        param.add(currentTime);
        param.add(currentTime);
        param.add(currentTime);
        param.add(currentTime);
        param.add(staffID);
        if (paramter != null && !paramter.equals("")) {
            sql.append(" and p.goodsname like ?");
            param.add("%" + paramter + "%");
        }

        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                param.toArray());
        return pageForm;
    }


    /**
     * 获取收藏的店铺
     *
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param sccid
     * @return
     */
    public PageForm getCollectShopsList(int pageNumber, int pageSize, String parameter, String sccid) {

        List<Object> param = new ArrayList<Object>();
        String sql = "select k.jfID,t.companyname,t.companyaddr,t.brandinfo,t.ccompanyId from t_Eshop_Cuscom m,Dtjoinfans k,Dt_Ccom_Com cc,Dtcontactcompany t where cc.compnay_id = m.companyid and cc.ccompany_id=t.ccompanyid and m.sccid = k.zsccid and m.state = ? and k.staffid = ?";
        param.add("2");
        param.add(sccid);
        if (parameter != null && !parameter.equals("")) {
            sql += " and t.companyname like ?";
            param.add("%" + parameter + "%");
        }

        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber, pageSize, sql, "select count(*) from (" + sql + ")", param.toArray());
        return pageForm;

    }


    /**
     * 批量取消商品收藏
     *
     * @param gcIds
     * @return
     */
    public void batchCCGoods(String[] gcIds) {
        List<Object> param = new ArrayList<Object>();
        String hql = "delete from GoodsCollect where gcId in(";

        for (int i = 0; i < gcIds.length; i++) {

            if (i == gcIds.length - 1) {
                hql += "?)";

            } else {
                hql += "?,";
            }
            param.add(gcIds[i]);


        }
        baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, param.toArray());
    }


    /**
     * 批量取消店铺收藏
     *
     * @param jfIDs
     * @return
     */
    public void batchCCShops(String[] jfIDs) {

        List<Object> param = new ArrayList<Object>();

        String hql = "delete from JoinFans where jfID in(";

        for (int i = 0; i < jfIDs.length; i++) {

            if (i == jfIDs.length - 1) {
                hql += "?)";

            } else {
                hql += "?,";
            }
            param.add(jfIDs[i]);


        }
        baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, param.toArray());

    }

    /**
     * 根据订单号获取注册的公司
     *
     * @param journalNum
     * @return
     */
    public String getComBz(String journalNum) {
        String hql = "from PayBackupBill where journalNum = ?";
        PayBackupBill pb = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{journalNum});
        System.out.println(pb == null ? "" : pb.getCompanyName());
        return pb == null ? "" : pb.getCompanyName();
    }


    /**
     * 购买不免费的公司会员临时存储公司信息现在用在周边经济入驻
     *
     * @param company
     * @param cdl
     * @return
     */
    public void tempCompany(Company company, CDetail cdl, String journalNum) {
        PayBackupBill payBackupBill = new PayBackupBill();
        payBackupBill.setPbbID(serverService.getServerID("pbbid"));
        payBackupBill.setJournalNum(journalNum);
        payBackupBill.setCompanyName(company.getCompanyName());
        payBackupBill.setIndustryType(company.getIndustryType());
        payBackupBill.setIndustryId(company.getIndustryId());
        payBackupBill.setBrandInfo(company.getBrandInfo());
        payBackupBill.setShopname(company.getShopname());
        payBackupBill.setCompanyPhone(cdl.getCompanyPhone());
        payBackupBill.setCompanyManager(cdl.getCompanyManager());
        payBackupBill.setManagertel(cdl.getManagertel());
        payBackupBill.setGdID(company.getGdID());
        payBackupBill.setAccuracy(company.getAccuracy());
        payBackupBill.setDimension(company.getDimension());
        payBackupBill.setPname(company.getPname());
        payBackupBill.setCityname(company.getCityname());
        payBackupBill.setAdname(company.getAdname());
        payBackupBill.setStreet(company.getStreet());
        payBackupBill.setLogo(cdl.getLogo());
        baseBeanService.save(payBackupBill);


    }

    /**
     * 购买不免费的公司会员临时存储公司信息现在用在周边经济入驻
     *
     * @param company
     * @param cdl
     * @return
     */
    private void setTempCompany(Company company, CDetail cdl, PayBackupBill pbb) {

        company.setCompanyName(pbb.getCompanyName());
        company.setIndustryType(pbb.getIndustryType());
        company.setIndustryId(pbb.getIndustryId());
        company.setBrandInfo(pbb.getBrandInfo());
        company.setShopname(pbb.getShopname());
        cdl.setCompanyPhone(pbb.getCompanyPhone());
        cdl.setCompanyManager(pbb.getCompanyManager());
        cdl.setManagertel(pbb.getManagertel());
        cdl.setLogo(pbb.getLogo());
        company.setGdID(pbb.getGdID());
        company.setAccuracy(pbb.getAccuracy());
        company.setDimension(pbb.getDimension());
        company.setPname(pbb.getPname());
        company.setCityname(pbb.getCityname());
        company.setAdname(pbb.getAdname());
        company.setStreet(pbb.getStreet());


    }

    /**
     * 微分账号绑定手机号或者微信账号和手机号合并
     *
     * @param scc
     * @param cus
     * @param account
     * @return
     */
    public void bindAccount(TEshopCusCom scc, TEshopCustomer cus, String account) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        if (account != null) {
            //如果手机号存在
            TEshopCusCom telscc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0", new Object[]{account});
            if (telscc == null) {  //如果手机号没有创建过账号，直接绑定到微信账号上
                scc.setAccount(account);
                cus.setAccount(account);

                beans.add(scc);
                beans.add(cus);

            } else {//如果手机号创建过账号，需要保留手机号账号，微信账号暂时停用 如果手机号绑定了其他微信，暂时解绑，绑定当前微信
                TEshopCustomer telcus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{account});

                telscc.setOpenId(scc.geteOpenId());
                telcus.setOpenId(cus.geteOpenId());
                beans.add(telcus);
                //将原始微信账号停用
                scc.setOpenId(scc.getOpenId() + account);
                scc.setAccount(scc.getAccount() + account);
                cus.setOpenId(cus.getOpenId() + account);
                cus.setAccount(cus.getAccount() + account);
                beans.add(scc);
                beans.add(cus);
                scc = telscc;
                cus = telcus;
            }

            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);

        }


    }


    /**
     * 根据支付订单号构建子单信息
     *
     * @param
     * @return
     */
    public List<SubOrders> getOrdersList(WxPayDto payDto) {

        List<BaseBean> beans = new ArrayList<BaseBean>();
        String sql = "select t.sub_mchid from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ? and t.applyment_state = 'FINISH'";

        String sqlcost = "select sum(to_number(g.quantity)*to_number(g.costmoney)),sum(money)-sum(to_number(g.quantity)*to_number(g.costmoney)) from Dtgoodsbills g where g.goodsname !='VIP客户' and g.cashierbillsid=?";
        List<Object> orderlist = new ArrayList<Object>();
        int source = 1;

        List<SubOrders> sub_orderslist = new ArrayList<SubOrders>();
        int yj = 0;
        StringBuilder sql1 = new StringBuilder();
        try {
            if (payDto.getAttach() != null) {

                if (payDto.getAttach().indexOf("smsk") != -1 || payDto.getAttach().indexOf("dsmzs") != -1 || payDto.getAttach().indexOf("qyht") != -1) {
                    source = 2;
                    String ppid = payDto.getAttach().substring(payDto.getAttach().lastIndexOf("-") + 1);
                    ProSetup proSetup = (ProSetup) baseBeanService.getBeanByHqlAndParams("from ProSetup where ppid = ?", new Object[]{ppid});
                    String q = Math.round(Float.parseFloat(payDto.getTotalFee()) / Float.parseFloat(proSetup.getRePrice())) + "";

                    BigDecimal sumcb = new BigDecimal(proSetup.getEfPrice()).multiply(new BigDecimal(q));
                    BigDecimal sumyj = new BigDecimal(payDto.getTotalFee()).subtract(sumcb);

                    Object[] obj = {payDto.getOrderId(), proSetup.getComId(), sumcb, sumyj};
                    orderlist.add(obj);


                } else if (payDto.getAttach().indexOf("gsmzs") != -1 || payDto.getAttach().indexOf("xdsmzs") != -1) {
                    source = 2;
                    PayBackupBill payBackupBill = (PayBackupBill) beandao.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{payDto.getOrderId()});

                    if (payBackupBill.getCoID() == null || payBackupBill.getCoID().equals("")) {
                        //当前公司人的购物车助手

                        sql1.append("select sum(ps.ef_price),sum(ps.BROKERAGE)");
                        sql1.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,DT_cart c");
                        sql1.append(" where  p.ppid=ps.ppid and p.ppid = c.pid");
                        sql1.append(" and ps.fcom_id is null and c.staff_id = ? and p.showweixin = ? and c.pos = ? and c.companyID = ?");

                        Object objs = baseBeanService.getObjectBySqlAndParams(sql,
                                new Object[]{payBackupBill.getWaiterID(), "01", "1", payBackupBill.getCompanyId()});
                        Object[] objf = (Object[]) objs;

                        Object[] obj = {payDto.getOrderId(), payBackupBill.getCompanyId(), objf[0], objf[1]};
                        orderlist.add(obj);
                    } else {
                        sql1.append("select sum(ps.ef_price),sum(ps.BROKERAGE)");
                        sql1.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,dtClientOrderGoods c");
                        sql1.append(" where  p.ppid=ps.ppid and p.ppid = c.ppid");
                        sql1.append(" and ps.fcom_id is null and c.coID = ?");
                        Object objs = baseBeanService.getObjectBySqlAndParams(sql,
                                new Object[]{payBackupBill.getCoID()});
                        Object[] objf = (Object[]) objs;

                        Object[] obj = {payDto.getOrderId(), payBackupBill.getCompanyId(), objf[0], objf[1]};
                        orderlist.add(obj);

                    }


                } else if (payDto.getAttach() != null && payDto.getAttach().indexOf("selfpay") != -1) {
                    source = 0;


                    sql1.append("select c.journalNum,p.companyid,sum(to_number(c.itemNum)*to_number(c.costmoney)),sum(to_number(c.itemNum)*to_number(c.price))-sum(to_number(c.itemNum)*to_number(c.costmoney))");
                    sql1.append(" from dt_ProductPackaging p,dt_selfcart c");
                    sql1.append(" where p.ppid = c.pid");
                    sql1.append(" and (c.journalNum = ? or (c.payjournalNum = ? and c.payjournalNum!=c.journalNum)) group by c.journalNum,p.companyid");
                    orderlist = baseBeanService.getListBeanBySqlAndParams(sql1.toString(),
                            new Object[]{payDto.getOrderId(), payDto.getOrderId()});


                } else {
                    String sqls = "select c.journalNum,c.companyID,c.projectName,c.cashierBillsID,c.priceSub from dtCashierBills c where c.journalNum in(select p.oriJournalNum from dtPayCashierBill p where p.payJournalNum = ?)";
                    orderlist = baseBeanService.getListBeanBySqlAndParams(sqls, new Object[]{payDto.getOrderId()});


                }
            } else {

                String sqls = "select c.journalNum,c.companyID,c.projectName,cashierBillsID,c.priceSub from dtCashierBills c where c.journalNum in(select p.oriJournalNum from dtPayCashierBill p where p.payJournalNum = ?)";
                orderlist = baseBeanService.getListBeanBySqlAndParams(sqls, new Object[]{payDto.getOrderId()});
            }
            System.out.println("orderlist:" + orderlist.size());
            boolean b = true;
            for (int i = 0; i < orderlist.size(); i++) {
                Object[] bbs = (Object[]) orderlist.get(i);

                SubOrders subOrders = (SubOrders) baseBeanService.getBeanByHqlAndParams("from SubOrders where out_trade_no = ?", new Object[]{bbs[0].toString()});
                if (subOrders == null) {
                    subOrders = new SubOrders();
                    subOrders.setSoId(serverService.getServerID("soId"));

                    subOrders.setOut_trade_no(bbs[0].toString());
                }
                subOrders.setMchid(WeChatUtil.MCH_ID);
                subOrders.setAttach(payDto.getAttach());

                Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{bbs[1].toString()});
                if (obj == null || obj.toString().equals("")) {
                    b = false;
                    sub_orderslist.clear();
                    sub_orderslist = null;
                    System.out.println(bbs[1].toString() + "公司没有认证");
                    break;
                }
                subOrders.setSub_mchid(obj.toString());


                Amount amount = new Amount();
                int totalFee = 0;
                if (source == 1) {
                    Object objs = beandao.getObjectBySqlAndParams(sqlcost, new Object[]{bbs[3].toString()});





                    Object[] objf = (Object[]) objs;
                    if (objf[1] != null) {
                        yj += Integer.parseInt(WeChatUtils.getMoney(String.format("%.2f", Float.parseFloat(objf[1].toString()))));//佣金总和
                    }

                    totalFee = Integer.parseInt(WeChatUtils.getMoney(String.format("%.2f", Float.parseFloat(objf[0].toString()))));//成本总和




                    String priceSub = bbs[4].toString();
                    BigDecimal pb = new BigDecimal(priceSub);
                    BigDecimal rb = new BigDecimal(payDto.getTotalFee());
                    BigDecimal yb = pb.subtract(rb);


                    if(pb.compareTo(rb)>0){
                       int dyj = 0;
                       int dtotalFee = 0;

                        dyj = yj-(Integer.parseInt(WeChatUtils.getMoney(yb.toString())));//待付佣金部分=总佣金-已付款金额(>0的情况)
                        if(dyj>0){
                           // 供应商成本金额：待付款金额-待付佣金部分
                            dtotalFee = (Integer.parseInt(WeChatUtils.getMoney(rb.toString())))-dyj;
                        }else{
                            //小于等于0 说明佣金已经够扣了， 合单支付无佣金部分，待付款金额全部为供应商成本
                            dyj=0;
                            dtotalFee = Integer.parseInt(WeChatUtils.getMoney(rb.toString()));

                        }
                        totalFee = dtotalFee;
                        yj=dyj;
                    }

                  //




                    subOrders.setDescription(bbs[2].toString());
                } else if (source == 2) {
                    yj += Integer.parseInt(WeChatUtils.getMoney(String.format("%.2f", Float.parseFloat(bbs[3].toString()))));
                    totalFee = Integer.parseInt(WeChatUtils.getMoney(String.format("%.2f", Float.parseFloat(bbs[2].toString()))));
                    subOrders.setDescription(payDto.getBody());
                } else {
                    yj += Integer.parseInt(WeChatUtils.getMoney(String.format("%.2f", Float.parseFloat(bbs[3].toString()))));
                    totalFee = Integer.parseInt(WeChatUtils.getMoney(String.format("%.2f", Float.parseFloat(bbs[2].toString()))));

                    subOrders.setDescription("消费");
                }
                amount.setTotal_amount(totalFee);
                amount.setCurrency("CNY");
                subOrders.setAmount(amount);
                subOrders.setMoney(totalFee + "");
                beans.add(subOrders);
                SettleInfo settle_info = new SettleInfo();
                settle_info.setProfit_sharing(true);
                settle_info.setSubsidy_amount(0);

                subOrders.setSettle_info(settle_info);
//            subOrders.setMoney(null);
                sub_orderslist.add(subOrders);


            }
            if (yj > 0 && b == true) {


                String hqlsf = "from PayCashierBill c where c.payJournalNum = ? and c.dtype is not null";
                PayCashierBill payCashierBill = (PayCashierBill) beandao.getBeanByHqlAndParams(hqlsf, new Object[]{payDto.getOrderId()});
                SubOrders subOrders = null;
                if (payCashierBill != null) {
                    subOrders = (SubOrders) baseBeanService.getBeanByHqlAndParams("from SubOrders where out_trade_no = ?", new Object[]{payCashierBill.getOriJournalNum()});
                }
                if (subOrders == null) {
                    subOrders = new SubOrders();
                    subOrders.setSoId(serverService.getServerID("soId"));


                    subOrders.setOut_trade_no(serverService.getBillID(Constant.COMPAYN_ID));


                    payCashierBill = new PayCashierBill();
                    payCashierBill.setPcbID(serverService.getServerID("pcbid"));
                    payCashierBill.setOriJournalNum(subOrders.getOut_trade_no());
                    payCashierBill.setPayJournalNum(payDto.getOrderId());
                    payCashierBill.setDtype(yj + "");
                    baseBeanService.save(payCashierBill);
                }


                subOrders.setMchid(WeChatUtil.MCH_ID);

                subOrders.setAttach(payDto.getAttach());

                subOrders.setSub_mchid("1631241826");// 1604771870    1627898266投资

                subOrders.setDescription("佣金");


                Amount amount = new Amount();


                amount.setTotal_amount(yj);
                amount.setCurrency("CNY");
                subOrders.setAmount(amount);
                subOrders.setMoney(yj + "");
                beans.add(subOrders);
                SettleInfo settle_info = new SettleInfo();
                settle_info.setProfit_sharing(true);
                settle_info.setSubsidy_amount(0);

                subOrders.setSettle_info(settle_info);
//              subOrders.setMoney(null);
                sub_orderslist.add(subOrders);


            }

            baseBeanService.executeHqlsByParamsList(beans, null, null);
            System.out.println("sub_orderslist" + sub_orderslist);
        } catch (Exception e) {
            e.printStackTrace();
            sub_orderslist.clear();
            sub_orderslist = null;
            System.out.println("报错啦");
        }
        return sub_orderslist;
    }

    /**
     * 提交保存第三方交易号
     *
     * @param sublist
     */
    public void setPayCashiber(Map<String, String> sublist, String payJournalNum) {
        String[] hqls = new String[sublist.size()];
        String hql = "update PayCashierBill set  trade_no = ? where payJournalNum = ? and oriJournalNum = ?";
        List<Object[]> parmsList = new ArrayList<Object[]>();
        Object[] obj = null;
        int i = 0;
        for (String key : sublist.keySet()) {

            String value = sublist.get(key);
            hqls[i] = hql;
            parmsList.add(new Object[]{value, payJournalNum, key});
            i++;
        }
        baseBeanService.executeHqlsByParamsList(null, hqls, parmsList);
    }


    /**
     * 添加颌单信息
     *
     * @param journalNum
     */
    public void addHdInfo(String journalNum) {
        PayBackupBill payBackupBill = (PayBackupBill) baseBeanService.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{journalNum});
        if (payBackupBill == null) {
            payBackupBill = new PayBackupBill();
            payBackupBill.setPbbID(serverService.getServerID("pbbid"));
            payBackupBill.setJournalNum(journalNum);
        }

        payBackupBill.setHdpay("1");
        baseBeanService.update(payBackupBill);

    }

    /**
     *
     *    创建表
     *
     * @return
     */
    public TableRalate  createTable(String companyID,String type){

        String hql="from TableRalate where companyID = ? and type = ? and year  = ?";
          Date currentDate = new Date();

         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
         String dateString = dateFormat.format(currentDate);

        TableRalate tableRalate = null;
        try {
             List<BaseBean> beans = new ArrayList<BaseBean>();


            tableRalate = (TableRalate)beandao.getBeanByHqlAndParams(hql,new Object[]{companyID,type,dateString});
            if(tableRalate==null){
               List<BaseBean>  list22 = beandao.getListBeanByHqlAndParams("from TableRalate where companyID = ? and type = ?",new Object[]{companyID,type});

              int c = -1;
               if(list22.size()>0){
                 TableRalate tableRalate22 = (TableRalate)list22.get(0);
                 c = tableRalate22.getCompanySeq();
               }else {
                 String sql = "select Max(companySeq) from dtTableRalate where type = ?";

                 try {
                     int cc = beandao.getConutByBySqlAndParams(sql, new Object[]{type});
                     c = cc + 1;
                 } catch (Exception e) {
                     c = c + 1;
                 }
             }


                String tableName = "dt"+type+"_"+c+"_"+dateString;
                String existing_table = "dt"+type;
                            //创建表
                String csqls = "CREATE TABLE "+tableName+" AS SELECT * FROM "+existing_table+" WHERE 1=0";
                tableRalate = new TableRalate();
                tableRalate.setTrID(serverService.getServerID("trid"));
                tableRalate.setCompanyID(companyID);
                tableRalate.setCompanySeq(c);
                tableRalate.setDates(new Date());
                tableRalate.setYear(dateString);
                tableRalate.setTablename(tableName);
                tableRalate.setType(type);
                beans.add(tableRalate);
                beandao.executeSqlsByParmsList(beans,new String[]{csqls},null);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableRalate;
    }


    /**
     *
     *    创建表 用于处理数据
     *
     * @return
     */
    public TableRalate  createTableDealData(String companyID,String type,String year){

        String hql="from TableRalate where companyID = ? and type = ? and year  = ?";
        Date currentDate = new Date();



        TableRalate tableRalate = null;
        try {
            List<BaseBean> beans = new ArrayList<BaseBean>();


            tableRalate = (TableRalate)beandao.getBeanByHqlAndParams(hql,new Object[]{companyID,type,year});
            if(tableRalate==null){
                List<BaseBean>  list22 = beandao.getListBeanByHqlAndParams("from TableRalate where companyID = ? and type = ?",new Object[]{companyID,type});

                int c = -1;
                if(list22.size()>0){
                    TableRalate tableRalate22 = (TableRalate)list22.get(0);
                    c = tableRalate22.getCompanySeq();
                }else {
                    String sql = "select Max(companySeq) from dtTableRalate where type = ?";

                    try {
                        int cc = beandao.getConutByBySqlAndParams(sql, new Object[]{type});
                        c = cc + 1;
                    } catch (Exception e) {
                        c = c + 1;
                    }
                }

                String tableName = "dt"+type+"_"+c+"_"+year;
                String existing_table = "dt"+type;
                //创建表
                String csqls = "CREATE TABLE "+tableName+" AS SELECT * FROM "+existing_table+" WHERE 1=0";
                tableRalate = new TableRalate();
                tableRalate.setTrID(serverService.getServerID("trid"));
                tableRalate.setCompanyID(companyID);
                tableRalate.setCompanySeq(c);
                tableRalate.setDates(new Date());
                tableRalate.setYear(year);
                tableRalate.setTablename(tableName);
                tableRalate.setType(type);
                beans.add(tableRalate);
                beandao.executeSqlsByParmsList(beans,new String[]{csqls},null);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableRalate;
    }

    /**
     *
     * 复制记录
     * @param source_table
     * @param companyID
     * @param type
     * @param idName
     * @param idValue
     */
    public void copyTable(String source_table,String companyID,String type,String idName,String idValue){

        TableRalate tableRalate = createTable(companyID,type);


        if(tableRalate!=null){
            String sql = "INSERT INTO "+ tableRalate.getTablename()+"  SELECT * FROM "+source_table+"  WHERE "+idName+"=?";
            List<Object[]> paramslist = new ArrayList<>();
            Object[] obj = {idValue};
            paramslist.add(obj);
            beandao.executeSqlsByParmsList(null,new String[]{sql},paramslist);
        }


    }

    /**
     *
     * 根据订单号复制订单和收款单到新表
     * @param journalNum
     * @return
     */
    public void copyCash(String journalNum,String bills){

        String  hql1 = "from CashierBills where journalNum = ? and billsType = '项目收入预算单'";
        CashierBills cashierBills = (CashierBills)beandao.getBeanByHqlAndParams(hql1,new Object[]{journalNum});
        String hql = "";
        if(cashierBills!=null) {
            copyTable("dtCashierBills", cashierBills.getCompanyID(), "CashierBills", "cashierBillsID", cashierBills.getCashierBillsID());//复制订单到新表
            if (bills.equals("d")) {//复制订单和收款单
                hql = "from CashierBills where jNumOrder = ? and billsType = '收款单' and projectName != '金币兑换' and projectName != '供应商成本'";

            } else if (bills.equals("j")) {//复制金币入库单或者积分入库单
                hql = "from CashierBills where jNumOrder = ? and ((billsType = '积分入库单' and projectName = '积分购物') or  (billsType = '金币入库单' and projectName = '金币购物'))";
            }
            List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, new Object[]{journalNum});
            for (BaseBean b : list) {
                CashierBills dd = (CashierBills) b;
                copyTable("dtCashierBills", cashierBills.getCompanyID(), "CashierBills", "cashierBillsID", dd.getCashierBillsID());//复制订单到新表
                System.out.println(dd.getJournalNum() + "-" + dd.getBillsType());

            }

        }
    }

    /**
     *
     * 获取表
     *
     * @return
     */
    public TableRalate  getTableName(String companyID,String type,String year){

        String hql="from TableRalate where companyID = ? and type = ? and year  = ?";


        TableRalate tableRalate = (TableRalate)beandao.getBeanByHqlAndParams(hql,new Object[]{companyID,type,year});




        return tableRalate;
    }


    /**
     *
     * 获取表
     *
     * @return
     */
    public void  copyData(String companyID,String year,String nyr){
        TableRalate  tableRalate = getTableName(companyID,"CashierBills",year);
        if(tableRalate!=null){

            String  hql = "from CashierBills where companyID = ? and billsType = '项目收入预算单' and fkStatus!='01' and cashierBillsID like ?";
            List<BaseBean> list = beandao.getListBeanByHqlAndParams(hql, new Object[]{companyID,"%"+nyr+"%"});

            String sql = "INSERT INTO "+ tableRalate.getTablename()+"  SELECT * FROM dtCashierBills  WHERE cashierBillsID=?";
            for(BaseBean b:list){
                try {
                    CashierBills cashierBills = (CashierBills) b;
                    List<Object[]> paramslist = new ArrayList<>();
                    Object[] obj = {cashierBills.getCashierBillsID()};
                    paramslist.add(obj);
                    System.out.println("订单：" + cashierBills.getCashierBillsID());
                    beandao.executeSqlsByParmsList(null, new String[]{sql}, paramslist);

                    String hql1 = "from CashierBills where jNumOrder = ? and ((billsType = '收款单' and projectName != '金币兑换' and projectName != '供应商成本') or (billsType = '积分入库单' and projectName = '积分购物') or  (billsType = '金币入库单' and projectName = '金币购物'))";
                    List<BaseBean> list2 = beandao.getListBeanByHqlAndParams(hql1, new Object[]{cashierBills.getJournalNum()});

                    for (BaseBean bc : list2) {
                        CashierBills cashierBills2 = (CashierBills) bc;
                        List<Object[]> paramslist2 = new ArrayList<>();
                        Object[] obj2 = {cashierBills2.getCashierBillsID()};
                        paramslist2.add(obj2);
                        System.out.println("收款单：" + cashierBills2.getCashierBillsID());
                        beandao.executeSqlsByParmsList(null, new String[]{sql}, paramslist2);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
    }
}



