package hy.ea.service.impl;

import hy.ea.bo.CBI;
import hy.ea.bo.CEMB;
import hy.ea.bo.CLogBook;
import hy.ea.bo.CMB;
import hy.ea.bo.CMI;
import hy.ea.bo.CMenu;
import hy.ea.bo.CREMI;
import hy.ea.dao.CBODao;
import hy.ea.dao.CEMBDao;
import hy.ea.dao.CMenuDao;
import hy.ea.dao.CREMIDao;
import hy.ea.service.CREMIService;
import hy.plat.bo.BaseBean;
import hy.plat.bo.SInterface;
import hy.plat.dao.impl.BaseBeanDao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class CREMIServiceImpl implements CREMIService{
	
	@Resource
	private CMenuDao cmenuDao;
	@Resource
	private CEMBDao cembdao;
	@Resource
	private CREMIDao cremiDao;
	@Resource
	private CBODao cboDao;
	@Resource
	private BaseBeanDao baseBeanDao;
	private static Map<String, Object> maps;

	@Override
	public List<CMB> getCMIListByEaIDAndRoleID(String companyID,String eaID, String roleID) {
		
		//要返回的数据
		List<CMB> cmiList = new ArrayList<CMB>();
		
		//取得此ea下所有的菜单
		List<CMenu> menuList = cmenuDao.getMenuListByEaID(companyID,eaID);
		if(menuList.size() <= 0){
			return cmiList;
		}
		
		//通过roleID、eaID取得已经分配给此角色此EA的Interface
		List<CREMI> cremiList = cremiDao.getCRemiListByRoleIDAndEaID(companyID,roleID, eaID);
		HashMap<String,String> cremiMap = new HashMap<String,String>();
		for(CREMI cremi : cremiList){
			cremiMap.put(cremi.getMenuID() + cremi.getInterfaceID(), "01");
		}
		
		Calendar l=Calendar.getInstance();
	    l.add(Calendar.MONTH,-5);
		
		for(CMenu cmenu : menuList){
			

			//设置Menu的基本信息
			CMB cmb = new CMB();
			cmb.setMenuID(cmenu.getMenuID());
			cmb.setEaID(cmenu.getEaID());
			cmb.setMenuName(cmenu.getMenuName());
			List<CBI> cbilists = new ArrayList<CBI>();
			
			
			List<CEMB> cbilist = cembdao.getCEMBListByEaIDAndMenuID(companyID, eaID, cmenu.getMenuID());
			for(CEMB cemb : cbilist){
				List<SInterface> sinterfaces = new ArrayList<SInterface>();
				if(cboDao.getBOByID(cemb.getBoID()) == null)continue;
				//设置CBI
				CBI cbi = new CBI();
				cbi.setBoID(cemb.getBoID());
				cbi.setBoName(cboDao.getBOByID(cemb.getBoID()).getBoName());
				cbi.setEaID(eaID);
				String hqlSInterface ="";
				Object[]  params=null;
				if(maps!=null&&maps.get("title")!=null){
						hqlSInterface = " from SInterface  where interfaceStatus like '0%'  and  boID = ?  and interfaceCreateDate>? order by interfaceID ";
					    params= new Object[]{cemb.getBoID(),l.getTime()};
				}else{
						hqlSInterface = " from SInterface  where interfaceStatus like '0%'  and  boID = ?  order by interfaceID ";
						params= new Object[]{cemb.getBoID()};
						
				}
				//通过eaID取得分配给此EA下的所有菜单的所有的BO的Interface，这里不处理状态
				List<BaseBean> sinterfaceList=baseBeanDao.getListBeanByHqlAndParams(hqlSInterface, params);
				for(BaseBean sinterfaceVar : sinterfaceList){
					SInterface  sinterface=(SInterface)sinterfaceVar;
					if(null != cremiMap.get(cmenu.getMenuID() + sinterface.getInterfaceID())){
						sinterface.setInterfaceStatus("98");
					}
				   sinterfaces.add(sinterface);
				}
				cbi.setSinterfaceList(sinterfaces);
				cbilists.add(cbi);
			}
			
			cmb.setCbiList(cbilists);
			cmiList.add(cmb);
		}
		
		return cmiList;
	}
	
	@Override
	public void saveCREMI(String companyID,String roleID, String eaID, String[] mis,CLogBook logBook) {
		cremiDao.saveCREMI(companyID,roleID, eaID, mis,logBook);
	}
	@Override
	public HashMap<String,String> getCIRMapByRoleID(String companyID,String roleID) {
		List<CREMI> cremiList = cremiDao.getCREMIListByRoleID(companyID,roleID);
		HashMap<String,String> cmiMap = new HashMap<String, String>();
		if(cremiList.size() > 0){
			for(CREMI cremi : cremiList){
				//String []urlmethod = cremi.getInterfaceUrl().split("/");
				cmiMap.put(cremi.getInterfaceUrl(), cremi.getRoleID());
			}
		}
		return cmiMap;
	}
	@Override
	public List<CMI> getCMIListByRoleIDAndEaIDForLogin(String companyID,String roleID,String eaID) {
		//要返回的数据
		List<CMI> cmiList = new ArrayList<CMI>();
		
		//取得此ea下所有的菜单
		List<CMenu> menuList = cmenuDao.getMenuListByRoleIDAndEaID(companyID,roleID, eaID);
		if(menuList.size() <= 0){
			return cmiList;
		}
		
		for(CMenu cmenu : menuList){
			
			//设置Menu的基本信息
			CMI cmi = new CMI();
			cmi.setMenuID(cmenu.getMenuID());
			cmi.setEaID(cmenu.getEaID());
			cmi.setMenuName(cmenu.getMenuName());
			List<SInterface> sinterfaceList = cremiDao.getSInterfaceListByRoleIDAndEaIDAndMenuID(companyID,roleID, eaID, cmenu.getMenuID());
			cmi.setSinterfaceList(sinterfaceList);
			cmiList.add(cmi);
		}
		
		return cmiList;
	}
	@Override
	public void saveCREMI(String companyID, String[] roleIDS, String eaID,
			String[] mis,String[] misno, CLogBook[] logBooks) {
		cremiDao.saveCREMI(companyID, roleIDS, eaID, mis,misno,logBooks);
		
	}

	@Override
	public List<CMB> getCMIListByEaIDAndRoleID(String companyID, String eaID,String roleID, Map<String, Object> maps) {
		CREMIServiceImpl.maps=maps;
		return  getCMIListByEaIDAndRoleID(companyID,eaID,roleID);
	}
}
