package hy.ea.bo.office;
import hy.plat.bo.BaseBean;


public class CardReader implements BaseBean,java.io.Serializable{
	private String cardReaderKey;
	private String code;//读卡器编号
	private String companyID;
	private String orienType;
	private String positionCode;
	private String positionName;
	public String getCardReaderKey() {
		return cardReaderKey;
	}
	public void setCardReaderKey(String cardReaderKey) {
		this.cardReaderKey = cardReaderKey;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrienType() {
		return orienType;
	}
	public void setOrienType(String orienType) {
		this.orienType = orienType;
	}
	public String getPositionCode() {
		return positionCode;
	}
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}


}
