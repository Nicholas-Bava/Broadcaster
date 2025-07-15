package mediator;

import broadcastershared.CommonBroadcastComponent;
import broadcastershared.Message;

import java.util.HashMap;
import java.util.Map;

public class Mediator {

    private Map<String, CommonBroadcastComponent> components;

    public Mediator() {
        this.components = new HashMap<>();
    }

    public void addComponent(CommonBroadcastComponent component) {
        components.put(component.getID(), component);
    }

    public void sendMessage(Message message) {
        if (message.getRecipientID().equals("*")){
            for (Map.Entry<String, CommonBroadcastComponent> entry : components.entrySet()) {
                entry.getValue().handleMessage(message);
            }
        } else {
            components.get(message.getRecipientID()).handleMessage(message);
        }

    }
}
