package android.biginner.com.tool_aar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络连接连接
 *
 * 判断网络是否连接
 * 判断是否是 WiFi 连接
 *
 * 打开网络设置
 */
public class NetworkUtil {

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected(Context context){
        ConnectivityManager sm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (sm != null) {
            NetworkInfo info = sm.getActiveNetworkInfo();
            if (info != null&&info.isConnected()) {
                if (info.getState()== NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断是否是wifi连接
     */
}
