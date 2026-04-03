package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 车辆座位添加判断
 * @author Administrator
 *
 */
public class carSeat implements BaseBean ,ExcelBean{
	private String seatID;
	private String seatKey;
    private String organizationID;
    private String quasiID;//车辆准载座位id
	private String companyID;
	private String seatNum;	//座位号
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getSeatID() {
		return seatID;
	}
	public void setSeatID(String seatID) {
		this.seatID = seatID;
	}
	public String getSeatKey() {
		return seatKey;
	}
	public void setSeatKey(String seatKey) {
		this.seatKey = seatKey;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getQuasiID() {
		return quasiID;
	}
	public void setQuasiID(String quasiID) {
		this.quasiID = quasiID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}
	public String[] getRulesArray(){
		return seatNum.replace(" ", "").split(",");
	}
}
