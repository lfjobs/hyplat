package hy.ea.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

public class HttpUtils {
    /**
     *
     * @description: 从服务器获得一个输入流
     * @author: Jeff
     * @date: 2019年12月7日
     * @return
     */
    public static InputStream getInputStream(String urlPath) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(urlPath);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置网络连接超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置应用程序要从网络连接读取数据
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            logger.info("调试信息");
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 从服务器返回一个输入流
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }
        } catch (MalformedURLException e) {
            logger.error("操作异常", e);
        } catch (IOException e) {
            logger.error("操作异常", e);
        }
        return inputStream;
    }

    /**
     *
     * @description: 将输入流输出到页面
     * @author: Jeff
     * @date: 2019年12月7日
     * @param resp
     * @param inputStream
     */
    public static void writeFile(HttpServletResponse resp, InputStream inputStream) {
        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = inputStream.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
        } catch (IOException e) {
            logger.error("操作异常", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                logger.error("操作异常", e);
            }
        }
    }

    public static void main(String args[]) {
        // 服务器图片url
        String urlPath = "http://tts.baidu.com/text2audio?per=1&lan=zh&ie=UTF-8&spd=3&text=TEXT";
        // 本地文件夹路径
        String diskPath = "g:\\Jeff\\audios\\";
        // 需要转换为语音的文字
        String text = "100";
        // 从服务器端获得图片，并保存到本地
        FileUtils.saveImageToDisk(getInputStream(urlPath.replace("TEXT", text)), diskPath);
    }
}
