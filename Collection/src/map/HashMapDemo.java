package map;

import org.junit.Test;
import pojo.Book;
import pojo.Phone;

import javax.management.StringValueExp;
import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-19 16:41
 */
public class HashMapDemo {


    /**
     * 统计字符串数组中每个字符串出现的次数
     * @param args  字符串数组
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
    public void test1(){
        HashMap<String, Book> map = new HashMap<>(14,10);

        map.put("1", new Book("a", 1));
        map.put("2", new Book("b", 2));
        map.put("3", new Book("c", 3));

        for (String o : map.keySet()) {
            System.out.println(o);
            System.out.println(map.get(o));
        }

        for (Book value : map.values()) {
            System.out.println(value);
        }

        for (Map.Entry<String, Book> entry : map.entrySet()) {
            System.out.println(entry);
        }
        System.out.println(map.containsKey(new String("1")) );
        System.out.println(map.containsValue(new Book("a",1)) );

        map.put(null, null);

    }

    @Test
    public void test2(){
        HashMap<Phone, String> map = new HashMap<>();
        Phone phone = new Phone("苹果");
        String put = map.put(phone, "1");

        phone.setBrand("华为");
        System.out.println(map.containsKey(phone));
        //改变book引用变量的
        phone = new Phone("小米");
        System.out.println(map.containsKey(phone));

    }

    @Test
    public void test4(){
        Integer f1 = 100, f2 = 100, f3 = 150, f4 = 150;

        System.out.println(f1 == f2);// true
        System.out.println(f3 == f4); //false

    }

    @Test
    public void test5(){
        Double d1 = 100.0, d2 = 100.0, d3 = 150.0, d4 = 150.0;

        System.out.println(d1 == d2);
        System.out.println(d3 == d4);




        String name="233";
        if ("".equals(name)) {

        }

//        name.equals("")
    }

    public static void m1(String name){
        if ("".equals(name)) {
            System.out.println("空");

        }
    }
    public static void m2(String name){
        if (name.equals("")) {
            System.out.println("空");
        }
    }

    public static void main(String[] args) {
        String s=null;
//        m1(s);
        m2(s);
    }


}
