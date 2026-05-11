// 对于一个进程，OS拥有最大允许线程数量的限制，超过线程数极限将造成OOM
// Exception in thread "main" java.lang.OutOfMemoryError:
// unable to create native thread: possibly out of memory or process/resource limits reached
//   at java.base/java.lang.Thread.start0(Native Method)
//   at java.base/java.lang.Thread.start(Thread.java:802)
//   at memory.ThreadNumberLimits.main(ThreadNumberLimits.java:15)
public class ThreadNumLimits {

    // 最大允许的并行线程数量受到OS系统线程数限制
    private static int maxNumThreadsSupported = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws Exception {
        while (true) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000 * 60 * 60 * 24);
                } catch (Exception ex) {
                }
            }).start();
        }
    }
}