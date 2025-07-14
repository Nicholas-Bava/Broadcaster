package chainofresponsibility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChainSimulation {
    public static void main(String[] args) {
        createDirectory("chain_logs");

        System.out.println("Starting Chain of Responsibility Pattern...");

        ConcreteComponent compOne = new ConcreteComponent("COMP_1", null);
        ConcreteComponent compTwo = new ConcreteComponent("COMP_2", compOne);
        ConcreteComponent compThree = new ConcreteComponent("COMP_3",compOne);
        ConcreteComponent compFour = new ConcreteComponent("COMP_4", compOne);
        ConcreteComponent compFive = new ConcreteComponent("COMP_5", compOne);
        ConcreteComponent compSix = new ConcreteComponent("COMP_6", compOne);
        ConcreteComponent compSeven = new ConcreteComponent("COMP_7", compOne);
        ConcreteComponent compEight = new ConcreteComponent("COMP_8", compOne);
        ConcreteComponent compNine = new ConcreteComponent("COMP_9", compOne);
        ConcreteComponent compTen = new ConcreteComponent("COMP_10", compOne);

        compOne.setNextComponent(compTwo);
        compTwo.setNextComponent(compThree);
        compThree.setNextComponent(compFour);
        compFour.setNextComponent(compFive);
        compFive.setNextComponent(compSix);
        compSix.setNextComponent(compSeven);
        compSeven.setNextComponent(compEight);
        compEight.setNextComponent(compNine);
        compNine.setNextComponent(compTen);

        List<ConcreteComponent> components = new ArrayList<>();
        components.add(compOne);
        components.add(compTwo);
        components.add(compThree);
        components.add(compFour);
        components.add(compFive);
        components.add(compSix);
        components.add(compSeven);
        components.add(compEight);
        components.add(compNine);
        components.add(compTen);

        // Start all components
        List<Thread> threads = new ArrayList<>();
        for (ConcreteComponent component : components) {
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



        System.out.println("Chain of Responsibility Pattern completed. Check chain_logs/ directory.");

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
