package thread_base;

// TODO. 只有一种方式创建Java线程: 实现Runnable接口, 实现run()方法
public class JavaThreadCreation {

    static class DemoRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable thread");
        }
    }

    static class DemoThread extends Thread {
        @Override
        public void run() {
            System.out.println(currentThread().getName());
        }
    }

    // TODO. 使用匿名类型创建线程并执行，可替换成lambda表达式
    public void testAnonymousThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous Runnable");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        new Thread() {
            @Override
            public void run() {
                System.out.println("Anonymous class");
            }
        }.start();
    }

    public static void main(String[] args) {
        // 创建并启动一个新线程，但是什么都不做
        Thread thread = new Thread();
        thread.start();

        // 线程的优先级只是给OS参考，并非确定的执行顺序
        Thread runThread = new Thread(new DemoRunnable());
        runThread.start();

        DemoThread demoThread = new DemoThread();
        demoThread.setName("Name");

        // TODO. 注意run()方法级别和start()线程级别调用
        // .run() 方法级别的调用: 等效于调主线程的run()方法，始终只在一个线程
        demoThread.run();

        // .start() 线程级别的调用: 会创建新的线程，并自动调用线程的run()方法
        demoThread.start();

        // 同个线程不能.start()启动多次，否则抛出IllegalThreadStateException
        // demoThread.start();
    }
}