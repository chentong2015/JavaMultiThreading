package collections.hashset;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashSet {

    // HashSet类型本身不是线程安全的类型，不支持多线程并发修改
    private static Set<String> set = new HashSet<>();

    // 构建一个线程安全的HashSet Wrapper包装类
    private static Set<String> setSafe = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(100);
        for (int index = 1; index < 10000; index++) {
            int finalIndex = index % 100;
            executors.execute(() -> {
                // set.add("item" + finalIndex); 195 多线程情况造成重复item
                setSafe.add("item" + finalIndex); // 100 保证重复的item不会被添加
            });
        }
        executors.shutdown();

        Thread.sleep(5000);
        System.out.println(setSafe.size());
        System.out.println("Done");
    }
}
