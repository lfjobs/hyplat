package hy.ea.finance.service.impl;


import com.alibaba.fastjson.JSON;
import com.hx.httpclient.utils.HttpUtils;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.WxAccountDetail;
import com.tiantai.wfj.bo.WxMainAccount;
import com.tiantai.wfj.bo.WxMonthAccount;
import com.tiantai.wfj.service.GoldOrderService;
import com.wechat.bo.sft.SubOrders;
import com.wechatpay.bo.WxPayDto;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.Company;
import hy.ea.bo.Enroll;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.finance.BenDis.*;
import hy.ea.bo.finance.*;
import hy.ea.bo.human.Staff;
import hy.ea.bo.invoicing.*;
import hy.ea.finance.dao.transferDao;
import hy.ea.finance.service.transferService;
import hy.ea.util.MobileMessage;
import hy.ea.util.StringUtil;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
public class transferServiceImpf implements transferService {
    @Resource
    private transferDao transferDao;
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private GoldOrderService goldOrderService;
    @Autowired
    private MobileMessage msage;//发短信zzl

    //物流信息
//	private String EBusinessID="1267773";
//	private String AppKey="ebf49c90-8c68-4341-acc2-432e6e16a9bc";
//	private String ReqURL="http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx";

    String host = "http://kdwlcxf.market.alicloudapi.com";
    String path = "/kdwlcx";
    String method = "GET";
    String appcode = "06ebeb1338a34ed49efc8438ef8135c2";

    private Logger logger = LoggerFactory.getLogger(transferServiceImpf.class);

    /**
     * 发货   一键发货
     *
     * @param com_id      供应商id
     * @param cashi_id    根据ID单条发货      如果批量发货  则为null （批量发货弃用只可以单条发货）
     * @param fh_staff_id 发货人id
     */
    public synchronized List<String> onkeyfh(String com_id, String fh_staff_id, String cashi_id) {
        ///logger.error("开始调用-一键发货-存储过程");
        List<String> strList = transferDao.onkeyfh(com_id, fh_staff_id, cashi_id);
        //logger.error("调用-一键发货-存储过程结束");
        return strList;
    }

