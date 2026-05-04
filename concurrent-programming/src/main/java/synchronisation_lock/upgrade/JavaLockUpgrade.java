package synchronisation_lock.upgrade;

import org.openjdk.jol.info.ClassLayout;

// 分析对象上的锁升级过程: 根据竞争程度来调整
public class JavaLockUpgrade {

    public static void main(String[] args) throws Exception {
        StorageClass obj = new StorageClass(1, "test");

        // 1. 无锁状态
        // OFF  SZ     TYPE DESCRIPTION         VALUE
        //  0   8      (object header: mark)    0x0000000000000001 (non-biasable; age: 0)
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        // 2. 轻量级锁状态: 存在简单的锁竞争
        // OFF  SZ     TYPE DESCRIPTION         VALUE
        //  0   8     (object header: mark)     0x000000735e3ff580 (thin lock: 0x000000735e3ff580)
        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }

        // 3. 重量级锁状态: 多线程竞争同一把锁
        // OFF  SZ     TYPE DESCRIPTION          VALUE
        //  0   8      (object header: mark)     0x0000023866937272 (fat lock: 0x0000023866937272)
        new Thread(() -> {
            synchronized (obj) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {}
            }
        }).start();

        new Thread(() -> {
            synchronized (obj) {
                System.out.println("竞争锁");
                System.out.println(ClassLayout.parseInstance(obj).toPrintable());
            }
        }).start();
    }

    static class StorageClass {
        int id;
        String name;

        public StorageClass(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
