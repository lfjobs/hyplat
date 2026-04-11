package com.tiantai.nwa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.apache.xerces.parsers.DOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;





/**
 * @author 
 *  
 */
public class XMLHelper {
	private static final Logger logger = LoggerFactory.getLogger(XMLHelper.class);

	//调试标志
	private static boolean m_debug = false;

	//默认编码格式
	private static final String ENCODING = "gb2312";
	

	/**
	 * 将一个XML文档文件解析为一个JAVA的文档对象
	 * 
	 * @param filename
	 *            文档文件名
	 * @return Document文档对象
	 */
	public static Document getDocument(String filename) throws Exception {
		return getSimulateData(filename);
	}

	/**
	 * 将一个XML格式良好的String解析为一个JAVA的文档对象
	 * 
	 * @param xmlString
	 *            XML格式良好的String
	 * @return Document 文档对象
	 */
	public static Document getDocumentFromString(String xmlString)
			throws Exception {
		//创建DOM解析器
		DOMParser parser = new DOMParser();

		//解析文档
		parser.parse(new InputSource(new StringReader(xmlString)));

		Document doc = parser.getDocument();

		return doc;
	}

	/**
	 * 将一个XML文档文件解析为的文档对象
	 * 
	 * @param filename
	 *            文档文件名
	 * @return Document 文档对象
	 */
	public static Document getSimulateData(String filename) throws Exception {
		//打开文件
		InputStream in = XMLHelper.class.getResourceAsStream(filename);

		//创建输入源
		InputSource input = new InputSource(in);

		//创建DOM解析器
		DOMParser parser = new DOMParser();

		//解析文档
		parser.parse(input);

		Document doc = parser.getDocument();

		in.close();

		//返回文档
		return doc;
	}

	/**
	 * 根据元素名取元素（只取一个）
	 * 
	 * @param doc
	 *            包含需要元素的文档
	 * @param name
	 *            元素名
	 * @return Element 元素节点，没有找到返回null
	 */
	public static Element getElementByName(final Document doc, final String name) {
		try {
			NodeList nodes = doc.getElementsByTagName(name);

			return (Element) nodes.item(0);
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 根据元素名取元素（只取第一个）
	 * 
	 * @param parent
	 *            父元素
	 * @param name
	 *            元素名
	 * @return Element 元素节点，没有找到返回null
	 */
	public static Element getElementByName(final Element parent,
			final String name) {
		try {
			NodeList nodes = parent.getElementsByTagName(name);

			return (Element) nodes.item(0);
		} catch (Exception e) {
			if (m_debug) {
				logger.error("操作异常", e);
			}
		}

		return null;
	}

	/**
	 * 根据元素名取元素（取全部）
	 * 
	 * @param parent
	 *            父元素
	 * @param name
	 *            元素名
	 * @return Element 元素节点，没有找到返回null
	 */
	public static NodeList getNodeListByName(final Element parent,
			final String name) {
		try {
			NodeList nodes = parent.getElementsByTagName(name);
			return nodes;
		} catch (Exception e) {
			if (m_debug) {
				logger.error("操作异常", e);
			}
		}
		return null;
	}

	/**
	 * 取子元素的值
	 * 
	 * @param doc
	 * @param parent
	 *            父元素
	 * @param name
	 *            子元素名,没有找到返回null
	 */
	public static String getElementValueByName(final Document doc,
			final Element parent, final String name) {
		try {
			return XMLHelper.getElementByName(parent, name).getFirstChild()
					.getNodeValue();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Return true if the string is a xml formate string.
	 * 
	 * @param xmlString
	 * @return true if the string is a xml formate string, otherwise false.
	 */
	public static boolean validateXMLFormate(String xmlString) {
		if ((xmlString == null) || xmlString.trim().equalsIgnoreCase("")) {
			return false;
		}

		try {
			StringReader reader = new StringReader(xmlString);
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document dom = docBuilder.parse(new InputSource(reader));

			return true;
		} catch (ParserConfigurationException e) {
		} catch (FactoryConfigurationError e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}

		return false;
	}

	/**
	 * 将文档对象转换为字符串
	 * 
	 * @param document
	 *            文档对象
	 * @return docment转换成的字符串
	 */
	public static String toXMLString(Document document) {
		return toXMLString(document, true);
	}

	/**
	 * 将文档对象输出为字符串
	 * 
	 * @param document
	 *            文档对象
	 * @param bFormated
	 *            是否格式化
	 * @return docment转换成的字符串
	 */
	public static String toXMLString(Document document, boolean bFormated) {
		try {
			OutputFormat outputformat = new OutputFormat("xml", ENCODING,
					bFormated);
			StringWriter stringwriter = new StringWriter();
			XMLSerializer xmlserializer = new XMLSerializer(stringwriter,
					outputformat);

			xmlserializer.serialize(document);

			return stringwriter.toString();
		} catch (Exception exception) {
			return null;
		}
	}

	/**
	 * 将文档元素输出为字符串
	 *	 
	 * @return Element 转换xml String
	 */
	public static String toXMLString(Element element) {
		try {
			OutputFormat outputformat = new OutputFormat("xml", ENCODING, true);

			outputformat.setLineWidth(0);

			StringWriter stringwriter = new StringWriter();
			XMLSerializer xmlserializer = new XMLSerializer(stringwriter,
					outputformat);

			xmlserializer.serialize(element);

			return stringwriter.toString();
		} catch (Exception exception) {
			return null;
		}
	}

}