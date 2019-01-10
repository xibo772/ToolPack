package android.biginner.com.tool_aar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.Calendar;
import java.util.Map;

/**
 * 创建人 : 黄卫志
 * 创建时间 : 2018/8/20/020.
 */
public class CalendarView extends View {

    private float density;//屏幕密度
    private int yearCurr, monthCurr, dayCurr;//当前日期
    private int yearSel, monthSel, daySel;//选中时间
    private int yearLast, monthLast, dayLast;//上个月时间
    private int yearNext, monthNext, dayNext;//下个月时间

    private int mTouchSlop;//产生滑动的最小距离
    private Paint mPaint;//画笔
    private MonthListener monthListener;//月份显示监听

    private int NUM_rows = 5;
    private int sizeWidth;
    private int indexMonth;
    private float dayWidth;
    private float dayHeight;


    public CalendarView(Context context) {
        super(context);
        //使用 new 创建对象
        init(context);
    }


    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //在 xml 中调用
        init(context);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 在xml中使用且有style样式
        init(context);
    }

    private void init(Context context) {
        //获取手机屏幕密度----用于计算控件展示宽度
        density = getResources().getDisplayMetrics().density;
        //获取当前年月日
        Calendar calendar = Calendar.getInstance();
        yearCurr = calendar.get(Calendar.YEAR);
        monthCurr = calendar.get(Calendar.MONTH);
        dayCurr = calendar.get(Calendar.DAY_OF_MONTH);
        //获取滑动生效的最小距离
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        //初始化画笔
        mPaint = new Paint();
        //设置选中时间
        setSelectDate(yearCurr, monthCurr, dayCurr);
        //设置上个月时间
        setDateLast();
        //设置下个月时间
        setDateNext();
        if (monthListener != null) {
            monthListener.setTextMonth(yearSel, monthSel);
        }
    }

    /**
     * 设置选中日期
     *
     * @param year
     * @param month
     * @param day
     */
    private void setSelectDate(int year, int month, int day) {
        yearSel = year;
        monthSel = month;
        daySel = day;
    }

    /**
     * 设置上个月日期
     */
    private void setDateLast() {
        /*
        如果当前日期是30或31日时，
        直接使用 月份-1  获取上个月的月份时，会导致获取的是当月的 1或2日，
        此时应该将日期设置成为0，使时间回到上个月最后一天，
        eg：当前时间为 2018-07-31，
            获取上个月时，设置 月份-1，获取到的时间为 2018-07-01
            此时设置日期为 2018-07-00，使日期自动回到上个月的最后一天 即2018-06-30
         */
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yearSel);
        calendar.set(Calendar.MONTH, monthSel - 1);
        if (calendar.get(Calendar.MONTH) == monthSel) {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
        }
        yearLast = calendar.get(Calendar.YEAR);
        monthLast = calendar.get(Calendar.MONTH);
        dayLast = calendar.get(Calendar.DAY_OF_MONTH);

    }

    /**
     * 设置下个月日期
     */
    private void setDateNext() {
        /*
        同理，获取下个月时也会存在日期不对的问题
        eg：2018-08-31，直接用 月份+1 ，获取到的日期为 2018-10-01；
            此时设置日期为 2018-10-00，使日期自动回到上个月的最后一天 即2018-09-30
         */
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yearSel);
        calendar.set(Calendar.MONTH, monthSel + 1);
        if (calendar.get(Calendar.MONTH) == monthSel) {
            calendar.set(Calendar.DAY_OF_MONTH, 0);
        }
        yearNext = calendar.get(Calendar.YEAR);
        monthNext = calendar.get(Calendar.MONTH);
        dayNext = calendar.get(Calendar.DAY_OF_MONTH);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        //获取实际的周数
        NUM_rows = getRows();
        int sizeHeight = (NUM_rows * 70);
        setMeasuredDimension(sizeWidth, sizeHeight);
    }

    /**
     * @return
     */
    public int getRows() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(yearSel, monthSel, daySel);

        int days = getMonthDays(yearSel, monthSel);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int week = getWeekOfOneInMonth(yearSel, monthSel);
        //每月总天数（28、29、30、31），周数有三种情况：4、5、6周
        if (days == 28 && week == 1) {
            //1日为周日，且为平年2月（28天）
            return 4;
        } else if ((week == 6 && days == 31) || (week == 7 && days >= 30)) {
            //1日为周五，总天数为31天
            //1日为周六，总天数为30、31天
            return 6;
        } else {
            return 5;
        }
    }

    /**
     * 获取指定月份的总天数
     *
     * @param year
     * @param month
     * @return
     */
    private int getMonthDays(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定月份第一天是周几（1-7：日、一-----六）
     *
     * @param year
     * @param month
     * @return
     */
    private int getWeekOfOneInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        deawDate(canvas, yearSel, monthSel, indexMonth * sizeWidth, 0);
    }

    private void deawDate(Canvas canvas, int year, int month, int startX, int startY) {
        //保存画布
        canvas.save();
        //平移画布
        canvas.translate(startX, startY);

        //计算日期显示的宽、高
        dayWidth = getWidth() * 1.0f / 7;
        dayHeight = getHeight() / NUM_rows;

        int monthDays = getMonthDays(year, month);
        int week = getWeekOfOneInMonth(year, month);

        int[][] days = new int[NUM_rows][7];
        int column, row;
        for (int day = 0; day < monthDays; day++) {
            column = (day + week) % 7 - 1;
            row = (day + week) / 7 - 1;
            days[row][column] = day + 1;

            //背景
            drawBackground(canvas, column, row, year, month, day + 1);
            //圆背景
            drawCircle(canvas, column, row, year, month, day + 1);
            //圆点
            drawDot(canvas, column, row, year, month, day + 1);
            //日期
            drawText(canvas, column, row, year, month, day + 1);
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas, int column, int row, int year, int month, int day) {
        //设置日期字体大小
        mPaint.setTextSize(30);
        //设置日期字体样式
        mPaint.setStyle(Paint.Style.STROKE);
        //measureText():包含字符左右自身留白
        //getTextBound()：去掉首字符左侧留白、尾字符右侧留白
        //日期宽度 * 列数 + 日期留白（日期宽度 - 日期字符串长度）/2
        float startX =dayWidth  * column + (dayWidth - mPaint.measureText(day+""))/2;
        float startY = dayHeight * row + dayHeight/2 - (mPaint.ascent() + mPaint.descent())/2;



        Map<Integer, Integer> map = setTextColor(year, month, day);
        //只有今天做特殊处理
        if (day==dayCurr&&month==monthCurr&&year==yearCurr){
            if (map != null&&!map.isEmpty()) {

            }else{

            }
        }else{
            if (map != null&&!map.isEmpty()) {

            }else{

            }
        }

    }

    private Map<Integer, Integer> setTextColor(int year, int month, int day) {
        return null;
    }

    private void drawDot(Canvas canvas, int column, int row, int year, int month, int day) {

    }

    private void drawCircle(Canvas canvas, int column, int row, int year, int month, int day) {

    }

    private void drawBackground(Canvas canvas, int column, int row, int year, int month, int day) {

    }


    /**
     * 设置月份监听
     *
     * @param listener
     */
    public void setListener(MonthListener listener) {
        this.monthListener = listener;
    }

    /**
     * 月份显示回调监听
     */
    interface MonthListener {
        void setTextMonth(int year, int month);
    }

}
