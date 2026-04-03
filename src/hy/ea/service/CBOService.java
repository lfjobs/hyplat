package hy.ea.service;

import hy.plat.bo.SBO;

import java.util.List;



public interface CBOService {
	
	/**
	 * 查询此EA下所有状态正常的SBO，当此EA下的某个SBO被分配给此EA的某个Menu时，则置此SBO的boStatus为98
	 * @param eaID
	 * @param menuID
	 * @return
	 */
	List<SBO> getSboForCMenuAllot(String companyID,String eaID,String menuID);
}
