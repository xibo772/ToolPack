package android.biginner.com.tool_aar.custom;

import android.biginner.com.tool_aar.R;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 吐司 图片
 * Created by buchou on 2017/9/27.
 */

public class ToastImage {

    public static void showLong(Context context) {
        showLong(context, " Hello World !");
    }

    public static void showLong(Context context, String str) {
        showLong(context, null, str);
    }

    public static void showLong(Context context, View view, String str) {
        showLong(context, view, str, Color.BLACK, Color.parseColor("#a0a3a0"), 24);
    }

    public static void showLong(Context context, View view, String str, int color, int layoutColor, float size) {
        try {
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            LinearLayout lLayout = new LinearLayout(context);
            lLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lLayout.setGravity(Gravity.CENTER_VERTICAL);
            lLayout.setBackgroundResource(R.drawable.background);
            if (view == null) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.ic_alarm);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
                lLayout.addView(imageView);
            } else {
                lLayout.addView(view);
            }
            TextView textView = new TextView(context);
            textView.setText(str);
            textView.setTextColor(color);
            textView.setTextSize(size);
            lLayout.addView(textView);
            toast.setView(lLayout);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showShort(Context context) {
        showShort(context, " Hello World !");
    }

    public static void showShort(Context context, String str) {
        showShort(context, null, str);
    }

    public static void showShort(Context context, View view, String str) {
        showShort(context, view, str, Color.BLACK, Color.parseColor("#a0a3a0"), 24);
    }

    public static void showShort(Context context, View view, String str, int textColor, int layoutColor, float size) {
        try {
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            LinearLayout lLayout = new LinearLayout(context);
            lLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lLayout.setGravity(Gravity.CENTER_VERTICAL);
            lLayout.setBackgroundResource(R.drawable.background);
            if (view == null) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.ic_alarm);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
                lLayout.addView(imageView);
            } else {
                lLayout.addView(view);
            }
            TextView textView = new TextView(context);
            textView.setText(str);
            textView.setTextColor(textColor);
            textView.setTextSize(size);
            lLayout.addView(textView);
            toast.setView(lLayout);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
