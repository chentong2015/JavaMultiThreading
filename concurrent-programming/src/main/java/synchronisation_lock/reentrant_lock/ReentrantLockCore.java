package synchronisation_lock.reentrant_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

// ReentrantLock
// 1. 正在等待reentrantLock锁的线程可以选择放弃等待
// 2. 公平锁: 作为公平锁使用，解决线程饥饿问题，释放锁时，任何一个等待锁的线程都有机会获得锁
// 3. 可重入锁: 获得到同步锁之后，可以再继续执行需要该同步锁的代码块
public class ReentrantLockCore {

    // 设置公平锁会耗费额外的processing去管理和确保公平，对性能影响较大，减低吞吐量
    private ReentrantLock reentrantLock = new ReentrantLock(true);

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
