package android.biginner.com.tool_jar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间装换类
 */
public class DateUtil {

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;
    private static String[] WEEK = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    /**
     * String 转换 Date
     *
     * @param str    str
     * @param format format
     * @return Date
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format, Locale.CHINA).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * String 转 String
     *
     * @param str          需要转换的数据
     * @param format       format 转换的日期格式
     * @param secondFormat secondFormat 时间格式
     * @return String
     */
    public static String string2String(String str, String format, String secondFormat) {
        try {
            return date2String(new SimpleDateFormat(format, Locale.CHINA).parse(str).getTime(), secondFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date().toString();
    }

    /**
     * Date（long） 转换 String
     *
     * @param time   time
     * @param format format
     * @return String
     */
    public static String date2String(long time, String format) {
        ;
        return new SimpleDateFormat(format, Locale.CHINA).format(time);
    }

    /**
     * long 去除 时分秒
     * 时分秒全部为0
     *
     * @param date date
     * @return long
     */
    public static long getYearMonthDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取目标时间和当前时间之间的差距
     *
     * @param date date
     * @return String
     */
    public static String getTimestampString(Date date) {
        Date curDate = new Date();
        long splitTime = curDate.getTime() - date.getTime();
        if (splitTime < (30 * ONE_DAY)) {
            if (splitTime < ONE_MINUTE) {
                return "刚刚";
            }
            if (splitTime < ONE_HOUR) {
                return String.format(Locale.CHINA, "%d分钟前", splitTime / ONE_MINUTE);
            }

            if (splitTime < ONE_DAY) {
                return String.format(Locale.CHINA, "%d小时前", splitTime / ONE_HOUR);
            }

            return String.format(Locale.CHINA, "%d天前", splitTime / ONE_DAY);
        }
        String result;
        result = "M月d日";
        return (new SimpleDateFormat(result, Locale.CHINA)).format(date);
    }


    /**
     * Date 转换 HH
     *
     * @param date date
     * @return String
     */
    public static String date2HH(Date date) {
        return new SimpleDateFormat("HH", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date date
     * @return String
     */
    public static String date2HHmm(Date date) {
        return new SimpleDateFormat("HH:mm", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date date
     * @return String
     */
    public static String date2HHmmss(Date date) {
        return new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 MM.dd
     *
     * @param date date
     * @return String
     */
    public static String date2MMdd(Date date) {
        return new SimpleDateFormat("MM.dd", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 yyyy.MM.dd
     *
     * @param date date
     * @return String
     */
    public static String date2yyyyMMdd(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 yyyy-MM
     */
    public static String date2yyyyMM(Date date) {
        String t = new SimpleDateFormat("yyyy-MM", Locale.CHINA).format(date);
        String[] split = t.split("-");
        if (split.length == 2) {
            int tp = Integer.parseInt(split[1]);
            ++tp;
            split[1] = String.valueOf(tp);
            if (split[1].length() == 1) {
                split[1] = "0" + split[1];
            }
            if (tp == 13) {
                return (Integer.parseInt(split[0]) + 1) + "-01";
            }
            return split[0] + "-" + split[1];
        }
        return t;
    }

    /**
     * Date 转换 yyyy-MM
     * 获取当前年月
     *
     * @param date
     * @return
     */
    public static String date3yyyyMM(Date date) {
        return new SimpleDateFormat("yyyy-MM", Locale.CHINA).format(date);
    }

    /**
     * Date 转换 MM月dd日 星期
     *
     * @param date date
     * @return String
     */
    public static String date2MMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("MM月dd日 星期", Locale.CHINA).format(date) + WEEK[dayOfWeek - 1];
    }

    /**
     * Date 转换 yyyy年MM月dd日 星期
     *
     * @param date date
     * @return String
     */
    public static String date2yyyyMMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("yyyy年MM月dd日 星期", Locale.CHINA).format(date) + WEEK[dayOfWeek - 1];
    }
}
