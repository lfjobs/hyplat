package com.tiantai.wfj.service.impl;

import com.daifu.chinapay.meth.SinglePay;
import com.daifu.chinapay.model.bean.BankWfjBean;
import com.daifu.chinapay.model.bean.TransactionBean;
import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.edmandServe.bo.DemandDetail;
import com.tiantai.wfj.service.WfjJifenService;
import com.wechat.bo.sft.Withdraw;
import com.wechat.utils.HTTPV3;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.DrivingSchool.SchProCategory;
import hy.ea.bo.company.BusiManager;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.InvestDeviceBind.DeviceBindStaff;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.invoicing.Inventory;
import hy.ea.bo.invoicing.stockInv;
import hy.ea.service.RegisterService;
import hy.ea.util.Constant;
import hy.ea.util.DownloadImage;
import hy.ea.util.MobileMessage;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WfjJifenServiceImpl implements WfjJifenService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService idgec;
    @Resource
    private RegisterService registerService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;

    private final static Logger logger = LoggerFactory.getLogger(WfjJifenServiceImpl.class);
    @Autowired
    private MobileMessage msage;//发短信

    /**
     * 金币兑现（对接银联代付接口）（一）
     *
     * @param sccid
     * @param user
     * @param money
     * @param bankId
     * @return
     * @throws Exception
     */
    @Transactional
    public synchronized String gecOrderAndDeal(String sccid, String user, int money, String bankId) throws Exception {

        String str = "";
        TransactionBean bean = SinglePay.BalanceQuery();
        try {
            //金币冻结的判断
            TEshopCustomer tucs = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams(" from TEshopCustomer where account= ? and logOff=0", new Object[]{user});
            if (tucs.getStatus() != null && tucs.getStatus().equals("1")) {
                throw new Exception("金币账户冻结,不能提现");
            }

            if (bean != null && bean.getMerAmt() != null && !bean.getMerAmt().equals("")) {
                BigDecimal balance = new BigDecimal(bean.getMerAmt());
                logger.error("备付金余额：" + balance);
                if (balance.compareTo(new BigDecimal((money + 1000000) + "")) == -1) {
                    msage.setMobiles("15810799888");
                    msage.setMessage("金币兑现账户备付金已不足1万，已免影响微分金用户金币兑现， 请尽快充值！");
                    logger.error("金币兑现账户备付金已不足1万，已免影响微分金用户金币兑现， 请尽快充值！");
                    msage.sendMsg("【微分金平台】");
                }
                if (balance.compareTo(new BigDecimal((money + 50000) + "")) > -1) {
                    logger.error("单笔代付");
                    //查询账户金币数
                    TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});
                    WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=?", new Object[]{customer.getStaffid(), sccid});
                    BigDecimal bigjifen = new BigDecimal(jifen.getWfjJifenScore());
                    if (bigjifen.compareTo(new BigDecimal(money + "")) > -1) {
                        Object[] c = (Object[]) baseBeanService.getObjectBySqlAndParams(
                                "select sb.BANKACCOUNT,sb.bankname,sb.province,sb.bankaddress,sb.cardholder,sb.branchName,sb.cardType" +
                                        " from dt_hr_staff_BankAccount sb" +
                                        " where sb.bankaccountid= ? ",
                                new Object[]{bankId});

                        //代付第一步，单笔代付生成订单
                        BankWfjBean entity = new BankWfjBean();
                        entity.setCardNo(c[0].toString().trim());
                        entity.setUserName(c[4].toString().trim());
                        entity.setOpenBank(c[1].toString().trim());
                        entity.setProv(c[2].toString().trim());
                        entity.setCity(c[3].toString().trim());
                        entity.setTransAmt(money - 200 + "");
                        entity.setPurpose("其他");

                        entity.setSubBank(c[5] == null ? "" : c[5].toString().trim());
                        entity.setFlag(c[6].toString().trim());//对公对私
                        entity.setVersion("20150304");//固定
                        entity.setTermType("07");

                        TransactionBean pay = new TransactionBean();
                        BeanUtils.copyProperties(entity, pay);
                        Map<String, Object> map = SinglePay.genterOrder(pay);//代付第一步，单笔代付-交易
                        logger.error("第一步：" + map.get("result"));

                        if (map.get("result").equals(0)) {
                            pay = (TransactionBean) map.get("bean");
                            map = null;
                            //生成各种及进行代付第二步单据
                            str = getOrderAndDeal(pay, sccid, customer.getStaffid(), customer.getAccount());
                        } else if (map.get("result").equals(3)) {
                            str = "签名数据不匹配!!!";
                            throw new Exception(str);
                        } else {
                            pay = (TransactionBean) map.get("bean");
                            if (pay.getResponseCode().equals("0100")) {
                                str = "商户提交的字段长度、格式错误,请核实银行卡信息!!!";
                                throw new Exception(str);
                            } else if (pay.getResponseCode().equals("0101")) {
                                str = "商户验签错误,请核实银行卡信息!!!";
                                throw new Exception(str);
                            } else if (pay.getResponseCode().equals("0102")) {
                                str = "手续费计算出错!!!";
                                throw new Exception(str);
                            } else if (pay.getResponseCode().equals("0103")) {
                                str = "商户备付金帐户金额不足,平台余额正在充值,请耐心等待！";
                                throw new Exception(str);
                            } else if (pay.getResponseCode().equals("0104")) {
                                str = "操作拒绝,请核实银行卡信息!!!";
                                throw new Exception(str);
                            }
                        }
                        logger.error(str);
                    } else {
                        str = "库存金币不足！！！";
                        throw new Exception("库存金币不足！！！");
                    }
                } else {
                    str = "商户备付金帐户金额不足,平台余额正在充值,请耐心等待！";
                    throw new Exception("商户备付金帐户金额不足,平台余额正在充值,请耐心等待！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 金币兑现（对接银联代付接口）（二）
     *
     * @param tscBean
     * @param sccid
     * @param staffId
     * @param wfj_user
     * @return
     * @throws Exception
     */
    private String getOrderAndDeal(TransactionBean tscBean, String sccid, String staffId, String wfj_user) throws Exception {
        String str = "";
        logger.error("用户兑现金币数开始。");
        logger.error("sccid：" + sccid + "  staffId:" + staffId + "  wfj_user:" + wfj_user);
        String sql = "select sta.staffname,sta.recordcode from dt_hr_staff sta where sta.staffid = ?";
        Object ret = this.beandao.getObjectBySqlAndParams(sql, new Object[]{staffId});
        String wfj_comid = "";
        String wfj_comname = "";

        TEshopCusCom cusCom = (TEshopCusCom) this.beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
        if (cusCom.getCompanyId() == null) {
            String cushql = "SELECT t.staffid, t.companyid, t.state,t.superioragent,t.pseudo_company_name,t.custype,t.sccid,t.account FROM T_ESHOP_CUSCOM t where t.state=? START WITH t.sccid = ? CONNECT BY PRIOR t.suppersccid = t.sccid";

            List<BaseBean> baseBeans = this.beandao.getListBeanBySqlAndParams(cushql, new Object[]{"2", sccid});
            if (baseBeans.size() > 0) {
                Object object = baseBeans.get(0);
                Object[] objects = (Object[]) object;
                wfj_comid = objects[1].toString();
            } else {
                str = "有参数为空";
                throw new Exception("有参数为空");
            }
        } else {
            wfj_comid = cusCom.getCompanyId();
        }
        wfj_comname = (String) this.beandao.getObjectBySqlAndParams("select c.companyname from dtcompany c where c.companyid=?", new Object[]{wfj_comid});
        if (ret == null) {
            str = "请先完善用户信息！！！";
            throw new Exception("请先完善用户信息！！！");
        }
        Object[] arr = (Object[]) ret;
        if (arr[0] == null) {
            str = "请先完善用户信息！！！";
            throw new Exception("请先完善用户信息！！！");
        }
        String comId = "company201009046vxdyzy4wg0000000025";
        String comName = "北京天太世统科技有限公司";
        String comgn = "groupcompany20120523G3VR9PXHZD0000000021";
        String staname = arr[0].toString();

        String cbid = this.idgec.getServerID("CashierBills");
        String cbid2 = this.idgec.getServerID("CashierBills");
        String cbid3 = this.idgec.getServerID("CashierBills");
        String cbid4 = this.idgec.getServerID("CashierBills");
        System.out.println(cbid);
        System.out.println(cbid2);
        System.out.println(cbid3);
        System.out.println(cbid4);
        String journum = tscBean.getMerSeqId();
        String pzid = this.idgec.getBillID(comId);
        String pzid2 = this.idgec.getBillID(comId);
        String pzid3 = this.idgec.getBillID(comId);
        String pzid4 = this.idgec.getBillID(comId);
        String amt = Float.parseFloat(tscBean.getTransAmt()) / 100 + "";//交易金额
        amt = new BigDecimal(amt).add(new BigDecimal(2)).toString();//总金额(交易金额+2元手续费)

        String hql = "select m from ProductPackaging m where m.goodsName =? and m.companyID=?";
        ProductPackaging gm = (ProductPackaging) this.beandao.getBeanByHqlAndParams(hql, new Object[]{"微分金金币", comId});
        if (gm == null) {
            str = "1操作失误了！！！";
            throw new Exception("金币产品数据错误,1操作失误了！！！");
        }
        ProductPackaging gmm = (ProductPackaging) this.beandao.getBeanByHqlAndParams(hql, new Object[]{"银行存款", comId});
        if (gmm == null) {
            str = "2操作失误了！！！";
            throw new Exception("银行存款数据错误,2操作失误了！！！");
        }
        List<BaseBean> beans = new ArrayList();
        CashierBills cb = null;
        cb = new CashierBills();
        cb.setCashierBillsID(cbid);
        cb.setCashierDate(new Date());
        cb.setBillsType("金币出库单");
        cb.setJournalNum(pzid);
        System.out.println("金币出库单:" + cb.getJournalNum());
        cb.setTrade_no(journum);
        cb.setStaffID(staffId);
        cb.setStaffName(staname);
        cb.setCompanyID(wfj_comid);
        cb.setCompanyName(wfj_comname);
        cb.setInputid(staffId);
        cb.setInputName(staname);
        cb.setInputCompanyid(wfj_comid);
        cb.setInputCompanyName(wfj_comname);
        cb.setStatus("16");
        cb.setCcompanyID("company201009046vxdyzy4wg0000000025");
        cb.setCcompanyName("北京天太世统科技有限公司");
        cb.setCompanyAddr("东直门外大街42号宇飞大厦801室");
        cb.setCompanyTel("010-64167113");
        cb.setCresponsible("刘太平");
        cb.setResponsibleTel("15810799888");
        cb.setAppstyle("07");
        cb.setStatusbill("04");
        cb.setPriceSub(amt);
        cb.setProID("003");
        cb.setProjectName("金币兑现");
        cb.setTrade_no(tscBean.getMerSeqId());

        logger.error("金币出库单:cb.setCompanyID(wfj_comid);=" + wfj_comid + "  cb.setInputCompanyid(wfj_comid)=" + wfj_comid);

        CashierBills cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        beans.add(cb1);

        GoodsBills gb = new GoodsBills();
        gb.setCashierBillsID(cbid);
        gb.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));

        gb.setGoodsID(gm.getGoodsID());
        gb.setGoodsNum(gm.getGoodsNum());
        gb.setGoodsName(gm.getGoodsName());
        gb.setStandard(gm.getStandard());
        gb.setGoodsVariableID(gm.getVariableID());
        gb.setWeight(gm.getWeight());
        gb.setPrice("0.01");
        gb.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100)).toString());
        gb.setMoney(amt);


        gb.setStartDate(null);
        gb.setEndDate(null);
        gb.setBatchNumber(null);
        gb.setPeriodDate(null);
        gb.setRemindedContent(null);
        gb.setCostType(null);
        gb.setPayType("08");
        gb.setPriceManage(null);
        gb.setKcStatus("16");
        gb.setGoodstatus("00");
        gb.setSortCode(null);
        gb.setPpID(gm.getPpID());

        beans.add(gb);

        GoodsBills gb1 = new GoodsBills();
        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid2);
        cb1.setBillsType("收款单");
        cb1.setStatus("45");
        cb1.setJournalNum(pzid2);
        System.out.println("收款单:" + cb1.getJournalNum());
        cb1.setGroupCompanySn(null);


        gb1 = new GoodsBills();
        //BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setCashierBillsID(cbid2);
        gb1.setKcStatus("15");
        gb1.setPrice("1");
        gb1.setGoodsName("金币兑现");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setGoodsVariableID("元");
        gb1.setQuantity(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");
        gb1.setMoney(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");

        beans.add(gb1);
        cb1.setPriceSub(gb1.getMoney());
        beans.add(cb1);

        CashApplyBills cab = new CashApplyBills();
        cab.setCashApplyBillsID(this.idgec.getServerID("CashApplyBills"));

        cab.setCashierBillsID(cbid);
        cab.setAppropriationMoney(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");
        cab.setAppropriateStatus("01");
        cab.setAppropriationcompanyID(comId);
        cab.setAppropriationcompanyName(comName);
        cab.setReceivablesID(staffId);
        cab.setReceivablesName(staname);
        cab.setReceopenBank(tscBean.getOpenBank());
        cab.setReceuserName(tscBean.getUserName());
        cab.setRecropriationNum(tscBean.getCardNo());
        cab.setAppstyle("01");
        beans.add(cab);


        RelatedBill rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid);
        rb.setCashfid(cbid2);
        rb.setBilltype("收款单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid4);
        rb.setBilltype("支款单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid);
        rb.setBilltype("金币入库单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid2);
        rb.setBilltype("收款单");
        beans.add(rb);


        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid3);
        cb1.setBillsType("金币入库单");
        cb1.setStatus("15");
        cb1.setJournalNum(pzid3);
        System.out.println("金币入库单:" + cb1.getJournalNum());
        cb1.setStaffID(null);
        cb1.setStaffName(null);
        cb1.setStaffCode(null);
        cb1.setCompanyID(comId);
        logger.error("金币入库单:cb.setCompanyID(wfj_comid);=" + comId);

        cb1.setCompanyName(comName);
        cb1.setGroupCompanySn(comgn);

        cb1.setInputid(null);
        cb1.setInputName("系统生成");
        cb1.setInputCompanyid(null);
        cb1.setInputCompanyName(null);


        cb1.setCcompanyID(null);
        cb1.setCcompanyName(null);
        cb1.setCompanyAddr(null);
        cb1.setCompanyTel(null);
        cb1.setCresponsible(null);
        cb1.setResponsibleTel(null);

        cb1.setContactUserID(staffId);
        cb1.setCtUserName(staname);
        beans.add(cb1);

        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid3);
        gb1.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100)).toString());
        gb1.setMoney(amt);
        gb1.setKcStatus("15");
        beans.add(gb1);


        boolean flag = ruku(cbid3, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "金币入库单", "0.01", Float.valueOf(Float.parseFloat(amt) * 100.0F), amt, gm.getGoodsID(), "金币仓库", false);
        if (!flag) {
            beans = null;
            str = "金币入库失败,3操作失误了！！！";
            throw new Exception(str);
        }
        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid4);
        cb1.setBillsType("支款单");
        cb1.setStatus("46");
        cb1.setJournalNum(pzid4);
        System.out.println("支款单:" + cb1.getJournalNum());
        cb1.setStaffID(null);

        cb1.setStaffCode(null);
        cb1.setStaffName(null);
        cb1.setCompanyID(comId);
        logger.error("支款单:cb.setCompanyID(wfj_comid);=" + comId);
        cb1.setCompanyName(comName);
        cb1.setGroupCompanySn(comgn);

        cb1.setInputid(null);
        cb1.setInputName("系统生成");
        cb1.setInputCompanyid(null);
        cb1.setInputCompanyName(null);


        cb1.setCcompanyID(null);
        cb1.setCcompanyName(null);
        cb1.setCompanyAddr(null);
        cb1.setCompanyTel(null);
        cb1.setCresponsible(null);
        cb1.setResponsibleTel(null);

        cb1.setContactUserID(staffId);
        cb1.setCtUserName(staname);
        beans.add(cb1);

        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid4);
        gb1.setPrice("1");
        gb1.setGoodsName("金币兑现");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setQuantity(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");
        gb1.setMoney(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");
        gb1.setGoodsVariableID("元");

        flag = ruku(cbid4, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "支款单", "1", Float.parseFloat(tscBean.getTransAmt()) / 100, Float.parseFloat(tscBean.getTransAmt()) / 100 + "", gmm.getGoodsID(), "资金仓库", true);
        if (!flag) {
            beans = null;
            str = "支款单生成失败,4操作失误了！！！";
            throw new Exception(str);
        }
        beans.add(gb1);
        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid4);
        gb1.setGoodsName("手续费");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setPrice("1");
        gb1.setQuantity("2");
        gb1.setMoney("2");
        gb1.setKcStatus("16");
        gb1.setGoodsVariableID("元");
        beans.add(gb1);

        flag = ruku(cbid4, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "支款单", "1", Float.valueOf(2.0F), "2", gmm.getGoodsID(), "资金仓库", true);
        if (!flag) {
            beans = null;
            str = "支款单生成失败，5操作失误了！！！";
            throw new Exception(str);
        }
        WfjJifenDetail edetail = new WfjJifenDetail();
        edetail.setJifenDetailId(this.idgec.getServerID("WfjJifenDetail"));
        String temp = null;
        BigDecimal tempjifen = new BigDecimal(0);
        BaseBean bguize = this.beandao.getBeanByHqlAndParams("from WfjGuize where companyId=? and wfjGuizeId=?", new Object[]{"company201009046vxdyzy4wg0000000025", "69CD270453D54AE4817274AC0D269B71"});
        WfjGuize eguize = (WfjGuize) bguize;

        BaseBean bb = this.beandao.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=?", new Object[]{staffId, sccid});
        String jifenId = ((WfjJifen) bb).getWfjJifenId();
        temp = "减少" + staname + "的金币,提现金币";
        tempjifen = new BigDecimal(amt).multiply(new BigDecimal(100));

        WfjJifen wfjJifen = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where wfjJifenId=? ", new Object[]{jifenId});
        String JifenScore = wfjJifen.getWfjJifenScore();
        float golds = Float.parseFloat(JifenScore) - Float.parseFloat(tempjifen.toString());
        wfjJifen.setWfjJifenScore(golds + "");
        beans.add(wfjJifen);

        edetail.setJifenDetailScore(tempjifen.toString());
        edetail.setWfjGuizeId(eguize.getWfjGuizeId());
        edetail.setWfjJifenId(jifenId);
        edetail.setJifenDetailState(Integer.valueOf(0));
        edetail.setJifenDetailName(temp);
        edetail.setJifenDetailDate(new Date());
        edetail.setWfjCashId(cbid);
        beans.add(edetail);

        WfjTask wt = new WfjTask();
        wt.setWfjTaskId(this.idgec.getServerID("WfjTask"));
        wt.setStaffId(staffId);
        wt.setWfjGuizeId(eguize.getWfjGuizeId());
        wt.setWfjTaskDate(new Date());
        beans.add(wt);

        System.out.println(tscBean.getTransAmt());

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("wfj_comId", wfj_comid);
        boolean isParam = isParamNull(paramMap);
        if (!isParam) {
            str = "有参数为空1";
            throw new Exception(str);
        }

        Map<String, Object> map = SinglePay.payMeth(tscBean);
        TransactionBean tbean = null;
        logger.error("第二部：" + map.get("result"));

        tbean = (TransactionBean) map.get("bean");
        if ((map.get("result").equals(Integer.valueOf(0))) && (tbean.getResponseCode().equals("0000"))) {
            str = "提交成功";
            beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        } else if (tbean.getResponseCode().equals("0100")) {
            str = "商户提交的字段长度、格式错误,请核实银行卡信息!!!";
            throw new Exception(str);
        } else if (tbean.getResponseCode().equals("0101")) {
            str = "商户验签错误,请核实银行卡信息!!!";
            throw new Exception(str);
        } else if (tbean.getResponseCode().equals("0102")) {
            str = "手续费计算出错!!!";
            throw new Exception(str);
        } else if (tbean.getResponseCode().equals("0103")) {
            str = "商户备付金帐户金额不足,平台余额正在充值,请耐心等待！";
            throw new Exception(str);
        } else if (tbean.getResponseCode().equals("0104")) {
            str = "操作拒绝,请核实银行卡信息!!!";
            throw new Exception(str);
        }
        logger.error(str);
        return str;
    }

    /**
     * （人工打款）提交兑现申请各种验证
     *
     * @param sccid
     * @param user
     * @param money
     * @param bankId
     * @return
     * @throws Exception
     */
    @Transactional
    public synchronized String gecOrderAndManualDeal(String sccid, String user, int money, String bankId) throws Exception {

        String str = "";
        try {
            //金币冻结的判断
            TEshopCustomer tucs = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams(" from TEshopCustomer where account= ? and logOff=0", new Object[]{user});
            if (tucs.getStatus() != null && tucs.getStatus().equals("1")) {
                str = "金币账户冻结,不能提现";
                throw new Exception(str);
            }
            logger.error("单笔扣金币");
            //查询账户金币数
            TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{user});
            WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=?", new Object[]{customer.getStaffid(), sccid});
            BigDecimal bigjifen = new BigDecimal(jifen.getWfjJifenScore());
            if (bigjifen.compareTo(new BigDecimal(money + "")) > -1) {
                Object[] c = (Object[]) baseBeanService.getObjectBySqlAndParams(
                        "select sb.BANKACCOUNT,sb.bankname,sb.province,sb.bankaddress,sb.cardholder,sb.branchName,sb.cardType" +
                                " from dt_hr_staff_BankAccount sb" +
                                " where sb.bankaccountid= ? ",
                        new Object[]{bankId});

                str = getOrderAndManualDeal(c, sccid, customer.getStaffid(), customer.getAccount(), money - 200 + "");

                logger.error(str);
            } else {
                str = "库存金币不足！！！";
                throw new Exception(str);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * （人工打款）提交兑现申请扣除金币
     *
     * @param c
     * @param sccid
     * @param staffId
     * @param wfj_user
     * @param totalmoney
     * @return
     * @throws Exception
     */
    @Transactional
    String getOrderAndManualDeal(Object[] c, String sccid, String staffId, String wfj_user, String totalmoney) throws Exception {
        String str = "提交成功";
        logger.error("用户兑现金币数开始。");
        logger.error("sccid：" + sccid + "  staffId:" + staffId + "  wfj_user:" + wfj_user);
        String sql = "select sta.staffname,sta.recordcode from dt_hr_staff sta where sta.staffid = ?";
        Object ret = this.beandao.getObjectBySqlAndParams(sql, new Object[]{staffId});
        String wfj_comid = "";
        String wfj_comname = "";

        TEshopCusCom cusCom = (TEshopCusCom) this.beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
        if (cusCom.getCompanyId() == null) {
            String cushql = "SELECT t.staffid, t.companyid, t.state,t.superioragent,t.pseudo_company_name,t.custype,t.sccid,t.account FROM T_ESHOP_CUSCOM t where t.state=? START WITH t.sccid = ? CONNECT BY PRIOR t.suppersccid = t.sccid";

            List<BaseBean> baseBeans = this.beandao.getListBeanBySqlAndParams(cushql, new Object[]{"2", sccid});
            if (baseBeans.size() > 0) {
                Object object = baseBeans.get(0);
                Object[] objects = (Object[]) object;
                wfj_comid = objects[1].toString();
            } else {
                str = "有参数为空";
                throw new Exception(str);
            }
        } else {
            wfj_comid = cusCom.getCompanyId();
        }
        wfj_comname = (String) this.beandao.getObjectBySqlAndParams("select c.companyname from dtcompany c where c.companyid=?", new Object[]{wfj_comid});
        if (ret == null) {
            str = "请先完善用户信息！！！";
            throw new Exception(str);
        }
        Object[] arr = (Object[]) ret;
        if (arr[0] == null) {
            str = "请先完善用户信息！！！";
            throw new Exception(str);
        }
        String comId = "company201009046vxdyzy4wg0000000025";
        String comName = "北京天太世统科技有限公司";
        String comgn = "groupcompany20120523G3VR9PXHZD0000000021";
        String staname = arr[0].toString();

        String cbid = this.idgec.getServerID("CashierBills");
        String cbid2 = this.idgec.getServerID("CashierBills");
        String cbid3 = this.idgec.getServerID("CashierBills");
        String cbid4 = this.idgec.getServerID("CashierBills");
        System.out.println(cbid);
        System.out.println(cbid2);
        System.out.println(cbid3);
        System.out.println(cbid4);
        String journum = serverService.getBillID(comId);
        String pzid = this.idgec.getBillID(comId);
        String pzid2 = this.idgec.getBillID(comId);
        String pzid3 = this.idgec.getBillID(comId);
        String pzid4 = this.idgec.getBillID(comId);
        String amt = Float.parseFloat(totalmoney) / 100 + "";//交易金额
        amt = new BigDecimal(amt).add(new BigDecimal(2)).toString();//总金额(交易金额+2元手续费)

        String hql = "select m from ProductPackaging m where m.goodsName =? and m.companyID=?";
        ProductPackaging gm = (ProductPackaging) this.beandao.getBeanByHqlAndParams(hql, new Object[]{"微分金金币", comId});
        if (gm == null) {
            str = "金币产品数据错误，1操作失误了！！！";
            throw new Exception(str);
        }
        ProductPackaging gmm = (ProductPackaging) this.beandao.getBeanByHqlAndParams(hql, new Object[]{"银行存款", comId});
        if (gmm == null) {
            str = "银行存款数据错误，2操作失误了！！！";
            throw new Exception(str);
        }
        List<BaseBean> beans = new ArrayList();

        WithDrawApply wda = new WithDrawApply();
        //sb.BANKACCOUNT,sb.bankname,sb.province,sb.bankaddress,sb.cardholder,sb.branchName,sb.cardType
        wda.setSccid(sccid);
        wda.setWdaID(this.idgec.getServerID("withdrawapply"));
        wda.setApplyDate(new Date());
        wda.setMoney(Float.parseFloat(totalmoney) / 100 + "");
        wda.setOrderNum(journum);
        wda.setRecevOpenAccountBank(c[1].toString() + c[5].toString());
        wda.setRecevCardProvince(c[2].toString());
        wda.setRecevCardCity(c[3].toString());
        wda.setReceCardDCode(c[1].toString() == "中国工商银行" ? "0200" : "0000");
        wda.setRecevCardAccount(c[0].toString());
        wda.setRecevCardName(c[4].toString());
        wda.setCurrency("RMB");
        wda.setPayOpenAccountBank("中国工行北京东城东直门支行");
        wda.setPayCardName("北京天太世统科技有限公司");
        wda.setPayCardAccount("0200210709020118801");
        wda.setPayurpose("商家业务员佣金");
        wda.setPayMode("0");
        wda.setPayState("00");
        beans.add(wda);

        CashierBills cb = null;
        cb = new CashierBills();
        cb.setCashierBillsID(cbid);
        cb.setCashierDate(new Date());
        cb.setBillsType("金币出库单");
        cb.setJournalNum(pzid);
        System.out.println("金币出库单:" + cb.getJournalNum());
        cb.setTrade_no(journum);
        cb.setStaffID(staffId);
        cb.setStaffName(staname);
        cb.setCompanyID(wfj_comid);
        cb.setCompanyName(wfj_comname);
        cb.setInputid(staffId);
        cb.setInputName(staname);
        cb.setInputCompanyid(wfj_comid);
        cb.setInputCompanyName(wfj_comname);
        cb.setStatus("16");
        cb.setCcompanyID("company201009046vxdyzy4wg0000000025");
        cb.setCcompanyName("北京天太世统科技有限公司");
        cb.setCompanyAddr("东直门外大街42号宇飞大厦801室");
        cb.setCompanyTel("010-64167113");
        cb.setCresponsible("刘太平");
        cb.setResponsibleTel("15810799888");
        cb.setAppstyle("07");
        cb.setStatusbill("04");
        cb.setPriceSub(amt);
        cb.setProID("003");
        cb.setProjectName("金币兑现");


        logger.error("金币出库单:cb.setCompanyID(wfj_comid);=" + wfj_comid + "  cb.setInputCompanyid(wfj_comid)=" + wfj_comid);

        CashierBills cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        beans.add(cb1);

        GoodsBills gb = new GoodsBills();
        gb.setCashierBillsID(cbid);
        gb.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));

        gb.setGoodsID(gm.getGoodsID());
        gb.setGoodsNum(gm.getGoodsNum());
        gb.setGoodsName(gm.getGoodsName());
        gb.setStandard(gm.getStandard());
        gb.setGoodsVariableID(gm.getVariableID());
        gb.setWeight(gm.getWeight());
        gb.setPrice("0.01");
        gb.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100)).toString());
        gb.setMoney(amt);


        gb.setStartDate(null);
        gb.setEndDate(null);
        gb.setBatchNumber(null);
        gb.setPeriodDate(null);
        gb.setRemindedContent(null);
        gb.setCostType(null);
        gb.setPayType("08");
        gb.setPriceManage(null);
        gb.setKcStatus("16");
        gb.setGoodstatus("00");
        gb.setSortCode(null);
        gb.setPpID(gm.getPpID());

        beans.add(gb);

        GoodsBills gb1 = new GoodsBills();
        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid2);
        cb1.setBillsType("收款单");
        cb1.setStatus("45");
        cb1.setJournalNum(pzid2);
        System.out.println("收款单:" + cb1.getJournalNum());
        cb1.setGroupCompanySn(null);


        gb1 = new GoodsBills();
        //BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setCashierBillsID(cbid2);
        gb1.setKcStatus("15");
        gb1.setPrice("1");
        gb1.setGoodsName("金币兑现");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setGoodsVariableID("元");
        gb1.setQuantity(Float.parseFloat(totalmoney) / 100 + "");
        gb1.setMoney(Float.parseFloat(totalmoney) / 100 + "");

        beans.add(gb1);
        cb1.setPriceSub(gb1.getMoney());
        beans.add(cb1);

        CashApplyBills cab = new CashApplyBills();
        cab.setCashApplyBillsID(this.idgec.getServerID("CashApplyBills"));

        cab.setCashierBillsID(cbid);
        cab.setAppropriationMoney(Float.parseFloat(totalmoney) / 100 + "");
        cab.setAppropriateStatus("01");
        cab.setAppropriationcompanyID(comId);
        cab.setAppropriationcompanyName(comName);
        cab.setReceivablesID(staffId);
        cab.setReceivablesName(staname);

        cab.setReceopenBank(c[1].toString().trim());
        cab.setReceuserName(c[4].toString().trim());
        cab.setRecropriationNum(c[0].toString().trim());
        cab.setAppstyle("01");
        beans.add(cab);


        RelatedBill rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid);
        rb.setCashfid(cbid2);
        rb.setBilltype("收款单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid4);
        rb.setBilltype("支款单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid);
        rb.setBilltype("金币入库单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid2);
        rb.setBilltype("收款单");
        beans.add(rb);


        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid3);
        cb1.setBillsType("金币入库单");
        cb1.setStatus("15");
        cb1.setJournalNum(pzid3);
        System.out.println("金币入库单:" + cb1.getJournalNum());
        cb1.setStaffID(null);
        cb1.setStaffName(null);
        cb1.setStaffCode(null);
        cb1.setCompanyID(comId);
        logger.error("金币入库单:cb.setCompanyID(wfj_comid);=" + comId);

        cb1.setCompanyName(comName);
        cb1.setGroupCompanySn(comgn);

        cb1.setInputid(null);
        cb1.setInputName("系统生成");
        cb1.setInputCompanyid(null);
        cb1.setInputCompanyName(null);


        cb1.setCcompanyID(null);
        cb1.setCcompanyName(null);
        cb1.setCompanyAddr(null);
        cb1.setCompanyTel(null);
        cb1.setCresponsible(null);
        cb1.setResponsibleTel(null);

        cb1.setContactUserID(staffId);
        cb1.setCtUserName(staname);
        beans.add(cb1);

        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid3);
        gb1.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100)).toString());
        gb1.setMoney(amt);
        gb1.setKcStatus("15");
        beans.add(gb1);


        boolean flag = ruku(cbid3, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "金币入库单", "0.01", Float.valueOf(Float.parseFloat(amt) * 100.0F), amt, gm.getGoodsID(), "金币仓库", false);
        if (!flag) {
            beans = null;
            str = "金币入库失败，3操作失误了！！！";
            throw new Exception(str);
        }
        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid4);
        cb1.setBillsType("支款单");
        cb1.setStatus("46");
        cb1.setJournalNum(pzid4);
        System.out.println("支款单:" + cb1.getJournalNum());
        cb1.setStaffID(null);

        cb1.setStaffCode(null);
        cb1.setStaffName(null);
        cb1.setCompanyID(comId);
        logger.error("支款单:cb.setCompanyID(wfj_comid);=" + comId);
        cb1.setCompanyName(comName);
        cb1.setGroupCompanySn(comgn);

        cb1.setInputid(null);
        cb1.setInputName("系统生成");
        cb1.setInputCompanyid(null);
        cb1.setInputCompanyName(null);


        cb1.setCcompanyID(null);
        cb1.setCcompanyName(null);
        cb1.setCompanyAddr(null);
        cb1.setCompanyTel(null);
        cb1.setCresponsible(null);
        cb1.setResponsibleTel(null);

        cb1.setContactUserID(staffId);
        cb1.setCtUserName(staname);
        beans.add(cb1);

        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid4);
        gb1.setPrice("1");
        gb1.setGoodsName("金币兑现");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setQuantity(Float.parseFloat(totalmoney) / 100 + "");
        gb1.setMoney(Float.parseFloat(totalmoney) / 100 + "");
        gb1.setGoodsVariableID("元");

        flag = ruku(cbid4, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "支款单", "1", Float.parseFloat(totalmoney) / 100, Float.parseFloat(totalmoney) / 100 + "", gmm.getGoodsID(), "资金仓库", true);
        if (!flag) {
            beans = null;
            str = "支款单生成失败，4操作失误了！！！";
            throw new Exception(str);
        }
        beans.add(gb1);
        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid4);
        gb1.setGoodsName("手续费");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setPrice("1");
        gb1.setQuantity("2");
        gb1.setMoney("2");
        gb1.setKcStatus("16");
        gb1.setGoodsVariableID("元");
        beans.add(gb1);

        flag = ruku(cbid4, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "支款单", "1", Float.valueOf(2.0F), "2", gmm.getGoodsID(), "资金仓库", true);
        if (!flag) {
            beans = null;
            str = "支款单生成失败，5操作失误了！！！";
            throw new Exception(str);
        }
        WfjJifenDetail edetail = new WfjJifenDetail();
        edetail.setJifenDetailId(this.idgec.getServerID("WfjJifenDetail"));
        String temp = null;
        BigDecimal tempjifen = new BigDecimal(0);
        BaseBean bguize = this.beandao.getBeanByHqlAndParams("from WfjGuize where companyId=? and wfjGuizeId=?", new Object[]{"company201009046vxdyzy4wg0000000025", "69CD270453D54AE4817274AC0D269B71"});
        WfjGuize eguize = (WfjGuize) bguize;

        BaseBean bb = this.beandao.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=?", new Object[]{staffId, sccid});
        String jifenId = ((WfjJifen) bb).getWfjJifenId();
        temp = "减少" + staname + "的金币,提现金币";
        tempjifen = new BigDecimal(amt).multiply(new BigDecimal(100));

        WfjJifen wfjJifen = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where wfjJifenId=? ", new Object[]{jifenId});
        String JifenScore = wfjJifen.getWfjJifenScore();
        float golds = Float.parseFloat(JifenScore) - Float.parseFloat(tempjifen.toString());
        wfjJifen.setWfjJifenScore(golds + "");
        beans.add(wfjJifen);

        edetail.setJifenDetailScore(tempjifen.toString());
        edetail.setWfjGuizeId(eguize.getWfjGuizeId());
        edetail.setWfjJifenId(jifenId);
        edetail.setJifenDetailState(Integer.valueOf(0));
        edetail.setJifenDetailName(temp);
        edetail.setJifenDetailDate(new Date());
        edetail.setWfjCashId(cbid);
        beans.add(edetail);

        WfjTask wt = new WfjTask();
        wt.setWfjTaskId(this.idgec.getServerID("WfjTask"));
        wt.setStaffId(staffId);
        wt.setWfjGuizeId(eguize.getWfjGuizeId());
        wt.setWfjTaskDate(new Date());
        beans.add(wt);

        System.out.println(totalmoney);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("wfj_comId", wfj_comid);
        boolean isParam = isParamNull(paramMap);
        if (!isParam) {
            str = "有参数为空1";
            throw new Exception(str);
        }
        logger.error(str);

        beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);

        return str;
    }

    /**
     * 记录导出信息
     *
     * @param excellist
     */
    @Transactional
    public void saveExportInfo(List<BaseBean> excellist, String staffID) {
        if (excellist.size() != 0) {
            List<BaseBean> beansList = new ArrayList<BaseBean>();
            for (BaseBean b : excellist) {
                WithDrawApply apply = (WithDrawApply) b;
                apply.setExportStaffID(staffID);
                apply.setExportDate(new Date());
                apply.setExportTimes(apply.getExportTimes() + 1);
                beansList.add(apply);

            }
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beansList, null, null);
        }
    }

    /**
     * 手动扣金币
     *
     * @param merSeqId
     * @param sccid
     * @param wfj_user
     * @param money
     * @param bankId
     * @return
     * @throws Exception
     */
    @Transactional
    public String test(String merSeqId, String sccid, String wfj_user, int money, String bankId) throws Exception {
        String str = "";
        logger.error("单笔代付");
        //查询账户金币数
        TEshopCustomer customer = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{wfj_user});
        WfjJifen jifen = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=?", new Object[]{customer.getStaffid(), sccid});
        BigDecimal bigjifen = new BigDecimal(jifen.getWfjJifenScore());
        if (bigjifen.compareTo(new BigDecimal(money + "")) > -1) {
            Object[] c = (Object[]) baseBeanService.getObjectBySqlAndParams(
                    "select sb.BANKACCOUNT,sb.bankname,sb.province,sb.bankaddress,sb.cardholder,sb.branchName,sb.cardType" +
                            " from dt_hr_staff_BankAccount sb" +
                            " where sb.bankaccountid= ? ",
                    new Object[]{bankId});

            //代付第一步，单笔代付生成订单
            BankWfjBean tscBean = new BankWfjBean();
            tscBean.setCardNo(c[0].toString().trim());
            tscBean.setUserName(c[4].toString().trim());
            tscBean.setOpenBank(c[1].toString().trim());
            tscBean.setProv(c[2].toString().trim());
            tscBean.setCity(c[3].toString().trim());
            tscBean.setTransAmt(money - 200 + "");
            tscBean.setPurpose("金币提现");

            tscBean.setSubBank(c[5] == null ? "" : c[5].toString().trim());
            tscBean.setFlag(c[6].toString().trim());//对公对私
            tscBean.setVersion("20150304");//固定
            tscBean.setTermType("07");
            tscBean.setMerSeqId(merSeqId);

            String staffId = customer.getStaffid();

            logger.error("用户兑现金币数开始。");
            logger.error("sccid：" + sccid + "  staffId:" + staffId + "  wfj_user:" + wfj_user);
            String sql = "select sta.staffname,sta.recordcode from dt_hr_staff sta where sta.staffid = ?";
            Object ret = this.beandao.getObjectBySqlAndParams(sql, new Object[]{staffId});
            String wfj_comid = "";
            String wfj_comname = "";

            TEshopCusCom cusCom = (TEshopCusCom) this.beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
            if (cusCom.getCompanyId() == null) {
                String cushql = "SELECT t.staffid, t.companyid, t.state,t.superioragent,t.pseudo_company_name,t.custype,t.sccid,t.account FROM T_ESHOP_CUSCOM t where t.state=? START WITH t.sccid = ? CONNECT BY PRIOR t.suppersccid = t.sccid";

                List<BaseBean> baseBeans = this.beandao.getListBeanBySqlAndParams(cushql, new Object[]{"2", sccid});
                if (baseBeans.size() > 0) {
                    Object object = baseBeans.get(0);
                    Object[] objects = (Object[]) object;
                    wfj_comid = objects[1].toString();
                } else {
                    str = "有参数为空";
                    throw new Exception(str);
                }
            } else {
                wfj_comid = cusCom.getCompanyId();
            }
            wfj_comname = (String) this.beandao.getObjectBySqlAndParams("select c.companyname from dtcompany c where c.companyid=?", new Object[]{wfj_comid});
            if (ret == null) {
                str = "请先完善用户信息！！！";
                throw new Exception(str);
            }
            Object[] arr = (Object[]) ret;
            if (arr[0] == null) {
                str = "请先完善用户信息！！！";
                throw new Exception(str);
            }
            String comId = "company201009046vxdyzy4wg0000000025";
            String comName = "北京天太世统科技有限公司";
            String comgn = "groupcompany20120523G3VR9PXHZD0000000021";
            String staname = arr[0].toString();

            String cbid = this.idgec.getServerID("CashierBills");
            String cbid2 = this.idgec.getServerID("CashierBills");
            String cbid3 = this.idgec.getServerID("CashierBills");
            String cbid4 = this.idgec.getServerID("CashierBills");
            System.out.println(cbid);
            System.out.println(cbid2);
            System.out.println(cbid3);
            System.out.println(cbid4);
            String journum = tscBean.getMerSeqId();
            String pzid = this.idgec.getBillID(comId);
            String pzid2 = this.idgec.getBillID(comId);
            String pzid3 = this.idgec.getBillID(comId);
            String pzid4 = this.idgec.getBillID(comId);
            String amt = Float.parseFloat(tscBean.getTransAmt()) / 100 + "";//交易金额
            amt = new BigDecimal(amt).add(new BigDecimal(2)).toString();//总金额(交易金额+2元手续费)

            String hql = "select m from ProductPackaging m where m.goodsName =? and m.companyID=?";
            ProductPackaging gm = (ProductPackaging) this.beandao.getBeanByHqlAndParams(hql, new Object[]{"微分金金币", comId});
            if (gm == null) {
                str = "金币产品信息错误，1操作失误了！！！";
                throw new Exception(str);
            }
            ProductPackaging gmm = (ProductPackaging) this.beandao.getBeanByHqlAndParams(hql, new Object[]{"银行存款", comId});
            if (gmm == null) {
                str = "银行存款产品信息错误，2操作失误了！！！";
                throw new Exception(str);
            }
            List<BaseBean> beans = new ArrayList();
            CashierBills cb = null;
            cb = new CashierBills();
            cb.setCashierBillsID(cbid);
            cb.setCashierDate(new Date());
            cb.setBillsType("金币出库单");
            cb.setJournalNum(pzid);
            System.out.println("金币出库单:" + cb.getJournalNum());
            cb.setTrade_no(journum);
            cb.setStaffID(staffId);
            cb.setStaffName(staname);
            cb.setCompanyID(wfj_comid);
            cb.setCompanyName(wfj_comname);
            cb.setInputid(staffId);
            cb.setInputName(staname);
            cb.setInputCompanyid(wfj_comid);
            cb.setInputCompanyName(wfj_comname);
            cb.setStatus("16");
            cb.setCcompanyID("company201009046vxdyzy4wg0000000025");
            cb.setCcompanyName("北京天太世统科技有限公司");
            cb.setCompanyAddr("东直门外大街42号宇飞大厦801室");
            cb.setCompanyTel("010-64167113");
            cb.setCresponsible("刘太平");
            cb.setResponsibleTel("15810799888");
            cb.setAppstyle("07");
            cb.setStatusbill("04");
            cb.setPriceSub(amt);
            cb.setProID("003");
            cb.setProjectName("金币兑现");
            cb.setTrade_no(tscBean.getMerSeqId());

            logger.error("金币出库单:cb.setCompanyID(wfj_comid);=" + wfj_comid + "  cb.setInputCompanyid(wfj_comid)=" + wfj_comid);

            CashierBills cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
            cb1 = (CashierBills) cb.cloneCashierBills();
            beans.add(cb1);

            GoodsBills gb = new GoodsBills();
            gb.setCashierBillsID(cbid);
            gb.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));

            gb.setGoodsID(gm.getGoodsID());
            gb.setGoodsNum(gm.getGoodsNum());
            gb.setGoodsName(gm.getGoodsName());
            gb.setStandard(gm.getStandard());
            gb.setGoodsVariableID(gm.getVariableID());
            gb.setWeight(gm.getWeight());
            gb.setPrice("0.01");
            gb.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100)).toString());
            gb.setMoney(amt);


            gb.setStartDate(null);
            gb.setEndDate(null);
            gb.setBatchNumber(null);
            gb.setPeriodDate(null);
            gb.setRemindedContent(null);
            gb.setCostType(null);
            gb.setPayType("08");
            gb.setPriceManage(null);
            gb.setKcStatus("16");
            gb.setGoodstatus("00");
            gb.setSortCode(null);
            gb.setPpID(gm.getPpID());

            beans.add(gb);

            GoodsBills gb1 = new GoodsBills();
            cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
            cb1 = (CashierBills) cb.cloneCashierBills();
            cb1.setCashierBillsID(cbid2);
            cb1.setBillsType("收款单");
            cb1.setStatus("45");
            cb1.setJournalNum(pzid2);
            System.out.println("收款单:" + cb1.getJournalNum());
            cb1.setGroupCompanySn(null);


            gb1 = new GoodsBills();
            //BeanUtils.copyProperties(gb1, gb);
            gb1 = (GoodsBills) gb.cloneGoodsBills();
            gb1.setCashierBillsID(cbid2);
            gb1.setKcStatus("15");
            gb1.setPrice("1");
            gb1.setGoodsName("金币兑现");
            gb1.setGoodsID(null);
            gb1.setPpID(null);
            gb1.setGoodsVariableID("元");
            gb1.setQuantity(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");
            gb1.setMoney(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");

            beans.add(gb1);
            cb1.setPriceSub(gb1.getMoney());
            beans.add(cb1);

            CashApplyBills cab = new CashApplyBills();
            cab.setCashApplyBillsID(this.idgec.getServerID("CashApplyBills"));

            cab.setCashierBillsID(cbid);
            cab.setAppropriationMoney(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");
            cab.setAppropriateStatus("01");
            cab.setAppropriationcompanyID(comId);
            cab.setAppropriationcompanyName(comName);
            cab.setReceivablesID(staffId);
            cab.setReceivablesName(staname);
            cab.setReceopenBank(tscBean.getOpenBank());
            cab.setReceuserName(tscBean.getUserName());
            cab.setRecropriationNum(tscBean.getCardNo());
            cab.setAppstyle("01");
            beans.add(cab);


            RelatedBill rb = new RelatedBill();
            rb.setRbID(this.idgec.getServerID("RelatedBill"));
            rb.setCashid(cbid);
            rb.setCashfid(cbid2);
            rb.setBilltype("收款单");
            beans.add(rb);

            rb = new RelatedBill();
            rb.setRbID(this.idgec.getServerID("RelatedBill"));
            rb.setCashid(cbid3);
            rb.setCashfid(cbid4);
            rb.setBilltype("支款单");
            beans.add(rb);

            rb = new RelatedBill();
            rb.setRbID(this.idgec.getServerID("RelatedBill"));
            rb.setCashid(cbid3);
            rb.setCashfid(cbid);
            rb.setBilltype("金币入库单");
            beans.add(rb);

            rb = new RelatedBill();
            rb.setRbID(this.idgec.getServerID("RelatedBill"));
            rb.setCashid(cbid3);
            rb.setCashfid(cbid2);
            rb.setBilltype("收款单");
            beans.add(rb);


            cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
            cb1 = (CashierBills) cb.cloneCashierBills();
            cb1.setCashierBillsID(cbid3);
            cb1.setBillsType("金币入库单");
            cb1.setStatus("15");
            cb1.setJournalNum(pzid3);
            System.out.println("金币入库单:" + cb1.getJournalNum());
            cb1.setStaffID(null);
            cb1.setStaffName(null);
            cb1.setStaffCode(null);
            cb1.setCompanyID(comId);
            logger.error("金币入库单:cb.setCompanyID(wfj_comid);=" + comId);

            cb1.setCompanyName(comName);
            cb1.setGroupCompanySn(comgn);

            cb1.setInputid(null);
            cb1.setInputName("系统生成");
            cb1.setInputCompanyid(null);
            cb1.setInputCompanyName(null);


            cb1.setCcompanyID(null);
            cb1.setCcompanyName(null);
            cb1.setCompanyAddr(null);
            cb1.setCompanyTel(null);
            cb1.setCresponsible(null);
            cb1.setResponsibleTel(null);

            cb1.setContactUserID(staffId);
            cb1.setCtUserName(staname);
            beans.add(cb1);

            gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
            gb1 = (GoodsBills) gb.cloneGoodsBills();
            gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
            gb1.setCashierBillsID(cbid3);
            gb1.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100)).toString());
            gb1.setMoney(amt);
            gb1.setKcStatus("15");
            beans.add(gb1);


            boolean flag = ruku(cbid3, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "金币入库单", "0.01", Float.valueOf(Float.parseFloat(amt) * 100.0F), amt, gm.getGoodsID(), "金币仓库", false);
            if (!flag) {
                beans = null;
                str = "金币入库失败，3操作失误了！！！";
                throw new Exception(str);
            }
            cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
            cb1 = (CashierBills) cb.cloneCashierBills();
            cb1.setCashierBillsID(cbid4);
            cb1.setBillsType("支款单");
            cb1.setStatus("46");
            cb1.setJournalNum(pzid4);
            System.out.println("支款单:" + cb1.getJournalNum());
            cb1.setStaffID(null);

            cb1.setStaffCode(null);
            cb1.setStaffName(null);
            cb1.setCompanyID(comId);
            logger.error("支款单:cb.setCompanyID(wfj_comid);=" + comId);
            cb1.setCompanyName(comName);
            cb1.setGroupCompanySn(comgn);

            cb1.setInputid(null);
            cb1.setInputName("系统生成");
            cb1.setInputCompanyid(null);
            cb1.setInputCompanyName(null);


            cb1.setCcompanyID(null);
            cb1.setCcompanyName(null);
            cb1.setCompanyAddr(null);
            cb1.setCompanyTel(null);
            cb1.setCresponsible(null);
            cb1.setResponsibleTel(null);

            cb1.setContactUserID(staffId);
            cb1.setCtUserName(staname);
            beans.add(cb1);

            gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
            gb1 = (GoodsBills) gb.cloneGoodsBills();
            gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
            gb1.setCashierBillsID(cbid4);
            gb1.setPrice("1");
            gb1.setGoodsName("金币兑现");
            gb1.setGoodsID(null);
            gb1.setPpID(null);
            gb1.setQuantity(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");
            gb1.setMoney(Float.parseFloat(tscBean.getTransAmt()) / 100 + "");
            gb1.setGoodsVariableID("元");

            flag = ruku(cbid4, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "支款单", "1", Float.parseFloat(tscBean.getTransAmt()) / 100, Float.parseFloat(tscBean.getTransAmt()) / 100 + "", gmm.getGoodsID(), "资金仓库", true);
            if (!flag) {
                beans = null;
                str = "支款单生成失败，4操作失误了！！！";
                throw new Exception(str);
            }
            beans.add(gb1);
            gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
            gb1 = (GoodsBills) gb.cloneGoodsBills();
            gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
            gb1.setCashierBillsID(cbid4);
            gb1.setGoodsName("手续费");
            gb1.setGoodsID(null);
            gb1.setPpID(null);
            gb1.setPrice("1");
            gb1.setQuantity("2");
            gb1.setMoney("2");
            gb1.setKcStatus("16");
            gb1.setGoodsVariableID("元");
            beans.add(gb1);

            flag = ruku(cbid4, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "支款单", "1", Float.valueOf(2.0F), "2", gmm.getGoodsID(), "资金仓库", true);
            if (!flag) {
                beans = null;
                str = "支款单生成失败，5操作失误了！！！";
                throw new Exception(str);
            }
            WfjJifenDetail edetail = new WfjJifenDetail();
            edetail.setJifenDetailId(this.idgec.getServerID("WfjJifenDetail"));
            String temp = null;
            BigDecimal tempjifen = new BigDecimal(0);
            BaseBean bguize = this.beandao.getBeanByHqlAndParams("from WfjGuize where companyId=? and wfjGuizeId=?", new Object[]{"company201009046vxdyzy4wg0000000025", "69CD270453D54AE4817274AC0D269B71"});
            WfjGuize eguize = (WfjGuize) bguize;

            BaseBean bb = this.beandao.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=?", new Object[]{staffId, sccid});
            String jifenId = ((WfjJifen) bb).getWfjJifenId();
            temp = "减少" + staname + "的金币,提现金币";
            tempjifen = new BigDecimal(amt).multiply(new BigDecimal(100));

            WfjJifen wfjJifen = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where wfjJifenId=? ", new Object[]{jifenId});
            String JifenScore = wfjJifen.getWfjJifenScore();
            float golds = Float.parseFloat(JifenScore) - Float.parseFloat(tempjifen.toString());
            wfjJifen.setWfjJifenScore(golds + "");
            beans.add(wfjJifen);

            edetail.setJifenDetailScore(tempjifen.toString());
            edetail.setWfjGuizeId(eguize.getWfjGuizeId());
            edetail.setWfjJifenId(jifenId);
            edetail.setJifenDetailState(Integer.valueOf(0));
            edetail.setJifenDetailName(temp);
            edetail.setJifenDetailDate(new Date());
            edetail.setWfjCashId(cbid);
            beans.add(edetail);

            WfjTask wt = new WfjTask();
            wt.setWfjTaskId(this.idgec.getServerID("WfjTask"));
            wt.setStaffId(staffId);
            wt.setWfjGuizeId(eguize.getWfjGuizeId());
            wt.setWfjTaskDate(new Date());
            beans.add(wt);

            System.out.println(tscBean.getTransAmt());

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("wfj_comId", wfj_comid);
            boolean isParam = isParamNull(paramMap);
            if (!isParam) {
                str = "有参数为空1";
                throw new Exception(str);
            }
            beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        } else {
            str = "库存金币不足！！！";
            throw new Exception(str);
        }
        logger.error(str);
        return str;
    }


    /**
     * 判断参数是否为空
     *
     * @param
     * @return
     */
    private boolean isParamNull(Map<String, Object> paramMap) {
        boolean flag = true;
        for (String str : paramMap.keySet()) {
            if (paramMap.get(str) == null) {
                logger.error(str + "为空");
                flag = false;
            }
        }
        return flag;
    }

    //入库
    private boolean ruku(String cbid, String gbid, List<BaseBean> list, String comId, String groupCompanySn, String staffId, String staName, String billType, String price, Float count, String money, String goodsId, String kucunName, boolean subtract) {
        try {

            String stockInvId = this.idgec.getServerID("stockInv");
            String inventoryId = this.idgec.getServerID("Inventory");


            String depotHql = "from DepotManage where companyID=? and depotName=? and depotState!=?";
            DepotManage depot = (DepotManage) this.beandao.getBeanByHqlAndParams(depotHql, new Object[]{comId, kucunName, "01"});

            FinancialBill fin = new FinancialBill();
            fin.setFinancialbillID(this.idgec.getServerID("FinancialBill"));
            fin.setCashierBillsID(cbid);
            fin.setGroupCompanySn(groupCompanySn);
            fin.setCompanyID(comId);
            fin.setOrganizationID(null);
            fin.setStaffsID(staffId);
            fin.setStaffsName(staName);
            fin.setJournalNum(this.idgec.getBillID(comId));
            fin.setBillsDate(new Date());
            fin.setBillStaffID(staffId);
            fin.setBillStaffName(staName);
            fin.setBillsType(billType);
            fin.setDepotID(depot.getDepotID());
            fin.setDepotName(depot.getDepotName());
            list.add(fin);

            Object[] obj = {comId, goodsId};
            String invHql = "from Inventory where companyID=? and goodsID=? and warehouse=?";
            Inventory inv = (Inventory) this.beandao.getBeanByHqlAndParams(invHql, new Object[]{comId, goodsId, depot.getDepotID()});
            String manageHql = "from ProductPackaging where companyID=? and goodsID=?";
            ProductPackaging manage = (ProductPackaging) this.beandao.getBeanByHqlAndParams(manageHql, obj);
            stockInv sto = new stockInv();
            sto.setStockinvID(stockInvId);
            sto.setCompanyID(comId);
            sto.setGroupCompanySn(groupCompanySn);
            sto.setGoodsBillsId(gbid);
            sto.setGoodsID(manage.getGoodsID());
            sto.setGoodsType(manage.getTradeID());
            sto.setGoodsName(manage.getGoodsName());
            sto.setInvenQuantity(count + "");
            sto.setSummoney(money);
            sto.setIntime(new Date());
            if (subtract) {
                sto.setType("01");
            } else {
                sto.setType("00");
            }
            sto.setWarehouse(depot.getDepotID());
            sto.setWarehouseName(depot.getDepotName());
            list.add(sto);
            if (inv == null) {
                inv = new Inventory();
                inv.setInventoryID(inventoryId);
                inv.setCompanyID(comId);
                inv.setGroupCompanySn(groupCompanySn);
                inv.setWarehouse(depot.getDepotID());
                inv.setWarehouseName(depot.getDepotName());
                inv.setGoodsID(manage.getGoodsID());
                inv.setGoodsName(manage.getGoodsName());
                inv.setGoodsType(manage.getTradeID());
                inv.setStandard(manage.getStandard());
                inv.setGoodsCoding(manage.getGoodsNum());
                inv.setUnitPrice(price);
                inv.setInvenQuantity(count + "");
                inv.setSumPrice(money);
                inv.setGoodstatus("00");
                list.add(inv);
            } else {
                inv.setInvenQuantity(Float.parseFloat(inv.getInvenQuantity()) + count + "");
                BigDecimal ret = new BigDecimal(inv.getSumPrice());
                if (subtract) {
                    ret.subtract(new BigDecimal(money));
                } else {
                    ret.add(new BigDecimal(money));
                }
                inv.setSumPrice(ret.toString());
                list.add(inv);
            }
            subtract = true;
        } catch (Exception e) {
            e.printStackTrace();
            subtract = false;
        }
        return subtract;
    }


    public PageForm getWdaList(PageForm pageForm, int pageNumber, Object[] params) {

        StringBuilder sql = new StringBuilder("select w.wdaID,w.applyDate,s.staffname,t.account,w.orderNum,w.recevOpenAccountBank,");
        sql.append(" w.recevCardProvince,w.recevCardCity,w.receCardDCode,w.recevCardName,w.recevCardAccount,w.money,w.payMode,");
        sql.append(" w.payOperatorName,w.tradeCode,w.payState,w.exportTimes");
        sql.append(" from dtwithdrawapply w,t_eshop_cuscom t,dt_hr_staff s");
        sql.append(" where w.sccid=t.sccid and t.staffid=s.staffid");
        List<Object> par = new ArrayList<Object>();
        if (params.length > 0) {
            if (params[0] != null && !params[0].equals("")) {
                sql.append(" and s.staffname like ? or t.account like ?");
                par.add("%" + params[0] + "%");
                par.add("%" + params[0] + "%");
            }
            if (params[1] != null && !params[1].equals("")) {
                sql.append(" and w.orderNum= ?");
                par.add(params[1]);
            }
            if (params[2] != null && !params[2].equals("")) {
                sql.append(" and w.payOperatorName like ?");
                par.add("%" + params[2] + "%");
            }
            if (params[3] != null && !params[3].equals("")) {
                sql.append(" and w.payState = ?");
                par.add(params[3]);
            }
            if (params[4] != null && !params[4].equals("") && params[5] != null && !params[5].equals("")) {
                sql.append(" and applyDate between ? and ?");
                par.add(Utilities.getDateFromString(params[4].toString(),
                        "yyyy-MM-dd hh:mm:ss"));
                par.add(Utilities.getDateFromString(params[5].toString(),
                        "yyyy-MM-dd hh:mm:ss"));
            }
        }
        sql.append(" order by w.applyDate desc");
        pageForm = baseBeanService.getPageFormBySQL(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber), sql.toString(), "select count(*) "
                        + sql.substring(sql.indexOf("from")), par.toArray());

        return pageForm;
    }

    /**
     * 导出查询
     *
     * @param companyID
     * @param exportNum
     * @return
     */
    public List<BaseBean> getExcelList(String companyID, int exportNum, Object[] params) {
        List<BaseBean> excellist = new ArrayList<BaseBean>();


        StringBuilder sb = new StringBuilder();
        sb.append("from WithDrawApply w where 1=1");
        //parms.add(companyID);
        List<Object> par = new ArrayList<Object>();
        if (params.length > 0) {
            if (params[0] != null && !params[0].equals("")) {
                sb.append(" and s.staffname like ? and t.account like ?");
                par.add("%" + params[0] + "%");
                par.add("%" + params[0] + "%");
            }
            if (params[1] != null && !params[1].equals("")) {
                sb.append(" and w.orderNum= ?");
                par.add(params[1]);
            }
            if (params[2] != null && !params[2].equals("")) {
                sb.append(" and w.payOperatorName like ?");
                par.add("%" + params[2] + "%");
            }
            if (params[3] != null && !params[3].equals("")) {
                sb.append(" and w.payState = ?");
                par.add(params[3]);
            }
            if (params[4] != null && !params[4].equals("") && params[5] != null && !params[5].equals("")) {
                sb.append(" and applyDate between ? and ?");
                par.add(Utilities.getDateFromString(params[4] + " 00:00:00",
                        "yyyy-MM-dd hh:mm:ss"));
                par.add(Utilities.getDateFromString(params[5] + " 23:59:59",
                        "yyyy-MM-dd hh:mm:ss"));
            }
        }
        sb.append(" order by w.applyDate desc");
        excellist = baseBeanDao.getListBeanByHqlAndParams(sb.toString(), par.toArray());

        return excellist;
    }

    @Override
    public WithDrawApply getQueryAudit(String wdaID) {
        String hql = " from WithDrawApply  where wdaID=?";
        WithDrawApply wday = (WithDrawApply) baseBeanDao.getObjectByHqlAndParams(hql.toString(), new String[]{wdaID});
        return wday;
    }

    @Transactional
    public boolean addAudit(String tradeCode, String receiptOprName, String payOperatorName, String wdaID, String payState, String auditOpinion, String flag, String payOperatorID, String receiptOprID) {
        boolean b = false;
        String hql = "from WithDrawApply where wdaID=?";
        WithDrawApply wday = (WithDrawApply) baseBeanDao.getObjectByHqlAndParams(hql.toString(), new String[]{wdaID});
        List<BaseBean> beans = new ArrayList();
        if ("02".equals(flag)) {
            wday.setPayOperatorName(payOperatorName);
            wday.setPayState(payState);
            wday.setAuditOpinion(auditOpinion);
            wday.setPayOperatorID(payOperatorID);

        } else {
            wday.setTradeCode(tradeCode);
            wday.setReceiptOprName(receiptOprName);
            wday.setReceiptOprID(receiptOprID);
        }
        beans.add(wday);
        baseBeanDao.saveBeansListAndexecuteSqlsByParams(beans, null, null);
        b = true;
        return b;
    }

    @Transactional
    public boolean buyComany(String goodname, TEshopCustomer cus, Company company, CAccount account, List<BaseBean> beans) {
        CDetail cdl = new CDetail();
        String hql = "from Staff d where d.staffID=? ";
        Staff sf = (Staff) this.beandao.getBeanByHqlAndParams(hql, new String[]{cus.getStaffid()});

        company.setCompanyID(this.idgec.getServerID("company"));
        company.setCompanyRegisterDate(new Date());
        company.setIsst(0);
        company.setShowwechat("01");
        company.setCodeID("RMB");
        company.setTotalSales("0");
        company.setCompanyPID(this.idgec.getServerID("pcompany"));
        company.setDistrictID(this.idgec.getServerID("district"));
        company.setGroupCompanySn(this.idgec.getServerID("groupCompanySn"));
        company.setCompanyLicenseCount(5);
        company.setCompanyStatus("01");
        company.setCompanyType("11");
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
        } else if (goodname.equals("0元加入")) {
            company.setCcomtype("6");
        } else {
            return false;
        }
        account.setAccountID(this.idgec.getServerID("caccount"));
        account.setCompanyID(company.getCompanyID());
        account.setAccountName("系统管理员");
        account.setStaffID(cus.getStaffid());
        account.setAccountName(cus.getAccount());
        account.setRoleID(this.idgec.getServerID("role"));
        account.setAccountEmail("sa");

        account.setAccountPassword(Utilities.MD5("123456"));

        account.setAccountStatus("00");
        account.setCompany(company);

        ContactCompany ccy = new ContactCompany();
        ccy.setCcompanyID(this.idgec.getServerID("ContactCompany"));
        ccy.setCompanyName(company.getCompanyName());
        ccy.setAddress(cdl.getCompanyAddress());
        ccy.setCompanyAddr(cdl.getCompanyAddress());
        ccy.setCompanyTel(cdl.getCompanyPhone());
        ccy.setCresponsible(cdl.getCompanyManager());
        ccy.setResponsibleTel(sf.getReference());
        ccy.setCustStatus("01");
        ccy.setEntryoName("微分金 ");
        ccy.setEntryPersonnel("微分金购买");
        ccy.setIndustryType(company.getIndustryType());
        ccy.setRemark("微分金购买");
        ccy.setDealIn(company.getIndustryType());


        cdl.setDetailID(this.idgec.getServerID("CDetail"));
        cdl.setCompanyID(company.getCompanyID());


        CcomCom rela = new CcomCom();
        rela.setCcomRelationId(this.idgec.getServerID("CcomCom"));
        rela.setComanyId(company.getCompanyID());
        rela.setCcompanyId(ccy.getCcompanyID());
        if (this.registerService.register(company, ccy, cdl, account)) {
            this.beandao.executeHqlsByParmsList(beans, null, null);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void registerCompanyInfo(String ccmomtype, TEshopCusCom scc, Company company, CDetail cdl) {
         if(company.getPname()==null){
             company.setPname("");
         }
        if(company.getCityname()==null){
            company.setCityname("");
        }
        if(company.getAdname()==null){
            company.setAdname("");
        }
        if(company.getStreet()==null){
            company.setStreet("");
        }


        try {

            List<BaseBean> beans = new ArrayList();

            Company company1 = (Company) this.beandao.getBeanByHqlAndParams("from Company where companyName = ?", new Object[]{company.getCompanyName()});
            if (company1 == null) {
                company.setCompanyID(this.idgec.getServerID("company"));

                if(cdl!=null&&cdl.getLogo()!=null&&cdl.getLogo().indexOf("autonavi")!=-1){
                    String logo = DownloadImage.download(cdl.getLogo(), UUID.randomUUID()+".jpg", "upload_files/"+company.getCompanyID()+"/contactcompany/"+Utilities.getDateString(new Date(),"yyy-MM-dd"),ServletActionContext.getServletContext().getRealPath(""));
                    cdl.setLogo(logo);
                }


                company.setCompanyPID(this.idgec.getServerID("pcompany"));
                company.setCompanyRegisterDate(new Date());
                company.setGroupCompanySn(this.idgec.getServerID("groupCompanySn"));

                company.setCompanyRegisterDate(new Date());

                company.setCompanyLicenseCount(5);
                company.setTotalSales("0");
                company.setCompanyStatus("00");
                company.setCompanyType("01");
                if(company.getCcomtype()==null||company.getCcomtype().equals("")){
                    company.setCcomtype("6");
                }

                if (company.getCompanyIdentifier() != null) {
                    company.setCompanyIdentifier(company.getCompanyIdentifier().trim().toLowerCase());
                }else{
                    company.setCompanyIdentifier(company.getCompanyName());
                }
                cdl.setDetailID(this.idgec.getServerID("cdetail"));
                cdl.setCompanyID(company.getCompanyID());
                cdl.setCompanyAddress((company.getPname()==company.getCityname()?"":company.getPname())+company.getCityname()+company.getAdname()+company.getStreet());


                CAccount account = new CAccount();
                account.setAccountID(this.idgec.getServerID("caccount"));
                account.setStaffID(scc.getStaffid());
                account.setCompanyID(company.getCompanyID());
                account.setAccountName("系统管理员");

                account.setAccountEmail("sa");

                account.setAccountPassword(Utilities.MD5("123456"));

                account.setAccountStatus("00");

                this.registerService.register(company, cdl, account);


                saveDepot(company.getCompanyID(),beans);
            } else {
                company = company1;
            }
            String hqlcom = "from ContactCompany where companyName = ?";
            ContactCompany contactCompany = null;
            contactCompany = (ContactCompany) this.beandao.getBeanByHqlAndParams(hqlcom, new Object[]{company.getCompanyName()});
            if (contactCompany == null) {
                contactCompany = new ContactCompany();
                contactCompany.setCcompanyID(this.idgec.getServerID("contactCompany"));
                contactCompany.setWebstatus("01");
                contactCompany.setCustStatus("02");
                contactCompany.setIndustryType(company.getIndustryType());
                contactCompany.setIndustryId(company.getIndustryId());
                contactCompany.setCompanyName(company.getCompanyName());
                contactCompany.setAuthState("00");
                contactCompany.setAccuracy(company.getAccuracy());
                contactCompany.setDimension(company.getDimension());
                contactCompany.setShopname(company.getShopname());
                contactCompany.setCompanyAddr((company.getPname()==company.getCityname()?"":company.getPname())+company.getCityname()+company.getAdname()+company.getStreet());
                contactCompany.setGdcate(company.getGdcate());
                contactCompany.setGdcode(company.getGdcode());
                contactCompany.setGdcate2(company.getGdcate2());
                contactCompany.setGdcode2(company.getGdcode2());
                contactCompany.setGdID(company.getGdID());
                if(cdl!=null) {
                    contactCompany.setCompanyTel(cdl.getCompanyPhone());
                    contactCompany.setCresponsible(cdl.getCompanyManager());
                    contactCompany.setResponsibleTel(cdl.getManagertel());
                    if(cdl.getLogo()!=null&&!cdl.getLogo().equals("")) {
                        contactCompany.setLogoPath(cdl.getLogo());
                    }
                }



                if (ccmomtype.equals("0")) {
                    contactCompany.setPcState("1");
                }
                beans.add(contactCompany);
            }
            CcomCom ccomCom = null;
            ccomCom = (CcomCom) this.beandao.getBeanByHqlAndParams("from CcomCom where ccompanyId = ? and comanyId = ?", new Object[]{contactCompany.getCcompanyID(), company.getCompanyID()});
            if (ccomCom == null) {
                ccomCom = new CcomCom();
                ccomCom.setCcomRelationId(this.idgec.getServerID("ccomcom"));
                ccomCom.setCcompanyId(contactCompany.getCcompanyID());
                ccomCom.setComanyId(company.getCompanyID());
                ccomCom.setState("0");
                beans.add(ccomCom);
            }
            scc.setCompanyId(company.getCompanyID());
            scc.setState("2");
            System.out.println("scc公司ID:" + scc.getCompanyId());
            scc.setPseudoCompanyName(company.getCompanyName());
            beans.add(scc);


            if(cdl!=null&&cdl.getBusiManagerID()!=null&&!cdl.getBusiManagerID().equals("")){
                BusiManager busiManager = new BusiManager();
                busiManager.setBsmId(serverService.getServerID("bsmid"));
                busiManager.setStaffID(cdl.getBusiManagerID());
                busiManager.setType("00");
                busiManager.setCreateDate(new Date());
                busiManager.setCcompanyID(contactCompany.getCcompanyID());
                beans.add(busiManager);
            }


            if (company.getIndustryType() != null) {
                String[] typeList = company.getIndustryType().split("/");
                if (typeList != null && typeList.length >= 2 && typeList[1] != null && "餐饮".equals(typeList[1])) {
                    CCode ccode1 = new CCode();
                    ccode1.setCodeID(idgec.getServerID("ccode"));
                    ccode1.setCodeValue("餐饮");
                    ccode1.setCompanyID(company.getCompanyID());
                    ccode1.setCodePID("scode20101014v5zed7cukk0000000002");
                    ccode1.setCodeNumber(15);
                    ccode1.setCodeSn("15");
                    ccode1.setCodeStatus("01");
                    beans.add(ccode1);

                    CCode ccode3 = new CCode();
                    ccode3.setCodeID(idgec.getServerID("ccode"));
                    ccode3.setCodeValue("餐桌");
                    ccode3.setCompanyID(company.getCompanyID());
                    ccode3.setCodePID(ccode1.getCodeID());
                    ccode3.setCodeNumber(1502);
                    ccode3.setCodeSn("1502");
                    ccode3.setCodeStatus("01");
                    beans.add(ccode3);

                    SchProCategory category1 = new SchProCategory();
                    category1.setCategoryId(idgec.getServerID("category"));
                    category1.setCategoryName("主食");
                    category1.setStatus("2");
                    category1.setCompanyId(company.getCompanyID());
                    beans.add(category1);

                    SchProCategory category2 = new SchProCategory();
                    category2.setCategoryId(idgec.getServerID("category"));
                    category2.setCategoryName("饮料");
                    category2.setStatus("2");
                    category2.setCompanyId(company.getCompanyID());
                    beans.add(category2);

                    SchProCategory category3 = new SchProCategory();
                    category3.setCategoryId(idgec.getServerID("category"));
                    category3.setCategoryName("套餐");
                    category3.setStatus("2");
                    category3.setCompanyId(company.getCompanyID());
                    beans.add(category3);

                    SchProCategory category4 = new SchProCategory();
                    category4.setCategoryId(idgec.getServerID("category"));
                    category4.setCategoryName("盖饭");
                    category4.setStatus("2");
                    category4.setCompanyId(company.getCompanyID());
                    beans.add(category4);

                    SchProCategory category5 = new SchProCategory();
                    category5.setCategoryId(idgec.getServerID("category"));
                    category5.setCategoryName("小吃");
                    category5.setStatus("2");
                    category5.setCompanyId(company.getCompanyID());
                    beans.add(category5);

                    SchProCategory category6 = new SchProCategory();
                    category6.setCategoryId(idgec.getServerID("category"));
                    category6.setCategoryName("本店特色");
                    category6.setStatus("2");
                    category6.setCompanyId(company.getCompanyID());
                    beans.add(category6);
                }
            }
            this.beandao.saveBeansListAndexecuteHqlsByParams(beans, new String[]{"update Staff set groupCompanySn = ? where staffID = ?"}, new Object[]{company.getGroupCompanySn(), scc.getStaffid()});

            // 绑定公司与个人关系
            String sccid = scc.getSccId();
            String companyId = company.getCompanyID();

            TEshopCusComCompany customerCompanyRef = new TEshopCusComCompany(sccid, companyId);
            this.beandao.save(customerCompanyRef);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void registerCompanyInfo(String ccmomtype, TEshopCusCom scc, Company company, CDetail cdl, int typeNumber) {
        try {
            List<BaseBean> beans = new ArrayList();

            Company company1 = (Company) this.beandao.getBeanByHqlAndParams("from Company where companyName = ?", new Object[]{company.getCompanyName()});
            if (company1 == null) {
                company.setCompanyID(this.idgec.getServerID("company"));
                company.setCompanyPID(this.idgec.getServerID("pcompany"));
                company.setCompanyRegisterDate(new Date());
                company.setGroupCompanySn(this.idgec.getServerID("groupCompanySn"));
                company.setTotalSales("0");
                company.setCompanyRegisterDate(new Date());

                company.setCompanyLicenseCount(5);
                if (typeNumber == 0) {
                    company.setCompanyStatus("00");
                } else {
                    company.setCompanyStatus("01");
                }
                company.setCompanyType("01");
                company.setCcomtype("0");
                if (company.getCompanyIdentifier() != null) {
                    company.setCompanyIdentifier(company.getCompanyIdentifier().trim().toLowerCase());
                }
                cdl.setDetailID(this.idgec.getServerID("cdetail"));
                cdl.setCompanyID(company.getCompanyID());


                CAccount account = new CAccount();
                account.setAccountID(this.idgec.getServerID("caccount"));
                if ("".equals(scc.getStaffid())) {
                    account.setStaffID(scc.getSccId());
                } else {
                    account.setStaffID(scc.getStaffid());
                }
                account.setCompanyID(company.getCompanyID());
                account.setAccountName("系统管理员");

                account.setAccountEmail("sa");

                account.setAccountPassword(Utilities.MD5("123456"));


                account.setAccountStatus("00");

                this.registerService.register(company, cdl, account);


                saveDepot(company.getCompanyID(),beans);
            } else {
                company = company1;
            }
            String hqlcom = "from ContactCompany where companyName = ?";
            ContactCompany contactCompany = null;
            contactCompany = (ContactCompany) this.beandao.getBeanByHqlAndParams(hqlcom, new Object[]{company.getCompanyName()});
            if (contactCompany == null) {
                contactCompany = new ContactCompany();
                contactCompany.setCcompanyID(this.idgec.getServerID("contactCompany"));
                if (typeNumber == 0) {
                    contactCompany.setWebstatus("01");
                } else {
                    contactCompany.setWebstatus("00");
                }
                contactCompany.setCustStatus("02");
                contactCompany.setIndustryType(company.getIndustryType());
                contactCompany.setCompanyName(company.getCompanyName());
                contactCompany.setAuthState("00");
                contactCompany.setLogoPath(cdl.getLogo());

                beans.add(contactCompany);
            }
            CcomCom ccomCom = null;
            ccomCom = (CcomCom) this.beandao.getBeanByHqlAndParams("from CcomCom where ccompanyId = ? and comanyId = ?", new Object[]{contactCompany.getCcompanyID(), company.getCompanyID()});
            if (ccomCom == null) {
                ccomCom = new CcomCom();
                ccomCom.setCcomRelationId(this.idgec.getServerID("ccomcom"));
                ccomCom.setCcompanyId(contactCompany.getCcompanyID());
                ccomCom.setComanyId(company.getCompanyID());
                ccomCom.setState("0");
                beans.add(ccomCom);
            }
            if (!"".equals(scc.getStaffid())) {
                scc.setCompanyId(company.getCompanyID());
                scc.setPseudoCompanyName(company.getCompanyName());
            }
            scc.setState("2");
            beans.add(scc);

            this.beandao.saveBeansListAndexecuteHqlsByParams(beans, new String[]{"update Staff set groupCompanySn = ? where staffID = ?"}, new Object[]{company.getGroupCompanySn(), scc.getStaffid()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void registerCompanyInfoZps(String ccmomtype, TEshopCusCom scc, Company company, CDetail cdl, String ppidUser) {
        try {
            List<BaseBean> beans = new ArrayList();

            Company company1 = (Company) this.beandao.getBeanByHqlAndParams("from Company where companyName = ?", new Object[]{company.getCompanyName()});
            if (company1 == null) {
                company.setCompanyID(this.idgec.getServerID("company"));
                company.setCompanyPID(this.idgec.getServerID("pcompany"));
                company.setCompanyRegisterDate(new Date());
                company.setGroupCompanySn(this.idgec.getServerID("groupCompanySn"));

                company.setCompanyRegisterDate(new Date());

                company.setCompanyLicenseCount(5);
                company.setTotalSales("0");

                company.setCompanyStatus("00");
                company.setCompanyType("01");
                company.setCcomtype("0");
                if (company.getCompanyIdentifier() != null) {
                    company.setCompanyIdentifier(company.getCompanyIdentifier().trim().toLowerCase());
                }
                cdl.setDetailID(this.idgec.getServerID("cdetail"));
                cdl.setCompanyID(company.getCompanyID());


                CAccount account = new CAccount();
                account.setAccountID(this.idgec.getServerID("caccount"));
                account.setStaffID(scc.getStaffid());
                account.setCompanyID(company.getCompanyID());
                account.setAccountName("系统管理员");

                account.setAccountEmail("sa");

                account.setAccountPassword(Utilities.MD5("123456"));

                account.setAccountStatus("00");
                this.registerService.register(company, cdl, account);


                saveDepot(company.getCompanyID(),beans);
            } else {
                company = company1;
            }
            String hqlcom = "from ContactCompany where companyName = ?";
            ContactCompany contactCompany = null;
            contactCompany = (ContactCompany) this.beandao.getBeanByHqlAndParams(hqlcom, new Object[]{company.getCompanyName()});
            if (contactCompany == null) {
                contactCompany = new ContactCompany();
                contactCompany.setCcompanyID(this.idgec.getServerID("contactCompany"));
                contactCompany.setWebstatus("01");
                contactCompany.setCustStatus("02");
                contactCompany.setIndustryType(company.getIndustryType());
                contactCompany.setCompanyName(company.getCompanyName());
                contactCompany.setAuthState("00");
                contactCompany.setLogoPath(cdl.getLogo());

                beans.add(contactCompany);
            }
            CcomCom ccomCom = null;
            ccomCom = (CcomCom) this.beandao.getBeanByHqlAndParams("from CcomCom where ccompanyId = ? and comanyId = ?", new Object[]{contactCompany.getCcompanyID(), company.getCompanyID()});
            if (ccomCom == null) {
                ccomCom = new CcomCom();
                ccomCom.setCcomRelationId(this.idgec.getServerID("ccomcom"));
                ccomCom.setCcompanyId(contactCompany.getCcompanyID());
                ccomCom.setComanyId(company.getCompanyID());
                ccomCom.setState("0");
                beans.add(ccomCom);
            }
            if (scc != null) {
                TEshopCusCom cusCom = new TEshopCusCom();
                cusCom.setSccId(this.idgec.getServerID("sccid"));
                cusCom.setAccount(scc.getAccount());
                cusCom.setStaffid(scc.getStaffid());
                cusCom.setCusType("0");

                cusCom.setCompanyId(company.getCompanyID());
                cusCom.setOrganizationID(scc.getOrganizationID());
                cusCom.setSuperioragent("");
                cusCom.setSupperSccId("");
                cusCom.setPseudoCompanyName(company.getCompanyName());
                cusCom.setState("2");
                cusCom.setTeccDate(new Date());
                cusCom.setPpid(ppidUser);
                cusCom.setAcquiesce("00");
                cusCom.setQrcodePath(scc.getQrcodePath());
                beans.add(cusCom);
            }
            beans.add(scc);

            this.beandao.saveBeansListAndexecuteHqlsByParams(beans, new String[]{"update Staff set groupCompanySn = ? where staffID = ?"}, new Object[]{company.getGroupCompanySn(), scc.getStaffid()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tuicus 推荐人
     * @param sccid  推荐人sccid
     * @param phones 注册人帐号
     * @param intf   注册人密码
     * @param staff  注册人的staff
     */
    @Override
    @Transactional
    public synchronized String zhuCe(TEshopCusCom tuicus, String sccid, String phones, String intf, Staff staff) {

        TEshopCustomer tr = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0", new Object[]{phones});
        if (tr != null) {
            return tr.getStaffid();
        }

        //推荐人上级
        TEshopCusCom supercus = null;
        if (tuicus != null) {
            supercus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{tuicus.getSupperSccId()});
        }
        if (tuicus == null
                || tuicus.getCusType().equals("1")
                || tuicus.getCusType().equals("0.5")
                || tuicus.getCusType().equals("6")
                || tuicus.getCusType().equals("7")
                || (supercus == null && !tuicus.getCusType().equals("0"))
                || (supercus != null && supercus.getCusType().equals("0.5"))
                || (supercus != null && supercus.getCusType().equals("0"))) {
            //20180404修改为如果推荐人的级别为6或7就去找他的上级作为推荐人
            if (tuicus != null && ("6".equals(tuicus.getCusType()) || "7".equals(tuicus.getCusType()))
                    && !"TEshopCusCom20161010W9FXK9NJ450000011682".equals(tuicus.getSupperSccId())) {
                sccid = tuicus.getSupperSccId();
                tuicus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
            } else {
                sccid = "TEshopCusCom20161010W9FXK9NJ450000011682";
                tuicus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
                supercus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{tuicus.getSupperSccId()});
            }
        }
        List<BaseBean> beans = new ArrayList<BaseBean>();
        List<Object[]> parm = null;
        String[] hqls = null;
        Staff sf = new Staff();
        TEshopCustomer tct = new TEshopCustomer();
        TEshopCusCom tcc = new TEshopCusCom();

        if (tuicus != null) {
            String pcusttype = tuicus.getCusType();
            if (Integer.parseInt(pcusttype) > 5) {
                // 大于5说明分享者级别级别低于代理商级别有可能是客户或者VIP客户
                tcc.setSupperSccId(supercus.getSccId());
                tcc.setSuperioragent(supercus.getAccount());
            } else {
                // 小于等于5说明分享者级别是代理商或更高级 0,1,2,3,4,5
                if (pcusttype.equals("0")) {//推荐人是平台
                    Object obj = baseBeanService
                            .getObjectBySqlAndParams("SELECT T.SCCID,T.ACCOUNT" +
                                    " FROM T_ESHOP_CUSCOM T where t.custype='1' START WITH T.SCCID=?" +
                                    " CONNECT BY PRIOR T.SCCID=T.supperSccId", new Object[]{tuicus.getSccId()});
                    if (obj != null) {
                        Object[] objs = (Object[]) obj;
                        tcc.setSupperSccId(objs[0].toString());//推荐人上级
                        tcc.setSuperioragent(objs[1].toString());
                    } else {
                        tuicus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(
                                "from TEshopCusCom where sccId=?", new Object[]{sccid});//平台账号下没有地税的推荐人
                        tcc.setSupperSccId(tuicus.getSccId());//推荐人上级
                        tcc.setSuperioragent(tuicus.getAccount());
                    }
                } else {//,1,2,3,4,5
                    tcc.setSupperSccId(tuicus.getSccId());//推荐人
                    tcc.setSuperioragent(tuicus.getAccount());
                }
            }
            tcc.setPpid(tuicus.getPpid());
        }
        System.out.println("用户" + phones + "  推荐人sccid   " + sccid);
        if (staff != null && staff.getStaffName() != null) {
            sf.setStaffName(staff.getStaffName());
        }
        sf.setStaffID(serverService.getServerID("Staff"));
        sf.setReference(phones);//电话
        //增加编号以及录入时间
        String phql = "select count(*) from Staff ";
        int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
        sf.setStaffCode("NO" + pcount);
        sf.setVerifyTime(new Date());
        sf.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
        sf.setStaus("01");// 默认身份证为空 为不确定人员

        //账号密码表
        tct.setCustid(serverService.getServerID("custid"));
        tct.setStaffid(sf.getStaffID());
        tct.setPassword(intf);
        tct.setAccount(phones);
        tct.setLogOff("0");
        beans.add(tct);
        //权限表
        tcc.setState("1");
        String tcsccid = serverService.getServerID("TEshopCusCom");
        tcc.setSccId(tcsccid);
        tcc.setStaffid(sf.getStaffID());
        tcc.setAccount(phones);
        tcc.setCusType("7");
        tcc.setTeccDate(new Date());
        tcc.setAcquiesce("01");//默认账户
        tcc.setPpid(tuicus.getPpid());
        tcc.setLogOff("0");
        beans.add(tcc);
        sf.setSccid(tcc.getSccId()); //staff中添加sccid
        beans.add(sf);
        JoinFans jf1 = new JoinFans();
        jf1.setJfID(serverService.getServerID("jfID"));
        jf1.setFsccId(tcc.getSccId());
        jf1.setFaccount(tcc.getAccount());
        jf1.setStaffid(sf.getStaffID());
        jf1.setSource("新版注册");
        jf1.setState("00");
        jf1.setZsccId(sccid);
        jf1.setZaccount(tuicus.getAccount());

        JoinFans jf2 = new JoinFans();
        jf2.setJfID(serverService.getServerID("jfID"));
        jf2.setFsccId(sccid);
        jf2.setFaccount(tuicus.getAccount());
        jf2.setStaffid(tuicus.getStaffid());
        jf2.setSource("新版注册");
        jf2.setState("00");
        jf2.setZsccId(tcc.getSccId());
        jf2.setZaccount(tcc.getAccount());
        beans.add(jf1);
        beans.add(jf2);

        hqls = new String[]{"update TEshopCusCom u set u.teccDate=? where u.sccId=?"};
        parm = new ArrayList<Object[]>();
        parm.add(new Object[]{new Date(), tuicus.getSccId()});

        //保存推荐人信息
        MarKeting mk = new MarKeting();
        mk.setMkSccId(sccid);//推荐人
        mk.setUserSccId(tcc.getSccId());//被推荐人
        mk.setMkuserID(tuicus.getAccount());
        mk.setUserID(tcc.getAccount());
        mk.setMkdate(new Date());
        mk.setMkID(serverService.getServerID("MarKeting"));
        beans.add(mk);


        if (!tuicus.getSccId().equals("TEshopCusCom20161010W9FXK9NJ450000011682")) {
            StringBuffer str = new StringBuffer();
            str.append(" select d.dbid,d.dbcarid from dt_deviceBind d, dt_devicebind_staff ds ");
            str.append(" where d.dbid = ds.dbsdbid and d.dbstatus = '1' ");
            str.append(" and ds.dbsstatus = '1' and ds.dbssccid = ? ");

            List<BaseBean> dlist = beandao.getListBeanBySqlAndParams(str.toString(), new Object[]{tuicus.getSccId()});
            if (dlist != null && dlist.size() > 0) {
                Object[] obj = (Object[]) (Object) dlist.get(0);
                DeviceBindStaff dstaff = new DeviceBindStaff();
                dstaff.setDbsKey(serverService.getServerID(""));
                dstaff.setDbsId(serverService.getServerID("DeviceBindStaff"));
                dstaff.setDbsDbId(obj[0].toString());
                dstaff.setDbsStaffId(tcc.getStaffid());
                dstaff.setDbsSccId(tcc.getSccId());
                dstaff.setDbsDate(new Date());
                dstaff.setDbsStatus("1");
                beans.add(dstaff);
            }
        } else {
            //没有推荐人时保存抢单需求
            DemandDetail demandDetail = new DemandDetail();
            demandDetail.setDdid(serverService.getServerID("ddetail"));
            demandDetail.setDdsccid(tcsccid);
            demandDetail.setDdtitle("新注册用户");
            demandDetail.setDdcontactname(sf.getStaffName());
            demandDetail.setDdcontactphone(phones);
            demandDetail.setDdscodeid("scode20170714cnjcrn5jm20000000067");
            demandDetail.setDdworktype("服务平台");
            demandDetail.setDdstatus("0");
            demandDetail.setDdBool('Y');
            demandDetail.setDdadddate(new Date());
            demandDetail.setDscount(0);
            demandDetail.setDdtype("1");
            demandDetail.setDdcid(tcc.getStaffid());
            demandDetail.setDdsccid(tcc.getSccId());
            demandDetail.setDdstaffid(tcc.getStaffid());
            beans.add(demandDetail);
        }

        baseBeanService.executeHqlsByParamsList(beans, hqls, parm);
        return tct.getStaffid();
    }

    /**
     * 支付宝、微信钱包、微信银行卡兑现
     *
     * @param sccid     兑现帐号id
     * @param staffId   兑现人员id
     * @param wfj_user  兑现帐号
     * @param jNumOrder 商户订单号
     * @param money     对现金额
     * @param trade_no  第三方交易号
     * @param methodPay 兑现方式
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public String goldWithdrawalVerify(String sccid, String staffId, String wfj_user, String jNumOrder, String money, String trade_no, String methodPay) throws Exception {
        String str = "";
        logger.error("用户兑现金币数开始。");
        logger.error("sccid：" + sccid + "  staffId:" + staffId + "  wfj_user:" + wfj_user);
        String sql = "select sta.staffname,sta.recordcode from dt_hr_staff sta where sta.staffid = ?";
        Object ret = this.beandao.getObjectBySqlAndParams(sql, new Object[]{staffId});
        String wfj_comid = "";
        String wfj_comname = "";

        TEshopCusCom cusCom = (TEshopCusCom) this.beandao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccid});
        if (cusCom.getCompanyId() == null) {
            String cushql = "SELECT t.staffid, t.companyid, t.state,t.superioragent,t.pseudo_company_name,t.custype,t.sccid,t.account FROM T_ESHOP_CUSCOM t where t.state=? START WITH t.sccid = ? CONNECT BY PRIOR t.suppersccid = t.sccid";

            List<BaseBean> baseBeans = this.beandao.getListBeanBySqlAndParams(cushql, new Object[]{"2", sccid});
            if (baseBeans.size() > 0) {
                Object object = baseBeans.get(0);
                Object[] objects = (Object[]) object;
                wfj_comid = objects[1].toString();
            } else {
                str = "有参数为空";
                throw new Exception(str);
            }
        } else {
            wfj_comid = cusCom.getCompanyId();
        }
        wfj_comname = (String) this.beandao.getObjectBySqlAndParams("select c.companyname from dtcompany c where c.companyid=?", new Object[]{wfj_comid});
        if (ret == null) {
            str = "请先完善用户信息！！！";
            throw new Exception(str);
        }
        Object[] arr = (Object[]) ret;
        if (arr[0] == null) {
            str = "请先完善用户信息！！！";
            throw new Exception(str);
        }
        String comId = "company201009046vxdyzy4wg0000000025";
        String comName = "北京天太世统科技有限公司";
        String comgn = "groupcompany20120523G3VR9PXHZD0000000021";
        String staname = arr[0].toString();

        String cbid = this.idgec.getServerID("CashierBills");
        String cbid2 = this.idgec.getServerID("CashierBills");
        String cbid3 = this.idgec.getServerID("CashierBills");
        String cbid4 = this.idgec.getServerID("CashierBills");
        System.out.println(cbid);
        System.out.println(cbid2);
        System.out.println(cbid3);
        System.out.println(cbid4);
        String journum = trade_no;
        String pzid = this.idgec.getBillID(comId);
        String pzid2 = this.idgec.getBillID(comId);
        String pzid3 = this.idgec.getBillID(comId);
        String pzid4 = this.idgec.getBillID(comId);
        String amt = new BigDecimal(money).add(new BigDecimal(Constant.POUNDAGE)).toString();//总金额(交易金额+2元手续费)

        String hql = "select m from ProductPackaging m where m.goodsName =? and m.companyID=?";
        ProductPackaging gm = (ProductPackaging) this.beandao.getBeanByHqlAndParams(hql, new Object[]{"微分金金币", comId});
        if (gm == null) {
            str="金币产品信息错误,1操作失误了！！！";
            throw new Exception(str);
        }
        ProductPackaging gmm = (ProductPackaging) this.beandao.getBeanByHqlAndParams(hql, new Object[]{"银行存款", comId});
        if (gmm == null) {
            str="银行存款产品信息错误，2操作失误了！！！";
            throw new Exception(str);
        }
        List<BaseBean> beans = new ArrayList();
        CashierBills cb = null;
        cb = new CashierBills();
        cb.setCashierBillsID(cbid);
        cb.setCashierDate(new Date());
        cb.setBillsType("金币出库单");
        cb.setJournalNum(pzid);
        System.out.println("金币出库单:" + cb.getJournalNum());
        cb.setTrade_no(journum);
        cb.setStaffID(staffId);
        cb.setStaffName(staname);
        cb.setCompanyID(wfj_comid);
        cb.setCompanyName(wfj_comname);
        cb.setInputid(staffId);
        cb.setInputName(staname);
        cb.setInputCompanyid(wfj_comid);
        cb.setInputCompanyName(wfj_comname);
        cb.setStatus("16");
        cb.setCcompanyID("company201009046vxdyzy4wg0000000025");
        cb.setCcompanyName("北京天太世统科技有限公司");
        cb.setCompanyAddr("东直门外大街42号宇飞大厦801室");
        cb.setCompanyTel("010-64167113");
        cb.setCresponsible("刘太平");
        cb.setResponsibleTel("15810799888");
        cb.setAppstyle("07");
        cb.setStatusbill("04");
        cb.setPriceSub(amt);
        cb.setProID("003");
        cb.setProjectName("金币兑现");
        cb.setTrade_no(trade_no);
        cb.setjNumOrder(jNumOrder);
        cb.setWfStatus4(methodPay);

        logger.error("金币出库单:cb.setCompanyID(wfj_comid);=" + wfj_comid + "  cb.setInputCompanyid(wfj_comid)=" + wfj_comid);

        CashierBills cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        beans.add(cb1);

        GoodsBills gb = new GoodsBills();
        gb.setCashierBillsID(cbid);
        gb.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));

        gb.setGoodsID(gm.getGoodsID());
        gb.setGoodsNum(gm.getGoodsNum());
        gb.setGoodsName(gm.getGoodsName());
        gb.setStandard(gm.getStandard());
        gb.setGoodsVariableID(gm.getVariableID());
        gb.setWeight(gm.getWeight());
        gb.setPrice("0.01");
        gb.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100)).toString());
        gb.setMoney(amt);


        gb.setStartDate(null);
        gb.setEndDate(null);
        gb.setBatchNumber(null);
        gb.setPeriodDate(null);
        gb.setRemindedContent(null);
        gb.setCostType(null);
        gb.setPayType("08");
        gb.setPriceManage(null);
        gb.setKcStatus("16");
        gb.setGoodstatus("00");
        gb.setSortCode(null);
        gb.setPpID(gm.getPpID());

        beans.add(gb);

        GoodsBills gb1 = new GoodsBills();
        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid2);
        cb1.setBillsType("收款单");
        cb1.setStatus("45");
        cb1.setJournalNum(pzid2);
        System.out.println("收款单:" + cb1.getJournalNum());
        cb1.setGroupCompanySn(null);


        gb1 = new GoodsBills();
        //BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setCashierBillsID(cbid2);
        gb1.setKcStatus("15");
        gb1.setPrice("1");
        gb1.setGoodsName("金币兑现");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setGoodsVariableID("元");
        gb1.setQuantity(Float.parseFloat(money) + "");
        gb1.setMoney(Float.parseFloat(money) + "");

        beans.add(gb1);
        cb1.setPriceSub(gb1.getMoney());
        beans.add(cb1);

        CashApplyBills cab = new CashApplyBills();
        cab.setCashApplyBillsID(this.idgec.getServerID("CashApplyBills"));

        cab.setCashierBillsID(cbid);
        cab.setAppropriationMoney(Float.parseFloat(money) + "");
        cab.setAppropriateStatus("01");
        cab.setAppropriationcompanyID(comId);
        cab.setAppropriationcompanyName(comName);
        cab.setReceivablesID(staffId);
        cab.setReceivablesName(staname);
//		cab.setReceopenBank(tscBean.getOpenBank());
//		cab.setReceuserName(tscBean.getUserName());
//		cab.setRecropriationNum(tscBean.getCardNo());
        cab.setAppstyle("01");
        beans.add(cab);


        RelatedBill rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid);
        rb.setCashfid(cbid2);
        rb.setBilltype("收款单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid4);
        rb.setBilltype("支款单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid);
        rb.setBilltype("金币入库单");
        beans.add(rb);

        rb = new RelatedBill();
        rb.setRbID(this.idgec.getServerID("RelatedBill"));
        rb.setCashid(cbid3);
        rb.setCashfid(cbid2);
        rb.setBilltype("收款单");
        beans.add(rb);


        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid3);
        cb1.setBillsType("金币入库单");
        cb1.setStatus("15");
        cb1.setJournalNum(pzid3);
        System.out.println("金币入库单:" + cb1.getJournalNum());
        cb1.setStaffID(null);
        cb1.setStaffName(null);
        cb1.setStaffCode(null);
        cb1.setCompanyID(comId);
        logger.error("金币入库单:cb.setCompanyID(wfj_comid);=" + comId);

        cb1.setCompanyName(comName);
        cb1.setGroupCompanySn(comgn);

        cb1.setInputid(null);
        cb1.setInputName("系统生成");
        cb1.setInputCompanyid(null);
        cb1.setInputCompanyName(null);


        cb1.setCcompanyID(null);
        cb1.setCcompanyName(null);
        cb1.setCompanyAddr(null);
        cb1.setCompanyTel(null);
        cb1.setCresponsible(null);
        cb1.setResponsibleTel(null);

        cb1.setContactUserID(staffId);
        cb1.setCtUserName(staname);
        beans.add(cb1);

        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid3);
        gb1.setQuantity(new BigDecimal(amt).multiply(new BigDecimal(100)).toString());
        gb1.setMoney(amt);
        gb1.setKcStatus("15");
        beans.add(gb1);


        boolean flag = ruku(cbid3, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "金币入库单", "0.01", Float.valueOf(Float.parseFloat(amt) * 100.0F), amt, gm.getGoodsID(), "金币仓库", false);
        if (!flag) {
            beans = null;
            str="金币入库失败,3操作失误了！！！";
            throw new Exception(str);
        }
        cb1 = new CashierBills();
//	    BeanUtils.copyProperties(cb1, cb);
        cb1 = (CashierBills) cb.cloneCashierBills();
        cb1.setCashierBillsID(cbid4);
        cb1.setBillsType("支款单");
        cb1.setStatus("46");
        cb1.setJournalNum(pzid4);
        System.out.println("支款单:" + cb1.getJournalNum());
        cb1.setStaffID(null);

        cb1.setStaffCode(null);
        cb1.setStaffName(null);
        cb1.setCompanyID(comId);
        logger.error("支款单:cb.setCompanyID(wfj_comid);=" + comId);
        cb1.setCompanyName(comName);
        cb1.setGroupCompanySn(comgn);

        cb1.setInputid(null);
        cb1.setInputName("系统生成");
        cb1.setInputCompanyid(null);
        cb1.setInputCompanyName(null);


        cb1.setCcompanyID(null);
        cb1.setCcompanyName(null);
        cb1.setCompanyAddr(null);
        cb1.setCompanyTel(null);
        cb1.setCresponsible(null);
        cb1.setResponsibleTel(null);

        cb1.setContactUserID(staffId);
        cb1.setCtUserName(staname);
        beans.add(cb1);

        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid4);
        gb1.setPrice("1");
        gb1.setGoodsName("金币兑现");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setQuantity(Float.parseFloat(money) + "");
        gb1.setMoney(Float.parseFloat(money) + "");
        gb1.setGoodsVariableID("元");

        flag = ruku(cbid4, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "支款单", "1", Float.parseFloat(money), Float.parseFloat(money) + "", gmm.getGoodsID(), "资金仓库", true);
        if (!flag) {
            beans = null;
            str="支款单生成失败，4操作失误了！！！";
            throw new Exception(str);
        }
        beans.add(gb1);
        gb1 = new GoodsBills();
//	    BeanUtils.copyProperties(gb1, gb);
        gb1 = (GoodsBills) gb.cloneGoodsBills();
        gb1.setGoodsBillsID(this.idgec.getServerID("GoodsBills"));
        gb1.setCashierBillsID(cbid4);
        gb1.setGoodsName("手续费");
        gb1.setGoodsID(null);
        gb1.setPpID(null);
        gb1.setPrice("1");
        gb1.setQuantity(new BigDecimal(Constant.POUNDAGE).toString());
        gb1.setMoney(new BigDecimal(Constant.POUNDAGE).toString());
        gb1.setKcStatus("16");
        gb1.setGoodsVariableID("元");
        beans.add(gb1);

        flag = ruku(cbid4, gb1.getGoodsBillsID(), beans, comId, comgn, staffId, staname, "支款单", "1", Float.valueOf(Constant.POUNDAGE), Constant.POUNDAGE + "", gmm.getGoodsID(), "资金仓库", true);
        if (!flag) {
            beans = null;
            str="支款单生成失败，5操作失误了！！！";
            throw new Exception(str);
        }
        WfjJifenDetail edetail = new WfjJifenDetail();
        edetail.setJifenDetailId(this.idgec.getServerID("WfjJifenDetail"));
        String temp = null;
        BigDecimal tempjifen = new BigDecimal(0);
        BaseBean bguize = this.beandao.getBeanByHqlAndParams("from WfjGuize where companyId=? and wfjGuizeId=?", new Object[]{"company201009046vxdyzy4wg0000000025", "69CD270453D54AE4817274AC0D269B71"});
        WfjGuize eguize = (WfjGuize) bguize;

        BaseBean bb = this.beandao.getBeanByHqlAndParams("from WfjJifen where staffId=? and sccid=?", new Object[]{staffId, sccid});
        String jifenId = ((WfjJifen) bb).getWfjJifenId();
        temp = "减少" + staname + "的金币,提现金币";
        tempjifen = new BigDecimal(amt).multiply(new BigDecimal(100));

        WfjJifen wfjJifen = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where wfjJifenId=? ", new Object[]{jifenId});
        String JifenScore = wfjJifen.getWfjJifenScore();
        float golds = Float.parseFloat(JifenScore) - Float.parseFloat(tempjifen.toString());
        if (golds < 0) {
            str="提现"+money+"元现金，金币不够扣除！";
            throw new Exception(str);
        } else {
            wfjJifen.setWfjJifenScore(golds + "");
            beans.add(wfjJifen);
        }


        edetail.setJifenDetailScore(tempjifen.toString());
        edetail.setWfjGuizeId(eguize.getWfjGuizeId());
        edetail.setWfjJifenId(jifenId);
        edetail.setJifenDetailState(Integer.valueOf(0));
        edetail.setJifenDetailName(temp);
        edetail.setJifenDetailDate(new Date());
        edetail.setWfjCashId(cbid);
        beans.add(edetail);

        WfjTask wt = new WfjTask();
        wt.setWfjTaskId(this.idgec.getServerID("WfjTask"));
        wt.setStaffId(staffId);
        wt.setWfjGuizeId(eguize.getWfjGuizeId());
        wt.setWfjTaskDate(new Date());
        beans.add(wt);

        System.out.println(money);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("wfj_comId", wfj_comid);
        boolean isParam = isParamNull(paramMap);
        if (!isParam) {
            str = "有参数为空1";
            throw new Exception(str);
        }

//		Map<String, Object> map = SinglePay.payMeth(tscBean);
//		TransactionBean tbean = null;
//		logger.error("第二部：" + map.get("result"));
//
//		tbean = (TransactionBean)map.get("bean");
//		if ((map.get("result").equals(Integer.valueOf(0))) && (tbean.getResponseCode().equals("0000"))){

        beandao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        str = "提交成功";
//	}
//		else if (tbean.getResponseCode().equals("0100")){
//			str = "商户提交的字段长度、格式错误,请核实银行卡信息!!!";
//		}
//		else if (tbean.getResponseCode().equals("0101")){
//			str = "商户验签错误,请核实银行卡信息!!!";
//		}
//		else if (tbean.getResponseCode().equals("0102")){
//			str = "手续费计算出错!!!";
//		}
//		else if (tbean.getResponseCode().equals("0103")){
//			str = "商户备付金帐户金额不足,平台余额正在充值,请耐心等待！";
//		}
//		else if (tbean.getResponseCode().equals("0104")){
//			str = "操作拒绝,请核实银行卡信息!!!";
//		}
        logger.error(str);
        return str;
    }


    @Override
    public PageForm goldWithdrawalList(int pageNumber, int pageSize, String user, WithDrawReq withDraw, String staffName, String sdate, String edate, String type) {
        PageForm pageForm;
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        if ("list".equals(type)) {
            sql.append("select wr.withdrawid, wr.sccid,wr.withdrawway,wr.withdrawdate,wr.state,wr.jnumorder,wr.amount,wr.message,ec.account,hs.staffname");
        } else if ("excel".equals(type)) {//"姓名","账号","订单号","金额","提现方式","信息","时间","状态"
            sql.append("select hs.staffname, ec.account,wr.jnumorder,wr.amount,(case wr.withdrawway when '01' then '支付宝' when '02' then '微信' when '03'");
            sql.append(" then '银行卡' else '无' end ) withdrawway,wr.message,wr.withdrawdate,(case wr.state when '00' then '提现成功'when '01' then");
            sql.append(" '提现成功(生成订单失败)'when '10' then '提现失败(正常)'when '11' then '提现失败(异常)' else '无'end) state");
        }
        sql.append(" from dtwithdrawreq wr");
        sql.append(" left join t_eshop_cuscom ec on ec.sccid = wr.sccid");
        sql.append(" left join dt_hr_staff hs on ec.staffid = hs.staffid where 1=1 ");
        if (withDraw != null) {
            if (withDraw.getWithDrawWay() != null && !"".equals(withDraw.getWithDrawWay()) && !"000".equals(withDraw.getWithDrawWay())) {
                sql.append(" and wr.withdrawway  = ?");
                param.add(withDraw.getWithDrawWay());
            }
            if (withDraw.getjNumOrder() != null && !"".equals(withDraw.getjNumOrder())) {
                sql.append(" and wr.jnumorder like ?");
                param.add("%" + withDraw.getjNumOrder() + "%");
            }
        }
        if (user != null && !"".equals(user)) {
            sql.append(" and ec.account like ?");
            param.add("%" + user + "%");
        }
        if (staffName != null && !"".equals(staffName)) {
            sql.append(" and hs.staffname like  ?");
            param.add("%" + staffName + "%");
        }
        if (sdate != null && !"".equals(sdate) && edate != null && !"".equals(edate)) {
            sql.append("and wr.withdrawdate between ? and ?");
            param.add(Utilities.getDateFromString(sdate + " 00:00:00", "yyyy-MM-dd HH:ss:mm"));
            param.add(Utilities.getDateFromString(edate + " 23:59:59", "yyyy-MM-dd HH:ss:mm"));
        }
        pageForm = baseBeanService.getPageFormBySQL(pageNumber == 0 ? 1 : pageNumber,
                pageSize == 0 ? 10 : pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", param.toArray());
        if ("excel".equals(type)) {
            pageForm = baseBeanService.getPageFormBySQL(pageNumber == 0 ? 1 : pageNumber,
                    pageSize == 0 ? pageForm.getRecordCount() : pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", param.toArray());
        }
        return pageForm;

    }

    /**
     * 用户提现后锁定,一个小时后解锁可再次提现
     *
     * @param withDrawReq
     * @return
     */
    @Transactional
    @Override
    public int saveOrUpdateithDrawReq(WithDrawReq withDrawReq) {
        int count = 1;
        WithDrawReq w = (WithDrawReq) baseBeanService.getBeanByHqlAndParams("from WithDrawReq where withDrawID=?", new Object[]{withDrawReq.getWithDrawID()});
        if (w == null) {
            String sql = "select count(*) from WithDrawReq where sccID=? and withDrawDate between (sysdate - 1/24) and sysdate";
            count = baseBeanService.getConutByByHqlAndParams(sql, new Object[]{withDrawReq.getSccID()});
            if (count <= 0) {
                baseBeanService.save(withDrawReq);
            }
        } else {
            w.setMessage(withDrawReq.getMessage());
            w.setState(withDrawReq.getState());
            baseBeanService.update(w);
        }
        return count;
    }

    public Object getDate(String sccid) {
        StringBuffer sql = new StringBuffer("select * from (select to_char(w.withdrawdate,'yyyy-MM-dd HH24:mi:ss'),w.state from dtWithDrawReq w where sccID = ? order by w.withdrawdate desc) where rownum=1 ");
        return baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{sccid});
    }

    /**
     *
     * 申请提现
     * @param companyID
     * @param amount
     * @param sccid
     * @return
     */
    @Transactional
    @Override
    public String withdrawbywechatsh(String companyID,int amount,String sccid,String out_request_no){

        String sql = "select t.sub_mchid from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ?";
        Object obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{companyID});
        String status = "提现失败";
        if(obj!=null) {

            List<BaseBean> beans= new ArrayList<BaseBean>();
            Withdraw withdraw = HTTPV3.withdraw(obj.toString(), amount, out_request_no);
            withdraw.setWdid(serverService.getServerID("id"));
            withdraw.setSccid(sccid);
            withdraw.setOprdate(new Date());


            withdraw = HTTPV3.searchWithdraw(withdraw);


          //  baseBeanDao.update(withdraw);
            status = withdraw.getStatus();

            beans.add(withdraw);

            addWxAccountDetail(withdraw,companyID,beans);

        }

     return status;
    }


    /**
     *
     * 记录提现明细
     */
    @Transactional
    public  void addWxAccountDetail(Withdraw withdraw,String companyID,List<BaseBean> beans){
       try{
        WxAccountDetail wxAccountDetail = (WxAccountDetail)baseBeanDao.getBeanByHqlAndParams("from WxAccountDetail where journalNum = ?",new Object[]{withdraw.getOut_request_no()});
        if(wxAccountDetail==null) {

            wxAccountDetail = new WxAccountDetail();
            wxAccountDetail.setWxdId(serverService.getServerID("wxdid"));
            wxAccountDetail.setCompanyID(companyID);
            wxAccountDetail.setCreateDate(withdraw.getOprdate());
            wxAccountDetail.setJournalNum(withdraw.getOut_request_no());


            wxAccountDetail.setSccid(withdraw.getSccid());
            try {
                if (withdraw.getSccid() != null && !withdraw.getSccid().equals("")) {
                    Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff s where s.staffID = (select m.staffid from TEshopCusCom m where sccId = ?)", new Object[]{withdraw.getSccid()});
                    wxAccountDetail.setStaffName(staff.getStaffName());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            WxMainAccount wxMainAccount = (WxMainAccount) baseBeanDao.getBeanByHqlAndParams("from WxMainAccount where companyID = ?", new Object[]{wxAccountDetail.getCompanyID()});


            BigDecimal d = new BigDecimal(withdraw.getAmount());


            wxMainAccount.setYe(new BigDecimal(wxMainAccount.getYe()).subtract(d).toString());
            wxMainAccount.setZc(new BigDecimal(wxMainAccount.getZc()).add(d).toString());
            Date date = withdraw.getOprdate();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            String dd = dateFormat.format(date);


            WxMonthAccount wxMonthAccount = (WxMonthAccount) baseBeanDao.getBeanByHqlAndParams("from WxMonthAccount where companyID = ? and month = ?", new Object[]{wxAccountDetail.getCompanyID(), dd});
            if (wxMonthAccount == null) {

                wxMonthAccount = new WxMonthAccount();
                wxMonthAccount.setWxaId(serverService.getServerID("wxaid"));
                wxMonthAccount.setSr("0");
                wxMonthAccount.setZc("0");
                wxMonthAccount.setYe("0");
                wxMonthAccount.setCompanyID(wxAccountDetail.getCompanyID());
                wxMonthAccount.setMonth(dd);

            }
            wxMonthAccount.setYe(new BigDecimal(wxMonthAccount.getYe()).subtract(d).toString());
            wxMonthAccount.setZc(new BigDecimal(wxMonthAccount.getZc()).add(d).toString());


            wxAccountDetail.setRate("");

            wxAccountDetail.setMoney(d.toString());


            wxAccountDetail.setSztype("M");
            wxAccountDetail.setType("微信商户号提现");
            beans.add(wxMainAccount);
            beans.add(wxMonthAccount);
            beans.add(wxAccountDetail);
            baseBeanDao.executeHqlsByParmsList(beans, null, null);
        }
    }catch (Exception e){
           e.printStackTrace();
       }
    }



    /**
     *
     * 获取微信商户号的总支出，和收入
     * @param Date
     * @param companyID
     * @return
     */
    public WxMonthAccount getWxSummry(String Date, String companyID){

        String hql = "from WxMonthAccount where month = ? and companyID = ?";

        WxMonthAccount wxMonthAccount = (WxMonthAccount)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{Date,companyID});

        if(wxMonthAccount==null){
            wxMonthAccount = new WxMonthAccount();
            wxMonthAccount.setSr("0");
            wxMonthAccount.setZc("0");

        }

      return wxMonthAccount;
    }

    /**
     *
     *
     *
     * @param pageNumber
     * @param pageSize
     * @param companyID
     * @return
     */
    public PageForm getWxDetailList(int pageNumber,int pageSize,String companyID,String year,String month){

        int day = Utilities.getDayMouth(Integer.parseInt(year.toString()), Integer.parseInt(month.toString()));
        Date date = Utilities.getDateFromString(year.toString() + "-" + month.toString()
                + "-01 00:00:01", "yyyy-MM-dd HH:mm:ss");
        Date endDate = Utilities.getDateFromString(year.toString() + "-" + month.toString() + "-" + day + " 23:59:59", "yyyy-MM-dd HH:mm:ss");

        String hql= "from WxAccountDetail where companyID = ? and createDate between ? and ? order by createDate desc";



        PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,hql,new Object[]{companyID,date,endDate});

        return pageForm;

    }

    /**
     *
     *
     * 详情
     * @param wxdId
     * @return
     */
    public Object getWxDetails(String wxdId,String wfjGuizeCalc){
        String sql = "";
        if("A".equals(wfjGuizeCalc)) {
            sql = "select cc.projectname,d.journalNum,f.staffname,p.goodsname,f.headimage,tc.sccid,d.createdate,d.money,d.type,d.sztype,cc.wfStatus4 from dtWxAccountDetaill d,t_Eshop_Cuscom tc,dt_hr_staff f,Dt_Productpackaging p,Dtcashierbills cc where  tc.staffid = f.staffid and tc.custype = p.model and cc.journalnum = d.journalnum and cc.contactuserid=f.staffid  and p.type='会员类型级别' and d.wxdid=?";

    }else if("M".equals(wfjGuizeCalc)) {

            sql = "select d.type,d.journalNum,f.staffname,p.goodsname,f.headimage,tc.sccid,d.createdate,d.money,d.type,d.sztype,d.status from dtWxAccountDetaill d,t_Eshop_Cuscom tc,dt_hr_staff f,Dt_Productpackaging p where  tc.staffid = f.staffid and tc.custype = p.model and d.sccid = tc.sccid and p.type='会员类型级别' and d.wxdid=?";
        }else{

            sql = "select d.type,d.journalNum,f.staffname,p.goodsname,f.headimage,tc.sccid,d.createdate,d.money,d.type,d.sztype,d.status from dtWxAccountDetaill d,t_Eshop_Cuscom tc,dt_hr_staff f,Dt_Productpackaging p where  tc.staffid = f.staffid and tc.custype = p.model and d.sccid = tc.sccid and p.type='会员类型级别' and d.wxdid=?";
        }


        Object objs = baseBeanDao.getObjectBySqlAndParams(sql, new Object[]{wxdId});
        return objs;
    }

    /**
     *
     * 查询提现结果
     * @param journalNum
     * @return
     */
    @Transactional
    public String searchWithdraw(String journalNum){

     String hql = "from Withdraw where out_request_no=?";
        Withdraw withdraw = (Withdraw)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{journalNum});
        String status = "";
        if(withdraw!=null) {
            withdraw = HTTPV3.searchWithdraw(withdraw);
            status = withdraw.getStatus();
        }
        WxAccountDetail wxAccountDetail = (WxAccountDetail)beandao.getBeanByHqlAndParams("from WxAccountDetail where journalNum = ?",new Object[]{journalNum});

        wxAccountDetail.setStatus(status);

        List<BaseBean> beans = new ArrayList<BaseBean>();
        beans.add(withdraw);
        beans.add(wxAccountDetail);
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);

        String state = "";
        if(status.equals("CREATE_SUCCESS")){

            state = "受理成功";
        }else if(status.equals("SUCCESS")){

            state = "提现成功";
        }else if(status.equals("FAIL")){

            state = "提现失败";
        }else if(status.equals("REFUND")){

            state = "提现退票";
        }else if(status.equals("CLOSE")){

            state = "关单";
        }else if(status.equals("INIT")){

            state = "业务单已创建";
        }

        return state;

    }

    /**
     * 保存仓库信息
     * @param companyID
     * @param beans
     */
    private void saveDepot(String companyID,List<BaseBean> beans){
        //新增库房
        String[] stra = {
                "股东库",
                "董事会库",
                "监事会库",
                "工会库",
                "顾问会库",
                "董事长室库",
                "总经理室库",
                "人事库",
                "财务库",
                "生产库",
                "营销库",
                "创收库"
        };
        String[] strb = {"实物仓库","资料仓库", "资金仓库", "金币仓库"};
        String[] strs = {"原材料库", "成品库", "销售库", "退货库", "物流库"};
        for (int j = 0; j < stra.length; j++) {
            String apid=saveDepot2(companyID,"001",j+1,stra[j],beans);
            for (int i = 0; i < strb.length; i++){
                if (!stra[j].equals("财务库")&&i>=2){
                    break;
                }
                String bpid=saveDepot2(companyID,apid,i+1,strb[i],beans);
                if (stra[j].equals("营销库")&&strb[i].equals("实物仓库")){
                    for (int n = 0; n < strs.length; n++) {
                        saveDepot2(companyID,bpid,n+1,strs[n],beans);
                    }
                }
            }
        }
    }

    /**
     * 保存仓库信息
     * @param comid 公司id
     * @param pid 父级id
     * @param i 序号
     * @param name 仓库名
     * @param beans 仓库集合
     */
    private String saveDepot2(String comid,String pid,int i,String name,List<BaseBean> beans){
        DepotManage depot = new DepotManage();
        depot.setDepotID(this.idgec.getServerID("DepotManage"));
        depot.setCompanyID(comid);
        depot.setDepotPID(pid);
        depot.setDepotName(name);
        depot.setDepotNum(i);
        depot.setDepotState("02");
        depot.setDepotType("1");
        beans.add(depot);
        return depot.getDepotID();
    }
}
