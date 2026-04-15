package com.batch.chinapay.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 批量代付配置文件
 * @author gaoyan
 *
 */
public class Config {

	private static Config instance = null;
	
	private Properties properties = null;
	
	public static final String KEY_CHINAPAY_MERID = "chinapay.merid";
	public static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";
	public static final String KEY_CHINAPAY_PUBKEY_FILEPATH = "chinapay.pubkey.filepath";
	public static final String FilePath = "chinapay.BatchContent.filepath";
	private Config() {
		init();
	}
	
	public static Config getInstance() {
		System.out.println("执行Config中的getInstance方法!");
		
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}
	
	/**
	 * 初始化配置文件
	 */
	public void init(){
		
		try{
			InputStream is = Config.class.getResourceAsStream("/com/batch/res/cp_config.properties");
			properties = new Properties();
			properties.load(is);
			
		}catch (Exception e){
			throw new RuntimeException("Failed to get properties!");
		}
	}
	
	/**
	 * 根据key值取得对应的value值
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}
}
