package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/**
 * 离职员工VO
 */
public class COSDimissionStaff implements BaseBean, ExcelBean,java.io.Serializable {
	
	private String companyID;
	private String staffID;
	private String dimissionStaffID;
	private String dimissionStaffKey;
	private Date dimissionDate;         //离职时间
	private String dimissionCause;      //离职原因
	private String issued;              //经手人
	private String dimissionStatus;     //离职状态   01为辞职，02为辞退，03为开除，04为终止
    private String handover;   //工作交接人
    private String contractType; // 合同类型
    private String contractTypeName; // 合同类型名称
    private String dimissionStatusName; //离职状态
    /**
     * 创建时间
     */
    private Date createDate;
	public static String[] columnHeadings() {
		String[] titles = { "序号", "离职时间","离职原因","经手人","离职状态"};
		return titles;
	}

	@Override
	public String[] properties() {
		String[] properties = {String.format("%1$tF", dimissionDate), dimissionCause,issued,dimissionStatus };
		return properties;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getDimissionStaffID() {
		return dimissionStaffID;
	}

	public void setDimissionStaffID(String dimissionStaffID) {
		this.dimissionStaffID = dimissionStaffID;
	}

	public String getDimissionStaffKey() {
		return dimissionStaffKey;
	}

	public void setDimissionStaffKey(String dimissionStaffKey) {
		this.dimissionStaffKey = dimissionStaffKey;
	}

	public Date getDimissionDate() {
		return dimissionDate;
	}

	public void setDimissionDate(Date dimissionDate) {
		this.dimissionDate = dimissionDate;
	}

	public String getDimissionCause() {
		return dimissionCause;
	}

	public void setDimissionCause(String dimissionCause) {
		this.dimissionCause = dimissionCause;
	}

	public String getIssued() {
		return issued;
	}

	public void setIssued(String issued) {
		this.issued = issued;
	}

	public String getDimissionStatus() {
		return dimissionStatus;
	}

	public void setDimissionStatus(String dimissionStatus) {
		this.dimissionStatus = dimissionStatus;
	}

    public String getHandover() {
        return handover;
    }

    public void setHandover(String handover) {
        this.handover = handover;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public String getDimissionStatusName() {
        return dimissionStatusName;
    }

    public void setDimissionStatusName(String dimissionStatusName) {
        this.dimissionStatusName = dimissionStatusName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
