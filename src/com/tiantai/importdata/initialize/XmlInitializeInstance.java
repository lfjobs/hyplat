package com.tiantai.importdata.initialize;

import java.util.Map;

import com.tiantai.importdata.entity.File;
import com.tiantai.importdata.exception.InvalidAttributeException;
import com.tiantai.importdata.util.XmlInitializeUtil;
/**
 * 初始化xml文件的单一类
 * @author zhb
 *
 */
public class XmlInitializeInstance {
	
	private static XmlInitializeInstance instance = null;
	private static Map<String,File> mapFile = null;

	private XmlInitializeInstance() throws InvalidAttributeException {
		if (mapFile==null || mapFile.isEmpty())
		{
			mapFile = XmlInitializeUtil.getFileMap();
		}
	}
	
	public static synchronized XmlInitializeInstance getInstance() throws InvalidAttributeException
	{
		if (instance==null){
			instance = new XmlInitializeInstance();			
		}
		return instance;
	}
	
	public Map<String,File> getMapFile(){
		return mapFile;
	}	

}
