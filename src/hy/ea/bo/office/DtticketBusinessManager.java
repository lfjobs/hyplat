package hy.ea.bo.office;
import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;

/*票务管理 */
/**
 * DtticketBusinessManager entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class DtticketBusinessManager implements BaseBean,ExcelBean, java.io.Serializable {


    // Fields    

     private String ticketbusinesskey;
     private String ticketbusinessid;
     private String companyId;
     private String staffId; //票务责任人id
     private String staffName;//责任人名称
     private String organizationId;
     private String ticketType;   //票据类型
     private String classOrtrains;//班次/车次
     private String startplace;   // 出发地点
     private String endplace;     // 到达地点
     private Date departure;      //出发日期
     private Date arrivalDate;    //到达日期
     private String departureTime;// 出发时间
     private String arrivalTime;  // 到达时间
     private String normalPrice;  // 标准价
     private String discount;     // 折扣价
     private String airTankLevel; //航舱等级
     private String positionLamp; //席别
     private String billNumber;   //单据号
     private String cname;
     private Date ctime;
     private String uname;
     private Date utime;



    /** default constructor */
    public DtticketBusinessManager() {
    }

    
    /** full constructor */
    public DtticketBusinessManager(String ticketbusinessid, String companyId,String staffId, String staffName, String organizationId, String ticketType, String classOrtrains, String startplace, String endplace, Date departure, Date arrivalDate, String departureTime, String arrivalTime, String normalPrice, String discount, String airTankLevel, String positionLamp, String billNumber, String cname, Date ctime, String uname, Date utime) {
        this.ticketbusinessid = ticketbusinessid;
        this.companyId = companyId;
        this.staffId = staffId;
        this.staffName = staffName;
        this.organizationId = organizationId;
        this.ticketType = ticketType;
        this.classOrtrains = classOrtrains;
        this.startplace = startplace;
        this.endplace = endplace;
        this.departure = departure;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.normalPrice = normalPrice;
        this.discount = discount;
        this.airTankLevel = airTankLevel;
        this.positionLamp = positionLamp;
        this.billNumber = billNumber;
        this.cname = cname;
        this.ctime = ctime;
        this.uname = uname;
        this.utime = utime;
    }

   
    // Property accessors

    public String getTicketbusinesskey() {
        return this.ticketbusinesskey;
    }
    
    public void setTicketbusinesskey(String ticketbusinesskey) {
        this.ticketbusinesskey = ticketbusinesskey;
    }

    public String getTicketbusinessid() {
        return this.ticketbusinessid;
    }
    
    public void setTicketbusinessid(String ticketbusinessid) {
        this.ticketbusinessid = ticketbusinessid;
    }

    public String getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrganizationId() {
        return this.organizationId;
    }
    
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getTicketType() {
        return this.ticketType;
    }
    
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

 

    public String getClassOrtrains() {
		return classOrtrains;
	}


	public void setClassOrtrains(String classOrtrains) {
		this.classOrtrains = classOrtrains;
	}


	public String getStartplace() {
        return this.startplace;
    }
    
    public void setStartplace(String startplace) {
        this.startplace = startplace;
    }

    public String getEndplace() {
        return this.endplace;
    }
    
    public void setEndplace(String endplace) {
        this.endplace = endplace;
    }

    public Date getDeparture() {
        return this.departure;
    }
    
    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getArrivalDate() {
        return this.arrivalDate;
    }
    
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureTime() {
        return this.departureTime;
    }
    
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return this.arrivalTime;
    }
    
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getNormalPrice() {
        return this.normalPrice;
    }
    
    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAirTankLevel() {
        return this.airTankLevel;
    }
    
    public void setAirTankLevel(String airTankLevel) {
        this.airTankLevel = airTankLevel;
    }

    public String getPositionLamp() {
        return this.positionLamp;
    }
    
    public void setPositionLamp(String positionLamp) {
        this.positionLamp = positionLamp;
    }

    public String getBillNumber() {
        return this.billNumber;
    }
    
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getCname() {
        return this.cname;
    }
    
    public void setCname(String cname) {
        this.cname = cname;
    }

    public Date getCtime() {
        return this.ctime;
    }
    
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getUname() {
        return this.uname;
    }
    
    public void setUname(String uname) {
        this.uname = uname;
    }

    public Date getUtime() {
        return this.utime;
    }
    
    public void setUtime(Date utime) {
        this.utime = utime;
    }


	public String getStaffId() {
		return staffId;
	}


	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}


	public String getStaffName() {
		return staffName;
	}


	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public static String[] columnHeadings() {
		String[] titles = { "序号", "票据类型", "班次/车次", "出发地点", "到达地点", "出发日期","到达日期", "出发时间", "到达时间 ", "标准价","折扣价","航舱等级","席别号","票据号"};
		return titles;
	}
	@Override
	public String[] properties() {
		String[] properties = { ticketType,classOrtrains,startplace,endplace, String.format("%1$tF", departure),String.format("%1$tF", arrivalDate),departureTime, arrivalTime,normalPrice,discount,airTankLevel,positionLamp,billNumber};
		return properties;
	}








}