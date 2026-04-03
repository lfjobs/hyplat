<%@ page language="java" import="java.util.*" pageEncoding="GB2312"%><%@ page contentType="text/html;charset=GB2312"%><%@ page import = "Xproer.*" %><%@ page import="org.apache.commons.lang.StringUtils" %><%@ page import="org.apache.commons.fileupload.*" %><%@ page import="org.apache.commons.fileupload.disk.*" %><%@ page import="org.apache.commons.fileupload.servlet.*" %><%
/*	
	更新记录：
		2013-01-25 取消对SmartUpload的使用，改用commons-fileupload组件。因为测试发现SmartUpload有内存泄露的问题。
*/
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String uname = "";// 		= request.getParameter("uid");
String upass = "";// 		= request.getParameter("fid");
String width = "";//图片宽度
String height = "";//图片高度
String remark = "";//图片描述
 
// Check that we have a file upload request
boolean isMultipart = ServletFileUpload.isMultipartContent(request);
FileItemFactory factory = new DiskFileItemFactory();   
ServletFileUpload upload = new ServletFileUpload(factory);
//upload.setSizeMax(10485760);//10MB
List files = null;
try 
{
	files = upload.parseRequest(request);
} 
catch (FileUploadException e) 
{
	out.println("上传文件异常"+e.toString());
    return;
   
}

FileItem imgFile = null;
// 得到所有上传的文件
Iterator fileItr = files.iterator();
// 循环处理所有文件
while (fileItr.hasNext()) 
{
	// 得到当前文件
	imgFile = (FileItem) fileItr.next();
	// 忽略简单form字段而不是上传域的文件域(<input type="text" />等)
	if(imgFile.isFormField())
	{
		String fn = imgFile.getFieldName();
		String fv = imgFile.getString(); 
		if(fn.equals("uname")) uname = fv;
		if(fn.equals("upass")) upass = fv;
		if(fn.equals("width")) width = fv;
		if(fn.equals("height")) height = fv;
		if(fn.equals("remark")) remark = fv;//如果是UTF-8需要进行URL解码
	}
	else 
	{
		break;
	}
}
Uploader up = new Uploader(pageContext,request);
up.SaveFile(imgFile);
out.write(up.GetFilePathRel());%>