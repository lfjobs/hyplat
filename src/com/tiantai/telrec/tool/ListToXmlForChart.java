package com.tiantai.telrec.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public abstract class ListToXmlForChart {
	@SuppressWarnings("rawtypes")
	public abstract String createXml(List list);

	public String doc2String(Document document) {
		String s = "";
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			OutputFormat format = new OutputFormat(" ", true, "GBK");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			s = out.toString("GBK");
		} catch (Exception ex) {
			logger.error("操作异常", ex);
		}
		return s;
	}
}
