package com.lingtao.ltvideo;

import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        DecalQueue2<String> decalQueue2 = new DecalQueue2();

        decalQueue2.put("1");
        decalQueue2.put("2");
        decalQueue2.put("3");
        decalQueue2.put("4");
        decalQueue2.printf();
        System.out.println();
        decalQueue2.getSwap(1);
        System.out.println();
        decalQueue2.printf();

        decalQueue2.getSwap(0);
        System.out.println();
        decalQueue2.printf();
    }
}