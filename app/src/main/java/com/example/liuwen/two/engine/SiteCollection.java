package com.example.liuwen.two.engine;

import com.example.liuwen.two.source.BiQuGe;
import com.example.liuwen.two.source.BiShenGe;
import com.example.liuwen.two.source.BinHuo;
import com.example.liuwen.two.source.DaoCaoRen;
import com.example.liuwen.two.source.DingDian;
import com.example.liuwen.two.source.JiDian;
import com.example.liuwen.two.source.KanShenZuo;
import com.example.liuwen.two.source.MianHuaTang;
import com.example.liuwen.two.source.Shunong;
import com.example.liuwen.two.source.Wenxuemi;
import com.example.liuwen.two.source.Wulin;
import com.example.liuwen.two.source.Xbiquge;
import com.example.liuwen.two.source.Zhuaji;
import com.example.liuwen.two.source.Zhuishu;

import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2019/04/16 16:41
 * desc   :单例模式，获取所有站点的类，可以添加自定义站点
 */
public class SiteCollection {


    private List<Site> sites = new ArrayList<>();

    /**
     * 默认添加这些站点解析，需要更改时对集合进行remove就行
     */
    private SiteCollection() {
        //normal
        sites.add(new Zhuishu());
        sites.add(new Xbiquge());
        sites.add(new Shunong());
        sites.add(new BiQuGe());
        sites.add(new Zhuaji());
        sites.add(new KanShenZuo());
        sites.add(new Wulin());
        sites.add(new Wenxuemi());
        sites.add(new DingDian());
        sites.add(new MianHuaTang());
        sites.add(new BiShenGe());

        //anim
        sites.add(new BinHuo());
        sites.add(new DaoCaoRen());

        //h
//        sites.add(new Shouji());
        sites.add(new JiDian());
//        sites.add(new Zhai());
    }

    public void addSites(List<Site> list) {
        for (Site site : list) {
            addSite(site);
        }
    }

    public void addSite(Site site) {
        if (!sites.contains(site)) {
            sites.add(site);
        }
    }

    public List<Site> getAllSites() {
        return sites;
    }

    public static SiteCollection getInstance() {
        return Holder.INSTANCE;
    }

    public static class Holder {
        private static SiteCollection INSTANCE = new SiteCollection();
    }
}
