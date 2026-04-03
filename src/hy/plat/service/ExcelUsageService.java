package hy.plat.service;

import java.io.InputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelUsageService {
	
	public Workbook getWorkbook(String fileName);
	
	public Workbook getWorkbook(InputStream in);
	
	public Sheet getSheet(String fileName,String sheetName);
	
	public Sheet getSheet(InputStream in,String sheetName);
	
	public Sheet getSheet(Workbook wb,String sheetName);
	
	public Sheet getSheet(Workbook wb,int order);
	
	public Row getFirstRow(Sheet sheet);
	
	public Row getLastRow(Sheet sheet);
	
	public Row getRow(Sheet sheet,int num);
	
	public Cell getCell(Row row,int num);
	
	public List<Cell> getColumn(Sheet sheet,int num);
	

}
