package com.aaron.utils;

import com.aaron.bean.DataBean;
import com.aaron.bean.PengfuBean;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
public class PengfuJsoup {
    private static PengfuJsoup pengfuJsoup;
    public static PengfuJsoup getInstance(){
        if (pengfuJsoup == null){
            synchronized (PengfuJsoup.class){
                if (pengfuJsoup == null){
                    pengfuJsoup = new PengfuJsoup();
                }
            }
        }
        return pengfuJsoup;
    }
    public List<PengfuBean> getPengfuList(Document document){
        List<PengfuBean> list = new ArrayList<>();
        Elements listItems = document.select("div.list-item");
        for (int i = 0; i <listItems.size(); i++){
            PengfuBean bean = new PengfuBean();
            Element pengfuItem = listItems.get(i);
            //头像信息
            Element headName = pengfuItem.select("div.head-name").first();
            if (headName != null){
                bean.userAvatar = headName.select("img").first().attr("src");
                bean.userName = headName.select("img").first().attr("alt");
                bean.title = headName.select("a[href]").get(1).text();//带有href属性的a元素
                bean.lastTime = headName.getElementsByClass("dp-i-b").first().text();
                bean.shareUrl = headName.select("a[href]").get(1).attr("href");
            }
            //文字或者图片
            Element conImg = pengfuItem.select("div").get(2);
            if (conImg != null){
                Element imgEle = conImg.select("img").first();
                DataBean dataBean = new DataBean();
                if (imgEle != null){
                    dataBean.showImg = imgEle.attr("src");
                    dataBean.wid = imgEle.attr("width");
                    dataBean.higt = imgEle.attr("height");
                    dataBean.gifsrcImg = imgEle.attr("gifsrc");
                }else {
                    dataBean.content = conImg.text();
                }
                bean.bean = dataBean;
            }
            //标签
            Element tagsEle = pengfuItem.select("div").get(3);
            if (tagsEle != null){
                List<String> tags = new ArrayList<>();
                Elements eles = tagsEle.select("a[href]");
                for (int j = 0; j < eles.size() ; j++) {
                    String tag = eles.get(j).text();
                    tags.add(tag);
                }
                bean.tags = tags;
            }
            list.add(bean);
//            Log.d("Aaron",bean.toString());
        }
        return list;
    }
}
