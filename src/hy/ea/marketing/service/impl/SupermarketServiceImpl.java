package hy.ea.marketing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiantai.wfj.bo.PosDevice;
import com.tiantai.wfj.bo.SqSelfCart;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.CartShopPromotion;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.marketing.service.SupermarketSerivce;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 超市自助收银
 *
 * @author [mz]
 * @version [1.0, 2019-01-18]
 * @see
 * @since
 */
@Service

public class SupermarketServiceImpl implements SupermarketSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;

    private Logger logger = Logger.getLogger(SupermarketServiceImpl.class);

    /**
     * 社区加入购物车
     *
     * @param pid      产品ID
     * @param stardard 规格
     * @param itemNum  数量
     * @param posNum   社区机器编码
     * @return
     */
    @Override
    @Transactional
    public String sqJoinCart(String pid, String stardard, String itemNum, String sendNum, String posNum, String barCode, String showNum, String sgrId, String relateID) {
        ProductPackaging pp = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams("from ProductPackaging where ppID = ?", new Object[]{pid});
        boolean bool = false;//判断是否是商城加入购物车
        if (barCode == null || barCode.equals("null")) {
            barCode = pp.getBarCode();
            bool = true;//商城加入购物车
        }
        String result = "";
        try {
            PosDevice pos = (PosDevice) baseBeanDao.getBeanByHqlAndParams("from PosDevice where posNum = ?", new Object[]{posNum});
            List<Object> param = new ArrayList<Object>();
            param.add(posNum);
            param.add(pid);
            param.add(stardard);
            String sqhrl = "";
            if (barCode != null) {
                param.add(barCode);
                sqhrl = "from SqSelfCart where posNum = ? and pid = ? and stardard = ? and barCode = ?";
//                if (barCode.length() == 13 && barCode.substring(0, 2).equals("21")) {
//                    sqhrl += " and barCode is null";
//                }
            } else {
                sqhrl = "from SqSelfCart where posNum = ? and pid = ? and stardard = ?";
            }

            SqSelfCart cat = (SqSelfCart) baseBeanService.getBeanByHqlAndParams(sqhrl, param.toArray());
            List<BaseBean> beans = new ArrayList<BaseBean>();
            if (cat == null) {
                cat = new SqSelfCart();
                cat.setSqId(serverService.getServerID("sqcart"));
                cat.setCompanyId(pp.getCompanyID());
                cat.setItemNum(itemNum);
                cat.setPid(pid);
                cat.setStardard(stardard);//规格
                cat.setPosNum(posNum);//规格
                cat.setBarCode(barCode);
                cat.setRelateID(relateID);
                cat.setSgrId(sgrId);
                cat.setAddDate(new Date());
                if (sendNum != null && !sendNum.equals("")) {
                    cat.setSendNum(sendNum);
                } else {
                    cat.setSendNum("0");
                }

                if (showNum != null && !showNum.equals("")) {
                    cat.setShowNum(showNum);
                } else {
                    cat.setShowNum(itemNum);
                }
                if (pos != null) {
                    cat.setComID(pos.getComID());
                    if (pos.getFhform() != null && !pos.getFhform().equals("2")) {
                        if (sendNum == null || sendNum.equals("")) {
                            cat.setSendNum(itemNum);
                        } else if ("0".equals(sendNum)) {
                            cat.setSendNum("0");
                        }
                    }

                }

                result = cat.getSqId();


            } else {
                if (!barCode.substring(0, 2).equals("21")) {
                    cat.setItemNum(Float.parseFloat(itemNum) + Float.parseFloat(cat.getItemNum()) + "");
                }


                if (pos != null && pos.getFhform() != null && !pos.getFhform().equals("2")) {
                    if (bool) {
                        cat.setSendNum(Integer.parseInt(itemNum) + Integer.parseInt(cat.getSendNum()) + "");
                        cat.setShowNum(Integer.parseInt(itemNum) + Integer.parseInt(cat.getShowNum()) + "");
                    } else {
                        if (barCode.substring(0, 2).equals("21")) {
                            cat.setSendNum(sendNum);
                            cat.setShowNum(showNum);
                        }else {
                            if (sendNum != null && !sendNum.equals("")) {
                                cat.setSendNum(Integer.parseInt(sendNum) + Integer.parseInt(cat.getSendNum()) + "");
                            }
                            if (showNum != null && !showNum.equals("")) {
                                cat.setShowNum(Integer.parseInt(showNum) + Integer.parseInt(cat.getShowNum()) + "");//改过原来直接等于shownum
                            }
                        }
                    }
                } else {
                    if (showNum != null && !showNum.equals("")) {
                        cat.setShowNum(showNum);
                    } else {
                        cat.setShowNum(Math.round(Float.parseFloat(cat.getItemNum())) + "");
                    }
                }
                result = "-1";

            }
            beans.add(cat);
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);

        } catch (Exception e) {
            logger.error("操作异常", e);
            result = "0";
        }


        return result;
    }

    /**
     * 社区减少购物车
     *
     * @param pid
     * @param stardard
     * @param itemNum
     * @param posNum
     * @return
     */
    @Override
    @Transactional
    public String sqReduceCart(String pid, String stardard, String itemNum, String sendNum, String posNum, String barCode, String showNum) {
        ProductPackaging pp = (ProductPackaging) baseBeanDao.getBeanByHqlAndParams("from ProductPackaging where ppID = ?", new Object[]{pid});
        boolean bool = false;//判断是否是商城加入购物车
        if (barCode == null || barCode.equals("null")) {
            barCode = pp.getBarCode();
            bool = true;//商城加入购物车
        }
        String result = "";
        try {
            SqSelfCart cat = null;
            if (barCode != null) {
                cat = (SqSelfCart) baseBeanService.getBeanByHqlAndParams("from SqSelfCart where posNum = ? and pid = ? and stardard = ? and barCode = ?", new Object[]{
                        posNum, pid, stardard, barCode});
            } else {
                cat = (SqSelfCart) baseBeanService.getBeanByHqlAndParams("from SqSelfCart where posNum = ? and pid = ? and stardard = ?", new Object[]{
                        posNum, pid, stardard});
            }
            List<BaseBean> beans = new ArrayList<BaseBean>();
            if (cat != null) {
                cat.setItemNum(Float.parseFloat(cat.getItemNum()) - Float.parseFloat(itemNum) + "");
                if (cat.getShowNum() != null) {
                    cat.setShowNum(Integer.parseInt(cat.getShowNum()) - Integer.parseInt(itemNum) + "");
                }
                if (showNum != null && !showNum.equals("")) {
                    cat.setShowNum(showNum);
                }

                if (bool) {
                    if (cat.getSendNum() != null) {
                        cat.setSendNum(Integer.parseInt(cat.getSendNum()) - Integer.parseInt(itemNum) + "");
                    }
                } else {
                    if (sendNum != null && !sendNum.equals("")) {
                        cat.setSendNum(Integer.parseInt(cat.getSendNum()) - Integer.parseInt(sendNum) + "");
                    }
                }


                if (cat.getItemNum().equals("0") || cat.getItemNum().equals("0.0")) {
                    baseBeanDao.deleteBeanByKey(SqSelfCart.class, cat.getSqKey());
                } else {
                    beans.add(cat);
                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, null, null);

                }

            }

            result = "1";
        } catch (Exception e) {
            logger.error("操作异常", e);
            result = "0";
        }
        return result;
    }


    /**
     *
     * 删除购物车商品
     *
     * @param pid
     * @param stardard
     * @param posNum
     * @param barCode
     */
    @Override
    @Transactional
    public void deleteCartGoods(String pid, String stardard, String posNum, String barCode) {
        List<BaseBean> catlist = null;
        try {
            if (barCode != null && !barCode.equals("") && !barCode.equals("null")) {
                catlist = baseBeanService.getListBeanByHqlAndParams("from SqSelfCart where posNum = ? and pid = ? and stardard = ? and barCode = ?", new Object[]{
                        posNum, pid, stardard, barCode});
            } else {
                catlist = baseBeanService.getListBeanByHqlAndParams("from SqSelfCart where posNum = ? and pid = ? and stardard = ?", new Object[]{
                        posNum, pid, stardard});
            }
            if (catlist.size() > 0) {
                SqSelfCart cat = (SqSelfCart) catlist.get(0);
                baseBeanDao.deleteBeanByKey(SqSelfCart.class, cat.getSqKey());
            }


        } catch (Exception e) {
            logger.error("操作异常", e);

        }
    }

    /**
     * 社区购物车商品数量
     *
     * @param posNum
     * @return
     */
    public Object sqTotalNumCart(String posNum, String ccompanyID) {
        Object obj = null;
        try {
            String sql = "select nvl(sum(c.showNum),0),nvl(max(c.relateID),1) from dt_sqselfcart c,dt_ProductPackaging  p where p.ppID = c.pid and c.posNum = ? and p.showweixin = ? and p.companyid = (select cc.compnay_id from Dt_Ccom_Com cc where cc.ccompany_id= ?) and c.hgCode is null";
            obj = baseBeanDao.getObjectBySqlAndParams(sql, new Object[]{posNum, "01", ccompanyID});
        } catch (Exception e) {
            obj = null;
        }

        return obj;
    }

    /**
     * 社区购物车商品数量
     *
     * @param posNum
     * @return
     */
    public Object sqPidTotalNumCart(String posNum, String ppid) {
        Object obj = null;
        try {
            String sql = "select nvl(sum(c.itemNum),0),nvl(max(c.sgrId),'1') from dt_sqselfcart c,dt_ProductPackaging  p where p.ppID = c.pid and c.posNum = ? " +
                    "and p.showweixin = ? and  p.ppID = ? and c.stardard = '默认规格' ";
            obj = baseBeanDao.getObjectBySqlAndParams(sql, new Object[]{posNum, "01", ppid});
            //    logger.info("获取购物车商品数量" + goodNum);
        } catch (Exception e) {
            obj = null;
        }
        return obj;

    }


    /**
     * 获取购物车
     *
     * @param posNum 社区机器编码
     * @return
     */
    @Override
    public List<BaseBean> getSqCartList(String posNum, String ccompanyID, int lxType,String fhform) {

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());
        StringBuilder sql = new StringBuilder();


        sql.append("select ps.ppid, p.goodsname,");
        sql.append("case when pc.re_price is not null then ROUND(pc.re_price * (1 + nvl(sz.total_pct, 0) / 100), 2) when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2)");
        sql.append(" when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price,");
        sql.append("c.itemNum,c.barcode,p.companyId,c.stardard,c.sendNum,g.quantity,g.spcation,");
        sql.append("case when pc.re_price is not null then '5' when tpa.type is not null then '4' when pa.type is not null then '3' else '0' end pricetype,");
        sql.append("case when pc.ef_price is not null then to_number(pc.ef_price) when tpa.type is not null then to_number(tpap.factory)");
        sql.append(" when pa.type is not null then to_number(pap.factory) else to_number(ps.ef_price) end costmoney,");
        sql.append("case when pc.pcid is not null then to_char(pc.pcid) when tpa.type is not null then to_char(tpap.actPriceId)");
        sql.append(" when pa.type is not null then to_char(pap.actPriceId) else ps.suid end activityID");
        sql.append(",c.showNum,c.sgrId,c.relateID,p.variableID,c.deptId,c.deptName");
        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid");
        sql.append(" inner join Dt_Sqselfcart c on p.ppid = c.pid");
        //变价记录
        sql.append(" left join dt_scangoodsrecord sgc on c.sgrid=sgc.sgrid");
        sql.append(" left join dt_pricechange pc on sgc.sgrid=pc.sqid");
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

        sql.append(" left join Dtgoodsbarcode g on p.goodsid = g.goodsid and c.barCode = g.barcode and g.status='01'");
        sql.append(" where ps.fcom_id is null  and c.posnum = ?  and p.showweixin = ? and  cm.ccompanyid = ?");

        if(!"3".equals(fhform)){
            sql.append(" and c.hgCode is null");

        }else{
            sql.append(" and c.hgCode is not null");
        }
        sql.append(" order by c.addDate asc");

        Object[] objects = new Object[]{currentTime, currentTime, currentTime, currentTime, posNum, "01", ccompanyID};
        //如果类型为1，sql语句变为查询批发商城信息
        if (lxType == 1) {//查询类型，0：其他入口进入1：批发商城进入
            getPfscShoppingCarSql(sql);
            objects = new Object[]{posNum, "01", ccompanyID};
        }
        List<BaseBean> list = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),objects);
        return list;
    }

    /**
     * 拼接批发商城sql语句
     *
     * @return
     */
    private void getPfscShoppingCarSql(StringBuilder sql) {
        sql.delete(0,sql.length());
        sql.append(" select ps.ppid, p.goodsname,");
        sql.append(" case when pc.re_price is not null then ROUND(pc.re_price * (1 + nvl(sz.total_pct, 0) / 100), 2) ");
        sql.append(" else ROUND(dpw.wholesale * (1 + nvl(sz.total_pct, 0) / 100), 2) end price, ");
        sql.append(" c.itemNum,c.barcode,p.companyId,c.stardard,c.sendNum,g.quantity,g.spcation, ");
        sql.append(" case when pc.re_price is not null then '5' else '1' end pricetype, ");
        sql.append(" case when pc.ef_price is not null then to_number(pc.ef_price) else to_number(dpw.wholesale) end costmoney, ");
        sql.append(" case when pc.pcid is not null then to_char(pc.pcid) else to_char(dpw.wholesaleId) end activityID,");
        sql.append(" c.showNum,c.sgrId,c.relateID ");
        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
        sql.append(" inner join Dt_Sqselfcart c on p.ppid = c.pid ");
        //变价记录
        sql.append(" left join dt_scangoodsrecord sgc on c.sgrid=sgc.sgrid");
        sql.append(" left join dt_pricechange pc on sgc.sgrid=pc.sqid");
        sql.append(" left join dt_pro_wholesale dpw on p.ppid = dpw.ppid and dpw.state = '00' ");
        sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append(" left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append(" left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append(" left join Dtgoodsbarcode g on p.goodsid = g.goodsid and c.barCode = g.barcode and g.status='01' ");
        sql.append(" where ps.fcom_id is null  and c.posnum = ? and p.showweixin = ? and  cm.ccompanyid = ? ");
    }

    /**
     * 清空公司购物车
     *
     * @param posNum 社区机器编码
     * @return
     */
    @Override
    @Transactional
    public void clearSqCart(String posNum) {

        String hqldelete = "delete from  SqSelfCart where posNum = ?";
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(null, new String[]{hqldelete}, new Object[]{posNum});

    }


    /**
     * 存储超市入口公司
     *
     * @param posNum
     * @return
     */
    @Override
    @Transactional
    public void saveAccessCComID(String posNum, String ccompanyID) {

        String hql = "from PosDevice where posNum = ?";
        PosDevice posDevice = (PosDevice) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{posNum});
        if (posDevice != null) {
            if (posDevice.getAccessCcomID() == null || !posDevice.getAccessCcomID().equals(ccompanyID)) {
                posDevice.setAccessCcomID(ccompanyID);

                baseBeanDao.update(posDevice);
            }
        }

    }

    /**
     * 根据机器编号获取入口公司用于返回页面
     *
     * @param posNum
     * @return
     */
    public Map<String, Object> getAccessCompany(String posNum) {
        String hqlpos = "from PosDevice where posNum = ?";
        PosDevice pos = (PosDevice) baseBeanDao.getBeanByHqlAndParams(hqlpos, new Object[]{posNum});
        Map<String, Object> map = new HashMap<String, Object>();

        if (pos != null && pos.getAccessCcomID() != null && !pos.getAccessCcomID().equals("")) {
            String hql = "from ContactCompany c where c.ccompanyID = ? ";
            ContactCompany cc = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{pos.getAccessCcomID()});
            if (cc != null) {
                map.put("ccompanyID", cc.getCcompanyID());
                map.put("industryType", cc.getIndustryType());
                map.put("companyName", cc.getCompanyName());
                map.put("postype", pos.getPosName());
                map.put("app", pos.getApp());
                map.put("result", "SUCCESS");
            }
        } else {

            map.put("result", "FAIL");
        }

        return map;
    }

    /**
     * 返回默认网址
     * @param posNum
     * @return
     */
    public String getAccessUrl(String posNum) {
        String url = "/ea/wfjshop/ea_getWFJshops.jspa";
        String hqlpos = "from PosDevice where posNum = ?";
        PosDevice pos = (PosDevice) baseBeanDao.getBeanByHqlAndParams(hqlpos, new Object[]{posNum});
        Map<String, Object> map = new HashMap<String, Object>();

        if (pos != null && pos.getAccessCcomID() != null && !pos.getAccessCcomID().equals("")) {
            String hql = "from ContactCompany c where c.ccompanyID = ? ";
            ContactCompany cc = (ContactCompany) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{pos.getAccessCcomID()});


            String industryType = cc.getIndustryType();
            String ccompanyID = cc.getCcompanyID();
            String companyName = cc.getCompanyName();
            String postype = pos.getPosName();
            String app = pos.getApp();


            if("05".equals(app)){//货柜
                url = "page/ea/main/marketing/supermarket/container/hgmain.jsp";
            }else if("01".equals(app)){//考场练车
                    url = "ea/mappointment/ea_theTestTime.jspa?sccId=&posNum="+posNum;
            }else if("02".equals(app)){//停车收费
                url = "ea/qrshare/ea_jumpManagement.jspa?posNum="+posNum;
             }else if("03".equals(app)){
                if (industryType != null && !industryType.equals("")) {
                    if (industryType.indexOf("超级市场") != -1) {
                        url = "ea/smg/sm_toSupermarketGoods.jspa?ccompanyID=" + ccompanyID + "&industryType=" + industryType + "&companyName=" + companyName + "&posNum=" + posNum;

                    } else if (industryType.indexOf("餐饮") != -1) {
                        url = "ea/industry/ea_CompanyProducts.jspa?ccompanyId=" + ccompanyID + "&pos=&posNum=" + posNum + "&postype=" + postype;
                    } else {
                        url = "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccompanyID + "&industryType=" + industryType + "&etype=";
                    }
                } else {
                    url = "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccompanyID + "&industryType=" + industryType + "&etype=";
                }
            }else if("04".equals(app)){//考勤签到
                url = "/ea/bonuspoints/ea_getCurrCompany.jspa?posNum="+posNum;
            }else if("05".equals(app)){
                url = "page/ea/main/marketing/supermarket/container/hgmain.jsp";
            }


        }

        return url;

    }

    /**
     * 根据机器编号是否是终端机
     * @param posNum
     * @return
     */
    public Map<String, Object> isExitPosNum(String posNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (posNum != null && !posNum.equals("")) {
            String hql = "from PosDevice p where p.posNum = ?";
            PosDevice p = (PosDevice) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{posNum});
            if (p == null) {
                map.put("result", "1");//没有这个设别
                map.put("postype", "00");//没有这个设别

            } else {
                map.put("result", "0");//有这个设备
                map.put("postype", p.getPosName());
                map.put("hgcode", p.getHgcode());
            }
        } else {
            map.put("result", "1");//没有这个设别
            map.put("postype", "00");//没有这个设别
        }

        return map;
    }

    /**
     * 选择促销品存储
     */
    @Override
    @Transactional
    public void joinCxp(String ptppid, String ptstandard, String ppid, String posNum, String cartid) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        if (ptppid != null && !ptppid.equals("")) {
            String[] ptppids = ptppid.split(",");
            String[] ptstandards = ptstandard.split(",,");
            CartShopPromotion csp = null;
            for (int i = 0; i < ptppids.length; i++) {
                csp = new CartShopPromotion();
                csp.setCspId(serverService.getServerID("cspId"));
                csp.setPpId(ppid);
                csp.setPptId(ptppids[i]);
                csp.setPtstandard(ptstandards[i]);
                csp.setPtCount(1);
                csp.setCartId(cartid);
                csp.setPosNum(posNum);
                beans.add(csp);
            }
        }
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    }

}
