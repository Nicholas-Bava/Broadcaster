package chainofresponsibility;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.Message;


public abstract class Handler extends CommonBroadcastComponent implements Runnable {

    protected CommonBroadcastComponent nextComponent;
    protected CommonBroadcastComponent firstComponent;


    public Handler(String ID, CommonBroadcastComponent firstComponent) {
        super(ID,"chain_logs/" + ID + "_log.txt");

        this.firstComponent = firstComponent;
    }

    @Override
    public boolean handleMessage(Message msg){

        // Forward if receiving own message
        if (msg.getSenderID().equals(ID)) {
            handleNext(msg);
            return true;
        }

        processMessageReceived(msg);

        return handleNext(msg);
    };

    @Override
    public void generateMessages() {

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



    public CommonBroadcastComponent getFirstComponent() {
        return firstComponent;
    }

    public void setFirstComponent(CommonBroadcastComponent firstComponent) {
        this.firstComponent = firstComponent;
    }

    public CommonBroadcastComponent getNextComponent() {
        return nextComponent;
    }

    @Override
    public void setNextComponent(CommonBroadcastComponent nextComponent) {
        this.nextComponent = nextComponent;
    }


}
