package synchronisation.synchronize;

// TODO. 基于Instance实例对象来实现同步
// For instance method, it locks the monitor associated with the instance for which it was invoked
public class SharedResourceSyn {

    private int index;

    // TODO. Synchronized Method 方法的同步(除构造器方法之外)
    // 同一时刻只有一个线程(可能)在执行该方法, 取消数据共享性, 别的线程会等待直到结束
    public synchronized void printSynMethod() {
        String name = Thread.currentThread().getName();
        for (index = 10; index > 0; index--) {
            System.out.println(name + ": " + index);
        }
    }

    // TODO. 仅关联特定实例对象的monitor进行加锁 !!
    //  同一对象的printSynMethod()和printSynMethod2()并发执行会造成共享数据的混乱
    //  不同对象的printSynMethod()和printSynMethod2()并发执行没有问题
    public void printSynMethod2() {
        String name = Thread.currentThread().getName();
        for (index = 10; index > 0; index--) {
            System.out.println(name + ": " + index);
        }
    }

    // TODO. Synchronized Statement/Block 语句块的同步
    // 如果lock action执行成功，则执行同步的语句块，结束后自动在同一个monitor上执行unlock action
    public void printSynBlock() {
        String name = Thread.currentThread().getName();

        // 同一时刻，只有一个Thread会拿到当前"共享对象"的锁，执行语句块后释放锁
        synchronized (this) {
            for (index = 10; index > 0; index--) {
                System.out.println(name + ": " + index);
            }
        }
    }
}
