package hy.ea.bo.human;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

/**
 * 招聘渠道
 * Dtrecruitchannel entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Dtrecruitchannel implements BaseBean,ExcelBean,java.io.Serializable{

	// Fields

	private String recruitchannelkey;
	private String recruitchannelid;
	private String companyID;
	
	private String sorts;              //类别
	private String channelname;        //名称
	private String url;                //网址
	private String address;            //详细地址
	private String fullAddress;        //详细地址名称
	private String tel;                //固定电话
	private String carroute;           //乘车路线
	private String contactman;         //联系人
	private String contactway;         //联系方式
	private String contactdept;        //联系部门
	private String note;               //备注
	
	private String inputtime;          //创建时间
	private String endmodifier;        //最后修改人
	private String endmodifytime;      //最后修改时间
	private String inputer;            //创建人

	public static String[] columnHeadings() {
		String[] titles = { "序号", "类别","名称","网址","详细地址","固定电话","乘车路线","联系人","联系方式","联系部门","备注"};
		return titles;
	}
	
	@Override
	public String[] properties() {
		String[] properties = {sorts,channelname,url,fullAddress,tel,carroute,contactman,contactway,contactdept,note};
		return properties;
	}
	
	/** default constructor */
	public Dtrecruitchannel() {
	}

	/** minimal constructor */
	public Dtrecruitchannel(String recruitchannelid) {
		this.recruitchannelid = recruitchannelid;
	}

	/** full constructor */
	public Dtrecruitchannel(String recruitchannelid, String companyID, String sorts,
			String channelname, String url, String address, String tel,
			String carroute, String contactman, String contactway,
			String contactdept, String note, String inputtime,
			String endmodifier, String endmodifytime, String inputer) {
		this.recruitchannelid = recruitchannelid;
		this.companyID = companyID;
		this.sorts = sorts;
		this.channelname = channelname;
		this.url = url;
		this.address = address;
		this.tel = tel;
		this.carroute = carroute;
		this.contactman = contactman;
		this.contactway = contactway;
		this.contactdept = contactdept;
		this.note = note;
		this.inputtime = inputtime;
		this.endmodifier = endmodifier;
		this.endmodifytime = endmodifytime;
		this.inputer = inputer;
	}

	// Property accessors

	public String getRecruitchannelkey() {
		return this.recruitchannelkey;
	}

	public void setRecruitchannelkey(String recruitchannelkey) {
		this.recruitchannelkey = recruitchannelkey;
	}

	public String getRecruitchannelid() {
		return this.recruitchannelid;
	}

	public void setRecruitchannelid(String recruitchannelid) {
		this.recruitchannelid = recruitchannelid;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getSorts() {
		return this.sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

	public String getChannelname() {
		return this.channelname;
	}

	public void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCarroute() {
		return this.carroute;
	}

	public void setCarroute(String carroute) {
		this.carroute = carroute;
	}

	public String getContactman() {
		return this.contactman;
	}

	public void setContactman(String contactman) {
		this.contactman = contactman;
	}

	public String getContactway() {
		return this.contactway;
	}

	public void setContactway(String contactway) {
		this.contactway = contactway;
	}

	public String getContactdept() {
		return this.contactdept;
	}

	public void setContactdept(String contactdept) {
		this.contactdept = contactdept;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getInputtime() {
		return this.inputtime;
	}

	public void setInputtime(String inputtime) {
		this.inputtime = inputtime;
	}

	public String getEndmodifier() {
		return this.endmodifier;
	}

	public void setEndmodifier(String endmodifier) {
		this.endmodifier = endmodifier;
	}

	public String getEndmodifytime() {
		return this.endmodifytime;
	}

	public void setEndmodifytime(String endmodifytime) {
		this.endmodifytime = endmodifytime;
	}

	public String getInputer() {
		return this.inputer;
	}

	public void setInputer(String inputer) {
		this.inputer = inputer;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
}