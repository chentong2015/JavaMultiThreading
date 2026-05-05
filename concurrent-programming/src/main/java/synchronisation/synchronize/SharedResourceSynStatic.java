package synchronisation.synchronize;

// TODO. 锁定class Object类型对象的monitor, 和具体实例对象无关
// For static method, the monitor associated with the "Class" object for the method's class is used
public class SharedResourceSynStatic {

    private static int number = 0;

    // TODO. Synchronized Static Method 锁定class类型对象的monitor
    public static void increaseSynMethod() {
        for (int index = 0; index < 20000; index++) {
            number++;
        }
    }

    // TODO. Synchronized Static Block
    public static void increaseSynBlock() {
        synchronized (SharedResourceSynStatic.class) {
            for (int index = 0; index < 20000; index++) {
                number++;
            }
        }
    }

    public static int getNumber() {
        return number;
    }
}
