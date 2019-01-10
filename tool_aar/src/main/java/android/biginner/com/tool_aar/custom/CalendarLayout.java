package android.biginner.com.tool_aar.custom;

import android.biginner.com.tool_aar.R;
import android.biginner.com.tool_aar.utils.LogUtil;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 日历控件
 * 创建人 : 黄卫志
 * 创建时间 : 2018/8/13/013.
 */
public class CalendarLayout extends LinearLayout implements View.OnClickListener {

    private float startX;
    private View topView;
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private int sizeHeight;
    private int sizeWidth;
    private ImageView imageUp;
    private ImageView imageDown;
    private Adapter adapter;

    public CalendarLayout(@NonNull Context context) {
        this(context, null);
    }

    public CalendarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        LogUtil.E("---------------测量=============");
        LogUtil.E(modeHeight);
        LogUtil.E(modeWidth);
        LogUtil.E(sizeHeight);
        LogUtil.E(sizeWidth);
        LogUtil.E("---------------测量=============\n");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView() {
        topView = LayoutInflater.from(getContext()).inflate(R.layout.layout_top_text, null);
        addView(topView);
        ((TextView) topView.findViewById(R.id.tv_date)).setText(year + "年" + month + "月");
        imageUp = (ImageView) topView.findViewById(R.id.btn_up);
        imageUp.setOnClickListener(this);
        imageDown = (ImageView) topView.findViewById(R.id.btn_down);
        imageDown.setOnClickListener(this);

        GridViewNoScroll gridView = new GridViewNoScroll(getContext());
        gridView.setNumColumns(7);
        gridView.setBackgroundColor(Color.RED);
        adapter = new Adapter(getContext(),layout);
        adapter.setData(getDays(month));
        gridView.setAdapter(adapter);
        gridView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        addView(gridView);
        setBackgroundColor(Color.GRAY);
    }

    private int layout;
    public void setDateLayout(@LayoutRes int layout){
        this.layout=layout;
    }

    private void last() {
        month--;
        adapter.setData(getDays(month));
        ((TextView) topView.findViewById(R.id.tv_date)).setText(year + "年" + month + "月");
    }

    private void next() {
        month++;
        adapter.setData(getDays(month));
        ((TextView) topView.findViewById(R.id.tv_date)).setText(year + "年" + month + "月");
    }
    private class Adapter extends BaseAdapter {
        private Context context;
        private List<DateBean> data;
        private int layoutID;

        public Adapter(Context context, int layoutID) {
            this.context = context;
            this.layoutID = layoutID;
        }

        public void setData(List<DateBean> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public DateBean getItem(int position) {
            return data == null || data.isEmpty() ? null : data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return data == null || data.isEmpty() ? 0 : position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_day, null);
            TextView textView = (TextView) convertView.findViewById(R.id.tv);
            DateBean bean = data.get(position);
            if (isEqualsDay(bean.getYear(), bean.getMonth(), bean.getDay())) {
                textView.setBackgroundColor(Color.GREEN);
            } else {
                textView.setBackgroundColor(Color.WHITE);
            }
            textView.setText(bean.day + "");
            return convertView;
        }
    }
    private class DateBean {
        private int year;
        private int month;
        private int day;

        public DateBean(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_up) {
            last();
        } else if (v.getId() == R.id.btn_down) {
            next();
        }
    }






    //日期列表获取
    private List<DateBean> getDays(int month) {
        List<DateBean> list = new ArrayList<>();
        //这个月1号是周几
        int weekMonthOne = GetWeekMonthOne(month);
        //这个月总天数
        int days = GetMonthDays(month);
        //上个月总天数
        int daysLast = GetLastMonthDays(month);

        int weekNextMonthOne = GetWeekMonthOne(month + 1);
        for (int j = 0; j < weekMonthOne - 1; j++) {
            list.add(0, new DateBean(year,month-1,(daysLast - j)));
        }
        for (int j = 0; j < days; j++) {
            list.add(new DateBean(year,month,j + 1));
        }
        if (weekNextMonthOne != 1) {
            for (int j = 0; j < 7 + 1 - weekNextMonthOne; j++) {
                list.add(new DateBean(year,month,j + 1));
            }
        }
        return list;
    }

    /**
     * 事件分发处理
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
//                if(父容器需要点击事件){
                intercepted = true;
//                }else{
//                    intercepted = false;
//                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float diff = endX - startX;
                if (Math.abs(diff) < sizeWidth / 3)
                    break;

                if (diff > 0) {
                    last();
                } else if (diff < 0) {
                    next();
                }
                break;

        }
        return true;
    }

    //是否是今天
    private boolean isEqualsDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(Calendar.YEAR);
        int month_ = calendar.get(Calendar.MONTH) + 1;
        int day_ = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month - 1, day);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (year == year_ && month == month_ && day == day_) {
            return true;
        } else {
            return false;
        }
    }


    //指定月份1号是周几
    public int GetWeekMonthOne(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //指定时间 的某月多少天
    public int GetMonthDays(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    //指定时间的下个月的总天数
    public int GetLastMonthDays(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1 - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


}
