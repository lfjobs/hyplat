<%@ page language="java" import="java.util.*" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.File"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="hy.ea.util.FileUtil"%>
<%@page import="hy.ea.util.ImageCut"%>    
<!-- <%
//	String path = request.getContextPath();
//	String basePath = request.getScheme() + "://"
		//	+ request.getServerName() + ":" + request.getServerPort()
		//	+ path + "/";
%>
<%!//private void checkImageDir(String userWebAppPath) {
	//	File file = new File(userWebAppPath);
		//if (!file.exists()) {
		//	file.mkdir();
		//}
	//}%>
<%!//private boolean delpic(String path) {
	//	String smaillP = path.substring(0, path.lastIndexOf(".")) + "s"
	//			+ path.substring(path.lastIndexOf("."));
	//	return FileUtil.deleteFile(path) && FileUtil.deleteFile(smaillP);
	//}%>
<%
	//String cmd = request.getParameter("cmd");
  //	String delpath = ""; 
  //	if(cmd !=null && "del".equalsIgnoreCase(cmd)){
  		
  		//delpath = request.getParameter("f");
  	//	if(delpath != null && delpath.length() > 1){
  	//		delpath = application.getRealPath("/") + delpath;
  	//	}	
  	//	if(delpic(delpath)){
  	//		out.print("删除成功!");
  	//	}else{
  	//	    out.print("删除失败!");
  	//	}  		
  //	}else{
  	 /**图片上传的相对路径*/
	//	String imgUploadPath = null;
		
	//	String imgWebAppPath = null;
		/**图片后缀*/
	//	String imgFileExt = null;
		
		/**图片名称:以当前日期*/
	//	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		//String imgFileId = formatter.format(new Date());
		
		//图片初始化高度与宽度
		//String width = null;
		//String height = null; 
		//int imgWidth = 0;
		//int imgHeight = 0;
		//int imgsW = 0;
		//int imgsH = 0;
 
	//	try {			
		//	com.jspsmart.upload.SmartUpload smartUpload = new com.jspsmart.upload.SmartUpload();
		//	smartUpload.initialize(getServletConfig(), request, response);
		//	smartUpload.upload();
			//com.jspsmart.upload.Request rqest = smartUpload.getRequest();		
			
			 
			//指定图片宽度和高度
			//width = rqest.getParameter("width");
			//if(null == width){
			//	width = "700";
		//	}
			//height= rqest.getParameter("height");	
			///if(null == height){
			//	height = "600";
			//}
			//是否生成小图
			//String small = rqest.getParameter("small"); 
			
			//文件保存路径
			//String IMGROOT = rqest.getParameter("path");
			//String savePhysicalPath = application.getRealPath(IMGROOT);
			//String TEMPPath = application.getRealPath("/TEMP");
		    /**检查是否有图片上传文件夹*/
		  //  checkImageDir(savePhysicalPath);	
		//	checkImageDir(TEMPPath);
			
			//imgWidth = Integer.parseInt(width);
			//imgHeight = Integer.parseInt(height);
			
			//String savepath = "";
			//String fileName = "";
			//文件个数
			//int fileCounts = smartUpload.getFiles().getCount();	
		
			//for (int i = 0; i <fileCounts; i++) {
			//	com.jspsmart.upload.File myFile = smartUpload.getFiles().getFile(i);
				
				// if (!myFile.isMissing()) {
					
				//	imgFileExt = myFile.getFileExt();
					
				//	fileName = imgFileId + i + System.currentTimeMillis();
					//组装图片真实名称
				//	imgFileId = fileName + "." + imgFileExt;
					
					//图片生成全路径
					//imgWebAppPath = savePhysicalPath + "\\" + imgFileId;
					//图片生成TEMP路径
					//TEMPPath = TEMPPath + "\\" + imgFileId;					
					//生成TEMP图片文件
					//myFile.saveAs(TEMPPath);
					
					
					//图片的相对路径
					//imgUploadPath = IMGROOT + "/" + imgFileId;
					
					//检查图片大小
					//BufferedImage src = ImageIO.read(new File(TEMPPath)); // 读入文件						 
					
				//	int imgSrcWidth = src.getWidth(); // 得到源图宽							 
					//重新指定大小
					//imgWidth = imgSrcWidth > imgWidth ? imgWidth : imgSrcWidth;
					
					//int imgSrcHeight = src.getHeight(); // 得到源图长
					//imgHeight = imgSrcHeight > imgHeight ? imgHeight : imgSrcHeight;
				
					//生成大图
					//ImageCut.scale(TEMPPath, imgWebAppPath,imgWidth,imgHeight);				 
					//如果有小图，则生成小图
					//if(small != null && "true".equalsIgnoreCase(small)){
					//	imgsW = Integer.parseInt(rqest.getParameter("sw") != null ? rqest.getParameter("sw") : "0");
					//	imgsH = Integer.parseInt(rqest.getParameter("sh") != null ? rqest.getParameter("sh") : "0"); 
		 			//	String saveSmallPath = savePhysicalPath + "\\" + fileName + "s." + imgFileExt;
						//生成小图
					//    ImageCut.scale(TEMPPath, saveSmallPath,imgsW,imgsH);		
					//}
					//此处删除Temp图
					//FileUtil.deleteFile(TEMPPath);
				//	savepath +=(imgUploadPath.replace("//","/") + "|");
				//}
			//}
			//    out.print(savepath.replace("\t","").replace("\n",""));
		//	}catch(Exception ex){
		//	    ex.printStackTrace();
		//    }
  //	} 
  %>
   -->