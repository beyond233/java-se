package com.beyond233.juc.entity;

/**
 * <p>项目文档: double-checked 实现单例</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-19 0:29
 */

public class User {

    private String name;
    private int age;
    /**
     * 加volatile防止指令重排序
     */
    private volatile static User instance;

    /**
     * 禁止外部方法调用构造器
     */
    private User() {
    }

    public User(final String name, final int age) {
        this.name = name;
        this.age = age;
    }

    public User(final int age, final String name) {
        this.name = name;
        this.age = age;
    }


    /**
     * 获取单例
     * DCL：double checked lock (双重检验锁)
     */
    public static User getInstance() {
        if (instance == null) {
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User();
                }
            }
        }
        return instance;
    }


}