    /**
     * 佣金分配
     * com_id 供应商公司id
     * id money 成本金额
     * merSeqId 第三方交易号
     *
     * @param cashid   订单
     * @param staff_id 操作人id
     * @return
     */
    public synchronized String Distribution(String cashid, String staff_id) {
        String str = "";
        boolean f=true;
        try {
            String hqlys = "from CashierBills where cashierBillsID = ? and fkStatus=?";
            CashierBills cashierBills = (CashierBills) baseBeanDao
                    .getBeanByHqlAndParams(hqlys,
                            new Object[]{cashid, "03"});

            StatusEntity s=(StatusEntity) baseBeanDao.getBeanByHqlAndParams("from StatusEntity where cashierbillsid=?",new Object[]{cashid});
            if(s!=null&&(s.getPaystatus().equals("01")||s.getPaystatus().equals("02"))){
                str="账款结清才可获取金币！";
                f=false;
            }
			/*促销品相关暂封
            String cxphql="SELECT COSTMONEY FROM DTGOODSBILLS WHERE CASHIERBILLSID IN " +
					"(SELECT PA.PTCASHIERBILLSID FROM DTGOODSBILLS GB, DT_PROMOTIONASSOCIATION PA" +
					" WHERE GB.CASHIERBILLSID = PA.CASHIERBILLSID AND GB.CASHIERBILLSID = ?)";
			List<BaseBean> cxp=baseBeanDao.getListBeanBySqlAndParams(cxphql, new Object[]{cashid});*/

            if (cashierBills != null&&f) {
                DtOrderBillAdd orderBillAdd = (DtOrderBillAdd) baseBeanDao.getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId = ?", new Object[]{cashierBills.getCashierBillsID()});
                Company ghs = (Company) baseBeanDao.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{orderBillAdd.getOaGysId()});
                BigDecimal money = BigDecimal.ZERO;

                logger.error(cashierBills.getJournalNum());
                String hqlg = "from GoodsBills where cashierBillsID = ?";

                List<BaseBean> goList = baseBeanDao
                        .getListBeanByHqlAndParams(
                                hqlg,
                                new Object[]{cashierBills.getCashierBillsID()});
                for (int i = 0; i < goList.size(); i++) {
                    GoodsBills goodsBills = (GoodsBills) goList.get(i);
                    if (goodsBills.getPremiums() != null && goodsBills.getPremiums().equals("1")) {
                        continue;
                    }
                    //money =(Float.valueOf(goodsBills.getCostmoney())*Float.valueOf(goodsBills.getQuantity())*100) + money;
                    money = money.add(new BigDecimal(goodsBills.getCostmoney()).multiply(new BigDecimal(goodsBills.getQuantity())).multiply(new BigDecimal(100)));
                }
                /*促销品相关暂封
                if (cxp!=null){
					Object[] cxps=cxp.toArray();
					for (int i=0;i<cxps.length;i++){
						money=money-(Float.valueOf(cxps[i].toString())*100);
						money =money.subtract(new BigDecimal(cxps[i].toString()).multiply(new BigDecimal(100)));
					}
				}*/

                str = dis(ghs.getCompanyID(), cashid, staff_id, money.compareTo(BigDecimal.ZERO) > 0 ? money.toString() : null);


                if (str == null || str.equals("")) {
                    List<BaseBean> backList = new ArrayList<BaseBean>();

                    updateCashState(cashierBills.getCompanyID(),"04",orderBillAdd.getFkDate(),cashid);//更改新标状态

                    orderBillAdd.setFhDate(new Date());

                    backList.add(orderBillAdd);
                    StatusEntity status = goldOrderService.SaveStatus(cashierBills.getCashierBillsID(), cashierBills.getJournalNum(), null, "09");
                    status.setXddate(cashierBills.getCashierDate());
                    backList.add(status);
                    baseBeanDao.executeSqlsByParmsList(backList, null, null);
                    List<BaseBean> baseBeans = baseBeanDao.getListBeanByHqlAndParams("from DtMemberBackup m where m.cashId=?", new Object[]{cashid});
                    logger.error("产品：" + cashierBills.getProjectName() + "-业务佣金人数:" + baseBeans.size());
                    if (baseBeans.size() > 0) {
                        DtMemberBackup backup = null;
                        for (int i = 0; i < baseBeans.size(); i++) {
                            backup = (DtMemberBackup) baseBeans.get(i);
                            if (backup != null && backup.getJbNum() != null && !backup.getJbbl().equals("") && backup.getMZh() != null) {
                                logger.error("产品：" + cashierBills.getProjectName() + "-count:" + i + "--账号:" + backup.getMZh() + "--金币数：" + backup.getJbNum());
                                List<String> slist = new ArrayList<String>();
                                slist.add(backup.getMZh());

                                JushMain.sendjiguangMessage("微分金成功到账" + Float.valueOf(backup.getJbNum()) / 100 + "元", "", "", "envelope", slist);
                                //pushMessage(backup);
                            }
                            backup = null;
                        }
                    }

                    List<BaseBean> dailibean = baseBeanDao.getListBeanByHqlAndParams("from DtDaiLiMember m where m.mbCusId=?", new Object[]{cashid});
                    logger.error("产品：" + cashierBills.getProjectName() + "-代理人数:" + dailibean.size());
                    if (dailibean.size() > 0) {
                        DtDaiLiMember dlMember = null;
                        for (int i = 0; i < dailibean.size(); i++) {
                            dlMember = (DtDaiLiMember) dailibean.get(i);
                            if (dlMember != null && dlMember.getJbNum() != null && dlMember.getMbZh() != null) {
                                logger.error("产品：" + cashierBills.getProjectName() + "-count:" + i + "--账号:" + dlMember.getMbZh() + "--金币数：" + dlMember.getJbNum());
                                List<String> slist = new ArrayList<String>();
                                slist.add(dlMember.getMbZh());
                                JushMain.sendjiguangMessage("产品消费返回代理佣金" + dlMember.getJbNum() + "个", "", "", "envelope", slist);
                                //pushMessage(backup);
                            }
                            dlMember = null;
                        }
                    }
                    str = "操作成功";


                } else {
                    str = "库存金币不足以支付订单号为" + str + "的佣金";
                }

                if("00".equals(cashierBills.getWfStatus4())) {
                    List<String> liststr = new ArrayList<String>();
                    addWillfishiHdBill(cashierBills.getJournalNum(),cashierBills.getCompanyID(),liststr);
                }
            }
        } catch (Exception e) {
            String a = e.toString();
            String b = a.substring(a.indexOf(":") + 1);
            logger.error("操作异常", e);
            str = b;
        }
        //logger.info("值：{}", str);
        return str;
    }

    /**
     *
     * 将需要完结分账的合单支付订单保存
     *
     * @return
     */
    private void addWillfishiHdBill(String journalNum,String companyID,List<String> liststr){
        logger.info("addWillfishiHdBill");
        List<BaseBean> beans = new ArrayList<BaseBean>();

      try {
          PayBackupBill pb = (PayBackupBill) baseBeanDao.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?", new Object[]{journalNum});
          //确认订单是合单支付
          if (pb != null && "1".equals(pb.getHdpay())) {
              logger.info("pb");

              HdBackupBill hdBackupBill = (HdBackupBill) baseBeanDao.getBeanByHqlAndParams("from HdBackupBill where journalNum = ?", new Object[]{journalNum});


              if (hdBackupBill == null) {

                  if(!liststr.contains(journalNum)) {

                      liststr.add(journalNum);
                      hdBackupBill = new HdBackupBill();
                      hdBackupBill.setHdbID(serverService.getServerID("hdp"));
                      hdBackupBill.setAttach(pb.getAttach());
                      hdBackupBill.setCreateDate(new Date());
                      hdBackupBill.setJournalNum(journalNum);
                      hdBackupBill.setStatus("0");
                      hdBackupBill.setCompanyID(companyID);
                      beans.add(hdBackupBill);
                      pb.setHdfinish("1");
                      beans.add(pb);
                      logger.info("调试信息");
                      baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans,null,null);

                  }


              }
          }
      }catch (Exception e){
          logger.error("操作异常", e);
      }

    }



    /**
     *
     * 记录明细
     */
    public  void addWxAccountDetail(HdBackupBillHistory hdBackupBill, List<BaseBean> beans,List<String> mainlist,List<String> monthlist,List<String> jourlist){
        try {
            WxAccountDetail wxAccountDetail = (WxAccountDetail) baseBeanDao.getBeanByHqlAndParams("from WxAccountDetail where journalNum = ?", new Object[]{hdBackupBill.getJournalNum()});

           if(wxAccountDetail==null) {
               if (!jourlist.contains(hdBackupBill.getJournalNum())) {
                   jourlist.add(hdBackupBill.getJournalNum());
                   wxAccountDetail = new WxAccountDetail();

                   wxAccountDetail.setWxdId(serverService.getServerID("wxdid"));
                   wxAccountDetail.setCompanyID(hdBackupBill.getCompanyID());
                   wxAccountDetail.setCreateDate(hdBackupBill.getFinishDate());
                   wxAccountDetail.setJournalNum(hdBackupBill.getJournalNum());




                   SubOrders sb = (SubOrders) baseBeanDao.getBeanByHqlAndParams("from SubOrders where out_trade_no = ?", new Object[]{wxAccountDetail.getJournalNum()});


                   WxMainAccount wxMainAccount = (WxMainAccount) baseBeanDao.getBeanByHqlAndParams("from WxMainAccount where companyID = ?", new Object[]{wxAccountDetail.getCompanyID()});

                   if (wxMainAccount == null) {
                       if (!mainlist.contains(wxAccountDetail.getCompanyID())) {

                           mainlist.add(wxAccountDetail.getCompanyID());
                           wxMainAccount = new WxMainAccount();
                           wxMainAccount.setWxmId(serverService.getServerID("wxmId"));
                           wxMainAccount.setCompanyID(wxAccountDetail.getCompanyID());
                           wxMainAccount.setRate("0.006");
                           wxMainAccount.setSr("0");
                           wxMainAccount.setYe("0");
                           wxMainAccount.setZc("0");

                       }

                   }
                   float rate = Float.parseFloat(wxMainAccount.getRate());
                   BigDecimal d = null;
                   if(hdBackupBill.getMoney()==null||hdBackupBill.getMoney().equals("")||hdBackupBill.getMoney().equals("0")) {
                       d = new BigDecimal(sb.getMoney()).multiply(new BigDecimal(1 - rate)).setScale(0, BigDecimal.ROUND_HALF_UP);
                   }else{
                       d = new BigDecimal(hdBackupBill.getMoney());
                   }

                   wxMainAccount.setSr(new BigDecimal(wxMainAccount.getSr()).add(d).toString());
                   wxMainAccount.setYe(new BigDecimal(wxMainAccount.getYe()).add(d).toString());

                   Date date = hdBackupBill.getFinishDate();

                   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                   String dd = dateFormat.format(date);


                   WxMonthAccount wxMonthAccount = (WxMonthAccount) baseBeanDao.getBeanByHqlAndParams("from WxMonthAccount where companyID = ? and month = ?", new Object[]{wxAccountDetail.getCompanyID(), dd});

                   if (wxMonthAccount == null) {
                       if (!monthlist.contains(wxAccountDetail.getCompanyID() + dd)) {
                           monthlist.add(wxAccountDetail.getCompanyID() + dd);
                           wxMonthAccount = new WxMonthAccount();
                           wxMonthAccount.setWxaId(serverService.getServerID("wxaid"));
                           wxMonthAccount.setSr("0");
                           wxMonthAccount.setZc("0");
                           wxMonthAccount.setYe("0");
                           wxMonthAccount.setCompanyID(wxAccountDetail.getCompanyID());
                           wxMonthAccount.setMonth(dd);

                       }

                   }

                   wxMonthAccount.setSr(new BigDecimal(wxMonthAccount.getSr()).add(d).toString());
                   wxMonthAccount.setYe(new BigDecimal(wxMonthAccount.getYe()).add(d).toString());


                   wxAccountDetail.setRate(wxMainAccount.getRate());

                   wxAccountDetail.setMoney(d.toString());



                   if("3".equals(hdBackupBill.getStatus())){
                       TEshopCusCom tc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where companyId = ?",new Object[]{wxAccountDetail.getCompanyID()});
                       if(tc!=null) {
                          wxAccountDetail.setSccid(tc.getSccId());
                      }
                       wxAccountDetail.setSztype("D");
                       wxAccountDetail.setType("佣金代收");
                         PayCashierBill payCashierBill = (PayCashierBill)baseBeanDao.getBeanByHqlAndParams("from PayCashierBill k where k.oriJournalNum = ?",new Object[]{hdBackupBill.getJournalNum()});

                       try {
                           if(payCashierBill!=null) {
                               String hql = "from Staff s where s.staffID = (select c.contactUserID from CashierBills c where c.journalNum = ?)";
                               Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{payCashierBill.getPayJournalNum()});
                               wxAccountDetail.setStaffName(staff.getStaffName());
                           }
                       }catch (Exception e){
                           logger.error("操作异常", e);
                       }
                   }else{
                       wxAccountDetail.setSztype("A");
                       wxAccountDetail.setType("供应商成本");
                       try {
                           String hql = "from Staff s where s.staffID = (select c.contactUserID from CashierBills c where c.journalNum = ?)";
                           Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{hdBackupBill.getJournalNum()});
                           wxAccountDetail.setStaffName(staff.getStaffName());
                       }catch (Exception e){
                           logger.error("操作异常", e);
                       }
                   }

                   beans.add(wxMainAccount);
                   beans.add(wxMonthAccount);
                   beans.add(wxAccountDetail);
                   logger.info("beans: {}", beans.size());
                   baseBeanDao.executeHqlsByParmsList(beans, null, null);
               }
           }
        }catch (Exception e){
            logger.error("操作异常", e);
        }

    }

    /**
     *
     * //将已发货且15天内未确认收货的订单加入待完结分账表里
     */
    public void addWqrshOrder(){
        //找出2022年9月19号以后的已发货的并且是微信支付的订单

        String sql = "select dd.OA_BILL_JOUNUM,dd.OA_COM_ID from DT_ORDER_BILL_ADD dd,Dtpaybackupbill p,Dtcashierbills ss where dd.oa_bill_jounum = p.journalnum and p.journalnum = ss.journalnum and ss.fkstatus='02' and dd.fhdate < (sysdate - 15) and dd.fhdate>to_date('2022/09/19 16:48:29','yyyy-mm-dd hh24:mi:ss') and p.hdpay = '1' and p.hdfinish is null";
        List<Object> list = baseBeanDao.getListObjectBySqlAndParams(sql,null);
        List<String> liststr = new ArrayList<String>();
        for (int i = 0;i<list.size();i++){

            Object[] obj = (Object[])list.get(i);
            if(obj!=null&&obj.length>0) {
                if(obj[0]!=null&&!obj[0].equals("")) {

                    addWillfishiHdBill(obj[0].toString(), obj[1].toString(),liststr);
                }
            }

      }


    }

    /**
     * 购买商城会员发短信
     */
    @Override
    public synchronized void pushMessage(DtMemberBackup backup) {
        try {
            msage.setMobiles(backup.getMZh());
            msage.setMessage("产品消费返回微金币" + backup.getJbNum() + "个");
            msage.sendMsg("【微分金平台】");
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
    }

    /**
     * 分利存储过程调用
     *
     * @param com_id   公司id
     * @param cashid   订单id
     * @param staff_id 操作人id
     */
    private String dis(String com_id, String cashid, String staff_id, String money) throws Exception {
        //	logger.error("开始调用-金币分配-存储过程");
        //调用金币分配存储过程
        //	logger.error("id-"+cashid);
        String cashnum = transferDao.Distribution(com_id, cashid, staff_id, money);
        //	logger.error("调用-金币分配-存储过程结束");
        return cashnum;
    }

    /**
     * 供应商成本存储过程调用
     *
     * @param com_id   公司id
     * @param cashid   订单id
     * @param staff_id 操作人id
     * @param money    成本价
     */
    public synchronized void cost(String com_id, String cashid, String staff_id, String money) {
        //	logger.error("开始调用-供应商成本-存储过程");
        //调用金币分配存储过程
        transferDao.cost(com_id, cashid, staff_id, money);
        //	logger.error("调用-供应商成本-存储过程结束");
    }

    /**
     * 修改订单状态
     *
     * @param cashid 订单id
     * @param status 要改成的状态
     */
    public void getCoasUpdate(String cashid, String status) {
        //	logger.info("开始调用-修改订单状态-存储过程");
        //调用修改订单状态存储过程
        transferDao.getCoasUpdate(cashid, status);
        //	logger.info("调用-修改订单状态-存储过程结束");
    }

    /**
     * 虚拟发货 com_id 公司id
     *
     * @param cashid
     */
    @Override
    public synchronized void virtual(String cashid) {
        try {
            //CashierBills c=baseBeanDao.getBeanByHqlAndParams("from CashierBills c where c.")
            //	logger.error("开始调用-虚拟发货-存储过程");
            transferDao.virtual(cashid, "cstaff20160325ZAUAJEU6JH6192643691");
            //	logger.error("id-"+cashid);
            //logger.error("调用-虚拟发货-存储过程结束");

            List<BaseBean> beanList = new ArrayList<BaseBean>();
            DtOrderBillAdd billAdd = (DtOrderBillAdd) baseBeanDao
                    .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{cashid});

            billAdd.setShDate(new Date());

            beanList.add(billAdd);

            StatusEntity status = goldOrderService.SaveStatus(cashid, null, null, "06");

            beanList.add(status);
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beanList, null, null);

        } catch (Exception e) {
            logger.error("操作异常", e);
        }

    }

    /**
     * 用户确认收货
     *
     * @param cashierBillsID 订单id
     */
    @Override
    public synchronized String recognitionHarvest(String cashierBillsID) {
        String str = null;
        try {
            //	logger.error("开始调用-确认收货-存储过程");
            transferDao.recognitionHarvest(cashierBillsID);
            //	logger.error("id-"+cashierBillsID);
            //	logger.error("结束调用-确认收货-存储过程");
            List<BaseBean> beanList = new ArrayList<BaseBean>();
            DtOrderBillAdd billAdd = (DtOrderBillAdd) baseBeanDao
                    .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{cashierBillsID});

            billAdd.setShDate(new Date());

            beanList.add(billAdd);

            StatusEntity status = goldOrderService.SaveStatus(cashierBillsID, null, null, "06");

            beanList.add(status);
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beanList, null, null);

            updateCashState(billAdd.getOaComId(),"03",billAdd.getFkDate(),cashierBillsID);

            str = Distribution(cashierBillsID, "cstaff20160325ZAUAJEU6JH6192643691");
            //	logger.error("金币分配-"+str);
            if (!"操作成功".equals(str)) {
				/*msage.setMobiles("15210904250");// 15210904250
				msage.setMessage("订单id为：" + cashierBillsID
						+ "的订单，系统自动收货分配金币时出错，错误信息为：" + str);
				String reStr;*/
                //reStr = msage.sendMsg();
                //	logger.info("值：{}", reStr);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("操作异常", e);
        }
        return str;
    }

    /**
     * 用户确认收货ljc
     *
     * @param cashierBillsID 订单id
     */
    @Override
    public synchronized String confirmReceipt(String cashierBillsID) {
        String s = "1";
        try {
            //	logger.error("开始调用-确认收货-存储过程");
            transferDao.recognitionHarvest(cashierBillsID);
            //	logger.error("id-"+cashierBillsID);
            //	logger.error("结束调用-确认收货-存储过程");
            List<BaseBean> beanList = new ArrayList<BaseBean>();
            DtOrderBillAdd billAdd = (DtOrderBillAdd) baseBeanDao
                    .getBeanByHqlAndParams("from DtOrderBillAdd where oaBillId=?", new Object[]{cashierBillsID});

            billAdd.setShDate(new Date());

            beanList.add(billAdd);

            StatusEntity status = goldOrderService.SaveStatus(cashierBillsID, null, null, "06");

            beanList.add(status);
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beanList, null, null);
        } catch (Exception e) {
            s = "0";
            logger.error("id-" + cashierBillsID + "-确认收货失败");
        }
        return s;
    }

    /**
     * 金币充值
     *
     * @param comid    公司id
     * @param jum      订单编号
     * @param staffid  付款人id
     * @param money    购买金币金额
     * @param appstyle 支付方式
     * @param trade_no 第三方交易号
     */
    @Override
    public synchronized void buyJinbi(String comid, String jum, String staffid, String sccid, String money, String appstyle, String trade_no) {
        String hql = "from CashierBills where journalNum = ?";
        CashierBills cashier = (CashierBills) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{jum});
        if (cashier == null) {
            //logger.error("开始调用-金币充值-存储过程");
            transferDao.buyJinbi(comid, jum, staffid, sccid, money, appstyle, trade_no);
            //logger.error("结束调用-金币充值-存储过程");

        } else {
            logger.info("该订单已经生成过了");
            //logger.error("该订单已经生成过了");
        }

    }


    //物流   单号识别
