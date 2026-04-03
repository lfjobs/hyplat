package hy.ea.bo.finance;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 产品价格类型表
 *
 */
public class ProductPriceCategory implements BaseBean,ExcelBean{
	private String pcKey;  //主键
	private String pcID;
	private String ppID;  //产品的业务主键 
	private String category;//价格类型
	private String price;// 单价
	private String money;// 金额

	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		return null;
	}

	

	public String getPcKey() {
		return pcKey;
	}



	public void setPcKey(String pcKey) {
		this.pcKey = pcKey;
	}



	public String getPpID() {
		return ppID;
	}

	public void setPpID(String ppID) {
		this.ppID = ppID;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPcID() {
		return pcID;
	}

	public void setPcID(String pcID) {
		this.pcID = pcID;
	}
	
}
