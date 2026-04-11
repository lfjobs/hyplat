package com.tiantai.telrec.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.opensymphony.xwork2.ActionSupport;

public class ClientQueryCustomerTelRecord extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8966171437911298692L;
	private com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService;
	private String telno;
	private String company;

	public com.tiantai.telrec.service.ClientInsertTelInfoService getTelInfoService() {
		return telInfoService;
	}

	public void setTelInfoService(
			com.tiantai.telrec.service.ClientInsertTelInfoService telInfoService) {
		this.telInfoService = telInfoService;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@SuppressWarnings("rawtypes")
	public String execute() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String,Object> map = new HashMap<String,Object>();
		List list = null;
		try {
			list = telInfoService.queryTelrecForTelIn(telno,company);
			map.put("telinRecords", list);
			
			list = telInfoService.queryTelrecForTelOut(telno, company);
			map.put("teloutRecords", list);
			JSONObject json = JSONObject.fromObject(map);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
		} catch (Exception e) {
			logger.error("操作异常", e);
		} 
		return null;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private String createXml(List list) {
		Document document = DocumentHelper.createDocument();
		Element records = document.addElement("List");
		try {
			for (int i = 0; i < list.size(); i++) {
				Element row = records.addElement("row");
				Object[] arr = (Object[]) list.get(i);
				String col = "col";
				for (int j = 0; j < arr.length; j++) {
					Element elen = row.addElement(col + j);
					elen.setText(arr[j].toString());
				}
			}
		} catch (Exception e) {

			logger.error("操作异常", e);
		}
		return doc2String(document);
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
			logger.error("操作异常", ex);
		}
		return s;
	}
}
