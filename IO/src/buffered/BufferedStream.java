package buffered;

import org.junit.Test;

import java.io.*;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-26 21:43
 */
public class BufferedStream {
    @Test
    public void fileCopy(){
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        BufferedInputStream bufferedInputStream=null;
        BufferedOutputStream bufferedOutputStream=null;
        try {
            //1.创建输入输出文件
            File source = new File("阿里Java开发手册.pdf");
            File copy = new File("阿里Java开发手册Copy3.pdf");
            //2.获取输入输出流以及对应的缓冲流
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(copy);
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            //3.读写文件
            //3.1先读文件
            byte[] bytes = new byte[10];
            int len;
            while ((len=bufferedInputStream.read(bytes))!=-1){
                //3.2写文件
                bufferedOutputStream.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.直接关闭外层缓冲流即可，关闭时会自动关闭内层的文件节点流
            try {
                if (bufferedInputStream!=null) {
                    bufferedInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bufferedOutputStream!=null) {
                    bufferedOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
