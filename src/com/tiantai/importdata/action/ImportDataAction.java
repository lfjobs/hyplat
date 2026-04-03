package com.tiantai.importdata.action;

import hy.plat.bo.BaseBean;
import hy.plat.service.BaseBeanService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.tiantai.importdata.entity.File;
import com.tiantai.importdata.exception.ImportException;
import com.tiantai.importdata.exception.InvalidAttributeException;
import com.tiantai.importdata.util.ValidateDataAndPrepareBean;
import com.tiantai.importdata.util.XmlInitializeUtil;

/**
 * 
 * @author zhb
 *
 */

@Controller
@Scope("prototype")
public class ImportDataAction {
	//标示文件类别(见ImportDataFileList.xml中的type节点，与<type>中的值对应)
	private String fileType1;
	private String fileType2;
	private String fileType3;
	private String fileType4;
	private String fileType5;
	//标示文件名称，每次最多上传5个文件。
	private String fileName1;
	private String fileName2;
	private String fileName3;
	private String fileName4;
	private String fileName5;
	
	private List<String> fileTypeList;//保存上传的文件类别
	private List<String> fileNameList;//保存上传的文件名称
	
	@Resource
	private BaseBeanService baseBeanService;
	
	/**
	 *为文件类别、文件名称复制(这些数据是通过ajax方法调用传递过来的)
	 */
	private void initList(){
		fileTypeList = new ArrayList<String>();
		fileNameList = new ArrayList<String>();
		if ((fileType1!=null) && (!fileType1.equals(""))) fileTypeList.add(fileType1);
		if ((fileType2!=null) && (!fileType2.equals(""))) fileTypeList.add(fileType2);
		if ((fileType3!=null) && (!fileType3.equals(""))) fileTypeList.add(fileType3);
		if ((fileType4!=null) && (!fileType4.equals(""))) fileTypeList.add(fileType4);
		if ((fileType5!=null) && (!fileType5.equals(""))) fileTypeList.add(fileType5);
		
		if ((fileName1!=null) && (!fileName1.equals(""))) fileNameList.add(fileName1);
		if ((fileName2!=null) && (!fileName2.equals(""))) fileNameList.add(fileName2);
		if ((fileName3!=null) && (!fileName3.equals(""))) fileNameList.add(fileName3);
		if ((fileName4!=null) && (!fileName4.equals(""))) fileNameList.add(fileName4);
		if ((fileName5!=null) && (!fileName5.equals(""))) fileNameList.add(fileName5);
	}
	/**
	 * 显示数据导入界面
	 * @return 返回action result的name属性值
	 */
	public String showImportDataPage()
	{
		Map<String, File> map;
		try {
			map = XmlInitializeUtil.getFileMap();
			ServletActionContext.getRequest().setAttribute("fileMap", map);	
		} catch (InvalidAttributeException e) {			
			e.printStackTrace();
		}			
		return "show";
	}
	/**
	 * 验证数据的合法性
	 * @return 返回action result的name属性值
	 */
	public String validateFileData()
	{
 		initList();
 		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
 		if (fileTypeList.size()>0){
 			boolean isError = false;
 			ValidateDataAndPrepareBean viBean =  new ValidateDataAndPrepareBean(null,null);
 			for (int i = 0; i < fileTypeList.size(); i++) {
				String type = fileTypeList.get(i);				
				String name = ServletActionContext.getServletContext().getRealPath("/upload/temp/") + System.getProperty("file.separator") + fileNameList.get(i);
				viBean.setFileType(type);
				viBean.setFileName(name);
				try {
					viBean.validate();
				} catch (ImportException e) {
					isError = true;					
					try {
						response.getWriter().println("系统正在检查文件" + fileNameList.get(i) + "时发现问题如下: ");//在这里可以获取到底层异常跑出的错误信息，拼接合理后就可以返回给response，再由js展现在页面
						response.getWriter().println( e.getMessage());
					} catch (IOException e1) {
						e1.printStackTrace();
					}					
					e.printStackTrace();
					break;
				}
			}
 			//
 			if (!isError){
 				try {
					response.getWriter().print("系统验证文件无误");
				} catch (Exception e) {					
					e.printStackTrace();
				}
 			}
 		}else{
 			//
 			try {
				response.getWriter().print("没有发现文件");
			} catch (Exception e) {				
				e.printStackTrace();
			}
 		}
 		return null;
	}
	
