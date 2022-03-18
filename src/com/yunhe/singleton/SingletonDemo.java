package com.yunhe.singleton;

/**
 * @author ：lk
 */
//测试类
public class SingletonDemo {
    public static void main(String[] args) {
        System.out.println(Week.MON.getDay());
        System.out.println(Week2.MON.getDay());
    }
}



//枚举
enum Week{
    MON("星期一"),TUE("星期二"),WED("星期三");
    private String day;
    Week(String day){
        this.day = day;
    }
    public String getDay(){
        return day;
    }
}

//单例模式来模拟---枚举
class Week2{
    public static final Week2 MON = new Week2("星期一");
    public static final Week2 TUE = new Week2("星期二");
    public static final Week2 WED = new Week2("星期三");

    private String day;
    private Week2(String day){
        this.day = day;
    }
    public String getDay(){
        return day;
    }
}














//单例模式 ---枚举
enum Singleton_Enum{
    INSTANCE("单例");
    private String name;
    Singleton_Enum(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }


}



//单例模式----静态内部类---懒汉式---效率高--jvm
//-Singleton6的加载不影响Singleton6Demo的加载
class Singleton6{
    //1.私有构造
    private Singleton6(){
        if(Singleton6Demo.singleton6 != null){

        }
    };
    //2.创建静态内部类
    private static class Singleton6Demo{
        //1.
        private static Singleton6 singleton6 = new Singleton6();
    }
    //3.
    public static Singleton6 getInstance(){
        return Singleton6Demo.singleton6;
    }
}



//单例类---懒汉式--双重检查锁
class Singleton5{
    //1.私有构造
    private Singleton5(){};
    //2.volatile禁止JVM重排序
    private static volatile Singleton5 singleton5;
    //3.获取实例
    public static Singleton5 getInstance(){
        //需要添加判断
        if(singleton5 == null){
            synchronized (SingletonDemo.class){
                if(singleton5 == null){
                    singleton5 = new Singleton5();
                    //jvm中被分解为以下三步:
                    /*1. 分配内存空间
                      2.初始化对象
                      3.将singleton5指向分配好的内存空间
                      JVM有优化重排序的功名,1 , 3(singleton5已经不为空,半成品对象) , 2
                    * */
                }
            }
        }


        return singleton5;
    }
}

//单例类---懒汉式--线程安全 ---消耗性能
class Singleton4{
    //1.私有构造
    private Singleton4(){};
    //2.
    private static Singleton4 singleton4;
    //3.获取实例
    public static synchronized Singleton4 getInstance(){
        if(singleton4 == null){
            //线程1,cpu执行权---线程2
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            singleton4 = new Singleton4();
        }
        return singleton4;
    }
}

//单例类---懒汉式---多线程error
class Singleton3{
    //1.私有构造
    private Singleton3(){};
    //2.
    private static Singleton3 singleton3;
    //3.获取实例
    public static  Singleton3 getInstance(){
        if(singleton3 == null){
            //线程1,cpu执行权---线程2
            singleton3 = new Singleton3();
        }
        return singleton3;
    }
}



//单例类
/*
* 饿汉式 : 在类加载的时候就会初始化实例对象 ---当即加载---占用内存
* 懒汉式 : 在类加载的时候不会立即实例化对象,在我们第一次访问它 ---延迟加载
* 提供的获取实例的方法的时候就会初始化
* */
//1.
class Singleton{
    //1.私有构造
    private Singleton(){};
    //2.创建唯一实例--static---类变量--随着类的加载而加载--加载一次
    private static Singleton singleton = new Singleton();
    //类的加载过程: 加载 , 验证 , 准备 ,解析, 初始化
    //3.提供一个获取实例的方法
    public static Singleton getInstance(){
        return  singleton;
    }
}
//2.
class Singleton1{
    //1.私有构造
    private Singleton1(){};
    //2.创建唯一实例--static---类变量--随着类的加载而加载--加载一次
    private static Singleton1 singleton1;
    static {
        singleton1 = new Singleton1();
    }
    //类的加载过程: 加载 , 验证 , 准备 ,解析, 初始化
    //3.提供一个获取实例的方法
    public static Singleton1 getInstance(){
        return  singleton1;
    }
}
//3.
class Singleton2{
    //1.私有构造
    private Singleton2(){};
    //2.创建唯一实例--static---类变量--随着类的加载而加载--加载一次
    public static final Singleton2 singleton2 = new Singleton2();
}




















