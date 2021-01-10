package system;

import org.junit.Test;
import pojo.User;

import java.io.*;

/**
 * <p>项目文档: 对象流</p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-28 9:49
 */
public class ObjectStream {

    /**
     * 序列化
     */
    @Test
    public void test(){
        //1.造
        ObjectOutputStream out = null;
        try {
            User user = new User("小徐", "男", 18);
            out = new ObjectOutputStream(new FileOutputStream("对象流.dat"));
            //2.写
            out.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //3.关
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 反序列化
     */
    @Test
    public void test1(){
        ObjectInputStream in = null;
        try {
            //1.造
            in = new ObjectInputStream(new FileInputStream("对象流.dat"));
            //2.读
            User user = (User) in.readObject();
            System.out.println(user);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //3.关
            if (in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
