package system;

import org.junit.Test;

import java.io.*;

/**
 * <p>项目文档: 打印流</p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-27 16:56
 */
public class Print {
        @Test
        public void test() throws IOException {
            //1.
            FileOutputStream stream = new FileOutputStream(new File("ASCII码字符.txt"));
            //创建打印流，设置为自动刷新模式（写如换行符或者字节'\n'都会自动刷新缓冲区）
            PrintStream ps = new PrintStream(stream, true);
            //把标准输出流（从控制台输出）改成输出到文件
            System.setOut(ps);

            //2.
            for (int i = 0; i < 255; i++) {
                System.out.print((char) i);
                //每50个换行
                if (i%50==0) {
                    System.out.println();
                }
            }
            ps.close();
            stream.close();

        }

}
