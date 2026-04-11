package com.stamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Html2PdfUtil {
	private static final Logger logger = LoggerFactory.getLogger(Html2PdfUtil.class);

    public static void  html2Pdf(String htmlUrl,String outpdf,String realPath){

        Document document = new Document(PageSize.A4);
        try{
           logger.info("转换开始");
            PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream(realPath+outpdf));
            document.open();
            URL url = new URL(htmlUrl);
            URLConnection conn = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));


            String line;
            StringBuilder htmlContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                htmlContent.append(line);
            }
            reader.close();
            logger.info("调试信息");
            ElementList list = new MyXMLWorkerHelper().parseToElementList(htmlContent.toString(),"");

            for(Element element:list){
                document.add(element);

            }

            document.close();
            logger.info("转换结束");
        }catch (Exception e){
            logger.error("操作异常", e);
        }

    }
}
