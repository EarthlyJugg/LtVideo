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


        int[] arr = {1, 2, 4, 8, 10, 31};
        String[] strings = {"A", "B", "C", "D", "E", "F"};

        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int first = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                int h16 = first | arr[j];
                String s = Integer.toHexString(h16);
                System.out.println(strings[i] + "|" + strings[j] + "=" + s);
            }

        }

    }
}