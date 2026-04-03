package hy.ea.production.service;

import hy.ea.bo.production.recruit.RecruitInfo;
import hy.ea.bo.production.recruit.TalentPool;
import hy.plat.bo.PageForm;

import java.util.Map;

public interface BidsRecruitService {
    /**
     *
     * 手机端发布职位
     * @param recruitInfo
     * @param staffID
     * @param companyID
     * @param companyName
     */
    public void addPosition(RecruitInfo recruitInfo, String staffID, String companyID, String companyName);

    /**
     *
     * 职位管理
     * @param pageNumber
     * @param pageSize
     * @param companyID
     * @return
     */
    public  PageForm  findPositionList(int pageNumber,int pageSize,String companyID,String status);

    /**
     *
     * 上线下线
     * @param riId
     * @return
     */
    public void onOfflineRecruit(String riId,String staffID);


    /**
     *
     * 删除招聘信息
     * @param riId
     */
    public void deleteRecruitInfo(String riId);

    /**\
     *
     * 编辑获取信息
     * @param riId
     * @return
     */
    public RecruitInfo getEditRecruitInfo(String riId);
    /**
     *
     * 投递的简历
     * @param pageNumber
     * @param pageSize
     * @param companyID
     * @param state
     * @param jobTitle
     * @return
     */
    public PageForm getTalentResumeList(int pageNumber,int pageSize,String companyID,String state,String jobTitle,String staffID);
    /**
     *
     * 简历详情
     * @param resumeID
     * @return
     */
    public Map<String,Object> resumedetail(String resumeID,String tpId);

    /**
     *
     * 设置状态
     * @param tpId
     * @param state
     */
    public void setOperate(String tpId,String state,String staffID);


    /**
     *
     * 获取面试邀请已有信息
     * @param staffID
     * @param tpId
     * @return
     */
    public Map<String,Object> getInventInfo(String staffID,String tpId);

    /**
     *
     * 邀请面试
     * @param tpId
     * @param talentPool
     */
   public void sendInvent(String tpId,TalentPool talentPool);

    /**
     * 短信通知
     * @param tm
     * @param talentPool
     */
    public void noticeMessage(String tm, TalentPool talentPool);


    /**
     *
     * 收藏的简历
     * @param pageNumber
     * @param pageSize
     * @param staffID
     * @return
     */
    public PageForm  getCollectResume(int pageNumber,int pageSize,String staffID);


    /**
     *
     * 取消简历收藏
     * @param resumeID
     * @param staffID
     */
    public void cancelCollectR(String resumeID  ,String staffID);


    /**
     *
     * 投递的简历或者邀请的职位
     * @param pageNumber
     * @param pageSize
     * @param state
     * @return
     */
    public PageForm getPostRecordList(int pageNumber,int pageSize,String staffID,String state,String type);


    /**
     *
     * 投递详情
     * @param tpId
     * @param staffID
     * @return
     */
    public Object getDetails(String tpId,String staffID);

    /**
     *
     * 改变状态同意拒绝
     * @param tpId
     * @param staffID
     */
    public  void changeState(String tpId,String staffID,String state);

    /**
     *
     * 公司操作不合适
     * @param tpId
     * @param staffID
     */
    public void auditResume(String tpId,String staffID);
    }
