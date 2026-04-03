package hy.ea.collage.service;


import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tiantai.wfj.bo.TEshopCusCom;

import hy.ea.bo.human.Staff;
import hy.ea.bo.production.scmanage.DSVideo;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;

public interface DspIndexSerivce {


    /**
     * 获取短视频列表
     *
     * @param staffId 职员ID
     * @param type:00 推荐 01 关注
     * @param limit 最大条数
     * @return
     */
    public List<BaseBean> getDsplist(String staffId, String type, int limit);

    /**
     * 获取短视频列表
     *
     * @param parameter type:00 推荐 01 关注
     * @return
     */
    public PageForm getDsplist(int pageNumber, int pageSize, String parameter, String staffId, String type);

    /**
     * 点赞或者取消赞
     *
     * @param videoID
     */
    public String addZan(String videoID, String staffID);

    /**
     * 关注用户
     *
     * @param videoStaffID
     * @param staffID
     * @return
     */
    public String careUser(String videoStaffID, String staffID);

    /**
     * 记录视频分享数
     *
     * @param videoID
     * @return
     */
    public void addShare(String videoID);


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
    public String addPL(String videoID, String staffID, String pcID, String content, String tostaffID);

    /**
     * 给评论点赞
     *
     * @param pcID
     * @param staffID
     * @return
     */
    public String addPlZan(String pcID, String staffID);


    /**
     * 获取视频的一级评论
     *
     * @param pageNumber
     * @param pageSize
     * @param videoID
     * @param staffID
     * @return
     */
    public PageForm searchPL(int pageNumber, int pageSize, String videoID, String staffID, String videoStaffID);

    /**
     * 获取视频的一条评论
     * @param videoID
     * @param staffID
     * @param pcID
     * @return
     */
    public Object getPL(String videoID, String staffID, String videoStaffID, String pcID);


    /**
     * 获取视频的评论的回复
     *
     * @param pageNumber
     * @param pageSize
     * @param videoID
     * @param staffID
     * @return
     */
    public PageForm searchReplyPL(int pageNumber, int pageSize, String videoID, String staffID, String videoStaffID, String pcpid);

    /**
     * 获取视频的评论一条回复
     * @param videoID
     * @param staffID
     * @param videoStaffID
     * @param pcpid
     * @param pcID
     * @return
     */
    public Object getReplyPL(String videoID, String staffID, String videoStaffID, String pcpid, String pcID);

    /**
     * 已阅读
     *
     * @param staffID
     * @param videoID
     * @return
     */
    public String addReadNum(String staffID, String videoID);

    /**
     * 获取个人基本信息
     *
     * @param staffID
     * @return
     */
    public Staff getBaseInfo(String staffID);

    /**
     * 获取用户信息
     *
     * @param staffID
     * @return
     */
    public TEshopCusCom getTc(String staffID);

    /**
     * 或者关注数或者粉丝数
     *
     * @param staffID
     * @param zf
     * @return
     */
    public int getJoinFans(String staffID, String zf);


    /**
     * 分享页，作者视频
     *
     * @param pageNumber
     * @param pageSize
     * @param staffID
     * @param videoStaffID
     * @return
     */
    public PageForm getShareVideoLists(int pageNumber, int pageSize, String staffID, String videoStaffID);


    /**
     * 获取我的视频
     *
     * @param staffID
     * @param state 视频状态 00：公开；01:好友可见 03：私密：仅自己可以看,09：删除
     * @return
     */
    public PageForm getMyVideoList(int pageNumber, int pageSize, String staffID, String state);

    /**
     * 获取点赞过的视频
     *
     * @param staffID
     * @return
     */
    public PageForm getlikeVideoList(int pageNumber, int pageSize, String staffID);

    /**
     * 查询一个视频的展示信息
     *
     * @return
     */
    public Object getVideoDetail(String videoID, String staffId);

    /**
     * 发布视频
     *
     * @param video
     * @param staffID
     * @return
     */
    public String pubVideo(DSVideo video, String staffID, String ppids, String pricetype);

    /**
     * 是否关注
     *
     * @param videoStaffID
     * @param staffID
     * @return
     */
    public String isCare(String videoStaffID, String staffID);

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
    public PageForm getDspProducts(String priceType, int pageNumber, int pageSize, String parameter, String sccId);


    /**
     * 获取视频商品
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public PageForm getDspProductsList(int pageNumber, int pageSize, String videoID, String ppid);

    /**
     * 删除视频
     *
     * @param videoID
     */
    public void deleteVideo(String videoID);

    /**
     * 编辑视频
     *
     * @param videoID
     */
    public void editVideo(String videoID, String titleName);


}