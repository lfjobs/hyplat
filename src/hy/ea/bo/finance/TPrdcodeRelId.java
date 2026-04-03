package hy.ea.bo.finance;

/**
 * TPrdcodeRelId entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TPrdcodeRelId implements java.io.Serializable {

	// Fields

	private String ppid;
	private String codeid;

	// Constructors

	/** default constructor */
	public TPrdcodeRelId() {
	}

	/** full constructor */
	public TPrdcodeRelId(String ppid, String codeid) {
		this.ppid = ppid;
		this.codeid = codeid;
	}

	// Property accessors

	public String getPpid() {
		return this.ppid;
	}

	public void setPpid(String ppid) {
		this.ppid = ppid;
	}

	public String getCodeid() {
		return this.codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TPrdcodeRelId))
			return false;
		TPrdcodeRelId castOther = (TPrdcodeRelId) other;

		return ((this.getPpid() == castOther.getPpid()) || (this.getPpid() != null
				&& castOther.getPpid() != null && this.getPpid().equals(
				castOther.getPpid())))
				&& ((this.getCodeid() == castOther.getCodeid()) || (this
						.getCodeid() != null && castOther.getCodeid() != null && this
						.getCodeid().equals(castOther.getCodeid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPpid() == null ? 0 : this.getPpid().hashCode());
		result = 37 * result
				+ (getCodeid() == null ? 0 : this.getCodeid().hashCode());
		return result;
	}

}