package android.biginner.com.tool_jar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时钟工具类
 * 时间的获取，比较
 */
public class ClockUtil {

    /**
     * 获取年份
     *
     * @return
     */
    //现在时间的年份
    public static int GetYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    //指定时间的年份
    public static int GetYear(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.YEAR);
    }

    //指定时间的年份
    public static int GetYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份
     * <p>
     * 月份[0,11]
     *
     * @return
     */
    //现在时间的月份
    public static int GetMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    //指定时间的月份
    public static int GetMonth(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.MONTH) + 1;
    }

    //指定时间的月份
    public static int GetMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static int GetDay() {
        //两种方式获取相同
//        return Calendar.getInstance().get(Calendar.DATE);
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    //指定时间的日期
    public static int GetDay(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    //指定时间的日期
    public static int GetDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取 是当年的第几天
     *
     * @return
     */
    //今天是今年的第几天
    public static int GetDayInYear() {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }

    //指定时间是当年的第几天
    public static int GetDayInYear(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    //指定时间是当年的第几天
    public static int GetDayInYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    //指定时间是当年的第几天
    public static int GetDayInYear(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取周几
     * [1,7] = {周天,周一.......周六}
     *
     * @return
     */
    //今天是星期几
    public static int GetDayInWeek() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    //指定时间是周几
    public static int GetDayInWeek(long lTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lTime);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //指定时间是周几
    public static int GetDayInWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //指定时间是周几
    public static int GetDayInWeek(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取周几
     *
     * @return
     */
    //今天是周几
    public static String GetWeek() {
        return week(GetDayInWeek());
    }

    //指定时间是周几
    public static String GetWeek(long lTime) {
        return week(GetDayInWeek(lTime));
    }

    //指定时间是周几
    public static String GetWeek(Date date) {
        return week(GetDayInWeek(date));
    }

    //指定时间是周几
    public static String GetWeek(int year, int month, int day) {
        return week(GetDayInWeek(year, month, day));
    }


    /**
     * 获取几点(12时制)
     *
     * @return
     */
    public static int GetHour12() {
        return Calendar.getInstance().get(Calendar.HOUR);
    }

    //指定时间是几点
    public static int GetHour12(long lTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lTime);
        return calendar.get(Calendar.HOUR);
    }

    /**
     * 获取几点(24时制)
     *
     * @return
     */
    public static int GetHour24() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    //指定时间是几点
    public static int GetHour24(long lTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lTime);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取 上午AM/下午PM
     * {0,1} = {上午,下午}
     *
     * @return
     */
    public static int GetAM_PM() {
        return Calendar.getInstance().get(Calendar.AM_PM);
    }

    //获取指定时间是上午下午
    public static int GetAM_PM(long lTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lTime);
        return calendar.get(Calendar.AM_PM);
    }

    /**
     * 获取 是上午AM/下午PM
     * {0,1} = {上午,下午}
     *
     * @return
     */
    public static String GetAmPm() {
//        return getAM_PM()==0?"AM":"PM";
        return ampm(GetAM_PM());
    }

    //获取指定时间是上午下午
    public static String GetAmPm(long lTime) {
        return ampm(GetAM_PM(lTime));
    }

    /**
     * 获取分钟数
     *
     * @return
     */
    public static int GetMinute() {
        return Calendar.getInstance().get(Calendar.MINUTE);
    }

    //指定时间的分钟数
    public static int GetMinute(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 获取 秒数
     *
     * @return
     */
    public static int GetSecond() {
        return Calendar.getInstance().get(Calendar.SECOND);
    }

    //指定时间的秒数
    public static int GetSecond(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 获取 毫秒值
     *
     * @return
     */
    public static int GetMillsecond() {
        return Calendar.getInstance().get(Calendar.MILLISECOND);
    }

    //指定时间的毫秒值
    public static int GetMillsecond(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.MILLISECOND);
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    //现在时间的时间戳
    public static long GetTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    //指定时间的时间戳
    public static long GetTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    //指定时间的时间戳
    //年 月 日 时(24时制) 分 秒 毫秒
    public static long GetTime(int... index) {
        return setTime(index);
    }


    /**
     * 获取 时间
     *
     * @param dateFormat
     * @return
     */
    public static String GetDate(String dateFormat) {
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static String GetDate(String dateFormat, long lTime) {
        return new SimpleDateFormat(dateFormat).format(new Date(lTime));
    }

    /**
     * 获取 指定时间是当年的第几周
     *
     * @return
     */
    //这个周是今年的第几周
    public static int GetWeekOfYear() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
    }

    //指定时间是当年的第几周
    public static int GetWeekOfYear(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    //指定时间是当年的第几周
    public static int GetWeekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    //指定时间是当年的第几周
    public static int GetWeekOfYear(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取这周是这个月的第几周(自然周)
     * <p>
     * 以日历表中显示
     * 这个月的第一行是第一周,第二行为第二周
     *
     * @return
     */
    public static int GetWeekOfMonth() {
        return Calendar.getInstance().get(Calendar.WEEK_OF_MONTH);
    }

    //指定时间是当月的第几周
    public static int GetWeekOfMonth(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    //指定时间是当月的第几周
    public static int GetWeekOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    //指定时间是当月的第几周
    public static int GetWeekOfMonth(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取 某年某月某日在当月是第几周(计算周)
     * 不以日历显示,而是以 7 天为一周计算
     * 1-7 , 8-14 , 15-21 , 22-28
     *
     * @return
     */
    public static int GetWeekInMonth() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    //指定时间在当月的第几周
    public static int GetWeekInMonth(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    //指定时间在当月的第几周
    public static int GetWeekInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    //指定时间在当月的第几周
    public static int GetWeekInMonth(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    /**
     * 获取 某年总共多少天
     *
     * @return
     */
    //几年多少天
    public static int GetYearDays() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    //指定时间 的某年多少天
    public static int GetYearDays(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    //指定时间 的某年多少天
    public static int GetYearDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    //指定时间 的某年多少天
    public static int GetYearDays(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取 某年某月总共多少天
     *
     * @return
     */
    public static int GetMonthDays() {
        return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间 的某月多少天
    public static int GetMonthDays(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间 的某月多少天
    public static int GetMonthDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间 的某月多少天
    public static int GetMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取 某年某月的第一天是周几
     *
     * @return
     */
    //这个月的第一天是周几
    public static int GetWeekMonthOne() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //指定时间的当月的第一天是周几
    public static int GetWeekMonthOne(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //指定时间的当月的第一天是周几
    public static int GetWeekMonthOne(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //指定时间的当月的第一天是周几
    public static int GetWeekMonthOne(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取下个月的总天数
     */
    //这个月的下个月总天数
    public static int GetNextMonthDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetNextMonthDays(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetNextMonthDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetNextMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1 + 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取 下下个月的总天数
     */
    //这个月的下个月总天数
    public static int GetNext2MonthDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetNext2MonthDays(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetNext2MonthDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetNext2MonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1 + 2);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取上个月的总天数
     */
    public static int GetLastMonthDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetLastMonthDays(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetLastMonthDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetLastMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1 - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取上上个月的总天数
     */
    public static int GetLast2MonthDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 2);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetLast2MonthDays(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 2);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetLast2MonthDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 2);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public static int GetLast2MonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1 - 2);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 与某个月间隔 几个月(gapIndex) 的月份的总天数
     * gapIndex
     * + 往后
     * - 往前
     *
     * @param gapIndex
     * @return
     */
    public static int GetMuchMonthDays(int gapIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + gapIndex);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int GetMuchMonthDays(long ltime, int gapIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + gapIndex);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int GetMuchMonthDays(Date date, int gapIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + gapIndex);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int GetMuchMonthDays(int year, int month, int gapIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1 + gapIndex);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 某年的明年 的总天数
     */
    public static int GetNextYearDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int GetNextYearDays(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int GetNextYearDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 某年的去年 的总天数
     */
    public static int GetLastYearDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int GetLastYearDays(long ltime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int GetLastYearDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 与 某年间隔  几年(gapIndex)   的总天数
     * gapIndex
     * + 往后
     * - 往前
     *
     * @param gapIndex
     * @return
     */
    public static int GetMuchYearDays(int gapIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + gapIndex);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int GetMuchYearDays(long ltime, int gapIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ltime);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + gapIndex);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static int GetMuchYearDays(Date date, int gapIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + gapIndex);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    /**
     * 两个时间是否 同年
     *
     * @param ltime
     * @param ltime1
     * @return
     */
    public static boolean IsEqualsYear(long ltime, long ltime1) {
        return GetYear(ltime) == GetYear(ltime1);
    }

    public static boolean IsEqualsYear(Date date, long ltime) {
        return GetYear(ltime) == GetYear(date);
    }

    public static boolean IsEqualsYear(Date date, Date date1) {
        return GetYear(date1) == GetYear(date);
    }

    /**
     * 两个时间是否 同月
     *
     * @param ltime
     * @param ltime1
     * @return
     */
    public static boolean IsEqualsMonth(long ltime, long ltime1) {
        return GetMonth(ltime) == GetMonth(ltime1)
                && IsEqualsYear(ltime, ltime1);
    }

    public static boolean IsEqualsMonth(Date date, long ltime) {
        return GetMonth(ltime) == GetMonth(date)
                && IsEqualsYear(date, ltime);
    }

    public static boolean IsEqualsMonth(Date date, Date date1) {
        return GetMonth(date1) == GetMonth(date)
                && IsEqualsYear(date, date1);
    }

    /**
     * 两个时间是否 同周
     *
     * @param ltime
     * @param ltime1
     * @return
     */
    public static boolean IsEqualsWeek(long ltime, long ltime1) {
        return GetWeekOfMonth(ltime) == GetWeekOfMonth(ltime1)
                && IsEqualsMonth(ltime, ltime1);
    }

    public static boolean IsEqualsWeek(Date date, long ltime) {
        return GetWeekOfMonth(ltime) == GetWeekOfMonth(date)
                && IsEqualsMonth(date, ltime);
    }

    public static boolean IsEqualsWeek(Date date, Date date1) {
        return GetWeekOfMonth(date1) == GetWeekOfMonth(date)
                && IsEqualsMonth(date, date1);
    }

    public static boolean IsEqualsWeek(int year, int month, int day, int year1, int month1, int day1) {
        return GetWeekOfMonth(year, month, day) == GetWeekOfMonth(year1, month1, day1)
                && year == year1
                && month == month1;
    }

    /**
     * 两个时间是否 同天
     *
     * @param ltime
     * @param ltime1
     * @return
     */
    public static boolean IsEqualsDay(long ltime, long ltime1) {
        return GetYear(ltime) == GetYear(ltime1)
                && IsEqualsMonth(ltime, ltime1);
    }

    public static boolean IsEqualsDay(Date date, long ltime) {
        return GetYear(ltime) == GetYear(date)
                && IsEqualsMonth(date, ltime);
    }

    public static boolean IsEqualsDay(Date date, Date date1) {
        return GetYear(date1) == GetYear(date)
                && IsEqualsMonth(date, date1);
    }

    /**
     * 比较两个时间先后
     */
    public static boolean IsAfter(long ltime, long ltime1) {
        return ltime > ltime1;
    }

    public static boolean IsAfter(long ltime, Date date) {
        return ltime > date.getTime();
    }

    public static boolean IsAfter(long ltime, int... index) {
        return ltime > setTime(index);
    }

    public static boolean IsAfter(Date date, Date date1) {
        return date.after(date1);
    }

    public static boolean IsAfter(Date date, int... index) {
        return date.getTime() > setTime(index);
    }

    /**
     * 计算两个时间的时间差
     * 计算所得均为 计算周期(计算年,计算月,计算天,计算周.....)
     */
    public static long GetDiffMillSecond(long ltime, long ltime1) {
        return ltime - ltime1;
    }

    public static long GetDiffMillSecond(long ltime, Date date) {
        return ltime - date.getTime();
    }

    public static long GetDiffMillSecond(Date date, Date date1) {
        return date.getTime() - date1.getTime();
    }

    public static long GetDiffMillSecond(long ltime, int... index) {
        return ltime - setTime(index);
    }

    public static long GetDiffMillSecond(Date date, int... index) {
        return date.getTime() - setTime(index);
    }
    public static long GetDiffMillSecond(int year,int month,int day,int hour_of_day,int minute,int second,int millSecond, int... index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millSecond);
        return calendar.getTimeInMillis() - setTime(index);
    }


    public static long GetDiffSecond(long ltime, long ltime1) {
        return second(ltime - ltime1);
    }

    public static long GetDiffSecond(long ltime, Date date) {
        return second(ltime - date.getTime());
    }

    public static long GetDiffSecond(Date date, Date date1) {
        return second(date.getTime() - date1.getTime());
    }
    public static long GetDiffSecond(long ltime, int... index) {
        return second(ltime - setTime(index));
    }

    public static long GetDiffSecond(Date date, int... index) {
        return second(date.getTime() - setTime(index));
    }
    public static long GetDiffSecond(int year,int month,int day,int hour_of_day,int minute,int second,int millSecond, int... index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millSecond);
        return second(calendar.getTimeInMillis() - setTime(index));
    }


    public static long GetDiffMinute(long ltime, long ltime1) {
        return minute(ltime - ltime1);
    }

    public static long GetDiffMinute(long ltime, Date date) {
        return minute(ltime - date.getTime());
    }

    public static long GetDiffMinute(Date date, Date date1) {
        return minute(date.getTime() - date1.getTime());
    } public static long GetDiffMinute(long ltime, int... index) {
        return minute(ltime - setTime(index));
    }

    public static long GetDiffMinute(Date date, int... index) {
        return minute(date.getTime() - setTime(index));
    }
    public static long GetDiffMinute(int year,int month,int day,int hour_of_day,int minute,int second,int millSecond, int... index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millSecond);
        return minute(calendar.getTimeInMillis() - setTime(index));
    }


    public static long GetDiffHour(long ltime, long ltime1) {
        return hour(ltime - ltime1);
    }

    public static long GetDiffHour(long ltime, Date date) {
        return hour(ltime - date.getTime());
    }

    public static long GetDiffHour(Date date, Date date1) {
        return hour(date.getTime() - date1.getTime());
    }
    public static long GetDiffHour(long ltime, int... index) {
        return hour(ltime - setTime(index));
    }

    public static long GetDiffHour(Date date, int... index) {
        return hour(date.getTime() - setTime(index));
    }
    public static long GetDiffHour(int year,int month,int day,int hour_of_day,int minute,int second,int millSecond, int... index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millSecond);
        return hour(calendar.getTimeInMillis() - setTime(index));
    }


    public static long GetDiffDay(long ltime, long ltime1) {
        return day(ltime - ltime1);
    }

    public static long GetDiffDay(long ltime, Date date) {
        return day(ltime - date.getTime());
    }

    public static long GetDiffDay(Date date, Date date1) {
        return day(date.getTime() - date1.getTime());
    }

    public static long GetDiffDay(long ltime, int... index) {
        return day(ltime - setTime(index));
    }

    public static long GetDiffDay(Date date, int... index) {
        return day(date.getTime() - setTime(index));
    }
    public static long GetDiffDay(int year,int month,int day,int hour_of_day,int minute,int second,int millSecond, int... index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millSecond);
        return day(calendar.getTimeInMillis() - setTime(index));
    }


    public static long GetDiffWeek(long ltime, long ltime1) {
        return day(ltime - ltime1) / 7;
    }

    public static long GetDiffWeek(long ltime, Date date) {
        return day(ltime - date.getTime()) / 7;
    }

    public static long GetDiffWeek(Date date, Date date1) {
        return day(date.getTime() - date1.getTime()) / 7;
    }

    public static long GetDiffWeek(long ltime, int... index) {
        return day(ltime - setTime(index))/7;
    }

    public static long GetDiffWeek(Date date, int... index) {
        return day(date.getTime() - setTime(index))/7;
    }
    public static long GetDiffWeek(int year,int month,int day,int hour_of_day,int minute,int second,int millSecond, int... index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millSecond);
        return day(calendar.getTimeInMillis() - setTime(index))/7;
    }


    public static long GetDiffMonth(long ltime, long ltime1) {
        return month(ltime - ltime1);
    }

    public static long GetDiffMonth(long ltime, Date date) {
        return month(ltime - date.getTime());
    }

    public static long GetDiffMonth(Date date, Date date1) {
        return month(date.getTime() - date1.getTime());
    }
    public static long GetDiffMonth(long ltime, int... index) {
        return month(ltime - setTime(index));
    }

    public static long GetDiffMonth(Date date, int... index) {
        return month(date.getTime() - setTime(index));
    }
    public static long GetDiffMonth(int year,int month,int day,int hour_of_day,int minute,int second,int millSecond, int... index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millSecond);
        return month(calendar.getTimeInMillis() - setTime(index));
    }


    public static long GetDiffYear(long ltime, long ltime1) {
        return year(ltime - ltime1);
    }

    public static long GetDiffYear(long ltime, Date date) {
        return year(ltime - date.getTime());
    }

    public static long GetDiffYear(Date date, Date date1) {
        return year(date.getTime() - date1.getTime());
    }

    public static long GetDiffYear(long ltime, int... index) {
        return year(ltime - setTime(index));
    }

    public static long GetDiffYear(Date date, int... index) {
        return year(date.getTime() - setTime(index));
    }
    public static long GetDiffYear(int year,int month,int day,int hour_of_day,int minute,int second,int millSecond, int... index) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hour_of_day);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,second);
        calendar.set(Calendar.MILLISECOND,millSecond);
        return year(calendar.getTimeInMillis() - setTime(index));
    }


    /**
     * 内部公共方法
     *
     * @param index
     * @return
     */
    //周几判断
    private static String week(int index) {
        switch (index) {
            case Calendar.SUNDAY:
                return "星期天";
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
            default:
                return "星期六";
        }
    }

    //上下午
    private static String ampm(int index) {
//        return index == 0 ? "AM" : "PM";
        return index == 0 ? "上午" : "下午";
    }

    private static long second(long ltime) {
        return ltime / 1000;
    }

    private static long minute(long ltime) {
        return ltime / (1000 * 60);
    }

    private static int hour(long ltime) {
        return (int) (ltime / (1000 * 60 * 60));
    }

    private static int day(long ltime) {
        return (int) (ltime / (1000 * 60 * 60 * 24));
    }

    private static int month(long ltime) {
        return (int) (ltime / (1000 * 60 * 60 * 24 * 30));
    }

    private static int year(long ltime) {
        return (int) (ltime / (1000 * 60 * 60 * 24 * 365));
    }

    //设置获取时间戳(index 为null 时,默认1970-01-01 08:00:00   不为空时,其他参数默认为 01-01 00:00:00 00)
    private static long setTime(int... index) {
        if (index == null) {
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.set(Calendar.YEAR, index[0]);
            calendar.set(Calendar.MONTH, index[1] - 1);
        } catch (Exception e) {
            e.printStackTrace();
            calendar.set(Calendar.MONTH, 0);
        }
        try {
            calendar.set(Calendar.DAY_OF_MONTH, index[2]);
        } catch (Exception e) {
            e.printStackTrace();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }
        try {
            calendar.set(Calendar.HOUR_OF_DAY, index[3]);
        } catch (Exception e) {
            e.printStackTrace();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
        try {
            calendar.set(Calendar.MINUTE, index[4]);
        } catch (Exception e) {
            e.printStackTrace();
            calendar.set(Calendar.MINUTE, 0);
        }
        try {
            calendar.set(Calendar.SECOND, index[5]);
        } catch (Exception e) {
            e.printStackTrace();
            calendar.set(Calendar.SECOND, 0);
        }
        try {
            calendar.set(Calendar.MILLISECOND, index[6]);
        } catch (Exception e) {
            e.printStackTrace();
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar.getTimeInMillis();
    }
}
