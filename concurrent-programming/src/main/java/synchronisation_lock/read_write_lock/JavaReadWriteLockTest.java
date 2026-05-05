package synchronisation_lock.read_write_lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JavaReadWriteLockTest {

    private int data = 0;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void read() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " Start Read ...");
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + " Read data ：" + data);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void write(int value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " Start write ...");
            Thread.sleep(3000);
            data = value;
            System.out.println(Thread.currentThread().getName() + " Write data ：" + value);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JavaReadWriteLockTest readWriteLockTest = new JavaReadWriteLockTest();
        for (int i = 0; i < 3; i++) {
            new Thread(readWriteLockTest::read, "ReadThread-" + i).start();
        }

        new Thread(() -> readWriteLockTest.write(100), "WriteThread-1").start();

        Thread.sleep(1000);
        for (int i = 3; i < 6; i++) {
            new Thread(readWriteLockTest::read, "ReadThread-" + i).start();
        }
    }
}
