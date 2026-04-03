package com.tiantai.wfj.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.tiantai.wfj.bo.TEshopCusCom;
import hy.ea.util.MobileMessage;
import mobile.tiantai.android.util.JushMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.service.WorkMessageService;

import hy.ea.bo.Company;
import hy.ea.bo.company.ContactCompany;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import mobile.tiantai.android.bo.AuditRecord;
@Service
public class WorkMessageServiceImpl implements WorkMessageService{
	
	@Resource
	private BaseBeanDao beandao;
	@Autowired
	private MobileMessage msage;
	@Override
	@Transactional
	public boolean agreeMessage(String audi, String AuditComment,String ccompanyID) {
	boolean	flat=false;
		String hql=" from AuditRecord  where  auid=? ";
		AuditRecord ad=(AuditRecord) beandao.getBeanByHqlAndParams(hql, new Object[]{audi});
		
		if(ad !=null){
		ad.setState("02");
		if(AuditComment.toString()==null || AuditComment.toString().equals("")){
			AuditComment="审核通过";
		}
		ad.setAuditComment(AuditComment);
		ad.setCommitDate(new Date());
		beandao.update(ad);
		flat=true;
		
		ContactCompany con=(ContactCompany) beandao.getBeanByHqlAndParams(" from ContactCompany where ccompanyID = ?",new Object[]{ccompanyID});
		con.setAuthState("02");
		beandao.update(con);

		String hqls = "from TEshopCusCom m where m.sccId  in (select f.sccid from Staff f where f.staffID = ?)";
		List<BaseBean>  list = beandao.getListBeanByHqlAndParams(hqls,new Object[]{ad.getStartID()});
		if(list.size()>0){
			TEshopCusCom c = (TEshopCusCom)list.get(0);
			pushMessage(c.getAccount(),"恭喜您！您的商家认证已通过审核！");
		}

		} 
		return flat;
	}

	@Override
	@Transactional
	public boolean rejectedMessage(String audi, String AuditComment,String ccompanyID) {
		boolean	flat=false;
		String hql=" from AuditRecord  where auid=? ";
		AuditRecord ad=(AuditRecord) beandao.getBeanByHqlAndParams(hql, new Object[]{audi});
		
		if(ad !=null){
		if(AuditComment.toString()==null || AuditComment.toString().equals("")){
			AuditComment="审核不通过";
		}
		ad.setState("03");
		ad.setAuditComment(AuditComment);
		ad.setCommitDate(new Date());
		beandao.update(ad);
		flat=true;
		ContactCompany con=(ContactCompany) beandao.getBeanByHqlAndParams(" from ContactCompany where ccompanyID = ?",new Object[]{ccompanyID});
		con.setAuthState("03");
		beandao.update(con);

			String hqls = "from TEshopCusCom m where m.sccId  in (select f.sccid from Staff f where f.staffID = ?)";
			List<BaseBean>  list = beandao.getListBeanByHqlAndParams(hqls,new Object[]{ad.getStartID()});
			if(list.size()>0){
				TEshopCusCom c = (TEshopCusCom)list.get(0);
				pushMessage(c.getAccount(),"很抱歉，您的商家认证审核失败！失败原因为："+ad.getAuditComment()+"请登陆APP移动办公进行修改资料！");
			}
		} 
		return flat;
	}


	/**
	 * 发短息推送
	 */
	private void pushMessage(String tel,String content) {
		try {

			msage.setMobiles(tel);
			msage.setMessage(content);
			msage.sendMsg("【微分金平台】");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//保存账号
		List<String> slist = new ArrayList<String>();//极光推送设备号
		slist = new ArrayList<String>();
		slist.add(tel);
		//极光推送
		JushMain.sendjiguangMessage(content, "会员", "homepage", "member", slist);
	}

	@Override
	@Transactional
	public List<BaseBean> findAudiPeople(String batchNum) {
		
		String str=" select d.auditname,d.auditcomname,d.auditorgname,d.position,to_char(d.commitdate,'YYYY-MM-DD HH24:MI:SS'),d.state,d.auditcomment from DT_AUDITRECORD d where d.batchnum = ? and d.state !='00'  order by d.sorts ";
		
		List<BaseBean> list1=beandao.getListBeanBySqlAndParams(str, new Object[]{batchNum});
		
		
		return list1;
	}

	@Override
	@Transactional
	public AuditRecord findByAuid(String auid) {
		String hql=" from AuditRecord  where auid=?";
		AuditRecord audi=(AuditRecord) beandao.getBeanByHqlAndParams(hql, new Object[]{auid});
		
		return audi;
	}

	@Override
	@Transactional
	public Map<String,List<BaseBean>> findCompany(String companyid) {
		
		Company com=(Company) beandao.getBeanByHqlAndParams(" from Company c where c.companyID=? ", new Object[]{companyid});
		String hqlgroup = "from Company where groupCompanySn = ? and companyID != ?";
		List<BaseBean> totallist = new ArrayList<BaseBean>();
		totallist.add(com);
		List<BaseBean> list = beandao.getListBeanByHqlAndParams(hqlgroup,new Object[]{com.getGroupCompanySn(),com.getCompanyID()});
		totallist.addAll(list);
		
		
		String hql=" from COrganization co  where  co.organizationPID = ? and co.companyID = ? and co.Status = '00'";
		
		List<BaseBean> orglist=beandao.getListBeanByHqlAndParams( hql, new Object[]{com.getCompanyID(),com.getCompanyID()});
		Map<String,List<BaseBean>> map = new HashMap<String,List<BaseBean>>();
		map.put("companylist", totallist);
		map.put("orglist", orglist);
		return map;
	}
	 
	
	/**
	 * 
	 * 查询部门
	 */
	public List<BaseBean> findOrg(String companyId){
		
		
		String hql=" from COrganization co  where  co.organizationPID = ? and co.companyID = ? and co.Status = '00' order by co.organizationNumber";
		List<BaseBean> orglist=beandao.getListBeanByHqlAndParams( hql, new Object[]{companyId,companyId});
		return orglist;
	}

	/** 
	 * 
	 * 查询人Yuan 
	 * @param companyId
	 * @param orgID
	 * @return
	 */
    public List<BaseBean> findStaffList(String companyId,String orgID){
		String sql = "select s.staffID,s.staffName,t.postName,s.headimage from dtCos c,dt_hr_staff s,dt_hr_deptpost t where c.companyID = ? and c.organizationID = ? and c.cosStatus = ? and c.status = ? and t.depPostID = c.depPostID and c.staffid = s.staffid";

		List<BaseBean> stafflist=beandao.getListBeanBySqlAndParams(sql, new Object[]{companyId,orgID,"50","01"});
		return stafflist;
	}

}	
