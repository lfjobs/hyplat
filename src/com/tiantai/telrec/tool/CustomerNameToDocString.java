package com.tiantai.telrec.tool;

import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class CustomerNameToDocString extends ListToXmlForChart {

	@SuppressWarnings("rawtypes")
	@Override
	public String createXml(List list) {
		Document document = DocumentHelper.createDocument();
		Element keys = document.addElement("keys");
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			Element key = keys.addElement("key");
			key.addText((String) map.get("customer_name"));
		}
		return this.doc2String(document);
	}

	public String doc2String(Document document) {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			OutputFormat format = new OutputFormat(" ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return s;
	}
}
