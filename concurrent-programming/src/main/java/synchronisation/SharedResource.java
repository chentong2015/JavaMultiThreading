package synchronisation;

public class SharedResource {

    // 实例对象属性存储在内存的堆空间: 所有Threads共享数据
    private int index;

    // TODO. 共享的数据，由于线程的调度造成执行的不规则性(交替且无序)
    public void print() {
        String name = Thread.currentThread().getName();
        for (index = 10; index > 0; index--) {
            // 并非原子操作: 线程在执行到这一步时可能发生切换
            System.out.println(name + ": " + index);
        }
    }

    // TODO. 共享的数据，由于非同步操作造成结果不可预见
    private int count = 0;

    public void increaseCount() {
        for (int i = 0; i < 20000; i++) {
            count++;
        }
    }

    public int getCount() {
        return count;
    }

    // TODO: 非共享的数据
    // 方法成员的局部变量存储存储在线程Stack栈空间中: 线程间相互独立
    // Local variables, formal method parameters, and exception handler parameters are never shared between threads
    public int printSecurity() {
        String name = Thread.currentThread().getName();
        for (int i = 10; i > 0; i--) {
            System.out.println(name + ": " + i);
        }

        int count = 0;
        for (int i = 0; i < 20000; i++) {
            count++;
        }
        return count;
    }
}
