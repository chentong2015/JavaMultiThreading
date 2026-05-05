package synchronisation_lock.read_write_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// TODO. 读写锁一般应用于"读多写少"的场景，提升读的性能
public class JavaReadWriteLock {

    Map<String, String> syncHashMap = new HashMap<>();
    ReadWriteLock lock = new ReentrantReadWriteLock();

    // 写锁要互斥，必须等到写锁unlock之后才能获取锁 -> 独占锁/独享锁
    Lock writeLock = lock.writeLock();

    // 读锁不互斥 -> 共享锁
    Lock readLock = lock.readLock();

    public void put(String key, String value) {
        try {
            writeLock.lock();
            syncHashMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public String get(String key) {
        try {
            readLock.lock();
            return syncHashMap.get(key);
        } finally {
            readLock.unlock();
        }
    }
}
