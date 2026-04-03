package hy.ea.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public interface UpLoadFileService {
	// 保存上传图片
	String savePhoto(String path, String photoContentType, File photo,
			String companyID, String storedpath);

	// 根据路径删除图片
	void deletePhotos(ArrayList<String> paths);

	// 根据 x轴、y轴、宽度、高度、倍数 截取并保存图片
	String interceptionSavePictures(String Suffix,String path, String fileType, File photo,
			String companyID, String storedpath,double x,double y,double width,double height,double rate) throws IOException;
	// 将字符串写入文件：word
	String saveFile(String path, String photoContentType, String strs,
			String companyID, String storedpath, String isNull);

	// 根据地址獲取文件內容
	String getFile(String path, String filepath);

	// 多文件上传返回多个文件的文件名数组mz
	String[] mutiFileUpload(String path, String[] sourcePathFileName,
			File[] sourcePath, String companyID, String storedpath);

	String[] mutiFileUpload(String path, String[] sourcePathFileName,
			String[] sourcePath, String companyID, String storedpath);

	/**
	 * 文件上传（分片上传合并文件）
	 *
	 * @param chunk     当前分片索引
	 * @param chunks    分片总数量
	 * @param fileName  原文件名
	 * @param file      文件
	 * @param path      路径
	 * @param companyid 所在公司id
	 * @return
	 */
	Map upLoadFile(String chunk, String chunks, String fileName, File file, String path, String companyid)throws Exception;

}
