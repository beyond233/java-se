package com.beyond233.juc.mode;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 描述: 异步模式之生产者消费者模式
 *
 * @author beyond233
 * @since 2021/1/24 19:53
 */
public class ProducerConsumer {

    public static void main(String[] args) {
        MessageQueue queue = new MessageQueue(1);

        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> queue.put(new Message(id, "value" + id)), "producer【" + id + "】").start();
        }

        new Thread(() -> {
            for (; ; ) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费消息： " + queue.take());
            }
        }, "consumer【" + 0 + "】").start();

    }

}


/**
 * 消息队列
 */
@Slf4j
class MessageQueue {

    /**
     * 消息队列： 存放消息的容器
     */
    private final LinkedList<Message> queue = new LinkedList<>();

    /**
     * 队列大小
     */
    private int capacity;

    /**
     * 取出消息
     */
    public Message take() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    log.info("消息队列 空空如也!  消费者waiting");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }
    }

    /**
     * 存放消息
     */
    public void put(Message message) {
        synchronized (queue) {
            while (queue.size() == capacity) {
                try {
                    log.info("消息队列 已经满了!  生产者waiting");
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(message);
            queue.notifyAll();
        }
    }

    MessageQueue(int capacity) {
        this.capacity = capacity;
    }
}

/**
 * 消息
 */
class Message {
    private Integer id;
    private Object value;

    Message(Integer id, Object value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return this.id;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + this.id +
                ", value=" + this.value +
                '}';
    }
}