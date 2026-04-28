package consumer_producer.blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;

// 1. 生产者和消费者使用同一个有界阻塞队列
// 2. 消费者只有在队列中有数据值才能读取，否则应该处于阻塞状态
public class BlockingConsumer implements Runnable {

    private ArrayBlockingQueue<String> queue;

    public BlockingConsumer(ArrayBlockingQueue<String> buffer) {
        this.queue = buffer;
    }

    // 在消费端循环监听队列中是否有新的信息
    @Override
    public void run() {
        while (true) {
            if (queue.isEmpty()) {
                continue;
            }

            if (queue.peek().equals("EOF")) {
                System.out.println("Existing..");
                break;
            }
            try {
                System.out.println("removed " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
