package thread_types;

// TODO. 默认创建的线程(包括线程池中线程)不是守护线程，需要执行到结束
public class DaemonThread {

    // setDaemon(false): non daemon thread, it continues to run until the end
    // setDaemon(true): daemon thread, it terminates when user defined thread(non daemon) terminates.
    static class UserThread extends Thread {

        public UserThread() {
            setDaemon(false);
        }

        public void run() {
            int count = 0;
            while (true) {
                System.out.println("Worker " + count++);
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
