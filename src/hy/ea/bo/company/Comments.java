package hy.ea.bo.company;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 评价表：Comments
 * */
public class Comments implements BaseBean{
	private String commekey;
	private String commeID;
	private String cashierBillsID;//订单ID
	private String ppID;//产品ID
	private String csid;//收货单ID
	private String comments;//评价内容
	private String anonymous;//是否为匿名评价00匿名 01:公开
	private String evaluation;//00中评，01好评，02差评
	private String score1;//描述相符 打星
	private String score2;//物流服务 打星
	private String score3;//服务态度 打星
	private String image1;//用户评价上传的图片
	private String image2;
	private String image3;
	private Date comdate;//评价日期
	
	
	public String getCommekey() {
		return commekey;
	}
	public void setCommekey(String commekey) {
		this.commekey = commekey;
	}
	public String getCommeID() {
		return commeID;
	}
	public void setCommeID(String commeID) {
		this.commeID = commeID;
	}
	public String getCashierBillsID() {
		return cashierBillsID;
	}
	public void setCashierBillsID(String cashierBillsID) {
		this.cashierBillsID = cashierBillsID;
	}
	public String getPpID() {
		return ppID;
	}
	public void setPpID(String ppID) {
		this.ppID = ppID;
	}
	public String getCsid() {
		return csid;
	}
	public void setCsid(String csid) {
		this.csid = csid;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public String getScore1() {
		return score1;
	}
	public void setScore1(String score1) {
		this.score1 = score1;
	}
	public String getScore2() {
		return score2;
	}
	public void setScore2(String score2) {
		this.score2 = score2;
	}
	public String getScore3() {
		return score3;
	}
	public void setScore3(String score3) {
		this.score3 = score3;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	public Date getComdate() {
		return comdate;
	}
	public void setComdate(Date comdate) {
		this.comdate = comdate;
	}
	
	
	
}
