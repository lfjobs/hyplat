package hy.ea.office.service;

import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.office.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;


public interface CarManageService {


	String amount(Date indate, Date outdate, String equipmentNumber, String carNumber,CarManage cm);

	PageForm getFeeList(int pageNumber,int pageSize,VenueInformation vf,String companyID);

	boolean checkAddFee(String companyID,String siteID,String carType,String timeUnits,String feecID);

	boolean addOrUpdatefeescale(FeeScale feeScale,ProSetup proSetup,Map<String, ProSetupSub> pssMap);

	public String updatefeescale(FeeScale feeScale,ProductPackaging productPackaging,GoodsManage goodsManage);

	List<BaseBean>   allProSetupSub(String suid);

	FeeScale queryStandard(String feecID);

	boolean delStandard(String feecID);

	boolean delVehicleInformation(String carmID);

	Map<String, Object> queryVehicleInformation(String carmID,String companyId);

	boolean addOrUpdateVehicle(CarManage cm, String companyId);

	Object message(String carmID);

	Map<String, Object> siteDetails(String siteId, boolean siteJudge,
			String companyId);

	List<BaseBean> queryStaff(String companyID,String staffname);

	List<BaseBean> queryNumber(String companyID, String siteId);

	boolean addOrUpdateSiteDetails(VenueInformation vf);

	boolean delSite(String siteId);

	boolean removeEquipment(String equipmentId);

	Map<String, Object> deviceLnfo(String equipmentId, boolean siteJudge,
			String companyId);

	boolean addOrUpdateDeviceLnfo(EquipmentInformation ef);

	boolean verificationfacility(String numberOrName);

	boolean verificationsite(String numberOrName);

	String change(Date date1, Date indate);

	List<BaseBean> queryequipmentNumber(String siteId);

	PageForm InformationList(String companyId, String status, String siteId,
			String numberOrName, PageForm pageForm, int pageNumber);

	List<Object> licensePlateComparison(String companyId, String siteId,
			String carNumber);


	boolean errorCorrection(String carmID1, String carmID2);

	String programCalls(String carmID) throws ParseException;

	PageForm siteList(String hasBeenUnder, String companyId,
			String numberOrName, PageForm pageForm, int pageNumber);


	/**
	 * 停车位管理
	 *
	 * @param
	 * @return
	 */
	 PageForm parkingSpaceList(ParkingSpace parkingSpace,int pageSize,int pageNumber,String companyId);
	/**
	 *
	 * 添加修改停车位
	 * @param parkingSpace
	 * @param companyId
	 * @param staffId
	 */
	void addUpdateSpace(ParkingSpace parkingSpace,String companyId,String staffId);

	/**
	 *
	 * 批量新增停车位
	 * @param parkingSpace
	 * @param companyId
	 * @param staffId
	 */
	void batAddSpace(ParkingSpace parkingSpace,String companyId,String staffId,String startNum,String endNum);
	/**
	 *
	 * 删除停车位
	 * @param parksId
	 */
	void deleteSpace(String parksId);
	/**
	 *
	 *更新状态
	 */
	void updateStatus(String parksId,String status);



	 /**
    * 停车位编号不重复
    * @param
    * @return
    */
   public String checkParkingNum(String parkingCode,String parksId,String siteId);

	/**
	 * 所在场地
	 * @param companyId
	 * @return
	 */
	public List<BaseBean> getSiteList(String companyId);


	/**
	 * 根据场地查询设备
	 * @param siteId
	 * @return
	 */
	public List<BaseBean> getEquipList(String siteId);

	/**
	 *
	 * 根据设备号查询所在场地有没有停车位了
	 * @param equipmentNumber
	 * @return
	 */
	Map<String,String> searchParkingCode(String equipmentNumber);

	/**
	 *
	 *
	 * @param companyId
	 * @param status  in  进入 ：out 出
	 */
	 String pushJG(String companyId,String status,String parkingCode,String carmID,String equipmentNumber,String tip);

