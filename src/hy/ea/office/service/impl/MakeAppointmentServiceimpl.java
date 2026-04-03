package hy.ea.office.service.impl;


import com.tiantai.wfj.bo.*;
import com.tiantai.wfj.front.WfjEshopProductAction;
import com.tiantai.wfj.service.WfjJifenService;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.BenDis.BonusPoints;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.*;
import hy.ea.finance.service.transferService;
import hy.ea.office.service.CarManageService;
import hy.ea.office.service.MakeAppointmentService;
import hy.ea.util.ToChineseFirstLetter;
import hy.ea.websitemall.card.service.QRCodeService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class MakeAppointmentServiceimpl implements MakeAppointmentService {

	private Logger logger = LoggerFactory.getLogger(WfjEshopProductAction.class);
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private QRCodeService qrCodeService;
	@Resource
	private transferService transService;
	private CAccount caccount;//账号表
	private VehicleBinding vehicleBinding;//车辆人员关系表
	@Resource
	private WfjJifenService wfjJifenService;
	@Resource
	private CarManageService cmService;

	/**
	 * 转换list *
	 *
	 * @param hql    hql语句
	 * @param object 所需参数的数组
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List transition(String hql, Object[] object) {
		return beandao.getListBeanBySqlAndParams(hql, object);
	}

	/**
	 * 分页
	 *
	 * @param pageNumber 当前页
	 * @param pageSize   显示条数
	 * @param sql        sql语句
	 * @param sqlcount   总记录数SQL语句
	 * @param params     参数
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
									 String sqlcount, Object[] params) {
		int count = beandao.getConutByBySqlAndParams(sqlcount, params);// 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = beandao.getConutByBySqlAndParamsAndPage(
				sql, params, firstResult, maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;

	}


	/**
	 * 获取总时间 *
	 *
	 * @param date   开始时间
	 * @param Indate 结束时间
	 * @return
	 */
	public String change(Date date, Date Indate) {
		long seconds = (Indate.getTime() - date.getTime()) / 1000;// 除以1000是为了转换成秒搜索
		long temp = 0;
		StringBuffer sb = new StringBuffer();
		temp = seconds / 3600;
		sb.append((temp < 10) ? "0" + temp + "时" : "" + temp + "时");

		temp = seconds % 3600 / 60;
		sb.append((temp < 10) ? "0" + temp + "分" : "" + temp + "分");

		temp = seconds % 3600 % 60;
		sb.append(((temp < 10) ? "0" + temp : "" + temp) + "秒");

		return sb.toString();
	}

	/**
	 * @return 返回当前用户对象
	 * @Title: 查询
	 * @Description: 获取当前用户信息
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public TEshopCusCom queryUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		TEshopCusCom cus = (TEshopCusCom) sw.getObject(session,
				SessionWrap.KEY_SHOPCUSCOM);
		return cus;
	}

	/**
	 * 根据手机号获取账号，没有就生成
	 *
	 * @param tel
	 * @param name
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public TEshopCusCom queryWfjtc(String tel, String name, String tuiStaffID) {

		TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?", new Object[]{tel, "01"});
		if (tc == null) {
			TEshopCusCom tuicus = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = (select f.sccid from Staff f where staffID = ?)", new Object[]{tuiStaffID});
			Staff staff = new Staff();
			staff.setStaffName(name);
			String staffID = wfjJifenService.zhuCe(tuicus, tuicus != null ? tuicus.getSccId() : "", tel, "123456", staff);
			tc = new TEshopCusCom();
			tc.setStaffid(staffID);
		}
		return tc;
	}

	/**
	 * 保存账号
	 *
	 * @param sccid :账号id
	 */
	@Override
	@Transactional
	public void addUser(String sccid) {
		TEshopCusCom tcc = (TEshopCusCom) beandao.getBeanByHqlAndParams("select t from TEshopCusCom as t where t.sccId = ?", new Object[]{sccid});
		TEshopCustomer tcr = (TEshopCustomer) beandao.getBeanByHqlAndParams("select e from TEshopCustomer as e where e.staffid = ?", new Object[]{tcc.getStaffid()});
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, tcc);
		sw.setObject(session, SessionWrap.KEY_CUSTOMER, tcr);
	}


	/**
	 * 绑定车辆
	 *
	 * @param carInformation :车辆信息表
	 * @param tcc            :账号信息
	 * @return
	 */
	@Override
	@Transactional
	public void addVehicle(CarInformation carInformation, TEshopCusCom tcc) {
		List<BaseBean> list = new ArrayList<BaseBean>();
		//保存车辆信息
		CarInformation cf = (CarInformation) beandao.getBeanByHqlAndParams("from CarInformation where carNum = ?", new Object[]{carInformation.getCarNum()});

		Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{tcc.getStaffid()});
		if (cf != null) {
			cf.setStaffID(tcc.getStaffid());
			cf.setStaffName(staff.getStaffName());
			cf.setCompanyID(tcc.getCompanyId());
			//	cf.setGoodsID(goodsManage.getGoodsID());
			cf.setEngineNum(carInformation.getEngineNum());
			list.add(cf);
		} else {

			GoodsManage goodsManage = new GoodsManage();
			goodsManage.setGoodsID(serverService.getServerID("goodsID"));
			goodsManage.setGoodsName(carInformation.getCarNum());
			goodsManage.setTypeID("车辆管理");
			goodsManage.setCompanyID(tcc.getCompanyId());
			goodsManage.setStaffID(tcc.getStaffid());
			goodsManage.setFiveClear("5");
			goodsManage.setStaffName(staff.getStaffName());
			// 编号处理
			String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.typeID=?";
			// 获取拼音码
			String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage
					.getTypeID());
			String Upstr = pinyin.toUpperCase();// 转换为大写
			int goodscodingnum = beandao.getConutByByHqlAndParams(hql,
					new Object[]{goodsManage.getTypeID()});
			DecimalFormat form = new DecimalFormat("000000");
			String ss = form.format(goodscodingnum + 1);
			goodsManage.setGoodsCoding(Upstr + "_" + ss);
			goodsManage.setCreatedate(new Date());
			list.add(goodsManage);


			carInformation.setCarID(serverService.getServerID("car"));
			carInformation.setStaffID(tcc.getStaffid());
			carInformation.setStaffName(staff.getStaffName());
			carInformation.setCompanyID(tcc.getCompanyId());
			carInformation.setGoodsID(goodsManage.getGoodsID());
			list.add(carInformation);
		}


		//保存车辆人员关系表
		vehicleBinding = new VehicleBinding();
		vehicleBinding.setVbID(serverService.getServerID("vb"));
		vehicleBinding.setCarID(cf != null ? cf.getCarID() : carInformation.getCarID());
		vehicleBinding.setStaffID(tcc.getStaffid());
		vehicleBinding.setBindingTime(new Date());
		vehicleBinding.setStatus("00");
		vehicleBinding.setIsDefault("01");
		
		list.add(vehicleBinding);

		String sql1 = "update dt_VehicleBinding set isDefault = ? where carID != ? and staffID = ? ";

		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[]{"00",vehicleBinding.getCarID(),vehicleBinding.getStaffID()});
		
	
		beandao.executeSqlsByParmsList(list, new String[]{sql1}, params);
		
	}

	/**
	 * 
	 * 是否绑定过这个车
	 * @return
	 */
	public String isExistCar(String staffID,String carNum){
		
		String hql = "from VehicleBinding v where v.staffID = ? and v.carID in (select c.carID from CarInformation c where c.carNum = ?)";
		VehicleBinding vb = (VehicleBinding)beandao.getBeanByHqlAndParams(hql, new Object[]{staffID,carNum});
		
		if(vb!=null){
			
			return "1";
		}else{
			return "0";
		}
		
		
	}
	

    /**
     * 
     * 设置默认
     * @param carNum
     * @param staffID
     * @return
     */
    public void setDefaultCar(String carNum,String staffID){
    	List<BaseBean> beans = new ArrayList<BaseBean>();
    	String hql = "from VehicleBinding v where v.staffID = ? and v.carID in (select c.carID from CarInformation c where c.carNum = ?)";
		VehicleBinding vb = (VehicleBinding)beandao.getBeanByHqlAndParams(hql, new Object[]{staffID,carNum});
		
		String sql1 = "update dt_VehicleBinding set isDefault = ? where carID != ? and staffID = ? ";

		if(vb!=null){
		    vb.setIsDefault("01");
		    beans.add(vb);
		}
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(new Object[]{"00",vb.getCarID(),staffID});
		beandao.executeSqlsByParmsList(beans, new String[]{sql1}, list);
		
    	
    }
	/**
	 * 解除绑定车辆
	 *
	 * @param carInformation :车辆信息表
	 * @param tcc            :账号信息
	 * @return
	 */
	@Override
	@Transactional
	public void delVehicle(CarInformation carInformation, TEshopCusCom tcc) {
		CarInformation cf = (CarInformation) beandao.getBeanByHqlAndParams("from CarInformation where carID=?", new Object[]{carInformation.getCarID()});
		String sql1 = "update dt_VehicleBinding set status = ?,unbundlingtime = ? where carID = ? and staffID = ? ";
		String sql2 = "delete from dtgoodsmanage where goodsid=?";
		String[] sqls = new String[]{sql1, sql2};
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(new Object[]{"01", new Date(), carInformation.getCarID(), tcc.getStaffid()});
		list.add(new Object[]{cf.getGoodsID()});
		beandao.executeSqlsByParmsList(null, sqls, list);
	}

	/**
	 * 查询绑定车辆
	 *
	 * @param tcc :账号表
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> viewVehicle(TEshopCusCom tcc) {

		String hql = "select c from VehicleBinding v,CarInformation c where v.staffID = ? and v.status = ? and v.carID = c.carID";

		List<BaseBean> cf =  beandao.getListBeanByHqlAndParams(hql, new Object[]{tcc.getStaffid(), "00"});

		return cf;
	}

	/**
	 * 查询历史绑定车辆记录
	 *
	 * @param tcc :账号表
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm HistoryBindingVehicle(TEshopCusCom tcc, int pageNumber, int pageSize) {

		StringBuilder sb = new StringBuilder();
		sb.append("select c.carnum,to_char(v.bindingTime, 'YYYY-MM-DD'),to_char(v.unbundlingTime, 'YYYY-MM-DD') from ");
		sb.append("dt_VehicleBinding v,dt_car_carinformation c ");
		sb.append("where v.staffID = ? and v.status = ? ");
		sb.append("and v.carID = c.carID ");

		PageForm pf = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", new Object[]{tcc.getStaffid(), "01"});

		return pf;
	}

	/**
	 * 查询停车场地list
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param venueInformation:场地表
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm parkingSpacesList(int pageNumber, int pageSize, VenueInformation venueInformation) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select v.siteid,y.logopath, v.sitename, v.fieldcapacity,");
		sb.append("(select GetDistance(?,?,v.eastlongitude,v.northlatitude) from dual) distance,v.itslocation");
		sb.append(" from dt_VenueInformation v ");
		sb.append(" left join dt_ccom_com m on v.companyId = m.compnay_id left join dtcontactcompany y on m.ccompany_id = y.ccompanyid ");
		sb.append("where v.status = ?");
		list.add(venueInformation.getEastLongitude());
		list.add(venueInformation.getNorthLatitude());
		list.add("00");
		if (venueInformation.getSiteName() != null && !venueInformation.getSiteName().equals("")) {
			sb.append("and siteName like ? ");
			list.add("%" + venueInformation.getSiteName() + "%");
		}
		sb.append("order by distance asc ");
		PageForm pf = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

		return pf;
	}


	/**
	 * 查询停车场地详情
	 *
	 * @param venueInformation:场地表
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object parkingSpacesDetails(VenueInformation venueInformation) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select v.siteid, v.sitename,(select GetDistance( ? , ? ,v.eastlongitude,v.northlatitude) from dual) distance, ");
		sb.append("v.itslocation,v.fieldcapacity, ");
		sb.append("(v.fieldcapacity - (select count(*) from dt_carmanage c, dt_equipmentinformation e ");
		sb.append("where c.equipmentnumber = e.equipmentnumber and e.siteid = ? )) singular,");
		sb.append("y.logopath,m.compnay_id,m.ccompany_id ");
		sb.append("from dt_VenueInformation v ");
		sb.append("left join dt_ccom_com m on v.companyId = m.compnay_id left join dtcontactcompany y on m.ccompany_id = y.ccompanyid ");
		sb.append("where v.status = ? and v.siteid = ?");
		list.add(venueInformation.getEastLongitude());
		list.add(venueInformation.getNorthLatitude());
		list.add(venueInformation.getSiteId());
		list.add("00");
		list.add(venueInformation.getSiteId());
		Object object = beandao.getObjectBySqlAndParams(sb.toString(), list.toArray());

		return object;
	}


	/**
	 * 查询停车场包月/年套餐
	 *
	 * @param siteId:场地id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> queryPlan(String siteId) {

		StringBuilder sb = new StringBuilder();
		sb.append("select p.ppid,p.image,v.sitename,p.type,");
		sb.append("to_char(s.re_price * (1 + nvl(sz.total_pct, 0) / 100),'fm999999990.00'),p.goodsid,f.carType,f.timeType ");
		sb.append("from dt_feescale f left join dt_productpackaging p on f.goodsid = p.goodsid left join dt_venueinformation v on f.siteid = v.siteid ");
		sb.append("left join dt_pro_setup s on s.ppid = p.ppid left join dt_ccom_com m on v.companyId = m.compnay_id ");
		sb.append("left join dtcontactcompany y on m.ccompany_id = y.ccompanyid left join dt_set_subsidize sz on y.industrytype = sz.gtid and stutas = ? ");
		sb.append("where f.siteid = ? and (p.type like ? or p.type like ? or p.type like ? or p.type like ?) and f.startusing = ? order by f.timeUnits");
		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(), new Object[]{"01",siteId,"%金币计时%", "%包月计时%", "%包年计时%", "%包天计时%", "00"});

		return list;
	}


	/**
	 *
	 * 验证是否购买过包天
	 * @return
	 */
	public String checkbday(String carNumber,String ppid){
	String result = "0";
    String hql ="from FeeScale s where s.goodsID = (select p.goodsID from ProductPackaging p where p.ppID = ?)";
    FeeScale feeScale = (FeeScale)beandao.getBeanByHqlAndParams(hql,new Object[]{ppid});
    if(feeScale!=null&&feeScale.getTimeType().equals("0")){
          //买的是当天 需要判断是否今天已经买过当天的了
		    Date day = new Date();
		   TimingCharging tc = (TimingCharging) beandao.getBeanByHqlAndParams("from TimingCharging where carNumber=? and siteId=? and state=?", new Object[]{carNumber,feeScale.getSiteId(),"00"});
           if(tc==null){
			   result = "0";
		   }else{
                 Date end = tc.getEndDate();
			     Date today =  getCurrentDay();
			      if(end.compareTo(today)==0){
                         //相等 说明买过了
                      return "1";
				  }else if(end.compareTo(today)<0){
                             //按小时制买的，没买到全天 可以接着买包天

                       return "0";
				  }else{
                       return "1";
				  }

		   }




		result = "1";
	}else{
		 result = "0";//其他情况可以直接买
	}

		return null;


	}
	private Date getCurrentDay(){
		Date date1 =  null;
		Date newDate = new Date();
		try {
			String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(newDate).toString().split("-");
			Integer year = Integer.parseInt(strNow[0]);
			Integer month = Integer.parseInt(strNow[1]);
			Integer day = Integer.parseInt(strNow[2]);
			String endStr = year+"-"+month+"-"+day+" "+"23:59:59";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			date1 =  sdf.parse(endStr);

		} catch (ParseException e) {

		}
		return  date1;
	}

	private Date getCurrentYMD(Date date){
		Date newDate = null;
		try {
			String[] strNow = new SimpleDateFormat("yyyy-MM-dd").format(date).toString().split("-");
			Integer year = Integer.parseInt(strNow[0]);
			Integer month = Integer.parseInt(strNow[1]);
			Integer day = Integer.parseInt(strNow[2]);
			String endStr = year+"-"+month+"-"+day;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			newDate =  sdf.parse(endStr);

		} catch (ParseException e) {

		}
		return  newDate;
	}

	/**
	 * 停车倒计时
	 *
	 * @param tcc :账号表
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> countdown(TEshopCusCom tcc) {

		StringBuilder sb = new StringBuilder();
		sb.append("select g.tcid,y.logopath,v.sitename,(trunc(g.enddate, 'DD') - trunc(sysdate, 'DD')), ");
		sb.append("to_char(g.startdate, 'YYYY-MM-DD'),to_char(g.enddate, 'YYYY-MM-DD') ");
		sb.append("from dt_timingcharging g, dt_venueinformation v,dt_ccom_com m,dtcontactcompany y  ");
		sb.append("where g.staffid = ? and g.state = ? and g.siteid = v.siteid and v.companyId = m.compnay_id and m.ccompany_id = y.ccompanyid order by g.startdate desc");

		List<BaseBean> list = beandao.getListBeanBySqlAndParams(sb.toString(), new Object[]{tcc.getStaffid(), "00"});

		return list;
	}


	/**
	 * 查询停车记录(车库中)
	 *
	 * @param tcc :账号表
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object parkingRecord_is(TEshopCusCom tcc) {

		StringBuilder sb = new StringBuilder();
		sb.append("select g.carmid,g.carnumber,to_char(g.indate, 'YYYY-MM-DD HH24:MI:SS'),g.status ");
		sb.append("from dt_VehicleBinding b, dt_car_carinformation c, dt_carmanage g ");
		sb.append("where b.staffID = ? and b.status = ? and b.carid = c.carid ");
		sb.append("and c.carnum = g.carnumber and g.status = ? and rownum = 1 ");

		Object object = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{tcc.getStaffid(), "00", "1"});

		return object;
	}

	/**
	 * 查询停车记录(历史)
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param tcc        :账号表
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm parkingRecord_history(int pageNumber, int pageSize, TEshopCusCom tcc) {

		StringBuilder sb = new StringBuilder();
		sb.append("select g.carmid,v.sitename,g.carnumber,to_char(g.indate, 'YYYY-MM-DD HH24:MI'), ");
		sb.append("to_char(g.outdate, 'YYYY-MM-DD HH24:MI'),g.money ");
		sb.append("from dt_VehicleBinding b, dt_car_carinformation c, dt_carmanage g,dt_equipmentinformation e,dt_venueinformation v ");
		sb.append("where b.staffID = ? and b.status = ? and b.carid = c.carid ");
		sb.append("and c.carnum = g.carnumber and g.status = ? ");
		sb.append("and g.equipmentnumber=e.equipmentnumber and e.siteid = v.siteid order by g.indate desc");

		List<Object> list = new ArrayList<Object>();
		list.add(tcc.getStaffid());
		list.add("00");
		list.add("0");

		PageForm pf = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

		return pf;
	}

	/**
	 * 验证车牌是否绑定
	 *
	 * @param carInformation :车辆表
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean validation(CarInformation carInformation) {

		boolean bl = true;

		String sql = "select v.vbid from dt_VehicleBinding v,dt_car_carinformation f where v.carid = f.carid and f.carnum = ? and v.status = ?";

		Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{carInformation.getCarNum(), "00"});

		if (obj != null) {
			bl = false;
		}

		return bl;
	}
	
	/**
	 * 
	 * 根据当前APP登录账号获取绑定的所有车牌号
	 * @param
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> getAllCarNum(String staffId){
		String sql = "select f.carNum from dt_VehicleBinding v,dt_car_carinformation f where v.carid = f.carid and v.staffId = ? and v.status = ?";
        List<BaseBean> list = beandao.getListBeanBySqlAndParams(sql, new Object[]{staffId,"00"});
		
		return list;
		
	}

	/**
	 * 
	 * 根据车牌查询单价
	 * @param carNum
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public  List<Object> searchFeeScale(String carNum,String equip){
		String carType="p";
		if(carNum.indexOf("学")!=-1){
              carType="c";
		}

		List<Object> ppklist = null;

		String equipmentNumber = "";
         if(equip!=null&&!equip.equals("")){
			 equipmentNumber = equip;

		 }else{

			 String hql = "from CarManage c where c.carNumber = ? and c.status = ? and c.equipmentNumber = ?) order by indate desc";
			 List<BaseBean> listcm = beandao.getListBeanByHqlAndParams(hql,
					 new Object[]{carNum, "0",equip});

			 if(listcm.size()>0) {
				 CarManage cm = (CarManage) listcm.get(0);
				 equipmentNumber = cm.getEquipmentNumber();
			 }
		 }
		// 获取场地信息
		VenueInformation vf = (VenueInformation) beandao.getBeanByHqlAndParams("select v from EquipmentInformation e,VenueInformation v where e.equipmentNumber=? and e.siteId=v.siteId", new Object[]{equipmentNumber});
		//查询公司收费标准

		StringBuilder sb = new StringBuilder();
		sb.append("select p.ppid,to_char(s.re_price * (1 + nvl(sz.total_pct, 0) / 100),'fm999999990.00'),f.companyid,f.timeUnits,sz.total_pct,p.goodsname from dt_feescale f");
		sb.append(" left join dt_productpackaging p on f.goodsid = p.goodsid left join dt_pro_setup s on p.ppid = s.ppid");
		sb.append(" left join dt_ccom_com m on f.companyid = m.compnay_id");
		sb.append(" left join dtcontactcompany y on m.ccompany_id = y.ccompanyid left join dt_set_subsidize sz on y.industrytype = sz.gtid and stutas = '01'");
		sb.append(" where f.siteid = ? and f.carType = ?  and f.startUsing='00'order by f.timeunits");
		ppklist = baseBeanService.getListBeanBySqlAndParams(sb.toString(),new Object[]{vf.getSiteId(),carType});
		return ppklist;
	}

	/**
	 * 付款修改状态
	 * @param carmID
	 * @return
	 */
	public void setCarManage(String carmID,String journalNum,String wfStatus4){
		System.out.println("carmID"+carmID);
		CarManage carManage = (CarManage)beandao.getBeanByHqlAndParams("from CarManage where carmID = ?",new Object[]{carmID});
		//付款把原来审核信息置空了。
		String chargeState = "03";
		String chargeType = "02";
		if("06".equals(wfStatus4)){
			chargeState = "04";
			chargeType = "00";
		}
		List<BaseBean> beans = new ArrayList<BaseBean>();
		if(carManage!=null){

			if(carManage.getStatus().equals("1")){
				carManage.setChargeState1(chargeState);
			}else{
				carManage.setChargeType(chargeType);
				carManage.setChargeState(chargeState);
			}
			System.out.println("chargrState1"+carManage.getChargeState1());
			carManage.setJournalNum(journalNum);
			carManage.setAuditStatus("");
			beans.add(carManage);
		}
//		String sql1 = "update DT_CARMANAGE set chargeType = ?,journalNum = ?,chargeState = ?,auditStatus = ? where carmID =  ? ";
//
//
//		String[] sqls = new String[]{sql1};
//		List<Object[]> list = new ArrayList<Object[]>();
//		list.add(new Object[]{chargeType,journalNum,chargeState,"",carmID});

		// 如果已经有提交审核的信息了，就把提交信息作废掉，因为已经付款了，无需人工审核。

		String hql1 = "from CarManageAudit c where c.carmID = ? and status = ?";
		CarManageAudit carManageAudit = (CarManageAudit)beandao.getBeanByHqlAndParams(hql1,new Object[]{carmID,carManage.getStatus()});
		System.out.println("carManage.getStatus()"+carManage.getStatus());
		if(carManageAudit!=null) {

			DocRelateOther tcs = (DocRelateOther) beandao.getBeanByHqlAndParams("from  DocRelateOther ds where ds.idValue = ?", new Object[]{carManageAudit.getCmaID()});
			if (tcs != null) {
				System.out.println("tcs.getDocId" + tcs.getDocId());
				if (carManageAudit != null) {

					String hql = "from Document t where t.docId = ?";
					Document doc = (Document) beandao.getBeanByHqlAndParams(hql, new Object[]{tcs.getDocId()});

					if (doc != null) {
						doc.setStatus("d");
						beans.add(doc);
					}
				}

			}


		}
		beandao.executeSqlsByParmsList(beans, null, null);
		System.out.println("车辆已支付费用，通知主机抬杆");
		cmService.pushMqtt(null, "pay", null, carmID, null,"");
	
		
	}
	/**-------------------------------驾校预约----------------------------------**/

	/**
	 * 查询产品详情id
	 *
	 * @param companyId :公司id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object jumpToBuy(String companyId) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select p.ppid,p.companyid,y.companyname,p.image,p.goodsname,p.remark,sp.RE_PRICE ");
		sb.append("from dt_productpackaging p,DT_PRO_SETUP sp, dt_ccom_com m, dtcontactcompany y ");
		sb.append("where p.companyid = ? and p.companyid = m.compnay_id and p.ppid=sp.ppid and sp.COM_ID = p.companyid ");
		sb.append("and m.ccompany_id = y.ccompanyid  ");
		sb.append(" and p.type like  ?");
		list.add("%金币计时%");
		Object object = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{companyId, list.toArray()});

		return object;
	}

	/**
	 * 查询考场列表
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param conditions:考场名称
	 * @param companyID       :公司id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm testList(int pageNumber, int pageSize, String conditions, String companyID) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select e.erid,y.Logopath,e.ername,e.itslocation,e.eastlongitude,e.northlatitude,sp.RE_PRICE,p.ppid ,e.companyid  ");
		sb.append(" from DT_ExaminationRoom e, dt_ccom_com m, dtcontactcompany y,dt_ExaminationRoomCharge c,dt_productpackaging p,DT_PRO_SETUP sp ");
		sb.append(" where p.ppid=sp.ppid and sp.COM_ID = p.companyid and e.companyid = ? and e.status = ? and e.whetherStatus = ? and e.companyid = m.compnay_id ");
		sb.append(" and m.ccompany_id = y.ccompanyid and e.erId = c.erId and c.goodsID = p.goodsid and c.startusing = ? ");
		list.add(companyID);
		list.add("00");
		list.add("00");
		list.add("00");
		if (conditions != null && !conditions.equals("")) {
			sb.append("and e.ername like ? ");
			list.add("%" + conditions + "%");
		}
		sb.append("order by e.erdate desc ");

		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

		return pageForm;
	}


	/**
	 * 查询教练员/主管列表
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param conditions:教练员名称/电话
	 * @param companyID           :公司id,type:查询类型
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm coachOrheadList(int pageNumber, int pageSize, String conditions, String companyID, String type) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select f.staffid,f.headimage,f.staffname,f.reference ");
		sb.append("from dtcos s, dt_hr_deptpost p,dt_hr_staff f ");
		sb.append("where s.companyid = ? and s.cosstatus = ? ");
		sb.append("and s.deppostid = p.deppostid and p.postname like ? and s.staffid = f.staffid ");
		list.add(companyID);
		list.add("50");
		list.add("%" + type + "%");
		if (conditions != null && !conditions.equals("")) {
			sb.append("and f.staffname like ?  ");
			list.add("%" + conditions + "%");
		}
		sb.append("order by f.verifytime desc ");

		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

		return pageForm;
	}

	/**
	 * 查询金币数是否足够
	 *
	 * @param tcc  :账号表
	 * @param erId :考场id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String goldCoins(TEshopCusCom tcc, String erId) {

		String ss;
		//00:成功,01:金币不足,02:重新登录,03:产品无法购买,04:当天已预约无法再次预约
		if (tcc != null) {
			List<BaseBean> list = beandao.getListBeanBySqlAndParams("select * from dt_BookingInformation where sccId=? and erId=? and signInState <> ?  and trunc(biftime) = trunc(sysdate) ", new Object[]{tcc.getSccId(), erId, "02"});
			if (list == null || list.size() == 0) {
				WfjJifen jf = (WfjJifen) beandao.getBeanByHqlAndParams("from WfjJifen where staffId = ? and sccid = ?", new Object[]{tcc.getStaffid(), tcc.getSccId()});
				if (jf != null && jf.getWfjJifenScore() != null) {
					StringBuilder sb = new StringBuilder();
					sb.append("select p.price from dt_examinationRoomCharge f,dt_productpackaging p ");
					sb.append("where f.erid = ? and f.goodsid = p.goodsid and f.startusing = ? ");
					Object object = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{erId, "00"});
					if (object != null) {
						int a = Double.valueOf(object.toString()).intValue() * 100;
						int b = Double.valueOf(jf.getWfjJifenScore()).intValue();
						if (b > a) {
							ss = "00";
						} else {
							ss = "01";
						}
					} else {
						ss = "03";
					}

				} else {
					ss = "01";
				}
			} else {
				ss = "04";
			}
		} else {
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.removeAttribute("url");
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			ss = "02";
		}
		return ss;
	}

	/**
	 * 预约
	 *
	 * @param tcc                :账号表
	 * @param bookingInformation :预约信息1111111
	 * @return
	 */
	@Override
	@Transactional
	public Object buySuccess(TEshopCusCom tcc, BookingInformation bookingInformation) {

		HttpServletRequest re = ServletActionContext.getRequest();
		String path = re.getContextPath();
		String basePath = re.getScheme() + "://" + re.getServerName() + ":"
				+ re.getServerPort() + path + "/";

		Staff staff = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{tcc.getStaffid()});

		bookingInformation.setBifId(serverService.getServerID("bif"));
		bookingInformation.setSccId(tcc.getSccId());
		bookingInformation.setSignInState("00");
		bookingInformation.setBifTime(new Date());

		//生成二维码
		String qrcodePath = qrCodeService.createQRCode("99" + bookingInformation.getJournalNum(),
				"png", "makeAppointment", "");
		
		bookingInformation.setQrCode(qrcodePath);



		//根据ddid查询订单id
		CashierBills gb = (CashierBills) beandao.getBeanByHqlAndParams("from CashierBills where journalNum = ? and billsType = ?", new Object[]{bookingInformation.getJournalNum(),"项目收入预算单"});
		bookingInformation.setCashierBillsID(gb.getCashierBillsID());
		beandao.save(bookingInformation);
        gb.setNopush("00");
		beandao.update(gb);
		Object[] obj = new Object[]{gb.getCashierBillsID(), staff.getStaffName()};

		return obj;
	}


	/**
	 * 签到/签退
	 *
	 * @param sccid :审查人id
	 * @param bifId :预约信息id
	 */
	@Override
	@Transactional
	public boolean signInOrCheckOut(String sccid, String bifId) {
		BookingInformation bif = null;
		if(bifId.indexOf("bif")==-1){
			bif = (BookingInformation) beandao.getBeanByHqlAndParams("from BookingInformation where journalNum = ? and to_char(bifTime,'YYYY-MM-dd') = to_char(?,'YYYY-MM-dd')", new Object[]{bifId, new Date()});

		}else{
			bif = (BookingInformation) beandao.getBeanByHqlAndParams("from BookingInformation where bifId = ? and to_char(bifTime,'YYYY-MM-dd') = to_char(?,'YYYY-MM-dd')", new Object[]{bifId, new Date()});

		}
		boolean bl = false;

		CashierBills cb = null;
		if (bif != null) {
			ExaminationRoom er = (ExaminationRoom) beandao.getBeanByHqlAndParams("from ExaminationRoom where erId = ?", new Object[]{bif.getErId()});
			Staff f = null;
			if("pos".equals(sccid)){
				f = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ?", new Object[]{er.getReviewerStaffId()});

			}else{
				f = (Staff) beandao.getBeanByHqlAndParams("from Staff where staffID = ? and sccid = ?", new Object[]{er.getReviewerStaffId(), sccid});

			}

			TEshopCusCom tcc = (TEshopCusCom) beandao.getBeanByHqlAndParams("from TEshopCusCom where  sccId = ?", new Object[]{bif.getSccId()});
			List<BaseBean> backList = new ArrayList<BaseBean>();
			if (f != null) {
				if (bif.getSignInState().equals("00")) {
					bif.setCheckInTime(new Date());
					bif.setSignInState("01");
					beandao.update(bif);
					logger.error("签到成功");
					bl = true;
				} else if (bif.getSignInState().equals("01") || bif.getSignInState().equals("03")) {


					if (bif.getSignInState().equals("01")) {

						bif.setSignBackTime(new Date());
						bif.setHowMuchTime(change(bif.getCheckInTime(), bif.getSignBackTime()));
						String hqlcb = "from CashierBills cb where cb.journalNum=? and cb.companyID = ?";
						cb = (CashierBills) beandao.getBeanByHqlAndParams(hqlcb, new String[]{bif.getJournalNum(), er.getCompanyId()});

						String hqlgb = "from GoodsBills gb where gb.cashierBillsID=?";
						GoodsBills goodsBills = (GoodsBills) beandao.getBeanByHqlAndParams(hqlgb, new String[]{cb.getCashierBillsID()});

						String hql = "from ProSetup where ppid = ? and comId = ?";
						ProSetup ps = (ProSetup) beandao.getBeanByHqlAndParams(hql, new Object[]{goodsBills.getPpID(), cb.getCompanyID()});

						goodsBills.setPricetype("0");
						//时间换算成小时
						int hID = bif.getHowMuchTime().indexOf("时");
						int mID = bif.getHowMuchTime().indexOf("分");
						int hNum = new Integer(bif.getHowMuchTime().substring(0, hID));//时
						int mNum = new Integer(bif.getHowMuchTime().substring(hID + 1, mID));//分
						int sNum = new Integer(bif.getHowMuchTime().substring(mID + 1, bif.getHowMuchTime().length() - 1));//秒
						if (sNum > 29) {//大于等于30秒 分钟数加1
							mNum++;
						}
						BigDecimal minute = new BigDecimal(mNum);
						BigDecimal unit = new BigDecimal(60);
						minute = minute.divide(unit, 2, BigDecimal.ROUND_HALF_UP);//以小时为单位的分

						BigDecimal count = new BigDecimal(hNum);
						count = count.add(minute).setScale(2, RoundingMode.UP);//数量
						unit = new BigDecimal(goodsBills.getPrice());
						bif.setMoney(String.valueOf(count.multiply(unit).setScale(2, RoundingMode.UP)));//实付款
						goodsBills.setMoney(bif.getMoney());//总价
						if ("0.00".equals(bif.getMoney())) {
							bif.setSignInState("02");
						} else {
							bif.setSignInState("04");
						}

						backList.add(bif);
						goodsBills.setQuantity(String.valueOf(count));
						goodsBills.setMoney(bif.getMoney());
						goodsBills.setRealMoney(bif.getMoney());
						goodsBills.setCostmoney(ps.getEfPrice());
						goodsBills.setStandard(bif.getHowMuchTime());
						cb.setPriceSub(bif.getMoney());
						backList.add(cb);
						backList.add(goodsBills);
						beandao.executeHqlsByParmsList(backList, null, null);
						logger.error("签退成功,等待支付!");
						bl = true;

					}


				} else if (bif.getSignInState().equals("02")) {
					logger.error("已签退,请勿重复签退!");
					bl = false;
				}
			} else {
				logger.error("请用审核人账号扫码!");
				bl = false;
			}
		} else {
			logger.error("预约已失效,请重新预约!");
			bl = false;
		}
		return bl;
	}

	/**
	 * 查询预约详情
	 *
	 * @param cbId:订单id
	 * @param sccId     : 账号id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object bookingDetails(String cbId, String sccId) {

		StringBuilder sb = new StringBuilder();
		sb.append("select e.itslocation,p.image,g.goodsname,ps.re_price,e.ername,d.staffname as dname,f.staffname as fname,n.journalnum, ");
		sb.append("n.qrcode,to_char(n.checkintime, 'YYYY-MM-DD HH24:MI:SS'),to_char(n.signbacktime, 'YYYY-MM-DD HH24:MI:SS'),n.signinstate, ");
		sb.append("n.howmuchtime,n.money,s.journalnum,to_char(s.cashierdate, 'YYYY-MM-DD HH24:MI:SS'), ");
		sb.append("to_char(n.signbacktime, 'YYYY-MM-DD HH24:MI:SS'),v.sell_sccid,v.sell_account,v.sell_staffname ,s.ctusername,s.companyname,s.companyid ");
		sb.append("from dt_bookinginformation n,dt_examinationroom e,dt_productpackaging p,dt_pro_setup ps,dt_hr_staff d,dt_hr_staff f,dtcashierbills s,dt_phone_bill_vo v, Dtgoodsbills g ");
		sb.append("where n.erid = e.erid and n.ppid = p.ppid and p.ppid = ps.ppid and n.directorstaffid = d.staffid and n.coachstaffid = f.staffid ");
		sb.append("and n.journalnum = s.journalnum  and s.cashierbillsid = g.cashierbillsid and  s.cashierbillsid = ? and n.sccid = ? and s.cashierbillsid = v.cashierbillsid and g.goodsname like '%场地预约%'");

		Object obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{cbId, sccId});
       
		return obj;
	}


	/**
	 * 快捷查询考场计时
	 *
	 * @param companyId : 公司id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ProductPackaging theTestTime(String companyId) {

		String hql = " select p from ProductPackaging p where p.companyID = ? and type =? ";

		ProductPackaging ppk = (ProductPackaging) beandao.getBeanByHqlAndParams(hql, new Object[]{companyId, "金币计时"});

		return ppk;
	}

	/**
	 * 判断用户是否是该驾校的审核员
	 *
	 * @param companyId : 公司id
	 * @param sccid:    当前用户sccid
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public boolean userJudge(String sccid, String companyId) {

		boolean bl = false;

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select e.erId from  T_ESHOP_CUSCOM t ,dt_hr_staff d ,dt_examinationroom e where ");
		sb.append(" d.sccid=t.sccid  and e.reviewerstaffid=d.staffid and t.sccid=? and e.status = ? and e.whetherStatus = ? ");
		list.add(sccid);
		list.add("00");
		list.add("00");
		if (companyId != null && !companyId.equals("")) {
			sb.append("and e.companyid = ? ");
			list.add(companyId);
		}

		List<BaseBean> base = beandao.getListBeanBySqlAndParams(sb.toString(), list.toArray());

		if (base != null && base.size() > 0) {
			bl = true;
		}

		return bl;
	}

	/**
	 * 我的练车记录
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param companyId  : 公司id
	 * @param sccId      : 当前用户id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm myRecord(String sccId, String companyId, int pageNumber, int pageSize) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select f.headimage,s.account,to_char(n.biftime, 'YYYY-MM-DD'),to_char(n.checkintime, 'YYYY-MM-DD HH24:MI'), ");
		sb.append("to_char(n.signbacktime, 'YYYY-MM-DD HH24:MI'),n.signinstate,n.howmuchtime,n.money,n.sccid,n.cashierBillsID ");
		sb.append("from dt_BookingInformation n,dt_examinationroom e,t_eshop_cuscom s,dt_hr_staff f ");
		sb.append("where n.erid = e.erid and n.sccid = ? and e.status = ? ");
		list.add(sccId);
		list.add("00");
		if (companyId != null && !companyId.equals("")) {
			sb.append("and e.companyid = ? ");
			list.add(companyId);
		}
		sb.append("and n.sccid = s.sccid and s.staffid = f.staffid ");
		sb.append("order by n.biftime desc ");

		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

		return pageForm;
	}

	/**
	 * 用户练车记录
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param companyId  : 公司id
	 * @param sccId      : 当前用户id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm userRecord(String sccId, String companyId, int pageNumber, int pageSize, String conditions) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select f.headimage,s.account,to_char(n.biftime, 'YYYY-MM-DD'),to_char(n.checkintime, 'YYYY-MM-DD HH24:MI'), ");
		sb.append("to_char(n.signbacktime, 'YYYY-MM-DD HH24:MI'),n.signinstate,n.howmuchtime,n.money,n.sccid,n.cashierBillsID ");
		sb.append("from dt_BookingInformation n,dt_examinationroom e,dt_hr_staff m,t_eshop_cuscom s,dt_hr_staff f ");
		sb.append("where n.erid = e.erid and e.reviewerstaffid = m.staffid ");

		if (sccId != null && !sccId.equals("")) {
			sb.append("and m.sccid = ? ");
			list.add(sccId);
		}

		if (companyId != null && !companyId.equals("")) {
			sb.append("and e.companyid = ? ");
			list.add(companyId);
		}
		sb.append("and e.status = ? and n.sccid = s.sccid and s.staffid = f.staffid ");
		list.add("00");
		if (conditions != null && !conditions.equals("")) {
			sb.append("and s.account like ?  ");
			list.add("%" + conditions + "%");
		}
		sb.append("order by n.biftime desc ");

		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

		return pageForm;
	}

	/**
	 * 查询驾校列表
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param conditions  : 名称
	 * @param itsLocation : 地址
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm ajaxSchoolList(int pageNumber, int pageSize, String conditions, String itsLocation) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		sb.append("select p.ppid,y.logopath,r.ername,r.itslocation ");
		sb.append("from dt_ExaminationRoom r, dt_ccom_com m, dtcontactcompany y,dt_productpackaging p ");
		sb.append("where r.status = ? and r.whetherstatus = ? ");
		list.add("00");
		list.add("00");
		if (itsLocation != null && !itsLocation.equals("")) {
			sb.append("and r.itsLocation like ? ");
			list.add("%" + itsLocation + "%");
		}
		if (conditions != null && !conditions.equals("")) {
			sb.append("and r.ername like ? ");
			list.add("%" + conditions + "%");
		}
		sb.append("and r.companyid = m.compnay_id and m.ccompany_id = y.ccompanyid ");
		sb.append("and r.companyid = p.companyid and p.type like ? ");
		list.add("%金币计时%");

		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

		return pageForm;
	}

	/**
	 * 查询资讯列表
	 *
	 * @param pageNumber
	 * @param pageSize
	 * @param sccId      : 账号ID
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm ajaxInformationList(int pageNumber, int pageSize, String sccId) {

		StringBuilder sb = new StringBuilder();
		sb.append("select p.ppid, p.image, p.goodsname,y.companyname,to_char(p.packagingdate, 'YYYY-MM-DD') ");
		sb.append("from dt_productpackaging p, dt_ccom_com m, dtcontactcompany y ");
		sb.append("where p.sccid = ? and p.type = ? and p.delstatus = ? ");
		sb.append("and p.companyid = m.compnay_id and m.ccompany_id = y.ccompanyid ");

		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", new Object[]{sccId, "会员分享", "00"});

		return pageForm;
	}

	/**
	 * 查询签到详情
	 *
	 * @param conditions :签到id
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object checkTheDetails(String conditions) {

		StringBuilder sb = new StringBuilder();
		sb.append("select f.staffname, to_char(s.signdate, 'YYYY-MM-DD'),s.signinformation, s.signimagepath ");
		sb.append("from dt_wfj_Sign s, t_eshop_cuscom m, dt_hr_staff f ");
		sb.append("where s.signid = ? and s.sccid = m.sccid ");
		sb.append("and m.staffid = f.staffid ");
		Object obj = beandao.getObjectBySqlAndParams(sb.toString(), new Object[]{conditions});

		return obj;
	}

	/**
	 * 查询签到详情
	 *
	 * @param ppids:产品id集合
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> informationList(String ppids) {

		List<BaseBean> list = null;
		if (ppids != null && !ppids.equals("")) {
			Object[] pps = ppids.split(",");
			StringBuilder sb = new StringBuilder();
			sb.append("select p.ppid,y.ccompanyid,p.image,p.goodsname,y.companyname,to_char(p.packagingdate, 'YYYY-MM-DD') ");
			sb.append("from dt_productpackaging p,dt_ccom_com m,dtcontactcompany y where ");
			sb.append("p.companyid = m.compnay_id and m.ccompany_id = y.ccompanyid ");
			sb.append("and (");
			for (int i = 0; i < pps.length; i++) {
				sb.append(" p.ppid = ? or");
			}
			sb.setLength(sb.length() - 2);
			sb.append(")");
			list = beandao.getListBeanBySqlAndParams(sb.toString(), pps);
		}
		return list;
	}

	/**
	 * ajax查询驾校列表
	 *
	 * @param conditions:公司名称
	 * @param companyAddr:公司地址
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm ajaxDrivingList(int pageNumber, int pageSize, String conditions, String companyAddr) {

		StringBuilder sb = new StringBuilder();
		List<Object> list = new ArrayList<Object>();
		/*sb.append("select distinct p.ppid,y.logopath,y.companyname,y.companyaddr ");
		sb.append("from dt_examinationroom e, dt_ccom_com c, dtcontactcompany y,dt_productpackaging p ");
		sb.append("where e.status = ? and e.whetherstatus = ? ");
		sb.append("and e.companyid = c.compnay_id and c.ccompany_id = y.ccompanyid ");*/

		sb.append("select distinct e.companyid,y.logopath,y.companyname,y.companyaddr ");
		sb.append("from dt_examinationroom e, dt_ccom_com c, dtcontactcompany y ");
		sb.append("where e.status = ? and e.whetherstatus = ? ");
		sb.append("and e.companyid = c.compnay_id and c.ccompany_id = y.ccompanyid ");
		list.add("00");
		list.add("00");
		if (conditions != null && !conditions.equals("")) {
			sb.append("and y.companyname like ? ");
			list.add("%" + conditions + "%");
		}
		if (companyAddr != null && !companyAddr.equals("")) {
			sb.append("and y.companyaddr like ? ");
			list.add("%" + companyAddr + "%");
		}
		/*sb.append("and e.companyid = p.companyid and p.type like ? ");
        list.add("%金币计时%");*/

		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(), "select count(*) from (" + sb.toString() + ")", list.toArray());

		return pageForm;

	}

	/**
	 * 签退分金币
	 *
	 * @param bifId
	 */
	public void bookDistribution(String bifId) {
		BookingInformation bif = (BookingInformation) beandao.getBeanByHqlAndParams("from BookingInformation where bifId = ?", new Object[]{bifId});
		if ("02".equals(bif.getSignInState())) {
			ExaminationRoom er = (ExaminationRoom) beandao.getBeanByHqlAndParams("from ExaminationRoom where erId = ?", new Object[]{bif.getErId()});

			CashierBills cb = (CashierBills) beandao.getBeanByHqlAndParams("from CashierBills where journalNum = ? and companyID = ?", new Object[]{bif.getJournalNum(), er.getCompanyId()});
			//分金币
			transService.Distribution(cb.getCashierBillsID(), "cstaff20160325ZAUAJEU6JH6192643691");
		}
	}


	/**
	 * 根据人找绑定的车牌（默认）
	 *
	 * @param sccId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String getCarNumberByTcc(String sccId) {
		String sql = "select c.carNum from DT_VehicleBinding b,dt_Car_CarInformation c,T_ESHOP_CUSCOM t where b.carId = c.carId and b.staffid = t.staffid and t.sccid = ? and b.status = ? and b.isDefault = ?";
		Object obj = beandao.getObjectBySqlAndParams(sql, new Object[]{sccId,"00", "01"});

		return obj == null ? "" : obj.toString();
	}


	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public BookingInformation getBookInfo(String journalNum) {
		String hql = "from BookingInformation where journalNum = ?";
		BookingInformation book = (BookingInformation) beandao.getBeanByHqlAndParams(hql, new Object[]{journalNum});

		return book;
	}


	/**
	 * 获取积分
	 *
	 * @param sccid
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String getJiFen(String sccid) {
		BonusPoints bonusPoints = (BonusPoints) beandao.getBeanByHqlAndParams(" from BonusPoints where sccid = ?", new Object[]{sccid});
		String jf = "0";
		if(bonusPoints!=null){
			 jf = bonusPoints.getBonusPointScore();

			 if(jf!=null&&jf.startsWith(".")){
				 jf = "0"+jf;
			 }
		}
		return jf;


	}

	/**
	 * 获取金币
	 *
	 * @param sccid
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String getJinBi(String sccid) {

		WfjJifen wfjJf = (WfjJifen) beandao.getBeanByHqlAndParams("from WfjJifen where sccid=?", new Object[]{sccid});

		String jb = "0";
		if(wfjJf!=null){
			jb = wfjJf.getWfjJifenScore();

			if(jb!=null&&jb.startsWith(".")){
				jb = "0"+jb;
			}
		}
		return jb;


	}


	/**
	 * 付款后改变预约状态
	 *
	 * @param journalNum
	 */
	@Override
	@Transactional
	public void setBookState(String journalNum) {

		String hql = "from BookingInformation where journalNum = ?";
		BookingInformation book = (BookingInformation) beandao.getBeanByHqlAndParams(hql, new Object[]{journalNum});
		if (book != null) {
			book.setSignInState("02");
			beandao.update(book);
		}
	}


	/**
	 * 获取用户
	 *
	 *
	 * @param sccId
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String,Object> getTcTr( String sccId) {
		Map<String,Object> map = new HashMap<String,Object>();
		TEshopCusCom scc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccId = ?", new Object[]{sccId});
		map.put("scc",scc);
		if (scc != null) {
			TEshopCustomer cus = (TEshopCustomer) baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0 ", new Object[]{scc.getAccount()});
			map.put("cus",cus);
		}

		return map;
	}

	/**
	 * 获取打印小票信息
	 *
	 * @param journalNum
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Object> printInfo(String journalNum) {
		StringBuilder sb = new StringBuilder();
		List<Object> paramlist = new ArrayList<Object>();
		sb.append("select b.journalnum,f.staffname fname,m.account macct, ");
		sb.append("to_char(b.bifTime, 'YYYY-MM-DD HH24:MI:SS'),");
		sb.append("e.ernumber,e.ername,d.staffname dnaem,s.staffname sname, ");
		sb.append("c.account cacct,a.staffname aname,t.companyname,b.bifId,");
		sb.append("to_char(b.checkintime, 'YYYY-MM-DD HH24:MI:SS'),to_char(b.signbacktime, 'YYYY-MM-DD HH24:MI:SS'),b.howMuchTime,b.money,b.signInState,b.qrCode");
		sb.append(" from dt_examinationroom e,dt_BookingInformation b,t_eshop_cuscom m,dt_hr_staff f, ");
		sb.append("dt_hr_staff d,dt_hr_staff s,t_eshop_cuscom c,dt_hr_staff a,dtcompany t ");
		sb.append("where e.erid = b.erid and b.sccid=m.sccid and m.staffid=f.staffid and a.sccid=c.sccid and  t.companyid = e.companyid ");
		sb.append("and e.reviewerstaffid=a.staffid and d.staffid=b.directorstaffid and s.staffid=b.coachstaffid ");
		sb.append("and e.status=? and c.acquiesce=? and b.journalnum = ?");
		paramlist.add("00");
		paramlist.add("01");
		paramlist.add(journalNum);
		List<Object> list = baseBeanService.getListBeanBySqlAndParams(sb.toString(), paramlist.toArray());



		return list;
	}


	/**
	 *
	 * 根据订单号获取详情信息
	 * @param journalNum
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String,String> getBookInfoCash(String journalNum){

		Map<String,String>   mp = new HashMap<String,String>();
		BookingInformation book = (BookingInformation)baseBeanService.getBeanByHqlAndParams("from BookingInformation where journalNum = ?",new Object[]{journalNum});
		if(book!=null) {
			mp.put("sccId", book.getSccId());
			CashierBills cc = (CashierBills) baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?", new Object[]{journalNum});
			if (cc != null) {
				mp.put("cbId", cc.getCashierBillsID());

			}
		}
         return  mp;
	}

	/**
	 * 根据终端号获取绑定的公司companyid
	 * @param posNum
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String,String> getComIDByPosNum(String posNum){
		Map<String,String>  map = new HashMap<String,String>();
		String companyId = "";
		String companyname = "";
         String hql  ="from PosDevice where posNum  = ?";
		PosDevice posDevice = (PosDevice)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{posNum});
		if(posDevice!=null){
			if(posDevice.getAccessCcomID()!=null&&!posDevice.getAccessCcomID().equals("")) {
				CcomCom ccomCom = (CcomCom) baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId = ?", new Object[]{posDevice.getAccessCcomID()});
				if(ccomCom!=null) {
					companyId = ccomCom.getComanyId();
					companyname = posDevice.getAccessCcomName();
					map.put("companyId",companyId);
					map.put("companyname",companyname);
				}
			}
		}

		return  map;
	}


	/**
	 *
	 * 验证刷脸用户
	 * @param openid
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Map<String,Object> faceValidate(String openid){
		Map<String,Object> map = new HashMap<String,Object>();
       String hql = "from TEshopCusCom where openId = ?";
		TEshopCusCom tc = (TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{openid});
		if(tc==null){
			map.put("result","0");
		   //没有账号，就要提示绑定手机号
		}else if(tc.getAccount()==null||tc.getAccount().length()>11){
			map.put("result","1");//有账号，没关联手机号，绑定手机号
		}else{
			String  account = tc.getAccount();

			String hqlc = "from BookingInformation t where to_char(t.bifTime,'YYYY-MM-dd') = to_char(?,'YYYY-MM-dd') and t.sccId = ? and (t.signInState = ? or t.signInState = ?  or t.signInState = ?)";
			BookingInformation book = (BookingInformation)baseBeanService.getBeanByHqlAndParams(hqlc,new Object[]{new Date(),tc.getSccId(),"00","01","04"});
			if(book==null){
				map.put("result","2");//没有预约练车
			}else{
                //有预约的单子
				map.put("result","3");
				map.put("signInState",book.getSignInState());
				map.put("bifId",book.getBifId());
				if("04".equals(book.getSignInState())){
					CashierBills bb = (CashierBills)baseBeanService.getBeanByHqlAndParams("from CashierBills where journalNum = ?",new Object[]{book.getJournalNum()});
					map.put("cbId",bb.getCashierBillsID());
					map.put("sccId", book.getSccId());
				}
			}
		}
		return map;

	}

	/**
	 *
	 * 绑定微分金账号
	 * @param openid
	 * @param tel
	 * @return
	 */
	@Override
	@Transactional
	public Map<String,Object> bindWfjAccount(String openid,String tel,String cardNum){
		Map<String,Object> map = new HashMap<String,Object>();
		List<BaseBean> beans = new ArrayList<BaseBean>();

		if(cardNum!=null&&!cardNum.equals("")) {
			GiftCards giftCards = (GiftCards)baseBeanService.getBeanByHqlAndParams("from GiftCards where cardNumber = ? and state = ?",new Object[]{cardNum,"1"});
			if(giftCards==null){
				map.put("result","4");//无效的卡号
			}else {
				TEshopCusCom tc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where staffid = ?", new Object[]{giftCards.getStaffID()});
			     tel = tc.getAccount();
			}
		}
		if(tel!=null&&!tel.equals("")){
			String hqlc = "from BookingInformation t where to_char(t.bifTime,'YYYY-MM-dd') = to_char(?,'YYYY-MM-dd') and t.sccId = (select tc.sccId from TEshopCusCom tc where tc.account = ? and tc.acquiesce = '01') and (t.signInState = '00' or t.signInState = '01' or t.signInState = '04')";
			BookingInformation book = (BookingInformation)baseBeanService.getBeanByHqlAndParams(hqlc,new Object[]{new Date(),tel});
			if(book==null){
                 map.put("result","0"); //该手机号没有预约练车，请确认手机号或先预约
			}else{

				TEshopCusCom wxtc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where openId = ?",new Object[]{openid});
				TEshopCustomer wxtr = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where openId = ?",new Object[]{openid});

				TEshopCusCom teltc = (TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account = ? and logOff=0 and acquiesce = '01'",new Object[]{tel});
				TEshopCustomer teltr = (TEshopCustomer)baseBeanService.getBeanByHqlAndParams("from TEshopCustomer where account = ? and logOff=0",new Object[]{tel});
				if(teltc!=null&&teltc.getOpenId()!=null){
					map.put("result","1");//该手机账号已绑定微信，不能重复绑定
				}else if(wxtc==null){
					map.put("result","3");//绑定成功
                         teltc.setOpenId(openid);
                         teltr.setOpenId(openid);
					beans.add(teltc);
					beans.add(teltr);
					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null,null);
				 }else{

                     //有2个账号
					 teltc.setOpenId(openid);
					 teltr.setOpenId(openid);
					if (wxtc != null&&wxtc.getAccount()!=null&&wxtc.getAccount().length()!=11) {
						wxtc.setOpenId(openid + tel);
						wxtc.setAccount(openid + tel);
						wxtr.setOpenId(openid + tel);
						wxtr.setAccount(openid + tel);
                        beans.add(wxtr);
                        beans.add(wxtc);
					}
					map.put("result","3");//绑定成功
					beans.add(teltr);
					beans.add(teltc);

					baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,null,null);

				}
				map.put("bifId",book.getBifId());

			}
		}
		return  map;

	}


	/**
	 *   根据用户获取预约ID
	 * @param sccId
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String getbookId(String sccId){
		String hqlc = "from BookingInformation t where to_char(t.bifTime,'YYYY-MM-dd') = to_char(?,'YYYY-MM-dd') and t.sccId = ? and (t.signInState = ? or t.signInState = ?  or t.signInState = ?)";
		BookingInformation book = (BookingInformation)baseBeanService.getBeanByHqlAndParams(hqlc,new Object[]{new Date(),sccId,"00","01","04"});
		if(book!=null){
			return book.getBifId();
		}

		return "";

	}

	/**
	 *
	 * 获取学员姓名
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public  String getStudentName(String tel){
		String hql = "from Staff f where f.staffID = (select r.staffid from TEshopCustomer r where r.account = ?)";
		Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{tel});
		if(staff!=null){
			return staff.getStaffName();
		}
        return "";
	}
	
	/**
	 * 
	 * 车辆出去记录
	 * @param carNum
	 * @return
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public CarManage getCarInRecord(String carNum,String status,String equip){
		String hqlc = "from EquipmentInformation c where c.equipmentNumber = ?";

		EquipmentInformation equipmentInformation = (EquipmentInformation)baseBeanService.getBeanByHqlAndParams(hqlc,new Object[]{equip});
		if(status==null||status.equals("")){
			status="0";//如果是旧的二维码默认为出
		}
		String hql1 = "from CarManage c where c.carNumber = ? and c.status = ? and c.chargeState1 = '00' and c.open1 = ? and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?) order by c.indate desc";
		String hql0 = "from CarManage c where c.carNumber = ? and c.status = ? and (c.chargeState = '00' or (c.chargeState is null and c.chargeType ='00')) and c.open = ? and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?) order by c.outdate desc";
		String hql = "";
		if("1".equals(status)){
			hql = hql1;
		}else{
			hql = hql0;
		}

		List<BaseBean> listcm = baseBeanService.getListBeanByHqlAndParams(hql,
				new Object[]{carNum,status,"0",equipmentInformation.getSiteId()});

		if(listcm.size()==0){

			if("1".equals(status)){
				hql = hql0;
				listcm = baseBeanService.getListBeanByHqlAndParams(hql,
						new Object[]{carNum,"0","0",equipmentInformation.getSiteId()});
			}else{
				hql = hql1;
				listcm = baseBeanService.getListBeanByHqlAndParams(hql,
						new Object[]{carNum,"1","0",equipmentInformation.getSiteId()});
			}


		}


		CarManage cm = null;
		if(listcm.size()>0){
			 cm = (CarManage) listcm.get(0);
			if(!"1".equals(cm.getStatus())) {
				if(cm.getOutdate()!=null&&!cm.getOutdate().equals("")){
					Date end = getCurrentYMD(cm.getOutdate());
					Date today = getCurrentYMD(new Date());
					if (end.compareTo(today) == 0) {
						//说明是在当天出去
					} else {
						cm = null;
					}
				}

			}

		}


		
		return cm;
	}

	/**
	 *
	 * 根据车辆类型查车
	 * @param carType
	 * @return
	 */
	public List<BaseBean> getBindCar(String carType,String staffID){

		String hql = "from CarInformation n where n.carID in(select v.carID from VehicleBinding v where v.staffID = ? and  v.status = ?) ";

		if(carType!=null&&carType.equals("c")){
			hql += "and n.carNum like ? ";
		}else{
			hql += "and n.carNum not like ? ";

		}
       List<BaseBean> clist = beandao.getListBeanByHqlAndParams(hql,new Object[]{staffID,"00","%学%"});
       return clist;
	}
}