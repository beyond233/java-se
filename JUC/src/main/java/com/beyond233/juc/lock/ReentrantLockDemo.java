package com.beyond233.juc.lock;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>项目文档: ReentrantLock解决哲学家就餐死锁情况</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-18 13:52
 */
public class ReentrantLockDemo {
    public static void main(String[] args) {
        Chopstick1 c1 = new Chopstick1("1");
        Chopstick1 c2 = new Chopstick1("2");
        Chopstick1 c3 = new Chopstick1("3");
        Chopstick1 c4 = new Chopstick1("4");
        Chopstick1 c5 = new Chopstick1("5");

        new Philosopher1("p1", c1, c2).start();
        new Philosopher1("p2", c2, c3).start();
        new Philosopher1("p3", c3, c4).start();
        new Philosopher1("p4", c4, c5).start();
        new Philosopher1("p5", c5, c1).start();
    }
}

/**
 * 模拟筷子
 */
@AllArgsConstructor
class Chopstick1 extends ReentrantLock {
    private static final long serialVersionUID = 263060022266101832L;
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
class Philosopher1 extends Thread {

    /**
     * 左手筷子
     */
    Chopstick1 left;
    /**
     * 右手筷子
     */
    Chopstick1 right;

    public Philosopher1(String name, Chopstick1 left, Chopstick1 right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    /**
     * 模拟吃饭
     */
    public void eat() throws InterruptedException {
        log.debug("吃饭");
        sleep(1);
    }

    @Override
    public void run() {
        while (true) {
            //拿起左手筷子
            if (left.tryLock()) {
                try {
                    //拿起右手筷子
                    if (right.tryLock()) {
                        try {
                            //吃饭
                            eat();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            //释放锁：放下右手筷子
                            right.unlock();
                        }
                    }
                } finally {
                    //释放锁：放下左手筷子
                    left.unlock();
                }
            }
        }
    }
}

