package synchronisation_lock.cas_optimistic;

import java.util.concurrent.atomic.AtomicReference;

// TODO. CAS锁 = 原子变量 + 自旋 + compareAndSet
// 将一个预期值和内存值进行比较，如果相等则更换新的值，反之自旋
public class CasLockImpl {

    // 被原子更新操作的对象引用
    private AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!owner.compareAndSet(null, current)) {
            // TODO. 当前线程放弃其当前对处理器的使用，减少对CPU的占用
            Thread.yield();
            // Thread.onSpinWait();
        }
        System.out.println("Get lock and set by thread " + current.getName());
    }

    // 只有持有锁的线程才能释放
    public void unlock() {
        Thread current = Thread.currentThread();
        if (!owner.compareAndSet(current, null)) {
            throw new IllegalMonitorStateException("Not lock owner");
        }
        System.out.println("Release lock for thread " + current.getName());
    }
}
