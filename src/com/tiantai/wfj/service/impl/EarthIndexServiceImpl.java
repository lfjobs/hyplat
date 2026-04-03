package com.tiantai.wfj.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.EarthIndexService;
import hy.ea.bo.human.StaffBrowseHistory;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class EarthIndexServiceImpl
        implements EarthIndexService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serveService;

    @Resource
    private BaseBeanService baseBeanService;


    @Resource
    private BaseBeanDao baseBeanDao;
    
    
   /**
    * 微分金手机号账号 user 获取用户
    */
	public TEshopCusCom getCusCom(String user){
		
		String hql = "from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?";
		
		TEshopCusCom tc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{user,"01"});
		
		  return tc;
	}

    /**
     * 微分金手机号账号 user 获取用户
     */
	public TEshopCustomer getCustomer(String user){
         String hql = "from TEshopCustomer where account = ? and logOff=0";
		
         TEshopCustomer tec = (TEshopCustomer)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{user});
		 return tec;
	}


    /**
     *
     * 获取浏览过的商家
     * @param pageNumber
     * @param pageSize
     * @param sccid
     * @return
     */
    public PageForm getBrowseCompanyList(int pageNumber, int pageSize, String sccid){

        PageForm pageForm = null;
        try {
            String sql = "select c.logopath,c.companyname,c.ccompanyID,c.industryType from dtContactCompany c,dt_hr_StaffBrowseHistory h where c.ccompanyID=h.ccompanyID and h.sccid = ? order by h.createDate desc";
             pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql, "select count(*) from(" + sql + ")", new Object[]{sccid});
        }catch (Exception e){
            e.printStackTrace();
        }
        return pageForm;
    }


    /**
     *
     * 获取浏览过资讯
     * @param pageNumber
     * @param pageSize
     * @param sccid
     * @return
     */
    public PageForm getBrowseNewsList(int pageNumber, int pageSize, String sccid){

        StringBuilder sql = new StringBuilder();
        List<Object> param = new ArrayList<Object>();
        sql.append(" select *  from (");
        sql.append(" select p.goodsName,to_char(p.PackagingDate, 'yyyy-MM-dd'),p.image,p.ppid,m.ccompany_Id,cc.companyname,chh.createDate");
        sql.append(" from dt_ProductPackaging p, DT_ccom_com m,dtcontactcompany cc,dt_hr_StaffBrowseHistory chh");
        sql.append(" where p.review = ? and p.delStatus = ? and p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid and chh.ppidzx = p.ppid and chh.sccid=?");
        param.add("00");
        param.add("00");
        param.add(sccid);
        sql.append(" union");

        sql.append(" select pp.goodsName,to_char(pp.PackagingDate, 'yyyy-MM-dd'),pp.image,pp.ppid,pp.companyid,s.staffname,ch.createDate");

        sql.append(" from dt_ProductPackaging pp, dt_hr_staff s,dt_hr_StaffBrowseHistory ch");
        sql.append(" where  pp.review = ? and pp.staffid = s.staffid and pp.companyid is null and ch.ppidzx = pp.ppid and ch.sccid = ?");

        sql.append(" )z order by z.createDate desc");

        param.add("00");
        param.add(sccid);




        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql.toString(),"select count(*) from("+sql+")",param.toArray());
        return pageForm;
    }


    /**
     *
     * 获取浏览过的小视频
     * @param pageNumber
     * @param pageSize
     * @param sccid
     * @return
     */
    public PageForm getBrowseVideoList(int pageNumber,int pageSize,String sccid){
         String sql = "select v.videoid,v.videourl,v.titlename,v.coverImgUrl,p.praisev from dt_DSVideo v,dt_ProductCommentVedio p,dt_hr_StaffBrowseHistory h where p.videoID = v.videoid and  v.videoid = h.videoID and v.state = '00' and h.modes = ? and h.sccid = ? order by h.createDate desc";
         PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql.toString(),"select count(*) from("+sql+")",new Object[]{"3",sccid});
         return pageForm;

    }

    /**
     *
     * 获取浏览过的商品
     * @param pageNumber
     * @param pageSize
     * @param sccid
     * @return
     */
    public PageForm getBrowseGoodsList(int pageNumber,int pageSize,String sccid){
        List<Object> parms = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        //获取当前时间
        SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = e.format(new Date());



        sql.append("select p.goodsname,ps.ppid,p.image,p.goodsid,p.companyid");
        sql.append(",cc.ccompany_Id,case when tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price ");

        sql.append(" from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
        sql.append("inner join dt_hr_StaffBrowseHistory sh on sh.ppidsp=p.ppid ");
        sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
        sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

        sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00' ");
        sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");


        sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

        sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
        sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
        sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
        sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");
        sql.append("where p.showweixin = '01' and p.qualified='1' and ps.state='00' and sh.sccid=? order by sh.createDate desc");

        parms.add(currentTime);
        parms.add(currentTime);
        parms.add(currentTime);
        parms.add(currentTime);
        parms.add(sccid);
        PageForm pageForm = baseBeanService.getPageFormBySQL(
                pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                parms.toArray());
        return pageForm;
    }

    /**
     *
     * 获取浏览过的应用
     * @param sccid
     * @return
     */
    public List<BaseBean> getBrowseAppList(String sccid){
      String hql = "from StaffBrowseHistory where modes = '5' and sccid = ? order by createDate desc";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,new Object[]{sccid});
      return list;
    }

    /**
     *
     * 添加浏览记录
     * @param sccid
     * @param appName
     * @param id
     */
    public void addBrowseHistory(String sccid,String appName,String id) {
        try {
            String hql = "from StaffBrowseHistory where appName = ? and sccid = ? and modes = ?";
            StaffBrowseHistory staffBrowseHistory = (StaffBrowseHistory) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{appName, sccid, "5"});

            List<BaseBean> beans = new ArrayList<BaseBean>();
            if (staffBrowseHistory == null) {
                staffBrowseHistory = new StaffBrowseHistory();
                staffBrowseHistory.setSbhid(serveService.getServerID("sbhid"));
                staffBrowseHistory.setAppName(appName);
                staffBrowseHistory.setCreateDate(new Date());
                staffBrowseHistory.setSccid(sccid);
                staffBrowseHistory.setModes("5");

            } else {
                staffBrowseHistory.setCreateDate(new Date());
            }

            beans.add(staffBrowseHistory);

            if(id!=null&&!id.equals("")) {
                if (appName != null && appName.equals("商家")) {

                    StaffBrowseHistory sh = (StaffBrowseHistory) baseBeanDao.getBeanByHqlAndParams("from StaffBrowseHistory where sccid = ? and modes = ? and ccompanyID = ?", new Object[]{sccid, "1", id});
                    if (sh == null) {
                        sh = new StaffBrowseHistory();
                        sh.setSbhid(serveService.getServerID("sbhid"));
                        sh.setCcompanyID(id);
                        sh.setCreateDate(new Date());
                        sh.setSccid(sccid);
                        sh.setModes("1");
                    } else {
                        sh.setCreateDate(new Date());
                    }
                    beans.add(sh);

                } else if (appName != null && appName.equals("资讯")) {

                    StaffBrowseHistory sh = (StaffBrowseHistory) baseBeanDao.getBeanByHqlAndParams("from StaffBrowseHistory where sccid = ? and modes = ? and ppidzx = ?", new Object[]{sccid, "2", id});
                    if (sh == null) {
                        sh = new StaffBrowseHistory();
                        sh.setSbhid(serveService.getServerID("sbhid"));
                        sh.setPpidzx(id);
                        sh.setCreateDate(new Date());
                        sh.setSccid(sccid);
                        sh.setModes("2");
                    } else {
                        sh.setCreateDate(new Date());
                    }
                    beans.add(sh);


                } else if (appName != null && appName.equals("视频")) {

                    StaffBrowseHistory sh = (StaffBrowseHistory) baseBeanDao.getBeanByHqlAndParams("from StaffBrowseHistory where sccid = ? and modes = ? and videoID = ?", new Object[]{sccid, "3", id});
                    if (sh == null) {
                        sh = new StaffBrowseHistory();
                        sh.setSbhid(serveService.getServerID("sbhid"));
                        sh.setVideoID(id);
                        sh.setCreateDate(new Date());
                        sh.setSccid(sccid);
                        sh.setModes("3");
                    } else {
                        sh.setCreateDate(new Date());
                    }
                    beans.add(sh);

                } else if (appName != null && appName.equals("购物")) {
                    StaffBrowseHistory sh = (StaffBrowseHistory) baseBeanDao.getBeanByHqlAndParams("from StaffBrowseHistory where sccid = ? and modes = ? and ppidsp = ?", new Object[]{sccid, "4", id});
                    if (sh == null) {
                        sh = new StaffBrowseHistory();
                        sh.setSbhid(serveService.getServerID("sbhid"));
                        sh.setPpidsp(id);
                        sh.setCreateDate(new Date());
                        sh.setSccid(sccid);
                        sh.setModes("4");
                    } else {
                        sh.setCreateDate(new Date());
                    }
                    beans.add(sh);
                }


            }
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}