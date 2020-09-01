package com.lingtao.ltvideo.giflib;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LtGif {



    private static List<GIfBean> pool = new LinkedList<>();

    public static GIfBean load(String url) {
        GIfBean gIfBean = new GIfBean(url);
        pool.add(gIfBean);
        return gIfBean;
    }

    public static void clear() {
        Iterator<GIfBean> iterator = pool.iterator();
        while (iterator.hasNext()) {
            GIfBean bean = iterator.next();
            bean.clear();
            iterator.remove();
            bean = null;
        }
    }


}
