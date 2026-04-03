package mobile.tiantai.android.util;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParserX {

	public boolean parse_status = false;// xml解析出错

	public String parse_error = "";// xml解析出错原因

	public String recon_status = "";// 识别出错代码或证件类型

	public String recon_error = "";// 识别出错原因

	public int length = 0;// 识别的item个数

	public String fieldname[] = new String[30];

	public String fieldvalue[] = new String[30];

	public XmlParserX(InputStream inputStream) throws Exception {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputStream);
			Element element = document.getDocumentElement();
			parse_status = getItemValue(element);
		} catch (Exception e) {
			parse_error = e.getMessage();
		}

	}

	private boolean getItemValue(Element element) {
		boolean value = false;
		try {
			recon_status = element.getElementsByTagName("status").item(0).getFirstChild().getNodeValue();
			recon_error = element.getElementsByTagName("value").item(0).getFirstChild().getNodeValue();

			NodeList nodelist = element.getElementsByTagName("item");
			if (nodelist != null) {
				length = nodelist.getLength();
				for (int i = 0; i < length; i++) {
					// 对每个item的处理
					fieldname[i] = "";
					fieldvalue[i] = "";
					Node node = nodelist.item(i);

					// 打印属性中的名称
					NamedNodeMap nnm = node.getAttributes();
					if (nnm != null) {
						Node nodea = nnm.getNamedItem("desc");
						if (nodea != null) {
							fieldname[i] = nodea.getNodeValue();
						}
					}

					// 打印名称对应的值
					Node nodevalue = node.getFirstChild();
					if (nodevalue != null) {
						fieldvalue[i] = nodevalue.getNodeValue();
					}
				}
			}
			value = true;
		} catch (Exception e) {
			parse_error = e.getMessage();
		}
		return value;
	}


}