package set;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import jdk.nashorn.internal.ir.IfNode;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import pojo.Book;
import pojo.Fruit;
import pojo.User;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-16 16:52
 */
public class TreeSetDemo {
    @Test
    public void test1() {
        TreeSet<Object> set = new TreeSet<>();

        set.add(new User("b", "123"));
        set.add(new User("a", "123"));
        set.add(new User("c", "123"));
        set.add(new User("d", "123"));
        set.add(new User("a", "213"));

        System.out.println(set);

        Object o = new Object();
    }

    @Test
    public void test2(){
        TreeSet<Object> set = new TreeSet<>();

        set.add(new Book("1", 1));
        set.add(new Book("2", 1));
        set.add(new Book("2", 23));
        set.add(new Book("3", 3));

        System.out.println(set);
    }

    @Test
    public void test3(){
        //使用comparator进行定制排序
        //按照书的价格进行排序
        Comparator<Book> comparator = (b1, b2) -> {
            double b1Price = b1.getPrice();
            double b2Price = b2.getPrice();
            return Double.compare(b1Price, b2Price);
        };

        TreeSet<Book> set = new TreeSet<>(comparator);
        //上面可以用下面的函数式接口的形式来简化代码
//        TreeSet<Book> set = new TreeSet<>(Comparator.comparingDouble(Book::getPrice));

//        TreeSet<Book> set = new TreeSet<>();



        set.add(new Book("a", 4));
        set.add(new Book("b", 1));
        set.add(new Book("c", 3));
        set.add(new Book("d", 3));

        set.forEach(System.out::println);



    }

    @Test
    public void test4(){
        TreeSet<Object> set = new TreeSet<>();
        set.add(new Fruit("1","1"));
        set.add(new Fruit("2","1"));
        set.add(new Fruit("3","1"));

        System.out.println(set);

    }



}
