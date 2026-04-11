package mobile.tiantai.android.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import mobile.tiantai.android.service.ElkcService;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("prototype")
public class ElkcAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;
	@Resource
	private ElkcService elkcService;
	private Object result;
	private String user;



	/**
	 * 微分金登录
	 * @param
	 * @user账户
	 * password密码
	 * @return 1:帐号不存在 2:密码为空 0:正常登录
	 */
	public String elkcLogin() {


		Map<String, Object> map = new HashMap<String, Object>();

		try{
			 map = elkcService.elkclogin(user);

		}catch(Exception e){
			map.put("returnCode", "-1");//失败登录
			logger.error("操作异常", e);;
		}
		map.put("returnCode", "0");// 正常登录
		result = JSONObject.fromObject(map);
		return "success";
	}





	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
