package execution_sequential;

import java.util.concurrent.atomic.AtomicInteger;

// TODO. CAS自旋方式(不阻塞线程)实现线程交替操作
public class JavaThreadOrder {

    private static AtomicInteger value = new AtomicInteger(0);

    // volatile 保证线程可见
    private static volatile boolean isFirst = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (value.get() <= 100) {
                if (isFirst) {
                    System.out.println("Thread 1 - " + value.getAndIncrement());
                    isFirst = false;
                }
            }
        }).start();

        new Thread(() -> {
            while (value.get() <= 100) {
                if (!isFirst) {
                    System.out.println("Thread 2 - " + value.getAndIncrement());
                    isFirst = true;
                }
            }
        }).start();
    }
}
