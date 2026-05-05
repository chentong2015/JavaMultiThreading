package synchronisation_lock.cas_optimistic;

import java.util.concurrent.atomic.AtomicInteger;

// TODO. CAS (乐观锁，自旋锁，轻量级锁) => 不一定比重量级锁性能高
// 1. 自旋操作，消耗CPU且占用资源: 对性能有影响
// 2. 在自旋时不断判断是否符合操作要求: 比较修改前的旧值，对比旧的版本号
// 3. 判断是否符合操作要求: 保证原子性
//
// CAS ABA问题: 即使符合修改条件也无法保证之前没有被修改过，可能发生"修改并还原"
public class CasLock {

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
