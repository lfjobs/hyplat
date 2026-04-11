package hy.ea.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


	public class DownloadImage {
	private static final Logger logger = LoggerFactory.getLogger(DownloadImage.class);

   /**
     * @param args
    * @throws Exception
     */
    public static void main(String[] args) throws Exception {
	        download("http://store.is.autonavi.com/showpic/f0bfcf0fe4652566312e3d26d2433c8c", "51bi.jpg","c:\\image\\","");
	   }

	    public static String download(String urlString, String filename,String savePath,String realpath) throws Exception {
	        // 构造URL
      URL url = new URL(urlString);
	       // 打开连接
	      URLConnection con = url.openConnection();
		       //设置请求超时为5s
	      con.setConnectTimeout(5*1000);
	     // 输入流
	       InputStream is = con.getInputStream();

		      // 1K的数据缓冲
	       byte[] bs = new byte[1024];
		       // 读取到的数据长度
		      int len;
	       // 输出的文件流
		      File sf=new File(realpath+savePath);
		       if(!sf.exists()){
			           sf.mkdirs();
			       }
		      OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
		        // 开始读取
	        while ((len = is.read(bs)) != -1) {
		          os.write(bs, 0, len);
	        }
		       // 完毕，关闭所有链接
		       os.close();
	        is.close();
        logger.info("调试信息");
       return savePath+"/"+filename;
  }

}