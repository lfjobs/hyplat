package hy.ea.ccompany.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.service.CCodeService;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class CCScheduledproductAction {

	private String result;
	@Resource
	private CCodeService codeService;

	public String getBillTypes() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		@SuppressWarnings("unused")
		String companyID = (String) ActionContext.getContext().getSession()
				.get("companyID");
		List<CCode> BillTypes = codeService.getCCodeListByPID(account.getCompanyID(), "scode20101101dfs3uhdprp0000000029");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("BillTypes", BillTypes);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
