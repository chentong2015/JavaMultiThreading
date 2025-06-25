package threads_park;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

// TODO. 使用Park + CAS自旋自定义一把锁
//  实现对线程加锁的效果和并发的控制
public class CustomParkLock {

    private final AtomicInteger lockStatus = new AtomicInteger(0);
    private final Queue<Thread> threadQueue = new ArrayDeque<>();

    // Park 挂起当前线程，添加到等待队列中
    public void lock() {
        while (!lockStatus.compareAndSet(0, 1)) {
            Thread thread = Thread.currentThread();
            threadQueue.add(thread);
            LockSupport.park(thread);
        }
    }

    // Unpark 从队列中取出一个线程并唤醒, 恢复初始值避免死锁
    public void unlock() {
        lockStatus.set(0);
        Thread thread = threadQueue.poll();
        LockSupport.unpark(thread);
    }
}
