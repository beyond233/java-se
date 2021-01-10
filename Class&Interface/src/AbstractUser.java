import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Redefinable;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-20 19:22
 */

/**
 * 抽象类可以实现接口，可以继承具体类，也可继承抽象类
* */
public abstract class AbstractUser extends Object implements Comparable{
    private String name;
    public int id;
    private static String addr;
    private final int age=18;

    public abstract void eat();

    public abstract void wc();


    public void sleep(){
        System.out.println("sleep");
    }

    public String getName() {
        return name;
    }
}

class Student extends AbstractUser{
    private String name;
    public int id;
    private static String addr;
    private final int age=18;

    @Override
    public void eat() {

    }

    @Override
    public void wc() {
        super.getName();
        System.out.println(super.id);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
