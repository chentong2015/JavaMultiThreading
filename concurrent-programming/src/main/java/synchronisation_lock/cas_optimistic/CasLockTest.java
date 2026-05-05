package synchronisation_lock.cas_optimistic;

public class CasLockTest {

    private static int count = 0;
    private static CasLockImpl casLockImpl = new CasLockImpl();

    public static void main(String[] args) throws InterruptedException {
        CasLock casLock = new CasLock();
        for (int index = 0; index < 1000; index++) {
            new Thread(casLock::increase).start();
        }
        Thread.sleep(2000);
        System.out.println(casLock.getMyNum());

        for (int index = 0; index < 3; index++) {
            new Thread(CasLockTest::increaseCount, "thread " + index).start();
        }
        Thread.sleep(2000);
        System.out.println(count);
    }

    // 使用自定义的CAS LOCK锁来保证线程的安全
    private static void increaseCount() {
        casLockImpl.lock();
        try {
            for (int index = 0; index < 20000; index++) {
                count++;
            }
        } finally {
            casLockImpl.unlock();
        }
    }
}
