package broadcastershared;

import java.util.Random;

// Abstract class to handle all commonalities between the 3 methods (since they all log and generate sample test
// messages the same way). Implementations then handle the specifics of their associated design pattern (within the
// 3 packages)
public abstract class CommonBroadcastComponent implements Runnable{

    protected String ID;
    protected Logger logger;
    protected Random rand = new Random();
    protected Random randRecipient = new Random();
    protected boolean running = true;

    protected CommonBroadcastComponent(String ID, String logFileName) {
        this.ID = ID;
        this.logger = new Logger(ID, logFileName);
    }

    // Implemented by 3 implementations (specific to design pattern)
    public abstract void generateMessages();
    // Implemented by 3 implementations (specific to design pattern)
    public abstract boolean handleMessage(Message msg);

    public void setNextComponent(CommonBroadcastComponent nextComponent) {
        // does nothing, implementation can override if needed (chain of responsibility)
    }

    public void update(Message msg) {
        // does nothing, implementation can override if needed (observer)
    }

    // Log the incomming text or command execution if message is for this component (either via * or ID)
    protected void processMessageReceived(Message msg) {
        if (msg.getRecipientID().equals("*") || msg.getRecipientID().equals(this.ID)) {

            if (msg.getMessageType().name().equals("TEXT")){
                logger.logText(msg);
            } else if (msg.getMessageType().name().equals("COMMAND")){
                long startTime = System.currentTimeMillis();
                try {
                    Thread.sleep(msg.getWaitTime());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                long finalTime = System.currentTimeMillis() - startTime;
                logger.logCommand(msg, String.valueOf(finalTime));
            }
        }
    }

    // Generate a Command
    protected Message generateCommandMessage() {
        Message commandMessage = new Message(this.ID,
                "COMP_" + Integer.toString(randRecipient.nextInt(10) + 1),
                Message.type.COMMAND,
                "Test Command");

        commandMessage.setWaitTime(rand.nextInt(101));

        return commandMessage;
    }

    // Generate a broadcast message to all components
    protected Message generateTextMessageToAll() {
        Message broadcastToAll = new Message(this.ID,
                "*",
                Message.type.TEXT,
                "Test Message to All Components");

        return broadcastToAll;
    }

    // Generate a text message to a random component
    protected Message generateRandomTextMessage() {
        Message broadcastToRandom = new Message(this.ID,
                "COMP_" + Integer.toString(randRecipient.nextInt(10) + 1),
                Message.type.TEXT,
                "Test Message to Random Component");

        return broadcastToRandom;
    }

    public String getID() {
        return ID;
    }

    // Run the component as a thread
    @Override
    public void run() {
        generateMessages();

        while (running) {
            if (Thread.currentThread().isInterrupted()) {
                stop();
            }
        }

    }

    public void stop() {
        running = false;
        if (logger != null) {
            logger.close();
        }
    }
}
