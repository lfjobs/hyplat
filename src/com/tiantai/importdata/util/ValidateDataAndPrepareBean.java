package com.tiantai.importdata.util;

import hy.plat.bo.BaseBean;
import hy.plat.service.ExcelUsageService;
import hy.plat.service.impl.ExcelUsageServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.tiantai.importdata.entity.Entity;
import com.tiantai.importdata.entity.File;
import com.tiantai.importdata.entity.TemplateField;
import com.tiantai.importdata.exception.InvalidAttributeException;
import com.tiantai.importdata.exception.ValidateFaultException;
import com.tiantai.importdata.validator.Validator;
/**
 * 验证excel文件，和准备数据导入模型的util类
 * @author zhb
 *
 */
public class ValidateDataAndPrepareBean {
	
	
	private ExcelUsageService excelUsageService = new ExcelUsageServiceImpl();
	
	private String fileType;
	private String fileName;
	
	private String[] sqlArray;
	private List<BaseBean> beanList; 
	
	private Map<String, Object> sessionMap;
	
	
	public ValidateDataAndPrepareBean(String fileType,String fileName) {
		this.fileType = fileType;
		this.fileName = fileName;
	}	
	
	public void validate() throws ValidateFaultException, InvalidAttributeException{		
		File file = XmlInitializeUtil.getFile(fileType);
		Entity entity = file.getEntity();				
		Workbook wb = excelUsageService.getWorkbook(fileName);
		Sheet sheet = excelUsageService.getSheet(wb, 0);
		
		List<TemplateField> templateFields = entity.getFields();
		for (TemplateField templateField : templateFields) {
			validate(templateField,sheet);
		}		
	}
	
	private void validate(TemplateField templateField, Sheet sheet)  throws ValidateFaultException{
		if (!templateField.getNeedImport()) return;
		List<Cell> column = excelUsageService.getColumn(sheet, templateField.getOrder());		
		List<Validator> validators = templateField.getValidators();
		Validator[] a = new Validator[validators.size()];
		validators.toArray(a);
		for (int i = 0; i < a.length-1; i++) {
			a[i].setNext(a[i+1]);
		}
		
		for (Cell cell : column) {
			if (cell!=null)
				a[0].chainAfterDeal(cell.getStringCellValue(), cell.getRowIndex(), cell.getColumnIndex());		
		}		
	}
	
	public void prepare() throws Exception{		
		Entity entity = XmlInitializeUtil.getFile(fileType).getEntity();
		//entity的table属性和class属性都存在时,class优先考虑
		if (entity.getClassName()!=null && !"".equals(entity.getClassName())){
			prepare(entity);
			
		}else if(entity.getTable()!=null && !"".equals(entity.getTable())){
			//TODO
		}
	}
	
	private void prepare(Entity entity) throws Exception{		
		Workbook wb = excelUsageService.getWorkbook(fileName);
		Sheet sheet = excelUsageService.getSheet(wb, 0);
				
		ExcelConfig config = new ExcelConfig(2);//从第三行开始读取数据       认为行号从0开始
		ExcelReader reader = new ExcelReader(config);
		reader.setEntity(entity);
		reader.setSheet(sheet);
		reader.InitExcelReader();
		reader.setSessionMap(this.sessionMap);
		
		beanList = new ArrayList<BaseBean>();
		while(true){
			Object object = reader.readLine();
			if (object!=null) beanList.add((BaseBean)object);
			else{
				break;
			}
		}		
	}	
	
	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getSqlArray() {
		return sqlArray;
	}

	public void setSqlArray(String[] sqlArray) {
		this.sqlArray = sqlArray;
	}

	public List<BaseBean> getBeanList() {
		return beanList;
	}

	public void setBeanList(List<BaseBean> beanList) {
		this.beanList = beanList;
	}

	public Map<String, Object> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
	
	
}
