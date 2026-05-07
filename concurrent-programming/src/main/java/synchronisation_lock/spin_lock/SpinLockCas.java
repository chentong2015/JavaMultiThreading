package synchronisation_lock.spin_lock;

import java.util.concurrent.atomic.AtomicReference;

// TODO. CAS锁 = 原子变量 + 自旋 + compareAndSet
// 将一个预期值和内存值进行比较，如果相等则更换新的值，反之自旋
//
// CAS ABA问题:
// 即使符合修改条件也无法保证之前没有被修改过，可能发生"修改并还原"
public class SpinLockCas {

    // 被原子更新操作的对象引用
    private AtomicReference<Thread> threadOwnLock = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!threadOwnLock.compareAndSet(null, current)) {
            // TODO. 当前线程放弃其当前对处理器的使用，减少对CPU的占用
            Thread.yield();
            Thread.onSpinWait();
        }
        System.out.println("Get lock and set by thread " + current.getName());
    }

    // 只有持有锁的线程才能释放, 其他线程调用这个unlock方法没有意义
    public void unlock() {
        Thread current = Thread.currentThread();
        if (!threadOwnLock.compareAndSet(current, null)) {
            throw new IllegalMonitorStateException("Not lock owner");
        }
        System.out.println("Release lock for thread " + current.getName());
    }
}
