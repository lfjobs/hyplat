package hy.ea.driving.action;

import hy.ea.bo.CAccount;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class BaoKaiXueAction {
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
	 * @time 2015-01-07
	 * @describe 设置学员证件是否齐全
	 * @return
	 */
	public String setDataIsCompleteOfStudent(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		beans = new ArrayList<BaseBean>();
		if (str != null && !"".equals(str)) {
			String[] strs = str.substring(0, str.lastIndexOf(",")).split(",");
			String hql = " from DtDrivingPrincipal where companyid=? and drivingprincipalid=?";
			for (int i = 0; i < strs.length; i++) {
				DtDrivingPrincipal dtDrivingPrincipal_local = (DtDrivingPrincipal) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] {account.getCompanyID(), strs[i].trim() });
				if("01".equals(dtDrivingPrincipal.getDataIsComplete())){
					dtDrivingPrincipal_local.setDataIsComplete("01");
				}else{
					dtDrivingPrincipal_local.setDataIsComplete("00");
				}
				beans.add(dtDrivingPrincipal_local);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
		}
		
		return "success";
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
