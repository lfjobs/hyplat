package com.mysl.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.util.RandomDatas;
import hy.plat.bo.BaseBean;
import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.BaseBeanService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import com.mysl.service.OfficeService;

@Service
@Transactional
public class OfficeServiceImpl implements OfficeService {
	@Resource
	private BaseBeanDao baseBeanDao;
	// 创建
	public String createOffice(String realpath, String fileType,
			String templatepath, String storePath) {
		String ext = "";
		String extname = "";
		if (fileType == "W" || fileType.equals("W")) {
			extname = "word_";
			ext = ".doc";
		} else {
			extname = "excel_";
			ext = ".xls";
		}
        
		String fileSaveName = extname + RandomDatas.getRandomString(6) + ext;
		try {
			
			File fileSaveDir = new File(realpath + storePath);
			File fileSave = new File(realpath + storePath, fileSaveName);
			File office = null;
			if (templatepath == null) {
				office = new File(realpath + "document/template/" + "blank"
						+ ext);
			} else {
				office = new File(realpath + templatepath);
			}
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}
			FileCopyUtils.copy(office, fileSave);
		} catch (IOException e) {
			logger.error("操作异常", e);
		}

		return storePath+ "/" + fileSaveName;
	}

	public void saveOffice(HttpServletRequest request,
			HttpServletResponse response, String realpath ,String storepath) {
		SOAOfficeX.SaveDocObj SOAObj = new SOAOfficeX.SaveDocObj(request,
				response);
		String serverFilePath = realpath + storepath;
		try {
			SOAObj.saveToFile(serverFilePath); // saveToFile 的参数是文档的物理绝对路径。
			SOAObj.returnOK();
		} catch (Exception e) {
			logger.error("操作异常", e);
		} finally {
			try {
				SOAObj.close();
			} catch (Exception e) {
				logger.error("操作异常", e);
			}
		}
	}

	/**
	 * 
	 * 删除Office文件
	 * 
	 */
	public void deleteOffice(String filePath) {

		try {
			File file = new File(filePath);
			if (!file.isDirectory()) {
				file.delete();
			}
		} catch (Exception e) {
			logger.info("删除文件夹出错");
		}

	}
	
	
	/**
	 * 
	 * 拷贝文件
	 */
	public String CopyOfficeFile(String realpath,String filePath){
		filePath = filePath.replace("\\", "/");
		String filename = filePath.substring(filePath.lastIndexOf("/")+1);
		String ext = filePath.substring(filePath.lastIndexOf("."));
		String extname = "";
		if (ext.equals(".doc")) {
			extname = "word_";
		} else {
			extname = "excel_";
		}
		String fileSaveName = extname+ RandomDatas.getRandomString(3) + ext;
		String filedir = filePath.replace(filename,"");
		try {
			
			File newfilepath = new File(realpath+filedir+"/"+fileSaveName);
			File fileSave = new File(realpath+filePath);
			FileCopyUtils.copy(fileSave,newfilepath);
		} catch (IOException e) {
			logger.error("操作异常", e);
		}
		
		return filedir+fileSaveName;

	}
	
	
	
	/**
	 * 
	 * 
	 * 获取任务的序号
	 * @param taskType
	 * @return 序号
	 */
	
	public String getTaskSequenceNum(String taskType,String companyid,String orgid,String proid){
		int num = 0;
		String s2 = "";
		String hql = "from DtMytask where taskType = ?";
		String sql = "";
		List<Object> params = new ArrayList<Object>();
		params.add(taskType);
		if(taskType.equals("htzd")||taskType.equals("scsj")){
			hql+=" and companyid = ?";
			
			params.add(companyid);
			
			sql = "select max(to_number(seqnum)) from DT_MYTASK where taskType = ? and companyid = ?";
			
		}
		if(taskType.equals("scrw")){
			hql+=" and  orgid = ?";
		
			params.add(orgid);
			
			sql = "select max(to_number(seqnum)) from DT_MYTASK where taskType = ? and  orgid = ? ";
		}
		if(taskType.equals("sjdg")||taskType.equals("htrw")){
			hql+=" and  proid = ?";
			params.add(proid);
			
			sql = "select max(to_number(seqnum)) from DT_MYTASK where taskType = ? and  proid = ?";
			
		}
		
		try{
		
		List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,params.toArray());
		if(list.size()==0){
			return "0001";
		}
		num = baseBeanDao.getConutByBySqlAndParams(sql, params.toArray());
		
		int nextnum = num + 1;
		
		if (nextnum > 0 && nextnum <= 9) {
			s2 = "000" + nextnum;
		} else if (nextnum > 9 && nextnum <= 99) {
			s2 = "00" + nextnum;
		} else if (nextnum > 99 && nextnum <= 999) {
			s2 = "0" + nextnum;
		} else if (nextnum > 999 && nextnum < 9999) {
			s2 = "" + nextnum;
		}else{
			s2 = ""+nextnum;
		}
		
		}catch(Exception e){
			logger.error("操作异常", e);
		}
		return s2;
		
	}
	
	/**
	 * 
	 * 判断编码是否重复
	 * 
	 */
	 public String checkCodeRepeat(String taskType,String companyid,String orgid,String proid,String taskcode){

		     String[] taskcodes = taskcode.split("-");
		     
		 
		 
			String hql = "from DtMytask where taskType = ? and taskcode like ?";
      
			List<Object> params = new ArrayList<Object>();
			params.add(taskType);
			if(taskType.equals("htzd")||taskType.equals("scsj")){
				hql+=" and companyid = ?";
				
				if(taskType.equals("htzd")){
				  params.add("%"+taskcodes[0]+"-"+taskcodes[1]+"%");
				}else{
				  params.add("%"+taskcodes[1]+"-"+taskcodes[2]+"%");
				}
				
				params.add(companyid);
				
			}
			if(taskType.equals("scrw")){
				hql+=" and  orgid = ?";
			    params.add("%"+taskcodes[2]+"-"+taskcodes[3]+"%");
				params.add(orgid);
				
				
			}
			if(taskType.equals("sjdg")||taskType.equals("htrw")){
				hql+=" and  proid = ?";
				params.add("%"+taskcode+"%");
				params.add(proid);
			
				
			}
			
			
			
			List<BaseBean> list = baseBeanDao.getListBeanByHqlAndParams(hql,params.toArray());
			if(list.size()==0){
				return "havenot";
			}
		 
		 
		 
		 return "have";
	 }

}