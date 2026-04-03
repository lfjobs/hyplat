package hy.ea.production.action.cprocedure.drive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.drive.CarSchoolInform;
import hy.ea.bo.production.drive.OrderCoachRecord;
import hy.ea.bo.production.drive.TeachingContent;
import hy.ea.production.service.CarSchoolService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;


/**
 * 驾校信息
 */
@Controller
public class CarSchoolAction {
	@Resource
	private CarSchoolService csService;
	
	private PageForm pageForm;
	private String result;
	private Map<String,Object> map;
	private CarSchoolInform carSchoolInform;//通知详情表
	private ProductPackaging productPackaging;//产品表
	private TEshopCusCom cusCom;//账号表
	private OrderCoachRecord orderCoachRecord;//预约教练记录表
	private TeachingContent teachingContent;//教学内容
	private List<Object> list;
	private String carInfo;//车辆详细信息
	private String coordinate;//经纬度坐标信息
	private String skipString;//跳转参数
	private List<BaseBean> orderList;
	private Object obj;
	private String teachingDate;
	private String log;//日志
	
	
	/** 
	* @Title: 跳转
	* @Description: 用于页面跳转
	* @return 返回的参数
	*/
	public String pageSkip(){
		cusCom = csService.queryUser();
		if(cusCom==null){
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.removeAttribute("url");
			HttpServletRequest request = ServletActionContext.getRequest();
			String url = request.getHeader("Referer");
			session.setAttribute("url", url);
			return "login";
		}else{
			if(skipString.equals("recommendFriend")){
				return "recommendFriend";//推荐好友
			}else if(skipString.equals("inform")){
				return "inform";//驾校通知
			}else if(skipString.equals("teaching")){
				orderList = csService.queryTrain(cusCom.getStaffid(),teachingDate);
				return "teaching";//教练每天日志
			}else if(skipString.equals("allLog")){
				return "allLog";//教练每月日志
			}else if(skipString.equals("regulations")){
				return "regulations";//车辆违章
			}
		}
		return "";
	}
	//驾校通知
	/** 
	* @Title: 查询
	* @Description: 查询驾校全部通知信息
	* @return 返回的数据   
	*/
	public String queryNews(){
		cusCom = csService.queryUser();
		map = csService.queryNews(pageForm.getPageNumber(),pageForm.getPageSize(),cusCom);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/** 
	* @Title:删除
	* @Description: 删除通知 
	*/
	public String delNews(){
		
		csService.delNews(carSchoolInform.getCsiId());
		
		return "delNews";
	}
	/** 
	* @Title: 查询
	* @Description: 查询驾校通知详情
	* @return 返回的数据   
	*/
	public String queryNewsDetails(){
		list = csService.queryNewsDetails(productPackaging.getPpID());
		csService.updateNewsDetails(carSchoolInform.getCsiId());
		return "queryNewsDetails";
	}
	/** 
	* @Title: 查询
	* @Description: 用户详情,金币余额,金币公告
	* @return 返回的数据   
	*/
	public String goldInform(){
		cusCom = csService.queryUser();
		if(cusCom==null){
			return "login";
		}
		List<Object> userList = csService.queryUserDetails(cusCom);
		Object goldBalance = csService.queryGold(cusCom);
		list = csService.goldAnnouncement();
		map = new HashMap<String, Object>();
		map.put("userList", userList);
		map.put("goldBalance", goldBalance);
		map.put("goldNoticeList", list);
		return "goldInform";
	}
	/** 
	* @Title: 查询
	* @Description: 查询获取金币的详细信息
	* @return 返回的数据   
	*/
	public String relevanceInformation(){
		cusCom = csService.queryUser();
		pageForm = csService.relevanceInformation(pageForm.getPageNumber(),pageForm.getPageSize(),cusCom);
		map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	//推荐好友
	/** 
	* @Title: 查询
	* @Description: 查询推荐人是当前账号的所有购买过驾校产品的账号信息
	* @return 返回的数据   
	*/
	public String recommendFriend(){
		cusCom = csService.queryUser();
		pageForm = csService.recommendFriend(pageForm.getPageNumber(),pageForm.getPageSize(),cusCom);
		map = new HashMap<String, Object>();
		map.put("pageForm", pageForm);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/** 
	* @Title: 查询
	* @Description: 查询车辆违章记录信息
	* @return 返回的数据   
	*/
	//该方法暂时用不上
	public String getWeizhangInfoPost(){
		//carInfo中有车牌号,车架号,发动机号
		result = csService.getWeizhangInfoPost(carInfo);
		return "success";
	}
	/** 
	* @Title: 查询
	* @Description: 查询学员所在位置
	* @return 返回的数据   
	*/
	//该方法暂时用不上
	public String location(){
		
		result = csService.location(coordinate);
		
		return "success";
	}
	
	//教练日志
	/** 
	* @Title: 查询
	* @Description: 查询教练一天的所有日志
	* @return 返回的数据   
	*/
	public String teaching(){
		//传入教练staffId,日期
		orderList = csService.teaching(orderCoachRecord.getStaffcid(),orderCoachRecord.getOrderDate());
		map = new HashMap<String, Object>();
		map.put("orderList", orderList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	
	/** 
	* @Title: 查询
	* @Description: 查询教学内容
	* @return 返回的数据   
	*/
	public String teachingContent(){
		
		map = csService.teachingContent(orderCoachRecord.getOcrId());
		
		return "teachingContent";
	}
	/** 
	* @Title: 查询
	* @Description: 查询教练当月所有日志
	* @return 返回的数据   
	*/
	public String allLog(){
		cusCom = csService.queryUser();
		orderList = csService.allLog(teachingDate,cusCom.getStaffid());
		map.put("orderList", orderList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
	/** 
	* @Title: 添加或修改
	* @Description: 添加或修改日志
	* @return 
	*/
	public String saveOrUpdateLog(){
		if(teachingContent.getTctKey()!=null && !teachingContent.getTctKey().equals("")){
			teachingContent = csService.queryLog(teachingContent.getTctKey());
			teachingContent.setTctContent(log);
			csService.saveOrUpdateLog(teachingContent,teachingDate);
		}else{
			TeachingContent teachingContent1 = new TeachingContent();
			teachingContent1.setOcrId(orderCoachRecord.getOcrId());
			teachingContent1.setTctContent(log);
			csService.saveOrUpdateLog(teachingContent1,teachingDate);
		}
		return "saveOrUpdateLog";
	}
	
	
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ProductPackaging getProductPackaging() {
		return productPackaging;
	}

	public void setProductPackaging(ProductPackaging productPackaging) {
		this.productPackaging = productPackaging;
	}

	public void setCarSchoolInform(CarSchoolInform carSchoolInform) {
		this.carSchoolInform = carSchoolInform;
	}

	public CarSchoolInform getCarSchoolInform() {
		return carSchoolInform;
	}
	
	public String getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}
	public String getSkipString() {
		return skipString;
	}
	public void setSkipString(String skipString) {
		this.skipString = skipString;
	}
	public TEshopCusCom getCusCom() {
		return cusCom;
	}
	public void setCusCom(TEshopCusCom cusCom) {
		this.cusCom = cusCom;
	}
	public OrderCoachRecord getOrderCoachRecord() {
		return orderCoachRecord;
	}
	public void setOrderCoachRecord(OrderCoachRecord orderCoachRecord) {
		this.orderCoachRecord = orderCoachRecord;
	}
	public List<BaseBean> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<BaseBean> orderList) {
		this.orderList = orderList;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getTeachingDate() {
		return teachingDate;
	}
	public void setTeachingDate(String teachingDate) {
		this.teachingDate = teachingDate;
	}
	public TeachingContent getTeachingContent() {
		return teachingContent;
	}
	public void setTeachingContent(TeachingContent teachingContent) {
		this.teachingContent = teachingContent;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
}
