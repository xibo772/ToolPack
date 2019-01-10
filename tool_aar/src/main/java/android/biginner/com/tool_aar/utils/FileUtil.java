package android.biginner.com.tool_aar.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 */
public class FileUtil {
    private Context mContext = null;

    private FileUtil() {
    }

    /**
     * 获取 Assets 文件夹下的资源
     */
    public static InputStream getAssets(Context context, String name) {
        InputStream result=null;
        try {
            //AssetManager 不能处理单个超过 1MB 的文件
            AssetManager assets = context.getAssets();
            result=assets.open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取 res/raw 目录下文件
     */
    public static InputStream getRaw(Context context, int resourceID){
        InputStream result=null;
        result = context.getResources().openRawResource(resourceID);
        return result;
    }
}
