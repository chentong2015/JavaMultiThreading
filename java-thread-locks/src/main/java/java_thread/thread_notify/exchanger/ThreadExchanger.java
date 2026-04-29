package java_thread.thread_notify.exchanger;

import java.util.concurrent.Exchanger;

// TODO: Exchanger 线程之间数据交流, 可用于工作流校对
// 用于进行线程间的数据交换，它提供一个同步点，在这个同步点两个线程可以交换彼此的数据
// 这两个线程通过exchange方法交换数据
// - 如果第一个线程先执行exchange方法，它会一直等待第二个线程也执行exchange，
// - 当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产出来的数据传递给对方
public class ThreadExchanger {

    public void testExchanger() throws InterruptedException {
        // 1. 线程A调用public V exchange(V dataA)方法，线程A到达同步点，并且在线程B到达同步点前一直等待
        // 2. 线程B调用public V exchange(V dataB)方法，线程B到达同步点
        // 3. 线程A与线程B都达到同步点时，线程将自己的数据传递给对方，两个线程完成了数据的交换了
        Exchanger<String> exchanger = new Exchanger<>();
        String valueFromAnotherThread = exchanger.exchange("exchange info");
    }
}
