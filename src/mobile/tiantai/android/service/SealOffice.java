package mobile.tiantai.android.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import jxl.Workbook;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.License;
import com.aspose.words.RelativeHorizontalPosition;
import com.aspose.words.SaveFormat;
import com.aspose.words.Shape;
import com.aspose.words.WrapType;

public class SealOffice
{
	private static final Logger logger = LoggerFactory.getLogger(SealOffice.class);
	

	//将图片印章插入到Word
	public static String  sealWord(String filepath,String fileImage,String realPath){
		String result = "0";
		try {
			File docIn = new File(realPath+filepath);
			File image = new File(realPath+fileImage);
			License license = new License();
			license.setLicense(new FileInputStream(realPath+"js\\common\\license.xml"));
			Document doc = new Document(new FileInputStream(docIn));
			DocumentBuilder builder = new DocumentBuilder(doc);
			builder.moveToDocumentEnd();
			double rightMargin = builder.getPageSetup().getRightMargin();
			Shape shape = builder.insertImage(new FileInputStream(image));
			shape.setWrapType(WrapType.NONE);
			shape.setBehindText(false);
			shape.setRelativeHorizontalPosition(RelativeHorizontalPosition.PAGE);
			shape.setLeft(builder.getPageSetup().getPageWidth()
					- shape.getWidth() - rightMargin);
			doc.save(realPath+filepath, SaveFormat.DOC);
			
			logger.info("finished~~~~~");
		} catch (Exception e) {
			result = "1";
			logger.error("操作异常", e);
		}
		return result;
		
		
	}
	
	
	//将图片印章插入到Excel
	public static String sealExcel(String filePath,String imagePath,String realPath){
		String result = "0";
		try {
			
			//Excel获得文件
			Workbook wb=Workbook.getWorkbook(new File(realPath+filePath));
			//打开一个文件的副本，并且指定数据写回到原文件
			WritableWorkbook book=
			Workbook.createWorkbook(new File(realPath+filePath),wb);
			//添加一个工作表
			WritableSheet sheet=book.createSheet("seal",10);
			File file=new File(realPath+imagePath);
			 
			WritableImage image=new WritableImage(1, 4, 6, 18,file);
			sheet.addImage(image);
			book.write();
			book.close();
			}catch(Exception e) {
				result = "1";
			logger.info("值：{}", e);
			}

		        
		    return result;
 }
		
		

	
	//将图片印章插入到Html
	public static String sealHtml(String filePath,String imagePath){
		String result = "0";
		 try {

			 File file = new File(filePath); 
			 BufferedWriter bw=null;
			
			 bw=new BufferedWriter(new FileWriter(file,true));
			 bw.write("<div style='text-align:center;'><img src='"+imagePath+"'/></div>");
			 bw.close();
		
		
		 } catch (Exception e) {
			result="1";
			logger.error("操作异常", e);
		} 
		
		return result;
	}
	
  public static void main(String[] args) {
	 SealOffice.sealExcel("","", "");
}
	

	

}
