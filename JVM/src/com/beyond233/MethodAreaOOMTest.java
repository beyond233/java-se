package com.beyond233;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * description: 设置JVM： -XX:MetaspaceSize=10m  -XX:MaxMetaspaceSize=10m
 * 其中CompressedClassSpaceSize为2m
 *
 * @author beyond233
 * @since 2021/8/23 22:20
 */
public class MethodAreaOOMTest extends ClassLoader {
    public static void main(String[] args) {
        int j = 0;
        try {
            MethodAreaOOMTest test = new MethodAreaOOMTest();
            for (int i = 0; i < 10000; i++) {
                // 创建ClassWriter对象，用于生成类的二进制字节码
                ClassWriter writer = new ClassWriter(0);
                // 指明版本号，修饰符，类名，包名，父类，接口
                writer.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Class" + i, null, "java/lang/Object", null);
                // 返回byte[]
                byte[] code = writer.toByteArray();
                // 类加载
                test.defineClass("Class" + i, code, 0, code.length);
                j++;
            }
        } finally {
            System.out.println(j);
        }
    }
}
