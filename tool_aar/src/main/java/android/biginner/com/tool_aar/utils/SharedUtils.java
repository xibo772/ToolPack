package android.biginner.com.tool_aar.utils;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * 共享参数 工具
 */

public class SharedUtils {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor edit;

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences sharedPreference) {
        sharedPreferences = sharedPreference;
    }

    public static void init(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
    }

    /**
     * 存的方法
     */
    public static void putString(String key, String value) {
        if (edit != null) {
            edit.putString(key, value);
            edit.commit();
        }
    }

    public static void putBoolean(String key, boolean value) {
        if (edit != null) {
            edit.putBoolean(key, value);
            edit.commit();
        }
    }

    public static void putInt(String key, int value) {
        if (edit != null) {
            edit.putInt(key, value);
            edit.commit();
        }
    }

    public static void putFloat(String key, int value) {
        if (edit != null) {
            edit.putFloat(key, value);
            edit.commit();
        }
    }

    public static void putLong(String key, int value) {
        if (edit != null) {
            edit.putLong(key, value);
            edit.commit();
        }
    }

    /**
     * 取得方法
     */
    public static String getString(String key, String deaflut) {
        if (sharedPreferences != null) {
            return sharedPreferences.getString(key, deaflut);
        }
        return "";
    }

    public static boolean getBoolean(String key, boolean deaflutValue) {
        if (sharedPreferences != null) {
            boolean aBoolean = sharedPreferences.getBoolean(key, deaflutValue);
            return aBoolean;
        }
        return false;
    }

    public static int getInt(String key, int deaflutValue) {
        if (sharedPreferences != null) {
            int anInt = sharedPreferences.getInt(key, deaflutValue);
            return anInt;
        }
        return deaflutValue;
    }

    public static float getFloat(String key, float deaflutValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getFloat(key, deaflutValue);
        }
        return deaflutValue;
    }

    public static long getLong(String key, long deaflutValue) {
        if (sharedPreferences != null) {
            return sharedPreferences.getLong(key, deaflutValue);
        }
        return deaflutValue;
    }
}
