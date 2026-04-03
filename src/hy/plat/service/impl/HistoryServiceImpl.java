package hy.plat.service.impl;

import hy.plat.dao.impl.HistoryDao;
import hy.plat.service.HistoryService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HistoryServiceImpl implements HistoryService {

	@Resource
	private HistoryDao historyDao;
	private int a=0;
	@Override
	public void filingHistory() {
		historyDao.filingHistory(a);
		a=getbya();
		if(a>1){
			while(a>1){
				historyDao.filingHistory(a);
				a--;
			}
		}
	}
	@Override
	public int getbya(){
		return historyDao.getbya();
	}
}
