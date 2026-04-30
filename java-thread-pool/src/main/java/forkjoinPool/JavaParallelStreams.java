package forkjoinPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO. Streams Pipeline 不要任意并行执行
// - Stream.parallel()并行执行并不一定能提高性能, 需要考虑特定场景
// - 拆分整个流成多个sub_streams流并发执行, 底层依赖通用的ForkJoinPool线程池
// - 并发执行中一个pipeline异常可能损害系统中其他不相关性能
public class JavaParallelStreams {

    // TODO: .stream().parallel() & .parallelStream() 如何执行
    // Returns a possibly parallel Stream with this collection as its source
    // When a stream executes in parallel, the Java runtime partitions the stream into multiple sub_streams.
    // Aggregate operations iterate over and process these sub_streams in parallel and then combine results.
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> partitions = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            partitions.add("partition: " + index);
        }

        // TODO. 依赖ForkJoinPool.commonPool-worker公共线程池
        long result = partitions.parallelStream()
                .map(partition -> {
                    System.out.println(Thread.currentThread().getName());
                    return partition + "::::";
                })
                .mapToLong(String::length)
                .sum();
        System.out.println(result);
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
