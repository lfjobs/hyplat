package com.tiantai.wfj.service.impl;

import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.service.MessageComService;
@Transactional
@Service
public class MessasgeComServiceImpl
        implements MessageComService {
    @Resource
    private BaseBeanDao beandao;
    @Resource
    private ServerService serveService;

    @Resource
    private BaseBeanService baseBeanService;


    @Resource
    private BaseBeanDao baseBeanDao;
    
    
   /**
    * 微分金手机号账号 user 获取用户
    */
	public TEshopCusCom getCusCom(String user){
		
		String hql = "from TEshopCusCom where account = ? and logOff=0 and acquiesce = ?";
		
		TEshopCusCom tc = (TEshopCusCom)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{user,"01"});
		
		  return tc;
	}

    /**
     * 微分金手机号账号 user 获取用户
     */
	public TEshopCustomer getCustomer(String user){
         String hql = "from TEshopCustomer where account = ? and logOff=0";
		
         TEshopCustomer tec = (TEshopCustomer)baseBeanDao.getBeanByHqlAndParams(hql, new Object[]{user});
		 return tec;
	}


   

}