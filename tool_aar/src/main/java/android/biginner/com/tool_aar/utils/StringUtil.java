package android.biginner.com.tool_aar.utils;

import android.content.Context;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

/**
 * 字符串相关工具类
 */
public class StringUtil {
    private StringUtil() {
    }

    /**
     * 判断----是否为 null
     *
     * @param str
     * @return
     */
    public static boolean isNull(CharSequence str) {
        return str == null;
    }

    /**
     * 判断----是否为 null或 ""
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 判断----是否为 null 或 "" 或 全部为空格
     *
     * @param str
     * @return
     */
    public static boolean isSpace(String str) {
        return isEmpty(str) || isEmpty(str.trim());
    }

    /**
     * 判断----不为空
     * @param sequence
     * @return
     */
    public static boolean isNoNull(CharSequence sequence) {
        return sequence != null;
    }
    /**
     * 判断----不为空
     * @param str
     * @return
     */
    public static boolean isNoEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * 获取字符串资源
     * @param context
     * @param rawID
     * @return
     */
    public static String getString(Context context, @StringRes int rawID) {
        String string = context.getResources().getString(rawID);
        context = null;
        return string;
    }

    /**
     * 判断----两个字符串是否相同
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) {
            return true;
        } else if (isNoNull(a) && isNoNull(b)
                && a instanceof String && b instanceof String) {
            return a.equals(b);
        } else {
            //还存在其他情况,不作处理了
            return false;
        }
    }

    /**
     * 补位
     *
     * @param i       要补位的数字
     * @param digits  数字位数
     * @return
     *
     * fillGap(1,3)
     * 将数字 1 补位成 三位数字形式
     *  "001"
     */
    public static String fillGap(int i,int digits){
        return String.format("%0"+digits+"d",i);
    }

}
