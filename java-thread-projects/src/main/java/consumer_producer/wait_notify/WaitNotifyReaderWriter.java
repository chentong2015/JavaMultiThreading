package consumer_producer.wait_notify;

// TODO. 使用Wait Notify实现消息的读写 => 等效于线程安全的队列
public class WaitNotifyReaderWriter {

    private String message;
    private boolean isEmpty = true;

    // 将wait()置于循环，在被唤醒的时候判断标识状态再执行
    public synchronized void writeSync(String message) throws InterruptedException {
        while (!isEmpty) {
            wait();
        }
        isEmpty = false;
        this.message = message;
        notifyAll();
    }

    public synchronized String readSync() throws InterruptedException {
        while (isEmpty) {
            wait();
        }
        isEmpty = true;
        notifyAll();
        return message;
    }
}