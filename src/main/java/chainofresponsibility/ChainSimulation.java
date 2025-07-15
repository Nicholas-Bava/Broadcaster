package chainofresponsibility;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.CommonSimulation;

public class ChainSimulation extends CommonSimulation {
    public static void main(String[] args) {
        createDirectory("chain_logs");

        System.out.println("Starting Chain of Responsibility Pattern...");

        CommonBroadcastComponent compOne = new ConcreteComponent("COMP_1", null);
        CommonBroadcastComponent compTwo = new ConcreteComponent("COMP_2", compOne);
        CommonBroadcastComponent compThree = new ConcreteComponent("COMP_3",compOne);
        CommonBroadcastComponent compFour = new ConcreteComponent("COMP_4", compOne);
        CommonBroadcastComponent compFive = new ConcreteComponent("COMP_5", compOne);
        CommonBroadcastComponent compSix = new ConcreteComponent("COMP_6", compOne);
        CommonBroadcastComponent compSeven = new ConcreteComponent("COMP_7", compOne);
        CommonBroadcastComponent compEight = new ConcreteComponent("COMP_8", compOne);
        CommonBroadcastComponent compNine = new ConcreteComponent("COMP_9", compOne);
        CommonBroadcastComponent compTen = new ConcreteComponent("COMP_10", compOne);

        compOne.setNextComponent(compTwo);
        compTwo.setNextComponent(compThree);
        compThree.setNextComponent(compFour);
        compFour.setNextComponent(compFive);
        compFive.setNextComponent(compSix);
        compSix.setNextComponent(compSeven);
        compSeven.setNextComponent(compEight);
        compEight.setNextComponent(compNine);
        compNine.setNextComponent(compTen);

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
        createAndStartThreads();

        // Run for 60 seconds
        runForTime(60000);

        // Stop threads
        interuptThreads();

        System.out.println("Chain of Responsibility Pattern completed. Check chain_logs/ directory.");

    }

}
