package broadcastershared;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonSimulation {

    protected static List<Thread> threads = new ArrayList<>();
    protected static List<CommonBroadcastComponent> components = new ArrayList<>();

    protected static void createAndStartThreads() {
        // Start all components
        for (CommonBroadcastComponent component : components) {
            Thread thread = new Thread(component);
            threads.add(thread);
            thread.start();
        }
    }

    // Run for 60 (or otherwise specified) seconds
    protected static void runForTime(long timeToRun) {
        try {
            System.out.println("Running for " + (timeToRun / 1000) + " seconds");
            Thread.sleep(timeToRun);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Stop threads
    protected static void interuptThreads() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    protected static boolean createDirectory(String dirPath) {
        File dir = new File(dirPath);

        // Delete if exists
        if (dir.exists()) {
            if (deleteDirectory(dir)) {
                System.out.println("Deleted existing directory: " + dirPath);
            } else {
                System.err.println("Failed to delete directory: " + dirPath);
                return false;
            }
        }

        if (dir.mkdirs()) {
            System.out.println("Created fresh directory: " + dirPath);
            return true;
        } else {
            System.err.println("Failed to create directory: " + dirPath);
            return false;
        }
    }

    protected static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!deleteDirectory(file)) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }
}
