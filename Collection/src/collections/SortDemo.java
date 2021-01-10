package collections;

import org.junit.Test;
import pojo.Ticket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * <p>项目文档:
 *  Collections工具类的sort方法有两种重载的形式
 *  第一种: 要求传入的待排序容器中存放的对象比较实现Comparable接口以实现元素的比较；
 *  第二种: 不强制性的要求容器中的元素必须可比较，但是要求传入第二个参数，参数是Comparator接口的子类型（需要重写compare方法
 *  实现元素的比较），相当于一个临时定义的排序规则，其实就是通过接口注入比较元素大小的算法，也是对回调模式的应用（Java中对
 *  函数式编程的支持）。
 *
 * </p>
 * @author beyond233
 * @version 1.0
 * @since 2020-06-26 12:41
 */
public class SortDemo {
    @Test
    public void test33(){
        ArrayList<Ticket> list = new ArrayList<>();

        list.add(new Ticket(1));
        list.add(new Ticket(2));
        list.add(new Ticket(3));

        //使用Collections.sort(Collection col ,Comparator com)对不具有排序功能的集合进行排序
        //升序
        Collections.sort(list, Comparator.comparingInt(Ticket::getPrice));
        list.forEach(System.out::println);

        //降序
        Collections.sort(list, (o1, o2) -> o2.getPrice() - o1.getPrice());
        list.forEach(System.out::println);

    }
}
