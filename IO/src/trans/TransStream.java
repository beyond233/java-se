package trans;


import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * <p>项目文档: 转换流</p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-27 11:36
 */
public class TransStream {
    @Test
    public void test() throws IOException {
        //1.
        File source = new File("日志.txt");
        File dest = new File("日志(GBK编码).txt");

        //2.
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(dest);
        //以UTF-8编码读取文件,不指定字符集时为项目默认字符集
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        //以GBK编码写文件,不指定字符集时为项目默认字符集
        OutputStreamWriter writer = new OutputStreamWriter(out,"GBK");

        //3.
        char[] chars = new char[5];
        int len;
        while ((len = reader.read(chars)) != -1) {
            String string = new String(chars, 0, len);
            System.out.print(string);
            writer.write(string);
        }

        //4.
        reader.close();
        writer.close();

    }
}