//	@Override
//	public String expno(String expNo) throws Exception {
//
//		String requestData= "{'LogisticCode':'" + expNo + "'}";
//
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
//		params.put("EBusinessID", EBusinessID);
//		params.put("RequestType", "2002");
//		String dataSign=encrypt(requestData, AppKey, "UTF-8");
//		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
//		params.put("DataType", "2");
//
//		String result=sendPost(ReqURL, params);
//
//		//根据公司业务处理返回的信息......
//
//		return result;
//	}
//
//	//物流及时查询
//	@Override
//	public String wuLiu(String expCode, String expNo) throws Exception {
//
//		String requestData= "{'OrderCode':'','ShipperCode':'" + expCode + "','LogisticCode':'" + expNo + "'}";
//
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("RequestData", urlEncoder(requestData, "UTF-8"));
//		params.put("EBusinessID", EBusinessID);
//		params.put("RequestType", "1002");
//		String dataSign=encrypt(requestData, AppKey, "UTF-8");
//		params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
//		params.put("DataType", "2");
//
//		String result=sendPost(ReqURL, params);
//
//		//根据公司业务处理返回的信息......
//		return result;
//	}
//
//	/**
//     * MD5加密
//     * @param str 内容
//     * @param charset 编码方式
//	 * @throws Exception
//     */
//	@SuppressWarnings("unused")
//	private String MD5(String str, String charset) throws Exception {
//	    MessageDigest md = MessageDigest.getInstance("MD5");
//	    md.update(str.getBytes(charset));
//	    byte[] result = md.digest();
//	    StringBuffer sb = new StringBuffer(32);
//	    for (int i = 0; i < result.length; i++) {
//	        int val = result[i] & 0xff;
//	        if (val <= 0xf) {
//	            sb.append("0");
//	        }
//	        sb.append(Integer.toHexString(val));
//	    }
//	    return sb.toString().toLowerCase();
//	}
//
//	private String base64(String str, String charset) throws UnsupportedEncodingException{
//		String encoded = base64Encode(str.getBytes(charset));
//		return encoded;
//	}
//
//	/**
//     * base64编码
//     * @param str 内容
//     * @param charset 编码方式
//	 * @throws UnsupportedEncodingException
//     */
//	@SuppressWarnings("unused")
//	private String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
//		String result = URLEncoder.encode(str, charset);
//		return result;
//	}
//
//	/**
//     * 电商Sign签名生成
//     * @param content 内容
//     * @param keyValue Appkey
//     * @param charset 编码方式
//	 * @throws UnsupportedEncodingException ,Exception
//	 * @return DataSign签名
//     */
//	@SuppressWarnings("unused")
//	private String encrypt (String content, String keyValue, String charset) throws UnsupportedEncodingException, Exception
//	{
//		if (keyValue != null)
//		{
//			return base64(MD5(content + keyValue, charset), charset);
//		}
//		return base64(MD5(content, charset), charset);
//	}
//
//	 /**
//     * 向指定 URL 发送POST方法的请求
//     * @param url 发送请求的 URL
//     * @param params 请求的参数集合
//     * @return 远程资源的响应结果
//     */
//	@SuppressWarnings("unused")
//	private String sendPost(String url, Map<String, String> params) {
//        OutputStreamWriter out = null;
//        BufferedReader in = null;
//        StringBuilder result = new StringBuilder();
//        try {
//            URL realUrl = new URL(url);
//            HttpURLConnection conn =(HttpURLConnection) realUrl.openConnection();
//            // 发送POST请求必须设置如下两行
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            // POST方法
//            conn.setRequestMethod("POST");
//            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.connect();
//            // 获取URLConnection对象对应的输出流
//            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//            // 发送请求参数
//            if (params != null) {
//		          StringBuilder param = new StringBuilder();
//		          for (Map.Entry<String, String> entry : params.entrySet()) {
//		        	  if(param.length()>0){
//		        		  param.append("&");
//		        	  }
//		        	  param.append(entry.getKey());
//		        	  param.append("=");
//		        	  param.append(entry.getValue());
//		        	  //logger.info("调试信息");
//		          }
//		          //logger.info("调试信息");
//		          out.write(param.toString());
//            }
//            // flush输出流的缓冲
//            out.flush();
//            // 定义BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
//        } catch (Exception e) {
//            logger.error("操作异常", e);
//        }
//        //使用finally块来关闭输出流、输入流
//        finally{
//            try{
//                if(out!=null){
//                    out.close();
//                }
//                if(in!=null){
//                    in.close();
//                }
//            }
//            catch(IOException ex){
//                logger.error("操作异常", ex);
//            }
//        }
//        return result.toString();
//    }
//
//    private static char[] base64EncodeChars = new char[] {
//        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
//        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
//        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
//        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
//        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
//        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
//        'w', 'x', 'y', 'z', '0', '1', '2', '3',
//        '4', '5', '6', '7', '8', '9', '+', '/' };
//
//    public static String base64Encode(byte[] data) {
//        StringBuffer sb = new StringBuffer();
//        int len = data.length;
//        int i = 0;
//        int b1, b2, b3;
//        while (i < len) {
//            b1 = data[i++] & 0xff;
//            if (i == len)
//            {
//                sb.append(base64EncodeChars[b1 >>> 2]);
//                sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
//                sb.append("==");
//                break;
//            }
//            b2 = data[i++] & 0xff;
//            if (i == len)
//            {
//                sb.append(base64EncodeChars[b1 >>> 2]);
//                sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
//                sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
//                sb.append("=");
//                break;
//            }
//            b3 = data[i++] & 0xff;
//            sb.append(base64EncodeChars[b1 >>> 2]);
//            sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
//            sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
//            sb.append(base64EncodeChars[b3 & 0x3f]);
//        }
//        return sb.toString();
//    }

    @Override
