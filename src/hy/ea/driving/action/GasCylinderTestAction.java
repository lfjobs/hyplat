package hy.ea.driving.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.driving.DtCarCarwindingdetection;
import hy.ea.bo.driving.DtCarCylinderInformation;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import com.opensymphony.xwork2.ActionContext;

public class GasCylinderTestAction{
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private List<BaseBean> beans;
	private PageForm pageForm;
	private int pageNumber;
	private String status;
	private Object obj;
	private Object obj2;
	private String carwindingdetectionid;
	private String bottleType;   //气瓶类型
	private String carCylinderId;
    private DtCarCylinderInformation cylinderInformation;
	private String parameter;
	private DtCarCarwindingdetection carwindingdetection;
	
	//保存气瓶缠绕瓶的检测信息
	public String saveWindingtest(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		beans = new ArrayList<BaseBean>();
		String carCylinderIda = carwindingdetection.getCarCylinderId();
		if("Winding".equals(bottleType)){
			if(null==carwindingdetection.getCarwindingdetectionid()||"".equals(carwindingdetection.getCarwindingdetectionid())){
				carwindingdetection.setCarwindingdetectionid(serverService.getServerID("DtCarCarwindingdetection"));
				carwindingdetection.setCarCylinderId(carCylinderIda);
				carwindingdetection.setCompanyid(account.getCompanyID());
				carwindingdetection.setOrganizationid(organizationID);
				parameter = "气瓶缠绕瓶检测保存:气瓶编号" +carCylinderId + ")"; 
			carwindingdetectionid = carwindingdetection.getCarwindingdetectionid();
			CLogBook logBook = logBookService
					.saveCLogBook(null, parameter, account);
			beans.add(logBook);
			beans.add(carwindingdetection);
			baseBeanService.executeHqlsByParamsList(beans, null, null);	
			}
			if(!"".equals(carwindingdetection.getResultInspection())&&"不合格".equals(carwindingdetection.getResultInspection())){
				DtCarCylinderInformation cylinder = new DtCarCylinderInformation();
				String hql = "from DtCarCylinderInformation where companyid=? and organizationid = ? and carCylinderId= ?";
				cylinder = (DtCarCylinderInformation) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID(),organizationID,carCylinderId});
				cylinder.setStatus("00");
				baseBeanService.update(cylinder);
			}
		}
		
		return searchWindingInformationById();
		
	}
	
	//查询气瓶缠绕瓶记录表信息
		public String searchWindingInformationById(){
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			Object[]  param = {account.getCompanyID(),organizationID,carCylinderId,carwindingdetectionid};
			String sql = "select aa.*,hs.staffname,pit.* from dt_carcylinderinformation aa" +
					" left join dt_hr_staff hs on aa.staffid = hs.staffid " +
					"left join dt_car_dispitchlist pit on aa.carcylinderid = pit.cylindernum where aa.companyid = ? and aa.organizationid = ? and aa.carcylinderid = ?";
				obj = baseBeanService.getObjectBySqlAndParams(sql, new Object[]{account.getCompanyID(),organizationID ,carCylinderId});
			
				String  hql2 = "from DtCarCarwindingdetection where companyid = ? and organizationid =? and carCylinderId =? and carwindingdetectionid=?";
			obj2 = baseBeanService.getBeanByHqlAndParams(hql2, param);
			if("Winding".equals(bottleType)){
				return "Windingpage";
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
	
	public DtCarCarwindingdetection getCarwindingdetection() {
		return carwindingdetection;
	}
	public void setCarwindingdetection(DtCarCarwindingdetection carwindingdetection) {
		this.carwindingdetection = carwindingdetection;
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



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCarCylinderId(String carCylinderId) {
		this.carCylinderId = carCylinderId;
	}



	/*public DtCarCylinderInformation getCylinderInformation() {
		return cylinderInformation;
	}
	public void setCylinderInformation(DtCarCylinderInformation cylinderInformation) {
		this.cylinderInformation = cylinderInformation;
	}*/
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}



	public String getBottleType() {
		return bottleType;
	}



	public void setBottleType(String bottleType) {
		this.bottleType = bottleType;
	}

	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	public String getCarwindingdetectionid() {
		return carwindingdetectionid;
	}

	public void setCarwindingdetectionid(String carwindingdetectionid) {
		this.carwindingdetectionid = carwindingdetectionid;
	}

	public DtCarCylinderInformation getCylinderInformation() {
		return cylinderInformation;
	}

	public void setCylinderInformation(DtCarCylinderInformation cylinderInformation) {
		this.cylinderInformation = cylinderInformation;
	}
	
	
}
