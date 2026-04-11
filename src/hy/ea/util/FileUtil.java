package hy.ea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.plat.service.HistoryService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
 

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext; 
import org.springframework.util.FileCopyUtils;

/**
 * 文件常用操作
 * @author yaloo
 *
 */ 
public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	@Resource
	private HistoryService historyService;
	private static final Map<String, String> mimeTypes = new HashMap<String, String>();

	private static final Map<String, String> fileTypeMap = new HashMap<String, String>();
	static {
		mimeTypes.put("htm", "text/html");
		mimeTypes.put("html", "text/html");
		mimeTypes.put("txt", "text/plain");
		mimeTypes.put("asc", "text/plain");
		mimeTypes.put("gif", "image/gif");
		mimeTypes.put("jpg", "image/jpeg");
		mimeTypes.put("jpeg", "image/jpeg");
		mimeTypes.put("png", "image/png");
		mimeTypes.put("mp3", "audio/mpeg");
		mimeTypes.put("m3u", "audio/x-mpegurl");
		mimeTypes.put("pdf", "application/pdf");
		mimeTypes.put("doc", "application/msword");
		mimeTypes.put("xls", "application/vnd.ms-excel");
		mimeTypes.put("ppt", "application/vnd.ms-powerpoint");
		mimeTypes.put("ogg", "application/ogg");
		mimeTypes.put("zip", "application/zip");
		mimeTypes.put("exe", "application/octet-stream");
		fileTypeMap.put("app", "申请");
		fileTypeMap.put("docs", "资料");
		fileTypeMap.put("other", "其他");
	}


	/**
	 * 返回文件下载流,根据需求需要文件名
	 * 
	 * @return
	 * @throws IOException
	 */
	public void downFile(String path) throws IOException {
		// getEncodeName();
		

		InputStream inputStream = ServletActionContext.getServletContext()
				.getResourceAsStream("/" + path);
		byte[] fileData = new byte[1024];
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(getMimeType(path));
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ getEncodeName(path) + "\"");
		OutputStream outputStream = response.getOutputStream();
		int len = 0;
		while (len >= 0) {
			len = inputStream.read(fileData);
			outputStream.write(fileData);
		}
		outputStream.flush();
		outputStream.close();
	}

	public String getEncodeName(String fileName) {
		String encodeName = "";
		try {
			encodeName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");// ISO-8859-1
		} catch (UnsupportedEncodingException e) {
			logger.error("操作异常", e);
		}
		return encodeName;
	}

	public String getMimeType(String filename) {
		String mimeType = null;
		if (filename != null && filename.length() > 0) {
			mimeType = (String) mimeTypes.get(filename.substring(
					filename.lastIndexOf('.') + 1).toLowerCase());
		}
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		return mimeType;
	}

	public Map<String, String> getFileTypeMap() {
		return fileTypeMap;
	}
/////////////////////////////////////////////////////////////////
///// add by yaloo 2012-05-31 09:27
/////////////////////////////////////////////////////////////////
	/**
	 * Delete files, can be a single file or folder
	 * @param fileName
	 *            待删除的文件名
	 * @return 文件删除成功返回true,否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			logger.info("删除文件失败：{}{}", fileName, "文件不存在");
			return false;
		} else {
			if (file.isFile()) {

				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}
	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		fileName = fileName.replace("\\\\", "\\");
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			//logger.info("删除单个文件{}{}", fileName, "成功！");
			return true;
		} else {
			logger.info("删除单个文件{}{}", fileName, "失败！");
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			logger.info("删除目录失败{}{}", dir, "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			logger.info("删除目录失败");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			logger.info("删除目录{}{}", dir, "成功！");
			return true;
		} else {
			logger.info("删除目录{}{}", dir, "失败！");
			return false;
		}
	}
	

	public String createBlankWord(String path,String companyID,String fileType) throws IOException{
		 	String wordpath="";
			// 考虑到linux,获取文件分隔符
			String fs = System.getProperties().getProperty("file.separator");
			String sep = null;
			if (fs.equals("/"))
				sep = fs;//linux
			else
				sep = "\\";// windows
			String fileSaveName = "word_" + RandomDatas.getRandomString(3);
			String PathDir = "document" + sep + "word" + sep
					+ companyID;
			wordpath = PathDir + sep + fileSaveName + "."+fileType;
			File fileSaveDir = new File(path + PathDir);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}
			File fileSave = new File(path + PathDir, fileSaveName + ".doc");
			fileSave.createNewFile();
			File blankword = new File(path + "document" + sep + "word" + sep
					+ "blank.doc");
			
			FileCopyUtils.copy(blankword, fileSave);
		return wordpath;
	}
	public void filingHistory(){
		historyService.filingHistory();
		
	}
/////////////////////////////////////////////////////////////////
//// add by yaloo	2013-05-20 14:27
/////////////////////////////////////////////////////////////////
	/**
	 * 读取Txt文件数据，每行以";"分隔
	 * @param f
	 * @return
	 */
	public static String getStrFromText(File f){
		String str = "";
		String t = "";
		if (null != f) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				try {
					while (null != (t = br.readLine())) {
						str += (t + ";");
					}
				} catch (IOException e) {
					logger.error("操作异常", e);
				}
				if (null != br) {
					try {
						br.close();
						br = null;
					} catch (IOException e) {
						logger.error("操作异常", e);
					}
				}
			} catch (FileNotFoundException e) {
				logger.error("操作异常", e);
			}
		}
		return str;
	}
	
	public static String getStrFromText(String filePath){ 
		
		if(!StringUtils.isNotBlank(filePath)){
			return "";
		}
		
		File f = new File(filePath);
		
		if(null == f || !f.isFile()){
			return "";
		}
		
		return getStrFromText(f);
		
	}
////////////////////////////////////////////////////////
	public static void main(String[] args) {
		for(int i =0;i<100;i++)
		logger.info("调试信息");
	}
}
