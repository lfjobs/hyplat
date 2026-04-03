package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

/**
 *
 * 扫一扫扫出条码后查询的表
 */
public class ScanBarCode implements BaseBean {

	private String key;
	private String id;
	private String barcode;
	private String urls;




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

	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}
}
