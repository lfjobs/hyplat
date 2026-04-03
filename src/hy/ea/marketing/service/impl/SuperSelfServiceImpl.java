package hy.ea.marketing.service.impl;

import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.MqttService;
import hy.ea.bo.CAccount;
import hy.ea.bo.CRole;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.DepotManage;
import hy.ea.bo.company.GoodsBarcode;
import hy.ea.bo.company.ScaleGoods;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.PayBackupBill;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.company.service.DepotManageService;
import hy.ea.marketing.service.SuperSelfSerivce;
import hy.ea.marketing.service.SupermarketSerivce;
import hy.ea.util.Constant;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.print.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * 超市自助收银
 *
 * @author [mz]
 * @version [1.0, 2017-03-17]
 * @see
 */
@Service
@Transactional
public class SuperSelfServiceImpl implements SuperSelfSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private DepotManageService depotManageService;

    private Logger logger = Logger.getLogger(SuperSelfServiceImpl.class);
    public static boolean bc = false;
    public static String r = "";
    private static float mixNum = 0;

    @Resource
    private SupermarketSerivce supermarketSerivce;

//    @Resource
//    public MqttService mqttService;

    @Resource
    private WfjJifenService wfjJifenService;


    @Override
    public int login(String companyIdentifier, CAccount account) {

        // 查询组织机构是否存在且状态是否正常
        Company cc = (Company) baseBeanDao.getBeanByHqlAndParams("from Company where companyIdentifier = ?", new Object[]{companyIdentifier});
        if (cc == null) {
            return 1;  //企业不存在
        } else if ("01".equals(cc.getCompanyStatus())) {
            return 2;//该企业已停用
        }
        String password = Utilities.MD5(account.getAccountPassword());
        //查询登陆名是否存在
        CAccount tempAccount = (CAccount) baseBeanDao.getBeanByHqlAndParams("from CAccount where accountEmail = ? and companyID = ? and accountPassword = ?", new Object[]{account.getAccountEmail(), cc.getCompanyID(), password});
        if (tempAccount == null) {
            return 3;//用户名或者密码不正确
        } else if (!"00".equals(tempAccount.getAccountStatus())) {
            return 4;//账号已停用
        }
        //查询用户的角色看是否是超市操作员
        CRole role = (CRole) baseBeanDao.getBeanByHqlAndParams("from CRole where roleID = ?", new Object[]{tempAccount.getRoleID()});
        if (role != null && !role.getRoleName().equals("超市操作员")) {
            return 5;//您没有权限登陆超市自助收银机
        }
        //如果登录账号存在则设置返回的staff
        account.setCompanyID(tempAccount.getCompanyID());
        account.setAccountEmail(tempAccount.getAccountEmail());
        account.setAccountPassword(tempAccount.getAccountPassword());
        account.setStaffID(tempAccount.getStaffID());
        account.setAccountID(tempAccount.getAccountID());
        //如果正常登陆 生成loginGuid
        String uuid = UUID.randomUUID().toString();
        tempAccount.setLoginGuid(uuid);
        account.setLoginGuid(uuid);

        baseBeanDao.update(tempAccount);


        return 0;

    }

    @Override
    public Map<String, Object> searchGoods(String comID, String barCode, int lxType) {
        Map<String, Object> map = new HashMap<String, Object>();
        String money = "";
        String bbcode = barCode;
        if (barCode != null && barCode.length() > 3) {
            String two = barCode.substring(0, 2);

            if ("21".equals(two)) {
                money = String.format("%.2f", (Float.parseFloat(bbcode.substring(7, 12)) * 0.01f));
                bbcode = bbcode.substring(2, 7);
            }
        }
        ProductPackaging pp = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams("from ProductPackaging where companyID = ? and barCode = ? and showweixin = ? and delStatus = ?", new Object[]{comID, bbcode, "01", "00"});
        String goodsName = "";
        String quantity = "";
        if (pp == null) {
            String hqlgm = "from GoodsBarcode where barCode = ? and status = ? and companyID = ?";
            GoodsBarcode gb = (GoodsBarcode) baseBeanDao.getBeanByHqlAndParams(hqlgm, new Object[]{bbcode, "01", comID});
            if (gb != null) {
                pp = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams("from ProductPackaging where goodsID = ? and showweixin = ? and delStatus = ?", new Object[]{gb.getGoodsid(), "01", "00"});
                if (pp == null) {
                    map.put("result", "1");
                    return map;
                }
                goodsName = "#" + gb.getSpcation() + pp.getGoodsName();
                quantity = gb.getQuantity();
            } else {
                map.put("result", "1");
                return map;
            }
        } else {
            goodsName = pp.getGoodsName();
        }
        if (pp != null) {
            if ("0".equals(pp.getIsScale())) {
                try {
                    String scalehql = "from ScaleGoods where goodsID = ? and companyID = ?";
                    ScaleGoods scaleGoods = (ScaleGoods) baseBeanDao.getBeanByHqlAndParams(scalehql, new Object[]{pp.getGoodsID(), comID});
                    if (scaleGoods != null) {
                        map.put("unit", scaleGoods.getUnitOfMeasureCode());
                    }
                } catch (Exception el) {
                    el.printStackTrace();
                }
            }
        }


        map.put("quantity", quantity);
        map.put("goodsname", goodsName);
        map.put("ppID", pp.getPpID());
        map.put("companyID", pp.getCompanyID());
        map.put("money", money);


        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();

        sql.append("select case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append(" case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append(" case when tpa.type is not null then to_char(tpap.factory) when pa.type is not null then to_char(pap.factory) else ps.ef_price end costmoney,");
        sql.append(" case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID");
        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");


        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append("where p.ppid =? ");

        Object[] objects = new Object[]{currentTime, currentTime, currentTime, currentTime, pp.getPpID()};
        //如果类型为1，sql语句变为查询批发商城信息
        if (lxType == 1) {//查询类型，0：其他入口进入1：批发商城进入
            getPfscShoppingCarSearchGoodsSql(sql);
            objects = new Object[]{pp.getPpID()};
        }
        Object obj = baseBeanDao.getObjectBySqlAndParams(sql.toString(), objects);
        Object[] objs = (Object[]) obj;
        map.put("price", objs[0].toString());
        map.put("pritype", objs[1].toString());
        map.put("costmoney", objs[2].toString());
        map.put("activityID", objs[3].toString());
        map.put("result", "0");


        return map;

    }

    /**
     * 扫码添加商品
     *
     * @param sql
     */
    private void getPfscShoppingCarSearchGoodsSql(StringBuilder sql) {
        sql.delete(0, sql.length());
        sql.append("select ROUND(dpw.wholesale * (1 + nvl(sz.total_pct, 0) / 100), 2) price,");
        sql.append(" '1' pricetype ,");
        sql.append(" to_number(dpw.wholesale) costmoney,");
        sql.append(" to_char(dpw.wholesaleId) activityID ");
        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
        sql.append(" left join dt_pro_wholesale dpw on p.ppid = dpw.ppid and dpw.state = '00' ");
        sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append("where p.ppid =? ");
    }

    /**
     * 生成订单号加入购物清单
     *
     * @param selfCartmap
     * @return journalnum
     */
    public Map<String, Object> joinSelfCart(Map<String, SelfCart> selfCartmap, String comID, String posNum, String relateID) {
        Map<String, Object> map = new HashMap<String, Object>();
        String journalNum = serverService.getBillID(comID);
        map.put("journalNum", journalNum);
        float totalMoney = 0;
        float totalNum = 0;
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        for (SelfCart selfCart : selfCartmap.values()) {
            selfCart.setCartId(serverService.getServerID("scid"));
            selfCart.setPosNum(posNum);
            selfCart.setJournalNum(journalNum);
            selfCart.setPayjournalNum(journalNum);
            selfCart.setComID(comID);
            selfCart.setRelateID(relateID);

            totalNum += Float.parseFloat(selfCart.getItemNum());
            totalMoney += Float.parseFloat(selfCart.getItemNum()) * Float.parseFloat(selfCart.getPrice());
            baseBeanList.add(selfCart);

        }
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);
        map.put("totalMoney", totalMoney);
        return map;
    }

    /**
     * 返回订单号
     *
     * @return
     */
    public Map<String, Object> joinSelfCartClose(String hgcode) {
        Map<String, Object> map = new HashMap<String, Object>();
        PosDevice posDevice = (PosDevice) baseBeanDao.getBeanByHqlAndParams("from PosDevice where hgcode = ?", new Object[]{hgcode});

        if (posDevice != null) {
            String comID = posDevice.getComID();
            String ccompanyID = posDevice.getAccessCcomID();
            String posNum = posDevice.getPosNum();

            String journalNum = serverService.getBillID(comID);

            Float totalMoney = 0f;
            Float totalNum = 0f;
            List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
            List<BaseBean> list = supermarketSerivce.getSqCartList(posNum, ccompanyID, 0, "3");
            for (int i = 0; i < list.size(); i++) {
                Object sqSelfCart = (Object) list.get(i);
                Object[] obj = (Object[]) sqSelfCart;
                SelfCart selfCart = new SelfCart();
                selfCart.setCartId(serverService.getServerID("scid"));
                selfCart.setPosNum(posNum);
                selfCart.setJournalNum(journalNum);
                selfCart.setPayjournalNum(journalNum);
                selfCart.setComID(comID);
                selfCart.setRelateID("");
                selfCart.setItemNum(obj[13].toString());
                selfCart.setPid(obj[0].toString());
                selfCart.setPrice(obj[2].toString());
                selfCart.setPritype(obj[10].toString());
                selfCart.setActivityID(obj[12].toString());
                selfCart.setCostmoney(obj[11].toString());
                selfCart.setCompanyId(obj[5].toString());
                selfCart.setGoodsName(obj[1].toString());
                selfCart.setBarCode(obj[4].toString());
                selfCart.setStardard(obj[6].toString());
                selfCart.setRelateID(obj[15] == null ? "" : obj[15].toString());
                selfCart.setVariableID(obj[16] == null ? "" : obj[16].toString());
                selfCart.setSgrId(obj[14] == null ? "" : obj[14].toString());
                selfCart.setDeptId(obj[17] == null ? "" : obj[17].toString());
                selfCart.setDeptName(obj[18] == null ? "" : obj[18].toString());

                totalNum += Float.parseFloat(selfCart.getItemNum());
                totalMoney += Float.parseFloat(selfCart.getItemNum()) * Float.parseFloat(selfCart.getPrice());
                baseBeanList.add(selfCart);

            }
            if (list.size() == 0) {
                map.put("wgw", "true");
            }
            map.put("journalNum", journalNum);
            DecimalFormat df = new DecimalFormat("#.##"); // 设置格式为两位小数
            String formattedNumber = df.format(totalMoney); // 格式化数字为字符串
            map.put("totalMoney", formattedNumber);
            map.put("totalNum", totalNum);
            map.put("comID", comID);
            map.put("ccompanyID", ccompanyID);

            baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);

        }
        return map;
    }

    /**
     * 单产品加入超市购物车
     *
     * @param ppid
     * @param comID
     * @param posNum
     * @return
     */
    public Map<String, Object> joinDanSelfCart(String ppid, String stardard, String price, String itemNum, String comID, String posNum) {
        String hql = "from ProductPackaging where ppID = ?";
        ProductPackaging pp = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{ppid});

        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());

        StringBuilder sql = new StringBuilder();

        sql.append(" select case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append(" case when tpa.type is not null then to_char(tpap.factory) when pa.type is not null then to_char(pap.factory) else ps.ef_price end costmoney,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)   else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append(" case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID");

        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");

        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");


        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append("where p.ppid=? ");

        Object obj = baseBeanService.getObjectBySqlAndParams(sql.toString(), new Object[]{currentTime, currentTime, currentTime, currentTime, ppid});

        Object[] objs = (Object[]) obj;
        Map<String, Object> map = new HashMap<String, Object>();
        String journalNum = serverService.getBillID(comID);
        map.put("jum", journalNum);
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        SelfCart selfCart = new SelfCart();
        selfCart.setCartId(serverService.getServerID("scid"));
        selfCart.setPosNum(posNum);
        selfCart.setJournalNum(journalNum);
        selfCart.setPayjournalNum(journalNum);
        selfCart.setStardard(stardard);
        selfCart.setSendNum(itemNum);
        selfCart.setBarCode(pp.getBarCode());
        selfCart.setComID(comID);
        selfCart.setCompanyId(pp.getCompanyID());
        selfCart.setPrice(objs[2].toString());
        selfCart.setItemNum(itemNum);
        selfCart.setGoodsName(pp.getGoodsName());
        selfCart.setPid(ppid);
        selfCart.setPritype(objs[0].toString());
        selfCart.setCostmoney(objs[1].toString());
        selfCart.setActivityID(objs[3].toString());
        baseBeanList.add(selfCart);


        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);

        return map;
    }

    /**
     * 更新数量
     *
     * @param ppid
     * @param itemNum
     */
    public void updateItemNum(String ppid, String itemNum, String journalNum) {
        String hql = "from SelfCart where journalNum = ? and pid = ?";
        SelfCart s = (SelfCart) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{journalNum, ppid});
        s.setItemNum(itemNum);
        baseBeanDao.update(s);
    }


    /**
     * 多公司多商品加入超市购物车
     *
     * @param posNum
     * @return
     */
    public Map<String, Object> joinMultiSelfCart(String posNum) {
        List<Object> params = new ArrayList<Object>();
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        String payjournalNum = "";

        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());

        StringBuilder sql = new StringBuilder();


        sql.append("select p.goodsName,p.ppID,case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)   else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append("c.stardard,c.itemNum,p.barCode,p.companyID,c.sendNum,");

        sql.append(" case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append(" case when tpa.type is not null then to_char(tpap.factory) when pa.type is not null then to_char(pap.factory) else ps.ef_price end costmoney,");
        sql.append(" case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID");

        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
        sql.append("inner join DT_SqSelfCart c on p.ppid = c.pid ");

        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");


        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append("where c.posNum=? and p.showweixin=?");

        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(currentTime);
        params.add(posNum);
        params.add("01");

        List<Object[]> ppklist = baseBeanService.getListBeanBySqlAndParams(sql.toString(), params.toArray());
        SelfCart selfCart = null;
        int i = 0;

        List<String> comlist = new ArrayList<String>();

        for (int b = 0; b < ppklist.size(); b++) {
            Object[] bbs = (Object[]) ppklist.get(b);
            String companyID = bbs[6].toString();
            if (!comlist.contains(companyID)) {
                comlist.add(companyID);
            }
        }
        for (int c = 0; c < comlist.size(); c++) {
            String journalNum = serverService.getBillID(comlist.get(c).toString());
            try {
                Thread.sleep(500);
            } catch (InterruptedException el) {
                el.printStackTrace();
            }
            if (c == 0) {
                payjournalNum = journalNum;

            }
            for (Object[] obj : ppklist) {
                if (obj[6].toString().equals(comlist.get(c).toString())) {
                    selfCart = new SelfCart();
                    selfCart.setCartId(serverService.getServerID("scid"));
                    selfCart.setPosNum(posNum);
                    selfCart.setJournalNum(journalNum);
                    selfCart.setStardard(obj[3] != null ? obj[3].toString() : "");
                    selfCart.setSendNum(obj[7] != null ? obj[7].toString() : "");
                    selfCart.setBarCode(obj[5] != null ? obj[5].toString() : "");
                    selfCart.setComID(obj[6].toString());
                    selfCart.setCompanyId(obj[6].toString());
                    selfCart.setPrice(obj[2].toString());
                    selfCart.setItemNum(obj[4] != null ? obj[4].toString() : "");
                    selfCart.setGoodsName(obj[0].toString());
                    selfCart.setPid(obj[1].toString());
                    selfCart.setPayjournalNum(payjournalNum);
                    selfCart.setPritype(obj[8] != null ? obj[8].toString() : "");
                    selfCart.setCostmoney(obj[9] != null ? obj[9].toString() : "");
                    selfCart.setActivityID(obj[10] != null ? obj[10].toString() : "");
                    baseBeanList.add(selfCart);
                    i++;
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("jum", payjournalNum);
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList, null, null);

        return map;
    }


    /**
     * 查询购买人的积分
     *
     * @param sccid
     * @return
     */
    public String searchPoint(String sccid) {
        BonusPoints bonusPoints = (BonusPoints) baseBeanService.getBeanByHqlAndParams(" from BonusPoints where sccid = ?", new Object[]{sccid});
        String jifenshu = "0";
        if (bonusPoints != null && bonusPoints.getBonusPointScore() != null) {
            BigDecimal bp = new BigDecimal(bonusPoints.getBonusPointScore());
            jifenshu = bp.toString();
//            if (bonusPoints.getBonusPointScore().contains(".")) {
//                jifenshu = bonusPoints.getBonusPointScore().substring(0, bonusPoints.getBonusPointScore().indexOf("."));
//            } else {
//                jifenshu = bonusPoints.getBonusPointScore();
//            }
        }
        return jifenshu;
    }

    /**
     * 查询购买人的积分
     *
     * @param sccid
     * @return
     */
    public String searchJINBIN(String sccid) {

        WfjJifen wfjJf = (WfjJifen) baseBeanService.getBeanByHqlAndParams("from WfjJifen where sccid=?", new Object[]{sccid});
        String jb = "0";
        if (wfjJf != null && wfjJf.getWfjJifenScore() != null) {
            BigDecimal bp = new BigDecimal(wfjJf.getWfjJifenScore());
            jb = bp.toString();
//            if (bonusPoints.getBonusPointScore().contains(".")) {
//                jifenshu = bonusPoints.getBonusPointScore().substring(0, bonusPoints.getBonusPointScore().indexOf("."));
//            } else {
//                jifenshu = bonusPoints.getBonusPointScore();
//            }
        }
        return jb;

    }

    /**
     * 生成支付前信息
     *
     * @param sccid
     * @param journalNum
     * @return
     */
    public void genPayBackupBill(String sccid, String journalNum, String addressID) {
        String hql = "from PayBackupBill where journalNum = ?";
        PayBackupBill cc = (PayBackupBill) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{journalNum});
        if (cc == null) {
            PayBackupBill payBackupBill = new PayBackupBill();
            payBackupBill.setPbbID(serverService.getServerID("pbbID"));
            payBackupBill.setJournalNum(journalNum);
            payBackupBill.setAttach("selfpay");
            payBackupBill.setSccid(sccid);
            payBackupBill.setAddressID(addressID);
            baseBeanService.save(payBackupBill);
        } else {
            cc.setSccid(sccid);
            cc.setAddressID(addressID);
            baseBeanService.update(cc);
        }
    }


    /**
     * 取消订单
     *
     * @param journalNum
     * @return
     */
    public String cancelOrder(String journalNum) {
        ///清空selfCart
        String hql = "delete from SelfCart c where journalNum = ?";
        Object[] params = new Object[]{journalNum};
        List<Object[]> parmsList = new ArrayList<Object[]>();
        parmsList.add(params);


        try {
            baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, parmsList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 修改商品
     *
     * @param journalNum
     * @return
     */
    public List<BaseBean> viewOrderDetail(String journalNum) {
        String hql = "from SelfCart where journalNum = ? or (payjournalNum=? and journalNum!=payjournalNum)";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{journalNum, journalNum});


        return list;
    }

    /**
     *
     * 判断货柜问题
     *
     * @param journalNum
     * @return
     */
    public List<BaseBean> viewGoodsBills(String journalNum) {
        String hql = "from CashierBills where journalNum = ? and goodsName = ?";
        CashierBills cc = (CashierBills) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{journalNum, "智能货柜"});
        List<BaseBean> list = new ArrayList<BaseBean>();

        if (cc != null) {
            String hqls = "from GoodsBills where cashibillsID = ?";
            list = baseBeanDao.getListBeanByHqlAndParams(hqls, new Object[]{journalNum, cc.getCashierBillsID()});
        }


        return list;
    }

    /**
     * 查询支付结果
     *
     * @param journalNum
     * @return
     */
    public boolean searchPayResult(String journalNum) {
        String hql = "from CashierBills where journalNum = ?";
        CashierBills cashierBills = (CashierBills) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{journalNum});
        if (cashierBills != null) {
            if ("01".equals(cashierBills.getFkStatus())) {//"金币计时".equals(cashierBills.getProjectName())&&
                return false;
            } else {
                //已支付
                return true;
            }
        }
        //未支付
        return false;
    }


    /**
     * 查询当前超市购物袋
     *
     * @param comID
     * @return
     */
    public Map<String, Object> searchShoppBag(String comID, int lxType) {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();

        sql.append("select ps.ppname,ps.ppid,");
        sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append("p.companyid,p.barCode,");
        sql.append("case when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append("case when tpa.type is not null then to_number(tpap.factory) when pa.type is not null then to_number(pap.factory) else to_number(ps.ef_price) end costmoney,");
        sql.append("case when tpa.type is not null then to_char(tpap.actPriceId) when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID");

        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid");
        //特价
        sql.append(" left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
        sql.append(" left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
        sql.append(" and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");

        //促销
        sql.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
        sql.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");
        sql.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");
        //消费红包
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        sql.append(" where ps.com_id= ? and (p.goodsName = ? or p.goodsName = ?) order by p.goodsName");


        //List<Object> baglist = baseBeanDao.getListObjectBySqlAndParams(sql.toString(), new Object[]{currentTime, currentTime, currentTime, currentTime, comID, "小购物袋", "大购物袋"});


        Object[] objects = new Object[]{currentTime, currentTime, currentTime, currentTime, comID, "小购物袋", "大购物袋"};
        //如果类型为1，sql语句变为查询批发商城信息
        if (lxType == 1) {//查询类型，0：其他入口进入1：批发商城进入
            getPfscShoppingCarGwdSql(sql);
            objects = new Object[]{comID, "小购物袋", "大购物袋"};
        }
        List<BaseBean> baglist = baseBeanDao.getListBeanBySqlAndParams(sql.toString(), objects);
        map.put("baglist", baglist);
        return map;
    }

    /**
     * 拼接批发商城购物袋sql语句
     *
     * @return
     */
    private void getPfscShoppingCarGwdSql(StringBuilder sql) {
        sql.delete(0, sql.length());
        sql.append("select ps.ppname,ps.ppid,");
        sql.append(" ROUND(dpw.wholesale * (1 + nvl(sz.total_pct, 0) / 100), 2) price, ");
        sql.append("p.companyid,p.barCode,");
        sql.append(" '1' pricetype, ");
        sql.append(" to_number(dpw.wholesale) costmoney, ");
        sql.append(" to_char(dpw.wholesaleId) activityID ");
        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid");
        sql.append(" left join dt_pro_wholesale dpw on p.ppid = dpw.ppid and dpw.state = '00' ");
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
        sql.append(" where ps.com_id= ? and (p.goodsName = ? or p.goodsName = ?) order by p.goodsName");
    }

    /**
     * 打印小票
     *
     * @param journalNum
     * @return
     */
    public String printTicket(final String journalNum, String staffID) {


        final List<BaseBean> cartlist = baseBeanDao.getListBeanByHqlAndParams("from SelfCart c where c.journalNum = ?", new Object[]{journalNum});
        CashierBills bb = (CashierBills) baseBeanDao.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});
        final Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});
        final String zf = "00".equals(bb.getWfStatus4()) ? "微信支付" : "01".equals(bb.getWfStatus4()) ? "支付宝" : "积分支付";
        final String staffcode = staff != null ? staff.getStaffCode() : "";
        final String printtime = Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss");
        BigDecimal totalMoney = new BigDecimal(0);
        float totalNum = 0;
        String pos = "";
        for (int i = 0; i < cartlist.size(); i++) {
            SelfCart selfCart = (SelfCart) cartlist.get(i);
            totalNum = totalNum + Float.parseFloat(selfCart.getItemNum());
            totalMoney = totalMoney.add(new BigDecimal(selfCart.getItemNum()).multiply(new BigDecimal(selfCart.getPrice())));
            if (i == 0) {
                pos = selfCart.getPosNum();
            }
        }

        final String ftotalMoney = totalMoney.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        final String ftotalNum = totalNum + "";
        System.out.println("00000000000:" + ftotalNum);
        if (PrinterJob.lookupPrintServices().length > 0) {
            System.out.println("--------------:" + PrinterJob.lookupPrintServices().length);
            /*
        打印格式
       */
            PageFormat pageFormat = new PageFormat();
            //设置打印起点从左上角开始，从左到右，从上到下打印
            pageFormat.setOrientation(PageFormat.PORTRAIT);
            /*
        打印页面格式设置
       */
            Paper paper = new Paper();
            //设置打印宽度（固定，和具体的打印机有关）和高度（跟实际打印内容的多少有关）
            paper.setSize(200, 460);
            //设置打印区域 打印起点坐标、打印的宽度和高度
            paper.setImageableArea(0, 0, 180, 460);
            pageFormat.setPaper(paper);
            //创建打印文档
            Book book = new Book();
            final String finalPos = pos;
            book.append(new Printable() {
                            @Override
                            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                                if (pageIndex > 0) {
                                    return NO_SUCH_PAGE;
                                }
                                Graphics2D graphics2D = (Graphics2D) graphics;
                                Font font = new Font("宋体", Font.PLAIN, 7);
                                graphics2D.setFont(font);

                                int yIndex = 5;
                                int lineHeight = 10;
                                int lineWidth = 120;
                                Color defaultColor = graphics2D.getColor();
                                Color grey = new Color(145, 145, 145);
                                lineWidth = 129;
                                lineHeight = 8;
                                graphics2D.setFont(new Font("宋体", Font.BOLD, 9));
                                graphics2D.setColor(defaultColor);
                                yIndex = drawString(graphics2D, "微分金商城", 45, yIndex + 2, lineWidth, 5);

                                graphics2D.setFont(new Font("宋体", Font.PLAIN, 7));
                                graphics2D.setColor(defaultColor);
                                yIndex = drawString(graphics2D, "http://www.impf2010.com", 30, yIndex + lineHeight + 5, lineWidth, 12);
                                graphics2D.setFont(new Font("宋体", Font.PLAIN, 8));
                                graphics2D.setColor(defaultColor);
                                yIndex = drawString(graphics2D, "欢迎下次光临", 45, yIndex + lineHeight + 5, lineWidth, 12);


                                graphics2D.setFont(new Font("宋体", Font.PLAIN, 7));
                                graphics2D.setColor(grey);

                                yIndex = drawString(graphics2D, "操作员：" + staffcode, 5, yIndex + lineHeight + 2, lineWidth, lineHeight);
                                yIndex = drawString(graphics2D, "pos：" + finalPos, 5 + lineWidth / 2, yIndex, lineWidth, lineHeight);
                                yIndex = drawString(graphics2D, "订单号：" + journalNum, 5, yIndex + lineHeight + 2, lineWidth, lineHeight);
                                yIndex = drawString(graphics2D, "打印时间：" + printtime, 5, yIndex + lineHeight + 2, lineWidth, lineHeight);
                                yIndex = yIndex + 2;
                                graphics2D.drawLine(5, yIndex, 5 + lineWidth, yIndex);

                                yIndex = drawString(graphics2D, "品名", 5, yIndex + lineHeight * 2 - 5, lineWidth, lineHeight);
                                yIndex = drawString(graphics2D, "数量", (lineWidth / 10) * 6 + 8, yIndex, lineWidth, lineHeight);
                                yIndex = drawString(graphics2D, "单价", (lineWidth / 10) * 9, yIndex, lineWidth, lineHeight);

                                for (int i = 0; i < cartlist.size(); i++) {
                                    SelfCart selfCart = (SelfCart) cartlist.get(i);
                                    graphics2D.setFont(new Font("宋体", Font.PLAIN, 7));
                                    yIndex = drawString(graphics2D, selfCart.getGoodsName(), 5, yIndex + 15, (lineWidth / 10) * 6, 10);
                                    yIndex = drawString(graphics2D, selfCart.getItemNum().toString(), (lineWidth / 10) * 7, yIndex, lineWidth, lineHeight);
                                    yIndex = drawString(graphics2D, String.format("%.2f", Float.parseFloat(selfCart.getPrice())), (lineWidth / 10) * 9, yIndex, lineWidth + 30, lineHeight);
                                }
                                graphics2D.setColor(grey);
                                yIndex = yIndex + 4;

                                graphics2D.drawLine(5, yIndex, 5 + lineWidth, yIndex);
                                graphics2D.setFont(new Font("宋体", Font.PLAIN, 8));
                                graphics2D.setColor(defaultColor);
                                yIndex = drawString(graphics2D, "应付金额：", 5, yIndex + lineHeight * 2, lineWidth, lineHeight);
                                yIndex = drawString(graphics2D, "(共" + ftotalNum + "件)" + ftotalMoney, (lineWidth / 10) * 6, yIndex, lineWidth + 30, lineHeight);
                                yIndex = drawString(graphics2D, "支付方式：", 5, yIndex + lineHeight + 5, lineWidth, lineHeight);
                                yIndex = drawString(graphics2D, zf, (lineWidth / 10) * 8, yIndex, lineWidth, lineHeight);

                                graphics2D.setFont(new Font("宋体", Font.PLAIN, 6));
                                graphics2D.setColor(grey);
                                yIndex = drawString(graphics2D, "电话：010-64167113", 5, yIndex + lineHeight, lineWidth, lineHeight);
                                yIndex = drawString(graphics2D, "地址：北京东城区东门外大街42号宇飞大厦801", 5, yIndex + lineHeight, lineWidth, lineHeight);
                                yIndex = yIndex + 20;
                                graphics2D.drawLine(0, yIndex, 140, yIndex);
                                return PAGE_EXISTS;
                            }
                        }
                    , pageFormat);
            //获取默认打印机
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            System.out.println("--------------:" + printerJob.getJobName());
            printerJob.setPageable(book);
            try {
                printerJob.print();
            } catch (PrinterException e) {
                e.printStackTrace();
                System.out.println("打印异常");
            }

//
//            ///清空selfCart
//            String hqldelete = "delete from SelfCart c where journalNum = ?";
//            Object[] params = new Object[]{journalNum};
//            List<Object[]> parmsList = new ArrayList<Object[]>();
//            parmsList.add(params);
//

            //   baseBeanService.executeHqlsByParamsList(null, new String[]{hqldelete}, parmsList);
        } else {
            System.out.println("没法发现打印机服务");
        }

        return null;


    }

    /**
     * 字符串输出
     *
     * @param graphics2D 画笔
     * @param text       打印文本
     * @param x          打印起点 x 坐标
     * @param y          打印起点 y 坐标
     * @param lineWidth  行宽
     * @param lineHeight 行高
     * @return 返回终点 y 坐标
     */

    private int drawString(Graphics2D graphics2D, String text, int x, int y, int lineWidth, int lineHeight) {
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        if (fontMetrics.stringWidth(text) < lineWidth) {
            graphics2D.drawString(text, x, y);
            return y;
        } else {
            char[] chars = text.toCharArray();
            int charsWidth = 0;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < chars.length; i++) {
                if ((charsWidth + fontMetrics.charWidth(chars[i])) > lineWidth) {
                    graphics2D.drawString(sb.toString(), x, y);
                    sb.setLength(0);
                    y = y + lineHeight;
                    charsWidth = fontMetrics.charWidth(chars[i]);
                    sb.append(chars[i]);
                } else {
                    charsWidth = charsWidth + fontMetrics.charWidth(chars[i]);
                    sb.append(chars[i]);
                }
            }
            if (sb.length() > 0) {
                graphics2D.drawString(sb.toString(), x, y);
                y = y + lineHeight;
            }
            return y - lineHeight;
        }
    }

    /**
     * 根据公司查询公司微分金账号
     *
     * @param companyID
     * @return
     */
    public String searchSccidByCompany(String companyID) {
        String hql = "from TEshopCusCom where companyId = ?";
        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{companyID});
        String sccid = "";
        if (tc != null) {
            sccid = tc.getSccId();
        }

        return sccid;
    }

    /**
     * 根据StaffID 查用户密码
     *
     * @param staffID
     * @return
     */
    public Map<String, Object> searchPwByStaff(String staffID, String comID, String telphone) {
        Map<String, Object> map1 = new HashMap<String, Object>();

        List<String> sbpwd = new ArrayList<>();

        if (staffID != null && !staffID.equals("")) {
            String hql = "from TEshopCusCom t where t.sccId = (select f.sccid from Staff f where f.staffID = ?) ";
            TEshopCusCom tt = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID});
            if (tt == null) {
                sbpwd.add("1");//操作员账户没有绑定微分金账户
            } else {
                String hqlc = "from TEshopCustomer where staffid = ?";
                TEshopCustomer tcr = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams(hqlc, new Object[]{tt.getStaffid()});
                if (tcr != null && tcr.getPaymentCode() != null && !tcr.getPaymentCode().equals("")) {
                    sbpwd.add(tcr.getPaymentCode());
                    map1.put("sccId", tt.getSccId());

                } else {
                    sbpwd.add("0");//没有设置交易密码

                }
            }
            map1.put("paymentCode", sbpwd);

        } else {
            StringBuilder sb = new StringBuilder();
            List<Object> param = new ArrayList<Object>();

            sb.append("select r.paymentcode,y.companyID  from t_Eshop_Customer r, t_Eshop_Cuscom  z, Dtcaccount y, dt_hr_staff f,dtCRole c");
            sb.append(" where y.staffid = f.staffid  and f.sccid = z.sccid  and z.account = r.account and c.roleid = y.roleid");
            if (comID != null && !comID.equals("")) {
                sb.append("  and y.companyid = ? ");
                param.add(comID);
            } else if (telphone != null && !telphone.equals("")) {
                sb.append("  and r.account = ? ");
                param.add(telphone);
            }
            sb.append("  and (c.rolename = ? or c.rolename = ?)");
            param.add("超市操作员");
            param.add("收银员");
            List<Object[]> list = baseBeanService.getListBeanBySqlAndParams(sb.toString(), param.toArray());

            for (int i = 0; i < list.size(); i++) {
                Object[] obj = (Object[]) list.get(i);
                if (obj[0] != null) {
                    sbpwd.add(obj[0].toString());
                }
                if (comID == null || comID.equals("")) {
                    map1.put("comID", obj[1].toString());
                }
            }
            //sbpwd.add("123456");
            map1.put("paymentCode", sbpwd);
        }

        return map1;
    }


    /**
     * 查询Sccid
     *
     * @param staffID
     * @return
     */
    public String searchSccidByStaff(String staffID) {

        String hql = "from TEshopCusCom where sccId = (select sccid from Staff where staffID = ?) ";
        TEshopCusCom tt = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID});
        if (tt == null) {
            return "1";//操作员账户没有绑定微分金账户
        }

        return tt.getSccId();

    }

    public void addMoreInfo(String journalNum, String ssprice, String zlprice) {
        String hql = "update SelfCart set ssprice = ?,zlprice = ? where journalNum = ?";
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{ssprice, zlprice, journalNum});
    }

    /**
     * 保存现金信息
     *
     * @param journalNum
     */
    public void saveCashMoreInfo(String journalNum, String oprID, String oprName) {
        List<BaseBean> listc = baseBeanService.getListBeanByHqlAndParams("from SelfCart where journalNum = ?", new Object[]{journalNum});

        if (listc != null && listc.size() > 0) {
            SelfCart selfCart = (SelfCart) listc.get(0);
            if (selfCart != null) {
                String hql = "update CashierBills set ssprice = ?,zlprice = ?,wfStatus4 = ?,inputid = ?,inputName = ? where journalNum = ?";
                baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{selfCart.getSsprice(), selfCart.getZlprice(), "06", oprID, oprName, journalNum});
            }
        }

    }

    public String getSccidBycomID(String companyID) {
        String hql = "from TEshopCusCom where companyId = ?";
        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{companyID});
        return tc != null ? tc.getSccId() : "";
    }

    /**
     * 核对购物卡是否正确以及余额是否充足
     *
     * @return
     */
    public Map<String, Object> checkScard(String sccId, String totalMoney, String journalNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
        String jifen = searchPoint(tc.getSccId());
        BigDecimal jb = new BigDecimal(jifen);
        BigDecimal yb = new BigDecimal("100");
        BigDecimal mb = new BigDecimal(totalMoney);
        String ttmoney = "";
        map.put("vipmoney", ttmoney);
        if (Float.parseFloat(tc.getCusType()) <= 5) {
            String hqls = "select ROUND(sum(to_number(price)*to_number(itemNum)),2) from SelfCart where payjournalNum = ?";
            Object obj = baseBeanDao.getObjectByHqlAndParams(hqls, new Object[]{journalNum});
            if (obj != null) {
                ttmoney = obj.toString();
                BigDecimal ttb = new BigDecimal(ttmoney);
                if (ttb.compareTo(mb) == -1) {
                    mb = ttb;
                    map.put("vipmoney", ttmoney);
                }
            }
        }
        if (jb.compareTo(mb.multiply(yb)) == -1) {
            map.put("result", "2");//积分不足
        } else {
            TEshopCustomer tcr = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{tc.getAccount()});

            map.put("result", "0");//积分充足
            map.put("sccId", tc.getSccId());//购物卡用户ID
            map.put("paymentCode", tcr.getPaymentCode());
        }


        return map;
    }

    /**
     * check用户地址
     *
     * @return
     */
    public StaffAddress getUserAddress(String sccId) {
        String staffaddhql = "from StaffAddress s where s.staffID=(select t.staffid from TEshopCusCom  t where t.sccId = ?) and  s.isDefault='是'";
        StaffAddress staffaddress = (StaffAddress) baseBeanService
                .getBeanByHqlAndParams(staffaddhql,
                        new String[]{sccId});

        return staffaddress;

    }

    /**
     * 添加修改地址
     *
     * @param sccId
     */
    public String addAddress(String sccId, StaffAddress staffAddress) {
        String addressID = "";

        if (staffAddress.getAddressID() != null && !staffAddress.getAddressID().equals("")) {
            String hql = "from StaffAddress where addressID = ?";
            StaffAddress staffAddressAdd = (StaffAddress) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffAddress.getAddressID()});
            staffAddressAdd.setAddressDetailed(staffAddress.getAddressDetailed());
            staffAddressAdd.setArea(staffAddress.getArea());
            staffAddressAdd.setConsignee(staffAddress.getConsignee());
            staffAddressAdd.setPhone(staffAddress.getPhone());
            baseBeanService.update(staffAddressAdd);
            addressID = staffAddress.getAddressID();

        } else {
            TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            staffAddress.setStaffID(tc.getStaffid());
            staffAddress.setCompanyID(tc.getCompanyId());
            staffAddress.setAddressID(serverService
                    .getServerID("StaffAddress"));
            staffAddress.setIsDefault("是");
            baseBeanService.save(staffAddress);
            addressID = staffAddress.getAddressID();
        }

        return addressID;
    }

    /**
     * 根据购物卡获取用户积分信息
     *
     * @param scard
     * @return
     */
    public Map<String, Object> getSccIdByscard(String scard) {
        Map<String, Object> map = new HashMap<String, Object>();
        String hql = "from GiftCards where cardNumber = ? and state = ?";
        GiftCards giftCards = (GiftCards) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{scard, "1"});
        if (giftCards == null) {
            map.put("result", "1");//不是购物卡
        } else {
            Object obj = baseBeanDao.getObjectBySqlAndParams("select t.sccId,f.staffName,f.staffId,t.account from T_ESHOP_CUSCOM t left join dt_hr_staff f on f.staffId = t.staffId where t.staffid = ? and t.acquiesce = ?", new Object[]{giftCards.getStaffID(), "01"});
            Object[] objs = (Object[]) obj;
            map.put("result", "0");//是购物卡
            map.put("sccId", objs[0].toString());//是购物卡
            map.put("staffName", objs[1].toString());//名字
            map.put("staffId", objs[2].toString());//名字
            map.put("account", objs[3].toString());//名字

        }
        return map;

    }

    /**
     * 根据支付凭证号查询购物车
     *
     * @param journalNum
     * @return
     */
    @Override
    public SelfCart getSelfCartByJum(String journalNum) {
        SelfCart SelfCart = (SelfCart) baseBeanDao.getBeanByHqlAndParams("from SelfCart where journalNum = ?", new Object[]{journalNum});
        return SelfCart;
    }

    /**
     * 根据订单判断是否是社区终端，目前只要是社区终端下单就不自动发货
     *
     * @return
     */
    public boolean isSqPos(String journalNum) {
        String hql = "from DtOrderBillAdd where oaBillJounum = ?";
        DtOrderBillAdd dt = (DtOrderBillAdd) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{journalNum});
        if (dt != null) {
            if (dt.getReceiveaddress() == null || dt.getReceiveaddress().equals("")) {
                return true;
            } else {
                return false;
            }
        }
