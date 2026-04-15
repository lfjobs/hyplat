package com.tiantai.dataSync;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CarInformation;
import hy.ea.bo.office.CardInfo;
import hy.ea.bo.office.CardReader;
import hy.ea.bo.office.CardRecord;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class DataSyncAction{

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 103910701495642067L;
	@Resource
	private BaseBeanService baseBeanService;

	@Resource
	private UpLoadFileService fileService;
	
	private String orgsimple;
	private String username;
	private String password;
	private String cardRecord;
	private String cardInfo;
    private String car_readertable;
    private String car_Readerdelstr;
    private String car_cardrecordtable;
    private String car_cardinfotable;
    private String car_cardinfodelstr;
    
    private File doc;
	// 公司id
	private String companyID; 
	// 上传文件名 eg:123455.jpg
	private String fileName;
	
	public String dataSync() {
		HttpServletResponse response = ServletActionContext.getResponse();
		String pwd = Utilities.MD5(password);
		String hql = "";
		//账号
		hql = "from CAccount where accountEmail = ? and accountPassword = ? and accountStatus = ?";
		CAccount account = (CAccount) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { username, pwd,"00" });
		Map<String,Object> map = new HashMap<String,Object>();
		if (account != null) {
			//公司
			hql = "from Company where companyID = ?";
			Company company = (Company) baseBeanService.getBeanByHqlAndParams(
					hql, new Object[] { account.getCompanyID() });
			//部门
			hql = "from COrganization where companyID = ? and organizationPID = ?";
		    List<BaseBean> beanlisto = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getCompanyID(),account.getCompanyID()});
            List<COrganization> orglist = new ArrayList<COrganization>(); 
            for (BaseBean  b  : beanlisto) {
            	COrganization  org = (COrganization)b;
            	orglist.add(org);
			}
		    //员工
			hql = "from Staff where staffID = ?";
	        Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getStaffID()});
            if(staff!=null){
	            account.setAccountName(staff.getStaffName());
	            map.put("staff", staff);
            }
            account.setAccountPassword(password);//将原始密码设置到account中
	        //所在公司的车辆
	        hql = "from CarInformation where companyID = ?";
	        List<BaseBean> beanlistc = baseBeanService.getListBeanByHqlAndParams(hql,new Object[]{account.getCompanyID()});
            List<CarInformation> carlist = new ArrayList<CarInformation>(); 
            for (BaseBean  b  : beanlistc) {
            	CarInformation  car = (CarInformation)b;
            	carlist.add(car);
			}
	        map.put("company", company);
	        map.put("corganizationlist",orglist);
	        map.put("Carlist",carlist);
			map.put("account", account);
		}

		try {

			JSONObject json = JSONObject.fromObject(map);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 *  验证账号是否存在	 * 
	 */
	public String userExist() {
		HttpServletResponse response = ServletActionContext.getResponse();
		String pwd = Utilities.MD5(password);
		String hql = "";
		//账号
		hql = "from CAccount where accountEmail = ? and accountPassword = ? and accountStatus = ?";
		CAccount account = (CAccount) baseBeanService.getBeanByHqlAndParams(
				hql, new Object[] { username, pwd,"00" });
		//员工
		hql = "from Staff where staffID = ?";
        Staff staff = (Staff)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{account.getStaffID()});
        if(staff!=null){
            account.setStaffName(staff.getStaffName());
        }
        account.setAccountPassword(password);//将原始密码设置到account中
		Map<String,Object> map = new HashMap<String,Object>();
		
	    map.put("account", account);

		try {
			JSONObject json = JSONObject.fromObject(map);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@SuppressWarnings("rawtypes")
	public String synCardRecord() {
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			List<BaseBean> crlist = new ArrayList<BaseBean>();
			JSONArray array = JSONArray.fromObject(cardRecord);
			for (Iterator iter = array.iterator(); iter.hasNext();) {
				JSONObject jsonObject = (JSONObject) iter.next();
				CardRecord cr = (CardRecord) JSONObject.toBean(jsonObject,
						CardRecord.class);
				crlist.add(cr);
			}
			
			baseBeanService.saveBeansListAndexecuteHqlsByParams(crlist, null,null);

			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			e.printStackTrace();
		}

		try {
			JSONObject json = JSONObject.fromObject(map);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 同步卡号信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String synCardInfo() {
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			List<BaseBean> crlist = new ArrayList<BaseBean>();
			JSONArray array = JSONArray.fromObject(cardInfo);
			for (Iterator iter = array.iterator(); iter.hasNext();) {
				JSONObject jsonObject = (JSONObject) iter.next();
				CardInfo cr = (CardInfo) JSONObject.toBean(jsonObject,
						CardInfo.class);
				crlist.add(cr);
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(crlist, null,
					null);

			map.put("result", "success");

		} catch (Exception e) {
			map.put("result", "fail");
			e.printStackTrace();
		}

		try {
			JSONObject json = JSONObject.fromObject(map);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 提交图片文件
	 * @return
	 */
	public String upLoadCarPhotoFile() {
		try {
			String path = ServletActionContext.getRequest().getSession()
					.getServletContext().getRealPath("\\");

			String urlPath = fileService.savePhoto(path, this.getFileName(),
					doc, this.getCompanyID(), "carManager/".concat(DateUtil
							.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", urlPath);

			HttpServletResponse response = ServletActionContext.getResponse();
			JSONObject json = JSONObject.fromObject(map);

			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 对读卡器 卡信息 卡记录 同步
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String synAllData() {
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
             //对读卡器进行同步
			if(!car_readertable.equals("")){
			List<BaseBean> crlist = new ArrayList<BaseBean>();
			JSONArray array = JSONArray.fromObject(car_readertable);
			for (Iterator iter = array.iterator(); iter.hasNext();) {
				JSONObject jsonObject = (JSONObject) iter.next();
				CardReader cr = (CardReader) JSONObject.toBean(jsonObject,
						CardReader.class);
				if(cr!=null){
					String hql = "from CardReader where code = ?";
					CardReader oldcr = (CardReader)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cr.getCode()});
					if(oldcr!=null){
						baseBeanService.deleteBeanByKey(CardReader.class, oldcr.getCardReaderKey());
					}
					crlist.add(cr);
				}
				
			}
			baseBeanService.saveBeansListAndexecuteHqlsByParams(crlist, null,
					null);
			}
			String[] codes = car_Readerdelstr.split(",");
			String[] hqls = new String[codes.length];
			List<Object[]> paramlist = new ArrayList<Object[]>();
			if(!car_Readerdelstr.equals("")){
			for(int i = 0;i<codes.length;i++){
				hqls[i]= "delete from CardReader where code = ?";
			    Object[] param = new Object[]{codes[i]}; 
			    paramlist.add(param);
			  }
		    baseBeanService.executeHqlsByParamsList(null, hqls, paramlist);
			}
			
			
			//对卡信息进行同步
			if(!car_cardinfotable.equals("")){
			List<BaseBean> crlist2 = new ArrayList<BaseBean>();
			JSONArray array2 = JSONArray.fromObject(car_cardinfotable);
			for (Iterator iter = array2.iterator(); iter.hasNext();) {
				JSONObject jsonObject = (JSONObject) iter.next();
				CardInfo cr = (CardInfo) JSONObject.toBean(jsonObject,
						CardInfo.class);
				if(cr!=null){
					String hql = "from CardInfo where ID = ?";
					CardInfo oldcr = (CardInfo)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cr.getID()});
					if(oldcr!=null){
						baseBeanService.deleteBeanByKey(CardInfo.class,oldcr.getCardInfoKey());
					}
					crlist2.add(cr);
				}				
			}
			
			baseBeanService.saveBeansListAndexecuteHqlsByParams(crlist2, null,null);
			}
			String[] codes2 = car_Readerdelstr.split(",");
			String[] hqls2 = new String[codes.length];
			List<Object[]> paramlist2 = new ArrayList<Object[]>();
			if(!car_cardinfodelstr.equals("")){
			for(int i = 0;i<codes2.length;i++){
				hqls[i]= "delete from CardReader where code = ?";
			    Object[] param = new Object[]{codes2[i]}; 
			    paramlist2.add(param);
			  }
		    baseBeanService.executeHqlsByParamsList(null, hqls2, paramlist2);
			}	
			
			//对卡记录同步			
			if(!car_cardrecordtable.equals("")){
				List<BaseBean> crlist3 = new ArrayList<BaseBean>();
				JSONArray array3 = JSONArray.fromObject(car_cardrecordtable);
				for (Iterator iter = array3.iterator(); iter.hasNext();) {
					JSONObject jsonObject = (JSONObject) iter.next();
					CardRecord cr = (CardRecord) JSONObject.toBean(jsonObject,
							CardRecord.class);
					if(cr!=null){
						String hql = "from CardRecord where ID = ?";
						CardRecord oldcr = (CardRecord)baseBeanService.getBeanByHqlAndParams(hql,new Object[]{cr.getID()});
						if(oldcr!=null){
							baseBeanService.deleteBeanByKey(CardRecord.class,oldcr.getCardRecordKey());
						}
						crlist3.add(cr);
					}
				}
				baseBeanService.saveBeansListAndexecuteHqlsByParams(crlist3, null,null);
				}
			
			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "fail");
			e.printStackTrace();
		}

		try {
			JSONObject json = JSONObject.fromObject(map);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getOrgsimple() {
		return orgsimple;
	}

	public void setOrgsimple(String orgsimple) {
		this.orgsimple = orgsimple;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getCardRecord() {
		return cardRecord;
	}
	public void setCardRecord(String cardRecord) {
		this.cardRecord = cardRecord;
	}
	public String getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(String cardInfo) {
		this.cardInfo = cardInfo;
	}
	public String getCar_readertable() {
		return car_readertable;
	}
	public void setCar_readertable(String car_readertable) {
		this.car_readertable = car_readertable;
	}
	public String getCar_Readerdelstr() {
		return car_Readerdelstr;
	}
	public void setCar_Readerdelstr(String car_Readerdelstr) {
		this.car_Readerdelstr = car_Readerdelstr;
	}
	public String getCar_cardinfotable() {
		return car_cardinfotable;
	}
	public void setCar_cardinfotable(String car_cardinfotable) {
		this.car_cardinfotable = car_cardinfotable;
	}
	public String getCar_cardinfodelstr() {
		return car_cardinfodelstr;
	}
	public void setCar_cardinfodelstr(String car_cardinfodelstr) {
		this.car_cardinfodelstr = car_cardinfodelstr;
	}
	public String getCar_cardrecordtable() {
		return car_cardrecordtable;
	}
	public void setCar_cardrecordtable(String car_cardrecordtable) {
		this.car_cardrecordtable = car_cardrecordtable;
	}
	
	public File getDoc() {
		return doc;
	}

	public void setDoc(File doc) {
		this.doc = doc;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	} 
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}  
}
