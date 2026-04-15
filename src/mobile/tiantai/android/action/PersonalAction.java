package mobile.tiantai.android.action;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import sun.misc.BASE64Decoder;


import com.opensymphony.xwork2.Action;
import com.tiantai.wfj.bo.TEshopCusCom;
import com.wintone.Adaptor.CipherAdaptor;

import hy.base.action.BaseAction;
import hy.ea.bo.human.Activities;
import hy.ea.bo.human.Staff;
import hy.ea.bo.human.StaffBankAccount;
import hy.ea.bo.human.StaffPapers;
import hy.ea.bo.human.StaffReceiptAddress;
import hy.ea.bo.human.UpLoadFile;
import hy.ea.bo.production.resume.JobWanted;
import hy.ea.service.UploadContentToFileService;
import hy.ea.util.FileUtil;
import hy.ea.util.RandomDatas;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import mobile.tiantai.android.service.TelService;
import mobile.tiantai.android.util.XmlParserX;
import net.sf.json.JSONObject;


/**
 * 移动端个人接口api
 * @author 
 *
 */

@Controller
@Scope("prototype")
public class PersonalAction extends BaseAction<Activities> {
	private static final long serialVersionUID = 1L;
	@Resource
	private UploadContentToFileService contentToFileService;
	
	//云端识别接口
	public static String Url = "http://59.110.113.109:8080/cxfServerX/doAllCardRecon";
	@Resource
	private TelService telService;
	/**
	 * 个人文章，兴趣查询
	 * account手机登录的帐号
	 * flag
	 * pageNumber页数(第几页)	
	 * @return 返回15条文章 
	 */
	
