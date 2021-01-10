/**
 * <p>项目文档: </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-20 19:08
 */
public interface UserInterface {
    public static final String NAME="xxx";
    public static final String MESSAGE = "hhh";

    public abstract void method1();


    public default void method2() {
        System.out.println(MESSAGE);
    }

    public static void method3(){
        System.out.println("static method");
    }
}

class UserInterImpl implements UserInterface{
    @Override
    public void method1() {
        System.out.println("重写接口中抽象方法");
    }

    @Override
    public void method2() {
        System.out.println("重写接口中default方法");
    }




    public static void main(String[] args) {
        UserInterImpl impl = new UserInterImpl();
        UserInterface inter = new UserInterImpl();
        inter.method2();

    }
}