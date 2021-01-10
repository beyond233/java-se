package file;

import com.sun.xml.internal.ws.developer.UsesJAXBContext;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * <p>项目文档: File类的使用</p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-24 15:02
 */
public class FileDemo {
    @Test
    public void test(){
        File file = new File("C:\\Users\\BEYOND\\IdeaProjects\\JavaSE8\\IO\\hello.txt");
        File filePath = new File("C:\\Users\\BEYOND\\IdeaProjects\\JavaSE8");
        System.out.println("获取绝对路径: "+file.getAbsolutePath());
        System.out.println("获取名称: "+file.getName());
        System.out.println("获取父级目录文件: "+file.getParent());
        System.out.println("文件长度: "+file.length());
        System.out.println("获取绝对路径名命名的文件对象: "+file.getAbsoluteFile());
        System.out.println("获取上一次修改文件的时间: "+new Date(file.lastModified()));
        System.out.println("获取指定目录下的所有文件或文件目录的名称数组："+ Arrays.toString(filePath.list()));
        System.out.println("获取指定目录下的所有文件或文件目录的File数组："+ Arrays.toString(filePath.listFiles()));

    }

    @Test
    public void test1(){
        File file = new File("hello.txt");
        File newFile = new File("xxx.txt");

        file.renameTo(newFile);

    }

    @Test
    public void test2() throws IOException {
        File file = new File("spring.txt");
        File file1= new File("springboot.txt");
        file.createNewFile();
        file1.createNewFile();
        File file2 = new File("C:\\Users\\BEYOND\\IdeaProjects\\JavaSE84");
        System.out.println(file2.isDirectory());
    }

    @Test
    public void test3(){
        //构造一个文件目录
        File file = new File("C:\\FileTest");
        //存放所有文件
        List<File> files = new ArrayList<>();
        //存放所有目录
        List<File> fileDirs = new ArrayList<>();
        //每个目录对应其中包含的所有东西
        HashMap<File, List<File>> map = new HashMap<>();
        List<File> filesList = new ArrayList<>();
        map.put(file, filesList);
        //判断该目录下的所有文件或目录
        judge(file,files,fileDirs,map);

        System.out.println("**********************************目录有：");
        for (File fileDir : fileDirs) {
            System.out.println(fileDir);
        }
        System.out.println("**********************************文件有："+files);
        for (File file1 : files) {
            System.out.println(file);
        }
        System.out.println("*******************************Map结构：");
        for (File file1 : map.keySet()) {
            for (File file2 : map.get(file1)) {
                System.out.println(file2);
            }
        }
    }
    public void judge(File file, List<File> files,List<File> fileDirs,Map<File,List<File>> map){
        //如果是目录，则递归遍历
        if (file.isDirectory()) {
            List<File> fileArrayList = new ArrayList<>();
            fileDirs.add(file);
            map.put(file, fileArrayList);
            System.out.println(file.getName()+"是目录,其下有：");
            File[] filesArray = file.listFiles();
            assert filesArray != null;
            for (File file1 : filesArray) {
                judge(file1,files,fileDirs,map);
            }
        }else{   //如果是文件
            files.add(file);

            System.out.println(file.getName()+"是文件");
        }
    }

    @Test
    public void test4(){
        //创建文件1
        File file = new File("c:\\FileTest\\dir1\\hello.txt");
        //创建一个和文件1在同一目录的文件2
        File file1 = new File(file.getParent(), "b.txt");
    }

}
