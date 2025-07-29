package synchronisation.shared_objects;

public class ShareObjectsTest {

    public static void main(String[] args) {
        JvmShareObjectsDesign1 design1 = new JvmShareObjectsDesign1();
        design1.testSharedObject();

        JvmShareObjectsDesign2 design2 = new JvmShareObjectsDesign2();
        design2.testSharedObject();
    }
}
