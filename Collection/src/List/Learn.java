package List;

import org.junit.Test;

import java.util.ArrayList;

/**
List体系特点：有索引，有序，元素可重复
ArrayList原理：底层使用的是Object类型的引用数组。即array[0]=1;相当于Object array0=new Integer(1);

Methods():

void add(E element) ,  void add(int index,E element)
void addALL()  ,  void addAll(int index,Collection<> elements)
E remove(int index)
E set(int index,E element)
E get(int index)
 */
public class Learn {
    static ArrayList list=new ArrayList();
    static ArrayList list1=new ArrayList();
    static{
        for (int i=1000;i<1010;i++) {
            list1.add(i);
        }
    }

@Test
    public void demo(){
    list.add(2);
    list.add(3);
    System.out.println(list);
    list.add(0,1);
    System.out.println(list);
    list.remove(2);
    System.out.println(list);
    System.out.println(list.set(1,3));//set()返回原index位置的元素
    System.out.println(list);
}

//addALL(),addAll(int index,Collection<> elements)：插入到指定位置
    @Test
    public void demo1_0(){
        list.add(0);
        System.out.println("before:");
        list.addAll(list1);
        System.out.println("after:"+list);
        //错误示范
        list.addAll(15,list1);//index不能超过list原先的size，否则：java.lang.IndexOutOfBoundsException: Index: 12, Size: 11
        System.out.println("index："+list);
    }
    @Test
    public void demo1_1(){
        list.add(0);
        System.out.println("before:");
        list.addAll(list1);
        System.out.println("after:"+list);
        //正确示范
        list.addAll(9,list1);//list原先size为11，index=9在size内
        System.out.println("index："+list);
    }



}
