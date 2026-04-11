package hy.ea.office.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.ea.bo.office.DocumentTemplate;
import hy.ea.office.service.DocumentTemplateService;

import hy.ea.util.RandomDatas;

import hy.plat.dao.impl.BaseBeanDao;
import hy.plat.service.ServerService;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

@Service
@Transactional
public class DocumentTemplateServiceImpl implements DocumentTemplateService {

	@Resource
	private BaseBeanDao baseBeanDao;
	@Resource
	private ServerService serverService;

	// 创建空白模板
	public String createBlankTemplate(String path,
			DocumentTemplate documentTemplate,String fileType,String staffID) throws IOException {
        String templatePathf="";
		// 考虑到linux,获取文件分隔符
		String fs = System.getProperties().getProperty("file.separator");
		String sep = null;
		if (fs.equals("/"))
			sep = fs;
		else
			sep = "\\";
		if(fileType=="W"||fileType.equals("W")){
		String fileSaveName = "word_" + RandomDatas.getRandomString(3);
		String companyID = documentTemplate.getCompanyId();
		String templatePathDir = "document" + sep + "template" + sep
				+ companyID;
		templatePathf = templatePathDir + sep + fileSaveName + ".doc";
		File fileSaveDir = new File(path + templatePathDir);
		File fileSave = new File(path + templatePathDir, fileSaveName + ".doc");
		File blankword = new File(path + "document" + sep + "template" + sep
				+ "blank.doc");
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
		FileCopyUtils.copy(blankword, fileSave);
		//String templateId = "template_" + RandomDatas.getRandomString(5);
		String templateId = serverService.getServerID("temp");
		documentTemplate.setTemplateId(templateId);
		documentTemplate.setFileType(fileType);
		documentTemplate.setTemplatePath(templatePathf);
		documentTemplate.setExt("doc");
		documentTemplate.setFileSaveName(fileSaveName);
		documentTemplate.setTime(new Date(System.currentTimeMillis()));
		documentTemplate.setCreaterID(staffID);
		}else{
			
			String fileSaveName = "excel_" + RandomDatas.getRandomString(3);
			String companyID = documentTemplate.getCompanyId();
			String templatePathDir = "document" + sep + "template" + sep
					+ companyID;
			templatePathf = templatePathDir + sep + fileSaveName + ".xls";
			File fileSaveDir = new File(path + templatePathDir);
			File fileSave = new File(path + templatePathDir, fileSaveName + ".xls");
			File blankword = new File(path + "document" + sep + "template" + sep
					+ "blank.xls");
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}
			FileCopyUtils.copy(blankword, fileSave);
			String templateId = "template_" + RandomDatas.getRandomString(5);
			documentTemplate.setTemplateId(templateId);
			documentTemplate.setFileType(fileType);
			documentTemplate.setTemplatePath(templatePathf);
			documentTemplate.setExt("xls");
			documentTemplate.setFileSaveName(fileSaveName);
			documentTemplate.setTime(new Date(System.currentTimeMillis()));
			documentTemplate.setCreaterID(staffID);
			
		}
		baseBeanDao.save(documentTemplate);
      
		return templatePathf;

	}

	// 删除模板的word文件
	public void delFileDoc(String templateFullPath) {

		try {
			File file = new File(templateFullPath);
			if (!file.isDirectory()) {
				file.delete();
			}
		} catch (Exception e) {
			logger.info("删除文件夹出错");
		}

	}

	public void delDocumentTemplateRecord(String templateId) {
		// 删除 documentTemplate表记录
		Object[] params = { templateId };
		String delhql = "delete from DocumentTemplate where templateId = ?";
		try {
			baseBeanDao.saveBeansListAndexecuteHqlsByParams(null,
					new String[] { delhql }, params);
		} catch (Exception e) {
			logger.error("操作异常", e);
		}

	}
}