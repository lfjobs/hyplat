package hy.ea.driving.action;

import hy.ea.bo.CCode;
import hy.ea.bo.driving.DrivingDealCheGuan;
import hy.ea.bo.driving.DtDrivingPrincipal;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/**
 * 
 * @author zc
 * @version 1.0
 * @describe 计件工资计算
 *
 */
@Controller
@Scope("prototype")
public class PieceworkAction{
	@Resource
	private ShowExcelService excelService;
	public InputStream excelStream;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	private DrivingDealCheGuan drivingDealCheGuan;
	
	private List<BaseBean> drivingDealCheGuanList;
	
	private List<CCode> addressTypelist;
	private String parameter;
	private Map<String, DrivingDealCheGuan> drivingDealCheGuanmap;
	private int pageNumber;
	private List<BaseBean> beans;
	private DtDrivingPrincipal dtDrivingPrincipal;
	/**
	 * 多选框参数传递
	 * 
	 * @return
	 */
	private String str;
	
	/**
	 * @author zc
	 * @time 2015-01-16
	 * @describe 招生数量计件
	 * @return
	 */
	public void  addOrUpdatePieceworkOfAdmissions(){
		
	}
	/**
	 * @author zc
	 * @time 2015-01-16
	 * @describe 培训学时计件
	 * @return
	 */
	public void  addOrUpdatePieceworkOfHours(){
		
	} 
	/**
	 * @author zc
	 * @time 2015-01-16
	 * @describe 合格数量计件
	 * @return
	 */
	public void  addOrUpdatePieceworkOfQualified(){
		
	}
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
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

	public List<CCode> getAddressTypelist() {
		return addressTypelist;
	}

	public void setAddressTypelist(List<CCode> addressTypelist) {
		this.addressTypelist = addressTypelist;
	}

	public DrivingDealCheGuan getDrivingDealCheGuan() {
		return drivingDealCheGuan;
	}

	public void setDrivingDealCheGuan(DrivingDealCheGuan drivingDealCheGuan) {
		this.drivingDealCheGuan = drivingDealCheGuan;
	}

	public List<BaseBean> getDrivingDealCheGuanList() {
		return drivingDealCheGuanList;
	}

	public void setDrivingDealCheGuanList(List<BaseBean> drivingDealCheGuanList) {
		this.drivingDealCheGuanList = drivingDealCheGuanList;
	}

	public Map<String, DrivingDealCheGuan> getDrivingDealCheGuanmap() {
		return drivingDealCheGuanmap;
	}

	public void setDrivingDealCheGuanmap(
			Map<String, DrivingDealCheGuan> drivingDealCheGuanmap) {
		this.drivingDealCheGuanmap = drivingDealCheGuanmap;
	}

	public List<BaseBean> getBeans() {
		return beans;
	}

	public void setBeans(List<BaseBean> beans) {
		this.beans = beans;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public DtDrivingPrincipal getDtDrivingPrincipal() {
		return dtDrivingPrincipal;
	}

	public void setDtDrivingPrincipal(DtDrivingPrincipal dtDrivingPrincipal) {
		this.dtDrivingPrincipal = dtDrivingPrincipal;
	}
	

	
}
