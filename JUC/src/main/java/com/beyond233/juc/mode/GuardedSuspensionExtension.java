package com.beyond233.juc.mode;

import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 描述:保护性暂停模式扩展： 将获取资源、准备资源与线程解耦
 * 如果在多个类之间使用GuardedObject，将其作为参数传递并不是很方便，因此设计一个中间解耦类，这样不仅能够解耦结果等待者和结果生产者，
 * 还可以同时管理多个GuardedObject
 *
 * @author beyond233
 * @since 2021/1/24 18:30
 */
public class GuardedSuspensionExtension {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Integer id : MailBox.ids()) {
            new Postman(id).start();
        }

    }
}

@Slf4j
class People extends Thread {
    @Override
    public void run() {
        // 居民收信
        GuardedObject mail = MailBox.create();
        log.info("开始收信,id={}", mail.getId());
        Object resource = mail.get(5000);
        log.info("收到信，id={}，内容={}", mail.getId(), resource);
    }
}

@Slf4j
class Postman extends Thread {
    private Integer id;

    Postman(Integer id) {
        this.id = id;
    }

    @Override
    public void run() {
        // 邮递员将信送到邮箱
        GuardedObject mail = MailBox.get(this.id);
        log.info("开邮递员送信，id={}", mail.getId());
        mail.complete("内容：" + this.id);
    }
}

class MailBox {
    private static Hashtable<Integer, GuardedObject> box = new Hashtable<>();

    private static int id = 1;

    public static synchronized int generateId() {
        return id++;
    }

    public static GuardedObject get(Integer id) {
        return box.remove(id);
    }

    public static GuardedObject create() {
        GuardedObject mail = new GuardedObject(generateId());
        box.put(mail.getId(), mail);
        return mail;
    }

    public static Set<Integer> ids() {
        return box.keySet();
    }
}

