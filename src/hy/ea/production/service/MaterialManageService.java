package hy.ea.production.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import hy.ea.bo.office.CarManageAudit;
import mobile.tiantai.android.bo.AuditRecord;

import com.tiantai.wfj.bo.TEshopCusCom;

import hy.ea.bo.CAccount;
import hy.ea.bo.company.ContactCompany;
import hy.ea.bo.finance.ProductPackaging;
import hy.ea.bo.office.Certificate;
import hy.ea.bo.production.scmanage.MaterialContent;
import hy.ea.bo.production.scmanage.MaterialMusic;
import hy.plat.bo.BaseBean;
import hy.plat.bo.PageForm;




public interface MaterialManageService {
     
	/**
	 * 保存上传的文件
	 * @param materialContent
	 * @return
	 */
	 public void saveContent(MaterialContent materialContent,String staffID);
	 /**
	  * 
	  * 单个删除上传文件以及缓存文件
	  * @return
	  */
	 public boolean deleteFileTempByone(String filepath,String realpath);
	 /**
	  * 
	  * 批量删除上传文件以及缓存文件
	  * @return
	  */
	 public boolean deleteFileTempByBat(String companyID,String staffID,String realpath);
	 
	 
	 /**
	  * 
	  * 保存后删除文件，包括处理，分组个数
	  * @param materialContent
	  * @return
	  */
	 public boolean deleteFileBypre(MaterialContent materialContent,String realpath);
	 
	 /**
	  * 
	  * 分享文章保存
	  * @param productPackaging
	  * @param staffID
	  * @param content
	 * @param miniSystemJudge 
	  * @return
	  */
	 public ProductPackaging saveQRShare(ProductPackaging productPackaging,String content, String miniSystemJudge);
	 
	 
	 /**
	  * 
	  * 处理数据
	  */
	 public void dealVideo();
	 
	 /**
	  * 
	  *  根据个人ID获取公司ID
	  * @param staffID
	  * @return
	  */
	 public String getStaffCompanyID(String staffID);
	 /**
	  * 
	  *  查询个人信息
	 * @param cuscom 
	  * @return
	  */
	public Object qrshareList(TEshopCusCom cuscom);
	/**
	  * 
	  *  查询个人分享
	 * @param sccid 
	  * @return
	  */
	public PageForm memberShare(int pageNumber, int i, String sccid,String companyID);

	/**
	 *
	 *  查询全面分享
	 * @param parameter
	 * @return
	 */
	public PageForm allShare(int pageNumber, int pageSize, String  parameter,String staffId);
	public PageForm inquireProduct(int pageNumber, int i, String string, String staffid);
	public void deleteProject(String ppID, String goodsid,String pictureName);
	public void updateLogin(String sccid, TEshopCusCom tcc);
	public void delTemporarySavePicture(String mcId);
	public String request(String httpUrl, String httpArg);
	public List<BaseBean> defaultMusic();
	public List<BaseBean> historyMusic(String staffid);
	public boolean addRecord(String staffid, MaterialMusic materialMusic);
	public String addMaterial(String staffid, String companyId, File picture,
			String pictureName);
	public void delTransformVideo(String pictureName);
	boolean transitionVeido(String share_path, String img_path, String veido);
	
	
	
	public PageForm miniList(int pageNumber, int pageSize, String companyId, String miniSystemJudge);
	public boolean addMessage(CAccount caccount, ContactCompany contactCompany, Certificate certificate);
	public ContactCompany queryMessage(CAccount caccount);
	public Certificate queryCertificate(CAccount caccount);
	public AuditRecord queryRecord(String auid);
	public void alterAudit(String companyID, String state);


	/**
	 *
	 * 获取sdkConfig
	 * @param url
	 * @return
	 */
	public Map<String,Object> getJssdkConfig(String url );

	public String saveTimeInfo(CarManageAudit carManageAudit,String staffID,String companyID);


 	public CarManageAudit getTimeHistory(String carmID);
}
