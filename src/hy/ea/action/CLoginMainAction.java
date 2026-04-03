package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CMI;
import hy.ea.service.CEAService;
import hy.ea.service.CREMIService;
import hy.plat.bo.SEA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;  
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext; 

@Controller
@Scope("prototype")
public class CLoginMainAction {
	@Resource
	private CEAService ceaService;
	@Resource
	private CREMIService cremiService;

	private List<SEA> seaList;
	private List<CMI> cmiList;
	private String eaID;
	private String eaName;
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<SEA> getSeaList() {
		return seaList;
	}

	public void setSeaList(List<SEA> seaList) {
		this.seaList = seaList;
	}

	public List<CMI> getCmiList() {
		return cmiList;
	}

	public void setCmiList(List<CMI> cmiList) {
		this.cmiList = cmiList;
	}

	public String getEaID() {
		return eaID;
	}

	public void setEaID(String eaID) {
		this.eaID = eaID;
	}

	public String getEaName() {
		return eaName;
	}

	public void setEaName(String eaName) {
		this.eaName = eaName;
	}

	public String generateleft() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "failed";
		}
		seaList = ceaService.getListSea(account.getCompanyID(), account
				.getRoleID());
		return "to_main_left";
	}

	public String generateleftwebkit() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "failed";
		}
		seaList = ceaService.getListSea(account.getCompanyID(), account
				.getRoleID());
		return "to_mobile_top";
	}

	public String generateSet() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			return "failed";
		}
		seaList = ceaService.getListSea(account.getCompanyID(), account
				.getRoleID());
		return "to_main_top";
	}

	public String generateMenu() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		cmiList = cremiService.getCMIListByRoleIDAndEaIDForLogin(account
				.getCompanyID(), account.getRoleID(), eaID);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cmiList", cmiList);
		JSONObject oj = JSONObject.fromObject(map);
		result = oj.toString();
		return "success";
	}
}
