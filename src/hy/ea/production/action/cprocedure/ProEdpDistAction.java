package hy.ea.production.action.cprocedure;

import hy.base.action.BaseAction;
import hy.ea.bo.CAccount;
import hy.ea.bo.production.FieldDistribution;
import hy.ea.bo.production.ProEqpmDist;
import hy.ea.bo.production.ProEqpmDistToGood;
import hy.ea.production.service.WarehouseService;
import hy.ea.util.DateUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

/**
 * 设备分配管理
 * 
 * @author zgzg
 * 
 */

@Controller(value = "proedpdistAction")
@Scope(value = "prototype")
public class ProEdpDistAction extends BaseAction<ProEqpmDist> {
	private static final long serialVersionUID = 145401107002550696L;
	private ProEqpmDist entity = this.getModel();
	private String search;
	public InputStream excelStream;
	private ProEqpmDistToGood proetogood;
	private String devices;
	private List<BaseBean> list;
	private String fiveClear;
	private String type;
	private String category;
	
	@Resource
	private WarehouseService warehouseService;
	/**
	 * 获得session
	 */
	private Map<String, Object> session = ActionContext.getContext()
			.getSession();

	/**
	 * 查询列表
	 * 
	 * @return
	 */
	public String findList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		
		String hql = "from ProEqpmDist where companyId = ?  and type=? and category=?";
		List<Object> parm = new ArrayList<Object>();
		parm.add(account.getCompanyID());
		parm.add(type);parm.add(category);
		if (search != null && search.equals("search")) {
			entity = (ProEqpmDist) session.get("tableSearch");
			hql += " where 1=1";
			if (entity.getProdctSn() != null
					&& entity.getProdctSn().trim().length() > 0) {
				hql += " and prodctSn like ?";
				parm.add("%" + entity.getProdctSn().trim() + "%");
			}
			if (entity.getGoodsName() != null
					&& entity.getGoodsName().trim().length() > 0) {
				hql += " and goodsName like ?";
				parm.add("%" + entity.getGoodsName().trim() + "%");
			}
		}
		if(fiveClear!=null&&!"".equals(fiveClear)){
			hql += " and fiveClear = ?";
			parm.add(fiveClear);
		}
		hql+=" order by distDate desc";
		pageForm = baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, parm.toArray());
		if (entity != null) {
			ActionContext.getContext().getValueStack().push(entity);
		}
		return "list";
	}
	/*
	 * 获取分配表中关联的场地数据
	 */
	@SuppressWarnings("unchecked")
	public String ajaxGetFieData(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String pedId=re.getParameter("pedId");
		String[] pedIds=pedId.split(",");
		List<Object> obj=new ArrayList<Object>();
		String sql="select dist.ped_id,fie.siteaddress from dt_pro_eqpm_dist dist  " +
				"left join dt_pro_eqpm_dist_to_good good on good.pedId = dist.ped_id  " +
				"left join DTFIELDDISTRIBUTION fie on good.fieid = fie.fielddistributionid  where";
		String sql2="";
		for(int i=0;i<pedIds.length;i++){
			if(!"".equals(pedIds[i])&&pedIds[i]!=null){
				if("".equals(sql2)){
					sql2+=" ( dist.ped_id=?";
					obj.add(pedIds[i]);
				}else{
					sql2+=" or dist.ped_id=?";
					obj.add(pedIds[i]);
				}
			}
		}
		sql2+=") and fiveClear=?";
		obj.add(fiveClear);
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql+sql2, obj.toArray());
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("list",list);
		JSONObject js=JSONObject.fromObject(map);
		result=js;
		return "success";
	}

	/**
	 * 跳转添加页面
	 * 
	 * @return
	 */
	public String toPage() {
		if (entity != null && entity.getPedId()!=null&&!entity.getPedId().equals("")) {
			String hql = "from ProEqpmDist where pedId = ?";

			entity = (ProEqpmDist) baseBeanService.getBeanByHqlAndParams(hql,
					new Object[] { entity.getPedId() });
            if(entity.getPedStartDate()!=null){
			entity.setSdate(Utilities.getDateString(entity.getPedStartDate(), "yyyy-MM-dd"));
            }
            if(entity.getPedEndDate()!=null){
			entity.setEdate(Utilities.getDateString(entity.getPedEndDate(), "yyyy-MM-dd"));
            }
            if(entity.getDistDate()!=null){
			entity.setOdate(Utilities.getDateString(entity.getDistDate(), "yyyy-MM-dd hh:mm:ss"));
            }

			String hqldevice = "from ProEqpmDistToGood where pedId = ?";
			List<BaseBean> list = baseBeanService
					.getListBeanByHqlAndParams(hqldevice,
							new Object[] { entity.getPedId() });
			String device = "";
			String options = "";
			for(BaseBean b:list){
				ProEqpmDistToGood g = (ProEqpmDistToGood) b;
				device+=g.getGoodId()+"-"+g.getGoodsName()+",";
				options+="<option  title='双击移除' value='"+g.getGoodId()+"' >"+g.getGoodsName()+"</option>";
				String fieHql="from FieldDistribution where fieldDistributionId=?";
				FieldDistribution fie=(FieldDistribution) baseBeanService.getBeanByHqlAndParams(fieHql, new Object[]{g.getFieId()==null?"":g.getFieId()});
				request.setAttribute("fie", fie);
			}
			ActionContext.getContext().getValueStack().push(entity);
			request.setAttribute("options", options);
			request.setAttribute("device", device);
			request.setAttribute("fiveClear",fiveClear);

		}

		return "toPage";
	}

	public String toSearch() {
		session.put("tableSearch", entity);
		return findList();
	}
	
	/*
	 * 获取场地分配信息
	 */
	public String ajaxAcquisitionSite(){
		HttpServletRequest re=ServletActionContext.getRequest();
		String ppID=re.getParameter("ppID");
		String hql="from FieldDistribution where ppId=?";
		Object[] obj={ppID};
		pageForm=baseBeanService.getPageForm(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 10 : pageNumber), hql, obj);
		Object con=baseBeanService.getObjectBySqlAndParams("select count(*) from DTFIELDDISTRIBUTION where ppId=?", obj);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("pageForm",pageForm);
		map.put("con",con);
		JSONObject js=JSONObject.fromObject(map);
		result=js;
		return "success";
	}
	public String saveProEdp() {
		List<BaseBean> beans = new ArrayList<BaseBean>();
		List<String> hqls=new ArrayList<String>();
		List<Object> parms=new ArrayList<Object>();
		
		if(entity.getPedId()==null||entity.getPedId().equals("")){
			entity.setPedId(serverService.getServerID("ProEqpmDist"));
		}else{
			//删除function
			String hqldelete = "delete from ProEqpmDistToGood where pedId = ?";
			hqls.add(hqldelete);
			parms.add(entity.getPedId());
			
		}
		entity.setCompanyId(this.getCurrentAccount().getCompanyID());
		entity.setFiveClear(fiveClear);
		
		if (entity.getSdate().length() > 0) {
			entity.setPedStartDate(DateUtil.parseDate("yyyy-MM-dd",
					entity.getSdate()));
		}
		if (entity.getEdate().length() > 0) {
			entity.setPedEndDate(DateUtil.parseDate("yyyy-MM-dd",
					entity.getEdate()));
		}
		if (entity.getOdate().length() > 0) {
			entity.setDistDate(DateUtil.parseDate("yyyy-MM-dd hh:mm:ss",
					entity.getOdate()));
		}
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String devices = request.getParameter("devices");

		

		ProEqpmDistToGood proetogood = null;
		String[] array1 = devices.split(",");
		String str = "";
		HttpServletRequest re=ServletActionContext.getRequest();
		String fieId=re.getParameter("field");
		for (int i = 0; i < array1.length; i++) {
			String[] array2 = array1[i].split("-");
			proetogood = new ProEqpmDistToGood();
			proetogood.setPedtgId(serverService.getServerID("pedtid"));
			proetogood.setGoodId(array2[0]);
			proetogood.setGoodsName(array2[1]);
			proetogood.setPedId(entity.getPedId());
			proetogood.setFieId(fieId);
			str += array2[1] + ",";

			beans.add(proetogood);

		}
		entity.setDevices(str.substring(0, str.length() - 1));
		beans.add(entity);
		baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, hqls.toArray(new String[]{}), parms.toArray());

		return "success";
	}

	public String deleteProEdp() {
		String hql1 = "delete ProEqpmDistToGood where pedId=?";
		String hql2 = "delete ProEqpmDist where pedId=?";
		baseBeanService.saveBeansListAndexecuteHqlsByParams(null, new String[] {
				hql1, hql2 }, new Object[] { entity.getPedId() });
		return "success";
	}
