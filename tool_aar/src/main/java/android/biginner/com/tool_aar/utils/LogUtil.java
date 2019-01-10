package android.biginner.com.tool_aar.utils;


import android.util.Log;

/**
 * Created by Administrator on 2018/7/31/031.
 */
public class LogUtil {
    private static String tag = "LOGUTIL";
    private static LogUtil logUtil;
    public static boolean isDebug = true;// 是否需要打印bug
    private static final String TAG = "TAG=====>";

    public static LogUtil E(String msg) {
        Log.e(tag, msg);
        return init();
    }

    public static LogUtil E(long msg) {
        Log.e(tag, "long===>>> " + msg);
        return init();
    }

    public static LogUtil E(int msg) {
        Log.e(tag, "int===>>> " + msg);
        return init();
    }

    public static LogUtil E(String tags, String msg) {
        Log.e(tag,tags+"===>"+ msg);
        return init();
    }

    public static LogUtil E(String tags, int msg) {
        Log.e(tag, tags+"===>"+ msg);
        return init();
    }

    public static LogUtil E(String tags, long msg) {
        Log.e(tag, tags+"===>"+ msg);
        return init();
    }

    private static LogUtil init() {
        if (logUtil == null) {
            logUtil = new LogUtil();
        }
        return logUtil;
    }

    private LogUtil() {
    }


    // 下面是传入类名打印log
    public static void i(Class<?> _class, String msg) {
        if (isDebug)
            Log.i(_class.getName(), "=====>"+msg);
    }

    public static void d(Class<?> _class, String msg) {
        if (isDebug)
            Log.i(_class.getName(), "=====>"+msg);
    }

    public static void e(Class<?> _class, String msg) {
        if (isDebug)
            Log.i(_class.getName(), "=====>"+msg);
    }

    public static void v(Class<?> _class, String msg) {
        if (isDebug)
            Log.i(_class.getName(), "=====>"+msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, "=====>"+msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, "=====>"+msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, "=====>"+msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, "=====>"+msg);
    }
}
