package com.tiantai.importdata.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.tiantai.importdata.entity.Entity;
import com.tiantai.importdata.entity.File;
import com.tiantai.importdata.entity.Generator;
import com.tiantai.importdata.entity.TableField;
import com.tiantai.importdata.entity.TemplateField;
import com.tiantai.importdata.exception.InvalidAttributeException;
import com.tiantai.importdata.factory.Factory;
import com.tiantai.importdata.factory.FactoryImpl;
import com.tiantai.importdata.pattern.Pattern;
import com.tiantai.importdata.validator.Validator;
import com.tiantai.nwa.util.XMLReader;
/**
 * 读取xml文件的util类
 * @author zhb
 *
 */
public class XmlInitializeUtil {
	
	private static final String IMPORT_DATA_FILE_LIST_PATH =  XmlInitializeUtil.class.getClassLoader().getResource("com/tiantai/importdata/xml/ImportDataFileList.xml").getPath().replaceAll("%20", " ");
	
	public static File getFile(String fileType) throws InvalidAttributeException {
		return getFileMap().get(fileType);
	}
	
	public static Map<String,File> getFileMap() throws InvalidAttributeException {
		Map<String,File> map = new HashMap<String,File>();
		
		XMLReader xmlReader = new XMLReader(IMPORT_DATA_FILE_LIST_PATH);
		List<Element> list = xmlReader.getElementsByRoot();
		for (Element element : list) {
			File file = new File();
			file.setType(element.getChildText("type"));
			file.setName(element.getChildText("name"));
			file.setDesc(element.getChildText("desc"));
			file.setXmlMetaFile(element.getChildText("xmlMetaFile"));
			file.setEntity(getEntity(element.getChildText("xmlMetaFile")));
			
			map.put(file.getType(), file);
		}
		return map;
	}
	
	private static Entity getEntity(String xmlMetaFile) throws InvalidAttributeException {
		Entity entity = new Entity();
		String path = XmlInitializeUtil.class.getClassLoader().getResource(xmlMetaFile).getPath().replaceAll("%20", " ");
		XMLReader xmlReader = new XMLReader(path);
		List<Element> list = xmlReader.getElementsByRoot();
		for (Element elem : list) {
			entity.setType(elem.getAttributeValue("type"));
			entity.setTable(elem.getAttributeValue("table"));
			entity.setClassName(elem.getAttributeValue("className"));			
			entity.setFields(getFields(elem));
			entity.setTableField(getTableFields(elem));
		}
		return entity;		
	}
	
	@SuppressWarnings("unchecked")
	private static List<TemplateField> getFields(Element elem) throws InvalidAttributeException {
		List<TemplateField> list = new ArrayList<TemplateField>();
		List<Object> fieldList = elem.getChildren("field");
		if (fieldList!=null && !fieldList.isEmpty()){
			for (Object object : fieldList) {
				list.add(getField((Element)object));
			}
		}
		return list;
	}
	
	private static TemplateField getField(Element elem) throws InvalidAttributeException {
		TemplateField templateField = new TemplateField();
		templateField.setOrder(Integer.parseInt(elem.getAttributeValue("order")));
		templateField.setName(elem.getAttributeValue("name"));
		templateField.setColumn(elem.getAttributeValue("column"));
		templateField.setProperty(elem.getAttributeValue("property"));
		templateField.setComment(elem.getAttributeValue("comment"));
		templateField.setNeedImport((elem.getAttributeValue("needImport").equalsIgnoreCase("true"))?true:false);
		templateField.setValidators(getValidators(elem));
		return templateField;
	}
	
	@SuppressWarnings("unchecked")
	private static List<TableField> getTableFields(Element elem){
		List<TableField> list = new ArrayList<TableField>();
		List<Object> tableFieldList = elem.getChildren("tableField");
		if (tableFieldList!=null && !tableFieldList.isEmpty()){
			for (Object object : tableFieldList) {
				list.add(getTableField((Element)object));
			}
		}
		return list;
	}
	
	private static TableField getTableField(Element elem){
		TableField tableField = new TableField();
		tableField.setName(elem.getAttributeValue("name"));
		tableField.setColumn(elem.getAttributeValue("column"));
		tableField.setProperty(elem.getAttributeValue("property"));
		tableField.setComment(elem.getAttributeValue("comment"));
		tableField.setGenerator(getGenerator(elem));
		return tableField;
	}
	
	@SuppressWarnings("unchecked")
	private static Generator getGenerator(Element elem){
		Generator generator = null;
		List<Object> list = elem.getChildren("generator");
		if (list!=null && !list.isEmpty()){
			Element generatorElem = (Element)list.get(0);
			generator = new Generator();
			generator.setType(generatorElem.getAttributeValue("type"));
			generator.setPrefix(generatorElem.getAttributeValue("prefix"));
		}
		return generator;
	}
	
	@SuppressWarnings("unchecked")
	private static List<Validator> getValidators(Element elem) throws InvalidAttributeException {
		List<Validator> list = new ArrayList<Validator>();
		List<Object> validatorList = elem.getChildren();
		if (validatorList!=null && !validatorList.isEmpty()){
			for (Object object : validatorList) {
				list.add(getValidator((Element)object));
			}
		}
		return list;
	}
	
	private static Validator getValidator(Element elem) throws InvalidAttributeException {
		Factory factory = new FactoryImpl();
		String name = elem.getAttributeValue("name");
		Validator validator = factory.createValidator(name);
		validator.setName(name);
		validator.setPatterns(getPatterns(elem));
		return validator;
	}
	
	
	@SuppressWarnings("unchecked")
	private static List<Pattern> getPatterns(Element elem) throws InvalidAttributeException {
		List<Pattern> list = new ArrayList<Pattern>();
		List<Object> patternList = elem.getChildren();
		if (patternList!=null && !patternList.isEmpty()){
			for (Object object : patternList) {
				list.add(getPattern((Element)object));
			}
		}
		return list;
	}
	
	
	private static Pattern getPattern(Element elem) throws InvalidAttributeException {
		Factory factory = new FactoryImpl();
		String type = elem.getAttributeValue("type");
		Pattern pattern = factory.createPattern(type);
		//利用反射将属性值付给pattern
		Field[] fields = pattern.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			String fieldType = field.getGenericType().toString();
			Class<?> inst = null;
			Object object = null;
			if (fieldType.equals("String")) {
				inst = String.class;
				object = elem.getAttributeValue(name);
			}
			if (fieldType.equals("int")) {
				inst = int.class;
				object = Integer.parseInt(elem.getAttributeValue(name));
			}
			if (fieldType.equals("boolean")) {
				inst = boolean.class;
				object = Boolean.parseBoolean(elem.getAttributeValue(name));
			}
			try{
			Method sm = pattern.getClass().getDeclaredMethod(
					"set" + FunctionUtil.A2UpperCase(name), inst);
			sm.invoke(pattern, object);
			}catch(Exception ex){
				throw new InvalidAttributeException(ex,ex.getMessage() + 
						": error occur when getPattern(), the pattern's type is " + type + 
						" and attribute value is " + object);				
			}
		}
		
		return pattern;		
	}

}
