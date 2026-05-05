package synchronisation;

public class SharedResourceTest {

    // TODO. 多线程共享一个对象引用
    // 1. 所有Thread共享进程对象存储的heap内存空间和方法区
    // 2. 所有Thread各自拥有自己的Stack栈空间，局部变量不被共享
    // 3. Threads will create their own copy of object
    public static void main(String[] args) throws InterruptedException {
        SharedResource sharedInstance = new SharedResource();
        Thread thread1 = new Thread(sharedInstance::print, "BaseThread 1");
        Thread thread2 = new Thread(sharedInstance::print, "BaseThread 2");
        thread1.start();
        thread2.start();
        Thread.sleep(3000);

        Thread thread3 = new Thread(sharedInstance::increaseCount, "BaseThread 3");
        Thread thread4 = new Thread(sharedInstance::increaseCount, "BaseThread 4");
        thread3.start();
        thread4.start();
        Thread.sleep(2000);
        System.out.println(sharedInstance.getCount());
    }
}