package hy.ea.bo.office;

import hy.plat.bo.BaseBean;

public class JzqTemplate implements BaseBean {

	private String jzqtKey;
	private String jzqtId;
	private String companyID;
	private String templateID;//君子签模板ID
	private String scene;//用于区分不同场景的模板
	private String templateName;//模板名称

	public String getJzqtKey() {
		return jzqtKey;
	}

	public void setJzqtKey(String jzqtKey) {
		this.jzqtKey = jzqtKey;
	}

	public String getJzqtId() {
		return jzqtId;
	}

	public void setJzqtId(String jzqtId) {
		this.jzqtId = jzqtId;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getTemplateID() {
		return templateID;
	}

	public void setTemplateID(String templateID) {
		this.templateID = templateID;
	}

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
}
