package hy.ea.driving.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.driving.DtCarCylinderInformation;
import hy.ea.bo.driving.DtCarDispitchlist;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

public class DisptichOrderAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private List<BaseBean> beans;
	private PageForm pageForm;
	private int pageNumber;
	private Object obj;
	
	private String bottletype;
	private String carCylinderId;
	private DtCarCylinderInformation cylinderInformation;
	private String parameter;
	private DtCarDispitchlist dispitchjob;
	//默认加载
	public String getListorderwork(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String sqlcount = "select count(*) from dt_car_dispitchlist dis left join dt_carcylinderinformation " +
				"car on dis.cylindernum = car.carcylinderid left join dt_hr_staff hr on car.staffid = hr.staffid where dis.companyid=? and dis.organizationId=?";
		String sql = "select dis.checkoutnum,car.cylindernum,hr.staffname,car.licensenumber,car.cylindermodel," +
				"car.manufacturecompany,car.leavefactorydate,car.certificateNumber,car.weight,car.volume,car.cylindertype,car.carCylinderId " +
				"from dt_car_dispitchlist dis left join dt_carcylinderinformation car on dis.cylindernum = car.carcylinderid " +
				"left join dt_hr_staff hr on car.staffid = hr.staffid where dis.companyid=? and dis.organizationId=?";
		pageForm = baseBeanService.getPageFormBySQL((null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), sql, sqlcount,  new Object[]{account.getCompanyID(),organizationID});
		return "sheetlist";
	}
//派工单保存
	public String toSavedispitch(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		
		if(dispitchjob.getCheckoutNum()!=""){
			dispitchjob.setDispitchId(serverService.getServerID("DtCarDispitchlist"));
			dispitchjob.setCompanyId(account.getCompanyID());
			dispitchjob.setOrganizationId(organizationID);
			parameter = "派工单保存:编号" +dispitchjob.getCheckoutNum() + ")"; 
		}
		CLogBook logBook = logBookService
				.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		beans.add(dispitchjob);
		baseBeanService.executeHqlsByParamsList(beans, null, null);
		return "success";
	}
	//打印报废通知单获得废瓶信息及车辆相关信息
	public String getcarlinderInformation(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		String hql = "from DtCarCylinderInformation car " +
				"where car.carCylinderId=? ";
		cylinderInformation = (DtCarCylinderInformation) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{carCylinderId});
		String sql = "select count(car.cylinderNum) from dt_carcylinderinformation car where car.status=? and car.licensenumber=?";
		int countnum = baseBeanService.getConutByBySqlAndParams(sql, new Object[]{00,cylinderInformation.getLicensenumber()});
		session.put("countnum", countnum);
		return "printpanfei";
	}
	
	//查询气瓶基本资料 查询
		public String detectionInformation(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String sql = "select aa.*,hs.staffname,pit.* from dt_carcylinderinformation aa" +
					" left join dt_hr_staff hs on aa.staffid = hs.staffid " +
					"left join dt_car_dispitchlist pit on aa.carcylinderid = pit.cylindernum where aa.companyid = ? and aa.organizationid = ? and aa.carcylinderid = ?";
				obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{account.getCompanyID(),organizationID ,carCylinderId});
			session.put("carlinder", obj);
			if("cylinders".equals(bottletype)){
				return "cylinderpage";
			}else{
				return "Windingpage";
			}
			
		}
	public List<BaseBean> getBeans() {
		return beans;
	}
	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}
	public PageForm getPageForm() {
		return pageForm;
	}
	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}
	public DtCarDispitchlist getDispitchjob() {
		return dispitchjob;
	}
	public void setDispitchjob(DtCarDispitchlist dispitchjob) {
		this.dispitchjob = dispitchjob;
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
	public String getCarCylinderId() {
		return carCylinderId;
	}
	public void setCarCylinderId(String carCylinderId) {
		this.carCylinderId = carCylinderId;
	}
	public DtCarCylinderInformation getCylinderInformation() {
		return cylinderInformation;
	}
	public void setCylinderInformation(DtCarCylinderInformation cylinderInformation) {
		this.cylinderInformation = cylinderInformation;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getBottletype() {
		return bottletype;
	}
	public void setBottletype(String bottletype) {
		this.bottletype = bottletype;
	}
	
}
