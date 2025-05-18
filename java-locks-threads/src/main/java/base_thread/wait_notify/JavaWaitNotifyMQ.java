package base_thread.wait_notify;

// TODO. 使用Wait Notify实现消息的读写 => 等效于线程安全的队列
public class JavaWaitNotifyMQ {

    private String message;
    private boolean isEmpty = true;

    // 将wait()置于循环，在被唤醒的时候判断标识状态再执行
    public synchronized void writeSync(String message) {
        while (!isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isEmpty = false;
        this.message = message;
        notifyAll();
    }

    public synchronized String readSync() {
        while (isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isEmpty = true;
        notifyAll();
        return message;
    }
}