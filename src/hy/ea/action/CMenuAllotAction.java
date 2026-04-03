package hy.ea.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CMenu;
import hy.ea.service.CBOService;
import hy.ea.service.CEAService;
import hy.ea.service.CEMBService;
import hy.ea.service.CLogBookService;
import hy.ea.service.CMenuService;
import hy.plat.bo.SBO;
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
public class CMenuAllotAction {
	
	@Resource
	private CEAService ceaService;
	@Resource
	private CMenuService cmenuService;
	@Resource
	private CBOService cboService;
	@Resource
	private CEMBService cembService;
	@Resource
	private CLogBookService logBookService;
	private List<SEA> seasForMenuAllot;
	private String parameter;
	private String result;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<SEA> getSeasForMenuAllot() {
		return seasForMenuAllot;
	}
	public void setSeasForMenuAllot(List<SEA> seasForMenuAllot) {
		this.seasForMenuAllot = seasForMenuAllot;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	
	/**
	 * 给菜单分配BO时页面加载SEA，用户首先选择SEA
	 * @return
	 */
	public String getListSeaForCMenuAllot(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		seasForMenuAllot = ceaService.getListSea(account.getCompanyID());
		return "success";
	}

	/**
	 * 给菜单分配BO时加载一个EA下所有的Menu，用户可以选择给某个EA下的某个Menu分配
	 * @return
	 */
	public String getListCMenuForAllot(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		//这里的parameter即是eaID
		List<CMenu> cmenuList = cmenuService.getMenuListByEaID(account.getCompanyID(),parameter);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mlist", cmenuList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 给菜单分配这个菜单所属的EA的BO时，要将已经分配给此菜单的BO置标记
	 * @return
	 */
	public String getListSBOForAllot(){
		CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
		if(account == null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nologin", 1);
			JSONObject obj = JSONObject.fromObject(map);
			result = obj.toString();
			return "success";
		}
		//这里的parameter中有eaID和menuID
		String []em = parameter.split("-");
		String eaID = em[0];
		String menuID = em[1];
		List<SBO> sboList = cboService.getSboForCMenuAllot(account.getCompanyID(),eaID,menuID); 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bolist", sboList);
		JSONObject obj = JSONObject.fromObject(map);
		result = obj.toString();
		return "success";
	}
	
	/**
	 * 保存给某EA下的某个Menu分配的BO信息
	 * @return
	 */
	public String saveCEMB(){
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		//这里的parameter中含有eaID,menuID和选中boID
		String []emb = parameter.split("-");
		String eaID = emb[0];
		String menuID = emb[1];
		String []boIDs = (3 == emb.length)?emb[2].split(","):null;
		CLogBook logBook = logBookService.saveCLogBook(null,"菜单分配BO(菜单名称："+ cmenuService.getMenuByID(account.getCompanyID(),menuID).getMenuName()
				+")", account);
		cembService.saveCEMB(account.getCompanyID(),eaID, menuID, boIDs,logBook);
		
		return getListSeaForCMenuAllot();
	}
}