	public String findActivites(){		
		try {
			String account = request.getParameter("account");
			String flag = request.getParameter("flag");//判断文章，兴趣标识
			String hql = "select new Activities(act.activitiesID,act.title,act.describe,act.picture,act.shareLink,act.author,act.releaseTime)"
					+ " from Activities act,TEshopCusCom tec "
					+ " where tec.staffid=act.staffId and tec.account= ? and act.category= ? and act.type= ?"
					+ " order by act.releaseTime desc ";
			String hqlc = "select count(act.activitiesID) from Activities act,TEshopCusCom tec "
					+ "where tec.staffid=act.staffId and tec.account= ? and act.category= ? and act.type= ?";
			if (pageNumber == 0) {
				pageNumber = 1;
			}
			if (account != null && account.length() > 1) {
				pageForm = baseBeanService.getPageForm(pageNumber, 15, hql, hqlc, new Object[] { account, flag, "01" });
				JSONObject jo = new JSONObject();
				List<JSONObject> temlist = new ArrayList<JSONObject>();
				JSONObject temp = null;
				if (pageForm != null) {
					for (BaseBean bean : pageForm.getList()) {
						temp = new JSONObject();
						Activities act = (Activities) bean;
						temp.accumulate("activitiesID", act.getActivitiesID());
						temp.accumulate("title", isNull(act.getTitle()));
						temp.accumulate("describe", isNull(act.getDescribe()));
						temp.accumulate("shareLink", isNull(act.getShareLink()));
						temp.accumulate("name", isNull(act.getAuthor()));
						temp.accumulate("time", act.getShowtime());
						if (act.getPicture() != null && act.getPicture().length() > 1) {
							temp.accumulate("picture", act.getPicture());
						} else {
							temp.accumulate("picture", "");
						}
						temlist.add(temp);
					}
				}
				jo.accumulate("acticles", temlist);
				jo.accumulate("pageNumber", pageForm == null ? 1 : pageForm.getPageNumber());
				jo.accumulate("pageCount", pageForm == null ? 0 : pageForm.getPageCount());
				result = jo;
			} 
		} catch (Exception e) {
			result=new JSONObject().accumulate("ret", "1");
		}
		return Action.SUCCESS;
	}
	/**
	 * 个人文章，兴趣详细信息	
	 * activitiesID	
	 * @return 返回title,shareLink,activitiesID,time,name,图片和描述的txtpath
	 */
	public String ActivitiesDetails(){
		try {
			String activitiesID = request.getParameter("activitiesID");
			String flag = request.getParameter("flag");//判断文章，兴趣标识
			String hql = "select new Activities(act.activitiesID,act.title,act.shareLink,act.txturl,act.author,act.releaseTime)"
					+ " from Activities act"
					+ " where act.activitiesID=?";
			if (activitiesID != null && activitiesID.length() > 1) {
				List<BaseBean> actlist = baseBeanService.getListBeanByHqlAndParams(hql,new Object[] { activitiesID});						
				JSONObject json = new JSONObject();
				if (actlist!= null&&actlist.size()>0) {
					Activities act=(Activities) actlist.get(0);
					json.accumulate("activitiesID", act.getActivitiesID());
					json.accumulate("title", isNull(act.getTitle()));					
					json.accumulate("web", "ea/perinfor/ea_getPersonalActivityData.jspa?activitiesID="+activitiesID+"&status=00&type="+flag);
					json.accumulate("time", isNull(act.getShowtime()));
					json.accumulate("name", isNull(act.getAuthor()));													
					json.accumulate("txtpath", isNull(act.getTxturl()));
					result = json;
				}
			} 
		} catch (Exception e) {
			result=new JSONObject().accumulate("ret", "1");
		}
		return Action.SUCCESS;
	}
	/**
	 * 个人文章，兴趣保存
	 * account手机登录的帐号
	 * title标题	
	 * flag
	 * @return 正常返回0，错误返回1。
	 */
	public String saveActivities(){
		try {
		String account=request.getParameter("account");//手机登录的帐号
		String flag=request.getParameter("flag");//判断文章，兴趣标识
		String title=request.getParameter("title");
		String describe=request.getParameter("describe");
		String shareLink=request.getParameter("shareLink");
		String filepath=request.getParameter("filepath");
		String time=request.getParameter("time");
		String author=request.getParameter("name");
		String [] filepaths=null;
		if(filepath!=null&&filepath.length()>1){
			filepaths=filepath.split("#");
		}
		List<BaseBean> beans=new ArrayList<BaseBean>();
		if(account!=null&&account.length()>1){
			if(title==null){
				result="标题不允许为空";
				return Action.SUCCESS;
			}
			TEshopCusCom tec =(TEshopCusCom)baseBeanService.getBeanByHqlAndParams("from TEshopCusCom where account=? AND logOff=0", new Object[]{account});
			if(tec!=null){
				Activities act=new Activities();
				act.setActivitiesID(serverService.getServerID("activities"));
				act.setTitle(title);
				act.setDescribe(describe);
				act.setShareLink(shareLink);
				act.setCategory(flag);
				act.setType("01");
				act.setStaffId(tec.getStaffid());
				act.setAuthor(author);
				if(time!=null&&time.length()==10){
					act.setReleaseTime(Utilities.getDateFromString(time,"yyyy-MM-dd"));
				}else if(time!=null&&time.length()==19){
					act.setReleaseTime(Utilities.getDateFromString(time,"yyyy-MM-dd HH:mm:ss"));
				}				
				//将描述和图片保存成txt
				StringBuffer txtcontent=new StringBuffer();
				txtcontent.append("<p>");					
				//关联UpLoadFile
				UpLoadFile uf=null;
				if(filepaths!=null){
					if(filepaths.length>3){
						result="上传图片不允许超过3张";
						return Action.SUCCESS;
					}					
					for(int i=0;i<filepaths.length;i++){
						
						txtcontent.append("<img src='");
						txtcontent.append(filepaths[i]);
						txtcontent.append("'/><br/>");
						
						uf=new UpLoadFile();
						uf.setFileID(serverService.getServerID("uploadfile"));
						uf.setParmeterID(act.getActivitiesID());
						uf.setFilepath(filepaths[i]);
						uf.setFiledesc(String.valueOf(i));
						beans.add(uf);
					}
					
					txtcontent.append("</p>");
					txtcontent.append("<p>");
					txtcontent.append(isNull(describe));
					txtcontent.append("</p>");
					String txtpath=saveContentToFile(txtcontent.toString());
					act.setTxturl(txtpath);
					act.setPicture(filepaths[filepaths.length-1]);//保存最后一张图片
				}
				beans.add(act);
			}
		}		
			baseBeanService.executeHqlsByParamsList(beans, null, null);
			result=new JSONObject().accumulate("ret", "0");//成功
		} catch (Exception e) {
			result=new JSONObject().accumulate("ret", "1");
		}
		return Action.SUCCESS;
	}
	/**
	 * 个人文章，兴趣修改
	 * activitiesID
	 * title标题	
	 * @return 正常返回0，错误返回1。
	 */
	public String updateActivities(){
		String activitiesID=request.getParameter("activitiesID");
		String title=request.getParameter("title");
		String describe=request.getParameter("describe");
		String shareLink=request.getParameter("shareLink");
		String filepath=request.getParameter("filepath");
		String [] filepaths=null;
		if(filepath!=null&&filepath.length()>1){
			filepaths=filepath.split("#");
		}	
		List<Object[]> parms = new ArrayList<Object[]>();
		List<Object> p1=new ArrayList<Object>();
		List<Object> p2=new ArrayList<Object>();
		List<String> hqls=new ArrayList<String>();
		List<BaseBean> beans=new ArrayList<BaseBean>();
		String filpath=ServletActionContext.getServletContext().getRealPath("/");
		if(title==null){
			result="标题不允许为空";
			return Action.SUCCESS;
		}
		if(activitiesID!=null&&activitiesID.length()>1){
			StringBuffer hql=new StringBuffer("update Activities set title= ?,");
			p1.add(title);
			if(describe!=null&&describe.length()>1){
				hql.append(" describe= ?,");
				p1.add(describe);
			}
			if(shareLink!=null&&shareLink.length()>1){
				hql.append(" shareLink=?");
				p1.add(shareLink);
			}			
			//修改uploadfile,删除重新保存			
			UpLoadFile uf=null;
			if(filepaths!=null){
				String hqlact="from Activities activitiesID=?";
				Activities act=(Activities) baseBeanService.getBeanByHqlAndParams(hqlact, new Object[]{activitiesID});
				if(act!=null){
					FileUtil.delete(filpath+act.getPicture());//删除本地图片
					FileUtil.delete(filpath+act.getTxturl());//删除本地txt
				}
				StringBuffer hqlup=new StringBuffer("delete UpLoadFile where parmeterID=?");
				hqls.add(hqlup.toString());
				p2.add(activitiesID);
				if(filepaths.length>3){
					result="上传图片不允许超过3张";
					return Action.SUCCESS;
				}					
				for(int i=0;i<filepaths.length;i++){
					uf=new UpLoadFile();
					uf.setFileID(serverService.getServerID("uploadfile"));
					uf.setParmeterID(activitiesID);
					uf.setFilepath(filepaths[i]);
					uf.setFiledesc(String.valueOf(i));
					beans.add(uf);
				}
				hql.append(",picture=?,releaseTime=?");
				p1.add(filepaths[filepaths.length-1]);
				p1.add(new Date());
			}
			hql.append(" where activitiesID=?");
			p1.add(activitiesID);
			hqls.add(hql.toString());
			parms.add(p1.toArray());
			parms.add(p2.toArray());
			try {
				baseBeanService.executeHqlsByParamsList(beans, hqls.toArray(new String[]{}), parms);
				result=new JSONObject().accumulate("ret", "0");
			} catch (Exception e) {
				result=new JSONObject().accumulate("ret", "1");
			}			
		}
		return Action.SUCCESS;
	}
	/**
	 * 个人文章，兴趣删除
	 * activitiesID
	 * @return 正常返回0，错误返回1。
	 */
	public String delActivities(){		
		try {
			String activitiesID = request.getParameter("activitiesID");
			List<String> hqls = new ArrayList<String>();
			List<Object[]> parms = new ArrayList<Object[]>();
			String filpath = ServletActionContext.getServletContext().getRealPath("/");
			List<BaseBean> uplist=new ArrayList<BaseBean>();
			if (activitiesID != null && activitiesID.length() > 1) {
				String hql = "from Activities where activitiesID=?";
				Activities act = (Activities) baseBeanService.getBeanByHqlAndParams(hql, new Object[] { activitiesID });
				if (act != null) {
					FileUtil.delete(filpath + act.getPicture());//删除本地图片
					FileUtil.delete(filpath+act.getTxturl());//删除本地txt
				}
				String hql1="from UpLoadFile where parmeterID=?";
				uplist=baseBeanService.getListBeanByHqlAndParams(hql1, new Object[]{activitiesID});
				if(uplist!=null&&uplist.size()>0){
					for(int i=0;i<uplist.size();i++){
						UpLoadFile up=(UpLoadFile) uplist.get(i);
						if(up!=null&&up.getFilepath().length()>1){
							FileUtil.delete(filpath + up.getFilepath());
						}
					}					
				}
				String hqlup = "delete UpLoadFile where parmeterID=?";
				String hqlact = "delete Activities where activitiesID=?";
				hqls.add(hqlact);
				hqls.add(hqlup);
				parms.add(new Object[] { activitiesID });
				parms.add(new Object[] { activitiesID });
				baseBeanService.executeHqlsByParamsList(null, hqls.toArray(new String[] {}), parms);
				result = new JSONObject().accumulate("ret", "0");
			} 
		} catch (Exception e) {
			result = new JSONObject().accumulate("ret", "1");
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
				+ "/upload_files/activitiesDetails/";
		try {
			contentToFileService.saveContent(id,
					content, path);
		} catch (IOException e) {
			e.printStackTrace();
	
		}	
		return "/upload_files/activitiesDetails/"+id+UploadContentToFileService.suffix;
	}
	
	/**
	 * 上传手机通讯录粉丝
	 * @return
	 */
	public String saveTel(){
		try {
			String user = request.getParameter("user");
			String sccId = request.getParameter("sccId");
			String tel = request.getParameter("tel");
			if(sccId==null||sccId.equals("")){
				result = new JSONObject().accumulate("ret", "2");
				return Action.SUCCESS;
			}
			if(user==null||user.equals("")){
				result = new JSONObject().accumulate("ret", "2");
				return Action.SUCCESS;
			}
			if(tel==null||tel.equals("")){
				result = new JSONObject().accumulate("ret", "2");
				return Action.SUCCESS;
			}
			telService.updateTel(sccId,user, tel);
			result = new JSONObject().accumulate("ret", "0");
		} catch (Exception e) {
			e.printStackTrace();
			result = new JSONObject().accumulate("ret", "1");
		}
		return Action.SUCCESS;
	}
	
	public String getWfjList(){
		try {
			String staffID = request.getParameter("staffID");
			String wfj = request.getParameter("wfj");//为0不是微分金用户    为1则是微分金用户			
			String pagenumber = request.getParameter("pageNumber");
			String parameter = request.getParameter("parameter");//搜索内容 手机号名字查询
			if(staffID==null||staffID.equals("")){
				result = new JSONObject().accumulate("ret", "2");
				return Action.SUCCESS;
			}
			if(wfj==null||wfj.equals("")){
				result = new JSONObject().accumulate("ret", "2");
				return Action.SUCCESS;
			}
			if(pagenumber==null||pagenumber.equals("")){
				result = new JSONObject().accumulate("ret", "2");
				return Action.SUCCESS;
			}
			pageForm = telService.getWfjList(staffID, wfj,pagenumber,parameter);
		    
		    List<JSONObject> list = new ArrayList<JSONObject>();
			List<JSONObject> list1 = new ArrayList<JSONObject>();
			if(pageForm == null){
				result = new JSONObject().accumulate("ret", "3");
				return Action.SUCCESS;
			}else{
				for (int i = 0; i < pageForm.getList().size(); i++) {
					Object[] obj = (Object[]) (Object) pageForm.getList().get(i);
					JSONObject temp = new JSONObject();
					if(obj[2]!=null&&!obj[2].equals("")){
						temp.accumulate("number", isNull(obj[0]));
						temp.accumulate("name", isNull(obj[1]));
						temp.accumulate("headimage", isNull(obj[3]));
						temp.accumulate("sccId", isNull(obj[4]));
						temp.accumulate("state", isNull(obj[5]));
						temp.accumulate("ccompanyid",isNull(obj[6]));
						list.add(temp);
					}else{
						temp.accumulate("number", isNull(obj[0]));
						temp.accumulate("name", isNull(obj[1]));
						list1.add(temp);
					}
				}
				JSONObject temp1 = new JSONObject();
				if(wfj.equals("1")){//是微分金用户
					temp1.accumulate("contact", list);
				}else if(wfj.equals("0")){//不是微分金用户
					temp1.accumulate("contact", list1);
				}
				temp1.accumulate("pageCount", pageForm.getPageCount());
				result = temp1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = new JSONObject().accumulate("ret", "1");
		}
		return Action.SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String getCard(){
		try {
			String staffID = request.getParameter("staffID");
			JSONObject temp = new JSONObject();
			if(staffID==null){
				result = new JSONObject().accumulate("ret", "1");
				return Action.SUCCESS;
			}
			//基本信息
			Staff staff=(Staff)baseBeanService.getBeanByHqlAndParams("from Staff where staffID=?",new Object[]{staffID});
			JSONObject tempbasic = new JSONObject();
			if(staff!=null){
				tempbasic.accumulate("number", isNull(staff.getReference()));
				tempbasic.accumulate("industry", isNull(staff.getIndustryType()));
				tempbasic.accumulate("company", isNull(staff.getWhereCompany()));
				tempbasic.accumulate("post", isNull(staff.getStaffPost()));
			}
			//证件信息
			StaffPapers paper=(StaffPapers)baseBeanService.getBeanByHqlAndParams("from StaffPapers where category=? and staffID=?", new Object[]{"001",staffID});
			JSONObject temppaper = new JSONObject();
			if(paper!=null){
				temppaper.accumulate("positive", isNull(paper.getPositive()));
			}
			//银行卡信息
			List<BaseBean> accountList=baseBeanService.getListBeanByHqlAndParams("from StaffBankAccount where staffID=? and type=?", new Object[]{staffID,"01"});
			JSONObject tempaccount = new JSONObject();
			if(accountList.size()>0){
				StaffBankAccount staffBankAccount = (StaffBankAccount)accountList.get(accountList.size()-1);
				tempaccount.accumulate("bankaccount", isNull(staffBankAccount.getBankAccount()));
				tempaccount.accumulate("bankname", isNull(staffBankAccount.getBankName()));
			}
			//收货地址
			List<BaseBean> addressList=baseBeanService.getListBeanByHqlAndParams("from StaffReceiptAddress where staffID=? and defaults = '00'", new Object[]{staffID});
			JSONObject tempaddress = new JSONObject();
			if(addressList.size()>0){
				StaffReceiptAddress staffReceiptAddress = (StaffReceiptAddress)addressList.get(addressList.size()-1);
				tempaddress.accumulate("receiptconsignee", isNull(staffReceiptAddress.getConsignee()));
				tempaddress.accumulate("receiptnumber", isNull(staffReceiptAddress.getPhoneNumber()));
				tempaddress.accumulate("receiptaddress", isNull(staffReceiptAddress.getProvince()+staffReceiptAddress.getCity()+staffReceiptAddress.getArea()+staffReceiptAddress.getAddress()));
			}
			//奖励风采
			JSONObject tempmien = new JSONObject();
			String miensql="select mate.route from dtMienStyle mien  left join dtMaterial mate" +
					" on mien.materialid=mate.materialid where mien.relateid=? and mate.status=? and type=?";
			List<String> mienlist=baseBeanService.getListBeanBySqlAndParams(miensql, new Object[]{staffID,"00","00"});
			if(mienlist.size()>0){
				if (mienlist.size()>=2) {
					for (int i = 0; i < 2; i++) {
						String route = (String) mienlist.get(i);
						tempmien.accumulate("route"+i, isNull(route));
					}
				}else{
					for (int i = 0; i < mienlist.size(); i++) {
						String route = (String)mienlist.get(i);
						tempmien.accumulate("route"+(i), isNull(route));
								
					}
				}
			}
			//求职简历
			JSONObject tempjob = new JSONObject();
			JobWanted jobWanted=(JobWanted)baseBeanService.getBeanByHqlAndParams("from JobWanted where staffID=?", new Object[]{staffID});
			if(jobWanted!=null){
				tempjob.accumulate("position", isNull(jobWanted.getPosition()));
				tempjob.accumulate("salary", isNull(jobWanted.getSalary()));
				tempjob.accumulate("region", isNull(jobWanted.getRegion()));
			}
			//个人论坛
			List<JSONObject> listluntan = new ArrayList<JSONObject>();
			String hql="from Activities where staffId=? and type=? and category=? order by releaseTime desc";
			List<BaseBean> list=baseBeanService.getListBeanByHqlAndParams(hql, new Object[]{staffID,"01","01"});
			if(list.size()>0){
				if(list.size()>=3){
					for (int i = 0; i < 3; i++) {
						JSONObject templuntan = new JSONObject();
						Activities lutan = (Activities)list.get(i);
						templuntan.accumulate("title", isNull(lutan.getTitle()));
						templuntan.accumulate("describe", isNull(lutan.getDescribe()));
						templuntan.accumulate("activitiesid", isNull(lutan.getActivitiesID()));
						listluntan.add(templuntan);
					}
				}else{
					for (int i = 0; i < list.size(); i++) {
						JSONObject templuntan = new JSONObject();
						Activities lutan = (Activities)list.get(i);
						templuntan.accumulate("title", isNull(lutan.getTitle()));
						templuntan.accumulate("picture", isNull(lutan.getPicture()));
						templuntan.accumulate("describe", isNull(lutan.getDescribe()));
						templuntan.accumulate("activitiesid", isNull(lutan.getActivitiesID()));
						listluntan.add(templuntan);
					}
				}
			}
			//个人文章
			List<JSONObject> listarticle = new ArrayList<JSONObject>();
			String hqlarticle="from Activities where staffId=? and type=? and category=? order by releaseTime desc";
			List<BaseBean> list1=baseBeanService.getListBeanByHqlAndParams(hqlarticle, new Object[]{staffID,"01","02"});
			if(list1.size()>0){
				if(list1.size()>=3){
					for (int i = 0; i < 3; i++) {
						JSONObject temparticle = new JSONObject();
						Activities article = (Activities)list1.get(i);
						temparticle.accumulate("title", isNull(article.getTitle()));
						temparticle.accumulate("picture", isNull(article.getPicture()));
						temparticle.accumulate("describe", isNull(article.getDescribe()));
						temparticle.accumulate("activitiesid", isNull(article.getActivitiesID()));
						listarticle.add(temparticle);
					}
				}else{
					for (int i = 0; i < list1.size(); i++) {
						JSONObject temparticle = new JSONObject();
						Activities article = (Activities)list1.get(i);
						temparticle.accumulate("title", isNull(article.getTitle()));
						temparticle.accumulate("picture", isNull(article.getPicture()));
						temparticle.accumulate("describe", isNull(article.getDescribe()));
						temparticle.accumulate("activitiesid", isNull(article.getActivitiesID()));
						listarticle.add(temparticle);
					}
				}
			}
			
			temp.accumulate("basic", tempbasic);
			temp.accumulate("paper", temppaper);
			temp.accumulate("bank", tempaccount);
			temp.accumulate("address", tempaddress);
			temp.accumulate("mien", tempmien);
			temp.accumulate("job", tempjob);
			temp.accumulate("luntan", listluntan);
			temp.accumulate("news", listarticle);
			result = temp;
		} catch (Exception e) {
			result = new JSONObject().accumulate("ret", "2");
			e.printStackTrace();
		}
		return Action.SUCCESS;
	}
	
	//解析base64格式的图片
	public String image(String imgsrc,String photopath){
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
    	String dir = path+photopath;
        File fileLocation = new File(dir);
        //判断上传路径是否存在，如果不存在就创建
        if(!fileLocation.exists()) {
            boolean isCreated  = fileLocation.mkdirs();
            if(!isCreated) {
                return null;
            }
        }
      //重命名
		String upName = UUID.randomUUID().toString()+System.currentTimeMillis()+".jpg";
        FileOutputStream out;
        String iconBase64 = imgsrc;
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
            out = new FileOutputStream(dir+"\\"+upName);
            out.write(buffer);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
		return photopath+"\\"+upName;
	}
	
	//云端识别接口
	public String distinguish(){
		//有一个移动端上传图片的过程  返回路径
		try {
			String path = ServletActionContext.getServletContext().getRealPath("");
	 		//String pic = request.getParameter("pic"); 图片路径
	 		String pic = path+"\\upload_files\\gooddesign\\华双虎.jpg";
	 		String imatype = pic.substring(pic.length()-3,pic.length());
	 		//String account = request.getParameter("account"); 随意
	 		//String type = request.getParameter("type");  接口需要传递的证件类别
	 		String account = "18225691607";
	 		String type = "2";
	 		String back = client(pic,account,type,imatype);//String 类型的xml数据
	 		JSONObject temp = XmlParser(back);
	 		result = temp;
		} catch (Exception e) {
			result=new JSONObject().accumulate("ret", "1");
		}
		return Action.SUCCESS;
	}
	
	public String client(String input,String account,String type,String imgtype){
		CipherAdaptor clientAdaptor = new CipherAdaptor();//详细说明见单用户证件识别服务系统客户端开发手册.doc文档中2.1.1 PC客户端识别服务接口
 		String strsrc = clientAdaptor.setRecgnPlainParam(input,type,"",null);  
 		JSONObject json = new JSONObject();//2.封装json对象
	    String paramdata=strsrc;  
	    String signdata="NULL";
		json.put("username", account);  
		json.put("paramdata", paramdata);  
		json.put("signdata",signdata);
		json.put("imgtype",imgtype);  
		String resultback=doPost(Url, json);
		String[] encData = resultback.split("==@@");
		return encData[0];
	}
	
	//解析云端识别接口返回的String类型的xml数据
	public JSONObject XmlParser(String result) {
		JSONObject temp = new JSONObject();
		ByteArrayInputStream is = null;
		try {
			is = new ByteArrayInputStream(result.getBytes("UTF-8"));
			XmlParserX xmlParser = new XmlParserX(is);
			if (xmlParser.parse_status) {
				if (xmlParser.length > 0) {
					System.out.println("证件类型：" + xmlParser.recon_status);
					temp.accumulate("type", xmlParser.recon_status);
					for (int i = 0; i < xmlParser.length; i++) {
						String name = xmlParser.fieldname[i].replaceAll("\"", "&quot;").replaceAll("&",
								"&amp;").replaceAll("<", "&lt;");
						String value = xmlParser.fieldvalue[i].replaceAll("\"", "&quot;").replaceAll("&",
								"&amp;").replaceAll("<", "&lt;");
						if(name.equals("头像")){
							String photopath= "upload_files\\gooddesign\\"+Utilities.getDateString(new Date(),"yyyy-MM-dd");
							value = image(value,photopath);
						}
						temp.accumulate(name, value);
					}
				} else {
					 System.out.println("错误编码：" + xmlParser.recon_status);
					 System.out.println("错误描述：" + xmlParser.recon_error);
				}
			} else {
				 System.out.println("解析xml出错：" + xmlParser.parse_error);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public static String doPost(String url,JSONObject json){
		String result="";
		CloseableHttpClient client = HttpClientBuilder.create().build();     //1.创建httpclient对象
		HttpPost post = new HttpPost(url);                                   //2.通过url创建post方法
		try {
			StringEntity entity = new StringEntity(json.toString());         //3.将传入的json封装成实体，并压入post方法
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");//发送json数据需要设置contentType
			post.setEntity(entity);
			CloseableHttpResponse response = client.execute(post);            //4.执行post方法，返回HttpResponse的对象
			if(response.getStatusLine().getStatusCode() == 200){         
				result = EntityUtils.toString(response.getEntity(),"UTF-8");  //5.如果返回结果状态码为200，则读取响应实体response对象的实体内容，并封装成String对象返回
			}
			try{                                         
				HttpEntity e = response.getEntity();                          //6.关闭资源
				if(e != null){  
					InputStream instream = e.getContent();  
					instream.close();  
				}  
			} catch (Exception e) {
				e.printStackTrace();  
			}finally{  
				response.close();  
			}	 
		} catch (Exception e) {
			e.printStackTrace();  
		}
		return result;
	}
}
