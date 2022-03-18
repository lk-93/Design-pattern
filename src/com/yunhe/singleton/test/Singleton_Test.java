package com.yunhe.singleton.test;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * @author ：lk
 * 反射与序列化反序列化对单例的破坏
 */
public class Singleton_Test {
    public static void main(String[] args) {

        //测试反射
       /* for (int i = 0; i < 50; i++) {
            new Thread(()->{
                extracted(Singleton9.class);
            }).start();
        }*/
       // extracted(Singleton10.class);

        //测试序列化与反序列化
        //extracted2(Singleton11.getInstance());


    }

    //测试序列化与反序列化
    private static <T> void extracted2(T t) {
        //1.创建对象输出流
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singleton.txt"))){
            //2.创建对象
           // Singleton_1 instance = Singleton_1.getInstance();
            //3.将对象写入文件
            oos.writeObject(t);
        }catch (Exception e){
            e.printStackTrace();
        }

        //1.创建对象的输入流
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singleton.txt"))) {
            //2.读取对象
            T t1;
            t1 = (T)ois.readObject();
            //Singleton_1 instance1 = Singleton_1.getInstance();
            System.out.println(t);
            System.out.println(t1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //1.测试反射
    private static <T> void extracted(Class<T> aClass) {
        //1.反射
        //1.获得字节吗对象
        //2.获得私有的无参构造
        Constructor<T> declaredConstructor = null;
        T t = null;
        try {
            declaredConstructor = aClass.getDeclaredConstructor();
            //3.暴力访问,取消java访问检查
            declaredConstructor.setAccessible(true);
            t = (T) declaredConstructor.newInstance();
            //Singleton_1 singleton_2 = (Singleton_1) declaredConstructor.newInstance();
            //System.out.println(singleton_1);
            //Singleton_1 instance = Singleton_1.getInstance();
            System.out.println(t);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


class Singleton11 implements Serializable {
    //1.私有构造
    private Singleton11(){
        if(singleton11 != null){
            throw new RuntimeException("我是单例!!!");
        }
        System.out.println("构造走了....");
    };
    //2.创建唯一实例--static---类变量--随着类的加载而加载--加载一次
    private static Singleton11 singleton11 = new Singleton11();
    //类的加载过程: 加载 , 验证 , 准备 ,解析, 初始化
    //3.提供一个获取实例的方法
    public static Singleton11 getInstance(){
        return  singleton11;
    }

    private Object readResolve(){
        return singleton11;
    }
}

class Singleton9{
    //1.私有构造
    private Singleton9(){
        if(Singleton9Demo.singleton9 != null){
                throw new RuntimeException("单例呀.....");
        }
        System.out.println("构造走了...");
    };
    //2.创建静态内部类
    private static class Singleton9Demo{
        //1.
        private static Singleton9 singleton9 = new Singleton9();
    }
    //3.
    public static Singleton9 getInstance(){
        return Singleton9Demo.singleton9;
    }
}


class Singleton10{
    //1.私有构造
    private Singleton10(){
        if(singleton10 != null){

            throw new RuntimeException("单例.....");
        }
        System.out.println("构造走了....");
    };
    //2.volatile禁止JVM重排序
    private static volatile Singleton10 singleton10;
    //3.获取实例
    public static Singleton10 getInstance(){
        //需要添加判断
        if(singleton10 == null){
            synchronized (Singleton_Test.class){
                if(singleton10 == null){
                    singleton10 = new Singleton10();
                    //jvm中被分解为以下三步:
                    /*1. 分配内存空间
                      2.初始化对象
                      3.将singleton10指向分配好的内存空间
                      JVM有优化重排序的功名,1 , 3(singleton10已经不为空,半成品对象) , 2
                    * */
                }
            }
        }


        return singleton10;
    }
}

enum Singleton12{
    INSTANCE;
}