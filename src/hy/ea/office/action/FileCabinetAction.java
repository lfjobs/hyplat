package hy.ea.office.action;

import hy.ea.bo.CAccount;
import hy.ea.bo.Company;
import hy.ea.bo.human.Staff;
import hy.ea.bo.office.FileCabinet;
import hy.ea.bo.office.FileCabinet.FileSearchInfo;
import hy.ea.bo.office.FileCabinet.FileUploadInfo;

import hy.ea.bo.office.FileFolder;
import hy.ea.bo.office.FileUpload;

import hy.ea.office.service.FileManagerService;
import hy.ea.service.UpLoadFileService;
import hy.ea.util.RandomDatas;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;
import hy.plat.service.BaseBeanService;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
/**
 * 文件柜管理
 */
public class FileCabinetAction {
	@Resource
	private BaseBeanService baseBeanService;
	@Resource
	private UpLoadFileService fileService;
	@Resource
	private FileManagerService fileManagerService;
	private FileCabinet fileCabinet;
	private FileFolder fileFolder;
	private FileUpload fileUpload;
	private FileSearchInfo fileSearchInfo;
	private FileUploadInfo fileUploadInfo;
	private List<BaseBean> listFolder;
	private List<BaseBean> listUpload;
	private String fileCabinetKey;
	private PageForm pageForm;
	private String result;
	private String search;
	private int pageNumber;
	private String fileCabinetIdmark;
	private String fileFolderIdmark;
	private String resultString;



