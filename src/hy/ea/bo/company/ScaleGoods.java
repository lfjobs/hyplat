package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

/**
 *
 * 需要用电子秤打条码的
 */
public class ScaleGoods implements BaseBean ,java.io.Serializable{

	private String sgKey;
	private String sgID;
	private String unitOfMeasureCode;//计价单位
	private Integer plu;// 商品号
	private String alternativeItemID;//货号
    private String goodsID;
    private String companyID;

	public String getSgKey() {
		return sgKey;
	}

	public void setSgKey(String sgKey) {
		this.sgKey = sgKey;
	}

	public String getSgID() {
		return sgID;
	}

	public void setSgID(String sgID) {
		this.sgID = sgID;
	}

	public String getUnitOfMeasureCode() {
		return unitOfMeasureCode;
	}

	public void setUnitOfMeasureCode(String unitOfMeasureCode) {
		this.unitOfMeasureCode = unitOfMeasureCode;
	}

	public Integer getPlu() {
		return plu;
	}

	public void setPlu(Integer plu) {
		this.plu = plu;
	}

	public String getAlternativeItemID() {
		return alternativeItemID;
	}

	public void setAlternativeItemID(String alternativeItemID) {
		this.alternativeItemID = alternativeItemID;
	}


	public String getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(String goodsID) {
		this.goodsID = goodsID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	@Override
	public String toString() {
		return "\"ScaleGoods\":{" +
				"\"sgKey\":\"" + sgKey + '\"' +
				", \"sgID\":\"" + sgID + '\"' +
				", \"unitOfMeasureCode\":\"" + unitOfMeasureCode + '\"' +
				", \"plu\":" + plu +
				", \"alternativeItemID\":\"" + alternativeItemID + '\"' +
				", \"goodsID\":\"" + goodsID + '\"' +
				", \"companyID\":\"" + companyID + '\"' +
				"},";
	}
}
