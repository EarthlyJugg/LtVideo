package com.lingtao.ltvideo;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        DecalQueue2<Decal> decalQueue2 = new DecalQueue2();

        Decal decal1 = new Decal("1");
        Decal decal2 = new Decal("2");
        Decal decal3 = new Decal("3");
        Decal decal4 = new Decal("4");
        decalQueue2.put(decal1);
        decalQueue2.put(decal2);
        decalQueue2.put(decal3);
        decalQueue2.put(decal4);

        decalQueue2.printf();
        System.out.println("----------------");
        decalQueue2.stickTop(decal1);
        decalQueue2.printf();
        System.out.println("----------------");

        decalQueue2.remove(decal1);
        decalQueue2.printf();
        System.out.println("----------------");


        decalQueue2.put(decal2);
        decalQueue2.put(decal3);
        decalQueue2.put(decal4);

        decalQueue2.printf();
        System.out.println("----------------");

    }
}