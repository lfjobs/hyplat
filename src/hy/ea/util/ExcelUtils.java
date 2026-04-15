package hy.ea.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import hy.ea.bo.ExcelBean;
import hy.plat.bo.BaseBean;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.struts2.ServletActionContext;


/**
 * 批量导出excel生成zip 
 * @author hongwang.zhang 16-01-29 
 *
 */
public class ExcelUtils {

    static HttpServletRequest request=ServletActionContext.getRequest();


    static int recordNum = 2;



    public static void main(String[] args) {
        int s=getSheetCount(52300);
        System.out.println(1);
    }

    /**
     * 确定分页的个数 
     *
     * @param rCount 总得记录条数 
     * @return
     */
    private static int getSheetCount(int rCount) {
        if (recordNum <= 0)
            return 1;
        if (rCount <= 0)
            return 1;
        int n = rCount % (recordNum); // 余数
        if (n == 0) {
            return rCount / recordNum;
        } else {
            return (int) (rCount / recordNum) + 1;
        }
    }

    //忽略警告  
    @SuppressWarnings("unchecked")
    public static String  batchExport(String[] columnHeadings,List list,int pageNum) throws IOException,
            WriteException{
        recordNum=pageNum;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHSS");
        String path = sdf.format(new Date());
        //获取服务器web路径
        String serverPath=path;
        System.out.println(serverPath);
        //在服务器端创建文件夹
        File file = new File(serverPath);
        if(!file.exists()){
            file.mkdir(); //创建文件夹
        }
        /*if(CollectionUtils.isEmpty(list)){
            return null;
        }*/
        //Class clazz = list.get(0).getClass();
        // 文件流
        ByteArrayOutputStream ostream = new ByteArrayOutputStream();
        WritableWorkbook book = null;
        //获取导出多少个excel
        int excelCount=getSheetCount(list.size());
        //导出每个excel
        int fromIndex=0;
        int toIndex=list.size()<pageNum?list.size():pageNum;
        for(int excel=0;excel<excelCount;excel++){
            List listExcel=null;
            if(toIndex>list.size()){
                listExcel=list.subList(fromIndex, list.size());
            }else{
                listExcel=list.subList(fromIndex,toIndex);
            }
            try {
                book = Workbook.createWorkbook(new File(serverPath+"/" + new SimpleDateFormat("yyyyMMddHHmmsss").format(new Date() )+"_"+(excel+1)+".xls"));
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            WritableSheet ws = null;

            Label lable = null;
            int rowIndex = 0;

            int rCount = listExcel.size() > 0 ? listExcel.size() : 0; // 行数
            int sheetCount = getSheetCount(rCount);// 获取分页工作表的个数

            for (int i = 0; i < sheetCount; i++) {// 初始化工作表的个数
                book.createSheet("Sheet-" + i, i); // 添加一个工作表
            }

            for (int index = 0; index < sheetCount; index++) {

                ws = book.getSheet(index);// 获取工作簿的第一个工作表
                // 列标
                WritableFont wfColumn = new WritableFont(WritableFont.TIMES, 10,
                        WritableFont.BOLD, false);
                WritableCellFormat wcfColumn = new WritableCellFormat(wfColumn);
                wcfColumn.setAlignment(Alignment.CENTRE);
                wcfColumn.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                int j = 0;
                // 标题
                /*Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    if (!fields[i].getName().equalsIgnoreCase("serialVersionUID") && !fields[i].getName().equalsIgnoreCase("$jacocoData")) {
                        lable = new Label(j++, 0, fields[i].getName(), wcfColumn);
                        ws.addCell(lable);
                    }
                }*/

                int h = 0;
                for (String ch : columnHeadings) {
                    ws.addCell(new Label(h, 0, ch, wcfColumn));
                    h++;
                }

                /*Method[] methods = clazz.getMethods();
                List<Method> getMethods = new ArrayList<Method>();
                for (j = 0; j < fields.length; j++) {
                    for (int i = 0; i < methods.length; i++) {
                        if (methods[i].getName().equalsIgnoreCase(
                                "get" + fields[j].getName())) {
                            getMethods.add(methods[i]);
                        }
                    }
                }*/

                // 内容
                WritableCellFormat wcfCell = new WritableCellFormat();
                wcfCell.setAlignment(Alignment.CENTRE);
                wcfCell.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

                int i = 0;
                for (int k = rowIndex; k < listExcel.size(); k++) {
                    /*Object obj = null;
                    obj = list.get(k);*/
                    BaseBean bean= (BaseBean) list.get(k);
                    int r = 0;
                    i++;
                    if (recordNum == i) {
                        rowIndex += recordNum;
                    }
                    int n = 0;
                    for (String pr : ((ExcelBean)bean).properties()) {
                        if(pr==null||pr==""||pr.equals("null"))
                            pr="";
                        ws.addCell(new Label(n, i, pr));
                        n++;
                    }
                    /*for (Method method : getMethods) {
                        try {
                            Object result = method.invoke(obj);
                            if (result == null) {
                                result = "";
                            }
                            if (result instanceof Date) {
                                result = Utilities.getDateString((Date)result,DateUtil.C_TIME_PATTON_DEFAULT);
                            }
                            lable = new Label(r++, i, result == null ? ""
                                    : result.toString(), wcfCell);
                            ws.addCell(lable);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }*/
                }
            }
            book.write();
            book.close();
            fromIndex+=pageNum;
            toIndex+=pageNum;
        }
        return serverPath;


    }

    /**
     * 生成.zip文件; 
     * @param path
     * @throws IOException
     */
    public static InputStream craeteZipPath(String path) throws IOException{
        ZipOutputStream zipOutputStream = null;
        String lj=path+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".zip";
        File file = new File(lj);
        zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        File[] files = new File(path).listFiles();
        FileInputStream fileInputStream = null;
        byte[] buf = new byte[1024];
        int len = 0;
        if(files!=null && files.length > 0){
            for(File excelFile:files){
                String fileName = excelFile.getName();
                fileInputStream = new FileInputStream(excelFile);
                //放入压缩zip包中;  
                zipOutputStream.putNextEntry(new ZipEntry(path + "/"+fileName));
                //读取文件;  
                while((len=fileInputStream.read(buf)) >0){
                    zipOutputStream.write(buf, 0, len);
                }
                //关闭;  
                zipOutputStream.closeEntry();
                if(fileInputStream != null){
                    fileInputStream.close();
                }
            }
        }

        if(zipOutputStream !=null){
            zipOutputStream.close();
        }
        File f = new File(lj);
        InputStream istream = new FileInputStream(f);
        return istream;
    }



}