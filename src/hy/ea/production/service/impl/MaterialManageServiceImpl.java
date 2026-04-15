package hy.ea.production.service.impl;

import com.tiantai.wfj.bo.TEshopCusCom;
import com.tiantai.wfj.bo.TEshopCustomer;
import com.tiantai.wfj.bo.WeChatToken;
import com.tiantai.wfj.util.SessionWrap;
import com.wechat.bo.AccessToken;
import com.wechat.utils.WeixinUtil;
import com.wechatpay.utils.RequestHandler;
import com.wechatpay.utils.WeChatUtils;
import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.company.CcomCom;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.company.GoodsManage;
import hy.ea.bo.finance.PlatformPackage;
import hy.ea.bo.finance.ProductCommentMain;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.human.COS;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.CarManage;
import hy.ea.bo.office.CarManageAudit;
import hy.ea.bo.office.Certificate;
import hy.ea.bo.office.VenueInformation;
import hy.ea.bo.production.AttriProduction;
import hy.ea.bo.production.FieldConStor;
import hy.ea.bo.production.GoodFunction;
import hy.ea.bo.production.scmanage.DSVideo;
import hy.ea.bo.production.scmanage.MaterialContent;
import hy.ea.bo.production.scmanage.MaterialGroup;
import hy.ea.bo.production.scmanage.MaterialMusic;
import hy.ea.production.service.MaterialManageService;
import hy.ea.service.UpLoadFileService;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.*;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;
import mobile.tiantai.android.bo.AuditRecord;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MaterialManageServiceImpl implements MaterialManageService {

	@Resource
	private BaseBeanDao basebeandao;
	@Resource
	private ServerService serverService;
	@Resource
	private UploadContentToFileService contentToFileService;
	@Resource
	private UpLoadFileService fileService;

	@Transactional
	@Override
	public void saveContent(MaterialContent materialContent, String staffID) {
		String hql = "from MaterialContent where fileType = ? and staffID = ? and state = ? order by createDate desc";
		List<BaseBean> templist = basebeandao.getListBeanByHqlAndParams(hql,
				new Object[] { materialContent.getFileType(), staffID, "00" });

		String[] arrayBz = { "00", "01", "02" };
		String[] arrayID = { "p201609222GD4A4IZ4E0000000008",
				"p201609222GD4A4IZ4E0000000009",
				"p201609222GD4A4IZ4E0000000010" };
		String[] arrayName = { "图片", "视频", "音频" };

		int index = geIndex(arrayBz, materialContent.getFileType());

		String sql = "select t.ppid,t.goodsname,primaryField,twoLevelField from dt_ProductPackaging t where t.ppid!=? connect by nocycle prior t.ppid = t.parentid start with t.ppid = ?";

		List<BaseBean> list = basebeandao.getListBeanBySqlAndParams(sql,
				new Object[] { arrayID[index], arrayID[index] });

		List<BaseBean> baseBeanlist = new ArrayList<BaseBean>();

		for (int j = 0; j < templist.size(); j++) {
			MaterialContent mc = (MaterialContent) templist.get(j);
			mc.setState("01");
			mc.setGroupID(materialContent.getGroupID());
			mc.setDescribe(materialContent.getDescribe());

			baseBeanlist.add(mc);
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(index);
				Object[] objs = (Object[]) obj;
				FieldConStor fc = new FieldConStor();
				fc.setFieldConID(serverService.getServerID("sfcid"));
				fc.setPpID(arrayID[index]);
				fc.setGoodsName(arrayName[index]);
				fc.setFieldPpID(objs[0].toString());
				fc.setFieldPpName(objs[1].toString());
				fc.setTextID("");
				fc.setCashierBillsID(mc.getMcId());
				fc.setContent(findContent(objs[1].toString(), mc).toString());
				fc.setPrimaryField(objs[2].toString());
				fc.setTwoLevelField(objs[3].toString());
				baseBeanlist.add(fc);

			}
		}
		String hqlgroup = "from MaterialGroup where mgId = ?";
		MaterialGroup mg = (MaterialGroup) basebeandao.getBeanByHqlAndParams(
				hqlgroup, new Object[] { materialContent.getGroupID() });
		mg.setFilenum(String.valueOf(Integer.parseInt(mg.getFilenum())
				+ templist.size()));
		if (mg.getFileType().equals("01")) {
			mg.setGroupcover(((MaterialContent) templist.get(0)).getFilecover());
		} else {
			mg.setGroupcover(((MaterialContent) templist.get(0)).getFilepath());
		}

		baseBeanlist.add(mg);
		basebeandao.saveBeansListAndexecuteHqlsByParams(baseBeanlist, null,
				null);

	}

	/**
	 * 
	 * 根据值获取下标
	 * 
	 * @param arr
	 * @param value
	 * @return
	 */
	private int geIndex(String[] arr, String value) {
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == value) {
				index = i;
				break;
			}
		}
		return index;
	}

	private static Object findContent(String goodsname, MaterialContent mc) {
		Object content = "";
		if (goodsname.contains("上传人ID")) {
			content = mc.getStaffID();
		} else if (goodsname.contains("分组ID")) {
			content = mc.getGroupID();
		} else if (goodsname.contains("描述")) {
			content = mc.getDescribe();

		} else if (goodsname.contains("路径")) {
			content = mc.getFilepath();

		} else if (goodsname.contains("状态")) {
			content = "00";

		} else if (goodsname.contains("公司ID")) {
			content = mc.getCompanyID();

		} else if (goodsname.contains("上传时间")) {
			content = mc.getCreateDate();

		}

		return content;
	}

	@Transactional
	@Override
	public boolean deleteFileTempByone(String filepath, String realpath) {
		boolean f = true;
		try {
			String hqldelete = "delete from  MaterialContent where filepath like ? ";
			basebeandao.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hqldelete }, new Object[] { "%" + filepath
							+ "%" });
			File file = new File(realpath + filepath);

			if (file.exists() && file.isFile()) {
				file.delete();
			}
		} catch (Exception e) {
			f = false;
			e.printStackTrace();
		}

		return f;
	}

	@Transactional
	@Override
	public boolean deleteFileTempByBat(String companyID, String staffID,
			String realpath) {
		boolean f = true;
		try {
			String hql = "from MaterialContent where companyID = ? and staffID = ? and state = ?";
			List<BaseBean> mclist = basebeandao.getListBeanByHqlAndParams(hql,
					new Object[] { companyID, staffID, "00" });
			for (int i = 0; i < mclist.size(); i++) {
				MaterialContent cs = (MaterialContent) mclist.get(i);
				File file = new File(realpath + cs.getFilepath());

				if (file.exists() && file.isFile()) {
					file.delete();
				}

			}

			String hqldelete = "delete from  MaterialContent where  staffID = ? and state = ?";
			basebeandao.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { hqldelete }, new Object[] { staffID, "00" });
		} catch (Exception e) {
			f = false;
			e.printStackTrace();
		}

		return f;
	}

	@Transactional
	@Override
	public boolean deleteFileBypre(MaterialContent materialContent,
			String realpath) {
		String hql = "from MaterialContent where mcId = ?";
		MaterialContent mc = (MaterialContent) basebeandao
				.getBeanByHqlAndParams(hql,
						new Object[] { materialContent.getMcId() });
		String hqlgroup = "from MaterialGroup where mgId = ?";
		MaterialGroup mg = (MaterialGroup) basebeandao.getBeanByHqlAndParams(
				hqlgroup, new Object[] { mc.getGroupID() });
		mg.setFilenum(String.valueOf(Integer.parseInt(mg.getFilenum()) + 1));
		if (mc.getFileType() != null && !mc.getFileType().equals("02")) {
			String maxsql = "select  filepath  from  dtMaterialContent where groupID = ? and mcId != ? and rownum = 1 order by createDate desc";
			Object obj = basebeandao
					.getObjectBySqlAndParams(
							maxsql,
							new Object[] { mc.getGroupID(),
									materialContent.getMcId() });
			if (obj != null) {
				mg.setGroupcover(obj.toString());
			} else {
				mg.setGroupcover("/images/ea/production/scmanage/nocontent2x.png");
			}
		}
		deleteFileTempByone(mc.getFilepath(), realpath);

		basebeandao.update(mg);

		return false;
	}

	@Override
	@Transactional
	public ProductPackaging saveQRShare(ProductPackaging productPackaging,
			String content, String miniSystemJudge) {
		TEshopCusCom cuscom = null;
		CAccount cacc = null;
		String staffid = null;
		if(miniSystemJudge.equals("00") || miniSystemJudge.equals("01") || miniSystemJudge.equals("02")){
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			SessionWrap sw = SessionWrap.getInstance();
			cacc = (CAccount) sw.getObject(session,
					SessionWrap.KEY_CACCOUNT);
			staffid = cacc.getStaffID();
			//为简介/文化/新闻添加sccid
			if(productPackaging.getSccid()==null || productPackaging.getSccid().equals("")){
				String sql = "select sccid from t_eshop_cuscom where staffid=? order by  decode(custype,'1',1,'2',2,'3',3,'4',4,'5',5,'6',6,'7',7,'0',8) asc ";
				List<BaseBean> list = basebeandao.getListBeanBySqlAndParams(sql, new Object[]{staffid});
				//如果员工没有账号则添加公司账号的sccid
				if(list==null || list.size()==0){
					list = basebeandao.getListBeanBySqlAndParams("select sccid from t_eshop_cuscom where companyId=? order by  decode(custype,'1',1,'2',2,'3',3,'4',4,'5',5,'6',6,'7',7,'0',8) asc ", new Object[]{cacc.getCompanyID()});
				}
				Object obj = list.get(0);
				productPackaging.setSccid((String)obj);
			}
		}else if(miniSystemJudge.equals("03") || miniSystemJudge.equals("04")){
			cuscom = (TEshopCusCom) basebeandao.getBeanByHqlAndParams(
					"from TEshopCusCom t where t.sccId = ?",
					new Object[] { productPackaging.getSccid() });
			staffid = cuscom.getStaffid();
		}
		Staff staff = (Staff) basebeandao.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{staffid});
		
		if (productPackaging != null) {
			List<BaseBean> baselist = new ArrayList<BaseBean>();
			List<String> hqls = new ArrayList<String>();
			List<Object> parms = new ArrayList<Object>();
			GoodsManage goodsManage = new GoodsManage();
			if (productPackaging.getPpID() != null
					&& !productPackaging.getPpID().equals("")) {
				//删除介绍
				GoodFunction gf = (GoodFunction) basebeandao
						.getBeanByHqlAndParams(
								"from GoodFunction where goodsid = ?",
								new Object[] { productPackaging.getGoodsID() });

				delTransformVideo(gf.getUrl());
				basebeandao.deleteBeanByKey(GoodFunction.class, gf.getGfkey());

                //删除其他图
				String hqldelete = "delete from AttriProduction where goodsid = ?";
				hqls.add(hqldelete);

				parms.add(goodsManage.getGoodsID());


				// 修改产品名称,时间,图片
				ProductPackaging pp = (ProductPackaging) basebeandao.getBeanByHqlAndParams("from ProductPackaging where ppID=?", new Object[]{productPackaging.getPpID()});
				//删除旧图
				pp.setGoodsName(productPackaging.getGoodsName());
				pp.setPackagingDate(new Date());
				pp.setImage(productPackaging.getImage());
				pp.setStaffName(staff.getStaffName());
				pp.setCategoryName(productPackaging.getCategoryName());
				basebeandao.update(pp);
				// 修改物品名称,主图
				GoodsManage gm = (GoodsManage) basebeandao.getBeanByHqlAndParams("from GoodsManage where goodsID=?", new Object[]{productPackaging.getGoodsID()});
				gm.setGoodsName(productPackaging.getGoodsName());
				gm.setPhotoPath(productPackaging.getImage());
				basebeandao.update(gm);
				

			} else {
				// 添加
				// 物品添加
				goodsManage.setGoodsID(serverService.getServerID("goodsID"));
				goodsManage.setGoodsName(productPackaging.getGoodsName());
				if (miniSystemJudge.equals("00")) {
					goodsManage.setTypeID("公司简介");
				} else if (miniSystemJudge.equals("01")) {
					goodsManage.setTypeID("公司文化");
				} else if (miniSystemJudge.equals("02")) {
					goodsManage.setTypeID("公司新闻");
				} else if (miniSystemJudge.equals("03")) {
					goodsManage.setTypeID("会员分享");
				} else if(miniSystemJudge.equals("04")){
					goodsManage.setTypeID("公司论坛");
				}
				goodsManage.setModel("共享资源");// zsy 中联园咨询列表展示使用
				if (miniSystemJudge.equals("03")) {

					goodsManage.setCompanyID(productPackaging.getCompanyID());
//					if (cuscom.getCompanyId() != null && !cuscom.getCompanyId().equals("")) {
//						goodsManage.setCompanyID(cuscom.getCompanyId());
//					} else {
//						goodsManage.setCompanyID(getStaffCompanyID(cuscom
//								.getStaffid()));
//					}
					goodsManage.setStaffID(cuscom.getStaffid());
				} else if(miniSystemJudge.equals("04")) {
					goodsManage.setCompanyID(productPackaging.getCompanyID());
					goodsManage.setStaffID(cuscom.getStaffid());
				}else{
					goodsManage.setCompanyID(cacc.getCompanyID());
					goodsManage.setStaffID(cacc.getStaffID());
				}

				goodsManage.setPhotoPath(productPackaging.getImage());
				// 编号处理
				String hql = "select count(vt.goodsCoding) from GoodsManage vt where vt.typeID=?";
				// 获取拼音码
				String pinyin = ToChineseFirstLetter.getFirstLetter(goodsManage
						.getTypeID());
				String Upstr = pinyin.toUpperCase();// 转换为大写
				int goodscodingnum = basebeandao.getConutByByHqlAndParams(hql,
						new Object[] { goodsManage.getTypeID() });
				DecimalFormat form = new DecimalFormat("000000");
				String ss = form.format(goodscodingnum + 1);
				goodsManage.setGoodsCoding(Upstr + "_" + ss);
				if (miniSystemJudge.equals("00")
						|| miniSystemJudge.equals("01")
						|| miniSystemJudge.equals("02") || miniSystemJudge.equals("04")) {
					goodsManage.setFiveClear("2");
				} else if (miniSystemJudge.equals("03")) {
					goodsManage.setFiveClear("5");
				}
				goodsManage.setCreatedate(new Date());
				baselist.add(goodsManage);

				// 产品
				productPackaging.setDelStatus("00");
				productPackaging.setProductstate("01");
				if (miniSystemJudge.equals("00")
						|| miniSystemJudge.equals("01")
						|| miniSystemJudge.equals("02") || miniSystemJudge.equals("04")) {
					productPackaging.setFiveClear("2");
				} else if (miniSystemJudge.equals("03")) {
					productPackaging.setFiveClear("5");
				}
				productPackaging.setModel("共享资源");// zsy 中联园咨询列表展示使用
				productPackaging.setReview("00");// zsy 区分展示
				if (miniSystemJudge.equals("03")) {
//					if (cuscom.getCompanyId() != null && !cuscom.getCompanyId().equals("")) {
//						productPackaging.setCompanyID(cuscom.getCompanyId());
//					} else {
//						productPackaging.setCompanyID(getStaffCompanyID(cuscom
//								.getStaffid()));
//					}
					productPackaging.setStaffID(cuscom.getStaffid());
				} else if(miniSystemJudge.equals("04")) {
					productPackaging.setCompanyID(productPackaging.getCompanyID());
					productPackaging.setStaffID(cuscom.getStaffid());
				}else{
					productPackaging.setCompanyID(cacc.getCompanyID());
					productPackaging.setStaffID(cacc.getStaffID());
				}
				productPackaging.setStaffName(staff.getStaffName());
				productPackaging.setPackagingDate(new Date());
				productPackaging.setAssemble("00");
				productPackaging.setGoodsID(goodsManage.getGoodsID());
				productPackaging.setPpID(serverService.getServerID("p"));
				if (miniSystemJudge.equals("00")) {
					productPackaging.setType("公司简介");
				} else if (miniSystemJudge.equals("01")) {
					productPackaging.setType("公司文化");
				} else if (miniSystemJudge.equals("02")) {
					productPackaging.setType("公司新闻");
				} else if (miniSystemJudge.equals("03")) {
					productPackaging.setType("会员分享");
				} else if(miniSystemJudge.equals("04")){
					productPackaging.setType("公司论坛");
				}
				baselist.add(productPackaging);
			}
			// 保存功能介绍
			GoodFunction goodFun = new GoodFunction();
			String url = saveContentToFile(content);
			goodFun.setGfid(serverService.getServerID("gfid"));
			goodFun.setOrders(1);
			goodFun.setUrl(url);
			goodFun.setName(productPackaging.getGoodsName());
			goodFun.setType("1");
			goodFun.setGoodsid(productPackaging.getGoodsID());
			baselist.add(goodFun);
			
			
			try {

				if (content != null && !"".equals(content)) {

					List<Object[]> listimg = getVideoUrl(content);
					for (int i = 0; i < listimg.size(); i++) {
						Object[] objs = listimg.get(i);
						DSVideo dsv = new DSVideo();
				    	dsv.setVideoID(serverService.getServerID("videoID"));
				    	dsv.setCreateDate(new Date());
				    	dsv.setIsTop("00");
				    	dsv.setPpid(productPackaging.getPpID());
				    	dsv.setCoverImgUrl(objs[1].toString());
				    	dsv.setVideoURL(objs[0].toString());
				    	dsv.setReadnum(0);
				    	dsv.setStaffID(productPackaging.getStaffID());
				    	dsv.setState("00");
				    	dsv.setTitleName(productPackaging.getGoodsName());
				    	baselist.add(dsv);
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	        
	    	


			//添加多张图片
			String fimage = productPackaging.getFileUrl();
			String[] fimagearry = null;
			if(fimage!=null&&!fimage.equals("")){
				fimagearry = fimage.split(",");
			}
			for (int f=0;f<fimagearry.length;f++) {
				AttriProduction attrp = new AttriProduction();
				attrp.setApid(serverService.getServerID("apid"));
				attrp.setType("2");
				attrp.setImgurl(fimagearry[f].trim());
				attrp.setSort(f+1);
				attrp.setGoodsid(goodsManage.getGoodsID());
				baselist.add(attrp);
			}


			//添加评论以及点赞统计表
			//增加阅读量
			ProductCommentMain pcm = (ProductCommentMain)basebeandao.getBeanByHqlAndParams("from ProductCommentMain where ppid=?", new Object[]{productPackaging.getPpID()});
			if (pcm == null) {
				pcm = new ProductCommentMain();
				pcm.setPcmID(serverService.getServerID("pcmid"));
				pcm.setPpid(productPackaging.getPpID());
				pcm.setPlcount(0);
				pcm.setPraise(0);
				pcm.setReadcount(0);
				baselist.add(pcm);

			}



			//添加发布平台
			if(productPackaging.getCcompanyID()!=null&&!productPackaging.getCcompanyID().equals("")) {
				PlatformPackage pfp = new PlatformPackage();
				pfp.setCcomIDPlatform(productPackaging.getCcompanyID());
				pfp.setPfID(serverService.getServerID("pfid"));
				pfp.setPpID(productPackaging.getPpID());
				baselist.add(pfp);
			}
			basebeandao.saveBeansListAndexecuteHqlsByParams(baselist,
					hqls.toArray(new String[] {}), parms.toArray());
		}

		return productPackaging;
	}
		
	
	
	/**
	 * 处理文章中的视频
	 */
	@Override
	@Transactional
	public void dealVideo(){
		List<BaseBean> baselist = new ArrayList<BaseBean>();
		String path1 = ServletActionContext.getServletContext()
                .getRealPath("\\");
		  String sql = "select n.url,t.goodsname,t.ppid,t.staffid from dt_productpackaging t,Dtgoodfunction n where t.goodsid = n.goodsid and (t.type='会员分享' or t.type='新闻') and n.url is not null and t.staffid is not null";
	      List<Object> list = basebeandao.getListObjectBySqlAndParams(sql,null);
	      for (int i = 0; i < list.size(); i++) {
	    	  Object obj0 = list.get(i);
	    	  Object[] objs0 = (Object[])obj0;
	    	  try {
				String content = contentToFileService.getContent(path1+objs0[0]);
				List<Object[]> listimg = getVideoUrl(content);
				for (int j = 0; j < listimg.size(); j++) {
					Object[] objs = listimg.get(j);
					DSVideo dsv = new DSVideo();
			    	dsv.setVideoID(serverService.getServerID("videoID"));
			    	dsv.setCreateDate(new Date());
			    	dsv.setIsTop("00");
			    	dsv.setPpid(objs0[2].toString());
			    	
			    	dsv.setCoverImgUrl(objs[1].toString());
			    	dsv.setVideoURL(objs[0].toString());
			    	
			    	dsv.setReadnum(0);
			    	dsv.setStaffID(objs0[3].toString());
			    	dsv.setState("00");
			    	dsv.setTitleName(objs0[1].toString());
			    	baselist.add(dsv);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	      
	     basebeandao.saveBeansListAndexecuteHqlsByParams(baselist, null, null);
	
	}
		
		 /*** 
	     * 获取ImageUrl地址 
	     * 
	     * @param 
	     * @return 
	     */
	    private List<Object[]> getVideoUrl(String content) { 
	    	
				List<Object[]> srcList = new ArrayList<Object[]>(); //用来存储获取到的图片地址
				Pattern p = Pattern.compile("<(video|Video)(.*?)(>|></video>|/>)");//匹配字符串中的img标签
				Matcher matcher = p.matcher(content);
				boolean hasPic = matcher.find();
				if(hasPic == true)//判断是否含有视频
				{
					while(hasPic) //如果含有视频，那么持续进行查找，直到匹配不到
					{
						String group = matcher.group(2);//获取第二个分组的内容，也就是 (.*?)匹配到的
						
						Pattern srcText = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");//匹配视频的地址
						Matcher matcher2 = srcText.matcher(group);
						
						Pattern srcText0 = Pattern.compile("(poster|POSTER)=(\"|\')(.*?)(\"|\')");//匹配视频封面图
						Matcher matcher20 = srcText0.matcher(group);
						
						if( matcher2.find() ) 
						{  
							Object[] obj = new Object[2];
							obj[0] = matcher2.group(3);
							if(matcher20.find()) {
						      	obj[1] = matcher20.group(3);
						    }else{
						    	obj[1] = "";
						    }
							srcList.add(obj);//把获取到的视频地址添加到列表中
							
						}
						hasPic = matcher.find();//判断是否还有img标签
					}	
				}
			System.out.println("匹配到的内容："+srcList);
	      return srcList; 
	    } 

	/**
	 * 
	 * 根据个人获取他的一个公司ID
	 * 
	 * @param staffID
	 * @return
	 */
	public String getStaffCompanyID(String staffID) {
		String companyID = "";
		String hql = "from COS where staffID = ?";
		List<BaseBean> list = basebeandao.getListBeanByHqlAndParams(hql,
				new Object[] { staffID });
		if (list.size() != 0) {
			COS cos = (COS) list.get(0);
			companyID = cos.getCompanyID();
		}

		return companyID;
	}

	private String saveContentToFile(String content) {
		String id = "share" + RandomDatas.getUUID();
		String path = ServletActionContext.getServletContext().getRealPath("")
				+ "/upload_files/goodDetail/";

		try {
			contentToFileService.saveContent(id, content, path);

		} catch (IOException e) {
			e.printStackTrace();

		}

		return "/upload_files/goodDetail/" + id
				+ UploadContentToFileService.suffix;
	}

	/**
	 * 分页
	 * 
	 * @param pageNumber
	 *            当前页
	 * @param pageSize
	 *            显示条数
	 * @param sql
	 *            sql语句
	 * @param sqlcount
	 *            总记录数SQL语句
	 * @param params
	 *            参数
	 * @return
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm getPageFormBySQL(int pageNumber, int pageSize, String sql,
			String sqlcount, Object[] params) {
		int count = basebeandao.getConutByBySqlAndParams(sqlcount, params);// 总条数
		if (count == 0)
			return null;
		int pageCount = count / pageSize + ((count % pageSize) == 0 ? 0 : 1);
		if (pageNumber < 1)
			pageNumber = 1;
		if (pageNumber > pageCount)
			pageNumber = pageCount;
		int firstResult = pageSize * (pageNumber - 1);
		int maxResult = Math.min(pageSize, count - firstResult);
		List<BaseBean> listBaseBean = basebeandao
				.getConutByBySqlAndParamsAndPage(sql, params, firstResult,
						maxResult);
		PageForm pageForm = new PageForm();
		pageForm.setPageSize(pageSize);
		pageForm.setRecordCount(count);
		pageForm.setPageCount(pageCount);
		pageForm.setPageNumber(pageNumber);
		pageForm.setList(listBaseBean);
		return pageForm;
	}

	/**
	 * @return
	 * @Title: 查询
	 * @Description: 查询个人信息
	 * @return 返回map
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Object qrshareList(TEshopCusCom cuscom) {
		StringBuilder sb = new StringBuilder();
		sb.append("select f.headimage,f.staffname,d.goodsname as custype,p.goodsname,m.sccid,f.staffid,m.companyId from ");
		sb.append("t_eshop_cuscom m,dt_hr_staff f,dt_productpackaging p,dt_productpackaging d ");
		sb.append("where m.sccid=? and m.staffid =f.staffid and m.ppid=p.ppid ");
		sb.append("and d.type=? and m.custype=d.model ");
		Object obj = basebeandao.getObjectBySqlAndParams(sb.toString(),
				new Object[] { cuscom.getSccId(), "会员类型级别" });
		return obj;
	}

	/**
	 * @return
	 * @Title: 查询
	 * @Description: 查询个人分享
	 * @return 返回map
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm memberShare(int pageNumber, int pageSize, String sccid,String companyID) {


		StringBuilder sb = new StringBuilder();
		sb.append("select a.goodsname,a.image,a.ppid,a.goodsid,a.packagingdate,");
		sb.append("max(case a.type when ? then a.c else 0 end),max(case a.type when ? then a.c else 0 end),");
		sb.append("max(case a.type when ? then a.c else 0 end),a.READCOUNT from ");
		sb.append("(select p.goodsname,p.image,p.ppid,p.goodsid,p.packagingdate,d.type,count(d.type) c,dm.READCOUNT ");
		sb.append("from dt_productpackaging p left join dt_ProductCommentMain dm on p.ppid = dm.ppid left join dt_ProductComment d ");
		sb.append("on p.ppid = d.ppid where p.type = ? and p.sccid = ? ");

		if(companyID!=null&&!companyID.equals("")){

			sb.append(" and p.companyID = ?");

		}else{

			sb.append(" and p.companyID is null ");
		}
		sb.append("and p.fiveclear = ? and p.delstatus= ? group by ");
		sb.append("p.goodsname, p.image, p.ppid, p.goodsid, p.packagingdate, d.type,dm.READCOUNT) a ");
		sb.append("group by a.goodsname,a.image,a.ppid,a.goodsid,a.packagingdate,a.READCOUNT ");
		sb.append("order by a.packagingdate desc ");

		PageForm pageForm = null;
		if(companyID!=null&&!companyID.equals("")){

			 pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(),
					"select count(*) from (" + sb.toString() + ")", new Object[] { "0","1","2","会员分享",sccid,companyID,"5","00" });

		}else{

			 pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(),
					"select count(*) from (" + sb.toString() + ")", new Object[] { "0","1","2","会员分享",sccid,"5","00" });
		}



		return pageForm;
	}

	/**
	 *
	 *  查询全面分享
	 * @param parameter
	 * @return
	 */
	public PageForm allShare(int pageNumber, int pageSize, String  parameter,String staffId){
		StringBuilder sql = new StringBuilder();
		List<Object> objList = new ArrayList<Object>();
		sql.append("select *  from (");
		sql.append(" select p.goodsName,to_char(p.packagingdate,'yyyy-mm-dd'),p.image,p.ppid,p.type,m.ccompany_Id,cc.companyname author,p.staffid,p.packagingdate");
		sql.append(" from dt_ProductPackaging p, DT_ccom_com m,dtcontactcompany cc");
		sql.append(" where (p.companyId = ? and p.type=? and p.fiveclear=? and p.delStatus=? and p.companyID = m.compnay_id and m.ccompany_id = cc.ccompanyid)");
		sql.append(" or (p.type = ? and p.review = ? and p.companyID=m.compnay_id and m.ccompany_id = cc.ccompanyid and p.companyid is not null)");
		sql.append(" union");
		sql.append(" select pp.goodsName,to_char(pp.packagingdate,'yyyy-mm-dd'),pp.image,pp.ppid,pp.type,pp.companyid,s.staffname author,pp.staffid,pp.packagingdate");
		sql.append(" from dt_ProductPackaging pp, dt_hr_staff s");
		sql.append(" where pp.type = ? and pp.review = ? and pp.staffid = s.staffid and pp.companyid is null) x");

		objList.add("company201009046vxdyzy4wg0000000065");
		objList.add("新闻");
		objList.add("2");
		objList.add("00");
		objList.add("会员分享");
		objList.add("00");
		objList.add("会员分享");
		objList.add("00");


		if(parameter!=""&&!parameter.equals("")){
			sql.append(" where (x.goodsName like ? or x.author like ?)");
			objList.add("%"+parameter+"%");
			objList.add("%"+parameter+"%");
		}
	    sql.append(" order by case when x.staffid= ? then 1 else 0 end desc");
		sql.append(" ,x.packagingdate desc");
		objList.add(staffId);

		PageForm pageForm = getPageFormBySQL(
				pageNumber, pageSize, sql.toString(), "select count(*) from (" + sql + ")",
				objList.toArray());
	   return pageForm;

	}

	/**
	 * @return
	 * @Title: 查询
	 * @Description: 查询产品(自己优先)
	 * @return 返回map
	 */
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm inquireProduct(int pageNumber, int i, String goodsname,
			String staffid) {
		StringBuilder sb = new StringBuilder();
		sb.append("select p.image,p.goodsname,d.re_price,p.ppid,p.goodsid,p.companyid,m.ccompany_id from ");
		sb.append("dt_productpackaging p,DT_ccom_com m,dt_pro_setup d where  p.goodsname like ? ");
		sb.append("and p.showweixin =? and p.type not in (?,?) and p.ppid=d.ppid ");
		sb.append("and p.companyid=m.compnay_id ");
		sb.append("order by case when p.staffid=? then 0 else 1 end ");
		PageForm pageForm = getPageFormBySQL(pageNumber, i, sb.toString(),
				"select count(*) from (" + sb.toString() + ")", new Object[] {
						'%' + goodsname + '%', "01", "公司会员", "个人会员", staffid });
		return pageForm;
	}

	/**
	 * @return
	 * @Title: 删除
	 * @Description: 删除项目
	 * @return
	 */
	@Transactional
	public void deleteProject(String ppID, String goodsid,String pictureName) {
		GoodFunction gf = (GoodFunction) basebeandao
				.getBeanByHqlAndParams("from GoodFunction where goodsid = ?",
						new Object[] { goodsid });
		String hql1 = "delete from ProductPackaging p where p.ppID = ?";
		String hql2 = "delete from GoodFunction g where g.goodsid = ?";
		String hql3 = "delete from GoodsManage m where m.goodsID = ?";
		List<Object[]> parmsList = new ArrayList<Object[]>();
		parmsList.add(new Object[] { ppID });
		parmsList.add(new Object[] { goodsid });
		parmsList.add(new Object[] { goodsid });
		basebeandao.executeHqlsByParmsList(null, new String[] { hql1, hql2,
				hql3 }, parmsList);
		delTransformVideo(pictureName+","+gf.getUrl());
	}

	/**
	 * @return
	 * @Title: 删除
	 * @Description: 删除临时保存图片
	 * @return
	 */
	@Transactional
	public void delTemporarySavePicture(String mcid) {
		String path = ServletActionContext.getServletContext().getRealPath("/");
		Object[] obj = mcid.split(",");
		// 查询临时保存图片的数据
		String hql = "from MaterialContent t where t.mcId in (";
		for (int i = 0; i < obj.length; i++) {
			hql += "?,";
		}
		hql = hql.substring(0, hql.length() - 1);
		hql += ")";

		List<BaseBean> list = basebeandao.getListBeanByHqlAndParams(hql, obj);
		for (int i = 0; i < list.size(); i++) {
			MaterialContent mct = (MaterialContent) list.get(i);
			// 删除图片
			FileUtil.delete(path + mct.getFilepath());
		}
		String hql1 = "delete " + hql;
		// 删除数据路径
		basebeandao.saveBeansListAndexecuteHqlsByParams(null,
				new String[] { hql1 }, obj);
	}

	/**
	 * @return
	 * @Title: 修改
	 * @Description: 修改默认登录账号
	 * @return
	 */
	@Transactional
	public void updateLogin(String sccid, TEshopCusCom tcc) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		SessionWrap sw = SessionWrap.getInstance();
		List<BaseBean> baseBeans = new ArrayList<BaseBean>();
		TEshopCusCom cuscom = (TEshopCusCom) basebeandao.getBeanByHqlAndParams(
				"from TEshopCusCom m where m.sccId=?", new Object[] { sccid });
		if (!tcc.getSccId().equals(cuscom.getSccId())) {
			cuscom.setAcquiesce("01");
			baseBeans.add(cuscom);
			tcc.setAcquiesce("00");
			baseBeans.add(tcc);
			basebeandao.saveBeansListAndexecuteHqlsByParams(baseBeans, null,
					null);
		}

		sw.setObject(session, SessionWrap.KEY_SHOPCUSCOM, cuscom);
	}

	/**
	 * @param @urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String results = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", Constant.MUSIC_KEY);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			results = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询历史音乐
	 * @return 返回list
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> historyMusic(String staffid) {
		String hql = "from MaterialMusic where staffId = ? and musicType = ?";
		List<BaseBean> hmsc = basebeandao.getListBeanByHqlAndParams(hql,
				new Object[] { staffid, "00" });
		return hmsc;
	}

	/**
	 * @Title: 查询
	 * @Description: 查询默认音乐
	 * @return 返回list
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<BaseBean> defaultMusic() {
		String hql = "from MaterialMusic where musicType = ?";
		List<BaseBean> dmsc = basebeandao.getListBeanByHqlAndParams(hql,
				new Object[] { "01" });
		return dmsc;
	}

	/**
	 * @return
	 * @Title: 添加
	 * @Description: 添加历史音乐记录
	 * @return 无返回
	 */
	@Override
	@Transactional
	public boolean addRecord(String staffid, MaterialMusic materialMusic) {
		List<BaseBean> list = basebeandao.getListBeanByHqlAndParams(
				"from MaterialMusic c where c.musicType = ? ",
				new Object[] { "00" });
		for (int i = 0; i < list.size(); i++) {
			MaterialMusic mc = (MaterialMusic) list.get(i);
			if (materialMusic.getMusichash().equals(mc.getMusichash())) {
				return false;
			}
		}
		try {
			materialMusic.setMmId(serverService.getServerID("mmId"));
			materialMusic.setStaffId(staffid);
			materialMusic.setCreateDate(new Date());
			materialMusic.setMusicType("00");
			basebeandao.save(materialMusic);
			String sql = "select count(mmid) from dtMaterialMusic c where c.musictype=?";
			int i = basebeandao.getConutByBySqlAndParams(sql,
					new Object[] { "00" });
			if (i >= 5) {
				StringBuilder sb = new StringBuilder();
				sb.append("delete from dtMaterialMusic c ");
				sb.append("where c.createdate = (select min(d.createdate) ");
				sb.append("from dtMaterialMusic d where d.musictype=?) ");
				basebeandao.saveBeansListAndexecuteSqlsByParams(null,
						new String[] { sb.toString() }, new Object[] { "00" });
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title: 新版添加
	 * @Description: 添加图片/视频
	 * @return 返回路径
	 * 
	 */
	@Transactional
	public String addMaterial(String staffid, String companyId, File picture,
			String pictureName) {
		HttpServletRequest re = ServletActionContext.getRequest();
		String path = re.getSession().getServletContext().getRealPath("/");
		if (companyId == null || companyId.equals("")) {
			companyId = getStaffCompanyID(staffid);
		}
		String photopath = fileService.savePhoto(path, pictureName, picture,
				companyId, "scmanage/".concat(DateUtil
						.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)));

		return photopath;
	}

	/**
	 * 
	 * @Title: 添加
	 * @Description: 转化视频格式,生成视频截图
	 * @return
	 * 
	 */
	@Override
	@Transactional
	public boolean transitionVeido(String share_path, String img_path,
			String veido) {
		boolean b = true;
		HttpServletRequest re = ServletActionContext.getRequest();
		String path = re.getSession().getServletContext().getRealPath("/");
		File file = new File(path + share_path);
		if (!file.exists()) {
			b = false;
		}
		// 获取配置的转换工具（ffmpeg.exe）的存放路径
		String ffmpeg_path = TTswUtils.getPath();
		b = FFmpegTransfer.mp4H264(ffmpeg_path, path + share_path,
				path + veido, path + img_path);
		return b;
	}

	/**
	 * 
	 * @Title: 删除
	 * @Description: 删除图片/视频
	 * @return
	 * 
	 */
	@Override
	@Transactional
	public void delTransformVideo(String pictureName) {
		String path = ServletActionContext.getServletContext().getRealPath("/");
		String[] url = pictureName.split(",");
		for (int i = 0; i < url.length; i++) {
			// 删除视频
			if (url[i] != null && !url[i].equals("")) {
				String[] u = url[i].split("\\.");
				String u1 = u[0]+"small."+u[1];
				File file = new File(path + u1);
				if(file.exists()){
					FileUtil.delete(path + u1);
				}
				FileUtil.delete(path + url[i]);
			}
		}
	}

	/***************************************** 公司简介/文化/新闻 ************************************/

	/**
	 * 
	 * @Title: 查询
	 * @Description: 查询公司新闻/简介/文化列表
	 * @return
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public PageForm miniList(int pageNumber, int pageSize, String companyId,
			String miniSystemJudge) {
		String type = null;
		if (miniSystemJudge.equals("00")) {
			type = "公司简介";
		} else if (miniSystemJudge.equals("01")) {
			type = "公司文化";
		} else if (miniSystemJudge.equals("02")) {
			type = "公司新闻";
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("select a.goodsname,a.image,a.ppid,a.goodsid,a.packagingdate,");
		sb.append("max(case a.type when ? then a.c else 0 end),max(case a.type when ? then a.c else 0 end),");
		sb.append("max(case a.type when ? then a.c else 0 end) from ");
		sb.append("(select p.goodsname,p.image,p.ppid,p.goodsid,p.packagingdate,d.type,count(d.type) c ");
		sb.append("from dt_productpackaging p left join dt_ProductComment d ");
		sb.append("on p.ppid = d.ppid where p.type = ? and p.companyid = ? ");
		sb.append("and p.fiveclear = ? and p.delstatus= ? group by ");
		sb.append("p.goodsname, p.image, p.ppid, p.goodsid, p.packagingdate, d.type) a ");
		sb.append("group by a.goodsname,a.image,a.ppid,a.goodsid,a.packagingdate ");
		sb.append("order by a.packagingdate desc ");
		PageForm pageForm = getPageFormBySQL(pageNumber, pageSize, sb.toString(),
				"select count(*) from (" + sb.toString() + ")", new Object[] {"0","1","2",type,companyId,"2", 
						"00"});
		return pageForm;
	}

	/**
	 * 
	 * @return
	 * @Title: 添加
	 * @Description: 添加完善公司信息
	 * @return
	 * 
	 */
	@Override
	@Transactional
	public boolean addMessage(CAccount caccount, ContactCompany contactCompany,
			Certificate certificate) {
		//保存公司详情
		String logoPath = contactCompany.getLogoPath();
		if(logoPath.indexOf(";base64")!=-1){
			if(logoPath.indexOf("jpeg")!=-1){
				logoPath = logoPath.replace("jpeg","jpg");
			}
			// 图片存储路径
			String photopath = "upload_files\\corporateLogo\\"
					+ caccount.getCompanyID() + "\\" + caccount.getStaffID();
			// 重命名
			String upName = UUID.randomUUID().toString()
					+ System.currentTimeMillis() + "." + logoPath.substring(logoPath.indexOf("image/")+6, logoPath.indexOf(";base64"));
			boolean b = Base64(photopath, logoPath, upName);
			if (!b) {
				return false;
			}
			logoPath = photopath + "\\" + upName;
		}else{
			logoPath = "upload_files"+logoPath.split("upload_files")[1];
		}
		
		List<BaseBean> list1 = basebeandao.getListBeanByHqlAndParams("from Company where companyID = ?", new Object[]{caccount.getCompanyID()});
		CcomCom com = (CcomCom) basebeandao.getBeanByHqlAndParams("from CcomCom where comanyId = ?", new Object[]{caccount.getCompanyID()});
		
		Company cy = (Company) list1.get(0);
		cy.setCompanyName(contactCompany.getCompanyName());
		
		StringBuilder sb = new StringBuilder();
		sb.append("update dtContactCompany c set c.companyname = ?,");
		sb.append("c.logopath = ?,c.representative = ?,c.idCard = ?,c.cresponsible = ?,");
		sb.append("c.Companyaddr = ?,c.Responsibletel = ?,c.authState = ? ");
		sb.append("where c.ccompanyid = ?");
		List<Object> list = new ArrayList<Object>();
		list.add(contactCompany.getCompanyName());
		list.add(logoPath);
		list.add(contactCompany.getRepresentative());
		list.add(contactCompany.getIdCard());
		list.add(contactCompany.getCresponsible());
		list.add(contactCompany.getCompanyAddr());
		list.add(contactCompany.getResponsibleTel());
		list.add("01");
		list.add(com.getCcompanyId());
        if(contactCompany.getIndustryType()!=null && contactCompany.getIndustryType().contains("驾校") &&  contactCompany.getJingdu() != null){
            String [] s=contactCompany.getJingdu().split(",");
            contactCompany.setCompanyAddr(s[0]);
            contactCompany.setJingdu(s[1]);
        }

		basebeandao.saveBeansListAndexecuteSqlsByParams(list1,
				new String[] { sb.toString() }, list.toArray());
		
		//保存公司证书
		String cft = certificate.getCertificateLocation();
		if(cft.indexOf(";base64")!=-1){
			if(cft.indexOf("jpeg")!=-1){
				cft = cft.replace("jpeg","jpg");
			}
			// 图片存储路径
			String photopath = "upload_files\\certificate\\"
					+ caccount.getCompanyID() + "\\" + caccount.getStaffID();
			// 重命名
			String upName = UUID.randomUUID().toString()
					+ System.currentTimeMillis() + "."
					+ cft.substring(cft.indexOf("image/")+6, cft.indexOf(";base64"));
			boolean b = Base64(photopath, cft,upName);
					
			if (!b) {
				return false;
			}
			cft = photopath + "\\" + upName;		
		}else{
			cft = "upload_files"+cft.split("upload_files")[1];
		}
		Certificate cc = (Certificate) basebeandao
				.getBeanByHqlAndParams(
						"from Certificate e where e.companyID=? and e.certificateType=?",
						new Object[] { caccount.getCompanyID(),
								certificate.getCertificateType() });
		if (cc != null) {
			cc.setCertificateLocation(cft);
			basebeandao.update(cc);
		} else {
			Certificate ctt = new Certificate();
			ctt.setCertificateID(serverService.getServerID("cf"));
			ctt.setCompanyID(caccount.getCompanyID());
			ctt.setCcompanyID(com.getCcompanyId());
			ctt.setCertificateType(certificate.getCertificateType());
			ctt.setCertificateName(certificate.getCertificateName());
			ctt.setCertificateLocation(cft);
			basebeandao.save(ctt);
		}
		
		//提交审核
		AuditRecord ar = new AuditRecord();
		ar.setAuid(serverService.getServerID("au"));
		ar.setBatchNum(serverService.getServerID("bat"));
		ar.setAuditID("cstaff20110712KAX2RHUQZI0000025385");
		ar.setAuditName("孟竹");
		ar.setAuditOrgID("organization20100909zijmxn7qze0000001402");
		ar.setAuditOrgName("教务处");
		ar.setAuditComID("company201009046vxdyzy4wg0000000025");
		ar.setAuditComName("北京天太世统科技有限公司");
		ar.setPosition("开发工程师");
		ar.setStartID(caccount.getStaffID());
		Staff staff = (Staff) basebeandao.getBeanByHqlAndParams("from Staff where staffID=?", new Object[]{caccount.getStaffID()});
		if(staff == null ){
            HttpSession session = ServletActionContext.getRequest().getSession();
            SessionWrap sw = SessionWrap.getInstance();
            TEshopCustomer cus = (TEshopCustomer) sw.getObject(session,
                    SessionWrap.KEY_CUSTOMER);
            staff=new Staff();
            staff= (Staff) basebeandao.getBeanByHqlAndParams(" from Staff where staffID=? ",new Object[]{cus.getStaffid()});

        }
		ar.setStartName(staff.getStaffName());
		ar.setSubmitDate(new Date());
		ar.setSorts("1");
		//详情链接
		String viewUrl = "ea/qrshare/ea_perfectionMessage.jspa?caccount.staffID="+caccount.getStaffID()+"&caccount.companyID="+caccount.getCompanyID()+"&auditSkip=01"+"&auditRecord.auid="+ar.getAuid()+"&auditRecord.batchNum="+ar.getBatchNum();
		ar.setViewUrl(viewUrl);
		ar.setModule("公司认证");
		ar.setThirdId(com.getCcompanyId());
		ar.setState("01");
		basebeandao.save(ar);
		return true;
	}

	public boolean Base64(String photopath, String logoPath, String upName) {
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		String dir = path + photopath;
		File fileLocation = new File(dir);
		// 判断上传路径是否存在，如果不存在就创建
		if (!fileLocation.exists()) {
			boolean isCreated = fileLocation.mkdirs();
			if (!isCreated) {
				return false;
			}
		}
		// 重命名
		FileOutputStream out;
		String iconBase64 = logoPath.substring(22);
		try {
			byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
			out = new FileOutputStream(dir + "/" + upName);
			out.write(buffer);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	
	/**
	 * 
	 * @Title: 查询
	 * @Description: 查询公司信息
	 * @return
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public ContactCompany queryMessage(CAccount caccount) {
		String hql = "select c from CcomCom m,ContactCompany c where m.comanyId = ? and m.ccompanyId = c.ccompanyID";
		
		ContactCompany con = (ContactCompany) basebeandao.getBeanByHqlAndParams(hql, new Object[]{caccount.getCompanyID()});
		
		return con;
	}

	/**
	 * 
	 * @Title: 查询
	 * @Description: 查询公司证书
	 * @return
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Certificate queryCertificate(CAccount caccount) {
		
		String hql = "from Certificate c where c.companyID = ? and c.certificateType = ?";
		
		Certificate cf = (Certificate) basebeandao.getBeanByHqlAndParams(hql, new Object[]{caccount.getCompanyID(),"营业执照"});
		
		return cf;
	}

	/**
	 * 
	 * @Title: 查询
	 * @Description: 查询审核
	 * @return
	 * 
	 */
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public AuditRecord queryRecord(String auid) {
		AuditRecord ar = (AuditRecord) basebeandao.getBeanByHqlAndParams("from AuditRecord where auid = ?", new Object[]{auid});
		return ar;
	}

	/**
	 * 
	 * @Title: 修改
	 * @Description: 修改往来单位审核状态
	 * @return
	 * 
	 */
	@Override
	@Transactional
	public void alterAudit(String companyID, String state) {
		
		String hql = "select c from CcomCom m,ContactCompany c where m.comanyId = ? and m.ccompanyId = c.ccompanyID";
		
		ContactCompany con = (ContactCompany) basebeandao.getBeanByHqlAndParams(hql, new Object[]{companyID});
		
		con.setAuthState(state);
	}


    /**
	 *
	 * 获取sdkConfig
	 * @param url
	 * @return
     */

	@Transactional
	public  Map<String,Object> getJssdkConfig(String url ){

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("appId","wxa1b3f84c027804c3");
		map.put("timestamp",System.currentTimeMillis() / 1000);
		map.put("nonceStr", WeChatUtils.getNonceStr());


		String hql = "from WeChatToken where appID = ?";
		WeChatToken wt = (WeChatToken) basebeandao.getBeanByHqlAndParams(hql, new Object[]{"wxa1b3f84c027804c3"});
		String accessTokens = "";
		String jsapi_ticket = "";
		if (wt != null && wt.getAccessToken() != null && !wt.getAccessToken().equals("")) {
			accessTokens = wt.getAccessToken();

			jsapi_ticket = wt.getJsapi_ticket();
            if(jsapi_ticket==null||jsapi_ticket.equals("")){
				jsapi_ticket = WeixinUtil.getJSticket(accessTokens);
				wt.setJsapi_ticket(jsapi_ticket);
				basebeandao.update(wt);
			}
		} else {


			AccessToken accessToken = WeixinUtil.getAccessToken("wxa1b3f84c027804c3", "26d569353c295fa8ad4fcb85a199f631");
			accessTokens = accessToken.getToken();
			jsapi_ticket = WeixinUtil.getJSticket(accessTokens);
			wt.setJsapi_ticket(jsapi_ticket);
			basebeandao.update(wt);

		}
		System.out.print("jsapi_ticket"+jsapi_ticket);

		String key = "jsapi_ticket="+jsapi_ticket+"&noncestr="+map.get("nonceStr")+"&timestamp="+map.get("timestamp")+"&url="+url;
		RequestHandler reqHandler = new RequestHandler(null, null);
		// 要签名
		try {
			String sign = reqHandler.shaEncode(key);
			map.put("signature",sign);
		} catch (Exception e) {

		}
  		return map;
	}

	/**
	 *
	 * 保存提交的信息
	 * @param carManageAudit
	 * @param staffID
	 * @param companyID
	 * @return
	 */
	@Transactional
	public String saveTimeInfo(CarManageAudit carManageAudit, String staffID, String companyID){
       if(carManageAudit.getCmaID()==null||carManageAudit.getCmaID().equals("")){
		   carManageAudit.setCmaID(serverService.getServerID("cmaid"));
	   }

		carManageAudit.setCreateDate(new Date());
		carManageAudit.setStaffID(staffID);
		carManageAudit.setCompanyID(companyID);
		carManageAudit.setAuditStatus("00");

        basebeandao.update(carManageAudit);



		return carManageAudit.getCmaID();
	}

	/**
	 *
	 * 获取设置免费时间页面数据如果有历史数据直接获取
	 * @param carmID
	 * @return
	 */
	public CarManageAudit getTimeHistory(String carmID){

		CarManageAudit carManageAudit = (CarManageAudit)basebeandao.getBeanByHqlAndParams( "from CarManageAudit where  carmID = ? and auditStatus = '00'",new Object[]{carmID});
		if(carManageAudit==null){
			CarManage carManage = (CarManage)basebeandao.getBeanByHqlAndParams( "from CarManage where carmID = ?",new Object[]{carmID});
			carManageAudit = new CarManageAudit();
			carManageAudit.setCarmID(carmID);
			carManageAudit.setCarNumber(carManage.getCarNumber());
			carManageAudit.setIndate(carManage.getIndate());
			carManageAudit.setOutdate(carManage.getOutdate());
			carManageAudit.setStatus(carManage.getStatus());

			String hql = "from VenueInformation n where n.siteId = (select  e.siteId from EquipmentInformation e where e.equipmentNumber = (select c.equipmentNumber from CarManage c where c.carmID = ?))";
			VenueInformation venueInformation = (VenueInformation)basebeandao.getBeanByHqlAndParams(hql,new Object[]{carManageAudit.getCarmID()});

			carManageAudit.setSiteId(venueInformation.getSiteId());
			carManageAudit.setSiteName(venueInformation.getSiteName());
		}


		return  carManageAudit;
	}



}
