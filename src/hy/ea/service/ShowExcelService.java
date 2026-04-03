package hy.ea.service;

import hy.plat.bo.BaseBean;

import java.io.File;
import java.io.InputStream;
import java.util.List;


public interface ShowExcelService {
	//生成对应的excel表单
	InputStream showExcel(String []columnHeadings,List<BaseBean> list);
	
	
	
	//导入
	 List<String[]> ExcelImporter(File path);
	 
	 //生成物品汇总excel表单
	InputStream goodsShowExcel(String[] columnHeadings, List<BaseBean> list);   
	
	InputStream goodsShowExcels(String[] columnHeadings, List<BaseBean> list);   
	
	
	//专门为流水报表整的
	InputStream showExcelByWater(String []columnHeadings,List<BaseBean> list,String title);

	//销售报表
	InputStream showExcelByReport(String []columnHeadings,List<BaseBean> list,Object sum,Object count,String reportType);
}
