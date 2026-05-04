package synchronisation.synchronize;

public class JavaSynchronized {

    // 共享数据区域
    private int num = 0;

    // 定义私有锁来实现同步, final可以放置被修改后导致的不同步
    private final Object lock = new Object();

    // synchronized 锁的是当前调用的这个方法的对象
    public synchronized void increase() {
        // synchronized (this) {  等效于给block加锁
        //     num++;
        // }
        // synchronized (lock) {
        //     num++;
        // }
        num++;
    }

    // 使用synchronized锁定Class类型
    private static int number = 0;

    public static synchronized void increase2() {
        // synchronized (DemoLockClass.class) {
        //     number++;
        // }
        number++;
    }
}
