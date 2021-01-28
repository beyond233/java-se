package com.beyond233.juc.lock;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * 偏向锁学习
 *
 * @author beyond233
 * @since 2021/1/17 21:55
 */
@Slf4j
public class BiasedLock {
    public static void main(final String[] args) {
        final Dog dog = new Dog();

        log.info(ClassLayout.parseInstance(dog).toPrintable());

        synchronized (dog) {
            log.info(ClassLayout.parseInstance(dog).toPrintable());
        }

        log.info(ClassLayout.parseInstance(dog).toPrintable());
    }

}

class Dog {
}
