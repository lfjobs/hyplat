package hy.ea.test;


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
            System.out.println(dir + "文件夹已存在");
        } else {
            System.out.println(dir + "文件夹不存在");
            dir.mkdir();
            System.out.println("创建文件夹" + dir);
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
        System.out.println("文件名字为：" + fileName);
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
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println(fileName + "写到本地磁盘完成");
    }
}
