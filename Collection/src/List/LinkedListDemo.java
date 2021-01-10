package List;

import org.junit.Test;
import pojo.Book;
import pojo.User;

import java.util.*;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-16 16:25
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<Object> list = new LinkedList<>();
        ArrayList<Object> arrayList = new ArrayList<>();
        list.addFirst(new User("1","1"));
        System.out.println("***"+list);

        list.addFirst(new User("1","2"));
        list.add(new User("2", "2"));
        list.addLast(new User("3", "3"));
        list.add(new User("4", "4"));

        System.out.println(list);
        list.remove(1);
        System.out.println(list);
        list.removeLast();
        System.out.println(list);

        System.out.println(list.get(2));
    }

    @Test
    public void test1(){
        Collection<Object> list = new LinkedList<>();
        Collection<Object> list1 = new LinkedList<>();
        list.add(new User());
        boolean empty = list.isEmpty();

        System.out.println(empty);
        System.out.println(list.containsAll(list1));
        list.retainAll(list1);
    }

    @Test
    public void test2(){
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        //错误方式1
//        Iterator<String> iterator = list.iterator();
//        while ((iterator.next())!=null){
//            System.out.println(iterator.next());
//        }

        //方式2

        User[] users = new User[3];
        System.out.println(users.length);



    }

    @Test
    public void test3(){
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("a");
        list.add("d");


        //removeFirstOccurrence()删除匹配到的第一个数据
        list.removeFirstOccurrence("a");
        list.set(0, "x");
        System.out.println(list);
        System.out.println("最末的a位置："+list.lastIndexOf("a"));


        //subList()返回左闭右开区间的集合，以下只会返回 "b"
        System.out.println(list.subList(1,2));
    }

    @Test
    public void test4(){
        LinkedList<Object> list = new LinkedList<>();
        list.add("a");
        list.add(new User());
        list.add("b");
        list.add("c");
        list.add(new Book("a", 12));

        //在复制时两个list的长度要保持一致
        List<Object> list1 = Arrays.asList(new Object[list.size()]);
        Collections.copy(list1,list);
        System.out.println(list1);

    }





}
