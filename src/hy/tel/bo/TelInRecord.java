package hy.tel.bo;  

import hy.ea.bo.ExcelBean;
import hy.ea.bo.human.Staff;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TelInRecord  implements BaseBean,ExcelBean{ 
	
     
     private String id;
     private String customName;
     private String telNumber;
     private String customType;
     private String customId;
     private String recordType;
     private String recordContent; 
     private Staff user;
     private Date recodeDate;
     private long isDeal;
     private String dealUser;
     private Date dealDate;
     private String dealContent;
     private long isDel;  
     private String company;

     private Date beginTime;
     private Date endTime;
     private String savePath;
     private String telcodeType;//电话号码类型；座机0手机1
     private Set<TelInRecordDeal> telInRecordDeals = new HashSet<TelInRecordDeal>(0);
     
     public static String[] columnHeadings() {
 		String[] titles = { "序号", "呼叫类型","访问类型","访问内容","访问时间"};
 		return titles;
 	}

 	@Override
 	public String[] properties() {
 		String[] properties = { };
 		return properties;
 	}
 	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getTelNumber() {
		return telNumber;
	}
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	public String getCustomType() {
		return customType;
	}
	public void setCustomType(String customType) {
		this.customType = customType;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getRecordContent() {
		return recordContent;
	}
	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	} 
	
	public Staff getUser() {
		return user;
	}
	public void setUser(Staff user) {
		this.user = user;
	}
	public Date getRecodeDate() {
		return recodeDate;
	}
	public void setRecodeDate(Date recodeDate) {
		this.recodeDate = recodeDate;
	}
	 
	public long getIsDel() {
		return isDel;
	}
	public void setIsDel(long isDel) {
		this.isDel = isDel;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public Set<TelInRecordDeal> getTelInRecordDeals() {
        return this.telInRecordDeals;
    }
    
    public void setTelInRecordDeals(Set<TelInRecordDeal> telInRecordDeals) {
        this.telInRecordDeals = telInRecordDeals;
    }

	public long getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(long isDeal) {
		this.isDeal = isDeal;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealContent() {
		return dealContent;
	}

	public void setDealContent(String dealContent) {
		this.dealContent = dealContent;
	}

	public String getTelcodeType() {
		return telcodeType;
	}

	public void setTelcodeType(String telcodeType) {
		this.telcodeType = telcodeType;
	}
    
}