package android.biginner.com.tool_aar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * 权限工具类
 * 6.0 (API级别23)
 *
 * checkSelfPermission() 判断是否被授予权限
 *
 * requestPermissions() 请求权限
 */
public class PowerUtil {
    public static boolean hasPermission(@NonNull Context context, @NonNull String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission : permissions) {
            boolean hasPermission = (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                return false;
            }
        }
        return true;
    }
    //动态请求权限
    public static void requestPermissions(Object o, int requestCode, String... permissions) {
        if (o instanceof Activity) {
            ActivityCompat.requestPermissions(((Activity) o), permissions, requestCode);
        } else if (o instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) o).requestPermissions(permissions, requestCode);
        }
    }

}
