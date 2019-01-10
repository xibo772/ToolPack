package android.biginner.com.tool_aar.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时控件
 *
 * 初始化控件，调用 setTime（）方法，设置时间和回调
 * 重新使用调用 cancle（）
 * 页面销毁是调用 setnull（）；
 */
public class TimerTextView extends android.support.v7.widget.AppCompatTextView {

    private Timer timer;
    private TimerTask timerTask;
    private Runnable runnable;

    public TimerTextView(Context context) {
        super(context);
    }

    public TimerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private long ltime = 0;

    public void setTime(long ltime, Runnable runnable) {
        this.ltime = ltime;
        this.runnable = runnable;
        start();
    }

    private void start() {
        if (ltime <= 0) {
            if (timer != null) {
                timer.cancel();
                timer = null;
                timerTask = null;
            }
            setText("00 : 00 : 00");
        } else {
            if (timer != null) {
                timer.cancel();
                timer = new Timer();
            } else {
                timer = new Timer();
            }
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    ltime -= 1;
                    long h = ltime / (1 * 60 * 60);
                    long m = ltime / (1 * 60) - 60 * h;
                    long s = ltime / 1 - 60 * m - 60 * 60 * h;
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("text", String.format("%02d", h) + " : " + String.format("%02d", m) + " : " + String.format("%02d", s));
                    message.setData(bundle);
                    if (ltime <= 0) {
                        timer.cancel();
                        handler.post(runnable);
                    }
                    handler.sendMessage(message);
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            setText(data.getString("text"));
            super.handleMessage(msg);
        }
    };

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
    }

    public void setNull() {
        handler = null;
        ltime = 0;
        runnable = null;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timerTask = null;
    }
}
