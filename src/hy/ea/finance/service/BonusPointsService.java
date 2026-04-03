package hy.ea.finance.service;

import java.util.List;
import java.util.Map;

import hy.ea.bo.Company;
import hy.plat.bo.PageForm;

public interface BonusPointsService {

	
	/**
	 * 跳转签到页面
	 * @param companyId  发起活动的公司id
	 * @param account  签到的人员帐号
	 * @return 字符串 tosign  yes 说明签到过了  no  说明还没有签到
	 */
	public String getSign(String companyId,String account);
	
	/**
	 * 活动	
	 * @param ccompanyId  发起活动的往来公司id
	 * @param companyId  公司id
	 * @return 字符串 flag
	 */
	public String getPrizeActivity(String ccompanyId,String isflag,String companyId);
	
	/**
	 * 签到送积分
	 * @param sccid 签到人的sccid
	 * @param staffid  签到人的staffid
	 * @param account  签到人的帐号
	 * @param companyId  被签到公司id
	 * @param num  签到赠送的积分数
	 * @return 字符串 flag
	 */
   public String toSign(String sccid,String staffid,String account,String companyId,Integer num, String paramString7);
	
	/**
	 * 分享文章   赠送积分
	 * @param sccid  分享文章人  sccid
	 * @param staffid  分享文章人  staffid
	 * @param account  分享文章帐号
	 * @param ppid   被分享文章的 id
	 * @return
	 */
   public String shareArticle(String sccid,String staffid,String account,String ppid);
	
   /**
	 * 获取积分详情列表
	 * @param pageNumber  分页（第几页）
	 * @param sccid  所查询积分明细人的sccid
	 * @return
	 * @throws Exception
	 */
   public PageForm getDetailList(int pageNumber,String sccid,String sdate,String edate,String type,String operator,String inAndExp,String schType) throws Exception;
      
   /**
	 *分享二维码 注册成功 上级获得赠送积分   赠送积分
	 * @param sccid  分享二维码的人的sccid
	 * @param staffid  分享二维码的人的staffid
	 * @param account 分享二维码的人 的帐号
	 * @param account 赠送积分数
	 * @return
	 */
   public String shareCode(String sccid,String staffid,String account,String jiFen);
   
   
   /**
	 * 积分充值
	 * @param comid 公司id
	 * @param jum 订单编号
	 * @param staffid 付款人id
	 * @param money 购买金币金额
	 * @param appstyle 支付方式
	 * @param trade_no 第三方交易号
	 */
   public void buyBonusPoints(String comid,String jum,String staffid,String sccid,String money,String appstyle,String trade_no);
	/**
	 * 手机签到购买公司所有人签到详情
	 * @param companyId
	 * @param pageNumber
	 * @return
	 */
	public PageForm signComDetail(String companyId, int pageNumber);

	/**
	 * 手机签到个人签到详情
	 * @param sccid
	 * @param account
	 * @param companyId
	 * @param pageNumber
	 * @return
	 */
	public PageForm signPerDetail(String sccid, String account, String companyId, int pageNumber);

	/**
	 * 分享之前保存手机签到信息
	 * @param sccid 签到人sccid
	 * @param account 签到人account
	 * @param companyId  被签到公司ID
	 * @param nums 积分数
	 * @param signSite 签到地址
	 * @param signInfo 签到信息
	 * @param signImagePath 签到发表图片路径
	 * @return
	 */
   public Map<String, Object> saveSign(String sccid, String account, String companyId, int nums, String signSite, String signInfo, String signImagePath);
	/**
	 * 判断签到次数
	 * @param companyId
	 * @param account
	 * @return
	 */
	public Map<String, Object> isPhoneSignCount(String companyId, String account);
	
	/**
	 * 
	 * 根据编号获取当前公司信息
	 * @param posNum
	 * @return
	 */
	public  Company getCompanyByPosNum(String posNum);
	
	/**
	 * mz
	 * 验证该手机号是否入职过公司
	 * @param sccid
	 * @param account
	 * @return
	 */
	public String  toSignValidate(String sccid,String account,String companyID);
	
	/**
	 * 
	 * 验证人脸ID是否绑定微分金账号
	 * @param openid
	 * @param companyId
	 * @return
	 */
	public Map<String,Object> faceValidate(String openid,String companyId);
	
	
	/**
	 * 绑定微分金账号
	 * @param openid
	 * @param tel
	 * @return
	 */
	public Map<String,Object> bindWfj(String openid,String tel,String companyId);
}
