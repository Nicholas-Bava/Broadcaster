package mediator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MediatorSimulation {

    public static void main(String[] args) {
        createDirectory("mediator_logs");

        System.out.println("Starting Mediator Pattern...");

        // Singleton :)
        Mediator mediator = new Mediator();

        List<Component> components = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Component comp = new Component("COMP_" + i, mediator);
            mediator.addComponent(comp);
            components.add(comp);
        }

        // Start all components
        List<Thread> threads = new ArrayList<>();
        for (Component component : components) {
            Thread thread = new Thread(component);
            threads.add(thread);
            thread.start();
        }

        // Run for 60 seconds
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop threads
        for (Thread thread : threads) {
            thread.interrupt();
        }



        System.out.println("Mediator Pattern completed. Check mediator_logs/ directory.");

    }

    public static boolean createDirectory(String dirPath) {
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

    private static boolean deleteDirectory(File dir) {
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
