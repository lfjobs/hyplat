package com.tiantai.telrec.tool;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class customerListToxmlForChart extends ListToXmlForChart {
	@SuppressWarnings("rawtypes")
	public String createXml(List list) {
		Document document = DocumentHelper.createDocument();
		Element graph = document.addElement("graph");
		graph.addAttribute("xaxisname", "");
		graph.addAttribute("yaxisname", "");
		graph.addAttribute("hovercapbg", "DEDEBE");
		graph.addAttribute("hovercapborder", "889E6D");
		graph.addAttribute("rotateNames", "0");
		graph.addAttribute("yAxisMaxValue", "10000");
		graph.addAttribute("numdivlines", "9");
		graph.addAttribute("divLineColor", "CCCCCC");
		graph.addAttribute("divLineAlpha", "80");
		graph.addAttribute("decimalPrecision", "0");
		graph.addAttribute("showAlternateHGridColor", "1");
		graph.addAttribute("AlternateHGridAlpha", "30");
		graph.addAttribute("AlternateHGridColor", "CCCCCC");
		graph.addAttribute("caption", "");// 主标题
		graph.addAttribute("subcaption", "");// 副标题
		Element categories = graph.addElement("categories");
		categories.addAttribute("font", "");
		categories.addAttribute("fontSize", "12");
		categories.addAttribute("fontColor", "000000");
		Element dataset1 = graph.addElement("dataset");
		dataset1.addAttribute("seriesname", "记录字节总长度");
		dataset1.addAttribute("color", "FDC12E");
		Element dataset2 = graph.addElement("dataset");
		dataset2.addAttribute("seriesname", "记录字节平均长度");
		dataset2.addAttribute("color", "56B9F9");
		Element dataset3 = graph.addElement("dataset");
		dataset3.addAttribute("seriesname", "录音总时间");
		dataset3.addAttribute("color", "C9198D");
		Element dataset4 = graph.addElement("dataset");
		dataset4.addAttribute("seriesname", "录音平均时间");
		dataset4.addAttribute("color", "015C37");
		try {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				Element category = categories.addElement("category");
				category.addAttribute("name", (String) map.get("customername"));
				Element set1 = dataset1.addElement("set");
				set1.addAttribute("value", map.get("sum_content") == null ? "0"
						: map.get("sum_content").toString());
				Element set2 = dataset2.addElement("set");
				set2.addAttribute("value", map.get("avg_content") == null ? "0"
						: String.valueOf(((BigDecimal) map.get("avg_content"))
								.intValue()));
				Element set3 = dataset3.addElement("set");
				set3.addAttribute("value", map.get("sum_time") == null ? "0"
						: String.valueOf(((Double) map.get("sum_time"))
								.intValue()));
				Element set4 = dataset4.addElement("set");
				set4.addAttribute("value", map.get("avg_time") == null ? "0"
						: String.valueOf(((Double) map.get("avg_time"))
								.intValue()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc2String(document);
	}
}
