package hy.ea.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
public class FileUtils {
    public static final String FORMAT_FILE_NAME = "%d.mp3";

    /**
     *
     * @description: 判断文件夹是否存在，不存在则创建
     * @author: Jeff
     * @date: 2019年12月7日
     * @param dir
     */
    public static void isDirPathExist(File dir) {
        // 判断文件夹是否存在
        if (dir.isDirectory()) {
            logger.info("调试信息");
        } else {
            logger.info("调试信息");
            dir.mkdir();
            logger.info("调试信息");
        }
    }

    /**
     *
     * @description: 从服务器获得输入流InputStream,并写到本地磁盘
     * @author: Jeff
     * @date: 2019年12月7日
     */
    public static void saveImageToDisk(InputStream inputStream, String diskPath) {
        String fileName = String.format(FORMAT_FILE_NAME, new Date().getTime());
        logger.info("调试信息");
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        try {
            File dirFile = new File(diskPath);
            // 判断文件夹是否存在，不存在则创建
            isDirPathExist(dirFile);
            fileOutputStream = new FileOutputStream(new File(dirFile, fileName));
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            logger.error("操作异常", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("操作异常", e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.error("操作异常", e);
                }
            }

        }
        logger.info("调试信息");
    }
}
