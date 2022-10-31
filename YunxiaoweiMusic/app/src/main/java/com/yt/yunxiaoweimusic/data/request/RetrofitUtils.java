package com.yt.yunxiaoweimusic.data.request;


import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.yt.yunxiaoweimusic.data.response.ProcessInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitUtils {
    private static final String TAG = "RetrofitUtils";
    private static final int DEFAULT_TIMEOUT = 12 * 1000;

    public static final String CONNECT_TIMEOUT = "CONNECT_TIMEOUT";
    public static final String READ_TIMEOUT = "READ_TIMEOUT";
    public static final String WRITE_TIMEOUT = "WRITE_TIMEOUT";

    public <T> T getApiUrl(String baseUrl, Class<T> clazz) {
        return initRetrofit(initOkHttp(baseUrl), baseUrl).create(clazz);
    }

    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(OkHttpClient client, String baseUrl) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp(final String baseUrl) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//设置读取超时时间
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//设置请求超时时间
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)//设置写入超时时间
                .addInterceptor(processInterceptor)//添加打印拦截器
                .retryOnConnectionFailure(true);//设置出现错误进行重新连接。
        client.addInterceptor(timeoutInterceptor);
        return client.build();
    }


    private ProcessInterceptor processInterceptor = new ProcessInterceptor();

    Interceptor timeoutInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            int connectTimeout;
            int readTimeout;
            int writeTimeout;

            String connectNew = request.header(CONNECT_TIMEOUT);
            String readNew = request.header(READ_TIMEOUT);
            String writeNew = request.header(WRITE_TIMEOUT);

            if (!TextUtils.isEmpty(connectNew)) {
                connectTimeout = Integer.valueOf(connectNew);
            } else {
                connectTimeout = DEFAULT_TIMEOUT;
            }
            if (!TextUtils.isEmpty(readNew)) {
                readTimeout = Integer.valueOf(readNew);
            } else {
                readTimeout = DEFAULT_TIMEOUT;
            }
            if (!TextUtils.isEmpty(writeNew)) {
                writeTimeout = Integer.valueOf(writeNew);
            } else {
                writeTimeout = DEFAULT_TIMEOUT;
            }

            return chain
                    .withConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                    .withReadTimeout(readTimeout, TimeUnit.MILLISECONDS)
                    .withWriteTimeout(writeTimeout, TimeUnit.MILLISECONDS)
                    .proceed(request);
        }
    };

}

