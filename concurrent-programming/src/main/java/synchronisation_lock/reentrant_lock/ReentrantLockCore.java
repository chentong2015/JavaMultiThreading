package synchronisation_lock.reentrant_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

// ReentrantLock 可重入锁
// 1. 正在等待reentrantLock锁的线程可以选择放弃等待
// 2. 获取reentrantLock锁后执行完毕回调等待队列，可以重新再获取锁执行
public class ReentrantLockCore {

    private ReentrantLock reentrantLock = new ReentrantLock();

    private void testReentrantLock() throws InterruptedException {
        // 获取在AQS队列中等待的线程数目
        int numThreadsWaiting = reentrantLock.getQueueLength();

        // 尝试获取lock，设置timeout时间避免不必要尝试
        if (reentrantLock.tryLock(1000, TimeUnit.MILLISECONDS)) {
            System.out.println("Get Lock");
        }

        // 直接获取一个锁
        reentrantLock.lock();
        try {
            System.out.println("do something");
        } finally {
            // 使用try-finally语句块，确保一定会释放，且只释放一次
            reentrantLock.unlock();
        }

        // TODO. 判断当前线程在获取锁时是否被中断
        reentrantLock.lockInterruptibly();
    }
}
