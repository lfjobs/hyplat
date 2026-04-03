package mobile.tiantai.android.bo;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
/**
 * 
 * 微信活动报名视图
 * @author 
 */

public class DtEnrollVO implements BaseBean, ExcelBean{
	
	private String enrollKey;
	private String enrollId;
	private String staffName;//报名人
	private String reference;//报名人电话
	private String staffIdentityCard;//报名人身份证
	private String wfjShop;//报名店铺
	private String shopOwner;//店主
	private String telephone;//店主电话
	private String staffDesc;//备注
	
	public static String[] columnHeadings() {
		String[] titles = { "序号","姓名","电话","身份证","微分金店","店主","电话", "备注"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = {staffName,reference,staffIdentityCard,wfjShop,shopOwner,telephone,staffDesc};
		return properties;
	}
	
	public String getEnrollKey() {
		return enrollKey;
	}
	public void setEnrollKey(String enrollKey) {
		this.enrollKey = enrollKey;
	}
	public String getEnrollId() {
		return enrollId;
	}
	public void setEnrollId(String enrollId) {
		this.enrollId = enrollId;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getStaffIdentityCard() {
		return staffIdentityCard;
	}
	public void setStaffIdentityCard(String staffIdentityCard) {
		this.staffIdentityCard = staffIdentityCard;
	}
	public String getWfjShop() {
		return wfjShop;
	}
	public void setWfjShop(String wfjShop) {
		this.wfjShop = wfjShop;
	}
	public String getShopOwner() {
		return shopOwner;
	}
	public void setShopOwner(String shopOwner) {
		this.shopOwner = shopOwner;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getStaffDesc() {
		return staffDesc;
	}
	public void setStaffDesc(String staffDesc) {
		this.staffDesc = staffDesc;
	}
}