package hy.plat.service.impl;

import hy.ea.bo.CAccount;
import hy.ea.bo.CDetail;
import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.ea.util.RandomDatas;
import hy.ea.util.Utilities;
import hy.plat.service.ServerService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ServerServiceImpl implements ServerService{
	
	private static List<String> list = new ArrayList<String>();
	//private static String random = RandomDatas.getRandomString(10);
	private static List<String> numlist = new ArrayList<String>();
	private static Map<String,  Integer> maplist = new HashMap<String, Integer>();
	private static Map<String,  Integer> cgpasslist = new HashMap<String, Integer>();//每个公司密码找回次数
	private static String datestring = Utilities.getDateString(new Date(),"yyyyMMdd");
	private static List<String> seriallist = new ArrayList<String>();
	private static Map<String,  Integer> serialmap = new HashMap<String, Integer>();
	static{
		for(Integer i=1;i<100001;i++){
			DecimalFormat df = new DecimalFormat("0000000000");
			list.add(df.format(i));
		}
		for(Integer i=1;i<10001;i++){
			DecimalFormat df = new DecimalFormat("00000");
			numlist.add(df.format(i));
		}
		for(Integer i=1;i<10001;i++){
			DecimalFormat df = new DecimalFormat("0000");
			seriallist.add(df.format(i));
		}
	}
	
	@Override
	public synchronized String getServerID(String type) {
		String random = RandomDatas.getRandomString(10);
		if(list.size() == 0){
			for(Integer i=1;i<100001;i++){
				DecimalFormat df = new DecimalFormat("0000000000");
				list.add(df.format(i));
			}
		}
		String sid = type + Utilities.getDateString(new Date(), "yyyyMMdd") + random + list.get(0);
		list.remove(0);
		return sid;
	}
	@Override
	public synchronized String getBillID(String companyID) {
        if(!datestring.equals(Utilities.getDateString(new Date(),"yyyyMMdd"))){
        	maplist.clear();
        	datestring =  Utilities.getDateString(new Date(),"yyyyMMdd");
		}
		if(maplist.get(companyID)==null){
		    maplist.put(companyID, 0);
		}
		String sid = Utilities.getDateString(new Date(), "yyyyMMddhhmmss")  + numlist.get(maplist.get(companyID));
		maplist.put(companyID, maplist.get(companyID)+1);
		return sid;
	}
	
	@Override
	public synchronized String getSerialNumber(String typeNumber) {
        if(!datestring.equals(Utilities.getDateString(new Date(),"yyyyMMdd"))){
        	serialmap.clear();
        	datestring =  Utilities.getDateString(new Date(),"yyyyMMdd");
		}
		if(serialmap.get(typeNumber)==null){
			serialmap.put(typeNumber, 0);
		}
		String serialNumber = Utilities.getDateString(new Date(), "yyyyMMdd")  +typeNumber+ seriallist.get(serialmap.get(typeNumber));
		serialmap.put(typeNumber, serialmap.get(typeNumber)+1);
		return serialNumber;
	}
	
	/*
	 * 密码找回 每天3次限制
	 * @see hy.plat.service.ServerService#getBillID(java.lang.String)
	 */
	
	@Override
	public synchronized Boolean getCBooleanByCompanyIdentifier(String companyIdentifier) {
        if(!datestring.equals(Utilities.getDateString(new Date(),"yyyyMMdd"))){
        	cgpasslist.clear();
        	datestring =  Utilities.getDateString(new Date(),"yyyyMMdd");
		}
        if(cgpasslist.get(companyIdentifier)==null){
        	cgpasslist.put(companyIdentifier, 0);
		}
       
        if(cgpasslist.get(companyIdentifier)==3){
        	return false;
        }
        cgpasslist.put(companyIdentifier, cgpasslist.get(companyIdentifier)+1);
		return true;
	}
	public static Map<String, Integer> getCgpasslist() {
		return cgpasslist;
	}
	@Override
	public boolean register(Company company, ContactCompany contactCompany,
			CDetail companyDetail, CAccount account) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
