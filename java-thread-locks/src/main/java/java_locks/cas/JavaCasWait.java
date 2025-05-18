package java_locks.cas;

public class JavaCasWait {

    // TODO: 线程自旋等待，直到循环判断的条件发生变化，判断再触发
    volatile boolean eventNotificationNotReceived;

    void waitForEventAndHandleIt() {
        while (eventNotificationNotReceived) {
            java.lang.Thread.onSpinWait();
        }
        readAndProcessEvent();
    }

    void readAndProcessEvent() {
        // Read event from some source and process it
        // . . .
    }
}
