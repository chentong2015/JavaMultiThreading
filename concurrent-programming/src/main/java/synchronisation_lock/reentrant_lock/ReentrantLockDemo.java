package synchronisation_lock.reentrant_lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    private static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        DemoClass demoClass = new DemoClass(0);
        for (int index = 0; index < 100; index++) {
            new Thread(demoClass::increaseValue).start();
        }

        Thread.sleep(3000);
        System.out.println(demoClass.value);
    }

    static class DemoClass {
        // 多线程共享的数据资源
        private int value;

        public DemoClass(int value) {
            this.value = value;
        }

        public void increaseValue() {
            reentrantLock.lock();
            try {
                // 增加计算量，将冲突概率放大
                for (int i = 0; i < 1000; i++) {
                    value++;
                }
            } finally {
                reentrantLock.unlock();
            }
        }
    }
}