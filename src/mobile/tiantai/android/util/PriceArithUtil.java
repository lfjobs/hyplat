package mobile.tiantai.android.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * 金额计算工具类
 * Created by Administrator on 2019-10-18.
 */
public class PriceArithUtil {
	private static final Logger logger = LoggerFactory.getLogger(PriceArithUtil.class);
    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static double add(double value1, double value2) {
        DecimalFormat df1 = new DecimalFormat("##,##0.00");
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        String dlStr = df1.format(b1.add(b2));
        double d1=0.0;
        try {
            d1 = df1.parse(dlStr).doubleValue();
        } catch (ParseException e) {
            logger.error("操作异常", e);
        }
        return Double.valueOf(d1).doubleValue();
       // return Double.valueOf(df1.format(b1.add(b2).doubleValue())).doubleValue();
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static double sub(double value1, double value2) {
        DecimalFormat df1 = new DecimalFormat("##,##0.00");
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        String dlStr = df1.format(b1.subtract(b2));
        double d1=0.0;
        try {
            d1 = df1.parse(dlStr).doubleValue();
        } catch (ParseException e) {
            logger.error("操作异常", e);
        }
        return Double.valueOf(d1).doubleValue();
       // return Double.valueOf(df1.format(b1.subtract(b2).doubleValue())).doubleValue();
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(double value1, double value2) {
        DecimalFormat df1 = new DecimalFormat("##,##0.00");
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        String dlStr = df1.format(b1.multiply(b2));
        double d1=0.0;
        try {
            d1 = df1.parse(dlStr).doubleValue();
        } catch (ParseException e) {
            logger.error("操作异常", e);
        }
        return Double.valueOf(d1).doubleValue();

    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @param scale  精确范围
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        DecimalFormat df1 = new DecimalFormat("##,##0.00");
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        String dlStr = df1.format(b1.divide(b2));
        double d1=0.0;
        try {
            d1 = df1.parse(dlStr).doubleValue();
        } catch (ParseException e) {
            logger.error("操作异常", e);
        }
        return Double.valueOf(d1).doubleValue();
        //return Double.valueOf(df1.format(b1.divide(b2, scale).doubleValue())).doubleValue();
    }

}
