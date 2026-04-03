package hy.ea.bo.human;

import hy.plat.bo.BaseBean;
/**
 * 证件照片信息    zj
 */
public class StaffPapers implements BaseBean {
	private	String papersKey;		
	private	String papersID;
	private	String category;		//证件类别   001：身份证  002：健康证  003：结婚证 
					//004：驾驶证 005：工作证  006：毕业证  007：学生证
	
	private	String positive;			//证件正面照片路径
	private	String back;				//证件背面照片路径
	private	String staffID;			//归属人ID
	
	public StaffPapers() {
		super();
	}
	public StaffPapers(String papersID,String positive,String back) {
		super();
		this.positive=positive;
		this.back=back;
		this.papersID=papersID;
	}
	public StaffPapers(String papersKey, String papersID, String category,
			String positive, String back, String staffID) {
		super();
		this.papersKey = papersKey;
		this.papersID = papersID;
		this.category = category;
		this.positive = positive;
		this.back = back;
		this.staffID = staffID;
	}
	public String getPapersKey() {
		return papersKey;
	}
	public void setPapersKey(String papersKey) {
		this.papersKey = papersKey;
	}
	public String getPapersID() {
		return papersID;
	}
	public void setPapersID(String papersID) {
		this.papersID = papersID;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getPositive() {
		return positive;
	}
	public void setPositive(String positive) {
		this.positive = positive;
	}
	public String getBack() {
		return back;
	}
	public void setBack(String back) {
		this.back = back;
	}
	public String getStaffID() {
		return staffID;
	}
	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	
}
