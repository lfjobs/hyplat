package com.stamp;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.File;


public class Office2PdfUtil {

    private static Office2PdfUtil office2PdfUtil = new Office2PdfUtil();
    private static  OfficeManager officeManager;
    //openOffice安装路径
    private static String OPEN_OFFICE_HOME = "C:\\Program Files (x86)\\OpenOffice 4\\";
    //服务端口
    private static int OPEN_OFFICE_PORT[] = {8100};

    public static Office2PdfUtil getOffice2PdfUtil() {


        return office2PdfUtil;
    }

    /**
     *
     * office2Pdf 方法
     * @descript：TODO
     * @param inputFile 文件全路径
     * @param pdfFilePath pdf文件全路径
     * @return void
     * @author lxz
     * @return
     */
    public static void office2Pdf(String inputFile,String pdfFilePath) {

        File pdfFile = new File(pdfFilePath);
        if (pdfFile.exists()) {
            pdfFile.delete();
        }
        try{
            long startTime = System.currentTimeMillis();
            //打开服务
            startService();
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            //开始转换
            converter.convert(new File(inputFile),new File(pdfFilePath));
            //关闭
            stopService();
            System.out.println("运行结束");
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void stopService(){
        if (officeManager != null) {
            officeManager.stop();
        }
    }

    public static void startService(){
        DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
        try {
            configuration.setOfficeHome(OPEN_OFFICE_HOME);//设置安装目录
            configuration.setPortNumbers(OPEN_OFFICE_PORT); //设置端口
            configuration.setTaskExecutionTimeout(1000 * 60 * 25L);
            configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
            officeManager = configuration.buildOfficeManager();
            officeManager.start();    //启动服务
        } catch (Exception ce) {
            System.out.println("office转换服务启动失败!详细信息:" + ce);
        }
    }


    public static void main(String[] args)
    {

        Office2PdfUtil.office2Pdf("D:\\temp\\123.jpg","D:\\temp\\mz3.pdf");

    }

}
