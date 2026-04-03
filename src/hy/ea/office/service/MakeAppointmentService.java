package hy.ea.office.service;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.office.BookingInformation;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CarManage;
import hy.ea.bo.office.VenueInformation;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.util.List;
import java.util.Map;

public interface MakeAppointmentService {

	/**
	 * 绑定车辆
	 * @param carInformation :车辆信息表
	 * @param tcc :账号信息
	 * @return
	 */
	void addVehicle(CarInformation carInformation, TEshopCusCom tcc);
	
	

	/**
	 * 
	 * 是否绑定过这个车
	 * @return
	 */
    String isExistCar(String staffID,String carNum);
    
    /**
     * 
     * 设置默认
     * @param carNum
     * @param staffID
     * @return
     */
    void setDefaultCar(String carNum,String staffID);
	/**
	 * @Title: 查询
	 * @Description: 获取当前用户信息
	 * @return 返回当前用户对象
	 */
	TEshopCusCom queryUser();

	/**
	 * 根据手机号获取账号，没有就生成
	 * @param tel
	 * @param name
	 * @return
	 */
	TEshopCusCom queryWfjtc(String tel,String name,String tuiStaffID);
	/* 保存账号
	 * @param sccid :账号id
	 */
	void addUser(String sccid);
	/**
	 * 解除绑定车辆
	 * @param carInformation :车辆信息表
	 * @param tcc :账号信息
	 * @return
	 */
	void delVehicle(CarInformation carInformation, TEshopCusCom tcc);
	/**
	 * 查询绑定车辆
	 * @param tcc :账号表
	 * @return
	 */
	List<BaseBean> viewVehicle(TEshopCusCom tcc);
	/**
	 * 查询历史绑定车辆记录
	 * @param tcc :账号表
	 * @return
	 */
	PageForm HistoryBindingVehicle(TEshopCusCom tcc, int pageNumber, int pageSize);
	/**
	 * 查询停车场地list
	 * @param pageNumber
	 * @param pageSize
	 * @param venueInformation:场地表
	 * @return
	 */
	PageForm parkingSpacesList(int pageNumber, int pageSize, VenueInformation venueInformation);
	/**
	 * 查询停车场包月/年套餐
	 * @param siteId:场地id
	 * @return
	 */
	List<BaseBean> queryPlan(String siteId);

	/**
	 *
	 * 验证是否购买过包天
	 * @return
	 */
	public String checkbday(String carNumber,String ppid);
	/**
	 * 查询停车场地详情
	 * @param venueInformation:场地表
	 * @return
	 */
	Object parkingSpacesDetails(VenueInformation venueInformation);
	/**
	 * 查询停车记录(车库中)
	 * @param tcc :账号表
	 * @return
	 */
	Object parkingRecord_is(TEshopCusCom tcc);
	/**
	 * 查询停车记录(历史)
	 *  @param pageNumber
	 * @param pageSize
	 * @param tcc :账号表
	 *@return
	 */
	PageForm parkingRecord_history(int pageNumber, int pageSize, TEshopCusCom tcc);
	/**
	 * 停车倒计时
	 * @param tcc :账号表
	 * @return
	 */
	List<BaseBean> countdown(TEshopCusCom tcc);
	/**
	 * 查询产品详情id
	 *@param companyId :公司id
	 *@return
	 */
    Object jumpToBuy(String companyId);
	/**
	 * 查询教练员/主管列表
	 *@return
	 * @param pageNumber
	 * @param pageSize
	 * @param conditions:教练员名称/电话
	 * @param companyID :公司id,type:查询类型
	 */
	PageForm coachOrheadList(int pageNumber, int pageSize, String conditions, String companyID, String type);
	/**
	 * 查询考场列表
	 *@return
	 * @param pageNumber
	 * @param pageSize
	 * @param conditions:考场名称
	 * @param companyID :公司id
	 */
	PageForm testList(int pageNumber, int pageSize, String conditions, String companyID);
	/**
	 * 查询金币数是否足够
	 * @param tcc :账号表
	 * @param erId :考场id
	 *@return
	 */
    String goldCoins(TEshopCusCom tcc, String erId);
	/**
	 * 预约
	 * @param tcc :账号表
	 * @param bookingInformation :预约信息
	 * @return
	 */
	Object buySuccess(TEshopCusCom tcc, BookingInformation bookingInformation);
	/**
	 * 签到/签退
	 * @param sccid  :审查人id
	 * @param bifId :预约信息id
	 */
	boolean signInOrCheckOut(String sccid,String bifId);
	/**
	 * 查询预约详情
	 * @param cbId:订单id
	 * @param sccId : 账号id
	 *@return
	 */
	Object bookingDetails(String cbId, String sccId);
	/**
	 * 快捷查询考场计时
	 * @param companyId : 公司id
	 *@return
	 */
	ProductPackaging theTestTime(String companyId);
	/**
	 * 判断用户是否是该驾校的审核员
	 * @param companyId : 公司id
	 * @param sccid : 当前用户sccid
	 *@return
	 */
	boolean userJudge(String sccid, String companyId);
	/**
	 * 我的练车记录
	 * @param pageNumber
	 * @param pageSize
	 * @param companyId : 公司id
	 * @param sccId : 当前用户id
	 *@return
	 */
	PageForm myRecord(String sccId, String companyId,int pageNumber,int pageSize);
	/**
	 * 用户练车记录
	 * @param pageNumber
	 * @param pageSize
	 * @param companyId : 公司id
	 * @param sccId : 当前用户id
	 *@return
	 */
	PageForm userRecord(String sccId, String companyId,int pageNumber,int pageSize,String conditions);
	/**
	 * 验证车牌是否绑定
	 * @param carInformation :车辆表
	 * @return
	 */
	boolean validation(CarInformation carInformation);
	
