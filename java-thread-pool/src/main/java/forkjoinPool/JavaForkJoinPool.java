package forkjoinPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

// TODO. ForkJoinPool 高并发线程池: 专门处理计算密集型任务
// 1. fork 差分成大量子任务(数量很大)
// 2. parallel 多线程并行处理子任务(单任务时间比较短)
// 3. join 合并子任务执行结果
public class JavaForkJoinPool {

    public static void main(String[] args) throws Exception {
        List<String> partitions = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            partitions.add("partition: " + index);
        }

        // TODO. 使用自定义的线程池来并发处理sub_streams流
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        Future<Long> future1 = forkJoinPool.submit(() ->
              partitions.parallelStream()
                      .map(partition -> {
                          // ForkJoinPool-1-worker-x 不同线程并发处理
                          System.out.println(Thread.currentThread().getName());
                          return partition + "::";
                      })
                      .mapToLong(String::length)
                      .sum()
        );
        System.out.println("Future 1 test");
        System.out.println(future1.get());

        // TODO. 始终只有一个子线程处理全部stream流，没有并发效果
        Future<Long> future2 = forkJoinPool.submit(() ->
                partitions.stream()
                        .map(partition -> {
                            // ForkJoinPool-1-worker-1 单线程处理
                            System.out.println(Thread.currentThread().getName());
                            return partition + "::";
                        })
                        .mapToLong(String::length)
                        .sum()
        );
        System.out.println("Future 2 test");
        System.out.println(future2.get());
    }
}
