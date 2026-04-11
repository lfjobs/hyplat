package hy.ea.collage.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.ProductComment;
import hy.ea.bo.finance.ProductCommentMain;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.scmanage.DSVideo;
import hy.ea.bo.production.scmanage.ProductCommentVedio;
import hy.ea.bo.production.scmanage.ProductOfVedio;
import hy.ea.bo.production.scmanage.UserReadVideo;
import hy.ea.collage.service.DspIndexSerivce;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 短视频首页
 *
 * @author [mz]
 * @version [1.0, 2020-07-6]
 * @see
 * @since
 */
@Service
public class DspIndexServiceImpl implements DspIndexSerivce {
    @Resource
    private BaseBeanDao baseBeanDao;
    @Resource
    private ServerService serverService;
    @Resource
    private BaseBeanService baseBeanService;

    public List<BaseBean> getDsplist(String staffId, String type, int limit) {
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<>();

        if ("00".equals(type)) {//推荐
            sql.append("select v.videoid, v.vodid, v.vodprovider, v.videourl,v.titlename,v.staffid,f.staffname,f.headimage,");
            sql.append("to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), nvl(p.praisev,0),nvl(p.plcountv,0), nvl(p.sharev,0), nvl(c.ispraise,0),case when j.fsccid is null THEN 0 ELSE 1 END, v.coverImgUrl");
            sql.append(" ,po.ppid,po.image,po.goodsname from dt_DSVideo v left join dt_hr_staff f on v.staffid = f.staffid");
            sql.append(" left join dt_ProductCommentVedio p on v.videoid = p.videoid");
            sql.append(" left join dt_ProductComment c on c.vedioid = v.videoid and c.type='1' and c.pcpid is null and c.staffid = ?");
            sql.append(" left join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ?");
            sql.append(" left join dt_UserReadVideo u on u.vedioid = v.videoid and u.staffID = ?");
            sql.append(" left join (select o.videoid ,max(o.ppid) ppid,max(pp.image) image,max(pp.goodsname) goodsname from  Dt_Productofvedio o   left join dt_productpackaging pp on pp.ppid= o.ppid group by o.videoid) po on po.videoid = v.videoid");
            sql.append(" where v.state = ? order by u.urvid desc,dbms_random.value desc");
        } else {//关注
            sql.append("select v.videoid, v.vodid, v.vodprovider, v.videourl,v.titlename,v.staffid,f.staffname,f.headimage,");
            sql.append("to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), nvl(p.praisev,0),nvl(p.plcountv,0), nvl(p.sharev,0), nvl(c.ispraise,0),case when j.fsccid is null THEN 0 ELSE 1 END,v.coverImgUrl");
            sql.append(",po.ppid,po.image,po.goodsname  from dt_DSVideo v left join dt_hr_staff f on v.staffid = f.staffid");
            sql.append(" left join dt_ProductCommentVedio p on v.videoid = p.videoid");
            sql.append(" left join dt_ProductComment c on c.vedioid = v.videoid and c.type='1' and c.pcpid is null and c.staffid = ?");
            sql.append(" inner join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ?");
            sql.append(" left join dt_UserReadVideo u on u.vedioid = v.videoid and u.staffID = ?");
            sql.append(" left join (select o.videoid ,max(o.ppid) ppid,max(pp.image) image,max(pp.goodsname) goodsname from  Dt_Productofvedio o   left join dt_productpackaging pp on pp.ppid= o.ppid group by o.videoid) po on po.videoid = v.videoid");
            sql.append(" where v.state = ? order by u.urvid desc,v.createdate desc,dbms_random.value desc");
        }
        objList.add(staffId);
        objList.add(staffId);
        objList.add(staffId);
        objList.add("00");

        return (List<BaseBean>) baseBeanService.getListBeanBySqlAndParams(sql.toString(), objList.toArray());
    }

