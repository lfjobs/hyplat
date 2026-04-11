package hy.ea.office.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.CAccount;
import hy.ea.bo.CLogBook;
import hy.ea.bo.human.COrganization;
import hy.ea.bo.office.EnterpriseVideo;
import hy.ea.service.CLogBookService;
import hy.ea.service.ShowExcelService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.DateUtil;
import hy.ea.util.FileUtil;
import hy.ea.util.Utilities;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;

/**
 * 企业录像管理
 * @author Administrator
 *
 */
public class EnterpriseVideoAction {
	private static final Logger logger = LoggerFactory.getLogger(EnterpriseVideoAction.class);
	
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private ServerService serverService;  
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private CLogBookService logBookService; 
	@Resource
	private ShowExcelService excelService;
	
	private EnterpriseVideo enterpriseVideo;
	private String parameter; 
	private PageForm pageForm;
	private List<COrganization> orgnizationList;
	private String search; 
	private int pageNumber;
	 
	private String downLoadPath;
	private InputStream excelStream;
	 
	private String sDate;
	private String eDate;
	private String result;
	
	// 保存企业视频信息
	public String saveEnterpriseVideo() {
		Map<String, Object> map = ActionContext.getContext().getSession();
		CAccount account = (CAccount) map.get("account");
		String companyID = account.getCompanyID();
		String organizationID = (String) map.get("organizationID");
		String realPath = ServletActionContext.getRequest().getRealPath("/");
		String ext = enterpriseVideo.getVideoPath().substring(
				enterpriseVideo.getVideoPath().lastIndexOf("."));
		if (ext.equals(".flv")) {
			// 保存其他数据
			if (null == enterpriseVideo.getEnterpriseVideoID()
					|| "".equals(enterpriseVideo.getEnterpriseVideoID())) {
				enterpriseVideo.setEnterpriseVideoID(serverService
						.getServerID("enterprisevideo"));
				parameter = "添加企业录像(名称:" + enterpriseVideo.getEnName() + ")";
			} else {
				String hql = "from EnterpriseVideo where companyID = ? and  enterpriseVideoID = ? ";
				EnterpriseVideo q0 = (EnterpriseVideo) baseBeanService
						.getBeanByHqlAndParams(hql, new Object[] {
								account.getCompanyID(),
								enterpriseVideo.getEnterpriseVideoID() });
				parameter = "修改企业录像(名称:" + q0.getEnName() + ")";
			}
			List<BaseBean> beans = new ArrayList<BaseBean>();
			enterpriseVideo.setCompanyID(companyID);
			enterpriseVideo.setOrganizationID(organizationID);
			CLogBook logbook = logBookService.saveCLogBook(organizationID,
					parameter, account);
			beans.add(enterpriseVideo);
			beans.add(logbook);
			baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, null,
					null);
			return "success";
		}
		// 自定义方式产生文件名
		String serialName = String.valueOf(System.currentTimeMillis());
		String filepath = "/upload_files/" + account.getCompanyID()
				+ "/office/enterprisevideo/"
				+ DateUtil.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT) + "/"
				+ serialName + ".flv";
		String codcFilePath = realPath + filepath; // 设置转换为flv格式后文件的保存路径
		String mediaPicPath = realPath + "upload_files/"
				+ account.getCompanyID() + "/office/enterprisevideo/"
				+ DateUtil.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)
				+ "/images" + File.separator + serialName + ".jpg"; // 设置上传视频截图的保存路径

		// 获取配置的转换工具（ffmpeg.exe）的存放路径
		String ffmpegPath = realPath + "js/cabs/video/ffmpeg.exe";

		String srcFile = realPath + "/" + enterpriseVideo.getVideoPath();

		enterpriseVideo.setVideoPath(filepath);

		// 转码

		try {
			boolean flag = executeCodecs(ffmpegPath, srcFile, codcFilePath,
					mediaPicPath);
			if (flag) {
				// 保存其他数据
				if (null == enterpriseVideo.getEnterpriseVideoID()
						|| "".equals(enterpriseVideo.getEnterpriseVideoID())) {
					enterpriseVideo.setEnterpriseVideoID(serverService
							.getServerID("enterprisevideo"));
					parameter = "添加企业录像(名称:" + enterpriseVideo.getEnName()
							+ ")";
				} else {
					String hql = "from EnterpriseVideo where companyID = ? and  enterpriseVideoID = ? ";
					EnterpriseVideo q0 = (EnterpriseVideo) baseBeanService
							.getBeanByHqlAndParams(hql, new Object[] {
									account.getCompanyID(),
									enterpriseVideo.getEnterpriseVideoID() });
					parameter = "修改企业录像(名称:" + q0.getEnName() + ")";
				}
				List<BaseBean> beans = new ArrayList<BaseBean>();
				enterpriseVideo.setCompanyID(companyID);
				enterpriseVideo.setOrganizationID(organizationID);
				CLogBook logbook = logBookService.saveCLogBook(organizationID,
						parameter, account);
				beans.add(enterpriseVideo);
				beans.add(logbook);
				baseBeanService.saveBeansListAndexecuteHqlsByParams(beans,
						null, null);
			}

		} catch (Exception e) {
			logger.error("操作异常", e);
		}

		return "success";

	}
	//删除企业录像
	 public String delEnterpriseVideo()
	    {
		    Map<String, Object> session = ActionContext.getContext().getSession();
			String organizationID=(String) session.get("organizationID"); 
			
			CAccount account = (CAccount) session.get("account");
		    Object[] params = {account.getCompanyID(),enterpriseVideo.getEnterpriseVideoID()};
		    String hql2="from EnterpriseVideo where companyID=?  and enterpriseVideoID = ? ";
		    EnterpriseVideo q0=(EnterpriseVideo) baseBeanService.getBeanByHqlAndParams(hql2, params);
		    
		    CLogBook  logbook=logBookService.saveCLogBook(organizationID, "删除企业录像(名称:"+q0.getEnName()+")", account);
	    	String hql="delete from EnterpriseVideo where companyID=?  and enterpriseVideoID = ?";
	    	List<BaseBean> beans=new ArrayList<BaseBean>();
	    	beans.add(logbook);
	    	baseBeanService.saveBeansListAndexecuteHqlsByParams(beans, new String[]{hql}, params);
		    return "success";
	    }
	 
	 //根据条件查询企业录像列表
		public String toSearch() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("tablesearch", enterpriseVideo);
			return getEnterpriseVideoList();
		}
	 //企业录像列表
		public String getEnterpriseVideoList() {
		    pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm.getPageNumber() : 1), (pageNumber==0?10:pageNumber), getList());
			return "enterprisevideolist";	
		}
		private DetachedCriteria getList() {
			Map<String, Object> session = ActionContext.getContext().getSession();
			CAccount account = (CAccount) session.get("account");
			String organizationID = (String) session.get("organizationID");
			String companyID = account.getCompanyID(); 
			
			DetachedCriteria dc = DetachedCriteria.forClass(EnterpriseVideo.class);
			dc.add(Restrictions.eq("companyID", companyID));
			dc.add(Restrictions.eq("organizationID", organizationID));
			if (search != null && search.equals("search")) {
				enterpriseVideo = (EnterpriseVideo) session.get("tablesearch");
				if(null!=enterpriseVideo.getEnName()&&!"".equals(enterpriseVideo.getEnName()))
				{
					dc.add(Restrictions.like("enName", enterpriseVideo.getEnName(), MatchMode.ANYWHERE));
				}  
				if(null!=sDate&&!"".equals(sDate)&&null!=eDate&&!"".equals(eDate)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						dc.add(Restrictions.between("enDate", sdf.parse(sDate), sdf.parse(eDate)));
					} catch (ParseException e) {
						logger.error("操作异常", e);
					}
				} 
			} 
			return dc;
		}
		
		// 导出企业录像
		public String showEnterpriseVideoExcel() {
			excelStream = excelService.showExcel(EnterpriseVideo.columnHeadings(), baseBeanService.getListByDC(getList()));
			CAccount account = (CAccount) ActionContext.getContext().getSession().get("account");
			CLogBook  logbook=logBookService.saveCLogBook(null,"企业录像列表", account);
			baseBeanService.update(logbook);
			return "showexcel";
		}	 

		//下载文件
		public void downFile(){ 
			FileUtil fu = new FileUtil();
			try {
				fu.downFile(downLoadPath);
			} catch (IOException e) {
				logger.error("操作异常", e);
			}
		} 
		
		
		
		
		
	    /**
	     * 视频转码
	     * @param ffmpegPath    转码工具的存放路径
	     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
	     * @param codcFilePath    格式转换后的的文件保存路径
	     * @param mediaPicPath    截图保存路径
	     * @return
	     * @throws Exception
	     */
	    public boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath,
	            String mediaPicPath) throws Exception {
	    	String ext =upFilePath.substring(upFilePath.lastIndexOf("."));
	    	 List<String> convert = new ArrayList<String>();
	    	if(!ext.equals(".mp4")&&!ext.equals(".flv")){
	        // 创建一个List集合来保存转换视频文件为flv格式的命令
	        convert.add(ffmpegPath); // 添加转换工具路径
	        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
	        convert.add(upFilePath); // 添加要转换格式的视频文件的路径
	        convert.add("-qscale");     //指定转换的质量
	        convert.add("6");
	        convert.add("-ab");        //设置音频码率
	        convert.add("64");
	        convert.add("-ac");        //设置声道数
	        convert.add("2");
	        convert.add("-ar");        //设置声音的采样频率
	        convert.add("22050");
	        convert.add("-r");        //设置帧频
	        convert.add("24");
	        convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
	        convert.add(codcFilePath);
	    	}
	        // 创建一个List集合来保存从视频中截取图片的命令
	        List<String> cutpic = new ArrayList<String>();
	        cutpic.add(ffmpegPath);
	        cutpic.add("-i");
	        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
	        cutpic.add("-y");
	        cutpic.add("-f");
	        cutpic.add("image2");
	        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
	        cutpic.add("2"); // 添加起始时间为第17秒
	        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
	        cutpic.add("0.001"); // 添加持续时间为1毫秒
	        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
	        cutpic.add("350*240"); // 添加截取的图片大小为350*240
	        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

	        boolean mark = true;
	        ProcessBuilder builder = new ProcessBuilder();
	        try {
	        	builder.command(cutpic);
		            builder.redirectErrorStream(true);
		            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
		            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
		            Process process = builder.start();
		            process.waitFor();
		            logger.info("转换完毕图片");
		            process.destroy();
	        	
	        	if(!ext.equals(".mp4")&&!ext.equals(".flv")){
	            builder.command(convert);
	            builder.redirectErrorStream(true);
	            builder.start();
//	            Process process2 = builder.start();
//	            process2.waitFor();
//	            logger.info("转换完毕视频");
//	            process2.destroy();
	           
	        	}
	          
	        } catch (Exception e) {
	            mark = false;
	            logger.info("值：{}", e);
	            logger.error("操作异常", e);
	          
	        }
	        return mark;
	    }
	    
	    /**
	     * 
	     * 
	     * ajax调用转码
	     * @return
	     */
	    public String ajaxCode(){
	    	
			Map<String, Object> map = ActionContext.getContext().getSession();
			CAccount account = (CAccount) map.get("account");
			String realPath = ServletActionContext.getRequest().getRealPath("/");
	    	
	    	// 自定义方式产生文件名
			String serialName = String.valueOf(System.currentTimeMillis());
			String filepath = "upload_files\\office\\filemanage\\"
					+ serialName + ".flv";
			String codcFilePath = realPath + filepath; // 设置转换为flv格式后文件的保存路径
			String mediaPicPath = realPath + "upload_files\\office\\filemanage\\"+ serialName + ".jpg"; // 设置上传视频截图的保存路径
			// 获取配置的转换工具（ffmpeg.exe）的存放路径
			String ffmpegPath = realPath + "js\\cabs\\video\\ffmpeg.exe";

			String srcFile = realPath + "\\" + enterpriseVideo.getVideoPath();
			try {

				boolean flag = executeCodecs(ffmpegPath, srcFile, codcFilePath,
						mediaPicPath);
				Map<String,Object> mapj = new HashMap<String, Object>();
				if(flag){
					String ext =srcFile.substring(srcFile.lastIndexOf("."));
			    	
			    	if(!ext.equals(".mp4")&&!ext.equals(".flv")){
					  mapj.put("filepath","upload_files/office/filemanage/"
							+ serialName + ".flv");
			    	}else{
			    		mapj.put("filepath",enterpriseVideo.getVideoPath());
			    	}
					mapj.put("imagepath","upload_files/office/filemanage/" + serialName + ".jpg");
					mapj.put("result","suc");
				}else{
					mapj.put("result","fail");
				}	
				
				JSONObject jo = JSONObject.fromObject(mapj);
				this.result = jo.toString();
				
			} catch (Exception e) {
				
				logger.error("操作异常", e);
			}
			
	    	
	    	return "success";
	    }
	    
	    
	    
		public EnterpriseVideo getEnterpriseVideo() {
			return enterpriseVideo;
		}
		public void setEnterpriseVideo(EnterpriseVideo enterpriseVideo) {
			this.enterpriseVideo = enterpriseVideo;
		}
		public String getParameter() {
			return parameter;
		}
		public void setParameter(String parameter) {
			this.parameter = parameter;
		}
		public PageForm getPageForm() {
			return pageForm;
		}
		public void setPageForm(PageForm pageForm) {
			this.pageForm = pageForm;
		}
		public String getSearch() {
			return search;
		}
		public void setSearch(String search) {
			this.search = search;
		}
		public int getPageNumber() {
			return pageNumber;
		}
		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}
		public InputStream getExcelStream() {
			return excelStream;
		}
		public void setExcelStream(InputStream excelStream) {
			this.excelStream = excelStream;
		}
		public String getDownLoadPath() {
			return downLoadPath;
		}
		public void setDownLoadPath(String downLoadPath) {
			this.downLoadPath = downLoadPath;
		}
		public String getSDate() {
			return sDate;
		}
		public void setSDate(String date) {
			sDate = date;
		}
		public String getEDate() {
			return eDate;
		}
		public void setEDate(String date) {
			eDate = date;
		}
		public List<COrganization> getOrgnizationList() {
			return orgnizationList;
		}
		public void setOrgnizationList(List<COrganization> orgnizationList) {
			this.orgnizationList = orgnizationList;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		} 
		
}
