package map;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-22 21:37
 */
public class PropertiesDemo {
    @Test
    public void test1() throws IOException {
        Properties pro = new Properties();
        FileInputStream stream = new FileInputStream("C:\\Users\\BEYOND\\IdeaProjects\\JavaSE8\\Collection\\src\\map\\user.properties");
        pro.load(stream);

        String name = pro.getProperty("name");
        String age = pro.getProperty("age");
        System.out.println(name+age+"岁了");
        stream.close();
    }
}
