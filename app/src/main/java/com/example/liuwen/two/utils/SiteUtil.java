package com.example.liuwen.two.utils;

import com.example.liuwen.two.engine.ISite;
import com.example.liuwen.two.source.BiQuGe;
import com.example.liuwen.two.source.BiShenGe;
import com.example.liuwen.two.source.BinHuo;
import com.example.liuwen.two.source.DaoCaoRen;
import com.example.liuwen.two.source.DingDian;
import com.example.liuwen.two.source.JiDian;
import com.example.liuwen.two.source.KanShenZuo;
import com.example.liuwen.two.source.MianHuaTang;
import com.example.liuwen.two.source.NewBiQuGe;
import com.example.liuwen.two.source.ShouJi;

import java.util.ArrayList;
import java.util.List;

/**
 * author : liuwen
 * e-mail : liuwen370494581@163.com
 * time   : 2018/11/08 11:39
 * desc   :
 */
public class SiteUtil {

    public static List<ISite> getAllSites() {
        List<ISite> sites = new ArrayList<>();
        sites.add(new BiQuGe());
        sites.add(new DingDian());
        sites.add(new NewBiQuGe());
        sites.add(new KanShenZuo());
        sites.add(new BiShenGe());
        sites.add(new MianHuaTang());
        sites.add(new BinHuo());
        sites.add(new DaoCaoRen());
        sites.add(new JiDian());
        sites.add(new ShouJi());
        return sites;
    }
}