//        String payhql = "from PayBackupBill where journalNum = ?";
//        PayBackupBill pp = (PayBackupBill)baseBeanDao.getBeanByHqlAndParams(payhql,new Object[]{journalNum});
//        if(pp!=null&&pp.getPrivateRoom()!=null&&!pp.getPrivateRoom().equals("")){
//            return true;//自动发货
//        }
//        if(pp!=null&&(pp.getNoviceAddress()==null||pp.getNoviceAddress().equals(""))){
//            return true;//自动发货
//        }
//        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams("from SelfCart where journalNum = ?",new Object[]{journalNum});
//        if(list.size()!=0) {
//           String hql = "from PosDevice cc where cc.posNum = (select max(f.posNum) from SelfCart f where f.journalNum=?)";
//
//           PosDevice pos = (PosDevice) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{journalNum});
//
//
//           if (pos != null && pos.getFhform() != null && !pos.getFhform().equals("2")) {
//               return false;//不自动发货
//           }
//       }else{
//           return false;//不自动发货
//       }

        return true;//自动发货
    }


    /**
     * 判断是显示一个数量还是2个数量
     *
     * @return
     */
    public String findFhForm(String posNum, String ccompanyID) {
        if (ccompanyID != null && !ccompanyID.equals("")) {
            ContactCompany ccompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams("from ContactCompany where ccompanyID = ?", new Object[]{ccompanyID});
            if (ccompany != null && ccompany.getIndustryType().indexOf("餐饮") != -1) {
                return "1";
            }
        }
        String hql = "from PosDevice cc where cc.posNum = ?";
        PosDevice pos = (PosDevice) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{posNum});


        if (pos != null && pos.getFhform() != null && !pos.getFhform().equals("2")) {
            return "2";  //不自动发货，显示2个数量：配送数量,已取数量
        }

        return "1";//自动发货，显示一个数量
    }


    @Override
    public TEshopCusCom getTEshopByCum(String cardNum) {
        GiftCards giftCards = (GiftCards) baseBeanDao.getBeanByHqlAndParams("from GiftCards where cardNumber = ?", new Object[]{cardNum});
        TEshopCusCom cusCom = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where staffid = ?", new Object[]{giftCards.getStaffID()});
        return cusCom;
    }

    @Override
    public void addSelfCart(String posNum, String cardNum, String journalNum, String totalNum, String totalMoney, String ppId, String proName, String comID, String companyId) {
        ProductPackaging packaging = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams("from ProductPackaging where companyID = ? and goodsName = ?", new Object[]{"company201009046vxdyzy4wg0000000025", "微分金积分"});
        SelfCart selfCart = new SelfCart();
        selfCart.setCartId(serverService.getServerID("scid"));
        selfCart.setPosNum(posNum);
        selfCart.setCardNum(cardNum);
        selfCart.setJournalNum(journalNum);
        selfCart.setPayjournalNum(journalNum);
        selfCart.setComID(comID);
        selfCart.setCompanyId(companyId);
        if (proName != null && !"".equals(proName)) {
            selfCart.setGoodsName(proName);
        } else {
            selfCart.setGoodsName(packaging != null ? packaging.getGoodsName() : "");
        }
        if (ppId != null && !"".equals(ppId)) {
            selfCart.setPid(ppId);
        } else {
            selfCart.setPid(packaging != null ? packaging.getPpID() : "");
        }
        selfCart.setItemNum(totalNum);
        selfCart.setPrice(totalMoney);
        baseBeanService.save(selfCart);
    }

    /**
     * 根据往来公司ID获取公司表
     */
    public Company getCompanyByCComID(String ccompanyID) {
        String hql = "from Company c where c.companyID = (select cc.comanyId from CcomCom cc where cc.ccompanyId = ?)";
        Company cc = (Company) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{ccompanyID});
        return cc;
    }

    /**
     * 生成备份信息
     *
     * @param payBackupBill
     * @return
     */
    public String savePaybackUp(PayBackupBill payBackupBill) {
        String hql = "from PayBackupBill where journalNum = ?";
        PayBackupBill cc = (PayBackupBill) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{payBackupBill.getJournalNum()});
        if (cc == null) {
            payBackupBill.setPbbID(serverService.getServerID("pbbID"));
            payBackupBill.setAttach("selfpay");
            baseBeanService.save(payBackupBill);
        } else {
            //     if(payBackupBill.getAddressID()!=null&&!payBackupBill.getAddressID().equals("")){
            cc.setAddressID(payBackupBill.getAddressID());
            //      }
            //     if(payBackupBill.getNoviceName()!=null&&!payBackupBill.getNoviceName().equals("")){
            cc.setNoviceName(payBackupBill.getNoviceName());
            //     }
            //     if(payBackupBill.getNovicePhone()!=null&&!payBackupBill.getNovicePhone().equals("")){
            cc.setNovicePhone(payBackupBill.getNovicePhone());
            //    }
            //     if(payBackupBill.getNoviceCode()!=null&&!payBackupBill.getNoviceCode().equals("")){
            cc.setNoviceCode(payBackupBill.getNoviceCode());
            //      }
            //    if(payBackupBill.getNoviceAddress()!=null&&!payBackupBill.getNoviceAddress().equals("")){
            cc.setNoviceAddress(payBackupBill.getNoviceAddress());
            //    }
            if (payBackupBill.getPtppid() != null && !payBackupBill.getPtppid().equals("")) {
                cc.setPtppid(payBackupBill.getPtppid());
            }
            if (payBackupBill.getPtstandard() != null && !payBackupBill.getPtstandard().equals("")) {
                cc.setPtstandard(payBackupBill.getPtstandard());
            }
            if (payBackupBill.getRemark() != null && !payBackupBill.getRemark().equals("")) {
                cc.setRemark(payBackupBill.getRemark());
            }
            //      if(payBackupBill.getPrivateRoom()!=null&&!payBackupBill.getPrivateRoom().equals("")){
            cc.setPrivateRoom(payBackupBill.getPrivateRoom());
            //      }
            if (payBackupBill.getEnrollID() != null && !payBackupBill.getEnrollID().equals("")) {
                cc.setEnrollID(payBackupBill.getEnrollID());
            }


            baseBeanService.update(cc);
        }

        return payBackupBill.getJournalNum();
    }

    /**
     * 获取打印小票关于公司的信息
     *
     * @param list :List<SelfCart>
     * @return
     */
    public ContactCompany getPrintCompanyInfo(List<BaseBean> list) {
        ContactCompany contactCompany = null;
        List<String> companylist = new ArrayList<String>();
        String companyID = "";
        String hql = "from ContactCompany cc where cc.ccompanyID = (select cm.ccompanyId from CcomCom cm where cm.comanyId = ?)";
        for (int i = 0; i < list.size(); i++) {
            SelfCart selfCart = (SelfCart) list.get(i);
            if (!companylist.contains(selfCart.getCompanyId())) {
                if (selfCart.getComID() != null && !selfCart.getComID().equals("")) {
                    companylist.add(selfCart.getComID());
                } else if (selfCart.getCompanyId() != null && !selfCart.getCompanyId().equals("")) {
                    companylist.add(selfCart.getCompanyId());
                }
            }
        }
        if (companylist.size() == 1) {
            companyID = companylist.get(0).toString();
        } else {
            companyID = Constant.ZLY_COMPNAYID;
        }
        contactCompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{companyID});

        return contactCompany;
    }

    @Override
    public List<BaseBean> getMemberList() {
        List<BaseBean> beans = baseBeanService.getListBeanBySqlAndParams("select ps.ppname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid" +
                "  from DT_PRO_SETUP ps, dt_ProductPackaging p where ps.com_id = ?" +
                "   and p.ppid = ps.ppid and p.showweixin = ? and p.type = ?" +
                "   and ps.fcom_id is null and ((p.model >= ? and p.model != ? and p.model != ?) or model = ?) order by sorting", new Object[]{"company201009046vxdyzy4wg0000000025", "01", "个人会员", "0", "0.5", "1", "8"});
        return beans;
    }

    @Override
    public String getSelfByProType(String ppId) {
        String type = (String) baseBeanDao.getObjectBySqlAndParams("select pp.type from dt_productpackaging pp where pp.ppid =  ?", new Object[]{ppId});
        return type;
    }

    /**
     * 如果产品原本是原价，并且设置了VIP价格，会员购买后修改成VIP的价格以及类型
     * mode;付款方式
     */
    public void updateSelfCart(String sccid, String journalNum, String mode) {

        List<BaseBean> listcart = baseBeanDao.getListBeanByHqlAndParams("from SelfCart where payjournalNum = ?", new Object[]{journalNum});
        if (listcart != null && listcart.size() != 0) {

            StringBuilder sql = new StringBuilder();
            if (!"cash".equals(mode)) {
                String hql = "from TEshopCusCom t where sccId = ?";
                TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{sccid});

                if (Float.parseFloat(tc.getCusType()) <= 5) {

                    sql.append("update dt_selfcart c set  c.activityID =  nvl((select vvv.vipId from dt_pro_vip vvv where vvv.ppid = c.pid and vvv.state='00'),c.activityID),c.oripri = c.price,c.price = nvl((select ");
                    sql.append("decode(substr(to_char(ROUND(v.vip*(1+nvl(sz.total_pct,0)/100),2)),0,1),'.','0'||to_char(ROUND(v.vip*(1+nvl(sz.total_pct,0)/100),2)),to_char(ROUND(v.vip*(1+nvl(sz.total_pct,0)/100),2)))");
                    sql.append(" from dt_pro_vip v left join dt_productpackaging p on v.ppid = p.ppid  and v.state = '00' and p.companyid = v.companyid ");
                    sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid  left join dtCompany t on t.companyid = cc.compnay_id");
                    sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
                    sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid =cm.industrytype and sz.stutas = '01' where v.ppid = c.pid),c.price)");
                    sql.append(" ,c.costmoney = nvl((select decode(substr(to_char(vv.factory),0,1),'.','0'||to_char(vv.factory),to_char(vv.factory)) from dt_pro_vip vv where vv.ppid = c.pid),c.costmoney)");
                    sql.append(" ,c.pritype = nvl((select '2' from dt_pro_vip vv where vv.ppid = c.pid and vv.state = '00'),'0')");
                    sql.append(" where c.pritype = ? and (c.journalnum = ? or (c.payjournalnum = ? and c.payjournalnum != c.journalnum)) and c.pid in (select z.ppid from dt_pro_vip z where c.pid = z.ppid and z.state='00')");
                    List<Object[]> list = new ArrayList<Object[]>();
                    Object[] objArray = {"0", journalNum, journalNum};
                    list.add(objArray);
                    baseBeanService.executeSqlsByParmsList(null, new String[]{sql.toString()}, list);
                } else {
                    sql.append("update dt_selfcart c set c.pritype = '0',c.price = c.oripri");
                    sql.append(",c.activityID = (select vv.SUID");
                    sql.append(" from DT_PRO_SETUP vv where vv.ppid = c.pid and vv.state = '00')");

                    sql.append(",c.costmoney = (select decode(substr(to_char(v.ef_price),0,1),'.','0'||to_char(v.ef_price),to_char(v.ef_price))");
                    sql.append(" from DT_PRO_SETUP v where v.ppid = c.pid and v.state = '00')");
                    sql.append("where c.pritype = ? and (c.journalnum = ? or (c.payjournalnum = ? and c.payjournalnum != c.journalnum)) and c.pid in (select z.ppid from DT_PRO_SETUP z where c.pid = z.ppid and z.state='00')");
                    List<Object[]> list = new ArrayList<Object[]>();
                    Object[] objArray = {"2", journalNum, journalNum};
                    list.add(objArray);
                    baseBeanService.executeSqlsByParmsList(null, new String[]{sql.toString()}, list);
                }
            } else {
                sql.append("update dt_selfcart c set c.pritype = '0',c.price = c.oripri");
                sql.append(",c.activityID = (select vv.SUID");
                sql.append(" from DT_PRO_SETUP vv where vv.ppid = c.pid and vv.state = '00')");
                sql.append(",c.costmoney = (select decode(substr(to_char(v.ef_price),0,1),'.','0'||to_char(v.ef_price),to_char(v.ef_price))");
                sql.append(" from DT_PRO_SETUP v where v.ppid = c.pid and v.state = '00')");
                sql.append("where c.pritype = ? and (c.journalnum = ? or (c.payjournalnum = ? and c.payjournalnum != c.journalnum)) and c.pid in (select z.ppid from DT_PRO_SETUP z where c.pid = z.ppid)");
                List<Object[]> list = new ArrayList<Object[]>();
                Object[] objArray = {"2", journalNum, journalNum};
                list.add(objArray);
                baseBeanService.executeSqlsByParmsList(null, new String[]{sql.toString()}, list);
            }

        }
    }


    /**
     * 根据购物卡获取sccid
     *
     * @param scard
     * @return
     */
    public String getSccidByCard(String scard) {
        String sccid = "";
        String hql = "from GiftCards where cardNumber = ? and state = ?";
        GiftCards giftCards = (GiftCards) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{scard, "1"});
        if (giftCards != null) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where staffid = ? and acquiesce = ?", new Object[]{giftCards.getStaffID(), "01"});
            sccid = tc.getSccId();
        }
        return sccid;
    }


    /**
     * 验证购物卡是否绑定微信openid
     *
     * @param scard
     * @return
     */
    public Map<String, Object> checkWxCard(String scard, String openid) {
        Map<String, Object> mp = new HashMap<String, Object>();
        String hql = "from GiftCards where cardNumber = ? and state = ?";
        GiftCards giftCards = (GiftCards) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{scard, "1"});
        if (giftCards != null) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where staffid = ? and acquiesce = ?", new Object[]{giftCards.getStaffID(), "01"});
            if (tc != null && tc.getOpenId() != null && !tc.getOpenId().equals("")) {
                mp.put("result", "3"); //购物卡已经绑定其他微信,无法再次绑定
            } else {
                TEshopCustomer tr = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{tc.getAccount()});

                mp.put("result", "4"); //可以绑定
                mp.put("sccId", tc.getSccId());
                mp.put("paymentCode", tr.getPaymentCode());
            }

        } else {
            mp.put("result", "1");//无效的购物卡
        }
        return mp;
    }


    /**
     * 验证购物卡是否绑定微信openid成功并绑定
     *
     * @return
     */
    public String bindCard(String sccId, String openid) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        try {
            Map<String, Object> mp = new HashMap<String, Object>();
            TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});

            //绑定人脸微信
            TEshopCustomer tr = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{tc.getAccount()});

            TEshopCusCom wxtc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where openId = ?", new Object[]{openid});
            TEshopCustomer wxtr = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where openId = ?", new Object[]{openid});
            if (tr != null) {
                tr.setOpenId(openid);
                tc.setOpenId(openid);
                beans.add(tc);
                beans.add(tr);
            }
            if (wxtc != null && wxtc.getAccount() != null && wxtc.getAccount().length() != 11) {
                wxtc.setOpenId(openid + tc.getAccount());
                wxtc.setAccount(openid + tc.getAccount());
                wxtr.setOpenId(openid + tc.getAccount());
                wxtr.setAccount(openid + tc.getAccount());
                beans.add(wxtc);
                beans.add(wxtr);
            }
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        } catch (Exception e) {

        }


        return "5";
    }

    /**
     * 获取VIP专享价格
     *
     * @return
     */
    public String getVipTmoney(String sccId, String journalNum, String totalMoney) {
        String vipmoney = "";
        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
        if (Float.parseFloat(tc.getCusType()) <= 5) {
            String hqls = "select ROUND(sum(to_number(price)*to_number(itemNum)),2) from SelfCart where payjournalNum = ?";
            Object obj = baseBeanDao.getObjectByHqlAndParams(hqls, new Object[]{journalNum});
            BigDecimal mb = new BigDecimal(totalMoney);
            if (obj != null) {
                String ttmoney = obj.toString();
                BigDecimal ttb = new BigDecimal(ttmoney);
                if (ttb.compareTo(mb) == -1) {
                    vipmoney = ttmoney;
                }
            }
        }
        return vipmoney;
    }


    /**
     * 根据账号密码或者操作员编号
     *
     * @param telphone
     * @param companyID
     * @param psw
     * @return
     */
    public Map<String, String> getCashOperator(String telphone, String companyID, String psw) {
        String staffID = "";
        StringBuilder sb = new StringBuilder();
        List<Object> param = new ArrayList<Object>();
        Map<String, String> map = new HashMap<String, String>();

        sb.append("select y.staffid,f.staffName from t_Eshop_Customer r, t_Eshop_Cuscom  z, Dtcaccount y, dt_hr_staff f,dtCRole c");
        sb.append(" where y.staffid = f.staffid  and f.sccid = z.sccid  and z.account = r.account and c.roleid = y.roleid");

        if (companyID != null && !companyID.equals("")) {
            sb.append("  and y.companyid = ? ");
            param.add(companyID);
        }
        if (telphone != null && !telphone.equals("")) {
            sb.append("  and r.account = ? ");
            param.add(telphone);
        }
        sb.append("  and (c.rolename = ? or c.rolename = ?)");
        sb.append(" and r.paymentCode = ?");
        param.add("超市操作员");
        param.add("收银员");
        param.add(psw);

        List<Object[]> list = baseBeanService.getListBeanBySqlAndParams(sb.toString(), param.toArray());
        if (list != null && list.size() > 0) {
            map.put("staffID", list.get(0)[0].toString());
            map.put("staffName", list.get(0)[1].toString());
        }

        return map;

    }

    /**
     * 根据公司ID获取往来单位
     *
     * @param comID
     * @return
     */
    public ContactCompany getCCompanyBycomID(String comID) {
        ContactCompany contactCompany = (ContactCompany) baseBeanDao.getBeanByHqlAndParams("from ContactCompany c where c.ccompanyID = (select cc.ccompanyId from CcomCom cc where cc.comanyId = ?)", new Object[]{comID});
        return contactCompany;
    }

    /**
     * 根据人查询扫描记录
     *
     * @param posNum
     * @param staffID
     * @param companyID
     * @return
     */
    public List<BaseBean> getScanRecord(String posNum, String staffID, String companyID) {
        String hql = "from ScanGoodsRecord where companyID = ?";
        List<Object> param = new ArrayList<Object>();
        param.add(companyID);

        if (posNum != null && !posNum.equals("")) {
            hql += "  and state = ?";
            hql += " and posNum = ?";
            param.add("09");
            param.add(posNum);

        } else if (staffID != null && !staffID.equals("")) {
            hql += " and ( state = ? or state = ?)";
            hql += " and staffID = ?";
            param.add("09");
            param.add("00");
            param.add(staffID);

        }
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql, param.toArray());

        return list;
    }


    /**
     * 扫描记录
     *
     * @return
     */
    public String joinScanRecord(ScanGoodsRecord scanGoods) {
        scanGoods.setSgrId(serverService.getServerID("sgrid"));
        scanGoods.setJoinDate(new Date());
        scanGoods.setState("00");
        scanGoods.setJoinNum(scanGoods.getItemNum());
        if (scanGoods.getPosNum() != null && !scanGoods.getPosNum().equals("")) {
            scanGoods.setStaffID("");
        }
        baseBeanDao.save(scanGoods);


        return scanGoods.getSgrId();


    }

    /**
     * 删除扫描记录
     *
     * @return
     */
    public void deleteScanRecord(String sgrId) {
        String hql = "update ScanGoodsRecord set state= ? where sgrId = ?";
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null,
                new String[]{hql}, new Object[]{"09", sgrId});


    }


    /**
     * 增加数量或者减少数量
     *
     * @param sgrId
     * @param itemNum
     */
    public String joinOrReduceGoods(String sgrId, String itemNum, ScanGoodsRecord scanGoods) {
        ScanGoodsRecord goodsRecord = null;
        if (sgrId != null && !sgrId.equals("")) {
            String hql = "from ScanGoodsRecord where sgrId = ?";
            goodsRecord = (ScanGoodsRecord) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{sgrId});
            if (goodsRecord != null) {
                if (Float.parseFloat(itemNum) > Float.parseFloat(goodsRecord.getJoinNum())) {
                    goodsRecord.setJoinNum(itemNum);
                }
                goodsRecord.setItemNum(itemNum);
                if ("0".equals(itemNum)) {
                    goodsRecord.setState("09");//已删除
                }
                baseBeanDao.update(goodsRecord);
            }
        } else {
            String hql = "from ScanGoodsRecord where posNum = ? and companyId = ? and pid = ? and stardard = ? and state!=?";
            goodsRecord = (ScanGoodsRecord) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{scanGoods.getPosNum(), scanGoods.getCompanyId(), scanGoods.getPid(), scanGoods.getStardard(), "01"});
            if (goodsRecord != null) {
                sgrId = goodsRecord.getSgrId();
                goodsRecord.setItemNum((Integer.parseInt(goodsRecord.getItemNum()) + Integer.parseInt(scanGoods.getItemNum())) + "");
                goodsRecord.setJoinNum(goodsRecord.getItemNum());
                baseBeanDao.update(goodsRecord);
            } else {
                sgrId = joinScanRecord(scanGoods);
            }

        }
        return sgrId;
    }

    /**
     * 记录理由
     *
     * @param relateID
     * @param reason
     * @return
     */
    public void addReason(String relateID, String reason) {

        String hql = "update ScanGoodsRecord set reason = ?,state= ? where relateID = ?";
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null,
                new String[]{hql}, new Object[]{reason, "01", relateID});

    }

    /**
     * 验证账号是否被其他用户登录 过
     *
     * @param accountEmail
     * @param staffID
     * @param comID
     * @param loginGuid
     * @return
     */
    public String validateLogin(String accountEmail, String staffID, String comID, String loginGuid) {

        //查询登陆名是否存在
        CAccount tempAccount = (CAccount) baseBeanDao.getBeanByHqlAndParams("from CAccount where accountEmail = ? and companyID = ? and staffID = ? and loginGuid = ?", new Object[]{accountEmail, comID, staffID, loginGuid});
        if (tempAccount == null) {
            return "0";
        }
        return "1";

    }

    /**
     * 保存变价信息
     *
     * @param parem
     * @throws Exception
     */
    @Override
    public void savePrice(List<BaseBean> parem) throws Exception {

        baseBeanDao.executeHqlsByParmsList(parem, null, null);
    }

    /**
     * 验证刷脸用户
     *
     * @param openid
     * @return
     */
    public Map<String, Object> faceValiShopping(String openid) {
        Map<String, Object> map = new HashMap<String, Object>();
        String hql = "from TEshopCusCom where openId = ?";
        TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{openid});
        if (tc == null) {
            map.put("result", "3");
            //没有账号，就要提示绑定手机号
        } else if (tc.getAccount() == null || tc.getAccount().length() > 11) {
            map.put("result", "4");//有账号，没关联手机号，绑定手机号
        } else {
            map.put("result", "5");//有账号，验证金额
            map.put("sccId", tc.getSccId());//有账号，验证金额
        }
        return map;
    }

    /**
     *
     * 得到库存数
     *
     * @param companyID
     * @param ppid
     * @return
     */
    public String getInv(String companyID, String ppid) {
//        Inventory inv = (Inventory)baseBeanService.getBeanByHqlAndParams("from Inventory where companyID = ? and productId = ? and warehouseName = ?",new Object[]{companyID,ppid,"销售库"});
//
//        String  invnum= "0";
//        if(inv!=null&&inv.getInvenQuantity()!=null&&!inv.getInvenQuantity().equals("")){
//
//            invnum = Float.parseFloat(inv.getInvenQuantity()==null?"0":inv.getInvenQuantity())-Float.parseFloat(inv.getPreQuantity()==null?"0":inv.getPreQuantity())+"";
//        }

        return "";

    }

    /**
     *
     * 记录预扣库存
     *
     * @param companyID
     * @param ppid
     * @param num
     * @return
     */
    public void setPreInv(String companyID, String ppid, String num) {
//        String  hql  = "update Inventory  set preQuantity = nvl(preQuantity,0) + ? where companyID = ? and productId = ? and warehouseName = ?";
//        List<Object[]> paramslist = new ArrayList<Object[]>();
//        Object[] obj = new Object[]{num,companyID,ppid,"销售库"};
//        paramslist.add(obj);
//        baseBeanDao.executeHqlsByParmsList(null,new String[]{hql},paramslist);

    }


    /**
     *
     * 获取自动开通权限
     *
     * @param sccid
     * @return
     */
    public CusComAuth getAuthcc(String sccid) {
        CusComAuth cusComAuth = (CusComAuth) baseBeanDao.getBeanByHqlAndParams("from CusComAuth where sccId = ?", new Object[]{sccid});
        return cusComAuth;
    }


    /**
     *
     * 开通或者不开通金币积分扣款
     *
     * @param coinfee
     */
    public void setKt(String coinfee, String sccid) {
        CusComAuth cusComAuth = (CusComAuth) baseBeanDao.getBeanByHqlAndParams("from CusComAuth where sccId = ?", new Object[]{sccid});

        if (cusComAuth == null) {
            cusComAuth = new CusComAuth();
            cusComAuth.setCaId(serverService.getServerID("caid"));
            cusComAuth.setCoinfee(coinfee);
            cusComAuth.setCoinDate(new Date());
            cusComAuth.setSccId(sccid);
            cusComAuth.setSource("0");

        }
        cusComAuth.setCoinfee(coinfee);

        cusComAuth.setCoinDate(new Date());
        baseBeanDao.update(cusComAuth);


    }

    /**
     * 获取信息
     *
     * @param staffID
     * @return
     */
    public Staff getInfo(String staffID) {
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{staffID});


        return staff;
    }

    /**
     *
     * 获取货柜编号
     *
     * @param companyID
     * @return
     */
    public List<BaseBean> getHgcodeList(String companyID) {
        String hql = "from DepotManage where companyID = ? and itemID in (select codeID from CCode where codeValue = '智能货柜') order by depotCoding";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{companyID});
        return list;
    }

    /**
     * 获取货柜编码
     *
     * @return
     */
    public String getHgcodeing(String posNum) {
        String hql = "from PosDevice where posNum = ?";
        PosDevice posDevice = (PosDevice) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{posNum});
        String hgcode = "";
        if (posDevice != null) {
            hgcode = posDevice.getHgcode();
        }
        return hgcode;
    }


    /**
     *
     * 验证账号获取用户信息
     *
     * @param phoneNumber
     * @return
     */
    public Staff validAccount(String phoneNumber) {

        String hql = "from TEshopCustomer where account = ? and logOff=0";
        TEshopCustomer c = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{phoneNumber});

        String hqlstaff = "from Staff where staffID = ?";
        Staff staff = null;
        if (c != null) {
            staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlstaff, new Object[]{c.getStaffid()});
        } else {
            //如果没有注册调用注册


            String sccid = "TEshopCusCom20161010W9FXK9NJ450000011682";

            TEshopCusCom tuicus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
            staff = new Staff();
            staff.setStaffName(phoneNumber.substring(0, 7) + "****");
            String stf = wfjJifenService.zhuCe(tuicus, sccid, phoneNumber, "123456", staff);
        }

        return staff;
    }

    /**
     * 核对购物卡是否正确以及余额是否充足
     *
     * @return
     */
    public Map<String, Object> checkHgcard(String sccId, String totalMoney) {
        Map<String, Object> map = new HashMap<String, Object>();
        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
        TEshopCustomer tcr = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0", new Object[]{tc.getAccount()});
        String jifen = searchPoint(tc.getSccId());
        BigDecimal jb = new BigDecimal(jifen);
        BigDecimal yb = new BigDecimal("100");
        BigDecimal mb = new BigDecimal(5);
        if (totalMoney == null || totalMoney.equals("")) {
            if (jb.compareTo(mb.multiply(yb)) == -1) {//开门时判断积分是否有钱
                map.put("result", "2");//积分不足5元
            } else {
                map.put("paymentCode", tcr.getPaymentCode());//积分充足
                map.put("result", "0");//积分充足


            }
        } else {
            //关门结算时判断是否够扣的
            BigDecimal mb1 = new BigDecimal(totalMoney);
            if (jb.compareTo(mb1.multiply(yb)) == -1) {//开门时判断积分是否有钱
                map.put("result", "3");//积分不够扣
            } else {
                map.put("result", "4");//积分充足
            }
        }


        return map;
    }

    /**
     *
     * 获取用户信息
     *
     * @param sccid
     * @return
     */
    public Staff getInfoBySccid(String sccid) {

        Staff cc = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff f where f.staffID = (select m.staffid from TEshopCusCom m where m.sccId = ?)", new Object[]{sccid});


        return cc;
    }

    /**
     *
     * 根据设备序列号查询往来公司
     *
     * @param posNum
     * @return
     */
    public ContactCompany getCCompanyByPosNum(String posNum) {

        String hql = "from ContactCompany c where c.ccompanyID = (select p.accessCcomID from PosDevice p where p.posNum = ?)";
        ContactCompany ccom = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{posNum});

        return ccom;

    }

    /* 根据货柜编号查询所有的秤盘编号以及所有的库存
     */
    public List<Object> getCplist(String hgcode) {

        String sql = "select c.depotCoding,sum(to_number(t.invenQuantity)*to_number(nvl(p.singleWeight,1))) from dt_inv_invt t,dtDepotManage c,dt_ProductPackaging p where  p.ppid = t.productId and t.warehouse = c.depotID and p.showweixin = ?  and c.depotPID = (select d.depotID from dtDepotManage d where  d.depotCoding = ?) group by c.depotcoding order by c.depotCoding";


        List<Object> list = baseBeanDao.getListObjectBySqlAndParams(sql, new Object[]{"01", hgcode});


        return list;
    }

    /**
     *
     * 添加重量记录
     *
     * @param weights
     * @param cplist
     * @param hgcode
     * @param staffID
     */
    private void addWeightRecord(String weights, String cplist, String hgcode, String staffID) {
        System.out.println("addWeightRecord");
        System.out.println(cplist);
        System.out.println(weights);


        try {
            WeightRecord weightRecord = new WeightRecord();
            weightRecord.setWrd(serverService.getServerID("wrid"));
            weightRecord.setCreateDate(new Date());
            weightRecord.setHgcode(hgcode);
            weightRecord.setWeightB(cplist);
            weightRecord.setWeightA(weights);
            weightRecord.setStaffID(staffID);

            baseBeanDao.save(weightRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 重量记录关联订单号
     *
     * @param hgcode
     * @param journalNum
     */
    public void addJournal(String hgcode, String journalNum) {
        try {
            String hql = "from WeightRecord where hgcode = ? order by createDate desc";
            List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{hgcode});
            if (list.size() > 0) {
                WeightRecord weightRecord = (WeightRecord) list.get(0);
                weightRecord.setJournalNum(journalNum);
                baseBeanDao.update(weightRecord);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * 货柜加入购物车
     *
     * @param weights
     * @param cplist
     * @param posNum
     * @param staffID
     * @return
     */
    public String addCartHg(String weights, String cplist, String posNum, String staffID, String hgcode) {

        addWeightRecord(weights, cplist, hgcode, staffID);
        String ccompanyID = "";
        System.out.println("posNum" + posNum);

        String[] arr = weights.split("#");//拿之后秤盘
        String[] arr2 = cplist.split("#");//拿之前初始秤盘
        List<Object[]> totallist = new ArrayList<Object[]>();
        float afterWeight = 0f;//拿之后秤盘
        float beforeWeight = 0f;//拿之前初始秤盘
        for (int a = 0; a < arr.length; a++) {
            String[] arr0 = arr[a].split(",");
            String cpweight = arr0[1];
            afterWeight = afterWeight + Float.parseFloat(cpweight);


        }
        System.out.println("weights" + weights);
        System.out.println("afterWeight" + afterWeight);
        System.out.println("cplist" + cplist);
        for (int b = 0; b < arr2.length; b++) {
            String[] arr22 = arr2[b].split(",");
            String cpweight2 = arr22[1];
            beforeWeight = beforeWeight + Float.parseFloat(cpweight2);

        }


        System.out.println("beforeWeight" + beforeWeight);

        float absweight = Math.abs(beforeWeight - afterWeight);
        System.out.println("absweight" + absweight);

        if (absweight > 0.02) {

            for (int i = 0; i < arr.length; i++) {
                String[] arr1 = arr[i].split(",");
                String cpcode = arr1[0];
                String cpweight = arr1[1];
                System.out.println("cpcode" + cpcode);
                System.out.println("cpweight" + cpweight);

                for (int j = 0; j < arr2.length; j++) {
                    String[] arr21 = arr2[j].split(",");
                    String cpcode2 = arr21[0];
                    String cpweight2 = arr21[1];

                    System.out.println("cpcode2" + cpcode2);
                    System.out.println("cpweight2" + cpweight2);
                    if (cpcode.equals(cpcode2)) {


                        float cx1 = Float.parseFloat(cpweight2) - Float.parseFloat(cpweight);


                        float cx = Math.abs(BigDecimal.valueOf(cx1).setScale(3, RoundingMode.HALF_UP).floatValue());
                        System.out.println("cx" + cx);
                        if (cx > 0.02) {//判断到时候增加误差上下范围
                            List<Object[]> listc = computeBar(cx, cpcode);
                            System.out.println("listc" + listc);
                            for (int u = 0; u < listc.size(); u++) {
                                totallist.add(listc.get(u));
                            }
                            System.out.println("totallist" + totallist);


                        }
                    }
                }

            }
        }
        if (totallist.size() > 0) {
            hgoinCart(totallist, posNum, hgcode);
            System.out.println("totallist2" + totallist);

            Object[] oc = totallist.get(0);
            ccompanyID = oc[4].toString();
        } else {
            ccompanyID = "1";
        }
        return ccompanyID;

    }

    private List<Object[]> convertListArrays(List<Object> list) {
        List<Object[]> convertedList = new ArrayList<>();
        for (Object obj : list) {
            if (obj instanceof Object[]) {
                convertedList.add((Object[]) obj);
            }
        }
        return convertedList;
    }

    private int computerPro(int start, int end, int floor, float sum, String p, float target, List<Object[]> b) {
        if (start == end) {//退出递归的方法，进行下次循环 已经达到多重循环的次数了，不用再增加循环，直接执行循环的内容就可以了
            // System.out.println(start);
            return 0;
        }
        Object[] obj = b.get(floor - 1);
//        BigDecimal a1 = new BigDecimal(obj[2].toString());
//        BigDecimal a2 = new BigDecimal(obj[1].toString());
//        int n = a1.divide(a2, BigDecimal.ROUND_HALF_UP).intValue();
        int n = Integer.parseInt(obj[2].toString());//每个商品库存数
        float weight = Float.parseFloat(obj[1].toString());//单量
        String ppid = obj[0].toString();//单量
        for (int i = 0; i <= n; i++) {
//            if(bc){
//                break;
//            }
            //  System.out.println(floor+"层:"+i);
            String p1 = p + ppid + "," + weight + "," + i * weight + "," + i + "-";

            float sum1 = sum + i * weight;

            sum1 = BigDecimal.valueOf(sum1).setScale(3, RoundingMode.HALF_UP).floatValue();
            System.out.println(sum1);

//            float sm0 = sum1+i*Float.parseFloat(Constant.Error_Num);
//
//                  sm0 = BigDecimal.valueOf(sm0).setScale(3, RoundingMode.HALF_UP).floatValue();
//            float sm1 = sum1-i*Float.parseFloat(Constant.Error_Num);
//
//                  sm1 = BigDecimal.valueOf(sm1).setScale(3, RoundingMode.HALF_UP).floatValue();

            if (floor == end) {
                float wc = sum1 - target;
                float absNum = Math.abs(wc);
                if (mixNum == 999) {
                    mixNum = absNum;
                } else if (absNum < mixNum) {
                    mixNum = absNum;
                    r = p1;
                    System.out.println("r:" + r);
//                    if(mixNum<=0.02){
//                        //误差值小于20g
//                        bc = true;
//                        System.out.println("bc:"+mixNum);
//                    }
                }
                System.out.println("mixNum" + mixNum);
                System.out.println("sum1:" + sum1);
                System.out.println("p1" + p1);
                //  if(sum1==target||(target>=sm0&&target<=sm1)){
                //     System.out.println(p1);
                //        System.out.println("target:" + sum1);
                //bc = true;
                //  r = p1;


                //   }
            }
            computerPro(start + 1, end, floor + 1, sum1, p1, target, b);
        }

        return 0;
    }

    /**
     *
     * 一个秤盘非标品和标品不能同时存在
     *
     * @param cx
     * @param cpcode
     * @return
     */
    private List<Object[]> computeBar(float cx, String cpcode) {
        Map<String, String> map = new HashMap<String, String>();
        List<Object[]> flist = new ArrayList<Object[]>();
        String al = "";
        String cc = "";
        float money = 0;
        String ppid = "";
        String companyID = "";
        String ccompanyid = "";
        //根据秤盘获取goodsID
        Object[] objs = null;
        String sqls = "select t.productId,p.singleWeight,t.invenQuantity,c.companyID,p.stanpro from dt_inv_invt t,dtDepotManage c,dt_ProductPackaging p where  p.ppid = t.productId and t.warehouse = c.depotid and to_number(t.invenQuantity) > 0 and p.showweixin = ? and t.warehouse = (select d.depotID from dtDepotManage d where  d.depotCoding = ?) order by c.depotCoding";
        List<Object> list = baseBeanDao.getListObjectBySqlAndParams(sqls, new Object[]{"01", cpcode});
        Object[] ccc = (Object[]) list.get(0);

        List<Object> list1 = new ArrayList<Object>();
        companyID = ccc[3].toString();
        String stanpro = ccc[4].toString();
        if ("0".equals(stanpro)) {
            //如果有多种商品 说明必须是标品，非标品不可以放在同一个秤盘，标品和非标品 不可再同一个秤盘。这块要根据标品单重量来判断是具体哪个产品
            //这种情况估计不能实现，暂时只考虑一个秤放一个商品

            List<Object[]> b = convertListArrays(list);
            r = "";
            bc = false;
            mixNum = 999;
            computerPro(0, b.size(), 1, 0f, "", cx, b);
            if (!"".equals(r)) {
                String[] arr = r.split("-");
                for (int j = 0; j < arr.length; j++) {
                    String[] array1 = arr[j].split(",");
                    String ppid1 = array1[0];
                    String dl = array1[1];
                    String cx1 = array1[2];
                    String num = array1[3];

                    if (Float.parseFloat(array1[2]) > 0.0f) {
                        Object[] c = new Object[]{ppid1, dl, cx1, companyID, "0", num, cpcode};
                        list1.add(c);
                    }


                }
            }


        } else {

            //非标品
            list1 = list;
        }
        List<Object[]> clist = new ArrayList<Object[]>();
        for (int k = 0; k < list1.size(); k++) {
            Object obj = list1.get(k);
            objs = (Object[]) obj;
            List<Object> params = new ArrayList<Object>();
            HttpServletRequest request = ServletActionContext.getRequest();
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = e.format(new Date());
            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            sql.append("case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price ");
            sql.append(",s.alternativeItemID,p.ppid,p.companyID,cm.ccompanyid");
            sql.append(" from  DT_PRO_SETUP ps ");
            sql.append("  left join dt_ProductPackaging p on ps.ppid = p.ppid and ps.state = '00'");
            sql.append(" inner join dtScaleGoods s on s.goodsID = p.goodsID ");
            //特价
            sql.append(" left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state = '00'");
            sql.append(" left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01'");
            sql.append(" and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");

            //促销
            sql.append(" left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state = '00'");
            sql.append(" left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00'");
            sql.append(" and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS')");
            //消费红包
            sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid");
            sql.append(" left join dtCompany t on t.companyid = cc.compnay_id");
            sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid");
            sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
            sql.append("  where ps.fcom_id is null");
            sql.append("  and p.isScale = ? and p.companyID = ? and p.ppid=?");
            //如果类型为1，sql语句变为查询批发商城信息

            params.add(currentTime);
            params.add(currentTime);
            params.add(currentTime);
            params.add(currentTime);


            params.add("0");
            params.add(companyID);

            params.add(objs[0].toString());

            Object objg = baseBeanDao.getObjectBySqlAndParams(sql.toString(), params.toArray());

            Object[] objgs = (Object[]) objg;
            al = objgs[1].toString();
            String price = objgs[0].toString();
            money = Float.parseFloat(String.format("%.2f", Float.parseFloat(price) * cx));
            ppid = objgs[2].toString();
//        companyID = objgs[3].toString();
            ccompanyid = objgs[4].toString();
            String C = "21" + al + pad2((money * 100) + "", 5);
            int C1 = 0;
            int C2 = 0;
            int V = 0;
            for (int i = 1; i < C.length() + 1; i++) {
                if (i % 2 == 1) {
                    C1 += C.charAt(i - 1);
                } else {
                    C2 += C.charAt(i - 1);
                }
            }
            String G1 = (C1 + C2 * 3) + "";
            V = 10 - Integer.parseInt(G1.substring(G1.length() - 1));
            System.out.println(money);
            System.out.println(al);
            System.out.println(pad2((money * 100) + "", 5));
            System.out.println(C);
            System.out.println(C + V + "");
            cc = C + V + "";
            float cs = cx;
            String numc = cx + "";
            if (objs[4] != null && "0".equals(objs[4])) {//标品且是多个产品
                cs = Float.parseFloat(objs[2].toString());
                numc = objs[5].toString();

            }
            Object[] obj1 = new Object[]{ppid, cc, money, companyID, ccompanyid, cs, numc, cpcode};
            System.out.println("numc:" + numc);
            if (!"0".equals(numc)) {
                System.out.println("numc12:" + numc);
                clist.add(obj1);
            }

//        map.put("money", money + "");
//        map.put("barcode", cc);
//        map.put("ppid", ppid);
//        map.put("companyID", companyID);
//        map.put("ccompanyid", ccompanyid);
        }

        return clist;
    }

    //补齐0
    private String pad2(String num, int n) {
        if (num.indexOf(".") != -1) {
            num = (int) Math.floor(Double.parseDouble(num)) + "";
            System.out.println("num" + num);
        }
        if ((num + "").length() >= n)
            return num;

        return pad2("0" + num, n);
    }

    /**
     *
     * 货柜直提购物加入购物车
     *
     * @param posNum
     * @return
     */
    private void hgoinCart(List<Object[]> totallist, String posNum, String hgcode) {
        try {
            String hql2 = "delete from SqSelfCart where hgcode = ?";
            String[] hqls = new String[]{hql2};
            List<BaseBean> beans = new ArrayList<BaseBean>();
            for (int k = 0; k < totallist.size(); k++) {
                Object[] oc = totallist.get(k);
                String pid = oc[0].toString();
                String barCode = oc[1].toString();
                String companyID = oc[3].toString();
                String money = oc[2].toString();
                float cx = (float) oc[5];
                String numc = oc[6].toString();
                String cpcode = oc[7].toString();
                //  ccompanyID = oc[4].toString();


                ProductPackaging pp = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams("from ProductPackaging where ppID = ?", new Object[]{pid});
                int itemNum = 0;
                String zlNum = "";
                if ("0".equals(pp.getStanpro())) {
                    //标品
                    // itemNum = (int) (cx / Float.parseFloat(pp.getSingleWeight()));//需要除以单量
//                    DecimalFormat df = new DecimalFormat("#.000");
//                    zlNum = df.format(cx / Float.parseFloat(pp.getSingleWeight()) + "");//需要除以单量
                    itemNum = Integer.parseInt(numc);
                    zlNum = itemNum + "";
                } else {
                    //不是标品
                    itemNum = 1;
                    zlNum = cx + "";

                }


                ScanGoodsRecord scanGoods = new ScanGoodsRecord();
                scanGoods.setPosNum(posNum);
                scanGoods.setPid(pid);
                scanGoods.setCompanyId(companyID);
                scanGoods.setItemNum(itemNum + "");
                scanGoods.setBarCode(barCode);
                scanGoods.setStardard("默认规格");
                scanGoods.setPrice(money);
                scanGoods.setGoodsName(pp.getGoodsName());
                scanGoods.setRelateID("");

                scanGoods.setSgrId(serverService.getServerID("sgrid"));
                scanGoods.setJoinDate(new Date());
                scanGoods.setState("00");
                scanGoods.setJoinNum(scanGoods.getItemNum());
                if (scanGoods.getPosNum() != null && !scanGoods.getPosNum().equals("")) {
                    scanGoods.setStaffID("");
                }
                beans.add(scanGoods);


                PosDevice pos = (PosDevice) baseBeanDao.getBeanByHqlAndParams("from PosDevice where posNum = ?", new Object[]{posNum});

                SqSelfCart cat = new SqSelfCart();
                cat.setSqId(serverService.getServerID("sqcart"));
                cat.setCompanyId(companyID);

                cat.setItemNum(itemNum + "");

                cat.setPid(pid);
                cat.setSgrId(scanGoods.getSgrId());
                cat.setStardard("默认规格");//规格
                cat.setPosNum(posNum);//规格
                cat.setComID(pos.getComID());
                cat.setBarCode(barCode);
                cat.setSendNum("0");
                cat.setShowNum(zlNum);
                cat.setHgCode(hgcode);
                cat.setAddDate(new Date());
                String hqlc = "from DepotManage where depotCoding = ?";
                DepotManage depotManage = (DepotManage) baseBeanDao.getBeanByHqlAndParams(hqlc, new Object[]{cpcode});
                if (depotManage != null) {
                    cat.setDeptId(depotManage.getDepotID());
                    cat.setDeptName(depotManage.getDepotName());
                }
                beans.add(cat);


            }
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, hqls, new Object[]{hgcode});
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 添加货柜用户信息
     *
     * @return
     */
    public void addHgRelateUser(String hgCode, String sccId, String loginMode) {
        String hql = "from  HgRelateUser where hgCode = ?";
        HgRelateUser hg = (HgRelateUser) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{hgCode});
        if (hg == null) {
            HgRelateUser hgRelateUser = new HgRelateUser();
            hgRelateUser.setHruId(serverService.getServerID("hruid"));

            hgRelateUser.setSccId(sccId);
            hgRelateUser.setHgCode(hgCode);
            hgRelateUser.setScanDate(new Date());
            hgRelateUser.setLoginMode(loginMode);
            baseBeanDao.save(hgRelateUser);
        }


    }

    /**
     *
     * 更新状态
     *
     * @param hgcode
     */
    public void updateUser(String hgcode) {
        String hql = "from  HgRelateUser where hgCode = ?";
        HgRelateUser hg = (HgRelateUser) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{hgcode});
        if (hg != null) {
            hg.setDoorState("1");
            baseBeanDao.update(hg);
        }

    }

    /**
     *
     * 删除货柜用户记录
     *
     * @param hgCode
     * @return
     */
    public void delHgRelateUser(String hgCode) {

        String hql = "delete from HgRelateUser where hgCode = ?";
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{hgCode});
    }

    /**
     *
     * 查询货柜扫码用户
     *
     * @param hgCode
     * @return
     */
    public String searchHgUser(String hgCode) {

        String hql = "from HgRelateUser where hgCode = ?";
        HgRelateUser hgRelateUser = (HgRelateUser) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{hgCode});
        return hgRelateUser != null ? hgRelateUser.getSccId() : "";
    }

    /**
     *
     * 查询货柜扫码用户
     *
     * @param hgCode
     * @return
     */
    public HgRelateUser getHgUser(String hgCode) {

        String hql = "from HgRelateUser where hgCode = ?";
        HgRelateUser hgRelateUser = (HgRelateUser) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{hgCode});
        return hgRelateUser;
    }


    /**
     * 根据订单获取剩余金额
     *
     * @param journalNum
     * @return
     */
    public UnPayRecord getUnPayRecord(String journalNum) {
        UnPayRecord unPayRecord = (UnPayRecord) baseBeanDao.getBeanByHqlAndParams("from UnPayRecord where journalNum=? and status = '00'", new Object[]{journalNum});

        return unPayRecord;

    }

    /**
     *
     * 更新货柜信息
     *
     * @param journalNum
     * @param hgcode
     */
    public void updateCash(String journalNum, String hgcode) {

        String hql = "update CashierBills set goodsCoding = ?,goodsName = ? where journalNum = ?";
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{hgcode, "智能货柜", journalNum});

    }

    /**
     *
     * 获取智能货柜未付款的单子
     *
     * @param staffid
     * @return
     */
    public String getUnPayCash(String staffid) {
        String hql = "from CashierBills where contactUserID = ? and goodsCoding is not null and goodsName = ? and fkStatus = ? and billsType = ?";
        CashierBills cc = (CashierBills) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffid, "智能货柜", "01", "项目收入预算单"});


        return cc != null ? cc.getCashierBillsID() : "";
    }


    /**
     *
     * 获取智能货柜未付款的单子
     *
     * @param sccId
     * @return
     */
    public Map<String, Object> getUnPayCashPos(String sccId, String staffID, Map<String, Object> map) {
        if (staffID == null || staffID.equals("")) {
            TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
            staffID = tc.getStaffid();
        }
        UnPayRecord unPayRecord = (UnPayRecord) baseBeanDao.getBeanByHqlAndParams("from UnPayRecord where sccId = ? and status = ?", new Object[]{sccId, "00"});
        if (unPayRecord != null) {
            map.put("remainMoney", unPayRecord.getRemainNum());
            //部分结算
        }
        String hql = "from CashierBills where contactUserID = ? and goodsCoding is not null and goodsName = ? and fkStatus = ? and billsType=?";
        CashierBills cc = (CashierBills) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID, "智能货柜", "01", "项目收入预算单"});


        //全部未结算
        map.put("cashierBills", cc);
        if (cc != null) {
            String sql = "select sum(to_number(num)) from (select sum(t.itemNum) num from DT_selfCart t where t.journalNum = ? and  (t.barcode not like '21%' or (t.barcode like '21%' and t.variableid  not in('KG','kg','千克'))) union all select count(to_number(t1.itemNum))num from DT_selfCart t1,dt_ProductPackaging p where t1.pid = p.ppid and (p.variableID = 'KG' or p.variableID = 'kg' or p.variableID = '千克')  and t1.journalNum =? and t1.barcode like '21%')";
            Object objs = baseBeanDao.getObjectBySqlAndParams(sql, new Object[]{cc.getJournalNum(), cc.getJournalNum()});

            map.put("journalNum", cc.getJournalNum());
            map.put("totalMoney", cc.getPriceSub());
            map.put("totalNum", objs.toString());
            map.put("companyName", cc.getCompanyName());
            map.put("ccompanyID", cc.getCcompanyID());
            map.put("comID", cc.getCompanyID());

            map.put("r", "1");
            if (unPayRecord == null) {
                map.put("remainMoney", cc.getPriceSub());
            }

        } else {
            map.put("r", "0");
        }


        return map;
    }

    /**
     *
     *
     * @param hgcode
     * @param journalNum
     */
    public void setHgCash(String hgcode, String journalNum) {

        String hql = "update CashierBills set goodsCoding = ?,goodsName = ? where journalNum = ? and billsType = ?";

        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{hgcode, "智能货柜", journalNum, "项目收入预算单"});
    }


    /**
     *
     * 清除购物车
     *
     * @param hgcode
     */
    public void clearSqCart(String hgcode) {
        String hql = "delete from SqSelfCart where hgcode = ?";
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{hgcode});

    }

    /**
     *
     * 扫码推送到开门成功
     *
     * @param hgcode
     * @param content
     * @param type
     * @param sccId
     */
    public void pushHg(String hgcode, String content, String type, String sccId) {
        String hql = "from PosDevice where hgcode = ?";
        List<BaseBean> poslist = baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{hgcode});

        if (poslist != null && poslist.size() > 0) {
            for (int i = 0; i < poslist.size(); i++) {
                PosDevice obj = (PosDevice) poslist.get(i);
                if (obj != null) {
                    List<String> slist = new ArrayList<String>();// 极光推送设备号
                    JSONObject json = new JSONObject();
                    json.accumulate("sccId", sccId); //
                    json.accumulate("loginMode", "scan");
                    json.accumulate("posNum", obj.getPosNum());
                    //  document.location.href = basePath+"ea/sm/ea_getOpenSuc.jspa?posNum="+posNum+"&sccId="+sccId+"&loginMode=scan";
                    slist.add(obj.getPosNum());
                    // 极光推送
                    JushMain.sendjiguangMessage(content, type, json.toString(), type,
                            slist);
                }
            }

        }


    }

    /**
     *
     * 货柜情况，现金存储公司ID
     *
     * @param journalNum
     * @param sccId
     */
    public void setCashCom(String journalNum, String sccId) {

        //存储下cashCompany sccid
        String hqlbb = "from PayBackupBill where journalNum = ?";
        PayBackupBill payBackupBill = (PayBackupBill) baseBeanService.getBeanByHqlAndParams(hqlbb, new Object[]{journalNum});
        if (payBackupBill != null) {
            payBackupBill.setCashCompany(sccId);
        } else {
            payBackupBill = new PayBackupBill();
            payBackupBill.setPbbID(serverService.getServerID("ppbid"));
            payBackupBill.setCashCompany(sccId);
            payBackupBill.setJournalNum(journalNum);
        }

        baseBeanDao.update(payBackupBill);
    }


    public void setPaySccid(String journalNum, String sccId) {
        String hql = "from PayBackupBill where journalNum = ?";
        PayBackupBill payBackupBill = (PayBackupBill) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{journalNum});
        if (payBackupBill != null) {
            payBackupBill.setSccid(sccId);

        } else {
            payBackupBill = new PayBackupBill();
            payBackupBill.setPbbID(serverService.getServerID("ppbid"));
            payBackupBill.setSccid(sccId);
            payBackupBill.setJournalNum(journalNum);
        }

        baseBeanDao.update(payBackupBill);
    }

    /**
     *
     * 关门
     *
     * @param posNum
     */
    public void pushCloseDoor(String posNum, String door) {
        System.out.println("推送到终端关门posNum" + posNum);
        try {
            MqttService.getInstance().closeRelay(Integer.parseInt(door), posNum);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 推送到终端
     *
     * @param posNum
     */
    public void pushOpenDoor(String posNum, String door) {
        System.out.println("推送到终端开门posNum" + posNum);
        //等着推送开门，
        try {
            MqttService.getInstance().openRelay(Integer.parseInt(door), posNum);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * 推送到终端
     *
     * @param posNum
     */
    public void pushMessage(String posNum, String url) {
        System.out.println("推送到终端打开页面posNum" + posNum);
        try {
            MqttService.getInstance().openWeb(posNum, url);
            System.out.println("url:" + url);
            System.out.println("推送到终端");
            //basePath+"ea/sm/ea_getOpenSuc.jspa?posNum="+posNum+"&sccId="+sccId+"&loginMode=scan");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * 推送序列号
     *
     * @param posNum
     */
    public void pushSeq(String posNum, String seq) {

        try {
            MqttService.getInstance().pubSeq(posNum, seq);
            System.out.println("发送序列号" + seq);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }


    /**
     *
     * 推送语音
     *
     * @param posNum
     */
    public void pushAudio(String posNum, String audio) {
        try {
            MqttService.getInstance().pubAudio(posNum, audio);
            System.out.println("推送语音" + audio);
        } catch (MqttException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * 根据货柜查询posNum
     *
     * @param hgcode
     * @return
     */
    public String getPosNumByHg(String hgcode) {
        PosDevice posDevice = (PosDevice) baseBeanDao.getBeanByHqlAndParams("from PosDevice where hgcode = ?", new Object[]{hgcode});
        String posNum = "";
        if (posDevice != null) {

            posNum = posDevice.getPosNum();


        }
        return posNum;
    }


    /**
     * 自动贩卖机管理员登录
     *
     * @param hgcode
     * @param tEshopCustomer
     * @return
     */
    @Override
    public Map<String, Object> adminLogin(String hgcode, TEshopCustomer tEshopCustomer) {
        Map<String, Object> map = new HashMap<String, Object>();
        DepotManage d = (DepotManage) depotManageService.getDepotToCoding(hgcode, null, 1);
        if (d == null) {
            //return 0; //没有找到秤盘信息
            map.put("flag", "没有找到货柜信息");
        } else {
            // 查询组织机构是否存在且状态是否正常
            Company cc = (Company) baseBeanDao.getBeanByHqlAndParams("from Company where companyID = ?", new Object[]{d.getCompanyID()});
            if (cc == null) {
                //return 1; //企业不存在
                map.put("flag", "贩卖机绑定的企业不存在");
            } else if ("01".equals(cc.getCompanyStatus())) {
                //return 2;//该企业已停用
                map.put("flag", "贩卖机绑定的企业已停用");
            } else {
                map.put("company", cc);
                //查询登陆名是否存在
                TEshopCustomer tecustorm = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0 and password = ?", new Object[]{tEshopCustomer.getAccount(), tEshopCustomer.getPassword()});
                if (tecustorm == null) {
                    //return 3;//用户名或者密码不正确
                    map.put("flag", "用户名或者密码不正确");
                } else {
                    TEshopCusCom t = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom c where c.staffid=? and c.account=?", new Object[]{tecustorm.getStaffid(), tecustorm.getAccount()});
                    if (t == null) {
                        map.put("flag", "权限获取失败");
                    }
                    //查询用户的角色看是否是自动贩卖机操作员
                    //CRole role = (CRole) baseBeanDao.getBeanByHqlAndParams("from CRole c where exists (from StaffRole s where s.roleId=c.roleID and s.companyId=? and s.staffId=?)", new Object[]{cc.getCompanyID(), tecustorm.getStaffid()});
                    StringBuilder b = new StringBuilder();
                    b.append("SELECT C.ROLENAME FROM DTCROLE C WHERE EXISTS");
                    b.append(" (SELECT 1 FROM DT_HR_STAFF_ROLE S WHERE S.ROLE_ID = C.ROLEID");
                    b.append(" AND S.COMPANY_ID = ? AND S.STAFF_ID IN ");
                    b.append(" (SELECT STAFF.STAFFID FROM DTCOS COS, T_ESHOP_CUSCOM CUS, DT_HR_STAFF STAFF");
                    b.append(" WHERE STAFF.STAFFID = COS.STAFFID AND STAFF.REFERENCE = CUS.ACCOUNT");
                    b.append(" AND COS.STATUS = ? AND COS.COSSTATUS = ? AND CUS.SCCID = ? AND COS.COMPANYID = ?");
                    b.append(" UNION SELECT CUS.STAFFID FROM T_ESHOP_CUSCOM CUS WHERE CUS.SCCID = ? AND CUS.COMPANYID = ?");
                    b.append(" UNION SELECT CUS.STAFFID FROM T_ESHOP_CUSCOM_COMPANY CUSC LEFT JOIN T_ESHOP_CUSCOM CUS ON CUS.SCCID = CUSC.SCCID");
                    b.append(" WHERE CUSC.SCCID = ? AND CUSC.COMPANYID = ?))");
                    List<Object> p = new ArrayList<>();
                    p.add(cc.getCompanyID());
                    p.add("01");
                    p.add("50");
                    p.add(t.getSccId());
                    p.add(cc.getCompanyID());
                    p.add(t.getSccId());
                    p.add(cc.getCompanyID());
                    p.add(t.getSccId());
                    p.add(cc.getCompanyID());
                    List<Object> o = baseBeanDao.getListObjectBySqlAndParams(b.toString(), p.toArray());
                    if (o == null || o.size() <= 0) {
                        map.put("flag", "您没有权限登陆自动贩卖货柜");
                    } else {
                        for (int i = 0; i < o.size(); i++) {
                            String a = o.get(i).toString();
                            if (a != null && a.equals("自动贩卖机操作员")) {
                                //如果正常登陆 生成VendingMachineAdminLogoned
                                String uuid = UUID.randomUUID().toString();
                                tecustorm.setVendingMachineAdminLogoned(uuid);
                                tEshopCustomer.setVendingMachineAdminLogoned(uuid);
                                tEshopCustomer.setStaffid(tecustorm.getStaffid());
                                baseBeanDao.update(tecustorm);
                                map.put("tEshopCustomer", tEshopCustomer);
                                map.put("flag", "登录成功");
                                break;
                            } else {
                                //return 4;//您没有权限登陆自动贩卖货柜
                                map.put("flag", "您没有权限登陆自动贩卖货柜");
                                continue;
                            }
                        }
                    }
                }
            }
        }
        return map;
    }


    /**
     * 验证贩卖机管理员账号是否被其他用户登录 过
     *
     * @param user
     * @param loginGuid
     * @return
     */
    public String validateAdminLogin(String user, String loginGuid) {

        //查询登陆名是否存在
        TEshopCustomer t = (TEshopCustomer) baseBeanDao.getBeanByHqlAndParams("from TEshopCustomer where account = ? and loginGuid = ?", new Object[]{user, loginGuid});
        if (t == null) {
            return "0";
        }
        return "1";

    }


    /**
     *
     * 保存结果
     *
     * @param sccId
     * @param journalNum
     * @return
     */
    public String addResult(String sccId, String journalNum, String hgCode, String hrId) {

        HgResult hgResult = (HgResult) baseBeanDao.getBeanByHqlAndParams("from HgResult where hrId = ? and sccId = ? and hgCode = ?", new Object[]{hrId, sccId, hgCode});
        if (hgResult != null) {
            hgResult.setJournalNum(journalNum);
            hgResult.setCreateDate(new Date());
            baseBeanDao.update(hgResult);

        } else {
            //为空
            hgResult = new HgResult();
            hgResult.setHrId(serverService.getServerID("hrid"));
            hgResult.setJournalNum(journalNum);
            hgResult.setCreateDate(new Date());
            hgResult.setHgCode(hgCode);
            hgResult.setSccId(sccId);
            baseBeanDao.save(hgResult);


        }

        return hgResult.getHrId();

    }

    /**
     *
     * 查询ID
     *
     * @param sccId
     * @param hgCode
     * @return
     */
    public String getHgResultHrId(String sccId, String hgCode) {
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams("from HgResult where sccId = ? and hgCode = ? order by createDate desc", new Object[]{sccId, hgCode});
        String hrId = "";
        if (list.size() > 0) {
            HgResult hgResult = (HgResult) list.get(0);
            hrId = hgResult.getHrId();
        }

        return hrId;

    }

    /**
     *
     * 获取结果
     *
     * @param sccId
     * @param hgCode
     * @return
     */
    public String getHgResult(String sccId, String hgCode, String hrId) {
        String hql = "from HgResult where sccId = ? and hgCode = ? and hrId = ?";

        HgResult hgResult = (HgResult) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{sccId, hgCode, hrId});
        HgRelateUser hgRelateUser = getHgUser(hgCode);
        String journalNum = "";
        String cashid = "";

        if (hgRelateUser != null) {
            //说明没关门 就还在选购中
            cashid = "04";

        } else {


            if (hgResult != null) {
                journalNum = hgResult.getJournalNum();
                if (journalNum != null && !journalNum.equals("")) {
                    CashierBills cashierBills = (CashierBills) baseBeanDao.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});

                    if (cashierBills != null) {
                        cashid = cashierBills.getCashierBillsID();

                    } else {
                        //如果等于空订单

                        cashid = "04";

                    }
                } else {

                    cashid = "03";

                }
            }
        }


        return cashid;
    }

}

