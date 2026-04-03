package hy.ea.bo.production.drive;


import hy.plat.bo.BaseBean;

/**
 * 
 * 
 * 教学日志
 * 
 * @author xgb
 *
 */
public class TeachingContent implements BaseBean {
	private String tctKey;
	private String tctId;
	private String integral;//基本分
	private String ocrId;//教练记录Id
	private String tctContent;//内容
	
	public String getTctKey() {
		return tctKey;
	}
	public void setTctKey(String tctKey) {
		this.tctKey = tctKey;
	}
	public String getTctId() {
		return tctId;
	}
	public void setTctId(String tctId) {
		this.tctId = tctId;
	}
	public String getOcrId() {
		return ocrId;
	}
	public void setOcrId(String ocrId) {
		this.ocrId = ocrId;
	}
	public String getIntegral() {
		return integral;
	}
	public void setIntegral(String integral) {
		this.integral = integral;
	}
	public String getTctContent() {
		return tctContent;
	}
	public void setTctContent(String tctContent) {
		this.tctContent = tctContent;
	}
}