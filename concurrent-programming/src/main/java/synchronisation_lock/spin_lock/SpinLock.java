package synchronisation_lock.spin_lock;

import java.util.concurrent.atomic.AtomicInteger;

// TODO. Spin自旋锁，乐观锁，轻量级锁 => 不一定比重量级锁性能高
// 1. 自旋操作，消耗CPU且占用资源: 对性能有影响
// 2. 在自旋时不断判断是否符合操作要求: 比较修改前的旧值，对比旧的版本号
// 3. 判断是否符合操作要求: 保证原子性
public class SpinLock {

    // TODO. 使用AtomicInteger来确保compareAndSet()操作是原子操作
    private AtomicInteger myNum = new AtomicInteger();

    // 线程安全, 等效于myNum.getAndIncrement();
    public void increase() {
        while (true) {
            int oldValue = myNum.get();
            int newValue = oldValue + 1;
            if (myNum.compareAndSet(oldValue, newValue)) {
               break;
            }
        }
    }

    public AtomicInteger getMyNum() {
        return myNum;
    }
}
