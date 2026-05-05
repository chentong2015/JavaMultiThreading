package synchronisation_lock;

// TODO. 利用对象来实现同步执行的效果
public class JavaSynchronizedLock {

    private int index;

    // 类型的静态成员存储在方法区，多线程共享性
    // 作为锁, 一般需要设置成final static常量, 保存值的不可变性
    // 作为锁, 不能使用primitive type, 不是对象且没有固有的锁
    private final static String syncStr = "lock";
    private final Object object = new Object();

    // 同一个时刻只有一个线程能够拿到object对象上的锁，执行语句块的内容
    public void print() {
        String name = Thread.currentThread().getName();
        synchronized (object) {
            for (index = 10; index > 0; index--) {
                System.out.println(name + ": " + index);
            }
        }
    }

    // TODO. 不可同步化局部变量对象
    // 每个Thread都将拥有自己的局部变量，都将获得锁然后执行
    public void printSynBlockTest() {
        String name = Thread.currentThread().getName();
        String localStr = "Lock";
        synchronized (localStr) {
            for (index = 10; index > 0; index--) {
                System.out.println(name + ": " + index);
            }
        }
    }
}