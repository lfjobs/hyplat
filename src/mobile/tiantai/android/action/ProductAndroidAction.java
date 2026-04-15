package mobile.tiantai.android.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hy.ea.bo.CAccount;
import hy.ea.bo.CCode;
import hy.ea.bo.Company;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.CashierBills;
import hy.ea.bo.finance.GoodsBills;
import hy.ea.bo.finance.ProLayout;
import hy.ea.bo.finance.ProSetup;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.AttriProduction;
import hy.ea.bo.production.BsimTest;
import hy.ea.bo.production.BudgetPlan;
import hy.ea.bo.production.GoodFunction;
import hy.ea.bo.production.ProductionAmount;
import hy.ea.bo.production.ProductionDocuments;
import hy.ea.bo.production.StaffTrack;
import hy.ea.service.UpLoadFileService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.RandomDatas;
import hy.ea.util.ToChineseFirstLetter;
import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class ProductAndroidAction {
	@Resource
	private ServerService serverService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private UploadContentToFileService contentToFileService;
	public ServletRequest request = ServletActionContext.getRequest();
	private Object result;
	private String accountID;   						//登录ID
	private String fiveClear;							//部门
	
	/**  ------------------------------------------------------------------------------物品设计-----------------------------------------------------------------------------   */
	/*
	 * 获取物品设计列表
	 */
	@SuppressWarnings("unchecked")
	public String getGoodDesignList(){
		JSONObject ret=new JSONObject();
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		String hql="select CASE WHEN g.goodsID is null THEN ' ' else g.goodsID end" +
				",CASE WHEN ap.imgurl is null THEN ' ' else ap.imgurl end" +
				",CASE WHEN g.tradeName is null THEN ' ' else g.tradeName end" +
				",CASE WHEN g.goodsname is null THEN ' ' else g.goodsname end" +
				" from dtGoodsManage g left join dtAttriProduction ap on g.goodsID=ap.goodsID and" +
				" ap.type=2 and ap.sort=1 where g.companyID=? and g.goodsState=? and g.fiveClear=?" +
				" order by g.createdate";
		Object[] obj={cc.getCompany().getCompanyID(),"00","4"};
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(hql,obj);
		if(list.size()>0){
			ret.accumulate("list",isNull(list));
		}else{ 
			ret.accumulate("list","");
		}
		result=ret;
		return Action.SUCCESS;
	}
	/*
	 * 获取物品详细信息
	 */
	public String getItemDetails(){
		JSONObject ret=new JSONObject();
		String goodsID=request.getParameter("goodsID");
		Object goods=baseBeanService.getObjectBySqlAndParams(
				"select  CASE WHEN goodsID is null THEN ' ' else goodsID end" +
					",CASE WHEN goodsName is null THEN ' ' else goodsName end" +
					",CASE WHEN tradeName is null THEN ' ' else tradeName end" +
					",CASE WHEN typeID is null THEN ' ' else typeID end" +
					",CASE WHEN barCode is null THEN ' ' else barCode end" +
					",CASE WHEN goodsCoding is null THEN ' ' else goodsCoding end" +
					",CASE WHEN brand is null THEN ' ' else brand end" +
					",CASE WHEN model is null THEN ' ' else model end" +
					",CASE WHEN standard is null THEN ' ' else standard end" +
				" from dtGoodsManage where goodsID=?", new Object[]{goodsID});
		if(goods!=null)
			ret.accumulate("goods",isNull(goods));
		@SuppressWarnings("unchecked")
		List<Object> attrList=baseBeanService.getListBeanBySqlAndParams(
				"select CASE WHEN apid is null THEN ' ' else apid end" +
					",CASE WHEN attriname is null THEN ' ' else attriname end" +
					",CASE WHEN attrivalue is null THEN ' ' else attrivalue end" +
					",CASE WHEN imgurl is null THEN ' ' else imgurl end" +
					",CASE WHEN type is null THEN ' ' else type end" +
					",CASE WHEN sort is null THEN 0 else sort end" +
				" from dtAttriProduction where goodsid=?", new Object[]{goodsID});
		if(attrList.size()>0)
			ret.accumulate("attrList",isNull(attrList));
		@SuppressWarnings("unchecked")
		List<Object> gfList=baseBeanService.getListBeanBySqlAndParams(
				"select CASE WHEN gfid is null THEN ' ' else gfid end" +
					",CASE WHEN name is null THEN ' ' else name end" +
					",CASE WHEN url is null THEN ' ' else url end" +
					",CASE WHEN orders is null THEN 0 else orders end" +
				" from dtGoodFunction where goodsid=?", new Object[]{goodsID});
		if(gfList.size()>0)
			ret.accumulate("gfList",isNull(gfList));
		result=ret;
		return Action.SUCCESS;
	}
	/*
	 * 添加或修改物品设计
	 */
	public String GoodDesignData(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		List<String> sqls=new ArrayList<String>();
		List<Object[]> objs=new ArrayList<Object[]>();
		List<BaseBean> list=new ArrayList<BaseBean>();
		Map<String,Object> map=new HashMap<String, Object>();
		String goodsID=request.getParameter("goodsID");													//物品ID
		String goodsName=request.getParameter("goodsName");									//物品名称
		String typeID=request.getParameter("typeID");															//物品类别
		String barCode=request.getParameter("barCode");													//物品条码
		String standard=request.getParameter("standard");												//物品规格
		String model=request.getParameter("model");															//型号
		String tradeID=request.getParameter("tradeID");														//行业ID
		String tradeName=request.getParameter("tradeName");										//行业名称
		String brand=request.getParameter("brand");															//品牌		
		String sizename = request.getParameter("sizename");											//尺码属性名
		String colorname=request.getParameter("colorname");										//颜色属性名
		String describe=request.getParameter("describe");													//物品描述
		String  gfid=request.getParameter("gfid");																	//要删除的文本ID

		String photoPath=request.getParameter("photoPath");										//主图路径
		if(photoPath!=null&&!"".equals(photoPath))
			map.put("photoPaths", photoPath.split(","));
		
		String sizevalue = request.getParameter("sizevalue");											//尺码属性值
		if(sizevalue!=null&&!"".equals(sizevalue))
			map.put("sizevalues", sizevalue.split(","));
		
		String colorvalue=request.getParameter("colorvalue");										//颜色属性值
		if(colorvalue!=null&&!"".equals(colorvalue))
			map.put("colorvalues", colorvalue.split(","));
		
		String shippath=request.getParameter("shippath");												//物品视频
		if(shippath!=null&&!"".equals(shippath))
			map.put("shippaths", shippath.split(","));
		
		String picture=request.getParameter("picture");														//物品图片
		if(picture!=null&&!"".equals(picture))
			map.put("pictures", picture.split(","));
		
		String delPicture=request.getParameter("delPicture");											//要删除的图片路径
		if(delPicture!=null&&!"".equals(delPicture))
			map.put("delPictures", delPicture.split(","));
		
		String  apid=request.getParameter("apid");																	//属性ID
		if(apid!=null&&!"".equals(apid))
			map.put("apids", apid.split(","));
		
		try {
			GoodsManage goods=(GoodsManage)baseBeanService.getBeanByHqlAndParams(
					"from GoodsManage where goodsID=?",new Object[]{goodsID});
			if(goods==null){
				goods=new GoodsManage();
				goods.setGoodsID(serverService.getServerID("GoodsManage"));
				goods.setCompanyID(cc.getCompany().getCompanyID());
				// 编号处理
				String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in" +
						" (select c.companyID from Company c where c.companyID=?)  and vt.typeID=? ";
				Object[] params = { cc.getCompany().getCompanyID(),typeID};
				// 获取拼音码
				String pinyin = ToChineseFirstLetter.getFirstLetter(typeID==null?"":typeID);
				String Upstr = pinyin.toUpperCase();// 转换为大写
				int goodscodingnum = baseBeanService.getConutByByHqlAndParams(hql,params);
				DecimalFormat form = new DecimalFormat("000000");
				String ss = form.format(goodscodingnum + 1);
				goods.setGoodsCoding(Upstr + "_" + ss);
			}
			
			goods.setGoodsName(goodsName);
			goods.setTypeID(typeID);
			goods.setStandard(standard);
			goods.setModel(model);
			goods.setGoodsState("00");
			goods.setFiveClear("4");
			goods.setTradeID(tradeID);
			goods.setTradeName(tradeName);
			goods.setBrand(brand);
			goods.setBarCode(barCode);
			list.add(goods);
			//主图
			String[] photoPaths=(String[]) map.get("photoPaths");
			if(photoPaths!=null){
				for(int i=0;i<photoPaths.length;i++){
					AttriProduction ap=new AttriProduction();
					ap.setApid(serverService.getServerID("AttriProduction"));
					ap.setAttriname("产品主图");
					ap.setAttrivalue(photoPaths[i]);
					ap.setGoodsid(goods.getGoodsID());
					ap.setType("2");
					ap.setSort(i);
					list.add(ap);
				}
			}
			String[] sizevalues=(String[]) map.get("sizevalues");
			if(sizevalues!=null){
				for(int i=0;i<sizevalues.length;i++){
					AttriProduction ap=new AttriProduction();
					ap.setApid(serverService.getServerID("AttriProduction"));
					ap.setAttriname(sizename);
					ap.setAttrivalue(sizevalues[i]);
					ap.setGoodsid(goods.getGoodsID());
					ap.setType("0");
					ap.setSort(i);
					list.add(ap);
				}
			}
			String[] colorvalues=(String[]) map.get("colorvalues");
			if(colorvalues!=null){
				for(int i=0;i<colorvalues.length;i++){
					AttriProduction ap=new AttriProduction();
					ap.setApid(serverService.getServerID("AttriProduction"));
					ap.setAttriname(colorname);
					ap.setAttrivalue(colorvalues[i]);
					ap.setGoodsid(goods.getGoodsID());
					ap.setType("1");
					ap.setSort(i);
					list.add(ap);
				}
			}
			String[] shippaths=(String[]) map.get("shippaths");
			if(shippaths!=null){
				for(int i=0;i<shippaths.length;i++){
					AttriProduction ap=new AttriProduction();
					ap.setApid(serverService.getServerID("AttriProduction"));
					ap.setAttriname("物品视频");
					ap.setImgurl(shippaths[i]);
					ap.setGoodsid(goods.getGoodsID());
					ap.setType("3");
					list.add(ap);
				}
			}
			String[] apids=(String[]) map.get("apids");
			if(apids!=null){
				String sql="delete AttriProduction where apid in (";
				List<Object> obj=new ArrayList<Object>();
				for(int i=0;i<apids.length;i++){
					if(i==0){
						sql+="?";obj.add(apids[i]);
					}else{
						sql+=",?";obj.add(apids[i]);
					}
				}
				sqls.add(sql+")");
				objs.add(obj.toArray());
			}			
			String content="";
			content+="<p>"+describe+"</p>";
			String[] pictures=(String[]) map.get("pictures");
			if(pictures!=null){
				for(int i=0;i<pictures.length;i++){
					content+="<img src='"+pictures[i]+"' />";
				}
			}
			String url=saveContentToFile(content);
			GoodFunction gf=(GoodFunction) baseBeanService.getBeanByHqlAndParams(
					"from GoodFunction where gfid=? goodsid", new Object[]{gfid,goods.getGoodsID()});
			String path = request.getServletContext().getRealPath("/");
			if(gf==null){
				gf=new GoodFunction();
				gf.setGfid(serverService.getServerID("GoodFunction"));
				gf.setName("物品简介");
				gf.setGoodsid(goods.getGoodsID());
			}else{
				String[] delPictures=(String[]) map.get("delPictures");
				if(delPictures!=null){
					for(int i=0;i<delPictures.length;i++){
						delPictures[i]=path+delPictures[i];
					}
				}
				ArrayList<String> arrayList = new ArrayList<String>();
				Collections.addAll(arrayList, delPictures);
				arrayList.add(path+gf.getUrl());
				fileService.deletePhotos(arrayList);
			}
			gf.setUrl(url);
			list.add(gf);
			baseBeanService.executeSqlsByParmsList(list, sqls.toArray(new String[]{}), objs);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;
		return Action.SUCCESS;
	}
	
	/*
	 * 删除物品
	 */
	public String  DeleteDetails(){
		String goodsID=request.getParameter("goodsID");
		JSONObject temp = new JSONObject();
		try {
			ArrayList<String> arrayList=new ArrayList<String>();
			List<String> sqls=new ArrayList<String>();
			Object[] obj={goodsID};
			String path = request.getServletContext().getRealPath("/");
			List<BaseBean> attrList=baseBeanService.getListBeanByHqlAndParams(
					"from AttriProduction where goodsid=?",obj);
			for(int i=0;i<attrList.size();i++){
				arrayList.add(path+((AttriProduction)attrList.get(i)).getImgurl());
			}
			List<BaseBean> gfList=baseBeanService.getListBeanByHqlAndParams(
					"from GoodFunction where goodsid=?",obj);
			for(int i=0;i<gfList.size();i++){
				arrayList.add(path+((GoodFunction)gfList.get(i)).getUrl());
			}
			fileService.deletePhotos(arrayList);
			sqls.add("delete dtgoodsManage where goodsID=?");
			sqls.add("delete dtAttriProduction where goodsID=?");
			sqls.add("delete dtGoodFunction where goodsID=?");
			baseBeanService.saveBeansListAndexecuteSqlsByParams(null, sqls.toArray(new String[]{}),obj);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		
		return Action.SUCCESS;
	}
	/**  ----------------------------------------------------------------------------物品设计结束----------------------------------------------------------------------   */

	/**  ------------------------------------------------------------------------------产品设计---------------------------------------------------------------------------   */

	/*
	 *  获取产品设计列表
	 */
	public String  getProductsList(){
		JSONObject ret=new JSONObject();
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		List<Object> obj=new ArrayList<Object>();
		String hierarchical=request.getParameter("hierarchical");
		String sql="select CASE WHEN p.ppID is null THEN ' ' else p.ppID end" +
				",CASE WHEN p.productCode is null THEN ' ' else p.productCode end" +
				",CASE WHEN p.goodsName is null THEN ' ' else p.goodsName end" +
				",CASE WHEN p.tradeName is null THEN ' ' else p.tradeName end" +
				",CASE WHEN p.quantity is null THEN ' ' else p.quantity end" +
				",CASE WHEN p.image is null THEN ' ' else p.image end" +
				" from dt_ProductPackaging p where p.companyID=? and p.delStatus=? and p.fiveClear=? ";
		obj.add(cc.getCompany().getCompanyID());obj.add("00");obj.add("4");
		if(!"".equals(hierarchical)&&hierarchical!=null){
			sql+=" and p.hierarchical=?";
			obj.add(hierarchical);
		}
		@SuppressWarnings("unchecked")
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql,obj.toArray());
		if(list.size()>0){
			ret.accumulate("list",isNull(list));
		}else{ 
			ret.accumulate("list","");
		}
		result=ret;
		return Action.SUCCESS;
	}
	/*
	 * 获取产品信息
	 */
	public String getProductsData(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject ret=new JSONObject();
		String ppID=request.getParameter("ppID");
		StringBuffer sql=new StringBuffer("select CASE WHEN p.ppID is null THEN ' ' else p.ppID end" +
				",CASE WHEN p.parentId is null THEN ' ' else p.parentId end" +
				",CASE WHEN p.parentName is null THEN ' ' else p.parentName end" +
				",CASE WHEN p.goodsID is null THEN ' ' else p.goodsID end" +
				",CASE WHEN p.image is null THEN ' ' else p.image end" +
				",CASE WHEN p.subjectID is null THEN ' ' else p.subjectID end" +
				",CASE WHEN p.subjectName is null THEN ' ' else p.subjectName end" +
				",CASE WHEN p.price is null THEN ' ' else p.price end" +
				",CASE WHEN p.quantity is null THEN ' ' else p.quantity end" +
				",CASE WHEN p.money is null THEN ' ' else p.money end" +
				",CASE WHEN s.RE_PRICE is null THEN ' ' else s.RE_PRICE end" +
				",CASE WHEN s.BROKERAGE is null THEN ' ' else s.BROKERAGE end" +
				",CASE WHEN p.remark is null THEN ' ' else p.remark end" +
				" from dt_ProductPackaging p left join dt_pro_setup s on s.ppid=p.ppid where" +
				" p.companyID=? and p.delStatus=? and p.fiveClear=? and p.ppID=?");
		Object obj=baseBeanService.getObjectBySqlAndParams(sql.toString(),
				new Object[]{cc.getCompany().getCompanyID(),"00","4",ppID});
		ret.accumulate("obj",isNull(obj));
		result=ret;
		return Action.SUCCESS;
	} 
	/*
	 * 添加产品
	 */
	public String addProductsData(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		List<BaseBean> list=new ArrayList<BaseBean>();
		JSONObject temp = new JSONObject();
		
		String ppID=request.getParameter("ppID");										//产品ID
		String parentId=request.getParameter("parentId");						//父级产品ID
		String parentName=request.getParameter("parentName");		//父级产品名称
		String goodsID=request.getParameter("goodsID");							//物品ID
		String subjectID=request.getParameter("subjectID");						//科目ID
		String subjectName=request.getParameter("subjectName");		//科目名称
		String price=request.getParameter("price");										//单价
		String quantity=request.getParameter("quantity");							//数量
		String money=request.getParameter("money");								//总金额
		String pretium=request.getParameter("pretium");							//销售价
		String profitAmount=request.getParameter("profitAmount");	//佣金
		String virtual=request.getParameter("virtual");									//虚拟物品
		String remark=request.getParameter("remark");								//备注
		
	try {
		ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?", new Object[]{ppID});
		ProductPackaging parent=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
				"from ProductPackaging where ppID=?", new Object[]{parentId});
		if(pp==null){
			Object photo=baseBeanService.getObjectBySqlAndParams(
					"select imgurl from dtAttriProduction where goodsid=? and type=? and sort=?",
					new Object[]{goodsID,"2","1"});
			GoodsManage goods=(GoodsManage)baseBeanService.getBeanByHqlAndParams(
					"from GoodsManage where goodsID=?", new Object[]{goodsID});
			pp=new ProductPackaging();
			pp.setPpID(serverService.getServerID("ProductPackaging"));
			pp.setGoodsID(goods.getGoodsID());
			pp.setCompanyID(cc.getCompany().getCompanyID());
			pp.setStaffID(cc.getStaff().getStaffID());
			pp.setGoodsName(goods.getGoodsName());
			pp.setStandard(goods.getStandard());
			pp.setImage(photo.toString());
			pp.setFiveClear("4");
			pp.setBarCode(goods.getBarCode());
			pp.setModel(goods.getModel());
			pp.setTradeID(goods.getTradeID());
			pp.setTradeName(goods.getTradeName());
			pp.setProductCode(goods.getGoodsCoding());
			pp.setProducttype(goods.getTypeID());
			pp.setBrand(goods.getBrand());
			pp.setDelStatus("00");
			pp.setProductstate("00");
			pp.setYjstatus("01");
		}
		pp.setSubjectID(subjectID);
		pp.setSubjectName(subjectName);
		pp.setParentId(parentId);
		pp.setParentName(parentName);
		pp.setPrice(price);
		pp.setQuantity(quantity);
		pp.setMoney(money);
		pp.setVirtual(virtual);
		pp.setRemark(remark);
		pp.setHierarchical(parent==null?"0":(Integer.parseInt(parent.getHierarchical())+1+""));
		list.add(pp);
		ProSetup ps=(ProSetup) baseBeanService.getBeanByHqlAndParams(
				"from ProSetup where ppid=? and comId=?", new Object[]{pp.getPpID(),cc.getCompany().getCompanyID()});
		if(ps==null){
			ps=new ProSetup();
			ps.setSuid(serverService.getServerID("ProSetup"));
			ps.setComId(cc.getCompany().getCompanyID());
			ps.setPpid(pp.getPpID());
			ps.setPpname(pp.getGoodsName());
			}
		ps.setEfPrice(pp.getPrice());
		ps.setRePrice(pretium);
		ps.setBrokerage(profitAmount);
		ps.setSjdate(new Date());
		list.add(ps);
		@SuppressWarnings("unchecked")
		List<Object> objList=baseBeanService.getListBeanBySqlAndParams(
				"select m.yj_id,l.pro_money,l.meid,l.bsid from dt_pro_main m left join dt_pro_layout l on l.meid=m.yj_id"
	   		 +" where m.com_ID=? and m.dc_status=? and l.com_id=? and  l.statu=?", 
			 	new Object[]{cc.getCompany().getCompanyID(),"00",cc.getCompany().getCompanyID(),"00"});
		for(int i=0;i<objList.size();i++){
			Object[] oss=(Object[]) objList.get(i);
			ProLayout pl=new ProLayout();
			pl.setLoid(serverService.getServerID("ProLayout"));
			pl.setSuid(ps.getSuid());
			pl.setBsid(oss[3].toString());
			pl.setMeid(oss[2].toString());
			pl.setProMoney(Double.parseDouble(oss[1].toString().split("%")[0])/100
					*Double.parseDouble(profitAmount==null?"0":profitAmount)+"");
			pl.setPmStatu("00");
			pl.setComId(cc.getCompany().getCompanyID());
			pl.setStatu("01");
			list.add(pl);
		}
		baseBeanService.saveBeansListAndexecuteSqlsByParams(list, new String[]{
				"delete dt_pro_Layout where Suid=? and statu=? and pm_Statu=?"}, new Object[]{ps.getSuid(),"01","00"});
		temp.accumulate("result", "0");
	} catch (Exception e) {
		temp.accumulate("result", "1");
	}
	result=temp;
		return Action.SUCCESS;
	}
	
	/*
	 *删除产品信息 
	 */
	public String delProductsData(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		String ppID=request.getParameter("ppID");
		try {
			ProSetup ps=(ProSetup) baseBeanService.getBeanByHqlAndParams(
					"from ProSetup where ppid=? and comId=?", new Object[]{ppID,cc.getCompany().getCompanyID()});
			List<String> sqls=new ArrayList<String>();
			List<Object[]> objs=new ArrayList<Object[]>();
			sqls.add("delete dt_ProductPackaging where ppID=? and companyID=?");
			objs.add(new Object[]{ppID,cc.getCompany().getCompanyID()});
			if(ps!=null){
				sqls.add("delete dt_pro_setup where suid=? and com_Id=?");
				objs.add(new Object[]{ps.getSuid(),cc.getCompany().getCompanyID()});
				sqls.add("delete dt_pro_layout where suid=? and com_Id=?");
				objs.add(new Object[]{ps.getSuid(),cc.getCompany().getCompanyID()});
			}
			baseBeanService.executeSqlsByParmsList(null, sqls.toArray(new String[]{}), objs);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;
		return Action.SUCCESS;
	}
	
	/**
	 * 提交模拟测试
	 * 
	 */
	public String  transferToSim(){
		JSONObject temp = new JSONObject();
		String ppID=request.getParameter("ppID");
		try {
			String hql = "from ProductPackaging where ppID = ?";
			ProductPackaging pp = (ProductPackaging) baseBeanService.getBeanByHqlAndParams(
					hql,new Object[]{ppID});
			
			BsimTest bt = new BsimTest();
			bt.setBsimTestId(serverService.getServerID("testid"));
			bt.setGoodBar(pp.getBarCode());
			bt.setGoodName(pp.getGoodsName());
			bt.setGoodStandard(pp.getStandard());
			bt.setIndustryClassification(pp.getTradeCode());
			bt.setPrice(pp.getPrice());
			bt.setMoney(pp.getMoney());
			bt.setBtnumber(pp.getQuantity());
			bt.setItemNumber(pp.getProductCode());
			bt.setStatus("00");
			bt.setId(pp.getPpID());
					
			bt.setAuditTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			List<BaseBean> beans = new ArrayList<BaseBean>();
			pp.setProductstate("01");
			beans.add(pp);
			beans.add(bt);
			baseBeanService.executeHqlsByParamsList(beans,null,null);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;		
		return Action.SUCCESS;
	}
	
	/**  ------------------------------------------------------------------------------产品设计结束-----------------------------------------------------------------------   */

	/**  ------------------------------------------------------------------------------项目流程设计-----------------------------------------------------------------------   */

	public String projectProcess(){
		String ppID=request.getParameter("ppID");
		JSONObject temp = new JSONObject();
		StringBuffer sql=new StringBuffer();
		sql.append("select CASE WHEN pp.ppid is null THEN ' ' else pp.ppid end" +
				",CASE WHEN pp.productCode is null THEN ' ' else pp.productCode end" +
				",CASE WHEN pp.goodsName is null THEN ' ' else pp.goodsName end" +
				"  from (select * from dt_ProductPackaging p connect by p.parentId= prior p.ppID" +
				"  start with p.ppID=?) pp  where pp.hierarchical!=?");
		@SuppressWarnings("unchecked")
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(
				sql.toString(),new Object[]{ppID,"0"});
		if(list.size()>0)  
			temp.accumulate("list",isNull(list));
		result=temp;
		return Action.SUCCESS;
	}
	
	/**  -------------------------------------------------------------------------项目流程设计结束-------------------------------------------------------------------------   */

	/**  --------------------------------------------------------------------------项目预算量设计---------------------------------------------------------------------------   */
	/*
	 * 获取预算量列表
	 */
	public String getBudgetPlanList(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject ret=new JSONObject();
		StringBuffer sql=new StringBuffer("select CASE WHEN bp.bpid is null THEN ' ' else bp.bpid end" +
				",CASE WHEN pp.image is null THEN ' ' else pp.image end" +
				",CASE WHEN pp.tradeName is null THEN ' ' else pp.tradeName end" +
				",CASE WHEN pp.goodsName is null THEN ' ' else pp.goodsName end" +
				",CASE WHEN bp.quantity is null THEN ' ' else bp.quantity end" +
				" from dtPBudgetPlan bp left join dt_ProductPackaging pp on bp.productID=pp.ppid" +
				" where bp.companyID=? and bp.type=?");
		@SuppressWarnings("unchecked")
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql.toString(),
				new Object[]{cc.getCompany().getCompanyID(),"01"});
		if(list.size()>0){
			ret.accumulate("list",isNull(list));
		}
		result=ret;
		return Action.SUCCESS;
	}
	
	/*
	 *添加预算量 
	 */
	public String addBudgetPlan(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		
		String ppID=request.getParameter("ppID");										//产品ID
		String bpid=request.getParameter("bpid");											//预算量ID
		String deviceNum=request.getParameter("deviceNum");				//流水线设备套数
		String maxBydevice=request.getParameter("maxBydevice");		//单套设备最大用人量
		String maxByhbyd=request.getParameter("maxByhbyd");			//单套设备每小时最大生产量
		String worktimeByd=request.getParameter("worktimeByd");		//单套设备每天工作多少时间hour
		String maxDay=request.getParameter("maxDay");							//日最大生产量
		String maxWeek=request.getParameter("maxWeek");					//周多大生产量
		String maxMonth=request.getParameter("maxMonth");				//月最大生产量
		String maxSeason=request.getParameter("maxSeason");				//周最大生产量
		String maxYear=request.getParameter("maxYear");						//年最大生产量
		String year=request.getParameter("year");											//预算年份
		String remark=request.getParameter("remark");								//备注
		
		try {
			BudgetPlan bp=(BudgetPlan)baseBeanService.getBeanByHqlAndParams(
					"from BudgetPlan where bpid=?",new Object[]{bpid});
			if(bp==null){
				ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
						"from ProductPackaging where ppID=?",new Object[]{ppID});
				bp=new BudgetPlan();
				bp.setBpid(serverService.getServerID("BudgetPlan"));
				bp.setTradeCode(pp.getTradeCode());
				bp.setProductCode(pp.getProductCode());
				bp.setProductName(pp.getGoodsName());
				bp.setProductID(pp.getPpID());
				bp.setPrice(pp.getPrice());
				bp.setQuantity(pp.getQuantity());
				bp.setMoney(pp.getMoney());
				bp.setCompanyID(cc.getCompany().getCompanyID());
				bp.setProducttype(pp.getProducttype());
			}
			bp.setDeviceNum(deviceNum);
			bp.setMaxBydevice(maxBydevice);
			bp.setMaxByhbyd(maxByhbyd);
			bp.setWorktimeByd(worktimeByd);
			bp.setMaxDay(maxDay);
			bp.setMaxWeek(maxWeek);
			bp.setMaxMonth(maxMonth);
			bp.setMaxSeason(maxSeason);
			bp.setMaxYear(maxYear);
			bp.setCreateDate(new Date());
			bp.setType("01");
			bp.setYear(year);
			bp.setRemark(remark);
			baseBeanService.saveOrUpdate(bp);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;		
		return Action.SUCCESS;
	}
	
	/*
	 * 获取产品预算量详细信息
	 */
	public String getBudgetPlan(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		String bpid=request.getParameter("bpid");
		JSONObject temp = new JSONObject();
		StringBuffer sql=new StringBuffer("select CASE WHEN bp.bpid is null THEN ' ' else bp.bpid end" +
				",CASE WHEN bp.productID is null THEN ' ' else bp.productID end" +
				",CASE WHEN bp.barCode is null THEN ' ' else bp.barCode end" +
				",CASE WHEN bp.tradeCode is null THEN ' ' else bp.tradeCode end" +
				",CASE WHEN bp.productCode is null THEN ' ' else bp.productCode end" +
				",CASE WHEN bp.productName is null THEN ' ' else bp.productName end" +
				",CASE WHEN bp.price is null THEN ' ' else bp.price end" +
				",CASE WHEN bp.quantity is null THEN ' ' else bp.quantity end" +
				",CASE WHEN bp.money is null THEN ' ' else bp.money end" +
				",CASE WHEN bp.deviceNum is null THEN ' ' else bp.deviceNum end" +
				",CASE WHEN bp.maxBydevice is null THEN ' ' else bp.maxBydevice end" +
				",CASE WHEN bp.maxByhbyd is null THEN ' ' else bp.maxByhbyd end" +
				",CASE WHEN bp.worktimeByd is null THEN ' ' else bp.worktimeByd end" +
				",CASE WHEN bp.maxDay is null THEN ' ' else bp.maxDay end" +
				",CASE WHEN bp.maxWeek is null THEN ' ' else bp.maxWeek end" +
				",CASE WHEN bp.maxMonth is null THEN ' ' else bp.maxMonth end" +
				",CASE WHEN bp.maxSeason is null THEN ' ' else bp.maxSeason end" +
				",CASE WHEN bp.maxYear is null THEN ' ' else bp.maxYear end" +
				",to_char(bp.createDate,'yyyy-MM-hh')" +
				",CASE WHEN bp.year is null THEN ' ' else bp.year end" +
				",CASE WHEN bp.remark is null THEN ' ' else bp.remark end" +
				" from dtPBudgetPlan bp where bp.bpid=? and bp.companyid=? and bp.createDate is not null");
		Object obj=baseBeanService.getObjectBySqlAndParams(
				sql.toString(),new Object[]{bpid,cc.getCompany().getCompanyID()});
		temp.accumulate("obj",isNull(obj));
		result=temp;
		return Action.SUCCESS;
	} 
	/*
	 * 删除预算量
	 */
	public String delBudgetPlan(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		String bpid=request.getParameter("bpid");
		JSONObject temp = new JSONObject();
		try {
			baseBeanService.saveBeansListAndexecuteSqlsByParams(null,
					new String[]{"delete dtPBudgetPlan where bpid=? and companyID=?"},
					new Object[]{bpid,cc.getCompany().getCompanyID()});
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;		
		return Action.SUCCESS;
	}
	/**  -----------------------------------------------------------------------项目预算量设计结束-----------------------------------------------------------------------   */

	/**  -------------------------------------------------------------------------------模拟测试------------------------------------------------------------------------------   */

	/*
	 * 获取模拟测试列表
	 */
	public String getSimulationTestList(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		String status=request.getParameter("status");
		StringBuffer sql=new StringBuffer("select " +
				" CASE WHEN bt.bsimTestId  is null THEN ' ' else bt.bsimTestId end" +
				",CASE WHEN pp.tradeName  is null THEN ' ' else pp.tradeName end" +
				",CASE WHEN pp.goodsName is null THEN ' ' else pp.goodsName end" +
				",CASE WHEN pp.image is null THEN ' ' else pp.image end" +
				",CASE WHEN pp.quantity is null THEN ' ' else pp.quantity end" +
				",CASE WHEN pp.price is null THEN ' ' else pp.price end" +
				",CASE WHEN pp.money is null THEN ' ' else pp.money end" +
				" from production_BsimTest bt left join dt_ProductPackaging pp on bt.id=pp.ppid" +
				" where pp.companyId=? and bt.status=?");
		@SuppressWarnings("unchecked")
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(
				sql.toString(),new Object[]{cc.getCompany().getCompanyID(),status});
		if(list.size()>0)
			temp.accumulate("list",isNull(list));
		result=temp;		
		return Action.SUCCESS;
	}
	/*
	 * 模拟测试操作
	 */
	public String simulationTestOperation(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		String bsimTestId=request.getParameter("bsimTestId");
		String status=request.getParameter("status");
		String auditoroption=request.getParameter("auditoroption");
		try {
			BsimTest bt=(BsimTest)baseBeanService.getBeanByHqlAndParams(
					"from BsimTest where bsimTestId=?",new Object[]{bsimTestId});
			bt.setStatus(status);
			bt.setAuditoroption(auditoroption);
			bt.setAuditor(cc.getStaff().getStaffName());
			bt.setAuditorId(cc.getStaff().getStaffID());
			bt.setCompanyId(cc.getCompany().getCompanyID());
			bt.setCompanyName(cc.getCompany().getCompanyName());
			baseBeanService.update(bt);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;		
		return Action.SUCCESS;
	}
	/**  ----------------------------------------------------------------------------模拟测试结束---------------------------------------------------------------------------   */

	/**  -----------------------------------------------------------------------------设置生产量-----------------------------------------------------------------------------   */

	public String getAmountList(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		StringBuffer sql=new StringBuffer("select " +
				" CASE WHEN pa.productionAmountID  is null THEN ' ' else pa.productionAmountID end" +
				",CASE WHEN p.productCode  is null THEN ' ' else p.productCode end" +
				",CASE WHEN p.goodsName  is null THEN ' ' else p.goodsName end" +
				",CASE WHEN p.tradeName  is null THEN ' ' else p.tradeName end" +
				",CASE WHEN p.barCode  is null THEN ' ' else p.barCode end" +
				",CASE WHEN p.image  is null THEN ' ' else p.image end" +
				" from dtProductionAmount pa left join dt_ProductPackaging p on pa.productID=p.ppid" +
				" where pa.companyID=? and pa.status=?");
		@SuppressWarnings("unchecked")
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(
				sql.toString(),new Object[]{cc.getCompany().getCompanyID(),"01"});
		if(list.size()>0)
			temp.accumulate("list",isNull(list));
		result=temp;		
		return Action.SUCCESS;
	}
	/*
	 * 添加生产量
	 */
	public String addAmount() throws ParseException{
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		String amount=request.getParameter("amount");
		String ppID=request.getParameter("ppID");
		String paID=request.getParameter("paID");
		String category=request.getParameter("category");
		try {
			ProductionAmount pa=(ProductionAmount)baseBeanService.getBeanByHqlAndParams(
					"from ProductionAmount where productionAmountID=?",new Object[]{paID});
			ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
					"from ProductPackaging where ppID=?", new Object[]{ppID});
			if(pa==null){
				pa=new ProductionAmount();
				pa.setProductionAmountID(serverService.getServerID("ProductionAmount"));
				pa.setCompanyID(cc.getCompany().getCompanyID());
				pa.setCompanyName(cc.getCompany().getCompanyName());
				pa.setProductID(pp.getPpID());
				pa.setGoodsName(pp.getGoodsName());
				pa.setBatchNumber(getBatchNumber(cc.getCompany().getCompanyID()));
			}
			pa.setSetDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			pa.setStaffID(cc.getStaff().getStaffID());
			pa.setStaffName(cc.getStaff().getStaffName());
			pa.setSingleID(cc.getStaff().getStaffID());
			pa.setSingleName(cc.getStaff().getStaffName());
			pa.setSingleDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			pa.setAmount(amount);
			pa.setStatus("00");
			pa.setCategory(category);
			baseBeanService.saveOrUpdate(pa);
			
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;
		return Action.SUCCESS;
	}
	/*
	 * 生产量提交
	 */
	public String amountSubmit(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		List<BaseBean> list=new ArrayList<BaseBean>();
		JSONObject temp = new JSONObject();
		String paID=request.getParameter("paID");
		try {
			ProductionAmount pa=(ProductionAmount)baseBeanService.getBeanByHqlAndParams(
					"from ProductionAmount where productionAmountID=?",new Object[]{paID});
			ProductPackaging pp=(ProductPackaging)baseBeanService.getBeanByHqlAndParams(
					"from ProductPackaging where ppID=?", new Object[]{pa.getProductID()});
			pa.setStatus("01");
			
			GoodsBills goods=new GoodsBills();
			goods.setGoodsBillsID(serverService.getServerID("GoodsBills"));
			goods.setCompanyID(cc.getCompany().getCompanyID());
			
			ProductionDocuments pd=new ProductionDocuments();
			pd.setProDocumentsID(serverService.getServerID("ProductionDocuments"));
			
			goods.setCashierBillsID(pd.getProDocumentsID());
			
			pd.setCompanyID(cc.getCompany().getCompanyID());
			pd.setCompanyName(cc.getCompany().getCompanyName());
			pd.setCashierBillsID(pa.getProductionAmountID());
			pd.setGoodsBillsID(goods.getGoodsBillsID());
			pd.setProDate(new SimpleDateFormat("yyyy-MM-dd : ").format(new Date()));
			pd.setBatchNumber(serverService.getBillID(cc.getCompany().getCompanyID()));
			pd.setStaffID(pa.getStaffID());
			pd.setStaffName(pa.getStaffName());
			pd.setInputID(cc.getStaff().getStaffID());
			pd.setInputName(cc.getStaff().getStaffName());
			pd.setStatus("00");pd.setType("01");
			pd.setSuperposition("01");
			
			goods.setPpID(pp.getPpID());
			goods.setGoodsID(pp.getGoodsID());
			goods.setTypeID(pp.getType());
			goods.setGoodsNum(pp.getGoodsNum());
			goods.setGoodsName(pp.getGoodsName());
			goods.setPrice(pp.getPrice());
			goods.setQuantity(pa.getAmount());
			goods.setMoney(Integer.parseInt(pp.getPrice())*Integer.parseInt(pa.getAmount())+"");
			goods.setStatus("28");
			
			List<BaseBean> ppList=baseBeanService.getListBeanByHqlAndParams(
					"from ProductPackaging where parentId=? and companyID=?", 
					new Object[]{goods.getPpID(),cc.getCompany().getCompanyID()});
			if(ppList.size()==0)
				pd.setCategory("00");
			else
				pd.setCategory("01");
			list.add(pa);list.add(pd);list.add(goods);
			track(list,pa,goods);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(list, null, null);
			
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;
		return Action.SUCCESS;
	}
	/*
	 * 删除生产量
	 */
	public String delAmount(){
		JSONObject temp = new JSONObject();
		String paID=request.getParameter("paID");
		try {
		String hql="from ProductionAmount where productionAmountID=?";
		ProductionAmount pa=(ProductionAmount)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{
				paID});
		if(pa!=null)
			baseBeanService.deleteBeanByKey(ProductionAmount.class, pa.getProductionAmountKey());
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;
		return Action.SUCCESS;
	}
	
	/*
	 * 获取生产量信息
	 */
	public String getAmount(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		String paID=request.getParameter("paID");
		
		StringBuffer sql=new StringBuffer("select " +
				" CASE WHEN pa.productionAmountID  is null THEN ' ' else pa.productionAmountID end" +
				",CASE WHEN pa.productID  is null THEN ' ' else pa.productID end" +
				",CASE WHEN pa.setDate  is null THEN ' ' else pa.setDate end" +
				",CASE WHEN pa.staffName  is null THEN ' ' else pa.staffName end" +
				" from dtProductionAmount pa" +
				" where pa.companyID=? and pa.status=? and pa.productionAmountID=?");
		Object obj=baseBeanService.getObjectBySqlAndParams(
				sql.toString(),new Object[]{cc.getCompany().getCompanyID(),"01",paID});
		temp.accumulate("obj",isNull(obj));
		result=temp;
		return Action.SUCCESS;
	}
	/**  --------------------------------------------------------------------------设置生产量结束--------------------------------------------------------------------------   */

	/**  ----------------------------------------------------------------------------采购预算申请---------------------------------------------------------------------------   */
	/*
	 * 获取采购预算列表信息
	 */
	public String getBudgetList(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
 
		StringBuffer sql=new StringBuffer("select CASE WHEN p.image  is null THEN ' ' else p.image end" +
				",CASE WHEN c.companyname  is null THEN ' ' else c.companyname end" +
				",CASE WHEN c.cashierbillsID  is null THEN ' ' else c.cashierbillsID end" +
				",CASE WHEN p.goodsname  is null THEN ' ' else p.goodsname end" +
				",CASE WHEN c.journalnum  is null THEN ' ' else c.journalnum end" +
				",CASE WHEN g.quantity  is null THEN ' ' else g.quantity end" +
				" from dtcashierbills c left join dtgoodsbills g on c.cashierbillsid=g.cashierbillsid" +
				" left join dt_productpackaging p on g.ppid=p.ppid  where c.companyid=? and c.billstype=?");
		@SuppressWarnings("unchecked")
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(
				sql.toString(),new Object[]{cc.getCompany().getCompanyID(),"采购申请单"});
		if(list.size()>0)
			temp.accumulate("list",list);
		result=temp;		
		return Action.SUCCESS;
	}
	
	/*
	 * 获取采购预算信息
	 */
	public String getBudget(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		String cashierBillsID=request.getParameter("cashierBillsID");
		StringBuffer sql=new StringBuffer("select " +
				" CASE WHEN c.cashierbillsID  is null THEN ' ' else c.cashierbillsID end" +
				",CASE WHEN c.companyname  is null THEN ' ' else c.companyname end" +
				",CASE WHEN org.organizationName  is null THEN ' ' else org.organizationName end" +
				",CASE WHEN c.staffName  is null THEN ' ' else c.staffName end" +
				",CASE WHEN g.goodsNum  is null THEN ' ' else p.goodsNum end" +
				",CASE WHEN p.goodsname  is null THEN ' ' else p.goodsname end" +
				",CASE WHEN c.journalnum  is null THEN ' ' else c.journalnum end" +
				",CASE WHEN c.inputName  is null THEN ' ' else c.inputName end" +
				",CASE WHEN to_char(c.cashierDate,'yyyy-MM-hh')  is null THEN ' '" +
				" else  to_char(c.cashierDate,'yyyy-MM-hh') end" +
				",CASE WHEN g.quantity  is null THEN ' ' else g.quantity end" +
				",CASE WHEN p.ppID  is null THEN ' ' else p.ppID end" +
				" from dtcashierbills c left join dtgoodsbills g on c.cashierbillsid=g.cashierbillsid" +
				" left join dt_productpackaging p on g.ppid=p.ppid " +
				" left join dtcorganization org on c.organizationID=org.organizationID" +
				" where c.companyid=? and c.cashierbillsID=? and c.billstype=?");
		Object obj=baseBeanService.getObjectBySqlAndParams(
				sql.toString(),new Object[]{cc.getCompany().getCompanyID(),cashierBillsID,"采购申请单"});
		if(obj!=null){
			temp.accumulate("obj",isNull(obj));
			StringBuffer sql2=new StringBuffer("select CASE WHEN pp.ppid is null THEN ' ' else pp.ppid end" +
					",CASE WHEN pp.productCode is null THEN ' ' else pp.productCode end" +
					",CASE WHEN pp.goodsName is null THEN ' ' else pp.goodsName end" +
					",CASE WHEN pp.model is null THEN ' ' else pp.model end" +
					",CASE WHEN pp.variableID is null THEN ' ' else pp.variableID end" +
					",CASE WHEN pp.price is null THEN ' ' else pp.price end" +
					"  from (select * from dt_ProductPackaging p connect by p.parentId= prior p.ppID" +
					"  start with p.ppID=?) pp  where pp.hierarchical!=?");
			@SuppressWarnings("unchecked")
			List<Object> list=baseBeanService.getListBeanBySqlAndParams(
					sql2.toString(),new Object[]{((Object[])obj)[10],"0"});
			if(list.size()>0)  
				temp.accumulate("list",isNull(list));
		}
		result=temp;		
		return Action.SUCCESS;
	}
	
	/*
	 * 添加采购预算信息
	 */
	public String addBudget(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		List<BaseBean> list=new ArrayList<BaseBean>();
		StringBuffer sql=new StringBuffer();
		List<Object> obj=new ArrayList<Object>();
		JSONObject temp = new JSONObject();
		String cashierBillsID=request.getParameter("cashierBillsID");
		String goodsBillsID=request.getParameter("goodsBillsID");
		String type=request.getParameter("type");
		String staffID=request.getParameter("staffID");
		String staffName=request.getParameter("staffName");
		try {
			@SuppressWarnings("unchecked")
			List<String[]> goodsList=(List<String[]>) request.getAttribute("goodsList");
			CashierBills c=(CashierBills) baseBeanService.getBeanByHqlAndParams(
					"from CashierBills where cashierBillsID=?",new Object[]{cashierBillsID});
			if(c==null){
				c=new CashierBills();
				c.setCashierBillsID(serverService.getServerID("CashierBills"));
				c.setCompanyID(cc.getCompany().getCompanyID());
				c.setCcompanyName(cc.getCompany().getCompanyName());
				c.setCashierDate(new Date());
				c.setBillsType("采购申请单");
				c.setJournalNum(cc.getCompany().getCompanyID());
				c.setStatus("00");
				c.setType(type);
				c.setFiveClear("4");
				c.setStatusbill("05");
			}
			c.setStaffID(staffID);
			c.setStaffName(staffName);
			c.setStaffCode(cc.getStaff().getStaffCode());
			c.setInputid(cc.getStaff().getStaffID());
			c.setInputName(cc.getStaff().getStaffName());
			list.add(c);
			if(goodsList!=null){
				for(int i=0;i<goodsList.size();i++){
					String[] strs=goodsList.get(i);
					GoodsManage  goodsManage  =  (GoodsManage) baseBeanService.getBeanByHqlAndParams(
				    		"from GoodsManage where goodsID = ?",new Object[]{strs[0]});
					GoodsBills goods=(GoodsBills) baseBeanService.getBeanByHqlAndParams(
							"from GoodsBills where goodsBillsID=?",new Object[]{strs[1]});
					if(goods==null){
						goods=new GoodsBills();
						goods.setGoodsBillsID(serverService.getServerID("GoodsBills"));
						goods.setCashierBillsID(c.getCashierBillsID());
						goods.setCompanyID(cc.getCompany().getCompanyID());
						goods.setGoodsID(goodsManage.getGoodsID());
						goods.setTypeID(goodsManage.getTypeID());
						goods.setGoodsNum(goodsManage.getGoodsCoding());
						goods.setGoodsName(goodsManage.getGoodsName());
						goods.setStandard(goodsManage.getStandard());
					}
					goods.setPrice(strs[2]);
					goods.setQuantity(strs[3]);
					goods.setMoney(strs[4]);
					list.add(goods);
				}
			}
			
			if(goodsBillsID!=null&&!"".equals(goodsBillsID)){
				String[] strs=goodsBillsID.split(",");
				sql.append("delete dtgoodsbills where goodsbillsID in (");
				for(int i=0;i<strs.length;i++){
					if(i==0)
						sql.append("?");
					else
						sql.append(",?");
					obj.add(strs[i]);
				}
				sql.append(")");
			}
			if(goodsBillsID!=null&&!"".equals(goodsBillsID))
				baseBeanService.saveBeansListAndexecuteSqlsByParams(list, new String[]{sql.toString()},obj.toArray());
			else
				baseBeanService.saveBeansListAndexecuteSqlsByParams(list, null,null);
			temp.accumulate("result", "0");
		} catch (Exception e) {
			temp.accumulate("result", "1");
		}
		result=temp;
		return Action.SUCCESS;
	}
	/*
	 * 删除采购预算信息
	 */
	public String delBudget(){
		JSONObject temp = new JSONObject();
		String cashierBillsID=request.getParameter("cashierBillsID");
		List<String> sql=new ArrayList<String>();
		List<Object> obj=new ArrayList<Object>();
	try {
		sql.add("delete dtcashierbills where cashierbillsID=?");
		sql.add("delete dtgoodsBills where cashierbillsID=?");
		obj.add(cashierBillsID);
		baseBeanService.saveBeansListAndexecuteSqlsByParams(null, sql.toArray(new String[]{}),obj.toArray());
		temp.accumulate("result", "0");
	} catch (Exception e) {
		temp.accumulate("result", "1");
	}
	result=temp;
		return Action.SUCCESS;
	}
	/**  ------------------------------------------------------------------------采购预算申请结束-----------------------------------------------------------------------   */

	private Object isNull(Object tep){
		if(tep==null||"null".equals(tep)){
			return "";
		}else{
			return tep;
		}
	}
	
	/**
	 * 
	 * 保存文本编辑器内容
	 * @param content
	 * @return
	 */
	private String saveContentToFile(String content) {
		String id = RandomDatas.getUUID();
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
	
	/*
	 * 生成生产跟踪信息
	 */
private void track(List<BaseBean> list,ProductionAmount pa,GoodsBills goods) throws ParseException{
		
		List<Object> obj=new ArrayList<Object>();
		String sql="select p.ppid,p.hierarchical  from dt_ProductPackaging p connect by p.parentId = prior p.ppid" 
					+"  start with p.ppID = ? and p.goodsname=?";
			obj.add(goods.getPpID());
			obj.add(goods.getGoodsName());
		@SuppressWarnings("unchecked")
		List<Object> list2=baseBeanService.getListBeanBySqlAndParams(sql, obj.toArray());
		int ir=0;
		for(int i=0;i<list2.size();i++){
			Object[] o=(Object[]) list2.get(i);
			if(i!=list2.size()-1){
				Object[] o2=(Object[]) list2.get(i+1);
				if(Integer.parseInt(o[1].toString())>=Integer.parseInt(o2[1].toString())){
					ir++;
				}
			}else{
				ir++;
			}
		}
		
		StaffTrack st=new StaffTrack();
		st.setStId(serverService.getServerID("StaffTrack"));
		st.setCashierBillsID(pa.getProductionAmountID());				//订单ID
		st.setEnrollDate(new SimpleDateFormat("yyyy-MM-dd").parse(pa.getSetDate()));						//下单时间
		st.setPpID(goods.getPpID());												//产品ID
		st.setPpName(goods.getGoodsName());						//产品名称	
		st.setStartDate(new Date());												//生产时间
		st.setResponID(pa.getStaffID());								//责任人
		st.setResponName(pa.getStaffName());				//责任人
		st.setPronum(ir+"");
		st.setStatus("00");
		list.add(st);
		
	}
	/*
	 * 获取生产批次号
	 */
	public String getBatchNumber(String companyID){
		String hql="from Company where companyID=?";
		Company cc=(Company)baseBeanService.getBeanByHqlAndParams(hql, new Object[]{companyID});
		String sql="select count(*) from dtProductionAmount where companyID=? and batchNumber like ?";
		int batchNumber=baseBeanService.getConutByBySqlAndParams(
					sql, new Object[]{companyID,"%"+cc.getCompanyIdentifier().toUpperCase()
							+new SimpleDateFormat("yyyyMMdd").format(new Date())+"%"});
		String str=cc.getCompanyIdentifier().toUpperCase()
				+new SimpleDateFormat("yyyyMMdd").format(new Date())+(100000+batchNumber);
		return str;
	}
	/*
	 * 获取行业分类
	 */
	public String getIndustryClassification(){
		CAccount cc=(CAccount)baseBeanService.getBeanByHqlAndParams(
				"select new CAccount(c,s) from CAccount a,Company c,Staff s  where a.companyID=c.companyID" +
				" and a.staffID=s.staffID and  a.accountID=?",new Object[]{accountID});
		JSONObject temp = new JSONObject();
		String codeID=request.getParameter("codeID");
		if(codeID==null||codeID.equals("")){
			if("00".equals(request.getParameter("type")))
				codeID="scode20150815wygb79q82p0000000005";
			else
				codeID="scode20101014v5zed7cukk0000000002";
		} 
		
		StringBuffer sql=new StringBuffer("select" +
				" new map(codeID as codeID,codeValue as codeValue)" +
				" from CCode where  codePID = ? and companyID = ? and" +
				" ( codeStatus = '00' or codeStatus = '01')  order by codeNumber ");
		List<BaseBean> list = baseBeanService.getListBeanByHqlAndParams(
				sql.toString(),new Object[]{codeID,cc.getCompany().getCompanyID()});
		if(list.size()>0)
			temp.accumulate("list",isNull(list));
		result=temp;
		return Action.SUCCESS;
	} 
	/*
	 * 获取产品结构
	 */
	@SuppressWarnings("unchecked")
	public String productMix(){
		JSONObject temp = new JSONObject();
		String ppID=request.getParameter("ppID");
		String sql="select CASE WHEN p.ppid  is null THEN ' ' else p.ppid end" +
				",CASE WHEN p.parentId  is null THEN ' ' else p.parentId end" +
				",CASE WHEN p.goodsname  is null THEN ' ' else p.goodsname end" +
				",CASE WHEN p.quantity  is null THEN ' ' else p.quantity end" +
				"  from dt_ProductPackaging p connect by parentId= prior ppid start with ppID=?";
		List<Object> list=baseBeanService.getListBeanBySqlAndParams(sql, new Object[]{ppID});
		temp.accumulate("list",isNull(list));
		result=temp;
		return Action.SUCCESS;
		}

	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getFiveClear() {
		return fiveClear;
	}
	public void setFiveClear(String fiveClear) {
		this.fiveClear = fiveClear;
	}
}
