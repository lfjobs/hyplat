package com.tiantai.webservice.bo;
 
import hy.ea.bo.office.archives.DtArchivesCheckvouchs;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 档案盘点辅助类
 * @author Administrator
 *
 */
@XmlRootElement
public class CheckVouchs {
	/**
	 * 芯片号列表
	 */
	private List<String> chips;
	/**
	 * 盘点信息
	 */
	private DtArchivesCheckvouchs checks;
	
	public List<String> getChips() {
		return chips;
	}
	public void setChips(List<String> chips) {
		this.chips = chips;
	}
	public DtArchivesCheckvouchs getChecks() {
		return checks;
	}
	public void setChecks(DtArchivesCheckvouchs checks) {
		this.checks = checks;
	}
}
