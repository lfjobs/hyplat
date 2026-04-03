package hy.ea.bo.office;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

public class CertificateaTable implements BaseBean, ExcelBean,java.io.Serializable {
/*
 * 车辆证件子集表
 * */
	private String certifikey;
	private String certifiID;
	private String carID;
	private String companyID ;
	private String organizationID;
	private String certificateaName;//证件及资料名称
	private Date receivedate;//发证日期
	private Date effectivedate;//有效日期
	private String certificateaNumber;//证件号
	private String giveorgan;//发证机关
	private String certificateaISBN;//证件资料文号	
	private String copies;//有无复印件
	private String verifierPeople;//审核人
	
	private String carNum;  //车牌号
	private String carType;//车型
	private String engineNum;//发动机编号
	private String frameNumber;//车架号
	private String carPeople;//负责人
	public static String[] columnHeadings() {
		// TODO Auto-generated method stub
		String[] titles={"编号","车牌号","车型","车架号","发动机号","负责人","证件及资料名称","发证日期","有效日期","证件号",
				"发证机关","证件资料文号","有无复印件","编号","审核人"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		String[] properties = {certificateaName,String.format("%1$tF", receivedate),String.format("%1$tF", effectivedate),
				certificateaNumber,giveorgan,certificateaISBN,copies,verifierPeople}; 
		return properties;
	}
	
	
	public String getCertifikey() {
		return certifikey;
	}
	public void setCertifikey(String certifikey) {
		this.certifikey = certifikey;
	}
	public String getCertifiID() {
		return certifiID;
	}
	public void setCertifiID(String certifiID) {
		this.certifiID = certifiID;
	}
	public String getCarID() {
		return carID;
	}
	public void setCarID(String carID) {
		this.carID = carID;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(String organizationID) {
		this.organizationID = organizationID;
	}
	public String getCertificateaName() {
		return certificateaName;
	}
	public void setCertificateaName(String certificateaName) {
		this.certificateaName = certificateaName;
	}
	public Date getReceivedate() {
		return receivedate;
	}
	public void setReceivedate(Date receivedate) {
		this.receivedate = receivedate;
	}
	public Date getEffectivedate() {
		return effectivedate;
	}
	public void setEffectivedate(Date effectivedate) {
		this.effectivedate = effectivedate;
	}
	public String getCertificateaNumber() {
		return certificateaNumber;
	}
	public void setCertificateaNumber(String certificateaNumber) {
		this.certificateaNumber = certificateaNumber;
	}
	public String getGiveorgan() {
		return giveorgan;
	}
	public void setGiveorgan(String giveorgan) {
		this.giveorgan = giveorgan;
	}
	public String getCertificateaISBN() {
		return certificateaISBN;
	}
	public void setCertificateaISBN(String certificateaISBN) {
		this.certificateaISBN = certificateaISBN;
	}
	public String getCopies() {
		return copies;
	}
	public void setCopies(String copies) {
		this.copies = copies;
	}
	public String getVerifierPeople() {
		return verifierPeople;
	}
	public void setVerifierPeople(String verifierPeople) {
		this.verifierPeople = verifierPeople;
	}

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getEngineNum() {
		return engineNum;
	}

	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}

	public String getFrameNumber() {
		return frameNumber;
	}

	public void setFrameNumber(String frameNumber) {
		this.frameNumber = frameNumber;
	}

	public String getCarPeople() {
		return carPeople;
	}

	public void setCarPeople(String carPeople) {
		this.carPeople = carPeople;
	}
	
}
