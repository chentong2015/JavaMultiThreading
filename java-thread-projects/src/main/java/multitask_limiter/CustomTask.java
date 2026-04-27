package multitask_limiter;

import java.util.Random;

public class CustomTask implements Runnable {

    private final String taskName;
    private final int priority;

    public CustomTask(String taskName, int priority) {
        this.taskName = taskName;
        this.priority = priority;
    }

    @Override
    public void run() {
       try {
           String threadName = Thread.currentThread().getName();
           System.out.println("Run " + taskName + " by " + threadName + " with priority: " + priority);
           Thread.sleep(new Random().nextInt(1000, 5000));
           System.out.println("Finish Run " + taskName + " by " + threadName + " with priority: " + priority);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }

    public int getPriority() {
        return priority;
    }
}