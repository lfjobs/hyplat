package hy.ea.service.impl;

import hy.ea.service.UpLoadFileService;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import hy.ea.util.Utilities;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpLoadFileServiceImpl implements UpLoadFileService {
	/**
	 * @param path
	 * @param fileType
	 *            传过来文件名称 （根据名称截取文件后缀类型）
	 * 
	 */
	@Override
	public synchronized String savePhoto(String path, String fileType,
			File photo, String companyID, String storedpath) {
		String savePath = path + "upload_files/" + companyID + "/" + storedpath;
		String type = fileType.substring(fileType.lastIndexOf(".") + 1);
		String fileName="" ;
		if(type.equals("apk"))
			
		{
			fileName=fileType.substring(0,fileType.lastIndexOf("."));
		
		}
		else if(type.equals("ipa"))
		{
			fileName=fileType.substring(0,fileType.lastIndexOf("."));
		}else
		{
			fileName	= UUID.randomUUID().toString().replaceAll("-", "");
		}

		String photoPath = savePath + "/" + fileName + "." + type;
		File dFile = new File(savePath);
		if (!dFile.exists()) {
			// 如果文件夹不存在则建一个
			dFile.mkdirs();
		}

		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(photoPath);
			fis = new FileInputStream(photo);
			byte[] buffer = new byte[1024 * 8];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {

		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
		return "upload_files/" + companyID + "/" + storedpath + "/" + fileName + "." + type;
	}

	@Override
	public synchronized void deletePhotos(ArrayList<String> paths) {
		for (int i = 0; i < paths.size(); i++) {
			File file = new File(paths.get(i));
			try {
				if (file.exists() && file.isFile()) {
					// 如果文件存在则删除
					file.delete();
				}
			} catch (Exception e) {
			} finally {
			}
		}
	}

	/**
	 *
	 * @param path
	 * @param photoContentType
	 * @param strs
	 * @param companyID
	 * @param storedpath
	 * @param isNull
	 * @return
	 */
	@Override
	public String saveFile(String path, String photoContentType, String strs,
			String companyID, String storedpath, String isNull) {
		String savePath = path + "upload_files/" + companyID + "/" + storedpath;
		String fileName = UUID.randomUUID().toString().replaceAll("-", "");
		String photoPath = savePath + "/" + fileName + "." + photoContentType;
		File dFile = new File(savePath);
		if (!dFile.exists()) {
			dFile.mkdirs();
		}
		File dFile1 = new File(path + isNull);
		String fileUrl = path + "upload_files/" + companyID + "/" + storedpath + "/" + fileName + "."
				+ photoContentType;
		if (dFile1.exists()) {
			dFile1.delete();
			photoPath = path + isNull;
			fileUrl = isNull;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(photoPath);
			byte[] ss = strs.getBytes();
			fos.write(ss);
		} catch (Exception e) {

		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
		return fileUrl;
	}

	/**
	 * 根据路径获取文件内容
	 */
	@Override
	public String getFile(String path, String filepath) {
		if ("".equals(filepath) && filepath == null) {
			return "";
		}
		File dFile = new File(path + filepath);
		if (!dFile.exists()) {
			return "";
		}
		String fileContent = "";
		FileInputStream fis = null;
		InputStreamReader isr = null;
		try {
			fis = new FileInputStream(dFile);
			isr = new InputStreamReader(fis, "gbk");
			BufferedReader br = new BufferedReader(isr);
			// 可变字符串
			StringBuffer sb = new StringBuffer();
			// 存储一行数据（br.readLine每次读写一行)
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			fileContent = new String(sb.toString().getBytes("gbk"), "gbk");

		} catch (Exception e) {

		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
		return fileContent;
	}

	// mz用户文件柜上传
	public String[] mutiFileUpload(String path, String[] sourcePathFileName,
			File[] sourcePath, String companyID, String storedpath) {
		String realPath = path + storedpath + "\\" + companyID + "\\";
		// String[] targetFileName = sourcePathFileName;
		String targetFileName = "";
		String[] targetFileNames = new String[sourcePathFileName.length];
		for (int i = 0; i < sourcePath.length; i++) {
		   int index = sourcePathFileName[i].lastIndexOf('.');
			String ext = sourcePathFileName[i].substring(index);
			targetFileName = UUID.randomUUID().toString().replaceAll("-", "")
					+ ext;
			targetFileNames[i] = targetFileName;
			File realf= new File(realPath);
			if(!realf.exists()){ 
				realf.mkdirs();
			}
			File target = new File(realPath, targetFileName);
			File dFile = new File(realPath);
			if (!dFile.exists()) {
				dFile.mkdirs();
			}
			try {
				FileUtils.copyFile(sourcePath[i], target);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return targetFileNames;

	}

	// mz用户图片管理上传
	@SuppressWarnings("unused")
	public String[] mutiFileUpload(String path, String[] sourcePathFileName,
			String[] sourcePath, String companyID, String storedpath) {
		String realPath = path + storedpath + companyID;
		// String[] targetFileName = sourcePathFileName;
		String fullPath = "";
		String targetFileName = "";
		String[] targetFileNames = new String [sourcePathFileName.length];
		for (int i = 0; i < sourcePath.length; i++) {
			String ext = sourcePathFileName[i].substring(sourcePathFileName[i]
					.lastIndexOf('.'));
			targetFileName = UUID.randomUUID().toString().replaceAll("-", "")
					+ ext;
			targetFileNames[i] = targetFileName;
			File target = new File(realPath, targetFileName);
			File dFile = new File(realPath);

			File sp = new File(sourcePath[i]);
			fullPath += "/" + storedpath + companyID + "/" + targetFileName
					+ ",";
			if (!dFile.exists()) {
				dFile.mkdirs();
			}
			try {
				FileUtils.copyFile(sp, target);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return targetFileNames;

	}

	@Override
	public String interceptionSavePictures(String Suffix,String path,
			String fileType, File photo, String companyID,
			String storedpath, double x, double y, double width, double height,double rate) throws IOException {
		FileInputStream is = null;
		ImageInputStream iis = null;
		String savePath = path + "upload_files/" + companyID + "/" + storedpath;
		String type = fileType.substring(fileType.lastIndexOf(".") + 1);
		String fileName="" ;
		if(type.equals("apk"))
		{	
			fileName=fileType.substring(0,3);	}
		else if(type.equals("ipa"))
		{	
			fileName=fileType.substring(0,3);
		}else{	
			fileName	= UUID.randomUUID().toString().replaceAll("-", "");
		}
		 try {
			 is=new FileInputStream(photo);
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(Suffix);
			ImageReader reader = it.next();
			 iis = ImageIO.createImageInputStream(is);
			 reader.setInput(iis, true);
			 ImageReadParam param = reader.getDefaultReadParam();
		   Rectangle rect = new Rectangle((int)(x*rate)+1,(int)(y*rate)+1, (int)(width*rate)+1,(int)(height*rate)+1);
		   param.setSourceRegion(rect);
		   BufferedImage bi = reader.read(0, param);
		   
		String photoPath = savePath + "/" + fileName + "." + Suffix;
		   File dFile = new File(photoPath);
			if (!dFile.exists()) {
				// 如果文件夹不存在则建一个
				dFile.mkdirs();
			}
		   ImageIO.write(bi,Suffix,dFile);
		 } finally {
		   if (is != null)
		    is.close();
		   if (iis != null)
		    iis.close();
		  }
		return "upload_files/" + companyID + "/" + storedpath + "/" + fileName + "." + Suffix;
	}

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
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public synchronized Map upLoadFile(String chunk, String chunks, String fileName, File file, String path, String companyid) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		//生成新的文件名 UUID+文件后缀
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		int index = fileName.lastIndexOf(".");
		String suffix = fileName.substring(index, fileName.length());
		String newFileName = name + suffix;
		String path2 = "/upload_files/" + companyid + "/product/"
				+ Utilities.getDateString(new Date(), "yyyy-MM-dd");
		//文件未分片直接存储
		//chunk当前分片 chunks分片总数量 当这两个值为空的时候 文件未进行分片上传
		if (chunk == null && chunks == null) {
			transferTo(path + path2, newFileName, file, map);
			map.put("path", path2 + "/" + newFileName);
			map.put("name", fileName);
			return map;
		}

		//文件分片

		//创建临时存储文件夹 并且 上传文件名称的格式为  当前分片-新的文件名称   下面会根据当前分片进行分片文件的数组排序
		//把文件传到临时文件夹中
		transferTo(path + path2 + "/" + fileName, chunk + "-" + newFileName, file, map);
		//如果文件为最后一片
		if (chunk != null && chunks != null) {
			Integer c = Integer.parseInt(chunk);
			Integer cc = Integer.parseInt(chunks);
			if (c.equals(cc - 1)) {
				//获取分片文件存储的临时文件夹
				File fileDir = new File(path + path2 + "/" + fileName);

				//获取文件夹下的所有文件
				File[] files = fileDir.listFiles();
				//根据文件名称进行排序
				files = sort(files);
				String compoundName = "分片合成后的文件" + newFileName;
				//合成的文件
				File resultFile = new File(path + path2, compoundName);

				if (!resultFile.getParentFile().exists()) {
					// 如果文件夹不存在则建一个
					resultFile.getParentFile().mkdirs();
				}
				boolean b = mergeFiles(files, resultFile);
				System.out.println(b ? "合并成功" : "合并失败");
				//在合成文件的时候已经删除临时文件 现在删除临时文件夹
				fileDir.delete();
				map.put("siSuccess", true);
				map.put("path", path2 + "/" + compoundName);
				map.put("name", fileName);
			}
		}
		System.out.println(path + path2);
		return map;
	}

	/**
	 * 合并文件
	 *
	 * @param files      分片文件集合
	 * @param resultFile 合并文件
	 * @return
	 */
	private static boolean mergeFiles(File[] files, File resultFile) {
		try {
			FileChannel resultFileChannel = new FileOutputStream(resultFile, true).getChannel();

			for (int i = 0; i < files.length; i++) {
				FileChannel blk = new FileInputStream(files[i]).getChannel();
				resultFileChannel.transferFrom(blk, resultFileChannel.size(), blk.size());
				blk.close();
			}
			resultFileChannel.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		for (int i = 0; i < files.length; i++) {
			System.out.println("临时文件:" + files[i].getName() + " 已删除");
			files[i].delete();
		}
		return true;
	}
	/**
	 * 文件上传
	 *
	 * @param savePath 文件保存路径
	 * @param newName  新文件名称
	 * @param photo    需要上传的文件
	 * @param map      返回参数
	 */
	public synchronized void transferTo(String savePath, String newName, File photo, Map<String, Object> map) throws Exception {
		String photoPath = savePath + "/" + newName;
		File dFile = new File(savePath);
		if (!dFile.exists()) {
			// 如果文件夹不存在则建一个
			dFile.mkdirs();
		}

		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(photoPath);
			fis = new FileInputStream(photo);
			byte[] buffer = new byte[1024 * 8];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {

				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
		map.put("siSuccess", true);
	}
	/**
	 * 根据文件名称进行排序
	 *
	 * @param array
	 * @return
	 */
	private File[] sort(File[] array) {
		//对数组用冒泡法进行从小到大的排序
		File temp;
		//定义一个整型临时变量temp
		//用两层循环比较两个相邻的元素，将值大的元素交换至右端，一直循环比较n-1趟，直至顺序排列完毕
		//外层循环控制排序趟数
		for (int i = 0; i < array.length; i++) {
			//内层循环控制排序趟数
			for (int j = i + 1; j < array.length; j++) {
				//若数组元素i大于数组元素j(即第i个数大于第i+1个数)，执行判断语句,调换两数位置，即将较小数往左移
				if (Integer.parseInt(array[i].getName().split("-")[0]) >
						Integer.parseInt(array[j].getName().split("-")[0])) {
					//若第i个数大于第i+1个数，则交换位置
					//将第i+1个数放到temp中，array[i] —> temp
					temp = array[i];
					//第j个数的值等于第j+1个数的值; array[j]—> array[i]
					array[i] = array[j];
					//第i+1个数的值=原始存在temp的 值;temp—> array[j]
					array[j] = temp;
				}
			}
		}
		for (File file : array) {
			System.out.println(file.getName());
		}
		return array;
	}
}