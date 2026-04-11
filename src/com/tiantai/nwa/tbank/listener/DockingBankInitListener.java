package com.tiantai.nwa.tbank.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.tiantai.nwa.util.DockingBankInitUtil;

public class DockingBankInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {		

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {		 
		DockingBankInitUtil.InitSystemProperties();
		DockingBankInitUtil.InitBankSXMap();
		DockingBankInitUtil.InitABCDictMap();
		logger.info("-------------init ok----------------");
	}

}
