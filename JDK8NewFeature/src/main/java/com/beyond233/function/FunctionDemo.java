package com.beyond233.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <p>项目文档: 四种函数式接口</p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-23 0:35
 */
public class FunctionDemo {
    public static void main(String[] args) {

        //1.Function函数式接口：有输入有输出
//        Function<String, String> function = new Function<String, String>() {
//            @Override
//            public String apply(String s) {
//                return "hello";
//            }
//        };

        Function<String, String> function = (s) -> {return "hello";};
//        Function<String, String> function = s -> "hello";

        System.out.println(function.apply("hhh"));


        //2.Predicate 断定型接口： 有输入，只有布尔类型返回值
//        Predicate<String> predicate = new Predicate<String>() {
//            @Override
//            public boolean test(String s) {
//                return s.isEmpty();
//            }
//        };

        Predicate<String> predicate = s -> s.isEmpty();

        //3.Consumer消费型接口：有输入，没有输出
        Consumer<String> consumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        //4.Supplier 供给型接口：没有输入，有输出
        Supplier<String> supplier = new Supplier<String>() {
            @Override
            public String get() {
                return "233";
            }
        };


    }
}
