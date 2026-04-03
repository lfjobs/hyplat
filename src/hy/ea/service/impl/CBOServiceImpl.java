package hy.ea.service.impl;

import hy.ea.bo.CEMB;
import hy.ea.dao.CBODao;
import hy.ea.dao.CEMBDao;
import hy.ea.service.CBOService;
import hy.plat.bo.SBO;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CBOServiceImpl implements CBOService{
	
	@Resource
	private CBODao cboDao;
	
	@Resource
	private CEMBDao cembDao;


	@Override
	public List<SBO> getSboForCMenuAllot(String companyID,String eaID, String menuID) {
		//取得所有状态正常的SBO
		List<SBO> sboList = cboDao.getBOListByEaID(eaID);
		//取得已分配信息
		List<CEMB> cembList = cembDao.getCEMBListByEaIDAndMenuID(companyID,eaID, menuID);
		HashMap<String,String> hashSemb = new HashMap<String, String>();
		for(CEMB cemb : cembList){
			hashSemb.put(cemb.getBoID(), "01");
		}
		
		for(SBO sbo : sboList){
			if(null != hashSemb.get(sbo.getBoID())){
				sbo.setBoStatus("98");
			}
		}
		
		return sboList;
	}
}
