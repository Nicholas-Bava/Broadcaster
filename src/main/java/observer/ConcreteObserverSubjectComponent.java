package observer;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.Message;

import java.util.HashMap;
import java.util.Map;

// Note that in this assignment, these components are both the Subject (notifies) and the Observer (updates)
public class ConcreteObserverSubjectComponent extends Observer implements Subject{

    Map<String, CommonBroadcastComponent> observers = new HashMap<>();

    protected ConcreteObserverSubjectComponent(String ID) {
        super(ID);
    }

    // Update only calls handleMessage if it was a broadcast to all or the message was specifically for this
    // component
    @Override
    public void update(Message msg) {

        if (msg.getRecipientID().equals("*") || msg.getRecipientID().equals(this.ID)) {
            handleMessage(msg);
        }

    }

    // This part is the key to my observer pattern. Rather than call handleMessage (like mediator does) for the specific
    // components that need to, just notify all observers that the "change" (or message in this case) was made. I let
    // the observer use its implemented update method to determine if the message is for it and what to do with it
    // if so.
    @Override
    public void notifyMessage(Message msg) {
        for (Map.Entry<String, CommonBroadcastComponent> entry : observers.entrySet()) {
            entry.getValue().update(msg);
        }
    }

    @Override
    public void subscribe(CommonBroadcastComponent observer) {
        observers.put(observer.getID(), observer);
    }

    @Override
    public void unsubscribe(CommonBroadcastComponent observer) {
        observers.remove(observer.getID());
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.getSenderID().equals(ID)) {
            return true;
        }

        processMessageReceived(msg);

        return true;
    }

    @Override
    public void generateMessages() {
        try {
            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message commandMessage = generateCommandMessage();
            notifyMessage(commandMessage);
            logger.logSent(commandMessage);

            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message broadcastToAll = generateTextMessageToAll();
            notifyMessage(broadcastToAll);
            logger.logSent(broadcastToAll);

            // Wait 1-20 seconds (0-19 plus 1)
            Thread.sleep(rand.nextInt(20000) + 1000);

            Message broadcastToRandom = generateRandomTextMessage();
            notifyMessage(broadcastToRandom);
            logger.logSent(broadcastToRandom);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }



}
