package hy.ea.production.action.cprocedure.scmanage;





import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.util.SessionWrap;

import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.scmanage.MaterialContent;
import hy.ea.bo.production.scmanage.MaterialGroup;
import hy.ea.production.service.MaterialManageService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateJsonValueProcessor;
import hy.ea.util.DateUtil;
import hy.ea.util.ImageCut;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 素材
 */
@Controller
@Scope("prototype")
public class MaterialManageAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private MaterialManageService scManageService;
	private PageForm pageForm;
	private String result;
	private int pageNumber;
	private String search;
	private String parameter;
	private String companyID;
	private String staffID;
	private Staff staff;
	private String type;//
	private MaterialGroup materialGroup;
	private MaterialContent materialContent;
	private File file;
    private String fileType;
    private List<BaseBean> list;
    private String sub;// 手动防止表单提交
    private String bc;
	/**
	 * 
	 * 
	 * 素材管理首页
	 * @return
	 */
    public String findScmanageIndex(){
    	HttpServletRequest request = ServletActionContext.getRequest();
    	HttpSession session = ServletActionContext.getRequest().getSession();
	    SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
					SessionWrap.KEY_CUSTOMER);
		
		String companyID = scManageService.getStaffCompanyID(cus.getStaffid());
        if(staffID==null||staffID.equals("")){
        	if(cus!=null){
        		staffID=cus.getStaffid();
        	}
        }
		staff = (Staff) baseBeanService.getBeanByHqlAndParams(
				"from Staff where staffID=? ",
				new Object[] { staffID});
	    sw.setObject(session, SessionWrap.KEY_STAFF,staff);
	    String sql = "select count(*) from dtMaterialContent where state = ? and fileType = ? and ";
		String params = "";
	    if(companyID!=null&&!companyID.equals("")){
		   Company company = (Company) baseBeanService.getBeanByHqlAndParams("from Company where companyID = ?",new Object[]{companyID});
		   sw.setObject(session, SessionWrap.KEY_COMPANY, company);
		    sql+=" companyID = ?";
		    params = company.getCompanyID();

		}else{
			sql+=" staffID = ?";
			params = staff.getStaffID();
		}
		
		
		int picnum = baseBeanService.getConutByBySqlAndParams(sql,new Object[]{"01","00",params});
		int videonum = baseBeanService.getConutByBySqlAndParams(sql,new Object[]{"01","01",params});
		int audionum = baseBeanService.getConutByBySqlAndParams(sql,new Object[]{"01","02",params});
		
		request.setAttribute("picnum", picnum);
		request.setAttribute("videonum", videonum);
		request.setAttribute("audionum", audionum);
    	return "scmanage";
    }
    
    /**
     * 
     * 图片素材首页
     * @return
     */
    public String findCateManageIndex(){
    	HttpSession session = ServletActionContext.getRequest().getSession();
	    SessionWrap sw = SessionWrap.getInstance();
		TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
					SessionWrap.KEY_CUSTOMER);
		Company company = (Company) sw.getObject(session,SessionWrap.KEY_COMPANY);
		
		String hql = "from MaterialGroup where fileType = ?  and state = '00' and ";
        String params = "";
        if(company==null||company.equals("")){
        	if(cus!=null){
        		hql+=" updateStaffID = ?";
            	params = cus.getStaffid();
        	}
        	
        }else{
        	hql+=" companyID = ?";
        	params = company.getCompanyID();
        }
  		
    	
    	list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{type.equals("nopic")?"00":type.equals("novideo")?"01":"02",params});
    	String returnback = type.equals("nopic")?"picmanage":type.equals("novideo")?"videomanage":"audiomanage";
    	
    	return returnback;
    	
    }
    
    
    
    
    
    /**
     * 
     * 有内容文件列表包括图片，视频，音频
     * @return
     */
    public String findFilelist(){
    	
    	HttpSession session = ServletActionContext.getRequest().getSession();
  		SessionWrap sw = SessionWrap.getInstance();
  		Company company = (Company) sw.getObject(session,SessionWrap.KEY_COMPANY);
  		Staff staff = (Staff) sw.getObject(session,SessionWrap.KEY_STAFF);
  		HttpServletRequest request = ServletActionContext.getRequest();
  		String ajaxtype = request.getParameter("ajaxtype");
		
    	String back = "";
		if (materialContent != null) {
			String fileType = materialContent.getFileType();
			if (ajaxtype != null && ajaxtype.equals("ajax")) {
				String hql = "from MaterialContent where groupID =  ?  and state = ? and";
				String params = "";
				if(company!=null){
					hql+=" companyID = ?";
					params = company.getCompanyID();
				}else{
					hql+=" staffID = ?";
					params = staff.getStaffID();
				}
				hql+="order by createDate desc";
				
				try {
					pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm.getPageNumber() : 1), 3, hql,
							new Object[] { materialGroup.getMgId(),  "01",params});
				} catch (Exception e) {
					e.printStackTrace();
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("pageForm", pageForm);
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); // 当输出时间格式时，采用和JS兼容的格式输出
				JSONObject jo = JSONObject.fromObject(map, jsonConfig);
				this.result = jo.toString();
				return "success";
			}
			if ("00".equals(fileType)) {
				back = "havepic";
			} else if ("01".equals(fileType)) {
				back = "havevideo";
			} else if ("02".equals(fileType)) {
				back = "haveaudio";
			}

		}
    	
    	return back;
    
    }

    
    
    /**
     * 
     * 查看图片详情
     * @return
     */
    public String viewPicDetail(){
    	
    	String hql = "from MaterialContent where groupID =  ? and state = ? order by createDate desc"; 

    	
    	List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{materialGroup.getMgId(),"01"});
    	
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("showlist",list);

		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
    	return "success";

    }
    
    
    /**
     * 
     * 查看视频详情
     * @return
     */
    public String viewVideoDetail(){
    	
    	return "success";
    }
    
    /**
     * 
     * 创建分组页面
     * @return
     */
    public String newOrEditorGroup(){
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	session.setAttribute("session_value", Math.random() + "");
    	if(materialGroup!=null&&materialGroup.getMgId()!=null&&!materialGroup.getMgId().equals("")){
    		String hql = "from MaterialGroup where mgId = ?";
    		materialGroup = (MaterialGroup) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{materialGroup.getMgId()});
    		return "editorgroup";
    	
    	}
    	return "newgroup";
    	
    }
    
    /**
     * 
     * 保存分组操作
     * @return
     */
    public String saveGroup(){

    	HttpSession session = ServletActionContext.getRequest().getSession();
    	
    

			if (sub != null && sub.equals(session.getAttribute("session_value"))) {
				session.removeAttribute("session_value");
				
				SessionWrap sw = SessionWrap.getInstance();
		  		Company company = (Company) sw.getObject(session,SessionWrap.KEY_COMPANY);
		  		Staff staff = (Staff) sw.getObject(session,SessionWrap.KEY_STAFF);
		  		MaterialGroup  maGroup = new MaterialGroup();
		    	
		    	if(materialGroup!=null&&materialGroup.getMgId()!=null&&!"".equals(materialGroup.getMgId())){
		    		  String hql = "from MaterialGroup where mgId = ?";
		    		  maGroup = (MaterialGroup) baseBeanService.getBeanByHqlAndParams(hql,new Object[]{materialGroup.getMgId()});
		    		  maGroup.setUpdateDate(new Date());
		    		  maGroup.setUpdateStaffID(staffID);
		    		  findFilelist();
		    	}else{
		    		maGroup.setMgId(serverService.getServerID("mgId"));
		    		maGroup.setCreateDate(new Date());
		    		maGroup.setUpdateDate(new Date());
		    	}
		    	maGroup.setCompanyID(company==null?"":company.getCompanyID());
		    	maGroup.setGroupname(materialGroup.getGroupname());
		    	maGroup.setGroupdesc(materialGroup.getGroupdesc());
		    	maGroup.setGroupcover("/images/ea/production/scmanage/nocontent2x.png");
		    	maGroup.setFileType(type.equals("nopic")?"00":type.equals("novideo")?"01":"02");
		    	maGroup.setState("00");
		    	maGroup.setCreateStaffID(staff.getStaffID());
		    	maGroup.setUpdateStaffID(staff.getStaffID());
		    	maGroup.setFilenum("0");
		    	baseBeanService.update(maGroup);
		    	materialGroup=maGroup;
			
			}
	

    	
    	if(bc!=null&&bc.equals("select")){
    		selectGroupList();
    	}
    	return type;
	     
	     
    }
    /**
     * 
     * 删除分组
     * @return
     */
    public String deleteGroup(){
    	String hql = "update MaterialGroup set state = ? where mgId = ?";
    	baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql}, new Object[]{"09",materialGroup.getMgId()});
    	
    	return findCateManageIndex();
    	
    }
    
    /**
     * 
     * 上传文件首页包括图片，视频，音频
     * @return
     */
    public String uploadFileIndex(){
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	session.setAttribute("session_value", Math.random() + "");
  		SessionWrap sw = SessionWrap.getInstance();
  		Staff staff = (Staff) sw.getObject(session,SessionWrap.KEY_STAFF);

    	String hql = "from MaterialContent where fileType = ? and staffID = ? and state = ?"; 
    	list = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{type.equals("nopic")?"00":type.equals("novideo")?"01":"02",staff.getStaffID(),"00"});

    	return type.equals("nopic")?"uploadpic":type.equals("novideo")?"uploadvideo":"uploadaudio";

    }
    
    /**
     * 
     * 点击删除按钮删除单独文件
     * @return
     */
    public String deleteTempFile(){
    	
    	String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
    	boolean bool = scManageService.deleteFileTempByone(materialContent.getFilepath(), realpath);
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", bool);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
    	return "success";
    }
    
    /**
     * 
     * 当没保存却离开页面时调用
     * 批量删除缓存文件
     * @return
     */
    public String deleteTempFileByBat(){
    	HttpSession session = ServletActionContext.getRequest().getSession();
  		SessionWrap sw = SessionWrap.getInstance();
  		Company company = (Company) sw.getObject(session,SessionWrap.KEY_COMPANY);
  		Staff staff = (Staff) sw.getObject(session,SessionWrap.KEY_STAFF);
  		String companyID = company==null?"":company.getCompanyID();
    	String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
    	boolean bool =  scManageService.deleteFileTempByBat(companyID, staff.getStaffID(), realpath);
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", bool);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
    	return "success";
    }
    
    
    
    /**
     * 
     * 包括图片和视频
     * @return
     */
    
    public String saveDescibe(){
    	String updatehql = "update MaterialContent set describe = ? where mcId = ?";
    	baseBeanService.saveBeansListAndexecuteHqlsByParams(null,new String[]{updatehql},new Object[]{materialContent.getDescribe(),materialContent.getMcId()});
    	
    	
    	return "success";
    }
    
    /**
     * 
     * 删除视频音频视频
     * @return
     */
    public String  deleteFileByPre(){
    	String realpath = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
    	boolean bool = scManageService.deleteFileBypre(materialContent, realpath);
    	Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", bool);
		JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
    	return "success";
    }
    
 
    /**
     * 
     * 上传文件 图片，视频，音频
     * @return
     */
    public String uploadFile(){
	    try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			SessionWrap sw = SessionWrap.getInstance();
			Company company = (Company) sw.getObject(session,SessionWrap.KEY_COMPANY);
			Staff staff = (Staff) sw.getObject(session,SessionWrap.KEY_STAFF);
			
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("\\");

			String urlPath = fileService.savePhoto(path, fileType.replace("/", "."),
					file, company!=null?company.getCompanyID():staff.getStaffID(), "scmanage/".concat(DateUtil
							.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)));
			
			if(fileType.indexOf("image")!=-1){
				String jjPath = urlPath.split("\\.")[0] + "small." + urlPath.split("\\.")[1];
			    ImageCut.scale(path+urlPath, path+jjPath, 100, 100);
			}

			MaterialContent mc = new MaterialContent();
			mc.setMcId(serverService.getServerID("mcId"));
			mc.setCompanyID(company!=null?company.getCompanyID():"");
			mc.setCreateDate(new Date());
			mc.setDescribe("");
			mc.setFilepath(urlPath);
			mc.setFileType(fileType.indexOf("image")!=-1?"00":fileType.indexOf("video")!=-1?"01":"02");
			mc.setFilesize(materialContent.getFilesize());
			mc.setFilecover("images/ea/production/scmanage/demo_01.jpg");
			mc.setGroupID("");
			mc.setStaffID(staff.getStaffID());
			mc.setState("00");
			baseBeanService.save(mc);
			
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("path", urlPath);
			map.put("mcid", mc.getMcId());
			JSONObject jo = JSONObject.fromObject(map);
			this.result = jo.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return "success";
    }
    
    /**
     * 
     * 处理数据
     * @return
     */
    public String dispose(){
    	String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
    	String hql = "from MaterialContent t where t.fileType=? ";
    	List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{"00"});
    	for (int i = 0; i < list.size(); i++) {
    		MaterialContent mc = (MaterialContent) list.get(i);
    		String urlPath = mc.getFilepath();
    		//判断原路径下是否有图片true:进入,false:跳过
    		File file=new File(path+urlPath);
    		if(file.exists()){
    			String jjPath = urlPath.split("\\.")[0] + "small." + urlPath.split("\\.")[1];
    			//判断小图路径下是否有图片true:进入创建,false:跳过
    			File file1=new File(path+jjPath);
    			if(!file1.exists()){
    				ImageCut.scale(path+urlPath, path+jjPath, 100, 100);
    			}
    		}
		}
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("state", "成功");
    	JSONObject jo = JSONObject.fromObject(map);
		this.result = jo.toString();
		return "success";
    	
    }
    
    
    
    
    
    /**
     * 
     * 选择分组
     * @return
     */
    public String selectGroupList(){
    	HttpSession session = ServletActionContext.getRequest().getSession();
  		SessionWrap sw = SessionWrap.getInstance();
  		Company company = (Company) sw.getObject(session,SessionWrap.KEY_COMPANY);
  		Staff staff = (Staff) sw.getObject(session,SessionWrap.KEY_STAFF);
  		List<Object> params = new ArrayList<Object>();
    	String hql = "from MaterialGroup where fileType = ? and state = ? and";
    	params.add(type.equals("nopic")?"00":type.equals("novideo")?"01":"02");
    	params.add("00");
    	
    	if(company!=null){
    		hql+=" companyID = ?";
    		params.add(company.getCompanyID());
    		
    	}else{
    		hql+=" updateStaffID = ?";
    		params.add(staff.getStaffID());
    	}
    
    	if(materialContent!=null&&materialContent.getGroupID()!=null&&!materialContent.getGroupID().equals("")){
    		hql+=" and mgId != ?";
    		params.add(materialContent.getGroupID());
    	}
    	hql+=" order by updateDate desc";
    	List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,params.toArray());
    	HttpServletRequest request = ServletActionContext.getRequest();
    	request.setAttribute("grouplist", list);
    	
    	return "selectgroup";
    	
    }
    
    /**
     * 
     * 
     * 保存名称描述等
     * @return
     */
    public String saveContent(){
    	HttpSession session = ServletActionContext.getRequest().getSession();
    	if (sub != null && !sub.equals("")) {
			if (sub != null && sub.equals(session.getAttribute("session_value"))) {
				session.removeAttribute("session_value");
				SessionWrap sw = SessionWrap.getInstance();
		  		Staff staff = (Staff) sw.getObject(session,SessionWrap.KEY_STAFF);
		    	scManageService.saveContent(materialContent,staff.getStaffID());
			}
		}

    	return findFilelist();
    	
    	
    }
    /**
     * 
     * 移动分组
     * @return
     */
    public String moveGroup(){
    	String hql = "update MaterialContent  set groupID = ? where  mcId = ?";
    	baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[]{hql},new Object[]{materialContent.getGroupID(),materialContent.getMcId()});
    	
    	
    	return "success";
    	
    }
	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getStaffID() {
		return staffID;
	}

	public void setStaffID(String staffID) {
		this.staffID = staffID;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public MaterialGroup getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(MaterialGroup materialGroup) {
		this.materialGroup = materialGroup;
	}



	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

   

	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}

	public MaterialContent getMaterialContent() {
		return materialContent;
	}

	public void setMaterialContent(MaterialContent materialContent) {
		this.materialContent = materialContent;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public String getBc() {
		return bc;
	}

	public void setBc(String bc) {
		this.bc = bc;
	}

	

   
     

   
	

}
