package com.mysl.service.impl;

import hy.ea.bo.Remind;

import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysl.service.RemindService;
/**
 * 消息提醒
 * @author lou
 *
 */
@Service
@Transactional
public class RemindServiceImpl implements RemindService{
	@Resource
	private BaseBeanService baseBeanService;
	
	public void addremind(Remind remind){
		Date currentDate = new Date(System.currentTimeMillis()); 
        Calendar calender = Calendar.getInstance();
        calender.setTime(currentDate);
        calender.add(Calendar.MINUTE, +2);
		remind.setReceiveDate(calender.getTime());
		List<BaseBean> beans = new ArrayList<BaseBean>();
		beans.add(remind);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null, null);
	}
}
