package hy.ea.bo.invoicing.voucher;

import hy.plat.bo.BaseBean;

/**
 * 资产负债损益表计算设定表
 * 计算设定
 * @author lou
 *
 */
public class DtInvCcpbsgl implements BaseBean,java.io.Serializable {

	// Fields

	private String ccpKey;
	private String ccpId;
	private String Sequence;//序号
	private String tabSymbol;//报表代号
	private String stContents;//会计科目（报表代号）
	private String comId;//公司id
	private String plaMode;//计算方式（A:加，B:减）
	private String plaType;//计算类别（A:会计科目，B:报表代号）

	// Constructors

	/** default constructor */
	public DtInvCcpbsgl() {
	}

	

	public DtInvCcpbsgl(String ccpKey, String ccpId, String sequence,
			String tabSymbol, String stContents, String comId, String plaMode,
			String plaType) {
		super();
		this.ccpKey = ccpKey;
		this.ccpId = ccpId;
		Sequence = sequence;
		this.tabSymbol = tabSymbol;
		this.stContents = stContents;
		this.comId = comId;
		this.plaMode = plaMode;
		this.plaType = plaType;
	}



	// Property accessors

	public String getCcpKey() {
		return this.ccpKey;
	}

	public void setCcpKey(String ccpKey) {
		this.ccpKey = ccpKey;
	}

	public String getCcpId() {
		return this.ccpId;
	}

	public void setCcpId(String ccpId) {
		this.ccpId = ccpId;
	}

	public String getTabSymbol() {
		return this.tabSymbol;
	}

	public void setTabSymbol(String tabSymbol) {
		this.tabSymbol = tabSymbol;
	}

	public String getStContents() {
		return this.stContents;
	}

	public void setStContents(String stContents) {
		this.stContents = stContents;
	}

	public String getComId() {
		return this.comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getPlaMode() {
		return this.plaMode;
	}

	public void setPlaMode(String plaMode) {
		this.plaMode = plaMode;
	}

	public String getPlaType() {
		return this.plaType;
	}

	public void setPlaType(String plaType) {
		this.plaType = plaType;
	}



	public String getSequence() {
		return Sequence;
	}



	public void setSequence(String sequence) {
		Sequence = sequence;
	}

}