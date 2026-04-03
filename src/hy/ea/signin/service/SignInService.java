package hy.ea.signin.service;

import java.util.Map;

import com.tiantai.wfj.bo.TEshopCusFace;
import hy.ea.bo.Company;
import hy.ea.interceptor.JsonHandlerException;
import hy.plat.bo.PageForm;

public interface SignInService {


    /**
     * 跳转签到页面
     *
     * @param companyId 发起活动的公司id
     * @param account   签到的人员帐号
     * @return 字符串 tosign  yes 说明签到过了  no  说明还没有签到
     */
    public String getSign(String companyId, String account) throws JsonHandlerException;

    /**
     * 分享之前保存手机签到信息
     *
     * @param sccid         签到人sccid
     * @param account       签到人account
     * @param companyId     被签到公司ID
     * @param nums          积分数
     * @param signSite      签到地址
     * @param signInfo      签到信息
     * @param signImagePath 签到发表图片路径
     * @return
     */
    public Map<String, Object> saveSign(String sccid, String account, String companyId, int nums, String signSite, String signInfo, String signImagePath) throws JsonHandlerException;

    /**
     * 根据编号获取当前公司信息
     *
     * @param posNum
     * @return
     */
    public Company getCompanyByPosNum(String posNum) throws JsonHandlerException;

    /**
     * @param account   签到人员微分金账号
     * @param companyId 签到公司ID
     * @return
     */
    public Map<String, Object> signIn(String account, String companyId) throws JsonHandlerException;


    /**
     * 微分金账号绑定FaceEntityId
     *
     * @param account      账号
     * @param faceEntityId 人脸样本ID
     * @return
     */
    public TEshopCusFace bindFaceEntityId(String account, String faceEntityId) throws JsonHandlerException;
}
