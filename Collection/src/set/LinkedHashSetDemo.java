package set;

import org.junit.Test;
import pojo.Fruit;

import java.util.LinkedHashSet;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-18 17:24
 */
public class LinkedHashSetDemo {

    @Test
    public void test0(){
        LinkedHashSet<Fruit> set = new LinkedHashSet<>();

        Fruit apple = new Fruit("苹果", "红色");
        Fruit orange = new Fruit("橘子", "黄色");

        set.add(apple);
        set.add(orange);

        System.out.println(set);


    }
}
