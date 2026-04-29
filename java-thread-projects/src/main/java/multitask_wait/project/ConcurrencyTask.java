package multitask_wait.project;

import java.util.Objects;
import java.util.concurrent.Callable;

public record ConcurrencyTask<T>(String id, Callable<T> task) {

    public ConcurrencyTask(String id, Callable<T> task) {
        this.id = Objects.requireNonNull(id);
        this.task = Objects.requireNonNull(task);
    }
}