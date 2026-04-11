package hy.ea.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.ExcelBean;
import hy.ea.service.ShowExcelService;
import hy.plat.bo.BaseBean;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.*;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowExcelServiceImpl implements ShowExcelService {

	@Override
	public synchronized InputStream showExcel( String[] columnHeadings,
			List<BaseBean> list) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			// 创建格式化对象实例
			WritableCellFormat totalx2Format = new WritableCellFormat(wf);
			// 垂直居中
			totalx2Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx2Format.setAlignment(Alignment.CENTRE);
			int length = list.size();
			int sheetSize=20000;
			int sheetNum=1; 
			if(length%sheetSize>0){   
				sheetNum=length/sheetSize+1;   
			}else{
			    sheetNum=length/sheetSize;   
			}
			if(sheetNum == 0 ){
				WritableSheet sheet = workbook.createSheet("第一张",0);
				int h = 0;
				for (String ch : columnHeadings) {
					sheet.addCell(new Label(h, 0, ch, totalx2Format));
					h++;
				}
			}
			for(int kk=0;kk<sheetNum;kk++){   
				WritableSheet sheet = workbook.createSheet("message"+kk,kk);
				int h = 0;
				for (String ch : columnHeadings) {
					sheet.addCell(new Label(h, 0, ch, totalx2Format));
					h++;
				}
				int i = 1;
				try {
					for (BaseBean bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
						int n = 0;

						if(columnHeadings[0]!=null&&columnHeadings[0].equals("序号")){
							jxl.write.Number number = new jxl.write.Number(0, i, i);
							sheet.addCell(number);
							n = 1;
						}
							for (String pr : ((ExcelBean)bean).properties()) {
								if(pr==null||pr==""||pr.equals("null"))
									pr="";
								sheet.addCell(new Label(n, i, pr));
								n++;
							}
							i++;
					}
				} catch (Exception e) {
					for (Object bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
						jxl.write.Number number = new jxl.write.Number(0, i, i);
						sheet.addCell(number);
						int n = 1;
						if (bean instanceof Object[]) {
							Object[] obs=(Object[])bean;
							for (Object pr : obs) {
								sheet.addCell(new Label(n, i, (pr==null?"":pr.toString())));
								n++;
							}
						}else {
							sheet.addCell(new Label(n, i, (bean==null?"":bean.toString())));
						}
						i++;
					}
					
				}
				
			}
			
			workbook.write();
			workbook.close();
			os.close();   
			list.clear();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}
	

		@Override
		public synchronized InputStream goodsShowExcel( String[] columnHeadings,
				List<BaseBean> list) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			WritableWorkbook workbook;
			try {
				workbook = Workbook.createWorkbook(os);
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
						jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
				// 创建格式化对象实例
				WritableCellFormat totalx2Format = new WritableCellFormat(wf);
				// 垂直居中
				totalx2Format.setVerticalAlignment(VerticalAlignment.CENTRE);
				// 水平居中
				totalx2Format.setAlignment(Alignment.CENTRE);
				int length = list.size();
				int sheetSize=20000;
				int sheetNum=1; 
				if(length%sheetSize>0){   
					sheetNum=length/sheetSize+1;   
				}else{
				    sheetNum=length/sheetSize;   
				}
				if(sheetNum == 0 ){
					WritableSheet sheet = workbook.createSheet("message",0);
					int h = 0;
					for (String ch : columnHeadings) {
						sheet.addCell(new Label(h, 0, ch, totalx2Format));
						h++;
					}
				}
				for(int kk=0;kk<sheetNum;kk++){   
					WritableSheet sheet = workbook.createSheet("message"+kk,kk);
					int h = 0;
					for (String ch : columnHeadings) {
						sheet.addCell(new Label(h, 0, ch, totalx2Format));
						h++;
					}
					int i = 1;
					float sundue = 0;
					float price=0;
					float balance=0;
					try {						
						for (BaseBean bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
								jxl.write.Number number = new jxl.write.Number(0, i, i);
								sheet.addCell(number);
								int n = 1;
								for (String pr : ((ExcelBean)bean).properties()) {
									if(pr==null||pr==""||pr.equals("null"))
										pr="";
									sheet.addCell(new Label(n, i, pr));
									if(n==12) {
										try {
											sundue +=Float.parseFloat(pr);
										} catch (Exception e) {
										}
										}
									if(n==13) {
										try {
											price +=Float.parseFloat(pr);
										} catch (Exception e) {
										}
										}
									if(n==15) {
										try {
											balance +=Float.parseFloat(pr);
										} catch (Exception e) {
										}
										}
									n++;
								}
								i++;
						}
					} catch (Exception e) {
						for (Object bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
							jxl.write.Number number = new jxl.write.Number(0, i, i);
							sheet.addCell(number);
							int n = 1;
							if (bean instanceof Object[]) {
								Object[] obs=(Object[])bean;
								for (Object pr : obs) {
								    if(n==11&&pr!=null){
								    	float tempFloat= Math.round(Float.parseFloat(pr.toString()) * 100) / 100f;
								    	jxl.write.Number temp = new jxl.write.Number(n, i,tempFloat);
										sheet.addCell(temp);
									}else if(n==12&&pr!=null){
										float tempFloat= Math.round(Float.parseFloat(pr.toString()) * 100) / 100f;
								    	jxl.write.Number temp = new jxl.write.Number(n, i,tempFloat);
										sheet.addCell(temp);
										sundue +=Float.parseFloat(pr.toString());
									}else if(n==13&&pr!=null){
										float tempFloat= Math.round(Float.parseFloat(pr.toString()) * 100) / 100f;
								    	jxl.write.Number temp = new jxl.write.Number(n, i,tempFloat);
										sheet.addCell(temp);
										price +=Float.parseFloat(pr.toString());
									}else if(n==15&&pr!=null){
										float tempFloat= Math.round(Float.parseFloat(pr.toString()) * 100) / 100f;
								    	jxl.write.Number temp = new jxl.write.Number(n, i,tempFloat);
										sheet.addCell(temp);
										balance +=Float.parseFloat(pr.toString());
									}else{
										sheet.addCell(new Label(n, i, (pr==null?"":pr.toString())));
									}
									n++;
								}
							}else {
								sheet.addCell(new Label(n, i, (bean==null?"":bean.toString())));
							}
							i++;
						}
						
					}
					jxl.write.Number number1 = new jxl.write.Number(12, i+3,Math.round(sundue * 100) / 100f);
					jxl.write.Number number2 = new jxl.write.Number(13, i+3,Math.round(price * 100) / 100f);
					jxl.write.Number number3 = new jxl.write.Number(15, i+3,Math.round(balance * 100) / 100f);
					sheet.addCell(number1);
					sheet.addCell(number2);
					sheet.addCell(number3);
					sheet.mergeCells(0, i+3, 1,i+3);
					sheet.addCell(new Label(0, i+3, "合  计", totalx2Format));					
				}				
				workbook.write();
				workbook.close();
				os.close();   
				list.clear();
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
			
			return new ByteArrayInputStream(os.toByteArray());
		}
	/**
	 * 跟据需求（归档报表）
	 */
	@Override
	public synchronized InputStream goodsShowExcels( String[] columnHeadings,
				List<BaseBean> list) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			WritableWorkbook workbook;
			try {
				workbook = Workbook.createWorkbook(os);
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
						jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
				// 创建格式化对象实例
				WritableCellFormat totalx2Format = new WritableCellFormat(wf);
				// 垂直居中
				totalx2Format.setVerticalAlignment(VerticalAlignment.CENTRE);
				// 水平居中
				totalx2Format.setAlignment(Alignment.CENTRE);
				int length = list.size();
				int sheetSize=20000;
				int sheetNum=1; 
				if(length%sheetSize>0){   
					sheetNum=length/sheetSize+1;   
				}else{
				    sheetNum=length/sheetSize;   
				}
				if(sheetNum == 0 ){
					WritableSheet sheet = workbook.createSheet("message",0);
					int h = 0;
					for (String ch : columnHeadings) {
						sheet.addCell(new Label(h, 0, ch, totalx2Format));
						h++;
					}
				}
				for(int kk=0;kk<sheetNum;kk++){   
					WritableSheet sheet = workbook.createSheet("message"+kk,kk);
					int h = 0;
					for (String ch : columnHeadings) {
						sheet.addCell(new Label(h, 0, ch, totalx2Format));
						h++;
					}
					int i = 1;
					float sundue = 0;
					float price=0;
					float balance=0;
					try {						
						for (BaseBean bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
								jxl.write.Number number = new jxl.write.Number(0, i, i);
								sheet.addCell(number);
								int n = 1;
								for (String pr : ((ExcelBean)bean).properties()) {
									if(pr==null||pr==""||pr.equals("null"))
										pr="";
									sheet.addCell(new Label(n, i, pr));
									if(n==30) {
										try {
											sundue +=Float.parseFloat(pr);
										} catch (Exception e) {
										}
										}
									if(n==32) {
										try {
											price +=Float.parseFloat(pr);
										} catch (Exception e) {
										}
										}
									n++;
								}
								i++;
						}
					} catch (Exception e) {
						for (Object bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
							jxl.write.Number number = new jxl.write.Number(0, i, i);
							sheet.addCell(number);
							int n = 1;
							if (bean instanceof Object[]) {
								Object[] obs=(Object[])bean;
								for (Object pr : obs) {
								    if(n==11&&pr!=null){
								    	float tempFloat= Math.round(Float.parseFloat(pr.toString()) * 100) / 100f;
								    	jxl.write.Number temp = new jxl.write.Number(n, i,tempFloat);
										sheet.addCell(temp);
									}else if(n==30&&pr!=null){
										float tempFloat= Math.round(Float.parseFloat(pr.toString()) * 100) / 100f;
								    	jxl.write.Number temp = new jxl.write.Number(n, i,tempFloat);
										sheet.addCell(temp);
										sundue +=Float.parseFloat(pr.toString());
									}else if(n==32&&pr!=null){
										float tempFloat= Math.round(Float.parseFloat(pr.toString()) * 100) / 100f;
								    	jxl.write.Number temp = new jxl.write.Number(n, i,tempFloat);
										sheet.addCell(temp);
										price +=Float.parseFloat(pr.toString());
									}else if(n==33&&pr!=null){
										float tempFloat= Math.round(Float.parseFloat(pr.toString()) * 100) / 100f;
								    	jxl.write.Number temp = new jxl.write.Number(n, i,tempFloat);
										sheet.addCell(temp);
										balance =sundue-price;
									}else{
										sheet.addCell(new Label(n, i, (pr==null?"":pr.toString())));
									}
									n++;
								}
							}else {
								sheet.addCell(new Label(n, i, (bean==null?"":bean.toString())));
							}
							i++;
						}
						
					}
					jxl.write.Number number1 = new jxl.write.Number(30, i+3,Math.round(sundue * 100) / 100f);
					jxl.write.Number number2 = new jxl.write.Number(32, i+3,Math.round(price * 100) / 100f);
					jxl.write.Number number3 = new jxl.write.Number(33, i+3,Math.round((sundue-price) * 100) / 100f);
					sheet.addCell(number1);
					sheet.addCell(number2);
					sheet.addCell(number3);
					sheet.mergeCells(0, i+3, 1,i+3);
					sheet.addCell(new Label(0, i+3, "合  计", totalx2Format));					
				}				
				workbook.write();
				workbook.close();
				os.close();   
				list.clear();
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
			
			return new ByteArrayInputStream(os.toByteArray());
		}
	@Override
	public synchronized List<String[]> ExcelImporter(File path) {
		// TODO Auto-generated method stub
		
		List<String[]> list = new ArrayList<String[]>();   
		try {
			  InputStream is = null;   
	          Workbook workBook = null;
			  is = new FileInputStream(path);   
			  workBook = Workbook.getWorkbook(is);   
			  
			  Sheet sheet = workBook.getSheet(0);
			  //logger.info("调试信息");//查看sheet的列   
	         // logger.info("调试信息");//查看sheet的行   
	          Cell cell = null;//
	          for (int j = 0; j < sheet.getRows(); j++) {   
	        	  String[] dataCells = new String[sheet.getColumns()+1];   
	        	  for(int n=0;n<sheet.getColumns();n++){
	        		  cell=sheet.getCell(n, j);
	        		  dataCells[n]=cell.getContents();
	        	  }
	        	  list.add(dataCells);
	        	  if(j == 1000)break;
	          }
	          workBook.close();//记得关闭   
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		return list;
	}
	
	
	@Override
	public synchronized InputStream showExcelByWater(String[] columnHeadings,
			List<BaseBean> list,String title) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableFont wf = new WritableFont(WritableFont.ARIAL,7,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLUE); // 定义格式 字体 下划线 斜体 粗体 颜色
			// 创建格式化对象实例
			WritableCellFormat totalx2Format = new WritableCellFormat(wf);
			// 垂直居中
			totalx2Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx2Format.setAlignment(Alignment.CENTRE);
			totalx2Format.setBackground(Colour.LIGHT_GREEN);
			totalx2Format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
				
			WritableSheet sheet = workbook.createSheet("Sheet1",0);
			//第一行 报表标题 
			
		
			//创建前4行26个单元格
			for (int i = 0; i < 26; i++) {
				for(int j=0;j<4;j++){
				sheet.addCell(new Label(i, j, "1"));//生成26个单元格第一行
				}
			}
			
			WritableFont wfz = new WritableFont(WritableFont.ARIAL,8,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			// 创建格式化对象实例
			WritableCellFormat totalx1Format = new WritableCellFormat(wfz);
			// 垂直居中
			totalx1Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx1Format.setAlignment(Alignment.CENTRE);
			totalx1Format.setBackground(Colour.LIGHT_GREEN);
			totalx1Format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
			
		    sheet.mergeCells(0,0, 25,0);
			sheet.addCell(new Label(0, 0, title, totalx1Format));
		    //第二行
			sheet.mergeCells(0,1, 12,1);
			sheet.addCell(new Label(0, 1, "费用明细账", totalx2Format));	
			
			sheet.mergeCells(13,1, 20,1);
			sheet.addCell(new Label(13, 1, "现金往来", totalx2Format));	
			
			
			sheet.mergeCells(21,1, 23,1);
			sheet.addCell(new Label(21, 1, "银行往来", totalx2Format));	
			
			sheet.mergeCells(24,1, 25,1);
			sheet.addCell(new Label(24, 1, "总额", totalx2Format));	
			//第三行第四行合并 
    		sheet.mergeCells(0,2,3,3);
    		sheet.addCell(new Label(0, 2, "基本信息管理", totalx2Format));	
    		
    		sheet.mergeCells(4,2,5,3);
    		
    		sheet.addCell(new Label(4, 2, "款源报账时间管理", totalx2Format));	
    		sheet.mergeCells(6,2,7,3);
    		sheet.addCell(new Label(6, 2, "凭证管理", totalx2Format));	
    		
    		sheet.mergeCells(8,2,8,3);
    		sheet.addCell(new Label(8, 2, "摘要", totalx2Format));	
    		
    		sheet.mergeCells(9,2,9,3);
    		sheet.addCell(new Label(9, 2, "会议科目管理", totalx2Format));	
    		
    		
    		sheet.mergeCells(10,2,12,3);
    		sheet.addCell(new Label(10, 2, "单位/数量/单价", totalx2Format));	
    		
    		
    		sheet.mergeCells(13,2,13,3);
    		sheet.addCell(new Label(13, 2, "预入金", totalx2Format));	
    		
    		
    		sheet.mergeCells(14,2,17,3);
    		sheet.addCell(new Label(14, 2, "现金支出", totalx2Format));	
    		
    		
    		sheet.mergeCells(18,2,20,3);
    		sheet.addCell(new Label(18, 2, "库存现金账管理", totalx2Format));	
    		
    		
    		sheet.mergeCells(21,2,23,3);
    		sheet.addCell(new Label(21, 2, "银行现金账管理", totalx2Format));	
    		
    		
    		sheet.mergeCells(24,2,25,3);
    		sheet.addCell(new Label(24, 2, "总余额管理", totalx2Format));	
			

 
    		int h = 0;
			for (String ch : columnHeadings) {
				sheet.addCell(new Label(h, 4, ch, totalx2Format));
				h++;
			}
			
			WritableFont wfc = new WritableFont(WritableFont.ARIAL,7,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			// 创建格式化对象实例
			WritableCellFormat totalx3Format = new WritableCellFormat(wfc);
			// 垂直居中
			totalx3Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx3Format.setAlignment(Alignment.CENTRE);
			totalx3Format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
			
			int i = 5;
			try {
				for (BaseBean bean : list){
						int n = 0;
						for (String pr : ((ExcelBean)bean).properties()) {
							if(pr==null||pr==""||pr.equals("null"))
								pr="";
							sheet.addCell(new Label(n, i, pr,totalx3Format));
							n++;
						}
						i++;
				}
			} catch (Exception e) {
				logger.info("sssssssssssssssss");
		        logger.error("操作异常", e);
				
			}
//			int s = list.size()+5;
//			int z = 0;
//			for (String ch : columnHeadings) {
//				sheet.addCell(new Label(z, s, ch, totalx2Format));
//				z++;
//			}
//			
//			for (BaseBean bean : list){
//				int n = 0;
//				for (String pr : ((ExcelBean)bean).properties()) {
//					if(pr==null||pr==""||pr.equals("null"))
//						pr="";
//					sheet.addCell(new Label(n, s+1, pr,totalx3Format));
//					n++;
//				}
//				s++;
//		   }
    		
			workbook.write();
			workbook.close();
			os.close();   
			list.clear();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}

	@Override
	public InputStream showExcelByReport(String[] columnHeadings, List<BaseBean> list, Object sum,Object count,String reportType) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			// 创建格式化对象实例
			WritableCellFormat totalx2Format = new WritableCellFormat(wf);
			// 垂直居中
			totalx2Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx2Format.setAlignment(Alignment.CENTRE);
			int length = list.size();
			int sheetSize=20000;
			int sheetNum=1;
			if(length%sheetSize>0){
				sheetNum=length/sheetSize+1;
			}else{
				sheetNum=length/sheetSize;
			}
			if(sheetNum == 0 ){
				WritableSheet sheet = workbook.createSheet("第一张",0);
				int h = 0;
				for (String ch : columnHeadings) {
					sheet.addCell(new Label(h, 0, ch, totalx2Format));
					h++;
				}
			}
			for(int kk=0;kk<sheetNum;kk++){
				WritableSheet sheet = workbook.createSheet("message"+kk,kk);
				int h = 0;
				for (String ch : columnHeadings) {
					sheet.addCell(new Label(h, 0, ch, totalx2Format));
					h++;
				}
				int i = 1;
				try {
					for (BaseBean bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
						int n = 0;

						if(columnHeadings[0]!=null&&columnHeadings[0].equals("序号")){
							jxl.write.Number number = new jxl.write.Number(0, i, i);
							sheet.addCell(number);
							n = 1;
						}
						for (String pr : ((ExcelBean)bean).properties()) {
							if(pr==null||pr==""||pr.equals("null"))
								pr="";
							sheet.addCell(new Label(n, i, pr));
							n++;
						}
						i++;
					}
					Object [] sumArray = new Object[]{};
					if(!"oldOrder".equals(reportType)){
						 sumArray = (Object [])sum;
					}
					if(!"order".equals(reportType)){
						if("oldOrder".equals(reportType)){
							sheet.addCell(new Label(13,1,"总金额"));
							sheet.addCell(new Label(14,1,sum.toString()));
						}else {
							Object [] countArray = (Object [])count;
							sheet.addCell(new Label(14,1,"消费总人数"));
							sheet.addCell(new Label(15,1,countArray[1].toString()));
							sheet.addCell(new Label(14,2,"消费总订单"));
							sheet.addCell(new Label(15,2,countArray[0].toString()));
							sheet.addCell(new Label(14,3,"消费总数量"));
							sheet.addCell(new Label(15,3,sumArray[0].toString()));
							sheet.addCell(new Label(14,4,"消费总金额"));
							sheet.addCell(new Label(15,4,sumArray[1].toString()));
							if("gross".equals(reportType)){
								sheet.addCell(new Label(14,5,"毛利润总金额"));
								sheet.addCell(new Label(15,5,sumArray[2].toString()));
							}else if("refin".equals(reportType)){
								sheet.addCell(new Label(14,5,"成本总金额"));
								sheet.addCell(new Label(15,5,sumArray[2].toString()));
							}
						}
					}else {
						sheet.addCell(new Label(14,1,"消费总人数"));
						sheet.addCell(new Label(15,1,sumArray[0].toString()));
						sheet.addCell(new Label(14,2,"消费总订单"));
						sheet.addCell(new Label(15,2,sumArray[1].toString()));
						sheet.addCell(new Label(14,3,"消费总金额"));
						sheet.addCell(new Label(15,3,sumArray[2].toString()));

					}
				} catch (Exception e) {
					for (Object bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
						jxl.write.Number number = new jxl.write.Number(0, i, i);
						sheet.addCell(number);
						int n = 1;
						if (bean instanceof Object[]) {
							Object[] obs=(Object[])bean;
							for (int x=0;x<obs.length;x++){
								if(("refin".equals(reportType)|| "order".equals(reportType)) && x==obs.length-1){
										break;
								}
								sheet.addCell(new Label(n, i, (obs[x]==null?"":obs[x].toString())));
								n++;
							}
						}else {
							sheet.addCell(new Label(n, i, (bean==null?"":bean.toString())));
						}
						i++;
					}
					Object [] sumArray = new Object[]{};
					if(!"oldOrder".equals(reportType)){
						sumArray = (Object [])sum;
					}
					if(!"order".equals(reportType)){
						if("oldOrder".equals(reportType)){
							sheet.addCell(new Label(13,1,"消费总金额"));
							sheet.addCell(new Label(14,1,sum.toString()));
						}else {
							Object [] countArray = (Object [])count;
							sheet.addCell(new Label(14,1,"消费总人数"));
							sheet.addCell(new Label(15,1,countArray[1].toString()));
							sheet.addCell(new Label(14,2,"消费总订单"));
							sheet.addCell(new Label(15,2,countArray[0].toString()));
							sheet.addCell(new Label(14,3,"消费总数量"));
							sheet.addCell(new Label(15,3,sumArray[0].toString()));
							sheet.addCell(new Label(14,4,"消费总金额"));
							sheet.addCell(new Label(15,4,sumArray[1].toString()));
							if("gross".equals(reportType)){
								sheet.addCell(new Label(14,5,"毛利润总金额"));
								sheet.addCell(new Label(15,5,sumArray[2].toString()));
							}else if("refin".equals(reportType)){
								sheet.addCell(new Label(14,5,"成本总金额"));
								sheet.addCell(new Label(15,5,sumArray[2].toString()));
							}
						}
					}else {
						sheet.addCell(new Label(14,1,"消费总人数"));
						sheet.addCell(new Label(15,1,sumArray[0].toString()));
						sheet.addCell(new Label(14,2,"消费总订单"));
						sheet.addCell(new Label(15,2,sumArray[1].toString()));
						sheet.addCell(new Label(14,3,"消费总金额"));
						sheet.addCell(new Label(15,3,sumArray[2].toString()));
					}
				}

			}

			workbook.write();
			workbook.close();
			os.close();
			list.clear();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return new ByteArrayInputStream(os.toByteArray());
	}

}
