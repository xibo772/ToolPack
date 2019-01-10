package android.biginner.com.tool_aar.network;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jun on 2016/12/7.
 */

public abstract class HttpCallBack<T extends HttpResponse> implements Callback<T> {


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {//网络访问  200
//            if (response.body().isSuccess()) { //成功
//                if (response.body() == null || response.body().getData() == null && response.code() != 200)
//                    onFailure(ArithUtils.parseI(response.body().getCode()), response.body().getMessage());
//                else
//                    onSuccess(response.body());
//            } else {//失败
//                onFailure(ArithUtils.parseI(response.body().getCode()), response.body().getMessage());
//            }
        }else {//http  失败
//            LLogger.e("http  失败 ");
//            int code = response.raw().code();
//            String message = response.raw().message();
//            LLogger.e("HTTP error"+code+"  "+message);
//            onFailure(code,message);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

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


    public abstract void onSuccess(T calldata);

    //    @CallSuper  //if overwrite,you should let it run.
    public void onFailure(int code, String message) {
        onFailure(code, message);
    }

}
