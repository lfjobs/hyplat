package hy.ea.human.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.attence.AttenceGroup;
import hy.ea.human.service.AttenceManageService;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Scope("prototype")
public class AttenceManageServiceImp implements AttenceManageService{
	@Resource
	private BaseBeanService baseBeanService;
     

	/**
	 * 
	 * 考勤组列表
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter
	 */
	public PageForm getAttenceGroupList(int pageNumber,int pageSize,String parameter,String companyID){
		
		String hql = "from AttenceGroup where companyID = ?";
		
		hql+=" order by createDate desc";
		
		PageForm pageForm = baseBeanService.getPageForm(pageNumber, pageSize, hql,new Object[]{companyID});
		
		
		return pageForm;
	}
	
	@Transactional	
	public void addAttenceGroup(AttenceGroup attenceGroup,String staffID,String companyID){
		
		
		
	}
	
	/**
	 * 
	 * 考勤记录汇总
	 * @param pageNumber
	 * @param pageSize
	 * @param parameter
	 */
	public PageForm getAttenceRecordPage(int pageNumber,int pageSize,String signType,String start,String end,String parameter,String companyID){
		
		List<Object> list = getList(signType,start,end, parameter,companyID,"");
		String sql = (String) list.get(0);
		List<Object> params = (List<Object>)list.get(1);
		
		PageForm pageForm = baseBeanService.getPageFormBySQL(pageNumber, pageSize, sql, "select count(1) from ("+sql+")",params.toArray());
		
		return pageForm;
	}
	
	/**
	 * 
	 * 考勤记录列表
	 * @param parameter
	 */
	public List<BaseBean> getAttenceRecordList(String signType,String start,String end,String parameter,String companyID){
		
		List<Object> list = getList(signType,start,end, parameter,companyID,"excel");
		String sql = (String) list.get(0);
		List<Object> params = (List<Object>)list.get(1);
		List<BaseBean> blist = baseBeanService.getListBeanBySqlAndParams(sql, params.toArray());
		
		
		return blist;
	}
	
	/**
	 * 查询条件
	 * @param signType
	 * @param start
	 * @param end
	 * @param parameter
	 * @param companyID
	 * @return
	 * 
	 *  
	 */
	private List<Object> getList(String signType,String start,String end,String parameter,String companyID,String type) {
		List<Object> result = new ArrayList<Object>();
		String address = "";
		if(companyID!=null&&!companyID.equals("")){
		   ContactCompany cc  = (ContactCompany)baseBeanService.getBeanByHqlAndParams("from ContactCompany y where y.ccompanyID = (select c.ccompanyId from CcomCom c where c.comanyId = ?)", new Object[]{companyID});
		   address = cc.getCompanyAddr();
		}
		String sql="";
		if("excel".equals(type)){
		     sql+="select f.staffCode,f.staffName,w.account,f.staffIdentityCard,w.signDate,case when w.signType = '01' then '刷脸机器签到' when  w.signType = '00' then '手机APP签到' else '手机APP签到' end,case when w.signType = '01' then ? when  w.signType = '00' then w.signSite else w.signSite end,w.signInformation";  
		}else{
			 sql+="select w.signId,f.staffCode,f.staffName,w.account,f.staffIdentityCard,w.signDate,w.signType,case when w.signType = '01' then ? when  w.signType = '00' then w.signSite else w.signSite end,w.signInformation";
		}
		sql+=" from DT_WFJ_SIGN w,dt_hr_staff f ,T_ESHOP_CUSCOM s where  f.staffid = s.staffid and w.sccid = s.sccid and w.companyid = ?";
		List<Object> params = new ArrayList<Object>();
		params.add(address);
		params.add(companyID);
		if(parameter!=null&&!parameter.equals("")){
			sql+=" and (f.staffCode = ? or f.staffName = ? or w.account = ? or f.staffIdentityCard = ?)";
			params.add(parameter);
			params.add(parameter);
			params.add(parameter);
			params.add(parameter);
		}
		if(start!=null&&!start.equals("")&&end!=null&&!end.equals("")){
			sql+=" and w.signDate between ? and ?";
			params.add(Utilities.getDateFromString(start+" 00:00:00", "yyyy-MM-dd hh:mm:ss"));
			params.add(Utilities.getDateFromString(end+" 23:59:59", "yyyy-MM-dd hh:mm:ss"));
		
		}
		if(signType!=null&&!signType.equals("")){
			if("00".equals(signType)){
				sql+=" and (w.signType = ? or w.signType is null)";
			}else{
			    sql+=" and w.signType = ?";
			}
			params.add(signType);
		
		}
		sql+=" order by w.signDate desc";
		result.add(sql);
		result.add(params);
		return result;
		
	}
	
}