//	public String wuLiu(String expCode, String expNo) throws Exception {
    public String wuLiu(String expNo) throws Exception {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode); //格式为:Authorization:APPCODE 83359fd73fe11248385f570e3c139xxx
        Map<String, String> querys = new HashMap<String, String>();
        //动态获取运单号，存到querys中
        querys.put("no", expNo);// !!! 请求参数
        String result = null;
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            result = EntityUtils.toString(response.getEntity());//这个就是返回值呦
            logger.info("值：{}", result);
        } catch (Exception e) {
            logger.error("操作异常", e);
        }
        return result;
    }

    /**
     * 保存拣货出库单
     *
     * @param cashid 订单号
     * @throws Exception
     */
    public synchronized void saveSorting(String cashid) throws Exception {
        CashierBills c = (CashierBills) baseBeanDao.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{cashid});
        List<BaseBean> gs = baseBeanDao.getListBeanByHqlAndParams("from GoodsBills where cashierBillsID=?", new Object[]{cashid});
        StatusEntity s = (StatusEntity) baseBeanDao.getBeanByHqlAndParams("from StatusEntity where cashierbillsid=?", new Object[]{cashid});
        transferPay t = (transferPay) baseBeanDao.getBeanByHqlAndParams("from transferPay where cashierbillsid=?", new Object[]{cashid});
        if (c != null) {
            Delivery d = null;
            d = (Delivery) baseBeanDao.getBeanByHqlAndParams("from Delivery where cashierbillsid=?", new Object[]{cashid});
            if (d == null) {
                List<BaseBean> param = new ArrayList<>();
                d = new Delivery();
                d.setCashierbillsid(cashid);
                d.setCompanyid(c.getCompanyID());
                d.setDeliverynum(serverService.getBillID(c.getCompanyID()));
                d.setJournalnum(c.getjNumOrder());
                d.setOrderid(serverService.getServerID("order"));
                d.setSellermessage(c.getRemark());
                if(t!=null&&t.getMapkey()!=null&&!t.getMapkey().equals("")){
                   String pname=t.getMapval().substring(t.getMapval().indexOf("-",1)+1,t.getMapval().indexOf("-",2));
                   String pcid=t.getMapval().substring(t.getMapval().indexOf("-",1)+1,t.getMapval().indexOf("-",2));
                   d.setPurchaserid(pcid);
                   d.setPurchasername(pname);
                }
                d.setAdddate(new Date());
                d.setStatus("01");

                s.setSupplierstatus("01");
                if (gs != null && gs.size() > 0) {
                    param.add(d);
                    param.add(s);
                    for (int i = 0; i < gs.size(); i++) {
                        GoodsBills g = (GoodsBills) gs.get(i);
                        DeliverGoods dg = new DeliverGoods();
                        dg.setDeliveryid(serverService.getServerID("delivery"));
                        dg.setGoodname(g.getGoodsName());
                        dg.setGoodsid(g.getGoodsID());
                        dg.setPpid(g.getPpID());
                        dg.setQuantity("0");
                        dg.setOrdernum(g.getQuantity());
                        dg.setUnitprice(g.getPrice());
                        dg.setTotalprices(g.getMoney());
                        dg.setOrderid(d.getOrderid());
                        param.add(dg);
                    }
                    baseBeanDao.executeHqlsByParmsList(param, null, null);
                }
            }
        }
    }

    /**
     * 拣货出库逻辑处理
     *
     * @return
     * @throws Exception
     */
    public String DeliveryLogicalProcessing(String content, String orderid, String staffid) throws Exception {
        List<Object> json = JSON.parseArray(content);
        Map<String, String> map = new HashMap<String, String>();
        Iterator<Object> it = json.iterator();
        JSONObject jsonObject = new JSONObject();
        String flag = "00";//结果种类  00 成功  01 库存不足  02 拣货未完成 03:发货单已生成不可重复生成 04:拣货人员数据错误
        while (it.hasNext()) {
            Object j = it.next();
            jsonObject = JSONObject.fromObject(j);
            map.put(jsonObject.getString("name"), jsonObject.getString("value"));
            if (Float.valueOf(jsonObject.getString("value")) <= 0) {
                flag = "02";
            }
        }
        List<BaseBean> list = new ArrayList<BaseBean>();
        List<BaseBean> list_i = new ArrayList<BaseBean>();
        List<BaseBean> list_g = new ArrayList<BaseBean>();
        Delivery d = (Delivery) baseBeanDao.getBeanByHqlAndParams("from Delivery where orderid=?", new Object[]{orderid});
        String sqlString = "from DeliverGoods where orderid=?";
        List<BaseBean> baseBeans = baseBeanDao.getListBeanByHqlAndParams(sqlString, new Object[]{orderid});

        if (baseBeans != null && baseBeans.size() > 0) {
            for (int i = 0; i < baseBeans.size(); i++) {
                DeliverGoods dg = (DeliverGoods) baseBeans.get(i);
                Float b = Float.valueOf(dg.getOrdernum()) - Float.valueOf(map.get(dg.getDeliveryid()));
                dg.setQuantity(map.get(dg.getDeliveryid()));
                dg.setError(b + "");
                list.add(dg);
                list_g.add(dg);
                if (d != null && flag.equals("00")) {
                    flag = outOfStock(dg, list_i, flag);
                }
            }
        }

        if (d != null && flag.equals("00")) {
            d.setStatus("02");
            Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{staffid});
            if (staff != null) {
                d.setPickingid(staffid);
                d.setPichingname(staff.getStaffName());
                flag = saveSend(d, list_g, list, flag);
                list.add(d);
            } else {
                flag = "04";
            }
        }

        if (flag.equals("00")) {
            baseBeanDao.executeHqlsByParmsList(list, null, null);
        }
        return flag;
    }

    /**
     * 出库
     *
     * @param d    拣货出库产品
     * @param list
     * @param flag 结果种类  00:成功  01:库存不足  02:拣货未完成 03:发货单已生成不可重复生成
     * @return
     */
    private String outOfStock(DeliverGoods d, List<BaseBean> list, String flag) {
        ProductPackaging p = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams("from ProductPackaging where ppid=?", new Object[]{d.getPpid()});
        String depotHql = "from DepotManage where companyID=? and depotPID=? and depotName=? and depotState=?";
        DepotManage depot = (DepotManage) baseBeanDao.getBeanByHqlAndParams(depotHql, new Object[]{p.getCompanyID(), "001", "销售库", "00"});
        String invHql = "from Inventory where companyID=? and warehouse=? and goodsID=? and productId=?";
        Inventory inv = (Inventory) baseBeanDao.getBeanByHqlAndParams(invHql, new Object[]{p.getCompanyID(), depot.getDepotID(), p.getGoodsID(), p.getPpID()});
        if (inv != null && (Double.parseDouble(inv.getInvenQuantity()) - Double.parseDouble(d.getQuantity())) > 0) {
            stockInv sto1 = new stockInv();
            sto1.setStockinvID(serverService.getServerID("stockInv"));
            sto1.setCompanyID(p.getCompanyID());
            sto1.setGoodsBillsId(d.getDeliveryid());
            sto1.setGoodsID(d.getGoodsid());
            sto1.setGoodsName(d.getGoodname());
            sto1.setGoodsType(p.getType());
            sto1.setIntime(new Date());
            sto1.setStaffID("");
            sto1.setStaffName("");
            sto1.setWarehouse(depot.getDepotID());
            sto1.setWarehouseName(depot.getDepotName());
            sto1.setType("01");
            sto1.setInvenQuantity(d.getQuantity());
            sto1.setSummoney(Double.parseDouble(p.getPrice()) * Double.parseDouble(d.getQuantity()) + "");
            list.add(sto1);
            inv.setInvenQuantity(Double.parseDouble(inv.getInvenQuantity()) - Double.parseDouble(d.getQuantity()) + "");
            inv.setSumPrice(StringUtil.formatFloatNumber(Double.parseDouble(inv.getSumPrice()) - Double.parseDouble(p.getPrice()) * Double.parseDouble(d.getQuantity())));
            list.add(inv);
            /*numHql[i]="update dtGoodsNum set cashierBillsID=?,goodsBillsID=? where productId=? and status=? and rownum <=?";
            obj.add(new Object[]{in.getInventoryID(),goods.getGoodsBillsID(),goods.getGoodsID(),inv.getInventoryID(),inv.getProductId(),"03",goods.getQuantity()});*/
        } else {
            flag = "01";
        }
        return flag;
    }

    /**
     * 生成发货单
     *
     * @param d
     * @param list_g
     * @param list
     * @param flag   结果种类  00:成功  01:库存不足  02:拣货未完成 03:发货单已生成不可重复生成
     * @throws Exception
     */
    private synchronized String saveSend(Delivery d, List<BaseBean> list_g, List<BaseBean> list, String flag) throws Exception {
        if (d != null) {
            CashierBills c = (CashierBills) baseBeanDao.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{d.getCashierbillsid()});
            StatusEntity s = (StatusEntity) baseBeanDao.getBeanByHqlAndParams("from StatusEntity where cashierbillsid=?", new Object[]{d.getCashierbillsid()});
            SendBill b = null;
            b = (SendBill) baseBeanDao.getBeanByHqlAndParams("from SendBill where cashierbillsid=?", new Object[]{d.getCashierbillsid()});
            if (b == null) {
                b = new SendBill();
                b.setCashierbillsid(b.getCashierbillsid());
                b.setCompanyid(c.getCompanyID());
                b.setSendnum(serverService.getBillID(c.getCompanyID()));
                b.setJournalnum(c.getjNumOrder());
                b.setSendid(serverService.getServerID("send"));
                b.setSellermessage(c.getRemark());
                b.setPurchasername(d.getPurchasername());
                b.setPurchaserid(d.getPurchaserid());
                b.setCashierbillsid(c.getCashierBillsID());
                b.setAdddate(new Date());
                b.setStatus("01");

                s.setSupplierstatus("02");
                s.setJhdate(new Date());
                c.setFkStatus("02");
                if (list_g != null && list_g.size() > 0) {
                    list.add(b);
                    list.add(s);
                    list.add(c);
                    for (int i = 0; i < list_g.size(); i++) {
                        DeliverGoods g = (DeliverGoods) list_g.get(i);
                        SendGoods dg = new SendGoods();
                        dg.setSendgoodid(serverService.getServerID("sendgood"));
                        dg.setGoodname(g.getGoodname());
                        dg.setGoodsid(g.getGoodsid());
                        dg.setPpid(g.getPpid());
                        dg.setQuantity(g.getQuantity());
                        dg.setOrdernum(g.getQuantity());
                        dg.setUnitprice(g.getUnitprice());
                        dg.setTotalprices(g.getTotalprices());
                        dg.setSendid(b.getSendid());
                        dg.setError(g.getError());
                        list.add(dg);
                    }
                }
            } else {
                flag = "03";
            }
        }
        return flag;
    }

    /**
     * 发货逻辑处理
     *
     * @return
     * @throws Exception
     */
    public String SendLogicalProcessing(String sendid, String staffid) throws Exception {
        String flag = "00";//结果种类  00 成功  01生成失败

        List<BaseBean> list = new ArrayList<BaseBean>();
        SendBill d = (SendBill) baseBeanDao.getBeanByHqlAndParams("from SendBill where sendid=?", new Object[]{sendid});
        String sqlString = "from SendGoods where sendid=?";
        List<BaseBean> baseBeans = baseBeanDao.getListBeanByHqlAndParams(sqlString, new Object[]{sendid});

        if (d != null && flag.equals("00")) {
            d.setStatus("02");
            Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{staffid});
            if (staff != null) {
                d.setPickingid(staffid);
                d.setPichingname(staff.getStaffName());
                flag = saveTransport(d, baseBeans, list, flag);
                list.add(d);
            } else {
                flag = "01";
            }
        }

        if (flag.equals("00")) {
            baseBeanDao.executeHqlsByParmsList(list, null, null);
        }
        return flag;
    }

    /**
     * 生成送货单
     *
     * @param d
     * @param list_g
     * @param list
     * @param flag   结果种类  00:成功  01:库存不足  02:拣货未完成 03:发货单已生成不可重复生成
     * @throws Exception
     */
    private synchronized String saveTransport(SendBill d, List<BaseBean> list_g, List<BaseBean> list, String flag) throws Exception {
        if (d != null) {
            CashierBills c = (CashierBills) baseBeanDao.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{d.getCashierbillsid()});
            StatusEntity s = (StatusEntity) baseBeanDao.getBeanByHqlAndParams("from StatusEntity where cashierbillsid=?", new Object[]{d.getCashierbillsid()});
            TransportBill b = null;
            b = (TransportBill) baseBeanDao.getBeanByHqlAndParams("from TransportBill where cashierbillsid=?", new Object[]{d.getCashierbillsid()});
            if (b == null) {
                b = new TransportBill();
                b.setCashierbillsid(b.getCashierbillsid());
                b.setCompanyid(c.getCompanyID());
                b.setTransportnum(serverService.getBillID(c.getCompanyID()));
                b.setJournalnum(c.getjNumOrder());
                b.setTransportid(serverService.getServerID("send"));
                b.setSellermessage(c.getRemark());
                b.setPurchasername(d.getPurchasername());
                b.setPurchaserid(d.getPurchaserid());
                b.setCashierbillsid(c.getCashierBillsID());
                b.setAdddate(new Date());
                b.setStatus("01");

                s.setSupplierstatus("03");
                s.setFhdate(new Date());
                if (list_g != null && list_g.size() > 0) {
                    list.add(b);
                    list.add(s);
                    for (int i = 0; i < list_g.size(); i++) {
                        SendGoods g = (SendGoods) list_g.get(i);
                        TransportGoods dg = new TransportGoods();
                        dg.setTgoodid(serverService.getServerID("sendgood"));
                        dg.setGoodname(g.getGoodname());
                        dg.setGoodsid(g.getGoodsid());
                        dg.setPpid(g.getPpid());
                        dg.setQuantity(g.getQuantity());
                        dg.setOrdernum(g.getQuantity());
                        dg.setUnitprice(g.getUnitprice());
                        dg.setTotalprices(g.getTotalprices());
                        dg.setTransportid(b.getTransportid());
                        dg.setError(g.getError());
                        list.add(dg);
                    }
                }
            } else {
                flag = "01";
            }
        }
        return flag;
    }

    /**
     * 送货逻辑处理
     *
     * @return
     * @throws Exception
     */
    public String TransportLogicalProcessing(String transportid, String Waybillno, String ExCode, String staffid) throws Exception {
        String flag = "00";//结果种类  00 成功  01生成失败
        List<BaseBean> list = new ArrayList<BaseBean>();
        TransportBill d = (TransportBill) baseBeanDao.getBeanByHqlAndParams("from TransportBill where transportid=?", new Object[]{transportid});
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{staffid});
        if (staff != null) {
            d.setPickingid(staffid);
            d.setPichingname(staff.getStaffName());
            d.setStatus("02");
            list.add(d);

            StatusEntity s = (StatusEntity) baseBeanDao.getBeanByHqlAndParams("from StatusEntity where cashierbillsid=?", new Object[]{d.getCashierbillsid()});

            if (Waybillno != null && !Waybillno.equals("")) {
                String hql = " from DtOrderBillAdd where oaBillId=?";
                DtOrderBillAdd dto = (DtOrderBillAdd) baseBeanDao.getBeanByHqlAndParams(hql,
                        new Object[]{d.getCashierbillsid()});

                dto.setWaybillno(Waybillno);
                dto.setExCode(ExCode);
                list.add(dto);
                s.setSupplierstatus("04");
            }
            s.setShdate(new Date());
            list.add(s);

            baseBeanDao.executeHqlsByParmsList(list, null, null);
        } else {
            flag = "01";
        }
        return flag;
    }

    /**
     * 保存欠款单
     *
     * @param cashid     单据id
     * @param raddressId 地址id
     * @param flag       结果种类  00 成功  01生成失败 02欠款单重复生成
     * @return
     * @throws Exception
     */
    public String addOverdraft(String cashid, String raddressId, String flag) throws Exception {
        CashierBills c = (CashierBills) baseBeanDao.getBeanByHqlAndParams("from CashierBills where cashierBillsID=?", new Object[]{cashid});
        StatusEntity s = (StatusEntity) baseBeanDao.getBeanByHqlAndParams("from StatusEntity where cashierbillsid=?", new Object[]{cashid});
        transferPay t = (transferPay) baseBeanDao.getBeanByHqlAndParams("from transferPay where cashierbillsid=?", new Object[]{cashid});
        List<BaseBean> list_g = baseBeanDao.getListBeanByHqlAndParams("from GoodsBills where cashierBillsID=?", new Object[]{cashid});
        List<BaseBean> list = new ArrayList<BaseBean>();
        OverdraftBill b = null;
        b = (OverdraftBill) baseBeanDao.getBeanByHqlAndParams("from OverdraftBill where cashierbillsid=?", new Object[]{cashid});
        if (b == null) {
            b = new OverdraftBill();
            b.setCashierbillsid(b.getCashierbillsid());
            b.setCompanyid(c.getCompanyID());
            b.setOverdraftnum(serverService.getBillID(c.getCompanyID()));
            b.setJournalnum(c.getjNumOrder());
            b.setOverdraftid(serverService.getServerID("send"));
            if(t!=null&&t.getMapkey()!=null&&!t.getMapkey().equals("")){
                String pname=t.getMapval().substring(t.getMapval().indexOf("-",1)+1,t.getMapval().indexOf("-",2));
                String pcid=t.getMapval().substring(t.getMapval().indexOf("-",1)+1,t.getMapval().indexOf("-",2));
                b.setPurchaserid(pcid);
                b.setPurchasername(pname);
            }
            b.setCashierbillsid(c.getCashierBillsID());
            b.setAdddate(new Date());
            b.setStatus("01");

            s.setPaystatus("02");
            if (list_g != null && list_g.size() > 0) {
                list.add(b);
                list.add(s);
                for (int i = 0; i < list_g.size(); i++) {
                    GoodsBills g = (GoodsBills) list_g.get(i);
                    OverdraftGoods dg = new OverdraftGoods();
                    dg.setOgid(serverService.getServerID("Ogid"));
                    dg.setGoodname(g.getGoodsName());
                    dg.setGoodsid(g.getGoodsID());
                    dg.setPpid(g.getPpID());
                    dg.setQuantity(g.getQuantity());
                    dg.setUnitprice(g.getPrice());
                    dg.setTotalprices(g.getMoney());
                    dg.setOverdraftid(b.getOverdraftid());
                    list.add(dg);
                }
            }
            if (raddressId != null && !raddressId.equals("")) {
                list.add(addAddress(list, cashid, raddressId));
            }
            baseBeanDao.executeSqlsByParmsList(list, null, null);
        } else {
            flag = "02";
        }
        return flag;
    }

    /**
     * 保存地址
     * @param cashid     单据id
     * @param raddressId 地址id
     * @throws Exception
     */
    public void addAddress(String cashid, String raddressId) throws Exception {
        List<BaseBean> list = new ArrayList<BaseBean>();
        if (raddressId != null && !raddressId.equals("")) {
            baseBeanDao.update(addAddress(list, cashid, raddressId));
        }
    }

    /**
     * 保存收货地址
     *
     * @param cashid     订单id
     * @param raddressId 公司发货地址id
     * @throws Exception
     */
    private BaseBean addAddress(List<BaseBean> list, String cashid, String raddressId) throws Exception {
        RefundAddress ra = (RefundAddress) baseBeanDao.getBeanByHqlAndParams("from RefundAddress where raddressId=?", new Object[]{raddressId});
        DtOrderBillAdd dto = (DtOrderBillAdd) baseBeanDao.getBeanByHqlAndParams(" from DtOrderBillAdd where oaBillId=?",
                new Object[]{cashid});
        dto.setReceiveaddress(ra.getRarea() + ra.getRstreet());
        dto.setReceivecode(ra.getRpostcode());
        dto.setReceivename(ra.getRname());
        dto.setReceivetel(ra.getRtel());
        return dto;
    }

    /**
     *
     * 退款补充数据
     * @param wxPayDto
     * @return
     */
    public List<WxPayDto> getRefundInfo(WxPayDto wxPayDto){
            List<WxPayDto> wxPayDtoList = new ArrayList<WxPayDto>();
            String hql = "from CashierBills c where c.journalNum = ? and billsType = ?";
            CashierBills cashierBills  = (CashierBills)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{wxPayDto.getOrderId(),"项目收入预算单"});
            String sql = "select t.sub_mchid from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ?";
            String sqlcost = "select sum(to_number(g.quantity)*to_number(g.costmoney)),sum(money)-sum(to_number(g.quantity)*to_number(g.costmoney)) from Dtgoodsbills g where g.goodsname !='VIP客户' and g.cashierbillsid=?";

            Object obj = baseBeanDao.getObjectBySqlAndParams(sql,new Object[]{cashierBills.getCompanyID()});

            if(obj==null||obj.toString().equals("")){
                  return null;
            }
            wxPayDto.setSub_mchid(obj.toString());
            wxPayDto.setSp_appid("wxf17107b0e9021507");

            Object objs =  baseBeanDao.getObjectBySqlAndParams(sqlcost,new Object[]{cashierBills.getCashierBillsID()});
            Object[] objf = (Object[])objs;
            wxPayDto.setTotalFee(WeChatUtils.getMoney(objf[0].toString()));
            wxPayDto.setRefundfee(WeChatUtils.getMoney(objf[0].toString()));
            wxPayDtoList.add(wxPayDto);


            PayCashierBill payCashierBill = (PayCashierBill)baseBeanDao.getBeanByHqlAndParams("from PayCashierBill p where p.dtype is not null and p.payJournalNum = (select pp.payJournalNum from PayCashierBill pp where pp.oriJournalNum = ?)",new Object[]{wxPayDto.getOrderId()});

           if(objf[1].toString()!=null&&!objf[1].toString().equals("")&&!"0".equals(objf[1].toString())) {
               WxPayDto wxPayDtoy = new WxPayDto();
               wxPayDtoy.setOrderId(payCashierBill.getOriJournalNum());
               wxPayDtoy.setRefundno("yj" + wxPayDto.getRefundno());
               wxPayDtoy.setSub_mchid("1604771870");
               wxPayDtoy.setSp_appid(wxPayDtoy.getSp_appid());
               wxPayDtoy.setTotalFee(payCashierBill.getDtype());
               wxPayDtoy.setRefundfee(WeChatUtils.getMoney(objf[1].toString()));
               wxPayDtoy.setSp_appid("wxf17107b0e9021507");
               wxPayDtoList.add(wxPayDtoy);
           }





          return wxPayDtoList;
    }

    /**
     *
     * 分账准备数据
     * @param companyID
     * @return
     */
    public  WxPayDto  getProfitInfo(String journalNum,String companyID){
        WxPayDto  wxPayDto = new WxPayDto();
        PayCashierBill payCashierBill = (PayCashierBill)baseBeanDao.getBeanByHqlAndParams("from PayCashierBill where oriJournalNum = ?",new Object[]{journalNum});
        wxPayDto.setTransaction_id(payCashierBill.getTrade_no());

        wxPayDto.setOut_order_no("fz"+journalNum);

//        String sql = "select t.sub_mchid from dt_sft_applyresult t ,dt_sft_applyparam p,Dtcontactcompany c,dt_ccom_com m where t.out_request_no= p.out_request_no and p.applyid = c.applyid and c.ccompanyid = m.ccompany_id and m.compnay_id= ?";
//        Object obj = baseBeanDao.getObjectBySqlAndParams(sql,new Object[]{companyID});
        String hql = "from SubOrders where out_trade_no = ?";
        SubOrders obj = (SubOrders)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{journalNum});

        wxPayDto.setSub_mchid(obj.getSub_mchid());
        wxPayDto.setDescription("分账完结");
        return wxPayDto;

    }


    /**
     *
     * 修改新表状态
     * @param companyID
     * @param fkStatus
     * @param fkdate
     * @param cashierBillsID
     */
        public void updateCashState(String companyID,String fkStatus,Date fkdate,String cashierBillsID){

            try {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
                String year = dateFormat.format(fkdate);


                TableRalate tableRalate = goldOrderService.getTableName(companyID, "CashierBills",year);
                if (tableRalate != null) {
                    String tableName = tableRalate.getTablename();
                    String sql = "update " + tableName + " set fkStatus= ?  where cashierBillsID = ?";

                    baseBeanDao.saveBeansListAndexecuteSqlsByParams(null, new String[]{sql}, new Object[]{fkStatus,cashierBillsID});
                }
            }catch (Exception e){
                logger.error("操作异常", e);;
            }
        }



}
