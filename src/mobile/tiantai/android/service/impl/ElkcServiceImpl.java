package mobile.tiantai.android.service.impl;

import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import mobile.tiantai.android.service.ElkcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


@Service
public class ElkcServiceImpl implements ElkcService {
	
	@Resource
	private ServerService serverService;
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private BaseBeanService baseBeanService;



	@Transactional(propagation=Propagation.NOT_SUPPORTED)
	@Override
    public synchronized  Map<String, Object> elkclogin(String user)
    {
        String returnsccid = "";
        String returnstaffid = "";
        Map<String, Object> returnmap = new HashMap<String,Object>();
        List<BaseBean> beans=new ArrayList<BaseBean>();
        TEshopCustomer customer = (TEshopCustomer) baseBeanService
                .getBeanByHqlAndParams("from TEshopCustomer where account=? AND logOff=0",
                        new Object[] { user });

        TEshopCusCom tcc=(TEshopCusCom)baseBeanService
                .getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0 and acquiesce='01'",
                        new Object[] { user });

        if(customer!=null)
        {

            if(tcc==null){
                List<BaseBean> list = baseBeanService.
                        getListBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0 order by cusType",
                                new Object[]{user});
                tcc=(TEshopCusCom)list.get(0);
                tcc.setAcquiesce("01");
                beans.add(tcc);
            }

            returnsccid = tcc.getSccId();
            returnstaffid = tcc.getStaffid();

        }else{
            String  sccid="TEshopCusCom20161010W9FXK9NJ450000011682";

            TEshopCusCom tuicus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{sccid});

            Staff sf = new Staff();
            TEshopCustomer tct = new TEshopCustomer();
            TEshopCusCom ntc = new TEshopCusCom();
            sf.setStaffID(serverService.getServerID("Staff"));
            sf.setReference(user);//电话
            sf.setStaffName(user);//姓名默认写成电话
            //增加编号以及录入时间
            String phql = "select count(*) from Staff ";
            int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
            sf.setStaffCode("NO" + pcount);
            sf.setVerifyTime(new Date());
            sf.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
            sf.setStaus("01");// 默认身份证为空 为不确定人员
            beans.add(sf);
            //账号密码表
            tct.setStaffid(sf.getStaffID());
            tct.setPassword("123456");
            tct.setAccount(user);
            beans.add(tct);
            //权限表
            ntc.setState("1");
            ntc.setSccId(serverService.getServerID("TEshopCusCom"));
            ntc.setStaffid(sf.getStaffID());
            ntc.setAccount(user);
            ntc.setCusType("7");
            ntc.setTeccDate(new Date());
            ntc.setAcquiesce("01");//默认账户
            ntc.setSuperioragent(tuicus.getAccount());
            ntc.setSupperSccId(tuicus.getSccId());
            ntc.setPpid(tuicus.getPpid());
            beans.add(ntc);

            JoinFans jf1 = new JoinFans();
            jf1.setJfID(serverService.getServerID("jfID"));
            jf1.setFsccId(ntc.getSccId());
            jf1.setFaccount(ntc.getAccount());
            jf1.setStaffid(sf.getStaffID());
            jf1.setSource("e路快车注册");
            jf1.setState("00");
            jf1.setZsccId(sccid);
            jf1.setZaccount(tuicus.getAccount());

            JoinFans jf2 = new JoinFans();
            jf2.setJfID(serverService.getServerID("jfID"));
            jf2.setFsccId(sccid);
            jf2.setFaccount(tuicus.getAccount());
            jf2.setStaffid(tuicus.getStaffid());
            jf2.setSource("e路快车注册");
            jf2.setState("00");
            jf2.setZsccId(ntc.getSccId());
            jf2.setZaccount(ntc.getAccount());
            beans.add(jf1);
            beans.add(jf2);

            //保存推荐人信息
            MarKeting mk = new MarKeting();
            mk.setMkSccId(sccid);//推荐人
            mk.setUserSccId(ntc.getSccId());//被推荐人
            mk.setMkuserID(tuicus.getAccount());
            mk.setUserID(ntc.getAccount());
            mk.setMkdate(new Date());
            mk.setMkID(serverService.getServerID("MarKeting"));
            beans.add(mk);

            returnsccid = ntc.getSccId();
            returnstaffid = ntc.getStaffid();

        }
         if(beans.size()!=0) {
             baseBeanService.executeHqlsByParamsList(beans, null, null);
         }
        returnmap.put("sccid",returnsccid);
        returnmap.put("staffid",returnstaffid);

       return returnmap;
    }


}

