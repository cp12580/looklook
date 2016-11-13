package com.aaron.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ZhihuNews implements Serializable{
    public String date;
    public ArrayList<Stories> stories;
    public ArrayList<TopStories> top_stories;

    @Override
    public String toString() {
        return "ZhihuNews{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
