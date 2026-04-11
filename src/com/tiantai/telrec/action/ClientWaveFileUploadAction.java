package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.plat.service.BaseBeanService;
import hy.tel.bo.TelInRecord;
import hy.tel.bo.TelOutRecord;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.tiantai.telrec.service.ClientInsertTelInfoService;

public class ClientWaveFileUploadAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3867705138449515196L;
	private File doc;
	private String docFileName;
	private String docContentType;
	private String dir;
	private String id;
	private String fileSaveName;// 文件保存名eg:20120418104448325467165.wav
	private String in_or_out;
	private String saveFilePath;
	
	@Resource
	private BaseBeanService baseBeanService;
	
	private ClientInsertTelInfoService telInfoService;

	@SuppressWarnings("deprecation")
	public String execute() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		try {
			CAccount caccount = (CAccount)ActionContext.getContext().getSession().get("account");
			if(caccount == null){
			    return null;
			}
			
			String realPath =request.getRealPath(saveFilePath);
			String serverPath = ServletActionContext.getRequest().getScheme()
					+ "://" + ServletActionContext.getRequest().getServerName()
					+ ":" + ServletActionContext.getRequest().getServerPort()
					+ ServletActionContext.getRequest().getContextPath()
					+ saveFilePath;

			File dFile = new File(serverPath);
			if (!dFile.exists()) {
				dFile.mkdirs();
			}

			String targetDirectory = realPath;
			String targetFileName = fileSaveName == null ? generateFileName(getDocFileName())
					: fileSaveName;
			
			setDir(serverPath + "/" + targetFileName); 
			
			File target = new File(targetDirectory, targetFileName);
			FileUtils.copyFile(doc, target);

			if("in".equals(in_or_out)){
				TelInRecord telIn = (TelInRecord)baseBeanService.getBeanByKey(TelInRecord.class, this.id);
				telIn.setSavePath(this.getDir());
				baseBeanService.update(telIn); 
			}else {
				if(in_or_out != null){
					TelOutRecord telOut = (TelOutRecord)baseBeanService.getBeanByKey(TelOutRecord.class, this.id);
					telOut.setSavePath(this.getDir());
					baseBeanService.update(telOut); 
				}
			}

			HttpServletResponse response = ServletActionContext.getResponse(); 
			response.getWriter().write("true");
			response.flushBuffer(); 
		} catch (Exception e) {
			logger.error("操作异常", e);
		} 
		return null;
	}

	/**
	 * 生成随机文件名文件名
	 * 
	 * @param fileName
	 * @return
	 */
	private String generateFileName(String fileName) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String formateDate = format.format(new Date());
		int random = new Random().nextInt(10000);
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position);
		return formateDate + random + extension;
	}

	public String getDocContentType() {
		return docContentType;
	}

	public void setDocContentType(String docContentType) {
		this.docContentType = docContentType;
	}

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileSaveName() {
		return fileSaveName;
	}

	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}

	public ClientInsertTelInfoService getTelInfoService() {
		return telInfoService;
	}

	public void setTelInfoService(ClientInsertTelInfoService telInfoService) {
		this.telInfoService = telInfoService;
	}
	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getIn_or_out() {
		return in_or_out;
	}

	public void setIn_or_out(String in_or_out) {
		this.in_or_out = in_or_out;
	}

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}
}
