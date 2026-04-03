package hy.ea.collage.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.ProductComment;
import hy.ea.bo.finance.ProductCommentMain;
import hy.ea.bo.human.StaffPlatform;
import hy.ea.bo.human.StaffPosInfo;
import hy.ea.collage.service.SbqIndexSerivce;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 商帮圈首页
 *
 * @author [mz]
 * @version [1.0, 2018-11-16]
 * @see
 * @since
 */
@Service

public class SbqIndexServiceImpl implements SbqIndexSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;

    private Logger logger = Logger.getLogger(SbqIndexServiceImpl.class);
    /**
     * 根据平台获取平台的动态
     * @param pageNumber
     * @param pageSize
     * @param ccomIDPlatform
     * @return
     */
    public Map<String, Object> getPageFormInfo(int pageNumber, int pageSize, String ccomIDPlatform,String staffID){
        Map<String,Object> map = new HashMap<String,Object>();
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        if(ccomIDPlatform!=null&&!ccomIDPlatform.equals("")){
            sql.append("select * from (");
            sql.append("select pp.goodsName, TO_CHAR(pp.PackagingDate,'YYYY-MM-DD HH24:MI:SS') pdate,pp.goodsID,pp.ppid,s.staffID,s.staffname,s.photo,nvl(pm.praise,0),nvl(pm.plcount,0)");
            sql.append(" from dt_ProductPackaging pp left join dt_hr_staff s on pp.staffid = s.staffid inner join dt_phl_PlatformPackage pc on pc.ppid = pp.ppid left join dt_ProductCommentMain pm on pm.ppid = pp.ppid");
            sql.append(" where pp.type = '会员分享' and pp.review = '00' and  pp.companyid is null and pc.ccomIDPlatform = ?");

            sql.append(" union");
            sql.append(" select p.goodsName,TO_CHAR(p.PackagingDate,'YYYY-MM-DD HH24:MI:SS'),p.goodsID,p.ppid,m.ccompany_id,cc.companyname,cc.logopath,nvl(pm.praise,0),nvl(pm.plcount,0)");
            sql.append("  from dt_ProductPackaging p inner join DT_ccom_com m on p.companyid = m.compnay_id left join  dtcontactcompany cc on m.ccompany_id = cc.ccompanyid inner join dt_phl_PlatformPackage ppc on ppc.ppid = p.ppid left join dt_ProductCommentMain pm  on pm.ppid = p.ppid");
            sql.append(" where  p.type = '会员分享' and  p.review = '00' and ppc.ccomIDPlatform = ?");
            sql.append(") ll");
            objList.add(ccomIDPlatform);
            objList.add(ccomIDPlatform);
        }else {
            sql.append("select * from (");
            sql.append("select pp.goodsName,TO_CHAR(pp.PackagingDate,'YYYY-MM-DD HH24:MI:SS') pdate,pp.goodsID,pp.ppid,s.staffID,s.staffname,s.photo,nvl(pm.praise,0),nvl(pm.plcount,0)");
            sql.append(" from dt_ProductPackaging pp left join dt_hr_staff s on pp.staffid = s.staffid left join dt_ProductCommentMain pm on pm.ppid = pp.ppid");
            sql.append(" where pp.type = '会员分享' and pp.review = '00' and  pp.companyid is null");

            sql.append(" union");
            sql.append(" select p.goodsName,TO_CHAR(p.PackagingDate,'YYYY-MM-DD HH24:MI:SS'),p.goodsID,p.ppid,m.ccompany_id,cc.companyname,cc.logopath,nvl(pm.praise,0),nvl(pm.plcount,0)");
            sql.append(" from dt_ProductPackaging p inner join DT_ccom_com m on p.companyid = m.compnay_id left join  dtcontactcompany cc on m.ccompany_id = cc.ccompanyid left join dt_ProductCommentMain pm  on pm.ppid = p.ppid");
            sql.append(" where p.type = '会员分享' and  p.review = '00' and p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid and pm.ppid = p.ppid");
            sql.append(") ll order by ll.pdate desc");

        }

        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());

        map.put("pageForm",pageForm);
        if(pageForm!=null) {
           map = getComZanCount(pageForm.getList(), map, staffID);
        }

        return map;
    }

    /**
     *
     * 获取评论数和点赞数目以及显示的缩略图
     * @return
     */
    private  Map<String, Object> getComZanCount(List<BaseBean> list,Map<String, Object> map,String staffID){

        //是否点赞
        StringBuilder   sbs = new StringBuilder();
        sbs.append(" select  staffid,ppid from dt_ProductComment where ppid in(");
    //图片
        StringBuilder   sbs2 = new StringBuilder();
        sbs2.append(" select imgurl,goodsid from dtAttriProduction where goodsid in(");

        List<Object> param2 = new ArrayList<Object>();//点赞
        List<Object> param3 = new ArrayList<Object>();//图片


        for (int i = 0;i<list.size();i++){
             Object[] objs = (Object[])(Object)list.get(i);
             if(i==list.size()-1){
                 sbs.append("?");
                 sbs2.append("?");
             }else{
                 sbs.append("?,");
                 sbs2.append("?,");
             }

              param2.add(objs[3].toString());
              param3.add(objs[2].toString());
        }
        sbs.append(")");
        sbs2.append(")");

        sbs.append(" and type = ?  and staffId = ? group by ppid,staffid");
        sbs2.append(" and type = ?  order by sort");
        param2.add("1");
        param2.add(staffID);
        param3.add("2");

        List<BaseBean> dzlist = baseBeanDao.getListBeanBySqlAndParams(sbs.toString(),param2.toArray());

        Map<String,Object> mp2 = new HashMap<String,Object>();
        Map<String,List<BaseBean>> mp3 = new HashMap<String,List<BaseBean>>();



        for (int j = 0;j<dzlist.size();j++){
            Object[]  oobs = (Object[])(Object)dzlist.get(j);
            mp2.put(oobs[1].toString(),oobs[0]);
        }

        map.put("dz",mp2);
       //缩略图

        List<BaseBean> piclist = baseBeanDao.getListBeanBySqlAndParams(sbs2.toString(),param3.toArray());
        map.put("piclist",piclist);


        return map;

    }


    /**
     *
     * 给文章点赞
     * @param ppid
     * @param staffId
     * @return
     */
    @Transactional
    public String dzopr(String ppid,String goodsId,String staffId){

        String hql = "from ProductComment where ppid = ? and StaffId = ? and type = ? and pcPID is null";
        ProductComment pc =  (ProductComment)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{ppid,staffId,"1"});

        String hqlmain = "from ProductCommentMain where ppid = ?";
        ProductCommentMain pcm = (ProductCommentMain)baseBeanDao.getBeanByHqlAndParams(hqlmain,new Object[]{ppid});


        List<BaseBean> beanList = new ArrayList<BaseBean>();
        List<String> hqls = new ArrayList<String>();
        List<Object> params = new ArrayList<Object>();

        String result = "";
        if(pc==null){
            pcm.setPraise(pcm.getPraise()+1);

          pc = new ProductComment();
          pc.setPcID(serverService.getServerID("pct"));
          pc.setPpid(ppid);
          pc.setGoodsId(goodsId);
          pc.setStaffId(staffId);
          pc.setContent("");
          pc.setPraise(0);
          pc.setCommentdate(new Date());
          pc.setCount(0);
          pc.setPcPID("");
          pc.setType("1");
          pc.setIspraise("0");
          pc.setIscollect("0");
          pc.setIsread("0");
          pc.setToStaffId("");
          beanList.add(pc);
            result = "1";
        }else{
            //有内容
            pcm.setPraise(pcm.getPraise()-1);
            String hqldelete = "delete from ProductComment where pcID = ?";
            hqls.add(hqldelete);
            params.add(pc.getPcID());
            result = "2";
        }
        beanList.add(pcm);

        baseBeanDao.saveBeansListAndexecuteHqlsByParams(beanList, hqls.toArray(new String[]{}),params.toArray());




        return result;
    }

    /**
     *
     * 获取附近的人
     * @return
     */
    public PageForm getFjPeo(int pageNumber,int pageSize,String staffid){
        String hql = "from StaffPosInfo where staffID = ?";
        StaffPosInfo ps = (StaffPosInfo) baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{staffid});
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select f.staffid,f.staffname,f.sex,f.photo,getdistance(?,?,s.longitude,s.latitude) distance,pp.goodsname");
        sql.append(",TRUNC(months_between(sysdate, to_date(f.birthday,'yyyy-mm-dd'))/12) AS age,em.sccid,cm.ccompany_id");
        sql.append(" from dt_hr_staffposinfo s left join t_Eshop_Cuscom em on s.staffid = em.staffid and em.acquiesce='01' left join dt_ccom_com cm on em.companyid = cm.compnay_id left join dt_hr_staff f on s.staffid = f.staffid AND GETDISTANCE(?, ?, S.LONGITUDE, S.LATITUDE) < ? left join");
        sql.append(" (select max(p.packagingdate) time, p.staffid,max(p.goodsname) goodsname from dt_productpackaging p right join dt_hr_staffposinfo t on t.staffid = p.staffid");
        sql.append(" where p.type = '会员分享' group by p.staffid) pp on pp.staffid = s.staffid");
        sql.append("  where f.staffid!=?  order by distance");
        objList.add(ps.getLongitude());
        objList.add(ps.getLatitude());

        objList.add(ps.getLongitude());
        objList.add(ps.getLatitude());
        objList.add(2500);
        objList.add(staffid);

        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());

        return pageForm;


    }

     /*
     * 获取已创建的行业平台
     * @return
     */
    public PageForm getPlatForm(int pageNumber,int pageSize,String staffid){

        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select f.companyname,f.logopath,f.ccompanyid,t.companyid,t.ppid,d.goodsname,sm.staffid");
        sql.append(" from t_Eshop_Cuscom t inner join Dt_Ccom_Com cm on t.companyid = cm.compnay_id");
        sql.append(" left join dt_staffplatform sm on sm.ccompanyid = cm.ccompany_id  and sm.staffid=?");
        sql.append(" left join Dtcontactcompany f on f.ccompanyid = cm.ccompany_id");
        sql.append(" inner join dt_ProductPackaging d on t.ppid = d.ppid");
        objList.add(staffid);
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber,pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());
        return pageForm;

    }

    /**
     *
     * 关注平台
     * @param
     * @param ccomIDPlatform
     * @return
     */
    @Transactional
    public String  followPlatForm(TEshopCusCom tc,String ccomIDPlatform){

        List<BaseBean> beanList = new ArrayList<BaseBean>();
        List<String> hqls = new ArrayList<String>();
        List<Object[]> parmsList = new ArrayList<Object[]>();

        String result = "";

         String hql = "from StaffPlatform where staffID = ? and  ccompanyID = ?";
         StaffPlatform spf = (StaffPlatform)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{tc.getStaffid(),ccomIDPlatform});

        TEshopCusCom ltc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where companyId = (select c.comanyId from CcomCom c where c.ccompanyId = ?)",new Object[]{ccomIDPlatform});

        if(spf==null){
             //关注
             spf = new StaffPlatform();
             spf.setPlatformID(serverService.getServerID("staffPlatform"));
             spf.setStaffID(tc.getStaffid());
             spf.setCcompanyID(ccomIDPlatform);
             beanList.add(spf);
             JoinFans jf1  = (JoinFans)baseBeanDao.getBeanByHqlAndParams("from JoinFans where zsccId = ? and fsccId = ?",new Object[]{ltc.getSccId(),tc.getSccId()});
             if(jf1==null){
              jf1 = new JoinFans();
             jf1.setJfID(serverService.getServerID("jfID"));
             jf1.setStaffid(tc.getStaffid());
             jf1.setFaccount(tc.getAccount());
             jf1.setFsccId(tc.getSccId());

             jf1.setSource("关注");
             jf1.setState("00");
             jf1.setZaccount(ltc.getAccount());
             jf1.setZsccId(ltc.getSccId());
             jf1.setFansDate(new Date());
             jf1.setCompany(ltc.getPseudoCompanyName());
             beanList.add(jf1);
             };
             result = "1";
         }else{
             //取消关注
             String hqldelete = "delete StaffPlatform where staffID = ? and ccompanyID = ?";
             hqls.add(hqldelete);
             Object[] param1 = new Object[]{tc.getStaffid(),ccomIDPlatform};
             parmsList.add(param1);

            //取消关注
            String hqlfans = "delete JoinFans where zsccId = ? and fsccId = ?";
            hqls.add(hqlfans);
            Object[] param2 = new Object[]{ltc.getSccId(),tc.getSccId()};
            parmsList.add(param2);
             result = "2";
         }

        baseBeanDao.executeHqlsByParmsList(beanList, hqls.toArray(new String[]{}),parmsList);


        return result;
    }


}
