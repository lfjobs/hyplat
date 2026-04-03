package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 *
 * 档案条码
 */
public class DocBarCode implements BaseBean {

	private String key;
	private String id;
	private String barcode;
	private String seq;
	private Date createDate;



	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
