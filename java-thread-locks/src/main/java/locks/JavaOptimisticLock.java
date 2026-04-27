package locks;

// OCC(Optimistic Concurrency Control): 乐观锁
// 通常用在数据争用不大，冲突较少的环境中，回滚的成本低于锁定数据的成本
// 假设多用户"并发的事务"在处理时不会彼此相互影响，各事务能够在不产生锁的情况下处理各自的那部分数据
// - 在提交数据之前，先检查在该事务读取数据之后(操作的这部分时间)有没有其他的事务又修改了那部分数据
// - 如果其他的事务有更新的话，正在提交的事务会进行回滚
public class JavaOptimisticLock {

    // TODO. 考虑并发冲突场景: 将target目标数据修改成它和value间的更大值
    public int updateTargetToMax(int target, int value) {
        int baseValue;
        int desiredTargetValue;
        int currentTargetValue = target;
        do {
            baseValue = currentTargetValue;
            desiredTargetValue = Math.max(baseValue, value);

            // 非原子性操作: 线程在这里可能会被抢占
            if (baseValue == target) {
                target = desiredTargetValue;
            }

            // 原子操作: 确保在没有其他线程更改target的前提下更改为desiredTargetValue
            // currentTargetValue = Interlocked.CompareExchange(target, desiredTargetValue, baseValue);
        } while (baseValue != currentTargetValue);
        return desiredTargetValue;
    }
}
