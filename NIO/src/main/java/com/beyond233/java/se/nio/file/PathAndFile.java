package com.beyond233.java.se.nio.file;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description:
 *
 * @author beyond233
 * @since 2021/5/2 19:41
 */
public class PathAndFile {

    @Test
    public void test() throws IOException {
        Path source = Paths.get("world.txt");
        Path target = Paths.get("world-copy.txt");
        Files.copy(source, target);
    }

    public static void main(String[] args) throws IOException {
//        Path source = Paths.get("world.txt");
//        Path target = Paths.get("world-copy.txt");
//        Files.copy(source, target);

        // 遍历文件目录下每一个文件、目录
        final AtomicInteger dirCount = new AtomicInteger();
        final AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\学习笔记"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("=================================================> " + dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file.getFileName());
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return super.visitFileFailed(file, exc);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return super.postVisitDirectory(dir, exc);
            }
        });
        System.out.println("dirCount = " + dirCount.get());
        System.out.println("fileCount = " + fileCount.get());

        // 统计每一个文件目录下有多少个文件
        System.out.println("----------------------------------------------------------------------------------------");
        HashMap<String, Integer> map = new HashMap<>(dirCount.get());
        Files.walk(Paths.get("D:\\学习笔记")).forEach(path -> {
            if (Files.isDirectory(path)) {
                map.put(path.toString(), 0);
            } else if (Files.isRegularFile(path)) {
                String dir = path.toString().replace("\\" + path.getFileName().toString(), "");
                map.put(dir, map.containsKey(dir) ? map.get(dir) + 1 : 0);
            }
        });

        int fileSum = 0;
        for (String s : map.keySet()) {
            System.out.println(s + " = " + map.get(s));
            fileSum += map.get(s);
        }
        System.out.println("dirCount = " + map.keySet().size());
        System.out.println("fileCount = " + fileSum);
    }
}