	Map<String,Object> pushMqtt(String companyId,String status,String parkingCode,String carmID,String equipmentNumber,String tip);

	/**
	 *
	 * 深圳闸机语音内容
	 * @param status
	 * @param parkingCode
	 * @param carmID
	 * @return
	 */
	String pushSz( String status, String parkingCode,
						  String carmID) ;
   /**
   * 查询付款状态
   * @param carmID
   * @return
   */
    String searchFeeState(String carmID);

	void addTiming(String string, String string2, String journalNum,String carNumber,String staffID,Date indate);

	void updateTiming(String journalNum, String type);

	boolean startUsing(String feecID,String startUsing);

	/**
	 * 查询场地列表 *
	 *
	 * @param companyID:公司id
	 * @param numberOrName:场地名称或编号
	 * @param pageForm:分页信息
	 * @return
	 */
    PageForm examinationRoomList(String companyID, String numberOrName, PageForm pageForm, int pageNumber);
	/**
	 * 查询场地详情 *
	 *
	 * @param companyID:公司id
	 * @param erId:考场id
	 * @param siteJudge:判断
	 * @return
	 */
	Map<String,Object> erDetails(String erId, boolean siteJudge, String companyID);

    /**
     * 添加或修改考场详情 *
     * @param exRoom:考场
     * @return
     */
    boolean addOrUpdateErDetails(ExaminationRoom exRoom);
    /**
     * 删除考场 *
     * @param erId:考场id
     * @return
     */
    boolean delExoom(String erId);
	/**
	 * 实时效验考场编号 *
	 * @param numberOrName:考场编号
	 * @return
	 */
	boolean verificationerRoom(String numberOrName);

	/**
	 * 启用考场 *
	 *@param erId:考场id
	 * @return
	 */
	boolean enableTheTest(String erId);
	/**
	 * 查询公司考场编号 *
	 *
	 * @param companyID
	 *            公司id
	 * @return
	 */
    List<BaseBean> queryerNumber(String companyID);
	/**
	 * 查询考场收费标准详情 *
	 * @param ercID:收费id
	 * @return
	 */
	ExaminationRoomCharge testStandard(String ercID);
	/**
	 * 查询考场详情 *
	 * @param companyID:公司ID
	 * @param erNumber:考场编号
	 * @return
	 */
	List<BaseBean> queryerNumber(String companyID, String erNumber);
    /**
     * 添加或修改考场详情 *
     * @param erCharge:考场收费标准
     * @param ppk:产品
     * @return
     */
    boolean addOrUpdateExRoomCharge(ExaminationRoomCharge erCharge, ProductPackaging ppk, File photo,String photoFileName,Map<String, ProSetupSub> pssMap,String brokerage );
    /**
     * 删除考场收费标准 *
     * @param ercID:考场收费id
     * @return
     */
    boolean delErCharge(String ercID);
    /**
     * 启用考场标准 *
     * @param ercID:考场收费id
     * @return
     */
    boolean examIsEnabled(String ercID);
    /**
     * 考场记录 *
     * @param companyID:公司ID
     * @param account:个人账号
     * @param signInState:签到状态
     * @return
     */

	PageForm testRecordList(String companyID, String account, String signInState, PageForm pageForm, int pageNumber);
	/**
	 * 删除考场记录 *
	 * @param bifId:记录id
	 * @return
	 */
	boolean delBookingInformation(String bifId);
	/**
	 * 考场记录详情 *
	 * @param bifId:记录id
	 * @return
	 */
	Object testRecord(String bifId);

	/**
	 * 查询佣金
	 * @param ercid
	 * @return
	 */
	List<BaseBean> queryProSetup(String ercid);

	/**
	 * 查询业务佣金
	 * @param ercid
	 * @return
	 */
	List<BaseBean> brokerage(String ercid);
	/**
	 * 添加进入记录
	 * @param number
	 * @param date
	 * @param equipmentNumber
	 * @param photopath
	 * @param upName1
	 * @param upName2
	 * @param companyId
	 * @param channel
	 * @param numberType
	 * @return
	 */
	String addManageCar(String number,String date,String equipmentNumber,String photopath,String upName1,String upName2,String companyId,String channel,String numberType,String parksId);

