package mobile.tiantai.android.action;

import hy.ea.bo.Company;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
@Scope("prototype")
public class shengweiAction {
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private List<BaseBean> courseList;
	private String weidianType;
	private String courseId;
	private Staff staff;
	private ContactRelation conRelation;
	private StaffAddress address;
	private List<BaseBean> beans;
	private String companyid;
	private String weidiantype;
	private String enroll;
	/**
	 * 获取微店产品列表
	 * 根据id获取一个产品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ListCourse(){	
		String sql="select pp.ppid,pp.goodsname,pp.image,pc.price,pp.certificateCost,pp.companyid " +
				" from dt_productpackaging pp,dt_productpricecategory pc " +
				" where pp.ppid=pc.ppid and pc.category='零售价' and  pp.showweixin='01' and pp.weidiantype=? and  pp.companyid=?";
		if(courseId!=null && !"".equals(courseId)){
			sql+=" and pp.ppid=?";
			Object[] params={weidianType,companyid,courseId};
			courseList=baseBeanService.getListBeanBySqlAndParams(sql, params);
			return "getCourseById";
		}else{
			Object[] params={weidianType,companyid};
			courseList=baseBeanService.getListBeanBySqlAndParams(sql, params);
		}
		return "listCourse";
	}
	
	/**
	 * 课程报名
	 * @return
	 */
	public String courseEnroll(){
		String hql="from Company where companyID=?";
		Company com=(Company) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyid });
		String sid=serverService.getServerID("staff");
		if(staff.getStaffID()==null){
			staff.setStaffID(sid);
		}
		staff.setStaffStatus("00");
		staff.setSource(com.getCompanyName());
		String phql = "select count(*) from Staff ";
		int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
		staff.setStaffCode("NO" + pcount);
		staff.setRecordCode("NO" + pcount);
		staff.setVerifyTime(new Date());
		staff.setGroupCompanySn("groupcompany20120523G3VR9PXHZD0000000021");
		conRelation = new ContactRelation();
		conRelation.setRelationID(serverService.getServerID("ContactRelation"));
		conRelation.setCompanyID(companyid);
		conRelation.setStaffID(sid);
		conRelation.setRelation("学员");
		StaffAddress addr=new StaffAddress();
		addr.setAddressID(serverService.getServerID("address"));
		addr.setStaffID(sid);
		addr.setCompanyID(companyid);
		addr.setAddressDetailed(address.getAddressDetailed());
		beans = new ArrayList<BaseBean>();
		beans.add(staff);
		beans.add(conRelation);
		beans.add(addr);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		if(enroll!=null && enroll.equals("enroll")){
			return	ListCourse();
		}else{
			return	getShopGoods();
		}
	}
	/**
	 * 获取微店商品
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getShopGoods(){
		List<Object> parms = new ArrayList<Object>();
		String sql=" select pp.ppid,pp.goodsname,pp.companyid,p.category,p.price,cp.companyname,pp.image,pp.weidiantype,cp.companyid" +
				" from dt_productpackaging pp " +
				" left join dtcompany cp on cp.companyid=pp.companyid " +
				" left join dt_productPriceCategory p on p.ppid=pp.ppid " +
				" where p.category='零售价' and pp.showweixin='01' and cp.showwechat='01'";
		
		if(weidiantype!=null && !"".equals(weidiantype)){
			sql+=" and pp.weidiantype=?";
			parms.add(weidiantype);
		}
		if(companyid != null && !"".equals(companyid)){
			sql+=" and cp.companyid=?";
			parms.add(companyid);
		}
		sql+=" order by pp.packagingdate desc";
		courseList=baseBeanService.getListBeanBySqlAndParams(sql, parms.toArray());
		if(companyid != null && !"".equals(companyid)){
			return "getCompanyGoods";
		}else{
			
			return "getShopGoods";
		}
		
	}
	public List<BaseBean> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<BaseBean> courseList) {
		this.courseList = courseList;
	}

	public String getWeidianType() {
		return weidianType;
	}

	public void setWeidianType(String weidianType) {
		this.weidianType = weidianType;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public Staff getStaff() {
		return staff;
	}
	public void setStaff(Staff staff) {
		this.staff = staff;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public String getWeidiantype() {
		return weidiantype;
	}

	public void setWeidiantype(String weidiantype) {
		this.weidiantype = weidiantype;
	}

	public String getEnroll() {
		return enroll;
	}

	public void setEnroll(String enroll) {
		this.enroll = enroll;
	}

	public StaffAddress getAddress() {
		return address;
	}

	public void setAddress(StaffAddress address) {
		this.address = address;
	}
	
}
