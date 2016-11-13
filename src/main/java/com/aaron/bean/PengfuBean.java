package com.aaron.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class PengfuBean {
    public String userName;
    public String lastTime;
    public String userAvatar;
    public String title;
    public String shareUrl;
    public List<String> tags;
    public DataBean bean;

    @Override
    public String toString() {
        return "PengfuBean{" +
                "userName='" + userName + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", title='" + title + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", tags=" + tags +
                ", bean=" + bean +
                '}';
    }
}
