package forkjoinPool;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// ForkJoinPool在实战项目中的运用
public class JavaForkJoinPoolProject {

    // 并行线程数量可通过Config参数设置
    private static final int SIZE_PARALLELISM = 10;

    // 并发处理流数据的验证和过滤，最后整合Set集合的结果
    private void validateFileStream(Stream<String> fileStream) throws ExecutionException, InterruptedException {
        ForkJoinPool customThreadPool = new ForkJoinPool(SIZE_PARALLELISM);
        AtomicInteger index = new AtomicInteger();
        try {
            Set<Integer> setGroupIds = customThreadPool.submit(() -> fileStream.parallel()
                            .map(line -> validateLine(line, index.getAndIncrement() + 1))
                            .filter(ValidationResult::isValid)
                            .map(ValidationResult::getMainId)
                            .collect(Collectors.toSet())).get();
            System.out.println(setGroupIds.size());
        } finally {
            // 符合线程池关闭的策略
            customThreadPool.shutdown();
        }
    }

    private ValidationResult validateLine(String line, int index) {
        if (index > 100) {
            return new ValidationResult(false, index + 2);
        }
        return new ValidationResult(true, index);
    }

    static class ValidationResult {
        private boolean valid;
        private int mainId;

        public ValidationResult(boolean valid, int mainId) {
            this.valid = valid;
            this.mainId = mainId;
        }

        public boolean isValid() {
            return valid;
        }

        public int getMainId() {
            return mainId;
        }
    }
}
