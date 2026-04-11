package hy.ea.office.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

import hy.ea.office.service.FileManagerService;
import org.springframework.stereotype.Service;
import sun.awt.shell.ShellFolder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;

@Service
public class FileManagerServiceImpl implements FileManagerService {
	
@SuppressWarnings("unused")
public String office2PDF(String sourcePath,String realPath, String fileName) {
	String OpenOffice_HOME = "C:\\Program Files (x86)\\OpenOffice 4";
	String DocFile = realPath +"\\"+sourcePath;

	String PDFFile = DocFile.replace(DocFile.substring(DocFile.lastIndexOf(".")),".pdf");
	String  PDFName =fileName.replace(fileName.substring(fileName.lastIndexOf(".")),".pdf");
	try {
		File inputFile = new File(DocFile);
		if (!inputFile.exists()) {
			return "nofind";// 找不到源文件, 则返回-1
		}
        
		// 如果目标路径不存在, 则新建该路径
		File outputFile = new File(PDFFile);
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		

		if (OpenOffice_HOME == null)
			return "nofind";
		// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
		if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
			OpenOffice_HOME += "\\";
		}
		// 启动OpenOffice的服务
		String command = OpenOffice_HOME
				+ "program\\soffice.exe -headless -accept=\"socket,port=8100;urp;\" -nofirststartwizard";
		Process pro = Runtime.getRuntime().exec(command);
		// connect to an OpenOffice.org instance running on port 8100
		OpenOfficeConnection connection = new SocketOpenOfficeConnection(
				"127.0.0.1", 8100);
		connection.connect();

		// convert
		DocumentConverter converter = new OpenOfficeDocumentConverter(
				connection);
		converter.convert(inputFile, outputFile);

		// close the connection
		connection.disconnect();
		// 关闭OpenOffice服务的进程
		pro.destroy();
		return PDFName;
	} catch (FileNotFoundException e) {
		logger.error("操作异常", e);
		return "nofind";
	} catch (ConnectException e) {
		logger.error("操作异常", e);
	} catch (IOException e) {
		logger.error("操作异常", e);
	}

