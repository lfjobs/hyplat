package hy.ea.finance.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import hy.ea.bo.finance.BenDis.SetSubsidize;
import hy.ea.bo.finance.BenDis.TypeSubsidize;
import hy.ea.bo.finance.BenDis.UallSubsidize;
import hy.ea.finance.service.SetSubsidizeService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.service.TelService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2018-01-06.
 */
@Service
@Transactional
public class SetSubsidizeServiceImpl implements SetSubsidizeService {


    @Resource
    private BaseBeanService baseBeanService;
    @Resource
    private TelService telService;

    private Logger logger = LoggerFactory.getLogger(SetSubsidizeServiceImpl.class);

    /**
     * 保存
     * @param subsidize 消费补助数据
     * @return
     */
    @Override
    public synchronized JSONObject saveSetSubsidize(SetSubsidize subsidize){
        Map<String,Object> map= new HashMap<String,Object>();
        try {
            baseBeanService.saveOrUpdate(subsidize);
            map.put("flag",true);
        }catch (Exception e){
            map.put("flag",false);
            logger.error("操作异常", e);
        }
        JSONObject oj =JSONObject.fromObject(map);
        return oj;
    }

    /**
     * 查询条数
     * @return
     */
    public int subsidizeByCount(){
        return baseBeanService.getConutByBySqlAndParams("select count(*) from dt_set_subsidize",null);
    }