    /**
     * 查询全面屏
     *
     * @param parameter
     * @return
     */
    public PageForm getDsplist(int pageNumber, int pageSize, String parameter, String staffId, String type) {
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<>();

        if ("00".equals(type)) {//推荐
            sql.append(" select v.videoid, v.vodid, v.vodprovider, v.videourl,v.titlename,v.staffid,f.staffname,f.headimage,");
            sql.append(" to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), nvl(p.praisev,0),nvl(p.plcountv,0), nvl(p.sharev,0), nvl(c.ispraise,0),case when j.fsccid is null THEN 0 ELSE 1 END,v.coverImgUrl");
            sql.append(" ,po.ppid,po.image,po.goodsname from dt_DSVideo v left join dt_hr_staff f on v.staffid = f.staffid");
            sql.append(" left join dt_ProductCommentVedio p on v.videoid = p.videoid");
            sql.append(" left join dt_ProductComment c on c.vedioid = v.videoid and c.type='1' and c.pcpid is null and c.staffid = ?");
            sql.append(" left join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ?");
            sql.append(" left join dt_UserReadVideo u on u.vedioid = v.videoid and u.staffID = ?");
            sql.append(" left join (select o.videoid ,max(o.ppid) ppid,max(pp.image) image,max(pp.goodsname) goodsname from  Dt_Productofvedio o   left join dt_productpackaging pp on pp.ppid= o.ppid group by o.videoid) po on po.videoid = v.videoid");
            sql.append(" where v.state = ? order by u.urvid desc"); // ,dbms_random.value desc
        } else {//关注
            sql.append(" select v.videoid, v.vodid, v.vodprovider, v.videourl,v.titlename,v.staffid,f.staffname,f.headimage,");
            sql.append(" to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), nvl(p.praisev,0),nvl(p.plcountv,0), nvl(p.sharev,0), nvl(c.ispraise,0),case when j.fsccid is null THEN 0 ELSE 1 END,v.coverImgUrl");
            sql.append(" ,po.ppid,po.image,po.goodsname  from dt_DSVideo v left join dt_hr_staff f on v.staffid = f.staffid");
            sql.append(" left join dt_ProductCommentVedio p on v.videoid = p.videoid");
            sql.append(" left join dt_ProductComment c on c.vedioid = v.videoid and c.type='1' and c.pcpid is null and c.staffid = ?");
            sql.append(" inner join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ?");
            sql.append(" left join dt_UserReadVideo u on u.vedioid = v.videoid and u.staffID = ?");
            sql.append(" left join (select o.videoid ,max(o.ppid) ppid,max(pp.image) image,max(pp.goodsname) goodsname from  Dt_Productofvedio o   left join dt_productpackaging pp on pp.ppid= o.ppid group by o.videoid) po on po.videoid = v.videoid");
            sql.append(" where v.state = ? order by u.urvid desc,v.createdate desc"); // ,dbms_random.value desc
        }
        objList.add(staffId);
        objList.add(staffId);
        objList.add(staffId);
        objList.add("00");

        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,
                pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());
        return pageForm;

    }

    /**
     * 点赞或者取消赞
     *
     * @param videoID
     * @param
     */
    @Transactional
    public String addZan(String videoID, String staffID) {
        String result = "";

        List<BaseBean> baseBeansList = new ArrayList<BaseBean>();
        // 视频点赞总表
        String hqlpcv = "from ProductCommentVedio where videoID = ?";
        ProductCommentVedio pcv = (ProductCommentVedio) baseBeanDao
                .getBeanByHqlAndParams(hqlpcv, new Object[]{videoID});
        // 视频点赞个人表
        String hql = "from ProductComment where vedioID = ? and staffID = ? and type = ? and pcpid is null";
        ProductComment pc = (ProductComment) baseBeanDao.getBeanByHqlAndParams(
                hql, new Object[]{videoID, staffID, "1"});
        if (pc == null) {
            // 没有点赞 操作增加点赞

            pc = new ProductComment();
            pc.setPcID(serverService.getServerID("pcID"));
            pc.setType("1");
            pc.setStaffId(staffID);
            pc.setVedioID(videoID);
            pc.setCommentdate(new Date());
            pc.setPraise(1);
            pc.setCount(0);
            pc.setIspraise("1");

            if (pcv == null) {
                pcv = new ProductCommentVedio();
                pcv.setPcvID(serverService.getServerID("pcvID"));
                pcv.setPlcountv(0);
                pcv.setPraisev(1);
                pcv.setSharev(0);
                pcv.setVideoID(videoID);
            } else {
                pcv.setPraisev(pcv.getPraisev() + 1);

            }
            result = "1";// 点赞

        } else {
            if ("1".equals(pc.getIspraise())) {//已点赞
                // 已经点赞过，操作取消点赞
                pc.setIspraise("0");
                pc.setCommentdate(new Date());

                if (pcv != null && pcv.getPraisev() > 0) {

                    pcv.setPraisev(pcv.getPraisev() - 1);
                }

                result = "0";// 取消赞
            } else {//未点赞
                pc.setIspraise("1");
                pc.setCommentdate(new Date());

                pcv.setPraisev(pcv.getPraisev() + 1);


                result = "1";// 点赞

            }

        }
        baseBeansList.add(pc);
        baseBeansList.add(pcv);
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(baseBeansList, null,
                null);

        return result;

    }

    /**
     * 关注用户
     *
     * @param videoStaffID
     * @param staffID
     * @return
     */
    @Transactional
    public String careUser(String videoStaffID, String staffID) {
        List<BaseBean> beanList = new ArrayList<BaseBean>();
        List<String> hqls = new ArrayList<String>();
        List<Object[]> parmsList = new ArrayList<Object[]>();
        String hqlcus = "from TEshopCusCom where staffID = ? and acquiesce = ?";
        String result = "";
        TEshopCusCom videotc = (TEshopCusCom) baseBeanDao
                .getBeanByHqlAndParams(hqlcus, new Object[]{videoStaffID,
                        "01"});
        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams(
                hqlcus, new Object[]{staffID, "01"});

        JoinFans jf1 = (JoinFans) baseBeanDao.getBeanByHqlAndParams(
                "from JoinFans where zsccId = ? and fsccId = ?", new Object[]{
                        videotc.getSccId(), tc.getSccId()});
        if (jf1 == null) {
            // 没有关注 去操作关注
            jf1 = new JoinFans();
            jf1.setJfID(serverService.getServerID("jfID"));
            jf1.setStaffid(tc.getStaffid());
            jf1.setFaccount(tc.getAccount());
            jf1.setFsccId(tc.getSccId());

            jf1.setSource("短视频关注");
            jf1.setState("00");
            jf1.setZaccount(videotc.getAccount());
            jf1.setZsccId(videotc.getSccId());
            jf1.setFansDate(new Date());
            jf1.setCompany(videotc.getPseudoCompanyName());
            beanList.add(jf1);
            result = "1";
        } else {
            // 取消关注
            String hqlfans = "delete JoinFans where zsccId = ? and fsccId = ?";
            hqls.add(hqlfans);
            Object[] param = new Object[]{videotc.getSccId(), tc.getSccId()};
            parmsList.add(param);
            result = "0";

        }
        baseBeanDao.executeHqlsByParmsList(beanList,
                hqls.toArray(new String[]{}), parmsList);

        return result;
    }

    /**
     * 记录视频分享数
     *
     * @param videoID
     * @return
     */
    @Transactional
    public void addShare(String videoID) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        ProductCommentVedio pcv = (ProductCommentVedio) baseBeanDao.getBeanByHqlAndParams("from ProductCommentVedio where videoID = ?", new Object[]{videoID});
        if (pcv == null) {
            pcv = new ProductCommentVedio();
            pcv.setPpid(videoID);
            pcv.setPcvID(serverService.getServerID("pcvid"));
            pcv.setPlcountv(0);
            pcv.setPraisev(0);
            pcv.setSharev(1);
            pcv.setVideoID(videoID);

        } else {
            pcv.setSharev(pcv.getSharev() + 1);

        }

        beans.add(pcv);
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
    }

    /**
     * 评论包括给他人评论回复
     *
     * @param videoID
     * @param staffID
     * @param pcID
     * @param content
     * @param tostaffID
     * @return
     */
    @Transactional
    public String addPL(String videoID, String staffID, String pcID, String content, String tostaffID) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String hql = "from DSVideo where videoID = ?";
        DSVideo video = (DSVideo) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{videoID}); //评论的视频


        ProductComment tc = new ProductComment();
        tc.setPcID(serverService.getServerID("pcid"));
        tc.setCommentdate(new Date());
        tc.setContent(content);
        tc.setCount(0);
        tc.setGoodsId("");
        tc.setIscollect("0");
        tc.setIspraise("0");
        tc.setIsread("0");
        //评论中回复他人评论
        if (pcID != null && !pcID.equals("")) {
            tc.setPcPID(pcID);
            tc.setToStaffId(tostaffID);
        }

        tc.setPpid(video.getPpid());
        tc.setPraise(0);
        tc.setStaffId(staffID);
        tc.setType("0");
        tc.setVedioID(videoID);
        tc.setWhichFloor("1");
        beans.add(tc);


        ProductCommentVedio pcv = (ProductCommentVedio) baseBeanDao.getBeanByHqlAndParams("from ProductCommentVedio where videoID = ?", new Object[]{videoID});

        if (pcv == null) {
            pcv = new ProductCommentVedio();
            pcv.setPpid(video.getVideoID());
            pcv.setPcvID(serverService.getServerID("pcvid"));
            pcv.setPlcountv(1);
            pcv.setPraisev(0);
            pcv.setSharev(0);
            pcv.setVideoID(videoID);

        } else {
            pcv.setPlcountv(pcv.getPlcountv() + 1);

        }
        beans.add(pcv);
        ProductCommentMain pcm = (ProductCommentMain) baseBeanDao.getBeanByHqlAndParams("from ProductCommentMain where ppid = ?", new Object[]{video.getPpid()});
        if (pcm == null) {
            pcm = new ProductCommentMain();
            pcm.setPcmID(serverService.getServerID("pcmid"));
            pcm.setPlcount(1);
            pcm.setPraise(0);
            pcm.setPpid(video.getPpid());

        } else {
            pcm.setPlcount(pcm.getPlcount() + 1);

        }

        beans.add(pcm);
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, null, null);

        return tc.getPcID();
    }


    /**
     * 给评论点赞
     *
     * @param pcID
     * @param staffID
     * @return
     */
    @Transactional
    public String addPlZan(String pcID, String staffID) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        String r = "0";
        ProductComment pc = (ProductComment) baseBeanDao.getBeanByHqlAndParams("from ProductComment where pcID = ?", new Object[]{pcID});

        String hqld = "";
        String hql = "from ProductComment where type = ? and pcPID = ? and StaffId = ?";
        ProductComment ttc = (ProductComment) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{"1", pcID, staffID});
        if (ttc != null) {
            //说明已经点赞过了，那就是取消点赞的操作 //将点赞记录删除
            hqld = "delete from ProductComment where pcID = ?";

            //评论的赞减少数量
            pc.setPraise(pc.getPraise() - 1);
            beans.add(pc);
        } else {
            //说明没有点过赞，这次是点赞操作
            ttc = new ProductComment();
            ttc.setPcID(serverService.getServerID("pcid"));
            ttc.setPraise(0);
            ttc.setStaffId(staffID);
            ttc.setType("1");
            ttc.setPpid(pc.getPpid());
            ttc.setVedioID(pc.getVedioID());
            ttc.setPcPID(pcID);
            ttc.setCommentdate(new Date());
            ttc.setCount(0);
            ttc.setIspraise("0");
            beans.add(ttc);


            //评论的赞增加+1
            pc.setPraise(pc.getPraise() + 1);
            beans.add(pc);
            r = "1";
        }
        baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hqld}, new Object[]{ttc.getPcID()});

        return r;

    }

    /**
     * 获取视频的一级评论
     *
     * @param pageNumber
     * @param pageSize
     * @param videoID
     * @param staffID
     * @return 评论用户ID、昵称 、头像、 评论时间、 评论内容 、评论ID，被赞数，当前用户是否给点赞,作者是否赞过，回复该评论总数
     */
    public PageForm searchPL(int pageNumber, int pageSize, String videoID, String staffID, String videoStaffID) {
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select tpc.staffid, ff.staffname, ff.headimage, to_char(tpc.commentdate, 'yyyy-mm-dd hh24:mi:ss'), tpc.content, tpc.pcid, tpc.praise,");
        sql.append("case when du.pcpid is not null then 1 else 0 end cudz, case when zu.pcpid is not null then 1 else 0 end zzdz,nvl(rf.rfs,0)");
        sql.append(" from dt_ProductComment tpc left join dt_hr_staff ff on tpc.staffid = ff.staffid");
        sql.append(" left join (select tt.pcpid from dt_ProductComment tt where tt.staffid = ? and tt.vedioid = ? and tt.type = '1') du on tpc.pcid = du.pcpid");
        sql.append(" left join (select tz.pcpid from dt_ProductComment tz where tz.staffid = ? and tz.vedioid = ? and tz.type = '1') zu on tpc.pcid = zu.pcpid");
        sql.append(" left join (select re.pcpid, count(re.pcpid) rfs from dt_ProductComment re where re.vedioid = ?  and re.pcpid is not null and re.type = '0' group by re.pcpid) rf on rf.pcpid = tpc.pcid");
        sql.append(" where tpc.vedioid = ? and tpc.type = '0' and tpc.tostaffid is null and tpc.pcpid is null order by tpc.praise desc,tpc.commentdate desc");

        objList.add(staffID);//当前用户
        objList.add(videoID);
        objList.add(videoStaffID);//作者
        objList.add(videoID);
        objList.add(videoID);
        objList.add(videoID);
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,
                pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());
        return pageForm;
    }

    public Object getPL(String videoID, String staffID, String videoStaffID, String pcID) {
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select tpc.staffid, ff.staffname, ff.headimage, to_char(tpc.commentdate, 'yyyy-mm-dd hh24:mi:ss'), tpc.content, tpc.pcid, tpc.praise,");
        sql.append("case when du.pcpid is not null then 1 else 0 end cudz, case when zu.pcpid is not null then 1 else 0 end zzdz,nvl(rf.rfs,0)");
        sql.append(" from dt_ProductComment tpc left join dt_hr_staff ff on tpc.staffid = ff.staffid");
        sql.append(" left join (select tt.pcpid from dt_ProductComment tt where tt.staffid = ? and tt.vedioid = ? and tt.type = '1') du on tpc.pcid = du.pcpid");
        sql.append(" left join (select tz.pcpid from dt_ProductComment tz where tz.staffid = ? and tz.vedioid = ? and tz.type = '1') zu on tpc.pcid = zu.pcpid");
        sql.append(" left join (select re.pcpid, count(re.pcpid) rfs from dt_ProductComment re where re.vedioid = ?  and re.pcpid is not null and re.type = '0' group by re.pcpid) rf on rf.pcpid = tpc.pcid");
        sql.append(" where tpc.vedioid = ? and tpc.type = '0' and tpc.tostaffid is null and tpc.pcpid is null and tpc.pcid = ? ");
        sql.append(" order by tpc.praise desc,tpc.commentdate desc");

        objList.add(staffID);//当前用户
        objList.add(videoID);
        objList.add(videoStaffID);//作者
        objList.add(videoID);
        objList.add(videoID);
        objList.add(videoID);
        objList.add(pcID);

        return baseBeanService.getObjectBySqlAndParams( sql.toString(), objList.toArray());
    }

    /**
     * 获取视频的评论的回复
     *
     * @param pageNumber
     * @param pageSize
     * @param videoID
     * @param staffID
     * @return
     */
    public PageForm searchReplyPL(int pageNumber, int pageSize, String videoID, String staffID, String videoStaffID, String pcpid) {

        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select tpc.staffid, ff.staffname, ff.headimage, to_char(tpc.commentdate, 'yyyy-mm-dd hh24:mi:ss'), tpc.content, tpc.pcid, tpc.praise,");
        sql.append("case when du.pcpid is not null then 1 else 0 end cudz, case when zu.pcpid is not null then 1 else 0 end zzdz,tpc.tostaffid tostaffid,tostaff.staffname toname");
        sql.append(" from dt_ProductComment tpc left join dt_hr_staff ff on tpc.staffid = ff.staffid");
        sql.append(" left join (select tt.pcpid from dt_ProductComment tt where tt.staffid = ? and tt.vedioid = ? and tt.type = '1') du on tpc.pcid = du.pcpid");
        sql.append(" left join (select tz.pcpid from dt_ProductComment tz where tz.staffid = ? and tz.vedioid = ? and tz.type = '1') zu on tpc.pcid = zu.pcpid");
        sql.append(" left join dt_hr_staff tostaff on tpc.tostaffid = tostaff.staffid");
        sql.append(" where tpc.pcpid = ? and tpc.vedioid = ? and tpc.type = '0'  order by  tpc.praise desc,tpc.commentdate desc");

        objList.add(staffID);//当前用户
        objList.add(videoID);
        objList.add(videoStaffID);//作者
        objList.add(videoID);

        objList.add(pcpid);
        objList.add(videoID);
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,
                pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());
        return pageForm;
    }

    /**
     * 获取视频的评论的回复
     *
     * @param videoID
     * @param staffID
     * @return
     */
    public Object getReplyPL(String videoID, String staffID, String videoStaffID, String pcpid, String pcID) {

        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select tpc.staffid, ff.staffname, ff.headimage, to_char(tpc.commentdate, 'yyyy-mm-dd hh24:mi:ss'), tpc.content, tpc.pcid, tpc.praise,");
        sql.append("case when du.pcpid is not null then 1 else 0 end cudz, case when zu.pcpid is not null then 1 else 0 end zzdz,tpc.tostaffid tostaffid,tostaff.staffname toname");
        sql.append(" from dt_ProductComment tpc left join dt_hr_staff ff on tpc.staffid = ff.staffid");
        sql.append(" left join (select tt.pcpid from dt_ProductComment tt where tt.staffid = ? and tt.vedioid = ? and tt.type = '1') du on tpc.pcid = du.pcpid");
        sql.append(" left join (select tz.pcpid from dt_ProductComment tz where tz.staffid = ? and tz.vedioid = ? and tz.type = '1') zu on tpc.pcid = zu.pcpid");
        sql.append(" left join dt_hr_staff tostaff on tpc.tostaffid = tostaff.staffid");
        sql.append(" where tpc.pcpid = ? and tpc.vedioid = ? and tpc.type = '0' and tpc.pcId = ? ");
        sql.append(" order by  tpc.praise desc,tpc.commentdate desc");

        objList.add(staffID);//当前用户
        objList.add(videoID);
        objList.add(videoStaffID);//作者
        objList.add(videoID);

        objList.add(pcpid);
        objList.add(videoID);
        objList.add(pcID);

        return baseBeanService.getObjectBySqlAndParams( sql.toString(), objList.toArray());
    }

    /**
     * 已阅读
     *
     * @param staffID
     * @param videoID
     * @return
     */
    @Transactional
    public String addReadNum(String staffID, String videoID) {
        List<BaseBean> beans = new ArrayList<BaseBean>();
        try {
            String hql = "from UserReadVideo where vedioID = ? and staffID = ?";
            UserReadVideo urv = (UserReadVideo) baseBeanDao
                    .getBeanByHqlAndParams(hql,
                            new Object[]{videoID, staffID});
            if (urv == null) {
                urv = new UserReadVideo();
                urv.setUrvID(serverService.getServerID("urvID"));
                urv.setReadDate(new Date());
                urv.setStaffID(staffID);
                urv.setVedioID(videoID);
                beans.add(urv);
                //视频增加阅读量
                String hqlv = "from DSVideo where videoID = ?";

                DSVideo dsv = (DSVideo) baseBeanDao
                        .getBeanByHqlAndParams(hqlv,
                                new Object[]{videoID});
                if (dsv != null) {
                    dsv.setReadnum(dsv.getReadnum() + 1);
                }
                beans.add(dsv);
                baseBeanDao.saveBeansListAndexecuteHqlsByParams(beans, null, null);
                return "0";
            }
        } catch (Exception e) {
            return "1";

        }
        return "0";

    }

    /**
     * 获取个人基本信息
     *
     * @param staffID
     * @return
     */
    public Staff getBaseInfo(String staffID) {
        //头像，简介，姓名，
        String hql = "from Staff where staffID = ?";
        Staff staff = (Staff) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{staffID});

        return staff;

    }


    /**
     * 获取用户信息
     *
     * @param staffID
     * @return
     */
    public TEshopCusCom getTc(String staffID) {

        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where staffID = ? and acquiesce = ?", new Object[]{staffID, "01"});
        if (tc == null) {
            List<BaseBean> tclist = baseBeanDao.getListBeanByHqlAndParams("from TEshopCusCom where staffID = ?  order by cusType", new Object[]{staffID});
            tc = (TEshopCusCom) tclist.get(0);

        }

        return tc;

    }

    /**
     * 或者关注数或者粉丝数
     *
     * @param staffID
     * @param zf      z:关注，f:粉丝
     * @return
     */
    public int getJoinFans(String staffID, String zf) {
        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where staffID = ? and acquiesce = ?", new Object[]{staffID, "01"});
        if (tc == null) {
            List<BaseBean> tclist = baseBeanDao.getListBeanByHqlAndParams("from TEshopCusCom where staffID = ?  order by cusType", new Object[]{staffID});
            if (!tclist.isEmpty()) {
                tc = (TEshopCusCom) tclist.get(0);
            }
        }
        String hql = "";
        int num = 0;
        if (tc != null) {
            if ("f".equals(zf)) {
                //查询粉丝数
                hql = "select count(*) from JoinFans where zsccId = ?";
                num = baseBeanDao.getConutByByHqlAndParams(hql, new Object[]{tc.getSccId()});
            } else {
                //查询关注数
                hql = " select count(*) from JoinFans where  fsccId = ?";
                num = baseBeanDao.getConutByByHqlAndParams(hql, new Object[]{tc.getSccId()});
            }
        }
        return num;
    }

    /**
     * 分享页，作者视频
     *
     * @param pageNumber
     * @param pageSize
     * @param staffID
     * @param videoStaffID
     * @return
     */
    public PageForm getShareVideoLists(int pageNumber, int pageSize, String staffID, String videoStaffID) {
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select v.videoid,v.videourl,v.titlename,v.staffid,f.staffname,nvl(f.headimage,0),");
        sql.append("to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), nvl(p.praisev,0),nvl(p.plcountv,0), nvl(p.sharev,0), nvl(c.ispraise,0),case when j.fsccid is null THEN 0 ELSE 1 END,v.coverImgUrl");
        sql.append(" from dt_DSVideo v left join dt_hr_staff f on v.staffid = f.staffid ");
        sql.append(" left join dt_ProductCommentVedio p on v.videoid = p.videoid");
        sql.append(" left join dt_ProductComment c on c.vedioid = v.videoid and c.type='1' and c.staffid = ?");
        sql.append(" left join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ?");
        sql.append(" where v.state = ? and v.staffid = ? order by v.createdate desc");

        objList.add(staffID);
        objList.add(staffID);
        objList.add("00");
        objList.add(videoStaffID);
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,
                pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());

        return pageForm;
    }

    /**
     * 作者浏览过的视频
     *
     * @param pageNumber
     * @param pageSize
     * @param staffID
     * @param videoStaffID
     * @return
     */
    public PageForm getViewVideoLists(int pageNumber, int pageSize, String staffID, String sccid, String videoStaffID) {

        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();
        sql.append("select v.videoid,v.videourl,v.titlename,v.staffid,f.staffname,nvl(f.headimage,0),");
        sql.append("to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), nvl(p.praisev,0),nvl(p.plcountv,0), nvl(p.sharev,0), nvl(c.ispraise,0),case when j.fsccid is null THEN 0 ELSE 1 END,v.coverImgUrl");
        sql.append(" from dt_DSVideo v left join dt_hr_staff f on v.staffid = f.staffid ");
        sql.append(" inner join dt_hr_StaffBrowseHistory h on  h.videoID = v.videoid");
        sql.append(" left join dt_ProductCommentVedio p on v.videoid = p.videoid");
        sql.append(" left join dt_ProductComment c on c.vedioid = v.videoid and c.type='1' and c.staffid = ?");
        sql.append(" left join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ?");
        sql.append(" where v.state = ? and h.sccid = ? order by h.createdate desc");

        objList.add(staffID);
        objList.add(staffID);
        objList.add("00");
        objList.add(sccid);
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber,
                pageSize, sql.toString(), "select count(*) from (" + sql + ")",
                objList.toArray());

        return pageForm;
    }

    /**
     * 获取我的视频
     *
     * @param staffID
     * @return
     */
    public PageForm getMyVideoList(int pageNumber, int pageSize, String staffID, String state) {
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        sql.append("select v.videoid, v.vodid, v.vodprovider, v.videourl, v.titlename, v.staffid, ");
        sql.append("f.staffname, f.headimage, to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), ");
        sql.append("nvl(p.praisev, 0), nvl(p.plcountv, 0), nvl(p.sharev, 0), nvl(v.readnum, 0), ");
        sql.append("case when j.fsccid is null THEN 0 ELSE 1 END, v.coverImgUrl, po.ppid, po.image, po.goodsname from dt_DSVideo v ");
        sql.append("left join dt_hr_staff f on v.staffid = f.staffid ");
        sql.append("left join dt_ProductCommentVedio p on v.videoid = p.videoid ");
        sql.append("left join dt_ProductComment c on c.vedioid = v.videoid and c.type = '1' and c.pcpid is null and c.staffid = ? ");
        sql.append("left join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ? ");
        sql.append("left join dt_UserReadVideo u on u.vedioid = v.videoid and u.staffID = ? ");
        sql.append("left join (select o.videoid, max(o.ppid) ppid, max(pp.image) image, max(pp.goodsname) goodsname  from Dt_Productofvedio o  left join dt_productpackaging pp on pp.ppid = o.ppid  group by o.videoid) po on po.videoid = v.videoid ");
        sql.append("where ");
        sql.append("v.state = ? and v.STAFFID = ? ");
        sql.append(" order by v.istop desc,v.createdate desc");

        objList.add(staffID);
        objList.add(staffID);
        objList.add(staffID);
        objList.add(state);
        objList.add(staffID);

        return baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql + ")", objList.toArray());
    }

    /**
     * 获取点赞过的视频
     *
     * @param staffID
     * @return
     */
    public PageForm getlikeVideoList(int pageNumber, int pageSize, String staffID) {

        List<Object> objList = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select v.videoid, v.vodid, v.vodprovider, v.videourl, v.titlename, v.staffid, ");
        sql.append("f.staffname, f.headimage, to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), ");
        sql.append("nvl(p.praisev, 0), nvl(p.plcountv, 0), nvl(p.sharev, 0), nvl(c.ispraise, 0), ");
        sql.append("case when j.fsccid is null THEN 0 ELSE 1 END, v.coverImgUrl, po.ppid, po.image, po.goodsname from dt_DSVideo v ");
        sql.append("left join dt_hr_staff f on v.staffid = f.staffid ");
        sql.append("left join dt_ProductCommentVedio p on v.videoid = p.videoid ");
        sql.append("left join dt_ProductComment c on c.vedioid = v.videoid and c.type = '1' and c.pcpid is null and c.staffid = ? ");
        sql.append("left join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ? ");
        sql.append("left join dt_UserReadVideo u on u.vedioid = v.videoid and u.staffID = ? ");
        sql.append("left join (select o.videoid, max(o.ppid) ppid, max(pp.image) image, max(pp.goodsname) goodsname  from Dt_Productofvedio o  left join dt_productpackaging pp on pp.ppid = o.ppid  group by o.videoid) po on po.videoid = v.videoid ");
        sql.append("where v.state = ? and v.STAFFID = ? and  c.type = ? and c.ispraise = ? ");
        sql.append(" order by c.commentdate desc");

        objList.add(staffID);
        objList.add(staffID);
        objList.add(staffID);
        objList.add("00");
        objList.add(staffID);
        objList.add("1");
        objList.add("1");

        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql + ")", objList.toArray());
        return pageForm;
    }

    /**
     * 查询一个视频的展示信息
     *
     * @return
     */
    public Object getVideoDetail(String videoID, String staffId) {
        StringBuilder sql = new StringBuilder();
        List<Object> objList = new ArrayList<Object>();

        sql.append("select v.videoid, v.vodid, v.vodprovider, v.videourl,v.coverImgUrl, v.titlename, v.staffid, f.staffname, f.headimage,");
        sql.append("to_char(v.createdate, 'yyyy-mm-dd hh24:mi:ss'), nvl(p.praisev,0),nvl(p.plcountv,0), nvl(p.sharev,0), nvl(c.ispraise,0),case when j.fsccid is null THEN 0 ELSE 1 END,");
        sql.append("o.ppid,pp.image,pp.goodsname,o.pricetype from dt_DSVideo v left join dt_hr_staff f on v.staffid = f.staffid");
        sql.append(" left join dt_ProductCommentVedio p on v.videoid = p.videoid");
        sql.append(" left join dt_ProductComment c on c.vedioid = v.videoid and c.type='1' and c.staffid = ?  and c.pcpid is null");
        sql.append(" left join Dtjoinfans j on j.zsccid = f.sccid and j.staffid = ?");
        sql.append(" left join dt_UserReadVideo u on u.vedioid = v.videoid and u.staffID = ?");
        sql.append(" left join Dt_Productofvedio o on o.videoid = v.videoid");
        sql.append(" left join dt_productpackaging pp on pp.ppid = o.ppid");
        sql.append(" where v.videoid = ? and rownum = 1");

        objList.add(staffId);
        objList.add(staffId);
        objList.add(staffId);
        objList.add(videoID);
        Object obj = baseBeanDao.getObjectBySqlAndParams(sql.toString(), objList.toArray());
        return obj;
    }


    /**
     * 发布视频
     *
     * @param video
     * @param staffID
     * @return
     */
    @Transactional
    public String pubVideo(DSVideo video, String staffID, String ppids, String priceType) {

        List<BaseBean> beanList = new ArrayList<BaseBean>();
        String result = "0";
        try {
            video.setVideoID(serverService.getServerID("videoid"));
            video.setCreateDate(new Date());
            video.setIsTop("00");
            if (video.getState() == null || video.getState().equals("")) {
                video.setState("00");
            }
            video.setStaffID(staffID);
            video.setReadnum(0);
            beanList.add(video);

            //保存商品关联
            if (ppids != null) {
                String[] ppIdList = ppids.split(",");
                for (String ppid : ppIdList) {
                    ProductOfVedio videoProduct = new ProductOfVedio();
                    videoProduct.setPvID(serverService.getServerID("pvid"));
                    videoProduct.setPpid(ppid);
                    videoProduct.setPricetype(priceType);
                    videoProduct.setVideoID(video.getVideoID());
                    beanList.add(videoProduct);
                }
            }
            baseBeanDao.saveBeansListAndexecuteHqlsByParams(beanList, null, null);
        } catch (Exception e) {
            logger.error("操作异常", e);
            result = "1";
        }
        return result;
    }


    /**
     * 是否关注
     *
     * @param videoStaffID
     * @param staffID
     * @return
     */
    public String isCare(String videoStaffID, String staffID) {
        String hql = "from JoinFans j where j.zsccId = (select f.sccid from Staff f where f.staffID = ?) and j.staffid = ?";
        List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql, new Object[]{videoStaffID, staffID});
        if (list != null && list.size() > 0) {
            return "1";
        }
        return "0";
    }

    /**
     * 获取视频商品
     *
     * @param priceType
     * @param pageNumber
     * @param pageSize
     * @param parameter
     * @param sccId
     * @return
     */
    public PageForm getDspProducts(String priceType, int pageNumber, int pageSize, String parameter, String sccId) {
        TEshopCusCom tc = (TEshopCusCom) baseBeanDao.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
        String companyID = "";
        if (tc != null) {
            companyID = tc.getCompanyId();
        }


        List<Object> parms = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        if ("1".equals(priceType)) {


            //获取当前时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = e.format(new Date());

            sql.append("select p.goodsid,p.ppid,p.goodsname,p.image,case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price");
            sql.append(",t.companyId,cm.ccompanyID,t.COMPANYNAME from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
            sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
            sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

            sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

            sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
            sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");


            sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

            sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
            sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
            sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
            sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");

            sql.append("where p.showweixin = ? and p.qualified=? ");
            sql.append(" and p.type not in(?,?,?,?,?,?,'扫码收款') ");
            sql.append(" and cm.industrytype not like '%餐饮%' ");
            sql.append(" and ps.state='00' ");
            parms.add(currentTime);
            parms.add(currentTime);
            parms.add(currentTime);
            parms.add(currentTime);
            parms.add("01");
            parms.add("1");
            String[] temp = {"个人会员", "公司会员", "省级城市", "县级城市", "乡镇城市", "村级新城"};
            Collections.addAll(parms, temp);
            if (parameter != null && !"".equals(parameter)) {
                sql.append(" and p.goodsname like ?");
                parms.add("%" + parameter.trim() + "%");
            }
            if (!"".equals(companyID)) {

                sql.append(" order by case when p.companyId = ? THEN 0 ELSE 1 END,p.packagingdate desc");
                parms.add(companyID);
            } else {
                sql.append(" order by p.packagingdate desc");
            }


        } else {
            sql.append("select pp.goodsid,pp.ppid,pp.goodsname,pp.image,round(sp.wholesale*(1+nvl(sz.total_pct,0)/100),2) pri");
            sql.append(",cc.compnay_id,cm.ccompanyID,cm.COMPANYNAME from dt_pro_wholesale sp  left join dt_productpackaging pp on sp.ppid = pp.ppid ");
            sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = pp.companyid left join dtCompany t on t.companyid = cc.compnay_id");
            sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
            sql.append(" where pp.showweixin = ? and pp.qualified=? and sp.state='00' ");
            parms.add("01");
            parms.add("1");
            if (parameter != null && !"".equals(parameter)) {
                sql.append(" and pp.goodsname like ?");
                parms.add("%" + parameter.trim() + "%");
            }
            if (!"".equals(companyID)) {
                sql.append(" order by case when pp.companyId = ? THEN 0 ELSE 1 END,pp.packagingdate desc");

                parms.add(companyID);
            } else {
                sql.append(" order by pp.packagingdate desc");
            }
        }
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());

        return pageForm;
    }


    /**
     * 获取视频商品
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageForm getDspProductsList(int pageNumber, int pageSize, String videoID, String ppid) {

        String hql = " from ProductOfVedio where videoID = ? and rownum = 1";

        ProductOfVedio productOfVedio = (ProductOfVedio) baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{videoID});
        String pricetype = "";
        if (productOfVedio != null) {
            pricetype = productOfVedio.getPricetype();
        }


        List<Object> parms = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        if ("1".equals(pricetype) || "".equals(pricetype)) {

            //零售
            //获取当前时间
            SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = e.format(new Date());

            sql.append("select p.goodsid,p.ppid,p.goodsname,p.image,case when  tpa.type is not null then ROUND(tpap.actPrice*(1+nvl(sz.total_pct,0)/100),2) when pa.type is not null then ROUND(pap.actPrice*(1+nvl(sz.total_pct,0)/100),2)  else ROUND(ps.re_price*(1+nvl(sz.total_pct,0)/100),2)  end price");
            sql.append(",t.companyId,cm.ccompanyID,2 from DT_PRO_SETUP ps inner join dt_ProductPackaging p on ps.ppid = p.ppid ");
            sql.append(" left join dt_ProductOfVedio v on p.ppid = v.ppid ");
            sql.append("left join dt_pro_activity_price tpap on tpap.ppid = p.ppid and tpap.state='00' ");
            sql.append("left join dt_pro_activity tpa on tpa.activityId = tpap.activityid and tpa.type='01' and (tpa.state!='02' and tpa.state!='04') ");

            sql.append("and tpa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and tpa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

            sql.append("left join dt_pro_activity_price pap on pap.ppid = p.ppid and pap.state='00'");
            sql.append("left join dt_pro_activity pa on pa.activityId = pap.activityid and pa.type='00' and (pa.state!='02' and pa.state!='04')");


            sql.append("and pa.endtime>=to_date(?,'yyyy-MM-dd HH24:MI:SS') and pa.starttime<=to_date(?,'yyyy-MM-dd HH24:MI:SS') ");

            sql.append("left join Dt_Ccom_Com cc on cc.compnay_id = p.companyid ");
            sql.append("left join dtCompany t on t.companyid = cc.compnay_id ");
            sql.append("left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid ");
            sql.append("left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01' ");

            sql.append("where p.showweixin = ? and p.qualified=? and  v.videoID = ?");

            sql.append(" and ps.state='00' ");
            parms.add(currentTime);
            parms.add(currentTime);
            parms.add(currentTime);
            parms.add(currentTime);
            parms.add("01");
            parms.add("1");
            parms.add(videoID);
            if (ppid != null && !ppid.equals("")) {
                sql.append(" order by CASE WHEN p.ppid = ? THEN 0 ELSE 1 END");
                parms.add(ppid);
            }


        } else {
            //批发
            sql.append("select pp.goodsid,pp.ppid,pp.goodsname,pp.image,round(sp.wholesale*(1+nvl(sz.total_pct,0)/100),2) pri");
            sql.append(" ,cc.compnay_id,cm.ccompanyID,1 from dt_pro_wholesale sp  left join dt_productpackaging pp on sp.ppid = pp.ppid ");
            sql.append(" left join dt_ProductOfVedio v on pp.ppid = v.ppid ");
            sql.append(" left join Dt_Ccom_Com cc on cc.compnay_id = pp.companyid left join dtCompany t on t.companyid = cc.compnay_id");
            sql.append(" left join dtContactCompany cm on cc.ccompany_id = cm.ccompanyid left join DT_SET_SUBSIDIZE sz on sz.gtid = cm.industrytype and sz.stutas = '01'");
            sql.append(" where v.videoID = ? and sp.state='00'");


            parms.add(videoID);
            if (ppid != null && !ppid.equals("")) {
                sql.append(" order by CASE WHEN pp.ppid = ? THEN 0 ELSE 1 END");
                parms.add(ppid);
            }
        }
        PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql.toString() + ")", parms.toArray());

        return pageForm;


    }

    /**
     * 删除视频
     *
     * @param videoID
     */
    public void deleteVideo(String videoID) {

        String hql = "update DSVideo set state = ? where videoID = ?";
        List<Object[]> paramlist = new ArrayList<Object[]>();
        paramlist.add(new Object[]{"09", videoID});
        baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, paramlist);

    }

    /**
     * 编辑视频
     *
     * @param videoID
     */
    public void editVideo(String videoID, String titleName) {

        String hql = "update DSVideo set titleName = ? where videoID = ?";
        List<Object[]> paramlist = new ArrayList<Object[]>();
        paramlist.add(new Object[]{titleName, videoID});
        baseBeanService.executeHqlsByParamsList(null, new String[]{hql}, paramlist);

    }

}
