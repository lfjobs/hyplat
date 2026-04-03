package hy.ea.office.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface ZOfficeService {
   /**
    * 
    * 创建office,如果templatepath为空则创建空白模板
    * @param realpath 工程路径
    * @param fileType 文件格式.W,E
    * @param templatepath 根据模板创建文件
    * @param storepath 创建文件后存储的位置；
    * @return
    */
	public String  createOffice(String realpath,String fileType,String templatepath,String storepath);

	
	
	public void saveOffice(HttpServletRequest request,HttpServletResponse response,String realpath,String storepath);
   
	/**
    * 删除Office文件根据文件实际路径
    */
	public void deleteOffice(String  filePath);
	
	/**
	 * 
	 * 拷贝文件
	 * @param filePath
	 * @return
	 */
	public String CopyOfficeFile(String realpath,String filePath);


}
