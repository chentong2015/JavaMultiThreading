package work_tasklimiter.project;

import java.util.Random;

// 模拟最多只有throttleLimit个线程在并发执行
public class CustomThrottleLimiterTest {

    public static final int THROTTLE_LIMIT = 5;

    public static void main(String[] args) throws InterruptedException {
        CustomThrottleLimiter throttleLimitImpl = new CustomThrottleLimiter(THROTTLE_LIMIT);

        Random random = new Random();
        for (int n = 0; n < 15; n++) {
            String taskName = "index " + n;
            throttleLimitImpl.putTask(new CustomTask(taskName, random.nextInt(1, 5)));
        }
        throttleLimitImpl.runTask();

        for (int n = 20; n < 25; n++) {
            String taskName = "index " + n;
            throttleLimitImpl.putTask(new CustomTask(taskName, random.nextInt(1, 5)));
        }
        throttleLimitImpl.runTask();
    }
}
