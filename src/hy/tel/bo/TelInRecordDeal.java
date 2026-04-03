package hy.tel.bo; 
 
import hy.plat.bo.BaseBean;

import java.util.Date;

@SuppressWarnings("serial")
public class TelInRecordDeal   implements BaseBean, java.io.Serializable {


    // Fields    

     private String id;
     private TelInRecord telInRecord;
     private TelOutRecord telOutRecord;
     private String dealuser;
     private Date dealdate;
     private String dealcontent;
     private String company;
     private Integer isdeal;
     private String companyname;


    // Constructors

    /** default constructor */
    public TelInRecordDeal() {
    }

	/** minimal constructor */
    public TelInRecordDeal(String id) {
        this.id = id;
    }
    
    /** full constructor */
    public TelInRecordDeal(String id, TelInRecord telInRecord, String dealuser, Date dealdate, String dealcontent, String company, Integer isdeal, String companyname) {
        this.id = id;
        this.telInRecord = telInRecord;
        this.dealuser = dealuser;
        this.dealdate = dealdate;
        this.dealcontent = dealcontent;
        this.company = company;
        this.isdeal = isdeal;
        this.companyname = companyname;
    }

   
    // Property accessors

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public TelInRecord getTelInRecord() {
        return this.telInRecord;
    }
    
    public void setTelInRecord(TelInRecord telInRecord) {
        this.telInRecord = telInRecord;
    }

    public String getDealuser() {
        return this.dealuser;
    }
    
    public void setDealuser(String dealuser) {
        this.dealuser = dealuser;
    }

    public Date getDealdate() {
        return this.dealdate;
    }
    
    public void setDealdate(Date dealdate) {
        this.dealdate = dealdate;
    }

    public String getDealcontent() {
        return this.dealcontent;
    }
    
    public void setDealcontent(String dealcontent) {
        this.dealcontent = dealcontent;
    }

    public String getCompany() {
        return this.company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getIsdeal() {
        return this.isdeal;
    }
    
    public void setIsdeal(Integer isdeal) {
        this.isdeal = isdeal;
    }

    public String getCompanyname() {
        return this.companyname;
    }
    
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

	public TelOutRecord getTelOutRecord() {
		return telOutRecord;
	}

	public void setTelOutRecord(TelOutRecord telOutRecord) {
		this.telOutRecord = telOutRecord;
	}

}