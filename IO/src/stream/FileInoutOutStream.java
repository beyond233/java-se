package stream;

import org.junit.Test;

import java.io.*;
import java.util.stream.Stream;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-26 18:18
 */
public class FileInoutOutStream {
    @Test
    public void test() throws IOException {
        //1.
        File file = new File("日志.txt");
        //2.
        FileInputStream inputStream = new FileInputStream(file);
        //3.
        byte[] bytes = new byte[10];
        int len;
        while ((len = inputStream.read(bytes)) != -1) {
            String s = new String(bytes, 0, len);
            System.out.print(s);
        }
        //4.   name小徐
        inputStream.close();
    }

    @Test
    public void fileCopy(){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //1.创建输入输出文件
            File source = new File("阿里Java开发手册.pdf");
            File copy = new File("阿里Java开发手册Copy.pdf");
            //2.获取输入输出流
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(copy);
            //3.读写文件
            //3.1先读文件
            byte[] bytes = new byte[10];
            int len;
            while ((len=inputStream.read(bytes))!=-1){
                //3.2写文件
                outputStream.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流
            try {
                if (inputStream!=null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outputStream!=null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void fileCopyUtil(String srcPath,String destPath){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            //1.创建输入输出文件
            File source = new File(srcPath);
            File copy = new File(destPath);
            //2.获取输入输出流
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(copy);
            //3.读写文件
            //3.1先读文件
            byte[] bytes = new byte[1024];
            int len;
            while ((len=inputStream.read(bytes))!=-1){
                //3.2写文件
                outputStream.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭流
            try {
                if (inputStream!=null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (outputStream!=null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void utilTest(){
        fileCopyUtil("阿里Java开发手册Copy.pdf","阿里Java开发手册Copy2.pdf");
    }

}
