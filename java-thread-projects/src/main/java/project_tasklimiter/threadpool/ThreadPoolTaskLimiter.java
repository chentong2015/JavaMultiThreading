package project_tasklimiter.threadpool;


import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTaskLimiter {

    private static final int THROTTLE_LIMIT = 5;

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(THROTTLE_LIMIT,
                THROTTLE_LIMIT,
                0L,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>()
        );


    }
}
