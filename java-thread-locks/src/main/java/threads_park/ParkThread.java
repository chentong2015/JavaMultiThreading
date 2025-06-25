package threads_park;

import java.util.concurrent.locks.LockSupport;

public class ParkThread {

    // TODO. 在持有锁情况下调用Park可能导致死锁!
    private final Object lock = new Object();

    public void testParkLock() {
        synchronized(lock) {
            // 持有锁
            LockSupport.park(); // 挂起，但不会释放 `lock`
            // 被 unpark 之后，依然持有 `lock`
        }
    }

    // 模拟线程获取锁和释放做的过程
    // Thread-0 got lock
    // Thread-0 released lock
    // Thread-3 got lock
    // Thread-3 released lock
    // Thread-2 got lock
    // Thread-2 released lock
    // Thread-1 got lock
    // Thread-1 released lock
    public static void main(String[] args) {
       for (int i = 0; i < 5; i++) {
           new CustomThread().start();
       }
    }
}
