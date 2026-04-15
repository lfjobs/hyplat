package com.tiantai.nwa.util;

import hy.ea.util.Constant;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jdom.Element;

import com.tiantai.nwa.tbank.bo.AbcDictBean;
import com.tiantai.nwa.tbank.bo.BankSX;

/**
 * 
 * @author zhb
 * 
 */
public class DockingBankInitUtil {

	private static Map<String, Map<String, String>> sysMap;//银行的交易特有数据
	private static Map<String, String> mapBankList;//银行列表
	private static Map<String, Map<String, String>> bankClientMap;//银行客户端配置
	private static Map<String, Map<String, String>> abcDictMap;//农业银行的数据字典	
	private static Map<String, String> bankSXMap;//银行缩写的数据字典
	
	
	//初始化银行客户端
	public static void InitBankClientMap()
	{
		if (getMapBankList()==null || getMapBankList().isEmpty())
		{
			return;
		}else if (getBankClientMap()==null || getBankClientMap().isEmpty())
		{
			Iterator<String> iter = getMapBankList().keySet().iterator();
			bankClientMap = new HashMap<String, Map<String, String>>();
			while (iter.hasNext()) {
				String string = (String) iter.next();
				String path = Constant.BANK_LIST_PATH + string + "_BankClient.properties";
				bankClientMap.put(string, getOneSystemProperties(getProperties(path)));
			}
		}
	}
	
	//获得银行缩写的list
	public static List<BankSX> getBankSXList()
	{
		InitBankSXMap();
		List<BankSX> bankSXList = new ArrayList<BankSX>();
		Iterator<String> iter = bankSXMap.keySet().iterator();
		while (iter.hasNext())
		{
			String sx = iter.next();
			bankSXList.add(new BankSX(bankSXMap.get(sx), sx));
		}
		return bankSXList;
	}
	