	// 站内搜索
	public String toSearchGlobal() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", fileSearchInfo);
		return getListSearchResultList();
	}

	public String getListSearchResultList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String staffId = account.getStaffID();
		fileSearchInfo = (FileSearchInfo) session.get("tablesearch");
		String hql1 = null;
		String hql2 = null;
		if (!fileSearchInfo.getName().equalsIgnoreCase("")) {
			hql1 = "from FileFolder where companyID= ? and createrId = ? and fileFolderName like"+"'%"+fileSearchInfo.getName()+"%'";
			hql2 = "from FileUpload where companyID= ? and uploadPersonId = ? and fileUploadName like"+"'%"+fileSearchInfo.getName()+"%'";
		
		} else {
			hql1 = "from FileFolder where companyID= ? and createrId = ?";
			hql2 = "from FileUpload where companyID= ? and uploadPersonId = ?";
		}
      if(fileSearchInfo.getSearchType().equalsIgnoreCase("Folder")){
    	  pageForm = baseBeanService.getPageForm(
  				(null != pageForm ? pageForm.getPageNumber() : 1),
  				(pageNumber == 0 ? 5 : pageNumber), hql1,new Object[]{companyID,staffId});
    	 
      }else{
    	  pageForm = baseBeanService.getPageForm(
    				(null != pageForm ? pageForm.getPageNumber() : 1),
    				(pageNumber == 0 ? 5 : pageNumber), hql2,new Object[]{companyID,staffId});
      }
		return "globalSearchlist";
	}

	// 查询文件柜
	public String toSearch() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", fileCabinet);
		return getListForFileCabinet();
	}

	private DetachedCriteria getList() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		String companyID = account.getCompanyID();
		String staffId = account.getStaffID();
		DetachedCriteria dc = DetachedCriteria.forClass(FileCabinet.class);
		dc.add(Restrictions.eq("companyId", companyID));
		dc.add(Restrictions.eq("createrId", staffId));
		dc.addOrder(Order.desc("createTime"));

		if (search != null && search.equalsIgnoreCase("search")) {
			fileCabinet = (FileCabinet) session.get("tablesearch");
			if (!"".equalsIgnoreCase(fileCabinet.getFileCabinetName())) {
				dc.add(Restrictions.like("fileCabinetName", fileCabinet
						.getFileCabinetName(), MatchMode.ANYWHERE));
			}
		}

		return dc;
	}

	/**
	 * 得到文件柜列表根据公司，人员(最开始显示文件柜列表)
	 * 
	 * @return
	 */
	public String getListForFileCabinet() {
		pageForm = baseBeanService.getPageFormByDC((null != pageForm ? pageForm
				.getPageNumber() : 1), (pageNumber == 0 ? 5 : pageNumber),
				getList());
		return "filecabinetlist";
	}

	/**
	 * 创建文件柜or修改文件柜
	 * 
	 * @return
	 */
	public String newFileCabinet() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		if (account == null) {

			return "not_login";
		}
		if (fileCabinet.getFileCabinetId() != null) {
			String hql = "from FileCabinet where fileCabinetId = ?";
			FileCabinet fc = (FileCabinet) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { fileCabinet
							.getFileCabinetId() });
			fc.setFileCabinetName(fileCabinet.getFileCabinetName());
			fc.setDescriptor(fileCabinet.getDescriptor());
			baseBeanService.update(fc);
			return getListForFileCabinet();
		}
		String companyId = account.getCompanyID();
		String staffId = account.getStaffID();
		String hql = "from Staff where staffId = ?";
		String hqlc = "from Company where companyId = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { staffId });
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlc,
				new Object[] { companyId });
		String fileCabinetId = "ca_" +RandomDatas.getUUID();
		String staffName = staff.getStaffName();
		fileCabinet.setCompanyId(companyId);
		fileCabinet.setCreaterId(staffId);
		fileCabinet.setCreaterName(staffName);
		fileCabinet.setCompanyName(company.getCompanyName());
		fileCabinet.setFileCabinetId(fileCabinetId);
		fileCabinet.setFileFolderNumber("0");
		fileCabinet.setFileNumber("0");
		fileCabinet.setUsedSpace("0kb");
		Date d = (Date) Calendar.getInstance().getTime();

		fileCabinet.setCreateTime(d);
		baseBeanService.save(fileCabinet);

		return getListForFileCabinet();
	}

	/**
	 * 得到某个文件柜中文件或者文件夹的列表
	 * 
	 * @return
	 */
	public String toSearchFolder() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.put("tablesearch", fileCabinet);
		return getListForFileCabinet();
	}
     /**
		 * 得到文件柜列表根据公司，人员
		 * 
		 * @return
		 */
	// 得到文件夹以及文件列表
	public String getFileOfFolderList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileCabinetId = request.getParameter("fileCabinetId");
		if (fileCabinetId == null) {
			if (fileCabinetIdmark == null
					|| fileCabinetIdmark.equalsIgnoreCase("")) {
				fileCabinetId = (String) request.getSession().getAttribute(
						"fileCabinetId");
			} else {
				fileCabinetId = fileCabinetIdmark;
			}
		}
		String hql = "from FileCabinet where fileCabinetId = ?";
		FileCabinet fileCabinet = (FileCabinet) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { fileCabinetId });

		String sql = "select f.filefolderid, f.filefoldername ,'F',f.key from dt_oa_file_folder f where f.filecabinetkey= ?"
				+ "union all select u.fileuploadid,u.fileuploadname,u.iconPath, u.fileuploadpath from dt_oa_file_upload u where u.filecabinetkey= ? ";

		String sqlcount = "select count(*) from(select f.filefolderid, f.filefoldername,'f',f.key from dt_oa_file_folder f where f.filecabinetkey=?"
				+ "union all select u.fileuploadid,u.fileuploadname,u.iconPath,u.fileuploadpath from dt_oa_file_upload u where u.filecabinetkey=?)";

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, sqlcount,
				new Object[] { fileCabinet.getKey(), fileCabinet.getKey() });
		request.getSession().setAttribute("fileCabinetId",
				fileCabinet.getFileCabinetId());
		request.setAttribute("fileCabinetId", fileCabinet.getFileCabinetId());
		request.setAttribute("fileCabinetKey", fileCabinet.getKey());
		request.setAttribute("fileCabinetName", fileCabinet
				.getFileCabinetName());
		return "fileFolderlist";
	}

	// 创建文件夹
	public String newFileFolder() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileCabinetKey = request.getParameter("fileCabinetKey");
		FileCabinet fileCabinet = (FileCabinet) baseBeanService.getBeanByKey(
				FileCabinet.class, fileCabinetKey);
		fileCabinet.setFileFolderNumber(String.valueOf(Integer
				.parseInt(fileCabinet.getFileFolderNumber()) + 1));
		baseBeanService.update(fileCabinet);
		String companyId = account.getCompanyID();
		String staffId = account.getStaffID();
		String hql = "from Company where companyId = ?";
		String hqlc = "from Staff where staffId = ?";
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { companyId });
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqlc,
				new Object[] { staffId });
		String companyName = company.getCompanyName();
		String staffName = staff.getStaffName();
		String fileFolderId = "fo_" +RandomDatas.getUUID();
		fileFolder.setCompanyId(companyId);
		fileFolder.setCreaterId(staffId);
		fileFolder.setCreaterName(staffName);
		fileFolder.setCompanyName(companyName);
		fileFolder.setFileFolderId(fileFolderId);
		fileFolder.setFileNumber("0");
		fileFolder.setUsedSpace("0kb");
		fileFolder.setFileCabinet(fileCabinet);
		fileFolder.setSaveCabinet(fileCabinet.getFileCabinetName());
		fileFolder.setTypeFolder("Folder");
		Date d = (Date) Calendar.getInstance().getTime();
		fileFolder.setCreateTime(d);
		fileFolder.setFileCabinet(fileCabinet);
		baseBeanService.save(fileFolder);
		fileCabinetIdmark = fileCabinet.getFileCabinetId();

		return getFileOfFolderList();
	}

	// 上传文件//将记录保存到3个表中FileUpload、FileFolder 、 FileCabinet
	public String uploadFile() {		
		String fileCabinetIdcurrent = fileUploadInfo.getFileCabinetId();
		String fileFolderIdcurrent = fileUploadInfo.getFileFolderId();// 可能为空
		String uploadMode = fileUploadInfo.getUploadMode();
		String fileSize = fileUploadInfo.getFileSize();
		String[] arrayfilesize = fileSize.split(",");
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String staffID = account.getStaffID();
		String companyID = account.getCompanyID();
		String hqls = "from Staff where staffId = ?";
		String hqlc = "from Company where companyId = ?";
		Staff staff = (Staff) baseBeanService.getBeanByHqlAndParams(hqls,
				new Object[] { staffID });
		Company company = (Company) baseBeanService.getBeanByHqlAndParams(hqlc,
				new Object[] { companyID });
		String path = ServletActionContext.getRequest().getSession()
				.getServletContext().getRealPath("/");
		File[] sourcePath = fileUpload.getSourcePath();
		String[] sourcePathFileName = fileUpload.getSourcePathFileName();
		String fileUploadPath = "";
		try {
			String[] targetFileNames = fileService.mutiFileUpload(path,
					sourcePathFileName, sourcePath, companyID, "upload_files/office/fileCabinet");
			String[] Iconpath = fileManagerService.getSystemIcon(path, companyID,targetFileNames);
			for (int i = 0; i < sourcePathFileName.length; i++) {
				FileUpload newFileUpload = new FileUpload();

				fileUploadPath = "upload_files\\\\office\\\\fileCabinet\\\\" + companyID + "\\\\"
						+ targetFileNames[i];
				newFileUpload.setFileUploadPath(fileUploadPath);
				newFileUpload.setFileUploadName(sourcePathFileName[i]);
				String fileUploadId = "fi_" +RandomDatas.getUUID();
				newFileUpload.setUploadPersonId(staffID);
				newFileUpload.setIconPath(Iconpath[i]);
				String dd = fileUpload.getSaveDirectory().substring(0, 2);
				if (dd != "" && "fo".equalsIgnoreCase(dd)) {// 存储位置为文件夹
					String hql1 = "from FileFolder where fileFolderId = ?";
					FileFolder ff = (FileFolder) baseBeanService
							.getBeanByHqlAndParams(hql1,
									new Object[] { fileUpload
											.getSaveDirectory() });
					ff.setFileNumber(String.valueOf(Integer.parseInt(ff// 增加文件夹中的文件数量
							.getFileNumber()) + 1));
					ff.setUsedSpace(String.valueOf(Integer.parseInt(ff// 增加文件夹的占用空间
							.getUsedSpace().substring(0,
									ff.getUsedSpace().length() - 2))
							+ Integer.parseInt(arrayfilesize[i]))
							+ "kb");
					baseBeanService.update(ff);
					FileCabinet fileCabinet = ff.getFileCabinet();
					fileCabinet.setFileNumber(String.valueOf(Integer // 增加文件柜中的文件数量
							.parseInt(fileCabinet.getFileNumber()) + 1));
					fileCabinet.setUsedSpace(String.valueOf(Integer // 增加文件柜的占用空间
							.parseInt(fileCabinet.getUsedSpace().substring(0,
									fileCabinet.getUsedSpace().length() - 2))
							+ Integer.parseInt(arrayfilesize[i]))
							+ "kb");
					baseBeanService.update(fileCabinet);
					newFileUpload.setSaveDirectory(fileCabinet
							.getFileCabinetName()
							+ "/" + ff.getFileFolderName());
					newFileUpload.setFileFolder(ff);

				} else {
					String hql3 = "from FileCabinet where fileCabinetId = ?";
					FileCabinet fc = (FileCabinet) baseBeanService
							.getBeanByHqlAndParams(hql3,
									new Object[] { fileUpload
											.getSaveDirectory() });
					newFileUpload.setSaveDirectory(fc.getFileCabinetName());
					newFileUpload.setFileCabinet(fc);
					fc.setFileNumber(String.valueOf(Integer.parseInt(fc
							.getFileNumber()) + 1));
					fc.setUsedSpace(String.valueOf(Integer // 增加文件柜的占用空间
							.parseInt(fc.getUsedSpace().substring(0,
									fc.getUsedSpace().length() - 2))
							+ Integer.parseInt(arrayfilesize[i]))
							+ "kb");
					baseBeanService.update(fc);
				}
				newFileUpload.setUploadPersonName(staff.getStaffName());
				newFileUpload.setCompanyId(companyID);
				newFileUpload.setCompanyName(company.getCompanyName());
				newFileUpload.setFileUploadId(fileUploadId);
				newFileUpload.setFileUploadSize(arrayfilesize[i] + "kb");
				newFileUpload.setTypeFolder("File");
				Date d = (Date) Calendar.getInstance().getTime();
				newFileUpload.setUploadTime(d);
				baseBeanService.save(newFileUpload);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (uploadMode == "folder" || uploadMode.equalsIgnoreCase("folder")) {
			fileCabinetIdmark = fileCabinetIdcurrent;
			return getFileOfFolderList();
		} else {
			fileFolderIdmark = fileFolderIdcurrent;
			return getFileOfFolder();
		}
	}

	// 文件的移动
	public String fileMove() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileCabinetIdcurrent = request.getParameter("fileCabinetId");
		String fileFolderIdcurrent = request.getParameter("fileFolderId");// 可能为空
		String uploadMode = request.getParameter("uploadMode");
		String fileId = request.getParameter("fileId");
		String saveDirectory = fileUpload.getSaveDirectory();// 获取的目录是：文件夹Id,文件柜Id
		String hql1 = "from FileUpload where fileUploadId = ?";
		FileUpload fileOld = (FileUpload) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] { fileId });// 原始文件
		FileCabinet cabinetOld = fileOld.getFileCabinet();// 文件所在原始文件柜也许为空
		FileFolder folderOld = null;
		if (cabinetOld == null) {
			folderOld = fileOld.getFileFolder();// 文件所在原始文件夹,此时不为空
			cabinetOld = folderOld.getFileCabinet();
		}

		try {
			// 去除原始文件信息；
			if (folderOld != null) {
				folderOld.setFileNumber(String.valueOf(Integer
						.parseInt(folderOld.getFileNumber()) - 1));
				folderOld.setUsedSpace(String.valueOf(Integer
						.parseInt(folderOld.getUsedSpace().substring(0,
								folderOld.getUsedSpace().length() - 2))
						- Integer
								.parseInt(fileOld.getFileUploadSize()
										.substring(
												0,
												fileOld.getFileUploadSize()
														.length() - 2)))
						+ "kb");
				baseBeanService.update(folderOld);

			}
			cabinetOld.setFileNumber(String.valueOf(Integer.parseInt(cabinetOld
					.getFileNumber()) - 1));
			cabinetOld.setUsedSpace(String.valueOf(Integer.parseInt(cabinetOld
					.getUsedSpace().substring(0,
							cabinetOld.getUsedSpace().length() - 2))
					- Integer.parseInt(fileOld.getFileUploadSize().substring(0,
							fileOld.getFileUploadSize().length() - 2)))
					+ "kb");
			baseBeanService.update(cabinetOld);

			if (saveDirectory.substring(0, 2).equalsIgnoreCase("fo")) {// 说明移动到文件夹中

				String hql2 = "from FileFolder where fileFolderId = ?";
				FileFolder moveFolder = (FileFolder) baseBeanService
						.getBeanByHqlAndParams(hql2,
								new Object[] { saveDirectory });// 将要移动到的文件夹
				FileCabinet moveCabinet = moveFolder.getFileCabinet();// 将要移动到的文件柜
				// 增加移动后的文件信息；

				moveFolder.setFileNumber(String.valueOf(Integer
						.parseInt(moveFolder.getFileNumber()) + 1));
				moveFolder.setUsedSpace(String.valueOf(Integer
						.parseInt(moveFolder.getUsedSpace().substring(0,
								moveFolder.getUsedSpace().length() - 2))
						+ Integer
								.parseInt(fileOld.getFileUploadSize()
										.substring(
												0,
												fileOld.getFileUploadSize()
														.length() - 2)))
						+ "kb");

				baseBeanService.update(moveFolder);

				moveCabinet.setFileNumber(String.valueOf(Integer
						.parseInt(moveCabinet.getFileNumber()) + 1));
				moveCabinet.setUsedSpace(String.valueOf(Integer
						.parseInt(moveCabinet.getUsedSpace().substring(0,
								moveCabinet.getUsedSpace().length() - 2))
						+ Integer
								.parseInt(fileOld.getFileUploadSize()
										.substring(
												0,
												fileOld.getFileUploadSize()
														.length() - 2)))
						+ "kb");

				baseBeanService.update(moveCabinet);

				fileOld.setSaveDirectory(moveCabinet.getFileCabinetName() + "/"
						+ moveFolder.getFileFolderName());
				fileOld.setFileFolder(moveFolder);
				fileOld.setFileCabinet(null);
				baseBeanService.update(fileOld);

			} else {// 移动到文件柜中
				String hql3 = "from FileCabinet where FileCabinetId = ?";
				FileCabinet moveCabinet = (FileCabinet) baseBeanService// 将要移动到的文件柜
						.getBeanByHqlAndParams(hql3,
								new Object[] { saveDirectory });

				moveCabinet.setFileNumber(String.valueOf(Integer
						.parseInt(moveCabinet.getFileNumber()) + 1));
				moveCabinet.setUsedSpace(String.valueOf(Integer
						.parseInt(moveCabinet.getUsedSpace().substring(0,
								moveCabinet.getUsedSpace().length() - 2))
						+ Integer
								.parseInt(fileOld.getFileUploadSize()
										.substring(
												0,
												fileOld.getFileUploadSize()
														.length() - 2)))
						+ "kb");

				baseBeanService.update(moveCabinet);

				fileOld.setSaveDirectory(moveCabinet.getFileCabinetName());
				fileOld.setFileFolder(null);
				fileOld.setFileCabinet(moveCabinet);
				baseBeanService.update(fileOld);

			}
		} catch (Exception e) {
			System.out.println("文件移动失败！");
			e.printStackTrace();
		}

		if (uploadMode == "folder" || uploadMode.equalsIgnoreCase("folder")) {
			fileCabinetIdmark = fileCabinetIdcurrent;
			return getFileOfFolderList();
		} else {
			fileFolderIdmark = fileFolderIdcurrent;
			return getFileOfFolder();
		}
	}

	// 文件的复制
	public String fileCopy() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileCabinetIdcurrent = request.getParameter("fileCabinetId");// 可能为空
		String fileFolderIdcurrent = request.getParameter("fileFolderId");// 可能为空
		String uploadMode = request.getParameter("uploadMode");
		String fileId = request.getParameter("fileId");
		String saveDirectory = fileUpload.getSaveDirectory();// 获取的目录是：文件夹Id,文件柜Id
		String hql1 = "from FileUpload where fileUploadId = ?";
		FileUpload fileOld = (FileUpload) baseBeanService
				.getBeanByHqlAndParams(hql1, new Object[] { fileId });// 原始文件
		FileCabinet cabinetOld = fileOld.getFileCabinet();// 文件所在原始文件柜也许为空
		FileFolder folderOld = null;
		if (cabinetOld == null) {
			folderOld = fileOld.getFileFolder();// 文件所在原始文件夹,此时不为空
			cabinetOld = folderOld.getFileCabinet();
		}

		try {
			// 原始文件信息；
			if (saveDirectory.substring(0, 2).equalsIgnoreCase("fo")) {// 说明移动到文件夹中

				String hql2 = "from FileFolder where fileFolderId = ?";
				FileFolder moveFolder = (FileFolder) baseBeanService
						.getBeanByHqlAndParams(hql2,
								new Object[] { saveDirectory });// 将要移动到的文件夹
				FileCabinet moveCabinet = moveFolder.getFileCabinet();// 将要移动到的文件柜
				// 增加移动后的文件信息；

				moveFolder.setFileNumber(String.valueOf(Integer
						.parseInt(moveFolder.getFileNumber()) + 1));
				moveFolder.setUsedSpace(String.valueOf(Integer
						.parseInt(moveFolder.getUsedSpace().substring(0,
								moveFolder.getUsedSpace().length() - 2))
						+ Integer
								.parseInt(fileOld.getFileUploadSize()
										.substring(
												0,
												fileOld.getFileUploadSize()
														.length() - 2)))
						+ "kb");

				baseBeanService.update(moveFolder);

				moveCabinet.setFileNumber(String.valueOf(Integer
						.parseInt(moveCabinet.getFileNumber()) + 1));
				moveCabinet.setUsedSpace(String.valueOf(Integer
						.parseInt(moveCabinet.getUsedSpace().substring(0,
								moveCabinet.getUsedSpace().length() - 2))
						+ Integer
								.parseInt(fileOld.getFileUploadSize()
										.substring(
												0,
												fileOld.getFileUploadSize()
														.length() - 2)))
						+ "kb");

				baseBeanService.update(moveCabinet);
				// 复制一个原始文件记录
				FileUpload newFileUpload = new FileUpload();
				String fileUploadId = "fi_" +RandomDatas.getUUID();
				newFileUpload.setFileUploadId(fileUploadId);
				newFileUpload.setCompanyId(fileOld.getCompanyId());
				newFileUpload.setCompanyName(fileOld.getCompanyName());
				newFileUpload.setFileUploadName(fileOld.getFileUploadName());
				newFileUpload.setFileUploadSize(fileOld.getFileUploadSize());
				newFileUpload.setUploadPersonId(fileOld.getUploadPersonId());
				newFileUpload
						.setUploadPersonName(fileOld.getUploadPersonName());
				newFileUpload.setUploadTime(fileOld.getUploadTime());
				newFileUpload.setFileUploadPath(fileOld.getFileUploadPath());

				newFileUpload.setSaveDirectory(moveCabinet.getFileCabinetName()
						+ "/" + moveFolder.getFileFolderName());
				newFileUpload.setTypeFolder("File");
				newFileUpload.setIconPath(fileOld.getIconPath());
				newFileUpload.setFileFolder(moveFolder);
				newFileUpload.setFileCabinet(null);
				baseBeanService.save(newFileUpload);

			} else {// 移动到文件柜中
				String hql3 = "from FileCabinet where FileCabinetId = ?";
				FileCabinet moveCabinet = (FileCabinet) baseBeanService// 将要移动到的文件柜
						.getBeanByHqlAndParams(hql3,
								new Object[] { saveDirectory });

				moveCabinet.setFileNumber(String.valueOf(Integer
						.parseInt(moveCabinet.getFileNumber()) + 1));
				moveCabinet.setUsedSpace(String.valueOf(Integer
						.parseInt(moveCabinet.getUsedSpace().substring(0,
								moveCabinet.getUsedSpace().length() - 2))
						+ Integer
								.parseInt(fileOld.getFileUploadSize()
										.substring(
												0,
												fileOld.getFileUploadSize()
														.length() - 2)))
						+ "kb");

				baseBeanService.update(moveCabinet);
				// 复制一个原始文件记录
				FileUpload newFileUpload = new FileUpload();
				String fileUploadId = "fi_" +RandomDatas.getUUID();
				newFileUpload.setFileUploadId(fileUploadId);
				newFileUpload.setCompanyId(fileOld.getCompanyId());
				newFileUpload.setCompanyName(fileOld.getCompanyName());
				newFileUpload.setFileUploadName(fileOld.getFileUploadName());
				newFileUpload.setFileUploadSize(fileOld.getFileUploadSize());
				newFileUpload.setUploadPersonId(fileOld.getUploadPersonId());
				newFileUpload
						.setUploadPersonName(fileOld.getUploadPersonName());
				newFileUpload.setUploadTime(fileOld.getUploadTime());
				newFileUpload.setFileUploadPath(fileOld.getFileUploadPath());

				newFileUpload
						.setSaveDirectory(moveCabinet.getFileCabinetName());
				newFileUpload.setIconPath(fileOld.getIconPath());
				newFileUpload.setTypeFolder("File");
				newFileUpload.setFileFolder(null);
				newFileUpload.setFileCabinet(moveCabinet);
				baseBeanService.save(newFileUpload);

			}
		} catch (Exception e) {
			System.out.println("文件移动失败！");
			e.printStackTrace();
		}
		if (uploadMode == "folder" || uploadMode.equalsIgnoreCase("folder")) {
			fileCabinetIdmark = fileCabinetIdcurrent;
			return getFileOfFolderList();
		} else {
			fileFolderIdmark = fileFolderIdcurrent;
			return getFileOfFolder();
		}

	}

	// 根据公司ID和创建人ID查询文件柜
	public String getFileCabinetList() {
		CAccount account = (CAccount) ActionContext.getContext().getSession()
				.get("account");
		String staffID = account.getStaffID();
		String companyID = account.getCompanyID();
		String hql = "from FileCabinet where companyId=? and createrId = ?";
		List<BaseBean> fileClist = baseBeanService.getListBeanByHqlAndParams(
				hql, new Object[] { companyID, staffID });
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig cfg = new JsonConfig();
		cfg.setRootClass(FileCabinet.class);
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if (arg1.equals("fileFolders") || arg1.equals("fileUploads")) {
					return true;
				} else {
					return false;
				}
			}
		});

		map.put("fileClist", fileClist);
		JSONObject oj = JSONObject.fromObject(map, cfg);
		result = oj.toString();
		return "success";
	}

	// 根据文件柜查询文件夹
	public String getFileFolderList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileCabinetId = request.getParameter("fileCabinetId");
		String hql = "from FileCabinet where fileCabinetId = ?";
		FileCabinet fileCabinet = (FileCabinet) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { fileCabinetId });
		String hqlf = "from FileFolder where fileCabinet = ?";

		List<BaseBean> fileFlist = baseBeanService.getListBeanByHqlAndParams(
				hqlf, new Object[] { fileCabinet });
		Map<String, Object> map = new HashMap<String, Object>();
		JsonConfig cfg = new JsonConfig();
		cfg.setRootClass(FileFolder.class);
		cfg.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				if (arg1.equals("fileCabinet") || arg1.equals("fileUploads")) {
					return true;
				} else {
					return false;
				}
			}
		});
		map.put("fileFlist", fileFlist);
		JSONObject oj = JSONObject.fromObject(map, cfg);
		result = oj.toString();

		return "success";
	}

	// 进入文件夹后获取这个文件夹中的所有文件
	public String getFileOfFolder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileFolderId = request.getParameter("fileFolderId");
		if (fileFolderId == null) {
			fileFolderId = fileFolderIdmark;
			if (fileFolderIdmark == null
					|| fileFolderIdmark.equalsIgnoreCase("")) {
				fileFolderId = (String) request.getSession().getAttribute(
						"fileFolderId");
			} else {
				fileFolderId = fileFolderIdmark;
			}

		}
		String hql = "from FileFolder where fileFolderId = ?";
		FileFolder fileFolder = (FileFolder) baseBeanService
				.getBeanByHqlAndParams(hql, new Object[] { fileFolderId });
		String sql = "select f.fileUploadid, f.fileuploadname,f.fileUploadPath,f.iconPath from dt_oa_file_upload f where f.fileFolderKey= ? order by uploadTime Desc";

		String sqlcount = "select count(*) from dt_oa_file_upload f where f.fileFolderKey= ? order by uploadTime Desc";

		pageForm = baseBeanService.getPageFormBySQL(
				(null != pageForm ? pageForm.getPageNumber() : 1),
				(pageNumber == 0 ? 5 : pageNumber), sql, sqlcount,
				new Object[] { fileFolder.getKey() });
		request.getSession().setAttribute("fileFolderId",
				fileFolder.getFileFolderId());
		request.setAttribute("fileCabinetId", fileFolder.getFileCabinet()
				.getFileCabinetId());
		request.setAttribute("fileFolderId", fileFolder.getFileFolderId());
		request.setAttribute("fileFolderKey", fileFolder.getKey());
		request.setAttribute("fileFolderName", fileFolder.getFileFolderName());

		return "fileUploadlist";
	}

	// 获取属性
	public String getAttributes() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileId = request.getParameter("fileId");
		if (fileId.substring(0, 2) != ""
				&& "fo".equalsIgnoreCase(fileId.substring(0, 2))) {// 查看文件夹属性
			String hql = "from FileFolder where fileFolderId = ?";
			FileFolder fileFolder = (FileFolder) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { fileId });
			String timeStr = fileFolder.getCreateTime().toString();
			fileFolder.setCreateTimeString(timeStr);
			Map<String, Object> map = new HashMap<String, Object>();
			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(FileCabinet.class);
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if (arg1.equals("fileCabinet")
							|| arg1.equals("fileUploads")) {
						return true;
					} else {
						return false;
					}
				}
			});
			map.put("bean", fileFolder);
			JSONObject oj = JSONObject.fromObject(map, cfg);
			result = oj.toString();
		} else {
			String hql = "from FileUpload where fileUploadId = ?";
			FileUpload fileUpload = (FileUpload) baseBeanService
					.getBeanByHqlAndParams(hql, new Object[] { fileId });
			String timeStr = fileUpload.getUploadTime().toString();
			fileUpload.setUploadTimeString(timeStr);
			Map<String, Object> map = new HashMap<String, Object>();
			JsonConfig cfg = new JsonConfig();
			cfg.setRootClass(FileCabinet.class);
			cfg.setJsonPropertyFilter(new PropertyFilter() {
				@Override
				public boolean apply(Object arg0, String arg1, Object arg2) {
					if (arg1.equals("fileCabinet") || arg1.equals("fileFolder")) {
						return true;
					} else {
						return false;
					}
				}
			});

			map.put("bean", fileUpload);
			JSONObject oj = JSONObject.fromObject(map, cfg);
			result = oj.toString();
		}

		return "success";
	}

	// 删除：文件柜；文件夹及文件列表；文件列表；
	public String delFileCabinet() {
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String Id = (String) request.getParameter("Id");
		if(Id==null){
			Id = fileCabinet.getFileCabinetId();
		}
		Object[] params = { Id };
		if (Id.substring(0, 2) != ""
				&& "ca".equalsIgnoreCase(Id.substring(0, 2))) {// 删除文件柜
			String hql = "from FileCabinet where fileCabinetId = ?";
			FileCabinet fc = (FileCabinet) baseBeanService
					.getBeanByHqlAndParams(hql, params);
			baseBeanService.deleteBeanByKey(FileCabinet.class, fc.getKey());
			return getListForFileCabinet();
		}
		if (Id.substring(0, 2) != ""
				&& "fo".equalsIgnoreCase(Id.substring(0, 2))) {// 删除文件夹
			String fileCabinetId = (String) request
					.getParameter("fileCabinetId");
			// 查询待删除文件夹中的信息更新文件柜的内容
			String hql2 = "from FileFolder where fileFolderId = ?";
			FileFolder oldfolder = (FileFolder) baseBeanService
					.getBeanByHqlAndParams(hql2, params);
			String fileNumber = oldfolder.getFileNumber();
			String usedSpace = oldfolder.getUsedSpace();
			String hql3 = "from FileCabinet where fileCabinetId = ?";
			FileCabinet oldCabi = (FileCabinet) baseBeanService
					.getBeanByHqlAndParams(hql3, new Object[] { fileCabinetId });
			oldCabi.setFileNumber(String.valueOf(Integer.parseInt(oldCabi
					.getFileNumber())
					- Integer.parseInt(fileNumber)));
			oldCabi.setUsedSpace(String.valueOf(Integer.parseInt(oldCabi
					.getUsedSpace().substring(0,
							oldCabi.getUsedSpace().length() - 2))
					- Integer.parseInt(usedSpace.substring(0, usedSpace
							.length() - 2)))
					+ "kb");
			oldCabi.setFileFolderNumber(String.valueOf(Integer.parseInt(oldCabi
					.getFileFolderNumber()) - 1));
			baseBeanService.update(oldCabi);
			String hql = "from FileFolder where fileFolderId = ?";
			FileFolder fo = (FileFolder) baseBeanService.getBeanByHqlAndParams(
					hql, params);
			baseBeanService.deleteBeanByKey(FileFolder.class, fo.getKey());
			fileCabinetIdmark = fileCabinetId;
			return getFileOfFolderList();
		}
		if (Id.substring(0, 2) != ""
				&& "fi".equalsIgnoreCase(Id.substring(0, 2))) {// 删除文件
			String sort = (String) request.getParameter("sort");

			String hql2 = "from FileUpload where fileUploadId = ?";
			FileUpload oldUpload = (FileUpload) baseBeanService
					.getBeanByHqlAndParams(hql2, params);
			if(oldUpload==null){
				if(sort.equalsIgnoreCase("folder")){
					String fileCabinetId = (String) request
				.getParameter("fileCabinetId");
				fileCabinetIdmark = fileCabinetId;
				return getFileOfFolderList();
				}
				if (sort.equalsIgnoreCase("file")){
					String fileFolderId  = (String) request
					.getParameter("fileFolderId");
					fileFolderIdmark = fileFolderId;
					return getFileOfFolder();
				}
				
			}
			String fileUploadSize = oldUpload.getFileUploadSize();

			String fileCabinetId = (String) request
					.getParameter("fileCabinetId");
			
			String hql3 = "from FileCabinet where fileCabinetId = ?";
			FileCabinet oldCabi = (FileCabinet) baseBeanService
					.getBeanByHqlAndParams(hql3, new Object[] { fileCabinetId });
			oldCabi.setFileNumber(String.valueOf(Integer.parseInt(oldCabi
					.getFileNumber()) - 1));
			oldCabi.setUsedSpace(String.valueOf(Integer.parseInt(oldCabi
					.getUsedSpace().substring(0,
							oldCabi.getUsedSpace().length() - 2))
					- Integer.parseInt(fileUploadSize.substring(0,
							fileUploadSize.length() - 2)))
					+ "kb");
			baseBeanService.update(oldCabi);// 更新文件柜信息
			if (sort.equalsIgnoreCase("folder")) {// 删除文件柜中的文件
				String hql = "delete from FileUpload where fileUploadId=?";
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
						new String[] { hql }, params);
				fileCabinetIdmark = fileCabinetId;
				return getFileOfFolderList();
			}
			if (sort.equalsIgnoreCase("file")) {// 删除文件夹中的文件
				String fileFolderId = (String) request
						.getParameter("fileFolderId");

				String hql4 = "from FileFolder where fileFolderId = ?";
				FileFolder oldfolder = (FileFolder) baseBeanService
						.getBeanByHqlAndParams(hql4,
								new Object[] { fileFolderId });

				oldfolder.setFileNumber(String.valueOf(Integer
						.parseInt(oldfolder.getFileNumber()) - 1));
				oldfolder.setUsedSpace(String.valueOf(Integer
						.parseInt(oldfolder.getUsedSpace().substring(0,
								oldfolder.getUsedSpace().length() - 2))
						- Integer.parseInt(fileUploadSize.substring(0,
								fileUploadSize.length() - 2)))
						+ "kb");
				baseBeanService.update(oldfolder);// 更新文件夹信息

				String hql = "delete from FileUpload where fileUploadId=?";
				baseBeanService.saveBeansListAndexecuteHqlsByParams(null,
						new String[] { hql }, params);
				fileFolderIdmark = fileFolderId;
				return getFileOfFolder();
			}

		}
		return "";

	}
	
	//判断是否是第一次打开
     @SuppressWarnings({ "unused", "deprecation" })
	public String existSwf(){
    	 Map<String, Object> session = ActionContext.getContext().getSession();
 		CAccount account = (CAccount) session.get("account");
    	 HttpServletRequest request = ServletActionContext.getRequest();
 		String sourcePath = (String) request.getParameter("filePath");
 		String fileName = sourcePath
 				.substring(sourcePath.lastIndexOf('\\') + 1);
 		String realPath = ServletActionContext.getRequest().getRealPath(
 				"/upload_files");
 		realPath = realPath.substring(0, realPath.lastIndexOf('\\'));
 		String swfPath = null;
 		String fullPath = realPath+"\\upload_files\\office\\fileCabinet\\"+account.getCompanyID()+"\\"+fileName.substring(0,fileName.lastIndexOf("."))+".swf";
 	    File ff = new File(fullPath);
 	    Map<String, Object> map = new HashMap<String, Object>();
 	    if(ff.exists()){
 	    	map.put("result", "exists");
 	    }else{
 	    	map.put("result", "unexists");
 	    }
 		JSONObject oj = JSONObject.fromObject(map);
 		this.result = oj.toString();
 		return "success";
     }
	// 将pdf文件转换为swf
	@SuppressWarnings("deprecation")
	public String pdfToSwf() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String sourcePath = (String) request.getParameter("filePath");
		String fileName = sourcePath
				.substring(sourcePath.lastIndexOf('\\') + 1);
		String realPath = ServletActionContext.getRequest().getRealPath(
				"/upload_files");
		realPath = realPath.substring(0, realPath.lastIndexOf('\\'));
		String swfPath = null;
		try {
			swfPath = fileManagerService.pdfToSwf(sourcePath, realPath,
					fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", swfPath);
		JSONObject oj = JSONObject.fromObject(map);
		this.result = oj.toString();
		return "success";
	}

	public String updateFolderName() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String fileCabinetId = request.getParameter("fileCabinetId");
		String folderId = request.getParameter("folderId");
		String fileFolderNameA = request.getParameter("fileFolderNameA");
		String hql = "from FileFolder where fileFolderId = ? ";
		FileFolder ff = (FileFolder) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { folderId });
		ff.setFileFolderName(fileFolderNameA);
		baseBeanService.update(ff);
		fileCabinetIdmark = fileCabinetId;
		return getFileOfFolderList();
	}
	
	//搜索后修改属性
	public String updateFolderName2() {
		Map<String, Object> session = ActionContext.getContext().getSession();
		CAccount account = (CAccount) session.get("account");
		if (account == null) {

			return "not_login";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		String folderId = request.getParameter("folderId");
		String fileFolderNameA = request.getParameter("fileFolderNameA");
		String hql = "from FileFolder where fileFolderId = ? ";
		FileFolder ff = (FileFolder) baseBeanService.getBeanByHqlAndParams(hql,
				new Object[] { folderId });
		ff.setFileFolderName(fileFolderNameA);
		baseBeanService.update(ff);
		return getListSearchResultList();
	}

	public PageForm getPageForm() {
		return pageForm;
	}

	public void setPageForm(PageForm pageForm) {
		this.pageForm = pageForm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
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

	public FileCabinet getFileCabinet() {
		return fileCabinet;
	}

	public void setFileCabinet(FileCabinet fileCabinet) {
		this.fileCabinet = fileCabinet;
	}

	public FileFolder getFileFolder() {
		return fileFolder;
	}

	public void setFileFolder(FileFolder fileFolder) {
		this.fileFolder = fileFolder;
	}

	public String getFileCabinetKey() {
		return fileCabinetKey;
	}

	public void setFileCabinetKey(String fileCabinetKey) {
		this.fileCabinetKey = fileCabinetKey;
	}

	public List<BaseBean> getListFolder() {
		return listFolder;
	}

	public void setListFolder(List<BaseBean> listFolder) {
		this.listFolder = listFolder;
	}

	public FileUpload getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(FileUpload fileUpload) {
		this.fileUpload = fileUpload;
	}

	public UpLoadFileService getFileService() {
		return fileService;
	}

	public void setFileService(UpLoadFileService fileService) {
		this.fileService = fileService;
	}

	public List<BaseBean> getListUpload() {
		return listUpload;
	}

	public void setListUpload(List<BaseBean> listUpload) {
		this.listUpload = listUpload;
	}

	public String getFileCabinetIdmark() {
		return fileCabinetIdmark;
	}

	public void setFileFolderIdmark(String fileFolderIdmark) {
		this.fileCabinetIdmark = fileFolderIdmark;
	}

	public FileSearchInfo getFileSearchInfo() {
		return fileSearchInfo;
	}

	public void setFileSearchInfo(FileSearchInfo fileSearchInfo) {
		this.fileSearchInfo = fileSearchInfo;
	}

	public FileUploadInfo getFileUploadInfo() {
		return fileUploadInfo;
	}

	public void setFileUploadInfo(FileUploadInfo fileUploadInfo) {
		this.fileUploadInfo = fileUploadInfo;
	}
	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

	public String getFileFolderIdmark() {
		return fileFolderIdmark;
	}

}