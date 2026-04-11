package com.tiantai.wfj.service.impl;

import com.tiantai.wfj.bo.MarKeting;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.WfjAccountService;
import com.wechat.bo.WxUserInfo;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.InvestDeviceBind.DeviceBindStaff;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Transactional
@Service
public class WfjAccountServiceImpl
        implements WfjAccountService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService idgec;

    @Resource
    private BaseBeanService baseBeanService;


    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;



    private Logger logger = LoggerFactory.getLogger(WfjAccountServiceImpl.class);

    /**
     *
     * 微信授权后获取到OpenID，以及微信基本信息后创建系统账号
     * @param wxUserInfo，tui
     * @return
     */
    public String createAccount(WxUserInfo wxUserInfo, String tjsccid,String realPath){

        //注册的新用户
        TEshopCusCom zccus= null;
        if(wxUserInfo.getOpenid()!=null&&!wxUserInfo.getOpenid().equals("")){
            zccus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where openId = ?", new Object[]{wxUserInfo.getOpenid()});

        }else if(wxUserInfo.getUserid()!=null&&!wxUserInfo.getUserid().equals("")){
            zccus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where userId = ?", new Object[]{wxUserInfo.getUserid()});

        }
        //推荐人
        TEshopCusCom tuicus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{tjsccid});
        if(zccus!=null){
           return zccus.getSccId();
        }
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

            tjsccid="TEshopCusCom20161010W9FXK9NJ450000011682";
            tuicus=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{tjsccid});
            supercus= (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId=?", new Object[]{tuicus.getSupperSccId()});
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
                                "from TEshopCusCom where sccId=?", new Object[]{tjsccid});//平台账号下没有地税的推荐人
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
        if(wxUserInfo!=null&&wxUserInfo.getNickname()!=null){
            sf.setStaffName(wxUserInfo.getNickname());
        }
        sf.setStaffID(serverService.getServerID("Staff"));
        sf.setReference("");//电话
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
        tct.setPassword("");
        if(wxUserInfo.getOpenid()!=null&&!wxUserInfo.getOpenid().equals("")) {
            tct.setOpenId(wxUserInfo.getOpenid());

            if(wxUserInfo.getTel()!=null&&!wxUserInfo.getTel().equals("")){
                tct.setAccount(wxUserInfo.getTel());
            }else{
                tct.setAccount(wxUserInfo.getOpenid());

            }

        }else if(wxUserInfo.getUserid()!=null&&!wxUserInfo.getUserid().equals("")){
            tct.setUserId(wxUserInfo.getUserid());
            if(wxUserInfo.getTel()!=null&&!wxUserInfo.getTel().equals("")){
                tct.setAccount(wxUserInfo.getTel());
            }else{
                tct.setAccount(wxUserInfo.getUserid());

            }
            tct.setAccount(wxUserInfo.getUserid());


        }
        beans.add(tct);
        //权限表
        tcc.setState("1");
        tcc.setSccId(serverService.getServerID("TEshopCusCom"));
        tcc.setStaffid(sf.getStaffID());
        if(wxUserInfo.getOpenid()!=null&&!wxUserInfo.getOpenid().equals("")) {
            tcc.setOpenId(wxUserInfo.getOpenid());

            if(wxUserInfo.getTel()!=null&&!wxUserInfo.getTel().equals("")){
                tcc.setAccount(wxUserInfo.getTel());
            }else{
                tcc.setAccount(wxUserInfo.getOpenid());

            }

        } else if(wxUserInfo.getUserid()!=null&&!wxUserInfo.getUserid().equals("")){
            tcc.setUserId(wxUserInfo.getUserid());
            if(wxUserInfo.getTel()!=null&&!wxUserInfo.getTel().equals("")){
                tcc.setAccount(wxUserInfo.getTel());
            }else{
                tcc.setAccount(wxUserInfo.getUserid());

            }

        }
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
        jf1.setSource("微信公众账号注册");
        jf1.setState("00");
        jf1.setZsccId(tjsccid);
        jf1.setZaccount(tuicus.getAccount());

        JoinFans jf2 = new JoinFans();
        jf2.setJfID(serverService.getServerID("jfID"));
        jf2.setFsccId(tjsccid);
        jf2.setFaccount(tuicus.getAccount());
        jf2.setStaffid(tuicus.getStaffid());
        jf2.setSource("微信公众账号注册");
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
        mk.setMkSccId(tjsccid);//推荐人
        mk.setUserSccId(tcc.getSccId());//被推荐人
        mk.setMkuserID(tuicus.getAccount());
        mk.setUserID(tcc.getAccount());
        mk.setMkdate(new Date());
        mk.setMkID(serverService.getServerID("MarKeting"));
        beans.add(mk);


        if(!tuicus.getSccId().equals("TEshopCusCom20161010W9FXK9NJ450000011682")){
            StringBuffer str=new StringBuffer();
            str.append(" select d.dbid,d.dbcarid from dt_deviceBind d, dt_devicebind_staff ds ");
            str.append(" where d.dbid = ds.dbsdbid and d.dbstatus = '1' ");
            str.append(" and ds.dbsstatus = '1' and ds.dbssccid = ? ");

            List<BaseBean> dlist=beandao.getListBeanBySqlAndParams(str.toString(),new Object[]{tuicus.getSccId()});
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
        }

        baseBeanService.executeHqlsByParamsList(beans, hqls, parm);
       // fileUpload(wxUserInfo.getHeadimgurl(),realPath);
      return tcc.getSccId();

    }

    /**
     *
     * 判断是否是微信公众账号访问如果是引导跳转到微信授权页面
     * @param request
     * @param url
     * @return
     */
    public String  isWxLogin(HttpServletRequest request,String url) {
        String urlmicro = "";
        try {
//            System.out.print(url);
            String userAgent = request.getHeader("user-agent").toLowerCase();
            Boolean s = userAgent == null || userAgent.indexOf("micromessenger") == -1 ? false : true;

            if (s == true) {
               String urlcode =  URLEncoder.encode(url, "utf-8");

                urlmicro = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=" + urlcode + "&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";

            }
        }catch (Exception e){
            logger.error("操作异常", e);;
        }

        return  urlmicro;
    }

    /**
     * 根据产品ID
     * @param pid
     * @return
     */
    public String  getTjBycanzuo(String pid){

          String hql = " from TEshopCusCom t where t.companyId = (select c.companyID from Cater c where c.ppID = ?)";
          TEshopCusCom tc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{pid});
          return tc!=null?tc.getSccId():"";

    }



    /**
     * 使用url或者http存入文件
     * @Title: fileUpload
     * @param @param fileUrl  文件url，可以是http
     * @param @param path     文件存储路径
     * @return void
     *
     */
    public  void fileUpload (final String fileUrl,final String path){
         Thread t=new Thread(new Runnable(){
            public void run(){
                //读取文件
                String s1 = fileUrl;
                java.io.InputStream is = null; //定义一个输入流。
                BufferedInputStream bis = null;//定义一个带缓冲的输入流 。
                //写到本地
                BufferedOutputStream bos = null; //定义一个带缓冲的输出流。
                try{
                    java.net.URL url = new java.net.URL(s1);//创建一个URL对象。
                    is = url.openStream();//打开到此 URL 的连接并返回一个用于从该连接读入的 InputStream。
                    bis = new java.io.BufferedInputStream(is);
                    File file = new File(path);
                    if(!file.exists()){ //测试此抽象路径名表示的文件或目录是否存在。
                        file.createNewFile();   //创建此抽象路径名表示的文件或目录。
                    }
                    bos = new BufferedOutputStream(new FileOutputStream(file));;
                    byte[] b = new byte[1024]; //创建字节数组。
                    while(bis.read(b)!=-1){//输入流中的数据如果还有下一行(!=-1)将继续循环
                        bos.write(b);//将字节数组写入输出流。
                    }
                }catch(Exception   e){
                    logger.info("调试信息");
                }finally{
                    try{
                        bos.flush();//刷新此缓冲的输出流。
                        bis.close(); //关闭此输入流 。
                    }catch(Exception   e){
                        logger.info("调试信息");
                    }
                }
            }}
        );
        t.start();
    }


    /**
     *
     * 验证是否绑定手机号
     * @param openid
     * @return
     */
    public String validateAccount(String openid){
        TEshopCusCom wxtc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where openId = ?",new Object[]{openid});
        TEshopCustomer wxtr = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where openId = ?",new Object[]{openid});

            //没有账号，或者有账号，但是没绑定手机号
             if(wxtc==null||(wxtc.getAccount()!=null&&wxtc.getAccount().equals(openid))){


                 return "0";
             }else{

                 return wxtc.getSccId();
             }
    }


    /**
     *
     * 绑定账号
     * @param openid
     * @param tel
     * @param nickName
     * @return
     */
    public String bindTel(String openid,String tel,String nickName){

        List<BaseBean> beans = new ArrayList<BaseBean>();

        TEshopCusCom teltc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = '01'",new Object[]{tel});
        TEshopCustomer teltr = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0",new Object[]{tel});

        TEshopCusCom wxtc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where openId = ?",new Object[]{openid});
        TEshopCustomer wxtr = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where openId = ?",new Object[]{openid});
        String r = "0";
        if(teltr!=null){
            //手机注册过账号
            if(teltr.getOpenId()!=null&&!teltr.getOpenId().equals("")){
                //说明绑定微信了
                if(!teltr.getOpenId().equals(openid)){
                    //说明绑定微信了，但是绑定了其他微信了，需要用户换个手机号
                    r = "1";//说明手机号绑定过其他微信了
                }
            }else{
                //说明手机号注册过，但是没有绑定微信，
                if(wxtr==null) {
                  //  并且微信也没有注册过，直接关联Openid

                    teltc.setOpenId(openid);
                    teltr.setOpenId(openid);
                    beans.add(teltc);
                    beans.add(teltr);
                    r = teltc.getSccId();
                }else{
                    //同时微信注册过了，这个时候要保留手机号注册的账号，作废原来微信的账号
                    //有2个账号
                    teltc.setOpenId(openid);
                    teltr.setOpenId(openid);
                    r = teltc.getSccId();

                    wxtc.setOpenId(openid + tel);
                    wxtc.setAccount(openid + tel);
                    wxtr.setOpenId(openid + tel);
                    wxtr.setAccount(openid + tel);
                    wxtc.setLogOff("1");
                    wxtr.setLogOff("1");
                    beans.add(wxtr);
                    beans.add(wxtc);

                    //绑定成功
                    beans.add(teltr);
                    beans.add(teltc);
                }
            }
        }else{
            //手机没有注册过账号

            if(wxtr!=null){
                //微信注册过账号，但是没有绑定手机号，直接微信账号关联手机号
                wxtc.setAccount(tel);
                wxtr.setAccount(tel);
                beans.add(wxtc);
                beans.add(wxtr);
                r = wxtc.getSccId();

            }else{
                //微信也没注册过
                WxUserInfo   wxUserInfo = new WxUserInfo();
                wxUserInfo.setOpenid(openid);
                wxUserInfo.setNickname(nickName);
                wxUserInfo.setTel(tel);
                r   = createAccount(wxUserInfo, "", null);
               // 新生成的账号

            }

        }

        if(beans.size()>0) {
            baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        }


        return r; //说明成功绑定
    }
}