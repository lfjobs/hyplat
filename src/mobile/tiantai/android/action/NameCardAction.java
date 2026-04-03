package mobile.tiantai.android.action;

import hy.base.action.BaseAction;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.human.StaffPapers;
import hy.ea.bo.human.UpLoadFile;
import hy.ea.bo.office.Certificate;
import hy.ea.util.FileUtil;
import hy.plat.bo.BaseBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

/**
 * 会员中心相关接口
 * @author zg
 *
 */
@Controller
@Scope("prototype")
public class NameCardAction extends BaseAction<ContactCompany>{

	private static final long serialVersionUID = -1181645099447754222L;
	private ContactCompany ett=this.getModel();

	/**
	 * 公司名片修改接口
	 * ccompanyID公司id(必传)
	 * cresponsible负责人<br/>
	 * responsibleTel负责人电话<br/>
	 * companyName公司名称<br/>
	 * companyAddr公司地址<br/>
	 * dealIn主营项目<br/>
	 * companyTel公司电话<br/>
	 * companyWeb公司网址<br/>
	 * address地址id最后县的id
	 * industryType公司行业
	 * @return 0:success 1:false
	 */
	public String mergeCompanyCardBy(){
		try { 
			String ccompanyID=request.getParameter("ccompanyID");
			String cresponsible=request.getParameter("cresponsible");
			String responsibleTel=request.getParameter("responsibleTel");
			String companyName=request.getParameter("companyName");
			String companyAddr=request.getParameter("companyAddr");
			String dealIn=request.getParameter("dealIn");
			String companyTel=request.getParameter("companyTel");
			String companyWeb=request.getParameter("companyWeb");
			String address=request.getParameter("address");
			String industryType=request.getParameter("industryType");
			
			ett.setCcompanyID(ccompanyID);
			ett.setCresponsible(cresponsible);
			ett.setResponsibleTel(responsibleTel);
			ett.setCompanyName(companyName);
			ett.setCompanyAddr(companyAddr);
			ett.setDealIn(dealIn);
			ett.setCompanyTel(companyTel);
			ett.setCompanyWeb(companyWeb);
			ett.setAddress(address);
			ett.setIndustryType(industryType);
			if (ccompanyID != null&&ccompanyID.length()>1) {
				List<Object> parms = new ArrayList<Object>();
				StringBuffer uhql = new StringBuffer(
						"update ContactCompany u set");
				if (ett.getCresponsible() != null
						&& ett.getCresponsible().length() > 1) {
					uhql.append(" u.cresponsible=?,");
					parms.add(ett.getCresponsible());
				}
				if (ett.getResponsibleTel() != null
						&& ett.getResponsibleTel().length() > 1) {
					uhql.append(" u.responsibleTel=?,");
					parms.add(ett.getResponsibleTel());
				}
				if (ett.getIndustryType() != null
						&& ett.getIndustryType().length() > 1) {
					uhql.append(" u.industryType=?,");
					parms.add(ett.getIndustryType());
				}
				if (ett.getCompanyName() != null
						&& ett.getCompanyName().length() > 1) {
					uhql.append(" u.companyName=?,");
					parms.add(ett.getCompanyName());
				}
				if (ett.getCompanyAddr() != null
						&& ett.getCompanyAddr().length() > 1) {
					uhql.append(" u.companyAddr=?,");
					parms.add(ett.getCompanyAddr());
				}
				if (ett.getDealIn() != null && ett.getDealIn().length() > 1) {
					uhql.append(" u.dealIn=?,");
					parms.add(ett.getDealIn());
				}
				if (ett.getCompanyTel() != null
						&& ett.getCompanyTel().length() > 1) {
					uhql.append(" u.companyTel=?,");
					parms.add(ett.getCompanyTel());
				}
				if (ett.getCompanyWeb() != null
						&& ett.getCompanyWeb().length() > 1) {
					uhql.append(" u.companyWeb=?,");
					parms.add(ett.getCompanyWeb());
				}
				if (ett.getAddress() != null && ett.getAddress().length() > 1) {
					uhql.append(" u.address=?,");
					parms.add(ett.getAddress());
				}
				if (uhql.indexOf(",") > 0) {
					uhql.append("u.ccompanyID=? where u.ccompanyID=?");
					parms.add(ett.getCcompanyID());
					parms.add(ett.getCcompanyID());
					baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
							new String[] { uhql.toString() }, parms.toArray());
					result=new JSONObject().accumulate("ret", "0");
				}
			}
				
		} catch (Exception e) {
			result=new JSONObject().accumulate("ret", "1");
		}
		return Action.SUCCESS;
	}

	/**
	 * 查询公司名片接口
	 * account登录的公司类型的手机号
	 * 
	 * @return 返回公司基本信息 
	 * cresponsible 负责人姓名
	 * responsibleTel 负责人电话
	 * industryType 行业分类
	 * companyName 公司名称
	 * companyAddr 公司详细地址
	 * dealIn 主营项目
	 * companyTel 公司电话
	 * companyWeb 公司网址
	 * ccompanyID 公司Id
	 * sheng 省份
	 * shengId 省份Id
	 * shi 市
	 * shiId 市Id
	 * xian 县
	 * xianId 县Id
	 */
    public String findCompanyCardBy(){
    	String account=request.getParameter("account");
    	StringBuffer hql=new StringBuffer();
    	hql.append("select new ContactCompany(");
    	hql.append("m.cresponsible,m.responsibleTel,m.industryType,m.companyName,");
    	hql.append("m.companyAddr,m.dealIn,m.companyTel,m.companyWeb,m.address,m.ccompanyID)");
    	hql.append(" from ContactCompany m,TEshopCusCom usr ,CcomCom rcc");
    	hql.append(" where m.ccompanyID=rcc.ccompanyId and rcc.comanyId=usr.companyId");
    	hql.append(" and usr.account=?");
    	if(account!=null&&account.length()>1){
    		ContactCompany cc=(ContactCompany) baseBeanService.getBeanByHqlAndParams(hql.toString(), new Object[]{account});
    		if(cc!=null){
    			JSONObject retjo=new JSONObject();
    			JSONObject jo=new JSONObject();
    			Object shengId="";
				Object sheng="";
    			Object shiId="";
    			Object shi="";
    			String xianId="";
    			Object xian="";
    			if(cc.getAddress()!=null&&cc.getAddress().length()>0){
        			String temp=cc.getAddress();
        			String th=null;
        			List<String[]> lst=new ArrayList<String[]>();
        			th="select t.districtid,t.districtname from dtsdistrict t connect by nocycle prior t.districtpid=t.districtid start with t.districtid=?";
    				lst=baseBeanService.getListBeanBySqlAndParams(th, new Object[]{temp});
        			if(lst.size()>=4){
        				Object[] shengarr=lst.get(lst.size()-2);
        				Object[] shiarr=lst.get(lst.size()-3);
        				Object[] xianarr=lst.get(lst.size()-4);
        				shengId=shengarr[0];
        				sheng=shengarr[1];
        				shiId=shiarr[0];
        				shi=shiarr[1];
        				xianId=xianarr[0].toString();
        				xian=xianarr[1];
        			}
    			}
    			
    			jo.accumulate("cresponsible", isNull(cc.getCresponsible()));
    			jo.accumulate("responsibleTel", isNull(cc.getResponsibleTel()));
    			jo.accumulate("industryType", isNull(cc.getIndustryType()));
    			jo.accumulate("companyName", isNull(cc.getCompanyName()));
    			jo.accumulate("companyAddr", isNull(cc.getCompanyAddr()));
    			jo.accumulate("dealIn", isNull(cc.getDealIn()));
    			jo.accumulate("companyTel", isNull(cc.getCompanyTel()));
    			jo.accumulate("companyWeb", isNull(cc.getCompanyWeb()));
    			jo.accumulate("sheng", sheng);
    			jo.accumulate("shengId", shengId);
    			jo.accumulate("shi", shi);
    			jo.accumulate("shiId", shiId);
    			jo.accumulate("xian", xian);
    			jo.accumulate("xianId", xianId);
    			jo.accumulate("ccompanyID", isNull(cc.getCcompanyID()));
    			retjo.accumulate("ccompany", jo);
    			result=retjo;
    		}
    	}
    	
    	
    	return Action.SUCCESS;
    }
    /**
	 * 行业导航
	 * 一级行业
	 */
	public String getIndustry(){
		String codePID=request.getParameter("codePID");
		String hql="select new map(a.codeID as codeID,a.codeValue as codeValue) from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber";
		if(codePID==null||codePID.equals("")){
			codePID="scode20150815wygb79q82p0000000005";
		}
		Object [] params={"company201009046vxdyzy4wg0000000025",codePID};
		List<BaseBean> industryList=baseBeanService.getListBeanByHqlAndParams(hql, params);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("industryList", industryList);
		JSONObject obj=JSONObject.fromObject(map);
		result=obj;
		return "success";
	}
	//二级行业分页
	public String getAjaxIndustry(){
		String codePID=request.getParameter("codePID");
		String hql = "select new map(a.codeID as codeID,a.codeValue as codeValue) from CCode a where a.companyID = ? and a.codePID = ? order by a.codeNumber";
		Object [] params={"company201009046vxdyzy4wg0000000025",codePID};
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql, params);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("list", list);
		JSONObject obj=JSONObject.fromObject(map);
		result=obj;
		return "success";
	}
	/**
	 * 公司证件信息修改(六个)
	 * account 登录的帐号
	 * operator操作类型[yyzz==营业执照][zzjg==组织机构代码证][gsds==国税地税登记证][yhkh==银行开户许可证][tjdj==统计登记证][cwdj=财务登记证]
	 * picpath图片路径,分隔符#
	 * mainId证件信息的主键
	 * @return ret 0:成功1:失败
	 */
	public String mergeCompanyCredential(){
		try {
			String account=request.getParameter("account");
			String operator=request.getParameter("operator");//必传
			String picstr=request.getParameter("picpath");//图片路径,分隔符#
			String mainId=request.getParameter("mainId");//主键
			
			String certificateType=null;//证件类型
			String[] picarr=null;
			if(picstr!=null){
				picarr=picstr.split("#");
			}
			String companyId=null;
			String ccompanyId=null;
			
			String hql="select new map(m.companyId as companyId,cc.ccompanyId as ccompanyId) from TEshopCusCom m,CcomCom cc where m.companyId=cc.comanyId and m.account=? and m.state=?";
			List<BaseBean> lst=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{account,"2"});
			Map<String,Object> map= new HashMap<String,Object>();
			if(lst!=null){
				map=(Map) lst.get(0);
				companyId=map.get("companyId").toString();
				ccompanyId=map.get("ccompanyId").toString();
			}
			if(operator!=null&&companyId!=null&&ccompanyId!=null){
				if(operator.equals("yyzz")){//营业执照
					certificateType="营业执照";
				}else if(operator.equals("zzjg")){//组织机构代码证
					certificateType="组织机构代码证";
				}else if(operator.equals("gsds")){//国税地税登记证
					certificateType="国税地税登记证";
				}else if(operator.equals("yhkh")){//银行卡户许可证
					certificateType="银行开户许可证";
				}else if(operator.equals("tjdj")){//统计登记证
					certificateType="统计登记证";
				}else if(operator.equals("cwdj")){//财政登记证
					certificateType="财务登记证";
				}else{
					result=new JSONObject().accumulate("ret", 1);
					return Action.SUCCESS;
				}
			}else{
				result=new JSONObject().accumulate("ret", 1);
				return Action.SUCCESS;
			}
			List<BaseBean> bean=new ArrayList<BaseBean>();
			String[] delhql=null;
			List<Object[]> delparm=null;
			if(mainId==null||mainId.equals("")){//添加
				mainId=serverService.getServerID("Certificate");
				Certificate entity=new Certificate();
				entity.setCompanyID(companyId);
				entity.setCcompanyID(ccompanyId);
				entity.setCertificateID(mainId);
				entity.setCertificateType(certificateType);
				
				bean.add(entity);
			}else{
				delhql=new String[]{"delete UpLoadFile where parmeterID=?"};
				delparm=new ArrayList<Object[]>();
				delparm.add(new Object[]{mainId});
				
				List<BaseBean> tempmap=baseBeanService.getListBeanByHqlAndParams("select new map(m.filepath as filepath) from UpLoadFile m where m.parmeterID=?", new Object[]{mainId});
				if(tempmap!=null&&tempmap.size()>0){
					String filpath = ServletActionContext.getServletContext().getRealPath("/");
					for (int i=0;i<tempmap.size();i++) {
						map=(Map) tempmap.get(i);
						if(map.get("filepath")!=null){
							try {
								FileUtil.delete(filpath+map.get("filepath"));
							} catch (Exception e) {
							}
						}
					}
					
				}
				
				
			}
			
			//处理图片
			if(picarr!=null&&picarr.length>0){
				UpLoadFile uf=null;
				for (int i=0;i<picarr.length;i++) {
					uf=new UpLoadFile();
					uf.setFileID(serverService.getServerID("UpLoadFile"));
					uf.setCompanyID(companyId);
					uf.setParmeterID(mainId);
					uf.setFilename(certificateType);
					uf.setFiledesc(i+"");
					uf.setFilepath(picarr[i]);
					bean.add(uf);
				}
			}
			
			baseBeanService.executeHqlsByParamsList(bean, delhql, delparm);
			result=new JSONObject().accumulate("ret", 0);
		} catch (Exception e) {
			result=new JSONObject().accumulate("ret", 1);
		}
		return Action.SUCCESS;
	}
	/**
	 * 公司证件信息查询(6个)
	 * account登录的手机帐号
	 * operator操作类型[yyzz==营业执照][zzjg==组织机构代码证][gsds==国税地税登记证][yhkh==银行开户许可证][tjdj==统计登记证][cwdj=财务登记证]
	 * @return
	 */
	public String findCompanyCredential(){
		String account=request.getParameter("account");
		String operator=request.getParameter("operator");
		
		try {
			String certificateType=null;
			if(operator!=null){
				if(operator.equals("yyzz")){//营业执照
					certificateType="营业执照";
				}else if(operator.equals("zzjg")){//组织机构代码证
					certificateType="组织机构代码证";
				}else if(operator.equals("gsds")){//国税地税登记证
					certificateType="国税地税登记证";
				}else if(operator.equals("yhkh")){//银行卡户许可证
					certificateType="银行开户许可证";
				}else if(operator.equals("tjdj")){//统计登记证
					certificateType="统计登记证";
				}else if(operator.equals("cwdj")){//财政登记证
					certificateType="财务登记证";
				}else{
					result=new JSONObject().accumulate("ret", 1);
					return Action.SUCCESS;
				}
			}else{
				result=new JSONObject().accumulate("ret", 1);
				return Action.SUCCESS;
			}
			
			Map<String,Object> map=new HashMap<String,Object>();
			StringBuffer hql=new StringBuffer();
			hql.append("select c.certificateID from TEshopCusCom usr,Certificate c ");
			hql.append("where usr.companyId=c.companyID and usr.account=?  and c.certificateType=? ");
			
			Object mainId= baseBeanService.getObjectByHqlAndParams(hql.toString(), new Object[]{account,certificateType});
			map.put("operator", operator);
			map.put("mainId", mainId);
			if(mainId!=null){
				hql=new StringBuffer();
				hql.append("select new UpLoadFile(m.filepath) ");
				hql.append("from UpLoadFile m where m.parmeterID=? and m.filename=? order by m.filedesc");
				List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql.toString(), new Object[]{mainId,certificateType});
				UpLoadFile ud=new UpLoadFile();
				List<JSONObject> bb=new ArrayList<JSONObject>();
				JSONObject jo=new JSONObject();
				for (BaseBean tbean : list) {
					if(tbean!=null){
						ud=(UpLoadFile) tbean;
						if(ud.getFilepath()!=null){
							jo=new JSONObject();
							jo.accumulate("filepath", ud.getFilepath());
							bb.add(jo);
						}
					}
				}
				map.put("list", bb);
				map.put("ret", 0);
				JSONObject obj=JSONObject.fromObject(map);
				result=obj;
			}
		} catch (Exception e) {
			result=new JSONObject().accumulate("ret", 1);
		}
    	
		return Action.SUCCESS;
	}
	/**
	 * 个人证件列表
	 * account登录帐号
	 * operator证件类别   001：身份证  002：健康证  003：结婚证 004：驾驶证 005：工作证  006：毕业证  007：学生证
	 * @return
	 */
	public String findPersonCredential(){
		String account=request.getParameter("account");
		String operator=request.getParameter("operator");
		if(account==null||operator==null){
			result=new JSONObject().accumulate("ret", 1);
			return Action.SUCCESS;
		}
		JSONObject jo=new JSONObject();
		StringBuffer hql=new StringBuffer();
		hql.append("select tc.staffid from Staff sta,TEshopCusCom tc where tc.account=? and tc.staffid=sta.staffID");
		Object staffId=baseBeanService.getObjectByHqlAndParams(hql.toString(), new Object[]{account});
		if(staffId==null){
			result=new JSONObject().accumulate("ret", 1);
			return Action.SUCCESS;
		}
		jo.accumulate("staffId", isNull(staffId));
		hql=new StringBuffer("select new StaffPapers(sp.papersID,sp.positive,sp.back) from StaffPapers sp "); 
		hql.append(" where sp.staffID=? and sp.category=?");
		BaseBean bb=baseBeanService.getBeanByHqlAndParams(hql.toString(), new Object[]{staffId,operator});
		jo.accumulate("ret", 0);
		if(bb!=null){
			StaffPapers temp=(StaffPapers) bb;
			jo.accumulate("positive", isNull(temp.getPositive()));
			jo.accumulate("back", isNull(temp.getBack()));
			jo.accumulate("mainId", isNull(temp.getPapersID()));
			result=jo;
		}else{
			result=jo;
		}
		return Action.SUCCESS;
	}
	/**
	 * 个人证件修改或者添加
	 * mainId
	 * positive
	 * back
	 * staffId
	 * operator证件类别   001：身份证  002：健康证  003：结婚证 004：驾驶证 005：工作证  006：毕业证  007：学生证
	 * @return
	 */
	public String mergePersonCredential(){
		try {
			String operator=request.getParameter("operator");//必传
			String mainId=request.getParameter("mainId");
			String positive=request.getParameter("positive");
			String staffId=request.getParameter("staffId");
			String back=request.getParameter("back");
			if(mainId==null||mainId.equals("")){
				mainId=serverService.getServerID("StaffPaper");
				StaffPapers sp=new StaffPapers();
				sp.setBack(back);
				sp.setPositive(positive);
				sp.setPapersID(mainId);
				sp.setStaffID(staffId);
				sp.setCategory(operator);
				baseBeanService.save(sp);
			}else{
				String hql="from StaffPapers where papersID=?";
				StaffPapers sp=  (StaffPapers) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{mainId});
				if(sp!=null){
					String filpath = ServletActionContext.getServletContext().getRealPath("");
					try {
						if(sp.getBack()!=null){
							FileUtil.delete(filpath+sp.getBack());
						}
						if(sp.getPositive()!=null){
							FileUtil.delete(filpath+sp.getPositive());
						}
					} catch (Exception e) {
					}
					sp.setBack(back);
					sp.setPositive(positive);
					baseBeanService.saveOrUpdate(sp);
				}
			}
			result=new JSONObject().accumulate("ret", 0);
		} catch (Exception e) {
			result=new JSONObject().accumulate("ret", 1);
		}
		return Action.SUCCESS;
	}
}
