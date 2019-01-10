package android.biginner.com.tool_aar.custom;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * 时间选择器
 * 可实现年月日时分的显示
 */

public class DateDialog {
    /**
     * 显示样式
     * AlertDialog.THEME_DEVICE_DEFAULT_DARK  浅绿色  日历 钟表表盘
     *AlertDialog.THEME_DEVICE_DEFAULT_LIGHT 绿色 日历 钟表表盘
     *AlertDialog.THEME_HOLO_LIGHT
     * AlertDialog.THEME_HOLO_DARK
     * AlertDialog.THEME_TRADITIONAL
     */
    public static void show(Context context, CallBack callBack) {
        show(context, callBack, false);
    }

    public static void show(final Context context, final CallBack callBack, final boolean bHaveTime) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);
        new DatePickerDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker datePicker, final int iyear, final int imonth, final int iday) {
                        if (bHaveTime) {
                            new TimePickerDialog(context,AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                                    new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker timePicker, int ihour, int iminute) {
                                            String strTime = String.format("%04d-%02d-%02d %02d:%02d",
                                                    iyear, imonth + 1, iday, ihour, iminute);
                                            calendar.set(Calendar.YEAR, iyear);
                                            calendar.set(Calendar.MONTH, imonth);
                                            calendar.set(Calendar.DAY_OF_MONTH, iday);
                                            calendar.set(Calendar.HOUR, ihour);
                                            calendar.set(Calendar.MINUTE, iminute);
                                            calendar.set(Calendar.SECOND, 0);
                                            calendar.set(Calendar.MILLISECOND, 0);
                                            Long lTime = calendar.getTimeInMillis();
                                            callBack.callback(lTime, strTime);
                                        }
                                    },
                                    hour,
                                    minute,
                                    true)
                                    .show();
                        } else {
                            String strTime = String.format("%04d-%02d-%02d",
                                    iyear, imonth + 1, iday);
                            calendar.set(Calendar.YEAR, iyear);
                            calendar.set(Calendar.MONTH, imonth);
                            calendar.set(Calendar.DAY_OF_MONTH, iday);
                            calendar.set(Calendar.HOUR, hour);
                            calendar.set(Calendar.MINUTE, minute);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            Long lTime = calendar.getTimeInMillis();
                            callBack.callback(lTime, strTime);
                        }
                    }
                },
                year,
                month,
                day)
                .show();
    }

    public interface CallBack {
        void callback(long lTime, String strTime);
    }

}
