package hy.ea.bo.driving.view;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

/**
 * ViewProgressStudentSubject entity. @author MyEclipse Persistence Tools
 */

public class ViewProgressStudentSubject implements java.io.Serializable,BaseBean,ExcelBean {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2180991666227631722L;
	private String compositekey;
	private String studentid;
	private String studentcode;
	private Date registrationdate;
	private String registrationcarname;
	private Date schoolopendate;
	private String studentname;
	private String studentsex;
	private String studentphone;
	private String studentcard;
	private String barcode;
	private String coachname;
	private String companyid;
	private String docstatus;
	private String complete;
	private String finished;
	private String total;
	
	/**非数据库字段***/
	private Date searchStaDate;
	private Date searchEndDate;
	private String theProgress;
	
	private static List<String> titleList;//用作excel导出 动态表头
    private List<String>	 contentList;
 
	// Constructors
	@Override
	public String[] properties() {
		// TODO Auto-generated method stub
		String[]  properties={String.format("%1$tF", registrationdate),registrationcarname,studentname,studentsex,studentphone,studentcard,coachname,
				finished+"/"+total};
		/*String[] completeArray=complete.split(",");
		String[] propertiesOther=new String[complete.split(",").length];
		for (int i = 0; i < propertiesOther.length; i++) {
			propertiesOther[i]="1".equals(completeArray[i].split("-")[1])?"已完成":"未完成";
		}
		properties=(String[]) ArrayUtils.addAll(properties, propertiesOther);*/
		return properties;
	}
	
	public static String[] columnHeadings(){
		String[] titles = { "序号", "报名时间","报考车型", "学员姓名", "性别", "电话","身份证","教练","完成进度"};
		/*String[] array=new String[titleList.size()];
		String[] completeArray=titleList.toArray(array);
		titles=(String[]) ArrayUtils.addAll(titles, completeArray);*/
		return titles;
	}

	/** default constructor */
	public ViewProgressStudentSubject() {
	}

	/** full constructor */
	public ViewProgressStudentSubject(String studentid, String studentcode,
			Date registrationdate, String registrationcarname,
			Date schoolopendate, String studentname, String studentsex,
			String studentphone, String studentcard, String barcode,
			String coachname, String companyid, String docstatus,
			String complete) {
		this.studentid = studentid;
		this.studentcode = studentcode;
		this.registrationdate = registrationdate;
		this.registrationcarname = registrationcarname;
		this.schoolopendate = schoolopendate;
		this.studentname = studentname;
		this.studentsex = studentsex;
		this.studentphone = studentphone;
		this.studentcard = studentcard;
		this.barcode = barcode;
		this.coachname = coachname;
		this.companyid = companyid;
		this.docstatus = docstatus;
		this.complete = complete;
	}

	// Property accessors

	public String getCompositekey() {
		return this.compositekey;
	}

	public void setCompositekey(String compositekey) {
		this.compositekey = compositekey;
	}

	public String getStudentid() {
		return this.studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getStudentcode() {
		return this.studentcode;
	}

	public void setStudentcode(String studentcode) {
		this.studentcode = studentcode;
	}

	public Date getRegistrationdate() {
		return this.registrationdate;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public String getRegistrationcarname() {
		return this.registrationcarname;
	}

	public void setRegistrationcarname(String registrationcarname) {
		this.registrationcarname = registrationcarname;
	}

	public Date getSchoolopendate() {
		return this.schoolopendate;
	}

	public void setSchoolopendate(Date schoolopendate) {
		this.schoolopendate = schoolopendate;
	}

	public String getStudentname() {
		return this.studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getStudentsex() {
		return this.studentsex;
	}

	public void setStudentsex(String studentsex) {
		this.studentsex = studentsex;
	}

	public String getStudentphone() {
		return this.studentphone;
	}

	public void setStudentphone(String studentphone) {
		this.studentphone = studentphone;
	}

	public String getStudentcard() {
		return this.studentcard;
	}

	public void setStudentcard(String studentcard) {
		this.studentcard = studentcard;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getCoachname() {
		return this.coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getDocstatus() {
		return this.docstatus;
	}

	public void setDocstatus(String docstatus) {
		this.docstatus = docstatus;
	}

	public String getComplete() {
		return this.complete;
	}

	public void setComplete(String complete) {
		this.complete = complete;
	}

	public Date getSearchStaDate() {
		return searchStaDate;
	}

	public void setSearchStaDate(Date searchStaDate) {
		this.searchStaDate = searchStaDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getTheProgress() {
		return theProgress;
	}

	public void setTheProgress(String theProgress) {
		this.theProgress = theProgress;
	}

	public String getFinished() {
		return finished;
	}

	public void setFinished(String finished) {
		this.finished = finished;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public static List<String> getTitleList() {
		return titleList;
	}

	public static void setTitleList(List<String> titleList) {
		ViewProgressStudentSubject.titleList = titleList;
	}

	public List<String> getContentList() {
		return contentList;
	}

	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}
	
}