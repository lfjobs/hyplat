package hy.ea.office.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.office.service.ZOfficeService;
import hy.ea.util.RandomDatas;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import com.zhuozhengsoft.pageoffice.FileSaver;

@Service
@Transactional
public class ZOfficeServiceImpl implements ZOfficeService {

	// 创建
	public String createOffice(String realpath, String fileType,
			String templatepath, String storePath) {
		String ext = "";
		String extname = "";
		if (fileType == "W" || fileType.equals("W")) {
			extname = "word_";
			ext = ".doc";
		} else {
			extname = "excel_";
			ext = ".xls";
		}
		//String fileSaveName = extname + RandomDatas.getRandomString(3) + ext;
		String fileSaveName = extname + UUID.randomUUID() + ext;
		try {
			
			File fileSaveDir = new File(realpath + storePath);
			File fileSave = new File(realpath + storePath, fileSaveName);
			File office = null;
			if (templatepath == null) {
				office = new File(realpath + "document/template/" + "blank"
						+ ext);
			} else {
				office = new File(realpath + templatepath);
			}
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}
			FileCopyUtils.copy(office, fileSave);
		} catch (IOException e) {
			logger.error("操作异常", e);
		}

		return storePath+ "/" + fileSaveName;
	}
    /**
     * 
     * 
     * storepath 路径包括名字
     */
	public void saveOffice(HttpServletRequest request,
			HttpServletResponse response, String realpath ,String storepath) {
		FileSaver fs = new FileSaver(request, response);

		
		try {
			
			fs.saveToFile(realpath+storepath);
		} catch (Exception e) {
			logger.error("操作异常", e);
		} finally {
			try {
				fs.close();
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
		}
	}

	/**
	 * 
	 * 删除Office文件
	 * 
	 */
	public void deleteOffice(String filePath) {

		try {
			File file = new File(filePath);
			if (!file.isDirectory()) {
				file.delete();
			}
		} catch (Exception e) {
			logger.info("删除文件夹出错");
		}

	}
	
	
	/**
	 * 
	 * 拷贝文件
	 */
	public String CopyOfficeFile(String realpath,String filePath){
		filePath = filePath.replace("\\", "/");
		String filename = filePath.substring(filePath.lastIndexOf("/")+1);
		String ext = filePath.substring(filePath.lastIndexOf("."));
		String extname = "";
		if (ext.equals(".doc")) {
			extname = "word_";
		} else {
			extname = "excel_";
		}
		String fileSaveName = extname+ RandomDatas.getRandomString(3) + ext;
		String filedir = filePath.replace(filename,"");
		try {
			
			File newfilepath = new File(realpath+filedir+"/"+fileSaveName);
			File fileSave = new File(realpath+filePath);
			FileCopyUtils.copy(fileSave,newfilepath);
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
		
		return filedir+fileSaveName;

	}

}