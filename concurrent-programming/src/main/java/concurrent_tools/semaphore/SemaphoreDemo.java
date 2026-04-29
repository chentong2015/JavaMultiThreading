package concurrent_tools.semaphore;

import java.util.concurrent.Semaphore;

// TODO. Semaphore 信号量
// 它通过控制一定数量的许可(permit)的方式, 来达到限制通用资源访问
// 例如: 控制并发的线程数, 流量控制, 公用资源访问控制
public class SemaphoreDemo {

    public void testSemaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(10);
        semaphore.acquire();
        semaphore.release();

        // 使用tryAcquire()立即返回permit的获取结果, 避免阻塞
        boolean hasPermit = semaphore.tryAcquire();
    }
}