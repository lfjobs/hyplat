package hy.ea.office.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CorPhotoBox;
import hy.ea.bo.office.Dtofficeimagemanager;
import hy.ea.bo.office.NetWork;
import hy.ea.service.CLogBookService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
/*办公室形象管理*/

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class OfficeImageManagerAction {
  
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private CLogBookService logBookService;
	private PageForm pageForm;
	private String photoname;
	private String photodescrib;
	
	private int pageNumber;
	private String search;
	private Dtofficeimagemanager officeimagemanager;
	private CorPhotoBox corphotobox;
	private String parameter;
	
	//查询信息
	private DetachedCriteria getList(){
		Map<String,Object> session =ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		String companyID = account.getCompanyID();
		DetachedCriteria dc=DetachedCriteria.forClass(Dtofficeimagemanager.class);
		dc.add(Restrictions.eq("companyid", companyID));
		dc.add(Restrictions.eq("organizationid", organizationID));
		/*if (search != null && search.equals("search")) {
			officeimagemanager =  (DtofficeImageManager) session.get("tablesearch");
			if(officeimagemanager.getOfficeDescription()!=null&&!officeimagemanager.getOfficeDescription().equals("")){
				dc.add(Restrictions.like("officeDescription",officeimagemanager.getOfficeDescription().trim(),MatchMode.ANYWHERE));
			}
			
			if(officeimagemanager.getMark()!=null&&!officeimagemanager.getMark().equals("")){
				dc.add(Restrictions.like("mark",officeimagemanager.getMark().trim(),MatchMode.ANYWHERE));
			}
	
		}*/
		return dc;
	}
	public String getOfficeImageManagerList(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("session_value", Math.random() + "");// 在不使用token的情况下,手动防止重复提交
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?5:pageNumber), getList());
		return "getofficeImageManagerList";
	}
	//存储相册
	public String savephototype() throws UnsupportedEncodingException {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		CAccount account = (CAccount) session.get("account");
		String organizationID = (String) session.get("organizationID");
		CorPhotoBox cd = new CorPhotoBox();
		cd.setPhotoBoxID(serverService.getServerID("CorPhotoBox"));
		parameter = "添加相册(相册名称:"+URLDecoder.decode(photoname, "UTF-8")+")";
		List<BaseBean> beans=new ArrayList<BaseBean>();
		cd.setPhotoBoxName(URLDecoder.decode(photoname, "UTF-8"));
		cd.setPhotoBoxDepict(URLDecoder.decode(photodescrib, "UTF-8"));
		cd.setCompanyID(account.getCompanyID());
		cd.setOrganizationID(organizationID);
		cd.setCreatorID(account.getStaffID());
		cd.setCreatorName(account.getAccountName());
		CLogBook  logbook=logBookService.saveCLogBook(organizationID, parameter, account);
		beans.add(cd);
		beans.add(logbook);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
		return "success";
	
	}
	//上传图片
	public void ProcessRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	        System.out.println("进来了");
	        System.out.println(request.getParameter("Filedata"));
	        try {
	            // 上传文件的临时目录
	            String temp=request.getParameter("folder");
	            System.out.println(temp);
	            String tempDir = request.getSession().getServletContext()
	                    .getRealPath("UploadFile");
	            File tFile = new File(tempDir);
	            if (!tFile.exists())
	                tFile.mkdir();
	            // 创建缓冲区
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            // 设置缓冲区大小 1*1024*1024 设置1Mb
	            factory.setSizeThreshold(1 * 1024 * 1024);
	            // 设置缓冲临时目录
	            factory.setRepository(new File(tempDir));
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            // 设置编码
	            upload.setHeaderEncoding("UTF-8");
	            response.setContentType("text/html;charset=utf-8");
	            // 设置文件最大值,这里设置5Mb,5*1024*1024;
	            upload.setSizeMax(5 * 1024 * 1024);
	            FileItemIterator fileItem = upload.getItemIterator(request); // 得到所有的文件
	            while (fileItem.hasNext()) {//根本没进来
	                FileItemStream fi = fileItem.next(); // 获得文件流
	                if (!fi.isFormField() && fi.getName().length() > 0) { // 过滤非文件域
	 
	                    String fileName = fi.getName(); // 获取文件名称
	                    String fName = tempDir + "\\" + fileName; // 文件最终存放目录
	                    File file = new File(fName);
	                    FileOutputStream out = new FileOutputStream(file);
	                    out.write(fi.toString().getBytes());
	                    out.flush();
	                    out.close();
	                }
	            }
	            Random r = new Random(10);
	            response.getWriter().write("" + r.nextInt());
	        } catch (Exception e) {http://localhost:8888/hyplat
	            System.out.println(e.getMessage());
	        }
	    }
	public String getPhotoname() {
		return photoname;
	}
	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}
	public String getPhotodescrib() {
		return photodescrib;
	}
	public void setPhotodescrib(String photodescrib) {
		this.photodescrib = photodescrib;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	public Dtofficeimagemanager getOfficeimagemanager() {
		return officeimagemanager;
	}
	public void setOfficeimagemanager(Dtofficeimagemanager officeimagemanager) {
		this.officeimagemanager = officeimagemanager;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public CorPhotoBox getCorphotobox() {
		return corphotobox;
	}
	public void setCorphotobox(CorPhotoBox corphotobox) {
		this.corphotobox = corphotobox;
	}
	
}
