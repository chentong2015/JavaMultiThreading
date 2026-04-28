package project_tasklimiter.custom;

import java.util.Comparator;

// 对Task进行优先级排序，用于插入优先队列中
public class CustomTaskComparator implements Comparator<CustomTask> {

    @Override
    public int compare(CustomTask task1, CustomTask task2) {
        return task1.getPriority() - task2.getPriority();
    }
}