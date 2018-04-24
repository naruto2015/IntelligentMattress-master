package com.rt.sm.internet;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rt.sm.BuildConfig;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gaodesong on 17/4/17.
 */

public class RetrofitUtils {


   //public static final String BASE = "http://ajzx.wttskb.com";

    public static final String BASE="http://sleepeq.lineshen.com/";

    public static final String BASE_URL = BASE+"sys/";

    public static String STATISTICS="http://sm-demo.oss-cn-hangzhou.aliyuncs.com/index.html#/count";

    public static String FIND="http://sm-demo.oss-cn-hangzhou.aliyuncs.com/index.html#/find";

    public static String NOTIFI_DETAIL="http://sm-demo.oss-cn-hangzhou.aliyuncs.com/index.html#/noty";

    public static String LOG_DETAIL="http://sm-demo.oss-cn-hangzhou.aliyuncs.com/index.html#/detail";

    public static String HEAD=BASE+"pub/public/downloadimage?sessiontoken=";

    public static Retrofit retrofit;

    public static OkHttpClient client;

    public static OkHttpClient.Builder builder;

    private static Context context;

    public static Api api;

    private static RetrofitUtils mInstance;

    public RetrofitUtils(){
        initOkhttpClient();
        retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public  static RetrofitUtils getInstance(Context mcontext) {

            if (mInstance == null){
                synchronized (RetrofitUtils.class){
                    context=mcontext.getApplicationContext();
                    mInstance = new RetrofitUtils();
                }
            }

            api= retrofit.create(Api.class);
            return mInstance;

    }


    private void initOkhttpClient()
    {
        builder = new OkHttpClient().newBuilder();
        //cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        builder
                .readTimeout(10000, TimeUnit.SECONDS)
                .connectTimeout(10000, TimeUnit.SECONDS)
                .writeTimeout(10000,TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        client=builder.build();

    }





}
