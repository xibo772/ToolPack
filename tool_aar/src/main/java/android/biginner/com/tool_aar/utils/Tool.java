package android.biginner.com.tool_aar.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class Tool {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        context = null;
        return Math.round(dpValue * scale);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        context = null;
        return Math.round(pxValue / scale);
    }

    /**
     * 初始化  DisplayMetrics
     * @param activity
     * @return
     */
    public static DisplayMetrics initDisplay(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        activity = null;
        return dm;
    }

    /**
     * 获取屏幕宽度
     * @param activity
     * @return
     */
    public static int getWidth(Activity activity) {
        if (activity == null) return -1;
        return initDisplay(activity).widthPixels;
    }

    /**
     * 获取屏幕高度
     * @param activity
     * @return
     */
    public static int getHeight(Activity activity) {
        if (activity == null) return -1;
        return initDisplay(activity).heightPixels;
    }


}