	/**
	 * 
	 * 根据当前APP登录账号获取绑定的所有车牌号
	 * @param sccid
	 * @return
	 */
	List<BaseBean> getAllCarNum(String staffId);
	
	/**
	 * 
	 * 根据车牌查询单价
	 * @param carNum
	 * @return
	 */
     List<Object> searchFeeScale(String carNum,String equip);

	
	/**
	 * 停车收费付款修改状态
	 * @param carmID
	 * @param journalNum
	 */
	 void setCarManage(String carmID,String journalNum,String wfStatus4);
	
	
	/**
	 * 查询驾校列表
     * @param conditions :考场名称
     * @param itsLocation :考场所在地
	 * @return
	 */
    PageForm ajaxSchoolList(int pageNumber, int pageSize, String conditions, String itsLocation);
	/**
	 * 查询资讯列表
	 * @param sccId :账号ID
	 * @return
	 */
	PageForm ajaxInformationList(int pageNumber, int pageSize, String sccId);
	/**
	 * 查询签到详情
	 * @param conditions :签到id
	 * @return
	 */
	Object checkTheDetails(String conditions);
	/**
	 * 查询新闻列表
	 * @param  ppids:产品id集合
	 * @return
	 */
	List<BaseBean> informationList(String ppids);
	/**
	 * ajax查询驾校列表
	 * @param  conditions:公司名称
	 * @param  companyAddr:公司地址
	 * @return
	 */
	PageForm ajaxDrivingList(int pageNumber, int pageSize, String conditions, String companyAddr);

	/**
	 *
	 * 签退分金币
	 * @param bifId
	 */
	public void bookDistribution(String bifId);
	/**
	 *
	 * 根据人找绑定的车牌
	 * @param sccId
	 * @return
	 */
	public String getCarNumberByTcc(String sccId);

	/**
	 * 根据订单号查询预约记录
	 * @param journalNum
	 * @return
	 */
	public BookingInformation getBookInfo(String journalNum);

	/**
	 * 获取积分
	 * @param sccid
	 * @return
	 */
	public String getJiFen(String sccid);

	/**
	 *
	 * 获取金币
	 * @param sccid
	 * @return
	 */
	public  String  getJinBi(String sccid);

	/**
	 *
	 * @param journalNum
	 */
	public void setBookState(String journalNum);

	/**
	 * 获取用户
	 * @param sccId
	 */
	public Map<String,Object> getTcTr(String sccId);


	/**
	 * 获取打印小票信息
	 *
	 * @param journalNum
	 * @return
	 */
	public List<Object> printInfo(String journalNum);

	/**
	 *
	 * 根据订单号获取详情信息
	 * @param journalNum
	 * @return
	 */
	public Map<String,String> getBookInfoCash(String journalNum);

	/**
	 * 根据终端号获取绑定的公司companyid
	 * @param posNum
	 * @return
	 */
	public Map<String,String> getComIDByPosNum(String posNum);


	/**
	 *
	 * 验证刷脸用户
	 * @param openid
	 * @return
	 */
	public Map<String,Object>  faceValidate(String openid);

	/**
	 *
	 * 绑定微分金账号
	 * @param openid
	 * @param tel
	 * @return
	 */
	public Map<String,Object> bindWfjAccount(String openid,String tel,String cardNum);

	/**
	 *   根据用户获取预约ID
	 * @param sccId
	 * @return
	 */
	public String getbookId(String sccId);

	/**
	 *
	 * 获取学员姓名
	 * @return
	 */
	public  String getStudentName(String tel);
	
	
	/**
	 * 
	 * 火车车辆进入记录
	 * @param carNum
	 * @return
	 */
	public CarManage getCarInRecord(String carNum,String status,String equip);

	/**
	 *
	 * 根据车辆类型查车
	 * @param carType
	 * @return
	 */
	public List<BaseBean> getBindCar(String carType,String staffID);

}
