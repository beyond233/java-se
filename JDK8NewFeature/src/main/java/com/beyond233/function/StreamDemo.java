package com.beyond233.function;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Stream流式计算
 * 数据存贮交给集合处理，计算则交给流来处理
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-23 0:57
 */
public class StreamDemo {
    public static void main(String[] args) {

        User a = new User(1, "a", 1, LocalDate.now());
        User b = new User(2, "b", 2, LocalDate.now().plusDays(1));
        User c = new User(3, "c", 3, LocalDate.now().plusDays(2));
        User d = new User(4, "d", 4, LocalDate.now().plusDays(3));
        User e = new User(5, "e", 5, LocalDate.now().plusDays(4));
        User f = new User(6, "f", 6, LocalDate.now().plusDays(5));
        User g = new User(7, "g", 7, LocalDate.now().plusDays(6));
        User h = new User(8, "h", 8, LocalDate.now().plusDays(7));
        User i = new User(8, "i", 9, LocalDate.now().plusDays(8));
        User j = new User(8, "j", 10, LocalDate.now().plusDays(9));

        List<User> list = Arrays.asList(a, b, c, d, e, f, g, h, i, j);

        //计算交给stream流
        list.stream()
                // 筛选id是偶数的
                .filter(user -> user.getId() % 2 == 0)
                // 年龄大于2
                .filter(user -> user.getAge() > 2)
                // 按照生日降序排列
                .sorted((u1, u2) -> u2.getBirthday().compareTo(u1.getBirthday()))
                // 名字转换为大写字母
                .map(user -> user.getName().toUpperCase())
                // 用户名字母倒序排列
                .sorted(Comparator.reverseOrder())
                // 输出指定个数用户
                .limit(3)
                .forEach(System.out::println);

    }
}
