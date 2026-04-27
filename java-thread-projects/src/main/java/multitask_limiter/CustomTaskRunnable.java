package multitask_limiter;

public class CustomTaskRunnable implements Runnable {

    private final String taskName;

    public CustomTaskRunnable(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
       try {
           System.out.println("Run CustomTaskRunnable " + taskName + " by " + Thread.currentThread().getName());
           Thread.sleep(5000);
           System.out.println("Finish Run CustomTaskRunnable " + taskName + " by " + Thread.currentThread().getName());
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }
}