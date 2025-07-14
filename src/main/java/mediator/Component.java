package mediator;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.Logger;
import broadcastershared.Message;

import java.util.Random;

public class Component extends CommonBroadcastComponent {

    private Mediator mediator;

    public Component(String ID, Mediator mediator) {
        super(ID,"mediator_logs/" + ID + "_log.txt");
        this.mediator = mediator;
    }

    public boolean handleMessage(Message msg) {
        if (msg.getSenderID().equals(ID)) {
            return true;
        }

        processMessageReceived(msg);

        return true;
    }

    @Override
    protected void generateMessages(){
        try {
            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message commandMessage = generateCommandMessage();

            mediator.sendMessage(commandMessage);
            logger.logSent(commandMessage);

            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message broadcastToAll = generateTextMessageToAll();

            mediator.sendMessage(broadcastToAll);
            logger.logSent(broadcastToAll);

            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message broadcastToRandom = generateRandomTextMessage();

            mediator.sendMessage(broadcastToRandom);
            logger.logSent(broadcastToRandom);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
