package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;

/**
 * WageCofcl entity. @author MyEclipse Persistence Tools
 */

public class WageCofcl implements BaseBean {

	// Fields

	private String cofClKey;
	private String cofClID;
	private String cofRaID;
	private String typePID;
	private BigDecimal typeMoney;
	
	private String typeMoneyT;
	public String getCofClKey() {
		return cofClKey;
	}
	public void setCofClKey(String cofClKey) {
		this.cofClKey = cofClKey;
	}
	public String getCofClID() {
		return cofClID;
	}
	public void setCofClID(String cofClID) {
		this.cofClID = cofClID;
	}
	public String getCofRaID() {
		return cofRaID;
	}
	public void setCofRaID(String cofRaID) {
		this.cofRaID = cofRaID;
	}
	public String getTypePID() {
		return typePID;
	}
	public void setTypePID(String typePID) {
		this.typePID = typePID;
	}
	public BigDecimal getTypeMoney() {
		return typeMoney;
	}
	public void setTypeMoney(BigDecimal typeMoney) {
		this.typeMoney = typeMoney;
	}
	public String getTypeMoneyT() {
		return typeMoneyT;
	}
	public void setTypeMoneyT(String typeMoneyT) {
		this.typeMoneyT = typeMoneyT;
	}

	
}