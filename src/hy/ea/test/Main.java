package hy.ea.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.commons.lang3.StringUtils;

public class Main {

    public static void main(String[] args) {
        String str = "半角符号示例:,!~    全角符号示例：，！~";
        logger.info("调试信息");
        String sbcResult = toSbc(str);
        logger.info("调试信息");
        logger.info("调试信息");
        String dbcResult = toDbc(str);
        logger.info("调试信息");//打印出java所有字符
//    printAllCharacter();
    }

    /*** 打印出java中所有Unicode编码的字符*/
    private static void printAllCharacter() {
        for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; ++i) {
            logger.info("调试信息");
        }
    }


    /**
     * * 全角转半角的函数(DBC case)
     * * 全角空格为12288，半角空格为32 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * ** @param input 任意字符串* @return 半角字符串
     */
    public static String toDbc(String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    /**
     * * 半角转全角的函数(SBC case)
     * * 全角空格为12288,半角空格为32，其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * ** @param input 任意字符串* @return 全角字符串
     */
    public static String toSbc(String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127) {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }


}
