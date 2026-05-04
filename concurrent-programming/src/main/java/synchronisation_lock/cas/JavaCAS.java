package synchronisation_lock.cas;

import java.util.concurrent.atomic.AtomicInteger;


public class JavaCAS {

    // 使用原子操作来实现synchronized锁的效果: 执行的时间更加的优化
    // 实现的原理：
    // 1. 使用volatile int value来存储值，多个线程是可见 !!
    // 2. 使用CAS(Compare and Swap比较交换)来实现
    //    while(true) {
    //       int oldValue = myNum.get();
    //       int newValue = oldValue + 1;
    //       if(myNum.compareAndSetInt(oldValue, newValue)) { // CAS的实现
    //          break;
    //       }
    //    }
    // 3. native方法对应到底层C++的实现/底层对于不同的平台/CPU有不同的实现
    //    Atomic::cmpxchg()
    //    mp = os::is_MP();  底层判断是否是多核CPU
    //    __asm__ __volatile__ (LOCK_IF_MP(%4) "cmxpchgq %1,(%3)" ...)
    //    mp "cmp $0, " $mp "; je 1f; lock; 1:" 汇编的实现
    private AtomicInteger myNum = new AtomicInteger();

    public void increase1() {
        myNum.incrementAndGet();
    }

    // 某方法试图更新一个共享变量时，CAS操作就会验证要赋值的变量是否保有上次已知值currentValue
    // 如果不是以知的旧值，说明另外一个线程正在试图更新。直到判断是旧值时，才做设置修改
    // TODO: CAS操作的问题
    // 1. 原子性问题: .compareAndSwap()方法本身不具备"原子性"，在操作中间可能被修改
    //    原子性实现: lock cmpxchg 相当于加了缓存行锁/总线锁(>64)，拿到锁之后再做比较并交换 / 硬件级别的加锁，只能针对一个缓存行加锁
    // 2. ABA问题: 如果仍然时以知的旧值，但是却不能保证它的值没有被修改过，可能发生"修改并还原"
    //    ABA方案：给值添加一个版本号version，每改变一次值，则修改一次版本号; 使用AtomicStampedReference类型
    // 3. 轻量级锁不一定比重量级锁性能高: 考虑高并发线程自旋造成的性能影响
    private int variableBeingSet;

    public void simulateNonBlockingSet(int newValue) {
        int currentValue;
        do {
            currentValue = variableBeingSet;
        } while (currentValue != compareAndSwap(currentValue, newValue));
    }

    private synchronized int compareAndSwap(int currentValue, int newValue) {
        if (variableBeingSet == currentValue) {
            variableBeingSet = newValue;
            return currentValue;
        }
        return variableBeingSet;
    }
}