	return "fail";
}
	
	
	// txt转化为pdf

	public String makePDF(String sourcePath, String realPath, String fileName) {
		String pdfName = "";
		try {
			String path = realPath + "\\" + sourcePath;
			String dir = path.replace(fileName, "");
			pdfName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
			String pdffullpath = dir + pdfName;
			File filepdf = new File(pdffullpath);
			if (filepdf.exists()) {
				return pdfName;
			}
			// 首先创建一个字体
			BaseFont bfChinese = BaseFont.createFont("STSong-Light",
					"UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
			String line = null;
			Document document;
			document = new Document(PageSize.A4, 50, 50, 50, 50);
			BufferedReader in = new BufferedReader(new FileReader(path));
			PdfWriter.getInstance(document, new FileOutputStream(pdffullpath));
			document.open();
			while ((line = in.readLine()) != null)
				document.add(new Paragraph(12, line, FontChinese));
			document.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pdfName;
	}

	// 2）将pdf转换为swf

	public String pdfToSwf(String sourcePath, String realPath, String fileName)
			throws IOException {
		String sourcePathss = realPath +"\\"+sourcePath.substring(0,sourcePath.lastIndexOf("/"))+"\\"+fileName.substring(0,fileName.lastIndexOf("."))+".swf";
		String targetPathss = sourcePath.substring(0,sourcePath.lastIndexOf("/"))+"\\"+fileName.substring(0,fileName.lastIndexOf("."))+".swf";
		File exit = new File(sourcePathss);
		if(exit.exists()){
			return targetPathss;
		}
		String ext = fileName.substring(fileName.lastIndexOf("."));
		if (ext.equalsIgnoreCase(".txt")) {
			fileName = makePDF(sourcePath, realPath, fileName);// 返回的是pdf的文件名
			sourcePath = sourcePath.replace(sourcePath.substring(sourcePath
					.lastIndexOf(".")), ".pdf");
		}
		if (ext.equalsIgnoreCase(".doc") || ext.equalsIgnoreCase(".docx")
				|| ext.equalsIgnoreCase(".xls")
				|| ext.equalsIgnoreCase(".xlsx")
				|| ext.equalsIgnoreCase(".ppt")
				|| ext.equalsIgnoreCase(".pptx")) {
			try {
				fileName = office2PDF(sourcePath,realPath,fileName);
			} catch (Exception e) {
				
				logger.error("操作异常", e);
			}
			sourcePath = sourcePath.replace(sourcePath.substring(sourcePath
					.lastIndexOf(".")), ".pdf");
		}
		String targetPath = sourcePath.replace(fileName, fileName.substring(0,
				fileName.lastIndexOf('.'))
				+ ".swf");
		String fulltargetPath = '"' + realPath + "\\" + targetPath + '"';
		String fullsourcePath = '"' + realPath + "\\" + sourcePath + '"';
		String fulltargetPath2 = realPath + "\\" + targetPath;
		File tp = new File(fulltargetPath2);
		try{
		if (tp.exists())
			return targetPath;
		String command = "E:\\SWFTools\\pdf2swf.exe" + " " + fullsourcePath
				+ " -o " + fulltargetPath
				+ " -s languagedir=E:\\xpdf-chinese-simplified"+" -T 9";
		Process process = Runtime.getRuntime().exec(command); // 调用外部程序
		final InputStream is1 = process.getInputStream();
		new Thread(new Runnable() {
			public void run() {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						is1));
				try {
					while (br.readLine() != null)
						;
				} catch (IOException e) {
					logger.error("操作异常", e);
				}
			}
		}).start(); // 启动单独的线程来清空process.getInputStream()的缓冲区
		InputStream is2 = process.getErrorStream();
		BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
		StringBuilder buf = new StringBuilder(); // 保存输出结果流
		String line = null;
		while ((line = br2.readLine()) != null)
			buf.append(line); // 循环等待ffmpeg进程结束
		logger.info("输出结果为：: {}", buf);
		}catch(Exception e){
			logger.error("操作异常", e);
		}
		return targetPath;
	}
	
	
	
	
	
	//根据指定的扩展名获取系统图标
	@SuppressWarnings("unused")
	public String[] getSystemIcon(String realPath,String companyID,String[] fileName) {
		  String[] Iconpath = new String[fileName.length];
		  String path = realPath+"upload_files\\office\\fileCabinet\\"+companyID+"\\";
		  for(int i=0;i<fileName.length;i++){
		try {
		    String ext = fileName[i].substring(fileName[i].lastIndexOf('.'));
		    String iconpath = realPath+"upload_files\\fileCabinet\\systemIcon\\"+ext+".jpg";//完整路径
		    String iconpath2 = "upload_files\\fileCabinet\\systemIcon\\"+ext+".jpg";//相对路径
		    File f_Ico = new File(iconpath);// 指定Ico目录
			if(f_Ico.exists()){
				Iconpath[i]=iconpath2.replace("\\","/");
				continue;
			}
			//从shell.dll中得到系统图标
			File file = File.createTempFile("icon", "." + ext);
			Image icon = ShellFolder.getShellFolder(file).getIcon(true);
			int width = icon.getWidth(null); // 得到源图宽
			int height = icon.getHeight(null); // 得到源图长
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, width, height);
			g.drawImage(icon, 0, 0, width, height, null);
			
			//将得到的图标转换成图片
			f_Ico.createNewFile();
			FileOutputStream out = new FileOutputStream(f_Ico); // 输出到文件流
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//			encoder.encode(tag); // JPEG编码
			ImageIO.write(tag, "jpg", out);
			out.close();
			Iconpath[i]=iconpath2.replace("\\","/");
			
		} catch (Exception e) {
			Iconpath[i]="images/ea/office/fileCabinet/default.jpg";
		}
		  }
		return Iconpath;
	}
	
}
