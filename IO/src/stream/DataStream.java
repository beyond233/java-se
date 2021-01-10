package stream;

import org.junit.Test;

import java.io.*;

/**
 * <p>项目文档: 数据流</p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-27 17:37
 */
public class DataStream {
    @Test
    public void test() throws IOException {
        //1.造
        DataOutputStream out = new DataOutputStream(new FileOutputStream("数据流.txt"));

        //2.写
        out.writeInt(100);
        byte[] bytes = {'a','b'};
        out.write(bytes);
        out.writeDouble(12.22);
        out.writeChar('a');
        out.writeChars("啊哈哈");
        out.writeBoolean(true);

        //3.
        out.close();

        //4.
        DataInputStream in = new DataInputStream(new FileInputStream("数据流.txt"));

        //5.按照写的顺序读
        int i = in.readInt();
        int read = in.read(new byte[2]);
        System.out.println(i);
        System.out.println((char)read);


    }
}
