package concurrent_tools.cyclic_barrier;

import java.util.concurrent.*;

// 项目场景: 等待一定数量的线程共同执行Audit发送和Log日志记录
public class CyclicBarrierTaskWait {

    private static final int NB_THREADS_SENDER = 5;
    private final ExecutorService executorServiceSender = Executors.newFixedThreadPool(NB_THREADS_SENDER);

    private static final int NB_THREADS_LOG = 40;
    private final ExecutorService executorService = Executors.newFixedThreadPool(NB_THREADS_LOG);

    private static final int NB_AUDIT_LOG = 100;

    // TODO. 等待多个线程执行Event发送并等待全部结束
    public void testCyclicBarrierWithThreads() throws ExecutionException, InterruptedException, TimeoutException {
        Future<Integer>[] result = new Future[NB_THREADS_LOG];
        CyclicBarrier startBarrier = new CyclicBarrier(NB_THREADS_SENDER + NB_THREADS_LOG);

        for (int i = 0; i < NB_THREADS_SENDER; ++i) {
            executorServiceSender.submit(() -> {
                // Wait for other threads
                startBarrier.await(10, TimeUnit.SECONDS);

                while (true) {
                    // .sendWaitingEvents();
                    Thread.sleep(Math.round(Math.random() * 100));
                }
            });
        }

        for (int i = 0; i < NB_THREADS_LOG; ++i) {
            result[i] = executorService.submit(() -> {
                // Wait for other threads
                startBarrier.await(5, TimeUnit.SECONDS);

                for (int j = 0; j < NB_AUDIT_LOG; ++j) {
                    // .sendWaitingEvents();
                    Thread.sleep(Math.round(Math.random() * 5));
                }
                return 0;
            });
        }

        // Wait for the end of all threads
        for (int i = 0; i < NB_THREADS_LOG; ++i) {
            result[i].get(30, TimeUnit.SECONDS);
        }
    }
}