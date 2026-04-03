package com.tiantai.wfj.front;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.MessageComService;
import com.tiantai.wfj.service.Office5L5CService;
import com.tiantai.wfj.util.SessionWrap;


@Controller
@Scope("prototype")
public class MessageComAction {
	

	@Resource
	private MessageComService messageComService;
	
	private String user;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession session = ServletActionContext.getRequest().getSession();
	SessionWrap sw = SessionWrap.getInstance();
	TEshopCustomer customer = (TEshopCustomer) sw.getObject(session,
            SessionWrap.KEY_CUSTOMER);
	 TEshopCusCom tc = (TEshopCusCom) sw.getObject(session,
             SessionWrap.KEY_SHOPCUSCOM);
	/**
	 * 
	 * 消息通讯
	 * @return
	 */
	public String  indexMesCom(){
		

		
		 return "indexmes";
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}



    
}
