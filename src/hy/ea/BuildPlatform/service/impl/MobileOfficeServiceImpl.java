package hy.ea.BuildPlatform.service.impl;


import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import hy.ea.bo.DrivingSchool.TbJpStudentInfo;
import hy.ea.bo.Enroll;
import hy.ea.bo.company.ContactRelation;
import hy.ea.bo.human.Staff;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.util.SessionWrap;

import hy.ea.BuildPlatform.service.MobileOfficeService;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.BuildPlatform.ApplicationDisplay;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;
@Service
public class MobileOfficeServiceImpl implements MobileOfficeService{
	@Resource
	private BaseBeanService bbservice;
	@Resource
	private ServerService serverService;
	/**
	 * 
	 * @param or 组织机构名称
	 * @param user 用户名
	 * @param password	密码
	 */
	@Override
	public Integer login(String or,String user,String password){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
//		TEshopCusCom cus=(TEshopCusCom) sw.getObject(session, SessionWrap.KEY_SHOPCUSCOM);
		String hql="from Company where companyIdentifier=?";
		Company company=(Company) bbservice.getBeanByHqlAndParams(hql, new Object[]{or.toLowerCase()});
		if(null==company){
			return 1;
		}
		if(null != company){
			if(or.equals("00")){ //判断登录公司是否存在
				if(!or.equals(company.getCompanyType())){
					return 1;
				}
			}
			if(or.equals("01")){ //判断客户登录公司是否存在
				if(company.getCompanyType().contains("1")==false){
						return 1;
				}
			}
		}
		//判断注册公司状态是否正常
		if(!"00".equals(company.getCompanyStatus())){
			return 2;
		}
		//System.out.print("companyID"+company.getCompanyID());
		hql="from CAccount where accountEmail=? and companyID=?";
		System.out.print("账号"+user.toLowerCase()+"公司"+company.getCompanyID());
		CAccount caccount=(CAccount) bbservice.getBeanByHqlAndParams(hql, new Object[]{user.toLowerCase(),company.getCompanyID()});
		if(null==caccount){
			return 3;
		}else{					
			sw.setObject(session, SessionWrap.KEY_CACCOUNT, caccount);
		}
		//账号状态不正常
		if(!"00".equals(caccount.getAccountStatus())){
			return 4;
		}				
		//检查登录名和密码是否正确
		if(!Utilities.MD5(password).equals(caccount.getAccountPassword())){
		    return 5;
		}
//		if(cus==null){
//			return 6;
//		}
//		if(!caccount.getCompanyID().equals(cus.getCompanyId())){
//			return 7;
//		}
		//System.out.print("staffID"+caccount.getStaffID());
		//System.out.print("companyID"+company.getCompanyID());
		String sql="select relation from  dtContactRelation where staffID=? and companyID=?  ";
		List<BaseBean> contactRelation= bbservice.getListBeanBySqlAndParams(sql, new Object[]{caccount.getStaffID(),company.getCompanyID()});
		if(null!=contactRelation) {
			Iterator it1 = contactRelation.iterator();
			while(it1.hasNext()){
				if ("学员".equals(it1.next())) {

					return 8;
				}
			}
		}
		String sql1="select d.postName from dtcos c left join dt_hr_deptpost d on c.depPostID = d.depPostID where c.staffID=? and d.companyID=?";
		List<BaseBean> staff=bbservice.getListBeanBySqlAndParams(sql1, new Object[]{caccount.getStaffID(),company.getCompanyID()});
		if(null!=staff) {
			Iterator ite = staff.iterator();
			while(ite.hasNext()){
				if ("教练".equals(ite.next())) {

					return 9;
				}
			}
		}
		return 0;
	}
	/**
	 *	个人信息
	 *	@param staffId  
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> personalInfo(String staffId){
		StringBuffer sql=new StringBuffer();
		sql.append("select hs.photo,hs.staffName,hs.staffID,c.companyID from dt_hr_Staff hs,dtcaccount c where hs.staffID=c.staffID");
		sql.append(" and c.staffID=?");	
		List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{staffId});
		return list;
	}
	/**
	 *	学员信息
	 *	@param staffId
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> StudentInfo(String staffId){
		StringBuffer sql=new StringBuffer();
		sql.append("select c.account,s.staffIdentityCard,s.staffName,c.staffID , s.staffCode,s.headimage from T_ESHOP_CUSTOMER c ,dt_hr_Staff s where c.staffID=s.staffID ");
		sql.append(" and c.staffID=?");
		List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{staffId});
		return list;
	}
	public Map<String,Object> StudentCard(String staffId){
		Map<String,Object> map = new HashMap<String,Object>();
		String sql="from TbJpStudentInfo where staffId=?";
		String sql1="from Staff where staffId=?";
		TbJpStudentInfo studentInfo=(TbJpStudentInfo)bbservice.getBeanByHqlAndParams(sql.toString(), new Object[]{staffId});
		Staff staff=(Staff)bbservice.getBeanByHqlAndParams(sql1.toString(), new Object[]{staffId});
		map.put("student",studentInfo);
		map.put("staff",staff);
		List<Object[]> list = new ArrayList<Object[]>();
		if(studentInfo==null){
			list =  bbservice.getListBeanBySqlAndParams("select e.idcard,e.companyID  from dtenroll e,dtcashierbills cb where e.staffid = ?  and cb.cashierbillsid  = e.cashierbillsid and cb.fkstatus = ? ",new Object[]{staffId,"00"});
		}
		map.put("list",list);
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map <String,Object> fastApplication(String companyId){
		HttpSession session = ServletActionContext.getRequest().getSession();
		SessionWrap sw = SessionWrap.getInstance();
		CAccount ca = (CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		
		Map<String,Object> map = new HashMap<String,Object>();
        List<Object> list = new ArrayList<Object>();
        List<String> ppId_list = new ArrayList<String>();
		StringBuffer sql = new StringBuffer();

		sql.append("from ProductPackaging p where p.goodsName=? and p.companyID=?");

		ProductPackaging p = (ProductPackaging) bbservice.getBeanByHqlAndParams(sql.toString(), new Object[]{"小系统",companyId});
		map.put("top", p != null ? p.getPpID() : "");

		sql.delete(0, sql.length());
//		sql.append("select * from(select p.goodsname,p.ppid,p.goodsid, p.parentid, p.sorting,p.image,p.standard");
//		sql.append(" from dt_productpackaging p,dt_applicationdisplay aps where p.ppid=aps.ppid and aps.staffid=?");
//		sql.append(" connect by nocycle prior p.ppid = p.parentid");
//		sql.append(" start with p.parentid=?");
//		sql.append(" union ");
//		sql.append(" select p.goodsname,p.ppid,p.goodsid,p.parentid,p.sorting,p.image,p.standard from dt_productpackaging p where parentid=?)a");
//		sql.append(" order by a.sorting");


        //小系统下所有下级
		sql.append(" select p.ppid ");
		sql.append(" from dt_productpackaging p");
		sql.append(" connect by nocycle prior p.ppid = p.parentid");
		sql.append(" start with p.parentid = ? ");

		List<Object> ori_list = bbservice.getListBeanBySqlAndParams(
		        sql.toString(),new Object[] {p != null ? p.getPpID() : ""}
        );

		//查询登录者快捷应用记录
        sql.delete(0,sql.length());
        sql.append("select aps.ppid from dt_applicationdisplay aps where aps.staffId = ?");
        List<Object> aps_list = bbservice.getListBeanBySqlAndParams(
                sql.toString(),new Object[] {ca != null ? ca.getStaffID() : ""}
        );

        if (aps_list == null || aps_list.size() == 0 )
        {
            sql.delete(0,sql.length());
            sql.append("select p.goodsname,p.ppid,p.goodsid,p.parentid,p.sorting,p.image,p.standard");
            sql.append(" from dt_productpackaging p where parentid = ? order by p.sorting");
            list = bbservice.getListBeanBySqlAndParams(
                    sql.toString(),new Object[] {p != null ? p.getPpID() : ""}
            );
        }
        else
        {
            sql.delete(0,sql.length());
            sql.append("select p.goodsname,p.ppid,p.goodsid,p.parentid,p.sorting,p.image,p.standard");
            sql.append(" from dt_productpackaging p where ppid in (");

            List<Object> plist = bbservice.getListBeanBySqlAndParams(
                    "select p.ppid from dt_productpackaging p where p.parentid = ?",
                    new Object[] {p != null ? p.getPpID() : ""}
            );

            for (int i = 0; i < ori_list.size(); i++)
            {
                String ori = ori_list.get(i).toString();
                for (int j = 0 ; j < aps_list.size(); j++)
                {
                    String aps = aps_list.get(j).toString();
                    if (ori.equals(aps))
                    {
                        sql.append("?,");
                        ppId_list.add(ori);
                    }
                }
            }

            for (int i = 0 ;i < plist.size(); i++)
            {
                sql.append("?,");
                ppId_list.add(plist.get(i).toString());
            };

            String temp = sql.toString().substring(0,sql.toString().length() - 1) + ") order by p.sorting";

            list = bbservice.getListBeanBySqlAndParams(
                    temp,ppId_list.toArray()
            );
        }

		map.put("child", list);		
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> toAddApplication(String ppId){
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);

		Map<String,Object> map=new HashMap<String,Object>();
		map.put("top", ppId);
		StringBuffer sql=new StringBuffer();
		sql.delete(0, sql.length());
		sql.append("select p.goodsname,p.ppid,p.goodsid,p.parentid,p.sorting,p.image,ad.appdisid from dt_productpackaging p");  
		sql.append(" left join dt_ApplicationDisplay ad on p.ppid=ad.ppid and ad.staffid=?");
		sql.append(" connect by nocycle prior p.ppid = p.parentid");
	    sql.append(" start with p.parentid =?");
		List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{ca!=null?ca.getStaffID():"",ppId});
		map.put("child", list);	
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public void delApplication(String ppId){
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		
		StringBuffer sql=new StringBuffer();
		sql.delete(0, sql.length());
		sql.append("select p.ppid from dt_productpackaging p");  
		sql.append(" connect by nocycle prior p.ppid = p.parentid");
	    sql.append(" start with p.parentid =?"); //所有下级不包含本身
	    
	    List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{ppId});	    
	    sql.delete(0, sql.length());
	    List<Object[]> param=new ArrayList<Object[]>();
	    Object[] o=null; 
	    String ssql="";
	    if(list.size()==0){
	    	o=new Object[2];
	    	o[0]=ca!=null?ca.getStaffID():"";
	    	sql.append("delete dt_ApplicationDisplay where staffId=? and ppId =?");
	    	ssql=sql.toString();
	    	o[1]=ppId;
	    }else{
	    	o=new Object[list.size()+2];
	    	o[0]=ca!=null?ca.getStaffID():"";
	    	sql.append("delete dt_ApplicationDisplay where staffId=? and ppId in(");
	    	for(int i=0;i<list.size();i++){
		    		sql.append("?,");
		    		o[i+1]=list.get(i);
		    }
	    	sql.append("?,");
	    	o[o.length-1]=ppId;
	    	ssql=sql.toString();
		    ssql=ssql.substring(0,ssql.toString().lastIndexOf(","))+")";
	    }
	    param.add(o);
	    
	    bbservice.executeSqlsByParmsList(null, new String[]{ssql}, param);
	}
	@SuppressWarnings("unchecked")
	public void addApplication(String ppId){
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		
		StringBuffer sql=new StringBuffer();
		sql.delete(0, sql.length());
		sql.append("select p.ppid from dt_productpackaging p");  
		sql.append(" connect by nocycle prior p.ppid = p.parentid");
	    sql.append(" start with p.parentid =?"); //所有下级不包含本身
	    
	    StringBuffer top=new StringBuffer();
	    top.append("select p.ppid from dt_productpackaging p");
	    top.append(" connect by nocycle prior p.parentid = p.ppid");
	    top.append(" start with p.ppid=?");//所有上级包含本身
	    
	    List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{ppId});
	    List<Object> toplist=bbservice.getListBeanBySqlAndParams(top.toString(), new Object[]{ppId});
	    List<BaseBean> beans=new ArrayList<BaseBean>();
	    
	    sql.delete(0, sql.length());
	    List<Object> param=new ArrayList<Object>();	
	    param.add(ca!=null?ca.getStaffID():"");
	    sql.append("select ppid from dt_ApplicationDisplay where staffId=? and ppId in(");	   	
	    for(int i=0;i<list.size();i++){
	    		sql.append("?,");
	    		param.add(list.get(i));
	    }	  
	    for (int j=0;j<toplist.size();j++){
	    	sql.append("?,");
	    	param.add(toplist.get(j));
	    }
	    String ssql=sql.toString();
	    ssql=ssql.substring(0,ssql.toString().lastIndexOf(","))+")";
	    List<Object> adlist=bbservice.getListBeanBySqlAndParams(ssql, param.toArray());
	    ApplicationDisplay ad=null;
	    int count=0;
	    if(list.size()>0){
	        for(int i=0;i<list.size();i++){
		    	if(adlist.size()==0){
		    		ad=new ApplicationDisplay();
	    	    	ad.setAppDisId(serverService.getServerID("appDis"));
	    	    	ad.setPpId(list.get(i).toString());
	    	    	ad.setStaffId(ca.getStaffID());
	    	    	beans.add(ad);
		    	}else{
		    		for(int j=0;j<adlist.size();j++){
			    		Object o=adlist.get(j);
			    		if(o.equals(list.get(i))){
			    			count++;
			    		}else{
			    			continue;		    			
			    		}
			    	}
			    	if(count==0){
			    		ad=new ApplicationDisplay();
		    	    	ad.setAppDisId(serverService.getServerID("appDis"));
		    	    	ad.setPpId(list.get(i).toString());
		    	    	ad.setStaffId(ca.getStaffID());
		    	    	beans.add(ad);
			    	}
		    	}
		    }
	    }
	    if(toplist.size()>0){
	    count=0;
	    	for(int j=0;j<toplist.size();j++){
	    		if(adlist.size()==0){
	    		ad=new ApplicationDisplay();
    	    	ad.setAppDisId(serverService.getServerID("appDis"));
    	    	ad.setPpId(toplist.get(j).toString());
    	    	ad.setStaffId(ca.getStaffID());
    	    	beans.add(ad);
	    		}else{
	    			for(int i=0;i<adlist.size();i++){
		    		Object o=adlist.get(i);
		    			if(o.equals(toplist.get(j))){
		    				count++;
		    			}else{
		    				continue;
		    			}
	    			}
	    			if(count==0){
		    		ad=new ApplicationDisplay();
			    	ad.setAppDisId(serverService.getServerID("appDis"));
			    	ad.setPpId(toplist.get(j).toString());
			    	ad.setStaffId(ca.getStaffID());
			    	beans.add(ad);
	    			}
	    		}	     
	    	}
	    }
	    bbservice.executeSqlsByParmsList(beans, null, null);	       
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> toMobileLogin(String companyId){
		StringBuffer sql=new StringBuffer();
		sql.append("select c.companyid,cc.ccompanyid,cc.companyname,cc.logoPath,c.companyIdentifier from dtcompany c, dt_ccom_com ccom, dtcontactcompany cc");
		sql.append(" where c.companyid = ccom.compnay_id and ccom.ccompany_id = cc.ccompanyid and c.companyid = ?");		
		List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{companyId});
		return list;
	}
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ContactCompany authentication(String companyID) {
		
		String hql = "select c from CcomCom m,ContactCompany c where m.comanyId = ? and m.ccompanyId = c.ccompanyID";
		
		ContactCompany con = (ContactCompany) bbservice.getBeanByHqlAndParams(hql, new Object[]{companyID});
		
		return con;
		
	}
	
}
