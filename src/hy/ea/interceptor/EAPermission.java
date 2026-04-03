package hy.ea.interceptor;

import hy.ea.bo.CAccount;

import java.util.HashMap;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class EAPermission extends ActionSupport implements Interceptor{

	
	private static final long serialVersionUID = 1L;
    
	public void destroy() {
	}

	public void init() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String intercept(ActionInvocation invocation) throws Exception {
		CAccount account = (CAccount)ActionContext.getContext().getSession().get("account");
		if(null != account){
			//处理权限控制
			String interfaceUrl = invocation.getInvocationContext().getName();
			String namespace = invocation.getProxy().getNamespace();
					if(namespace !=null && namespace.trim().length()>0){
						if(!"/".equals(namespace.trim())){
							namespace +="/";
						}
					}
			interfaceUrl = namespace + interfaceUrl;
			HashMap<String, String> miMap = (HashMap)ActionContext.getContext().getSession().get("cir");
			if(null != miMap.get(interfaceUrl) && miMap.get(interfaceUrl).equals(account.getRoleID())){
				
				return invocation.invoke();
			}
			System.out.println( interfaceUrl +":没权限!!!!!!!!!");
			return "no_authority";
//			return invocation.invoke();
		}
		/*System.out.println(":没登录!!!!!!!!!");*/
		return "not_login";
	}
}
