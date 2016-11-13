package com.aaron.domain;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PengfuRequest {
    static Retrofit retrofit = null;
    public static PengfuApi getPengfuService(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://m.pengfu.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(PengfuApi.class);
    }
}
