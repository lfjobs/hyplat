package com.tiantai.telrec.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
 
import net.sf.json.JSONObject;
import hy.ea.bo.CCode;
import hy.ea.service.CCodeService;

import com.opensymphony.xwork2.ActionSupport;

public class ClientQueryParam extends ActionSupport { 
	
	private String companyid;
	@Resource
	private CCodeService codeService;
	private static final long serialVersionUID = -2879582001360130636L;

	public String execute() throws IOException {

	Map<String, Object> map = new HashMap<String, Object>();
		if (companyid != null) {

			List<CCode> codeSexList = codeService.getCCodeListByPID(companyid,
					"scode20100331qqrexctzfc0000000035");
			List<CCode> codeRelationList = codeService.getCCodeListByPID(
					companyid, "scode20110106hfjes5ucxp0000000017");
			List<CCode> codeNationalityList = codeService.getCCodeListByPID(
					companyid, "scode201004233ern4m24yx0000000014");
			List<CCode> codeNationList = codeService.getCCodeListByPID(
					companyid, "scode20100331mk6yn5b5f60000000006");
			List<CCode> codeNativePlaceList = codeService.getCCodeListByPID(
					companyid, "scode2010053143wpua87db0000000008");
			List<CCode> typelist = codeService.getCCodeListByPID(companyid,
					"scode20110106hfjes5ucxp0000000003");
			List<CCode>  telType=  codeService.getCCodeListByPID(companyid,
			"scode20120511cyyypnpchw0000000002");
			map.put("codeSexList", codeSexList);
			map.put("codeNationalityList", codeNationalityList);
			map.put("codeNationList", codeNationList);
			map.put("codeNativePlaceList", codeNativePlaceList);
			map.put("codeRelationList", codeRelationList);
			map.put("codeIndustryType", typelist);
			map.put("telType", telType);
		}
		JSONObject oj = JSONObject.fromObject(map);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(oj.toString()); 
		return null;
	} 
	
	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public CCodeService getCodeService() {
		return codeService;
	}

	public void setCodeService(CCodeService codeService) {
		this.codeService = codeService;
	}
}
