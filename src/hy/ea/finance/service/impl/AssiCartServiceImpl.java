package hy.ea.finance.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.wechat.bo.TemplateMsg;
import com.wechat.bo.TemplateMsgResult;
import com.wechat.utils.ConstantURL;
import com.wechat.utils.WeixinUtil;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.*;
import hy.ea.bo.finance.BenDis.DtOrderBillAdd;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.Cater;
import hy.ea.finance.service.AssiCartService;
import hy.ea.util.MobileMessage;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.util.JushMain;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AssiCartServiceImpl
        implements AssiCartService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService idgec;
    @Autowired
    private MobileMessage msage;
    @Resource
    private BaseBeanService baseBeanService;


    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;



    private Logger logger = LoggerFactory.getLogger(AssiCartServiceImpl.class);


    /**
     *   根据sccid获取人的公司
     * @param sccid
     * @return
     */
    public List<BaseBean> getListCompanyBySccid(String  sccid){
        List<BaseBean> list = new ArrayList<BaseBean>();
        TEshopCusCom tc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",new Object[]{sccid});

        if(tc!=null){
          if(tc.getCompanyId()!=null&&!tc.getCompanyId().equals("")){
             ContactCompany ccompany = (ContactCompany)baseBeanDao.getBeanByHqlAndParams("from ContactCompany where ccompanyID = (select c.ccompanyId from CcomCom c where c.comanyId = ?)",new Object[]{tc.getCompanyId()});
             list.add(ccompany);
          }
        }
        List<BaseBean> companylist = null;
        String hql = "from ContactCompany  m where m.ccompanyID in(select c.ccompanyId from CcomCom c where c.comanyId in (select cc.companyID from COS cc where cc.staffID = ? and cc.cosStatus = ? and cc.companyID != ?))";
        

        companylist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{tc.getStaffid(),"50",(tc.getCompanyId()!=null&&!tc.getCompanyId().equals(""))?tc.getCompanyId():"1"});


        if(companylist.size()==0){
            Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where sccid = ?",new Object[]{tc.getSccId()});
            if(staff!=null){

                companylist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{staff.getStaffID(),"50",(tc.getCompanyId()!=null&&!tc.getCompanyId().equals(""))?tc.getCompanyId():"1"});
            }
        }
        if(companylist.size()!=0) {
            list.addAll(companylist);
        }

        return  list;
    }


    /**
     *   根据sccid获取人的公司
     * @param sccid
     * @return
     */
    public List<String> getCompanyIDBySccid(String  sccid){
        List<String> list = new ArrayList<String>();
        TEshopCusCom tc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",new Object[]{sccid});

        if(tc!=null){
            if(tc.getCompanyId()!=null&&!tc.getCompanyId().equals("")){

                list.add(tc.getCompanyId());
            }
        }
        String hql = "from COS cc where cc.staffID = ? and cc.cosStatus = ? and cc.companyID != ?";


        List<BaseBean> coslist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{tc.getStaffid(),"50",(tc.getCompanyId()!=null&&!tc.getCompanyId().equals(""))?tc.getCompanyId():"1"});


        if(coslist.size()==0){
            Staff staff = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where sccid = ?",new Object[]{tc.getSccId()});
            if(staff!=null){

                coslist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{staff.getStaffID(),"50",(tc.getCompanyId()!=null&&!tc.getCompanyId().equals(""))?tc.getCompanyId():"1"});
            }
        }
        if(coslist.size()!=0) {
            COS cos = null;
           for (int i = 0;i<coslist.size();i++){
               cos = (COS)coslist.get(i);
               if(cos.getCompanyID()!=null&&!cos.getCompanyID().equals("")){
                   if(!list.contains(cos.getCompanyID())){
                         list.add(cos.getCompanyID());
                   }
               }

           }
        }

        return  list;
    }

    /**
     *
     * 获取购物车产品
     * @param staffId
     * @return
     */
    public List<Object>  getCartList(String staffId,String companyID){
        StringBuilder sql = new StringBuilder();
        sql.append("select p.goodsname,ps.re_price,c.itemNum,c.stardard");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,DT_cart c");
        sql.append(" where  p.ppid=ps.ppid and p.ppid = c.pid");
        sql.append(" and ps.fcom_id is null and c.staff_id = ? and p.showweixin = ? and c.pos = ? and c.companyID = ?");

        List<Object> beanList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
                new Object[] {staffId,"01","1",companyID});

        return beanList;

    }

    /***
     * 计算总金额
     * @param list
     * @return
     */
    public float getTotalMoney(List<Object>  list){
        float money = 0;
        if(list.size()!=0){
            for (int i=0;i<list.size();i++){
                Object obj = list.get(i);
                Object[] objs = (Object[])obj;
                money=money+Float.parseFloat(objs[1].toString())*Float.parseFloat(objs[2].toString());
            }
        }
         return money;

    }

    /**
     *
     * 获取待结算订单明细
     * @param coID
     * @return
     */
    public List<Object>  getClientGoods(String coID){
        String sql = "select g.goodsName,g.price,g.quantity from dtClientOrderGoods g where g.coID = ?";


        List<Object> beanList = baseBeanService.getListBeanBySqlAndParams(sql,
                new Object[] {coID});

        return beanList;

    }



    /**
     *
     * 根据公司ID查询往来单位
     * @param companyId
     * @return
     */
    public ContactCompany  getContactCompany(String companyId){

        ContactCompany  contactCompany = (ContactCompany)baseBeanDao.getBeanByHqlAndParams("from ContactCompany cc where cc.ccompanyID = (select c.ccompanyId from CcomCom c where c.comanyId = ?)",new Object[]{companyId});

        return contactCompany;

    }

    /**
     *
     * 根据支付凭证号查询回调需要的参数
     * @param journalNum
     * @return
     */
    public PayBackupBill getPayBakupByJum(String journalNum){

        PayBackupBill payBackupBill =(PayBackupBill)baseBeanDao.getBeanByHqlAndParams("from PayBackupBill where journalNum = ?",new Object[]{journalNum});
       return payBackupBill;
    }

    /**
     *
     * 订单详情
     * @param coID
     * @return
     */
    public Map<String,Object> getOrderDetail(String coID){

        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("select r.coID,r.orderNum,r.boardNo,r.companyID,cc.logopath,cc.companyName,trunc(r.totalMoney, 2),r.state,r.balanceName,r.balanceDate,r.caterID,cc.ccompanyID,r.staffName from dtClientOrder r,dtContactCompany cc,DT_ccom_com mm");
        sql.append(" where r.companyID = mm.compnay_id and cc.ccompanyID = mm.ccompany_Id and r.coID = ?");
        Object obj = baseBeanDao.getObjectBySqlAndParams(sql.toString(),new Object[]{coID});
        Object[] objs = (Object[])obj;

        map.put("state",(objs==null||objs[7]==null)?"":objs[7].toString());

        map.put("clientOrder",obj);

        String sqld = "select r.codID,r.waiterID,r.orderDate,r.remark,f.headimage,f.staffName,r.eatType from dtClientOrderDetail r,dt_hr_staff f where r.coID = ? and f.staffID = r.waiterID order by r.orderDate desc";
        List<BaseBean> list = baseBeanDao.getListBeanBySqlAndParams(sqld,new Object[]{coID});
        map.put("clientDetail",list);

        String hql = "from ClientOrderGoods where coID = ? order by price";
        List<BaseBean> goodlist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{coID});

        map.put("clientGoods",goodlist);
        return map;


    }

    /**
     *
     * 生成订单
     * @param staffid
     * @param companyId
     * @param cater
     * @param codeID
     * @param remark
     */
    @Override
    @Transactional
    public void genClientOrder(String staffid, String companyId, Cater cater, String codeID, String remark,String eatType) {
        StringBuilder sql =new StringBuilder();
        sql.append("select sum(ps.re_price*c.itemNum)");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,DT_cart c");
        sql.append(" where  p.ppid=ps.ppid and p.ppid = c.pid");
        sql.append(" and ps.fcom_id is null and c.staff_id = ? and p.showweixin = ? and c.pos = ? and c.companyID = ?");
        Object jo = baseBeanDao.getObjectBySqlAndParams(sql.toString(),new Object[]{staffid,"01","1",companyId});
        Staff staff =  (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{staffid});
        List<BaseBean> baseBeanList= new ArrayList<BaseBean>();
        //第一个下单
        ClientOrder clientOrder = null;
        if(codeID==null||codeID.equals("")){
            clientOrder = new ClientOrder();
            clientOrder.setCoID(serverService.getServerID("coID"));
            clientOrder.setOrderNum(serverService.getBillID(companyId));
            clientOrder.setState("01");
            clientOrder.setCompanyID(companyId);
            clientOrder.setTotalMoney(jo!=null?jo.toString():"");
            clientOrder.setBoardNo(cater!=null?cater.getBoardNo():"");

            if(cater!=null&&(cater.getID()==null|| "".equals(cater.getID()))){
                Cater cc = (Cater) baseBeanDao.getBeanByHqlAndParams("from Cater where boardNo = ? and companyID = ?",new Object[]{cater.getBoardNo(),companyId});
                clientOrder.setCaterID(cc!=null?cc.getID():"");
            }else{
                clientOrder.setCaterID(cater!=null?cater.getID():"");

            }

            clientOrder.setCreateDate(new Date());
            baseBeanList.add(clientOrder);

        }else{
            String hql = "from ClientOrder where coID = ?";
            clientOrder = (ClientOrder)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{codeID});
            clientOrder.setTotalMoney(Float.parseFloat(clientOrder.getTotalMoney())+Float.parseFloat(jo.toString())+"");
            baseBeanList.add(clientOrder);

        }
            ClientOrderDetail ccdetail = new ClientOrderDetail();
            ccdetail.setCodID(serverService.getServerID("codID"));
            ccdetail.setCoID(clientOrder.getCoID());
            ccdetail.setOrderDate(new Date());
            ccdetail.setRemark(remark);
            ccdetail.setWaiterID(staffid);
            ccdetail.setWaiterName(staff.getStaffName());
            ccdetail.setEatType(eatType);//堂食，打包

            ccdetail.setSn(searchMealNum(companyId,baseBeanList));

            List<Object>  list = getDcCartList(staffid,companyId);
            float dm = 0;

            for (int i = 0;i<list.size();i++){
                Object obj = list.get(i);
                Object[] objs = (Object[])obj;
                ClientOrderGoods cogood = new ClientOrderGoods();
                cogood.setCogID(serverService.getServerID("cogID"));
                cogood.setCoID(clientOrder.getCoID());
                cogood.setCodID(ccdetail.getCodID());
                cogood.setGoodsName(objs[0]!=null?objs[0].toString():"");
                cogood.setPrice(objs[1].toString());
                cogood.setQuantity(objs[2].toString());
                cogood.setMoney(Float.parseFloat(objs[2].toString()) * Float.parseFloat(objs[1].toString()) + "");
                dm=dm+Float.parseFloat(cogood.getMoney());
                cogood.setPpid(objs[3].toString());
                cogood.setStandard(objs[4].toString());
                baseBeanList.add(cogood);

            }
            ccdetail.setDtotalMoney(dm+"");
            baseBeanList.add(ccdetail);

          String hql = "delete from Cart where staffId = ? and companyId = ?";
          baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList,new String[]{hql},new Object[]{staffid,companyId});
          //推送并打印小票
         String hqql = " from TEshopCusCom where companyId = ?";
         TEshopCusCom com = (TEshopCusCom) beandao.getObjectByHqlAndParams(hqql, new Object[]{companyId});
         getCaiYinXiangQing(clientOrder,ccdetail,list,ccdetail.getOrderDate(),com);
    }
    /**
     *
     *
     * @param staffId
     * @return
     */

     private  List<Object>  getDcCartList(String staffId,String companyID){
        StringBuilder sql = new StringBuilder();
        sql.append("select p.goodsname,ps.re_price,c.itemNum,p.ppID,c.stardard");
        sql.append(" from DT_PRO_SETUP ps,dt_ProductPackaging p,DT_cart c");
        sql.append(" where  p.ppid=ps.ppid and p.ppid = c.pid");
        sql.append(" and ps.fcom_id is null and c.staff_id = ? and p.showweixin = ? and c.pos = ? and c.companyID = ?");

        List<Object> beanList = baseBeanService.getListBeanBySqlAndParams(sql.toString(),
                new Object[] {staffId,"01","1",companyID});

        return beanList;

    }

    /**
     *
     * 查询餐桌
     * @param companyID
     * @return
     */
    public List<BaseBean> getCateList(String companyID){

      String hql = "from Cater where companyID = ?";
      List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{companyID});

      return list;
    }

    @Transactional
    public void genFiniClientOrder(String cashierBillsID,String coID) {
        String hql = "from CashierBills where cashierBillsID = ?";
        CashierBills cb = (CashierBills)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{cashierBillsID});

        Staff staff = null;
        if(cb.getWaiterID()!=null&&!cb.getWaiterID().equals("")) {
            staff = (Staff) baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{cb.getWaiterID()});
        }
        List<BaseBean> baseBeanList= new ArrayList<BaseBean>();
        if(coID!=null&&!coID.equals("")){
         String hqlcli = "from ClientOrder where coID = ?";
         ClientOrder order = (ClientOrder)baseBeanDao.getBeanByHqlAndParams(hqlcli,new Object[]{coID});
         order.setCashierBillsID(cashierBillsID);
         order.setState("00");
         order.setBalanceDate(new Date());
         order.setBalanceID(cb.getWaiterID());
         order.setBalanceName(staff.getStaffName());
         order.setStaffID(cb.getContactUserID());
         order.setStaffName(cb.getCtUserName());
         baseBeanList.add(order);
        }else {
            //第一个下单
            ClientOrder clientOrder = new ClientOrder();
            clientOrder.setCoID(serverService.getServerID("coID"));
            clientOrder.setOrderNum(serverService.getBillID(cb.getCompanyID()));
            clientOrder.setState("00");
            clientOrder.setCompanyID(cb.getCompanyID());

            clientOrder.setBoardNo("无");
            clientOrder.setCaterID("");
            clientOrder.setCreateDate(new Date());
            clientOrder.setBalanceDate(new Date());
            clientOrder.setBalanceID(cb.getWaiterID());
            clientOrder.setBalanceID(staff.getStaffName());
            clientOrder.setCashierBillsID(cashierBillsID);
            clientOrder.setStaffID(cb.getContactUserID());
            clientOrder.setStaffName(cb.getCtUserName());

            baseBeanList.add(clientOrder);


            ClientOrderDetail ccdetail = new ClientOrderDetail();
            ccdetail.setCodID(serverService.getServerID("codID"));
            ccdetail.setCoID(clientOrder.getCoID());
            ccdetail.setOrderDate(new Date());
            ccdetail.setRemark(cb.getRemark());
            ccdetail.setWaiterID(cb.getWaiterID());
            ccdetail.setWaiterName(staff != null ? staff.getStaffName() : "");
            baseBeanList.add(ccdetail);

            String hqlgood = "from GoodsBills where cashierBillsID = ?";
            List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hqlgood, new Object[]{cashierBillsID});
            float money = 0;
            DecimalFormat fnum = new DecimalFormat("##0.00");


            for (int i = 0; i < list.size(); i++) {
                GoodsBills goodsBills = (GoodsBills) list.get(i);
                ClientOrderGoods cogood = new ClientOrderGoods();
                cogood.setCogID(serverService.getServerID("cogID"));
                cogood.setCoID(clientOrder.getCoID());
                cogood.setCodID(ccdetail.getCodID());
                cogood.setGoodsName(goodsBills.getGoodsName());
                cogood.setPrice(goodsBills.getPrice());
                cogood.setQuantity(goodsBills.getQuantity());
                cogood.setMoney(Float.parseFloat(goodsBills.getQuantity()) * Float.parseFloat(goodsBills.getPrice()) + "");
                cogood.setPpid(goodsBills.getPpID());
                cogood.setStandard(goodsBills.getStandard());
                money = money + Float.parseFloat(cogood.getMoney());
                baseBeanList.add(cogood);

            }
            clientOrder.setTotalMoney(fnum.format(money));
        }

        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList,null,null);


    }


    private void zfMessage(TEshopCusCom cus,String content,String type,String body,String id) {
        List<String> slist = new ArrayList<String>();//极光推送设备号
        String sql = "select t.contactway  from dt_hr_staff_contact t where t.staffid= ? and t.contactType in (select e.codeID from dtCCode e where  e.codevalue= ? and e.companyid = ?)";
        List list = beandao.getListBeanBySqlAndParams(sql, new Object[]{cus.getStaffid(), "短信提醒",cus.getCompanyId()});
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
            msage.sendMsg("【微分金平台】");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //xgb
        logger.error("生成订单-----提醒人账号:" + cus.getAccount() + "------公司下员工账号:" + cellphoneMark);
        //保存账号
        String[] arr = cellphoneMark.split(",");
        slist = Arrays.asList(arr);
        //极光推送
        JushMain.sendjiguangMessage(content, type, body,id, slist);

    }

    //餐饮特别推送
    public void getCaiYinXiangQing(ClientOrder clientOrder,  ClientOrderDetail ccdetail,List<Object>  glist, Date nowDate,TEshopCusCom com){


            JSONObject json = new JSONObject();
            List<JSONObject> goodsList = new ArrayList<>();

            for (int i = 0; i < glist.size(); i++) {
                Object[] goodsBills = (Object[]) glist.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("shuliang", isNull(goodsBills[2]));
                jsonObject.accumulate("price", isNull(goodsBills[1]));
                String standard = (goodsBills[4] == null || goodsBills[4].toString().equals("") || goodsBills[4].toString().equals("默认规格")) ? "" : "(" +goodsBills[4].toString() + ")";
                jsonObject.accumulate("goodsname", goodsBills[0].toString() + standard);
                goodsList.add(jsonObject);
            }
            json.accumulate("TotalPrice", ccdetail.getDtotalMoney());
            json.accumulate("companyName", com.getPseudoCompanyName());
            json.accumulate("goodsList", goodsList);
            //按指定格式生成当前时间

            SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            json.accumulate("nowDate", dateFormats.format(nowDate));//付款时间
            json.accumulate("danhao", clientOrder.getOrderNum());//订单号
            json.accumulate("beizhu",isNull(ccdetail.getRemark()));
            json.accumulate("payType", "待结算");

            json.accumulate("mealNum", ccdetail.getSn());//取餐号
            json.accumulate("eatType", ccdetail.getEatType());//堂食，打包
            json.accumulate("zhuohao", clientOrder.getBoardNo());
            json.accumulate("waiter", ccdetail.getWaiterName());//点餐员



            System.out.println(json.toString());
            logger.error("body" + json.toString());

            zfMessage(com,"您有一笔新的微分金订单，请及时处理!","餐饮订单",json.toString(),"canyin");


    }
    /**
     *
     * 生成取餐顺序号
     * @param companyID
     * @param beanList
     * @return
     */
    private String searchMealNum(String companyID,List<BaseBean> beanList){
        String hql = "from MealNum where companyID = ?";
        MealNum mealNum = (MealNum)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{companyID});
        int num = 0;
        if(mealNum==null){
            num = num+1;
            mealNum = new MealNum();
            mealNum.setMnId(serverService.getServerID("mnId"));
            mealNum.setCompanyID(companyID);
            mealNum.setMealNum(num);
        }else{
            num = mealNum.getMealNum()+1;
            mealNum.setMealNum(num);
        }
        beanList.add(mealNum);

        return num+"";
    }

    private Object isNull(Object tep){
        if(tep==null||"null".equals(tep)){
            return "";
        }else{
            return tep;
        }
    }






}