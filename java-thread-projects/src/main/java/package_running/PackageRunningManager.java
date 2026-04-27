package package_running;

import java.util.HashSet;
import java.util.Set;

public class PackageRunningManager {

    // TODO. 修改集合数据都在Lock Block锁范围内执行，不需要线程安全的Set
    private final Set<String> runningPackageSet = new HashSet<>();

    private boolean isRunInProgress(String packageName) {
        return runningPackageSet.contains(packageName);
    }

    private void addRunningPackage(String packageName) {
        if (runningPackageSet.contains(packageName)) {
            return;
        }
        runningPackageSet.add(packageName);
    }

    private void removeRunningPackage(String packageName) {
        if (packageName == null || !runningPackageSet.contains(packageName)) {
            return;
        }
        runningPackageSet.remove(packageName);
    }

    // TODO. 获取锁后必须再判断PackageID的执行状态
    // 因为运行过程是异步的，直接返回并释放锁
    // 其它的线程获取到锁之后，必须要能够判断是否正在运行
    public void runPackage(String packageId, String label) {
        synchronized (runningPackageSet) {
            if (isRunInProgress(packageId)) {
                System.out.println("Concurrent: The package is running : " + label);
                return;
            }

            // 同一时刻只有一个线程能够记录(修改)Package的运行状态
            addRunningPackage(packageId);

            try {
                new Thread(() -> {
                    System.out.println("run asynchronously");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Finish running package : " + label);
                    // 在异步的线程执行接受后，必须移除当前packageId
                    removeRunningPackage(packageId);
                }).start();
            } catch (Exception exception){
                // 如果执行的过程中出现异常，必须保证移除packageId
                removeRunningPackage(packageId);
            }
        }
    }
}
