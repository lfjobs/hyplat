package hy.ea.marketing.bo;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * DtCrmCustomermenu entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtCrmCustomermenu implements java.io.Serializable, BaseBean{

	// Fields

	private String customermenukey;
	private String customermenuid;
	private String companyid;
	private String jd;//进度
	private String yxcp;//意向产品
	private String bjjl;//报价记录
	private String jpzl;//竞品资料
	private String shgl;//社会关联
	private String yjsz;//预警设置
	private String lxfs;//联系方式
	private String hyzy;//行业职业
	private String jypx;//教育培训
	private String dzhz;//地址汇总
	private String xqah;//兴趣爱好
	private String zjzs;//证件证书
	private String jntc;//技能特长	

	// Constructors

	/** default constructor */
	public DtCrmCustomermenu() {
	}

	/** full constructor */
	public DtCrmCustomermenu(String customermenuid, String companyid,
			String jd, String yxcp, String bjjl, String jpzl, String shgl,
			String yjsz, String lxfs, String hyzy, String jypx, String dzhz,
			String xqah, String zjzs, String jntc, String cname, Date ctime,
			String uname, Date utime) {
		this.customermenuid = customermenuid;
		this.companyid = companyid;
		this.jd = jd;
		this.yxcp = yxcp;
		this.bjjl = bjjl;
		this.jpzl = jpzl;
		this.shgl = shgl;
		this.yjsz = yjsz;
		this.lxfs = lxfs;
		this.hyzy = hyzy;
		this.jypx = jypx;
		this.dzhz = dzhz;
		this.xqah = xqah;
		this.zjzs = zjzs;
		this.jntc = jntc;		
	}

	// Property accessors

	public String getCustomermenukey() {
		return this.customermenukey;
	}

	public void setCustomermenukey(String customermenukey) {
		this.customermenukey = customermenukey;
	}

	public String getCustomermenuid() {
		return this.customermenuid;
	}

	public void setCustomermenuid(String customermenuid) {
		this.customermenuid = customermenuid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getJd() {
		return this.jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getYxcp() {
		return this.yxcp;
	}

	public void setYxcp(String yxcp) {
		this.yxcp = yxcp;
	}

	public String getBjjl() {
		return this.bjjl;
	}

	public void setBjjl(String bjjl) {
		this.bjjl = bjjl;
	}

	public String getJpzl() {
		return this.jpzl;
	}

	public void setJpzl(String jpzl) {
		this.jpzl = jpzl;
	}

	public String getShgl() {
		return this.shgl;
	}

	public void setShgl(String shgl) {
		this.shgl = shgl;
	}

	public String getYjsz() {
		return this.yjsz;
	}

	public void setYjsz(String yjsz) {
		this.yjsz = yjsz;
	}

	public String getLxfs() {
		return this.lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

	public String getHyzy() {
		return this.hyzy;
	}

	public void setHyzy(String hyzy) {
		this.hyzy = hyzy;
	}

	public String getJypx() {
		return this.jypx;
	}

	public void setJypx(String jypx) {
		this.jypx = jypx;
	}

	public String getDzhz() {
		return this.dzhz;
	}

	public void setDzhz(String dzhz) {
		this.dzhz = dzhz;
	}

	public String getXqah() {
		return this.xqah;
	}

	public void setXqah(String xqah) {
		this.xqah = xqah;
	}

	public String getZjzs() {
		return this.zjzs;
	}

	public void setZjzs(String zjzs) {
		this.zjzs = zjzs;
	}

	public String getJntc() {
		return this.jntc;
	}

	public void setJntc(String jntc) {
		this.jntc = jntc;
	}	

}