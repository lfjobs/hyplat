package hy.ea.marketing.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tiantai.wfj.bo.GiftCards;
import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.edmandServe.bo.DemandDetail;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.InvestDeviceBind.DeviceBindStaff;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.marketing.service.GiftCardService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/13.
 */
@Transactional
@Service
public class GiftCardServiceImpl implements GiftCardService {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;

    private PageForm pageForm;

    @Override
    public PageForm getSearch(int pageNumber, int pageSize, String companyID, Staff staff, GiftCards giftCards) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select mke.name,mke.account,mke.staffidentitycard,mke.bonuspointscore,mke.sccid,s.staffname,mke.cardnumber,hs.staffname as oper,mke.state,mke.companyid,mke.staffid");
        sql.append(" from dt_hr_staff s left join t_eshop_cuscom es on s.staffid = es.staffid and es.acquiesce = ?");
        sql.append(" right join (select sss.staffname as name,e.account,sss.staffidentitycard,b.bonuspointscore,m.mksccid,e.sccid,g.operator,g.cardnumber,g.state,g.companyid,sss.staffid");
        sql.append(" from dt_hr_staff sss right join t_eshop_cuscom e on sss.staffid = e.staffid and e.acquiesce = ?");
        sql.append(" left join DT_WFJ_BonusPoints b on e.sccid = b.sccid");
        sql.append(" left join dtmarketing m on e.sccid = m.usersccid");
        sql.append(" right join dt_giftcards g on sss.staffid = g.staffid) mke");
        sql.append(" on mke.mksccid = es.sccid left join dt_hr_staff hs  on hs.staffid = mke.operator");
        sql.append(" where mke.companyid = ?");
        param.add("01");
        param.add("01");
        param.add(companyID);
        if(staff!=null){
            if(staff.getStaffName()!=null && !"".equals(staff.getStaffName())){
                sql.append(" and mke.name like ?");
                param.add("%"+staff.getStaffName()+"%");
            }
            if(staff.getReference()!=null && !"".equals(staff.getReference())){
                sql.append(" and mke.account like ?");
                param.add("%"+staff.getReference()+"%");
            }
            if(staff.getStaffIdentityCard()!=null && !"".equals(staff.getStaffIdentityCard())){
                sql.append(" and mke.staffidentitycard like ?");
                param.add("%"+staff.getStaffIdentityCard()+"%");
            }
        }
        if(giftCards!=null && !"".equals(giftCards.getCardNumber())){
            sql.append(" and mke.cardnumber like ?");
            param.add("%"+giftCards.getCardNumber()+"%");
        }
        pageForm = baseBeanService.getPageFormBySQL(pageNumber == 0 ? 1 : pageNumber,
                pageSize == 0 ? 10 : pageSize , sql.toString(),"select count(*) from (" + sql.toString() + ")" ,param.toArray());
        return pageForm;
    }

    /**
     *
     * @param tuicus  推荐人
     * @param sccid   推荐人sccid
     * @param phones  注册人帐号
     * @param intf     注册人密码
     * @param staff   注册人的staff
     * @param giftCards   购物卡
     * @param staffAddress   移动端保存的地址信息
     * @param sccid 移动端登录人的sccid
     * @param paymentCode 支付密码
     */
    @Override
    public  synchronized void registered(TEshopCusCom tuicus, String sccid, String phones, String intf, Staff staff, GiftCards giftCards, StaffAddress staffAddress,String sccid_app,String paymentCode) {
        HttpSession session = ServletActionContext.getRequest().getSession();
        HttpServletRequest request = ServletActionContext.getRequest();
        CAccount account = (CAccount)session.getAttribute("account");
        //推荐人上级
        TEshopCusCom supercus = null;
        if(tuicus!=null){
            supercus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{tuicus.getSupperSccId()});
        }
        if(tuicus==null
                ||tuicus.getCusType().equals("1")
                ||tuicus.getCusType().equals("0.5")
                ||tuicus.getCusType().equals("6")
                ||tuicus.getCusType().equals("7")
                ||(supercus==null&&!tuicus.getCusType().equals("0"))
                ||(supercus!=null&&supercus.getCusType().equals("0.5"))
                ||(supercus!=null&&supercus.getCusType().equals("0"))){
            //20180404修改为如果推荐人的级别为6或7就去找他的上级作为推荐人
            if (tuicus!=null && ("6".equals(tuicus.getCusType()) || "7".equals(tuicus.getCusType()))
                    && !"TEshopCusCom20161010W9FXK9NJ450000011682".equals(tuicus.getSupperSccId())){
                sccid = tuicus.getSupperSccId();
                tuicus=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
            }else{
                sccid="TEshopCusCom20161010W9FXK9NJ450000011682";
                tuicus=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});
                supercus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{tuicus.getSupperSccId()});
            }
        }
        List<BaseBean> beans=new ArrayList<BaseBean>();
        List<Object[]> parm=null;
        String[] hqls=null;
        Staff sf = new Staff();
        TEshopCustomer tct = new TEshopCustomer();
        TEshopCusCom tcc = new TEshopCusCom();

        if(tuicus!=null){
            String pcusttype = tuicus.getCusType();
            if (Integer.parseInt(pcusttype) > 5) {
                // 大于5说明分享者级别级别低于代理商级别有可能是客户或者VIP客户
                tcc.setSupperSccId(supercus.getSccId());
                tcc.setSuperioragent(supercus.getAccount());
            } else{
                // 小于等于5说明分享者级别是代理商或更高级 0,1,2,3,4,5
                if(pcusttype.equals("0")){//推荐人是平台
                    Object obj = baseBeanService
                            .getObjectBySqlAndParams("SELECT T.SCCID,T.ACCOUNT" +
                                    " FROM T_ESHOP_CUSCOM T where t.custype='1' START WITH T.SCCID=?" +
                                    " CONNECT BY PRIOR T.SCCID=T.supperSccId", new Object[] { tuicus.getSccId() });
                    if(obj!=null){
                        Object[] objs = (Object[])obj;
                        tcc.setSupperSccId(objs[0].toString());//推荐人上级
                        tcc.setSuperioragent(objs[1].toString());
                    }else{
                        tuicus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(
                                "from TEshopCusCom where sccId=?", new Object[]{sccid});//平台账号下没有地税的推荐人
                        tcc.setSupperSccId(tuicus.getSccId());//推荐人上级
                        tcc.setSuperioragent(tuicus.getAccount());
                    }
                }else{//,1,2,3,4,5
                    tcc.setSupperSccId(tuicus.getSccId());//推荐人
                    tcc.setSuperioragent(tuicus.getAccount());
                }
            }
            tcc.setPpid(tuicus.getPpid());
        }
        logger.info("调试信息");
        if(staff!=null&&staff.getStaffName()!=null){
            sf.setStaffName(staff.getStaffName());
        }
        sf.setStaffID(serverService.getServerID("Staff"));
        sf.setReference(phones);//电话
        sf.setStaffIdentityCard(staff.getStaffIdentityCard());
        //增加编号以及录入时间
        String phql = "select count(*) from Staff ";
        int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
        sf.setStaffCode("NO" + pcount);
        sf.setVerifyTime(new Date());
        sf.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
        sf.setStaus("01");// 默认身份证为空 为不确定人员

        //用于app端开通购物卡保存地址
        if(staffAddress!=null){
            staffAddress.setAddressID(serverService.getServerID("address"));
            staffAddress.setStaffID(sf.getStaffID());
            staffAddress.setIsDefault("否");
            staffAddress.setPhone(staff.getReference());
            beans.add(staffAddress);
        }

        //账号密码表
        tct.setCustid(serverService.getServerID("custid"));
        tct.setStaffid(sf.getStaffID());
        tct.setPassword(intf);
        tct.setAccount(phones);
        tct.setPaymentCode(paymentCode);
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

        hqls=new String[]{"update TEshopCusCom u set u.teccDate=? where u.sccId=?"};
        parm=new ArrayList<Object[]>();
        parm.add(new Object[]{new Date(),tuicus.getSccId()});

        //保存推荐人信息
        MarKeting mk = new MarKeting();
        mk.setMkSccId(sccid);//推荐人
        mk.setUserSccId(tcc.getSccId());//被推荐人
        mk.setMkuserID(tuicus.getAccount());
        mk.setUserID(tcc.getAccount());
        mk.setMkdate(new Date());
        mk.setMkID(serverService.getServerID("MarKeting"));
        beans.add(mk);

        //保存购物卡信息
        Staff staff1 = new Staff();
        TEshopCusCom cus = new TEshopCusCom();
        String staffID = "";
        if(account==null){
            //app
            cus = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid = ?",new Object[]{sccid_app});
            if(sccid_app==null){
                //收银端
                Cookie[] cookies = request.getCookies();
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("user")){
                        staffID  =  cookie.getValue();
                        continue;
                    }
                }

            }else {
                staff1 = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{cus.getStaffid()});
            }

        }else {
            //5l5c
            staff1 = (Staff)baseBeanDao.getBeanByHqlAndParams("from Staff where staffID = ?",new Object[]{account.getStaffID()});
        }

        GiftCards gift  = new GiftCards();
        gift.setCardId(serverService.getServerID("giftCards"));
        gift.setStaffID(sf.getStaffID());
        gift.setCompanyID(account!=null?account.getCompanyID():"company20180510CQZCDKTT690000006064");
        gift.setCardNumber(giftCards.getCardNumber());
        gift.setApplyCardDate(new Date());
        gift.setState("1");
        gift.setOperator(staff1!=null?staff1.getStaffID():staffID);
        beans.add(gift);

        if(!tuicus.getSccId().equals("TEshopCusCom20161010W9FXK9NJ450000011682")){
            StringBuffer str=new StringBuffer();
            str.append(" select d.dbid,d.dbcarid from dt_deviceBind d, dt_devicebind_staff ds ");
            str.append(" where d.dbid = ds.dbsdbid and d.dbstatus = '1' ");
            str.append(" and ds.dbsstatus = '1' and ds.dbssccid = ? ");

            List<BaseBean> dlist=baseBeanDao.getListBeanBySqlAndParams(str.toString(),new Object[]{tuicus.getSccId()});
            if(dlist != null && dlist.size()>0) {
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
        }else{
            //没有推荐人时保存抢单需求
            DemandDetail demandDetail = new DemandDetail();
            demandDetail.setDdid(serverService.getServerID("ddetail"));
            demandDetail.setDdsccid(tcsccid);
            demandDetail.setDdtitle("新注册用户");
            demandDetail.setDdcontactname(staff.getStaffName());
            demandDetail.setDdcontactphone(phones);
            demandDetail.setDdscodeid("scode20170714cnjcrn5jm20000000067");
            demandDetail.setDdworktype("服务平台");
            demandDetail.setDdstatus("0");
            demandDetail.setDdBool('Y');
            demandDetail.setDdadddate(new Date());
            demandDetail.setDscount( 0 );
            beans.add(demandDetail);
        }

        baseBeanService.executeHqlsByParamsList(beans, hqls, parm);
    }


}
