package hy.ea.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ffmpeg视频格式转换
 * 
 * @author mz
 * 
 */
public class FFmpegTransfer {
	private static final Logger logger = LoggerFactory.getLogger(FFmpegTransfer.class);
	public static int COMPLETE;
	public static Logger logger = LoggerFactory.getLogger(FFmpegTransfer.class);
	/**
	 * 视频转码
	 * 
	 * @param ffmpegPath
	 *            转码工具的存放路径
	 * @param upFilePath
	 *            用于指定要转换格式的文件,要截图的视频源文件
	 * @param mediaPicPath
	 *            截图保存路径
	 * @return
	 * @throws Exception
	 */
	public static boolean executeCodecs(String ffmpegPath, String upFilePath,
			String mediaPicPath) throws Exception {

		// 创建一个List集合来保存从视频中截取图片的命令
		List<String> cutpic = new ArrayList<String>();
		cutpic.add(ffmpegPath);
		cutpic.add("-i");
		cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
		cutpic.add("-y");
		cutpic.add("-f");
		cutpic.add("image2");
		cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
		cutpic.add("1"); // 添加起始时间为第17秒
		cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
		cutpic.add("0.001"); // 添加持续时间为1毫秒
		cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
		cutpic.add("350*240"); // 添加截取的图片大小为350*240
		cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

		boolean mark = true;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(cutpic);
			builder.start();
		} catch (Exception e) {
			mark = false;
			logger.info("值：{}", e);
			logger.error("操作异常", e);
		}
		return mark;
	}

	/**
	 * 视频截图
	 * 
	 * @param ffmpeg_path
	 *            转码工具的存放路径
	 * @param veido_path
	 *            用于指定要转换格式的文件,要截图的视频源文件
	 * @return
	 * @throws Exception
	 */
	public static boolean processImg(String veido_path, String ffmpeg_path) {
		File file = new File(veido_path);
		if (!file.exists()) {
			logger.error("路径[" + veido_path + "]对应的视频文件不存在!");
			return false;
		}
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpeg_path);
		commands.add("-i");
		commands.add(veido_path);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("1");// 这个参数是设置截取视频多少秒时的画面
		// commands.add("-t");
		// commands.add("0.001");
		commands.add("-s");
		commands.add("700x525");
		commands.add(veido_path.substring(0, veido_path.lastIndexOf("."))
				.replaceFirst("vedio", "file") + ".jpg");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			builder.start();
			logger.info("截取成功");
			return true;
		} catch (Exception e) {
			logger.error("操作异常", e);
			return false;
		}
	}

	public static boolean mp4Th254(String ffmpegPath, String upFilePath,
			String codcFilePath) {

		// 创建一个List集合来保存转换视频文件为mp4格式的命令

		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // 添加转换工具路径
		convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		convert.add(upFilePath); // 添加要转换格式的视频文件的路径
		convert.add("-qscale"); // 指定转换的质量
		convert.add("6");
		convert.add("-acodec");// 指定编码格式
		convert.add("libmp3lame");
		convert.add("-ab"); // 设置音频码率
		convert.add("64");
		convert.add("-ac"); // 设置声道数
		convert.add("2");
		convert.add("-ar"); // 设置声音的采样频率
		convert.add("48000");
		convert.add("-r"); // 设置帧频
		convert.add("29.97");
		convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
		convert.add(codcFilePath);

		boolean mark = true;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(convert);
			builder.start();
		} catch (Exception e) {
			mark = false;
			logger.info("值：{}", e);
			logger.error("操作异常", e);
		}
		return mark;

	}

	public static boolean mp4H264(String ffmpegPath, String upFilePath,
			String codcFilePath,String img_path) {

		// 创建一个List集合来保存转换视频文件为mp4格式的命令

		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // 添加转换工具路径
		convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		convert.add(upFilePath); // 添加要转换格式的视频文件的路径
		convert.add("-c:v"); // 指定转换的质量
		convert.add("libx264");
		convert.add("-strict");// 指定编码格式 
		convert.add("-2");
		convert.add("-qscale"); // 指定转换的质量
		convert.add("6");
		convert.add("-acodec");// 指定编码格式
		convert.add("libmp3lame");
		convert.add("-ab"); // 设置音频码率
		convert.add("64");
		convert.add("-ac"); // 设置声道数
		convert.add("2");
		convert.add("-ar"); // 设置声音的采样频率
		convert.add("48000");
		convert.add("-r"); // 设置帧频
		convert.add("29.97");
		convert.add("-aspect");
		convert.add("16:16");
		convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
		convert.add(codcFilePath);
		
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpegPath);
		commands.add("-i");
		commands.add(codcFilePath);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("1");// 这个参数是设置截取视频多少秒时的画面
		// commands.add("-t");
		// commands.add("0.001");
		commands.add("-s");
		commands.add("700x525");
		commands.add(img_path);
		
		boolean mark = true;
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(convert);
			Process p = builder.start();
			doWaitPro(p);
			p.destroy();
			builder.command(commands);
			builder.redirectErrorStream(true);
			builder.start();
		} catch (Exception e) {
			mark = false;
			logger.info("值：{}", e);
			logger.error("操作异常", e);
		}
		return mark;

	}

	// 等待线程处理完成
	public static void doWaitPro(Process p) {
		try {
			String errorMsg = readInputStream(p.getErrorStream(), "error");
			String outputMsg = readInputStream(p.getInputStream(), "out");
			int c = p.waitFor();
			if (c != 0) {// 如果处理进程在等待
				logger.error("处理失败：" + errorMsg);
			} else {
				logger.error(COMPLETE + outputMsg);
			}
		} catch (IOException e) {
			// tanghui Auto-generated catch block
			logger.error("操作异常", e);
		} catch (InterruptedException e) {
			// tanghui Auto-generated catch block
			logger.error("操作异常", e);
		}

	}
	
	/**
	 * 
	 * @Title: readInputStream
	 * @Description: 完成进度百分比
	 * @param
	 * @return String
	 * @throws
	 */
	private static String readInputStream(InputStream is, String f)
			throws IOException {
		// 将进程的输出流封装成缓冲读者对象
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer lines = new StringBuffer();// 构造一个可变字符串
		long totalTime = 0;

		// 对缓冲读者对象进行每行循环
		for (String line = br.readLine(); line != null; line = br.readLine()) {
			lines.append(line);// 将每行信息字符串添加到可变字符串中
			int positionDuration = line.indexOf("Duration:");// 在当前行中找到第一个"Duration:"的位置
			int positionTime = line.indexOf("time=");
			if (positionDuration > 0) {// 如果当前行中有"Duration:"
				String dur = line.replace("Duration:", "");// 将当前行中"Duration:"替换为""
				dur = dur.trim().substring(0, 8);// 将替换后的字符串去掉首尾空格后截取前8个字符
				int h = Integer.parseInt(dur.substring(0, 2));// 封装成小时
				int m = Integer.parseInt(dur.substring(3, 5));// 封装成分钟
				int s = Integer.parseInt(dur.substring(6, 8));// 封装成秒
				totalTime = h * 3600 + m * 60 + s;// 得到总共的时间秒数
			}
			if (positionTime > 0) {// 如果所用时间字符串存在
				// 截取包含time=的当前所用时间字符串
				String time = line.substring(positionTime,
						line.indexOf("bitrate") - 1);

				time = time.substring(time.indexOf("=") + 1, time.indexOf("."));// 截取当前所用时间字符串
				int h = Integer.parseInt(time.substring(0, 2));// 封装成小时
				int m = Integer.parseInt(time.substring(3, 5));// 封装成分钟
				int s = Integer.parseInt(time.substring(6, 8));// 封装成秒
				long hasTime = h * 3600 + m * 60 + s;// 得到总共的时间秒数
				float t = (float) hasTime / (float) totalTime;// 计算所用时间与总共需要时间的比例
				COMPLETE = (int) Math.ceil(t * 100);// 计算完成进度百分比
			}
			logger.error("完成：" + COMPLETE + "%");
		}
		br.close();// 关闭进程的输出流
		return lines.toString();
	}

	public static String getStandardTime(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",
				Locale.getDefault());
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		Date date = new Date(timestamp * 1000);
		sdf.format(date);
		return sdf.format(date);
	}
	
	
	
	public static void main(String[] args) {

//		String codcFilePath = "D:\\video\\7890101011.mp4"; // 设置转换为mp4格式后文件的保存路径
//		String mediaPicPath = "D:\\video\\117.jpg"; // 设置上传视频截图的保存路径
//		// 获取配置的转换工具（ffmpeg.exe）的存放路径
//		String ffmpegPath = "D:\\ffmpeg\\bin\\ffmpeg.exe";
//
//		String srcFile = "D:\\video\\123.avi";

		// FFmpegTransfer.executeCodecs(ffmpegPath, srcFile, mediaPicPath);
//		FFmpegTransfer.mp4H264(ffmpegPath, srcFile, codcFilePath);
		// FFmpegTransfer.coverToFLV(codcFilePath, srcFile, ffmpegPath);

	}
}
