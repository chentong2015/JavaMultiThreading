package synchronisation.synchronize;

public class SharedResourceSynTest {

    public static void main(String[] args) throws InterruptedException {
        SharedResourceSyn sharedResourceSyn = new SharedResourceSyn();
        SharedResourceSyn sharedResourceSyn2 = new SharedResourceSyn();
        Thread thread1 = new Thread(sharedResourceSyn::printSynMethod, "thread 1");
        Thread thread2 = new Thread(sharedResourceSyn2::printSynMethod2, "thread 2");
        thread1.start();
        thread2.start();
        Thread.sleep(3000);

        // 测试静态方法的同步效果
        Thread thread3 = new Thread(SharedResourceSynStatic::increaseSynMethod, "thread 3");
        Thread thread4 = new Thread(SharedResourceSynStatic::increaseSynMethod, "thread 4");
        thread3.start();
        thread4.start();

        Thread.sleep(2000);
        System.out.println(SharedResourceSynStatic.getNumber());
    }
}
