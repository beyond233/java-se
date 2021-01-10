package com.beyond233.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * <p>项目文档: Stream流式计算
 *             数据存贮交给集合处理，计算则交给流来处理
 * </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-23 0:57
 */
public class StreamDemo {
    public static void main(String[] args) {
        User a = new User(1, "a", 1);
        User b = new User(2, "b", 2);
        User c = new User(3, "c", 3);
        User d = new User(4, "d", 4);
        User e = new User(5, "e", 5);
        User f = new User(6, "f", 6);
        User g = new User(7, "g", 7);
        User h = new User(8, "h", 8);

        List<User> list = Arrays.asList(a, b, c, d, e,f,g,h);

        //计算交给stream流
        list.stream()
                //筛选id是偶数的
                .filter(user -> user.getId() % 2 == 0)
                //年龄大于2
                .filter(user -> user.getAge()>2)
                //名字转换为大写字母
                .map(user -> user.getName().toUpperCase())
                //用户名字母倒序排列
                .sorted(Comparator.reverseOrder())
                //只输出1个用户
                .limit(2)
                .forEach(System.out::println);


    }
}
