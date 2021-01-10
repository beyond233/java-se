package util;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;
import org.junit.Test;

import java.io.*;

/**
 * <p>项目文档: 对象克隆工具类 </p>
 *
 * @author beyond233
 * @version 1.0
 * @since 2020-06-26 9:29
 */
public  class CloneUtil {
    private CloneUtil(){
        throw new AssertionError();
    }
    public static <T> T clone(T object) throws Exception {
        //1.将对象流化并写出到字节数组输出流的内部字节数组中
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ObjectOutputStream oOut = new ObjectOutputStream(bOut);
        oOut.writeObject(object);

        //2.从字节数组输出流的内部字节数组中读入对象流并反流化为对象
        ByteArrayInputStream bIn = new ByteArrayInputStream(bOut.toByteArray());
        ObjectInputStream oIn = new ObjectInputStream(bIn);
        return (T) oIn.readObject();
    }
}

/**
 * User类
 */
class User implements Serializable {
    private String name;
    private static final Long serialVersionUID=-9102017020286042305L;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
    public static void main(String[] args) throws Exception {
        User user = new User("233");
        User clone = CloneUtil.clone(user);
        System.out.println(clone);
        System.out.println(user==clone);

    }


}

