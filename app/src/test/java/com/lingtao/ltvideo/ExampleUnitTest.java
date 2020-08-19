package com.lingtao.ltvideo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private  int total = 0;
    private Object object = new Object();

    @Test
    public void addition_isCorrect() throws InterruptedException {
        Buffer buffer = new Buffer();
        Producer p1 = new Producer(buffer, 1);
        Producer p2 = new Producer(buffer, 2);
        Producer p3 = new Producer(buffer, 3);
        Consumer c1 = new Consumer(buffer, 1);
        p1.start();
        p2.start();
        p3.start();
        c1.start();

        while (true) {
            Thread.sleep(5000);
        }
    }

    /**
     * 缓冲区
     */
    class Buffer {
        private List<Integer> data = new ArrayList<>();
        private static final int MAX = 50;
        private static final int MIN = 0;

        public synchronized int get() {
            while (data.size() <= MIN) {
                try {
                    //队列没数据了，消费者要取数据需要等待生产者生产数据
                    wait();
                } catch (InterruptedException e) {

                }
            }
            Integer i = data.remove(0);
            notifyAll();
            return i;
        }

        public synchronized void put(int value) {
            //队列满了，生产者不能再放入数据
            //等待消费者取走数据后才能继续放入数据
            while (MAX <= data.size()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            data.add(value);
            notifyAll();
        }
    }


    /**
     * 消费者
     */
    class Consumer extends Thread {
        private Buffer buffer;
        private int number;

        public Consumer(Buffer b, int number) {
            buffer = b;
            this.number = number;
        }

        @Override
        public void run() {
            int value;
            for (int i = 0; i < 10000; i++) {
                // 从缓冲区中获取数据
                value = buffer.get();
                try {
                    // 模拟消费数据
                    sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println("消费者 #" + this.number + " got: " + value);
            }
        }
    }

    /**
     * 生产者
     */
    class Producer extends Thread {
        private Buffer buffer;
        private int number;

        public Producer(Buffer b, int number) {
            buffer = b;
            this.number = number;
        }

        @Override
        public void run() {
            for (int i = 0; i < 30000; i++) {
                buffer.put(i);
                printf(this.number);
            }
        }
    }

    private void printf(int number) {
        synchronized (object) {
            System.out.println("生产者" + number + "，生产了数据:" + (total++));
        }
    }
}