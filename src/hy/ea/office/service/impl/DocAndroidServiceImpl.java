package hy.ea.office.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.office.DocumentFileAttach;
import hy.ea.office.service.DocAndroidService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

@Service
@Transactional
public class DocAndroidServiceImpl implements DocAndroidService {

	public static int j = 0;

	// ////////////////////////////////////////////poi//////////////////////////////////////

	public static void writeFile(String content, String path,String filetype) {
		FileOutputStream fos = null;
		BufferedWriter bw = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			if(filetype.equals("W")){
			 bw = new BufferedWriter(new OutputStreamWriter(fos, "GB2312"));
			}else{
			 bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			}
			bw.write(content);
		} catch (FileNotFoundException fnfe) {
			fnflogger.error("操作异常", e);
		} catch (IOException ioe) {
			iologger.error("操作异常", e);
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fos != null)
					fos.close();
			} catch (IOException ie) {
			}
		}
	}

	/**
	 * 
	 * wordToHtml
	 */
	public static int convertWordToHtml(String fileName, String outPutFile,String picpath) {
		j=0;
		int flag = 0;
		HWPFDocument wordDocument;
		try {
			wordDocument = new HWPFDocument(new FileInputStream(fileName));

			WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
					DocumentBuilderFactory.newInstance().newDocumentBuilder()
							.newDocument());
			List pics = wordDocument.getPicturesTable().getAllPictures();
			final String[] filenamess = new String[100];
			wordToHtmlConverter.setPicturesManager(new PicturesManager() {

				public String savePicture(byte[] content,
						PictureType pictureType, String suggestedName,
						float widthInches, float heightInches) {
					logger.info("值：{}", suggestedName);
					logger.info("值：{}", j);
					filenamess[j] = suggestedName;
					   j++;
					return "test/" + suggestedName;
				}
			});

			wordToHtmlConverter.processDocument(wordDocument);

			if (pics != null) {
				for (int i = 0; i < pics.size(); i++) {
					Picture pic = (Picture) pics.get(i);

					logger.info("_________");
					logger.info("调试信息");
					try {
						if (filenamess[i] != null
								&& !filenamess[i].toString().equals("null")) {
							pic.writeImageContent(new FileOutputStream(
									picpath+filenamess[i]));
							logger.info("调试信息");
						}

					} catch (FileNotFoundException e) {
						logger.error("操作异常", e);
					}
				}
			}
			Document htmlDocument = wordToHtmlConverter.getDocument();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DOMSource domSource = new DOMSource(htmlDocument);
			StreamResult streamResult = new StreamResult(out);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer serializer = tf.newTransformer();
			serializer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(OutputKeys.METHOD, "html");
			serializer.transform(domSource, streamResult);
			out.close();
			String  content = new String(out.toByteArray());
			content=content.replace("class=\"b1 b2\"", "");
			writeFile(content, outPutFile,"W");
		} catch (Exception e1) {
			flag = 1;
			e1.printStackTrace();
		}

		return flag;
	}

	/**
	 * excelToHtml
	 * 
	 * @param
	 * @return
	 */
	public StringBuffer headerHtmlStart(String title) {
		StringBuffer sb = new StringBuffer("");
		sb
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n");
		sb.append("<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\">\n");
		sb
				.append("<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, must-revalidate\">\n");
		sb.append("<META HTTP-EQUIV=\"expires\" CONTENT=\"0\">\n");
		sb.append("<title>" + title + "</title>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");// 进入页面就刷新下
		return sb;
	}

	public StringBuffer headerHtmlEnd(StringBuffer sb) {
		sb.append("</body>\n");// 进入页面就刷新下
		sb.append("</html>\n");// 进入页面就刷新下

		return sb;
	}

	public int convertExcelToHtml(String excelpath, String htmlpath) {
		int flag = 0;
		try {
			
			
			File sourcefile = new File(excelpath);

			InputStream is = new FileInputStream(sourcefile);

			POIFSFileSystem fs = new POIFSFileSystem(is);

			HSSFWorkbook wb = new HSSFWorkbook(fs);//得到Excel工作簿对象

			StringBuffer sb = headerHtmlStart("excel");

			Sheet sheet = wb.getSheetAt(0);//得到Excel工作表对象

           //获取图片
			
			
			getPictures(wb);
			
			
			int lastRowNum = sheet.getLastRowNum();//取得有效的行数

			Map<String, String> map[] = getRowSpanColSpanMap(sheet);

			sb.append("<table border='0' cellspacing='1' width='100%'>");

			HSSFRow row = null;//行

			HSSFCell cell = null;//单元格

			for (int rowNum = sheet.getFirstRowNum(); rowNum < lastRowNum; rowNum++) {

				row = (HSSFRow) sheet.getRow(rowNum);//通过行号获取行

				if (row == null) {

					sb.append("<tr><td > &nbsp;</td></tr>");

					continue;
				}

				sb.append("<tr>");

				int lastColNum = row.getLastCellNum();

				for (int colNum = 0; colNum < lastColNum; colNum++) {

					cell = row.getCell(colNum);//通过行和列获取单元格

					if (cell == null) {

						sb.append("<td>&nbsp;</td>");

						continue;
					}

					String stringValue = getCellValue(cell);//获取单元格里的值

					if (map[0].containsKey(rowNum + "," + colNum)) {

						String pointString = map[0].get(rowNum + "," + colNum);

						map[0].remove(rowNum + "," + colNum);

						int bottomeRow = Integer
								.valueOf(pointString.split(",")[0]);

						int bottomeCol = Integer
								.valueOf(pointString.split(",")[1]);

						int rowSpan = bottomeRow - rowNum + 1;

						int colSpan = bottomeCol - colNum + 1;

						sb.append("<td rowspan= '" + rowSpan + "' colspan= '"
								+ colSpan + "' ");

					} else if (map[1].containsKey(rowNum + "," + colNum)) {

						map[1].remove(rowNum + "," + colNum);

						continue;

					} else {

						sb.append("<td ");
					}

					HSSFCellStyle cellStyle = cell.getCellStyle();//得到单元格样式

                    
					if (cellStyle != null) {

						short alignment = cellStyle.getAlignment();

						sb.append("align='" + convertAlignToHtml(alignment)
								+ "' ");

						short verticalAlignment = cellStyle
								.getVerticalAlignment();

						sb.append("valign='"
								+ convertVerticalAlignToHtml(verticalAlignment)
								+ "' ");

						HSSFFont hf = cellStyle.getFont(wb);

						short boldWeight = hf.getBoldweight();

						short fontColor = hf.getColor();

						sb.append("style='");

						HSSFPalette palette = wb.getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式

						HSSFColor hc = palette.getColor(fontColor);

						sb.append("font-weight:" + boldWeight + ";"); // 字体加粗

						// logger.info("调试信息");

						sb
								.append("font-size: " + hf.getFontHeight() / 2
										+ "%;"); // 字体大小

						String fontColorStr = convertToStardColor(hc);

						if (fontColorStr != null
								&& !"".equals(fontColorStr.trim())) {

							sb.append("color:" + fontColorStr + ";"); // 字体颜色
						}

						short bgColor = cellStyle.getFillForegroundColor();

						hc = palette.getColor(bgColor);

						String bgColorStr = convertToStardColor(hc);

						if (bgColorStr != null && !"".equals(bgColorStr.trim())) {

							sb.append("background-color:" + bgColorStr + ";"); // 背景颜色
						}

						short borderColor = cellStyle.getBottomBorderColor();

						hc = palette.getColor(borderColor);

						String borderColorStr = convertToStardColor(hc);

						if (borderColorStr != null
								&& !"".equals(borderColorStr.trim())) {

							sb.append("border-color:" + borderColorStr + ";"); // 边框颜色
						}

						sb.append("' ");
						
					}

                  

					sb.append(">");

					if (stringValue == null || "".equals(stringValue.trim())) {

						sb.append(" &nbsp; ");
					} else {

						// 将ascii码为160的空格转换为html下的空格（&nbsp;）
						sb.append(stringValue.replace(String
								.valueOf((char) 160), "&nbsp;"));

					}

					sb.append("</td>");

				}

				sb.append("</tr>");
			}

			sb.append("</table>");
			sb = headerHtmlEnd(sb);

			writeFile(sb.toString(), htmlpath,"E");
		} catch (Exception e) {
			flag = 1;
			logger.error("操作异常", e);
		}

		return flag;
	}

	@SuppressWarnings("unchecked")
	private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

		Map<String, String> map0 = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();

		int mergedNum = sheet.getNumMergedRegions();//得到所有区域数

		CellRangeAddress range = null;

		for (int i = 0; i < mergedNum; i++) {

			range = sheet.getMergedRegion(i);//得到所有区域

			int topRow = range.getFirstRow();//第一行

			int topCol = range.getFirstColumn();//第一列

			int bottomRow = range.getLastRow();//最后一行

			int bottomCol = range.getLastColumn();//最后一列

			map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);

			int tempRow = topRow;

			while (tempRow <= bottomRow) {

				int tempCol = topCol;

				while (tempCol <= bottomCol) {

					map1.put(tempRow + "," + tempCol, "");

					tempCol++;
				}

				tempRow++;
			}

			map1.remove(topRow + "," + topCol);

		}

		Map[] map = { map0, map1 };

		return map;
	}
	
   @SuppressWarnings("unused")
