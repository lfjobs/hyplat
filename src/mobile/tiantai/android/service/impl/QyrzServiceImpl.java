package mobile.tiantai.android.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.util.HttpClient;
import hy.ea.bo.production.GoodFunction;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.Constant;
import hy.ea.util.Utilities;
import hy.ea.util.bean.POI;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;
import mobile.tiantai.android.service.QyrzService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QyrzServiceImpl implements QyrzService {

	@Resource
	private BaseBeanDao baseBeanDao;
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private UploadContentToFileService contentToFileService;
	/**
	 * 查询公司加入
	 */
	public List<BaseBean> getpk() {

		StringBuilder sql = new StringBuilder();
		sql.append(
				"select p.goodsname,ps.ppid,ps.re_price,p.image,p.model,p.goodsid,p.companyid,p.remark,m.companyname,cc.ccompany_Id from DT_PRO_SETUP ps,dt_ProductPackaging p,dtCompany m,DT_ccom_com cc where cc.compnay_id = m.companyid and  ps.com_id= ? and m.companyid = p.companyid ");
		sql.append(" and p.ppid=ps.ppid and p.showweixin ='01' and p.type= ? and  ps.fcom_id is null and model != ?  order by sorting ");


		// 查询公司会员列表
		List<BaseBean> productList = baseBeanDao.getListBeanBySqlAndParams(sql.toString(),
				new String[]{"company201009046vxdyzy4wg0000000025", "公司会员", "0"});
		return productList;
	}


	/**
	 *
	 * 获取产品图文详情
	 * @return
	 */
	public String getProDetail(String goodsid,String realpath){

		String hql = "from GoodFunction where goodsid = ?";
		GoodFunction gf = (GoodFunction)baseBeanDao.getBeanByHqlAndParams(hql,new Object[]{goodsid});
		String url = "";
		if(gf!=null){
			url = gf.getUrl();
		}
		try {
			return contentToFileService.getContent(realpath+url);
		} catch (IOException e) {

		}

		return "";
	}

	/**
	 *
	 * 获取附近20个商铺的发布的动态，如果没有拿公共库
	 * @return
	 */
	public List<BaseBean> getNewsList(String x,String y){
		 String sql = "select m.companyID from dtCompany  m where  GETDISTANCE(?, ?, m.accuracy, m.dimension) < ?  order by dbms_random.value";
         List<Object> companylist = baseBeanDao.getListObjectBySqlAndParams(sql,new Object[]{x,y,10000});

		List<BaseBean> list = null;
		List<BaseBean> commonlist = null;
		List<Object> param = new ArrayList<Object>();
        if(companylist!=null&&companylist.size()>0){
			String sql1 = "select * from (select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.model,p.ppid from dt_ProductPackaging p where p.companyId in(";
			for (int i = 0;i<companylist.size();i++){

				if(i==companylist.size()-1){
					sql1+="?";
				}else{
					sql1+="?,";
				}
				param.add(companylist.get(i).toString());

			}
			param.add("新闻");
			param.add("会员分享");
			param.add("00");
			  sql1 += ") and (p.type=? or p.type=?) and p.review = ? and p.image is not null order by packagingDate desc) where rownum <6";
			list = baseBeanDao.getListBeanBySqlAndParams(sql1, param.toArray());

		}
		int rownum = 6;
		if(list!=null&&list.size()>0){
            rownum = 6-list.size();
		}
         if(rownum>0){
			 String sql1 = "select * from (select p.goodsName,p.PackagingDate,p.image,p.goodsID,p.model,p.ppid from dt_ProductPackaging p where p.companyId=? and (p.type=? or p.type=?) and p.review = ? and p.image is not null order by packagingDate desc) where rownum < ?";

			 commonlist = baseBeanDao.getListBeanBySqlAndParams(sql1, new Object[]{"company201009046vxdyzy4wg0000000025", "新闻", "会员分享","00",rownum});
		 }
		 if(rownum<6){
			 list.addAll(commonlist);
		 }else{
			 list = commonlist;
		 }


		return list;

	}


	/**
	 *
	 * 获取当前用户完成的认领任务
	 * @param staffID
	 * @return
	 */
	public PageForm getCompeleteBusi(int pageNumber, int pageSize, String date, String parameter, String gdcate,String staffID,String gdcate2){
		 List<Object> params = new ArrayList<Object>();
		 String sql = "select c.ccompanyID,c.companyName,c.logoPath,c.companyAddr,c.shopname from dtContactCompany c,DT_BManager b where c.ccompanyID = b.ccompanyID and b.type = ? and b.staffID = ?";

		 params.add("00");
		 params.add(staffID);
		if(gdcate2!=null&&!gdcate2.equals("")){
			sql+=" and c.gdcate2 = ?";
			params.add(gdcate2);
		 }else if(gdcate!=null&&!gdcate.equals("")){
			 sql+=" and c.gdcate = ?";
			 params.add(gdcate);
		 }
		if(parameter!=null&&!parameter.equals("")){
			sql+=" and (c.companyName like ? or c.shopname like ? or c.companyTel = ? or c.responsibleTel = ?)";
			params.add("%"+parameter+"%");
			params.add("%"+parameter+"%");
			params.add("%"+parameter+"%");
			params.add("%"+parameter+"%");
		}

		if(date!=null&&!date.equals("")){
			sql+=" and b.createDate between ? and ?";
			Date start =  Utilities.getDateFromString(date + " 00:00:00",
					"yyyy-MM-dd hh:mm:ss");
			Date end =  Utilities.getDateFromString(date + " 23:59:59",
					"yyyy-MM-dd hh:mm:ss");

			params.add(start);
			params.add(end);
		}
        sql+=" order by b.createDate desc";
		PageForm  pageForm = baseBeanService.getPageFormBySQL(pageNumber,pageSize,sql,"select count(*) from (" + sql + ")",params.toArray());
		return pageForm;

	}


	/**
	 *
	 *  未认领但发送过短信的公司
	 * @return
	 */
	public PageForm getCompanyZ(int pageNumber,int pageSize,String parameter,String gdcate,String jd,String wd,String yw) {
		PageForm pageForm = new PageForm();
		try {
			if (gdcate == null || gdcate.equals("")) {
				gdcate = "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息";
			}
			try {
				try {
					parameter = URLEncoder.encode(parameter, "utf-8");
					gdcate = URLEncoder.encode(gdcate, "utf-8");
				} catch (Exception e) {

				}
			} catch (Exception e) {

			}


			String url = "https://restapi.amap.com/v3/place/around?key=" + Constant.gdkey + "&keywords=" + parameter + "&location=" + jd + "," + wd + "&radius=10000&types=" + gdcate + "&offset=" + pageSize + "&page=" + pageNumber;
			String result = HttpClient.doGet(url);

			JSONObject jsonObject = new JSONObject(result);
			List<BaseBean> list1 = new ArrayList<BaseBean>();
			List<BaseBean> list = new ArrayList<BaseBean>();
			List<BaseBean> list2 = new ArrayList<BaseBean>();


			POI poi = null;

			String sql1 = "select kl.gdid from Dtcontactcompany kl where kl.gdid is not null and kl.gdid in(";
			List<Object> params1 = new ArrayList<Object>();
			String sql = "select telNum from dtTelMessage where telNum in(";
			List<Object> params = new ArrayList<Object>();
			if (jsonObject.getString("info").equals("OK")) {
				JSONArray pois = jsonObject.getJSONArray("pois");
				for (int i = 0; i < pois.length(); i++) {
					poi = new POI();
					JSONObject jsonObject1 = pois.getJSONObject(i);
					poi.setId(jsonObject1.getString("id"));
					sql1 += "?,";
					params1.add(poi.getId());
					poi.setName(jsonObject1.getString("name"));
					poi.setType(jsonObject1.getString("type"));
					poi.setTypecode(jsonObject1.getString("typecode"));
					try {
						poi.setAddress(jsonObject1.getString("address"));
					} catch (Exception e) {
//					logger.info("调试信息");
					}

					poi.setLocation(jsonObject1.getString("location"));
					try {
						poi.setTel(jsonObject1.getString("tel"));

					} catch (Exception e) {
						logger.info("调试信息");
					}
					poi.setPname(jsonObject1.getString("pname"));
					poi.setCityname(jsonObject1.getString("cityname"));
					poi.setAdname(jsonObject1.getString("adname"));
					poi.setDistance(jsonObject1.getString("distance"));
					float dis = Float.parseFloat(poi.getDistance());
					DecimalFormat ff = new DecimalFormat("#0.0");

					if (dis >= 1000) {
						String d = ff.format(dis / 1000) + "千米";
						poi.setDistance(d);
					} else {
						poi.setDistance(poi.getDistance() + "米");
					}

					JSONArray photo = jsonObject1.getJSONArray("photos");
					String[] photos = new String[photo.length()];
					try {
						for (int j = 0; j < photo.length(); j++) {

							JSONObject jsonObject2 = photo.getJSONObject(j);
							photos[j] = jsonObject2.getString("url");
						}
					} catch (Exception e) {
						logger.error("操作异常", e);
					}
					poi.setPhotos(photos);
					list.add(poi);

				}

				sql1 = sql1.substring(0, sql1.length() - 1);
				sql1 += ")";
				List<Object> gdlist = new ArrayList<Object>();
				if (params1.size() > 0) {
					gdlist = baseBeanDao.getListObjectBySqlAndParams(sql1, params1.toArray());//已经入驻的gdId

				} else {
					list1 = list;
				}

				for (int jj = 0; jj < list.size(); jj++) {
					POI poi1 = (POI) list.get(jj);
					if (!gdlist.contains(poi1.getId())) {
						list1.add(poi1);

						if ("y".equals(yw)) {
							if (!"".equals(poi1.getTel()) && poi1.getTel() != null) {
								String[] tels = poi1.getTel().split(";");
								for (int k = 0; k < tels.length; k++) {
									sql += "?,";
									params.add(tels[k]);
								}

							}
						}
					}
				}

				if ("y".equals(yw)) {

					// 已发送
					sql = sql.substring(0, sql.length() - 1);
					sql += ")";

					List<Object> tellist = new ArrayList<Object>();
					if (params.size() > 0) {
						tellist = baseBeanDao.getListObjectBySqlAndParams(sql, params.toArray());  //已经发过的
					}

					if (tellist.size() > 0) {
						for (int ii = 0; ii < list1.size(); ii++) {
							POI poi1 = (POI) list1.get(ii);
							String tels = poi1.getTel();
							if (tels != null && !tels.equals("")) {
								String[] telsArray = tels.split(";");
								for (int kk = 0; kk < telsArray.length; kk++) {
									if (tellist.contains(telsArray[kk])) {
										list2.add(poi1);
										break;
									}
								}
							}

						}
					}

					pageForm.setList(list2);
				} else {
					pageForm.setList(list1);
				}


			} else {
				pageForm.setList(list);
			}
			if (list.size() == 0) {
				logger.info("----------------");
				pageForm.setPageNumber(-1);
			} else {
				pageForm.setPageNumber(pageNumber);
			}
			logger.info("值：{}", pageNumber);
		}catch (Exception e){
			logger.error("操作异常", e);
		}


		return pageForm;
	}

}
