package hy.ea.marketing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiantai.wfj.bo.*;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.finance.PayBackupBill;
import hy.ea.bo.human.Staff;
import hy.ea.marketing.service.PayFaceDeviceSerivce;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 刷脸设备管理
 *
 * @author [mz]
 * @version [1.0, 2019-11-15]
 * @see
 * @since
 */
@Service

public class PayFaceDeviceServiceImpl implements PayFaceDeviceSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private ServerService serverService;


    private Logger logger = Logger.getLogger(PayFaceDeviceServiceImpl.class);


    @Override
    @Transactional
    public void addOrUpdate(PayFaceDevice pos, String companyID, String staffID) {
        if (pos != null) {

                 String hql = "from Staff where staffID = ?";
                //录入人员
                 Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID});
                 pos.setInputID(staffID);
                 pos.setInputName(input.getStaffName());
                 pos.setCompanyID(companyID);

                pos.setCreateDate(new Date());
                if (pos.getPfdID() == null || pos.getPfdID().equals("")) {
                    pos.setPfdID(serverService.getServerID("pfdID"));
                    pos.setBindState("00");
                    pos.setActiveState("00");
                    pos.setTradeMoney("0");
                    pos.setRakeMoney("0");
                    baseBeanDao.update(pos);
                }else{
                  String hqls = "from PayFaceDevice where pfdID = ?";
                    PayFaceDevice pd = (PayFaceDevice) baseBeanDao.getBeanByHqlAndParams(hqls,new Object[]{pos.getPfdID()});
                    pd.setSn(pos.getSn());
                    pd.setModel(pos.getModel());
                    pd.setFactoryID(pos.getFactoryID());
                    pd.setFactoryName(pos.getFactoryName());
                    pd.setSource(pos.getSource());
                    pd.setStaffID(pos.getStaffID());
                    pd.setStaffName(pos.getStaffName());
                    baseBeanDao.update(pd);
                }


            }


    }

    @Override
    @Transactional
    public void delete(String pfdkey) {

        baseBeanDao.deleteBeanByKey(PayFaceDevice.class,pfdkey);

    }

    /**
     * 设备号全网唯一
     * @param sn
     * @return
     */
    public String checkRepPosNum(String sn,String pfdID){
        if(pfdID==null||pfdID.equals("")){
            pfdID= " ";
        }
        String hql = "from PayFaceDevice where sn = ? and pfdID!=?";
        List<BaseBean> poslist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{sn,pfdID});
        if(poslist.size()==0){

            return "0";
        }else{
            return "1";
        }

    }

    /**
     * 判断商户是否签约
     *
     * @param sbdID
     * @return
     */
    public String checkComPosNum(String subCompanyID, String sbdID){
        if(sbdID==null||sbdID.equals("")){
            sbdID = " ";
        }
        String hql = "from StoreBindDevice where subCompanyID = ? and sbdID!=?";
        List<BaseBean> poslist = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{subCompanyID,sbdID});
        if(poslist.size()==0){
            return "0";
        }else{
            return "1";
        }
    }

    /**
     * 验证设备是否录入到系统里，如果录入了，查询是否已经绑定过商户，如果绑定了则不能再绑定
     * @param sn
     * @return
     */
    public String checkSn(String sn){
          String hql = "from PayFaceDevice where sn = ?";
          PayFaceDevice pd = (PayFaceDevice)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{sn});
          if(pd==null){
              return "1";//系统无此设备号请录入
          }else if(pd.getClerkSccid()==null||pd.getClerkSccid().equals("")){
              return "2";//设备没有分配业务员
          }else{
            String hql1 = "from BindDevice where sn = ?";
              BindDevice sbd = (BindDevice)baseBeanDao.getBeanByHqlAndParams(hql1,new Object[]{sn});
              if(sbd!=null){
                  return "3";//该设备已经绑定过商户，不能绑定多个商户
              }

          }
          return  "0"; //可以绑定
    }

    /**
     * 设备绑定商户
     * @param sn
     * @param subCompanyID
     * @param staffID
     * @return
     */
    @Override
    @Transactional
    public void  storeBindDevice(String sn,String subCompanyID,String  staffID){
        String hql = "from PayFaceDevice where sn = ?";
        PayFaceDevice pd = (PayFaceDevice)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{sn});
        if(pd!=null) {

            String hql1 = "from StoreBindDevice where subCompanyID = ?";
            StoreBindDevice storeBindDevice = (StoreBindDevice) baseBeanDao.getBeanByHqlAndParams(hql1, new Object[]{subCompanyID});

          if(storeBindDevice!=null){
              BindDevice bindDevice = new BindDevice();
              bindDevice.setBdID(serverService.getServerID("sbid"));
              bindDevice.setSubCompanyID(subCompanyID);
              bindDevice.setSn(sn);
              bindDevice.setPfdID(pd.getPfdID());
              bindDevice.setBindDate(new Date());
              bindDevice.setBindID(staffID);
              String hqlstaff = "from Staff where staffID = ?";
              Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlstaff, new Object[]{staffID});
              bindDevice.setBindName(input.getStaffName());
              baseBeanDao.update(bindDevice);

              pd.setBindState("01");
              baseBeanDao.update(pd);
          }

        }

    }

    /**
     * 移除设备和商户绑定
     * @param
     * @return
     */
    @Override
    @Transactional
    public void removeStoreBind(String sn,String subCompanyID,String staffID){
            //查询商户
            String hql = "from StoreBindDevice where subCompanyID = ?";
            StoreBindDevice sbd = (StoreBindDevice)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{subCompanyID});


            //查询商户关联设备
            String hqls = "from BindDevice where subCompanyID = ? and sn = ?";
            BindDevice db = (BindDevice)baseBeanDao.getBeanByHqlAndParams(hqls,new Object[]{subCompanyID,sn});


            List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
              //保存绑定记录
            StoreDeviceHistory history = new StoreDeviceHistory();
            history.setSdhID(serverService.getServerID("sdhID"));
            history.setSubCompanyID(sbd.getSubCompanyID());
            history.setStoreID(sbd.getStoreID());
            history.setStoreName(sbd.getStoreName());
            history.setSubAppID(sbd.getSubAppID());
            history.setSubMchid(sbd.getSubMchid());
            history.setBindDate(db.getBindDate());
            history.setRemoveDate(new Date());
            history.setSn(sn);
            history.setPfdID(db.getPfdID());
            String hqlstaff = "from Staff where staffID = ?";
            Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlstaff, new Object[]{staffID});
            history.setRemoveID(staffID);
            history.setRemoveName(input.getStaffName());
            baseBeanList.add(history);

             //修改绑定状态
             String hqlf = "from PayFaceDevice where sn = ?";
             PayFaceDevice pd = (PayFaceDevice)baseBeanDao.getBeanByHqlAndParams(hqlf,new Object[]{sn});
             pd.setBindState("00");
             baseBeanList.add(pd);
              //删除设备关联商户
             String deletehql = "delete from  BindDevice where sn = ? and subCompanyID = ?";
             List<Object> param = new ArrayList<Object>();
             param.add(sn);
             param.add(subCompanyID);


            baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList,new String[]{deletehql},param.toArray());



    }

    /**
     * 设备绑定业务员
     * @param sn
     * @param wfjAccount
     * @return
     */
    @Override
    @Transactional
    public void bindClerk(String sn,String wfjAccount,String staffID){

        String hql = "from PayFaceDevice where sn = ?";
        PayFaceDevice payFaceDevice =(PayFaceDevice)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{sn});
        if(payFaceDevice!=null&&(payFaceDevice.getClerkSccid()==null||payFaceDevice.getClerkSccid().equals(""))){
            Object obj = checkUser(wfjAccount);
            Object[] objs = (Object[])obj;
            payFaceDevice.setClearkAccount(objs[0].toString());
            payFaceDevice.setClerkID(objs[1].toString());
            payFaceDevice.setClerkSccid(objs[2].toString());
            payFaceDevice.setClerkName(objs[3].toString());

            payFaceDevice.setBindDate(new Date());
            payFaceDevice.setBindID(staffID);
            String hqlstaff = "from Staff where staffID = ?";
            Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlstaff, new Object[]{staffID});
            payFaceDevice.setBindName(input.getStaffName());

            baseBeanDao.update(payFaceDevice);
        }

    }

    /**
     *
     * 验证手机号是否
     * @param wfjAccount
     * @return
     */
    public Object checkUser(String wfjAccount){

        String  sql = "select t.account,t.staffid,t.sccid,f.staffname from T_ESHOP_CUSCOM t,dt_hr_staff f where t.acquiesce = ? and t.account = ? and t.staffid = f.staffid";
        Object obj =  baseBeanDao.getObjectBySqlAndParams(sql,new Object[]{"01",wfjAccount});


        return obj;
    }

    /***
     *  解绑业务员
     * @param sn
     * @param wfjAccount
     * @param staffID
     */
    @Override
    @Transactional
    public void  removeClerk(String sn,String wfjAccount,String staffID){
              List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
              String hql = "from PayFaceDevice where sn = ?";
              PayFaceDevice pfd = (PayFaceDevice) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{sn});
              if(pfd!=null&&pfd.getClerkID()!=null&&!pfd.getClerkID().equals("")){
                  ClerkDeviceHistory history = new ClerkDeviceHistory();
                  history.setCdhID(serverService.getServerID("cdhid"));
                  history.setClerkSccid(pfd.getClerkSccid());
                  history.setClerkName(pfd.getClerkName());
                  history.setClearkAccount(pfd.getClearkAccount());
                  history.setClerkID(pfd.getClerkID());
                  history.setRemoveID(staffID);
                  String hqlstaff = "from Staff where staffID = ?";
                  //录入人员
                  Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlstaff, new Object[]{staffID});
                  history.setRemoveName(input.getStaffName());
                  history.setBindDate(pfd.getBindDate());
                  history.setRemoveDate(new Date());
                  baseBeanList.add(history);

                  pfd.setClerkID("");
                  pfd.setClerkName("");
                  pfd.setClerkSccid("");
                  pfd.setClearkAccount("");
                  baseBeanList.add(pfd);

                    baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList,null,null);
              }

    }

    /**
     *
     *获取商家
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageForm getCompanyList(int pageNumber, int pageSize,String companyName){

        String sql = "select t.account,t.companyid,m.companyname,m.industrytype,f.staffname,m.ccompanyid from t_Eshop_Cuscom t,dt_ccom_com c,Dtcontactcompany m,dt_hr_staff f where t.companyid = c.compnay_id and c.ccompany_id = m.ccompanyid and t.staffid = f.staffid and m.webstatus=? and m.companyname like ?";

        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")",new Object[]{"01","%"+(companyName==null?"":companyName)+"%"});

        return pageForm;
    }

    /**
     *  商户微信信息保存
     * @param store
     * @param companyID
     * @param staffID
     */
    @Override
    @Transactional
    public void addStoreOrUpdate(StoreBindDevice store, String companyID, String staffID){
        if (store != null) {

            String hql = "from Staff where staffID = ?";
            //录入人员
            Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID});
            store.setInputID(staffID);
            store.setInputName(input.getStaffName());
            store.setCompanyID(companyID);

            store.setCreateDate(new Date());

            if(store.getStoreID()!=null&&store.getStoreID().length()>32){
                store.setStoreID(store.getStoreID().substring(7));
            }

            if (store.getSbdID()== null || store.getSbdID().equals("")) {
                store.setSbdID(serverService.getServerID("sbdID"));
                baseBeanDao.update(store);
            }else{
                String hqls = "from StoreBindDevice where sbdID = ?";
                StoreBindDevice pd = (StoreBindDevice) baseBeanDao.getBeanByHqlAndParams(hqls,new Object[]{store.getSbdID()});

                pd.setSubMchid(store.getSubMchid());
                pd.setSubAppID(store.getSubAppID());

                pd.setSubCompanyID(store.getSubCompanyID());
                pd.setStoreID(store.getStoreID());
                pd.setStoreName(store.getStoreName());

                pd.setStaffID(store.getStaffID());
                pd.setStaffName(store.getStaffName());

                baseBeanDao.update(pd);
            }


        }

    }

    /**
     *
     * 调整费率并记录历史记录
     * @param subCompanyID
     * @param rate
     * @return
     */
    @Override
    @Transactional
    public void changeRate(String subCompanyID,String rate,String staffID){

        String hql = "from StoreBindDevice where subCompanyID = ?";
        StoreBindDevice sbd = (StoreBindDevice)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{subCompanyID});


        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        if(sbd!=null){
            StoreRateHistory history = new StoreRateHistory();
            history.setSdhID(serverService.getServerID("sdhID"));
            history.setSubCompanyID(sbd.getSubCompanyID());
            history.setStoreID(sbd.getStoreID());
            history.setStoreName(sbd.getStoreName());
            history.setSubAppID(sbd.getSubAppID());
            history.setSubMchid(sbd.getSubMchid());

            history.setChangeDate(new Date());
            history.setChangeRateDate(sbd.getChangeRateDate());

            String hqlstaff = "from Staff where staffID = ?";
            Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlstaff, new Object[]{staffID});
            history.setChangeID(staffID);
            history.setChangeName(input.getStaffName());
            baseBeanList.add(history);

            sbd.setStoreRate(rate);
            sbd.setChangeRateDate(new Date());

            baseBeanList.add(sbd);

            baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList,null,null);
        }






    }

    /**
     * 删除商户
     * @param sbdKey
     */
    @Override
    @Transactional
    public void deleteStore(String sbdKey){

        baseBeanDao.deleteBeanByKey(StoreBindDevice.class,sbdKey);

    }

    /**
     *   根据商户获取它绑定的设备
     * @param pageNumber
     * @param pageSize
     * @param subCompanyID
     * @param parameter
     * @return
     */
    public PageForm getStoreAllDevice(int pageNumber, int pageSize, String subCompanyID,String parameter){
         String sql ="select b.bdID,b.sn,b.bindname,b.binddate,p.clerkname from DT_BindDevice b,dt_payfacedevice p where b.sn=p.sn and b.subcompanyid = ? and b.sn like ?";
          PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")",new Object[]{subCompanyID,"%"+(parameter==null?"":parameter)+"%"});

         return  pageForm;
    }

    /**
     *
     * 验证商户是否绑定过设备
     * @param subCompanyID
     * @return
     */
    public String checkStoreDevice(String subCompanyID){
         String hql = "from BindDevice where subCompanyID = ?";
         List<BaseBean> list  = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{subCompanyID});
         if(list.size()!=0){
             return "1";
         }
            return "0";
    }

    /**
     *
     * 根据设备号获取绑定的商户的微信APPID和商户号
     * @return
     */
    public Object getWXData(String sn){
        String sql = "select k.subappid,k.submchid from dt_binddevice b,dt_storebinddevice k where b.subcompanyid = k.subcompanyid and b.sn=?";
        Object obj = baseBeanDao.getObjectBySqlAndParams(sql,new Object[]{sn});
          return obj;

    }
    /**
     * 刷脸支付记录信息
     * @param journalNum
     * @param openid
     * @param transaction_id
     * @param money
     */
    @Override
    @Transactional
    public void savePayBill(String journalNum,String openid,String nickname, String transaction_id,String money,String comID,String device_id){
        try {
               String hql = "from PayBackupBill where journalNum = ?";
               PayBackupBill pb = (PayBackupBill)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{journalNum});
               if(pb==null){
                   PayBackupBill payBackupBill = new PayBackupBill();
                   payBackupBill.setPbbID(serverService.getServerID("pbbid"));
                   payBackupBill.setOpenId(openid);
                   payBackupBill.setNickname(nickname);
                   payBackupBill.setCompanyId(comID);
                   payBackupBill.setTransaction_id(transaction_id);
                   payBackupBill.setJournalNum(journalNum);
                   payBackupBill.setDevice_id(device_id);

                   payBackupBill.setMoney(WeChatUtils.changeF2Y(money));

                   baseBeanDao.save(payBackupBill);
               }else{
                   pb.setOpenId(openid);
                   pb.setNickname(nickname);
                   pb.setTransaction_id(transaction_id);
                   pb.setMoney(WeChatUtils.changeF2Y(money));
                   pb.setDevice_id(device_id);
                   baseBeanDao.update(pb);
               }
        }catch (Exception e){
            logger.error("操作异常", e);
        }

    }



    /**
     * 设备交易金额
     * @param sn
     * @return
     */
    @Override
    @Transactional
    public void recordTradeMoney(String sn,String money,String journalNum,String source,String openid,String nickname){

        //累计交易额
        List<BaseBean> baseBeanList = new ArrayList<BaseBean>();
        String hql = "from PayFaceDevice where sn = ?";
        PayFaceDevice pd = (PayFaceDevice)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{sn});

        if("face".equals(source)) {
            BigDecimal y = new BigDecimal(0);
            if (pd.getTradeMoney() != null && !pd.getRakeMoney().equals("")) {
                y = new BigDecimal(pd.getTradeMoney());
            }
            BigDecimal m = new BigDecimal(money);
            BigDecimal sum = y.add(m);
            pd.setTradeMoney(sum + "");
            baseBeanList.add(pd);
        }

          String hqld = "from StoreBindDevice s where s.subCompanyID = (select b.subCompanyID from BindDevice b where b.sn = ?)";
          StoreBindDevice sbd =  (StoreBindDevice)baseBeanDao.getBeanByHqlAndParams(hqld,new Object[]{sn});
        //记录明细
           PayFaceRecord payFaceRecord = new PayFaceRecord();
           payFaceRecord.setPftrID(serverService.getServerID("pftrid"));
           payFaceRecord.setTradeMoney(money);
           if(sbd!=null) {
               payFaceRecord.setSubCompanyID(sbd.getSubCompanyID());
               payFaceRecord.setAppID(sbd.getSubAppID());
               payFaceRecord.setStoreRate(sbd.getStoreRate());

           }
           payFaceRecord.setClerkSccid(pd.getClerkSccid());
           payFaceRecord.setTradeDate(new Date());
           payFaceRecord.setJournalNum(journalNum);
           payFaceRecord.setSn(sn);
           payFaceRecord.setSource(source);
           payFaceRecord.setOpenID(openid);
           payFaceRecord.setNickname(nickname);
           baseBeanList.add(payFaceRecord);
           baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeanList,null,null);


    }


    /**
     *   海报
     * @param pageNumber
     * @param pageSize
     * @param companyID
     * @param parameter
     * @return
     */
    public PageForm getPosterList(int pageNumber, int pageSize, String companyID,String parameter,String type){
        List<Object> param = new ArrayList<Object>();
        String hql  = "from SetPoster where deviceType = ? and companyID = ? ";
        param.add(type);
        param.add(companyID);
        if(parameter!=null&&!parameter.equals("")){
            hql += "and posterName like ? ";
            param.add("%"+parameter+"%");
        }
        hql+=" order by sorts";

        PageForm pageForm = baseBeanService.getPageForm(pageNumber,pageSize,hql,param.toArray());

        return  pageForm;

    }

    /**
     * 删除海报
     * @param spKey
     */
    @Override
    @Transactional
    public void deletePoster(String spKey) {

        baseBeanDao.deleteBeanByKey(SetPoster.class,spKey);

    }

    /**
     * 上下线海报
     * @param spID
     * @param isPublish
     */
    @Override
    @Transactional
    public void onOffLine(String spID,String isPublish,String staffID){
        String hqlstaff = "from Staff where staffID = ?";
        Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlstaff, new Object[]{staffID});
       String hql = "from SetPoster where spID = ?";
        SetPoster poster = (SetPoster) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{spID});
        poster.setIsPublish(isPublish);
        if(isPublish.equals("00")) {
            poster.setOfflineDate(new Date());
            poster.setOfflineID(staffID);
            poster.setOfflineName(input.getStaffName());
        }else{
            poster.setPublishDate(new Date());
            poster.setPublishID(staffID);
            poster.setPublishName(input.getStaffName());
        }

        baseBeanDao.update(poster);
    }

    /**
     *
     * 海报排序
     * @param sorts
     */
    @Override
    @Transactional
    public void posterSorts(String spID,int sorts){
          String[] hqls=new String[]{"update SetPoster set sorts = ? where spID = ?"};
          List<Object[]> param=new ArrayList<Object[]>();
          param.add(new Object[]{sorts,spID});

          baseBeanDao.executeHqlsByParmsList(null,hqls,param);
    }

    /**
     *
     * 获取在线海报
     * @return
     */
    public List<Object> getPosterList(){

       String sql = "select posterPath from DT_SetPoster where isPublish = ? and deviceType = ? and rownum < 5 order by sorts";
       List<Object> list = baseBeanDao.getListObjectBySqlAndParams(sql,new Object[]{"01","00"});


       return list;
    }

    /**
     * 添加海报
     * @param poster
     */
    @Override
    @Transactional
    public void addPoster(SetPoster poster){

        String hqlstaff = "from Staff where staffID = ?";
        Staff input = (Staff) baseBeanDao.getBeanByHqlAndParams(hqlstaff, new Object[]{poster.getInputID()});

        if(poster!=null&&poster.getSpID()!=null&&!poster.getSpID().equals("")){
            SetPoster setPoster = (SetPoster)baseBeanDao.getBeanByHqlAndParams("from SetPoster where spID = ?",new Object[]{poster.getSpID()});
            setPoster.setPosterName(poster.getPosterName());
            if(poster.getPosterPath()!=null&&!poster.getPosterPath().equals("")){
                setPoster.setPosterPath(poster.getPosterPath());
            }
            setPoster.setCreateDate(new Date());
            setPoster.setInputID(poster.getInputID());
            setPoster.setInputName(input.getStaffName());
            baseBeanDao.update(setPoster);
        }else{
            poster.setSpID(serverService.getServerID("spid"));
            int sort = 0;
            try {
                String sql = "select max(sorts) from DT_SetPoster where deviceType = ?";
                int  maxSort = baseBeanDao.getConutByBySqlAndParams(sql, new Object[]{"00"});
                sort = maxSort+1;
            }catch (Exception e){
                sort = 1;
            }
            poster.setSorts(sort);
            poster.setCreateDate(new Date());
            poster.setInputID(poster.getInputID());
            poster.setInputName(input.getStaffName());
            poster.setIsPublish("00");
            baseBeanDao.save(poster);
        }





    }


    /**
     * 从数据库里获取已经保存的accessToken
     *
     * @return
     */
    public String getAccessTokenFromDataBase() {
        String hql = "from WeChatToken";
        WeChatToken wt = (WeChatToken) baseBeanDao.getBeanByHqlAndParams(hql, null);

        return wt.getAccessToken();
    }
}
