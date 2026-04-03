package hy.ea.finance.action.InvestDeviceBind;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;

import hy.ea.bo.CAccount;
import hy.ea.finance.service.InvestDeviceBind.DeviceBindService;
import hy.ea.production.service.WarehouseService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;

public class DeviceBindAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private DeviceBindService deviceBindService;
	@Resource
	private ServerService serverService;
	@Resource
	private BaseBeanDao beandao;
	@Resource
	private WarehouseService warehouseService;
	
	private int pageNumber;
	private String result;
	private String investType; //投资类别 01.公司投资设备，00.挂靠设备
	private PageForm pageForm;
	private InputStream excelStream;
	
	
	/**
	 * 车辆信息公司汇总列表
	 * @return
	 */
	public String carInforList(){
		CAccount account = getAccount();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		HttpServletRequest request= ServletActionContext.getRequest();
		String carNum = request.getParameter("carNum");
		String deviceStatu = request.getParameter("deviceStatu");
		PageForm pageForm = deviceBindService.CarInforList(account, carNum,deviceStatu, pageNumber);
		JSONObject obj = new JSONObject();
		obj.accumulate("pageForm", pageForm);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 投资责任人列表
	 * @return
	 */
	public String selInvestRespose(){
		CAccount account = getAccount();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		HttpServletRequest request= ServletActionContext.getRequest();
		String tzName = request.getParameter("tzName");
		String tzAccount = request.getParameter("tzAccount");
		String tzCustype = request.getParameter("tzCustype");
		PageForm pageForm = deviceBindService.selInvestRespose(pageNumber,account,tzName,tzAccount,tzCustype);
		JSONObject obj = new JSONObject();
		obj.accumulate("pageForm", pageForm);
		result = obj.toString();
		return "success";
	}
	
	//查询公司投资的投资责任人
	public String selComInvestment(){
		CAccount account = getAccount();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		List<BaseBean> investList = deviceBindService.selComInvestment(account);
		JSONObject obj = new JSONObject();
		obj.accumulate("invest", investList);
		result = obj.toString();
		return "success";
	}
	
	
	//给DeviceBind表添加一条记录
	public String addDeviceBind(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String carid = request.getParameter("carid");
		String goodsid = request.getParameter("goodsid");
		String investSccid = request.getParameter("investsccid");
		String investStaffid = request.getParameter("investstaffid");
		String carNum = request.getParameter("carNum");
		deviceBindService.addDeviceBind(carid, goodsid, investSccid, investStaffid, carNum);
		return "success";
	}
	
	
	//查询所有设备
	public String selDeviceBind(){
		CAccount account = getAccount();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "nologin";
		}
		HttpServletRequest request= ServletActionContext.getRequest();
		String carNum = request.getParameter("carNum");
		String deviceStatu = request.getParameter("deviceStatu");
		String tzName = request.getParameter("tzName");
		String tzAccount = request.getParameter("tzAccount");
		request.setAttribute("carNum",carNum);
        request.setAttribute("deviceStatu",deviceStatu);
        request.setAttribute("tzName",tzName);
        request.setAttribute("tzAccount",tzAccount);
		pageForm = deviceBindService.selDeviceBind(account, carNum, deviceStatu, tzName, tzAccount,pageForm);
		request.setAttribute("pageForm", pageForm);
		return "investdevicebind";
	}
	
	//添加关联业务员
	public String addGlStaff(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String dbId = request.getParameter("dbId");
		String sccidStaffids = request.getParameter("sccidStaffids");
		deviceBindService.addGlStaff(dbId, sccidStaffids);
		return selGlStaff();
	}
	
	
	//查询所有已关联业务员
	public String selGlStaff(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String dbId = request.getParameter("dbId");
		request.setAttribute("dbId", dbId);
		pageForm = deviceBindService.selGlStaff(dbId,pageForm);
		request.setAttribute("pageForm", pageForm);
		return "addYeWuYuan";
	}
	
	//判断该车辆是否已经被添加到dt_deviceBind表中
	public String isCarInDevice(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String carId = request.getParameter("carId");
		String carnum = request.getParameter("carnum");
		result = deviceBindService.isCarInDevice(carId, carnum);
		return "success";
	}
	
	//判断选中业务员是否已被关联
	public String isStaffInDbs(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String sccidStaffids = request.getParameter("sccidStaffids");
		CAccount account = getAccount();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		result = deviceBindService.isStaffInDbs(account, sccidStaffids);
		return "success";
	}
	
	//删除deviceBind表的某条数据(即修改该条数据的状态为0，同时修改与该条数据关联的deviceBindStaff表的数据状态为02)
	public String delDeviceBind(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String dbId = request.getParameter("dbId");
		if(dbId != null){
			deviceBindService.delDeviceBind(dbId);
		}
		return "success";
	}
	
	//删除deviceBindStaff表的某条数据(即修改该条数据的状态为01)
	public String delDeviceBindStaff(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String dbsid = request.getParameter("dbsid");
		String dbId = request.getParameter("dbId");
		request.setAttribute("dbId", dbId);
		if(dbsid != null){
			deviceBindService.delDeviceBindStaff(dbsid);
		}
		return "success";
	}
	
	//查看某一设备的所有相关信息
	/*public String selOneDeviceBind(){
		HttpServletRequest request= ServletActionContext.getRequest();
		String dbId = request.getParameter("dbId");
		return "selOneDeviceBind";
	}*/
	
	/**
	 * 投资设备绑定设置导出
	 */
	public String exportExcelDevice(){
		CAccount account = getAccount();
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "nologin";
		}
		HttpServletRequest request= ServletActionContext.getRequest();
		String carNum = request.getParameter("carNum");
		String deviceStatu = request.getParameter("deviceStatu");
		String tzName = request.getParameter("tzName");
		String tzAccount = request.getParameter("tzAccount");
		List<Object> list=deviceBindService.exportExcelDevice(account, carNum, deviceStatu, tzName, tzAccount);
		String[] str={"公司名称","部门","名称","车牌号","投资类型","设备状态","管理责任人","投资人姓名","投资人微分金账号","投资人会员级别","业务员姓名","业务员微分金账号","业务员会员级别"};
		excelStream=warehouseService.OutOrderExcel("投资设备绑定设置表单", str, list);
		return "showexcel";
	}
	
	
	
	public CAccount getAccount(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		return account;
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

	public String getInvestType() {
		return investType;
	}

	public void setInvestType(String investType) {
		this.investType = investType;
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	
}
