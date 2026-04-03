package hy.ea.bo.finance;

import hy.plat.bo.BaseBean;


/**
 * 促销品和购物车关联
 * 
 */
public class  CartShopPromotion implements BaseBean
{
	private String cspKey;
	private String cspId;
	private String cartId;//购物车ID
	private String ppId;//主产品ID
	private String pptId;//促销品ID
	private String ptstandard;//促销品规格C
	private Integer ptCount;//促销品的数量
	private String staffId;//人员ID
	private String posNum;//终端机编号



	public String getCspKey()
	{
		return cspKey;
	}

	public void setCspKey(String cspKey)
	{
		this.cspKey = cspKey;
	}

	public String getCspId()
	{
		return cspId;
	}

	public void setCspId(String cspId)
	{
		this.cspId = cspId;
	}

	public String getCartId()
	{
		return cartId;
	}

	public void setCartId(String cartId)
	{
		this.cartId = cartId;
	}

	public String getPpId() {
		return ppId;
	}

	public void setPpId(String ppId)
	{
		this.ppId = ppId;
	}

	public String getPptId()
	{
		return pptId;
	}

	public void setPptId(String pptId)
	{
		this.pptId = pptId;
	}

	public String getPtstandard()
	{
		return ptstandard;
	}

	public void setPtstandard(String ptstandard)
	{
		this.ptstandard = ptstandard;
	}
	
	public Integer getPtCount() {
		return ptCount;
	}

	public void setPtCount(Integer ptCount) {
		this.ptCount = ptCount;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getPosNum() {
		return posNum;
	}

	public void setPosNum(String posNum) {
		this.posNum = posNum;
	}
}
