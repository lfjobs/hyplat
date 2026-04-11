package mobile.tiantai.android.service.impl;

import com.tiantai.wfj.bo.WfjJifen;
import hy.ea.bo.finance.CashierBills;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bo.JiShi;
import mobile.tiantai.android.bo.TravelRecords;
import mobile.tiantai.android.bo.WeiZhi;
import mobile.tiantai.android.service.DanCheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22 0022.
 */
@Transactional
@Service
public class DanCheServiceImpl implements DanCheService {

    @Resource
    private BaseBeanDao beandao;

    @Resource
    private ServerService serverService;

    private Logger log = LoggerFactory.getLogger(DanCheServiceImpl.class);

    /**
     *计时开始
     * @param deviceid
     * @return
     */
    @Override
    public Boolean jiShi(String deviceid) {
      Boolean bl=true;

        try {
            JiShi js=new JiShi();
            js= (JiShi) beandao.getBeanByHqlAndParams(" from JiShi where staffid= ?",new Object[]{deviceid});
            if(js == null){
              js.setJsid(serverService.getServerID("jsid"));
              js.setJskey(serverService.getServerID("jskey"));
              js.setDeviceid(deviceid);
              js.setBeginDate(new Date());
              js.setClmessage("0");
            }else {
                js.setBeginDate(new Date());
                js.setClmessage("0");
            }
            beandao.save(js);
        } catch (Exception e) {
            log.error("开锁计时异常"+deviceid);
            bl=false;
        }


        return bl;
    }


    /**
     * 单车开始时间的保存和结束时间的保存，相减计算时间
     *
     * @param  sccid 人员的staffid
     *         deviceid  车辆的二维码唯一车标号
     *         true 为开始计时成功  false 为该车正在使用或者其他问题
     */
    public Boolean zhiFuQiXing(String sccid,String deviceid) {
        Boolean bl=true;
        List<BaseBean> saveList=new ArrayList<BaseBean>();
        try {
            JiShi js=new JiShi();
            js= (JiShi) beandao.getBeanByHqlAndParams(" from JiShi where deviceid = ?",new Object[]{deviceid});
            if(js==null){
                bl=false;
            }else{
                if(js.getSccid() != null && !js.getSccid().equals("") && js.getSccid().equals(sccid)) {
                    js.setBeginDate(new Date());
                    js.setClmessage("0");

                    WeiZhi weiZhi = (WeiZhi) beandao.getBeanByHqlAndParams(" from WeiZhi where deviceId = ?", new Object[]{deviceid});
                    weiZhi.setLockstatus(1);
                    saveList.add(weiZhi);
                    saveList.add(js);
                }
            }
            beandao.executeHqlsByParmsList(saveList,null,null);
        } catch (Exception e) {
            log.error("--------开始计时有误---------车辆编号"+deviceid);
            bl=false;
        }

        return  bl;
    }
    /**
     * 单车关锁时候计算骑行时间，并将骑行的记录存入骑行明细表中
     *
     * @param  deviceid  车辆的二维码唯一车标号
     * @param  sccid  人员的staffid
     *         true 为关锁计算其他成功  false 为该车计算出现问题，查看logger 日志
     */
    public Boolean closeSuo(String deviceid,String sccid){
        Boolean bl=true;
        List<BaseBean> saveList=new ArrayList<BaseBean>();

        try {

            //将计时表结束时间记录，算出时间总和
            JiShi js=new JiShi();
            js= (JiShi) beandao.getBeanByHqlAndParams(" from JiShi where deviceid = ? and sccid = ?",new Object[]{deviceid,sccid});
            if(js == null){
                bl=false;     //非开始骑行的人结算车
            }else{
                js.setEndDate(new Date());
                //计算骑行的金额
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d1 = df.parse(js.getEndDate().toString());
                Date d2 = df.parse(js.getBeginDate().toString());
                long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                if(minutes>30l){
                    hours=hours+1;
                }
                long je=hours*1;
                //扣除个人的金币数
                long jinbishu=je*100;
                String sjinbishu=String.valueOf(jinbishu);
                //将完成的行驶记录插入到行程记录当中
                TravelRecords tl=new TravelRecords();
                tl.setTrkey(serverService.getServerID("tl"));
                tl.setTrid(serverService.getServerID("tl"));
                tl.setDeviceid(deviceid);
                tl.setBeginDate(js.getBeginDate());
                tl.setEndDate(js.getEndDate());
                tl.setScxiaoshi(String.valueOf(hours));
                tl.setScfenzhong(String.valueOf(minutes));
                tl.setTrMoney(String.valueOf(jinbishu));
                tl.setSccid(sccid);
                saveList.add(tl);
                //减去个人钱包中的金币数
                WfjJifen  jinbi = (WfjJifen) beandao.getBeanByHqlAndParams(" from WfjJifen where sccid = ?",new Object[]{sccid});
                if(jinbi == null || jinbi.equals("")){
                      bl=false;
                      return bl;
                }else if (Float.parseFloat(jinbi.getWfjJifenScore())-Float.parseFloat(sjinbishu)>=0){
                      String syjinbi=String.valueOf(Float.parseFloat(jinbi.getWfjJifenScore())-Float.parseFloat(sjinbishu));
                      jinbi.setWfjJifenScore(syjinbi);
                      saveList.add(jinbi);
                      //生成金币出入库单子



                      beandao.executeHqlsByParmsList(saveList,null,null);
                }else{


                }


                //生成金币出入库单子





                //将单车表weizhi表中的该车锁的状态lockstatus变为 0-锁车
                WeiZhi weiZhi= (WeiZhi) beandao.getBeanByHqlAndParams(" from WeiZhi where deviceId = ?",new Object[]{deviceid});
                weiZhi.setLockstatus(0);
                saveList.add(weiZhi);
                beandao.executeHqlsByParmsList(saveList,null,null);

                //删除计时表中的完成行驶记录
                beandao.deleteBeanByKey(JiShi.class,js.getJskey());
            }

        } catch (Exception e) {
            logger.error("操作异常", e);
            log.error("----关锁后结算出现问题的单车编号为，查看JiShi表-----"+deviceid);
        }


        return  bl;
    }
}
