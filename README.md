# Design-pattern
#### 设计模式

------

##### 设计模式简介

设计模式是程序员面对软件工程设计问题所总结出来的有用经验,模式不是代码,而是某类问题的解决方案,设计模式代表了最佳实践.提高了软件的维护性,通用性和扩展,并降低软件的复杂度.

设计模式分为3类共23种

1. 创建型模式 : 单例模式 , 工厂模式 , 抽象工厂模式 , 原型模式 , 建造者模式
2. 结构型模式 : 适配器模式 , 桥接模式 , 装饰模式 , 组合模式 , 外观模式 , 享元模式 , 代理模式
3. 行为型模式 : 模板方法模式 , 命令模式 , 访问者模式 , 迭代器模式 , 观察者模式 , 中介者模式 , 备忘录模式 , 解释器模式(Interpreter模式) , 状态模式 , 策略模式 , 责任链模式

##### 单例设计模式

在整个软件系统中,对某个类只能存在一个对象实例,并且该类只提供一个取得其对象实例的方法

```java
//1.恶汉式
class SingletonDemo{
 //1.私有构造
 private SingletonDemo(){};
 //2.定义对象
 private static SingletonDemo singletonDemo = new SingletonDemo();
 //3.对外提供get方法
 public static SingletonDemo getInstance(){
     return singletonDemo;
 }
}
```

```java
//2.自定义恶汉式
class SingletonDemo1{
	//1.私有构造
	private SingletonDemo1(){};
	//2.定义对象--被final修饰的变量不可被更改
	public static final SingletonDemo1 singletonDemo1 = new SingletonDemo1();

}
```

```java
//懒汉式
//多线程情况下有问题
class SingletonDemo1{
 //1.私有构造
 private SingletonDemo1(){};
 //2.定义对象
 private static SingletonDemo1 singletonDemo1;
 //3.对外提供get方法
 public  static  SingletonDemo1 getInstance(){
     if(singletonDemo1 == null)
         singletonDemo1 = new SingletonDemo1();
         return singletonDemo1;
     }
}
```

```java
//懒汉式--加锁
class SingletonDemo1{
//1.私有构造
private SingletonDemo1(){};
//2.定义对象
private static SingletonDemo1 singletonDemo1;
//3.对外提供get方法
public static synchronized SingletonDemo1 getInstance(){
   if(singletonDemo1 == null)
       singletonDemo1 = new SingletonDemo1();
   return singletonDemo1;
}
/**这样虽然解决了问题，但是因为用到了synchronized，会导致很大的性能开销，并且加锁其实
只需要在第一次初始化的时候用到，之后的调用都没必要再进行加锁。*/

}
```

```java
//3.懒汉式--双重检查锁
class SingletonDemo1{
//1.私有构造
private SingletonDemo1(){};
//2.定义对象
/**  添加volatile关键字,使用了volatile关键字后，重排序被禁止，
所有的写（write）操作都将发生在读（read）操作之前。 */
private volatile static SingletonDemo1 singletonDemo1;
//3.对外提供get方法
public static  SingletonDemo1 getInstance(){

if(singletonDemo1 == null){
   synchronized(SingletonDemo1.class){
       if(singletonDemo1 == null){
           singletonDemo1 = new SingletonDemo1();
       }
   }

}
   return singletonDemo1;
}
}

/* 实例化对象的那行代码，实际上在JVM中被分解成以下三个步骤：
		分配内存空间
		初始化对象
		将对象指向刚分配的内存空间
由于JVM可能存在重排序，上述的二三步骤没有依赖的关系，可能会出现先执行第三步，
后执行第二步的情况。也就是说可能会出现instance变量还没初始化完成，其他线程就已
经判断了该变量值不为null，结果返回了一个没有初始化完成的半成品的情况。
而加上volatile关键字修饰后，可以保证instance变量的操作不会被JVM所重排序，
每个线程都是按照上述一二三的步骤顺序的执行，这样就不会出现问题。*/
```

```java
//4.使用静态内部类
class Singleton{
    //1.私有构造
    private Singleton(){};
    //2.定义内部类
    private static class SingletonDemo{
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonDemo.INSTANCE;
    }
}
/** 该方法采用了类装载的机制来保证初始化实例时只有一个线程;
	静态内部类方式在Singleton类被装载时,不会立即实例化,调用getInstance方法,才会装载该静态内部类,从而完成Singleton的实例化;
	类的静态属性只会在第一次加载类时候初始化,jvm保证了线程的安全,利用静态内部类特点实现延迟加载,效率高
*/
```

```java
//5.使用枚举
enum Singleton{
    INSTANCE
}
//借助枚举来实现单利模式,不仅能避免多线程同步问题,而且还能防止反序列化重新创建新的对象
```
