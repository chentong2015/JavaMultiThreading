package synchronisation.shared_objects;


public class JvmShareObjectsDesign1 {

    // 多个线程所共享的对象，启动包含所共享的object
    private HandlerClass sharedHandler;

    // TODO. 每个线程都会修改共享对象，创建新的Handler Object
    //  导致线程A在调用对象方法时，可能调用到的线程B所创建的Handler Object
    //  导致线程A在执行时使用错误object对象，与A初始的object对象不一致
    public void testSharedObject() {
        for (int i=0; i < 100; i++) {
            new Thread(() -> {
                sharedHandler = new HandlerClass(new Object());
                sharedHandler.handleObject();
            }).start();
        }
    }

    static class HandlerClass {
        private final Object object;

        public HandlerClass(Object object) {
            this.object = object;
            System.out.println("Init Object:" + object);
        }

        // 处理时，object可能已经被别的线程修改，造成异常
        public void handleObject() {
            System.out.println("Current Object:" + object);
            if (object == null) {
                // throw new RuntimeException("null object");
                System.out.println("Cause Other Exceptions");
            }
        }
    }
}
