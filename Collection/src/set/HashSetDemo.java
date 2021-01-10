package set;

import com.sun.org.glassfish.external.statistics.AverageRangeStatistic;
import org.junit.Test;
import pojo.Book;
import pojo.Fruit;
import pojo.Ticket;
import pojo.User;

import java.util.Collection;
import java.util.HashSet;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-16 8:20
 */

public class HashSetDemo {
    public static void main(String[] args) {
        HashSet c = new HashSet<>();
        c.add(new User("jack","123"));
        //remove()判断对象是否存在集合里是通过equals()方法来判断，equals()方法相等那么两个对象的hashcode值也该相等
        boolean result = c.remove(new User("jack", "123"));
        System.out.println("移除对象："+result);

        c.add("hello");
        boolean result1 = c.remove("hello");
        System.out.println("移除常量字符串："+result1);


        c.add(new User("a", "123"));
        c.add(new User("b", "123"));
        c.add(new User("c", "123"));
        c.add(new User("d", "123"));
        for (Object o : c) {
            User user = (User) o;
            System.out.println(user.getName());
        }

    }

    @Test
    public void test(){
        HashSet<Book> set = new HashSet<>();

        set.add(new Book("a",12));
        set.add(new Book("a",12));
        set.add(new Book("a",33));
        set.add(new Book("b",12));

        System.out.println(set);

        System.out.println(new Book("a",12)==new Book("a",12));
        System.out.println(new Book("a",12).equals(new Book("a",12)));

    }

    @Test
    public void test2(){
        Fruit orange = new Fruit("橘子", "绿色");
        Fruit apple = new Fruit("苹果", "绿色");

        HashSet<Fruit> set = new HashSet<>();
        set.add(orange);
        set.add(apple);
        System.out.println("添加两个水果："+set);


        //问题一：此时set中有哪些元素？
        apple.setColor("红色");
        set.remove(apple);
        System.out.println("一："+set);

        //问题二：此时set中有哪些元素？
        set.add(new Fruit("苹果", "红色"));
        System.out.println("二："+set);

        //问题三：此时set中有哪些元素？
        set.add(new Fruit("苹果", "绿色"));
        System.out.println("三："+set);

    }

    @Test
    public void test3(){
        //Ticket 类没有重写hashcode和equals方法
        HashSet<Ticket> set = new HashSet<>();

        Ticket t1 = new Ticket(12);
        Ticket t2 = new Ticket(13);
        Ticket t3 = new Ticket(13);

        set.add(t1);
        set.add(t2);
        set.add(t3);

        System.out.println(set);

    }





}
