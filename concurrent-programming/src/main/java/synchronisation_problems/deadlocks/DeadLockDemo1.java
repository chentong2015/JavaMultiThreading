package synchronisation_problems.deadlocks;

// 当一个线程拿到对象上的锁，在执行方法的过程中由于条件标识不满足处于循环
// 另外的线程没有办法再拿到同一个对象上的锁，从而无法修改条件标识
public class DeadLockDemo1 {

    private String message;

    public synchronized void write(String message) {
        while (message == null) {
            // spin wait
        }
        this.message = message;
    }

    public synchronized void read() {
        while (message == null) {
            // spin wait
        }
        System.out.println("Read message: " + message);
        message = null;
    }

    public static void main(String[] args) {
        System.out.println("Start read..");
        DeadLockDemo1 deadLockDemo1 = new DeadLockDemo1();
        deadLockDemo1.read();

        // 用于写的线程根本就无法运行
        System.out.println("Start write..");
        new Thread(() -> {
            deadLockDemo1.write("test message");
            System.out.println(Thread.currentThread().getState());
        }).start();
    }
}
