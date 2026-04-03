package hy.ea.bo.office;

import java.io.File;
import java.util.Date;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 安全检查项
 * @author Administrator ZC
 *
 */
public class OASafeInspectItem implements BaseBean,ExcelBean {
	private String key; 
	private String id;//检查项ID
	private String refid;//安全单据ID 外键 DT_OA_SAFE_INSPECT_INFO（ID）
	private Date   operationDate;//日期
	private Date   startDate;//检查起时间
	private Date   endStart;//检查止时间
	private String barCode;//条码号(争议)
	private String inspectItemno;//检查项编号
	private String inspectAddress;//检查地点
	private String inspectTarget;//监察对象
	private String inspectName;//检查名称
	private String safeTypeID;//安全检查类别序号 外键 DT_OA_SAFEKIND（ID）
	private String inspectType;//检查项目
	private String inspectResult;//检查结果
	private String attachMent;//检查结果附件存储路径
	private Boolean isRemind;//数据提醒
	private String moblle;//手机号
	private String message;//短信息
	private String inspectOrremark;//检查人意见
	private String ManagerRemark;//主管检查意见
	private String comments;//备注
	
	private File    photo;
	private String  photoContentType;
	
	
	public static String[] columnHeadings() {
		String[] titles = {""};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = { };
		return properties;
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

	public String getRefid() {
		return refid;
	}

	public void setRefid(String refid) {
		this.refid = refid;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndStart() {
		return endStart;
	}

	public void setEndStart(Date endStart) {
		this.endStart = endStart;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getInspectItemno() {
		return inspectItemno;
	}

	public void setInspectItemno(String inspectItemno) {
		this.inspectItemno = inspectItemno;
	}

	public String getInspectAddress() {
		return inspectAddress;
	}

	public void setInspectAddress(String inspectAddress) {
		this.inspectAddress = inspectAddress;
	}

	public String getInspectTarget() {
		return inspectTarget;
	}

	public void setInspectTarget(String inspectTarget) {
		this.inspectTarget = inspectTarget;
	}

	public String getInspectName() {
		return inspectName;
	}

	public void setInspectName(String inspectName) {
		this.inspectName = inspectName;
	}

	public String getSafeTypeID() {
		return safeTypeID;
	}

	public void setSafeTypeID(String safeTypeID) {
		this.safeTypeID = safeTypeID;
	}

	public String getInspectType() {
		return inspectType;
	}

	public void setInspectType(String inspectType) {
		this.inspectType = inspectType;
	}

	public String getInspectResult() {
		return inspectResult;
	}

	public void setInspectResult(String inspectResult) {
		this.inspectResult = inspectResult;
	}

	public String getAttachMent() {
		return attachMent;
	}

	public void setAttachMent(String attachMent) {
		this.attachMent = attachMent;
	}

	public Boolean getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(Boolean isRemind) {
		this.isRemind = isRemind;
	}

	public String getMoblle() {
		return moblle;
	}

	public void setMoblle(String moblle) {
		this.moblle = moblle;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInspectOrremark() {
		return inspectOrremark;
	}

	public void setInspectOrremark(String inspectOrremark) {
		this.inspectOrremark = inspectOrremark;
	}

	public String getManagerRemark() {
		return ManagerRemark;
	}

	public void setManagerRemark(String managerRemark) {
		ManagerRemark = managerRemark;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	
	
	
	
	
}
