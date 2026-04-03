package hy.ea.bo.human.salary;

import hy.plat.bo.BaseBean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * WageCofJJ entity. @author MyEclipse Persistence Tools
 */
public class WageCofJJ implements BaseBean ,java.io.Serializable{
	// Fields
	private String cofJjKey;
	private String cofJjID;
	private Integer cofSortSn;
	private Date cofJjDate;
	private String goodsName;
	private BigDecimal cjProPrice;
	private String postName; //职务
	private String deppostID;
	private String jjCodeName;
	private String jjCodeID;
	private String unit;
	private Long cjState; //提成模式 0固定 1 比率
	private BigDecimal cjTcxs;
	private String cjAname;
	private Date cjAdate;
	private String cjUname;
	private Date cjUdate;
	private Long confJjState; //0：正常使用 9：停用
	private String companyID;
	private String groupCompanySn;
	private String yyyyCofDate;
	private String cofSortSnT;
	private String cjProPriceT;
	private String cjTcxsT;
	private String cjStateT;
	public String getCofJjKey() {
		return cofJjKey;
	}
	public void setCofJjKey(String cofJjKey) {
		this.cofJjKey = cofJjKey;
	}
	public String getCofJjID() {
		return cofJjID;
	}
	public void setCofJjID(String cofJjID) {
		this.cofJjID = cofJjID;
	}
	public Integer getCofSortSn() {
		return cofSortSn;
	}
	public void setCofSortSn(Integer cofSortSn) {
		this.cofSortSn = cofSortSn;
	}
	public Date getCofJjDate() {
		return cofJjDate;
	}
	public void setCofJjDate(Date cofJjDate) {
		this.cofJjDate = cofJjDate;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public BigDecimal getCjProPrice() {
		return cjProPrice;
	}
	public void setCjProPrice(BigDecimal cjProPrice) {
		this.cjProPrice = cjProPrice;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getDeppostID() {
		return deppostID;
	}
	public void setDeppostID(String deppostID) {
		this.deppostID = deppostID;
	}
	public String getJjCodeName() {
		return jjCodeName;
	}
	public void setJjCodeName(String jjCodeName) {
		this.jjCodeName = jjCodeName;
	}
	public String getJjCodeID() {
		return jjCodeID;
	}
	public void setJjCodeID(String jjCodeID) {
		this.jjCodeID = jjCodeID;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Long getCjState() {
		return cjState;
	}
	public void setCjState(Long cjState) {
		this.cjState = cjState;
	}
	public BigDecimal getCjTcxs() {
		return cjTcxs;
	}
	public void setCjTcxs(BigDecimal cjTcxs) {
		this.cjTcxs = cjTcxs;
	}
	public String getCjAname() {
		return cjAname;
	}
	public void setCjAname(String cjAname) {
		this.cjAname = cjAname;
	}
	public Date getCjAdate() {
		return cjAdate;
	}
	public void setCjAdate(Date cjAdate) {
		this.cjAdate = cjAdate;
	}
	public String getCjUname() {
		return cjUname;
	}
	public void setCjUname(String cjUname) {
		this.cjUname = cjUname;
	}
	public Date getCjUdate() {
		return cjUdate;
	}
	public void setCjUdate(Date cjUdate) {
		this.cjUdate = cjUdate;
	}
	public Long getConfJjState() {
		return confJjState;
	}
	public void setConfJjState(Long confJjState) {
		this.confJjState = confJjState;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getGroupCompanySn() {
		return groupCompanySn;
	}
	public void setGroupCompanySn(String groupCompanySn) {
		this.groupCompanySn = groupCompanySn;
	}
	public String getYyyyCofDate() {
		return yyyyCofDate;
	}
	public void setYyyyCofDate(String yyyyCofDate) {
		this.yyyyCofDate = yyyyCofDate;
	}
	public String getCofSortSnT() {
		return cofSortSnT;
	}
	public void setCofSortSnT(String cofSortSnT) {
		this.cofSortSnT = cofSortSnT;
	}
	public String getCjProPriceT() {
		return cjProPriceT;
	}
	public void setCjProPriceT(String cjProPriceT) {
		this.cjProPriceT = cjProPriceT;
	}
	public String getCjTcxsT() {
		return cjTcxsT;
	}
	public void setCjTcxsT(String cjTcxsT) {
		this.cjTcxsT = cjTcxsT;
	}
	public String getCjStateT() {
		return cjStateT;
	}
	public void setCjStateT(String cjStateT) {
		this.cjStateT = cjStateT;
	}
	


}