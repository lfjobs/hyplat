package hy.plat.service.impl;

import hy.plat.bo.SDistrict;
import hy.plat.dao.SDistrictnDao;
import hy.plat.service.SDistrictService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class SDistrictServiceImpl implements SDistrictService {
	@Resource
	private SDistrictnDao districtDao;

	@Override
	public void deleteDistrictByID(String districtID) {
		List<SDistrict> sdistrictList = districtDao.getDistrictListByPID(districtID);
		if(sdistrictList.size() > 0){
			for(SDistrict sdistrict : sdistrictList){
				deleteDistrictByID(sdistrict.getDistrictID());
				districtDao.deleteDistrictByID(sdistrict.getDistrictID());
			}
		}
		districtDao.deleteDistrictByID(districtID);
	}
	
	@Override
	public List<SDistrict> getDistrictListByPID(String districtPID) {
		return districtDao.getDistrictListByPID(districtPID);
	}


	@Override
	public SDistrict getDistrictByID(String districtID) {
		return districtDao.getDistrictByID(districtID);
	}

	@Override
	public List<SDistrict> getDistrictListByID(String districtID) {
		List<SDistrict> districtList = new ArrayList<SDistrict>();
		getListDistrict(districtList,districtID);
		return districtList;
	}
	
	private void getListDistrict(List<SDistrict> list,String districtID){
		SDistrict district = districtDao.getDistrictByID(districtID);
		list.add(district);
		if(!"0".equals(district.getDistrictPID())){
			getListDistrict(list,district.getDistrictPID());
		}
	}

	@Override
	public SDistrict getSDistrictByPIDAndName(String districtPID,
			String districtName) {
		return districtDao.getSDistrictByPIDAndName(districtPID, districtName);
	}

	@Override
	public List<SDistrict> getDistrictListNOselfByPID(String districtPID,
			String districtID) {
		// TODO Auto-generated method stub
		return districtDao.getDistrictListNOselfByPID(districtPID, districtID);
	}
}
