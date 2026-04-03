package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class AddressAction {
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
	private StaffAddress address;
	
	private List<BaseBean> addressList;
	
	private List<CCode> addressTypelist;
	private String parameter;
	private Map<String, StaffAddress> addressmap;
	private int pageNumber;
	private List<BaseBean> beans;
	
	/**
	 * 添加或修改地址
	 * 参数：addressmap
	 * 返回：getListAddress()
	 */
	public String saveAddress() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		address = new StaffAddress();
		beans= new ArrayList<BaseBean>();
		if(addressmap!=null){
		for(StaffAddress addresss:addressmap.values()){
			this.address.setStaffID(addresss.getStaffID());
			if (null == addresss.getAddressID() || "".equals(addresss.getAddressID())) {
				addresss.setAddressID(serverService.getServerID("address"));
				parameter = "添加地址";
			}
			else
			{
				parameter = "修改地址";
			}
			String[] hql3={"from Staff where staffID=?"} ;
			addresss.setCompanyID(account.getCompanyID());
			Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql3[0], new Object[]{addresss.getStaffID()});
			parameter += "(人员名称:"+staff.getStaffName()+")";
			beans.add(addresss);
		}
		CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		}
		return "succ";
	}

	// 删除某条地址信息
	public String delAddress() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Object[] params = { address.getStaffID(),address.getAddressID() };
		String hql2="from Staff where staffID=? ";
		Staff staff=(Staff) baseBeanService.getBeanByHqlAndParams(hql2, new Object[]{address.getStaffID()});
		beans = new ArrayList<BaseBean>();
		CLogBook logBook = logBookService.saveCLogBook(null,"删除地址(人员名称："+ staff.getStaffName()+")", account);
		beans.add(logBook);
		String hql1 = "delete StaffAddress where  staffID= ? and addressID = ?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,new String[]{hql1} , params);
		return "succ";
	}

	
	/**
	 * 根据单位和登录人查看地址信息列表
	 * 参数：address.getStaffID()｛人员ID｝
	 * 返回：addressList
	 */
	public String getListAddress() {
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Map<String, String> map = new HashMap<String, String>();
		addressTypelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode201004233ern4m24yx0000000258");
		for (CCode b : addressTypelist) {
			map.put(b.getCodeID(),b.getCodeValue());
		}
		StaffAddress.setOMap(map);
		Object[] params = { address.getStaffID() };
		
		addressList = baseBeanService.getListBeanByHqlAndParams(
				" from StaffAddress where staffID = ? order by addressID desc", params);
		
		return "list";
	}
	
	/**
	 * 导出地址
	 * @return
	 */
	public String showAddressExcel() {
		List<BaseBean> list;
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Map<String, String> map = new HashMap<String, String>();
		addressTypelist = codeService.getCCodeListByPID(account.getCompanyID(),"scode201004233ern4m24yx0000000258");
		for (CCode b : addressTypelist) {
			map.put(b.getCodeID(),b.getCodeValue());
		}
		StaffAddress.setOMap(map);
		Object[] params = { address.getStaffID() };
		list = baseBeanService.getListBeanByHqlAndParams(
						" from StaffAddress where staffID = ? order by addressID desc",
						params);
		excelStream = excelService.showExcel(StaffAddress.columnHeadings(),list);
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

	public Map<String, StaffAddress> getAddressmap() {
		return addressmap;
	}

	public void setAddressmap(Map<String, StaffAddress> addressmap) {
		this.addressmap = addressmap;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public StaffAddress getAddress() {
		return address;
	}

	public void setAddress(StaffAddress address) {
		this.address = address;
	}

	public List<CCode> getAddressTypelist() {
		return addressTypelist;
	}

	public void setAddressTypelist(List<CCode> addressTypelist) {
		this.addressTypelist = addressTypelist;
	}

	public List<BaseBean> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<BaseBean> addressList) {
		this.addressList = addressList;
	}

	
}
