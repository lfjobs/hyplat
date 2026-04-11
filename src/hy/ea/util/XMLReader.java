package hy.ea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XMLReader {
	private static final Logger logger = LoggerFactory.getLogger(XMLReader.class);

	protected Element m_RootElement = null;

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

}
