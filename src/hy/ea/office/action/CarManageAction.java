package hy.ea.office.action;

import com.faceSDK.faceUtil.DateUtils;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.util.SessionWrap;
import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.finance.BenDis.JoinFans;
import hy.ea.bo.finance.BenDis.ProSetupSub;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.invoicing.FinancialBill;
import hy.ea.bo.office.*;
import hy.ea.office.service.CarManageService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Scope("prototype")
public class CarManageAction {
	private static final Logger logger = LoggerFactory.getLogger(CarManageAction.class);
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CarManageService cmService;

	private ParkingSpace parkingSpace;
	@Resource
	private ShowExcelService excelService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private String parameter;
	private int pageNumber;
	private String result;
	private Object results;
	private String search;

	private String type;
	private String title;

	private CarManage cm;
	private CAccount caccount;
	private FeeScale feeScale;
	private EquipmentInformation ef;
	private VenueInformation vf;
	private String carmID;
	private String hasBeenUnder;//已满/未满
	private String companyName;//公司名称
	private String numberOrName;//编号或名称
	private Map<String,ProSetupSub> pssMap;
	private boolean siteJudge;

	private ProductPackaging ppk;
	private ProSetup proSetup;



	private ExaminationRoom exRoom;
	private ExaminationRoomCharge erCharge;
	private TEshopCusCom tcc;
	private BookingInformation bfmation;
	private Object object;
	private File photo;
	private String photoFileName;
	private String brokerage;
	private String timeType;
	private String inTime;
	private String outTime;
	private InputStream excelStream;

	private TimingCharging timingCharging;

	private static Map<String,String> dateMap = new HashMap<String,String>();
    private static Map<String,String> indateMap = new HashMap<String,String>();
    private static Map<String,String> outdateMap = new HashMap<String,String>();



