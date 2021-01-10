package system;

import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * <p>项目文档: 标准输入输出流</p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-27 12:38
 */
public class InOutStream {
    @Test
    public static void systemInTest() throws IOException {
        //题目：从键盘输入字符串，要求将读取到的整行字符串转换为大写字母输出，然后继续进行输入操作，直到输入e或exit才退出此程序。
        //原理：使用System.in实现。将其通过转换流转换为字符流，再用缓冲流包装，这
        // 样就可以使用缓冲流的readLine()方法读取一整行字符串了。

        //1.
        BufferedInputStream stream = new BufferedInputStream(System.in);

        //2.
        System.out.println("input: ");
        outFor:
        while (true) {
            byte[] bytes = new byte[4];
            int len;
            while ((len = stream.read(bytes)) != -1) {
                //使用trim()去除末尾的回车符,因为读取控制台输入时不仅会读取输入内容还会读取用结束输入的回车符
                String s = new String(bytes, 0, len).trim();
                if ("e".equalsIgnoreCase(s)||"exit".equalsIgnoreCase(s)) {
                    System.out.println("END");
                    break outFor;
                }
                System.out.print(s.toUpperCase());
            }
        }
        //3.

    }

    @Test
    public static void systemOutTest() throws IOException {
        //题目：从键盘输入字符串，要求将读取到的整行字符串转换为大写字母输出，然后继续进行输入操作，直到输入e或exit才退出此程序。
        //原理：使用System.in实现。将其通过转换流转换为字符流，再用缓冲流包装，这
        // 样就可以使用缓冲流的readLine()方法读取一整行字符串了。

        //1.
        InputStreamReader isReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(isReader);

        //2.
        System.out.println("input: ");
        while (true) {
            String s = reader.readLine();
            if ("e".equalsIgnoreCase(s)||"exit".equalsIgnoreCase(s)) {
                System.out.println("END");
                break;
            }
            System.out.println(s.toUpperCase());
        }
        //3.
        reader.close();

    }

    public static void main(String[] args) throws IOException {
       systemInTest();
    }


}
