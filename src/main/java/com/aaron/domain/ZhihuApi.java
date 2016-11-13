package com.aaron.domain;

import com.aaron.bean.ZhihuNews;
import com.aaron.bean.ZhihuStart;
import com.aaron.bean.ZhihuStory;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2016/9/9.
 */
public interface ZhihuApi {
    //得到今天的消息
    @GET("api/4/news/latest")
    Observable<ZhihuNews> getLastestNews();
    //得到往期消息
    @GET("api/4/news/before/{date}")
    Observable<ZhihuNews> getTheDaily(@Path("date") String date);
    //得到具体的文章
    @GET("api/4/news/{id}")
    Observable<ZhihuStory> getZhihuStory(@Path("id") String id);
    //http://news-at.zhihu.com/api/4/start-image/1080*1776
    @GET("api/4/start-image/720*1184")
    Observable<ZhihuStart> getZhihuStart();

}