    /**
     * 根据往来单位id查询该公司的消费补助分配数据
     * @param ccompanyid 往来单位id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public BaseBean setSubsidizeByType(String ccompanyid){
        Map<String,Object> map= new HashMap<String,Object>();
        SetSubsidize setSubsidize=null;
        try {
            String hql="select new SetSubsidize(s.sskey, s.ssid, s.companyid, s.codeNum, s.gtid, s.stid, s.totalPct, s.flPct, s.slPct, s.adddate, s.staffid, s.stutas) from SetSubsidize s,ContactCompany c where s.gtid=c.industryType and s.stutas=? and c.ccompanyID=? ";
            setSubsidize =(SetSubsidize)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{"01",ccompanyid});
        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return setSubsidize;
    }

    /**
     * 根据公司id查询该公司的消费补助分配数据
     * @param companyid 往来单位id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public BaseBean setSubsidizeByTypeGetcid(String companyid){
        Map<String,Object> map= new HashMap<String,Object>();
        SetSubsidize setSubsidize=null;
        try {

            String hql="select new SetSubsidize(s.sskey, s.ssid, s.companyid, s.codeNum, s.gtid, s.stid, s.totalPct," +
                    " s.flPct, s.slPct, s.adddate, s.staffid, s.stutas) from SetSubsidize s,ContactCompany c,CcomCom cc" +
                    " where s.gtid=c.industryType and c.ccompanyID=cc.ccompanyId and s.stutas=? and cc.comanyId=? ";
            setSubsidize =(SetSubsidize)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{"01",companyid});
        }catch (Exception e){
            logger.error("操作异常", e);
        }
        return setSubsidize;
    }

    /**
     * 列表查询
     * @param pageForm
     * @param pageNumber
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public PageForm ListSetSubsidize(PageForm pageForm,int pageNumber){
        StringBuilder hql=new StringBuilder("select new SetSubsidize (sss.sskey,sss.ssid, sss.codeNum, sss.gtid, t.stname, sss.totalPct,");
        hql.append(" sss.flPct, sss.slPct, sss.adddate, s.staffName, sss.stutas, sss.xfPct, sss.xbPct, sss.fsPct)from SetSubsidize sss,Staff s,TypeSubsidize t");
        hql.append(" where sss.staffid=s.staffID and sss.stid=t.stid and sss.stutas=? order by sss.adddate desc");
        pageForm=baseBeanService.getPageForm(
                (null != pageForm ? pageForm.getPageNumber() : 1),
                (pageNumber == 0 ? 10 : pageNumber),hql.toString(),"select count(*) "+hql.substring(hql.indexOf("from")),new Object[]{"01"} );
        return pageForm;
    }

    /**
     * 查询消费补助类别
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public JSONObject ListTypeSubsidize(){
        Map<String,Object> map= new HashMap<String,Object>();
        try {
            List<BaseBean> typeSubsidizeList=baseBeanService.getListBeanByHqlAndParams("from TypeSubsidize",null);
            map.put("typeList",typeSubsidizeList);
        }catch (Exception e){
            map.put("typeList",false);
            logger.error("操作异常", e);
        }
        JSONObject oj =JSONObject.fromObject(map);
        return oj;
    }

    /**
     * 查询行业类别
     * @param codePid 行业类别父id
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public JSONObject getCCodeListByPID(String codePid){
        Map<String,Object> map= new HashMap<String,Object>();
        try {
            String hql="from SCode where  codePID = ?  and ( codeStatus = ? or codeStatus = ?) and  codeNumber<? order by codeSn ";
            List<BaseBean> scodeList=
                    baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{codePid,"00","01",5});
            map.put("scodeList",scodeList);
        }catch (Exception e){
            map.put("scodeList",false);
            logger.error("操作异常", e);
        }
        JSONObject oj =JSONObject.fromObject(map);
        return oj;
    }

    /**
     * 删除
     * @param ssid 消费补助id
     * @return
     */
    @Override
    public JSONObject delSetSubsidize(String ssid){
        Map<String,Object> map= new HashMap<String,Object>();
        try {
            String hql="update SetSubsidize set stutas=? where ssid = ?";
            baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{hql},new Object[]{"02",ssid});
            map.put("flag",true);
        }catch (Exception e){
            map.put("flag",false);
            logger.error("操作异常", e);
        }
        JSONObject oj =JSONObject.fromObject(map);
        return oj;
    }

    /**
     * 查询消费补助行业是否添加过
     * @param gtid 行业类别
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public JSONObject getsstype(String gtid){
        Map<String,Object> map= new HashMap<String,Object>();
        try {
            String hql="select count(*) from SetSubsidize where gtid=? and stutas=?";
            int count=baseBeanService.getConutByByHqlAndParams(hql,new Object[]{gtid,"01"});
            map.put("count",count);
        }catch (Exception e){
            map.put("count",1);
            logger.error("操作异常", e);
        }
        JSONObject oj =JSONObject.fromObject(map);
        return oj;
    }

    /**
     * 根据key查询数据
     * @param sskey key
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public SetSubsidize getSubsidize(String sskey){
        String hql="from SetSubsidize s where sskey=? ";
        return (SetSubsidize)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{sskey});
    }

    /**
     * 处理历史数据
     * @param subsidize
     */
    public void handleHistory(SetSubsidize subsidize){
        try {
            StringBuilder casql=new StringBuilder();
            casql.append("select oa_sccid,journalnum from (select cc.oa_sccid,cc.journalnum from dtcashierbills c,(select c.journalnum, o.oa_sccid");
            casql.append(" from dtcashierbills c,dt_order_bill_add o,t_eshop_cuscom t,dtcontactcompany cccc, dt_ccom_com ccc");
            casql.append(" where c.statusbill = ? and c.companyid = ccc.compnay_id and ccc.ccompany_id = cccc.ccompanyid");
            casql.append(" and c.cashierbillsid = o.oa_bill_id and t.sccid = o.oa_sccid and c.billstype =?");
            casql.append(" and c.fkstatus <>?  and c.fkstatus <>?  and c.status <>?  and cccc.industrytype = ?");
            casql.append(" order by c.cashierdate) cc where c.jnumorder = cc.journalnum and ((c.billstype = ? and c.projectname <>? ) or");
            casql.append(" (c.billstype =?  and c.projectname =? )) and c.cashierdate between ? and ? order by c.cashierdate desc) where rownum = 1");

            String date= Utilities.getDateString(new Date(),"yyyy-MM-dd");

            Object[] a=(Object[])baseBeanService.getObjectBySqlAndParams(casql.toString(),new Object[]{"04","项目收入预算单","01","09","99",subsidize.getGtid(),"收款单","供应商成本","积分入库单","积分购物",Utilities.getDateFromString(date + " 00:00:00","yyyy-MM-dd HH:mm:ss"),Utilities.getDateFromString(date + " 23:59:59","yyyy-MM-dd HH:mm:ss")});

            StringBuilder counsql=new StringBuilder("select * from (select us.countnum,us.usid from dt_subsidize_backup sb, dt_uall_subsidize us");
            counsql.append(" where sb.m_id = us.sccid and sb.cash_jum = ? and us.ssid = ? order by us.countnum desc)");

            List<Object[]>bb= baseBeanService.getListBeanBySqlAndParams(counsql.toString(),new Object[]{a[1],subsidize.getSsid()});

            //根据行业类别查询消费补助绑定的最后一个人
            StringBuilder uasql=new StringBuilder("from UallSubsidize where ssid = ? and countNum > ? order by countNum");
            List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(uasql.toString(),new Object[]{subsidize.getSsid(),Integer.parseInt(bb.get(0)[0].toString())});
            StringBuilder uapsql=new StringBuilder("from UallSubsidize where ssid = ? and countNum >= ? order by countNum");
            List<BaseBean> plist=baseBeanService.getListBeanByHqlAndParams(uasql.toString(),new Object[]{subsidize.getSsid(),Integer.parseInt(bb.get(1)[0].toString())});
            UallSubsidize u=(UallSubsidize) plist.get(0);
            String psccid=u.getPsccid();
            int num=0;
            int pnum=0;
            TypeSubsidize tSubsidize=(TypeSubsidize) baseBeanService.getBeanByHqlAndParams("from TypeSubsidize where stid=?",new Object[]{subsidize.getStid()});
            List<BaseBean> baBe=new ArrayList<>();
            for (int i=0;i<list.size();i++){
                UallSubsidize usubsidize=(UallSubsidize)list.get(i);
                UallSubsidize uu=(UallSubsidize)plist.get(pnum);
                if(num<tSubsidize.getStnum()){
                    logger.error(tSubsidize.getStnum().toString());
                    usubsidize.setPsccid(uu.getSccid());
                    num++;
                    if(num==tSubsidize.getStnum()){
                        pnum++;
                    }
                }else {
                    usubsidize.setPsccid(uu.getSccid());
                    num=1;
                }
                baBe.add(usubsidize);
            }
            baseBeanService.executeHqlsByParamsList(baBe,null,null);
        }catch (Exception e){
            logger.error("操作异常", e);
        }

    }


    /**
     * 发红包
     * @param goldNum 金币数
     * @param sender 发送人
     * @param ssid
     * @param gtid
     * @return
     */
    public void slkdfj(String goldNum,String sender,String ssid,String gtid,String strnum,String endnum){
        JSONObject ret = new JSONObject();
        JSONObject jobj = new JSONObject();
        try {
            if(strnum==null||strnum.equals("")){
                StringBuilder casql=new StringBuilder();
                casql.append("select oa_sccid,journalnum from (select cc.oa_sccid,cc.journalnum from dtcashierbills c,(select c.journalnum, o.oa_sccid");
                casql.append(" from dtcashierbills c,dt_order_bill_add o,t_eshop_cuscom t,dtcontactcompany cccc, dt_ccom_com ccc");
                casql.append(" where c.statusbill = ? and c.companyid = ccc.compnay_id and ccc.ccompany_id = cccc.ccompanyid");
                casql.append(" and c.cashierbillsid = o.oa_bill_id and t.sccid = o.oa_sccid and c.billstype =?");
                casql.append(" and c.fkstatus <>?  and c.fkstatus <>?  and c.status <>?  and cccc.industrytype = ?");
                casql.append(" order by c.cashierdate) cc where c.jnumorder = cc.journalnum and ((c.billstype = ? and c.projectname <>? ) or");
                casql.append(" (c.billstype =?  and c.projectname =? )) and c.cashierdate between ? and ? order by c.cashierdate desc) where rownum = 1");

                String date= Utilities.getDateString(new Date(),"yyyy-MM-dd");
                Object[] a=(Object[])baseBeanService.getObjectBySqlAndParams(casql.toString(),new Object[]{"04","项目收入预算单","01","09","99",gtid,"收款单","供应商成本","积分入库单","积分购物",Utilities.getDateFromString(date + " 00:00:00","yyyy-MM-dd HH:mm:ss"),Utilities.getDateFromString(date + " 23:59:59","yyyy-MM-dd HH:mm:ss")});
                StringBuilder counsql=new StringBuilder("select * from (select us.countnum,us.sccid from dt_subsidize_backup sb, dt_uall_subsidize us");
                counsql.append(" where sb.m_id = us.sccid and sb.cash_jum = ? and us.ssid = ? order by us.countnum desc)");

                List<Object[]>bb= baseBeanService.getListBeanBySqlAndParams(counsql.toString(),new Object[]{a[1],ssid});
                strnum=bb.get(0)[0].toString();
            }

            List<Object> parem=new ArrayList<>();
            StringBuilder uasql=new StringBuilder("from UallSubsidize where ssid = ? and countNum >= ?");
            parem.add(ssid);
            parem.add(Integer.parseInt(strnum));
            if (endnum!=null&&!endnum.equals("")){
                uasql.append(" and countNum<=?");
                parem.add(Integer.parseInt(endnum));
            }
            uasql.append(" order by countNum");
            List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(uasql.toString(),parem.toArray());

            String liuYan = "消费红包抵扣";// 留言
            for (int i = 0; i < list.size(); i++) {
                UallSubsidize us=(UallSubsidize) list.get(i);
                String recipient=us.getSccid();// 接收人
                logger.error(recipient);
                int j=   telService.redPacket(sender,recipient,goldNum,liuYan);

            }
        } catch (Exception e) {
                logger.error("操作异常", e);
        }
    }

    /**
     * 根据帐号验证交易密码
     * @param zh
     * @param mm
     * @return
     */
    public JSONObject getmm(String zh,String mm){
        JSONObject jobj = new JSONObject();
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("falg", "核对正确");
        try {
            TEshopCustomer tEshopCustomer=(TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0",new Object[]{zh});
            if (tEshopCustomer!=null){

                TEshopCusCom tEshopCusCom=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0",new Object[]{zh});
                map.put("sccid", tEshopCusCom.getSccId());
                if(tEshopCustomer.getPaymentCode()!=null&&tEshopCustomer.getPaymentCode().equals(mm)){
                    if(tEshopCustomer.getStatus()!=null&&tEshopCustomer.getSource().equals("1")){
                        map.put("falg", "帐号已冻结");
                    }
                }else{
                    map.put("falg", "支付密码为空或者不正确");
                }
            }else{
                map.put("falg", "手机号不正确");
            }
        } catch (Exception e) {
            logger.error("操作异常", e);
            map.put("falg", "查询错误");
        }
        jobj =JSONObject.fromObject(map);
        return jobj;
    }
}