	/**
	 * 
	 * @param key abc_dict.xml中定义的一项字典关键字，如provCode,currency
	 * @return List<AbcDictBean> 银行某一字典list
	 */
	public static List<AbcDictBean> getAbcDictListByKey(String key)
	{
		InitABCDictMap();
		List<AbcDictBean> list = new ArrayList<AbcDictBean>();
		Map<String,String> map = abcDictMap.get(key);
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext())
		{
			String code = iter.next();
			list.add(new AbcDictBean(map.get(code),code));
		}
		return list;
	}
	
	/**
	 * 
	 * @param key abc_dict.xml中定义的一项字典关键字，如provCode,currency
	 * @param code abc_dict.xml中定义的一项字典的某个code，如provCode 中code=22 表示四川省
	 * @return AbcDictBean 
	 */
	public static AbcDictBean getAbcDictBeanByCode(String key,String code)
	{
		List<AbcDictBean> list = getAbcDictListByKey(key);
		Object[] arr = list.toArray();
		AbcDictBean adBean = null;
		for (Object object : arr) {
			adBean = (AbcDictBean)object;
			if (adBean.getCode().equals(code)) break;
		}
		return adBean;
	}
	
	
	
	//初始化银行缩写
	public static void InitBankSXMap()
	{
		if (getBankSXMap()!=null && !getBankSXMap().isEmpty())
		{
			return;
		}
		bankSXMap = new HashMap<String, String>();
		String bankSXFilePath = Constant.BANK_SX_FILE_PATH;
		XMLReader xmlReader = new XMLReader(bankSXFilePath);
		List<Element> list1 = xmlReader.getElementsByRoot();
		for (Element element : list1) {			
			bankSXMap.put(element.getChildText("short"), element.getChildText("name"));			
		}
		
	}
	
	//初始化农业银行
	public static void InitABCDictMap()
	{
		if (getAbcDictMap()!=null && !getAbcDictMap().isEmpty())
		{
			return;
		}
		abcDictMap = new HashMap<String, Map<String, String>>();
		String abcDictFilePath = Constant.ABC_DICT_FILE_PATH;
		XMLReader xmlReader = new XMLReader(abcDictFilePath);
		List<Element> list1 = xmlReader.getElementsByRoot();
		for (Element element : list1) {
			abcDictMap.put(element.getName(),getMapByElement(element));
		}
		
	}
	//获取银行数据字典的某一项
	public static Map<String, String> getMapByElement(Element element)
	{
		if (element == null || element.getChildren().isEmpty()) return null;
		Map<String, String> map = new HashMap<String, String>();
		Iterator<?> iter = element.getChildren().iterator();
		while (iter.hasNext())
		{
			Element one = (Element)iter.next();
			map.put(one.getChildText("code"), one.getChildText("text"));
		}
		return map;
	}
	
	//=====================================================================//
	
	//根据properties文件路径，获取Properties对象。
	public static Properties getProperties(String path) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return prop;
	}
	//初始化一家银行的交易特有数据
	public static Map<String, String> getOneSystemProperties(Properties prop) {
		if (prop == null)
			return null;
		Map<String, String> map = new HashMap<String, String>();		
		Enumeration<Object> enu = prop.keys();
		while (enu.hasMoreElements()) {
			String tmp = (String)enu.nextElement();
			map.put(tmp, prop.getProperty(tmp));
		}
		return map;
	}
	
	//根据给定的银行列表文件对应的Properties对象，初始化银行列表
	public static void InitBankListProperties(Properties prop) {
		if (getMapBankList() != null && !getMapBankList().isEmpty()) {
			return;
		}
		if (prop == null)
			return;
		String banklist = "";
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<Object> enu = prop.keys();
		while (enu.hasMoreElements())// 只有一行
		{
			String tmp = (String)enu.nextElement();
			banklist = prop.getProperty(tmp);
			break;
		}
		String[] arrBankList = banklist.split(",");//逗号分隔
		for (String string : arrBankList) {
			map.put(string, string);
		}
		setMapBankList(map);
	}
	//初始化银行列表
	public static void InitBankListProperties() {
		String bankList = Constant.BANK_LIST_PATH + "BankList.properties";		
		InitBankListProperties(getProperties(bankList));
	}	

	//初始化银行以及每个银行交易的特有数据。（调用入口）
	public static void InitSystemProperties() {
		InitBankListProperties();		
		InitBankClientMap();
		if (getMapBankList() != null && !getMapBankList().isEmpty()) {
			if (getSysMap() != null && !getSysMap().isEmpty()) {
				return;
			}
			Map<String, String> map = getMapBankList();
			Map<String, Map<String, String>> totalMap = new HashMap<String, Map<String, String>>();
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String banksx = (String) iter.next();
				String path = Constant.BANK_LIST_PATH + banksx + ".properties";		
				totalMap.put(banksx,getOneSystemProperties(getProperties(path)));
			}
			setSysMap(totalMap);
		}
	}

	public static Map<String, Map<String, String>> getAbcDictMap() {
		return abcDictMap;
	}

	public static void setAbcDictMap(Map<String, Map<String, String>> abcDictMap) {
		DockingBankInitUtil.abcDictMap = abcDictMap;
	}
	
	public static Map<String, Map<String, String>> getSysMap() {
		return sysMap;
	}

	public static void setSysMap(Map<String, Map<String, String>> sysMap) {
		DockingBankInitUtil.sysMap = sysMap;
	}

	public static Map<String, String> getMapBankList() {
		return mapBankList;
	}

	public static void setMapBankList(Map<String, String> mapBankList) {
		DockingBankInitUtil.mapBankList = mapBankList;
	}

	public static Map<String, String> getBankSXMap() {
		return bankSXMap;
	}

	public static void setBankSXMap(Map<String, String> bankSXMap) {
		DockingBankInitUtil.bankSXMap = bankSXMap;
	}
	
	public static void main(String[] args) {
		InitSystemProperties();
	}

	public static Map<String, Map<String, String>> getBankClientMap() {
		return bankClientMap;
	}

	public static void setBankClientMap(Map<String, Map<String, String>> bankClientMap) {
		DockingBankInitUtil.bankClientMap = bankClientMap;
	}	
}
