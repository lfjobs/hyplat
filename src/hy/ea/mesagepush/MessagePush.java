package hy.ea.mesagepush;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.Remind;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.comet4j.core.CometContext;
import org.comet4j.core.CometEngine;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class MessagePush implements ServletContextListener,HttpSessionListener {
	
	
	private static Map<String, HttpSession>  sessionMap=new HashMap<String, HttpSession>();
	//private BaseBeanService baseBeanService;
	private static WebApplicationContext springContext;
	
	
	 
	 private static final String CHANNEL = "hello";
	 private static final String CONTEXT = "context";  //消息内容
	 private static final String REMINDURL="rurl";
     public void contextInitialized(ServletContextEvent event) {
    	 	 springContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
    	 	 sessionMap=new HashMap<String, HttpSession>();
             CometContext cc = CometContext.getInstance();
             cc.registChannel(CHANNEL);//注册应用的channel
             cc.registChannel(CONTEXT);//注册应用的channel
             cc.registChannel(REMINDURL);//注册应用的channel
             Thread helloAppModule = new Thread(new HelloAppModule(), "Sender App Module");
             /*helloAppModule.setDaemon(true);
             helloAppModule.start();*/

     }

     class HelloAppModule implements Runnable {
		public void run() {
			while (true) {
				try {
						Thread.sleep(1000);
						 
						//logger.info("调试信息");
						BaseBeanService testService = (BaseBeanService) springContext
								.getBean("baseBeanServiceImpl");
						List<CAccount>  accounts=new ArrayList<CAccount>();
						for (HttpSession session : sessionMap.values()) {
							CAccount account1 = (CAccount) session.getAttribute("account");
							accounts.add(account1);
							//logger.info("调试信息");
						}
						for (CAccount acount : accounts) {
							String hql2="from Remind  where receiveDate<=? and (remindStatus=? or remindStatus=?) and remindType=?  and staffID=?";
							List<BaseBean> reList3=testService.getListBeanByHqlAndParams(hql2, new Object[]{new Date(),"01","03","01",acount.getStaffID()});
							for (BaseBean baseBean : reList3) {
								Remind  rm=(Remind)baseBean;
								CometEngine engine = CometContext.getInstance().getEngine();
								engine.sendToAll(CONTEXT,rm.getCircularTitle());
								engine.sendToAll(CHANNEL,rm.getCircularText());
								engine.sendToAll(REMINDURL,rm.getDetailedurl());
								
								//修改状态 
								String hql1="from Remind r where receiveDate<=? and (remindStatus=? or remindStatus=?) and remindType=?";
								List<BaseBean> reList1=testService.getListBeanByHqlAndParams(hql1, new Object[]{new Date(),"01","03","01"});
								List<BaseBean> re1List1=null;
								if(reList1.size()>0){
									re1List1=new ArrayList<BaseBean>();
									for (int i=0;i<reList1.size();i++) {
										Remind remind1 =(Remind)reList1.get(i);
										CAccount cAccount2=(CAccount)testService.getBeanByHqlAndParams("from CAccount c where c.staffID=?", new Object[]{remind1.getStaffID()});
										if(cAccount2.getAccountOnLine().equals("01")){
											remind1.setRemindStatus("02");
											re1List1.add(remind1);
											continue;
										}
										remind1.setRemindStatus("03");
										re1List1.add(remind1);
									}
									testService.executeHqlsByParamsList(re1List1, null, null);

								} 
								if (null != rm.getCircularText()){
									//如果当前登录人有可发送的消息 则在控制台
									logger.info("调试信息");
									
								}
							}
						}
				} catch (Exception ex) {
					logger.info("调试信息");
				}
				
				
				
				}
		}
     }

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		sessionMap.put(se.getSession().getId(), se.getSession());
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		sessionMap.remove(se.getSession().getId());
		
	}

	
}
