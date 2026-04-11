package hy.ea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.commons.lang3.StringUtils;

/**
 * 多文件上传
 * @author Administrator
 *
 */
public class FileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String cmd = request.getParameter("cmd");
		String delpath = "";
		if (cmd != null && "del".equalsIgnoreCase(cmd)) {

			delpath = request.getParameter("f").replace("/", "\\");
			if (delpath != null && delpath.length() > 1) {
				String rootRealPath = getRealPath(null);
				String tmpRoot = rootRealPath.substring(rootRealPath
						.lastIndexOf("\\") + 1);
				String tmpReal = "";
				if (delpath.indexOf("\\") == 0) {
					tmpReal = delpath.substring(1);
					tmpReal = tmpReal.substring(0, tmpReal.indexOf("\\"));
				}
				if (tmpRoot.equals(tmpReal)) {
					delpath = delpath.replace("\\" + tmpReal, "");
				}
				delpath = getRealPath(null) + delpath;
			}
			if (delpic(delpath,false)) {
				out.print("删除成功!");
			} else {
				out.print("删除失败!");
			}
		} else {
			this.doPost(request, response);
		}
	}

	@SuppressWarnings({ "unused" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
        
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		if (!isMultipart) {
			logger.info(">> This wasn't a file upload request!");
			return;
		}

		PrintWriter out = resp.getWriter();
		// create factory and file cleanup tracker
		FileCleaningTracker tracker = FileCleanerCleanup
				.getFileCleaningTracker(getServletContext());
		File tmpDir = new File(getBaseDir() + "/upload/temp");
		checkImageDir(getBaseDir() + "/upload/temp");
		DiskFileItemFactory factory = new DiskFileItemFactory(
				DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, tmpDir);
		factory.setFileCleaningTracker(tracker);

		// save upload file to disk
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		Properties prop = System.getProperties();
		try {
			List<FileItem> items = upload.parseRequest(req); 
			FileItem uplFile = null;
			for (FileItem item : items) {
				if (item.isFormField()) { 
					prop.put(item.getFieldName(), item.getString());
					//logger.info("调试信息");
				} else { 
					uplFile = (FileItem) item; 
					
                   
				}
			}
		
			
		  	 /**图片上传的相对路径*/
			String imgUploadPath = null;
			
			String imgWebAppPath = null;
			/**图片后缀*/
			String imgFileExt = null;
			
			/**图片名称:以当前日期*/ 
       //	String imgFileId = DateUtil.getCurrentDate("yyyyMMddhhmmss")+RandomDatas.getRandomNumber(5);
//			String imgFileId = uplFile.getName().substring(0,uplFile.getName().lastIndexOf('.'))+RandomDatas.getRandomNumber(5);
			String imgFileId = 	UUID.randomUUID().toString().replaceAll("-", "");
			//图片初始化高度与宽度
			String width = null;
			String height = null; 
			int imgWidth = 0;
			int imgHeight = 0;
			int imgsW = 0;
			int imgsH = 0;
			
			//指定图片宽度和高度
			width = prop.get("width") == null ? "700" :prop.get("width").toString(); 
			height= prop.get("height") == null ? "800" :prop.get("height").toString(); 
			//是否生成小图
			String small = prop.get("small") == null ? "" : prop.get("small").toString();
			
			//文件保存路径
			String IMGROOT = prop.get("path") == null ? "" : prop.get("path").toString();
			String savePhysicalPath = this.getRealPath(IMGROOT);
			String TEMPPath = this.getRealPath(IMGROOT);
		    
			/**检查是否有图片上传文件夹*/
		    checkImageDir(savePhysicalPath);	
			checkImageDir(TEMPPath);
			
			imgWidth = Integer.parseInt(width);
			imgHeight = Integer.parseInt(height);
			
			String savepath = ""; 
			imgFileExt = uplFile.getName().substring(uplFile.getName().lastIndexOf(".") + 1).toLowerCase();
			String fileName = imgFileId;
			//组装图片真实名称
			imgFileId = fileName + "." + imgFileExt;	
			
			//图片生成全路径
			imgWebAppPath = savePhysicalPath + "\\" + imgFileId;
			//图片生成TEMP路径
			TEMPPath = TEMPPath + "\\" + imgFileId;
	
			//生成TEMP图片文件
			//myFile.saveAs(TEMPPath);
			File TempFile = new File(TEMPPath);
			
			uplFile.write(TempFile);  
			
			//图片的相对路径
			imgUploadPath = IMGROOT + "/" + imgFileId;
			
			if(imgFileExt.equalsIgnoreCase("png")||imgFileExt.equalsIgnoreCase("jpg")||imgFileExt.equalsIgnoreCase("gif")||imgFileExt.equalsIgnoreCase("bmp")||imgFileExt.equalsIgnoreCase("jpeg")){
			
			//检查图片大小
			BufferedImage src = ImageIO.read(TempFile); // 读入文件						 
			
			int imgSrcWidth = src.getWidth(); // 得到源图宽							 
			//重新指定大小
			imgWidth = imgSrcWidth > imgWidth ? imgWidth : imgSrcWidth;
			
			int imgSrcHeight = src.getHeight(); // 得到源图长
			imgHeight = imgSrcHeight > imgHeight ? imgHeight : imgSrcHeight;
		
			//生成大图
//			ImageCut.scale(TEMPPath, imgWebAppPath,imgWidth,imgHeight);	
			
			//如果有小图，则生成小图
			if(small != null && "true".equalsIgnoreCase(small)){
				imgsW = Integer.parseInt(prop.get("sw") != null ? prop.get("sw").toString() : "0");
				imgsH = Integer.parseInt(prop.get("sh") != null ? prop.get("sh").toString() : "0"); 
 				String saveSmallPath = savePhysicalPath + "\\" + fileName + "s." + imgFileExt;
				//生成小图
			    ImageCut.scale(TEMPPath, saveSmallPath,imgsW,imgsH);		
			}
			//此处删除Temp图
			//FileUtil.deleteFile(TEMPPath);
//			savepath +=imgUploadPath.replace("//","/") + ",";
		//	logger.info("调试信息"); 
			
			
			
			}
			savepath +=imgUploadPath.replace("//","/") + ",";
			out.print(savepath.replace("\t","").replace("\n",""));
		} catch (Exception e) {
			logger.info("调试信息");
			throw new IOException(e.getMessage());
		}

        out.flush();
        out.close();
	}

	private String getRealPath(String path) {
		ServletContext context = this.getServletContext();

		if (null == path || path.length() < 1) {
			return context.getRealPath("");
		}

		return context.getRealPath(path);
	}

	private String getBaseDir() {
		return this.getServletContext().getRealPath("");
	}

	private boolean delpic(String path, boolean hasSmail) {
		if (hasSmail) {
			String smaillP = path.substring(0, path.lastIndexOf(".")) + "s"
					+ path.substring(path.lastIndexOf("."));
			return FileUtil.deleteFile(path) && FileUtil.deleteFile(smaillP);
		}
		return FileUtil.deleteFile(path);
	}
	
	private void checkImageDir(String userWebAppPath) {
		File file = new File(userWebAppPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	
}
