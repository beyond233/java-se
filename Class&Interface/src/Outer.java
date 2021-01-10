import java.security.PrivateKey;

/**
 * <p>项目文档: 内部类</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-20 12:40
 */
public class Outer {
    static String name="x";

    /**成员内部类*/
    class Inner{
        private int age;
        public void b(){
            //在成员内部类中调用外部类方法
            a();
            //在成员内部类中调用外部类属性成员
            String s = name;
            //局部内部类
            class PartInner{
                private String name;
            }
        }

        /* 非静态内部类不能再有静态内部类
        static class PartInner2{}
        */


    }
    /** 静态成员内部类*/
    static class StaticInner{
        private String sex;

        public void c(){
            //在静态内部类中不能调用非静态方法
        }

        //静态内部类可以再有静态内部类
        static class PartInner2{

        }
        //静态内部类可以包含非静态内部类
        class PartInner3{

        }
    }

    /**
     * 外部类方法
     * */
    public void a(){
        System.out.println("外部类 a()方法");
    }

}

class Test{
    public static void main(String[] args) {
        //创建非静态内部类对象需要先创建外部类对象
        Outer o = new Outer();
        Outer.Inner inner = o.new Inner();

        //创建静态内部类对象
        Outer.StaticInner staticInner = new Outer.StaticInner();

        class aa implements Comparable<String>{
            @Override
            public int compareTo(String o) {
                return 0;
            }
        }

        //匿名内部类
        Comparable com = o1 -> 0;


    }
}