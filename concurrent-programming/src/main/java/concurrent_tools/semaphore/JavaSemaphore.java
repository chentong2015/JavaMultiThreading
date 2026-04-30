package concurrent_tools.semaphore;

import java.util.concurrent.Semaphore;

// TODO. Semaphore 信号量: 控制一定数量的许可(permit)来限制执行
// acquire和release需要成对出现且在同一个执行上下文(例如try-finally)
// Semaphore常用于控制并发的线程数, 流量控制, 公用资源访问控制
public class JavaSemaphore {

    public void testSemaphore() throws InterruptedException {
        Semaphore semaphore = new Semaphore(10);
        // 阻塞: Park挂起当前线程，等待系统信号来恢复
        semaphore.acquire();

        // 非阻塞: 使用tryAcquire()立即返回permit的获取
        boolean hasPermit = semaphore.tryAcquire();

        // 释放获取到的Permit许可，激活正在等待信号的线程
        semaphore.release();
    }
}