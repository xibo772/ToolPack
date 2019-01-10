package android.biginner.com.tool_jar;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by buchou on 2017/10/19.
 */

public class ArithUtils {
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    //这个类不能实例化
    private ArithUtils() {
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的转换。
     *
     * @param str 需要转换的字符串
     * @return 两个参数的和
     */
    public static double parseDouble(String str) {
        BigDecimal b = null;
        if (str != null && !str.isEmpty()) {
            b = new BigDecimal(str);
        } else {
            b = new BigDecimal("0");
        }
        return b.doubleValue();
    }

    /**
     * double 类型转换为String 类型
     *
     * @param v
     * @return
     */
    public static String parseString(double v) {
        double round = round(v, 10);
        return Double.toString(round);
    }

    /**
     * String 类型数字 转换成 int类型
     *
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        int result = 0;
        try {
            Pattern compile = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+$");
            if (str != null && !str.isEmpty()) {
                Matcher matcher = compile.matcher(str);
                if (matcher.matches()) {
                    if (str.indexOf(".") > 0) {
                        String[] split = str.split("\\.");
                        String s = split[0];
                        result = Integer.parseInt(s);
                    } else {
                        result = Integer.parseInt(str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * String 类型数字 转换成 long类型
     *
     * @param str
     * @return
     */
    public static long parseLong(String str) {
        long result = 0;
        try {
            Pattern compile = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+$");
            if (str != null && !str.isEmpty()) {
                Matcher matcher = compile.matcher(str);
                if (matcher.matches()) {
                    if (str.indexOf(".") > 0) {
                        String[] split = str.split("\\.");
                        String s = split[0];
                        result = Long.parseLong(s);
                    } else {
                        result = Long.parseLong(str);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 格式化 double 类型, 使其整数部分每三位分割 并 保留两位小数
     *
     * @param v
     * @param format ",###.00"
     * @return
     */
    public static String formatDouble(double v, String format) {
        if (format == null || format.isEmpty()) {
            format = ",###.00";
        }
        return new DecimalFormat(format).format(v);
    }

    /**
     * 格式化 int 类型 ,
     *
     * @param index
     * @param format
     * @return
     */
    public static String formatInt(int index, String format) {
        if (format == null || format.isEmpty()) {
            format = ",###";
        }
        return new DecimalFormat(format).format(index);
    }

    /**
     * 格式化 long 类型
     *
     * @param index
     * @param format
     * @return
     */
    public static String formatLong(int index, String format) {
        if (format == null || format.isEmpty()) {
            format = ",###";
        }
        return new DecimalFormat(format).format(index);
    }


}
