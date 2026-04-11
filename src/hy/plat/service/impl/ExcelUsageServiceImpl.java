package hy.plat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hy.plat.service.ExcelUsageService;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
@Service
public class ExcelUsageServiceImpl implements ExcelUsageService {

	@Override
	public Workbook getWorkbook(String fileName){		
		try {
			return getWorkbook(new BufferedInputStream(new FileInputStream(fileName)));
		} catch (FileNotFoundException e) {			
			logger.error("操作异常", e);
			return null;
		}
	}

	@Override
	public Workbook getWorkbook(InputStream in){		
		try {
			return  WorkbookFactory.create(in);
		} catch (InvalidFormatException e) {			
			logger.error("操作异常", e);
			return null;
		} catch (IOException e) {			
			logger.error("操作异常", e);
			return null;
		}
	}

	@Override
	public Sheet getSheet(String fileName, String sheetName) {		
		return getWorkbook(fileName).getSheet(sheetName);
	}

	@Override
	public Sheet getSheet(InputStream in, String sheetName) {		
		return getWorkbook(in).getSheet(sheetName);
	}

	@Override
	public Sheet getSheet(Workbook wb, String sheetName) {		
		return wb.getSheet(sheetName);
	}
	
	@Override
	public Sheet getSheet(Workbook wb, int order) {		
		return wb.getSheetAt(order);
	}

	@Override
	public Row getFirstRow(Sheet sheet) {		
		return sheet.getRow(0);
	}

	@Override
	public Row getLastRow(Sheet sheet) {		
		return sheet.getRow(sheet.getLastRowNum());
	}
	
	@Override
	public Row getRow(Sheet sheet, int num) {		
		return sheet.getRow(num);
	}

	@Override
	public Cell getCell(Row row, int num) {		
		return row.getCell(num);
	}

	@Override
	public List<Cell> getColumn(Sheet sheet, int num) {
		List<Cell> column = new ArrayList<Cell>();
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			column.add(getRow(sheet,i).getCell(num));//跳过2行，从第三行开始取(认为行号从0开始)
		}
		return column;
	}	
}
