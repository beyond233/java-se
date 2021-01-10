package com.beyond233.juc.collection;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * <p>项目文档: 阻塞队列</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-21 19:19
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        test2();


    }

    public static void test1(){
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(3);
        System.out.println(queue.add(1));
        System.out.println(queue.add(2));
        System.out.println(queue.add(3));
        //超过队列大小，抛出异常legalStateException: Queue full
//        System.out.println(queue.add(4));

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        //队列没有元素时仍然去移除元素：抛出异常：java.util.NoSuchElementException
        System.out.println(queue.remove());

    }
    public static void test2() throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);

        System.out.println(queue.offer(1));
        System.out.println(queue.offer(2));
        System.out.println(queue.offer(3));
        System.out.println(queue.offer(4));

        //获取对首元素
        System.out.println("对首元素: "+queue.peek());
        System.out.println("-----------------");

        System.out.println(queue.poll());
        System.out.println("对首元素: "+queue.peek());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        //超时等待2秒：若2秒后未获取到数据则退出
        System.out.println(queue.poll(2, TimeUnit.SECONDS));
    }
}
