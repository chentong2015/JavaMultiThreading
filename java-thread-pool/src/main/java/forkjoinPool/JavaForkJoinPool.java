package forkjoinPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class JavaForkJoinPool {

    // TODO. Stream.parallel() 会使用全局的ForkJoinPool.commonPool()
    private static void parallelExecution() {
        IntStream.range(0, 100).parallel().forEach(i -> {
            System.out.println(Thread.currentThread().getName());
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        List<String> partitions = new ArrayList<>();
        for (int index = 0; index < 500000; index++) {
            partitions.add("partition: " + index);
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        Future<Long> future = forkJoinPool.submit(() ->
              partitions.parallelStream()
                      .map(partition -> partition + "::")
                      .mapToLong(String::length)
                      .sum()
        );
        System.out.println(future.get());

        // long result = partitions.parallelStream()
        //         .map(partition -> partition + "::")
        //         .mapToLong(String::length)
        //         .sum();
        // System.out.println(result);

        System.out.println(System.currentTimeMillis() - startTime);
    }
}
