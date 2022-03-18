package com.yunhe.demo;

import java.io.*;
import java.lang.reflect.Constructor;

/**
 * @author ：lk
 */
/*
* 1.反射对单例模式的破坏
* 原因: 可以拿到构造.解决方案: 饿汉式在构造中添加判断--可以,懒汉式---不可以
* 2.序列化以及反序列化对单例的破坏
* 原因: 判断是否存在readResolve方法,如果存在就执行该方法,如果不存在----创建一个对象--反射
* 3.以上两种方案为什么对枚举不起作用
* 原因: 反射: 方法里边有判断--Enum修饰的不能创建对象
*      序列化反序列化: 枚举在进行序列化的时候会将枚举项(对象)的name序列化,反序列化的时候--values(获得所有的枚举项)
* 根据name--枚举项一一对应----map<name,枚举项>---valuesOf(get(key))
* */
//测试
public class SingletonTest {
    public static void main(String[] args) {
       //1.反射对单例模式的破坏

      /*  System.out.println(Singleton_2.getInstance());
        System.out.println("------------------");
        refSingleton(Singleton_2.class);*/

        //2.序列化以及反序列化对单例的破坏
        //serSingleton(Singleton_1.getInstance());
        //3.以上两种方案为什么对枚举不起作用
        serSingleton(Singleton_3.INSTANCE);

    }

    //序列化反序列化的方法
    private static <T> void serSingleton(T t) {
        //2.1 将对象进行输出
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singleton.txt"))){
            //2.获得对象
            //Singleton_1 instance = Singleton_1.getInstance();
            System.out.println(t);
            //3.输出
            oos.writeObject(t);
        }catch (Exception e){
            e.printStackTrace();
        }

        //2.2 对象输入流
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singleton.txt"))){
            //2.
            T t1 = (T) ois.readObject();
            System.out.println(t1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //反射
    private static <T> void refSingleton(Class<T> tClass) {
        //1.
        //Class<Singleton_1> singleton_1Class = Singleton_1.class;
        //2.
        try {
            Constructor<T> declaredConstructor = tClass.getDeclaredConstructor();
            //3.
            declaredConstructor.setAccessible(true);
            //4.
           T t = declaredConstructor.newInstance();
            System.out.println(t);
           /* T t2 = declaredConstructor.newInstance();
            System.out.println(t2);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//1.单例类---饿汉式--静态内部类
class Singleton_1 implements Serializable {
    private Singleton_1(){
        if(singleton_1 != null){
            throw new RuntimeException("不允许反射访问...");
        }
    };
    private static Singleton_1 singleton_1 = new Singleton_1();
    public static Singleton_1 getInstance(){
        return singleton_1;
    }
    //1.
    private Object readResolve(){
        return singleton_1;
    }
}

//2.懒汉式
class Singleton_2{
    private static boolean flag = false;
    private Singleton_2(){
        if(flag){
            throw new RuntimeException("不允许反射访问");
        }
        flag = true;
    };
    private static volatile Singleton_2 singleton_2;
    public static Singleton_2 getInstance(){
        if(singleton_2 == null){
            synchronized (Singleton_2.class){
                if(singleton_2 == null){
                    singleton_2 = new Singleton_2();
                }
            }
        }
        return singleton_2;
    }
}
//3.创建枚举
enum Singleton_3{
    INSTANCE;
}







