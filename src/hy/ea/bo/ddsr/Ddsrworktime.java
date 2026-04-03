package hy.ea.bo.ddsr;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * Ddsrworktime entity. @author MyEclipse Persistence Tools
 */

public class Ddsrworktime implements BaseBean,java.io.Serializable {

	// Fields

	private String wotiKey;
	private Ddsrcoach ddsrcoach;
	private String wotiCompanyid;
	private Date wotiStrdaydate;
	private Date wotiEnddaydate;
	private Short wotiClass;
	private String wotiStrdate;
	private String wotiEnddate;
	private Date wotiCreatedate;
	private Date wotiUpdatedate;
	private Short wotiType;

	// Constructors

	/** default constructor */
	public Ddsrworktime() {
	}

	/** full constructor */
	public Ddsrworktime(Ddsrcoach ddsrcoach, String wotiCompanyid,
			Short wotiClass, String wotiStrdate, String wotiEnddate,
			Date wotiCreatedate, Date wotiUpdatedate) {
		this.ddsrcoach = ddsrcoach;
		this.wotiCompanyid = wotiCompanyid;
		this.wotiClass = wotiClass;
		this.wotiStrdate = wotiStrdate;
		this.wotiEnddate = wotiEnddate;
		this.wotiCreatedate = wotiCreatedate;
		this.wotiUpdatedate = wotiUpdatedate;
	}

	// Property accessors

	public String getWotiKey() {
		return this.wotiKey;
	}

	public void setWotiKey(String wotiKey) {
		this.wotiKey = wotiKey;
	}

	public Ddsrcoach getDdsrcoach() {
		return this.ddsrcoach;
	}

	public void setDdsrcoach(Ddsrcoach ddsrcoach) {
		this.ddsrcoach = ddsrcoach;
	}

	public String getWotiCompanyid() {
		return this.wotiCompanyid;
	}

	public void setWotiCompanyid(String wotiCompanyid) {
		this.wotiCompanyid = wotiCompanyid;
	}

	public Short getWotiClass() {
		return this.wotiClass;
	}

	public void setWotiClass(Short wotiClass) {
		this.wotiClass = wotiClass;
	}

	public String getWotiStrdate() {
		return this.wotiStrdate;
	}

	public void setWotiStrdate(String wotiStrdate) {
		this.wotiStrdate = wotiStrdate;
	}

	public String getWotiEnddate() {
		return this.wotiEnddate;
	}

	public void setWotiEnddate(String wotiEnddate) {
		this.wotiEnddate = wotiEnddate;
	}

	public Date getWotiCreatedate() {
		return this.wotiCreatedate;
	}

	public void setWotiCreatedate(Date wotiCreatedate) {
		this.wotiCreatedate = wotiCreatedate;
	}

	public Date getWotiUpdatedate() {
		return this.wotiUpdatedate;
	}

	public void setWotiUpdatedate(Date wotiUpdatedate) {
		this.wotiUpdatedate = wotiUpdatedate;
	}

	public Short getWotiType() {
		return wotiType;
	}

	public void setWotiType(Short wotiType) {
		this.wotiType = wotiType;
	}
	
	public Date getWotiStrdaydate() {
		return wotiStrdaydate;
	}

	public void setWotiStrdaydate(Date wotiStrdaydate) {
		this.wotiStrdaydate = wotiStrdaydate;
	}

	public Date getWotiEnddaydate() {
		return wotiEnddaydate;
	}

	public void setWotiEnddaydate(Date wotiEnddaydate) {
		this.wotiEnddaydate = wotiEnddaydate;
	}
}