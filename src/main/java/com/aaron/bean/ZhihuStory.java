package com.aaron.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 * webview知乎
 */
public class ZhihuStory implements Serializable{
    public String body;
    public String image_source;
    public String title;
    public String image;
    public String share_url;
    public String ga_prefix;
    public int type;
    public int id;
    public List<String> css;

    @Override
    public String toString() {
        return "ZhihuStory{" +
                "body='" + body + '\'' +
                ", image_source='" + image_source + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", share_url='" + share_url + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", css=" + css +
                '}';
    }
}
