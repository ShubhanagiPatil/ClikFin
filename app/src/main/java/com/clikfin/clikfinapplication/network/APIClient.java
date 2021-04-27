package com.clikfin.clikfinapplication.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class APIClient {

    public enum type {
        JSON, STRING
    }

    //private static final String BASE_URL = "http://192.168.1.5:9999";
    //private static final String BASE_URL = "http://15.207.2.168";   'http://app.clikfin.com/info/
    public static final String BASE_URL = "http://app.clikfin.com";

    private static ApiInterface jsonApiInterface;
    private static ApiInterface stringApiInterface;

    public static ApiInterface getClient(type clientType) {
        ApiInterface temp;
        if ( clientType == type.JSON) {
            temp = jsonApiInterface;
        } else {
            temp = stringApiInterface;
        }
        if (temp == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            Converter.Factory factory;

            if ( clientType == type.JSON) {
                factory = GsonConverterFactory.create();
            } else {
                factory = ScalarsConverterFactory.create();
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(factory)
                    .client(client)
                    .build();

            temp = retrofit.create(ApiInterface.class);
            if ( clientType == type.JSON) {
                jsonApiInterface = temp;
            } else {
                stringApiInterface = temp;
            }
        }
        return temp;
    }

public static Retrofit getRetrofit(){
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    Converter.Factory factory;

        factory = GsonConverterFactory.create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(factory)
            .client(client)
            .build();

    return retrofit;
}
}