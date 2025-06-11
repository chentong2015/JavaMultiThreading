package fork_join_pool;

import java.util.stream.IntStream;

public class JavaForkJoinPool {

    // TODO. Stream.parallel() 会使用全局的ForkJoinPool.commonPool()
    public static void main(String[] args) {
        IntStream.range(0, 100).parallel().forEach(i -> {
            System.out.println(Thread.currentThread().getName());
        });
    }
}
