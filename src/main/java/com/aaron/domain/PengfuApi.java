package com.aaron.domain;


import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/10.
 */
public interface PengfuApi {
    @GET("index_{page}.html")
    Observable<ResponseBody> getPengfuList(@Path("page") String page);
}
