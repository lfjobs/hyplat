package mobile.tiantai.android.action;

import hy.base.action.BaseAction;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.production.GoodFunction;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.RandomDatas;
import hy.ea.util.ToChineseFirstLetter;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.Action;
import com.tiantai.wfj.bo.TEshopCusCom;

/**
 * 移动端新闻接口api
 * @author zg
 *
 */
@Controller
@Scope("prototype")
public class MobileNewsAction extends BaseAction<Object> {
	private static final long serialVersionUID = 8675011188323330876L;
	@Resource
	private UploadContentToFileService contentToFileService;
	/**
	 * 上传新闻
	 * account登录帐号
	 * newtitle标题
	 * newtime时间
	 * newpicpath图片串,分隔符#
	 * newcontent内容
	 * @return 不返回正常,返回有内容均是错误信息.
	 */
	public String saveNewByNorm(){
		List<BaseBean> lst=new ArrayList<BaseBean>();
		String account=request.getParameter("account");//手机登录的帐号
		String title=request.getParameter("newtitle");
		String time=request.getParameter("newtime");
		String path=request.getParameter("newpicpath");
		String content=request.getParameter("newcontent");
		String[] picarr=path.split("#");//图片路径
		
		String hql ="select new TEshopCusCom(usr.account,cc.industryType,c.groupCompanySn,usr.companyId) from TEshopCusCom usr ,CcomCom rcc,ContactCompany cc,Company c";
		hql+=" where rcc.comanyId=usr.companyId and c.companyID=usr.companyId and cc.ccompanyID=rcc.ccompanyId and usr.account=?";
		TEshopCusCom cuser=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account});
		if(cuser==null){
			result="不是公司的账户";
			return Action.SUCCESS;
		}
		hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
		Object[] params = { cuser.getGroupsn(),cuser.getIndustryType() };
		int goodscodingnum = baseBeanService.getConutByByHqlAndParams(hql,params);
		//物品
		//公司,物品名称,物品编号,类别(新闻),行业(新闻找),主图中存一个图片.物品不为空
		GoodsManage gm=new GoodsManage();
		gm.setGoodsID(serverService.getServerID("GoodsManage"));
		gm.setCompanyID(cuser.getCompanyId());
		gm.setGoodsName(title);
		gm.setTypeID("普通新闻");
		gm.setFiveClear("2");//办公室
		gm.setGoodsState("00");//正常状态
		gm.setTradeCode(cuser.getIndustryType());//行业
		String pinyin = ToChineseFirstLetter.getFirstLetter(cuser.getIndustryType());
		String Upstr = pinyin.toUpperCase();// 转换为大写
		DecimalFormat form = new DecimalFormat("000000");
		String ss = form.format(goodscodingnum + 1);
		gm.setGoodsCoding(Upstr + "_" + ss);//编码
		gm.setPhotoPath(picarr==null?"":picarr[0]);
		
		lst.add(gm);
		//关联表 goodfunction  物品 文件
		StringBuffer txtcontent=new StringBuffer();
		txtcontent.append("<p>");
		for (String tep : picarr) {
			txtcontent.append("<img src='");
			txtcontent.append(tep);
			txtcontent.append("'/><br/>");
		}
		txtcontent.append("</p>");
		txtcontent.append("<p>");
		txtcontent.append(content);
		txtcontent.append("</p>");
		String txtpath=saveContentToFile(txtcontent.toString());
		GoodFunction gf=new GoodFunction();
		gf.setGfid(serverService.getServerID("GoodFunction"));
		gf.setGoodsid(gm.getGoodsID());
		gf.setUrl(txtpath);
		gf.setName(title);
		gf.setOrders(1);
		gf.setType("1");
		lst.add(gf);
		//产品表
		//物品id,公司,五方面那个(固定2),编号(带过来),单价1,金额1,状态00,物品类别,主图中存一个图片.
		ProductPackaging pp=new ProductPackaging();
		pp.setPpID(serverService.getServerID("ProductPackaging"));
		pp.setCompanyID(cuser.getCompanyId());
		pp.setFiveClear("2");
		pp.setGoodsNum(gm.getGoodsCoding());
		pp.setPrice("1");
		pp.setQuantity("1");
		pp.setMoney("1");
		pp.setDelStatus("00");
		pp.setImage(picarr==null?"":picarr[0]);
		pp.setType("普通新闻");
		if(time!=null&&time.length()==10){
			pp.setPackagingDate(Utilities.getDateFromString(time,"yyyy-MM-dd"));
		}else if(time!=null&&time.length()==19){
			pp.setPackagingDate(Utilities.getDateFromString(time,"yyyy-MM-dd HH:mm:ss"));
		}
		pp.setGoodsID(gm.getGoodsID());
		pp.setGoodsNum(gm.getGoodsCoding());
		pp.setGoodsName(gm.getGoodsName());
		pp.setModel("新闻动态");
		lst.add(pp);
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(lst, null, null);
		} catch (Exception e) {
			result="保存失败";
		}
		return Action.SUCCESS;
	}
	/**
	 * 上传转载新闻
	 * account手机登录帐号,必须公司的帐号
	 * newtitle标题
	 * newtime时间
	 * newaddress转载的网址
	 * @return 不返回正常,返回有内容均是错误信息.
	 */
	public String saveNewByOther(){
		List<BaseBean> lst=new ArrayList<BaseBean>();
		String account=request.getParameter("account");//手机登录的帐号
		String title=request.getParameter("newtitle");
		String time=request.getParameter("newtime");
		String content=request.getParameter("newaddress");
		
		String hql ="select new TEshopCusCom(usr.account,cc.industryType,c.groupCompanySn,usr.companyId) from TEshopCusCom usr ,CcomCom rcc,ContactCompany cc,Company c";
		hql+=" where rcc.comanyId=usr.companyId and c.companyID=usr.companyId and cc.ccompanyID=rcc.ccompanyId and usr.account=?";
		TEshopCusCom cuser=(TEshopCusCom) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{account});
		if(cuser==null){
			result="不是公司的账户";
			return Action.SUCCESS;
		}
		hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.companyID in (select c.companyID from Company c where c.groupCompanySn=?)  and vt.typeID=? ";
		Object[] params = { cuser.getGroupsn(),cuser.getIndustryType() };
		int goodscodingnum = baseBeanService.getConutByByHqlAndParams(hql,params);
		//物品
		//公司,物品名称,物品编号,类别(新闻),行业(新闻找),主图中存一个图片.物品不为空
		GoodsManage gm=new GoodsManage();
		gm.setGoodsID(serverService.getServerID("GoodsManage"));
		gm.setCompanyID(cuser.getCompanyId());
		gm.setGoodsName(title);
		gm.setTypeID("转载新闻");
		gm.setFiveClear("2");//办公室
		gm.setGoodsState("00");//正常状态
		if(time!=null&&time.length()==10){
			gm.setCreatedate(Utilities.getDateFromString(time,"yyyy-MM-dd"));
		}else if(time!=null&&time.length()==19){
			gm.setCreatedate(Utilities.getDateFromString(time,"yyyy-MM-dd HH:mm:ss"));
		}
		gm.setTradeCode(cuser.getIndustryType());//行业
		String pinyin = ToChineseFirstLetter.getFirstLetter(cuser.getIndustryType());
		String Upstr = pinyin.toUpperCase();// 转换为大写
		DecimalFormat form = new DecimalFormat("000000");
		String ss = form.format(goodscodingnum + 1);
		gm.setGoodsCoding(Upstr + "_" + ss);//编码
		gm.setPhotoPath(null);//转载拿不出来图片,赋值null
		
		lst.add(gm);
		//关联表 goodfunction  物品 文件
		StringBuffer txtcontent=new StringBuffer();
		txtcontent.append(content);
		String txtpath=saveContentToFile(txtcontent.toString());
		GoodFunction gf=new GoodFunction();
		gf.setGfid(serverService.getServerID("GoodFunction"));
		gf.setGoodsid(gm.getGoodsID());
		gf.setUrl(txtpath);
		gf.setName(title);
		gf.setOrders(1);
		gf.setType("1");
		lst.add(gf);
		//产品表
		//物品id,公司,五方面那个(固定2),编号(带过来),单价1,金额1,状态00,物品类别,主图中存一个图片.
		ProductPackaging pp=new ProductPackaging();
		pp.setPpID(serverService.getServerID("ProductPackaging"));
		pp.setCompanyID(cuser.getCompanyId());
		pp.setFiveClear("2");
		pp.setGoodsNum(gm.getGoodsCoding());
		pp.setPrice("1");
		pp.setQuantity("1");
		pp.setMoney("1");
		pp.setDelStatus("00");
		pp.setImage(null);////转载拿不出来图片,赋值null
		pp.setType("转载新闻");
		if(time!=null&&time.length()==10){
			pp.setPackagingDate(Utilities.getDateFromString(time,"yyyy-MM-dd"));
		}else if(time!=null&&time.length()==19){
			pp.setPackagingDate(Utilities.getDateFromString(time,"yyyy-MM-dd HH:mm:ss"));
		}
		pp.setGoodsID(gm.getGoodsID());
		pp.setGoodsNum(gm.getGoodsCoding());
		pp.setGoodsName(gm.getGoodsName());
		pp.setModel("转载新闻");
		lst.add(pp);
		try {
			baseBeanService.saveBeansListAndexecuteHqlsByParams(lst, null, null);
		} catch (Exception e) {
			result="保存失败";
		}
		return Action.SUCCESS;
	}
	/**
	 * 修改新闻updateNewsBy
	 * ppID产品id
	 * goodsID物品id
	 * newtitle标题
	 * newtime时间
	 * newpicpath图片路径,#隔开
	 * newcontent内容
	 * newsType 0:普通新闻 1:转载新闻
	 * @return 不返回正常,返回有内容均是错误信息.
	 */
	public String updateNewsBy(){
		///parms
		String ppID=request.getParameter("ppID");
		String goodsID=request.getParameter("goodsID");
		String title=request.getParameter("newtitle");
		String time=request.getParameter("newtime");
		String path=request.getParameter("newpicpath");
		String content=request.getParameter("newcontent");
		String newsType=request.getParameter("newsType");//不传或者 0:普通新闻 1:转载新闻
		
		String[] picarr=path.split("#");//图片路径
		///start
		if(ppID==null||goodsID==null){
			result="参数无效";
			return Action.SUCCESS;
		}
		if(title!=null||time!=null||(path!=null&&content!=null)){
			List<String> hqls=new ArrayList<String>();
			StringBuffer hql1=new StringBuffer("update ProductPackaging u");
			StringBuffer hql2=new StringBuffer("update GoodsManage u");
			StringBuffer hql3=null;
			List<Object[]> parms=new ArrayList<Object[]>();
			List<Object> parm1=new ArrayList<Object>();
			List<Object> parm2=new ArrayList<Object>();
			List<Object> parm3=new ArrayList<Object>();
			hql1.append(" set");
			hql2.append(" set");
			if(title!=null&&title.length()>0){
				hql1.append(" u.goodsName=?,");
				hql2.append(" u.goodsName=?,");
				parm1.add(title);
				parm2.add(title);
			}
			if(time!=null&&time.length()>1){
				hql1.append(" u.packagingDate=?,");
				hql2.append(" u.createdate=?,");
				if(time!=null&&time.length()==19){
					parm1.add(Utilities.getDateFromString(time,"yyyy-MM-dd HH:mm:ss"));
					parm2.add(Utilities.getDateFromString(time,"yyyy-MM-dd HH:mm:ss"));
				}else{
					parm1.add(Utilities.getDateFromString(time,"yyyy-MM-dd"));
					parm2.add(Utilities.getDateFromString(time,"yyyy-MM-dd"));
				}
			}
			if(newsType.equals("0")&&picarr!=null&&picarr.length>0&&content!=null&&content.length()>1){
				StringBuffer txtcontent=new StringBuffer();
				txtcontent.append("<p>");
				for (String tep : picarr) {
					txtcontent.append("<img src='");
					txtcontent.append(tep);
					txtcontent.append("'/><br/>");
				}
				txtcontent.append("</p>");
				txtcontent.append("<p>");
				txtcontent.append(content);
				txtcontent.append("</p>");
				String txtpath=saveContentToFile(txtcontent.toString());
				hql3=new StringBuffer();
				hql1.append(" u.image = ?,");
				hql2.append(" u.photoPath=?,");
				hql3.append(" update GoodFunction gf set gf.url=? where gf.goodsid=?");
				parm1.add(picarr[0]);
				parm2.add(picarr[0]);
				parm3.add(txtpath);
				parm3.add(goodsID);
			}else if (content!=null&&content.length()>1){
				StringBuffer txtcontent=new StringBuffer();
				txtcontent.append("<p>");
				txtcontent.append(content);
				txtcontent.append("</p>");
				String txtpath=saveContentToFile(txtcontent.toString());
				hql3=new StringBuffer();
				hql1.append(" u.image = ?,");
				hql2.append(" u.photoPath=?,");
				hql3.append(" update GoodFunction gf set gf.url=? where gf.goodsid=?");
				parm1.add(picarr[0]);
				parm2.add(picarr[0]);
				parm3.add(txtpath);
				parm3.add(goodsID);
			}
			hql1.replace(0, hql1.length(), hql1.substring(0, hql1.length()-1));
			hql2.replace(0, hql2.length(), hql2.substring(0, hql2.length()-1));
			hql1.append(" where u.ppID=?");
			hql2.append(" where u.goodsID=?");
			parm1.add(ppID);
			parm2.add(goodsID);
			hqls.add(hql1.toString());
			hqls.add(hql2.toString());
			parms.add(parm1.toArray());
			parms.add(parm2.toArray());
			
			if(hql3.length()>0&&parm3.size()>1){
				hqls.add(hql3.toString());
				parms.add(parm3.toArray());
			}
			try {
				baseBeanService.executeHqlsByParamsList(null, hqls.toArray(new String[]{}), parms);
			} catch (Exception e) {
				result="修改失败";
			}
		}
		return Action.SUCCESS;
	}
	/**
	 * 查看新闻详细
	 * ppID产品id
	 * goodsID物品id
	 * @return 返回一条新闻标题,时间,内容,此条新闻地址
	 */
	public String viewNewBy(){
		String ppID=request.getParameter("ppID");
		String goodsID=request.getParameter("goodsID");
		String account=request.getParameter("account");
		String hql="select new ProductPackaging(m.packagingDate,m.goodsName,gf.url)  from ProductPackaging m ,GoodFunction gf where m.goodsID=gf.goodsid and m.goodsID=? and m.ppID=?";
		if(ppID!=null&&goodsID!=null){
			List<Object> parm=new ArrayList<Object>();
			parm.add(goodsID);
			parm.add(ppID);
			ProductPackaging pp=(ProductPackaging) baseBeanService.getBeanByHqlAndParams(hql, parm.toArray());
			JSONObject jo=new JSONObject();
			//网页版新闻地址
			String ccompanyId="";
			StringBuffer sql=new StringBuffer();
			sql.append("select ccom.ccompany_id from dt_ccom_com ccom,t_eshop_cuscom tec where tec.companyid=ccom.compnay_id and tec.account=?");
			List<Object> list= baseBeanService.getListBeanBySqlAndParams(sql.toString(), new Object[]{account});
			if(list!=null&&list.size()>0){
				Object obj=list.get(0);
				Object [] objs=(Object[]) obj;
				ccompanyId=(String) objs[0];
			}							
			if(pp!=null){
				jo.accumulate("newtitle", isNull(pp.getGoodsName()));
				jo.accumulate("newtime", isNull(pp.getShowdate()));
				jo.accumulate("txtpath", isNull(pp.getNewcontent()));
				jo.accumulate("web","ea/wfjshop/ea_getWFJnews.jspa?goodsid="+goodsID+"&ccompanyId="+ccompanyId);
			}
			result=jo;
		}
		return Action.SUCCESS;
	}
	/**
	 * 查询公司新闻列表
	 * account手机登录的帐号
	 * pageNumber页数(第几页)
	 * newsType不传或者 0:普通新闻 1:转载新闻
	 * @return 返回15条新闻 
	 */
	public String findNewsBy(){
		String account=request.getParameter("account");//手机登录的帐号
		String newsType=request.getParameter("newsType");//不传或者 0:普通新闻 1:转载新闻
		String tempType="普通新闻";
		if(newsType!=null&&newsType.equals("1")){
			tempType="转载新闻";
		}
		if(pageNumber==0){
			pageNumber=1;
		}
		String hql ="select new ProductPackaging(m.ppID,m.goodsID,m.packagingDate,m.goodsName,m.image) from ProductPackaging m,TEshopCusCom usr where usr.companyId=m.companyID and usr.account=? and m.type=? order by m.packagingDate desc";
		String hqlc="select count(m.ppID) from ProductPackaging m,TEshopCusCom usr where usr.companyId=m.companyID and usr.account=? and m.type=?";
		pageForm=baseBeanService.getPageForm(
				pageNumber,
				15,
				hql,hqlc, new Object[]{account,tempType});
		JSONObject jo=new JSONObject();
		List<JSONObject> temlist=new ArrayList<JSONObject>();
		JSONObject temp=new JSONObject();
		if(pageForm!=null){
			for (BaseBean bean : pageForm.getList()) {
				temp=new JSONObject();
				ProductPackaging pp=(ProductPackaging) bean;
				temp.accumulate("ppID", pp.getPpID());
				temp.accumulate("goodsID", pp.getGoodsID());
				temp.accumulate("newtime", isNull(pp.getShowdate()));
				temp.accumulate("newtitle", isNull(pp.getGoodsName()));
				if(pp.getImage()!=null&&pp.getImage().length()>1){
					temp.accumulate("newpicpath", pp.getImage());
				}else{
					temp.accumulate("newpicpath", "");
				}
				temlist.add(temp);
			}
			jo.accumulate("pp", temlist);
			jo.accumulate("pageNumber", pageForm.getPageNumber());
			jo.accumulate("pageCount", pageForm.getPageCount());
		}
		result=jo;
		return Action.SUCCESS;
	}
	/**
	 * 删除新闻
	 * ppID产品Id
	 * goodsID物品Id
	 * @return 返回有结果为:删除失败,不返回结果为:成功
	 */
	public String delNewsBy(){
		try {
			String ppID=request.getParameter("ppID");
			String goodsID=request.getParameter("goodsID");
			String filpath=ServletActionContext.getServletContext().getRealPath("/");
			String hql="from GoodsManage gm where gm.goodsID=?";
			GoodsManage gm=(GoodsManage) baseBeanService.getBeanByHqlAndParams(hql, new Object[]{goodsID});
			if(gm!=null){
				FileUtil.delete(filpath+gm.getPhotoPath());
			}
			List<String> hqls=new ArrayList<String>();
			List<Object[]> parms=new ArrayList<Object[]>();
			hqls.add("delete from GoodsManage gm where gm.goodsID=?");
			parms.add(new Object[]{goodsID});
			hqls.add("delete from ProductPackaging pp where pp.ppID=?");
			parms.add(new Object[]{ppID});
			baseBeanService.executeHqlsByParamsList(null, hqls.toArray(new String[]{}), parms);
		} catch (Exception e) {
			result="删除失败";
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 
	 * 保存文本编辑器内容
	 * @param content
	 * @return
	 */
	private String saveContentToFile(String content) {
		String id = RandomDatas.getUUID();
		String path = ServletActionContext.getServletContext().getRealPath("/")
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
