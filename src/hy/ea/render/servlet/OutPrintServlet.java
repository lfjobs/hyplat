package hy.ea.render.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class OutPrintServlet extends HttpServlet {
	/**
	 * 接受JasperReport的Applet的调用,输出文件
	 * 
	 * @param filename
	 *            输出的文件名称
	 * @return 输出的文件流
	 */
	@SuppressWarnings("unused")
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String fileType = "notknown";
		String fName = request.getParameter("filename");
		String type = request.getParameter("type");
		String fs = System.getProperties().getProperty("file.separator");
		String sep = null;
		if (fs.equals("/"))
			sep = fs;
		else
			sep = "\\";
		String strPathFile = request.getSession().getServletContext()
				.getRealPath(sep);
		// String path = request.getContextPath();
		// String serverPath = request.getScheme() + "://"
		// + request.getServerName() + ":" + request.getServerPort()
		// + path + "/";
		// File renderedFile = new File(strPathFile
		// + fileName);

		// byte[] bb=fName.getBytes("ISO-8859-1");
		// fName=new String(bb,"UTF-8");
		// logger.info("值：{}", fName);
		String ffName = java.net.URLDecoder.decode(fName, "UTF-8");
		String fileName = ffName.trim();
		String postfix = "";
		int lastDot = fileName.lastIndexOf(".");
		if (lastDot != -1) {
			postfix = fileName.substring(lastDot + 1);
			if (postfix.equalsIgnoreCase("docx")
					|| postfix.equalsIgnoreCase("doc")
					|| postfix.equalsIgnoreCase("rtf")) {
				fileType = "word";
				// response.setContentType("application/rtf");
			} else if (postfix.equalsIgnoreCase("pdt")) {
				fileType = "pdf";
				// response.setContentType("application/pdf");
			} else if (postfix.equalsIgnoreCase("xls")) {
				fileType = "excel";
				// response.setContentType("application/xls");
			}
		}
		int lastSlash = fileName.lastIndexOf(fs);
		String downloadName = null;
		if (lastSlash != -1)
			downloadName = fileName.substring(lastSlash + 1);
		else
			downloadName = fileName;

		// File ff = null;
		// try {
		// ff = new File(strPathFile + fileName);
		// } catch (Exception e) {
		// logger.error("操作异常", e);
		// }
		// Long filelength = ff.length();
		// int len = filelength.intValue();
		// // 写流文件到前端浏览器
		// // ServletOutputStream out = response.getOutputStream();
		// response.reset();
		// response.setCharacterEncoding("gb2312");
		// response.setContentType("application/x-download");
		// response.setContentLength(len);
		// String filedisplay = URLEncoder.encode(downloadName, "UTF-8");
		// response.setHeader("Content-disposition", "attachment;filename="
		// + filedisplay);
		//
		// InputStream inStream = new FileInputStream(ff);
		// OutputStream out = response.getOutputStream();
		// byte[] b = new byte[1024];
		// int length;
		// while ((length = inStream.read(b)) > 0)
		// out.write(b, 0, length);
		// out.flush();
		// inStream.close();
		// out.close();

		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-download");
		String filedisplay = URLEncoder.encode(downloadName, "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename="
				+ filedisplay);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream out = response.getOutputStream();
		try {
			bis = new BufferedInputStream(new FileInputStream(strPathFile
					+ fileName));
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[5000];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
			bos.flush();
			
		} catch (IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		
//		if(type!=null&&type.equals("zip")){
//			File file = new File(strPathFile +"document/office.zip");
//			if(file.exists()){
//				file.delete();
//			}
//		}

		// 设置响应头和下载保存的文件名
		// response.reset();
		// response.setContentType("application/x-msdownload"); // windows
		// // response.setHeader("Content-Disposition", "attachment;
		// filename=\""
		// // + new String(downloadName.getBytes("gb2312"), "iso8859-1")
		// // + "\"");
		// response.setHeader("Content-Disposition", "attachment; filename=\""
		// + downloadName + "\"");
		//
		// OutputStream output = null;
		// FileInputStream fis = null;
		// try {
		// File f = new File(strPathFile + fileName);
		// output = response.getOutputStream();
		// fis = new FileInputStream(f);
		// // 设置每次写入缓存大小
		// // byte[] b = new byte[(int) f.length()];
		// // int i = 0;
		// // while ((i = fis.read(b)) > 0) {
		// // output.write(b, 0, i);
		// // }
		// byte[] b = new byte[500];
		// // out.print(f.length());
		// // 把输出流写入客户端
		// int n = 0;
		// while ((n = fis.read(b)) != -1) {
		// output.write(b, 0, n);
		// }
		// output.flush();
		// } catch (Exception e) {
		// logger.error("操作异常", e);
		// } finally {
		// if (fis != null) {
		// fis.close();
		// fis = null;
		// }
		// if (output != null) {
		// output.close();
		// output = null;
		// }
		// }

		// String postfix = "";
		// int lastDot = fileName.lastIndexOf(".");
		// if (lastDot != -1) {
		// postfix = fileName.substring(lastDot + 1);
		// if (postfix.equalsIgnoreCase("docx")
		// || postfix.equalsIgnoreCase("doc")
		// || postfix.equalsIgnoreCase("rtf")) {
		// fileType = "word";
		// exporter = new JRRtfExporter();
		// response.setContentType("application/rtf");
		// } else if (postfix.equalsIgnoreCase("pdt")) {
		// fileType = "pdf";
		// exporter = new JRPdfExporter();
		// response.setContentType("application/pdf");
		// } else if (postfix.equalsIgnoreCase("xls")) {
		// fileType = "excel";
		// exporter = new JRXlsExporter();
		// response.setContentType("application/xls");
		// }
		// }
		// if (exporter == null)
		// return;

		// PrintWriter out = response.getWriter();
		// JasperPrint jasperPrint = null;
		// try {
		// jasperPrint = JasperFillManager.fillReport(strPathFile + fileName,
		// null);
		// } catch (JRException e) {
		// out.println("<html>");
		// out.println("<head>");
		// out.println("<title>JasperReports</title>");
		// out
		// .println("<link rel=\"stylesheet\" type=\"text/css\"
		// href=\"../stylesheet.css\" title=\"Style\">");
		// out.println("</head>");
		//
		// out.println("<body bgcolor=\"white\">");
		//
		// out
		// .println("<span class=\"bnew\">JasperReports encountered this error
		// :</span>");
		// out.println("<pre>");
		//
		// e.printStackTrace(out);
		//
		// out.println("</pre>");
		//
		// out.println("</body>");
		// out.println("</html>");
		//
		// }
		// request.getSession().setAttribute(
		// BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
		// jasperPrint);
		// List jasperPrintList = BaseHttpServlet.getJasperPrintList(request);
		//
		// if (jasperPrintList == null) {
		// throw new ServletException(
		// "No JasperPrint documents found on the HTTP session.");
		// }
		//
		// Boolean isBuffered = Boolean
		// .valueOf(request
		// .getParameter(BaseHttpServlet.BUFFERED_OUTPUT_REQUEST_PARAMETER));
		// if (isBuffered.booleanValue()) {
		// FileBufferedOutputStream fbos = new FileBufferedOutputStream();
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
		// jasperPrintList);
		// exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, fbos);
		//
		// try {
		// // JRRtfExporter exporter = new JRRtfExporter();
		// exporter.exportReport();
		// fbos.close();
		//
		// if (fbos.size() > 0) {
		// // response.setContentType("application/rtf");
		// response.setHeader("Content-Disposition",
		// "inline; filename=" + strPathFile + fileName);
		// response.setContentLength(fbos.size());
		// ServletOutputStream ouputStream = response
		// .getOutputStream();
		//
		// try {
		// fbos.writeData(ouputStream);
		// fbos.dispose();
		// ouputStream.flush();
		// } finally {
		// if (ouputStream != null) {
		// try {
		// ouputStream.close();
		// } catch (IOException ex) {
		// }
		// }
		// }
		// }
		// } catch (JRException e) {
		// throw new ServletException(e);
		// } finally {
		// fbos.close();
		// fbos.dispose();
		// }
		// } else {
		// // response.setContentType("application/rtf");
		// response.setHeader("Content-Disposition", "inline; filename="
		// + strPathFile + fileName);
		//
		// // JRRtfExporter exporter = new JRRtfExporter();
		// exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
		// jasperPrintList);
		//
		// OutputStream ouputStream = response.getOutputStream();
		// exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
		// ouputStream);
		//
		// try {
		// exporter.exportReport();
		// } catch (JRException e) {
		// throw new ServletException(e);
		// } finally {
		// if (ouputStream != null) {
		// try {
		// ouputStream.close();
		// } catch (IOException ex) {
		// }
		// }
		// }
		// }
	}
}
