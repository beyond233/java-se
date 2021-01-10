package file;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;

/**
 * <p>项目文档: 随机存取文件流，即可作为输入流，也可作为输出流</p>
 *
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-28 11:34
 */
public class RandomAccessFileDemo {
    /**
     * 作为输入流使用
     * */
    @Test
    public void test() throws IOException {
        //1.
        RandomAccessFile r = new RandomAccessFile("日志.txt", "r");
        //2.
        String s;
        int len;
        while ((s=r.readLine()) != null) {
            System.out.println(s);
//            System.out.print((char)len);
        }
        //3.
        r.close();
    }

    /**
     * ****输入流和输出流结合使用案例：实现向指定位置插入数据且不覆盖原有位置数据的效果****
     * RandomAccessFile在往文件中某个位置写数据时，都是以覆盖的方式写入：
     * 1.如果文件中该位置已经存在数据那就覆盖原有位置的数据，其他位置的数据不变;
     * 2.如果该位置无数据那就相当于追加的效果
     * 如果想要实现向指定位置插入数据且不覆盖原有位置数据的效果，那就需要将插入位置及其后面的数据都保存下来
     * 插入数据后再写入保存的数据，这样可以实现但效率低，通常我们都是追加，即先获取原文件长度，然后使用seek()方法
     * 将指针移动到文件末尾。也就是索引为原文件长度的位置（索引从0开始）。
     * */
    @Test
    public void insert() throws IOException {
        //1.
        File file = new File("日志.txt");
        RandomAccessFile r = new RandomAccessFile(file,"rw");

        //2.
        //例如插入数据到文件开头,此时应移动指针到文件开头,即0处
        r.seek(0);
        //读取插入位置之后的数据并保存到StringBuilder中
        //StringBuilder长度应该为文件的长度
//        StringBuilder s = new StringBuilder((int) file.length());
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[10];
        int len;
        while ((len = r.read(bytes)) != -1) {
            //使用StringBuilder保存数据
//            s.append(new String(bytes, 0, len));
            byteOutStream.write(bytes, 0, len);
        }
        //保存完插入位置后面的数据后开始插入数据，由于刚刚读取时指针已经移动到文件末尾了
        r.seek(0);
        r.write("嘻嘻嘻".getBytes());
        //插入数据完成后再写入之前保存的数据
        r.write(byteOutStream.toByteArray());
//        r.write(s.toString().getBytes());

        //3.
        r.close();

    }
}
