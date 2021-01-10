package reader;

import org.junit.Test;

import java.io.*;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-26 16:05
 */
public class FileReaderWriter {
    @Test
    public void fileReaderTest() throws IOException {
        FileReader reader = null;
        try {
            //1.构造文件对象
            File file = new File("hello.txt");
            //2.获取输入流
            reader = new FileReader(file);
            //3.读取文件
            //3.1读取方式一: read()方法返回读入的一个字符的ascii码值，若读到文件末尾则返回-1
//            int read = reader.read();
//            while (read!=-1){
//                System.out.print((char) read);
//                read = reader.read();
//            }
//            //3.2读取方式一的代码简化版
//            int data;
//            while ((data=reader.read())!=-1){
//                System.out.print((char)data);
//            }
            //3.3读取方式二:
            /*
            *read(Char[] chars)方法每次从文件中读取和chars数组长度相等个数的字符，然后
            *每次将读取到的多个字符按照读取顺序存放到chars数组中，并返回每次实际读取到的字符个数
            **/
            char[] chars = new char[5];
            //len用来表示每次从文件中实际读取到的字符的个数
            int len;
            while ((len=reader.read(chars))!=-1){
                //错误写法1：控制台打印 ：hello JavaIOava
                /*
                *错误原因：每次读取5个字符。第一次读取到'hello'，第二次读取到' Java'(空格也算字符),
                * 最后一次只读取到'IO'，但是却打印了数组中全部字符
                **/
//                for (int i = 0; i < chars.length; i++) {
//                    System.out.print(chars[i]);
//                }
                //错误写法2：控制台打印 ：hello JavaIOava,原因同错误方式1
//                String string = new String(chars);
//                System.out.print(string);
                //正确方式1
//                for (int i = 0; i < len; i++) {
//                    System.out.print(chars[i]);
//                }
                //正确方式2
                String s = new String(chars, 0, len);
                System.out.print(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.若创建了流则最后一定要关闭流
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void fileWriterTest() throws IOException {
        //1.创建文件对象
        File file = new File("写文件.txt");
        //2.获取输出流: append表示若该文件存在时，是向文件中追加还是删除原有全部数据再重新写入,true表示追加
        FileWriter writer = new FileWriter(file,true);
        //3.写文件
        writer.write("我第一次正在写数据到文件中。");
        //4.关闭流
        writer.close();

    }

    @Test
    public void fileCopyTest() {
        FileReader reader = null;
        FileWriter writer = null;
        try {
            //1.创建输入输出文件
            File source = new File("hello.txt");
            File copy = new File("helloCopy.txt");
            //2.获取输入输出流
            reader = new FileReader(source);
            writer = new FileWriter(copy);
            //3.读写文件
            //3.1先读文件
            char[] chars = new char[10];
            int len;
            while ((len=reader.read(chars))!=-1){
                //3.2写文件
                writer.write(chars,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流
            try {
                if (reader!=null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (writer!=null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
