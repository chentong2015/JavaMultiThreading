package synchronisation_lock.reentrant_lock.condition;

public class ReentrantLockConditionTest {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockCondition javaReentrantLockCondition = new ReentrantLockCondition();
        for (int index = 0; index < 15; index++) {
            String item = "item " + index;
            new Thread(() -> {
                try {
                    javaReentrantLockCondition.putItem(item);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println(javaReentrantLockCondition.getCount()); // 10

        for (int index = 0; index < 3; index++) {
            new Thread(() -> {
                try {
                    String item = javaReentrantLockCondition.takeItem();
                    System.out.println(item);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println(javaReentrantLockCondition.getCount()); // 10

        for (int index = 0; index < 5; index++) {
            new Thread(() -> {
                try {
                    String item = javaReentrantLockCondition.takeItem();
                    System.out.println(item);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println(javaReentrantLockCondition.getCount()); // 7
    }
}
