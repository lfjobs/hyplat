/*
 *北京天太世统科技有限公司 010-64164005 
 *author：zg。email：longsky_03@sina.com
 */

package hy.ea.bo;

import java.io.Serializable;

import hy.plat.bo.BaseBean;

/**
 * @author zg longsky_03@sina.com
 * @version 1.0
 * @since 1.0
 */

@SuppressWarnings("serial")
public class OrgModal implements Serializable,BaseBean {
    

	private String orgModalKey;
	private String orgModalId;
	private String orgnizationId;
	private String modalId;
	private String companyId;
	public String getOrgModalKey() {
		return orgModalKey;
	}
	public void setOrgModalKey(String orgModalKey) {
		this.orgModalKey = orgModalKey;
	}
	public String getOrgModalId() {
		return orgModalId;
	}
	public void setOrgModalId(String orgModalId) {
		this.orgModalId = orgModalId;
	}
	public String getOrgnizationId() {
		return orgnizationId;
	}
	public void setOrgnizationId(String orgnizationId) {
		this.orgnizationId = orgnizationId;
	}
	public String getModalId() {
		return modalId;
	}
	public void setModalId(String modalId) {
		this.modalId = modalId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}

