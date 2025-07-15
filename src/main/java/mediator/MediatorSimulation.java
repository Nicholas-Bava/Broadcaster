package mediator;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.CommonSimulation;

public class MediatorSimulation extends CommonSimulation {

    public static void main(String[] args) {
        createDirectory("mediator_logs");

        System.out.println("Starting Mediator Pattern...");

        // Singleton :)
        Mediator mediator = new Mediator();

        for (int i = 1; i <= 10; i++) {
            CommonBroadcastComponent comp = new Component("COMP_" + i, mediator);
            mediator.addComponent(comp);
            components.add(comp);
        }

        // Start all components
        createAndStartThreads();

        // Run for 60 seconds
        runForTime(60000);

        // Stop threads
        interuptThreads();

        System.out.println("Mediator Pattern completed. Check mediator_logs/ directory.");

    }

}
