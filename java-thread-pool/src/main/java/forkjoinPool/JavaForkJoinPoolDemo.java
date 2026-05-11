package forkjoinPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

// TODO. 使用自定义ForkJoinPool来并发处理sub_streams流
public class JavaForkJoinPoolDemo {

    public static void main(String[] args) throws Exception {
        List<String> partitions = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            partitions.add("partition: " + index);
        }

        // 使用parallelStream流，带有并行效果 ForkJoinPool-1-worker-x
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        Future<Long> future = forkJoinPool.submit(() ->
              partitions.parallelStream()
                      .map(partition -> {
                          System.out.println(Thread.currentThread().getName());
                          return partition + "::";
                      })
                      .mapToLong(String::length)
                      .sum()
        );

        // 阻塞等待并行流的处理结束
        System.out.println(future.get());
    }
}