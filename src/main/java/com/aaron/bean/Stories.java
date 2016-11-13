package com.aaron.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/9.
 */
public class Stories implements Serializable{
//    "images": [
//            "http://pic4.zhimg.com/fca8f4c0a6da80a1c375b64bf6866ebb.jpg"
//            ],
//            "type": 0,
//            "id": 8774641,
//            "ga_prefix": "090812",
//            "title": "大误 · 此刻，我想起了我的「王水」姑娘"
    public int type;
    public int id;
    public String ga_prefix;
    public String title;
    public List<String> images;
    public boolean isRead = false;

    @Override
    public String toString() {
        return "Stories{" +
                "type=" + type +
                ", id=" + id +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", title='" + title + '\'' +
                ", images=" + images +
                ", isRead=" + isRead +
                '}';
    }
}