	/**
	 *
	 * 出去时更新数据
	 * @param cm
	 * @param number
	 * @param date
	 * @param equipmentNumber
	 * @param photopath
	 * @param upName1
	 * @param upName2
	 * @param channel
	 */
    void updateManageCar(CarManage cm,String number,String date,String equipmentNumber,String photopath,String upName1,String upName2,String channel);

    /**
     *
     * 根据终端编号查询公司ID
     * @param posNum
     * @return
     */
    String getCompanyByPosNum(String posNum);



    /**
     *
     * 查询打印信息
     * @param carmID
     * @return
     */
    public Object getCarManageInfo(String carmID);

    /**
     *
     * 出入没有进入记录时纪录异常数据
     * @param number
     * @param date
     * @param equipmentNumber
     * @param photopath
     */
    public String addErrorOut(String number,String date,String equipmentNumber,String photopath,String upName1,String upName2,String companyId,String channel,String numberType,String parksId);


    /**
     *
     * 根绝设备号获取当前车牌
     * @param equipmentNumber
     * @return
     */
    public String getCarNumByEq(String equipmentNumber,String status);

	/**
	 *
	 * 获取消费红包比例
	 * @param companyID
	 * @return
	 */
	public String getTotalPct(String companyID);




	/**
	 * 收费VIP套餐
	 *
	 * @param
	 * @return
	 */
	PageForm getVipFeeList(TimingCharging TimingCharging, int pageSize, int pageNumber, String companyId);



	/**
	 *
	 * 添加修改收费VIP套餐
	 * @param timingCharging

	 */
	 void addUpdateVIP(TimingCharging timingCharging);


	/**
	 *
	 * 删除VIP套餐
	 * @param tcId
	 */
	void deleteVIP(String tcId);


	/**
	 * 车牌是否重复
	 * @param
	 * @return
	 */
	public String checkcarNum(String carNum,String tcId,String siteId);

	/**
	 * 修改进入记录

	 * @return
	 */
	String updateInCar(String carmID,String date,String photopath,String upName1,String upName2);


	/**
	 *
	 * 进入的时候判断
	 * * @param number
	 * @param date
	 * @return
	 */
	public Map<String,Object> checkCarRecord(String number,String date,String photopath,String upName1,String  upName2,String equipmentNumber,String companyID, String channel, String numberType, String parksId);



	/**
	 *
	 * 不需要审核的车辆列表
	 * @return
	 */
	public PageForm getFeeAuditList(int pageNumber,int pageSize,String parameter,String companyID);


	/**
	 *
	 * 添加免审核的
	 * @param carNumber
	 * @param siteId
	 * @param companyID
	 * @return
	 */
	public String addFeeCar(String carNumber,String siteId,String companyID,String staffID,String siteName);

	/**
	 *
	 * 删除免审核
	 * @param cfID
	 */
	public void removeFeeCar(String cfID);
	/**
	 * 移动端修改场地启用状态
	 * @param vf
	 * @return
	 */
	boolean uppdateSiteDetails(VenueInformation vf);

	PageForm siteListInfo(String hasBeenUnder, String companyId, String numberOrName, PageForm pageForm, int pageNumber);

	boolean mobileDelSite(VenueInformation vfInfo);

	Object getParksByParksId(String parksId);

	boolean mobileDelParks(ParkingSpace parkingSpace);

	boolean mobileUpdateParksStatus(ParkingSpace parkingSpace);

	Object getDeviceByEquipmentId(String equipmentId);

	void addParkingSpaceEmpty(ParkingSpaceEmpty emp);

	List<BaseBean> findParkingCodeBySiteId(String siteId);

	List<BaseBean> findParkingSpaceEmptyByParksId(ParkingSpaceEmpty emp,String startTime,String endTime);

	List<String> getNewSiteList(String sccId);
}
