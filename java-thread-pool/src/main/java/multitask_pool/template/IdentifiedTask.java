package multitask_pool.template;

import java.util.concurrent.Callable;

public class IdentifiedTask<T> {

    private final String id;
    private final Callable<T> task;

    public IdentifiedTask(String id, Callable<T> task) {
        this.id = id;
        this.task = task;
    }

    public String getId() {
        return id;
    }

    public Callable<T> getTask() {
        return task;
    }
}
