package android.biginner.com.tool_aar.network;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 创建人 : 黄卫志
 * 创建时间 : 2018/5/29/029.
 */
public class HttpService {
    private final String mBase_url = "";
    private final RequestService requestService;
    private static HttpService httpService;

    public static RequestService getService() {
        if (httpService == null)
            httpService = new HttpService();
        return httpService.requestService;
    }

    private HttpService() {
        OkHttpClient.Builder mbuilder = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//http数据log，日志中打印出HTTP请求&响应数据
                .connectTimeout(30, TimeUnit.SECONDS)//建立连接所用的时间
                .readTimeout(30, TimeUnit.SECONDS)//建立连接后从服务器读取到可用资源所用的时间
                .writeTimeout(30, TimeUnit.SECONDS);
        //添加网络拦截器
        if (false) {//判断是否是调试环境
            mbuilder.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }


        requestService = new Retrofit.Builder()
                .baseUrl(mBase_url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("YYYY-MM-dd'T'HH:MM:SS").create()))
                //直接获取返回字符串
//                .addConverterFactory(ScalarsConverterFactory.create())
                .client(mbuilder.build())
                .build().create(RequestService.class);
    }
}
