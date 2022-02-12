package com.beyond233;

/**
 * description: 逃逸分析的几种情况分析.
 * 如何判断是否发生逃逸分析：观察new的对象是否在方法外被调用，是则发生逃逸。
 *
 * @author beyond233
 * @since 2021/8/16 22:55
 */
public class EscapeAnalysis {
    public Object obj;

    /**
     * 方法返回Object对象，发生逃逸
     */
    public Object getInstance() {
        return obj == null ? new Object() : obj;
    }

    /**
     * 为成员属性赋值，发生逃逸
     */
    public void setObj() {
        this.obj = new Object();
    }

    /**
     * 对象的作用域只在当前方法中有效，没有发生逃逸
     */
    public void a() {
        Object o = new Object();
    }

    /**
     * 引用成员变量的值，发生逃逸
     */
    public void b() {
        Object instance = getInstance();
    }

}
