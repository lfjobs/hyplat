package hy.ea.service;

import java.io.IOException;

public interface UploadContentToFileService {
	
	public String suffix = ".txt";
	/**
	 * 将内容保存成指定文件（以id命名）
	 * @param id 保存对象的ID
	 * @param content 在线编辑的对象内容
	 * @param path 保存文件的绝对路径
	 */
	public void saveContent(String id, String content, String path) throws IOException;

	/**
	 * 将内容保存成指定文件（以id命名）
	 * @param content 在线编辑的对象内容
	 * @param path 保存文件的绝对路径
	 */
	public void saveContent(String content, String path) throws IOException;
	
	/**
	 * 读取指定文件的内容
	 * @param path 文件绝对路径
	 * @return 文件正文
	 * @throws IOException
	 */
	public String getContent(String path) throws IOException;
	
}