	/**
	 * 导入数据到数据库中
	 * @return 返回action result的name属性值
	 */
	public String importData2DataBase()
	{
		initList();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		if (fileTypeList.size()>0){ 
			boolean isError = false;
			ValidateDataAndPrepareBean viBean =  new ValidateDataAndPrepareBean(null,null);
			viBean.setSessionMap(ActionContext.getContext().getSession());
			for (int i = 0; i < fileTypeList.size(); i++) {
				String type = fileTypeList.get(i);
				String name = ServletActionContext.getServletContext().getRealPath("/upload/temp/") + System.getProperty("file.separator") + fileNameList.get(i);
				viBean.setFileType(type);
				viBean.setFileName(name);
				try {
					viBean.prepare();
					importData(viBean);
				} catch (Exception e) {
					isError = true;
					try {
						response.getWriter().println("系统导入文件" + fileNameList.get(i) + "时发现问题如下: ");//在这里可以获取到底层异常跑出的错误信息，拼接合理后就可以返回给response，再由js展现在页面
						response.getWriter().println( e.getMessage());
					} catch (IOException e1) {
						e1.printStackTrace();
					}					
					e.printStackTrace();
					break;
				}						
			}
			//
 			if (!isError){
 				try {
					response.getWriter().print("数据导入成功");
				} catch (Exception e) {					
					e.printStackTrace();
				}
 			}
		}else{
 			//
 			try {
				response.getWriter().print("没有发现文件");
			} catch (Exception e) {				
				e.printStackTrace();
			}
 		} 		 		
		return null;
	}
	/**
	 * 导入数据的方法
	 * @param viBean 这个bean中或者保存了构造出的对象bean列表，或者保存了插入数据库的insert语句(二者只有一个，bean列表优先考虑)
	 */
	private void importData(ValidateDataAndPrepareBean viBean){
		//以BaseBean形式向数据库中插入的形式优先考虑(一次写入最多50)
		if (viBean.getBeanList()!=null && viBean.getBeanList().size()>0){
			List<BaseBean> list = viBean.getBeanList();			
			List<BaseBean> tmpList = new ArrayList<BaseBean>();
			int counter = 0;
			for (BaseBean baseBean : list) {
				tmpList.add(baseBean);
				counter++;
				if (counter==50){
					baseBeanService.saveBeansListAndexecuteHqlsByParams(tmpList,null,null);
					tmpList.clear();
					counter = 0;
				}
			}
			
			if (!tmpList.isEmpty())
			{
				baseBeanService.saveBeansListAndexecuteHqlsByParams(tmpList,null,null);
			}			
			
		}else if (viBean.getSqlArray()!=null && viBean.getSqlArray().length>0){
			String[] sqlArray = viBean.getSqlArray();
			
			List<String> list = new ArrayList<String>();
			int counter = 0;
			for (String string : sqlArray) {
				list.add(string);
				counter++;
				if (counter==50){
					baseBeanService.executeSqlsByParmsList(null, (String[])list.toArray(), null);
					list.clear();
					counter = 0;
				}
			}
			
			if (!list.isEmpty())
			{
				baseBeanService.executeSqlsByParmsList(null, (String[])list.toArray(), null);
			}
			
		}
		
	}

	public String getFileType1() {
		return fileType1;
	}

	public void setFileType1(String fileType1) {
		this.fileType1 = fileType1;
	}

	public String getFileType2() {
		return fileType2;
	}

	public void setFileType2(String fileType2) {
		this.fileType2 = fileType2;
	}

	public String getFileType3() {
		return fileType3;
	}

	public void setFileType3(String fileType3) {
		this.fileType3 = fileType3;
	}

	public String getFileType4() {
		return fileType4;
	}

	public void setFileType4(String fileType4) {
		this.fileType4 = fileType4;
	}

	public String getFileType5() {
		return fileType5;
	}

	public void setFileType5(String fileType5) {
		this.fileType5 = fileType5;
	}

	public String getFileName1() {
		return fileName1;
	}

	public void setFileName1(String fileName1) {
		this.fileName1 = fileName1;
	}

	public String getFileName2() {
		return fileName2;
	}

	public void setFileName2(String fileName2) {
		this.fileName2 = fileName2;
	}

	public String getFileName3() {
		return fileName3;
	}

	public void setFileName3(String fileName3) {
		this.fileName3 = fileName3;
	}

	public String getFileName4() {
		return fileName4;
	}

	public void setFileName4(String fileName4) {
		this.fileName4 = fileName4;
	}

	public String getFileName5() {
		return fileName5;
	}

	public void setFileName5(String fileName5) {
		this.fileName5 = fileName5;
	}
	
	public static void main(String[] args) {
		ImportDataAction action = new ImportDataAction();
		action.validateFileData();
	}

}
