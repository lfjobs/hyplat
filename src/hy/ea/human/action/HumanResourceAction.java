package hy.ea.human.action;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.CLogBook;
import hy.ea.bo.DrivingSchool.TbJpCar;
import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.DrivingSchool.TbJpTeacher;
import hy.ea.bo.Enroll;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.human.Humanresource;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffAddress;
import hy.ea.service.CCodeService;
import hy.ea.service.CLogBookService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class HumanResourceAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CCodeService codeService;
	@Resource
	private CLogBookService logBookService;
	@Resource
	private UpLoadFileService fileService;

	private List<BaseBean> beans;
	private String parameter;
	private String result;
	// 接收photo文件
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private List<Integer> addormod;  //判断添加或修改标志
	private String showType;//add 添加 edit 修改

	private String sub;//代替token 手动做防止重复提交   存储生成的随机数

	private Staff cstaff;
	private TbJpStudentInfo student;
	private Humanresource humanresource;
	private String relationshipValue;
	private String relationshipValueAll;
	private TbJpCar tbcar;
    private Enroll enroll;
    private String relation;
    private TbJpTeacher tbJpTeacher;

	/**
	 * 获取人事菜单
	 * @return list
	 */
	public String getHumanResource() {
        HttpServletRequest request = ServletActionContext.getRequest();
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		request.setAttribute("company",account.getCompany());
		//加载默认民族
		List<CCode> codeNationList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode20100331mk6yn5b5f60000000006");
		//加载默认籍贯
		List<CCode> codeNativePlaceList = codeService.getCCodeListByPID(account
				.getCompanyID(), "scode2010053143wpua87db0000000008");
		ActionContext.getContext().put("nations", codeNationList);
		ActionContext.getContext().put("nativeplaces", codeNativePlaceList);

		String hql = "from Humanresource where companyid = ?";
		humanresource = (Humanresource)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account.getCompanyID()});

		if(!showType.equals("add")){
			cstaff =(Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID = ? ", new Object[]{cstaff.getStaffID()});
			enroll = (Enroll)baseBeanService.getBeanByHqlAndParams("from Enroll e where e.staffId = ?",new Object[]{cstaff.getStaffID()});
			student = (TbJpStudentInfo)baseBeanService.getBeanByHqlAndParams("from TbJpStudentInfo st where st.staffId = ?",new Object[]{cstaff.getStaffID()});
			tbJpTeacher = (TbJpTeacher)baseBeanService.getBeanByHqlAndParams("from TbJpTeacher t where t.staffId = ?",new Object[]{cstaff.getStaffID()});
			if ("学员".equals(relation)) {
				if (student == null) {
					if (enroll != null) {
						student = new TbJpStudentInfo();
						student.setStaffId(cstaff.getStaffID());
						student.setTrainType(enroll.getLicenceType());
						student.setApplyDate(enroll.getEnrollDate());
						student.setCompanyId(enroll.getCompanyID());
						student.setStudentId(serverService.getServerID("student"));
						if ("".equals(cstaff.getSex()) && cstaff.getSex() == "男") {
							student.setSex("1");
						} else if ("".equals(cstaff.getSex()) && cstaff.getSex() == "女") {
							student.setSex("2");
						}
						student.setName(cstaff.getStaffName());
						student.setCardType("1");
						student.setCardNum(cstaff.getStaffIdentityCard());
						student.setPhone(cstaff.getReference());
						student.setAddress(cstaff.getAddress());

					}
				}
			}
			if(cstaff.getSccid()!=null&&!cstaff.getSccid().equals(""))
			{
                TEshopCusCom t =(TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where sccid=?",new Object[]{cstaff.getSccid()});
                request.setAttribute("tcc",t);
            }
			String hqla = " from ContactRelation where staffID = ? and companyID = ? ";
			List<BaseBean> beans=baseBeanService.getListBeanByHqlAndParams(hqla, new Object[]{cstaff.getStaffID(),account.getCompanyID()});
			relationshipValueAll="";
			for (BaseBean baseBean : beans) {
				ContactRelation contactRelation=(ContactRelation)baseBean;
				if(contactRelation.getRelation()!=null&&!"".endsWith(contactRelation.getRelation())){
					relationshipValueAll+=contactRelation.getRelation()+" , ";
				}

			}
		}
		return "list";
	}

	/**
	 * 保存或修改Staff
	 *
	 * @return
	 */
	public String saveCStaff() {
		String groupCompanySn = ActionContext.getContext().getSession()
				.get("groupCompanySn").toString();
		if (groupCompanySn == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		String hql = "select count(*) from Staff where  staffIdentityCard = ? and groupCompanySn = ? ";
		Object[] params = { cstaff.getStaffIdentityCard(), groupCompanySn };
		beans = new ArrayList<BaseBean>();
		int count = baseBeanService.getConutByByHqlAndParams(hql, params);
		if (cstaff.getStaffID() == null || "".equals(cstaff.getStaffID())) {
			String phql = "select count(*) from Staff ";
			int pcount = baseBeanService.getConutByByHqlAndParams(phql, null);
			cstaff.setStaffCode("NO" + pcount);
			cstaff.setRecordCode("NO" + pcount);
			cstaff.setVerifyTime(new Date());
			cstaff.setStaffID(serverService.getServerID("cstaff"));
			cstaff.setEntryPersonnel(account.getStaffName());
			parameter = "添加员工（人员姓名：:" + cstaff.getStaffName() + ")";
		} else {
			String hql2 = "from Staff where staffID=?";
			Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql2,
					new Object[] { cstaff.getStaffID() });
			cstaff.setVerifyTime(new Date());
			parameter = "修改员工(人员名称:" + staff.getStaffName() + ")";
			if (staff.getStaffIdentityCard() != null
					&& !staff.getStaffIdentityCard().equals(""))
				if (staff.getStaffIdentityCard().equals(
						cstaff.getStaffIdentityCard())) {
					count = 0;
				}
		}

        if ("学员".equals(relation)) {
            //添加学员信息表
            student.setStaffId(cstaff.getStaffID());
            if (student.getStudentId()!=null ||("").equals(student.getStudentId()))
                student.setStudentId(serverService.getServerID("student"));
            student.setCompanyId(account.getCompany().getCompanyID());
            if (cstaff.getBirthday() != null && !"".equals(cstaff.getBirthday())) {
                student.setBrith(Utilities.getDateFromString(cstaff.getBirthday(), "yyyyMMdd"));
            }
            if ("".equals(cstaff.getSex()) && cstaff.getSex() == "男") {
                student.setSex("1");
            } else if ("".equals(cstaff.getSex()) && cstaff.getSex() == "女") {
                student.setSex("2");
            }
            student.setStaffId(cstaff.getStaffID());
            student.setName(cstaff.getStaffName());
            student.setCardType("1");
            student.setCardNum(cstaff.getStaffIdentityCard());
            student.setPhone(cstaff.getReference());
            beans.add(student);
        } else if ("教练".equals(relation)) {
            tbJpTeacher.setStaffId(cstaff.getStaffID());
            tbJpTeacher.setSex("男".equals(cstaff.getSex())?"1":"2");
            tbJpTeacher.setCompanyId(account.getCompanyID() ==null?"":account.getCompanyID().toString());
            tbJpTeacher.setIdcard(cstaff.getStaffIdentityCard());
            tbJpTeacher.setName(cstaff.getStaffName());
            if ( "".equals(tbJpTeacher.getTeacherId()) || tbJpTeacher.getTeacherId()==null){
				tbJpTeacher.setTeacherId(serverService.getServerID("tbJpTeacher"));
			}
            tbJpTeacher.setPhoto(cstaff.getPhoto());
            tbJpTeacher.setMobile(cstaff.getReference());
            beans.add(tbJpTeacher);
        }
		if (count > 0) {
			return "此处为社会人力存在人员";
		}
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("/");
			String photoPath = fileService
					.savePhoto(
							path,
							photoFileName,
							photo,
							account.getCompanyID(),
							"/human/personPhotos/"
									+ Utilities.getDateString(new Date(),
											"yyyy-MM-dd"));
			if (cstaff.getPhoto() != null && !"".equals(cstaff.getPhoto())) {
				ArrayList<String> paths = new ArrayList<String>();
				paths.add(path + cstaff.getPhoto());
				fileService.deletePhotos(paths);
			}
			cstaff.setPhoto(photoPath);
		}
		cstaff.setGroupCompanySn(groupCompanySn);
		cstaff.setStaffStatus("00");
		cstaff.setSource("系统");
		/**
		 * 快捷添加个人往来关系
		 */
		ContactRelation conRelation=(ContactRelation)saveRelation(cstaff);
		if(conRelation!=null){
			beans.add(conRelation);
		}
		/**
		 * 快捷添加地址信息
		 */
		StaffAddress staffAddress=(StaffAddress)saveAddress(cstaff);
		if(staffAddress!=null){
			beans.add(staffAddress);
		}
        /**
         * 添加教练车辆信息
         */
        if(tbcar !=null) {
            if(cstaff.getStaffName() == null) {
                Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID= ? ",
                        new Object[]{cstaff.getStaffID()});
                cstaff.setStaffName(staff.getStaffName());
            }
            TbJpCar tbJpCar = (TbJpCar) baseBeanService.getBeanByHqlAndParams(" from TbJpCar where carnum = ?",new Object[]{tbcar.getCarnum()});
            if(tbJpCar == null){
                tbJpCar=new TbJpCar();
                tbJpCar.setCarnum(tbcar.getCarnum());
                tbJpCar.setLinceDispathDate(tbcar.getLinceDispathDate());
                tbJpCar.setPlateNumber(tbcar.getPlateNumber());
                tbJpCar.setSkillLevel(tbcar.getSkillLevel());
                tbJpCar.setFranum(tbcar.getFranum());
                tbJpCar.setPerdritype(tbcar.getPerdritype());
                tbJpCar.setCreatedate(tbcar.getCreatedate());
                tbJpCar.setUpdatedate(tbcar.getUpdatedate());
                tbJpCar.setJcqk(tbcar.getJcqk());
                tbJpCar.setRemark(tbcar.getRemark());
                tbJpCar.setDizhi(tbcar.getDizhi());
                tbJpCar.setCreateperson(cstaff.getStaffName());
                beans.add(tbJpCar);
            }else {
                tbJpCar.setCarnum(tbcar.getCarnum());
                tbJpCar.setLinceDispathDate(tbcar.getLinceDispathDate());
                tbJpCar.setPlateNumber(tbcar.getPlateNumber());
                tbJpCar.setSkillLevel(tbcar.getSkillLevel());
                tbJpCar.setFranum(tbcar.getFranum());
                tbJpCar.setPerdritype(tbcar.getPerdritype());
                tbJpCar.setCreatedate(tbcar.getCreatedate());
                tbJpCar.setUpdatedate(tbcar.getUpdatedate());
                tbJpCar.setJcqk(tbcar.getJcqk());
                tbJpCar.setRemark(tbcar.getRemark());
                tbJpCar.setDizhi(tbcar.getDizhi());
                tbJpCar.setCreateperson(cstaff.getStaffName());
                baseBeanService.update(tbJpCar);
            }
        }

		/**/
			beans.add(cstaff);
			CLogBook logBook = logBookService.saveCLogBook(null, parameter, account);
			beans.add(logBook);
			try {
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			showType = "edit";
		return getHumanResource();
	}
	/**
	 * 保存往来关系
	 * @return
	 */
	public BaseBean saveRelation(BaseBean baseBean){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		ContactRelation conRelation=null;
		if(relationshipValue!=null&&!"".equals(relationshipValue)){
		String hql = "select count(*) from ContactRelation where staffID = ? and companyID = ? and relation = ?";
		Object[] params = { ((Staff)baseBean).getStaffID(), account.getCompanyID(),
				relationshipValue.trim() };
			int count = baseBeanService.getConutByByHqlAndParams(hql, params);
			if(count==0){
				conRelation=new ContactRelation();
				conRelation.setRelationID(serverService
						.getServerID("contactrelation"));
				conRelation.setCompanyID(account.getCompanyID());
				conRelation.setStaffID(((Staff)baseBean).getStaffID());
				conRelation.setRelation(relationshipValue.trim() );
			}
		}
		return conRelation;
	}

	/**
	 * 保存地址信息
	 * @return
	 */
	public BaseBean saveAddress(BaseBean baseBean){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		Staff staff=(Staff)baseBean;
		StaffAddress staffAddress=null;
		if(staff!=null&&staff.getStaffAddress()!=null&&!"".equals(staff.getStaffAddress().trim())){
		String hql = " from StaffAddress where staffID = ?  and addressID = ?";
		Object[] params = { ((Staff)baseBean).getStaffID(), staff.getAddress()};
			staffAddress = (StaffAddress)baseBeanService.getBeanByHqlAndParams(hql, params);
			if(staffAddress==null){
				staffAddress=new StaffAddress();
				staffAddress.setAddressID(serverService.getServerID("address"));
				staff.setAddress(staffAddress.getAddressID());
			}
			staffAddress.setStaffID(staff.getStaffID());
			staffAddress.setCompanyID(account.getCompanyID());
			staffAddress.setAddressType("scode201004233ern4m24yx0000000260");//地址类别ID为系统预设
			staffAddress.setAddressDetailed(staff.getStaffAddress());
			staffAddress.setLivestartDate(new Date());
		}
		return staffAddress;
	}

	/**
	 * 保存人事菜单启用项
	 * @return
	 */
	public String saveHumanResource(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",
				new Object[] { account.getStaffID()});
		if(humanresource.getHumanresourceid() == null
				|| "".equals(humanresource.getHumanresourceid())){
			humanresource.setHumanresourceid(serverService.getServerID("humanresource"));
			parameter = staff.getStaffName() + "添加了人事菜单启用项";
		}else{
			parameter = staff.getStaffName() + "修改了人事菜单启用项";
		}
		humanresource.setCompanyid(account.getCompanyID());
		beans = new ArrayList<BaseBean>();
		beans.add(humanresource);
		CLogBook logBook = logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(logBook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<Integer> getAddormod() {
		return addormod;
	}
	public Staff getCstaff() {
		return cstaff;
	}

	public void setCstaff(Staff cstaff) {
		this.cstaff = cstaff;
	}

	public void setAddormod(List<Integer> addormod) {
		this.addormod = addormod;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public Humanresource getHumanresource() {
		return humanresource;
	}

	public void setHumanresource(Humanresource humanresource) {
		this.humanresource = humanresource;
	}

	public String getRelationshipValue() {
		return relationshipValue;
	}

	public void setRelationshipValue(String relationshipValue) {
		this.relationshipValue = relationshipValue;
	}

	public String getRelationshipValueAll() {
		return relationshipValueAll;
	}

	public void setRelationshipValueAll(String relationshipValueAll) {
		this.relationshipValueAll = relationshipValueAll;
	}

	public TbJpStudentInfo getStudent() {
		return student;
	}

	public void setStudent(TbJpStudentInfo student) {
		this.student = student;
	}

	public Enroll getEnroll() {
		return enroll;
	}

	public void setEnroll(Enroll enroll) {
		this.enroll = enroll;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public TbJpTeacher getTbJpTeacher() {
		return tbJpTeacher;
	}

	public void setTbJpTeacher(TbJpTeacher tbJpTeacher) {
		this.tbJpTeacher = tbJpTeacher;
	}
}