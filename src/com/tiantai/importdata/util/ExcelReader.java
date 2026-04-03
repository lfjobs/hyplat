package com.tiantai.importdata.util;

import hy.ea.bo.CAccount;
import hy.plat.bo.BaseBean;
import hy.plat.service.ServerService;
import hy.plat.service.impl.ServerServiceImpl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Sheet;

import com.tiantai.importdata.entity.Entity;
import com.tiantai.importdata.entity.Generator;
import com.tiantai.importdata.entity.TableField;
import com.tiantai.importdata.entity.TemplateField;
/**
 * excel文件的读取方法类
 * @author zhb
 *
 */
public class ExcelReader {
		
	private ExcelConfig excelConfig;
	private Sheet sheet;
	private Entity entity;
	private Map<String,Object> sessionMap;
	
	
	private int curPosition;
	
	
	private ServerService serverService = new ServerServiceImpl();
	
	public ExcelReader() {	
	}
	
	public ExcelReader(ExcelConfig excelConfig) {
		this.excelConfig = excelConfig;		
	}
	
	public void InitExcelReader(){
		this.curPosition = excelConfig.getBeginRowNum();
	}
	
	public BaseBean readLine() throws Exception{
		if (curPosition <= sheet.getLastRowNum()){
			int row = curPosition;
			curPosition++;
			return getLine(row);
		}
		return null;
	}
	
	private BaseBean getLine(int row) throws Exception {
		HSSFRow rowline = (HSSFRow) sheet.getRow(row);					
		BaseBean bean = (BaseBean)Class.forName(entity.getClassName()).newInstance();
		int columns = rowline.getLastCellNum();
		for (int i = 0; i < columns; i++) {
			TemplateField templateField = entity.getFields().get(i);
			if (templateField.getNeedImport()) {//这列数据需要导入
				HSSFCell cell = rowline.getCell(i);				
				if (cell != null) {
					String cellValue = null;
					switch (cell.getCellType()) {
					
					case HSSFCell.CELL_TYPE_STRING:
						cellValue = cell.getStringCellValue();
						Method sm = bean.getClass().getDeclaredMethod(
								"set" + FunctionUtil.A2UpperCase(templateField.getProperty()), String.class);
						sm.invoke(bean, cellValue);
						break;
					
					case HSSFCell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							cellValue = this.excelConfig.getFormater().format(date);
							//TODO
						}else{
							Integer num = new Integer((int) cell.getNumericCellValue());
							cellValue = String.valueOf(num);
							//TODO
						}
						
					default:
						
					}
				}
			}
		}
		
		List<TableField> tableFields = entity.getTableField();
		if (tableFields!=null){
			for (TableField tableField : tableFields) {
				Method sm = bean.getClass().getDeclaredMethod(
						"set" + FunctionUtil.A2UpperCase(tableField.getProperty()), String.class);
				Generator generator = tableField.getGenerator();
				String type = generator.getType();
				String id = null;
				if (type.equals("uuid")) id = UUID.randomUUID().toString().replaceAll("-", "");
				if (type.equals("sysid")) id = serverService.getServerID(generator.getPrefix());
				if (type.equals("sessionid")) {
					if (tableField.getProperty().equals("companyID")) id = ((CAccount) sessionMap.get("account")).getCompanyID();
					if (tableField.getProperty().equals("organizationID")) id = (String) sessionMap.get("organizationID");
					if (tableField.getProperty().equals("groupCompanySn")) id = (String) sessionMap.get("groupCompanySn");
				}
				
				sm.invoke(bean, id);
			}
		}
		
		return bean;
	}
		
	public ExcelConfig getExcelConfig() {
		return excelConfig;
	}

	public void setExcelConfig(ExcelConfig excelConfig) {
		this.excelConfig = excelConfig;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public Map<String,Object> getSessionMap() {
		return sessionMap;
	}

	public void setSessionMap(Map<String,Object> sessionMap) {
		this.sessionMap = sessionMap;
	}

}
