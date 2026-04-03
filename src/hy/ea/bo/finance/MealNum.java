package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 收付码商城总订单
 * @author mz
 *
 */
public class MealNum implements BaseBean{
	
	private String mnkey;
	private String mnId;
	private String companyID;//
	private int mealNum;//序号


	public String getMnkey() {
		return mnkey;
	}

	public void setMnkey(String mnkey) {
		this.mnkey = mnkey;
	}

	public String getMnId() {
		return mnId;
	}

	public void setMnId(String mnId) {
		this.mnId = mnId;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public int getMealNum() {
		return mealNum;
	}

	public void setMealNum(int mealNum) {
		this.mealNum = mealNum;
	}


}
