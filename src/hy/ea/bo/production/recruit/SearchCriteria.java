package hy.ea.bo.production.recruit;



import hy.plat.bo.BaseBean;
/**
 * 
 * 
 * 搜索条件 仅仅实体无数据库
 * @author mz
 *
 */
public class SearchCriteria implements BaseBean {

	private String sckey; // 主键
	private String scId;
	private String keyword;  //搜索输入关键字
	private String industry;  //行业类别
	private String position;  //职位类别
	private String city;  // 城市
	private String district;//区
	private String street;//街道
	
	private String sex; //性别
	private String education; //学历
	private String experience; //工作经验
	private String age;//年龄
	
	private String posType;//职位类型， e.g.全职 ，兼职，实习，
	private String publishDate;//发布时间 e.g. 今天，最近三天，最近一周，最近一个月
	private String comPro;//公司性质 e.g.国企，民营等
	private String comScale;//公司规模 e.g. 20人以下，20-99人等
	private String salary;//月薪  e.g.1千以下，1千-2千
	
	private String sortbycri;//智能排序00   月薪最高01  最新发布02

	
	
	public String getSckey() {
		return sckey;
	}
	public void setSckey(String sckey) {
		this.sckey = sckey;
	}
	public String getScId() {
		return scId;
	}
	public void setScId(String scId) {
		this.scId = scId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getPosType() {
		return posType;
	}
	public void setPosType(String posType) {
		this.posType = posType;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getComPro() {
		return comPro;
	}
	public void setComPro(String comPro) {
		this.comPro = comPro;
	}
	public String getComScale() {
		return comScale;
	}
	public void setComScale(String comScale) {
		this.comScale = comScale;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getSortbycri() {
		return sortbycri;
	}
	public void setSortbycri(String sortbycri) {
		this.sortbycri = sortbycri;
	}
    
    
}