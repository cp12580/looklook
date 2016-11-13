package com.aaron.domain;


import com.aaron.bean.Meizhi;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/11/11.
 */
public interface MeizhiApi {
    @GET("api/data/福利/10/{page}")
    Observable<Meizhi> getMeizhiList(@Path("page")int page);
}
