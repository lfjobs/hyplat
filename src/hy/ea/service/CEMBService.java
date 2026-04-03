package hy.ea.service;

import hy.ea.bo.CLogBook;

public interface CEMBService {
	
	/**
	 * 保存CEMB信息
	 * @param companyID
	 * @param eaID
	 * @param menuID
	 * @param boIDs
	 * @param logBook
	 */
	void saveCEMB(String companyID,String eaID,String menuID,String[] boIDs,CLogBook logBook);
}
