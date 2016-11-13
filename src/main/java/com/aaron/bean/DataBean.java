package com.aaron.bean;

/**
 * Created by Administrator on 2016/9/24.
 */
public class DataBean {
    public String content;
    public String showImg;
    public String gifsrcImg;
    public String wid;
    public String higt;

    @Override
    public String toString() {
        return "DataBean{" +
                "content='" + content + '\'' +
                ", showImg='" + showImg + '\'' +
                ", gifsrcImg='" + gifsrcImg + '\'' +
                ", wid='" + wid + '\'' +
                ", higt='" + higt + '\'' +
                '}';
    }
}
