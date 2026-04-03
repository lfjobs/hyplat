package hy.ea.bo.finance.BenDis;

import hy.plat.bo.BaseBean;

/*积分表*/
public class BonusPoints implements BaseBean{
	
	private String bonusPointsKey;
	private String bonusPointsId;//积分id
	private String sccid;//人员ssccid
	private String bonusPointScore;//积分总数
			
	
	public String getBonusPointsKey() {
		return bonusPointsKey;
	}
	public void setBonusPointsKey(String bonusPointsKey) {
		this.bonusPointsKey = bonusPointsKey;
	}
	public String getBonusPointsId() {
		return bonusPointsId;
	}
	public void setBonusPointsId(String bonusPointsId) {
		this.bonusPointsId = bonusPointsId;
	}
	public String getSccid() {
		return sccid;
	}
	public void setSccid(String sccid) {
		this.sccid = sccid;
	}
	public String getBonusPointScore() {
		return bonusPointScore;
	}
	public void setBonusPointScore(String bonusPointScore) {
		this.bonusPointScore = bonusPointScore;
	}
	
		
}

