package hy.ea.human.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.human.UpLoadFile;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zhuozhengsoft.pageoffice.FileSaver;

@Controller
@Scope("prototype")
public class UpLoadAction {
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	private UpLoadFile loadFile;
	private List<UpLoadFile> loadFileList;
	private File photo;
	private String photoFileName;
	private String photoContentType;
	private PageForm pageForm;
	private int pageNumber;
	protected static final Logger LOGGER = Logger.getLogger(Class.class);
	private String result;
	private String fileType;

	public PageForm getPageForm() {
		return pageForm;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public UpLoadFile getLoadFile() {
		return loadFile;
	}

	public void setLoadFile(UpLoadFile loadFile) {
		this.loadFile = loadFile;
	}

	public List<UpLoadFile> getLoadFileList() {
		return loadFileList;
	}

	public void setLoadFileList(List<UpLoadFile> loadFileList) {
		this.loadFileList = loadFileList;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

	public String saveImage() {
		ActionContext.getContext().put("loadFile", loadFile);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (photo != null) {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("\\");
			String photoPath = fileService
					.savePhoto(path, photoFileName, photo, account
							.getCompanyID(), "/human/cPhotos/"
							+ Utilities.getDateString(new Date(), "yyyy-MM-dd"));
			loadFile.setFilepath(photoPath);
		}
		if (null == loadFile.getFileID() || "".equals(loadFile.getFileID())) {
			loadFile.setFileID(serverService.getServerID("loadFile"));
		}
		loadFile.setCompanyID(account.getCompanyID());
		baseBeanService.update(loadFile);
		return getListImage();
	}

	public String delImage() {
		ActionContext.getContext().put("loadFile", loadFile);
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String[] hql = { "delete UpLoadFile where companyID = ?  and parmeterID = ? and fileID = ?" };
		String hql1 = "from  UpLoadFile where companyID = ?  and parmeterID = ? and fileID = ?";
		Object[] params = { account.getCompanyID(), loadFile.getParmeterID(),
				loadFile.getFileID() };
		UpLoadFile upload = (UpLoadFile) baseBeanService.getBeanByHqlAndParams(
				hql1, params);
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("\\");
		ArrayList<String> paths = new ArrayList<String>();
		paths.add(path + upload.getFilepath());
		fileService.deletePhotos(paths);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, hql, params);
		return getListImage();
	}

	public String getListImage() {

		Object[] params = { loadFile.getParmeterID() };
		pageForm = baseBeanService.getPageForm((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 4 : pageNumber),
				" from UpLoadFile where  parmeterID = ?", params);
		return "list";
	}

	public String editWord() throws IOException {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		FileUtil createFile = new FileUtil();
		String newTemplatePath = createFile.createBlankWord(path, account
				.getCompanyID(), fileType.equals("W") ? "doc" : "xls");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", newTemplatePath);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	/**
	 * 
	 * 远程新建word
	 * 
	 * @return
	 * @throws IOException
	 */
	public String remoteWord() throws IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		FileUtil createFile = new FileUtil();
		String newTemplatePath = null;
		try {
			newTemplatePath = createFile.createBlankWord(path, "remote",
					fileType.equals("W") ? "doc" : "xls");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("docPath", newTemplatePath);
		JSONObject json = JSONObject.fromObject(map);

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;

	}

	/**
	 * 保存上传word文件
	 * 
	 * @param newkey
	 *            公文数据库记录的主键
	 * 
	 * @return
	 */
	public String saveWord() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");

		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		HttpServletRequest request = ServletActionContext.getRequest();
		String docPath = request.getParameter("docPath");
		HttpServletResponse response = ServletActionContext.getResponse();
          FileSaver fs = new FileSaver(request, response);

		
		try {
			
			fs.saveToFile(path+docPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "none";
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
