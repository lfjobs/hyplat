package hy.ea.invoicing.service.impl;

import hy.ea.invoicing.service.CcpbsglService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
@Service
public class CcpbsglServiceImpl implements CcpbsglService {
	
	/**
	 * 资产负债表excel导出
	 */
	@Override
	public synchronized InputStream zfShowExcel( Object[][] titlea,Object[][] titlea2,Map<String, Object[]> titlea3,Object[] objs) {
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
			totalx2Format.setBorder(Border.NONE,BorderLineStyle.THIN);
			WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			
			// 创建格式化对象实例
			WritableCellFormat totalx3Format = new WritableCellFormat(wf2);
			// 垂直居中
			totalx3Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx3Format.setAlignment(Alignment.LEFT);
			totalx3Format.setBorder(Border.NONE,BorderLineStyle.THIN);
			NumberFormat nf = new NumberFormat("#,##0");
			// 创建数字格式化对象实例
			WritableCellFormat numFormat = new WritableCellFormat(nf);
			
			/*int length = list.size();
			int sheetSize=20000;
			int sheetNum=1; 
			if(length%sheetSize>0){   
				sheetNum=length/sheetSize+1;   
			}else{
			    sheetNum=length/sheetSize;   
			}*/
			WritableSheet sheet = workbook.createSheet("message",0);
			sheet.setColumnView(0,5);
			sheet.setColumnView(5,5);
			sheet.setColumnView(4,3);
			sheet.setColumnView(1,30);
			sheet.setColumnView(6,30);
			sheet.setColumnView(2,20);
			sheet.setColumnView(3,20);
			sheet.setColumnView(7,20);
			sheet.setColumnView(8,20);
			sheet.mergeCells(0,0,10,0);
			sheet.mergeCells(2,2,5,2);
			String td="报表日期："+(objs[1]==null?"":objs[1].toString());
			String dw="单位："+(objs[0]==null?"":objs[0].toString());
			sheet.addCell(new Label(0, 0, "资产负债表", totalx2Format));	
			sheet.addCell(new Label(1, 2, "编制单位：", totalx2Format));	
			sheet.addCell(new Label(2, 2, objs[2]==null?"":objs[2].toString(), totalx2Format));
			sheet.addCell(new Label(6, 2, td, totalx2Format));
			sheet.addCell(new Label(8, 2, dw, totalx2Format));
			
			sheet.addCell(new Label(0, 4, "序号", totalx2Format));	
			sheet.addCell(new Label(1, 4, "资产", totalx2Format));
			sheet.addCell(new Label(2, 4, "期末余额", totalx2Format));
			sheet.addCell(new Label(3, 4, "年初余额", totalx2Format));
			sheet.addCell(new Label(5, 4, "序号", totalx2Format));	
			sheet.addCell(new Label(6, 4, "负债和所有者权益", totalx2Format));
			sheet.addCell(new Label(7, 4, "期末余额", totalx2Format));
			sheet.addCell(new Label(8, 4, "年初余额", totalx2Format));
			int t1=titlea.length;
			int t2=titlea2.length;
			int h = 5;
			int x=1;
			for (Object[] ch : titlea) {
				sheet.addCell(new Label(1, h, ch[1]==null?"":ch[1].toString(), totalx3Format));
				sheet.addCell(new Label(0, h, ch[0].toString(), totalx3Format));
				Object[] sj=titlea3.get(ch[2].toString());
				if(sj.length>0){
					sheet.addCell(new Number(2, h, Double.valueOf( sj[2]==null?"0.00":sj[1].toString()), numFormat));
					sheet.addCell(new Number(3, h, Double.valueOf( sj[1]==null?"0.00":sj[1].toString()), numFormat));
				}
				h++;
				x++;
			}
			int h2 = 5;
			for (Object[] ch2: titlea2) {
				sheet.addCell(new Label(6, h2, ch2[1]==null?"":ch2[1].toString(), totalx3Format));
				sheet.addCell(new Label(5, h2, ch2[0].toString(), totalx3Format));
				Object[] sj=titlea3.get(ch2[2].toString());
				if(sj.length>0){
					sheet.addCell(new Number(7, h2, Double.valueOf( sj[2]==null?"0.00":sj[1].toString()), numFormat));
					sheet.addCell(new Number(8, h2, Double.valueOf( sj[1]==null?"0.00":sj[1].toString()), numFormat));
				}
				h2++;
				x++;
			}
			workbook.write();
			workbook.close();
			os.close();   
			/*list.clear();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	/**
	 * 资产负债表及损益表excel导出
	 */
	@Override
	public synchronized InputStream syzfExcel(Object[][] titlea,Object[][] titlea2,Object[][] titlea3,Map<String, Object[]> titlea4,Map<String, Object[]> syval,Object[] objs){
		
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
			totalx2Format.setBorder(Border.NONE,BorderLineStyle.THIN);
			WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			
			// 创建格式化对象实例
			WritableCellFormat totalx3Format = new WritableCellFormat(wf2);
			// 垂直居中
			totalx3Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx3Format.setAlignment(Alignment.LEFT);
			totalx3Format.setBorder(Border.NONE,BorderLineStyle.THIN);
			NumberFormat nf = new NumberFormat("#,##0");
			// 创建数字格式化对象实例
			WritableCellFormat numFormat = new WritableCellFormat(nf);
			
			WritableSheet sheet = workbook.createSheet("message",0);
			sheet.setColumnView(0,5);
			sheet.setColumnView(5,5);
			sheet.setColumnView(4,3);
			sheet.setColumnView(1,30);
			sheet.setColumnView(6,30);
			sheet.setColumnView(2,20);
			sheet.setColumnView(3,20);
			sheet.setColumnView(7,20);
			sheet.setColumnView(8,20);
			sheet.mergeCells(0,0,10,0);
			sheet.mergeCells(2,2,5,2);
			String td="报表日期："+(objs[1]==null?"":objs[1].toString());
			String dw="单位："+(objs[0]==null?"":objs[0].toString());
			sheet.addCell(new Label(0, 0, "资产负债表", totalx2Format));	
			sheet.addCell(new Label(1, 2, "编制单位：", totalx2Format));	
			sheet.addCell(new Label(2, 2, objs[2]==null?"":objs[2].toString(), totalx2Format));
			sheet.addCell(new Label(6, 2, td, totalx2Format));
			sheet.addCell(new Label(8, 2, dw, totalx2Format));
			
			sheet.addCell(new Label(0, 4, "序号", totalx2Format));	
			sheet.addCell(new Label(1, 4, "资产", totalx2Format));
			sheet.addCell(new Label(2, 4, "期末余额", totalx2Format));
			sheet.addCell(new Label(3, 4, "年初余额", totalx2Format));
			sheet.addCell(new Label(5, 4, "序号", totalx2Format));	
			sheet.addCell(new Label(6, 4, "负债和所有者权益", totalx2Format));
			sheet.addCell(new Label(7, 4, "期末余额", totalx2Format));
			sheet.addCell(new Label(8, 4, "年初余额", totalx2Format));
			int t1=titlea.length;
			int t2=titlea2.length;
			int h = 5;
			for (Object[] ch : titlea) {
				sheet.addCell(new Label(1, h, ch[1]==null?"":ch[1].toString(), totalx3Format));
				sheet.addCell(new Label(0, h, ch[0].toString(), totalx3Format));
				Object[] sj=titlea4.get(ch[2].toString());
				if(sj.length>0){
					sheet.addCell(new Number(2, h, Double.valueOf( sj[2]==null?"0.00":sj[1].toString()), numFormat));
					sheet.addCell(new Number(3, h, Double.valueOf( sj[1]==null?"0.00":sj[1].toString()), numFormat));
				}
				h++;
			}
			int h2 = 5;
			for (Object[] ch2: titlea2) {
				sheet.addCell(new Label(6, h2, ch2[1]==null?"":ch2[1].toString(), totalx3Format));
				sheet.addCell(new Label(5, h2, ch2[0].toString(), totalx3Format));
				Object[] sj=titlea4.get(ch2[2].toString());
				if(sj.length>0){
					sheet.addCell(new Number(7, h2, Double.valueOf( sj[2]==null?"0.00":sj[1].toString()), numFormat));
					sheet.addCell(new Number(8, h2, Double.valueOf( sj[1]==null?"0.00":sj[1].toString()), numFormat));
				}
				h2++;
			}
			
			WritableSheet sheet2 = workbook.createSheet("message2",1);
			sheet2.setColumnView(0,30);
			sheet2.setColumnView(1,20);
			sheet2.setColumnView(2,30);
			sheet2.setColumnView(3,10);
			sheet2.setColumnView(4,30);
			sheet2.mergeCells(0,0,4,0);
			sheet2.mergeCells(0,2,1,2);
			sheet2.mergeCells(2,2,3,2);
			sheet2.addCell(new Label(0, 0, "损益表", totalx2Format));	
			sheet2.addCell(new Label(0, 2, "编制单位："+(objs[2]==null?"":objs[2].toString()), totalx2Format));	
			sheet2.addCell(new Label(2, 2, "年月："+(objs[1]==null?"":objs[1].toString()), totalx2Format));
			sheet2.addCell(new Label(4, 2, "单位："+(objs[0]==null?"":objs[0].toString()), totalx2Format));
			
			sheet2.addCell(new Label(0, 4, "项目", totalx2Format));	
			sheet2.addCell(new Label(1, 4, "项次", totalx2Format));
			sheet2.addCell(new Label(2, 4, "本月数", totalx2Format));
			sheet2.addCell(new Label(3, 4, "%", totalx2Format));
			sheet2.addCell(new Label(4, 4, "年度累计数", totalx2Format));	
			int h3 = 5;
			if(titlea!=null){
				for (Object[] ch : titlea) {
					sheet.addCell(new Label(0, h, ch[1]==null?"":ch[1].toString(), totalx3Format));
					sheet.addCell(new Label(1, h, ch[0]==null?"":ch[0].toString(), totalx3Format));
					Object [] s=syval.get(ch[1]);
					sheet.addCell(new Number(2, h, Double.parseDouble(s[1].toString()),numFormat));
					sheet.addCell(new Number(3, h, Double.parseDouble(s[2].toString()),numFormat));
					sheet.addCell(new Number(4, h, Double.parseDouble(s[3].toString()),numFormat));
					h++;
				}
			}
			workbook.write();
			workbook.close();
			os.close();   
			/*list.clear();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	
	
	
	/**
	 * 损益表excel导出
	 */
	@Override
	public synchronized InputStream syShowExcel( Object[][] titlea,Object[] objs,Map<String, Object[]> syval) {
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
			
			WritableFont wf2 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			// 创建格式化对象实例
			WritableCellFormat totalx3Format = new WritableCellFormat(wf2);
			// 垂直居中
			totalx3Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx3Format.setAlignment(Alignment.LEFT);
			
			NumberFormat nf = new NumberFormat("#,##0");
			// 创建数字格式化对象实例
			WritableCellFormat numFormat = new WritableCellFormat(nf);
			
			/*int length = list.size();
			int sheetSize=20000;
			int sheetNum=1; 
			if(length%sheetSize>0){   
				sheetNum=length/sheetSize+1;   
			}else{
			    sheetNum=length/sheetSize;   
			}*/
			WritableSheet sheet = workbook.createSheet("message",0);

			sheet.setColumnView(0,30);
			sheet.setColumnView(1,20);
			sheet.setColumnView(2,30);
			sheet.setColumnView(3,10);
			sheet.setColumnView(4,30);
			sheet.mergeCells(0,0,4,0);
			sheet.mergeCells(0,2,1,2);
			sheet.mergeCells(2,2,3,2);
			sheet.addCell(new Label(0, 0, "损益表", totalx2Format));	
			sheet.addCell(new Label(0, 2, "编制单位："+(objs[2]==null?"":objs[2].toString()), totalx2Format));	
			sheet.addCell(new Label(2, 2, "年月："+(objs[1]==null?"":objs[1].toString()), totalx2Format));
			sheet.addCell(new Label(4, 2, "单位："+(objs[0]==null?"":objs[0].toString()), totalx2Format));
			
			sheet.addCell(new Label(0, 4, "项目", totalx2Format));	
			sheet.addCell(new Label(1, 4, "项次", totalx2Format));
			sheet.addCell(new Label(2, 4, "本月数", totalx2Format));
			sheet.addCell(new Label(3, 4, "%", totalx2Format));
			sheet.addCell(new Label(4, 4, "年度累计数", totalx2Format));	
			int h = 5;
			
			if(titlea!=null){
				for (Object[] ch : titlea) {
					sheet.addCell(new Label(0, h, ch[1]==null?"":ch[1].toString(), totalx3Format));
					sheet.addCell(new Label(1, h, ch[0]==null?"":ch[0].toString(), totalx3Format));
					if(syval.size()>0){
						Object [] s=syval.get(ch[1]);
						sheet.addCell(new Number(2, h, Double.parseDouble(s[1].toString()),numFormat));
						sheet.addCell(new Number(3, h, Double.parseDouble(s[2].toString()),numFormat));
						sheet.addCell(new Number(4, h, Double.parseDouble(s[3].toString()),numFormat));
					}
					h++;
				}
			}
			workbook.write();
			workbook.close();
			os.close();   
			/*list.clear();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}
	
	@Override
	public synchronized InputStream syVoucherExcel(String json){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("First Sheet",0);
			sheet.setColumnView(10,15);sheet.setColumnView(11,15);//设置第10列和第11列的宽度为
			//设置excel里的文本为居中显示
			WritableFont titleWf = new WritableFont(WritableFont.createFont("仿宋_GB2312"),// 字体  
                    10,//WritableFont.DEFAULT_POINT_SIZE,   // 字号  
                    WritableFont.BOLD,                  // 粗体  
                    false,                                 // 斜体  
                    UnderlineStyle.NO_UNDERLINE,            // 下划线  
                    Colour.BLACK,                       // 字体颜色  
                    ScriptStyle.NORMAL_SCRIPT); 
			WritableCellFormat cellFormat=new WritableCellFormat(titleWf); 
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setWrap(true); 
			
			// 创建数字格式化对象实例，并设置为千分位显示
			WritableCellFormat numFormat = new WritableCellFormat(new NumberFormat("#,##0.00"));
			numFormat.setAlignment(Alignment.RIGHT);
			//设置excel页面的头部
			sheet.mergeCells(6,0,8,0);
			sheet.addCell(new Label(6,0,"试算表",cellFormat));
			//第二行数据
			for(int i=0;i<12;i++){
				if(i==3){
					sheet.mergeCells(i,1,i+1,1);
					sheet.addCell(new Label(i,1,"期初",cellFormat));
					continue;
				}
				if(i==5){
					sheet.mergeCells(i,1,i+1,1);
					sheet.addCell(new Label(i,1,"期中",cellFormat));
					continue;
				}
				if(i==7){
					sheet.mergeCells(i,1,i+1,1);
					sheet.addCell(new Label(i,1,"期末",cellFormat));
					continue;
				}
				if(i==10){
					sheet.mergeCells(i,1,i+1,1);
					sheet.addCell(new Label(i,1,"期末本位币",cellFormat));
					continue;
				}
				sheet.addCell(new Label(i,1,"",cellFormat));
			}
			//第三行数据
			sheet.addCell(new Label(0,2,"会计科目",cellFormat));
			sheet.addCell(new Label(1,2,"科目说明",cellFormat));
			sheet.addCell(new Label(2,2,"部门",cellFormat));
			//有重复数据，所以在这里设置了个循环
			for(int i=3;i<9;i++){
				if(i%2!=0){
					sheet.addCell(new Label(i,2,"借方金额",cellFormat));
				}else{
					sheet.addCell(new Label(i,2,"贷方金额",cellFormat));
				}
			}
			sheet.addCell(new Label(9,2,"币别",cellFormat));
			sheet.addCell(new Label(10,2,"借方本位币金额",cellFormat));
			sheet.addCell(new Label(11,2,"贷方本位币金额",cellFormat));
			//excel 要显示的主题内容
			
			JSONArray array=JSON.parseArray(json);
			for(int i=0;i<array.size();i++){
				JSONArray obj=(JSONArray) array.get(i);
				for(int r=0;r<obj.size();r++){
					//判断当前列的内容是否是金额
					if(r!=0&&r!=1&&r!=2&&r!=9){
						sheet.addCell(new Number(r,i+3,Double.valueOf(obj.get(r).toString()),numFormat));
					}else{
						sheet.addCell(new Label(r,i+3,obj.get(r).toString()));
					}	
				}
			}
			workbook.write();
			workbook.close();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
	  /**
     * 流水账导出excel
     */
	@Override
	public InputStream syAccountExcel(String json1, String json2,String type) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("First Sheet",0);
			//设置excel里的文本为居中显示
			WritableFont titleWf = new WritableFont(WritableFont.createFont("仿宋_GB2312"),// 字体  
                    10,//WritableFont.DEFAULT_POINT_SIZE,   // 字号  
                    WritableFont.BOLD,                  // 粗体  
                    false,                                 // 斜体  
                    UnderlineStyle.NO_UNDERLINE,            // 下划线  
                    Colour.BLACK,                       // 字体颜色  
                    ScriptStyle.NORMAL_SCRIPT); 
			WritableCellFormat cellFormat=new WritableCellFormat(titleWf); 
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setWrap(true); 
			// 创建数字格式化对象实例
			NumberFormat nf = new NumberFormat("#,##0");
			WritableCellFormat numFormat = new WritableCellFormat(nf);
			numFormat.setAlignment(Alignment.RIGHT);
			if(type.equals("00")){
				sheet.setColumnView(0,15);
				sheet.setColumnView(1,30);sheet.setColumnView(8,15);//列的宽度为
				//设置excel页面的头部
				sheet.mergeCells(3,0,5,0);//合并单元格
				sheet.addCell(new Label(3,0,"现金流水账",cellFormat));
				//汇总
				for(int i=0;i<10;i++){
					if(i==0){
						sheet.addCell(new Label(i,1,"现金结余汇总表",cellFormat));
						continue;
					}
					if(i==1){
						sheet.mergeCells(i,1,i+1,1);
						sheet.addCell(new Label(i,1,"（按币别，日期排序产生报表）",cellFormat));
						continue;
					}
					sheet.addCell(new Label(i,1,"",cellFormat));
				}
				sheet.addCell(new Label(0,2,"日期",cellFormat));
				sheet.addCell(new Label(1,2,"科目代号",cellFormat));
				sheet.addCell(new Label(2,2,"科目说明",cellFormat));
				sheet.addCell(new Label(3,2,"币别",cellFormat));
				sheet.addCell(new Label(4,2,"前日余额",cellFormat));
				sheet.addCell(new Label(5,2,"收入金额",cellFormat));
				sheet.addCell(new Label(6,2,"支出金额",cellFormat));
				sheet.addCell(new Label(7,2,"本日结余",cellFormat));
				sheet.addCell(new Label(8,2,"",cellFormat));
				sheet.addCell(new Label(9,2,"",cellFormat));
				//excel 要显示的主题内容提要
				JSONArray array=JSON.parseArray(json1);
				int mx=array.size();
				for(int i=0;i<mx;i++){
					JSONArray obj=(JSONArray) array.get(i);
					for(int r=0;r<obj.size();r++){
						if(r!=0&&r!=1&&r!=2&&r!=3){
							Label la=new Label(r,i+3,obj.get(r).toString(),numFormat);
							sheet.addCell(la);
						}else{
							Label la=new Label(r,i+3,obj.get(r).toString());
							sheet.addCell(la);
						}	
					}
				}
				//明细
				for(int i=0;i<10;i++){
					if(i==0){
						sheet.addCell(new Label(i,mx+3,"现金结余明细表",cellFormat));
						continue;
					}
					if(i==1){
						sheet.mergeCells(i,mx+3,i+2,mx+3);
						sheet.addCell(new Label(i,mx+3,"（按币别，日期，凭证流水号排序产生报表）",cellFormat));
						continue;
					}
					sheet.addCell(new Label(i,mx+3,"",cellFormat));
				}
				sheet.addCell(new Label(0,mx+4,"日期",cellFormat));
				sheet.addCell(new Label(1,mx+4,"科目代号",cellFormat));
				sheet.addCell(new Label(2,mx+4,"科目说明",cellFormat));
				sheet.addCell(new Label(3,mx+4,"币别",cellFormat));
				sheet.addCell(new Label(4,mx+4,"前日余额",cellFormat));
				sheet.addCell(new Label(5,mx+4,"收入金额",cellFormat));
				sheet.addCell(new Label(6,mx+4,"支出金额",cellFormat));
				sheet.addCell(new Label(7,mx+4,"本日结余",cellFormat));
				sheet.addCell(new Label(8,mx+4,"凭证流水号",cellFormat));
				sheet.addCell(new Label(9,mx+4,"摘要",cellFormat));
				//excel 要显示的主题内容提要
				JSONArray array1=JSON.parseArray(json2);
				for(int i=0;i<array1.size();i++){
					JSONArray obj=(JSONArray) array.get(i);
					for(int r=0;r<obj.size();r++){
						if(r!=0&&r!=1&&r!=2&&r!=3&&r!=8&&r!=9){
							Label la=new Label(r,i+mx+5,obj.get(r).toString(),numFormat);
							sheet.addCell(la);
						}else{
							Label la=new Label(r,i+mx+5,obj.get(r).toString());
							sheet.addCell(la);
						}	
					}
				}
			}else if(type.equals("01")){
				sheet.setColumnView(0,15);
				sheet.setColumnView(1,30);sheet.setColumnView(2,15);sheet.setColumnView(11,15);//列的宽度为
				//设置excel页面的头部
				sheet.mergeCells(4,0,7,0);//合并单元格
				sheet.addCell(new Label(4,0,"银行流水账",cellFormat));
				//汇总
				for(int i=0;i<13;i++){
					if(i==0){
						sheet.addCell(new Label(i,1,"银行结余汇总表",cellFormat));
						continue;
					}
					if(i==1){
						sheet.mergeCells(i,1,i+1,1);
						sheet.addCell(new Label(i,1,"（按币别，银行账户，日期排序）",cellFormat));
						continue;
					}
					sheet.addCell(new Label(i,1,"",cellFormat));
				}
				sheet.addCell(new Label(0,2,"日期",cellFormat));
				sheet.addCell(new Label(1,2,"银行账号",cellFormat));
				sheet.addCell(new Label(2,2,"银行开户行名称",cellFormat));
				sheet.addCell(new Label(3,2,"开户人",cellFormat));
				sheet.addCell(new Label(4,2,"币别",cellFormat));
				sheet.addCell(new Label(5,2,"会计科目",cellFormat));
				sheet.addCell(new Label(6,2,"科目说明",cellFormat));
				sheet.addCell(new Label(7,2,"前日余额",cellFormat));
				sheet.addCell(new Label(8,2,"收入金额",cellFormat));
				sheet.addCell(new Label(9,2,"支出金额",cellFormat));
				sheet.addCell(new Label(10,2,"本日结余",cellFormat));
				sheet.addCell(new Label(11,2,"",cellFormat));
				sheet.addCell(new Label(12,2,"",cellFormat));
				//excel 要显示的主题内容提要
				JSONArray array=JSON.parseArray(json1);
				int mx=array.size();
				for(int i=0;i<mx;i++){
					JSONArray obj=(JSONArray) array.get(i);
					for(int r=0;r<obj.size();r++){
						if(r!=0&&r!=1&&r!=2&&r!=3&&r!=4&&r!=5&&r!=6){
							Label la=new Label(r,i+3,obj.get(r).toString(),numFormat);
							sheet.addCell(la);
						}else{
							Label la=new Label(r,i+3,obj.get(r).toString());
							sheet.addCell(la);
						}	
					}
				}
				//明细
				for(int i=0;i<13;i++){
					if(i==0){
						sheet.addCell(new Label(i,mx+3,"银行结余明细表",cellFormat));
						continue;
					}
					if(i==1){
						sheet.mergeCells(i,mx+3,i+2,mx+3);
						sheet.addCell(new Label(i,mx+3,"（按币别，银行账户，日期，凭证流水号排序）",cellFormat));
						continue;
					}
					sheet.addCell(new Label(i,mx+3,"",cellFormat));
				}
				sheet.addCell(new Label(0,mx+4,"日期",cellFormat));
				sheet.addCell(new Label(1,mx+4,"银行账号",cellFormat));
				sheet.addCell(new Label(2,mx+4,"银行开户行名称",cellFormat));
				sheet.addCell(new Label(3,mx+4,"开户人",cellFormat));
				sheet.addCell(new Label(4,mx+4,"币别",cellFormat));
				sheet.addCell(new Label(5,mx+4,"会计科目",cellFormat));
				sheet.addCell(new Label(6,mx+4,"科目说明",cellFormat));
				sheet.addCell(new Label(7,mx+4,"前日余额",cellFormat));
				sheet.addCell(new Label(8,mx+4,"收入金额",cellFormat));
				sheet.addCell(new Label(9,mx+4,"支出金额",cellFormat));
				sheet.addCell(new Label(10,mx+4,"本日结余",cellFormat));
				sheet.addCell(new Label(11,mx+4,"凭证流水号",cellFormat));
				sheet.addCell(new Label(12,mx+4,"摘要",cellFormat));
				//excel 要显示的主题内容提要
				JSONArray array1=JSON.parseArray(json2);
				for(int i=0;i<array1.size();i++){
					JSONArray obj=(JSONArray) array.get(i);
					for(int r=0;r<obj.size();r++){
						if(r!=0&&r!=1&&r!=2&&r!=3&&r!=4&&r!=5&&r!=6&&r!=11&&r!=12){
							Label la=new Label(r,i+mx+5,obj.get(r).toString(),numFormat);
							sheet.addCell(la);
						}else{
							Label la=new Label(r,i+mx+5,obj.get(r).toString());
							sheet.addCell(la);
						}	
					}
				}
			}
			workbook.write();
			workbook.close();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ByteArrayInputStream(out.toByteArray());
	}
}
