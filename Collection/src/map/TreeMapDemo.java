package map;

import org.junit.Test;
import pojo.Book;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-22 21:10
 */
public class TreeMapDemo {

    @Test
    public void test1(){
        TreeMap<Book, String> map = new TreeMap<>();

        map.put(new Book("Java编程思想", 32.1),"Java");
        map.put(new Book("Python入门", 25),"Python");
        map.put(new Book("GoLang语言实战", 82.2),"GO");
        map.put(new Book("Swift语言", 25),"Swift");

        for (Map.Entry<Book, String> entry : map.entrySet()) {
            System.out.println(entry);
        }


    }
}
