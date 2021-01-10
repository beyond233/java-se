package List;

import org.junit.Test;
import pojo.Ticket;
import pojo.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-16 11:37
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add(new User("a", "123"));
        list.add(new User("b", "123"));
        list.add(new User("c", "123"));
        list.add(new User("d", "123"));
        list.add(new User("a", "213"));

        System.out.println("*************排序前**************");
        for (Object o : list) {
            System.out.println((User)o);
        }

        System.out.println("*************排序后**************");
        Collections.sort(list);
        for (Object o : list) {
            System.out.println((User)o);
        }

        System.out.println("*************统计个数**************");
        String[] s={"a","a","b","b","c"};
        System.out.println(count(s));

    }

    /**
     * 统计字符串数组中每个字符串出现的次数
     * @param args  字符串数组
     * @return java.lang.String
     * @since 2020/4/16 13:32
     */
    public static String count(String[] args){
        HashMap<String, Integer> map = new HashMap<>();
        for (String arg : args) {
            Integer count = map.get(arg);
            map.put(arg, count == null ? 1 : count+1);
        }
        return map.toString();
    }

    @Test
    public void test2(){
        List  list = new ArrayList<>();
        list.add("2");
        list.add("1");
        list.add("3");

        System.out.println(list);

        Collections.sort(list);
        System.out.println(list);
    }

    @Test
    public void test33(){
        ArrayList<Ticket> list = new ArrayList<>();

        list.add(new Ticket(1));
        list.add(new Ticket(2));
        list.add(new Ticket(3));

        //使用collections.sort(Collection col ,Comparable com)对不具有排序功能的集合进行排序

    }
}
