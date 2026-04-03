package hy.ea.driving.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.driving.DrivingDealCheGuan;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DrivingCheGuanAction {
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
	
	/**
	 * 添加或修改车管
	 * 参数：drivingDealCheGuanmap
	 * 返回：getListAddress()
	 */
	public String saveDrivingCheGuan() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		beans= new ArrayList<BaseBean>();
		if(drivingDealCheGuanmap!=null){
		for(DrivingDealCheGuan drivingDealCheGuan:drivingDealCheGuanmap.values()){
			if (null == drivingDealCheGuan.getDrivingDealCheGuanid() || "".equals(drivingDealCheGuan.getDrivingDealCheGuanid())) {
				drivingDealCheGuan.setDrivingDealCheGuanid(serverService.getServerID("drivingDealCheGuan"));
				parameter = "添加车管信息";
			}
			else
			{
				parameter = "修改车管信息";
			}
			String[] hql3={"from Staff where staffID=?"} ;
			drivingDealCheGuan.setCompanyid(account.getCompanyID());
			Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql3[0], new Object[]{drivingDealCheGuan.getStaffID()});
			drivingDealCheGuan.setStaffName(staff.getStaffName());
			Staff staff1=(Staff) baseBeanService.getBeanByHqlAndParams(hql3[0], new Object[]{account.getStaffID()});
			if(staff1!=null){
				drivingDealCheGuan.setOperationResponsibleID(staff1.getStaffID());
				drivingDealCheGuan.setOperationResponsibleName(staff1.getStaffName());
				parameter += "(人员名称:"+staff1.getStaffName()+")";
			}
			drivingDealCheGuan.setInputDate(new Date());
			
			beans.add(drivingDealCheGuan);
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "success";
	}

	// 删除某条地址信息
	public String delDrivingCheGuan() {
		String hql1 = "delete DrivingDealCheGuan where  drivingDealCheGuanid = ?";
		Object[] params = { drivingDealCheGuan.getDrivingDealCheGuanid() };
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{hql1} , params);
		return "success";
	}

	
	/**
	 * 根据单位和登录人查看地址信息列表
	 * 参数：address.getStaffID()｛人员ID｝
	 * 返回：addressList
	 */
	public String getListDrivingCheGuan() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = { drivingDealCheGuan.getStaffID(),account.getCompanyID() };
		
		drivingDealCheGuanList = baseBeanService.getListBeanByHqlAndParams(
				" from DrivingDealCheGuan where staffID = ? and companyid=? order by inputDate desc", params);
		
		return "cheguan_list";
	}
	
	/**
	 * 导出地址
	 * @return
	 */
	public String showDrivingCheGuan() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Map<String, String> map = new HashMap<String, String>();
		addressTypelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode201004233ern4m24yx0000000258");
		for (CCode b : addressTypelist) {
			map.put(b.getCodeID(),b.getCodeValue());
		}
		StaffAddress.setOMap(map);
		Object[] params = { drivingDealCheGuan.getStaffID() };
		list = baseBeanService.getListBeanByHqlAndParams(
						" from DrivingDealCheGuan where staffID = ? order by inputDate desc",
						params);
		excelStream = excelService.showExcel(DrivingDealCheGuan.columnHeadings(),list);
		CLogBook logBook = logBookService.saveCLogBook(null,"导出地址", account);
		baseBeanService.update(logBook);
		return "showexcel";
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
	

	
}
