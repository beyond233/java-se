package com.beyond233.juc.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>项目文档: 死锁测试</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-17 23:11
 */
@Slf4j(topic = "哲学家进餐问题")
public class DeadLock {
    public static void main(String[] args) {
        Chopstick c1 = new Chopstick("1");
        Chopstick c2 = new Chopstick("2");
        Chopstick c3 = new Chopstick("3");
        Chopstick c4 = new Chopstick("4");
        Chopstick c5 = new Chopstick("5");

        new Philosopher("p1", c1, c2).start();
        new Philosopher("p2", c2, c3).start();
        new Philosopher("p3", c3, c4).start();
        new Philosopher("p4", c4, c5).start();
        new Philosopher("p5", c5, c1).start();

    }
}


/**
 * 模拟筷子
 */
@Data
@AllArgsConstructor
class Chopstick {
    private String name;

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}

/**
 * 模拟哲学家
 */
@Slf4j(topic = "哲学家")
class Philosopher extends Thread {

    /**
     * 左手筷子
     */
    final Chopstick left;
    /**
     * 右手筷子
     */
    final Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    /**
     * 模拟吃饭
     */
    public void eat() {
        log.debug("吃饭");
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            //拿起左手筷子
            synchronized (left) {
                //拿起右手筷子
                synchronized (right) {
                    eat();
                }
                // 放下右手筷子
            }
            // 放下左手筷子
        }
    }
}

