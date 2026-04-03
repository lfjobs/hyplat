package hy.ea.human.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.ExcelBean;
import hy.ea.bo.human.vo.SalaryIntegral;
import hy.ea.human.service.LogBookService;
import hy.ea.service.CompanyService;
import hy.plat.bo.BaseBean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/*
 * 工作日志 LogBookAction
 */
@Controller
@Scope("prototype")
public class LogBookServiceImp implements LogBookService{
     
	@Resource
	private CompanyService companyService;
	
	private static int title;
	@Override
	public InputStream showExcel(String[] columnHeadings, List<BaseBean> list) {
		int ct=title==1?6:5;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(os);
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 8,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK); // 定义格式 字体 下划线 斜体 粗体 颜色
			// 创建格式化对象实例
			WritableCellFormat totalx2Format = new WritableCellFormat(wf);
			// 垂直居中
			totalx2Format.setVerticalAlignment(VerticalAlignment.CENTRE);
			// 水平居中
			totalx2Format.setAlignment(Alignment.CENTRE);
			//自动换行
			totalx2Format.setWrap(true);
			CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
			String companyName = companyService.getCompanyByCompanyID(account.getCompanyID()).getCompanyName();
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
				sheet.setRowView(2, 1723);
				sheet.setColumnView(0,4);
				sheet.setColumnView(1,title==1?7:27);
				sheet.setColumnView(2,title==1?27:7);
				sheet.setColumnView(3,title==1?7:11);
				sheet.setColumnView(4,11);
				sheet.setColumnView(5,title==1?11:4);
				sheet.setColumnView(6,4);
				sheet.setColumnView(7,4);
				sheet.setColumnView(8,4);
				sheet.setColumnView(9,4);
				sheet.setColumnView(10,4);
				sheet.setColumnView(11,4);
				sheet.setColumnView(12,4);
				sheet.setColumnView(13,4);
				sheet.setColumnView(14,4);
				sheet.setColumnView(15,4);
				sheet.setColumnView(16,4);
				sheet.setColumnView(17,4);
				sheet.setColumnView(18,4);
				sheet.setColumnView(19,4);
				sheet.setColumnView(20,4);
				sheet.setColumnView(21,4);
				sheet.setColumnView(22,4);
				sheet.setColumnView(23,4);
				sheet.setColumnView(24,4);
				sheet.setColumnView(25,4);
				sheet.setColumnView(26,8);
				sheet.setColumnView(27,8);
				sheet.setColumnView(28,10);
				sheet.setColumnView(29,8);
				sheet.setColumnView(30,10);
				sheet.setColumnView(31,8);
				sheet.setColumnView(32,8);
				
				sheet.mergeCells(0, 0, columnHeadings.length-1,1 );
				sheet.addCell(new Label(0,0,companyName+"  "+(SalaryIntegral.getTitleDate()!=null?SalaryIntegral.getTitleDate():""),totalx2Format));
//				sheet.addCell(new Label(28, 3, "考勤统计表", totalx2Format));

				jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
				jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc);
//				jxl.write.Label labelCFC = new jxl.write.Label(28, 3, "This is a Label Cell", wcfFC);
//				sheet.addCell(labelCFC); 
				
				int h = 0;
				for (String ch : columnHeadings) {
					sheet.addCell(new Label(h, 2, ch, totalx2Format));
					h++;
				}
				
				int i = 1;
				float[] fl = new float[h-5];
				
				for (BaseBean bean : list.subList(kk*sheetSize, length > (kk+1)*sheetSize ?(kk+1)*sheetSize : length)){
					jxl.write.Number number = new jxl.write.Number(0, i+3, i);
					sheet.addCell(number);
					int n = 1;
					for (String pr : ((ExcelBean)bean).properties()) {
						
						if(n == 21 || n == 22 || n == 23 || n == 24 || n == 25 || n == 26 || n== 27){
							sheet.addCell(new Label(n, i+3, pr,wcfFC));
						}else{
							sheet.addCell(new Label(n, i+3, pr));
							
						}
						for(int a=h-ct;a>2;a--){
							if(n==h-a) fl[a-3] +=Float.parseFloat(pr);
						}
						n++;
					}
					i++;
				}
				
				for(int a=h-ct;a>2;a--){
					sheet.addCell(new Label(h-a, i+3, Float.toString(Math.round(fl[a-3] * 100) / 100f), totalx2Format));
				}
				
				sheet.mergeCells(0, i+3, 1,i+3);
				sheet.addCell(new Label(0, i+3, "合计", totalx2Format));
				
				sheet.mergeCells(0, i+4, 2,i+5);
				sheet.addCell(new Label(0, i+4, "总经理：", totalx2Format));
				
				sheet.mergeCells(10, i+4, 13,i+5);
				sheet.addCell(new Label(10, i+4, "部门主管：", totalx2Format));
				
				sheet.mergeCells(18, i+4, 20,i+5);
				sheet.addCell(new Label(18, i+4, "人事主管：", totalx2Format));
				
				sheet.mergeCells(24, i+4, 26,i+5);
				sheet.addCell(new Label(24, i+4, "人事文员：", totalx2Format));
			}
			
			workbook.write();
			workbook.close();
			os.close();   
			list.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ByteArrayInputStream(os.toByteArray());
	}
	public static int getTitle() {
		return title;
	}
	public static void setTitle(int title) {
		LogBookServiceImp.title = title;
	}
	
}
