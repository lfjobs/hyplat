package hy.ea.BuildPlatform.service.impl;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tiantai.wfj.service.impl.WfjJifenServiceImpl;
import com.tiantai.wfj.util.SessionWrap;

import hy.ea.BuildPlatform.service.PeopleManageMenService;
import hy.ea.bo.CAccount;
import hy.ea.bo.BuildPlatform.TeamBuild;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.Staff;
import hy.ea.bo.production.GoodFunction;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.RandomDatas;
import hy.ea.util.ToChineseFirstLetter;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

@Service
public class PeopleManageMenServiceImpl implements PeopleManageMenService {
	@Resource
	private BaseBeanService bbservice;
	@Resource
	private ServerService serverService;
	@Resource
	private BaseBeanDao basebeandao;
	@Resource
	private UploadContentToFileService contentToFileService;
	
	private final static Logger logger=LoggerFactory.getLogger(WfjJifenServiceImpl.class);

	@Override
	public PageForm getPageFormBySql(CAccount ca,PageForm pageForm){
		StringBuffer sql=new StringBuffer();	
		sql.append(" select s.staffname,s.staffid,s.headimage,s.position, t.companyid,p.goodsname");
		sql.append(" from dt_hr_staff s,dtteambuild t, dt_productpackaging p");
		sql.append(" where p.staffid = s.staffid and p.staffid = t.staffid and t.companyid=? and p.type=? and p.companyId=?");
		
		pageForm=bbservice.getPageFormBySQL(
				pageForm!=null?pageForm.getPageNumber():1, 10, sql.toString(), 
						"select count(*) from ("+sql.toString()+")", new Object[]{ca!=null?ca.getCompanyID():"","团队管理",ca!=null?ca.getCompanyID():""});
		return pageForm;
	}
	@Override
	public void savaOrUpdate(Staff staff,CAccount ca,String ppId){
		List<BaseBean> beans=new ArrayList<BaseBean>();
		if(staff.getStaffID()==null||staff.getStaffID().equals("")){	
		staff.setStaffID(serverService.getServerID("staff"));
		String phql = "select count(*) from Staff ";
		int pcount = bbservice.getConutByByHqlAndParams(phql, null);
		staff.setStaffCode("NO" + pcount);
		staff.setVerifyTime(new Date());
		staff.setStaus("01");
		staff.setStaffDesc("团队管理");
		}
		beans.add(staff);
		TeamBuild tb=new TeamBuild();
		tb.setTeamBuildId(serverService.getServerID("teambuild"));
		tb.setCompanyId(ca.getCompanyID());
		tb.setStaffId(staff.getStaffID());
		beans.add(tb);
		ProductPackaging p=(ProductPackaging) bbservice.getBeanByHqlAndParams("from ProductPackaging where ppID=?", new Object[]{ppId});
		GoodsManage g=(GoodsManage) bbservice.getBeanByHqlAndParams("from GoodsManage where goodsID=?", new Object[]{p!=null?p.getGoodsID():""});
		p.setStaffID(staff.getStaffID());
		g.setStaffID(staff.getStaffID());
		beans.add(p);
		beans.add(g);
		bbservice.executeHqlsByParamsList(beans, null, null);		
	}
	@Override
	@Transactional
	public String savePersonalBrief(ProductPackaging pp,String content,String companyId){
		List<BaseBean> beans=new ArrayList<BaseBean>();
		if (pp != null) {
			GoodsManage goodsManage = new GoodsManage();
			if (pp.getPpID() != null && !pp.getPpID().equals("")) {				
				GoodFunction gf = (GoodFunction) bbservice.getBeanByHqlAndParams("from GoodFunction where goodsid = ?", new Object[]{pp.getGoodsID()});				
				delTransformVideo(gf.getUrl());				
               //删除功能介绍
				String hqldelete = "delete from GoodFunction where goodsid = ?";
				//修改产品名称,时间,图片
				String hqlupdate1 = "update ProductPackaging p set  p.goodsName=?,p.packagingDate=?,p.image=?  where p.ppID = ? ";
				//修改物品名称,主图
				String hqlupdate2 = "update GoodsManage g set g.goodsName=?,g.photoPath=? where g.goodsID = ?";
				
				List<Object[]> parmsList=new ArrayList<Object[]>();
				parmsList.add(new Object[]{pp.getGoodsID()} );
				parmsList.add(new Object[]{pp.getGoodsName(),new Date(),pp.getImage(),pp.getPpID()});
				parmsList.add(new Object[]{pp.getGoodsName(),pp.getImage(),pp.getGoodsID()});
				
				basebeandao.executeHqlsByParmsList(null,new String[]{hqldelete,hqlupdate1,hqlupdate2} , parmsList);              
			} else {
				// 添加
				// 物品添加
				goodsManage.setGoodsID(serverService.getServerID("goodsID"));
				goodsManage.setGoodsName(pp.getGoodsName());
				goodsManage.setTypeID("个人简介");
				//goodsManage.setStaffID(staffId);
				goodsManage.setPhotoPath(pp.getImage());
				// 编号处理
				String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.typeID=?";
				// 获取拼音码
				String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage.getTypeID());
				String Upstr = pinyin.toUpperCase();// 转换为大写
				int goodscodingnum = basebeandao.getConutByByHqlAndParams(hql,
						new Object[] { goodsManage.getTypeID() });
				DecimalFormat form = new DecimalFormat("000000");
				String ss = form.format(goodscodingnum + 1);
				goodsManage.setGoodsCoding(Upstr + "_" + ss);
				goodsManage.setFiveClear("5");
				goodsManage.setCreatedate(new Date());
				goodsManage.setCompanyID(companyId);
				beans.add(goodsManage);

				// 产品
				pp.setDelStatus("00");
				pp.setProductstate("01");
				pp.setFiveClear("5");
				//productPackaging.setStaffID(staffId);
				pp.setPackagingDate(new Date());
				pp.setAssemble("00");
				pp.setGoodsID(goodsManage.getGoodsID());
				pp.setPpID(serverService.getServerID("p"));
				pp.setCompanyID(companyId);
				pp.setType("团队管理");
				beans.add(pp);
			}
			
			//保存功能介绍
			 GoodFunction goodFun = new GoodFunction();
				String url = saveContentToFile(content);
				goodFun.setGfid(serverService.getServerID("gfid"));
				goodFun.setOrders(1);
				goodFun.setUrl(url);
				goodFun.setName(pp.getGoodsName());
				goodFun.setType("1");
				goodFun.setGoodsid(pp.getGoodsID());
				beans.add(goodFun);
			bbservice.executeSqlsByParmsList(beans, null, null);	
		}
		return pp!=null?pp.getPpID():"";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getPeopleInfo(String staffId){
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuffer sql=new StringBuffer();	
		sql.append("select s.staffname,s.staffid,p.goodsid,p.ppid,s.position,p.goodsname,s.headimage from dt_hr_staff s,dt_productpackaging p");
		sql.append(" where s.staffId=p.staffid and p.type=? and s.staffid=?");
		List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{"团队管理",staffId});
		//简介
		Object [] obj=(Object[]) list.get(0);
		map.put("obj", obj);
		String hql = "from GoodFunction where goodsid = ?";
		GoodFunction goodFunction = (GoodFunction) bbservice.getBeanByHqlAndParams(
				hql, new Object[] {obj!=null? obj[2]:"" });
		try {
			String path = ServletActionContext.getServletContext()
					.getRealPath("\\");
			if(goodFunction!=null){
				String content = contentToFileService.getContent(path
						+ goodFunction.getUrl());
				map.put("content", content);
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getPersonalBrief(String ppId){
		Map<String,Object> map=new HashMap<String,Object>();
		StringBuffer sql=new StringBuffer();	
		sql.append("select p.goodsid,p.ppid,p.goodsname,p.image from dt_productpackaging p");
		sql.append(" where p.type=? and p.ppid=?");
		
		List<Object> list=bbservice.getListBeanBySqlAndParams(sql.toString(), new Object[]{"团队管理",ppId});
		//简介
		Object [] obj=(Object[]) list.get(0);
		map.put("obj", obj);
		String hql = "from GoodFunction where goodsid = ?";
		GoodFunction goodFunction = (GoodFunction) bbservice.getBeanByHqlAndParams(
				hql, new Object[] {obj!=null? obj[0]:"" });
		try {
			String path = ServletActionContext.getServletContext()
					.getRealPath("\\");
			if(goodFunction!=null){
				String content = contentToFileService.getContent(path
						+ goodFunction.getUrl());
				map.put("content", content);
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return map;
	}
	//粉丝好友查询
	@Override
	public PageForm fansFriends(PageForm pageForm,String search){
		StringBuffer sql=new StringBuffer();		
		sql.append("select hs.staffname, cus.staffid, hs.headimage,hs.reference from dtcaccount ca, dtjoinfans jf, t_eshop_cuscom cus, dt_hr_staff hs");
		sql.append(" where ca.staffid = cus.staffid and cus.sccid = jf.zsccid and jf.staffid=hs.staffid and ca.accountid=? and cus.acquiesce = ?");
		sql.append(" and jf.fsccid is not null");		
		HttpSession session =ServletActionContext.getRequest().getSession();
		SessionWrap sw=SessionWrap.getInstance();
		CAccount ca=(CAccount) sw.getObject(session,SessionWrap.KEY_CACCOUNT);
		List<Object> param=new ArrayList<Object>();
		param.add(ca!=null?ca.getAccountID():"");
		param.add("01");
		if(search!=null&&!search.equals("")){
			sql.append(" and hs.staffname like?");
			param.add("%"+search+"%");
		}
		pageForm=bbservice.getPageFormBySQL(
				pageForm!=null?pageForm.getPageNumber():1, 10, sql.toString(), 
						"select count(*) from ("+sql.toString()+")", param.toArray());
		return pageForm;
	}
	//查询staff
	@Override
	public Staff toGetStaff(String staffid){
		Staff staff=null;
		if(staffid.length()>0){
			staff=(Staff) bbservice.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{staffid});
		}
		return staff;
	}
	//查询公司在职员工
	@Override
	public PageForm getStaffForCompany(PageForm pageForm,String companyId,String search){		
		StringBuffer sql =new StringBuffer(); 
		List<Object> params =new ArrayList<Object>();
		sql.append("select s.staffid,s.staffname,s.reference,s.position,s.headimage");
		sql.append(" from dtcos c,dt_hr_staff s");
		sql.append(" where c.staffid = s.staffid");
		sql.append(" and c.companyid = ? and c.organizationid != ? and c.cosstatus = ? and c.status=?");
		sql.append(" and not exists(select t.staffid from dtteambuild t where t.companyid=? and t.staffid=s.staffid)");
		//hql.append("from Staff s where exists (select staffID from COS c where c.staffID=s.staffID and companyID = ?  and cosStatus = ? and organizationID != ? )");
		params.add(companyId);
		params.add("90");
		params.add("50");
		params.add("01");
		params.add(companyId);
		if(search!=null&&search.length()>0){
			sql.append(" and s.staffName like ?");
			params.add("%"+search+"%");
		}
		pageForm=bbservice.getPageFormBySQL(pageForm!=null?pageForm.getPageNumber():1, 10, sql.toString(),"select count(*) from ( "+sql.toString()+" )",params.toArray());
		return pageForm;
	}
	public void delTransformVideo(String pictureName) {
		String path = ServletActionContext.getServletContext().getRealPath("/");
		String[] url = pictureName.split(",");
		for (int i = 0; i < url.length; i++) {
			//删除视频
			if(url[i]!=null && !url[i].equals("")){
				FileUtil.delete(path + url[i]);
			}
		}
	}
	private String saveContentToFile(String content) {
		String id = "share"+RandomDatas.getUUID();
		String path = ServletActionContext.getServletContext().getRealPath("")
				+ "/upload_files/goodDetail/";		
		try {
			contentToFileService.saveContent(id,
					content, path);

		} catch (IOException e) {
			e.printStackTrace();
	
		}		
		return "/upload_files/goodDetail/"+id+UploadContentToFileService.suffix;
	}
	
}
