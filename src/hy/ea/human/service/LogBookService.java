package hy.ea.human.service;

import hy.plat.bo.BaseBean;

import java.io.InputStream;
import java.util.List;

public interface LogBookService {
	//生成对应的excel表单
	InputStream showExcel(String []columnHeadings,List<BaseBean> list);
	
	
}
