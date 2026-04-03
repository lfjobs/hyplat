package hy.ea.util.bean;
import java.io.File;
import java.util.List;
/**
 * 
 * @author zg
 *
 */
public class ExcelImport {
	private File excelFile;
	private String excelFileContentType;
	private List<String[]> lists;
	private String[] propers;// 字段name
	private int dataCount;
	private String importPath;//导入保存url
	private String exclePath;//导出模版url
	private int exclePathstatus;//导出模版提示 1为导出 0为放过
	
	/**
	 * 获取excel文件
	 * @return
	 */
	public File getExcelFile() {
		return excelFile;
	}

	/**
	 * 设置excel文件
	 * @param excelFile
	 */
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}
	/**
	 * 关联  （excelFile）字段
	 * @param excelFileContentType
	 */
	public String getExcelFileContentType() {
		return excelFileContentType;
	}

	/**
	 * 关联  （excelFile）字段
	 * @param excelFileContentType
	 */
	public void setExcelFileContentType(String excelFileContentType) {
		this.excelFileContentType = excelFileContentType;
	}

	/**
	 * 获取excel转换成数据的对象
	 * @return
	 */
	public List<String[]> getLists() {
		return lists;
	}
	/**
	 * 将excel转换成数据存储到此对象中
	 * @return
	 */
	public void setLists(List<String[]> lists) {
		this.lists = lists;
	}

	/**
	 * 字段的名称
	 * @return
	 */
	public String[] getPropers() {
		return propers;
	}

	/**
	 * 设置字段的名称
	 * @param propers
	 */
	public void setPropers(String[] propers) {
		this.propers = propers;
	}

	/**
	 * 获取对象中共有多少字段
	 * @return
	 */
	public int getDataCount() {
		return dataCount;
	}

	/**
	 * 设置字段
	 * @param dataCount
	 */
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	public String getImportPath() {
		return importPath;
	}

	public void setImportPath(String importPath) {
		this.importPath = importPath;
	}

	public String getExclePath() {
		return exclePath;
	}

	public void setExclePath(String exclePath) {
		this.exclePath = exclePath;
	}

	public int getExclePathstatus() {
		return exclePathstatus;
	}

	public void setExclePathstatus(int exclePathstatus) {
		this.exclePathstatus = exclePathstatus;
	}
	
	

}
