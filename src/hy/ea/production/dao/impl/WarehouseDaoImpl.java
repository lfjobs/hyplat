package hy.ea.production.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import hy.ea.production.dao.WarehouseDao;
import hy.ea.util.Converter;
import hy.plat.bo.BaseBean;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Transactional
public class WarehouseDaoImpl implements WarehouseDao {
	@Resource
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("deprecation")
	public void numberOfGeneratedItems(String[] str) {
		try {
			Connection ct = sessionFactory.getCurrentSession().connection();
			CallableStatement cs =null;
			if(str.length==8){
				cs = ct.prepareCall("{call establish_goods_number(?,?,?,?,?,?,?,?,'','')}");
			}else{
				cs = ct.prepareCall("{call establish_goods_number(?,?,?,?,?,?,?,?,?,?)}");
			}
		    for(int i=0;i<str.length;i++){
		    	 cs.setString(i+1, str[i]);
		    }
		    cs.execute();
		    cs.close();
		    ct.close();
		  } catch (Exception e) {
		    System.out.println(e.getMessage());
		    e.printStackTrace();
		  }
	}

/**
	 * 1：表ID
	 * 2：公司ID
	 * 3：库存ID
	 * 4：单据ID
	 * 5：物品ID
	 * 6：状态
	 * 7：数量
	 * 8：编号
	 */
	@SuppressWarnings("deprecation")
	public void EstablishGoodsNumber(String[] str) {
		try {
		    Connection ct = sessionFactory.getCurrentSession().connection();
		    CallableStatement cs = ct.prepareCall("{call on_Key_sh(?,?,?,?,?,?,?,?)}");
		    for(int i=0;i<str.length;i++){
		    	 cs.setString(i+1, str[i]);
		    }
		    cs.execute();
		    cs.close();
		    ct.close();
		  } catch (Exception e) {
		    System.out.println(e.getMessage());
		    e.printStackTrace();
		  }
	}


public InputStream OutOrderExcel(String title,String[] str,List<Object> list) {
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
			
			// 创建数字格式化对象实例，并设置为千分位显示
			WritableCellFormat numFormat = new WritableCellFormat(new NumberFormat("#,##0.00"));
			numFormat.setAlignment(Alignment.RIGHT);
			
			//设置excel页面的头部
			String[] ss=title.split(",-");
			sheet.mergeCells(0,0,str.length,0);
			sheet.addCell(new Label(0,0,ss[0],cellFormat));
			sheet.addCell(new Label(0,1,"序号"));
			for(int i=0;i<str.length;i++){
				sheet.addCell(new Label(i+1,1,str[i],cellFormat));
			}
			int iir=0;
			float dou=0;
			//excel 要显示的主题内容
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				sheet.addCell(new Label(0,i+2,i+1+""));
				for(int r=0;r<obj.length;r++){
						sheet.addCell(new Label(r+1,i+2,obj[r]==null?" ":obj[r].toString()));
				}
				if(ss.length>1){
					float sss=Float.parseFloat(obj[Integer.parseInt(ss[1])].toString());
					dou+=sss;
					iir=i;
				}
			}
			if(ss.length>1){
				sheet.addCell(new Label(0,iir+2,""));
				for(int i=0;i<str.length;i++){
					if(i==Integer.parseInt(ss[1]))
						sheet.addCell(new Label(i+1,iir+2,dou+""));
					else if(i==0)
						sheet.addCell(new Label(i+1,iir+2,"合计"));
					else
					sheet.addCell(new Label(i+1,iir+2,""));
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


public InputStream OutFieldisExcel(List<Object> list) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("First Sheet",0);
			sheet.setColumnView(0,15);sheet.setColumnView(5,20);sheet.setColumnView(4,20);
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
			sheet.mergeCells(0,0,8,0);
			sheet.addCell(new Label(0,0,"场地分配管理",cellFormat));
			//第二行数据
			sheet.addCell(new Label(0,1,"项目编号",cellFormat));
			sheet.addCell(new Label(1,1,"产品名称",cellFormat));
			sheet.addCell(new Label(2,1,"开始时间",cellFormat));
			sheet.addCell(new Label(3,1,"结束时间",cellFormat));
			sheet.addCell(new Label(4,1,"场地位置",cellFormat));
			sheet.addCell(new Label(5,1,"分配责任人",cellFormat));
			sheet.addCell(new Label(6,1,"分配时间",cellFormat));
			sheet.addCell(new Label(7,1,"职责",cellFormat));
			sheet.addCell(new Label(8,1,"备注",cellFormat));
			//excel 要显示的主题内容
			
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				for(int r=0;r<obj.length;r++){
					//判断当前列的内容是否是金额
						sheet.addCell(new Label(r,i+2,obj[r].toString()));
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


public synchronized InputStream OutboundOrderExcel(List<Object> list){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(out);
			WritableSheet sheet = workbook.createSheet("First Sheet",0);
			sheet.setColumnView(0,20);sheet.setColumnView(10,20);sheet.setColumnView(2,15);//设置第10列和第11列的宽度为
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
			sheet.mergeCells(0,0,10,0);
			sheet.addCell(new Label(0,0,"生产出库单",cellFormat));
			//第二行数据
			sheet.addCell(new Label(0,1,"出库单编号",cellFormat));
			sheet.addCell(new Label(1,1,"产品编号",cellFormat));
			sheet.addCell(new Label(2,1,"产品名称",cellFormat));
			sheet.addCell(new Label(3,1,"规格",cellFormat));
			sheet.addCell(new Label(4,1,"出库数量",cellFormat));
			sheet.addCell(new Label(5,1,"单价",cellFormat));
			sheet.addCell(new Label(6,1,"总金额",cellFormat));
			sheet.addCell(new Label(7,1,"仓库名称",cellFormat));
			//有重复数据，所以在这里设置了个循环
			sheet.addCell(new Label(8,1,"仓库地址",cellFormat));
			sheet.addCell(new Label(9,1,"负责人",cellFormat));
			sheet.addCell(new Label(10,1,"出库时间",cellFormat));
			//excel 要显示的主题内容
			
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				for(int r=0;r<obj.length;r++){
					//判断当前列的内容是否是金额
						sheet.addCell(new Label(r,i+2,obj[r].toString()));
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

@Override
public InputStream receiptExcelTable(String title, String[] str,
		List<Object> list) {
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	try {
		WritableWorkbook workbook = Workbook.createWorkbook(out);
		WritableSheet sheet = workbook.createSheet("First Sheet",0);
		for(int i=0;i<str.length;i++){
			int ir=str[i].length();
			sheet.setColumnView(i,ir*4);
		}
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
		sheet.mergeCells(0,0,str.length,0);
		sheet.addCell(new Label(0,0,title,cellFormat));
		sheet.addCell(new Label(0,1,"序号"));
		for(int i=0;i<str.length;i++){
			sheet.addCell(new Label(i+1,1,str[i],cellFormat));
		}
		int ir=0;
		float highPrice=0,smallPrice=0;
		//excel 要显示的主题内容
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[])list.get(i);
			sheet.addCell(new Label(0,ir+2,(i+1)+""));
			for(int r=0;r<obj.length;r++){
					sheet.addCell(new Label(r+1,ir+2,obj[r]==null?" ":obj[r].toString()));
			}
			smallPrice+=Float.parseFloat(obj[8].toString());
			ir++;
			if(i!=list.size()-1){
				if(!obj[8].equals(((Object[])list.get(i+1))[8])){
					for(int r=0;r<obj.length;r++){
						if(r==8)
							sheet.addCell(new Label(r+1,ir+2,smallPrice+""));
						else if(r==9)
							sheet.addCell(new Label(r+1,ir+2,obj[r]==null?" ":obj[r].toString()));
						else if(r==0)
							sheet.addCell(new Label(r+1,ir+2,"合计"));
						else
							sheet.addCell(new Label(r+1,ir+2,""));
					}
					highPrice+=smallPrice;smallPrice=0;ir++;
				}
			}else{
				for(int r=0;r<obj.length;r++){
					if(r==8)
						sheet.addCell(new Label(r+1,ir+2,smallPrice+""));
					else if(r==9)
						sheet.addCell(new Label(r+1,ir+2,obj[r]==null?" ":obj[r].toString()));
					else if(r==0)
						sheet.addCell(new Label(r+1,ir+2,"合计"));
					else
						sheet.addCell(new Label(r+1,ir+2,""));
				}
				highPrice+=smallPrice;smallPrice=0;ir++;
			}
		}
		for(int i=0;i<str.length+1;i++){
			if(i==1)
				sheet.addCell(new Label(i,ir+2,"总合计"));
			else if(i==9)
				sheet.addCell(new Label(i,ir+2,highPrice+""));
			else
				sheet.addCell(new Label(i,ir+2,""));
		}
		workbook.write();
		workbook.close();
		out.close();
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	return new ByteArrayInputStream(out.toByteArray());
}

@SuppressWarnings({ "rawtypes", "deprecation" })
@Override
public List<Object> getProductMix(String[] str) {
	List<Object> list=new ArrayList<Object>();
	try {
		Connection ct = sessionFactory.getCurrentSession().connection();
	    CallableStatement cs = ct.prepareCall("{call get_product_mix(?,?,?,?,?)}");
	    for(int i=0;i<str.length;i++){
	    	 cs.setString(i+1, str[i]);
	    }
		cs.registerOutParameter(5, oracle.jdbc.OracleTypes.CURSOR); // 返回的记录集
		
		ResultSet rs = (ResultSet) cs.getObject(5);
		Converter c = new Converter();
		Collection jihe=c.get(rs, Object.class);
		for (Iterator it = jihe.iterator(); it.hasNext();) {
			BaseBean ltest = (BaseBean) it.next();
			list.add(ltest);
           }

	    cs.execute();
	    cs.close();
	    ct.close();
	  } catch (Exception e) {
	    System.out.println(e.getMessage());
	    e.printStackTrace();
	  }
	return list;
}
}
