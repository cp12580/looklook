package com.aaron.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Meizhi {
    public boolean error;
    public List<Results> results;

    public class Results{
        public String _id;
        public String desc;
        public String type;
        public String url;

    }
}