private String getPictures(HSSFWorkbook workbook){
	   List<HSSFPictureData> pictures = workbook.getAllPictures();   
	   HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0); 
          
	    int i = 0;   
	          for (HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {   
		           HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();   
		   
		           if (shape instanceof HSSFPicture) {   
		        	    HSSFPicture pic = (HSSFPicture) shape;   
		        	    int row = anchor.getRow1();   
		        	    int col = anchor.getCol1();
		        	    logger.info("调试信息");   
		        	    int pictureIndex = pic.getPictureIndex()-1;   
		        	    HSSFPictureData picData = pictures.get(pictureIndex);   
		        	    logger.info("调试信息");   
		        	    try {
										savePic(row, picData);
									} catch (Exception e) {
										
										logger.error("操作异常", e);
									}   
		        	             }   

		             i++;   
		         }  
	   return null;

   }
   
   private static void savePic(int i, PictureData pic) throws Exception {   
	   
	          String ext = pic.suggestFileExtension();   
	    
	          byte[] data = pic.getData();   
	          
	          
	          if (ext.equals("jpeg")) {   
	             FileOutputStream out = new FileOutputStream(   
	                      "c:\\test\\pict" + i + ".jpg");   
	              out.write(data);   
	              out.close();   
	          }   
	           if (ext.equals("png")) {   
	              FileOutputStream out = new FileOutputStream(   
	                    "c:\\test\\pict" + i + ".png");   
	            out.write(data);   
	              out.close();   
	          }   
	      }   

	

	private String convertAlignToHtml(short alignment) {

		String align = "left";

		switch (alignment) {

		case HSSFCellStyle.ALIGN_LEFT:
			align = "left";
			break;
		case HSSFCellStyle.ALIGN_CENTER:
			align = "center";
			break;
		case HSSFCellStyle.ALIGN_RIGHT:
			align = "right";
			break;

		default:
			break;
		}

		return align;
	}

	private String convertVerticalAlignToHtml(short verticalAlignment) {

		String valign = "middle";

		switch (verticalAlignment) {

		case HSSFCellStyle.VERTICAL_BOTTOM:
			valign = "bottom";
			break;
		case HSSFCellStyle.VERTICAL_CENTER:
			valign = "center";
			break;
		case HSSFCellStyle.VERTICAL_TOP:
			valign = "top";
			break;
		default:
			break;
		}

		return valign;
	}

	private String convertToStardColor(HSSFColor hc) {

		StringBuffer sb = new StringBuffer("");

		if (hc != null) {

			if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {

				return null;
			}

			sb.append("#");

			for (int i = 0; i < hc.getTriplet().length; i++) {

				sb
						.append(fillWithZero(Integer.toHexString(hc
								.getTriplet()[i])));
			}
		}

		return sb.toString();
	}

	private String fillWithZero(String str) {

		if (str != null && str.length() < 2) {

			return "0" + str;
		}
		return str;
	}

	private String getCellValue(HSSFCell cell) {

		switch (cell.getCellType()) {

		case HSSFCell.CELL_TYPE_NUMERIC:

			DecimalFormat format = new DecimalFormat("#0.##");

			return format.format(cell.getNumericCellValue());

		case HSSFCell.CELL_TYPE_STRING:

			return cell.getStringCellValue();

		default:
			return "";
		}
	}

	public String getHtmlByPoi(String realpath, String basePath,
			DocumentFileAttach attach) {
		String htmlsb = "";

		try {

			String docpath = attach.getFilePath();
			String fullpath = realpath + docpath;
			String filetype = attach.getFileType();
			String filename = attach.getFileName();
			String htmlname = filename.substring(0,
					filename.lastIndexOf(".") + 1)
					+ "html";
			String htmlpath = "document/html/";
			String realhtmlpath = realpath + htmlpath + htmlname;
			String picpath = realpath+"document\\html\\test\\";
			File filehtml = new File(realpath + htmlpath);
			if (!filehtml.exists()) {
				filehtml.mkdirs();
			}
			String urlhtml = basePath + htmlpath + htmlname;

			int flag = 0;
			if (docpath != null) {
				File file = new File(realpath + docpath);
				if (file.exists()) {
					if (filetype.equals("W")) {
						flag = convertWordToHtml(fullpath, realhtmlpath,picpath);
					} else {
						flag = convertExcelToHtml(fullpath, realhtmlpath);
					}

					if (flag == 0) {
						htmlsb = urlhtml;
					}

				}
			}
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return htmlsb;
	}

}
