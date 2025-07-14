package chainofresponsibility;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.Message;
import broadcastershared.Logger;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public abstract class Handler extends CommonBroadcastComponent implements Runnable {

    protected Handler nextComponent;
    protected Handler firstComponent;


    public Handler(String ID, Handler firstComponent) {
        super(ID,"chain_logs/" + ID + "_log.txt");

        this.firstComponent = firstComponent;
    }

    protected boolean handleMessage(Message msg){

        // Forward if receiving own message
        if (msg.getSenderID().equals(ID)) {
            handleNext(msg);
            return true;
        }

        processMessageReceived(msg);

        return handleNext(msg);
    };

    @Override
    protected void generateMessages() {

        try {
            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message commandMessage = generateCommandMessage();

            forwardMessage(commandMessage);

            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message broadcastToAll = generateTextMessageToAll();

            forwardMessage(broadcastToAll);

            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message broadcastToRandom = generateRandomTextMessage();

            forwardMessage(broadcastToRandom);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    // Helper to facilitate forwarding to start of chain (or next if component is the start)
    private void forwardMessage(Message message) {
        if (firstComponent != null) {
            firstComponent.handleMessage(message);
        } else {
            nextComponent.handleMessage(message);
        }
        logger.logSent(message);
    }

    // Helper to send message to next component
    protected boolean handleNext(Message msg) {
        if (nextComponent != null) {
            return nextComponent.handleMessage(msg);
        }
        return true;
    }



    public Handler getFirstComponent() {
        return firstComponent;
    }

    public void setFirstComponent(Handler firstComponent) {
        this.firstComponent = firstComponent;
    }

    public Handler getNextComponent() {
        return nextComponent;
    }

    public void setNextComponent(Handler nextComponent) {
        this.nextComponent = nextComponent;
    }


}
