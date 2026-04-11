package com.tiantai.nwa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XMLReader {
	private static final Logger logger = LoggerFactory.getLogger(XMLReader.class);

	protected Element m_RootElement = null;
	
	public XMLReader() {		
	}

	public XMLReader(String xmlFile) {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = null;
			document = builder.build(new FileInputStream(xmlFile));
			m_RootElement = document.getRootElement();
		} catch (Exception e) {
			logger.error("操作异常", e);
		}
	}

	public String getElementvalue(String currentRootName, String key) {
		String value = null;
		Element currentRoot = getElement(null, currentRootName);
		if (null == currentRoot) {
			currentRoot = m_RootElement;
		}
		Element keyNode = getElement(currentRoot, key);
		if (null != keyNode) {
			value = keyNode.getTextTrim();
		}
		return value;
	}

	@SuppressWarnings("rawtypes")
	private Element getElement(Element currentRoot, String nodeName) {
		Element retElement = null;
        
		if (null == nodeName)
			return m_RootElement;

		if (null == currentRoot) {
			currentRoot = m_RootElement;
		}

		if (null != currentRoot) {
			retElement = currentRoot.getChild(nodeName);
			if (null == retElement) {
				List nestElements = currentRoot.getChildren();
				Iterator iterator = nestElements.iterator();
				while (iterator.hasNext() && null == retElement) {
					retElement = getElement((Element) iterator.next(), nodeName);
				}
			}
		}

		return retElement;
	}
	
	

	public String getElementAtrribute(String elementName, String attributeName) {
		Element element = getElement(null, elementName);
		if (null == element)
			return null;

		return element.getAttributeValue(attributeName);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Element> getElementsByRoot(){
		return m_RootElement.getChildren();		
	}
	
	/**   
     * 字符串转换为DOCUMENT   
     *    
     * @param xmlStr 字符串   
     * @return doc JDOM的Document   
     * @throws Exception   
     */    
    public static Document string2Doc(String xmlStr) throws Exception {  
    	//byte[]  result = xmlStr.getBytes("utf-8");
        java.io.Reader in = new StringReader(xmlStr);     
        Document doc = (new SAXBuilder()).build(in);            
        return doc;     
    }
    
    /**   
     * Document转换为字符串   
     *    
     * @param xmlFilePath XML文件路径   
     * @return xmlStr 字符串   
     * @throws Exception   
     */    
    public static String doc2String(Document doc) throws Exception {     
        Format format = Format.getPrettyFormat();     
        format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题     
        XMLOutputter xmlout = new XMLOutputter(format);     
        ByteArrayOutputStream bo = new ByteArrayOutputStream();     
        xmlout.output(doc, bo);     
        return bo.toString();     
    } 
	
	public String toString()
	{
		Format format = Format.getPrettyFormat();     
		format.setEncoding("gb2312");// 设置xml文件的字符为UTF-8
		XMLOutputter xmlout = new XMLOutputter(format);     
		ByteArrayOutputStream bo = new ByteArrayOutputStream(); 
		try {
			xmlout.output(m_RootElement, bo);
			return bo.toString();//.replaceAll("<AuthNo />", "<AuthNo></AuthNo>").replaceAll("<Sign />", "<Sign></Sign>").replaceAll("\r\n", "").replaceAll(" ", ""); 
		} catch (IOException e) {			
			logger.error("操作异常", e);
		}
		return null;
	}

}
