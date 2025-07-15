package observer;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.CommonSimulation;


public class ObserverSimulation extends CommonSimulation {

    public static void main(String[] args) {
        createDirectory("observer_logs");

        System.out.println("Starting Observer Pattern...");


        for (int i = 1; i <= 10; i++) {
            CommonBroadcastComponent comp = new ConcreteObserverSubjectComponent("COMP_" + i);
            components.add(comp);
        }

        // Subscribe each component to the other components
        for (CommonBroadcastComponent comp : components) {
            if (comp instanceof Subject) {
                Subject subject = (Subject) comp;

                for (CommonBroadcastComponent compToSubscribe : components) {
                    if (!comp.getID().equals(compToSubscribe.getID())) {
                        subject.subscribe(compToSubscribe);
                    }
                }
            }
        }

        // Start all components
        createAndStartThreads();

        // Run for 60 seconds
        runForTime(60000);

        // Stop threads
        interuptThreads();

        System.out.println("Observer Pattern completed. Check observer_logs/ directory.");

    }
}
