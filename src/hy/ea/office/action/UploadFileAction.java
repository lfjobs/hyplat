package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.office.EnterpriseStamp;
import hy.ea.service.UpLoadFileService;
import hy.plat.service.BaseBeanService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class UploadFileAction extends ActionSupport {

	@Resource
	private BaseBeanService baseBeanService;
	@SuppressWarnings("unused")
	@Resource
	private UpLoadFileService fileService;
	private String id;
	private String customerid;
	private File doc;
	private String docContentType;
	private String dir;
	private String docFileName;
	
	@SuppressWarnings("deprecation")
	public String execute() {
		String hql = "from CAccount where accountID=?";
		CAccount caccount = (CAccount) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { this.customerid });
		hql = "from EnterpriseStamp where enterpriseStampID=? ";
		EnterpriseStamp enterstamp = (EnterpriseStamp) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { id });
		HttpServletResponse response = ServletActionContext.getResponse();
		if (caccount != null) {
			String realPath = ServletActionContext.getRequest().getRealPath(
			"/upload_files/ea/office/enterpriseStamp/" + caccount.getCompanyID());
			String serverPath = ServletActionContext.getRequest().getScheme()
			+ "://" + ServletActionContext.getRequest().getServerName()
			+ ":" + ServletActionContext.getRequest().getServerPort()
			+ ServletActionContext.getRequest().getContextPath()
			+ "/upload_files/ea/office/enterpriseStamp/" + caccount.getCompanyID();
			try {
				File dFile = new File(serverPath);
				if (!dFile.exists()) {
					// 如果文件夹不存在则创建一个
					dFile.mkdirs();
				}
				String targetDirectory = realPath;
				//String targetFileName = generateFileName();
				String targetFileName = this.getDocFileName();
				setDir("/upload_files/ea/office/enterpriseStamp/" + caccount.getCompanyID() + "/" + targetFileName);
				String dir1 = this.getDir();
				File target = new File(targetDirectory, targetFileName);
				FileUtils.copyFile(doc, target);
				enterstamp.setScanningAccessories(dir1);
				baseBeanService.update(enterstamp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("ok");
			response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	@SuppressWarnings("unused")
	private String generateFileName() {
		String FileName = UUID.randomUUID().toString().replaceAll("-", "");
		return FileName + ".pjpeg";
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getDocContentType() {
		return docContentType;
	}

	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}
	
	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}
}
