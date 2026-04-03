package hy.ea.bo.office.vo;

import hy.plat.bo.BaseBean;

/**
 * 
 * 分配房间下拉框预设视图
 * 
 * @author 李伟志
 * 
 */
public class HotelVO implements BaseBean{

	private String companyname;
	private String hotelname;

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getHotelname() {
		return hotelname;
	}

	public void setHotelname(String hotelname) {
		this.hotelname = hotelname;
	}

}