	/**
	 * 纠正车辆错误数据 *
	 *
	 * @return
	 */
	public String errorCorrection(){

		boolean b = cmService.errorCorrection(cm.getCarmID(),carmID);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * pc查询错误车牌对应的进入车辆信息 *
	 *
	 * @return
	 */
	public String errorLicensePlateDetails(){

		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		List<Object> list = cmService.licensePlateComparison(account.getCompanyID(),vf.getSiteId(),cm.getCarNumber());

		map.put("list", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 实时效验场地编号 *
	 *
	 * @return
	 */
	public String verificationsite(){

		boolean b = cmService.verificationsite(numberOrName);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	/**
	 * 实时效验设备编号 *
	 *
	 * @return
	 */
	public String verificationfacility(){

		boolean b = cmService.verificationfacility(numberOrName);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 添加或修改设备详情 *
	 *
	 * @return
	 */
	public String addOrUpdateDeviceLnfo(){

		boolean b = cmService.addOrUpdateDeviceLnfo(ef);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 查询场地详情 *
	 *
	 * @return
	 */
	public String deviceLnfo(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}
		map = cmService.deviceLnfo(ef.getEquipmentId(),siteJudge,account.getCompanyID());
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 删除设备 *
	 *
	 * @return
	 */
	public String removeEquipment(){
		boolean b = cmService.removeEquipment(ef.getEquipmentId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 查询设备列表 *
	 *
	 * @return
	 */
	public String facilityList(){
		CAccount account = AccessToTheCompanyId();

		if(account==null)
		{
			return "nologin";
		}

		StringBuilder sb =  new StringBuilder();
		logger.debug("设施列表查询开始");
		List<Object> list  = new ArrayList<Object>();
		sb.append("select e.equipmentid,y.companyname,e.unittype,e.equipmentnumber, ");
		sb.append("e.devicename,e.manufacturer,to_char(e.equipmentdate, 'YYYY-MM-DD HH24:MI:SS'), ");
		sb.append("to_char(e.installationTime, 'YYYY-MM-DD HH24:MI:SS'),v.sitename,e.status,e.channel ");
		sb.append("from dt_venueinformation v,dt_equipmentinformation e,dt_ccom_com c,dtcontactcompany y ");
		sb.append("where v.companyid = ? and v.siteid = e.siteid and v.companyid = c.compnay_id ");
		sb.append("and c.ccompany_id = y.ccompanyid ");
		list.add(account.getCompanyID());
		if (numberOrName!=null && !numberOrName.equals(""))
		{
			sb.append("and (e.equipmentNumber like ? or e.deviceName like ?) ");
			list.add("%"+numberOrName+"%");
			list.add("%"+numberOrName+"%");
		}
		if (hasBeenUnder!=null && !hasBeenUnder.equals(""))
		{
			sb.append("and e.status = ? ");
			list.add(hasBeenUnder);
		}
		sb.append("order by e.equipmentdate desc ");

		pageForm = baseBeanService.getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sb.toString(),
				"select count(*) from (" + sb.toString() + ")",list.toArray());

		return "facilityList";
	}


	/**
	 * 删除场地 *
	 *
	 * @return
	 */
	public String delSite(){
		boolean b = cmService.delSite(vf.getSiteId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 添加或修改场地详情 *
	 *
	 * @return
	 */
	public String addOrUpdateSiteDetails(){

		boolean b = cmService.addOrUpdateSiteDetails(vf);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 查询场地详情 *
	 *
	 * @return
	 */
	public String siteDetails(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}
		map = cmService.siteDetails(vf.getSiteId(),siteJudge,account.getCompanyID());
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 查询场地列表 *
	 *
	 * @return
	 */
	public String siteList(){
		CAccount account = AccessToTheCompanyId();

		if(account==null)
		{
			return "nologin";
		}

		pageForm = cmService.siteList(hasBeenUnder,account.getCompanyID(),numberOrName,pageForm,pageNumber);

		return "siteList";
	}

	/**
	 * ajax查询场地列表 *
	 *
	 * @return
	 */
	public String ajaxSiteList(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		pageForm = cmService.siteList(hasBeenUnder,account.getCompanyID(),numberOrName,pageForm,pageNumber);

		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}


	/**
	 * 进出车辆管理查看信息 *
	 *
	 * @return
	 */
	public String getList() {
		CAccount account = AccessToTheCompanyId();

		if(account==null)
		{
			return "nologin";
		}
		pageForm = cmService.InformationList(account.getCompanyID(), cm.getStatus(),vf!=null?vf.getSiteId():"",numberOrName , pageForm, pageNumber);
		if (cm!=null&&cm.getStatus()!=null&&cm.getStatus().equals("2"))
		{
			return "errorlist";
		}
		else
		{
			return "list";
		}
	}
	/**
	 * 查询汇总 *
	 *
	 * @return
	 */
	public String collectList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = AccessToTheCompanyId();

		if(account==null)
		{
			return "nologin";
		}
		List<Object> list = new ArrayList<Object>();

		StringBuilder sb = new StringBuilder();
		sb.append("select c.carmID,c.carNumber,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),v.siteNumber,v.sitename,e.equipmentNumber,e.devicename, ");
		sb.append("f.staffname,c.status,c.carmNumber,c.money,c.time,c.model,c.chargeType,c.chargeState from ");
		sb.append("dt_VenueInformation v,dt_EquipmentInformation e,Dt_Carmanage c,dt_hr_staff f ");
		sb.append("where v.siteid=e.siteid and e.equipmentnumber=c.equipmentnumber and v.staffId=f.staffId ");
		sb.append("and v.companyId = ? and (c.status = ? or c.status = ?) ");
		list.add(account.getCompanyID());
		list.add("0");
		list.add("1");

		if (cm!=null&&cm.getCarNumber()!=null && !"".equals(cm.getCarNumber()))
		{
			sb.append("and c.carNumber like ? ");
			list.add("%"+cm.getCarNumber()+"%");
		}

		if("none".equals(timeType)){
			//进出时间包含在该时间段内
			if(inTime!=null&&!"".equals(inTime)){
				sb.append("and c.indate >= to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
				list.add(inTime);
			}
			if(outTime!=null&&!"".equals(outTime)){
				sb.append("and c.outdate<= to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
				list.add(outTime);
			}
		}else if("in".equals(timeType)){
			//进入时间包含在该时间段内
			if(inTime!=null&&!"".equals(inTime)){
				sb.append("and c.indate >= to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
				list.add(inTime);
			}
			if(outTime!=null&&!"".equals(outTime)){
				sb.append("and c.indate<= to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
				list.add(outTime);
			}
		}else if("out".equals(timeType)){
			//离开时间包含在该时间段内
			if(inTime!=null&&!"".equals(inTime)){
				sb.append("and c.outdate >= to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
				list.add(inTime);
			}
			if(outTime!=null&&!"".equals(outTime)){
				sb.append("and c.outdate<= to_date(?,'yyyy-MM-dd HH24:MI:SS') ");
				list.add(outTime);
			}
		}
		if(ef!=null && ef.getEquipmentNumber()!=null && !"".equals(ef.getEquipmentNumber())){
			sb.append("and (e.equipmentnumber like ? or e.devicename like ?) ");
			list.add("%"+ef.getEquipmentNumber()+"%");
			list.add("%"+ef.getEquipmentNumber()+"%");
		}
		if(vf!=null && vf.getSiteNumber()!=null && !"".equals(vf.getSiteNumber())){
			sb.append("and (v.sitenumber like ? or v.sitename like ?)");
			list.add("%"+vf.getSiteNumber()+"%");
			list.add("%"+vf.getSiteNumber()+"%");
		}
		sb.append("order by c.indate desc");
		pageForm = baseBeanService.getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sb.toString(),
				"select count(*) from (" + sb.toString() + ")",list.toArray());
		session.put("sqllist",sb.toString());
		session.put("params",list);
		return "collectList";
	}

	public String showExcel(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = AccessToTheCompanyId();
		String sql=(String)session.get("sqllist");
		String sel="select c.carmNumber,c.carNumber,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),to_char(case when c.money is null and c.status<>'1' then'未设置收费标准,请前往设置' else c.money end),c.time,v.siteNumber,v.sitename,e.equipmentNumber,e.devicename, " +
				"f.staffname,to_multi_byte(case c.status when'1' then'院内' else'离开' end),to_char(CASE C.CHARGESTATE WHEN '00' THEN '未结算' WHEN '01' THEN '包月或包年' WHEN '02' THEN '金币/积分收费' WHEN '03' THEN '扫码支付' WHEN '04' THEN '现金支付' WHEN '05' THEN '消费金额未0无需收费' ELSE '未出库' END ) ";
		sql=sel+sql.substring(sql.indexOf("from"),sql.length());
		List<Object> params=(List<Object>)session.get("params");
		if(account==null||sql==null||params.size()==0)
		{
			return "nologin";
		}
		List<BaseBean> list = (List<BaseBean>) baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
		excelStream = excelService.showExcel(new String[]{"序号", "编号", "车牌号", "进入时间", "离开时间", "总收费(元)", "共计时间", "场地编号",
				"场地名称", "设备编号","设备名称", "场地负责人", "状态","收费方式"}, list);

		CLogBook logBook = logBookService.saveCLogBook(null, "停车场管理导出", account);
		baseBeanService.update(logBook);
		return "showexcel";
	}
	/**
	 * 查询车辆停车信息以及对应的信息 *
	 *
	 * @return
	 */
	public String queryVehicleInformation(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		map = cmService.queryVehicleInformation(cm.getCarmID(),account.getCompanyID());

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 查询车辆停车详细信息 *
	 *
	 * @return
	 */
	public String queryMessage(){

		Object obj = cmService.message(cm.getCarmID());
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("obj", obj);
		return "queryMessage";
	}
	/**
	 * 删除车辆停车信息 *
	 *
	 * @return
	 */
	public String delVehicleInformation(){

		boolean b = cmService.delVehicleInformation(cm.getCarmID());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * pc添加/修改车辆停车信息 *
	 *
	 * @return
	 */
	public String addOrUpdateVehicle(){

		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		boolean b = cmService.addOrUpdateVehicle(cm,account.getCompanyID());
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 查询收费标准列表 *
	 *
	 * @return
	 */
	public String feescale() {
		HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = AccessToTheCompanyId();
		String rs = request.getParameter("news");
		String sajax = request.getParameter("sajax");
		if(account==null)
		{
			if ("news".equals(rs)) {

				return "login";
			}

			return "nologin";
		}
		pageForm = cmService.getFeeList(
				null != pageForm ? pageForm.getPageNumber() : 1,
			20,vf,account.getCompanyID());
		String totalPct =  cmService.getTotalPct(account.getCompanyID());
		if("sajax".equals(sajax)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pageForm", pageForm);
			map.put("totalPct", totalPct);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}



		request.setAttribute("totalPct",totalPct);
		request.setAttribute("companyId", account.getCompanyID());

		//代理类别查询
		List<BaseBean> dlList = baseBeanService.getListBeanByHqlAndParams
				("from ProductPackaging where type=? and goodsname<>? order by sorting",
						new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
		request.setAttribute("dlList",dlList);
		if("news".equals(rs)){
			return "feenews";
		}
		return "feescale";
	}

	/**
	 * 启用收费标准 *
	 *
	 * @return
	 */
	public String startUsing() {
		boolean b= cmService.startUsing(feeScale.getFeecID(),feeScale.getStartUsing());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 添加修改收费标准 *
	 *
	 * @return
	 */
	public String addOrUpdatefeescale() {
		boolean checkFee =  cmService.checkAddFee(feeScale.getCompanyID(),feeScale.getSiteId(),feeScale.getCarType(),feeScale.getTimeUnits(),feeScale.getFeecID());
		boolean b = false;
		if(checkFee) {
			b = cmService.addOrUpdatefeescale(feeScale, proSetup, pssMap);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		map.put("checkFee", checkFee);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}
	/**
	 * 删除收费标准 *
	 *
	 * @return
	 */
	public String delStandard() {

		boolean b = cmService.delStandard(feeScale.getFeecID());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 查询收费标准详情 *
	 *
	 * @return
	 */
	public String queryStandard(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		feeScale = cmService.queryStandard(feeScale.getFeecID());

		List<BaseBean> number = cmService.queryNumber(account.getCompanyID(),feeScale.getSiteId());

		List<BaseBean> list = cmService.queryStaff(account.getCompanyID(),"");
		List<BaseBean> setupSublist = cmService.allProSetupSub(proSetup.getSuid());
		map.put("setupSublist", setupSublist);
		map.put("feeScale", feeScale);
		map.put("number", number);
		map.put("list", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 查询公司场地编号 *
	 *
	 * @return
	 */
	public String queryNumber(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		List<BaseBean> number = cmService.queryNumber(account.getCompanyID(),null);

		map.put("number", number);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 查询设备编号 *
	 *
	 * @return
	 */
	public String queryequipmentNumber(){

		List<BaseBean> standard = cmService.queryequipmentNumber(ef.getSiteId());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("standard", standard);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}



	/**
	 * 查询公司场地编号以及员工 *
	 *
	 * @return
	 */
	public String queryNumberStaffn(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		List<BaseBean> number = cmService.queryNumber(account.getCompanyID(),null);

		List<BaseBean> list = cmService.queryStaff(account.getCompanyID(),parameter);

		map.put("number", number);
		map.put("list", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	/**
	 * 查询员工 *
	 *
	 * @return
	 */
	public String queryStaffn(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		List<BaseBean> list = cmService.queryStaff(account.getCompanyID(),parameter);

		map.put("list", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 请求方法 *
	 *
	 * @return
	 */
	public String programCalls(){
		String state = "";
		try {
			HttpServletRequest request = ServletActionContext.getRequest();

			String post = myRequestWrapper(request);

			JSONObject json = JSONObject.fromObject(post);

			String chLicense = json.getString("chLicense");

			String date = json.getString("date");
			System.out.print(date+"pro");
			state = cmService.programCalls(chLicense);

		} catch (IOException e) {
			state = "00";
		} catch (ParseException e) {
			state = "00";
		}


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", state);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 *
	 * 打印小票
	 * @return
	 */
	public String printInfo() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String carmID = request.getParameter("carmID");

		Object obj = cmService.getCarManageInfo(carmID);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", obj);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 进出口方法 *
	 *
	 * @return
	 */
	public String shibie() {
		//	System.out.print("进入shibie");

		boolean bl = false;
		int Open = 0;
		String  romID = "";
		String number = "";
		String channel = "";
		String equipmentNumber = "";
		String panoramabase64 = "";
		String plateNumberbase64 = "";
		String date = "";
		JSONObject jsonalarm = null;
		JSONObject jsonresult = null;
		String numberType = "";
		JSONObject jsonplate = null;
		try {
			HttpServletRequest request1 = ServletActionContext.getRequest();


			InputStream in=request1.getInputStream();
			int size=request1.getContentLength();

			// logger.info("调试信息");
			if (in != null && size > 0) {
				int readCount = 0; // 已经成功读取的字节的个数
				int nRead = 0;
			byte[] buf = new byte[size];
				try {
					while (readCount < size) {
					nRead = in.read(buf, readCount, size - readCount);
						if( nRead == -1) // 到末尾
					{
						break;
					}
					readCount += nRead;
				}
				//	  logger.info("调试信息");
					JSONObject json = null;
					try {
					json = JSONObject.fromObject(new String(buf));

				if (json != null
							&& json.toString().indexOf("AlarmInfoPlate") == -1) {
						JSONObject xtparam = json
								.getJSONObject("KeepAlive");
					if (xtparam != null) {
							if (xtparam.toString().indexOf("ROMID") != -1) {
									 romID = xtparam.getString("ROMID");// 相机ID
									//	logger.info("值：{}", xtparam);
										logger.info("romID: {}", romID);
							//   romID = request1.getParameter("romID");

									if (romID != null && !romID.equals("")) {
										equipmentNumber = romID;
										String hqlout1 = "from CarManage where equipmentNumber = ?  order by indate desc";
                                          List<BaseBean> clic =  baseBeanService.getListBeanByHqlAndParams(hqlout1,new Object[]{romID});
										date = Utilities.getDateString(new Date(), "yyyy-MM-dd HH:mm:ss");

                                          if(clic.size()>0){
                                          	CarManage  cs = (CarManage)clic.get(0);
                                          	number = cs.getCarNumber();
                                          	channel = cs.getStatus();
                                          	if(channel.equals("0")){
                                          		//如果是出去的心跳
                                                      if("1".equals(cs.getOpen())){
														  //说明抬过杆了
														  logger.info("[道闸控制] 出口抬杆完成");
														  return Action.SUCCESS;
													  }

											}else {
												if("1".equals(cs.getOpen1())){
													//说明抬过杆了
													logger.info("[道闸控制] 入口抬杆完成");

													return Action.SUCCESS;
												}
											}
										  }

//
//										//logger.info("心跳请求");
//										// 如果没有进入的数据先查询下有没有最新未结算的记录，如果有的话，不给开门提示有未结算的，先处理未结算的
//										String hqlout = "from CarManage where equipmentNumber = ? and status = ? and open = ? and outdate is not null order by outdate desc";
//										List<BaseBean> listcmout = baseBeanService
//												.getListBeanByHqlAndParams(
//														hqlout,
//														new Object[] { romID,
//																"0", "0" });
//										CarManage cmout = null;

//										if (listcmout.size() > 0) {
//											cmout = (CarManage) listcmout
//													.get(0);
//
//										}
//										if (cmout != null&&cmout
//												.getChargeState()!=null
//												&& !"00".equals(cmout
//												.getChargeState())) { // 说明上一setOpen("1")个离开的付款了
//
//											// 等于0的时候没太过杆
//											cmout.setOpen("1");//222
//											baseBeanService.update(cmout);
//											logger.info("抬杆");
//
//											Open = 1;// 抬杆
//											JSONObject jsonObjList = new JSONObject();
//
//											JSONObject jsonObjList1 = new JSONObject();
//
//											jsonObjList1.accumulate("Open",
//													Open);
//
//											JSONObject jsonObj1 = new JSONObject();
//											jsonObj1.accumulate("data", "");
//											jsonObj1.accumulate("datalen", 0);
//
//											jsonObjList1.accumulate(
//													"SerialData", jsonObj1);
//											jsonObjList.accumulate("Response",
//													jsonObjList1);
//											results = jsonObjList;
//											if (results != null) {
//												System.out.print(results
//														.toString());
//											}
//											return Action.SUCCESS;
//
//										} else {
//											return Action.SUCCESS;
//										}

									}

								} else {
									return Action.SUCCESS;
								}
	}
				}
					} catch (Exception e) {
						logger.info("车辆粗加工完成");
						logger.error("操作异常", e);
					}
					if (romID == null|| romID.equals("")) {
						jsonalarm = json.getJSONObject("AlarmInfoPlate");
						jsonresult = jsonalarm.getJSONObject("result");

						jsonplate = jsonresult.getJSONObject("PlateResult");

						channel = jsonalarm.getString("channel");


						logger.info("值：{}", channel);
						logger.info("值：{}", equipmentNumber);
						number = jsonplate.getString("license");
						logger.info("值：{}", number);

					}
					if(number!=null&&!number.equals("")) {
						if (romID == null || romID.equals("")) {
							 date = jsonplate.getString("recotime");// 识别时间
							System.out.print(date + "shibie");
							 equipmentNumber = jsonalarm.getString("seriaIno");// 设备编号
							logger.info("equipmentNumber: {}", equipmentNumber);
					    	panoramabase64 = jsonplate.getString("imageFile");//全景图
						     plateNumberbase64 = jsonplate.getString("imageFragmentFile");//车牌图
							 numberType = jsonplate.getString("type");//车牌类型
							String triggerType = jsonplate.getString("triggerType");//车牌类型
							logger.info("triggerType: {}", triggerType);
						;
						}
						// 通过设备id查询到公司id以及人员id
						StringBuilder sb = new StringBuilder();
						sb.append("select t from ");
						sb.append("EquipmentInformation e,VenueInformation v,TEshopCusCom t ");
						sb.append("where e.siteId=v.siteId and v.companyId=t.companyId ");
						sb.append("and e.equipmentNumber=? ");
						TEshopCusCom cuscom = (TEshopCusCom) baseBeanService
								.getBeanByHqlAndParams(sb.toString(),
										new Object[]{equipmentNumber});
						if (cuscom == null) {
							logger.info("录入车辆失败请根据下面设备编号查询账号是否存在--------设备编号:: {}", equipmentNumber);
							Open = 0;
						} else {
							// 图片存储路径
							String photopath = "upload_files\\theLicenseInformation\\"
									+ cuscom.getCompanyId() + "\\" + cuscom.getStaffid();
							// 重命名
							String upName1 = UUID.randomUUID().toString()
									+ System.currentTimeMillis() + ".jpeg";
							String upName2 = UUID.randomUUID().toString()
									+ System.currentTimeMillis() + ".jpeg";
							// 生成全景jpeg图片
							GenerateImage(panoramabase64, photopath, upName1);
							// 生成车牌jpeg图片
							GenerateImage(plateNumberbase64, photopath, upName2);


							if (channel.equals("1")) {// 进入停车场

								//进入时候判断停车场有无车位了
								Map<String, String> parking = cmService.searchParkingCode(equipmentNumber);
								String parkingCode = "";
								String parksId = "";
								if (parking != null && "suc".equals(parking.get("result"))) {
									parkingCode = parking.get("parkingCode");
									parksId = parking.get("parksId");

								}

								if (parkingCode == null
										|| parkingCode.equals("")) {
									Open = 0;// 不开无停车位
									cmService.pushJG(cuscom.getCompanyId(), "in", parkingCode, null, equipmentNumber,"");

								} else {

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
									Map<String,Object> mp = cmService.checkCarRecord(number,date,photopath,upName1,upName2,equipmentNumber,cuscom.getCompanyId(), channel, numberType,parksId);
                                    Open = (int)mp.get("Open");

									String tip = String.valueOf(mp.get("tip"));
									String carmID = String.valueOf(mp.get("carmID"));
									String  ests = "0";


										Date start = Utilities.getDateFromString(date,"yyy-MM-dd HH:mm:ss");
										if(dateMap!=null){
											String dates = dateMap.get(carmID);
											if(dates!=null&&!dates.equals("")){

												Date end1 = Utilities.getDateFromString(dates,"yyyy-MM-dd HH:mm:ss");
												long diff = start.getTime()-end1.getTime();
												long second = diff/1000;
												if(second<=15){
													ests = "1";
												}else{
													//大于15
													dateMap.put(carmID,date);
												}

											}else{
												dateMap.put(carmID,date);
											}



									}

                                 if("0".equals(ests)) {
									 cmService.pushJG(cuscom.getCompanyId(), "in", parkingCode, carmID, equipmentNumber, tip);
								 }

								}

							} else if (channel.equals("0")) {// 离开停车场
								logger.info("[车辆检测] 车辆离开，车牌号：{}", number);
								String hql = "from CarManage where carNumber=? and status=? and indate is not null order by indate desc";
								List<BaseBean> listcm = baseBeanService.getListBeanByHqlAndParams(hql,
										new Object[]{number, "1"});
								if(listcm.size()>0){
									cm = (CarManage) listcm.get(0);
								}


								if (cm != null) {
									logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);

									cmService.updateManageCar(cm, number, date, equipmentNumber, photopath, upName1, upName2, channel);
									logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);
									String state = cmService.programCalls(cm.getCarmID());
									if ("01".equals(state)) {
										Open = 1;//开
										logger.info("0元和套餐直接成功");
									} else {
										Open = 0;//不开

										logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);

									}
									cmService.pushJG(cuscom.getCompanyId(), "out", null, cm.getCarmID(), equipmentNumber,"");


								} else {
									logger.info("离开时二次识别车辆");
									//如果没有进入的数据先查询下有没有最新未结算的记录，如果有的话，不给开门提示有未结算的，先处理未结算的
									String hqlout = "from CarManage where carNumber=? and status=? and outdate is not null order by outdate desc";
									List<BaseBean> listcmout = baseBeanService.getListBeanByHqlAndParams(hqlout,
											new Object[]{number, "0"});
									CarManage cmout = null;

									if(listcmout.size()>0){
										cmout = (CarManage) listcmout.get(0);

									}


									String estss = "0";
									Date startc = Utilities.getDateFromString(date,"yyy-MM-dd HH:mm:ss");
									if(dateMap!=null){
										String datesc = dateMap.get(cmout.getCarmID());
										if(datesc!=null&&!datesc.equals("")){

											Date endc = Utilities.getDateFromString(datesc,"yyyy-MM-dd HH:mm:ss");
											long diffc = startc.getTime()-endc.getTime();
											long secondc = diffc/1000;
											if(secondc<=15){
												estss = "1";
											}else{
												//大于15
												dateMap.put(cmout.getCarmID(),date);
											}

										}else{
											dateMap.put(cmout.getCarmID(),date);
										}



									}

									if(cmout!=null&&"00".equals(cmout.getChargeState())){
										logger.info("之前有未结算的提示结算");
										//说明之前有未结算的提示结算
										Open = 0;//不开
										if("0".equals(estss)){
											cmService.pushJG(cuscom.getCompanyId(), "out", null, cmout.getCarmID(), equipmentNumber,"");

										}

									}else{
										logger.info("没有未结算");
										if(cmout!=null&&!"00".equals(cmout.getChargeState())){  //说明上一个离开的付款了，现在没有进入记录，查询上一个离开时间，如果在20分钟内算是同一次出去
											logger.info("查询上次结算记录");
											Date start  = new Date();
											Date  out = cmout.getOutdate();

											long diff = start.getTime()-out.getTime();
											logger.info("diff: {}", diff);
											long minute = diff/60/1000;
											logger.info("minute: {}", minute);
											if(minute<20){//算是同一次出去
												logger.info("小于20分钟");
												Open = 1;//开门

                                                if("0".equals(estss)){
													cmService.pushJG(cuscom.getCompanyId(), "out", null, cmout.getCarmID(), equipmentNumber,"");

												}



											}else{
												logger.info("大于20分钟");
												Open = 0;//不开门

											}
											logger.info("开门状态：{}", Open);
										}

										if(Open==0){//异常数据处理，没有进入记录
											//离开停车场查询不到进入数据则添加一个出口的数据以便于查询
											//进入时候判断停车场有无车位了
											Map<String,String> parking = cmService.searchParkingCode(equipmentNumber);

											String parksId = "";
											if(parking!=null&&"suc".equals(parking.get("result"))){

												parksId = parking.get("parksId");

											}
											String carmID = cmService.addErrorOut(number, date, equipmentNumber, photopath, upName1, upName2,cuscom.getCompanyId(),channel,numberType,parksId);
											logger.info("异常添加一个出去记录");
											String state = cmService.programCalls(carmID);
											if ("01".equals(state)) {
												Open = 1;//开
												logger.info("0元和套餐直接成功");
											} else {
												Open = 0;//不开

												logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);

											}
											cmService.pushJG(cuscom.getCompanyId(), "out", null, carmID, equipmentNumber,"");



										}

									}



								}
							}
						}
					}else{
						Open = 0;
					}
					bl = true;
				} catch (Exception e) {
					logger.error("操作异常", e);
				}
			}
			else{
				Open = 0;
				logger.info("size没有值");

			}



		} catch (IOException e) {
			Open = 0;
			logger.info("报错1");
			logger.error("操作异常", e);
		} catch (Exception e) {
			logger.error("操作异常", e);
			Open = 0;
			logger.info("报错2");
		}
		logger.info("Open: {}", Open);
		if(Open==1){

			JSONObject jsonObjList = new JSONObject();


			JSONObject jsonObjList1 = new JSONObject();

			jsonObjList1.accumulate("Open",Open);

			JSONObject jsonObj1 = new JSONObject();
			jsonObj1.accumulate("data", "");
			jsonObj1.accumulate("datalen", 0);

			jsonObjList1.accumulate("SerialData", jsonObj1);

			jsonObjList.accumulate("Response", jsonObjList1);
			results = jsonObjList;
		}
		if(results!=null){
			System.out.print(results.toString());
		}

		return Action.SUCCESS;
	}

	public String shenzhenshibie2() {


	JSONObject jsonObjList = new JSONObject();

		JSONObject jsonObjList1 = new JSONObject();

		jsonObjList1.accumulate("info",
				"ok");

		jsonObjList1.accumulate("is_pay",
				"true");


//		//语音播报
//		JSONObject jsonObjList2 = new JSONObject();
//		jsonObjList2.accumulate("type","ps_voice_play");
//         String content = "什么鬼东西";
//		String s =  new String(Base64.getEncoder().encode(content.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
//
//		jsonObjList2.accumulate("voice",s);
//		jsonObjList2.accumulate("voice_interval",0);
//		jsonObjList2.accumulate("voice_volume",100);
//		jsonObjList2.accumulate("voice_male",1);
//
//		jsonObjList1.accumulate("playserver_json_request",jsonObjList2);


		JSONObject jsonObjList2 = new JSONObject();
		jsonObjList2.accumulate("serialChannel",0);
         //String content = "什么鬼东西";
		//String s =  new String(Base64.getEncoder().encode(content.getBytes(StandardCharsets.UTF_8)),StandardCharsets.UTF_8);
		jsonObjList2.accumulate("dataLen",123);

		jsonObjList1.accumulate("serialData",jsonObjList2);
		jsonObjList.accumulate("Response_AlarmInfoPlate",
				jsonObjList1);
		results = jsonObjList;

		if(results!=null){
			System.out.print("results2:"+results.toString());
		}



		return Action.SUCCESS;

	}





	public String shenzhenshibie() {
			System.out.print("shibie2222222222");
			String content = "";
		String plateid = "";
		String siteid = "";
			HttpServletRequest request;
        JSONObject jsonObject = null;
		try {
          request = ServletActionContext.getRequest();
            InputStream in = request.getInputStream();
            int size = request.getContentLength();
            //	 logger.info("调试信息");
            if (in != null && size > 0) {
                int readCount = 0; // 已经成功读取的字节的个数
                int nRead = 0;
                byte[] buf = new byte[size];
                try {
                    while (readCount < size) {
                        nRead = in.read(buf, readCount, size - readCount);
                        if (nRead == -1) // 到末尾
                        {
                            break;
                        }
                        readCount += nRead;
                    }
                    //    logger.info("调试信息");
                    jsonObject = JSONObject.fromObject(new String(buf,"UTF-8"));
                   // logger.info("调试信息");
                } catch (IOException e) {
                    logger.error("操作异常", e);
                }
            }
        }catch(Exception e){

            }

		if(null == jsonObject || jsonObject.isEmpty()){
			return Action.SUCCESS;
		}

		boolean bl = false;
		int Open = 0;
		String info = "no";//ok是开门
		//判断是摄像头轮询数据还是直接请求数据
		JSONObject channelNum = jsonObject.getJSONObject("channel_num");
		if(channelNum.isEmpty() && channelNum.equals("1")){

                logger.info("channelNum: {}", channelNum);

			//这个值表示是摄像头通过轮询调用服务器
			//获取摄像头id
			String cameraId = jsonObject.getString("serialno");
			//channel_num等于1表示是相机轮询问过来的数据
			if (cameraId != null && !cameraId.equals("")) {
				logger.info("cameraId: {}", cameraId);
				// 如果没有进入的数据先查询下有没有最新未结算的记录，如果有的话，不给开门提示有未结算的，先处理未结算的
				String hqlout = "from CarManage where equipmentNumber = ? and status = ? and open = ? and outdate is not null order by outdate desc";
				List<BaseBean> listcmout = baseBeanService
						.getListBeanByHqlAndParams(
								hqlout,
								new Object[] { cameraId,
										"0", "0" });
				CarManage cmout = null;

				if (listcmout.size() > 0) {
					cmout = (CarManage) listcmout
							.get(0);

				}
				if (cmout != null&&cmout
						.getChargeState()!=null
						&& !"00".equals(cmout
						.getChargeState())) { // 说明上一setOpen("1")个离开的付款了

					// 等于0的时候没太过杆
					cmout.setOpen("1");//222
					baseBeanService.update(cmout);
					logger.info("抬杆");

					Open = 1;// 抬杆
					info = "ok";
					JSONObject jsonObjList = new JSONObject();

					JSONObject jsonObjList1 = new JSONObject();

					jsonObjList1.accumulate("info",
							info);//开闸信息


					jsonObjList.accumulate("Response_AlarmInfoPlate",
							jsonObjList1);
					results = jsonObjList;
					if (results != null) {
						System.out.print(results
								.toString());
					}
					return Action.SUCCESS;

				} else {
					return Action.SUCCESS;
				}
			}
		}
		try {

			//有值才说明识别到数据病传输过来了
			JSONObject chePaiAllData = null;
			if(jsonObject!=null && jsonObject.size()>0 && jsonObject.toString().indexOf("AlarmInfoPlate") != -1){
				chePaiAllData = jsonObject.getJSONObject("AlarmInfoPlate");
				if(chePaiAllData.toString().indexOf("serialno")==-1){
					return Action.SUCCESS;
				}
			}

			//获取摄像头id
			String equipmentNumber = chePaiAllData.getString("serialno");
			JSONObject jsonresult = chePaiAllData.getJSONObject("result");

			JSONObject jsonplate  =  jsonresult.getJSONObject("PlateResult");

			String channel= chePaiAllData.getString("channel");

			//获取车牌号
			String number= jsonplate.getString("license");
			 plateid = jsonplate.getString("plateid");

//			 String number = "A1232";
//			 String equipmentNumber = request.getParameter("equipmentNumber");
			logger.info("number: {}", number);
		//	logger.info("调试信息");
			logger.info("equipmentNumber: {}", equipmentNumber);
			if(number!=null&&!number.equals("")) {
				JSONObject timeStamp = jsonplate.getJSONObject("timeStamp").getJSONObject("Timeval");
				String date1 = timeStamp.getString("usec");// 识别时间,毫秒值
				logger.info("date1: {}", date1);
              //  String date = Utilities.getDateString(new Date(Long.valueOf(date1)),"yyyy-MM-dd HH:mm:ss");
				String panoramabase64 = "";
				String plateNumberbase64="";
				try {
					panoramabase64 = jsonplate.getString("imageFile");//全景图
					plateNumberbase64 = jsonplate.getString("imageFragmentFile");//车牌图
				}catch (Exception e){
                   logger.info("没有图片");
				}
				String numberType = jsonplate.getString("type");//车牌类型

				String date = Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss");
				logger.info("date: {}", date);

		//		logger.info("调试信息");
				// 通过设备id查询到公司id以及人员id
				StringBuilder sb = new StringBuilder();
				sb.append("select t from ");
				sb.append("EquipmentInformation e,VenueInformation v,TEshopCusCom t ");
				sb.append("where e.siteId=v.siteId and v.companyId=t.companyId ");
				sb.append("and e.equipmentNumber=? ");


				String hql11 = "from EquipmentInformation where equipmentNumber = ?";
				EquipmentInformation c = (EquipmentInformation)baseBeanService.getBeanByHqlAndParams(hql11,new Object[]{equipmentNumber});
				if(c!=null){

					channel = c.getChannel();
					siteid = c.getSiteId();

				}

				TEshopCusCom cuscom = (TEshopCusCom) baseBeanService
						.getBeanByHqlAndParams(sb.toString(),
								new Object[]{equipmentNumber});
				if (cuscom == null) {
					logger.error("录入车辆失败请根据下面设备编号查询账号是否存在--------设备编号:" + equipmentNumber);
					Open = 0;
					info = "no";
				} else {
					// 图片存储路径
					String photopath = "upload_files\\theLicenseInformation\\"
							+ cuscom.getCompanyId() + "\\" + cuscom.getStaffid();
					// 重命名
					String upName1 = UUID.randomUUID().toString()
							+ System.currentTimeMillis() + ".jpeg";
					String upName2 = UUID.randomUUID().toString()
							+ System.currentTimeMillis() + ".jpeg";
					// 生成全景jpeg图片

					if(!"".equals(panoramabase64)) {
						GenerateImage(panoramabase64, photopath, upName1);
						// 生成车牌jpeg图片
						GenerateImage(plateNumberbase64, photopath, upName2);
					}
                      logger.info("channel: {}", channel);

					if (channel.equals("1")) {// 进入停车场
						logger.info("进入停车场");
                         if(indateMap!=null){
							 indateMap.put(siteid+number,date);//记录进入最新识别时间
						 }
						Date start = Utilities.getDateFromString(date,"yyyy-MM-dd HH:mm:ss");//进来时间
						    if(outdateMap!=null) {
								String dateout = outdateMap.get(siteid+number);//最后离开识别时间
								if(dateout!=null) {
									logger.info("dateout: {}", dateout);
									Date end = Utilities.getDateFromString(dateout, "yyyy-MM-dd HH:mm:ss");
									long diff = start.getTime() - end.getTime();//最新进入时间-最后离开识别时间
									logger.info("diff1: {}", diff);
									long minute = diff /1000;
									logger.info("minute: {}", minute);
									if (minute <= 60) {//
										logger.info("小于等于1分钟");
										Open = 0;//出去的时间和进入的时间小于一分钟可能是车尾识别到了，此次识别作废
										info = "no";
										logger.info("车尾识别到了进去的摄像头");
										return Action.SUCCESS;
									}
								}
							}

						//进入时候判断停车场有无车位了
						Map<String, String> parking = cmService.searchParkingCode(equipmentNumber);
						String parkingCode = "";
						String parksId = "";
						if (parking != null && "suc".equals(parking.get("result"))) {
							parkingCode = parking.get("parkingCode");
							parksId = parking.get("parksId");

						}
						logger.info("parkingCode: {}", parkingCode);
						if (parkingCode == null
								|| parkingCode.equals("")) {
							Open = 0;// 不开无停车位
							info = "no";

						 content = cmService.pushJG(cuscom.getCompanyId(), "in", parkingCode, null, equipmentNumber,"");

						} else {

							Map<String,Object> mp = cmService.checkCarRecord(number,date,photopath,upName1,upName2,equipmentNumber,cuscom.getCompanyId(), channel, numberType,parksId);
							Open = (int)mp.get("Open");

							String tip = String.valueOf(mp.get("tip"));
							String carmID = String.valueOf(mp.get("carmID"));
							String  ests = "0";



							 if(dateMap!=null){
							 //	logger.info("调试信息");
									String dates = dateMap.get(carmID);
								 logger.info("dates: {}", dates);
                                    if(dates!=null&&!dates.equals("")){
										logger.info("dates: {}", dates);
										Date end1 = Utilities.getDateFromString(dates,"yyyy-MM-dd HH:mm:ss");
										long diff = start.getTime()-end1.getTime();
										long second = diff/1000;
										if(second<=15){
											ests = "1";
											logger.info("second: {}", second);
										}else{
											//大于15
											dateMap.put(carmID,date);
											logger.info("大于15");
										}

									}else{
										logger.info("第一次");
										logger.info("carmID: {}", carmID);
										logger.info("调试信息");
										dateMap.put(carmID,date);
									//	logger.info("调试信息");

									}

								}



							if(Open==1){
								info = "ok";
								dateMap.remove(carmID);
							}else{
								info = "no";
							}
							if("0".equals(ests)) {
								cmService.pushJG(cuscom.getCompanyId(), "in", parkingCode, carmID, equipmentNumber, tip);
							}
						}

					} else if (channel.equals("0")) {// 离开停车场
						logger.info("离开停车场");
						if(outdateMap!=null){
							outdateMap.put(siteid+number,date);//记录离开最新识别时间
						}
						String hql = "from CarManage c where c.carNumber=? and c.status=? and c.indate is not null and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?) order by c.indate desc";
						List<BaseBean> listcm = baseBeanService.getListBeanByHqlAndParams(hql,
								new Object[]{number, "1",siteid});
						if(listcm.size()>0){
							cm = (CarManage) listcm.get(0);
						}


						if (cm != null) {
							logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);
                                if(indateMap!=null){
									Date start = Utilities.getDateFromString(date,"yyyy-MM-dd HH:mm:ss");//当前离开识别时间
									String datein = indateMap.get(siteid+number);//最后进来识别时间
									logger.info("datein: {}", datein);
									if(datein!=null) {

										Date in = Utilities.getDateFromString(datein, "yyyy-MM-dd HH:mm:ss");
										long diff = start.getTime() - in.getTime();
										logger.info("diff1: {}", diff);
										long minute = diff /1000;
										logger.info("minute: {}", minute);
										if (minute <= 60) {//算是同一次出去
											logger.info("小于等于1分钟");
											Open = 0;//说明可能是进去的时候车尾识别到了出去的摄像头，此次识别作废
											info = "no";
											logger.info("车尾识别到了出去的摄像头");
											return Action.SUCCESS;
										}
									}
								}

								cmService.updateManageCar(cm, number, date, equipmentNumber, photopath, upName1, upName2, channel);
								logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);
								String state = cmService.programCalls(cm.getCarmID());
								if ("01".equals(state)) {
									Open = 1;//开
									info = "ok";
									logger.info("0元和套餐直接成功");
								} else {
									Open = 0;//不开
									info = "no";
									logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);

								}
								cmService.pushJG(cuscom.getCompanyId(), "out", null, cm.getCarmID(), equipmentNumber,"");




						} else {
							logger.info("离开时二次识别车辆");
							//如果没有进入的数据先查询下有没有最新未结算的记录，如果有的话，不给开门提示有未结算的，先处理未结算的

							String hqlout = "from CarManage c where c.carNumber=? and c.status=? and c.outdate is not null and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?) order by c.outdate desc";
							List<BaseBean> listcmout = baseBeanService.getListBeanByHqlAndParams(hqlout,
									new Object[]{number, "0",siteid});
							CarManage cmout = null;
							String estss = "0";
							if(listcmout.size()>0) {
								cmout = (CarManage) listcmout.get(0);
								if(indateMap!=null){
									Date startcc = Utilities.getDateFromString(date,"yyyy-MM-dd HH:mm:ss");//当前离开识别时间
									String datein = indateMap.get(siteid+number);//最后进来识别时间
									logger.info("datein: {}", datein);
									if(datein!=null) {
										Date incc = Utilities.getDateFromString(datein, "yyyy-MM-dd HH:mm:ss");

										long diffcc = startcc.getTime() - incc.getTime();
										logger.info("diff1cc: {}", diffcc);
										long minutecc = diffcc /1000;
										logger.info("minutecc: {}", minutecc);

										if (minutecc <= 60) {//算是同一次出去
											logger.info("minutecc小于等于1分钟");
											Open = 0;//说明可能是进去的时候车尾识别到了出去的摄像头，此次识别作废
											info = "no";
											logger.info("车尾识别到了出去的摄像头");
											return Action.SUCCESS;
										}
									}

							}
								Date startc = Utilities.getDateFromString(date,"yyy-MM-dd HH:mm:ss");
								if(dateMap!=null){
									String datesc = dateMap.get(cmout.getCarmID());
									if(datesc!=null&&!datesc.equals("")){

										Date endc = Utilities.getDateFromString(datesc,"yyyy-MM-dd HH:mm:ss");
										long diffc = startc.getTime()-endc.getTime();
										long secondc = diffc/1000;
										if(secondc<=15){
											estss = "1";
										}else{
											//大于15
											dateMap.put(cmout.getCarmID(),date);
										}

									}else{
										dateMap.put(cmout.getCarmID(),date);
									}



								}
							}


							if(cmout!=null&&"00".equals(cmout.getChargeState())){
								logger.info("之前有未结算的提示结算");
								//说明之前有未结算的提示结算
								Open = 0;//不开
								info = "no";
								if("0".equals(estss)) {
									cmService.pushJG(cuscom.getCompanyId(), "out", null, cmout.getCarmID(), equipmentNumber, "");
								}


							}else{
								logger.info("没有未结算");
								if(cmout!=null&&!"00".equals(cmout.getChargeState())){  //说明上一个离开的付款了，现在没有进入记录，查询上一个离开时间，如果在20分钟内算是同一次出去
									logger.info("查询上次结算记录");
									Date start  = new Date();
									Date  out = cmout.getOutdate();

									long diff = start.getTime()-out.getTime();
									logger.info("diff: {}", diff);
									long minute = diff/60/1000;
									logger.info("minute: {}", minute);
									if(minute<20){//算是同一次出去
										logger.info("小于20分钟");
										Open = 1;//开门
										info = "ok";
										if("0".equals(estss)) {
											cmService.pushJG(cuscom.getCompanyId(), "out", null, cmout.getCarmID(), equipmentNumber, "");
                                            dateMap.remove(cmout.getCarmID());
										}
									}else{
										logger.info("大于20分钟");
										Open = 0;//不开门
										info = "no";
									}
									logger.info("开门状态：{}", Open);
								}

								if(Open==0){//异常数据处理，没有进入记录
									//离开停车场查询不到进入数据则添加一个出口的数据以便于查询
									//进入时候判断停车场有无车位了
									Map<String,String> parking = cmService.searchParkingCode(equipmentNumber);

									String parksId = "";
									if(parking!=null&&"suc".equals(parking.get("result"))){

										parksId = parking.get("parksId");

									}
									String carmID = cmService.addErrorOut(number, date, equipmentNumber, photopath, upName1, upName2,cuscom.getCompanyId(),channel,numberType,parksId);
									logger.info("异常添加一个出去记录");
									String state = cmService.programCalls(carmID);
									if ("01".equals(state)) {
										Open = 1;//开
										info = "ok";
										logger.info("0元和套餐直接成功");
									} else {
										Open = 0;//不开
										info = "no";
										logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);
									}
									cmService.pushJG(cuscom.getCompanyId(), "out", null, carmID, equipmentNumber,"");


								}

							}



						}
					}
				}
			}else{
				Open = 0;
				info = "no";
			}
			bl = true;




		} catch (Exception e) {
			logger.error("操作异常", e);
			Open = 0;
			info = "no";
			logger.info("报错2");
		}
		logger.info("Open: {}", Open);


			JSONObject jsonObjList = new JSONObject();

			JSONObject jsonObjList1 = new JSONObject();

			jsonObjList1.accumulate("info",
					info);
		jsonObjList1.accumulate("plateid",
				plateid);
		jsonObjList1.accumulate("content",
				content.getBytes(StandardCharsets.UTF_8));
			jsonObjList1.accumulate("is_pay",
					"true");


		jsonObjList.accumulate("Response_AlarmInfoPlate",
				jsonObjList1);
		results = jsonObjList;

		if(results!=null){
			System.out.print("results:"+results.toString());
		}

		return Action.SUCCESS;
	}






	/**
	 *
	 *新版车辆识别
	 * @return
	 */
	public String shibiecar() {
		System.out.print("进入shibiecar");
//		"gwid": 778855663344,
//				"ctime": 1641138473,
//				"carno": "川A5575P",
//				"imageData":"base64"
//
		JSONObject json = null;
		try {
			HttpServletRequest request1 = ServletActionContext.getRequest();

			InputStream in=request1.getInputStream();
			int size=request1.getContentLength();
			logger.info("调试信息");
			if (in != null && size > 0) {
				int readCount = 0; // 已经成功读取的字节的个数
				int nRead = 0;
				byte[] buf = new byte[size];
				try {
					while (readCount < size) {
						nRead = in.read(buf, readCount, size - readCount);
						if (nRead == -1) // 到末尾
						{
							break;
						}
						readCount += nRead;
					}

					try {
						json = JSONObject.fromObject(new String(buf,"UTF-8"));
					} catch (Exception e) {


					}
				} catch (Exception e) {

				}

			}
		}catch(IOException e){

		}
//		String  equipmentNumber = request1.getParameter("gwid");//摄像头编号
//		String ctime = request1.getParameter("ctime");//时间戳
//		String number = request1.getParameter("carno");//车牌号
		//String panoramabase64 = request1.getParameter("imageData");//全景
		//	logger.info("调试信息");

		String  equipmentNumber = json.getString("gwid");
		String ctime = json.getString("ctime");
		String number = json.getString("carno");//车牌号
		logger.info("值：{}", number);
//		try {
//			number = URLDecoder.decode(number, "utf-8");
//			logger.info("值：{}", number);
//		}catch (UnsupportedEncodingException e){
//			logger.error("操作异常", e);
//		}

		String panoramabase64 = json.getString("imageData");
		String plateNumberbase64 = json.getString("Plateimg");

		logger.info("值：{}", equipmentNumber);
		logger.info("值：{}", ctime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date(Long.valueOf(ctime+"000")));
		logger.info("值：{}", date);


		String hqlc = "from EquipmentInformation where equipmentNumber = ?";
		EquipmentInformation ei = (EquipmentInformation)baseBeanService.getBeanByHqlAndParams(hqlc,new Object[]{equipmentNumber});
		String channel = "";
		if(ei!=null) {
			channel = ei.getChannel();
		}else{
			logger.info("未录入设备序号");
		}

		boolean bl = false;
		int Open = 0;



		try {

			if(number!=null&&!number.equals("")) {

				String numberType = "";//车牌类型
				String triggerType = "";//车牌类型


				// 通过设备id查询到公司id以及人员id
				StringBuilder sb = new StringBuilder();
				sb.append("select t from ");
				sb.append("EquipmentInformation e,VenueInformation v,TEshopCusCom t ");
				sb.append("where e.siteId=v.siteId and v.companyId=t.companyId ");
				sb.append("and e.equipmentNumber=? ");
				TEshopCusCom cuscom = (TEshopCusCom) baseBeanService
						.getBeanByHqlAndParams(sb.toString(),
								new Object[]{equipmentNumber});
				if (cuscom == null) {
					logger.info("录入车辆失败请根据下面设备编号查询账号是否存在--------设备编号:: {}", equipmentNumber);
					Open = 0;
				} else {
					// 图片存储路径
					String photopath = "upload_files\\theLicenseInformation\\"
							+ cuscom.getCompanyId() + "\\" + cuscom.getStaffid();
					// 重命名
					String upName1 = UUID.randomUUID().toString()
							+ System.currentTimeMillis() + ".jpeg";
					String upName2 = UUID.randomUUID().toString()
							+ System.currentTimeMillis() + ".jpeg";


					if (channel.equals("1")) {// 进入停车场
						// 生成全景jpeg图片
						GenerateImage(panoramabase64, photopath, upName1);
						logger.info("调试信息");
						// 生成车牌jpeg图片
						GenerateImage(plateNumberbase64, photopath, upName2);

						//进入时候判断停车场有无车位了
						Map<String,String> parking = cmService.searchParkingCode(equipmentNumber);
						String parkingCode = "";
						String parksId = "";
						if(parking!=null&&"suc".equals(parking.get("result"))){
							parkingCode = parking.get("parkingCode");
							parksId = parking.get("parksId");

						}

						if (parkingCode == null
								|| parkingCode.equals("")) {
							Open = 0;// 不开无停车位
							cmService.pushJG(cuscom.getCompanyId(), "in", parkingCode, null, equipmentNumber,"");

						} else {
							String carmID = cmService.addManageCar(number,date,equipmentNumber,photopath,upName1,upName2,cuscom.getCompanyId(),channel,numberType,parksId);
							Open = 1;// 开门有停车位
							cmService.pushJG(cuscom.getCompanyId(), "in", parkingCode, carmID, equipmentNumber,"");

						}

					} else if (channel.equals("0")) {// 离开停车场
						logger.info("[车辆检测] 车辆离开，车牌号：{}", number);
						String hql = "from CarManage where carNumber=? and status=? and indate is not null order by indate desc";
						List<BaseBean> listcm = baseBeanService.getListBeanByHqlAndParams(hql,
								new Object[]{number, "1"});
						if(listcm.size()>0){
							cm = (CarManage) listcm.get(0);
						}


						if (cm != null) {
							// 生成全景jpeg图片
							GenerateImage(panoramabase64, photopath, upName1);
							logger.info("调试信息");
							// 生成车牌jpeg图片
							GenerateImage(plateNumberbase64, photopath, upName2);

							logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);
							cmService.updateManageCar(cm,number,date,equipmentNumber,photopath,upName1,upName2,channel);
							logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);
							String state = cmService.programCalls(cm.getCarmID());
							if ("01".equals(state)) {
								Open = 1;//开
								logger.info("0元和套餐直接成功");
							} else {
								Open = 0;//不开

								logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);

							}
							cmService.pushJG(cuscom.getCompanyId(), "out", null, cm.getCarmID(), equipmentNumber,"");


						} else {
							logger.info("离开时二次识别车辆");
							//如果没有进入的数据先查询下有没有最新未结算的记录，如果有的话，不给开门提示有未结算的，先处理未结算的
							String hqlout = "from CarManage where carNumber=? and status=? and outdate is not null order by outdate desc";
							List<BaseBean> listcmout = baseBeanService.getListBeanByHqlAndParams(hqlout,
									new Object[]{number, "0"});
							CarManage cmout = null;

							if(listcmout.size()>0){
								cmout = (CarManage) listcmout.get(0);

							}
							if(cmout!=null&&"00".equals(cmout.getChargeState())){
								logger.info("之前有未结算的提示结算");
								//说明之前有未结算的提示结算
								Open = 0;//不开

								cmService.pushJG(cuscom.getCompanyId(), "out", null, cmout.getCarmID(), equipmentNumber,"");

							}else{
								logger.info("没有未结算");
								if(cmout!=null&&!"00".equals(cmout.getChargeState())){  //说明上一个离开的付款了，现在没有进入记录，查询上一个离开时间，如果在20分钟内算是同一次出去
									logger.info("查询上次结算记录");
									Date start  = new Date();
									Date  out = cmout.getOutdate();

									long diff = start.getTime()-out.getTime();
									logger.info("diff: {}", diff);
									long minute = diff/60/1000;
									logger.info("minute: {}", minute);
									if(minute<20){//算是同一次出去
										logger.info("小于20分钟");
										Open = 1;//开门
										// 等于0的时候没太过杆
										cmout.setOpen("1");//222
										baseBeanService.update(cmout);
										logger.info("抬杆");

									}else{
										logger.info("大于20分钟");
										Open = 0;//不开门

									}
									logger.info("开门状态：{}", Open);
								}

								if(Open==0){//异常数据处理，没有进入记录
									//离开停车场查询不到进入数据则添加一个出口的数据以便于查询
									//进入时候判断停车场有无车位了
									Map<String,String> parking = cmService.searchParkingCode(equipmentNumber);

									String parksId = "";
									if(parking!=null&&"suc".equals(parking.get("result"))){

										parksId = parking.get("parksId");

									}
									String carmID = cmService.addErrorOut(number, date, equipmentNumber, photopath, upName1, upName2,cuscom.getCompanyId(),channel,numberType,parksId);
									logger.info("异常添加一个出去记录");
									String state = cmService.programCalls(carmID);
									if ("01".equals(state)) {
										Open = 1;//开
										logger.info("0元和套餐直接成功");
										// 等于0的时候没太过杆
										cmout.setOpen("1");//222
										baseBeanService.update(cmout);
										logger.info("抬杆");
									} else {
										Open = 0;//不开

										logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);

									}
									cmService.pushJG(cuscom.getCompanyId(), "out", null, carmID, equipmentNumber,"");

								}

							}



						}
					}
				}
			}else{
				Open = 3;
			}
			bl = true;
		} catch (Exception e) {
			logger.error("操作异常", e);
		}



		logger.info("Open: {}", Open);
		JSONObject jsonObjList1 = new JSONObject();

		jsonObjList1.accumulate("Open",Open);
		results = jsonObjList1;
		if(results!=null){
			System.out.print(results.toString());
		}

		return Action.SUCCESS;
	}


	public String chepai1(BufferedReader br) {
		String inputLine;
		String str = "";
		try {
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
			br.close();
		} catch (IOException e) {
			logger.info("IOException: : {}", e);
		}
		logger.info("str:: {}", str);
		return str;
	}

	public String chepai() {
		ServletRequest request = ServletActionContext.getRequest();
		String date = request.getParameter("date");
		String number = request.getParameter("number");

		JoinFans jf = new JoinFans();
		jf.setJfID(serverService.getServerID("chepai"));
		jf.setSource("车牌识别");
		jf.setZaccount(number);
		jf.setFaccount(date);

		baseBeanService.save(jf);

		return Action.SUCCESS;
	}

	public  String sbsb(){
		logger.info("11111111111");

		return null;
	}
	/**
	 * 获得车牌识别的Json数据 *
	 *
	 * @param request
	 *            作用域
	 * @return
	 */
	private static String myRequestWrapper(HttpServletRequest request)
			throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}
		return stringBuilder.toString();
	}

	/**
	 * base64字符串转化成图片 *
	 *
	 * @param imgStr
	 *            64位码
	 * @param photopath
	 *            包路径
	 * @param upName
	 *            图片名称
	 * @return
	 */
	private static boolean GenerateImage(String imgStr, String photopath,
										 String upName) {



		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String dir = path + photopath;
		File fileLocation = new File(dir);
		// 判断上传路径是否存在，如果不存在就创建
		if (!fileLocation.exists()) {
			boolean isCreated = fileLocation.mkdirs();
			if (!isCreated) {
				return false;
			}
		}
		// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) {// 图像数据为空
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			OutputStream out = new FileOutputStream(dir +"\\"+ upName);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取登录人的公司id *
	 * @return
	 */
	private CAccount AccessToTheCompanyId(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		caccount = (CAccount) session.getAttribute("account");

		return caccount;
	}


	/*-------------------------------------- 考场计时 -----------------------------------*/

	/**
	 * 查询考场list *
	 * @return
	 */
	public String examinationRoomList(){

		CAccount account = AccessToTheCompanyId();

		if(account==null)
		{
			return "nologin";
		}

		pageForm = cmService.examinationRoomList(account.getCompanyID(),numberOrName,pageForm,pageNumber);

		return "examinationRoomList";
	}

	/**
	 * ajax查询考场列表 *
	 *
	 * @return
	 */
	public String ajaxExaminationRoomList(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		pageForm = cmService.examinationRoomList(account.getCompanyID(),numberOrName,pageForm,pageNumber);

		map.put("pageForm", pageForm);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();

		return "success";
	}



	/**
	 * 查询场地详情 *
	 *
	 * @return
	 */
	public String erDetails(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}
		map = cmService.erDetails(exRoom.getErId(),siteJudge,account.getCompanyID());
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 添加或修改考场详情 *
	 *
	 * @return
	 */
	public String addOrUpdateErDetails(){

		boolean b = cmService.addOrUpdateErDetails(exRoom);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 删除考场 *
	 *
	 * @return
	 */
	public String delExoom(){
		boolean b = cmService.delExoom(exRoom.getErId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 实时效验考场编号 *
	 *
	 * @return
	 */
	public String verificationerRoom(){

		boolean b = cmService.verificationerRoom(numberOrName);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 启用考场 *
	 *
	 * @return
	 */
	public String enableTheTest() {
		boolean b = cmService.enableTheTest(exRoom.getErId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 查询审核人账号 *
	 *
	 * @return
	 */
	public String queryAccount() {
		Object object = baseBeanService.getObjectBySqlAndParams("select account from t_eshop_customer where staffid = ?",new Object[]{caccount.getStaffID()});
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Account", object);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 查询考场收费标准列表 *
	 *
	 * @return
	 */
	public String chargeList() {

		CAccount account = AccessToTheCompanyId();

		if(account==null)
		{
			return "nologin";
		}

		List<String> list = new ArrayList<String>();

		StringBuilder sb = new StringBuilder();
		sb.append("select e.ercid,e.chargenumber,sp.RE_PRICE,e.erid,r.erNumber, ");
		sb.append("r.erName,e.staffID,e.staffName,e.startusing ");
		sb.append("from dt_ExaminationRoomCharge e,dt_productpackaging p,DT_PRO_SETUP sp,dt_ExaminationRoom r ");
		sb.append("where p.ppid=sp.ppid and sp.COM_ID = p.companyid and e.companyid = ? and (e.startusing = ? or e.startusing = ?) and r.status = ? ");
		sb.append("and e.goodsid = p.goodsid and e.erId = r.erId and p.type like ? ");
		list.add(account.getCompanyID());
		list.add("00");
		list.add("01");
		list.add("00");
		list.add("%金币计时%");
		if (exRoom!=null && exRoom.getErNumber()!=null && !exRoom.getErNumber().equals(""))
		{
			sb.append("and r.erNumber like ? ");
			list.add("%"+exRoom.getErNumber()+"%");
		}
		sb.append("order by p.packagingdate desc");

		pageForm = baseBeanService.getPageFormBySQL(
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, sb.toString(),
				"select count(*) from (" + sb.toString() + ")",list.toArray());
		return "chargeList";
	}

	/**
	 * 查询公司考场编号以及员工 *
	 *
	 * @return
	 */
	public String erNumberPersonnel(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		List<BaseBean> number = cmService.queryerNumber(account.getCompanyID(),null);

		List<BaseBean> list = cmService.queryStaff(account.getCompanyID(),"");
		//代理类别查询
		List<BaseBean> dlList = baseBeanService.getListBeanByHqlAndParams
				("from ProductPackaging where type=? and goodsname<>? order by sorting",
						new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
		map.put("dlList", dlList);
		map.put("number", number);
		map.put("list", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 查询考场收费标准详情 *
	 *
	 * @return
	 */
	public String testStandard(){
		CAccount account = AccessToTheCompanyId();

		Map<String, Object> map = new HashMap<String, Object>();
		if(account==null)
		{
			map.put("nologin", true);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}

		erCharge = cmService.testStandard(erCharge.getErcID());

		ppk = (ProductPackaging) baseBeanService.getBeanByHqlAndParams("from ProductPackaging where goodsID = ?",new Object[]{erCharge!=null?erCharge.getGoodsID():""});

		String hql = "from ProSetup where ppid = ? and comId = ?";
		ProSetup psp = (ProSetup)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{ppk.getPpID(),ppk.getCompanyID()});

		List<BaseBean> arrlist=baseBeanService
				.getListBeanByHqlAndParams(
						"from AttriProduction where goodsid=? and type='2' order by sort",
						new Object[]{erCharge!=null?erCharge.getGoodsID():""});
		List<BaseBean> number = cmService.queryerNumber(account.getCompanyID(),exRoom.getErNumber());

		List<BaseBean> list = cmService.queryStaff(account.getCompanyID(),"");
		//佣金查询
		List<BaseBean> prtset=cmService.queryProSetup(erCharge.getErcID());
		//代理类别查询
		List<BaseBean> dlList = baseBeanService.getListBeanBySqlAndParams
				(" select p.ppid,p.goodsid,p.goodsname from dt_ProductPackaging p where p.type=? and p.goodsname<>? order by sorting",
						new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
		List<BaseBean> brokerage=cmService.brokerage(erCharge.getErcID());
		map.put("dlList", dlList);
		map.put("erCharge", erCharge);
		map.put("ppk", ppk);
		map.put("psp", psp);
		map.put("number", number);
		map.put("list", list);
		map.put("prtset",prtset);
		map.put("brokerage",brokerage);
		map.put("arrlist",arrlist);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 添加修改收费标准 *
	 *
	 * @return
	 */
	public String addOrUpdateExRoomCharge() {
		boolean b = cmService.addOrUpdateExRoomCharge(erCharge,ppk, photo,photoFileName,pssMap,brokerage);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}
	/**
	 * 删除收费标准 *
	 *
	 * @return
	 */
	public String delErCharge() {

		boolean b = cmService.delErCharge(erCharge.getErcID());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 * 启用考场收费标准 *
	 *
	 * @return
	 */
	public String examIsEnabled() {
		boolean b = cmService.examIsEnabled(erCharge.getErcID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 *  考场记录*
	 *
	 * @return
	 */
	public String testRecordList() {
		CAccount account = AccessToTheCompanyId();

		if(account==null)
		{
			return "nologin";
		}

		pageForm = cmService.testRecordList(account.getCompanyID(),tcc.getAccount(),bfmation.getSignInState(),pageForm,pageNumber);

		return "testRecordList";
	}

	/**
	 * 删除考场记录 *
	 *
	 * @return
	 */
	public String delBookingInformation(){
		boolean b = cmService.delBookingInformation(bfmation.getBifId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boolean", b);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 * 查看考场记录详情 *
	 *
	 * @return
	 */
	public String viewExaminationRecords(){

		object = cmService.testRecord(bfmation.getBifId());

		return "viewExaminationRecords";
	}

	/**
	 * 打印考场记录详情 *
	 *
	 * @return
	 */
	public String printingTestRecord(){

		object = cmService.testRecord(bfmation.getBifId());

		return "printingTestRecord";
	}

////////////////////////////////////////停车位管理////////////////////////////////////////////////////////////////////


	/**
	 * 停车位管理 *
	 *
	 * @return
	 */
	public String parkingSpaceList() {
		CAccount account = AccessToTheCompanyId();
		if (account == null) {
			return "nologin";
		}
		if (search != null && search.equals("search")) {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();

			parkingSpace = (ParkingSpace) session.get("parkingSpace");
		}
		pageForm = cmService.parkingSpaceList(parkingSpace,
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, account.getCompanyID());

		return "parkingList";
	}
	/**
	 * 添加修改停车位
	 * @return
	 */
	public String addUpdateSpace(){
		CAccount account = AccessToTheCompanyId();
		if(account==null)
		{
			return "nologin";
		}

		cmService.addUpdateSpace(parkingSpace,account.getCompanyID(),account.getStaffID());

		return "success";

	}

	/**
	 * 删除停车位
	 *
	 * @return
	 */
	public String deleteSpace(){

		cmService.deleteSpace(parkingSpace.getParksId());

		return "success";

	}
	/**
	 *
	 * 更新状态
	 * @return
	 */
	public String updateStatus(){

		cmService.updateStatus(parkingSpace.getParksId(),parkingSpace.getStatus());
		return "success";
	}

	/**
	 *
	 * 所在场地
	 * @return
	 */
	public String getSiteList(){
		CAccount account = AccessToTheCompanyId();
		if (account == null) {
			return "nologin";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String accessCcomID = request.getParameter("accessCcomID");
		String companyId = account.getCompanyID();
		if(accessCcomID!=null&&!accessCcomID.equals("")){

			CcomCom ccom = (CcomCom)baseBeanService.getBeanByHqlAndParams("from CcomCom where ccompanyId = ?", new Object[]{accessCcomID});
			companyId  = ccom.getComanyId();
		}
		List<BaseBean> list = cmService.getSiteList(companyId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sitelist", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 *
	 * 所在场地
	 * @return
	 */
	public String getEquipList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String siteId = request.getParameter("siteId");

		List<BaseBean> list = cmService.getEquipList(
				siteId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("equiplist", list);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}


	/**
	 *
	 * 查询
	 * @return
	 */
	public  String toSearchSpace(){
		Map<String, Object> session = ActionContext.getContext().getSession();

		session.put("parkingSpace", parkingSpace);

		return parkingSpaceList();
	}
	/**
	 *
	 * 批量新增停车位
	 * @return
	 */
	public String batAddSpace(){
		CAccount account = AccessToTheCompanyId();
		if (account == null) {
			return "nologin";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String startNum = request.getParameter("startNum");
		String endNum = request.getParameter("endNum");
		cmService.batAddSpace(parkingSpace,account.getCompanyID(),account.getStaffID(),startNum,endNum);

		return "success";

	}


	/**
	 *
	 * 判断编号是否重复
	 * @return
	 */
	public  String  checkParkingNum(){
		String result = cmService.checkParkingNum(parkingSpace.getParkingCode(),parkingSpace.getParksId(),parkingSpace.getSiteId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result",result);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/////////////////////////////////////////////会员套餐/////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 *
	 * 查询
	 * @return
	 */
	public  String toSearchFeeVIP(){
		Map<String, Object> session = ActionContext.getContext().getSession();

		session.put("timingCharging", timingCharging);

		return getVipFeeList();
	}



	/**
	 * 收费VIP套餐*
	 *
	 * @return
	 */
	public String getVipFeeList() {
		CAccount account = AccessToTheCompanyId();
		if (account == null) {
			return "nologin";
		}
		if (search != null && search.equals("search")) {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();

			timingCharging = (TimingCharging) session.get("timingCharging");
		}
		pageForm = cmService.getVipFeeList(timingCharging,
				null != pageForm ? pageForm.getPageNumber() : 1,
				pageNumber == 0 ? 10 : pageNumber, account.getCompanyID());

		return "vipList";
	}


	/**
	 * 添加VIP套餐
	 * @return
	 */
	public String addUpdateVIP(){
		CAccount account = AccessToTheCompanyId();
		if(account==null)
		{
			return "nologin";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		timingCharging.setStartDate(Utilities.getDateFromString(start,"yyyy-MM-dd HH:mm:ss"));
		timingCharging.setEndDate(Utilities.getDateFromString(end,"yyyy-MM-dd HH:mm:ss"));
		cmService.addUpdateVIP(timingCharging);

		return "success";

	}

	/**
	 * 删除VIP套餐
	 *
	 * @return
	 */
	public String deleteVIP(){

		cmService.deleteVIP(timingCharging.getTcId());

		return "success";

	}

	/**
	 *
	 * 判断编号是否重复
	 * @return
	 */
	public  String  checkcarNum(){
		String result = cmService.checkcarNum(timingCharging.getCarNumber(),timingCharging.getTcId(),timingCharging.getSiteId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result",result);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
	}

	/**
	 *
	 * 不需要审核的车辆列表
	 * @return
	 */
	public String getFeeAuditList(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session1 = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		String parameter = request.getParameter("parameter");
		String ajax = request.getParameter("ajax");

		CAccount account = AccessToTheCompanyId();

		String posNum = request.getParameter("posNum");
		if (posNum != null && !posNum.equals("")) {


			TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
					SessionWrap.KEY_SHOPCUSCOM);


			if(tc==null){
				//说明用的终端机

				String url = request.getHeader("Referer");
				session1.setAttribute("preUrl", url);
				return "login";
			}else{
				String companyId = cmService.getCompanyByPosNum(posNum);

				if(account==null){
					account = new CAccount();
				}
				account.setCompanyID(companyId);
				account.setStaffID(tc.getStaffid());
				session1.setAttribute("account", account);
			}




		}
		if(account!=null) {
		    pageForm = cmService.getFeeAuditList(pageNumber, 30, parameter, account.getCompanyID());
          			session1.setAttribute("preUrl","");
		}else{
			String url = request.getHeader("Referer");
			session1.setAttribute("preUrl", url);
			return "login";
		}
		if("ajax".equals(ajax)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("pageForm", pageForm);
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
			return "success";
		}



		return  "feeaudit";
	}


	/**
	 *
	 * 添加免费审核
	 * @return
	 */
	public String addFeeCar(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session1 = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		String siteId = request.getParameter("siteId");
		String siteName = request.getParameter("siteName");
		String carNumber = request.getParameter("carNumber");


		CAccount account = AccessToTheCompanyId();


		String posNum = request.getParameter("posNum");
		request.setAttribute("message","11");
		if (posNum != null && !posNum.equals("")) {


			TEshopCusCom tc = (TEshopCusCom) sw.getObject(session1,
					SessionWrap.KEY_SHOPCUSCOM);


			if(tc==null){
				//说明用的终端机

				String url = request.getHeader("Referer");
				session1.setAttribute("preUrl", url);
				FinancialBill financialBill = new FinancialBill();
				financialBill.setFinancialbillID("login");
				request.setAttribute("financialBill",financialBill);

				return "success";
			}else{
				String companyId = cmService.getCompanyByPosNum(posNum);

				if(account==null){
					account = new CAccount();
				}
				account.setCompanyID(companyId);
				account.setStaffID(tc.getStaffid());
				session1.setAttribute("account", account);
			}

		}


		if(account!=null) {
			cmService.addFeeCar(carNumber,siteId,account.getCompanyID(),account.getStaffID(),siteName);
		}

         return "success";
	}

	/**
	 *
	 * 移除免费审核
	 * @return
	 */
	public String removeFeeCar(){

		HttpServletRequest request = ServletActionContext.getRequest();
		String cfID = request.getParameter("cfID");


		cmService.removeFeeCar(cfID);

              return "success";
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CAccount getCaccount() {
		return caccount;
	}

	public void setCaccount(CAccount caccount) {
		this.caccount = caccount;
	}

	public CarManage getCm() {
		return cm;
	}

	public void setCm(CarManage cm) {
		this.cm = cm;
	}

	public FeeScale getFeeScale() {
		return feeScale;
	}

	public void setFeeScale(FeeScale feeScale) {
		this.feeScale = feeScale;
	}

	public VenueInformation getVf() {
		return vf;
	}

	public void setVf(VenueInformation vf) {
		this.vf = vf;
	}
	public String getCarmID() {
		return carmID;
	}
	public void setCarmID(String carmID) {
		this.carmID = carmID;
	}
	public String getHasBeenUnder() {
		return hasBeenUnder;
	}
	public void setHasBeenUnder(String hasBeenUnder) {
		this.hasBeenUnder = hasBeenUnder;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public boolean isSiteJudge() {
		return siteJudge;
	}
	public void setSiteJudge(boolean siteJudge) {
		this.siteJudge = siteJudge;
	}
	public String getNumberOrName() {
		return numberOrName;
	}
	public void setNumberOrName(String numberOrName) {
		this.numberOrName = numberOrName;
	}
	public EquipmentInformation getEf() {
		return ef;
	}
	public void setEf(EquipmentInformation ef) {
		this.ef = ef;
	}

	public ProductPackaging getPpk() {
		return ppk;
	}

	public void setPpk(ProductPackaging ppk) {
		this.ppk = ppk;
	}

	public ExaminationRoom getExRoom() {
		return exRoom;
	}

	public void setExRoom(ExaminationRoom exRoom) {
		this.exRoom = exRoom;
	}

	public ExaminationRoomCharge getErCharge() {
		return erCharge;
	}

	public void setErCharge(ExaminationRoomCharge erCharge) {
		this.erCharge = erCharge;
	}

	public TEshopCusCom getTcc() {
		return tcc;
	}

	public void setTcc(TEshopCusCom tcc) {
		this.tcc = tcc;
	}

	public BookingInformation getBfmation() {
		return bfmation;
	}

	public void setBfmation(BookingInformation bfmation) {
		this.bfmation = bfmation;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public Map<String, ProSetupSub> getPssMap() {
		return pssMap;
	}

	public void setPssMap(Map<String, ProSetupSub> pssMap) {
		this.pssMap = pssMap;
	}

	public String getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(String brokerage) {
		this.brokerage = brokerage;
	}

	public Object getResults() {
		return results;
	}

	public void setResults(Object results) {
		this.results = results;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}


	public ParkingSpace getParkingSpace() {
		return parkingSpace;
	}


	public void setParkingSpace(ParkingSpace parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public ProSetup getProSetup() {
		return proSetup;
	}

	public void setProSetup(ProSetup proSetup) {
		this.proSetup = proSetup;
	}

	public TimingCharging getTimingCharging() {
		return timingCharging;
	}

    public void setTimingCharging(TimingCharging timingCharging) {
        this.timingCharging = timingCharging;
    }


    //=================================================================移动端接口停车场管理================================================================
    private String companyId;

    /**
     * 移动端查询场地列表
     *
     * @return
     */
    public String mobileSiteList() {

        pageForm = cmService.siteListInfo(hasBeenUnder, companyId, numberOrName, pageForm, pageSize);
        JSONObject jsonObject = JSONObject.fromObject(pageForm);
        result = jsonObject.toString();

        return "success";
    }

    /**
     * 查询公司人员
     *
     * @return
     */
    public String mobileQueryStaff() {
        List<BaseBean> list = cmService.queryStaff(vf.getCompanyId(),"");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dataList", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 修改停车场使用状态
     *
     * @return
     */
    public String mobileUppdateSiteDetails() {
        VenueInformation vfInfo = new VenueInformation();
        vfInfo.setSiteId(vf.getSiteId());
        vfInfo.setStatus(vf.getStatus());
        boolean b = cmService.uppdateSiteDetails(vfInfo);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", b);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 删除停车场
     *
     * @return
     */
    public String mobileDelSite() {
        VenueInformation vfInfo = new VenueInformation();
        vfInfo.setSiteId(vf.getSiteId());
        //查询是否存在车位，如果存在则不能删除，不存在则删除

        boolean b = cmService.mobileDelSite(vfInfo);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", b);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    //=================================================================移动端接口停车位管理================================================================

    /**
     * 查询公司下面的所有停车位
     *
     * @return
     */
    public String mobileParkingSpaceList() {

        pageForm = cmService.parkingSpaceList(parkingSpace, pageSize, pageNumber, companyId);
        JSONObject jsonObject = JSONObject.fromObject(pageForm);
        result = jsonObject.toString();
        return "success";
    }

    SessionWrap sw = SessionWrap.getInstance();
    HttpSession session = ServletActionContext.getRequest().getSession();

    /**
     * 所在场地
     *
     * @return
     */
    public String mobileGetSiteList() {
        TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
                SessionWrap.KEY_SHOPCUSCOM);
        String sccId = null;
        if (tc != null) {
            sccId = tc.getSccId();
            List<BaseBean> listInfo = new ArrayList<>();
            List<String> list = cmService.getNewSiteList(sccId);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    List<BaseBean> addList = cmService.getSiteList(list.get(i));
                    listInfo.addAll(addList);
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("sitelist", listInfo);
            JSONObject jo = JSONObject.fromObject(map);
            this.result = jo.toString();
            return "success";
        }
        return null;
    }

    /**
     * 添加或修改车位
     *
     * @return
     */
    public String mobileUpdateSpace() {
        CAccount account = AccessToTheCompanyId();
        if (account == null) {
            return "nologin";
        }
        cmService.addUpdateSpace(parkingSpace, parkingSpace.getCompanyId(), account.getStaffID());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", true);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 根据id查询单条数据进行回显修改操作
     *
     * @return
     */
    public String mobileGetParksByParksId() {
        Object parksInfo = cmService.getParksByParksId(parkingSpace.getParksId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parksInfo", parksInfo);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 删除车位
     *
     * @return
     */
    public String mobileDelParks() {
        boolean b = cmService.mobileDelParks(parkingSpace);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", b);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }

    /**
     * 修改车位状态
     *
     * @return
     */
    public String mobileUpdateParksStatus() {
        boolean b = cmService.mobileUpdateParksStatus(parkingSpace);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("boolean", b);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }
    //=================================================================移动端接口收费设置================================================================

    /**
     * 查询收费标准列表 *
     *
     * @return
     */
    public String mobileFeescale() {
        HttpServletRequest request = ServletActionContext.getRequest();
        CAccount account = AccessToTheCompanyId();

        if (account == null) {
            return "nologin";
        }

        pageForm = cmService.getFeeList(
                null != pageForm ? pageForm.getPageNumber() : 1,
                pageNumber == 0 ? 10 : pageNumber, vf, companyId);

        String totalPct = cmService.getTotalPct(companyId);
        request.setAttribute("totalPct", totalPct);

        //代理类别查询
        List<BaseBean> dlList = baseBeanService.getListBeanByHqlAndParams
                ("from ProductPackaging where type=? and goodsname<>? order by sorting",
                        new Object[]{"佣金分配代理类别", "佣金分配代理类别"});
        request.setAttribute("dlList", dlList);
        request.setAttribute("companyId", companyId);
        return "mobileFeescale";
    }


    //=================================================================移动端接口设备管理================================================================

    /**
     * 查询公司名称和场地信息
     *
     * @return
     */
    public String mobileDeviceLnfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map = cmService.deviceLnfo(ef.getEquipmentId(), siteJudge, companyId);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 查询设备列表
     *
     * @return
     */
    public String mobileFacilityList() {
        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append("select e.equipmentid,y.companyname,e.unittype,e.equipmentnumber, ");
        sb.append("e.devicename,e.manufacturer,to_char(e.equipmentdate, 'YYYY-MM-DD HH24:MI:SS'), ");
        sb.append("to_char(e.installationTime, 'YYYY-MM-DD HH24:MI:SS'),v.sitename,e.status,e.channel ");
        sb.append("from dt_venueinformation v,dt_equipmentinformation e,dt_ccom_com c,dtcontactcompany y ");
        sb.append("where v.companyid = ? and v.siteid = e.siteid and v.companyid = c.compnay_id ");
        sb.append("and c.ccompany_id = y.ccompanyid ");
        list.add(companyId);
        if (numberOrName != null && !numberOrName.equals("")) {
            sb.append("and (e.equipmentNumber like ? or e.deviceName like ?) ");
            list.add("%" + numberOrName + "%");
            list.add("%" + numberOrName + "%");
        }
        if (hasBeenUnder != null && !hasBeenUnder.equals("")) {
            sb.append("and e.status = ? ");
            list.add(hasBeenUnder);
        }
        sb.append("order by e.equipmentdate desc ");

        pageForm = baseBeanService.getPageFormBySQL(
                null != pageForm ? pageForm.getPageNumber() : 1,
                pageNumber == 0 ? 10 : pageNumber, sb.toString(),
                "select count(*) from (" + sb.toString() + ")", list.toArray());
        JSONObject jsonObject = JSONObject.fromObject(pageForm);
        result = jsonObject.toString();
        return "success";
    }

    /**
     * 查询单条设备数据进行修改回显
     *
     * @return
     */
    public String mobileGetDeviceByEquipmentId() {
        Object info = cmService.getDeviceByEquipmentId(ef.getEquipmentId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", info);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";

    }


    //=================================================================移动端接口车辆进出记录================================================================
    public String timeNumber;
    public Integer pageSize;

    /**
     * 查询汇总 *
     *
     * @return
     */
    public String mobileCollectList() {
        List<Object> list = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("select c.carmID,c.carNumber,to_char(c.indate,'YYYY-MM-DD HH24:MI:SS'),to_char(c.outdate,'YYYY-MM-DD HH24:MI:SS'),v.siteNumber,v.sitename,e.equipmentNumber,e.devicename, ");
        sb.append("f.staffname,c.status,c.carmNumber,c.money,c.time,c.model,c.chargeType,c.chargeState from ");
        sb.append("dt_VenueInformation v,dt_EquipmentInformation e,Dt_Carmanage c,dt_hr_staff f ");
        sb.append("where v.siteid=e.siteid and e.equipmentnumber=c.equipmentnumber and v.staffId=f.staffId ");
        sb.append("and v.companyId = ? and (c.status = ? or c.status = ?) ");
        list.add(companyId);
        list.add("0");
        list.add("1");
        if (timeNumber != null && !"0".equals(timeNumber)) {
            Map<String, Date> dateMap = DateUtils.determineTimeNumber(timeNumber);
            logger.info("值：{}", dateMap);
            sb.append("and c.indate >= ?");
            list.add(dateMap.get("startTime"));
            sb.append("and c.outdate<= ?");
            list.add(dateMap.get("endTime"));
        }
        sb.append("order by c.indate desc");
        pageForm = baseBeanService.getPageFormBySQL(
                pageNumber, pageSize, sb.toString(),
                "select count(*) from (" + sb.toString() + ")", list.toArray());
        JSONObject jsonObject = JSONObject.fromObject(pageForm);
        result = jsonObject.toString();
        return "success";
    }

    public String getCompanyId() {
        return companyId;
    }


    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    //=======================================================空位停车开始===========================================================
    private ParkingSpaceEmpty emp;

    /**
     * 发布空位停车
     *
     * @return
     */
    public String addParkingSpaceEmpty() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CAccount account = AccessToTheCompanyId();
        emp.setStaffId(account.getStaffID());
        emp.setStaffName(account.getStaffName());
        emp.setStartTime(formatter.parse(emp.getStartTimeTransfer()));
        emp.setEndTime(formatter.parse(emp.getEndTimeTransfer()));
        cmService.addParkingSpaceEmpty(emp);
        return "success";
    }

    /**
     * 删除空位停车
     *
     * @return
     */
    public String delParkingSpaceEmpty() {

        return null;
    }

    /**
     * 查询符合条件的车位编号
     *
     * @return
     */
    public String findParkingCodeBySiteId() {
        List<BaseBean> list = cmService.findParkingCodeBySiteId(parkingSpace.getSiteId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parkList", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    /**
     * 查询个人发布的空位停车记录
     *
     * @return
     */
    public String findParkingSpaceEmpty() {
        CAccount account = AccessToTheCompanyId();
        String staffID = account.getStaffID();
        List<Object> list = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append("select v.SITENAME,e.PARKING_CODE,CASE  WHEN p.STATUS = '00' THEN '空闲'  WHEN p.STATUS = '01' THEN '已使用'  END AS state_label," +
                "TO_CHAR(e.START_TIME, 'YYYY-MM-DD HH24:MI:SS'),TO_CHAR(e.END_TIME, 'YYYY-MM-DD HH24:MI:SS'),e.LIAISON_NAME,e.LIAISON_PHON from DT_PARKINGSPACE_EMPTY e " +
                "LEFT JOIN dt_venueinformation v on e.SITE_ID=v.SITEID LEFT JOIN DT_PARKINGSPACE p on e.PARKS_ID=p.PARKSID where e.STAFF_ID=? ");
        list.add(staffID);
        if (timeNumber != null && !"0".equals(timeNumber)) {
            Map<String, Date> dateMap = DateUtils.determineTimeNumber(timeNumber);
            logger.info("值：{}", dateMap);
            sb.append("and e.START_TIME >= ?");
            list.add(dateMap.get("startTime"));
            sb.append("and e.START_TIME<= ?");
            list.add(dateMap.get("endTime"));
        }
        sb.append(" order by e.START_TIME desc");
        pageForm = baseBeanService.getPageFormBySQL(
                pageNumber, pageSize, sb.toString(),
                "select count(*) from (" + sb.toString() + ")", list.toArray());
        org.json.JSONObject jsonObject = new org.json.JSONObject();
        if (pageForm != null) {
            jsonObject = new org.json.JSONObject(pageForm);
            result = jsonObject.toString();
        } else {
            result = null;
        }

        return "success";
    }

    public String findParkingSpaceEmptyByParksId() {
        String parksId = emp.getParksId();
        List<BaseBean> list = cmService.findParkingSpaceEmptyByParksId(emp, emp.getStartTimeTransfer(), emp.getEndTimeTransfer());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("emplist", list);
        JSONObject jo = JSONObject.fromObject(map);
        this.result = jo.toString();
        return "success";
    }

    public ParkingSpaceEmpty getEmp() {
        return emp;
    }

    public void setEmp(ParkingSpaceEmpty emp) {
        this.emp = emp;
    }
//=======================================================空位停车结束===========================================================

	//========================车牌识别mqtt======================
	public String payloadJson;

	public String mqttShenZhenShiBie() {
		Map<String,Object> resultMap=new LinkedHashMap<>();
		String content = "";
		String plateid = "";
		String siteid = "";
		HttpServletRequest request;
		JSONObject jsonObject = null;
		if(null == jsonObject || jsonObject.isEmpty()){
			jsonObject= JSONObject.fromObject(payloadJson);
		}

		boolean bl = false;
		int Open = 0;
		String info = "no";//ok是开门
		//判断是摄像头轮询数据还是直接请求数据
		JSONObject channelNum = jsonObject.getJSONObject("channel_num");
		if(channelNum.isEmpty() && channelNum.equals("1")){

			logger.info("channelNum: {}", channelNum);

			//这个值表示是摄像头通过轮询调用服务器
			//获取摄像头id
			String cameraId = jsonObject.getString("serialno");
			//channel_num等于1表示是相机轮询问过来的数据
			if (cameraId != null && !cameraId.equals("")) {
				logger.info("cameraId: {}", cameraId);
				// 如果没有进入的数据先查询下有没有最新未结算的记录，如果有的话，不给开门提示有未结算的，先处理未结算的
				String hqlout = "from CarManage where equipmentNumber = ? and status = ? and open = ? and outdate is not null order by outdate desc";
				List<BaseBean> listcmout = baseBeanService
						.getListBeanByHqlAndParams(
								hqlout,
								new Object[] { cameraId,
										"0", "0" });
				CarManage cmout = null;

				if (listcmout.size() > 0) {
					cmout = (CarManage) listcmout
							.get(0);

				}
				if (cmout != null&&cmout
						.getChargeState()!=null
						&& !"00".equals(cmout
						.getChargeState())) { // 说明上一setOpen("1")个离开的付款了

					// 等于0的时候没太过杆
					cmout.setOpen("1");//222
					baseBeanService.update(cmout);
					logger.info("抬杆");

					Open = 1;// 抬杆
					info = "ok";
					JSONObject jsonObjList = new JSONObject();

					JSONObject jsonObjList1 = new JSONObject();

					jsonObjList1.accumulate("info",
							info);//开闸信息


					jsonObjList.accumulate("Response_AlarmInfoPlate",
							jsonObjList1);
					results = jsonObjList;
					if (results != null) {
						System.out.print(results
								.toString());
					}
					return Action.SUCCESS;

				} else {
					return Action.SUCCESS;
				}
			}
		}
		try {

			//有值才说明识别到数据病传输过来了
			JSONObject chePaiAllData = null;
			if(jsonObject!=null && jsonObject.size()>0 && jsonObject.toString().indexOf("AlarmInfoPlate") != -1){
				chePaiAllData = jsonObject.getJSONObject("AlarmInfoPlate");
				if(chePaiAllData.toString().indexOf("serialno")==-1){
					return Action.SUCCESS;
				}
			}

			//获取摄像头id
			String equipmentNumber = chePaiAllData.getString("serialno");
			JSONObject jsonresult = chePaiAllData.getJSONObject("result");

			JSONObject jsonplate  =  jsonresult.getJSONObject("PlateResult");

			String channel= chePaiAllData.getString("channel");

			//获取车牌号
			String number= jsonplate.getString("license");
			plateid = jsonplate.getString("plateid");

//			 String number = "A1232";
//			 String equipmentNumber = request.getParameter("equipmentNumber");
			if(number!=null){
				//number = number.replace(" ", "+");
				//logger.info("调试信息");
				byte[] decodedBytes = Base64.getDecoder().decode(number);
				String decodedString = new String(decodedBytes, "UTF-8");
				//logger.info("调试信息");
				if("_无_".equals(decodedString)){
					logger.info("无车牌号  不进行任何的操作");
					number=null;
				}else{
					number=decodedString;
				}
			}else{
				number=null;
			}
			logger.info("number: {}", number);
			//	logger.info("调试信息");
			logger.info("equipmentNumber: {}", equipmentNumber);
			if(number!=null&&!number.equals("")) {
				JSONObject timeStamp = jsonplate.getJSONObject("timeStamp").getJSONObject("Timeval");
				String date1 = timeStamp.getString("usec");// 识别时间,毫秒值
				logger.info("date1: {}", date1);
				//  String date = Utilities.getDateString(new Date(Long.valueOf(date1)),"yyyy-MM-dd HH:mm:ss");
				String panoramabase64 = "";
				String plateNumberbase64="";
				try {
					panoramabase64 = jsonplate.getString("imageFile");//全景图
					plateNumberbase64 = jsonplate.getString("imageFragmentFile");//车牌图
				}catch (Exception e){
					logger.info("没有图片");
				}
				String numberType = jsonplate.getString("type");//车牌类型

				String date = Utilities.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss");
				logger.info("date: {}", date);

				//		logger.info("调试信息");
				// 通过设备id查询到公司id以及人员id
				StringBuilder sb = new StringBuilder();
				sb.append("select t from ");
				sb.append("EquipmentInformation e,VenueInformation v,TEshopCusCom t ");
				sb.append("where e.siteId=v.siteId and v.companyId=t.companyId ");
				sb.append("and e.equipmentNumber=? ");


				String hql11 = "from EquipmentInformation where equipmentNumber = ?";
				EquipmentInformation c = (EquipmentInformation)baseBeanService.getBeanByHqlAndParams(hql11,new Object[]{equipmentNumber});
				if(c!=null){

					channel = c.getChannel();
					siteid = c.getSiteId();

				}

				TEshopCusCom cuscom = (TEshopCusCom) baseBeanService
						.getBeanByHqlAndParams(sb.toString(),
								new Object[]{equipmentNumber});
				if (cuscom == null) {
					logger.info("录入车辆失败请根据下面设备编号查询账号是否存在--------设备编号:: {}", equipmentNumber);
					Open = 0;
					info = "no";
				} else {
					// 图片存储路径
					String photopath = "upload_files\\theLicenseInformation\\"
							+ cuscom.getCompanyId() + "\\" + cuscom.getStaffid();
					// 重命名
					String upName1 = UUID.randomUUID().toString()
							+ System.currentTimeMillis() + ".jpeg";
					String upName2 = UUID.randomUUID().toString()
							+ System.currentTimeMillis() + ".jpeg";
					// 生成全景jpeg图片

					if(!"".equals(panoramabase64)) {
						GenerateImage(panoramabase64, photopath, upName1);
						// 生成车牌jpeg图片
						GenerateImage(plateNumberbase64, photopath, upName2);
					}

					if (channel.equals("1")) {// 进入停车场

						//查询是否存在未离开的记录，如果有则更新这条记录，没有则正常执行
						String findCarInfoSQL = "select CARMKEY,INDATE,OUTDATE from (select CARMKEY,INDATE,OUTDATE from DT_CARMANAGE where CARNUMBER=? ORDER BY INDATE DESC ) tmp where ROWNUM = 1";
						Object obj= baseBeanService.getObjectBySqlAndParams(findCarInfoSQL, new Object[]{number});
						if(obj!=null){
							Object[]  objInfo=(Object[]) obj;
							Object carmkey = objInfo[0];
							Object inDate = objInfo[1];
							Object outDate = objInfo[2];
							if(inDate!=null && outDate==null){
								//开始更新当前这条记录的进入时间
								logger.info("当前车辆：{} 有未离开记录，直接更新进入时间。", number);
								String updateSql = "update DT_CARMANAGE set INDATE=? where CARMKEY=? and CARNUMBER=?";
								baseBeanService.saveBeansListAndexecuteSqlsByParams(null,
										new String[] { updateSql }, new Object[] {new Date(),carmkey,number});
							}
						}
						logger.info("进入停车场");
						if(indateMap!=null){
							indateMap.put(siteid+number,date);//记录进入最新识别时间
						}
						Date start = Utilities.getDateFromString(date,"yyyy-MM-dd HH:mm:ss");//进来时间
						if(outdateMap!=null) {
							String dateout = outdateMap.get(siteid+number);//最后离开识别时间
							if(dateout!=null) {
								logger.info("dateout: {}", dateout);
								Date end = Utilities.getDateFromString(dateout, "yyyy-MM-dd HH:mm:ss");
								long diff = start.getTime() - end.getTime();//最新进入时间-最后离开识别时间
								logger.info("diff1: {}", diff);
								long minute = diff /1000;
								logger.info("minute: {}", minute);
								if (minute <= 60) {//
									logger.info("小于等于1分钟");
									Open = 0;//出去的时间和进入的时间小于一分钟可能是车尾识别到了，此次识别作废
									info = "no";
									logger.info("车尾识别到了进去的摄像头");
									return Action.SUCCESS;
								}
							}
						}

						//进入时候判断停车场有无车位了
						Map<String, String> parking = cmService.searchParkingCode(equipmentNumber);
						String parkingCode = "";
						String parksId = "";
						if (parking != null && "suc".equals(parking.get("result"))) {
							parkingCode = parking.get("parkingCode");
							parksId = parking.get("parksId");

						}
						logger.info("parkingCode: {}", parkingCode);
						if (parkingCode == null
								|| parkingCode.equals("")) {
							Open = 0;// 不开无停车位
							info = "no";
							resultMap = cmService.pushMqtt(cuscom.getCompanyId(), "in", parkingCode, null, equipmentNumber,"");
							//直接发送语音和文字
							LinkedHashMap<String,String> pronunciationgMap=new LinkedHashMap<>();
							LinkedHashMap<String,String> showTextMap=new LinkedHashMap<>();
							pronunciationgMap.put("1",number+"_1");
							pronunciationgMap.put("2","0A2B_2");//车位已满0A,禁止通行2B
							showTextMap.put(number,"01");
							showTextMap.put("车位已满","02");
							showTextMap.put("禁止通行","03");
							showTextMap.put("请联系管理员","04");
							//显示屏文字
							//logger.info("值：{}", equipmentNumber);
							CarMqttService.xianshishuju("1234567890",equipmentNumber,showTextMap,"ok");
							//语音
							CarMqttService.pronunciation("1234567890",equipmentNumber,pronunciationgMap);
						} else {

							Map<String,Object> mp = cmService.checkCarRecord(number,date,photopath,upName1,upName2,equipmentNumber,cuscom.getCompanyId(), channel, numberType,parksId);
							Open = (int)mp.get("Open");

							String tip = String.valueOf(mp.get("tip"));
							String carmID = String.valueOf(mp.get("carmID"));
							String  ests = "0";



							if(dateMap!=null){
								//	logger.info("调试信息");
								String dates = dateMap.get(carmID);
								logger.info("dates: {}", dates);
								if(dates!=null&&!dates.equals("")){
									logger.info("dates: {}", dates);
									Date end1 = Utilities.getDateFromString(dates,"yyyy-MM-dd HH:mm:ss");
									long diff = start.getTime()-end1.getTime();
									long second = diff/1000;
									if(second<=15){
										ests = "1";
										logger.info("second: {}", second);
									}else{
										//大于15
										dateMap.put(carmID,date);
										logger.info("大于15");
									}

								}else{
									logger.info("第一次");
									logger.info("carmID: {}", carmID);
									logger.info("调试信息");
									dateMap.put(carmID,date);
									//	logger.info("调试信息");

								}

							}



							if(Open==1){
								info = "ok";
								dateMap.remove(carmID);
							}else{
								info = "no";
							}
							if("0".equals(ests)) {
								resultMap = cmService.pushMqtt(cuscom.getCompanyId(), "in", parkingCode, carmID, equipmentNumber, tip);
							}
						}

					} else if (channel.equals("0")) {// 离开停车场
						logger.info("离开停车场");
						if(outdateMap!=null){
							outdateMap.put(siteid+number,date);//记录离开最新识别时间
						}
						String hql = "from CarManage c where c.carNumber=? and c.status=? and c.indate is not null and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?) order by c.indate desc";
						List<BaseBean> listcm = baseBeanService.getListBeanByHqlAndParams(hql,
								new Object[]{number, "1",siteid});
						if(listcm.size()>0){
							cm = (CarManage) listcm.get(0);
						}


						if (cm != null) {
							logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);
							if(indateMap!=null){
								Date start = Utilities.getDateFromString(date,"yyyy-MM-dd HH:mm:ss");//当前离开识别时间
								String datein = indateMap.get(siteid+number);//最后进来识别时间
								logger.info("datein: {}", datein);
								if(datein!=null) {

									Date in = Utilities.getDateFromString(datein, "yyyy-MM-dd HH:mm:ss");
									long diff = start.getTime() - in.getTime();
									logger.info("diff1: {}", diff);
									long minute = diff /1000;
									logger.info("minute: {}", minute);
									if (minute <= 60) {//算是同一次出去
										logger.info("小于等于1分钟");
										Open = 0;//说明可能是进去的时候车尾识别到了出去的摄像头，此次识别作废
										info = "no";
										logger.info("车尾识别到了出去的摄像头");
										return Action.SUCCESS;
									}
								}
							}

							cmService.updateManageCar(cm, number, date, equipmentNumber, photopath, upName1, upName2, channel);
							logger.info("[车辆检测] 存在未完成的进入记录，车牌号：{}", number);
							String state = cmService.programCalls(cm.getCarmID());
							if ("01".equals(state)) {
								Open = 1;//开
								info = "ok";
								logger.info("0元和套餐直接成功");
							} else {
								Open = 0;//不开
								info = "no";
								logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);

							}
							resultMap = cmService.pushMqtt(cuscom.getCompanyId(), "out", null, cm.getCarmID(), equipmentNumber,"");




						} else {
							logger.info("离开时二次识别车辆");
							//如果没有进入的数据先查询下有没有最新未结算的记录，如果有的话，不给开门提示有未结算的，先处理未结算的

							String hqlout = "from CarManage c where c.carNumber=? and c.status=? and c.outdate is not null and c.equipmentNumber in (select n.equipmentNumber from EquipmentInformation n where n.siteId = ?) order by c.outdate desc";
							List<BaseBean> listcmout = baseBeanService.getListBeanByHqlAndParams(hqlout,
									new Object[]{number, "0",siteid});
							CarManage cmout = null;
							String estss = "0";
							if(listcmout.size()>0) {
								cmout = (CarManage) listcmout.get(0);
								if(indateMap!=null){
									Date startcc = Utilities.getDateFromString(date,"yyyy-MM-dd HH:mm:ss");//当前离开识别时间
									String datein = indateMap.get(siteid+number);//最后进来识别时间
									logger.info("datein: {}", datein);
									if(datein!=null) {
										Date incc = Utilities.getDateFromString(datein, "yyyy-MM-dd HH:mm:ss");

										long diffcc = startcc.getTime() - incc.getTime();
										logger.info("diff1cc: {}", diffcc);
										long minutecc = diffcc /1000;
										logger.info("minutecc: {}", minutecc);

										if (minutecc <= 60) {//算是同一次出去
											logger.info("minutecc小于等于1分钟");
											Open = 0;//说明可能是进去的时候车尾识别到了出去的摄像头，此次识别作废
											info = "no";
											logger.info("车尾识别到了出去的摄像头");
											return Action.SUCCESS;
										}
									}

								}
								Date startc = Utilities.getDateFromString(date,"yyy-MM-dd HH:mm:ss");
								if(dateMap!=null){
									String datesc = dateMap.get(cmout.getCarmID());
									if(datesc!=null&&!datesc.equals("")){

										Date endc = Utilities.getDateFromString(datesc,"yyyy-MM-dd HH:mm:ss");
										long diffc = startc.getTime()-endc.getTime();
										long secondc = diffc/1000;
										if(secondc<=15){
											estss = "1";
										}else{
											//大于15
											dateMap.put(cmout.getCarmID(),date);
										}

									}else{
										dateMap.put(cmout.getCarmID(),date);
									}



								}
							}


							if(cmout!=null&&"00".equals(cmout.getChargeState())){
								logger.info("之前有未结算的提示结算");
								//说明之前有未结算的提示结算
								Open = 0;//不开
								info = "no";
								if("0".equals(estss)) {
									resultMap = cmService.pushMqtt(cuscom.getCompanyId(), "out", null, cmout.getCarmID(), equipmentNumber, "");
								}


							}else{
								logger.info("没有未结算");
								if(cmout!=null&&!"00".equals(cmout.getChargeState())){  //说明上一个离开的付款了，现在没有进入记录，查询上一个离开时间，如果在20分钟内算是同一次出去
									logger.info("查询上次结算记录");
									Date start  = new Date();
									Date  out = cmout.getOutdate();

									long diff = start.getTime()-out.getTime();
									logger.info("diff: {}", diff);
									long minute = diff/60/1000;
									logger.info("minute: {}", minute);
									if(minute<20){//算是同一次出去
										logger.info("小于20分钟");
										Open = 1;//开门
										info = "ok";
										if("0".equals(estss)) {
											resultMap = cmService.pushMqtt(cuscom.getCompanyId(), "out", null, cmout.getCarmID(), equipmentNumber, "");
											dateMap.remove(cmout.getCarmID());
										}
									}else{
										logger.info("大于20分钟");
										Open = 0;//不开门
										info = "no";
									}
									logger.info("开门状态：{}", Open);
								}

								if(Open==0){//异常数据处理，没有进入记录
									//离开停车场查询不到进入数据则添加一个出口的数据以便于查询
									//进入时候判断停车场有无车位了
									Map<String,String> parking = cmService.searchParkingCode(equipmentNumber);

									String parksId = "";
									if(parking!=null&&"suc".equals(parking.get("result"))){

										parksId = parking.get("parksId");

									}
									String carmID = cmService.addErrorOut(number, date, equipmentNumber, photopath, upName1, upName2,cuscom.getCompanyId(),channel,numberType,parksId);
									logger.info("异常添加一个出去记录");
									String state = cmService.programCalls(carmID);
									if ("01".equals(state)) {
										Open = 1;//开
										info = "ok";
										logger.info("0元和套餐直接成功");
									} else {
										Open = 0;//不开
										info = "no";
										logger.info("[门禁控制] 未支付车辆拒绝开门，车牌号：{}", number);
									}
									resultMap = cmService.pushMqtt(cuscom.getCompanyId(), "out", null, carmID, equipmentNumber, "");


								}

							}



						}
					}
				}
			}else{
				Open = 0;
				info = "no";
			}
			bl = true;




		} catch (Exception e) {
			logger.error("操作异常", e);
			Open = 0;
			info = "no";
			logger.info("报错2");
		}
		logger.info("Open: {}", Open);


		JSONObject jsonObjList = new JSONObject();

		JSONObject jsonObjList1 = new JSONObject();

		jsonObjList1.accumulate("info",
				info);
		jsonObjList1.accumulate("plateid",
				plateid);

		jsonObjList1.accumulate("content",new org.json.JSONObject(resultMap).toString());
		jsonObjList1.accumulate("is_pay",
				"true");


		jsonObjList.accumulate("Response_AlarmInfoPlate",
				jsonObjList1);
		results = jsonObjList;

		if(results!=null){
			System.out.print("results:"+results.toString());
		}

		return Action.SUCCESS;
	}
	public String deviceSN;

	/**
	 * 远程控制道闸的开关，id为自定义，deviceSN为前端传递的设备编号。
	 * @return
	 */
	public String remoteGateOpening(){
		CarMqttService.isOpen("123456",deviceSN);
		return "success";
	}

	/**
	 * 查询所有的场地列表和场地下面绑定的道闸设备信息
	 * @return
	 */
	public String findSiteList(){
		//e.STATUS属性用于向前端传递这个设备是否在线的状态。
		String sql = "select v.SITEID,v.SITENAME,e.EQUIPMENTNUMBER,e.CHANNEL,e.STATUS from DT_VENUEINFORMATION v LEFT JOIN DT_EQUIPMENTINFORMATION e on v.SITEID=e.SITEID where SITETYPE='1' and e.STATUS='00'";
		List siteList = baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{});
		Map<String,List<Object>> map=new HashMap<>();
		if(siteList!=null){
			for (int i = 0; i < siteList.size(); i++) {
				Object[] obj=(Object[])siteList.get(i);
				String o = (String) obj[0];
				if(map.containsKey(o)){
					List<Object> objects = map.get(o);
					String equipmentNumber = (String)obj[2];
					Boolean deviceState = CarMqttService.deviceStatusMap.get(equipmentNumber);
					if(deviceState!=null && deviceState==true){
						//设备在线
						obj[4]="01";
					}else{
						obj[4]="02";
					}
					objects.add(obj);
					map.put(o,objects);
				}else{
					List<Object> objects=new ArrayList<>();
					String equipmentNumber = (String)obj[2];
					Boolean deviceState = CarMqttService.deviceStatusMap.get(equipmentNumber);
					if(deviceState!=null && deviceState==true){
						//设备在线
						obj[4]="01";
					}else{
						obj[4]="02";
					}
					objects.add(obj);
					map.put(o,objects);
				}
			}
		}
		JSONObject js=JSONObject.fromObject(map);
		result=js.toString();
		return "success";
	}
}
