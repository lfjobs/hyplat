package hy.ea.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CheckChinese {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        //输入文件地址
        System.out.print("输入文件地址：");
        String filePath = scanner.nextLine();
        StringBuffer stringBuffer = checkFileByLines(filePath);
        logger.info("调试信息");
        write(filePath, stringBuffer.toString());
    }

    public static StringBuffer checkFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            // logger.info("调试信息");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            StringBuffer stringBuffer = new StringBuffer();
            while ((tempString = reader.readLine()) != null) {
                //把这一行中的中文字符替换成英文字符
                for (int i = 0; i < tempString.length(); i++) {
                    //把这个字符串中的中文括号换成英文括号
                    if (tempString.charAt(i) == '（')
                        stringBuffer.append('(');
                    else if (tempString.charAt(i) == '）')
                        stringBuffer.append(')');
                    else if (tempString.charAt(i) == '，')
                        stringBuffer.append(',');
                    else if (tempString.charAt(i) == '＠')
                        stringBuffer.append('@');
                    else if (tempString.charAt(i) == '；')
                        stringBuffer.append(';');
                    else
                        stringBuffer.append(tempString.charAt(i));
                }
                stringBuffer.append("\n");
                line++;
            }
            reader.close();
            return stringBuffer;
        } catch (IOException e) {
            logger.error("操作异常", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    public static String checkByLines(String content){
        String sReturn = content;
        try {
            sReturn = sReturn.toUpperCase();
            sReturn = sReturn.replace('，', ',');
            sReturn = sReturn.replace('。', '.');
            sReturn = sReturn.replace('；', ';');
            sReturn = sReturn.replace('！', '!');
            sReturn = sReturn.replace('？', '?');
            sReturn = sReturn.replace('：', ':');
            sReturn = sReturn.replace('"', '＂');
            sReturn = sReturn.replace('“', '＂');
            sReturn = sReturn.replace('”', '＂');
            sReturn = sReturn.replace('）', ')');
            sReturn = sReturn.replace('（', '(');
            sReturn = sReturn.replace('】', ']');
            sReturn = sReturn.replace('【', '[');
            sReturn = sReturn.replace('》', '>');
            sReturn = sReturn.replace('《', '<');
// sReturn = sReturn.replace('-', ' ');
// sReturn = sReturn.replace('_', ' ');
            sReturn = sReturn.replace('0', '0');
            sReturn = sReturn.replace('1', '1');
            sReturn = sReturn.replace('2', '2');
            sReturn = sReturn.replace('3', '3');
            sReturn = sReturn.replace('4', '4');
            sReturn = sReturn.replace('5', '5');
            sReturn = sReturn.replace('6', '6');
            sReturn = sReturn.replace('7', '7');
            sReturn = sReturn.replace('8', '8');
            sReturn = sReturn.replace('9', '9');
            sReturn = sReturn.replace('A', 'A');
            sReturn = sReturn.replace('B', 'B');
            sReturn = sReturn.replace('C', 'C');
            sReturn = sReturn.replace('D', 'D');
            sReturn = sReturn.replace('E', 'E');
            sReturn = sReturn.replace('F', 'F');
            sReturn = sReturn.replace('G', 'G');
            sReturn = sReturn.replace('H', 'H');
            sReturn = sReturn.replace('I', 'I');
            sReturn = sReturn.replace('J', 'J');
            sReturn = sReturn.replace('K', 'K');
            sReturn = sReturn.replace('L', 'L');
            sReturn = sReturn.replace('M', 'M');
            sReturn = sReturn.replace('N', 'N');
            sReturn = sReturn.replace('O', 'O');
            sReturn = sReturn.replace('P', 'P');
            sReturn = sReturn.replace('Q', 'Q');
            sReturn = sReturn.replace('R', 'R');
            sReturn = sReturn.replace('S', 'S');
            sReturn = sReturn.replace('T', 'T');
            sReturn = sReturn.replace('U', 'U');
            sReturn = sReturn.replace('V', 'V');
            sReturn = sReturn.replace('W', 'W');
            sReturn = sReturn.replace('X', 'X');
            sReturn = sReturn.replace('Y', 'Y');
            sReturn = sReturn.replace('Z', 'Z');
            sReturn = strReplace(sReturn, "‘", "'");
        } catch (Exception ex) {
            logger.error("操作异常", e);
        }
        return sReturn;
    }

    public static synchronized String strReplace(String sAll, String sOld,String sNew) {
        int iT = 0;
        String sF = null;
        String sH = null;
        /* 如果新串中包括旧串,不让替多只让替少 */
        if (sNew.indexOf(sOld) != -1) {
            return sAll;
        }
        if ((sAll == null) || (sOld == null) || (sNew == null)) {
            return sAll;
        }
        iT = sAll.indexOf(sOld);
        while (iT != -1) {
            sF = sAll.substring(0, iT);
            sH = sAll.substring(iT + sOld.length());
            sAll = sF + sNew + sH;
            iT = sAll.indexOf(sOld);
        }
        return sAll;
    }

    static public void write(String filename, String string) {
        try {
            File file = new File(filename);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            FileWriter fileWritter = new FileWriter(file);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(string);
            bufferWritter.close();

            logger.info("调试信息");

        } catch (IOException e) {
            logger.error("操作异常", e);
        }
    }
}