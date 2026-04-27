package package_running;

public class PackageRunningTest {

    public static void main(String[] args) throws Exception {
        PackageRunningManager packageRunningManager = new PackageRunningManager();
        new Thread(() -> packageRunningManager.runPackage("001", "label")).start();
        new Thread(() -> packageRunningManager.runPackage("001", "label")).start();

        Thread.sleep(6000);

        new Thread(() -> packageRunningManager.runPackage("001", "label")).start();
        new Thread(() -> packageRunningManager.runPackage("001", "label")).start();
    }
}
