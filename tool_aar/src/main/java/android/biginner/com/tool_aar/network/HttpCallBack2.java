package android.biginner.com.tool_aar.network;


import android.util.Log;

import com.google.gson.Gson;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 需要直接对网络返回的字符串进行处理
 * 如 返回数据为  密文字符串
 * 需要对返回 密文字符串 进行解密,在进行 Json 解析 时
 * 使用此类
 */

public abstract class HttpCallBack2<T> implements Callback<String> {
    private final String TAG = "TAG_HTTP";

    private Class<T> type;
    public HttpCallBack2(Class<T> type) {
        this.type = type;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {//网络访问  200
            String body = response.body();
            Log.e(TAG, body);
            body = onStringProcessor(body);
            try {
                onSuccess((T) new Gson().fromJson(body, type.newInstance().getClass()));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        } else {//http  失败
            Log.e(TAG, "http  失败 ");
            int code = response.raw().code();
            String message = response.raw().message();
            Log.e(TAG, "HTTP error" + code + "  " + message);
            onFailure(code, message);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

        String errorMessage = "onFailure";
        if (t instanceof SocketTimeoutException) {
            errorMessage = "服务器响应超时";
        } else if (t instanceof ConnectException) {
            errorMessage = "网络连接异常，请检查网络";
        } else if (t instanceof RuntimeException) {
            errorMessage = "运行时错误";
        } else if (t instanceof UnknownHostException) {
            errorMessage = "无法解析主机，请检查网络连接";
        } else if (t instanceof UnknownServiceException) {
            errorMessage = "未知的服务器错误";
        }
        onFailure(-999, errorMessage);
    }


    public abstract String onStringProcessor(String body);

    public abstract void onSuccess(T t);

    //    @CallSuper  //if overwrite,you should let it run.
    public void onFailure(int code, String message) {
        onFailure(code, message);
    }

}
