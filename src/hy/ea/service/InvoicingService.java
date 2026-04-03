package hy.ea.service;

import hy.plat.bo.BaseBean;

import java.util.List;


public interface InvoicingService{

	//将数字转为汉字大写
	public String  cnUpperCaser(String original);
	//递归获取子项目
	public List<BaseBean> getSubProject(List<BaseBean> sublist,String pJournalNum);
}
