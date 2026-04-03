package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

/**
 * 凭证类别
 * @author lf
 *
 */

public class DtInvVoutype implements BaseBean {

	// Fields

	private String VTKey;
	private String VTId;
	private String VTComid;//公司id
	private String VTDh;//代号
	private String VTJc;//简称
	private String VTName;//凭证类别名称
	private String VTDs;//借方必有科目
	private String VTCs;//贷方必有科目
	private String VTPd;//预设值
	private String VTR;//备注
	private String VTCid;//新增人员id
	private String VTCname;//新增人员名称
	private String VTCtime;//新增时间
	private String VTUid;//修改人员id
	private String VTUname;//修改人员名称
	private String VTUtime;//修改时间

	// Constructors

	/** default constructor */
	public DtInvVoutype() {
	}

	/** full constructor */
	public DtInvVoutype(String VTId, String VTComid, String VTDh, String VTJc,
			String VTName, String VTDs, String VTCs, String VTPd, String VTR,
			String VTCid, String VTCname, String VTCtime, String VTUid,
			String VTUname, String VTUtime) {
		this.VTId = VTId;
		this.VTComid = VTComid;
		this.VTDh = VTDh;
		this.VTJc = VTJc;
		this.VTName = VTName;
		this.VTDs = VTDs;
		this.VTCs = VTCs;
		this.VTPd = VTPd;
		this.VTR = VTR;
		this.VTCid = VTCid;
		this.VTCname = VTCname;
		this.VTCtime = VTCtime;
		this.VTUid = VTUid;
		this.VTUname = VTUname;
		this.VTUtime = VTUtime;
	}

	// Property accessors

	public String getVTKey() {
		return this.VTKey;
	}

	public void setVTKey(String VTKey) {
		this.VTKey = VTKey;
	}

	public String getVTId() {
		return this.VTId;
	}

	public void setVTId(String VTId) {
		this.VTId = VTId;
	}

	public String getVTComid() {
		return this.VTComid;
	}

	public void setVTComid(String VTComid) {
		this.VTComid = VTComid;
	}

	public String getVTDh() {
		return this.VTDh;
	}

	public void setVTDh(String VTDh) {
		this.VTDh = VTDh;
	}

	public String getVTJc() {
		return this.VTJc;
	}

	public void setVTJc(String VTJc) {
		this.VTJc = VTJc;
	}

	public String getVTName() {
		return this.VTName;
	}

	public void setVTName(String VTName) {
		this.VTName = VTName;
	}

	public String getVTDs() {
		return this.VTDs;
	}

	public void setVTDs(String VTDs) {
		this.VTDs = VTDs;
	}

	public String getVTCs() {
		return this.VTCs;
	}

	public void setVTCs(String VTCs) {
		this.VTCs = VTCs;
	}

	public String getVTPd() {
		return this.VTPd;
	}

	public void setVTPd(String VTPd) {
		this.VTPd = VTPd;
	}

	public String getVTR() {
		return this.VTR;
	}

	public void setVTR(String VTR) {
		this.VTR = VTR;
	}

	public String getVTCid() {
		return this.VTCid;
	}

	public void setVTCid(String VTCid) {
		this.VTCid = VTCid;
	}

	public String getVTCname() {
		return this.VTCname;
	}

	public void setVTCname(String VTCname) {
		this.VTCname = VTCname;
	}

	public String getVTCtime() {
		return this.VTCtime;
	}

	public void setVTCtime(String VTCtime) {
		this.VTCtime = VTCtime;
	}

	public String getVTUid() {
		return this.VTUid;
	}

	public void setVTUid(String VTUid) {
		this.VTUid = VTUid;
	}

	public String getVTUname() {
		return this.VTUname;
	}

	public void setVTUname(String VTUname) {
		this.VTUname = VTUname;
	}

	public String getVTUtime() {
		return this.VTUtime;
	}

	public void setVTUtime(String VTUtime) {
		this.VTUtime = VTUtime;
	}

}