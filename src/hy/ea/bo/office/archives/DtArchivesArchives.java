package hy.ea.bo.office.archives;

import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DtArchivesArchives entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DtArchivesArchives implements java.io.Serializable, BaseBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8047900990351492693L;
	private String archiveskey;
	private DtArchivesCataloguearchives dtArchivesCataloguearchives;
	private String archiveCode;
	private String archivesid;
	private String companyid;
	private String barcode;
	private String chipid;
	private String name;
	private String securitylevel;
	private String remark;
	private String creatuser;
	private Date createdate;
	private String modifyuser;
	private Date modifydate;
	private Date startValidity;
	private Date endValidity;
    //人事合同专有属性
	private String staffID;// 用于人事合同档案是哪个员工的\
	private String contractCode;//合同编号
	private Date contractSignDate;//合同签订日期
	private Date relieveContractDate;//解除、终止合同日期
	private Date renewalDate;//续签，终止时间；
	
	private String attach;//是否有附件0无；1:有；
	
	@SuppressWarnings("rawtypes")
	private Set dtArchivesArchiveshistories = new HashSet(0);
	@SuppressWarnings("rawtypes")
	private Set dtArchivesAttachments = new HashSet(0);

	private String obsoletestatus;// 作废状态00正常，01作废

	// Constructors

	public String getObsoletestatus() {
		return obsoletestatus;
	}

	public void setObsoletestatus(String obsoletestatus) {
		this.obsoletestatus = obsoletestatus;
	}

	/** default constructor */
	public DtArchivesArchives() {
	}

	/** minimal constructor */
	public DtArchivesArchives(String archiveskey,
			DtArchivesCataloguearchives dtArchivesCataloguearchives,
			String archivesid, String companyid, String name, String creatuser,
			Date createdate) {
		this.archiveskey = archiveskey;
		this.dtArchivesCataloguearchives = dtArchivesCataloguearchives;
		this.archivesid = archivesid;
		this.companyid = companyid;
		this.name = name;
		this.creatuser = creatuser;
		this.createdate = createdate;
	}

	/** full constructor */
	@SuppressWarnings("rawtypes")
	public DtArchivesArchives(String archiveskey,
			DtArchivesCataloguearchives dtArchivesCataloguearchives,
			String archivesid, String companyid, String barcode, String chipid,
			String name, String securitylevel, String remark, String creatuser,
			Date createdate, String modifyuser, Date modifydate,
			Set dtArchivesArchiveshistories, Set dtArchivesAttachments) {
		this.archiveskey = archiveskey;
		this.dtArchivesCataloguearchives = dtArchivesCataloguearchives;
		this.archivesid = archivesid;
		this.companyid = companyid;
		this.barcode = barcode;
		this.chipid = chipid;
		this.name = name;
		this.securitylevel = securitylevel;
		this.remark = remark;
		this.creatuser = creatuser;
		this.createdate = createdate;
		this.modifyuser = modifyuser;
		this.modifydate = modifydate;
		this.dtArchivesArchiveshistories = dtArchivesArchiveshistories;
		this.dtArchivesAttachments = dtArchivesAttachments;
	}

	// Property accessors

	public String getArchiveskey() {
		return this.archiveskey;
	}

	public void setArchiveskey(String archiveskey) {
		this.archiveskey = archiveskey;
	}

	public DtArchivesCataloguearchives getDtArchivesCataloguearchives() {
		return this.dtArchivesCataloguearchives;
	}

	public void setDtArchivesCataloguearchives(
			DtArchivesCataloguearchives dtArchivesCataloguearchives) {
		this.dtArchivesCataloguearchives = dtArchivesCataloguearchives;
	}

	public String getArchivesid() {
		return this.archivesid;
	}

	public void setArchivesid(String archivesid) {
		this.archivesid = archivesid;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getChipid() {
		return this.chipid;
	}

	public void setChipid(String chipid) {
		this.chipid = chipid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecuritylevel() {
		return this.securitylevel;
	}

	public void setSecuritylevel(String securitylevel) {
		this.securitylevel = securitylevel;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreatuser() {
		return this.creatuser;
	}

	public void setCreatuser(String creatuser) {
		this.creatuser = creatuser;
	}

	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getModifyuser() {
		return this.modifyuser;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public Date getModifydate() {
		return this.modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	@SuppressWarnings("rawtypes")
	public Set getDtArchivesArchiveshistories() {
		return this.dtArchivesArchiveshistories;
	}

	@SuppressWarnings("rawtypes")
	public void setDtArchivesArchiveshistories(Set dtArchivesArchiveshistories) {
		this.dtArchivesArchiveshistories = dtArchivesArchiveshistories;
	}

	@SuppressWarnings("rawtypes")
	public Set getDtArchivesAttachments() {
		return this.dtArchivesAttachments;
	}

	@SuppressWarnings("rawtypes")
	public void setDtArchivesAttachments(Set dtArchivesAttachments) {
		this.dtArchivesAttachments = dtArchivesAttachments;
	}

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	/**
	 * 
	 * 用于传值的内部类bo
	 * 
	 * @author mz
	 * 
	 */
	public static class ArchiveTemp implements BaseBean ,java.io.Serializable{
		private String key;
		private String id;
		private String archivesid;
		private String historyID;// 历史记录ID
		private String archiveCode;
		private String name;
		private String barcode;
		private String chipid;
		private String securitylevel;
		private String startDate;// 有效期
		private String endDate;
		private String creator;// 档案创建人
		private String catalogue;
		private String location;
		private String attach;
		private String filesize;
		private String companyID;
		private String inuser;
		private String inusername;
		private Date intime;
		private String intimestr;
		private String outuser;
		private String outusername;
		private Date outtime;
		private String outtimestr;
		private String state;

		private String obsoletestatus;
		
		
		
		// 只为赋值方便
		private String organization;
		private String companyName;
		private String catemodule;
        private String staffName;
		
		private String staffID;
		private String contractCode;//合同编号
		private String contractSignDate;//合同签订日期
		private String renewalDate;//续签，终止时间;
		
		


		public String getRenewalDate() {
			return renewalDate;
		}

		public void setRenewalDate(String renewalDate) {
			this.renewalDate = renewalDate;
		}

		public String getContractCode() {
			return contractCode;
		}

		public void setContractCode(String contractCode) {
			this.contractCode = contractCode;
		}

		public String getContractSignDate() {
			return contractSignDate;
		}

		public void setContractSignDate(String contractSignDate) {
			this.contractSignDate = contractSignDate;
		}

		public String getStaffID() {
			return staffID;
		}

		public void setStaffID(String staffID) {
			this.staffID = staffID;
		}

		public String getCatemodule() {
			return catemodule;
		}

		public void setCatemodule(String catemodule) {
			this.catemodule = catemodule;
		}

		public String getCompanyName() {
			return companyName;
		}

		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}

		public String getInuser() {
			return inuser;
		}

		public void setInuser(String inuser) {
			this.inuser = inuser;
		}

		public Date getIntime() {
			return intime;
		}

		public void setIntime(Date intime) {
			this.intime = intime;
		}

		public String getOutuser() {
			return outuser;
		}

		public void setOutuser(String outuser) {
			this.outuser = outuser;
		}

		public Date getOuttime() {
			return outtime;
		}

		public void setOuttime(Date outtime) {
			this.outtime = outtime;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCompanyID() {
			return companyID;
		}

		public void setCompanyID(String companyID) {
			this.companyID = companyID;
		}

		public String getFilesize() {
			return filesize;
		}

		public void setFilesize(String filesize) {
			this.filesize = filesize;
		}

		public String getArchiveCode() {
			return archiveCode;
		}

		public void setArchiveCode(String archiveCode) {
			this.archiveCode = archiveCode;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getBarcode() {
			return barcode;
		}

		public void setBarcode(String barcode) {
			this.barcode = barcode;
		}

		public String getChipid() {
			return chipid;
		}

		public void setChipid(String chipid) {
			this.chipid = chipid;
		}

		public String getSecuritylevel() {
			return securitylevel;
		}

		public void setSecuritylevel(String securitylevel) {
			this.securitylevel = securitylevel;
		}

		public String getCatalogue() {
			return catalogue;
		}

		public void setCatalogue(String catalogue) {
			this.catalogue = catalogue;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getAttach() {
			return attach;
		}

		public void setAttach(String attach) {
			this.attach = attach;
		}

		public String getHistoryID() {
			return historyID;
		}

		public void setHistoryID(String historyID) {
			this.historyID = historyID;
		}

		public String getArchivesid() {
			return archivesid;
		}

		public void setArchivesid(String archivesid) {
			this.archivesid = archivesid;
		}

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

		public String getOuttimestr() {
			return outtimestr;
		}

		public void setOuttimestr(String outtimestr) {
			this.outtimestr = outtimestr;
		}

		public String getOrganization() {
			return organization;
		}

		public void setOrganization(String organization) {
			this.organization = organization;
		}

		public String getIntimestr() {
			return intimestr;
		}

		public void setIntimestr(String intimestr) {
			this.intimestr = intimestr;
		}

		public String getObsoletestatus() {
			return obsoletestatus;
		}

		public void setObsoletestatus(String obsoletestatus) {
			this.obsoletestatus = obsoletestatus;
		}

		public String getInusername() {
			return inusername;
		}

		public void setInusername(String inusername) {
			this.inusername = inusername;
		}

		public String getOutusername() {
			return outusername;
		}

		public void setOutusername(String outusername) {
			this.outusername = outusername;
		}

		public String getCreator() {
			return creator;
		}

		public void setCreator(String creator) {
			this.creator = creator;
		}

		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getStaffName() {
			return staffName;
		}

		public void setStaffName(String staffName) {
			this.staffName = staffName;
		}

	}

	public Date getStartValidity() {
		return startValidity;
	}

	public void setStartValidity(Date startValidity) {
		this.startValidity = startValidity;
	}

	public Date getEndValidity() {
		return endValidity;
	}

	public void setEndValidity(Date endValidity) {
		this.endValidity = endValidity;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Date getContractSignDate() {
		return contractSignDate;
	}

	public void setContractSignDate(Date contractSignDate) {
		this.contractSignDate = contractSignDate;
	}

	public Date getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}

	public Date getRelieveContractDate() {
		return relieveContractDate;
	}

	public void setRelieveContractDate(Date relieveContractDate) {
		this.relieveContractDate = relieveContractDate;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}



}