/*
 * 添加东西，原有功能失效，所以注释  zj

	public String showExcel() {
		List<Object> parm = new ArrayList<Object>();
		String hql = " from ProEqpmDist where companyId = ?";
		
		parm.add(this.getCurrentAccount().getCompanyID());
		if (search != null && search.equals("search")) {
			entity = (ProEqpmDist) session.get("tableSearch");
			if (entity.getProdctSn() != null
					&& entity.getProdctSn().trim().length() > 0) {
				hql += " and prodctSn like ?";
				parm.add("%" + entity.getProdctSn().trim() + "%");
			}
			if (entity.getGoodsName() != null
					&& entity.getGoodsName().trim().length() > 0) {
				hql += " and goodsName like ?";
				parm.add("%" + entity.getGoodsName().trim() + "%");
			}
		}

		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(hql,
				parm.toArray());
		excelStream = excelService
				.showExcel(ProEqpmDist.columnHeadings(), list);
		CLogBook logBook = logBookService.saveCLogBook(null, "导出设备分配表",
				this.getCurrentAccount());
		baseBeanService.update(logBook);
		return "showexcel";
	}*/
	
	
	@SuppressWarnings("unchecked")
	public String showExcel() {
		List<Object> parm = new ArrayList<Object>();
		String sql="select dist.PRODCT_SN,dist.goodsName,dist.PED_START_DATE,dist.PED_END_DATE," +
				"dist.staffName,dist.duty,dist.distDate,dist.devices,fie.siteaddress," +
				"dist.distRemark from dt_pro_eqpm_dist dist" +
				" left join dt_pro_eqpm_dist_to_good good on good.pedId = dist.ped_id" +
				" left join DTFIELDDISTRIBUTION fie on good.fieid = fie.fielddistributionid where dist.COMPANYID = ?";
		parm.add(this.getCurrentAccount().getCompanyID());
		if (search != null && search.equals("search")) {
			entity = (ProEqpmDist) session.get("tableSearch");
			if (entity.getProdctSn() != null
					&& entity.getProdctSn().trim().length() > 0) {
				sql += " and dist.PRODCT_SN like ?";
				parm.add("%" + entity.getProdctSn().trim() + "%");
			}
			if (entity.getGoodsName() != null
					&& entity.getGoodsName().trim().length() > 0) {
				sql += " and dist.goodsName like ?";
				parm.add("%" + entity.getGoodsName().trim() + "%");
			}
		}
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql, parm.toArray());
		String[] str={"项目产品编号","项目产品名称","开始时间","结束时间","分配责任人","职责","分配时间","设备","所属场地","分配备注"};
		excelStream=warehouseService.OutOrderExcel("设备分配管理", str, list);
		return "showexcel";
	}

	/**
	 * 
	 * 
	 * 获取设备
	 * 
	 * @return
	 */
	public String toPrintDevices() {

		List<Object> parm = new ArrayList<Object>();

		String hql = " from ProEqpmDist where companyId = ?";

		parm.add(this.getCurrentAccount().getCompanyID());
		if (search != null && search.equals("search")) {
			entity = (ProEqpmDist) session.get("tableSearch");
			if (entity.getProdctSn() != null
					&& entity.getProdctSn().trim().length() > 0) {
				hql += " and prodctSn like ?";
				parm.add("%" + entity.getProdctSn().trim() + "%");
			}
			if (entity.getGoodsName() != null
					&& entity.getGoodsName().trim().length() > 0) {
				hql += " and goodsName like ?";
				parm.add("%" + entity.getGoodsName().trim() + "%");
			}
		}

		list = baseBeanService.getListBeanByHqlAndParams(hql,
				parm.toArray());
		
		return "toprint";
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public ProEqpmDistToGood getProetogood() {
		return proetogood;
	}

	public void setProetogood(ProEqpmDistToGood proetogood) {
		this.proetogood = proetogood;
	}

	public String getDevices() {
		return devices;
	}

	public void setDevices(String devices) {
		this.devices = devices;
	}

	public List<BaseBean> getList() {
		return list;
	}

	public void setList(List<BaseBean> list) {
		this.list = list;
	}
	public String getFiveClear() {
		return fiveClear;
	}

	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
}
